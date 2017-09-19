package com.urt.service.reports;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.reports.LaoAcctDepositMmDto;
import com.urt.interfaces.reports.BalanceReportsService;
import com.urt.mapper.ext.LaoAcctDepositMmPoExtMapper;
import com.urt.miniService.reports.MiniBalanceReportsServiceImpl;
@Service("balanceReportsService")
@Transactional(propagation = Propagation.REQUIRED)
public class BalanceReportsImpl implements BalanceReportsService{
	@Autowired
	private MiniBalanceReportsServiceImpl minBalanceReportsImpl;
	@Autowired
	private LaoAcctDepositMmPoExtMapper laoAcctDepositMmPoExtMapper;

	@Override
	public Map<String, Object> queryPage(LaoAcctDepositMmDto dto, int pageNo,
			int pageSize) {
		Map<String, Object> map = minBalanceReportsImpl.queryPage(dto, pageNo,
				pageSize);
		return map;
	}

	@Override
	public List<Map<String, Object>> selectBalance(int indate,int indate2) {
		List<Map<String, Object>> map = laoAcctDepositMmPoExtMapper.selectBalance(indate,indate2);
		return map;
	}

}
