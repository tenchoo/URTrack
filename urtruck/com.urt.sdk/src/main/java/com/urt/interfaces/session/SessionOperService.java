package com.urt.interfaces.session;



public interface SessionOperService {
	/**
	 * description 根据cookie中的sessionId，所属域，设置对应的value
	 * @param String seesionId 会话id，String part 所属域,String Value 对应得值,int seconds生命周期
	 * return null
	 * */
	void setRedisKeyValue(String seesionId,String part,String Value,int seconds);
	/**
	 * description 根据cookie中的sessionId，所属域，返回对应的value
	 * @param String seesionId 会话id，String part 所属域,int seconds生命周期
	 * return  对应得值或者null
	 * */
	String getRedisKeyVaue(String seesionId,String part,int seconds);
	
	/**
	 * description 根据所属域，key关键字，value值，请求等待时间，锁的有效期 写redis锁
	 * @param String keyPart 域，String key关键字,long acquireTimeout请求等待时间, int seconds生命周期
	 * return  1成功，0失败，-1异常
	 * */
	int tryLock(String keyPart, String key,String value,long acquireTimeout, int seconds);
	/**
	 * description 根据所属域，key关键字，value值，释放redis锁
	 * @param String keyPart 域，String key关键字
	 * return  null
	 * */
	void unLock(String keyPart, String key);
	
	//针对集群，参数及返回值含义如上
	Long setCluKeyValue(String seesionId, String part, String Value, int seconds);
	
	//针对集群，参数及返回值含义如上
	String getCluKeyVaue(String seesionId, String part, int seconds);
	//针对集群，参数及返回值含义如上
	int tryCluLock(String keyPart, String key, String value, long acquireTimeout, int seconds);	
	//针对集群，参数及返回值含义如上
	 void unCluLock(String keyPart, String key) ;
}

