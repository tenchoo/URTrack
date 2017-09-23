package com.urt.mapper.mno;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.urt.po.mno.LaoMnoSystemConfig;
import com.urt.po.mno.LaoMnoSystemConfigExample;

public interface LaoMnoSystemConfigMapper {
    long countByExample(LaoMnoSystemConfigExample example);

    int deleteByExample(LaoMnoSystemConfigExample example);

    int deleteByPrimaryKey(Long configId);

    int insert(LaoMnoSystemConfig record);

    int insertSelective(LaoMnoSystemConfig record);

    List<LaoMnoSystemConfig> selectByExample(LaoMnoSystemConfigExample example);

    LaoMnoSystemConfig selectByPrimaryKey(Long configId);

    int updateByExampleSelective(@Param("record") LaoMnoSystemConfig record, @Param("example") LaoMnoSystemConfigExample example);

    int updateByExample(@Param("record") LaoMnoSystemConfig record, @Param("example") LaoMnoSystemConfigExample example);

    int updateByPrimaryKeySelective(LaoMnoSystemConfig record);

    int updateByPrimaryKey(LaoMnoSystemConfig record);
}