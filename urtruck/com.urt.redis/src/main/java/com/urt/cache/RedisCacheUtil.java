package com.urt.cache;

import java.util.List;
import java.util.Map;

/**
* 类说明：
* @author Administrator
* @date 2016年6月12日 下午7:06:37
*/
public class RedisCacheUtil extends BaseRedisCache{
	/*
	 * 简单读取
	 */
	public String  getStr(RedisKey key,String keyValue) throws Exception{
		String keyId=getRedisKey(key,keyValue);
		return jedisCluster.getObject().get(keyId);		
	}
	/*
	 * 简单读取
	 */
	public Object get(RedisKey key,String keyValue) throws Exception{
		String keyId=getRedisKey(key,keyValue);
		String redisValue =jedisCluster.getObject().get(keyId);		
		return  jsonMapper.fromJson(redisValue,key.getDtoClass());
	}
	/*
	 * 简单写
	 */
	
	public void  save(RedisKey key,String keyValue,String cacheValue ) throws Exception{
		String keyId=getRedisKey(key,keyValue);
		jedisCluster.getObject().set(keyId, cacheValue);	
		
	}
	/*
	 * 简单写map
	 */
	public void  saveMap(RedisKey key,String keyValue,Map<String ,String> cacheValue ) throws Exception{
		String keyId=getRedisKey(key,keyValue);
		jedisCluster.getObject().hmset(keyId, cacheValue);	
	}
	/*
	 * 简单 读map
	 */
	public List<String>  getMap(RedisKey key,String keyValue,final String... fields ) throws Exception{
		String keyId=getRedisKey(key,keyValue);
		return jedisCluster.getObject().hmget(keyId, fields);	
	}
}
