package com.urt.web.dmp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.urt.common.util.ResultMsg;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.UtilDto;
import com.urt.dto.dmp.LaoDMPCardDto;
import com.urt.dto.dmp.LaoDMPCardGroupDto;
import com.urt.dto.dmp.LaoDMPGroupDto;
import com.urt.interfaces.dmp.DMPCardGroupService;
import com.urt.interfaces.dmp.DMPDeviceManageService;
import com.urt.interfaces.dmp.DMPService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
import com.urt.web.common.util.FtpsFileList;
import com.urt.web.util.RandomUtil;

@Controller
@RequestMapping("/deviceManage")
public class DeviceManagementController {
	Logger log = Logger.getLogger(getClass());

	@Autowired
	private DMPDeviceManageService dmpDeviceManageService;
	@Autowired
	private DMPService dmpService;
	@Autowired
	private DMPCardGroupService dmpCardGroupService;

	// 跳转终端设备管理页面
	@RequestMapping("/deviceManage")
	public ModelAndView deviceMana() {
		ModelAndView mv = new ModelAndView("/dmp/deviceGroup");
		return mv;
	}

	@RequestMapping("/addDeviceManage")
	public ModelAndView createDeviceManage() {
		ModelAndView mv = new ModelAndView("dmp/addDeviceManage");
		return mv;
	}

	// 获取设备组list
	@ResponseBody
	@RequestMapping("/deviceManageList")
	public Map<String, Object> deviceList(HttpServletRequest req,
			HttpServletResponse resp, LaoDMPGroupDto dto) {
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart")
				.toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength")
				.toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;
		LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute(
				"sessionUser");
		long custId = user.getCustId();
		dto.setChannelCustid(custId);
		Map<String, Object> resultManage = dmpDeviceManageService.queryPage(
				dto, pageNo, pageSize);
		return resultManage;
	}

	// 删除设备组
	@ResponseBody
	@RequestMapping("/del")
	public ResultMsg del(Long id) {
		ResultMsg msg = new ResultMsg();
		if (dmpDeviceManageService.delDeviceManage(id) > 0) {
			dmpCardGroupService.delCardGroup(id);
				msg.setSuccess(true);
				msg.setMsg("删除成功！");
			
		} else {
			msg.setMsg("删除失败！");
		}
		return msg;
	}

