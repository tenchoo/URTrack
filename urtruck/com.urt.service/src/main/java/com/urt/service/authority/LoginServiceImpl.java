package com.urt.service.authority;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.cache.RedisCacheUtil;
import com.urt.cache.RedisKey;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoSsNavigationDto;
import com.urt.dto.LaoSsResourceDto;
import com.urt.interfaces.authority.LoginService;
import com.urt.miniService.authority.MiniSsNavigationServiceImpl;
import com.urt.miniService.authority.MiniSsResourceServiceImpl;
import com.urt.miniService.authority.MiniSsUserServiceImpl;
import com.urt.modules.mapper.JsonMapper;


/**
 * 类说明：用户登录service实现
 * 
 * @author cuichao
 * @date 2016年6月20日 下午7:37:00
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
	JsonMapper jsonMapper = new JsonMapper();
	@Autowired
	private MiniSsUserServiceImpl miniSsUserServiceImpl;
	@Autowired
	private MiniSsNavigationServiceImpl miniSsNavigationServiceImpl;
	@Autowired
	private MiniSsResourceServiceImpl miniSsResourceServiceImpl;
	@Autowired
	private RedisCacheUtil redisCacheUtil;

	/**
	 * 功能描述：根据用户登录名和密码验证用户，验证通过后获得用户菜单集和资源集
	 * 
	 * @author cuichao
	 * @date 2016年6月21日 上午11:12:59
	 * @param @param
	 *            loginName
	 * @param @return
	 */
	@Override
	public LaoSsAccountDto getUserInfo(String loginName) {
		LaoSsAccountDto laoSsAccountDto = null;
		try {
			//tfFSsUserDto = (TfFSsUserDto) redisCacheUtil.get(RedisKey.SSUSER_LOGIN_NAME, loginName);
		} catch (Exception e) {
			logger.error("*****************在缓存中获取loginName===" + loginName + "的记录时出错！",e);
		}
		if(laoSsAccountDto == null){
			laoSsAccountDto = miniSsUserServiceImpl.getByLoginName(loginName);
			if (laoSsAccountDto == null) {
				return null;
			}
			// 菜单列表
			List<LaoSsNavigationDto> navigationList = miniSsNavigationServiceImpl.getListByUserId(laoSsAccountDto.getAcconutId());
			// 资源列表
			List<LaoSsResourceDto> resourceList = miniSsResourceServiceImpl.getListByUserId(laoSsAccountDto.getAcconutId());
			laoSsAccountDto.setNavigations(navigationList);
			laoSsAccountDto.setResources(resourceList);
			try {
				//redisCacheUtil.save(RedisKey.SSUSER_LOGIN_NAME, loginName,jsonMapper.toJson(tfFSsUserDto));
			} catch (Exception e) {
				logger.error("*****************向缓存中插入userId===" + laoSsAccountDto.getAcconutId() + "的记录时出错！",e);
			}
		}
		return laoSsAccountDto;
	}

}
