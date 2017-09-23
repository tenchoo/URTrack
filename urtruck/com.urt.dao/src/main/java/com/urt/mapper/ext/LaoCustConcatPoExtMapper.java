package com.urt.mapper.ext;

import java.util.List;

import com.urt.common.util.Page;
import com.urt.po.LaoCustConcatPo;

public interface LaoCustConcatPoExtMapper{
	public List<LaoCustConcatPo> queryPage(Page<LaoCustConcatPo> page);
}
