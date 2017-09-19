package com.urt.interfaces.Goods;

import java.util.List;

import com.urt.dto.Goods.GoodsElementDto;

/**
 * 
 * @author zhaoyf
 *
 */
public interface GoodsElementService {
	
	public int insert(GoodsElementDto GoodsElementDto);
	
	public int inserts(List<GoodsElementDto> GoodsElementDtos);
	
	public int delete(Integer elementId);
	
	public int update(GoodsElementDto goodsElementDto);
	
	public GoodsElementDto findByGoodsId(Integer GoodsElementId);
	
	public GoodsElementDto findByElementId(Integer elementId);

	public List<GoodsElementDto> findGoodsElementByGoodsId(Long goodsId);

}
