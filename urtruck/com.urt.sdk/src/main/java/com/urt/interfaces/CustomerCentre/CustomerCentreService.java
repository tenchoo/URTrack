package com.urt.interfaces.CustomerCentre;

import java.util.Map;

import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Remain.LaoBAccesslogDto;

public interface CustomerCentreService {

	Map<String, Object> queryByCustId(Long custId);
    /**
     * 查询当月的充值记录总钱数  已反向的总数
     * @param custId 客户Id
     * @return Map<String, Object>
     */
	Map<String, Object> queryCurrentMonthSumitMoneyByCustId(Long custId);
	/**
	 * 线下预充值的方法
	 * 描述 此方法并不会直接充值到账户,先生成预订单之后,通知财务，待财务审核后,更新此订单的状态，并同步企业余额
	 * @param rechargeType
	 * @param rechargeMoney
	 * @return
	 */
	Map<String, Object> offLineReserveRecharge(String rechargeType, Long money, LaoSsAccountDto user);
	
	/**
	 * 充值记录列表查询
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> queryPage(Long custId, int pageNo, int pageSize,String pageType);
	
	//根据主键查询充值提现日志表
	LaoBAccesslogDto selectByPrimaryKey(Long accessId);


}