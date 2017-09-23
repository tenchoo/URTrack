package com.urt.service.apiMsg;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.Ability.collect.UserOrderProduct;
import com.urt.Ability.collect.UserStateChange;
import com.urt.dto.ApiMsgLogDto;
import com.urt.dto.LaoUserOperatorPlanDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.apiMsg.ApiMsgLogService;
import com.urt.mapper.ApiMsgLogMapper;
import com.urt.mapper.GoodsMapper;
import com.urt.mapper.GoodsReleaseMapper;
import com.urt.mapper.LaoUserOperatorPlanMapper;
import com.urt.mapper.OperatorPlanMapper;
import com.urt.mapper.OperatorsMapper;
import com.urt.mapper.ext.GoodsElementExtMapper;
import com.urt.mapper.ext.GoodsExtMapper;
import com.urt.mapper.ext.LaoUserGoodsExtMapper;
import com.urt.mapper.ext.LaoUserOperatorPlanExtMapper;
import com.urt.mapper.ext.TradeExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.msgProducter.trade.ApiMsgLogProducer;
import com.urt.po.ApiMsgLog;
import com.urt.po.Goods;
import com.urt.po.GoodsElement;
import com.urt.po.GoodsRelease;
import com.urt.po.LaoUser;
import com.urt.po.LaoUserOperatorPlan;
import com.urt.po.OperatorPlan;
import com.urt.po.Operators;
import com.urt.po.Trade;
import com.urt.po.TradeGoods;
import com.urt.po.TradeOperatorPlan;
import com.urt.utils.SeqID;
import com.urt.utils.SpringContextUtils;
import com.urt.utils.ZkGenerateSeq;
@Service("apiMsgLogService")
public class ApiMsgLogServiceImpl implements ApiMsgLogService{

	private static Logger logger=LoggerFactory.getLogger(ApiMsgLogServiceImpl.class);
	@Autowired
	private ApiMsgLogMapper apiMsgLogDao;
	
	@Autowired
	private ApiMsgLogProducer apiMsgLogProducer;
	
	@Autowired
	private LaoUserGoodsExtMapper userGoodsExtDao;
	
	@Autowired
	private LaoUserOperatorPlanExtMapper userOperatorPlanExtDao;
	
	@Autowired
	private LaoUserOperatorPlanMapper userOperatorPlanDao;
	
	private UserStateChange userStateChange;
	
	private UserOrderProduct userOrderProduct;
	
	@Autowired
	private OperatorsMapper   operatorsDAO;
	
	@Autowired
	private TradeExtMapper tradeExtDAO;
	
	@Autowired
	private TradeService tradeService ;
	
	@Autowired
	private GoodsMapper goodsDao ;
	
	@Autowired
	private GoodsElementExtMapper goodsElementExtMapper ;
	
	@Autowired
	private OperatorPlanMapper operatorPlanMapper ;
	
	@Autowired
	private GoodsReleaseMapper goodsReleaseMapper ;
	
	@Autowired
	private LaoUserOperatorPlanMapper laoUserOperatorPlanDAO ;
	
	@Override
	public int deleteByPrimaryKey(Long msgId) {
		// TODO Auto-generated method stub
		return apiMsgLogDao.deleteByPrimaryKey(msgId);
	}

	@Override
	public int insert(ApiMsgLogDto record) {
		// TODO Auto-generated method stub
		ApiMsgLog apiMsgLog=new ApiMsgLog();
		BeanMapper.copy(record, apiMsgLog);
		return apiMsgLogDao.insert(apiMsgLog);
	}

	@Override
	public int insertSelective(ApiMsgLogDto record) {
		// TODO Auto-generated method stub
		ApiMsgLog apiMsgLog=new ApiMsgLog();
		BeanMapper.copy(record, apiMsgLog);
		return apiMsgLogDao.insertSelective(apiMsgLog);
	}

