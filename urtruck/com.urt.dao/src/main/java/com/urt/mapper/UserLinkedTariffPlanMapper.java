package com.urt.mapper;

import com.urt.po.UserLinkedTariffPlan;
 
public interface UserLinkedTariffPlanMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserLinkedTariffPlan record);

    int insertSelective(UserLinkedTariffPlan record);

    UserLinkedTariffPlan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserLinkedTariffPlan record);

    int updateByPrimaryKey(UserLinkedTariffPlan record);
}