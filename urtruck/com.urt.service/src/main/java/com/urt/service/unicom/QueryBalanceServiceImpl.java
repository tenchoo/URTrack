package com.urt.service.unicom;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lenovo.pay.utils.DateUtils;
import com.urt.Ability.unicom.jsonbean.Account;
import com.urt.Ability.unicom.util.Sha256;
import com.urt.Ability.unicom.vo.NowRatePlanResponse;
import com.urt.Ability.unicom.vo.TerminalUsageResponse;
import com.urt.dto.unicom.DeviceDto;
import com.urt.interfaces.unicom.QueryBalanceService;
import com.urt.mapper.ext.ChargeOrderExtMapper;
import com.urt.po.ChargeOrder;
import com.urt.po.UserInfo;

@Service("queryBalanceService")
@Transactional(propagation = Propagation.REQUIRED)
public class QueryBalanceServiceImpl implements QueryBalanceService {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private StService stService;
	@Autowired
	private TariffPlanService tariffPlanService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private ChargeOrderExtMapper chargeOrderDao;

	@Override
	public Map<String, Object> queryFlow(DeviceDto device) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			String iccid = device.getIccid();
			String deviceid = device.getDeviceId();
			String st = device.getLpsust();
			String s = device.getSecretKey();
			if (StringUtils.isEmpty(iccid) || StringUtils.isEmpty(deviceid)
					|| StringUtils.isEmpty(st) || StringUtils.isEmpty(s)) {
				resultMap.put("retcode", "-1");// 参数不全
				return resultMap;
			}
			String signStr = Sha256
					.sha256(Sha256.sha256(iccid + deviceid + st));
			if (!s.equalsIgnoreCase(signStr)) {
				resultMap.put("retcode", "-5");// 签名错误
				log.info("签名错误！！！iccid=" + iccid);
				return resultMap;
			}
			Account account = stService.authSt(st);
			resultMap.put("iccid", iccid);
			if (account == null) {
				resultMap.put("retcode", "-2");// st校验失败
				log.info("st校验失败 iccid=" + iccid);
				return resultMap;
			}
			UserInfo userInfo = userInfoService.getUserInfoByIccid(iccid);
			if (userInfo != null
					&& !account.getAccountID().equalsIgnoreCase(
							userInfo.getUserid())) {
				resultMap.put("retcode", "-6");// 该卡已被绑定
				log.info("该卡已被绑定 iccid=" + iccid);
				return resultMap;
			}
			if (userInfo == null
					|| UserInfo.UserInfoStatus.ACTIVE.getCodeValue() != userInfo
							.getUserstatus()) {
				resultMap.put("retcode", "-7");// 账户不存在
				log.info("账户不存在 或者账户未激活!!!! iccid=" + iccid);
				return resultMap;
			}

			NowRatePlanResponse nowRatePlanResponse = tariffPlanService
					.getNowRatePlan(iccid, account.getAccountID());
			log.info("查询当前资费计划!!!! iccid=" + iccid+"userId="+account.getAccountID());
			if (nowRatePlanResponse == null) {
				// 如果失败,重试一次
				nowRatePlanResponse = tariffPlanService.getNowRatePlan(iccid,
						account.getAccountID());
				if (null == nowRatePlanResponse) {
					resultMap.put("retcode", "-3");
					log.info("获取当前资费计划失败 nowRatePlanResponse="
							+ nowRatePlanResponse);
					return resultMap;
				}
			} else {
				// 如果流量为0,刚更新数据库的的状态为apn1
				if ("0.000".equals(nowRatePlanResponse.getTotalCapacithy())) {
					userInfo.setApntype("apn1");
					userInfo.setModifydate(new Date());
					userInfoService.updateUserActiveStatus(iccid,
							account.getAccountID(), userInfo);
				}
			}

