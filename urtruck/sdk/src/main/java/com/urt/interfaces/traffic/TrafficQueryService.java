package com.urt.interfaces.traffic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.urt.dto.LaoCustGroupDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.traffic.LaoTrafficMmDto;
import com.urt.dto.traffic.TrafficQueryDetailsDto;
import com.urt.dto.traffic.TrafficQueryNowDto;

public interface TrafficQueryService {


	// 剩余流量实时查询
	TrafficQueryNowDto doNowTrafficQuery(String iccid);
	// 日流量查询
	List<TrafficQueryDetailsDto> doDayTrafficQuery(String iccid,String date);
	// 月流量查询
	List<TrafficQueryDetailsDto> doMonthTrafficQuery(String iccid,String date);
	
	// 查询用户属性名称
	Map<String,Object> selectStaticName(Long custId);

	// 根据渠道客户ID查询
	List<LaoTrafficMmDto> selectByChannelCustId(Long channelCustId);
  
    // 根据渠道客户ID，剩余流量查询，使用流量
    List<LaoTrafficMmDto> selectByTraffic(Map<String,Object> map);
    List<LaoTrafficMmDto> selectByTraffic(long channelCustId, String type,String dataCycle, Integer integer, Integer integer2);
    // 根据渠道客户name 查询渠道客户ID
    public List<LaoCustGroupDto> selectAll();
    
    public Map<String,Object> selectMonthFlowCount(Long channelCustId);
  //近15天流量查询按MB显示
    public Map<String,Object> selectMonthFlowCountMB(Long channelCustId);
    
    List<Map<String, Object>> selectMaxFlowCount(Long channelCustId);
    
    List<Map<String, Object>> selectMinFlowCount(Long channelCustId);
    
    Map<String, Object> selectPrefixFlow(Long channelCustId);
    
    long queryListByUserId(Long acconutId);
    
    //查询同一客户渠道下总人数
    int selectCountByChannel(Map<String,Object> map);
    /**
     * 根据条件查询企业流量分布
     * @param channelCustId
     * @param type
     * @param month 
     * @param integer
     * @param integer2
     * @param value2 
     * @param value1 
     * @param goodsId 
     * @param operatorId 
     * @return
     */
	Map<String, Object> selectDataSpread(long channelCustId, String type,
			String month, Integer integer, Integer integer2, String value1, String value2,String operatorId,String goodsId);
	
	Map<String, String> getUpdateTimeInfo();
    /**
     * 根据判断ICCID是否是当前用户的ICCID或者其渠道发展客户的ICCID
     * @param ICCID
     * @param custId
     * @param custType 客户类型
     * @return true false
     */
	boolean bIsLegalIccId(String iccid,Long custId);
	List<GoodsDto> selectGoodsByOperator(String operatorId);
	String getImsiByIccid(String iccid);
	String getIccidByImsi(String imsi);
	Map<String, Object> queryPage(Map<String, Object> map, int pageNo, int pageSize);
	
}