	@Override
	public ApiMsgLogDto selectByPrimaryKey(Long msgId) {
		// TODO Auto-generated method stub
		ApiMsgLog apiMsgLog = apiMsgLogDao.selectByPrimaryKey(msgId);
		ApiMsgLogDto dto=null;
		if(apiMsgLog!=null){
			dto=new ApiMsgLogDto();
			BeanMapper.copy(apiMsgLog, dto);
		}
		return dto;
	}

	@Override
	public int updateByPrimaryKeySelective(ApiMsgLogDto record) {
		// TODO Auto-generated method stub
		ApiMsgLog po=new ApiMsgLog();
		BeanMapper.copy(record, po);
		return apiMsgLogDao.updateByPrimaryKeySelective(po);
	}

	@Override
	public int updateByPrimaryKey(ApiMsgLogDto record) {
		// TODO Auto-generated method stub
		ApiMsgLog po=new ApiMsgLog();
		BeanMapper.copy(record, po);
		return apiMsgLogDao.updateByPrimaryKey(po);
	}
	/**
	 * 发送消息给kafka
	 */
	@Override
	public void sendApiMsgLog(Long id, String iccid) {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("iccid", iccid);
		map.put("id", id);
		apiMsgLogProducer.sendMessage(map);
	}
	/**
	 * 获取二次计费需要发的包
	 */
	@Override
	public List<LaoUserOperatorPlanDto> getNextPlans(String iccid) {
		// TODO Auto-generated method stub
		List<LaoUserOperatorPlan> nextPlan = userOperatorPlanExtDao.getNextPlans(iccid);
		List<LaoUserOperatorPlanDto> dtos=new ArrayList<LaoUserOperatorPlanDto>();
		for(LaoUserOperatorPlan po:nextPlan){
			LaoUserOperatorPlanDto dto=new LaoUserOperatorPlanDto();
			BeanMapper.copy(po, dto);
			dtos.add(dto);
		}
			
		return dtos;
	}
	/**
	 * 获取包月不限量要发送的包
	 */
	@Override
	public LaoUserOperatorPlanDto getNextPlan(String iccid) {
		// TODO Auto-generated method stub
		LaoUserOperatorPlan nextPlan = userOperatorPlanExtDao.getNextPlan(iccid);
		LaoUserOperatorPlanDto dto=null;
		if(nextPlan!=null){
			dto=new LaoUserOperatorPlanDto();
			BeanMapper.copy(nextPlan, dto);
			return dto;
		}
		return null;
	}
	
	@Override
	public int updateUserOperatorPlan(LaoUserOperatorPlanDto dto) {
		// TODO Auto-generated method stub
		LaoUserOperatorPlan plan=new LaoUserOperatorPlan();
		System.out.println();
		long currentTimeMillis = System.currentTimeMillis();
		BeanMapper.copy(dto, plan);
		System.out.println(System.currentTimeMillis()-currentTimeMillis);
		return userOperatorPlanDao.updateByPrimaryKeySelective(plan);
	}

