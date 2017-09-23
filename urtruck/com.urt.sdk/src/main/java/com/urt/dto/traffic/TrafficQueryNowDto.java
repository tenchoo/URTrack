package com.urt.dto.traffic;

import java.io.Serializable;
import java.util.List;

public class TrafficQueryNowDto implements Serializable {

	private static final long serialVersionUID = 810186266665993694L;

	/** ICCID */
	private String iccid;
	/** 运营商 */
	private String operatorName;
	/** msisdn号码 */
	private String msisdn;
	/** 剩余流量 */
	private String dataRemaining;
	/** 终端类型 */
	private String type;
	/** 客户姓名 */
	private String userName;
	/** 型号 */
	private String version;
	/** 渠道客户名称 */
	private String channelCustName;
	/** 卡状态 */
	private String state;
	/** 异常信息 */
	private String expMsg;
	
	/** 为了展示全部套餐信息*/
	private List<PackagePlanDto> listPack;
	
	/** 运营ID */
	private String operatorId;
	
	/**为了将gla功能转接到h5上，添加的参数
	* @author sunhao
	* @date 2017年2月9日 下午3:50:23
	 */
	private String expirationDate; //正使用的流量包 到期 时间
	private String ratePlanName;   //当前使用的流量包名称
	
	/**为了展示卡在网状态查询，添加的参数
	 * @author lingfei
	 * @date 2017年6月12日17:24:09
	 */
	private String isOnline; //正使用的流量包 到期 时间
	
	private String monthSign; //月付灵活共享，月付单卡的标识
	
	
	public String getMonthSign() {
		return monthSign;
	}

	public void setMonthSign(String monthSign) {
		this.monthSign = monthSign;
	}

	public String getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public List<PackagePlanDto> getListPack() {
		return listPack;
	}

	public void setListPack(List<PackagePlanDto> listPack) {
		this.listPack = listPack;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getRatePlanName() {
		return ratePlanName;
	}

	public void setRatePlanName(String ratePlanName) {
		this.ratePlanName = ratePlanName;
	}
	public String getExpMsg() {
		return expMsg;
	}
	public void setExpMsg(String expMsg) {
		this.expMsg = expMsg;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getDataRemaining() {
		return dataRemaining;
	}
	public void setDataRemaining(String dataRemaining) {
		this.dataRemaining = dataRemaining;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getChannelCustName() {
		return channelCustName;
	}
	public void setChannelCustName(String channelCustName) {
		this.channelCustName = channelCustName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "TrafficQueryNowDto [iccid=" + iccid + ", operatorName="
				+ operatorName + ", msisdn=" + msisdn + ", dataRemaining="
				+ dataRemaining + ", type=" + type + ", userName=" + userName
				+ ", version=" + version + ", channelCustName="
				+ channelCustName + ", state=" + state + ", expMsg=" + expMsg
				+ ", listPack=" + listPack + ", operatorId=" + operatorId
				+ ", expirationDate=" + expirationDate + ", ratePlanName="
				+ ratePlanName + "]";
	}

}
