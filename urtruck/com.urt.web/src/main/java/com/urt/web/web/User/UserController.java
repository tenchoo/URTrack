package com.urt.web.web.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.Goods.GoodsReleaseDto;
import com.urt.dto.Goods.OperatorsDto;
import com.urt.dto.Goods.ServiceStatusDto;
import com.urt.dto.Trade.TradeDto;
import com.urt.dto.Trade.TradeFeeSubDto;
import com.urt.dto.unicom.PaymentDto;
import com.urt.interfaces.Goods.GoodsOrderService;
import com.urt.interfaces.Goods.GoodsReleaseService;
import com.urt.interfaces.Goods.GoodsService;
import com.urt.interfaces.Goods.OperatorsService;
import com.urt.interfaces.Goods.ServiceStatusService;
import com.urt.interfaces.Trade.TradeFeeSubService;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.chargeOff.UserFeeInfoService;
import com.urt.interfaces.remain.RemainService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
import com.urt.web.common.util.ActionUtil;
import com.urt.web.common.util.ImportExcelUtils;
import com.urt.web.common.util.StringUtil;
import com.urt.web.util.WeixinUtil;

/**
 * 类说明：主界面
 * 
 * @author sunhao
 * @date 2016年8月23日15:40:42
 */
@Controller
@RequestMapping("/userService")
public class UserController {
	Logger log = Logger.getLogger(getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private TradeService tradeService;

	@Autowired
	private GoodsReleaseService goodsReleaseService;

	@Autowired
	private OperatorsService operatorService;

	@Autowired
	private ServiceStatusService serviceStatusService;

	@Autowired
	private GoodsOrderService laoGoodsOrderService;

	@Autowired
	private TradeFeeSubService tradeFeeSubService;

	@Autowired
	private RemainService remainService;
	
	@Autowired
	private GoodsService  goodsService;
	
	@Autowired
	private UserFeeInfoService userFeeInfoService;

	private final Map<String, Object> resultMap = new ConcurrentHashMap<String, Object>();

	/**
	 * 上传excel文件信息界面
	 */
	@RequestMapping("/uploadExcel")
	public ModelAndView uploadExcel() {
		List<OperatorsDto> operators = operatorService.findOperators();
		ModelAndView mv = new ModelAndView("/user/upload");
		mv.addObject("operatorList", operators);
		resultMap.clear();
		return mv;
	}

	/**
	 * 根据运行商查询服务状态
	 */
	@ResponseBody
	@RequestMapping("/getCardstatusList")
	public List<Map<String, Object>> getCardstatusList(
			HttpServletResponse response, String operators) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> initMap = new HashMap<String, Object>();
		initMap.put("text", "请选择");
		initMap.put("id", -1);
		list.add(initMap);
		List<ServiceStatusDto> dtolist = serviceStatusService
				.selectByOperatorId(operators);
		Map<String, Object> selectMap = null;
		if (dtolist != null && dtolist.size() > 0) {
			for (ServiceStatusDto serviceStatusDto : dtolist) {
				selectMap = new HashMap<String, Object>();
				selectMap.put("text", serviceStatusDto.getServiceName());
				selectMap.put("id", serviceStatusDto.getStateCode());
				list.add(selectMap);
			}
		}
		return list;
	}

	/**
	 * 根据条件查询产品
	 */
	@ResponseBody
	@RequestMapping("/getGoodRealses")
	public List<Map<String, Object>> getGoodRealses(
			HttpServletResponse response, String custId, String value1,
			String value2, String operatorsId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> initMap = new HashMap<String, Object>();
		initMap.put("text", "请选择");
		initMap.put("id", -1);
		list.add(initMap);
		List<GoodsDto> dtolist = laoGoodsOrderService.queryLaoGoods(custId,
				operatorsId, value1, value2);
		Map<String, Object> selectMap = null;
		if (dtolist != null && dtolist.size() > 0) {
			for (GoodsDto goodsDto : dtolist) {
				selectMap = new HashMap<String, Object>();
				selectMap.put("text", goodsDto.getGoodsName());
				selectMap.put("id", goodsDto.getGoodsReleaseId());
				list.add(selectMap);
			}
		}
		return list;
	}

	/**
	 * 得到上传的卡信息
	 */
	@ResponseBody
	@RequestMapping("/getIccidInfo")
	public Map<String, Object> getIccidInfo(HttpServletRequest request) {
		int pageStart = request.getParameter("iDisplayStart") == null ? 1
				: Integer.parseInt(request.getParameter("iDisplayStart")
						.toString());// 开始显示的项
		int pageSize = request.getParameter("iDisplayLength") == null ? 1
				: Integer.parseInt(request.getParameter("iDisplayLength")
						.toString());// 显示多少项
		resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
		if (resultMap.containsKey("total"))
			resultMap.put("iTotalDisplayRecords", resultMap.get("total"));// 总记录数
		return resultMap;
	}

