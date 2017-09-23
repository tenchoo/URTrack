package com.urt.service.esim;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.dto.LaoEsimGoodsDto;
import com.urt.interfaces.esim.EsimUserService;
import com.urt.mapper.LaoEsimTradeMapper;
import com.urt.mapper.LaoEsimUserBundingMapper;
import com.urt.mapper.LaoEsimUserGivenMapper;
import com.urt.mapper.LaoEsimUserGoodsMapper;
import com.urt.mapper.LaoEsimUserMapper;
import com.urt.mapper.ext.LaoEsimUserExtMapper;
import com.urt.mapper.ext.LaoEsimUserGoodsExtMapper;
import com.urt.po.LaoEsimTrade;
import com.urt.po.LaoEsimUser;
import com.urt.po.LaoEsimUserBunding;
import com.urt.po.LaoEsimUserGiven;
import com.urt.po.LaoEsimUserGoods;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

@Service("esimUserService")
public class EsimUserServiceImpl implements EsimUserService {

	@Autowired
	private LaoEsimUserMapper laoEsimUserMapper;
	@Autowired
	private LaoEsimTradeMapper laoEsimTradeMapper;
	@Autowired
	private LaoEsimUserGoodsExtMapper laoEsimUserGoodsExtMapper;
    
	@Autowired
	private LaoEsimUserGoodsMapper userGoodsMapper;
	
	@Autowired
	private LaoEsimUserBundingMapper laoEsimUserBundingMapper;
	
	@Autowired
	private LaoEsimUserGoodsMapper usergoodsMap;
	@Autowired
	private LaoEsimUserExtMapper esimUserExt;
	
	@Autowired
	private LaoEsimUserGivenMapper userGivenMapper;
	
	
	

