package com.urt.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoDMPCardGroupPo;
import com.urt.po.LaoDMPStrategyEditPo;

public interface LaoDMPCardGroupPoExtMapper {
	int batchInsert(List<LaoDMPCardGroupPo> listDto);
	LaoDMPCardGroupPo selectByIm(@Param("imei")String imei,@Param("id")Long id);
	LaoDMPCardGroupPo selectByGroupId(Long id);
	Integer  delCardGroup(Long id);
	//根据imei查询组名，多个组名的话用逗号隔开
	String selectGroupByImei(String imei);
	List<LaoDMPStrategyEditPo> getSchemesbyImei(@Param("imei")String imei);
	Integer selectDeviceNum(Long id);
	//根据IMEI查询IMEI所在的组id
	List<Long> getGroupIdsByImei(@Param("imei")String imei);
}