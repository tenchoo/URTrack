package com.urt.service.dmp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.gson.Gson;
import com.urt.cache.DMPCacheUtil;
import com.urt.common.util.ConversionUtil;
import com.urt.dto.dmp.LaoDMPStrategyDto;
import com.urt.interfaces.dmp.DMPStrategyService;
import com.urt.mapper.LaoDMPLogPoMapper;
import com.urt.mapper.LaoDMPStrategyEditPoMapper;
import com.urt.mapper.ext.LaoDMPCardGroupPoExtMapper;
import com.urt.mapper.ext.LaoDMPOperationPoExtMapper;
import com.urt.mapper.ext.LaoDMPSchemeStrategyPoExtMapper;
import com.urt.mapper.ext.LaoDMPStrategyEditPoExtMapper;
import com.urt.mapper.ext.LaoDMPStrategyPoExtMapper;
import com.urt.mapper.ext.LaoDMPStrategyRelationPoExtMapper;
import com.urt.po.LaoDMPLogPo;
import com.urt.po.LaoDMPOperationPo;
import com.urt.po.LaoDMPStrategyEditPo;
import com.urt.po.LaoDMPStrategyPo;
import com.urt.po.LaoDMPStrategyRelationPo;
import com.urt.service.dmp.entity.PositionData;
import com.urt.utils.SpringContextUtils;

/**
 * 策略工具类
 * @author Administrator
 *
 */
