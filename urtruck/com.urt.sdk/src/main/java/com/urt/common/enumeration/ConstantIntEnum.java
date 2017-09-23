package com.urt.common.enumeration;

public enum ConstantIntEnum {
	 RELEATIME(1800, "锁的有效时长");

    private Integer code;
    private String explain;

    private ConstantIntEnum(Integer  code, String explain) {
        this.code = code;
        this.explain = explain;
    }

    public Integer getCode() {
        return code;
    }

    public String getExplain() {
        for (ConstantIntEnum c : ConstantIntEnum.values()) {
            if (c.getCode().equals(code)) {
                return c.explain;
            }
        }
        return null;
    }

    public static String getExplain(String code) {
        for (ConstantIntEnum c : ConstantIntEnum.values()) {
            if (c.getCode().equals(code)) {
                return c.explain;
            }
        }
        return null;
    }

}
