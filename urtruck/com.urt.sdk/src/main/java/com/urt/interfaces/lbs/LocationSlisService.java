package com.urt.interfaces.lbs;

import java.util.List;
import java.util.Map;

public interface LocationSlisService {

	String queryLocationSlis(String iccid);
	
	String queryByBaiduUtil(String addrStr);
	
	String queryLocationjedisCluster(String iccid);
	
	void sendLocationjedis(List<Map<String,Object>> listMap);
	
}
