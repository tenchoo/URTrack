package com.urt.service.lbs;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.JedisCluster;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.EcCMCC.utils.DigestUtils;
import com.urt.interfaces.lbs.LocationSlisService;
import com.urt.mapper.LaoOperatordealLogMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.modules.nosql.redis.JedisClusterFactory;
import com.urt.msgProducter.trade.LbsQueryLocationProducer;
import com.urt.po.LaoOperatordealLog;
import com.urt.po.LaoUser;
import com.urt.service.dmp.baiduutil.BaiduUtil;
import com.urt.utils.HttpClientUtil;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

@Service("locationSlisService")
@Transactional(propagation = Propagation.REQUIRED)
public class LocationSlisServiceImpl implements LocationSlisService{

	protected static final Logger logger = Logger.getLogger(LocationSlisServiceImpl.class);

	private static final String url="http://183.230.96.66:8087/v2/";
	protected  String appid="";//SWT82AW(浙江);//CE8O2GB(东莞)//STQJ24N(lbs)//
	protected  String passwd="";//LXDD41;//LXDD85//LXDD11
	
	@Value("${queryLocationRedisTime}")
	private String queryLocationRedisTime;
	@Autowired
	private LaoUserExtMapper laoUserDao;
	@Autowired
	private LaoOperatordealLogMapper laoOperatordealLogDao;
	@Autowired
	protected JedisClusterFactory jedisCluster;
	@Autowired
	protected LbsQueryLocationProducer lbsQueryLocationProducer;

	@Override
	public String queryLocationSlis(String iccid) {
		String strLocation = "";
		LaoOperatordealLog loggerLog = new LaoOperatordealLog();
		loggerLog.setSuccess("1"); // 0成功1失败
		try {
			LaoUser laoUser = laoUserDao.selectByIccid(iccid);
			if (laoUser != null) {
				int operatorId = laoUser.getOperatorsId();
				if (5 == operatorId) {
					//浙江移动lbs
					this.appid="STQJ24N";
					this.passwd="LXDD11";
				} else if (2 == operatorId){
					//东莞移动lbs
					this.appid="CE8O2GB";
					this.passwd="LXDD85";
				} else {
					logger.info("===========================所传ICCID运营商有误！");
					strLocation = "operatorsError";
					return strLocation;
				}
				logger.info("=======================根据运营商ID="+operatorId+"确定appid="+appid+",passwd="+passwd);
				// 调用EC lbs接口
				loggerLog.setInputParameters(iccid);
				String result = locationSlis(iccid);
				loggerLog.setOutputParameters(result);
				logger.info("========================lbs接口返回result="+result);
				String lon = "";
				String lat = "";
				if (!StringUtils.isBlank(result)) {
					JSONObject json = JSON.parseObject(result);
					if (json.containsKey("status") && "0".equals(json.getString("status"))) {
						loggerLog.setResultInfo("success");
						loggerLog.setSuccess("0"); // 0成功1失败
						loggerLog.setResultCode("0"); // 0成功1失败
						JSONArray resultArray = json.getJSONArray("result");
						if (null != resultArray && resultArray.size() > 0) {
							JSONObject resultJSONObj = (JSONObject) resultArray.get(0);
							lon = resultJSONObj.getString("lon");// longitude经度
							lat = resultJSONObj.getString("lat");// latitude纬度
							strLocation = lon + "," + lat;
						}
					} else if (json.containsKey("status") && "103".equals(json.getString("status"))) {
						result = locationSlis(iccid);
						json = JSON.parseObject(result);
						if (json.containsKey("status") && "0".equals(json.getString("status"))) {
							loggerLog.setResultInfo("success");
							loggerLog.setSuccess("0"); // 0成功1失败
							loggerLog.setResultCode("0"); // 0成功1失败
							JSONArray resultArray = json.getJSONArray("result");
							if (null != resultArray && resultArray.size() > 0) {
								JSONObject resultJSONObj = (JSONObject) resultArray.get(0);
								lon = resultJSONObj.getString("lon");// longitude经度
								lat = resultJSONObj.getString("lat");// latitude纬度
								strLocation = lon + "," + lat;
							}
						} else if (json.containsKey("status") && "104".equals(json.getString("status"))) {
							loggerLog.setResultInfo("success");
							loggerLog.setSuccess("0"); // 0成功1失败
							loggerLog.setResultCode("0"); // 0成功1失败
							strLocation = "shutError";
						} else {
							logger.info("=========================调用调用EC lbs接口返回失败");
							loggerLog.setResultInfo("");
							loggerLog.setSuccess("1"); // 0成功1失败
							loggerLog.setResultCode("1"); // 0成功1失败
						}
					} else if (json.containsKey("status") && "104".equals(json.getString("status"))) {
							loggerLog.setResultInfo("success");
							loggerLog.setSuccess("0"); // 0成功1失败
							loggerLog.setResultCode("0"); // 0成功1失败
							strLocation = "shutError";
					} else {
						logger.info("=========================调用调用EC lbs接口返回失败");
						strLocation = "otherError";
						loggerLog.setResultInfo("");
						loggerLog.setSuccess("1"); // 0成功1失败
						loggerLog.setResultCode("1"); // 0成功1失败
					}
				} else {
					strLocation = "otherError";
					logger.info("=========================调用调用EC lbs接口返回失败");
					loggerLog.setResultInfo("失败");
					loggerLog.setSuccess("1"); // 0成功1失败
					loggerLog.setResultCode("1"); // 0成功1失败
				}
				/*// 调用百度工具查询地址名称
				if (!StringUtils.isBlank(lon)&&!StringUtils.isBlank(lat)) {
					String address = BaiduUtil.getAddress(lat, lon);
					logger.info("========================百度工具getAddress接口返回address="+address);
					if (!StringUtils.isBlank(address)) {
						strLocation = address;
					}
				}*/
			} else {
				logger.info("===========================所传ICCID有误！");
				strLocation = "iccidError";
				return strLocation;
			}
		} catch (Exception e) {
			strLocation = "lbsError";
			logger.info("========================调用接口异常！====================");
			e.printStackTrace();
		} finally {
			loggerLog.setIccid(iccid);
			loggerLog.setOperatorType("6"); // 6物联卡定位
			loggerLog.setCreateDate(new Date());
			loggerLog.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
			laoOperatordealLogDao.insertSelective(loggerLog);
		}
		return strLocation;
	}

