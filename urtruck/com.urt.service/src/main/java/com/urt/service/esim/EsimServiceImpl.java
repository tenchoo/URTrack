package com.urt.service.esim;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.urt.dto.LaoEsimGoodsDto;
import com.urt.dto.LaoEsimLogDto;
import com.urt.interfaces.esim.Es2Service;
import com.urt.interfaces.esim.EsimService2;
import com.urt.interfaces.esim.EsimUserService;
import com.urt.mapper.LaoEsimGoodsMapper;
import com.urt.mapper.LaoEsimIccidLibMapper;
import com.urt.mapper.LaoEsimImeiEidMapper;
import com.urt.mapper.LaoEsimLogMapper;
import com.urt.mapper.LaoEsimTradeMapper;
import com.urt.mapper.LaoEsimUserBundingMapper;
import com.urt.mapper.LaoEsimUserGivenMapper;
import com.urt.mapper.LaoEsimUserMapper;
import com.urt.mapper.ext.LaoEsimGoodsExtMapper;
import com.urt.mapper.ext.LaoEsimIccidLibExtMapper;
import com.urt.mapper.ext.LaoEsimImeiEidExtMapper;
import com.urt.mapper.ext.LaoEsimUserExtMapper;
import com.urt.mapper.ext.LaoEsimUserGoodsExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoEsimGoods;
import com.urt.po.LaoEsimIccidLib;
import com.urt.po.LaoEsimImeiEid;
import com.urt.po.LaoEsimLog;
import com.urt.po.LaoEsimUser;
import com.urt.po.LaoEsimUserBunding;
import com.urt.po.LaoEsimUserGiven;
import com.urt.po.LaoEsimUserGoods;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

@Service("esimService2")
public class EsimServiceImpl implements EsimService2 {
	
	private Logger log = Logger.getLogger(getClass());

	@Autowired
	private LaoEsimGoodsExtMapper laoEsimGoodsExtMapper;
	@Autowired
	private LaoEsimGoodsMapper laoEsimGoodsMapper;

	@Autowired
	private EsimUserService esimUserService;

	@Autowired
	private LaoEsimIccidLibExtMapper laoEsimIccidLibExtMapper;

	@Autowired
	private LaoEsimUserExtMapper laoEsimUserExtMapper;

	@Autowired
	private LaoEsimUserMapper laoEsimUserMapper;

	@Autowired
	private LaoEsimImeiEidExtMapper laoEsimImeiEidExtMapper;
	@Autowired
	private LaoEsimImeiEidMapper laoEsimImeiEidMapper;

	@Autowired
	private LaoEsimUserGoodsExtMapper userGoods;
	@Autowired
	private Es2Service es2Service;

	@Autowired
	private LaoEsimLogMapper esimLog;
	@Autowired
	private LaoEsimUserBundingMapper  userBunding;
	
	@Autowired
	private LaoEsimUserGivenMapper userGiven;
	
	
	
	@Autowired
	private LaoEsimUserExtMapper userExtMapper;
	

	@Override
	public boolean checkCard(String imei, String eid) {
		LaoEsimImeiEid queryByEidAndImei = laoEsimImeiEidExtMapper.queryByEidAndImei(imei, eid);
		if (null == queryByEidAndImei) {
			LaoEsimImeiEid queryByImei = laoEsimImeiEidExtMapper.queryByImei(imei);
			if (null != queryByImei && !eid.equals(queryByImei.getEid())) {

				return false;

			} else if (null == queryByImei) {
				LaoEsimImeiEid queryByEid = laoEsimImeiEidExtMapper.queryByEid(eid);
				if (null == queryByEid) {
					// imei号和eid 都没在绑定表中 把eid和imei号都插入
					LaoEsimImeiEid laoEsimImeiEid = new LaoEsimImeiEid();
					laoEsimImeiEid.setCreatedate(new Date());
					laoEsimImeiEid.setUpdatedate(new Date());
					laoEsimImeiEid.setEid(eid);
					laoEsimImeiEid.setImeiid(imei);
					String id = ZkGenerateSeq.getIdSeq(SeqID.DEVICEREL_ID);
					laoEsimImeiEid.setId(Long.valueOf(id));
					int insert = laoEsimImeiEidMapper.insert(laoEsimImeiEid);
					if (insert > 0) {
						return true;
					}
					return false;

				} else {
					if (null == queryByEid.getImeiid()) {
						// 根据Eid更新imei
						laoEsimImeiEidMapper.updateByEid(eid, imei);
						return true;
					}
					return false;
				}

			}

			return false;
		}
		return true;
	}

