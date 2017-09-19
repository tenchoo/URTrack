package com.urt.dto.http.mno;

import java.util.Date;

import com.urt.common.enumeration.MnoInterfaceCode;

public class InterfaceHandlerContext implements java.io.Serializable {


    private boolean success = true;//存在异常需要返回

    private String systemId;

    private String appKey;

    private String serverName;

    private String content;//请求内容 全部

    private Date startTime;//开始时间

    private Date endTime;//结束时间

    private String requestTime;//对方的请求时间

    private Date requestTimeParse;

    private String requestId;

    private String sign;

    private String httpCode;

    private String requestIp;

    private String requestInfo;

    private String requestInfoDecode;

    private String respText;

    private String respInfo;

    private MnoInterfaceCode interfaceCode = MnoInterfaceCode.SUCCESS;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public Date getRequestTimeParse() {
        return requestTimeParse;
    }

    public void setRequestTimeParse(Date requestTimeParse) {
        this.requestTimeParse = requestTimeParse;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(String httpCode) {
        this.httpCode = httpCode;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }

    public String getRespText() {
        return respText;
    }

    public void setRespText(String respText) {
        this.respText = respText;
    }

    public String getRespInfo() {
        return respInfo;
    }

    public void setRespInfo(String respInfo) {
        this.respInfo = respInfo;
    }

    public MnoInterfaceCode getInterfaceCode() {
        return interfaceCode;
    }

    public void setInterfaceCode(MnoInterfaceCode interfaceCode) {
        this.interfaceCode = interfaceCode;
    }

    public String getRequestInfoDecode() {
        return requestInfoDecode;
    }

    public void setRequestInfoDecode(String requestInfoDecode) {
        this.requestInfoDecode = requestInfoDecode;
    }
    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }


}
