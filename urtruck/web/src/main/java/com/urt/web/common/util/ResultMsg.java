package com.urt.web.common.util;

public class ResultMsg implements java.io.Serializable {
	/**   
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)    
	*/
	private static final long serialVersionUID = 1L;
	
	private boolean success = false; //返回执行结果
	private String msg; //返回执行结果信息
	private String gobackUrl;//返回跳转的URL
	private Object objData = null;//返回数据集
	
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getGobackUrl() {
		return gobackUrl;
	}
	public void setGobackUrl(String gobackUrl) {
		this.gobackUrl = gobackUrl;
	}
	
	public Object getObjData() {
		return objData;
	}
	public void setObjData(Object objData) {
		this.objData = objData;
	}
	/**
	 * 提供静态工厂创建实例，获得一个成功类型的DTO
	 */
	public static  ResultMsg getSuccessMsg(String msg,Object objData) {
		ResultMsg resultMsg = new ResultMsg();
		resultMsg.setSuccess(true);
		resultMsg.setMsg(msg);
		resultMsg.setObjData(objData);
		return resultMsg;
	}

	/**
	 * 提供静态工厂创建实例，获得一个失败类型的DTO
	 * 
	 * @param <T>
	 * @param errorType
	 * @param errorMsg
	 * @return
	 */
	public static ResultMsg getFaildMsg(String msg,Object objData) {
		ResultMsg resultMsg = new ResultMsg();
		resultMsg.setSuccess(false);
		resultMsg.setMsg(msg);
		resultMsg.setObjData(objData);
		return resultMsg;
	}
}
