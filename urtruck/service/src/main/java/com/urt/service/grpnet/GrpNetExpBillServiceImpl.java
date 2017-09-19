package com.urt.service.grpnet;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.dto.grpnet.*;
import com.urt.interfaces.grpnet.GrpNetExpBillService;
import com.urt.mapper.*;
import com.urt.mapper.ext.*;
import com.urt.po.*;
import com.urt.utils.*;

@Service("grpNetExpBillService")
public class GrpNetExpBillServiceImpl implements GrpNetExpBillService {
	Logger log = Logger.getLogger(getClass());

	@Autowired
	private BBillExtMapper bBillExtMapper;
	@Autowired
	private DItemExtMapper dItemExtMapper;
	@Autowired
	private LaoUserMapper laoUserMapper;
	@Autowired
	private OperatorsExtMapper opersExtMapper;
	@Autowired
	private GoodsExtMapper goodsExtMapper;
	@Autowired
	private LaoUserGoodsMapper laoUserGoodsMapper;
	@Autowired
	private LaoUserSvcstateMapper laoUserSvcstateMapper;
	@Autowired
	private ServiceStatusExtMapper serviceStatusExtMapper;

	/**
	 * 获取用户明细账单
	 * 
	 */
	@Override
	public List<Map<String, Object>> qryBBillByCycleId(Integer cycleId) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		List<BBillExt> bBillExts = bBillExtMapper.selectBillItemByCycleId(cycleId);
		String sNumber = null;
		Long sumUserFee = 0L;
		Long sumUserBDiscnt = 0L;
		Long sumFee = 0L;
		Long sumBDiscnt = 0L;
		Long fee = 0L;
		Long bDiscnt = 0L;
		Long realFee = 0L;
		Map<String, Object> resultMap = null;
		Map<String, Object> resultMapCom = null;
		for (BBillExt bill : bBillExts) {
			resultMap = new HashMap<String, Object>();
			// 每个用户合计一次
			if (sNumber != null && !sNumber.equals(bill.getSerialNumber())) {
				resultMapCom = new HashMap<String, Object>();
				resultMapCom.put("XH1", sNumber);
				resultMapCom.put("XH2", "");
				resultMapCom.put("XH3", "合计");
				resultMapCom.put("XH4",new DecimalFormat("0.00").format((double)sumUserFee / 100));
				resultMapCom.put("XH5", new DecimalFormat("0.00").format(-(double)sumUserBDiscnt / 100));
				resultMapCom.put("XH6", new DecimalFormat("0.00").format((double)(sumUserFee - sumUserBDiscnt) / 100));
				resultMapCom.put("XH7", "");
				sumUserFee = 0L;
				sumUserBDiscnt = 0L;
				dataList.add(resultMapCom);
			}
			fee = bill.getFee(); // 优惠前金额
			bDiscnt = bill.getbDiscnt(); // 优惠金额
			realFee = bill.getFee() - bill.getbDiscnt(); // 实际金额
			sumUserFee = sumUserFee + fee; // 合计优惠前金额
			sumUserBDiscnt = sumUserBDiscnt + bDiscnt; // 合计优惠金额
			sumFee = sumFee + fee; // 优惠前金额 总计
			sumBDiscnt = sumBDiscnt + bDiscnt; // 优惠金额 总计
			resultMap.put("XH1", bill.getSerialNumber());
			resultMap.put("XH2", bill.getDItemExt().getPItemName());
			resultMap.put("XH3", bill.getDItemExt().getItemName());
			resultMap.put("XH4", new DecimalFormat("0.00").format((double)fee / 100));
			resultMap.put("XH5",new DecimalFormat("0.00").format(-(double)bDiscnt / 100));
			resultMap.put("XH6",new DecimalFormat("0.00").format((double)realFee / 100));
			resultMap.put("XH7", bill.getDItemExt().getTaxRate() + "%");

			dataList.add(resultMap);
			sNumber = bill.getSerialNumber();
		}
		// 最后一个用户数据合计
		resultMapCom = new HashMap<String, Object>();
		resultMapCom.put("XH1", sNumber);
		resultMapCom.put("XH2", "");
		resultMapCom.put("XH3", "合计");
		resultMapCom.put("XH4",new DecimalFormat("0.00").format((double)sumUserFee / 100));
		resultMapCom.put("XH5",new DecimalFormat("0.00").format(-(double)sumUserBDiscnt / 100));
		resultMapCom.put("XH6", new DecimalFormat("0.00").format((double)(sumUserFee - sumUserBDiscnt) / 100));
		resultMapCom.put("XH7", "");
		dataList.add(resultMapCom);

