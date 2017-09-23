package com.urt.web.filter;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.alibaba.dubbo.common.json.JSON;
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
import com.urt.web.common.util.MD5;
import com.urt.web.common.util.StringUtil;
import com.urt.web.util.EncryptUtil;

public class RechargeFilter implements Filter {
	private static Logger log = LoggerFactory.getLogger(RechargeFilter.class);
	private String weixinBindUrl = "https://passport.lenovo.com/wauthen2/thirdWxOauth?";
	private Set<String> prefixIignores = new HashSet<String>();
	//private LaoSsAccessLogDto accessLogDto;
	
	// 本地
	// private String oauthkey="118.144.186.215";
	//private String oauthkey="118.144.186.220";
	// 测试环境
    //private String oauthkey="223.203.218.66";
	// 生产
	private String oauthkey = "192.168.70.1";// 223.202.19.205 192.168.70.1
	private String oauthkey1 = "172.25.99.12";
	private String oauthkey2 = "10.0.20.110";// 10.0.20.110
	private String oauthkey3 = "10.0.20.111";// 10.0.20.111
	private String realm = "gla.lenovo.com";
	private String openid = "testopenid";
	private String accesstoken = "testaccesstoken";
	private String tokensecret = "123";
	private String appkey = "123";
	private String nickname = "";
	private String imageurl = "http://www.testimageurl.com";
	private String source = "";
	private String authUrl = "https://passport.lenovo.com/interserver/authen/1.2/getaccountid?";
	private String wust = "lenovoid.wust";
	private String cb = "";
	private String authkey = "";
	private String authkey1 = "";
	private String authkey2 = "";
	private String authkey3 = "";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		String contextPath = filterConfig.getServletContext().getContextPath();
		String initParameter = filterConfig.getInitParameter("ignores");
		String[] ignoreArray = initParameter.split(",");
		for (String s : ignoreArray) {
			prefixIignores.add(contextPath + s);
		}
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String requestURL = request.getRequestURL().toString();
		String params = request.getQueryString();
		String loginTag = null;
		String paramDate = null;
		cb = "http://gla.lenovo.com"/* + request.getServerName() */// 服务器地址
				/*
				 * + ":" + request.getServerPort()
				 */// 端口号
				+ request.getContextPath() // 项目名称
				+ request.getServletPath() // 请求页面或其他地址
				+ "?" + (request.getQueryString());
		log.info(">>>>>>>>>>>>cb=" + cb);
		if (canIgnore(request)) {
			chain.doFilter(request, response);
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>静态资源无需拦截" + cb);
			return;
		}
		Map<String,Object> mapDto = new HashMap<String,Object>();
		mapDto.put("accessTime", new Date());
		mapDto.put("url", request.getContextPath() + request.getServletPath());
		mapDto.put("userIp", getIp3(request));
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>当前访问的IP地址为：" + mapDto.get("userIp").toString());
		if(request.getSession().getAttribute("sessionUser")!=null){
			LaoSsAccountDto currentUser = (LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
			mapDto.put("loginId", currentUser.getAcconutId().toString());
			mapDto.put("loginName", currentUser.getLoginName());
		}
		if(request.getSession().getAttribute("lenovoid")!=null){
			mapDto.put("thirdPartyAccountId", request.getSession().getAttribute("lenovoid").toString());
			mapDto.put("thirdPartyAccount", "1");
		}
		request.getSession().setAttribute("weixinCb", cb);
		ServletContext sc = servletRequest.getServletContext();
		XmlWebApplicationContext xwac = (XmlWebApplicationContext) WebApplicationContextUtils
				.getWebApplicationContext(sc);
		LaoSsAccountService accountService = (LaoSsAccountService) xwac
				.getBean("accountService");

		LaoAccountRelService relService = (LaoAccountRelService) xwac
				.getBean("laoAccountRelService");
		LaoCustomerService customerService = (LaoCustomerService) xwac
				.getBean("customerService");
		log.info(">>>" + "判断是否需要拦截；外部请求URL：" + requestURL);
		Enumeration paramNames = request.getParameterNames();
		boolean flag = false;
		while (paramNames.hasMoreElements()) {
			String name = paramNames.nextElement().toString();
			String value = request.getParameter(name);
			if("iccid".equals(name)){
				mapDto.put("iccid", value);
			}
			if (sqlValidate(value)) {
				log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + value + "小样还想注入");
				flag = true;
			}
		}
		if (flag) {
			throw new IOException("您发送请求中的参数中含有非法字符");
		} else {
			String parameter = servletRequest.getParameter("fromsys");
			log.info(">>>>>>>>>>>>>>>>>>>fromsys=" + parameter);
			if (parameter != null && parameter.trim().length() > 0) {
				String fromSys = "";
				try {
					fromSys = EncryptUtil.aesDecrypt(
							servletRequest.getParameter("fromsys"),
							"e2da530b3bdcb4f5");
					log.info(">>>>fromSys=" + fromSys);

				} catch (Exception e2) {
					log.info("<<<<<<<<<解析fromSys异常");
					log.info("<<<<<<<<<<<<<异常信息：" + e2.getMessage());
				}
				// 来源于微信平台
				if ("wechat".equals(fromSys)) {
					String requestContextPath = request.getContextPath().trim();
					String requestServletPath = request.getServletPath().trim();
					log.info(">>>>>>即将进入微信的处理环节");
					log.info(">>>>>>访问路径为： " + requestContextPath + requestServletPath);
					if (request.getSession().getAttribute("sessionUser") != null 
							&& (!"/batchFtpImport/billByNumber".equals(requestContextPath+requestServletPath))) {
						chain.doFilter(request, response);
					} else {
						// 判断是否跟gla绑定
						String encodeStr = request.getParameter("data");
						String jsonString;
						try {
							jsonString = EncryptUtil.aesDecrypt(encodeStr,
									"e2da530b3bdcb4f5");
							log.info(">>>>>>>>>>>>>>>>jsonString=" + jsonString);
							Gson gson = new Gson();
							HashMap map = gson.fromJson(jsonString,
									HashMap.class);
							openid = map.get("paraid").toString();
							request.getSession().setAttribute("openId", openid);
							mapDto.put("thirdPartyAccountId", openid);
							mapDto.put("thirdPartyAccountType", "2");
							LaoAccountRelDto relDto = new LaoAccountRelDto();
							relDto.setRelType("1001");
							relDto.setRelAccount(openid);
							List<LaoAccountRelDto> rels = relService
									.queryRelByRelType(relDto);
							if (rels != null && rels.size() > 0) {
								LaoAccountRelDto laoAccountRelDto = rels.get(0);
								Long accountId = laoAccountRelDto.getAccountId();
								LaoSsAccountDto laoSsAccountDto = accountService.queryAccountById(accountId);
								// 直接登录
								AuthenticationToken token=new UsernamePasswordToken(laoSsAccountDto.getLoginName(),laoSsAccountDto.getPlainPassword());
							
								SecurityUtils.getSubject().login(token);
								chain.doFilter(request, response);
							} else {
								// 判断Lenovo 是否绑定
								MD5 md5 = new MD5();
								authkey = md5.md5(oauthkey).toUpperCase();
								authkey1 = md5.md5(oauthkey1).toUpperCase();
								authkey2 = md5.md5(oauthkey2).toUpperCase();
								authkey3 = md5.md5(oauthkey3).toUpperCase();

								log.info(">>>>>>>>>>oauthkey=" + oauthkey);
								log.info(">>>>>>>>>>>>>>>>jsonString="
										+ jsonString);
								// 2017年4月10日13:19:58 sunhao 开始
								if (map.get("loginTag") != null) {
									loginTag = map.get("loginTag").toString();
									map.remove("loginTag");
									paramDate = gson.toJson(map);
									log.info(">>>>>>>>>>>>>>>>paramDate="
											+ paramDate);
									paramDate = EncryptUtil.aesEncrypt(
											paramDate, "e2da530b3bdcb4f5");
								}
								// 2017年4月10日13:19:58 sunhao
								openid = map.get("paraid").toString();
								request.getSession().setAttribute("openId",
										openid);
								accesstoken = map.get("atid").toString();
								request.getSession().setAttribute(
										"accesstoken", accesstoken);
								nickname = map.get("paraname").toString();
								request.getSession().setAttribute("nickname",
										nickname);
								imageurl = map.get("iu").toString();

								HttpPost httpPost = new HttpPost(weixinBindUrl);// 创建httpPost
								// 创建参数队列
								List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
								nameValuePairs.add(new BasicNameValuePair(
										"oauthkey", authkey + "," + authkey1
												+ "," + authkey2 + ","
												+ authkey3));
								nameValuePairs.add(new BasicNameValuePair(
										"realm", realm));
								nameValuePairs.add(new BasicNameValuePair(
										"openid", openid));
								nameValuePairs.add(new BasicNameValuePair(
										"accesstoken", accesstoken));
								nameValuePairs.add(new BasicNameValuePair(
										"tokensecret", tokensecret));
								nameValuePairs.add(new BasicNameValuePair(
										"appkey", appkey));
								nameValuePairs.add(new BasicNameValuePair(
										"nickname", nickname));
								nameValuePairs.add(new BasicNameValuePair(
										"imageurl", imageurl));
								nameValuePairs.add(new BasicNameValuePair(
										"ctx", wust));
								nameValuePairs.add(new BasicNameValuePair(
										"callback", cb));
								nameValuePairs.add(new BasicNameValuePair(
										"source", realm));
								try {
									httpPost.setEntity(new UrlEncodedFormEntity(
											nameValuePairs, "UTF-8"));
									httpPost.setHeader("X-Forwarded-For",
											oauthkey + "," + oauthkey1 + ","
													+ oauthkey2 + ","
													+ oauthkey3);
								} catch (Exception e) {
									e.printStackTrace();
								}
								String jsonStr = sendHttpPost(httpPost);
								// 判断是否绑定
								String url = weixinBindUrl + "oauthkey="
										+ oauthkey + "&realm=" + realm
										+ "&openid=" + openid + "&accesstoken="
										+ accesstoken + "&tokensecret="
										+ tokensecret + "&appkey=" + appkey
										+ "&nickname=" + nickname
										+ "&imageurl=" + imageurl + "&ctx="
										+ wust + "&source=" + realm
										+ "&callback=" + cb;
								log.info(">>>>>>>>>>>>>>>>>>联想绑定接口请求url=" + url);
								log.info(">>>>>>>>>>>>>>>>>>联想绑定接口返回结果="
										+ jsonStr);
								Map<String, Object> lenovoMap;
								lenovoMap = JSON.parse(jsonStr, Map.class);
								log.info(">>>>>>验证原路径信息： " + requestContextPath + ":" + requestServletPath);
								if("/batchFtpImport/billByNumber".equals(requestContextPath+requestServletPath)){
									log.info(">>>>>>>原路径信息正常，如未绑定联想接口，将自动进入绑定界面!");
								}
								if ((boolean) lenovoMap.get("thirdBinded") == true) {
									request.getSession().setAttribute("bindurl",
											"http://gla.lenovo.com/glaH5App/index");
									// 已绑定
									// 登录认证
									wust = lenovoMap.get("lpsust").toString();
									request.getSession().setAttribute("lpsust", wust);
									String authUrlStr = authUrl + "lpsust=" + wust
											+ "&realm=" + realm;
									log.info(">>>>>>>>>>>>登录认证的URL authUrl="
											+ authUrlStr);
									String xml = HttpUtil.sendGetHttps(authUrlStr);
									Account account = this.parseXML2Account(xml);
									if (account != null) {
										request.getSession().setAttribute("lenovoid",
												account.getAccountID());
										request.getSession().setAttribute("username",
												account.getUsername());
									}
									if(StringUtil.isNotBlank(account.getUsername())){
										Random random = new Random();
										LaoSsAccountDto laoSsAccountDto=new LaoSsAccountDto();;
										if(accountService.getUserByLoginName(account.getUsername())!=null){
											laoSsAccountDto = accountService.getUserByLoginName(account.getUsername());
											relDto.setAccountId(laoSsAccountDto.getAcconutId());
										}else{
											Long userId = Long.valueOf(ZkGenerateSeq
													.getIdSeq(SeqID.SYSUSER_ID));
											laoSsAccountDto.setLoginName(account
													.getUsername());
											laoSsAccountDto.setPassword("123456");
											laoSsAccountDto.setPlainPassword("123456");
											laoSsAccountDto.setNickname("gla"
													+ random.nextInt(999999));
											laoSsAccountDto.setAcconutId(userId);
											accountService.saveCommonUser(laoSsAccountDto);
											relDto.setAccountId(userId);
										}
										LaoCustomerDto dto = new LaoCustomerDto();
										long custId = customerService.initCustInfo(dto);
										// 编辑用户绑定的客户id
										accountService.updateCustId(laoSsAccountDto,
												custId);
										// 生成绑定信息
										Long id = Long.valueOf(ZkGenerateSeq
												.getIdSeq(SeqID.SYSUSER_ID));
										relDto.setRecvTime(new Date());
										relDto.setRelId(id);
										relDto.setRelLoginname(laoSsAccountDto.getLoginName());
										relDto.setRelNickname(laoSsAccountDto.getNickname());
										relService.save(relDto);
									}
								}else if("/batchFtpImport/billByNumber".equals(requestContextPath+requestServletPath)){
									log.info("~~~~~~~~~~~~~~~执行未绑定的流程");
	    							// 跳转到联想绑定页面
	    							HttpServletResponse response1 = (HttpServletResponse) response;
	    							// 添加cb
	    							//绑定成功后会继续处理
	    							log.info("~~~~~~~~~~~~~~~执行绑定后，会跳转至：" + cb);
//	    							cb="http://www.baidu.com";
	    							String bindurl = lenovoMap.get("redirectUrl").toString() + "&realm=" + realm + "&callback=" + cb;
	    							log.info(">>>>>>>>>>>>>>>>请求联想绑定url"+bindurl);
	    							response1.sendRedirect(bindurl);
								}
								chain.doFilter(request, response);
							}
						} catch (Exception e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}

					}

				} else if ("qq".equals(fromSys)) {
					log.info(">>>>>>>>>>>暂无qq的处理流程");
					// qq逻辑
					chain.doFilter(request, response);
				} else {
					log.info(">>>>>>>>>>>未知来源 不予处理");
				}
			} else {
				log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~正常链接无需拦截");
				chain.doFilter(request, response);
			}
			try {
				log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~mapDto:"+mapDto.values().toString());
				accountService.addAccessLog(mapDto);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("accountService.addAccessLog(mapDto) failed", e);
			}
		}

