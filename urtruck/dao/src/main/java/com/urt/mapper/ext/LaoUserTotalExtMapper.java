package com.urt.mapper.ext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface LaoUserTotalExtMapper {
     //根据大区和运营商，查询用户数目，1.联通2.移动 3.电信 
    List<Map<String, Object>> queryUserOperNum();
    //根据大区和用户当前状态查询用户数目，1.正常 2.停机 3.待激活
    List<Map<String, Object>> queryUserStateNum();
    //对应小柱体
    List<Map<String, Object>> queryIndustryNum();
    /**
     * 管理员首页
     * @return
     */
    List<Map<String, Object>> queryNumByAdmin(String startDate,String endDate);
    /**
     * 获取总连接数
     * @return
     */
    long getCount();
    
    /**
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    List<Map<String, Object>> queryDetailByNio(Map<String, Object> paraMap);
    
    //NIO index
   
    List<Map<String, Object>> queryCountByLifeCycle();
    
    List<Map<String, Object>> queryCountByValue();
    
    List<Map<String, Object>> queryLinkMom(Map<String, Object> paraMap);
    
    List<Map<String, Object>> getTotalByMM();
    
    List<Map<String, Object>> getLifeCycleSumByMM();
    
    List<Map<String, Object>> getTypeSumByMM();
    
    List<Map<String, Object>> getMomByMM(Map<String, String> map);
    
    Map<String, Object> getRechargeTotal();
    
    List<Map<String, Object>> getRechargeByMom(Map<String, String> paramMap);
    
    List<Map<String, Object>> getRechargeByValue();
    
    long getApiCount();
    
    List<Map<String, Object>> getApiByValue();
    
    List<Map<String, Object>> getApiMom(Map<String, String> paraMap);
    
    List<Map<String, Object>> getLinkByBar();
    
    long getLinkByMonth(String month);
    
    BigDecimal getFlowByApn(String month);
    
    BigDecimal getFlowByApn1(String month);
    
    BigDecimal getFlowByApn2(String month);
    
    Map<String, Object> getAmountByMonth(String month);
    
    long getApiByMonth(String month);
    
}
