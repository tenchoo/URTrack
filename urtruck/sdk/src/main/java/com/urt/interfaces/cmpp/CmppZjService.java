package com.urt.interfaces.cmpp;


public interface CmppZjService {
	//根据电话号码发送短信
	boolean sendCmpp3Msg(String msg, String telNo, String lang);

	//建立短信服务
	boolean connect();
	
}
