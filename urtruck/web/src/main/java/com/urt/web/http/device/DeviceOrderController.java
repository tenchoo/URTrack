package com.urt.web.http.device;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoPeripheralSysAccessLogDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoSsResourceDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.Goods.GoodsReleaseDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.Trade.TradeFeeSubDto;
import com.urt.dto.device.Account;
import com.urt.interfaces.Goods.GoodsReleaseService;
import com.urt.interfaces.Goods.GoodsService;
import com.urt.interfaces.Trade.TradeFeeSubService;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.http.ServerCheckService;
import com.urt.web.common.util.StringUtil;

/**
 * device+ 产品订购
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/deviceorder")
public class DeviceOrderController {
	
	private static final Logger log=Logger.getLogger(DeviceOrderController.class);
	//private static final String PRIVATEATTACH="http://vbtest.lenovomm.cn/common/applay_order.xhtml?c=payWeb";//支付测试地址
//	private static final String PRIVATEATTACH="http://vbtest.lenovomm.cn/common/applay_order.xhtml?c=payWeb";//支付正式地址
	//private static final String REALM="h5mobiletest.lenovomm.com";
	private static final String PAYTYPE="1";
	private static final String PRODUCTNAME="Lenovo Connect";
	private static final String SERVERNAME="device+产品订购";
	
	@Value("#{configProperties['charge.pcwebPayRequestUrl']}")
	private  String PRIVATEATTACH;
	@Value("#{configProperties['device.REALM']}")
	private  String REALM;
	
	@Autowired
	private UserService userService;
	@Autowired
	private TradeService tradeService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private ServerCheckService serverService;
	@Autowired
	private TradeFeeSubService tradeFeeSubService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private GoodsReleaseService goodsReleaseService;
	@RequestMapping(value = "goodsorder", method = { RequestMethod.POST,RequestMethod.GET})
	public void goodsorder(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "iccid", required = true) String iccid,
			@RequestParam(value = "goodId", required = true) String goodId,
			@RequestParam(value = "st", required = true) String st
			) throws Exception {
		log.info("enter the method goodsorder");
		iccid = iccid.replace("#", "B");
		PrintWriter out = response.getWriter();
		JSONObject resultJson=new JSONObject();
		String retCode = "1";//-1 参数不全  -4 系统异常 1 正常 -6 没有可查询的产品 -7 非法iccid -10为未交费
		String custId="";
		try {
			IccidLibDto iccidLib = userService.selectByIccid(iccid);
			custId=iccidLib.getCustid();
			if(StringUtil.isBlank(custId)){
				retCode="-7";
			}else{
				LaoSsAccountDto ss = new LaoSsAccountDto();
				LaoSsResourceDto res = new LaoSsResourceDto();
				res.setResCode("2");
				List<LaoSsResourceDto> ListRes = new ArrayList<LaoSsResourceDto>();
				ListRes.add(res);
				ss.setResources(ListRes);
				GoodsReleaseDto relpease = goodsReleaseService.findBygoodsReleaseId(Integer.valueOf(goodId));	
				String tradeId = tradeService.addTrade(ss,custId, iccid,String.valueOf(relpease.getGoodsId()),goodId,"120","0");		
				TradeDto tradeDto = tradeService.queryTradeId(Long.parseLong(tradeId));
				//tradeFeeSubService.addTradeFeeSub(tradeDto);//算费
				String fee = relpease.getReleasePrice();
				Account account = deviceService.authSt(st);
				GoodsDto goods = goodsService.findByGoodsId(relpease.getGoodsId());
				if(tradeId != null){
					log.info("trade参数======================"+tradeDto.toString());
					if(goods.getGoodsPrices() != null && Double.valueOf(goods.getGoodsPrices()) > 0){
						tradeFeeSubService.addTradeFeeSub(tradeDto);
						TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
					Double	fee1 = Double.parseDouble(tradeFeeSubDto.getFee());
						//fee = feee/100;
						tradeDto.setFee(tradeFeeSubDto.getFee());
						tradeService.updateTrade(tradeDto);
					}else{
					Double	fee1 = Double.parseDouble(goods.getGoodsPrices().toString());
						tradeDto.setFee(String.valueOf(fee1));
						tradeService.updateTrade(tradeDto);
					}
				}
				resultJson.put("payType", PAYTYPE);
				resultJson.put("realm", REALM);
				resultJson.put("payAmount", fee);
				resultJson.put("stName", st);
				resultJson.put("productName", PRODUCTNAME);
				resultJson.put("merchantOrderId", tradeId);
				resultJson.put("userName", account.getAccountID());
				resultJson.put("userId", account.getUsername());
				resultJson.put("privateAttach", PRIVATEATTACH);
			}
		} catch (Exception e) {
			retCode="-4";
			e.printStackTrace();
		}
		log.info("支付地址"+PRIVATEATTACH);
		resultJson.put("retCode", retCode);
		out.println(resultJson.toString());
		
		//保存日志
		LaoPeripheralSysAccessLogDto recordDto=new LaoPeripheralSysAccessLogDto();
		if(!StringUtil.isBlank(custId)){
			recordDto.setCustId(Long.valueOf(custId));
		}
		String ip = request.getRemoteAddr();
		recordDto.setIpAddress(ip);
		recordDto.setUserName(iccid);
		recordDto.setServerName(SERVERNAME);
		if("1".equals(retCode)){
			recordDto.setIsSuccess("1");
		}else{
			recordDto.setIsSuccess("0");
		}
		recordDto.setErrorCode(retCode);
		JSONObject reqJson=new JSONObject();
		reqJson.put("iccid", iccid);
		reqJson.put("goodId", goodId);
		reqJson.put("st", st);
		recordDto.setReqInfo(reqJson.toString());
		recordDto.setRspInfo(resultJson.toString());
		recordDto.setAccessDate(new Date());
		recordDto.setParaName1("device");
		serverService.savaLogerToDb(recordDto);
		log.info("exit the method goodsorder");
	}
	
	
}
