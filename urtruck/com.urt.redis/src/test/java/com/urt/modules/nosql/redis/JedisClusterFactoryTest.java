package com.urt.modules.nosql.redis;

import java.security.SecureRandom;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.urt.modules.mapper.JsonMapper;
import com.urt.modules.nosql.redis.JedisClusterFactory;

import redis.clients.jedis.Jedis;

@DirtiesContext
@ContextConfiguration(locations = { "classpath:META-INF/spring/applicationContext-redis.xml" })
public class JedisClusterFactoryTest extends AbstractJUnit4SpringContextTests{
	@Autowired
	private JedisClusterFactory jedisCluster;

	@Test
	public void testJedisCluster() throws Exception {
		
		Jedis jedis = new Jedis("172.17.8.248", 6379); //172.21.3.99 8888 9999
		System.out.println("Server is running: " + jedis.ping());
		
		jedis.set("name", "jack");
		System.out.println(jedis.get("name"));
		
		jedisCluster.getObject().set("name", "啊芝");
        String val = jedisCluster.getObject().get("name");
        System.out.println(val);
		
		JsonMapper jsonMapper = new JsonMapper();
		SecureRandom random = new SecureRandom();
		int randomIndex = random.nextInt( 10000);

		final String key = new StringBuilder().append("jclustertext").append(":")
				.append(randomIndex).toString();

		Session session = new Session(key);
		session.setAttrbute("name", key);
		session.setAttrbute("seq", randomIndex);
		session.setAttrbute("address", "address:" + randomIndex);
		session.setAttrbute("tel", "tel:" + randomIndex);

	
		jedisCluster.getObject().set(session.getId(), jsonMapper.toJson(session));

		String sessionBackString = jedisCluster.getObject().get(session.getId());
		System.out.println(sessionBackString);
		
	}
}
