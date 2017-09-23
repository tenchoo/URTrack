package com.urt.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.LaoSsResourcePo;
import com.urt.po.TfFSsResourcePo;

public interface LaoSsResourcePoExtMapper {

	/**
	 * 功能描述：分页查询资源列表
	 * @author zhaoxy
	 * @date 2016年6月20日 下午5:03:50
	 * @param TfFSsResourcePage
	 * @return List<TfFSsUserPo>
	 */
	List<LaoSsResourcePo> queryResourceByPage(Page<LaoSsResourcePo> tfFSsResourcePage);

	/**
	 * 功能描述：根据系统用户id查询资源列表
	 * @author cuichao
	 * @date 2016年6月21日 下午1:20:35
	 * @param @param userId
	 * @param @return 
	 * @return List<TfFSsResourcePo>
	 */
	List<LaoSsResourcePo> queryListByUserId(Long userId);
	
}
