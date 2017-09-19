package com.urt.Ability.shanghaiCMCC;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.shanghaiCMCC.utils.ConstantUtil;
import com.urt.interfaces.ShangHaiCMC.SI_ActivateVoiceService;
import com.urt.utils.HttpClientUtil;
/**
 * 语音功能开通及关闭服务
 */
@Service("sI_ActivateVoiceService")
public class SI_ActivateVoiceServiceImpl implements SI_ActivateVoiceService {

	@Override
	public String activeVoiceService(String msisdn, String oprcode, String oprtime,
			String introamstatus, String clipstatus, String ocsiprov, String ocsitplid, String tcsiprov,
			String tcsitplid) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Request_ID", "");
		jsonObject.put("MSISDN", msisdn);
		jsonObject.put("OprCode", oprcode);
		jsonObject.put("OprTime", oprtime);
		jsonObject.put("IntRoamStatus", introamstatus);
		jsonObject.put("CLIPStatus", clipstatus);
		jsonObject.put("CAMELStaus.OCSIPROV", ocsiprov);
		if(null != ocsitplid && !"".equals(ocsitplid)){
			jsonObject.put("CAMELStaus.OCSITPLID", ocsitplid);
		}
		jsonObject.put("CAMELStaus.TCSIPROV", tcsiprov);
		if(null != tcsitplid && !"".equals(tcsitplid)){
			jsonObject.put("CAMELStaus.TCSITPLID", tcsitplid);
		}
		jsonObject.put("Request_Date_Time", "");
		String httpUrl = ConstantUtil.URL + "/SI_VoiceServiceManagement";
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String result = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		return result;
	}

}
