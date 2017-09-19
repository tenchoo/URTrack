package com.urt.mapper.ext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.urt.po.LaoSmsInfo;


public interface LaoSmsInfoExtMapper {
    List<LaoSmsInfo> querySendInfo(Map<String,Object> paraMap);
    
    
    int updateByOutsmsidSelective(LaoSmsInfo info);
    
    
    int countSendInfo(HashMap<String,Object> paraMap);


	List<Map<String, Object>> queryqueryAcceptSms(Map<String, Object> paraMap);


	int countAcceptSms(Map<String, Object> paraMap);
	
	/**
	 * 根据手机号 查询发送过 已经成功的短信信息
	 */
    List<LaoSmsInfo> queryInfoByMsisdn(String msisdn);
}