package com.urt.web.web.glaH5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.urt.common.enumeration.ConstantEnum;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.Goods.GoodsReleaseDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.Trade.TradeFeeSubDto;
import com.urt.dto.unicom.PaymentDto;
import com.urt.interfaces.Goods.GoodsOrderService;
import com.urt.interfaces.Goods.GoodsReleaseService;
import com.urt.interfaces.Goods.GoodsService;
import com.urt.interfaces.Goods.PayService;
import com.urt.interfaces.Trade.TradeFeeSubService;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.authority.LaoSsAccountService;
import com.urt.interfaces.authority.LoginService;
import com.urt.interfaces.remain.RemainService;
import com.urt.interfaces.traffic.TrafficQueryService;
import com.urt.interfaces.unicom.DeviceService;
import com.urt.interfaces.unicom.PaymentService;
import com.urt.interfaces.webDesign.WebDesignService;
import com.urt.web.common.util.AccountCookie;
import com.urt.web.common.util.ActionUtil;
import com.urt.web.common.util.ICCID;
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
@RequestMapping("/glaH5AppPay")
public class GlaH5AppPaymentController {
	private static  final Logger Log=Logger.getLogger(GlaH5AppPaymentController.class);
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private PayService payService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private TradeFeeSubService tradeFeeSubService;

	@Autowired
	private RemainService remainService;
	
	@Autowired
	private GoodsReleaseService goodsReleaseService;
	
	@Autowired
	private DeviceService deviceService;
	
	@Value("#{configProperties['charge.pcwebPayRequestUrl']}")
	private String pcwebPayRequestUrl;
	
	@Autowired
	private GoodsOrderService goodsOrderService;
	
	@Autowired
	private LaoSsAccountService accountService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	TrafficQueryService trafficQueryService;
	
	@Autowired
	WebDesignService webDesignService;
	/**
	 * 进入到充流量页面
	 * @return
	 */
	@RequestMapping("/toChargeView")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/newH5/charge");
		//登陆之后，查询用户绑定的iccid
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		
		Log.info(">>>>>>>>>>>>>>>>>>>>>>>进入到充流量页面 <<<<<<<<<<<<<<<<<<<<<<<<<<<<"+user);
		if(user != null){
			Long custId = user.getCustId();
			Log.info(">>>>>>>>>>>>>>>>>>>>>>>进入到充流量页面 <<<<<<<<<<<<<<<<<<<<<<<<<<<<"+user.getLoginName());
			if(custId != null){
				List<String> iccidList = userService.getIccidByCustId(custId);
				request.getSession().setAttribute("glaIccidList",iccidList);
			}
		}
		
