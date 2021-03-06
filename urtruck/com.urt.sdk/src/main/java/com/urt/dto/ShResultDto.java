package com.urt.dto;

import java.io.Serializable;
/**
 *返回结果集 
 *
 */
public class ShResultDto implements Serializable {


	private static final long serialVersionUID = 8594740517343055865L;
	
		private String respCode ;
		private String respDesc ;
	    private String requestId;
	    private boolean isSuccess;
	    private String serviceName;
	    
	    public String getServiceName() {
			return serviceName;
		}
		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}
		public String getRespCode() {
			return respCode;
		}
		public void setRespCode(String respCode) {
			this.respCode = respCode;
		}
		public String getRespDesc() {
			return respDesc;
		}
		public void setRespDesc(String respDesc) {
			this.respDesc = respDesc;
		}
		public String getRequestId() {
			return requestId;
		}
		public void setRequestId(String requestId) {
			this.requestId = requestId;
		}
		public boolean isSuccess() {
			return isSuccess;
		}
		public void setSuccess(boolean isSuccess) {
			this.isSuccess = isSuccess;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
}
