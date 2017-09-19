package com.urt.interfaces.Trade;

import java.util.List;

import com.urt.dto.Trade.TradeDto;
import com.urt.dto.Trade.TradeFeeSubDto;
import com.urt.dto.unicom.PaymentDto;

public interface TradeFeeSubService {
	
	//算费
	public int addTradeFeeSub(TradeDto tradeDto) throws Exception;
	//更新
	public int updateTradeFeeSub(TradeFeeSubDto tradeFeeSubDto)throws Exception;
	//查询
	public TradeFeeSubDto queryTradeFreeSubByTradeId(String tradeId)throws Exception;
	//收费状态变更
	public int changePayTag(String tradeId,PaymentDto payment)throws Exception;
	
	//查询消费记录
	public List<TradeFeeSubDto> queryTradeFreeSubByIccid(String iccid, String startTime, String endTime, int curPage, int pageSize);
	//查询消费总数
	public int count(String iccid, String startTime, String endTime);
	public int countTotal(String custId, String startTime, String endTime);
	
	//查询消费记录  2017年6月21日10:05:10 sunhao
	public List<TradeFeeSubDto> queryTradeFeeSubByCustId(String custId, String startTime, String endTime, int curPage, int pageSize);
}
