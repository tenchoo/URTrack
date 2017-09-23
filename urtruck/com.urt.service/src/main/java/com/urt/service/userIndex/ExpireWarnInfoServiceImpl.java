package com.urt.service.userIndex;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.interfaces.userIndex.ExpireWarnInfoService;
import com.urt.mapper.ext.LaoUserExtMapper;

@Service("expireWarnService")
@Transactional(propagation = Propagation.REQUIRED)
public class ExpireWarnInfoServiceImpl implements ExpireWarnInfoService{
	Logger log = Logger.getLogger(ExpireWarnInfoServiceImpl.class);

	@Autowired
	private LaoUserExtMapper userExtMapper;

	@Override
	public List<Map<String, Object>> selectExpireWarnInfo(long custId) {
		return userExtMapper.selectExpireWarnInfo(custId);
	}

	@Override
	public List<Map<String, Object>> selectExpireWarnInfoAll(long custId) {
		// TODO Auto-generated method stub
		return userExtMapper.selectExpireWarnInfoAll(custId);
	}

}
