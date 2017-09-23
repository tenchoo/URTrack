package com.urt.interfaces.reports;

import java.util.Map;

import com.urt.dto.reports.LaoUserExpireMMDto;

public interface ExpireService {
	Map<String, Object> queryPage(LaoUserExpireMMDto dto, int pageNo,
			int pageSize);
}
