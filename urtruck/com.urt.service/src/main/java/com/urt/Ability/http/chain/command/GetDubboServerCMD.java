package com.urt.Ability.http.chain.command;


import java.util.Date;
import java.util.List;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.urt.Ability.http.log.AccessInDBLogger;
import com.urt.Ability.http.util.Constants;
import com.urt.mapper.LaoPeripheralSysAccessLogMapper;
import com.urt.mapper.LaoProvideServerMapper;
import com.urt.mapper.ext.ResourceCheckMapper;
import com.urt.po.LaoProvideServer;

public class GetDubboServerCMD implements Command{
	/**
	 * 日志记录器
	 */
	private static final Logger logger = Logger.getLogger(GetDubboServerCMD.class);
	
	LaoProvideServerMapper resourceMap;
	LaoPeripheralSysAccessLogMapper laoToDb;
	@Override
	public boolean execute(Context ctx) throws Exception {
		logger.info("*******************Server Check*****************");
		boolean flag =false;
		//通过custid userid serverName来查找是否能调用此服务
		resourceMap = (LaoProvideServerMapper) ctx.get("resourceMap");
		logger.info("*********resourceMap***********"+resourceMap+"******************");
		List<LaoProvideServer> server = null;
		String operationName = "";
		String serverName = "";
		try{
			server = resourceMap.selectServer((Long) ctx.get(Constants.CustID),
						(String) ctx.get(Constants.Iccid),
					(String) ctx.get(Constants.ServerName));
			if(null != server && server.size()>0){
				serverName = server.get(0).getServerTag();
				operationName  = server.get(0).getOperationTag();
			}	 
		}catch(Exception e){
			e.printStackTrace();
			ctx.put(Constants.respCode, "0002");
			ctx.put(Constants.respDesc, "database exception");
			flag = true;
		}
		
		//LaoProvideServer server = resourceMap.selectServer((Integer) ctx.get(Constants.CustID),(String) ctx.get(Constants.Iccid), (String) ctx.get(Constants.ServerName));
		
		logger.info("*********resourceNum***********"+server+"******************");
		
		if(("".equals(serverName) || null == serverName) || ("".equals(operationName))|| null == operationName){
			flag = true;
			ctx.put(Constants.respCode, "0031");
			ctx.put(Constants.respDesc, "service not open");
		}
		if(!flag){
			ctx.put(Constants.respCode, "1000");
			ctx.put(Constants.respDesc, "此服务可以调用");
			ctx.put(Constants.ServerTagName, serverName);
			ctx.put(Constants.OperationName, operationName);
		}		
		if(flag){	
			logger.info(ctx.get("RESPDESC"));
			//日志记录
			logger.info(ctx.get(Constants.respDesc));
			laoToDb = (LaoPeripheralSysAccessLogMapper) ctx.get("laoToDb");
			AccessInDBLogger log = new AccessInDBLogger();
			log.logIntoFile(laoToDb,(Long)ctx.get(Constants.CustID), (String) ctx.get(Constants.ipAddress), (String)ctx.get(Constants.Iccid), (String)ctx.get(Constants.ServerName), "1", 
					(String)ctx.get(Constants.respCode),(String)ctx.get(Constants.requestInfoToDb), "", (Date)ctx.get(Constants.createDate));
			
			return flag;
		}
		
		return false;
	}

}
