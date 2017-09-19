package com.urt.Ability.http.device;


import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lenovo.pay.utils.HttpResponseResult;
import com.lenovo.pay.utils.HttpUtil;
import com.lenovo.pay.utils.security.MD5;
import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.LaoBatchDatadetailDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.Trade.TradeFeeSubDto;
import com.urt.dto.unicom.PayBackDto;
import com.urt.interfaces.Trade.TradeFeeSubService;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.http.device.DeviceProductService;
import com.urt.interfaces.remain.RemainService;
import com.urt.mapper.TradeFeeSubMapper;
import com.urt.mapper.TradeMapper;
import com.urt.mapper.ext.DeviceProductPoExtMapper;
import com.urt.mapper.ext.TradeExtMapper;
import com.urt.mapper.ext.TradeFeeSubExtMapper;
import com.urt.po.DeviceProductPo;
import com.urt.po.LaoBatchDatadetailPo;
import com.urt.po.Trade;
import com.urt.po.TradeFeeSub;
import com.urt.po.TradeGoods;
import com.urt.utils.HttpClientUtil;

@Service("deviceProductService")
public class DeviceServiceImpl implements DeviceProductService{
	
	private static final Logger log=Logger.getLogger(DeviceServiceImpl.class);
	private static final String DIGESTBYMD5KEY="dmksedfol41458p48sljqklfdjv39y8d";//MD5安全key
	
