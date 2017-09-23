package com.urt.service.userIndex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.interfaces.userIndex.ChannelCustAlmInfoService;
import com.urt.interfaces.userIndex.ChannelCustInfoOutlineService;
import com.urt.interfaces.userIndex.CorporateClientService;
import com.urt.mapper.ext.LaoAlmRuleLogPoExtMapper;
import com.urt.mapper.ext.LaoCustomerNoticePoExtMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.service.dmp.DMPServiceImpl;

@Service("almInfoService")
@Transactional(propagation = Propagation.REQUIRED)
public class ChannelCustAlmInfoImpl implements ChannelCustAlmInfoService{
	Logger log = Logger.getLogger(ChannelCustAlmInfoImpl.class);

	@Autowired
	private LaoAlmRuleLogPoExtMapper almRuleLogPoExtMapper;

	@Override
	public List<Map<String, Object>> selectAlmInfo(long custId) {
		return almRuleLogPoExtMapper.selectAlmInfo(custId);
	}

	@Override
	public List<Map<String, Object>> selectAlmInfoAll(long custId) {
		return almRuleLogPoExtMapper.selectAlmInfoAll(custId);
	}
	

}