		return;

	}

	private static RequestConfig requestConfig = RequestConfig.custom()
			.setSocketTimeout(35000).setConnectTimeout(35000)
			.setConnectionRequestTimeout(35000).build();

	private static String sendHttpPost(HttpPost httpPost) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		String responseContent = null;
		try {
			// 创建默认的httpClient实例.
			httpClient = HttpClients.createDefault();
			httpPost.setConfig(requestConfig);
			// 执行请求
			response = httpClient.execute(httpPost);
			entity = response.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭连接,释放资源
				if (response != null) {
					response.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public Weixin parseXML2Weixin(String xml) {
		Weixin weixin = new Weixin();
		// System.out.println(xml);
		String thirdBinded = StringUtils.substringBetween(xml, "<thirdBinded>",
				"</thirdBinded>");
		String lpsust = StringUtils.substringBetween(xml, "<lpsust>",
				"</lpsust>");
		String redirectUrl = StringUtils.substringBetween(xml, "<redirectUrl>",
				"</redirectUrl>");

		if (StringUtils.isNotBlank(thirdBinded)) {
			weixin.setThirdBinded(thirdBinded);
		}
		if (StringUtils.isNotBlank(lpsust)) {
			weixin.setLpsust(lpsust);
		}
		if (StringUtils.isNotBlank(redirectUrl)) {
			weixin.setRedirectUrl(redirectUrl);
		}

		return weixin;
	}

	/**
	 * 自定义Account对象，使得携带用户的登录名外还可以携带更多信息.
	 */
	public class Weixin implements Serializable {

		private static final long serialVersionUID = 2009047057323590514L;
		private String thirdBinded;
		private String lpsust;
		private String redirectUrl;

		public String getThirdBinded() {
			return thirdBinded;
		}

		public void setThirdBinded(String thirdBinded) {
			this.thirdBinded = thirdBinded;
		}

		public String getLpsust() {
			return lpsust;
		}

		public void setLpsust(String lpsust) {
			this.lpsust = lpsust;
		}

		public String getRedirectUrl() {
			return redirectUrl;
		}

		public void setRedirectUrl(String redirectUrl) {
			this.redirectUrl = redirectUrl;
		}

	}

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
		public String AccountID;
		public String Username;
		public String uname;
		public String verified;
		public String wust;
		public String idcLocation;
		public String amount;
		public String DeviceID;
		public String Thirdname;

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

	protected static boolean sqlValidate(String str) {
		str = str.toLowerCase();// 统一转为小写
		String badStr = "' |and |exec |execute |insert |select |delete |update |count |drop |chr |mid |master |truncate |"
				+ "char |declare |sitename |net user |xp_cmdshell |or |like |and |exec |execute |insert |create |drop |"
				+ "table |from |grant |use |group_concat |column_name |"
				+ "information_schema.columns |table_schema |union |where |select |delete |update |order | by |count |"
				+ "chr |mid |master |truncate |char |declare |or |-- |like |"
				+
				// js关键字
				"abstract |arguments |boolean |break |byte |case |catch |char |class |const |continue |debugger |"
				+ "default |delete |do |double |else |enum |eval |export |extends |false |final |finally |float |for |"
				+ "function |goto |if |implements |import |in  |instanceof |int |interface |let |long |native |new |null |"
				+ "package |private |protected |public |return |short |static |super |switch |synchronized |this |throw |"
				+ "throws |transient |try |typeof |var |void |volatile |while |with |yield |"
				+ "Array |Date |function |hasOwnProperty |Infinity |isFinite |isNaN |isPrototypeOf|"
				+ "length|Math|NaN|name |Number|Object|prototype|String|toString|undefined|valueOf|"
				+ "getClass|java|JavaArray|javaClass|JavaObject|JavaPackage|alert|all |anchor|anchors"
				+ " |area|assign|blur|button|checkbox|clearInterval|clearTimeout|clientInformation|close|"
				+ "closed|confirm|constructor|crypto|decodeURI|decodeURIComponent|defaultStatus|document|"
				+ "element |elements |embed|embeds|encodeURI|encodeURIComponent|escape|event |fileUpload|focus|"
				+ "form |forms |frame |innerHeight|innerWidth|layer|layers|link|location|mimeTypes|navigate|navigator|"
				+ "frames|frameRate|hidden|history|offscreenBuffering|open |opener|option|outerHeight|"
				+ "outerWidth|packages|pageXOffset|pageYOffset|parent|parseFloat|parseInt|password|pkcs11|plugin|"
				+ "prompt|propertyIsEnum|radio|reset|screenX|screenY|scroll|secure|select|self|setInterval|setTimeout|"
				+ "submit|taint|text |textarea|top |unescape|untaint|window|onblur|onclick|onerror|onfocus|onkeydown|"
				+ "onkeypress|onkeyup|onmouseover|onload|onmouseup|onmousedown|onsubmit"; // 过滤掉的sql关键字，可以手动添加
		String[] badStrs = badStr.split("\\|");
		for (int i = 0; i < badStrs.length; i++) {
			if (str.indexOf(badStrs[i]) >= 0) {
				log.info(">>>>>>>>>>>>>>>>>冲突的关键字：" + badStrs[i]);
				return true;
			}
		}
		return false;
	}

	private boolean canIgnore(HttpServletRequest request) {
		String url = request.getRequestURI();
		log.info(">>>>>>>>>>>>>>>>>???：" + url);
		for (String ignore : prefixIignores) {
			log.info(">>>>>>>>>>>>>>>>>-----：" + ignore);
			if (url.indexOf(ignore) != -1) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param request
	 * @return 获取到真正的客户端ip
	 */
	private static String getIp3(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值,第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
}
