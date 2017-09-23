package com.urt.service.unicom;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.Ability.http.util.TokenUtils;
import com.urt.Ability.unicom.jsonbean.Account;
import com.urt.Ability.unicom.service.QueryProductInfoService;
import com.urt.Ability.unicom.util.Sha256;
import com.urt.Ability.unicom.util.SoapConstant;
import com.urt.cache.DMPCacheUtil;
import com.urt.dto.unicom.DeviceDto;
import com.urt.interfaces.unicom.DeviceService;
import com.urt.mapper.LaoKeyManagementMapper;
import com.urt.mapper.ext.IccidLibExtMapper;
import com.urt.po.IccidLibrary;
import com.urt.po.ImeiLibrary;
import com.urt.po.LaoKeyManagement;
import com.urt.po.UserInfo;

@Service("deviceService")
@Transactional(propagation = Propagation.REQUIRED)
public class DeviceServiceImpl implements DeviceService {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private StService stService;
	@Autowired
	private IccidLibraryService iccidLibraryService;
	@Autowired
	private ImeiLibraryService imeiLibraryService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private TariffPlanService tariffPlanService;
	@Autowired
	private QueryProductInfoService queryProductInfoService;
	@Autowired
	private TariffPlanNormalService tariffPlanNormalService;
	@Autowired
	private TariffPlanExceptionService tariffPlanExceptionService;
	@Autowired
	private IccidLibExtMapper iccidLibExtDAO;
	
	@Autowired
	private DMPCacheUtil redisCacheUtil;
	
	@Autowired
	private LaoKeyManagementMapper laoKey;

