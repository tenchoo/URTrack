package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

public interface LaoWebDesignExtMapper {

	//根据custId查询纪录
    List<Map<String,Object>> selectAllByCustId(Long custId);
    
    //查询所有的custId
    List<Map<String, Object>> queryAllCustId();
    
    //分页查询所有记录
    List<Map<String, Object>> queryPage(Map<String, Object> param);

    //查询记录条数
    Long getAllCount();
}