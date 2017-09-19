package com.urt.web.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@Controller
@RequestMapping("/sale")
public class SaleReportsController {
	@Autowired
	private ReportFormService reportFormService;
	//跳转销售报表页面
	@RequestMapping("/saleIndex")
	public ModelAndView intoChannelIndex() {
		ModelAndView mv = new ModelAndView("/reportsIndex/saleReports");
		return mv;
	}
	//根据type查询报表
	@ResponseBody
	@RequestMapping("/saleReport")
	public List<Map<String, Object>> showCodeAndName(HttpServletRequest request, HttpServletResponse response,HttpSession session)throws Exception{
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		int typeCode = 5;
		LaoReptInfoDto dto = reportFormService.selectByType(typeCode);
		Map<String, Object> map0 = new HashMap<String, Object>();	
		map0.put("text", dto.getReptName());
		map0.put("id",dto.getTradeTypeCode());
		listMap.add(map0);
		
		return listMap;
	}
}