	/**
	 * 订购不限量的包
	 */
	@Override
	public boolean order(LaoUserOperatorPlanDto userOperatorPlan,
			LaoUserDto laoUser, String iccid,Boolean flag) {
		// TODO Auto-generated method stub
		LaoUserOperatorPlan plan=new LaoUserOperatorPlan();
		BeanMapper.copy(userOperatorPlan, plan);
		LaoUser user=new LaoUser(); 
		BeanMapper.copy(laoUser, user);
		
		Operators op = operatorsDAO.selectByPrimaryKey(userOperatorPlan.getOperatorsId());
		userOrderProduct = (UserOrderProduct) SpringContextUtils.getBean(op.getPlanDealClass());
		//userStateChange = (UserStateChange) SpringContextUtils.getBean(op.getStatusDealClass());
		boolean isSuccess = userOrderProduct.order(plan, user, iccid);
		if(flag==true){
			if(isSuccess==true){
				LaoUserOperatorPlan maxOperatorPlan = userOperatorPlanExtDao.selectMaxOperatorPlanByTradeId(userOperatorPlan.getTradeId());
				if(maxOperatorPlan.getGoodsIndex()==userOperatorPlan.getGoodsIndex()){
					logger.info(">>>>>>>>>>>>>>>>>>>订购不限量限速流量包");
					maxOperatorPlan.setStartDate(new Date());
					maxOperatorPlan.setOpeartorsDealRst("2");
					userOperatorPlanDao.insertSelective(maxOperatorPlan);
				}
			}
		}
		return isSuccess;
	}
	/**
	 * 订购二次计费包
	 */
	@Override
	public boolean order(List<LaoUserOperatorPlanDto> userOperatorPlans,
			LaoUserDto laoUser, String iccid) {
		// TODO Auto-generated method stub
		LaoUserOperatorPlan plan=null;
		List<LaoUserOperatorPlan> list=new ArrayList<LaoUserOperatorPlan>();
		for(LaoUserOperatorPlanDto dto:userOperatorPlans){
			plan=new LaoUserOperatorPlan();
			BeanMapper.copy(dto, plan);
			list.add(plan);
		}
		
		LaoUser user=new LaoUser(); 
		BeanMapper.copy(laoUser, user);
		Operators op = operatorsDAO.selectByPrimaryKey(userOperatorPlans.get(0).getOperatorsId());
		userOrderProduct = (UserOrderProduct) SpringContextUtils.getBean(op.getPlanDealClass());
		//userStateChange = (UserStateChange) SpringContextUtils.getBean(op.getStatusDealClass());
		boolean flag = userOrderProduct.order(list, user, iccid);
		return flag;
	}
    
	/**
	 * 获取上一次订购的包
	 */
	@Override
	public LaoUserOperatorPlanDto getBeforePlan(
			LaoUserOperatorPlanDto userOperatorPlanDto) {
		// TODO Auto-generated method stub
		LaoUserOperatorPlan plan=new LaoUserOperatorPlan();
		BeanMapper.copy(userOperatorPlanDto, plan);
		LaoUserOperatorPlan beforePlan = userOperatorPlanExtDao.getBeforePlan(plan);
		LaoUserOperatorPlanDto dto=null;
		if(beforePlan!=null){
			dto=new LaoUserOperatorPlanDto();
			BeanMapper.copy(beforePlan, dto);
		}
		return dto;
	}
	
	/**
	 * 月初定时任务 获取有效期内不限量包的iccid，goodsId
	 * @return 
	 */

	@Override
	public List<Map<String, Object>> getIccidAndGoodsId() {
		// TODO Auto-generated method stub
		return userGoodsExtDao.getIccidAndGoodsId();
	}
	/**
	 * 
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getIccidAndGoodsIdByDay() {
		// TODO Auto-generated method stub
		return userGoodsExtDao.getIccidAndGoodsIdByDay();
	}

	/**
	 * 月初不限量向kafka发送消息
	 */
	@Override
	public void sendButtomlessInfo(List<Map<String, Object>> maps) {
		// TODO Auto-generated method stub
		for(Map<String, Object> map:maps){
			apiMsgLogProducer.sendMessage(map);
		}
		
	}

