package com.urt.service.alarm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jetty.util.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.json.JSONObject;
import com.urt.Ability.collect.UserQueryCardStatus;
import com.urt.common.util.ResultMsg;
import com.urt.dto.LaoAlmElemValueDto;
import com.urt.dto.LaoAlmRuleDto;
import com.urt.dto.LaoAlmRuleElemDto;
import com.urt.dto.LaoAlmRuleLogDto;
import com.urt.dto.LaoAlmRuleTypeDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.MailDto;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.alarm.AlarmService;
import com.urt.interfaces.sendMessage.SendMessageService;
import com.urt.mapper.LaoAlmElemValuePoMapper;
import com.urt.mapper.LaoAlmRuleLogPoMapper;
import com.urt.mapper.LaoAlmRulePoMapper;
import com.urt.mapper.LaoAlmRuleTypePoMapper;
import com.urt.mapper.LaoCustomerPoMapper;
import com.urt.mapper.LaoSmsTmplMapper;
import com.urt.mapper.OperatorPlanMapper;
import com.urt.mapper.ext.IccidLibExtMapper;
import com.urt.mapper.ext.LaoAlmElemValueExtPoMapper;
import com.urt.mapper.ext.LaoAlmRuleElemPoExtMapper;
import com.urt.mapper.ext.LaoAlmRuleLogPoExtMapper;
import com.urt.mapper.ext.LaoAlmRulePoExtMapper;
import com.urt.mapper.ext.LaoAlmRuleTypePoExtMapper;
import com.urt.mapper.ext.LaoTrafficMmExtMapper;
import com.urt.mapper.ext.LaoValParamExtMapper;
import com.urt.miniService.MiniAlarmLogServiceImpl;
import com.urt.miniService.MiniAlarmServiceImpl;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoAlmElemValuePo;
import com.urt.po.LaoAlmRuleElemPo;
import com.urt.po.LaoAlmRuleLogPo;
import com.urt.po.LaoAlmRulePo;
import com.urt.po.LaoAlmRuleTypePo;
import com.urt.po.LaoCustomerPo;
import com.urt.po.LaoSmsTmpl;
import com.urt.po.LaoTrafficMm;
import com.urt.po.LaoValParam;
import com.urt.po.OperatorPlan;
import com.urt.service.authority.LaoSsAccountServiceImpl;
import com.urt.service.util.MailUtil;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

@Service("alarmService")
public class AlarmServiceImpl implements AlarmService {
	private static Logger logger = LoggerFactory
			.getLogger(LaoSsAccountServiceImpl.class);
	@Autowired
	private MiniAlarmServiceImpl miniAlarmService;
	@Autowired
	private MiniAlarmLogServiceImpl miniAlarmLogServiceImpl;

	@Autowired
	private LaoAlmRuleTypePoMapper ruleTypeDao;

	@Autowired
	private LaoAlmRuleTypePoExtMapper ruleTypeExtDao;

	@Autowired
	private LaoAlmRulePoExtMapper ruleExtDao;

	@Autowired
	private LaoAlmRuleElemPoExtMapper ruleElemExtDao;

	@Autowired
	private LaoAlmRulePoMapper ruleDao;

	@Autowired
	private LaoAlmElemValuePoMapper elemValueDao;

	@Autowired
	private LaoAlmElemValueExtPoMapper elemValueExtDao;

	@Autowired
	private LaoTrafficMmExtMapper trafficExtDao;

	@Autowired
	private IccidLibExtMapper iccidExtDao;

	@Autowired
	private SendMessageService sendMessageService;

	@Autowired
	private MailUtil mailUtil;

	@Autowired
	private LaoTrafficMmExtMapper trafficMmExtDao;

	@Autowired
	private LaoAlmRuleLogPoMapper ruleLogDao;

	@Autowired
	private LaoSmsTmplMapper laoSmsTmplMapperDao;

	@Autowired
	private LaoValParamExtMapper laoValParamExtMapperDao;

	@Autowired
	private LaoCustomerPoMapper custDao;

