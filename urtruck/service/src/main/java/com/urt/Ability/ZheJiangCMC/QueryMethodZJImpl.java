package com.urt.Ability.ZheJiangCMC;

import org.springframework.stereotype.Service;

import com.urt.Ability.EcCMCC.EcBaseMethodImpl;
import com.urt.interfaces.ZheJiangCMC.QueryMethodZJ;

@Service("queryMethodZJ")
public class QueryMethodZJImpl extends EcBaseMethodImpl implements QueryMethodZJ{
	private   String appid="SWT82AW";
	private static final String ecid="571019999571009572";
	private   String passwd="LXDD41";
	
	public  QueryMethodZJImpl(){
		super.appid=appid;
		super.passwd=passwd;
	}
	
}
