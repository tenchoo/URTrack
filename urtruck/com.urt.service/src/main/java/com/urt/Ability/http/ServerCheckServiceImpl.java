package com.urt.Ability.http;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.urt.Ability.http.log.AccessInDBLogger;
import com.urt.Ability.http.util.Constants;
import com.urt.Ability.http.util.TokenUtils;
import com.urt.Ability.http.util.ToolsUtil;
import com.urt.common.util.ConversionUtil;
import com.urt.dto.LaoPeripheralSysAccessLogDto;
import com.urt.dto.http.HttpServerInfo;
import com.urt.dto.http.LaoCustConfigDTO;
import com.urt.interfaces.http.ServerCheckService;
import com.urt.mapper.LaoKeyManagementMapper;
import com.urt.mapper.LaoPeripheralSysAccessLogMapper;
import com.urt.mapper.LaoProvideServerMapper;
import com.urt.mapper.LaoServerProvideVerifiyMapper;
import com.urt.mapper.LaoUserIpManagerMapper;
import com.urt.mapper.ext.LaoCustConfigExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.modules.nosql.redis.JedisClusterFactory;
import com.urt.po.LaoCustConfig;
import com.urt.po.LaoKeyManagement;
import com.urt.po.LaoPeripheralSysAccessLog;
import com.urt.po.LaoProvideServer;

import net.sf.json.JSONObject;
import redis.clients.jedis.JedisCluster;

@Service("serverCheckService")
public class ServerCheckServiceImpl implements ServerCheckService{
	/**日志****/
	private static final Logger logger = Logger.getLogger(ServerCheckServiceImpl.class);
	//private Date createDate = new Date();

	@Value("${randonIdRedisTime}")
	private String randonIdRedisTime;
	@Autowired
	LaoUserIpManagerMapper ipQuery;

	@Autowired
	LaoKeyManagementMapper laoKey;

	@Autowired
	LaoPeripheralSysAccessLogMapper laoToDb; 

	@Autowired
	LaoProvideServerMapper resourceMap;

	@Autowired
	LaoCustConfigExtMapper laoCustConfigExtMapper;

	@Autowired
	LaoServerProvideVerifiyMapper laoServerProvideVerifiyMapper;

	@Autowired
	protected JedisClusterFactory jedisCluster;

	/*@Autowired
	private RedisClientService redisClientService;*/



