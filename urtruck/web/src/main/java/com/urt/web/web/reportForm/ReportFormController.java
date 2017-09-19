package com.urt.web.web.reportForm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.urt.dto.report.LaoReptInfoDto;
import com.urt.interfaces.report.ReportFormService;
import com.urt.web.util.JDBCHandlerByReport;
import com.urt.web.web.batchQuery.ExcelUtil;
/**
 * 报表处理
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/reportController")
public class ReportFormController {
	private static Logger logger = LoggerFactory.getLogger(JDBCHandlerByReport.class);
	@Autowired
	private ReportFormService reportFormService;
	@Autowired
	JDBCHandlerByReport jDBCHandlerByReport;
	
	
	public JDBCHandlerByReport getJdbcHandler() {
		return jDBCHandlerByReport;
	}
	public void setJdbcHandler(JDBCHandlerByReport jDBCHandlerByReport) {
		this.jDBCHandlerByReport = jDBCHandlerByReport;
	}

	// 进入 报表查询页面
	@RequestMapping( value="toReportForm", method = { RequestMethod.POST,RequestMethod.GET})	
	public ModelAndView toReportForm(HttpServletRequest request, HttpServletResponse response,HttpSession session)throws Exception{
		ModelAndView mv = new ModelAndView("/report/reportForm");
		return mv;
	}
	
	
	// 明细
	@RequestMapping( value="toDetail", method = { RequestMethod.POST,RequestMethod.GET})	
	public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response,HttpSession session)throws Exception{
		ModelAndView mv = new ModelAndView("/report/reportForm");
		return mv;
	}
	
	
	@ResponseBody
	@RequestMapping("/queryReportOne")
	public Map<String, Object> queryReportOne(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			String typeCode, String indate)	
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		//Long reptId = Long.valueOf(rept);
		int typecode = Integer.parseInt(typeCode);
		int indate2 = 0;
		if(indate!=null){
			 indate2 = Integer.parseInt(indate);
		}else{
			 indate2 = 0;
		}
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			LaoReptInfoDto dto = reportFormService.selectByType(typecode);
			if (dto != null) {
				String[] displayInfo = dto.getDisplayInfo().split(",");
				logger.info("表头---------------------------------------"+displayInfo);
				String[] keys = dto.getColKey().split(",");
				logger.info("字段-------------------------------------" + keys);
				char[] colArr = dto.getColAttr().toCharArray();
				String sqlStr = dto.getSqlInfo();
				logger.info("sql--------------------------------------" + sqlStr);
				String condCols = dto.getCondCols();
				List<Map<String, Object>> list = jDBCHandlerByReport.getResultMap(sqlStr,indate2,keys,condCols);
				map.put("displayInfo", displayInfo);
				map.put("colIds", keys);
				map.put("colArr", colArr);
				map.put("condCols", condCols);
				map.put("list", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 展示全部报表
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/showCodeAndName")
	public List<Map<String, Object>> showCodeAndName(HttpServletRequest request, HttpServletResponse response,HttpSession session)throws Exception{
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		List<LaoReptInfoDto> listDto = reportFormService.selectCodeAndName();
		Map<String, Object> map0 = new HashMap<String, Object>();
		map0.put("text", "请选择");
		map0.put("id", -1);
		listMap.add(map0);
		if (listDto != null &&listDto.size() > 0) {
			for(LaoReptInfoDto dto : listDto){
				Map<String, Object> mapi = new HashMap<String, Object>();
				mapi.put("text", dto.getReptName());
				mapi.put("id", dto.getTradeTypeCode());
				listMap.add(mapi);
			}
		}
		return listMap;
	}
	
	//导出excel文件
	@RequestMapping(value = "exportExcel")
	public String download(HttpServletRequest request, HttpServletResponse response,String typeCode, String indate) throws IOException {
		String fileName = "";
		int typecode = Integer.parseInt(typeCode);
		int indate2;
		if(indate==null){
			 indate2 = 0;
		}else{
			 indate2 = Integer.parseInt(indate);
		}
		
		LaoReptInfoDto dto = reportFormService.selectByType(typecode);
		fileName= dto.getReptName();
			String[] displayInfo = dto.getDisplayInfo().split(",");
			logger.info("要导出的表头---------------------------------------"+displayInfo);
			String[] keyValue = dto.getColKey().split(",");
			logger.info("导出对应的字段-------------------------------------" + keyValue);
			String sqlStr = dto.getSqlInfo();
			logger.info("导出sql-------------------------------------" + sqlStr);
			String condCols = dto.getCondCols();
		List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
		Map<String,Object> mapValue = new HashMap<String, Object>();
		List<Map<String, Object>> resultMap= jDBCHandlerByReport.getResultMap(sqlStr,indate2,keyValue,condCols);
		mapValue.put("sheetName", "sheet1");
		mapList.add(mapValue);
		for (Map<String, Object> map : resultMap) {
			mapList.add(map);
		}
		try {
			String[] columnNames = displayInfo;// 列名
			logger.info("列名-------------------------------------" + columnNames);
			String[] keys = keyValue;// map中的key
			logger.info("map中的key-------------------------------------" + keys);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			try {
				ExcelUtil.createWorkBook(mapList, keys, columnNames).write(os);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			// 进行转码，使其支持中文文件名
			String codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
			//System.out.println("文件名转码:"+codedFileName);
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
			return null;
		}
		return null;
	}
}
