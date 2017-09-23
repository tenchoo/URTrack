package com.urt.web.web.glaH5;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.lenovo.pay.core.bo.Constant;
import com.lenovo.pay.utils.CookieUtils;
import com.urt.common.util.ResultMsg;
import com.urt.dto.LaoAccountRelDto;
import com.urt.dto.LaoCustomerDto;
import com.urt.dto.LaoSmsInfoDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.interfaces.authority.LaoAccountRelService;
import com.urt.interfaces.authority.LaoSsAccountService;
import com.urt.interfaces.authority.LoginService;
import com.urt.interfaces.customer.LaoCustomerService;
import com.urt.interfaces.sendMessage.SendMessageService;
import com.urt.interfaces.unicom.DeviceService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
import com.urt.web.common.util.QRCode.TwoDimensionCode;
import com.urt.web.util.LenovoLoginUtil;
import com.urt.web.web.auth.CaptchaException;
import com.urt.web.web.auth.UsernamePasswordCaptchaToken;


/**
 * 类说明：主界面
 * 
 * @author sunhao
 * @date 2016年8月23日15:40:42
 */
@Controller
@RequestMapping("/glaH5App")
public class GlaH5AppController {
	private static  final Logger Log=Logger.getLogger(GlaH5AppController.class);
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private LaoSsAccountService accountService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private SendMessageService sendMessageService;
	
	@Value("#{configProperties['lenovoid.realm']}")
	private String realm;
	
	@Value("#{configProperties['wxqr.login.url']}")
	private String wxUrl;
	
	@Autowired
	private LaoAccountRelService relService;
	
	@Autowired
	private LaoCustomerService customerService;
	
