package com.urt.cmpp.util;

import java.net.Socket;

import org.springframework.beans.factory.InitializingBean;

/**
 * 接口调用
 * 
 *
 */
public class MsgActivityTimer implements InitializingBean {
	/**
	 * 短信接口长链接，定时进行链路检查
	 */
	public void executeInternal() {
		System.out.println("×××××××××××××开始链路检查××××××××××××××");
		boolean res = MsgContainer.activityTestISMG();
		if(!res){
			System.out.println("链路检测未连接,尝试建立socket连接！！");
			Socket socket = MsgContainer.getSocketInstance();
		}
		System.out.println("×××××××××××××链路检查结束××××××××××××××");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("首次加载,尝试建立socket连接！！");
		Socket socket = MsgContainer.getSocketInstance();
	}
}
