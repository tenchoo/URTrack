package com.urt.miniService.authority;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.LaoPoolDto;
import com.urt.dto.LaoPoolMemberDto;
import com.urt.dto.LaoPoolUseInfoDto;
import com.urt.dto.Goods.GoodsReleaseDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.mapper.ext.LaoPoolExtMapper;
import com.urt.mapper.ext.LaoPoolMemberExtMapper;
import com.urt.mapper.ext.LaoPoolUseInfoExtMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.po.LaoPool;
import com.urt.po.LaoPoolMember;
import com.urt.po.LaoPoolUseInfo;
import com.urt.po.LaoUser;
@Service("MiniGoodOrderServiceImpl")
public class MiniGoodOrderServiceImpl {

	@Autowired
	private	LaoUserExtMapper laoUserExtMapper;
	
	@Autowired
	private LaoPoolExtMapper laoPoolExtMapper;
	
	@Autowired
	private LaoPoolMemberExtMapper laoPoolMemberExtMapper;
	
	@Autowired
	private LaoPoolUseInfoExtMapper laoPoolUseInfoExtMapper;
	/**
	 * 功能描述：查询
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryPage(LaoUserDto dto, int pageNo, int pageSize) {
		Page<LaoUser> page = new Page<LaoUser>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (LaoUser) ConversionUtil.dto2po(dto, LaoUser.class));
		page.setParams(params);
		List<LaoUser> laoUserList = laoUserExtMapper.queryPage(page);

		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", ConversionUtil.poList2dtoList(laoUserList, LaoUserDto.class));
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
		return resultMap;
	}
	
	public Map<String, Object> querypoolInfoMe(LaoPoolDto dto, int pageNo, int pageSize) {
		Page<LaoPool> page = new Page<LaoPool>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (LaoPool) ConversionUtil.dto2po(dto, LaoPool.class));
		page.setParams(params);
		List<LaoPool> laoPoolList = laoPoolExtMapper.querypoolInfoMethod(page);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", ConversionUtil.poList2dtoList(laoPoolList, LaoPoolDto.class));
		resultMap.put("iTotalRecords", pageSize);//å½“å‰é¡µåŒ…å«çš„è®°å½•æ•°
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//æ€»è®°å½•æ•°
		return resultMap;
	}


	public Map<String, Object> poolMenberInfoMe(LaoPoolMemberDto dto, int pageNo, int pageSize) {
		Page<LaoPoolMember> page = new Page<LaoPoolMember>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (LaoPoolMember) ConversionUtil.dto2po(dto, LaoPoolMember.class));
		page.setParams(params);
		System.out.println("MiniGoodOrderServiceImpl----------------page.getParams"+((LaoPoolMember)page.getParams().get("param")).toString());
		List<LaoPoolMember> poolMenberList = laoPoolMemberExtMapper.queryMenberInfoMethod(page);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", ConversionUtil.poList2dtoList(poolMenberList, LaoPoolMemberDto.class));
		resultMap.put("iTotalRecords", pageSize);//å½“å‰é¡µåŒ…å«çš„è®°å½•æ•°
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//æ€»è®°å½•æ•° 
		return resultMap;
	}


	public Map<String, Object> poolUseInfoMe(LaoPoolUseInfoDto dto, int pageNo, int pageSize) {
		Page<LaoPoolUseInfo> page = new Page<LaoPoolUseInfo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (LaoPoolUseInfo) ConversionUtil.dto2po(dto, LaoPoolUseInfo.class));
		params.put("Start", dto.getMonthStart());
		params.put("End", dto.getMonthStart());
		page.setParams(params);
		List<LaoPoolUseInfo> poolUseInfoList = laoPoolUseInfoExtMapper.querypoolUseInfoMethod(page);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", ConversionUtil.poList2dtoList(poolUseInfoList, LaoPoolUseInfoDto.class));
		resultMap.put("iTotalRecords", pageSize);//å½“å‰é¡µåŒ…å«çš„è®°å½•æ•°
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//æ€»è®°å½•æ•° 
		return resultMap;
	}
}
