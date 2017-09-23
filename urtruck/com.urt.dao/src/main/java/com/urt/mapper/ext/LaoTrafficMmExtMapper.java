package com.urt.mapper.ext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.LaoAlmRulePo;
import com.urt.po.LaoTrafficMm;

public interface LaoTrafficMmExtMapper {

	
	// 根据渠道客户ID查询
	List<LaoTrafficMm> selectByChannelCustId(Long channelCustId);
	
	List<LaoTrafficMm> getLaoTrafficMms(Long custId);
	
	LaoTrafficMm getUserPlanInfoByIccid(String iccid);
	// 根据用户ID查询
	LaoTrafficMm selectByUseId(@Param("userId")Long userId, @Param("dataCycleMm")String dataCycleMm);
  
    // 根据渠道客户ID，剩余流量查询，使用流量
	List<Map<String,Object>> selectByTraffic(Map<String,Object> map);
    
    List<Map<String, Object>> selectMonthFlowCount(Long channelCustId);
    
    List<Map<String, Object>> selectMaxFlowCount(Long channelCustId);
    
    List<Map<String, Object>> selectMinFlowCount(Long channelCustId);
    
    //查询同一客户渠道下总人数
    int selectCountByChannel(Map<String,Object> map);

	Map<String, Object> selectPrefixFlow(Long channelCustId);

	BigDecimal selectMaxMinFiveFlow(@Param("channelCustId") Long channelCustId, @Param("useSum") long useSum);

	BigDecimal selectMaxMinFiveCount(@Param("channelCustId") Long channelCustId, @Param("maxMinFlow") long maxMinFlow);

	BigDecimal selectMinMaxFiveFlow(@Param("channelCustId") Long channelCustId, @Param("useSum") long useSum);

	BigDecimal selectMinMaxFiveCount(@Param("channelCustId") Long channelCustId, @Param("minMaxFlow") long minMaxFlow);

	List<Map<String, Object>> selectEightCount(Map<String, Object> monthRateMap);
	
	List<Map<String, Object>> getFlowCellByCustIdPlanId(Map<String, Object> map);
	
	List<Map<String, Object>> getLaoTrafficMmsByCustId(Map<String, Object> map);
	
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
    //查询使用流量
	Map<String, Object> selectUseData(@Param("channelCustId")long channelCustId, @Param("useCount1")Integer useCount1,
			@Param("month")String month, @Param("useCount2")Integer integer, @Param("value1")String value1, @Param("value2")String value2, @Param("operatorId")String operatorId, @Param("goodsId")String goodsId);
	//查询剩余流量
	Map<String, Object> selectRemainData(@Param("channelCustId")long channelCustId, @Param("useCount1")Integer useCount1,
			@Param("month")String month, @Param("useCount2")Integer integer, @Param("value1")String value1, @Param("value2")String value2, @Param("operatorId")String operatorId, @Param("goodsId")String goodsId);
    //查询前百分之五的流量最大值
	BigDecimal selectMaxFlowPrefiveOfUse(@Param("channelCustId")long channelCustId, @Param("month")String month, @Param("sum")long sum, @Param("useCount1")Integer useCount1, @Param("useCount2")Integer useCount2, @Param("value1")String value1, @Param("value2")String value2, @Param("operatorId")String operatorId, @Param("goodsId")String goodsId);
	BigDecimal selectMaxFlowPrefiveOfUseOfRe(@Param("channelCustId")long channelCustId,@Param("month")String month, @Param("sum")long sum, @Param("useCount1")Integer useCount1, @Param("useCount2")Integer useCount2,@Param("value1")String value1, @Param("value2")String value2, @Param("operatorId")String operatorId, @Param("goodsId")String goodsId);
	//查询后百分之无的流量最小值
	BigDecimal selectMinFlowBack(@Param("channelCustId")long channelCustId, @Param("month")String month, @Param("sum")long sum,
			@Param("useCount1")Integer useCount1,  @Param("useCount2")Integer useCount2, @Param("value1")String value1, @Param("value2")String value2, @Param("operatorId")String operatorId, @Param("goodsId")String goodsId);
	BigDecimal selectMinFlowBackOfRe(@Param("channelCustId")long channelCustId, @Param("month")String month, @Param("sum")long sum,
			@Param("useCount1")Integer useCount1, @Param("useCount2")Integer useCount2,@Param("value1")String value1, @Param("value2")String value2, @Param("operatorId")String operatorId, @Param("goodsId")String goodsId);
    //中间八组组名和人数
	List<Map<String, Object>> selectEight(@Param("channelCustId")long channelCustId, @Param("month")String month, @Param("maxmf")long maxmf,
			@Param("minmf")long minmf, @Param("avgFlowOfEight")Double avgFlowOfEight, @Param("useCount1")Integer useCount1, @Param("useCount2")Integer useCount2,@Param("value1")String value1, @Param("value2")String value2, @Param("operatorId")String operatorId, @Param("goodsId")String goodsId);

	List<Map<String, Object>> selectEightOfRe(@Param("channelCustId")long channelCustId, @Param("month")String month, @Param("maxmf")long maxmf,
			@Param("minmf")long minmf,  @Param("avgFlowOfEight")Double avgFlowOfEight,@Param("useCount1")Integer useCount1, @Param("useCount2")Integer useCount2,@Param("value1")String value1, @Param("value2")String value2, @Param("operatorId")String operatorId, @Param("goodsId")String goodsId);

	BigDecimal selectPreFiveCount(@Param("channelCustId")long channelCustId, @Param("month")String month, @Param("maxmf")long maxmf,
			@Param("useCount1")Integer useCount1, @Param("useCount2")Integer useCount2, @Param("value1")String value1, @Param("value2")String value2, @Param("operatorId")String operatorId, @Param("goodsId")String goodsId);

	BigDecimal selectPreFiveCountOfre(@Param("channelCustId")long channelCustId, @Param("month")String month, @Param("maxmf")long maxmf,
			@Param("useCount1")Integer useCount1, @Param("useCount2")Integer useCount2, @Param("value1")String value1, @Param("value2")String value2, @Param("operatorId")String operatorId, @Param("goodsId")String goodsId);

	BigDecimal selectBackFiveCount(@Param("channelCustId")long channelCustId, @Param("month")String month, @Param("minmf")long minmf,
			@Param("useCount1")Integer useCount1, @Param("useCount2")Integer useCount2,@Param("value1")String value1, @Param("value2")String value2, @Param("operatorId")String operatorId, @Param("goodsId")String goodsId);

	BigDecimal selectBackFiveCountOfRe(@Param("channelCustId")long channelCustId, @Param("month")String month, @Param("minmf")long minmf,
			@Param("useCount1")Integer useCount1, @Param("useCount2")Integer useCount2, @Param("value1")String value1, @Param("value2")String value2, @Param("operatorId")String operatorId, @Param("goodsId")String goodsId);

	List<Map<String, Object>> selectUseByTraffic(@Param("channelCustId")long channelCustId,@Param("dataCycle")String dataCycle, @Param("useCount1")Integer useCount1, @Param("useCount2")Integer useCount2);

	List<Map<String, Object>> selectRemainByTraffic(@Param("channelCustId")long channelCustId,@Param("dataCycle")String dataCycle, @Param("useCount1")Integer useCount1, @Param("useCount2")Integer useCount2);

	BigDecimal getNotSendFlowSum(Long userId);

	int getCardNum(@Param("custId")long custId, @Param("rate")float rate);

	List<Map<String, Object>> queryPage(Page<Map<String, Object>> page);

	
}
