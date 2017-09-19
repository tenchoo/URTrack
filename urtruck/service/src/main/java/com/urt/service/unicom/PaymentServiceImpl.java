package com.urt.service.unicom;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lenovo.pay.utils.GenerateOrderIdUtil;
import com.lenovo.pay.utils.security.MD5;
import com.urt.Ability.unicom.util.SoapConstant;
import com.urt.dto.FlowConfigDto;
import com.urt.dto.unicom.PaymentDto;
import com.urt.interfaces.unicom.PaymentService;
import com.urt.mapper.IccidLibraryMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.ChargeOrder;
import com.urt.po.FlowConfig;
import com.urt.po.IccidLibrary;
import com.urt.po.UserInfo;
import com.urt.service.util.WeixinPayConstants;
import com.urt.service.util.WeixinUtil;

@Service("paymentService")
@Transactional(propagation = Propagation.REQUIRED)
public class PaymentServiceImpl implements PaymentService {
	Logger log = Logger.getLogger(PaymentServiceImpl.class);
	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private FlowConfigService flowConfigService;

	@Autowired
	private ChargeOrderService chargeOrderService;

	@Autowired
	private TariffPlanService tariffPlanService;

	@Autowired
	private RedisClientService redisClientService;

	@Autowired
	private IccidLibraryService iccidLibraryService;
	
	@Autowired
	IccidLibraryMapper libraryDao;
	
	@Value("${charge.pcwebPayRequestUrl}")
	private String pcwebPayRequestUrl;

