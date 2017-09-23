package com.urt.Ability.http.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.urt.Ability.collect.UserQueryCardStatus;
import com.urt.Ability.http.util.Constants;
import com.urt.Ability.http.util.ToolsUtil;
import com.urt.common.util.JsonUtil;
import com.urt.dto.CardStatusDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoSsResourceDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.Trade.TradeFeeSubDto;
import com.urt.dto.http.CardActiveResp;
import com.urt.dto.http.StopOnDto;
import com.urt.dto.traffic.TrafficQueryNowDto;
import com.urt.dto.unicom.PaymentDto;
import com.urt.interfaces.Trade.TradeFeeSubService;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.http.CardActiveService;
import com.urt.interfaces.remain.RemainService;
import com.urt.mapper.GoodsReleaseMapper;
import com.urt.mapper.OperatorsMapper;
import com.urt.mapper.ServiceStatusMapper;
import com.urt.mapper.ext.GoodsReleaseExtMapper;
import com.urt.mapper.ext.IccidLibExtMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.modules.nosql.redis.JedisClusterFactory;
import com.urt.po.GoodsRelease;
import com.urt.po.IccidLib;
import com.urt.po.LaoUser;
import com.urt.po.Operators;
import com.urt.po.ServiceStatus;
import com.urt.service.util.WeixinUtil;
import com.urt.utils.SpringContextUtils;

import net.sf.json.JSONArray;
import redis.clients.jedis.JedisCluster;


/**
 * 
 * @author wangxb20
 * 激活/停机开/状态查询
 */

@Service("cardActiveService")
public class CardActiveServiceImpl implements CardActiveService {
	/**日志****/
	protected static final Logger logger = Logger.getLogger(CardActiveServiceImpl.class);
	@Autowired
	TradeService tradeService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	LaoUserExtMapper laoUserDao;
	
	@Autowired
	OperatorsMapper operatorsDao;
	
	@Autowired
	IccidLibExtMapper iccidLibExtMapper;
	@Autowired
	ServiceStatusMapper serviceStatus;
	@Autowired
	GoodsReleaseExtMapper goodsReleaseExtMapper;
	@Autowired
	GoodsReleaseMapper goodsReleaseMapper;
	
	
	@Autowired
	private TradeFeeSubService  tradeFeeSubService;

	@Autowired
	private RemainService remainService;
	
