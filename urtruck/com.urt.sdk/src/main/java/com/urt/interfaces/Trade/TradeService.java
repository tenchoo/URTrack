package com.urt.interfaces.Trade;

import java.util.List;
import java.util.Map;

import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.unicom.PaymentDto;


public interface TradeService {
	
	/**
	 * 个人客户生成订单
	 * @param orderId
	 * @param iccid
	 * @param goodsId
	 * @param tradeTypeCode
	 * @return
	 * @throws Exception
	 */
	public String addTrade(LaoSsAccountDto user,String custId,String iccid,String goodsId,String goodsReleaseId,String tradeTypeCode,String ifMaintenance)throws Exception;
	/**
	 * 集团客户生成订单
	 * @param custId
	 * @param orderId
	 * @param iccid
	 * @param goodsId
	 * @param tradeTypeCode
	 * @return
	 * @throws Exception
	 */
	public String addTrade(LaoSsAccountDto user,String custId,String orderId,String iccid,String goodsId,String goodsReleaseId,String tradeTypeCode,String ifMaintenance) throws Exception;
	
	
	public String creatTrade(String custId,String orderId,Map<String, Object> param,String goodsId,String goodsReleaseId,String tradeTypeCode,String ifMaintenance) throws Exception;
	/**

	/**
	 * 查询订单
	 * @param TradeId
	 * @return
	 * @throws Exception
	 */
	public TradeDto queryTradeId(Long tradeId)throws Exception;
	/**
	 * 
	 * @param tradeDto
	 * @throws Exception
	 */
	public int updateTrade(TradeDto tradeDto)throws Exception;
	
	//收费状态变更
	public int changeStatus(String tradeId,PaymentDto payment)throws Exception;
	/**
	* 功能描述：判断trade 是否完成  判断条件 根据iccid和业务类型和商品 找到trade 订单状态：0订单，1算费，2收费，3完成
	* @author sunhao
	* @date 2016年12月13日 下午2:06:44
	* @param @param iccid
	* @param @param typeCode
	* @param @param goodsId
	* @param @return
	* @return TradeDto
	* @throws
	 */
	public boolean tradingFailure(String iccid, String typeCode);
	//根据custId和日期查询订购记录
	Map<String, Object> queryPage(TradeDto dto, int pageNo,
			int pageSize,String monthId);
	List<Map<String, Object>> selectRecord(String monthId,Long custId);
}
