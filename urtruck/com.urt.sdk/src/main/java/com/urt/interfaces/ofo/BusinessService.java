package com.urt.interfaces.ofo;

import java.util.List;
import java.util.Map;

import com.urt.dto.LaoFtpfileCollectDto;
import com.urt.dto.Goods.ServiceStatusDto;

public interface BusinessService {

	List<Map<String, Object>> queryCardInfo(String fileID,String exportVal);

	Map<String, Object> queryPage(int pageNo, int pageSize,String fileName);

	Boolean updateIccid(String fileId, String selectVal);

	List<LaoFtpfileCollectDto> getFile();

	List<LaoFtpfileCollectDto> getTypeCodeByFileId(String fileId);
	boolean dataInsert();

	void emailNotify();
	
	public void  ftpFileUpload();
	

	Map<String, Object> batchupdate(List<Map<String, Object>> list, String fileId, String typecodeId, String resultId);

	

}
