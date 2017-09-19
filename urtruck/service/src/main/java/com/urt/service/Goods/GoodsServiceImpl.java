package com.urt.service.Goods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;

import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoUserGoodsDto;
import com.urt.dto.LaoUserOperatorPlanDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.Goods.GoodsReleaseDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.interfaces.Goods.GoodsService;
import com.urt.mapper.GoodsElementMapper;
import com.urt.mapper.GoodsMapper;
import com.urt.mapper.GoodsReleaseMapper;
import com.urt.mapper.LaoUserGoodsMapper;
import com.urt.mapper.LaoUserOperatorPlanMapper;
import com.urt.mapper.OperatorPlanMapper;
import com.urt.mapper.OperatorsMapper;
import com.urt.mapper.ext.GoodsElementExtMapper;
import com.urt.mapper.ext.GoodsExtMapper;
import com.urt.mapper.ext.GoodsReleaseExtMapper;
import com.urt.miniService.authority.MiniGoodsServiceImpl;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.Goods;
import com.urt.po.GoodsElement;
import com.urt.po.GoodsRelease;
import com.urt.po.LaoUserGoods;
import com.urt.po.LaoUserOperatorPlan;
import com.urt.po.OperatorPlan;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
@Service("goodsService")
@Transactional(propagation=Propagation.REQUIRED)
public class GoodsServiceImpl implements GoodsService{
	
