package com.urt.Ability.unicom.util;


public class SoapConstant {
   public static final String NAMESPACE_URI = "http://api.jasperwireless.com/ws/schema";
   public static final String PREFIX = "jws";
   public static final String DOMAIN = "http://api.jasperwireless.com";
   public static final String SOAPACTION_EDITNETWORKACCESSCONFIG="/ws/service/networkaccess/EditNetworkAccessConfig";
   public static final String SOAPACTION_GETTERMINALDETAILS="/ws/service/terminal/GetTerminalDetails";
   public static final String SOAPACTION_GETTERMINALRATING="/ws/service/terminal/GetTerminalRating";
   public static final String SOAPACTION_GETTERMINALUSAGEDATA="/ws/service/billing/GetTerminalUsageDataDetails";
   public static final String SOAPACTION_GETTERMINALUSAGE="/ws/service/billing/GetTerminalUsage";
   /**	获取通信计划	*/
   public static final String SOAPACTION_GETNETWORKACCESSCONFIG="/ws/service/networkaccess/GetNetworkAccessConfig";
   /**	变更资费计划	*/
   public static final String SOAPACTION_EDITTERMINAL="/ws/service/terminal/EditTerminal";
  
   /**设备**/
   public static final String SOAPACTION_GETMODIFIEDTERMINALS="/ws/service/terminal/GetModifiedTerminals";
   public static final String SOAPACTION_GETTERMINALSBYMSISDN="/ws/service/terminal/GetTerminalsByMsisdn";
   public static final String SOAPACTION_GETTERMINALSBYIMSI="/ws/service/terminal/GetTerminalsByIMSI";
   public static final String SOAPACTION_GETTERMINALSBYSECURESIMID="/ws/service/terminal/GetTerminalsBySecureSimId";
   public static final String SOAPACTION_GETTERMINALLATESTREGISTRATION="/ws/service/terminal/GetTerminalLatestRegistration";
   public static final String SOAPACTION_SENDCANCELLOCATION="/ws/service/terminal/SendCancelLocation";
   public static final String SOAPACTION_ASSIGNORUPDATEIPADDRESS="/ws/service/terminal/AssignOrUpdateIPAddress";
   /**全球SIM卡**/
   public static final String SOAPACTION_TRANSFERGLOBALSIM="/ws/service/globalsimtransfer/TransferGlobalSim";
   public static final String SOAPACTION_CANCELGLOBALSIMTRANSFER="/ws/service/globalsimtransfer/CancelGlobalSimTransfer";
   public static final String SOAPACTION_GETGLOBALSIMTRANSFERSTATUS="/ws/service/globalsimtransfer/GetGlobalSimTransferStatus";
   /**短信SMS**/
   public static final String SOAPACTION_GETMODIFIEDSMS="/ws/service/sms/GetModifiedSMS";
   public static final String SOAPACTION_GETSMSDETAILS="/ws/service/sms/GetSMSDetails";
   public static final String SOAPACTION_SENDSMS = "/ws/service/sms/SendSMS";
   public static final String SOAPACTION_SENDBULKSMS="/ws/service/sms/SendBulkSMS";
   public static final String SOAPACTION_SENDSMSTOMSISDN="/ws/service/sms/SendSMSToMsisdn";
   public static final String SOAPACTION_SENDBULKSMSTOMSISDN="/ws/service/sms/SendBulkSMSToMsisdn";
   /**资费计划**/
   public static final String SOAPACTION_ACTIVATETERMINALEVENT="/ws/service/eventplan/ActivateTerminalEvent";
   
   
   /**	修改用户续约模式	*/
   public static final String SOAPACTION_EDITTERMINALRATING="/ws/service/terminal/EditTerminalRating";
   
   public static final String SOAPACTION_QUEUETERMINALRATEPLAN="/ws/service/terminal/QueueTerminalRatePlan";
   /**	修改用户续约模式	*/
   public static final String SOAPACTION_REMOVERATEPLANFROMQUEUE="/ws/service/terminal/RemoveRatePlanFromQueue";
   /**	得到session信息	*/
   public static final String SOAPACTION_SESSIONINFO="/ws/service/terminal/GetSessionInfo";
   
   public static final String NOTITY_TYPE_FLOW="DATA_LIMIT";
   public static final String NOTITY_TYPE_DATE="DATE_LIMIT";
   public static final String CHANGE="SIM_RATEPLAN_CHANGE";
   
   public static final String DATA_LIMIT = "50";
   public static final String DATE_LIMIT = "120";
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
   
   public static final String SPLIT1 = "\\|\\|";
   public static final String DOMAIN_LENOVO="http://dcs.mspcloud.cn";
   public static final String SOAPACTION_QUERYPRODUCTINFO="/dc/service/productQueryServiceSoap/QueryProductInfo";
   public static final String PREFIX_LENOVO = "tem";
   public static final String NAMESPACE_URI_LENOVO = "http://tempuri.org/";
}