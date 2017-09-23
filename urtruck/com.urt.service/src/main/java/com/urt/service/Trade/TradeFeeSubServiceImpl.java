package com.urt.service.Trade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.Goods.DiscontDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.Trade.TradeFeeSubDto;
import com.urt.dto.unicom.PaymentDto;
import com.urt.interfaces.Goods.GoodsService;
import com.urt.interfaces.Trade.TradeFeeSubService;
import com.urt.mapper.DiscontMapper;
import com.urt.mapper.GoodsMapper;
import com.urt.mapper.GoodsReleaseMapper;
import com.urt.mapper.TradeFeeSubMapper;
import com.urt.mapper.TradeMapper;
import com.urt.mapper.ext.GoodsElementExtMapper;
import com.urt.mapper.ext.LaoSsAccountPoExtMapper;
import com.urt.mapper.ext.LaoUserOperatorPlanExtMapper;
import com.urt.mapper.ext.TradeExtMapper;
import com.urt.mapper.ext.TradeFeeSubExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.Discont;
import com.urt.po.GoodsElement;
import com.urt.po.GoodsRelease;
import com.urt.po.LaoSsAccountPo;
import com.urt.po.LaoUserOperatorPlan;
import com.urt.po.Trade;
import com.urt.po.TradeFeeSub;
import com.urt.po.TradeFeeSubPo;
@Service("tradeFeeSubService")
@Transactional(propagation=Propagation.REQUIRED)
/**
 * 
 * @author zhaoyf
 *
 */
public class TradeFeeSubServiceImpl implements TradeFeeSubService{
	
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private GoodsReleaseMapper goodsReleaseMapper;
	@Autowired
	private GoodsElementExtMapper goodsElementExtMapper;
	@Autowired
	private DiscontMapper discontMapper;
	@Autowired
	private TradeExtMapper tradeExtMapper;
	@Autowired
	private TradeMapper tradeMapper;
	@Autowired
	private TradeFeeSubExtMapper tradeFeeSubExtMapper;
	@Autowired
	private TradeFeeSubMapper tradeFeeSubMapper;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private LaoUserOperatorPlanExtMapper userOperatorPlanExtMapper;
	@Autowired
	private LaoSsAccountPoExtMapper laoSsAccountPoExtMapper;
	