	@Override
	public Map<String,Object> aliPayCallBack(PaymentDto payment) {
		Map<String,Object> result = new HashMap<String,Object>();
		//支付状态 1 成功 2 失败
		 Integer orderStatus = payment.getOrderStatus();
		//lao充值的订单号
		 String merchantOrderId = payment.getMerchantOrderId();
		//支付方式
		 Integer payType = payment.getPayType();
		//支付订单号
		 String orderId = payment.getOrderId();
		 String realm = payment.getRealm();
		 String sign = payment.getSign();
		 
		Map<String, Object> params = getParamsMap(payment);
		log.info("### enter into CashierAction page ..."+params);
		if(StringUtils.isEmpty(sign) || StringUtils.isEmpty(orderId) || StringUtils.isEmpty(realm) || orderStatus==null) {
			log.error("param is error");
			result.put("recode", false);
			return result;
		}
		//支付成功,更新状态,请求联通充流量
		ChargeOrder chargeOrder = chargeOrderService.findChargeOrderByOrderId(merchantOrderId);
		
		if(chargeOrder==null){
			log.error("order is null");
			result.put("recode", false);
			return result;
		}
		if(chargeOrder.getPaystatus() ==1){
			log.error("order is already success");
			result.put("recode", false);
			return result;
		}
		log.info("original: " + sign);
		String originalSign = MD5.md5AndSort(params, "NWoyWMINTqgcbZM091btN2L1bmQbEZZs");
		log.info("sysOriginal: " + originalSign);
		if(!originalSign.equalsIgnoreCase(sign)) {
			log.error("sign is error");
			result.put("recode", false);
			return result;
		}
		
		log.info("chargeOrder.getPayAmount().toString():"+chargeOrder.getPayamount().toString().trim());
		log.info("params.get(\"payAmount\"): "+params.get("payAmount"));
		
		
		log.info("rmbYuan2Fen(chargeOrder.getPayAmount().toString().trim(): "+String.valueOf(rmbYuan2Fen(chargeOrder.getPayamount().toString().trim())));
		log.info("params.get(\"payAmount\").toString().trim(): "+params.get("payAmount").toString().trim());
		
		log.info("比较payamount: "+ String.valueOf(rmbYuan2Fen(chargeOrder.getPayamount().toString().trim())).equals(params.get("payAmount").toString().trim()));
		
		if(!String.valueOf(rmbYuan2Fen(chargeOrder.getPayamount().toString().trim())).equals(params.get("payAmount").toString().trim())){
			log.error("payAmount验证未通过，为非法订单请求, 此订单请求有中途篡改数据的嫌疑。");
			result.put("recode", false);
			return result;
		}else{
			log.error("已经通过payAmount验证,为合法订单");
		}
		
		log.info("支付宝回调 start --- 订单号："+ merchantOrderId);
	
		chargeOrder.setPaytype(payType);//支付方式
		if(orderStatus.equals(1)){
			log.info("支付成功 ： 订单号："+merchantOrderId);
			chargeOrder.setPayorderid(orderId);
			chargeOrder.setUpdatedate(new Date());
			chargeOrder.setPaystatus(1);//支付成功
			boolean updateRes = chargeOrderService.updateChargeOrder(chargeOrder);
			
			//查询用户信息
			UserInfo userInfo = userInfoService.getUserInfoByIccid(chargeOrder.getIccid());
			
			if(updateRes){
				log.info("充值开始,iccid : "+chargeOrder.getIccid());
				//得到iccid卡的信息
				IccidLibrary iccidLibrary = iccidLibraryService.findIccidLibraryByIccid(chargeOrder.getIccid());

				//删除默认包月计划
				tariffPlanService.removeDefaultRatePlan(chargeOrder.getIccid());
				
				if(chargeOrder.getFlowsize().equals("1G")){
					if(Integer.valueOf("1").equals(userInfo.getFirstcharge())){
						log.info("充值开始--首充-->充1G 送1G----,iccid : "+chargeOrder.getIccid()+"测试先送10m");
						tariffPlanService.addRatePlan(chargeOrder.getIccid(), chargeOrder.getUserid(), TariffPlanService.RATEPLAN_5M);
						tariffPlanService.addRatePlan(chargeOrder.getIccid(), chargeOrder.getUserid(), TariffPlanService.RATEPLAN_5M);
						
/*						log.info("充值开始--首充-->充1G 送1G----,iccid : "+chargeOrder.getIccid());
						tariffPlanService.addRatePlan(chargeOrder.getIccid(), chargeOrder.getUserId(), TariffPlanService.RATEPLAN_1G);
						tariffPlanService.addRatePlan(chargeOrder.getIccid(), chargeOrder.getUserId(), TariffPlanService.RATEPLAN_1G);
*/						//更新首充状态
						userInfo.setFirstcharge(-1);
						userInfo.setModifydate(new Date());				
						userInfoService.updateUserActiveStatus(userInfo.getIccid(), userInfo.getUserid(), userInfo);
						log.info("更新首充状态为-1结束");
						
					}else{
						log.info("充值开始--非首充-->充1G 送1G----,iccid : "+chargeOrder.getIccid()+"测试先送10m");
						tariffPlanService.addRatePlan(chargeOrder.getIccid(), chargeOrder.getUserid(), TariffPlanService.RATEPLAN_5M);
						tariffPlanService.addRatePlan(chargeOrder.getIccid(), chargeOrder.getUserid(), TariffPlanService.RATEPLAN_5M);
/*						log.info("充值开始--非首充-->充1G 送1G----,iccid : "+chargeOrder.getIccid());
						tariffPlanService.addRatePlan(chargeOrder.getIccid(), chargeOrder.getUserId(), TariffPlanService.RATEPLAN_1G);
						tariffPlanService.addRatePlan(chargeOrder.getIccid(), chargeOrder.getUserId(), TariffPlanService.RATEPLAN_1G);
*/					}
					
				}else if(chargeOrder.getFlowsize().equals("10G")){
					if(Integer.valueOf("1").equals(userInfo.getFirstcharge())){
						log.info("充值开始 -首充-->充10G--送10G ,iccid : "+chargeOrder.getIccid() + "测试先送500M");
						tariffPlanService.addRatePlan(chargeOrder.getIccid(), chargeOrder.getUserid(), TariffPlanService.RATEPLAN_500M);
						
/*						log.info("充值开始 -首充-->充10G ,iccid : "+chargeOrder.getIccid());
						tariffPlanService.addRatePlan(chargeOrder.getIccid(), chargeOrder.getUserId(), TariffPlanService.RATEPLAN_10G);
*/						
						//更新首充状态
						userInfo.setFirstcharge(-1);
						userInfo.setModifydate(new Date());				
						userInfoService.updateUserActiveStatus(userInfo.getIccid(), userInfo.getUserid(), userInfo);
						log.info("更新首充状态为-1结束");
						
					}else{
						log.info("充值开始 -非首充-->充10G ,iccid : "+chargeOrder.getIccid() + "测试先送500M");
						tariffPlanService.addRatePlan(chargeOrder.getIccid(), chargeOrder.getUserid(), TariffPlanService.RATEPLAN_500M);
/*						log.info("充值开始 -非首充-->充10G ,iccid : "+chargeOrder.getIccid());
						tariffPlanService.addRatePlan(chargeOrder.getIccid(), chargeOrder.getUserId(), TariffPlanService.RATEPLAN_10G);
*/					}
					
				}else if(chargeOrder.getFlowsize().equals("3个4G")){
					//首充活动					
					if(Integer.valueOf("1").equals(userInfo.getFirstcharge()) && "Miix".equals(iccidLibrary.getCardType())){
					log.info("充值开始 -首充-->充12G ,iccid : "+chargeOrder.getIccid() + "测试先赠送15M流量");
					tariffPlanService.addRatePlan(chargeOrder.getIccid(), chargeOrder.getUserid(), TariffPlanService.RATEPLAN_5M);
					tariffPlanService.addRatePlan(chargeOrder.getIccid(), chargeOrder.getUserid(), TariffPlanService.RATEPLAN_5M);
					tariffPlanService.addRatePlan(chargeOrder.getIccid(), chargeOrder.getUserid(), TariffPlanService.RATEPLAN_5M);

//					log.info("充值开始 -首充-->充12G ,iccid : "+chargeOrder.getIccid());
//					tariffPlanService.addRatePlan(chargeOrder.getIccid(), chargeOrder.getUserId(), TariffPlanService.RATEPLAN_4G);
//					tariffPlanService.addRatePlan(chargeOrder.getIccid(), chargeOrder.getUserId(), TariffPlanService.RATEPLAN_4G);
//					tariffPlanService.addRatePlan(chargeOrder.getIccid(), chargeOrder.getUserId(), TariffPlanService.RATEPLAN_4G);
					
					//更新首充状态
					userInfo.setFirstcharge(-1);
					userInfo.setModifydate(new Date());				
					userInfoService.updateUserActiveStatus(userInfo.getIccid(), userInfo.getUserid(), userInfo);
					log.info("更新首充状态为-1结束");
					
				}
			}
				//队列尾部追加默认包月计划
				tariffPlanService.addDefaultRatePlan(chargeOrder.getIccid(), chargeOrder.getUserid());
				log.info("充值结束,iccid : "+chargeOrder.getIccid());
				
				//充值结束  把卡切换到apn2,并清除缓存
			
				//查询剩余流量
				Map<String, String> map_ratePlan = tariffPlanService.getRatePlan(chargeOrder.getIccid(), chargeOrder.getUserid());
				
				//如果卡为已激活，并且(状态为apn1或者流量为0),则进行通信计划切换
				if( userInfo.getUserstatus() == UserInfo.UserInfoStatus.ACTIVE.getCodeValue() && ("apn1".equals(userInfo.getApntype())  || "0.000".equals(map_ratePlan.get("dataRemaining")))){
					log.info("切换通信计划-----开始-----------");
					boolean changeResult = tariffPlanService.openInternet(chargeOrder.getIccid(), chargeOrder.getUserid());
					if(changeResult){
						log.info("切换通信计划----成功-- 由于apn1切换到apn2");
					}else{
						log.info("切换通信计划----失败--进行再次切换 ");
						boolean changeAgainRes =  tariffPlanService.openInternet(chargeOrder.getIccid(), chargeOrder.getUserid());
						if(changeAgainRes){
							log.info("---再次切换成功--");
						}else{
							log.info("---再次切换失败！！！--");
						}
					}
				}
				
				//更新订单状态
				chargeOrder.setChargestatus(1);//充值成功
				chargeOrder.setChargedate(new Date());
				chargeOrder.setUpdatedate(new Date());
				chargeOrderService.updateChargeOrder(chargeOrder);
				//清除缓存
				redisClientService.del(chargeOrder.getIccid() + "-" + SoapConstant.NOTITY_TYPE_FLOW);
			}
			
		}else{
			//支付失败
			log.info("支付失败 ： 订单号："+merchantOrderId);
			chargeOrder.setPaystatus(2);//支付失败
			chargeOrder.setUpdatedate(new Date());
			chargeOrderService.updateChargeOrder(chargeOrder);
			result.put("recode", false);
			return result;
		}
		//回调成功后，返回给通用收银台success
		result.put("recode", true);
		result.put("iccid", chargeOrder.getIccid());
		result.put("flowSize", chargeOrder.getFlowsize());
		result.put("payAmount", chargeOrder.getPayamount());
		return result;
	}

