package com.urt.interfaces.userIndex;

import java.util.Map;

public interface ChannelCustInfoOutlineService{
	//查询企业用户下的总链接数
	Map<String,Object> getInfoOutline(Long custId);
}

