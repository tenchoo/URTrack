package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.LaoCustomerPo;

public interface LaoCustomerPoExtMapper {
	
	public List<LaoCustomerPo> queryPage(Page<LaoCustomerPo> page);

	public List<LaoCustomerPo> queryAgent(LaoCustomerPo po);
	/**
	* 功能描述：查询所有的客户
	* @author sunhao
	* @date 2017年4月1日 下午4:17:03
	* @param @param po
	* @param @return
	* @return List<LaoCustomerPo>
	* @throws
	 */
	public List<LaoCustomerPo> queryAllAgents();
	
	public List<LaoCustomerPo> queryCustName(@Param("custName")String custName);
	
	public List<LaoCustomerPo> queryComCustByName(@Param("custName")String custName);
	
	public List<LaoCustomerPo> queryPageNormal(Page<LaoCustomerPo> page);
	public List<LaoCustomerPo> feeQueryPage(Page<LaoCustomerPo> page);
	
	public List<Long> queryCustId(Long devCust);
	// 查询用户属性名称
	Map<String,Object> selectStaticName(Long custId);

	public List<LaoCustomerPo> queryChannelCust(@Param("sellType")String sellType);
	
	public List<LaoCustomerPo> queryPerPage(Page<LaoCustomerPo> page);
	
	public Long queryPerCustId(Map<String,Object> param);
}
