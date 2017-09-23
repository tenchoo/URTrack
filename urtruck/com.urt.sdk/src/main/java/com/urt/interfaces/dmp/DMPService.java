package com.urt.interfaces.dmp;

import java.util.List;
import java.util.Map;

import com.urt.dto.dmp.LaoDMPCardDto;


public interface DMPService {
    /**
     * storm中获取终端设备信息，在业务层，把数据加入redis和orcle
     * @param posDataText 设备数据json字符串
     * @param deviceType  设备类型
     * @param randomText  消息的唯一标识
     * @param custId 
     * @return 返回true或false，处理成功或失败
     */
	boolean dmpDataHandler(String posDataText, byte deviceType,
			String randomText, String custId);
    //分页查询设备信息
	Map<String, Object> queryPage(LaoDMPCardDto dto, int pageNo,
			int pageSize);
	//获取imei当前经纬度
	String getPositionPoint(String imei);
	//获取单点的轨迹
	List<String> getPointTrail(String imei);
	//获取批量点坐标
	List<String> getPositionPoints(String[] imeiArr);

	//查询是否存在IMEI
	LaoDMPCardDto  selectByIm(String imei);
	
	int insertCard(List<LaoDMPCardDto> cardListDto);
	//根据设备类型查询该类型下的IMEL号
	List<String> getImei(LaoDMPCardDto cardDto);
	//查询设备详情页
	Map<String, Object> selectDeviceDetails(String imei);
	Map<String, Object >selectIsoline(String imei);

}
