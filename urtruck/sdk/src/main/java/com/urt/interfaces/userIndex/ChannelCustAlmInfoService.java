package com.urt.interfaces.userIndex;

import java.util.List;
import java.util.Map;

public interface ChannelCustAlmInfoService{
	//根据custId查询指定条数通知
	List<Map<String, Object>> selectAlmInfo(long custId);
	//查询所有条数通知
	List<Map<String, Object>> selectAlmInfoAll(long custId);
}

