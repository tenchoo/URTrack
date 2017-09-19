package com.urt.interfaces.authority;

import java.util.List;
import java.util.Map;

import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.PasswordBean;

/**
* 类说明：系统用户维护服务接口
* @author zhangbt3
* @date 2016年6月20日 下午3:03:03
*/
public interface TfFSsUserService {

	/**
	 * 功能描述：根据userId查询系统用户
	 * @author zhangbt3
	 * @date 2016年6月20日 下午3:11:01
	 * @param userId
	 * @return
	 */
	public LaoSsAccountDto selectUserById(Long userId);
	
	/**
	 * 功能描述：根据条件分页查询系统用户列表
	 * @author zhangbt3
	 * @date 2016年6月20日 下午3:14:15
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return Map<String,Object>
	 */
	Map<String, Object> selectUserByPage(LaoSsAccountDto dto,int pageNo,int pageSize);
	
	/**
	 * 功能描述：根据用户实体查询用户集合
	 * @author zhangbt3
	 * @date 2016年6月27日 下午7:31:23
	 * @param record
	 * @return List<TfFSsUserPo>
	 */
	List<LaoSsAccountDto> selectUsersByModel(LaoSsAccountDto record);
	
	/**
	 * 功能描述：新增系统用户
	 * @author zhangbt3
	 * @date 2016年6月20日 下午3:11:30
	 * @param dto
	 * @return
	 */
	public int saveUser(LaoSsAccountDto dto);
	
	/**
	 * 功能描述：更新系统用户
	 * @author zhangbt3
	 * @date 2016年6月20日 下午3:12:11
	 * @param dto
	 * @return
	 */
	public int updateUser(LaoSsAccountDto dto);

	/**
	 * 功能描述：重置用户密码（管理员权限）
	 * @author zhangbt3
	 * @date 2016年6月24日 下午2:40:21
	 * @param userId
	 * @return int
	 */
	public int resetPassword(Long userId);
	
	/**
	 * 功能描述：修改密码（用户个人使用）
	 * @author zhangbt3
	 * @date 2016年6月24日 下午2:44:54
	 * @param userId
	 * @return
	 */
	public int changePassword(PasswordBean pb);
}
