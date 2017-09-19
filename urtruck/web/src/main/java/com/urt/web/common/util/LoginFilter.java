/**
 * 
 */
package com.urt.web.common.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lenovo.pay.core.bo.Constant;
import com.lenovo.pay.utils.CookieUtils;
import com.lenovo.pay.utils.GameUtils;

/**
 * 
 * @ClassName: LoginFilter
 * @Description: 登录验证过滤
 * @author LY
 * @date 2014年10月10日
 *
 */
@Component("h5LoginFilter")
public class LoginFilter implements Filter {

	Logger logger = Logger.getLogger(getClass());

	private static final long serialVersionUID = 7223278702472110545L;
	private static final String CALLBACK = "callback.xhtml";
	
	@Value("${passort.login.url}")
	private  String INDEX;
	@Value("${h5Filter}")
	private String h5Filter;
			//"https://passport.lenovo.com/wauthen2/gateway?lenovoid.action=uilogin&lenovoid.realm=h5mobiletest.lenovomm.com&&lenovoid.uinfo=username,email&lenovoid.cb=http://h5mobiletest.lenovomm.com/account/callback.xhtml&lenovoid.vp=0";

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest httpRequest,
			ServletResponse httpResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) httpRequest;
		HttpServletResponse response = (HttpServletResponse) httpResponse;

		String requestUrl = request.getRequestURI();
		String params = request.getQueryString();
		// 不过滤的uri  
        String[] inFilter = h5Filter.split(",");  

        AccountCookie accountCookie = new AccountCookie(request, response);
		accountCookie = accountCookie.getCookieAccount();
		for (String str : inFilter) {
			if(requestUrl.contains(str)){
				if(request.getSession().getAttribute("sessionUser") == null){
					request.getSession().invalidate();
					response.sendRedirect("../glaH5App/login");
					return;
				}
				break; 
//				if (request.getSession().getAttribute("lpsust") == null || request.getSession().getAttribute("lenovoid") == null) {
//					CookieUtils.removeCookie(response, Constant.CASHIER_COOKIE_KEY);
//					request.getSession().invalidate();
//					if(null != params){
//						response.sendRedirect(INDEX + "&lenovoid.ctx="+requestUrl+ "?" +params);
//					}else{
//						response.sendRedirect(INDEX + "&lenovoid.ctx="+requestUrl);
//					}
//					return;
//				}
			}
		}
		
		chain.doFilter(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
    
	}

}
