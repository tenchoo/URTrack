package com.urt.service.grpnet;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.grpnet.BatchFtpImportDto;
import com.urt.interfaces.grpnet.BatchFtpImportService;
import com.urt.mapper.BatchFtpImportMapper;
import com.urt.msgProducter.trade.SendMsgByColonyProducer;
import com.urt.po.BatchFtpImport;

@Service("batchFtpImportService")
@Transactional(propagation = Propagation.REQUIRED)
public class BatchFtpImportServiceImpl implements BatchFtpImportService {

	@Autowired
	private BatchFtpImportMapper batchFtpImportDao;
	@Autowired
	private SendMsgByColonyProducer sendMessageProducer;
	
	@Override
	public BatchFtpImportDto selectByPrimaryKey(Short tradeTypeCode) {
		BatchFtpImport record =batchFtpImportDao.selectByPrimaryKey(tradeTypeCode);
		BatchFtpImportDto recordNew = new BatchFtpImportDto();
		try {
			BeanUtils.copyProperties(recordNew, record);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return recordNew;
	}

	@Override
	public void sendUserMsg(List<Map<String, Object>> listMap) {
		
		sendMessageProducer.sendMessage(listMap);
	}

}
