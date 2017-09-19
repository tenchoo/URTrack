package com.urt.Ability.DongguanCMC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.urt.Ability.collect.QueryTelByIccid;
import com.urt.utils.HttpClientUtil;

@Service("queryTelByIccidCMCC")
public class QueryTelByIccidCMCC extends QueryTelByIccid {
	Logger log = Logger.getLogger(getClass());

	@Override
	protected ResultMsg sendMessage(Object... args) {
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		String iccid = (String) args[0];
		ResultMsg msg = new ResultMsg();
		msg.setSuccess(true);
		msg.setTel(iccid);
		msg.setInputMessage("");
		msg.setOutMessage("");
		msg.setOpeartorsDealCode("0");
		return msg;
	}

	@Override
	protected ResultMsg queryOperatorPlan(Object... args) {
		// TODO Auto-generated method stub
		String msisdn = "";
		if (args == null || args.length == 0 || args.length < 3)
			throw new IllegalArgumentException();
		msisdn = (String) args[0];
		String method = "iot.member.package.query";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.msisdn, msisdn);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
				+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&"
				+ ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		String resultStr = HttpClientUtil.getInstance().sendHttpGet(httpUrl);

		JSONObject jsonObject;
		ResultMsg msg=new ResultMsg();
		try {
			jsonObject = new JSONObject(resultStr);
			Object object2 = jsonObject.get("PackageVos");
			JSONArray array = new JSONArray(object2.toString());
			List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
			for (int i = 0; i < array.length(); i++) {
				Map<String, Object> map=new HashMap<String, Object>();
				Object object = array.get(i);
				jsonObject = new JSONObject(object.toString());
				jsonObject = new JSONObject(
						jsonObject.getString("packageInfoVo"));
				String code = jsonObject.get("packageCode").toString();
				String startTime = jsonObject.get("beginTime").toString();
				String endTime = jsonObject.get("endTime").toString();
				map.put("planCode", code);
				map.put("startTime", startTime);
				map.put("endTime", endTime);
				System.out.println(">>>>>>>>>>" + code + ";" + startTime + ";"
						+ endTime);
				list.add(map);
				
			}
			msg.setInputMessage(httpUrl);
			msg.setOutMessage(resultStr);
			msg.setSuccess(true);
			msg.setOpeartorsDealCode("0");
			msg.setPlans(list);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg.setInputMessage(httpUrl);
			msg.setOutMessage(resultStr);
			msg.setSuccess(true);
			msg.setOpeartorsDealCode("1");
		}
		return msg;
	}

}
