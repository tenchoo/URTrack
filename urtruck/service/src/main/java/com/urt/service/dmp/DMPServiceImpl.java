package com.urt.service.dmp;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.gson.Gson;
import com.urt.cache.DMPCacheUtil;
import com.urt.common.util.ConversionUtil;
import com.urt.dto.dmp.LaoDMPCardDto;
import com.urt.interfaces.dmp.DMPService;
import com.urt.mapper.LaoDMPCardPoMapper;
import com.urt.mapper.LaoDMPPositionPoMapper;
import com.urt.mapper.ext.LaoDMPCardGroupPoExtMapper;
import com.urt.mapper.ext.LaoDMPCardPoExtMapper;
import com.urt.mapper.ext.LaoDMPDeviceAttributePoExtMapper;
import com.urt.mapper.ext.LaoDMPPositionPoExtMapper;
import com.urt.mapper.ext.LaoDMPSchemeGroupPoExtMapper;
import com.urt.mapper.ext.LaoDMPStrategyEditPoExtMapper;
import com.urt.mapper.ext.LaoDeviceRelExtMapper;
import com.urt.miniService.MiniDeviceInfoServiceImpl;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoDMPCardPo;
import com.urt.po.LaoDMPDeviceAttributePo;
import com.urt.po.LaoDMPPositionPo;
import com.urt.po.LaoDMPStrategyEditPo;
import com.urt.po.LaoDMPStrategyPo;
import com.urt.service.dmp.baiduutil.BaiduUtil;
import com.urt.service.dmp.entity.CardData;
import com.urt.service.dmp.entity.DeviceData;
import com.urt.service.dmp.entity.PositionData;
import com.urt.service.dmp.entity.PostData;
import com.urt.service.dmp.mqttutil.MQTTClint;
import com.urt.service.dmp.mqttutil.MqttConfig;
import com.urt.service.dmp.mqttutil.PublishManager;
import com.urt.service.dmp.util.DMPStrategesUtil;
import com.urt.utils.SeqID;
import com.urt.utils.SpringContextUtils;
import com.urt.utils.ZkGenerateSeq;

@Service("dmpService")
@Transactional(propagation = Propagation.REQUIRED)
public class DMPServiceImpl implements DMPService {
	
