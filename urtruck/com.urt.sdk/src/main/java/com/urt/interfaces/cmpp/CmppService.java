package com.urt.interfaces.cmpp;

import java.util.List;
import java.util.Map;

public interface CmppService {
	//根据电话号码发送短信
	boolean sendCmpp3Msg(String msg, String telNo, String lang);

	//建立短信服务
	boolean connect();
	
	void sendUserMsg(List<Map<String, Object>> array);
}
