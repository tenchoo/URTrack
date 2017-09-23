package com.urt.interfaces.DongguanCMC;

public interface GroupMethod {
	// 集团信息查询API
	public String infoQuery(String ecCode);

	// 集团成员批量停机告警清单查询API
	public String dailyalarmQuery(String ecCode, String dateStr, Integer pageNo);

	// 集团成员批量流量告警清单查询
	public String dailydatausagealarmQuery(String ecCode, String dateStr, Integer pageNo);

	// 集团成员批量日流量查询
	public String dailydatausageQuery(String ecCode, String dateStr, Integer pageNo);

	// 集团成员批量月流量查询
	public String monthlydatausageQuery(String ecCode, String month, Integer pageNo);

	// 集团成员批量费用查询
	public String memberbillQuery(String ecCode, String month, Integer pageNo);

	// 集团总费用查询（历史）
	public String billQuery(String ecCode, String month);

	// 集团成员信息查询
	public String memberInfoQuery(String ecCode, String pageNo);

	// 通过集团编号和月份查询集团的购卡总数
	public String membercountQuery(String ecCode, String month);

	// 集团产品信息查询
	public String productInfoQuery(String ecCode);

	// 集团余额信息实时查询
	public String balanceQuery(String ecCode, String productCode);

	public String test(String ecCode);
}
