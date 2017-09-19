package com.urt.web.http.devicePush;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.device.RedisService;
import com.urt.web.common.util.device.SoapConstant;

@Controller
@RequestMapping("/UnicomRateChange")
public class UnicomRateChangeController{


	private static Log log = LogFactory.getLog(UnicomRateChangeController.class);

	@Autowired
	private RedisService redisClientService;
	@Autowired
	private DeviceService deviceService;
	
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    protected static String apiSecretKey = "default";
    
    @RequestMapping(value = "notify", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> index(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = getRequestMap(this.getClass(),request);
    	String timestamp = request.getParameter("timestamp");
		String signature = request.getParameter("signature");
		if (!isValidSignature(timestamp, signature)) {
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
		
        // Verify the signature2 (SHA-256). Don't process the event if the signature2 is invalid.
        if (!isValidSignature256(timestamp, signature)) {
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
		
		
        Map<String,String> result = deviceService.getUnicomNotify(params,"UnicomRateChange");
		
		log.info("================开始清理缓存=======================");
		log.info("============结果========="+result.toString());
		if (result.get("eventType").equals(SoapConstant.CHANGE)) {
			log.info("=============="+result.get("iccid") + "-" + SoapConstant.NOTITY_TYPE_FLOW);
			//清除缓存
			redisClientService.del(result.get("iccid") + "-" + SoapConstant.NOTITY_TYPE_FLOW);
			Boolean exists = redisClientService.exists(result.get("iccid") + "-" + SoapConstant.NOTITY_TYPE_FLOW);
			log.info("=====键是否存在==="+exists);
		} 
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("code", "0");
		return resultMap;
	}
	
	private   boolean isValidSignature(String timestamp, String signature) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(apiSecretKey.getBytes(), HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(keySpec);
            String expectedSignature = new String(Base64.encodeBase64(mac.doFinal(timestamp.getBytes())));
            if (expectedSignature.equals(signature)) {
                return true;
            } else {
                System.err.println("Invalid signature: "+signature+" does not match expected signature: "+expectedSignature);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error verifying signature: "+e);
            return false;
        }
    }


    private   boolean isValidSignature256(String timestamp, String signature2) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(apiSecretKey.getBytes(), HMAC_SHA256_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(keySpec);
            String expectedSignature2 = new String(Base64.encodeBase64(mac.doFinal(timestamp.getBytes())));
            if (expectedSignature2.equals(signature2)) {
                return true;
            } else {
                System.err.println("Invalid signature2: "+signature2+" does not match expected signature2: "+expectedSignature2);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error verifying signature2: "+e);
            return false;
        }
    }
    private Map<String, String> getRequestMap(Class<?> clazz,HttpServletRequest httpServletRequest) {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = httpServletRequest.getParameterMap();
		StringBuffer buffer = new StringBuffer(clazz.getSimpleName());
		for(Map.Entry<String, String[]> entry : requestParams.entrySet()) {
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
