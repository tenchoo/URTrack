package com.urt.cmpp.service;

import java.net.Socket;

import org.springframework.stereotype.Service;

import com.urt.cmpp.util.MsgContainer;
import com.urt.interfaces.cmpp.CmppZjService;
@Service("cmppZjServiceImpl")
public class CmppZjServiceImpl implements CmppZjService{
	
	@Override
	public boolean sendCmpp3Msg(String msg,String iccid, String lang) {
		boolean res = MsgContainer.sendMsg(msg, iccid, lang);
		System.out.println("sendCmpp3Msg 返回："+res);
		return res;
	}
	
	@Override
	public boolean connect(){
		System.out.println("建立socket连接！！");
		boolean res = false;
		Socket socket = MsgContainer.getSocketInstance();
		if(socket != null){
			res =  true;
		}
		return res;
	}
}
