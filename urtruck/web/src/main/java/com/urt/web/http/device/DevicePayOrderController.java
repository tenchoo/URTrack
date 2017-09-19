package com.urt.web.http.device;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
import com.urt.interfaces.pay.PayOrderService;
import com.urt.web.common.util.StringUtil;

/**
 * 创建订单
 * 
 * @author
 *
 */
@Controller
@RequestMapping("/payOrder")
public class DevicePayOrderController {
	private static final Logger log = Logger.getLogger(DevicePayOrderController.class);
	
	@Value("#{configProperties['device.PRIVATEATTACHUrl']}")
	private String PRIVATEATTACH;
	
	//private static final String PRIVATEATTACH = "http://vbtest.lenovomm.cn/common/applay_order.xhtml?c=payWeb";// 支付测试地址
	// PRIVATEATTACH="http://vbtest.lenovomm.cn/common/applay_order.xhtml?c=payWeb";//支付正式地址
	private static final String REALM = "gla.lenovo.com";
	private static final String PAYTYPE = "8";
	private static final String PRODUCTNAME = "小懂上网";
	private static final String SERVERNAME = "device+创建订单";
	private static final String SIGN_TYPE = "MD5";
	private static final String _INPUT_CHARSET = "Utf8";
	@Value("#{configProperties['device.padMD5Key']}")
	private String MD5KEY;
	//private static final String MD5KEY = "rehjghugxggtretreghgyogOU3351hbA";
	private static final String MERCHANTID = "5555555";

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
	private PayOrderService payOrderService;
	@Autowired
	GoodsReleaseService goodsReleaseService;
	@RequestMapping(value = "payOrder", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody JSONObject goodsorder(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "iccid", required = true) String iccid,
			@RequestParam(value = "goodId", required = true) String goodId,
			@RequestParam(value = "st", required = true) String st) throws Exception {
		log.info("enter the method goodsorder");
		JSONObject resultJson = new JSONObject();
		String retCode = "1";// -1 参数不全 -4 系统异常 1 正常 -6 没有可查询的产品 -7 非法iccid
								// -10为未交费
		String custId = "";
		String eString = "";
		iccid = iccid.replace("#", "B");
		try {
			JSONObject respCreateOrderRsult = new JSONObject();
			IccidLibDto iccidLib = userService.selectByIccid(iccid);
			custId = iccidLib.getCustid();
			if (StringUtil.isBlank(custId)) {
				retCode = "-7";
				respCreateOrderRsult.put("RESP_CODE", retCode);
				respCreateOrderRsult.put("RESP_DESC", "非法iccid");
				return respCreateOrderRsult;
			} else {				
				LaoSsAccountDto ss = new LaoSsAccountDto();
				LaoSsResourceDto res = new LaoSsResourceDto();
				res.setResCode("2");
				List<LaoSsResourceDto> ListRes = new ArrayList<LaoSsResourceDto>();
				ListRes.add(res);
				ss.setResources(ListRes);
				GoodsReleaseDto relpease = goodsReleaseService.findBygoodsReleaseId(Integer.valueOf(goodId));
				log.info("relpeaseiD" + relpease.getGoodsId());
				String tradeId = tradeService.addTrade(ss,custId, iccid, String.valueOf(relpease.getGoodsId()),goodId,"120", "0");
				Account account = deviceService.authSt(st);
				log.info(">>>>St>>>"+st);
				log.info("<<<<<<<<<<<<lenovoId>>>>>>>"+account+">>>>>ICCID>>>>>>>"+iccid);
				String price = relpease.getReleasePrice();
				log.info("relpeaseprice" + price);
				GoodsDto goods = goodsService.findByGoodsId(relpease.getGoodsId());
				if (tradeId != null) {
					TradeDto tradeDto = tradeService.queryTradeId(Long.parseLong(tradeId));
					log.info("trade参数======================" + tradeDto.toString());
					// tradeFeeSubService.addTradeFeeSub(tradeDto);//算费
					if (goods.getGoodsPrices() != null && Double.valueOf(goods.getGoodsPrices()) > 0) {
						tradeFeeSubService.addTradeFeeSub(tradeDto);
						TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
						Double fee = Double.parseDouble(tradeFeeSubDto.getFee());
						// fee = feee/100;
						tradeDto.setFee(tradeFeeSubDto.getFee());
						tradeService.updateTrade(tradeDto);
					} else {
						Double fee = Double.parseDouble(goods.getGoodsPrices().toString());
						tradeDto.setFee(String.valueOf(fee));
						tradeService.updateTrade(tradeDto);
					}
				}
				// 设置时间
				Date date = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				String createTime = format.format(date);
				// 设置签名
				StringBuffer sb = new StringBuffer();
				SortedMap<String, Object> sortMap = new TreeMap<>();
				sortMap.put("merchantId", MERCHANTID);
				sortMap.put("merchantOrderId", tradeId);
				sortMap.put("productName", goods.getGoodsName());
				sortMap.put("_input_charset", _INPUT_CHARSET);
				sortMap.put("timestamp", createTime);
				sortMap.put("payAmount", dataHanding(price));
				sortMap.put("userName", account.getUsername());
				sortMap.put("userId", account.getAccountID());
				sortMap.put("privateAttach", PRIVATEATTACH);
				Set<Entry<String, Object>> entrySet = sortMap.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					sb.append(entry.getKey() + "=" + entry.getValue() + "&");
				}
				StringBuffer append = sb.append("key" + "=" + MD5KEY);
				String string2 = append.toString();
				String sign = MD5Encoder(string2, _INPUT_CHARSET);

				resultJson.put("merchantId", MERCHANTID);
				resultJson.put("merchantOrderId", tradeId);
				resultJson.put("productName", goods.getGoodsName());
				resultJson.put("privateAttach", PRIVATEATTACH);
				resultJson.put("_input_charset", _INPUT_CHARSET);
				resultJson.put("sign_type", SIGN_TYPE);
				resultJson.put("timestamp", createTime);
				resultJson.put("payAmount", dataHanding(price));
				resultJson.put("userName", account.getUsername());
				resultJson.put("userId", account.getAccountID());
				resultJson.put("sign", sign);
				JSONObject resultOrder = payOrderService.createOrder(resultJson);
				
				log.info(">>>>>>>>>>>>>>>>>>>>>订单返回结果<<<<<<<<<<<<<<<<"+resultOrder.toJSONString());
				String tradeNo = (String) resultOrder.get("tradeNo");
				// 响应给调用者

				respCreateOrderRsult.put("RESP_CODE", (String) resultOrder.get("resultCode"));
				if (!eString.equals((String) resultOrder.get("errorMsg"))) {
					respCreateOrderRsult.put("RESP_DESC", (String) resultOrder.get("errorMsg"));
				} else {
					eString = "创建订单成功";
					respCreateOrderRsult.put("RESP_DESC", eString);
				}
				// 响应支付数据
				// 为sign进行加密

				SortedMap<String, String> respMap = new TreeMap<>();
				StringBuffer sbf = new StringBuffer();
				respMap.put("merchantId", MERCHANTID);
				respMap.put("merchantOrderId", tradeId);
				respMap.put("tradeNo", tradeNo);
				respMap.put("userName", account.getUsername());
				respMap.put("userId", account.getAccountID());
				respMap.put("payType", PAYTYPE);
				respMap.put("timestamp", createTime);
				respMap.put("_input_charset", _INPUT_CHARSET);
				respMap.put("sign_type", SIGN_TYPE);
				Set<Entry<String, String>> entrySet2 = respMap.entrySet();
				for (Entry<String, String> entry : entrySet2) {
					sbf.append(entry.getKey() + "=" + entry.getValue() + "&");
				}
				StringBuffer append1 = sb.append("key" + "=" + MD5KEY);
				String str3 = append1.toString();
				String sign1 = MD5Encoder(str3, _INPUT_CHARSET);
				JSONObject payResp = new JSONObject();
				payResp.put("merchantId", MERCHANTID);
				payResp.put("merchantOrderId", tradeId);
				payResp.put("tradeNo", tradeNo);
				payResp.put("userName", account.getUsername());
				payResp.put("userId", account.getAccountID());
				payResp.put("realm", REALM);
				payResp.put("payType", PAYTYPE);
				payResp.put("timestamp", createTime);
				payResp.put("_input_charset", _INPUT_CHARSET);
				payResp.put("productName", PRODUCTNAME);
				payResp.put("sign_type", SIGN_TYPE);
				payResp.put("sign", sign1);
				payResp.put("requestType", "1");
				// 响应给前台
				respCreateOrderRsult.put("payResp", payResp);
				log.info("exit the method goodsorder");
				return respCreateOrderRsult;
			}
		} catch (Exception e) {
			retCode = "-4";
			eString = "创建订单失败";
		}
		resultJson.put("RESP_CODE", retCode);
		resultJson.put("RESP_DESC", eString);

