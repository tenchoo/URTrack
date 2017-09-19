package com.urt.interfaces.User;

import java.util.List;

import com.urt.dto.Goods.LaoUserHisDto;

public interface UserHisService {
	int insertSelective(LaoUserHisDto record);
    int insertBatch(List<LaoUserHisDto> record);
}