	@Override
	public Map<String, Object> getPaymentInfo(String userId) {
		Map<String, Object> resultMap = new HashMap<>();

		List<UserInfo> userInfoList = userInfoService
				.getUserInfosByUserid(userId);

		List<FlowConfig> flowConfigList = flowConfigService
				.findFlowConfigList();

		resultMap.put("userInfoList", userInfoList);
		resultMap.put("flowConfigList", flowConfigList);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getPaymentMsg(String userId) {
		Map<String, Object> resultMap = new HashMap<>();

		List<UserInfo> userInfoList = userInfoService.getUserInfosByUserid(userId);

		List<FlowConfig> flowConfigList = flowConfigService.findFlowConfigList();
		
		List<FlowConfigDto> list = null;
		if(flowConfigList != null && flowConfigList.size() > 0){
			list = new ArrayList<FlowConfigDto>();
			for (FlowConfig flowConfig : flowConfigList) {
				FlowConfigDto dto = new FlowConfigDto();
				BeanMapper.copy(flowConfig, dto);
				list.add(dto);
			}
		}

		resultMap.put("userInfoList", userInfoList);
		resultMap.put("flowConfigList", list);
		return resultMap;
	}

	/**
	 * 人民币元转换成分
	 * 
	 * @param yuan
	 * @return
	 */
	public static Long rmbYuan2Fen(String yuan) {
		BigDecimal b1 = new BigDecimal(yuan);
		BigDecimal b2 = new BigDecimal(100);
		BigDecimal ret = b1.multiply(b2);
		return ret.longValue();
	}


	private Map<String, Object> getParamsMap(PaymentDto payment) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bankOrderId", payment.getBankOrderId());
		params.put("lenovoId", payment.getLenovoId());
		params.put("merchantOrderId", payment.getMerchantOrderId());
		params.put("orderId", payment.getOrderId());
		params.put("orderStatus", payment.getOrderStatus());
		params.put("payType", payment.getPayType());
		params.put("orderTime", payment.getOrderTime());
		params.put("realm", payment.getRealm());
		// 因为收银台返回的payAmount为lang类型
		params.put("payAmount", payment.getPayAmount().longValue());
		params.put("privateAttach", payment.getPrivateAttach());
		return params;
	}

