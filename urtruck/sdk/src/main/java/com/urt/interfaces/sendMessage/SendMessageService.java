package com.urt.interfaces.sendMessage;

import java.util.List;
import java.util.Map;

import com.urt.dto.LaoSmsInfoDto;

public interface SendMessageService {

	boolean smsSend(Map<String,Object> map);
	
	Map<String,Object> smsSendBack(Map<String,Object> map);
	
	/**
	 * 根据手机号 查询发送过 已经成功的短信信息
	 */
	public List<LaoSmsInfoDto> queryInfoByMsisdn(String msisdn);
}
