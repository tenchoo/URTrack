package com.urt.web.web.customer;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.urt.common.util.ResultMsg;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoPeripheralSysAccessLogDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoSsRealNameVerifyDto;
import com.urt.dto.LaoSsResourceDto;
import com.urt.dto.LaoUserGoodsDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.Goods.GoodsReleaseDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.Trade.TradeFeeSubDto;
import com.urt.dto.device.Account;
import com.urt.dto.traffic.TrafficQueryDetailsDto;
import com.urt.dto.traffic.TrafficQueryNowDto;
import com.urt.dto.unicom.DeviceDto;
import com.urt.interfaces.Goods.GoodsOrderService;
import com.urt.interfaces.Goods.GoodsReleaseService;
import com.urt.interfaces.Goods.GoodsService;
import com.urt.interfaces.Trade.TradeFeeSubService;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.authority.LaoAccountRelService;
import com.urt.interfaces.authority.LaoSsAccountService;
import com.urt.interfaces.device.DeviceService;
import com.urt.interfaces.http.CardActiveService;
import com.urt.interfaces.http.ServerCheckService;
import com.urt.interfaces.realNameVerify.RealNameVerifyServiece;
import com.urt.interfaces.traffic.TrafficQueryService;
import com.urt.web.common.util.AccountCookie;
import com.urt.web.common.util.ICCID;
import com.urt.web.common.util.StringUtil;
@Controller
@RequestMapping("/customerQuery")
public class rateDetailSearchController {
	
	private static final Logger log = Logger.getLogger(rateDetailSearchController.class);
	@Autowired
	private DeviceService deviceService;
	@Autowired
	TrafficQueryService trafficQueryService;
	@Autowired
	private TradeFeeSubService tradeFeeSubService;
	@Autowired
	private LaoAccountRelService laoAccountRelService;
	@Autowired
	private LaoSsAccountService laoSsAccountService;
	@Autowired
	private UserService userService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private TradeService tradeService;
	@Autowired
	private CardActiveService cardActiveService; 
	@Autowired
	private GoodsReleaseService goodsReleaseService;
	@Autowired 
	private RealNameVerifyServiece realNameVerifyServiece;
	@Autowired 
	private GoodsOrderService goodsOrderService;
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
	private ServerCheckService serverService;
/*	@Value("${passort.login.url}")
	private  String INDEX;*/
	@Value("#{configProperties['passort.login.url']}")
	private  String INDEX;

