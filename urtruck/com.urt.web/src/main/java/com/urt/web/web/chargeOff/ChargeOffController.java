package com.urt.web.web.chargeOff;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.urt.common.enumeration.ConstantEnum;
import com.urt.dto.LaoBatchDataDto;
import com.urt.dto.LaoCustomerDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoSsStaticDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.Goods.OperatorsDto;
import com.urt.dto.Remain.LaoBPaylogDto;
import com.urt.dto.chargeOff.LaoOperatorsBillDto;
import com.urt.dto.chargeOff.LaoOperatorsCycleDto;
import com.urt.dto.chargeOff.LaoOperatorsbillResultDto;
import com.urt.dto.chargeOff.LaoUserbillsDetailDto;
import com.urt.interfaces.Goods.OperatorsService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.batch.BatchService;
import com.urt.interfaces.chargeOff.ChargeOffService;
import com.urt.interfaces.chargeOff.UserBillsDetailService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
import com.urt.web.common.util.ActionUtil;
import com.urt.web.common.util.ExcelReaderUtil;
import com.urt.web.common.util.POIExcelUtil;

/**
* 功能描述：出账功能类
* 类名：ChargeOffController 
* @author sunhao
* @date 2017年3月7日
 */
@Controller
@RequestMapping(value = "chargeOff")
public class ChargeOffController {
	@Autowired
	private OperatorsService operatorService;
	
	@Autowired
	private ChargeOffService chargeOffService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserBillsDetailService userBillsDetailService;	
	
