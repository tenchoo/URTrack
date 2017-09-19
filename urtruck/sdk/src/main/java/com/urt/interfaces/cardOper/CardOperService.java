package com.urt.interfaces.cardOper;

import java.util.List;
import java.util.Map;

public interface CardOperService {
	 public int singleStateChg(String iccId,String msisonId,String psptId,String psptTypeCode,Long chCustId,Long accountId);
	 public int multiStateChg(Map<String, List<String>> tMap,Long accountId);
	 public Map<String, Object> queryPage(Long chCustId, Integer pageNo,Integer pageSize);
}
