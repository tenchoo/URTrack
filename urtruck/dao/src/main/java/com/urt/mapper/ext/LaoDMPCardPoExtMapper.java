package com.urt.mapper.ext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.LaoDMPCardPo;

public interface LaoDMPCardPoExtMapper {
    //卡数据过时修改
	void updateFlag(@Param("imei")String imei, @Param("triggedReson")short triggedReson);
	//根据imei获取卡信息有效记录的id
	Long selectCardIdByImei(@Param("imei")String imei);
	
	List<HashMap<String, Object>> queryPage(Page<LaoDMPCardPo> page);
	//根据IMEI查询是否存在
	LaoDMPCardPo selectByImei(String imei);
	//将遍历出的IMEL存入设备表
	int insertImei(List<LaoDMPCardPo> listDto);
	//根据custId和设备类型 查询Imei号
	List<String>  getImei(LaoDMPCardPo cardPo);
	//根据IMEI查询设备详情
	LaoDMPCardPo selectDeviceDetails(String imei);
	//根据IMEI查询设备型号,客户名称,电池电量，网络类型
	Map<String, Object> selectDeviceAttirbute(String imei);
   
}