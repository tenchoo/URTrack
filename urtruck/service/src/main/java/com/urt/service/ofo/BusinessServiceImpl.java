package com.urt.service.ofo;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.utils.BufferInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.pool.vendor.SybaseExceptionSorter;
import com.urt.dto.LaoFtpfileCollectDto;
import com.urt.dto.MailDto;
import com.urt.dto.Goods.ServiceStatusDto;
import com.urt.interfaces.ofo.BusinessService;
import com.urt.mapper.LaoFtpfileCollectMapper;
import com.urt.mapper.LaoFtpfileInfoMapper;
import com.urt.mapper.ext.LaoDownloadfileConfigExtMapper;
import com.urt.mapper.ext.LaoFtpFileInfoExtMapper;
import com.urt.mapper.ext.LaoFtpfileCollectExtMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoDownloadfileConfig;
import com.urt.po.LaoFtpfileCollect;
import com.urt.po.LaoFtpfileInfo;
import com.urt.po.ServiceStatus;
import com.urt.service.ofo.util.FtpUtils;
import com.urt.service.util.MailUtil;

@Service("businessService")
public class BusinessServiceImpl implements BusinessService {

	@Autowired
	private LaoFtpFileInfoExtMapper extMapper;

	@Autowired
	private LaoFtpfileInfoMapper infoMapper;

	@Autowired
	private LaoFtpfileCollectExtMapper collectMapper;

	@Autowired
	private LaoFtpfileCollectMapper collectMap;

	@Autowired
	private LaoDownloadfileConfigExtMapper laoDownload;

	private static Logger logger = Logger.getLogger(BusinessServiceImpl.class);

	@Autowired
	private MailUtil mailUtil;

	@Autowired
	private LaoUserExtMapper laoUserExtDao;

