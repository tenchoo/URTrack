package com.urt.web.web.trafficQuery;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.urt.common.enumeration.ConstantEnum;
import com.urt.dto.LaoCustGroupDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.traffic.LaoTrafficMmDto;
import com.urt.dto.traffic.PackagePlanDto;
import com.urt.dto.traffic.TrafficQueryDetailsDto;
import com.urt.dto.traffic.TrafficQueryNowDto;
import com.urt.interfaces.task.TrafficTaskService;
import com.urt.interfaces.traffic.TrafficQueryService;
import com.urt.web.common.util.ActionUtil;
import com.urt.web.common.util.ICCID;
import com.urt.web.web.batchQuery.ExcelUtil;

/**
 * 用户流量查询
 * 
 * @date 2016年8月23日15:40:42
 */
@Controller
@RequestMapping("/traffic")
public class TrafficQueryController {

	private static final Logger log = Logger.getLogger(TrafficQueryController.class);

	@Autowired
	TrafficQueryService trafficQueryService;
	@Autowired
	TrafficTaskService trafficTaskService;

	@RequestMapping(value = "/toQueryView")
	public ModelAndView toQueryView() {
		ModelAndView mv = new ModelAndView("/traffic/trafficQuery");
		return mv;
	}

	/**
	 * 按日期查询流量
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doTrafficQuery")
	public Map<String,Object> doDateTrafficQuery(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> resultMap=new HashMap<String, Object>();
		List<TrafficQueryDetailsDto> list = null;
		String iccid = request.getParameter("iccid");
		String imsi = request.getParameter("imsi");
		if (iccid.length() == 19 && "898606".equals(iccid.substring(0, 6))) {
			iccid = ICCID.replaceLastChar(iccid);
		}
		if(StringUtils.isNotEmpty(iccid.trim())){
			imsi=trafficQueryService.getImsiByIccid(iccid);
		}else if(StringUtils.isNotEmpty(imsi.trim())){
			iccid=trafficQueryService.getIccidByImsi(imsi);
        }
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		Long custId = user.getCustId();

		if (!ActionUtil.ifSuperUser(request) && !trafficQueryService.bIsLegalIccId(iccid, custId)) {// 不是已登陆客户或者其渠道发展客户，不展示剩余流量
			list = new ArrayList<TrafficQueryDetailsDto>();
			TrafficQueryDetailsDto dto = new TrafficQueryDetailsDto();
			dto.setExpMsg("您没有权限查询该ICCID的流量使用信息！");
			dto.setDataVolume("");
			dto.setSessionStartTime("");
			list.add(dto);
			resultMap.put("list", list);
			resultMap.put("iccid", iccid);
			resultMap.put("imsi", imsi);
			return resultMap;
		}

		String selectType = request.getParameter("selectType");
		try {
			if ("dayQuery".equals(selectType)) {
				String dayDate = request.getParameter("dayDate");
				list = trafficQueryService.doDayTrafficQuery(iccid, dayDate);
				if (list != null && list.size() > 0) {
					TrafficQueryDetailsDto dto = list.get(list.size() - 1);
					dto.setDataVolume("总计：" + dto.getDataVolume());
					dto.setSessionStartTime(dto.getSessionStartTime() + "(该天)");
				} else {
					list = new ArrayList<TrafficQueryDetailsDto>();
					TrafficQueryDetailsDto dtoCount = new TrafficQueryDetailsDto();
					dtoCount.setDataVolume("总计： 0");
					dtoCount.setSessionStartTime(dayDate + "(该天)");
					list.add(dtoCount);
				}
			} else if ("monthQuery".equals(selectType)) {
				String monthDate = request.getParameter("monthDate");
				list = trafficQueryService.doMonthTrafficQuery(iccid, monthDate);
				double count = 0;
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						TrafficQueryDetailsDto dto = list.get(i);
						count = count + Double.parseDouble(dto.getDataVolume());
					}
					DecimalFormat decimalFormat = new DecimalFormat("#0.000");// 格式化设置
					TrafficQueryDetailsDto dtoCount = new TrafficQueryDetailsDto();
					dtoCount.setDataVolume("总计：" + decimalFormat.format(count));
					dtoCount.setSessionStartTime(monthDate + "(该月)");
					list.add(dtoCount);
				} else {
					list = new ArrayList<TrafficQueryDetailsDto>();
					TrafficQueryDetailsDto dtoCount = new TrafficQueryDetailsDto();
					dtoCount.setDataVolume("总计： 0");
					dtoCount.setSessionStartTime(monthDate + "(该月)");
					list.add(dtoCount);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("list", list);
		resultMap.put("iccid", iccid);
		resultMap.put("imsi", imsi);
		return resultMap;
	}

	/**
	 * 剩余查询流量
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doNowTrafficQuery")
	public Map<String,Object> doDateTrafficQuery1(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> mv=new HashMap<String, Object>();
		String iccid = request.getParameter("iccid");
		String imsi = request.getParameter("imsi");
		
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		Long custId = user.getCustId();

		if (iccid.length() == 19 && "898606".equals(iccid.substring(0, 6))) {
			iccid = ICCID.replaceLastChar(iccid);
		}
		
		if(StringUtils.isNotEmpty(iccid.trim())){
			imsi=trafficQueryService.getImsiByIccid(iccid);
		}else if(StringUtils.isNotEmpty(imsi.trim())){
			iccid=trafficQueryService.getIccidByImsi(imsi);
        }

		if (!ActionUtil.ifSuperUser(request) && !trafficQueryService.bIsLegalIccId(iccid, custId)) {// 不是已登陆客户或者其渠道发展客户，不展示剩余流量
			mv.put("trafficDto", null);
			mv.put("iccid", iccid);
			mv.put("expMsg", "你没有权限查询该ICCID的剩余流量信息！");
			return mv;
		}
		TrafficQueryNowDto trafficDto = null;
		trafficDto = trafficQueryService.doNowTrafficQuery(iccid);
		String expMsg = null;
		if (trafficDto != null) {
			expMsg = trafficDto.getExpMsg();
			if(trafficDto.getIccid()==null){
				trafficDto.setIccid("");
			}
			if(trafficDto.getChannelCustName()==null){
				trafficDto.setChannelCustName("");
			}
			if(trafficDto.getDataRemaining()==null){
				trafficDto.setDataRemaining("");
			}
			if(trafficDto.getExpirationDate()==null){
				trafficDto.setExpirationDate("");
			}
			if(trafficDto.getMsisdn()==null){
				trafficDto.setMsisdn("");
			}
			if(trafficDto.getOperatorName()==null){
				trafficDto.setOperatorName("");
			}
			if(trafficDto.getRatePlanName()==null){
				trafficDto.setRatePlanName("");
			}
			if(trafficDto.getState()==null){
				trafficDto.setState("");
			}
			if(trafficDto.getType()==null){
				trafficDto.setType("");
			}
			if(trafficDto.getUserName()==null){
				trafficDto.setUserName("");
			}
			if(trafficDto.getVersion()==null){
				trafficDto.setVersion("");
			}
//			List<PackagePlanDto> listPack=new ArrayList<PackagePlanDto>(); 
//			listPack= trafficDto.getListPack();
//			mv.put("listPack",listPack);
		}
//		mv.put("operatorId", trafficDto.getOperatorId());
		mv.put("trafficDto", trafficDto);
		mv.put("iccid", iccid);
		mv.put("imsi", imsi);
		mv.put("expMsg", expMsg);
		return mv;
	}

	@ResponseBody
	@RequestMapping("/selectMonthFlowCount")
	public Map<String, Object> selectMonthFlowCount(HttpServletRequest req) throws Exception {
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		Long custId = user.getCustId();

		Map<String, Object> map = trafficQueryService.selectMonthFlowCount(custId);

		return map;
	}
	//近15天流量使用按MB显示
	@ResponseBody
	@RequestMapping("/selectMonthFlowCountMB")
	public Map<String, Object> selectMonthFlowCountMB(HttpServletRequest req) throws Exception {
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		Long custId = user.getCustId();
		
		Map<String, Object> map = trafficQueryService.selectMonthFlowCountMB(custId);
		
		return map;
	}

	@ResponseBody
	@RequestMapping("/selectPrefixFlow")
	public Map<String, Object> selectPrefixFlow(HttpServletRequest req) throws Exception {
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		Long custId = user.getCustId();

		Map<String, Object> maxMinMap = trafficQueryService.selectPrefixFlow(custId);

		return maxMinMap;
	}

	@RequestMapping(value = "/toQueryMmView")
	public ModelAndView toQuery2View(HttpServletRequest request) {
		// 国际化
		// Locale locale=request.getLocale();
		// String language = request.getParameter("language");
		// if(language!=null&&language!=""){
		// String[] lan = language.split("_");
		// locale=new Locale(lan[0], lan[1]);
		// }

		ModelAndView mv = new ModelAndView("/traffic/trafficQueryMm");
		Map<String, String> info = trafficQueryService.getUpdateTimeInfo();
		mv.addObject("info", info);
		// mv.addObject("loc", locale);
		return mv;
	}

	/**
	 * 进入查询页面 加载渠道客户名称
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getChannelCust")
	public List<Map<String, Object>> getGroupList(HttpServletRequest request) {
		Map<String, Object> initMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		List<LaoCustGroupDto> dtos = trafficQueryService.selectAll();
		initMap.put("text", "请选择");
		initMap.put("id", -1);
		list.add(initMap);
		if (!ActionUtil.ifSuperUser(request)) {
			// 企业客户登录时 只显示该客户的渠道客户名称,没登陆的时候，显示联想客户的
			String custName = "";
			Long custId = (user.getCustId() == null || user.getCustId().equals("")) == true
					? ConstantEnum.CUSTID.getCode() : user.getCustId();
			Map<String, Object> initMap1 = new HashMap<String, Object>();
			for (LaoCustGroupDto dto : dtos) {
				if (custId.equals(dto.getCustId())) {
					custName = dto.getCustName();
					break;
				}
			}

			initMap1.put("text", custName);
			initMap1.put("id", user.getCustId());
			list.add(initMap1);
			return list;
		}

		for (LaoCustGroupDto dto : dtos) {
			// 管理员登录时
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", dto.getCustName());
			map.put("id", dto.getCustId());
			list.add(map);
		}
		return list;
	}

	/**
	 * 按渠道客户ID查询 月流量表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doQuery")
	public Map<String, Object> doDateQueryMm(HttpServletRequest request, HttpServletResponse response) {
		// DOTO 注意-1和-2
		String type = request.getParameter("type");// 流量查询类型，1、使用流量查询，2、剩余流量查询
		long channelCustId = Long.parseLong(request.getParameter("channelCustId"));
		String useCount1 = request.getParameter("useCount1");
		String useCount2 = request.getParameter("useCount2");
		String month = request.getParameter("month");
		String value1 = request.getParameter("value1");
		String value2 = request.getParameter("value2");
		String operatorId=request.getParameter("operatorId");
		String goodsId=request.getParameter("goodsId");
		Map<String, Object> resultMap = null;
		resultMap = trafficQueryService.selectDataSpread(channelCustId, type,month,
				Integer.parseInt(useCount1) == -1 ? null : Integer.parseInt(useCount1) * 1024,
				Integer.parseInt(useCount2) == -1 ? null : Integer.parseInt(useCount2) * 1024,
				"-1".equals(value1) ? null : value1, "-1".equals(value2) ? null : value2,
				operatorId,goodsId);
		log.info("echarts中的数据++++++++++++++++++++++++++++++++++++++++++++" + resultMap);
		System.out.println(resultMap);
		return resultMap;
	}

	/**
	 * 按渠道客户ID查询 月流量表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doQueryMm")
	public Map<String, Object> doDateTrafficQueryMm(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map0 = new HashMap<String, Object>();
		List<LaoTrafficMmDto> list = null;
		long channelCustId = Long.parseLong(request.getParameter("channelCustId"));
		String dataCycle = new SimpleDateFormat("yyyyMM").format(new Date());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channelCustId", channelCustId);
		map.put("dataCycle", dataCycle);
		// 该企业下总人数
		int countAll = trafficQueryService.selectCountByChannel(map);
		String useCount1 = request.getParameter("useCount1");
		String useCount2 = request.getParameter("useCount2");
		double userCountMin = 0;
		double userCountMax = 0;
		if (!StringUtils.isBlank(useCount1)) {
			double useCountd1 = Double.parseDouble(useCount1) * 1024;
			userCountMin = useCountd1;
			map.put("useCount1", useCountd1);
		}
		if (!StringUtils.isBlank(useCount2)) {
			double useCountd2 = Double.parseDouble(useCount2) * 1024;
			userCountMax = useCountd2;
			map.put("useCount2", useCountd2);
		}
		// 满足条件的用户详细信息
		list = trafficQueryService.selectByTraffic(map);
		// 满足条件的人数
		int count = list.size();
		map0.put("count", count);
		double score = 0;
		if (countAll != 0) {
			score = count * 100 / countAll;
		}
		BigDecimal b = new BigDecimal(score);
		score = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		// 占百分百,保留俩位小数
		map0.put("score", score);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				double useCounti = list.get(i).getUseCount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (userCountMax < useCounti) {
					userCountMax = useCounti;
				}
				if (userCountMin > useCounti) {
					userCountMin = useCounti;
				}
			}
		}
		// 根据使用量.剩余量的最大值平均分10等份
		double[] xStr = new double[10];
		for (int i = 0; i < xStr.length; i++) {
			xStr[i] = (userCountMax - userCountMin) / 9 * i + userCountMin;
		}
		int[] yStr1 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		for (int i = 0; i < list.size(); i++) {
			double useCount3 = list.get(i).getUseCount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			if (useCount3 <= xStr[0]) {
				yStr1[0]++;
			}
			for (int j = 1; j < xStr.length; j++) {
				if (xStr[j - 1] < useCount3 && useCount3 <= xStr[j]) {
					yStr1[j]++;
				}
			}
		}
		for (int i = 0; i < xStr.length; i++) {
			BigDecimal big = new BigDecimal(xStr[i] / 1024);
			xStr[i] = big.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		map0.put("xStr", xStr);
		map0.put("yStr1", yStr1);
		return map0;
	}

	/**
	 * 按渠道客户ID查询 月流量表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dataRemainingDoQueryMm")
	public Map<String, Object> doDateTrafficQueryMm2(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map0 = new HashMap<String, Object>();
		List<LaoTrafficMmDto> list = null;
		long channelCustId = Long.parseLong(request.getParameter("channelCustId"));
		String dataCycle = new SimpleDateFormat("yyyyMM").format(new Date());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channelCustId", channelCustId);
		map.put("dataCycle", dataCycle);
		// 该企业下总人数
		int countAll = trafficQueryService.selectCountByChannel(map);
		String dataRemaining1 = request.getParameter("dataRemaining1");
		String dataRemaining2 = request.getParameter("dataRemaining2");
		double userCountMin = 0;
		double userCountMax = 0;
		if (!StringUtils.isBlank(dataRemaining1)) {
			double dataRemainingd1 = Double.parseDouble(dataRemaining1) * 1024;
			userCountMin = dataRemainingd1;
			map.put("dataRemaining1", dataRemainingd1);
		}
		if (!StringUtils.isBlank(dataRemaining2)) {
			double dataRemainingd2 = Double.parseDouble(dataRemaining2) * 1024;
			userCountMax = dataRemainingd2;
			map.put("dataRemaining2", dataRemainingd2);
		}
		// 满足条件的用户详细信息
		list = trafficQueryService.selectByTraffic(map);
		// 满足条件的人数
		int count = list.size();
		map0.put("count", count);
		double score = 0;
		if (countAll != 0) {
			score = count * 100 / countAll;
		}
		BigDecimal b = new BigDecimal(score);
		score = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		// 占百分百,保留俩位小数
		map0.put("score", score);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				double dataRemainingi = list.get(i).getDataRemaining().setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				if (userCountMax < dataRemainingi) {
					userCountMax = dataRemainingi;
				}
				if (userCountMin > dataRemainingi) {
					userCountMin = dataRemainingi;
				}
			}
		}
		// 根据使用量.剩余量的最大值平均分10等份
		double[] xStr = new double[10];
		for (int i = 0; i < xStr.length; i++) {
			xStr[i] = (userCountMax - userCountMin) / 9 * i + userCountMin;
		}
		int[] yStr2 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		for (int i = 0; i < list.size(); i++) {
			double dataRemaining3 = list.get(i).getDataRemaining().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			if (dataRemaining3 <= xStr[0]) {
				yStr2[0]++;
			}
			for (int j = 1; j < xStr.length; j++) {
				if (xStr[j - 1] < dataRemaining3 && dataRemaining3 <= xStr[j]) {
					yStr2[j]++;
				}
			}
		}
		for (int i = 0; i < xStr.length; i++) {
			BigDecimal big = new BigDecimal(xStr[i] / 1024);
			xStr[i] = big.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		map0.put("xStr", xStr);
		map0.put("yStr2", yStr2);
		return map0;
	}

	/**
	 * 导出本月流量使用详细情况表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "exportExcel")
	public String download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileName = "本月流量使用详细情况表";
		List<LaoTrafficMmDto> listDao = null;
		long channelCustId = Long.parseLong(request.getParameter("channelCustId"));
		String dataCycle = new SimpleDateFormat("yyyyMM").format(new Date());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channelCustId", channelCustId);
		map.put("dataCycle", dataCycle);
		String useCount1 = request.getParameter("useCount1");
		String useCount2 = request.getParameter("useCount2");
		String dataRemaining1 = request.getParameter("dataRemaining1");
		String dataRemaining2 = request.getParameter("dataRemaining2");
		if (!StringUtils.isBlank(useCount1)) {
			double useCountd1 = Double.parseDouble(useCount1) * 1024;
			map.put("useCount1", useCountd1);
		}
		if (!StringUtils.isBlank(useCount2)) {
			double useCountd2 = Double.parseDouble(useCount2) * 1024;
			map.put("useCount2", useCountd2);
		}
		if (!StringUtils.isBlank(dataRemaining1)) {
			double dataRemainingd1 = Double.parseDouble(dataRemaining1) * 1024;
			map.put("dataRemaining1", dataRemainingd1);
		}
		if (!StringUtils.isBlank(dataRemaining2)) {
			double dataRemainingd2 = Double.parseDouble(dataRemaining2) * 1024;
			map.put("dataRemaining2", dataRemainingd2);
		}
		try {
			// 满足条件的用户详细信息
			listDao = trafficQueryService.selectByTraffic(map);
			// 用户属性名称查询
			Map<String, Object> mapStaticName = trafficQueryService.selectStaticName(channelCustId);
			List<Map<String, Object>> list = createExcelRecord(listDao);
			String staticNamea = "";
			String staticNameb = "";
			if (mapStaticName != null) {
				staticNamea = (String) mapStaticName.get("STATIC_NAMEA");
				staticNameb = (String) mapStaticName.get("STATIC_NAMEB");
			}
			String[] columnNames = { "批次流水号", "iccid", "服务号码", "已使用流量截止日期", "已使用流量(MB)", "查询时间", "截止查询时间剩余流量(MB)",
					staticNamea, staticNameb };// 列名
			String[] keys = { "batchId", "iccid", "msisdn", "dataAdded", "useCount", "updateTime", "dataRemaining",
					"staticNameA", "staticNameB" };// map中的key
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			try {
				ExcelUtil.createWorkBook(list, keys, columnNames).write(os);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			// 进行转码，使其支持中文文件名
			String codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
			// System.out.println("文件名转码:"+codedFileName);
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((codedFileName + ".xls").getBytes(), "utf-8"));
			ServletOutputStream out = response.getOutputStream();
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
				bis = new BufferedInputStream(is);
				bos = new BufferedOutputStream(out);
				byte[] buff = new byte[2048];
				int bytesRead;
				// Simple read/write loop.
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
			} catch (final IOException e) {
				throw e;
			} finally {
				if (bos != null)
					bos.close();
				if (bis != null)
					bis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	private List<Map<String, Object>> createExcelRecord(List<LaoTrafficMmDto> listDao) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", "sheet1");
		listmap.add(map);
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		for (int j = 0; j < listDao.size(); j++) {
			LaoTrafficMmDto dto = listDao.get(j);
			Date date = dto.getUpdateTime();
			String dateStr = format.format(date);
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
			String dataAdded = "";
			try {
				dataAdded = sdf2.format(sdf1.parse(dto.getDataAdded()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String strUseCount = "";
			String strDataRemain = "";
			// 单位转化为MB
			DecimalFormat df = new DecimalFormat("######0.00");
			if (dto.getUseCount() != null) {
				double dUseCount = dto.getUseCount().setScale(BigDecimal.ROUND_HALF_UP).doubleValue();
				strUseCount = df.format(dUseCount / 1024);
			}
			if (dto.getDataRemaining() != null) {
				double dDataRemain = dto.getDataRemaining().setScale(BigDecimal.ROUND_HALF_UP).doubleValue();
				strDataRemain = df.format(dDataRemain / 1024);
			}
			Map<String, Object> mapValue = new HashMap<String, Object>();
			mapValue.put("batchId", dto.getBatchId());
			mapValue.put("iccid", dto.getIccid());
			mapValue.put("msisdn", dto.getMsisdn());
			mapValue.put("dataAdded", dataAdded);
			mapValue.put("useCount", strUseCount);
			mapValue.put("updateTime", dateStr);
			mapValue.put("dataRemaining", strDataRemain);
			mapValue.put("staticNameA", dto.getStaticNameA());
			mapValue.put("staticNameB", dto.getStaticNameB());
			Date activeDate = dto.getActiveDate();
			mapValue.put("activeDate", activeDate == null ? "" : format.format(activeDate));
			mapValue.put("endDate", format.format(dto.getEndDate()));
			mapValue.put("cardStatus", dto.getCardStatus());
			mapValue.put("imsi", dto.getImsi());
			mapValue.put("imei", dto.getImei());
			mapValue.put("deviceType", dto.getType());
			listmap.add(mapValue);
		}
		return listmap;
	}

	/**
	 * 进入查询页面 加载渠道客户名称
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPersonal")
	public List<Map<String, Object>> getAllChannelCust(HttpServletRequest request) {
		Map<String, Object> initMap = new HashMap<String, Object>();
		Map<String, Object> initMapq = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		List<LaoCustGroupDto> dtos = trafficQueryService.selectAll();
		initMapq.put("text", "请选择");
		initMapq.put("id", -1);
		if (!ActionUtil.ifSuperUser(request)) {
			// 企业客户登录时 只显示该客户的渠道客户名称
			String custName = "";
			for (LaoCustGroupDto dto : dtos) {
				long custId = dto.getCustId();
				if (custId == user.getCustId()) {
					custName = dto.getCustName();
				}
			}
			initMap.put("text", custName);
			initMap.put("id", user.getCustId());
		}
		list.add(initMapq);
		list.add(initMap);
		return list;
	}

	public static void main(String[] args) {
		// String a="100";
		// String b="100";
		// System.out.println(a==b);
		Integer a = 200;
		Integer b = 200;
		System.out.println(a == b);

	}
}