	Logger log = Logger.getLogger(getClass());

	
	/**
	 * 添加算费
	 */
	@Override
	public int addTradeFeeSub(TradeDto tradeDto) throws Exception {
		// TODO Auto-generated method stub
		TradeFeeSub tradeFeeSub = new TradeFeeSub();
		tradeFeeSub.setTradeId(tradeDto.getTradeId());//业务流水号
		tradeFeeSub.setAcceptMonth(tradeDto.getAcceptMonth());//受理月份
		tradeFeeSub.setGoodsId(tradeDto.getGoodsId());//商品ID
		GoodsRelease goodsRelease = goodsReleaseMapper.selectByPrimaryKey(tradeDto.getGoodsReleaseId());
		tradeFeeSub.setOldfee(goodsRelease.getReleasePrice());//应缴金额
		//查询商品元素
		List<GoodsElement> findGoodsElementByGoodsId = goodsElementExtMapper.findGoodsElementByGoodsId(tradeDto.getGoodsId());
		DiscontDto realDiscont = new DiscontDto();
		for (GoodsElement goodsElementDto : findGoodsElementByGoodsId) {
			if("0".equals(goodsElementDto.getElementType())){     //0:优惠包  1:原始包
				Integer originalId = goodsElementDto.getOriginalId();
				Discont discont = discontMapper.selectByPrimaryKey(originalId);	
				BeanMapper.copy(discont, realDiscont);
			}else{
				Integer discontValue = 100;  //默认优惠值
				realDiscont.setDiscontValue(discontValue);
			}
		}
		Integer discontValue = realDiscont.getDiscontValue(); //取到优惠值
		Double oldPrice = Double.parseDouble(goodsRelease.getReleasePrice().toString());
		log.info("oldPrice----"+oldPrice);
		Double discont = Double.parseDouble(discontValue.toString());
		log.info("discont----"+discont);
		Double result = (oldPrice*discont/100);
		log.info("result------"+result);
		String valueOf = String.valueOf(oldPrice);
		String valueOf2 = String.valueOf(result);
//		log.info("Math.round(result)------"+Math.round(result));
//		log.info("(long)goods.getGoodsPrices()------"+Long.parseLong(goods.getGoodsPrices()));
//		log.info(Long.parseLong(goods.getGoodsPrices()) != Math.round(result));
		tradeFeeSub.setFee(String.valueOf(result)); //实缴
//		if(Long.parseLong(goods.getGoodsPrices()) != Math.round(result)){
		if(!String.valueOf(oldPrice).equals(String.valueOf(result))){
			tradeFeeSub.setDiscontId((long)realDiscont.getDiscontId()); //优惠ID
//			tradeFreeSub.setDerateRemark("");//减免原因
			tradeFeeSub.setCalculateTag("N");//手工算费标记 N：自动 Y：手动
			tradeFeeSub.setAcceptDate(tradeDto.getAcceptDate());//受理日期
			tradeFeeSub.setFeeStaffId("admin");//收费员工
		}else{
			tradeFeeSub.setCalculateTag("N");//手工算费标记 N：自动 Y：手动
			tradeFeeSub.setAcceptDate(tradeDto.getAcceptDate());//受理日期
			tradeFeeSub.setFeeStaffId("admin");//收费员工
		}
		return tradeFeeSubMapper.insert(tradeFeeSub);
	}
	/**
	 * 更新
	 */
	@Override
	public int updateTradeFeeSub(TradeFeeSubDto tradeFeeSubDto) throws Exception {
		// TODO Auto-generated method stub
		TradeFeeSub tradeFeeSub = new TradeFeeSub();
		BeanMapper.copy(tradeFeeSubDto, tradeFeeSub);
		return tradeFeeSubMapper.updateByPrimaryKey(tradeFeeSub);
	}
	/**
	 * 查询
	 */
	@Override
	public TradeFeeSubDto queryTradeFreeSubByTradeId(String tradeId)throws Exception{
		TradeFeeSub queryTradeFreeSubByTradeId = tradeFeeSubExtMapper.queryTradeFeeSubByTradeId(Long.parseLong(tradeId));
		TradeFeeSubDto tradeFeeSubDto = new TradeFeeSubDto();
		BeanMapper.copy(queryTradeFreeSubByTradeId, tradeFeeSubDto);
		return tradeFeeSubDto;
	}
	
	/**
	 *	
	 */
	@Override
	public int changePayTag(String tradeId,PaymentDto payment) throws Exception {
		// TODO Auto-generated method stub
		TradeFeeSub tradeFeeSub = tradeFeeSubExtMapper.queryTradeFeeSubByTradeId(Long.valueOf(tradeId));
		tradeFeeSub.setPayTag("1");//0:未收费  1：已收费
		tradeFeeSub.setPayOrderId(payment.getOrderId());//订单号
		tradeFeeSub.setPayType(payment.getPayType().toString());//1：支付宝
		tradeFeeSub.setPayDate(new Date());//收费日期
		return tradeFeeSubMapper.updateByPrimaryKey(tradeFeeSub);

	}


