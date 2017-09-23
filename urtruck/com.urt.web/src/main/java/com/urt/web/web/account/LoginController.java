package com.urt.web.web.account;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.urt.common.util.ResultMsg;
import com.urt.constant.SysConstants;
import com.urt.dto.LaoAccountRelDto;
import com.urt.dto.LaoCustomerDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoSsLoginLogDto;
import com.urt.dto.LaoSsNavigationDto;
import com.urt.dto.LaoSsRoleDto;
import com.urt.interfaces.authority.LaoAccountRelService;
import com.urt.interfaces.authority.LaoSsAccountService;
import com.urt.interfaces.authority.LaoSsRoleService;
import com.urt.interfaces.customer.LaoCustomerService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
import com.urt.web.util.LenovoLoginUtil;
import com.urt.web.util.WeixinUtil;
import com.urt.web.web.auth.CaptchaException;
import com.urt.web.web.auth.valdateCode.CaptchaServlet;

/**
 * 类说明：用户登录controller
 * 
 * @author cuichao
 * @date 2016年6月22日 下午12:48:50
 */
@Controller("loginController")
@RequestMapping(value = "/login")
public class LoginController {
	private static Logger logger = LoggerFactory
			.getLogger(LoginController.class);

	@Value("#{configProperties['passort.login.url']}")
	private String lenovoUrl;

	@Autowired
	private LaoSsRoleService laoSsRoleService;
	@Autowired
	private LaoSsAccountService accountService;
	@Autowired
	private LaoCustomerService customerService;
	@Autowired
	private LaoAccountRelService relService;
	
	@Value("#{configProperties['lenovoid.realm']}")
	private String realm;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("lenovoUrl", lenovoUrl);
		return mv;
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome() {
		return "ui3/platform";
	}
	
	@RequestMapping(value="/glanew/index", method = RequestMethod.GET)
	public String glaNewIndex() {
		return "glanew/index";
	}
	
