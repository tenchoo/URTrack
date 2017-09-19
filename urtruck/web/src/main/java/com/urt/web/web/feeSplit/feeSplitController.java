package com.urt.web.web.feeSplit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.urt.common.util.ResultMsg;
import com.urt.dto.LaoCustGroupDto;
import com.urt.dto.LaoCustomerDto;
import com.urt.dto.LaoSmsInfoDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Remain.LaoBAccesslogDto;
import com.urt.interfaces.CustomerCentre.CustomerCentreService;
import com.urt.interfaces.authority.LaoSsAccountService;
import com.urt.interfaces.customer.LaoCustConcatService;
import com.urt.interfaces.customer.LaoCustGroupService;
import com.urt.interfaces.customer.LaoCustomerService;
import com.urt.interfaces.traffic.TrafficQueryService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
import com.urt.web.common.util.ActionUtil;

/**
 * 分润管理
 */
@Controller
@RequestMapping(value = "/feeSplit")
public class feeSplitController {
	@Autowired
	LaoCustomerService customerService;
	@Autowired
	LaoCustConcatService custConcatService;
	@Autowired
	LaoCustGroupService custGroupService;
	@Autowired
	LaoSsAccountService laoSsAccountService;
	@Autowired
	TrafficQueryService trafficQueryservice;
	@Autowired
	CustomerCentreService customerCentreService;
	
	
	 //跳转到企业账户查询
	@RequestMapping(value = "/customerList")
	public String conpanyList() {
		return "feeSplit/customerList";
	}

