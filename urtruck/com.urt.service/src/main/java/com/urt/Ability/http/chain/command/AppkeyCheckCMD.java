package com.urt.Ability.http.chain.command;

import java.util.Date;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.cxf.common.i18n.Exception;
import org.apache.log4j.Logger;

import com.urt.Ability.http.log.AccessInDBLogger;
import com.urt.Ability.http.util.Constants;
import com.urt.mapper.LaoKeyManagementMapper;
import com.urt.mapper.LaoPeripheralSysAccessLogMapper;

public class AppkeyCheckCMD implements Command{
	/**
	 * 日志记录器
	 */
	private static final Logger logger = Logger.getLogger(AppkeyCheckCMD.class);
	LaoKeyManagementMapper laoKey;
	LaoPeripheralSysAccessLogMapper laoToDb;
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public boolean execute(Context ctx) throws Exception {
		// TODO Auto-generated method stub
		logger.info("*******************APPKEY校验*****************");
		boolean flag = false;
		if(null ==ctx.get(Constants.Appkey) || null == ctx.get(Constants.CustID)){
			if(null ==ctx.get(Constants.Appkey)){
				ctx.put(Constants.respCode, "0010");
				ctx.put(Constants.respDesc, "APPKEY不能为NULL");
				
			}
			if(null == ctx.get(Constants.CustID)){
				ctx.put(Constants.respCode, "0011");
				ctx.put(Constants.respDesc, "CUSTID不能为NULL");
			}
			flag = true;
			
		}else{
			laoKey = (LaoKeyManagementMapper) ctx.get("laoKey");
			logger.info("**************laoKey******"+laoKey+"******************");
			int appkeyNum = laoKey.doQueryAppKey((Long)ctx.get(Constants.CustID), (String) ctx.get(Constants.Appkey));		
			logger.info("************appkeyNum********"+appkeyNum+"******************");
			logger.info("appkeyNum="+appkeyNum);
			if(appkeyNum<=0){
				ctx.put(Constants.respCode, "0012");
				ctx.put(Constants.respDesc, "请求的APPKEY错误");
				flag = true;
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
