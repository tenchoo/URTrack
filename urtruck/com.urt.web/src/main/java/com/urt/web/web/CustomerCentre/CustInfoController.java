package com.urt.web.web.CustomerCentre;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.urt.dto.LaoCustomerDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Goods.CustomerDto;
import com.urt.dto.Remain.LaoBAccesslogDto;
import com.urt.dto.Remain.LaoFAcctdepositDto;
import com.urt.dto.custcentre.LaoCustomerAccountDto;
import com.urt.interfaces.CustomerCentre.CustomerCentreService;
import com.urt.interfaces.CustomerCentre.CustomerQueryService;
import com.urt.interfaces.customer.LaoCustomerService;
import com.urt.interfaces.remain.RemainService;
import com.urt.web.web.remain.RemainController;

@Controller
@RequestMapping("/customer")
public class CustInfoController {
      
	@Autowired
	private LaoCustomerService laoCustomerService;
	
	@Autowired
	private  LaoCustomerService custoemrService;
	
	@Autowired
	private  CustomerCentreService  centre;
	
	@Autowired
	private  CustomerQueryService  queryService;
	
	@Autowired
	private RemainService remainService;
	
	@Autowired
	
	private static Logger logger = LoggerFactory
			.getLogger(RemainController.class);
	
	@RequestMapping(value = "/manageinfo")
	public ModelAndView manageInfo(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("/newcust/customerinfo");
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		// 客户信息
		LaoCustomerDto laoCustomerDto = laoCustomerService.selectDtoById(user.getCustId());
		
		mv.addObject("custInfo",laoCustomerDto);
		return mv;
	}
	
	@RequestMapping(value = "/recharge")
	public ModelAndView recharge(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("/newcust/recharge");
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		/*// 客户信息
		LaoCustomerDto laoCustomerDto = laoCustomerService.selectDtoById(user.getCustId());
		
		mv.addObject("custInfo",laoCustomerDto);*/
		return mv;
	}
	

	
	@RequestMapping(value = "/accountoverview")
	public ModelAndView accountOverview(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("/newcust/accountOverview");
		
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		
	    // 账户信息
	 	LaoFAcctdepositDto ldto = remainService.selectdepositByCustId(user.getCustId());
	 	

		Map<String, Object>  monthMoney=centre.queryCurrentMonthSumitMoneyByCustId(user.getCustId());
	
        mv.addObject("surplusMoney", ldto.getDepositMoney());
        
        mv.addObject("currentMonthSubmitMoney",monthMoney.get("currentMonthSubmitMoney"));
		mv.addObject("currentMonthReturnMoney",monthMoney.get("currentMonthSubmitMoney"));
		
		return mv;
	}
	
	
	@RequestMapping(value = "/accountmanage")
	public ModelAndView accountManage(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("/newcust/accountmanage");
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		// 客户信息
		LaoCustomerDto laoCustomerDto = laoCustomerService.selectDtoById(user.getCustId());
			
		mv.addObject("custInfo",laoCustomerDto);
		
		return mv;
	}
	
	
	
	@RequestMapping(value = "addcuststyle", method = { RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public boolean accountManage(HttpServletRequest request, HttpServletResponse response) {
		String style = request.getParameter("lang");
		String welcomeLanguage = request.getParameter("welcomeLanguage");
		String pic = request.getParameter("pic");
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		
		// 客户信息
		LaoCustomerDto laoCustomerDto = laoCustomerService.selectDtoById(user.getCustId());
	    boolean saveCustStye=custoemrService.saveStyle(welcomeLanguage,style,pic,laoCustomerDto.getCustId(),laoCustomerDto.getCustName());
		
		return saveCustStye;
	}
	
	
	@RequestMapping(value = "addAccount", method = { RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public boolean addCustomerAccount(HttpServletRequest request,@ModelAttribute("LaoCustomerAccount") LaoCustomerAccountDto accountDto) {
		
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		
		// 客户信息
		LaoCustomerDto laoCustomerDto = laoCustomerService.selectDtoById(user.getCustId());
		
	    boolean result	=custoemrService.saveAccount(accountDto);
		
		return result;
	}
	
	/**
	 * 充值提交
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "rechargeSbmit", method = { RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public  Map<String, Object> rechargeSbmit(HttpServletRequest request, HttpServletResponse response){
		  
		 Map<String, Object>  resultMap= new HashMap<String,Object>();
 		
		
		 //充值类型   1 线上充值  2线下充值
		 String rechargeType = request.getParameter("rechargeType");
		 //线上支付方式     1 支付宝  2 微信  3 银联 
		 String rechargeType1_Type="";
		 //提交的充值金额
		// String money = ;
		 
		 //处理提交的金额
		 long   moneyVal= Long.valueOf(request.getParameter("money"));
		 
		 
		 LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		 
		 
		 resultMap=centre.offLineReserveRecharge(rechargeType,moneyVal*1000L,user);
		 
		 
		
		return resultMap;
	}
	
	/**
	 * 充值 提现列表记录列表查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/rechargeRecordList")
	@ResponseBody
	public Map<String, Object> rechargeRecordList(HttpServletRequest request, HttpServletResponse response) {
		int pageStart = Integer.parseInt(request.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(request.getParameter("iDisplayLength").toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;
      
		String custId = request.getParameter("custId");
		//区分是提现的请求列表  还是 充值的请求列表
		String  pageType=request.getParameter("pagetype");
		
		LaoBAccesslogDto dto = new LaoBAccesslogDto();
		dto.setChannelCustId(Long.valueOf(custId));
		dto.setAccessTag("".equals(pageType) ? "0":"3");
		// 分页显示
		Map<String, Object> pageData = queryService.queryPageList(dto, pageNo, pageSize);

		return pageData;
	} 
	

}
