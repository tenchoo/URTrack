package com.urt.interfaces.authority;

import java.util.List;

import com.urt.dto.LaoSsNavigationDto;

/**
 * 类说明：菜单接口
 * @author Administrator
 * @date 2016年6月22日 下午5:52:01
 */
public interface LaoSsNavigationService {
	/**
	 * 功能描述：查询所有菜单
	 * @author zhaoxy9
	 * @date 2016年6月22日 下午5:53:54
	 * @param @return 
	 * @return List<TfFSsNavigationDto>
	 */
	public List<LaoSsNavigationDto> queryList();
}