	@Autowired
	private OperatorPlanMapper operatorPlanDao;

	@Autowired
	private TradeService tradeService;

	@Autowired
	private UserService userService;
	@Autowired
	private LaoAlmRuleLogPoExtMapper ruleLogExtDao;

	//
	@Override
	public Map<String, Object> queryPage(LaoAlmRuleDto dto, Integer pageNo,
			Integer pageSize) {
		Map<String, Object> map = miniAlarmService.queryPage(dto, pageNo,
				pageSize);
		return map;
	}
	@Override
	public Map<String, Object> queryLogPage(LaoAlmRuleLogDto almRuleLogDto,
			int pageNo, int pageSize) {
		Map<String, Object> map = miniAlarmLogServiceImpl.queryPage(almRuleLogDto, pageNo,
				pageSize);
		return map;
	}
	@Override
	public List<Map<String, Object>> getLevel1() {
		return ruleTypeExtDao.getLevel1();
	}

	@Override
	public List<Map<String, Object>> getLevel2(Integer pid) {
		return ruleTypeExtDao.getLevel2(pid);
	}

	@Override
	public Integer delRule(Long ruleId) {
		int result = ruleExtDao.deleteByPrimaryKey(ruleId);
		/* int valueResult = elemValueExtDao.deletesByRuleId(ruleId); */
		if (result > 0) {
			return 1;
		}
		return -1;
	}

	@Override
	public List<Map<String, Object>> getElementsByRuleType(LaoAlmRuleElemDto dto) {
		LaoAlmRuleElemPo po = new LaoAlmRuleElemPo();
		BeanMapper.copy(dto, po);
		return ruleElemExtDao.getElementsByRuleType(po);
	}

	@Override
	public Integer saveRule(LaoAlmRuleDto dto) {
		LaoAlmRulePo po = new LaoAlmRulePo();
		BeanMapper.copy(dto, po);
		return ruleDao.insertSelective(po);
	}

	@Override
	public Integer saveRuleElemVaue(LaoAlmElemValueDto dto) {
		LaoAlmElemValuePo po = new LaoAlmElemValuePo();
		BeanMapper.copy(dto, po);
		return elemValueDao.insertSelective(po);
	}

	@Override
	public LaoAlmRuleDto getRuleById(Long ruleId) {
		// TODO Auto-generated method stub
		LaoAlmRuleDto dto = null;
		LaoAlmRulePo po = ruleDao.selectByPrimaryKey(ruleId);
		if (po != null) {
			dto = new LaoAlmRuleDto();
			BeanMapper.copy(po, dto);
			return dto;
		}
		return dto;
	}

