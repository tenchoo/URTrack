package com.urt.web.web.remain;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.urt.dto.LaoCustGroupDto;
import com.urt.dto.LaoCustomerDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Remain.BalAlarmDto;
import com.urt.dto.Remain.LaoFAcctdepositDto;
import com.urt.dto.Remain.LaoRuleDefDto;
import com.urt.interfaces.customer.LaoCustomerService;
import com.urt.interfaces.remain.RemainService;
import com.urt.web.common.util.ActionUtil;

/**
 * 类说明：余额中心
 * 
 */
@Controller
@RequestMapping(value = "/remain")
public class RemainController {
	private static Logger logger = LoggerFactory
			.getLogger(RemainController.class);
	@Autowired
	private RemainService remainService;
	@Autowired
	private LaoCustomerService laoCustomerService;
	@RequestMapping(value = "/manualSettlement")
	public ModelAndView manualSettlement(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/remain/manualSettlement");
		return mv;
	}

	@RequestMapping(value = "/accountManage")
	public ModelAndView accountManage(HttpServletRequest request) {
		boolean res = ActionUtil.ifSuperUser(request);
		ModelAndView mv = new ModelAndView();
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		logger.info("*****accountManage********：sessionId:"+request.getSession().getId());
		//LaoSsAccountDto user=new Gson().fromJson(sessionOperService.getCluKeyVaue(request.getRequestedSessionId(), "sessionUser", ConstantIntEnum.RELEATIME.getCode()), LaoSsAccountDto.class);
		// Long accountId = user.getAcconutId();
		// int res = remainService.getRoleIdByAccountId(accountId);
		if (res == true) {
			mv = new ModelAndView("/remain/accountManage");
		} else {
			mv = new ModelAndView("/remain/accountCust");
			mv.addObject("custId", user.getCustId());
		}
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/saveRules")
	public Map<String, Object> saveRules(HttpServletRequest req) throws ParseException {
		String data = req.getParameter("accData");
		int status = remainService.saveRules(data);
		Map<String, Object> resMap = new HashMap<String, Object>();
		if (status == 1) {
			resMap.put("result", "success");
		} else {
			resMap.put("result", "fail");
		}
		return resMap;

	}

	@ResponseBody
	@RequestMapping(value = "/editRules")
	public Map<String, Object> editRules(HttpServletRequest req) throws ParseException {
		logger.info("******editRules*****:"+req.getSession().getId());
		String data = req.getParameter("accData");
		int status = remainService.updateRules(data);	
		Map<String, Object> resMap = new HashMap<String, Object>();
		if (status == 1) {
			resMap.put("result", "success");
		} else {
			resMap.put("result", "fail");
		}
		return resMap;
	}

	@ResponseBody
	@RequestMapping(value = "/getGroupList")
	public List<Map<String, Object>> getGroupList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> initMap = new HashMap<String, Object>();
		initMap.put("text", "请选择");
		initMap.put("id", -1);
		list.add(initMap);
		List<LaoCustGroupDto> dtos = remainService.selectAll();
		for (LaoCustGroupDto dto : dtos) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", dto.getCustName());
			map.put("id", dto.getCustId());
			list.add(map);
		}
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/getRules")
	public List<Map<String, Object>> getRules() {
		List<LaoRuleDefDto> dtos = remainService.selectRules();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> initMap = new HashMap<String, Object>();
		initMap.put("text", "请选择");
		initMap.put("id", -1);
		list.add(initMap);
		for (LaoRuleDefDto dto : dtos) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", dto.getRulegroupId() + " " + dto.getRulegroupName());
			map.put("id", dto.getRulegroupId());
			list.add(map);
		}
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/subPay")
	public Map<String, Object> subPay(HttpServletRequest req) {
		String params = req.getParameter("payData");
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		int status = remainService.payment(params, user.getAcconutId());
		Map<String, Object> resMap = new HashMap<String, Object>();
		if (status == 1) {
			resMap.put("result", "success");
		} else {
			resMap.put("result", "fail");
		}
		return resMap;
	}

