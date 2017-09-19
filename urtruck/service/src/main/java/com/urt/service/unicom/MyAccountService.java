package com.urt.service.unicom;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lenovo.pay.utils.DateUtils;
import com.urt.mapper.ChargeOrderMapper;
import com.urt.po.ChargeOrder;

@Service("myAccountService")
public class MyAccountService {
	@Autowired
	private ChargeOrderMapper chargeOrderDao;
	Logger logger = Logger.getLogger(getClass());
	
	
}