	@Override
	public List<LaoAlmElemValueDto> getRuleElemVaue(Long ruleId) {
		// TODO Auto-generated method stub
		List<LaoAlmElemValuePo> elemValuesByRuleId = elemValueExtDao
				.getElemValuesByRuleId(ruleId);
		List<LaoAlmElemValueDto> dtos = new ArrayList<LaoAlmElemValueDto>();
		for (LaoAlmElemValuePo po : elemValuesByRuleId) {
			LaoAlmElemValueDto dto = new LaoAlmElemValueDto();
			BeanMapper.copy(po, dto);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public LaoAlmRuleTypeDto getParentAlmRuleType(Long ruleTypeId) {
		// TODO Auto-generated method stub
		LaoAlmRuleTypePo po = ruleTypeExtDao.getRuleTypeById(ruleTypeId);
		if (po != null) {
			LaoAlmRuleTypeDto dto = new LaoAlmRuleTypeDto();
			BeanMapper.copy(po, dto);
			return dto;
		}
		return null;
	}

	@Override
	public Integer updateRule(LaoAlmRuleDto dto) {
		// TODO Auto-generated method stub
		LaoAlmRulePo po = new LaoAlmRulePo();
		BeanMapper.copy(dto, po);
		return ruleDao.updateByPrimaryKeySelective(po);
	}

	@Override
	public Integer delAllElemValueByRuleId(Long ruleId) {
		// TODO Auto-generated method stub
		return elemValueExtDao.deletesByRuleId(ruleId);
	}

	@Override
	public String sendMsgByCust(LaoSsAccountDto currentUser) {
		// TODO Auto-generated method stub
		Long custId = currentUser.getCustId();
		List<LaoAlmRulePo> allRules = ruleExtDao.getRulesByCustId(custId);
		String batcheId = ZkGenerateSeq.getIdSeq(SeqID.ALMRULERELATION_ID);
		for (LaoAlmRulePo po : allRules) {
			logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&ruleId:"
					+ po.getRuleId());
			LaoAlmRuleLogPo logPo = new LaoAlmRuleLogPo();
			logPo.setRuleId(po.getRuleId());
			logPo.setBatch(batcheId);
			logPo.setCustId(custId);
//			logPo.setChannelCustId(po.getChannelCustId());
			logPo.setDealTime(new Date());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			logPo.setCycleId(new Long(sdf.format(new Date())));
			logPo.setOperId(currentUser.getAcconutId().toString());
			List<LaoAlmElemValuePo> elemValues = elemValueExtDao
					.getElemValuesByRuleId(po.getRuleId());
			LaoCustomerPo cust = custDao.selectByPrimaryKey(po.getCustId());
			MailDto mail = null;
			Map<String, Object> map = null;
			String emailReceiver = "";
			String smsReceiver = "";
			String sendType = "";
			String percent = "";
			String action = "";
			Boolean flag = false;
			logPo.setChannelCustId(po.getCustId());
			if (po.getRuleTypeId() == 5) {
				logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&进入流量池告警");
				// 流量池预警
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("custId", po.getCustId());
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>流量池1111111111111111111111");
				for (LaoAlmElemValuePo elementValue : elemValues) {
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>流量池222222222222222222222222222");
					if (elementValue.getElementId() == 4) {
						action = elementValue.getElemValue();
					} else if (elementValue.getElementId() == 2) {
						// 百分比
						percent = elementValue.getElemValue();
					} else if (elementValue.getElementId() == 6) {
						// 短信接收者
						smsReceiver = elementValue.getElemValue();
					} else if (elementValue.getElementId() == 9) {
						// 邮件接收者
						emailReceiver = elementValue.getElemValue();
					} else {
						// 未知处理
					}
				}
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>流量池3333333333333333333");
				float rate = new Float(percent)/100;
				logPo.setRate(rate+"");
				params.put("rate", rate);
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>percent:"+percent+";custId:"+po.getCustId());
				List<Map<String, Object>> flowCells = trafficMmExtDao.getFlowCellByCustIdPlanId(params);
				
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>flowCells.size"+flowCells.size());
				for (Map<String, Object> flowCell : flowCells) {
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>流量池44444444444444444444444444444444");
					String planId = "";
					if (flowCell.get("PLAN_ID") != null) {
						planId = flowCell.get("PLAN_ID").toString();
					}
					logPo.setRemark("套餐id=" + planId);
					logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&流量池百分比:"
							+ percent + ";短信接收者：" + smsReceiver + ";邮件接收者:"
							+ emailReceiver + "flag=" + flag);
					map = new HashMap<String, Object>();
					if (planId.equals("")) {
						map.put("planName", "数据问题");
					} else {
						OperatorPlan operatorPlan = operatorPlanDao
								.selectByPrimaryKey(Integer.valueOf(planId));
						map.put("planName", operatorPlan.getPlanName());
					}
					if (cust != null && cust.getCustName() != null
							&& cust.getCustName().trim().length() > 0) {
						map.put("custName", cust.getCustName());
					} else {
						map.put("custName", "gla");
					}
					map.put("rate", percent);
					map.put("templateId", "3");// 集群网的短信模板ID
					map.put("sendTime", new Date());
					String templetContent = this.getTempletContent(map);
					logger.info("邮件发送内容："+templetContent);
					if (action != null && action.trim().length() > 0) {
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>流量池55555555555555555555555");
						String[] actions = action.split(",");
						for (String str : actions) {
							Long alarmId = Long.valueOf(ZkGenerateSeq
									.getIdSeq(SeqID.SYSUSER_ID));
							logPo.setAlarmId(alarmId);
							logPo.setOperate(str);
							if ("1".equals(str)) {
								if (!"".equals(templetContent)) {
									logPo.setTempletcontent(templetContent);
									String[] emailReceivers = emailReceiver
											.split(",");
									for (int index = 0; index < emailReceivers.length; index++) {
										map.put("number", emailReceivers[index]);
										mail = new MailDto(
												emailReceivers[index], "流量池告警",
												templetContent);
										mailUtil.send(mail);
									}
									logger.info("&&&&&&&&&&&&&&&&&&&&&&流量池预警发送邮件");
									logPo.setDealTag("1");
								} else {
									logger.info("&&&&&&&&&&&&&&&&&&&&&&异常信息：流量池预警邮件内容为空");
									logPo.setDealTag("0");
								}
							} else if ("2".equals(str)) {

								boolean smsSend = false;
								map.put("number", smsReceiver);
								Map<String, Object> reuslt = sendMessageService.smsSendBack(map);
								logger.info("reuslt："+reuslt);
								smsSend=(boolean) reuslt.get("flag");
								logger.info("smsSend："+smsSend);
								String content = (String) reuslt.get("content");
								logger.info("content："+content);
								logPo.setTempletcontent(content);
								if (smsSend) {
									logPo.setDealTag("1");
									logger.info("&&&&&&&&&&&&&&&&&&&&&&流量池预警发送短信成功");
								} else {
									logPo.setDealTag("0");
									logger.info("&&&&&&&&&&&&&&&&&&&&&&流量池预警发送短信失败");
								}
							} else if ("3".equals(str)) {

								logger.info("&&&&&&&&&&&&&&&&&&&&&&流量池不支持停机！！！！！！！！！");
							} else {
								logPo.setDealTag("0");
							}
							ruleLogDao.insertSelective(logPo);
						}
					} else {
						logger.info("&&&&&&&&&&&&&&&&&&&&&&无发送动作");
					}

				}
			} else if (po.getRuleTypeId() == 6) {
				logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&进入单卡流量告警");
				// 单卡预警
				for (LaoAlmElemValuePo elementValue : elemValues) {
					if (elementValue.getElementId() == 4) {
						action = elementValue.getElemValue();
					} else if (elementValue.getElementId() == 2) {
						// 百分比
						percent = elementValue.getElemValue();

					} else if (elementValue.getElementId() == 6) {
						// 短信接收者
						smsReceiver = elementValue.getElemValue();

					} else if (elementValue.getElementId() == 9) {
						// 邮件接收者
						emailReceiver = elementValue.getElemValue();

					} else {
						// 未知处理
					}
				}
				logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&单卡告警   单卡百分比:"
						+ percent + ";短信接收者：" + smsReceiver + ";邮件接收者:"
						+ emailReceiver + "flag=" + flag);
				Map<String, Object> params = new HashMap<String, Object>();
				logger.info("百分比percent=:"+percent);
				float rate = new Float(percent)/100;
				logPo.setRate(rate+"");
				params.put("rate", rate);
				params.put("custId", po.getCustId());
				List<Map<String, Object>> laoTrafficMms = trafficExtDao
						.getLaoTrafficMmsByCustId(params);
				/*
				 * Map<Long, Integer> maps = new HashMap<Long, Integer>();
				 * List<Long> list = new ArrayList<Long>(); JSONObject
				 * jsonObject = new JSONObject();
				 */
				for (Map<String, Object> trafficMm : laoTrafficMms) {

					logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&单卡触发动作");
					String iccids = trafficMm.get("ICCIDS").toString();
					map = new HashMap<String, Object>();
					if (cust != null && cust.getCustName() != null
							&& cust.getCustName().trim().length() > 0) {
						map.put("custName", cust.getCustName());
					} else {
						map.put("custName", "gla");
					}
					map.put("rate", percent);
					int cardNum=trafficExtDao.getCardNum(custId,rate);
					logger.info("cardNum="+cardNum);
//					map.put("cardNum", trafficMm.get("COUNT_NUM"));
					map.put("cardNum", cardNum);
					map.put("templateId", "4");// 集群网的短信模板ID
					map.put("sendTime", new Date());
					if (action != null && action.trim().length() > 0) {
						String[] actions = action.split(",");
						for (String str : actions) {
							Long alarmId = Long.valueOf(ZkGenerateSeq
									.getIdSeq(SeqID.SYSUSER_ID));
							logPo.setAlarmId(alarmId);
							logPo.setOperate(str);
							if ("1".equals(str)) {
								String templetContent = this.getTempletContent(map);
								logger.info("单卡邮件发送内容："+templetContent);
								logPo.setTempletcontent(templetContent);
								if (!"".equals(templetContent)) {
									// map.put("number", emailReceiver);
									String[] emailReceivers = emailReceiver
											.split(",");
									for (int index = 0; index < emailReceivers.length; index++) {
										map.put("number", emailReceivers[index]);
										mail = new MailDto(
												emailReceivers[index],
												"单卡流量告警", templetContent);
										mailUtil.send(mail);
									}

									logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&单卡预警发送邮件");
									logPo.setDealTag("1");
								} else {
									logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&异常信息：单卡预警邮件内容为空");
									logPo.setDealTag("0");
								}
							} else if ("2".equals(str)) {
								/*String[] emailReceivers = emailReceiver
										.split(",");*/
								boolean smsSend = false;
								map.put("number", smsReceiver);
								Map<String, Object> reuslt = sendMessageService.smsSendBack(map);
								logger.info("短信发送结果："+reuslt);
								smsSend=(boolean) reuslt.get("flag");
								logger.info("短信是否发送成功："+reuslt);
								String content = (String) reuslt.get("content");
								logger.info("短信通知内容："+content);
								logPo.setTempletcontent(content);
								if (smsSend) {
									logPo.setDealTag("1");
									logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&单卡预警发送短信成功");
								} else {
									logPo.setDealTag("0");
									logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&单卡预警发送短信失败");
								}
							} else if ("3".equals(str)) {
								logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&单卡预警停机操作");
								// 停机操作
								String[] strs = iccids.split(",");
								for (String iccid : strs) {
									try {
										String tradeId = tradeService.addTrade(
												null, cust.getCustId()
														.toString(), iccid, "",
												"", "130", "0");
										String userArchiving = userService
												.userArchiving(tradeId);
										if ("ok".equals(userArchiving)) {
											logPo.setDealTag("1");
										} else {
											logger.info("&&&&&&&&&&&&&&&单卡预警停机操作失败");
											logPo.setDealTag("0");
										}
									} catch (Exception e) {
										logger.info("&&&&&&&&&&&&&&&&&&&&&单卡预警停机操作异常");
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}

							} else {
								logPo.setDealTag("0");
							}
							ruleLogDao.insertSelective(logPo);
						}
					} else {
						logger.info("无发送动作");
					}
				}

			} else {
				// 未处理的逻辑
			}

		}
		return batcheId;
	}
	@Override
	public String sendMsg(LaoSsAccountDto currentUser) {

		// TODO Auto-generated method stub
		List<LaoAlmRulePo> allRules = ruleExtDao.getAllRules();
		String batcheId = ZkGenerateSeq.getIdSeq(SeqID.ALMRULERELATION_ID);
		for (LaoAlmRulePo po : allRules) {
			logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&ruleId:"
					+ po.getRuleId());
			LaoAlmRuleLogPo logPo = new LaoAlmRuleLogPo();
			logPo.setRuleId(po.getRuleId());
			logPo.setBatch(batcheId);
			Long custId = po.getCustId();
			logPo.setCustId(custId);
//			logPo.setChannelCustId(po.getChannelCustId());
			logPo.setDealTime(new Date());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			logPo.setCycleId(new Long(sdf.format(new Date())));
			logPo.setOperId(currentUser.getAcconutId().toString());
			List<LaoAlmElemValuePo> elemValues = elemValueExtDao
					.getElemValuesByRuleId(po.getRuleId());
			LaoCustomerPo cust = custDao.selectByPrimaryKey(po.getCustId());
			MailDto mail = null;
			Map<String, Object> map = null;
			String emailReceiver = "";
			String smsReceiver = "";
			String sendType = "";
			String percent = "";
			String action = "";
			Boolean flag = false;
			logPo.setChannelCustId(po.getCustId());
			if (po.getRuleTypeId() == 5) {
				logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&进入流量池告警");
				// 流量池预警
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("custId", po.getCustId());
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>流量池1111111111111111111111");
				for (LaoAlmElemValuePo elementValue : elemValues) {
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>流量池222222222222222222222222222");
					if (elementValue.getElementId() == 4) {
						action = elementValue.getElemValue();
					} else if (elementValue.getElementId() == 2) {
						// 百分比
						percent = elementValue.getElemValue();
					} else if (elementValue.getElementId() == 6) {
						// 短信接收者
						smsReceiver = elementValue.getElemValue();
					} else if (elementValue.getElementId() == 9) {
						// 邮件接收者
						emailReceiver = elementValue.getElemValue();
					} else {
						// 未知处理
					}
				}
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>流量池3333333333333333333");
				float rate = new Float(percent)/100;
				logPo.setRate(rate+"");
				params.put("rate", rate);
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>percent:"+percent+";custId:"+po.getCustId());
				List<Map<String, Object>> flowCells = trafficMmExtDao.getFlowCellByCustIdPlanId(params);
				
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>flowCells.size"+flowCells.size());
				for (Map<String, Object> flowCell : flowCells) {
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>流量池44444444444444444444444444444444");
					String planId = "";
					if (flowCell.get("PLAN_ID") != null) {
						planId = flowCell.get("PLAN_ID").toString();
					}
					logPo.setRemark("套餐id=" + planId);
					logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&流量池百分比:"
							+ percent + ";短信接收者：" + smsReceiver + ";邮件接收者:"
							+ emailReceiver + "flag=" + flag);
					map = new HashMap<String, Object>();
					if (planId.equals("")) {
						map.put("planName", "数据问题");
					} else {
						OperatorPlan operatorPlan = operatorPlanDao
								.selectByPrimaryKey(Integer.valueOf(planId));
						map.put("planName", operatorPlan.getPlanName());
					}
					if (cust != null && cust.getCustName() != null
							&& cust.getCustName().trim().length() > 0) {
						map.put("custName", cust.getCustName());
					} else {
						map.put("custName", "gla");
					}
					map.put("rate", percent);
					map.put("templateId", "3");// 集群网的短信模板ID
					map.put("sendTime", new Date());
					String templetContent = this.getTempletContent(map);
					logger.info("邮件发送内容："+templetContent);
					if (action != null && action.trim().length() > 0) {
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>流量池55555555555555555555555");
						String[] actions = action.split(",");
						for (String str : actions) {
							Long alarmId = Long.valueOf(ZkGenerateSeq
									.getIdSeq(SeqID.SYSUSER_ID));
							logPo.setAlarmId(alarmId);
							logPo.setOperate(str);
							if ("1".equals(str)) {
								if (!"".equals(templetContent)) {
									logPo.setTempletcontent(templetContent);
									String[] emailReceivers = emailReceiver
											.split(",");
									for (int index = 0; index < emailReceivers.length; index++) {
										map.put("number", emailReceivers[index]);
										mail = new MailDto(
												emailReceivers[index], "流量池告警",
												templetContent);
										mailUtil.send(mail);
									}
									logger.info("&&&&&&&&&&&&&&&&&&&&&&流量池预警发送邮件");
									logPo.setDealTag("1");
								} else {
									logger.info("&&&&&&&&&&&&&&&&&&&&&&异常信息：流量池预警邮件内容为空");
									logPo.setDealTag("0");
								}
							} else if ("2".equals(str)) {

								boolean smsSend = false;
								map.put("number", smsReceiver);
								Map<String, Object> reuslt = sendMessageService.smsSendBack(map);
								logger.info("reuslt："+reuslt);
								smsSend=(boolean) reuslt.get("flag");
								logger.info("smsSend："+smsSend);
								String content = (String) reuslt.get("content");
								logger.info("content："+content);
								logPo.setTempletcontent(content);
								if (smsSend) {
									logPo.setDealTag("1");
									logger.info("&&&&&&&&&&&&&&&&&&&&&&流量池预警发送短信成功");
								} else {
									logPo.setDealTag("0");
									logger.info("&&&&&&&&&&&&&&&&&&&&&&流量池预警发送短信失败");
								}
							} else if ("3".equals(str)) {

								logger.info("&&&&&&&&&&&&&&&&&&&&&&流量池不支持停机！！！！！！！！！");
							} else {
								logPo.setDealTag("0");
							}
							ruleLogDao.insertSelective(logPo);
						}
					} else {
						logger.info("&&&&&&&&&&&&&&&&&&&&&&无发送动作");
					}

				}
			} else if (po.getRuleTypeId() == 6) {
				logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&进入单卡流量告警");
				// 单卡预警
				for (LaoAlmElemValuePo elementValue : elemValues) {
					if (elementValue.getElementId() == 4) {
						action = elementValue.getElemValue();
					} else if (elementValue.getElementId() == 2) {
						// 百分比
						percent = elementValue.getElemValue();

					} else if (elementValue.getElementId() == 6) {
						// 短信接收者
						smsReceiver = elementValue.getElemValue();

					} else if (elementValue.getElementId() == 9) {
						// 邮件接收者
						emailReceiver = elementValue.getElemValue();

					} else {
						// 未知处理
					}
				}
				logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&单卡告警   单卡百分比:"
						+ percent + ";短信接收者：" + smsReceiver + ";邮件接收者:"
						+ emailReceiver + "flag=" + flag);
				Map<String, Object> params = new HashMap<String, Object>();
				logger.info("百分比percent=:"+percent);
				float rate = new Float(percent)/100;
				logPo.setRate(rate+"");
				params.put("rate", rate);
				params.put("custId", po.getCustId());
				List<Map<String, Object>> laoTrafficMms = trafficExtDao
						.getLaoTrafficMmsByCustId(params);
				/*
				 * Map<Long, Integer> maps = new HashMap<Long, Integer>();
				 * List<Long> list = new ArrayList<Long>(); JSONObject
				 * jsonObject = new JSONObject();
				 */
				for (Map<String, Object> trafficMm : laoTrafficMms) {

					logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&单卡触发动作");
					String iccids = trafficMm.get("ICCIDS").toString();
					map = new HashMap<String, Object>();
					if (cust != null && cust.getCustName() != null
							&& cust.getCustName().trim().length() > 0) {
						map.put("custName", cust.getCustName());
					} else {
						map.put("custName", "gla");
					}
					map.put("rate", percent);
					int cardNum=trafficExtDao.getCardNum(custId,rate);
					logger.info("cardNum="+cardNum);
//					map.put("cardNum", trafficMm.get("COUNT_NUM"));
					map.put("cardNum", cardNum);
					map.put("templateId", "4");// 集群网的短信模板ID
					map.put("sendTime", new Date());
					if (action != null && action.trim().length() > 0) {
						String[] actions = action.split(",");
						for (String str : actions) {
							Long alarmId = Long.valueOf(ZkGenerateSeq
									.getIdSeq(SeqID.SYSUSER_ID));
							logPo.setAlarmId(alarmId);
							logPo.setOperate(str);
							if ("1".equals(str)) {
								String templetContent = this.getTempletContent(map);
								logger.info("单卡邮件发送内容："+templetContent);
								logPo.setTempletcontent(templetContent);
								if (!"".equals(templetContent)) {
									// map.put("number", emailReceiver);
									String[] emailReceivers = emailReceiver
											.split(",");
									for (int index = 0; index < emailReceivers.length; index++) {
										map.put("number", emailReceivers[index]);
										mail = new MailDto(
												emailReceivers[index],
												"单卡流量告警", templetContent);
										mailUtil.send(mail);
									}

									logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&单卡预警发送邮件");
									logPo.setDealTag("1");
								} else {
									logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&异常信息：单卡预警邮件内容为空");
									logPo.setDealTag("0");
								}
							} else if ("2".equals(str)) {
								/*String[] emailReceivers = emailReceiver
										.split(",");*/
								boolean smsSend = false;
								map.put("number", smsReceiver);
								Map<String, Object> reuslt = sendMessageService.smsSendBack(map);
								logger.info("短信发送结果："+reuslt);
								smsSend=(boolean) reuslt.get("flag");
								logger.info("短信是否发送成功："+reuslt);
								String content = (String) reuslt.get("content");
								logger.info("短信通知内容："+content);
								logPo.setTempletcontent(content);
								if (smsSend) {
									logPo.setDealTag("1");
									logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&单卡预警发送短信成功");
								} else {
									logPo.setDealTag("0");
									logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&单卡预警发送短信失败");
								}
							} else if ("3".equals(str)) {
								logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&单卡预警停机操作");
								// 停机操作
								String[] strs = iccids.split(",");
								for (String iccid : strs) {
									try {
										String tradeId = tradeService.addTrade(
												null, cust.getCustId()
														.toString(), iccid, "",
												"", "130", "0");
										String userArchiving = userService
												.userArchiving(tradeId);
										if ("ok".equals(userArchiving)) {
											logPo.setDealTag("1");
										} else {
											logger.info("&&&&&&&&&&&&&&&单卡预警停机操作失败");
											logPo.setDealTag("0");
										}
									} catch (Exception e) {
										logger.info("&&&&&&&&&&&&&&&&&&&&&单卡预警停机操作异常");
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}

							} else {
								logPo.setDealTag("0");
							}
							ruleLogDao.insertSelective(logPo);
						}
					} else {
						logger.info("无发送动作");
					}
				}

			} else {
				// 未处理的逻辑
			}

		}
		return batcheId;
	}

	public String getTempletContent(Map<String, Object> map) {

		int templateId = Integer.parseInt((String) map.get("templateId"));
		LaoSmsTmpl laoSmsTmpl = laoSmsTmplMapperDao
				.selectByPrimaryKey(templateId);
		if (laoSmsTmpl == null) {
			return "";
		}
		// 查询变量参数表
		List<LaoValParam> listValParam = laoValParamExtMapperDao
				.selectAllParamsByRuleId((long) templateId);
		Map<String, Object> mapContent = new HashMap<String, Object>();
		String content = laoSmsTmpl.getTmplContext();
		try {
			if (listValParam == null) {
				return "";
			} else {
				for (int i = 0; i < listValParam.size(); i++) {
					LaoValParam laoVal = listValParam.get(i);
					String paramValue = map.get(laoVal.getParaValue())
							.toString();
					content = content.replaceAll(laoVal.getParaName(),
							paramValue);
				}
			}
		} catch (Exception e) {
			logger.info(">>>>>>>>>>>>获取模板失败");
			content = "";
		}
		return content;
	}

	@Override
	public Integer updateRuleStatus(Long id, String status) {
		// TODO Auto-generated method stub
		LaoAlmRulePo po = ruleDao.selectByPrimaryKey(id);
		po.setValidTag(status);
		return ruleDao.updateByPrimaryKeySelective(po);
	}
	@Override
	public List<Map<String, Object>> queryCardInfo(String almId) {
		List<Map<String, Object>> cardInfos=null;
		cardInfos=ruleLogExtDao.queryCardInfo(almId);//单卡流量告警
		return cardInfos;
	}
}
