package com.urt.service.Goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.Goods.DiscontDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.Goods.GoodsElementDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.Goods.PersonalOrderDto;
import com.urt.dto.traffic.TrafficQueryNowDto;
import com.urt.interfaces.Goods.DiscontService;
import com.urt.interfaces.Goods.GoodsElementService;
import com.urt.interfaces.Goods.GoodsOrderService;
import com.urt.interfaces.traffic.TrafficQueryService;
import com.urt.mapper.GoodsMapper;
import com.urt.mapper.GoodsReleaseMapper;
import com.urt.mapper.ext.GoodsOrderExtMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.miniService.authority.MiniGoodOrderServiceImpl;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.Goods;
import com.urt.po.GoodsRelease;
import com.urt.po.LaoUser;

@Service("goodsOrderService")
@Transactional(propagation=Propagation.REQUIRED)
public class GoodsOrderServiceImpl implements GoodsOrderService{

	@Autowired
	private GoodsOrderExtMapper goodsOrderExtMapper;
	@Autowired
	private MiniGoodOrderServiceImpl miniGoodOrderServiceImpl;
	@Autowired
	private TrafficQueryService trafficQueryService;
	@Autowired
	private LaoUserExtMapper laoUserExtMapper;
	@Autowired
	private GoodsElementService goodsElementService;
	@Autowired
	private DiscontService discontService;
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private GoodsReleaseMapper goodsReleaseMapper; 
	
	
	public List<LaoUserDto> queryLaoUser(){

		List<LaoUser> laoUserList = goodsOrderExtMapper.queryLaoUser();
		List<LaoUserDto> list = new ArrayList<LaoUserDto>();
		if(null != laoUserList){
			for (LaoUser LaoUser : laoUserList) {
				LaoUserDto laoUserDto = new LaoUserDto();
				BeanMapper.copy(LaoUser, laoUserDto);
				list.add(laoUserDto);
			}
			return list;
		}else{
			return null;
		}

	}
	
	@SuppressWarnings("null")
	public List<LaoUserDto> queryLaoUserCon(String custId,String operatorsId,
			String iccid,String value1,String value2){

		Map<String,Object> param = new HashMap<String,Object>();
		param.put("custId", custId);
		param.put("operatorsId", operatorsId);
		param.put("iccid", iccid);
		param.put("value1", value1);
		param.put("value2", value2);
		List<LaoUser> laoUsers = goodsOrderExtMapper.queryLaoUserCon(param);
		List<LaoUserDto> laoUserDtos = new ArrayList<LaoUserDto>();		
		if(null != laoUsers || laoUsers.size() != 0){
			for (LaoUser laoUser : laoUsers) {
				LaoUserDto laoUserDto = new LaoUserDto();
				BeanMapper.copy(laoUser, laoUserDto);
				laoUserDtos.add(laoUserDto);
			}
			return laoUserDtos;
		}else{
			return null;
		}

	}
	
	@SuppressWarnings("null")
	public List<GoodsDto> queryLaoGoods(String custId,
			String operatorsId,String value1,String value2){
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("custId", custId);
		param.put("operatorsId", operatorsId);
/*		if("-1".equals(value1)){
			param.put("value1", "");
			param.put("value2", "");
		}else{
			param.put("value1", value1);
			param.put("value2", value2);
		}*/
		List<GoodsDto> goodsDtos = new ArrayList<GoodsDto>();
		List<Goods> goodsList = goodsOrderExtMapper.queryLaoGoods(param);
		if(null != goodsList || goodsList.size() != 0){
			for (Goods good : goodsList) {
					GoodsDto goodsDto = new GoodsDto();			
					BeanMapper.copy(good, goodsDto);
					if(("0").equals(good.getUnit())){
						goodsDto.setUnit("月");
					}else if(("1").equals(good.getUnit())){
						goodsDto.setUnit("天");
					}
					
					List<GoodsElementDto> elements = goodsElementService.findGoodsElementByGoodsId(goodsDto.getGoodsId());
					if(elements != null && elements.size() > 0){
						for (GoodsElementDto goodsElementDto : elements) {
							if(("0").equals(goodsElementDto.getElementType())){
								goodsDto.setDiscount(true);
								break;
							}
						}
					}
					goodsDtos.add(goodsDto);
				}
				return goodsDtos;
		}else{
			return null;
		}

	}
	
