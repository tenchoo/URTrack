package com.urt.interfaces.http.device;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.unicom.PayBackDto;




public interface DeviceProductService {
	/**
	 * 查询所有需要展示的广告信息
	 * @return
	 */
	List<Map<String,Object>> selectShowAds(String tag);
	/**
     * 缴费查询
     * @param tradeId
     * @return
     */
    JSONObject selectTradeInfoByTradeId(Long tradeId);
    /**
     * 根据iccid查询缴费详情
     * @param iccid
     * @return
     */
    Map<String,Object> selectPayDetailByIccid(int pageSize,int pageNo,TradeDto tradeDto);
    /**
     * 如果订单表不为空处理支付回调数据
     * @param backDto
     * @return
     * @throws Exception 
     */
	Map<String, Object> payCallBacktMap(PayBackDto backDto) throws Exception;
	
	Map<String, Object> payCallBack(PayBackDto backDto);
    
}