	//跳转到下属企业列表页面
	@RequestMapping(value = "/queryList/{id}")
	public ModelAndView conpanyList(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("feeSplit/enterpriseList");
		mv.addObject("custId", id);
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/toAccountList")
	public Map<String, Object> roleList(HttpServletRequest req, HttpServletResponse resp,long custId) {
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		LaoCustomerDto dto = new LaoCustomerDto();
		dto.setCustId(custId);
		Map<String, Object> resultMap = customerService.feeQueryPage(dto, pageNo, pageSize);
		return resultMap;
	}


	   //跳转到企业收支查询
		@RequestMapping(value = "/incomeList")
		public String incomeList() {
			return "feeSplit/incomeList";
		}
		
		 //跳转到充值审核页面
		@RequestMapping(value = "/rechargeList")
		public String rechargeList() {
			return "feeSplit/rechargeList";
		}
	
		 //跳转到出账提现审核页面
		@RequestMapping(value = "/checkOutList")
		public String checkOutList() {
			return "feeSplit/checkOutList";
		}
		
		 //跳转到提现申请审核页面
		@RequestMapping(value = "/cashCheckList")
		public String cashCheckList() {
			return "feeSplit/cashCheckList";
		}
	
		//确认是否通过审核
		@RequestMapping(value = "/toConfirm")
		@ResponseBody
		public ResultMsg toConfirm(String accessId) {
			ResultMsg msg = new ResultMsg();
			//根据 id去充值日志表中查找对应的记录，把Lao_b_accesslog中的CANCEL_TAG 改为1已审核 
			Boolean resulet = false;
			msg.setSuccess(resulet);
			return msg;
		}
		
		
		
		//企业收支查询列表
		@SuppressWarnings("deprecation")
		@ResponseBody
		@RequestMapping(value = "/incomeQuerryList")
		public Map<String, Object> queryList(HttpServletRequest req, HttpServletResponse resp) {
			LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
			int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// 开始显示的项
			int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// 显示多少项
			int start = pageStart + 1;
			int end = pageSize + pageStart;
			String transType = req.getParameter("transType");
			
			//时间先不考虑
			/*String startTimeRes = req.getParameter("start");
			String endTimeRes = req.getParameter("end");
			if (!startTimeRes.equals("")) {
				Date startTime = new Date(startTimeRes);
				paraMap.put("startTime", startTime);
			}
			if (!endTimeRes.equals("")) {
				Date endTime = new Date(endTimeRes);
				endTime.setHours(23);
				endTime.setMinutes(59);
				endTime.setSeconds(59);
				paraMap.put("endTime", endTime);
			}
			paraMap.put("start", start);
			paraMap.put("end", end);
			*/
			Map<String, Object> resultMap =customerCentreService.queryPage(user.getCustId(), pageStart, pageSize, transType);
			return resultMap;
		}
	

		
		
	@RequestMapping("/toDetail/{accessId}")
	public ModelAndView toDetail(@PathVariable("accessId") Long accessId) {
		ModelAndView mv = new ModelAndView("feeSplit/incomeDetail");
		LaoBAccesslogDto  laoBAccesslogDto= customerCentreService.selectByPrimaryKey(accessId);
		mv.addObject("laoBAccesslogDto", laoBAccesslogDto);
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		mv.addObject("busiLicenceValidDateStr", sdf.format(custGroupDto.getBusiLicenceValidDate()));
		List<LaoSsAccountDto> accounts = laoSsAccountService.queryByCustId(id);
		StringBuffer sb = new StringBuffer();
		for (LaoSsAccountDto dto : accounts) {
			sb.append(dto.getAcconutId().toString() + ",");
		}
		if (sb != null && sb.length() > 0) {
			mv.addObject("ids", sb.toString().substring(0, sb.toString().length() - 1));
		} else {
			mv.addObject("ids", "");
		}*/
		return mv;
	}
	
	

	

	private void selectByPrimaryKey(Long accessId) {
		// TODO Auto-generated method stub
		
	}

	@ResponseBody
	@RequestMapping(value = "/save")
	public ResultMsg save(LaoCustomerDto customerDto, LaoCustGroupDto custGroupDto, HttpServletRequest request,
			String busiLicenceValidDateStr,String dev,String devRV) {

		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		Long id = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			custGroupDto.setBusiLicenceValidDate(sdf.parse(busiLicenceValidDateStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		customerDto.setCustType("1");
		if (customerDto.getCustId() != null) {
			/*
			 * if(accountIds!=null){ String[] ids=accountIds.split(","); for(int
			 * i=0;i<ids.length;i++){
			 * laoSsAccountService.updateAccountCustId(Long.valueOf(ids[i]),
			 * customerDto.getCustId()); } }
			 */
			customerService.update(customerDto);
			custGroupService.update(custGroupDto);
		} else {
			/*
			 * if(accountIds!=null){ String[] ids=accountIds.split(","); for(int
			 * i=0;i<ids.length;i++){
			 * laoSsAccountService.updateAccountCustId(Long.valueOf(ids[i]),
			 * id); } }
			 */
			customerDto.setCustId(id);
			customerDto.setInDate(new Date());
			customerDto.setInStaffId(user.getAcconutId().toString());
			// Long parentId = user.getCustId();//TODO
			// customerDto.setParentId(parentId);
			custGroupDto.setSellType(custGroupDto.getSellType());
			if (custGroupDto.getSellType().equals("2")) {
				customerDto.setDevCust(Long.valueOf(dev));
				customerDto.setRsrvStr1(devRV);
			} else if(custGroupDto.getSellType().equals("1")){
//				customerDto.setDevAct(user.getAcconutId());
				customerDto.setRsrvStr1(dev);
				customerDto.setDevCust(1L);
			}else{
//				customerDto.setDevAct(user.getAcconutId());
//				customerDto.setDevCust(null);
				customerDto.setRsrvStr1(dev);
				customerDto.setDevCust(1L);
			}		
			customerService.save(customerDto);
			custGroupDto.setCustId(id);
			custGroupService.save(custGroupDto);
		}
		ResultMsg msg = new ResultMsg();
		msg.setSuccess(true);
		msg.setObjData(id);
		return msg;
	}

	
}
