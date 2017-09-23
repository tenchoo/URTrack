package com.urt.Ability.DongguanCMC;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.urt.Ability.collect.BatchTrafficQuery;
import com.urt.dto.traffic.DailydatausageBean;
import com.urt.dto.traffic.LaoSimDateDetailDto;
import com.urt.dto.traffic.OlMberFlows;
import com.urt.mapper.LaoOperatordealLogMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.mapper.ext.LaoUserOperatorPlanExtMapper;
import com.urt.po.LaoOperatordealLog;
import com.urt.utils.HttpClientUtil;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

/**
 * 移动流量查询
 * 
 * @author lingfei
 * @date 2017年02月09日
 */
@Service("batchTrafficQueryCMCC")
public class BatchTrafficQueryCMCC extends BatchTrafficQuery<Object> {

	Logger log = Logger.getLogger(getClass());

	@Autowired
	private LaoUserExtMapper laoUserDao;
	@Autowired
	private LaoOperatordealLogMapper laoOperatordealLogDAO;
	@Autowired
	private LaoUserOperatorPlanExtMapper laoUserOperatorPlanExtDao;
	
	@Override
	protected void queryDayUseCount(LaoSimDateDetailDto laoSimDateDetailDto) {
		String iccid = "0";
		String strDate = "yyyy-MM-dd";
		String dateStr = "yyyyMM";
		String msisdn = "";
		if (laoSimDateDetailDto != null) {
			iccid = laoSimDateDetailDto.getIccid();
			msisdn = laoSimDateDetailDto.getMsisdn();
			strDate = laoSimDateDetailDto.getData();
			dateStr = strDate.replace("-", "").substring(0, 6);
		}
		LaoOperatordealLog logger = new LaoOperatordealLog();
		logger.setCreateDate(new Date());
		logger.setIccid(iccid);
		logger.setSuccess("1");
		logger.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
		logger.setOperatorType("5");// 1订购2发短信3状态变更4查询卡状态5流量查询
		logger.setOperatorId(laoSimDateDetailDto.getOperatorsId()+"");
		try {
			String method = "iot.member.dailydatausage.query";
			Map<String, String> paramMap = EncryptUtils.genComMap();
			paramMap.put(ConstantsUntil.method, method);
			paramMap.put(ConstantsUntil.msisdn, msisdn);
			paramMap.put(ConstantsUntil.dateStr, dateStr);
			String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
			String httpUrl = ConstantsUntil.URL + "?"
					+ EncryptUtils.genComUtr() + "&" + ConstantsUntil.msisdn
					+ "=" + msisdn + "&" + ConstantsUntil.dateStr + "="
					+ dateStr + "&" + ConstantsUntil.sign + "=" + secret + "&"
					+ ConstantsUntil.method + "=" + method;
			logger.setInputParameters(httpUrl);
			String reuslt = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			if (!StringUtils.isBlank(reuslt)) {
				// 记录参数
				if (reuslt.length() > 2000) {
					logger.setOutputParameters(reuslt.substring(0, 2000));
				} else {
					logger.setOutputParameters(reuslt);
				}
				JSONObject json = JSON.parseObject(reuslt);
				if (json.containsKey("resultCode")) {
					if (json.getString("resultCode").equals("1")) {
						logger.setResultCode("failed");
						logger.setResultInfo("1");// 0成功1失败
						logger.setSuccess("1"); // 0成功1失败
					} else if (json.getString("resultCode").equals("0")) {
						logger.setResultCode("success");
						logger.setResultInfo("0");// 0成功1失败
						logger.setSuccess("0"); // 0成功1失败
						Gson gson = new Gson();
						DailydatausageBean dailyBean = gson.fromJson(reuslt,
								new TypeToken<DailydatausageBean>() {}.getType());
						List<OlMberFlows> listOl = dailyBean.getOlMberDdFlows();
						if (listOl != null && listOl.size() > 0) {
							for (int i = 0; i < listOl.size(); i++) {
								OlMberFlows olmber = listOl.get(i);
								if (strDate.equals(olmber.getDateStr().substring(0, 10))) {
									String flowSum = listOl.get(i).getFlowSum();
									laoSimDateDetailDto.setDayUseCount(flowSum);
								}
							}
						}
					}
				}else{
					logger.setResultCode("failed");
					logger.setResultInfo("1");// 0成功1失败
					logger.setSuccess("1"); // 0成功1失败
				}
			} else {
				logger.setOutputParameters(reuslt);
				logger.setResultCode("failed");
				logger.setResultInfo("1");// 0成功1失败
				logger.setSuccess("1"); // 0成功1失败
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.setResultInfo("调用接口异常");// 0成功1失败
			logger.setSuccess("1"); // 0成功1失败
			return ;
		} finally {
			try {
				// 记录传入传出参数
				laoOperatordealLogDAO.insertSelective(logger);
			} catch (Exception e) {
				log.info("记录传入传出参数异常");
				e.printStackTrace();
			}
		}
		
	}
	@Override
	protected void queryMonthUseCount(LaoSimDateDetailDto laoSimDateDetailDto) {
		String iccid = "0";
		String startDateStr = "yyyyMM";
		String endDateStr = "yyyyMM";
		String msisdn = "";
		if (laoSimDateDetailDto != null) {
			iccid = laoSimDateDetailDto.getIccid();
			msisdn = laoSimDateDetailDto.getMsisdn();
			startDateStr = laoSimDateDetailDto.getData().replace("-", "").substring(0, 6);
			endDateStr = startDateStr;
		}
		LaoOperatordealLog logger = new LaoOperatordealLog();
		logger.setCreateDate(new Date());
		logger.setIccid(iccid);
		logger.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
		logger.setOperatorType("5");// 1订购2发短信3状态变更4查询卡状态5流量查询
		logger.setOperatorId(laoSimDateDetailDto.getOperatorsId()+"");
		try {
			String method = "iot.member.monthlydatausage.query";
			Map<String, String> paramMap = EncryptUtils.genComMap();
			paramMap.put(ConstantsUntil.method, method);
			paramMap.put(ConstantsUntil.msisdn, msisdn);
			paramMap.put(ConstantsUntil.startDateStr, startDateStr);
			paramMap.put(ConstantsUntil.endDateStr, endDateStr);
			String secret = EncryptUtils.sign(paramMap, ConstantsUntil.secret);
			String httpUrl = ConstantsUntil.URL + "?" + EncryptUtils.genComUtr()
					+ "&" + ConstantsUntil.msisdn + "=" + msisdn + "&"
					+ ConstantsUntil.startDateStr + "=" + startDateStr + "&"
					+ ConstantsUntil.endDateStr + "=" + endDateStr + "&"
					+ ConstantsUntil.sign + "=" + secret + "&"
					+ ConstantsUntil.method + "=" + method;
			String reuslt = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			if (!StringUtils.isBlank(reuslt)) {
				// 记录参数
				if (reuslt.length() > 2000) {
					logger.setOutputParameters(reuslt.substring(0, 2000));
				} else {
					logger.setOutputParameters(reuslt);
				}
				JSONObject json = JSON.parseObject(reuslt);
				if (json.containsKey("resultCode")) {
					if (json.getString("resultCode").equals("1")) {
						logger.setResultCode("failed");
						logger.setResultInfo("1");// 0成功1失败
						logger.setSuccess("1"); // 0成功1失败
					} else if (json.getString("resultCode").equals("0")) {
						logger.setResultCode("success");
						logger.setResultInfo("0");// 0成功1失败
						logger.setSuccess("0"); // 0成功1失败
						Gson gson = new Gson();
						DailydatausageBean dailyBean = gson.fromJson(reuslt,
								new TypeToken<DailydatausageBean>() {}.getType());
						List<OlMberFlows> listOl = dailyBean.getOlMberDdFlows();
						if (listOl != null && listOl.size() > 0) {
							for (int i = 0; i < listOl.size(); i++) {
								OlMberFlows olmber = listOl.get(i);
								String mmFlowSum = olmber.getMmflowNum();
								laoSimDateDetailDto.setMonthUseCount(mmFlowSum);
							}
						}
					}
				}else{
					logger.setResultCode("failed");
					logger.setResultInfo("1");// 0成功1失败
					logger.setSuccess("1"); // 0成功1失败
				}
			} else {
				logger.setOutputParameters(reuslt);
				logger.setResultCode("failed");
				logger.setResultInfo("1");// 0成功1失败
				logger.setSuccess("1"); // 0成功1失败
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.setResultInfo("调用接口异常");// 0成功1失败
			logger.setSuccess("1"); // 0成功1失败
			return ;
		} finally {
			try {
				// 记录传入传出参数
				laoOperatordealLogDAO.insertSelective(logger);
			} catch (Exception e) {
				log.info("记录传入传出参数异常");
				e.printStackTrace();
			}
		}
		
	}
	@Override
	protected void queryMonthDataRemaining(
			LaoSimDateDetailDto laoSimDateDetailDto) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void queryCardMsg(LaoSimDateDetailDto laoSimDateDetailDto) {
		// TODO Auto-generated method stub
		
	}
	
}
