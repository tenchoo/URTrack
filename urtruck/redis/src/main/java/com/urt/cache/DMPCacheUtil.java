package com.urt.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;


public class DMPCacheUtil extends BaseRedisCache{
    /**
     * 简单读取
     * @throws Exception 
     */
	public String getValue(String key) throws Exception{
		return get(key);	
	}
	/**
	 * 简单保存
	 * @param key
	 * @param value
	 * @throws Exception 
	 */
	public void set(String key,String value) throws Exception{
		save(key,value);
	}
	/**
	 * 删除key
	 * @param key
	 * @throws Exception
	 */
	public void del(String key) throws Exception{
		jedisCluster.getObject().del(key);
	}
	/**
	 * 判断键是否存在
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Boolean exists(String key) throws Exception{
		return jedisCluster.getObject().exists(key);
	}
	 /**
	   * <p>设置key value并制定这个键值的有效期</p>
	   * @param key
	   * @param value
	   * @param seconds 单位:秒
	   * @return 成功返回OK 失败和异常返回null
	   */
	public String  setex(String key,int seconds,String value) throws Exception{
		return jedisCluster.getObject().setex(key, seconds, value);		
	}
	/**
	 * 为键设置过期时间，新的过期时间可以覆盖旧的过期时间
	 * @param key
	 * @param seconds
	 * @return 设置成功返回1，设置失败返回0，
	 * @throws Exception
	 */
	public Long  expire(String key,int seconds) throws Exception{
		return jedisCluster.getObject().expire(key, seconds);		
	}
	/**
	 * 写入set集合，不能存入相同的值 
	 * 返回值为加入了几个元素，如果有重复值，则加入失败，返回0
	 */
	public Long  sadd(String key,String value) throws Exception{
		return jedisCluster.getObject().sadd(key, value);		
	}
	
	/**
	 * 获取redis中的set集合所有值
	 */
	public Set<String>  smembers(String key) throws Exception{
		return jedisCluster.getObject().smembers(key);		
	}
	/**
	 * 返回key的value个数，如果key不存在，返回0
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Long scard(String key) throws Exception{
		return jedisCluster.getObject().scard(key);		
	}
	/**
	 * 	移除并返回set集合中的一个随机元素
	 * @param key
	 * @return key的一个随机值
	 * @throws Exception
	 */
	public String spop(String key) throws Exception{
		return jedisCluster.getObject().spop(key);
	}
	/**
	 * 从set集合中随机获取一个值
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String srandmember(String key) throws Exception{
		return jedisCluster.getObject().srandmember(key);
	}
	/**
	 * 从set集合中随机获取count个值
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List<String> srandmember(String key,int count) throws Exception{
		return jedisCluster.getObject().srandmember(key, count);
	}
	/**
	 * 判断元素是否在集合中
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public Boolean sismember(String key,String value) throws Exception{
		return jedisCluster.getObject().sismember(key, value);	
	}
	
	/**
	 *  把value放入list集合，0下标是第一位，也是最后放入的值，可以重复存储，
	 *  同时放多个值时，末尾的value是最后放入的，下标为0
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public Long lpush(String key,String... value) throws Exception{
		return jedisCluster.getObject().lpush(key, value);
	}
	/**
	 * 根据下标范围取出list的值
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List<String> lrange(String key,long start,long end) throws Exception{
		return jedisCluster.getObject().lrange(key, start, end);
	}
	/**
	 * 删除list的下标为0的value 并返回该value，如果key不存在，返回null
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String lpop(String key) throws Exception{
		return jedisCluster.getObject().lpop(key);
	}
	/**
	 * 获得list的长度，如果key不存在，返回0
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Long llen(String key) throws Exception{
		return jedisCluster.getObject().llen(key);
	}
	/**
	 * 去除list中制定下标的值，没有返回null
	 * @param key
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public String lindex(String key,long index) throws Exception{
		return jedisCluster.getObject().lindex(key, index);
	}
	/**
	 * 删除指定范围外的元素，成功返回OK
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public String ltrim(String key,long start,long end) throws Exception{
		return jedisCluster.getObject().ltrim(key, start, end);
	}
	/**
	 * 向redis的HashMap中加入一个键值对
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public Long hset(String key,String field,String value) throws Exception{
		return jedisCluster.getObject().hset(key, field, value);
	}
	/**
	 * 从redis中获取一个map
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> hgetAll(String key) throws Exception{
		return jedisCluster.getObject().hgetAll(key);
	}
	/**
	 * 获取redis中map的value
	 * @param key
	 * @param field
	 * @return
	 * @throws Exception
	 */
	public String hget(String key,String field) throws Exception{
		return jedisCluster.getObject().hget(key, field);
	}
	/**
	 * 判断以key为键的map中是否存在以field为键的值
	 * @param key
	 * @param field
	 * @return
	 * @throws Exception
	 */
	public Boolean hexists(String key,String field) throws Exception{
		return jedisCluster.getObject().hexists(key, field);
	}
	/**
	 * 获取map中所有的key
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Set<String> hkeys(String key) throws Exception{
		return jedisCluster.getObject().hkeys(key);
	}
	/**
	 * 获取map中所有的value
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List<String> hvals(String key) throws Exception{
		return jedisCluster.getObject().hvals(key);
	}
	public Long hdel(String key,String...field) throws Exception{
		return jedisCluster.getObject().hdel(key, field);
	}
	public static void main(String[] args) throws Exception {
		DMPCacheUtil cacheUtil=new DMPCacheUtil();
		Long result1 = cacheUtil.sadd("sets", "value1");
		Long result2 = cacheUtil.sadd("sets", "value2");
		Long result3 = cacheUtil.sadd("sets", "value3");
		Set<String> values = cacheUtil.smembers("sets");
		System.out.println("result1:"+result1+"--result2:"+result2+"--result3:"+result3+"--values:"+values);
	}
}
