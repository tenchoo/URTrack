package com.urt.Ability.M2M.vo;

import java.io.Serializable;

public class BaseQRRsp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String version_id;			//xx．xx 一共五位
	private String test_flag;			//0-生产数据，1-测试数据
	private String service_name;
	private String service_parameter;
	private String msgId;				//ID—uuid
	private String reqTime;				//yyyymmddhhmmss
	private String consumer;			//用户名
	private String password;			//密码
	private String respTime;			//yyyymmddhhmmss
	private String result_code;			//返回码：0 表示成功，其他返回值表示失败
	private String result_msg;			//返回描述消息
	private String[] res_recordset;
	public String getVersion_id() {
		return version_id;
	}
	public void setVersion_id(String version_id) {
		this.version_id = version_id;
	}
	public String getTest_flag() {
		return test_flag;
	}
	public void setTest_flag(String test_flag) {
		this.test_flag = test_flag;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public String getService_parameter() {
		return service_parameter;
	}
	public void setService_parameter(String service_parameter) {
		this.service_parameter = service_parameter;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getReqTime() {
		return reqTime;
	}
	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}
	public String getConsumer() {
		return consumer;
	}
	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRespTime() {
		return respTime;
	}
	public void setRespTime(String respTime) {
		this.respTime = respTime;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getResult_msg() {
		return result_msg;
	}
	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}
	public String[] getRes_recordset() {
		return res_recordset;
	}
	public void setRes_recordset(String[] res_recordset) {
		this.res_recordset = res_recordset;
	}		
	
}
