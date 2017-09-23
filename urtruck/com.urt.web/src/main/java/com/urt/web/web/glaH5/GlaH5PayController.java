package com.urt.web.web.glaH5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.util.log.Log;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Goods.GoodsReleaseDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.Trade.TradeFeeSubDto;
import com.urt.dto.unicom.PaymentDto;
import com.urt.interfaces.Goods.GoodsReleaseService;
import com.urt.interfaces.Goods.GoodsService;
import com.urt.interfaces.Goods.PayService;
import com.urt.interfaces.Trade.TradeFeeSubService;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.remain.RemainService;
import com.urt.interfaces.unicom.PaymentService;
import com.urt.web.common.util.StringUtil;
import com.urt.web.util.EncryptUtil;
import com.urt.web.util.WeixinPayConstants;
import com.urt.web.util.WeixinUtil;

/**
 * 类说明：充值服务
 * 
 * @author sunhao
 * @date 2016年8月23日15:40:42
 */
@Controller
@RequestMapping("/glaH5")
public class GlaH5PayController {

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private TradeService tradeService;

	@Autowired
	private PayService payService;

	@Autowired
	private UserService userService;

	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private TradeFeeSubService tradeFeeSubService;

	@Autowired
	private RemainService remainService;
	
	@Autowired
	private GoodsReleaseService goodsReleaseService;

	/**
	 * 进入到充流量页面
	 * 
	 * @return
	 */
	@RequestMapping("/toChargeFlow")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/glaH5/toChargeFlow");
		String encodeStr = request.getParameter("data");
		String openId = null;
		// String accesstoken = null;
		try {
			String jsonString = EncryptUtil.aesDecrypt(encodeStr,"e2da530b3bdcb4f5");
			Gson gson = new Gson();
			HashMap map = gson.fromJson(jsonString, HashMap.class);
			openId = map.get("paraid").toString();
			// accesstoken = map.get("atid").toString();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		request.getSession().setAttribute("openId", openId);
		return mv;
	}

