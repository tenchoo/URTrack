package com.urt.miniService.authority;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Goods.GoodsReleaseDto;
import com.urt.mapper.ext.GoodsReleaseExtMapper;
import com.urt.po.GoodsRelease;
@Service("miniGoodReleaseServiceImpl")
public class MiniGoodReleaseServiceImpl {
	@Autowired
	private GoodsReleaseExtMapper goodsReleaseExtMapper;
	/**
	 * 功能描述：查询
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryPage(GoodsReleaseDto dto, int pageNo, int pageSize) {
		Page<GoodsRelease> page = new Page<GoodsRelease>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (GoodsRelease) ConversionUtil.dto2po(dto, GoodsRelease.class));
		page.setParams(params);
		List<GoodsRelease> goodsReleaseList = goodsReleaseExtMapper.queryPage(page);
		
		for (GoodsRelease goodsRelease : goodsReleaseList) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			goodsRelease.setStartDateValue(sdf.format(goodsRelease.getStartDate()));
			goodsRelease.setEndDateValue(sdf.format(goodsRelease.getEndDate()));
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", ConversionUtil.poList2dtoList(goodsReleaseList, GoodsReleaseDto.class));
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
		return resultMap;
	}
	//查询客户产品展示列表
	public Map<String, Object> queryCustPage(GoodsReleaseDto goodsReleaseDto, int pageNo, int pageSize) {
		
		Page<GoodsRelease> page = new Page<GoodsRelease>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		page.setParams(params);
		params.put("param", goodsReleaseDto);
		List<GoodsRelease> goodsReleaseList = goodsReleaseExtMapper.queryPage(page);
		for (GoodsRelease goodsRelease : goodsReleaseList) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			goodsRelease.setStartDateValue(sdf.format(goodsRelease.getStartDate()));
			goodsRelease.setEndDateValue(sdf.format(goodsRelease.getEndDate()));
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", ConversionUtil.poList2dtoList(goodsReleaseList, GoodsReleaseDto.class));
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
		return resultMap;
	}
}