	//跳转到流量查询页面
	@RequestMapping( value="toRateSearch", method = { RequestMethod.POST,RequestMethod.GET})	
	public ModelAndView toRateSearch(HttpServletRequest request, HttpServletResponse response,HttpSession session)throws Exception{
		//gla系统登录
		log.info("INDEX**************"+INDEX);
		String requestUrl = request.getRequestURI();
        AccountCookie accountCookie = new AccountCookie(request, response);
		accountCookie = accountCookie.getCookieAccount();
		//外部传参调用
		ModelAndView mv = new ModelAndView("/customer/rateSearch");
		String iccid = request.getParameter("iccid").replace("#", "B");
		String st = request.getParameter("st");
//		String deviceid = request.getParameter("deviceid");
//		String s = request.getParameter("s");
		log.info("iccid*************"+iccid+"st**************"+st);
		
		Account account = deviceService.authSt(st);
		if(account == null){
			request.getSession().invalidate();
			response.sendRedirect(INDEX + "&lenovoid.ctx="+requestUrl);
		}else{
			session.setAttribute("account", account);
			session.setAttribute("lpsust", st);
			session.setAttribute("lenovoid", account.getAccountID());
//			String lenovoId = account.getAccountID();//联想账户
			//String userName = account.getUname();
			String userName = account.getUsername();
			if (iccid.length() == 19 && "89860616".equals(iccid.substring(0, 8))) {
				iccid = ICCID.replaceLastChar(iccid);
			}
			log.info("补充iccid*******************"+iccid);
//			log.info("lenovoId*******************"+lenovoId);
			log.info("userName*******************"+userName);
			//查询到custId
			LaoSsAccountDto laoSsAccountDto = laoSsAccountService.getUserByLoginName(userName);
			session.setAttribute("sessionUser", laoSsAccountDto);
			String custid = "";
			if (laoSsAccountDto != null) {
				custid = laoSsAccountDto.getCustId().toString();
			}
			log.info("custid***************"+custid);
			
			//先判断用户是否拥有这张卡的操作权限   然后判断用户是否可以订购这个产品  然后判断用户是否可以订购这个产品  
				if(userService.hasPermission(iccid, custid)){
					TrafficQueryNowDto doNowTrafficQuery = trafficQueryService.doNowTrafficQuery(iccid);
					if (doNowTrafficQuery == null) {
						log.info("剩余流量查询返回空********************");
						return mv;
					}
					log.info("tostring********************"+doNowTrafficQuery.toString());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					mv.addObject("timestamp", sdf.format(new Date()));
					mv.addObject("userName",userName);
					DecimalFormat decimalFormat = new DecimalFormat("#0");//格式化设置
					String dataRemaining = doNowTrafficQuery.getDataRemaining();
					String format = decimalFormat.format(Double.parseDouble(dataRemaining));
					log.info("dataRemaining******************************"+format);
					mv.addObject("dataRemaining",format);
					mv.addObject("iccid", iccid);
					Integer operatorsId	= 0 ;
					LaoUserDto laoUserDto = userService.getLaoUserDtoByIccid(iccid);
					if (laoUserDto != null) {
						operatorsId	= laoUserDto.getOperatorsId();
					}
					if(operatorsId == 1){
						//联通
						if(StringUtil.isBlank(doNowTrafficQuery.getRatePlanName())){
							mv.addObject("ratePlanName", "110WLW004085_MON-FIX_5120M-0S");
						}else{
							mv.addObject("ratePlanName", doNowTrafficQuery.getRatePlanName());

						}
						if(StringUtil.isBlank(doNowTrafficQuery.getExpirationDate())){
							mv.addObject("expirationDate","月底");

						}else{
							mv.addObject("expirationDate",doNowTrafficQuery.getExpirationDate().substring(0, 10));
							log.info("expirationDate*********"+doNowTrafficQuery.getExpirationDate().substring(0, 10));

						}						
					} else if(operatorsId == 2) {
						//移动
						LaoUserGoodsDto laoUserGoodsDto = userService.getUserGoodsByIccid(iccid);
						Date endDate = laoUserGoodsDto.getEndDate();
						String format2 = sdf.format(endDate);
						Long goodsId = laoUserGoodsDto.getGoodsId();
						String goodsName = goodsService.findByGoodsId(goodsId).getGoodsName();
						mv.addObject("ratePlanName",goodsName);//套餐名称
						mv.addObject("expirationDate",format2);//到期
						
					} else if(operatorsId == 3) {
						//电信
					}
				}else{
					ModelAndView mvExcept = new ModelAndView("/customer/noPermission");//跳转到异常页面，用户无操作卡的权限
					String msg = userName+"无操作"+iccid+"的权限";
					log.info("msg********"+msg);
					mvExcept.addObject("msg",msg);
					return mvExcept;
					
				}
		}
		
		return mv;	
	}
	//跳转到流量查询页面
	@RequestMapping( value="toRateSearch2", method = { RequestMethod.POST,RequestMethod.GET})	
	public ModelAndView toRateSearch2(HttpServletRequest request, HttpServletResponse response,HttpSession session)throws Exception{
		ModelAndView mv = new ModelAndView("/customer/rateSearch");
		String chargeMsg = request.getParameter("chargeMsg");
		if (!StringUtils.isBlank(chargeMsg)) {
			mv.addObject("chargeMsg", chargeMsg);
		}
		LaoSsAccountDto laoSsAccountDto = (LaoSsAccountDto) session.getAttribute("sessionUser");
		Account account = (Account) session.getAttribute("account");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		if (laoSsAccountDto != null) {
			String acconutId = laoSsAccountDto.getAcconutId()+"";
			String loginName = laoSsAccountDto.getLoginName();
			Long custId = laoSsAccountDto.getCustId();
			mv.addObject("custId", custId);
			mv.addObject("acconutId", acconutId);
			mv.addObject("loginName", loginName);
			mv.addObject("timestamp", sdf.format(new Date()));
		}
		if (account != null) {
			String userName = account.getUsername();
			mv.addObject("userName", userName);
		}
		return mv;	
	}


