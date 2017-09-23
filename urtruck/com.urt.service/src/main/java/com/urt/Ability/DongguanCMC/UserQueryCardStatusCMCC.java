package com.urt.Ability.DongguanCMC;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.EcCMCC.utils.DigestUtils;
import com.urt.Ability.collect.UserQueryCardStatus;
import com.urt.mapper.ext.IccidLibExtMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.po.LaoUser;
import com.urt.utils.HttpClientUtil;
@Service(value="userQueryCardStatusCMCC")
public class UserQueryCardStatusCMCC extends UserQueryCardStatus{
	
	private static final String url="http://183.230.96.66:8087/v2/";
	protected  String appid="CE8O2GB";
	protected  String passwd="LXDD85";
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private MemberMethodImpl memberMethodImpl;
	@Autowired
	private IccidLibExtMapper iccidLibExtMapper;
	
	@Autowired
	private LaoUserExtMapper laoUserDao;

	@Override
	protected ResultMsg sendMessage(Object... args) {
		// TODO Auto-generated method stub
		/*if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		String iccid = (String) args[0];
		ResultMsg msg = new ResultMsg();
		String status = null;
		
		Long beginTime = System.currentTimeMillis();
		log.info("................激活前查询设备状态...................time:"
				+ beginTime);

		try {
			IccidLib iccidLib = iccidLibExtMapper.selectByIccid(iccid);
			status = memberMethodImpl.statusQuery(iccidLib.getMsisdn());
		} catch (Exception e) {
			// TODO: handle exception
		}
				
		if(status != null){
			JSONObject json = JSON.parseObject(status);
			msg.setSuccess(true);
			msg.setOutMessage(status);
			msg.setOpeartorsDealCode("success");
			msg.setOpeartorsDealRst("0");
			msg.setStatus(json.getString("status"));
		}else{
			msg.setOutMessage(status);
			msg.setOpeartorsDealCode("failed");
			msg.setOpeartorsDealRst("1");
		}
		
		log.info("................激活前查询设备状态.......... Total cost: "
				+ (System.currentTimeMillis() - beginTime) / 1000.0);
		return msg;*/
		if (args == null || args.length == 0){
			throw new IllegalArgumentException();
		}
		String iccid = (String) args[0];
		ResultMsg msg = new ResultMsg();
		String status = null;
		Long beginTime = System.currentTimeMillis();
		log.info("................EC浙江移动卡状态查询....................time:"
				+ beginTime);

		try {
			LaoUser laoUser = laoUserDao.selectByIccid(iccid);
			
			String method="userstatusrealsingle";
			String ebid="0001000000009";
			String transid=appid+DigestUtils.dataString();
			String token = DigestUtils.sha256Hex(appid+passwd+transid);
			String httpUrl=url+method+"?"+"appid="+appid+"&"+"transid="
					+transid+"&"+"ebid="+ebid+"&"+"token="+token+"&msisdn="+laoUser.getMsisdn();
			status = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			//status = ecMethod.userstatusrealsingle(laoUser.getMsisdn());
		} catch (Exception e) {
			log.info("................调用EC浙江移动卡状态查询异常.............");
			e.printStackTrace();
		}
				
		JSONObject json = JSON.parseObject(status);
		if(!StringUtils.isBlank(status) && "0".equals(json.getString("status"))){
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
		
		log.info("................EC浙江移动卡状态查询.......... Total cost: "
				+ (System.currentTimeMillis() - beginTime) / 1000.0);
		return msg;
	}

}
