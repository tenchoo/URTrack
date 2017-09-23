package com.urt.interfaces.User;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface UserTotalService {
    //根据大区和运营商，查询用户数目，1.联通2.移动 3.电信 ,行业类别
    Map<String, Long> queryStateDetail();
    Map<String, Long> queryOperDetail();
    Map<String, Long> queryIndustryNum();
    JSONObject getNumber(String startDate,String endDate);
    long getCount();
    JSONObject queryDetailByNio(String startDate,String endDate,Long custId);
    JSONObject initIndexData();
}