	@RequestMapping(value = "/toIndex", method = RequestMethod.GET)
	public String toIndex(HttpSession httpSession, Model model,String url,String title,HttpServletRequest request) {
		LaoSsAccountDto user = (LaoSsAccountDto) httpSession
				.getAttribute("sessionUser");
		List<LaoSsRoleDto> roles = laoSsRoleService.queryRoleListByAccountId(user
				.getAcconutId());
		user.setRoles(roles);
		logger.debug("当前登录用户： " + user.getLoginName());
		if(1==roles.get(0).getRoleId()){
			url="/login/glanew/index";
		}else if(2==roles.get(0).getRoleId()){
			/*url="/corporate/corporateIndex";*/
		}else if(3==roles.get(0).getRoleId()){
			url="/personal/index";
		}else if(5==roles.get(0).getRoleId()){
			//渠道客户首页
			url="/custIndex/channelCustIndex";
		} else if(100==roles.get(0).getRoleId()){
			url="/nioIndex/admin";
		}else if(101==roles.get(0).getRoleId()){
			url="/nioIndex/companyManager";
		}else if(102==roles.get(0).getRoleId()){
			url="/nioIndex/vehicle";
		}else if(103==roles.get(0).getRoleId()){
			url="/nioIndex/chargingPile";
		}else if(104==roles.get(0).getRoleId()){
			url="/nioIndex/enterpriseCustomer";
		}
		if("companycustom".equals(roles.get(0).getRoleName())){
			url="/corporate/corporateIndex";
		}
		// 一级菜单
		List<LaoSsNavigationDto> navList1 = Lists.newArrayList();
		// 二级菜单
		List<LaoSsNavigationDto> navList2 = Lists.newArrayList();
		//首页
		LaoSsNavigationDto indexNav = null;
		
		if (CollectionUtils.isNotEmpty(user.getNavigations())) {
			for (LaoSsNavigationDto tfFSsNavigationDto : user.getNavigations()) {
				if ("1".equals(tfFSsNavigationDto.getUrlLevel())) {
					navList1.add(tfFSsNavigationDto);
				}
				if ("2".equals(tfFSsNavigationDto.getUrlLevel())) {
					navList2.add(tfFSsNavigationDto);
				}
			}
			boolean flag=true;
			// 向一级菜单加入对应二级菜单
			String urlStr="";
			
			for (int i=0;i<navList1.size();i++) {
				LaoSsNavigationDto dto1=navList1.get(i);
				if(SysConstants.INDEX_NAV_ID.longValue() == dto1.getNavigationId().longValue()){
					if(url!=null){
						dto1.setUrl(url);
					}
					indexNav = dto1;
				}
				
				if (CollectionUtils.isEmpty(dto1.getNavigationList())) {
					
					for (LaoSsNavigationDto dto2 : navList2) {
						
						if (dto2.getParentId().equals(dto1.getNavigationId())) {
							dto1.getNavigationList().add(dto2);
							if(flag){
								urlStr="/"+dto2.getUrl();
								flag=false;
							}
						}
					}
				}
			}
			if(url==null){
				/*url="/"+navList1.get(0).getNavigationList().get(0).getUrl();*/
				url=urlStr;
			}
			if(title==null){
				if(navList1.get(0).getNavigationList()!=null && navList1.get(0).getNavigationList().size()>0){
					title=navList1.get(0).getNavigationList().get(0).getNavName();
				}else{
					title=navList1.get(0).getNavName();
				}
			}
		}
		LaoSsLoginLogDto loginLogDto=new LaoSsLoginLogDto();
		loginLogDto.setAccountId(user.getAcconutId());
		loginLogDto.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
		loginLogDto.setIp(WeixinUtil.getIpAddr(request));
		loginLogDto.setLoginTime(new Date());
		loginLogDto.setSessionId(httpSession.getId());
		laoSsRoleService.addLoginLog(loginLogDto);
		model.addAttribute("user", user);
		if (indexNav != null)
		{
			// 首页单独传递
			navList1.remove(indexNav);
		}
		
		model.addAttribute("navList", JSONArray.toJSON(navList1));
		model.addAttribute("resList", user.getResources());
		model.addAttribute("url",url);
		model.addAttribute("title",title);
		model.addAttribute("indexNav", indexNav);
		//return "ui3/template";
		return "glanew/home";
	}

	
	/**
	 * 功能描述：当shiro完成用户验证并成功后，开始准备首页数据
	 * 
	 * @author cuichao
	 * @date 2016年6月27日 下午3:20:40
	 * @param @param httpSession
	 * @param @param model
	 * @param @return
	 * @return String
	 */
	@RequestMapping(value = "/loginSuccess", method = RequestMethod.GET)
	public String loginSuccess(HttpSession httpSession, Model model,String url,String title,HttpServletRequest request) {
		/*LaoSsAccountDto user = (LaoSsAccountDto) httpSession
				.getAttribute("sessionUser");
		List<LaoSsRoleDto> roles = laoSsRoleService.queryRoleListByAccountId(user
				.getAcconutId());
		user.setRoles(roles);
		logger.debug("当前登录用户： " + user.getLoginName());
		if(1==roles.get(0).getRoleId()){
			url="/userTotalService/userDataList";
		}else if(2==roles.get(0).getRoleId()){
			url="/corporate/corporateIndex";
		}else if(3==roles.get(0).getRoleId()){
			url="/personal/index";
		}else if(5==roles.get(0).getRoleId()){
			//渠道客户首页
			url="/custIndex/channelCustIndex";
		} 
		// 一级菜单
		List<LaoSsNavigationDto> navList1 = Lists.newArrayList();
		// 二级菜单
		List<LaoSsNavigationDto> navList2 = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(user.getNavigations())) {
			for (LaoSsNavigationDto tfFSsNavigationDto : user.getNavigations()) {
				if ("1".equals(tfFSsNavigationDto.getUrlLevel())) {
					navList1.add(tfFSsNavigationDto);
				}
				if ("2".equals(tfFSsNavigationDto.getUrlLevel())) {
					navList2.add(tfFSsNavigationDto);
				}
			}
			boolean flag=true;
			// 向一级菜单加入对应二级菜单
			String urlStr="";
			for (int i=0;i<navList1.size();i++) {
				LaoSsNavigationDto dto1=navList1.get(i);
				if("首页".equals(dto1.getNavName())){
					if(url!=null){
						dto1.setUrl(url);
						navList1.add(i, dto1);
					}
				}
				
				if (CollectionUtils.isEmpty(dto1.getNavigationList())) {
					
					for (LaoSsNavigationDto dto2 : navList2) {
						
						if (dto2.getParentId().equals(dto1.getNavigationId())) {
							dto1.getNavigationList().add(dto2);
							if(flag){
								urlStr="/"+dto2.getUrl();
								flag=false;
							}
						}
					}
				}
			}
			if(url==null){
				url="/"+navList1.get(0).getNavigationList().get(0).getUrl();
				url=urlStr;
			}
			if(title==null){
				if(navList1.get(0).getNavigationList()!=null && navList1.get(0).getNavigationList().size()>0){
					title=navList1.get(0).getNavigationList().get(0).getNavName();
				}else{
					title=navList1.get(0).getNavName();
				}
			}
		}
		LaoSsLoginLogDto loginLogDto=new LaoSsLoginLogDto();
		loginLogDto.setAccountId(user.getAcconutId());
		loginLogDto.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
		loginLogDto.setIp(WeixinUtil.getIpAddr(request));
		loginLogDto.setLoginTime(new Date());
		loginLogDto.setSessionId(httpSession.getId());
		laoSsRoleService.addLoginLog(loginLogDto);
		model.addAttribute("user", user);
		model.addAttribute("navList", JSONArray.toJSON(navList1));
		model.addAttribute("resList", user.getResources());
		model.addAttribute("url",url);
		model.addAttribute("title",title);
		return "ui3/template";*/
		return "redirect:/login/toIndex";
		//return "ui3/platform";
	}

