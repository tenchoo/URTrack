package com.urt.web.web.User;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.urt.dto.IccidBatchdataDto;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoSsStaticDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.Goods.OperatorsDto;
import com.urt.dto.Goods.ServiceStatusDto;
import com.urt.interfaces.Goods.GoodsOrderService;
import com.urt.interfaces.Goods.OperatorsService;
import com.urt.interfaces.Goods.ServiceStatusService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.authority.TagService;
import com.urt.interfaces.batch.BatchService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
import com.urt.web.common.util.ActionUtil;
import com.urt.web.common.util.ExcelUtilsImportNew;
import com.urt.web.common.util.ImportExcelUtils;

/**
 * 类说明：批量录入
 * 
 * @author lingfei
 * @date 2017年03月06日
 */
@Controller
@RequestMapping("/userNewService")
public class UserNewController {

	private final Logger log = Logger.getLogger(getClass().getName());

	@Autowired
	private UserService userService;

	@Autowired
	private OperatorsService operatorService;

	@Autowired
	private ServiceStatusService serviceStatusService;

	@Autowired
	private GoodsOrderService laoGoodsOrderService;

	@Autowired
	private BatchService batchService;

	@Autowired
	private TagService tagService;
	
	/**
	 * 上传excel文件信息界面
	 */
	@RequestMapping("/uploadExcelNew")
	public ModelAndView uploadExcel() {
		List<OperatorsDto> operators = operatorService.findOperators();
		ModelAndView mv = new ModelAndView("/user/uploadNew");
		mv.addObject("operatorList", operators);
		return mv;
	}

	// 录入明细
	@RequestMapping("/queryDetail")
	public ModelAndView queryDetail(HttpServletResponse response, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("user/queryDetail");
		String batchId = request.getParameter("batchId");
		mv.addObject("batchId", batchId);
		return mv;
	}

	// 录入信息
	@RequestMapping("/queryOneDetail")
	public ModelAndView queryOneDetail(HttpServletResponse response, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("user/queryOneDetail");
		Long batchId = Long.parseLong(request.getParameter("batchId"));
		IccidBatchdataDto dto = userService.selectByBatchId(batchId);
		Map<String, String> map = userService.selectOneDetailByBatchId(batchId + "");
		if (map != null) {
			mv.addObject("map", map);
		}
		mv.addObject("batchId", batchId);
		mv.addObject("dto", dto);
		return mv;
	}

	/**
	 * 根据运行商查询服务状态
	 */
	@ResponseBody
	@RequestMapping("/getCardstatusList")
	public List<Map<String, Object>> getCardstatusList(HttpServletResponse response, String operators) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> initMap = new HashMap<String, Object>();
		initMap.put("text", "请选择");
		initMap.put("id", -1);
		list.add(initMap);
		List<ServiceStatusDto> dtolist = serviceStatusService.selectByOperatorId(operators);
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
	public List<Map<String, Object>> getGoodRealses(HttpServletResponse response, String custId, String value1,
			String value2, String operatorsId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> initMap = new HashMap<String, Object>();
		initMap.put("text", "请选择");
		initMap.put("id", -1);
		list.add(initMap);
		List<GoodsDto> dtolist = laoGoodsOrderService.queryLaoGoods(custId, operatorsId, value1, value2);
		Map<String, Object> selectMap = null;
		if (dtolist != null && dtolist.size() > 0) {
			for (GoodsDto goodsDto : dtolist) {
				selectMap = new HashMap<String, Object>();
				selectMap.put("text", goodsDto.getGoodsName() + "(价格:" + goodsDto.getGoodsPrices() + "元;周期:"
						+ goodsDto.getReleaseCycle() + "月)");
				selectMap.put("id", goodsDto.getGoodsReleaseId());
				list.add(selectMap);
			}
		}
		return list;
	}

	/**
	 * 分页查询批量录入总表记录
	 */
	@ResponseBody
	@RequestMapping("/getIccidInfo")
	public Map<String, Object> getIccidInfo(HttpServletRequest req, HttpServletResponse resp, IccidBatchdataDto dto)
			throws Exception {
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;
		
		Map<String, Object> resultMap = userService.selectByPage(dto, pageNo, pageSize);
		System.out.println(resultMap);
		return resultMap;
	}

	/**
	 * 分页查询批量录入明细表记录lao_iccid_lib
	 */
	@ResponseBody
	@RequestMapping("/getDetailIccidInfo")
	public Map<String, Object> getDetailIccidInfo(HttpServletRequest req, HttpServletResponse resp, IccidLibDto dto)
			throws Exception {
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;
		Map<String, Object> resultMap = userService.selectDetailByPage(dto, pageNo, pageSize);
		return resultMap;
	}

