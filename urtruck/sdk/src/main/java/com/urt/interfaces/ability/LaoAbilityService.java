package com.urt.interfaces.ability;

import java.util.List;
import java.util.Map;

import com.urt.dto.LaoProvideServerDto;


public interface LaoAbilityService {

	Map<String, Object> queryPage(Integer pageNo,Integer pageSize,String custName);

	List<LaoProvideServerDto> queryPrivateServer(Long id);

	List<LaoProvideServerDto> getPrivideServer();

	Boolean updateServer(String serverIds, String custId);

	Boolean inSertIntoServer(String custId, String serverIds);

}