	// 添加设备组
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String, Object> save(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest req, UtilDto utilDto, LaoDMPGroupDto dto) {
		Long ruleId = 0L;
		 ruleId = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.LAODMPGROUP_ID));
		//ruleId = RandomUtil.generateLong(16);
		LaoSsAccountDto currentUser = (LaoSsAccountDto) req.getSession()
				.getAttribute("sessionUser");
		dto.setChannelCustid(currentUser.getCustId());
		dto.setId(ruleId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Integer result = dmpDeviceManageService.saveDeviceManage(dto);
		Long id = dto.getId();
		if(file==null&&result>0){
			resultMap.put("success", true);
			resultMap.put("retMsg", "添加成功");
			return resultMap;
		}
		try {
			if (!file.isEmpty()) {
				String fileName = file.getOriginalFilename();
				if (file.getOriginalFilename().endsWith("xlsx")) {
					InputStream is = file.getInputStream();
					List<Map<String, String>> listMap = null;
					try {
						Map<String, Object> map = FtpsFileList.readFile(is,fileName);
						listMap = (List<Map<String, String>>) map.get("listMap");
						LaoDMPCardGroupDto groupDto = new LaoDMPCardGroupDto();
						for (int i = 0; i < listMap.size(); i++) {
							List<LaoDMPCardDto> cardListDto = new ArrayList<LaoDMPCardDto>();
							List<LaoDMPCardGroupDto> groupListDto = new ArrayList<LaoDMPCardGroupDto>();
							Map<String, String> mapi = listMap.get(i);
							String imei = mapi.get("0");
							LaoDMPCardGroupDto dbDto = dmpCardGroupService
									.selectByIm(imei, id);
							if(dbDto==null){
								groupDto.setImei(imei);
								groupDto.setGroupId(id);
								groupListDto.add(groupDto);
								dmpCardGroupService.batchDevice(groupListDto);
							}
							LaoDMPCardDto cardDto = dmpService.selectByIm(imei);
							if (cardDto == null) {
								LaoSsAccountDto user = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
								Long custid = user.getCustId();
								LaoDMPCardDto cdDto = new LaoDMPCardDto();
								Long cardId = 0L;
								cardId =  Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.CARD_ID));
								//cardId = RandomUtil.generateLong(16);
								cdDto.setId(cardId);
								cdDto.setImei(imei);
								cdDto.setCustid(custid);
								cardListDto.add(cdDto);
								dmpService.insertCard(cardListDto);
							}
						}
					} catch (Exception e) {
						resultMap.put("retMsg", "导入文件为空");
					}
				}
			} 
			resultMap.put("success", true);
			resultMap.put("retMsg", "添加成功");
		}
		catch (IOException e) {
			resultMap.put("retMsg", "导入文件失败！");
			e.printStackTrace();
		}
		return resultMap;
	}

	// 根据id查询一条数据
	@RequestMapping("/toUpdate/{id}")
	public ModelAndView toUpdate(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("dmp/editManage");
		LaoDMPGroupDto dto = dmpDeviceManageService.getDeviceManageById(id);
		mv.addObject("dto", dto);
		return mv;
	}

	// 编辑数据
	@ResponseBody
	@RequestMapping(value = "/update")
	public ResultMsg updateManage(HttpServletRequest req, UtilDto utilDto,
			LaoDMPGroupDto dto) {
		ResultMsg msg = new ResultMsg();
		LaoSsAccountDto currentUser = (LaoSsAccountDto) req.getSession()
				.getAttribute("sessionUser");
		dto.setChannelCustid(currentUser.getCustId());
		Integer result = dmpDeviceManageService.updateManage(dto);
		if (result > 0) {
			msg.setSuccess(true);
			msg.setMsg("编辑成功");
		} else {
		msg.setMsg("编辑失败");
		}
		return msg;
	}

	/**
	 * 将设备导入现有设备组
	 * 
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/importCardGroup")
	public Map<String, Object> importCardGroup(
			@RequestParam(value = "file") MultipartFile file,
			HttpServletResponse response, HttpServletRequest request,
			String cardstatus, String ifMaintenance, Long id)
			throws IOException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("retMsg", "导入成功!");
		// 判断上传文件，如果不为空，将之转换成对象
		try {
			if (!file.isEmpty()) {
				String fileName = file.getOriginalFilename();
				if (file.getOriginalFilename().endsWith("xlsx")) {
					InputStream is = file.getInputStream();
					List<Map<String, String>> listMap = null;
					try {
						Map<String, Object> map = FtpsFileList.readFile(is,
								fileName);
						listMap = (List<Map<String, String>>) map.get("listMap");
						LaoDMPCardGroupDto dto = new LaoDMPCardGroupDto();
						for (int i = 0; i < listMap.size(); i++) {
							List<LaoDMPCardDto> cardListDto = new ArrayList<LaoDMPCardDto>();
							List<LaoDMPCardGroupDto> listDto = new ArrayList<LaoDMPCardGroupDto>();
							Map<String, String> mapi = listMap.get(i);
							String imei = mapi.get("0");
							LaoDMPCardGroupDto dbDto = dmpCardGroupService
									.selectByIm(imei, id);
							if (dbDto == null) {
								dto.setImei(imei);
								dto.setGroupId(id);
								listDto.add(dto);
								dmpCardGroupService.batchDevice(listDto);
							}
							LaoDMPCardDto cardDto = dmpService.selectByIm(imei);
							if (cardDto == null) {
								LaoDMPCardDto cdDto = new LaoDMPCardDto();
								Long cardId = 0L;
								cardId =  Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.CARD_ID));
								//cardId = RandomUtil.generateLong(16);
								LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
								Long custid = user.getCustId();
								cdDto.setCustid(custid);
								cdDto.setId(cardId);
								cdDto.setImei(imei);
								cardListDto.add(cdDto);	
								dmpService.insertCard(cardListDto);
							}
						}
					} catch (Exception e) {
						resultMap.put("retMsg", "导入文件为空");
					}
				}
			} 
		} catch (Exception e) {
			resultMap.put("retMsg", "导入文件失败！");
			e.printStackTrace();
		}
		return resultMap;
	}
	@ResponseBody
	@RequestMapping("/deviceNum")
	//查询分组下的设备数量
	public Map<String, Object> deviceNum(Long id){
		Integer deviceNum = dmpCardGroupService.selectDeviceNum(id);
		Map<String, Object> numMap = new HashMap<String, Object>();
		numMap.put("deviceNum",deviceNum);
		return numMap;
	}

	public static void main(String[] args) {
		Long ruleId = RandomUtil.generateLong(16);
		System.out.println(ruleId);
	}
}
