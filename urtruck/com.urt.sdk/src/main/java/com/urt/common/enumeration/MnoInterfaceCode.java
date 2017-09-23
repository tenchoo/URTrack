package com.urt.common.enumeration;

public enum MnoInterfaceCode {

    SUCCESS("0000", "success"),
    IP_ERROR("0001", "IP error"),
    DATABASE_EXCEPTION("0002", "database exception"),
    IP_NOT_APPLIED("0003", "IP not applied"),
    PARAM_INVALID("0004", "params invalid"),
    APPKEY_INVALID("0010", "appKey invalid"),
    SYSTEMID_IS_NULL("0011", "systemId is null"),
    SYSTEMID_INVALID("0011", "systemId invalid"),
    SIGN_IS_NULL("0020", "sign is null"),
    SIGN_INVALID("0021", "sign invalid"),
    SERVER_NOT_OPEN("0031", "service not open"),
    SERVERNAME_IS_NULL("0032", "serverName is null"),
    REQUESTID_IS_NULL("0041", "requestId is null"),
    REQUESTID_REPEAT("0043", "requestId is repeat"),
    REQUESTTIME_IS_NULL("0045", "requestTime is null"),
    REQUESTTIME_FORMAT_ERROR("0046", "requestTime format error"),
    REQUESTINFO_IS_NULL("0048", "requestInfo is null"),
    REQUESTINFO_FORMAT_ERROR("0049", "requestInfo format error"),
    ICCID_NOT_IN_CUSTID("0053", "iccid not in custId"),
    ICCID_NOT_EXIST("0054", "iccid is not exist"),
    SERVER_INVALID("1001", "service invalid"),
    SYSTEM_ERROR("1002", "system exception"),
    LIMIT_TIMES("9000", "limit 10 times per minute"),
    AUTOORITY_INVALID("6666", "无权访问"),
    OTHER_ERROR("9999", "other errors")

    ;

    private String code;
    private String explain;

     MnoInterfaceCode(String code, String explain) {
        this.code = code;
        this.explain = explain;
    }

    public String getCode() {
        return code;
    }

    public String getExplain() {
        for (MnoInterfaceCode c : MnoInterfaceCode.values()) {
            if (c.getCode().equals(code)) {
                return c.explain;
            }
        }
        return null;
    }

    public static String getExplain(String code) {
        for (MnoInterfaceCode c : MnoInterfaceCode.values()) {
            if (c.getCode().equals(code)) {
                return c.explain;
            }
        }
        return null;
    }

}
