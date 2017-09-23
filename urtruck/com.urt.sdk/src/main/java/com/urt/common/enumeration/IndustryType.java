package com.urt.common.enumeration;
/**
 * 行业类型
 * 0 只能展业     1 智能车联    2 智能互联      3 运营商物联
 * */
public enum IndustryType {
    Z("智能展业", "智能展业"),C("智能车联", "智能车联"),H("智能互联","智能互联"),Y("运营商物联","运营商物联");

    private String code;
    private String explain;

    private IndustryType(String code, String explain) {
        this.code = code;
        this.explain = explain;
    }

    public String getCode() {
        return code;
    }

    public String getExplain() {
        for (IndustryType c : IndustryType.values()) {
            if (c.getCode().equals(code)) {
                return c.explain;
            }
        }
        return null;
    }

    public static String getExplain(String code) {
        for (IndustryType c : IndustryType.values()) {
            if (c.getCode().equals(code)) {
                return c.explain;
            }
        }
        return null;
    }

}
