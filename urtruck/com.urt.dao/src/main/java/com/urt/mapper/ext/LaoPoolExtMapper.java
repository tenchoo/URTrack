package com.urt.mapper.ext;

import java.util.List;

import com.urt.common.util.Page;
import com.urt.po.LaoPool;
public interface LaoPoolExtMapper {
	List<LaoPool> querypoolInfoMethod(Page<LaoPool> page);

	List<LaoPool> queryPoolNameMethod();

	List<LaoPool> findLaoPool();
}
