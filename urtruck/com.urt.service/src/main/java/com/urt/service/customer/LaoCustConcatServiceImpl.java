package com.urt.service.customer;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.dto.LaoCustConcatDto;
import com.urt.dto.LaoCustomerDto;
import com.urt.interfaces.customer.LaoCustConcatService;
import com.urt.mapper.LaoCustConcatPoMapper;
import com.urt.miniService.MiniSsCustConcatServiceImpl;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoCustConcatPo;
import com.urt.po.LaoCustomerPo;
@Service("laoCustConcatService")
public class LaoCustConcatServiceImpl implements LaoCustConcatService{
	@Autowired
	MiniSsCustConcatServiceImpl miniCustConcatService;
	@Autowired
	LaoCustConcatPoMapper dao;
	@Override
	public Map<String, Object> queryPage(LaoCustConcatDto dto, Integer pageNo,
			Integer pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> map=miniCustConcatService.queryPage(dto, pageNo, pageSize);
		return map;
	}
	@Override
	public int save(LaoCustConcatDto dto) {
		// TODO Auto-generated method stub
		LaoCustConcatPo po=(LaoCustConcatPo)ConversionUtil.dto2po(dto, LaoCustConcatPo.class);
		return dao.insertSelective(po);
	}
	@Override
	public int update(LaoCustConcatDto dto) {
		/*LaoCustConcatPo po=(LaoCustConcatPo)ConversionUtil.dto2po(dto, LaoCustConcatDto.class);*/
		LaoCustConcatPo po=new LaoCustConcatPo();
		BeanMapper.copy(dto, po);
		return dao.updateByPrimaryKeySelective(po);
	}
	@Override
	public LaoCustConcatDto selectDtoById(Long custId) {
		// TODO Auto-generated method stub
		LaoCustConcatPo po=dao.selectByPrimaryKey(custId);
		LaoCustConcatDto dto=(LaoCustConcatDto)ConversionUtil.po2dto(po, LaoCustConcatDto.class);
		return dto;
	}
	@Override
	public int del(Long contactId) {
		// TODO Auto-generated method stub
		return dao.deleteByPrimaryKey(contactId);
	}

}
