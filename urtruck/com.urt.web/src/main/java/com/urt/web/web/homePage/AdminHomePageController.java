package com.urt.web.web.homePage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.urt.interfaces.homePage.AdminHomePageService;
import com.urt.web.web.batchQuery.ExcelUtil;

/**
 * 管理员首页
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/homePage")
public class AdminHomePageController {
	
	private static final Logger log = Logger.getLogger(AdminHomePageController.class);

	@Autowired
	private AdminHomePageService adminHomePageService;
	
	@RequestMapping(value = "/toHomePageView")
	public ModelAndView toHomePageView() {
		ModelAndView mv = new ModelAndView("/homePage/adminHomePage");
		List<Map<String,Object>> useList = null;
		List<Map<String,Object>> avgUseList = null;
		List<Map<String,Object>> consumeCustMaxList = null;
		List<Map<String,Object>> consumeCustMaxAvgList = null;
		List<Map<String,Object>> consumeSingleMaxList = null;
		List<Map<String,Object>> consumeSingleMinList = null;
		try {
			useList = adminHomePageService.selectTop5ByUseFlow();
			if (useList != null && useList.size() > 0) {
				mv.addObject("useList", useList);
			}
			avgUseList =adminHomePageService.selectTop5ByAverageUseFlow();
			if (avgUseList != null && avgUseList.size() > 0) {
				mv.addObject("avgUseList", avgUseList);
			}
			consumeCustMaxList = adminHomePageService.selectTop5ByCustConsume();
			if (consumeCustMaxList != null && consumeCustMaxList.size() > 0) {
				mv.addObject("consumeCustMaxList", consumeCustMaxList);
			}
			consumeCustMaxAvgList = adminHomePageService.selectTop5ByAverageConsume();
			if (consumeCustMaxAvgList != null && consumeCustMaxAvgList.size() > 0) {
				mv.addObject("consumeCustMaxAvgList", consumeCustMaxAvgList);
			}
			consumeSingleMaxList = adminHomePageService.selectTop5ByPersonal();
			if (consumeSingleMaxList != null && consumeSingleMaxList.size() > 0) {
				mv.addObject("consumeSingleMaxList", consumeSingleMaxList);
			}
			consumeSingleMinList = adminHomePageService.selectLast5ByPersonal();
			if (consumeSingleMinList != null && consumeSingleMinList.size() > 0) {
				mv.addObject("consumeSingleMinList", consumeSingleMinList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return mv;
		}
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/doAjaxHomePageByState")
	public Map<String,Object> doAjaxHomePageByState(HttpServletRequest req)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String, Object>> list = adminHomePageService.selectCountByState();
		if (list != null && list.size() > 0) {
			int count = 0;
			String[] data1 = new String[list.size()+1];
			@SuppressWarnings("unchecked")
			Map<String,Object>[] data2 = new Map[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> mapi = list.get(i);
				BigDecimal big = (BigDecimal) mapi.get("SUMCOUNT");
				int sumi = big.intValue();
				String staticName = mapi.get("STATIC_NAME").toString();
				count = count + sumi;
				data1[i] = staticName;
				Map<String,Object> mapData2 = new HashMap<String,Object>();
				mapData2.put("value", sumi);
				mapData2.put("name", staticName);
				data2[i] = mapData2;
			}
			data1[list.size()] = "";//"系统总用户数："+count+"";
			DecimalFormat decimalFormat = new DecimalFormat(",###");// 格式化设置
			String countStr = decimalFormat.format(count);//299,792,458 
			map.put("countStr", countStr);
			map.put("data1", data1);
			map.put("data2", data2);
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/doAjaxHomePageByOperators")
	public Map<String,Object> doAjaxHomePageByOperators(HttpServletRequest req)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String, Object>> list = adminHomePageService.selectCountByOperators();
		if (list != null && list.size() > 0) {
			String[] data1 = new String[list.size()+1];
			@SuppressWarnings("unchecked")
			Map<String,Object>[] data2 = new Map[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> mapi = list.get(i);
				BigDecimal big = (BigDecimal) mapi.get("SUMCOUNT");
				int sumi = big.intValue();
				String operatorsName = mapi.get("OPERATORS_NAME").toString();
				data1[i] = operatorsName;
				Map<String,Object> mapData2 = new HashMap<String,Object>();
				mapData2.put("value", sumi);
				mapData2.put("name", operatorsName);
				data2[i] = mapData2;
			}
			data1[list.size()] = "";//"接入运营商："+list.size()+"家";
			map.put("data1", data1);
			map.put("data2", data2);
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/doAjaxHomePageByTop10Cust")
	public Map<String,Object> doAjaxHomePageByTop10Cust(HttpServletRequest req)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String, Object>> list = adminHomePageService.selectTop10ByCust();
		if (list != null && list.size() > 0) {
			String[] xStr = new String[list.size()];
			int[] yStr = new int[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> mapi = list.get(i);
				BigDecimal big = (BigDecimal) mapi.get("SUMCOUNT");
				int sumi = big.intValue();
				String custName = mapi.get("CUST_NAME").toString();
				xStr[i] = custName;
				yStr[i] = sumi;
			}
			map.put("xStr", xStr);
			map.put("yStr", yStr);
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/doAjaxHomePageByUserCountYear")
	public Map<String,Object> doAjaxHomePageByUserCountYear(HttpServletRequest req)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String, Object>> list = adminHomePageService.selectUserCountByYear();
		if (list != null && list.size() > 0) {
			String[] xStr = new String[list.size()];
			int[] yStr = new int[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> mapi = list.get(i);
				BigDecimal big = (BigDecimal) mapi.get("SUMCOUNT");
				int sumi = big.intValue();
				String custName = mapi.get("CYCDATE").toString();
				xStr[i] = custName.substring(0,4)+"/"+custName.substring(4,6);
				yStr[i] = sumi;
			}
			map.put("xStr", xStr);
			map.put("yStr", yStr);
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/doAjaxHomePageByTrade")
	public Map<String,Object> doAjaxHomePageByTrade(HttpServletRequest req)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String, Object>> list = adminHomePageService.selectCountByTrade();
		if (list != null && list.size() > 0) {
			int count = 0;
			String[] data1 = new String[list.size()+1];
			@SuppressWarnings("unchecked")
			Map<String,Object>[] data2 = new Map[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> mapi = list.get(i);
				BigDecimal big = (BigDecimal) mapi.get("SUMCOUNT");
				int sumi = big.intValue();
				String operatorsName = mapi.get("TRADE_TYPE_NAME").toString();
				data1[i] = operatorsName;
				Map<String,Object> mapData2 = new HashMap<String,Object>();
				count = count + sumi;
				mapData2.put("value", sumi);
				mapData2.put("name", operatorsName);
				data2[i] = mapData2;
			}
			data1[list.size()] = "业务总量："+count+"笔";
			map.put("data1", data1);
			map.put("data2", data2);
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/doAjaxHomePageByFlowCountMonth")
	public Map<String,Object> doAjaxHomePageByFlowCountMonth(HttpServletRequest req)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String, Object>> list = adminHomePageService.selectFlowCountByMonth();
		if (list != null && list.size() > 0) {
			String[] xStr = new String[list.size()];
			double[] yStr = new double[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> mapi = list.get(i);
				BigDecimal big = (BigDecimal) mapi.get("SUMCOUNT");
				double sumi = big.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				String custName = mapi.get("DATA_CYCLE").toString();
				xStr[i] = custName.substring(6,8);
				yStr[i] = sumi;
			}
			map.put("xStr", xStr);
			map.put("yStr", yStr);
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/doAjaxHomePageByComePayment")
	public Map<String,Object> doAjaxHomePageByComePayment(HttpServletRequest req)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String, Object>> list = adminHomePageService.selectComeByPayment();
		if (list != null && list.size() > 0) {
			String[] data1 = new String[list.size()];
			@SuppressWarnings("unchecked")
			Map<String,Object>[] data2 = new Map[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> mapi = list.get(i);
				BigDecimal big = (BigDecimal) mapi.get("SUMCOUNT");
				int sumi = big.intValue();
				String operatorsName = mapi.get("STATIC_NAME").toString();
				data1[i] = operatorsName;
				Map<String,Object> mapData2 = new HashMap<String,Object>();
				mapData2.put("value", sumi);
				mapData2.put("name", operatorsName);
				data2[i] = mapData2;
			}
			map.put("data1", data1);
			map.put("data2", data2);
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/doAjaxHomePageByConsume6Month")
	public Map<String,Object> doAjaxHomePageByConsume6Month(HttpServletRequest req)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String, Object>> list = adminHomePageService.selectConsumeBy6Month();
		if (list != null && list.size() > 0) {
			String[] xStr = new String[list.size()];
			double[] yStr = new double[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> mapi = list.get(i);
				BigDecimal big = (BigDecimal) mapi.get("SUMCOUNT");
				double sumi = big.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				String custName = mapi.get("CYCDATE").toString();
				xStr[i] = custName.substring(0,4)+"/"+custName.substring(4,6);
				yStr[i] = sumi;
			}
			map.put("xStr", xStr);
			map.put("yStr", yStr);
		}
		return map;
	}
	
	// 进入管理员报表页面
	@RequestMapping(value = "/toAdminReport")
	public ModelAndView toAdminReport() {
		log.info("进入管理员报表页面=========================");
		ModelAndView mv = new ModelAndView("/homePage/adminReport");
		return mv;
	}
	
	// 查询报表数据
	@ResponseBody
	@RequestMapping("/doAjaxAdminReport")
	public List<Map<String, Object>> doAjaxAdminReport(HttpServletResponse response, HttpServletRequest request, String date) {
		if (StringUtils.isBlank(date)) {
			return null;
		}
		log.info("进入查询报表数据=========================date="+date);
		List<Map<String, Object>> list = adminHomePageService.queryUserIncomeByIntelligent(date);
		Map<String, Object> mapZnhl = adminHomePageService.queryZhiNengHuLian(date);
		log.info("查询报表数据=========================list:"+list);
		log.info("查询报表数据=========================mapZnhl:"+mapZnhl);
		if (list == null || list.size() == 0) {
			log.info("查询报表数据=========================mapZnhl:"+mapZnhl);
			return null;
		} else {
			DecimalFormat decimalFormat = new DecimalFormat("#0.00");//格式化设置
			// 合计TO B 
			int sumA = 0; double sumB = 0; 
			int sumC = 0; double sumD = 0; int sumE = 0;
			for (Map<String, Object> map : list) {
				if ("其他".equals(map.get("STATICNAME"))) {
					continue;
				}
				int suma = 0;
				double sumb = 0;
				int sumc = 0;
				double sumd = 0;
				int sume = 0;
				Object obja = (Object) map.get("SUMUSERA");
				Object objb = (Object) map.get("SUMINCOMEB");
				Object objc = (Object) map.get("SUMUSERC");
				Object objd = (Object) map.get("SUMINCOMED");
				Object obje = (Object) map.get("SUMUSERE");
				if (obja != null) {
					String stra = obja.toString();
					suma = Integer.parseInt(stra);
				}
				if (objb != null) {
					String strb = objb.toString();
					sumb = Double.parseDouble(strb);
				}
				if (objc != null) {
					String strc = objc.toString();
					sumc = Integer.parseInt(strc);
				}
				if (objd != null) {
					String strd = objd.toString();
					sumd = Double.parseDouble(strd);
				}
				if (obje != null) {
					String stre = obje.toString();
					sume = Integer.parseInt(stre);
				}
				sumA = sumA +suma;
				sumB = sumB +sumb;
				sumC = sumC +sumc;
				sumD = sumD +sumd;
				sumE = sumE +sume;
			}
			Map<String, Object> mapSum = new HashMap<String, Object>();
			mapSum.put("STATICNAME", "合计");
			mapSum.put("SUMUSERA",sumA);
			mapSum.put("SUMINCOMEB",decimalFormat.format(sumB));
			mapSum.put("SUMUSERC",sumC);
			mapSum.put("SUMINCOMED",decimalFormat.format(sumD));
			mapSum.put("SUMUSERE",sumE);
			list.add(mapSum);
			if (mapZnhl != null && mapZnhl.size() > 0) {
				mapZnhl.put("STATICNAME", "智能互联TOC");
				list.add(mapZnhl);
			}
			// 总计TO B 和 TO C
			int sumCountA = 0; double sumCountB = 0; 
			int sumCountC = 0; double sumCountD = 0; int sumCountE = 0;
			int suma = 0; double sumb = 0;
			int sumc = 0; double sumd = 0; int sume = 0;
			if (mapZnhl != null && mapZnhl.size() > 0) {
				Object obja = (Object) mapZnhl.get("SUMUSERA");
				Object objb = (Object) mapZnhl.get("SUMINCOMEB");
				Object objc = (Object) mapZnhl.get("SUMUSERC");
				Object objd = (Object) mapZnhl.get("SUMINCOMED");
				Object obje = (Object) mapZnhl.get("SUMUSERE");
				if (obja != null) {
					String stra = obja.toString();
					suma = Integer.parseInt(stra);
				}
				if (objb != null) {
					String strb = objb.toString();
					sumb = Double.parseDouble(strb);
				}
				if (objc != null) {
					String strc = objc.toString();
					sumc = Integer.parseInt(strc);
				}
				if (objd != null) {
					String strd = objd.toString();
					sumd = Double.parseDouble(strd);
				}
				if (obje != null) {
					String stre = obje.toString();
					sume = Integer.parseInt(stre);
				}
			}
			sumCountA = sumA +suma;
			sumCountB = sumB +sumb;
			sumCountC = sumC +sumc;
			sumCountD = sumD +sumd;
			sumCountE = sumE +sume;
			Map<String, Object> mapSumCount = new HashMap<String, Object>();
			mapSumCount.put("STATICNAME", "总计");
			mapSumCount.put("SUMUSERA",sumCountA);
			mapSumCount.put("SUMINCOMEB",decimalFormat.format(sumCountB));
			mapSumCount.put("SUMUSERC",sumCountC);
			mapSumCount.put("SUMINCOMED",decimalFormat.format(sumCountD));
			mapSumCount.put("SUMUSERE",sumCountE);
			list.add(mapSumCount);
		}
		log.info("返回页面报表数据=========================list:"+list);
		return list;
	}
	
	// 导出报表
	@RequestMapping("/doExportAdminReport")
	public void doExportAdminReport(HttpServletResponse response, HttpServletRequest request, String date) {
		if (StringUtils.isBlank(date)) {
			return ;
		}
		try {
			log.info("开始查询导出报表数据=========================date="+date);
			List<Map<String, Object>> list = adminHomePageService.queryUserIncomeByIntelligent(date);
			Map<String, Object> mapZnhl = adminHomePageService.queryZhiNengHuLian(date);
			if (list == null || list.size() == 0) {
				return ;
			} else {
				DecimalFormat decimalFormat = new DecimalFormat("#0.00");//格式化设置
				// 合计TO B 
				int sumA = 0; double sumB = 0; 
				int sumC = 0; double sumD = 0; int sumE = 0;
				for (int j = 0; j < list.size(); j++) {
					Map<String, Object> map = list.get(j);
					if ("其他".equals(map.get("STATICNAME"))) {
						list.remove(map);
						continue;
					}
					int suma = 0;
					double sumb = 0;
					int sumc = 0;
					double sumd = 0;
					int sume = 0;
					Object obja = (Object) map.get("SUMUSERA");
					Object objb = (Object) map.get("SUMINCOMEB");
					Object objc = (Object) map.get("SUMUSERC");
					Object objd = (Object) map.get("SUMINCOMED");
					Object obje = (Object) map.get("SUMUSERE");
					if (obja != null) {
						String stra = obja.toString();
						suma = Integer.parseInt(stra);
					}
					if (objb != null) {
						String strb = objb.toString();
						sumb = Double.parseDouble(strb);
					}
					if (objc != null) {
						String strc = objc.toString();
						sumc = Integer.parseInt(strc);
					}
					if (objd != null) {
						String strd = objd.toString();
						sumd = Double.parseDouble(strd);
					}
					if (obje != null) {
						String stre = obje.toString();
						sume = Integer.parseInt(stre);
					}
					sumA = sumA +suma;
					sumB = sumB +sumb;
					sumC = sumC +sumc;
					sumD = sumD +sumd;
					sumE = sumE +sume;
				}
				Map<String, Object> mapSum = new HashMap<String, Object>();
				mapSum.put("STATICNAME", "合计");
				mapSum.put("SUMUSERA",sumA);
				mapSum.put("SUMINCOMEB",decimalFormat.format(sumB));
				mapSum.put("SUMUSERC",sumC);
				mapSum.put("SUMINCOMED",decimalFormat.format(sumD));
				mapSum.put("SUMUSERE",sumE);
				list.add(mapSum);
				if (mapZnhl != null && mapZnhl.size() > 0) {
					mapZnhl.put("STATICNAME", "智能互联TOC");
					list.add(mapZnhl);
				}
				// 总计TO B 和 TO C
				int sumCountA = 0; double sumCountB = 0; 
				int sumCountC = 0; double sumCountD = 0; int sumCountE = 0;
				int suma = 0; double sumb = 0;
				int sumc = 0; double sumd = 0; int sume = 0;
				if (mapZnhl != null && mapZnhl.size() > 0) {
					Object obja = (Object) mapZnhl.get("SUMUSERA");
					Object objb = (Object) mapZnhl.get("SUMINCOMEB");
					Object objc = (Object) mapZnhl.get("SUMUSERC");
					Object objd = (Object) mapZnhl.get("SUMINCOMED");
					Object obje = (Object) mapZnhl.get("SUMUSERE");
					if (obja != null) {
						String stra = obja.toString();
						suma = Integer.parseInt(stra);
					}
					if (objb != null) {
						String strb = objb.toString();
						sumb = Double.parseDouble(strb);
					}
					if (objc != null) {
						String strc = objc.toString();
						sumc = Integer.parseInt(strc);
					}
					if (objd != null) {
						String strd = objd.toString();
						sumd = Double.parseDouble(strd);
					}
					if (obje != null) {
						String stre = obje.toString();
						sume = Integer.parseInt(stre);
					}
				}
				sumCountA = sumA +suma;
				sumCountB = sumB +sumb;
				sumCountC = sumC +sumc;
				sumCountD = sumD +sumd;
				sumCountE = sumE +sume;
				Map<String, Object> mapSumCount = new HashMap<String, Object>();
				mapSumCount.put("STATICNAME", "总计");
				mapSumCount.put("SUMUSERA",sumCountA);
				mapSumCount.put("SUMINCOMEB",decimalFormat.format(sumCountB));
				mapSumCount.put("SUMUSERC",sumCountC);
				mapSumCount.put("SUMINCOMED",decimalFormat.format(sumCountD));
				mapSumCount.put("SUMUSERE",sumCountE);
				list.add(mapSumCount);
			}
			Map<String, Object> map0 = new HashMap<String, Object>();
			map0.put("sheetName", "sheet1");
			list.add(0, map0);
			String[] columnNames = { "一级分类", "用户（当月新增）", "收入（当月新增）", "用户（财年累计）", "收入（财年累计）", "总用户数" };// 列名
			String[] keys = { "STATICNAME", "SUMUSERA", "SUMINCOMEB", "SUMUSERC", "SUMINCOMED", "SUMUSERE" };// map中的key
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			log.info("开始导出报表=========================");
			try {
				ExcelUtil.createWorkBook(list, keys, columnNames).write(os);
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
			String fileName = "管理员报表";
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
