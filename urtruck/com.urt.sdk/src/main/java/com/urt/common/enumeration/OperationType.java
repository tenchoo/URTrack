package com.urt.common.enumeration;

public enum OperationType {
    INSERT("INSERT", "增加用户信息"),UPDATE("UPDATE", "修改用户信息"),DELETE("DELETE","删除用户信息");

    private String code;
    private String explain;

    private OperationType(String code, String explain) {
        this.code = code;
        this.explain = explain;
    }

    public String getCode() {
        return code;
    }

    public String getExplain() {
        for (OperationType c : OperationType.values()) {
            if (c.getCode().equals(code)) {
                return c.explain;
            }
        }
        return null;
    }

    public static String getExplain(String code) {
        for (OperationType c : OperationType.values()) {
            if (c.getCode().equals(code)) {
                return c.explain;
            }
        }
        return null;
    }

}
