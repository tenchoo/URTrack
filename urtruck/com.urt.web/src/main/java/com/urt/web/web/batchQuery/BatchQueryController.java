package com.urt.web.web.batchQuery;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.urt.dto.LaoBatchDataDto;
import com.urt.dto.LaoBatchDatadetailDto;
import com.urt.interfaces.batch.BatchService;

@Controller
@RequestMapping("/batchQuery")
public class BatchQueryController {
	
	@Autowired
	private BatchService batchService;
	
	@RequestMapping("/batchQueryResult")
	public String batchQueryResult(HttpServletRequest req)throws Exception{
		
		return "batchQuery/batchQueryResult";
	}
	
	@RequestMapping(value = "TestOuter", method = { RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody JSONObject TestOuter(
			HttpServletRequest request,
			HttpServletResponse response
			){
		JSONObject respObj=new JSONObject();
		respObj.put("resultCode", "0000");
		respObj.put("resultMsg", "处理成功");
		return respObj;
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
			batchService.updateByBatchId(dataDto);
		}catch(Exception e){
			result="fail";
		}
		mv.put("data", result);
		return mv;
	}
	@ResponseBody
	@RequestMapping("/queryFailData")
	public ModelAndView  queryFailData(HttpServletRequest req,HttpServletResponse resp)throws Exception{
		ModelAndView mv = new ModelAndView("batchQuery/batchFailResult");
		String batchId=req.getParameter("batchId");
		mv.addObject("batchId", batchId);
        return mv;
	}
	/**
	 * 批量执行失败的数据lao_batch_datadetail
	 * @param req
	 * @param resp
	 * @param datadetailDto
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/queryFailDataList")
	public Map<String, Object> queryFailDataList(HttpServletRequest req,HttpServletResponse resp,LaoBatchDatadetailDto datadetailDto)throws Exception{
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());//开始显示的项
        int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());//显示多少项
        int pageNo = (pageStart / pageSize)+1;//第几页
        String sEcho = req.getParameter("sEcho");
        Map<String, Object> resultMap= batchService.queryFailDateList(datadetailDto,pageNo,pageSize);
        resultMap.put("sEcho", sEcho);
		return resultMap;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="exportExcel")
    public String download(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String batchId=request.getParameter("batchId");
        String fileName=batchId+"_"+System.currentTimeMillis();
        Map<String, Object> resultMap= batchService.queryFailDateList(new Long(batchId));
        List<LaoBatchDatadetailDto> laoBatchDatadetailDtoList=(List<LaoBatchDatadetailDto>) resultMap.get("data");
        List<Map<String,Object>> list=createExcelRecord(laoBatchDatadetailDtoList);
        String columnNames[]={"批次流水号","业务类型","服务号码","iccid","操作时间","处理状态","结果信息","操作人员"};//列名
        String keys[]    =     {"batchId","tradeTypeCode","msisdn","iccid","recvTime","dealTag","resultInfo","operId"};//map中的key
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBook(list,keys,columnNames).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        // 进行转码，使其支持中文文件名
        String codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");

        response.setHeader("Content-Disposition", "attachment;filename="+ new String((codedFileName + ".xls").getBytes(), "utf-8"));
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
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return null;
    }
    private List<Map<String, Object>> createExcelRecord(List<LaoBatchDatadetailDto> laoBatchDatadetailDtoList) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH/mm/ss"); 
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        LaoBatchDatadetailDto dto=null;
        for (int j = 0; j < laoBatchDatadetailDtoList.size(); j++) {
            dto=laoBatchDatadetailDtoList.get(j);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            
            Date revDate = dto.getRecvTime();
            if(revDate!=null){
            	mapValue.put("recvTime", sdf.format(revDate));
            }
            mapValue.put("batchId", dto.getBatchId());
            mapValue.put("tradeTypeCode", dto.getTradeTypeCode());
            mapValue.put("msisdn", dto.getMsisdn());
            mapValue.put("iccid", dto.getIccid());
            mapValue.put("dealTag", dto.getDealTag());
            mapValue.put("resultInfo", dto.getResultInfo());
            mapValue.put("operId", dto.getOperId());
            listmap.add(mapValue);
        }
        return listmap;
    }
	@RequestMapping("/sendSM")
	public ModelAndView  sendSM(HttpServletRequest req,HttpServletResponse resp)throws Exception{
		ModelAndView mv = new ModelAndView("batchQuery/sendSM");
        return mv;
	}
	@ResponseBody
	@RequestMapping("/toSendSM")
	public Map<String,Object>  toSendSM(HttpServletRequest req,HttpServletResponse resp)throws Exception{
		Map<String,Object> map=new HashMap<String, Object>();
		String iccid = req.getParameter("iccid");
		String context = req.getParameter("context");
		map.put("succ", "succ");
        return map;
	}
}
