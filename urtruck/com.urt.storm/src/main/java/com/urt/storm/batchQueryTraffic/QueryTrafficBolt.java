package com.urt.storm.batchQueryTraffic;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.urt.dto.LaoBatchDatadetailDto;
import com.urt.dto.LaoDeviceRelDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.traffic.LaoSimDateDetailDto;
import com.urt.dto.traffic.LaoTrafficDetailDto;
import com.urt.dto.traffic.LaoTrafficMmDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.batch.BatchService;
import com.urt.interfaces.traffic.BatchInsertTrafficQueryService;
import com.urt.interfaces.traffic.BatchTrafficQueryService;
import com.urt.interfaces.traffic.TimerTrafficQueryService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

public class QueryTrafficBolt extends BaseRichBolt {
	private static Logger logger = LoggerFactory.getLogger(QueryTrafficBolt.class);

	private TimerTrafficQueryService trafficQueryService;
	private BatchInsertTrafficQueryService batchInsertService;
	private BatchService batchService;
	private UserService userService;
	private BatchTrafficQueryService batchTrafficQueryService;
	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"classpath:applicationContext-dubbo-service.xml");

	private transient OutputCollector collector;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		this.batchInsertService = applicationContext
				.getBean(BatchInsertTrafficQueryService.class);
		this.trafficQueryService = applicationContext
				.getBean(TimerTrafficQueryService.class);
		this.batchService = applicationContext
				.getBean(BatchService.class);
		this.userService = applicationContext
				.getBean(UserService.class);
		this.batchTrafficQueryService = applicationContext
				.getBean(BatchTrafficQueryService.class);
	}

	@Override
	public void execute(Tuple input) {
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>进入QueryTrafficBolt的execute函数");
		String channelCustId = null;
		String userId = null;
		String operatorsId = null;
		String iccid = null;
		String batchId = null;
		LaoUserDto laoUser = new LaoUserDto();
		LaoTrafficMmDto laoTrafficMmDto = null;
		LaoBatchDatadetailDto dto = new LaoBatchDatadetailDto();
		dto.setBatchId(1L);
		LaoSimDateDetailDto laoSimDateDetailDto = null;
		try {
			String str = (String) input.getValue(0);
			if ("".equals(str) || str.length() < 15) {
				dto.setRemark("-99999");//重复数据
				collector.ack(input);
				return;
			}
			logger.debug(str);
			JSONObject json = new JSONObject(str);
			if (!json.isNull("CHANNEL_CUST_ID")) {
				channelCustId = json.getString("CHANNEL_CUST_ID");
			}
			if (!json.isNull("USER_ID")) {
				userId = json.getString("USER_ID");
			}
			if (!json.isNull("OPERATORS_ID")) {
				operatorsId = json.getString("OPERATORS_ID");
			}
			if (!json.isNull("ICCID")) {
				iccid = json.getString("ICCID");
				laoUser.setIccid(json.getString("ICCID"));
			}
			if (!json.isNull("iccid")) {
				iccid = json.getString("iccid");
			}
			if (!json.isNull("MSISDN")) {
				laoUser.setMsisdn(json.getString("MSISDN"));
			}
			if (!json.isNull("STATE_CODE")) {
				laoUser.setStateCode(json.getString("STATE_CODE"));
			}
			if (!json.isNull("batchId")) {
				batchId = json.getString("batchId");
				dto.setBatchId(json.getLong("batchId"));
			}
			
			//在批量详细表中查询，如果同一批次有记录，就不执行
			if(!batchService.duplicateRemoval(iccid, batchId)){
				logger.info(">>>>>>>>>>>>>>>>>>>>>批量详细表中查询，如果同一批次有记录，就不执行");
				dto.setRemark("-99999");//重复数据
				collector.ack(input);
    			return ;
			}
			
			if(StringUtils.isNotBlank(channelCustId) && StringUtils.isNotBlank(userId) &&StringUtils.isNotBlank(operatorsId)){
				laoUser.setChannelCustId(Long.parseLong(channelCustId));
				laoUser.setUserId(Long.parseLong(userId));
				laoUser.setOperatorsId(Integer.parseInt(operatorsId));
			}else if(StringUtils.isNotBlank(iccid)){
				laoUser = userService.getLaoUserDtoByIccid(iccid);
			}
			// 查询日期为昨天
			Calendar calder = Calendar.getInstance();
			calder.add(Calendar.DATE, -1);
			String data = new SimpleDateFormat("yyyy-MM-dd").format(calder.getTime());
			String dataCheck = new SimpleDateFormat("dd").format(calder.getTime());
			String monthCycle = data.substring(0, 7);
			logger.info(">>>>>>>>>>>>>>>>>>>>>调用 流量查询doTrafficQuery方法");
			laoSimDateDetailDto = batchTrafficQueryService.doTrafficQuery(iccid, data, monthCycle);
			if (laoSimDateDetailDto != null) {
				//卡状态变更
				String status = laoSimDateDetailDto.getState();
				if (status!=null && status.trim().length()>0){
					if (!status.equals(laoUser.getStateCode()) && !status.equals("error") &&status.matches("\\d+")) {
						laoUser.setStateCode(status);
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>卡状态变更");
						// user表中卡状态也要更新
						userService.updateUser(laoUser);
						userService.updateUserStatus(laoUser);
					}
				} else {
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>卡状态异常，不操作更新");
				}
				
				//更新lao_user表中deviceId 字段
		        String renewalPolicy = laoSimDateDetailDto.getRenewalPolicy();
		        if ((StringUtils.isNotBlank(renewalPolicy)) && (!renewalPolicy.equals(laoUser.getDeviceId()))) {
		          laoUser.setDeviceId(renewalPolicy);
		          userService.updateUser(laoUser);
		        }
		        // 添加激活时间
		        if (laoUser.getFirstCallTime() == null) {
		        	String dateActivated = laoSimDateDetailDto.getDateActivated();
		        	if (!StringUtils.isBlank(dateActivated)) {
		        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:SS");
		        		Date firstCallTime = sdf.parse(dateActivated);
		        		laoUser.setFirstCallTime(firstCallTime);
				        userService.updateUser(laoUser);
					}
				}
				// 存入每日流量使用表数据库，一天内多次查询则更新
				// 查询今天该条数据是否有记录
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("iccid", laoSimDateDetailDto.getIccid());
				map.put("dataCycle", laoSimDateDetailDto.getData().replace("-", ""));
				LaoTrafficDetailDto laoDetailDto =  batchInsertService.selectByIccidAndCycle(map);
				if (laoDetailDto == null) {
					// 存入每日流量使用表
					LaoTrafficDetailDto laoDetailDtoNew = this.copyGetLaoTrafficDetailDto(laoSimDateDetailDto);
					int in1 = batchInsertService.insertSelective(laoDetailDtoNew);
					if (in1 != 1) {
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>入参："+laoDetailDtoNew.toString());
					}
				} else if(laoDetailDto.getUseCount() == null){
					// 更新每日流量使用表
					if (StringUtils.isNotBlank(laoSimDateDetailDto.getDataRemaining())) {
						BigDecimal big3 = new BigDecimal(laoSimDateDetailDto.getDataRemaining());
						laoDetailDto.setDataRemaining(big3);
					}
					laoDetailDto.setRecvTime(new Date());
					if (StringUtils.isNotBlank(laoSimDateDetailDto.getDayUseCount())) {
						BigDecimal big4 = new BigDecimal(laoSimDateDetailDto.getDayUseCount());
						laoDetailDto.setUseCount(big4);
					}
					laoDetailDto.setRemark("一天内多次查询,更新数据");
					batchInsertService.update(laoDetailDto);
				}
				
				// 更新月流量表，1号则新增
				// 查询本月该条数据是否有记录
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>更新或新增月流量表");
				String dataCycleMm = laoSimDateDetailDto.getMonthCycle().replace("-", "");
				// 每月27 28 29 30 31 日为下个月的账期数据
				String nextMonthDate = "27,28,29,30,31";
				if (nextMonthDate.contains(dataCheck)) {
					Calendar calder1 = Calendar.getInstance();
					calder1.add(Calendar.DATE, -1);
					calder1.add(Calendar.MONTH, 1);
					monthCycle = new SimpleDateFormat("yyyy-MM").format(calder1.getTime());
					dataCycleMm = monthCycle.replace("-", "");//yyyyMM
					laoSimDateDetailDto.setMonthCycle(dataCycleMm);// 将27 28 29 30 31日的数据归为下个账期月里面
				}
				laoTrafficMmDto = trafficQueryService.selectByUseId(laoSimDateDetailDto.getUserId(), dataCycleMm);
				if (laoTrafficMmDto == null) {
					// 新增数据
					LaoTrafficMmDto laoTrafficMmDtoNew = this.copyGetLaoTrafficMmDto(laoSimDateDetailDto);
					
					//查询未发包流量
					BigDecimal notSendFlow = null;
					try {
						notSendFlow = trafficQueryService.getNotSendFlow(laoSimDateDetailDto.getUserId());
					} catch (Exception e) {
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>查询未发包流量异常userId="+laoSimDateDetailDto.getUserId());
						e.printStackTrace();
						notSendFlow = new BigDecimal(0);
					}
					laoTrafficMmDtoNew.setDataRemaining(laoTrafficMmDtoNew.getDataRemaining().add(notSendFlow));
					// 月付灵活共享 和 月付单卡 的只需要使用量  剩余量不要(因导出需要填写-1)
					if ("sign".equals(laoSimDateDetailDto.getMonthSign())) {
						laoTrafficMmDtoNew.setDataRemaining(new BigDecimal(-1));
					}
					trafficQueryService.insertSelective(laoTrafficMmDtoNew);
				} else {
					// 更新数据
					if (StringUtils.isNotBlank(laoSimDateDetailDto.getDataRemaining())) {
						BigDecimal big5 = new BigDecimal(laoSimDateDetailDto.getDataRemaining());
						/*laoTrafficMmDto.setDataRemaining(big5);*/
						BigDecimal notSendFlow = null;
						try {
							notSendFlow = trafficQueryService.getNotSendFlow(laoSimDateDetailDto.getUserId());
						} catch (Exception e) {
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>查询未发包流量异常userId="+laoSimDateDetailDto.getUserId());
							e.printStackTrace();
							notSendFlow = new BigDecimal(0);
						}
						laoTrafficMmDto.setDataRemaining(big5.add(notSendFlow));
					}
					laoTrafficMmDto.setDataAdded(laoSimDateDetailDto.getData().replace("-", ""));//更新累加的流量最新时间
					laoTrafficMmDto.setRemark("更新数据");
					laoTrafficMmDto.setUpdateTime(new Date());
					if (StringUtils.isNotBlank(laoSimDateDetailDto.getMonthUseCount())) {
						BigDecimal big6 = new BigDecimal(laoSimDateDetailDto.getMonthUseCount());
						laoTrafficMmDto.setUseCount(big6);
					}
					// 月付灵活共享 和 月付单卡 的只需要使用量  剩余量不要(因导出需要填写-1)
					if ("sign".equals(laoSimDateDetailDto.getMonthSign())) {
						laoTrafficMmDto.setDataRemaining(new BigDecimal(-1));
					}
					trafficQueryService.updateByPrimaryKeySelective(laoTrafficMmDto);
				}
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>更新或新增LAO_DEVICE_REL表(imei,imsi)");
				// 新增或更新LAO_DEVICE_REL表
				String imei = laoSimDateDetailDto.getImei();
				String imsi = laoSimDateDetailDto.getImsi();
				if (StringUtils.isNotBlank(imei)) {
					LaoDeviceRelDto laoDeviceRelDto = batchTrafficQueryService.selectByUserIdAndidType(laoSimDateDetailDto.getUserId(), "IMEI");
					if (laoDeviceRelDto != null) {
						if (!imei.equals(laoDeviceRelDto.getDeviceId())) {
							// 更新原来的设置为无效,
							laoDeviceRelDto.setValidTag("0");
							laoDeviceRelDto.setUpdateTime(new Date());
							laoDeviceRelDto.setRemark("表中有此卡，imei改变，设置为无效");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>RelLogingname="+laoDeviceRelDto.getRelLogingname());
							batchTrafficQueryService.updateByPrimaryKeySelective(laoDeviceRelDto);
							// 然后新增一条有效
							laoDeviceRelDto.setValidTag("1");
							laoDeviceRelDto.setRelId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID)));
							laoDeviceRelDto.setRecvTime(new Date());
							laoDeviceRelDto.setDeviceId(imei);
							laoDeviceRelDto.setRemark("表中有此卡，imei改变，新增的数据");
							batchTrafficQueryService.insertSelectiveDevice(laoDeviceRelDto);
						}
					} else {
						// 新增一条有效数据
						LaoDeviceRelDto laoDeviceRelDtoNew = new LaoDeviceRelDto();
						laoDeviceRelDtoNew.setRelId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID)));
						laoDeviceRelDtoNew.setUserId(laoSimDateDetailDto.getUserId());
						laoDeviceRelDtoNew.setIccid(laoSimDateDetailDto.getIccid());
						laoDeviceRelDtoNew.setIdType("IMEI");
						laoDeviceRelDtoNew.setValidTag("1");
						laoDeviceRelDtoNew.setDeviceId(imei);
						laoDeviceRelDtoNew.setRecvTime(new Date());
						laoDeviceRelDtoNew.setOperId("system");
						laoDeviceRelDtoNew.setRemark("表中没有此卡，新增的数据");
						laoDeviceRelDtoNew.setUpdateTime(new Date());
						batchTrafficQueryService.insertSelectiveDevice(laoDeviceRelDtoNew);
					}
				} else {
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>查询imei为空");
				}
				if (StringUtils.isNotBlank(imsi)) {
					LaoDeviceRelDto laoDeviceRelDto = batchTrafficQueryService.selectByUserIdAndidType(laoSimDateDetailDto.getUserId(), "IMSI");
					if (laoDeviceRelDto == null) {
						// 新增一条有效数据
						LaoDeviceRelDto laoDeviceRelDtoNew = new LaoDeviceRelDto();
						laoDeviceRelDtoNew.setRelId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID)));
						laoDeviceRelDtoNew.setUserId(laoSimDateDetailDto.getUserId());
						laoDeviceRelDtoNew.setIccid(laoSimDateDetailDto.getIccid());
						laoDeviceRelDtoNew.setIdType("IMSI");
						laoDeviceRelDtoNew.setValidTag("1");
						laoDeviceRelDtoNew.setDeviceId(imsi);
						laoDeviceRelDtoNew.setRecvTime(new Date());
						laoDeviceRelDtoNew.setOperId("system");
						laoDeviceRelDtoNew.setRemark("表中没有此卡，新增的数据");
						laoDeviceRelDtoNew.setUpdateTime(new Date());
						batchTrafficQueryService.insertSelectiveDevice(laoDeviceRelDtoNew);
					}
				} else {
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>查询imsi为空");
				}
				dto.setDealTag("2");
				dto.setRemark("ok");
			} else {
				dto.setDealTag("3");
				dto.setRemark(">>>流量查询异常");
				logger.info(">>>>>>>>>>>>>>>>>>>>>doTrafficQuery方法调用异常返回null");
			}
		} catch (Exception e) {
			dto.setDealTag("3");
			dto.setRemark("抛出异常");
			e.printStackTrace();
		}finally{
			if(!("-99999").equals(dto.getRemark())){
				// 插入批量任务明细表
				dto.setIccid(laoUser.getIccid());
				dto.setMsisdn(laoUser.getMsisdn());
				dto.setChannelCustId(laoUser.getChannelCustId());
				dto.setUserId(laoUser.getUserId());
				dto.setRecvTime(new Date());
				dto.setOperId("admin");
				dto.setUpdateTime(new Date());
				dto.setTradeTypeCode((short)160);
				dto.setDatadetailId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID)));
				logger.info(">>>>>>>>>>>>>>>>>>>>>插入批量任务明细表"+dto.toString());
				batchService.saveBatchDataDetail(dto);
			}
			collector.ack(input);
		}
	}


	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("message"));
	}
	
	
	private LaoTrafficDetailDto copyGetLaoTrafficDetailDto(LaoSimDateDetailDto dto) {
		LaoTrafficDetailDto newDto = new LaoTrafficDetailDto();
		newDto.setBatchId(dto.getBatchId());
		newDto.setChannelCustId(dto.getChannelCustId());
		newDto.setDataCycle(dto.getData().replace("-", ""));
		if (StringUtils.isNotBlank(dto.getDataRemaining())) {
			BigDecimal big1 = new BigDecimal(dto.getDataRemaining());
			newDto.setDataRemaining(big1);
		}
		newDto.setIccid(dto.getIccid());
		newDto.setMsisdn(dto.getMsisdn());
		newDto.setOperatorsId(dto.getOperatorsId());
		newDto.setRecvTime(new Date());
		newDto.setRemark("定时任务操作");
		if (StringUtils.isNotBlank(dto.getDayUseCount())) {
			BigDecimal big2 = new BigDecimal(dto.getDayUseCount());
			newDto.setUseCount(big2);
		}
		newDto.setUserId(dto.getUserId());
		return newDto;
	}
	private LaoTrafficMmDto copyGetLaoTrafficMmDto(LaoSimDateDetailDto dto) {
		LaoTrafficMmDto newDto = new LaoTrafficMmDto();
		newDto.setBatchId(dto.getBatchId());
		newDto.setChannelCustId(dto.getChannelCustId());
		newDto.setDataAdded(dto.getData().replace("-", ""));
		newDto.setDataCycle(dto.getMonthCycle().replace("-", ""));//数据周期 一个月一条记录
		if (StringUtils.isNotBlank(dto.getDataRemaining())) {
			BigDecimal big1 = new BigDecimal(dto.getDataRemaining());
			newDto.setDataRemaining(big1);
		}
		newDto.setIccid(dto.getIccid());
		newDto.setMsisdn(dto.getMsisdn());
		newDto.setOperatorsId(dto.getOperatorsId());
		newDto.setRecvTime(new Date());
		newDto.setRemark(dto.getRemark());
		newDto.setUpdateTime(new Date());
		if (StringUtils.isNotBlank(dto.getMonthUseCount())) {
			BigDecimal big2 = new BigDecimal(dto.getMonthUseCount());
			newDto.setUseCount(big2);
		}
		newDto.setUserId(dto.getUserId());
		return newDto;
	}
	
}
