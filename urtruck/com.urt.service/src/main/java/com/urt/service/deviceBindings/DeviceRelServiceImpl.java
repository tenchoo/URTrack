package com.urt.service.deviceBindings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.LaoDeviceRelDto;
import com.urt.dto.LaoSsStaticDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.interfaces.device.DeviceRelService;
import com.urt.mapper.LaoDeviceRelMapper;
import com.urt.mapper.ext.LaoDeviceRelExtMapper;
import com.urt.mapper.ext.LaoSsStaticPoExtMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoDeviceRel;
import com.urt.po.LaoDeviceRelExt;
import com.urt.po.LaoSsStaticPo;
import com.urt.po.LaoUser;

@Service("deviceRelService")
public class DeviceRelServiceImpl implements DeviceRelService{
    
	
	@Autowired
	private LaoDeviceRelExtMapper laoDeviceRelExtDao;
	@Autowired
	private LaoDeviceRelMapper laoDeviceRelDao;
	@Autowired
	private LaoSsStaticPoExtMapper laoSsStaticPoExtDao;
	@Autowired
	private LaoUserExtMapper laoUserDao;
	
	@Override
	public LaoDeviceRelDto selectByUserIdAndidType(Map<String, Object> map) {
		LaoDeviceRelDto dtoNew = new LaoDeviceRelDto();
		LaoDeviceRel dto = laoDeviceRelExtDao.selectByUserIdAndidType(map);
		BeanMapper.copy(dto, dtoNew);
		return dtoNew;
	}

/*	@Override
	public List<LaoDeviceRelDto> selectByCondition(Map<String, Object> map) {
		List<LaoDeviceRelDto> listNew = null;
		List<LaoDeviceRel> list = LaoDeviceRelExtDao.selectByCondition(map);
		if (list != null && list.size() > 0) {
			LaoDeviceRel dto = new LaoDeviceRel();
			listNew = new ArrayList<LaoDeviceRelDto>();
			LaoDeviceRelDto dtoNew = new LaoDeviceRelDto();
			BeanMapper.copy(dto, dtoNew);
			listNew.add(dtoNew);
		}
		return listNew;
	}*/

	@Override
	public int batchInsert(List<LaoDeviceRelDto> list) {
		List<LaoDeviceRel> listDto = new ArrayList<LaoDeviceRel>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LaoDeviceRelDto dto = list.get(i);
				LaoDeviceRel dtoNew = new LaoDeviceRel();
				BeanMapper.copy(dto, dtoNew);
				listDto.add(dtoNew);
			}
		}
		return laoDeviceRelExtDao.batchInsert(listDto);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectByCondition(LaoDeviceRelDto dto,
			int pageNo, int pageSize) {
		Page<LaoDeviceRelExt> deviceRelExtpage = new Page<LaoDeviceRelExt>();
		deviceRelExtpage.setPageNo(pageNo);
		deviceRelExtpage.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (LaoDeviceRelExt) ConversionUtil.dto2po(dto, LaoDeviceRelExt.class));
		deviceRelExtpage.setParams(params);
		List<LaoDeviceRelExt> deviceRelList = laoDeviceRelExtDao.selectByCondition(deviceRelExtpage);
		List<LaoDeviceRelDto> deviceRelDtoList = ConversionUtil.poList2dtoList(deviceRelList, LaoDeviceRelDto.class);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", deviceRelDtoList);
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", deviceRelExtpage.getTotalRecord());//总记录数 
		return resultMap;
	}

	@Override
	public List<LaoSsStaticDto> getIdTypeByDeviceRel() {
		List<LaoSsStaticDto> listDto = null ;
		List<LaoSsStaticPo> list = laoSsStaticPoExtDao.getIdTypeByDeviceRel();
		if (list != null && list.size() > 0) {
			listDto = new ArrayList<LaoSsStaticDto>();
			for (LaoSsStaticPo laoSsStaticPo : list) {
				LaoSsStaticDto dto = new LaoSsStaticDto();
				BeanMapper.copy(laoSsStaticPo, dto);
				listDto.add(dto);
			}
		}
		return listDto;
	}

	@Override
	public LaoUserDto selectByIccid(String iccid) {
		LaoUserDto laoUserDto = null ;
		LaoUser laoUser = laoUserDao.selectByIccid(iccid);
		if (laoUser != null) {
			laoUserDto = new LaoUserDto();
			BeanMapper.copy(laoUser, laoUserDto);
		}
		return laoUserDto;
	}

	@Override
	public int insertSelective(LaoDeviceRelDto record) {
		if (record != null) {
			LaoDeviceRel recordNew = new LaoDeviceRel();
			BeanMapper.copy(record, recordNew);		
			return laoDeviceRelDao.insertSelective(recordNew);
		}
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(Long relId) {
		return laoDeviceRelDao.deleteByPrimaryKey(relId);
	}

	@Override
	public int updateByPrimaryKeySelective(LaoDeviceRelDto record) {
		LaoDeviceRel recordNew = null;
		if (record != null) {
			recordNew = new LaoDeviceRel();
			BeanMapper.copy(record, recordNew);	
		}
		return laoDeviceRelDao.updateByPrimaryKeySelective(recordNew);
	}

	@Override
	public LaoDeviceRelDto selectByPrimaryKey(Long relId) {
		LaoDeviceRel laoDeviceRel = laoDeviceRelDao.selectByPrimaryKey(relId);
		LaoDeviceRelDto dto = null;
			if (laoDeviceRel != null) {
				dto = new LaoDeviceRelDto();
				BeanMapper.copy(laoDeviceRel, dto);	
			}
		return dto;
	}
	
}
