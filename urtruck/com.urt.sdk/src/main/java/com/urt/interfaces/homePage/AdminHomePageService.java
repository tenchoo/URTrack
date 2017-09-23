package com.urt.interfaces.homePage;

import java.util.List;
import java.util.Map;

public interface AdminHomePageService {

	// 查询本月流量使用量前五名
	List<Map<String, Object>> selectTop5ByUseFlow();
	
	// 查询本月流量平均单卡使用量前五名
	List<Map<String, Object>> selectTop5ByAverageUseFlow();
	
	// 查询本月企业消费量前五名
	List<Map<String, Object>> selectTop5ByCustConsume();
	
	// 查询本月企业平均消费前五名
	List<Map<String, Object>> selectTop5ByAverageConsume();
	
	// 查询本月个人消费前五名
	List<Map<String, Object>> selectTop5ByPersonal();
	
	// 查询本月个人消费后五名
	List<Map<String, Object>> selectLast5ByPersonal();
	
	// 查询总卡数根据服务状态分类
	List<Map<String, Object>> selectCountByState();
	
	// 查询总卡数根据运营商分类
	List<Map<String, Object>> selectCountByOperators();
	
	// 查询企业的卡总量前10名
	List<Map<String, Object>> selectTop10ByCust();
	
	// 查询近1年内每月用户增长数量
	List<Map<String, Object>> selectUserCountByYear();
	
	// 查询本月业务量
	List<Map<String, Object>> selectCountByTrade();
	
	// 查询近一个月使用流量
	List<Map<String, Object>> selectFlowCountByMonth();
	
	// 查询本月营收费用
	List<Map<String, Object>> selectComeByPayment();
	
	// 查询近六个月的消费
	List<Map<String, Object>> selectConsumeBy6Month();
	
	// TO B 智能展业,智能互联,智能车联,运营商物联分类查询用户数及收入情况
	List<Map<String, Object>> queryUserIncomeByIntelligent(String date);
	// TO C 智能互联 分类查询用户数及收入情况
	Map<String, Object> queryZhiNengHuLian(String date);

}
