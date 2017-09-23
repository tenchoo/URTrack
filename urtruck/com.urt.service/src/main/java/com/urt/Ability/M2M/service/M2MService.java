package com.urt.Ability.M2M.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.util.log.Log;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.urt.Ability.M2M.ConstantsUntil;
import com.urt.Ability.M2M.EncryptUtils;
import com.urt.utils.HttpClientUtil;

/**
 * 物联网API
 * 
 * @author sunhao
 *
 */
@Service("m2MService")
public class M2MService {

	// 物联网流量查询
	@SuppressWarnings("deprecation")
	public String M2M(String callNbr, String startTime, String endTime) {

		String httpUrl = ConstantsUntil.M2MURL;
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.reqTime, new Date().toLocaleString());
		paramMap.put(ConstantsUntil.service_name, "M2M");
		Map<String, String> map = new HashMap<String, String>();
		map.put("callNbr", callNbr);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		paramMap.put(ConstantsUntil.service_parameter, JSON.toJSONString(map));
		System.out.println(JSON.toJSONString(paramMap));
		Log.info(httpUrl);
		return HttpClientUtil.getInstance().sendHttpPost(httpUrl,
				JSON.toJSONString(paramMap));
	}

	public static void main(String[] args) {
		M2MService service = new M2MService();
		System.out.println(service.M2M("18912312312", "20150809", "20160809"));
		System.out.println(service.M2MBalance("18912312312"));
	}

	// 物联网余额查询
	@SuppressWarnings("deprecation")
	public String M2MBalance(String callNbr) {

		String httpUrl = ConstantsUntil.M2MURL;
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.reqTime, new Date().toLocaleString());
		paramMap.put(ConstantsUntil.service_name, "M2MBalance");
		Map<String, String> map = new HashMap<String, String>();
		map.put("callNbr", callNbr);
		paramMap.put(ConstantsUntil.service_parameter, JSON.toJSONString(map));
		System.out.println(JSON.toJSONString(paramMap));
		Log.info(httpUrl);
		return HttpClientUtil.getInstance().sendHttpPost(httpUrl,
				JSON.toJSONString(paramMap));
	}

	// 物联网套餐查询
	@SuppressWarnings("deprecation")
	public String M2MPakage(String callNbr) {

		String httpUrl = ConstantsUntil.M2MURL;
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.reqTime, new Date().toLocaleString());
		paramMap.put(ConstantsUntil.service_name, "M2MPakage");
		Map<String, String> map = new HashMap<String, String>();
		map.put("callNbr", callNbr);
		paramMap.put(ConstantsUntil.service_parameter, JSON.toJSONString(map));
		System.out.println(JSON.toJSONString(paramMap));
		Log.info(httpUrl);
		return HttpClientUtil.getInstance().sendHttpPost(httpUrl,
				JSON.toJSONString(paramMap));
	}

	//物联网停复机功能
	@SuppressWarnings("deprecation")
	public String M2MDisabledNumber(String orderTypeId, String accessNumber) {

		String httpUrl = ConstantsUntil.M2MURL;
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.reqTime, new Date().toLocaleString());
		paramMap.put(ConstantsUntil.service_name, "M2MDisabledNumber");
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderTypeId", orderTypeId);
		map.put("accessNumber", accessNumber);
		paramMap.put(ConstantsUntil.service_parameter, JSON.toJSONString(map));
		System.out.println(JSON.toJSONString(paramMap));
		Log.info(httpUrl);
		return HttpClientUtil.getInstance().sendHttpPost(httpUrl,
				JSON.toJSONString(paramMap));
	}

	// 物联网卡状态查询功能
	@SuppressWarnings("deprecation")
	public String M2MCardStatus(String tcdMdn, String iccid) {

		String httpUrl = ConstantsUntil.M2MURL;
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.reqTime, new Date().toLocaleString());
		paramMap.put(ConstantsUntil.service_name, "M2MCardStatus");
		Map<String, String> map = new HashMap<String, String>();
		map.put("tcdMdn", tcdMdn);
		map.put("iccid", iccid);
		paramMap.put(ConstantsUntil.service_parameter, JSON.toJSONString(map));
		System.out.println(JSON.toJSONString(paramMap));
		Log.info(httpUrl);
		return HttpClientUtil.getInstance().sendHttpPost(httpUrl,
				JSON.toJSONString(paramMap));
	}

	// 物联网定位功能
	@SuppressWarnings("deprecation")
	public String M2MLocation(String callNbr) {

		String httpUrl = ConstantsUntil.M2MURL;
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.reqTime, new Date().toLocaleString());
		paramMap.put(ConstantsUntil.service_name, "M2MLocation");
		Map<String, String> map = new HashMap<String, String>();
		map.put("callNbr", callNbr);
		paramMap.put(ConstantsUntil.service_parameter, JSON.toJSONString(map));
		System.out.println(JSON.toJSONString(paramMap));
		Log.info(httpUrl);
		return HttpClientUtil.getInstance().sendHttpPost(httpUrl,
				JSON.toJSONString(paramMap));
	}

	// 物联网接入号码查询功能
	@SuppressWarnings("deprecation")
	public String M2MLocation(String iccid, String imsi) {

		String httpUrl = ConstantsUntil.M2MURL;
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.reqTime, new Date().toLocaleString());
		paramMap.put(ConstantsUntil.service_name, "M2MLocation");
		Map<String, String> map = new HashMap<String, String>();
		map.put("iccid", iccid);
		map.put("imsi", imsi);
		paramMap.put(ConstantsUntil.service_parameter, JSON.toJSONString(map));
		System.out.println(JSON.toJSONString(paramMap));
		Log.info(httpUrl);
		return HttpClientUtil.getInstance().sendHttpPost(httpUrl,
				JSON.toJSONString(paramMap));
	}
}
