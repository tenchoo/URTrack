package com.urt.web.http.recevice;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.urt.dto.IccidLibDto;
import com.urt.dto.http.HttpServerInfo;
import com.urt.dto.http.LaoCustConfigDTO;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.device.RedisService;
import com.urt.interfaces.http.AppkeyAndIpCreate;
import com.urt.interfaces.http.ServerCheckService;
import com.urt.interfaces.traffic.TrafficQueryService;
import com.urt.web.common.util.ICCID;
import com.urt.web.common.util.StringUtil;

import net.sf.json.JSONObject;

/*
 * @author wangxb20
 * @date 20161017
 *  <p>
 * Title: HTTP统一接口平台[JAVA]
 * </p>
 * <p>
 * Description: 统一接口平台[JAVA],通过此servlet提供http方式的接入
 * 提供通用的http监听接入,可以通过配置实现所有的http接入实现
 * </p>
 */

@Controller
@RequestMapping("/httpOpenServer")
public class HttpDateReceiverController {
	/** 日志 ****/
	protected static final Logger logger = Logger.getLogger(HttpDateReceiverController.class);

	@Autowired
	private RedisService redisClientService;

	@Autowired
	private ServerCheckService serverCheckService;

	@Autowired
	private AppkeyAndIpCreate appkeyAndIpCreate;
	@Autowired
	private HttpInterfaceAdd httpInterfaceAdd;

	@Autowired
	private UserService userService;
	
	@Autowired
	TrafficQueryService trafficQueryService;

	private final static String DEFAULT_CHARSET = "UTF-8";
	private final static String resultInfo = "resultInfo";
	private final static String respCode = "respCode";
	private final static String respDesc = "respDesc";