	/**
	* 功能描述：协议
	* @author sunhao
	* @date 2017年5月24日 下午1:55:26
	* @param @param request
	* @param @param response
	* @param @return
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping("/protocol")
	public ModelAndView protocol(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/newH5/protocol");
		return mv;
	}
	
	/**
	* 功能描述：使用条款
	* @author sunhao
	* @date 2017年5月24日 下午1:55:26
	* @param @param request
	* @param @param response
	* @param @return
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping("/termsOfUse")
	public ModelAndView termsOfUse(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/newH5/termsOfUse");
		return mv;
	}
	
	/**
	* 功能描述：帮助界面
	* @author sunhao
	* @date 2017年5月24日 下午1:55:26
	* @param @param request
	* @param @param response
	* @param @return
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping("/helper")
	public ModelAndView helper(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/newH5/helper");
		return mv;
	}
	/**
	* 功能描述：注册界面
	* @author sunhao
	* @date 2017年5月24日 下午1:55:41
	* @param @param request
	* @param @param response
	* @param @return
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping("/toRegister")
	public ModelAndView toRegister(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/newH5/register");
		return mv;
	}
	
	/**
	* 功能描述：发送短信验证码
	* @author sunhao
	* @date 2017年5月24日 下午1:55:55
	* @param @param session
	* @param @param phone
	* @param @return
	* @return boolean
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/sendCheckMsg")
	public boolean sendCheckMsg(HttpSession session, String phone){
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("sendTime", new Date());
		map.put("number", phone);
		map.put("templateId", "5");// 手机验证码的短信模板ID
		
		//生成随机的验证码 4位
		String sources = "0123456789";
		int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(4);
        for(int i = 0; i < 4; i++){
            verifyCode.append(sources.charAt(rand.nextInt(codesLen-1)));
        }
		map.put("checkCode", verifyCode.toString());
		
		//同一号码，验证码发送成功了多次之后，不再处理验证码请求
		List<LaoSmsInfoDto> queryInfoByMsisdn = sendMessageService.queryInfoByMsisdn(phone);
		boolean smsSend = true;
		if(queryInfoByMsisdn != null && queryInfoByMsisdn.size() > 1000){
			return false;
		}else{
			smsSend = sendMessageService.smsSend(map);
		}
		
		if(smsSend){
			session.setAttribute("checkedCode", verifyCode.toString());
			session.setMaxInactiveInterval(60*5);//3分钟有效期
		}
		return smsSend;
	}
	
	/**
	* 功能描述：注册功能
	* @author sunhao
	* @date 2017年5月24日 下午1:56:17
	* @param @param user
	* @param @param redirectAttributes
	* @param @param session
	* @param @return
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping("/register")
	public ModelAndView register(LaoSsAccountDto user, RedirectAttributes redirectAttributes, HttpSession session) {
		ModelAndView mv = new ModelAndView("/newH5/register");
		String regex = "^[0-9A-Za-z]{6,20}$";
		if(!user.getLoginName().matches(regex)){
			return mv;
		}
		//验证码校验
		if(session.getAttribute("checkedCode") != null && session.getAttribute("checkedCode").equals(user.getSalt())){
			user.setSalt(null);//清空
			LaoSsAccountDto laoSsAccountDto = new LaoSsAccountDto();
			Long userId = Long.valueOf(ZkGenerateSeq
					.getIdSeq(SeqID.SYSUSER_ID));
			laoSsAccountDto.setLoginName(user.getLoginName());
			laoSsAccountDto.setPassword(user.getPassword());
			laoSsAccountDto.setPlainPassword(user.getPlainPassword());
			laoSsAccountDto.setNickname(user.getLoginName());
			laoSsAccountDto.setAcconutId(userId);
			accountService.saveCommonUser(laoSsAccountDto);
			
			LaoCustomerDto dto = new LaoCustomerDto();
			long custId = customerService.initCustInfo(dto);
			// 编辑用户绑定的客户id
			accountService.updateCustId(laoSsAccountDto, custId);
			
			LaoAccountRelDto relDto = new LaoAccountRelDto();
			relDto.setRelType("1001");
			if(session.getAttribute("openId") != null){
				relDto.setRelAccount(session.getAttribute("openId").toString());
			}
			Long id = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
			relDto.setAccountId(userId);
			relDto.setRecvTime(new Date());
			relDto.setRelId(id);
			relService.save(relDto);
			
			mv.setViewName("/newH5/registerSuc");
		}
		return mv;
	}
	
	/**
	* 功能描述：重置密码界面
	* @author sunhao
	* @date 2017年5月24日 下午1:56:33
	* @param @param request
	* @param @param response
	* @param @return
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping("/toResetPwd")
	public ModelAndView toResetPwd(HttpServletRequest request, HttpServletResponse response, String phone) {
		ModelAndView mv = new ModelAndView("/newH5/toResetPwd");
		mv.addObject("phone", phone);
		return mv;
	} 
	
	/**
	* 功能描述：重置密码功能
	* @author sunhao
	* @date 2017年5月24日 下午1:59:54
	* @param @param request
	* @param @param response
	* @param @return
	* @return ModelAndView
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/resetPwd")
	public ResultMsg resetPwd(HttpServletRequest req, LaoSsAccountDto dto, HttpSession session) {
		ResultMsg msg = new ResultMsg();
		msg.setSuccess(true);
		LaoSsAccountDto user = accountService.getUserByLoginName(dto.getLoginName());
		if (session.getAttribute("checkedCode") != null && session.getAttribute("checkedCode").equals(dto.getSalt()) && user != null) {
			if(dto.getPlainPassword().equals(user.getPlainPassword())){
				msg.setSuccess(false);
				msg.setMsg("新密码和旧密码重复");
			}else{
				dto.setAcconutId(user.getAcconutId());
				dto.setSalt(null);
				if (accountService.changePassword(dto, user.getPlainPassword()) > 0) {
					msg.setSuccess(true);
				} else {
					msg.setSuccess(false);
					msg.setMsg("其它错误");
				}
			}
		} else {
			msg.setSuccess(false);
			msg.setMsg("账号不存在或验证码错误");
		}
		return msg;
	}
	
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/newH5/login");
		return mv;
	}
	
	@RequestMapping("/index")
	@ResponseBody
	public String index(HttpSession session, HttpServletRequest request, HttpServletResponse response, String captcha, String account, String password) {
		JSONObject result = new JSONObject();
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		/**
		 * 三种情况： 1.lenovo注册，gla注册过： gla登陆失败（密码是随机设置，无法登陆），lenovo登陆成功
		 * 		  2.只gla注册： gla登陆成功 不走下面, 如果错误？
		 * 		  3.只lenovo 注册： leonovo登陆， 将生成gla用户绑定信息，将密码设置成一样。
		 */
		LaoSsAccountDto user = loginService.getUserInfo(account);
		if (user != null) {
			try {
				AuthenticationToken token=new UsernamePasswordCaptchaToken(account, password.toCharArray(), false, "host",captcha);
				SecurityUtils.getSubject().login(token);
				
				//登陆上记录用户信息
				request.getSession().setAttribute("sessionUser",user);
				request.getSession().setAttribute("resList", JSONArray.toJSON(user.getResources()));
				
				//绑定用户openId
				accountService.bindingGla(account, password, session.getAttribute("openId").toString());
				result.put("success", "glaH5AppQuery/index");
			} catch (AuthenticationException e) {
				if (UnknownAccountException.class.getName().equals(e.getClass().getName())) {
					result.put("error", "无此帐号");
				} else if (IncorrectCredentialsException.class.getName().equals(e.getClass().getName())) {
					result.put("error", "用户名/密码错误");
				}else if (CaptchaException.class.getName().equals(e.getClass().getName())) {
					result.put("error", "验证码错误");
				} else {
					result.put("error", "其他错误");
				}
			}
			
		}else{
			result.put("error", "无此帐号");
		}
		
