package com.urt.interfaces.cmpp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.urt.dto.LaoSmsDeliverDto;
import com.urt.dto.LaoSmsInfoDto;

public interface LaoCmppService {
	
	Long saveMsg(String msg,String iccid,String custId);
	
	Long saveMsg(String msg,String iccid,String custId,Long acctId);
	
	int updateMsgSuccess(Long smsId);
	 
	List<LaoSmsInfoDto> querySendInfo(HashMap<String, Object> paraMap);
	
	int countSendInfo(HashMap<String, Object> paraMap);
	
	void saveDeliver(LaoSmsDeliverDto laoSmsDeliver);
	
	String pushSmstoPlatform(String targetMsisdn,String sendMsisdn,String smsId,String smsContent,String pushDate);
	
	LaoSmsDeliverDto selectBySrcNumber(Long srcNumber);
	
	void updateSmsDeliver(LaoSmsDeliverDto laoSmsDeliver);

	List<LaoSmsInfoDto> queryAcceptSms(HashMap<String, Object> paraMap);

	int countAcceptSms(Map<String, Object> paraMap);
}
