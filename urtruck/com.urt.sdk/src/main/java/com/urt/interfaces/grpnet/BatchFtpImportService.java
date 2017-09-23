package com.urt.interfaces.grpnet;

import java.util.List;
import java.util.Map;

import com.urt.dto.grpnet.BatchFtpImportDto;


/**
 * FTP取文件导入账单
 * 
 * @author Lingfei 
 * @date 2016年9月18日
 */
public interface BatchFtpImportService {
	//根据tradeTypeCode 查询
	BatchFtpImportDto selectByPrimaryKey(Short tradeTypeCode);

	// 发送消息给卡不卡
	void sendUserMsg(List<Map<String, Object>> listMap);
}
