package com.urt.web.web.Goods;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.urt.common.enumeration.ConstantEnum;
import com.urt.common.util.ResultMsg;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.Goods.GoodsReleaseDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.Goods.OperatorsDto;
import com.urt.dto.Goods.PersonalOrderDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.Trade.TradeFeeSubDto;
import com.urt.interfaces.Goods.GoodsOrderService;
import com.urt.interfaces.Goods.GoodsReleaseService;
import com.urt.interfaces.Goods.GoodsService;
import com.urt.interfaces.Goods.OperatorsService;
import com.urt.interfaces.Goods.PayService;
import com.urt.interfaces.ShangHaiCMC.SI_ActivateAPN;
import com.urt.interfaces.ShangHaiCMC.SI_OrderPackage;
import com.urt.interfaces.Trade.TradeFeeSubService;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.remain.RemainService;
import com.urt.interfaces.traffic.TrafficQueryService;
import com.urt.web.common.util.ActionUtil;
import com.urt.web.common.util.ICCID;

@Controller
@RequestMapping("/laouser")
/**
 * 
 * @author zhaoyf
 *
 */
public class GoodsOrderController {

	@Autowired
	private GoodsOrderService goodsOrderService;
	@Autowired
	private GoodsReleaseService goodsReleaseService;
	@Autowired
	private TradeService tradeService;
	@Autowired
	private UserService userService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private TradeFeeSubService tradeFeeSubService;
	@Autowired
	private PayService payService;
	@Autowired
	TrafficQueryService trafficQueryService;
	@Autowired
	private RemainService remainService;
	@Autowired
	private OperatorsService oprtatorsService;

	Logger log = Logger.getLogger(getClass());

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryLaoUser")
	public String queryLaoUser(HttpServletRequest request) {
		List<LaoUserDto> laoUserDtos = new ArrayList<LaoUserDto>();
		request.setAttribute("laoUsers", laoUserDtos);
		return "Goods/goodsOrder";
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryLaoUsers")
	public String laoUsers(HttpServletRequest request) {
		List<LaoUserDto> laoUserDtos = goodsOrderService.queryLaoUser();
		request.setAttribute("laoUsers", laoUserDtos);
		return "Goods/userTables";
	}

	/**
	 * 
	 * @param request
	 * @param custId
	 * @param operatorsId
	 * @param iccid
	 * @param value1
	 * @param value2
	 * @return
	 */
	@RequestMapping("/queryLaoUserCon")
	public String queryLaoUserCon(HttpServletRequest request,
			@RequestParam(value = "custId", required = false) String custId,
			@RequestParam(value = "operatorsId", required = false) String operatorsId,
			@RequestParam(value = "iccid", required = false) String iccid,
			@RequestParam(value = "value1", required = false) String value1,
			@RequestParam(value = "value2", required = false) String value2) {
		List<LaoUserDto> laoUserDtos = goodsOrderService.queryLaoUserCon(custId, operatorsId, iccid, value1, value2);
		request.setAttribute("custId", custId);
		request.setAttribute("operatorsId", operatorsId);
		request.setAttribute("value1", value1);
		request.setAttribute("value2", value2);
		request.setAttribute("laoUsers", laoUserDtos);
		return "Goods/userTables";
	}

	/**
	 * 展示选购的产品
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/order")
	public String order(HttpServletRequest request) {
		HttpSession session = request.getSession();
		/*
		 * String custId = (String)session.getAttribute("custId"); String
		 * operatorsId = (String)session.getAttribute("operatorsId"); String
		 * value1 = (String)session.getAttribute("value1"); String value2 =
		 * (String)session.getAttribute("value2");
		 */
		Object attribute = session.getAttribute("iccidList");

		List<String> list = new ArrayList<String>();
		list = (ArrayList<String>) attribute;
		String iccid = list.get(0);
		// IccidLibDto iccidLibDto = userService.selectByIccid(iccid);
		LaoUserDto laoUserDtoByIccid = userService.getLaoUserDtoByIccid(iccid);
		/*
		 * String custId = iccidLibDto.getCustid(); String operatorsId =
		 * iccidLibDto.getOperators(); String value1 = iccidLibDto.getValue1();
		 * String value2 = iccidLibDto.getValue2();
		 */
		String custId = laoUserDtoByIccid.getChannelCustId().toString();
		String operatorsId = laoUserDtoByIccid.getOperatorsId().toString();
		String value1 = laoUserDtoByIccid.getValue1();
		String value2 = laoUserDtoByIccid.getValue2();
		session.setAttribute("custId", custId);

		List<GoodsDto> laoGoodsDtos = goodsOrderService.queryLaoGoods(custId, operatorsId, value1, value2);
		request.setAttribute("laoGoodsDtos", laoGoodsDtos);
		session.removeAttribute("operatorsId");
		session.removeAttribute("value1");
		session.removeAttribute("value2");

		return "Goods/goodsDetail";
	}

