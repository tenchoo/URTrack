package com.urt.service.unicom;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urt.po.UserInfo;

@Service("aliveCheckService")
@Transactional
public class AliveCheckService {
	private static Log log = LogFactory.getLog(AliveCheckService.class);
	public static String REDIS_ALIVE_CHECK_CATAGORY_WITH_CHILD = "REDIS_ALIVE_CHECK_CATAGORY_WITH_CHILD";
	public static String REDIS_ALIVE_CHECK_CATAGORY_PARENT = "REDIS_ALIVE_CHECK_CATAGORY_PARENT";

	@Autowired
	private RedisClientService redisClientService;
	@Autowired
	private TariffPlanService tariffPlanService;
	@Autowired
	private UserInfoService userInfoService;
	@Value("${alive.period}")
	private String alivePeriod;
	
	public void checkUserAlive() {
		log.info("aliveCheckService come in....");
		
		Map<String, String> userMap = redisClientService.hgetall(REDIS_ALIVE_CHECK_CATAGORY_PARENT);
		for(Map.Entry<String, String> entry : userMap.entrySet()) {
			String iccid = entry.getKey();

			String aliveTimeTemp = redisClientService.hget(REDIS_ALIVE_CHECK_CATAGORY_WITH_CHILD, iccid);
			UserInfo userInfo = userInfoService.getUserInfoByIccid(iccid);
			if(StringUtils.isEmpty(aliveTimeTemp)) {
				closeInternet(iccid, userInfo.getIccid(), false);
			} else {
				Long now = System.currentTimeMillis();
				double diffMin = (now - Long.valueOf(aliveTimeTemp)) / 1000.0 / 60;
				if(diffMin >= Integer.parseInt(alivePeriod)) {//超过5分钟
					closeInternet(iccid, userInfo.getIccid(), true);
				}
			}
			
		}
	}
	private void closeInternet(String iccid, String userId, boolean isDeleteChildKey) {
		boolean result = tariffPlanService.closeInternet(iccid, userId);
		if(result) {
			redisClientService.hdel(REDIS_ALIVE_CHECK_CATAGORY_PARENT, iccid);
			if(isDeleteChildKey)
				redisClientService.hdel(REDIS_ALIVE_CHECK_CATAGORY_WITH_CHILD, iccid);
		}
	}
	

}
