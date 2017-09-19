package com.urt.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.LaoDMPStrategyEditPo;


public interface LaoDMPStrategyEditPoExtMapper {
	List<LaoDMPStrategyEditPo> selectByCustId(@Param("custId")Long custId);

	LaoDMPStrategyEditPo selectByPrimaryKey(@Param("schemeId")long schemeId);

	List<LaoDMPStrategyEditPo> queryPage(Page<LaoDMPStrategyEditPo> page);

	int delStrategyEdit(@Param("schemeId")long schemeId);

	int blockUp(@Param("schemeId")Long schemeId);

	int startUsing(@Param("schemeId")Long id);

	List<LaoDMPStrategyEditPo> getSchemesByGroupId(@Param("groupId")Long groupId);

}