package com.urt.web.web.account;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.lenovo.pay.utils.HttpUtil;
import com.urt.dto.LaoAccountRelDto;
import com.urt.dto.LaoCustomerDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.interfaces.authority.LaoAccountRelService;
import com.urt.interfaces.authority.LaoSsAccountService;
import com.urt.interfaces.customer.LaoCustomerService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
import com.urt.web.common.util.AccountCookie;

/**
 * 类说明：登陆验证跳转回来
 * 
 * @author haosun1
 * @date 2016年8月19日15:32:39
 */
@Controller
@RequestMapping("/account")
public class CallbackController {
	public static final int HASH_INTERATIONS = 1024;
	private static Logger logger = LoggerFactory.getLogger(CallbackController.class);
	private static final String UILOGIN = "uilogin";
	private static final String UILOGOUT = "uilogout";
	private static final String WUST = "lenovoid.wust";
	private static final String REALM = "lenovoid.realm";
	private static final String ACTION = "lenovoid.action";
	private static final String CTX = "lenovoid.ctx";
	//private static final boolean String = false;
	@Value("#{configProperties['passort.auth.url']}")
	private String authUrl ;
	
	@Value("#{configProperties['h5Url']}")
	private String h5Filter ;
	private Account account;
	@Autowired
	private LaoAccountRelService relService;
	@Autowired
	LaoSsAccountService accountService;
	@Autowired
	LaoCustomerService customerService;
	
	@RequestMapping("/callback")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/DongguanCMC/index");

		String wust = request.getParameter(WUST);
		String realm = request.getParameter(REALM);
		String action = request.getParameter(ACTION);
		String ctx =request.getParameter(CTX);
		if (StringUtils.isNotBlank(action)) {
			if (action.equals(UILOGIN)) {
				String xml = HttpUtil.sendGetHttps(authUrl + "lpsust=" + wust
						+ "&realm=" + realm);
				account = this.parseXML2Account(xml);
				String[] hfilter =h5Filter.split(",");
				
				if(account != null){
					request.getSession().setAttribute("lenovoid", account.getAccountID());
					request.getSession().setAttribute("username", account.getUsername());
				}
				request.getSession().setAttribute("lpsust", wust);
				// 小董上网web登录
				if (ctx.endsWith("/customerQueryWeb/loginSuccess")) {
					ModelAndView mandv = new ModelAndView("redirect:"+ctx);
					mandv.addObject("st", wust);
					return mandv;
				}
				for (String  str:hfilter){
					if (ctx!=null && ctx.indexOf(str)>0) {
						AccountCookie ac = new AccountCookie(request, response);
						ac.setUuid(account.getAccountID());
						ac.setUname(account.getUsername());
						ac.setActiveTime(System.currentTimeMillis());
						ac.setCookieAdmin(ac);
						
						//与gla绑定信息
						LaoAccountRelDto relDto=new LaoAccountRelDto();
						relDto.setRelType("1000");
						relDto.setRelAccount(account.getAccountID());
						List<LaoAccountRelDto> rels = relService.queryRelByRelType(relDto);
						if(rels==null || rels.size()<=0){
							// 生成平台用户，客户信息
							Random random=new Random();
							LaoSsAccountDto laoSsAccountDto = new LaoSsAccountDto();
							Long userId = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
							laoSsAccountDto.setLoginName(account.getUsername());
							laoSsAccountDto.setPassword("123456");
							/*laoSsAccountDto.setRelatedId(account.AccountID);*/
							laoSsAccountDto.setPlainPassword("123456");
							laoSsAccountDto.setNickname("gla"+ random.nextInt(999999));
							laoSsAccountDto.setAcconutId(userId);
							accountService.saveCommonUser(laoSsAccountDto);
							LaoCustomerDto dto = new LaoCustomerDto();
							long custId = customerService.initCustInfo(dto);
							// 编辑用户绑定的客户id
							accountService.updateCustId(laoSsAccountDto,custId);
							//生成绑定信息
							Long id = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
							relDto.setAccountId(userId);
							relDto.setRecvTime(new Date());
							relDto.setRelId(id);
							relService.save(relDto);
							
							AuthenticationToken token=new UsernamePasswordToken("lixue11","lx123456");
							SecurityUtils.getSubject().login(token);
						}
						//存储session
						LaoSsAccountDto accountDto = accountService.getUserByLoginName(account.getUsername());
						request.getSession().setAttribute("sessionUser", accountDto);
						return new ModelAndView("redirect:"+ctx);
					}
				}
				//判断是否绑定联想账号
				/*LaoSsAccountDto laoSsAccountDto=accountService.getUserByRelared(account.AccountID);*/
				LaoAccountRelDto relDto=new LaoAccountRelDto();
				relDto.setRelType("1000");
				relDto.setRelAccount(account.AccountID);
				List<LaoAccountRelDto> rels = relService.queryRelByRelType(relDto);
				if(rels!=null && rels.size()>0){
					Gson gson=new Gson();
					String json = gson.toJson(rels.get(0));
					LaoAccountRelDto rel = gson.fromJson(json, LaoAccountRelDto.class);
					LaoSsAccountDto laoSsAccountDto = accountService.queryAccountById(rel.getAccountId());
					if(laoSsAccountDto!=null){
						AuthenticationToken token=new UsernamePasswordToken("lixue11","lx123456");
						SecurityUtils.getSubject().login(token);
						/*SecurityUtils.getSubject().getSession().setAttribute("sessionUser", laoSsAccountDto);*/
						mv = new ModelAndView("redirect:/login/loginSuccess");
					}else{
						mv = new ModelAndView("redirect:/register/toRegister");
						mv.addObject("userName", account.getUsername());
						mv.addObject("relatedId", account.getAccountID());
					}
				}else{
					mv = new ModelAndView("redirect:/register/toRegister");
					mv.addObject("userName", account.getUsername());
					mv.addObject("relatedId", account.getAccountID());
					return mv;
				}
				
			} else if (action.equals(UILOGOUT)) {
				mv = new ModelAndView("index");
				return mv;
			}
		}

