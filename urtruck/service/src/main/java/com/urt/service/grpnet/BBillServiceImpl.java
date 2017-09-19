package com.urt.service.grpnet;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.LaoBatchDatadetailDto;
import com.urt.dto.grpnet.BBillDto;
import com.urt.dto.grpnet.DObjectFeecountExtDto;
import com.urt.dto.grpnet.DObjectFeediscntExtDto;
import com.urt.dto.grpnet.GrpnetFeecountDto;
import com.urt.dto.grpnet.GrpnetFeediscntDto;
import com.urt.dto.grpnet.GrpnetImpbillDto;
import com.urt.interfaces.grpnet.BBillService;
import com.urt.mapper.BBillMapper;
import com.urt.mapper.LaoBatchDatadetailPoMapper;
import com.urt.mapper.LaoUserGoodsMapper;
import com.urt.mapper.LaoUserMapper;
import com.urt.mapper.LaoUserSvcstateMapper;
import com.urt.mapper.ext.DObjectFeecountExtMapper;
import com.urt.mapper.ext.DObjectFeediscntExtMapper;
import com.urt.mapper.ext.GoodsExtMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.mapper.ext.OperatorsExtMapper;
import com.urt.mapper.ext.ServiceStatusExtMapper;
import com.urt.po.BBill;
import com.urt.po.DObjectFeecountExt;
import com.urt.po.DObjectFeediscntExt;
import com.urt.po.Goods;
import com.urt.po.GrpnetFeecount;
import com.urt.po.GrpnetFeediscnt;
import com.urt.po.LaoBatchDatadetailPo;
import com.urt.po.LaoUser;
import com.urt.po.LaoUserGoods;
import com.urt.po.LaoUserSvcstate;
import com.urt.po.Operators;
import com.urt.po.ServiceStatus;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

@Service("bBillService")
@Transactional(propagation = Propagation.REQUIRED)
public class BBillServiceImpl implements BBillService {

	@Autowired
	private BBillMapper bBillDao;
	@Autowired
	private DObjectFeecountExtMapper dObjectFeecountExtDao;
	@Autowired
	private DObjectFeediscntExtMapper dObjectExtFeediscntDao;
	@Autowired
	private LaoUserExtMapper laoUserExtDto;
	@Autowired
	private LaoUserMapper laoUserDto;
	@Autowired
	private OperatorsExtMapper opersExtMapper;
	@Autowired
	private ServiceStatusExtMapper serviceStatusExtMapper;
	@Autowired
	private GoodsExtMapper goodsExtMapper;
	@Autowired
	private LaoUserGoodsMapper laoUserGoodsMapper;
	@Autowired
	private LaoUserSvcstateMapper laoUserSvcstateMapper;
	@Autowired
	private LaoBatchDatadetailPoMapper laoBatchDatadetailPoDto;