	public String  locationSlis(String  iccid){
		String method="location_slis";
		String ebid="0001000000191";
		String transid=appid+DigestUtils.dataString();
		String token = DigestUtils.sha256Hex(appid+passwd+transid);
		String httpUrl=url+method+"?"+"appid="+appid+"&"+"transid="
				+transid+"&"+"ebid="+ebid+"&"+"token="+token+"&iccid="+iccid;
		String result = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		return result;
	}

	@Override
	public String queryLocationjedisCluster(String iccid) {
		String addrStr = "";
		try {
			JedisCluster jedisClust = jedisCluster.getObject();
			addrStr = jedisClust.get(iccid + "_" + "jedisLbs");
			logger.info("============================================jedisCluster取值,iccid="+iccid+"==============addrStr="+addrStr);
			if (StringUtils.isBlank(addrStr)) {
				addrStr = queryLocationSlis(iccid);
				logger.info("============================================jedisCluster存值,iccid="+iccid+"==============addrStr="+addrStr);
				if (!StringUtils.isBlank(addrStr)&&!addrStr.equals("Error")) {
					jedisClust.set(iccid + "_" + "jedisLbs", addrStr);
					// 设置count和数据的过期时间 2*60*60
					jedisClust.expire(iccid + "_" + "jedisLbs", Integer.valueOf(queryLocationRedisTime));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return addrStr;
	}

	@Override
	public void sendLocationjedis(List<Map<String, Object>> listMap) {
		
		lbsQueryLocationProducer.sendMessage(listMap);
	}

	@Override
	public String queryByBaiduUtil(String addrStr) {
		String strLocation = "";
		String [] addr = addrStr.split(",");
		// 调用百度工具查询地址名称
		if (!StringUtils.isBlank(addr[0])&&!StringUtils.isBlank(addr[1])) {
			String address = BaiduUtil.getAddress(addr[1], addr[0]);
			logger.info("========================百度工具getAddress接口返回address="+address);
			if (!StringUtils.isBlank(address)) {
				strLocation = address;
			}
		}
		return strLocation;
	}
}
