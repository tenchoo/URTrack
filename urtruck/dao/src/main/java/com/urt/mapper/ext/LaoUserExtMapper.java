package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.LaoUser;

public interface LaoUserExtMapper {
	
	List<String> getIccidByCustId(Long custId);
	
	List<String> getAllIccidByCustId(Long custId);
	
	String getIccid(String end);

    LaoUser selectByIccid(String iccid);
    // 根据手机号查询用户
    LaoUser selectByMsisdn(String msisdn);
    
    String getMsisdnByIccid(String iccid);
    //查询客户信息
	public List<LaoUser> queryPage(Page<LaoUser> page);
	
	 //查询用户总量
    int getUserCount();
    
    //查询用户
    List<Map<String, Object>> queryUserInfo(Map<String, Object> param);
    //查询用户
    List<Map<String, Object>> queryUserInfoByCtcc(Map<String, Object> param);
    // 查询集群网用户总数
    int getUserCountByColony();
    // 查询集群网用户
    List<Map<String, Object>> queryUserInfoByColony(Map<String, Object> param);
    
    List<Map<String, Object>> selectCustCardInfo(Long channelCustId);
    
    List<Map<String, Object>> selectCategoryCount(Long channelCustId);
    //查询企业客户本月订购产品
    List<Map<String, Object>> selectGoodsCorporate(Long channelCustId);

	LaoUser queryMessage(String iccid);
	
	//根据custId查询卡总数
    int getCardCount(Long custId);
    
    String getCustIdByIccid(String iccid);
    
    List<Map<String, Object>> getCustId(Long custId);
    
    
    Integer getModifiedCountByIccid(String iccid);

	int getNewCardsOfCurrentMonth(@Param("custId")Long custId);

	int getCardsOfPrevMonth(@Param("custId")Long custId);

	List<Map<String, Object>> selectExpireWarnInfo(@Param("custId")long custId);

	List<Map<String, Object>> selectExpireWarnInfoAll(@Param("custId")long custId);

	//查询运营商下对应的卡数量
    List<Map<String, Object>> selectCorporateCount(Long channelCustId);
    
    String queryInStoreDate(LaoUser laoUserPo);

	List<Map<String, Object>> selectCountByState(LaoUser laoUserPo);
	
    // 查询ofo停机时间
    List<Map<String, Object>> queryStopDate();
    
	LaoUser singleCardQuery(LaoUser user);
	
    int updateByIccidOrMsi(LaoUser laoUser);
    
    LaoUser selectByMap(Map<String,Object> param);
    
    List<LaoUser> selectByListIccid(List<String> list);
    
    List<LaoUser> selectByListMsison(List<String> list);
    
    int updateByIccidSelective(List<LaoUser> laoUser);
    
    int updateByMsisonSelective(List<LaoUser> laoUser);
    
    public List<LaoUser> queryPageUser(Page<LaoUser> page);
    
    int updateByUserId(LaoUser record);
    
    int updateByIccid(LaoUser record);
    //批量销卡
	int updateByIccids(List<String> iccidList);
	//根据ICCID查询卡详细信息
	Map<String, Object> selectDetaisByIccid(String iccid);
}