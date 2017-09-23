package com.urt.miniService.authority;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.Goods.GoodsDto;
import com.urt.mapper.ext.GoodsExtMapper;
import com.urt.po.Goods;
import com.urt.po.GoodsRelease;
@Service("miniGoodsServiceImpl")
public class MiniGoodsServiceImpl {
	@Autowired
	private GoodsExtMapper goodsExtMapper;
	/**
	 * 功能描述：查询
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryPage(GoodsDto dto, int pageNo, int pageSize) {
		Page<Goods> page = new Page<Goods>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (Goods) ConversionUtil.dto2po(dto, Goods.class));
		page.setParams(params);
		List<Goods> goodsList = goodsExtMapper.queryGoodsByPage(page);
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", ConversionUtil.poList2dtoList(goodsList, GoodsDto.class));
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
		return resultMap;
	}
}