public class DMPStrategesUtil {
	Logger log = Logger.getLogger(DMPStrategesUtil.class);
	private static double EARTH_RADIUS = 6378.137; // 地球半径
	@Autowired
	private DMPCacheUtil dmpCacheUtil;
	@Autowired
	private LaoDMPStrategyEditPoExtMapper editPoExtMapper;
	@Autowired
	private LaoDMPStrategyEditPoMapper editPoMapper;
	@Autowired
	private LaoDMPSchemeStrategyPoExtMapper dmpSchemeStrategyPoExtMapper;
	@Autowired
	private LaoDMPCardGroupPoExtMapper cardGroupExtMapper;
	@Autowired
	private LaoDMPLogPoMapper dmpLogPoMapper;
	@Autowired
	LaoDMPStrategyRelationPoExtMapper dmpStrategyRelationPoExtMapper;
	@Autowired
	LaoDMPStrategyPoExtMapper strategyPoExtMapper;
	@Autowired
	LaoDMPOperationPoExtMapper operationExtMapper;
	/**
	 * 根据IMEI获取所有针对该IMEI的非公共策略
	 * @param imei
	 * @return
	 */
	public List<LaoDMPStrategyEditPo> getStrategies(String imei) {
		log.info("进入接口getStrategies");
		log.info("imei="+imei);
		List<LaoDMPStrategyEditPo> schemePos=null;
		Gson gson = new Gson();
		try {
			List<String> schemes = dmpCacheUtil.lrange("scheme_"+imei, 0, -1);
			log.info("根据IMEI从redis中获取针对该IMEI的所有方案schemes："+schemes);
			if(schemes==null || schemes.size()<=0){
				log.info("根据IMEI从redis中获取针对该IMEI的所有方案schemes为空，下面根据IMEI去数据库查询");
				schemePos=cardGroupExtMapper.getSchemesbyImei(imei);
				log.info("根据IMEI从数据库查询到的针对该IMEI的方案schemePos："+schemePos);
				if(schemePos!=null&&schemePos.size()>0){
					log.info("根据IMEI从数据库查询到的针对该IMEI的方案schemePos不为空");
					for(LaoDMPStrategyEditPo schemePo:schemePos){
						String schemeStr = gson.toJson(schemePo);
						dmpCacheUtil.lpush("scheme_"+imei, schemeStr);
					}
				}else{
					log.info("根据IMEI从数据库查询到的针对该IMEI的方案schemePos为空");
				}
			}else{
				schemePos=new ArrayList<LaoDMPStrategyEditPo>();
				for(String scheme:schemes){
					LaoDMPStrategyEditPo schemePo=gson.fromJson(scheme, LaoDMPStrategyEditPo.class);
					schemePos.add(schemePo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("走出接口getStrategies");
		return schemePos;
	}
    /**
     * 获取custid下所有的策略list
     * @param positionData
     * @param custId 
     * @return
     */
	public List<LaoDMPStrategyEditPo> getStrategies(PositionData positionData, String custId) {
		log.info("进入方法 getStrategies");
		log.info("positionData="+positionData.toString());
		log.info("custId="+custId);
		List<LaoDMPStrategyEditPo> schemePos=null;
		Gson gson = new Gson();
		try {
			List<String> schemes = dmpCacheUtil.hvals("schemes_"+custId);
			log.info("根据custId从redis中获得的策略字符串list为schemes="+schemes);
			//如果redis中没有用户编辑的策略，从数据库中取出，并放入redis中
			if(schemes==null || schemes.size()<=0){
				log.info("根据custid从redis中获取到用户编辑的策略为空，下面去数据库中去查，如果查到，放入redis中");
				schemePos = editPoExtMapper.selectByCustId(Long.parseLong(custId));
				if(schemePos!=null&&schemePos.size()>0){
					for(LaoDMPStrategyEditPo schemePo:schemePos){
						String schemeStr = gson.toJson(schemePo);
						dmpCacheUtil.hset("schemes_"+custId, String.valueOf(schemePo.getId()), schemeStr);
					}
				}
			}else{
				log.info("从redis中获取到了用户下的所有编辑的策略");
				schemePos=new ArrayList<LaoDMPStrategyEditPo>();
				for(String scheme:schemes){
					LaoDMPStrategyEditPo schemePo=gson.fromJson(scheme, LaoDMPStrategyEditPo.class);
					schemePos.add(schemePo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return schemePos;
	}
	/**
	 * 从用户下的编辑方案中获取针对所有设备的公共策略
	 * @param dmpStrategyEditPos
	 * @return
	 */
	public List<LaoDMPStrategyPo> getCommonScheme(List<LaoDMPStrategyEditPo> dmpStrategyEditPos,Gson gson) {
		log.info("进入方法getCommonScheme");
		log.info("dmpStrategyEditPos="+dmpStrategyEditPos);
		List<LaoDMPStrategyPo> strategies=new ArrayList<LaoDMPStrategyPo>();
		for(LaoDMPStrategyEditPo editPo:dmpStrategyEditPos){
			String targitType = editPo.getTargittype();
			log.info("targitType="+targitType);
			Long schemeId = editPo.getId();
			log.info("schemeId="+schemeId);
			if("1".equals(targitType)){//“1” 公共策略，针对所有设备 ；“2” 针对指定组中的设备
				try {
					List<String> strategiesStr=dmpCacheUtil.hvals("strategiesofscheme_"+schemeId);
					if(strategiesStr!=null&&strategiesStr.size()>0){
						log.info("根据schemeId从redis中获取的执行策略list不为空");
						getStrategiesFromRedis(strategiesStr,strategies,gson);
					}else{
						log.info("根据schemeId从redis中获取的执行策略集合为空");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return strategies;
	}
	private void getStrategiesFromDB(Long schemeId,List<LaoDMPStrategyPo> strategies, Gson gson) throws Exception {
		List<LaoDMPStrategyPo> strategyPoes = dmpSchemeStrategyPoExtMapper.selectBySchemeId(schemeId);
		log.info("从数据库查询的策略为strategyPoes="+strategyPoes);
		if(strategyPoes!=null&&strategyPoes.size()>0){
			log.info("从数据库查询的策略不为空");
			strategies.addAll(strategyPoes);
			for(LaoDMPStrategyPo strategyPo:strategyPoes){
				String strategyPoStr = gson.toJson(strategyPo);
				dmpCacheUtil.hset("strategiesofscheme_"+schemeId, String.valueOf(strategyPo.getId()), strategyPoStr);
			}
		}
	}
	/**
	 * 把redis中获取到的执行策略字符串转化为策略对象，用list接收
	 * @param strategiesStr
	 * @param strategies
	 * @param gson
	 */
	private void getStrategiesFromRedis(List<String> strategiesStr,List<LaoDMPStrategyPo> strategies, Gson gson) {
		for(String strategyStr:strategiesStr){
			LaoDMPStrategyPo dmpStrategyPo=gson.fromJson(strategyStr, LaoDMPStrategyPo.class);
			strategies.add(dmpStrategyPo);
		}
		
	}
	
	/**
	 * 当POS机移动到湖北省外
	 * 
	 * @param address
	 * @return
	 */
	public boolean positionUtil(String nowPositon, String area) {
		log.info("进入方法positionUtil");
		log.info("nowPositon="+nowPositon);
		log.info("area="+area);
		int index = nowPositon.indexOf(area);
		if (index == -1)
			return true;
		return false;
	}
	/**
	 * 当POS机两次使用位置间距大于500米
	 * 
	 * @param referPositionData
	 * @param nowPositionData
	 * @param distance  单位m
	 * @return
	 */
	public boolean checkDistance(PositionData referPositionData,PositionData nowPositionData, int distance) {
		int res = getDistance(referPositionData.getLatitude(),
				referPositionData.getLongtitude(), nowPositionData.getLatitude(),
				nowPositionData.getLongtitude());
		log.info("res="+res);
		log.info("distance="+distance);
		log.info("从redis和传入信息中取出两次登录的经纬度");
		if (Math.abs(res) >=distance) {
			log.info("判断POS机两次使用位置间距大于:"+distance);
			return true;
		}
		return false;
	}
	
	/**
	 * 计算出当前位置和上次登录位置的位置间距 返回值单位是米
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return单位m
	 */
	public static int getDistance(Double lat1, Double lng1, Double lat2,
			Double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double difference = radLat1 - radLat2;
		double mdifference = rad(lng1) - rad(lng2);
		double distance = 2 * Math.asin(Math.sqrt(Math.pow(
				Math.sin(difference / 2), 2)
				+ Math.cos(radLat1)
				* Math.cos(radLat2)
				* Math.pow(Math.sin(mdifference / 2), 2)));
		distance = distance * EARTH_RADIUS;
		distance = Math.round(distance * 1000);
		String distanceStr = distance + "";
		distanceStr = distanceStr.substring(0, distanceStr.indexOf("."));

		return Integer.parseInt(distanceStr);
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}
	public static void main(String[] args) {
		//pointStrList.add("116.399@39.910");
		// pointStrList.add("116.404@39.915");
		// pointStrList.add("116.405@39.920");
		// pointStrList.add("116.425@39.900");
		int result=getDistance(39.910,116.399,39.915,116.404);
		System.out.println(result);
	}
	public void savaDMPLog(LaoDMPLogPo dmpLogPo) {
		log.info("进入savaDMPLog");
		int result = 0;
		try {
			result = dmpLogPoMapper.insertSelective(dmpLogPo);
		} catch (Exception e) {
			log.info("日志插入失败");
		}
		if(result>0)
			log.info("插入日志成功");
		else
			log.info("插入日志失败");
		log.info("走出savaDMPLog");
	}
	public List<LaoDMPStrategyPo> getSpecialScheme(List<LaoDMPStrategyEditPo> dmpStrategyEditPos) {
		List<LaoDMPStrategyPo> strategies=new ArrayList<LaoDMPStrategyPo>();
		Gson gson=new Gson();
		for(LaoDMPStrategyEditPo editPo:dmpStrategyEditPos){
			Long schemeId = editPo.getId();
			try {
				List<String> strategiesStr=dmpCacheUtil.hvals("strategiesofscheme_"+schemeId);
				if(strategiesStr!=null&&strategiesStr.size()>0){
					log.info("根据schemeId从redis中获取的执行策略list不为空");
					getStrategiesFromRedis(strategiesStr,strategies,gson);
				}else{
					log.info("根据schemeId从redis中获取的执行策略list为空，下面要从数据库中查询方案下面的执行策略");
					getStrategiesFromDB(schemeId,strategies,gson);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return strategies;
	}
	/**
	 * 校验redis和数据库中数据是否正确
	 * @param custId
	 * @return
	 */
//	public void checkStrategyRelation(String custId,Gson gson) {
//		log.info("进入方法 checkStrategyRelation");
//		log.info("custId="+custId);
//		try {
//			List<String> relations = dmpCacheUtil.lrange("relation_"+custId,0,-1);
//			log.info("根据custId从redis中获得的策略关系集合relations="+relations);
//			//如果redis中没有用户编辑的策略，从数据库中取出，并放入redis中
//			if(relations==null || relations.size()<=0){
//				log.info("根据custid从redis中获取的策略关系集合为空，下面去数据库中去查，如果查到，放入redis中");
//				List<LaoDMPStrategyRelationPo> relationsDB = dmpStrategyRelationPoExtMapper.selectByCustId(Long.parseLong(custId));
//				if(relationsDB!=null&&relationsDB.size()>0){
//					log.info("从数据库中查询到的策略关系集合不为空，下面把策略关机集合放入redis中");
//					for(LaoDMPStrategyRelationPo relationPo:relationsDB){
//						String strategyRelationStr = gson.toJson(relationPo);
//						dmpCacheUtil.lpush("relation_"+custId, strategyRelationStr);
//						log.info("把策略关系放入redis中后，把初始化应用策略，执行策略，操作");
//						checkScheme(relationPo,gson);
//					}
//				}
//			}else{
//				for(String relationStr:relations){
//					LaoDMPStrategyRelationPo relationPo=gson.fromJson(relationStr, LaoDMPStrategyRelationPo.class);
//					checkScheme(relationPo,gson);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		log.info("走出方法 checkStrategyRelation");
//	}
	/**
	 * 校验redis和数据库中数据是否正确
	 * @param custId
	 * @return
	 */
	public void checkStrategyRelation(String custId,Gson gson) {
		log.info("进入方法 checkStrategyRelation");
		log.info("custId="+custId);
		try {
			checkScheme(Long.parseLong(custId),gson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("走出方法 checkStrategyRelation");
	}
	private void checkScheme(Long custId, Gson gson) throws Exception {
		log.info("进入方法checkScheme");
		log.info("custId="+custId);
		Set<String> schemeStrs = dmpCacheUtil.hkeys("schemes_"+custId);
		log.info("schemeStrs="+schemeStrs);
		if(schemeStrs==null||schemeStrs.size()<=0){
			List<LaoDMPStrategyEditPo> schemePos = editPoExtMapper.selectByCustId(custId);
			if(schemePos!=null&&schemePos.size()>0){
				for(LaoDMPStrategyEditPo scheme:schemePos){
					dmpCacheUtil.hset("schemes_"+custId, String.valueOf(scheme.getId()), gson.toJson(scheme));
					checkStrategy(scheme.getId(),gson);
				}
			}
		}else{
			for(String schemeIdStr:schemeStrs){
				checkStrategy(Long.parseLong(schemeIdStr),gson);
			}
		}
		log.info("走出方法checkScheme");
		
	}
	private void checkStrategy(Long schemeId, Gson gson) throws Exception {
		log.info("进入方法checkStrategy");
		log.info("schemeId="+schemeId);
		 Set<String> strategyIds = dmpCacheUtil.hkeys("strategiesofscheme_"+schemeId);
		log.info("strategyIds="+strategyIds);
		if(strategyIds==null||strategyIds.size()<=0){
			List<LaoDMPStrategyPo> strategyPos = strategyPoExtMapper.getStrategis(schemeId);
			if(strategyPos!=null&&strategyPos.size()>0){
				for(LaoDMPStrategyPo strategy:strategyPos){
					dmpCacheUtil.hset("strategiesofscheme_"+schemeId, String.valueOf(strategy.getId()), gson.toJson(strategy));
					checkOperation(schemeId,strategy.getId(), gson);
				}
			}else{
				for(String strategyId:strategyIds){
					checkOperation(schemeId,Long.parseLong(strategyId), gson);
				}
			}
		}
		log.info("走出方法checkStrategy");
	}
	private void checkOperation(Long schemeId, Long strategyId, Gson gson) throws Exception {
		log.info("进入方法checkOperation");
		log.info("schemeId="+schemeId);
		log.info("strategyId="+strategyId);
		Set<String> strategyIds_operationIds = dmpCacheUtil.hkeys("operationsofscheme_"+schemeId);
		log.info("strategyIds_operationIds="+strategyIds_operationIds);
		if(strategyIds_operationIds==null||strategyIds_operationIds.size()<=0){
			List<LaoDMPOperationPo> operations = operationExtMapper.getOperationsBystategy(schemeId,strategyId);
			if(operations!=null&&operations.size()>0){
				for(LaoDMPOperationPo operationPo:operations){
					dmpCacheUtil.hset("operationsofscheme_"+schemeId, strategyId+"_"+operationPo.getId(), gson.toJson(operationPo));
				}
			}
		}
		log.info("走出方法checkOperation");
	}
	/**
	 * 校验redis中的用户策略，redis中没有就去数据库中查询
	 * @param relationPo
	 * @param gson
	 * @throws Exception
	 */
	private void checkScheme(LaoDMPStrategyRelationPo relationPo, Gson gson) throws Exception {
		log.info("进入方法checkScheme");
		log.info("relationPo="+relationPo);
		Long custId = relationPo.getCustId();
		Long schemeId = relationPo.getSchemeId();
		String schemeStr = dmpCacheUtil.hget("schemes_"+custId,  String.valueOf(schemeId));
		log.info("schemeStr="+schemeStr);
		if(StringUtils.isEmpty(schemeStr)){
			log.info("从redis中获取的用户策略为空，下面根据shemeId从数据库中去查询");
			LaoDMPStrategyEditPo scheme = editPoExtMapper.selectByPrimaryKey(schemeId);
			log.info("scheme="+scheme);
			if(scheme!=null){
				log.info("把用户策略放进redis");
				dmpCacheUtil.hset("schemes_"+custId,  String.valueOf(schemeId), gson.toJson(scheme));
				checkStrategy(relationPo,gson);
			}else{
				log.info("根据shemeId从数据库中没有查到用户策略，校验停止");
			}
		}else{
			checkStrategy(relationPo,gson);
		}
		log.info("走出方法checkScheme");
	}
	/**
	 * 校验执行策略，redis中没有就去数据库中查询
	 * @param relationPo
	 * @param gson
	 * @throws Exception
	 */
	private void checkStrategy(LaoDMPStrategyRelationPo relationPo, Gson gson) throws Exception {
		log.info("进入方法checkStrategy");
		log.info("relationPo="+relationPo);
		Long schemeId = relationPo.getSchemeId();
		Long strategyId = relationPo.getStrategyId();
		String strategyStr = dmpCacheUtil.hget("strategiesofscheme_"+schemeId, String.valueOf(strategyId));
		log.info("strategyStr="+strategyStr);
		if(StringUtils.isEmpty(strategyStr)){
			log.info("从redis中获取的执行策略为空，下面根据strategyId从数据库中去查询");
			LaoDMPStrategyPo strategyPo=strategyPoExtMapper.selectById(strategyId);
			if(strategyPo!=null){
				log.info("把执行策略放进redis");
				dmpCacheUtil.hset("strategiesofscheme_"+schemeId, String.valueOf(strategyId), gson.toJson(strategyPo));
			}else{
				log.info("根据执行策略主键没有查到执行策略");
			}
		}else{
			checkOperation(relationPo,gson);
		}
		log.info("走出方法checkStrategy");
	}
	/**
	 * 校验操作，redis中没有就去数据库中查询
	 * @param relationPo
	 * @param gson
	 * @throws Exception
	 */
	private void checkOperation(LaoDMPStrategyRelationPo relationPo, Gson gson) throws Exception {
		log.info("进入方法checkOperation");
		log.info("relationPo="+relationPo);
		Long schemeId = relationPo.getSchemeId();
		Long strategyId = relationPo.getStrategyId();
		Long operationId = relationPo.getOperationId();
		if(operationId==null){
			log.info("用户策略schemeId没有操作");
			return;
		}
		String operationStr=dmpCacheUtil.hget("operationsofscheme_"+schemeId+"_"+strategyId, String.valueOf(operationId));
		log.info("operationStr="+operationStr);
		if(StringUtils.isEmpty(operationStr)){
			log.info("从redis中获取的操作为空，下面根据operationId从数据库中去查询");
			LaoDMPOperationPo operatonPo=operationExtMapper.selectById(operationId);
			if(operatonPo!=null){
				log.info("把操作放进redis");
				dmpCacheUtil.hset("operationsofscheme_"+schemeId+"_"+strategyId, String.valueOf(operationId), gson.toJson(operatonPo));
			}else{
				log.info("应用策略中没有操作");
			}
		}
		log.info("走出方法checkOperation");
	}
	/**
	 * 获取策略中的约束实现类
	 * @param imei
	 * @param scheme
	 * @param gson
	 * @throws Exception
	 */
	public void schemeExecute(String imei, LaoDMPStrategyEditPo scheme,Gson gson) throws Exception {
		log.info("进入方法schemeExecute");
		log.info("scheme="+scheme);
		Long custId = scheme.getChannelcustId();
		Long schemeId = scheme.getId();
		log.info("schemeId="+schemeId);
		List<String> strategiesStr=dmpCacheUtil.hvals("strategiesofscheme_"+schemeId);
		if(strategiesStr!=null&&strategiesStr.size()>0){
			log.info("根据schemeId从redis中获取的执行策略list不为空");
			for(String strategyStr:strategiesStr){
				LaoDMPStrategyPo dmpStrategyPo=gson.fromJson(strategyStr, LaoDMPStrategyPo.class);
				LaoDMPStrategyDto strategyDto=(LaoDMPStrategyDto) ConversionUtil.po2dto(dmpStrategyPo, LaoDMPStrategyDto.class);
				DMPStrategyService strategyService=SpringContextUtils.getBean(dmpStrategyPo.getStrategyimpl());
				strategyService.StrategyExecute(strategyDto,imei,String.valueOf(custId),schemeId,null);
			}
		}else{
			log.info("根据schemeId从redis中获取的执行策略集合为空");
		}
		log.info("走出方法schemeExecute");
	}
	
}
