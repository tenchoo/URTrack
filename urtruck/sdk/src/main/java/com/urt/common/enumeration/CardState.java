package com.urt.common.enumeration;

public enum CardState {
    Z("1", "在网"),T("2","停机"),W("3","待激活");

    private String code;
    private String explain;

    private CardState(String code, String explain) {
        this.code = code;
        this.explain = explain;
    }

    public String getCode() {
        return code;
    }

    public String getExplain() {
        for (CardState c : CardState.values()) {
            if (c.getCode().equals(code)) {
                return c.explain;
            }
        }
        return null;
    }

    public static String getExplain(String code) {
        for (CardState c : CardState.values()) {
            if (c.getCode().equals(code)) {
                return c.explain;
            }
        }
        return null;
    }

}
