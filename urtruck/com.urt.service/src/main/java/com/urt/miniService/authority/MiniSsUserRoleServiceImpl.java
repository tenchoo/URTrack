package com.urt.miniService.authority;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.urt.common.util.ConversionUtil;
import com.urt.dto.LaoSsAccountRoleDto;
import com.urt.mapper.LaoSsAccountRolePoMapper;
import com.urt.mapper.ext.LaoSsAccountRolePoExtMapper;
import com.urt.po.LaoSsAccountRolePo;

/**
 * 类说明：用户角色关系表微服务
 * @author zhangbt3
 * @date 2016年6月20日 下午3:20:16
 */
@Service("miniUserRoleService")
public class MiniSsUserRoleServiceImpl {

	@Autowired
	private LaoSsAccountRolePoExtMapper laoSsAccountRolePoExtMapper;
	@Autowired
	private LaoSsAccountRolePoMapper laoSsAccountRolePoMapper;
	
	/**
	 * 功能描述：根据用户id查询中间表
	 * @author cuichao
	 * @date 2016年6月20日 下午8:09:08
	 * @param @param userId
	 * @param @return 
	 * @return List<TfFSsUserRoleDto>
	 */
	@SuppressWarnings("unchecked")
	public List<LaoSsAccountRoleDto> getListByUserId(Long userId){
		List<LaoSsAccountRolePo> poList = laoSsAccountRolePoExtMapper.queryListByUserId(userId);
		if(CollectionUtils.isNotEmpty(poList)){
			List<LaoSsAccountRoleDto> dtoList = ConversionUtil.poList2dtoList(poList, LaoSsAccountRoleDto.class);
			return dtoList;
		}
		return Collections.emptyList();
	}
	
	/**
	 * 功能描述：根据roleId查询
	 * 
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LaoSsAccountRoleDto> queryListByRoleId(Long roleId) {
		List<LaoSsAccountRolePo> poList = laoSsAccountRolePoExtMapper.queryListByRoleId(roleId);
		if (CollectionUtils.isNotEmpty(poList)) {
			List<LaoSsAccountRoleDto> dtoList = ConversionUtil.poList2dtoList(poList, LaoSsAccountRoleDto.class);
			return dtoList;
		}
		return Collections.emptyList();
	}
	
	/**
	 * 功能描述：新增用户角色中间表
	 * @author zhangbt3
	 * @date 2016年6月23日 上午11:42:08
	 * @param dto
	 * @return
	 */
	public int insertTfFSsUserRole(LaoSsAccountRoleDto dto){
		LaoSsAccountRolePo userRole = (LaoSsAccountRolePo) ConversionUtil.dto2po(dto, LaoSsAccountRolePo.class);
		int result = -1;
		try {
			result = laoSsAccountRolePoMapper.insertSelective(userRole);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 功能描述：删除用户角色中间表
	 * @author zhangbt3
	 * @date 2016年6月23日 下午2:29:24
	 * @param userId
	 * @return
	 */
	public int deleteSsUserRole(Long userId){
		int result = -1;
		List<LaoSsAccountRolePo> SsUserRoleList = laoSsAccountRolePoExtMapper.queryListByUserId(userId);
		if(SsUserRoleList != null && SsUserRoleList.size() > 0 ){
			for (LaoSsAccountRolePo accountRolePo : SsUserRoleList) {
				try {
					result = laoSsAccountRolePoMapper.deleteByPrimaryKey(userId, accountRolePo.getRoleId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
		}
		return result;
	}
}
