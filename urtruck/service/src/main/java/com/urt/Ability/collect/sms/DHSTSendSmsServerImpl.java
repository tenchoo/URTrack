package com.urt.Ability.collect.sms;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.urt.Ability.http.util.HttpPostSend;
import com.urt.Ability.http.util.TokenUtils;
import com.urt.Ability.http.util.ToolsUtil;
import com.urt.mapper.LaoOperatordealLogMapper;
import com.urt.mapper.LaoSmsInfoMapper;
import com.urt.po.LaoOperatordealLog;
import com.urt.po.LaoSmsInfo;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service("dHSTSendSmsServerImpl")
public class DHSTSendSmsServerImpl extends SendSmsAbstract{
	protected static final Logger logger = Logger.getLogger(HttpPostSend.class);
	private final static String  account ="jt740404";
	private final static String  password= "r95mWDxG";
	private final static String  sign= "【联想懂的通信】";
	private String subcode;
	/*@Value("${sendsms.dhst.account}")
	private String account;
	@Value("${sendsms.dhst.password}")
	private String password;
	@Value("${sendsms.dhst.sign}")
	private String sign;
	@Value("${sendsms.dhst.subcode}")
	private String subcode;*/
	
	@Autowired
	private LaoOperatordealLogMapper laoOperatordealLogDAO;
	private LaoOperatordealLog log;
	private Map<String,String> loggerMapSendMesaage = new HashMap<String,String>();
	
	/*
	 * (non-Javadoc)
	 * @see com.lenovo.LAOAPI.Ability.collect.sms.SendSmsAbstract#sendSMS(java.util.Map)
	 * 调用大汉三通发送短信
	 */
	@Override
	public String sendSMS(Map<String,Object> smsContent) {
		logger.info("***************SEND MESSAGE BEGIN******************");
			boolean flag = false;
			//发送前格式封装
			String RequestjsonSms = paramReady(smsContent);
			logger.info("request sms message"+RequestjsonSms);
			//httpPost+json发送
			String httpRspMessage = httpPostSend(RequestjsonSms);
			logger.info("response sms message"+httpRspMessage);
			
			if("0".equals(JSONObject.fromObject(httpRspMessage).getString("result"))){
				flag = true;
			}			
			//日志记录
			loggerMapSendMesaage.put("REQUESTSMS", RequestjsonSms);
			loggerMapSendMesaage.put("RSPMESSAGE", httpRspMessage);
			try{
				inserLogger(loggerMapSendMesaage);
			}catch(Exception e){
				logger.info("***************日志记录数据库错误******************");
			}
			
			return httpRspMessage;
	}

	/**
	 * 请求参数组装
	 * @param smsContent
	 * @return
	 */
	private String paramReady(Map<String,Object> smsContent){		
		Map<String,String> sms = new HashMap<String,String>();
		String phones = null;
		phones = smsContent.get("number").toString();
		if(ToolsUtil.checkStringIsNull(phones)){
			phones = smsContent.get("iccid").toString();
		}		
		sms.put("account", account);
		sms.put("password", TokenUtils.degistByMD5(password));	
		sms.put("msgid", smsContent.get("msgId").toString());	
		sms.put("phones", phones);
		sms.put("content", smsContent.get("content").toString());
		sms.put("sign", sign);
		sms.put("subcode", subcode);
		sms.put("sendtime", new SimpleDateFormat("YYYYMMddHHmm").format(new Date()));
		JSONObject json = JSONObject.fromObject(sms);
		loggerMapSendMesaage.put("ICCID",phones);
		//如果是外围系统调用的需要把请求短信内容记录到数据库
		return json.toString();
	}
	/**
	 * 调用http请求大汉三通真正发送短信
	 * @param jsonSms
	 * @return
	 */
	private String httpPostSend(String jsonSms){
		HttpPostSend http = new HttpPostSend();
		/*
		 * 通过http请求大汉三通发短信
		 */
		String rspMessage = http.invoke(jsonSms, "http://www.dh3t.com/json/sms/Submit");	
		loggerMapSendMesaage.put("RESULTINFO", JSONObject.fromObject(rspMessage).getString("desc"));
		loggerMapSendMesaage.put("RESULTCODE", JSONObject.fromObject(rspMessage).getString("result"));
		return rspMessage;
	}
	
	
/*	private void updateToDb(JSONObject jb,String msid){
		LaoSmsInfo lsms = new LaoSmsInfo();
		lsms.setSmsId(ToolsUtil.checkStringIsNull(msid)?Long.valueOf(jb.getString("msgid")):Long.valueOf(msid));
		lsms.setResultInfo(jb.getString("desc"));
		lsms.setErrorCode(jb.getString("result"));
		lsms.setDealTag("1");
		lsms.setSmsCorp("1000");//大汉三通
		laoSms.updateByPrimaryKeySelective(lsms);
    }*/
	
