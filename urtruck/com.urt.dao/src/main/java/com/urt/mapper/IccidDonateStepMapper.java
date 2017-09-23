package com.urt.mapper;

import org.apache.ibatis.annotations.Param;

import com.urt.po.IccidDonateStep;
 
public interface IccidDonateStepMapper {
    int insert(IccidDonateStep record);

    int insertSelective(IccidDonateStep record);
    
    int update(IccidDonateStep record);
    
    IccidDonateStep doQueryFirst(@Param("iccid")String iccid,@Param("userId")String userId);
}