	@Override
	public Map<String, String> toAliPay(String userId, String userName, String flowSize, Double payAmount, String iccid, String lpsust) {
		Map<String, String> params = null;
		 //校验流量订单是否合法
		 FlowConfig flowConfig = flowConfigService.findFlowConfigByParam(flowSize, payAmount+"");
		 //非法订单
		 if(null == flowConfig){
			 log.info("非法订单请求-------iccid = "+iccid);
			 return params;
		 }

		String orderId = GenerateOrderIdUtil.generateCommonOrderNo("L", userId);
		//创建订单
		ChargeOrder order = new ChargeOrder();
		order.setOrderid(orderId);
		order.setIccid(iccid);
		order.setUserid(userId);
		order.setUsername(userName);
		order.setFlowsize(flowSize);
		order.setPayamount(payAmount);
		order.setCreatedate(new Date());
		boolean addResult = chargeOrderService.addChargeOrder(order);
		
		if(addResult){
			//请求支付
			params = new HashMap<String,String>();
			params.put("payType", "1");//支付宝pcweb支付
			params.put("realm", "gla.lenovo.com");
			params.put("payAmount", payAmount.toString());
			params.put("stName", lpsust);
			params.put("productName", "联想懂的上网");
			params.put("merchantOrderId", orderId);
			params.put("userName", userName);
			params.put("userId", userId);
			params.put("privateAttach", pcwebPayRequestUrl);
			return params;
		}
		return null;
	}

