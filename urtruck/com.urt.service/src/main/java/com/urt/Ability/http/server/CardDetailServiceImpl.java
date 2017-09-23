package com.urt.Ability.http.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.dto.http.CardDetail;
import com.urt.dto.http.CardInfo;
import com.urt.interfaces.http.CardDetailService;
import com.urt.mapper.LaoUserStateDdMapper;
import com.urt.po.LaoUserStateDd;
/**
 * 
 * @author issuser
 * 查询客户当前拥有的卡的总数量，一二级分类，以及状态，在正常和停机状态下的卡的数量
 *
 */
@Service("cardDetailService")
public class CardDetailServiceImpl implements CardDetailService {

	protected static final Logger logger = Logger.getLogger(CardDetailServiceImpl.class);
	@Autowired
	private LaoUserStateDdMapper laoUserStateDdMapper;
	
	/**
	 * 详细信息
	 */
	@Override
	public CardDetail queryCardDetail(Map<String, String> reqInfo) {
		logger.info("********CardDetailServerImpl.queryCardDetail()************ begin ****************");
		CardDetail cd = null;
		String newTime = null;
		String custId = reqInfo.get("custId");
		String dayTime = reqInfo.get("dayTime");
		String cardNewTime = getNewTime(custId);
		if(custId != null && !"".equals(custId)){
			cd = new CardDetail();
			//当客户未输入时间或者输入的时间没有，默认使用数据库最新查出的时间
			if(dayTime != null && !"".equals(dayTime)){
				//判断客户输入的时间是否大于数据
				if(Long.valueOf(dayTime) <= Long.valueOf(cardNewTime)){
					newTime = dayTime;
				}else{
					newTime = cardNewTime;
				}
			}else{
				newTime = cardNewTime;
			}
			//获取卡的总数量
			String count = queryCardcount(custId,newTime);
			//获取卡详细信息
			List<CardInfo> cardInfo = getCardInfo(custId,newTime);
			if(cardInfo != null && !"".equals(cardInfo.get(0).getCardNum())){
				cd.setRespCode("0000");
				cd.setRespDesc("success");
				cd.setCardTotalNum(count);
				cd.setCardInfo(cardInfo);
			}else{
				cd.setRespCode("0002");
				cd.setRespDesc("database exception");
			}
		}else{
			cd.setRespCode("9999");
			cd.setRespDesc("other errors");
		}
		logger.info("********CardDetailServerImpl.queryCardDetail()****************************" + cd.toString());
		return cd;
	}
	
	/**
	 * @return
	 * 查询卡的总数量
	 * 
	 */
	private String queryCardcount(String cardDetail,String newTime){
		logger.info("********CardDetailServerImpl.queryCardcount()*******card begin*********************");
		long channelCustId = Long.valueOf(cardDetail);
		long dayTime = Long.valueOf(newTime);
		Integer cardCount = laoUserStateDdMapper.selectCardCount(channelCustId,dayTime);
		logger.info("********CardDetailServerImpl.queryCardcount()*******card end*********************" + cardCount);
		if(cardCount == null){
			return "";
		}
		return String.valueOf(cardCount);
	}
	
	/**
	 * 查询客户下卡的一二级分类，卡的状态，正常和停机下的卡的数量
	 * @param cardDetail
	 * @return
	 */
	private List<CardInfo> getCardInfo(String cardDetail,String newTime){
		CardInfo cardInfo = null;
		long channelCustId = Long.valueOf(cardDetail);
		long dayTime = Long.valueOf(newTime);
		//根据一级二级分类和状态分组查询卡的一二级和数量状态信息。
		List<LaoUserStateDd> cardList = laoUserStateDdMapper.selectCardDetail(channelCustId,dayTime);
		List list = new ArrayList<>();
		if(cardList != null && cardList.size()>0){
			for (int i = 0; i < cardList.size(); i++) {
				cardInfo = new CardInfo();
				cardInfo.setCategoryOne(cardList.get(i).getValue1Name());
				cardInfo.setCategoryTwo(cardList.get(i).getValue2Name());
				cardInfo.setCardState(cardList.get(i).getStateName());
				cardInfo.setCardNum(String.valueOf(cardList.get(i).getCardNum()));
				list.add(cardInfo);
			}
		}else{
				cardInfo = new CardInfo();
				cardInfo.setCategoryOne("");
				cardInfo.setCategoryTwo("");
				cardInfo.setCardState("");
				cardInfo.setCardNum("");
				list.add(cardInfo);
		}
		logger.info("********CardDetailServerImpl.getCardInfo()****************************" + list.toString());
		return list;
	}
	
	
	
	/**
	 * 查询最新的时间
	 */
	private String getNewTime(String cardDetail){
		long channelCustId = Long.valueOf(cardDetail);
		Integer newTime = laoUserStateDdMapper.selectNewTimeCard(channelCustId);
		if(newTime != null){
			return String.valueOf(newTime);
		}
		return "";
	}

}
