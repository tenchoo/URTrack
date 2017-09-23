package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import com.urt.po.LaoFtpfileInfo;

public interface LaoFtpFileInfoExtMapper {

	

	List<Map<String, Object>> queryBytypeCodeAndfileName(LaoFtpfileInfo info);

	int updateIccid(Map<String, Object> param);

	int batchInsert(List<LaoFtpfileInfo> infoList);

	int batchUpdate(Map<String, Object> map);

}
