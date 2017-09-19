package com.urt.miniService.authority;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoSsRoleDto;
import com.urt.mapper.LaoSsRolePoMapper;
import com.urt.mapper.TfFSsRolePoMapper;
import com.urt.mapper.ext.LaoSsRolePoExtMapper;
import com.urt.po.LaoSsRolePo;
import com.urt.po.TfFSsRolePo;
import com.urt.po.TfFSsUserPo;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

/**
 * 类说明：系统角色微服务
 * @author zhangbt3
 * @date 2016年6月20日 上午11:52:16
 */
@Service("miniRoleService")
public class MiniSsRoleServiceImpl {

	@Autowired
	private LaoSsRolePoMapper laoSsRolePoMapper;
	@Autowired
	private LaoSsRolePoExtMapper laoSsRolePoExtMapper;
	/**
	 * 功能描述：系统角色新增微服务
	 * @author zhangbt3
	 * @date 2016年6月20日 上午12:08:43
	 * @param dto
	 * @return int
	 */
	public int saveSsRole(LaoSsRoleDto dto) {
		LaoSsRolePo po = (LaoSsRolePo)ConversionUtil.dto2po(dto, LaoSsRolePo.class);
		return laoSsRolePoMapper.insertSelective(po);
	}
	
	/**
	 * 功能描述：查询系统角色
	 * @author zhangbt3
	 * @date 2016年6月20日 上午12:09:17
	 * @param userId
	 * @return TfFSsRoleDto
	 */
	public LaoSsRoleDto selectSsRoleById(Long roleId) {
		LaoSsRolePo po=laoSsRolePoMapper.selectByPrimaryKey(roleId);
		return (LaoSsRoleDto) ConversionUtil.po2dto(po, LaoSsRoleDto.class);
	}
	
	/**
	 * 功能描述：查询系统角色集合
	 * @author zhangbt3
	 * @date 2016年6月24日 上午10:44:10
	 * @return TfFSsRoleDto
	 */
	@SuppressWarnings("unchecked")
	public List<LaoSsRoleDto> selectAllSsRoles() {
		List<LaoSsRolePo> ssRolePos = laoSsRolePoExtMapper.queryRoleList();
		return ConversionUtil.poList2dtoList(ssRolePos, LaoSsRoleDto.class);
	}
	
	/**
	 * 功能描述：更新系统角色
	 * @author zhangbt3
	 * @date 2016年6月20日 下午14:34:40
	 * @param dto
	 * @return
	 */
	public int updateSsRole(LaoSsRoleDto dto){
		LaoSsRolePo record = (LaoSsRolePo) ConversionUtil.dto2po(dto, LaoSsRolePo.class);
		return laoSsRolePoMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 功能描述：删除系统角色
	 * @author zhangbt3
	 * @date 2016年6月20日 下午14:25:35
	 * @param roleId
	 * @return
	 */
	public int deleteSsRole(Long roleId){
		return laoSsRolePoMapper.deleteByPrimaryKey(roleId);
	}
	
	/**
	 * 功能描述：角色分页查询
	 * @author zhaoxy9
	 * @date 2016年6月22日 下午2:30:50
	 * @param @param dto
	 * @param @param pageNo
	 * @param @param pageSize
	 * @param @return 
	 * @return Map<String,Object>
	 */
	public Map<String, Object> selectTfFRoleByPage(LaoSsRoleDto dto, int pageNo, int pageSize) {
		Page<LaoSsRolePo> rolePage = new Page<LaoSsRolePo>();
		rolePage.setPageNo(pageNo);
		rolePage.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (LaoSsRolePo) ConversionUtil.dto2po(dto, LaoSsRolePo.class));
		rolePage.setParams(params);
		List<LaoSsRolePo> roleList = laoSsRolePoExtMapper.queryRoleByPage(rolePage);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", ConversionUtil.poList2dtoList(roleList, LaoSsRoleDto.class));
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", rolePage.getTotalRecord());//总记录数 
		return resultMap;
	}
}
