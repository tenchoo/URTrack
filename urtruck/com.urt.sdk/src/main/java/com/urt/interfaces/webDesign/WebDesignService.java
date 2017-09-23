package com.urt.interfaces.webDesign;

import java.util.List;
import java.util.Map;

import com.urt.dto.LaoWebDesignDto;

public interface WebDesignService {

	//插入数据
	public String insert(long custId, String color, String image, String statusCode, String webUrl, String webContent, String value1, String value2);
	
	//通过Key更新数据
	public int updateByDesignId(LaoWebDesignDto webDesignDto);
	
	//通过CustId查询所有的定制信息
	public List<Map<String,Object>> selectAllByCustId(String custId);
	
    //查询所有的custId
    public List<Map<String, Object>> queryAllCustId();
    
    //分页查询所有记录
    public List<Map<String, Object>> queryPage(Map<String, Object> param);
    
    //通过designId查询记录
    public LaoWebDesignDto getWebDesignDtoByDesignId(Long designId);
	
    //获取所有的记录数
    public Long getAllCount();
    
    //生成sign签名
    public String getSign(String custId);
    
    //通过custId查询CustName
    public String getCustNameByCustId(Long custId) ;
}
