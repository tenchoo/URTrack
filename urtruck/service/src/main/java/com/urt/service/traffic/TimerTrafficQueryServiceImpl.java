package com.urt.service.traffic;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.Ability.collect.UserTrafficQuery;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.traffic.LaoTrafficDetailDto;
import com.urt.dto.traffic.LaoTrafficMmDto;
import com.urt.dto.traffic.TrafficQueryNowDto;
import com.urt.interfaces.traffic.TimerTrafficQueryService;
import com.urt.mapper.LaoTrafficMmMapper;
import com.urt.mapper.OperatorsMapper;
import com.urt.mapper.ext.LaoTrafficMmExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.msgProducter.trade.QueryTrafficProducer;
import com.urt.po.LaoTrafficMm;
import com.urt.po.Operators;
import com.urt.utils.SeqID;
import com.urt.utils.SpringContextUtils;
import com.urt.utils.ZkGenerateSeq;

@Service("timerTrafficQueryService")
@Transactional(propagation = Propagation.REQUIRED)
public class TimerTrafficQueryServiceImpl implements TimerTrafficQueryService {
	private static Logger logger = LoggerFactory.getLogger(TimerTrafficQueryServiceImpl.class);
	@Autowired
	private OperatorsMapper operatorsDao;
	@Autowired
	private QueryTrafficProducer queryTrafficProducer;
	@Autowired
	private LaoTrafficMmMapper laoTrafficMmDao;
	@Autowired
	private LaoTrafficMmExtMapper laoTrafficMmExtDao;

	@Override
	public LaoTrafficDetailDto doTrafficQuery(LaoUserDto laoUser) {
		int operatorId = laoUser.getOperatorsId();
		String iccid = laoUser.getIccid();
		Operators op = operatorsDao.selectByPrimaryKey(operatorId);
		LaoTrafficDetailDto laoDto = getTrafficQueryToDay(op, iccid);
		if (laoDto != null) {
			Long batchId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID));
			laoDto.setBatchId(batchId);
			laoDto.setChannelCustId(laoUser.getChannelCustId());
			laoDto.setIccid(iccid);
			laoDto.setMsisdn(laoUser.getMsisdn());
			int operatorsId = op.getOperatorsId();
			laoDto.setOperatorsId(operatorsId);
			laoDto.setUserId(laoUser.getUserId());
			// 查询日期为昨天
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			String cycleStartDate = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
			laoDto.setDataCycle(cycleStartDate);
		}
		return laoDto;
	}
	
	@Override
	public void sendUserMsg(List<Map<String, Object>> listMap){
		queryTrafficProducer.sendMessage(listMap);
	};
	
	private LaoTrafficDetailDto getTrafficQueryToDay(Operators op,
			String iccid) {
		// 根据不同的运营商，调对应的流量查询接口
		LaoTrafficDetailDto dto = null;
		TrafficQueryNowDto trafficDto = null;
		try {
			UserTrafficQuery<?> userTrafficQuery = (UserTrafficQuery<?>) SpringContextUtils.getBean(op.getTrafficQueryClass());
			//查询昨天一天的使用流量
			dto = userTrafficQuery.sendBatchMessage(iccid,op);
			//查询当前剩余流量
			trafficDto = userTrafficQuery.trafficNowQuery(iccid, op);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("查询剩余流量异常！");
			dto = new LaoTrafficDetailDto();
			dto.setUseCount(new BigDecimal(0));
			dto.setDataRemaining(new BigDecimal(0));
			return dto;
		}
		if (trafficDto != null && dto != null) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+trafficDto.getDataRemaining());
			if(StringUtils.isNotBlank(trafficDto.getDataRemaining())){
				dto.setDataRemaining(new BigDecimal(trafficDto.getDataRemaining()));
			}else{
				dto.setDataRemaining(new BigDecimal(0));
			}
		}
		return dto;
	}
	
	@Override
	public int insertSelective(LaoTrafficMmDto record) {
		LaoTrafficMm recordNew = new LaoTrafficMm();
		BeanMapper.copy(record, recordNew);
		return laoTrafficMmDao.insertSelective(recordNew);
	}

	@Override
	public int updateByPrimaryKeySelective(LaoTrafficMmDto record) {
		LaoTrafficMm recordNew = new LaoTrafficMm();
		if (record != null) {
			BeanMapper.copy(record, recordNew);
		}
		return laoTrafficMmDao.updateByPrimaryKeySelective(recordNew);
	}

	@Override
	public LaoTrafficMmDto selectByUseId(Long userId, String dataCycleMm) {
		LaoTrafficMmDto recordNew = null ;
		LaoTrafficMm record = laoTrafficMmExtDao.selectByUseId(userId,dataCycleMm);
		if (record != null) {
			recordNew = new LaoTrafficMmDto();
			BeanMapper.copy(record, recordNew);
		}
		return recordNew;
	}

	@Override
	public BigDecimal getNotSendFlow(Long userId) {
		// TODO Auto-generated method stub
		
		return laoTrafficMmExtDao.getNotSendFlowSum(userId);
	}

}
