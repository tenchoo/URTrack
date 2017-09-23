package com.urt.common.enumeration;
/**
 * 运营商类型枚举
 * 1 联通          2 移动        3 电信
 * */
public enum OperatorType {
    L("1", "联通"),Y("2", "移动"),D("3","电信");

    private String code;
    private String explain;

    private OperatorType(String code, String explain) {
        this.code = code;
        this.explain = explain;
    }

    public String getCode() {
        return code;
    }

    public String getExplain() {
        for (OperatorType c : OperatorType.values()) {
            if (c.getCode().equals(code)) {
                return c.explain;
            }
        }
        return null;
    }

    public static String getExplain(String code) {
        for (OperatorType c : OperatorType.values()) {
            if (c.getCode().equals(code)) {
                return c.explain;
            }
        }
        return null;
    }

}
