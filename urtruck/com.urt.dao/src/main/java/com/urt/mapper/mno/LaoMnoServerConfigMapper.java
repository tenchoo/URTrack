package com.urt.mapper.mno;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.urt.po.mno.LaoMnoServerConfig;
import com.urt.po.mno.LaoMnoServerConfigExample;

public interface LaoMnoServerConfigMapper {
    long countByExample(LaoMnoServerConfigExample example);

    int deleteByExample(LaoMnoServerConfigExample example);

    int deleteByPrimaryKey(Long configId);

    int insert(LaoMnoServerConfig record);

    int insertSelective(LaoMnoServerConfig record);

    List<LaoMnoServerConfig> selectByExample(LaoMnoServerConfigExample example);

    LaoMnoServerConfig selectByPrimaryKey(Long configId);

    int updateByExampleSelective(@Param("record") LaoMnoServerConfig record, @Param("example") LaoMnoServerConfigExample example);

    int updateByExample(@Param("record") LaoMnoServerConfig record, @Param("example") LaoMnoServerConfigExample example);

    int updateByPrimaryKeySelective(LaoMnoServerConfig record);

    int updateByPrimaryKey(LaoMnoServerConfig record);
}