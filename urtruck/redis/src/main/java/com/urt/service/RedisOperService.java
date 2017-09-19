package com.urt.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.urt.cache.BaseRedisCache;
import com.urt.common.enumeration.ConstantIntEnum;
import com.urt.common.util.JsonUtil;

/**
 * <p>
 * Title: RedisClusterServiceImpl.java
 * 
 * <p>
 * Description:
 * 
 * <p>
 * Copyright: Copyright (c) 2017
 * 
 * 
 * @author qxg 2017年4月14日
 * @version 1.0
 */
@Service
public class RedisOperService extends BaseRedisCache {
    private final static Logger logger = Logger.getLogger(RedisOperService.class);

    public Long setRedisObj(String key, Object obj) {
        try {
            if (key != null && obj != null) {
                // 如果字段是哈希表中的一个新建字段，并且值设置成功，返回 1 。 如果哈希表中域字段已经存在且旧值已被新值覆盖，返回 0
                jedisCluster.getObject().set(key, new Gson().toJson(obj));
                return 0L;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 1L;
        }
        return 0L;
    }

    public Long delRedisObj(String key) {
        try {
            return jedisCluster.getObject().del(key);
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public <T> T getRedisObj(String key, Class<T> type) {
        try {
            return JsonUtil.fromJson(jedisCluster.getObject().get(key), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * return 1 if the timeout was set. 0 if key does not exist or the timeout
     * could not be set.
     */
    public Long setKeyValue(String key, String part, String Value, int seconds) {
        try {
            // 如果字段是哈希表中的一个新建字段，并且值设置成功，返回 1 。 如果哈希表中域字段已经存在且旧值已被新值覆盖，返回 0
            jedisCluster.getObject().hset(key, part, Value);
            return jedisCluster.getObject().expire(key, seconds);

        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public String getKeyVaue(String key, String part, int seconds) {
        try {
            if (jedisCluster.getObject().expire(key, seconds) != 1)// seconds
                return null;
            return jedisCluster.getObject().hget(key, part);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long setRedisHttpSession(HttpSession httpSession, String part, Object obj) {
        try {
            if (httpSession != null) {
                // 如果字段是哈希表中的一个新建字段，并且值设置成功，返回 1 。 如果哈希表中域字段已经存在且旧值已被新值覆盖，返回 0
                jedisCluster.getObject().hset(httpSession.getId(), part, new Gson().toJson(obj));
                return jedisCluster.getObject().expire(httpSession.getId(), ConstantIntEnum.RELEATIME.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public <T> T getRedisHttpSession(HttpSession httpSession, String part, Class<T> type) {
        try {
            if (httpSession == null
                    || jedisCluster.getObject().expire(httpSession.getId(), ConstantIntEnum.RELEATIME.getCode()) != 1)// seconds
                return null;
            return JsonUtil.fromJson(jedisCluster.getObject().hget(httpSession.getId(), part), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long setRedisKeyValue(HttpServletRequest request, String part, Object obj) {
        try {
            if (request != null) {
                // 如果字段是哈希表中的一个新建字段，并且值设置成功，返回 1 。 如果哈希表中域字段已经存在且旧值已被新值覆盖，返回 0
                jedisCluster.getObject().hset(request.getRequestedSessionId(), part, new Gson().toJson(obj));
                return jedisCluster.getObject().expire(request.getRequestedSessionId(),
                        ConstantIntEnum.RELEATIME.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public <T> T getRedisKeyVaue(HttpServletRequest request, String part, Class<T> type) {
        try {
            if (request == null || jedisCluster.getObject().expire(request.getRequestedSessionId(),
                    ConstantIntEnum.RELEATIME.getCode()) != 1)// seconds
                return null;
            return JsonUtil.fromJson(jedisCluster.getObject().hget(request.getRequestedSessionId(), part), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int tryLock(String keyPart, String key, String value, long acquireTimeout, int seconds) {
        try {// 重复任务毕竟个例，如果给每个任务都加锁的话，效率太低，redis压力也大        
            long end = System.currentTimeMillis() + acquireTimeout;
            String keyString = keyPart.concat(key);
            logger.info("begin tryLock redis ! key is :" + keyString);
            // int lockExpire = (int) releaseSeconds / 1000;
            if (seconds <= 0) {
                throw new RuntimeException("错误的失效key失效时间");
            }
           // while (System.currentTimeMillis() < end) {
                if (jedisCluster.getObject().setnx(keyString, value) == 1) {
                    if (jedisCluster.getObject().expire(keyString, seconds) != 1) {
                        logger.error("set key expire failed key is :" + keyString);
                        throw new RuntimeException("set key expire failed");
                    }
                    logger.info("tryLock setnx 1 return keyLock:" + keyString);
                    return 1;
                } else {
                    logger.info("tryLock redis but key already exist so return key is :"+keyString);
                    return 0;
                   // Thread.sleep(10);
                }
           // }
        } catch (Exception e) {
            e.printStackTrace();
            unLock(keyPart, key);
            return -1;
        }      
    }

    public void unLock(String keyPart, String key) {
        try {
            String lockKey = keyPart.concat(key);
            jedisCluster.getObject().del(lockKey);
            logger.info("unLock  keyLock:" + lockKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
