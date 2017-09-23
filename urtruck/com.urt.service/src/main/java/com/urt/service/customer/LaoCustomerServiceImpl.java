package com.urt.service.customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.dto.LaoCustomerDto;
import com.urt.dto.custcentre.LaoCustomerAccountDto;
import com.urt.interfaces.customer.LaoCustomerService;
import com.urt.mapper.LaoCustPersonPoMapper;
import com.urt.mapper.LaoCustomerPoMapper;
import com.urt.mapper.LaoCustomerStyleMapper;
import com.urt.mapper.ext.LaoCustomerPoExtMapper;
import com.urt.mapper.ext.LaoFAcctDepositPoExtMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.miniService.MiniSsCustomerServiceImpl;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoCustPersonPo;
import com.urt.po.LaoCustomerPo;
import com.urt.po.LaoCustomerStyle;
import com.urt.po.LaoCustomerVerifyPo;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

@Service("customerService")
public class LaoCustomerServiceImpl implements LaoCustomerService{
	@Autowired
	MiniSsCustomerServiceImpl miniCutomerService;
	@Autowired
	LaoCustomerPoMapper dao;
	@Autowired
	LaoCustomerPoExtMapper daoExt;
	@Autowired
	LaoFAcctDepositPoExtMapper depositPoExtDao;
	@Autowired
	LaoUserExtMapper userExtDao;
	@Autowired
	LaoCustomerPoMapper customerDao;
	@Autowired
	LaoCustPersonPoMapper custPsersonDao;
	
	@Autowired
	LaoCustomerStyleMapper  customerStyle;
	
	@Override
	public Map<String, Object> queryPage(LaoCustomerDto dto,
			Integer pageNo, Integer pageSize) {
		Map<String, Object> map=miniCutomerService.queryPage(dto, pageNo, pageSize);
		return map;
	}
	
	
	
	/**
	 * 分润管理
	 */
	
	@Override
	public Map<String, Object> feeQueryPageList(LaoCustomerDto dto,
			Integer pageNo, Integer pageSize) {
		Map<String, Object> map=miniCutomerService.queryPage(dto, pageNo, pageSize);
		@SuppressWarnings("unchecked")
		List<LaoCustomerDto> list = (List<LaoCustomerDto>) map.get("data");
		for(LaoCustomerDto custDto: list){
			List<LaoCustomerDto> laoList = queryChildCustList(custDto.getCustId(),false);
			if(null==laoList || laoList.size()==0){
				custDto.setEnterpriseNumber(0);
			}else{
				custDto.setEnterpriseNumber(laoList.size());
			}
			
		}
		map.put("data", list);
		return map;
	}
	@Override
	public Map<String, Object> feeQueryPage(LaoCustomerDto dto,
			Integer pageNo, Integer pageSize) {
		Map<String, Object> map=miniCutomerService.feeQueryPage(dto, pageNo, pageSize);
		return map;
	}
	
	@Override
	public int save(LaoCustomerDto dto) {
		// TODO Auto-generated method stub
		LaoCustomerPo po=(LaoCustomerPo)ConversionUtil.dto2po(dto, LaoCustomerPo.class);
		return dao.insertSelective(po);
	}
	@Override
	public int update(LaoCustomerDto dto) {
		// TODO Auto-generated method 
		/*LaoCustomerPo po=(LaoCustomerPo)ConversionUtil.dto2po(dto, LaoCustomerPo.class);*/
		LaoCustomerPo po=new LaoCustomerPo();
		BeanMapper.copy(dto, po);
		return dao.updateByPrimaryKeySelective(po);
	}
	@Override
	public LaoCustomerDto selectDtoById(Long custId) {
		LaoCustomerPo po=dao.selectByPrimaryKey(custId);
		LaoCustomerDto dto=(LaoCustomerDto)ConversionUtil.po2dto(po, LaoCustomerDto.class);
		return dto;
	}
	@Override
	public List<LaoCustomerDto> queryAgent() {
		// TODO Auto-generated method stub
		List<LaoCustomerPo> pos=daoExt.queryAgent(null);
		List<LaoCustomerDto> dtos=new ArrayList<LaoCustomerDto>();
		for(LaoCustomerPo po:pos){
			LaoCustomerDto dto=new LaoCustomerDto();
			BeanMapper.copy(po, dto);
			dtos.add(dto);
		}
		return dtos;
	}
	@Override
	public Long selectDepositMoney(Long custId) {
		Long depositMoney = depositPoExtDao.selectDepositMomey(custId);
		
		return depositMoney;
	}
	@Override
	public List<Map<String, Object>> selectCustCardInfo(Long channelCustId) {
		List<Map<String, Object>> cardMap = userExtDao.selectCustCardInfo(channelCustId);
		return cardMap;
	}

