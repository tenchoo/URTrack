package com.urt.web.reports;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.urt.dto.report.LaoReptInfoDto;
import com.urt.interfaces.report.ReportFormService;
import com.urt.interfaces.reports.FinanciaReportsService;
import com.urt.web.web.batchQuery.ExcelUtil;

@Controller
@RequestMapping("/collectionReports")
public class CollectionReportsController {
	@Autowired
	private ReportFormService reportFormService;
	//跳转计收报表页面
	@RequestMapping("/collectionIndex")
	public ModelAndView intoChannelIndex() {
		ModelAndView mv = new ModelAndView("/reportsIndex/collectionlReports");
		return mv;
	}
	//根据type查询报表
	@ResponseBody
	@RequestMapping("/collectReport")
	public List<Map<String, Object>> showCodeAndName(HttpServletRequest request, HttpServletResponse response,HttpSession session)throws Exception{
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		int typeCode = 2;
		LaoReptInfoDto dto = reportFormService.selectByType(typeCode);
		Map<String, Object> map0 = new HashMap<String, Object>();
		map0.put("text", dto.getReptName());
		map0.put("id",dto.getTradeTypeCode());
		listMap.add(map0);
		
		return listMap;
	}
}
