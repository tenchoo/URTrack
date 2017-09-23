package com.urt.web.http.devicePush;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.device.RedisService;
import com.urt.web.common.util.device.SoapConstant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Controller
@RequestMapping("/UnicomNotifyChange")
public class UnicomNotifyChangeController {

	@Autowired
	private RedisService redisClientService;
	@Autowired
	private DeviceService deviceService;

	public static final int EXPIRESTIME = 30 * 60;
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
	protected static String apiSecretKey = "default";
	private static Log log = LogFactory.getLog(UnicomNotifyChangeController.class);

	@RequestMapping(value = "notify", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> index(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = getRequestMap(this.getClass(), request);
		System.out.println(">>>>>>>>>>>>>>>>>>>联通流量使用1G提醒<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		String timestamp = request.getParameter("timestamp");
		String signature = request.getParameter("signature");
		if (!isValidSignature(params.get("timestamp"), params.get("signature"))) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("text/html");
			try {
				response.getWriter().println("Bad signature");
				System.out.println("Bad signature");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		if (!isValidSignature256(params.get("timestamp"), params.get("signature2"))) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("text/html");
			try {
				response.getWriter().println("Bad signature2");
				System.out.println("Bad signature2");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		Map<String, String> result = deviceService.getUnicomNotify(params, "UnicomNotifyChange");
		log.info("=================联通推送流量使用1G的api完成===================");
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>result iccid" + (String) result.get("iccid")+"======="+result.toString());
		if (result.get("iccid") != null) {
			if (result.get("eventType").equals(SoapConstant.NOTITY_TYPE_FLOW)) {
				log.info(">>>>>>>>>>>>key======" + (String) result.get("iccid") + "-"+ (String) result.get("eventType"));
				redisClientService.set(result.get("iccid") + "-" + result.get("eventType"), SoapConstant.DATA_LIMIT);
			} else if (result.get("eventType").equals(SoapConstant.NOTITY_TYPE_DATE)) {
				log.info(">>>>>>>>>>>>key======" + (String) result.get("iccid") + "-"+ (String) result.get("eventType"));
				redisClientService.set(result.get("iccid") + "-" + result.get("eventType"), SoapConstant.DATE_LIMIT);
			}
		}
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("code", "0");
		return resultMap;
	}
	private boolean isValidSignature(String timestamp, String signature) {
		try {
			SecretKeySpec keySpec = new SecretKeySpec(apiSecretKey.getBytes(), HMAC_SHA1_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(keySpec);
			String expectedSignature = new String(Base64.encodeBase64(mac.doFinal(timestamp.getBytes())));
			if (expectedSignature.equals(signature)) {
				return true;
			} else {
				System.err.println(
						"Invalid signature: " + signature + " does not match expected signature: " + expectedSignature);
				return false;
			}
		} catch (Exception e) {
			System.err.println("Error verifying signature: " + e);
			return false;
		}
	}

	private boolean isValidSignature256(String timestamp, String signature2) {
		try {
			SecretKeySpec keySpec = new SecretKeySpec(apiSecretKey.getBytes(), HMAC_SHA256_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
			mac.init(keySpec);
			String expectedSignature2 = new String(Base64.encodeBase64(mac.doFinal(timestamp.getBytes())));
			if (expectedSignature2.equals(signature2)) {
				return true;
			} else {
				System.err.println("Invalid signature2: " + signature2 + " does not match expected signature2: "
						+ expectedSignature2);
				return false;
			}
		} catch (Exception e) {
			System.err.println("Error verifying signature2: " + e);
			return false;
		}
	}

	public Map<String, String> getRequestMap(Class<?> clazz, HttpServletRequest httpServletRequest) {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = httpServletRequest.getParameterMap();
		StringBuffer buffer = new StringBuffer(clazz.getSimpleName());
		for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
			String name = entry.getKey();
			String[] values = entry.getValue();
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			buffer.append(" key:<" + name + "> value:<" + valueStr + ">");
			params.put(name, valueStr);
		}
		return params;
	}
}
