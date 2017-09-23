package com.urt.web.web.order;

import java.io.IOException;
import java.util.Date;
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

import com.urt.dto.ApiMsgLogDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.apiMsg.ApiMsgLogService;
import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.device.RedisService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

@Controller
@RequestMapping("/jasperNotify")
public class JasperNotifyController {
	
	private static Log log = LogFactory.getLog(JasperNotifyController.class);
	@Autowired
	private RedisService redisClientService;
	@Autowired
	private UserService userService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private ApiMsgLogService apiMsgLogService;
    public static final int EXPIRESTIME = 30 * 60;
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    protected static String apiSecretKey = "default";
    
	@RequestMapping(value="/notify")
	public void orderGoods(HttpServletRequest request, HttpServletResponse response){
		System.out.println(">>>>>>>>>>>>>>>>>>>测试");
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
			return ;
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
			return ;
		}
        Map<String,String> result = deviceService.getUnicomNotify(params,"unicomNotify");
        log.info("=================联通推送的api完成===================");
        Long id = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
        String iccid="";
        ApiMsgLogDto apiMsgLog=new ApiMsgLogDto();
    	log.info(">>>>>>>>>>>>>>>>>>>>>>>>result iccid"+result.get("iccid"));
    	if(result.get("iccid")!=null){
    		iccid = result.get("iccid").toString();
//        		String goodsId = userService.getGoodsIdByIccid(iccid);
    		apiMsgLog.setMsgId(id);
    		apiMsgLog.setEventId(result.get("eventId"));
    		apiMsgLog.setEventType(result.get("eventType"));
    		apiMsgLog.setIccid(iccid);
    		apiMsgLog.setDealTag("0");
    		apiMsgLog.setDealResult("0");
    		apiMsgLog.setOperatorId(1);
    		apiMsgLog.setRecvTime(new Date());
    		apiMsgLog.setUpdateTime(new Date());
    		apiMsgLog.setOperatorId(1);
    		apiMsgLog.setUpdateAccount("admin");
    		
    	}
        apiMsgLogService.insertSelective(apiMsgLog);
        apiMsgLogService.sendApiMsgLog(id, iccid);
        return ; 
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
