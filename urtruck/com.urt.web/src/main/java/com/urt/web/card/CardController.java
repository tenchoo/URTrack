package com.urt.web.card;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.urt.common.util.ResultMsg;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Goods.GoodsReleaseDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.Trade.TradeFeeSubDto;
import com.urt.dto.unicom.PaymentDto;
import com.urt.interfaces.Goods.GoodsReleaseService;
import com.urt.interfaces.Trade.TradeFeeSubService;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.authority.TagService;
import com.urt.interfaces.remain.RemainService;
import com.urt.web.common.util.ActionUtil;
import com.urt.web.filter.RechargeFilter;


@Controller
@RequestMapping(value="/card")
public class CardController {
	private static Logger log = LoggerFactory.getLogger(RechargeFilter.class);
	@Autowired
	TagService tagService;
	@Autowired
	TradeService tradeService;
	@Autowired
	UserService userService;
	@Autowired
	private RemainService remainService;
	@Autowired
	private TradeFeeSubService tradeFeeSubService;
	@Autowired
	private GoodsReleaseService goodsReleaseService;
	@ResponseBody
	@RequestMapping("/activate")
	public String activate(String custId,String iccid,String code,HttpServletRequest request) {
		
		Integer failCount = 0;
		
		IccidLibDto dto=userService.selectByIccid(iccid);
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		if(dto == null){
			return "InvalidCard";
		}
		if(dto.getActived().equals("1")){
			boolean ifSuperUser = ActionUtil.ifSuperUser(request);
			if (!ifSuperUser&!dto.getCustid().equals(String.valueOf(user.getCustId()))) {
				return "noAccess";
			}
			try {				
				GoodsReleaseDto goodsReleaseDto = goodsReleaseService.findBygoodsReleaseId(Integer.parseInt(dto.getInitproduct().trim()));
				String tradeId = tradeService.addTrade(user,dto.getCustid(), null,iccid,goodsReleaseDto.getGoodsId().toString() ,dto.getInitproduct(), "100",dto.getIfMaintenance());

					TradeDto tradeDto = tradeService.queryTradeId(Long.parseLong(tradeId));

					/*
					 * GoodsDto goodsDto = goodsService.findByGoodsId(Long.parseLong());
					 */
					if (goodsReleaseDto.getReleasePrice() != null && Double.valueOf(goodsReleaseDto.getReleasePrice()) > 0) {
						tradeFeeSubService.addTradeFeeSub(tradeDto);// 算费
						TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
						// String fee = tradeFeeSubDto.getFee().toString();
						tradeDto.setFee(tradeFeeSubDto.getFee());
						PaymentDto payment = new PaymentDto();
						payment.setOrderId(tradeId);//订单号
						payment.setPayType(0);
						tradeFeeSubService.changePayTag(tradeId, payment);
					}
					// 扣费
					TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
					HashMap<String, Object> paraMap = new HashMap<String, Object>();
					paraMap.put("channelCustId", tradeDto.getChannalCustId());// 渠道客户ID
					paraMap.put("tradeTypeCode", "100");// 业务类型编码, 订购100
					paraMap.put("recvFee", rmbYuan2Fen(tradeFeeSubDto.getFee()));// 缴费金额
					paraMap.put("fee", rmbYuan2Fen(tradeFeeSubDto.getOldfee()));// 应收费用
					paraMap.put("discntFee", rmbYuan2Fen(tradeFeeSubDto.getFee()));// 优惠后费用
					paraMap.put("realFee", rmbYuan2Fen(tradeFeeSubDto.getFee()));// 实收费用

					paraMap.put("payTag", "0");// 0：未缴费 1：用户已缴费 2：客户已缴费
					paraMap.put("userId", tradeDto.getUserId());// 用户
					paraMap.put("tradeId", tradeDto.getTradeId());// tradeId
					paraMap.put("goodsId", tradeDto.getGoodsId());// goodsId
					paraMap.put("tradeId", tradeDto.getTradeId());//tradeId
					int charge = remainService.charge(paraMap);// 扣费
					if (charge == 1) {
						return "notsufficientfunds";
					}
				
				String result = userService.userArchiving(tradeId);
				return result;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "activefailed";
			}
		}else{
			return "activated";
		}
		
	}
	@RequestMapping("/toActivate")
	public ModelAndView toActivate(HttpServletRequest request) {
		ModelAndView mv=new ModelAndView("card/activate");
		LaoSsAccountDto user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
		mv.addObject("custId", user.getCustId());
		return mv;
	}
	@ResponseBody
	@RequestMapping(value="/getIccidInfoById")
	public Map getIccidInfoById(String id) {
		return tagService.getIccidLibByIccid(id);
	}
	
	/**
	 * 人民币元转换成分
	 * 
	 * @param yuan
	 * @return
	 */
	public static Long rmbYuan2Fen(String yuan) {
		BigDecimal b1 = new BigDecimal(yuan);
		BigDecimal b2 = new BigDecimal(100);
		BigDecimal ret = b1.multiply(b2);
		return ret.longValue();
	}
}
