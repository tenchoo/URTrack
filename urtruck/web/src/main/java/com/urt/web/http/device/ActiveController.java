package com.urt.web.http.device;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.urt.common.enumeration.ConstantIntEnum;
import com.urt.dto.CardStatusDto;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoDeviceRelDto;
import com.urt.dto.LaoPeripheralSysAccessLogDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.device.Account;
import com.urt.dto.http.CardActiveResp;
import com.urt.dto.http.StopOnDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.http.CardActiveService;
import com.urt.interfaces.http.ServerCheckService;
import com.urt.interfaces.session.SessionOperService;
import com.urt.web.common.util.device.Sha256;

@Controller
@RequestMapping("/deviceActive")
public class ActiveController {
	private static final Logger log = Logger.getLogger(QueryGoodsController.class);

	private static final String SERVERNAME = "激活数据上网服务";
	
	private static final String KeyPort="device90001";
	@Autowired
	private UserService userService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private CardActiveService cardActiveService;
	
	@Autowired
	private SessionOperService sessionOperService;

	@Autowired
	private ServerCheckService serverService;

	@RequestMapping(value = "activeCard", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> activeCard(HttpServletRequest request, HttpServletResponse response) {

		String iccid = request.getParameter("iccid").replace("#", "B");
		String st = request.getParameter("st");
		String deviceid = request.getParameter("deviceid");
		String s = request.getParameter("s");
		String privatekey = request.getParameter("privatekey");
        Date date = new Date();
		JSONObject reqJson = new JSONObject();
		reqJson.put("iccid", iccid);
		reqJson.put("st", st);
		reqJson.put("deviceid", deviceid);
		reqJson.put("s", s);
		reqJson.put("privatekey", privatekey);

		Map<String, Object> resultMap = new HashMap<>();
		LaoPeripheralSysAccessLogDto recordDto = new LaoPeripheralSysAccessLogDto();
		try {
			IccidLibDto iccidLibrary = userService.selectByIccid(iccid);
			Account account = deviceService.authSt(st);
			log.info(">>>>St>>>"+st+"<<<<<<<<<<<<lenovoId>>>>>>>"+account.getUsername());
			LaoUserDto userInfo = userService.getLaoUserDtoByIccid(iccid);
			if (StringUtils.isEmpty(iccid) || StringUtils.isBlank(st) || StringUtils.isBlank(deviceid)
					|| StringUtils.isBlank(s)) {
				resultMap.put("retcode", "-1");// 参数不全
				log.info("参数不全！！！");
			} else if (!s.equalsIgnoreCase(Sha256.sha256(Sha256.sha256(iccid + deviceid + st)))) {
				resultMap.put("retcode", "-5");// 签名错误
				log.info("签名错误！！！iccid=" + iccid);
			} else if (null == iccidLibrary) {
				resultMap.put("retcode", "-7");// 系统异常,iccid库不存在此卡
				log.info("非法iccid");
			} else if ("HwmY9oKEBEjs9r3g72CrJ8m3trHQRJ4w".equals(deviceid)) {
				if (!"MIFI".equals(iccidLibrary.getDevicetype())) {
					resultMap.put("retcode", "-9");// iccid所属设备非mifi设备
					log.info("iccid所属设备非mifi设备");
				}
			} else if ("MIFI".equals(iccidLibrary.getDevicetype())) {
				if (StringUtils.isBlank(privatekey)) {
					resultMap.put("retcode", "-1");// 非法iccid
					log.info("参数不全！！！iccid=" + iccid);
				}
				if (!privatekey.equals(iccidLibrary.getPrivatekey())) {
					resultMap.put("retcode", "-8");// 密钥与iccid不匹配w
					log.info("密钥与iccid不匹配！！iccid=" + iccid + "密钥：" + privatekey);
				}
			} 
			else if(account == null){
				resultMap.put("retcode", "-2");// st校验失败
				log.info("*****");
			}else if(userInfo != null && "0".equals(iccidLibrary.getActived()) && !"2".equals(iccidLibrary.getOperators())){
				resultMap.put("retcode", "2");
				resultMap.put("nacid", "正常");
				log.info(" 已激活 iccid="+iccid);	
			}else {
				String channelCustId = iccidLibrary.getCustid();
				String custId = "";
				if (!deviceService.lenovoIdCheck(account.getAccountID())
						&& (!"".equals(account.getUsername()) || null != account.getUsername())) {
					// 如果此accountid在库中没有 需要 默认添加 数据到 lao_customer lao_ss_account
					// lao_cust_person lao_account_rel
					custId = deviceService.initPersonCustomer(account.getAccountID(),account.getUsername());
					if (null == custId || "".equals(custId)) {
						throw new Exception("生成用户报错");
					}
				}else{
				  Long queryCustId = deviceService.queryCustId(account.getUsername());
				  if(null == queryCustId){
					  custId="90001";
				  }else{
					  custId=queryCustId.toString();  
				  }
				  
				}
				// 激活操作
				Map<String, String> reqInfo = new HashMap<String, String>();
				reqInfo.put("iccid", iccid);
				reqInfo.put("custId", channelCustId);
				reqInfo.put("ifMaintenance", "0");
				reqInfo.put("tradeTypeCode", "110");
				reqInfo.put("personCustId", custId);
				reqInfo.put("accountId", account.getAccountID());
				// 移动卡停开机
				if ("2".equals(iccidLibrary.getOperators())) {
					CardStatusDto queryCardStatus = cardActiveService.queryCardStatus(reqInfo);
					String cardStatus = queryCardStatus.getCardStatus();
					// 2 停机状态
					if ("2".equals(cardStatus)) {
						Map<String, String> hashMap = new HashMap<>();
						hashMap.put("stateCode", "1");
						hashMap.put("iccid", iccid);
						hashMap.put("custId", channelCustId);
						StopOnDto stopOn = cardActiveService.stopOn(hashMap);
						if ("0".equals(stopOn.getIsSuccess())) {
							resultMap.put("retcode", "1");     //1正常
							resultMap.put("iccid", iccid);
							resultMap.put("nacid", "APN2");
							resultMap.put("respDesc",stopOn.getRespDesc());
							recordDto.setRspInfo(stopOn.getRespDesc());
						} else {
							resultMap.put("retcode", "0");     //-11开机异常
							resultMap.put("respDesc",stopOn.getRespDesc());
							recordDto.setRspInfo(stopOn.getRespDesc());
						}
					} else if("1".equals(cardStatus)){
						resultMap.put("retcode", "1"); 
						resultMap.put("iccid", iccid);
						resultMap.put("nacid", "APN2");
						//1正常   
						recordDto.setRspInfo(cardStatus);
					}
				} else {
				  
                    //做加锁操作
					 //做加锁操作
					 if (0==sessionOperService.tryCluLock(KeyPort, iccid, "iccid", 1, 300)) {
						   log.info("加锁失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
						   resultMap.put("retcode", "2");
						   resultMap.put("nacid", "正常");
							log.info("已激活 iccid:"+iccid);
						   return resultMap;
					 }  
					CardActiveResp cardResult = cardActiveService.cardActive(reqInfo);
					log.info("***********cardResult***********" + cardResult);
					// 卡状态查询
					CardStatusDto card = cardActiveService.queryCardStatus(reqInfo);
					String newcardStatus;
					if (card == null || "".equals(card.getCardStatus())) {
						throw new Exception("查询卡状态报错");
					} else {
						// 卡状态转换
						String cardStatus = card.getCardStatus();
						log.info("******激活数据上网服务*****cardStatus***********" + cardStatus);
						newcardStatus = deviceService.cardStatusChange(cardStatus, iccid);
					}
					log.info("****激活数据上网服务*******newcardStatus***********" + newcardStatus);
					resultMap.put("iccid", iccid);
					resultMap.put("nacid", newcardStatus);
					if ("ok".equals(cardResult.getResult()) || "maintenance".equals(cardResult.getResult())) {
						log.info("*********************ifMaintenance**********" + cardResult.getResult());
						resultMap.put("retcode", "1");
						LaoUserDto userInfo1 = userService.getLaoUserDtoByIccid(iccid);
						Long userId = userInfo1.getUserId();

						if (userInfo1!=null) {
							 LaoDeviceRelDto laoDeviceRelDto = deviceService.selectByUserId(userId);
							if (laoDeviceRelDto!=null && laoDeviceRelDto.getUserId().equals(userId)) {
								log.info("***********已绑定过***********" + true);
							}else{
								// 首次激活 lenovoId和iccid绑定
								boolean b =
										deviceService.insertLaoDeviceRel(userInfo1.getUserId().toString(), iccid, deviceid, account.getAccountID(),account.getUsername());
								
								log.info("***********绑定***********" + b);
								
							}
						}
					} else {
						resultMap.put("retcode", "0");
					}
					recordDto.setCustId(Long.valueOf(channelCustId));
				}
				/*
				 * 将个人客户id放入到lao_user表中 20170309 wangxb20
				 */
				LaoUserDto userInfo1 = userService.getLaoUserDtoByIccid(iccid);
				if(!"".equals(custId) && null != userInfo1){
					Long userId = userInfo1.getUserId();
					log.info("*********************custid insert**********"+userId);
					LaoUserDto laoUser = new LaoUserDto();
					laoUser.setUserId(userId);
					laoUser.setCustId(Long.valueOf(custId));
					userService.updateUser(laoUser);
				}

			}
			

		} catch (Exception e) {
			resultMap.put("retcode", "-4");// 系统异常
			log.info("系统异常");
			e.printStackTrace();
			e.printStackTrace();
		}

		if ("1".equals(resultMap.get("retcode"))) {
			recordDto.setIsSuccess("0");
		} else {
			recordDto.setIsSuccess("1");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		recordDto.setErrorCode(resultMap.get("retcode").toString());
		recordDto.setRspInfo(new JSONObject().toJSONString(resultMap));
		recordDto.setIpAddress(request.getRemoteHost());
		recordDto.setUserName(iccid);
		recordDto.setServerName(SERVERNAME);
		recordDto.setAccessDate(date);
		recordDto.setParaName1("device");
		recordDto.setParaName2(sdf.format(new Date()));
		recordDto.setReqInfo(reqJson.toString());
		serverService.savaLogerToDb(recordDto);
		return resultMap;
	}

	private boolean isMobileNO(String mobiles) {

		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

		Matcher m = p.matcher(mobiles);

		System.out.println(m.matches() + "---");

		return m.matches();

	}
}