	@Autowired
	protected JedisClusterFactory jedisCluster;
	@Value("${flowQueryRedisTime}")
	private  String flowQueryRedisTime;
	/*
	 * (non-Javadoc)
	 * @see com.lenovo.LAOAPI.interfaces.http.CardActiveService#cardActive(java.util.Map)、
	 * 激活接口
	 */
	@Override
	public CardActiveResp cardActive(Map<String, String> reqInfo) {	
		CardActiveResp card = new CardActiveResp();
		logger.info("***********************cardActive*******************");
		String custId = reqInfo.get(Constants.CustID);
		String orderId = null;	
		String iccid = reqInfo.get(Constants.Iccid);
		String ifMaintenance = reqInfo.get("ifMaintenance");
		String personCustId = reqInfo.get("personCustId");
		String accountId = reqInfo.get("accountId");
		/*
		 * 增加校验操作
		 */
		IccidLib iccidLib = iccidLibExtMapper.selectByIccid(iccid);
		if(null == iccidLib){
			card.setResult("faild");
			card.setRespCode("9999");
			card.setRespDesc("ICCID NOT EXIST");
			return card;
		}
		if(ToolsUtil.checkStringIsNull(iccidLib.getInitproduct())){
			card.setResult("faild");
			card.setRespCode("9999");
			card.setRespDesc("GOODS NOT EXIST");
			return card;
		}
		if("0".equals(iccidLib.getActived())){
			card.setResult("ok");
			card.setRespCode("0000");
			card.setRespDesc("ALREADY ACTIVE");
			return card;
		}	
		String goodsId = iccidLibExtMapper.selectByIccid(iccid).getInitproduct();
		GoodsRelease goodsRelease = goodsReleaseMapper.selectByPrimaryKey(Integer.valueOf(goodsId));
		logger.info("***********************goodsRelease*******************"+goodsRelease.getGoodsId());
		String tradeId= null;
		String result = null;
		String tradeTypeCode = "100";
		if(!ToolsUtil.checkStringIsNull(reqInfo.get("tradeTypeCode"))){
			tradeTypeCode = reqInfo.get("tradeTypeCode");
		}
		try {
			LaoSsAccountDto ss = new LaoSsAccountDto();
			LaoSsResourceDto res = new LaoSsResourceDto();
			res.setResCode("2");
			if(StringUtils.isBlank(personCustId)){
				personCustId=custId;
			}
			ss.setCustId(Long.valueOf(personCustId));
			if(StringUtils.isNotBlank(accountId)){
				ss.setAcconutId(Long.valueOf(accountId));
			}else{
				ss.setAcconutId(Long.valueOf(personCustId));
			}
			//ss.setCustId(custId);
			List<LaoSsResourceDto> ListRes = new ArrayList<LaoSsResourceDto>();
			ListRes.add(res);
			ss.setResources(ListRes);
			// 调用一个方法得到tradeId	
			tradeId = tradeService.addTrade(ss,custId,orderId,iccid,String.valueOf(goodsRelease.getGoodsId()),goodsId,tradeTypeCode,ifMaintenance);
			if(orderId == null) 
			orderId = tradeId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (StringUtils.isBlank(tradeId)) {
			card.setResult("faild");
			card.setRespCode("9999");
			card.setRespDesc("TradeId is null");
			logger.info("用户归档 中的tradeId  null*******************************************");
		} else {
			int resul = getRemain(tradeId,goodsRelease,custId,tradeTypeCode);
			if(0==resul){
				result = userService.userArchiving(tradeId);
			}else{
				card.setRespCode("1055");
				card.setRespDesc("扣费失败");
				return card;
			}
			if(ToolsUtil.checkStringIsNull(result)){
				card.setRespCode("9999");
				card.setRespDesc("卡激失败");
			}else{
				if("ok".equals(result)||"maintenance".equals(result)){
					card.setRespCode("0000");
					card.setGoodsId(goodsId);
					card.setTradeId(tradeId);
					card.setRespDesc("卡激活成功");
					card.setResult(result);	
				}else{
					card.setRespCode("9999");
					card.setRespDesc(result);
					card.setResult(result);	
					card.setGoodsId(goodsId);
					card.setTradeId(tradeId);
				}
				
			}	
		}
		return  card;
	}
	
	private int getRemain(String tradeId,GoodsRelease goodsRelease,String custId,String tradeTypeCode){
		TradeDto tradeDto;
		int restFlag= 0;
		try {
			tradeDto = tradeService.queryTradeId(Long.parseLong(tradeId));
			if(goodsRelease.getReleasePrice() != null && Double.parseDouble(goodsRelease.getReleasePrice()) > 0){
				tradeFeeSubService.addTradeFeeSub(tradeDto);//算费
				TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
				tradeDto.setFee(tradeFeeSubDto.getFee());
				tradeService.updateTrade(tradeDto);
				//收费
				PaymentDto payment = new PaymentDto();
				payment.setOrderId(tradeId);//订单号
				payment.setPayType(0);
				tradeFeeSubService.changePayTag(tradeId, payment);
		    }
			// 扣费
			 TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
			 HashMap<String,Object> paraMap = new HashMap<String,Object>();
			 paraMap.put("channelCustId", custId);//渠道客户ID
			 paraMap.put("tradeTypeCode", tradeTypeCode);//业务类型编码
			 paraMap.put("recvFee", WeixinUtil.getMoney(tradeFeeSubDto.getFee()));//缴费金额
			 paraMap.put("fee", WeixinUtil.getMoney(tradeFeeSubDto.getOldfee()));//应收费用
			 paraMap.put("discntFee", WeixinUtil.getMoney(tradeFeeSubDto.getFee()));//优惠后费用
			 paraMap.put("realFee", WeixinUtil.getMoney(tradeFeeSubDto.getFee()));//实收费用
				 paraMap.put("payTag", "0"); //0：未缴费 1：用户已缴费 2：客户已缴费
				 paraMap.put("tradeId", tradeId);
				 paraMap.put("goodsId", goodsRelease.getGoodsId());
				 paraMap.put("userId", tradeDto.getUserId());
			 restFlag = remainService.charge(paraMap);//扣费
		} catch (Exception e1) {
			restFlag = 0;
		}
		return restFlag;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lenovo.LAOAPI.interfaces.http.CardActiveService#stopOn(java.util.Map)
	 * 停开机操作
	 */	
	@Override
	public StopOnDto stopOn (Map<String, String> reqInfo) {	
		StopOnDto card = new StopOnDto();
		logger.info("***********************cardActive*******************");
		String custId = reqInfo.get(Constants.CustID);
		String orderId = null;	
		String iccid = reqInfo.get(Constants.Iccid);
		String stateCode = reqInfo.get("stateCode");
		String goodsId = null;
		String tradeId= null;
		String result = null;
		String tradeTypeCode =null;
		LaoUser iccidLib = laoUserDao.selectByIccid(iccid);
		if(null == iccidLib){
			card.setRespCode("9999");
			card.setRespDesc("ICCID NOT EXIST");
			card.setIsSuccess("1");
			return card;
		}
		if(StringUtils.isBlank(stateCode)){
			card.setRespCode("9999");
			card.setRespDesc("stateCode IS NOT NULL");
			card.setIsSuccess("1");
			return card;
		}
		if("1".equals(stateCode) &&("6".equals(iccidLib.getStateCode()) 
				|| "19".equals(iccidLib.getStateCode()) || "7".equals(iccidLib.getStateCode()))){
			card.setRespCode("0000");
			card.setRespDesc("Card status is already boot");
			card.setIsSuccess("0");
			return card;
		}

		if("0".equals(stateCode) &&("5".equals(iccidLib.getStateCode()) 
				|| "20".equals(iccidLib.getStateCode()) || "8".equals(iccidLib.getStateCode()))){
			card.setRespCode("0000");
			card.setRespDesc("Card status is stopped");
			card.setIsSuccess("0");
			return card;
		}
		if(StringUtils.isNotBlank(stateCode)){
			if("0".equals(stateCode)){
				tradeTypeCode = "130";
			}else if("1".equals(stateCode)){
				tradeTypeCode = "140";
			}
		}
		try {
			
			// 调用一个方法得到tradeId
			tradeId = tradeService.addTrade(null,custId, orderId, iccid,goodsId,"", tradeTypeCode,"0");
			if(orderId == null) 
			orderId = tradeId;
		} catch (Exception e) {
			card.setRespCode("9999");
			card.setRespDesc("停开机失败");
			card.setIsSuccess("1");
			e.printStackTrace();
			return card;
		}
		
		if (StringUtils.isBlank(tradeId)) {
			card.setRespCode("9999");
			card.setRespDesc("TradeId is null");
			card.setIsSuccess("1");
			logger.info("用户归档 中的tradeId  null*******************************************");
		} else {
			try{
				result = userService.userArchiving(tradeId);
				if(ToolsUtil.checkStringIsNull(result)){
					card.setRespCode("9999");
					card.setRespDesc("停开机失败");
					card.setIsSuccess("1");
				}else{
					if("ok".equals(result)){
						card.setRespCode("0000");
						card.setRespDesc("success");
						card.setIsSuccess("0");
					}else{
						card.setRespCode("9999");
						card.setRespDesc(result);
						card.setIsSuccess("1");
					}
					
				}
			}catch(Exception e){
				card.setRespCode("9999");
				card.setRespDesc("停开机失败");
				card.setIsSuccess("1");
			}
		}
		if ("2".equals(iccidLib.getOperatorsId().toString())) {
			try {
				JedisCluster	jedisClust = jedisCluster.getObject();
				jedisClust.del(iccid+"_"+"CardStatusQuery");
			} catch (Exception e) {
				e.printStackTrace();
				return  card;
			}
		}
		return  card;
	}
	
	
	
	/*
	 * 
	 * (non-Javadoc)
	 * @see com.lenovo.LAOAPI.interfaces.http.CardActiveService#queryCardStatus(java.util.Map)
	 * 对外公布的卡状态查询接口（联通、移动、电信）统一接口
	 */
	@Override
	public CardStatusDto queryCardStatus(Map<String, String> reqInfo) {
		CardStatusDto c = null;
		IccidLib laoUser = null;
		String iccid = reqInfo.get("iccid");
	
		IccidLib iccidLib  = iccidLibExtMapper.selectByIccid(iccid);
		if(null == iccidLib){
			c = new CardStatusDto();
			c.setRespCode("9999");
			c.setRespDesc("ICCID NOT EXIST");
			return c;
		}
		if(ToolsUtil.checkStringIsNull(iccid)){
			c = new CardStatusDto();
			c.setRespCode("2000");
			c.setRespDesc("iccid is null");
			return c;
		}else{
			laoUser = iccidLibExtMapper.selectByIccid(iccid);
			
			UserQueryCardStatus serQueryCardStatus = null;
			if(null != laoUser){
				Operators op = null;
				String operatorId = laoUser.getOperators();
				op = operatorsDao.selectByPrimaryKey(Integer.valueOf(operatorId));
				if(null != op){
					serQueryCardStatus = SpringContextUtils.getBean(op.getQueryCardStatus());
					if(null !=serQueryCardStatus){
						if ("2".equals(op.getOperatorsId().toString())) {
							try {
								JedisCluster	jedisClust = jedisCluster.getObject();
								String	 CardStatusQuery = jedisClust.get(iccid+"_"+"CardStatusQuery");
								if(null==CardStatusQuery){
									c = serQueryCardStatus.queryCardStatus(iccid);
									if (null!=c && null!=c.getCardStatus() && ToolsUtil.checkStringIsNull(c.getRespCode())) {
										c.setRespCode("0000");
										c.setRespDesc("success");
										if("2".equals(laoUser.getOperators())){
												String sta = c.getCardStatus().trim();	
												c.setCardStatus(getMobileState().get(sta));
												logger.info("state is :"+c.getCardStatus());
												JSONArray fromObject = JSONArray.fromObject(c);
												jedisClust.set(iccid+"_"+"CardStatusQuery",fromObject.toString());
												//设置count和数据的过期时间 12*60
												jedisClust.expire(iccid+"_"+"CardStatusQuery",Integer.valueOf(flowQueryRedisTime));
										}
									}else{
										c = new CardStatusDto();
										c.setRespCode("9999");
										c.setRespDesc("其他错误");	
										return c;
									}
								}else{
									JSONArray jsonArray = JSONArray.fromObject(CardStatusQuery);
									for (int i = 0; i < jsonArray.size(); i++) {
										net.sf.json.JSONObject jsonObject = jsonArray.getJSONObject(i);
										c = JsonUtil.fromJson(jsonObject.toString(), CardStatusDto.class);

									}
									
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else{
							try{
								c = serQueryCardStatus.queryCardStatus(iccid);
							}catch(Exception e){
								c = new CardStatusDto();
								c.setRespCode("9999");
								c.setRespDesc("其他错误");	
								return c;
							}
						}
						
					}
				}else{
					c = new CardStatusDto();
					c.setRespCode("3000");
					c.setRespDesc("operators没有配置对应的状态处理类");
				}
				
			}else{
				c = new CardStatusDto();
				c.setRespCode("3000");
				c.setRespDesc("iccid不存在");
				return c;
			}
		}
		if(null !=c && null != c.getCardStatus()){
			if(ToolsUtil.checkStringIsNull(c.getRespCode())){
				c.setRespCode("0000");
				c.setRespDesc("success");
				if("2".equals(laoUser.getOperators())){
						String sta = c.getCardStatus().trim();	
						c.setCardStatus(getMobileState().get(sta));
				}else {
					ServiceStatus card = serviceStatus.selectByPrimaryKey(c.getCardStatus());
		 			if(null != card){
		 				c.setCardStatus(card.getOutsidestate());
					}
				}
				logger.info("state is :"+c.getCardStatus());
			}
		}else{
			c = new CardStatusDto();
			c.setRespCode("9999");
			c.setRespDesc("其它错误");
		}
		return c;
	}
	private Map<String,String> getMobileState(){
		Map<String,String> states = new HashMap<String,String>();
		states.put("00", "1");
		states.put("01", "2");
		states.put("02", "2");
		states.put("07", "3");
		states.put("03", "4");
		states.put("04", "4");
		states.put("05", "5");
		states.put("99", "6");
	 return states;
	}

}
