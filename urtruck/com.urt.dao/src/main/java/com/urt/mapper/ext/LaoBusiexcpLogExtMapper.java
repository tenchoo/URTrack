package com.urt.mapper.ext;

import java.util.List;

import com.urt.common.util.Page;
import com.urt.po.LaoBusiexcpLog;

public interface LaoBusiexcpLogExtMapper {

	//查询自动处理exception
    public List<LaoBusiexcpLog> autoResExceptions();
    //查询手动处理exception
    public List<LaoBusiexcpLog> handResExceptions(Page<LaoBusiexcpLog> page);

}