	/**
	 * 批量录入卡信息(使用excel导入信息)
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/batchImport")
	public void upload(@RequestParam(value = "file") MultipartFile file,
			IccidLibDto iccidDto, String type, String version)
			throws IOException {
		ImportExcelUtils<IccidLibDto> utils = null;
		List<IccidLibDto> list = null;

		Long beginTime = System.currentTimeMillis();
		log.info("................批量导入开始...................time:" + beginTime);
		// 判断上传文件，如果不为空，将之转换成对象
		if (!file.isEmpty()) {
			utils = new ImportExcelUtils<IccidLibDto>();
			iccidDto.setValue1(type);
			iccidDto.setValue2(version);
			if (file.getOriginalFilename().endsWith("xlsx")) {
				list = utils.importExcel(file.getInputStream(), iccidDto, true);
			} else {
				list = utils.importExcel(file.getInputStream(), iccidDto, false);
			}
			// 将对象插入数据库
			int total = 0;
			if (list != null && list.size() > 0) {
				String batchId = ZkGenerateSeq.getIdSeq(SeqID.BATCHID);
				List<IccidLibDto> cast = new ArrayList<IccidLibDto>();
				for (int i=0 ; i < list.size() ; i++) {
					list.get(i).setBatchId(batchId);
					cast.add(list.get(i));
					if(i%1000 == 0){
						total = total + userService.batchInsert(cast);
						cast.clear();
					}
				}
				if(cast.size() > 0){
					total = total + userService.batchInsert(cast);
				}
				log.info("批量导入库中条数为" + total+ "................批量导入结束.......... Total cost:"+ (System.currentTimeMillis() - beginTime) / 1000.0);
				if (total != list.size()) {
					log.error("批量导入库有数据遗漏，实际导入：" + total+ "************************************应该导入："+ list.size());
				}
				log.info("批量导入库中条数为" + total + "................批量导入结束.......... Total cost:"+ (System.currentTimeMillis() - beginTime) / 1000.0);
				resultMap.clear();
				resultMap.put("total", total);
				resultMap.put("data", list);
			} else {
				log.error("读excel 文件为空************************************");
			}

		} else {
			log.error("导入excel 文件失败************************************");
		}
	}

	/**
	 * 批量录入卡信息(手工导入一段卡信息)
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/batchImport2")
	public void exprotIccid(HttpServletResponse response,
			HttpServletRequest request, int startIccid, int endIccid,
			String iccidHeader, IccidLibDto iccidDto, String type,
			String version, String iccidEnd) throws IOException {
		log.info("录入参数为:iccidHeader" + iccidHeader + "startIccid:" + startIccid
				+ "endIccid:" + endIccid + "iccidEnd:" + iccidEnd);

		List<IccidLibDto> list = new ArrayList<IccidLibDto>();
		IccidLibDto iccid = null;
		String format = "%0" + String.valueOf(endIccid).length() + "d";
		for (int i = startIccid; i <= endIccid; i++) {
			iccid = new IccidLibDto();
			iccid.setIccid(iccidHeader + String.format(format, i) + iccidEnd);
			iccid.setCustid(iccidDto.getCustid());
			iccid.setValue1(type);
			iccid.setValue2(version);
			iccid.setDevicetype(iccid.getValue1());
			iccid.setCardtype(iccid.getValue2());
			iccid.setOperators(iccidDto.getOperators());
			iccid.setInitproduct(iccidDto.getInitproduct());
			iccid.setAttribute1(iccidDto.getAttribute1());
			iccid.setAttribute2(iccidDto.getAttribute2());
			iccid.setCardstatus(iccidDto.getCardstatus());
			iccid.setIfMaintenance(iccidDto.getIfMaintenance());

			iccid.setMsisdn("no data");
			iccid.setPrivatekey("no data");
			iccid.setCtype("no data");
			list.add(iccid);
		}

		Long beginTime = System.currentTimeMillis();
		log.info("................批量导入开始...................time:" + beginTime);
		// 将对象插入数据库
		int total = 0;
		if (list != null && list.size() > 0) {
			String batchId = ZkGenerateSeq.getIdSeq(SeqID.BATCHID);
			List<IccidLibDto> cast = new ArrayList<IccidLibDto>();
			for (int i=0 ; i < list.size() ; i++) {
				list.get(i).setBatchId(batchId);
				cast.add(list.get(i));
				if(i%1000 == 0){
					total = total + userService.batchInsert(cast);
					cast.clear();
				}
			}
			if(cast.size() > 0){
				total = total + userService.batchInsert(cast);
			}
			log.info("批量导入库中条数为" + total+ "................批量导入结束.......... Total cost:"+ (System.currentTimeMillis() - beginTime) / 1000.0);
			if (total != list.size()) {
				log.error("批量导入库有数据遗漏，实际导入：" + total+ "************************************应该导入："+ list.size());
			}
		} else {
			log.info("批量导入库数据有误，请确认数据 ");
		}
		resultMap.put("total", total);
		resultMap.put("data", list);
	}

	/**
	 * 卡激活界面
	 */
	@RequestMapping("/iccidActivation")
	public ModelAndView deviceActived() {
		ModelAndView mv = new ModelAndView("/user/activation");
		return mv;
	}

