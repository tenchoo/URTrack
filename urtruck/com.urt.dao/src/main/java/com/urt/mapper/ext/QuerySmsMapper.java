package com.urt.mapper.ext;

import java.util.List;

import com.urt.po.LaoQuerySms;

public interface QuerySmsMapper {
	List<LaoQuerySms> selectSms(String custId,String startDate,String endDate);
}
