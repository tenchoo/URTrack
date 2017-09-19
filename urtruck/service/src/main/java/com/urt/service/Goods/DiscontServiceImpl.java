package com.urt.service.Goods;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.Goods.DiscontDto;
import com.urt.interfaces.Goods.DiscontService;
import com.urt.mapper.DiscontMapper;
import com.urt.mapper.ext.DiscontExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.Discont;
@Service("discontService")
@Transactional(propagation=Propagation.REQUIRED)
public class DiscontServiceImpl implements DiscontService{

	@Autowired
	private DiscontExtMapper discontExtMapper;
	@Autowired
	private DiscontMapper discontMapper;
	

	@SuppressWarnings("null")
	@Override
	public List<DiscontDto> findDiscont() {
		// TODO Auto-generated method stub
		List<DiscontDto> list =new ArrayList<DiscontDto>();		
		List<Discont> discontList = discontExtMapper.findDiscont();
		if(null != discontList || discontList.size() != 0){
			for (Discont discont : discontList) {
				DiscontDto discontDto = new DiscontDto();
				BeanMapper.copy(discont, discontDto);
				list.add(discontDto);
			}
			return list;
		}else{
			return null;
		}

	}
	

	@Override
	public DiscontDto queryByDiscontId(Integer originalId) {
		// TODO Auto-generated method stub
		Discont discont = discontMapper.selectByPrimaryKey(originalId);
		if(null != discont){
			DiscontDto discontDto = new DiscontDto();
			BeanMapper.copy(discontDto, discontDto);		
			return discontDto;
		}else{
			return null;
		}

	}

}
