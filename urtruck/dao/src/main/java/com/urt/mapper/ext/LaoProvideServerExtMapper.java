package com.urt.mapper.ext;

import java.util.List;

import com.urt.po.LaoProvideServer;

public interface LaoProvideServerExtMapper {

	List<LaoProvideServer> queryPrivateServerByCustId(Long custId);

	List<LaoProvideServer> queryList();

}