	@Autowired
	private DeviceProductPoExtMapper deviceExtDao;
	@Autowired
	private TradeFeeSubExtMapper tradeFeeDao;
	@Autowired
	private TradeExtMapper tradeExtDao;
	@Autowired
	private TradeService tradeService;
	@Autowired
	private TradeExtMapper tradeExtMapper;
	@Autowired
	private TradeMapper tradeMapper;
	@Autowired
	private TradeFeeSubExtMapper tradeFeeSubExtMapper;
	@Autowired
	private TradeFeeSubMapper tradeFeeSubMapper;
	@Autowired
	private TradeFeeSubService tradeFeeSubService;
	@Autowired
	private RemainService remainService;
	@Autowired
	private UserService userService;
	@Override
	public List<Map<String,Object>> selectShowAds(String tag) {
		log.info("enter the method selectShowAds");
		List<Map<String,Object>> info=new ArrayList<Map<String,Object>>();
		List<DeviceProductPo> ads = deviceExtDao.selectShowAds(tag);
		if(ads!=null&&ads.size()>0){
			for(DeviceProductPo deviceAd:ads){
				Map<String,Object> infomap=new HashMap<String, Object>();
				infomap.put("imgUrl", deviceAd.getImgurl());
				//infomap.put("adPrice", deviceAd.getAdprice());
				infomap.put("adName", deviceAd.getAdname());
				infomap.put("adLinkUrl", deviceAd.getAdlinkurl());
				infomap.put("adLintroduce", deviceAd.getAdlintroduce());
				info.add(infomap);
			}
		}
		log.info("exit the method selectShowAds");
		return info;
	}
	@Override
	public JSONObject selectTradeInfoByTradeId(Long tradeId) {
		log.info("enter the method selectTradeInfoByTradeId");
		JSONObject respJson=new JSONObject();
		Map<String, String> tradeFeeSubMap = tradeFeeDao.selectTradeInfoByTradeId(tradeId);
		if(tradeFeeSubMap!=null){
			respJson.put("paymentMoney", tradeFeeSubMap.get("FEE"));
			respJson.put("paymentTime", tradeFeeSubMap.get("PAYDATE"));
			respJson.put("goodsName", tradeFeeSubMap.get("GOODSNAME"));
			respJson.put("payTag", tradeFeeSubMap.get("PAYTAG"));
		}
		log.info("exit the method selectTradeInfoByTradeId");
		return respJson;
	}
	@Override
	public Map<String, Object> selectPayDetailByIccid(int pageSize,
			int pageNo, TradeDto tradeDto) {
		log.info("enter the method selectPayDetailByIccid");
		Page<TradeFeeSub> page = new Page<TradeFeeSub>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (Trade) ConversionUtil.dto2po(tradeDto, Trade.class));
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setParams(params);
		List<TradeFeeSub> detailList = tradeExtDao.selectPayDetailByIccid(page);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", ConversionUtil.poList2dtoList(detailList, TradeFeeSubDto.class));
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
		log.info("exit the method selectPayDetailByIccid");
		return resultMap;
	}
	@Override
	public Map<String, Object> payCallBacktMap(PayBackDto backDto) throws Exception {
		log.info("backDto======查看参数"+backDto.toString());
		Map<String,Object> result = new HashMap<String,Object>();
		//支付状态 0、初始化1 成功 2 失败
		 Integer orderStatus = backDto.getOrderStatus();
		//lao充值的订单号
		 String tradeId = backDto.getMerchantOrderId();
		//支付方式
		 Integer payType = backDto.getPayType();
		 String sign = backDto.getSign();
		 if(StringUtils.isEmpty(sign) || StringUtils.isEmpty(tradeId) || orderStatus==null) {
				log.error("param is error");
				result.put("recode", false);
				return result;
			}
		 String params=getStringParam(backDto);
		 log.info("### 充值完成数据回调   ..."+params);
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
		 String originalSign = MD5Encoder(params, backDto.get_input_charset());
		 log.info("sysOriginal: " + originalSign);
		 if(!originalSign.equalsIgnoreCase(sign)) {
				log.error("sign is error");
				result.put("recode", false);
				return result;
			}
		 if(!String.valueOf(rmbYuan2Fen(tradeDto.getFee().toString().trim())).equals(backDto.getPayAmount()+"")){
				log.error("payAmount验证未通过，为非法订单请求, 此订单请求有中途篡改数据的嫌疑。");
				result.put("recode", false);
				return result;
			}else{
				log.error("已经通过payAmount验证,为合法订单");
			}
		 log.info("支付宝回调 start --- 订单号："+ tradeId);
		 
		 Trade trade = tradeExtMapper.selectByTradeId(Long.valueOf(tradeId));
		 TradeFeeSub tradeFeeSub = tradeFeeSubExtMapper.queryTradeFeeSubByTradeId(Long.valueOf(tradeId));
		 if(orderStatus.equals(1)){
				log.info("支付成功 ： 订单号："+tradeId);
				//更新trade表状态
				trade.setFeeState("1");//收费标志 0：未收费 1：已收费
				trade.setFeeTime(new Date());
				trade.setFinishDate(new Date());
				int changeStatus = tradeMapper.updateByPrimaryKey(trade);
				
				//更新算费表状态
				tradeFeeSub.setPayTag("1");//0:未收费  1：已收费
				tradeFeeSub.setPayOrderId(backDto.getTradeNo());//订单号
				tradeFeeSub.setPayType(String.valueOf(backDto.getPayType()));//1：支付宝
				tradeFeeSub.setPayDate(new Date());//收费日期
				int changePayTag = tradeFeeSubMapper.updateByPrimaryKey(tradeFeeSub);
				if("1".equals(String.valueOf(changeStatus)) && "1".equals(String.valueOf(changePayTag))){
					TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
					HashMap<String,Object> paraMap = new HashMap<String,Object>();
					paraMap.put("channelCustId", tradeDto.getChannalCustId());//渠道客户ID
					paraMap.put("tradeTypeCode", "120");//业务类型编码
					paraMap.put("recvFee", rmbYuan2Fen(tradeFeeSubDto.getFee()));//缴费金额
					paraMap.put("fee", rmbYuan2Fen(tradeFeeSubDto.getOldfee()));//应收费用
					paraMap.put("discntFee", rmbYuan2Fen(tradeFeeSubDto.getFee()));//优惠后费用
					paraMap.put("realFee", rmbYuan2Fen(tradeFeeSubDto.getFee()));//实收费用
					paraMap.put("payTag", "1");//0：未缴费 1：用户已缴费 2：客户已缴费
					paraMap.put("tradeId", tradeId);
					paraMap.put("goodsId", trade.getGoodsId());
					paraMap.put("userId", trade.getUserId());
					int regs=remainService.charge(paraMap);//扣费 regs=0扣费成功、1扣费失败
					userService.userArchiving(tradeId);// 用户归档
				}
				
			}else{
				//支付失败
				log.info("支付失败 ： 订单号："+tradeId);
				
				//更新trade表状态
				trade.setFeeState("0");//收费标志 0：未收费 1：已收费
				trade.setFeeTime(new Date());
				trade.setFinishDate(new Date());
				tradeMapper.updateByPrimaryKey(trade);
				
				//更新算费表状态
				tradeFeeSub.setPayTag("0");//0:未收费  1：已收费
				tradeFeeSub.setPayOrderId(backDto.getTradeNo());//订单号
				tradeFeeSub.setPayType(String.valueOf(payType));//1：支付宝
				tradeFeeSub.setPayDate(new Date());//收费日期
				tradeFeeSubMapper.updateByPrimaryKey(tradeFeeSub);

				result.put("recode", false);
				return result;
			}
			//回调成功后，返回给通用收银台success
			result.put("recode", true);
			result.put("iccid", tradeDto.getIccid());
//			result.put("flowSize", chargeOrder.getFlowsize());
			result.put("payAmount", tradeDto.getFee());
		 return result;
	}
	private Map<String, Object> getParamsMap(PayBackDto backDto) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("payType", backDto);
		map.put("_input_charset", backDto);
		map.put("payAmount", backDto);
		map.put("merchantOrderId", backDto);
		map.put("tradeNo", backDto);
		map.put("userId", backDto);
		map.put("userName", backDto);
		map.put("privateAttach", backDto);
		map.put("bankOrderid", backDto);
		map.put("orderStatus", backDto);
		map.put("orderTime", backDto);
		return map;
	}
	private String getStringParam(PayBackDto backDto) {
		StringBuffer sb = new StringBuffer();
		Map<String, Object> map=new TreeMap<>();
		map.put("payType", backDto.getPayType());
		map.put("_input_charset", backDto.get_input_charset());
		map.put("payAmount", backDto.getPayAmount());
		map.put("merchantOrderId", backDto.getMerchantOrderId());
		map.put("tradeNo", backDto.getTradeNo());
		map.put("userId", backDto.getUserId());
		map.put("userName", backDto.getUserName());
		map.put("privateAttach", backDto.getPrivateAttach());
		map.put("bankOrderid", backDto.getBankOrderid());
		map.put("orderStatus", backDto.getOrderStatus());
		map.put("orderTime", backDto.getOrderTime());
		Set<Entry<String, Object>> entrySet = map.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			sb.append(entry.getKey() + "=" + entry.getValue() + "&");
		}
		StringBuffer append = sb.append("key"+"="+DIGESTBYMD5KEY);
		String string2 = append.toString();
		return string2;
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
	@Override
	public Map<String, Object> payCallBack(PayBackDto backDto) {
		return null;
	}
	/**
	 * Utf-8 MD5加密
	 * @param s
	 * @param charset
	 * @return
	 */
	public static String MD5Encoder(String s, String charset) {
		try {
			byte[] btInput = s.getBytes(charset);
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < md.length; i++) {
				int val = ((int) md[i]) & 0xff;
				if (val < 16) {
					sb.append("0");
				}
				sb.append(Integer.toHexString(val));
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}
	public static String dataHanding(String price){
		String[] split = price.split("\\.");
		String s=split[0]+split[1];
		Long regs = Long.parseLong(s);
//		Integer parseInt = Integer.parseInt(s);
		String string = regs.toString();
		return string;
	}

	public static void main(String[] args){
		
		PayBackDto backDto=new PayBackDto();
		backDto.setPayType(8);
		backDto.set_input_charset("utf-8");
		backDto.setSign_type("MD5");
		backDto.setPayAmount(1);
		backDto.setMerchantOrderId("1011712270001735");
		backDto.setTradeNo("1201703160515039874010483966");
		backDto.setUserName("15232921737");
		backDto.setUserId("10079964010");
		backDto.setBankOrderid("2017031621001004140238282869");
		backDto.setOrderTime("20170316171516");
		backDto.setOrderStatus(1);
		backDto.setPrivateAttach("http://vbtest.lenovomm.cn/common/applay_order.xhtml?c=payWeb");
		backDto.setSign("c065413840a3d0e330e91d5e004c5eb8");
		
		String string = backDto.toString();
		System.out.println(string);
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String sendHttpPost = instance.sendHttpPost("http://localhost/back/payBack", backDto.toString());
		// sendPost =("http://localhost/back/payBack", backDto.toString());
		System.out.println(sendHttpPost);
		
	}
}
