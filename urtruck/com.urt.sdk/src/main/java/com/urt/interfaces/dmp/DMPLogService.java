package com.urt.interfaces.dmp;

import java.util.Map;

import com.urt.dto.dmp.LaoDMPLogDto;


public interface DMPLogService {
	//分页查询日志信息
		Map<String, Object> queryPage(LaoDMPLogDto lgoDto, int pageNo,
				int pageSize);

		void insertDMPLog(LaoDMPLogDto logDto);
}
