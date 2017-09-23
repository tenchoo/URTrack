package com.urt.web.http.device;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.urt.dto.CardStatusDto;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoPeripheralSysAccessLogDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.device.Account;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.http.CardActiveService;
import com.urt.interfaces.http.ServerCheckService;
import com.urt.web.common.util.device.Sha256;


@Controller
@RequestMapping("/serviceStatus")
public class ServiceStatusController {

	private static final Logger log = Logger.getLogger(ServiceStatusController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private CardActiveService cardActiveService;
	
	@Autowired
	private ServerCheckService serverService;
	private static final String SERVERNAME="查询服务状态接口";
	
	@RequestMapping(value = "queryStatus", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> queryStatus(HttpServletRequest request, HttpServletResponse response){	
		String iccid = request.getParameter("iccid").replace("#", "B");;
		String st = request.getParameter("st");
		String deviceid = request.getParameter("deviceid");
		String s = request.getParameter("s");
		String privatekey = request.getParameter("privatekey");
		String realname = request.getParameter("realname");
		String idnum = request.getParameter("idnum");
	    Date date=new Date();
		JSONObject reqJson=new JSONObject();
		reqJson.put("iccid", iccid);
		reqJson.put("st", st);
		reqJson.put("deviceid", deviceid);
		reqJson.put("s", s);
		reqJson.put("privatekey", privatekey);
		reqJson.put("realname", realname);
		reqJson.put("idnum", idnum);
		
		Map<String, Object> resultMap = new HashMap<>();
		LaoPeripheralSysAccessLogDto recordDto=new LaoPeripheralSysAccessLogDto();
		try {

			// 查询iccid库，查找对应设备
			IccidLibDto iccidLibrary = userService.selectByIccid(iccid);
			Account account = deviceService.authSt(st);
			log.info(">>>>St>>>"+st);
			log.info("<<<<<<<<<<<<lenovoId>>>>>>>"+account+">>>>>ICCID>>>>>>>"+iccid);
			LaoUserDto userInfo = userService.getLaoUserDtoByIccid(iccid);
			if (StringUtils.isEmpty(iccid) || StringUtils.isBlank(st) || StringUtils.isBlank(deviceid)
					|| StringUtils.isBlank(s)) {
				resultMap.put("retcode", "-1");// 参数不全
				log.info("参数不全！！！");
			}else if(!s.equalsIgnoreCase(Sha256.sha256(Sha256.sha256(iccid + deviceid + st)))){
				resultMap.put("retcode", "-5");// 签名错误
				log.info("签名错误！！！iccid=" + iccid);
			}else if(null == iccidLibrary){
				resultMap.put("retcode", "-7");// 系统异常,iccid库不存在此卡
				log.info("非法iccid");
			}else if("HwmY9oKEBEjs9r3g72CrJ8m3trHQRJ4w".equals(deviceid)){
				if(!"MIFI".equals(iccidLibrary.getDevicetype())){
					resultMap.put("retcode", "-9");// iccid所属设备非mifi设备
					log.info("iccid所属设备非mifi设备");
				}
			}else if("MIFI".equals(iccidLibrary.getDevicetype())){
				if (StringUtils.isBlank(privatekey) || StringUtils.isBlank(realname)
						|| StringUtils.isBlank(idnum)) {
					resultMap.put("retcode", "-1");// 非法iccid
					log.info("参数不全！！！iccid=" + iccid);
				}
				if (!privatekey.equals(iccidLibrary.getPrivatekey())) {
					resultMap.put("retcode", "-8");// 密钥与iccid不匹配w
					log.info("密钥与iccid不匹配！！iccid=" + iccid + "密钥：" + privatekey);
				}
			}else if(account == null){
				resultMap.put("retcode", "-2");// st校验失败
				log.info("*****");
			}else{
				
				 String operators =  iccidLibrary.getOperators();
				 if(null != userInfo && !account.getUsername().equals(deviceService.checkLenovoIccid(userInfo.getUserId(),iccid, deviceid, account.getAccountID(),account.getUsername())) && "1".equals(operators)) {
					log.info("**********card is not bind*********");
					resultMap.put("retcode", "-6");//该卡已被绑定
				 }
				 if("2".equals(operators)){
					 String lenovo = deviceService.checkLenovoIccid(userInfo.getUserId(),iccid, deviceid, account.getAccountID(),account.getUsername());
					 if("".equals(lenovo)){
						 boolean b = deviceService.insertLaoDeviceRel(userInfo.getUserId().toString(), iccid, deviceid, account.getAccountID(),account.getUsername());		
							log.info("***********绑定***********"+b);
					 }else if(null != userInfo && !account.getUsername().equals(deviceService.checkLenovoIccid(userInfo.getUserId(),iccid, deviceid, account.getAccountID(),account.getUsername()))){
						 resultMap.put("retcode", "-6");
					 }
				 }
				 if(!"-6".equals(resultMap.get("retcode"))){
					Map<String,String> reqInfo = new HashMap<String,String>();
					reqInfo.put("iccid", iccid);
					//卡状态查询
					CardStatusDto card = cardActiveService.queryCardStatus(reqInfo);
					String newcardStatus;
					boolean  isActive = true;
					if(card == null || null ==card.getCardStatus()){
						isActive = false;
						newcardStatus = "APN1";
						isActive = false;
						resultMap.put("retcode", "-4");
						//throw new Exception("查询卡状态报错");
					}else{
						//卡状态转换
						String cardStatus = card.getCardStatus();
						log.info("********cardStatus**********"+cardStatus);
						
						String active = iccidLibrary.getActived();
						log.info("*********************active**********"+active);
						if("1".equals(active)){
							isActive = false;
						}else if("2".equals(cardStatus) && "2".equals(operators)){
							isActive= false;
						}
						newcardStatus = deviceService.cardStatusChange(cardStatus, iccid);
						resultMap.put("isActive",isActive);
						resultMap.put("iccid", iccid);
						resultMap.put("nacid", newcardStatus);
						resultMap.put("retcode", "1");// 成功
					}
					recordDto.setCustId(Long.valueOf(iccidLibrary.getCustid()));
				 }
			}
					

		} catch (Exception e) {
			resultMap.put("retcode", "-4");// 系统异常
			log.info("系统异常");
			e.printStackTrace();
		}
		if("1".equals(resultMap.get("retcode"))){
			recordDto.setIsSuccess("0");
		}else{
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
}
