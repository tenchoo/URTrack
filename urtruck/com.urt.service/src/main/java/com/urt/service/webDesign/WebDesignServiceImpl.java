package com.urt.service.webDesign;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.Ability.http.util.TokenUtils;
import com.urt.dto.LaoWebDesignDto;
import com.urt.interfaces.webDesign.WebDesignService;
import com.urt.mapper.LaoCustomerPoMapper;
import com.urt.mapper.LaoKeyManagementMapper;
import com.urt.mapper.LaoWebDesignMapper;
import com.urt.mapper.ext.LaoWebDesignExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoCustomerPo;
import com.urt.po.LaoKeyManagement;
import com.urt.po.LaoWebDesign;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

@Service("webDesignService")
@Transactional(propagation = Propagation.REQUIRED)
public class WebDesignServiceImpl implements WebDesignService {

	@Autowired
	private LaoWebDesignMapper webDesignMapper;
	
	@Autowired
	private LaoWebDesignExtMapper webDesignExtMapper;
	
	@Autowired
	private LaoCustomerPoMapper laoCustomerPoMapper;
	
	@Autowired
	private LaoKeyManagementMapper laoKeyManagementExtMapper;
	
	@Override
	public String insert(long custId, String color,
			String image, String statusCode, String webUrl,
			String webContent, String value1, String value2) {
		LaoWebDesign laoWebDesign = new LaoWebDesign();
		String designId = null;
		designId = ZkGenerateSeq.getIdSeq(SeqID.BATCHID);
		System.out.println("WebDesignServiceImpl.insert()+++++++designId: " + designId);
		laoWebDesign.setDesignId(Long.parseLong(designId));
		laoWebDesign.setCustId(custId);
		laoWebDesign.setCustName(getCustNameByCustId(custId));
		laoWebDesign.setColor(color);
		laoWebDesign.setImage(image);
		String designDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		laoWebDesign.setDesignDate(designDate);
		System.out.println("WebDesignServiceImpl.insert()+++++++designDate: " + designDate);
		laoWebDesign.setStatusCode(statusCode);
		laoWebDesign.setWebUrl(webUrl);
		laoWebDesign.setWebContent(webContent);
		laoWebDesign.setValue1(value1);
		laoWebDesign.setValue2(value2);
		int b = webDesignMapper.insert(laoWebDesign);
		if(b > 0){
			return designId;
		}else{
			return null;
		}
	}

	@Override
	public int updateByDesignId(LaoWebDesignDto webDesignDto) {
		LaoWebDesign webDesign = null;
		if(webDesignDto != null && webDesignDto.getCustId() != null && !"".equals(webDesignDto.getCustId())){
			webDesignDto.setCustName(getCustNameByCustId(webDesignDto.getCustId()));
			webDesign = new LaoWebDesign();
			BeanMapper.copy(webDesignDto, webDesign);
		}
		return webDesignMapper.updateByPrimaryKey(webDesign);
	}

	@Override
	public List<Map<String, Object>> selectAllByCustId(String custId) {
		Long tmpCustId = null;
		if(custId != null && !"".equals(custId)){
			tmpCustId = Long.parseLong(custId);
			return webDesignExtMapper.selectAllByCustId(tmpCustId);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> queryAllCustId() {
		
		return webDesignExtMapper.queryAllCustId();
	}

	@Override
	public List<Map<String, Object>> queryPage(Map<String, Object> param) {
		
		return webDesignExtMapper.queryPage(param);
	}

	@Override
	public String getCustNameByCustId(Long custId) {
		if(custId != null && !"".equals(custId)){
			LaoCustomerPo laoCustomer = laoCustomerPoMapper.selectByPrimaryKey(custId);
			return laoCustomer.getCustName();
		}else{
			return null;
		}
	}

	@Override
	public LaoWebDesignDto getWebDesignDtoByDesignId(Long designId) {
		if(designId != null){
			LaoWebDesign webDesign = webDesignMapper.selectByPrimaryKey(designId);
			LaoWebDesignDto webDesignDto = null;
			if(webDesign != null){
				webDesignDto = new LaoWebDesignDto();
				BeanMapper.copy(webDesign, webDesignDto);
			}
			return webDesignDto;
		}else{
			return null;
		}
	}

	@Override
	public Long getAllCount() {
		
		return webDesignExtMapper.getAllCount();
	}

	@Override
	public String getSign(String custId) {
		LaoKeyManagement laoKey = laoKeyManagementExtMapper.selectByCustId(custId);
		Map<String, String> paramValues = new HashMap<String,String>();
		paramValues.put("custId", custId);
		paramValues.put("iccid", null);
		paramValues.put("logo", "2");
		String sign = TokenUtils.md5Sign(paramValues,null,laoKey.getAuthKey());
		return sign;
	}

}
