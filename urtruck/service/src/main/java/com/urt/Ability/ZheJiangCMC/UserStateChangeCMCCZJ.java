package com.urt.Ability.ZheJiangCMC;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.collect.UserStateChange;
import com.urt.interfaces.ZheJiangCMC.MemberMethodZJ;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.mapper.ext.TradeExtMapper;
import com.urt.po.LaoUser;
import com.urt.po.Trade;

@Service("userStateChangeCMCCZJ")
public class UserStateChangeCMCCZJ extends UserStateChange{
	private Logger log=Logger.getLogger(getClass());
	@Autowired
	private MemberMethodZJ memberMethod;

	@Autowired
	private LaoUserExtMapper laoUserExtDao;
	
	@Autowired
	private TradeExtMapper tradeExtDAO ; //业务台帐主表

	@Override
	protected ResultMsg sendMessage(Object... args) {
		//iccid,newStateCode,serviceId,tradeId
		ResultMsg msg = new ResultMsg();
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		String iccid = (String) args[0];
		String newStateCode = "";
		String msisdn= "";
		String tradeId= "";
		if(args.length > 1)
			newStateCode = (String)args[1];
		if(args.length > 2)
			tradeId = (String)args[3];

		if (StringUtils.isNotBlank(iccid)) {
			LaoUser laoUser = laoUserExtDao.selectByIccid(iccid);
			if (laoUser != null) {
				msisdn = laoUser.getMsisdn();
			}
		}
		
		log.info(">>>>>>>>>>>>>>>>>>>进入UserStateChangeCMCCZJ的方法sendMessage,入参args:" + args.toString());
		Trade trade = tradeExtDAO.selectByTradeId(Long.parseLong(tradeId));
		if ("130".equals(trade.getTradeTypeCode()) || "140".equals(trade.getTradeTypeCode())) {
			// 1 卡状态变更
			log.info(">>>>>>>>>>>>>>>>>>>浙江移动转换的新的卡状态:" + newStateCode);
			String retGPRS = "";
			if ("45".equals(newStateCode)) {
				msg.setInputMessage(msisdn +",K0022");
				//先复机，在开启
				retGPRS = memberMethod.stopGPRS(msisdn, "K0022");
				log.info(">>>>>>>>>>>>>>>>>>>(浙江移动接口)浙江移动变更卡状态,营业复机接口返回:retGPRS1：" + retGPRS);
				retGPRS = memberMethod.resetGPRS(msisdn);
				log.info(">>>>>>>>>>>>>>>>>>>(浙江移动接口)浙江移动变更卡状态,开启GPRS接口返回:retGPRS2：" + retGPRS);
				msg.setOutMessage(retGPRS);
				if (StringUtils.isNotBlank(retGPRS)) {
					JSONObject parseObject = JSON.parseObject(retGPRS);
					JSONObject jsonObject = parseObject.getJSONObject("RESP_PARAM");
					JSONObject jsonObject2 = jsonObject.getJSONObject("PUB_INFO");
					if (jsonObject2 != null) {
						String return_res = jsonObject2.getString("RETURN_RESULT");
						if ("0".equals(return_res) ) {
							msg.setSuccess(true);
							msg.setOpeartorsDealCode("success");
							msg.setOpeartorsDealRst("0"); 
						} else {
							msg.setSuccess(false);
							msg.setOpeartorsDealCode("false");
							msg.setOpeartorsDealRst(return_res); 
						}
					}
				}
			} else {
				//GPRS停机  T0022
				msg.setInputMessage(msisdn +",T0022");
				retGPRS = memberMethod.stopGPRS(msisdn, "T0022");
				log.info(">>>>>>>>>>>>>>>>>>>(浙江移动接口)浙江移动变更卡状态,营业停机接口返回:retGPRS2：" + retGPRS);
				msg.setOutMessage(retGPRS);
				if (StringUtils.isNotBlank(retGPRS)) {
					JSONObject parseObject = JSON.parseObject(retGPRS);
					JSONObject jsonObject = parseObject.getJSONObject("RESP_PARAM");
					JSONObject jsonObject2 = jsonObject.getJSONObject("PUB_INFO");
					if (jsonObject2 != null) {
						String return_res = jsonObject2.getString("RETURN_RESULT");
						if ("0".equals(return_res) ) {
							msg.setSuccess(true);
							msg.setOpeartorsDealCode("success");
							msg.setOpeartorsDealRst("0"); 
						} else {
							msg.setSuccess(false);
							msg.setOpeartorsDealCode("false");
							msg.setOpeartorsDealRst(return_res); 
						}
					} else{
						msg.setSuccess(false);
						msg.setOpeartorsDealCode("false");
						msg.setOpeartorsDealRst(""); 
					}
				} else{
					msg.setSuccess(false);
					msg.setOpeartorsDealCode("false");
					msg.setOpeartorsDealRst(""); 
				}
			}
		} else {
			// 2 激活
			msg.setInputMessage(msisdn);
			String retStr = memberMethod.cardActive(msisdn);
			log.info(">>>>>>>>>>>>>>>>>>>(浙江移动接口)浙江移动激活,返回:retGPRS2：" + retStr);
			msg.setOutMessage(retStr);
			if (StringUtils.isNotBlank(retStr)) {
				JSONObject parseObject = JSON.parseObject(retStr);
				JSONObject jsonObject = parseObject.getJSONObject("RESP_PARAM");
				JSONObject jsonObject2 = jsonObject.getJSONObject("PUB_INFO");
				if (jsonObject2 != null) {
					String return_res = jsonObject2.getString("RETURN_RESULT");
					if ("0".equals(return_res) ) {
						msg.setSuccess(true);
						msg.setOpeartorsDealCode("success");
						msg.setOpeartorsDealRst("0"); 
					} else {
						msg.setSuccess(false);
						msg.setOpeartorsDealCode("false");
						msg.setOpeartorsDealRst(return_res); 
					}
				} else{
					msg.setSuccess(false);
					msg.setOpeartorsDealCode("false");
					msg.setOpeartorsDealRst(""); 
				}
			} else{
				msg.setSuccess(false);
				msg.setOpeartorsDealCode("false");
				msg.setOpeartorsDealRst(""); 
			}
		}
		
		return msg;
	}

}
