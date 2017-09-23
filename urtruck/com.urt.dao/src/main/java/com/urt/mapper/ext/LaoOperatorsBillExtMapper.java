package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.LaoOperatorsBill;

public interface LaoOperatorsBillExtMapper {
	//批量导入
    int batchInsert(List<LaoOperatorsBill> records);
    
    //查询user_id 
    List<String> getUserIdList( @Param("cycleId")int cycleId, @Param("balanceTag")String balanceTag);
    
    //查询账单
    List<LaoOperatorsBill> getOperatorBillList( @Param("cycleId")Integer cycleId, @Param("operatorsId")Integer operatorsId, @Param("balanceTag")String balanceTag, @Param("userId")String userId);
    
    //查询 {GLATOTALCOST=0, SUMNUM=64076, TOTALCOST=72440750}  gla总费用， 总数， 运营商总费用
    Map<String, Object> queryMap(@Param("cycleId")Integer cycleId, @Param("operatorsId")Integer operatorsId, @Param("balanceTag")String balanceTag);
    
    //分页查询
    List<LaoOperatorsBill> queryOperatorsBills(Page<LaoOperatorsBill> page);
}