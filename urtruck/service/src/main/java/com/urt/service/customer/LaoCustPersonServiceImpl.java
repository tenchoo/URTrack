package com.urt.service.customer;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.LaoCustPersonDto;
import com.urt.interfaces.customer.LaoCustPersonService;
import com.urt.mapper.LaoCustPersonPoMapper;
import com.urt.mapper.ext.LaoCustPersonPoExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoCustPersonPo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service("custPersonService")
public class LaoCustPersonServiceImpl implements LaoCustPersonService{
	
	  private static Logger logger = LoggerFactory.getLogger(LaoCustPersonServiceImpl.class);
	@Autowired
	private LaoCustPersonPoMapper custPersonDao;

	@Autowired
	private LaoCustPersonPoExtMapper custPersonPoExtDao;
	@Override
	public int deleteByPrimaryKey(Long custId) {
		// TODO Auto-generated method stub
		return custPersonDao.deleteByPrimaryKey(custId);
	}

	@Override
	public int insert(LaoCustPersonDto record) {
		// TODO Auto-generated method stub
		LaoCustPersonPo po=new LaoCustPersonPo();
		BeanMapper.copy(record, po);
		return custPersonDao.insert(po);
	}

	@Override
	public int insertSelective(LaoCustPersonDto record) {
		// TODO Auto-generated method stub
		LaoCustPersonPo po=new LaoCustPersonPo();
		BeanMapper.copy(record, po);
		return custPersonDao.insertSelective(po);
	}

	@Override
	public LaoCustPersonDto selectByPrimaryKey(Long custId) {
		// TODO Auto-generated method stub
		LaoCustPersonPo po=custPersonDao.selectByPrimaryKey(custId);
		LaoCustPersonDto dto=new LaoCustPersonDto();
		if(po!=null){
			BeanMapper.copy(po, dto);
		}
		return dto;
	}

	@Override
	public int updateByPrimaryKeySelective(LaoCustPersonDto record) {
		// TODO Auto-generated method stub
		LaoCustPersonPo po=new LaoCustPersonPo();
		BeanMapper.copy(record, po);
		return custPersonDao.updateByPrimaryKeySelective(po);
	}

	@Override
	public int updateByPrimaryKey(LaoCustPersonDto record) {
		// TODO Auto-generated method stub
		LaoCustPersonPo po=new LaoCustPersonPo();
		BeanMapper.copy(record, po);
		return custPersonDao.updateByPrimaryKey(po);
	}
	 @Override
	 public Map<String, Object> queryPage(LaoCustPersonDto dto, int pageNo, int pageSize) {

	   List<LaoCustPersonPo> custList = new ArrayList<LaoCustPersonPo>();
	   Page<LaoCustPersonPo> page = new Page<LaoCustPersonPo>();       
	   page.setPageNo(pageNo);
	   page.setPageSize(pageSize);
	   Map<String, Object> params = new HashMap<String, Object>();
	   params.put("param", (LaoCustPersonPo) ConversionUtil.dto2po(dto, LaoCustPersonPo.class));
	   page.setParams(params);
	   params.put("pid", dto.getCustId());
	        //custList = custPersonPoExtDao.queryPage(page);
	              
	        
	   Map<String, Object> resultMap = new HashMap<String, Object>();
	   resultMap.put("data", ConversionUtil.poList2dtoList(custList, LaoCustPersonDto.class));
	   resultMap.put("iTotalRecords", pageSize);//å½“å‰é¡µåŒ…å«çš„è®°å½•æ•°
	   resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//æ€»è®°å½•æ•° 
	   return resultMap;
	  }

	  @Override
	  public List<LaoCustPersonDto> queryCustName(String custName) {
	     List<LaoCustPersonPo> custList= custPersonPoExtDao.queryCustName(custName); 
	     List<LaoCustPersonDto> dtos=new ArrayList<LaoCustPersonDto>();
	     for(LaoCustPersonPo po:custList){
	       LaoCustPersonDto dto=new LaoCustPersonDto();
	       BeanMapper.copy(po, dto);
	       dtos.add(dto);
	      }
	      return dtos;
	 }

	    @Override
	    public List<LaoCustPersonDto> queryCustByIccid(String iccId) {
	        List<LaoCustPersonPo> custList= custPersonPoExtDao.queryCustByIccid(iccId);
	        List<LaoCustPersonDto> dtos=new ArrayList<LaoCustPersonDto>();
	        for(LaoCustPersonPo po:custList){
	            LaoCustPersonDto dto=new LaoCustPersonDto();
	            BeanMapper.copy(po, dto);
	            dtos.add(dto);
	        }
	        return dtos;
	    }

	    @Override
	    public List<LaoCustPersonDto> queryCustById(String idNum) {
	        List<LaoCustPersonPo> custList=custPersonPoExtDao.queryCustById(idNum);
	        List<LaoCustPersonDto> dtos=new ArrayList<LaoCustPersonDto>();
	        for(LaoCustPersonPo po:custList){
	            LaoCustPersonDto dto=new LaoCustPersonDto();
	            BeanMapper.copy(po, dto);
	            dtos.add(dto);
	        }
	        return dtos;
	    }

	    @Override
	    public int save(LaoCustPersonDto record) {
	        LaoCustPersonPo po=(LaoCustPersonPo)ConversionUtil.dto2po(record, LaoCustPersonPo.class);
	        custPersonDao.insertSelective(po);
	        return 0;
	    }
}
