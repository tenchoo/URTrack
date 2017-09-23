package com.urt.mapper.ext;

import java.util.List;

import com.urt.po.LaoCustGroupPo;

public interface LaoCustGroupPoExtMapper {
	public List<LaoCustGroupPo> selectAll();
	
	public List<LaoCustGroupPo> queryChannelCust(String sellType);
	
	public String queryCustname(String custid);
}
