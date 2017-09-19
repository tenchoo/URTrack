package com.urt.interfaces.chargeOff;

import java.util.List;
import java.util.Map;

import com.urt.dto.LaoCustomerDto;
import com.urt.dto.LaoSsStaticDto;
import com.urt.dto.LaoUserOperatorPlanDto;
import com.urt.dto.Goods.OperatorPlanDto;
import com.urt.dto.Remain.LaoBPaylogDto;
import com.urt.dto.chargeOff.LaoOperatorsBillDto;
import com.urt.dto.chargeOff.LaoOperatorsCycleDto;
import com.urt.dto.chargeOff.LaoOperatorsbillResultDto;
import com.urt.dto.chargeOff.LaoUserfeeInfoDto;

public interface ChargeOffService {
	
	/**
	* 功能描述：根据operatorsPid 查询到对应的plan_id
	* @author sunhao
	* @date 2017年3月29日 下午3:09:55
	* @param @param userId
	* @param @return
	* @return List<LaoUserOperatorPlanDto>
	* @throws
	 */
	OperatorPlanDto selectByPid(String operatorsPid);
	/**
	* 功能描述：批量导入
	* @author sunhao
	* @date 2017年3月9日 下午5:59:03
	* @param @param records
	* @param @return
	* @return int
	* @throws
	 */
    int batchInsert(List<LaoOperatorsBillDto> records);
    /**
    * 功能描述：根据账期和平帐标志， 运营商ID, 查询账单
    * @author sunhao
    * @date 2017年3月9日 下午5:57:55
    * @param @param cycleId
    * @param @param balanceTag
    * @param @return
    * @return List<LaoOperatorsBillDto>
    * @throws
     */
    List<LaoOperatorsBillDto> getOperatorBills(Integer cycleId,Integer operatorsId, String balanceTag, String userId);
    
    /**
    * 功能描述：功能描述：根据账期和平帐标志， 运营商ID, 查询账单 (分页)
    * @author sunhao
    * @date 2017年3月27日 下午2:56:44
    * @param @param dto
    * @param @param pageNo
    * @param @param pageSize
    * @param @return
    * @return Map<String,Object>
    * @throws
     */
    public Map<String, Object> queryOperatorsBillsByPage(LaoOperatorsBillDto dto, int pageNo, int pageSize);
    
    /**
    * 功能描述：根据运营商和运营商ID 计算每个运营商的账期日，并更新最新要计算的账期的起始和截止时间
    * @author sunhao
    * @date 2017年3月11日 上午11:38:41
    * @param @param record
    * @param @return
    * @return int
    * @throws
     */
    int updateOperatorsCycle(LaoOperatorsCycleDto record);
    int insertOperatorsCycle(LaoOperatorsCycleDto record);
    
    /**
     * 功能描述：根据运营商和运营商ID 计算每个运营商的账期日，并更新最新要计算的账期的起始和截止时间
     * @author sunhao
     * @date 2017年3月11日 上午11:38:41
     * @param @param record
     * @param @return
     * @return int
     * @throws
      */
    LaoOperatorsCycleDto  getOperatorsCycle(int cycId, String idValue, String idType);
    
    /**
    * 功能描述：向kafka中发送信息
    * @author sunhao
    * @date 2017年3月13日 上午11:29:35
    * @param @param listMap
    * @return void
    * @throws
     */
    public void sendUserMsg(List<Map<String, Object>> listMap);
    
    /**
     * 功能描述：向kafka中发送信息
     * @author sunhao
     * @date 2017年3月13日 上午11:29:35
     * @param @param listMap
     * @return void
     * @throws
      */
     public void sendBillMsg(List<Map<String, String>> listMap);
    
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
    List<LaoUserOperatorPlanDto> getUserPlansByBillTag(Long userId, String billTag);
    
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
    List<LaoUserOperatorPlanDto> getUserPlansByPlanId(Long userId, int planId);
    
    /**
    * 功能描述：根据账期和平帐标志 查询 user_id 的list
    * @author sunhao
    * @date 2017年3月15日 上午10:45:19
    * @param @param cycleId
    * @param @param balanceTag
    * @param @return
    * @return List<String>
    * @throws
     */
    List<String> getUserIdList(int cycleId, String balanceTag);
    
