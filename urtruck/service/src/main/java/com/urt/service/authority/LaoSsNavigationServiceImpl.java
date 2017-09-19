package com.urt.service.authority;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.LaoSsNavigationDto;
import com.urt.interfaces.authority.LaoSsNavigationService;
import com.urt.miniService.authority.MiniSsNavigationServiceImpl;


/**
 * 功能描述：菜单接口实现
 * @author zhaoxy9
 * @date 2016年6月22日
 */
@Service("laoSsNavigationService")
@Transactional(propagation=Propagation.REQUIRED)
public class LaoSsNavigationServiceImpl implements LaoSsNavigationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LaoSsNavigationServiceImpl.class);
	@Autowired
	private MiniSsNavigationServiceImpl miniSsNavigationServiceImpl;
	/**
	 * 
	 */
	@Override
	public List<LaoSsNavigationDto> queryList() {
		List<LaoSsNavigationDto> tfFSsNavigationDtos=miniSsNavigationServiceImpl.queryList();
		return tfFSsNavigationDtos;
	}
	
	
}
