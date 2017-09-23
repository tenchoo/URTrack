package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import com.urt.po.LaoFtpfileCollect;

public interface LaoFtpfileCollectExtMapper {
 
	Integer countTotal(Map<String, Object> param);

	List<Map<String, Object>> queryPage(Map<String, Object> param);

	int updateCollect(LaoFtpfileCollect ftpfileCollect);

	List<LaoFtpfileCollect> selectAll();

	List<LaoFtpfileCollect> selectTypecodeByfileId(String fileId);
}
