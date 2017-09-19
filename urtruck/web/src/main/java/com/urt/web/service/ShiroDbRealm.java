/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.urt.web.service;

import java.io.Serializable;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Objects;
import com.urt.dto.LaoSsAccountDto;
import com.urt.interfaces.authority.LoginService;
import com.urt.utils.Encodes;
import com.urt.web.web.auth.CaptchaException;
import com.urt.web.web.auth.UsernamePasswordCaptchaToken;
import com.urt.web.web.auth.valdateCode.CaptchaServlet;

public class ShiroDbRealm extends AuthorizingRealm {
	private static Logger log = LoggerFactory.getLogger(ShiroDbRealm.class);
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	@Autowired
	private LoginService loginService;

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		log.debug(authcToken.getClass().getName());
		if("org.apache.shiro.authc.UsernamePasswordToken".equals(authcToken.getClass().getName())){
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+"pz");
			UsernamePasswordToken  token = (UsernamePasswordToken ) authcToken;
			LaoSsAccountDto user = loginService.getUserInfo(token.getUsername());
			if (user != null) {
				// 非正常状态下不允许登录
				if (!user.getStatus().equals("0")) {
					throw new DisabledAccountException("账号已冻结");
				}
				this.setSession("sessionUser",user);
				this.setSession("resList", JSONArray.toJSON(user.getResources()));
				byte[] salt = Encodes.decodeHex(user.getSalt());
				/*return new SimpleAuthenticationInfo(new ShiroUser(3071448170025178l, "lixue11", "统一"),
						"61cb97ee9f2137c6b4f585823d7dc05938a8c6a9", ByteSource.Util.bytes(salt), getName());*/
				return new SimpleAuthenticationInfo(new ShiroUser(user.getAcconutId(), user.getLoginName(), user.getNickname()),
						user.getPassword(), ByteSource.Util.bytes(salt), getName());
			} else {
				throw new UnknownAccountException("未找到账号");
			}
		}else{
			UsernamePasswordCaptchaToken  token = (UsernamePasswordCaptchaToken) authcToken;
			LaoSsAccountDto user = loginService.getUserInfo(token.getUsername());
			// 非正常状态下不允许登录
			if (user != null && !user.getStatus().equals("0")) {
				throw new DisabledAccountException("账号已冻结");
			}
			// 增加判断验证码逻辑
			String captcha = token.getCaptcha();
			String exitCode = (String) SecurityUtils.getSubject().getSession()
					.getAttribute(CaptchaServlet.KEY_CAPTCHA);
			Random random = new Random();
			SecurityUtils.getSubject().getSession()
			.setAttribute(CaptchaServlet.KEY_CAPTCHA,random.nextInt(9));
			if (null == captcha || !captcha.equalsIgnoreCase(exitCode)) {
				throw new CaptchaException("验证码错误");
			}

			if (user != null) {
				this.setSession("sessionUser",user);
				this.setSession("resList", JSONArray.toJSON(user.getResources()));
				byte[] salt = Encodes.decodeHex(user.getSalt());
				return new SimpleAuthenticationInfo(new ShiroUser(user.getAcconutId(), user.getLoginName(), user.getNickname()),
						user.getPassword(), ByteSource.Util.bytes(salt), getName());
			} else {
				throw new UnknownAccountException("未找到账号");
			}
		}
		
	
		
	}
	
	/** 
     * 将一些数据放到ShiroSession中,以便于其它地方使用 
     * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到 
     */  
    private void setSession(Object key, Object value){  
        Subject currentUser = SecurityUtils.getSubject();  
        if(null != currentUser){  
            Session session = currentUser.getSession();  
            log.debug("Session默认超时时间为[" + session.getTimeout() + "]毫秒");  
            if(null != session){  
                session.setAttribute(key, value);  
            }  
        }  
    }

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		LaoSsAccountDto tfFSsUserDto = loginService.getUserInfo(shiroUser.getName());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		return info;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(HASH_ALGORITHM);
		matcher.setHashIterations(HASH_INTERATIONS);

		setCredentialsMatcher(matcher);
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public Long id;
		public String loginName;
		public String name;

		public ShiroUser(Long id, String loginName, String name) {
			this.id = id;
			this.loginName = loginName;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}

		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(loginName);
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			if (loginName == null) {
				if (other.loginName != null) {
					return false;
				}
			} else if (!loginName.equals(other.loginName)) {
				return false;
			}
			return true;
		}
	}
}
