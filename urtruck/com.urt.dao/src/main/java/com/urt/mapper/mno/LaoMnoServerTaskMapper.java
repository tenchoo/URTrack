package com.urt.mapper.mno;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.urt.po.mno.LaoMnoServerTask;
import com.urt.po.mno.LaoMnoServerTaskExample;

public interface LaoMnoServerTaskMapper {
    long countByExample(LaoMnoServerTaskExample example);

    int deleteByExample(LaoMnoServerTaskExample example);

    int deleteByPrimaryKey(Long taskId);

    int insert(LaoMnoServerTask record);

    int insertSelective(LaoMnoServerTask record);

    List<LaoMnoServerTask> selectByExample(LaoMnoServerTaskExample example);

    LaoMnoServerTask selectByPrimaryKey(Long taskId);

    int updateByExampleSelective(@Param("record") LaoMnoServerTask record, @Param("example") LaoMnoServerTaskExample example);

    int updateByExample(@Param("record") LaoMnoServerTask record, @Param("example") LaoMnoServerTaskExample example);

    int updateByPrimaryKeySelective(LaoMnoServerTask record);

    int updateByPrimaryKey(LaoMnoServerTask record);
}