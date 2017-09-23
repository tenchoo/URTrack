package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoUserOperatorPlan;

public interface LaoUserOperatorPlanExtMapper {
	
    List<LaoUserOperatorPlan> selectByUserId(Long userId);
    
    List<LaoUserOperatorPlan> selectByTradeId(Long tradeId);

    LaoUserOperatorPlan queryCurrentMonthPlan(@Param("userId")Long userId);
    
    LaoUserOperatorPlan getCurrentMonthPlan(@Param("userId")Long userId);
    // 根据iccid查询出套餐总流量 (单位KB)
    double getCountFlowByICCID(String iccid);
    
    List<LaoUserOperatorPlan> getNextPlans(String iccid);
    
    LaoUserOperatorPlan getNextPlan(String iccid);
    
    LaoUserOperatorPlan getBeforePlan(LaoUserOperatorPlan record);
    
    /**
     * 功能描述：为联通提供根据 user_id 和 BILL_TAG 查询到 plan 以及相关费用信息方法，  查询的数据按照开始时间排序
     * @author sunhao
     * @date 2017年3月13日 下午4:55:03
     * @param @param userId
     * @param @param billTag
     * @param @return
     * @return List<LaoUserOperatorPlan>
     * @throws
      */
    List<LaoUserOperatorPlan> getUserPlansByBillTag(@Param("userId")Long userId, @Param("billTag")String billTag);
    
    /**
    * 功能描述：为移动和电信提供根据 user_id 和 plan_Id 查询到 plan 以及相关费用信息方法，  查询的数据必须是在当前的资费计划有效时间内（即开始使用时间和结束时间之间）
    * @author sunhao
    * @date 2017年3月14日 下午1:59:04
    * @param @param userId
    * @param @param billTag
    * @param @return
    * @return List<LaoUserOperatorPlan>
    * @throws
     */
    List<LaoUserOperatorPlan> getUserPlansByPlanId(@Param("userId")Long userId, @Param("planId")int planId);
    
    /**
    * 功能描述：根据trade_id 分组，计算出 这个user_id 总费用  针对联通的数据
    * @author sunhao
    * @date 2017年3月17日 下午3:23:13
    * @param @param userId
    * @param @param tradeId
    * @param @return
    * @return LaoUserOperatorPlan
    * @throws
     */
    List<Map<String, Object>> getCostTotalsOfUnicom(Long userId);
    
    /**
     * 功能描述：根据trade_id 分组，计算出 这个user_id 总费用  针对移动和电信月包的数据
     * @author sunhao
     * @date 2017年3月17日 下午3:23:13
     * @param @param userId
     * @param @param tradeId
     * @param @return
     * @return LaoUserOperatorPlan
     * @throws
      */
     List<Map<String, Object>> getCostTotals(Long userId);
     
     LaoUserOperatorPlan selectMaxOperatorPlanByTradeId(Long tradeId);
     /**
      * 功能描述：根据trade_id 查询lao_user_operator_plan 
      * @author sunhao
      * @date 2017年6月21日10:26:31
      * @param tradeId
      * @return
      */
     List<LaoUserOperatorPlan> selectOperatorPlanByTradeId(Long tradeId);
     
     int updateByUserIdAndGoodsId(List<Map<String, Object>> obj);
     
     int updateByUserDate(List<Map<String, Object>> obj);
}