		//lenovo 手机号/密码登陆
		if(!result.containsKey("success") && (result.containsKey("error") && !result.getString("error").equals("验证码错误")) && p.matcher(account).matches()){
			String loginXml = LenovoLoginUtil.login(realm, account, password, getIpAddr(request));
			if(loginXml != null){
				String accountID = StringUtils.substringBetween(loginXml, "<Userid>","</Userid>");
				String lpsust = StringUtils.substringBetween(loginXml, "<Value>","</Value>");
				String error = StringUtils.substringBetween(loginXml, "<Error>","</Error>");
				String code = StringUtils.substringBetween(loginXml, "<Code>","</Code>");
				if(accountID != null && lpsust != null){
					result.clear();
//					request.getSession().setAttribute("lenovoid", accountID);
//					request.getSession().setAttribute("username", account);
//					request.getSession().setAttribute("lpsust", lpsust);
					result.put("success", "glaH5AppQuery/index");
					
					//绑定gla用户信息
					if(user == null){
						accountService.bindingGla(account, password, session.getAttribute("openId").toString());
						user = loginService.getUserInfo(account);
						request.getSession().setAttribute("sessionUser",user);
						request.getSession().setAttribute("resList", JSONArray.toJSON(user.getResources()));
					}
				}else if(error != null && code != null){
					if(code.equals("USS-0103")) result.put("error", "无此帐号");
					if(code.equals("USS-0101")) result.put("error", "密码错误");
					if(code.equals("USS-0111")) result.put("error", "帐号被disable");
					if(code.equals("USS-0135")) result.put("error", "无效的请求参数");
					if(code.equals("USS-0151")) result.put("error", "帐号多次登录失败，会被锁定，需要稍后再登录尝试");
					if(code.equals("USS-0121")) result.put("error", "无效的realm");
					if(code.equals("USS-0123")) result.put("error", "该服务禁止访问");
				}
			}
		}
		