	/**
	 * doPut 对Post方法的处理实现.
	 * 
	 * @param req
	 *            HttpServletRequest
	 * @param resp
	 *            HttpServletResponse
	 * @throws UnsupportedEncodingException
	 * @throws ServletException
	 * @throws IOException
	 * @todo Implement this javax.servlet.http.HttpServlet method response
	 *       {"RESULTINFO":{"RESP_CODE":"","RESP_DESC":"","BODY_INFO":{"PARA1":
	 *       "","PARA2":""}}}
	 */
	@RequestMapping(value = "serviceProvide", method = RequestMethod.POST)
	public void httpServerProvide(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		logger.info("***********************HTTPPOST BEGIN************************");
		// 参数校验
		String resultCheck = paramCheck(request);
		//参数校验是否正确
		if (StringUtil.isBlank(resultCheck)) {
			request.setCharacterEncoding(DEFAULT_CHARSET);
			// 处理body体请求数据
			Enumeration bodyParaNames = request.getParameterNames();
			List<String> bodyParaNameList = new ArrayList<String>();
			Map<String, String> requestInfo = new HashMap<String, String>();
			String custId = request.getParameter("custId");
			/*
			 * 获取客户全局配置
			 * 访问次数:vists;
			 * 是否有访问次数受限isTag;
			 * 是否有随机数校验isRandomCheck;
			 * 是否ip校验isIpCheck;
			 * 是否有访问权限isLimitCustVists;
			 */
			LaoCustConfigDTO lc = serverCheckService.custConfigChenk(custId);;
			//是否屏蔽客户客户的访问权限
			if ((null != lc && null != lc.getIsLimitCustVists() && "2".equals(lc.getIsLimitCustVists())) || null == lc
					|| null == lc.getIsLimitCustVists()) {
				boolean isVist = true;
				// 访问次数初始化
				int vistTimes = 30;
				if (null != lc) {
					if(request.getParameter("serverName").equals(lc.getServerName())){
						vistTimes = null != lc.getServerNameLimit() ? Integer.valueOf(lc.getServerNameLimit()) : vistTimes;
						isVist = visitsLimit(custId+lc.getServerName(), vistTimes);
					}else{
						vistTimes = null != lc.getVists() ? Integer.valueOf(lc.getVists()) : vistTimes;
						isVist = visitsLimit(custId, vistTimes);
					}			
				} else {
					isVist = visitsLimit(custId, vistTimes);
				}
				//访问测试是否超限
				if (isVist) {
					if (null != lc && null != lc.getIsIpCheck()) {
						if ("1".equals(lc.getIsIpCheck())) {
							String ip = getIp2(request);
							requestInfo.put("ipAddress", ip);
						} else if ("2".equals(lc.getIsIpCheck())) {
							requestInfo.put("ipAddress", "121.34.131.146");
						}
					} else {
						requestInfo.put("ipAddress", "121.34.131.146");
					}
					while (bodyParaNames.hasMoreElements()) {
						bodyParaNameList.add((String) bodyParaNames.nextElement());
					}
					for (String bodyPara : bodyParaNameList) {
						//是否是联通的末尾带字母的iccid
						
							requestInfo.put(bodyPara, request.getParameter(bodyPara));
						
					}
					HttpServerInfo checkRspInfo = serverCheckService.check(requestInfo);
					String checkRspCode = checkRspInfo.getRespCode();
					Map<String, Object> rspInfo = null;
					//服务权限校验是否成功
					if ("1000".equals(checkRspCode)) {
						String serverName = checkRspInfo.getServerName();
						String operationName = checkRspInfo.getOperationName();
						if ("898606".equals(requestInfo.get("iccid").substring(0, 6))) {
							String iccid = request.getParameter("iccid");
							iccid = ICCID.replaceLastChar(iccid);
							requestInfo.put("iccid", iccid);
						}else if(request.getParameter("iccid").length()==15){
							//IMSI查询
							String iccid=trafficQueryService.getIccidByImsi(request.getParameter("iccid"));
							requestInfo.put("iccid", iccid);
						}
						Map<String, Object> rspI = httpInterfaceAdd.RspInfo(operationName, serverName, requestInfo);
						rspInfo = rspI;
						// 调用服务之后 不管成功失败需要记录日志到日志表
						serverCheckService.loggerToDb(requestInfo, rspI);
					} else {
						// 返回错误编码
						Map<String, Object> result = new HashMap<String, Object>();
						result.put(respCode, checkRspInfo.getRespCode());
						result.put(respDesc, checkRspInfo.getRespDesc());
						rspInfo = new HashMap<String, Object>();
						rspInfo.put(resultInfo, result);
					}
					logger.info("*************" + Arrays.toString(rspInfo.values().toArray()));
					JSONObject jsonObject = JSONObject.fromObject(rspInfo);
					System.out.println(jsonObject);
					response.setContentType("application/json;charset=UTF-8");
					PrintWriter out;
					try {
						out = response.getWriter();
						out.println(jsonObject.toString());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Map<String, Object> result = new HashMap<String, Object>();
					Map<String, Object> vistRspInfo = new HashMap<String, Object>();
					result.put(respCode, "8888");
					result.put(respDesc, "每分钟访问次数不能超过:" + vistTimes);
					vistRspInfo.put(resultInfo, result);

					String resultStr = JSONObject.fromObject(vistRspInfo).toString();

					System.out.println(resultStr);
					response.setContentType("application/json;charset=UTF-8");
					PrintWriter out;
					try {
						out = response.getWriter();
						out.println(resultStr.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				Map<String, Object> result = new HashMap<String, Object>();
				Map<String, Object> vistRspInfo = new HashMap<String, Object>();
				result.put(respCode, "6666");
				result.put(respDesc, "无权访问");
				vistRspInfo.put(resultInfo, result);

				String resultStr = JSONObject.fromObject(vistRspInfo).toString();

				System.out.println(resultStr);
				response.setContentType("application/json;charset=UTF-8");
				PrintWriter out;
				try {
					out = response.getWriter();
					out.println(resultStr.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} else {
			logger.info("response parms:" + resultCheck);
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.println(resultCheck);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		LaoCustConfigDTO d = new LaoCustConfigDTO();
		System.out.println(d.getIsIpCheck());
	}

	/**
	 * 
	 * @param request
	 * @return
	 *         <p>
	 *         参数校验
	 *         </p>
	 */
	private String paramCheck(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String custId = request.getParameter("custId");
		String iccid = request.getParameter("iccid");
		String md5 = request.getParameter("sign");
		String serverName = request.getParameter("serverName");
		boolean flag = true;
		if (StringUtil.isBlank(custId)) {
			result.put(respCode, "0011");
			result.put(respDesc, "custId is not null");
			flag = false;
		}
		if (StringUtil.isBlank(md5) && flag) {
			result.put(respCode, "0020");
			result.put(respDesc, "sign is not null");
			flag = false;
		}
		if (StringUtil.isBlank(serverName) && flag) {
			result.put(respCode, "0032");
			result.put(respDesc, "serverName is not null");
			flag = false;
		}
		if (StringUtil.isNoneBlank(iccid) && flag) {
			iccid = "898606".equals(request.getParameter("iccid").substring(0, 6)) ? ICCID.replaceLastChar(iccid)
					: iccid;
			IccidLibDto iccidLib=null;
			if(iccid.length()>18){
				iccidLib = userService.selectByIccid(iccid);
			}else if(iccid.length()==15){
				//IMSI查询
				iccid=trafficQueryService.getIccidByImsi(iccid);
				iccidLib = userService.selectByIccid(iccid);
			}

			if (null != iccidLib && !custId.equals(iccidLib.getCustid())) {
				result.put(respCode, "0053");
				result.put(respDesc, "iccid not in custId");
				flag = false;
			} else if (null == iccidLib) {
				result.put(respCode, "0054");
				result.put(respDesc, "iccid is not exist");
				flag = false;
			}
		}
		String resultStr = "";
		if (!flag) {
			Map<String, Object> rspInfo = new HashMap<String, Object>();
			rspInfo.put(resultInfo, result);
			resultStr = JSONObject.fromObject(rspInfo).toString();
		}
		return resultStr;
	}

	/**
	 * 
	 * @param request
	 * @return 获取到真正的客户端ip
	 */
	private static String getIp2(HttpServletRequest request) {
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

	/**
	 * 
	 * @param custId
	 *            每个客户每分钟访问的次数限制
	 */
	private boolean visitsLimit(String custId, int limit) {
		if ("".equals(custId) || null == custId) {
			return true;
		}
		boolean flag = true;
		try{
			if (null != redisClientService.get(custId)) {
				String value = redisClientService.get(custId);
				String val[] = value.split("_");
				SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date newDate = new Date();
				Date end = null;
				Date state = null;
				try {
					end = dfs.parse(dfs.format(newDate));
					state = dfs.parse(val[1]);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if ((end.getTime() - state.getTime()) / 1000 < 60) {
					int newValue = Integer.valueOf(val[0]) + 1;
					if (newValue > limit) {
						logger.info("每分钟只能反问" + limit + "次");
						flag = false;
					} else {
						logger.info("》》》继续访问《《《《《");
						redisClientService.set(custId, newValue + "_" + val[1]);
						flag = true;
					}
				} else {
					logger.info("》》》初始化《《《《《");
					redisClientService.set(custId, 1 + "_" + dfs.format(newDate));
					flag = true;
				}
			} else {
				logger.info("第一次访问");
				SimpleDateFormat dfs2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				redisClientService.set(custId, 1 + "_" + dfs2.format(new Date()));
				flag = true;
			}
		}catch(Exception e){
			flag = true;
		}
		return flag;
	}

	/**
	 * doGet 对Get方法的处理实现.
	 * 
	 * @param req
	 *            HttpServletRequest
	 * @param resp
	 *            HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 * @todo Implement this javax.servlet.http.HttpServlet method
	 */
	@RequestMapping(value = "serviceProvide", method = RequestMethod.GET)
	public void httpServerProvidePublic(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		httpServerProvide(request, response);
	}

	@RequestMapping(value = "appkey", method = RequestMethod.GET)
	public String createAppKey(String custId) {
		Map<String, String> reqInfo = new HashMap<String, String>();
		reqInfo.put("CUSTID", "123232");
		String appkey = appkeyAndIpCreate.getAppkey(reqInfo);
		logger.info("****************createAppKey*****************" + appkey);
		return appkey;
	}

	@RequestMapping(value = "ipApply", method = RequestMethod.GET)
	public String ipApply(String custId, String IpAddress) {
		logger.info("****************request ipApply********************");
		Map<String, String> reqInfo = new HashMap<String, String>();
		reqInfo.put("CUSTID", "12345678");
		reqInfo.put("IPADDRESS", "123.123.123.123");
		String result = appkeyAndIpCreate.registerIp(reqInfo);
		logger.info("****************" + result);
		return result;
	}
	

}
