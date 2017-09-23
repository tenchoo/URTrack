package com.urt.interfaces.http;

import java.util.Map;

import com.urt.dto.http.DayFlowQuery;
import com.urt.dto.http.SurplusFlowQuery;
import com.urt.dto.http.SurplusFlowQueryAndPackageCountDto;
import com.urt.dto.http.TerminalDetailRatingDto;

public interface FlowQueryServer {
	//剩余流量查询
	public SurplusFlowQuery surplusFlowQuery(Map<String,String> reqInfo);
	//日流量查询
	public DayFlowQuery dayFlowQuery(Map<String,String> reqInfo);
	//月流量查询
	public DayFlowQuery monthFlowQuery(Map<String,String> reqInfo);
	/**
	 * 联通剩余流量加第一个包的到期时间和总包数
	 * @param reqInfo
	 * @return
	 */
	public SurplusFlowQueryAndPackageCountDto surplusFlowQueryAndPackageCount(Map<String,String> reqInfo);
	/**
	 * 当前包套餐档次、当前包的激活时间、当前包套餐截止时间、当前包已用流量、当前包剩余流量。
	 * @param reqInfo
	 * @return
	 */
	public TerminalDetailRatingDto currentPackage(Map<String, String> reqInfo);
	
}