	/**
	 * 选购产品
	 * 
	 * @param request
	 * @param boxIds
	 * @param custId
	 * @param operatorsId
	 * @param value1
	 * @param value2
	 * @return
	 */
	@RequestMapping("/batchorder")
	@ResponseBody
	public Map<String, Object> batchOrder(HttpServletRequest request,
			@RequestParam(value = "boxIds[]", required = false) List<String> boxIds) {
		/*
		 * @RequestParam(value="custId",required=false) String custId,
		 * 
		 * @RequestParam(value="operatorsId",required=false) String operatorsId,
		 * 
		 * @RequestParam(value="value1",required=false) String value1,
		 * 
		 * @RequestParam(value="value2",required=false) String value2){
		 */
		Map<String, Object> icm = new HashMap<String, Object>();
		try {
			HttpSession session = request.getSession();
			session.setAttribute("iccidList", boxIds);
			/*
			 * session.setAttribute("custId",custId);
			 * session.setAttribute("operatorsId",operatorsId);
			 * session.setAttribute("value1",value1);
			 * session.setAttribute("value2",value2);
			 */
			icm.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			icm.put("success", false);
		}
		return icm;
	}

	/**
	 * 和运营商交互，完成订购
	 * 
	 * @param request
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/orderSubmit")
	@ResponseBody
	public Map<String, Object> orderSubmit(HttpServletRequest request,
			@RequestParam(value = "goodsId", required = false) String goodsId) throws Exception {
		HashMap<String, Object> staMap = new HashMap<String, Object>();
		String[] ids = goodsId.split(",");
		HttpSession session = request.getSession();
		List<String> iccidList = (List<String>) session.getAttribute("iccidList");
		String custId = (String) session.getAttribute("custId");
		System.out.println(goodsId + "   " + iccidList + "   " + custId);
		// 清除session
		session.removeAttribute("iccidList");
		session.removeAttribute("custId");
		String orderId = null;
		Integer successCount = 0;
		Integer failCount = 0;
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		for (String iccid : iccidList) {
			String tradeId = tradeService.addTrade(user, custId, orderId, iccid, ids[0], ids[1], "120", "0");// 生成订单
			if (tradeId == null) {
				failCount++;
				continue;
			}
			if (orderId == null) {
				orderId = tradeId;
			}

			TradeDto tradeDto = tradeService.queryTradeId(Long.parseLong(tradeId));

			/*
			 * GoodsDto goodsDto = goodsService.findByGoodsId(Long.parseLong());
			 */
			GoodsReleaseDto goodsReleaseDto = goodsReleaseService.findBygoodsReleaseId(Integer.valueOf(ids[1]));
			if (goodsReleaseDto.getReleasePrice() != null && Double.valueOf(goodsReleaseDto.getReleasePrice()) > 0) {
				tradeFeeSubService.addTradeFeeSub(tradeDto);// 算费
				TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
				// String fee = tradeFeeSubDto.getFee().toString();
				tradeDto.setFee(tradeFeeSubDto.getFee());
			}
			// 扣费
			TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
			HashMap<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("channelCustId", tradeDto.getChannalCustId());// 渠道客户ID
			paraMap.put("tradeTypeCode", "120");// 业务类型编码, 订购120
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
				failCount++;
				continue;
			}
			// 用户归档操作
			Integer operatorsId = goodsService.findByGoodsId(goodsReleaseDto.getGoodsId()).getOperatorsId();
			OperatorsDto operatorsDto = oprtatorsService.selectByPrimaryKey(operatorsId);
			
			if("0".equals(operatorsDto.getAsync())){
				String userArchiving = userService.userArchiving(tradeId);
				if ("ok".equals(userArchiving) || "maintenance".equals(userArchiving)) {
					successCount = successCount + 1;
				} else {
					failCount++;
				}
			}else{
				//异步
			}
			
			

		}
		staMap.put("success", successCount);
		staMap.put("fail", failCount);

		return staMap;
	}

	/**
	 * 批量订购展示页面
	 * 
	 * @param req
	 * @param resp
	 * @param laoUserDto
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "list")
	public Map<String, Object> List(HttpServletRequest req, HttpServletResponse resp, LaoUserDto laoUserDto,
			HttpSession session) throws Exception {
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;
		if (laoUserDto.getCustId() == null) {
			laoUserDto.setCustId(new Long(-1));
		}

		if ("-1".equals(laoUserDto.getValue1())) {
			laoUserDto.setValue1("");
			laoUserDto.setValue2("");
		} else if ("-1".equals(laoUserDto.getValue2())) {
			laoUserDto.setValue2("");
		}
		Map<String, Object> resultMap = goodsOrderService.queryPage(laoUserDto, pageNo, pageSize);
		return resultMap;
	}

	/**
	 *
	 * @param session
	 * @param req
	 * @return 跳转到个人订购页面
	 */
	@RequestMapping(value = "/toPersonalOrder")
	public ModelAndView toPersonalOrder() {
		ModelAndView mv = new ModelAndView("/Goods/personalOrder");
		return mv;
	}

	/**
	 * 获取卡片
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryIccids")
	public List<LaoUserDto> queryIccids(HttpSession session) throws Exception {
		LaoSsAccountDto currentUser = (LaoSsAccountDto) session.getAttribute("sessionUser");
		// 如果没登陆则显示联想公用产品的信息
		Long custId = (currentUser.getCustId() == null || currentUser.getCustId().equals("")) == true
				? ConstantEnum.CUSTID.getCode() : currentUser.getCustId();
		List<LaoUserDto> laoUserList = goodsOrderService.queryLaoUserByCustId(custId);
		return laoUserList;

	}

	/**
	 * 
	 * @param iccid
	 * @return
	 * @throws Exception
	 *             个人订购显示卡片信息
	 */
	@ResponseBody
	@RequestMapping(value = "/showUser")
	public ResultMsg showUser(HttpServletRequest req) throws Exception {
		String iccid = null;
		String parameter = req.getParameter("iccid");
		String parameter2 = req.getParameter("iccid2");
		if (StringUtils.isBlank(parameter) && StringUtils.isNotBlank(parameter2)) {
			iccid = parameter2;
		} else if (StringUtils.isBlank(parameter2) && StringUtils.isNotBlank(parameter)) {
			iccid = parameter;
		} else if (StringUtils.isNotBlank(parameter2) && StringUtils.isNotBlank(parameter)) {
			iccid = parameter;
		}

		PersonalOrderDto personalOrderDto = goodsOrderService.queryPersonalOrderDto(iccid);
		personalOrderDto.setDataRemaining(personalOrderDto.getDataRemaining() + "M");
		ResultMsg msg = new ResultMsg();
		msg.setObjData(personalOrderDto);
		msg.setSuccess(true);
		return msg;
	}

	/**
	 * 
	 * @param iccid
	 * @return
	 * @throws Exception
	 *             个人订购显示产品
	 */
	@ResponseBody
	@RequestMapping(value = "/showGoodsRealease")
	public List<GoodsDto> showGoodsRealease(HttpServletRequest req) throws Exception {
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		String iccid = null;
		String parameter = req.getParameter("iccid");
		String parameter2 = req.getParameter("iccid2");
		if (StringUtils.isBlank(parameter) && StringUtils.isNotBlank(parameter2)) {
			iccid = parameter2;
		} else if (StringUtils.isBlank(parameter2) && StringUtils.isNotBlank(parameter)) {
			iccid = parameter;
		} else if (StringUtils.isNotBlank(parameter2) && StringUtils.isNotBlank(parameter)) {
			iccid = parameter;
		}
		if (iccid.length() == 19 && iccid.contains("898606")) {
			iccid = ICCID.replaceLastChar(iccid);
		}

		LaoUserDto laoUserDto = userService.getLaoUserDtoByIccid(iccid);
		String custId = laoUserDto.getChannelCustId().toString();
		String operatorsId = laoUserDto.getOperatorsId().toString();
		// 没登陆，展示联想公用custId对应的产品，不是管理员客户,并且卡和客户不一致,则展示普通资费
		if (user == null
				|| (!ActionUtil.ifSuperUser(req) && !trafficQueryService.bIsLegalIccId(iccid, user.getCustId()))) {
			custId = String.valueOf(ConstantEnum.CUSTID.getCode());
		}
		String value1 = laoUserDto.getValue1();
		String value2 = laoUserDto.getValue2();
		List<GoodsDto> laoGoodsDtos = goodsOrderService.queryLaoGoods(custId, operatorsId, value1, value2);
		return laoGoodsDtos;

	}
	
	/**
	 * 
	 * @param iccid
	 * @return
	 * @throws Exception
	 *            H5 个人订购显示产品(李雪 要求）
	 */
	@ResponseBody
	@RequestMapping(value = "/showH5GoodsRealease")
	public List<GoodsDto> showH5GoodsRealease(HttpServletRequest req) throws Exception {
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		String iccid = null;
		String parameter = req.getParameter("iccid");
		String parameter2 = req.getParameter("iccid2");
		if (StringUtils.isBlank(parameter) && StringUtils.isNotBlank(parameter2)) {
			iccid = parameter2;
		} else if (StringUtils.isBlank(parameter2) && StringUtils.isNotBlank(parameter)) {
			iccid = parameter;
		} else if (StringUtils.isNotBlank(parameter2) && StringUtils.isNotBlank(parameter)) {
			iccid = parameter;
		}
		if (iccid.length() == 19 && iccid.contains("898606")) {
			iccid = ICCID.replaceLastChar(iccid);
		}

		LaoUserDto laoUserDto = userService.getLaoUserDtoByIccid(iccid);
		String custId = laoUserDto.getChannelCustId().toString();
		String operatorsId = laoUserDto.getOperatorsId().toString();
		// 没登陆，展示联想公用custId对应的产品，不是管理员客户,并且卡和客户不一致,则展示普通资费
		if (user == null
				|| (!ActionUtil.ifSuperUser(req) && !trafficQueryService.bIsLegalIccId(iccid, user.getCustId()))) {
			custId = String.valueOf(ConstantEnum.CUSTID.getCode());
		}
		String value1 = laoUserDto.getValue1();
		String value2 = laoUserDto.getValue2();
		List<GoodsDto> laoGoodsDtos = goodsOrderService.queryH5LaoGoods(custId, operatorsId, value1, value2);
		return laoGoodsDtos;

	}

	/**
	 * 展示价格
	 * 
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/showPrice")
	public String showPrice(Long goodsId, Integer goodsReleaseId, HttpSession session) throws Exception {
		String countPrice = goodsOrderService.countPrice(goodsId, goodsReleaseId);
		session.setAttribute("goodsId", goodsId);
		session.setAttribute("goodsReleaseId", goodsReleaseId);
		return countPrice;
	}

	/**
	 * 跳转确认支付界面
	 * 
	 * @return
	 */
	@RequestMapping("/toConfirm")
	public ModelAndView toConfirm(HttpServletRequest request, HttpSession session) throws Exception {
		Double fee = null;
		String goodsName = null;
		String goodsPic = null;
		String iccid = null;
		Long goodsId = (Long) session.getAttribute("goodsId");
		Integer goodsReleaseId = (Integer) session.getAttribute("goodsReleaseId");
		String parameter = request.getParameter("iccid");
		String parameter2 = request.getParameter("iccid2");
		if (StringUtils.isBlank(parameter) && StringUtils.isNotBlank(parameter2)) {
			iccid = parameter2;
		} else if (StringUtils.isBlank(parameter2) && StringUtils.isNotBlank(parameter)) {
			iccid = parameter;
		} else if (StringUtils.isNotBlank(parameter2) && StringUtils.isNotBlank(parameter)) {
			iccid = parameter2;
		}
		// LaoSsAccountDto
		// currentUser=(LaoSsAccountDto)session.getAttribute("sessionUser");
		// String custId = currentUser.getCustId().toString();
		// IccidLibDto selectByIccid = userService.selectByIccid(iccid);
		// String custId = selectByIccid.getCustid();
		String custId = userService.getCustIdByIccid(iccid);
		if (custId == null || custId.equals("")) {
			ModelAndView mv = new ModelAndView("/Goods/notice");
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("/Goods/confirmPay");
			log.info("goodsReleaseId:" + goodsReleaseId + "goodsId:" + goodsId + "   iccid" + iccid + "   custId"
					+ custId);
			LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
			String tradeId = tradeService.addTrade(user, custId, iccid, goodsId.toString(), goodsReleaseId.toString(),
					"120", "0");
			session.removeAttribute("goodsId");
			session.removeAttribute("goodsReleaseId");
			if (tradeId != null) {
				TradeDto tradeDto = tradeService.queryTradeId(Long.parseLong(tradeId));
				log.info("trade参数======================" + tradeDto.toString());
				GoodsDto goodsDto = goodsService.findByGoodsId(goodsId);

				goodsName = goodsDto.getGoodsName();
				GoodsReleaseDto goodsReleaseDto = goodsReleaseService
						.findBygoodsReleaseId(Integer.valueOf(goodsReleaseId.toString()));
				goodsPic = goodsDto.getGoodsPic();

				if (goodsReleaseDto.getReleasePrice() != null
						&& Double.valueOf(goodsReleaseDto.getReleasePrice()) > 0) {
					tradeFeeSubService.addTradeFeeSub(tradeDto);
					TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
					fee = Double.parseDouble(tradeFeeSubDto.getFee());
					// fee = feee/100;
					tradeDto.setFee(tradeFeeSubDto.getFee());
					tradeService.updateTrade(tradeDto);
				} else {
					fee = Double.parseDouble(goodsReleaseDto.getReleasePrice().toString());
					tradeDto.setFee(String.valueOf(fee));
					tradeService.updateTrade(tradeDto);
				}
			} else {
				mv = new ModelAndView("/Goods/noPermission");
				mv.addObject("msg", "无修改本月套餐的权限");
				return mv;
			}
			mv.addObject("goodsPic", goodsPic);
			mv.addObject("goodsName", goodsName);
			mv.addObject("iccid", iccid);
			mv.addObject("goodsId", goodsId);
			mv.addObject("goodsReleaseId", goodsReleaseId);
			mv.addObject("fee", fee);
			mv.addObject("tradeId", tradeId);
			return mv;
		}
	}

	/**
	 * 支付，创建订单
	 * 
	 * @return
	 */
	@RequestMapping("/torealPay")
	public ModelAndView toaliPay(HttpSession session, String iccid, String goodsId, String tradeId, String fee) {
		// ModelAndView mv = new ModelAndView("/H5/pay");
		String userId = (String) session.getAttribute("lenovoid");
		String lpsust = (String) session.getAttribute("lpsust");
		String userName = (String) session.getAttribute("username");
		log.info("iccid-----" + iccid);
		log.info("goodsId---------" + goodsId);
		log.info("fee-------" + fee);
		log.info("tradeId-----" + tradeId);
		log.info("userId-----" + userId);
		log.info("lpsust-----" + lpsust);
		log.info("userName-----" + userName);
		if (userId != null && lpsust != null && userName != null) {
			ModelAndView mv = new ModelAndView("/H5/pay");
			double realFee = Double.parseDouble(fee);
			Map<String, String> map = payService.toAliPay(userId, userName, realFee, iccid, lpsust, tradeId);
			if (map == null || map.size() < 0) {
				mv = new ModelAndView("redirect:/paymentService/topay");
			} else {
				mv.addObject("pcwebPayRequestUrl", map.get("privateAttach"));
				map.put("privateAttach", ""); // 将privateAttach清空
				mv.addObject("params", map);
			}
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("/Goods/remind");
			return mv;
		}

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
