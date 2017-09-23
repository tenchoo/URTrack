package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

public interface LaoUserTotalExt {
     //根据大区和运营商，查询用户数目，1.联通2.移动 3.电信 
    List<Map<String, Object>> queryUserOperNum();
    //根据大区和用户当前状态查询用户数目，1.正常 2.停机 3.待激活
    List<Map<String, Object>> queryUserStateNum();
    
    //对应小柱体
    List<Map<String, Object>> queryIndustryNum();
}
