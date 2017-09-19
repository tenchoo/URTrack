package com.urt.miniService.authority;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.mapper.GoodsMapper;
import com.urt.mapper.GoodsReleaseMapper;
import com.urt.mapper.LaoUserGoodsMapper;
import com.urt.mapper.LaoUserMapper;
import com.urt.mapper.LaoUserOperatorPlanMapper;
import com.urt.mapper.LaoUserResMapper;
import com.urt.mapper.LaoUserSvcstateMapper;
import com.urt.mapper.OperatorPlanMapper;
import com.urt.mapper.ext.GoodsReleaseExtMapper;
import com.urt.mapper.ext.IccidLibExtMapper;
import com.urt.mapper.ext.TradeDiscontExtMapper;
import com.urt.mapper.ext.TradeExtMapper;
import com.urt.mapper.ext.TradeGoodsExtMapper;
import com.urt.mapper.ext.TradeOperatorPlanExtMapper;
import com.urt.mapper.ext.TradeSvcStateExtMapper;
import com.urt.po.Goods;
import com.urt.po.GoodsRelease;
import com.urt.po.IccidLib;
import com.urt.po.LaoUser;
import com.urt.po.LaoUserGoods;
import com.urt.po.LaoUserOperatorPlan;
import com.urt.po.LaoUserRes;
import com.urt.po.LaoUserSvcstate;
import com.urt.po.OperatorPlan;
import com.urt.po.Trade;
import com.urt.po.TradeGoods;
import com.urt.po.TradeOperatorPlan;
import com.urt.po.TradeSvcState;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

/**
 * 类说明：用户归档微服务
 * 
 * @author haosun1
 * @date 2016年9月27日09:31:58
 */
@Service("miniUserArchivingService")
public class MiniUserArchivingServiceImpl {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private LaoUserMapper laoUserDAO; // 用户资料

	@Autowired
	private LaoUserGoodsMapper laoUserGoodsDAO; // 用户商品

	@Autowired
	private LaoUserResMapper laoUserResDAO; // 用户资源

	@Autowired
	private LaoUserSvcstateMapper laoUserSvcstateDAO;// 用户服务状态

	@Autowired
	private LaoUserOperatorPlanMapper laoUserOperatorPlanDAO;// 用户套餐
	
	@Autowired
	private TradeExtMapper tradeExtDAO ; //业务台帐主表
	
	@Autowired
	private TradeGoodsExtMapper tradeGoodsExtDAO; //业务台帐商品表
	
	@Autowired
	private TradeOperatorPlanExtMapper tradeOperatorPlanExtDAO ;// 业务台帐套餐表
	
	@Autowired
	private TradeSvcStateExtMapper  tradeSvcStateExtDAO;// 业务台帐状态子表
	
	@Autowired
	private OperatorPlanMapper  operatorPlanMapper;// 业务台帐优惠子表
	
	@Autowired
	private IccidLibExtMapper  iccidLibExtDAO;// 业务台帐优惠子表
	
	@Autowired 
	private GoodsReleaseMapper goodsReleaseDao;
	
	@Autowired 
	private GoodsMapper goodsDao;
	
	/**
	 * 从业务台帐主表 得到记录 向 用户资料中插入一条数据
	 * 
	 * @param tradeId
	 * @return
	 */
	public void insertLaoUser(String tradeId) {
		//查询业务台帐主表
		Trade trade = tradeExtDAO.selectByTradeId(Long.parseLong(tradeId));
		TradeSvcState tradeSvcState = tradeSvcStateExtDAO.selectByTradeId(Long.parseLong(tradeId));
		List<TradeOperatorPlan> tradeOperatorPlanList = tradeOperatorPlanExtDAO.selectByTradeId(Long.parseLong(tradeId));
		LaoUser user = null;
		if(trade != null){
			user =new LaoUser();
			user.setUserId(trade.getUserId());
			if(tradeSvcState != null ){
				user.setStateCode(tradeSvcState.getStateCode());
//				user.setStatusCode(tradeSvcState.getStateCode());
			}else{
				user.setStateCode("5");
			}
			user.setCustId(trade.getCustId());
			user.setChannelCustName(trade.getCustName());
			user.setCustName(trade.getCustName());
			user.setIccid(trade.getIccid());
			user.setChannelCustId(trade.getChannalCustId());
			user.setAcctTag("0");
			user.setPrepayTag("0");
			user.setRemoveTag("0");
			if(tradeOperatorPlanList !=null && tradeOperatorPlanList.size()> 0){
				user.setOperatorsId(tradeOperatorPlanList.get(0).getOperatorsId());
			}
			if(trade.getTradeId() != null){
				IccidLib iccid = iccidLibExtDAO.selectByIccid(trade.getIccid().toString());
				user.setAttribute1(iccid.getAttribute1());
				user.setAttribute2(iccid.getAttribute2());
				user.setValue1(iccid.getValue1());
				user.setValue2(iccid.getValue2());
				user.setDeviceId(iccid.getDevicetype());
				user.setInDate(iccid.getInDate());
				user.setMsisdn(iccid.getMsisdn());
				user.setDeviceId(iccid.getVal1());
			}
			if(laoUserDAO.selectByPrimaryKey(trade.getUserId()) != null){
				laoUserDAO.updateByPrimaryKeySelective(user);
			}else{
				user.setOpenDate(new Date());
				laoUserDAO.insertSelective(user);
			}
		}else{
			log.debug("************根据tradeId查询业务台帐主表为空*****************");
		}
	}

