package com.urt.service.task;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.Ability.collect.sms.SendSmsServerImpl;
import com.urt.dto.task.LaoTrafficMMTaskDto;
import com.urt.interfaces.task.TrafficTaskService;
import com.urt.mapper.LaoTrafficMMTaskPoMapper;
import com.urt.mapper.ext.LaoTrafficMMTaskPoExtMapper;
import com.urt.mapper.ext.LaoTrafficMmExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoTrafficMMTaskPo;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

@Service("trafficTaskService")
@Transactional(propagation = Propagation.REQUIRED)
public class TrafficTaskServiceImpl implements TrafficTaskService {
	
	protected static final Logger logger = Logger.getLogger(SendSmsServerImpl.class);
	@Autowired
	LaoTrafficMMTaskPoExtMapper taskDao;
	@Autowired
	LaoTrafficMMTaskPoMapper taskMapper;
	@Autowired
	private LaoTrafficMmExtMapper laoTrafficMmDao;
	@Override
	public int getTaskCount(long channelCustId, String type, String month,int parseInt,
			int parseInt2) {
		int count=taskDao.getTaskCount(channelCustId,type,month,parseInt,parseInt2);
		return count;
	}
	@Override
	public Long getTaskId(long channelCustId, String type,String month, int parseInt,
			int parseInt2,String value1,String value2,String operatorId,String goodsId) {
		Long id=taskDao.getTaskId(channelCustId,type,month,parseInt,parseInt2,value1,value2,operatorId,goodsId);
		return id;
	}
	@Override
	public Long saveTaskInfo(Long userId, long channelCustId, String type,String month,
			int parseInt, int parseInt2,String value1,String value2,String operatorId,String goodsId) {
		logger.info("生成task_id前《《《《《《《《《《《《《《《《《");
		Long id = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.TASK_ID));
		logger.info("生成task_id后》》》》》》》》》》》》》》》》》》》"+id);
		LaoTrafficMMTaskPo task=new LaoTrafficMMTaskPo();
		task.setId(id);
		task.setOperId(userId);
		task.setChannelCustId(channelCustId);
		task.setType(type);
		task.setData1(new BigDecimal(parseInt));
		task.setData2(new BigDecimal(parseInt2));
		task.setTaskState("0");//0：任务未开始，1任务未完成，2任务已完成
		task.setValue1(value1);
		task.setValue2(value2);
		task.setMonth(month);
		if(operatorId!=null&&operatorId.trim()!=""){
			task.setOperatorid(Integer.parseInt(operatorId));
		}
		if(goodsId!=null&&goodsId.trim().length()>0){
			task.setGoodsid(Long.parseLong(goodsId));
		}
		Map<String, Object> countMap;
		if("1".equals(type)){
			countMap=laoTrafficMmDao.selectUseData(channelCustId,parseInt,month, "-2".equals(parseInt2+"")?null:parseInt2,"-1".equals(value1)?null:value1,"-1".equals(value2)?null:value2,operatorId,goodsId);
		}else{
			countMap=laoTrafficMmDao.selectRemainData(channelCustId,parseInt,month, "-2".equals(parseInt2+"")?null:parseInt2,"-1".equals(value1)?null:value1,"-1".equals(value2)?null:value2,operatorId,goodsId);
		}
		BigDecimal sum = (BigDecimal) countMap.get("SUM");//当前企业下，按条件查询这个月用户总数量
		task.setCount(sum.intValue());
		taskMapper.insertSelective(task);
		return id;
	}
	@Override
	public LaoTrafficMMTaskDto getTaskById(Long id) {
		LaoTrafficMMTaskPo task = taskMapper.selectByPrimaryKey(id);
		LaoTrafficMMTaskDto taskDto=new LaoTrafficMMTaskDto();
		BeanMapper.copy(task, taskDto);
		return taskDto;
	}
	@Override
	public void insertBinary(LaoTrafficMMTaskDto taskDao) {
		LaoTrafficMMTaskPo task=new LaoTrafficMMTaskPo();
		BeanMapper.copy(taskDao, task);
		taskMapper.updateByPrimaryKeySelective(task);
	}
	
	
}
