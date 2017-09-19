package com.urt.web.web.deviceBindings;

import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.urt.common.util.ResultMsg;
import com.urt.dto.LaoDeviceRelDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoSsStaticDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.interfaces.device.DeviceRelService;
import com.urt.interfaces.traffic.BatchTrafficQueryService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
import com.urt.web.common.util.FtpsFileList;

/**
 * 类说明：
 * 设备绑定：单卡,批量操作
 * 
 * 
 */
@Controller
@RequestMapping("/deviceBindings")
public class DeviceBindingsController {
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private DeviceRelService deviceRelService;
	@Autowired
	private BatchTrafficQueryService batchTrafficQueryService;	
	/**
	 * 上传excel文件信息界面
	 */
	@RequestMapping("/comeDeviceBindings")
	public ModelAndView uploadExcel() {
		ModelAndView mv = new ModelAndView("/device/deviceBindings");
		return mv; 	
	}
	
	/**
	 * 分页查询展示
	 * 
	 * @param req
	 * @param response
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/toSelectByCondition")
	public Map<String, Object> toSelectByCondition(HttpServletRequest req,
			HttpServletResponse response, LaoDeviceRelDto dto) {
		if (dto != null) {
			if ("-1".equals(dto.getChannelCustId()+"")) {
				dto.setChannelCustId(null);
			}
			if ("-1".equals(dto.getIdType())) {
				dto.setIdType(null);
			}
		}
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;
		Map<String, Object> resultMap = deviceRelService.selectByCondition(dto,pageNo, pageSize);
		return resultMap;
	}
	
	/**
	 * 单个设备绑定
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addOneDeviceBindings")
	public Map<String, Object> doAdd(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String iccid = request.getParameter("iccid");
		String idType = request.getParameter("idType");
		String deviceId = request.getParameter("deviceId");
		try {
			if (StringUtils.isBlank(iccid)||StringUtils.isBlank(idType)||StringUtils.isBlank(deviceId)) {
				map.put("msg", "传入参数不能为空！");
				return map;
			}
			LaoUserDto laoUserDto = deviceRelService.selectByIccid(iccid);
			if (laoUserDto == null) {
				map.put("msg", "该卡号不存在,请确认iccid是否正确!");
				return map;
			}
			// 判断该设备类型是否已绑定
			LaoDeviceRelDto laoDeviceRelDto = batchTrafficQueryService.selectByUserIdAndidType(laoUserDto.getUserId(), idType);
			if (laoDeviceRelDto != null) {
				map.put("msg", "该设备类型已绑定！");
				return map;
			}
			LaoDeviceRelDto dto = new LaoDeviceRelDto();
			dto.setRelId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID)));
			dto.setIccid(iccid);
			dto.setIdType(idType);
			dto.setDeviceId(deviceId);
			dto.setUserId(laoUserDto.getUserId());
			dto.setValidTag("1");
			dto.setRecvTime(new Date());
			dto.setUpdateTime(new Date());
			LaoSsAccountDto user = (LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
			String accountId = "";
			if(user != null) {
				accountId = user.getAcconutId().toString();
			}
			dto.setOperId(accountId);
			int in = deviceRelService.insertSelective(dto);
			if (in == 1) {
				map.put("msg", "新增设备绑定成功！");
			} else {
				map.put("msg", "绑定失败！");
			}
		} catch (NumberFormatException e) {
			map.put("msg", "出现异常！");
			e.printStackTrace();
			return map;
		}
		return map;
	}
	/**
	 * 进入查询页面 加载渠道客户名称
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delOneDeviceBindings")
	public Map<String, Object> delOneDeviceBindings(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String relId = request.getParameter("relId");
		try {
			if (StringUtils.isBlank(relId)) {
				map.put("msg", "传入参数不能为空！");
				return map;
			}
			Long relIdLong = Long.parseLong(relId);
			int in = deviceRelService.deleteByPrimaryKey(relIdLong);
			if (in == 1) {
				map.put("msg", "删除成功！");
			} else {
				map.put("msg", "删除失败！");
			}
		} catch (NumberFormatException e) {
			map.put("msg", "出现异常！");
			e.printStackTrace();
			return map;
		}
		return map;
	}
	
	@RequestMapping("/toUpdate/{relId}")
	public ModelAndView toUpdate(@PathVariable("relId") Long relId) {
		ModelAndView mv = new ModelAndView("device/edit");
		LaoDeviceRelDto dto = null;
		if (!StringUtils.isBlank(relId+"")) {
			dto = deviceRelService.selectByPrimaryKey(relId);
		}
		mv.addObject("dto", dto);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/doUpdate")
	public ResultMsg doUpdate(LaoDeviceRelDto dto) {
		ResultMsg msg = new ResultMsg();
		try {
			if (dto != null) {
				LaoDeviceRelDto laoDeviceRelDto = batchTrafficQueryService.selectByUserIdAndidType(dto.getUserId(), dto.getIdType());
				if (laoDeviceRelDto != null) {
					msg.setSuccess(false);
					return msg;
				}
				int in = deviceRelService.updateByPrimaryKeySelective(dto);
				if (in == 1) {
					msg.setSuccess(true);
				}
			}
		} catch (Exception e) {
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}
	/**
	 * 进入查询页面 加载渠道客户名称
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getIdType")
	public List<Map<String, Object>> getIdType(HttpServletRequest request,
			HttpServletResponse response) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> initMap = new HashMap<String, Object>();
		List<LaoSsStaticDto> dtos = deviceRelService.getIdTypeByDeviceRel();
		initMap.put("text", "请选择");
		initMap.put("id", -1);
		list.add(initMap);
		for (LaoSsStaticDto dto : dtos) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", dto.getStaticName());
			map.put("id", dto.getStaticName());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 批量设备绑定
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/batchImport")
	public Map<String,Object> batchDeviceBindings(@RequestParam(value = "file") MultipartFile file,HttpServletResponse response, HttpServletRequest request, String cardstatus, String ifMaintenance)
			throws IOException {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("retMsg", "导入成功!");
		// 判断上传文件，如果不为空，将之转换成对象
		try {
			if (!file.isEmpty()) {
				String fileName = file.getOriginalFilename();
				if (file.getOriginalFilename().endsWith("xlsx")) {
					InputStream is = file.getInputStream();
					List<Map<String, String>> listMap = null;
					Map<String, Object> map = FtpsFileList.readFile(is, fileName);
					listMap = (List<Map<String, String>>) map.get("listMap");
					LaoSsAccountDto user = (LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
					String accountId = "";
					if(user != null) {
						accountId = user.getAcconutId().toString();
					}
					List<LaoDeviceRelDto> listDto = new ArrayList<LaoDeviceRelDto>();
					for (int i = 0; i < listMap.size(); i++) {
						Map<String, String> mapi = listMap.get(i);
						String iccid = mapi.get("0");
						LaoUserDto userDto = deviceRelService.selectByIccid(iccid);
						if (userDto != null) {
							LaoDeviceRelDto laoDeviceRelDto = batchTrafficQueryService.selectByUserIdAndidType(userDto.getUserId(), mapi.get("1"));
							if (laoDeviceRelDto != null) {
								resultMap.put("retMsg", "导入文件中iccid，有已经绑定对应设备类型的！");
								return resultMap;
							}
							LaoDeviceRelDto dto = new LaoDeviceRelDto();
							dto.setRelId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID)));
							dto.setUserId(userDto.getUserId());
							dto.setIccid(iccid);
							dto.setIdType(mapi.get("1"));
							dto.setDeviceId(mapi.get("2"));
							dto.setValidTag("1");
							dto.setRecvTime(new Date());
							dto.setOperId(accountId);
							dto.setUpdateTime(new Date());
							dto.setRemark("批量绑定设备");
							dto.setRelType("");
							dto.setRelAccount("");
							listDto.add(dto);
						} else {
							resultMap.put("retMsg", "导入文件中的iccid，有不存在用户表的！");
							return resultMap;
						}
					}
					deviceRelService.batchInsert(listDto);
				}
			} else {
				resultMap.put("retMsg", "导入文件为空！");
			}
		} catch (Exception e) {
			resultMap.put("retMsg", "导入文件失败！");
			e.printStackTrace();
			return resultMap;
		}
		return resultMap;
	}
	
	
}