	@Override
	public Map<String, String> verifySim(String iccid, String imei,String secretUserName) {
		Map<String, String> resultMap = new HashMap<>();
		if (StringUtils.isEmpty(iccid) || StringUtils.isEmpty(imei)
				|| StringUtils.isEmpty(secretUserName)) {
			resultMap.put("retcode", "-1");// 参数不全
			log.info("DeviceService verifySim 参数不全！！！");
			return resultMap;
		}

		Account account = stService.authSt(secretUserName);
		resultMap.put("iccid", iccid);
		if (account == null) {
			resultMap.put("retcode", "-2");// st校验失败
			log.info("DeviceService st校验失败 iccid=" + iccid);
			return resultMap;
		}

		IccidLibrary iccidLibrary = iccidLibraryService
				.findIccidLibraryByIccid(iccid);
		ImeiLibrary imeiLibrary = imeiLibraryService
				.findImeiLibraryByIccid(imei);
		boolean iccidRes = iccidLibrary != null ? true : false;
		boolean imeiRes = imeiLibrary != null ? true : false;

		if (iccidRes && imeiRes) {
			resultMap.put("retcode", "1");
		} else {
			resultMap.put("retcode", "-3");
			if (!iccidRes) {
				log.info("----DeviceService------非法iccid---------");
			}
			if (!imeiRes) {
				log.info("----DeviceService-----非法imei----------");
			}
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> getDeviceStatus(DeviceDto device) {
		Long beginTime = System.currentTimeMillis();
		log.info("................激活前查询设备状态...................time:" + beginTime);
		Map<String, Object> resultMap = null;

		resultMap = commonVerify(device);
		if (resultMap.size() != 0) {
			return resultMap;
		}
		Account account = stService.authSt(device.getLpsust());
		if (account == null) {
			resultMap.put("retcode", "-2");// st校验失败
			log.info("st校验失败:********"+device.getLpsust());
			return resultMap;
		}
		UserInfo userInfo = userInfoService.getUserInfoByIccid(device.getIccid());
		if (userInfo != null && !account.getAccountID().equalsIgnoreCase(userInfo.getUserid())) {
			resultMap.put("retcode", "-6");// 该卡已被绑定
			return resultMap;
		}
		String apnName = null;
		boolean isActive = false;
		if (userInfo == null) {
			String tariffPlanName = tariffPlanService
					.getNowActiveTariffPlanName(device.getIccid(),
							device.getDeviceId(), device.getImsi(),
							device.getMsisdn(), account.getUsername(),
							account.getAccountID(), device.getRealname(),
							device.getIdnum(), 1);
			if (tariffPlanName == null) {
				resultMap.put("retcode", "-3");// 获取资费计划异常
				log.info("获取资费计划异常");
				return resultMap;
			}
			apnName = tariffPlanName;
			// isActive = true;
		} else {
			apnName = userInfo.getApntype();
			isActive = UserInfo.UserInfoStatus.ACTIVE.getCodeValue() != userInfo.getUserstatus() ? false : true;
		}
		resultMap.put("isActive", isActive);
		resultMap.put("iccid", device.getIccid());
		resultMap.put("nacid", apnName);
		resultMap.put("retcode", "1");// 成功

		return resultMap;
	}

	@Override
	public Map<String, Object> activeInetAccess(DeviceDto device) {

		Long beginTime = System.currentTimeMillis();
		log.info("................激活开始...................time:" + beginTime);
		Map<String, Object> resultMap = null;

		try {
			resultMap = commonVerify(device);
			if (resultMap.size() != 0) {
				return resultMap;
			}
			Account account = stService.authSt(device.getLpsust());
			resultMap.put("iccid", device.getIccid());
			if (account == null) {
				resultMap.put("retcode", "-2");// st校验失败
				log.info("st校验失败 iccid=" + device.getIccid());
				return resultMap;
			}
			UserInfo userInfo = userInfoService.getUserInfoByIccid(device
					.getIccid());
			if (userInfo == null) {
				resultMap.put("retcode", "-4");// 系统异常
				log.info("未关联Lenovoid iccid=" + device.getIccid());
				return resultMap;
			}

			if (userInfo != null && !account.getAccountID().equalsIgnoreCase(userInfo.getUserid())) {
				resultMap.put("retcode", "-6");// 该卡已被绑定
				log.info("该卡已被绑定 iccid=" + device.getIccid());
				return resultMap;
			}

			if (userInfo != null) {
				if (UserInfo.UserInfoStatus.ACTIVE.getCodeValue() == userInfo.getUserstatus()) {
					resultMap.put("retcode", "2");
					resultMap.put("nacid", userInfo.getApntype());
					return resultMap;
				}
				if (UserInfo.UserInfoStatus.DISABLED.getCodeValue() == userInfo
						.getUserstatus()) {
					resultMap.put("retcode", "3");// 非法用户
					log.info("非法用户 iccid=" + device.getIccid());
					return resultMap;
				}
			}
			if (0 == userInfo.getUserstatus() && "apn1".equals(userInfo.getApntype()) && 1 == userInfo.getDonateflag()) {
				resultMap.put("retcode", "1");
				resultMap.put("nacid", userInfo.getApntype());
				log.info("重复激活中------------------");
				return resultMap;

			}
			// 更新用户imei 暂时去掉
			// userInfo.setImei(imei);
			// userInfoService.updateUserActiveStatus(iccid,
			// userInfo.getUserId(), userInfo);
			if (StringUtils.isBlank(device.getWtype())) {
				device.setWtype("1");
			}
			boolean activeResult = false;
			if (2 == userInfo.getDonateflag()) {
				activeResult = tariffPlanNormalService.sendActiveRequest(
						device.getIccid(), userInfo.getUserid(),
						userInfo.getUsername(), device.getWtype());
			} else if (-1 == userInfo.getDonateflag()) {
				activeResult = tariffPlanExceptionService.sendActiveRequest(
						device.getIccid(), userInfo.getUserid(),
						userInfo.getUsername(), device.getWtype());
			}
			boolean opResult = tariffPlanNormalService
					.createUserDefaultTariffPlan("apn1", "apn2",
							device.getIccid(), account.getAccountID(),
							activeResult);
			userInfo = userInfoService.getUserInfoByIccid(device.getIccid());
			if (opResult) {
				resultMap.put("retcode", "1");
				resultMap.put("nacid", userInfo.getApntype());
			} else {
				resultMap.put("retcode", "0");
			}

			log.info("................激活结束.......... Total cost: "
					+ (System.currentTimeMillis() - beginTime) / 1000.0);
		} catch (Exception e) {
			resultMap.put("retcode", "-4");// 系统异常
			log.info("系统异常");
			e.printStackTrace();
		}

		return resultMap;
	}

	@Override
	public Map<String, Object> openInternet(DeviceDto device) {
		Long beginTime = System.currentTimeMillis();
		log.info("打开网络开始：open internet BeginTime: " + beginTime);
		Map<String, Object> resultMap = new HashMap<>();
		
		try{
		
		if(StringUtils.isEmpty(device.getIccid()) || StringUtils.isEmpty(device.getDeviceId()) || StringUtils.isEmpty(device.getLpsust()) || StringUtils.isEmpty(device.getSecretKey())){
			resultMap.put("retcode", "-1");//参数不全
			log.info("参数不全！！！");
			return resultMap;
		}
		String signStr = Sha256.sha256(Sha256.sha256(device.getIccid() + device.getDeviceId() + device.getLpsust()));
		if(!device.getSecretKey().equalsIgnoreCase(signStr)) {
			resultMap.put("retcode", "-5");//签名错误
			log.info("签名错误！！！iccid=" + device.getIccid());
			return resultMap;
		}
		Long stBeginTime = System.currentTimeMillis();
		Account account = stService.authSt(device.getLpsust());
		log.info("st cost: " + (System.currentTimeMillis() - stBeginTime)/1000.0);
		resultMap.put("iccid", device.getIccid());
		if(account == null) {
			resultMap.put("retcode", "-2");//st校验失败
			log.info("st校验失败 iccid="+device.getIccid());
			return resultMap;
		}
		UserInfo userInfo = userInfoService.getUserInfoByIccid(device.getIccid());
		if(userInfo != null && !account.getAccountID().equalsIgnoreCase(userInfo.getUserid())) {
			resultMap.put("retcode", "-6");//该卡已被绑定
			log.info("该卡已被绑定 iccid="+device.getIccid());
			return resultMap;
		}
		if(userInfo == null) {
			resultMap.put("retcode", "-4");//账户不存在
			log.info("账户不存在 iccid="+device.getIccid());
			return resultMap;
		}
		
		boolean changeResult  = false;
		Map<String, String> map_ratePlan = tariffPlanService.getRatePlan(device.getIccid(), account.getAccountID());
		
		//如果状态为已激活并且 (通道为apn1或者流量为0) 则不进行通道切换
		if(!(userInfo.getUserstatus() == UserInfo.UserInfoStatus.ACTIVE.getCodeValue() && ("apn1".equals(userInfo.getApntype()) || "0.000".equals(map_ratePlan.get("dataRemaining"))))) {
			changeResult = tariffPlanService.openInternet(device.getIccid(), account.getAccountID());
		}
	
//		boolean changeResult = true;
		
		userInfo = userInfoService.getUserInfoByIccid(device.getIccid());
		resultMap.put("nacid", userInfo.getApntype());
		resultMap.put("retcode", changeResult ? "1" : "0");
		resultMap.put("iccid", device.getIccid());
		resultMap.putAll(map_ratePlan);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		resultMap.put("now",sdf.format(new Date()));
		resultMap.put("data_limit",SoapConstant.DATA_LIMIT);
		resultMap.put("date_limit",SoapConstant.DATE_LIMIT);
		log.info("打开网络结束：open internet Total cost: " + (System.currentTimeMillis() - beginTime) / 1000.0);
		
		}catch (Exception e){
			resultMap.put("retcode", "-4");//系统异常
			log.info("系统异常");
			e.printStackTrace();
		}
			
		return resultMap;
	}

	@Override
	public Map<String, Object> queryLenovoidStatus(String userId) {
		Map<String, Object> resultMap = new HashMap<>();

		try {
			if (StringUtils.isEmpty(userId)) {
				resultMap.put("retcode", "-1");// 参数不全
				log.info("参数不全");
				return resultMap;
			}
			List<UserInfo> userInfoList = userInfoService.getUserInfosByUserid(userId);
			if (CollectionUtils.isEmpty(userInfoList)) {
				resultMap.put("retcode", "2");// 未绑定sim卡
				log.info("未绑定sim卡 + iccId" + userId);
				return resultMap;
			}

			IccidLibrary iccidLibrary = null;
			List<IccidLibrary> iccidLibraryList = new ArrayList<IccidLibrary>();
			for (UserInfo userInfo : userInfoList) {
				log.info(">>>>>>>>>>>>>>>>>>>>iccid:"+userInfo.getIccid());
				iccidLibrary = iccidLibraryService
						.findIccidLibraryByIccid(userInfo.getIccid());
				if (null != iccidLibrary) {
					iccidLibraryList.add(iccidLibrary);
				}
			}

			resultMap.put("iccidList", iccidLibraryList);
			resultMap.put("retcode", "1");// 成功
		} catch (Exception e) {
			resultMap.put("retcode", "4");// 系统异常
			log.info("系统异常");
			e.printStackTrace();
		}
		return resultMap;
	}

	public Map<String, Object> commonVerify(DeviceDto device) {
		Long beginTime = System.currentTimeMillis();
		log.info("................激活前进入通用的校验...................time:" + beginTime);
		Map<String, Object> resultMap = new HashMap<>();
		try {
			if (StringUtils.isEmpty(device.getIccid())
					|| StringUtils.isBlank(device.getLpsust())
					|| StringUtils.isBlank(device.getDeviceId())
					|| StringUtils.isBlank(device.getSecretKey())) {
				resultMap.put("retcode", "-1");// 参数不全
				log.info("参数不全！！！");
				return resultMap;
			}

			// 校验签名
			String signStr = Sha256.sha256(Sha256.sha256(device.getIccid() + device.getDeviceId() + device.getLpsust()));
			if (!device.getSecretKey().equalsIgnoreCase(signStr)) {
				resultMap.put("retcode", "-5");// 签名错误
				log.info("签名错误！！！iccid=" + device.getIccid());
				return resultMap;
			}

			// 查询iccid库，查找对应设备
			IccidLibrary iccidLibrary = iccidLibraryService
					.findIccidLibraryByIccid(device.getIccid());
			if (null == iccidLibrary) {
				resultMap.put("retcode", "-7");// 系统异常,iccid库不存在此卡
				log.info("非法iccid");
				return resultMap;
			} else {
				// 如果是mifi的特殊deviceid
				if ("HwmY9oKEBEjs9r3g72CrJ8m3trHQRJ4w".equals(device
						.getDeviceId())) {
					if (!"MIFI".equals(iccidLibrary.getDevicetype())) {
						resultMap.put("retcode", "-9");// iccid所属设备非mifi设备
						log.info("iccid所属设备非mifi设备");
						return resultMap;
					}
				}

				// 如果是mifi 则要校验密钥
				if ("MIFI".equals(iccidLibrary.getDevicetype())) {
					if (StringUtils.isBlank(device.getPrivatekey())
							|| StringUtils.isBlank(device.getRealname())
							|| StringUtils.isBlank(device.getIdnum())) {
						resultMap.put("retcode", "-1");// 非法iccid
						log.info("参数不全！！！iccid=" + device.getIccid());
						return resultMap;
					}

					if (!device.getPrivatekey().equals(iccidLibrary.getPrivatekey())) {
						resultMap.put("retcode", "-8");// 密钥与iccid不匹配
						log.info("密钥与iccid不匹配！！iccid=" + device.getIccid() + "密钥：" + device.getPrivatekey());
						return resultMap;
					}
				}
			}
		} catch (Exception e) {
			resultMap.put("retcode", "-4");// 系统异常
			log.info("系统异常");
			e.printStackTrace();
		}
		return resultMap;
	}
	
	
	/**
     * 简单读取
     * @throws Exception 
     */
	public String getValue(String key) throws Exception{
		return redisCacheUtil.getValue(key);	
	}
	/**
	 * 简单保存
	 * @param key
	 * @param value
	 * @throws Exception 
	 */
	public void set(String key,String value) throws Exception{
		redisCacheUtil.set(key,value);
	}
	/**
	 * 删除key
	 * @param key
	 * @throws Exception
	 */
	public void del(String key) throws Exception{
		redisCacheUtil.del(key);
	}
	/**
	 * 判断键是否存在
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Boolean exists(String key) throws Exception{
		return redisCacheUtil.exists(key);
	}
	 /**
	   * <p>设置key value并制定这个键值的有效期</p>
	   * @param key
	   * @param value
	   * @param seconds 单位:秒
	   * @return 成功返回OK 失败和异常返回null
	   */
	public String  setex(String key,int seconds,String value) throws Exception{
		return redisCacheUtil.setex(key, seconds, value);		
	}

	@Override
	public boolean checkSign(String custId, String iccid, String sign, String logo) {
		LaoKeyManagement laoKeyPO = laoKey.selectByCustId(custId);
		Map<String,String> params = new HashMap<String,String>();
		params.put("custId", custId);
		params.put("iccid", iccid);
		params.put("logo", logo);
		String md5Sign = TokenUtils.md5Sign(params, null, laoKeyPO.getAuthKey());
		if(StringUtils.isNotBlank(md5Sign) && md5Sign.equals(sign)){
			return true;
		}
		return false;
	}
}
