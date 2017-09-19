package com.urt.miniService.authority;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.mapper.DiscontMapper;
import com.urt.mapper.GoodsMapper;
import com.urt.mapper.GoodsReleaseMapper;
import com.urt.mapper.OperatorPlanMapper;
import com.urt.mapper.ServiceStatusMapper;
import com.urt.mapper.TradeDiscontMapper;
import com.urt.mapper.TradeGoodsMapper;
import com.urt.mapper.TradeMapper;
import com.urt.mapper.TradeOperatorPlanMapper;
import com.urt.mapper.TradeSvcStateMapper;
import com.urt.mapper.ext.GoodsElementExtMapper;
import com.urt.mapper.ext.ServiceStatusChgExtMapper;
import com.urt.mapper.ext.TradeGoodsExtMapper;
import com.urt.po.Discont;
import com.urt.po.Goods;
import com.urt.po.GoodsElement;
import com.urt.po.GoodsRelease;
import com.urt.po.OperatorPlan;
import com.urt.po.ServiceStatus;
import com.urt.po.ServiceStatusChg;
import com.urt.po.Trade;
import com.urt.po.TradeDiscont;
import com.urt.po.TradeGoods;
import com.urt.po.TradeOperatorPlan;
import com.urt.po.TradeSvcState;
@Service("miniTradeService")
public class MiniTradeServiceImpl {
	
	@Autowired
	private TradeMapper tradeMapper;
	@Autowired
	private TradeOperatorPlanMapper tradeOperatorPlanMapper;
	@Autowired
	private TradeSvcStateMapper tradeSvcStateMapper;
	@Autowired
	private TradeDiscontMapper tradeDiscontMapper;
	@Autowired
	private TradeGoodsMapper tradeGoodsMapper;
	@Autowired
	private GoodsElementExtMapper goodsElementExtMapper;
	@Autowired
	private OperatorPlanMapper operatorPlanMapper;
	@Autowired
	private DiscontMapper discontMapper;
	@Autowired
	private ServiceStatusChgExtMapper serviceStatusChgExtMapper;
	@Autowired
	private ServiceStatusMapper serviceStatusMapper;
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private GoodsReleaseMapper goodsReleaseMapper;
	@Autowired
	private TradeGoodsExtMapper tradeGoodsExtDAO;

	
	private static Log log = LogFactory.getLog(MiniTradeServiceImpl.class);
	
	/**
	 * @author zhaoyf
	 * @param trade
	 * @throws Exception
	 * 添加台账套餐子表
	 */
	public void addTradeOperatorPlan(Trade trade)throws Exception{
		List<GoodsElement> goodsElementList = goodsElementExtMapper.findGoodsElementByGoodsId(trade.getGoodsId());
		TradeGoods tradeGoods = tradeGoodsExtDAO.selectByTradeId(trade.getTradeId());
		for (GoodsElement goodsElement : goodsElementList) {
			String elementType = goodsElement.getElementType();
			if("1".equals(elementType)){
				TradeOperatorPlan tradeOperatorPlan = new TradeOperatorPlan();
				tradeOperatorPlan.setTradeId(trade.getTradeId());
				tradeOperatorPlan.setAcceptMonth(trade.getAcceptMonth());
				tradeOperatorPlan.setGoodsId(trade.getGoodsId());
				tradeOperatorPlan.setModifyTag("0");//0 增加 1 删除 2 修改
				OperatorPlan operatorPlan = operatorPlanMapper.selectByPrimaryKey(goodsElement.getOriginalId());
				tradeOperatorPlan.setPlanId(goodsElement.getOriginalId());
				tradeOperatorPlan.setOperatorsId(operatorPlan.getOperators());
				tradeOperatorPlan.setOperatorsPid(operatorPlan.getOperatorsPid());
				tradeOperatorPlan.setEnableTag(operatorPlan.getEnableTag());
				tradeOperatorPlan.setInvalidType(operatorPlan.getInvalidType());
				tradeOperatorPlan.setStartDate(goodsElement.getStartDate());
				tradeOperatorPlan.setEndDate(tradeGoods.getEndDate());
				tradeOperatorPlan.setPackageType(goodsElement.getPackageType());
				tradeOperatorPlan.setGoodsIndex(goodsElement.getGoodsIndex());
				tradeOperatorPlan.setPlanType(operatorPlan.getPlanType());
				tradeOperatorPlan.setAcceptDate(trade.getAcceptDate());
				tradeOperatorPlan.setUserId(trade.getUserId());
				tradeOperatorPlan.setPlanClassify(operatorPlan.getPlanClassify());
				tradeOperatorPlanMapper.insert(tradeOperatorPlan);
			}
			
		}
		
	}
	/**
	 * @author zhaoyf
	 * @param trade
	 * @throws Exception
	 * 添加业务服务状态子表
	 */
	public void addTradeSvcState(Trade trade,String tradeTypeCode,String stateCode)throws Exception{
		TradeSvcState tradeSvcState = new TradeSvcState();
		tradeSvcState.setTradeId(trade.getTradeId());
		tradeSvcState.setAcceptMonth(trade.getAcceptMonth());
		tradeSvcState.setUserId(trade.getUserId());	
		ServiceStatusChg serviceStatusChg = serviceStatusChgExtMapper.selectByTradeTypeCode(tradeTypeCode, stateCode);
		if(serviceStatusChg != null && serviceStatusChg.getNewStateCode() != null){
			String newStateCode = serviceStatusChgExtMapper.selectByTradeTypeCode(tradeTypeCode, stateCode).getNewStateCode();
			tradeSvcState.setStateCode(newStateCode);
			ServiceStatus serviceStatus = serviceStatusMapper.selectByPrimaryKey(newStateCode);
			tradeSvcState.setServiceId(serviceStatus.getServiceId());		
			tradeSvcState.setStartDate(serviceStatus.getStartdate());
			tradeSvcState.setEndDate(serviceStatus.getEnddate());
			tradeSvcState.setAcceptDate(new Date());
			tradeSvcState.setModifyTag("0");//0 增加 1 删除 2 修改
			tradeSvcStateMapper.insert(tradeSvcState);
		}else{
			log.info("------没有相关的状态转换！！！");
		}

	}
	