	/**
	 * @param httpRspMessage
	 * @return
	 * 掉http来获得短信状态报告列表
	 */
	public String getSendSmsReprot(String reqMsidn){	
		HttpPostSend http = new HttpPostSend();
		Map<String,String> sms = new HashMap<String,String>();
		sms.put("account", account);
		sms.put("password", TokenUtils.degistByMD5(password));
		String reportReq = JSONObject.fromObject(sms).toString();
		String rspMessageReport = http.invoke(reportReq, "http://www.dh3t.com/json/sms/Report");
		logger.info("rspMessageReport sms message"+rspMessageReport);
		if(!ToolsUtil.checkStringIsNull(rspMessageReport)){
			loggerMapSendMesaage.put("RSPMESSAGE", rspMessageReport.length()>=2000?rspMessageReport.substring(0, 2000):rspMessageReport);
			JSONObject json = JSONObject.fromObject(rspMessageReport);
			loggerMapSendMesaage.put("RESULTINFO", json.getString("desc"));
			loggerMapSendMesaage.put("RESULTCODE", json.getString("result"));
			if("0".equals(json.getString("result"))){
				JSONArray jsonA = null;;
				try{
					jsonA = json.getJSONArray("reports");
					loggerMapSendMesaage.put("iccid", Integer.toString(jsonA.size()));
				}catch(Exception e){
					loggerMapSendMesaage.put("iccid","1");
					e.printStackTrace();
				}
				/*for(int a=0;a<jsonA.size();a++){
					//更新数据库状态
					updateToDb(jsonA.getJSONObject(a),"");
				}*/
			}else{
				try {
					
					throw new Exception("*************调用大汗三通短信状态报告错误************"+json.getString("result"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try{
			inserLogger(loggerMapSendMesaage);
		}catch(Exception e){
			logger.info("***************日志记录数据库错误******************");
		}
	
		return rspMessageReport;
			
		
	}
	/*
	 * 日志记录
	 */
	private void inserLogger(Map<String,String> loggerMap){
		//记录入参
			logger.info("***********"+Arrays.toString(loggerMap.values().toArray()));
			if(null !=loggerMap){
				log = new LaoOperatordealLog();
				log.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
				if(ToolsUtil.checkStringIsNull(loggerMap.get("iccid"))){
					log.setIccid("0000000000");
				}else{
					log.setIccid(loggerMap.get("iccid"));	
				}
				log.setInputParameters(loggerMap.get("REQUESTSMS"));
				//记录出参
				log.setOutputParameters(loggerMap.get("RSPMESSAGE"));
				log.setResultInfo(loggerMap.get("RESULTINFO"));
				log.setResultCode(loggerMap.get("RESULTCODE"));
				log.setSuccess(ToolsUtil.checkStringIsNull(loggerMap.get("RESULTCODE"))? "1":loggerMap.get("RESULTCODE"));
				log.setOperatorType("2");//1订购2发短信3状态变更4查询卡状态
				log.setCreateDate(new Date());
				laoOperatordealLogDAO.insert(log);
			}else{
				logger.info("******NO LOGGER TO DB*****");
			}
				
	}
	public static void main(String[] args) {
		String json = "{\"result\":\"0\",\"desc\":\"成功\",\"reports\":[]}";
		JSONObject jb = JSONObject.fromObject(json);
		JSONArray jsonA = jb.getJSONArray("reports");
		System.out.println(jsonA.size());
		//System.out.println(jb.getJSONArray("reports").getJSONObject(0).get("msgid"));
		//DHSTSendSmsServerImpl dh = new DHSTSendSmsServerImpl();
		//dh.sendSMS("1000\t1064930395282\tHello\t123456789");
	}

}
