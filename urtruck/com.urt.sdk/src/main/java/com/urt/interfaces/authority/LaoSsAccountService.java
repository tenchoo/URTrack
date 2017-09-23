/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.urt.interfaces.authority;

import java.util.List;
import java.util.Map;

import com.urt.dto.LaoSsAccessLogDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoStaffDto;
import com.urt.dto.PasswordBean;
import com.urt.dto.UserDto;



/**
 * 用户管理类.
 * 
 * @author calvin
 */
public interface  LaoSsAccountService {

	
	public List<UserDto> getAllUserDto() ;

	public UserDto getUserDto(Long id) ;

	public LaoSsAccountDto getUserByLoginName(String loginName);

	public void registerUserDto(UserDto user) ;

	public void updateUserDto(UserDto user);

	public void deleteUserDto(Long id) ;
	
	public Long selectById(Long id);
	
	public boolean isSupervisor(Long id);
	
	public LaoSsAccountDto getUserByRelared(String id);
	
	public LaoSsAccountDto selectUserById(String relatedId);
	
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
	
	public int saveCommonUser(LaoSsAccountDto dto);
	
	/**
	 * 功能描述：更新系统用户
	 * @author zhangbt3
	 * @date 2016年6月20日 下午3:12:11
	 * @param dto
	 * @return
	 */
	public int updateUser(LaoSsAccountDto dto);
	
	public int updateAccount(LaoSsAccountDto dto);

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
	public int changePassword(LaoSsAccountDto dto,String oldpw);
	
	public int updateAccountCustId(Long accountId,Long custId);
	
	List<LaoSsAccountDto> queryByCustId(Long custId);
	
	int updateCustId(LaoSsAccountDto dto,long custId);
	
	LaoSsAccountDto queryAccountById(Long id); 
	
	LaoSsAccountDto queryAccountByAcconutId(Long acconutId);
	
	/**
	 * 功能描述：根据accountId,username绑定gla用户信息
	 * @author sunhao
	 * @date 2017年5月16日10:35:43
	 * @param accountId,username
	 * @return
	 */
	public void bindingGla(String username, String password, String openId);
	
	public int addAccessLog(Map<String,Object> mapDto);

	public List<LaoStaffDto> queryStaffs();
	
	/**
	 * 修改账号状态
	 * @param accountId 账号标识
	 * @param status 0表示正常，1表示冻结
	 */
	public void changeAccountStatus(Long accountId, String status);

}
