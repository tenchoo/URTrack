package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.IccidLib;

public interface IccidLibExtMapper {
	//批量导入方法
    int batchInsert(List<IccidLib> iccidList);
    
    //根据iccid查询
    IccidLib selectByIccid(String iccid);
    
    //查询list
    List<IccidLib> doQueryList(@Param("custid")String custid,@Param("attribute1")String attribute1, @Param("value1")String value1,@Param("attribute2")String attribute2, @Param("value2")String value2,@Param("startIccid")String startIccid, @Param("endIccid") String endIccid, @Param("startRow")int startRow,@Param("endRow") int endRow);
    
    int countAmount(@Param("custid")String custid,@Param("attribute1")String attribute1, @Param("value1")String value1,@Param("attribute2")String attribute2, @Param("value2")String value2,@Param("startIccid")String startIccid, @Param("endIccid") String endIccid);
    
  //根据iccid查询
    void updateByIccid(IccidLib iccid);
    
    //查询总量
    int getCount();
    
    //分页查询
    List<Map<String, Object>> queryCardInfo(Map<String, Object> param);
    
    //验证用户是否可以订购这个产品
    IccidLib ifOrderProduct(@Param("iccid")String iccid, @Param("goodsId")String goodsId);
    
    /*List<String> queryIccidByCustId(String custId);*/
    
	List<IccidLib> selectDetailByPage(Page<IccidLib> iccidLibExtpage);

	Map<String,String> selectOneDetailByBatchId(String batchId);
	
	List<Map<String,Object>> selectDetailByBatchId(String batchId);
	
	 //根据iccid 或者mssion查询
   IccidLib selectByMap(Map<String,Object> param);
   
	int batchInsertTSP(List<IccidLib> list);
  
   List<IccidLib> selectByListIccid(List<String> list);
   
   List<IccidLib> selectByListMsison(List<String> list);
   
   int updateByIccidSelective(List<IccidLib> iccidLib);
   
   int updateByMsisonSelective(List<IccidLib> iccidLib);

   List<IccidLib> selectDetailByPageTSP(Page<IccidLib> iccidLibExtpage);

   List<IccidLib> selectByIccidStrArry(String iccids);
}