	//查询本月订购产品
		@Override
		public Map<String, Object> selectGoodsCorporate(Long channelCustId) {
			Map<String,Object> map = new HashMap<String,Object>();
			List<Map<String, Object>> list = userExtDao.selectGoodsCorporate(channelCustId);
			if (list != null && list.size() > 0) {
				String[] data1 = new String[list.size()+1];
				@SuppressWarnings("unchecked")
				Map<String,Object>[] data2 = new Map[list.size()];
				for (int i = 0; i < list.size(); i++) {
					Map<String,Object> mapi = list.get(i);
					BigDecimal big = (BigDecimal) mapi.get("SUM");
					int sumi = big.intValue();
					String goodsName = mapi.get("GOODSNAME").toString();
					data1[i] = goodsName;
					Map<String,Object> mapData2 = new HashMap<String,Object>();
					mapData2.put("value", sumi);
					mapData2.put("name", goodsName);
					data2[i] = mapData2;
				}
				data1[list.size()] = "";
				map.put("data1", data1);
				map.put("data2", data2);
			}
			return map;
		}
	@Override
	public List<Map<String, Object>> selectCategoryCount(Long channelCustId) {
		List<Map<String, Object>> categoryMap = userExtDao.selectCategoryCount(channelCustId);
		return categoryMap;
	}
	@Override
	public long initCustInfo(LaoCustomerDto dto) {
		// TODO Auto-generated method stub
		Long custId=Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
		LaoCustomerPo customer=new LaoCustomerPo();
		customer.setCustId(custId);
		/*SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");*/
		customer.setInDate(new Date());
		customer.setCustType("0");
		customer.setCustState("0");
		customer.setPsptTypeCode("1");
		customer.setPsptId("**");
		customer.setCustName("系统生成");
		customerDao.insert(customer);
		LaoCustomerVerifyPo verifyPo=(LaoCustomerVerifyPo)ConversionUtil.dto2po(dto, LaoCustomerVerifyPo.class);
		
		
		LaoCustPersonPo custPersonPo=new LaoCustPersonPo();
		Long custPersonId=Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
		custPersonPo.setCustId(custId);
		custPersonPo.setCustName("系统生成");
		custPersonPo.setPsptTypeCode("1");
		custPersonPo.setPsptId("**");
		custPsersonDao.insert(custPersonPo);
		
		return custId;
		
	}
	//查询企业客户运营商整体情况
	@Override
	public Map<String, Object> selectCorporate(Long channelCustId) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String, Object>> list = userExtDao.selectCorporateCount(channelCustId);
		map.put("list", list);
		if (list != null && list.size() > 0) {
			String[] data1 = new String[list.size()+1];
			@SuppressWarnings("unchecked")
			Map<String,Object>[] data2 = new Map[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> mapi = list.get(i);
				BigDecimal big = (BigDecimal) mapi.get("SUM");
				int sumi = big.intValue();
				String operatorsName = mapi.get("OPERATORSNAME").toString();
				data1[i] = operatorsName;
				Map<String,Object> mapData2 = new HashMap<String,Object>();
				mapData2.put("value", sumi);
				mapData2.put("name", operatorsName);
				data2[i] = mapData2;
			}
			data1[list.size()] = "";
			map.put("data1", data1);
			map.put("data2", data2);
		}
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<LaoCustomerDto> queryChannelCust(String sellType) {
		List<LaoCustomerPo> pos=daoExt.queryChannelCust(sellType);
		return ConversionUtil.poList2dtoList(pos, LaoCustomerDto.class);
	}

	@Override
	public Map<String, Object> queryPerPage(LaoCustomerDto dto,
	            Integer pageNo, Integer pageSize) {
	    Map<String, Object> map=miniCutomerService.queryPerPage(dto, pageNo, pageSize);
	    return map;
	}
	@Override
	public boolean saveStyle(String welcomeLanguage, String style, String pic,Long custId,String custName) {
		
		 LaoCustomerStyle laoCustomerStyle = new LaoCustomerStyle();
		 laoCustomerStyle.setCustId(custId);
		 laoCustomerStyle.setCustName(custName);
		 laoCustomerStyle.setCustStyle(style);
		 laoCustomerStyle.setParaName1(welcomeLanguage);
		 laoCustomerStyle.setCreatDate(new Date());
		 laoCustomerStyle.setCustLogo(pic);
		 int resultVal = customerStyle.insertSelective(laoCustomerStyle);
		 if (resultVal>0) {
			
			 return true;
		 }
		return false;
	}
	@Override
	public boolean saveAccount(LaoCustomerAccountDto accountDto) {
		
		//LaoCustomerAccount  po=null;
		//BeanMapper.copy(accountDto, po);
		
		return false;
	}
	
	/**
	 * 查询客户下级客户列表：
	 * 下级客户指该客户是由上级客户（parentId）发展来的客户。
	 * 
	 * @param custId 客户标识
	 * @param includeSelf 返回列表中是否包含自身，true包含自身，false不包含自身
	 * @return 下级客户（发展客户）列表
	 */
	@SuppressWarnings("unchecked")
	public List<LaoCustomerDto> queryChildCustList(Long custId, boolean includeSelf) {
		List<LaoCustomerPo> custList = dao.selectChildCustList(custId);
		if (CollectionUtils.isNotEmpty(custList)) {
			if (!includeSelf) {
				for (LaoCustomerPo po : custList) {
					if (po.getCustId().longValue() == custId.longValue()){
						custList.remove(po);
						break;
					}
				}
			}
		}
		return ConversionUtil.poList2dtoList(custList, LaoCustomerDto.class);
	}


}
