package com.urt.web.web.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.device.Account;
import com.urt.interfaces.authority.LaoSsAccountService;
import com.urt.interfaces.device.DeviceService;
import com.urt.web.common.util.AccountCookie;
@Controller
@RequestMapping("/customerQueryWeb")
public class rateDetailSearchWebController {
	
	private static final Logger log = Logger.getLogger(rateDetailSearchWebController.class);
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private LaoSsAccountService laoSsAccountService;

	@Value("#{configProperties['passort.login.url']}")
	private  String INDEX;

	
	// 登录成功跳转页面
	@RequestMapping( value="loginSuccess", method = { RequestMethod.POST,RequestMethod.GET})	
	public ModelAndView loginSuccess(HttpServletRequest request, HttpServletResponse response,HttpSession session)throws Exception{
		ModelAndView mv = new ModelAndView("/customer/loginSuccess");
		//gla系统登录
		log.info("INDEX**************"+INDEX);
		String requestUrl = request.getRequestURI();
		AccountCookie accountCookie = new AccountCookie(request, response);
		accountCookie = accountCookie.getCookieAccount();
		//外部传参调用
		String st = request.getParameter("st");
		Account account = deviceService.xdswAuthSt(st);
		if (account == null) {
			request.getSession().invalidate();
			response.sendRedirect(INDEX + "&lenovoid.ctx="+requestUrl);
		} else {
			session.setAttribute("account", account);
			String userName = account.getUsername();
			mv.addObject("loginName", userName);
			log.info("userName*******************"+userName);
			LaoSsAccountDto laoSsAccountDto = laoSsAccountService.getUserByLoginName(userName);
			if (laoSsAccountDto != null) {
				session.setAttribute("sessionUser", laoSsAccountDto);
			}
		}
		return mv;
	}
	
}
