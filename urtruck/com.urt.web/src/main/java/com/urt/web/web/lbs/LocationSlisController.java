package com.urt.web.web.lbs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.urt.dto.LaoBatchDataDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.interfaces.batch.BatchService;
import com.urt.interfaces.lbs.LocationSlisService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
import com.urt.web.common.util.FtpsFileList;
import com.urt.web.web.batchQuery.ExcelUtil;

@Controller
@RequestMapping("/lbs")
public class LocationSlisController {

	private static final Logger log = Logger.getLogger(LocationSlisController.class);
	@Autowired
	private LocationSlisService locationSlisService;
	@Autowired
	private BatchService batchService;
	
	@RequestMapping(value = "/toLocationView")
	public ModelAndView toQueryView() {
		ModelAndView mv = new ModelAndView("/lbs/locationSlis");
		return mv;
	}
	
	/*
	 * 单卡定位查询
	 */
	@ResponseBody
	@RequestMapping("/queryOne")
	public String queryOne(HttpServletResponse response, String iccid) {
		String addrStr = locationSlisService.queryLocationjedisCluster(iccid);
		log.info("=======================LocationSlisController,queryOne,queryLocationjedisCluster;addrStr="+addrStr);
		if (StringUtils.isBlank(addrStr)) {
			addrStr = "otherError";
		}
		return addrStr;
	}
	
	//单点定位展示地图
	@RequestMapping("/showOne")
	public ModelAndView showOne(String addrStr) {
		ModelAndView mv = new ModelAndView("/lbs/showOneLocation");
		mv.addObject("addrStr", addrStr);
		return mv;
	}

	
	//批量卡定位展示地图
	@RequestMapping("/showBatch")
	public ModelAndView showBatch(String batchId) {
		ModelAndView mv = new ModelAndView("/lbs/showBatchLocation");
		mv.addObject("batchId", batchId);
		return mv;
	}