		//gla 微信支付充值准备
		if(request.getSession().getAttribute("openId") == null && request.getParameter("data") != null){
			String encodeStr = request.getParameter("data");
			Log.info(">>>>>>>>>>>>>>>>encodeStr="+encodeStr);
			String openId = null;
			try {
				String jsonString=EncryptUtil.aesDecrypt(encodeStr, "e2da530b3bdcb4f5");
				Log.info(">>>>>>>>>>>>>>>>jsonString="+jsonString);
				Gson gson=new Gson();
				HashMap map1 = gson.fromJson(jsonString, HashMap.class);
				openId=map1.get("paraid").toString();
				request.getSession().setAttribute("openId", openId);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			Log.info(">>>>>>>>>>>>>>>>openId:"+openId);
		}
		
		//扫描二维码连接 :处理
		String iccid = request.getParameter("iccid");
		String custid = request.getParameter("custId");
		String sign = request.getParameter("sign");
		String openId = request.getParameter("openid");
		String logo = request.getParameter("logo");
		if(StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(iccid) && StringUtils.isNotBlank(custid) && StringUtils.isNotBlank(sign)){
			Log.info(">>>>>>>>>>>>>>>>微信扫描参数<<<<<<<<<<<<<<<<<<<<<<<<<<<<：openId:"+openId+"iccid:"+iccid+"custId:"+custid+"sign:"+sign+"logo:"+logo);
			request.getSession().setAttribute("openId", openId);
			request.getSession().setAttribute("logo", logo);
			if(iccid.length() == 19 && iccid.contains("898606")){
				iccid = ICCID.replaceLastChar(iccid);
				boolean flag= ifIccidOfGla(iccid);
				if(!flag){ //如果不是gla系统的用户
					mv = new ModelAndView("redirect:../glaH5AppActive/index");
				}
			}else{//新功能  用户查询时候的iccid 带到充值界面
				if(iccid.length() < 20){
					iccid = userService.getIccid(iccid);
				}
			}
			boolean checkSign = deviceService.checkSign(custid, iccid, sign, logo);//验证参数
			Log.info(">>>>>>>>>>>>>>>>checkSign:"+checkSign);
			if(checkSign){//如果正确，登陆
				List<LaoSsAccountDto> accountList = accountService.queryByCustId(Long.parseLong(custid));
				if(accountList != null && accountList.size() > 0){
					LaoSsAccountDto laoSsAccountDto = accountList.get(0);
					// 直接登录
					AuthenticationToken token=new UsernamePasswordToken(laoSsAccountDto.getLoginName(),laoSsAccountDto.getPlainPassword());
					SecurityUtils.getSubject().login(token);
					
					laoSsAccountDto = loginService.getUserInfo(laoSsAccountDto.getLoginName());
					request.getSession().setAttribute("sessionUser", laoSsAccountDto);
				}
			}
		}
		//测试数据
		logo = "2";
		custid = "3070740340009007";
		sign = webDesignService.getSign(custid);
		System.out.println("+++++++++++++++++++++++: " + custid + ":" + logo + ":" + sign);
		
		//企业网站定制扫描二维码处理
		if(StringUtil.isNotBlank(custid) && "2".equals(logo) && StringUtil.isNotBlank(sign)){
			request.getSession().setAttribute("openId", openId);
			request.getSession().setAttribute("logo", logo);
			boolean checkSign = deviceService.checkSign(custid, null, sign, logo);//验证参数
			Log.info(">>>>>>>>>>>>>>>>checkSign:"+checkSign);
			if(checkSign){//如果正确，登陆
				List<LaoSsAccountDto> accountList = accountService.queryByCustId(Long.parseLong(custid));
				if(accountList != null && accountList.size() > 0){
					LaoSsAccountDto laoSsAccountDto = accountList.get(0);
					// 直接登录
					AuthenticationToken token=new UsernamePasswordToken(laoSsAccountDto.getLoginName(),laoSsAccountDto.getPlainPassword());
					SecurityUtils.getSubject().login(token);
					
					laoSsAccountDto = loginService.getUserInfo(laoSsAccountDto.getLoginName());
					request.getSession().setAttribute("sessionUser", laoSsAccountDto);
				}
				List<Map<String, Object>> lists = webDesignService.selectAllByCustId(custid);
				if(lists != null && lists.size() > 0){
					Map<String, Object> webDesign = lists.get(0);
					request.getSession().setAttribute("webDesign", webDesign);
					Log.info(">>>>>>>>>>>>>>>>>>>>>查询所有的定制信息" + webDesign);
				}
			}
			
		}
		
		LaoSsAccountDto usr = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		String custId = String.valueOf(ConstantEnum.CUSTID.getCode());
		String operatorId = "";
		if(StringUtils.isNotBlank(iccid)){
			if(iccid.length() == 19 && iccid.contains("898606")){
				iccid = ICCID.replaceLastChar(iccid);
				boolean flag= ifIccidOfGla(iccid);
				if(!flag){ //如果不是gla系统的用户,iccid置为空，让用户重新输入
					iccid = "";
				}
			}else{
				if(iccid.length() < 20){
					iccid = userService.getIccid(iccid);
				}
			}
		}
		if(StringUtil.isNotBlank(iccid)){
			//通过iccid查询laoUser
			LaoUserDto laoUserDto = userService.getLaoUserDtoByIccid(iccid);
			custId = laoUserDto.getChannelCustId().toString();
			operatorId = String.valueOf(laoUserDto.getOperatorsId());
			// 没登陆，展示联想公用custId对应的产品，不是管理员客户,并且卡和客户不一致,则展示普通资费
			if (usr == null
					|| (!ActionUtil.ifSuperUser(request) && !trafficQueryService.bIsLegalIccId(iccid, usr.getCustId()))) {
				custId = String.valueOf(ConstantEnum.CUSTID.getCode());
			}
		}
		if("".equals(operatorId) || operatorId == null){
			operatorId = "1";
		}
		Log.info(">>>>>>>>>>>>>>>>客户产品查询<<<<<<<<<<<<<<<<<<<<<<<<<<<<custId: " + custId);
		List<GoodsDto> goodsList = goodsOrderService.queryH5LaoGoods(custId, operatorId, null, null);
		mv.addObject("goodsList", goodsList);
		/*if (user == null) {
			String custId = String.valueOf(ConstantEnum.CUSTID.getCode());
			List<GoodsDto> goodsList = goodsOrderService.queryLaoGoods(custId, "1", null, null);
			mv.addObject("goodsList", goodsList);
		}else{
			String custId = user.getCustId().toString();
			List<GoodsDto> goodsList = goodsOrderService.queryLaoGoods(custId, "1", null, null);
			mv.addObject("goodsList", goodsList);
		}*/
		mv.addObject("ssiccid", iccid);
		return mv;
	}
	
