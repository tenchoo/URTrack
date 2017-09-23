package com.urt.service.batch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.Ability.collect.QueryTelByIccid;
import com.urt.Ability.collect.UserQueryCardStatus;
import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.CardStatusDto;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoBatchDataDto;
import com.urt.dto.LaoBatchDatadetailDto;
import com.urt.dto.UserOperatorInfoDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.grpnet.GrpnetImpbillDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.batch.BatchService;
import com.urt.mapper.LaoBatchDataPoMapper;
import com.urt.mapper.LaoBatchDatadetailPoMapper;
import com.urt.mapper.OperatorsMapper;
import com.urt.mapper.UserOperatorInfoPOMapper;
import com.urt.mapper.ext.LaoBatchDataPoExtMapper;
import com.urt.mapper.ext.LaoBatchDatadetailPoExtMapper;
import com.urt.mapper.ext.ServiceStatusChgExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.msgProducter.trade.BatchRemoveRatePlanProducer;
import com.urt.msgProducter.trade.CardInfoProducer;
import com.urt.msgProducter.trade.CardStatusProducer;
import com.urt.msgProducter.trade.UserInfoProducer;
import com.urt.po.LaoBatchDataPo;
import com.urt.po.LaoBatchDatadetailPo;
import com.urt.po.Operators;
import com.urt.po.UserOperatorInfoPO;
import com.urt.utils.SeqID;
import com.urt.utils.SpringContextUtils;
import com.urt.utils.ZkGenerateSeq;

@Service(value="batchServer")
public class BatchServiceImpl implements BatchService {
	@Autowired
	private LaoBatchDatadetailPoMapper batchDataDetailDao;
	@Autowired
	private LaoBatchDatadetailPoExtMapper batchDataDetailExtDao;
	@Autowired
	private LaoBatchDataPoMapper batchDataDao;
	@Autowired
	private LaoBatchDataPoExtMapper batchDataExtDao;
	@Autowired
	private UserOperatorInfoPOMapper userOperatorInfoDao;

	private UserQueryCardStatus userQueryCardStatus;

	private QueryTelByIccid queryTelByIccid;

	@Autowired
	private OperatorsMapper operatorsDAO;
	
	@Autowired
	private UserService  userService;
	
	@Autowired
	private CardStatusProducer cardStatusProducer;
	
	@Autowired 
	private CardInfoProducer cardInfoProducer;
	@Autowired 
	private UserInfoProducer userInfoProducer;
	@Autowired
	private ServiceStatusChgExtMapper serviceStatusChgExtDAO;
	
	@Autowired 
	private BatchRemoveRatePlanProducer batchRemoveRatePlanProducer;
	

	@Override
	public int saveBatchData(LaoBatchDataDto dto) {
		// TODO Auto-generated method stub
		LaoBatchDataPo po = new LaoBatchDataPo();
		BeanMapper.copy(dto, po);
		return batchDataDao.insertSelective(po);
	}

	@Override
	public int saveBatchDataDetail(LaoBatchDatadetailDto dto) {
		// TODO Auto-generated method stub
		LaoBatchDatadetailPo po = new LaoBatchDatadetailPo();
		BeanMapper.copy(dto, po);
		return batchDataDetailDao.insertSelective(po);
	}

	@Override
	public String queryCardStatus(IccidLibDto iccid) {
		// TODO Auto-generated method stub
		Operators op = operatorsDAO.selectByPrimaryKey(Integer.valueOf(iccid
				.getOperators()));
		userQueryCardStatus = (UserQueryCardStatus) SpringContextUtils
				.getBean(op.getQueryCardStatus());
		CardStatusDto queryCardStatus = userQueryCardStatus
				.queryCardStatus(iccid.getIccid());
		return queryCardStatus.getCardStatus();
	}

	@Override
	public String queryCardTel(IccidLibDto iccid) {
		Operators op = operatorsDAO.selectByPrimaryKey(Integer.valueOf(iccid
				.getOperators()));
		queryTelByIccid = (QueryTelByIccid) SpringContextUtils.getBean(op
				.getQueryTelClass());
		return queryTelByIccid.queryTelByIccid(iccid.getIccid());
	}

