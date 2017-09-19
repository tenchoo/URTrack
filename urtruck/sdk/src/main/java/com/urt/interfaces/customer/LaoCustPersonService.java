package com.urt.interfaces.customer;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.dto.LaoCustPersonDto;

public interface LaoCustPersonService {
	 int deleteByPrimaryKey(Long custId);

     int insert(LaoCustPersonDto record);

     int insertSelective(LaoCustPersonDto record);

     LaoCustPersonDto selectByPrimaryKey(Long custId);

     int updateByPrimaryKeySelective(LaoCustPersonDto record);

     int updateByPrimaryKey(LaoCustPersonDto record);
     
     int save(LaoCustPersonDto record);
     Map<String, Object> queryPage(LaoCustPersonDto dto, int pageNo, int pageSize);
     //根据客户名称查询个人客户信息
     List<LaoCustPersonDto> queryCustName(@Param("custName")String custName);
     //根据客户卡号查询个人客户信息
     List<LaoCustPersonDto> queryCustByIccid(@Param("iccId")String iccId);
     //根据客户身份证号查询个人客户信息
     List<LaoCustPersonDto> queryCustById(@Param("idNum")String idNum);   
}
