package com.urt.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.ServiceStatus;

public interface ServiceStatusExtMapper {

    List<ServiceStatus> selectByOperatorId(String operatorId);
    
    ServiceStatus selectByStatechangeId(String statechangeId);
    
    ServiceStatus queryState(@Param("serviceId")Integer serviceId, @Param("stateCode")String stateCode);
    
    List<ServiceStatus> selectSvcStatusByIdName(@Param("operatorsId")Integer operatorsId, @Param("serviceName")String serviceName);

}
