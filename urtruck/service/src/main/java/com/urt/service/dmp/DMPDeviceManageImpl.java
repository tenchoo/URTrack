package com.urt.service.dmp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.dmp.LaoDMPGroupDto;
import com.urt.interfaces.dmp.DMPDeviceManageService;
import com.urt.mapper.LaoDMPGroupPoMapper;
import com.urt.mapper.ext.LaoDMPGroupPoExtMapper;
import com.urt.miniService.MiniDeviceManageServiceImpl;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoDMPGroupPo;
@Service("dmpDeviceManageService")
@Transactional(propagation = Propagation.REQUIRED)
public class DMPDeviceManageImpl implements DMPDeviceManageService{
	@Autowired
	private MiniDeviceManageServiceImpl minDMPDeviceManageImpl;
	@Autowired
	private LaoDMPGroupPoExtMapper dMPDeviceManageImpl;
	@Autowired
	private LaoDMPGroupPoMapper deviceManageDAO;
	@Override
	public Map<String, Object> queryPage(LaoDMPGroupDto dto, int pageNo,
			int pageSize) {
		Map<String, Object> map = minDMPDeviceManageImpl.queryPage(dto, pageNo,
				pageSize);
		return map;
	}
	@Override
	public Integer delDeviceManage(Long deviceManageId) {
		int result = dMPDeviceManageImpl.delDeviceManage(deviceManageId);
		if (result > 0) {
			return 1;
		}
		return -1;
	}
	@Override
	public Integer saveDeviceManage(LaoDMPGroupDto dto) {
		LaoDMPGroupPo po = new LaoDMPGroupPo();
		BeanMapper.copy(dto, po);
		return deviceManageDAO.insertSelective(po);
	}
	@Override
	public LaoDMPGroupDto getDeviceManageById(Long manageId) {
		// TODO Auto-generated method stub
		LaoDMPGroupDto dto = null;
		LaoDMPGroupPo po = deviceManageDAO.selectByPrimaryKey(manageId);
		if (po != null) {
			dto = new LaoDMPGroupDto();
			BeanMapper.copy(po, dto);
			return dto;	
		}
		return dto;
	}
	@Override
	public Integer updateManage(LaoDMPGroupDto dto) {
		LaoDMPGroupPo po = new LaoDMPGroupPo();
		BeanMapper.copy(dto, po);
		return deviceManageDAO.updateByPrimaryKeySelective(po);
	}
	

}
