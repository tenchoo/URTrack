package com.urt.interfaces.User;

import java.util.List;
import java.util.Map;

import com.urt.dto.IccidBatchdataDto;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoPoolDto;
import com.urt.dto.LaoPoolMemberDto;
import com.urt.dto.LaoPoolUseInfoDto;
import com.urt.dto.LaoUserGoodsDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.Goods.LaoUserDto;

public interface UserService {
	//根据custId 查询前十位iccid
	public List<String> getIccidByCustId(Long custId);
	//根据custId 查询所有iccid
	public List<String> getAllIccidByCustId(Long custId);
	
	//根据iccid 后八位查询iccid
	public String getIccid(String end);
	
	// 批量插入卡信息
	public int batchInsert(List<IccidLibDto> iccidLibList);
	
	//多条件查询入库的卡信息
	public Map<String, Object> queryIccidInfo(String custid,String attribute1, String value1, String attribute2, String value2, String startIccid, String endIccid, int pageStart, int pageSize);
	
	//查询符合激活条件的卡信息
	public List<IccidLibDto> queryIccids(String custid, String attribute1, String value1, String attribute2, String value2, String startIccid, String endIccid);
	
	// 用户归档
	public String userArchiving (String tradeId);
	
	public Boolean userArchivingByNio (String tradeId);
	
	public IccidLibDto selectByIccid(String iccid);
	
	public List<GoodsDto> getGoodsByIccid(String iccid); 
	
	public LaoUserGoodsDto getUserGoodsByIccid(String iccid); 
	
	public int getCardCount();
	
	String getCustIdByIccid(String iccid);
	
	public List<Map<String, Object>> queryCardInfo(Map<String, Object> map);
	
	int updateByPrimaryKey(IccidLibDto record);
	
	int updateUser(LaoUserDto record);
	
	int updateUserStatus(LaoUserDto record);
	
	int getUserCount();
	
	List<Map<String, Object>> queryUserInfo(Map<String, Object> param);
	
	public int getCardCountByColony();
	
	List<Map<String, Object>> queryUserInfoByColony(Map<String, Object> param);
	
	LaoUserDto getLaoUserDtoByIccid(String iccid);
	
	//验证用户是否可以订购这个产品
	public boolean ifOrderProduct(String iccid, String goodsId);
	 
	public boolean hasPermission(String iccid, String custId);
	
	LaoUserDto getLaoUserDtoByMsisdn(String msisdn);
	
	//微信支付回调方法
	public String callBackOfWeixin(String payOrderId, String custId,String tradeId);
	
	Map<String, Object> selectByPage(IccidBatchdataDto dto, int pageNo,
			int pageSize);
	
	Map<String, Object> selectDetailByPage(IccidLibDto dto, int pageNo,
			int pageSize);
	
	int insertSelective(IccidBatchdataDto record);
	
    IccidBatchdataDto selectByBatchId(Long batchId);
    
    Map<String,String> selectOneDetailByBatchId(String batchId);
    Map<String, Object> queryPage(LaoUserDto dto, int pageNo, int pageSize);
	List<Map<String,Object>> selectDetailByBatchId(String batchId);
	int batchInsertTSP(List<IccidLibDto> iccidLibList);
	Map<String, Object> poolMenberInfo(LaoPoolMemberDto dto, int pageNo, int pageSize);
	Map<String, Object> poolUseInfo(LaoPoolUseInfoDto dto, int pageNo, int pageSize);
	Map<String, Object> querypoolInfo(LaoPoolDto dto, int pageNo, int pageSize);
	List<LaoPoolDto> queryPoolName();
	int userTestCycle(String iccid, String msisdn) throws Exception;
	public Map<String, Object> selectDetailByPageTSP(IccidLibDto dto, int pageNo, int pageSize);
	//批量销卡
	Map<String, Object> updateByIccids(List<String> iccidList);
	//卡详细信息
	Map<String, Object> selectSimDetails(String iccid);

}
