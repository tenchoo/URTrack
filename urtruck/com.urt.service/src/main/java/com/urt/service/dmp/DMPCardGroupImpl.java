package com.urt.service.dmp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.common.util.ConversionUtil;
import com.urt.dto.dmp.LaoDMPCardGroupDto;
import com.urt.interfaces.dmp.DMPCardGroupService;
import com.urt.mapper.ext.LaoDMPCardGroupPoExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoDMPCardGroupPo;
import com.urt.po.LaoDMPLogPo;
@Service("dmpCardGroupService")
@Transactional(propagation = Propagation.REQUIRED)
public class DMPCardGroupImpl implements DMPCardGroupService{
	@Autowired
	private LaoDMPCardGroupPoExtMapper dmpCardGroupDao;

	@Override
	public int batchDevice(List<LaoDMPCardGroupDto> list) {
		List<LaoDMPCardGroupPo> listDto = new ArrayList<LaoDMPCardGroupPo>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LaoDMPCardGroupDto dto = list.get(i);
				LaoDMPCardGroupPo po = (LaoDMPCardGroupPo) ConversionUtil.dto2po(dto, LaoDMPCardGroupPo.class);
				listDto.add(po);
			}
		}
			return dmpCardGroupDao.batchInsert(listDto);
	}

	@Override
	public LaoDMPCardGroupDto selectByIm(String imei, Long id) {
		LaoDMPCardGroupDto dto = null;
		LaoDMPCardGroupPo po = dmpCardGroupDao.selectByIm(imei,id);
		if(po!=null){
			dto = (LaoDMPCardGroupDto) ConversionUtil.po2dto(po, LaoDMPCardGroupDto.class);
		}
		return dto;
	}

	@Override
	public LaoDMPCardGroupDto selectByGroupId(Long id) {
		LaoDMPCardGroupDto dto = null;
		LaoDMPCardGroupPo po = dmpCardGroupDao.selectByGroupId(id);
		if(po!=null){
			dto = (LaoDMPCardGroupDto) ConversionUtil.po2dto(po, LaoDMPCardGroupDto.class);
		}
		return dto;
	}

	@Override
	public void delCardGroup(Long id) {
		dmpCardGroupDao.delCardGroup(id);
			
	}

	@Override
	public Integer selectDeviceNum(Long id) {
		Integer deviceNum = dmpCardGroupDao.selectDeviceNum(id);
		return deviceNum;
	}

}
