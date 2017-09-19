package com.urt.web.common.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.lenovo.pay.core.bo.Constant;
import com.lenovo.pay.utils.CookieUtils;
import com.lenovo.pay.utils.security.Des3CryptUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.ServletActionContext;


public class AccountCookie implements Serializable{

	/** 
	 * 
	 */
	private static final long serialVersionUID = 2628719750900502941L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String uuid;
	private String uname;
	private long activeTime;
	

	public AccountCookie() {
	}

	public AccountCookie(String uuid, String uname) {
		this.uuid = uuid;
		this.uname = uname;
		this.activeTime = System.currentTimeMillis();
	}

	public AccountCookie(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	/**
	 * 获取用户的登录信息
	 * 
	 * @param req
	 *            HttpServletRequest
	 * @return UserCookie
	 */
	public AccountCookie getCookieAccount() {
		Cookie cookie = CookieUtils.getCookie(request, Constant.CASHIER_COOKIE_KEY);
		if (cookie != null) {
			String result = null;
			try {
				result = URLDecoder.decode(cookie.getValue(), "UTF-8");
				result = Des3CryptUtils.decrypt(result, Constant.COOKIE_PASSWORD);
				result = URLDecoder.decode(result, "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONObject json = JSONObject.fromObject(result);
			String uuid = json.getString("uuid");
			String uname = json.getString("uname");
			String activeTime = json.getString("activeTime");
			if (StringUtils.isBlank(uuid) || StringUtils.isBlank(uname)) {
				CookieUtils.removeCookie(ServletActionContext.getResponse(),Constant.CASHIER_COOKIE_KEY);
				return new AccountCookie(Constant.RADIO_NO_VALUE_STR, Constant.RADIO_NO_VALUE_STR);
			}
			
			AccountCookie accountCookie = new AccountCookie();
			accountCookie.setUuid(uuid);
			accountCookie.setUname(uname);
			accountCookie.setActiveTime(NumberUtils.toLong(activeTime));
			return accountCookie;
		} else {
			return new AccountCookie(Constant.RADIO_NO_VALUE_STR, Constant.RADIO_NO_VALUE_STR);
		}
	}

	public void setCookieAdmin(AccountCookie accountCookie) {
		String value = null;
		try {
			value = URLEncoder.encode(accountCookie.toString(), "UTF-8");
			value = Des3CryptUtils.encrypt(value, Constant.COOKIE_PASSWORD);
			value = URLEncoder.encode(value, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		int oneDay = 3600 * 5;
		CookieUtils.addCookie(response, Constant.CASHIER_COOKIE_KEY, value, oneDay);
	}

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("uuid", getUuid());
		json.put("uname", getUname());
		json.put("activeTime", activeTime);
		return json.toString();
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public long getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(long activeTime) {
		this.activeTime = activeTime;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