		// 所有用户总计
		resultMapCom = new HashMap<String, Object>();
		resultMapCom.put("XH1", "");
		resultMapCom.put("XH2", "");
		resultMapCom.put("XH3", "总计");
		resultMapCom.put("XH4", new DecimalFormat("0.00").format((double)sumFee / 100));
		resultMapCom.put("XH5",new DecimalFormat("0.00").format(-(double)sumBDiscnt / 100));
		resultMapCom.put("XH6",new DecimalFormat("0.00").format((double)(sumFee - sumBDiscnt) / 100));
		resultMapCom.put("XH7", "");
		dataList.add(resultMapCom);

		return dataList;
	}
	/**
	 * 获取用户汇总账单
	 * 
	 */
	@Override
	public List<Map<String, Object>> qryBBillSumByUserCycleId(Integer cycleId) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		List<BBillExtPo> bBillExts = bBillExtMapper.selectBillSumByUserCycleId(cycleId);
		String sNumber = null;
		Long sumUserFee = 0L;
		Long sumUserBDiscnt = 0L;
		Long fee = 0L;
		Long bDiscnt = 0L;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (BBillExtPo bill : bBillExts) {
			if (sNumber != null && !sNumber.equals(bill.getSerialNumber())) {
				resultMap.put("XH1", sNumber);
				resultMap.put("XH2", new DecimalFormat("0.00").format(-(double)sumUserBDiscnt / 100));
				resultMap.put("XH3", new DecimalFormat("0.00").format((double)(sumUserFee - sumUserBDiscnt) / 100));
				
				dataList.add(resultMap);
				
				sumUserFee = 0L;
				sumUserBDiscnt = 0L;
				resultMap = new HashMap<String, Object>();
			}
			resultMap.put("XH"+bill.getpItemId(), new DecimalFormat("0.00").format((double)bill.getFee()/100));
			fee = bill.getFee(); // 优惠前金额
			bDiscnt = bill.getbDiscnt(); // 优惠金额

			sumUserFee = sumUserFee + fee; // 合计优惠前金额
			sumUserBDiscnt = sumUserBDiscnt + bDiscnt; // 合计优惠金额
			sNumber = bill.getSerialNumber();
		}
		
		//最后一个用户
		resultMap.put("XH1", sNumber);
		resultMap.put("XH2", new DecimalFormat("0.00").format(-(double)sumUserBDiscnt / 100));
		resultMap.put("XH3", new DecimalFormat("0.00").format((double)(sumUserFee - sumUserBDiscnt) / 100));
		dataList.add(resultMap);
		
		return dataList;
	}

	/**
	 * 获取第一级账目项
	 * 
	 */
	@Override
	public List<DItemDto> qryDItemByPItemId() {
		List<DItem> dItemList = dItemExtMapper.selDItemByPItemId();
		List<DItemDto> dItemDtoList = new ArrayList<DItemDto>();
		DItemDto dItemDto = null;
		try {
			for (int i = 0; i < dItemList.size(); i++) {
				dItemDto = new DItemDto();
				BeanUtils.copyProperties(dItemDto, dItemList.get(i));
				dItemDtoList.add(dItemDto);
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
         return dItemDtoList;
	}
	/**
	 * 获取费用汇总账单
	 * 
	 */
	@Override
	public List<Map<String, Object>> qryBBillSumByItemCycleId(Integer cycleId) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		List<BBillExt> bBillExts = bBillExtMapper.selectBillSumByItemCycleId(cycleId);
		Long sumFee = 0L;
		Long sumBDiscnt = 0L;
		Map<String, Object> resultMap = null;
		for (BBillExt bill : bBillExts) {
			resultMap = new HashMap<String, Object>();
			resultMap.put("XH1", bill.getDItemExt().getPItemName());
			resultMap.put("XH2", bill.getDItemExt().getItemName());
			resultMap.put("XH3",new DecimalFormat("0.00").format((double)bill.getFee() / 100));
			resultMap.put("XH4", new DecimalFormat("0.00").format(-(double)bill.getbDiscnt() / 100));
			resultMap.put("XH5", new DecimalFormat("0.00").format((double)(bill.getFee() - bill.getbDiscnt()) / 100));
			resultMap.put("XH6", bill.getDItemExt().getTaxRate() + "%");
			
			dataList.add(resultMap);
			
			sumFee = sumFee + bill.getFee(); // 合计优惠前金额
			sumBDiscnt = sumBDiscnt + bill.getbDiscnt(); // 合计优惠金额
		}
		
		//最后合计
		resultMap = new HashMap<String, Object>();
		resultMap.put("XH1", "");
		resultMap.put("XH2", "合计");
		resultMap.put("XH3", new DecimalFormat("0.00").format((double)sumFee / 100));
		resultMap.put("XH4",new DecimalFormat("0.00").format(-(double)sumBDiscnt / 100));
		resultMap.put("XH5",new DecimalFormat("0.00").format((double)(sumFee - sumBDiscnt) / 100));
		resultMap.put("XH6", "");
		
		dataList.add(resultMap);
		
		return dataList;
	}

	/**
	 * 对尚未产生用户信息的集群网手机号码进行处理
	 * 
	 */
	@Override
	public void addGrpNetUserInfo(Integer cycleId) {
		//获取配置的集群网渠道客户信息，虚拟客户ID
		Long custId = 3070000000000000L;
		
		//获取集群网运营商信息
		List<Operators> operList = opersExtMapper.selOperatorsByName("联想懂的通信");
		Operators oper = operList.get(0);
		
		//获取服务配置信息
		List<ServiceStatus> serviceStatusList = serviceStatusExtMapper.selectSvcStatusByIdName(oper.getOperatorsId(), "懂的通信懂陪伴虚拟服务");
		ServiceStatus svcStatus = serviceStatusList.get(0);
		
		//获取集群网统一配置的商品信息
		List<Goods>  goodsList = goodsExtMapper.selGoodsByName("懂的通信懂陪伴虚拟产品");
		Goods goods = goodsList.get(0);
		
		//获取系统时间
		Date sysDate = new Date();
		
		//endDate统一为 2050-12-31 23:59:59
		Date endDate = null;
		String endDateStr = "2050-12-31 23:59:59";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		try {
			endDate = formatter.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 获取尚未产生用户信息的集群网手机号码
		List<BBill> billUser = bBillExtMapper.selectSerialNumberByUserId(cycleId);
		for(int i = 0; i < billUser.size(); i++){
			BBill bill = billUser.get(i);
			
			//入用户信息表 LAO_USER
			LaoUser laoUser = new LaoUser();
			Long userId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.USER_ID));
			laoUser.setUserId(userId);
			laoUser.setChannelCustId(custId);
			laoUser.setMsisdn(bill.getSerialNumber());
			laoUser.setOperatorsId(oper.getOperatorsId());
			laoUser.setAcctTag("0");
			laoUser.setPrepayTag("0");
			laoUser.setRemoveTag("0");
			laoUser.setInDate(sysDate);
			laoUser.setOpenDate(sysDate);
			laoUser.setStateCode(svcStatus.getStateCode());		
			laoUserMapper.insert(laoUser);
			
			//入用户商品表 LAO_USER_GOODS
			LaoUserGoods laoUserGoods = new LaoUserGoods();
			laoUserGoods.setUserId(userId);
			laoUserGoods.setGoodsId(goods.getGoodsId());
			laoUserGoods.setStartDate(sysDate);
			laoUserGoods.setEndDate(endDate);
			laoUserGoods.setBiRulesId(0L);		
			laoUserGoodsMapper.insert(laoUserGoods);
			
			//入用户服务状态表  LAO_USER_SVCSTATE
			LaoUserSvcstate laoUserSvcState = new LaoUserSvcstate();
			laoUserSvcState.setUserId(userId);
			laoUserSvcState.setServiceId(svcStatus.getServiceId());
			laoUserSvcState.setStartDate(sysDate);
			laoUserSvcState.setEndDate(endDate);
			laoUserSvcState.setUpdateTime(sysDate);
			laoUserSvcState.setStateCode(svcStatus.getStateCode());
			laoUserSvcstateMapper.insert(laoUserSvcState);

		}
		
		//更新lao_b_bill表中所有已经存在的user_id的数据
		bBillExtMapper.updateAllUserIdByCycleId(cycleId);
	}
	
	@Override
	public List<Map<String, Object>> selectBillItemByCycleIdByNumber(
			Integer cycleId, String number) {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Map<String,Object> mapParm = new HashMap<String,Object>();
		mapParm.put("cycleId", cycleId);
		mapParm.put("number", number);
		List<BBillExt> list = bBillExtMapper.selectBillItemByCycleIdByNumber(mapParm);
		List<DItem> listDitem = dItemExtMapper.selectAll();
		// 查询出ItemId,pItemId 的集合MAP<账目编码,上级账目编码>
		Map<Integer, Integer> mapInt = new HashMap<Integer, Integer>();
		// 查询出ItemId,ItemName 的集合MAP<账目编码,账目名称>
		Map<Integer, String> mapStr = new HashMap<Integer, String>();
		DItem record = null;
		for (int i = 0; i < listDitem.size(); i++) {
			record = listDitem.get(i);
			mapInt.put(record.getItemId(), record.getpItemId());
		}
		for (int i = 0; i < listDitem.size(); i++) {
			record = listDitem.get(i);
			mapStr.put(record.getItemId(), record.getItemName());
		}
		double count = 0;
		 DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");//格式化设置  
		 /**
		SimpleDateFormat sdfym = new SimpleDateFormat("YYYYMM");
		Calendar calm = Calendar.getInstance();
		calm.add(Calendar.MONTH, -1);
		cycleId = Integer.parseInt(sdfym.format(calm.getTime()));
		String cycleStr = sdfm.format(calm.getTime());
		  */
		for (int i = 0; i < list.size(); i++) {
			BBillExt bBill = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			int itemId = bBill.getItemId();
			map.put("itemName", mapStr.get(mapInt.get(itemId)));// 产品名称
			map.put("feeName", mapStr.get(itemId));// 费用名称
			double discnt = bBill.getFee() + bBill.getbDiscnt()*(-1);
			count = count + discnt;
			map.put("discnt", decimalFormat.format(discnt / 100));// 实际应付金额
			listMap.add(map);
		}
		Map<String, Object> map0 = new HashMap<String, Object>();
		map0.put("itemName", "合计");// 产品名称
		map0.put("feeName", "");// 费用名称
		map0.put("discnt", decimalFormat.format(count / 100));// 实际应付金额
		listMap.add(map0);
		return listMap;
	}
	@Override
	public Map<String, Object> selectBillSumByCycleIdAndNumber(
			Integer cycleId, String number) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapParm = new HashMap<String, Object>();
		mapParm.put("cycleId", cycleId);
		mapParm.put("number", number);
		List<BBillExtPo> billList = bBillExtMapper.selectBillSumByCycleIdAndNumber(mapParm);
		if (billList !=null && billList.size() > 0) {
			double total = 0;
	        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");//格式化设置  
			for (int i = 0; i < billList.size(); i++) {
				BBillExtPo bill = billList.get(i);
				double discnt = bill.getFee() + bill.getbDiscnt()*(-1);
				if ("语音".equals(bill.getpItemName())) {
					map.put("sound", decimalFormat.format(discnt / 100));
					total = total + discnt;
				} else if("短信".equals(bill.getpItemName())){
					map.put("message", decimalFormat.format(discnt / 100));
					total = total + discnt;
				} else if("流量".equals(bill.getpItemName())){
					map.put("flow", decimalFormat.format(discnt / 100));
					total = total + discnt;
				}
			}
			map.put("total", decimalFormat.format(total / 100));
		}
		return map;
	}
}