	@SuppressWarnings("null")
	public List<GoodsDto> queryH5LaoGoods(String custId,
			String operatorsId,String value1,String value2){
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("custId", custId);
		param.put("operatorsId", operatorsId);
/*		if("-1".equals(value1)){
			param.put("value1", "");
			param.put("value2", "");
		}else{
			param.put("value1", value1);
			param.put("value2", value2);
		}*/
		List<GoodsDto> goodsDtos = new ArrayList<GoodsDto>();
		List<Goods> goodsList = goodsOrderExtMapper.queryLaoGoods(param);
		if(null != goodsList || goodsList.size() != 0){
			for (Goods good : goodsList) {
					GoodsDto goodsDto = new GoodsDto();			
					BeanMapper.copy(good, goodsDto);
					if(("0").equals(good.getUnit())){
						goodsDto.setUnit("月");
					}else if(("1").equals(good.getUnit())){
						goodsDto.setUnit("天");
					}
					
					List<GoodsElementDto> elements = goodsElementService.findGoodsElementByGoodsId(goodsDto.getGoodsId());
					if(elements != null && elements.size() > 0){
						for (GoodsElementDto goodsElementDto : elements) {
							if(("0").equals(goodsElementDto.getElementType())){
								goodsDto.setDiscount(true);
								break;
							}
						}
					}
					//如果是优惠产品，展示全名，不是就取产品名中间的总流量
					if(!goodsDto.isDiscount()){
						String goodsname = goodsDto.getGoodsName();
						/* 正则匹配规则有问题，应该只提取流量的数据，不应该提取所有的数字
						String regEx="[^0-9 GM*+]";   
						Pattern p = Pattern.compile(regEx);   
						Matcher matcher = p.matcher(goodsname);
						if(StringUtils.isNotBlank(matcher.replaceAll("").trim())){
							goodsDto.setGoodsName(matcher.replaceAll("").trim());
						}
						*/
						//修改正则规则为："数字+G/M" 提取goodsname中的流量信息
						//如果goodsname中有*号，则直接显示第一条xG*xx的信息，如：5G*12
						//如果goodsname中没有*号，则将所有的xG信息进行加和后显示
						String regEx = "";
						if(goodsname.indexOf("送") > -1){
							regEx=".?[0-9]+(.?[0-9]+)?[GM](\\u002A.?[0-9]+)?(送[0-9]+(.?[0-9]+)?[GM])?"; 
						}else{
							regEx="[0-9]+(.?[0-9]+)?[GM](\\u002A.?[0-9]+)?";   
						}
						Pattern p = Pattern.compile(regEx);   
						Matcher matcher = p.matcher(goodsname.replace(" ", ""));
						if(matcher.find()){
							goodsDto.setGoodsName(matcher.group());
						}else{
							goodsDto.setGoodsName(goodsname);
						}
					}
					goodsDtos.add(goodsDto);
				}
				return goodsDtos;
		}else{
			return null;
		}

	}
	