	/**
	 * 得到上传的卡信息
	 */
	@RequestMapping("/queryIccid")
	@ResponseBody
	public Map<String, Object> queryIccid(String custId, String attribute1,
			String type, String attribute2, String version, String startIccid,
			String endIccid, HttpServletRequest request) {
		int pageStart = request.getParameter("iDisplayStart") == null ? 1
				: Integer.parseInt(request.getParameter("iDisplayStart")
						.toString());// 开始显示的项
		int pageSize = request.getParameter("iDisplayLength") == null ? 1
				: Integer.parseInt(request.getParameter("iDisplayLength")
						.toString());// 显示多少项
		if (("-1").equals(custId) || !isNumeric(custId)) {
			custId = "";
		}
		if (("-1").equals(type) || !isNumeric(type)) {
			type = "";
		}
		if (("-1").equals(version) || !isNumeric(version)) {
			version = "";
		}
		Map<String, Object> map = userService.queryIccidInfo(custId, "", type,
				"", version, startIccid, endIccid, pageStart, pageSize);
		return map;
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (StringUtils.isBlank(str))
			return false;
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 用户归档（生成订单信息）
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/userArchiving")
	@ResponseBody
	public String userArchiving(String custId, String type, String version,
			String startIccid, String endIccid, HttpServletRequest request) {
		if (("-1").equals(custId) || !isNumeric(custId)) {
			custId = "";
		}
		if (("-1").equals(type) || !isNumeric(type)) {
			type = "";
		}
		if (("-1").equals(version) || !isNumeric(version)) {
			version = "";
		}
		List<IccidLibDto> list = userService.queryIccids(custId, "", type, "", version, startIccid, endIccid);
		String tradeId = null;
		String result = null;
		String orderId = null;
		int restFlag = 0;
		if (list != null && list.size() > 0) {
			LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
			if (user != null) {
				boolean right = ActionUtil.ifSuperUser(request);
				if (right) {
					for (IccidLibDto iccidDto : list) {
						String custid = iccidDto.getCustid();
						String iccid = iccidDto.getIccid();
						String goodReleaseId = iccidDto.getInitproduct();
						GoodsReleaseDto goodsReleaseDto = goodsReleaseService.findBygoodsReleaseId(Integer.valueOf(iccidDto.getInitproduct()));
						try {
							// 调用一个方法得到tradeId
							tradeId = tradeService.addTrade(user,custid, orderId,iccid,goodsReleaseDto.getGoodsId().toString() ,goodReleaseId, "100",iccidDto.getIfMaintenance());
							if(StringUtil.isBlank(tradeId)){
								return "订单失败！";
							}
							if (orderId == null)
								orderId = tradeId;
							
							//算费
							TradeDto tradeDto = tradeService.queryTradeId(Long.parseLong(tradeId));
							if(goodsReleaseDto.getReleasePrice() != null && Double.parseDouble(goodsReleaseDto.getReleasePrice()) > 0){
							tradeFeeSubService.addTradeFeeSub(tradeDto);//算费
							TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
//							String fee = tradeFeeSubDto.getFee().toString();
							tradeDto.setFee(tradeFeeSubDto.getFee());
							tradeService.updateTrade(tradeDto);
							//收费
							PaymentDto payment = new PaymentDto();
							payment.setOrderId(tradeId);//订单号
							payment.setPayType(0);
							tradeFeeSubService.changePayTag(tradeId, payment);
							}
							 
							// 扣费
							 TradeFeeSubDto tradeFeeSubDto = tradeFeeSubService.queryTradeFreeSubByTradeId(tradeId);
							 HashMap<String,Object> paraMap = new HashMap<String,Object>();
							 paraMap.put("channelCustId", custid);//渠道客户ID
							 paraMap.put("tradeTypeCode", "100");//业务类型编码
							 paraMap.put("recvFee", WeixinUtil.getMoney(tradeFeeSubDto.getFee()));//缴费金额
							 paraMap.put("fee", WeixinUtil.getMoney(tradeFeeSubDto.getOldfee()));//应收费用
							 paraMap.put("discntFee", WeixinUtil.getMoney(tradeFeeSubDto.getFee()));//优惠后费用
							 paraMap.put("realFee", WeixinUtil.getMoney(tradeFeeSubDto.getFee()));//实收费用
							 paraMap.put("payTag", "0");//0：未缴费 1：用户已缴费 2：客户已缴费
							 paraMap.put("tradeId", tradeId);
							 paraMap.put("goodsId", tradeDto.getGoodsId());
							 paraMap.put("userId", tradeDto.getUserId());
							 restFlag = remainService.charge(paraMap);//扣费
							} catch (Exception e) {
								e.printStackTrace();
							}
						if(restFlag == 0){
							if (StringUtils.isBlank(tradeId)) {
								log.info("用户归档 中的tradeId  null*******************************************");
							} else {
								result = userService.userArchiving(tradeId);
							}
						}else{
							result = "扣费失败！";
						}

					}
				}else{
					result = "权限不足";
				}
			}
		}
		return result;
	}
}
