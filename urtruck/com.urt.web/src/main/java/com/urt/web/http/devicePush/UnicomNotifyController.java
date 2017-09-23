package com.urt.web.http.devicePush;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.device.RedisService;
import com.urt.web.common.util.device.SoapConstant;

@Controller
@RequestMapping("/unicomNotify")
public class UnicomNotifyController {

	private static Log log = LogFactory.getLog(UnicomNotifyController.class);
	@Autowired
	private RedisService redisClientService;
	@Autowired
	private DeviceService deviceService;
	public static final int EXPIRESTIME = 30 * 60;
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
	protected static String apiSecretKey = "default";

	@RequestMapping(value = "/notify")
	public void index(HttpServletRequest request, HttpServletResponse response) {
		System.out.println(">>>>>>>>>>>测试联想推送的剩余50M流量提醒<<<<<<<<<<");
		Map<String, String> params = getRequestMap(this.getClass(), request);
		if (!isValidSignature(params.get("timestamp"), params.get("signature"))) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("text/html");
			try {
				response.getWriter().println("Bad signature");
				System.out.println("Bad signature");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
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
			return;
		}
		Map<String, String> result = deviceService.getUnicomNotify(params, "unicomNotify");
		log.info("=================联通推送50M流量提醒的api完成===================");
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>result iccid" + (String) result.get("iccid"));
		log.info("============结果========="+result.toString());
		if (result.get("iccid") != null) {
			if (result.get("eventType").equals(SoapConstant.NOTITY_TYPE_FLOW)) {
				log.info(">>>>>>>>>>>>key======" + (String) result.get("iccid") + "-"+ (String) result.get("eventType"));
				redisClientService.set(result.get("iccid") + "-" + result.get("eventType"), SoapConstant.DATA_LIMIT);
			} else if (result.get("eventType").equals(SoapConstant.NOTITY_TYPE_DATE)) {
				log.info(">>>>>>>>>>>>key======" + (String) result.get("iccid") + "-"+ (String) result.get("eventType"));
				redisClientService.set(result.get("iccid") + "-" + result.get("eventType"), SoapConstant.DATE_LIMIT);
			}
		}
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

	private Map<String, String> getRequestMap(Class<?> clazz, HttpServletRequest httpServletRequest) {
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
		log.info(buffer.toString());
		return params;
	}

}
