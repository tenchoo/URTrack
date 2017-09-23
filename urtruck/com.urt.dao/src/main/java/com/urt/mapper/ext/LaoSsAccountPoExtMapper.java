package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.LaoSsAccountPo;
import com.urt.po.TfFSsUserPo;

public interface LaoSsAccountPoExtMapper {

	/**
	 * 功能描述：分页查询用户列表
	 * @author zhangbt3
	 * @date 2016年6月20日 下午5:03:50
	 * @param tfFSsUserPage
	 * @return List<TfFSsUserPo>
	 */
	List<LaoSsAccountPo> queryUserByPage(Page<LaoSsAccountPo> tfFSsUserPage);
	
	/**
	 * 功能描述：根据用户实体查询用户集合
	 * @author zhangbt3
	 * @date 2016年6月27日 下午7:31:23
	 * @param record
	 * @return List<TfFSsUserPo>
	 */
	List<LaoSsAccountPo> queryUsersByModel(LaoSsAccountPo record);
	
	/**
	 * 功能描述：
	 * @author cuichao
	 * @date 2016年6月20日 下午7:47:12
	 * @param @param loginName
	 * @param @param password
	 * @param @return 
	 * @return TfFSsUserPo
	 */
	 LaoSsAccountPo queryByLoginName(@Param("loginName")String loginName);
	 
	 List<LaoSsAccountPo> queryByCustId(Long custId);
	/**
	 * 功能描述：根据登录用户Id查询客户Id
	 * @author wangfu
	 * @date 2016年5月23日 下午5:17:30
	 * @param @param param
	 * @param @return 
	 * @return List<Map<String,Object>>
	 */
	 Map<String, Object> selectById(Map<String, Object> param);
	 
	 LaoSsAccountPo getUserByRelared(String relaredId);
	 
	 int updateCustId(LaoSsAccountPo record);
}
