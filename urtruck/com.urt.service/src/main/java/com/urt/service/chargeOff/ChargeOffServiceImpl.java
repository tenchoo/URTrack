package com.urt.service.chargeOff;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.LaoCustomerDto;
import com.urt.dto.LaoSsStaticDto;
import com.urt.dto.LaoUserOperatorPlanDto;
import com.urt.dto.Goods.OperatorPlanDto;
import com.urt.dto.Goods.OperatorsDto;
import com.urt.dto.Remain.LaoBPaylogDto;
import com.urt.dto.chargeOff.LaoOperatorsBillDto;
import com.urt.dto.chargeOff.LaoOperatorsCycleDto;
import com.urt.dto.chargeOff.LaoOperatorsbillResultDto;
import com.urt.dto.chargeOff.LaoUserfeeInfoDto;
import com.urt.interfaces.Goods.OperatorsService;
import com.urt.interfaces.chargeOff.ChargeOffService;
import com.urt.mapper.LaoCustomerPoMapper;
import com.urt.mapper.LaoOperatorsBillMapper;
import com.urt.mapper.LaoOperatorsCycleMapper;
import com.urt.mapper.LaoOperatorsbillResultMapper;
import com.urt.mapper.LaoUserOperatorPlanMapper;
import com.urt.mapper.LaoUserbillsDetailMapper;
import com.urt.mapper.LaoUserfeeInfoMapper;
import com.urt.mapper.ext.LaoBPaylogExtMapper;
import com.urt.mapper.ext.LaoCustomerPoExtMapper;
import com.urt.mapper.ext.LaoOperatorPlanExtMapper;
import com.urt.mapper.ext.LaoOperatorsBillExtMapper;
import com.urt.mapper.ext.LaoOperatorsCycleExtMapper;
import com.urt.mapper.ext.LaoOperatorsbillResultExtMapper;
import com.urt.mapper.ext.LaoSsStaticPoExtMapper;
import com.urt.mapper.ext.LaoUserOperatorPlanExtMapper;
import com.urt.mapper.ext.LaoUserbillsDetailExtMapper;
import com.urt.mapper.ext.LaoUserfeeInfoExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.msgProducter.trade.BillingImportProducer;
import com.urt.msgProducter.trade.ChargeOffProducer;
import com.urt.po.LaoBPaylog;
import com.urt.po.LaoBPaylogPo;
import com.urt.po.LaoCustomerPo;
import com.urt.po.LaoOperatorsBill;
import com.urt.po.LaoOperatorsCycle;
import com.urt.po.LaoOperatorsbillResult;
import com.urt.po.LaoSsStaticPo;
import com.urt.po.LaoUserOperatorPlan;
import com.urt.po.LaoUserbillsDetail;
import com.urt.po.LaoUserfeeInfo;
import com.urt.po.OperatorPlan;
import com.urt.service.util.WeixinUtil;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

/**
* 功能描述：出账实现类
* 类名：ChargeOffServiceImpl 
* @author sunhao
* @date 2017年3月7日
 */
@Service("chargeOffService")
@Transactional(propagation = Propagation.REQUIRED)
public class ChargeOffServiceImpl implements ChargeOffService {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private LaoOperatorsBillExtMapper laoOperatorsBillExtDAO;
	
	@Autowired
	private LaoOperatorsBillMapper laoOperatorsBillDAO;
	
	@Autowired
	private LaoOperatorsCycleExtMapper laoOperatorsCycleExtDAO;
	
	@Autowired
	private LaoOperatorsCycleMapper laoOperatorsCycleDAO;
	
	@Autowired
	private ChargeOffProducer chargeOffProducer;
	
	@Autowired
	private BillingImportProducer billingImportProducer;
	
	@Autowired
	private LaoUserOperatorPlanExtMapper laoUserOperatorPlanExtDAO;
	
	@Autowired
	private LaoUserOperatorPlanMapper laoUserOperatorPlanDAO;
	
	@Autowired
	private LaoUserfeeInfoExtMapper laoUserfeeInfoExtDAO;
	
	@Autowired
	private LaoUserfeeInfoMapper laoUserfeeInfoDAO;
	
	@Autowired
	private LaoUserbillsDetailMapper laoUserbillsDetailDAO;
	
	@Autowired
	private LaoOperatorsbillResultExtMapper laoOperatorsbillResultExtDAO;
	
	@Autowired
	private LaoOperatorsbillResultMapper laoOperatorsbillResultDAO;
	
	@Autowired
	private OperatorsService operatorService;
	
	@Autowired
	private LaoCustomerPoMapper laoCustomerPoDAO;
	
	@Autowired
	private LaoOperatorPlanExtMapper laoOperatorPlanExtDAO;
	
