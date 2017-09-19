package com.urt.service.reports;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.reports.laoUserOrderMmDto;
import com.urt.interfaces.reports.FinanciaReportsService;
import com.urt.mapper.ext.laoUserOrderMmPoExtMapper;
import com.urt.miniService.reports.MiniFinanciaReportsServiceImpl;
@Service("financiaReportsService")
@Transactional(propagation = Propagation.REQUIRED)
public class FinanciaReportsImpl implements FinanciaReportsService{
	@Autowired
	private MiniFinanciaReportsServiceImpl minFinanciaReportsImpl;
	@Autowired
	private laoUserOrderMmPoExtMapper laoUserOrderMmPoExtMapper;
	@Override
	public Map<String, Object> queryPage(laoUserOrderMmDto dto, int pageNo,
			int pageSize) {
		Map<String, Object> map = minFinanciaReportsImpl.queryPage(dto, pageNo,
				pageSize);
		return map;
	}

	@Override
	public List<Map<String, Object>> selectFinancia(int indate) {
		List<Map<String, Object>> map = laoUserOrderMmPoExtMapper.selectFinancia(indate);
		return map;
	}

}