	/*
	 * 批量卡定位查询
	 */
	@ResponseBody
	@RequestMapping("/getBatchMap")
	public List<String> getBatchMap(HttpServletResponse response, Long batchId) {
		List<String> list = new ArrayList<String>();
		List<Map<String, Object>> listMap = batchService.queryLbsList(batchId);
		log.info("=======================LocationSlisController,getBatchMap,queryLbsList;listMap="+listMap);
		if (listMap != null ) {
			for (int i = 0; i < listMap.size(); i++) {
				Map<String, Object> map = listMap.get(i);
				list.add((String) map.get("addrStr"));
			}
		}
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="/queryResultList")
	public Map<String, Object> queryResultList(HttpServletRequest req,HttpServletResponse resp,LaoBatchDataDto laoBatchDataDto)throws Exception{
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); 
		 int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());//开始显示的项
		 int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());//显示多少项
		 int pageNo = (pageStart / pageSize)+1;
		 String sEcho = req.getParameter("sEcho");
		 String startTime = req.getParameter("startTime");
		 String endTime = req.getParameter("endTime");
		 Date startDate=null;
		 Date endDate=null;
		 if(startTime==null||"".equals(startTime)||endTime==null||"".equals(endTime)){
			 startTime=sdf.format(new Date());
			 endTime=startTime;
		 }
		 startDate=sdf.parse(startTime);
		 if(startTime.equals(endTime)){
			 long endMill = startDate.getTime()+24*3600*1000;
			 endDate=new Date(endMill-1000);
		 }else{
			 endDate=sdf.parse(endTime);
		 }
		 laoBatchDataDto.setTradeTypeCode((short)1135);
		 batchService.updateBatchDate(startDate, endDate,laoBatchDataDto);
		 Map<String, Object> resultMap = batchService.queryPage(pageNo,pageSize,startDate,endDate,laoBatchDataDto);
		 resultMap.put("sEcho", sEcho);
		 return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/updateByBatchId")
	public Map<String,String> updateByBatchId(HttpServletRequest req,HttpServletResponse resp,LaoBatchDataDto dataDto)throws Exception{
		Map<String,String> mv = new HashMap<>();
		String result="succ";
		try{
			dataDto.setTradeTypeCode((short)1135);
			batchService.updateByBatchId(dataDto);
		}catch(Exception e){
			result="fail";
		}
		mv.put("data", result);
		return mv;
	}
	
	/**
	 * 批量卡导入(excel)
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/batchImport")
	public Map<String, Object> upload(
			@RequestParam(value = "file") MultipartFile file,
			HttpServletRequest request) throws IOException {
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long batchId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID));
		try {
			if (!file.isEmpty()) {
				String fileName = file.getOriginalFilename();
				if (file.getOriginalFilename().endsWith("xlsx")) {
					InputStream is = file.getInputStream();
					Map<String, Object> map0 = FtpsFileList.readFile(is,fileName);
					List<Map<String, String>> listM = (List<Map<String, String>>) map0.get("listMap");
					for (int i = 0; i < listM.size(); i++) {
						Map<String, Object> map = new HashMap<String, Object>();
						String iccid = listM.get(i).get("0");
						map.put("batchId", batchId);
						map.put("iccid", iccid);
						listMap.add(map);
					}
				}
			}
			// 记录批量任务总表
			// 同一批数据，批次号一样
			LaoSsAccountDto user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
			String accountId = "";
			if(user != null) {
				accountId = user.getAcconutId().toString();
			}
			// 记录批量导入汇总表
			this.saveBatchData(listMap.size(), batchId,(short)1135,accountId);
			log.info("=======================LocationSlisController,upload,saveBatchData;batchId="+batchId);
			// 发送kafka消息
			locationSlisService.sendLocationjedis(listMap);
			resultMap.put("msg", "导入成功,请查看处理进度");
		} catch (Exception e) {
			log.info("===============================导入失败！");
			resultMap.put("msg", "导入失败！");
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * 存入批量汇总表
	 * 
	 * @param count
	 * @param length
	 * @param batchId
	 */
	private void saveBatchData(int count, Long batchId,Short tradeTypeCode,String accountId) {
		LaoBatchDataDto dto = new LaoBatchDataDto();
		dto.setBatchId(batchId);// 批次号
		dto.setDealTag("1");// 处理状态:0-未处理，1-处理中，2-处理完成
		dto.setFailNum(0L);// 失败数量
		dto.setOperId(accountId);// 操作人员
		dto.setRecvTime(new Date());// 初始入表时间
		dto.setRemark("");// 备注
		dto.setResultInfo("");// 处理结果信息
		dto.setRsrvInfo1("");// 保留字段1 :暂填运行商ID
		dto.setRsrvInfo2("");// 保留字段2
		dto.setSuccNum(0L);// 成功数量
		dto.setSumNum((long) count);// 总记录数
		dto.setTradeTypeCode(tradeTypeCode);// 业务类型编码
		dto.setUpdateTime(new Date());// 更新时间
		batchService.saveBatchData(dto);
	}
	
	
	
	// 导出明细
	@RequestMapping("/doExport")
	public void doExport(HttpServletResponse response, HttpServletRequest request, Long batchId) {
		if (batchId != null) {
			log.info("开始查询导出lbs数据=========================batchId="+batchId);
			List<Map<String, Object>> listMap = batchService.queryLbsList(batchId);
			try {
				Map<String, Object> map0 = new HashMap<String, Object>();
				map0.put("sheetName", "sheet1");
				listMap.add(0, map0);
				String[] columnNames = { "ICCID", "查询时间","地址名称", "地址经纬度(lon,lat)" };// 列名
				String[] keys = { "iccid", "queryTime", "addrName", "addrStr" };// map中的key
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				log.info("开始导出lbs数据=========================");
				try {
					ExcelUtil.createWorkBook(listMap, keys, columnNames).write(os);
				} catch (IOException e) {
					e.printStackTrace();
					return ;
				}
				byte[] content = os.toByteArray();
				InputStream is = new ByteArrayInputStream(content);
				// 设置response参数，可以打开下载页面
				response.reset();
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				// 进行转码，使其支持中文文件名
				String fileName = "lbs定位明细";
				String codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
				// System.out.println("文件名转码:"+codedFileName);
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
				} catch (final IOException e) {
					throw e;
				} finally {
					if (bos != null)
						bos.close();
					if (bis != null)
						bis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ;
			}
		}
	}
}
