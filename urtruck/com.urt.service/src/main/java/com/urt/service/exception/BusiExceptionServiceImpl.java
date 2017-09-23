package com.urt.service.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.exception.LaoBusiexcpLogDto;
import com.urt.dto.exception.LaoExcpDefDto;
import com.urt.interfaces.exception.BusiExceptionService;
import com.urt.mapper.LaoBusiexcpLogMapper;
import com.urt.mapper.LaoExcpDefMapper;
import com.urt.mapper.ext.LaoBusiexcpLogExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.msgProducter.trade.ExceptionResProducer;
import com.urt.po.LaoBusiexcpLog;
import com.urt.po.LaoExcpDef;
@Service("busiExceptionService")
@Transactional(propagation = Propagation.REQUIRED)
public class BusiExceptionServiceImpl implements BusiExceptionService {

	@Autowired
	private LaoBusiexcpLogMapper laoBusiexcpLogDAO;
	@Autowired
	private LaoBusiexcpLogExtMapper laoBusiexcpLogExtDAO;
	@Autowired
	private LaoExcpDefMapper LaoExcpDefDAO;
	@Autowired
	private ExceptionResProducer exceptionResProducer;

	@Override
	public void insBusiExcpLog(LaoBusiexcpLogDto dto){
		LaoBusiexcpLog record=null;
		if(dto != null){
			record = new LaoBusiexcpLog();
			BeanMapper.copy(dto,record);
			laoBusiexcpLogDAO.insert(record);
		}
	}

	@Override
	public List<LaoBusiexcpLogDto> getAutoResExceptions() {
		List<LaoBusiexcpLogDto> dtoList = null;
		List<LaoBusiexcpLog> autoResExceptions = laoBusiexcpLogExtDAO
				.autoResExceptions();
		if (autoResExceptions != null && autoResExceptions.size() > 0) {
			dtoList = new ArrayList<LaoBusiexcpLogDto>();
			for (LaoBusiexcpLog laoBusiexcpLog : autoResExceptions) {
				LaoBusiexcpLogDto busiexcpLogDto = new LaoBusiexcpLogDto();
				BeanMapper.copy(laoBusiexcpLog, busiexcpLogDto);
				dtoList.add(busiexcpLogDto);
			}
		}
		return dtoList;
	}

	@Override
	public Map<String, Object> getHandedExceptions(LaoBusiexcpLogDto dto,
			int pageNo, int pageSize) {
		Page<LaoBusiexcpLog> page = new Page<LaoBusiexcpLog>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		LaoBusiexcpLog laoBusiexcpLog = new LaoBusiexcpLog();
		if (dto != null) {
			BeanMapper.copy(dto, laoBusiexcpLog);
		}
		params.put("logs", laoBusiexcpLog);
		page.setParams(params);
		List<LaoBusiexcpLog> autoResExceptions = laoBusiexcpLogExtDAO
				.handResExceptions(page);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", ConversionUtil.poList2dtoList(autoResExceptions,
				LaoBusiexcpLogDto.class));
		resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());// 总记录数

		return resultMap;
	}

	@Override
	public LaoBusiexcpLogDto getBusiexcpLog(Long excpId) {
		LaoBusiexcpLog laoBusiexcpLog = laoBusiexcpLogDAO
				.selectByPrimaryKey(excpId);
		LaoBusiexcpLogDto dto = null;
		if (laoBusiexcpLog != null) {
			dto = new LaoBusiexcpLogDto();
			BeanMapper.copy(laoBusiexcpLog, dto);
		}
		return dto;
	}

	@Override
	public LaoExcpDefDto getLaoExcpDefDto(Short excpTypeCode) {
		LaoExcpDef laoExcpDef = LaoExcpDefDAO.selectByPrimaryKey(excpTypeCode);
		LaoExcpDefDto dto = null;
		if (LaoExcpDefDAO != null) {
			dto = new LaoExcpDefDto();
			BeanMapper.copy(laoExcpDef, dto);
		}
		return dto;
	}

	@Override
	public void sendUserMsg(List<Map<String, Object>> listMap) {
		exceptionResProducer.sendTask(listMap);
	}
	
}
