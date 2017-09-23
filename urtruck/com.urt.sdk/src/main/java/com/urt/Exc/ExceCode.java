package com.urt.Exc;
/**
* 类说明：	
* 系统管理异常码定义规则：60100为模块号，01为功能（角色），001为序列号，其他模块可以参考
* @author Administrator
* @date 2016年6月3日 下午12:04:23
*/
public enum ExceCode {
	TRADE_Exe("100","订单提交异常"),
	TRADE_ACTIVE_Exe("120","订单激活异常"),
	SYS_ROLE_USED("6010001001","角色已经被使用");
	
	private String Msgcode;
	private String MsgInfo;

	private ExceCode(String Msgcode,String MsgInfo){
		this.Msgcode=Msgcode;
	}

	public String getMsgcode() {
		return Msgcode;
	}

	public void setMsgcode(String msgcode) {
		Msgcode = msgcode;
	}

	public String getMsgInfo() {
		return MsgInfo;
	}

	public void setMsgInfo(String msgInfo) {
		MsgInfo = msgInfo;
	}

}
