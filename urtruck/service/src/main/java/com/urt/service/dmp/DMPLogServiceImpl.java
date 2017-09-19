package com.urt.service.dmp;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.common.util.ConversionUtil;
import com.urt.dto.dmp.LaoDMPLogDto;
import com.urt.interfaces.dmp.DMPLogService;
import com.urt.mapper.LaoDMPLogPoMapper;
import com.urt.miniService.MiniDeviceLogServiceImpl;
import com.urt.po.LaoDMPLogPo;
@Service("dmpLogService")
@Transactional(propagation = Propagation.REQUIRED)
public class DMPLogServiceImpl implements DMPLogService{
	Logger log = Logger.getLogger(DMPServiceImpl.class);
	@Autowired
	private MiniDeviceLogServiceImpl miniDeviceLogServiceImpl;
	@Autowired
	private LaoDMPLogPoMapper logMapper;
	@Override
	public Map<String, Object> queryPage(LaoDMPLogDto logDto, int pageNo,
			int pageSize) {
		log.info("DMP业务进入接口----queryPage----");
		Map<String, Object> map = miniDeviceLogServiceImpl.queryPage(logDto,
				pageNo, pageSize);
		log.info("DMP业务走出接口----queryPage----");
		return map;
	}
	@Override
	public void insertDMPLog(LaoDMPLogDto logDto) {
		logMapper.insertSelective((LaoDMPLogPo) ConversionUtil.dto2po(logDto, LaoDMPLogPo.class));
	}
}
