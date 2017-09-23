package com.urt.interfaces.Goods;

import java.util.List;
import java.util.Map;

import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoUserOperatorPlanDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.Goods.GoodsReleaseDto;
import com.urt.dto.Goods.LaoUserDto;

/**
 * 
 * @author zhaoyf
 *
 */
public interface GoodsService {
		
	public int insert(GoodsDto goodsDto);
	
	public int delete(Long goodsId);
	
	public int update(GoodsDto goodsDto);
		
	public GoodsDto findByGoodsId(Long goodsId);
		
	public List<GoodsDto> findGoods();
	
	public Map<String, Object> queryPage(GoodsDto dto,Integer pageNo,Integer pageSize);
	
	public String getGoodsPricesByGoodsId(String goodsId);
	
    public List<GoodsReleaseDto> insertUserGoods(IccidLibDto lib,LaoUserDto record);

    public List<LaoUserOperatorPlanDto> addLaoUserOperatorPlan(Long goodsId,Long userId,Integer releaseId,Long tradeId,boolean bflag);
		
}