	//剩余流量查询
	@ResponseBody
	@RequestMapping("/doQuery")
	public Map<String,Object> doQuery(String iccid,HttpSession session)throws Exception{
		log.info("iccid----------------------"+iccid);
		Map<String, Object> map = new HashMap<String, Object>();
		LaoUserDto laoUserDtoByIccid = userService.getLaoUserDtoByIccid(iccid);
		if (null == laoUserDtoByIccid){
			map.put("msg", "0");
		} else {
			if (iccid.length() == 19 && "89860616".equals(iccid.substring(0, 8))) {
				iccid = ICCID.replaceLastChar(iccid);
			}
			
			Account account =(Account) session.getAttribute("account");
			String userName = account.getUname();
			TrafficQueryNowDto doNowTrafficQuery = trafficQueryService.doNowTrafficQuery(iccid);
			if (doNowTrafficQuery == null) {
				log.info("查询剩余流量为空**********************");
				map.put("dataRemaining", 0);
				return map;
			}
			log.info("tostring********************"+doNowTrafficQuery.toString());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			map.put("timestamp", sdf.format(new Date()));
			map.put("userName",userName);
			DecimalFormat decimalFormat = new DecimalFormat("#0");//格式化设置
			String dataRemaining = doNowTrafficQuery.getDataRemaining();
			String format = decimalFormat.format(Double.parseDouble(dataRemaining));
			log.info("dataRemaining******************************"+format);
			map.put("dataRemaining", format);
			map.put("iccid", iccid);
			Integer operatorsId	= 0 ;
			LaoUserDto laoUserDto = userService.getLaoUserDtoByIccid(iccid);
			if (laoUserDto != null) {
				operatorsId	= laoUserDto.getOperatorsId();
			}
			if(operatorsId == 1){
				//联通
				if (StringUtil.isBlank(doNowTrafficQuery.getRatePlanName())){
					map.put("ratePlanName", "110WLW004085_MON-FIX_5120M-0S");
				} else {
					map.put("ratePlanName", doNowTrafficQuery.getRatePlanName());
				}
				if (StringUtil.isBlank(doNowTrafficQuery.getExpirationDate())){
					map.put("expirationDate","月底");
				} else {
					map.put("expirationDate",doNowTrafficQuery.getExpirationDate().substring(0, 10));
				}						
			} else if(operatorsId == 2) {
				//移动
				LaoUserGoodsDto laoUserGoodsDto = userService.getUserGoodsByIccid(iccid);
				Date endDate = laoUserGoodsDto.getEndDate();
				String format2 = sdf.format(endDate);
				Long goodsId = laoUserGoodsDto.getGoodsId();
				String goodsName = goodsService.findByGoodsId(goodsId).getGoodsName();
				//mv.addObject("ratePlanName",goodsName);//套餐名称
				//mv.addObject("expirationDate",format2);//到期
				map.put("ratePlanName",goodsName);
				map.put("expirationDate",format2);
			} else if(operatorsId == 3) {
				//电信
			}
			
			
		}
		return map;
	}
	//剩余流量查询
	@ResponseBody
	@RequestMapping("/rateSearch")
	public Map<String,Object> rateSearch(String iccid,HttpSession session)throws Exception{
		log.info("iccid----------------------"+iccid);
		Map<String, Object> map = new HashMap<String, Object>();
		LaoUserDto laoUserDtoByIccid = userService.getLaoUserDtoByIccid(iccid);
		if(null == laoUserDtoByIccid){
			map.put("msg", "0");
		}else{
			if (iccid.length() == 19 && "89860616".equals(iccid.substring(0, 8))) {
				iccid = ICCID.replaceLastChar(iccid);
			}
			
			Account account =(Account) session.getAttribute("account");
			String userName = account.getUname();
			TrafficQueryNowDto doNowTrafficQuery = trafficQueryService.doNowTrafficQuery(iccid);
			
			log.info("tostring********************"+doNowTrafficQuery.toString());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			map.put("timestamp", sdf.format(new Date()));
			map.put("userName",userName);
			DecimalFormat decimalFormat = new DecimalFormat("#0");//格式化设置
			String dataRemaining = doNowTrafficQuery.getDataRemaining();
			String format = decimalFormat.format(Double.parseDouble(dataRemaining));
			log.info("dataRemaining******************************"+format);
			map.put("dataRemaining", format);
			map.put("iccid", iccid);
			Integer operatorsId = userService.getLaoUserDtoByIccid(iccid).getOperatorsId();
			if(null != operatorsId && operatorsId == 1){
				//联通
				
				if(StringUtil.isBlank(doNowTrafficQuery.getRatePlanName())){
					map.put("ratePlanName", "110WLW004085_MON-FIX_5120M-0S");
				}else{
					map.put("ratePlanName", doNowTrafficQuery.getRatePlanName());

				}
				if(StringUtil.isBlank(doNowTrafficQuery.getExpirationDate())){
					map.put("expirationDate","月底");

				}else{
					map.put("expirationDate",doNowTrafficQuery.getExpirationDate().substring(0, 10));

				}						
			} else if(null != operatorsId && operatorsId == 2) {
				//移动
				LaoUserGoodsDto laoUserGoodsDto = userService.getUserGoodsByIccid(iccid);
				Date endDate = laoUserGoodsDto.getEndDate();
				String format2 = sdf.format(endDate);
				Long goodsId = laoUserGoodsDto.getGoodsId();
				String goodsName = goodsService.findByGoodsId(goodsId).getGoodsName();
				//mv.addObject("ratePlanName",goodsName);//套餐名称
				//mv.addObject("expirationDate",format2);//到期
				map.put("ratePlanName",goodsName);
				map.put("expirationDate",format2);
			}else{
				//电信
			}
		

		}
		
		return map;
		
	}
	
