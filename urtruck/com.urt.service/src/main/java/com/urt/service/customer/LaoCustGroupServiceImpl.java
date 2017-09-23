package com.urt.service.customer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.dto.LaoCustConcatDto;
import com.urt.dto.LaoCustGroupDto;
import com.urt.dto.LaoCustomerDto;
import com.urt.interfaces.customer.LaoCustGroupService;
import com.urt.mapper.LaoCustGroupPoMapper;
import com.urt.mapper.ext.LaoCustGroupPoExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoCustConcatPo;
import com.urt.po.LaoCustGroupPo;
import com.urt.po.LaoCustomerPo;

@Service(value="laoCustGroupService")
public class LaoCustGroupServiceImpl implements LaoCustGroupService{
	@Autowired LaoCustGroupPoMapper dao;
	
	@Autowired LaoCustGroupPoExtMapper daoExt;
	@Override
	public int save(LaoCustGroupDto dto) {
		// TODO Auto-generated method stub
		LaoCustGroupPo po=(LaoCustGroupPo)ConversionUtil.dto2po(dto, LaoCustGroupPo.class);
		return dao.insertSelective(po);
	}
	@Override
	public int update(LaoCustGroupDto dto) {
		// TODO Auto-generated method stub
		/*LaoCustGroupPo po=(LaoCustGroupPo)ConversionUtil.dto2po(dto, LaoCustGroupPo.class);*/
		LaoCustGroupPo po=new LaoCustGroupPo();
		BeanMapper.copy(dto, po);
		return dao.updateByPrimaryKeySelective(po);
	}
	@Override
	public LaoCustGroupDto selectDtoById(Long custId) {
		LaoCustGroupPo po=dao.selectByPrimaryKey(custId);
		LaoCustGroupDto dto=(LaoCustGroupDto)ConversionUtil.po2dto(po, LaoCustGroupDto.class);
		return dto;
	}
	
    @Override
    public List<LaoCustGroupDto> queryChannelCust(String sellType) {
        List<LaoCustGroupPo> pos=daoExt.queryChannelCust(sellType);
        List<LaoCustGroupDto> dtos=new ArrayList<LaoCustGroupDto>();
        for(LaoCustGroupPo po:pos){
            LaoCustGroupDto dto=new LaoCustGroupDto();
            BeanMapper.copy(po, dto);
            dtos.add(dto);
        }
        return dtos;
    }
	
}
