package com.urt.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoDMPGroupPo;
import com.urt.po.LaoDMPStrategyEditPo;

public interface LaoDMPSchemeGroupPoExtMapper {

	List<LaoDMPStrategyEditPo> getSchemesByGroupId(@Param("groupId")Long groupId);

	List<LaoDMPGroupPo> getGroupsByScheme(@Param("id")Long id);

	void deleteBySchemeId(@Param("schemeId")Long schemeId);
    
}