	/**
	 * 产品列表展示
	 */
	public List<Map<String, Object>> queryGoods() {
		List<LaoEsimGoods> list = laoEsimGoodsExtMapper.queryGoods();
		List<Map<String, Object>> arrayList = new ArrayList<>();
		if (null != list && list.size() > 0) {
			for (LaoEsimGoods laoEsimGoods : list) {
				Map<String, Object> goodsMap = new HashMap<String, Object>();
				goodsMap.put("goodsId", laoEsimGoods.getGoodsId());
				goodsMap.put("goodName", laoEsimGoods.getGoodsName());
				goodsMap.put("goodsPrice", laoEsimGoods.getGoodsPrice());
				goodsMap.put("goodsPic", laoEsimGoods.getGoodsPic());
				goodsMap.put("countryId", laoEsimGoods.getCountryId());
				goodsMap.put("countryName", laoEsimGoods.getCountryname());
				goodsMap.put("goodsCycle", laoEsimGoods.getGoodsCycle());
				arrayList.add(goodsMap);
			}
			return arrayList;
		}
		return null;
	}

	@Override
	public LaoEsimGoodsDto findGoodByGoodsId(String goodsId) {
		LaoEsimGoods laoEsimGoods = laoEsimGoodsExtMapper.findGoodByGoodsId(Long.valueOf(goodsId));
		if (null == laoEsimGoods) {

			return null;
		}
		LaoEsimGoodsDto dto = new LaoEsimGoodsDto();
		BeanMapper.copy(laoEsimGoods, dto);
		return dto;
	}
    /**
     * 订购service
     */
	@Override
	@Transactional()
	public Map<String, Object> orderGoods(String username, LaoEsimGoodsDto dto, String eid, String orderTag,
			String iccid) {

		Map<String, Object> resutMap = new HashMap<>();
		
        String tradeTypeCode="";
		String goodsStatus = "";
		//根据eid lenovoId 状态为0查询订购失败数据,有数据则
		LaoEsimUser queryByUserName=laoEsimUserExtMapper.queryUserOrderFail(eid,username);
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("eid", eid);
		paramMap.put("goodsId", dto.getGoodsId());
		LaoEsimUser queryUserEidandGoodsId = laoEsimUserExtMapper.queryUserEidandGoodsId(paramMap);
		// 110 绑定 100订购
		if ("100".equals(orderTag) ) {
			goodsStatus = "订购";
			if ("".equals(iccid) && null==queryByUserName && null==queryUserEidandGoodsId) {
				LaoEsimIccidLib esimIccidLib = laoEsimIccidLibExtMapper.selectByGoodsId(String.valueOf(dto.getGoodsId()));
				if (null == esimIccidLib) {
					resutMap.put("respCode", "0005"); // 没有空闲的卡资源
					return resutMap;
				}
				iccid = esimIccidLib.getIccid();
			}else if(null!=queryByUserName){ //订购失败,在次订购时
				iccid=queryByUserName.getIccid();
			}
			tradeTypeCode="200";
		} else if ("110".equals(orderTag) && null != iccid) {
			goodsStatus = "绑定";
			tradeTypeCode="240";
			
		} /*else {
			resutMap.put("respCode", "0007"); // 绑定操作非法
			return resutMap;
		}*/
		// 调用Es2绑定
		String statusCode="";   //trade表中的状态
		String mactingId = "";
		String dpAddress = "";
		String orderStatus="1";
		if (null!=queryUserEidandGoodsId  ) {
			if (queryUserEidandGoodsId.getCurentuserstatus()==1 ||queryUserEidandGoodsId.getCurentuserstatus()==2) {
				log.info("此eid已经订购过该产品>>>>>>>>>>>>>>>>>>>");
				resutMap.put("iccid", queryUserEidandGoodsId.getIccid());
				resutMap.put("profileId",queryUserEidandGoodsId.getProfileid() );
				resutMap.put("dpAddress", queryUserEidandGoodsId.getDpaddress());
			}
		}else{
			//未订购过
			String downloadOrder = es2Service.downloadOrder(iccid, eid);
			//String downloadOrder = "{\"header\":{\"functionExecutionStatus\":{\"status\":\"Executed-Success\"}}}";
			log.info("downloadOrder接口返回结果>>>>>>>>>>>"+downloadOrder);
			JSONObject downloadOrderJSON = JSON.parseObject(downloadOrder);
			if (checkEs2Resp(downloadOrder) && null!=downloadOrderJSON.getString("iccid")) {
				String confirmOrder = es2Service.confirmOrder(iccid);
				log.info("confirmOrder接口返回结果>>>>>>>>>>>"+confirmOrder);
				String releaseProfile = es2Service.releaseProfile(iccid);
				log.info("releaseProfile接口返回结果>>>>>>>>>>>"+confirmOrder);
				if (checkEs2Resp(releaseProfile) && checkEs2Resp(confirmOrder)) {
					JSONObject parseObject = JSON.parseObject(confirmOrder);
					mactingId =parseObject.getString("matchingId");
					dpAddress = parseObject.getString("smdpAddress");
					statusCode="0";  
				}else{
					orderStatus="0";
					statusCode="2";
				}
			}else{
				orderStatus="0";
				statusCode="2";
			}
			//生成trade
			Long tradeId = esimUserService.insertTrade(username, eid, iccid, dto.getCountryId(), dto.getGoodsId(), tradeTypeCode);
			
			// 更新卡资源 到在用状态
			laoEsimIccidLibExtMapper.updateByIccid(iccid);
			if (null!=queryByUserName && 0==queryByUserName.getCurentuserstatus() ) {
				queryByUserName.setCurentuserstatus(Short.valueOf(orderStatus));
				queryByUserName.setProfileid(mactingId);
				queryByUserName.setDpaddress(dpAddress);
				laoEsimUserMapper.updateByPrimaryKeySelective(queryByUserName);
			}else{
				String insertUser = esimUserService.insertUser(orderStatus,username, dto, eid, iccid, goodsStatus, mactingId, dpAddress);
			}
			//更新trade表
			esimUserService.updateTrade(statusCode, String.valueOf(tradeId));
			if ("2".equals(statusCode) && "0".equals(orderStatus)) {
				resutMap.put("respCode", "1007");
			}else{
				resutMap.put("iccid", iccid);
				resutMap.put("profileId", mactingId);
				resutMap.put("dpAddress", dpAddress);
			}
		}
		return resutMap;
	}

