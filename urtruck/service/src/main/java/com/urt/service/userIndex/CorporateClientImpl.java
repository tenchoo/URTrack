package com.urt.service.userIndex;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.LaoCustomerNoticeDto;
import com.urt.interfaces.userIndex.CorporateClientService;
import com.urt.mapper.ext.LaoCustomerNoticePoExtMapper;
import com.urt.miniService.MiniCustNoticeServiceImpl;
import com.urt.service.dmp.DMPServiceImpl;

@Service("corporateService")
@Transactional(propagation = Propagation.REQUIRED)
public class CorporateClientImpl implements CorporateClientService{
	Logger log = Logger.getLogger(DMPServiceImpl.class);
	@Autowired
	private LaoCustomerNoticePoExtMapper noticePoExtMapper;
	@Autowired
	private MiniCustNoticeServiceImpl minCustNoticeImpl;
	@Override
	public List<Map<String, Object>> selectNotice(long custId){
		List<Map<String, Object>> map = noticePoExtMapper.selectNotice(custId);
		return map;
		
	}
	@Override
	public List<Map<String, Object>> selectNoticeAll(long custId) {
		List<Map<String, Object>> map = noticePoExtMapper.selectNoticeAll(custId);
		return map;
	}
	@Override
	public Map<String, Object> queryPage(LaoCustomerNoticeDto dto, int pageNo,
			int pageSize) {
		Map<String, Object> map = minCustNoticeImpl.queryPage(dto, pageNo,
				pageSize);
		return map;
	}
	@Override
	public Map<String, Object> selectNoticeContent(long noticeId) {
			noticePoExtMapper.updateClickNum(noticeId);
			Map<String, Object> map = noticePoExtMapper.selectNoticeContent(noticeId);
		return map;
	}
}