		return mv;
	}
	/*@RequestMapping("/callback")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/DongguanCMC/index");

		String wust = request.getParameter(WUST);
		String realm = request.getParameter(REALM);
		String action = request.getParameter(ACTION);
		String ctx = request.getParameter(CTX);
		if (StringUtils.isBlank(ctx)) {
			ctx = "/login/loginSuccess";
		}

		if (StringUtils.isNotBlank(action)) {
			if (action.equals(UILOGIN)) {
				String xml = HttpUtil.sendGetHttps(authUrl + "lpsust=" + wust
						+ "&realm=" + realm);
				account = this.parseXML2Account(xml);
				if (account != null) {
					AccountCookie ac = new AccountCookie(request, response);
					ac.setUuid(account.getAccountID());
					ac.setUname(account.getUsername());
					ac.setActiveTime(System.currentTimeMillis());
					// ac.setUserId(userId);
					ac.setCookieAdmin(ac);
					// BigDecimal amount =
					// userAccountService.getAccountAmount(ac.getUuid());
					request.setAttribute("uuid", ac.getUuid());
					request.setAttribute("uname", ac.getUname());
					request.getSession().setAttribute("lenovoid", account.getAccountID());
				}
			} else if (action.equals(UILOGOUT)) {
				CookieUtils.removeCookie(ServletActionContext.getResponse(),
						Constant.CASHIER_COOKIE_KEY);
				mv = new ModelAndView("index");
				return mv;
			}
		}

		return mv;
	}
*/
	public Account parseXML2Account(String xml) {
		Account account = null;
		// System.out.println(xml);
		String accountID = StringUtils.substringBetween(xml, "<AccountID>",
				"</AccountID>");
		String userName = StringUtils.substringBetween(xml, "<Username>",
				"</Username>");
		String deviceID = StringUtils.substringBetween(xml, "<DeviceID>",
				"</DeviceID>");
		String verified = StringUtils.substringBetween(xml, "<verified>",
				"</verified>");
		if (StringUtils.isNotBlank(accountID)
				&& StringUtils.isNotBlank(userName)) {
			account = new Account();
			account.setAccountID(accountID);
			account.setUname(userName);
			account.setUsername(userName);
			account.setDeviceID(deviceID);
			account.setVerified(verified);
		}
		return account;
	}
	
	/**
	 * 自定义Account对象，使得携带用户的登录名外还可以携带更多信息.
	 */
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	public static class Account implements Serializable {

		private static final long serialVersionUID = 2009047057323590514L;
		private String AccountID;
		private String Username;
		private String uname;
		private String verified;
		private String wust;
		private String idcLocation;
		private String amount;
		private String DeviceID;
		private String Thirdname;
		
		public String getAccountID() {
			return AccountID;
		}

		public void setAccountID(String accountID) {
			AccountID = accountID;
		}

		public String getUsername() {
			return Username;
		}

		public void setUsername(String username) {
			Username = username;
		}

		public String getVerified() {
			return verified;
		}

		public void setVerified(String verified) {
			this.verified = verified;
		}

		public String getWust() {
			return wust;
		}

		public String getIdcLocation() {
			return idcLocation;
		}

		public void setIdcLocation(String idcLocation) {
			this.idcLocation = idcLocation;
		}

		public String getDeviceID() {
			return DeviceID;
		}

		public void setDeviceID(String deviceID) {
			DeviceID = deviceID;
		}

		public void setWust(String wust) {
			this.wust = wust;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getUname() {
			return uname;
		}

		public void setUname(String uname) {
			this.uname = uname;
		}

		public String getThirdname() {
			return Thirdname;
		}

		public void setThirdname(String thirdname) {
			Thirdname = thirdname;
		}
	}

}