    Logger log = Logger.getLogger(getClass());
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private GoodsExtMapper goodsExtMapper;
	@Autowired
	private GoodsElementMapper goodsElementMapper;
	@Autowired
	private GoodsElementExtMapper goodsElementExtMapper;
	@Autowired
	private GoodsReleaseMapper goodsReleaseMapper;
	@Autowired
	private GoodsReleaseExtMapper goodsReleaseExtMapper;
	@Autowired
	private MiniGoodsServiceImpl miniGoodsServiceImpl;
	@Autowired
	private OperatorsMapper operatorsMapper;
	@Autowired
	private OperatorPlanMapper operatorPlanMapper ;
	@Autowired
	private LaoUserOperatorPlanMapper laoUserOperatorPlanDAO ;
	@Autowired
	private LaoUserGoodsMapper laoUserGoodsMapper;
	@Override
	public int insert(GoodsDto goodsDto) {
		// TODO Auto-generated method stub
		long goodsId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.GOODS_ID));		
		Goods goods = new Goods();
		BeanMapper.copy(goodsDto, goods);
		goods.setGoodsId(goodsId);
		goods.setCreatedate(new Date());
		return goodsMapper.insert(goods);
	}
	@Override
	public GoodsDto findByGoodsId(Long goodsId) {
		// TODO Auto-generated method stub
		Goods selectByPrimaryKey = goodsMapper.selectByPrimaryKey(goodsId);
		GoodsDto goodsDto =null;
		if(selectByPrimaryKey!=null){
			goodsDto=new GoodsDto();
			BeanMapper.copy(selectByPrimaryKey, goodsDto);
		}
		return goodsDto;
	}

	@Override
	public int delete(Long goodsId) {
		// TODO Auto-generated method stub
		List<GoodsElement> findGoodsElementByGoodsId = goodsElementExtMapper.findGoodsElementByGoodsId(goodsId);
		for (GoodsElement goodsElement : findGoodsElementByGoodsId) {
			goodsElementMapper.deleteByPrimaryKey(goodsElement.getElementId());
		}		
		List<GoodsRelease> findByGoodsId = goodsReleaseExtMapper.findByGoodsId(goodsId);
		for (GoodsRelease goodsRelease : findByGoodsId) {
			goodsReleaseMapper.deleteByPrimaryKey(goodsRelease.getGoodsReleaseId());
		}
		return goodsMapper.deleteByPrimaryKey(goodsId);
		
	}
	@Override
	public int update(GoodsDto goodsDto) {
		// TODO Auto-generated method stub
		Goods goods = new Goods();
		BeanMapper.copy(goodsDto, goods);
		goods.setUpdatedate(new Date());
		return goodsMapper.updateByPrimaryKey(goods);
		
	}
	@Override
	public List<GoodsDto> findGoods() {

		// TODO Auto-generated method stub
		List<GoodsDto> list =new ArrayList<GoodsDto>();			
		List<Goods> findAll = goodsExtMapper.findAll();
		for (Goods goods : findAll) {
			GoodsDto goodsDto = new GoodsDto();
			BeanMapper.copy(goods, goodsDto);
			list.add(goodsDto);
		}
		return list;
		}
	@Override
	public Map<String, Object> queryPage(GoodsDto dto, Integer pageNo,
			Integer pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> map=miniGoodsServiceImpl.queryPage(dto, pageNo, pageSize);
		return map;
	}
	@Override
	public String getGoodsPricesByGoodsId(String goodsId) {
		Goods goods = goodsMapper.selectByPrimaryKey(Long.valueOf(goodsId));
		GoodsDto goodsDto=new GoodsDto();
		BeanMapper.copy(goods, goodsDto);		
		return goodsDto.getGoodsPrices();
	}

	@Override
    public List<GoodsReleaseDto> insertUserGoods(IccidLibDto lib, LaoUserDto record) {
        List<GoodsReleaseDto> goodsList = new ArrayList<GoodsReleaseDto>();
        List<GoodsRelease> goodsReleaseList = goodsReleaseExtMapper.findByChannelGroupId(record.getChannelCustId());
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
        for(GoodsRelease goodsRelease:goodsReleaseList){
            log.info("GoodsServiceImpl.insertUserGoods GoodsId:"+goodsRelease.getGoodsId()+" Initproduct:"+lib.getInitproduct()+" TestGoodId:"+lib.getTestGoodsRlsId());
            if(goodsRelease.getGoodsReleaseId().equals(Long.valueOf(lib.getInitproduct()))){
                LaoUserGoodsDto goodsDto=new LaoUserGoodsDto();
                goodsDto.setReleaseCycle("2");
                goodsDto.setBiRulesId(2L);
                goodsDto.setStartDate(record.getInDate());
                goodsDto.setStartUseDate(record.getInDate());//å¯ç”¨æ—¶é—´
                goodsDto.setGoodsReleaseId(goodsRelease.getGoodsReleaseId());
                goodsDto.setGoodsId(goodsRelease.getGoodsId());
                goodsDto.setUserId(record.getUserId());              
                try {
                    goodsDto.setEndDate(sdf.parse("20501231235959"));
                } catch (ParseException e) {                  
                    e.printStackTrace();
                }
                LaoUserGoods userGoods = new LaoUserGoods();
                BeanMapper.copy(goodsDto, userGoods);
                GoodsReleaseDto goodsReleaseDto = new GoodsReleaseDto();
                BeanMapper.copy(goodsRelease, goodsReleaseDto);
                laoUserGoodsMapper.insertSelective(userGoods);
                goodsList.add(goodsReleaseDto);
            }else if(goodsRelease.getGoodsReleaseId().equals(lib.getTestGoodsRlsId())){
                LaoUserGoodsDto goodsDto=new LaoUserGoodsDto();
                goodsDto.setReleaseCycle("2");
                goodsDto.setBiRulesId(2L);         
                goodsDto.setGoodsReleaseId(goodsRelease.getGoodsReleaseId());
                goodsDto.setGoodsId(goodsRelease.getGoodsId());
                goodsDto.setUserId(record.getUserId());
                goodsDto.setStartDate(record.getInDate());
                try {
                    goodsDto.setStartUseDate(sdf.parse("20501231235959"));//å¯ç”¨æ—¶é—´
                    goodsDto.setEndDate(sdf.parse("20501231235959"));
                } catch (ParseException e) {                  
                    e.printStackTrace();
                }  
                LaoUserGoods userGoods = new LaoUserGoods();
                BeanMapper.copy(goodsDto, userGoods);
                GoodsReleaseDto goodsReleaseDto = new GoodsReleaseDto();
                BeanMapper.copy(goodsRelease, goodsReleaseDto);
                laoUserGoodsMapper.insertSelective(userGoods);
                goodsList.add(goodsReleaseDto);
            }                    
        }     
        return goodsList;
    }

    @Override
    public List<LaoUserOperatorPlanDto> addLaoUserOperatorPlan(Long goodsId,Long userId,Integer releaseId,Long tradeId,boolean bflag){
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        GoodsRelease goodsRelease = goodsReleaseMapper.selectByPrimaryKey(releaseId);
        List<GoodsElement> goodsElementList = goodsElementExtMapper.findGoodsElementByGoodsId(goodsId);
        List<LaoUserOperatorPlanDto> userOperatorPlans=new ArrayList<LaoUserOperatorPlanDto>();
        for (int i=0;i<goodsElementList.size();i++) {
            GoodsElement goodsElement= goodsElementList.get(i);
            String elementType = goodsElement.getElementType();
            if("1".equals(elementType)){
                LaoUserOperatorPlan userOperatorPlan =  new LaoUserOperatorPlan();
                OperatorPlan operatorPlan = operatorPlanMapper.selectByPrimaryKey(goodsElement.getOriginalId());
                userOperatorPlan.setUserId(userId);         
                userOperatorPlan.setGoodsId(goodsId);
                userOperatorPlan.setGoodsIndex(goodsElement.getGoodsIndex());
                userOperatorPlan.setOperatorsId(operatorPlan.getOperators());
                userOperatorPlan.setOperatorsPid(operatorPlan.getOperatorsPid());
                userOperatorPlan.setPlanId(operatorPlan.getPlanId());
                userOperatorPlan.setPlanType(operatorPlan.getPlanType());
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(new Date());
                if("0".equals(goodsRelease.getUnit())){
                    calendar.add(Calendar.MONTH, Integer.valueOf(goodsRelease.getReleaseCycle()));
                }else{
                    calendar.set(Calendar.DATE, Integer.valueOf(goodsRelease.getReleaseCycle()));
                }
                if(goodsRelease.getSilentPeriod()!=null){
                     calendar.add(Calendar.MONTH, Integer.valueOf(goodsRelease.getSilentPeriod()));
                }
                userOperatorPlan.setEndDate(calendar.getTime());
                userOperatorPlan.setOpeartorsDealCode("");
                userOperatorPlan.setOpeartorsDealNum(0);
                
                if("4".equals(goods.getGoodsType())){
                    if(i==0 || i==1){
                        userOperatorPlan.setOpeartorsDealRst("3");
                    }else{
                        userOperatorPlan.setOpeartorsDealRst("2");
                    }
                }else if("5".equals(goods.getGoodsType()) || "6".equals(goods.getGoodsType())){
                    if(i==0){
                        userOperatorPlan.setOpeartorsDealRst("3");
                    }else{
                        userOperatorPlan.setOpeartorsDealRst("2");
                    }
                }else{
                    userOperatorPlan.setOpeartorsDealRst("3");
                }
                
                if(bflag && !"7".equals(goods.getGoodsType())){ //æµ‹è¯•æœŸå¼€é€šï¼Œä¸”ä¸æ˜¯æµ‹è¯•æœŸäº§å“ 
                    try {
                        userOperatorPlan.setStartDate(new SimpleDateFormat("YYYYMMddHHmmss").parse("20501231235959"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else{
                    userOperatorPlan.setStartDate(new Date());
                }
                //ä¸ºå‡ºè´¦æ–°å¢žå­—æ®µ
                userOperatorPlan.setTradeId(tradeId);
                userOperatorPlan.setCostPrice(goods.getGoodsPrices());
                userOperatorPlan.setBillTag("0");//0-æœªå‡ºè´¦ï¼›1-å·²å‡ºè´¦
                
                userOperatorPlan.setGoodsReleaseId(releaseId);
                laoUserOperatorPlanDAO.insert(userOperatorPlan);
                LaoUserOperatorPlanDto userOperDto = new LaoUserOperatorPlanDto();
                BeanMapper.copy(userOperatorPlan, userOperDto);
                userOperatorPlans.add(userOperDto);
            }
        }
        return userOperatorPlans;
        
    }	

}
