package com.urt.web.common.util.device;


public class SoapConstant {
   public static final String NAMESPACE_URI = "http://api.jasperwireless.com/ws/schema";
   public static final String PREFIX = "jws";
   public static final String DOMAIN = "http://api.jasperwireless.com";
   public static final String SOAPACTION_EDITNETWORKACCESSCONFIG="/ws/service/networkaccess/EditNetworkAccessConfig";
   public static final String SOAPACTION_GETTERMINALDETAILS="/ws/service/terminal/GetTerminalDetails";
   public static final String SOAPACTION_GETTERMINALRATING="/ws/service/terminal/GetTerminalRating";
   public static final String SOAPACTION_GETTERMINALUSAGEDATA="/ws/service/billing/GetTerminalUsageDataDetails";
   /**	获取通信计划	*/
   public static final String SOAPACTION_GETNETWORKACCESSCONFIG="/ws/service/networkaccess/GetNetworkAccessConfig";
   /**	变更资费计划	*/
   public static final String SOAPACTION_EDITTERMINAL="/ws/service/terminal/EditTerminal";
   /**	修改用户续约模式	*/
   public static final String SOAPACTION_EDITTERMINALRATING="/ws/service/terminal/EditTerminalRating";
   
   public static final String SOAPACTION_QUEUETERMINALRATEPLAN="/ws/service/terminal/QueueTerminalRatePlan";
   /**	修改用户续约模式	*/
   public static final String SOAPACTION_REMOVERATEPLANFROMQUEUE="/ws/service/terminal/RemoveRatePlanFromQueue";

   public static final String NOTITY_TYPE_FLOW="DATA_LIMIT";
   public static final String NOTITY_TYPE_DATE="DATE_LIMIT";
   public static final String CHANGE="SIM_RATEPLAN_CHANGE";
   public static final String RATE_NOTIFY="CTD_USAGE";
   
   public static final String DATA_LIMIT = "50";
   public static final String DATE_LIMIT = "120";
   public static final String DATA_CHANGE = "5M";
   /**	流量告警事件卡片（小于50M时触发）： */
   public static final String EVENT_TITLE_FLOW_CN = "您的流量不足提醒";
   public static final String EVENT_TITLE_FLOW_EN = "reminder of your insufficient data flow";
   public static final String EVENT_CONTENT_FLOW_CN = "用户当前剩余流量少于{0}M";
   public static final String EVENT_CONTENT_FLOW_EN = "current data balance is less than {0}M";
   public static final String EVENT_ACTION1 = "http://mobileaccess.lenovomm.com/cashier.xhtml";
   /**	 资费计划有效期告警事件卡片 */
   public static final String EVENT_TITLE_DATE_CN = "您的资费计划到期提醒";
   public static final String EVENT_TITLE_DATE_EN = "reminder of your data plan expiration";
   public static final String EVENT_CONTENT_DATE_CN = "用户当前资费计划剩余有效期少于{0}小时";
   public static final String EVENT_CONTENT_DATE_EN = "the current data plans remaining period is less than {0} hours";
   public static final String EVENT_ACTION2 = "http://mobileaccess.lenovomm.com/cashier.xhtml";

   /**	 流量告警事件卡片（每用完1G触发） */
   public static final String EVENT_TITLE_NOTIFY_CN = "流量使用量提醒";
   public static final String EVENT_TITLE_NOTIFY_EN = "reminder of data flow usage";
   public static final String EVENT_CONTENT_NOTIFY_CN = "截止{0},您当前套餐累计已使用 {1}GB";
   public static final String EVENT_CONTENT_NOTIFY_EN = "Up to {0},Your current package has been used {1}GB";
   
   public static final String SPLIT1 = "\\|\\|";
   public static final String DOMAIN_LENOVO="http://dcs.mspcloud.cn";
   public static final String SOAPACTION_QUERYPRODUCTINFO="/dc/service/productQueryServiceSoap/QueryProductInfo";
   public static final String PREFIX_LENOVO = "tem";
   public static final String NAMESPACE_URI_LENOVO = "http://tempuri.org/";
}