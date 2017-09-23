package com.urt.mapper.ext;

import java.util.Map;

import com.urt.po.LaoTrafficDetail;

public interface LaoTrafficDetailExtMapper {

	LaoTrafficDetail selectByIccidAndCycle(Map<String, Object> map);
}
