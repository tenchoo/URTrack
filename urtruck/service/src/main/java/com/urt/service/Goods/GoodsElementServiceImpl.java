package com.urt.service.Goods;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.Goods.GoodsElementDto;
import com.urt.interfaces.Goods.GoodsElementService;
import com.urt.mapper.DiscontMapper;
import com.urt.mapper.GoodsElementMapper;
import com.urt.mapper.OperatorPlanMapper;
import com.urt.mapper.ext.GoodsElementExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.Discont;
import com.urt.po.GoodsElement;
import com.urt.po.OperatorPlan;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
@Service("goodsElementService")
@Transactional(propagation=Propagation.REQUIRED)
public class GoodsElementServiceImpl implements GoodsElementService{
	
	@Autowired
	private GoodsElementMapper goodsElementMapper;
	@Autowired
	private GoodsElementExtMapper goodsElementExtMapper;
	@Autowired
	private OperatorPlanMapper operatorPlanMapper;
	@Autowired
	private DiscontMapper discontMapper;

	@Override
	public int insert(GoodsElementDto goodsElementDto) {
		// TODO Auto-generated method stub
		Integer elementId = Integer.valueOf(ZkGenerateSeq.get8IdSeq(SeqID.GOODSELEMENT_ID));
		GoodsElement goodsElement = new GoodsElement();
		BeanMapper.copy(goodsElementDto, goodsElement);
		goodsElement.setElementId(elementId);
		goodsElement.setStartDate(new Date());
		return goodsElementMapper.insert(goodsElement);
		
	}

	@SuppressWarnings("null")
	@Override
	public List<GoodsElementDto> findGoodsElementByGoodsId(Long goodsId) {
		// TODO Auto-generated method stub
		List<GoodsElementDto> list =new ArrayList<GoodsElementDto>();
		List<GoodsElement> goodsElementList = goodsElementExtMapper.findGoodsElementByGoodsId(goodsId);
		if(null != goodsElementList || goodsElementList.size() != 0){
			for (GoodsElement goodsElement : goodsElementList) {
				GoodsElementDto goodsElementDto = new GoodsElementDto();
				BeanMapper.copy(goodsElement,goodsElementDto);
				String elementType = goodsElementDto.getElementType();
				if(elementType.equals("1")){
					OperatorPlan selectByPrimaryKey = operatorPlanMapper.selectByPrimaryKey(goodsElementDto.getOriginalId());
					goodsElementDto.setElementName(selectByPrimaryKey.getPlanName());
				}else{
					Discont selectByPrimaryKey = discontMapper.selectByPrimaryKey(goodsElementDto.getOriginalId());
					goodsElementDto.setElementName(selectByPrimaryKey.getDiscontName());
				}
				list.add(goodsElementDto);
			} 
			return list;
		}else{
			return null;
		}

	}


	@Override
	public GoodsElementDto findByGoodsId(Integer GoodsElementId) {
		// TODO Auto-generated method stub		
		GoodsElement goodsElement = goodsElementMapper.selectByPrimaryKey(GoodsElementId);
		if(null != goodsElement){
			GoodsElementDto goodsElementDto = new GoodsElementDto();
			BeanMapper.copy(goodsElement, goodsElementDto);
			return goodsElementDto;
		}else{
			return null;
		}

	}

	@Override
	public int delete(Integer elementId) {
		return goodsElementMapper.deleteByPrimaryKey(elementId);
	}

	@Override
	public int update(GoodsElementDto goodsElementDto) {
		// TODO Auto-generated method stub
		GoodsElement goodsElement = new GoodsElement();
		BeanMapper.copy(goodsElementDto, goodsElement);
		return goodsElementMapper.updateByPrimaryKey(goodsElement);
	}
	@Override
	public GoodsElementDto findByElementId(Integer elementId){
		// TODO Auto-generated method stub
		GoodsElement goodsElement = goodsElementMapper.selectByPrimaryKey(elementId);
		if(null != goodsElement){
			GoodsElementDto goodsElementDto = new GoodsElementDto();
			BeanMapper.copy(goodsElement, goodsElementDto);		
			return goodsElementDto;	
		}else{
			return null;
		}
	
	}

	@Override
	public int inserts(List<GoodsElementDto> list) {
		// TODO Auto-generated method stub
		List<GoodsElement> pos=new ArrayList<GoodsElement>();
		for(GoodsElementDto dto:list){
			Integer elementId = Integer.valueOf(ZkGenerateSeq.get8IdSeq(SeqID.GOODSELEMENT_ID));
			dto.setElementId(elementId);
			dto.setStartDate(new Date());
			GoodsElement goodsElement = new GoodsElement();
			BeanMapper.copy(dto, goodsElement);
			pos.add(goodsElement);
			
		}
		return goodsElementExtMapper.inserts(pos);
	};
}
