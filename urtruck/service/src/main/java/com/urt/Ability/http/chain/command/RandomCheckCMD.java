package com.urt.Ability.http.chain.command;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.log4j.Logger;

import com.urt.Ability.http.log.AccessInDBLogger;
import com.urt.Ability.http.util.Constants;
import com.urt.Ability.http.util.ToolsUtil;
import com.urt.mapper.LaoKeyManagementMapper;
import com.urt.mapper.LaoPeripheralSysAccessLogMapper;
import com.urt.mapper.LaoServerProvideVerifiyMapper;
import com.urt.po.LaoKeyManagement;
import com.urt.po.LaoServerProvideVerifiy;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

/**
 * 
 * @author wangxb20
 * 随机数校验规则:随机数: randomId 时区:timeZone
 * randomId组成【3位随机数+YYYYMMDDhhmmss+3位随机数】
 * 规则 如果LAO_SERVER_PROVIDE_VERIFIY 表中同一个客户不能存在相同的randomId 
 */
public class RandomCheckCMD implements Command{
	/**
	 * 日志记录器
	 */
	private LaoServerProvideVerifiyMapper laoServerProvideVerifiyMapper;
	private LaoPeripheralSysAccessLogMapper laoToDb;
	private LaoKeyManagementMapper laoKey;
	private static final Logger logger = Logger.getLogger(MD5CheckCMD.class);
	@Override
	public boolean execute(Context ctx) throws Exception {
		logger.info("*******************Random check begin*****************");
		laoServerProvideVerifiyMapper = (LaoServerProvideVerifiyMapper)ctx.get("provideVerifiy");
		boolean flag = false;
		LaoKeyManagement laoKeyPO = null;
		String notRandomCheck = null;
			laoKey = (LaoKeyManagementMapper) ctx.get("laoKey");
			laoKeyPO = laoKey.selectByCustId(String.valueOf(ctx.get(Constants.CustID)));
			notRandomCheck = laoKeyPO.getParaName2();
			if(!ToolsUtil.checkStringIsNull(notRandomCheck)){
				return false;	
			}
			logger.info("*******************11111111111*****************");	
		String randomId = (String)ctx.get("randomId");
		Long custId = (Long)ctx.get(Constants.CustID);
		if(ToolsUtil.checkStringIsNull(randomId)){
			logger.info("******************randomId is not null*****************");	
			ctx.put(Constants.respCode, "0041");
			ctx.put(Constants.respDesc, "randomId is not null");
			flag = true;
		}
		if(!flag){
			   Pattern pattern = Pattern.compile("[0-9]*"); 
			   Matcher isNum = pattern.matcher(randomId);
			   if( !isNum.matches() ){
				   flag = true; 
			   }else if(20 != randomId.length()){ 
				   flag = true; 
			   }
			   if(flag){
				   logger.info("******************randomId format is error*****************");	
				   ctx.put(Constants.respCode, "0042");
				   ctx.put(Constants.respDesc, "randomId format is error");
			   }
			   
		}
		List<LaoServerProvideVerifiy> VerPO = null;
		if(!flag){
			VerPO = laoServerProvideVerifiyMapper.selectByRandomId(randomId, String.valueOf(custId));
		}
		
		if(!flag && VerPO.size()>0){
			
			 ctx.put(Constants.respCode, "0043");
			 ctx.put(Constants.respDesc, "randomId is not repeat");
			 logger.info("******************randomId is not repeat*****************");
			 flag = true;
		}
		if(!flag){
			//插入数据库
			LaoServerProvideVerifiy verifiyPO = new LaoServerProvideVerifiy();
			verifiyPO.setCreatedate(new Date());
			verifiyPO.setCustid(String.valueOf(custId));
			verifiyPO.setRandomid(randomId);
			verifiyPO.setServername((String)ctx.get(Constants.ServerName));
			verifiyPO.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.VERIFIY_ID)));
			laoServerProvideVerifiyMapper.insert(verifiyPO);
			flag = false;
		}
		logger.info("******************ctx*****************"+flag +"-------"+ctx.get("RESPCODE")+ctx.get("RESPDESC"));
		if(flag){
			//日志记录
			logger.info(ctx.get(Constants.respDesc));
			laoToDb = (LaoPeripheralSysAccessLogMapper) ctx.get("laoToDb");
			AccessInDBLogger log = new AccessInDBLogger();
			log.logIntoFile(laoToDb,(Long)ctx.get(Constants.CustID), (String) ctx.get(Constants.ipAddress), (String)ctx.get(Constants.Iccid), (String)ctx.get(Constants.ServerName), "1", 
					(String)ctx.get(Constants.respCode),(String)ctx.get(Constants.requestInfoToDb), "", (Date)ctx.get(Constants.createDate));
			
			return flag;
		}
		logger.info("******************ctx*****************"+ctx.get("RESPCODE")+ctx.get("RESPDESC"));
		
		return false;
	}

}
