package com.urt.service.Goods;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lenovo.pay.utils.security.MD5;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.Trade.TradeFeeSubDto;
import com.urt.dto.unicom.PaymentDto;
import com.urt.interfaces.Goods.PayService;
import com.urt.interfaces.Trade.TradeFeeSubService;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.remain.RemainService;
/**
 * 
 * @author zhaoyf
 *
 */
@Service("payService")
@Transactional(propagation = Propagation.REQUIRED)
public class PayServiceImpl implements PayService {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private UserService userService;
	@Autowired
	private TradeService tradeService;
	@Autowired
	private TradeFeeSubService tradeFeeSubService;
	@Autowired
	private RemainService remainService;
	
	@Value("${charge.pcwebPayRequestUrl}")
	private String pcwebPayRequestUrl;

	@Override
	public Map<String, String> toAliPay(String userId, String userName,
			Double fee, String iccid, String lpsust, String tradeId) {
		// TODO Auto-generated method stub
		log.info("userId--:"+userId+"  userName--:"+userName+"  fee--:"+fee+"  iccid--:"+iccid+"  lpsust--:"+lpsust+"  tradeId"+tradeId);
		Map<String, String> params = null;
		//请求支付
		params = new HashMap<String,String>();
		params.put("payType", "1");//支付宝pcweb支付
		params.put("realm", "gla.lenovo.com");
		params.put("payAmount", fee.toString());
		params.put("stName", lpsust);
		params.put("productName", "联想懂的上网");
		params.put("merchantOrderId", tradeId);
		params.put("userName", userName);
		params.put("userId", userId);
		params.put("privateAttach", pcwebPayRequestUrl);
		return params;
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
	public Map<String, Object> aliPayCallBacktMap(PaymentDto payment)
			throws Exception {
		// TODO Auto-generated method stub
		log.info("payment======查看参数"+payment.toString());
		Map<String,Object> result = new HashMap<String,Object>();
		//支付状态 1 成功 2 失败
		 Integer orderStatus = payment.getOrderStatus();
		//lao充值的订单号
		 String tradeId = payment.getMerchantOrderId();
		//支付方式
		 Integer payType = payment.getPayType();
		//支付订单号
		 String payOrderId = payment.getOrderId();
		 String realm = payment.getRealm();
		 String sign = payment.getSign();
		 
		Map<String, Object> params = getParamsMap(payment);
		log.info("### 充值完成数据回调   ..."+params);
		if(StringUtils.isEmpty(sign) || StringUtils.isEmpty(tradeId) || StringUtils.isEmpty(realm) || orderStatus==null) {
			log.error("param is error");
			result.put("recode", false);
			return result;
		}
		//支付成功,更新状态,请求联通充流量
		//ChargeOrder chargeOrder = chargeOrderService.findChargeOrderByOrderId(merchantOrderId);
		TradeDto tradeDto = tradeService.queryTradeId(Long.parseLong(tradeId));
		if(tradeDto == null){
			log.error("trade is null");
			result.put("recode", false);
			return result;
		}
		if("1".equals(tradeDto.getFeeState())){
			log.error("trade is already success");
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
		
		log.info("chargeOrder.getPayAmount().toString():"+tradeDto.getFee().toString().trim());
		log.info("params.get(\"payAmount\"): "+params.get("payAmount"));
		
		
		log.info("rmbYuan2Fen(chargeOrder.getPayAmount().toString().trim(): "+String.valueOf(rmbYuan2Fen(tradeDto.getFee().toString().trim())));
		log.info("params.get(\"payAmount\").toString().trim(): "+params.get("payAmount").toString().trim());
		
		log.info("比较payamount: "+ String.valueOf(rmbYuan2Fen(tradeDto.getFee().toString().trim())).equals(params.get("payAmount").toString().trim()));
		
		if(!String.valueOf(rmbYuan2Fen(tradeDto.getFee().toString().trim())).equals(params.get("payAmount").toString().trim())){
			log.error("payAmount验证未通过，为非法订单请求, 此订单请求有中途篡改数据的嫌疑。");
			result.put("recode", false);
			return result;
		}else{
			log.error("已经通过payAmount验证,为合法订单");
		}
		
		log.info("支付宝回调 start --- 订单号："+ tradeId);
	
		//chargeOrder.setPaytype(payType);//支付方式
	
		
		if(orderStatus.equals(1)){
			log.info("支付成功 ： 订单号："+tradeId);
/*			tradeDto.setFeeState("1");//收费标志
			tradeDto.setFeeTime(new Date());//收费时间
			int updateTrade = tradeService.updateTrade(tradeDto);
			
*/		
			int changeStatus = tradeService.changeStatus(tradeId, payment);//更新trade表状态
			int changePayTag = tradeFeeSubService.changePayTag(tradeId, payment);//更新算费表状态
			if("1".equals(String.valueOf(changeStatus)) && "1".equals(String.valueOf(changePayTag))){
/*				TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
				tradeFeeSubDto.setPayTag("1");//0:未收费 1：已收费
				tradeFeeSubService.updateTradeFeeSub(tradeFeeSubDto);*/
				TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
				
				
				HashMap<String,Object> paraMap = new HashMap<String,Object>();
				paraMap.put("channelCustId", tradeDto.getChannalCustId());//渠道客户ID
				paraMap.put("tradeTypeCode", "120");//业务类型编码
				paraMap.put("recvFee", rmbYuan2Fen(tradeFeeSubDto.getFee()));//缴费金额
				paraMap.put("fee", rmbYuan2Fen(tradeFeeSubDto.getOldfee()));//应收费用
				paraMap.put("discntFee", rmbYuan2Fen(tradeFeeSubDto.getFee()));//优惠后费用
				paraMap.put("realFee", rmbYuan2Fen(tradeFeeSubDto.getFee()));//实收费用
				paraMap.put("payTag", "1");//0：未缴费 1：用户已缴费 2：客户已缴费
				paraMap.put("userId", tradeDto.getUserId());//用户
				paraMap.put("tradeId", tradeDto.getTradeId());//tradeId
				paraMap.put("goodsId", tradeDto.getGoodsId());//goodsId
				paraMap.put("tradeId", tradeDto.getTradeId());//goodsId
				
				
				
				remainService.charge(paraMap);//扣费
				String userArchiving = userService.userArchiving(tradeId);// 用户归档
				if("ok".equals(userArchiving)){
					//回调成功后，返回给通用收银台success
					result.put("recode", true);
					result.put("iccid", tradeDto.getIccid());
//					result.put("flowSize", chargeOrder.getFlowsize());
					result.put("payAmount", tradeDto.getFee());
					return result;
				}
			}
			
		}else{
			//支付失败
			log.info("支付失败 ： 订单号："+tradeId);
			TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
			tradeFeeSubDto.setPayOrderId(payment.getOrderId());//订单号
			tradeFeeSubDto.setPayType(payment.getPayType().toString());//1：支付宝
			tradeFeeSubDto.setPayDate(new Date());//收费日期

//			chargeOrder.setPaystatus(2);//支付失败
//			chargeOrder.setUpdatedate(new Date());
//			chargeOrderService.updateChargeOrder(chargeOrder);
			result.put("recode", false);
			return result;
		}
		

		return result;
	}

	
	

	


	


}
