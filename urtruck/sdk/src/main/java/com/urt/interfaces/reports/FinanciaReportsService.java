package com.urt.interfaces.reports;

import java.util.List;
import java.util.Map;

import com.urt.dto.reports.laoUserOrderMmDto;

public interface FinanciaReportsService {
	Map<String, Object> queryPage(laoUserOrderMmDto dto, int pageNo,
			int pageSize);

	List<Map<String, Object>> selectFinancia(int indate);
}