	@Override
	public boolean order(Long goodsId, String iccid, Long userId,Integer releaseId,Date date) {
		// TODO Auto-generated method stub
		Long tradeId= Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
		boolean	flag=false;
		try {
			List<LaoUserOperatorPlan> userOperatorPlans = addLaoUserOperatorPlan(goodsId, userId, releaseId, tradeId, date);
			Operators op = operatorsDAO.selectByPrimaryKey(userOperatorPlans.get(0).getOperatorsId());
			userOrderProduct = (UserOrderProduct) SpringContextUtils.getBean(op.getPlanDealClass());
			flag= userOrderProduct.orderGoods(userId.toString(),String.valueOf(tradeId));
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(">>>>>>>>>>月初初始化订购异常"+e.getMessage());
		}finally{
			return flag;
		}
	}
	/**
	 * 生成 laoOperatorPlan
	 * @param goodsId
	 * @param userId
	 * @param releaseId
	 * @param tradeId
	 * @param startDate
	 * @return
	 * @throws Exception
	 */
	public List<LaoUserOperatorPlan> addLaoUserOperatorPlan(Long goodsId,Long userId,Integer releaseId,Long tradeId,Date startDate)throws Exception{
		Goods goods = goodsDao.selectByPrimaryKey(goodsId);
		GoodsRelease goodsRelease = goodsReleaseMapper.selectByPrimaryKey(releaseId);
		List<GoodsElement> goodsElementList = goodsElementExtMapper.findGoodsElementByGoodsId(goodsId);
		/*TradeGoods tradeGoods = tradeGoodsExtDAO.selectByTradeId(trade.getTradeId());*/
		List<LaoUserOperatorPlan> userOperatorPlans=new ArrayList<LaoUserOperatorPlan>();
		for (int i=0;i<goodsElementList.size();i++) {
			GoodsElement goodsElement= goodsElementList.get(i);
			String elementType = goodsElement.getElementType();
			if("1".equals(elementType)){
				LaoUserOperatorPlan userOperatorPlan =  new LaoUserOperatorPlan();
				OperatorPlan operatorPlan = operatorPlanMapper.selectByPrimaryKey(goodsElement.getOriginalId());
				userOperatorPlan.setUserId(userId);
				userOperatorPlan.setStartDate(new Date());
				userOperatorPlan.setGoodsId(goodsId);
				userOperatorPlan.setGoodsIndex(goodsElement.getGoodsIndex());
				userOperatorPlan.setOperatorsId(operatorPlan.getOperators());
				userOperatorPlan.setOperatorsPid(operatorPlan.getOperatorsPid());
				userOperatorPlan.setPlanId(operatorPlan.getPlanId());
				userOperatorPlan.setPlanType(operatorPlan.getPlanType());
			    Calendar calendar=Calendar.getInstance();
			    calendar.setTime(startDate);
			    if("0".equals(goodsRelease.getUnit())){
			    	calendar.add(Calendar.MONTH, Integer.valueOf(goodsRelease.getReleaseCycle()));
			    }else{
			    	calendar.set(Calendar.DATE, Integer.valueOf(goodsRelease.getReleaseCycle()));
			    }
			    if(goodsRelease.getSilentPeriod()!=null){
			    	 calendar.add(Calendar.MONTH, Integer.valueOf(goodsRelease.getSilentPeriod()));
			    }
				userOperatorPlan.setEndDate(calendar.getTime());
				userOperatorPlan.setOpeartorsDealCode("");
				userOperatorPlan.setOpeartorsDealNum(0);
				
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
				//为出账新增字段
				userOperatorPlan.setTradeId(0l);
				userOperatorPlan.setCostPrice(goods.getGoodsPrices());
				userOperatorPlan.setBillTag("0");//0-未出账；1-已出账
				
				userOperatorPlan.setGoodsReleaseId(releaseId);
				laoUserOperatorPlanDAO.insert(userOperatorPlan);
				userOperatorPlans.add(userOperatorPlan);
			}
		}
		return userOperatorPlans;
		
	}

	@Override
	public boolean isButtomless(LaoUserOperatorPlanDto userOperatorPlan) {
		boolean flag=false;
		LaoUserOperatorPlan maxOperatorPlan = userOperatorPlanExtDao.selectMaxOperatorPlanByTradeId(userOperatorPlan.getTradeId());
		if(maxOperatorPlan.getGoodsIndex()==userOperatorPlan.getGoodsIndex()){
			flag=true;
		}
		return flag;
	}
	

}
