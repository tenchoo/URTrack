package com.urt.Ability.JiangSuCMC;


import org.springframework.stereotype.Service;

import com.urt.Ability.EcCMCC.EcBaseMethodImpl;
import com.urt.interfaces.JiangSuCMC.QueryMethodJS;

@Service("queryMethodJS")
public class QueryMethodJSImpl extends EcBaseMethodImpl implements QueryMethodJS{
	private  String appid="";
	private  String passwd="";
	public QueryMethodJSImpl(){
		super.appid=appid;
		super.passwd=passwd;
	}
	
}
