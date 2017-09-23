package com.urt.web.http.recevice;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.http.SendSmsDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.cmpp.CmppService;
import com.urt.interfaces.cmpp.CmppZjService;
import com.urt.interfaces.cmpp.LaoCmppService;
import com.urt.interfaces.http.CardActiveService;
import com.urt.interfaces.http.CardDetailService;
import com.urt.interfaces.http.FlowQueryServer;
import com.urt.interfaces.http.OrderProductService;
import com.urt.interfaces.http.SendSmsServer;
import com.urt.utils.SpringContextUtils;
import com.urt.web.common.util.StringUtil;
@Service("httpInterfaceAdd")
public class HttpInterfaceAdd {
	/**日志****/
	protected static final Logger logger = Logger.getLogger(HttpInterfaceAdd.class);
	@Autowired
	private LaoCmppService laoCmppService;
	@Autowired
	private CmppService cmppService;
	@Autowired
	private CmppZjService cmppZjService;
	@Autowired
	private UserService userService;
	public  Map<String,Object> RspInfo(String methodName,String serverName,Map<String,String> reqInfo){
		Map<String,Object> rspInfo = new HashMap<String,Object>();
		Object rspobj = null;
		try{
			if("orderProductService".equals(serverName)){	
				OrderProductService o = (OrderProductService)SpringContextUtils.getBean(serverName);
				//流量订购
				if("orderProduct".equals(methodName)){
					rspobj= o.orderProduct(reqInfo);	
				}else if("queryProduct".equals(methodName)){		
					rspobj= o.queryProduct(reqInfo);
				}
			}else if("cardActiveService".equals(serverName)){
				CardActiveService o = (CardActiveService)SpringContextUtils.getBean(serverName);
				//卡激活
				if("cardActive".equals(methodName)){
					rspobj = o.cardActive(reqInfo);
				//卡状态查询
				}else if("queryCardStatus".equals(methodName)){
					rspobj = o.queryCardStatus(reqInfo);
				//停开机
				}else if("stopOn".equals(methodName)){
					rspobj = o.stopOn(reqInfo);
				}
				
			}else if("flowQueryServer".equals(serverName)){
				FlowQueryServer flow = (FlowQueryServer)SpringContextUtils.getBean(serverName);
				//剩余流量查询
				if("surplusFlowQuery".equals(methodName)){
					logger.info("flowQueryServer*surplusFlowQuery*******************************");
					rspobj = flow.surplusFlowQuery(reqInfo);
				//日流量查询
				}else if("dayFlowQuery".equals(methodName)){
					rspobj = flow.dayFlowQuery(reqInfo);
				//月流量查询
				}else if("monthFlowQuery".equals(methodName)){
					rspobj = flow.monthFlowQuery(reqInfo);
				}else if ("surplusFlowQueryUnicom".equals(methodName)) {
					rspobj  = flow.surplusFlowQueryAndPackageCount(reqInfo);
				}else if ("currentPackageQuery".equals(methodName)) {
					rspobj = flow.currentPackage(reqInfo);
				}
			}else if("sendSmsServer".equals(serverName)){
				logger.info("SendSmsMessageService********************************");
				//SendSmsServer flow = (SendSmsServer)SpringContextUtils.getBean("sendSmsServer");
				boolean res = false;
				//if(!ToolsUtil.checkStringIsNull(number)){
				String  smsContent = reqInfo.get("smsContent");
				logger.info("***************************"+smsContent+"-----"+smsContent.length());
				
				//String smsContent = URLDecoder.decode(reqInfo.get("smsContent"),"UTF-8");
				String iccid = reqInfo.get("iccid");
				LaoUserDto userInfo = userService.getLaoUserDtoByIccid(iccid);
				SendSmsDto smsDto = new SendSmsDto();
				Long msgId = null;
				if(null != userInfo){
					String number = userInfo.getMsisdn();
					String custId = reqInfo.get("custId");
					String lang = reqInfo.get("lang");
					if(StringUtil.isBlank(lang)){
						lang = "EngLish";
					}
						if(!StringUtil.isBlank(number)){
							msgId = laoCmppService.saveMsg(smsContent, iccid,custId);
							// TODO 判断运营商是浙江移动还是东莞移动
							if ("5".equals(userInfo.getOperatorsId()+"")) {
								res = cmppZjService.sendCmpp3Msg(smsContent, number, lang);
							}else {
								res = cmppService.sendCmpp3Msg(smsContent, number, lang);
							}
							//res = cmppService.sendCmpp3Msg(smsContent, number);
							if(res){
								laoCmppService.updateMsgSuccess(msgId);
							}
						}
						
						logger.info("sms length is:"+smsContent.length());
				}else{
					res = false;
				}
					if(res){
						smsDto.setRespCode("0000");
						smsDto.setRespDesc("success");
						smsDto.setSendSmsTag("0");
						smsDto.setSmsId(String.valueOf(msgId));
					}else{
						smsDto.setRespCode("9999");
						smsDto.setRespDesc("fail");
						smsDto.setSendSmsTag("1");
						smsDto.setSmsId("");
					}
				rspobj = smsDto;
			}else if("querySmsServer".equals(serverName)){
				SendSmsServer querySms = SpringContextUtils.getBean("sendSmsServer");
				rspobj = querySms.querySms(reqInfo);
			}else if("cardDetailService".equals(serverName)){
				CardDetailService queryDetail = (CardDetailService)SpringContextUtils.getBean(serverName);
				rspobj = queryDetail.queryCardDetail(reqInfo);
			}
		}catch(Exception e){
			Map<String,Object> errorInfo = new HashMap<String,Object>();
			errorInfo.put("respCode", "1002");
			errorInfo.put("respDesc", "system exception");
			rspInfo.put("resultInfo", errorInfo);
			logger.info("exception****************");
			e.printStackTrace();
			return rspInfo;
		}
		if( null == rspobj){
			Map<String,Object> errorInfo = new HashMap<String,Object>();
			logger.info("null****************");
			errorInfo.put("respCode", "1001");
			errorInfo.put("respDesc", "service call failed");
			rspInfo.put("resultInfo", errorInfo);
			return rspInfo;
		}
		rspInfo.put("resultInfo",rspobj);
		return rspInfo;
	}
}
