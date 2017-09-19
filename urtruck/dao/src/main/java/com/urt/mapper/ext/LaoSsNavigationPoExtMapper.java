package com.urt.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoSsNavigationPo;
import com.urt.po.TfFSsNavigationPo;

/**
 * 功能描述：导航菜单扩展接口
 * @author cuichao
 * @date 2016年6月21日
 */
public interface LaoSsNavigationPoExtMapper {
    
	/**
	 * 功能描述：根据用户查询用户菜单
	 * @author cuichao
	 * @date 2016年6月21日 上午10:29:51
	 * @param @param userId
	 * @param @return 
	 * @return List<TfFSsNavigationPo>
	 */
	List<LaoSsNavigationPo> queryListByUserId(Long userId);
	
	/**
	 * 功能描述：查询菜单
	 * @author zhaoxy9
	 * @date 2016年6月22日 下午5:29:36
	 * @param @return 
	 * @return List<TfFSsNavigationPo>
	 */
	List<LaoSsNavigationPo> queryList();
}