	@Override
	public String queryCardStatusByUserDto(LaoUserDto userDto) {
		// TODO Auto-generated method stub
		Operators op = operatorsDAO.selectByPrimaryKey(Integer.valueOf(userDto.getOperatorsId()
				));
		userQueryCardStatus = (UserQueryCardStatus) SpringContextUtils
				.getBean(op.getQueryCardStatus());
		CardStatusDto queryCardStatus = userQueryCardStatus
				.queryCardStatus(userDto.getIccid());
		return queryCardStatus.getCardStatus();
	}

	@Override
	public String queryCardTelByUserDto(LaoUserDto userDto) {
		// TODO Auto-generated method stub
		Operators op = operatorsDAO.selectByPrimaryKey(Integer.valueOf(userDto.getOperatorsId()
				));
		queryTelByIccid = (QueryTelByIccid) SpringContextUtils.getBean(op
				.getQueryTelClass());
		return queryTelByIccid.queryTelByIccid(userDto.getIccid());
	}

	@Override
	public List<Map<String, Object>> queryPlanByUserDto(LaoUserDto userDto) {
		// TODO Auto-generated method stub
		Operators op = operatorsDAO.selectByPrimaryKey(Integer.valueOf(userDto.getOperatorsId()
				));
		queryTelByIccid = (QueryTelByIccid) SpringContextUtils.getBean(op
				.getQueryTelClass());
		return queryTelByIccid.queryPlanByIccid(userDto.getIccid());
	}

	@Override
	public int saveBatchUserOperatorPlan(UserOperatorInfoDto dto) {
		// TODO Auto-generated method stub
		UserOperatorInfoPO record=new UserOperatorInfoPO();
		BeanMapper.copy(dto, record);
		return userOperatorInfoDao.insertSelective(record);
	}

	@Override
	public void sendCardMsg(int total, long id) {
		int pageNumber=total/1000;
		if(pageNumber==0){
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("begin", 1);
			param.put("end", total);
			List<Map<String, Object>> list=userService.queryCardInfo(param);
			for(Map<String, Object> map:list){
				map.put("batch_id", id);
			}
			
			cardInfoProducer.sendMessage(list);
		}
		for(int i=1;i<=pageNumber+1;i++){
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("begin", (i-1)*1000+1);
			param.put("end", i*1000);
			List<Map<String, Object>> list=userService.queryCardInfo(param);
			for(Map<String, Object> map:list){
				map.put("batch_id", id);
			}
			
			cardInfoProducer.sendMessage(list);
		}
		
	}

	@Override
	public void sendUserMsg(int total, long id) {
		// TODO Auto-generated method stub
		int userPageNumber=total/1000;
		
		if(userPageNumber==0){
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("begin", 1);
			param.put("end", total);
			List<Map<String, Object>> list=userService.queryUserInfo(param);
			for(Map<String, Object> map:list){
				map.put("batch_id", id);
			}
			
			userInfoProducer.sendMessage(list);
		}
		for(int i=1;i<=userPageNumber;i++){
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("begin", (i-1)*1000+1);
			param.put("end", i*1000);
			List<Map<String, Object>> list=userService.queryUserInfo(param);
			for(Map<String, Object> map:list){
				map.put("batch_id", id);
			}
			userInfoProducer.sendMessage(list);
		}
	}

	@Override
	public Map<String,Object> batchImport(List<Map<String,Object>> list, String custId, String cardstatus,String ifMaintenance, String accountId, String ifAdmin) {
		int total = 0;
		Map<String,Object> msgMap = new HashMap<String,Object>();
		//lao_batch_data 插入一条数据 
		LaoBatchDataDto dto=new LaoBatchDataDto();
		Long id = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
		dto.setBatchId(id);
		dto.setRecvTime(new Date());
		dto.setSumNum((long)list.size());
		dto.setOperId(accountId);
		dto.setTradeTypeCode(Short.valueOf(cardstatus));
		saveBatchData(dto);
		
		//验证用户是否有操作这批卡权限
		List<Map<String, Object>> iccidList = new ArrayList<Map<String, Object>>();
		Map<String,Object> resultMap = null;
		for (int index = 0; index < list.size(); index++) {
			resultMap = list.get(index);
			if(resultMap != null){
				resultMap.put("accountId", accountId);
				resultMap.put("custId", custId);
				resultMap.put("batch_id", id);
				resultMap.put("newStatus", cardstatus);
				resultMap.put("ifMaintenance", ifMaintenance);
				resultMap.put("ifAdmin", ifAdmin);
				iccidList.add(resultMap);//这里是将数据库中查询得到的iccid存入信息
			}
			
			//每一千批量操作一次
			if(iccidList.size() == 1000){
				total = total + iccidList.size();
				cardStatusProducer.sendTask(iccidList);
				iccidList = null;
				iccidList = new ArrayList<Map<String, Object>>();
			}
		}
		//剩余的不够一千的一次操作完
		if(iccidList != null && iccidList.size() > 0){
			total = total + iccidList.size();
			cardStatusProducer.sendTask(iccidList);
		}
		msgMap.put("total", total);
		return msgMap;
	}