	/**
	 * Excel导入"录入信息"
	 * 
	 * @param file2
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/batchImportNewMsg")
	public IccidBatchdataDto uploadNewMsg(@RequestParam(value = "file2") MultipartFile file2) {
		IccidBatchdataDto batchdataDto = null;

		try {
			if (!file2.isEmpty()) {
				ExcelUtilsImportNew excelUtilsImportNew = new ExcelUtilsImportNew();
				if (file2.getOriginalFilename().endsWith("xlsx")) {
					batchdataDto = excelUtilsImportNew.getIccidBatchdataDto(file2.getInputStream(), true);
				} else {
					batchdataDto = excelUtilsImportNew.getIccidBatchdataDto(file2.getInputStream(), false);
				}
				if (batchdataDto == null) {
					log.info("................上传Excel有问题！..............");
				}
			}
		} catch (IOException e) {
			log.error("--------------解析Excel异常---------------！");
			e.printStackTrace();
		}
		return batchdataDto;
	}
	/**
	 * 卡批量录入（使用excel）
	 * 
	 * @param file
	 * @param request
	 * @return
	 * @throws IOException
	 */	
	@ResponseBody
	@RequestMapping("/batchImportNew")
	public Map<String, Object> upload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request,IccidBatchdataDto IccidDto) throws IOException {
		ImportExcelUtils<IccidLibDto> utils = null;
		List<IccidLibDto> list = null;
		Long beginTime = System.currentTimeMillis();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 判断上传文件，如果不为空，将之转换成对象
		if (!file.isEmpty()) {
			utils = new ImportExcelUtils<IccidLibDto>();
			if (file.getOriginalFilename().endsWith("xlsx")) {
				list = utils.importExcel(file.getInputStream(), new IccidLibDto(), true);
			} else {
				list = utils.importExcel(file.getInputStream(), new IccidLibDto(), false);
			}
			long batchId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID));
                                  
			// 插入批量录入总表lao_iccid_batchdata
			int count = 0;
			try {
				IccidBatchdataDto batchdataDto = new IccidBatchdataDto();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if (batchdataDto != null) {
					batchdataDto.setFileName(file.getOriginalFilename());//录入文件名
					batchdataDto.setBatchId(batchId);
					batchdataDto.setSimType(IccidDto.getSimType());
					batchdataDto.setSimSize(IccidDto.getSimSize());
					batchdataDto.setIccidStart(IccidDto.getIccidStart());
					batchdataDto.setIccidEnd(IccidDto.getIccidEnd());
					batchdataDto.setRecvTime(new Date());
					batchdataDto.setRsrvInfo1("");// 一级标识名称
					batchdataDto.setRsrvInfo2("");// 二级标识名称
					batchdataDto.setPayment("0");
					batchdataDto.setGenerateinfor("1");
					batchdataDto.setSumNum(IccidDto.getSumNum());
					count = userService.insertSelective(batchdataDto);
				}
				log.info("批量总表库中新增条数为" + count + ".......................... ");
			} catch (Exception e) {
				log.error("插入数据库lao_iccid_batchdata失败！", e);
				resultMap.put("msg", "导入失败！");				
			}		
		}
		return resultMap;

		
	}       
	/**
	 * 批量录入卡信息(使用excel导入信息)
	 * 
	 * @throws IOException
	 * 
	 */
	
	@ResponseBody
	@RequestMapping("/batchImport")
	public Map<String, Object> upload(@RequestParam(value = "file") MultipartFile file, IccidLibDto iccidDto,
			IccidBatchdataDto batchdataDto, String type, String version, String type1, String version1, String delDate,
			String payment, String generateUserInfor, HttpServletRequest request) throws IOException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if (batchdataDto.getNumberStart().length() > 20 || batchdataDto.getNumberEnd().length() > 20
				|| batchdataDto.getPullNumber().length() > 20) {
			resultMap.put("msg", "批量导入数据有误，请确认数据(手机号码超长)");
			return resultMap;
		}
		ImportExcelUtils<IccidLibDto> utils = null;
		// BatchOperationUtils utilsBat = null;
		// List<Map<String,Object>> listMap = null;
		List<IccidLibDto> list = null;
		Long beginTime = System.currentTimeMillis();

		String typeName = "";
		String versionName = "";
		if (iccidDto != null) {
			
			LaoSsStaticDto typeDto = tagService.getStaticByCustIdCode(type1, "10");
			typeName = typeDto.getStaticName();
			LaoSsStaticDto versionDto = tagService.getStaticByCustIdCode(version1, "10");
			versionName = versionDto.getStaticName();
		}
		log.info("................批量导入开始...................time:" + beginTime);
		// 判断上传文件，如果不为空，将之转换成对象
		if (!file.isEmpty()) {
			utils = new ImportExcelUtils<IccidLibDto>();
			// utilsBat = new BatchOperationUtils();
			iccidDto.setValue1(type);
			iccidDto.setValue2(version);
			if (file.getOriginalFilename().endsWith("xlsx")) {
				list = utils.importExcel(file.getInputStream(), iccidDto, true);
			} else {
				list = utils.importExcel(file.getInputStream(), iccidDto, false);
			}
			// if (file.getOriginalFilename().endsWith("xlsx")) {
			// listMap = utilsBat.importExcel(file.getInputStream(), true);
			// } else {
			// listMap = utilsBat.importExcel(file.getInputStream(), false);
			// }
			long batchId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID));

			// 插入批量录入总表lao_iccid_batchdata
			int count = 0;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if (batchdataDto != null) {
					//batchdataDto.setFileName(file.getOriginalFilename());//录入文件名
					batchdataDto.setBatchId(batchId);
					batchdataDto.setRecvTime(new Date());
					batchdataDto.setRsrvInfo1(typeName);// 一级标识名称
					batchdataDto.setRsrvInfo2(versionName);// 二级标识名称
					if (!StringUtils.isBlank(delDate)) {
						batchdataDto.setDeliveryDate(sdf.parse(delDate));
					}
					if (payment != null) {
						batchdataDto.setPayment("0");
					} else {
						batchdataDto.setPayment("1");
					}
					if (generateUserInfor != null) {
						batchdataDto.setGenerateinfor("0");
					} else {
						batchdataDto.setGenerateinfor("1");
					}
					count = userService.insertSelective(batchdataDto);
				}
				log.info("批量总表库中新增条数为" + count + ".......................... ");
			} catch (Exception e) {
				log.error("插入数据库lao_iccid_batchdata失败！");
				e.printStackTrace();
				resultMap.put("msg", "导入失败！");
				return resultMap;
			}
			// 将对象插入明细表lao_iccid_lib
			int total = 0;
			if (list != null && list.size() > 0) {
				List<IccidLibDto> cast = new ArrayList<IccidLibDto>();
				try {
					for (int i = 0; i < list.size(); i++) {
						list.get(i).setBatchId(batchId + "");
						list.get(i).setAttribute1(type1);
						list.get(i).setAttribute2(version1);
						cast.add(list.get(i));
						if ((i) % 1000 == 0 && i != 0) {
							total = total + userService.batchInsert(cast);
							cast.clear();
						}
					}
					if (cast.size() > 0) {
						total = total + userService.batchInsert(cast);
					}
				} catch (Exception e) {
					log.error("插入数据库lao_iccid_lib失败！");
					e.printStackTrace();
					resultMap.put("msg", "导入失败！");
					return resultMap;
				}
				log.info("批量导入库中条数为" + total + "................批量导入结束.......... Total cost:"
						+ (System.currentTimeMillis() - beginTime) / 1000.0);
				if (total != list.size()) {
					log.error("批量导入库有数据遗漏，实际导入：" + total + "************************************应该导入：" + list.size());
				}
				log.info("批量导入库中条数为" + total + "................批量导入结束.......... Total cost:"
						+ (System.currentTimeMillis() - beginTime) / 1000.0);
			} else {
				log.error("读excel 文件为空************************************");
			}
			resultMap.put("msg", "成功导入" + total + "条");
		} else {
			resultMap.put("msg", "导入excel 文件失败");
			log.error("导入excel 文件失败************************************");
			resultMap.put("msg", "导入失败！");
			return resultMap;
		}
		/*
		 * Long custId = Long.parseLong(iccidDto.getCustid()); Long accountId =
		 * 0L; LaoSsAccountDto
		 * user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser"
		 * ); if(user != null) { accountId = user.getAcconutId(); } // 判断是否已缴费
		 * 调用余额中心的接口 log.info("--------------------判断是否已缴费  调用余额中心的接口"); if
		 * (payment != null) { Long orderFee =
		 * Long.parseLong(batchdataDto.getOrderFee()); int intPay = 0; try {
		 * intPay = remainService.paymentOut(100 * orderFee + "", custId,
		 * accountId); } catch (Exception e) {
		 * log.info("---------------调用余额中心的接口出现异常-------------------");
		 * e.printStackTrace(); } log.info("---------------调用余额中心的接口完毕  intPay:"
		 * + intPay); } // 判断是否生成在维用户信息 向kafka发消息
		 * log.info("--------------------判断是否生成在维用户信息"); if (generateUserInfor
		 * != null) { String cardstatus = iccidDto.getCardstatus(); boolean
		 * right = ActionUtil.ifSuperUser(request); String ifAdmin = ""; if
		 * (right) { ifAdmin = "1"; } Map<String, Object> result = new
		 * HashMap<String, Object>(); try { result =
		 * batchService.batchImport(listMap, custId + "", cardstatus, "1",
		 * accountId + "", ifAdmin); } catch (Exception e) {
		 * log.info("---------------调用生成在为用户信息接口出现异常---------------------");
		 * e.printStackTrace(); } log.info(
		 * "---------------调用生成在为用户信息接口完毕  result:" + result); }
		 */
		return resultMap;
	}       
	     
	/**
	 * 批量录入卡信息(手工导入一段卡信息)
	 * 
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/batchImport2")
	public Map<String, Object> exprotIccid(HttpServletResponse response, HttpServletRequest request, int startIccid,
			int endIccid, String iccidHeader, IccidLibDto iccidDto, String type, String version, String type1,
			String version1, String iccidTail, IccidBatchdataDto batchdataDto, String delDate, String payment,
			String generateUserInfor) throws IOException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// List<Map<String,Object>> listMap = null;
		log.info("录入参数为:iccidHeader" + iccidHeader + "startIccid:" + startIccid + "endIccid:" + endIccid + "iccidTail:"
				+ iccidTail);
		long batchId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID));
		if (batchdataDto.getNumberStart().length() > 20 || batchdataDto.getNumberEnd().length() > 20
				|| batchdataDto.getPullNumber().length() > 20) {
			resultMap.put("msg", "批量导入数据有误，请确认数据(手机号码超长)");
			return resultMap;
		}

		// 插入批量录入总表lao_iccid_batchdata
		int count = 0;
		String typeName = "";
		String versionName = "";
		if (iccidDto != null) {
			LaoSsStaticDto typeDto = tagService.getStaticByCustIdCode(type1, "10");
			typeName = typeDto.getStaticName();
			LaoSsStaticDto versionDto = tagService.getStaticByCustIdCode(version1, "10");
			versionName = versionDto.getStaticName();
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (batchdataDto != null) {
				batchdataDto.setBatchId(batchId);
				if (!StringUtils.isBlank(delDate)) {
					batchdataDto.setDeliveryDate(sdf.parse(delDate));
				}
				batchdataDto.setRecvTime(new Date());
				batchdataDto.setRsrvInfo1(typeName);// 一级标识名称
				batchdataDto.setRsrvInfo2(versionName);// 二级标识名称
				count = userService.insertSelective(batchdataDto);
			}
			log.info("批量总表库中新增条数为" + count + ".......................... ");
		} catch (Exception e) {
			resultMap.put("msg", "批量信息记录库数据有误，请确认数据(lao_iccid_batchdata表)");
			log.info("批量导入库数据有误，请确认数据 ");
			e.printStackTrace();
		}
		// 将对象插入数据库lao_iccid_lib
		List<IccidLibDto> list = new ArrayList<IccidLibDto>();
		IccidLibDto iccid = null;
		String format = "%0" + String.valueOf(endIccid).length() + "d";
		for (int i = startIccid; i <= endIccid; i++) {
			iccid = new IccidLibDto();
			iccid.setIccid(iccidHeader + String.format(format, i) + iccidTail);
			iccid.setCustid(iccidDto.getCustid());
			iccid.setValue1(type);
			iccid.setValue2(version);
			iccid.setDevicetype(iccid.getValue1());
			iccid.setCardtype(iccid.getValue2());
			iccid.setOperators(iccidDto.getOperators());
			iccid.setInitproduct(iccidDto.getInitproduct());
			iccid.setAttribute1(type1);
			iccid.setAttribute2(version1);
			iccid.setCardstatus(iccidDto.getCardstatus());
			iccid.setIfMaintenance(iccidDto.getIfMaintenance());
			iccid.setBatchId(batchId + "");
			iccid.setMsisdn("no data");
			iccid.setPrivatekey("no data");
			iccid.setCtype("no data");
			list.add(iccid);
		}

		Long beginTime = System.currentTimeMillis();
		log.info("................批量导入开始...................time:" + beginTime);
		int total = 0;
		if (list != null && list.size() > 0) {
			List<IccidLibDto> cast = new ArrayList<IccidLibDto>();
			try {
				for (int i = 0; i < list.size(); i++) {
					cast.add(list.get(i));
					if ((i) % 1000 == 0 && i != 0) {
						total = total + userService.batchInsert(cast);
						cast.clear();
					}
				}
				if (cast.size() > 0) {
					total = total + userService.batchInsert(cast);
				}
			} catch (Exception e) {
				log.error("插入数据库lao_iccid_lib失败！");
				e.printStackTrace();
				resultMap.put("msg", "批量导入库数据有误，请确认数据(lao_iccid_lib表)");
				return resultMap;
			}
			log.info("批量导入库中条数为" + total + "................批量导入结束.......... Total cost:"
					+ (System.currentTimeMillis() - beginTime) / 1000.0);
			if (total != list.size()) {
				log.error("批量导入库有数据遗漏，实际导入：" + total + "************************************应该导入：" + list.size());
			}
			resultMap.put("msg", "成功导入" + total + "条");
		} else {
			resultMap.put("msg", "批量导入库数据有误，请确认数据(lao_iccid_lib表)");
			log.info("批量导入库数据有误，请确认数据 ");
		}
		/*
		 * Long custId = Long.parseLong(iccidDto.getCustid()); Long accountId =
		 * 0L; LaoSsAccountDto
		 * user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser"
		 * ); if(user != null) { accountId = user.getAcconutId(); } // 判断是否已缴费
		 * 调用余额中心的接口 log.info("--------------------判断是否已缴费  调用余额中心的接口"); if
		 * (payment != null) { Long orderFee =
		 * Long.parseLong(batchdataDto.getOrderFee()); int intPay = 0; try {
		 * intPay = remainService.paymentOut(100 * orderFee + "", custId,
		 * accountId); } catch (Exception e) {
		 * log.info("---------------调用余额中心的接口出现异常-------------------");
		 * e.printStackTrace(); } log.info("---------------调用余额中心的接口完毕  intPay:"
		 * + intPay); } // 判断是否生成在维用户信息 向kafka发消息
		 * log.info("--------------------判断是否生成在维用户信息"); if (generateUserInfor
		 * != null) { String cardstatus = iccidDto.getCardstatus(); boolean
		 * right = ActionUtil.ifSuperUser(request); String ifAdmin = ""; if
		 * (right) { ifAdmin = "1"; } Map<String, Object> result = new
		 * HashMap<String, Object>(); try { result =
		 * batchService.batchImport(listMap, custId + "", cardstatus, "1",
		 * accountId + "", ifAdmin); } catch (Exception e) {
		 * log.info("---------------调用生成在为用户信息接口出现异常---------------------");
		 * e.printStackTrace(); } log.info(
		 * "---------------调用生成在为用户信息接口完毕  result:" + result); }
		 */
		return resultMap;
	}

	/**
	 * 生成用户信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doUserInfor")
	public Map<String, String> doUserInfor(HttpServletRequest request, String batchId) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			log.info("--------------------生成用户信息开始");
			List<Map<String, Object>> listMap = userService.selectDetailByBatchId(batchId);
			for (int i = 0; i < listMap.size(); i++) {
				Map<String, Object> mapi = listMap.get(i);
				mapi.put("iccid", mapi.get("ICCID"));
				mapi.put("custId", mapi.get("CUSTID"));
				mapi.put("msisdn", mapi.get("MSISDN"));
			}
			Map<String, Object> map0 = listMap.get(0);
			Long custId = Long.parseLong((String) map0.get("custId"));
			Long accountId = 0L;
			LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
			if (user != null) {
				accountId = user.getAcconutId();
			}
			String cardstatus = (String) map0.get("CARDSTATUS");
			boolean right = ActionUtil.ifSuperUser(request);
			String ifAdmin = "";
			if (right) {
				ifAdmin = "1";
			}
			result = batchService.batchImport(listMap, custId + "", cardstatus, "1", accountId + "", ifAdmin);
		} catch (Exception e) {
			log.info("---------------调用生成用户信息接口出现异常---------------------");
			e.printStackTrace();
			return map;
		}
		log.info("---------------调用生成用户信息接口完毕  result,total:" + result.get("total"));
		map.put("msg", "OK");
		return map;
	}

	/**
	 * 查询 一级标识
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getType1")
	public List<Map<String, Object>> getType1(HttpServletRequest request) {
		List<Map<String, Object>> list = null;
		list = tagService.queryProductTypeVAL1();
		return list;
	}

	/**
	 * 查询 二级标识
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getVersion1")
	public List<Map<String, Object>> getVersion1(HttpServletRequest request, String pid) {
		List<Map<String, Object>> list = null;
		list = tagService.queryProductVersionVAL2(pid);
		return list;
	}

	@ResponseBody
	@RequestMapping("/getGoodRealsesTSP")
	public List<Map<String, Object>> getGoodRealsesTSP(HttpServletResponse response, String custId, String value1,
			String value2, String operatorsId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> initMap = new HashMap<String, Object>();
		initMap.put("text", "è¯·é€‰æ‹©");
		initMap.put("id", -1);
		list.add(initMap);
		List<GoodsDto> dtolist = laoGoodsOrderService.queryLaoGoodsTSP(custId, operatorsId, value1, value2);
		Map<String, Object> selectMap = null;
		if (dtolist != null && dtolist.size() > 0) {
			for (GoodsDto goodsDto : dtolist) {
				selectMap = new HashMap<String, Object>();
				selectMap.put("text", goodsDto.getGoodsName() + "(ä»·æ ¼:" + goodsDto.getGoodsPrices() + "å…ƒ;å‘¨æœŸ:"
						+ goodsDto.getReleaseCycle() + "æœˆ)");
				selectMap.put("id", goodsDto.getGoodsReleaseId());
				list.add(selectMap);
			}
		}
		return list;
	}

	/**
	 * TSPæ ¹æ®æ¡ä»¶æŸ¥è¯¢æµ‹è¯•æœŸäº§å“
	 */
	@ResponseBody
	@RequestMapping("/getTestGoodRealses")
	public List<Map<String, Object>> getTestGoodRealses(HttpServletResponse response, String custId, String value1,
			String value2, String operatorsId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> initMap = new HashMap<String, Object>();
		initMap.put("text", "è¯·é€‰æ‹©");
		initMap.put("id", -1);
		list.add(initMap);
		List<GoodsDto> dtolist = laoGoodsOrderService.queryLaoTestGoods(custId, operatorsId, value1, value2);
		// String custId,String operatorsId,String value1,String value2
		Map<String, Object> selectMap = null;
		if (dtolist != null && dtolist.size() > 0) {
			for (GoodsDto goodsDto : dtolist) {
				selectMap = new HashMap<String, Object>();
				selectMap.put("text", goodsDto.getGoodsName() + "(ä»·æ ¼:" + goodsDto.getGoodsPrices() + "å…ƒ;å‘¨æœŸ:"
						+ goodsDto.getReleaseCycle() + "æœˆ)");
				selectMap.put("id", goodsDto.getGoodsReleaseId());
				list.add(selectMap);
			}
		}
		return list;
	}

	/**
	 * TSPæ‰¹é‡å½•å…¥å¡ä¿¡æ¯(ä½¿ç”¨excelå¯¼å…¥ä¿¡æ¯)
	 * 
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/batchImportTSP")
	public Map<String, Object> uploadTSP(@RequestParam(value = "file") MultipartFile file, IccidLibDto iccidDto,
			String type, String version, String type1, String version1, String delDate, String payment,
			String generateUserInfor, HttpServletRequest request) throws IOException {
		Long testGoodsRlsId = iccidDto.getTestGoodsRlsId();
		String buyOrderNo = iccidDto.getBuyOrderNo();
		Short testCycle = iccidDto.getTestCycle();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// log.info("a:"+batchdataDto.getNumberStart().length()+"b:"+batchdataDto.getNumberEnd().length()+"c:"+batchdataDto.getPullNumber().length());
		/*
		 * if (batchdataDto.getNumberStart().length()>20 ||
		 * batchdataDto.getNumberEnd().length()>20 ||
		 * batchdataDto.getPullNumber().length()>20) { resultMap.put("msg",
		 * "æ‰¹é‡å¯¼å…¥æ•°æ®æœ‰è¯¯ï¼Œè¯·ç¡®è®¤æ•°æ®(æ‰‹æœºå·ç è¶…é•¿)");
		 * return resultMap; }
		 */
		ImportExcelUtils<IccidLibDto> utils = null;
		List<IccidLibDto> list = null;
		Long beginTime = System.currentTimeMillis();
		String typeName = "";
		String versionName = "";
		/*
		 * if (iccidDto != null) { LaoSsStaticDto typeDto =
		 * tagService.getStaticByCustIdCodeTSP(type1, "10"); typeName =
		 * typeDto.getStaticName(); LaoSsStaticDto versionDto =
		 * tagService.getStaticByCustIdCodeTSP(version1, "10"); versionName =
		 * versionDto.getStaticName(); }
		 */
		log.info("................æ‰¹é‡å¯¼å…¥å¼€å§‹...................time:" + beginTime);
		// åˆ¤æ–­ä¸Šä¼ æ–‡ä»¶ï¼Œå¦‚æžœä¸ä¸ºç©ºï¼Œå°†ä¹‹è½¬æ¢æˆå¯¹è±¡
		if (!file.isEmpty()) {
			utils = new ImportExcelUtils<IccidLibDto>();
			iccidDto.setValue1(type);
			iccidDto.setValue2(version);
			if (file.getOriginalFilename().endsWith("xlsx")) {
				list = utils.importExcel(file.getInputStream(), iccidDto, true);
			} else {
				list = utils.importExcel(file.getInputStream(), iccidDto, false);
			}
			long batchId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID));

			// æ’å…¥æ‰¹é‡å½•å…¥æ€»è¡¨lao_iccid_batchdata
			int count = 0;
			try {
				if (list.size() <= 0 || list.get(0).getIccid().length() > 20
						|| list.get(list.size() - 1).getIccid().length() > 20) {
					resultMap.put("msg", "æ‰¹é‡å¯¼å…¥æ•°æ®æœ‰è¯¯ï¼Œè¯·ç¡®è®¤æ•°æ®(æ‰‹æœºå·ç è¶…é•¿)");
					return resultMap;
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				IccidBatchdataDto batchdataDto = new IccidBatchdataDto();
				batchdataDto.setBatchId(batchId);
				batchdataDto.setIccidStart(list.get(0).getIccid());
				batchdataDto.setIccidEnd(list.get(list.size() - 1).getIccid());
				batchdataDto.setRecvTime(new Date());
				batchdataDto.setRsrvInfo1(typeName);// ä¸€çº§æ ‡è¯†åç§°
				batchdataDto.setRsrvInfo2(versionName);// äºŒçº§æ ‡è¯†åç§°
				if (!StringUtils.isBlank(delDate)) {
					batchdataDto.setDeliveryDate(sdf.parse(delDate));
				}
				if (payment != null) {
					batchdataDto.setPayment("0");
				} else {
					batchdataDto.setPayment("1");
				}
				if (generateUserInfor != null) {
					batchdataDto.setGenerateinfor("0");
				} else {
					batchdataDto.setGenerateinfor("1");
				}
				count = userService.insertSelective(batchdataDto);
				log.info("æ‰¹é‡æ€»è¡¨åº“ä¸­æ–°å¢žæ¡æ•°ä¸º" + count + ".......................... ");
			} catch (Exception e) {
				log.error("æ’å…¥æ•°æ®åº“lao_iccid_batchdataå¤±è´¥ï¼");
				e.printStackTrace();
				resultMap.put("msg", "å¯¼å…¥å¤±è´¥1ï¼");
				return resultMap;
			}
			// å°†å¯¹è±¡æ’å…¥æ˜Žç»†è¡¨lao_iccid_lib
			int total = 0;
			if (list != null && list.size() > 0) {
				List<IccidLibDto> cast = new ArrayList<IccidLibDto>();
				try {
					for (int i = 0; i < list.size(); i++) {
						list.get(i).setBatchId(batchId + "");
						list.get(i).setAttribute1(type1);
						list.get(i).setAttribute2(version1);
						list.get(i).setTestGoodsRlsId(testGoodsRlsId);
						list.get(i).setBuyOrderNo(buyOrderNo);
						list.get(i).setTestCycle(testCycle);
						cast.add(list.get(i));
						if ((i) % 1000 == 0 && i != 0) {
							total = total + userService.batchInsertTSP(cast);
							cast.clear();
						}
					}
					if (cast.size() > 0) {
						total = total + userService.batchInsertTSP(cast);
					}
				} catch (Exception e) {
					log.error("æ’å…¥æ•°æ®åº“lao_iccid_libå¤±è´¥ï¼");
					e.printStackTrace();
					resultMap.put("msg", "å¯¼å…¥å¤±è´¥2ï¼");
					return resultMap;
				}
				log.info("æ‰¹é‡å¯¼å…¥åº“ä¸­æ¡æ•°ä¸º" + total
						+ "................æ‰¹é‡å¯¼å…¥ç»“æŸ.......... Total cost:"
						+ (System.currentTimeMillis() - beginTime) / 1000.0);
				if (total != list.size()) {
					log.error("æ‰¹é‡å¯¼å…¥åº“æœ‰æ•°æ®é—æ¼ï¼Œå®žé™…å¯¼å…¥ï¼š" + total
							+ "************************************åº”è¯¥å¯¼å…¥ï¼š" + list.size());
				}
				log.info("æ‰¹é‡å¯¼å…¥åº“ä¸­æ¡æ•°ä¸º" + total
						+ "................æ‰¹é‡å¯¼å…¥ç»“æŸ.......... Total cost:"
						+ (System.currentTimeMillis() - beginTime) / 1000.0);
			} else {
				log.error("è¯»excel æ–‡ä»¶ä¸ºç©º************************************");
			}
			resultMap.put("msg", "æˆåŠŸå¯¼å…¥" + total + "æ¡");
		} else {
			resultMap.put("msg", "å¯¼å…¥excel æ–‡ä»¶å¤±è´¥");
			log.error("å¯¼å…¥excel æ–‡ä»¶å¤±è´¥************************************");
			resultMap.put("msg", "å¯¼å…¥å¤±è´¥3ï¼");
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * TSPæ‰¹é‡å½•å…¥å¡ä¿¡æ¯(æ‰‹å·¥å¯¼å…¥ä¸€æ®µå¡ä¿¡æ¯)
	 * 
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/batchImport2TSP")
	public Map<String, Object> exprotIccidTSP(HttpServletResponse response, HttpServletRequest request, int startIccid,
			int endIccid, String iccidHeader, IccidLibDto iccidDto, String type, String version, String type1,
			String version1, String iccidTail, IccidBatchdataDto batchdataDto, String delDate, String payment,
			String generateUserInfor) throws IOException {
		log.info("-----------------------------------è¿›å…¥batchImport2TSP-----------------");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		log.info("å½•å…¥å‚æ•°ä¸º:iccidHeader" + iccidHeader + "startIccid:" + startIccid + "endIccid:" + endIccid
				+ "iccidTail:" + iccidTail);
		long batchId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID));
		if (batchdataDto.getNumberStart().length() > 20 || batchdataDto.getNumberEnd().length() > 20
				|| batchdataDto.getPullNumber().length() > 20) {
			resultMap.put("msg", "æ‰¹é‡å¯¼å…¥æ•°æ®æœ‰è¯¯ï¼Œè¯·ç¡®è®¤æ•°æ®(æ‰‹æœºå·ç è¶…é•¿)");
			return resultMap;
		}

		// æ’å…¥æ‰¹é‡å½•å…¥æ€»è¡¨lao_iccid_batchdata
		int count = 0;
		String typeName = "";
		String versionName = "";
		if (iccidDto != null) {
			LaoSsStaticDto typeDto = tagService.getStaticByCustIdCodeTSP(type1, "10");
			typeName = typeDto.getStaticName();
			LaoSsStaticDto versionDto = tagService.getStaticByCustIdCodeTSP(version1, "10");
			versionName = versionDto.getStaticName();
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (batchdataDto != null) {
				batchdataDto.setBatchId(batchId);
				if (!StringUtils.isBlank(delDate)) {
					batchdataDto.setDeliveryDate(sdf.parse(delDate));
				}
				batchdataDto.setRecvTime(new Date());
				batchdataDto.setRsrvInfo1(typeName);// ä¸€çº§æ ‡è¯†åç§°
				batchdataDto.setRsrvInfo2(versionName);// äºŒçº§æ ‡è¯†åç§°
				count = userService.insertSelective(batchdataDto);
			}
			log.info("æ‰¹é‡æ€»è¡¨åº“ä¸­æ–°å¢žæ¡æ•°ä¸º" + count + ".......................... ");
		} catch (Exception e) {
			resultMap.put("msg", "æ‰¹é‡ä¿¡æ¯è®°å½•åº“æ•°æ®æœ‰è¯¯ï¼Œè¯·ç¡®è®¤æ•°æ®(lao_iccid_batchdataè¡¨)");
			log.info("æ‰¹é‡å¯¼å…¥åº“æ•°æ®æœ‰è¯¯ï¼Œè¯·ç¡®è®¤æ•°æ® ");
			e.printStackTrace();
		}
		// å°†å¯¹è±¡æ’å…¥æ•°æ®åº“lao_iccid_lib
		List<IccidLibDto> list = new ArrayList<IccidLibDto>();
		IccidLibDto iccid = null;
		String format = "%0" + String.valueOf(endIccid).length() + "d";
		for (int i = startIccid; i <= endIccid; i++) {
			iccid = new IccidLibDto();
			iccid.setIccid(iccidHeader + String.format(format, i) + iccidTail);
			iccid.setCustid(iccidDto.getCustid());
			iccid.setDevicetype(iccid.getValue1());
			iccid.setPrivatekey("no data");
			iccid.setInitproduct(iccidDto.getInitproduct());
			iccid.setTestGoodsRlsId(iccidDto.getTestGoodsRlsId());
			iccid.setOperators(iccidDto.getOperators());
			iccid.setCtype("no data");
			iccid.setAttribute1(type1);
			iccid.setAttribute2(version1);
			iccid.setValue1(type);
			iccid.setValue2(version);
			iccid.setBatchId(batchId + "");
			/* åŽé¢ä¹Ÿè¦å†™çš„ */
			iccid.setCardtype(iccid.getValue2());
			iccid.setMsisdn(iccid.getMsisdn());
			iccid.setTestCycle(iccid.getTestCycle());
			iccid.setBuyOrderNo(iccid.getBuyOrderNo());

			list.add(iccid);
		}

		Long beginTime = System.currentTimeMillis();
		log.info("................æ‰¹é‡å¯¼å…¥å¼€å§‹...................time:" + beginTime);
		int total = 0;
		if (list != null && list.size() > 0) {
			List<IccidLibDto> cast = new ArrayList<IccidLibDto>();
			try {
				for (int i = 0; i < list.size(); i++) {
					cast.add(list.get(i));
					if ((i) % 1000 == 0 && i != 0) {
						total = total + userService.batchInsertTSP(cast);
						cast.clear();
					}
				}
				if (cast.size() > 0) {
					total = total + userService.batchInsertTSP(cast);
				}
			} catch (Exception e) {
				log.error("æ’å…¥æ•°æ®åº“lao_iccid_libå¤±è´¥ï¼");
				e.printStackTrace();
				resultMap.put("msg", "æ‰¹é‡å¯¼å…¥åº“æ•°æ®æœ‰è¯¯ï¼Œè¯·ç¡®è®¤æ•°æ®(lao_iccid_libè¡¨)");
				return resultMap;
			}
			log.info("æ‰¹é‡å¯¼å…¥åº“ä¸­æ¡æ•°ä¸º" + total + "................æ‰¹é‡å¯¼å…¥ç»“æŸ.......... Total cost:"
					+ (System.currentTimeMillis() - beginTime) / 1000.0);
			if (total != list.size()) {
				log.error("æ‰¹é‡å¯¼å…¥åº“æœ‰æ•°æ®é—æ¼ï¼Œå®žé™…å¯¼å…¥ï¼š" + total
						+ "************************************åº”è¯¥å¯¼å…¥ï¼š" + list.size());
			}
			resultMap.put("msg", "æˆåŠŸå¯¼å…¥" + total + "æ¡");
		} else {
			resultMap.put("msg", "æ‰¹é‡å¯¼å…¥åº“æ•°æ®æœ‰è¯¯ï¼Œè¯·ç¡®è®¤æ•°æ®(lao_iccid_libè¡¨)");
			log.info("æ‰¹é‡å¯¼å…¥åº“æ•°æ®æœ‰è¯¯ï¼Œè¯·ç¡®è®¤æ•°æ® ");
		}
		return resultMap;
	}

	/**
	 * TSPæŸ¥è¯¢ ä¸€çº§æ ‡è¯†
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getType1TSP")
	public List<Map<String, Object>> getType1TSP(HttpServletRequest request, String custId) {
		List<Map<String, Object>> list = null;
		list = tagService.queryProductTypeVAL1TSP(custId);
		return list;
	}

	// è¿›å…¥uploadNew2ç•Œé¢
	@RequestMapping(value = "/uploadExcelTSP")
	public ModelAndView uploadExcelTSP() {
		List<OperatorsDto> operators = operatorService.findOperators();
		ModelAndView mv = new ModelAndView("/user/uploadNew2");
		mv.addObject("operatorList", operators);
		return mv;
	}

	/**
	 * TSPåˆ†é¡µæŸ¥è¯¢æ‰¹é‡å½•å…¥æ˜Žç»†è¡¨è®°å½•
	 */
	@ResponseBody
	@RequestMapping("/getDetailIccidInfoTSP")
	public Map<String, Object> getDetailIccidInfoTSP(HttpServletRequest req, HttpServletResponse resp, IccidLibDto dto)
			throws Exception {
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// å¼€å§‹æ˜¾ç¤ºçš„é¡¹
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// æ˜¾ç¤ºå¤šå°‘é¡¹
		int pageNo = (pageStart / pageSize) + 1;
		Map<String, Object> resultMap = userService.selectDetailByPageTSP(dto, pageNo, pageSize);
		return resultMap;
	}

	@RequestMapping(value = "/queryDetailNew")
	public ModelAndView queryDetailNew(HttpServletResponse response, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("user/queryDetailNew");
		String batchId = request.getParameter("batchId");
		mv.addObject("batchId", batchId);
		return mv;
	}
}
