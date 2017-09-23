package com.urt.mapper.ext;

import java.util.List;

import com.urt.po.LaoUserHisPo;

public interface LaoUserHisPoExtMapper {
	 int insertBatch(List<LaoUserHisPo> record);
}