	public  HttpServerInfo check(Map<String,String> requstInfo) {
		Map<String,String> request = requstInfo;
		HttpServerInfo httpCheckRspDTO = new HttpServerInfo();
		//
		Map<String,String> paramCheck = parameterCheckCMD(requstInfo);

		if(paramCheck.size()>0){
			httpCheckRspDTO.setRespCode(paramCheck.get(Constants.respCode));
			httpCheckRspDTO.setRespDesc(paramCheck.get(Constants.respDesc));
			return httpCheckRspDTO;
		}
		Map<String,String> ipCheck = ipCheck(requstInfo);
		if(ipCheck.size()>0){
			httpCheckRspDTO.setRespCode(ipCheck.get(Constants.respCode));
			httpCheckRspDTO.setRespDesc(ipCheck.get(Constants.respDesc));
			return httpCheckRspDTO;
		}
		Map<String,String> random = random(requstInfo);
		if(!random.isEmpty()){
			httpCheckRspDTO.setRespCode(random.get(Constants.respCode));
			httpCheckRspDTO.setRespDesc(random.get(Constants.respDesc));
			return httpCheckRspDTO;
		}
		Map<String,String> md5 = md5(requstInfo);
		if(!md5.isEmpty()){
			httpCheckRspDTO.setRespCode(md5.get(Constants.respCode));
			httpCheckRspDTO.setRespDesc(md5.get(Constants.respDesc));
			return httpCheckRspDTO;
		}

		Map<String,String> getServer = getServer(requstInfo);
		if(!getServer.isEmpty()){
			httpCheckRspDTO.setRespCode(getServer.get(Constants.respCode));
			httpCheckRspDTO.setRespDesc(getServer.get(Constants.respDesc));
			if("1000".equals(getServer.get(Constants.respCode))){
				httpCheckRspDTO.setServerName(getServer.get(Constants.ServerTagName));
				httpCheckRspDTO.setOperationName(getServer.get(Constants.OperationName));
			}
			return httpCheckRspDTO;
		}
		logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+request.get(Constants.CustID)+request.get(Constants.Iccid));
		httpCheckRspDTO.setRespCode("9999");
		httpCheckRspDTO.setRespDesc("其他错误");
		return  httpCheckRspDTO;
	}
	/**
	 * 
	 * @param requstInfo
	 * @return
	 * 参数校验
	 */
	private Map<String,String> parameterCheckCMD(Map<String,String> requstInfo){
		logger.info("***************** Parameter begin*******************");
		Map<String,String> m = new HashMap<String,String>();
		boolean isIP = ToolsUtil.checkIp(requstInfo.get(Constants.ipAddress));
		if(!isIP){
			m.put(Constants.respCode, "0001");
			m.put(Constants.respDesc, "IP ERROR:"+(String)requstInfo.get(Constants.ipAddress));
			return  m;
		}
		if(null == requstInfo.get(Constants.CustID)){
			m.put(Constants.respCode, "0011");
			m.put(Constants.respDesc, "custId is not null");
			return m;
		}
		if(null == requstInfo.get(Constants.ServerName)){
			m.put(Constants.respCode, "0032");
			m.put(Constants.respDesc, "serverName is not null");
			return m;
		}
		if(null == requstInfo.get(Constants.MD5)){
			m.put(Constants.respCode, "0020");
			m.put(Constants.respDesc, "sign is not null");
			return m;
		}
		return m;
	}

	private Map<String,String> ipCheck(Map<String,String> requstInfo){
		Map<String,String> m = new HashMap<String,String>();	
		//flag作为一个标识符
		boolean flag = false;
		// TODO Auto-generated method stub
		logger.info("*******************IP地址校验*****************");
		//通过集团编号或者集团名称来查找相应的ip地址
		logger.info("**********IpCheckCMD**********"+ipQuery+"******************");
		int ipNum= 0;
		String cust_ip = "";
		JedisCluster jedisClust = null;
		try {
			//获取jedis集群的连接
			jedisClust = jedisCluster.getObject();
			//根据key从缓存中读取数据
			cust_ip = jedisClust.get(requstInfo.get(Constants.CustID) + "_" + requstInfo.get(Constants.ipAddress));
			//判断缓存中是否有数据，
			if(StringUtils.isBlank(cust_ip)){
				//如果没有数据，去数据库查询IP是否存在。
				ipNum = ipQuery.doQueryIP(Long.valueOf(requstInfo.get(Constants.CustID)), requstInfo.get(Constants.ipAddress));
				cust_ip = String.valueOf(ipNum);
				//0代表randomID是重复的，1代表不重复，当是0的时候，不能往redis里添加数据
				if(!"0".equals(cust_ip)){
					String key = jedisClust.set(requstInfo.get(Constants.CustID) + "_" + requstInfo.get(Constants.ipAddress), cust_ip);
					logger.info("================1、校验IP地址是否存在==============cust_ip======="+cust_ip+"======ip_key====="+key);
					flag = false;
				}else{
					logger.info("====重新打印ipNum===="+ipNum);
					//重复提示用户
					m.put(Constants.respCode, "0003");
					m.put(Constants.respDesc, "IP not applied:"+requstInfo.get(Constants.ipAddress));
					flag = true;
				}
			}else{
				flag = false;
			}
			logger.info("=================2、校验IP地址是否存在==============cust_ip======="+cust_ip);
		} catch (Exception e) {
			//出现异常，打印异常信息，删除错误的缓存数据。
			logger.info("====校验IP地址出现异常===="+e);
			jedisClust.del(requstInfo.get(Constants.CustID) + "_" + requstInfo.get(Constants.ipAddress));
		}
		//出现错误，将flag赋值为true，并将错误数据保存到数据库。
		if(flag){	
			logger.info(m.get(Constants.respDesc));
			AccessInDBLogger log = new AccessInDBLogger();
			log.logIntoFile(laoToDb,Long.valueOf(requstInfo.get(Constants.CustID)), requstInfo.get(Constants.ipAddress), requstInfo.get(Constants.Iccid), requstInfo.get(Constants.ServerName), "1", 
					m.get(Constants.respCode),JSONObject.fromObject(requstInfo).toString(), "",new Date());

		}
		return m;
	}
	
	
	/*public int tryLock(String keyPart, String key, String value, long acquireTimeout, int seconds) {
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
	}*/

	/*public void unLock(String keyPart, String key) {
		String lockKey = keyPart.concat(key);
		try {
			redisClientService.del(lockKey);
			logger.info("unLock  keyLock:" + lockKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/



	/**
	 * 校验randomID时，先校验是否重复，再校验客户存在并且随机码有效，出现错误向数据库记录错误信息
	 * @param requstInfo
	 * @return
	 */
	private Map<String,String> random(Map<String,String> requstInfo){
		Map<String,String> m = new HashMap<String,String>();
		logger.info("*******************Random check begin*****************");
		boolean flag = false;
		LaoCustConfig lConfig = null;
		String custId_lConfig = "";
		String randomId = requstInfo.get("randomId");
		JedisCluster jedisClust = null;
		if(!flag){
			try {
				jedisClust = jedisCluster.getObject();
				Long setnx = jedisClust.setnx(requstInfo.get(Constants.CustID) + "_" + requstInfo.get("randomId"), requstInfo.get("randomId"));
				if(setnx == 1){				
					jedisClust.expire(requstInfo.get(Constants.CustID) + "_" + requstInfo.get("randomId"),Integer.valueOf(randonIdRedisTime));					
				}else{
					m.put(Constants.respCode, "0043");
					m.put(Constants.respDesc, "randomId is not repeat");
					logger.info("******************randomId is not repeat*****************");
					flag = true;
				}
				logger.info("=============setnx==============" + setnx);
			} catch (Exception e) {
				logger.info("=====校验random时出现异常===" + e);
				jedisClust.del(requstInfo.get(Constants.CustID) + "_" + requstInfo.get("randomId"));
				m.put(Constants.respCode, "9999");
				m.put(Constants.respDesc, "后台其他错误");
				flag = true;
			}			
			logger.info("******************randomId*****************" + randomId);	
		}

		
		try {
			custId_lConfig = jedisClust.get("lao_cust_config"  + "_" + requstInfo.get(Constants.CustID));
			if(StringUtils.isBlank(custId_lConfig)){
				lConfig = laoCustConfigExtMapper.selectByPrimaryKey(Long.valueOf(requstInfo.get(Constants.CustID)));
				if(lConfig != null){
					String strr = JSON.toJSONString(lConfig);
					String key = jedisClust.set("lao_cust_config"  + "_" + requstInfo.get(Constants.CustID), strr);
					logger.info("=============校验客户randomId等==============strr========" + strr+"======random_key====="+key);
				}
				
			}else{
				lConfig = JSON.parseObject(custId_lConfig, LaoCustConfig.class);
			}
			logger.info("=============校验客户randomId等==============custId_lConfig========" + custId_lConfig);
		} catch (Exception e) {
			logger.info("=====校验客户randomId时出现异常=======" + e);
			jedisClust.del("lao_cust_config"  + "_" + requstInfo.get(Constants.CustID));
		}
		//1、有效，2、失效。随机码的状态
		if((null !=lConfig && "2".equals(lConfig.getIsRandomCheck()))|| null == lConfig){
			return m;
		}
		
		if(ToolsUtil.checkStringIsNull(randomId)){
			logger.info("******************randomId is not null*****************");	
			m.put(Constants.respCode, "0041");
			m.put(Constants.respDesc, "randomId is not null");
			flag = true;
		}
		if(!flag){
			Pattern pattern = Pattern.compile("[0-9]*"); 
			Matcher isNum = pattern.matcher(randomId);
			if( !isNum.matches() ){
				flag = true; 
			}else if(20 != randomId.length()){ 
				flag = true; 
			}
			if(flag){
				logger.info("******************randomId format is error*****************");	
				m.put(Constants.respCode, "0042");
				m.put(Constants.respDesc, "randomId format is error");
			}

		}

		if(flag){	
			logger.info(m.get(Constants.respDesc));
			AccessInDBLogger log = new AccessInDBLogger();
			log.logIntoFile(laoToDb,Long.valueOf(requstInfo.get(Constants.CustID)), requstInfo.get(Constants.ipAddress), requstInfo.get(Constants.Iccid), requstInfo.get(Constants.ServerName), "1", 
					m.get(Constants.respCode),JSONObject.fromObject(requstInfo).toString(), "",new Date());

		}
		logger.info("******************ctx*****************"+m.get("RESPCODE")+m.get("RESPDESC"));
		return m;
	}

	private Map<String,String> md5(Map<String,String> requstInfo){
		Map<String,String> m = new HashMap<String,String>();

		// TODO Auto-generated method stub
		logger.info("*******************MD5校验*****************");
		LaoKeyManagement laoKeyPO = null;
		String custId_sign = "";
		JedisCluster jedisClust = null;
		try {
			jedisClust = jedisCluster.getObject();
			custId_sign = jedisClust.get("lao_key_management" + "_" + requstInfo.get(Constants.CustID));
			if(StringUtils.isBlank(custId_sign)){
				laoKeyPO = laoKey.selectByCustId(String.valueOf(requstInfo.get(Constants.CustID)));
				if(laoKeyPO != null){
					custId_sign = JSON.toJSONString(laoKeyPO);
					String key = jedisClust.set("lao_key_management" + "_" + requstInfo.get(Constants.CustID), custId_sign);
					logger.info("============校验md5============custId_sign========"+custId_sign+"======md5_key====="+key);
				}
			}else{
				laoKeyPO = JSON.parseObject(custId_sign, LaoKeyManagement.class);
			}
			logger.info("============校验md5============custId_sign========"+custId_sign);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("============校验md5时出现异常============" + e);
			jedisClust.del("lao_key_management" + "_" + requstInfo.get(Constants.CustID));
		}
		boolean flag = false;
		if(null == laoKeyPO){
			m.put(Constants.respCode, "0010");
			m.put(Constants.respDesc, "appKey not configured");
			flag = true;
		}

		//每次调用服务必传的参数
		List<String> reqPubInfo = new ArrayList<String>();
		reqPubInfo.add(Constants.MD5);
		reqPubInfo.add(Constants.Appkey);
		reqPubInfo.add(Constants.ipAddress);

		if(!flag){
			String localmd5 = TokenUtils.md5Sign(requstInfo,reqPubInfo, laoKeyPO.getAuthKey());
			logger.info("*******************localmd5*****************"+localmd5);
			if(!requstInfo.get(Constants.MD5).equals(localmd5)){
				m.put(Constants.respCode, "0021");
				m.put(Constants.respDesc, "sign validators fail");
				flag = true;
				if(null != requstInfo.get("smsContent")){
					logger.info("smsContent is not md5");
					m.clear();
				}
			}	
		}	
		if(flag){	
			logger.info(m.get(Constants.respDesc));
			AccessInDBLogger log = new AccessInDBLogger();
			log.logIntoFile(laoToDb,Long.valueOf(requstInfo.get(Constants.CustID)), requstInfo.get(Constants.ipAddress), requstInfo.get(Constants.Iccid), requstInfo.get(Constants.ServerName), "1", 
					m.get(Constants.respCode),JSONObject.fromObject(requstInfo).toString(), "",new Date());

		}

		return m;
	}

	private Map<String,String> getServer(Map<String,String> requstInfo){
		Map<String,String> m = new HashMap<String,String>();	

		logger.info("*******************Server Check*****************");
		boolean flag =false;
		//通过custid userid serverName来查找是否能调用此服务
		logger.info("*********resourceMap***********"+resourceMap+"******************");
		List<LaoProvideServer> server = null;
		String operationName = "";
		String serverName = "";
		String cust_iccid_serverName = "";
		JedisCluster jedisClust = null;
		try {
			jedisClust = jedisCluster.getObject();
			cust_iccid_serverName = jedisClust.get(requstInfo.get(Constants.ServerName) + "_" + requstInfo.get(Constants.CustID));
			if(StringUtils.isBlank(cust_iccid_serverName)){
				server = resourceMap.selectServer(Long.valueOf(requstInfo.get(Constants.CustID)),requstInfo.get(Constants.Iccid),
						requstInfo.get(Constants.ServerName));
				if(server !=null){
					cust_iccid_serverName = JSON.toJSONString(server);
					String key = jedisClust.set(requstInfo.get(Constants.ServerName) + "_" + requstInfo.get(Constants.CustID), cust_iccid_serverName);
					logger.info("======================校验调用的服务=============cust_iccid_serverName============"+cust_iccid_serverName+"======server_key====="+key);
				}
			}else{
				server = JSON.parseArray(cust_iccid_serverName, LaoProvideServer.class);
			}
			logger.info("======================校验调用的服务=============cust_iccid_serverName============"+cust_iccid_serverName);
		} catch (Exception e) {
			logger.info("======校验调用的服务时出现异常========" + e);
			jedisClust.del(requstInfo.get(Constants.ServerName) + "_" + requstInfo.get(Constants.CustID));
		}
		if(null != server && server.size()>0){
			serverName = server.get(0).getServerTag();
			operationName  = server.get(0).getOperationTag();
		}
		//LaoProvideServer server = resourceMap.selectServer((Integer) ctx.get(Constants.CustID),(String) ctx.get(Constants.Iccid), (String) ctx.get(Constants.ServerName));

		logger.info("*********resourceNum***********"+server+"******************");

		if(("".equals(serverName) || null == serverName) || ("".equals(operationName))|| null == operationName){
			flag = true;
			m.put(Constants.respCode, "0031");
			m.put(Constants.respDesc, "service not open");
		}
		if(!flag){
			m.put(Constants.respCode, "1000");
			m.put(Constants.respDesc, "此服务可以调用");
			m.put(Constants.ServerTagName, serverName);
			m.put(Constants.OperationName, operationName);
		}		
		if(flag){	
			logger.info(m.get(Constants.respDesc));
			AccessInDBLogger log = new AccessInDBLogger();
			log.logIntoFile(laoToDb,Long.valueOf(requstInfo.get(Constants.CustID)), requstInfo.get(Constants.ipAddress), requstInfo.get(Constants.Iccid), requstInfo.get(Constants.ServerName), "1", 
					m.get(Constants.respCode),JSONObject.fromObject(requstInfo).toString(), "",new Date());

		}

		return m;
	}



	/*
	 * (non-Javadoc)
	 * @see com.lenovo.LAOAPI.interfaces.http.ServerCheckService#loggerToDb(java.util.Map, java.util.Map)
	 * 接口描述：调用服务时记录日志
	 */

	@Override
	public void loggerToDb(Map<String, String> requestInfo, Map<String, Object> rspInfo) {
		logger.info("调用服务时记录日志");
		LaoPeripheralSysAccessLog  loginfo = new LaoPeripheralSysAccessLog();
		loginfo.setCustId(Long.valueOf(requestInfo.get(Constants.CustID)));
		loginfo.setIpAddress(requestInfo.get(Constants.ipAddress));
		loginfo.setUserName("");
		loginfo.setErrorCode((String)rspInfo.get(Constants.respCode));
		if("0000".equals(rspInfo.get(Constants.respCode))){
			loginfo.setIsSuccess("0");
		}else{
			loginfo.setIsSuccess("1");
		}	
		loginfo.setReqInfo(JSONObject.fromObject(requestInfo).toString());
		loginfo.setRspInfo(JSONObject.fromObject(rspInfo).toString().length()>2000 ?
				JSONObject.fromObject(rspInfo).toString().substring(0, 2000):JSONObject.fromObject(rspInfo).toString());
		loginfo.setAccessDate(new Date());
		loginfo.setServerName(requestInfo.get(Constants.ServerName));
		loginfo.setUserName(requestInfo.get(Constants.Iccid));	
		AccessInDBLogger log = new AccessInDBLogger();
		log.logIntoDB(loginfo, laoToDb);
		//logger.info("日志内容为："+JSONObject.fromObject(loginfo).toString());
	}
	@Override
	public void savaLogerToDb(LaoPeripheralSysAccessLogDto recordDto) {
		LaoPeripheralSysAccessLog recordPo = (LaoPeripheralSysAccessLog)ConversionUtil.dto2po(recordDto, LaoPeripheralSysAccessLog.class);
		laoToDb.insertSelective(recordPo);
	}
	@Override
	public LaoCustConfigDTO custConfigChenk(String custId){
		LaoCustConfigDTO cDto = new LaoCustConfigDTO();
		LaoCustConfig lc = null;
		String custId_lc = "";
		JedisCluster jedisClust = null;
		try {
			jedisClust = jedisCluster.getObject();
			custId_lc = jedisClust.get("lao_cust_config"  + "_" + custId);
			if(StringUtils.isBlank(custId_lc)){
				lc = laoCustConfigExtMapper.selectByPrimaryKey(Long.valueOf(custId));
                if(lc !=null){
    				custId_lc = JSON.toJSONString(lc);
    				String key = jedisClust.set("lao_cust_config"  + "_" + custId, custId_lc);
    				logger.info("=============第一次查看校验客户ID、AppKey、服务等============custId_lc========" + custId_lc+"======check_key====="+key);
                }
				
			}else{
				lc = JSON.parseObject(custId_lc, LaoCustConfig.class);
			}
			logger.info("=============第二次查看校验客户ID、AppKey、服务等============custId_lc========" + custId_lc);
		} catch (Exception e) {
			logger.info("====校验客户ID、AppKey、服务等====" + e);
			jedisClust.del("lao_cust_config"  + "_" + custId);
		}
		if(null == lc){

		}else{
			BeanMapper.copy(lc, cDto);
		}	
		return cDto;
	}



}