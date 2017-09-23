package com.urt.interfaces.http;
import java.util.Map;

import com.urt.dto.LaoPeripheralSysAccessLogDto;
import com.urt.dto.http.HttpServerInfo;
import com.urt.dto.http.LaoCustConfigDTO;
public interface ServerCheckService {
	
	public HttpServerInfo check(Map<String,String> reqInfo);
	public void loggerToDb(Map<String,String> requestInfo,Map<String,Object> rspInfo);
	
	public void savaLogerToDb(LaoPeripheralSysAccessLogDto record);
	
	public LaoCustConfigDTO custConfigChenk(String custId);
}
