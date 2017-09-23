package com.urt.mapper.ext;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.urt.common.util.Page;
import com.urt.po.LaoBatchDataPo;

/**
 * 批量数据处理结果查询扩展接口
 * @author Administrator
 *
 */
public interface LaoBatchDataPoExtMapper {
	/**
	 * 查询数据总表前更新数据库中的成功数量和失败数量
	 * @return
	 */
	public void updateBatchDate(Map<String,Object> params);
	
	public void updateByBatchId(LaoBatchDataPo dataPo);
	/**
	 * 查询批量数据表
	 * @param page
	 * @return
	 */
	public List<LaoBatchDataPo> queryPage(Page<LaoBatchDataPo> page);
	
	public int queryPageNum(Map<String,Object> params);
}