	/**
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryList")
	public Map<String, Object> queryList(HttpServletRequest req, HttpServletResponse resp) {
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// 显示多少项
		int start = pageStart + 1;
		int end = pageSize + pageStart;
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		String postCustId = req.getParameter("custId");
		Long custId = Long.valueOf(postCustId);
		paraMap.put("start", start);
		paraMap.put("end", end);
		paraMap.put("custId", custId);
		// 返现规则
		List<LaoRuleDefDto> defList = remainService.selectRuleByCustId(paraMap);
		int count = remainService.countRulesByCustId(custId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", defList);
		resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", count);// 总记录数
		return resultMap;
	}

	/**
	 * 返现规则列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addQueryList")
	public Map<String, Object> addQueryList(HttpServletRequest req, HttpServletResponse resp) {
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// 显示多少项
		int start = pageStart + 1;
		int end = pageSize + pageStart;
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		String postGroupId = req.getParameter("custGroupId");
		Long groupId = Long.valueOf(postGroupId);
		paraMap.put("start", start);
		paraMap.put("end", end);
		paraMap.put("groupId", groupId);
		// 返现规则
		List<LaoRuleDefDto> defList = remainService.selectRulesByGroupId(paraMap);
		int count = remainService.countRulesByGroupId(groupId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", defList);
		resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", count);// 总记录数
		return resultMap;
	}

	@ResponseBody
	@RequestMapping(value = "/getGroupInfo")
	public List<Map<String, Object>> getGroupInfo(HttpServletRequest request) {
		String postCustId = request.getParameter("custId");
		Long custId = Long.valueOf(postCustId);
		// 客户信息
		LaoCustomerDto laoCustomerDto = laoCustomerService.selectDtoById(custId);
		// 账户信息
		LaoFAcctdepositDto ldto = remainService.selectdepositByCustId(custId);
		Map<String, Object> initMap = new HashMap<String, Object>();
		// 客户
		initMap.put("custName", laoCustomerDto.getCustName());
		initMap.put("custState", getCustState(laoCustomerDto.getCustState()));
		initMap.put("psptTypeCode", getPsptTypeCode(laoCustomerDto.getPsptTypeCode()));
		initMap.put("psptId", laoCustomerDto.getPsptId());
		if (ldto != null) {
			initMap.put("account", true);
			// 结算信息
			int count = remainService.countUsersByCustId(custId);
			// 账户
			initMap.put("depositMoney", ldto.getDepositMoney());
			initMap.put("startCycId", ldto.getStartCycId());
			initMap.put("endCycId", ldto.getEndCycId());
			initMap.put("cashTag", getCash(ldto.getCashTag()));

			// 结算
			initMap.put("count", count);

			// 企业未交
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("custId", custId);
			paramMap.put("curMonth", getCurMonthTime());
			paramMap.put("payTag", 0);
			int comNoPay = remainService.selectRecvFeeByPayTag(paramMap);

			// 用户已缴
			paramMap.put("payTag", 1);
			int userPaid = remainService.selectRecvFeeByPayTag(paramMap);

			// 企业已缴
			paramMap.put("payTag", 2);
			int comPaid = remainService.selectRecvFeeByPayTag(paramMap);

			initMap.put("comNoPay", comNoPay);
			initMap.put("userPaid", userPaid);
			initMap.put("comPaid", comPaid);

			int needCount = remainService.selectNeedReturn(paramMap);
			int returned = remainService.selectReturned(paramMap);
			initMap.put("needCount", needCount);
			initMap.put("returned", returned);

		} else {
			initMap.put("account", false);
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(initMap);
		return list;
	}

	/**
	 * 获取返现显示
	 * 
	 * @param cashTag
	 * @return
	 */
	public String getCash(String cashTag) {
		String res = "";
		if (cashTag == null) {
			return res;
		}
		switch (cashTag) {
		case "0":
			res = "不结算返现";
			break;
		case "1":
			res = "实时结算返现";
			break;
		case "2":
			res = "手工结算返现";
			break;
		default:
			break;
		}
		return res;
	}

	/**
	 * 获取客户状态
	 * 
	 * @param cashTag
	 * @return
	 */
	public String getCustState(String custState) {
		String res = "注销客户";
		if (custState.equals("0")) {
			res = "潜在客户";
		} else if (custState.equals("1")) {
			res = "在用客户";
		}
		return res;
	}

	/**
	 * 获取证件类型
	 * 
	 * @param cashTag
	 * @return
	 */
	public String getPsptTypeCode(String psptTypecode) {
		String res = "照会";
		if (psptTypecode.equals("1")) {
			res = "营业执照";
		} else if (psptTypecode.equals("2")) {
			res = "法人证书";
		} else if (psptTypecode.equals("3")) {
			res = "组织机构代码证";
		} else if (psptTypecode.equals("4")) {
			res = "介绍信";
		}
		return res;
	}

	public Date getCurMonthTime() {
		// 获取前月的第一天
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.set(cal_1.get(Calendar.YEAR), cal_1.get(Calendar.MONTH), 1, 0, 0, 0);
		return cal_1.getTime();
	}

