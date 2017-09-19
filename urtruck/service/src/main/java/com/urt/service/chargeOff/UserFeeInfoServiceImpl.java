package com.urt.service.chargeOff;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.IccidLibDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.Trade.TradeFeeSubDto;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.chargeOff.UserFeeInfoService;
import com.urt.mapper.LaoUserfeeInfoMapper;
import com.urt.po.LaoUserfeeInfo;
import com.urt.service.util.WeixinUtil;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

@Service("userFeeInfoService")
@Transactional(propagation = Propagation.REQUIRED)
public class UserFeeInfoServiceImpl implements UserFeeInfoService {

	@Autowired
	private LaoUserfeeInfoMapper LaoUserfeeInfoDAO;
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public int insert(String fee, String recvFee, String payTag, Long tradeId) {
		//插入用户费用表记录   ：缴费金额 -RECV_FEE   应收费用-FEE   未出账金额-BILL_FEE，初始=FEE
		LaoUserfeeInfo record = new LaoUserfeeInfo();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		int acceptMonth = Integer.parseInt(format.format(new Date()));
		record.setAcceptMonth(acceptMonth);
		record.setFee(Long.parseLong(WeixinUtil.getMoney(fee)));
		record.setBillFee(Long.parseLong(WeixinUtil.getMoney(fee)));
		record.setChargeId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.CHARGE_ID)));
		record.setRecvTime(new Date());
		if(tradeId != null){
			TradeDto queryTradeId;
			try {
				queryTradeId = tradeService.queryTradeId(tradeId);
				if(queryTradeId != null){
					record.setGoodsId(queryTradeId.getGoodsId());
					record.setGoodsReleaseId(queryTradeId.getGoodsReleaseId() == null? null:queryTradeId.getGoodsReleaseId().longValue());
					record.setIccid(queryTradeId.getIccid());
//					record.setOperId(operId);
					record.setPayTag(payTag);
					record.setRecvFee(Long.parseLong(WeixinUtil.getMoney(recvFee)));
					record.setTradeId(tradeId);
					record.setUserId(queryTradeId.getUserId());
					record.setChannelCustId(queryTradeId.getChannalCustId());
					record.setCustId(queryTradeId.getCustId());
					
					IccidLibDto selectByIccid = userService.selectByIccid(queryTradeId.getIccid());
					if(selectByIccid != null){
						record.setMsisdn(selectByIccid.getMsisdn());
						record.setOperatorsId(selectByIccid.getOperators() == null? null:Integer.parseInt(selectByIccid.getOperators()));
						if(selectByIccid.getOperators().equals("1")){
							Calendar calendar = Calendar.getInstance(); 
							int day = calendar.get(Calendar.DAY_OF_MONTH);
							if(day > 26){
								calendar.add(calendar.MONTH, 1);
								acceptMonth = Integer.parseInt(format.format(calendar.getTime()));
								record.setAcceptMonth(acceptMonth);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return LaoUserfeeInfoDAO.insert(record);
	}

}
