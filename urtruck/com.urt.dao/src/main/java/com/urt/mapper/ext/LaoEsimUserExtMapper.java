package com.urt.mapper.ext;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoEsimUser;

public interface LaoEsimUserExtMapper {

	int updateByEidAndIccid(LaoEsimUser esimUser);

	//LaoEsimUser queryByEidAndIccidUsername(@Param("eid")String eid, @Param("iccid")String iccid);

	LaoEsimUser queryUser(LaoEsimUser esimUser);

	LaoEsimUser queryUserOrderFail(@Param("eid")String eid, @Param("lenovoid")String username);

	LaoEsimUser queryUserEidandGoodsId(Map<String, Object> paramMap);

	
}
