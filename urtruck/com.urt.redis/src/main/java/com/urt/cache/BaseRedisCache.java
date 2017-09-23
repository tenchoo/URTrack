package com.urt.cache;

import org.springframework.beans.factory.annotation.Autowired;

import com.urt.modules.mapper.JsonMapper;
import com.urt.modules.nosql.redis.JedisClusterFactory;

/**
* 类说明：redis操作基础类
* @author 付和平
* @date 2016年6月12日 下午4:40:30
*/
public abstract class BaseRedisCache {
	protected JsonMapper jsonMapper = new JsonMapper();
	@Autowired
	protected JedisClusterFactory jedisCluster;
	
	/*
	 * 简单保存
	 */
	protected void save(String keyid,String value) throws Exception{
		jedisCluster.getObject().set(keyid, value);
	}
	/*
	 * 简单读取
	 */
	protected String  get(String keyid) throws Exception{
		return jedisCluster.getObject().get(keyid);
	}
	protected String getRedisKey(RedisKey key,String keyValue){
		return new StringBuilder().append(key.getTableName()).append(":")
				.append(key.getColName()).append(":")
				.append(keyValue).toString();
	}
}