	//充值记录查询
	@ResponseBody
	@RequestMapping("/chargeSearch")
	public Map<String,Object> chargeSearch(HttpServletRequest request)throws Exception{
		String iccid = request.getParameter("iccid");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		Integer curPage = Integer.parseInt(request.getParameter("currentPage"));//当前页
		Map<String, Object> map = new HashMap<String, Object>();
		log.info("iccid************"+iccid+"startTime************"+startTime+"endTime************"+endTime+"currentPage*************"+curPage);
		List<TradeFeeSubDto> tradeFeeSubDtoList = tradeFeeSubService.queryTradeFreeSubByIccid(iccid, startTime, endTime, curPage, Integer.parseInt("5"));
		if(tradeFeeSubDtoList != null){
			int total = tradeFeeSubService.count(iccid, startTime, endTime);
			//int total = 7;
			int totalPage =total/5+(total%5==0?0:1);//总页数
			
			map.put("tradeFeeSubDtoList",tradeFeeSubDtoList);
			map.put("totalPage",totalPage);
			map.put("currentPage",curPage);
			map.put("total",total);
			return map;
		}else{
			map.put("msg", "0");
		}
		return map;
			
	}
	
	//消费记录查询
	@ResponseBody
	@RequestMapping("/recordSearch")
	public Map<String,Object> recordSearch(HttpServletRequest request)throws Exception{
		String iccid = request.getParameter("iccid");
		String date = request.getParameter("start");
		log.info("iccid*************"+iccid+"date*******************"+date);
		List<TrafficQueryDetailsDto> trafficQueryDetailsDtoList = trafficQueryService.doMonthTrafficQuery(iccid, date);		
		Map<String, Object> map = new HashMap<String, Object>();
		if(trafficQueryDetailsDtoList != null){
			log.info("size**********"+trafficQueryDetailsDtoList.size());
			ArrayList<String> xList = new ArrayList<String>();//x轴数据
			ArrayList<String> yList = new ArrayList<String>();//y轴数据
			for (int i = 0; i <trafficQueryDetailsDtoList.size(); i++) {
				TrafficQueryDetailsDto dto = trafficQueryDetailsDtoList.get(i);
				xList.add(dto.getSessionStartTime().substring(8, 10));
				double str = Double.parseDouble(dto.getDataVolume());
				DecimalFormat decimalFormat = new DecimalFormat("#0");//格式化设置
				String format = decimalFormat.format(str * 1024);
				log.info("format*************"+format);
				yList.add(format);
			}
			map.put("xList",xList);
			map.put("yList",yList);
			return map;
		}else{
			map.put("msg", "0");
		}
		return map;
		
	}	
	
