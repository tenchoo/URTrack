package com.urt.interfaces.handlePic;

import com.urt.dto.handlePic.LaoPicturesDto;



public interface HandlePicService {
	
	int deleteByPrimaryKey(Long picId);

    int insert(LaoPicturesDto record);

    int insertSelective(LaoPicturesDto record);

    LaoPicturesDto selectByPrimaryKey(Long picId);

    int updateByPrimaryKeySelective(LaoPicturesDto record);

    int updateByPrimaryKeyWithBLOBs(LaoPicturesDto record);

    int updateByPrimaryKey(LaoPicturesDto record);

}