	@Override
	public String insertUser(String orderStatus,String username, LaoEsimGoodsDto dto, String eid, String iccid, String goodsStatus,String mactingId,String dpaddress) {
		Long userId=null;
		Long tradeId = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.DEVICEREL_ID));
		if ("订购".equals(goodsStatus)) {
			LaoEsimUser laoEsimUser = new LaoEsimUser();
			userId = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.DEVICEREL_ID));
			laoEsimUser.setUserId(userId);
			laoEsimUser.setEid(eid);
			laoEsimUser.setIccid(iccid);
			laoEsimUser.setLenovoid(username);
			laoEsimUser.setBinddate(new Date());
			laoEsimUser.setCurentuserstatus(Short.valueOf(orderStatus));  
			laoEsimUser.setProfileid(mactingId);
			laoEsimUser.setDpaddress(dpaddress);
			
			laoEsimUserMapper.insertSelective(laoEsimUser);
			
			
			LaoEsimUserGoods laoEsimUserGoods = new LaoEsimUserGoods();
			Date startDate = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(startDate);
			calendar.add(Calendar.DATE, Integer.valueOf(dto.getGoodsCycle()));// 把日期往后增加
			laoEsimUserGoods.setGoodsId(Long.valueOf(dto.getGoodsId()));
			laoEsimUserGoods.setUserId(userId);
			laoEsimUserGoods.setGoodsstatus(goodsStatus);
			Date endDate = calendar.getTime();
			laoEsimUserGoods.setStartdate(startDate);
			laoEsimUserGoods.setEnddate(endDate);
			laoEsimUserGoods.setBinddate(new Date());
			
			laoEsimUserGoods.setCreatedate(new Date());
	        
			userGoodsMapper.insertSelective(laoEsimUserGoods);
			
			
			
		} else if ("绑定".equals(goodsStatus)) {
			LaoEsimUser user = new LaoEsimUser();
			user.setLenovoid(username);
			user.setIccid(iccid);
			LaoEsimUser eismUser = esimUserExt.queryUser(user);
			userId=eismUser.getUserId();
			String  userGoodsStatus=null;
			//更新表 eism_user
			if (eismUser.getCurentuserstatus()==6 &&  "1".equals(orderStatus) ){
				userGoodsStatus="已转赠";
				
				eismUser.setCurentuserstatus(Short.valueOf("7"));  //转赠停用
				//更新一下转赠表
				LaoEsimUserGiven userGiven = new LaoEsimUserGiven();
				userGiven.setUserId(userId);
				userGiven.setEidtarger(eid); 
				userGiven.setLenovoidtargerisbundingdate(new Date());
				userGivenMapper.updateByUserId(userGiven);
				
			}else if(eismUser.getCurentuserstatus()==3 &&  "1".equals(orderStatus)){
				eismUser.setCurentuserstatus(Short.valueOf("4"));  
				userGoodsStatus="绑定";
				LaoEsimUserBunding laoEsimUserBunding = new LaoEsimUserBunding();
				laoEsimUserBunding.setEidtarger(eid);
				laoEsimUserBunding.setUserId(userId);
				laoEsimUserBunding.setBundlingdate(new Date());
				laoEsimUserBundingMapper.updateUserBunding(laoEsimUserBunding);
			}
			
		    LaoEsimUserGoods goods = new LaoEsimUserGoods();
		    goods.setGoodsstatus(userGoodsStatus);
		    goods.setUpdatedate(new Date());
		    goods.setUserId(userId);
			userGoodsMapper.updateUserGoods(goods);
			
			
			
			eismUser.setProfileid(mactingId);
			eismUser.setDpaddress(dpaddress);
			if ("0".equals(orderStatus)) {
				
			}else{
				eismUser.setEid(eid);
			}
			eismUser.setIccid(iccid);
			eismUser.setBinddate(new Date());
			laoEsimUserMapper.updateByPrimaryKeySelective(eismUser);
		}
		return String.valueOf(userId);
	}
    @Override
	public void upDataUser(String userId, String goodsCycle) {

		LaoEsimUser esimUser = new LaoEsimUser();
		esimUser.setCurentuserstatus(Short.valueOf("1")); // 已绑定停用
		esimUser.setBinddate(new Date());
		String profileid = "";
		String dpaddress = "";
		esimUser.setProfileid(profileid);
		esimUser.setDpaddress(dpaddress);
		esimUser.setUserId(Long.valueOf(userId));

		laoEsimUserMapper.updateByPrimaryKey(esimUser);

		LaoEsimUserGoods esimGoods = new LaoEsimUserGoods();
		Date startDate = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);
		calendar.add(Calendar.DATE, Integer.valueOf(goodsCycle));// 把日期往后增加
		Date endDate = calendar.getTime();
		esimGoods.setStartdate(startDate);
		esimGoods.setEnddate(endDate);

		laoEsimUserGoodsExtMapper.updateByUserId(esimGoods);
	}

	@Override
	public Long insertTrade(String username, String eid, String iccid, String countryId, Long goodsId,
			String tradeTypeCode) {
		Long tradeId = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.DEVICEREL_ID));

		LaoEsimTrade laoEsimTrade = new LaoEsimTrade();
		laoEsimTrade.setTradeId(tradeId);
		laoEsimTrade.setCountryId(countryId);
		laoEsimTrade.setGoodsId(goodsId);
		laoEsimTrade.setEid(eid);
		laoEsimTrade.setIccid(iccid);
		laoEsimTrade.setLenovoid(username);
		laoEsimTrade.setAcceptdate(new Date());
		laoEsimTrade.setHandletag(Short.valueOf("1"));
		laoEsimTrade.setTradeTypecode(tradeTypeCode);
		int insertSelective = laoEsimTradeMapper.insertSelective(laoEsimTrade);
		if (insertSelective>0) {
			return  tradeId;
		}
		return null;
		
	}
	
	public void updateTrade(String statusCode,String tradeId){
		LaoEsimTrade trade = new LaoEsimTrade();
		trade.setTradeId(Long.valueOf(tradeId));
		trade.setHandletag(Short.valueOf(statusCode));
		trade.setFinishdate(new Date());
		laoEsimTradeMapper.updateByPrimaryKeySelective(trade);
		
		
	}

	@Override
	public void insertBunding(Long userId, String eid) {

		LaoEsimUserBunding laoEsimUserBunding = new LaoEsimUserBunding();
		laoEsimUserBunding.setUserId(userId);
		laoEsimUserBunding.setEid(eid);
		laoEsimUserBunding.setUnbundlingdate(new Date());
		laoEsimUserBundingMapper.insert(laoEsimUserBunding);
	}

	@Override
	public void inSertUserGoods(String goodsCycle, String goodsId, Long userId,String goodsStatus ) {
		LaoEsimUserGoods laoEsimUserGoods = new LaoEsimUserGoods();
		laoEsimUserGoods.setUserId(userId);
		laoEsimUserGoods.setGoodsId(Long.valueOf(goodsId));

		Date startDate = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);
		calendar.add(Calendar.DATE, Integer.valueOf(goodsCycle));// 把日期往后增加
		Date endDate = calendar.getTime();
		laoEsimUserGoods.setStartdate(startDate);
		laoEsimUserGoods.setEnddate(endDate);
		laoEsimUserGoods.setGoodsstatus(goodsStatus);
		laoEsimUserGoods.setCreatedate(new Date());
		usergoodsMap.insertSelective(laoEsimUserGoods);
		
		

	}
	public void insertUserGiven(Long userId,String goodsId,String username,String usernameTarge,String eid){
		LaoEsimUserGiven userGiven = new LaoEsimUserGiven();
		userGiven.setUserId(userId);
		userGiven.setEid(eid);
		userGiven.setGivendate(new Date());
		userGiven.setGoodsid(Long.valueOf(goodsId));
		userGiven.setLenovoid(username);
		userGiven.setLenovoidtarger(usernameTarge);
		userGivenMapper.insertSelective(userGiven);
	}
	/**
	 * 更新绑定解绑表
	 */
	@Override
	public void updateUserBunding(Long userId, String eid) {
		LaoEsimUserBunding laoEsimUserBunding = new LaoEsimUserBunding();
		laoEsimUserBunding.setEidtarger(eid);
		laoEsimUserBunding.setUserId(userId);
		laoEsimUserBunding.setUnbundlingdate((new Date()));
		laoEsimUserBundingMapper.updateUserBunding(laoEsimUserBunding);
		
	}
    
	
	
	
}