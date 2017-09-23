package com.urt.Ability.BeiJingCMC;

import org.springframework.stereotype.Service;

import com.urt.Ability.EcCMCC.EcBaseMethodImpl;
import com.urt.interfaces.BeiJingCMC.QueryMethodBJ;

@Service("queryMethodBJ")
public class QueryMethodBJImpl extends EcBaseMethodImpl implements QueryMethodBJ{
    
	private String appid="";
	private String passwd="";
	
	public QueryMethodBJImpl(){
		super.appid=appid;
		super.passwd=passwd;
	}
}
