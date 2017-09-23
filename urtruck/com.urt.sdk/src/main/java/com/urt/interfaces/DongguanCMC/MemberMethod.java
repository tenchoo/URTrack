package com.urt.interfaces.DongguanCMC;

public interface MemberMethod {
	// 通过集团编号查询客户基本信息的API
		public String infoQuery(String msisdn);

		// 成员剩余流量信息实时查询API
		public String dataplanleftQuery(String msisdn);

		// 成员日流量信息查询API
		// dateStr 月份 String 6 YES 格式:yyyyMM
		public String dailydatausageQuery(String msisdn, String dateStr);

		// 成员日流量信息查询API
		// startDateStr 开始月份 String 6 YES 格式:yyyyMM
		// endDateStr 结束月份 String 6 YES 格式:yyyyMM
		public String monthlydatausageQuery(String msisdn, String startDateStr, String endDateStr);

		// 成员月费用详单查询
		public String monthlybilldetailQuery(String msisdn, String dateStr);

		// 成员月费用详单查询
		public String monthlybillQuery(String msisdn, String startDateStr, String endDateStr);

		// 成员告警信息查询API
		public String alarmQuery(String msisdn, String dateStr);

		// 成员号码流量信息实时查询API
		public String datausageQuery(String msisdn);

		// 成员余额实时查询
		public String balanceQuery(String msisdn);

		// 成员套餐已订购信息实时查询
		public String packageQuery(String msisdn);

		// 成员业务状态实时状态查询
		public String statusQuery(String msisdn);

		// 终端停开机状态实时查询
		public String terminalstatusQuery(String msisdn);

		public String simstateChangeOpen(String msisdn);

		public String simstateChangeClose(String msisdn);

		// 号码停开机API
		public String simstateChange(String msisdn, String ncode);
		
		//GPRS停开API
		public String dataservicestateChange(String msisdn, String optType); 
		
		//Apn切换API
		public String apnChange(String msisdn, String prdOrdNum1, String prdOrdNum2);
		
		//套餐修改
		public String planChange(String msisdn, String newPackage, String oldPackage);
		
		//套餐叠加
		public String additionplanChange(String msisdn, String newPackage);
}