	//充值记录详情查询
	@ResponseBody
	@RequestMapping("/chargeDetailSearch")
	public Map<String,Object> chargeDetailSearch(String iccid, String tradeId,HttpSession session)throws Exception{
		log.info("iccid*************"+iccid+"tradeId*******************"+tradeId);
		TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
		TradeDto queryTradeId = tradeService.queryTradeId(Long.parseLong(tradeId));
		Long goodsId = queryTradeId.getGoodsId();
		String goodsName = goodsService.findByGoodsId(goodsId).getGoodsName();
		Account account = (Account)session.getAttribute("account");
		String userName = account.getUsername();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("iccid", iccid);
		map.put("goodsName", goodsName);
		map.put("tradeFeeSubDto", tradeFeeSubDto);
		return map;
		
	}	


	// 设备激活页面
	@RequestMapping( value="toDeviceActivation", method = { RequestMethod.POST,RequestMethod.GET})	
	public ModelAndView toDeviceActivation(HttpServletRequest request, HttpServletResponse response,HttpSession session)throws Exception{
		ModelAndView mv = new ModelAndView("/customer/deviceActivation");
		LaoSsAccountDto laoSsAccountDto = (LaoSsAccountDto) session.getAttribute("sessionUser");
		if (laoSsAccountDto != null) {
			String loginName = laoSsAccountDto.getLoginName();
			mv.addObject("loginName", loginName);
		}
		return mv;
	}
	// 品牌介绍页面
	@RequestMapping( value="toBrandIntroduction", method = { RequestMethod.POST,RequestMethod.GET})	
	public ModelAndView toBrandIntroduction(HttpServletRequest request, HttpServletResponse response,HttpSession session)throws Exception{
		ModelAndView mv = new ModelAndView("/customer/brandIntroduction");
		LaoSsAccountDto laoSsAccountDto = (LaoSsAccountDto) session.getAttribute("sessionUser");
		if (laoSsAccountDto != null) {
			String loginName = laoSsAccountDto.getLoginName();
			mv.addObject("loginName", loginName);
		}
		return mv;
	}
	// 设备激活操作
	@ResponseBody
	@RequestMapping(value = "/doDeviceActivation")
	public Map<String,Object> doDeviceActivation(HttpServletRequest request,HttpServletResponse response,HttpSession session, DeviceDto deviceDto) {
		Map<String,Object> mapRet = new HashMap<String,Object>();
		if (deviceDto == null || deviceDto.getIccid() == null) {
			mapRet.put("msg", "设备绑定号为空！");
			return mapRet;
		}
		log.info("》》》》》》》》》》》》》》》》页面传来19位数的iccid=" + deviceDto.getIccid());
		String iccid = ICCID.replaceLastChar(deviceDto.getIccid());
		log.info("》》》》》》》》》》》》》》》》调用ICCID.replaceLastChar方法后20位数的iccid=" + iccid);
		LaoSsRealNameVerifyDto dtoSsRealName = new LaoSsRealNameVerifyDto();
		dtoSsRealName.setIccid(iccid);
		dtoSsRealName.setIdnum(deviceDto.getIdnum());
		dtoSsRealName.setRealname(deviceDto.getRealname());
		dtoSsRealName.setIdtype((short) 1);
		// 得到当前用户
		// 判断当前用户是否进行过实名认证
		// 进行实名认证
		LaoSsAccountDto currentUser = (LaoSsAccountDto)session.getAttribute("sessionUser");
		if (currentUser != null) {
			if(!realNameVerifyServiece.checkRealName(dtoSsRealName.getIdnum(), currentUser.getAcconutId())){
				log.info("身份证已经认证过,或今日认证次数用完了");
			}else{
				try {
					ResultMsg remsg = realNameVerifyServiece.customerVerifyServiece(dtoSsRealName,currentUser);
					if (!remsg.isSuccess()) {
						mapRet.put("msg", "实名认证失败！");
						return mapRet;
					}
				} catch (Exception e) {
					log.info("》》》实名认证错误信息："+e.getMessage()+";"+e.getCause());
					mapRet.put("msg", "实名认证失败！");
					return mapRet;
				}
			}
		}
		log.info("身份证通过");
		// 设备激活
		IccidLibDto dto = userService.selectByIccid(iccid);
		if (dto != null) {
			// 判断激活码是否正确
			if (deviceDto.getPrivatekey().equals(dto.getPrivatekey())) {
				LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
				if(dto.getActived().equals("1")){
					try {
						GoodsReleaseDto goodsReleaseDto = goodsReleaseService.findBygoodsReleaseId(Integer.valueOf(dto.getInitproduct()));
						String tradeId = tradeService.addTrade(user,dto.getCustid(), null,iccid,goodsReleaseDto.getGoodsId().toString() ,dto.getInitproduct(), "100",dto.getIfMaintenance());
						String resultArch = userService.userArchiving(tradeId);
						if ("ok".equals(resultArch)) {
							mapRet.put("msg", "设备激活成功！");
							return mapRet;
						} else {
							mapRet.put("msg", "设备激活失败！");
							return mapRet;
						}
					} catch (Exception e) {
						log.info("》》》设备激活异常："+e.getMessage()+";"+e.getCause());
						e.printStackTrace();
						mapRet.put("msg", "设备激活异常！");
						return mapRet;
					}
				} else {
					mapRet.put("msg", "该设备号已激活！");
					return mapRet;
				}
			} else {
				mapRet.put("msg", "激活码错误！");
				return mapRet;
			}
		}
		return mapRet;	
	}
	
	
	// 设备充值页面
	@RequestMapping( value="toRecharge", method = { RequestMethod.POST,RequestMethod.GET})	
	public ModelAndView toRecharge(HttpServletRequest request, HttpServletResponse response,HttpSession session)throws Exception{
		ModelAndView mv = new ModelAndView("/customer/recharge");
		String chargeMsg = request.getParameter("chargeMsg");
		if (!StringUtils.isBlank(chargeMsg)) {
			mv.addObject("chargeMsg", chargeMsg);
		}
		LaoSsAccountDto laoSsAccountDto = (LaoSsAccountDto) session.getAttribute("sessionUser");
		if (laoSsAccountDto != null) {
			String acconutId = laoSsAccountDto.getAcconutId()+"";
			String loginName = laoSsAccountDto.getLoginName();
			Long custId = laoSsAccountDto.getCustId();
			mv.addObject("custId", custId);
			mv.addObject("acconutId", acconutId);
			mv.addObject("loginName", loginName);
		}
		if (!StringUtils.isBlank(chargeMsg)) {
			mv.addObject("chargeMsg", chargeMsg);
		}
		return mv;
	}
	
