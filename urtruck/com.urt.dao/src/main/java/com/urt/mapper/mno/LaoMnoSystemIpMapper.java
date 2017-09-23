package com.urt.mapper.mno;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.urt.po.mno.LaoMnoSystemIp;
import com.urt.po.mno.LaoMnoSystemIpExample;

public interface LaoMnoSystemIpMapper {
    long countByExample(LaoMnoSystemIpExample example);

    int deleteByExample(LaoMnoSystemIpExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LaoMnoSystemIp record);

    int insertSelective(LaoMnoSystemIp record);

    List<LaoMnoSystemIp> selectByExample(LaoMnoSystemIpExample example);

    LaoMnoSystemIp selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LaoMnoSystemIp record, @Param("example") LaoMnoSystemIpExample example);

    int updateByExample(@Param("record") LaoMnoSystemIp record, @Param("example") LaoMnoSystemIpExample example);

    int updateByPrimaryKeySelective(LaoMnoSystemIp record);

    int updateByPrimaryKey(LaoMnoSystemIp record);
}