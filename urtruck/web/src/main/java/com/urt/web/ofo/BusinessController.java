package com.urt.web.ofo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.urt.common.util.ResultMsg;
import com.urt.dto.LaoFtpfileCollectDto;
import com.urt.interfaces.ofo.BusinessService;
import com.urt.web.common.util.BatchOperationUtils;
import com.urt.web.web.batchQuery.ExcelUtil;

/**
 * 业务处理controller
 * 
 * @author syf
 *
 */
@Controller
@RequestMapping("/business")
public class BusinessController {

	@Autowired
	private BusinessService service;

	@RequestMapping(value = "/list")
	public String ruleList() {
		return "ofo/business";
	}

	private static final Logger log = Logger.getLogger(DownloadFileController.class);

	@RequestMapping(value = "exportFile", method = { RequestMethod.POST, RequestMethod.GET })
	public String exportExc(HttpServletRequest reqest, HttpServletResponse response) {
		String userAgent = reqest.getHeader("USER-AGENT");
		//String tradetypecode = reqest.getParameter("typeCode");
		String fileID = reqest.getParameter("fileID");
		String exportVal = reqest.getParameter("exportVal");
		String exportfileName = "文件详情";
		List<Map<String, Object>> cardInfo = service.queryCardInfo(fileID, exportVal);

		String[] keys = { "FILE_NAME", "ICCID", "TRADETYPECODE", "GOODSRELEASEID" };
		String[] columnNames = { "文件名", "ICCID", "业务类型编码", "产品ID" };
		List<Map<String, Object>> list = createExcelRecord(cardInfo);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ExcelUtil.createWorkBook(list, keys, columnNames).write(os);

			// 设置response参数，可以打开下载页面
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			String codedFileName = null;
			// 进行转码，使其支持中文文件名
			if (StringUtils.contains(userAgent, "Mozilla")) {// google,火狐浏览器
				codedFileName = new String(exportfileName.getBytes(), "ISO8859-1");
			} else {
				codedFileName = java.net.URLEncoder.encode(exportfileName, "UTF-8");
			}
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((codedFileName + ".xls").getBytes(), "utf-8"));
			ServletOutputStream out = response.getOutputStream();

			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
				bis = new BufferedInputStream(is);
				bos = new BufferedOutputStream(out);
				byte[] buff = new byte[2048];
				int bytesRead;
				// Simple read/write loop.
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (bos != null)
					bos.close();
				if (bis != null)
					bis.close();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/listFile")
	@ResponseBody
	public Map<String, Object> pageList(HttpServletRequest reqest, HttpServletResponse response, String fileName) {
		int pageStart = Integer.parseInt(reqest.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(reqest.getParameter("iDisplayLength").toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;

		Map<String, Object> pageData = service.queryPage(pageNo, pageSize, fileName);

		return pageData;

	}

	@RequestMapping(value = "update", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResultMsg updateFileTypeCode(HttpServletRequest reqest, HttpServletResponse response) {
		ResultMsg resultMsg = new ResultMsg();

		String fileId = reqest.getParameter("fileId");
		String selectVal = reqest.getParameter("selectVal");

		Boolean result = service.updateIccid(fileId, selectVal);
		resultMsg.setSuccess(result);
		return resultMsg;
	}

	@ResponseBody
	@RequestMapping("/getFileList")
	public List<Map<String, Object>> getFileList(HttpServletResponse response) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> initMap = new HashMap<String, Object>();
		list.add(initMap);
		List<LaoFtpfileCollectDto> dtolist = service.getFile();
		Map<String, Object> selectMap = null;
		if (dtolist != null && dtolist.size() > 0) {
			for (LaoFtpfileCollectDto fileCollectDto : dtolist) {
				selectMap = new HashMap<String, Object>();
				selectMap.put("text", fileCollectDto.getFileName());
				selectMap.put("id", fileCollectDto.getId());
				list.add(selectMap);
			}
		}
		return list;
	}

	@ResponseBody
	@RequestMapping("/getTypeCodeByFileId")
	public List<Map<String, Object>> getTypeCodeByFileId(HttpServletResponse response, String fileId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<LaoFtpfileCollectDto> dtolist = service.getTypeCodeByFileId(fileId);
		Map<String, Object> selectMap = null;
		if (dtolist != null && dtolist.size() > 0) {
			for (LaoFtpfileCollectDto fileCollectDto : dtolist) {
				selectMap = new HashMap<String, Object>();
				selectMap.put("text", fileCollectDto.getTradetypecode());
				selectMap.put("id", fileCollectDto.getId());
				list.add(selectMap);
			}
		}
		return list;
	}

	private List<Map<String, Object>> createExcelRecord(List<Map<String, Object>> cardInfos) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", "sheet1");
		listmap.add(map);
		for (Map<String, Object> mapParam : cardInfos) {

			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("FILE_NAME", mapParam.get("FILE_NAME"));
			hashMap.put("ICCID", mapParam.get("ICCID"));
			hashMap.put("TRADETYPECODE", mapParam.get("TRADETYPECODE"));
			hashMap.put("GOODSRELEASEID", mapParam.get("GOODSRELEASEID"));
			listmap.add(hashMap);
		}
		return listmap;
	}

	// 批量导入更新
	@ResponseBody
	@RequestMapping(value = "batchUpdate", method = { RequestMethod.POST, RequestMethod.GET })
	public Map<String, Object> batchUpdate(@RequestParam(value = "file") MultipartFile file,
			HttpServletRequest reqest, String fileId, String typecodeId, String resultId) {
		BatchOperationUtils utils = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = null;
		// 判断上传文件，如果不为空，将之转换成对象
		if (!file.isEmpty()) {
			try {
				utils = new BatchOperationUtils();
				if (file.getOriginalFilename().endsWith("xlsx")) {
					list = utils.importExcel(file.getInputStream(), true);
				} else {
					list = utils.importExcel(file.getInputStream(), false);
				}

				if (list != null && list.size() > 0) {

					resultMap = service.batchupdate(list, fileId, typecodeId, resultId);
				} else {
					resultMap.put("msg", "读取excel 内容为空");
					log.error("读取excel 内容为空************************************");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return resultMap;

	}

}
