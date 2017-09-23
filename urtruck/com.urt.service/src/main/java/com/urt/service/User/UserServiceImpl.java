package com.urt.service.User;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sun.xml.wss.XWSSecurityException;
import com.urt.Ability.collect.UserOrderProduct;
import com.urt.Ability.collect.UserStateChange;
import com.urt.Ability.unicom.service.EditTerminalService;
import com.urt.Ability.unicom.service.GetTerminalDetailsService;
import com.urt.Ability.unicom.vo.EditTerminalResponse;
import com.urt.Ability.unicom.vo.GetTerminalDetailsResponse;
import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.IccidBatchdataDto;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoPoolDto;
import com.urt.dto.LaoPoolMemberDto;
import com.urt.dto.LaoPoolUseInfoDto;
import com.urt.dto.LaoUserGoodsDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.Goods.GoodsReleaseDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.Trade.TradeFeeSubDto;
import com.urt.dto.unicom.PaymentDto;
import com.urt.interfaces.Goods.GoodsService;
import com.urt.interfaces.Trade.TradeFeeSubService;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.remain.RemainService;
import com.urt.mapper.IccidBatchdataMapper;
import com.urt.mapper.IccidLibMapper;
import com.urt.mapper.LaoUserGoodsMapper;
import com.urt.mapper.LaoUserMapper;
import com.urt.mapper.LaoUserOperatorPlanMapper;
import com.urt.mapper.LaoUserResMapper;
import com.urt.mapper.LaoUserSvcstateMapper;
import com.urt.mapper.OperatorsMapper;
import com.urt.mapper.ServiceMapper;
import com.urt.mapper.TradeMapper;
import com.urt.mapper.ext.GoodsExtMapper;
import com.urt.mapper.ext.IccidBatchdataExtMapper;
import com.urt.mapper.ext.IccidLibExtMapper;
import com.urt.mapper.ext.LaoCustGroupPoExtMapper;
import com.urt.mapper.ext.LaoPoolExtMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.mapper.ext.LaoUserGoodsExtMapper;
import com.urt.mapper.ext.OperatorsExtMapper;
import com.urt.mapper.ext.TradeExtMapper;
import com.urt.mapper.ext.TradeOperatorPlanExtMapper;
import com.urt.mapper.ext.TradeSvcStateExtMapper;
import com.urt.miniService.authority.MiniGoodOrderServiceImpl;
import com.urt.miniService.authority.MiniUserArchivingServiceImpl;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.Goods;
import com.urt.po.IccidBatchdata;
import com.urt.po.IccidLib;
import com.urt.po.LaoPool;
import com.urt.po.LaoUser;
import com.urt.po.LaoUserGoods;
import com.urt.po.LaoUserOperatorPlan;
import com.urt.po.LaoUserSvcstate;
import com.urt.po.Operators;
import com.urt.po.Trade;
import com.urt.po.TradeOperatorPlan;
import com.urt.po.TradeSvcState;
import com.urt.service.util.WeixinUtil;
import com.urt.utils.SeqID;
import com.urt.utils.SpringContextUtils;
import com.urt.utils.ZkGenerateSeq;

