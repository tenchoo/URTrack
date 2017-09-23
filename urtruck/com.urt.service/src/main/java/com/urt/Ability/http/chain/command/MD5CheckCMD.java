package com.urt.Ability.http.chain.command;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.log4j.Logger;

import com.urt.Ability.http.log.AccessInDBLogger;
import com.urt.Ability.http.util.Constants;
import com.urt.Ability.http.util.TokenUtils;
import com.urt.mapper.LaoKeyManagementMapper;
import com.urt.mapper.LaoPeripheralSysAccessLogMapper;
import com.urt.po.LaoKeyManagement;

public class MD5CheckCMD implements Command{
	/**
	 * 日志记录器
	 */
	private static final Logger logger = Logger.getLogger(MD5CheckCMD.class);
	
	private LaoPeripheralSysAccessLogMapper laoToDb;
	
	private LaoKeyManagementMapper laoKey;
	@Override
	public boolean execute(Context ctx) throws Exception {
		// TODO Auto-generated method stub
	logger.info("*******************MD5校验*****************");
	laoKey = (LaoKeyManagementMapper) ctx.get("laoKey");
	LaoKeyManagement laoKeyPO = laoKey.selectByCustId(String.valueOf(ctx.get(Constants.CustID)));
	boolean flag = false;
	if(null == laoKeyPO){
		ctx.put(Constants.respCode, "0010");
		ctx.put(Constants.respDesc, "appKey not configured");
		flag = true;
	}
	
	if(!flag){
		String localmd5 = TokenUtils.md5Sign((Map<String,String>)ctx.get(Constants.md5InputParm),
				(List<String>) ctx.get(Constants.requestPublicInfo), laoKeyPO.getAuthKey());
		logger.info("*******************localmd5*****************"+localmd5);
		if(!ctx.get(Constants.MD5).equals(localmd5)){
			ctx.put(Constants.respCode, "0021");
			ctx.put(Constants.respDesc, "sign validators fail");
			flag = true;
			if(null != ctx.get("smsContent")){
				logger.info("smsContent is not md5");
				flag = false;
			}
		}
		
	}	
	if(flag){	
		logger.info(ctx.get(Constants.respDesc));
		laoToDb = (LaoPeripheralSysAccessLogMapper) ctx.get("laoToDb");
		AccessInDBLogger log = new AccessInDBLogger();
		log.logIntoFile(laoToDb,(Long)ctx.get(Constants.CustID), (String) ctx.get(Constants.ipAddress), (String)ctx.get(Constants.Iccid), (String)ctx.get(Constants.ServerName), "1", 
				(String)ctx.get(Constants.respCode),(String)ctx.get(Constants.requestInfoToDb), "", (Date)ctx.get(Constants.createDate));
		return true;
	}
	
		return false;
	}

}
