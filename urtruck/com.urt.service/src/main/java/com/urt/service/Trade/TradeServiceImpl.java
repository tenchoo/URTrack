package com.urt.service.Trade;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Goods.DiscontDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.dmp.LaoDMPCardDto;
import com.urt.dto.unicom.PaymentDto;
import com.urt.interfaces.Trade.TradeService;
import com.urt.mapper.GoodsMapper;
import com.urt.mapper.LaoCustomerPoMapper;
import com.urt.mapper.OperatorPlanMapper;
import com.urt.mapper.TradeMapper;
import com.urt.mapper.ext.GoodsElementExtMapper;
import com.urt.mapper.ext.IccidLibExtMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.mapper.ext.TradeExtMapper;
import com.urt.miniService.MinTradeRecordServiceImpl;
import com.urt.miniService.authority.MiniTradeServiceImpl;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.Discont;
import com.urt.po.Goods;
import com.urt.po.GoodsElement;
import com.urt.po.IccidLib;
import com.urt.po.LaoCustomerPo;
import com.urt.po.LaoOperatordealLog;
import com.urt.po.LaoUser;
import com.urt.po.OperatorPlan;
import com.urt.po.Trade;
import com.urt.service.util.ActionUtil;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
/**
 * 
 * @author zhaoyf
 *
 */
@Service("tradeService")
@Transactional(propagation=Propagation.REQUIRED)
public class TradeServiceImpl implements TradeService{

	private static Log log = LogFactory.getLog(TradeServiceImpl.class);

	@Autowired
	private IccidLibExtMapper iccidLibExtMapper;
	@Autowired
	private LaoUserExtMapper laoUserExtMapper;
	@Autowired
	private MiniTradeServiceImpl miniTradeService;
	@Autowired
	private LaoCustomerPoMapper laoCustomerPoMapper;
	@Autowired
	private TradeMapper tradeMapper;
	@Autowired
	private TradeExtMapper tradeExtMapper;
	@Autowired
	private GoodsElementExtMapper goodsElementExtMapper;
	@Autowired
	private OperatorPlanMapper planDao;
	@Autowired
	private GoodsMapper goodsDao;
	@Autowired
	private MinTradeRecordServiceImpl miniTradeRecordServiceImpl;
	/*
	 * 1、需要操作账号信息。增加 account_id 。
	 * 2、在trade_goods添加 发布id，周期，同步到user_goods中。
	 * 3、addtrade，支持3类业务，
	 * 一 激活，custId登录人的custID，需要生成状态变更表，需要生成产品订购平
	 * 二 订购，联通订购时查询联通卡状态，更新卡状态后，生产订单数据。
	 * 三 状态变更。
	 * 
	 * 
	 * 	 * 
	 * (non-Javadoc)
	 * @see com.lenovo.LAOAPI.interfaces.Trade.TradeService#addTrade(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */

	@Override
	public String addTrade(LaoSsAccountDto user,String custId,String orderId,String iccid, String goodsId,String goodsReleaseId,String tradeTypeCode,String ifMaintenance)throws Exception {
		String tradeId = null;
		log.info("orderId-"+orderId+"  iccid-"+iccid+"  goodsId-"+goodsId+"  tradeTypeCode-"+tradeTypeCode);
		if("120".equals(tradeTypeCode)){
			List<GoodsElement> findGoodsElementByGoodsId = goodsElementExtMapper.findGoodsElementByGoodsId(Long.valueOf(goodsId));
			Boolean flag=true;
			/*for (GoodsElement goodsElementDto : findGoodsElementByGoodsId) {
				Integer originalId = goodsElementDto.getOriginalId();
				OperatorPlan plan = planDao.selectByPrimaryKey(originalId);
				if("0".equals(plan.getPlanType())){
					flag=true;
					break;
				}
			}*/
			Goods goods = goodsDao.selectByPrimaryKey(Long.valueOf(goodsId));
			if("1".equals(goods.getGoodsType()) || "4".equals(goods.getGoodsType())){
				flag=false;
			}
			if(flag){
				if(user!=null){
					if(!ActionUtil.checkResource(user,"2")){
						log.info(">>>>>>>>>>>无修改当月套餐权限 ");
						if(laoUserExtMapper.getModifiedCountByIccid(iccid)<=0){
							log.info(">>>>>>>>>>>无权限且没到期订购失败 iccid="+iccid);
							return null;
						}
					}
				}else{
					log.info(">>>>>>>>>>>未登录无法订购需要判断权限的订单 iccid="+iccid);
				}
				
			}
			
			
		}
		//三、服务状态变更。
		if(StringUtils.isBlank(goodsId)){
			//
			if(StringUtils.isNotBlank(iccid) && StringUtils.isNotBlank(tradeTypeCode) && StringUtils.isNotBlank(custId)){
				LaoUser laoUser = laoUserExtMapper.selectByIccid(iccid);
				if(laoUser != null){
					Trade trade = new Trade();
					tradeId = ZkGenerateSeq.getIdSeq(SeqID.TRADE_ID);				
					trade.setTradeId(Long.parseLong(tradeId));
					String format = new SimpleDateFormat("MM").format(new Date());
					trade.setAcceptMonth(Short.parseShort(format));
					trade.setTradeTypeCode(Short.parseShort(tradeTypeCode));
					trade.setInModeCode("0");//默认为0
					trade.setSubscribeState("0");//默认为0，订单提交
					trade.setIfMaintenance(ifMaintenance);//是否在维护
					trade.setUserId(laoUser.getUserId());
					trade.setChannalCustId(laoUser.getChannelCustId());
					//trade.setCustId(custId);
					trade.setIccid(iccid);
					trade.setExecTime(new Date());
					String stateCode = laoUser.getStateCode();
					if(laoUser.getCustId() != null){
						LaoCustomerPo laoCustomerPo = laoCustomerPoMapper.selectByPrimaryKey(laoUser.getChannelCustId());
						trade.setCustName(laoCustomerPo.getCustName());
					}
					trade.setAcceptDate(new Date());
					if(user!=null && user.getAcconutId()!=null){
						trade.setFeeStaffId(user.getAcconutId().toString());
					}else{
						trade.setFeeStaffId("default");
					}
					
					if(orderId != null){
						trade.setOrderId(Long.parseLong(orderId));
					}else{
						trade.setOrderId(Long.parseLong(tradeId));
					}
					tradeMapper.insertSelective(trade);
					miniTradeService.addTradeSvcState(trade,tradeTypeCode,stateCode);
				}
				//返回参数不对。
				else{
					
					
				}
			}
		}else{
			//订购和激活
			if(StringUtils.isNotBlank(iccid) && StringUtils.isNotBlank(goodsId) && StringUtils.isNotBlank(tradeTypeCode) && StringUtils.isNotBlank(custId)){
				LaoUser laoUser = laoUserExtMapper.selectByIccid(iccid);
				//用户不为空，订购流程，从用户资料生成订单。再修改订单数据。
				if(laoUser != null){
					Trade trade = new Trade();
					tradeId = ZkGenerateSeq.getIdSeq(SeqID.TRADE_ID);				
					trade.setTradeId(Long.parseLong(tradeId));
					String format = new SimpleDateFormat("MM").format(new Date());
					trade.setAcceptMonth(Short.parseShort(format));
					trade.setTradeTypeCode(Short.parseShort(tradeTypeCode));
					trade.setInModeCode("0");//默认为0
					trade.setSubscribeState("0");//默认为0，订单提交
					trade.setGoodsId(Long.parseLong(goodsId));
					trade.setGoodsReleaseId(Integer.valueOf(goodsReleaseId));
					trade.setUserId(laoUser.getUserId());
					trade.setChannalCustId(laoUser.getChannelCustId());
					//订单cust——id
					trade.setCustId(laoUser.getCustId());
					
					trade.setIccid(iccid);
					//
					trade.setExecTime(new Date());
					String stateCode = laoUser.getStateCode();
					if(laoUser.getCustId() != null){
						LaoCustomerPo laoCustomerPo = laoCustomerPoMapper.selectByPrimaryKey(laoUser.getChannelCustId());
						trade.setCustName(laoCustomerPo.getCustName());
					}
					trade.setAcceptDate(new Date());
					if(user!=null && user.getAcconutId()!=null){
						trade.setFeeStaffId(user.getAcconutId().toString());
					}else{
						trade.setFeeStaffId("default");
					}
					trade.setIfMaintenance(ifMaintenance);//是否在维护
					if(orderId != null){
						trade.setOrderId(Long.parseLong(orderId));
					}else{
						trade.setOrderId(Long.parseLong(tradeId));
					}
					tradeMapper.insertSelective(trade);
					miniTradeService.addTradeDiscont(trade);
					miniTradeService.addTradeGoods(trade);
					miniTradeService.addTradeOperatorPlan(trade);
					miniTradeService.addTradeSvcState(trade, tradeTypeCode,stateCode);
				}else{
					//用户不存在，激活。
					IccidLib iccidLib = iccidLibExtMapper.selectByIccid(iccid);
					String stateCode = null;
					if(iccidLib != null){
						Trade trade = new Trade();
						tradeId = ZkGenerateSeq.getIdSeq(SeqID.TRADE_ID);
						trade.setTradeId(Long.parseLong(tradeId));
						String format = new SimpleDateFormat("MM").format(new Date());
						trade.setAcceptMonth(Short.parseShort(format));
						trade.setTradeTypeCode(Short.parseShort(tradeTypeCode));
						trade.setInModeCode("0");//默认为0
						trade.setSubscribeState("0");//默认为0，订单提交
						trade.setGoodsId(Long.parseLong(goodsId));
						trade.setGoodsReleaseId(Integer.valueOf(goodsReleaseId));
						trade.setUserId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.USER_ID)));
						trade.setChannalCustId(Long.parseLong(iccidLib.getCustid()));
						trade.setCustId(user.getCustId());
						trade.setIccid(iccid);
						trade.setExecTime(new Date());					
						if(iccidLib.getCardstatus() == null){
							if(iccidLib.getOperators().equals(1)){
								stateCode = "5";//联通默认状态
							}
	/*						if(iccidLib.getOperators().equals(2)){
								stateCode = "5";//移动默认状态
							}
							if(iccidLib.getOperators().equals(3)){
								stateCode = "5";//电信默认状态
							}*/
							
						}else{
							stateCode = iccidLib.getCardstatus();
						}
						if(iccidLib.getCustid() != null){
							LaoCustomerPo laoCustomerPo = laoCustomerPoMapper.selectByPrimaryKey(Long.parseLong(iccidLib.getCustid()));
							trade.setCustName(laoCustomerPo.getCustName());
						}
						trade.setAcceptDate(new Date());
						if(user!=null && user.getAcconutId()!=null){
							trade.setFeeStaffId(user.getAcconutId().toString());
						}else{
							trade.setFeeStaffId("default");
						}
						trade.setIfMaintenance(ifMaintenance);//是否在维
						if(orderId != null){
							trade.setOrderId(Long.parseLong(orderId));
						}else{
							trade.setOrderId(Long.parseLong(tradeId));
						}
						tradeMapper.insertSelective(trade);
						miniTradeService.addTradeDiscont(trade);
						miniTradeService.addTradeGoods(trade);
						miniTradeService.addTradeOperatorPlan(trade);
						miniTradeService.addTradeSvcState(trade,tradeTypeCode,stateCode);
					}else{
						log.info("非法的ICCID········");
					}
				}
				
			}
		}
		
		return tradeId;
	}
	

	@Override
	public String addTrade(LaoSsAccountDto user,String custId, String iccid, String goodsId,String goodsReleaseId,
			String tradeTypeCode,String ifMaintenance) throws Exception {
		 return this.addTrade(user,custId,"0", iccid, goodsId,goodsReleaseId, tradeTypeCode,ifMaintenance);

	}


	@Override
	public TradeDto queryTradeId(Long tradeId) throws Exception {
		// TODO Auto-generated method stub
		Trade trade = tradeExtMapper.selectByTradeId(tradeId);
		TradeDto tradeDto = new TradeDto();
		BeanMapper.copy(trade, tradeDto);
		return tradeDto;
	}


	@Override
	public int updateTrade(TradeDto tradeDto) throws Exception {
		// TODO Auto-generated method stub
		Trade trade = new Trade();
		BeanMapper.copy(tradeDto, trade);
		return tradeMapper.updateByPrimaryKey(trade);

	}
	
	/**
	 * 
	 */