	//查询客户信息
	@Override
	public Map<String, Object> queryPage(LaoUserDto dto, Integer pageNo,
			Integer pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> map=miniGoodOrderServiceImpl.queryPage(dto, pageNo, pageSize);
		return map;
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("null")
	@Override
	public	List<LaoUserDto> queryLaoUserByCustId(Long custId){
		List<LaoUserDto> laoUserDtoList = new ArrayList<LaoUserDto>();
		List<LaoUser> laoUserList = goodsOrderExtMapper.queryLaoUserByCustId(custId);
		if(null != laoUserList || laoUserList.size() != 0){
			for (LaoUser laoUser : laoUserList) {
				LaoUserDto laoUserDto = new LaoUserDto();
				BeanMapper.copy(laoUser, laoUserDto);
				laoUserDtoList.add(laoUserDto);
			}
			return laoUserDtoList;		
		}else{
			return null;
		}
	
	}
	

	@Override
	public PersonalOrderDto queryPersonalOrderDto(String iccid){
		TrafficQueryNowDto doNowTrafficQuery = trafficQueryService.doNowTrafficQuery(iccid);
		LaoUser queryMessage = laoUserExtMapper.queryMessage(iccid);
		PersonalOrderDto personalOrderDto = new PersonalOrderDto();
		personalOrderDto.setIccid(iccid);
		personalOrderDto.setDataRemaining(doNowTrafficQuery.getDataRemaining());
		personalOrderDto.setCardState(doNowTrafficQuery.getState());
		personalOrderDto.setChannelCustName(doNowTrafficQuery.getChannelCustName());
		personalOrderDto.setOperatorName(doNowTrafficQuery.getOperatorName());
		personalOrderDto.setValue1(queryMessage.getV1Name());
		personalOrderDto.setValue2(queryMessage.getV2Name());		
		return personalOrderDto;
		
	}
	

	@Override
	public String countPrice(Long goodsId,Integer goodsReleaseId) {		
		DiscontDto relDiscontDto = new DiscontDto();
		List<GoodsElementDto> findGoodsElementByGoodsId = goodsElementService.findGoodsElementByGoodsId(goodsId);
		for (GoodsElementDto goodsElementDto : findGoodsElementByGoodsId) {
			if("0".equals(goodsElementDto.getElementType())){
				Integer originalId = goodsElementDto.getOriginalId();
				DiscontDto discontDto = discontService.queryByDiscontId(originalId);
				BeanMapper.copy(discontDto, relDiscontDto);
				
			}else{
				Integer discontValue = 100;
				relDiscontDto.setDiscontValue(discontValue);
			}
		}
		Integer discontValue = relDiscontDto.getDiscontValue();
		GoodsRelease goodsRelease = goodsReleaseMapper.selectByPrimaryKey(goodsReleaseId);
		Double oldPrice = Double.parseDouble(goodsRelease.getReleasePrice().toString());
		Double discont = Double.parseDouble(discontValue.toString());
		Double result = (oldPrice*discont/100);		
		String newPrice = result.toString();
		return newPrice;
	}
	
	@SuppressWarnings("null")
	public List<GoodsDto> queryLaoTestGoods(String custId,
			String operatorsId,String value1,String value2){
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("custId", custId);
		param.put("operatorsId", operatorsId);
		List<GoodsDto> goodsDtos = new ArrayList<GoodsDto>();
		List<Goods> goodsList = goodsOrderExtMapper.queryLaoTestGoods(param);
		if(null != goodsList || goodsList.size() != 0){
			for (Goods good : goodsList) {
					GoodsDto goodsDto = new GoodsDto();			
					BeanMapper.copy(good, goodsDto);
					if(("0").equals(good.getUnit())){
						goodsDto.setUnit("æœˆ");
					}else if(("1").equals(good.getUnit())){
						goodsDto.setUnit("å¤©");
					}
					
					List<GoodsElementDto> elements = goodsElementService.findGoodsElementByGoodsId(goodsDto.getGoodsId());
					if(elements != null && elements.size() > 0){
						for (GoodsElementDto goodsElementDto : elements) {
							if(("0").equals(goodsElementDto.getElementType())){
								goodsDto.setDiscount(true);
								break;
							}
						}
					}
					goodsDtos.add(goodsDto);
				}
				return goodsDtos;
		}else{
			return null;
		}
	}

	@Override
	@SuppressWarnings("null")
	public List<GoodsDto> queryLaoGoodsTSP(String custId,
			String operatorsId,String value1,String value2){
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("custId", custId);
		param.put("operatorsId", operatorsId);
/*		if("-1".equals(value1)){
			param.put("value1", "");
			param.put("value2", "");
		}else{
			param.put("value1", value1);
			param.put("value2", value2);
		}*/
		List<GoodsDto> goodsDtos = new ArrayList<GoodsDto>();
		List<Goods> goodsList = goodsOrderExtMapper.queryLaoGoodsTSP(param);
		if(null != goodsList || goodsList.size() != 0){
			for (Goods good : goodsList) {
					GoodsDto goodsDto = new GoodsDto();			
					BeanMapper.copy(good, goodsDto);
					if(("0").equals(good.getUnit())){
						goodsDto.setUnit("æœˆ");
					}else if(("1").equals(good.getUnit())){
						goodsDto.setUnit("å¤©");
					}
					
					List<GoodsElementDto> elements = goodsElementService.findGoodsElementByGoodsId(goodsDto.getGoodsId());
					if(elements != null && elements.size() > 0){
						for (GoodsElementDto goodsElementDto : elements) {
							if(("0").equals(goodsElementDto.getElementType())){
								goodsDto.setDiscount(true);
								break;
							}
						}
					}
					goodsDtos.add(goodsDto);
				}
				return goodsDtos;
		}else{
			return null;
		}

	}
}
