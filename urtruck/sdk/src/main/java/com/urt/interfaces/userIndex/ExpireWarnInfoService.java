package com.urt.interfaces.userIndex;

import java.util.List;
import java.util.Map;

public interface ExpireWarnInfoService{
	//根据custId查询指定条数通知
	List<Map<String, Object>> selectExpireWarnInfo(long custId);
	//查询所有条数通知
	List<Map<String, Object>> selectExpireWarnInfoAll(long custId);
}

