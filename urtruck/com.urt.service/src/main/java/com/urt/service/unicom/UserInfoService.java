package com.urt.service.unicom;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lenovo.game.cache.interceptor.annotation.LiveTime;
import com.lenovo.game.cache.interceptor.annotation.Remove;
import com.urt.mapper.TariffPlanMapper;
import com.urt.mapper.UserInfoMapper;
import com.urt.mapper.UserLinkedTariffPlanMapper;
import com.urt.mapper.WhiteListMapper;
import com.urt.po.TariffPlan;
import com.urt.po.UserInfo;
import com.urt.po.UserLinkedTariffPlan;

@Service("userInfoService")
@Transactional
public class UserInfoService {
	@Autowired
	private UserInfoMapper userInfoDAO;
	@Autowired
	private UserLinkedTariffPlanMapper userLinkedTariffPlanDAO;
	@Autowired
	private TariffPlanMapper tariffPlanDAO;
	@Autowired
	private WhiteListMapper whiteListDAO;
	
	@LiveTime(time = 60* 60 *2, key = "UserInfoService.getUserInfoByIccidAndUserId.(1).(2)")
	public UserInfo getUserInfoByIccidAndUserId(String iccid, String userId) {
		UserInfo userInfo = userInfoDAO.doQueryFirst(iccid, userId);
		return userInfo;
	}
	@LiveTime(time = 60* 60 *2, key = "UserInfoService.getUserInfoByIccid.(1)")
	public UserInfo getUserInfoByIccid(String iccid) {
		UserInfo userInfo = userInfoDAO.doQueryFirstByIccid(iccid);
		return userInfo;
	}	
	@LiveTime(time = 60* 60 *2, key = "UserInfoService.getOnlineUser")
	public List<UserInfo> getOnlineUser() {
		return userInfoDAO.doQueryListByApnType("apn2");
	}
	public List<UserInfo> getUserInfosByIccid(String iccid) {
		String hql = " from UserInfo where iccid=? ";
		List<UserInfo> list = userInfoDAO.doQueryListByIccid(iccid);
		return list;
	}	
	
	public List<UserInfo> getUserInfosByUserid(String userId) {
//		String hql = " from UserInfo where userId=? ";
		List<UserInfo> list = userInfoDAO.doQueryListByUserId(userId);
		return list;
	}	
	
	public void deleteUserInfo(UserInfo userInfo){
		userInfoDAO.delete(userInfo);
	}
	
	public boolean saveUserInfo(String apnType, String deviceId, String iccid, String imsi, String msisdn, String userId, String userName, String realName, String idNum, Integer firstCharge) {
		try {
			Date now = new Date();
			UserInfo userInfo = new UserInfo();
			userInfo.setApntype(apnType);
			userInfo.setCreatedate(now);
			userInfo.setDeviceid(deviceId);
			userInfo.setIccid(iccid);
			userInfo.setImsi(imsi);
			userInfo.setModifydate(now);
			userInfo.setMsisdn(msisdn);
			userInfo.setUserid(userId);
			userInfo.setUsername(userName);
			userInfo.setUserstatus(UserInfo.UserInfoStatus.INIT.getCodeValue());
			userInfo.setAlivechecktime(System.currentTimeMillis());
			userInfo.setRealname(realName);
			userInfo.setIdnum(idNum);
			userInfo.setFirstcharge(firstCharge);
			userInfo.setDonateflag(2);
			userInfoDAO.insert(userInfo);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	@Remove(keys = "{UserInfoService.getUserInfoByIccidAndUserId.(1).(2)}{UserInfoService.getUserInfoByIccid.(1)}{UserInfoService.getOnlineUser}")
	public void updateUserActiveStatus(String iccid, String userId, UserInfo userInfo)  {
		userInfoDAO.updateByPrimaryKey(userInfo);
	}
//	@LiveTime(time = 60* 60 *2, key = "UserInfoService.geAvailabletLinkedTariffPlan.(2).(3)")	
//	public List<UserLinkedTariffPlan> geAvailabletLinkedTariffPlan(Long cusor, String iccid, String userId) {
//		String hql = " from UserLinkedTariffPlan where id>? and iccid=? and userId=? and orderStatus=? and usedStatus=? ";
//		return userLinkedTariffPlanDAO.doQueryList(hql, cusor, iccid, userId, UserLinkedTariffPlan.OrderStatus.BUYED_SUCCESS.getCodeValue(), UserLinkedTariffPlan.UsedStatus.AVAILABLE.getCodeValue());
//	}
	@LiveTime(time = 60* 60 *2, key = "UserInfoService.getDefaultTariffPlan.(1)")
	public TariffPlan getDefaultTariffPlan(String tariffPlanName) {
//		String hql = " from TariffPlan where tariffPlanName=?  and tariffPlanStauts=? ";
		return tariffPlanDAO.doQueryFirst(tariffPlanName, TariffPlan.TariffPlanStatus.NORMAL.getCodeValue());
	}
	@Remove(keys = "{UserInfoService.geAvailabletLinkedTariffPlan.(1).(2)}")
	public void saveUserLinkedTariffPlan(String iccid, String userId, String userName, TariffPlan tariffPlan, boolean isActiveOk) {
		Date now = new Date();
		UserLinkedTariffPlan userLinkedTariffPlan = new UserLinkedTariffPlan();
		userLinkedTariffPlan.setCreatedate(now);
		userLinkedTariffPlan.setIccid(iccid);
		userLinkedTariffPlan.setLinktariffplanid(tariffPlan.getId());
		userLinkedTariffPlan.setModifydate(now);
		userLinkedTariffPlan.setOrderstatus(isActiveOk ? UserLinkedTariffPlan.OrderStatus.BUYED_SUCCESS.getCodeValue() : UserLinkedTariffPlan.OrderStatus.BUYED_FAIL.getCodeValue());
		userLinkedTariffPlan.setTariffplancapacity(tariffPlan.getTariffplancapacity());
		userLinkedTariffPlan.setTariffplanexpdate(tariffPlan.getTariffplanexpdate());
		userLinkedTariffPlan.setTariffplanlength(tariffPlan.getTariffplanlength());
		userLinkedTariffPlan.setTariffplanname(tariffPlan.getTariffplanname());
		userLinkedTariffPlan.setTariffplanstauts(tariffPlan.getTariffplanstauts());
		userLinkedTariffPlan.setUsedstatus(UserLinkedTariffPlan.UsedStatus.AVAILABLE.getCodeValue());
		userLinkedTariffPlan.setUserid(userId);
		userLinkedTariffPlan.setUsername(userName);
		userLinkedTariffPlanDAO.insert(userLinkedTariffPlan);
	}
	
	public boolean isLenovoDevice(String sn){
		return false;
	}
	
	/**
	 * 查看是否白名单
	 * @param cell
	 * @param type
	 * @return
	 */
	public boolean isWhiteList(String cell, int type) {
		long count = whiteListDAO.doQueryCount(cell, type);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	public void removeUser(String iccid,String username){
		
	}
	/**
	 * 修改donateFlag的状态
	 * @param userInfo
	 * @throws Exception
	 */
	public void updateDonateFlag(UserInfo userInfo)throws Exception{
		userInfoDAO.updateByPrimaryKey(userInfo);
	}

}
