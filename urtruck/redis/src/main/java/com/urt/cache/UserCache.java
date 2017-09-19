package com.urt.cache;

import com.urt.dto.UserDto;

public class UserCache extends BaseRedisCache{

	private String keyPrefix = "ss.user:";

	public UserDto getUser(Long id) {
		final String key = new StringBuilder().append(keyPrefix).append(":").append(id).toString();
		String userStr;
		try {
			userStr = get(key);
			// 再重新从Redis中取出并反序列化
			//String sessionBackString = jcluster.get(key);
			if(userStr!=null&&!userStr.isEmpty())
			return  jsonMapper.fromJson(userStr, UserDto.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public UserDto getUserLogName(String loginName) {
		final String keyLoginName = new StringBuilder().append(keyPrefix).append(":").append("LoginName:").append(loginName).toString();
		String userStr;
		try {
			userStr = get(keyLoginName);
			// 再重新从Redis中取出并反序列化
			//String sessionBackString = jcluster.get(key);
			if(userStr!=null&&!userStr.isEmpty())
			return  jsonMapper.fromJson(userStr, UserDto.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void save( UserDto user){
		final String keyid = new StringBuilder().append(keyPrefix).append(":").append(user.getId()).toString();
		final String keyLoginName = new StringBuilder().append(keyPrefix).append(":").append("LoginName:").append(user.getLoginName()).toString();

		try {
			save(keyid, jsonMapper.toJson(user));
			save(keyLoginName, jsonMapper.toJson(user));			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