	/**
	 * @author zhaoyf
	 * @param trade
	 * @throws Exception
	 * 添加业务台账优惠子表
	 */
	public void addTradeDiscont(Trade trade)throws Exception{
		List<GoodsElement> goodsElementList = goodsElementExtMapper.findGoodsElementByGoodsId(trade.getGoodsId());
		for (GoodsElement goodsElement : goodsElementList) {
			String elementType = goodsElement.getElementType();
			if("0".equals(elementType)){
				TradeDiscont tradeDiscont = new TradeDiscont();
				tradeDiscont.setTradeId(trade.getTradeId());
				tradeDiscont.setAcceptMonth(trade.getAcceptMonth());
				tradeDiscont.setUserId(trade.getUserId());
				tradeDiscont.setGoodsId(trade.getGoodsId());
				tradeDiscont.setDiscntId(goodsElement.getOriginalId());
				Discont discont = discontMapper.selectByPrimaryKey(goodsElement.getOriginalId());
				tradeDiscont.setStartDate(discont.getStartdate());
				tradeDiscont.setEndDate(discont.getEnddate());
				tradeDiscont.setModifyTag("0");//0 增加 1 删除 2 修改
				tradeDiscont.setAcceptDate(trade.getAcceptDate());
				tradeDiscontMapper.insert(tradeDiscont);
			}
		}
	}
	
	/**
	 * @author zhaoyf
	 * @param trade
	 * @throws Exception
	 * 添加台账商品子表
	 */
	public void addTradeGoods(Trade trade)throws Exception{
		GoodsRelease goodsRelease = goodsReleaseMapper.selectByPrimaryKey(trade.getGoodsReleaseId());
		Goods goods = goodsMapper.selectByPrimaryKey(trade.getGoodsId());
		TradeGoods tradeGoods = new TradeGoods();
		tradeGoods.setTradeId(trade.getTradeId());
		tradeGoods.setAcceptMonth(trade.getAcceptMonth());
		tradeGoods.setUserId(trade.getUserId());		
		tradeGoods.setGoodsId(trade.getGoodsId());
		tradeGoods.setModifyTag("0");
		tradeGoods.setStartDate(trade.getAcceptDate());
		tradeGoods.setBiRulesId(Long.parseLong("0"));
		tradeGoods.setAcceptDate(trade.getAcceptDate());
		tradeGoods.setGoodsReleaseId(trade.getGoodsReleaseId());
		tradeGoods.setReleaseCycle(goodsRelease.getReleaseCycle());
	    Calendar calendar=Calendar.getInstance();
	    calendar.setTime(tradeGoods.getStartDate());
	    if("0".equals(goodsRelease.getUnit())){
	    	calendar.add(Calendar.MONTH, Integer.valueOf(goodsRelease.getReleaseCycle()));
	    }else{
	    	calendar.set(Calendar.DATE, Integer.valueOf(goodsRelease.getReleaseCycle()));
	    }
	    if(goodsRelease.getSilentPeriod()!=null){
	    	 calendar.add(Calendar.MONTH, Integer.valueOf(goodsRelease.getSilentPeriod()));
	    }
		tradeGoods.setEndDate(calendar.getTime());
		tradeGoodsMapper.insert(tradeGoods);
	}
	


}
