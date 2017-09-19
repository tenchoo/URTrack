package com.urt.service.dmp;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.interfaces.dmp.DMPUserService;
@Service("dmpUserService")
@Transactional(propagation = Propagation.REQUIRED)
public class DMPUserServiceImpl implements DMPUserService {
	Logger log = Logger.getLogger(DMPUserServiceImpl.class);
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		
		
	}
}
