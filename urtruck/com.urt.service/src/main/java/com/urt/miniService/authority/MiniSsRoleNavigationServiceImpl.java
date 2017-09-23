package com.urt.miniService.authority;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.dto.LaoSsRoleNavigationDto;
import com.urt.mapper.LaoSsRoleNavigationPoMapper;
import com.urt.mapper.TfFSsRoleNavigationPoMapper;
import com.urt.mapper.ext.LaoSsRoleNavigationPoExtMapper;
import com.urt.po.LaoSsRoleNavigationPo;
import com.urt.po.TfFSsRoleNavigationPo;

/**
 * 类说明：角色菜单中间表微服务
 * @author zhangbt3
 * @date 2016年6月20日 下午6:55:16
 */
@Service("miniRoleNavigationService")
public class MiniSsRoleNavigationServiceImpl {

	@Autowired
	private LaoSsRoleNavigationPoMapper laoSsRoleNavigationPoMapper;
	@Autowired
	private LaoSsRoleNavigationPoExtMapper laoSsRoleNavigationPoExtMapper;
	/**
	 * 功能描述：角色菜单新增微服务
	 * @author zhangbt3
	 * @date 2016年6月20日 下午7:00:43
	 * @param dto
	 * @return int
	 */
	public int saveSsRoleNavigation(LaoSsRoleNavigationDto dto) {
		LaoSsRoleNavigationPo po = (LaoSsRoleNavigationPo)ConversionUtil.dto2po(dto, LaoSsRoleNavigationPo.class);
		return laoSsRoleNavigationPoMapper.insertSelective(po);
	}
	
	/**
	 * 功能描述：删除角色菜单
	 * @author zhangbt3
	 * @date 2016年6月20日  下午7:10:35
	 * @param roleId
	 * @param navigationId
	 * @return
	 */
	public int deleteSsRoleNavigation(Long roleId,Long navigationId){
		return laoSsRoleNavigationPoMapper.deleteByPrimaryKey(roleId,navigationId);
	}
	/**
	 * 功能描述：根据角色ID查询角色的菜单功能
	 * @author zhaoxy9
	 * @date 2016年6月23日 下午3:45:26
	 * @param @param roleId
	 * @param @return 
	 * @return List<TfFSsRoleNavigationDto>
	 */
	@SuppressWarnings("unchecked")
	public List<LaoSsRoleNavigationDto> queryListByRoleId(Long roleId) {
		List<LaoSsRoleNavigationPo> pos=laoSsRoleNavigationPoExtMapper.queryListByRoleId(roleId);
		return ConversionUtil.poList2dtoList(pos, LaoSsRoleNavigationDto.class);
	}
	/**
	 * 功能描述：根据角色删除角色菜单
	 * @author zhaoxy9
	 * @date 2016年6月23日 下午6:00:52
	 * @param @param roleId 
	 * @return void
	 */
	public void deleteTfFSsRoleNavigationByRoleId(Long roleId) {
		laoSsRoleNavigationPoExtMapper.deleteRoleNavigationByRoleId(roleId);
	}
}