		return result.toString();
	}
	
	//微信二维码登陆
	@RequestMapping("/weixinLogin")
	@ResponseBody
	public String weixinLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response, String account, String password) {
		/**
		 * 三种情况： 1.lenovo注册，gla注册过： gla登陆失败（密码是随机设置，无法登陆），lenovo登陆成功
		 * 		  2.只gla注册： gla登陆成功 不走下面, 如果错误？
		 * 		  3.只lenovo 注册： leonovo登陆， 将生成gla用户绑定信息，将密码设置成一样。
		 */
		JSONObject result = new JSONObject();
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		LaoSsAccountDto user = loginService.getUserInfo(account);
		if (user != null) {
			try {
				AuthenticationToken token=new UsernamePasswordToken(account, password);
				SecurityUtils.getSubject().login(token);
				
				//登陆上记录用户信息
				request.getSession().setAttribute("sessionUser",user);
				request.getSession().setAttribute("resList", JSONArray.toJSON(user.getResources()));
				
				//绑定用户openId
				accountService.bindingGla(account, password, session.getAttribute("openId").toString());
				result.put("success", "login/loginSuccess");
			} catch (AuthenticationException e) {
				if (UnknownAccountException.class.getName().equals(e.getClass().getName())) {
					result.put("error", "无此帐号");
				} else if (IncorrectCredentialsException.class.getName().equals(e.getClass().getName())) {
					result.put("error", "用户名/密码错误");
				}else if (CaptchaException.class.getName().equals(e.getClass().getName())) {
					result.put("error", "验证码错误");
				} else {
					result.put("error", "其他错误");
				}
			}
			
		}else{
			result.put("error", "无此帐号");
		}
		
		//lenovo 手机号/密码登陆
		if(!result.containsKey("success") && (result.containsKey("error") && !result.getString("error").equals("验证码错误")) && p.matcher(account).matches()){
			String loginXml = LenovoLoginUtil.login(realm, account, password, getIpAddr(request));
			if(loginXml != null){
				String accountID = StringUtils.substringBetween(loginXml, "<Userid>","</Userid>");
				String lpsust = StringUtils.substringBetween(loginXml, "<Value>","</Value>");
				String error = StringUtils.substringBetween(loginXml, "<Error>","</Error>");
				String code = StringUtils.substringBetween(loginXml, "<Code>","</Code>");
				if(accountID != null && lpsust != null){
					result.clear();
					result.put("success", "login/loginSuccess");
					
					//绑定gla用户信息
					if(user == null){
						accountService.bindingGla(account, password, session.getAttribute("openId").toString());
						user = loginService.getUserInfo(account);
						request.getSession().setAttribute("sessionUser",user);
						request.getSession().setAttribute("resList", JSONArray.toJSON(user.getResources()));
					}
				}else if(error != null && code != null){
					if(code.equals("USS-0103")) result.put("error", "无此帐号");
					if(code.equals("USS-0101")) result.put("error", "密码错误");
					if(code.equals("USS-0111")) result.put("error", "帐号被disable");
					if(code.equals("USS-0135")) result.put("error", "无效的请求参数");
					if(code.equals("USS-0151")) result.put("error", "帐号多次登录失败，会被锁定，需要稍后再登录尝试");
					if(code.equals("USS-0121")) result.put("error", "无效的realm");
					if(code.equals("USS-0123")) result.put("error", "该服务禁止访问");
				}
			}
		}
		
		return result.toString();
	}
	
	/**
	 * 生成二维码图片
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getQrCodeImg")
	public void GetQrCodeImg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//生成唯一ID
		String uuid = ZkGenerateSeq.getIdSeq(SeqID.LOGIN_ID);
		//二维码内容
		String content = wxUrl + uuid;
		//生成二维码
		String imgName =  uuid + "_" + (int) (new Date().getTime() / 1000) + ".png";
		String imgPath = request.getServletContext().getRealPath("/")+"/static/h5/" + imgName;
		TwoDimensionCode handler = new TwoDimensionCode();
		handler.encoderQRCode(content, imgPath, "png");
		
		//生成的图片访问地址
		String qrCodeImg = "static/h5/" + imgName;
		String jsonStr = "{\"uuid\":" + uuid + ",\"qrCodeImg\":\"" + qrCodeImg + "\"}";
		response.getWriter().write(jsonStr);
		response.getWriter().close();
	}
	
	@RequestMapping("/loginCheck")
	public void loginCheck(HttpServletRequest request, HttpServletResponse response, String uuid) throws Exception{
		JSONObject result = new JSONObject();
		Log.info("二维码登陆>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>uuid"+uuid);
		
		if(StringUtils.isNotBlank(uuid)){
			String query = deviceService.getValue(uuid);
			Log.info(uuid+"二维码登陆>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>redis"+query);
			if(StringUtils.isNotBlank(query)){
				JSONObject redisResult = JSONObject.fromObject(query);
				//保存openId
				request.getSession().setAttribute("openId", redisResult.get("openId"));
				result.put("redirect", "banding");
				result.put("nickname", redisResult.get("nickname"));
				
				if(redisResult.containsKey("accountId")){
					Long accountId = redisResult.getLong("accountId");
					Log.info("二维码登陆>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>accountId:"+accountId);
					LaoSsAccountDto laoSsAccountDto = accountService.queryAccountById(accountId);
					// 直接登录
					AuthenticationToken token=new UsernamePasswordToken(laoSsAccountDto.getLoginName(),laoSsAccountDto.getPlainPassword());
					SecurityUtils.getSubject().login(token);
					
					laoSsAccountDto = loginService.getUserInfo(laoSsAccountDto.getLoginName());
					request.getSession().setAttribute("sessionUser", laoSsAccountDto);
					result.clear();
					result.put("redirect", "login/loginSuccess");
				}
			}else{
				result.put("redirect", "error");
			}
		}
		response.getWriter().write(result.toString());
		response.getWriter().close();
	}
	/**
	 * 微信公众号关注之后,携带用户的唯一标识/用户昵称/用户微信openId
	 * @param request
	 * @param response
	 */
	@RequestMapping("/checkSignature")
	private void CheckSignature(HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
        //昵称
        String nickname = request.getParameter("nickname");
        //openId
        String openId = request.getParameter("openid");
        //UUId
        String qruuid = request.getParameter("uuid");
        
        result.put("openId", openId);
        Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>微信推送事件:"+request.getQueryString());
        //gla 微信推送信息
        LaoAccountRelDto relDto = new LaoAccountRelDto();
        relDto.setRelType("1001");
        relDto.setRelAccount(openId);
		try {
			nickname = java.net.URLDecoder.decode(new String(nickname.getBytes("ISO-8859-1"), "UTF-8"), "UTF-8");
			result.put("nickname", nickname);
			Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>微信主人:"+nickname);
			if (StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(qruuid)) {
				List<LaoAccountRelDto> rels = relService.queryRelByRelType(relDto);
				if (rels != null && rels.size() > 0) {
					LaoAccountRelDto laoAccountRelDto = rels.get(0);
					// 登陆上记录用户信息
					result.put("accountId", laoAccountRelDto.getAccountId());
					deviceService.setex(qruuid, 1800, result.toString());
				}else{
					deviceService.setex(qruuid, 1800, result.toString());
				}
			} else {
				deviceService.setex(qruuid, 1800, result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/loginout")
	public String loginout(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		if(session != null && session.getAttribute("sessionUser") != null){
			session.removeAttribute("sessionUser");
		}
		CookieUtils.removeCookie(response, Constant.CASHIER_COOKIE_KEY);
		return "redirect:/glaH5App/login";
	}
	
	//解析ip地址
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
