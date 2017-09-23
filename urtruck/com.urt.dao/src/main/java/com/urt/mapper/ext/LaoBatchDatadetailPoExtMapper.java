package com.urt.mapper.ext;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.LaoBatchDataPo;
import com.urt.po.LaoBatchDatadetailPo;

/**
 * 批量数据处理结果查询扩展接口
 * @author Administrator
 *
 */
public interface LaoBatchDatadetailPoExtMapper {
	/**
	 * 根据batchId查询数据详情表
	 * @param page
	 * @return
	 */
	public List<LaoBatchDatadetailPo> queryFailPageByBatchId(Page<LaoBatchDatadetailPo> page);
	
	public List<LaoBatchDatadetailPo> queryFailPage();
	
	public int queryPageNum(Map<String,Object> params);

	public List<LaoBatchDatadetailPo> queryFailDataByBatchId(Long batchId);
	
	public List<LaoBatchDatadetailPo> queryDataByBatchId(Long batchId);
	// 批量插入
	int batchInsert(List<LaoBatchDatadetailPo> list);
	
	//去重
	public List<LaoBatchDatadetailPo> duplicateRemoval(@Param("iccid")String iccid, @Param("batchId")String batchId);
	//去重 根据msisdn
	public List<LaoBatchDatadetailPo> duplicateRemovalByMsisdn(@Param("msisdn")String msisdn, @Param("batchId")String batchId);
}