package com.urt.Ability.M2M;


import java.util.HashMap;
import java.util.Map;

	public class EncryptUtils {
	public static Map<String, String> genComMap(){
	    	Map<String,String> comMap=new HashMap<String,String>();
	    	comMap.put(ConstantsUntil.msgId, ConstantsUntil.msgIdVal);
	    	comMap.put(ConstantsUntil.version_id, ConstantsUntil.version_id_Val);
	    	comMap.put(ConstantsUntil.consumer, ConstantsUntil.consumerVal);
	    	comMap.put(ConstantsUntil.password, ConstantsUntil.passwordVal);
	    	comMap.put(ConstantsUntil.test_flag, ConstantsUntil.test_flag_val);
	    	comMap.put(ConstantsUntil.password, ConstantsUntil.passwordVal);
	    	return comMap;
	    }
	}
