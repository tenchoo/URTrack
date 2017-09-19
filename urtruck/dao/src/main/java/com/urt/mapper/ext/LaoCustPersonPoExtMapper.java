package com.urt.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.LaoCustPersonPo;

public interface LaoCustPersonPoExtMapper {
	 public List<LaoCustPersonPo> queryPage(Page<LaoCustPersonPo> page);
	    //根据客户名称查询个人客户信息
	 public List<LaoCustPersonPo> queryCustName(@Param("custName")String custName);
	    //根据客户卡号查询个人客户信息
	 public List<LaoCustPersonPo> queryCustByIccid(@Param("iccId")String iccId);
	    //根据客户身份证号查询个人客户信息
	 public List<LaoCustPersonPo> queryCustById(@Param("idNum")String idNum);   
}
