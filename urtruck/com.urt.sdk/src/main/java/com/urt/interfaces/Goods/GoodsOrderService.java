package com.urt.interfaces.Goods;

import java.util.List;
import java.util.Map;

import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.Goods.PersonalOrderDto;
/**
 * 产品订购
 * @author zhaoyf
 *
 */
public interface GoodsOrderService {
	/**
	 * 查询所有卡片信息
	 * @return
	 */
	List<LaoUserDto> queryLaoUser();
	/**
	 * 
	 * @param custId
	 * @param operatorsId
	 * @param iccid
	 * @param value1
	 * @param value2
	 * @return
	 */
	List<LaoUserDto> queryLaoUserCon(String custId,String operatorsId,String iccid,String value1,String value2);
	/**
	 * 展示卡对应的产品
	 * @param custId
	 * @param operatorsId
	 * @param value1
	 * @param value2
	 * @return
	 */
	List<GoodsDto> queryLaoGoods(String custId,String operatorsId,String value1,String value2);	
	public List<GoodsDto> queryH5LaoGoods(String custId,String operatorsId,String value1,String value2);
	/**
	 * 
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryPage(LaoUserDto dto,Integer pageNo,Integer pageSize);
	/**
	 * 个人订购展示绑定卡片信息
	 * @param custId
	 * @return
	 */
	List<LaoUserDto> queryLaoUserByCustId(Long custId);
	/**
	 * 个人订购卡信息展示
	 * @param iccid
	 * @return
	 */
	PersonalOrderDto queryPersonalOrderDto(String iccid);
	/**
	 * 计算价格
	 * @param goodsId
	 * @return
	 */
	public String countPrice(Long goodsId,Integer goodsReleaseId);
	
	List<GoodsDto> queryLaoTestGoods(String custId,String operatorsId,String value1,String value2);
	
	List<GoodsDto> queryLaoGoodsTSP(String custId, String operatorsId, String value1, String value2);
}
