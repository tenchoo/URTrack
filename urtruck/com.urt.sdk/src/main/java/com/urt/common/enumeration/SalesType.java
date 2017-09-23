package com.urt.common.enumeration;

public enum SalesType {
    N("1", "北区"),E("2", "东区"),S("3","南区");

    private String code;
    private String explain;

    private SalesType(String code, String explain) {
        this.code = code;
        this.explain = explain;
    }

    public String getCode() {
        return code;
    }

    public String getExplain() {
        for (SalesType c : SalesType.values()) {
            if (c.getCode().equals(code)) {
                return c.explain;
            }
        }
        return null;
    }

    public static String getExplain(String code) {
        for (SalesType c : SalesType.values()) {
            if (c.getCode().equals(code)) {
                return c.explain;
            }
        }
        return null;
    }

}
