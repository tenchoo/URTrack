package com.urt.service.dmp.strategyimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

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

@Service("dmpRangeDistanceServieImpl")
@Transactional(propagation = Propagation.REQUIRED)
public class DMPRangeDistanceServieImpl implements DMPStrategyService {
	Logger log = Logger.getLogger(DMPRangeDistanceServieImpl.class);
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
		try {
			Long strategyId = strategy.getId();
			log.info("strategyId="+strategyId);
			String distanceStr = strategy.getParameter1();
			log.info("distanceStr="+distanceStr);
			//当前位置和参照点的最大允许距离
			int distance=Integer.parseInt(distanceStr);
			log.info("distance="+distance);
			log.info("referPosition_imei="+"referPosition_" + imei);
			String referPositon = dmpCacheUtil.getValue("referPosition_" + imei);
			log.info("referPositon="+referPositon);
			String nowPositon = dmpCacheUtil.getValue("nowPosition_" + imei);
			log.info("nowPositon="+nowPositon);
			Gson gson=new Gson();

			//保存日志
			LaoDMPLogPo dmpLogPo=new LaoDMPLogPo();
			dmpLogPo.setCustId(Long.parseLong(custId));
			dmpLogPo.setImei(imei);
			dmpLogPo.setGroupId(groupId);
			dmpLogPo.setTriggerCause("storm");
			dmpLogPo.setOperateType("storm");
			dmpLogPo.setOperateUser("storm");
			
			PositionData referPositionData = gson.fromJson(referPositon, PositionData.class);
			log.info("referPositionData="+referPositionData);
			PositionData nowPositionData = gson.fromJson(nowPositon, PositionData.class);
			log.info("nowPositionData="+nowPositionData);
			
			boolean b=false;
			if(referPositionData!=null&&nowPositionData!=null){
				b = strategesUtil.checkDistance(referPositionData,nowPositionData,distance);
			}
			log.info("b="+b);
			if(b){
				List<LaoDMPOperationPo> operationPos=dmpOperationPoExtMapper.getOperationsBystategy(schemeId, strategyId);
//				List<LaoDMPOperationPo> operationPos=null;
//				Map<String, String> operationMap = dmpCacheUtil.hgetAll("operationsofscheme_"+schemeId);
//				log.info("operationMap="+operationMap);
//				if(operationMap!=null&&operationMap.size()>0){
//					Set<Entry<String, String>> entrySet = operationMap.entrySet();
//					Iterator<Entry<String, String>> iterator = entrySet.iterator();
//					while(iterator.hasNext()){
//						Entry<String, String> entry = iterator.next();
//						String key = entry.getKey();
//						log.info("key="+key);
//						log.info("key.contains(String.valueOf(strategyId))="+key.contains(String.valueOf(strategyId)));
//						if(key.contains(String.valueOf(strategyId))){
//							operationPos=new ArrayList<LaoDMPOperationPo>();
//							String value = entry.getValue();
//							log.info("value="+value);
//							operationPos.add(gson.fromJson(value, LaoDMPOperationPo.class));
//						}
//					}
//				}else{
//					dmpLogPo.setOperationComment("没有操作");
//					log.info("没有操作");
//				}
				log.info("operationPos="+operationPos);
				if(operationPos!=null&&operationPos.size()>0){
					for(LaoDMPOperationPo po:operationPos){
						log.info("执行操作---"+po.getOperationName());
						//调用操作实现类
						DMPOperateService operateService=SpringContextUtils.getBean(po.getOperatorimpl());
						boolean o = operateService.OperateExecute(imei, custId);
						log.info("o="+o);
						if(o){
							dmpLogPo.setIsSuccess("1");//"1"成功   "0"失败
							log.info("对设备进行操作成功以后，删除设备在redis中的距离参考点");
						    dmpCacheUtil.del("referPosition_" + imei);
						}else{
							log.info("操作未成功");
							dmpLogPo.setIsSuccess("0");
						}
						String dmpLogId = ZkGenerateSeq.getIdSeq(SeqID.DMPLOG_ID);
						dmpLogPo.setId(Long.parseLong(dmpLogId));
						dmpLogPo.setOperationId(po.getId());
						dmpLogPo.setOperationComment(po.getOperationComment());
						strategesUtil.savaDMPLog(dmpLogPo);
					}
				}else{
					log.info("距离约束下没有操作");
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
				log.info("设备移动距离没有超过规定距离，没有违反规则dmpRangeDistanceServieImpl");
				String dmpLogId = ZkGenerateSeq.getIdSeq(SeqID.DMPLOG_ID);
				dmpLogPo.setId(Long.parseLong(dmpLogId));
				dmpLogPo.setIsAgainst("0");
				strategesUtil.savaDMPLog(dmpLogPo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
public static void main(String[] args) {
      Long strategyId = 4737482736743721L;
      
      System.out.println(strategyId);
}
	
	
}
