package com.urt.Ability.http.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.Ability.http.util.Constants;
import com.urt.Ability.http.util.ToolsUtil;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoSsResourceDto;
import com.urt.dto.Goods.DiscontDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.Trade.TradeFeeSubDto;
import com.urt.dto.http.CardActiveResp;
import com.urt.dto.http.GoodsIccidDto;
import com.urt.dto.http.GoodsList;
import com.urt.dto.unicom.PaymentDto;
import com.urt.interfaces.Goods.GoodsOrderService;
import com.urt.interfaces.Trade.TradeFeeSubService;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.http.OrderProductService;
import com.urt.interfaces.remain.RemainService;
import com.urt.mapper.GoodsReleaseMapper;
import com.urt.mapper.ext.GoodsReleaseExtMapper;
import com.urt.mapper.ext.IccidLibExtMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.mapper.ext.TradeFeeSubExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.Discont;
import com.urt.po.GoodsElement;
import com.urt.po.GoodsRelease;
import com.urt.po.IccidLib;
import com.urt.po.LaoUser;
import com.urt.po.TradeFeeSub;
import com.urt.service.util.WeixinUtil;

/**
 * @author wangxb20
 *产品订购 /查询
 */
@Service("orderProductService")
public class OrderProductServiceImpl implements OrderProductService{

	/**日志****/
	protected static final Logger logger = Logger.getLogger(OrderProductServiceImpl.class);
	@Autowired
	TradeService tradeService;
	
	@Autowired
	UserService userService;
	@Autowired
	LaoUserExtMapper laoUserDao;
	@Autowired
	IccidLibExtMapper iccidLibExtMapper;
	@Autowired
	private GoodsOrderService goodsOrderService;
	@Autowired
	GoodsReleaseExtMapper goodsReleaseExtMapper;
	@Autowired
	GoodsReleaseMapper goodsReleaseMapper;
	
	@Autowired
	private TradeFeeSubService  tradeFeeSubService;

