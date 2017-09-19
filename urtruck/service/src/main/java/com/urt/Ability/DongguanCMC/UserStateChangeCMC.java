package com.urt.Ability.DongguanCMC;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.collect.UserStateChange;
import com.urt.mapper.ext.IccidLibExtMapper;
import com.urt.po.IccidLib;
import com.urt.utils.HttpClientUtil;

/**
 * 移动服务状态变更
 * 
 * @author sunhao
 *
 */
@Service("userStateChangeCMC")
public class UserStateChangeCMC extends UserStateChange {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private MemberMethodImpl memberMethod;

	@Autowired
	private IccidLibExtMapper iccidLibExtDAO;

	@Override
	protected ResultMsg sendMessage(Object... args) {
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		ResultMsg resultMsg = new ResultMsg();
		String iccid = (String) args[0];
		String newState = null;
		Integer serviceId = -1;
		String msisdn=null;
		if (args.length > 1)
			newState = (String) args[1];
		if (args.length > 2)
			serviceId = (Integer) args[2];

		if (StringUtils.isNotBlank(iccid)) {
			IccidLib selectByIccid = iccidLibExtDAO.selectByIccid(iccid);
			if (selectByIccid != null) {
				iccid = selectByIccid.getMsisdn();
				msisdn=selectByIccid.getMsisdn();
			}
		}
		log.info(">>>>>>>>>>>>>>>>>>>移动转换的新的卡状态:" + newState);
		String method = "iot.member.simstate.change";
		Map<String, String> paramMap = EncryptUtils.genComMap();
		paramMap.put(ConstantsUntil.method, method);
		paramMap.put(ConstantsUntil.ncode, newState);
		paramMap.put(ConstantsUntil.msisdn, msisdn);
		String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr() + "&" + ConstantsUntil.msisdn + "="
				+ msisdn + "&" + ConstantsUntil.ncode + "=" + newState + "&" + ConstantsUntil.sign + "=" + secret + "&"
				+ ConstantsUntil.method + "=" + method;
		// return HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		// String method = "iot.member.dataservicestate.change";
		// Map<String, String> paramMap = EncryptUtils.genComMap();
		// paramMap.put(ConstantsUntil.method, method);
		// paramMap.put(ConstantsUntil.optType, newState);
		// paramMap.put(ConstantsUntil.msisdn, iccid);
		// String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
		// String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
		// + "&" + ConstantsUntil.msisdn + "=" + iccid + "&"
		// + ConstantsUntil.optType + "=" + newState + "&"
		// + ConstantsUntil.sign + "=" + secret + "&"
		// + ConstantsUntil.method + "=" + method;
		log.info("请求的url=" + httpUrl);
		// 记录入参
		resultMsg.setInputMessage(httpUrl);
		String reuslt = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		JSONObject json = JSON.parseObject(reuslt);

		resultMsg.setOutMessage(reuslt);
		resultMsg.setOpeartorsDealCode(json.getString("message"));
		if (json.containsKey("resultCode")) {
			if (json.getString("resultCode").equals("1")) {
				resultMsg.setOpeartorsDealRst("1"); // 0成功1失败
			} else {
				resultMsg.setSuccess(true);
				resultMsg.setOpeartorsDealRst("0"); // 0成功1失败
			}

		} else if (json.containsKey("code")) {
			if (json.getString("code").equals("1")) {
				resultMsg.setOpeartorsDealRst("1"); // 0成功1失败
			} else {
				resultMsg.setSuccess(true);
				resultMsg.setOpeartorsDealRst("0"); // 0成功1失败
			}
		}
		return resultMsg;
	}

}