@Service("userService")
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl   implements UserService{
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private IccidLibExtMapper iccidLibExtDAO;
	
	@Autowired
	LaoUserExtMapper userExtDao;
	
	@Autowired
	LaoUserGoodsExtMapper userGoodsExtDao;
	
	@Autowired
	private OperatorsMapper   operatorsDAO;
	
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
	private MiniUserArchivingServiceImpl miniUserArchivingService;
	
	@Autowired
	private IccidLibMapper iccidLibDao;
	
	@Autowired
	private TradeExtMapper tradeExtDAO ; //业务台帐主表
	
	@Autowired
	private TradeMapper tradeDAO ; //业务台帐主表
	
	@Autowired
	private TradeSvcStateExtMapper  tradeSvcStateExtDAO;// 业务台帐状态子表
	
	@Autowired
	private TradeOperatorPlanExtMapper  tradeOperatorPlanExtMapper;
	
	@Autowired
	private ServiceMapper servicerDAO;
	
	@Autowired
	private IccidLibExtMapper libExtDao;
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private TradeFeeSubService tradeFeeSubService;
	
	@Autowired
	private RemainService remainService;
	
	@Autowired
	private IccidBatchdataExtMapper iccidBatchdataExtDao;
	
	@Autowired
	private IccidBatchdataMapper iccidBatchdataDao;
	
	@Autowired
	private GetTerminalDetailsService getTerminalDetailsService;
	
	@Autowired
	private EditTerminalService editTerminalService;
	

	@Autowired
	private MiniGoodOrderServiceImpl MiniGoodOrderServiceImpl;
	
	@Autowired
	private LaoUserExtMapper laoUserExtMapper;
	
	@Autowired
	private LaoPoolExtMapper laoPoolExtMapper;

    @Autowired
    private GoodsService goodsService;
    
	@Autowired
	private GoodsExtMapper goodsExtMapper;
	
	@Autowired
	private OperatorsExtMapper operatorsExtMapper;
	
	@Autowired
	private LaoCustGroupPoExtMapper laoCustGroupPoExtMapper;
//	@Autowired
//	private UserFeeInfoService userFeeInfoService;
	
	/**
	 * 批量录入卡信息
	 */
	@Override
	public int batchInsert(List<IccidLibDto> iccidLibList) {
		
		List<IccidLib> list = null;
		int result = 0;
		if(iccidLibList != null && iccidLibList.size() > 0){
			list = new ArrayList<IccidLib>();
			for (IccidLibDto iccidLibDto : iccidLibList) {
				IccidLib iccidLib = new IccidLib();
				BeanMapper.copy(iccidLibDto, iccidLib);
				iccidLib.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
				iccidLib.setInDate(new Date());
				iccidLib.setActived("1");//录入时候默认 未激活
				list.add(iccidLib);
			}
			result = iccidLibExtDAO.batchInsert(list);
		}else{
			log.info("读取excel中的变量为null");
		}
		return result;
	}
	/**
	 * 分页查询入库的卡信息
	 */
	@Override
	public Map<String, Object> queryIccidInfo(String custid, String attribute1, String value1, String attribute2, String value2, String startIccid, String endIccid, int pageStart, int pageSize) {
		List<IccidLibDto> list = new ArrayList<IccidLibDto>();
		List<IccidLib> iccidlist = iccidLibExtDAO.doQueryList(custid, attribute1, value1, attribute2, value2, startIccid, endIccid, pageStart, pageStart+pageSize);
		if(iccidlist != null && iccidlist.size() > 0){
			for (IccidLib iccidLib : iccidlist) {
				IccidLibDto iccidLibDto = new IccidLibDto();
				BeanMapper.copy(iccidLib,iccidLibDto);
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
				iccidLibDto.setInTime(sdf.format(iccidLib.getInDate()));
				list.add(iccidLibDto);
			}
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", list);
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", iccidLibExtDAO.countAmount(custid, attribute1, value1, attribute2, value2, startIccid, endIccid));//总记录数 
		return resultMap;
	}
	
	/**
	 * 查询符合条件所有的卡信息
	 */
	@Override
	public List<IccidLibDto> queryIccids(String custid, String attribute1, String value1, String attribute2, String value2, String startIccid, String endIccid) {
		List<IccidLibDto> list = null;
		int total = iccidLibExtDAO.countAmount(custid, attribute1, value1, attribute2, value2, startIccid, endIccid);
		List<IccidLib> iccidlist = iccidLibExtDAO.doQueryList(custid, attribute1, value1, attribute2, value2, startIccid, endIccid, 0, total);
		if(iccidlist != null && iccidlist.size() > 0){
			list = new ArrayList<IccidLibDto>();
			for (IccidLib iccidLib : iccidlist) {
				IccidLibDto iccidLibDto = new IccidLibDto();
				BeanMapper.copy(iccidLib,iccidLibDto);
				list.add(iccidLibDto);
			}
		}
		return list;
	}
	/**
	 * 用户归档操作
	 */
	@Override
	public String userArchiving(String tradeId) {
		String iccid = null;
		boolean flag = true;
		
		//根据 tradeId 在trade的服务状态表或服务套餐表 中查询得到运营商ID ，得到对应运营商  产品订购和服务状态变更 处理类********************
		TradeSvcState tradeSvcState = tradeSvcStateExtDAO.selectByTradeId(Long.parseLong(tradeId));
		UserStateChange userStateChange = null;
		UserOrderProduct userOrderProduct = null;
		if(tradeSvcState != null){
			com.urt.po.Service sevice = servicerDAO.selectByPrimaryKey(tradeSvcState.getServiceId());
			if(sevice != null){
				Operators op = operatorsDAO.selectByPrimaryKey(sevice.getOperatorsId());
				if(!StringUtils.isBlank(op.getStatusDealClass()))
				userStateChange = (UserStateChange) SpringContextUtils.getBean(op.getStatusDealClass());
				userOrderProduct = (UserOrderProduct) SpringContextUtils.getBean(op.getPlanDealClass());
			}else{
				log.debug("/***************用户归档操作**********service没有查询到");
			}
		}else{//如果是订购流程，服务状态不需要修改
			List<TradeOperatorPlan> ls=tradeOperatorPlanExtMapper.selectByTradeId(Long.parseLong(tradeId));
			if(ls==null || ls.size() ==0)return "operatorServiceFailed";
			Operators op = operatorsDAO.selectByPrimaryKey(ls.get(0).getOperatorsId());
			userOrderProduct = (UserOrderProduct) SpringContextUtils.getBean(op.getPlanDealClass());
			userStateChange = (UserStateChange) SpringContextUtils.getBean(op.getStatusDealClass());
		}
		
		Trade trade = tradeExtDAO.selectByTradeId(Long.parseLong(tradeId));
		if(trade != null){
			iccid = trade.getIccid();
		}
		
		//用户中心相关表插入数据
		miniUserArchivingService.insertLaoUser(tradeId);
		miniUserArchivingService.insertLaoUserGoods(tradeId);
		LaoUserSvcstate userSvcstate = miniUserArchivingService.insertLaoUserSvcstate(tradeId);
		List<LaoUserOperatorPlan> userOpratorPlanList = miniUserArchivingService.insertLaoUserOperatorPlan(tradeId);
		
		//判断是否在维   如果在维 下面的步骤不做
		if(StringUtils.isNotBlank(trade.getIfMaintenance()) && trade.getIfMaintenance().equals("0")){        //0:正常  1:在维
			//如果有套餐，需要执行套餐订购流程 
			if(userOpratorPlanList != null && userOpratorPlanList.size() > 0){
				//订购流程
				if(trade != null && trade.getUserId() != null){
					// 浙江移动接口 无须走订购套餐流程
					IccidLib iccidLib = libExtDao.selectByIccid(iccid);
					if ("5".equals(iccidLib.getOperators())) {
						flag = true;
					} else {
						flag = userOrderProduct.orderGoods(trade.getUserId().toString(),tradeId);
					}
				}else{
					log.info(iccid+">>>>>>>>>>>>>>>订购失败:原因userId获取失败");
					flag = false;
				}
				//更新trade表
				if(flag){
					trade.setSubscribeState("3");
					trade.setFinishDate(new Date());
					tradeDAO.updateByPrimaryKey(trade);
				}
			}
			if(flag){
				if(tradeSvcState==null || userSvcstate ==null){  //如果服务状态不需要变更，直接返回
					//更新trade表
					trade.setSubscribeState("3");
					trade.setFinishDate(new Date());
					tradeDAO.updateByPrimaryKey(trade);
					return "ok";
				}
				//调用业务开通和状态变更相关参数和方法
				flag = userStateChange.stateChange(iccid, userSvcstate.getUserId(), userSvcstate.getServiceId(), userSvcstate.getStartDate(), userSvcstate.getStateCode(),tradeId);
				IccidLib lib = libExtDao.selectByIccid(iccid);
				if("1".equals(lib.getOperators())){
					/**
					 * wangxb20 20170622  begin如果卡在已停用情况下做的订购需要切换为已激活
					 */
					String status=null;
					try {
						status = getCountId(iccid);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SOAPException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (XWSSecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(120 == trade.getTradeTypeCode() && "DEACTIVATED_NAME".equals(status)){
						String messageId = ZkGenerateSeq.getIdSeq(SeqID.USER_ID);
						SOAPMessage sMessage=null;
						try {
							sMessage = editTerminalService.call(iccid,messageId,"ACTIVATED_NAME","3");
						}catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						EditTerminalResponse resp = (EditTerminalResponse) editTerminalService.parseMessage(sMessage);
						System.out.println("*****************停机到已激活的切换*************");
					}
				}
				
				//-------end------------------------------
				
				if(flag){
					//如果激活成功，更新卡的状态
					IccidLib iccidLib = libExtDao.selectByIccid(iccid);
					if(iccidLib != null && iccidLib.getActived().equals("1")){
						iccidLib.setActived("0");  //0成功1失败
						libExtDao.updateByIccid(iccidLib);
					}
					
					//更新trade表
					trade.setSubscribeState("3");
					trade.setFinishDate(new Date());
					tradeDAO.updateByPrimaryKey(trade);
					return "ok";
				}else{
					//更新trade表
					trade.setSubscribeState("4");
					trade.setFinishDate(new Date());
					tradeDAO.updateByPrimaryKey(trade);
					return "activefailed";
				}
			}else{
				//更新trade表
				trade.setSubscribeState("4");
				trade.setFinishDate(new Date());
				tradeDAO.updateByPrimaryKey(trade);
				return "orderfailed";
			}
		}else{
			//如果激活成功，更新卡的状态
			IccidLib iccidLib = libExtDao.selectByIccid(iccid);
			if(iccidLib != null && iccidLib.getActived().equals("1")){
				iccidLib.setActived("0");  //0成功1失败
				libExtDao.updateByIccid(iccidLib);
			}
			
			//更新trade表
			trade.setSubscribeState("3");
			trade.setFinishDate(new Date());
			tradeDAO.updateByPrimaryKey(trade);
			return "maintenance";
		}
	}
	@Override
	public IccidLibDto selectByIccid(String iccid) {
		// TODO Auto-generated method stub
		IccidLibDto dto= null;
		IccidLib selectByIccid = iccidLibExtDAO.selectByIccid(iccid);
		if(selectByIccid != null){
			dto = new IccidLibDto();
			BeanMapper.copy(selectByIccid, dto);
		}
		return dto;
	}
	@Override
	public int getCardCount() {
		// TODO Auto-generated method stub
		return iccidLibExtDAO.getCount();
	}
	@Override
	public List<Map<String, Object>> queryCardInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return iccidLibExtDAO.queryCardInfo(map);
	}
	@Override
	public int updateByPrimaryKey(IccidLibDto record) {
		// TODO Auto-generated method stub
		IccidLib iccid=new IccidLib();
		BeanMapper.copy(record, iccid);
		return iccidLibDao.updateByPrimaryKey(iccid);
		
	}
	@Override
	public int getUserCount() {
		// TODO Auto-generated method stub
		return userExtDao.getUserCount();
	}
	@Override
	public List<Map<String, Object>> queryUserInfo(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return userExtDao.queryUserInfoByCtcc(param);
	}
	@Override
	public int updateUser(LaoUserDto record) {
		// TODO Auto-generated method stub
		LaoUser user=new LaoUser();
		BeanMapper.copy(record, user);
		return laoUserDAO.updateByPrimaryKeySelective(user);
	}
	@Override
	public int updateUserStatus(LaoUserDto record) {
		LaoUser user=new LaoUser();
		BeanMapper.copy(record, user);
		laoUserDAO.updateByPrimaryKeySelective(user);
		// 判断lao_user_svcstate是否存在user
		LaoUserSvcstate laoUserSvcstate = laoUserSvcstateDAO.selectByUserId(record.getUserId());
		if (laoUserSvcstate != null) {
			// 将当前状态失效 end_date 当前时间
			laoUserSvcstate.setEndDate(new Date());
			laoUserSvcstateDAO.updateByPrimaryKeySelective(laoUserSvcstate);
		}
		// 新增一条记录 start_date 当前时间
		LaoUserSvcstate userSvcstate = new LaoUserSvcstate();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
		try {
			userSvcstate.setEndDate(sdf.parse("20501231235959"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		userSvcstate.setOpeartorsDealCode("0");//业务处理返回码
		userSvcstate.setOpeartorsDealNum(1);//业务处理次数
		userSvcstate.setOpeartorsDealRst("0");//业务处理结果
		userSvcstate.setServiceId(record.getOperatorsId());
		// 当前时间加一秒
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 1);
		userSvcstate.setStartDate(cal.getTime());
		userSvcstate.setStateCode(record.getStateCode());
		userSvcstate.setUpdateTime(new Date());
		userSvcstate.setUserId(record.getUserId());
		return laoUserSvcstateDAO.insertSelective(userSvcstate);
	}
	
	@Override
	public LaoUserDto getLaoUserDtoByIccid(String iccid) {
		LaoUserDto dto=null;
		LaoUser selectByIccid = userExtDao.selectByIccid(iccid);
		if( selectByIccid != null){
			dto = new LaoUserDto();
			BeanMapper.copy(selectByIccid, dto);
		}
		return dto;
	}
	@Override
	public boolean ifOrderProduct(String iccid, String goodsId) {
		IccidLib ifOrderProduct = iccidLibExtDAO.ifOrderProduct(iccid, goodsId);
		if(ifOrderProduct != null){
			return true;
		}
		return false;
	}
	@Override
	public boolean hasPermission(String iccid, String custid) {
		boolean flag = false;
		LaoUserDto laoUserDto = getLaoUserDtoByIccid(iccid);
		IccidLibDto iccidDto = selectByIccid(iccid);
		if(laoUserDto != null){
			if(StringUtils.isNotBlank(custid) && laoUserDto.getChannelCustId() != null && custid.equals(laoUserDto.getChannelCustId().toString())){
				flag = true;
			}else if(StringUtils.isNotBlank(custid) && laoUserDto.getCustId() != null && custid.equals(laoUserDto.getCustId().toString())){
				flag = true;
			}
		}else if(iccidDto != null){
			if(StringUtils.isNotBlank(custid) && custid.equals(iccidDto.getCustid())){
				flag = true;
			}
		}
		return flag;
	}
	@Override
	public String getCustIdByIccid(String iccid) {
		return userExtDao.getCustIdByIccid(iccid);
	}
	@Override
	public List<Map<String, Object>> queryUserInfoByColony(
			Map<String, Object> param) {
		
		return userExtDao.queryUserInfoByColony(param);
	}
	@Override
	public int getCardCountByColony() {
		
		return userExtDao.getUserCountByColony();
	}
	@Override
	public LaoUserDto getLaoUserDtoByMsisdn(String msisdn) {
		LaoUserDto dto=null;
		if(userExtDao.selectByMsisdn(msisdn) != null){
			dto = new LaoUserDto();
			BeanMapper.copy(userExtDao.selectByMsisdn(msisdn), dto);
		}
		return dto;
	}
	@Override
	public List<GoodsDto> getGoodsByIccid(String iccid) {
		// TODO Auto-generated method stub
		List<Goods> goods = userGoodsExtDao.getGoodsListByIccid(iccid);
		List<GoodsDto> goodsDtos=new ArrayList<GoodsDto>();
		for(Goods good:goods){
			GoodsDto dto=new GoodsDto();
			BeanMapper.copy(good, dto);
			goodsDtos.add(dto);
		}
		return goodsDtos;
	}
	@Override
	public String callBackOfWeixin(String payOrderId, String custId, String tradeId) {
		// 扣费
		TradeFeeSubDto tradeFeeSubDto;
		try {
			tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
			if(StringUtils.isNotBlank(custId) && tradeFeeSubDto != null){
				HashMap<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("channelCustId", custId);// 渠道客户ID
				paraMap.put("tradeTypeCode", "120");// 业务类型编码
				paraMap.put("recvFee",WeixinUtil.getMoney(tradeFeeSubDto.getFee()));// 缴费金额
				paraMap.put("fee", WeixinUtil.getMoney(tradeFeeSubDto.getOldfee()));// 应收费用
				paraMap.put("discntFee", WeixinUtil.getMoney(tradeFeeSubDto.getFee()));// 优惠后费用
				paraMap.put("realFee", WeixinUtil.getMoney(tradeFeeSubDto.getFee()));// 实收费用
				paraMap.put("payTag", "1");// 0：未缴费 1：用户已缴费 2：客户已缴费
				paraMap.put("payOrderId", payOrderId);
				paraMap.put("payDate", new Date());
				paraMap.put("tradeId", tradeId);
				paraMap.put("goodsId", tradeFeeSubDto.getGoodsId());
				if(StringUtils.isNotBlank(tradeId)){
					TradeDto trade = tradeService.queryTradeId(Long.parseLong(tradeId));
					if(trade != null) paraMap.put("userId", trade.getUserId());
				}
				
				int rstFlg = remainService.charge(paraMap);// 扣费
				
				PaymentDto payment = new PaymentDto();
				payment.setOrderId(payOrderId);//订单号
				payment.setPayType(2);
				tradeFeeSubService.changePayTag(tradeId, payment);
				//用户归档操作
				if(rstFlg == 0){
					String result = userArchiving(tradeId);// 用户归档
					log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>微信回调用户归档:" + result);
					if(("ok").equals(result)){
						return WeixinUtil.sendXml("SUCCESS", "OK");
					}else{
						return WeixinUtil.sendXml("FAIL", "用户归档操作失败");
					}
				}else{
					return WeixinUtil.sendXml("FAIL", "扣费操作失败");
				}
			}else{//默认h5的直接返回 操作成功
				return WeixinUtil.sendXml("SUCCESS", "OK");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, Object> selectByPage(IccidBatchdataDto dto, int pageNo,
			int pageSize) {
	    Page<IccidBatchdata> deviceRelExtpage = new Page<IccidBatchdata>();
		deviceRelExtpage.setPageNo(pageNo);
		deviceRelExtpage.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (IccidBatchdata) ConversionUtil.dto2po(dto, IccidBatchdata.class));
		deviceRelExtpage.setParams(params);
		List<IccidBatchdata> deviceRelList = iccidBatchdataExtDao.selectByPage(deviceRelExtpage);
		@SuppressWarnings("unchecked")
		List<IccidBatchdataDto> deviceRelDtoList = ConversionUtil.poList2dtoList(deviceRelList, IccidBatchdataDto.class);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", deviceRelDtoList);
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", deviceRelExtpage.getTotalRecord());//总记录数 
		return resultMap;
	}
	@Override
	public Map<String, Object> selectDetailByPage(IccidLibDto dto, int pageNo,
			int pageSize) {
	    Page<IccidLib> deviceRelExtpage = new Page<IccidLib>();
		deviceRelExtpage.setPageNo(pageNo);
		deviceRelExtpage.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (IccidLib) ConversionUtil.dto2po(dto, IccidLib.class));
		deviceRelExtpage.setParams(params);
		List<IccidLib> deviceRelList = libExtDao.selectDetailByPage(deviceRelExtpage);
		@SuppressWarnings("unchecked")
		List<IccidLibDto> deviceRelDtoList = ConversionUtil.poList2dtoList(deviceRelList, IccidLibDto.class);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", deviceRelDtoList);
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", deviceRelExtpage.getTotalRecord());//总记录数 
		return resultMap;
	}
	@Override
	public int insertSelective(IccidBatchdataDto record) {
		IccidBatchdata recordNew = null;
		if (record != null) {
			recordNew = new IccidBatchdata();
			BeanMapper.copy(record, recordNew);
		}
		return iccidBatchdataDao.insertSelective(recordNew);
	}
	@Override
	public LaoUserGoodsDto getUserGoodsByIccid(String iccid) {
		// TODO Auto-generated method stub
		LaoUserGoods userGoods = userGoodsExtDao.getUserGoodsByIccid(iccid);
		LaoUserGoodsDto dto = new LaoUserGoodsDto();
		if(userGoods!=null){
			dto=new LaoUserGoodsDto();
			BeanMapper.copy(userGoods, dto);
		}
		return dto;
	}
	@Override
	public IccidBatchdataDto selectByBatchId(Long batchId) {
		IccidBatchdataDto dto = null;
		IccidBatchdata po = iccidBatchdataDao.selectByPrimaryKey(batchId);
		if (po != null) {
			dto = new IccidBatchdataDto();
			BeanMapper.copy(po, dto);
		}
		return dto;
	}
	@Override
	public Map<String, String> selectOneDetailByBatchId(String batchId) {
		return iccidLibExtDAO.selectOneDetailByBatchId(batchId);
	}
	
	@Override
	public List<String> getIccidByCustId(Long custId) {
		return userExtDao.getIccidByCustId(custId);
	}
	
	@Override
	public List<String> getAllIccidByCustId(Long custId) {
		return userExtDao.getAllIccidByCustId(custId);
	}
	@Override
	public String getIccid(String end) {
		return userExtDao.getIccid(end);
	}
	@Override
	public List<Map<String, Object>> selectDetailByBatchId(String batchId) {
		
		return iccidLibExtDAO.selectDetailByBatchId(batchId);
	}
	private String getCountId(String iccid) throws IllegalArgumentException, SOAPException, IOException, XWSSecurityException{
		SOAPMessage request = null;		
		SOAPMessage response = null;
		GetTerminalDetailsResponse responseFirst = null;
		List<String> list = new ArrayList<String>();
		list.add(iccid);
		
		request= getTerminalDetailsService.createRequest(list,"lmh-test-terminal-detail");
		SOAPMessage createRequestFirst = getTerminalDetailsService.createRequest(list,"lmh-test-terminal-detail");
		request = getTerminalDetailsService.secureMessage(createRequestFirst, getTerminalDetailsService.getUsername(), getTerminalDetailsService.getPasswd());	
		SOAPConnection createConnection = getTerminalDetailsService.getConnectionFactory().createConnection();
		response = createConnection.call(request, new URL(getTerminalDetailsService.getUrl()));
		responseFirst = (GetTerminalDetailsResponse) getTerminalDetailsService.parseMessage(response);
		String status = responseFirst.getList().get(0).getStatus();
		//String userFlow = responseFirst.getList().get(0).getMonthToDateUsage();
		/*Map<String,String> map = new HashMap<String,String>();
		map.put("accountId", accountId);
		map.put("userFlow", userFlow);*/
		return status;
	}
	@Override
	public Boolean userArchivingByNio(String tradeId) {
		String iccid = null;
		boolean flag = true;
		//根据 tradeId 在trade的服务状态表或服务套餐表 中查询得到运营商ID ，得到对应运营商  产品订购和服务状态变更 处理类********************
		TradeSvcState tradeSvcState = tradeSvcStateExtDAO.selectByTradeId(Long.parseLong(tradeId));
		UserStateChange userStateChange = null;
		if(tradeSvcState != null){
			com.urt.po.Service sevice = servicerDAO.selectByPrimaryKey(tradeSvcState.getServiceId());
			if(sevice != null){
				Operators op = operatorsDAO.selectByPrimaryKey(sevice.getOperatorsId());
				if(!StringUtils.isBlank(op.getStatusDealClass()))
				userStateChange = (UserStateChange) SpringContextUtils.getBean(op.getStatusDealClass());
			}else{
				log.debug("/***************用户归档操作**********service没有查询到");
			}
		}
		Trade trade = tradeExtDAO.selectByTradeId(Long.parseLong(tradeId));
		//用户中心相关表插入数据
		miniUserArchivingService.insertLaoUser(tradeId);
		miniUserArchivingService.insertLaoUserGoods(tradeId);
		LaoUserSvcstate userSvcstate = miniUserArchivingService.insertLaoUserSvcstate(tradeId);
		if(tradeSvcState==null || userSvcstate ==null){  //如果服务状态不需要变更，直接返回
			//更新trade表
			trade.setSubscribeState("3");
			trade.setFinishDate(new Date());
			tradeDAO.updateByPrimaryKey(trade);
			return flag;
		}
		//调用业务开通和状态变更相关参数和方法
		flag = userStateChange.stateChange(iccid, userSvcstate.getUserId(), userSvcstate.getServiceId(), userSvcstate.getStartDate(), userSvcstate.getStateCode(),tradeId);
		return flag;
	}
	
	
	@Override
    public int userTestCycle(String iccid, String msisdn) throws Exception {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("iccid", iccid);
        param.put("msisdn", msisdn);
    
        IccidLib lib = iccidLibExtDAO.selectByMap(param);       
        if (lib == null) {
            log.info("UserServiceImpl.userTestCycle iccid:" + iccid + " msisdn:" + msisdn + " find IccidLib is NULL!");
            return -2;
        }
        param.put("chCustId", lib.getCustid());
        LaoUser record=insertLaoUser(lib,param);
        if (record == null) {
            log.info("UserServiceImpl.userTestCycle Laouser record is NULL!");
            return -2;
        }
        List<GoodsReleaseDto> goodsList = new ArrayList<GoodsReleaseDto>();
        List<String> tradeList = new ArrayList<String>();
        LaoUserDto userDto = new LaoUserDto();
        IccidLibDto libDto = new IccidLibDto();
        BeanMapper.copy(record, userDto);
        BeanMapper.copy(lib, libDto);
        //BeanMapper.copy(record, userDto);
        log.info("UserServiceImpl.userTestCycle  begin insertUserGoods iccid:" + iccid + " msisdn:" + msisdn );
        goodsList=goodsService.insertUserGoods(libDto, userDto);
        // åˆ›å»ºè®¢å•ï¼Œç®—è´¹
        for (GoodsReleaseDto userGoods : goodsList) { 
            log.info("UserServiceImpl.userTestCycle  begin creatTrade iccid:" + iccid + " msisdn:" + msisdn );
            String tradeId = tradeService.creatTrade(String.valueOf(record.getChannelCustId()), null, param,
                    String.valueOf(userGoods.getGoodsId()), String.valueOf(userGoods.getGoodsReleaseId()), "100",
                    lib.getIfMaintenance());
            log.info("UserServiceImpl.userTestCycle  begin addLaoUserOperatorPlan iccid:" + iccid + " msisdn:" + msisdn +" tradeId:"+tradeId);
            goodsService.addLaoUserOperatorPlan(userGoods.getGoodsId(),record.getUserId(),userGoods.getGoodsReleaseId(),Long.valueOf(tradeId),true);
            TradeDto tradeDto = tradeService.queryTradeId(Long.parseLong(tradeId));
            if (userGoods.getReleasePrice() != null && Double.parseDouble(userGoods.getReleasePrice()) > 0) {
                tradeFeeSubService.addTradeFeeSub(tradeDto);// ç®—è´¹
                TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
                tradeDto.setFee(tradeFeeSubDto.getFee());
                tradeService.updateTrade(tradeDto);
                tradeList.add(tradeId);
            }
	
	
        }
        // ç”¨æˆ·çŠ¶æ€ ä¿®æ”¹ï¼Œ
        return 0;
    }
	
	 private LaoUser insertLaoUser(IccidLib lib,Map<String, Object> param) {
	        LaoUser user = null;      
	        user = userExtDao.selectByMap(param);
	        Date inDate = new Date();
	        if (user == null) {
	            user = new LaoUser();
	            user.setChannelCustId(Long.valueOf(lib.getCustid()));
	            user.setIccid(lib.getIccid());
	            user.setMsisdn(lib.getMsisdn());
	            user.setInDate(inDate);
	            user.setOpenDate(inDate);
	            user.setUserId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.USER_ID)));
	            user.setOperatorsId(Integer.valueOf(lib.getOperators()));
	            user.setAttribute1(lib.getAttribute1());
	            user.setAttribute2(lib.getAttribute2());
	            user.setValue1(lib.getValue1());
	            user.setValue2(lib.getValue2());
	            user.setRemoveTag(lib.getCycleState());
	            user.setTestStartDate(inDate);
	            user.setVic(lib.getVic());
	            user.setTestCycle(lib.getTestCycle());
	            user.setImsi(lib.getImsi());
	            user.setStateCode("25");
	            user.setScoreValue(0L);
	            user.setCreditClass(0);
	            user.setBasicCreditValue(0L);
	            user.setCreditClass(0);
	            user.setAcctTag("0");        
	            laoUserDAO.insertSelective(user);
	            return user;
	        }
	        return user;
	    }
	@Override
	public Map<String, Object> queryPage(LaoUserDto dto, int pageNo, int pageSize) {
		Map<String, Object> map=MiniGoodOrderServiceImpl.queryPage(dto, pageNo, pageSize);
		return map;
	}
	@Override
	public int batchInsertTSP(List<IccidLibDto> iccidLibList) {
		List<IccidLib> list = null;
		int result = 0;
		if(iccidLibList != null && iccidLibList.size() > 0){
			list = new ArrayList<IccidLib>();
			for (IccidLibDto iccidLibDto : iccidLibList) {
				
				//åˆ›å»ºå¯¹è±¡
				IccidLib iccidLib = new IccidLib();
				BeanMapper.copy(iccidLibDto, iccidLib);
				iccidLib.setAttribute1(" ");
				iccidLib.setAttribute2(" ");
				iccidLib.setAtt1(" ");
				iccidLib.setAtt2(" ");
				//å½•å…¥æ—¶å€™é»˜è®¤ æœªæ¿€æ´»
				iccidLib.setActived("0");
				//è®¾ç½®æ˜¯å¦åœ¨ç»´
				iccidLib.setIfMaintenance("no data");
				//è®¾ç½®å¡çŠ¶æ€
				iccidLib.setCardstatus("nodata");
				//è®¾ç½®ID
				Long id=Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
				iccidLib.setId(id);
				//è®¾ç½®ç”Ÿå‘½å‘¨æœŸçŠ¶æ€ï¼Œé»˜è®¤æ˜¯æµ‹è¯•æœŸ
				iccidLib.setCycleState("7");
				//è®¾ç½®å…¥è¡¨æ—¥æœŸ
				Date date=new Date();
				iccidLib.setInDate(date);
				//è®¾ç½®æµ‹è¯•å‘¨æœŸ
				Short testcycle=iccidLib.getTestCycle();
				if("".equals(testcycle.toString()) || testcycle==null){
					iccidLib.setTestCycle((short)6);
				}
				//è®¾ç½®SIMé‡‡è´­å•å·
				String buyOrderNo=iccidLib.getBuyOrderNo();
				if("".equals(buyOrderNo) || buyOrderNo==null){
					iccidLib.setBuyOrderNo("no data");
				}
				//è®¾ç½®MSISDN
				String msisdn=iccidLib.getMsisdn();
				if("".equals(msisdn) || msisdn==null){
					iccidLib.setMsisdn("no data");
				}
				//è®¾ç½®Ctypeå¡ç±»åž‹
				String ctype=iccidLib.getCtype();
				if("".equals(ctype) || ctype==null){
					iccidLib.setCtype("no data");
				}
				list.add(iccidLib);
				log.info("iccidLib---------------------"+iccidLib);
			}
			result = iccidLibExtDAO.batchInsertTSP(list);
		}else{
			log.info("è¯»å–excelä¸­çš„å˜é‡ä¸ºnull");
		}
		return result;
	}
	@Override
	public List<LaoPoolDto> queryPoolName() {
		List<LaoPoolDto> namelist=new ArrayList<LaoPoolDto>();
		List<LaoPool> laopool=laoPoolExtMapper.queryPoolNameMethod();
		for(LaoPool i:laopool){
			LaoPoolDto poolDto=new LaoPoolDto();
			BeanMapper.copy(i, poolDto);
			namelist.add(poolDto);
		}
		return namelist;
	}
	/*@Override
	public Map<String, Object> selectUserByPage(LaoUserDto dto,
			int pageNo, int pageSize) {
		Map<String, Object> map=miniUserServiceImpl.queryPage(dto, pageNo, pageSize);
		return map;
	}*/
	@Override
	public Map<String, Object> selectDetailByPageTSP(IccidLibDto dto, int pageNo,
			int pageSize) {
	    Page<IccidLib> deviceRelExtpage = new Page<IccidLib>();
		deviceRelExtpage.setPageNo(pageNo);
		deviceRelExtpage.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (IccidLib) ConversionUtil.dto2po(dto, IccidLib.class));
		deviceRelExtpage.setParams(params);
		List<IccidLib> deviceRelList = libExtDao.selectDetailByPageTSP(deviceRelExtpage);
		@SuppressWarnings("unchecked")
		List<IccidLibDto> deviceRelDtoList = ConversionUtil.poList2dtoList(deviceRelList, IccidLibDto.class);
		for(IccidLibDto i:deviceRelDtoList){
			//æ­£å¼æœŸäº§å“åç§°
			String initproname=goodsExtMapper.queryLaoGoodsName(i.getInitproduct());
			i.setInitproductname(initproname);
			//æµ‹è¯•æœŸäº§å“åç§°
			String testproname=goodsExtMapper.queryLaoGoodsName(i.getTestGoodsRlsId().toString());
			i.setTestGoodsRlsIdname(testproname);
			//è¿è¥å•†åç§°
			String opername=operatorsExtMapper.queryOperatorName(i.getOperators());
			i.setOperatorsName(opername);
			//éƒ¨é—¨åç§°
			String custName=laoCustGroupPoExtMapper.queryCustname(i.getCustid());
			i.setCustName(custName);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", deviceRelDtoList);
		resultMap.put("iTotalRecords", pageSize);//å½“å‰é¡µåŒ…å«çš„è®°å½•æ•°
		resultMap.put("iTotalDisplayRecords", deviceRelExtpage.getTotalRecord());//æ€»è®°å½•æ•° 
		return resultMap;
	}
	@Override
	public Map<String, Object> poolMenberInfo(LaoPoolMemberDto dto, int pageNo, int pageSize) {
		Map<String, Object> map=MiniGoodOrderServiceImpl.poolMenberInfoMe(dto, pageNo, pageSize);
		return map;
	}
	@Override
	public Map<String, Object> poolUseInfo(LaoPoolUseInfoDto dto, int pageNo, int pageSize) {
		Map<String, Object> map=MiniGoodOrderServiceImpl.poolUseInfoMe(dto, pageNo, pageSize);
		return map;
	}
	@Override
	public Map<String, Object> querypoolInfo(LaoPoolDto dto, int pageNo, int pageSize) {
		Map<String, Object> map=MiniGoodOrderServiceImpl.querypoolInfoMe(dto, pageNo, pageSize);
		return map;
	}
	//批量销卡
	@Override
	public Map<String, Object> updateByIccids(List<String> iccidList) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int  resultInt= userExtDao.updateByIccids(iccidList);
		if(resultInt==iccidList.size()){
			resultMap.put("msg","销卡成功");
		}
		return resultMap;
	}
	@Override
	public Map<String, Object> selectSimDetails(String iccid) {
		Map<String, Object> resultMap = userExtDao.selectDetaisByIccid(iccid);
		return resultMap;
	}
}