	/**
	 * 跳转确认支付界面
	 * @return
	 */
	@RequestMapping("/toComfirm")
	public ModelAndView  aliPay(HttpSession session, HttpServletRequest request, @RequestParam(value="goodsId", required = false)String goodsId, @RequestParam(value="goodsReleaseId", required = false)String goodsReleaseId, @RequestParam(value="iccid", required = false)String iccid){
		ModelAndView mv = new ModelAndView("/newH5/toalipay");
		if(iccid.length() == 19 && iccid.contains("898606")){
			iccid = ICCID.replaceLastChar(iccid);
		}
		mv.addObject("iccid", iccid);
		if(StringUtil.isNoneBlank(goodsId)){
			GoodsDto findByGoodsId = goodsService.findByGoodsId(Long.parseLong(goodsId));
			mv.addObject("goodsName", findByGoodsId.getGoodsName());
		}
		if(StringUtil.isNoneBlank(goodsReleaseId)){
			GoodsReleaseDto product = goodsReleaseService.findBygoodsReleaseId(Integer.parseInt(goodsReleaseId));
			mv.addObject("product",product);
		}
		mv.addObject("goodsReleaseId",goodsReleaseId);
		
		return mv;
	}
	/**
	* 功能描述：判断iccid 是否属于Gla
	* @author sunhao
	* @date 2017年3月4日 下午12:13:23
	* @param @return
	* @return boolean
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/ifIccidOfGla")
	public boolean ifIccidOfGla(String iccid){
		boolean flag = false ;
		if(iccid.length() == 19 && iccid.contains("898606")){
			iccid = ICCID.replaceLastChar(iccid);
		}
		LaoUserDto laoUserDtoByIccid = userService.getLaoUserDtoByIccid(iccid);
		if(laoUserDtoByIccid != null){
			flag = true;
		}
		return flag;
	}
	
	/**
	* 功能描述：根据iccid 后面8位查询完整的iccid
	* @author sunhao
	* @date 2017年3月4日 下午12:13:23
	* @param @return
	* @return boolean
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/queryIccid")
	public String queryIccid(String iccid){
		return userService.getIccid(iccid);
	}
	
	/**
	 * 支付，创建订单 
	 * @return
	 */
	@RequestMapping("/torealPay")
	public ModelAndView  toaliPay(HttpServletRequest request, HttpServletResponse response, HttpSession session, String iccid, String goodsReleaseId, String payWay){
		Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>创建订单生成:payWay"+payWay+"|iccid:"+iccid+"|goodReleaseId:"+goodsReleaseId);
		String goodsId = null;
		String payAmount = null;
		String flowSize = null;
		if(StringUtil.isNoneBlank(goodsReleaseId)){
			GoodsReleaseDto product = goodsReleaseService.findBygoodsReleaseId(Integer.parseInt(goodsReleaseId));
			if(product != null){
				goodsId = product.getGoodsId().toString();
				if(StringUtil.isNoneBlank(goodsId)){
					GoodsDto findByGoodsId = goodsService.findByGoodsId(Long.parseLong(goodsId));
					flowSize = findByGoodsId.getGoodsName();
				}
				payAmount = product.getReleasePrice();
			}
		}
		
		Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>payAmount:"+payAmount);
		ModelAndView mv = new ModelAndView("/newH5/pay");
		Map<String,String> map = null;
		if(iccid.length() == 19 && iccid.contains("898606")){
			iccid = ICCID.replaceLastChar(iccid);
		}
		boolean flag= ifIccidOfGla(iccid);
		AccountCookie accountCookie = new AccountCookie(request,response);
		accountCookie = accountCookie.getCookieAccount();
		String userId = accountCookie.getUuid();
		String userName = accountCookie.getUname();
		String lpsust=(String) session.getAttribute("lpsust");
		//支付逻辑判断
		if(("0").equals(payWay)){//支付宝
			
			if(flag){ //如果是gla系统的用户，走的是gla系统的支付宝方法
				map = zhifbGlaPay(goodsId, iccid, flowSize, payAmount, goodsReleaseId, request, userId, userName, lpsust);
				mv.addAllObjects(map);
			}
			if(map == null || map.size() < 0){
				mv = new ModelAndView("redirect:/glaH5AppPay/toChargeView");
			}else{
				mv.addObject("pcwebPayRequestUrl", map.get("privateAttach"));
				map.put("privateAttach", "");      //将privateAttach清空
				mv.addObject("params", map);
				mv.addObject("payWay", "0");
			}
		}else if(("1").equals(payWay)){//微信支付
			if(flag){ //如果是gla系统的用户，走的是gla系统微信支付方法
				map = weiXinGlaPay(goodsId, iccid, flowSize, payAmount, goodsReleaseId, request);
				mv.addAllObjects(map);
			}
			if(map == null || map.size() < 0){
				mv = new ModelAndView("redirect:/glaH5AppPay/toChargeView");
			}else{
				mv.addObject("payWay", "1");
				mv.addObject("params", map);
			}
		}
		Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>map:"+map.toString());
		return mv;
	}
	