    /**
    * 功能描述：平帐（对账成功，更新LAO_OPERATORS_BILL对应记录的GLA_FEE=REAL_FEE，BALANCE_TAG=1已平账；否则，GLA_FEE= b.cost_price，BALANCE_TAG=2未平账）
    * @author sunhao
    * @date 2017年3月15日 下午3:09:57
    * @param @param glaFee
    * @param @param balanceTag
    * @return void
    * @throws
     */
    int updateOperatorBill(Long glaFee, String balanceTag, Long operatorsBillId);
    
    /**
    * 功能描述：平帐的时候成功：更新lao_user_operator_plan的bill_tag=1
    * @author sunhao
    * @date 2017年3月15日 下午3:38:26
    * @param @param record
    * @param @return
    * @return int
    * @throws
     */
    int updateUserOperatorPlan(LaoUserOperatorPlanDto record);
    
    /**
    * 功能描述：根据tradeId 查询 用户费用表数据， 得到的结果集 添加了过滤条件（BILL_FEE > 0）
    * @author sunhao
    * @date 2017年3月17日 下午2:23:04
    * @param @param tradeId
    * @param @return
    * @return LaoUserfeeInfoDto
    * @throws
     */
    public LaoUserfeeInfoDto selectByTradeId(String tradeId);
    
    
    /**
    * 功能描述：因为操作数据太大，将blot中的逻辑转移到这里
    * @author sunhao
    * @date 2017年5月4日 下午5:32:52
    * @param @param userId
    * @param @param cycleId
    * @param @param operatorId
    * @param @return
    * @return String
    * @throws
     */
    public String chargeOffBefore(String userId, int cycleId, String operatorId);
    /**
    * 功能描述：核心功能--》 计算费用
    * @author sunhao
    * @date 2017年3月17日 下午3:05:56
    * @param @return
    * @return String
    * @throws
     */
    public String chargeOff(LaoUserOperatorPlanDto userOperatorPlan, LaoOperatorsBillDto operatorsBill, String totalCost, int cycleId);
    
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
     public List<Map<String, Object>> getCostTotalsOfUnicom(Long userId);
     
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
     public List<Map<String, Object>> getCostTotals(Long userId);
      
     /**
     * 功能描述：查询 对账结果（加上了分页）
     * @author sunhao
     * @date 2017年3月24日 下午4:29:48
     * @param @param cycleId
     * @param @param operatorsId
     * @param @return
     * @return List<LaoOperatorsbillResultDto>
     * @throws
      */
     public Map<String, Object> getBillsResult(LaoOperatorsbillResultDto dto, int pageNo, int pageSize);
     
     /**
     * 功能描述：查询所有的客户
     * @author sunhao
     * @date 2017年4月1日 下午4:20:58
     * @param @return
     * @return List<LaoCustomerDto>
     * @throws
      */
     public List<LaoCustomerDto> queryAllAgents();
     
     /**
     * 功能描述：营收 报表（分页）
     * @author sunhao
     * @date 2017年4月6日 下午4:18:41
     * @param @param dto
     * @param @param pageNo
     * @param @param pageSize
     * @param @param startDate
     * @param @param endDate
     * @param @return
     * @return Map<String,Object>
     * @throws
      */
     public Map<String, Object> revenueByPage(LaoBPaylogDto dto, int pageNo, int pageSize, String startDate, String endDate);
     
     /**
     * 功能描述：营收 报表
     * @author sunhao
     * @date 2017年4月8日 下午2:06:48
     * @param @param dto
     * @param @param pageNo
     * @param @param pageSize
     * @param @param startDate
     * @param @param endDate
     * @param @return
     * @return Map<String,Object>
     * @throws
      */
     public List<LaoBPaylogDto> revenue(LaoBPaylogDto dto, String startDate, String endDate);
     
     /**
     * 功能描述：查询费用类型
     * @author sunhao
     * @date 2017年4月7日 下午5:23:44
     * @param @return
     * @return List<LaoSsStaticDto>
     * @throws
      */
     public List<LaoSsStaticDto>  queryPaymentOps();
}
