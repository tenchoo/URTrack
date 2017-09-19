package com.urt.interfaces.CustomerCentre;

import java.util.Map;

import com.urt.dto.Remain.LaoBAccesslogDto;

public interface CustomerQueryService {

	/**
	 * 充值 提现 列表 历史记录查询 
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public  Map<String,Object>  queryPageList(LaoBAccesslogDto dto, int pageNo, int pageSize);
}
