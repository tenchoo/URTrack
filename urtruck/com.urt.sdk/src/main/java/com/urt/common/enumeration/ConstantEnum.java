package com.urt.common.enumeration;

public enum ConstantEnum {
    ZERO(0L, "长整形常量0"),CUSTID(3071054050000028L, "联想公用产品custId"),RELEATIME(600000L, "锁的有效时长"),ACQTIME(1000L, "锁重试时长");

    private Long code;
    private String explain;

    private ConstantEnum(Long code, String explain) {
        this.code = code;
        this.explain = explain;
    }

    public Long getCode() {
        return code;
    }

    public String getExplain() {
        for (ConstantEnum c : ConstantEnum.values()) {
            if (c.getCode().equals(code)) {
                return c.explain;
            }
        }
        return null;
    }

    public static String getExplain(String code) {
        for (ConstantEnum c : ConstantEnum.values()) {
            if (c.getCode().equals(code)) {
                return c.explain;
            }
        }
        return null;
    }

}
