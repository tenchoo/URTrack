package com.urt.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.urt.common.util.ResultMsg;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoSsStaticDto;
import com.urt.interfaces.authority.TagService;

@Controller
@RequestMapping("/ss")
public class StaticController {
	Logger log = Logger.getLogger(getClass());
	@Autowired TagService tagService;
	
	@ResponseBody
	@RequestMapping("/getVersion")
	public ResultMsg getVersion(String pid,String custId) {
		List<Map> list=tagService.queryProductVersionByPid(pid,custId);
		ResultMsg resultMsg=new ResultMsg();
		resultMsg.setObjData(list);
		resultMsg.setSuccess(true);
		return resultMsg;
	}
	@ResponseBody
	@RequestMapping("/getPidByValue")
	public String getPidByValue(String id,String custId) {
		LaoSsStaticDto dto=tagService.getStaticByCustIdCode(id, custId);
		return dto.getStaticId().toString();
		
	}
	/**
	 * 异步获取类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getTypeList")
	public List<Map<String, Object>> getTypeList(String custId) {
		List<Map> maps=tagService.queryProductTypeByCustId(custId);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		Map<String, Object> initMap=new HashMap<String, Object>();
		initMap.put("text", "请选择");
		initMap.put("id", -1);
		list.add(initMap);
		for(Map map:maps){
			Map<String, Object> selectMap=new HashMap<String, Object>();
			selectMap.put("text", map.get("STATIC_NAME"));
			selectMap.put("id", map.get("STATIC_CODE"));
			list.add(selectMap);
		}
		return list;
	}
	
	/**
	 * 异步获取版本数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getVersionList")
	public List<Map<String, Object>> getVersionList(String pid,String custId) {
		List<Map> maps=tagService.queryProductVersionByPid(pid,custId);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		Map<String, Object> initMap=new HashMap<String, Object>();
		initMap.put("text", "请选择");
		initMap.put("id", -1);
		list.add(initMap);
		for(Map map:maps){
			Map<String, Object> selectMap=new HashMap<String, Object>();
			selectMap.put("text", map.get("textKey"));
			selectMap.put("id", map.get("valueKey"));
			list.add(selectMap);
		}
		return list;
	}
	/**
	 * 异步获取属性信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAttrs")
	public Map<String, Object> getAttrs(String custId) {
		Map<String, Object> map=new HashMap<String, Object>();
		List<Map> attr1List = tagService.getOptionsByTypeId("col", "attribute1", custId);
		if(attr1List!=null&&attr1List.size()>0){
			Map attr1=attr1List.get(0);
			map.put("attr1", attr1.get("STATIC_NAME"));
			map.put("attrV1", attr1.get("STATIC_CODE"));
		}
		List<Map> attr2List= tagService.getOptionsByTypeId("col", "attribute2", custId);
		if(attr2List!=null&&attr2List.size()>0){
			Map attr2=attr2List.get(0);
			map.put("attr2", attr2.get("STATIC_NAME"));
			map.put("attrV2", attr2.get("STATIC_CODE"));
		}
		return map;
	}
	/**
	 * 异步获取类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getPoolList")
	public List<Map<String, Object>> getPoolList(String custId) {
		List<Map> maps=tagService.queryPoolsByCustId(custId);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		Map<String, Object> initMap=new HashMap<String, Object>();
		initMap.put("text", "请选择");
		initMap.put("id", -1);
		list.add(initMap);
		for(Map map:maps){
			Map<String, Object> selectMap=new HashMap<String, Object>();
			selectMap.put("text", map.get("STATIC_NAME"));
			selectMap.put("id", map.get("STATIC_CODE"));
			list.add(selectMap);
		}
		return list;
	}
}
