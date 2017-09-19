package com.urt.Ability.shanghaiCMCC;
import java.util.Date;

/**
 * 变更数据业务状态和 APN
 */
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.shanghaiCMCC.utils.CommonUtils;
import com.urt.Ability.shanghaiCMCC.utils.ConstantUtil;
import com.urt.dto.ShResultDto;
import com.urt.interfaces.ShangHaiCMC.SI_ActivateAPN;
import com.urt.utils.HttpClientUtil;
@Service(value="sI_ActivateAPN")
public class SI_ActivateAPNImpl implements SI_ActivateAPN {
	
	@Override
	public ShResultDto activeStatusAndAPN(String msisdn, String oper_type,String apn1_oper, String apn2_oper) {
			//封装数据
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("MSISDN", msisdn);
		jsonObject.put("OPER_TYPE", oper_type);
		jsonObject.put("Request_ID", "");
		jsonObject.put("Request_Date_Time", CommonUtils.getFormate(new Date()));
		jsonObject.put("APN1_Oper", apn1_oper);
		jsonObject.put("APN2_Oper", apn2_oper);
		
		
		String httpUrl = ConstantUtil.URL+"/SI_ActivateAPN";
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String result = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		//是否插入数据库（lao_service_status表）
		return null;
	}

	@Override
	public ShResultDto activeStatus(String msisdn, String oper_type) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("MSISDN", msisdn);
		jsonObject.put("OPER_TYPE", oper_type);
		jsonObject.put("Request_ID", "");
		jsonObject.put("Request_Date_Time", CommonUtils.getFormate(new Date()));
		
		String httpUrl = ConstantUtil.URL+"/SI_ActivateAPN";
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String result = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		
		return null;
	}

}
