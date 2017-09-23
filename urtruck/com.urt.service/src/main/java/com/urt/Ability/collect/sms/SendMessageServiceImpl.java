package com.urt.Ability.collect.sms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.urt.dto.LaoSmsInfoDto;
import com.urt.interfaces.http.SendSmsServer;
import com.urt.interfaces.sendMessage.SendMessageService;
import com.urt.mapper.LaoSmsInfoMapper;
import com.urt.mapper.LaoSmsTmplMapper;
import com.urt.mapper.ext.LaoSmsInfoExtMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.mapper.ext.LaoValParamExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoSmsInfo;
import com.urt.po.LaoSmsTmpl;
import com.urt.po.LaoUser;
import com.urt.po.LaoValParam;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

@Service("sendMessageService")
@Transactional(propagation = Propagation.REQUIRED)
public class SendMessageServiceImpl implements SendMessageService{
	
	protected static final Logger logger = Logger.getLogger(SendSmsServerImpl.class);
	@Autowired
	private SendSmsServer sendSmsServer ;
	@Autowired
	private LaoSmsTmplMapper laoSmsTmplMapperDao ;
	@Autowired
	private LaoSmsInfoMapper laoSmsInfoMapperDao ;
	@Autowired
	private LaoSmsInfoExtMapper laoSmsInfoExtMapperDao ;
	@Autowired
	private LaoUserExtMapper laoUserExtMapperDao ;
	@Autowired
	private LaoValParamExtMapper laoValParamExtMapperDao ;
	@Override
	public Map<String, Object> smsSendBack(Map<String, Object> map) {
		Map<String,Object> resultBack=new HashMap<String, Object>();
		String content="";
		boolean flag = false;
		long smsId = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
		LaoSmsInfo laoInfo = new LaoSmsInfo();
		logger.info("---------------进入短信发送SendMessageService的smsSend方法----------------");
		int count = 0;
		try {
			// 参数校验
			this.docheck(map);
			// 获取参数
			String number = (String) map.get("number");
			String iccid = (String) map.get("iccid");
			int templateId = Integer.parseInt((String)map.get("templateId"));
			Date sendTime = (Date) map.get("sendTime");
			String operId = (String) map.get("operId");
			if (sendTime == null) {
				sendTime = new Date();
			}
			if (operId == null) {
				operId = "system";
			}
			laoInfo.setOperId(operId);// 操作人员
			laoInfo.setSendTime(sendTime);// 发送时间
			laoInfo.setMsisdn(number);// 服务号码
			laoInfo.setTempleteId(templateId);// 短信模板ID
			laoInfo.setIccid(iccid);// ICCID
			// 查询用户表
			LaoUser laoUser = laoUserExtMapperDao.selectByMsisdn(number);
			if (laoUser != null) {
				laoInfo.setUserId(laoUser.getUserId());// 用户ID
				laoInfo.setChannelCustId(laoUser.getChannelCustId());// 渠道客户ID
			}
			// 根据 短信模板ID 查询短信模板表
			LaoSmsTmpl laoSmsTmpl= laoSmsTmplMapperDao.selectByPrimaryKey(templateId);
			if (laoSmsTmpl == null) {
				resultBack.put("flag", flag);
				return resultBack;
			} else {
				laoInfo.setSmsCorp(laoSmsTmpl.getSmsCorp());// 短信处理方
			}
			// 查询变量参数表
			List<LaoValParam> listValParam = laoValParamExtMapperDao.selectAllParamsByRuleId((long)templateId);
			Map<String, Object> mapContent = new HashMap<String,Object>();
			content = laoSmsTmpl.getTmplContext();
			try {
				if (listValParam == null) {
					resultBack.put("flag", flag);
					return resultBack;
				} else {
					for (int i = 0; i < listValParam.size(); i++) {
						LaoValParam laoVal = listValParam.get(i);
						String paramValue = map.get(laoVal.getParaValue()).toString();
						logger.info("paramValue:"+paramValue);
						content = content.replaceAll(laoVal.getParaName(), paramValue);
					}
				}
			} catch (Exception e) {
				laoInfo.setRemark("替换模板参数异常");
				throw new Exception("替换模板参数异常，请检查变量参数表和所传map中的参数！");
			}
			laoInfo.setSmsContext(content);// 发送内容
			mapContent.put("number", number);
			mapContent.put("iccid", iccid);
			mapContent.put("content", content);
			mapContent.put("msgId", smsId);
			// 判断是否调统一短信发送接口 1统一发送；2单独发送
			if ("1".equals(laoSmsTmpl.getSmsType())) {
				mapContent.put("sendWay", laoSmsTmpl.getSmsCorp());
			} else if ("2".equals(laoSmsTmpl.getSmsType())) {
				int operatorsId = laoUser.getOperatorsId();
				mapContent.put("sendWay", operatorsId);
			}
			logger.info("---------------记录短信发送表----------------");
			laoInfo.setDealTag("1");// 处理状态 1-待处理，2-已处理，3-处理失败
			laoInfo.setResultInfo("即将调用短信发送接口");
			laoInfo.setRemark("0新增数据");
			laoInfo.setSmsId(smsId);// 短信发送流水
			laoInfo.setUpdateTime(new Date());// 更新时间
			count = laoSmsInfoMapperDao.insertSelective(laoInfo);
			logger.info("---------------调用短信发送接口sendSmsServer----------------");
			String reStr = sendSmsServer.sendSMS(mapContent);
			logger.info("---------------接口返回reStr:"+reStr+"----------------");
			
			// 回执调用
			if ("1000".equals(mapContent.get("sendWay"))) {
				// 统一发送,大汉三通
				String result = "";
				String outSmsid= "";
				result = JSONObject.fromObject(reStr).getString("result");
				if (StringUtils.isBlank(reStr) || !"0".equals(result)) {
					laoInfo.setDealTag("3");// 处理状态 1-待处理，2-已处理，3-处理失败
					laoInfo.setResultInfo("失败");// 0成功 1失败
					laoInfo.setRemark("失败");
					resultBack.put("flag", flag);
					resultBack.put("content", content);
					return resultBack;
				} else {
					outSmsid = JSONObject.fromObject(reStr).getString("msgid");
					laoInfo.setDealTag("1");// 处理状态 1-待处理，2-已处理，3-处理失败
					laoInfo.setResultInfo("待处理");
				}
				//第一次更新：短信发送已经成功请求到大汉三通
				laoInfo.setOutSmsid(outSmsid);
				laoInfo.setRemark("1第一次更新数据");
				laoSmsInfoMapperDao.updateByPrimaryKeySelective(laoInfo);
				//开始回调
				if ("0".equals(result)) {
					logger.info("---------------开始回执调用(大汉三通)----------------");
					String retString = sendSmsServer.getReport("1000", iccid, number);
					if (!StringUtils.isBlank(retString)) {
						Gson gson = new Gson();
						RespOutBean outBean = gson.fromJson(retString,
								new TypeToken<RespOutBean>() {}.getType());
						if ("0".equals(outBean.getResult())) {
							if (outBean.getReports() != null && outBean.getReports().size()>0) {
								for (int j = 0; j < outBean.getReports().size(); j++) {
									Report report= outBean.getReports().get(j);
									laoInfo.setOutSmsid(report.getMsgid());
									laoInfo.setDealTag("2");// 处理状态 1-待处理，2-已处理，3-处理失败
									laoInfo.setResultInfo("0");// 0成功 1失败
									//第二次更新：短信已经成功发送完
									laoInfo.setRemark("2第二次更新数据完成");
									laoSmsInfoExtMapperDao.updateByOutsmsidSelective(laoInfo);
									flag = true;
								}
							}
						}
					}
				}
			} else if("1".equals(mapContent.get("sendWay"))){
				// 单独发送 联通
			}
			resultBack.put("flag", flag);
			resultBack.put("content", content);
			return resultBack;
		} catch (Exception e) {
			e.printStackTrace();
			laoInfo.setDealTag("3");// 处理状态 1-待处理，2-已处理，3-处理失败
			laoInfo.setResultInfo("1失败");// 0成功 1失败
			resultBack.put("flag", flag);
			resultBack.put("content", content);
			return resultBack;
		} finally {
			laoInfo.setSmsId(smsId);// 短信发送流水
			laoInfo.setUpdateTime(new Date());// 更新时间
			if (count == 1) {
				laoSmsInfoMapperDao.updateByPrimaryKeySelective(laoInfo);
			}
		}
	}
	@Override
	public boolean smsSend(Map<String, Object> map) {
		boolean flag = false;
		long smsId = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
		LaoSmsInfo laoInfo = new LaoSmsInfo();
		logger.info("---------------进入短信发送SendMessageService的smsSend方法----------------");
		int count = 0;
		try {
			// 参数校验
			this.docheck(map);
			// 获取参数
			String number = (String) map.get("number");
			String iccid = (String) map.get("iccid");
			int templateId = Integer.parseInt((String)map.get("templateId"));
			Date sendTime = (Date) map.get("sendTime");
			String operId = (String) map.get("operId");
			if (sendTime == null) {
				sendTime = new Date();
			}
			if (operId == null) {
				operId = "system";
			}
			laoInfo.setOperId(operId);// 操作人员
			laoInfo.setSendTime(sendTime);// 发送时间
			laoInfo.setMsisdn(number);// 服务号码
			laoInfo.setTempleteId(templateId);// 短信模板ID
			laoInfo.setIccid(iccid);// ICCID
			// 查询用户表
			LaoUser laoUser = laoUserExtMapperDao.selectByMsisdn(number);
			if (laoUser != null) {
				laoInfo.setUserId(laoUser.getUserId());// 用户ID
				laoInfo.setChannelCustId(laoUser.getChannelCustId());// 渠道客户ID
			}
			// 根据 短信模板ID 查询短信模板表
			LaoSmsTmpl laoSmsTmpl= laoSmsTmplMapperDao.selectByPrimaryKey(templateId);
			if (laoSmsTmpl == null) {
				return flag;
			} else {
				laoInfo.setSmsCorp(laoSmsTmpl.getSmsCorp());// 短信处理方
			}
			// 查询变量参数表
			List<LaoValParam> listValParam = laoValParamExtMapperDao.selectAllParamsByRuleId((long)templateId);
			Map<String, Object> mapContent = new HashMap<String,Object>();
			String content = laoSmsTmpl.getTmplContext();
			try {
				if (listValParam == null) {
					return flag;
				} else {
					for (int i = 0; i < listValParam.size(); i++) {
						LaoValParam laoVal = listValParam.get(i);
						String paramValue = map.get(laoVal.getParaValue()).toString();
						content = content.replaceAll(laoVal.getParaName(), paramValue);
					}
				}
			} catch (Exception e) {
				laoInfo.setRemark("替换模板参数异常");
				throw new Exception("替换模板参数异常，请检查变量参数表和所传map中的参数！");
			}
			laoInfo.setSmsContext(content);// 发送内容
			mapContent.put("number", number);
			mapContent.put("iccid", iccid);
			mapContent.put("content", content);
			mapContent.put("msgId", smsId);
			// 判断是否调统一短信发送接口 1统一发送；2单独发送
			if ("1".equals(laoSmsTmpl.getSmsType())) {
				mapContent.put("sendWay", laoSmsTmpl.getSmsCorp());
			} else if ("2".equals(laoSmsTmpl.getSmsType())) {
				int operatorsId = laoUser.getOperatorsId();
				mapContent.put("sendWay", operatorsId);
			}
			logger.info("---------------记录短信发送表----------------");
			laoInfo.setDealTag("1");// 处理状态 1-待处理，2-已处理，3-处理失败
			laoInfo.setResultInfo("即将调用短信发送接口");
			laoInfo.setRemark("0新增数据");
			laoInfo.setSmsId(smsId);// 短信发送流水
			laoInfo.setUpdateTime(new Date());// 更新时间
			count = laoSmsInfoMapperDao.insertSelective(laoInfo);
			logger.info("---------------调用短信发送接口sendSmsServer----------------");
			String reStr = sendSmsServer.sendSMS(mapContent);
			logger.info("---------------接口返回reStr:"+reStr+"----------------");
			
			// 回执调用
			if ("1000".equals(mapContent.get("sendWay"))) {
				// 统一发送,大汉三通
				String result = "";
				String outSmsid= "";
				result = JSONObject.fromObject(reStr).getString("result");
				if (StringUtils.isBlank(reStr) || !"0".equals(result)) {
					laoInfo.setDealTag("3");// 处理状态 1-待处理，2-已处理，3-处理失败
					laoInfo.setResultInfo("失败");// 0成功 1失败
					laoInfo.setRemark("失败");
					return flag;
				} else {
					outSmsid = JSONObject.fromObject(reStr).getString("msgid");
					laoInfo.setDealTag("1");// 处理状态 1-待处理，2-已处理，3-处理失败
					laoInfo.setResultInfo("待处理");
				}
				//第一次更新：短信发送已经成功请求到大汉三通
				laoInfo.setOutSmsid(outSmsid);
				laoInfo.setRemark("1第一次更新数据");
				laoSmsInfoMapperDao.updateByPrimaryKeySelective(laoInfo);
				//开始回调
				if ("0".equals(result)) {
					logger.info("---------------开始回执调用(大汉三通)----------------");
					String retString = sendSmsServer.getReport("1000", iccid, number);
					if (!StringUtils.isBlank(retString)) {
						Gson gson = new Gson();
						RespOutBean outBean = gson.fromJson(retString,
								new TypeToken<RespOutBean>() {}.getType());
						if ("0".equals(outBean.getResult())) {
							if (outBean.getReports() != null && outBean.getReports().size()>0) {
								for (int j = 0; j < outBean.getReports().size(); j++) {
									Report report= outBean.getReports().get(j);
									laoInfo.setOutSmsid(report.getMsgid());
									laoInfo.setDealTag("2");// 处理状态 1-待处理，2-已处理，3-处理失败
									laoInfo.setResultInfo("0");// 0成功 1失败
									//第二次更新：短信已经成功发送完
									laoInfo.setRemark("2第二次更新数据完成");
									laoSmsInfoExtMapperDao.updateByOutsmsidSelective(laoInfo);
									flag = true;
								}
							}
						}
					}
				}
			} else if("1".equals(mapContent.get("sendWay"))){
				// 单独发送 联通
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			laoInfo.setDealTag("3");// 处理状态 1-待处理，2-已处理，3-处理失败
			laoInfo.setResultInfo("1失败");// 0成功 1失败
			return flag;
		} finally {
			laoInfo.setSmsId(smsId);// 短信发送流水
			laoInfo.setUpdateTime(new Date());// 更新时间
			if (count == 1) {
				laoSmsInfoMapperDao.updateByPrimaryKeySelective(laoInfo);
			}
		}
	}

	private void docheck(Map<String, Object> map) throws Exception{
		if (map == null || map.isEmpty()) {
			throw new Exception("入参不能为空！");
		} else {
			if (map.get("templateId") == null) {
				throw new Exception("templateId不能为空！");
			}
			if (map.get("number") == null && map.get("iccid") == null) {
				throw new Exception("number和iccid不能全为空！");
			}
		}
		
	}

	protected class RespOutBean {
		private String result; // 响应码
		private String desc;// 成功失败说明
		private List<Report> reports;// 响应报告明细
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public List<Report> getReports() {
			return reports;
		}
		public void setReports(List<Report> reports) {
			this.reports = reports;
		}
		
	}
	
	protected class Report {
		private String msgid; // 短信流水号
		private String phone;// 手机号
		private String status;// 短信发送结果：0——成功；1——接口处理失败；2——运营商网关失败；
		private String desc;// 当status为1时，以desc的错误码为准
		private String wgcode;// 当status为2时，表示运营商网关返回的原始值；
		private String time;// 时间yyyy-MM-ddHH:mm:ss。
		private String smsCount;// 长短信条数。
		private String smsIndex;// 长短信第几条标示。
		public String getMsgid() {
			return msgid;
		}
		public void setMsgid(String msgid) {
			this.msgid = msgid;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public String getWgcode() {
			return wgcode;
		}
		public void setWgcode(String wgcode) {
			this.wgcode = wgcode;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getSmsCount() {
			return smsCount;
		}
		public void setSmsCount(String smsCount) {
			this.smsCount = smsCount;
		}
		public String getSmsIndex() {
			return smsIndex;
		}
		public void setSmsIndex(String smsIndex) {
			this.smsIndex = smsIndex;
		}
		
	}

	@Override
	public List<LaoSmsInfoDto> queryInfoByMsisdn(String msisdn) {
		List<LaoSmsInfo> queryInfoList = laoSmsInfoExtMapperDao.queryInfoByMsisdn(msisdn);
		List<LaoSmsInfoDto> dtoList = null;
		if(queryInfoList != null){
			dtoList = BeanMapper.mapList(queryInfoList, LaoSmsInfoDto.class);
		}
		return dtoList;
	}

}
