package com.urt.cache;

import com.urt.dto.TfFSsUserDto;

/**
* 类说明：
* @author Administrator
* @date 2016年6月12日 下午5:11:03
*/
public enum RedisKey {
	SSUSER_USER_ID("ss_user", "userId",TfFSsUserDto.class),
	SSUSER_LOGIN_NAME("ss_user","loginName",TfFSsUserDto.class),
	SELECT_TYPE_CODE("code_list", "typeCode",null);
	// 成员变量
	private String tableName;
	private String colName;
	private Class<?> dtoClass;
	RedisKey(String tableName,String colName, Class<?> dtoClass ){
		this.tableName=tableName;
		this.colName=colName;
		this.dtoClass=dtoClass;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	public Class<?> getDtoClass() {
		return dtoClass;
	}
	public void setDtoClass(Class<?> dtoClass) {
		this.dtoClass = dtoClass;
	}
}
