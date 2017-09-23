package com.urt.Ability.http.chain.command;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.log4j.Logger;

import com.urt.Ability.http.util.Constants;
import com.urt.Ability.http.util.ToolsUtil;

public class ParameterCheckCMD implements Command{
	/**
	 * 日志记录器
	 */
	private static final Logger logger = Logger.getLogger(ParameterCheckCMD.class);
	@Override
	public boolean execute(Context ctx) throws Exception {
		logger.info("***************** Parameter begin*******************");
		boolean isIP = ToolsUtil.checkIp((String)ctx.get(Constants.ipAddress));
		if(!isIP){
			ctx.put(Constants.respCode, "0001");
			ctx.put(Constants.respDesc, "IP ERROR:"+(String)ctx.get(Constants.ipAddress));
			return  true;
		}
		if(null == ctx.get(Constants.CustID)){
			ctx.put(Constants.respCode, "0011");
			ctx.put(Constants.respDesc, "custId is not null");
			return true;
		}
		if(null == ctx.get(Constants.ServerName)){
			ctx.put(Constants.respCode, "0032");
			ctx.put(Constants.respDesc, "serverName is not null");
			return true;
		}
		if(null == ctx.get(Constants.MD5)){
			ctx.put(Constants.respCode, "0020");
			ctx.put(Constants.respDesc, "sign is not null");
			return true;
		}
		return false;
	}

}
