package com.urt.service.traffic;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.Ability.collect.UserQueryCardStatus;
import com.urt.Ability.collect.UserTrafficQuery;
import com.urt.Ability.collect.sms.SendSmsServerImpl;
import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.CardStatusDto;
import com.urt.dto.LaoCustGroupDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.traffic.LaoTrafficMmDto;
import com.urt.dto.traffic.TrafficQueryDetailsDto;
import com.urt.dto.traffic.TrafficQueryNowDto;
import com.urt.interfaces.authority.TagService;
import com.urt.interfaces.traffic.TimerTrafficQueryService;
import com.urt.interfaces.traffic.TrafficQueryService;
import com.urt.mapper.LaoCustGroupPoMapper;
import com.urt.mapper.OperatorsMapper;
import com.urt.mapper.ServiceStatusMapper;
import com.urt.mapper.ext.GoodsExtMapper;
import com.urt.mapper.ext.LaoCustGroupPoExtMapper;
import com.urt.mapper.ext.LaoCustomerPoExtMapper;
import com.urt.mapper.ext.LaoDataImportLogExtMapper;
import com.urt.mapper.ext.LaoDeviceRelExtMapper;
import com.urt.mapper.ext.LaoSsAccountRolePoExtMapper;
import com.urt.mapper.ext.LaoTrafficMmExtMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.mapper.ext.OperatorPlanExtMapper;
import com.urt.mapper.ext.ServiceStatusExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.Goods;
import com.urt.po.LaoAlmRulePo;
import com.urt.po.LaoCustGroupPo;
import com.urt.po.LaoSsAccountRolePo;
import com.urt.po.LaoTrafficMm;
import com.urt.po.LaoUser;
import com.urt.po.Operators;
import com.urt.po.ServiceStatus;
import com.urt.utils.SpringContextUtils;

@Service("trafficQueryService")
@Transactional(propagation = Propagation.REQUIRED)
public class TrafficQueryServiceImpl implements TrafficQueryService {

	protected static final Logger logger = Logger.getLogger(SendSmsServerImpl.class);
	@Autowired
	private LaoUserExtMapper laoUserDao;
	@Autowired
	private OperatorsMapper operatorsDao;
	@Autowired
	private TagService tagService;
	@Autowired
	private LaoCustGroupPoMapper custGroupPoDao;
	@Autowired
	private LaoTrafficMmExtMapper laoTrafficMmDao;
	@Autowired
	private LaoCustGroupPoExtMapper laoCustGroupPoExtDao;
	@Autowired
	private LaoSsAccountRolePoExtMapper laoSsAccountRolePoExtDao;
	@Autowired
	private ServiceStatusMapper serviceStatusDao;
	@Autowired
	private LaoCustomerPoExtMapper laoCustomerPoExtDao;
	@Autowired
	private LaoDataImportLogExtMapper laoDataImportLogDao;
	@Autowired
	private TimerTrafficQueryService timerTrafficQueryService;
	@Autowired
	private OperatorPlanExtMapper operatorPlanExtDao;
	@Autowired
	private GoodsExtMapper goodsExtMapper;
	@Autowired
	private LaoDeviceRelExtMapper deviceRelExtMapper;
	@Autowired
	private ServiceStatusExtMapper ServiceStatusExtDao;
	
