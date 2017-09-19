package com.urt.service.userIndex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.interfaces.userIndex.ChannelCustInfoOutlineService;
import com.urt.interfaces.userIndex.CorporateClientService;
import com.urt.mapper.ext.LaoCustomerNoticePoExtMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.service.dmp.DMPServiceImpl;

@Service("infoOutlineService")
@Transactional(propagation = Propagation.REQUIRED)
public class ChannelCustInfoOutlineImpl implements ChannelCustInfoOutlineService{
	Logger log = Logger.getLogger(ChannelCustInfoOutlineImpl.class);

	@Autowired
	private LaoUserExtMapper userExtMapper;
	@Override
	public Map<String, Object> getInfoOutline(Long custId) {
		log.info("进入方法getInfoOutline");
		Map<String,Object> infoMap=new HashMap<String, Object>();
		int cardCountAll = userExtMapper.getCardCount(custId);//channelcust下卡总数
		int newCardsOfCurrentMonth = userExtMapper.getNewCardsOfCurrentMonth(custId);//本月新增客户数
		int cardsOfPrevMonth=userExtMapper.getCardsOfPrevMonth(custId);//上月新增客户数
		int rate;//环比
		if(newCardsOfCurrentMonth>0&&cardsOfPrevMonth==0){
			rate=9999;
		}else if(newCardsOfCurrentMonth==0&&cardsOfPrevMonth==0){
			rate=0;
		}else{
			BigDecimal b1=new BigDecimal(newCardsOfCurrentMonth);
			BigDecimal b2=new BigDecimal(cardsOfPrevMonth);
			BigDecimal b3=new BigDecimal(100);
			rate = b1.subtract(b2).divide(b2, 2, RoundingMode.HALF_UP).multiply(b3).intValue();
		}
		infoMap.put("cardCountAll", cardCountAll);
		infoMap.put("newCardsOfCurrentMonth", newCardsOfCurrentMonth);
		infoMap.put("rate", rate+"%");
		log.info("走出方法getInfoOutline");
		return infoMap;
	}
  public static void main(String[] args) {
	BigDecimal b1=new BigDecimal(1);
	BigDecimal b2=new BigDecimal(3);
	BigDecimal b3=new BigDecimal(100);
	BigDecimal rate = b1.divide(b2,2, RoundingMode.HALF_UP);
	BigDecimal rateValue = rate.multiply(b3);
	System.out.println(rateValue.intValue());
}

}
