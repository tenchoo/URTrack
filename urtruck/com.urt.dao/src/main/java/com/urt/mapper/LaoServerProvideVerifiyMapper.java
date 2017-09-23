package com.urt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.LaoServerProvideVerifiy;

public interface LaoServerProvideVerifiyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LaoServerProvideVerifiy record);

    int insertSelective(LaoServerProvideVerifiy record);

    LaoServerProvideVerifiy selectByPrimaryKey(Long id);
    
    List<LaoServerProvideVerifiy> selectByRandomId(@Param("randomid")String randomid,@Param("custid")String custid);

    int updateByPrimaryKeySelective(LaoServerProvideVerifiy record);

    int updateByPrimaryKey(LaoServerProvideVerifiy record);
}