package com.urt.interfaces.grpnet;

import java.util.List;
import java.util.Map;

import com.urt.dto.grpnet.GrpnetImpbillDto;

public interface GrpnetImpbillService {
	// GrpnetImpbillService 批量插入数据
	int batchInsert(List<GrpnetImpbillDto> list);
	// DItemService查询全部数据,返回MAP<name,id>
    Map<String,Integer> selectAllMapDtem();
    // 发送消息给kafka
    public void sendUserMsg(List<List<GrpnetImpbillDto>> list);
}
