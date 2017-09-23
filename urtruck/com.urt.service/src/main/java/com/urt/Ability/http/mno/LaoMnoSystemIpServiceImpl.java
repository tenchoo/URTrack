package com.urt.Ability.http.mno;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.dto.http.mno.LaoMnoSystemIpDto;
import com.urt.interfaces.http.mno.LaoMnoSystemIpService;
import com.urt.mapper.mno.LaoMnoSystemIpMapper;

import java.util.List;

@Service("laoMnoSystemIpService")
public class LaoMnoSystemIpServiceImpl implements LaoMnoSystemIpService {

	protected static final Logger logger = Logger.getLogger(LaoMnoSystemIpServiceImpl.class);

	@Autowired
	LaoMnoSystemIpMapper laoMnoSystemIpMapper;


	@Override
	public List<LaoMnoSystemIpDto> selectBySystemId(String systemId) {
		return null;
	}
}
