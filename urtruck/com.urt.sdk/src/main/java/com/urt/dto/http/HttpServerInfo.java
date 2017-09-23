package com.urt.dto.http;

import java.io.Serializable;

/*
 * @author wangxb20	
 * @能力开发校验javaBean
 */
public class HttpServerInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8706919831120088301L;
	
	/*
	 * 校验回执编码
	 */
	private String respCode;
	
	/*
	 * 校验回执描述
	 */
	private String respDesc;
	
	/*
	 * 服务名
	 */
	private String serverName;
	
	/*
	 * 服务对应的某个操作
	 */
	private String operationName;
	
	/*
	 * 入参
	 */
	private String operationParameters;
	
	public String getServerName() {
		return serverName;
	}
	
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getOperationParameters() {
		return operationParameters;
	}

	public void setOperationParameters(String operationParameters) {
		this.operationParameters = operationParameters;
	}

	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	

}
