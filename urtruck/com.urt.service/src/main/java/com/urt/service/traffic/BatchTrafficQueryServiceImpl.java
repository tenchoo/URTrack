package com.urt.service.traffic;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.Ability.collect.BatchTrafficQuery;
import com.urt.dto.LaoDeviceRelDto;
import com.urt.dto.traffic.LaoSimDateDetailDto;
import com.urt.interfaces.traffic.BatchTrafficQueryService;
import com.urt.mapper.LaoDeviceRelMapper;
import com.urt.mapper.LaoTrafficMmMapper;
import com.urt.mapper.OperatorsMapper;
import com.urt.mapper.ServiceStatusMapper;
import com.urt.mapper.ext.LaoDeviceRelExtMapper;
import com.urt.mapper.ext.LaoTrafficMmExtMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.msgProducter.trade.QueryTrafficProducer;
import com.urt.po.LaoDeviceRel;
import com.urt.po.LaoUser;
import com.urt.po.Operators;
import com.urt.utils.SeqID;
import com.urt.utils.SpringContextUtils;
import com.urt.utils.ZkGenerateSeq;

@Service("batchTrafficQueryService")
@Transactional(propagation = Propagation.REQUIRED)
public class BatchTrafficQueryServiceImpl implements BatchTrafficQueryService {

	private static Logger logger = LoggerFactory.getLogger(TimerTrafficQueryServiceImpl.class);
	@Autowired
	private LaoUserExtMapper laoUserExtDao;
	@Autowired
	private OperatorsMapper operatorsDao;
	@Autowired
	private QueryTrafficProducer queryTrafficProducer;
	@Autowired
	private LaoTrafficMmMapper laoTrafficMmDao;
	@Autowired
	private LaoTrafficMmExtMapper laoTrafficMmExtDao; 
	@Autowired
	private ServiceStatusMapper serviceStatusDao;
	@Autowired
	private LaoDeviceRelMapper laoDeviceRelDao;
	@Autowired
	private LaoDeviceRelExtMapper laoDeviceRelExtDao;
	
	@Override
	public LaoSimDateDetailDto doTrafficQuery(String iccid,String data,String monthCycle) {
		LaoSimDateDetailDto laoSimDateDetailDto = new LaoSimDateDetailDto();
		LaoUser laoUser = laoUserExtDao.selectByIccid(iccid);
		if (laoUser == null) {
			logger.info("================ 传入iccid有误,laoUser表中无此用户 ==================");
			return null;
		}
		int operatorId = laoUser.getOperatorsId();
		Operators op = operatorsDao.selectByPrimaryKey(operatorId);
		Long batchId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID));
		laoSimDateDetailDto.setBatchId(batchId);
		laoSimDateDetailDto.setChannelCustId(laoUser.getChannelCustId());
		laoSimDateDetailDto.setData(data);
		laoSimDateDetailDto.setIccid(laoUser.getIccid());
		laoSimDateDetailDto.setMonthCycle(monthCycle);
		laoSimDateDetailDto.setMsisdn(laoUser.getMsisdn());
		laoSimDateDetailDto.setOperatorsId(laoUser.getOperatorsId());
		laoSimDateDetailDto.setRecvTime(new Date());
		laoSimDateDetailDto.setUserId(laoUser.getUserId());
		try {
			// 获取dayUseCount monthUseCount dataRemaining imei imsi
			BatchTrafficQuery<?> batchTrafficQuery = (BatchTrafficQuery<?>) SpringContextUtils.getBean(op.getBatchQueryClass());
			batchTrafficQuery.doQuery(laoSimDateDetailDto);
		} catch (Exception e) {
			logger.info("-----------------批量查询流量任务BatchTrafficQueryService异常------------------");
			e.printStackTrace();
		}
/*		
		CardStatusDto cardStatus = null;
		try {
			// 调用卡状态查询方法
			UserQueryCardStatus queryCardStatus = (UserQueryCardStatus) SpringContextUtils.getBean(op.getQueryCardStatus());
			cardStatus = queryCardStatus.queryCardStatus(iccid);
		} catch (Exception e1) {
			logger.info("------------批量查询流量任务中卡状态查询异常--------------");
			e1.printStackTrace();
			return laoSimDateDetailDto;
		}
		if (cardStatus != null) {
			laoSimDateDetailDto.setState(cardStatus.getCardStatus());
		}*/
		return laoSimDateDetailDto;
	}


	@Override
	public int insertSelectiveDevice(LaoDeviceRelDto recordDto) {
		LaoDeviceRel record = new LaoDeviceRel();
		BeanMapper.copy(recordDto, record);
		int i = laoDeviceRelDao.insertSelective(record);
		return i;
	}


	@Override
	public LaoDeviceRelDto selectByUserIdAndidType(Long userId,String idType) {
		LaoDeviceRelDto laoDeviceRelDto = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("idType", idType);
		LaoDeviceRel laoDeviceRel = laoDeviceRelExtDao.selectByUserIdAndidType(map);
		if (laoDeviceRel != null) {
			laoDeviceRelDto = new LaoDeviceRelDto();
			BeanMapper.copy(laoDeviceRel, laoDeviceRelDto);
		}
		return laoDeviceRelDto;
	}


	@Override
	public int updateByPrimaryKeySelective(LaoDeviceRelDto recordDto) {
		LaoDeviceRel record = new LaoDeviceRel();
		BeanMapper.copy(recordDto, record);
		int i = laoDeviceRelDao.updateByPrimaryKeySelective(record);
		return i;
	}

}
