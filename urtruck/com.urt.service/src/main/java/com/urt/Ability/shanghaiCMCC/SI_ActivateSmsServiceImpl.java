package com.urt.Ability.shanghaiCMCC;

import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.shanghaiCMCC.utils.ConstantUtil;
import com.urt.interfaces.ShangHaiCMC.SI_ActivateSmsService;
import com.urt.utils.HttpClientUtil;
/**
 * 短信开关操作
 *
 */
@Service(value="sI_ActivateSmsService")
public class SI_ActivateSmsServiceImpl implements SI_ActivateSmsService {

	@Override
	public String activateSmaService(String msisdn, String oper_type) {
		
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("MSISDN", msisdn);
			jsonObject.put("OPER_TYPE", oper_type);
			jsonObject.put("Request_ID", "");
			jsonObject.put("Request_Date_Time", "");
			String httpUrl = ConstantUtil.URL + "/SI_SmsStatusManagement";
			HttpClientUtil instance = HttpClientUtil.getInstance();
			String result = instance.sendHttpPost(httpUrl,jsonObject.toJSONString());
			
			return result;
	}

}
