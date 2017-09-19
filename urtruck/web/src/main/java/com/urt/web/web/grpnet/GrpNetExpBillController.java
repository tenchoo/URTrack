package com.urt.web.web.grpnet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.urt.dto.grpnet.DItemDto;
import com.urt.interfaces.grpnet.GrpNetExpBillService;
import com.urt.web.common.util.*;

@Controller("grpNetExpBillController")
@RequestMapping(value = "/grpNetExpBill")
public class GrpNetExpBillController {

	@Autowired
	private GrpNetExpBillService grpNetExpBillService;

	@RequestMapping(value = "/grpNetBill", method = RequestMethod.GET)
	public String grpNetBill() {
		return "/grpNet/grpNetBill";
	}

	@RequestMapping(value = "/grpNetBillType")
	public ModelAndView grpNetBillType(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		// 获取账单类型
		String tradeType = request.getParameter("tradeType");
		// 集群网用户明细账单
		if ("0".equals(tradeType)) {
			ModelAndView mv = new ModelAndView("redirect:/grpNetExpBill/expByUserDetailBill");
			String cycleId = request.getParameter("cycleId");
			mv.addObject("cycleId", cycleId);
			return mv;
		}else if ("1".equals(tradeType)) {  //集群网用户汇总账单
			ModelAndView mv = new ModelAndView("redirect:/grpNetExpBill/expByUserSumBill");
			String cycleId = request.getParameter("cycleId");
			mv.addObject("cycleId", cycleId);
			return mv;
		}else if ("2".equals(tradeType)) {  //集群网费用汇总账单
			ModelAndView mv = new ModelAndView("redirect:/grpNetExpBill/expByItemSumBill");
			String cycleId = request.getParameter("cycleId");
			mv.addObject("cycleId", cycleId);
			return mv;
	    }
		return null;

	}
	/*
	 * 获取用户明细账单
	 */
	@RequestMapping(value = "/expByUserDetailBill")
	public void expExcelByUserDetailBill(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {

		// 获取账单信息--账期
		Integer cycleId = Integer.parseInt(request.getParameter("cycleId"));
		// 获取账单信息--地域
		String cityName = "";
		String companyName = "北京联想调频科技有限公司";

		// 设置文件名称前缀
		String fileName = "集群网用户明细账单_" + cycleId.toString();
		/*if (StringUtils.isNotBlank(companyName)){
			fileName = fileName + "_" + companyName;
		}*/
		if (StringUtils.isNotBlank(cityName))
			fileName = fileName + "_" + cityName;

//		session.setAttribute("state", null);
		response.setContentType("application/vnd.ms-excel");
		ServletOutputStream outputStream = response.getOutputStream();

		// 进行转码，使其支持中文文件名
		String codedFileName = null;
		codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");

		List<List<Map<String, Object>>> headInfoListList = new ArrayList<List<Map<String, Object>>>();
		Map<String, Object> itemMap = new HashMap<String, Object>();

		List<List<String>> headPatternList = new ArrayList<List<String>>();
		List<String> headPattern = null;
		String head = null;

		// 表头第一行
		ArrayList<Map<String, Object>> headInfoList0 = new ArrayList<Map<String, Object>>();
		headPattern = new ArrayList<String>();
		itemMap.put("title", "账户名称：" + companyName);
		headInfoList0.add(itemMap);
		// 设置该表头位置
		head = "0,0,0,6"; // 行：第0行-第5行 列：第0列-第6列
		headPattern.add(head);
		headPatternList.add(headPattern);
		headInfoListList.add(headInfoList0);

		// 表头第二行
		ArrayList<Map<String, Object>> headInfoList1 = new ArrayList<Map<String, Object>>();
		headPattern = new ArrayList<String>();
		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "账单账期");
		headInfoList1.add(itemMap);
		// 设置该表头位置
		head = "1,1,0,0"; // 行：第1行-第1行 列：第0列-第0列
		headPattern.add(head);

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", cycleId.toString());
		headInfoList1.add(itemMap);
		// 设置该表头位置
		head = "1,1,1,1"; // 行：第1行-第1行 列：第1列-第1列
		headPattern.add(head);

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "付款方式");
		headInfoList1.add(itemMap);
		// 设置该表头位置
		head = "1,1,3,3"; // 行：第1行-第1行 列：第3列-第3列
		headPattern.add(head);

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "现金支付");
		headInfoList1.add(itemMap);
		// 设置该表头位置
		head = "1,1,4,5"; // 行：第1行-第1行 列：第4列-第5列
		headPattern.add(head);
		headPatternList.add(headPattern);

		headInfoListList.add(headInfoList1);

		// 表头第3行，标准表头
		List<Map<String, Object>> headInfoStandard = new ArrayList<Map<String, Object>>();
		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "设备号码");
		itemMap.put("columnWidth", 25);
		itemMap.put("dataKey", "XH1");
		headInfoStandard.add(itemMap);

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "产品名称");
		itemMap.put("columnWidth", 20);
		itemMap.put("dataKey", "XH2");
		headInfoStandard.add(itemMap);

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "费用名称");
		itemMap.put("columnWidth", 50);
		itemMap.put("dataKey", "XH3");
		headInfoStandard.add(itemMap);

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "优惠前金额(元)");
		itemMap.put("columnWidth", 30);
		itemMap.put("dataKey", "XH4");
		headInfoStandard.add(itemMap);

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "优惠金额(元)");
		itemMap.put("columnWidth", 30);
		itemMap.put("dataKey", "XH5");
		headInfoStandard.add(itemMap);

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "实际金额(元)");
		itemMap.put("columnWidth", 30);
		itemMap.put("dataKey", "XH6");
		headInfoStandard.add(itemMap);

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "税率");
		itemMap.put("columnWidth", 20);
		itemMap.put("dataKey", "XH7");
		headInfoStandard.add(itemMap);

		List<Map<String, Object>> dataList = grpNetExpBillService.qryBBillByCycleId(cycleId);

		ExcelExpUtil.exportExcelByMultiHeads(companyName, codedFileName, headInfoListList, headPatternList,
				headInfoStandard, dataList, outputStream);
	}
	/*
	 * 获取用户汇总账单
	 */
	@RequestMapping(value = "/expByUserSumBill")
	public void expExcelByUserSumBill(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {

		// 获取账单信息--账期
		Integer cycleId = Integer.parseInt(request.getParameter("cycleId"));
		String companyName = "北京联想调频科技有限公司";

		// 设置文件名称前缀
		String fileName = "集群网用户汇总账单_" + cycleId.toString();

		session.setAttribute("state", null);
		response.setContentType("application/vnd.ms-excel");
		ServletOutputStream outputStream = response.getOutputStream();

		// 进行转码，使其支持中文文件名
		String codedFileName = null;
		codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		List<List<Map<String, Object>>> headInfoListList = new ArrayList<List<Map<String, Object>>>();
		Map<String, Object> itemMap = null;

		List<List<String>> headPatternList = new ArrayList<List<String>>();
		List<String> headPattern = null;
		String head = null;
		
		//获取所有的费用项目的上一级产品项目		
		List<DItemDto> itemList = grpNetExpBillService.qryDItemByPItemId();

		// 表头第一行
		ArrayList<Map<String, Object>> headInfoList0 = new ArrayList<Map<String, Object>>();
		headPattern = new ArrayList<String>();
		
		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "号码");
		headInfoList0.add(itemMap);
		// 设置该表头位置
		head = "0,1,0,0"; // 行：第0行-第0行 列：第0列-第0列
		headPattern.add(head);
		
		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "优惠前金额（元）");
		headInfoList0.add(itemMap);
		// 设置该表头位置
		Integer pItemSize = itemList.size();
		head = "0,0,1,"+ pItemSize.toString(); // 行：第0行-第0行 列：第1列-第5列
		headPattern.add(head);
		
		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "优惠金额（元）");
		headInfoList0.add(itemMap);
		// 设置该表头位置
		pItemSize = pItemSize + 1;
		head = "0,1,"+ pItemSize.toString() + "," + pItemSize.toString(); // 行：第0行-第0行 列：第?列-第?列
		headPattern.add(head);
		
		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "优惠后总金额（元）");
		headInfoList0.add(itemMap);
		// 设置该表头位置
		pItemSize = pItemSize + 1;
		head = "0,1,"+ pItemSize.toString() + "," + pItemSize.toString(); // 行：第0行-第0行 列：第1列-第5列
		headPattern.add(head);
		
		headPatternList.add(headPattern);

		headInfoListList.add(headInfoList0);

		// 表头第2行，标准表头
		List<Map<String, Object>> headInfoStandard = new ArrayList<Map<String, Object>>();
		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "号码");
		itemMap.put("columnWidth", 25);
		itemMap.put("dataKey", "XH1");
		headInfoStandard.add(itemMap);
        
        //动态获取产品列
		for(int i = 0; i < itemList.size(); i++){
			itemMap = new HashMap<String, Object>();
			itemMap.put("title", itemList.get(i).getItemName());
			itemMap.put("columnWidth", 20);
			itemMap.put("dataKey", "XH"+itemList.get(i).getItemId());
			headInfoStandard.add(itemMap);
		}

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "优惠金额（元）");
		itemMap.put("columnWidth", 30);
		itemMap.put("dataKey", "XH2");
		headInfoStandard.add(itemMap);

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "优惠后总金额（元）");
		itemMap.put("columnWidth", 30);
		itemMap.put("dataKey", "XH3");
		headInfoStandard.add(itemMap);

		dataList = grpNetExpBillService.qryBBillSumByUserCycleId(cycleId);

		ExcelExpUtil.exportExcelByMultiHeads(companyName, codedFileName, headInfoListList, headPatternList,
				headInfoStandard, dataList, outputStream);
	}

	/*
	 * 获取用户明细账单
	 */
	@RequestMapping(value = "/expByItemSumBill")
	public void expExcelByItemSumBill(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {

		// 获取账单信息--账期
		Integer cycleId = Integer.parseInt(request.getParameter("cycleId"));
		// 获取账单信息--地域
		String cityName = "";
		String companyName = "北京联想调频科技有限公司";

		// 设置文件名称前缀
		String fileName = "集群网费用项汇总账单_" + cycleId.toString();
		/*if (StringUtils.isNotBlank(companyName)){
			fileName = fileName + "_" + companyName;
		}*/
		if (StringUtils.isNotBlank(cityName))
			fileName = fileName + "_" + cityName;

		response.setContentType("application/vnd.ms-excel");
		ServletOutputStream outputStream = response.getOutputStream();

		// 进行转码，使其支持中文文件名
		String codedFileName = null;
		codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");

		List<List<Map<String, Object>>> headInfoListList = new ArrayList<List<Map<String, Object>>>();
		Map<String, Object> itemMap = new HashMap<String, Object>();

		List<List<String>> headPatternList = new ArrayList<List<String>>();
		List<String> headPattern = null;
		String head = null;

		// 表头第一行
		ArrayList<Map<String, Object>> headInfoList0 = new ArrayList<Map<String, Object>>();
		headPattern = new ArrayList<String>();
		itemMap.put("title", "账户名称：" + companyName);
		headInfoList0.add(itemMap);
		// 设置该表头位置
		head = "0,0,0,5"; // 行：第0行-第5行 列：第0列-第6列
		headPattern.add(head);
		headPatternList.add(headPattern);
		headInfoListList.add(headInfoList0);

		// 表头第二行
		ArrayList<Map<String, Object>> headInfoList1 = new ArrayList<Map<String, Object>>();
		headPattern = new ArrayList<String>();
		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "账单账期");
		headInfoList1.add(itemMap);
		// 设置该表头位置
		head = "1,1,0,0"; // 行：第1行-第1行 列：第0列-第0列
		headPattern.add(head);

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", cycleId.toString());
		headInfoList1.add(itemMap);
		// 设置该表头位置
		head = "1,1,1,1"; // 行：第1行-第1行 列：第1列-第1列
		headPattern.add(head);

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "付款方式");
		headInfoList1.add(itemMap);
		// 设置该表头位置
		head = "1,1,3,3"; // 行：第1行-第1行 列：第3列-第3列
		headPattern.add(head);

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "现金支付");
		headInfoList1.add(itemMap);
		// 设置该表头位置
		head = "1,1,4,5"; // 行：第1行-第1行 列：第4列-第5列
		headPattern.add(head);
		headPatternList.add(headPattern);

		headInfoListList.add(headInfoList1);

		// 表头第3行，标准表头
		List<Map<String, Object>> headInfoStandard = new ArrayList<Map<String, Object>>();

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "产品名称");
		itemMap.put("columnWidth", 20);
		itemMap.put("dataKey", "XH1");
		headInfoStandard.add(itemMap);

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "费用名称");
		itemMap.put("columnWidth", 50);
		itemMap.put("dataKey", "XH2");
		headInfoStandard.add(itemMap);

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "优惠前金额(元)");
		itemMap.put("columnWidth", 30);
		itemMap.put("dataKey", "XH3");
		headInfoStandard.add(itemMap);

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "优惠金额(元)");
		itemMap.put("columnWidth", 30);
		itemMap.put("dataKey", "XH4");
		headInfoStandard.add(itemMap);

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "实际金额(元)");
		itemMap.put("columnWidth", 30);
		itemMap.put("dataKey", "XH5");
		headInfoStandard.add(itemMap);

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "税率");
		itemMap.put("columnWidth", 20);
		itemMap.put("dataKey", "XH6");
		headInfoStandard.add(itemMap);

		List<Map<String, Object>> dataList = grpNetExpBillService.qryBBillSumByItemCycleId(cycleId);

		ExcelExpUtil.exportExcelByMultiHeads(companyName, codedFileName, headInfoListList, headPatternList,
				headInfoStandard, dataList, outputStream);
	}

	/*
	 * 为了管理集群网用户，产生用户相关信息
	 */
	@RequestMapping(value = "/dealUserInfo")
	public void dealUserInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session){
	
		// 获取账单信息--账期
		//Integer cycleId = Integer.parseInt(request.getParameter("cycleId"));
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		String preMonth = dateFormat.format(c.getTime());
		//preMonth = "201609";
		//增加集群网用户相关信息
		grpNetExpBillService.addGrpNetUserInfo(Integer.parseInt(preMonth));
		
	}

}
