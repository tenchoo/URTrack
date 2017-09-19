package com.urt.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoEsimDetail;


public interface LaoEsimDetailExtMapper {
    /**
     * 根据iccid,imei查找正在用的流量卡的iccid
     * @param lenovoId
     * @param imei
     * @return
     */
	List<LaoEsimDetail> getEsimDetails(@Param("lenovoId")String lenovoId, @Param("imei")String imei);
	
	
	LaoEsimDetail getEsimDetailsOnly(@Param("lenovoId")String lenovoId, @Param("imei")String imei);
    /**
     * 更新LAO_ESIM_DETAIL表正在用的流量卡记录
     * @param iccid
     * @param imei
     */
	int updateRemainData(@Param("iccid")String iccid,@Param("imei")String remainData);
   
}