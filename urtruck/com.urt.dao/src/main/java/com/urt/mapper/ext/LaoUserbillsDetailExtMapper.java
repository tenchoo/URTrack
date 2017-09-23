package com.urt.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.LaoUserbillsDetail;
import com.urt.po.LaoUserbillsDetailPo;

public interface LaoUserbillsDetailExtMapper {

    List<LaoUserbillsDetailPo> queryUserBillsDetailsByPage(Page<LaoUserbillsDetail> page);
    
    List<LaoUserbillsDetailPo> queryUserBillsDetails(LaoUserbillsDetail po);
    
    LaoUserbillsDetail getUserBillDetailByChargeId(String chargeId);
    
    Long countTotal(@Param("custId")Long custId, @Param("cycleId")Integer cycleId, @Param("operatosId")Integer operatosId);
}