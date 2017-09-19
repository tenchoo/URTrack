package com.urt.common.enumeration;

public enum CustTypeEnum {
	    PERSON("0", "个人客户"),CHACUST("1", "渠道客户");

	    private String code;
	    private String explain;

	    private CustTypeEnum(String code, String explain) {
	        this.code = code;
	        this.explain = explain;
	    }

	    public String getCode() {
	        return code;
	    }

	    public String getExplain() {
	        for (CustTypeEnum c : CustTypeEnum.values()) {
	            if (c.getCode().equals(code)) {
	                return c.explain;
	            }
	        }
	        return null;
	    }

	    public static String getExplain(String code) {
	        for (CustTypeEnum c : CustTypeEnum.values()) {
	            if (c.getCode().equals(code)) {
	                return c.explain;
	            }
	        }
	        return null;
	    }

}
