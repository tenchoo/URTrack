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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.urt.dto.LaoCustConcatDto;
import com.urt.dto.LaoCustGroupDto;
import com.urt.dto.LaoCustPersonDto;
import com.urt.dto.LaoCustomerDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.interfaces.authority.LaoSsAccountService;
import com.urt.interfaces.cardOper.CardOperService;
import com.urt.interfaces.customer.LaoCustConcatService;
import com.urt.interfaces.customer.LaoCustGroupService;
import com.urt.interfaces.customer.LaoCustPersonService;
import com.urt.interfaces.customer.LaoCustomerService;
import com.urt.interfaces.traffic.TrafficQueryService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
import com.urt.web.common.util.ActionUtil;

/**
 * 个人客户中心
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/person")
public class PerCustController {

    private static Logger logger = LoggerFactory.getLogger(PerCustController.class);
    @Autowired
    LaoCustomerService customerService;
    @Autowired
    LaoCustConcatService custConcatService;
    @Autowired
    private LaoCustPersonService custPersonService; 
    @Autowired
    LaoSsAccountService laoSsAccountService;
    @Autowired
    TrafficQueryService trafficQueryservice;
    @Autowired
    LaoCustGroupService custGroupService;
    @Autowired
    CardOperService cardOperService;
	@Autowired
	TrafficQueryService trafficQueryService;

    /**
     * 跳转个人客户中心
     * 
     * @return
     */
    @RequestMapping(value = "/personList")
    public String conpanyList() {
        return "cust/person/customerList";
    }

    
    /**
     * 跳转个人客户联系人中心
     * 
     * @return
     */
    @RequestMapping(value = "/toConcatList/{id}")
    public ModelAndView conpanyList(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("cust/person/concatList");
        mv.addObject("custId", id);
        return mv;
    }

    @RequestMapping(value = "/toAccountList/{id}")
    public ModelAndView toAccountList(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("cust/person/accountList");
        mv.addObject("custId", id);
        return mv;
    }

    /**
     * 获取个人客户列表
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
        dto.setAdmin(true);
        Map<String, Object> resultMap = customerService.queryPerPage(dto, pageNo, pageSize);
        return resultMap;
    }
    
    /**
     * 获取测试期转正式期号码列表
     * 
     * @param req
     * @param resp
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/cardList")
    public Map<String, Object> cardList(HttpServletRequest req, HttpServletResponse resp, String chCustId) {
        int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// 开始显示的项
        int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// 显示多少项
        int pageNo = (pageStart / pageSize) + 1;        
        logger.info("chCustId:"+chCustId);
        Map<String, Object> resultMap = cardOperService.queryPage(Long.valueOf(chCustId), pageNo, pageSize);
        logger.info("resultMap.size:"+resultMap.size());
        return resultMap;
    }

    
    //queryPerInfo
    @ResponseBody
    @RequestMapping(value = "/queryPerInfo")
    public Map<String, Object> roleInfo(HttpServletRequest req, HttpServletResponse resp, LaoCustomerDto dto) {
        int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// 开始显示的项
        int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// 显示多少项
        int pageNo = (pageStart / pageSize) + 1; 
         dto.setAdmin(true);
        Map<String, Object> resultMap = customerService.queryPerPage(dto, pageNo, pageSize);
        return resultMap;
    }
    
    @RequestMapping("/person/createCustomer")
    public ModelAndView createCustomer() {
        ModelAndView mv = new ModelAndView("cust/person/customerInsertOrUpdate");
        return mv;
    }

    @RequestMapping("/createConcat/{custId}")
    public ModelAndView createConcat(@PathVariable("custId") Long custId) {
        ModelAndView mv = new ModelAndView("cust/person/writeCustomerConcat");
        mv.addObject("custId", custId);
        return mv;
    }

    @RequestMapping("/toUpdate/{id}")
    public ModelAndView toUpdate(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("cust/person/customerInsertOrUpdate");
        LaoCustomerDto customerDto = customerService.selectDtoById(id);
        LaoCustPersonDto custPersonDto=custPersonService.selectByPrimaryKey(id);
        
        if(custPersonDto!=null){
            if("0".equals(custPersonDto.getSex())){
                custPersonDto.setSex("男");
            }else{
                custPersonDto.setSex("女");
            }
        }
        
        mv.addObject("customerDto", customerDto);
        mv.addObject("custPersonDto", custPersonDto);       
        List<LaoSsAccountDto> accounts = laoSsAccountService.queryByCustId(id);
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

    @RequestMapping("/toDetail/{id}")
    public ModelAndView toDetail(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("cust/person/customerDetail");
        LaoCustomerDto customerDto = customerService.selectDtoById(id); 
        LaoCustPersonDto custPersonDto=custPersonService.selectByPrimaryKey(id);
        if(custPersonDto!=null){
            if("0".equals(custPersonDto.getSex())){
                custPersonDto.setSex("男");
            }else{
                custPersonDto.setSex("女");
            }
        }
        mv.addObject("customerDto", customerDto);
        mv.addObject("custPersonDto", custPersonDto);
        List<LaoSsAccountDto> accounts = laoSsAccountService.queryByCustId(id);
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
        ModelAndView mv = new ModelAndView("cust/person/writeCustomerConcat");
        LaoCustConcatDto concatDto = custConcatService.selectDtoById(id);
        mv.addObject("concatDto", concatDto);
        mv.addObject("custId", custId);

        return mv;
    }

    @RequestMapping("/delConcat/{id}")
    public ModelAndView delConcat(@PathVariable("id") Long id, Long custId) {
        custConcatService.del(id);
        ModelAndView mv = new ModelAndView("cust/person/concatList");
        mv.addObject("custId", custId);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/save")
    public ResultMsg save(LaoCustomerDto customerDto, LaoCustPersonDto custPersonDto, HttpServletRequest request) {
        LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
        String[] datas = request.getParameter("accData").split(";");
        Long id = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
        if(!"".equals(datas[0])){
            custPersonDto.setCustId(Long.valueOf(datas[0]));
            customerDto.setCustId(Long.valueOf(datas[0]));
        }    
        custPersonDto.setCustName(datas[1]);
        customerDto.setCustName(datas[1]);
        custPersonDto.setPsptId(datas[2]);
        customerDto.setPsptId(datas[2]);
        custPersonDto.setSex(datas[3]);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //PSPT_TYPE_CODE
        custPersonDto.setPsptAddr(datas[5]);
        custPersonDto.setPostAddress(datas[6]);
        customerDto.setCustState(datas[7]);
        custPersonDto.setPsptTypeCode(datas[8]);
        customerDto.setPsptTypeCode(datas[8]);
        try {
            if(!"".equals(datas[4])){
                custPersonDto.setBirthday(sdf.parse(datas[4]));
            }
            custPersonDto.setUpdateTime(new Date());
        } catch (ParseException e1) {          
            e1.printStackTrace();
        }
        customerDto.setCustType("0");
        logger.info(" Address:"+custPersonDto.getPostAddress()+" PostCode:"+custPersonDto.getPostCode()+" Addr:"+custPersonDto.getPsptAddr());
        try {
            if (customerDto.getCustId() != null) {           
                customerService.update(customerDto);
                custPersonService.updateByPrimaryKey(custPersonDto);
            } else {         
                customerDto.setCustId(id);
                custPersonDto.setCustId(id);
                customerDto.setInDate(new Date());
                customerDto.setInStaffId(user.getAcconutId().toString());      
                customerDto.setDevAct(user.getAcconutId());
                customerDto.setDevCust(null);
                customerService.save(customerDto);
                custPersonService.save(custPersonDto);           
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        ModelAndView mv = new ModelAndView("cust/person/concatList");
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
        ModelAndView mv = new ModelAndView("cust/person/customerIndex");
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
    @ResponseBody
    @RequestMapping("/importCustomer")
    public List importCustomer(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request)
            throws Exception {
        List<String> list = new ArrayList<String>();
        // // 判断上传文件，如果不为空，将之转换成对象
        if (!file.isEmpty()) {
            if (file.getOriginalFilename().endsWith("xlsx")) {
                list = readExcel(file);
            }
        }    
        return list;
    }

    private List<String> readExcel(MultipartFile file) throws IOException {
        InputStream is = file.getInputStream();
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);// 第一页
        List<String> localList = new ArrayList<String>();
        localList.add(xssfSheet.getRow(5).getCell(1).getStringCellValue());// 客户名称
        localList.add(xssfSheet.getRow(5).getCell(4).getStringCellValue());// 客户注册地址
        localList.add(xssfSheet.getRow(6).getCell(1).getStringCellValue());// 注册法人
        localList.add(xssfSheet.getRow(6).getCell(4).getStringCellValue());// 营业执照号
        System.out.println(localList);
        is.close();
        xssfWorkbook.close();
        return localList;
    }

    @RequestMapping("/toUpdateImport/{str}")
    public ModelAndView toUpdateImport(@PathVariable("str") List<String> list) {
        ModelAndView mv = new ModelAndView("cust/person/customerInsertOrUpdate");
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
            customerService.update(customerDto);
            custGroupService.update(custGroupDto);
        } else {
            customerDto.setCustId(id);
            customerDto.setInDate(new Date());
            customerDto.setInStaffId(user.getAcconutId().toString());
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
    public List<Map<String, Object>> getChannelCust(HttpServletRequest request) {
    	LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
    	//List<LaoCustGroupDto> dtos = trafficQueryService.selectAll();
    	//List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	
    	List<LaoCustGroupDto> dtos = trafficQueryService.selectAll();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> initMap = new HashMap<String, Object>();
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
    	
      
        initMap.put("text", "请选择");
        initMap.put("id", -1);
        list.add(initMap);      
        for (LaoCustGroupDto dto : dtos) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", dto.getCustName());
            map.put("id", dto.getCustId());
            list.add(map);
        }
        return list;
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

}
