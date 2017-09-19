package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.po.TradeFeeSub;
import com.urt.po.TradeFeeSubPo;

public interface TradeFeeSubExtMapper {
    /**
     * 
     * @param tradeId
     * @return
     */
    TradeFeeSub queryTradeFeeSubByTradeId(Long tradeId);
    /**
     * 缴费查询
     * @param tradeId
     * @return
     */
    Map<String,String> selectTradeInfoByTradeId(Long tradeId);
    
    /**
    * 功能描述：根据iccid查询消费记录
    * @author sunhao
    * @date 2017年2月15日 下午1:35:35
    * @param @param iccid
    * @param @return
    * @return List<TradeFeeSub>
    * @throws
     */
    List<TradeFeeSub> queryTradeFeeSubByIccid(@Param("iccid")String iccid,@Param("startTime") String startTime, @Param("endTime")String endTime,@Param("startRow") int startRow,@Param("endRow") int endRow);
    
    /**
    * 功能描述：得到总条数
    * @author sunhao
    * @date 2017年2月16日 下午4:32:40
    * @param @param iccid
    * @param @param startTime
    * @param @param endTime
    * @param @return
    * @return int
    * @throws
     */
    int countToatal(@Param("iccid")String iccid,@Param("startTime") String startTime, @Param("endTime")String endTime);
    int countTotal(@Param("custId")String custId,@Param("startTime") String startTime, @Param("endTime")String endTime);
    /**
     * 功能描述：根据custId 和月份查询消费记录
     * @param custId
     * @param startTime
     * @param endTime
     * @param startRow
     * @param endRow
     * @return
     */
    List<TradeFeeSubPo> queryTradeFeeSubByCustId(@Param("custId")String custId,@Param("startTime") String startTime, @Param("endTime")String endTime,@Param("startRow") int startRow,@Param("endRow") int endRow);
}