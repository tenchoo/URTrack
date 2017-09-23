package com.urt.mapper.ext;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoEsimImeiEid;

public interface LaoEsimImeiEidExtMapper {

	LaoEsimImeiEid queryByEidAndImei(@Param("imeiid")String imeiid,@Param("eid")String eid);

	LaoEsimImeiEid queryByEid(String eid);

	LaoEsimImeiEid queryByImei(String imei);

}