	// 查询账号下对应的ICCID
	@ResponseBody
	@RequestMapping(value = "/lookRecharge")
	public List<Map<String, Object>> lookRecharge(HttpServletRequest request,HttpServletResponse response,HttpSession session,Long custId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<LaoUserDto> listDto = goodsOrderService.queryLaoUserByCustId(custId);
		Map<String, Object> initMap = new HashMap<String, Object>();
		initMap.put("text", "请选择");
		initMap.put("id", -1);
		list.add(initMap);
		if (listDto != null && listDto.size() > 0) {
			for (LaoUserDto dto : listDto) {
				Map<String, Object> initMap1 = new HashMap<String, Object>();
				initMap1.put("text", dto.getIccid());
				initMap1.put("id", dto.getIccid());
				list.add(initMap1);
			}
		}
		return list;
	}
	
	
	// 跳转实名认证页面
	@RequestMapping( value="toVerify", method = { RequestMethod.POST,RequestMethod.GET})	
	public ModelAndView toVerify(HttpServletRequest request, HttpServletResponse response,HttpSession session)throws Exception{
		ModelAndView mv = new ModelAndView("/customer/verify");
		LaoSsAccountDto laoSsAccountDto = (LaoSsAccountDto) session.getAttribute("sessionUser");
		if (laoSsAccountDto != null) {
			String loginName = laoSsAccountDto.getLoginName();
			mv.addObject("loginName", loginName);
		}
		return mv;
	}
	// 跳转到首页
	@RequestMapping( value="loginSuccessIndex", method = { RequestMethod.POST,RequestMethod.GET})	
	public ModelAndView loginSuccessIndex(HttpServletRequest request, HttpServletResponse response,HttpSession session)throws Exception{
		ModelAndView mv = new ModelAndView("/customer/loginSuccess");
		LaoSsAccountDto laoSsAccountDto = (LaoSsAccountDto) session.getAttribute("sessionUser");
		if (laoSsAccountDto != null) {
			String loginName = laoSsAccountDto.getLoginName();
			mv.addObject("loginName", loginName);
		}
		return mv;
	}
/*	// 登录成功跳转页面
	@RequestMapping( value="loginSuccess", method = { RequestMethod.POST,RequestMethod.GET})	
	public ModelAndView loginSuccess(HttpServletRequest request, HttpServletResponse response,HttpSession session)throws Exception{
		ModelAndView mv = new ModelAndView("/customer/loginSuccess");
		//gla系统登录
		log.info("INDEX**************"+INDEX);
		String requestUrl = request.getRequestURI();
		AccountCookie accountCookie = new AccountCookie(request, response);
		accountCookie = accountCookie.getCookieAccount();
		//外部传参调用
		String st = request.getParameter("st");
		Account account = deviceService.xdswAuthSt(st);
		if (account == null) {
			request.getSession().invalidate();
			response.sendRedirect(INDEX + "&lenovoid.ctx="+requestUrl);
		} else {
			session.setAttribute("account", account);
			String userName = account.getUsername();
			mv.addObject("loginName", userName);
			log.info("userName*******************"+userName);
			LaoSsAccountDto laoSsAccountDto = laoSsAccountService.getUserByLoginName(userName);
			if (laoSsAccountDto != null) {
				session.setAttribute("sessionUser", laoSsAccountDto);
			}
		}
		return mv;
	}*/
	// 退出
	@RequestMapping( value="toExit", method = { RequestMethod.POST,RequestMethod.GET})	
	public ModelAndView toExit(HttpServletRequest request, HttpServletResponse response,HttpSession session)throws Exception{
		SecurityUtils.getSubject().logout();
		request.getSession().invalidate();
		ModelAndView mv = new ModelAndView("/customer/toLogin");
		return mv;
	}
	

