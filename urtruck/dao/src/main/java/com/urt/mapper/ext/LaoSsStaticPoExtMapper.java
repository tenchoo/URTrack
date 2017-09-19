package com.urt.mapper.ext;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoSsStaticPo;

public interface LaoSsStaticPoExtMapper {
	List<Map> queryStatic(LaoSsStaticPo record); 
    
    LaoSsStaticPo getStaticByCustIdCode(LaoSsStaticPo record); 

    List<Map> queryStaticByClo(LaoSsStaticPo record);
    
    List<Map> queryStaticByCode(LaoSsStaticPo record);
    
    List<Map> queryStaticByParent(LaoSsStaticPo record);
    
    List<Map> queryProductTypeByCustId(String custId);
    
    List<Map> queryProductTypeVAL1();
    
    List<Map> queryPoolsByCustId(String custId);
    
    List<Map> queryProductVersionByPid(LaoSsStaticPo po);
    
    List<Map> queryProductVersionVAL2(String pid);
    
    List<LaoSsStaticPo> getIdTypeByDeviceRel();
    
	Map<String, Object> getStaticByIccid(String id);
	
	//查询费用类型
	List<LaoSsStaticPo> queryPaymentOps();
	// TO B 智能展业,智能互联,智能车联,运营商物联分类查询用户数及收入情况
	List<Map<String, Object>> queryUserIncomeByIntelligent(@Param("date1")String date1,@Param("date2")String date2,@Param("date3")Date date3);
	// TO C 智能互联 分类查询用户数及收入情况
	List<Map<String, Object>> queryZhiNengHuLian(@Param("date1")String date1,@Param("date2")String date2,@Param("date3")Date date3);
	
	List<Map> queryProductTypeVAL1TSP(String custId);

	LaoSsStaticPo getStaticByCustIdCodeTSP(LaoSsStaticPo queryPo);
}
