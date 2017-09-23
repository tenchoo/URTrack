package com.urt.service.reports;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.reports.LaoUserExpireMMDto;
import com.urt.interfaces.reports.ExpireService;
import com.urt.miniService.reports.MiniExpireServiceImpl;

@Service("expireService")
@Transactional(propagation = Propagation.REQUIRED)
public class ExpireServiceImpl implements ExpireService{
	@Autowired
	private MiniExpireServiceImpl minExpireServiceImpl;
	@Override
	public Map<String, Object> queryPage(LaoUserExpireMMDto dto, int pageNo,
			int pageSize) {
		Map<String, Object> map = minExpireServiceImpl.queryPage(dto, pageNo,
				pageSize);
		return map;
	}

}