			// BigDecimal capacity =
			// tariffPlanService.getUserRemainTotalCapacity(iccid,
			// account.getAccountID(), nowRatePlanResponse);
			resultMap.put("nacid", userInfo.getApntype());
			resultMap
					.put("ratePlanName", nowRatePlanResponse.getRatePlanName());
			resultMap.put("expirationDate",
					nowRatePlanResponse.getExpirationDate());
			resultMap.put("termLength", nowRatePlanResponse.getTermLength());
			resultMap.put("dataRemaining",
					nowRatePlanResponse.getDataRemaining());
			resultMap.put("capacity", nowRatePlanResponse.getTotalCapacithy());
			resultMap.put("iccid", iccid);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			resultMap.put("timestamp", sdf.format(new Date()));
			resultMap.put("retcode", "1");

		} catch (Exception e) {
			resultMap.put("retcode", "-4");// 系统异常
			log.info("系统异常");
			e.printStackTrace();
		}

		return resultMap;
	}

	@Override
	public Map<String, Object> queryPurchaseHistory(DeviceDto device,
			String accountId, String pageNumber) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String iccid = device.getIccid();
			String deviceid = device.getDeviceId();
			String st = device.getLpsust();
			String s = device.getSecretKey();

			if (StringUtils.isEmpty(iccid) || StringUtils.isEmpty(deviceid)
					|| StringUtils.isEmpty(st) || StringUtils.isEmpty(s)) {
				resultMap.put("retcode", "-1");// 参数不全
				return resultMap;
			}
			String signStr = Sha256
					.sha256(Sha256.sha256(iccid + deviceid + st));
			if (!s.equalsIgnoreCase(signStr)) {
				resultMap.put("retcode", "-5");// 签名错误
				log.info("签名错误！！！iccid=" + iccid);
				return resultMap;
			}
			Account account = stService.authSt(st);
			resultMap.put("iccid", iccid);
			if (account == null) {
				resultMap.put("retcode", "-2");// st校验失败
				log.info("st校验失败 iccid=" + iccid);
				return resultMap;
			}
			UserInfo userInfo = userInfoService.getUserInfoByIccid(iccid);
			if (userInfo != null
					&& !account.getAccountID().equalsIgnoreCase(
							userInfo.getUserid())) {
				resultMap.put("retcode", "-6");// 该卡已被绑定
				log.info("该卡已被绑定 iccid=" + iccid);
				return resultMap;
			}
			if (userInfo == null
					|| UserInfo.UserInfoStatus.ACTIVE.getCodeValue() != userInfo
							.getUserstatus()) {
				resultMap.put("retcode", "-7");// 账户不存在
				log.info("账户不存在 或者账户未激活!!!! iccid=" + iccid);
				return resultMap;
			}
			List<TerminalUsageResponse> terminalUsageDataDetails;
			terminalUsageDataDetails = tariffPlanService.getTerminalUsageDataDetails(iccid, accountId, pageNumber);
			resultMap.put("list", terminalUsageDataDetails);
		} catch (Exception e) {
			resultMap.put("retcode", "-4");// 系统异常
			log.info("系统异常");
			e.printStackTrace();
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> findChargeOrderPage(String startTime,
			String endTime, String userId, int curPage, int pageSize) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Date end =null ,start = null;
		if(!StringUtils.isNotBlank(userId)){
			log.debug("UserId can't be null in method of queryBalanceServiceImpl");
			return resultMap;
		}
		if(StringUtils.isNotBlank(startTime)){
			startTime = startTime+" 00:00:00";
			start = DateUtils.getFormatDateOnDayAndTime(startTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			endTime = endTime+" 23:59:59";
			end = DateUtils.getFormatDateOnDayAndTime(endTime);
		}
		int startRow = (curPage-1) * pageSize;
		int endRow = curPage * pageSize;
		List<ChargeOrder> list = chargeOrderDao.doQueryList(userId,startRow, endRow, start, end);
		resultMap.put("list",list);
		int totalRecord  = chargeOrderDao.doQueryCount(userId,start,end);
		totalRecord = (totalRecord + pageSize -1) / pageSize;
		resultMap.put("totalPage", totalRecord);
		return resultMap;
	}

}
