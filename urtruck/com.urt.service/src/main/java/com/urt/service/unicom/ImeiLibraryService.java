package com.urt.service.unicom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urt.mapper.ImeiLibraryMapper;
import com.urt.po.ImeiLibrary;

@Service("imeiLibraryService")
@Transactional
public class ImeiLibraryService {

	@Autowired
	private ImeiLibraryMapper imeiLibraryDAO;
	
	
	public ImeiLibrary findImeiLibraryByIccid(String iccid){
		
		return imeiLibraryDAO.doQueryFirst(iccid);
	}
	
}