	/**
	 * 连接service
	 */
	@Override
	public boolean connectServer(String username, String eid, String iccid) {
		boolean flay = true;
		
		String tradeTypeCode = "210"; // 启用profile
		Long tradeId = esimUserService.insertTrade(username, eid, iccid, null, null, tradeTypeCode);
		// 先查询,先判断是否是订购还是绑定状态,在更新
		String statusCode="0";
		LaoEsimUser esimUser = new LaoEsimUser();
		esimUser.setLenovoid(username);
		esimUser.setIccid(iccid);
		esimUser.setEid(eid);
		LaoEsimUser user = laoEsimUserExtMapper.queryUser(esimUser);
        if (null != user) {
        	user.setFirstcalltime(new Date());
			
        	if ( user.getCurentuserstatus() == 1) {
        		user.setCurentuserstatus(Short.valueOf("2"));
        		
        	} else if (null != user && user.getCurentuserstatus() == 4) {
        		user.setCurentuserstatus(Short.valueOf("5"));
        	} else if (null != user && user.getCurentuserstatus() == 7) {
        		user.setCurentuserstatus(Short.valueOf("8"));
        	}
        	laoEsimUserMapper.updateByPrimaryKeySelective(user);
		}else{
			statusCode="2";
			flay=false;
		}
		// 更新esim_trade 表
		esimUserService.updateTrade(statusCode, String.valueOf(tradeId));
		return flay;

	}