	/**
	* 功能描述：gla微信支付
	* @author sunhao
	* @date 2017年2月13日 下午6:04:37
	* @param @param iccid
	* @param @param goodsName
	* @param @param payAmount
	* @param @param goodsId
	* @param @param request
	* @param @return
	* @return Map<String,String>
	* @throws
	 */
	public Map<String, String> weiXinGlaPay(String goodsId, String iccid, String goodsName,String payAmount, String goodReleaseId, HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<String, String>();
		// *******************************生成订单，算费
		if(iccid.length() == 19 && iccid.contains("898606")){
			iccid = ICCID.replaceLastChar(iccid);
		}
		String totalFee = null;
		String tradeId = null;
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		String custId = null;
		if(user != null && user.getCustId() != null){
			custId = user.getCustId().toString();
		}else{
			LaoUserDto laoUser = userService.getLaoUserDtoByIccid(iccid);
			if(laoUser != null){
				if(laoUser.getCustId() != null){
					custId = laoUser.getCustId().toString();
				}else if(laoUser.getChannelCustId() != null){
					custId = laoUser.getChannelCustId().toString();
				}
			}
		}
		GoodsReleaseDto goodsReleaseDto = goodsReleaseService.findBygoodsReleaseId(Integer.valueOf(goodReleaseId));
		Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>微信订单生成:custId"+custId+"|iccid:"+iccid+"|goodReleaseId:"+goodReleaseId);
		try {
			if(StringUtil.isNotBlank(goodsId) && goodsId.equals(goodsReleaseDto.getGoodsId().toString())){
				tradeId = tradeService.addTrade(user,custId, iccid, goodsId, goodReleaseId,"120", "0");
			}
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
		paramMap.put("notify_url", WeixinPayConstants.weixinPayBackurl);
		paramMap.put("openid", (String)request.getSession().getAttribute("openId"));
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
		
		payMap.put("iccid", iccid);
		payMap.put("goodsName", goodsName);
		payMap.put("payAmount", payAmount);
		payMap.put("tradeId", tradeId);
		payMap.put("goodReleaseId", goodReleaseId);
		return payMap;
	} 
	/**
	* 功能描述：gla支付宝支付
	* @author sunhao
	* @date 2017年2月17日 下午4:50:12
	* @param @param iccid
	* @param @param goodsName
	* @param @param payAmount
	* @param @param goodsId
	* @param @param request
	* @param @return
	* @return Map<String,String>
	* @throws
	 */
	public Map<String, String> zhifbGlaPay(String goodsId, String iccid, String goodsName,String payAmount, String goodReleaseId, HttpServletRequest request, String userId, String userName, String lpsust ) {
		Map<String, String> params = null;
		// *******************************生成订单，算费
		if(iccid.length() == 19 && iccid.contains("898606")){
			iccid = ICCID.replaceLastChar(iccid);
		}
		String totalFee = null;
		String tradeId = null;
		String custId = userService.getCustIdByIccid(iccid);
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		GoodsReleaseDto goodsReleaseDto = goodsReleaseService.findBygoodsReleaseId(Integer.valueOf(goodReleaseId));
		try {
			if(StringUtil.isNotBlank(goodsId) && goodsId.equals(goodsReleaseDto.getGoodsId().toString())){
				tradeId = tradeService.addTrade(user,custId, iccid, goodsId, goodReleaseId,"120", "0");
			}
			if(StringUtil.isBlank(tradeId)){
				return params;
			}
			// 算费
			TradeDto tradeDto = tradeService.queryTradeId(Long.parseLong(tradeId));
			GoodsDto goodsDto = goodsService.findByGoodsId(tradeDto.getGoodsId());
			if (goodsDto.getGoodsPrices() != null&& Double.valueOf(goodsDto.getGoodsPrices()) > 0) {
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
		
		//请求支付
		params = new HashMap<String,String>();
		params.put("payType", "1");//支付宝pcweb支付
		params.put("realm", "gla.lenovo.com");
		params.put("payAmount", payAmount.toString());
		params.put("stName", lpsust);
		params.put("productName", "联想懂的上网");
		params.put("merchantOrderId", tradeId);
		params.put("userName", userName);
		params.put("userId", userId);
		params.put("privateAttach", pcwebPayRequestUrl);
		return params;
	}
	/**
	 * 支付宝回调函数
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@RequestMapping("/aliPayCallBack")
	public ModelAndView  aliPayCallBack(HttpServletRequest request, HttpServletResponse response, HttpSession session,PaymentDto payment) throws NumberFormatException, Exception{
		String orderId = payment.getMerchantOrderId();
		Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>alipayCallBack: orderId " + orderId);
		TradeDto tradeDto = tradeService.queryTradeId(Long.parseLong(orderId));
		if(tradeDto != null){
			ModelAndView mv = new ModelAndView("/Goods/paySuccess");
			Map<String, Object> result = payService.aliPayCallBacktMap(payment);
			Boolean flag = (Boolean) result.get("recode");
			if(!flag){
				mv = new ModelAndView("/Goods/payFail");
			}		
			mv.addObject("iccid",  result.get("iccid"));
			mv.addObject("payAmount", result.get("payAmount"));
			return mv;
		}else{
			ModelAndView mv = new ModelAndView("/glaH5/paySuccess");
			Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>wechat alipayCallback: 参数： " + payment.toString());
			Map<String,Object> result = paymentService.aliPayCallBack(payment);
			Boolean flag = (Boolean) result.get("recode");
			if(!flag){
				mv = new ModelAndView("/glaH5/payFail");
			}
			mv.addObject("iccid",  result.get("iccid"));
			mv.addObject("flowSize", result.get("flowSize"));
			mv.addObject("payAmount", result.get("payAmount"));
			return mv;
		}
		

	}
	
	/**
	 * 微信回调函数
	 * 
	 * @return
	 * @throws IOException
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	@RequestMapping("/payCallBack")
	public void aliPayCallBack(HttpServletRequest request,HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(hander(request));
		out.flush();
		out.close();
	}
	
	/**
	 * 功能描述：处理微信回调的内容，判断sign 验证信息
	 * @author sunhao
	 * @date 2017年1月18日 上午10:15:40
	 * @param @param request
	 * @param @return
	 * @return String
	 * @throws
	 */
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
				Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>微信回调处理失败！" + notityXml);
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
				String payOrderId = resultMap.get("transaction_id");
				return userService.callBackOfWeixin(payOrderId, custId, tradeId);
				
			} else {
				Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>微信签名不一致");
				return WeixinUtil.sendXml("FAIL", "签名不一致");
			}

		} catch (Exception e) {
			Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>微信回调出现异常");
			e.printStackTrace();
			return WeixinUtil.sendXml("FAIL", "获取微信回调失败");
		}
	}
	
