package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import com.urt.common.util.Page;
import com.urt.po.LaoBAccesslog;


public interface LaoBAccesslogExtMapper {

	List<LaoBAccesslog> selectByBalanceId(Map<String, Object> paraMap);
	
	int countByBalanceId(Map<String, Object> paraMap);

	List<Map<String, Object>> queryPage(Map<Object, Object> pageMap);
	
	Integer pageCount(Map<Object, Object> pageMap);
    
	List<Map<String, Object>> queryPageByType(Page<LaoBAccesslog> page);


    //List<Map<String, Object>> queryPage(Page<LaoBAccesslog> page);
	LaoBAccesslog selectByPrimaryKey(Long accessId);
	
	
}