	public void updateLaoUser(LaoUser laoUser) {
		laoUserDAO.updateByUserId(laoUser);
	}

	/**
	 * 从业务台帐商品子表 得到记录 向 用户商品中插入一条数据
	 * 
	 * @param tradeId
	 * @return
	 */
	public void insertLaoUserGoods(String tradeId) {
		
		TradeGoods tradeGoods = tradeGoodsExtDAO.selectByTradeId(Long.parseLong(tradeId));
		if(tradeGoods != null){
			GoodsRelease goodsRelease = goodsReleaseDao.selectByPrimaryKey(tradeGoods.getGoodsReleaseId());
			LaoUserGoods userGoods = new LaoUserGoods();
			userGoods.setUserId(tradeGoods.getUserId());
			userGoods.setGoodsId(tradeGoods.getGoodsId());
			userGoods.setStartDate(tradeGoods.getStartDate());
			userGoods.setBiRulesId(tradeGoods.getBiRulesId());
			userGoods.setEndDate(tradeGoods.getEndDate());
			userGoods.setReleaseCycle(goodsRelease.getReleaseCycle());
			userGoods.setGoodsReleaseId(goodsRelease.getGoodsReleaseId());
			laoUserGoodsDAO.insertSelective(userGoods);
		}else{
			log.debug("************根据tradeId查询业务台帐商品子表为空*****************");
		}
	}

	/*
	 * 更新操作
	 */
	public void updateLaoUserGoods(LaoUserGoods laoUserGoods) {
		laoUserGoodsDAO.updateByPrimaryKey(laoUserGoods);
	}

	/**
	 * 从业务台帐资源子表 得到记录 向 用户资料中插入一条数据
	 * 
	 * @param tradeId
	 * @return
	 */
	public void insertLaoUserRes(String tradeId) {
		
	}

	public void updateLaoUserRes(LaoUserRes laoUserRes) {

		laoUserResDAO.updateByPrimaryKey(laoUserRes);
	}

	/**
	 * 从业务台帐服务状态子表 得到记录 向 用户服务状态中插入一条数据
	 * 
	 * @param tradeId
	 * @return
	 */
	public LaoUserSvcstate insertLaoUserSvcstate(String tradeId) {
		LaoUserSvcstate userSvcstate = new LaoUserSvcstate();
		TradeSvcState tradeSvcState = tradeSvcStateExtDAO.selectByTradeId(Long.parseLong(tradeId));
		if(tradeSvcState != null){
			userSvcstate.setServiceId(tradeSvcState.getServiceId());
			userSvcstate.setStartDate(tradeSvcState.getAcceptDate());
			userSvcstate.setUserId(tradeSvcState.getUserId());
			userSvcstate.setStateCode(tradeSvcState.getStateCode());
			userSvcstate.setEndDate(tradeSvcState.getEndDate());
			userSvcstate.setOpeartorsDealCode("");
			userSvcstate.setOpeartorsDealNum(0);
			userSvcstate.setOpeartorsDealRst("1");//0成功1失败
			
			//将旧的服务状态设置成失效状态
			LaoUserSvcstate oldUserSvcstate= laoUserSvcstateDAO.selectByPrimaryKey(tradeSvcState.getUserId(), tradeSvcState.getServiceId(), tradeSvcState.getStartDate());
			if(oldUserSvcstate != null){
				Calendar c = Calendar.getInstance();
				c.setTime(oldUserSvcstate.getStartDate());
				c.add(Calendar.SECOND, -1);
				oldUserSvcstate.setEndDate(c.getTime());
				oldUserSvcstate.setUpdateTime(new Date());
				//孙昊 修改
				oldUserSvcstate.setStateCode(tradeSvcState.getStateCode());
				laoUserSvcstateDAO.updateByPrimaryKey(oldUserSvcstate);
			}
			
			//插入新数据
			laoUserSvcstateDAO.insert(userSvcstate);
		}else{
			log.debug("************根据tradeId查询业务台帐服务状态子表为空*****************");
		}
		
		return userSvcstate;
	}
	