	/**
	 * 跳转确认支付界面
	 * @return
	 * @param request
	 * @param response
	 * @param session
	 * @param iccid
	 * @param goodId
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "goodsorder", method = { RequestMethod.POST,RequestMethod.GET})
	public ModelAndView goodsorder(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(value = "iccid", required = false) String iccid,
			@RequestParam(value = "payType", required = false) String payType,
			@RequestParam(value = "goodsReleaseId", required = false) String goodId,
			@RequestParam(value = "goodsName", required = false) String goodsName
			) throws Exception {
		ModelAndView mv = new ModelAndView("/H5/pay");
		Map<String,Object> params = new HashMap<String,Object>();
		Account account = (Account) session.getAttribute("account");
		log.info("enter the method goodsorder");
		iccid = iccid.replace("#", "B");
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
				String tradeId = tradeService.addTrade(ss,custId, iccid,String.valueOf(relpease.getGoodsId()),goodId,"150","0");		
				TradeDto tradeDto = tradeService.queryTradeId(Long.parseLong(tradeId));
				//tradeFeeSubService.addTradeFeeSub(tradeDto);//算费
				String fee = relpease.getReleasePrice();
				GoodsDto goods = goodsService.findByGoodsId(relpease.getGoodsId());
				if(tradeId != null){
					log.info("trade参数======================"+tradeDto.toString());
					if(goods.getGoodsPrices() != null && Double.valueOf(goods.getGoodsPrices()) > 0){
						tradeFeeSubService.addTradeFeeSub(tradeDto);
						TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
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
				resultJson.put("productName", PRODUCTNAME);
				resultJson.put("merchantOrderId", tradeId);
				resultJson.put("userName", account.getAccountID());
				resultJson.put("userId", account.getUsername());
				resultJson.put("privateAttach", PRIVATEATTACH);
				// 微信支付
				if ("1".equals(payType)) {
					mv = new ModelAndView("redirect:/PayCreate/PayCreate");
					double feeDoub = Double.parseDouble(fee);
					int feeInt = (int) (feeDoub * 100);
					mv.addObject("order_price", feeInt);// 商品价格
					mv.addObject("body", goodsName);// 商品名称
					mv.addObject("out_trade_no", tradeId);// 订单号
					return mv;
				}
				params.put("merchantOrderId", tradeId);
				params.put("payAmount", fee);
				params.put("payType", PAYTYPE);
				params.put("productName", PRODUCTNAME);
				params.put("realm", REALM);
				params.put("userId", account.getUsername());
				params.put("userName", account.getAccountID());
/*				// 拼接支付URL
				retUrl.append(PRIVATEATTACH)
					.append("&merchantOrderId=").append(tradeId)
					.append("&payAmount=").append(fee)
					.append("&payType=").append(PAYTYPE)
					.append("&productName=").append(PRODUCTNAME)
					.append("&realm=").append(REALM)
					.append("&userId=").append(account.getUsername())
					.append("&userName=").append(account.getAccountID());
				log.info("retUrl参数======================"+retUrl.toString());*/
			}
		} catch (Exception e) {
			retCode="-4";
			e.printStackTrace();
		}
		log.info("支付地址"+PRIVATEATTACH);
		resultJson.put("retCode", retCode);
