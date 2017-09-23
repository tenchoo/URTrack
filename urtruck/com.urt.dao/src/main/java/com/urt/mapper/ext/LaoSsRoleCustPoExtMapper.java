package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Primary;

import com.urt.po.LaoSsRoleCustPo;


public interface LaoSsRoleCustPoExtMapper {

	List<Map<String, Object>> queryPage(Map<String, Object> paraMap);

	Integer countTotal(@Param("custName")String custName);

	LaoSsRoleCustPo queryByCustId(Long custId);

	void insertLaoRoleServerConn(@Param("roleId")Long roleId, @Param("serverId")Long serverId);

	List<Map<String, Object>> queryLaoRoleServerConn(@Param("roleId")Long roleId, @Param("serverId")Long serverId);

	Long queryRoleMax();

	int deleteLaoRoleServerConn(Long roleId);
}
