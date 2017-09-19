package com.urt.Ability.JiangSuCMC;

import com.urt.utils.HttpClientUtil;

public class ManageMethodJSImpl {
     
	//物联网单卡实时流量
	public  String sigeCardQueryInfo(){
		String httpUrl="";
		String xml="<?xml version='1.0' encoding='UTF-8'?>"
				+"<operation_in>"+
				     "<process_code>"+"OPEN_QRYINTERNETUSERGPRS"+"</process_code>"+
				     "<app_id>"+"109000000066"+"</app_id>"+
				     "<access_token>"+"ABCDE12345ABCDE12409"+"</access_token>"+ 
				     "<sign>"+"</sign>"+
				     "<verify_code>"+"</verify_code>"+ 
				     "<req_type>"+01+"</req_type>"+ 
				     "<terminal_id>"+"</terminal_id>" 
				     +"<accept_seq>"+"</accept_seq>"+
				     "<req_time>"+"20170213154229"+"</req_time>"+ 
				     "<req_seq>"+"0_9834345"+"</req_seq>"
				     +"<content>"+"<service_number>"+"1064844744450"+"</service_number>"+ 
				       "<cycle>"+201704+"</cycle>"+"</content>"+
				  "</operation_in>";
		//HttpClientUtil.getInstance().sendHttpPost(httpUrl, params);
		
		System.out.println(xml);
		
		return null;
	}
	public static void main(String[] args) {
		 String xml="<?xml version='1.0' encoding='UTF-8'?>"
					+"<operation_in>"+
					     "<process_code>"+"OPEN_QRYINTERNETUSERGPRS"+"</process_code>"+
					     "<app_id>"+"109000000066"+"</app_id>"+
					     "<access_token>"+"ABCDE12345ABCDE12409"+"</access_token>"+ 
					     "<sign>"+"</sign>"+
					     "<verify_code>"+"</verify_code>"+ 
					     "<req_type>"+01+"</req_type>"+ 
					     "<terminal_id>"+"</terminal_id>" 
					     +"<accept_seq>"+"</accept_seq>"+
					     "<req_time>"+"20170213154229"+"</req_time>"+ 
					     "<req_seq>"+"0_9834345"+"</req_seq>"
					     +"<content>"+"<service_number>"+"1064844744450"+"</service_number>"+ 
					       "<cycle>"+201704+"</cycle>"+"</content>"+
					  "</operation_in>";
//					HttpClientUtil.getInstance().sendHttpPost(httpUrl, params);
					
					System.out.println(xml);
	}
}