	@Autowired
	private LaoCustomerPoExtMapper laoCustomerPoExtDAO;
	
	@Autowired
	private LaoBPaylogExtMapper laoBPaylogExtDAO;
	
	@Autowired
	private LaoSsStaticPoExtMapper laoSsStaticPoExtDAO;
	
	@Autowired
	private LaoUserbillsDetailExtMapper LaoUserbillsDetailExtDAO;
	/**
	 * 功能描述：批量导入
	 */
	@Override
	public int batchInsert(List<LaoOperatorsBillDto> records) {
		List<LaoOperatorsBill> list = null;
		if(records != null && records.size()> 0){
			list = new ArrayList<LaoOperatorsBill>();
			for (LaoOperatorsBillDto dto : records) {
				LaoOperatorsBill po = new LaoOperatorsBill();
				BeanMapper.copy(dto, po);
				list.add(po);
			}
		}
		if(list != null){
			if(list.size() > 1){
				return laoOperatorsBillExtDAO.batchInsert(list);
			}
			return laoOperatorsBillDAO.insert(list.get(0));
		}
		return 0;
	}
	
	
	/**
	 * 功能描述：根据账期和平帐标志 和operatorsId 查询账单
	 */
	@Override
	public List<LaoOperatorsBillDto> getOperatorBills(Integer cycleId,Integer operatorsId, String balanceTag, String userId) {
		List<LaoOperatorsBillDto> dtoList = new ArrayList<LaoOperatorsBillDto>();
		List<LaoOperatorsBill> list = laoOperatorsBillExtDAO.getOperatorBillList(cycleId,operatorsId, balanceTag, userId);
		if(list != null && list.size() > 0)
		for (LaoOperatorsBill po : list) {
			LaoOperatorsBillDto dto = new LaoOperatorsBillDto();
			BeanMapper.copy(po, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	/**
	 *  功能描述：根据账期和平帐标志 和operatorsId 查询账单(分页)
	 */
	@Override
	public Map<String, Object> queryOperatorsBillsByPage(LaoOperatorsBillDto dto, int pageNo, int pageSize) {
		Page<LaoOperatorsBill> page = new Page<LaoOperatorsBill>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		LaoOperatorsBill laoOperatorsBill = new LaoOperatorsBill();
		if (dto != null) {
			BeanMapper.copy(dto, laoOperatorsBill);
		}
		params.put("bill", laoOperatorsBill);
		page.setParams(params);
		List<LaoOperatorsBill> list = laoOperatorsBillExtDAO.queryOperatorsBills(page);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<LaoOperatorsBillDto> dtoList = new ArrayList<>();
		if(list != null && list.size() > 0){
			for (LaoOperatorsBill laoOperatorsBill2 : list) {
				LaoOperatorsBillDto billDto = new LaoOperatorsBillDto();
				Long channelCustId = laoOperatorsBill2.getChannelCustId();
				BeanMapper.copy(laoOperatorsBill2, billDto);
				LaoCustomerPo po = laoCustomerPoDAO.selectByPrimaryKey(channelCustId);
				if(po != null){
					billDto.setOperatorsName(po.getCustName());
				}
				
				//元转换成分
				BigDecimal fee = new BigDecimal(billDto.getFee());
				fee = fee.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
				billDto.setFee(fee.longValue());
				
				BigDecimal glFee = new BigDecimal(billDto.getGlaFee());
				glFee = glFee.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
				billDto.setGlaFee(glFee.longValue());
				
				BigDecimal realFee = new BigDecimal(billDto.getRealFee());
				realFee = realFee.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
				billDto.setRealFee(realFee.longValue());
				
				dtoList.add(billDto);
			}
		}
		/*resultMap.put("data", ConversionUtil.poList2dtoList(list,
				LaoOperatorsBillDto.class));*/
		resultMap.put("data", dtoList);
		resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());// 总记录数

		return resultMap;
	}
	
	/**
	 * 功能描述：根据运营商和运营商ID 计算每个运营商的账期日，并更新最新要计算的账期的起始和截止时间
	 */
	@Override
	public int updateOperatorsCycle(LaoOperatorsCycleDto record) {
		LaoOperatorsCycle po = new LaoOperatorsCycle();
		if(record != null)
		BeanMapper.copy(record,po);
		return laoOperatorsCycleExtDAO.updateCycle(po);
	}
	
	@Override
	public int insertOperatorsCycle(LaoOperatorsCycleDto record){
		LaoOperatorsCycle po = new LaoOperatorsCycle();
		if(record != null)
		BeanMapper.copy(record,po);
		return laoOperatorsCycleDAO.insert(po);
	}
	
	/**
    * 功能描述：向kafka中发送信息
     */
	@Override
	public void sendUserMsg(List<Map<String, Object>> listMap) {
		chargeOffProducer.sendMessage(listMap);
	}
	
	/**
    * 功能描述：向kafka中发送信息
     */
	@Override
	public void sendBillMsg(List<Map<String, String>> listMap) {
		billingImportProducer.sendMessage(listMap);
	}

	@Override
	public List<LaoUserOperatorPlanDto> getUserPlansByBillTag(Long userId, String billTag) {
		List<LaoUserOperatorPlan> planList = laoUserOperatorPlanExtDAO.getUserPlansByBillTag(userId, billTag);
		List<LaoUserOperatorPlanDto> resultList = null;
		if(planList !=null && planList.size()> 0){
			resultList = new ArrayList<LaoUserOperatorPlanDto>();
			for (LaoUserOperatorPlan laoUserOperatorPlan : planList) {
				LaoUserOperatorPlanDto dto = new LaoUserOperatorPlanDto();
				BeanMapper.copy(laoUserOperatorPlan, dto);
				resultList.add(dto);
			}
		}
		return resultList;
	}


	@Override
	public List<LaoUserOperatorPlanDto> getUserPlansByPlanId(Long userId, int planId) {
		List<LaoUserOperatorPlan> planList = laoUserOperatorPlanExtDAO.getUserPlansByPlanId(userId, planId);
		List<LaoUserOperatorPlanDto> resultList = null;
		if(planList !=null && planList.size()> 0){
			resultList = new ArrayList<LaoUserOperatorPlanDto>();
			for (LaoUserOperatorPlan laoUserOperatorPlan : planList) {
				LaoUserOperatorPlanDto dto = new LaoUserOperatorPlanDto();
				BeanMapper.copy(laoUserOperatorPlan, dto);
				resultList.add(dto);
			}
		}
		return resultList;
	}


	@Override
	public List<String> getUserIdList(int cycleId, String balanceTag) {
		return laoOperatorsBillExtDAO.getUserIdList(cycleId, balanceTag);
	}

	/**
	 * 对账成功，更新LAO_OPERATORS_BILL对应记录的GLA_FEE=REAL_FEE，BALANCE_TAG=1已平账；否则，GLA_FEE= b.cost_price，BALANCE_TAG=2未平账；
	 */
	@Override
	public int updateOperatorBill(Long glaFee, String balanceTag, Long operatorsBillId) {
		LaoOperatorsBill record  = new LaoOperatorsBill();
		record.setOperatorsBillId(operatorsBillId);
		record.setGlaFee(glaFee);
		record.setBalanceTag(balanceTag);
		return laoOperatorsBillDAO.updateByPrimaryKeySelective(record);
	}


	@Override
	public int updateUserOperatorPlan(LaoUserOperatorPlanDto record) {
		LaoUserOperatorPlan plan  = null;
		if(record != null){
			plan = new LaoUserOperatorPlan();
			BeanMapper.copy(record, plan);
		}
		return laoUserOperatorPlanDAO.updateByPrimaryKeySelective(plan);
	}


	@Override
	public LaoOperatorsCycleDto getOperatorsCycle(int cycId, String idValue, String idType) {
		LaoOperatorsCycleDto  dto = null;
		LaoOperatorsCycle operatorsCycle = laoOperatorsCycleExtDAO.getOperatorsCycle(cycId, idValue, idType);
		if(operatorsCycle != null){
			dto = new LaoOperatorsCycleDto();
			BeanMapper.copy(operatorsCycle, dto);
		}
		return dto;
	}


	@Override
	public LaoUserfeeInfoDto selectByTradeId(String tradeId) {
		LaoUserfeeInfoDto dto = null;
		LaoUserfeeInfo selectByTradeId = laoUserfeeInfoExtDAO.selectByTradeId(tradeId);
		if(selectByTradeId != null){
			dto = new LaoUserfeeInfoDto();
			BeanMapper.copy(selectByTradeId, dto);
		}
		return dto;
	}

	@Override
	public String chargeOff(LaoUserOperatorPlanDto userOperatorPlan, LaoOperatorsBillDto operatorsBill, String totalCost, int cycleId) {
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>进入出账实际操作：cycleId="+cycleId+"||totalCost ="+totalCost);
		Long costPrice = 0l;
		Long inFee = 0l;
		LaoUserbillsDetail userBillDetail = null;
		if(userOperatorPlan != null && StringUtils.isNotBlank(totalCost)){
			Long totalFee = Long.parseLong(WeixinUtil.getMoney(totalCost));
			LaoUserfeeInfoDto userFee = selectByTradeId(userOperatorPlan.getTradeId().toString());
			if(userFee != null){
				//平帐操作
				if(operatorsBill != null){
					updateOperatorBill(operatorsBill.getRealFee(), "1", operatorsBill.getOperatorsBillId());
					if(operatorsBill.getOperatorsId() == 1){//如果是联通，更新lao_user_operator_plan的bill_tag=1；电信和移动不做变更；
						userOperatorPlan.setBillTag("1");
						updateUserOperatorPlan(userOperatorPlan);
					}
				}
				
				costPrice = Long.parseLong(WeixinUtil.getMoney(userOperatorPlan.getCostPrice()));
				 // 创建一个数值格式化对象  
		        NumberFormat numberFormat = NumberFormat.getInstance();  
		        // 设置精确到小数点后2位  
		        numberFormat.setMaximumFractionDigits(2);  
		        String result = numberFormat.format((float) costPrice / (float) totalFee); 
		        BigDecimal b1 = new BigDecimal(Double.valueOf(result));
		        BigDecimal b2 = new BigDecimal(Double.valueOf(userFee.getFee()));
		        BigDecimal multiply = b1.multiply(b2);
				inFee = multiply.longValue(); //该记录的成本cost_price/总成本total_cost =成本比例RATE
				
				userBillDetail = LaoUserbillsDetailExtDAO.getUserBillDetailByChargeId(userFee.getChargeId().toString());
				if(userBillDetail == null){
					userBillDetail = new LaoUserbillsDetail();
				}
				//判断该账期应出账收入 
				if(userFee.getBillFee() == userFee.getFee() && userFee.getStartBillCyc() == cycleId){ //如果BILL_FEE=FEE，则START_BILL_CYC=当前出账账期；否则不更新
					userFee.setLastestBillCyc(cycleId);
					userBillDetail.setFee(userFee.getBillFee());
				}else if(userFee.getBillFee() >= inFee){
					userFee.setBillFee(userFee.getBillFee() - inFee);
					userBillDetail.setFee(inFee);
				}else if(userFee.getBillFee() < inFee){
					userBillDetail.setFee(userFee.getBillFee());
				}
				
				userFee.setUpdateTime(new Date());
				userBillDetail.setUpdateTime(new Date());
				
				//插入一条新数据到LaoUserbillsDetail 表中
				userBillDetail.setCycleId(cycleId);
				userBillDetail.setOperatosId(userOperatorPlan.getOperatorsId());
				userBillDetail.setGoodsReleaseId(userOperatorPlan.getGoodsReleaseId() != null ? userOperatorPlan.getGoodsReleaseId().longValue():null);
				userBillDetail.setGoodsId(userOperatorPlan.getGoodsId());
				userBillDetail.setPlanId(userOperatorPlan.getPlanId());
				userBillDetail.setRecvTime(new Date());
				userBillDetail.setUserId(userOperatorPlan.getUserId());
				userBillDetail.setChargeId(userFee.getChargeId());//LAO_USERFEE_INFO.USERFEE_ID
				userBillDetail.setCustId(userFee.getCustId());
				userBillDetail.setChannelCustId(userFee.getChannelCustId());
				
				if(userBillDetail.getBillDetailId() != null){
					laoUserbillsDetailDAO.updateByPrimaryKeySelective(userBillDetail);
				}else{
					userBillDetail.setBillDetailId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BILL_DETAIL_ID)));
					laoUserbillsDetailDAO.insertSelective(userBillDetail);
				}
				
				//更新结果到各个表中
				LaoUserfeeInfo userFeePo = new LaoUserfeeInfo();
				BeanMapper.copy(userFee, userFeePo);
				laoUserfeeInfoDAO.updateByPrimaryKeySelective(userFeePo);
			}else{
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>LaoUserfeeInfo 没有记录|"+userOperatorPlan.getTradeId().toString());
			}
		}
		return null;
	}


	@Override
	public List<Map<String, Object>> getCostTotalsOfUnicom(Long userId) {
		return laoUserOperatorPlanExtDAO.getCostTotalsOfUnicom(userId);
	}


	@Override
	public List<Map<String, Object>> getCostTotals(Long userId) {
		return laoUserOperatorPlanExtDAO.getCostTotals(userId);
	}


	@Override
	public Map<String, Object> getBillsResult(LaoOperatorsbillResultDto result, int pageNo, int pageSize) {
		Integer cycleId = result.getCycleId();
		Integer operatorsId = result.getOperatorsId();
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>查询入参：cycleId"+cycleId+"&operatorsId"+operatorsId);
		//分页查询运营商录入的账单信息
		LaoOperatorsBillDto billDto = new LaoOperatorsBillDto();
		Map<String, Object> queryOperatorsBillsByPage = null;
		if(cycleId != null){
			billDto.setCycleId(cycleId);
			billDto.setOperatorsId(operatorsId);
			queryOperatorsBillsByPage = queryOperatorsBillsByPage(billDto, pageNo, pageSize);
		}
		List<LaoOperatorsbillResultDto> resultList =  new ArrayList<LaoOperatorsbillResultDto>();
		
		//分页查询对账结果
		Page<LaoOperatorsbillResult> page = new Page<LaoOperatorsbillResult>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		LaoOperatorsbillResult laoOperatorsbillResult = new LaoOperatorsbillResult();
		if (result != null) {
			BeanMapper.copy(result, laoOperatorsbillResult);
		}
		params.put("result", laoOperatorsbillResult);
		page.setParams(params);
		List<LaoOperatorsbillResult> operatorsbillResultList = laoOperatorsbillResultExtDAO.getOperatorsbillResultList(page);
		
		//如果cycleId和operatorsId 参数齐全，运营商录入的账单信息有信息但是运营商账单结果表仍然没有结果， 需要做统计账单平帐信息，插入数据到运营商账单结果表
		if(operatorsbillResultList == null || operatorsbillResultList.size() == 0 && operatorsId != null){
			if(queryOperatorsBillsByPage != null && Integer.parseInt(queryOperatorsBillsByPage.get("iTotalDisplayRecords").toString())> 0 && cycleId != null){
				LaoOperatorsbillResult record = new LaoOperatorsbillResult();
				record.setOperatorsId(operatorsId);
				record.setCycleId(cycleId);
				record.setUpdateTime(new Date());
//				record.setUpdateAccountId(updateAccountId);
				record.setBatchId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BILL_RESULT_ID)));
				//查询 {GLATOTALCOST=0, SUMNUM=64076, TOTALCOST=72440750}  gla总费用， 总数， 运营商总费用
				//0-未对账；1-已平账；2-未平账
				Map<String, Object> queryMap = laoOperatorsBillExtDAO.queryMap(cycleId, operatorsId, "");
				BigDecimal totalCost = null;
				BigDecimal glaCost = null;
				BigDecimal sumNum = null;
				if(queryMap != null){
					totalCost = (BigDecimal) queryMap.get("TOTALCOST");
					glaCost = (BigDecimal) queryMap.get("GLATOTALCOST");
					sumNum = (BigDecimal) queryMap.get("SUMNUM");
					
					//元转换成分
					totalCost = totalCost.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
					glaCost = glaCost.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
					record.setTotalCost(totalCost.longValue());
					record.setGlaTotalCost(glaCost.longValue());
					record.setSumNum(sumNum.longValue());
				}
				//平账
				queryMap = laoOperatorsBillExtDAO.queryMap(cycleId, operatorsId, "1");
				if(queryMap != null){
					sumNum = (BigDecimal) queryMap.get("SUMNUM");
					record.setSuccNum(sumNum.longValue());
				}
				//未平账
				queryMap = laoOperatorsBillExtDAO.queryMap(cycleId, operatorsId, "2");
				if(queryMap != null){
					sumNum = (BigDecimal) queryMap.get("SUMNUM");
					record.setFailNum(sumNum.longValue());
				}
				laoOperatorsbillResultDAO.insert(record);
				resultList = new ArrayList<LaoOperatorsbillResultDto>();
				LaoOperatorsbillResultDto dto = new LaoOperatorsbillResultDto();
				BeanMapper.copy(record, dto);
				
				List<OperatorsDto> operators = operatorService.findOperators();
				for (OperatorsDto operatorsDto : operators) {
					if(operatorsDto.getOperatorsId() == operatorsId){
						dto.setOperatorsName(operatorsDto.getOperatorsName());
						break;
					}
				}
				//未对账
				queryMap = laoOperatorsBillExtDAO.queryMap(cycleId, operatorsId, "0");
				if(queryMap != null){
					sumNum = (BigDecimal) queryMap.get("SUMNUM");
					dto.setUnChargeNum(sumNum.longValue());
				}
				dto.setOperateTime(new SimpleDateFormat("yyyy-MM-dd H:m:s").format(dto.getUpdateTime()));
				resultList.add(dto);
			}
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>对不起，查询不到任何录入账单和账单结果相关数据:cycleId"+cycleId+"&operatorsId"+operatorsId);
		}else{
			resultList = new ArrayList<LaoOperatorsbillResultDto>();
			for (LaoOperatorsbillResult operatorsbillResult : operatorsbillResultList) {
				
				Integer cycleId2 = operatorsbillResult.getCycleId();
				Integer operatorsId2 = operatorsbillResult.getOperatorsId();
				Map<String, Object> resulMap = laoOperatorsBillExtDAO.queryMap(cycleId2, operatorsId2, "1");
				BigDecimal sumNum = null;
				
				//平账
				if(resulMap != null){
					sumNum = (BigDecimal) resulMap.get("SUMNUM");
					operatorsbillResult.setSuccNum(sumNum.longValue());
				}
				//未平账
				resulMap = laoOperatorsBillExtDAO.queryMap(cycleId2, operatorsId2, "2");
				if(resulMap != null){
					sumNum = (BigDecimal) resulMap.get("SUMNUM");
					operatorsbillResult.setFailNum(sumNum.longValue());
				}
				//更新其他值
				resulMap = laoOperatorsBillExtDAO.queryMap(cycleId, operatorsId, "");
				if(resulMap != null){
					BigDecimal totalCost = (BigDecimal) resulMap.get("TOTALCOST");
					BigDecimal glaCost = (BigDecimal) resulMap.get("GLATOTALCOST");
					sumNum = (BigDecimal) resulMap.get("SUMNUM");
					
					//元转换成分
					totalCost = totalCost.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
					glaCost = glaCost.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
					operatorsbillResult.setTotalCost(totalCost.longValue());
					operatorsbillResult.setGlaTotalCost(glaCost.longValue());
					operatorsbillResult.setSumNum(sumNum.longValue());
				}
				
				//更新数据
				laoOperatorsbillResultDAO.updateByPrimaryKeySelective(operatorsbillResult);
				
				LaoOperatorsbillResultDto dto = new LaoOperatorsbillResultDto();
				BeanMapper.copy(operatorsbillResult, dto);
				List<OperatorsDto> operators = operatorService.findOperators();
				for (OperatorsDto operatorsDto : operators) {
					if(operatorsDto.getOperatorsId() == operatorsbillResult.getOperatorsId())
					dto.setOperatorsName(operatorsDto.getOperatorsName());
					break;
				}
				//未对账的
				resulMap = laoOperatorsBillExtDAO.queryMap(cycleId2, operatorsId2, "0");
				if(resulMap != null){
					sumNum = (BigDecimal) resulMap.get("SUMNUM");
					dto.setUnChargeNum(sumNum.longValue());
				}
				dto.setOperateTime(new SimpleDateFormat("yyyy-MM-dd H:m:s").format(dto.getUpdateTime()));
				resultList.add(dto);
			}
			
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", resultList);
		resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());// 总记录数
		return resultMap;
	}


	@Override
	public OperatorPlanDto selectByPid(String operatorsPid) {
		OperatorPlan selectByPid = laoOperatorPlanExtDAO.selectByPid(operatorsPid);
		OperatorPlanDto dto = null;
		if(selectByPid != null){
			dto = new OperatorPlanDto();
			BeanMapper.copy(selectByPid, dto);
		}
		return dto;
	}


	@Override
	public List<LaoCustomerDto> queryAllAgents() {
		List<LaoCustomerPo> queryAllAgents = laoCustomerPoExtDAO.queryAllAgents();
		if(queryAllAgents != null && queryAllAgents.size() > 0){
			return ConversionUtil.poList2dtoList(queryAllAgents,LaoCustomerDto.class);
		}
		return null;
	}


	@Override
	public Map<String, Object> revenueByPage(LaoBPaylogDto dto, int pageNo, int pageSize, String startDate, String endDate) {
		Page<LaoBPaylog> page = new Page<LaoBPaylog>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		LaoBPaylog payLog = new LaoBPaylog();
		if (dto != null) {
			BeanMapper.copy(dto, payLog);
		}
		
		//时间转换
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(StringUtils.isNotBlank(startDate) && startDate.length() ==8 && StringUtils.isNotBlank(endDate) && endDate.length() ==8){
			startDate = startDate.substring(0, 4)+"-"+startDate.substring(4, 6)+"-"+startDate.substring(6);
			endDate = endDate.substring(0, 4)+"-"+endDate.substring(4, 6)+"-"+endDate.substring(6);
		}else{
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>营收查询有时间录入错误"+startDate+"||||"+endDate);
			resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
			resultMap.put("iTotalDisplayRecords", page.getTotalRecord());// 总记录数
			resultMap.put("data", new ArrayList<LaoBPaylogDto>());
			return resultMap;
		}
		
		//时间修改
		Date sdate = null;
		Date edate = null;
		try {
			sdate = dateFormat.parse(startDate+" 00:00:00");
			edate = dateFormat.parse(endDate+" 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//暂时将开始时间和结束时间 放到  RecvTime 和  FeecntTime 中
		payLog.setRecvTime(sdate);
		payLog.setFeecntTime(edate);
		params.put("result", payLog);
		page.setParams(params);
		
		List<Map> revenue = laoBPaylogExtDAO.revenueByPage(page);
		
		List<LaoBPaylogDto> list = new ArrayList<LaoBPaylogDto>();
		if(revenue != null && revenue.size()> 0){
			list = convert2Dto(revenue);
		}
		
		resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());// 总记录数
		resultMap.put("data", list);
		return resultMap;
	}
	
	@Override
	public List<LaoBPaylogDto> revenue(LaoBPaylogDto dto, String startDate, String endDate) {
		LaoBPaylog payLog = new LaoBPaylog();
		if (dto != null) {
			BeanMapper.copy(dto, payLog);
		}
		
		//时间转换
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(StringUtils.isNotBlank(startDate) && startDate.length() ==8 && StringUtils.isNotBlank(endDate) && endDate.length() ==8){
			startDate = startDate.substring(0, 4)+"-"+startDate.substring(4, 6)+"-"+startDate.substring(6);
			endDate = endDate.substring(0, 4)+"-"+endDate.substring(4, 6)+"-"+endDate.substring(6);
		}else{
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>营收查询有时间录入错误"+startDate+"||||"+endDate);
			return null;
		}
		
		//时间修改
		Date sdate = null;
		Date edate = null;
		try {
			sdate = dateFormat.parse(startDate+" 00:00:00");
			edate = dateFormat.parse(endDate+" 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//暂时将开始时间和结束时间 放到  RecvTime 和  FeecntTime 中
		payLog.setRecvTime(sdate);
		payLog.setFeecntTime(edate);
		List<LaoBPaylogPo> resultList = laoBPaylogExtDAO.revenue(payLog);
		if(resultList != null && resultList.size() > 0){
			return ConversionUtil.poList2dtoList(resultList,LaoBPaylogDto.class);
		}
		return null;
	}
	
	public List<LaoBPaylogDto> convert2Dto(List<Map> list){
		/**
		 * CANCEL_TAG=0, TRADE_TYPE_CODE=120, RECV_TIME=2017-04-01 16:16:24.0, 
		 * CHANNEL_CUST_ID=3071311390025064, USER_ID=1001712190020018, PAY_TAG=0, 
		 * CHARGE_ID=3011616240000018, GOODS_NAME=3月27日测试余额中心, OUTSIDESTATE=1, RECV_FEE=0, 
		 * ICCID=8986031645202979523, OPERATORS_NAME=中国联通, CUST_NAME=深圳中慧广文化传播有限公司, 
		 * REAL_FEE=10000, PAYMENT_OP=10004, DISCNT_FEE=10000, R=9, FEECNT_TAG=0, GOODS_ID=3111917090000002, 
		 * PAY_FEE_MODE_CODE=1, TRADE_ID=1011616230000044, STATIC_NAME=扣费, FEE=10000
		 */
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<LaoBPaylogDto> dtoList = new ArrayList<LaoBPaylogDto>();
		for (Map map : list) {
			LaoBPaylogDto dto = new LaoBPaylogDto();
			if(map.containsKey("CUST_NAME")){
				dto.setCustName(map.get("CUST_NAME").toString());
			}
			if(map.containsKey("ICCID")){
				dto.setIccid(map.get("ICCID").toString());
			}
			if(map.containsKey("MSISDN")){
				dto.setMsisdn(map.get("MSISDN").toString());
			}
			if(map.containsKey("OPERATORS_NAME")){
				dto.setOperatorName(map.get("OPERATORS_NAME").toString());
			}
			if(map.containsKey("GOODS_NAME")){
				dto.setGoodName(map.get("GOODS_NAME").toString());
			}
			if(map.containsKey("OUTSIDESTATE")){
				dto.setStatus(map.get("OUTSIDESTATE").toString());
			}
			if(map.containsKey("REAL_FEE")){
				dto.setRealFee(Long.parseLong(map.get("REAL_FEE").toString()));
			}
			if(map.containsKey("STATIC_NAME")){
				dto.setStaticName(map.get("STATIC_NAME").toString());
			}
			if(map.containsKey("RECV_TIME")){
				try {
					dto.setRecvTime(dateFormat.parse(map.get("RECV_TIME").toString()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			dtoList.add(dto);
		}
		return dtoList;
	}


	@Override
	public List<LaoSsStaticDto> queryPaymentOps() {
		List<LaoSsStaticPo> queryPaymentOps = laoSsStaticPoExtDAO.queryPaymentOps();
		if(queryPaymentOps != null && queryPaymentOps.size() > 0){
			return ConversionUtil.poList2dtoList(queryPaymentOps,LaoSsStaticDto.class);
		}
		return null;
	}


	@Override
	public String chargeOffBefore(String userId, int cycleId, String operatorId) {
		List<LaoOperatorsBillDto> operatorBills = null;
		if(StringUtils.isNotBlank(userId) && cycleId != 0 && StringUtils.isNotBlank(operatorId)){
			operatorBills = getOperatorBills(cycleId,Integer.parseInt(operatorId), "0", userId);//0-未对账；1-已平账；2-未平账
		}else{
			return null;
		}
		
		List<LaoUserOperatorPlanDto> planList = null;
		List<Map<String, Object>> list = null;
		String totalCost = null;
		//处理出账逻辑开始
		if(operatorBills != null && operatorBills.size() > 0){
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>处理出账逻辑开始:");	
			if(("1").equals(operatorId)){   //针对联通的出账处理逻辑
				planList = getUserPlansByBillTag(Long.parseLong(userId), "0");//0-未出账；1-已出账
				list = getCostTotalsOfUnicom(Long.parseLong(userId));
			}else if(("2").equals(operatorId) || ("3").equals(operatorId)){ //针对移动和电信的出账处理逻辑
				int planId =operatorBills.get(0).getPlanId();               //移动和电信的planId 是唯一的吗？
				planList = getUserPlansByPlanId(Long.parseLong(userId), planId);
				list = getCostTotals(Long.parseLong(userId));
			}
			/**	  用user_id， plan_id匹配出来的费用REAL_FEE如果=lao_user_operator_plan中根据user_id, plan_id计算出来的费用cost_price
			 * 对账成功，更新LAO_OPERATORS_BILL对应记录的GLA_FEE=REAL_FEE，BALANCE_TAG=1已平账；否则，GLA_FEE= b.cost_price，BALANCE_TAG=2未平账；
			 * 	   如果是联通，更新lao_user_operator_plan的bill_tag=1；电信和移动不做变更；
			 */
			if(operatorBills != null && operatorBills.size()> 0){
				for (int i = 0; i < operatorBills.size(); i++) {
					LaoOperatorsBillDto operatorsBill = operatorBills.get(i);
					
					if(planList != null && planList.size()> 0){
						for (int j = 0; j < planList.size(); j++) {
							LaoUserOperatorPlanDto userOperatorPlan = planList.get(j);
							
							if(operatorsBill.getPlanId() == userOperatorPlan.getPlanId() && userOperatorPlan.getStartUseDate() != null ){//匹配成功
								if(StringUtils.isNotBlank(userOperatorPlan.getCostPrice()) && operatorsBill.getRealFee() == Long.parseLong(WeixinUtil.getMoney(userOperatorPlan.getCostPrice())) && operatorsBill.getOperatorsPid().equals(userOperatorPlan.getOperatorsPid())){ 
									if(list != null && list.size() > 0){ 
										planList.remove(j);
										
										//出账处理
										for (Map<String, Object> map : list) {   //map 中的内容：{TOTAL_COST=60, TRADE_ID=1012005440000480}
											if(map != null){
												BigDecimal tradeId = (BigDecimal) map.get("TRADE_ID");
												BigDecimal cost = (BigDecimal) map.get("TOTAL_COST");
												if(tradeId.longValue() == userOperatorPlan.getTradeId()){
													totalCost = cost.toString();
													chargeOff(userOperatorPlan, operatorsBill, totalCost, cycleId);
												}
											}
										}
									}
								}else{       //未平帐操作
									updateOperatorBill(Long.parseLong(userOperatorPlan.getCostPrice()), "2", operatorsBill.getOperatorsBillId());
								}
								break;
							}
						}
					}/*else{
						chargeOffService.updateOperatorBill(-1l, "2", operatorsBill.getOperatorsBillId());
					}*/
				}
			}
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>处理出账中运营商账单为空");
			
			LaoOperatorsCycleDto operatorsCycle = getOperatorsCycle(cycleId, operatorId, "0");//idType :0-运营商；1-企业客户；2-个人客户   
			if(planList != null && planList.size()> 0){//判断该记录是否是到期未激活，如果是到期未激活，则也需要出账计收
				for (LaoUserOperatorPlanDto laoUserOperatorPlanDto : planList) {
					if(laoUserOperatorPlanDto.getStartUseDate() == null && laoUserOperatorPlanDto.getEndDate() != null && laoUserOperatorPlanDto.getEndDate() != null){
						if(laoUserOperatorPlanDto.getEndDate().getTime() > operatorsCycle.getStartDate().getTime() && 
								laoUserOperatorPlanDto.getEndDate().getTime() < operatorsCycle.getEndDate().getTime()){
							//出账处理
							if(list != null && list.size() > 0){ 
								for (Map<String, Object> map : list) {   //map 中的内容：{TOTAL_COST=60, TRADE_ID=1012005440000480}
									BigDecimal tradeId = (BigDecimal) map.get("TRADE_ID");
									BigDecimal cost = (BigDecimal) map.get("TOTAL_COST");
									if(tradeId.longValue() == laoUserOperatorPlanDto.getTradeId()){
										totalCost = cost.toString();
										chargeOff(laoUserOperatorPlanDto,null, totalCost, cycleId);
									}
								}
							}
						}
					}
				}
			}
			
		}
		return null;
	}
}
