package com.urt.service.authority;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.dto.LaoAccountRelDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.interfaces.authority.LaoAccountRelService;
import com.urt.mapper.LaoAccountRelPoMapper;
import com.urt.mapper.ext.LaoAccountRelPoExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.Goods;
import com.urt.po.LaoAccountRelPo;

@Service("laoAccountRelService")
public class LaoAccountRelServiceImpl implements LaoAccountRelService{
	@Autowired
	private LaoAccountRelPoExtMapper relExtDao;
	@Autowired
	private LaoAccountRelPoMapper relDao;
	@Override
	public List<LaoAccountRelDto> queryRelByRelType(LaoAccountRelDto dto) {
		// TODO Auto-generated method stub
		LaoAccountRelPo po=new LaoAccountRelPo();
		BeanMapper.copy(dto, po);
		 List<LaoAccountRelPo> pos = relExtDao.queryRelByRelType(po);
		 List<LaoAccountRelDto> dtos=new ArrayList<LaoAccountRelDto>();
		 LaoAccountRelDto d;
		 for(LaoAccountRelPo p:pos){
			 d=new LaoAccountRelDto();
			 BeanMapper.copy(p, d);
			 dtos.add(d);
		 }
		
		 return dtos;
	}
	@Override
	public int save(LaoAccountRelDto dto) {
		// TODO Auto-generated method stub
		LaoAccountRelPo po=new LaoAccountRelPo();
		BeanMapper.copy(dto, po);
		return relDao.insert(po);
	}
	@Override
	public LaoAccountRelDto queryRelByRelAccount(String relAccount) {
		// TODO Auto-generated method stub
		LaoAccountRelPo queryRelAccount = relExtDao.queryRelAccount(relAccount);
		LaoAccountRelDto laoAccountRelDto = new LaoAccountRelDto();
		BeanMapper.copy(queryRelAccount, laoAccountRelDto);		
		return laoAccountRelDto;

	}
	
	@Override
	public LaoAccountRelDto queryRelAccountByOpenId(String openId) {
		// TODO Auto-generated method stub
		LaoAccountRelPo queryRelAccount = relExtDao.queryRelAccountByOpenId(openId);
		LaoAccountRelDto laoAccountRelDto = new LaoAccountRelDto();
		if (queryRelAccount != null) {
			BeanMapper.copy(queryRelAccount, laoAccountRelDto);		
		}
		return laoAccountRelDto;
		
	}

}