	@Autowired
	private RemainService remainService;
	/*
	 * (non-Javadoc)
	 * @see com.lenovo.LAOAPI.interfaces.http.OrderProductService#orderProduct(java.util.Map)
	 * <p>产品订购接口</p>
	 */
	@Override
	public CardActiveResp orderProduct(Map<String, String> reqInfo) {
		CardActiveResp card = new CardActiveResp();
		logger.info("***********************orderProduct*******************");
		String custId = reqInfo.get(Constants.CustID);
		logger.info("***********************orderProduct*******************"+custId);
		String orderId = null;	
		String iccid = reqInfo.get(Constants.Iccid);
		String goodsId = reqInfo.get("goodsId");
		String personCustId = reqInfo.get("personCustId");
		String tradeId= null;
		String result = null;
		//String goodsReleaseId = reqInfo.get("goodsReleaseId");
		
		LaoUser iccidLib = laoUserDao.selectByIccid(iccid);
		if(null == iccidLib){
			card.setRespCode("0054");
			card.setRespDesc("ICCID NOT EXIST");
			return card;
		}		
		GoodsRelease goodsRelease = goodsReleaseMapper.selectByPrimaryKey(Integer.valueOf(goodsId));
		if(null == goodsRelease){
			card.setRespCode("7001");
			card.setRespDesc("GOODS NOT EXIST");
			return card;
		}
		logger.info("***********************orderProduct** goodsRelease*****************"+goodsRelease.getGoodsId());
		try {
			LaoSsAccountDto ss = new LaoSsAccountDto();
			LaoSsResourceDto res = new LaoSsResourceDto();
			res.setResCode("2");
			if(StringUtils.isBlank(personCustId)){
				personCustId=custId;
			}
			ss.setCustId(Long.valueOf(personCustId));
			//ss.setCustId(custId);
			List<LaoSsResourceDto> ListRes = new ArrayList<LaoSsResourceDto>();
			ListRes.add(res);
			ss.setResources(ListRes);
			ss.setAcconutId(Long.valueOf(personCustId));
			// 调用一个方法得到tradeId
			tradeId = tradeService.addTrade(ss,custId,orderId, iccid,String.valueOf(goodsRelease.getGoodsId()),goodsId, "120","0");
			card.setTradeId(tradeId);
			if(orderId == null) 
			orderId = tradeId;
		} catch (Exception e) {
			card.setRespCode("9999");
			card.setRespDesc("其它错误");
			e.printStackTrace();
			return card;
		}
		int restFlag =1;
		if (StringUtils.isBlank(tradeId)) {
			card.setRespCode("9999");
			card.setRespDesc("订单没有创建");
			logger.info("用户归档 中的tradeId  null*******************************************");
		} else {
			
			try{
				restFlag= getRemain(tradeId,goodsRelease,custId,"120");
				if(0==restFlag){
					result = userService.userArchiving(tradeId);
				}else{
					card.setRespCode("1055");
					card.setRespDesc("扣费失败");
					return card;
				}
				
			}catch(Exception e){
				card.setRespCode("9999");
				card.setRespDesc("其它错误");
				return card;
			}
			if("ok".equals(result)||"maintenance".equals(result)){
				card.setRespCode("0000");
				card.setRespDesc("流量订购成功");
				card.setGoodsId(goodsId);
				card.setTradeId(tradeId);
			}else{
				card.setRespCode("9999");
				card.setGoodsId(goodsId);
				card.setTradeId(tradeId);
			}
			card.setResult(result);
		}
		return  card;
	}

	
	private int getRemain(String tradeId,GoodsRelease goodsRelease,String custId,String tradeTypeCode){
		TradeDto tradeDto;
		int restFlag= 1;
		try {
			tradeDto = tradeService.queryTradeId(Long.parseLong(tradeId));
			if(null != goodsRelease.getReleasePrice() && Double.parseDouble(goodsRelease.getReleasePrice()) > 0){
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
			 paraMap.put("payTag", "0");//0：未缴费 1：用户已缴费 2：客户已缴费
			 paraMap.put("tradeId", tradeId);
			 paraMap.put("goodsId", goodsRelease.getGoodsId());
			 paraMap.put("userId", tradeDto.getUserId());
			 restFlag = remainService.charge(paraMap);//扣费 0成功  1失败
		} catch (Exception e1) {
			e1.getMessage();
		}
		return restFlag;
	}



	/*
	 * (non-Javadoc)
	 * @see com.lenovo.LAOAPI.interfaces.http.OrderProductService#queryProduct(java.util.Map)
	 * <p>根据iccid来查询可订购的产品</p>
	 */
	public GoodsIccidDto queryProduct(Map<String, String> reqInfo) {
			GoodsIccidDto goodsList = new GoodsIccidDto();
			String iccid = reqInfo.get(Constants.Iccid);
			String custId = reqInfo.get(Constants.CustID);
			LaoUser iccidLib = laoUserDao.selectByIccid(iccid);
			if(null == iccidLib){
				goodsList.setRespCode("0054");
				goodsList.setRespDesc("ICCID NOT EXIST");
				return goodsList;
			}
			IccidLibDto iccidLibDto = userService.selectByIccid(iccid);	
			custId = iccidLibDto.getCustid();
			String operatorsId = iccidLibDto.getOperators();
			String value1 = iccidLibDto.getValue1();
			String value2 = iccidLibDto.getValue2();
			List<GoodsDto> laoGoodsDtos = null;
			try{
				laoGoodsDtos = goodsOrderService.queryLaoGoods(custId,operatorsId,value1,value2);
			}catch(Exception e){
				goodsList.setRespCode("9999");
				goodsList.setRespDesc("其它错误");
				return goodsList;
			}
			List<GoodsList> l = new ArrayList<GoodsList>();
				for(GoodsDto goods :laoGoodsDtos){
					GoodsList goodsIccid = new GoodsList();
					goodsIccid.setGoodsId(goods.getGoodsReleaseId().toString());
					goodsIccid.setGoodsName(goods.getGoodsName());
					goodsIccid.setGoodsPic(goods.getGoodsPic());
					goodsIccid.setGoodsPrices(null==goods.getGoodsPrices()? "0" : goods.getGoodsPrices());
					goodsIccid.setAttrValue1(value1);
					goodsIccid.setAttrValue2(value2);
					l.add(goodsIccid);
				}
				goodsList.setProducts(l);
			if(l.size()>0){
				goodsList.setRespCode("0000");
				goodsList.setRespDesc("success");
			}else{
				goodsList.setRespCode("7001");
				goodsList.setRespDesc("没有找到相应的产品");
			}
		return goodsList;
	}

}