	@Override
	public  void updateBatchDate(String startTime, String endTime) {
		Date startDate = null;
		Date endDate = null; 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Map<String, Object> params = new HashMap<String, Object>();
		if(startTime==null||endTime==null){
			long nowMills = System.currentTimeMillis();
			long startMills = nowMills-(24*3600*1000);
			startDate=new Date(startMills);
			endDate=new Date(nowMills);
		 }else{
			 try {
				 startDate = sdf.parse(startTime);
				 endDate = sdf.parse(endTime);
			 } catch (ParseException e) {
				 e.printStackTrace();
			 }
		 }
//		params.put("param", (LaoBatchDataPo) ConversionUtil.dto2po(new LaoBatchDataDto(), LaoBatchDataPo.class));
		params.put("startTime", startDate);
		params.put("endTime", endDate);
		batchDataExtDao.updateBatchDate(params);
	}
	@Override
	public void updateByBatchId(LaoBatchDataDto dataDto) {
		batchDataExtDao.updateByBatchId((LaoBatchDataPo) ConversionUtil.dto2po(dataDto, LaoBatchDataPo.class));
	}


	/*@Override
	public void sendMsg(int total,long id) {
		// TODO Auto-generated method stub
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("begin", 1);
		param.put("end", total);
		List<Map<String, Object>> list=userService.queryCardInfo(param);
		for(Map<String, Object> map:list){
			map.put("batch_id", id);
		}
		cardInfoUpdateProducer.sendMessage(list);
		
	}*/