	@Override
	public TrafficQueryNowDto doNowTrafficQuery(String iccid) {
		TrafficQueryNowDto trafficDto = null;
		LaoUser laoUser = laoUserDao.selectByIccid(iccid);
		if (laoUser != null) {
			int operatorId = laoUser.getOperatorsId();
			// 运营商查询
			Operators op = operatorsDao.selectByPrimaryKey(operatorId);
			// 客户姓名，终端类型，型号
			@SuppressWarnings("rawtypes")
			Map map = null;
			try {
				logger.info("------------查询 客户姓名，终端类型，型号，渠道客户名称--------------");
				map = tagService.getIccidLibByIccid(iccid);
			} catch (Exception e2) {
				logger.info("------------调用TagService的getIccidLibByIccid方法异常--------------");
				e2.printStackTrace();

			}
			CardStatusDto cardStatus = null;
			try {
				// 调用卡状态查询方法
				UserQueryCardStatus queryCardStatus = (UserQueryCardStatus) SpringContextUtils
						.getBean(op.getQueryCardStatus());
				logger.info("------------查询 卡状态--------------");
				cardStatus = queryCardStatus.queryCardStatus(iccid);
			} catch (Exception e1) {
				logger.info("------------调用UserQueryCardStatus的queryCardStatus方法异常--------------");
				e1.printStackTrace();
			}
			UserTrafficQuery<?> userTrafficQuery = (UserTrafficQuery<?>) SpringContextUtils
					.getBean(op.getTrafficQueryClass());
			try {
				// 剩余流量查询
				logger.info("------------剩余流量查询--------------");
				trafficDto = userTrafficQuery.trafficNowQuery(iccid, op);
				// 查询未发包流量
				if (trafficDto != null && trafficDto.getDataRemaining() != null) {
					BigDecimal notSendFlow = timerTrafficQueryService.getNotSendFlow(laoUser.getUserId());
					double notSendReam = 0;
					if (notSendFlow != null) {
						notSendReam = notSendFlow.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
					double dataReam = Double.parseDouble(trafficDto.getDataRemaining());
			        DecimalFormat decimalFormat = new DecimalFormat("#0.000");//格式化设置 
					trafficDto.setDataRemaining(decimalFormat.format(notSendReam + dataReam));
					// 套餐展示
//					List<Map<String, Object>> listOper = operatorPlanExtDao.queryForNotSend(iccid);
//					List<PackagePlanDto> listPack = trafficDto.getListPack();
//					if (listOper != null && listOper.size()>0) {
//						for (int i = 0; i < listOper.size(); i++) {
//							Map<String, Object> mapOper = listOper.get(i);
//							PackagePlanDto packDto = new PackagePlanDto();
//							BigDecimal packCountB = (BigDecimal)mapOper.get("PACKCOUNT");
//							if ("0".equals(packCountB+"")) {
//								continue;
//							}
//							packDto.setPackageCount(packCountB+"M");
//							packDto.setPackageName((String)mapOper.get("PLANNAME"));
//							packDto.setPackageRemain(packDto.getPackageCount());
//							packDto.setPackageEndTime("未使用");
//							packDto.setPackageStartTime("未使用");
//							String timeUnit = (String)mapOper.get("TIMEUNIT");
//							if ("0".equals(timeUnit)) {
//								if (mapOper.get("TIMELENGTH") !=null) {
//									packDto.setPackageTimeLenth((BigDecimal)mapOper.get("TIMELENGTH")+"天");
//								} else {
//									continue;
//								}
//							} else if("1".equals(timeUnit)){
//								if (mapOper.get("TIMELENGTH") !=null) {
//									packDto.setPackageTimeLenth((BigDecimal)mapOper.get("TIMELENGTH")+"月");
//								} else {
//									continue;
//								}
//							} else if("2".equals(timeUnit)){
//								if (mapOper.get("TIMELENGTH") !=null) {
//									packDto.setPackageTimeLenth((BigDecimal)mapOper.get("TIMELENGTH")+"季");
//								} else {
//									continue;
//								}
//							} else if("3".equals(timeUnit)){
//								if (mapOper.get("TIMELENGTH") !=null) {
//									packDto.setPackageTimeLenth((BigDecimal)mapOper.get("TIMELENGTH")+"年");
//								} else {
//									continue;
//								}
//							}
//							listPack.add(packDto);
//						}
//					}
				}
			} catch (Exception e) {
				logger.info("------------调用UserTrafficQuery的trafficNowQuery方法异常--------------");
				e.printStackTrace();
				trafficDto = new TrafficQueryNowDto();
			}
			trafficDto.setIccid(iccid);
			trafficDto.setOperatorId(operatorId+"");
			trafficDto.setMsisdn(laoUser.getMsisdn());// 联通的没有
			trafficDto.setOperatorName(op.getOperatorsName());
			if (cardStatus != null && cardStatus.getCardStatus() != null) {
				if ("2".equals(operatorId + "")||"5".equals(operatorId + "")) {
					ServiceStatus ss = ServiceStatusExtDao.selectByStatechangeId(cardStatus.getCardStatus());
					if (ss != null) {
						trafficDto.setState(ss.getServiceName());
					}
				} else {
					ServiceStatus ss = serviceStatusDao.selectByPrimaryKey(cardStatus.getCardStatus());
					if (ss != null) {
						trafficDto.setState(ss.getServiceName());
					}
				}
			}
			if (map != null) {
				trafficDto.setUserName((String) map.get("CUST_NAME"));
				trafficDto.setType((String) map.get("VAL1"));
				trafficDto.setVersion((String) map.get("VAL2"));
				LaoCustGroupPo custGroupPo = custGroupPoDao.selectByPrimaryKey(laoUser.getChannelCustId());
				if (custGroupPo != null) {
					// 渠道客户名称
					trafficDto.setChannelCustName(custGroupPo.getCustName());
				}
			}

		} else {
			trafficDto = new TrafficQueryNowDto();
			trafficDto.setExpMsg("该用户不存在，请确认ICCID或IMSI是否正确！");
		}

		return trafficDto;
	}

	@Override
	public List<TrafficQueryDetailsDto> doDayTrafficQuery(String iccid, String date) {
		List<TrafficQueryDetailsDto> listDto = null;
		LaoUser laoUser = laoUserDao.selectByIccid(iccid);
		if (laoUser != null) {
			try {
				int operatorId = laoUser.getOperatorsId();
				Operators op = operatorsDao.selectByPrimaryKey(operatorId);
				UserTrafficQuery<?> userTrafficQuery = (UserTrafficQuery<?>) SpringContextUtils
						.getBean(op.getTrafficQueryClass());
				listDto = userTrafficQuery.trafficDayQuery(iccid, date, op);
			} catch (Exception e) {
				e.printStackTrace();
				return listDto;
			}
		}
		return listDto;
	}

	@Override
	public List<TrafficQueryDetailsDto> doMonthTrafficQuery(String iccid, String date) {
		List<TrafficQueryDetailsDto> listDto = null;
		LaoUser laoUser = laoUserDao.selectByIccid(iccid);
		if (laoUser != null) {
			try {
				int operatorId = laoUser.getOperatorsId();
				Operators op = operatorsDao.selectByPrimaryKey(operatorId);
				UserTrafficQuery<?> userTrafficQuery = (UserTrafficQuery<?>) SpringContextUtils
						.getBean(op.getTrafficQueryClass());
				listDto = userTrafficQuery.trafficMonthQuery(iccid, date + "-01", op);

			} catch (Exception e) {
				e.printStackTrace();
				return listDto;
			}
		}
		return listDto;
	}

	@Override
	public List<LaoTrafficMmDto> selectByChannelCustId(Long channelCustId) {
		List<LaoTrafficMm> list = laoTrafficMmDao.selectByChannelCustId(channelCustId);
		List<LaoTrafficMmDto> listDto = new ArrayList<LaoTrafficMmDto>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				LaoTrafficMm lao = list.get(i);
				LaoTrafficMmDto laoDto = new LaoTrafficMmDto();
				BeanMapper.copy(lao, laoDto);
				listDto.add(laoDto);
			}
		}
		return listDto;
	}

	@Override
	public List<LaoTrafficMmDto> selectByTraffic(long channelCustId, String type, String dataCycle, Integer integer,
			Integer integer2) {
		List<Map<String, Object>> list = null;
		if ("1".equals(type)) {
			list = laoTrafficMmDao.selectUseByTraffic(channelCustId, dataCycle, integer, integer2);
		} else {
			list = laoTrafficMmDao.selectRemainByTraffic(channelCustId, dataCycle, integer, integer2);
		}
		List<LaoTrafficMmDto> listDto = new ArrayList<LaoTrafficMmDto>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> mapObj = list.get(i);
				LaoTrafficMmDto laoDto = new LaoTrafficMmDto();
				long batchId = Long.parseLong(mapObj.get("BATCH_ID").toString());
				laoDto.setBatchId(batchId);
				laoDto.setIccid(mapObj.get("ICCID").toString());
				String msisdn = "";
				if (mapObj.get("MSISDN") != null) {
					msisdn = mapObj.get("MSISDN").toString();
				}
				laoDto.setMsisdn(msisdn);
				laoDto.setDataAdded(mapObj.get("DATA_ADDED").toString());
				if (mapObj.get("USE_COUNT") != null) {
					BigDecimal bigUseCount = new BigDecimal(mapObj.get("USE_COUNT").toString());
					laoDto.setUseCount(bigUseCount);
				}
				laoDto.setUpdateTime((Date) mapObj.get("UPDATE_TIME"));
				if (mapObj.get("DATA_REMAINING") != null) {
					BigDecimal dataRemaining = new BigDecimal(mapObj.get("DATA_REMAINING").toString());
					laoDto.setDataRemaining(dataRemaining);
				}
				String staticNameA = "";
				if (mapObj.get("STATICNAMEA") != null) {
					staticNameA = mapObj.get("STATICNAMEA").toString();
				}
				laoDto.setStaticNameA(staticNameA);
				String staticNameB = "";
				if (mapObj.get("STATICNAMEB") != null) {
					staticNameB = mapObj.get("STATICNAMEB").toString();
				}
				laoDto.setStaticNameB(staticNameB);
				Date activeDate = (Date) mapObj.get("START_USE_DATE");
				Date endDate = (Date) mapObj.get("END_DATE");
				String cardStatus = (String) mapObj.get("CARDSTATUS");
				String deviceType = (String) mapObj.get("DEVICETYPE");
				String imei = (String) mapObj.get("IMEI");
				String imsi = (String) mapObj.get("IMSI");
				laoDto.setActiveDate(activeDate);
				laoDto.setEndDate(endDate);
				laoDto.setCardStatus(cardStatus);
				laoDto.setType(deviceType);
				laoDto.setImei(imei);
				laoDto.setImsi(imsi);
				listDto.add(laoDto);
			}
		}
		return listDto;
	}

	@Override
	public List<LaoTrafficMmDto> selectByTraffic(Map<String, Object> map) {
		List<Map<String, Object>> list = laoTrafficMmDao.selectByTraffic(map);
		List<LaoTrafficMmDto> listDto = new ArrayList<LaoTrafficMmDto>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> mapObj = list.get(i);
				LaoTrafficMmDto laoDto = new LaoTrafficMmDto();
				long batchId = Long.parseLong(mapObj.get("BATCH_ID").toString());
				laoDto.setBatchId(batchId);
				laoDto.setIccid(mapObj.get("ICCID").toString());
				String msisdn = "";
				if (mapObj.get("MSISDN") != null) {
					msisdn = mapObj.get("MSISDN").toString();
				}
				laoDto.setMsisdn(msisdn);
				laoDto.setDataAdded(mapObj.get("DATA_ADDED").toString());
				if (mapObj.get("USE_COUNT") != null) {
					BigDecimal bigUseCount = new BigDecimal(mapObj.get("USE_COUNT").toString());
					laoDto.setUseCount(bigUseCount);
				}
				laoDto.setUpdateTime((Date) mapObj.get("UPDATE_TIME"));
				if (mapObj.get("DATA_REMAINING") != null) {
					BigDecimal dataRemaining = new BigDecimal(mapObj.get("DATA_REMAINING").toString());
					laoDto.setDataRemaining(dataRemaining);
				}
				String staticNameA = "";
				if (mapObj.get("STATICNAMEA") != null) {
					staticNameA = mapObj.get("STATICNAMEA").toString();
				}
				laoDto.setStaticNameA(staticNameA);
				String staticNameB = "";
				if (mapObj.get("STATICNAMEB") != null) {
					staticNameB = mapObj.get("STATICNAMEB").toString();
				}
				laoDto.setStaticNameB(staticNameB);
				listDto.add(laoDto);
			}
		}
		return listDto;
	}

	@Override
	public List<LaoCustGroupDto> selectAll() {
		List<LaoCustGroupPo> list = laoCustGroupPoExtDao.selectAll();
		List<LaoCustGroupDto> listDto = new ArrayList<LaoCustGroupDto>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				LaoCustGroupPo lao = list.get(i);
				LaoCustGroupDto laoDto = new LaoCustGroupDto();
				BeanMapper.copy(lao, laoDto);
				listDto.add(laoDto);
			}
		}
		return listDto;
	}

	@Override
	public Map<String, Object> selectMonthFlowCount(Long channelCustId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> monthFlowMap = laoTrafficMmDao.selectMonthFlowCount(channelCustId);
		List<String> dayList = new ArrayList<String>();
		Map<String, Double> sumMap = new HashMap<String, Double>();
		for (Map<String, Object> dayFlowMap : monthFlowMap) {
			String day = (String) dayFlowMap.get("DATCYCLE");
			BigDecimal flowSum = (BigDecimal) dayFlowMap.get("FLOWSUM");
			String key = day.substring(day.length() - 2);
			Double sum = flowSum.doubleValue();
			sumMap.put(key, sum / (1024 * 1024));
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date curDate = new Date();
		for (int i = 0; i < 15; i++) {
			String time = df.format(curDate.getTime() - (14 - i) * 24 * 60 * 60 * 1000);
			String timeKey = time.substring(time.length() - 2);
			dayList.add(timeKey);
		}
		map.put("sumMap", sumMap);
		map.put("dayList", dayList);
		return map;
	}
	//近15天流量使用按M显示
	@Override
	public Map<String, Object> selectMonthFlowCountMB(Long channelCustId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> monthFlowMap = laoTrafficMmDao.selectMonthFlowCount(channelCustId);
		List<String> dayList = new ArrayList<String>();
		Map<String, Double> sumMap = new HashMap<String, Double>();
		for (Map<String, Object> dayFlowMap : monthFlowMap) {
			String day = (String) dayFlowMap.get("DATCYCLE");
			BigDecimal flowSum = (BigDecimal) dayFlowMap.get("FLOWSUM");
			String key = day.substring(day.length() - 2);
			Double sum = flowSum.doubleValue();
			sumMap.put(key, sum / 1024);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date curDate = new Date();
		for (int i = 0; i < 15; i++) {
			String time = df.format(curDate.getTime() - (14 - i) * 24 * 60 * 60 * 1000);
			String timeKey = time.substring(time.length() - 2);
			dayList.add(timeKey);
		}
		map.put("sumMap", sumMap);
		map.put("dayList", dayList);
		return map;
	}

	@Override
	public List<Map<String, Object>> selectMaxFlowCount(Long channelCustId) {
		List<Map<String, Object>> maxFlows = laoTrafficMmDao.selectMaxFlowCount(channelCustId);
		return maxFlows;
	}

	@Override
	public List<Map<String, Object>> selectMinFlowCount(Long channelCustId) {
		List<Map<String, Object>> minFlows = laoTrafficMmDao.selectMinFlowCount(channelCustId);
		return minFlows;
	}

	@Override
	public long queryListByUserId(Long acconutId) {
		List<LaoSsAccountRolePo> list = laoSsAccountRolePoExtDao.queryListByUserId(acconutId);
		return list.get(0).getRoleId();
	}

	@Override
	public int selectCountByChannel(Map<String, Object> map) {
		return laoTrafficMmDao.selectCountByChannel(map);
	}

	@Override
	public Map<String, Object> selectPrefixFlow(Long channelCustId) {
		DecimalFormat df = new DecimalFormat("0.000"); // 格式化double
		Map<String, Object> maxMinMap = new HashMap<String, Object>();
		Map<String, Object> monthRateMap = new HashMap<String, Object>();
		Map<String, BigDecimal> yMap = new HashMap<String, BigDecimal>();
		List<String> xFlow = new ArrayList<String>();// 上月流量分布图x轴数据
		List<String> yRate = new ArrayList<String>();// 上月流量分布图y轴数据
		Map<String, Object> flowMap = laoTrafficMmDao.selectPrefixFlow(channelCustId);
		// select min(to_number(use_count)) minflow,max(to_number(use_count))
		// maxflow,avg(to_number(use_count)) avgflow，count(1) usesum from
		// lao_traffic_mm
		// where channel_cust_id=#{channelCustId} and
		// data_cycle=to_char(add_months(sysdate,-1),'yyyymm')
		BigDecimal useSum = (BigDecimal) flowMap.get("USESUM");// 上月流量用户总数量
		// BigDecimal minFlow = (BigDecimal)
		// flowMap.get("MINFLOW");//上月所有用户中用的最小流量
		BigDecimal maxFlow = (BigDecimal) flowMap.get("MAXFLOW");// 上月所有用户中用的最大流量
		long mflong = 0;
		if (maxFlow != null) {
			mflong = maxFlow.longValue();
		}
		// BigDecimal avgFlow = (BigDecimal)
		// flowMap.get("AVGFLOW");//上月所有用户中用的平均流量
		if (useSum != null && useSum.intValue() != 0) {
			BigDecimal maxOfMinFlow = laoTrafficMmDao.selectMaxMinFiveFlow(channelCustId, useSum.longValue());// 前5%中流量最大值
			// select max(to_number(use_count)) from
			// (select use_count,ROWNUM from
			// (select to_number(use_count) use_count from lao_traffic_mm
			// where channel_cust_id=#{channelCustId,jdbcType=DECIMAL} and
			// data_cycle =to_char(add_months(sysdate,-1),'yyyymm') order by
			// to_number(use_count) ASC) D)
			// where ROWNUM
			// <![CDATA[<]]>#{useSum,jdbcType=DECIMAL}<![CDATA[*]]>0.05
			long maxmf = 0, minmf = 0;
			double minmfd = 0, maxmfd = 0;
			Long maxmcount = null, minmcount = null;
			if (maxOfMinFlow != null) {
				maxmf = maxOfMinFlow.longValue();// 前百分之五中流量最大值
				maxmfd = maxOfMinFlow.doubleValue();// 前百分之五中流量最大值
			}
			BigDecimal maxMinCount = laoTrafficMmDao.selectMaxMinFiveCount(channelCustId, maxmf);// 前5%中的用户总数
																									// select
																									// count(1)
																									// sumPrefixNum
																									// from
																									// lao_traffic_mm
																									// where
																									// channel_cust_id=#{channelCustId}
																									// and
																									// data_cycle=to_char(add_months(sysdate,-1),'yyyymm')
																									// and
																									// to_number(use_count)
																									// <![CDATA[<=]]>#{maxMinFlow,jdbcType=DECIMAL}
																									// </select>
			BigDecimal minOfMaxFlow = laoTrafficMmDao.selectMinMaxFiveFlow(channelCustId, useSum.longValue());// 后百分之五中流量最小值
			// select min(to_number(use_count)) from
			// (select use_count,ROWNUM from
			// (select to_number(use_count) use_count from lao_traffic_mm
			// where channel_cust_id=#{channelCustId,jdbcType=DECIMAL} and
			// data_cycle =to_char(add_months(sysdate,-1),'yyyymm') order by
			// to_number(use_count) DESC) D)
			// where ROWNUM
			// <![CDATA[<]]>#{useSum,jdbcType=DECIMAL}<![CDATA[*]]>0.05
			if (minOfMaxFlow != null) {
				minmf = minOfMaxFlow.longValue();
				minmfd = minOfMaxFlow.doubleValue();
			}
			if (maxMinCount != null) {
				maxmcount = maxMinCount.longValue();
			}
			BigDecimal minMaxCount = laoTrafficMmDao.selectMinMaxFiveCount(channelCustId, minmf);// 后5%中的用户总数
			// select count(1) sumPrefixNum
			// from lao_traffic_mm
			// where channel_cust_id=#{channelCustId} and
			// data_cycle=to_char(add_months(sysdate,-1),'yyyymm') and
			// to_number(use_count) <![CDATA[>=]]>#{minMaxFlow,jdbcType=DECIMAL}
			// </select>
			if (minMaxCount != null) {
				minmcount = minMaxCount.longValue();
			}
			Double avgFlowOfEight = (minmfd - maxmfd) / 8;// 共分十组，前5%是第一组，后5%是第十组，中间分八组
			monthRateMap.put("channelCustId", channelCustId);
			monthRateMap.put("maxMinFlow", maxmf);
			monthRateMap.put("minMaxFlow", minmf);
			monthRateMap.put("avgFlowOfEight", avgFlowOfEight);
			List<Map<String, Object>> listMap = laoTrafficMmDao.selectEightCount(monthRateMap);// 把用户中前百分之五和后百分之五之间的百分之九十分8组，8组组名和人数
			// select groupcount,sum(1) sum from (select
			// FLOOR((to_number(use_count)<![CDATA[-]]>#{maxMinFlow,jdbcType=DECIMAL})<![CDATA[/]]>#{avgFlowOfEight,jdbcType=DECIMAL})
			// groupcount from lao_traffic_mm
			// where channel_cust_id=#{channelCustId} and
			// data_cycle=to_char(add_months(sysdate,-1),'yyyymm') AND
			// to_number(use_count) <![CDATA[<]]> #{minMaxFlow,jdbcType=DECIMAL}
			// AND to_number(use_count)
			// <![CDATA[>]]>#{maxMinFlow,jdbcType=DECIMAL})
			// group by groupcount
			// List<Map<String, Object>> listMap =
			// laoTrafficMmDao.selectEightCount(channelCustId,
			// maxOfMinFlow.longValue(), minOfMaxFlow.longValue(),
			// avgFlowOfEight);
			for (Map<String, Object> gMap : listMap) {
				yMap.put(gMap.get("GROUPCOUNT").toString(), (BigDecimal) gMap.get("SUM"));// {组名，组中总人数}
			}
			xFlow.add(0, "<=" + maxmf / 1024 + "MB");
			for (int i = 1; i < 8; i++) {
				xFlow.add(i, "<=" + (maxmf + avgFlowOfEight.longValue() * i) / 1024 + "MB");
			}

			xFlow.add(8, "<=" + minmf / 1024 + "MB");
			xFlow.add(9, "<=" + mflong / 1024 + "MB");
			for (int i = 0; i < 10; i++) {
				if (i == 0) {
					yRate.add(i, maxmcount == null ? "0" : maxmcount.toString());
				} else if (i == 9) {
					yRate.add(i, minmcount == null ? "0" : minmcount.toString());
				} else {
					BigDecimal sum = yMap.get((i - 1) + "");
					if (sum != null) {
						yRate.add(i, df.format(sum.doubleValue()));
					} else {
						yRate.add(i, 0 + "");
					}
				}
			}
		}
		maxMinMap.put("xFlow", xFlow);
		maxMinMap.put("yRate", yRate);
		return maxMinMap;
	}

	@Override
	public Map<String, Object> selectStaticName(Long custId) {
		return laoCustomerPoExtDao.selectStaticName(custId);
	}

	@Override
	public Map<String, Object> selectDataSpread(long channelCustId, String type, String month, Integer useCount1,
			Integer useCount2, String value1, String value2,String operatorId,String goodsId) {
		// DecimalFormat df = new DecimalFormat();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, BigDecimal> yMap = new HashMap<String, BigDecimal>();
		// String dataCycle = new SimpleDateFormat("yyyyMM").format(new Date());
		map.put("channelCustId", channelCustId);
		map.put("dataCycle", month);
		// 该企业下总人数
		int countAll = laoTrafficMmDao.selectCountByChannel(map);
		Map<String, Object> countMap;
		if ("1".equals(type)) {
			countMap = laoTrafficMmDao.selectUseData(channelCustId, useCount1, month,
					"-2".equals(useCount2 + "") ? null : useCount2, value1, value2,operatorId,goodsId);
		} else {
			countMap = laoTrafficMmDao.selectRemainData(channelCustId, useCount1, month,
					"-2".equals(useCount2 + "") ? null : useCount2, value1, value2,operatorId,goodsId);
		}
		BigDecimal sum = (BigDecimal) countMap.get("SUM");// 当前企业下，按条件查询这个月用户总数量
		BigDecimal maxFlow = (BigDecimal) countMap.get("MAXFLOW");// 最大流量值
		BigDecimal all = new BigDecimal(countAll);
		BigDecimal rate = null;
		if (countAll != 0) {
			rate = (sum.multiply(new BigDecimal(100))).divide(all, 0, BigDecimal.ROUND_HALF_EVEN);
		}
		// 获得echarts图标展示的x,y
		List<String> xFlow = new ArrayList<String>();// 上月流量分布图x轴数据
		List<String> yRate = new ArrayList<String>();// 上月流量分布图y轴数据
		if (sum != null && sum.intValue() != 0) {
			// 如果最大流量为零，只分一组
			if (maxFlow == null || maxFlow.intValue() == 0) {
				xFlow.add(0, "0");
				yRate.add(0, sum.toString());
			} else {
				createEchartsData(channelCustId, type, month, useCount1, useCount2, value1, value2, yMap, sum, xFlow,
						yRate,operatorId,goodsId);
			}
		}
		resultMap.put("count", sum);
		resultMap.put("score", rate == null ? "0.0" : rate.doubleValue());
		resultMap.put("xStr", xFlow);
		resultMap.put("yStr1", yRate);
		return resultMap;
	}

	// 获取echarts表格需要的数据
	private void createEchartsData(long channelCustId, String type, String month, Integer useCount1, Integer useCount2,
			String value1, String value2, Map<String, BigDecimal> yMap, BigDecimal sum, List<String> xFlow,
			List<String> yRate, String operatorId, String goodsId) {
		// 前百分之五的数据
		BigDecimal maxOfMinFlow = null;
		if ("1".equals(type)) {
			maxOfMinFlow = laoTrafficMmDao.selectMaxFlowPrefiveOfUse(channelCustId, month, sum.longValue(), useCount1,
					useCount2, value1, value2,operatorId,goodsId);// 前百分之五中流量最大值
		} else {
			maxOfMinFlow = laoTrafficMmDao.selectMaxFlowPrefiveOfUseOfRe(channelCustId, month, sum.longValue(),
					useCount1, useCount2, value1, value2,operatorId,goodsId);
		}
		long maxmf = 0, // 前百分之五中流量最大值的long型
				minmf = 0; // 后百分之五中流量最小值的long型
		double minmfd = 0, // 后百分之五中流量最小值的double型
				maxmfd = 0;// 前百分之五中流量最大值double型
		Long maxmcount = null;// 前百分之五中的用户总数
		Long minmcount = null;// 后百分之五中的用户总数
		if (maxOfMinFlow != null) {
			maxmf = maxOfMinFlow.longValue();
			maxmfd = maxOfMinFlow.doubleValue();
		}

		// 后百分之五的数据
		BigDecimal minOfMaxFlow = null;
		if ("1".equals(type)) {
			minOfMaxFlow = laoTrafficMmDao.selectMinFlowBack(channelCustId, month, sum.longValue(), useCount1,
					useCount2, value1, value2,operatorId,goodsId);// 后百分之五中流量最小值
		} else {
			minOfMaxFlow = laoTrafficMmDao.selectMinFlowBackOfRe(channelCustId, month, sum.longValue(), useCount1,
					useCount2, value1, value2,operatorId,goodsId);// 后百分之五中流量最小值
		}
		if (minOfMaxFlow != null) {
			minmf = minOfMaxFlow.longValue();
			minmfd = minOfMaxFlow.doubleValue();
		}

		BigDecimal maxMinCount = null;
		if ("1".equals(type)) {
			maxMinCount = laoTrafficMmDao.selectPreFiveCount(channelCustId, month, maxmf, useCount1, useCount2, value1,
					value2,operatorId,goodsId);// 前5%中的用户总数
		} else {
			maxMinCount = laoTrafficMmDao.selectPreFiveCountOfre(channelCustId, month, maxmf, useCount1, useCount2,
					value1, value2,operatorId,goodsId);// 前5%中的用户总数
		}
		if (maxMinCount != null) {
			maxmcount = maxMinCount.longValue();
		}
		BigDecimal minMaxCount = null;
		if ("1".equals(type)) {
			minMaxCount = laoTrafficMmDao.selectBackFiveCount(channelCustId, month, minmf, useCount1, useCount2, value1,
					value2,operatorId,goodsId);// 后5%中的用户总数
		} else {
			minMaxCount = laoTrafficMmDao.selectBackFiveCountOfRe(channelCustId, month, minmf, useCount1, useCount2,
					value1, value2,operatorId,goodsId);
		}
		if (maxMinCount != null) {
			minmcount = minMaxCount.longValue();
		}

		// 中间百分之九十的数据
		Double avgFlowOfEight = (minmfd - maxmfd) / 8;// 共分十组，前5%是第一组，后5%是第十组，中间分八组
		List<Map<String, Object>> listMap = null;
		if ("1".equals(type)) {
			listMap = laoTrafficMmDao.selectEight(channelCustId, month, maxmf, minmf, avgFlowOfEight, useCount1,
					useCount2, value1, value2,operatorId,goodsId);// 把用户中前百分之五和后百分之五之间的百分之九十分8组，8组组名和人数
		} else {
			listMap = laoTrafficMmDao.selectEightOfRe(channelCustId, month, maxmf, minmf, avgFlowOfEight, useCount1,
					useCount2, value1, value2,operatorId,goodsId);// 把用户中前百分之五和后百分之五
		}
		for (Map<String, Object> gMap : listMap) {
			yMap.put(gMap.get("GROUPCOUNT").toString(), (BigDecimal) gMap.get("SUM"));// {组名，组中总人数}
		}

		if (minmf < 1024) {
			xFlow.add(0, "<=" + maxmf + "KB");
			for (int i = 1; i < 8; i++) {
				xFlow.add(i, "<" + (maxmf + avgFlowOfEight.longValue() * i) + "KB");
			}

			xFlow.add(8, "<" + minmf + "KB");
			xFlow.add(9, ">=" + minmf + "KB");
		} else {
			xFlow.add(0, "<=" + maxmf / 1024 + "MB");
			for (int i = 1; i < 8; i++) {
				xFlow.add(i, "<" + (maxmf + avgFlowOfEight.longValue() * i) / 1024 + "MB");
			}

			xFlow.add(8, "<" + minmf / 1024 + "MB");
			xFlow.add(9, ">=" + minmf / 1024 + "MB");
		}

		for (int i = 0; i < 10; i++) {
			if (i == 0) {
				yRate.add(i, maxmcount == null ? "0" : maxmcount.toString());
			} else if (i == 9) {
				yRate.add(i, minmcount == null ? "0" : minmcount.toString());
			} else {
				BigDecimal s = yMap.get((i - 1) + "");
				if (s != null) {
					yRate.add(i, s.toString());
				} else {
					yRate.add(i, 0 + "");
				}
			}
		}
	}

	@Override
	public Map<String, String> getUpdateTimeInfo() {
		List<Map<String, Object>> infos = laoDataImportLogDao.selectUpdateTime();
		Map<String, String> info = new HashMap<String, String>();
		if (infos != null && infos.size() > 0) {
			for (Map<String, Object> iterm : infos) {
				String oreratorName = (String) iterm.get("OPERATORNAME");
				String updatetime = (String) iterm.get("UPDATETIME");
				info.put(oreratorName, updatetime);
			}
		}
		return info;
	}

	// Bigdecimal类型流量从kb转化为MB，然后在取整，再转化为kb
	public long translate(BigDecimal bigData) {
		BigDecimal tranRate = new BigDecimal(1024);
		BigDecimal divideData = bigData.divide(tranRate, 0, BigDecimal.ROUND_HALF_EVEN);
		return divideData.longValue();
	}

	public static void main(String[] args) {
//		BigDecimal b1 = new BigDecimal(1062);
//		TrafficQueryServiceImpl tr=new TrafficQueryServiceImpl();
//		long a = tr.translate(b1);
//		System.out.println(a);
	}

	@Override
	public boolean bIsLegalIccId(String iccid, Long custId) {
		LaoUser laoUser = laoUserDao.selectByIccid(iccid);
		if (laoUser == null)
			return false;
		if (custId.equals(laoUser.getChannelCustId()) || (custId.equals(laoUser.getCustId()))) {
			return true;
		}
		List<Long> lcustId = laoCustomerPoExtDao.queryCustId(custId);
		if (lcustId!=null &&(lcustId.contains(laoUser.getChannelCustId()) || lcustId.contains(laoUser.getCustId()))) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<GoodsDto> selectGoodsByOperator(String operatorId) {
		List<Goods> goods=goodsExtMapper.selectGoodsByOperator(Integer.parseInt(operatorId));
		return ConversionUtil.poList2dtoList(goods, GoodsDto.class);
	}

	@Override
	public String getImsiByIccid(String iccid) {
		return deviceRelExtMapper.getImsiByIccid(iccid);
	}

	@Override
	public String getIccidByImsi(String imsi) {
		return deviceRelExtMapper.getIccidByImsi(imsi);
	}

	@Override
	public Map<String, Object> queryPage(Map<String, Object> map,int pageNo, int pageSize) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", map);
		page.setParams(params);
		List<Map<String, Object>> alarms = laoTrafficMmDao.queryPage(page);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", alarms);
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
		return resultMap;
	}

}
