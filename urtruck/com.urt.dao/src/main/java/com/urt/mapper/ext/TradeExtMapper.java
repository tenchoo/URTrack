package com.urt.mapper.ext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.Trade;
import com.urt.po.TradeFeeSub;

public interface TradeExtMapper {
	
    Trade selectByTradeId(Long tradeId);
    
    List<Trade> tradingFailure(String iccid);
    
    List<TradeFeeSub> selectPayDetailByIccid(Page<TradeFeeSub> page);
    //根据客户id和日期查询订购记录
    List<HashMap<String, Object>> queryPage(Map<String, Object> page);
    //查询要导出的数据
	List<Map<String, Object>> selectRecord(@Param("monthId")String monthId, @Param("custId")Long custId);
	//查询总条数
	long getTotalRecord(Map<String, Object> page);

}