	@Override
	public Map<String, Object> queryFailDateList(Long batchId) {
		List<LaoBatchDatadetailPo> laoBatchDatadetailPoList = batchDataDetailExtDao.queryFailDataByBatchId(batchId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", ConversionUtil.poList2dtoList(laoBatchDatadetailPoList, LaoBatchDatadetailDto.class));
		return resultMap;
	}
	@Override
	public List<Map<String, Object>> queryLbsList(Long batchId) {
		List<LaoBatchDatadetailPo> laoBatchDatadetailPoList = batchDataDetailExtDao.queryDataByBatchId(batchId);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (laoBatchDatadetailPoList != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			for (int i = 0; i < laoBatchDatadetailPoList.size(); i++) {
				LaoBatchDatadetailPo dto = laoBatchDatadetailPoList.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("iccid", dto.getIccid());
				map.put("queryTime", sdf.format(dto.getRecvTime()));
				map.put("addrName", dto.getParaCode29());
				map.put("addrStr", dto.getParaCode30());
				list.add(map);
			}
		}
		return list;
	}

	
	@Override
	public Map<String, Object> queryFailDateList() {
		List<LaoBatchDatadetailPo> laoBatchDatadetailPoList = batchDataDetailExtDao.queryFailPage();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", ConversionUtil.poList2dtoList(laoBatchDatadetailPoList, LaoBatchDatadetailDto.class));
		return resultMap;
	}

	@Override
	public Map<String, Object> queryFailDateList(LaoBatchDatadetailDto datadetailDto, int pageNo, int pageSize) {
		Page<LaoBatchDatadetailPo> page = new Page<LaoBatchDatadetailPo>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (LaoBatchDatadetailPo) ConversionUtil.dto2po(datadetailDto, LaoBatchDatadetailPo.class));
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setParams(params);
		List<LaoBatchDatadetailPo> laoBatchDatadetailPoList = batchDataDetailExtDao.queryFailPageByBatchId(page);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", ConversionUtil.poList2dtoList(laoBatchDatadetailPoList, LaoBatchDatadetailDto.class));
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
		return resultMap;
	}
	@Override
	public void updateBatchDate(Date startDate, Date endDate,LaoBatchDataDto batchDataDto) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startTime", startDate);
		params.put("endTime", endDate);
		params.put("param", (LaoBatchDataPo) ConversionUtil.dto2po(batchDataDto, LaoBatchDataPo.class));
		batchDataExtDao.updateBatchDate(params);
	}
	@Override
	public Map<String, Object> queryPage(int pageNo,int pageSize, Date startDate,Date endDate,LaoBatchDataDto batchDataDto) {
		Page<LaoBatchDataPo> page = new Page<LaoBatchDataPo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (LaoBatchDataPo) ConversionUtil.dto2po(batchDataDto, LaoBatchDataPo.class));
		params.put("startTime", startDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		calendar.add(Calendar.DATE,1);//整数往后推,负数往前移动
		endDate = calendar.getTime();
		params.put("endTime", endDate);
		page.setParams(params);

		List<LaoBatchDataPo> laoBatchDataPoList = batchDataExtDao.queryPage(page);
		long totalRecord = page.getTotalRecord();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", ConversionUtil.poList2dtoList(laoBatchDataPoList,LaoBatchDataDto.class));
		resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", totalRecord);// 总记录数
		return resultMap;
	}
	
	@Override
	public List<GrpnetImpbillDto> batchInsert(List<GrpnetImpbillDto> listDto) {
		List<LaoBatchDatadetailPo> listNew = new ArrayList<LaoBatchDatadetailPo>();
		for (int i = 0; i < listDto.size(); i++) {
			// 明细表参数组装
			GrpnetImpbillDto dto = listDto.get(i);
			LaoBatchDatadetailPo datadetailDto = new LaoBatchDatadetailPo();
			datadetailDto.setBatchId(dto.getBatchId());
			datadetailDto.setChannelCustId(3070000000000000L);//集群网渠道客户ID统一为3070000000000000
			datadetailDto.setCustId(0L);// 客户ID
			datadetailDto.setDealTag("1");//0-待处理，1-处理中，2-处理成功，3-处理失败
			datadetailDto.setDatadetailId(dto.getBillId());// 记录ID
			datadetailDto.setFlowStatus("3");// 扭转状态:1、数据校验中；2、生成订单中；3、业务处理中；4、业务处理完成；5运营商卡状态没有数据，无法更新；6运营商卡电话没有数据，无法更新 7 运营商无此卡信息
			datadetailDto.setGoodsId(0L);// 商品ID
			datadetailDto.setIccid("");// iccid
			datadetailDto.setMsisdn(dto.getSerialNumber());// 服务号
			datadetailDto.setOperId(dto.getUpdateStaffId());// 操作人员ID
			datadetailDto.setRecvTime(new Date());// 生成时间
			datadetailDto.setRemark("新增的数据");// 备注
			datadetailDto.setResultInfo("");// 处理结果信息
			datadetailDto.setTradeId(0L);// 业务流水
			short tradeTypeCode = Short.parseShort(dto.getRsrvInfo1());
			datadetailDto.setTradeTypeCode(tradeTypeCode);// 业务类型编码
			datadetailDto.setUpdateTime(new Date()); // 更新时间
			datadetailDto.setUserId(dto.getUserId()); // 用户ID
			listNew.add(datadetailDto);
//			// 将明细ID记录在入 参中返回，以便更新
//			dto.setRsrvInfo2(datadetailDto.getDatadetailId()+"");
		}
		batchDataDetailExtDao.batchInsert(listNew);
		return listDto;
	}
	
	@Override
	public List<String> getTypeCode() {
		return serviceStatusChgExtDAO.getTypeCode();
	}

	@Override
	public boolean duplicateRemoval(String iccid, String batchId) {
		List<LaoBatchDatadetailPo> duplicateRemoval = batchDataDetailExtDao.duplicateRemoval(iccid, batchId);
		if(duplicateRemoval != null && duplicateRemoval.size() > 0){
			return false;
		}
		return true;
	}
	
	@Override
	public boolean duplicateRemovalByMsisdn(String msisdn, String batchId) {
		List<LaoBatchDatadetailPo> duplicateRemoval = batchDataDetailExtDao.duplicateRemovalByMsisdn(msisdn, batchId);
		if(duplicateRemoval != null && duplicateRemoval.size() > 0){
			return false;
		}
		return true;
	}


	@Override
	public void batchRemoveRatePlan( List<Map<String, Object>> list) {
		batchRemoveRatePlanProducer.sendMessage(list);
	}
}