	/**
	 * 解绑service
	 */
	@Override
	public Map<String,Object> cancelServer(String username, String eid, String iccid) {
		Map<String,Object> map = new HashMap<>();
		// 调用 es2 接口
		LaoEsimUser esimUser = new LaoEsimUser();
		esimUser.setLenovoid(username);
		esimUser.setIccid(iccid);
		esimUser.setEid(eid);
		LaoEsimUser user = laoEsimUserExtMapper.queryUser(esimUser);

		// 先查询,先判断是否是订购还是绑定状态,在更新
		if (null!=user ) {
			String tradeTypeCode = "220"; 
			Long tradeId = esimUserService.insertTrade(username, eid, iccid, null, null, tradeTypeCode);
			String cancelOrder = es2Service.cancelOrder(iccid, eid,user.getProfileid());
			log.info("cancelOrder接口返回>>>>>>>>>>"+cancelOrder);
			String statusCode="0";
			if (checkEs2Resp(cancelOrder)|| checkEs2CancelOrder(cancelOrder)) {
				log.info("调用es2 解绑接口成功>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				try {
					// lao_esim_user_bunding
					LaoEsimUser laoEsimUser = new LaoEsimUser();
					laoEsimUser.setCurentuserstatus(Short.valueOf("3")); // 3 解绑
					laoEsimUser.setEid(""); // 设置为空
					laoEsimUser.setProfileid(""); // 设置为空
					laoEsimUser.setDpaddress("");
					laoEsimUser.setUserId(user.getUserId()); //
					laoEsimUserMapper.updateByPrimaryKeySelective(laoEsimUser);
					//查询
					LaoEsimUserBunding findUserBunding = userBunding.findUserBunding(user.getUserId());
					if (null==findUserBunding) {
						esimUserService.insertBunding(user.getUserId(), eid);
					}else{
						esimUserService.updateUserBunding(user.getUserId(), eid);
					}
					
				} catch (NumberFormatException e) {
					statusCode="2";
					log.info("解绑服务更新数据异常>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					map.put("respCode", "9999");
					e.printStackTrace();
				}
			}else{
				statusCode="2";
				log.info("调用es2 解绑接口失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				map.put("respCode", "1003");
			}
			esimUserService.updateTrade(statusCode, String.valueOf(tradeId));
		}else{
			map.put("respCode", "1003");
		}
		return map;

	}
    /**
     * 
     * 转赠service
     */
	@Override
	public Map<String,Object> changeServer(String username, String eid, String iccid, String username2, String goodsId) {
		Map<String,Object> map=new HashMap<>();
		LaoEsimUser user = new LaoEsimUser();
		user.setLenovoid(username);
		user.setIccid(iccid);
		user.setEid(eid);
		LaoEsimUser esimUser = laoEsimUserExtMapper.queryUser(user);
		String goodsStatus = "转赠";
		// 更新user
		// 生成订单表
		if (null != esimUser) {
			Long tradeId = esimUserService.insertTrade(username, eid, iccid, null, Long.valueOf(goodsId), "230");
			String cancelOrder = es2Service.cancelOrder(iccid, eid, esimUser.getProfileid());
			String handletagCode="";
			log.info("解绑接口es2>>>>>"+cancelOrder);
			if (checkEs2Resp(cancelOrder) || checkEs2CancelOrder(cancelOrder)) {
				log.info("调用es2 解绑接口成功>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				 handletagCode="0";
				try {
					esimUserService.insertBunding(esimUser.getUserId(), eid);
					esimUser.setEid("");
					esimUser.setCurentuserstatus(Short.valueOf("6"));
					esimUser.setProfileid("");
					esimUser.setDpaddress("");
					esimUser.setLenovoid(username2); // 目标账号
					laoEsimUserMapper.updateByPrimaryKey(esimUser);

					// 生成UserGoods表信息
					//LaoEsimGoods esimGoods = laoEsimGoodsExtMapper.findGoodByGoodsId(Long.valueOf(goodsId));
                     LaoEsimUserGoods laoEsimUserGoods = new LaoEsimUserGoods();
                     laoEsimUserGoods.setGoodsstatus(goodsStatus);
                     laoEsimUserGoods.setUpdatedate(new Date());
                     laoEsimUserGoods.setUserId(esimUser.getUserId());
					userGoods.updateByUserId(laoEsimUserGoods);
					// lao_esim_user_given
				    LaoEsimUserGiven given=userGiven.findByUserId(esimUser.getUserId());
				    if (null==given) {
						
				    	esimUserService.insertUserGiven(esimUser.getUserId(), goodsId, username, username2, eid);
					}else{
						//更新一下转赠表
						LaoEsimUserGiven po = new LaoEsimUserGiven();
						po.setUserId(esimUser.getUserId());
						po.setEidtarger(eid); 
						po.setLenovoidtargerisbundingdate(new Date());
						userGiven.updateByUserId(po);
					}
				} catch (Exception e) {
					handletagCode="2";
					map.put("respCode", "9999");
					log.info("转赠服务更新数据异常>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					e.printStackTrace();
				}

			}else{
				handletagCode="2";
				log.info("调用es2 解绑接口失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				map.put("respCode", "1004"); //转赠失败
			}
			esimUserService.updateTrade(handletagCode, String.valueOf(tradeId));
		}else{
			map.put("respCode", "9999");
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> findOrderGoods(String username) {

		List<Map<String, Object>> list = userGoods.findOrderGoodsByUsername(username);
		if (null != list && list.size() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
			for (Map<String, Object> map : list) {
				HashMap<String,Object> hashMap = new HashMap<>();
				hashMap.put("goodsPrice", map.get("GOODS_PRICE"));
				hashMap.put("profileId", map.get("PROFILEID"));
				hashMap.put("goodName", map.get("GOODS_NAME"));
				
				Object object = map.get("CURENTUSERSTATUS");
			    if ("0".equals(object.toString())) {
					hashMap.put("eid", "");
				}else{
					hashMap.put("eid", map.get("EID"));
				}
				hashMap.put("iccid", map.get("ICCID"));
				hashMap.put("goodsStatus", map.get("GOODSSTATUS"));
				hashMap.put("dpAddress", map.get("DPADDRESS"));
				hashMap.put("countryId", map.get("COUNTRY_ID"));
				hashMap.put("countryName", map.get("COUNTRYNAME"));
				Date STARTDATE = (Date) map.get("STARTDATE");
				hashMap.put("startDate", sdf.format(STARTDATE));
				hashMap.put("goodsPic", map.get("GOODS_PIC"));
				hashMap.put("goodsId", map.get("GOODS_ID"));
				Date ENDDATE = (Date) map.get("ENDDATE");
				hashMap.put("endDate", sdf.format(ENDDATE));
				arrayList.add(hashMap);
			}
			return arrayList;
		}
		return null;
	}

	@Override
	public void insertEsimLog(LaoEsimLogDto esimLogDto) {
		Long logId = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.DEVICEREL_ID));
		esimLogDto.setId(logId);
		LaoEsimLog po = new LaoEsimLog();
		BeanMapper.copy(esimLogDto, po);
		esimLog.insertSelective(po);

	}

	@Override
	public boolean checkIccid(String iccid) {
		LaoEsimIccidLib eb = laoEsimIccidLibExtMapper.selectByIccid(iccid);
		if (null == eb) {
			return false;
		}
		return true;
	}
    
	private static boolean checkEs2Resp(String result){
		JSONObject parseObject = JSON.parseObject(result);
		String string = parseObject.getString("header");
		JSONObject parseObject2 = JSON.parseObject(string);
		String string2 = parseObject2.getString("functionExecutionStatus");
		JSONObject parseObject3 = JSON.parseObject(string2);
		String string3 = parseObject3.getString("status");
		if ("Executed-Success".equals(string3)){
			return true;
		}
		return false;
	} 
	private static boolean checkEs2CancelOrder(String result){
		JSONObject parseObject = JSON.parseObject(result);
		String string = parseObject.getString("header");
		JSONObject parseObject2 = JSON.parseObject(string);
		String string2 = parseObject2.getString("functionExecutionStatus");
		JSONObject parseObject3 = JSON.parseObject(string2);
		String string3 = parseObject3.getString("status");
		String statusCodeData = parseObject3.getString("statusCodeData");
		JSONObject statusCode = JSON.parseObject(statusCodeData);
		if ("Failed".equals(string3) && "8.2.1".equals(statusCode.getString("subjectCode")) && "3.3".equals(statusCode.getString("reasonCode")) || "1.2".equals(statusCode.getString("reasonCode")) ){
			return true;
		}
		return false;
	} 
	
}
