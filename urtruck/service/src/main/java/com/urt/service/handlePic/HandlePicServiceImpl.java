package com.urt.service.handlePic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.handlePic.LaoPicturesDto;
import com.urt.interfaces.handlePic.HandlePicService;
import com.urt.mapper.LaoPicturesPoMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoPicturesPo;

@Service("handlePicService")
@Transactional(propagation = Propagation.REQUIRED)
public class HandlePicServiceImpl implements HandlePicService {

	@Autowired
	private LaoPicturesPoMapper laoPicturesPoMapper;
	@Override
	public int deleteByPrimaryKey(Long picId) {
		return laoPicturesPoMapper.deleteByPrimaryKey(picId);
	}

	@Override
	public int insert(LaoPicturesDto record) {
		LaoPicturesPo po = null;
		if(record != null){
			po = new LaoPicturesPo();
			BeanMapper.copy(record, po);
		}
		return laoPicturesPoMapper.insert(po);
	}

	@Override
	public int insertSelective(LaoPicturesDto record) {
		LaoPicturesPo po = null;
		if(record != null){
			po = new LaoPicturesPo();
			BeanMapper.copy(record, po);
		}
		return laoPicturesPoMapper.insertSelective(po);
	}

	@Override
	public LaoPicturesDto selectByPrimaryKey(Long picId) {
		LaoPicturesDto dto = null;
		LaoPicturesPo po = laoPicturesPoMapper.selectByPrimaryKey(picId);
		if(po != null){
			dto = new LaoPicturesDto();
			BeanMapper.copy(po, dto);
		}
		return dto;
	}

	@Override
	public int updateByPrimaryKeySelective(LaoPicturesDto record) {
		LaoPicturesPo po = null;
		if(record != null){
			po = new LaoPicturesPo();
			BeanMapper.copy(record, po);
		}
		return laoPicturesPoMapper.updateByPrimaryKeySelective(po);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(LaoPicturesDto record) {
		LaoPicturesPo po = null;
		if(record != null){
			po = new LaoPicturesPo();
			BeanMapper.copy(record, po);
		}
		return laoPicturesPoMapper.updateByPrimaryKeyWithBLOBs(po);
	}

	@Override
	public int updateByPrimaryKey(LaoPicturesDto record) {
		LaoPicturesPo po = null;
		if(record != null){
			po = new LaoPicturesPo();
			BeanMapper.copy(record, po);
		}
		return laoPicturesPoMapper.updateByPrimaryKey(po);
	}

}
