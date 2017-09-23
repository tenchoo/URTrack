package com.urt.dto.http.mno;

import java.io.Serializable;

import com.urt.common.enumeration.MnoInterfaceCode;

public class MnoInterfaceResponseDto implements Serializable {

    private static final long serialVersionUID = 3058721563908697960L;

    private String respCode = MnoInterfaceCode.SUCCESS.getCode();
    private String respDesc = MnoInterfaceCode.SUCCESS.getExplain();
    private String requestId;
    private String respInfo;

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

    public String getRespInfo() {
        return respInfo;
    }

    public void setRespInfo(String respInfo) {
        this.respInfo = respInfo;
    }
}
