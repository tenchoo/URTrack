package com.urt.service.Goods;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.Goods.GoodsReleaseDto;
import com.urt.interfaces.Goods.GoodsReleaseService;
import com.urt.mapper.GoodsMapper;
import com.urt.mapper.GoodsReleaseMapper;
import com.urt.mapper.LaoCustomerPoMapper;
import com.urt.mapper.ext.GoodsReleaseExtMapper;
import com.urt.mapper.ext.LaoSsStaticPoExtMapper;
import com.urt.miniService.authority.MiniGoodReleaseServiceImpl;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.GoodsRelease;
import com.urt.po.LaoCustomerPo;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
@Service("goodsReleaseService")
@Transactional(propagation=Propagation.REQUIRED)
public class GoodsReleaseServiceImpl implements GoodsReleaseService{
	
	@Autowired
	private GoodsReleaseMapper goodsReleaseMapper;
	@Autowired
	private GoodsReleaseExtMapper goodsReleaseExtMapper;
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private LaoCustomerPoMapper laoCustomerPoMapper;
	@Autowired
	private MiniGoodReleaseServiceImpl miniGoodReleaseServiceImpl;
	@Autowired
	private LaoSsStaticPoExtMapper laoSsStaticPoExtMapper;
	@Override
	public int add(GoodsReleaseDto goodsReleaseDto) {
		// TODO Auto-generated method stub
		Integer goodsReleaseId = Integer.valueOf(ZkGenerateSeq.get8IdSeq(SeqID.GOODRELEASE_ID));
		GoodsRelease goodsRelease = new GoodsRelease();
		BeanMapper.copy(goodsReleaseDto, goodsRelease);
		goodsRelease.setGoodsReleaseId(goodsReleaseId);
		return goodsReleaseMapper.insertSelective(goodsRelease);

	}
	@Override
	public int delete(Integer goodsReleaseId) {
		return goodsReleaseMapper.deleteByPrimaryKey(goodsReleaseId);
	}
	
	 
	
	
	
	@Override
	public int update(GoodsReleaseDto goodsReleaseDto) {
		// TODO Auto-generated method stub
		GoodsRelease goodsRelease = new GoodsRelease();
		BeanMapper.copy(goodsReleaseDto, goodsRelease);
		return goodsReleaseMapper.updateByPrimaryKey(goodsRelease);
	}

	@Override
	public GoodsReleaseDto findBygoodsReleaseId(Integer goodsReleaseId) {
		// TODO Auto-generated method stub
		GoodsRelease goodsRelease = goodsReleaseMapper.selectByPrimaryKey(goodsReleaseId);
		if(null != goodsRelease){
			GoodsReleaseDto goodsReleaseDto = new GoodsReleaseDto();
			BeanMapper.copy(goodsRelease, goodsReleaseDto);
			return goodsReleaseDto;
		}else{
			return null;
		}

	}

	@SuppressWarnings("null")
	@Override
	public List<GoodsReleaseDto> findGoodsRelease() {
		// TODO Auto-generated method stub
		List<GoodsReleaseDto> list = new ArrayList<GoodsReleaseDto>();
		List<GoodsRelease> goodsReleaseList = goodsReleaseExtMapper.findAll();
		if( null != goodsReleaseList || goodsReleaseList.size() != 0){
			for (GoodsRelease goodsRelease : goodsReleaseList) {
				GoodsReleaseDto goodsReleaseDto = new GoodsReleaseDto();
				BeanMapper.copy(goodsRelease, goodsReleaseDto);
				list.add(goodsReleaseDto);
			}
			return list;
		}else{
			return null;
		}

	}

	@SuppressWarnings("null")
	@Override
	public List<GoodsReleaseDto> findByChannelGroupId(Long channelGroupId) {
		// TODO Auto-generated method stub
		List<GoodsReleaseDto> list = new ArrayList<GoodsReleaseDto>();
		List<GoodsRelease> goodsReleaseList = goodsReleaseExtMapper.findByChannelGroupId(channelGroupId);
		if(null != goodsReleaseList || goodsReleaseList.size() != 0 ){
			for (GoodsRelease goodsRelease : goodsReleaseList) {
				GoodsReleaseDto goodsReleaseDto = new GoodsReleaseDto();
				BeanMapper.copy(goodsRelease, goodsReleaseDto);
				long goodsId = goodsReleaseDto.getGoodsId();
				String goodsName = goodsMapper.selectByPrimaryKey(goodsId).getGoodsName();
				goodsReleaseDto.setGoodsName(goodsName);
				Long custId = goodsReleaseDto.getChannelGroupId();
				LaoCustomerPo selectByPrimaryKey = laoCustomerPoMapper.selectByPrimaryKey(custId);
				goodsReleaseDto.setCustName(selectByPrimaryKey.getCustName());
				list.add(goodsReleaseDto);
			}
			return list;
		}else{
			return null;
		}

	}

	@SuppressWarnings("null")   
	@Override
	public List<GoodsReleaseDto> findByGoodsId(Long goodsId) {
		// TODO Auto-generated method stub
		
		List<GoodsReleaseDto> list = new ArrayList<GoodsReleaseDto>();
		List<GoodsRelease> goodsReleaseList = goodsReleaseExtMapper.findByGoodsId(goodsId);
		if(null != goodsReleaseList || goodsReleaseList.size() != 0){
			for (GoodsRelease goodsRelease : goodsReleaseList) {
				GoodsReleaseDto goodsReleaseDto = new GoodsReleaseDto();
				BeanMapper.copy(goodsRelease, goodsReleaseDto);
				list.add(goodsReleaseDto);
			}
			return list;
		}else{
			return null;
		}

	}
	@Override
	public Map<String, Object> queryPage(GoodsReleaseDto dto, Integer pageNo,
			Integer pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> map=miniGoodReleaseServiceImpl.queryPage(dto, pageNo, pageSize);
		return map;
	}
	@Override
	public int updateProductStatus(Integer goodsReleaseId) {
		GoodsRelease goodsRelease = new GoodsRelease();
		goodsRelease.setEndDate(new Date());
		return goodsReleaseMapper.updateByPrimaryKeySelective(goodsRelease) ;
	}
	//查询客户产品展示列表
	@Override
	public Map<String, Object> queryCustPage(GoodsReleaseDto goodsReleaseDto, int pageNo, int pageSize) {
	Map<String, Object> map=miniGoodReleaseServiceImpl.queryCustPage(goodsReleaseDto, pageNo, pageSize);
		return map;
	}
	@Override
	public int deleteCustGoods(Integer goodsReleaseId) {	
	return goodsReleaseMapper.deleteByPrimaryKey(goodsReleaseId);
	}
	
}	
	
	
	