	/**
	 * 支付，创建订单
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@ResponseBody
	@RequestMapping("/toRealPay")
	public Map<String, String> toaliPay(String iccid, String goodsName,String payAmount, String goodsId, HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<String, String>();
		// *******************************生成订单，算费
		String totalFee = null;
		String tradeId = null;
		String custId = userService.getCustIdByIccid(iccid);
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		GoodsReleaseDto goodsReleaseDto = goodsReleaseService.findBygoodsReleaseId(Integer.valueOf(goodsId));
		try {
			tradeId = tradeService.addTrade(user,custId, iccid, goodsId.toString(),goodsReleaseDto.getGoodsId().toString(),"120", "0");
			if(StringUtil.isBlank(tradeId)){
				return paramMap;
			}
			// 算费
			TradeDto tradeDto = tradeService.queryTradeId(Long.parseLong(tradeId));
			if(goodsReleaseDto.getReleasePrice() != null && Double.parseDouble(goodsReleaseDto.getReleasePrice()) > 0){
				tradeFeeSubService.addTradeFeeSub(tradeDto);// 算费
				TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
				tradeDto.setFee(tradeFeeSubDto.getFee());
				tradeService.updateTrade(tradeDto);
				totalFee = WeixinUtil.getMoney(tradeFeeSubDto.getFee());
			}
			Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>微信订单生成:"+tradeId+"算费:"+totalFee);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		// *******************************封装微信需要的参数
		String nonce_str = WeixinUtil.getRandomString(32);
		String prepay_id = null;
		String ip = WeixinUtil.getIpAddr(request);// WeixinUtil.getIpAddr(request)118.144.186.210
		paramMap.put("appid", WeixinPayConstants.appid);
		paramMap.put("body", goodsName);
		paramMap.put("attach", custId);// 附加数
		paramMap.put("mch_id", WeixinPayConstants.mch_id);
		paramMap.put("nonce_str", nonce_str);
		paramMap.put("notify_url", WeixinPayConstants.notifyurl);
		paramMap.put("openid", request.getSession().getAttribute("openId").toString());
		paramMap.put("out_trade_no", tradeId);
		paramMap.put("spbill_create_ip", ip);
		paramMap.put("total_fee", totalFee);
		paramMap.put("trade_type", WeixinPayConstants.trade_type);
		Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>微信参数:"+paramMap.toString());
		//*******************************微信签名验证
		String sign = null;
		try {
			sign = WeixinUtil.getPayCustomSign(paramMap,WeixinPayConstants.api_key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		paramMap.put("sign", sign);
		String xml = WeixinUtil.ArrayToXml(paramMap);
		//*******************************微信prepay_id 预支付ID生成
		prepay_id = WeixinUtil.getPrepayId(xml);

		// *******************************向h5界面传递参数，从前台发起微信支付
		String timeStamp = System.currentTimeMillis() + "";
		timeStamp = timeStamp.substring(0, 10);// 微信只要精确到秒
		Map<String, String> payMap = new HashMap<String, String>();
		payMap.put("appId", WeixinPayConstants.appid);
		payMap.put("timeStamp", timeStamp);
		nonce_str = WeixinUtil.getRandomString(32);
		payMap.put("nonceStr", nonce_str);
		payMap.put("signType", "MD5");
		payMap.put("package", "prepay_id=" + prepay_id);
		String paySign = null;
		try {
			paySign = WeixinUtil.getPayCustomSign(payMap,WeixinPayConstants.api_key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		payMap.put("paySign", paySign);
		payMap.put("prepay_id", "prepay_id=" + prepay_id);
		Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>微信界面参数:"+payMap.toString());
		return payMap;
	}

	/**
	 * 支付完回调函数
	 * 
	 * @return
	 * @throws IOException
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	@RequestMapping("/payCallBack")
	public void aliPayCallBack(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>微信回调开始");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(hander(request));
		out.flush();
		out.close();
	}

	/**
	 * 功能描述：处理微信回调的内容，判断sign 验证信息
	 * 
	 * @author sunhao
	 * @date 2017年1月18日 上午10:15:40
	 * @param @param request
	 * @param @return
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("deprecation")
	public String hander(HttpServletRequest request) {
		StringBuilder notityXml = new StringBuilder();
		BufferedReader bufferedReader = null;
		String inputLine;
		try {
			bufferedReader = request.getReader();
			while ((inputLine = bufferedReader.readLine()) != null)
				notityXml.append(inputLine);
			if (bufferedReader != null)
				bufferedReader.close();
			if (notityXml.length() < 10) {
				return WeixinUtil.sendXml("FAIL", "xml为空");
			}
			Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>微信回调参数返回成功！" + notityXml);

			JSONObject json = WeixinUtil.xml2JSON(notityXml.toString());
			Map<String, String> resultMap = JSON.parseObject(
					json.getString("xml").replace("[", "").replace("]", ""),
					new TypeReference<Map<String, String>>() {
					});
			String sign = resultMap.get("sign");
			resultMap.remove("sign");
			//*******************************微信签名验证
			String resign = WeixinUtil.getPayCustomSign(resultMap,WeixinPayConstants.api_key);
			
			//*******************************验证通过 扣费，用户归档
			if (sign.equals(resign)) {
				// 得到参数
				String custId = resultMap.get("attach");
				String tradeId = resultMap.get("out_trade_no");
				// 扣费
				TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
				HashMap<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("channelCustId", custId);// 渠道客户ID
				paraMap.put("tradeTypeCode", "120");// 业务类型编码
				paraMap.put("recvFee", tradeFeeSubDto.getFee());// 缴费金额
				paraMap.put("fee", tradeFeeSubDto.getOldfee());// 应收费用
				paraMap.put("discntFee", tradeFeeSubDto.getFee());// 优惠后费用
				paraMap.put("realFee", tradeFeeSubDto.getFee());// 实收费用
				paraMap.put("payTag", "1");// 0：未缴费 1：用户已缴费 2：客户已缴费
//				paraMap.put("payOrderId", resultMap.get("transaction_id"));
//				paraMap.put("payDate", new Date());
				paraMap.put("tradeId", tradeId);
				paraMap.put("goodsId", tradeFeeSubDto.getGoodsId());
				
				if(StringUtils.isNotBlank(tradeId)){
					TradeDto trade = tradeService.queryTradeId(Long.parseLong(tradeId));
					if(trade != null) paraMap.put("userId", trade.getUserId());
				}
				int rstFlg = remainService.charge(paraMap);// 扣费
				
				PaymentDto payment = new PaymentDto();
				payment.setOrderId(resultMap.get("transaction_id"));//订单号
				payment.setPayType(2);
				//用户归档操作
				if(rstFlg == 0){
					String result = userService.userArchiving(tradeId);// 用户归档
					Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>微信回调用户归档:" + result);
					if(("ok").equals(result)){
						tradeFeeSubService.changePayTag(tradeId, payment);
						return WeixinUtil.sendXml("SUCCESS", "OK");
					}else{
						return WeixinUtil.sendXml("FAIL", "用户归档操作失败");
					}
				}else{
					return WeixinUtil.sendXml("FAIL", "扣费操作失败");
				}
				
			} else {
				Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>微信签名不一致");
				return WeixinUtil.sendXml("FAIL", "签名不一致");
			}

		} catch (Exception e) {
			Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>微信回调出现异常");
			return WeixinUtil.sendXml("FAIL", "获取微信回调失败");
		}
	}

	/**
	 * 支付结果展示
	 * 
	 * @return
	 * @throws IOException
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	@RequestMapping("/payShow")
	public ModelAndView payResult(String goodsName, String iccid, String payAmount)
			throws IOException {
		ModelAndView mv = new ModelAndView("/glaH5/payResultShow");
		mv.addObject("payAmount", payAmount);
		mv.addObject("iccid", iccid);
		mv.addObject("goodsName", goodsName);
		return mv;
	}
}