	@Override
	public int batchInsert(List<GrpnetImpbillDto> listDto) {
		List<BBillDto> listBBillDto = new ArrayList<BBillDto>();
		BBillDto bBillDto = null;
		GrpnetImpbillDto grpnetImpbillDto = null;
		// 关联表查询出账目编码对应的计算方式，得到一个List<账目编码关联计算方式>
		List<DObjectFeecountExt> listDob = dObjectFeecountExtDao.selectAll();
		List<DObjectFeediscntExt> listDobd = dObjectExtFeediscntDao.selectAll();
		for (int i = 0; i < listDto.size(); i++) {
			grpnetImpbillDto = listDto.get(i);
			bBillDto = new BBillDto();
			bBillDto.setItemId(grpnetImpbillDto.getItemId());// 账目编码
			bBillDto.setCycleId(grpnetImpbillDto.getCycleId());// 账期标识
			bBillDto.setRecvTime(new Date());// 生成时间
			bBillDto.setUpdateTime(new Date());// 更新时间
			bBillDto.setUpdateStaffId(grpnetImpbillDto.getUpdateStaffId());// 更新员工
			Long longUse = grpnetImpbillDto.getUseCount();
			bBillDto.setUseCount(longUse);// 使用量
			bBillDto.setUserId(grpnetImpbillDto.getUserId());// 用户ID
			bBillDto.setBillId(grpnetImpbillDto.getBillId());// 账单ID
			bBillDto.setAcctId((long) 0);// 账户ID
			bBillDto.setBalance((long) 0);// 账目余额
			bBillDto.setBillPayTag("1");// 账单销账标志
			bBillDto.setCanpayTag("1");// 账单类型标志
			bBillDto.setAdjustAfter((long) 0);// 账后调整金额
			bBillDto.setAdjustBefore((long) 0);// 账前调整金额
			Short in = (short) (grpnetImpbillDto.getCycleId() % 100);
			bBillDto.setPartitionId(in);// 账单月
			bBillDto.setSerialNumber(grpnetImpbillDto.getSerialNumber());// 手机号
			bBillDto.setaDiscnt((long) 0);
			bBillDto.setRsrvInfo1("");
			bBillDto.setRsrvInfo2("");
			// 1.计算费用
			// 根据账目编码，匹配计算对象标示，查出对应计算方法Map<itemId,GrpnetFeecount>
			// 先判断计算对象标示1.单价*使用量2.流量分段计算
			Integer itemId = grpnetImpbillDto.getItemId();
			DObjectFeecountExt dFeecount = null;
			List<GrpnetFeecount> dFeecountList = null;
			GrpnetFeecount gFeecount = null;
			// 找到对应计算方式的标志。
			boolean bool = false;
			for (int j = 0; j < listDob.size(); j++) {
				if (bool) {
					break;
				}
				dFeecount = listDob.get(j);
				// 先找到对应的itemId
				if (itemId.equals(dFeecount.getAttrId())) {
					dFeecountList = dFeecount.getGrpnetFeecount();
					for (int j2 = 0; j2 < dFeecountList.size(); j2++) {
						gFeecount = dFeecountList.get(j2);
						// 账目编码确定后判断上下限值
						String minV = gFeecount.getMinValue();// 下限值
						Long longminV = Long.parseLong(minV);
						String maxV = gFeecount.getMaxValue();// 上限值
						String minP = gFeecount.getMinPrice();// 下限封顶价
						Double doubleminP = Double.parseDouble(minP);
						String maxP = gFeecount.getMaxPrice();// 上限封顶价
						Double doublemaxP = Double.parseDouble(maxP);
						String unitfee = gFeecount.getUnitFee();// 单价
						Double doubleUnit = Double.parseDouble(unitfee);
						// 上下限值是"-1",计算方式为: 使用量*单价
						if ("-1".equals(minV) && "-1".equals(maxV)) {
							bBillDto.setFee((long) ((longUse * doubleUnit) / 10 + 0.5));// 应收费用
							// bBillDto.setRsrvInfo1(longUse * doubleUnit /
							// 10+"");
							bool = true;
							break;
							// 否则为分段计价
						} else if (longUse >= Long.parseLong(minV)
								&& longUse < Long.parseLong(maxV)) {
							// 计算价格：(使用量-下限值)*单价+下限价
							// 再计算是否超出封顶值
							Double doubleCount = (longUse - longminV)
									* doubleUnit + doubleminP;
							if (doubleCount >= doublemaxP) {
								bBillDto.setFee((long) (doublemaxP / 10 + 0.5));
							} else {
								bBillDto.setFee((long) (doubleCount / 10 + 0.5));
							}
							bool = true;
							break;
						}// else if()... 以后还可能出现其他计算方式
					}

				}
			}
			// 2.计算优惠费用
			DObjectFeediscntExt dFeediscnt = null;
			List<GrpnetFeediscnt> dFeediscntList = null;
			GrpnetFeediscnt gFeediscnt = null;
			// 初始优惠费用为零，对与没有优惠的业务即是。
			bBillDto.setbDiscnt((long) 0);
			bool = false;
			for (int j = 0; j < listDobd.size(); j++) {
				if (bool) {
					break;
				}
				dFeediscnt = listDobd.get(j);
				if (itemId.equals(dFeediscnt.getAttrId())) {
					dFeediscntList = dFeediscnt.getGrpnetFeediscnt();
					for (int j2 = 0; j2 < dFeediscntList.size(); j2++) {
						gFeediscnt = dFeediscntList.get(j2);
						String minV = gFeediscnt.getMinValue();
						String maxV = gFeediscnt.getMaxValue();
						String baseFee = gFeediscnt.getBaseFee();// 优惠基准值
						double longbase = Double.parseDouble(baseFee);
						String dcv = gFeediscnt.getDiviedChildValue();// 折扣分子
						double longdcv = Double.parseDouble(dcv);
						String dpv = gFeediscnt.getDiviedParentValue();// 折扣分母
						double longdpv = Double.parseDouble(dpv);
						String discnt = gFeediscnt.getDiscntFee();// 减免固定值
						double longdi = Double.parseDouble(discnt);
						// -1表明此值不作为限定条件，否则配置限定值
						if ("-1".equals(minV) && "-1".equals(maxV)) {
							// 通用公式：(X-优惠基准值)*折扣分子/折扣分母+减免固定值0:通用(设定/按比例减免或补收/减免或补收固定值)
							double longFee = Double.parseDouble(bBillDto.getFee().toString());
							Long londiscnt = (long) ((longFee - longbase)
									* longdcv / longdpv + longdi + 0.5);
							bBillDto.setbDiscnt(londiscnt);// 计费优惠金额
							bool = true;
							break;
						}// else if()...
					}
				}
			}
			listBBillDto.add(bBillDto);
		}
		List<BBill> listNew = new ArrayList<BBill>();
		BBill recordNew = null;
		for (int j = 0; j < listBBillDto.size(); j++) {
			BBillDto record = listBBillDto.get(j);
			recordNew = new BBill();
			try {
				BeanUtils.copyProperties(recordNew, record);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			listNew.add(recordNew);
		}
		return bBillDao.batchInsert(listNew);
	}
	@Override
	public List<DObjectFeecountExtDto> selectAllFeecount() {
		List<DObjectFeecountExt> list = dObjectFeecountExtDao.selectAll();
		List<DObjectFeecountExtDto> listNew = new ArrayList<DObjectFeecountExtDto>();
		DObjectFeecountExtDto recordNew = null;
		List<GrpnetFeecount> gfeecountList = null;
		List<GrpnetFeecountDto> gfeecountDtoList = null;
		GrpnetFeecount gfeecount = null;
		GrpnetFeecountDto gfeecountDto = null;
		for (int i = 0; i < list.size(); i++) {
			DObjectFeecountExt record = list.get(i);
			recordNew = new DObjectFeecountExtDto();
			try {
				BeanUtils.copyProperties(recordNew, record);
				gfeecountList= record.getGrpnetFeecount();
				gfeecountDtoList = new ArrayList<GrpnetFeecountDto>();
				for (int j = 0; j < gfeecountList.size(); j++) {
					gfeecount = gfeecountList.get(j);
					gfeecountDto = new GrpnetFeecountDto();
					BeanUtils.copyProperties(gfeecountDto, gfeecount);
					gfeecountDtoList.add(gfeecountDto);
				}
				recordNew.setGrFeecountDto(gfeecountDtoList);
				listNew.add(recordNew);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return listNew;
	}

	
	@Override
	public List<DObjectFeediscntExtDto> selectAllFeediscnt() {
		List<DObjectFeediscntExt> list = dObjectExtFeediscntDao.selectAll();
		List<DObjectFeediscntExtDto> listNew = new ArrayList<DObjectFeediscntExtDto>();
		DObjectFeediscntExtDto recordNew = null;
		List<GrpnetFeediscnt> gFeediscntList = null;
		List<GrpnetFeediscntDto> gFeediscntDtoList = null;
		GrpnetFeediscnt gFeediscnt = null;
		GrpnetFeediscntDto gFeediscntDto = null;
		for (int i = 0; i < list.size(); i++) {
			DObjectFeediscntExt record = list.get(i);
			recordNew = new DObjectFeediscntExtDto();
			try {
				BeanUtils.copyProperties(recordNew, record);
				gFeediscntList= record.getGrpnetFeediscnt();
				gFeediscntDtoList = new ArrayList<GrpnetFeediscntDto>();
				for (int j = 0; j < gFeediscntList.size(); j++) {
					gFeediscnt = gFeediscntList.get(j);
					gFeediscntDto = new GrpnetFeediscntDto();
					BeanUtils.copyProperties(gFeediscntDto, gFeediscnt);
					gFeediscntDtoList.add(gFeediscntDto);
				}
				recordNew.setGrFeediscntDto(gFeediscntDtoList);
				listNew.add(recordNew);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return listNew;
	}

	@Override
	public int insertUser(String msisdn) {
		int inUser = 0;
		LaoUser laoUser0 = laoUserExtDto.selectByMsisdn(msisdn);
		if (laoUser0 == null) {
			LaoUser laoUser = new LaoUser();
			// 获取配置的集群网渠道客户信息，虚拟客户ID
			Long custId = 3070000000000000L;
			// 获取集群网运营商信息
			List<Operators> operList = opersExtMapper.selOperatorsByName("联想懂的通信");
			Operators oper = operList.get(0);
			// 获取服务配置信息
			List<ServiceStatus> serviceStatusList = serviceStatusExtMapper
					.selectSvcStatusByIdName(oper.getOperatorsId(),"懂的通信懂陪伴虚拟服务");
			ServiceStatus svcStatus = serviceStatusList.get(0);
			// 获取集群网统一配置的商品信息
			List<Goods> goodsList = goodsExtMapper.selGoodsByName("懂的通信懂陪伴虚拟产品");
			Goods goods = goodsList.get(0);
			// 获取系统时间
			Date sysDate = new Date();
			// endDate统一为 2050-12-31 23:59:59
			Date endDate = null;
			String endDateStr = "2050-12-31 23:59:59";
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				endDate = formatter.parse(endDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Long userId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.USER_ID));
			laoUser.setUserId(userId);// 用户ID：每个用户唯一
			laoUser.setChannelCustId(custId);// 渠道客户ID： 同一企业或公司相同
			laoUser.setMsisdn(msisdn);// 服务号（手机号）
			laoUser.setOperatorsId(oper.getOperatorsId());// 运营商ID
			laoUser.setAcctTag("0");
			laoUser.setPrepayTag("0");
			laoUser.setRemoveTag("0");
			laoUser.setInDate(sysDate);
			laoUser.setOpenDate(sysDate);
			laoUser.setStateCode(svcStatus.getStateCode());
			// 入用户表 LAO_USER
			inUser = laoUserDto.insertSelective(laoUser);
			// 入用户商品表 LAO_USER_GOODS
			LaoUserGoods laoUserGoods = new LaoUserGoods();
			laoUserGoods.setUserId(userId);
			laoUserGoods.setGoodsId(goods.getGoodsId());
			laoUserGoods.setStartDate(sysDate);
			laoUserGoods.setEndDate(endDate);
			laoUserGoods.setBiRulesId(0L);
			laoUserGoodsMapper.insertSelective(laoUserGoods);
			// 入用户服务状态表 LAO_USER_SVCSTATE
			LaoUserSvcstate laoUserSvcState = new LaoUserSvcstate();
			laoUserSvcState.setUserId(userId);
			laoUserSvcState.setServiceId(svcStatus.getServiceId());
			laoUserSvcState.setStartDate(sysDate);
			laoUserSvcState.setEndDate(endDate);
			laoUserSvcState.setUpdateTime(sysDate);
			laoUserSvcState.setStateCode(svcStatus.getStateCode());
			laoUserSvcstateMapper.insertSelective(laoUserSvcState);
		}
		return inUser;
	}
	@Override
	public int updateDatadetail(LaoBatchDatadetailDto dto) {
		LaoBatchDatadetailPo record = new LaoBatchDatadetailPo();
		try {
			BeanUtils.copyProperties(record, dto);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return laoBatchDatadetailPoDto.updateByPrimaryKeySelective(record);
	}

}
