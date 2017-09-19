package com.urt.mapper.mno;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.urt.po.mno.LaoMnoAccessLog;
import com.urt.po.mno.LaoMnoAccessLogExample;

public interface LaoMnoAccessLogMapper {
    long countByExample(LaoMnoAccessLogExample example);

    int deleteByExample(LaoMnoAccessLogExample example);

    int deleteByPrimaryKey(Long logId);

    int insert(LaoMnoAccessLog record);

    int insertSelective(LaoMnoAccessLog record);

    List<LaoMnoAccessLog> selectByExampleWithBLOBs(LaoMnoAccessLogExample example);

    List<LaoMnoAccessLog> selectByExample(LaoMnoAccessLogExample example);

    LaoMnoAccessLog selectByPrimaryKey(Long logId);

    int updateByExampleSelective(@Param("record") LaoMnoAccessLog record, @Param("example") LaoMnoAccessLogExample example);

    int updateByExampleWithBLOBs(@Param("record") LaoMnoAccessLog record, @Param("example") LaoMnoAccessLogExample example);

    int updateByExample(@Param("record") LaoMnoAccessLog record, @Param("example") LaoMnoAccessLogExample example);

    int updateByPrimaryKeySelective(LaoMnoAccessLog record);

    int updateByPrimaryKeyWithBLOBs(LaoMnoAccessLog record);

    int updateByPrimaryKey(LaoMnoAccessLog record);
}