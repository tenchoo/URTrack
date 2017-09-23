package com.urt.web.web.ability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.urt.common.util.ResultMsg;
import com.urt.dto.LaoCustomerDto;
import com.urt.dto.LaoProvideServerDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoUserIpManagerDto;
import com.urt.interfaces.ability.LaoAbilityService;
import com.urt.interfaces.ability.LaoUserIpManagerService;
import com.urt.interfaces.authority.LaoSsAccountService;
import com.urt.interfaces.customer.LaoCustomerService;
import com.urt.interfaces.http.AppkeyAndIpCreate;
import com.urt.web.common.util.ActionUtil;

/**
 * 
 * 能力开放
 *
 */
@Controller
@RequestMapping(value = "/abilityOpen")
public class AbilityOpenController {

	protected static final Logger logger = Logger.getLogger(AbilityOpenController.class);
	@Autowired
	private AppkeyAndIpCreate appkeyAndIpCreate;
	@Autowired
	private LaoUserIpManagerService laoUserIpManagerService;
	@Autowired
	private LaoAbilityService laoAbilityService;

	@Autowired
	private LaoCustomerService laoCustomerService;

	@RequestMapping(value = "/abilityConfiguration")
	public ModelAndView aBilityConfiguration(HttpServletRequest request) {
		// admin页面
		ModelAndView mv = new ModelAndView("/abilityopen/adminConfiguration");
		boolean ifSuperUser = ActionUtil.ifSuperUser(request);
		if (ifSuperUser) {
			return mv;
		}
		mv = new ModelAndView("/abilityopen/abilityConfiguration");

		return mv;
	}

	@RequestMapping(value = "/abilityShiro")
	public ModelAndView abilityShiro(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/abilityopen/abilityShiro");
		return mv;
	}

	@RequestMapping(value = "/addipaddress")
	public ModelAndView addipaddress(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/abilityopen/addIpaddress");
		return mv;
	}