	Logger log = Logger.getLogger(DMPServiceImpl.class);
	@Autowired
	private LaoDMPCardGroupPoExtMapper dmpCardGroupDao;
	@Autowired
	private DMPCacheUtil dmpCacheUtil;
	@Autowired
	private DMPStrategesUtil strategesUtil;
	@Autowired
	private LaoDMPCardPoExtMapper cardExtMapper;
	@Autowired
	private LaoDMPCardPoMapper cardMapper;
	@Autowired
	LaoDMPDeviceAttributePoExtMapper attrExtMapper;
	@Autowired
	LaoDMPPositionPoMapper positionMapper;
	@Autowired
	private MiniDeviceInfoServiceImpl miniDeviceInfoServiceImpl;
	@Autowired
	LaoDMPPositionPoExtMapper positionExtMapper;
	@Autowired
	private LaoDMPStrategyEditPoExtMapper schemeExtMapper; 
	@Autowired
	private LaoDMPSchemeGroupPoExtMapper schemeGroupExtMapper;
	@Autowired
	private LaoDeviceRelExtMapper deviceRelExtMapper;
	@Override
	public boolean dmpDataHandler(String posDataText, byte deviceType,
			String randomText, String custId) {
		log.info("进入接口----dmpDataHandler----");
		log.info("解密后的设备字符串---：" + posDataText);
		Gson gson = new Gson();
		PostData postData = gson.fromJson(posDataText, PostData.class);
		log.info("设备信息对象postData---：" + postData);

		int triggedReson = postData.getTriggedReason();
		log.info("消息发送的事件类型triggedReson---：" + triggedReson);

		CardData cardData = postData.getCardData();
		log.info("卡信息对象cardData---：" + cardData);
		DeviceData deviceData = postData.getDeviceData();
		log.info("设备数据对象deviceData---：" + deviceData);
		PositionData positionData = postData.getPositionData();
		log.info("位置信息的对象数据positionData---：" + positionData);
		String imei = null;
		// 处理消息唯一标识
		try {
			// value为imei 判断这条信息是否处理过
			String value = dmpCacheUtil.getValue(randomText);
			log.info("根据消息的唯一标示获得的设备imei=======：" + value);
			if (StringUtils.isNotEmpty(value))
				return false;
			imei = cardData.getImei();
			log.info("从卡数据中获得的imei---：" + imei);
			if (StringUtils.isEmpty(imei))
				return false;
			// 1、把消息的唯一标识存入redis中 超时时间为600s
			dmpCacheUtil.setex(randomText, 600, imei);
			log.info("把唯一标示和imei放入redis中，放入成功");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		boolean result=false;
		try {
			// 处理位置信息
			if (positionData != null) {
				addAddress(positionData);
				
				positionHandle(gson, positionData, imei);
				Long cardId = cardExtMapper.selectCardIdByImei(cardData.getImei());
				if (cardId == null)
					insertAndUpdateCardData(deviceData, cardData, deviceType,triggedReson, cardId, custId);

			}
			// 把终端的位置信息json字符串存入redis的list集合中，以position_imei为key
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        //用IMSI去数据库查询iccid
		String imsi = cardData.getImsi();
		log.info("imsi="+imsi);
		if(StringUtils.isNotEmpty(imsi.trim())){
			String iccid=deviceRelExtMapper.getIccidByImsi(imsi);
			log.info("iccid="+iccid);
			if(iccid!=null&&"".equals(iccid.trim()))
				cardData.setIccid(iccid);
		}
		
		if (triggedReson == 0 || triggedReson == 1) {// 0定时周期性发送事件 ，1插卡事件
			log.info("终端设备周期性时间和插卡时间触发消息发送，处理消息");
			if(cycleAndInsertEvent(gson, cardData, deviceData, deviceType,imei, triggedReson, custId))
				result=true;
		} else if (triggedReson == 2) {// 2拔卡发送信息事件
			log.info("终端设备拔卡事件触发消息发送，处理消息");
			if(invalidDataHandle(imei, (short) triggedReson))
				result=true;
		} else if (triggedReson == 3) {// 3位置变化发送信息事件
			log.info("终端设备位置变化事件触发消息发送，处理消息");
			result=true;
		} else {// 异常信息
			log.info("未识别是什么时间触发了信息发送");
//			return false;
		}
		strategisHandler(custId, cardData,positionData,deviceData);
		return result;
	}
	/**
	 * 策略处理
	 * @param custId
	 * @param cardData
	 * @param positionData
	 * @param deviceData
	 */
	private void strategisHandler(String custId, CardData cardData, PositionData positionData, DeviceData deviceData) {
		log.info("进入策略处理方法--strategisHandler");
		log.info("custId="+custId);
		log.info("cardData="+cardData);
		log.info("positionData="+positionData);
		log.info("deviceData="+deviceData);
		Gson gson=new Gson();
		strategesUtil.checkStrategyRelation(custId,gson);
		strategisOnCustIdHandler(custId, cardData, positionData,gson);
		StrategisOnImeiHandler(custId, cardData, positionData,gson);
		log.info("走出策略处理方法--strategisHandler");
	}
	/**
	 * 根据imei获取用户下所有的针对指定组的策略，并处理
	 * @param custId
	 * @param cardData
	 * @param positionData
	 * @param gson 
	 */
	private void StrategisOnImeiHandler(String custId, CardData cardData,PositionData positionData, Gson gson) {
		log.info("进入接口StrategisOnImeiHandler");
		log.info("custId="+custId);
		log.info("cardData="+cardData);
		log.info("positionData="+positionData);
		String imei = cardData.getImei();
		log.info("imei="+imei);
		//根据IMEI获得IMEI所对应的组id
		List<LaoDMPStrategyEditPo> dmpStrategyEditPos=null;
		try {
			List<Long> groupIds=dmpCardGroupDao.getGroupIdsByImei(imei);
			log.info("根据IMEI从数据库查询IMEI所在的组的 groupIds"+groupIds);
			if(groupIds!=null&&groupIds.size()>0){
				log.info("根据IMEI从数据库查询IMEI所在的组的groupIds不为空");
				for(Long groupId:groupIds){
					log.info("根据组id从redis中获取groupid下的所有方案schemeIds为空,下面从数据库中根据groupId获取");
					dmpStrategyEditPos=schemeExtMapper.getSchemesByGroupId(groupId);
					log.info("dmpStrategyEditPos="+dmpStrategyEditPos);
					if(dmpStrategyEditPos!=null&&dmpStrategyEditPos.size()>0){
						log.info("从数据库中根据groupId获取组下的所有方案方案不为空");
						for(LaoDMPStrategyEditPo scheme:dmpStrategyEditPos){
							log.info("下面要判断该设备是否违反了针对它的方案");
							log.info("scheme="+scheme);
							dmpCacheUtil.hset("schemeIdsofgroup_"+groupId, String.valueOf(scheme.getId()),gson.toJson(scheme));
							Long channelcustId=scheme.getChannelcustId();
							if(channelcustId==null){
								scheme.setChannelcustId(Long.parseLong(custId));
							}
							strategesUtil.schemeExecute(cardData.getImei(),scheme,gson);
						}
					}else{
						log.info("从数据库中根据groupId获取组下的所有方案方案为空");
					}
					log.info("走出接口StrategisOnImeiHandler");
				}
			}else{
				log.info("根据IMEI从数据库查询IMEI所在的组的groupIds为空,所以该IMEI身上没有任何非公共的方案");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 根据custid获取用户下所有的公共策略，并处理
	 * @param custId
	 * @param cardData
	 * @param positionData
	 * @param gson 
	 */
	private void strategisOnCustIdHandler(String custId, CardData cardData,PositionData positionData, Gson gson) {
		log.info("进入方法--strategisOnCustIdHandler，根据custid获取用户下所有的公共策略，并处理");
		log.info("custId="+custId);
		log.info("cardData="+cardData);
		log.info("positionData="+positionData);
		try {
			List<String> schemes = dmpCacheUtil.hvals("schemes_"+custId);
			log.info("schemes="+schemes);
			if(schemes==null||schemes.size()<=0){
				log.info("用户"+custId+"名下没有策略");
				return;
			}
			for(String schemeStr:schemes){
				LaoDMPStrategyEditPo scheme = gson.fromJson(schemeStr, LaoDMPStrategyEditPo.class);
				if("1".equals(scheme.getTargittype())){
					strategesUtil.schemeExecute(cardData.getImei(),scheme,gson);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("走出方法--strategisOnCustIdHandler，根据custid获取用户下所有的公共策略，并处理");
	}
    /**
     * 初始化位置信息中的位置描述
     * @param positionData
     */
	private void addAddress(PositionData positionData) {
		double lat=positionData.getLatitude();
		double lng = positionData.getLongtitude();
		String address = BaiduUtil.getAddress(String.valueOf(lat), String.valueOf(lng));
		positionData.setAddress(address);
	}

	/**
	 * 如果设备移出湖北省，给客户通知
	 * 
	 * @param imei
	 * @return
	 */
//	private String httpSendPost(String imei) {
//		HttpClientUtil httpClient = HttpClientUtil.getInstance();
//		JSONObject jsonPost = new JSONObject();
//		jsonPost.put("CODE", 10001);
//		jsonPost.put("IMEI", imei);
//		jsonPost.put("COMMENT", "该设备工作区域移动到了湖北省外");
//		String resp = httpClient.sendHttpPostOfJson(
//				TOHUBEIYINLIANDEVICEPOSITIONINFOWARN, jsonPost.toString());
//		return resp;
//	}

	

	private void positionHandle(Gson gson, PositionData positionData,
			String imei) throws Exception {
		String positionStr = gson.toJson(positionData);
		log.info("位置信息的字符串positionStr---：" + positionStr);
		// 从redis中获取当前位置数据
		String nowPositon = dmpCacheUtil.getValue("nowPosition_" + imei);
		log.info("从redis中获取上次更新的位置信息nowPositon---：" + nowPositon);
		if (nowPositon != null && !"".equals(nowPositon)) {
			if (!nowPositon.equals(positionStr)) {
				log.info("redis中存储的当前位置和消息中发过类的当前位置不同，位置发生改变，更新系统中的位置信息");
				updatePositionInfo(positionData, imei, positionStr);
			}
		} else {
			log.info("redis中没有终端设备的当前位置信息，更新系统中的位置信息");
			updatePositionInfo(positionData, imei, positionStr);
		}
	}



	// 拔卡时间发送消息处理
	private boolean invalidDataHandle(String imei, short triggedReson) {
		try {
			// dmpCacheUtil.del("nowPosition_"+imei);
			// dmpCacheUtil.del("position_"+imei);
			// dmpCacheUtil.del("cardStr_"+imei);
			// log.info("终端卡被拔出，删除redis中关于imei的位置信息和卡信息，删除成功");
			log.info("终端卡被拔出，把数据库中的卡记录修改为无效状态，flag=1");
			cardExtMapper.updateFlag(imei, triggedReson);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 处理终端周期性和插卡事件发送的消息
	 * 
	 * @param positionData
	 * @param cardData
	 * @param deviceData
	 * @param deviceType
	 * @param imei
	 * @param triggedReson
	 * @return
	 */
	private boolean cycleAndInsertEvent(Gson gson, CardData cardData,
			DeviceData deviceData, byte deviceType, String imei,
			int triggedReson, String custId) {
		try {
			String cardStr = gson.toJson(cardData);
			log.info("卡信息的字符串cardStr---：" + cardStr);

			// 从redis中获取当前卡数据
			String cartDataStr = dmpCacheUtil.getValue("cardStr_" + imei);
			log.info("从redis中获取卡信息cartDataStr---：" + cartDataStr);

			if (cartDataStr != null && !"".equals(cartDataStr)) {
				if (cartDataStr.equals(cardStr)) {
					log.info("从redis中取出的卡信息和终端发送过来的卡信息相同，系统中的卡信息不更新");
					return true;
				}
			}
			log.info("更新系统中的卡信息");
			dmpCacheUtil.set("cardStr_" + imei, cardStr);
			Long cardId = cardExtMapper.selectCardIdByImei(cardData.getImei());
			insertAndUpdateCardData(deviceData, cardData, deviceType,
					triggedReson, cardId, custId);
			// 把终端的位置信息json字符串存入redis的list集合中，以position_imei为key
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		log.info("走出接口----cycleAndInsertEvent----");
		return true;
	}

	private void insertDeviceAtrribute(DeviceData deviceData, String cardDataId) {
		List<LaoDMPDeviceAttributePo> attrList = new ArrayList<LaoDMPDeviceAttributePo>();
		try {
			Field[] fields = DeviceData.class.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				String attrName = field.getName();
				String attrValue = String.valueOf(field.get(deviceData));
				log.info("向表lao_dmpdevice_attribute中插入数据   属性名attrName---："
						+ attrName + "----属性值attrValue--：" + attrValue);
				if (!"serialVersionUID".equals(attrName)) {
					LaoDMPDeviceAttributePo deviceAttr = new LaoDMPDeviceAttributePo();
					deviceAttr.setCarddataid(cardDataId);
					deviceAttr.setAttributename(attrName);
					deviceAttr.setAttributevalue(attrValue);
					attrList.add(deviceAttr);
				}
			}
			log.info("表lao_dmpdevice_attribute批量插入对象的集合长度---："
					+ attrList.size());
			if (attrList.size() > 0) {
				attrExtMapper.insertDeviceAtrribute(attrList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 位置信息放入redis和oracle
	/**
	 * "position_"+imei存放位置信息的list的key "nowPosition_"+imei 存放当前位置的key
	 * 
	 * @param positionData
	 * @param imei
	 * @param positionStr
	 * @throws Exception
	 */
	private void updatePositionInfo(PositionData positionData, String imei,
			String positionStr) throws Exception {
		// 如果当前位置信息改变，更新redis中的位置信息，并把位置信息放入list中
		dmpCacheUtil.set("nowPosition_" + imei, positionStr);
		//第一次的定位作为该设备的位移参照点
		String referPosition = dmpCacheUtil.getValue("referPosition_" + imei);
		log.info("referPosition="+referPosition);
		if(StringUtils.isEmpty(referPosition)){
			dmpCacheUtil.set("referPosition_" + imei, positionStr);
		}
		Long dolng = dmpCacheUtil.lpush("position_" + imei, positionStr);
		log.info("把终端的位置信息json字符串存入redis的list集合中，以position_imei为key，返回结果dolng"
				+ dolng);
		boolean positionResult = insertPositionInfoToDB(positionData, imei);
		if (positionResult) {
			log.error("把位置信息插入数据库成功");
		} else {
			log.error("把位置信息插入数据库失败");
		}
	}

	/**
	 * 插入新的卡数据
	 * 
	 * @param deviceData
	 * @param cardData
	 * @param deviceType
	 * @param triggedReson
	 * @param cardId
	 * @param custId
	 * @return
	 */
	private void insertAndUpdateCardData(DeviceData deviceData,
			CardData cardData, byte deviceType, int triggedReson, Long cardId,
			String custId) {
		try {
			// 初始化设备数据信息
			LaoDMPCardPo cardPo = new LaoDMPCardPo();
			cardPo.setIccid(cardData.getIccid());
			cardPo.setImei(cardData.getImei());
			cardPo.setImsi(cardData.getImsi());
			cardPo.setDevicetype((int) deviceType);
			cardPo.setPhonenumber(cardData.getPhoneNumber());
			cardPo.setNetworkoperatorname(cardData.getNetworkOperatorName());
			cardPo.setSimstate(cardData.getSimState());
			cardPo.setTriggedreason((short) triggedReson);
			cardPo.setCustid(Long.parseLong(custId));
			long start = cardData.getCardInsertTime();// 插卡时间
			long end = cardData.getCardAbsentTime();// 拔卡时间
			if (start > 0) {
				Date startTime = new Date(start);
				cardPo.setStarttime(startTime);
			} else {
				cardPo.setStarttime(new Date());
			}
			if (end > 0) {
				Date endTime = new Date(end);
				cardPo.setEndtime(endTime);
			}
			if (triggedReson == 0) {
				// 根据imei获取卡信息有效记录的id
				log.info("根据imei获取卡信息有效记录的id");
				if (cardId == null) {
					insertCardInfo(deviceData, cardPo);
				} else {
					cardPo.setId(cardId);
					cardPo.setUpdatetime(new Date());
					log.info("正常周期情况下，修改数据库卡数据");
					cardMapper.updateByPrimaryKeySelective(cardPo);

				}
			} else if (triggedReson == 3) {
				if (cardId == null)
					insertCardInfo(deviceData, cardPo);
			} else {
				// 修改原有卡信息flag为1，过时
				log.info("修改原有卡信息flag为1，过时");
				cardExtMapper.updateFlag(cardData.getImei(),
						(short) triggedReson);
				String cardDataId = ZkGenerateSeq.getIdSeq(SeqID.DEVICEDATA_ID);
				cardPo.setId(Long.parseLong(cardDataId));
				// 把设备数据信心对象插入数据库
				log.info("把设备数据信心对象插入数据库");
				cardMapper.insertSelective(cardPo);
				log.info("向表lao_DMPCard_Data中插入卡信息后返回的cardDataId---："
						+ cardDataId);
				insertDeviceAtrribute(deviceData, cardDataId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void insertCardInfo(DeviceData deviceData, LaoDMPCardPo cardPo) {
		String cardDataId = ZkGenerateSeq.getIdSeq(SeqID.DEVICEDATA_ID);
		Long cardId = Long.parseLong(cardDataId);
		cardPo.setId(cardId);
		// 把设备数据信心对象插入数据库
		log.info("把设备数据信心对象插入数据库");
		cardMapper.insertSelective(cardPo);
		log.info("向表lao_DMPCard_Data中插入卡信息后返回的cardDataId---：" + cardDataId);
		log.info("向表lao_dmpdevice_attribute中插入设备信息");
		insertDeviceAtrribute(deviceData, cardDataId);
	}

	// 把位置信息存入数据库
	private boolean insertPositionInfoToDB(PositionData positionData,
			String imei) {
		log.info("进入方法insertPositionInfoToDB");
		try {
			// 初始化位置信息对象
			LaoDMPPositionPo positionInfo = new LaoDMPPositionPo();
			// LaoPositionInfo positionInfo=new LaoPositionInfo();
			String positionId = ZkGenerateSeq.getIdSeq(SeqID.POSITIONINFO_ID);
			log.info("zookeeper生成的位置信息主键 positionId="+positionId);
			positionInfo.setId(Long.parseLong(positionId));
			positionInfo.setAddress(positionData.getAddress());
			positionInfo.setCid((long) positionData.getCid());
			positionInfo.setErrorinfo(positionData.getErrorInfo());
			positionInfo.setImei(imei);
			positionInfo.setLac((long) positionData.getLac());
			positionInfo
					.setLatitude(new BigDecimal(positionData.getLatitude()));
			positionInfo.setLongtitude(new BigDecimal(positionData
					.getLongtitude()));
			positionInfo.setMbasestationid(positionData.getmBaseStationId());
			positionInfo.setMcc(positionData.getMcc());
			positionInfo.setMnc(positionData.getMnc());
			positionInfo.setMnetworkid(positionData.getmNetworkId());
			positionInfo.setMsystemid(positionData.getmSystemId());
			positionInfo.setSpeed(new BigDecimal(positionData.getSpeed()));
			positionInfo.setRegisteredstate(positionData.getRegisteredState());
			// //把位置信息对象插入数据库
			positionMapper.insertSelective(positionInfo);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		log.info("走出方法insertPositionInfoToDB");
		return true;
	}

	@Override
	public Map<String, Object> queryPage(LaoDMPCardDto dto, int pageNo,
			int pageSize) {
		log.info("DMP业务进入接口----queryPage----");
		Map<String, Object> map = miniDeviceInfoServiceImpl.queryPage(dto,
				pageNo, pageSize);
		log.info("DMP业务走出接口----queryPage----");
		return map;
	}

	@Override
	public String getPositionPoint(String imei) {
		log.info("进入接口----getPositionPoint----");
		Gson gson = new Gson();
		PositionData positionData=null;
		// 从redis中获取当前位置数据
		String pointStr="";
		try {
			String nowPositon = dmpCacheUtil.getValue("nowPosition_" + imei);
			if (nowPositon == null && "".equals(nowPositon)) {
				LaoDMPPositionPo dmpPositionPo = positionExtMapper.selectLongitudeAndLatitude(imei);
				if(dmpPositionPo==null){
					return null;
				}
				positionData=new PositionData();
				positionData.setAddress(dmpPositionPo.getAddress());
				positionData.setLatitude(dmpPositionPo.getLatitude().doubleValue());
				positionData.setLongtitude(dmpPositionPo.getLongtitude().doubleValue());
				dmpCacheUtil.set("nowPosition_" + imei, gson.toJson(positionData));
			}else{
				log.info("当前位置信息字符串nowPositon--：" + nowPositon);
				positionData = gson.fromJson(nowPositon,PositionData.class);
			}
			if (positionData == null) {
				return null;
			}
			double longtitude = positionData.getLongtitude();
			double latitude = positionData.getLatitude();
			pointStr = longtitude + "@" + latitude;
			// pointStr=116.304988+"@"+40.060042;
			// pointStr=116.425+"@"+39.900;
			log.info("单点的经纬度point--：" + pointStr);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		log.info("走出接口----getPositionPoint----");
		return pointStr;
	}

	@Override
	public List<String> getPointTrail(String imei) {
		log.info("进入接口----getPointTrail----");
		Gson gson = new Gson();
		List<String> pointStrList = new ArrayList<String>();
		try {
			List<String> pointsStr = dmpCacheUtil.lrange("position_" + imei, 0,
					-1);
			log.info("单点轨迹数据pointsStr--：" + pointsStr);
			log.info("该终端设备轨迹图中的点个数--：" + pointsStr.size());
			if (pointsStr == null || pointsStr.size() <= 0)
				return null;
			Collections.reverse(pointsStr);
			for (String point : pointsStr) {
				PositionData positionData = gson.fromJson(point,
						PositionData.class);
				if (positionData == null)
					break;
				double longtitude = positionData.getLongtitude();
				double latitude = positionData.getLatitude();
				String pointStr = longtitude + "@" + latitude;
				// String pointStr = "116.304347@40.05945";
				log.info("单点的经纬度point--：" + pointStr);
				pointStrList.add(pointStr);
			}
			// String pointStr = "116.304347@40.05945";
			// log.info("单点的经纬度point--："+pointStr);
			// pointStrList.add(pointStr);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// pointStrList.add("116.399@39.910");
		// pointStrList.add("116.404@39.915");
		// pointStrList.add("116.405@39.920");
		// pointStrList.add("116.425@39.900");
		log.info("走出接口----getPointTrail----");
		return pointStrList;
	}

	@Override
	public List<String> getPositionPoints(String[] imeiArr) {
		log.info("进入接口----getPositionPoints----");
		log.info("需要显示的所有点的imei" + imeiArr);
		Gson gson = new Gson();
		List<String> points = new ArrayList<String>();
		// points.add("116.307852@40.057031");
		// points.add("116.313082@40.047674");
		// points.add("116.328749@40.026922");
		// points.add("116.347571@39.988698");
		// points.add("116.316163@39.997753");
		// points.add("116.345867@39.998333");
		// points.add("116.403472@39.999411");
		// points.add("116.307901@40.05901");
		// 从redis中获取当前位置数据
		for (String imei : imeiArr) {
			try {
				String nowPositon = dmpCacheUtil
						.getValue("nowPosition_" + imei);
				log.info("当前位置信息字符串nowPositon--：" + nowPositon);
				if (nowPositon == null && "".equals(nowPositon)) {
					continue;
				}
				PositionData positionData = gson.fromJson(nowPositon,
						PositionData.class);
				if (positionData == null) {
					continue;
				}
				double longtitude = positionData.getLongtitude();
				double latitude = positionData.getLatitude();
				String pointStr = longtitude + "@" + latitude;
				log.info("单点的经纬度point--：" + pointStr);
				points.add(pointStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("走出接口----getPositionPoints----");
		return points;
	}

	@Override
	public LaoDMPCardDto selectByIm(String imei) {
		LaoDMPCardDto dto = null;
		LaoDMPCardPo po = cardExtMapper.selectByImei(imei);
		if (po != null) {
			dto = (LaoDMPCardDto) ConversionUtil.po2dto(po, LaoDMPCardDto.class);
		}
		return dto;
	}

	public static void main(String[] args) throws IllegalArgumentException,
			IllegalAccessException {

	}

	@Override
	public int insertCard(List<LaoDMPCardDto> cardListDto) {
		List<LaoDMPCardPo> listDto = new ArrayList<LaoDMPCardPo>();
		if (cardListDto != null && cardListDto.size() > 0) {
			for (int i = 0; i < cardListDto.size(); i++) {
				LaoDMPCardDto dto = cardListDto.get(i);
				LaoDMPCardPo dtoNew = new LaoDMPCardPo();
				BeanMapper.copy(dto, dtoNew);
				listDto.add(dtoNew);
			}
		}
		return cardExtMapper.insertImei(listDto);
	}

	@Override
	public List<String> getImei(LaoDMPCardDto cardDto) {
		List<String> imeis = cardExtMapper
				.getImei((LaoDMPCardPo) ConversionUtil.dto2po(cardDto,
						LaoDMPCardPo.class));
		return imeis;
	}

	@Override
	public Map<String, Object> selectDeviceDetails(String imei) {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		LaoDMPCardDto cardDto = null;
		LaoDMPCardPo po = cardExtMapper.selectDeviceDetails(imei);
		Map<String, Object> resultmap = cardExtMapper.selectDeviceAttirbute(imei);
		Map<String, Object> map = new HashMap<>();
		map.put("resultmap", resultmap);
		if (po != null) {
			cardDto = (LaoDMPCardDto) ConversionUtil.po2dto(po,
					LaoDMPCardDto.class);
			Date createTime = cardDto.getCreatetime();
			Date updateTime=cardDto.getUpdatetime();
			if(updateTime!=null){
				String updateStr=format.format(updateTime);
				map.put("updateStr", updateStr);
			}
			String imputTime=format.format(createTime);
			map.put("imputTime", imputTime);
			map.put("cardDto", cardDto);
			String snmStateZh = getSimState(cardDto);
			map.put("snmStateZh", snmStateZh);
		}
		String groupNames = dmpCardGroupDao.selectGroupByImei(imei);
		if(groupNames!=null){
			map.put("ishavegroup",groupNames);
		}else{
			map.put("ishavegroup","无");
		}
		String commad = MqttConfig.COMMAND_PREFIX + MqttConfig.IS_ONLINE;
		PublishManager mqttManager = MQTTClint.publicCommand(imei, commad);
		boolean isok = mqttManager.handleResult();
		map.put("isonline", isok);
		return map;
	}

	private String getSimState(LaoDMPCardDto cardDto) {
		Integer deviceType = cardDto.getDevicetype();
		Integer simstate = cardDto.getSimstate();
		if(simstate==null)
			return "未知";
		String snmStateZh="";
		if(deviceType==0){//安卓
			switch(simstate){
			case 0:
				snmStateZh="未知";
				break;		
			case 1:
				snmStateZh="脱离设备";
				break;
			case 2:
				snmStateZh="被锁(需要用户的SIM PIN码解锁)";
				break;
			case 3:
				snmStateZh="被锁(需要用户的SIM PUK码解锁)";
				break;
			case 4:
				snmStateZh="被锁(需要网络PIN码解锁)";
				break;
			case 5:
				snmStateZh="就绪";
				break;
			case 6:
				snmStateZh="未就绪";
				break;
			case 7:
				snmStateZh="永久废弃";
				break;
			case 8:
				snmStateZh="卡异常";
				break;
			case 9:
				snmStateZh="卡受限";
				break;
			default:
				snmStateZh="未知";
			}
		}else if(deviceType==1){
			switch(simstate){//windows
			case 0:
				snmStateZh="未注册，也为搜寻新的运营商去注册";
				break;
			case 1:
				snmStateZh="已注册，国内网络";
				break;
			case 2:
				snmStateZh="未注册，正在寻找新的运营商去注册";
				break;
			case 3:
				snmStateZh="注册拒绝";
				break;
			case 4:
				snmStateZh="未知";
				break;
			case 5:
				snmStateZh="已注册，漫游状态";
				break;
			default:
				snmStateZh="不可用";
			}
		}else if(deviceType==10000){//未知设备
			snmStateZh="未知";
		}
		return snmStateZh;
	}
	@Override
	public Map<String, Object> selectIsoline(String imei) {
		String commad = MqttConfig.COMMAND_PREFIX + MqttConfig.IS_ONLINE;
		PublishManager mqttManager = MQTTClint.publicCommand(imei, commad);
		boolean isok = mqttManager.handleResult();
		Map<String, Object> map = new HashMap<String, Object>();
		if(isok==true){
			map.put("msg", "是");
		}else{
			map.put("msg", "否");
		}
		return map;
	}

}
