package com.urt.mapper.ext;

import java.util.List;

import com.urt.po.LaoAccountRelPo;


public interface LaoAccountRelPoExtMapper {
	List<LaoAccountRelPo> queryRelByRelType(LaoAccountRelPo po);
	LaoAccountRelPo queryRelAccount(String relAccount);
	LaoAccountRelPo queryRelAccountByOpenId(String relAccount);
}