	@Override
	public List<TradeFeeSubDto> queryTradeFreeSubByIccid(String iccid, String startTime, String endTime, int curPage, int pageSize) {
		 int startRow = (curPage-1)*pageSize;
		 int endRow = startRow+pageSize;
		 List<TradeFeeSub> tradeFeeSubList = tradeFeeSubExtMapper.queryTradeFeeSubByIccid(iccid, startTime, endTime, startRow, endRow);
		 List<TradeFeeSubDto> list = null;
		 if(tradeFeeSubList != null && tradeFeeSubList.size() > 0){
			 list = new ArrayList<TradeFeeSubDto>();
			 for (TradeFeeSub tradeFeeSub : tradeFeeSubList) {
				 TradeFeeSubDto tradeFeeSubDto = new TradeFeeSubDto();
				 BeanMapper.copy(tradeFeeSub, tradeFeeSubDto);
				 GoodsDto good = goodsService.findByGoodsId(tradeFeeSub.getGoodsId());
				 Trade trade = tradeExtMapper.selectByTradeId(tradeFeeSubDto.getTradeId());
				 if(trade != null){
					 GoodsRelease goodsRealse = goodsReleaseMapper.selectByPrimaryKey(trade.getGoodsReleaseId());
					 if(goodsRealse != null){
						 good.setUnit(goodsRealse.getUnit());
						 good.setReleaseCycle(goodsRealse.getReleaseCycle());
					 }
				 }
				 tradeFeeSubDto.setGoodsDto(good);
				 list.add(tradeFeeSubDto);
			 }
		 }
		 return list;
	}
	@Override
	public int count(String iccid, String startTime, String endTime) {
		return tradeFeeSubExtMapper.countToatal(iccid, startTime, endTime);
	}
	@Override
	public int countTotal(String custId, String startTime, String endTime) {
		return tradeFeeSubExtMapper.countTotal(custId, startTime, endTime);
	}
	@Override
	public List<TradeFeeSubDto> queryTradeFeeSubByCustId(String custId, String startTime, String endTime, int curPage, int pageSize) {
		List<TradeFeeSubDto> dtoList = null;
		
		int startRow = (curPage-1)*pageSize;
		int endRow = startRow+pageSize;
		List<TradeFeeSubPo> tradeFeeSubList = tradeFeeSubExtMapper.queryTradeFeeSubByCustId(custId, startTime, endTime, startRow, endRow);
		if(tradeFeeSubList != null && tradeFeeSubList.size() > 0){
			dtoList = new ArrayList<TradeFeeSubDto>();
			for (TradeFeeSubPo tradeFeeSubPo : tradeFeeSubList) {
				TradeFeeSubDto dto = new TradeFeeSubDto();
				BeanMapper.copy(tradeFeeSubPo, dto);
				if(dto.getTradeId() != null) {
					List<LaoUserOperatorPlan> selectOperatorPlanByTradeId = userOperatorPlanExtMapper.selectOperatorPlanByTradeId(dto.getTradeId());
					if(selectOperatorPlanByTradeId != null && selectOperatorPlanByTradeId.size() > 0){
						for (LaoUserOperatorPlan laoUserOperatorPlan : selectOperatorPlanByTradeId) {
							if(("1").equals(laoUserOperatorPlan.getOpeartorsDealRst())){
								dto.setOrderResult("订购失败");
								break;
							}else{
								dto.setOrderResult("订购成功");
							}
						}
					}else{
						dto.setOrderResult("订购失败");
					}
					
					//查询
					Trade trade = tradeExtMapper.selectByTradeId(dto.getTradeId());
					if(trade != null){
						List<LaoSsAccountPo> custList = null;
						if(trade.getCustId() != null){
							custList = laoSsAccountPoExtMapper.queryByCustId(trade.getCustId());
						}else{
							custList = laoSsAccountPoExtMapper.queryByCustId(trade.getChannalCustId());
						}
						
						if(custList != null && custList.size() > 0){
							dto.setCustName(custList.get(0).getLoginName());
						}
					}
				}
				//写死一笔订单中 购买的商品的数量
				dto.setPagNumber(1);
				//转换时间格式
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(dto.getPayDate() != null){
					dto.setPayDateStr(format.format(dto.getPayDate()));
				}
				dtoList.add(dto);
			}
		}
		return dtoList;
	}
}
