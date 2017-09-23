package com.urt.Ability.http.chain.command;

import java.util.Date;
import java.util.Map;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.Filter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.urt.Ability.http.log.AccessInDBLogger;
import com.urt.Ability.http.util.Constants;
import com.urt.Ability.http.util.ToolsUtil;
import com.urt.mapper.LaoPeripheralSysAccessLogMapper;
import com.urt.mapper.LaoUserIpManagerMapper;


public class IpCheckCMD implements Command{
	/**
	 * 日志记录器
	 */
	private static final Logger logger = Logger.getLogger(IpCheckCMD.class);
	
	LaoUserIpManagerMapper ipQuery;
	LaoPeripheralSysAccessLogMapper laoToDb;
	@Override
	public boolean execute(Context ctx) throws Exception {
		
		boolean flag = false;
		// TODO Auto-generated method stub
		logger.info("*******************IP地址校验*****************");
			//通过集团编号或者集团名称来查找相应的ip地址
			ipQuery = (LaoUserIpManagerMapper) ctx.get("ipQuery");
			logger.info("**********IpCheckCMD**********"+ipQuery+"******************");
			int ipNum= 0;
			try{
				ipNum = ipQuery.doQueryIP((Long)ctx.get(Constants.CustID), (String) ctx.get(Constants.ipAddress));
			}catch (Exception e) {
				logger.info("**********IpCheckCMD**********服务端查询数据库报错或查询对象为NULL******************");
				e.printStackTrace();
				ctx.put(Constants.respCode, "0002");
				ctx.put(Constants.respDesc, "database exception");
				flag = true;
			}
			logger.info("ipNum="+ipNum);
			//ip地址校验
			if(!flag && ipNum <= 0){
				ctx.put(Constants.respCode, "0003");
				ctx.put(Constants.respDesc, "IP not applied:"+(String)ctx.get(Constants.ipAddress));
				flag = true;
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