//		out.println(resultJson.toString());
		
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
		recordDto.setReqInfo(reqJson.toString());
		recordDto.setRspInfo(resultJson.toString());
		recordDto.setAccessDate(new Date());
		recordDto.setParaName1("device");
		serverService.savaLogerToDb(recordDto);
		log.info("exit the method goodsorder");
		mv.addObject("pcwebPayRequestUrl", PRIVATEATTACH);
		mv.addObject("params", params);
		return mv;
	}
	
	
	
	// 查询账号下对应的ICCID
	@ResponseBody
	@RequestMapping(value = "/queryOrder")
	public Map<String, Object> queryOrder(HttpServletRequest request,HttpServletResponse response,HttpSession session,String orderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		TradeFeeSubDto tradeFeeSubDto = null;
		TradeDto tradeDto = null;
		try {
			log.info("--------------------查询订单号：-------------------------"+orderId);
			tradeDto = tradeService.queryTradeId(Long.parseLong(orderId));
			tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(orderId);
		} catch (Exception e) {
			log.info("---------------------------------------------查询订单异常,tradeFeeSubDto="+tradeFeeSubDto);
		}
		map.put("chargeMsg", 9);
		if (tradeFeeSubDto != null && tradeDto != null) {
			log.info("----------------------------------payTag="+tradeFeeSubDto.getPayTag());
			if ("1".equals(tradeFeeSubDto.getPayTag())) {
				log.info("订单已支付成功");
				map.put("iccid", tradeDto.getIccid());
				map.put("chargeMsg", 1);
			} else if("0".equals(tradeFeeSubDto.getPayTag())){
				map.put("chargeMsg", 0);
			}
		}
		return map;
	}
}
