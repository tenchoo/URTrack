package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.LaoDeviceRel;
import com.urt.po.LaoDeviceRelExt;

public interface LaoDeviceRelExtMapper {

    LaoDeviceRel selectByUserIdAndidType(Map<String,Object> map);
    
    List<LaoDeviceRelExt> selectByCondition(Page<LaoDeviceRelExt> deviceRelExtpage);
    
    int batchInsert(List<LaoDeviceRel> list);
    
    LaoDeviceRel selectByUserId(Long userId);

	String getImsiByIccid(@Param("iccid")String iccid);

	String getIccidByImsi(@Param("imsi")String imsi);
    
}