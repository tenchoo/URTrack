package com.urt.Ability.ZheJiangCMC;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.collect.UserQueryCardStatus;
import com.urt.interfaces.ZheJiangCMC.QueryMethodZJ;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.po.LaoUser;
/**
 * 浙江移动卡状态查询
 * @author admin
 *
 */
@Service("userQueryCardStatusCMCCZJ")
public class UserQueryCardStatusCMCCZJ extends UserQueryCardStatus {
      
	
	@Autowired
	private  QueryMethodZJ ecMethod;
	
	@Autowired
	private LaoUserExtMapper laoUserDao;
	
	Logger log = Logger.getLogger(getClass());
	@Override
	protected ResultMsg sendMessage(Object... args) {
		if (args == null || args.length == 0){
			throw new IllegalArgumentException();
		}
		String iccid = (String) args[0];
		ResultMsg msg = new ResultMsg();
		String status = null;
		Long beginTime = System.currentTimeMillis();
		log.info("................浙江移动卡状态查询....................time:"
				+ beginTime);

		try {
			LaoUser laoUser = laoUserDao.selectByIccid(iccid);
			status = ecMethod.userstatusrealsingle(laoUser.getMsisdn());
		} catch (Exception e) {
			log.info("................调用浙江移动卡状态查询异常.............");
			e.printStackTrace();
		}
				
		JSONObject json = JSON.parseObject(status);
		if(!StringUtils.isBlank(status) && "0".equals(json.get("status"))){
			msg.setSuccess(true);
			msg.setOutMessage(status);
			msg.setOpeartorsDealCode("success");
			msg.setOpeartorsDealRst("0");
			JSONArray resultArray = json.getJSONArray("result");
			JSONObject resultJSONObj = (JSONObject) resultArray.get(0);
			msg.setStatus(resultJSONObj.getString("STATUS"));
		}else{
			msg.setOutMessage(status);
			msg.setOpeartorsDealCode("failed");
			msg.setOpeartorsDealRst("1");
		}
		
		log.info("................浙江移动卡状态查询.......... Total cost: "
				+ (System.currentTimeMillis() - beginTime) / 1000.0);
		return msg;
	}

}
