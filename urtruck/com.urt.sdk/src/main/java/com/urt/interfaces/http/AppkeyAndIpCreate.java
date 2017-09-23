package com.urt.interfaces.http;

import java.util.Map;

public interface AppkeyAndIpCreate {
	public String getAppkey(Map<String,String> reqInfo);
	public String registerIp(Map<String,String> reqInfo);
	public Map<String, Object> appKeyList(int start, int end,String custName);
	public String queryAppkey(Map<String, String> reqInfo);
	
}
