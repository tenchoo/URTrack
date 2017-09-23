package com.urt.Ability.shanghaiCMCC;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.shanghaiCMCC.utils.ConstantUtil;
import com.urt.interfaces.ShangHaiCMC.SI_ChangeSIMPhase;
import com.urt.utils.HttpClientUtil;
@Service("sI_ChangeSIMPhase")
public class SI_ChangeSIMPhaseImpl implements SI_ChangeSIMPhase {

	@Override
	public String changeSIMPhase(String eid, String msisdn) {
		
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Request_ID", "");
				jsonObject.put("Request_Date_Time", "");
				jsonObject.put("EID", eid);
				jsonObject.put("MSISDN", msisdn);
				HttpClientUtil instance = HttpClientUtil.getInstance();
				String httpUrl = ConstantUtil.URL + "/SI_ChangeSIMPhase";
				String result = instance.sendHttpPost(httpUrl,jsonObject.toJSONString());
				//System.out.println(result);
				return result;
			
	}

}