//	@Override
	public int changeStatus(String tradeId, PaymentDto payment)
			throws Exception {
		// TODO Auto-generated method stub
		Trade trade = tradeExtMapper.selectByTradeId(Long.valueOf(tradeId));
		trade.setFeeState("1");//收费标志 0：未收费 1：已收费
		trade.setFeeTime(new Date());
		trade.setFinishDate(new Date());
		return tradeMapper.updateByPrimaryKey(trade);
	}
	
	@Override
	public boolean tradingFailure(String iccid, String typeCode) {
		if (StringUtils.isNotBlank(typeCode) && typeCode.equals("100")) {
			if (StringUtils.isNotBlank(iccid)) {
				List<Trade> tradeList = tradeExtMapper.tradingFailure(iccid);
				if (tradeList != null && tradeList.size() > 0 ) {
					return false;
				}
				/*if (tradeList != null && tradeList.size() > 0) {
					for (Trade trade : tradeList) {
						if (!trade.getSubscribeState().equals("3") && typeCode.equals(trade.getTradeTypeCode())) {
							return false;
						}
					}
				}
				return true;*/
			}
		}
		return true;
	}


	@Override
	public Map<String, Object> queryPage(TradeDto dto, int pageNo, int pageSize,String monthId) {
			log.info("DMP业务进入接口----queryPage----");
			Map<String, Object> map = miniTradeRecordServiceImpl.queryPage(dto,
					pageNo, pageSize,monthId);
			log.info("DMP业务走出接口----queryPage----");
			return map;
		}


	@Override
	public List<Map<String, Object>> selectRecord(String monthId, Long custId) {
		List<Map<String, Object>> map = tradeExtMapper.selectRecord(monthId,custId);
		return map;
	}
	
	@Override
    public String creatTrade(String custId, String orderId, Map<String, Object> param, String goodsId,
            String goodsReleaseId, String tradeTypeCode, String ifMaintenance) throws Exception {
        String tradeId = null;
        log.info("orderId-"+orderId+"  iccid-"+param.get("iccid")+" msisdn-"+param.get("msisdn")+"  goodsId-"+goodsId+"  tradeTypeCode-"+tradeTypeCode);
            //è®¢è´­å’Œæµ‹è¯•æœŸå¼€é€š     
        if(StringUtils.isNotBlank(goodsId) && StringUtils.isNotBlank(tradeTypeCode) && StringUtils.isNotBlank(custId)){
            LaoUser laoUser = laoUserExtMapper.selectByMap(param);
                //ç”¨æˆ·ä¸ä¸ºç©ºï¼Œè®¢è´­æµç¨‹ï¼Œä»Žç”¨æˆ·èµ„æ–™ç”Ÿæˆè®¢å•ã€‚å†ä¿®æ”¹è®¢å•æ•°æ®ã€‚
            if(laoUser != null){
                Trade trade = new Trade();
                tradeId = ZkGenerateSeq.getIdSeq(SeqID.TRADE_ID);               
                trade.setTradeId(Long.parseLong(tradeId));
                String format = new SimpleDateFormat("MM").format(new Date());
                trade.setAcceptMonth(Short.parseShort(format));
                trade.setTradeTypeCode(Short.parseShort(tradeTypeCode));
                trade.setInModeCode("0");//é»˜è®¤ä¸º0
                trade.setSubscribeState("0");//é»˜è®¤ä¸º0ï¼Œè®¢å•æäº¤
                trade.setGoodsId(Long.parseLong(goodsId));
                trade.setGoodsReleaseId(Integer.valueOf(goodsReleaseId));
                trade.setUserId(laoUser.getUserId());
                trade.setChannalCustId(laoUser.getChannelCustId());
                    //è®¢å•custâ€”â€”id
                trade.setCustId(laoUser.getCustId());                   
                trade.setIccid(laoUser.getIccid());
                trade.setExecTime(new Date());
                String stateCode = laoUser.getStateCode();
                if(laoUser.getCustId() != null){
                   LaoCustomerPo laoCustomerPo = laoCustomerPoMapper.selectByPrimaryKey(laoUser.getChannelCustId());
                   trade.setCustName(laoCustomerPo.getCustName());
                }
                trade.setAcceptDate(new Date());
                trade.setFeeStaffId("default");
                trade.setIfMaintenance(ifMaintenance);//æ˜¯å¦åœ¨ç»´æŠ¤
                if(orderId != null){
                   trade.setOrderId(Long.parseLong(orderId));
                }else{
                   trade.setOrderId(Long.parseLong(tradeId));
                }
                 tradeMapper.insertSelective(trade);
                 miniTradeService.addTradeDiscont(trade);
                 miniTradeService.addTradeGoods(trade);
                 miniTradeService.addTradeOperatorPlan(trade);
                 miniTradeService.addTradeSvcState(trade, tradeTypeCode,stateCode);
             }
         }             
        return tradeId;
    
    }

}
