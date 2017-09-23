package com.urt.interfaces.exception;

import java.util.List;
import java.util.Map;

import com.urt.dto.exception.LaoBusiexcpLogDto;
import com.urt.dto.exception.LaoExcpDefDto;


public interface BusiExceptionService {

	void insBusiExcpLog(LaoBusiexcpLogDto dto);
	
	//查询自动处理的异常
	List<LaoBusiexcpLogDto> getAutoResExceptions();
	//查询手动处理的异常
	Map<String, Object> getHandedExceptions(LaoBusiexcpLogDto dto, int pageNo, int pageSize);
	//根据excpId 查询异常
	LaoBusiexcpLogDto getBusiexcpLog(Long excpId);
	//根据excpTypeCode 异常类型编码  查询异常定义类
	LaoExcpDefDto getLaoExcpDefDto(Short excpTypeCode);
	
	//批量处理异常信息
	public void sendUserMsg(List<Map<String, Object>> listMap);
}