		// 保存日志
		LaoPeripheralSysAccessLogDto recordDto = new LaoPeripheralSysAccessLogDto();
		if (!StringUtil.isBlank(custId)) {
			recordDto.setCustId(Long.valueOf(custId));
		}
		String ip = request.getRemoteAddr();
		recordDto.setIpAddress(ip);
		recordDto.setUserName(iccid);
		recordDto.setServerName(SERVERNAME);
		if ("1".equals(retCode)) {
			recordDto.setIsSuccess("1");
		} else {
			recordDto.setIsSuccess("0");
		}
		recordDto.setErrorCode(retCode);
		JSONObject reqJson = new JSONObject();
		reqJson.put("iccid", iccid);
		reqJson.put("goodId", goodId);
		reqJson.put("st", st);
		recordDto.setReqInfo(reqJson.toString());
		recordDto.setRspInfo(resultJson.toString());
		recordDto.setAccessDate(new Date());
		recordDto.setParaName1("device");
		serverService.savaLogerToDb(recordDto);
		log.info("exit the method goodsorder");
		return resultJson;
	}

	/**
	 * Utf-8 MD5加密
	 * 
	 * @param s
	 * @param charset
	 * @return
	 */
	public static String MD5Encoder(String s, String charset) {
		try {
			byte[] btInput = s.getBytes(charset);
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < md.length; i++) {
				int val = ((int) md[i]) & 0xff;
				if (val < 16) {
					sb.append("0");
				}
				sb.append(Integer.toHexString(val));
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * 人民币元转换成分
	 * 
	 * @param yuan
	 * 
	 * @return
	 */
	public static String dataHanding(String yuan) {
		BigDecimal b1 = new BigDecimal(yuan);
		BigDecimal b2 = new BigDecimal(100);
		BigDecimal ret = b1.multiply(b2);
		String price = String.valueOf(ret.longValue());
		return price;
	}
	
	public static void main(String[] args) {
		System.out.println(dataHanding("40.05"));
	}
}