	/**
	 * 用户登出
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		SecurityUtils.getSubject().logout();
		request.getSession().invalidate();
		return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";
	}

	/**
	 * 功能描述：验证失败，回到登录页
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String fail(
			@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName,@RequestParam(FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM) String password,
			Model model, HttpServletRequest req) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM,
				userName);
		String exceptionClassName = (String) req
				.getAttribute("shiroLoginFailure");
		String error = null;
		if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
			error = "用户名不存在";
		} else if (IncorrectCredentialsException.class.getName().equals(
				exceptionClassName)) {
			error = "用户名/密码错误";
		} else if (CaptchaException.class.getName().equals(exceptionClassName)) {
			error = "验证码错误";
		} 
		else if (DisabledAccountException.class.getName().equals(exceptionClassName)) {
			error = "账号已冻结";
		}
		else {
			error = "其他错误";
		}
		if(error.equals("用户名不存在")){
			System.out.println(">>>>>>>>>>>>>>>>>");
			String loginXml = LenovoLoginUtil.login(realm, userName, password, getIpAddr(req));
			if(loginXml != null){
				String accountID = StringUtils.substringBetween(loginXml, "<Userid>","</Userid>");
				String lpsust = StringUtils.substringBetween(loginXml, "<Value>","</Value>");
				String errorCode = StringUtils.substringBetween(loginXml, "<Error>","</Error>");
				String code = StringUtils.substringBetween(loginXml, "<Code>","</Code>");
				if(accountID != null && lpsust != null){
					LaoSsAccountDto laoSsAccountDto = new LaoSsAccountDto();
					Long userId = Long.valueOf(ZkGenerateSeq
							.getIdSeq(SeqID.SYSUSER_ID));
					laoSsAccountDto.setLoginName(userName);
					laoSsAccountDto.setPassword(password);
					laoSsAccountDto.setPlainPassword(password);
					Random random = new Random();
					laoSsAccountDto.setNickname("gla"
							+ random.nextInt(999999));
					laoSsAccountDto.setAcconutId(userId);
					accountService.saveCommonUser(laoSsAccountDto);
					LaoCustomerDto dto = new LaoCustomerDto();
					long custId = customerService.initCustInfo(dto);
					// 编辑用户绑定的客户id
					accountService.updateCustId(laoSsAccountDto,
							custId);
					// 生成绑定信息
					Long id = Long.valueOf(ZkGenerateSeq
							.getIdSeq(SeqID.SYSUSER_ID));
					LaoAccountRelDto relDto = new LaoAccountRelDto();
					relDto.setRelType("1000");
					relDto.setRelAccount(accountID);
					relDto.setAccountId(userId);
					relDto.setRecvTime(new Date());
					relDto.setRelId(id);
					relDto.setRelLoginname(userName);
					relDto.setRelNickname(laoSsAccountDto.getNickname());
					relService.save(relDto);
					AuthenticationToken token=new UsernamePasswordToken(userName,password);
					SecurityUtils.getSubject().login(token);
					return "redirect:/login/loginSuccess";
				}else if(error != null && code != null){
					if(code.equals("USS-0103")) error="无此帐号";
					if(code.equals("USS-0101")) error="密码错误";
					if(code.equals("USS-0111")) error="帐号被disable";
					if(code.equals("USS-0135")) error="无效的请求参数";
					if(code.equals("USS-0151")) error="帐号多次登录失败，会被锁定，需要稍后再登录尝试";
					if(code.equals("USS-0121")) error="无效的realm";
					if(code.equals("USS-0123")) error="该服务禁止访问";
				}
			}
		}
		model.addAttribute("error", error);
		return "login";
	}

	public String getLenovoUrl() {
		return lenovoUrl;
	}

	public void setLenovoUrl(String lenovoUrl) {
		this.lenovoUrl = lenovoUrl;
	}

	/**
	 * 功能描述：首页
	 */
	@RequestMapping("/welcome/index")
	public String index(HttpServletRequest request) {
		return "index1";
	}
	@ResponseBody
	@RequestMapping("/checkCaptcha")
	public ResultMsg checkCaptcha(String captcha) {
		ResultMsg resultMsg=new ResultMsg();
		resultMsg.setSuccess(true);
		String exitCode = (String) SecurityUtils.getSubject().getSession()
				.getAttribute(CaptchaServlet.KEY_CAPTCHA);
		Random random = new Random();
		SecurityUtils.getSubject().getSession()
		.setAttribute(CaptchaServlet.KEY_CAPTCHA,random.nextInt(9));
		if(null == captcha || !captcha.equalsIgnoreCase(exitCode)){
			resultMsg.setSuccess(false);
		}else{
			resultMsg.setSuccess(true);
		}
		return resultMsg;
	}
	public String getIpAddr(HttpServletRequest request) {

		String ip = request.getHeader("Cdn-Src-Ip");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("http_client_ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		// 如果是多级代理，那么取第一个ip为客户ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}
		if (ip.indexOf(",") > -1) {
			ip = ip.substring(0, ip.indexOf(","));
		}
		return ip;
	}
}
