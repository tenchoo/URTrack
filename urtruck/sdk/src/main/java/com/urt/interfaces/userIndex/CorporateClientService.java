package com.urt.interfaces.userIndex;

import java.util.List;
import java.util.Map;

import com.urt.dto.LaoCustomerNoticeDto;

public interface CorporateClientService {
	Map<String, Object> queryPage(LaoCustomerNoticeDto dto, int pageNo,
			int pageSize);
	//根据custId查询指定条数通知
	List<Map<String, Object>> selectNotice(long custId);
	//查询所有条数通知
	List<Map<String, Object>> selectNoticeAll(long custId);
	//根据noticeId查询重要通知内容
	Map<String, Object> selectNoticeContent(long noticeId);
}