	@Override
	public Map<String, String> weixinH5Pay(String ip, String openId, String userId, String userName,
			String flowSize, Double payAmount, String iccid, String lpsust) {
		
		Map<String, String> paramMap = new HashMap<String, String>();
		 //校验流量订单是否合法
		 FlowConfig flowConfig = flowConfigService.findFlowConfigByParam(flowSize, payAmount+"");
		 //非法订单
		 if(null == flowConfig){
			 log.info("非法订单请求-------iccid = "+iccid);
			 return null;
		 }

		String orderId = GenerateOrderIdUtil.generateCommonOrderNo("L", userId);
		//创建订单
		ChargeOrder order = new ChargeOrder();
		order.setOrderid(orderId);
		order.setIccid(iccid);
		order.setUserid(userId);
		order.setUsername(userName);
		order.setFlowsize(flowSize);
		order.setPayamount(payAmount);
		order.setCreatedate(new Date());
		chargeOrderService.addChargeOrder(order);
		
		// *******************************封装微信需要的参数
		String totalFee = WeixinUtil.getMoney(payAmount.toString());
		String nonce_str = WeixinUtil.getRandomString(32);
		String prepay_id = null;
		paramMap.put("appid", WeixinPayConstants.appid);
		paramMap.put("body", flowSize);
		paramMap.put("attach", "-1");// 附加数
		paramMap.put("mch_id", WeixinPayConstants.mch_id);
		paramMap.put("nonce_str", nonce_str);
		paramMap.put("notify_url", WeixinPayConstants.weixinPayBackurl);
		paramMap.put("openid", openId);
		paramMap.put("out_trade_no", orderId);
		paramMap.put("spbill_create_ip", ip);
		paramMap.put("total_fee", totalFee);
		paramMap.put("trade_type", WeixinPayConstants.trade_type);
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>微信参数:"+paramMap.toString());
		//*******************************微信签名验证
		String sign = null;
		try {
			sign = WeixinUtil.getPayCustomSign(paramMap,WeixinPayConstants.api_key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		paramMap.put("sign", sign);
		String xml = WeixinUtil.ArrayToXml(paramMap);
		//*******************************微信prepay_id 预支付ID生成
		prepay_id = WeixinUtil.getPrepayId(xml);

		// *******************************向h5界面传递参数，从前台发起微信支付
		String timeStamp = System.currentTimeMillis() + "";
		timeStamp = timeStamp.substring(0, 10);// 微信只要精确到秒
		Map<String, String> payMap = new HashMap<String, String>();
		payMap.put("appId", WeixinPayConstants.appid);
		payMap.put("timeStamp", timeStamp);
		nonce_str = WeixinUtil.getRandomString(32);
		payMap.put("nonceStr", nonce_str);
		payMap.put("signType", "MD5");
		payMap.put("package", "prepay_id=" + prepay_id);
		String paySign = null;
		try {
			paySign = WeixinUtil.getPayCustomSign(payMap,WeixinPayConstants.api_key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		payMap.put("paySign", paySign);
		payMap.put("prepay_id", "prepay_id=" + prepay_id);
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>微信界面参数:"+payMap.toString());
		
		payMap.put("iccid", iccid);
		payMap.put("goodsName", flowSize);
		payMap.put("payAmount", payAmount.toString());
		return payMap;
	}

	@Override
	public boolean ifIccidOfH5(String iccid) {
		IccidLibrary doQueryFirst = libraryDao.doQueryFirst(iccid);
		if(doQueryFirst != null){
			return true;
		}
		return false;
	}

}