	/**
	 * 生成Appkey
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/createAppKey")
	@ResponseBody
	public String createAppKey(String custId) {
		Map<String, String> reqInfo = new HashMap<String, String>();
		reqInfo.put("custId", custId);
		String appkey = appkeyAndIpCreate.getAppkey(reqInfo);
		logger.info("*******生成AppKeycreateAppKey*****************" + appkey);
		return appkey;
	}
	/**
	 * 查询Appkey
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/queryAppKey")
	@ResponseBody
	public String queryAppKey(String custId,HttpServletRequest request) {
		Map<String, String> reqInfo = new HashMap<String, String>();
		reqInfo.put("custId", custId);
		String appkey = appkeyAndIpCreate.queryAppkey(reqInfo);
		logger.info("*******查询到的AppKeycreateAppKey*****************" + appkey);
		return appkey;
	}

	/**
	 * Ip列表展示
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ipAddRessList")
	@ResponseBody
	public Map<String, Object> ipAddRessList(HttpServletRequest request, HttpServletResponse response) {
		int pageStart = Integer.parseInt(request.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(request.getParameter("iDisplayLength").toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;

		String custId = request.getParameter("custId");
		LaoUserIpManagerDto dto = new LaoUserIpManagerDto();
		dto.setCustId(Long.valueOf(custId));
		// 分页显示
		Map<String, Object> pageData = laoUserIpManagerService.queryPage(dto, pageNo, pageSize);

		return pageData;
	}

	@RequestMapping(value = "/custList")
	@ResponseBody
	public Map<String, Object> custList(HttpServletRequest request, HttpServletResponse response, String custName) {
		int pageStart = Integer.parseInt(request.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(request.getParameter("iDisplayLength").toString());// 显示多少项

		int start = pageStart + 1;
		int end = pageSize + pageStart;
		Map<String, Object> pageData = laoAbilityService.queryPage(start, end, custName);

		return pageData;
	}

	@RequestMapping("/toUpdate/{id}")
	public ModelAndView toUpdate(@PathVariable("id") Long id) {

		ModelAndView mv = new ModelAndView("/abilityopen/update");
		// 客户对象
		LaoCustomerDto customer = laoCustomerService.selectDtoById(id);

		// 根据CustId,查询该客户对应的接口权限
		List<LaoProvideServerDto> dtos = laoAbilityService.queryPrivateServer(id);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dtos.size(); i++) {
			sb.append(dtos.get(i).getServerId().toString() + ",");
		}
		String str = sb.toString();
		// 查询所有的接口
		List<LaoProvideServerDto> privideServer = laoAbilityService.getPrivideServer();
		for (int i = 0; i < privideServer.size(); i++) {
			LaoProvideServerDto laoProvideServerDto = privideServer.get(i);
			if (str.indexOf(laoProvideServerDto.getServerId().toString()) >= 0) {
				laoProvideServerDto.setCheck(true);
			} else {
				laoProvideServerDto.setCheck(false);
			}
		}
		mv.addObject("customer", customer);
		mv.addObject("vBeans", privideServer);
		mv.addObject("custList", dtos);
		return mv;
	}

	@RequestMapping("/updateServer")
	@ResponseBody
	public ResultMsg updateServer(String serverIds, String custId) {
		ResultMsg msg = new ResultMsg();
		Boolean resulet = laoAbilityService.updateServer(serverIds, custId);
		msg.setSuccess(resulet);
		return msg;
	}

	@RequestMapping("/toAdd")
	public ModelAndView toAdd(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/abilityopen/addCustAbility");
		List<LaoProvideServerDto> privideServer = laoAbilityService.getPrivideServer();
		mv.addObject("vBeans", privideServer);
		return mv;
	}

	@RequestMapping(value = "/addCustServer")
	@ResponseBody
	public ResultMsg addCustServer(String custId, String serverIds) {
		ResultMsg msg = new ResultMsg();
		Boolean resulet = laoAbilityService.inSertIntoServer(custId, serverIds);
		msg.setSuccess(resulet);
		return msg;
	}

	@RequestMapping(value = "/delIP")
	@ResponseBody
	public ResultMsg delIP(String id) {
		ResultMsg msg = new ResultMsg();
		Boolean resulet = laoUserIpManagerService.delUserIP(id);
		msg.setSuccess(resulet);
		return msg;
	}

	@RequestMapping(value = "/stopIP")
	@ResponseBody
	public ResultMsg stopIP(String id) {
		ResultMsg msg = new ResultMsg();
		Boolean resulet = laoUserIpManagerService.stopIP(id);
		msg.setSuccess(resulet);
		return msg;
	}

	@RequestMapping(value = "/openIP")
	@ResponseBody
	public ResultMsg openIP(String id) {
		ResultMsg msg = new ResultMsg();
		Boolean resulet = laoUserIpManagerService.openIP(id);
		msg.setSuccess(resulet);
		return msg;
	}

	@RequestMapping(value = "/saveIp")
	@ResponseBody
	public ResultMsg saveIp(String custId, String ip) {
		ResultMsg msg = new ResultMsg();

		Boolean resulet = laoUserIpManagerService.saveIp(Long.valueOf(custId), ip);
		msg.setSuccess(resulet);
		return msg;
	}
	
	@RequestMapping(value = "/appKeyList")
	@ResponseBody
	public Map<String, Object> appKeyList(HttpServletRequest request,String custName) {
		logger.info("请求参数>>>>>"+custName);
		Integer pageStart = Integer.valueOf(request.getParameter("iDisplayStart").toString());
		Integer pageSize = Integer.valueOf(request.getParameter("iDisplayLength").toString());
		int start = pageStart + 1;
		int end = pageStart + pageSize;
		Map<String, Object> pageData = appkeyAndIpCreate.appKeyList(start, end,custName);
		logger.info("分页数据>>>>>"+pageData.toString());
		return pageData;
	}

}
