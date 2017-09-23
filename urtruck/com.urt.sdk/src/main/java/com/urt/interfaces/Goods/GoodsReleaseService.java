package com.urt.interfaces.Goods;

import java.util.List;
import java.util.Map;

import com.urt.dto.Goods.GoodsReleaseDto;

/**
 * 
 * @author zhaoyf
 *
 */
public interface GoodsReleaseService {
	
	public int add(GoodsReleaseDto goodsReleaseDto);
	
	public int delete(Integer goodsReleaseId);
	
	public int update(GoodsReleaseDto goodsReleaseDto);
	
	public GoodsReleaseDto findBygoodsReleaseId(Integer goodsReleaseId);
	
	public List<GoodsReleaseDto> findGoodsRelease();
	
	public List<GoodsReleaseDto> findByChannelGroupId(Long channelGroupId);
	
	public List<GoodsReleaseDto> findByGoodsId(Long goodsId);

	public Map<String, Object> queryPage(GoodsReleaseDto dto,Integer pageNo,Integer pageSize);

	public int updateProductStatus(Integer goodsReleaseId);

	public Map<String, Object> queryCustPage(GoodsReleaseDto goodsReleaseDto, int pageNo, int pageSize);

	public int deleteCustGoods(Integer goodsReleaseId);



	

	
    
	

	

	



}
