package com.urt.service.traffic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.traffic.LaoTrafficDetailDto;
import com.urt.interfaces.traffic.BatchInsertTrafficQueryService;
import com.urt.mapper.LaoTrafficDetailMapper;
import com.urt.mapper.ext.LaoTrafficDetailExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoTrafficDetail;


@Service("batchInsertTrafficQueryService")
@Transactional(propagation = Propagation.REQUIRED)
public class BatchInsertTrafficQueryServiceImpl implements BatchInsertTrafficQueryService {

	@Autowired
	LaoTrafficDetailMapper trafficQueryDao;
	
	@Autowired
	LaoTrafficDetailExtMapper trafficQueryExtDao;

	@Override
	public int insertSelective(LaoTrafficDetailDto recordDto) {
		LaoTrafficDetail record = new LaoTrafficDetail();
		BeanMapper.copy(recordDto, record);
		return trafficQueryDao.insertSelective(record);
	}

	@Override
	public LaoTrafficDetailDto selectByIccidAndCycle(Map<String, Object> map) {
		LaoTrafficDetail record = trafficQueryExtDao.selectByIccidAndCycle(map);
		LaoTrafficDetailDto recordDto = null;
		if (record != null) {
			recordDto = new LaoTrafficDetailDto();
			BeanMapper.copy(record, recordDto);
		}
		return recordDto;
	}

	@Override
	public int update(LaoTrafficDetailDto recordDto) {
		LaoTrafficDetail record = new LaoTrafficDetail();
		BeanMapper.copy(recordDto, record);
		return trafficQueryDao.updateByPrimaryKeySelective(record);
	}
	
}
