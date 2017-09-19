package com.urt.mapper.ext;

import java.util.List;

import com.urt.common.util.Page;
import com.urt.po.LaoPoolUseInfo;

public interface LaoPoolUseInfoExtMapper {

	List<LaoPoolUseInfo> querypoolUseInfoMethod(Page<LaoPoolUseInfo> page);

}
