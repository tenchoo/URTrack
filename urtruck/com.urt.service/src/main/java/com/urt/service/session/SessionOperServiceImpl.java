package com.urt.service.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.interfaces.session.SessionOperService;
import com.urt.service.RedisOperService;
import com.urt.service.unicom.RedisClientService;

/**
 * 会话控制
 * 
 * @author qixg
 *
 */
@Service("sessionOperService")
public class SessionOperServiceImpl implements SessionOperService {

	private static Logger logger = LoggerFactory.getLogger(SessionOperServiceImpl.class);
	
	@Autowired
	private RedisClientService redisClientService;
	
	@Autowired
	private RedisOperService redisOperService;

	@Override
	public void setRedisKeyValue(String seesionId, String part, String Value, int seconds) {
		redisClientService.hset(seesionId, part, Value, seconds);
	}

	@Override
	public String getRedisKeyVaue(String seesionId, String part, int seconds) {
		redisClientService.expire(seesionId, seconds);
		return redisClientService.hget(seesionId, part);
	}

	@Override
	public int tryLock(String keyPart, String key, String value, long acquireTimeout, int seconds) {
		try {//重复任务毕竟个例，如果给每个任务都加锁的话，效率太低，redis压力也大
			logger.info("begin tryLock redis ! ");
			long end = System.currentTimeMillis() + acquireTimeout;
			String keyString = keyPart.concat(key);			
			if (seconds <= 0) {
				throw new RuntimeException("错误的失效key失效时间");
			}
			while (System.currentTimeMillis() < end) {
				if (redisClientService.setnx(keyString, value) == 1) {
					if (redisClientService.expire(keyString, seconds) != 1) {
						logger.error("set key expire failed key is :" + keyString);
						throw new RuntimeException("set key expire failed");
					}
					logger.info("tryLock setnx 1 return keyLock:" + value);
					return 1;
				} else {
					logger.info("tryLock redis but key already exist so sleep 10");
					Thread.sleep(10);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();		
			unLock(keyPart, key);
			return -1;
		}
		return 0;
	}

	@Override
	public void unLock(String keyPart, String key) {
		String lockKey = keyPart.concat(key);
		try {
			redisClientService.del(lockKey);
			logger.info("unLock  keyLock:" + lockKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Long setCluKeyValue(String key, String part, String Value, int seconds) {
		
		return redisOperService.setKeyValue(key, part, Value, seconds);
	}

	@Override
	public String getCluKeyVaue(String key, String part, int seconds) {
		
		return redisOperService.getKeyVaue(key, part, seconds);
	}

	@Override
	public int tryCluLock(String keyPart, String key, String value, long acquireTimeout, int seconds) {
		return redisOperService.tryLock(keyPart, key, value, acquireTimeout, seconds);
	}

	@Override
	public void unCluLock(String keyPart, String key) {
		redisOperService.unLock(keyPart, key);
		
	}
}