	@Autowired
	private BatchService batchService;
	/**
	* 功能描述：账单导入的主界面
	* @author sunhao
	* @date 2017年3月7日 下午2:12:04
	* @param @return
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping("/toIndex")
	public ModelAndView uploadExcel() {
		List<OperatorsDto> operators = operatorService.findOperators();
		ModelAndView mv = new ModelAndView("/chargeOff/billingImport");
		mv.addObject("operators", operators);
		return mv;
	}
	
	/**
	* 功能描述：账单导入功能
	* @author sunhao
	* @date 2017年3月7日 下午2:51:21
	* @param @param file
	* @param @param operator
	* @param @param cycleId
	* @return void
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/billingImport")
	public Map<String,Object> upload(@RequestParam(value = "file") MultipartFile file, String operators, String cycleId, HttpSession session, HttpServletRequest request){
		ExcelReaderUtil util = new ExcelReaderUtil();
		List<Map<String, String>> list = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			if (!file.isEmpty()) {
				if (file.getOriginalFilename().endsWith("xlsx")) {
					list = util.importExcel(file.getInputStream(), true);
				} else {
					list = util.importExcel(file.getInputStream(), false);
				}
			}
			String id = ZkGenerateSeq.getIdSeq(SeqID.BATCH_ID);
			for (Map<String, String> map : list) {
				map.put("batchId", id);
				map.put("operators", operators);
				map.put("cycleId", cycleId);
			}
			if (list != null && list.size() > 0) {
				LaoBatchDataDto dto=new LaoBatchDataDto();
				dto.setBatchId(Long.parseLong(id));
				dto.setRecvTime(new Date());
				dto.setSumNum((long)list.size());
				LaoSsAccountDto user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
				if(user != null) {
					dto.setOperId(user.getAcconutId().toString());
				}
				dto.setTradeTypeCode(Short.valueOf("200"));
				batchService.saveBatchData(dto);
				
				List<Map<String, String>> sendList = new ArrayList<Map<String, String>>();
				for(int i = 0; i < list.size(); i++){
					sendList.add(list.get(i));
					if(i%10000 == 0){
						chargeOffService.sendBillMsg(sendList);
						sendList.clear();
					}
				}
				chargeOffService.sendBillMsg(sendList);
				resultMap.put("sucMsg", list.size());
			}else{
				resultMap.put("errorMsg", "读取excel 内容为空");
			}
			/*// 将对象插入数据库
			int total = 0;
			if (list != null && list.size() > 0) {
				List<LaoOperatorsBillDto> cast = new ArrayList<LaoOperatorsBillDto>();
				for (int i=0 ; i < list.size() ; i++) {
					LaoOperatorsBillDto dto = new LaoOperatorsBillDto();
					String iccid = list.get(i).get("ICCID");
					
					//根据iccid 或msisdn 查询得到user_id 
					LaoUserDto laoUserDto = userService.getLaoUserDtoByIccid(iccid);
					if(laoUserDto != null){
						dto.setUserId(laoUserDto.getUserId());
						dto.setChannelCustId(laoUserDto.getChannelCustId());
					}else{
						laoUserDto = userService.getLaoUserDtoByMsisdn(list.get(i).get("MSISDN"));
						if(laoUserDto != null){
							dto.setUserId(laoUserDto.getUserId());
							dto.setChannelCustId(laoUserDto.getChannelCustId());
						}
					}
					
					dto.setIccid(list.get(i).get("ICCID"));
					String realFee = list.get(i).get("REAL_FEE");
					if(StringUtils.isNotBlank(realFee)){
						realFee = WeixinUtil.getMoney(realFee.trim());
						dto.setRealFee(Long.parseLong(realFee));
					}
					String fee = list.get(i).get("FEE");
					if(StringUtils.isNotBlank(fee)){
						fee = WeixinUtil.getMoney(fee.trim());
						dto.setFee(Long.parseLong(fee));
					}
					dto.setMsisdn(list.get(i).get("MSISDN"));
					dto.setOperatorsPid(list.get(i).get("OPERATORS_PID"));
					dto.setOperatorsBillId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BILL_ID)));
					dto.setOperatorsId(Integer.parseInt(operators));
					dto.setCycleId(Integer.parseInt(cycleId));
					dto.setBalanceTag("0");//0-未对账；1-已平账；2-未平账
					dto.setRecvTime(new Date());
					
					String pid = list.get(i).get("OPERATORS_PID");
					OperatorPlanDto selectByPid = chargeOffService.selectByPid(pid);
					if(selectByPid != null){
						dto.setPlanId(selectByPid.getPlanId());
					}
					
					cast.add(dto);
					if(i%1000 == 0){
						total = total + chargeOffService.batchInsert(cast);
						cast.clear();
					}
				}
				if(cast.size() > 0){
					total = total + chargeOffService.batchInsert(cast);
				}
			}*/
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	* 功能描述：出账操作：使用kafka 和 storm 处理
	* @author sunhao
	* @date 2017年3月11日 下午2:43:13
	* @param @param cycleId operators
	* @return void
	* @throws
	* 当前时间推算上一个离当前时间最近的账期日作为结束时间
	*例如，联通账单是是27号LAO_OPERATORS_CYCLE. CYCLE_DAY，当前出账账期为201702，则推算得到最近的要出账的开始时间为START_DATE=1月26日23:59:59，结束时间为END_DATE=2月27日00:00:00;
	*例如，电信账单是1号LAO_OPERATORS_CYCLE. CYCLE_DAY，当前出账账期为201702，则推算得到最近的要出账的开始时间为START_DATE=2月1日00:00:00，结束时间为END_DATE=2月28日23:59:59，;
	 */
	@RequestMapping("/doneCharge")
	public void chargeOff(String operators, String  cycleId, HttpServletRequest request){
		List<String> userIdList = chargeOffService.getUserIdList(Integer.parseInt(cycleId), "0");//0-未对账；1-已平账；2-未平账
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		if(userIdList != null && userIdList.size()> 0){
			String id = ZkGenerateSeq.getIdSeq(SeqID.BATCH_ID);
			LaoBatchDataDto dto=new LaoBatchDataDto();
			dto.setBatchId(Long.parseLong(id));
			dto.setRecvTime(new Date());
			dto.setSumNum((long)userIdList.size());
			LaoSsAccountDto user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
			if(user != null) {
				dto.setOperId(user.getAcconutId().toString());
			}
			dto.setTradeTypeCode(Short.valueOf("190"));
			batchService.saveBatchData(dto);
			
			for (String userId : userIdList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", userId);
				map.put("batchId", id);
				map.put("cycleId", cycleId);
				map.put("operatorId", operators);
				listMap.add(map);
			}
		}
		
		LaoOperatorsCycleDto record = new LaoOperatorsCycleDto();
		int cycId = Integer.parseInt(cycleId);
		Calendar calendar = Calendar.getInstance(); 
		int year = calendar.get(Calendar.YEAR);
		
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天
        int day = calendar.get(Calendar.DAY_OF_MONTH);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d H:m:s");  
	    // 指定一个日期  
	    try {
	    	StringBuilder builder = new StringBuilder();
	    	int mm = cycId%100;
			if(("1").equals(operators)){  //如果是联通的卡
				if(mm - 1 == 0){
					builder.append(year-1).append("-").append("12").append("-").append("26 23:59:59");
				}else{
					builder.append(year).append("-").append(mm -1).append("-").append("26 23:59:59");
				}
				Date date = dateFormat.parse(builder.toString());
				record.setStartDate(date);
				builder.delete(0, builder.length());//清空
				builder.append(year).append("-").append(mm).append("-").append("27 00:00:00");
				date = dateFormat.parse(builder.toString());
				record.setEndDate(date);
				record.setIdType("0");			//0-运营商；1-企业客户；2-个人客户
				record.setIdValue(operators);
			}else{					//如果是联通以外的卡
				builder.append(year).append("-").append(mm).append("-").append("1 00:00:00");
				Date date = dateFormat.parse(builder.toString());
				record.setStartDate(date);
				builder.delete(0, builder.length());//清空
				builder.append(year).append("-").append(mm).append("-").append(day).append(" 23:59:59");
				date = dateFormat.parse(builder.toString());
				record.setEndDate(date);
				record.setIdType("0");			//0-运营商；1-企业客户；2-个人客户
				record.setIdValue(operators);
			}
			record.setCycId(cycId);
			LaoOperatorsCycleDto operatorsCycle = chargeOffService.getOperatorsCycle(cycId, operators, "0");//idType :0-运营商；1-企业客户；2-个人客户  
			if(operatorsCycle == null){
				chargeOffService.insertOperatorsCycle(record);
			}else{
				chargeOffService.updateOperatorsCycle(record);
			}
			
			List<Map<String, Object>> sendList = new ArrayList<Map<String, Object>>();
			for(int i = 0; i < listMap.size(); i++){
				sendList.add(listMap.get(i));
				if(i%10000 == 0){
					chargeOffService.sendUserMsg(sendList);
					sendList.clear();
				}
			}
			chargeOffService.sendUserMsg(sendList);
	    } catch (ParseException e) {
			e.printStackTrace();
		} 
		
	}
	
	/***********************************对账结果展示**************************************/
	/**
	* 功能描述：展示对账结果
	* @author sunhao
	* @date 2017年3月22日 下午1:49:06
	* @param @return
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping("/billingShow")
	public ModelAndView showResult() {
		List<OperatorsDto> operators = operatorService.findOperators();
		ModelAndView mv = new ModelAndView("/chargeOff/billingShow");
		mv.addObject("operators", operators);
		return mv;
	}
	
	/**
	* 功能描述：分页查询对账结果表， 如果没有数据，插入数据（前提是必须有OperatorsId 和CycleId 参数且 运营商账单中有数据）
	* @author sunhao
	* @date 2017年3月28日 下午2:05:14
	* @param @param dto
	* @param @param request
	* @param @return
	* @return Map<String,Object>
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/getBillsResult")
	public Map<String, Object> getCardstatusList(LaoOperatorsbillResultDto dto, HttpServletRequest request) {
		int pageStart = request.getParameter("iDisplayStart") ==null ? 1:Integer.parseInt(request.getParameter("iDisplayStart").toString());//开始显示的项
		int pageSize = request.getParameter("iDisplayLength") == null ? 5:Integer.parseInt(request.getParameter("iDisplayLength").toString());//显示多少项
		int pageNo = (pageStart / pageSize)+1;
		Map<String, Object> resultMap = new HashMap<>();
		if(dto.getCycleId() != null){
			if( dto.getOperatorsId() == -1) dto.setOperatorsId(null);
			resultMap = chargeOffService.getBillsResult(dto, pageNo, pageSize);
		}else{
			resultMap.put("data", new ArrayList<LaoOperatorsbillResultDto>());
			resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
			resultMap.put("iTotalDisplayRecords", 0);// 总记录数
		}
		return resultMap;
	}
	
	/**
	* 功能描述：分页查询运营商账单数据
	* @author sunhao
	* @date 2017年3月28日 下午2:07:18
	* @param @param dto
	* @param @param request
	* @param @return
	* @return Map<String,Object>
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/getOperatorsBills")
	public Map<String, Object> getOperatorsBills(LaoOperatorsBillDto dto, HttpServletRequest request) {
		int pageStart = request.getParameter("iDisplayStart") ==null ? 1:Integer.parseInt(request.getParameter("iDisplayStart").toString());//开始显示的项
		int pageSize = request.getParameter("iDisplayLength") == null ? 5:Integer.parseInt(request.getParameter("iDisplayLength").toString());//显示多少项
		int pageNo = (pageStart / pageSize)+1;
		Map<String, Object> resultMap = new HashMap<>();
		if(dto.getCycleId() != null){
			if( dto.getOperatorsId() == -1) dto.setOperatorsId(null);
			resultMap = chargeOffService.queryOperatorsBillsByPage(dto, pageNo, pageSize);
		}else{
			resultMap.put("data", new ArrayList<LaoOperatorsBillDto>());
			resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
			resultMap.put("iTotalDisplayRecords", 0);// 总记录数
		}
		return resultMap;
	}
	
	/**
	* 功能描述：导出详细的运营商账单信息
	* @author sunhao
	* @date 2017年3月31日 下午6:10:33
	* @param @param cId
	* @param @param opId
	* @param @param flag
	* @param @param response
	* @return void
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/exportBilling")
	public void exportBilling(Integer cId, Integer opId, String flag, HttpServletResponse response, HttpServletRequest request){
		POIExcelUtil util = new POIExcelUtil();
		List<LaoOperatorsBillDto> list = null;
		if(cId != null && opId != null && StringUtils.isNotBlank(flag)){
			list = chargeOffService.getOperatorBills(cId, opId, flag, null);
		}
		Map<String,String> maps = null;
		if(list != null && list.size() > 0){
			maps = new HashMap<String,String>();
			maps.put("partitionId", "分区");
			maps.put("cycleId", "账期");
			maps.put("operatorsName", "运营商ID");
			maps.put("channelCustId", "渠道客户ID");
			maps.put("iccid", "ICCID");
			maps.put("msisdn", "服务号码");
			maps.put("operatorsPid", "运营商套餐ID");
			maps.put("planId", "套餐编码");
			maps.put("productName", "外部产品名称");
			maps.put("useCount", "使用量");
			maps.put("fee", "应收费用");
			maps.put("realFee", "实际费用");
			maps.put("balanceTag", "对账结果标识");
			maps.put("glaFee", "GLA所出成本费用");
			maps.put("recvTime", "生成时间");
			
			String excelFileName = "运营商账单列表.xls";
			util.excelExport(maps, list, excelFileName, response);
		}
	}
	
	/***********************************用户账单**************************************/
	/**
	* 功能描述：进入用户账单展示界面
	* @author sunhao
	* @date 2017年4月7日 下午1:44:34
	* @param @return
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping("/userBillingShow")
	public ModelAndView userBillingShow() {
		List<OperatorsDto> operators = operatorService.findOperators();
		List<LaoCustomerDto> queryAllAgents = chargeOffService.queryAllAgents();
		ModelAndView mv = new ModelAndView("/chargeOff/userBillingShow");
		mv.addObject("operators", operators);
		mv.addObject("agents",queryAllAgents);
		return mv;
	}
	
	/**
	* 功能描述：此功能提供给财务使用，可以查看每个月的成本对应收收入。 分页查询用户每月详细的收入和总费用
	* @author sunhao
	* @date 2017年4月1日 下午1:54:40
	* @param @param dto
	* @param @param request
	* @param @return
	* @return Map<String,Object>
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/getUserBillsDetail")
	public Map<String, Object> getUserBillsDetail(LaoUserbillsDetailDto dto, HttpServletRequest request) {
		int pageStart = request.getParameter("iDisplayStart") ==null ? 1:Integer.parseInt(request.getParameter("iDisplayStart").toString());//开始显示的项
		int pageSize = request.getParameter("iDisplayLength") == null ? 5:Integer.parseInt(request.getParameter("iDisplayLength").toString());//显示多少项
		int pageNo = (pageStart / pageSize)+1;
		Map<String, Object> resultMap = new HashMap<>();
		if(dto.getCycleId() != null && dto.getCustId() != null){
			resultMap = userBillsDetailService.queryUserBillsByPage(dto, pageNo, pageSize);
		}else{
			resultMap.put("data", new ArrayList<LaoUserbillsDetailDto>());
			resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
			resultMap.put("iTotalDisplayRecords", 0);// 总记录数
		}
		return resultMap;
	} 
	
	/**
	* 功能描述：计算总费用
	* @author sunhao
	* @date 2017年5月5日 下午5:20:03
	* @param @param dto
	* @param @param request
	* @param @return
	* @return Map<String,Object>
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/getTotal")
	public Long getCountTotal(LaoUserbillsDetailDto dto, HttpServletRequest request) {
		if(dto != null){
			return userBillsDetailService.countTotal(dto.getCustId(), dto.getCycleId(), dto.getOperatosId());
		}
		return 0l;
	}
	
	/**
	* 功能描述：导出 用户账单  
	* @author sunhao
	* @date 2017年4月1日 下午1:54:40
	* @param @param dto
	* @param @param request
	* @param @return
	* @return Map<String,Object>
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/exportBillsDetails")
	public void exportBillsDetails(LaoUserbillsDetailDto dto, HttpServletRequest request, HttpServletResponse response) {
		POIExcelUtil util = new POIExcelUtil();
		List<LaoUserbillsDetailDto> list = null;
		if(dto.getCycleId() != null && dto.getCustId() != null){
			list = userBillsDetailService.queryUserBills(dto);
		}
		Map<String,String> maps = null;
		if(list != null && list.size() > 0){
			maps = new HashMap<String,String>();
			maps.put("custName", "客户名称");
			maps.put("operatosName", "运营商名称");
			maps.put("iccid", "ICCID");
			maps.put("msisdn", "服务号码");
			maps.put("goodsName", "产品名称");
			maps.put("status", "当前状态");
			maps.put("fee", "出账费用");
			maps.put("oweFee", "未出账费用");
			
			String excelFileName = "用户账单列表.xls";
			util.excelExport(maps, list, excelFileName, response);
		}
	}
	
	/***********************************营收账单**************************************/
	/**
	* 功能描述：营收展示界面
	* @author sunhao
	* @date 2017年4月7日 下午1:46:05
	* @param @return
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping("/revenueView")
	public ModelAndView revenueBillingShow(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/chargeOff/revenueView");
		List<LaoSsStaticDto> queryPaymentOps = chargeOffService.queryPaymentOps();
		List<LaoCustomerDto> queryAllAgents = chargeOffService.queryAllAgents();
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		if (!ActionUtil.ifSuperUser(request)) {
			if(user != null){
				Long custId = user.getCustId();
				if(custId == null) custId = ConstantEnum.CUSTID.getCode();
				for (LaoCustomerDto laoCustomerDto : queryAllAgents) {
					if(laoCustomerDto.getCustId() == custId){
						mv.addObject("agents", laoCustomerDto);
						break;
					}
				}
			}
		}else{
			mv.addObject("agents", queryAllAgents);
		}
		mv.addObject("paymentType",queryPaymentOps);
		return mv;
	}
	
	/**
	* 功能描述：
	* @author sunhao
	* @date 2017年4月7日 下午2:43:56
	* @param @param dto
	* @param @param request
	* @param @return
	* @return Map<String,Object>
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/revenue")
	public Map<String, Object> revenue(LaoBPaylogDto dto, HttpServletRequest request, String startDate, String endDate) {
		int pageStart = request.getParameter("iDisplayStart") ==null ? 1:Integer.parseInt(request.getParameter("iDisplayStart").toString());//开始显示的项
		int pageSize = request.getParameter("iDisplayLength") == null ? 5:Integer.parseInt(request.getParameter("iDisplayLength").toString());//显示多少项
		int pageNo = (pageStart / pageSize)+1;
		Map<String, Object> resultMap = new HashMap<>();
		if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate) && dto.getChannelCustId() != null){
			if(StringUtils.isNotBlank(dto.getIccid())){
				LaoUserDto laoUserDto = userService.getLaoUserDtoByIccid(dto.getIccid());
				if(laoUserDto != null){
					dto.setUserId(laoUserDto.getUserId());
				}else{
					dto.setUserId(Long.parseLong(dto.getIccid()));
				}
			}else if(StringUtils.isNotBlank(dto.getMsisdn())){
				LaoUserDto laoUserDtoByMsisdn = userService.getLaoUserDtoByMsisdn(dto.getMsisdn());
				if(laoUserDtoByMsisdn != null){
					dto.setUserId(laoUserDtoByMsisdn.getUserId());
				}else{
					dto.setUserId(Long.parseLong(dto.getMsisdn()));
				}
			}
			resultMap = chargeOffService.revenueByPage(dto, pageNo, pageSize, startDate, endDate);
		}else{
			resultMap.put("data", new ArrayList<LaoBPaylogDto>());
			resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
			resultMap.put("iTotalDisplayRecords", 0);// 总记录数
		}
		return resultMap;
	} 
	
	/**
	* 功能描述：导出 营收账单
	* @author sunhao
	* @date 2017年4月8日 下午2:43:13
	* @param @param dto
	* @param @param startDate
	* @param @param endDate
	* @param @param request
	* @param @param response
	* @return void
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/exportRevenue")
	public void exportRevenue(LaoBPaylogDto dto, String startDate, String endDate, HttpServletRequest request, HttpServletResponse response){
		POIExcelUtil util = new POIExcelUtil();
		List<LaoBPaylogDto> list = null;
		if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate) && dto.getChannelCustId() != null){
			if(StringUtils.isNotBlank(dto.getIccid())){
				LaoUserDto laoUserDto = userService.getLaoUserDtoByIccid(dto.getIccid());
				if(laoUserDto != null){
					dto.setUserId(laoUserDto.getUserId());
				}else{
					dto.setUserId(Long.parseLong(dto.getIccid()));
				}
			}else if(StringUtils.isNotBlank(dto.getMsisdn())){
				LaoUserDto laoUserDtoByMsisdn = userService.getLaoUserDtoByMsisdn(dto.getMsisdn());
				if(laoUserDtoByMsisdn != null){
					dto.setUserId(laoUserDtoByMsisdn.getUserId());
				}else{
					dto.setUserId(Long.parseLong(dto.getMsisdn()));
				}
			}
			list = chargeOffService.revenue(dto, startDate, endDate);
		}
		Map<String,String> maps = null;
		if(list != null && list.size() > 0){
			maps = new HashMap<String,String>();
			maps.put("custName", "客户名称");
			maps.put("operatorName", "运营商名称");
			maps.put("iccid", "ICCID");
			maps.put("msisdn", "服务号码");
			maps.put("goodName", "产品名称");
			maps.put("status", "当前状态");
			maps.put("realFee", "实际费用");
			maps.put("staticName", "对账结果标识");
			maps.put("recvTime", "生成时间");
			
			String excelFileName = "营收账单列表.xls";
			util.excelExport(maps, list, excelFileName, response);
		}
	}
}