	public void updateLaoUserSvcstate(LaoUserSvcstate userSvcstate) {
		laoUserSvcstateDAO.updateByPrimaryKey(userSvcstate);
	}

	/**
	 * 从业务台帐套餐子表 得到记录 向 用户套餐中插入一条数据
	 * 
	 * @param tradeId
	 * @return
	 */
	public List<LaoUserOperatorPlan> insertLaoUserOperatorPlan(String tradeId) {
		Trade trade = tradeExtDAO.selectByTradeId(Long.parseLong(tradeId));
		TradeGoods tradeGoods = tradeGoodsExtDAO.selectByTradeId(Long.parseLong(tradeId));
		Goods goods=null;
		if(tradeGoods!=null){
			goods = goodsDao.selectByPrimaryKey(tradeGoods.getGoodsId());
		}
		boolean maintenance=Integer.valueOf(trade.getIfMaintenance())>0 ? true:false;
		List<LaoUserOperatorPlan> userOperatorPlanList = null;
		List<TradeOperatorPlan> tradeOperatorPlanList = tradeOperatorPlanExtDAO.selectByTradeId(Long.parseLong(tradeId));
		if(tradeOperatorPlanList != null && tradeOperatorPlanList.size() > 0){
			userOperatorPlanList = new ArrayList<>();
			
			/*Boolean flag=false;
			if(goods!=null && "4".equals(goods.getGoodsType())){
				flag=true;
			}*/
			for (int i=0;i<tradeOperatorPlanList.size();i++) {
				TradeOperatorPlan tradeOperatorPlan=tradeOperatorPlanList.get(i);
				LaoUserOperatorPlan userOperatorPlan =  new LaoUserOperatorPlan();
				userOperatorPlan.setUserId(tradeOperatorPlan.getUserId());
				userOperatorPlan.setStartDate(new Date());
				userOperatorPlan.setGoodsId(tradeOperatorPlan.getGoodsId());
				userOperatorPlan.setGoodsIndex(tradeOperatorPlan.getGoodsIndex());
				userOperatorPlan.setOperatorsId(tradeOperatorPlan.getOperatorsId());
				userOperatorPlan.setOperatorsPid(tradeOperatorPlan.getOperatorsPid());
				userOperatorPlan.setPlanId(tradeOperatorPlan.getPlanId());
				userOperatorPlan.setPlanType(tradeOperatorPlan.getPlanType());
				userOperatorPlan.setEndDate(tradeOperatorPlan.getEndDate());
				userOperatorPlan.setPlanClassify(tradeOperatorPlan.getPlanClassify());
				userOperatorPlan.setOpeartorsDealCode("");
				userOperatorPlan.setOpeartorsDealNum(0);
				if(maintenance){
					userOperatorPlan.setOpeartorsDealRst("0");
				}else{
					if("4".equals(goods.getGoodsType())){
						if(i==0 || i==1){
							userOperatorPlan.setOpeartorsDealRst("3");
						}else{
							userOperatorPlan.setOpeartorsDealRst("2");
						}
					}else if("5".equals(goods.getGoodsType()) || "6".equals(goods.getGoodsType())){
						if(i==0){
							userOperatorPlan.setOpeartorsDealRst("3");
						}else{
							userOperatorPlan.setOpeartorsDealRst("2");
						}
					}else{
						userOperatorPlan.setOpeartorsDealRst("3");
					}
				}
				//为出账新增字段
				userOperatorPlan.setTradeId(Long.parseLong(tradeId));
				userOperatorPlan.setCostPrice(goods.getGoodsPrices());
				userOperatorPlan.setBillTag("0");//0-未出账；1-已出账
				
				userOperatorPlan.setGoodsReleaseId(tradeGoods.getGoodsReleaseId());
				laoUserOperatorPlanDAO.insert(userOperatorPlan);
				userOperatorPlanList.add(userOperatorPlan);
			}
		}else{
			log.debug("************根据tradeId查询业务台帐套餐子表为空*****************");
		}
		return userOperatorPlanList;
	}
	
	public void updateLaoUserOperatorPlan(LaoUserOperatorPlan userOperatorPlan) {
		laoUserOperatorPlanDAO.updateByPrimaryKey(userOperatorPlan);
	}
}