	/**
	 * 数据写入
	 */
	public boolean dataInsert() {
		Date date = new Date();// 获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		StringBuffer sb = new StringBuffer();
		List<LaoDownloadfileConfig> list = laoDownload.selectListFileByofo();
		if (null != list && list.size() > 0) {
			for (LaoDownloadfileConfig fileConfig : list) {
				sb.append(fileConfig.getFileprefixName());
				if (null != fileConfig.getFilesuffixDate()) {
					Date filesuffixDate = fileConfig.getFilesuffixDate();
					sb.append(sdf.format(filesuffixDate));
				} else {
					sb.append(sdf.format(date) + ".REQ");
				}
				sb.append(",");
			}
		}
		String fileNames = sb.toString().substring(0, sb.toString().length() - 1);
		// String
		// fileNames="cardStopOrOpen_9001_20170817.REQ,productOrderOrChange_9001_20170817.REQ";

		logger.info("要读取的文件" + fileNames);
		long statdate = System.currentTimeMillis();
		Map<String, Object> readFile = FtpUtils.readFile(fileNames);
		String result = (String) readFile.get("result");
		System.out.println("时间---" + (System.currentTimeMillis() - statdate) / 1000);
		boolean resultVal = false;
		if ("success".equals(result)) {
			@SuppressWarnings("unchecked")
			List<LaoFtpfileInfo> infoList = (List<LaoFtpfileInfo>) readFile.get("infoFile");
			@SuppressWarnings("unchecked")
			List<LaoFtpfileCollect> collectList = (List<LaoFtpfileCollect>) readFile.get("collectFile");
			if (collectList != null && collectList.size() > 0) {

				for (LaoFtpfileCollect laoFtpfileCollect : collectList) {
					collectMap.insert(laoFtpfileCollect);
				}
				resultVal = true;
			}
			// 批量插入明细表
			int batchInsert = extMapper.batchInsert(infoList);
			if (batchInsert > 0) {
				resultVal = true;
			}
			String[] split = fileNames.split(",");
			for (int i = 0; i < split.length; i++) {
				InputStream input = null;
				try {
					if ("F100".equals(readFile.get(split[i]))) {
						input = new ByteArrayInputStream("F100".getBytes("utf-8"));
						String substring = split[i].substring(0, split[i].length() - 2);
						boolean uploadFile = FtpUtils.uploadFile(substring + "SP", input);
						logger.info("响应文件结果---F100" + uploadFile);
					} else if ("F002".equals(readFile.get(split[i]))) {
						// 明细与总记录数不匹配
						input = new ByteArrayInputStream("F002".getBytes("utf-8"));
						String substring = split[i].substring(0, split[i].length() - 2);
						boolean uploadFile = FtpUtils.uploadFile(substring + "SP", input);
						logger.info("响应文件结果---F002" + uploadFile);
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}

		}
		return resultVal;
	}

	@Override
	public List<Map<String, Object>> queryCardInfo(String fileID, String exportVal) {
		// 根据fileId
		LaoFtpfileCollect collectFile = collectMap.selectByPrimaryKey(Long.valueOf(fileID));
		LaoFtpfileInfo info = new LaoFtpfileInfo();
		info.setFileName(collectFile.getFileName());
		info.setTradetypecode(collectFile.getTradetypecode());
		if ("0".equals(exportVal)) {

		} else if ("1".equals(exportVal)) {
			info.setStatus(Short.valueOf(exportVal));
		} else if ("2".equals(exportVal)) {
			info.setStatus(Short.valueOf("3"));
		} else if ("3".equals(exportVal)) {
			info.setStatus(Short.valueOf("2"));
		}
		List<Map<String, Object>> list = extMapper.queryBytypeCodeAndfileName(info);

		return list;
	}

	@Override
	public Map<String, Object> queryPage(int pageNo, int pageSize, String fileName) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pageNo", pageNo);
		param.put("pageSize", pageSize);
		param.put("fileName", fileName);
		Integer countTotal = collectMapper.countTotal(param);
		List<Map<String, Object>> queryPage1 = collectMapper.queryPage(param);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", queryPage1);
		resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", countTotal);
		return resultMap;
	}

	@Override
	public Boolean updateIccid(String fileId, String selectVal) {
		// 根据fileId
		LaoFtpfileCollect collectFile = collectMap.selectByPrimaryKey(Long.valueOf(fileId));
		LaoFtpfileCollect ftpfileCollect = new LaoFtpfileCollect();
		if (selectVal.equals("1")) {
			selectVal = "3"; // 处理失败
		} else {
			selectVal = "2"; // 处理成功
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fileName", collectFile.getFileName());
		param.put("typeCode", collectFile.getTradetypecode());
		param.put("selectVal", selectVal);
		int up = extMapper.updateIccid(param);
		if (up > 0) {
			// 更新汇总表
			if ("3".equals(selectVal)) {
				selectVal = "3"; // 处理失败
				ftpfileCollect.setFailnum(up);
			} else {
				selectVal = "2"; // 处理成功
				ftpfileCollect.setSuccessnum(up);
			}
			ftpfileCollect.setPendingnum(0);
			ftpfileCollect.setUpdateDate(new Date());
			ftpfileCollect.setId(Long.valueOf(fileId));
			int updateCollect = collectMap.updateByPrimaryKeySelective(ftpfileCollect);
			if (updateCollect > 0) {
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public List<LaoFtpfileCollectDto> getFile() {
		List<LaoFtpfileCollectDto> list = null;
		List<LaoFtpfileCollect> selectAll = collectMapper.selectAll();
		if (selectAll != null && selectAll.size() > 0) {
			list = new ArrayList<LaoFtpfileCollectDto>();
			for (LaoFtpfileCollect fileCollect : selectAll) {
				LaoFtpfileCollectDto fDto = new LaoFtpfileCollectDto();
				BeanMapper.copy(fileCollect, fDto);
				list.add(fDto);
			}
		}
		return list;
	}

	@Override
	public List<LaoFtpfileCollectDto> getTypeCodeByFileId(String fileId) {
		List<LaoFtpfileCollectDto> list = null;
		List<LaoFtpfileCollect> selectAll = collectMapper.selectTypecodeByfileId(fileId);
		if (selectAll != null && selectAll.size() > 0) {
			list = new ArrayList<LaoFtpfileCollectDto>();
			for (LaoFtpfileCollect fileCollect : selectAll) {
				LaoFtpfileCollectDto fDto = new LaoFtpfileCollectDto();
				BeanMapper.copy(fileCollect, fDto);
				list.add(fDto);
			}
		}
		return list;
	}

	// 邮件通知
	@Override
	public void emailNotify() {
		MailDto mail = new MailDto("shiyf3@lenovo.com", "ofo待处理文件", "请到GLA平台查询ofo待处理文件");
		boolean send = mailUtil.send(mail);
		logger.info("邮件发送结果-----" + send);

	}

	/*
	 * ofo 数据库捞取数据，数据写入文件(non-Javadoc)
	 * 
	 * @see com.lenovo.LAOAPI.interfaces.ofo.BusinessService#ftpFileUpload()
	 */
	public void ftpFileUpload() {
		/**
		 * 查数据库捞取ofo停机用户
		 */
		 List<Map<String, Object>> listMap = laoUserExtDao.queryStopDate();
		logger.info("查数据库捞取ofo停机用户--"+listMap.toString());
		/**
		 * 把捞取出来的数据写入文件
		 */
		try {

			if (listMap != null && listMap.size() > 0) {

				String systemCode = "9001";// 系统代码
				Date date = new Date();// 获取当前时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String createTime = sdf.format(date);// 文件生成时间
				String Filename = "cardStopDetail_" + systemCode + "_" + createTime + ".REQ";

				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

				StringBuffer sum = new StringBuffer("");// 拼接成10位的数据总数
				String count = listMap.size() + "";
				for (int i = 10 - count.length(); i > 0; i--) {
					sum.append("0");
				}
				sum.append(count);
				outputStream.write((sum + "\n").getBytes("utf-8"));
				for (Map<String, Object> map : listMap) {
					outputStream.write(((String)map.get("ICCID") + "," + (String)map.get("MSTOPDATE") + "\n").getBytes("utf-8"));
				}

				byte[] bye = outputStream.toByteArray();
				System.out.println("文件创建成功！!");

				try {
					ByteArrayInputStream in = new ByteArrayInputStream(bye);
					boolean flag = FtpUtils.uploadFile("10.0.20.110", 21, "lenovobill", "EeFHY6TE5qn4YGyeBHt_","/data/file/ofo/output", Filename, in);
					if (flag) {
						logger.info("Ftp文件生成完成");
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {

					outputStream.close();// 此步之后文件被写入数据
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 批量更新
	 */
	@Override
	public Map<String, Object> batchupdate(List<Map<String, Object>> list, String fileId, String typecodeId,
			String resultId) {
		List<LaoFtpfileInfo> infoList = null;
		// 根据fileId
		LaoFtpfileCollect collectFile = collectMap.selectByPrimaryKey(Long.valueOf(fileId));

		Map<String, Object> resultMap = new HashMap<>();
		int resultInt = 0;
		int sucessNum = 0; // 成功
		int failNum = 0; // 失败
		if (null != list && list.size() > 0) {
			infoList = new ArrayList<LaoFtpfileInfo>();
			Map<String, Object> result = new HashMap<String, Object>();
			for (Map<String, Object> map : list) {
				String iccid = (String) map.get("iccid");
				LaoFtpfileInfo info = new LaoFtpfileInfo();
				// info.setFileName(collectFile.getFileName()); //需要通过Id查询汇总表名称
				// info.setTradetypecode("120");
				info.setIccid(iccid);
				// info.setStatus(Short.valueOf(resultId));
				infoList.add(info);
			}
			result.put("idList", infoList);
			result.put("status", resultId);
			result.put("fileName", collectFile.getFileName());
			result.put("type", collectFile.getTradetypecode());
			resultInt = extMapper.batchUpdate(result);
		}
		// 失败
		if ("3".equals(resultId)) {
			failNum = resultInt;
		} else {
			sucessNum = resultInt;
		}
		// 在更新汇总表
		LaoFtpfileCollect collect = new LaoFtpfileCollect();
		collect.setPendingnum(collectFile.getPendingnum() - resultInt);
		collect.setSuccessnum(collectFile.getSuccessnum() + sucessNum);
		collect.setFailnum(collectFile.getFailnum() + failNum);
		collect.setId(Long.valueOf(fileId));
		collectMap.updateByPrimaryKeySelective(collect);
		resultMap.put("msg", "成功更新" + resultInt + "条");
		return resultMap;
	}

}
