package com.urt.web.customer;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.urt.common.enumeration.ConstantEnum;
import com.urt.common.util.ResultMsg;
import com.urt.constant.SysConstants;
import com.urt.dto.LaoCustConcatDto;
import com.urt.dto.LaoCustGroupDto;
import com.urt.dto.LaoCustPersonDto;
import com.urt.dto.LaoCustomerDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoStaffDto;
import com.urt.interfaces.authority.LaoSsAccountService;
import com.urt.interfaces.customer.LaoCustConcatService;
import com.urt.interfaces.customer.LaoCustGroupService;
import com.urt.interfaces.customer.LaoCustPersonService;
import com.urt.interfaces.customer.LaoCustomerService;
import com.urt.interfaces.traffic.TrafficQueryService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

/**
 * 客户中心
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/cust")
public class CustomerController {
	@Autowired
	LaoCustomerService customerService;
	@Autowired
	LaoCustConcatService custConcatService;
	@Autowired
	LaoCustGroupService custGroupService;
	@Autowired
	private LaoCustPersonService custPersonService;
	@Autowired
	LaoSsAccountService laoSsAccountService;
	@Autowired
	TrafficQueryService trafficQueryservice;

	/**
	 * 跳转企业客户中心
	 * 
	 * @return
	 */
	@RequestMapping(value = "/conpanyList")
	public String conpanyList() {
		return "cust/company/customerList";
	}

	
	/**
	 * 跳转企业客户联系人中心
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toConcatList/{id}")
	public ModelAndView conpanyList(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("cust/company/concatList");
		mv.addObject("custId", id);
		return mv;
	}

	@RequestMapping(value = "/toAccountList/{id}")
	public ModelAndView toAccountList(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("cust/company/accountList");
		mv.addObject("custId", id);
		return mv;
	}

	/**
	 * 获取企业客户列表
	 * 
	 * @param req
	 * @param resp
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryList")
	public Map<String, Object> roleList(HttpServletRequest req, HttpServletResponse resp, LaoCustomerDto dto) {
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		/* List<LaoSsRoleDto> roles = user.getRoles(); */
		/*
		 * List<LaoSsRoleDto> roles =
		 * laoSsRoleService.queryRoleListByAccountId(user .getAcconutId()); Gson
		 * gson=new Gson(); String json = gson.toJson(roles.get(0));
		 * LaoSsRoleDto role = gson.fromJson(json, LaoSsRoleDto.class);
		 */
		
		// TODO: 使用上下级关系加载客户列表，不再使用数据权限
		/*boolean ifSuperUser = ActionUtil.ifSuperUser(req);
		if (ifSuperUser == false) {
			dto.setAdmin(false);
			dto.setParentId(user.getCustId());
			if (user.getCustId() != null && user.getCustId() > 0) {
				dto.setCustId(user.getCustId());
			} else {
				dto.setCustId(-1l);
			}
		} else {
			dto.setAdmin(true);
		}*/
		dto.setCustId(user.getCustId());
		Map<String, Object> resultMap = customerService.queryPage(dto, pageNo, pageSize);
		return resultMap;
	}

	//分润管理企业账户查询
	@ResponseBody
	@RequestMapping(value = "/feeQueryList")
	public Map<String, Object> feeRoleList(HttpServletRequest req, HttpServletResponse resp, LaoCustomerDto dto) {
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		dto.setCustId(user.getCustId());
		Map<String, Object> resultMap = customerService.feeQueryPageList(dto, pageNo, pageSize);
		return resultMap;
	}

	@RequestMapping("/company/createCustomer")
	public ModelAndView createCustomer() {
		ModelAndView mv = new ModelAndView("cust/company/customerInsertOrUpdate");
		// 界面模式：add,edit,view
		mv.addObject("viewMode", "add");
		return mv;
	}

	@RequestMapping("/createConcat/{custId}")
	public ModelAndView createConcat(@PathVariable("custId") Long custId) {
		ModelAndView mv = new ModelAndView("cust/company/writeCustomerConcat");
		mv.addObject("custId", custId);
		return mv;
	}

	@RequestMapping("/toUpdate/{id}")
	public ModelAndView toUpdate(@PathVariable("id") Long id) {
		String path = "cust/company/customerInsertOrUpdate";
		ModelAndView mv = editOrViewCust(id, path);
		// 界面模式：add,edit,view
		mv.addObject("viewMode", "edit");
		return mv;
	}

	@RequestMapping("/toDetail/{id}")
	public ModelAndView toDetail(@PathVariable("id") Long id) {
		//ModelAndView mv = new ModelAndView("cust/company/customerDetail");
		String path = "cust/company/customerDetail";
		ModelAndView mv = editOrViewCust(id, path);
		// 界面模式：add,edit,view
		mv.addObject("viewMode", "view");
		return mv;
	}
	
	/**
	 * 修改客户或者查看客户详情
	 * @param custId
	 * @return
	 */
	private ModelAndView editOrViewCust(Long custId, String path)
	{
		ModelAndView mv = new ModelAndView(path);
		LaoCustomerDto customerDto = customerService.selectDtoById(custId);
		LaoCustGroupDto custGroupDto = custGroupService.selectDtoById(custId);
		mv.addObject("customerDto", customerDto);
		mv.addObject("custGroupDto", custGroupDto);
		// 查询上级客户信息
		if (customerDto != null && customerDto.getParentId() != null) {
			LaoCustomerDto parentCustomerDto  = customerService.selectDtoById(customerDto.getParentId());
			mv.addObject("parentCustDto", parentCustomerDto);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		mv.addObject("busiLicenceValidDateStr", sdf.format(custGroupDto.getBusiLicenceValidDate()));
		List<LaoSsAccountDto> accounts = laoSsAccountService.queryByCustId(custId);
		StringBuffer sb = new StringBuffer();
		for (LaoSsAccountDto dto : accounts) {
			sb.append(dto.getAcconutId().toString() + ",");
		}
		if (sb != null && sb.length() > 0) {
			mv.addObject("ids", sb.toString().substring(0, sb.toString().length() - 1));
		} else {
			mv.addObject("ids", "");
		}
		return mv;
	}

	@RequestMapping("/toUpdateConcat/{id}")
	public ModelAndView toUpdateConcat(@PathVariable("id") Long id, Long custId) {
		ModelAndView mv = new ModelAndView("cust/company/writeCustomerConcat");
		LaoCustConcatDto concatDto = custConcatService.selectDtoById(id);
		mv.addObject("concatDto", concatDto);
		mv.addObject("custId", custId);

		return mv;
	}

	@RequestMapping("/delConcat/{id}")
	public ModelAndView delConcat(@PathVariable("id") Long id, Long custId) {
		custConcatService.del(id);
		ModelAndView mv = new ModelAndView("cust/company/concatList");
		mv.addObject("custId", custId);
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/save")
	public ResultMsg save(LaoCustomerDto customerDto, LaoCustGroupDto custGroupDto, HttpServletRequest request,
			String busiLicenceValidDateStr,String dev,String devRV) {

		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		Long id = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			custGroupDto.setBusiLicenceValidDate(sdf.parse(busiLicenceValidDateStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		customerDto.setCustType("1");
		if (customerDto.getCustId() != null) {
			customerService.update(customerDto);
			custGroupService.update(custGroupDto);
		} else {
			customerDto.setCustId(id);
			customerDto.setInDate(new Date());
			customerDto.setInStaffId(user.getAcconutId().toString());
			//custGroupDto.setSellType(custGroupDto.getSellType());
			if (custGroupDto.getSellType().equals("2") || custGroupDto.getSellType().equals("3")) {
				customerDto.setDevCust(Long.valueOf(dev));
				customerDto.setRsrvStr1(devRV);
				customerDto.setParentId(Long.valueOf(dev));
			}else{
				customerDto.setRsrvStr1(dev);
				// 已经不适用，暂时保留兼容老数据
				customerDto.setDevCust(SysConstants.superCustId);
				customerDto.setParentId(SysConstants.superCustId);
			}		
			customerService.save(customerDto);
			custGroupDto.setCustId(id);
			custGroupService.save(custGroupDto);
		}
		ResultMsg msg = new ResultMsg();
		msg.setSuccess(true);
		msg.setObjData(id);
		return msg;
	}

	@ResponseBody
	@RequestMapping(value = "/saveCustPerson")
	public ResultMsg saveCustPerson(LaoCustomerDto customerDto, LaoCustPersonDto custPerson, String birthdayStr,
			String psptEndDateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (birthdayStr != null && birthdayStr.trim().length() > 0) {
				custPerson.setBirthday(sdf.parse(birthdayStr));
			}
			if (psptEndDateStr != null && psptEndDateStr.trim().length() > 0) {
				custPerson.setPsptEndDate(sdf.parse(psptEndDateStr));
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		customerDto.setCustType("0");
		int flag = 0;
		if (customerDto.getCustId() != null) {
			flag += customerService.update(customerDto);
			flag += custPersonService.updateByPrimaryKeySelective(custPerson);
		} else {
			/*
			 * customerDto.setCustId(id); customerDto.setInDate(new Date());
			 * customerDto.setInStaffId(user.getAcconutId().toString());
			 * customerService.save(customerDto); custGroupDto.setCustId(id);
			 * custGroupService.save(custGroupDto);
			 */
		}
		ResultMsg msg = new ResultMsg();
		if (flag > 1) {
			msg.setSuccess(true);
		} else {
			msg.setSuccess(false);
		}
		return msg;
	}

	/**
	 * 联系人列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/contactList")
	public Map<String, Object> contactList(HttpServletRequest req, HttpServletResponse resp, String contactName,
			String custId, LaoCustConcatDto dto) {
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;
		Map<String, Object> resultMap = custConcatService.queryPage(dto, pageNo, pageSize);
		return resultMap;
	}

	/**
	 * 异步保存联系人信息
	 * 
	 * @param dto
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveConcat")
	public ResultMsg save(@ModelAttribute LaoCustConcatDto dto, HttpServletRequest request) {
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		dto.setUpdateTime(new Date());
		dto.setUpdateStaffId(user.getAcconutId().toString());
		if (dto.getContactId() != null) {
			custConcatService.update(dto);
		} else {
			Long id = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
			dto.setContactId(id);

			custConcatService.save(dto);
		}
		ModelAndView mv = new ModelAndView("cust/company/concatList");
		mv.addObject("custId", dto.getCustId());
		ResultMsg msg = new ResultMsg();
		msg.setSuccess(true);
		return msg;
	}

	/**
	 * 异步获取所有人员
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAccountList")
	public List<Map<String, Object>> getAccountList() {
		List<LaoSsAccountDto> dtos = laoSsAccountService.selectUsersByModel(new LaoSsAccountDto());
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (LaoSsAccountDto dto : dtos) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", dto.getNickname());
			map.put("id", dto.getAcconutId());
			list.add(map);
		}
		return list;
	}

	/**
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAgentList")
	public List<Map<String, Object>> getAgentList() {
		List<LaoCustomerDto> dtos = customerService.queryAgent();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> initMap = new HashMap<String, Object>();
		initMap.put("text", "请选择");
		initMap.put("id", -1);
		list.add(initMap);
		for (LaoCustomerDto dto : dtos) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", dto.getCustName());
			map.put("id", dto.getCustId());
			list.add(map);
		}
		return list;
	}

	@RequestMapping("/customerIndex")
	public ModelAndView customerIndex(HttpServletRequest req) throws Exception {
		ModelAndView mv = new ModelAndView("cust/company/customerIndex");
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		Long custId = user.getCustId();

		LaoCustomerDto custDto = customerService.selectDtoById(custId);
		Long deposiMoney = customerService.selectDepositMoney(custId);
		List<Map<String, Object>> maxFlows = trafficQueryservice.selectMaxFlowCount(custId);
		List<Map<String, Object>> minFlows = trafficQueryservice.selectMinFlowCount(custId);

		mv.addObject("custName", custDto.getCustName());
		mv.addObject("custState", custDto.getCustState());
		mv.addObject("psptTypeCode", custDto.getPsptTypeCode());
		mv.addObject("psptId", custDto.getPsptId());
		mv.addObject("deposiMoney", deposiMoney);
		mv.addObject("maxFlows", maxFlows);
		mv.addObject("minFlows", minFlows);
		return mv;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@RequestMapping("/customerIndexEcharts")
	public Map<String, List<Object>> customerIndexEcharts(HttpServletRequest req) throws Exception {
		Map map = new HashMap();
		List<Object> operatorList = new ArrayList<Object>();
		List<Object> statesList = new ArrayList<Object>();
		Map sums = new HashMap();
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		Long custId = user.getCustId();

		List<Map<String, Object>> cardInfoList = customerService.selectCustCardInfo(custId);
		if (cardInfoList != null && cardInfoList.size() > 0) {
			String[] data1 = new String[cardInfoList.size()+1];
			Map<String,Object>[] data2 = new Map[cardInfoList.size()];
			for (int i = 0; i < cardInfoList.size(); i++) {
				Map<String,Object> mapi = cardInfoList.get(i);
				String operator = (String) mapi.get("OPERATORSNAME");
				BigDecimal operatorsId = (BigDecimal) mapi.get("OPERATORSID");
				String state = (String) mapi.get("SERVICENAME");
				String stateCode = (String) mapi.get("STATECODE");
				BigDecimal sum = (BigDecimal) mapi.get("SUM");
				if (!operatorList.contains(operator + "_" + operatorsId.longValue())) {
					operatorList.add(operator + "_" + operatorsId.longValue());
				}
				if (!statesList.contains(state + "_" + stateCode)) {
					statesList.add(state + "_" + stateCode);
				}
				sums.put(stateCode + "_" + operatorsId, sum);
				int sumi = sum.intValue();
				data1[i] = operator;
				Map<String,Object> mapData2 = new HashMap<String,Object>();
				mapData2.put("value", sumi);
				mapData2.put("name", operator);
				data2[i] = mapData2;
			}
			data1[cardInfoList.size()] = "";
			map.put("data1", data1);
			map.put("data2", data2);
		}
		map.put("operatorList", operatorList);
		map.put("statesList", statesList);
		map.put("sums", sums);
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@RequestMapping("/selectCategoryCount")
	public Map<String, List<Object>> selectCategoryCount(HttpServletRequest req) throws Exception {
		Map map = new HashMap();
		List<Object> operatorList = new ArrayList<Object>();
		List<Object> goodsList = new ArrayList<Object>();
		Map sums = new HashMap();
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
		Long custId = user.getCustId();

		List<Map<String, Object>> categoryList = customerService.selectCategoryCount(custId);
		for (Map<String, Object> objMap : categoryList) {
			String operator = (String) objMap.get("OPERATORSNAME");
			BigDecimal operatorId = (BigDecimal) objMap.get("OPERATORSID");
			String category = (String) objMap.get("GOODSNAME");
			BigDecimal categoryId = (BigDecimal) objMap.get("GOODID");
			BigDecimal sum = (BigDecimal) objMap.get("SUM");
			if (!operatorList.contains(operator + "_" + operatorId.longValue())) {
				operatorList.add(operator + "_" + operatorId.longValue());
			}
			if (!goodsList.contains(category + "_" + categoryId.longValue())) {
				goodsList.add(category + "_" + categoryId.longValue());
			}
			sums.put(categoryId + "_" + operatorId, sum);
		}
		map.put("operatorList", operatorList);
		map.put("goodsList", goodsList);
		map.put("sums", sums);
		return map;
	}

	/**
	 * 导入excel
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/importCustomer")
	public List importCustomer(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request)
			throws Exception {
		//LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		List<String> list = new ArrayList<String>();
		// // 判断上传文件，如果不为空，将之转换成对象
		if (!file.isEmpty()) {
			if (file.getOriginalFilename().endsWith("xlsx")) {
				list = readExcel(file);
			}
		}
		// ModelAndView mv=new
		// ModelAndView("cust/company/customerInsertOrUpdate");
		// Map<String,Object> resMap = new HashMap<String,Object>();
		// resMap.put("customerDto", customerDto);
		// resMap.put("custGroupDto", custGroupDto);
		return list;
	}

	private List<String> readExcel(MultipartFile file) throws IOException {
		InputStream is = file.getInputStream();
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);// 第一页
		List<String> localList = new ArrayList<String>();
		// localList.add(xssfSheet.getRow(1).getCell(1).getStringCellValue());
		// localList.add(xssfSheet.getRow(1).getCell(3).getStringCellValue());
		// localList.add(xssfSheet.getRow(1).getCell(5).getStringCellValue());
		// localList.add(xssfSheet.getRow(2).getCell(1).getStringCellValue());
		// localList.add(xssfSheet.getRow(2).getCell(3).getStringCellValue());
		// localList.add(xssfSheet.getRow(2).getCell(5).getStringCellValue());
		// localList.add(xssfSheet.getRow(3).getCell(1).getStringCellValue());
		// localList.add(xssfSheet.getRow(3).getCell(3).getStringCellValue());
		// localList.add(xssfSheet.getRow(3).getCell(5).getStringCellValue());
		localList.add(xssfSheet.getRow(5).getCell(1).getStringCellValue());// 客户名称
		localList.add(xssfSheet.getRow(5).getCell(4).getStringCellValue());// 客户注册地址
		localList.add(xssfSheet.getRow(6).getCell(1).getStringCellValue());// 注册法人
		localList.add(xssfSheet.getRow(6).getCell(4).getStringCellValue());// 营业执照号
		// localList.add(xssfSheet.getRow(8).getCell(1).getStringCellValue());
		// localList.add(xssfSheet.getRow(8).getCell(3).getStringCellValue());
		// localList.add(xssfSheet.getRow(8).getCell(5).getStringCellValue());
		// localList.add(xssfSheet.getRow(9).getCell(1).getStringCellValue());
		System.out.println(localList);
		is.close();
		xssfWorkbook.close();
		return localList;
	}

	@RequestMapping("/toUpdateImport/{str}")
	public ModelAndView toUpdateImport(@PathVariable("str") List<String> list) {
		ModelAndView mv = new ModelAndView("cust/company/customerInsertOrUpdate");
		LaoCustomerDto customerDto = new LaoCustomerDto();
		customerDto.setCustName(list.get(0));
		LaoCustGroupDto custGroupDto = new LaoCustGroupDto();
		custGroupDto.setGroupAddr(list.get(1));
		custGroupDto.setJuristicPsptId(list.get(2));
		custGroupDto.setBusiLicenceNo(list.get(3));
		mv.addObject("customerDto", customerDto);
		mv.addObject("custGroupDto", custGroupDto);
		return mv;
	}

	@RequestMapping(value = "/saveAndCreate")
	public ModelAndView saveAndCreate(LaoCustomerDto customerDto, LaoCustGroupDto custGroupDto,
			HttpServletRequest request, String busiLicenceValidDateStr) {

		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		Long id = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			custGroupDto.setBusiLicenceValidDate(sdf.parse(busiLicenceValidDateStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		customerDto.setCustType("1");
		if (customerDto.getCustId() != null) {
			/*
			 * if(accountIds!=null){ String[] ids=accountIds.split(","); for(int
			 * i=0;i<ids.length;i++){
			 * laoSsAccountService.updateAccountCustId(Long.valueOf(ids[i]),
			 * customerDto.getCustId()); } }
			 */
			customerService.update(customerDto);
			custGroupService.update(custGroupDto);
		} else {
			/*
			 * if(accountIds!=null){ String[] ids=accountIds.split(","); for(int
			 * i=0;i<ids.length;i++){
			 * laoSsAccountService.updateAccountCustId(Long.valueOf(ids[i]),
			 * id); } }
			 */
			customerDto.setCustId(id);
			customerDto.setInDate(new Date());
			customerDto.setInStaffId(user.getAcconutId().toString());
			/*
			 * Long parentId = user.getCustId();
			 * customerDto.setParentId(parentId);
			 */
			if (custGroupDto.getSellType().equals("2")) {
				customerDto.setDevCust(user.getCustId());
				customerDto.setDevAct(null);
				custGroupDto.setSellType("1");
			} else if(custGroupDto.getSellType().equals("1")){
				custGroupDto.setSellType("0");
				customerDto.setDevAct(user.getAcconutId());
				customerDto.setDevCust(null);
			}else{
				custGroupDto.setSellType("2");
				customerDto.setDevAct(user.getAcconutId());
				customerDto.setDevCust(null);
			}			
			customerService.save(customerDto);
			custGroupDto.setCustId(id);
			custGroupService.save(custGroupDto);
		}
		ModelAndView mv = new ModelAndView("ssUser/toAdd");
		return mv;
	}

	/**
	 * 查询渠道客户
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getChannelCust")
	@ResponseBody
	public List<LaoCustomerDto> getChannelCust(String sellType) {
//		List<LaoCustGroupDto> dtos = custGroupService.queryChannelCust(sellType);
		List<LaoCustomerDto> dtos=customerService.queryChannelCust(sellType);
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		Map<String, Object> initMap = new HashMap<String, Object>();
//		initMap.put("text", "请选择");
//		initMap.put("id", -1);
//		list.add(initMap);		
//		for (LaoCustGroupDto dto : dtos) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("text", dto.getCustName());
//			map.put("id", dto.getCustId());
//			list.add(map);
//		}
		return dtos;
	}
	
	/**
	 * 查询自营客户
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSelfCust")
	@ResponseBody
	public List<Map<String, Object>> getSelfCust() {
		List<LaoSsAccountDto> dtos = laoSsAccountService.queryByCustId(ConstantEnum.ZERO.getCode());
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> initMap = new HashMap<String, Object>();
		initMap.put("text", "请选择");
		initMap.put("id", -1);
		list.add(initMap);				
		for (LaoSsAccountDto dto : dtos) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", dto.getLoginName());
			map.put("id", dto.getAcconutId());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 查询自营客户
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getStaffs")
	@ResponseBody
	public List<LaoStaffDto> getStaffs() {
		List<LaoStaffDto> list = laoSsAccountService.queryStaffs();
		return list;
	}
}
