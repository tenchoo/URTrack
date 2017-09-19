package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;




import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.LaoAcctDepositMmPo;


public interface LaoAcctDepositMmPoExtMapper {
	//分页查询余额中心报表
	List<Map<String, Object>> queryPage(Page<LaoAcctDepositMmPo> page);
	//查询导出信息
	List<Map<String, Object>> selectBalance(@Param("indate")int indate,@Param("indate2")int indate2);
   
}