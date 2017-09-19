package com.urt.service.dmp.strategyimpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.gson.Gson;
import com.urt.cache.DMPCacheUtil;
import com.urt.dto.MailDto;
import com.urt.dto.dmp.LaoDMPStrategyDto;
import com.urt.interfaces.dmp.DMPOperateService;
import com.urt.interfaces.dmp.DMPStrategyService;
import com.urt.mapper.LaoDMPLogPoMapper;
import com.urt.mapper.ext.LaoDMPOperationPoExtMapper;
import com.urt.mapper.ext.LaoDMPStrategyOperationPoExtMapper;
import com.urt.mapper.ext.LaoDMPStrategyRelationPoExtMapper;
import com.urt.po.LaoDMPLogPo;
import com.urt.po.LaoDMPOperationPo;
import com.urt.service.dmp.DMPServiceImpl;
import com.urt.service.dmp.entity.CardData;
import com.urt.service.dmp.entity.PositionData;
import com.urt.service.dmp.util.DMPStrategesUtil;
import com.urt.service.util.MailUtil;
import com.urt.utils.SeqID;
import com.urt.utils.SpringContextUtils;
import com.urt.utils.ZkGenerateSeq;

@Service("dmpRangeAreaServieImpl")
@Transactional(propagation = Propagation.REQUIRED)
public class DMPRangeAreaServieImpl implements DMPStrategyService {
	Logger log = Logger.getLogger(DMPRangeAreaServieImpl.class);
	@Autowired
	private DMPCacheUtil dmpCacheUtil;
	@Autowired
	private DMPStrategesUtil strategesUtil;
	@Autowired
	private LaoDMPStrategyOperationPoExtMapper dmpStrategyOperationPoExtMapper;
	@Autowired
	private LaoDMPOperationPoExtMapper dmpOperationPoExtMapper;
	@Autowired
	private MailUtil mailUtil;
	
	
	@Override
	public void StrategyExecute(LaoDMPStrategyDto strategy, String imei,String custId,Long schemeId,Long groupId) {
		log.info("进入区域范围限定的处理结构，判断设备是否出了指定的行政区域，比如 湖北");
		try {
			Long strategyId = strategy.getId();
			log.info("strategyId="+strategyId);
			String area = strategy.getParameter1();
			log.info("imei为+"+imei+"的设备被划定的活动区域area为： "+area);
			String nowPositon = dmpCacheUtil.getValue("nowPosition_" + imei);
			log.info("根据IMEI从redis中获取的设备当前位置字符串nowPositon="+nowPositon);
			if(StringUtils.isNotEmpty(nowPositon)){
				log.info("根据IMEI从redis中获取的设备当前位置字符串nowPositon不为空");
				Gson gson=new Gson();
				
				//保存日志
				LaoDMPLogPo dmpLogPo=new LaoDMPLogPo();
				dmpLogPo.setCustId(Long.parseLong(custId));
				dmpLogPo.setImei(imei);
				dmpLogPo.setGroupId(groupId);
				dmpLogPo.setTriggerCause("storm");
				dmpLogPo.setOperateType("storm");
				dmpLogPo.setOperateUser("storm");
				
				PositionData nowPositionData = gson.fromJson(nowPositon, PositionData.class);
				String nowArea = nowPositionData.getAddress();
				log.info("nowPositionData="+nowPositionData);
				boolean b = strategesUtil.positionUtil(nowArea,area);
				log.info("b="+b);
				if(b){
					dmpLogPo.setIsAgainst("1");//"1"违规，"0"没有违规
					log.info("设备不在被划定的区域内，违反了规则，需要执行相应操作，下面取出该策略下的操作，并执行");
					List<LaoDMPOperationPo> operationPos=dmpOperationPoExtMapper.getOperationsBystategy(schemeId, strategyId);
//					List<LaoDMPOperationPo> operationPos=null;
//					Map<String, String> operationMap = dmpCacheUtil.hgetAll("operationsofscheme_"+schemeId);
//					log.info("根据应用策略id获取redis中操作的map集合operationMap="+operationMap);
//					if(operationMap!=null&&operationMap.size()>0){
//						Set<Entry<String, String>> entrySet = operationMap.entrySet();
//						Iterator<Entry<String, String>> iterator = entrySet.iterator();
//						while(iterator.hasNext()){
//							Entry<String, String> entry = iterator.next();
//							String key = entry.getKey();
//							log.info("key="+key);
//							log.info("key.contains(String.valueOf(strategyId))="+key.contains(String.valueOf(strategyId)));
//							if(key.contains(String.valueOf(strategyId))){
//								operationPos=new ArrayList<LaoDMPOperationPo>();
//								String value = entry.getValue();
//								log.info("value="+value);
//								operationPos.add(gson.fromJson(value, LaoDMPOperationPo.class));
//							}
//						}
//					}else{
//						dmpLogPo.setOperationComment("没有操作");
//						log.info("没有操作");
//					}
					log.info("operationPos="+operationPos);
					if(operationPos!=null&&operationPos.size()>0){
						log.info("根据策略id获取的操作operationPos不为空，下面去执行操作");
						for(LaoDMPOperationPo po:operationPos){
							log.info("操作名----："+po.getOperationName());
							log.info("操作实现类名----："+po.getOperatorimpl());
							//调用操作实现类
							DMPOperateService operateService=SpringContextUtils.getBean(po.getOperatorimpl());
							boolean o = operateService.OperateExecute(imei, custId);
							if(o){
								dmpLogPo.setIsSuccess("1");//"1"成功   "0"失败
							}else{
								dmpLogPo.setIsSuccess("0");
							}
							String dmpLogId = ZkGenerateSeq.getIdSeq(SeqID.DMPLOG_ID);
							dmpLogPo.setId(Long.parseLong(dmpLogId));
							dmpLogPo.setOperationId(po.getId());
							dmpLogPo.setOperationComment(po.getOperationComment());
							strategesUtil.savaDMPLog(dmpLogPo);
						}
					}else{
						log.info("范围约束下没有操作");
						String dmpLogId = ZkGenerateSeq.getIdSeq(SeqID.DMPLOG_ID);
						dmpLogPo.setId(Long.parseLong(dmpLogId));
						dmpLogPo.setOperationComment("没有操作");
						strategesUtil.savaDMPLog(dmpLogPo);
					}
					String iccid="";
					String cartDataStr = dmpCacheUtil.getValue("cardStr_" + imei);
					log.info("从redis中获取的卡信息cartDataStr="+cartDataStr);
					if(!StringUtils.isBlank(cartDataStr)){
						CardData cardData=gson.fromJson(cartDataStr, CardData.class);
						iccid=cardData.getIccid();
						log.info("iccid="+iccid);
					}
					MailDto mail=new MailDto("zhouyd3@lenovo.com","设备违规预警","<h1>预警内容！</h1></br>&nbsp&nbsp&nbsp&nbsp 设备IMEI号："+imei+"，ICCID号："+iccid+"，触发原因："+strategy.getStrategyName()+"，执行操作：关闭wifi和停卡");
					boolean sendResult = mailUtil.send(mail);
					if(sendResult)
						log.info("邮件发送成功");
					else
						log.info("邮件发送失败");
				}else{
					log.info("设备在被划定的区域内，没有违反规则dmpRangeAreaServieImpl");
					String dmpLogId = ZkGenerateSeq.getIdSeq(SeqID.DMPLOG_ID);
					dmpLogPo.setId(Long.parseLong(dmpLogId));
					dmpLogPo.setIsAgainst("0");
					strategesUtil.savaDMPLog(dmpLogPo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
}