	/**
	 * 列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/queryGrid")
	public Map<String, Object> queryGrid(HttpServletRequest req, HttpServletResponse resp) {
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// 显示多少项
		int start = pageStart + 1;
		int end = pageSize + pageStart;
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		String cyc = req.getParameter("cycId");
		String type = req.getParameter("type");
		List<HashMap<String, Object>> resList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> tempMap = new HashMap<String, Object>();
		int count = 0;
		if (type.equals("0")) {
			tempMap = (HashMap<String, Object>) remainService.getAccessList(user.getCustId(), cyc, start, end);
		} else if (type.equals("1")) {
			tempMap = (HashMap<String, Object>) remainService.getPayLogList(user.getCustId(), cyc, start, end);
		} else if (type.equals("2")) {
			tempMap = (HashMap<String, Object>) remainService.getBillResList(user.getCustId(), cyc, start, end);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resList = (List<HashMap<String, Object>>) tempMap.get("resList");
		count = (int) tempMap.get("count");
		resultMap.put("data", resList);
		resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", count);// 总记录数
		return resultMap;
	}

	@ResponseBody
	@RequestMapping(value = "/searchCom")
	public Map<String, Object> searchCom(HttpServletRequest request) {
		int pageStart = Integer.parseInt(request.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(request.getParameter("iDisplayLength").toString());// 显示多少项
		int start = pageStart + 1;
		int end = pageSize + pageStart;
		String custId = request.getParameter("custId");
		if (custId.equals("null")) {
			custId = null;
		}
		String cashbackTag = request.getParameter("cashbackTag");
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("start", start);
		paraMap.put("end", end);
		paraMap.put("custId", custId);
		paraMap.put("cashbackTag", cashbackTag);
		List<HashMap<String, Object>> resultList = remainService.queryBillResByCustId(paraMap);
		int count = remainService.coutBillResByCustId(paraMap);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", resultList);
		resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", count);// 总记录数
		return resultMap;
	}

	@ResponseBody
	@RequestMapping(value = "/getCustomList")
	public List<Map<String, Object>> getCustomList(HttpServletRequest req) {
		String param = req.getParameter("q");
		List<Map<String, Object>> resList = remainService.selectComCustByName(param);
		return resList;
	}

	@ResponseBody
	@RequestMapping(value = "/cashBack")
	public Map<String, Object> cashBack(HttpServletRequest req) {
		String balanceIds = req.getParameter("balanceIds");
		String balanceArr[] = balanceIds.split(",");
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		String acctId = null;
		if (user.getAcconutId() != null) {
			acctId = user.getAcconutId().toString();
		}
		for (String id : balanceArr) {
			remainService.returnFee(Long.valueOf(id), acctId);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		return resultMap;
	}
	
	@RequestMapping("/createRule/{custId}")
	public ModelAndView createRule(@PathVariable("custId") Long custId) {
	  ModelAndView mv = new ModelAndView("remain/addBalAlarm");
	  mv.addObject("bCustId", custId);     
	  return mv;
	 }
	
	@ResponseBody
	@RequestMapping(value = "/saveBalRules")
	public int saveBalRules(HttpServletRequest req) throws ParseException {	  
	    String data = req.getParameter("accData");
	    return remainService.saveBalRules(data);
	 }
	
     @ResponseBody
	 @RequestMapping(value = "/getBalRules/{custId}")
	 public Map<String, Object> getBalRules(@PathVariable("custId") Long custId) throws ParseException {           	
	   Map<String, Object> mapBal=new HashMap<String, Object>();
	   List<HashMap<String, Object>> resList = new ArrayList<HashMap<String, Object>>();
	   try {
	        BalAlarmDto balAlarmDto =remainService.getBalRules(custId);
	        if(balAlarmDto!=null){
	          mapBal.put("ruleName", balAlarmDto.getRuleName());
	          mapBal.put("balFee", balAlarmDto.getBalRemain());
	          mapBal.put("emaileId", balAlarmDto.getEmail()!=null?balAlarmDto.getEmail():" ");
	          mapBal.put("telphoneId", balAlarmDto.getTelphone()!=null?balAlarmDto.getTelphone():" ");
	          mapBal.put("cal", balAlarmDto.getCal());	               
	         } 	            
           } catch (Exception e) {
              e.printStackTrace();
        }
	   if(mapBal.size()==5){
	       resList.add((HashMap<String, Object>) mapBal);
	   }   
	    return mapBal;
	}
     
     @ResponseBody
     @RequestMapping(value = "/delBalRules/{custId}")
     public Long delBalRules(@PathVariable("custId") Long custId) throws ParseException {        
       try {
            return remainService.delBalRules(custId);       
           } catch (Exception e) {
              e.printStackTrace();
              return -1L;
        }
    }
     
     @ResponseBody
     @RequestMapping(value = "/updBalRules/{custId}")
     public ModelAndView updBalRules(@PathVariable("custId") Long custId) throws ParseException {
       ModelAndView mv = new ModelAndView("remain/balEdit"); 
       try {
            BalAlarmDto balAlarmDto =remainService.getBalRules(custId);         
            mv.addObject("balAlarmDto", balAlarmDto);            
           } catch (Exception e) {
              e.printStackTrace();
              return null;
        }
       return mv;
    }
     
     @ResponseBody
     @RequestMapping(value = "/editBalRules")
     public int editBalRules(HttpServletRequest req) throws ParseException {   
         String data = req.getParameter("accData");
         String[] datas = data.split(";");
          remainService.delBalRules(Long.valueOf(datas[5]));
         return remainService.saveBalRules(data);
      } 
}