	/**
	 * 微信支付结果展示
	 * @return
	 * @throws IOException
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	@RequestMapping("/payShow")
	public ModelAndView payResult(String goodsName, String iccid, String payAmount, String tradeId, String goodReleaseId)throws IOException {
		Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>微信支付结果展示");
		if(iccid.length() == 19 && iccid.contains("898606")){
			iccid = ICCID.replaceLastChar(iccid);
		}
		TradeFeeSubDto tredeFee;
		ModelAndView mv = new ModelAndView("/newH5/payResultShow");
		try {
			tredeFee = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
			if(tredeFee != null){
				mv.addObject("startTime", tredeFee.getAcceptDate());
				mv.addObject("orderId", tredeFee.getPayOrderId());
			}
			
			GoodsReleaseDto findBygoodsReleaseId = goodsReleaseService.findBygoodsReleaseId(Integer.parseInt(goodReleaseId));
			if(findBygoodsReleaseId != null){
				String unit = findBygoodsReleaseId.getUnit(); //0是月1是天
				if(("0").equals(unit)){
					mv.addObject("limitData", findBygoodsReleaseId.getReleaseCycle()+"月");
				}else if(("1").equals(unit)){
					mv.addObject("limitData", findBygoodsReleaseId.getReleaseCycle()+"天");
				}
				if(StringUtils.isBlank(findBygoodsReleaseId.getGoodsName())){
					GoodsDto findByGoodsId = goodsService.findByGoodsId(findBygoodsReleaseId.getGoodsId());
					if(findByGoodsId != null){
						mv.addObject("goodsName", findByGoodsId.getGoodsName());
					}
				}else{
					mv.addObject("goodsName", findBygoodsReleaseId.getGoodsName());
				}
			}
			
			mv.addObject("payAmount", payAmount);
			mv.addObject("iccid", iccid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	public static void main(String[] args) throws Exception {
		String notityXml = "<xml><appid><![CDATA[wxc282e33f9192be5d]]></appid><attach><![CDATA[1031354340000001]]></attach><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1277879901]]></mch_id><nonce_str><![CDATA[c03yzn2ulvq96nrgyu5nm4fm2qh18bv4]]></nonce_str><openid><![CDATA[oORPawhJelaW-NneIrwR5ya3s7Zg]]></openid><out_trade_no><![CDATA[1011516440417974]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[4E2E1B5AB60955CD3F92BF84A9E7172B]]></sign><time_end><![CDATA[20170524151652]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4008942001201705242362824640]]></transaction_id></xml>";
		JSONObject json = WeixinUtil.xml2JSON(notityXml);
		Map<String, String> resultMap = JSON.parseObject(
				json.getString("xml").replace("[", "").replace("]", ""),
				new TypeReference<Map<String, String>>() {
				});
		String sign = resultMap.get("sign");
		System.out.println(sign);
		resultMap.remove("sign");
		//*******************************微信签名验证
		String resign = WeixinUtil.getPayCustomSign(resultMap,WeixinPayConstants.api_key);
		
		//*******************************验证通过 扣费，用户归档
		if (sign.equals(resign)) {
			// 得到参数
			String custId = resultMap.get("attach");
			String tradeId = resultMap.get("out_trade_no");
			String payOrderId = resultMap.get("transaction_id");
			
		}
	}
}
