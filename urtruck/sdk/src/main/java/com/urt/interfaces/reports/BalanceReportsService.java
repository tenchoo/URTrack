package com.urt.interfaces.reports;

import java.util.List;
import java.util.Map;

import com.urt.dto.reports.LaoAcctDepositMmDto;

public interface BalanceReportsService {
	Map<String, Object> queryPage(LaoAcctDepositMmDto dto, int pageNo,
			int pageSize);

	List<Map<String, Object>> selectBalance(int indate,int indate2);
}
