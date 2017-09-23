package com.urt.service.unicom;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urt.mapper.IccidLibraryMapper;
import com.urt.po.IccidLibrary;

@Service("iccidLibraryService")
@Transactional
public class IccidLibraryService {

	Logger log = Logger.getLogger(getClass());
	@Autowired
	private IccidLibraryMapper iccidLibraryDAO;
	
	
	public IccidLibrary findIccidLibraryByIccid(String iccid){
		log.info("findIccidLibraryByIccid>>>>>>>>>>>>>>>>>>>>iccid:"+iccid);
		return iccidLibraryDAO.doQueryFirst(iccid);
	}
	
}
