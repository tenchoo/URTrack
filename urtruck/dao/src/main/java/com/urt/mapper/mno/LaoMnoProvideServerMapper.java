package com.urt.mapper.mno;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.urt.po.mno.LaoMnoProvideServer;
import com.urt.po.mno.LaoMnoProvideServerExample;

public interface LaoMnoProvideServerMapper {
    long countByExample(LaoMnoProvideServerExample example);

    int deleteByExample(LaoMnoProvideServerExample example);

    int deleteByPrimaryKey(Long serverId);

    int insert(LaoMnoProvideServer record);

    int insertSelective(LaoMnoProvideServer record);

    List<LaoMnoProvideServer> selectByExample(LaoMnoProvideServerExample example);

    LaoMnoProvideServer selectByPrimaryKey(Long serverId);

    int updateByExampleSelective(@Param("record") LaoMnoProvideServer record, @Param("example") LaoMnoProvideServerExample example);

    int updateByExample(@Param("record") LaoMnoProvideServer record, @Param("example") LaoMnoProvideServerExample example);

    int updateByPrimaryKeySelective(LaoMnoProvideServer record);

    int updateByPrimaryKey(LaoMnoProvideServer record);
}