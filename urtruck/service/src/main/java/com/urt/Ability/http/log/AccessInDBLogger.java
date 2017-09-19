package com.urt.Ability.http.log;

import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.cxf.common.i18n.Exception;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.urt.Ability.http.util.Constants;
import com.urt.mapper.LaoPeripheralSysAccessLogMapper;
import com.urt.po.LaoPeripheralSysAccessLog;

/**
 * @author wangxb20
 * @version
 * 
 */
public class AccessInDBLogger {

	/*
	 * 日志记录
	 */
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	 
	/**
	 * Method logIntoFile.
	 * @param custid String
	 * @param ipAddress String
	 * @param UserID String
	 * @param ServerName String
	 * @param issuccess String
	 * @param errorCode String
	 * @param respdesc String
	 * @param requestMessage String
	 * @param responseMesssage String
	 * @param requestDate String
	 */
	public void logIntoFile(LaoPeripheralSysAccessLogMapper laoToDb ,Long custid, String ipAddress, String UserID, String ServerName,String issuccess, 
			String errorCode,String requestMessage,String responseMesssage, Date requestDate) {
		StringBuffer sb = new StringBuffer();
			sb.append(custid).append('\t')
				.append(ipAddress).append('\t')
				.append(UserID).append('\t')
				.append(ServerName).append('\t')
				.append(issuccess).append('\t')
				.append(errorCode).append('\t')
				.append(requestMessage).append('\t')
				.append(responseMesssage).append('\t')
				.append(requestDate).append('\t');
		logger.info(sb.toString());
		/*
		 * log to DB
		 */
		LaoPeripheralSysAccessLog  loginfo = new LaoPeripheralSysAccessLog();
			loginfo.setCustId(custid);
			loginfo.setIpAddress(ipAddress);
			loginfo.setUserName("");
			loginfo.setErrorCode(errorCode);
			loginfo.setIsSuccess(issuccess);
			loginfo.setReqInfo(requestMessage);
			loginfo.setRspInfo("");
			loginfo.setAccessDate(requestDate);
			loginfo.setServerName(ServerName);
			loginfo.setUserName(UserID);
			logIntoDB(loginfo,laoToDb);
	}
	
	public void logIntoDB(LaoPeripheralSysAccessLog loninfo,LaoPeripheralSysAccessLogMapper laoToDb) {
		laoToDb.insert(loninfo);
	}
}
