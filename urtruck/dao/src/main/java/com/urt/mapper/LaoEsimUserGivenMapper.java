package com.urt.mapper;

import com.urt.po.LaoEsimUserGiven;

public interface LaoEsimUserGivenMapper {
    int insert(LaoEsimUserGiven record);

    int insertSelective(LaoEsimUserGiven record);

	int updateByUserId(LaoEsimUserGiven userGiven);

	LaoEsimUserGiven findByUserId(Long userId);
}