/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/9/19 12:47:11                           */
/*==============================================================*/


drop procedure if exists DD_REMAIN;

drop procedure if exists INSERTACTIVE;

drop procedure if exists INSERTACTIVE1;

drop procedure if exists INSERTACTIVE2;

drop procedure if exists PROC_ACCT_DEPOSIT_DD;

drop procedure if exists PROC_ACCT_DEPOSIT_MM;

drop procedure if exists PROC_ACCT_DEPOSIT_TEST;

drop procedure if exists PROC_GOODS_STATE_DD;

drop procedure if exists PROC_GOODS_STATE_MM;

drop procedure if exists PROC_USER_ACTIVE_DD;

drop procedure if exists PROC_USER_ACTIVE_MM;

drop procedure if exists PROC_USER_EXPIRE_DD;

drop procedure if exists PROC_USER_EXPIRE_MM;

drop procedure if exists PROC_USER_FLOW_DD;

drop procedure if exists PROC_USER_FLOW_MM;

drop procedure if exists PROC_USER_IN_DD;

drop procedure if exists PROC_USER_IN_MM;

drop procedure if exists PROC_USER_ORDER_DD;

drop procedure if exists PROC_USER_ORDER_MM;

drop procedure if exists PROC_USER_RECHARGE_DD;

drop procedure if exists PROC_USER_RECHARGE_MM;

drop procedure if exists PROC_USER_STATE_DD;

drop procedure if exists PROC_USER_STATE_MM;

drop procedure if exists PRO_4;

drop procedure if exists PRO_5;

drop procedure if exists TEST;

drop procedure if exists TEST_A;

drop table if exists FAN_USER;

drop table if exists FAN_USER_SVCSTATE;

drop table if exists LAO_ACCOUNT_REL;

drop table if exists LAO_ACCT_DEPOSIT_DD;

drop table if exists LAO_ACCT_DEPOSIT_MM;

drop table if exists LAO_ALM_ELEMENT;

drop table if exists LAO_ALM_ELEM_VALUE;

drop table if exists LAO_ALM_RULE;

drop table if exists LAO_ALM_RULE_ELEM;

drop table if exists LAO_ALM_RULE_LOG;

drop table if exists LAO_ALM_RULE_TYPE;

drop table if exists LAO_API_MSG_LOG;

drop table if exists LAO_BAL_ALM_LOG;

drop index IDX_LAO_BATCH_DATA_RECV_TIME on LAO_BATCH_DATA;

drop table if exists LAO_BATCH_DATA;

drop index LAO_BATCH_DATADETAIL_INDEX1 on LAO_BATCH_DATADETAIL;

drop table if exists LAO_BATCH_DATADETAIL;

drop index I_LAO_BATCH_DATADETAIL_ICCID on LAO_BATCH_DATADETAIL17_2_15;

drop index IDX_DATADETAIL_BATCH_ID on LAO_BATCH_DATADETAIL17_2_15;

drop table if exists LAO_BATCH_DATADETAIL17_2_15;

drop index LAO_BATCH_DATADETAIL_PK_ICCID on LAO_BATCH_DATADETAIL_17413;

drop table if exists LAO_BATCH_DATADETAIL_17413;

drop table if exists LAO_BATCH_FTP_IMPORT;

drop table if exists LAO_BATCH_RESULT;

drop table if exists LAO_BILL_RESULT;

drop table if exists LAO_BILL_RESULT_HIS;

drop table if exists LAO_B_ACCESSLOG;

drop table if exists LAO_B_ACCESSLOG_20170526;

drop index IDX_B_BILL_USER_ID on LAO_B_BILL;

drop index IDX_B_BILL_ACCT_ID on LAO_B_BILL;

drop table if exists LAO_B_BILL;

drop table if exists LAO_B_PAYLOG;

drop index IDX_B_PAYRELATION_USER_ID on LAO_B_PAYRELATION;

drop index IDX_B_PAYRELATION_ACCT_ID on LAO_B_PAYRELATION;

drop table if exists LAO_B_PAYRELATION;

drop table if exists LAO_CARDLIFE_CHG;

drop table if exists LAO_CHARGE_ORDER;

drop index IDX_LAO_CUSTOMER_RSRV_STR1 on LAO_CUSTOMER;

drop table if exists LAO_CUSTOMER;

drop table if exists LAO_CUSTOMER_NOTICE;

drop table if exists LAO_CUSTOMER_STYLE;

drop table if exists LAO_CUSTOMER_VERIFY;

drop table if exists LAO_CUST_CONFIG;

drop table if exists LAO_CUST_CONTACT;

drop table if exists LAO_CUST_GROUP;

drop table if exists LAO_CUST_PERSON;

drop index IDX_DATAIMPORT_IMP_DATE on LAO_DATAIMPORT_LOG;

drop table if exists LAO_DATAIMPORT_LOG;

drop table if exists LAO_DEVICEDATA_INFO;

drop table if exists LAO_DEVICEPRODUCT_AD;

drop index LAO_DEVICE_REL_ICCID on LAO_DEVICE_REL;

drop table if exists LAO_DEVICE_REL;

drop table if exists LAO_DISCONT;

drop table if exists LAO_DMPCARD_DATA;

drop table if exists LAO_DMPDEVICE_ATTRIBUTE;

drop table if exists LAO_DMPPOSITION_DATA;

drop table if exists LAO_DMPSCHEME_GROUP;

drop table if exists LAO_DMPSCHEME_STRATEGY;

drop table if exists LAO_DMPSTRATEGY_EDIT;

drop table if exists LAO_DMPSTRATEGY_OPERATION;

drop table if exists LAO_DMPSTRATEGY_RELATION;

drop table if exists LAO_DMP_CARDGROUP;

drop table if exists LAO_DMP_GROUP;

drop table if exists LAO_DMP_LOG;

drop table if exists LAO_DMP_OPERATION;

drop table if exists LAO_DMP_STRATEGY;

drop table if exists LAO_DOWNLOADFILE_CONFIG;

drop table if exists LAO_D_COMMPARA;

drop table if exists LAO_D_ITEM;

drop index IDX_D_OBJECT_ATTR on LAO_D_OBJECT;

drop table if exists LAO_D_OBJECT;

drop table if exists LAO_ESIM_DETAIL;

drop table if exists LAO_ESIM_FLOWINFO;

drop table if exists LAO_ESIM_FLOW_GIVEN_DETAIL;

drop index IDX_LAO_ESIM_GOODS on LAO_ESIM_GOODS;

drop table if exists LAO_ESIM_GOODS;

drop table if exists LAO_ESIM_GOODS_PLAN;

drop index IDX_LAO_ESIM_ICCID_LIB on LAO_ESIM_ICCID_LIB;

drop table if exists LAO_ESIM_ICCID_LIB;

drop table if exists LAO_ESIM_IMEI;

drop index IDX_LAO_ESIM_IMEI_EID on LAO_ESIM_IMEI_EID;

drop table if exists LAO_ESIM_IMEI_EID;

drop table if exists LAO_ESIM_LENOVOID_GOODSID;

drop table if exists LAO_ESIM_LENOVO_IMEI;

drop index IDX_LAO_ESIM_LOG on LAO_ESIM_LOG;

drop table if exists LAO_ESIM_LOG;

drop table if exists LAO_ESIM_OPERATOR_PROPERTY;

drop table if exists LAO_ESIM_STATE;

drop index IDX_LAO_ESIM_TRADE on LAO_ESIM_TRADE;

drop table if exists LAO_ESIM_TRADE;

drop index IDX_LAO_ESIM_TRADEFEESUB on LAO_ESIM_TRADEFEESUB;

drop table if exists LAO_ESIM_TRADEFEESUB;

drop index IDX_LAO_ESIM_USER on LAO_ESIM_USER;

drop table if exists LAO_ESIM_USER;

drop index IDX_LAO_ESIM_USER_BUNDING on LAO_ESIM_USER_BUNDING;

drop table if exists LAO_ESIM_USER_BUNDING;

drop index IDX_LAO_ESIM_USER_GIVEN on LAO_ESIM_USER_GIVEN;

drop table if exists LAO_ESIM_USER_GIVEN;

drop index IDX_LAO_ESIM_USER_GOODS on LAO_ESIM_USER_GOODS;

drop table if exists LAO_ESIM_USER_GOODS;

drop table if exists LAO_FLOWOPERATION_FLOWORDER;

drop table if exists LAO_FLOWOPERATION_LOG;

drop table if exists LAO_FLOW_CONFIG;

drop index LAO_FTPFILE_COLLECT on LAO_FTPFILE_COLLECT;

drop index IDX_LAO_FTPFILE_COLLECT on LAO_FTPFILE_COLLECT;

drop table if exists LAO_FTPFILE_COLLECT;

drop index LAO_FTPFILE_INFO on LAO_FTPFILE_INFO;

drop index IDX_LAO_FTPFILE_INFO on LAO_FTPFILE_INFO;

drop table if exists LAO_FTPFILE_INFO;

drop table if exists LAO_F_ACCTDEPOSIT;

drop table if exists LAO_F_ACCTDEPOSIT_20170526;

drop table if exists LAO_F_ACCTDEPOSIT_CHANGE;

drop table if exists LAO_GOODS;

drop table if exists LAO_GOODS_BAK;

drop table if exists LAO_GOODS_ELEMENT;

drop table if exists LAO_GOODS_FLOW_DD;

drop table if exists LAO_GOODS_OPERATIVE;

drop table if exists LAO_GOODS_RELEASE;

drop table if exists LAO_GOODS_RELEASE_CMP;

drop table if exists LAO_GOODS_STATE_DD;

drop table if exists LAO_GOODS_STATE_MM;

drop index IDX_GRPNET_FEECOUNT_OBJECT_ID on LAO_GRPNET_FEECOUNT;

drop table if exists LAO_GRPNET_FEECOUNT;

drop index IDX_GRPNET_FEEDISCNT_OBJECT_ID on LAO_GRPNET_FEEDISCNT;

drop table if exists LAO_GRPNET_FEEDISCNT;

drop index IDX_GRPNET_IMPBILL_USER_ID on LAO_GRPNET_IMPBILL;

drop index IDX_GRPNET_IMPBILL_CYCLE_ID on LAO_GRPNET_IMPBILL;

drop index IDX_GRPNET_IMPBILL_BATCH_ID on LAO_GRPNET_IMPBILL;

drop table if exists LAO_GRPNET_IMPBILL;

drop table if exists LAO_ICCID_ASSIGN_BATCH;

drop table if exists LAO_ICCID_ASSIGN_LIB;

drop table if exists LAO_ICCID_BATCHDATA;

drop table if exists LAO_ICCID_DONATE_STEP;

drop index UNIQ_IDX_LAO_ICCID_LIB_ICCID on LAO_ICCID_LIB;

drop table if exists LAO_ICCID_LIB;

drop table if exists LAO_ICCID_LIBRARY;

drop table if exists LAO_ICCID_LIB_BAK;

drop table if exists LAO_ICCID_LIB_FUHPBAK;

drop table if exists LAO_ICCID_LIB_SUN;

drop table if exists LAO_ICCID_LIB_ZC;

drop table if exists LAO_ICCID_LOG;

drop table if exists LAO_IMEI_LB;

drop table if exists LAO_IMEI_LIB;

drop table if exists LAO_IMEI_LIBRARY;

drop table if exists LAO_IMEI_LIBRARY_INSERT;

drop table if exists LAO_KEY_MANAGEMENT;

drop table if exists LAO_LBS_DATA;

drop table if exists LAO_MNO_ACCESS_LOG;

drop table if exists LAO_MNO_PROVIDE_SERVER;

drop table if exists LAO_MNO_SERVER_CONFIG;

drop table if exists LAO_MNO_SERVER_TASK;

drop table if exists LAO_MNO_SYSTEM_CONFIG;

drop table if exists LAO_MNO_SYSTEM_IP;

drop table if exists LAO_OPERATORDEAL_LOG;

drop index IDX_LAO_OPERATORS_NAME on LAO_OPERATORS;

drop table if exists LAO_OPERATORS;

drop table if exists LAO_OPERATORSBILL_RESULT;

drop index INDEX_LAO_OPERATORS_BILL_ICCID on LAO_OPERATORS_BILL;

drop table if exists LAO_OPERATORS_BILL;

drop table if exists LAO_OPERATORS_BILL_BAK;

drop table if exists LAO_OPERATORS_CYCLE;

drop table if exists LAO_OPERATOR_PLAN;

drop table if exists LAO_PERIPHERAL_LOG_0601;

drop table if exists LAO_PERIPHERAL_SYS_ACCESS_LOG;

drop table if exists LAO_PICTURES;

drop table if exists LAO_POOL;

drop table if exists LAO_POOL_MEMBER;

drop table if exists LAO_POOL_USEINFO;

drop table if exists LAO_POSITION_INFO;

drop table if exists LAO_PROVIDE_SERVER;

drop table if exists LAO_RATECHANGE;

drop table if exists LAO_REALNAME_VERIFY;

drop table if exists LAO_REPT_INFO;

drop table if exists LAO_ROLE_SERVER_CONN;

drop table if exists LAO_RULE_DEF;

drop table if exists LAO_RULE_REL;

drop table if exists LAO_SERVER_PROVIDE_VERIFIY;

drop table if exists LAO_SERVICE;

drop table if exists LAO_SERVICE_STATUS;

drop table if exists LAO_SERVICE_STATUS_CHG;

drop table if exists LAO_SMS_DELIVER;

drop table if exists LAO_SMS_INFO;

drop table if exists LAO_SMS_TMPL;

drop table if exists LAO_SS_ACCESS_LOG;

drop index UNIQUE_INDEX_LOGIN_NAME on LAO_SS_ACCOUNT;

drop table if exists LAO_SS_ACCOUNT;

drop table if exists LAO_SS_ACCOUNT_BAK;

drop table if exists LAO_SS_ACCOUNT_ROLE;

drop table if exists LAO_SS_LOGIN_LOG;

drop table if exists LAO_SS_NAVIGATION;

drop table if exists LAO_SS_RESOURCE;

drop table if exists LAO_SS_ROLE;

drop table if exists LAO_SS_ROLE_CUST;

drop table if exists LAO_SS_ROLE_NAVIGATION;

drop table if exists LAO_SS_ROLE_RESOURCE;

drop index LAO_SS_STATIC_CODE on LAO_SS_STATIC;

drop table if exists LAO_SS_STATIC;

drop table if exists LAO_SS_STATIC_BAK;

drop table if exists LAO_STAFF;

drop table if exists LAO_TARIFF_PLAN;

drop table if exists LAO_TASK;

drop table if exists LAO_TEMP_CHENJJ7;

drop table if exists LAO_TEMP_CHENJJ7_BUCHONG;

drop table if exists LAO_TEMP_ICCID;

drop table if exists LAO_TEMP_TELECOM_1202_CHENJJ7;

drop table if exists LAO_TEMP_ZHAO;

drop table if exists LAO_TEMP_ZHAO1;

drop table if exists LAO_TEMP_ZHAO2;

drop table if exists LAO_TEMP_ZZ;

drop table if exists LAO_TRADE;

drop table if exists LAO_TRADEFEE_SUB;

drop table if exists LAO_TRADEFEE_SUB_BAK;

drop table if exists LAO_TRADE_BAK;

drop table if exists LAO_TRADE_DISCNT;

drop table if exists LAO_TRADE_GOODS;

drop index INDEX_TRADE_OPE_PLAN_USERID on LAO_TRADE_OPERATOR_PLAN;

drop table if exists LAO_TRADE_OPERATOR_PLAN;

drop table if exists LAO_TRADE_RES;

drop table if exists LAO_TRADE_SVCSTATE;

drop table if exists LAO_TRADE_TYPE;

drop table if exists LAO_TRAFFICMM_TASK;

drop table if exists LAO_TRAFFICQUERY_LOG;

drop index IDX_TRAFFIC_DETAIL_USER_ID on LAO_TRAFFIC_DETAIL;

drop index IDX_TRAFFIC_DETAIL_DATA_CYCLE on LAO_TRAFFIC_DETAIL;

drop index IDX_TRAFFIC_DETAIL_CUST on LAO_TRAFFIC_DETAIL;

drop table if exists LAO_TRAFFIC_DETAIL;

drop index IDX_TRAFFIC_MM_USER_ID on LAO_TRAFFIC_MM;

drop index IDX_TRAFFIC_MM_CYCLE on LAO_TRAFFIC_MM;

drop index IDX_TRAFFIC_MM_CUST on LAO_TRAFFIC_MM;

drop table if exists LAO_TRAFFIC_MM;

drop table if exists LAO_TRAFFIC_MM00;

drop table if exists LAO_TRAFFIC_MM_170306;

drop table if exists LAO_TRAFFIC_MM_170426;

drop table if exists LAO_TRAFFIC_MM_170427;

drop table if exists LAO_TRAFFIC_MM_170605;

drop table if exists LAO_TRAFFIC_MM_17526;

drop index IDX_LAO_USER_OPERATOR_ID on LAO_USER;

drop index IDX_LAO_USER_ICCID on LAO_USER;

drop index IDX_LAO_USER_CUST_ID on LAO_USER;

drop index IDX_LAO_USER_ATRIBUTE2 on LAO_USER;

drop index IDX_LAO_USER_ATRIBUTE1 on LAO_USER;

drop table if exists LAO_USER;

drop table if exists LAO_USER2C_ACTIVE_DD;

drop table if exists LAO_USER2C_ORDER_DD;

drop table if exists LAO_USER2C_STATE_DD;

drop table if exists LAO_USERBILLS_DETAIL;

drop index INDEX_LAO_USERFEE_INFO_TRADEID on LAO_USERFEE_INFO;

drop table if exists LAO_USERFEE_INFO;

drop table if exists LAO_USER_ACTIVE_DD;

drop table if exists LAO_USER_ACTIVE_MM;

drop table if exists LAO_USER_BAK1;

drop index IDX_LAO_USER_CHG_OPERATOR_ID on LAO_USER_CHG;

drop index IDX_LAO_USER_CHG_MSISDN on LAO_USER_CHG;

drop index IDX_LAO_USER_CHG_ICCID on LAO_USER_CHG;

drop index IDX_LAO_USER_CHG_CUST_ID on LAO_USER_CHG;

drop index IDX_LAO_USER_CHG_CHANNEL_CUST on LAO_USER_CHG;

drop index IDX_LAO_USER_CHG_ATRIBUTE2 on LAO_USER_CHG;

drop index IDX_LAO_USER_CHG_ATRIBUTE1 on LAO_USER_CHG;

drop table if exists LAO_USER_CHG;

drop index IDX_LAO_USER_CMP_MSISDN on LAO_USER_CMP;

drop index IDX_LAO_USER_CMP_ICCID on LAO_USER_CMP;

drop index IDX_LAO_USER_CMP_CHANNEL_CUST on LAO_USER_CMP;

drop table if exists LAO_USER_CMP;

drop table if exists LAO_USER_EXPIRE_DD;

drop table if exists LAO_USER_EXPIRE_MM;

drop table if exists LAO_USER_FLOW_DD;

drop table if exists LAO_USER_FLOW_MM;

drop table if exists LAO_USER_GOODS;

drop table if exists LAO_USER_GOODS_BAK_20170628;

drop table if exists LAO_USER_GOODS_CMP;

drop index IDX_LAO_USER_HIS_MSISDN on LAO_USER_HIS;

drop index IDX_LAO_USER_HIS_ICCID on LAO_USER_HIS;

drop index IDX_LAO_USER_HIS_CUST_ID on LAO_USER_HIS;

drop index IDX_LAO_USER_HIS_CHANNEL_CUST on LAO_USER_HIS;

drop table if exists LAO_USER_HIS;

drop table if exists LAO_USER_INFO;

drop table if exists LAO_USER_IN_DD;

drop table if exists LAO_USER_IN_MM;

drop table if exists LAO_USER_IP_MANAGER;

drop table if exists LAO_USER_IP_MANAGER01;

drop table if exists LAO_USER_LINKED_TARIFFPLAN;

drop table if exists LAO_USER_OPERATOR_INFO;

drop table if exists LAO_USER_OPERATOR_PLAN;

drop table if exists LAO_USER_OPERATOR_PLAN_CMP;

drop table if exists LAO_USER_ORDER_DD;

drop table if exists LAO_USER_ORDER_MM;

drop table if exists LAO_USER_RECHARGE_DD;

drop table if exists LAO_USER_RECHARGE_MM;

drop table if exists LAO_USER_RES;

drop table if exists LAO_USER_STATE_DD;

drop table if exists LAO_USER_STATE_MM;

drop table if exists LAO_USER_SVCSTATE;

drop table if exists LAO_USER_TURN_DD;

drop table if exists LAO_USER_TURN_MM;

drop table if exists LAO_VAL_PARAM;

drop table if exists LAO_WHITELIST;

drop table if exists LAO_WXB;

drop table if exists T_TEST_B;

drop table if exists WXB_TMP;

drop table if exists WXB_TMP1;

/*==============================================================*/
/* User: T2                                                     */
/*==============================================================*/
--create user T2;

/*==============================================================*/
/* Table: FAN_USER                                              */
/*==============================================================*/
create table FAN_USER
(
   USER_ID              varchar(255),
   CHANNEL_CUST_ID      varchar(255),
   CUST_ID              varchar(255),
   MSISDN               varchar(255),
   OPERATORS_ID         varchar(255),
   SCORE_VALUE          varchar(255),
   CREDIT_CLASS         varchar(255),
   BASIC_CREDIT_VALUE   varchar(255),
   CREDIT_VALUE         varchar(255),
   ACCT_TAG             varchar(255),
   PREPAY_TAG           varchar(255),
   IN_DATE              varchar(255),
   OPEN_DATE            varchar(255),
   REMOVE_TAG           varchar(255),
   DESTROY_TIME         varchar(255),
   PRE_DESTROY_TIME     varchar(255),
   FIRST_CALL_TIME      varchar(255),
   LAST_STOP_TIME       varchar(255),
   ICCID                varchar(255),
   DEVICE_ID            varchar(255),
   STATUS_CODE          varchar(255),
   ALIVE_CHECK_TIME     varchar(255),
   IMEL                 varchar(255),
   STATE_CODE           varchar(255),
   ATTRIBURTE1          varchar(255),
   VALUE1               varchar(255),
   ATTRIBUTE2           varchar(255),
   VALUE2               varchar(255)
);

/*==============================================================*/
/* Table: FAN_USER_SVCSTATE                                     */
/*==============================================================*/
create table FAN_USER_SVCSTATE
(
   USER_ID              varchar(255),
   SERVICE_ID           varchar(255),
   START_DATE           varchar(255),
   END_DATE             varchar(255),
   UPDATE_TIME          varchar(255),
   STATE_CODE           varchar(255),
   OPEARTORS_DEAL_RST   varchar(255),
   OPEARTORS_DEAL_CODE  varchar(255),
   OPEARTORS_DEAL_NUM   varchar(255)
);

/*==============================================================*/
/* Table: LAO_ACCOUNT_REL                                       */
/*==============================================================*/
create table LAO_ACCOUNT_REL
(
   REL_ID               numeric(16,0) not null,
   ACCOUNT_ID           numeric(16,0) not null,
   REL_TYPE             varchar(4),
   REL_ACCOUNT          varchar(50),
   REL_LOGINNAME        varchar(200),
   REL_NICKNAME         varchar(200),
   REL_IMGURL           varchar(500),
   RECV_TIME            datetime,
   REMARK               varchar(200),
   primary key (REL_ID),
   check ("REL_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_ACCT_DEPOSIT_DD                                   */
/*==============================================================*/
create table LAO_ACCT_DEPOSIT_DD
(
   IN_DATE              numeric(8,0) comment '每日，格式为YYYYMMDD',
   CHANNEL_CUST_ID      numeric(16,0) comment '渠道客户ID',
   CHANNEL_CUST_NAME    varchar(100) comment '渠道客户名称',
   CUST_ID              numeric(16,0) comment '客户ID',
   CUST_NAME            varchar(100) comment '客户名称',
   STAFF_ID             varchar(50) comment '发展人员ID',
   STAFF_NAME           varchar(100) comment '发展人名称',
   REGION_ID            varchar(50) comment '区域',
   REGION_NAME          varchar(100) comment '区域名称',
   OPERATORS_ID         numeric(8,0) comment '运营商ID',
   OPERATORS_NAME       varchar(100) comment '运营商名称',
   VALUE1               varchar(50) comment '一级标签',
   VALUE1_NAME          varchar(100) comment '一级标签名称',
   VALUE2               varchar(50) comment '二级标签',
   VALUE2_NAME          varchar(100) comment '二级标签名称',
   IN_MONEY             varchar(20) comment '充值金额',
   FEE                  varchar(20) comment '消费金额',
   LEFT_MONEY           varchar(20) comment '余额',
   UPDATE_TIME          datetime comment '更新时间,系统时间',
   REMARK               varchar(200) comment '备注',
   PAYMENT_OP           numeric(8,0) comment '10000 收费，10004扣费'
);

alter table LAO_ACCT_DEPOSIT_DD comment '每日余额汇总';

/*==============================================================*/
/* Table: LAO_ACCT_DEPOSIT_MM                                   */
/*==============================================================*/
create table LAO_ACCT_DEPOSIT_MM
(
   IN_DATE              numeric(8,0) comment '每月，格式为YYYYMM',
   CHANNEL_CUST_ID      numeric(16,0) comment '渠道客户ID',
   CHANNEL_CUST_NAME    varchar(100) comment '渠道客户名称',
   CUST_ID              numeric(16,0) comment '客户ID	',
   CUST_NAME            varchar(100) comment '客户名称',
   STAFF_ID             varchar(50) comment '发展人员ID',
   STAFF_NAME           varchar(100) comment '发展人名称',
   REGION_ID            varchar(50) comment '区域	',
   REGION_NAME          varchar(100) comment '区域名称',
   OPERATORS_ID         numeric(8,0) comment '运营商ID',
   OPERATORS_NAME       varchar(100) comment '运营商名称',
   VALUE1               varchar(50) comment '一级标签',
   VALUE1_NAME          varchar(100) comment '一级标签名称',
   VALUE2               varchar(50) comment '二级标签',
   VALUE2_NAME          varchar(100) comment '二级标签名称',
   IN_MONEY             varchar(20) comment '充值金额',
   FEE                  varchar(20) comment '消费金额',
   LEFT_MONEY           varchar(20) comment '余额',
   UPDATE_TIME          datetime comment '更新时间',
   REMARK               varchar(200) comment '备注',
   PAYMENT_OP           numeric(8,0) comment '操作类型'
);

alter table LAO_ACCT_DEPOSIT_MM comment '每月余额汇总';

/*==============================================================*/
/* Table: LAO_ALM_ELEMENT                                       */
/*==============================================================*/
create table LAO_ALM_ELEMENT
(
   ELEMENT_TYPE         varchar(6) comment '1-？？？？？？？？？？2-？？？？？？？？？？3-？？？？？？？？',
   ELEMENT_ID           numeric(16,0) not null,
   ELEMENT_NAME         varchar(200) comment '？？？？',
   ELEMENT_DESC         varchar(500) comment '？？？？？？？？？？？？？？？？？？？？？？？？？？ #COND1#  > 480 & #COND2# < 400
            ？？？？？？？？？？COND1？？COND2？？LAO_RULE_PARAM？？？？？？？？RULE_ID？？？？？？？？？？？？
            ？？？？？？？？？？？？？？java？？？？js？？？？？？？？？？？？？？？？？？TRUE？？？？？？？？？？？？CAL_FORMULA？？？？？？？？？？？？？？？？？？',
   ELEMENT_LEVEL        char(1) comment '1-1？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？2-2？？？？？？？？？？1？？？？？？？？',
   ELEM_DISPLAY         varchar(500),
   ELEMENT_SOURCE       varchar(200) comment '？？？？？？？？？？？？？？？？？？？？
            para_code1？？？？？？？？？？？？？？？？？？？？para_code2？？？？？？value
            select para_code1,para_code2 from LAO_D_COMMPARA where PARA_NAME=LAO_ALM_ELEMENT. ELEM_SOURCE and PARA_CODE= LAO_ALM_RULE_TYPE. RULE_TYPE_ID',
   P_ELEMENT_ID         numeric(16,0),
   DISPLAY_TYPE         varchar(4) comment '1-label？？？？？？elem_dispaly？？？？; 2-？？？？？？？？？？？？？？？？？？？？？？ 3-？？？？？？？？？？？？？？？？ELEMENT_SOURCE？？？？？？？？？？lao_d_commpara？？？？？？？？？？4-？？？？？？？？？？？？？？？？？？？？？？？？？？？？',
   ELEMENT_DEFAULT      varchar(200),
   REMARK               varchar(200) comment '？？？？',
   primary key (ELEMENT_ID),
   check ("ELEMENT_ID" IS NOT NULL)
);

alter table LAO_ALM_ELEMENT comment '？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_ALM_ELEM_VALUE                                    */
/*==============================================================*/
create table LAO_ALM_ELEM_VALUE
(
   RULE_ID              numeric(16,0) not null,
   RULE_TYPE_ID         numeric(16,0) not null comment '？？？？？？？？？？？？ 2？？？？？？？？？？',
   ELEMENT_ID           numeric(16,0) not null,
   ELEM_VALUE           varchar(4000) comment '？？？？',
   VALID_TAG            char(1) comment '0-？？？？？？1-？？？？',
   RECV_TIME            datetime,
   RECV_OPER_ID         varchar(40),
   UPDATE_TIME          datetime,
   UPDATE_OPER_ID       varchar(40),
   REMARK               varchar(200) comment '？？？？',
   check ("RULE_ID" IS NOT NULL)
);

alter table LAO_ALM_ELEM_VALUE comment '？？？？？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_ALM_RULE                                          */
/*==============================================================*/
create table LAO_ALM_RULE
(
   RULE_ID              numeric(16,0) not null,
   RULE_NAME            varchar(500),
   RULE_TYPE_ID         numeric(16,0) not null comment '？？？？？？？？？？？？ 2？？？？？？？？？？',
   CHANNEL_CUST_ID      numeric(16,0),
   CUST_ID              numeric(16,0),
   RECV_TIME            datetime,
   RECV_OPER_ID         varchar(40),
   VALID_TAG            char(1) comment '0-？？？？？？1-？？？？',
   UPD_TIME             datetime,
   UPD_OPER_ID          varchar(40),
   REMARK               varchar(200) comment '？？？？',
   primary key (RULE_ID),
   check ("RULE_ID" IS NOT NULL)
);

alter table LAO_ALM_RULE comment '？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_ALM_RULE_ELEM                                     */
/*==============================================================*/
create table LAO_ALM_RULE_ELEM
(
   RULE_TYPE_ID         numeric(16,0) not null,
   ELEMENT_ID           numeric(16,0) not null,
   DISPLAY_INDEX        numeric(6,0),
   VALID_TAG            char(1) comment '1-？？？？？？0-？？？？',
   CHANNEL_CUST_ID      numeric(16,0),
   REMARK               varchar(200) comment '？？？？',
   ELEM_GROUP           numeric(2,0),
   primary key (RULE_TYPE_ID, ELEMENT_ID),
   check ("RULE_TYPE_ID" IS NOT NULL)
);

alter table LAO_ALM_RULE_ELEM comment '？？？？？？？？？？？？？？？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_ALM_RULE_LOG                                      */
/*==============================================================*/
create table LAO_ALM_RULE_LOG
(
   ALARM_ID             numeric(16,0) not null,
   CYCLE_ID             numeric(16,0),
   RULE_ID              numeric(16,0),
   CHANNEL_CUST_ID      numeric(16,0),
   CUST_ID              numeric(16,0),
   USER_ID              numeric(16,0),
   ICCID                varchar(50),
   DEAL_TAG             char(1),
   DEAL_TIME            datetime,
   OPER_ID              varchar(40),
   REMARK               varchar(200),
   OPERATE              varchar(2) comment '1邮件2短信3停机',
   BATCH                varchar(16) comment '发送警告批次',
   CARDNUM              numeric(8,0) comment 'iccid数量',
   RATE                 varchar(10) comment '使用流量警戒线百分率',
   TEMPLETCONTENT       varchar(500) comment '通知内容',
   check ("ALARM_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_ALM_RULE_TYPE                                     */
/*==============================================================*/
create table LAO_ALM_RULE_TYPE
(
   RULE_TYPE_ID         numeric(16,0) not null,
   RULE_TYPE_NAME       varchar(200) comment '？？？？',
   RULE_TYPE_DESC       varchar(500) comment '？？？？？？？？？？？？？？？？？？？？？？？？？？ #COND1#  > 480 & #COND2# < 400
            ？？？？？？？？？？COND1？？COND2？？LAO_RULE_PARAM？？？？？？？？RULE_ID？？？？？？？？？？？？
            ？？？？？？？？？？？？？？java？？？？js？？？？？？？？？？？？？？？？？？TRUE？？？？？？？？？？？？CAL_FORMULA？？？？？？？？？？？？？？？？？？',
   RULE_LEVEL           char(1) comment '1-1？？？？1-2？？',
   RULE_P_ID            numeric(16,0),
   VALID_TAG            char(1) comment '1-？？？？？？0-？？？？',
   EXE_TAG              varchar(2) comment '0-？？？？？？？？？？1-？？？？？？？？',
   REMARK               varchar(200) comment '？？？？',
   check ("RULE_TYPE_ID" IS NOT NULL)
);

alter table LAO_ALM_RULE_TYPE comment '？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_API_MSG_LOG                                       */
/*==============================================================*/
create table LAO_API_MSG_LOG
(
   MSG_ID               numeric(16,0) not null,
   EVENT_ID             varchar(100),
   EVENT_TYPE           varchar(100),
   RULE_NAME            varchar(500),
   ICCID                varchar(100),
   MSG_CONTEXT          varchar(4000),
   DEAL_TAG             char(1),
   DEAL_RESULT          varchar(1000),
   OPERATOR_ID          numeric(8,0),
   RECV_TIME            datetime,
   UPDATE_TIME          datetime,
   UPDATE_ACCOUNT       varchar(40),
   REMARK               varchar(200),
   check ("MSG_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_BAL_ALM_LOG                                       */
/*==============================================================*/
create table LAO_BAL_ALM_LOG
(
   ALARM_ID             numeric(16,0) not null,
   RULE_NAME            varchar(60),
   CHANNEL_CUST_ID      numeric(16,0),
   CUR_BAL              numeric(16,0),
   REMIANBAL            numeric(16,0),
   SMS_TAG              char(1),
   EMAIL_TAG            char(1),
   DEAL_TIME            datetime,
   primary key (ALARM_ID),
   check ("ALARM_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_BATCH_DATA                                        */
/*==============================================================*/
create table LAO_BATCH_DATA
(
   BATCH_ID             numeric(16,0) not null comment '？？？？？？？？？？',
   TRADE_TYPE_CODE      numeric(4,0) comment '？？？？？？？？？？？？',
   OPERATORS_ID         numeric(8,0) comment '？？？？？？id',
   DEAL_TAG             varchar(2) comment '0-？？？？？？？？1-？？？？？？？？2-？？？？？？？？',
   RESULT_INFO          varchar(200),
   RECV_TIME            datetime comment '？？？？？？？？？？？？',
   UPDATE_TIME          datetime comment '？？？？？？？？',
   OPER_ID              varchar(40) comment '？？？？？？？？',
   SUM_NUM              numeric(11,0) default 0 comment '？？？？？？',
   SUCC_NUM             numeric(11,0) default 0 comment '？？？？？？？？',
   FAIL_NUM             numeric(11,0) default 0 comment '？？？？？？？？',
   REMARK               varchar(200) comment '？？？？',
   RSRV_INFO1           varchar(200) comment '？？？？？？？？1',
   RSRV_INFO2           varchar(200) comment '？？？？？？？？2',
   primary key (BATCH_ID),
   check ("BATCH_ID" IS NOT NULL)
);

alter table LAO_BATCH_DATA comment '？？？？？？？？？？？？？？';

/*==============================================================*/
/* Index: IDX_LAO_BATCH_DATA_RECV_TIME                          */
/*==============================================================*/
create index IDX_LAO_BATCH_DATA_RECV_TIME on LAO_BATCH_DATA
(
   RECV_TIME
);

/*==============================================================*/
/* Table: LAO_BATCH_DATADETAIL                                  */
/*==============================================================*/
create table LAO_BATCH_DATADETAIL
(
   DATADETAIL_ID        numeric(16,0) not null,
   BATCH_ID             numeric(16,0) not null,
   TRADE_TYPE_CODE      numeric(4,0),
   CHANNEL_CUST_ID      numeric(16,0),
   CUST_ID              numeric(16,0),
   USER_ID              numeric(16,0),
   MSISDN               varchar(40),
   ICCID                varchar(50),
   GOODS_ID             numeric(16,0),
   RECV_TIME            datetime,
   FLOW_STATUS          varchar(2),
   DEAL_TAG             char(1),
   RESULT_INFO          varchar(500),
   UPDATE_TIME          datetime,
   TRADE_ID             numeric(16,0),
   OPER_ID              varchar(40),
   PARA_CODE1           varchar(100),
   PARA_CODE2           varchar(100),
   PARA_CODE3           varchar(100),
   PARA_CODE4           varchar(100),
   PARA_CODE5           varchar(100),
   PARA_CODE6           varchar(100),
   PARA_CODE7           varchar(100),
   PARA_CODE8           varchar(100),
   PARA_CODE9           varchar(100),
   PARA_CODE10          varchar(100),
   PARA_CODE11          varchar(100),
   PARA_CODE12          varchar(100),
   PARA_CODE13          varchar(100),
   PARA_CODE14          varchar(100),
   PARA_CODE15          varchar(100),
   PARA_CODE16          varchar(100),
   PARA_CODE17          varchar(100),
   PARA_CODE18          varchar(100),
   PARA_CODE19          varchar(100),
   PARA_CODE20          varchar(100),
   PARA_CODE21          varchar(100),
   PARA_CODE22          varchar(100),
   PARA_CODE23          varchar(100),
   PARA_CODE24          varchar(100),
   PARA_CODE25          varchar(100),
   PARA_CODE26          varchar(100),
   PARA_CODE27          varchar(100),
   PARA_CODE28          varchar(100),
   PARA_CODE29          varchar(100),
   PARA_CODE30          varchar(100),
   REMARK               varchar(200),
   check ("DATADETAIL_ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: LAO_BATCH_DATADETAIL_INDEX1                           */
/*==============================================================*/
create index LAO_BATCH_DATADETAIL_INDEX1 on LAO_BATCH_DATADETAIL
(
   BATCH_ID,
   ICCID
);

/*==============================================================*/
/* Table: LAO_BATCH_DATADETAIL17_2_15                           */
/*==============================================================*/
create table LAO_BATCH_DATADETAIL17_2_15
(
   DATADETAIL_ID        numeric(16,0) not null comment '？？？？？？？？？？？？？？',
   BATCH_ID             numeric(16,0) not null comment '？？？？？？？？？？',
   TRADE_TYPE_CODE      numeric(4,0) comment '？？？？？？？？？？？？',
   CHANNEL_CUST_ID      numeric(16,0) comment '？？？？？？？？ID',
   CUST_ID              numeric(16,0) comment '？？？？ID',
   USER_ID              numeric(16,0) comment '？？？？ID',
   MSISDN               varchar(40) comment '？？？？？？？？',
   ICCID                varchar(50) comment 'ICCID',
   GOODS_ID             numeric(16,0) comment '？？？？ID',
   RECV_TIME            datetime comment '？？？？？？？？',
   FLOW_STATUS          varchar(2) comment '1？？？？？？？？？？？？？？2？？？？？？？？？？？？？？3？？？？？？？？？？？？？？4？？？？？？？？？？？？？？？？5？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？6？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？ 7 ？？？？？？？？？？？？？？？？',
   DEAL_TAG             char(1) comment '？？？？？？？？？？0-？？？？？？？？1-？？？？？？？？2-？？？？？？？？？？3-？？？？？？？？',
   RESULT_INFO          varchar(500) comment '？？？？？？？？',
   UPDATE_TIME          datetime comment '？？？？？？？？',
   TRADE_ID             numeric(16,0) comment '？？？？？？？？',
   OPER_ID              varchar(40) comment '？？？？？？？？',
   PARA_CODE1           varchar(100) comment 'para_code1-30？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？',
   PARA_CODE2           varchar(100),
   PARA_CODE3           varchar(100),
   PARA_CODE4           varchar(100),
   PARA_CODE5           varchar(100),
   PARA_CODE6           varchar(100),
   PARA_CODE7           varchar(100),
   PARA_CODE8           varchar(100),
   PARA_CODE9           varchar(100),
   PARA_CODE10          varchar(100),
   PARA_CODE11          varchar(100),
   PARA_CODE12          varchar(100),
   PARA_CODE13          varchar(100),
   PARA_CODE14          varchar(100),
   PARA_CODE15          varchar(100),
   PARA_CODE16          varchar(100),
   PARA_CODE17          varchar(100),
   PARA_CODE18          varchar(100),
   PARA_CODE19          varchar(100),
   PARA_CODE20          varchar(100),
   PARA_CODE21          varchar(100),
   PARA_CODE22          varchar(100),
   PARA_CODE23          varchar(100),
   PARA_CODE24          varchar(100),
   PARA_CODE25          varchar(100),
   PARA_CODE26          varchar(100),
   PARA_CODE27          varchar(100),
   PARA_CODE28          varchar(100),
   PARA_CODE29          varchar(100),
   PARA_CODE30          varchar(100),
   REMARK               varchar(200),
   primary key (DATADETAIL_ID),
   check ("DATADETAIL_ID" IS NOT NULL)
);

alter table LAO_BATCH_DATADETAIL17_2_15 comment '？？？？？？？？？？？？？？？？？？';

/*==============================================================*/
/* Index: IDX_DATADETAIL_BATCH_ID                               */
/*==============================================================*/
create index IDX_DATADETAIL_BATCH_ID on LAO_BATCH_DATADETAIL17_2_15
(
   BATCH_ID
);

/*==============================================================*/
/* Index: I_LAO_BATCH_DATADETAIL_ICCID                          */
/*==============================================================*/
create index I_LAO_BATCH_DATADETAIL_ICCID on LAO_BATCH_DATADETAIL17_2_15
(
   ICCID
);

/*==============================================================*/
/* Table: LAO_BATCH_DATADETAIL_17413                            */
/*==============================================================*/
create table LAO_BATCH_DATADETAIL_17413
(
   DATADETAIL_ID        numeric(16,0) not null,
   BATCH_ID             numeric(16,0) not null,
   TRADE_TYPE_CODE      numeric(4,0),
   CHANNEL_CUST_ID      numeric(16,0),
   CUST_ID              numeric(16,0),
   USER_ID              numeric(16,0),
   MSISDN               varchar(40),
   ICCID                varchar(50),
   GOODS_ID             numeric(16,0),
   RECV_TIME            datetime,
   FLOW_STATUS          varchar(2),
   DEAL_TAG             char(1),
   RESULT_INFO          varchar(500),
   UPDATE_TIME          datetime,
   TRADE_ID             numeric(16,0),
   OPER_ID              varchar(40),
   PARA_CODE1           varchar(100),
   PARA_CODE2           varchar(100),
   PARA_CODE3           varchar(100),
   PARA_CODE4           varchar(100),
   PARA_CODE5           varchar(100),
   PARA_CODE6           varchar(100),
   PARA_CODE7           varchar(100),
   PARA_CODE8           varchar(100),
   PARA_CODE9           varchar(100),
   PARA_CODE10          varchar(100),
   PARA_CODE11          varchar(100),
   PARA_CODE12          varchar(100),
   PARA_CODE13          varchar(100),
   PARA_CODE14          varchar(100),
   PARA_CODE15          varchar(100),
   PARA_CODE16          varchar(100),
   PARA_CODE17          varchar(100),
   PARA_CODE18          varchar(100),
   PARA_CODE19          varchar(100),
   PARA_CODE20          varchar(100),
   PARA_CODE21          varchar(100),
   PARA_CODE22          varchar(100),
   PARA_CODE23          varchar(100),
   PARA_CODE24          varchar(100),
   PARA_CODE25          varchar(100),
   PARA_CODE26          varchar(100),
   PARA_CODE27          varchar(100),
   PARA_CODE28          varchar(100),
   PARA_CODE29          varchar(100),
   PARA_CODE30          varchar(100),
   REMARK               varchar(200),
   primary key (DATADETAIL_ID),
   check ("DATADETAIL_ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: LAO_BATCH_DATADETAIL_PK_ICCID                         */
/*==============================================================*/
create index LAO_BATCH_DATADETAIL_PK_ICCID on LAO_BATCH_DATADETAIL_17413
(
   BATCH_ID,
   ICCID
);

/*==============================================================*/
/* Table: LAO_BATCH_FTP_IMPORT                                  */
/*==============================================================*/
create table LAO_BATCH_FTP_IMPORT
(
   TRADE_TYPE_CODE      numeric(4,0) not null comment '？？？？？？？？？？？？',
   TRADE_TYPE_NAME      varchar(50) comment '？？？？？？？？？？？？',
   HOST_IP              varchar(20) comment '？？？？？？？？？？？？',
   HOST_PORT            varchar(10) comment '？？？？？？？？',
   USER_NAME            varchar(20) comment '？？？？？？？？？？',
   PASSWD               varchar(50),
   PROC_NAME            varchar(40),
   FILE_NAME            varchar(50),
   FILE_PATH            varchar(100),
   BAK_FILE_PATH        varchar(100),
   REMARK               varchar(200),
   primary key (TRADE_TYPE_CODE),
   check ("TRADE_TYPE_CODE" IS NOT NULL)
);

alter table LAO_BATCH_FTP_IMPORT comment '？？？？ftp？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_BATCH_RESULT                                      */
/*==============================================================*/
create table LAO_BATCH_RESULT
(
   BATCH_ID             numeric(16,0) not null comment '？？？？？？？？？？',
   TRADE_TYPE_CODE      numeric(4,0) comment '？？？？？？？？？？？？',
   DEAL_TAG             char(1) comment '？？？？？？？？',
   RESULT_INFO          varchar(200) comment '？？？？？？？？？？？？',
   RECV_TIME            datetime comment '？？？？？？？？？？？？',
   UPDATE_TIME          datetime comment '？？？？？？？？',
   UPDATE_STAFF_ID      char(8) comment '？？？？？？？？？？',
   SUM_NUM              numeric(11,0) comment '？？？？？？',
   SUCC_NUM             numeric(11,0) comment '？？？？？？？？',
   FAIL_NUM             numeric(11,0) comment '？？？？？？？？',
   REMARK               varchar(200) comment '？？？？',
   primary key (BATCH_ID),
   check ("BATCH_ID" IS NOT NULL)
);

alter table LAO_BATCH_RESULT comment '？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_BILL_RESULT                                       */
/*==============================================================*/
create table LAO_BILL_RESULT
(
   BALANCE_ID           numeric(16,0) not null,
   CHARGE_ID            numeric(16,0) comment '？？？？？？？？？？？？？？？？LAO_F_PAYLOG？？？？CHARGE_ID',
   CHANNEL_CUST_ID      numeric(16,0),
   CYCLE_ID             numeric(6,0) comment '？？？？',
   BACK_FEE             numeric(11,0) comment '1-？？？？',
   BALANCE_TIME         datetime,
   LEFT_FEE             numeric(11,0) comment '？？？？？？？？？？',
   CANPAY_TAG           varchar(1) comment '？？？？？？？？？？？？,0-？？？？？？？？？？1-？？？？？？？？',
   CASHBACK_TAG         varchar(3) comment '？？？？？？？？
            0-？？？？？？
            1-？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？',
   CASH_TIME            datetime comment '？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？',
   CASH_CHARGE_ID       numeric(16,0) comment '？？？？？？？？？？？？？？',
   RECV_OPER_ID         varchar(40),
   RULE_ID              numeric(11,0),
   CANCEL_TAG           char(1),
   CANCEL_OPER_ID       varchar(40),
   CANCEL_TIME          datetime,
   CANCEL_CHARGE_ID     numeric(16,0),
   RSRV_INFO1           varchar(200),
   RSRV_INFO2           varchar(200) comment '？？？？？？？？？？0-？？？？？？？？1-？？？？？？',
   primary key (BALANCE_ID),
   check ("BALANCE_ID" IS NOT NULL)
);

alter table LAO_BILL_RESULT comment '？？？？？？';

/*==============================================================*/
/* Table: LAO_BILL_RESULT_HIS                                   */
/*==============================================================*/
create table LAO_BILL_RESULT_HIS
(
   BALANCE_ID           numeric(16,0) not null,
   CHARGE_ID            numeric(16,0) comment '？？？？？？？？？？？？？？？？LAO_F_PAYLOG？？？？CHARGE_ID',
   CHANNEL_CUST_ID      numeric(16,0),
   CYCLE_ID             numeric(6,0) comment '？？？？',
   BACK_FEE             numeric(11,0) comment '1-？？？？',
   BALANCE_TIME         datetime,
   LEFT_FEE             numeric(11,0) comment '？？？？？？？？？？',
   CANPAY_TAG           varchar(1) comment '？？？？？？？？？？？？,0-？？？？？？？？？？1-？？？？？？？？',
   CASHBACK_TAG         varchar(3) comment '？？？？？？？？
            0-？？？？？？
            1-？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？',
   CASH_TIME            datetime comment '？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？',
   CASH_CHARGE_ID       numeric(16,0) comment '？？？？？？？？？？？？？？',
   RECV_OPER_ID         varchar(40),
   RULE_ID              numeric(11,0),
   CANCEL_TAG           char(1),
   CANCEL_OPER_ID       varchar(40),
   CANCEL_TIME          datetime,
   CANCEL_CHARGE_ID     numeric(16,0),
   RSRV_INFO1           varchar(200),
   RSRV_INFO2           varchar(200) comment '？？？？？？？？？？0-？？？？？？？？1-？？？？？？',
   primary key (BALANCE_ID),
   check ("BALANCE_ID" IS NOT NULL)
);

alter table LAO_BILL_RESULT_HIS comment '？？？？？？';

/*==============================================================*/
/* Table: LAO_B_ACCESSLOG                                       */
/*==============================================================*/
create table LAO_B_ACCESSLOG
(
   ACCESS_ID            numeric(16,0) not null,
   CHARGE_ID            numeric(16,0),
   ACCT_BALANCE_ID      numeric(16,0),
   OLD_BALANCE          numeric(11,0),
   MONEY                numeric(11,0),
   NEW_BALANCE          numeric(11,0),
   INVOICE_FEE          numeric(11,0),
   ACCESS_TAG           char(1),
   RECV_OPER_ID         varchar(40),
   UPDATE_TIME          datetime,
   CANCEL_TAG           char(1) comment '0-？？？？？？1-？？？？',
   CANCEL_TIME          datetime,
   CANCEL_OPER_ID       varchar(40),
   CHANNEL_CUST_ID      numeric(16,0),
   CREATE_DATE          datetime,
   CHECKED_TAG          numeric(1,0),
   primary key (ACCESS_ID),
   check ("ACCESS_ID" IS NOT NULL)
);

alter table LAO_B_ACCESSLOG comment '？？？？？？？？？？？？？？？？？？？？？？？？dba？？？？？？？？？？？？？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_B_ACCESSLOG_20170526                              */
/*==============================================================*/
create table LAO_B_ACCESSLOG_20170526
(
   ACCESS_ID            numeric(16,0) not null,
   CHARGE_ID            numeric(16,0),
   ACCT_BALANCE_ID      numeric(16,0),
   OLD_BALANCE          numeric(11,0),
   MONEY                numeric(11,0),
   NEW_BALANCE          numeric(11,0),
   INVOICE_FEE          numeric(11,0),
   ACCESS_TAG           char(1),
   RECV_OPER_ID         varchar(40),
   UPDATE_TIME          datetime,
   CANCEL_TAG           char(1),
   CANCEL_TIME          datetime,
   CANCEL_OPER_ID       varchar(40),
   check ("ACCESS_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_B_BILL                                            */
/*==============================================================*/
create table LAO_B_BILL
(
   BILL_ID              numeric(16,0) not null comment '？？？？？？？？？？',
   PARTITION_ID         numeric(4,0) not null comment '？？？？？？？？',
   ACCT_ID              numeric(16,0) not null comment '？？？？ID',
   USER_ID              numeric(16,0) not null comment '？？？？ID',
   SERIAL_NUMBER        varchar(100) not null,
   ITEM_ID              numeric(6,0) not null comment '？？？？？？？？',
   CYCLE_ID             numeric(6,0) not null comment '？？？？？？？？',
   USE_COUNT            numeric(11,0) default 0,
   FEE                  numeric(11,0) default 0 comment '？？？？？？？？？？？？？？？？？？',
   BALANCE              numeric(11,0) default 0 comment '？？？？？？？？？？？？？？？？？？',
   B_DISCNT             numeric(11,0) default 0 comment '？？？？？？？？？？？？？？？？？？？？？？',
   A_DISCNT             numeric(11,0) default 0 comment '？？？？？？？？？？？？？？？？？？？？？？',
   ADJUST_BEFORE        numeric(11,0) default 0 comment '？？？？？？？？？？？？？？？？？？？？？？',
   ADJUST_AFTER         numeric(11,0) default 0 comment '？？？？？？？？？？？？？？？？？？？？？？',
   CANPAY_TAG           char(1) comment '？？？？？？？？？？？？？？1 ？？？？？？？？',
   BILL_PAY_TAG         char(1) comment '？？？？？？？？？？？？？？0？？？？？？ 1？？？？？？',
   RECV_TIME            datetime comment '？？？？？？？？',
   UPDATE_TIME          datetime comment '？？？？？？？？',
   UPDATE_STAFF_ID      varchar(40) comment '？？？？？？？？',
   RSRV_INFO1           varchar(200) comment '？？？？？？？？1',
   RSRV_INFO2           varchar(200) comment '？？？？？？？？2',
   primary key (BILL_ID),
   check ("BILL_ID" IS NOT NULL)
);

alter table LAO_B_BILL comment '？？？？？？';

/*==============================================================*/
/* Index: IDX_B_BILL_ACCT_ID                                    */
/*==============================================================*/
create index IDX_B_BILL_ACCT_ID on LAO_B_BILL
(
   ACCT_ID,
   PARTITION_ID
);

/*==============================================================*/
/* Index: IDX_B_BILL_USER_ID                                    */
/*==============================================================*/
create index IDX_B_BILL_USER_ID on LAO_B_BILL
(
   USER_ID,
   PARTITION_ID
);

/*==============================================================*/
/* Table: LAO_B_PAYLOG                                          */
/*==============================================================*/
create table LAO_B_PAYLOG
(
   CHARGE_ID            numeric(16,0) not null,
   CHANNEL_CUST_ID      numeric(16,0),
   CUST_ID              numeric(16,0),
   USER_ID              numeric(16,0),
   PAY_FEE_MODE_CODE    numeric(4,0) comment '1-？？？？',
   PAYMENT_OP           numeric(8,0) comment '10000  ？？？？
            10001  ？？？？
            10002  ？？？？？？？？
            10003  ？？？？
            10004      ？？？？',
   TRADE_ID             numeric(16,0),
   TRADE_TYPE_CODE      numeric(4,0) comment '100 ？？？？ 120？？？？ 130？？？？？？？？',
   PAY_TAG              char(1) comment '0-？？？？？？？？1-？？？？？？;2-？？？？？？？？？？',
   RECV_FEE             numeric(11,0),
   FEE                  numeric(11,0),
   DISCNT_FEE           numeric(11,0),
   REAL_FEE             numeric(11,0),
   RES_TYPE             varchar(6),
   RES_NUM              numeric(11,0),
   USER_ITEM1           varchar(100),
   USER_ITEM2           varchar(100),
   USER_ITEM3           varchar(100),
   USER_ITEM4           varchar(100),
   GOODS_ID             numeric(16,0),
   RECV_TIME            datetime,
   SALE_MANAGER         varchar(40),
   RECV_OPER_ID         varchar(40),
   FEECNT_TAG           char(1) comment '0-？？？？？？？？1-？？？？？？？？2-？？？？？？',
   FEECNT_TIME          datetime,
   REMARK               varchar(100),
   CANCEL_TAG           char(1) comment '0-？？？？？？1-？？？？？？',
   CANCEL_OPER_ID       varchar(40),
   CANCEL_TIME          datetime,
   RSRV_INFO1           varchar(100),
   RSRV_INFO2           varchar(100),
   primary key (CHARGE_ID),
   check ("CHARGE_ID" IS NOT NULL)
);

alter table LAO_B_PAYLOG comment '？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_B_PAYRELATION                                     */
/*==============================================================*/
create table LAO_B_PAYRELATION
(
   PAYRELATION_ID       numeric(16,0) not null comment '？？？？？？？？ID',
   USER_ID              numeric(16,0) not null comment '？？？？ID',
   ACCT_ID              numeric(16,0) not null comment '？？？？ID',
   PAY_PRIORITY         numeric(4,0) comment '？？？？？？？？？？？？？？',
   LIMIT_TYPE           char(1) comment '？？？？？？？？？？？？？？？？',
   LIMIT_VALUE          numeric(11,0) comment '？？？？？？？？？？？？？？？？',
   START_CYC_ID         numeric(6,0) comment '？？？？？？？？',
   END_CYC_ID           numeric(6,0) comment '？？？？？？？？',
   DEFAULT_TAG          char(1) comment '？？？？？？？？',
   VALID_TAG            char(1) comment '？？？？？？？？',
   UPDATE_TIME          datetime comment '？？？？？？？？',
   UPDATE_STAFF_ID      char(8) comment '？？？？？？？？',
   RSRV_INFO1           varchar(200) comment '？？？？？？？？1',
   RSRV_INFO2           varchar(200) comment '？？？？？？？？2',
   primary key (PAYRELATION_ID),
   check ("PAYRELATION_ID" IS NOT NULL)
);

alter table LAO_B_PAYRELATION comment '？？？？？？？？？？';

/*==============================================================*/
/* Index: IDX_B_PAYRELATION_ACCT_ID                             */
/*==============================================================*/
create index IDX_B_PAYRELATION_ACCT_ID on LAO_B_PAYRELATION
(
   ACCT_ID
);

/*==============================================================*/
/* Index: IDX_B_PAYRELATION_USER_ID                             */
/*==============================================================*/
create index IDX_B_PAYRELATION_USER_ID on LAO_B_PAYRELATION
(
   USER_ID
);

/*==============================================================*/
/* Table: LAO_CARDLIFE_CHG                                      */
/*==============================================================*/
create table LAO_CARDLIFE_CHG
(
   ID                   numeric(16,0) not null,
   MSISDN               varchar(50),
   ICCID                varchar(50),
   OLD_STATE_CODE       char(1) not null,
   NEW_STATE_CODE       char(1) not null,
   UPDATE_TIME          datetime,
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_CHARGE_ORDER                                      */
/*==============================================================*/
create table LAO_CHARGE_ORDER
(
   ID                   numeric(8,0) not null comment '？？？？ID',
   USERNAME             varchar(100) comment '？？？？？？？？？？？？？？？？？？？？？？',
   USERID               varchar(100) comment '？？？？ID',
   ICCID                varchar(50) not null comment 'Iccid',
   ORDERID              varchar(50) not null comment '？？？？？？',
   FLOWSIZE             varchar(30) comment '？？？？？？？？？？',
   PAYAMOUNT            numeric(8,0) not null comment '？？？？？？？？  ？？？？？？？？？？',
   PAYSTATUS            numeric(8,0) default 3 comment '？？？？？？？？:  1 ？？？？？？？？;  2？？？？？？？？; 3？？？？？？;',
   CHARGESTATUS         numeric(8,0) comment '？？？？？？？？:  1 ？？？？？？？？;  -1 ？？？？？？？？ ',
   CREATEDATE           timestamp not null comment '？？？？？？？？',
   UPDATEDATE           timestamp comment '？？？？？？？？',
   CHARGEDATE           timestamp comment '？？？？？？？？',
   PAYTYPE              numeric(8,0) comment '？？？？？？？？   1 ？？？？？？？？？？ ',
   PAYORDERID           varchar(100) comment '？？？？？？？？？？',
   primary key (ID),
   check ("ID" IS NOT NULL)
);

alter table LAO_CHARGE_ORDER comment '？？？？？？？？？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_CUSTOMER                                          */
/*==============================================================*/
create table LAO_CUSTOMER
(
   CUST_ID              numeric(16,0) not null,
   PARTITION_ID         numeric(4,0),
   CUST_NAME            varchar(100) not null,
   CUST_TYPE            char(1) not null,
   CUST_STATE           char(1) not null,
   PSPT_TYPE_CODE       char(1),
   PSPT_ID              varchar(100),
   REMOVE_DATE          datetime,
   REMOVE_CHANGE        varchar(100),
   RSRV_STR1            varchar(100),
   RSRV_DATE1           datetime,
   IN_DATE              datetime not null,
   IN_STAFF_ID          varchar(100),
   PARENT_ID            numeric(16,0),
   DEV_CUST             numeric(16,0),
   DEV_ACT              numeric(16,0),
   primary key (CUST_ID),
   check ("CUST_ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: IDX_LAO_CUSTOMER_RSRV_STR1                            */
/*==============================================================*/
create index IDX_LAO_CUSTOMER_RSRV_STR1 on LAO_CUSTOMER
(
   RSRV_STR1
);

/*==============================================================*/
/* Table: LAO_CUSTOMER_NOTICE                                   */
/*==============================================================*/
create table LAO_CUSTOMER_NOTICE
(
   NOTICEID             numeric(16,0) not null comment '？？？？ID',
   CHANNEL_CUST_ID      numeric(16,0) comment 'channelCustID',
   CUST_ID              numeric(16,0) comment 'custID',
   USER_ID              numeric(16,0) comment '？？？？ID',
   NOTICE_TYPE          varchar(50) comment '？？？？？？？？',
   NOTICE_TITLE         varchar(50) comment '？？？？？？？？',
   NOTICE_CONTENT       varchar(500) comment '？？？？？？？？',
   CREATOR              numeric(16,0) comment '？？？？？？？？？？',
   START_TIME           datetime comment '？？？？？？？？？？？？',
   NOTICEFLAG           numeric(1,0) comment '？？？？？？？？？？？？',
   END_TIME             datetime comment '？？？？？？？？？？？？',
   CREATE_TIME          datetime default 'SYSDATE' comment '？？？？？？？？？？？？',
   UPDATETIME           datetime comment '？？？？？？？？？？？？',
   PARAMETER1           varchar(50),
   PARAMETER2           varchar(50),
   PARAMETER3           varchar(50),
   CLICK_NUMBER         numeric(8,0) default 0 comment '？？？？？？？？？？？？',
   primary key (NOTICEID),
   check ("NOTICEID" IS NOT NULL)
);

alter table LAO_CUSTOMER_NOTICE comment '？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_CUSTOMER_STYLE                                    */
/*==============================================================*/
create table LAO_CUSTOMER_STYLE
(
   CUST_ID              numeric(16,0) not null,
   CUST_NAME            varchar(100) not null,
   CUST_LOGO            varchar(100) not null,
   CUST_STYLE           varchar(100),
   CREAT_DATE           datetime,
   ISDISABLED           numeric(1,0) not null default 0,
   PARA_NAME1           varchar(100),
   PARA_NAME2           varchar(100),
   check ("CUST_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_CUSTOMER_VERIFY                                   */
/*==============================================================*/
create table LAO_CUSTOMER_VERIFY
(
   CUST_ID              numeric(16,0),
   ID                   numeric(8,0),
   IDTYPE               numeric(1,0),
   REALNAME             varchar(30),
   IDNUM                varchar(50),
   HANDPICURL           varchar(256),
   FRONTPICURL          varchar(256),
   BACKPICURL           varchar(256),
   TEL                  varchar(30),
   VERIFYSTATUS         numeric(1,0),
   FAILTIMES            numeric(10,0),
   CREATETIME           datetime,
   UPDATETIME           datetime,
   ICCID                varchar(100),
   PHOTOURL             varchar(256),
   ACCOUNT_ID           numeric(16,0)
);

/*==============================================================*/
/* Table: LAO_CUST_CONFIG                                       */
/*==============================================================*/
create table LAO_CUST_CONFIG
(
   ID                   numeric(16,0) not null comment '？？？？ID',
   CUST_ID              numeric(16,0) comment '？？？？ID',
   VISTS                varchar(10) comment '？？？？？？？？？？？？？？',
   IS_TAG               varchar(2) comment '？？？？？？？？？？？？？？？？:  1 ？？？？;  2？？？？',
   CREATEDATE           datetime comment '？？？？？？？？',
   IS_RANDOM_CHECK      varchar(2) comment '？？？？？？？？？？？？？？？？？？:  1 ？？？？;  2？？？？',
   IS_IP_CHECK          varchar(2) comment 'ip？？？？？？？？？？？？:  1 ？？？？;  2？？？？',
   IS_LIMIT_CUST_VISTS  varchar(2) comment '？？？？？？？？？？？？？？？？:  1 ？？？？;  2？？？？？？',
   PARA_NAME1           varchar(100) comment '？？？？？？？？',
   PARA_NAME2           varchar(100) comment '？？？？？？？？？？？？get/post',
   SERVER_NAME          varchar(50) comment '？？？？？？？？？？',
   SERVER_NAME_LIMIT    varchar(10) comment '？？？？？？？？？？？？？？？？？？？？？？？？？？？？',
   SENDSMS_CALLBACKURL  varchar(100) comment '？？？？？？？？？？？？',
   check ("ID" IS NOT NULL)
);

alter table LAO_CUST_CONFIG comment '？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_CUST_CONTACT                                      */
/*==============================================================*/
create table LAO_CUST_CONTACT
(
   CONTACT_ID           numeric(16,0) not null,
   CUST_ID              numeric(16,0),
   CONTACT_NAME         varchar(100),
   CONTACT_PHONE        varchar(15),
   CONTACT_FAX          varchar(15),
   CONTACT_EMAIL        varchar(50),
   CONTACT_POST_CODE    char(6),
   CONTACT_POST_ADDR    varchar(80),
   CONTACT_HOME_ADDR    varchar(80),
   CONTACT_PSPT_TYPE_CODE char(1),
   CONTACT_PSPT_ID      varchar(30),
   CONTACT_WORK_NAME    varchar(50),
   CONTACT_WORK_ADDR    varchar(80),
   CONTACT_DEPART       varchar(50),
   CONTACT_DUTY         varchar(50),
   BEST_CONTACT_TIME    varchar(50),
   UPDATE_TIME          datetime,
   UPDATE_STAFF_ID      varchar(20),
   UPDATE_DEPART_ID     varchar(10),
   REMARK               varchar(200),
   RSRV_NUM1            numeric(5,0),
   RSRV_STR1            varchar(20),
   RSRV_DATE1           datetime,
   RSRV_TAG1            char(1),
   IS_PRIMARY           varchar(1),
   primary key (CONTACT_ID),
   check ("CONTACT_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_CUST_GROUP                                        */
/*==============================================================*/
create table LAO_CUST_GROUP
(
   CUST_ID              numeric(16,0) not null,
   PARTITION_ID         numeric(4,0),
   GROUP_ID             varchar(40),
   CUST_NAME            varchar(100) not null,
   GROUP_TYPE           char(2),
   CUST_CLASS_TYPE      char(10),
   GROUP_STATUS         char(1),
   GROUP_ADDR           varchar(200),
   PROVINCE_CODE        varchar(100),
   EPARCHY_CODE         varchar(100),
   UNIFY_PAY_CODE       varchar(30),
   ORG_STRUCT_CODE      varchar(30),
   BUSI_LICENCE_TYPE    char(1) not null,
   BUSI_LICENCE_NO      varchar(100) not null,
   BUSI_LICENCE_VALID_DATE datetime not null,
   GROUP_MEMO           varchar(500),
   INTERNATIONAL        char(1),
   JURISTIC_PSPT_ID     varchar(20),
   JURISTIC_PSPT_TYPE   char(1),
   BUSI_TAX_ID          varchar(50),
   API_KEY              varchar(50),
   SELL_TYPE            varchar(2),
   REGION_CODE          varchar(32),
   COUNTRY_SEAT         varchar(100),
   ACCT_STATUS          varchar(1),
   BUSI_TAG1            varchar(32),
   BUSI_TAG2            varchar(32),
   BUSI_TAG3            varchar(32),
   primary key (CUST_ID),
   check ("CUST_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_CUST_PERSON                                       */
/*==============================================================*/
create table LAO_CUST_PERSON
(
   CUST_ID              numeric(16,0) not null,
   PSPT_TYPE_CODE       char(1) not null,
   PSPT_ID              varchar(100) not null,
   PSPT_END_DATE        datetime,
   PSPT_ADDR            varchar(80),
   CUST_NAME            varchar(100) not null,
   SEX                  char(1),
   BIRTHDAY             datetime,
   BIRTHDAY_LUNAR       varchar(50),
   BIRTHDAY_FLAG        char(1),
   POST_ADDRESS         varchar(80),
   POST_CODE            char(6),
   POST_PERSON          varchar(50),
   PHONE                varchar(100),
   FAX_NBR              varchar(40),
   EMAIL                varchar(50),
   NATIONALITY_CODE     varchar(2),
   LOCAL_NATIVE_CODE    varchar(4),
   GRADUATE_SCHOOL      varchar(100),
   SPECIALITY           varchar(50),
   UPDATE_TIME          datetime,
   UPDATE_STAFF_ID      varchar(20),
   UPDATE_DEPART_ID     varchar(10),
   REMARK               varchar(500),
   RSRV_NUM1            numeric(5,0),
   RSRV_STR1            varchar(20),
   RSRV_DATE3           datetime,
   RSRV_TAG3            char(1),
   primary key (CUST_ID),
   check ("CUST_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_DATAIMPORT_LOG                                    */
/*==============================================================*/
create table LAO_DATAIMPORT_LOG
(
   IMP_ID               numeric(16,0) not null comment '？？？？？？？？？？？？',
   IMP_TYPE             varchar(4) comment '？？？？？？？？？？？？？？1-？？？？？？？？？？2-？？？？？？？？？？？？？？？？？？',
   IMP_NAME             varchar(100) comment '？？？？？？？？？？？？',
   IMP_DATE             varchar(8) comment '？？？？？？？？？？？？？？yyyymmdd',
   OPERATOR_TYPE        varchar(20) comment '？？？？？？？？？？？？？？',
   OPERATOR_NAME        varchar(50) comment '？？？？？？？？',
   OPERATORS_ID         numeric(8,0) comment '？？？？？？id,？？？？lao_operators.operators_id',
   UPDATE_TIME          datetime comment '？？？？？？？？',
   UPDATE_STAFF         varchar(50) comment '？？？？？？？？',
   REMARK               varchar(100) comment '？？？？',
   primary key (IMP_ID),
   check ("IMP_ID" IS NOT NULL)
);

alter table LAO_DATAIMPORT_LOG comment '？？？？？？？？？？？？？？？？？？？？？？';

/*==============================================================*/
/* Index: IDX_DATAIMPORT_IMP_DATE                               */
/*==============================================================*/
create index IDX_DATAIMPORT_IMP_DATE on LAO_DATAIMPORT_LOG
(
   IMP_DATE
);

/*==============================================================*/
/* Table: LAO_DEVICEDATA_INFO                                   */
/*==============================================================*/
create table LAO_DEVICEDATA_INFO
(
   ID                   numeric(16,0) not null comment '？？？？ID',
   ICCID                varchar(50) comment 'iccid',
   IMEI                 varchar(50) comment '？？？？IMEI？？',
   IMSI                 varchar(50) comment '？？？？IMSI？？',
   PHONENUMBER          varchar(20) comment '？？？？？？',
   SIMSTATE             numeric(5,0) comment '？？？？？？',
   NETWORKOPERATORNAME  varchar(50) comment '？？？？？？？？？？',
   BOARD                varchar(20) comment '？？？？',
   BRAND                varchar(20) comment '？？？？？？？？？？',
   MANUFACTURER         varchar(20) comment '？？？？？？？？？？',
   SERIAL               varchar(50) comment '？？？？？？？？？？',
   PRODUCT              varchar(20) comment '？？？？？？？？？？',
   SDK_VERSION          varchar(20) comment '？？？？？？？？',
   BATTERYLEVEL         numeric(5,0) comment '？？？？？？？？？？[0-100]',
   ELAPSEDTIME          numeric(20,0) comment '？？？？？？？？？？？？？？？？？？？？？？？？',
   CREATETIME           datetime not null default 'SYSDATE' comment '？？？？？？？？',
   UPDATETIME           datetime comment '？？？？？？？？',
   DEVICETYPE           numeric(5,0) comment '？？？？？？？？',
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_DEVICEPRODUCT_AD                                  */
/*==============================================================*/
create table LAO_DEVICEPRODUCT_AD
(
   ADID                 varchar(40) not null comment '？？？？ID',
   IMGURL               varchar(255) comment '？？？？？？？？？？？？',
   ADPRICE              varchar(20) comment '？？？？',
   ADNAME               varchar(20) comment '？？？？',
   ADLINKURL            varchar(255) comment '？？？？？？？？',
   ADLINTRODUCE         varchar(255) comment '？？？？？？？？',
   ISSHOW               varchar(2) default '1' comment '？？？？？？？？？？？？？？1？？？？？？ 0？？？？？？？？ ？？？？？？1',
   PARANAME1            varchar(100),
   PARANAME2            varchar(100),
   primary key (ADID),
   check ("ADID" IS NOT NULL)
);

alter table LAO_DEVICEPRODUCT_AD comment '？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_DEVICE_REL                                        */
/*==============================================================*/
create table LAO_DEVICE_REL
(
   REL_ID               numeric(16,0) not null,
   USER_ID              numeric(16,0),
   ICCID                varchar(100),
   ID_TYPE              varchar(100) comment '？？？？？？？？？？？？？？？？lao_ss_static？？？？？？static_code？？？？
            ？？？？？？？？？？？？？？？？IMEI,IMSI,？？？？？？？？',
   DEVICE_ID            varchar(50),
   REL_TYPE             varchar(4) comment '1000-lenovoID？？1001-？？？？ID？？1002-？？？？？？？？1003-QQ？？1004-？？？？？？？？LAO_ACCOUNT_REL？？REL_TYPE',
   REL_ACCOUNT          varchar(50),
   VALID_TAG            char(1) comment '0-？？？？？？1-？？？？',
   RECV_TIME            datetime,
   OPER_ID              varchar(40),
   UPDATE_TIME          datetime,
   REMARK               varchar(200),
   REL_LOGINGNAME       varchar(50),
   primary key (REL_ID),
   check ("REL_ID" IS NOT NULL)
);

alter table LAO_DEVICE_REL comment '？？？？？？？？？？？？';

/*==============================================================*/
/* Index: LAO_DEVICE_REL_ICCID                                  */
/*==============================================================*/
create index LAO_DEVICE_REL_ICCID on LAO_DEVICE_REL
(
   ICCID,
   ID_TYPE,
   VALID_TAG
);

/*==============================================================*/
/* Table: LAO_DISCONT                                           */
/*==============================================================*/
create table LAO_DISCONT
(
   DISCONT_ID           numeric(8,0) not null,
   DISCONT_NAME         varchar(100),
   CREATEDATE           datetime,
   UPDATEDATE           datetime,
   STARTDATE            datetime,
   ENDDATE              datetime,
   DISCONT_TYPE         char(1),
   DISCONT_VALUE        numeric(8,0),
   primary key (DISCONT_ID),
   check (DISCONT_TYPE is null or (DISCONT_TYPE in ('1','2')))
);

/*==============================================================*/
/* Table: LAO_DMPCARD_DATA                                      */
/*==============================================================*/
create table LAO_DMPCARD_DATA
(
   ID                   numeric(16,0) not null comment '？？？？ID',
   ICCID                varchar(50) comment 'iccid',
   IMEI                 varchar(50) not null comment '？？？？IMEI？？',
   IMSI                 varchar(50) comment '？？？？IMSI？？',
   PHONENUMBER          varchar(20) comment '？？？？？？',
   SIMSTATE             numeric(5,0) comment '？？？？？？',
   NETWORKOPERATORNAME  varchar(50) comment '？？？？？？？？？？',
   DEVICETYPE           numeric(5,0) default 10000 comment '？？？？？？？？？？？？？？？？？？？？ 0: android   1: windows',
   CREATETIME           datetime not null default 'SYSDATE' comment '？？？？？？？？/？？？？？？？？',
   STARTTIME            datetime comment '？？？？？？？？',
   ENDTIME              datetime not null default to_date('2050/12/31','yyyy/mm/dd') comment '？？？？？？？？',
   UPDATETIME           datetime comment '？？？？？？？？',
   FLAG                 numeric(2,0) default 0 comment '？？？？？？？？ 0？？？？？？？？1？？？？',
   TRIGGEDREASON        numeric(2,0) comment '？？？？？？？？？？？？？？？？？？？？？？ 0？？？？？？？？？？？？？？？？？？？？1？？？？？？？？？？？？？？？？2 ？？？？？？？？？？？？？？？？？？3？？？？？？？？？？？？？？？？？？？？',
   CUSTID               numeric(16,0) comment '？？？？？？？？custId',
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_DMPDEVICE_ATTRIBUTE                               */
/*==============================================================*/
create table LAO_DMPDEVICE_ATTRIBUTE
(
   CARDDATAID           varchar(50) not null comment '？？？？？？lao_dmpdevice_attribute？？？？？？id',
   ATTRIBUTENAME        varchar(50) comment 'dmp？？？？？？',
   ATTRIBUTEVALUE       varchar(50) comment '？？？？？？',
   check ("CARDDATAID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_DMPPOSITION_DATA                                  */
/*==============================================================*/
create table LAO_DMPPOSITION_DATA
(
   ID                   numeric(16,0) not null comment '主键ID',
   IMEI                 varchar(50) comment 'imei',
   LATITUDE             numeric(10,7) comment '？？？？',
   LONGTITUDE           numeric(10,7) comment '？？？？',
   ADDRESS              varchar(200) comment '？？？？？？？？？？？？',
   SPEED                numeric(5,3) comment '？？？？',
   ERRORINFO            varchar(200) comment '？？？？？？？？？？？？？？？？？？？？',
   MCC                  varchar(5) comment '？？？？？？？？？？？？？？？？？？460',
   MNC                  varchar(5) comment '？？？？？？？？？？？？？？？？？？？？？？00？？02？？04？？07？？？？？？？？01？？06？？09？？？？？？？？03？？05？？11',
   MBASESTATIONID       numeric(5,0) comment '？？？？？？？？？？？？',
   MSYSTEMID            numeric(5,0) comment '？？？？？？？？？？？？',
   MNETWORKID           numeric(5,0) comment '？？？？？？？？？？？？',
   LAC                  numeric(10,0) comment '？？？？？？',
   CID                  numeric(10,0) comment '？？？？？？',
   REGISTEREDSTATE      varchar(10) comment '？？？？？？？？？？？？？？？？？？？？？？',
   CREATETIME           datetime not null default 'SYSDATE' comment '？？？？？？？？/？？？？？？？？',
   UPDATETIME           datetime comment '？？？？？？？？',
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_DMPSCHEME_GROUP                                   */
/*==============================================================*/
create table LAO_DMPSCHEME_GROUP
(
   SCHEME_ID            numeric(16,0) comment '？？？？？？？？？？？？？？？？？？ID',
   GROUP_ID             numeric(16,0) comment '？？？？？？？？？？？？？？？？？？？？？？？？ID',
   DELFLAG              varchar(5) comment '0？？？？？？1？？？？',
   CREATETIME           datetime not null default 'SYSDATE' comment '？？？？？？？？',
   UPDATETIME           datetime comment '？？？？？？？？',
   check ("CREATETIME" IS NOT NULL)
);

alter table LAO_DMPSCHEME_GROUP comment '？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_DMPSCHEME_STRATEGY                                */
/*==============================================================*/
create table LAO_DMPSCHEME_STRATEGY
(
   SCHEME_ID            numeric(16,0) comment '？？？？？？？？？？？？？？？？？？ID',
   STRATEGY_ID          numeric(16,0) comment '？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？ID',
   DELFLAG              varchar(5) comment '0？？？？？？1？？？？',
   CREATETIME           datetime not null default 'SYSDATE' comment '？？？？？？？？',
   UPDATETIME           datetime comment '？？？？？？？？',
   check ("CREATETIME" IS NOT NULL)
);

alter table LAO_DMPSCHEME_STRATEGY comment '？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_DMPSTRATEGY_EDIT                                  */
/*==============================================================*/
create table LAO_DMPSTRATEGY_EDIT
(
   ID                   numeric(16,0) not null comment '？？？？ID',
   CHANNELCUST_ID       numeric(16,0) comment '？？？？？？？？custId',
   TARGITTYPE           varchar(5) comment '1？？？？？？？？？？？？？？？？2？？？？？？？？？？？？',
   SCHEME_NAME          varchar(50) comment '？？？？？？？？？？？？？？？？？？',
   SCHEME_COMMENT       varchar(500) comment '？？？？？？？？？？？？？？？？？？',
   DELFLAG              varchar(5) comment '0？？？？？？1？？？？,2？？？？',
   CREATETIME           datetime not null default 'SYSDATE' comment '？？？？？？？？',
   UPDATETIME           datetime comment '？？？？？？？？',
   primary key (ID),
   check ("ID" IS NOT NULL)
);

alter table LAO_DMPSTRATEGY_EDIT comment '？？？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_DMPSTRATEGY_OPERATION                             */
/*==============================================================*/
create table LAO_DMPSTRATEGY_OPERATION
(
   STRATEGY_ID          numeric(16,0) comment '？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？ID',
   OPERATION_ID         numeric(16,0) comment '？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？ID',
   DELFLAG              varchar(5) comment '0？？？？？？1？？？？',
   CREATETIME           datetime not null default 'SYSDATE' comment '？？？？？？？？',
   UPDATETIME           datetime comment '？？？？？？？？',
   SCHEME_ID            numeric(16,0) comment '？？？？？？？？？？？？？？ID',
   check ("CREATETIME" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_DMPSTRATEGY_RELATION                              */
/*==============================================================*/
create table LAO_DMPSTRATEGY_RELATION
(
   CUST_ID              numeric(16,0),
   SCHEME_ID            numeric(16,0) comment '？？？？？？？？？？？？？？ID',
   STRATEGY_ID          numeric(16,0) comment '？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？ID',
   OPERATION_ID         numeric(16,0) comment '？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？ID',
   DELFLAG              varchar(5) comment '0？？？？？？1？？？？',
   CREATETIME           datetime not null default 'SYSDATE' comment '？？？？？？？？',
   UPDATETIME           datetime comment '？？？？？？？？',
   check ("CREATETIME" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_DMP_CARDGROUP                                     */
/*==============================================================*/
create table LAO_DMP_CARDGROUP
(
   IMEI                 varchar(50) not null comment '？？？？IMEI',
   GROUP_ID             numeric(16,0) comment '？？ID',
   CREATTIME            datetime default 'SYSDATE',
   UPDATETIME           datetime,
   FLAG                 numeric(2,0) default 0 comment '0？？？？  1？？？？',
   check ("IMEI" IS NOT NULL)
);

alter table LAO_DMP_CARDGROUP comment '？？？？IMEI？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_DMP_GROUP                                         */
/*==============================================================*/
create table LAO_DMP_GROUP
(
   ID                   numeric(16,0) not null comment '？？？？ID',
   GROUP_NAME           varchar(200) comment '？？？？',
   GROUP_COMMENT        varchar(500) comment '？？？？？？',
   CHANNEL_CUSTID       numeric(16,0) comment '？？？？？？？？custid',
   DELFLAG              varchar(20) default '1' comment '0？？？？？？1？？？？',
   CREATETIME           datetime not null default 'SYSDATE' comment '？？？？？？？？？？',
   UPDATETIME           datetime comment '？？？？？？？？？？',
   primary key (ID),
   check ("ID" IS NOT NULL)
);

alter table LAO_DMP_GROUP comment '？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_DMP_LOG                                           */
/*==============================================================*/
create table LAO_DMP_LOG
(
   ID                   numeric(16,0) not null comment '？？？？ID',
   CUST_ID              numeric(16,0) comment '？？？？custID',
   GROUP_ID             numeric(16,0) comment '？？ID',
   IMEI                 varchar(50) comment '？？？？IMEI',
   STRATEGY_ID          numeric(16,0) comment '？？？？ID',
   TRIGGER_CAUSE        varchar(50) comment '？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？,？？？？id？？？？',
   IS_AGAINST           varchar(5) comment '？？？？？？？？ 0？？？？？？？？ 1？？？？',
   OPERATION_ID         numeric(16,0) comment '？？？？ID',
   IS_SUCCESS           varchar(5) comment '？？？？？？？？？？？？,0？？？？？？1？？？？',
   OPERATE_TIME         datetime not null default 'SYSDATE' comment '？？？？？？？？？？？？？？？？？？？？？？？？',
   OPERATE_TYPE         varchar(20) comment '？？？？？？？？？？？？？？？？？？？？storm',
   OPERATE_USER         varchar(50) comment '？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？session？？？？？？？？？？？？？？？？？？？？storm？？？？？？？？？？？？？？storm',
   OPERATION_COMMENT    varchar(200) comment '？？？？？？？？？？？？？？？？ID？？？？？？？？？？？？？？？？？？',
   primary key (ID),
   check ("ID" IS NOT NULL)
);

alter table LAO_DMP_LOG comment 'dmp？？？？？？';

/*==============================================================*/
/* Table: LAO_DMP_OPERATION                                     */
/*==============================================================*/
create table LAO_DMP_OPERATION
(
   ID                   numeric(16,0) not null comment '？？？？ID',
   OPERATION_NAME       varchar(200) comment '？？？？？？',
   OPERATION_COMMENT    varchar(500) comment '？？？？？？？？',
   OPERATORIMPL         varchar(50) comment '？？？？？？？？？？？？',
   DELFLAG              varchar(5) comment '0？？？？？？1？？？？',
   CREATETIME           datetime not null default 'SYSDATE' comment '？？？？？？？？？？',
   UPDATETIME           datetime comment '？？？？？？？？？？',
   PARAMETER1           varchar(200) comment '？？？？？？？？？？？？？？？？？？？？？？？？？？？？',
   PARAMETER2           varchar(200),
   PARAMETER3           varchar(200),
   PARAMETER4           varchar(200),
   primary key (ID),
   check ("ID" IS NOT NULL)
);

alter table LAO_DMP_OPERATION comment '？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_DMP_STRATEGY                                      */
/*==============================================================*/
create table LAO_DMP_STRATEGY
(
   ID                   numeric(16,0) not null comment '？？？？ID',
   STRATEGY_NAME        varchar(200) comment '？？？？？？',
   STATEGY_COMMENT      varchar(500) comment '？？？？？？？？',
   STRATEGYIMPL         varchar(50) comment '？？？？？？？？？？？？？？？？？？？？？？？？？？',
   DELFLAG              varchar(5) comment '0？？？？？？1？？？？,2？？？？',
   CREATETIME           datetime not null default 'SYSDATE' comment '？？？？？？？？？？',
   UPDATETIME           datetime comment '？？？？？？？？？？',
   PARAMETER1           varchar(200),
   PARAMETER2           varchar(200),
   PARAMETER3           varchar(200),
   PARAMETER4           varchar(200),
   primary key (ID),
   check ("ID" IS NOT NULL)
);

alter table LAO_DMP_STRATEGY comment '？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_DOWNLOADFILE_CONFIG                               */
/*==============================================================*/
create table LAO_DOWNLOADFILE_CONFIG
(
   ID                   numeric(16,0) not null,
   FILEPREFIX_NAME      varchar(100),
   FILESUFFIX_DATE      datetime,
   ISDOWNLOAD           numeric(1,0) not null default 0,
   PARA_NAME1           varchar(50),
   PARA_NAME2           varchar(50),
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_D_COMMPARA                                        */
/*==============================================================*/
create table LAO_D_COMMPARA
(
   PARA_ID              numeric(6,0) not null comment '？？？？ID',
   PARA_NAME            varchar(100) comment '？？？？？？？？',
   PARA_TYPE            varchar(50) not null comment '？？？？？？？？',
   PARA_CODE1           varchar(200) comment '？？？？？？1',
   PARA_CODE2           varchar(200) comment '？？？？？？2',
   PARA_CODE3           varchar(200) comment '？？？？？？3',
   PARA_CODE4           varchar(200) comment '？？？？？？4',
   PARA_CODE5           varchar(200) comment '？？？？？？5',
   PARA_CODE6           varchar(200) comment '？？？？？？6',
   RSRV_INFO1           varchar(200) comment '？？？？？？？？1',
   RSRV_INFO2           varchar(200) comment '？？？？？？？？2',
   primary key (PARA_TYPE, PARA_ID),
   check ("PARA_ID" IS NOT NULL)
);

alter table LAO_D_COMMPARA comment '？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_D_ITEM                                            */
/*==============================================================*/
create table LAO_D_ITEM
(
   ITEM_ID              numeric(6,0) not null comment '？？？？ID',
   ITEM_NAME            varchar(100) comment '？？？？？？？？',
   ITEM_DESC            varchar(100) comment '？？？？？？？？',
   P_ITEM_ID            numeric(6,0) comment '？？？？？？？？？？？？',
   START_CYCLE_ID       numeric(6,0) comment '？？？？？？？？',
   END_CYCLE_ID         numeric(6,0) comment '？？？？？？？？',
   TAX_RATE             numeric(6,2) comment '？？？？',
   REMARK               varchar(200) comment '？？？？',
   primary key (ITEM_ID),
   check ("ITEM_ID" IS NOT NULL)
);

alter table LAO_D_ITEM comment '？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_D_OBJECT                                          */
/*==============================================================*/
create table LAO_D_OBJECT
(
   OBJECT_ID            numeric(8,0) not null comment '？？？？？？？？',
   OBJECT_NAME          varchar(100) comment '？？？？？？？？',
   ATTR_TYPE            char(1) not null comment '？？？？？？？？？？0？？？？',
   ATTR_ID              numeric(6,0) not null comment '？？？？ID',
   REMARK               varchar(200) comment '？？？？',
   primary key (ATTR_ID, ATTR_TYPE, OBJECT_ID),
   check ("OBJECT_ID" IS NOT NULL)
);

alter table LAO_D_OBJECT comment '？？？？？？？？？？';

/*==============================================================*/
/* Index: IDX_D_OBJECT_ATTR                                     */
/*==============================================================*/
create index IDX_D_OBJECT_ATTR on LAO_D_OBJECT
(
   ATTR_ID,
   ATTR_TYPE
);

/*==============================================================*/
/* Table: LAO_ESIM_DETAIL                                       */
/*==============================================================*/
create table LAO_ESIM_DETAIL
(
   ID                   numeric(16,0) not null comment '？？？？id',
   LENOVOID             varchar(20) comment 'lenovoID',
   IMEI                 varchar(100) comment '？？？？？？',
   STARTDATE            datetime comment '？？？？？？？？',
   ENDDATE              datetime comment '？？？？？？？？',
   ICCID                varchar(20),
   STARTSURPLUSFLOW     varchar(20) comment '？？？？？？？？？？？？',
   ENDSURPLUSFLOW       varchar(20) comment '？？？？？？？？？？？？',
   CURRENTUSEFLOW       varchar(20) comment '？？？？？？？？？？？？',
   OPERATORS            varchar(10) comment '？？？？？？ 1？？？？ 2？？？？？？',
   CURRENTISUSETAG      varchar(20) default '1' comment '？？？？？？？？？？？？0:？？？？？？？？ 1？？？？？？',
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_ESIM_FLOWINFO                                     */
/*==============================================================*/
create table LAO_ESIM_FLOWINFO
(
   ID                   numeric(16,0) not null,
   LENOVOID             varchar(20) comment 'lenovoID',
   TOTALFLOW            varchar(20) comment '？？？？？？',
   SURPLUSFLOW          varchar(20) comment '？？？？？？？？',
   OPERATORS            varchar(10) comment '1？？？？？？？？2？？？？？？',
   CREATEDATE           datetime not null default 'SYSDATE',
   UPDATEDATE           datetime not null default 'SYSDATE',
   IMSI                 varchar(50),
   GOODSID              varchar(50),
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_ESIM_FLOW_GIVEN_DETAIL                            */
/*==============================================================*/
create table LAO_ESIM_FLOW_GIVEN_DETAIL
(
   ID                   numeric(16,0) not null,
   GIVENLENOVOID        varchar(50),
   BGIVENLENOVOID       varchar(50),
   FLOWSIZE             varchar(20),
   CREATEDATE           datetime,
   PARAM1               varchar(256),
   PARAM2               varchar(256),
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_ESIM_GOODS                                        */
/*==============================================================*/
create table LAO_ESIM_GOODS
(
   ID                   numeric(16,0) not null comment '主键ID',
   GOODS_ID             numeric(16,0) not null comment '产品ID',
   GOODS_NAME           varchar(40) not null comment '产品名称',
   GOODS_PRICE          varchar(30) not null comment '产品价格',
   GOODS_PIC            varchar(100) not null comment '产品链接',
   CREATEDATE           datetime comment '创建时间',
   GOODS_TYPE           varchar(3) not null comment '产品类型',
   ISPUBLIC             numeric(1,0) not null default 0 comment '是否发布0:发布 1:未发布',
   COUNTRY_ID           varchar(5) not null comment '区域id',
   COUNTRYNAME          varchar(50) not null comment '区域名字',
   GOODS_CYCLE          varchar(5) not null comment '产品周期(天)',
   PARAM1               varchar(50),
   PARAM2               varchar(50),
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: IDX_LAO_ESIM_GOODS                                    */
/*==============================================================*/
create index IDX_LAO_ESIM_GOODS on LAO_ESIM_GOODS
(
   GOODS_ID,
   COUNTRY_ID,
   GOODS_TYPE
);

/*==============================================================*/
/* Table: LAO_ESIM_GOODS_PLAN                                   */
/*==============================================================*/
create table LAO_ESIM_GOODS_PLAN
(
   ID                   numeric(16,0) not null,
   GOODSID              varchar(30) comment '？？？？？？',
   OPERATORS_PID        varchar(20) comment '？？？？？？？？？？？？',
   PLANSIZE             varchar(20) comment '？？？？？？？？',
   PLANDESC             varchar(100) comment '？？？？？？？？',
   OPERATORS            varchar(10) comment '？？？？？？ 1？？？？ 2？？？？？？',
   GOODNAME             varchar(100) comment '？？？？？？？？',
   GOODSURL             varchar(100),
   GOODSPRICE           varchar(100),
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_ESIM_ICCID_LIB                                    */
/*==============================================================*/
create table LAO_ESIM_ICCID_LIB
(
   ID                   numeric(16,0) not null comment '主键id',
   ICCID                varchar(20) not null comment 'Iccid号',
   CARDSTATUS           varchar(6) not null comment '卡状态',
   COUNTRY_ID           varchar(6) not null comment '区域id',
   INDATE               datetime not null comment '录入时间',
   ICCIDCURENTSTATUS    numeric(1,0) not null default 0 comment '0:空闲 1:使用',
   PARAM1               varchar(50) comment '扩展字段1',
   PARAM2               varchar(50) comment '扩展字段2',
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: IDX_LAO_ESIM_ICCID_LIB                                */
/*==============================================================*/
create index IDX_LAO_ESIM_ICCID_LIB on LAO_ESIM_ICCID_LIB
(
   ICCID,
   COUNTRY_ID,
   ICCIDCURENTSTATUS,
   CARDSTATUS
);

/*==============================================================*/
/* Table: LAO_ESIM_IMEI                                         */
/*==============================================================*/
create table LAO_ESIM_IMEI
(
   IMEI                 varchar(30) not null comment '？？？？？？',
   DEVICETYPE           varchar(50) comment '？？？？？？？？',
   DEVICEPIC            varchar(100) comment '？？？？？？？？',
   DEVICENAME           varchar(100) comment '？？？？？？？？',
   DEVICEDESC           varchar(200) comment '？？？？？？？？',
   PARANAME1            varchar(100) comment '？？？？？？？？1',
   PARANAME2            varchar(100) comment '？？？？？？？？2',
   primary key (IMEI),
   check ("IMEI" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_ESIM_IMEI_EID                                     */
/*==============================================================*/
create table LAO_ESIM_IMEI_EID
(
   ID                   numeric(16,0) not null,
   EID                  varchar(50) not null,
   IMEIID               varchar(50),
   IMEINAME             varchar(50),
   CREATEDATE           datetime,
   UPDATEDATE           datetime,
   PARAM1               varchar(50),
   PARAM2               varchar(50),
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: IDX_LAO_ESIM_IMEI_EID                                 */
/*==============================================================*/
create index IDX_LAO_ESIM_IMEI_EID on LAO_ESIM_IMEI_EID
(
   EID,
   IMEIID
);

/*==============================================================*/
/* Table: LAO_ESIM_LENOVOID_GOODSID                             */
/*==============================================================*/
create table LAO_ESIM_LENOVOID_GOODSID
(
   ID                   numeric(16,0) not null,
   LENOVOID             varchar(20) comment 'lenovoid',
   GOODSID              varchar(20) comment '？？？？ID',
   CREATEDATE           datetime not null default 'SYSDATE' comment '？？？？？？？？',
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_ESIM_LENOVO_IMEI                                  */
/*==============================================================*/
create table LAO_ESIM_LENOVO_IMEI
(
   ID                   numeric(16,0) not null comment '？？？？id',
   LENOVOID             varchar(20) comment '？？？？？？？？',
   IMEI                 varchar(30) comment '？？？？？？',
   CREATEDATE           datetime not null default 'SYSDATE',
   ENDTIME              datetime comment '？？？？？？？？？？？？？？',
   FLAG                 varchar(10) comment '？？？？？？？？？？？？？？？？1？？？？？？？？2？？？？？？',
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_ESIM_LOG                                          */
/*==============================================================*/
create table LAO_ESIM_LOG
(
   ID                   numeric(16,0) not null,
   LENOVOID             varchar(30),
   EID                  varchar(50) not null,
   ICCID                varchar(20),
   REQUESTINFO          varchar(1000),
   RESPONSEINFO         varchar(2000),
   RESPCODE             varchar(10),
   INDATE               datetime,
   TRADE_TYPECODE       varchar(30),
   PARAM1               varchar(50),
   PARAM2               varchar(50),
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: IDX_LAO_ESIM_LOG                                      */
/*==============================================================*/
create index IDX_LAO_ESIM_LOG on LAO_ESIM_LOG
(
   EID,
   LENOVOID
);

/*==============================================================*/
/* Table: LAO_ESIM_OPERATOR_PROPERTY                            */
/*==============================================================*/
create table LAO_ESIM_OPERATOR_PROPERTY
(
   ID                   numeric(16,0) not null comment '？？？？id',
   OPERATOR             varchar(10) comment '？？？？？？？？？？',
   OPERATORNAME         varchar(50) comment '？？？？？？？？？？',
   USERNAME             varchar(20) comment '？？？？？？？？？？？？？？？？？？？？',
   PASSWORD             varchar(16) comment '？？？？？？？？？？？？？？？？？？',
   URL                  varchar(200) comment '？？？？？？？？',
   LICENSEKEY           varchar(50) comment '？？？？？？？？',
   VERSION              varchar(10) comment '？？？？',
   CREATEDATE           datetime not null default 'SYSDATE' comment '？？？？？？？？',
   PARAM1               varchar(50) comment '？？？？？？？？',
   PARAM2               varchar(50) comment '？？？？？？？？',
   primary key (ID),
   check ("ID" IS NOT NULL)
);

alter table LAO_ESIM_OPERATOR_PROPERTY comment '？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_ESIM_STATE                                        */
/*==============================================================*/
create table LAO_ESIM_STATE
(
   ID                   numeric(16,0) not null,
   IMEI                 varchar(30) comment '？？？？？？',
   ICCID                varchar(20) comment 'iccid',
   STATUS               varchar(10) comment '？？？？？？',
   OPERATORS            varchar(10) comment '？？？？？？ 1？？？？？？？？ 2？？？？？？',
   IMSI                 varchar(30),
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_ESIM_TRADE                                        */
/*==============================================================*/
create table LAO_ESIM_TRADE
(
   TRADE_ID             numeric(16,0) not null comment '订单Id',
   TRADE_TYPECODE       varchar(3) not null comment '操作类型',
   GOODS_ID             numeric(16,0) comment '产品id',
   COUNTRY_ID           varchar(5) comment '区域id',
   ICCID                varchar(20) not null comment 'iccid号',
   EID                  varchar(50) not null comment 'eid号',
   ACCEPTDATE           datetime comment '受理时间',
   LENOVOID             varchar(30) not null comment '登陆账号',
   FINISHDATE           datetime comment '完成时间',
   HANDLETAG            numeric(1,0) not null default 0 comment '处理标志 0:成功处理1:正处理2:处理失败)',
   PARAM1               varchar(50) comment '扩展字段1',
   PARAM2               varchar(50) comment '扩展字段2',
   primary key (TRADE_ID),
   check ("TRADE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: IDX_LAO_ESIM_TRADE                                    */
/*==============================================================*/
create index IDX_LAO_ESIM_TRADE on LAO_ESIM_TRADE
(
   TRADE_ID,
   TRADE_TYPECODE,
   ICCID,
   LENOVOID,
   ACCEPTDATE
);

/*==============================================================*/
/* Table: LAO_ESIM_TRADEFEESUB                                  */
/*==============================================================*/
create table LAO_ESIM_TRADEFEESUB
(
   TRADE_ID             numeric(16,0) not null,
   GOODS_ID             numeric(16,0),
   OLDFEE               varchar(20),
   FEE                  varchar(20),
   PAYORDERID           varchar(20),
   PAYTAG               numeric(1,0) not null default 1,
   PAYDATE              datetime,
   PAYTYPE              varchar(10) not null,
   CREATEDATE           datetime not null,
   PARAM1               varchar(50),
   PARAM2               varchar(50),
   check ("TRADE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: IDX_LAO_ESIM_TRADEFEESUB                              */
/*==============================================================*/
create index IDX_LAO_ESIM_TRADEFEESUB on LAO_ESIM_TRADEFEESUB
(
   TRADE_ID
);

/*==============================================================*/
/* Table: LAO_ESIM_USER                                         */
/*==============================================================*/
create table LAO_ESIM_USER
(
   USER_ID              numeric(16,0) not null comment '用户Id',
   LENOVOID             varchar(30) not null comment '登陆账号',
   EID                  varchar(50) comment 'eid号',
   ICCID                varchar(20) not null comment 'iccid号',
   BINDDATE             datetime comment '绑定时间',
   FIRSTCALLTIME        datetime comment '激活时间',
   ENDCALLTIME          datetime comment '失效时间',
   CURENTUSERSTATUS     numeric(1,0) not null default 1 comment '用户状态 0:订购失败 1:已绑定停用 2:已绑定使用 3:解绑 4:绑定停用 5绑定使用 6:转赠 7:转赠停用 8:转赠使用',
   CARDSTATUS           varchar(6) comment '卡状态',
   DPADDRESS            varchar(100) comment 'dp地址',
   PROFILEID            varchar(100) comment '绑定id',
   PARAM1               varchar(50) comment '扩展字段1',
   PARAM2               varchar(50) comment '扩展字段2',
   primary key (USER_ID),
   check ("USER_ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: IDX_LAO_ESIM_USER                                     */
/*==============================================================*/
create index IDX_LAO_ESIM_USER on LAO_ESIM_USER
(
   LENOVOID,
   ICCID
);

/*==============================================================*/
/* Table: LAO_ESIM_USER_BUNDING                                 */
/*==============================================================*/
create table LAO_ESIM_USER_BUNDING
(
   USER_ID              numeric(16,0) not null comment '用户Id',
   EID                  varchar(50) not null comment 'Eid号',
   EIDTARGER            varchar(50) comment '被转赠eid号',
   UNBUNDLINGDATE       datetime comment '解绑时间',
   BUNDLINGDATE         datetime comment '绑定时间',
   PARAM1               varchar(50) comment '扩展字段1',
   PARAM2               varchar(50) comment '扩展字段2',
   check ("USER_ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: IDX_LAO_ESIM_USER_BUNDING                             */
/*==============================================================*/
create index IDX_LAO_ESIM_USER_BUNDING on LAO_ESIM_USER_BUNDING
(
   USER_ID
);

/*==============================================================*/
/* Table: LAO_ESIM_USER_GIVEN                                   */
/*==============================================================*/
create table LAO_ESIM_USER_GIVEN
(
   USER_ID              numeric(16,0) not null comment '用户Id',
   EID                  varchar(50) not null comment 'Eid号',
   EIDTARGER            varchar(50) comment '被转赠eid号',
   LENOVOID             varchar(50) not null comment '登陆账号',
   LENOVOIDTARGER       varchar(50) not null comment '目标账号',
   GOODSID              numeric(16,0) comment '产品id',
   GIVENDATE            datetime comment '转赠时间',
   LENOVOIDTARGERISBUNDINGDATE datetime comment '绑定时间',
   PARAM1               varchar(50) comment '扩展字段1',
   PARAM2               varchar(50) comment '扩展字段2',
   check ("USER_ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: IDX_LAO_ESIM_USER_GIVEN                               */
/*==============================================================*/
create index IDX_LAO_ESIM_USER_GIVEN on LAO_ESIM_USER_GIVEN
(
   USER_ID
);

/*==============================================================*/
/* Table: LAO_ESIM_USER_GOODS                                   */
/*==============================================================*/
create table LAO_ESIM_USER_GOODS
(
   USER_ID              numeric(16,0) not null comment '用户Id',
   GOODS_ID             numeric(16,0) not null comment '产品id',
   STARTDATE            datetime not null comment '开始时间',
   ENDDATE              datetime not null comment '结束时间',
   BINDDATE             datetime comment '绑定时间',
   CREATEDATE           datetime comment '创建时间',
   UPDATEDATE           datetime comment '更新时间',
   GOODSSTATUS          varchar(10) not null comment '(订购/(转赠/已转赠)/(解绑/绑定)',
   PARAM1               varchar(50) comment '扩展字段1',
   PARAM2               varchar(50) comment '扩展字段2',
   check ("USER_ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: IDX_LAO_ESIM_USER_GOODS                               */
/*==============================================================*/
create index IDX_LAO_ESIM_USER_GOODS on LAO_ESIM_USER_GOODS
(
   USER_ID
);

/*==============================================================*/
/* Table: LAO_FLOWOPERATION_FLOWORDER                           */
/*==============================================================*/
create table LAO_FLOWOPERATION_FLOWORDER
(
   FLOWORDERID          varchar(40) not null,
   CUSTCHANNELID        varchar(30),
   CLIENTORDERID        varchar(32),
   MOBILE               varchar(20),
   PACKAGESIZE          varchar(20),
   STEPSTATE1           varchar(4),
   STEPSTATETIME1       datetime,
   STEPSTATE2           varchar(4),
   STEPSTATETIME2       datetime,
   STEPSTATE3           varchar(4),
   STEPSTATETIME3       datetime,
   CALLBACKDISCOUNT     varchar(20),
   CALLBACKDOSTMONEY    varchar(20),
   STEPSTATE4           varchar(4),
   STEPSTATETIME4       datetime,
   PARANAME1            varchar(100),
   PARANAME2            varchar(100),
   ISSUCCESS            varchar(4) default 'no',
   primary key (FLOWORDERID),
   check ("FLOWORDERID" IS NOT NULL)
);

alter table LAO_FLOWOPERATION_FLOWORDER comment '？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_FLOWOPERATION_LOG                                 */
/*==============================================================*/
create table LAO_FLOWOPERATION_LOG
(
   LOGID                numeric(20,0) not null,
   FLOWORDERID          varchar(32),
   REQUESTSYSTEM        varchar(50),
   RESPONSESYSTEM       varchar(50),
   REQUESTPARM          varchar(1000),
   RESPONSEPARM         varchar(1000),
   CALLSERVER           varchar(50),
   CALLTIME             datetime,
   RESPCODE             varchar(10),
   RESPDESC             varchar(100),
   CALLISSUCCESS        varchar(4),
   PARANAME1            varchar(100),
   PARANAME2            varchar(100),
   STEPSTATE            varchar(2),
   primary key (LOGID),
   check ("LOGID" IS NOT NULL)
);

alter table LAO_FLOWOPERATION_LOG comment '？？？？？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_FLOW_CONFIG                                       */
/*==============================================================*/
create table LAO_FLOW_CONFIG
(
   ID                   numeric(8,0) not null comment '？？？？ID',
   FLOWSIZE             varchar(30) comment '？？？？？？？？',
   PRICE                varchar(30) comment '？？？？',
   primary key (ID),
   check ("ID" IS NOT NULL)
);

alter table LAO_FLOW_CONFIG comment 'LAO？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_FTPFILE_COLLECT                                   */
/*==============================================================*/
create table LAO_FTPFILE_COLLECT
(
   ID                   numeric(16,0) not null,
   FILE_NAME            varchar(100),
   CREATE_DATE          datetime,
   UPDATE_DATE          datetime,
   TRADETYPECODE        varchar(5),
   CARDTOTAL            numeric(8,0),
   SUCCESSNUM           numeric(8,0) default 0,
   FAILNUM              numeric(8,0) default 0,
   PENDINGNUM           numeric(8,0),
   PARA_NAME1           varchar(50),
   PARA_NAME2           varchar(50),
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: IDX_LAO_FTPFILE_COLLECT                               */
/*==============================================================*/
create index IDX_LAO_FTPFILE_COLLECT on LAO_FTPFILE_COLLECT
(
   FILE_NAME
);

/*==============================================================*/
/* Index: LAO_FTPFILE_COLLECT                                   */
/*==============================================================*/
create index LAO_FTPFILE_COLLECT on LAO_FTPFILE_COLLECT
(
   TRADETYPECODE
);

/*==============================================================*/
/* Table: LAO_FTPFILE_INFO                                      */
/*==============================================================*/
create table LAO_FTPFILE_INFO
(
   ID                   numeric(16,0) not null comment '主键',
   FILE_NAME            varchar(100) comment '文件名',
   ICCID                varchar(20),
   MSISDN               varchar(20),
   CREATEDATE           datetime comment '创建时间',
   TRADETYPECODE        varchar(5) comment '180 变更 130 停机 140 开机 120 订购',
   ERRORDESC            varchar(20) comment '错误编码',
   STATUS               numeric(1,0) not null default 1 comment '1 新建  2 处理成功 4 正在处理  3 处理失败',
   PROCESSMODE          numeric(1,0) not null default 2 comment '处理方式 1 自动处理 2 手动处理 ',
   GOODSRELEASEID       numeric(8,0) comment '产品发布Id',
   OPERATORS_PID        numeric(1,0) default NULL comment '运营商Id',
   CUSTID               varchar(30) comment '客户名称',
   PARA_NAME2           varchar(50),
   UPDATEDATE           datetime comment '更新时间',
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: IDX_LAO_FTPFILE_INFO                                  */
/*==============================================================*/
create index IDX_LAO_FTPFILE_INFO on LAO_FTPFILE_INFO
(
   ICCID
);

/*==============================================================*/
/* Index: LAO_FTPFILE_INFO                                      */
/*==============================================================*/
create index LAO_FTPFILE_INFO on LAO_FTPFILE_INFO
(
   TRADETYPECODE
);

/*==============================================================*/
/* Table: LAO_F_ACCTDEPOSIT                                     */
/*==============================================================*/
create table LAO_F_ACCTDEPOSIT
(
   ACCT_BALANCE_ID      numeric(16,0) not null,
   CHANNEL_CUST_ID      numeric(16,0),
   INIT_MONEY           numeric(11,0) comment '？？？？？？？？',
   DEPOSIT_MONEY        numeric(11,0),
   INVOICE_FEE          numeric(11,0),
   PRINT_FEE            numeric(11,0),
   START_CYC_ID         numeric(11,0),
   END_CYC_ID           numeric(11,0),
   START_DATE           datetime comment '？？？？？？？？',
   END_DATE             datetime comment '？？？？？？？？',
   CASH_TAG             char(1) comment '0-？？？？？？？？？？ 1-？？？？？？？？？？？？ 2-？？？？？？？？？？？？',
   UPDATE_STAFF_ID      varchar(8) comment '？？？？？？？？',
   UPDATE_TIME          datetime comment '？？？？？？？？',
   OPER_ID              varchar(40),
   REMARK               varchar(200) comment '？？？？',
   RSRV_STR1            varchar(200) comment '？？？？4',
   RSRV_STR2            varchar(200) comment '？？？？2 ？？？？？？？？',
   primary key (ACCT_BALANCE_ID),
   check ("ACCT_BALANCE_ID" IS NOT NULL)
);

alter table LAO_F_ACCTDEPOSIT comment '？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_F_ACCTDEPOSIT_20170526                            */
/*==============================================================*/
create table LAO_F_ACCTDEPOSIT_20170526
(
   ACCT_BALANCE_ID      numeric(16,0) not null,
   CHANNEL_CUST_ID      numeric(16,0),
   INIT_MONEY           numeric(11,0),
   DEPOSIT_MONEY        numeric(11,0),
   INVOICE_FEE          numeric(11,0),
   PRINT_FEE            numeric(11,0),
   START_CYC_ID         numeric(11,0),
   END_CYC_ID           numeric(11,0),
   START_DATE           datetime,
   END_DATE             datetime,
   CASH_TAG             char(1),
   UPDATE_STAFF_ID      varchar(8),
   UPDATE_TIME          datetime,
   OPER_ID              varchar(40),
   REMARK               varchar(200),
   RSRV_STR1            varchar(200),
   RSRV_STR2            varchar(200),
   check ("ACCT_BALANCE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_F_ACCTDEPOSIT_CHANGE                              */
/*==============================================================*/
create table LAO_F_ACCTDEPOSIT_CHANGE
(
   ACCOUNT_CHANGE_ID    numeric(16,0) not null,
   ACCT_BALANCE_ID      numeric(16,0) not null,
   CHANNEL_CUST_ID      numeric(16,0) not null,
   CREATE_DATE          datetime,
   UPDATE_DATE          datetime,
   UPDATE_MONEY         numeric(11,0) not null,
   OLD_MONEY            numeric(11,0) not null,
   NEW_MONEY            numeric(11,0) not null,
   CHANGE_TYPE          numeric(1,0),
   MOVE_ID              numeric(16,0),
   ACCESS_ID            numeric(16,0),
   CHARGE_ID            numeric(16,0),
   BALANCE_ID           numeric(16,0),
   OPER_ID              varchar(40),
   RSRV_STR1            varchar(100),
   RSRV_STR2            varchar(100),
   primary key (ACCOUNT_CHANGE_ID),
   check ("ACCOUNT_CHANGE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_GOODS                                             */
/*==============================================================*/
create table LAO_GOODS
(
   GOODS_ID             numeric(16,0) not null,
   GOODS_NAME           varchar(100),
   GOODS_PIC            varchar(100),
   OPERATORS_ID         numeric(8,0),
   UPDATEDATE           datetime,
   CREATEDATE           datetime,
   GOODS_PRICES         varchar(30),
   CREATE_STAFF_ID      numeric(16,0),
   GOODS_DESC           varchar(200),
   GOODS_TYPE           varchar(2),
   ACTIVE_WAY           char(1),
   BI_RULES_ID          numeric(16,0),
   primary key (GOODS_ID),
   check ("GOODS_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_GOODS_BAK                                         */
/*==============================================================*/
create table LAO_GOODS_BAK
(
   GOODS_ID             numeric(16,0) not null,
   GOODS_NAME           varchar(100),
   GOODS_PIC            varchar(100),
   OPERATORS_ID         numeric(8,0),
   UPDATEDATE           datetime,
   CREATEDATE           datetime,
   GOODS_PRICES         numeric(8,0),
   CREATE_STAFF_ID      numeric(16,0),
   GOODS_DESC           varchar(200),
   check ("GOODS_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_GOODS_ELEMENT                                     */
/*==============================================================*/
create table LAO_GOODS_ELEMENT
(
   ELEMENT_ID           numeric(8,0) not null,
   ELEMENT_TYPE         char(1),
   ORIGINAL_ID          numeric(8,0),
   START_DATE           datetime,
   END_DATE             datetime,
   PACKAGE_TYPE         char(1),
   GOODS_INDEX          numeric(2,0),
   GOODS_ID             numeric(16,0),
   PLAN_CLASSIFY        varchar(10) comment '1流量 2语音 3 短信',
   primary key (ELEMENT_ID),
   check (ELEMENT_TYPE is null or (ELEMENT_TYPE in ('0','1')))
);

/*==============================================================*/
/* Table: LAO_GOODS_FLOW_DD                                     */
/*==============================================================*/
create table LAO_GOODS_FLOW_DD
(
   IN_DATE              numeric(8,0) comment '每日，格式为YYYYMMDD',
   CHANNEL_CUST_ID      numeric(16,0) comment '渠道客户ID',
   CHANNEL_CUST_NAME    varchar(100) comment '渠道客户名称',
   CUST_ID              numeric(16,0) comment '客户ID',
   CUST_NAME            varchar(100) comment '客户名称',
   STAFF_ID             varchar(50) comment '发展人员ID',
   STAFF_NAME           varchar(100) comment '发展人名称',
   REGION_ID            varchar(50) comment '区域',
   REGION_NAME          varchar(100) comment '区域名称',
   OPERATORS_ID         numeric(8,0) comment '运营商ID',
   OPERATORS_NAME       varchar(100) comment '运营商名称',
   VALUE1               varchar(50) comment '一级标签',
   VALUE1_NAME          varchar(100) comment '一级标签名称',
   VALUE2               varchar(50) comment '二级标签',
   VALUE2_NAME          varchar(100) comment '二级标签名称',
   GOODS_ID             numeric(16,0),
   GOODS_NAME           varchar(100),
   CARDNUM              numeric(10,0) comment '用户数',
   TOTAL_CNT            varchar(50) comment '流量总量',
   USE_CNT              varchar(50) comment '使用量',
   LEFT_CNT             varchar(50) comment '剩余量',
   UPDATE_TIME          datetime comment '更新时间',
   REMARK               varchar(200) comment '备注'
);

alter table LAO_GOODS_FLOW_DD comment '每日商品流量汇总';

/*==============================================================*/
/* Table: LAO_GOODS_OPERATIVE                                   */
/*==============================================================*/
create table LAO_GOODS_OPERATIVE
(
   ID                   numeric(8,0) not null,
   PICTUREURL           varchar(255),
   PICTURELINKURL       varchar(255),
   ISSHOW               numeric(8,0) not null default 0,
   PARAM1               varchar(255),
   PARAM2               varchar(255),
   GOODSID              numeric(8,0) not null,
   DISPLAYTAG           numeric(10,0) not null,
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_GOODS_RELEASE                                     */
/*==============================================================*/
create table LAO_GOODS_RELEASE
(
   GOODS_RELEASE_ID     numeric(8,0) not null,
   GOODS_ID             numeric(16,0),
   START_DATE           datetime,
   END_DATE             datetime,
   CHANNEL_GROUP_ID     numeric(16,0),
   GROUP_ATTR_N1        varchar(50),
   GROUP_ATTR_V1        varchar(50),
   GROUP_ATTR_N2        varchar(50),
   GROUP_ATTR_V2        varchar(50),
   RELEASE_PRICE        varchar(30),
   RELEASE_CYCLE        varchar(6),
   POOL_ID              numeric(16,0),
   UNIT                 varchar(1),
   BASE_PLAN_ID         numeric(8,0),
   SMS                  numeric(1,0),
   SPEECH_SOUNDS        numeric(1,0),
   SILENT_PERIOD        varchar(4),
   EXTENSION_MODEL      numeric(1,0),
   DISCOUNT_LEVEL       numeric(1,0),
   DISCOUNT_RATE        numeric(2,0),
   EXCESS_TACTICS       numeric(1,0),
   EXCESS_RATE          numeric(3,0),
   IS_ALL_NORM_PLAN     numeric(1,0),
   IS_BIND_IMEI         numeric(1,0),
   IS_PRIVATE_NETWORK   numeric(1,0),
   APN_REALM            varchar(50),
   IS_DEFAULT_PLAN      numeric(1,0),
   REMARK               varchar(200),
   RELS_CUST_ID         numeric(16,0),
   PAR_RELS_ID          numeric(16,0),
   GOODS_RELEASE_NAME   varchar(200),
   RULE_ID              numeric(16,0),
   primary key (GOODS_RELEASE_ID),
   check ("GOODS_RELEASE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_GOODS_RELEASE_CMP                                 */
/*==============================================================*/
create table LAO_GOODS_RELEASE_CMP
(
   GOODS_RELEASE_ID     numeric(8,0) not null,
   GOODS_ID             numeric(16,0),
   START_DATE           datetime,
   END_DATE             datetime,
   CHANNEL_GROUP_ID     numeric(16,0),
   GROUP_ATTR_N1        varchar(50),
   GROUP_ATTR_V1        varchar(50),
   GROUP_ATTR_N2        varchar(50),
   GROUP_ATTR_V2        varchar(50),
   RELEASE_PRICE        varchar(30),
   RELEASE_CYCLE        varchar(6),
   POOL_ID              numeric(16,0),
   UNIT                 varchar(1),
   BASE_PLAN_ID         numeric(8,0),
   SMS                  varchar(1),
   SPEECH_SOUNDS        varchar(1),
   SILENT_PERIOD        varchar(4),
   EXTENSION_MODEL      varchar(1),
   DISCOUNT_LEVEL       varchar(1),
   DISCOUNT_RATE        varchar(10),
   EXCESS_TACTICS       varchar(1),
   EXCESS_RATE          varchar(10),
   IS_ALL_NORM_PLAN     varchar(1),
   IS_BIND_IMEI         varchar(1),
   IS_PRIVATE_NETWORK   varchar(1),
   APN_REALM            varchar(50),
   IS_DEFAULT_PLAN      varchar(1),
   CONTINUE_ACT         varchar(4),
   ACTIVE_WAY           varchar(2),
   REMARK               varchar(200),
   primary key (GOODS_RELEASE_ID),
   check ("GOODS_RELEASE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_GOODS_STATE_DD                                    */
/*==============================================================*/
create table LAO_GOODS_STATE_DD
(
   IN_DATE              numeric(8,0),
   CHANNEL_CUST_ID      numeric(16,0),
   CHANNEL_CUST_NAME    varchar(100),
   CUST_ID              numeric(16,0),
   CUST_NAME            varchar(100),
   STAFF_ID             varchar(50),
   STAFF_NAME           varchar(100),
   REGION_ID            varchar(50),
   REGION_NAME          varchar(100),
   OPERATORS_ID         numeric(8,0),
   OPERATORS_NAME       varchar(100),
   VALUE1               varchar(50),
   VALUE1_NAME          varchar(100),
   VALUE2               varchar(50),
   VALUE2_NAME          varchar(100),
   GOODS_ID             numeric(16,0),
   GOODS_NAME           varchar(100),
   STATE_CODE           varchar(10),
   STATE_NAME           varchar(100),
   STATE_ASNAME         varchar(100),
   CARD_NUM             numeric(16,0),
   UPDATE_TIME          datetime,
   REMARK               varchar(200)
);

/*==============================================================*/
/* Table: LAO_GOODS_STATE_MM                                    */
/*==============================================================*/
create table LAO_GOODS_STATE_MM
(
   IN_DATE              numeric(8,0),
   CHANNEL_CUST_ID      numeric(16,0),
   CHANNEL_CUST_NAME    varchar(100),
   CUST_ID              numeric(16,0),
   CUST_NAME            varchar(100),
   STAFF_ID             varchar(50),
   STAFF_NAME           varchar(100),
   REGION_ID            varchar(50),
   REGION_NAME          varchar(100),
   OPERATORS_ID         numeric(8,0),
   OPERATORS_NAME       varchar(100),
   VALUE1               varchar(50),
   VALUE1_NAME          varchar(100),
   VALUE2               varchar(50),
   VALUE2_NAME          varchar(100),
   GOODS_ID             numeric(16,0),
   GOODS_NAME           varchar(100),
   STATE_CODE           varchar(10),
   STATE_NAME           varchar(100),
   STATE_ASNAME         varchar(100),
   CARD_NUM             numeric(16,0),
   UPDATE_TIME          datetime,
   REMARK               varchar(200)
);

/*==============================================================*/
/* Table: LAO_GRPNET_FEECOUNT                                   */
/*==============================================================*/
create table LAO_GRPNET_FEECOUNT
(
   FEECOUNT_ID          numeric(8,0) not null comment '？？？？？？？？？？',
   OBJECT_ID            numeric(6,0) not null comment '？？？？？？？？？？？？',
   COMPUTE_METHOD       numeric(2,0) comment '？？？？？？？？',
   BASE_UNIT            varchar(2) comment '？？？？？？？？？？1？？？？？？2？？？？3 KB？？4？？？？？？？？？？？？？？',
   UNIT_FEE             varchar(20) comment '？？？？？？？？？？？？？？？？？？？？？？',
   MIN_VALUE            varchar(20) comment '？？？？？？',
   MAX_VALUE            varchar(20) comment '？？？？？？',
   MIN_PRICE            varchar(20) comment '？？？？？？？？？？？？？？？？？？？？',
   MAX_PRICE            varchar(20) comment '？？？？？？？？？？？？？？？？？？？？',
   TOP_VALUE            varchar(20) comment '？？？？？？',
   REMARK               varchar(200) comment '？？？？',
   primary key (FEECOUNT_ID),
   check ("FEECOUNT_ID" IS NOT NULL)
);

alter table LAO_GRPNET_FEECOUNT comment '？？？？？？？？？？';

/*==============================================================*/
/* Index: IDX_GRPNET_FEECOUNT_OBJECT_ID                         */
/*==============================================================*/
create index IDX_GRPNET_FEECOUNT_OBJECT_ID on LAO_GRPNET_FEECOUNT
(
   OBJECT_ID
);

/*==============================================================*/
/* Table: LAO_GRPNET_FEEDISCNT                                  */
/*==============================================================*/
create table LAO_GRPNET_FEEDISCNT
(
   FEEDISCNT_ID         numeric(8,0) not null comment '？？？？？？？？？？',
   OBJECT_ID            numeric(6,0) not null comment '？？？？？？？？？？？？',
   COMPUTE_METHOD       numeric(2,0) comment '？？？？？？？？',
   MIN_VALUE            varchar(20) comment '？？？？？？？？？？',
   MAX_VALUE            varchar(20) comment '？？？？？？？？？？',
   DIVIED_CHILD_VALUE   varchar(20) comment '？？？？？？？？？？？？',
   DIVIED_PARENT_VALUE  varchar(20) comment '？？？？？？？？？？？？',
   DISCNT_FEE           varchar(20) comment '？？？？？？？？？？',
   BASE_FEE             varchar(20) comment '？？？？？？？？？？？？？？',
   REMARK               varchar(200) comment '？？？？',
   primary key (FEEDISCNT_ID),
   check ("FEEDISCNT_ID" IS NOT NULL)
);

alter table LAO_GRPNET_FEEDISCNT comment '？？？？？？？？？？';

/*==============================================================*/
/* Index: IDX_GRPNET_FEEDISCNT_OBJECT_ID                        */
/*==============================================================*/
create index IDX_GRPNET_FEEDISCNT_OBJECT_ID on LAO_GRPNET_FEEDISCNT
(
   OBJECT_ID
);

/*==============================================================*/
/* Table: LAO_GRPNET_IMPBILL                                    */
/*==============================================================*/
create table LAO_GRPNET_IMPBILL
(
   BATCH_ID             numeric(16,0) not null comment '？？？？？？？？？？',
   BILL_ID              numeric(16,0) not null comment '？？？？？？？？？？',
   USER_ID              numeric(16,0) comment '？？？？ID',
   SERIAL_NUMBER        varchar(100) not null comment '？？？？？？？？',
   ITEM_ID              numeric(6,0) comment '？？？？？？？？',
   ITEM_NAME            varchar(100) comment '？？？？？？？？',
   CYCLE_ID             numeric(6,0) comment '？？？？？？？？',
   USE_COUNT            numeric(11,0) comment '？？？？？？',
   DEAL_TAG             char(1) comment '？？？？？？？？？？0？？？？？？ 1？？？？？？ 2？？？？？？？？',
   RECV_TIME            datetime comment '？？？？？？？？',
   RESULT_INFO          varchar(200) comment '？？？？？？？？？？？？',
   UPDATE_TIME          datetime comment '？？？？？？？？',
   UPDATE_STAFF_ID      varchar(40) comment '？？？？？？？？',
   FILE_SRC             varchar(100) comment '？？？？？？？？',
   RSRV_INFO1           varchar(200) comment '？？？？？？？？1',
   RSRV_INFO2           varchar(200) comment '？？？？？？？？2',
   primary key (BILL_ID),
   check ("BATCH_ID" IS NOT NULL)
);

alter table LAO_GRPNET_IMPBILL comment '？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？';

/*==============================================================*/
/* Index: IDX_GRPNET_IMPBILL_BATCH_ID                           */
/*==============================================================*/
create index IDX_GRPNET_IMPBILL_BATCH_ID on LAO_GRPNET_IMPBILL
(
   BATCH_ID
);

/*==============================================================*/
/* Index: IDX_GRPNET_IMPBILL_CYCLE_ID                           */
/*==============================================================*/
create index IDX_GRPNET_IMPBILL_CYCLE_ID on LAO_GRPNET_IMPBILL
(
   CYCLE_ID
);

/*==============================================================*/
/* Index: IDX_GRPNET_IMPBILL_USER_ID                            */
/*==============================================================*/
create index IDX_GRPNET_IMPBILL_USER_ID on LAO_GRPNET_IMPBILL
(
   USER_ID
);

/*==============================================================*/
/* Table: LAO_ICCID_ASSIGN_BATCH                                */
/*==============================================================*/
create table LAO_ICCID_ASSIGN_BATCH
(
   BATCH_ID             numeric(16,0) not null comment '？？？？？？？？？？',
   TRADE_TYPE_CODE      numeric(4,0) comment '？？？？？？？？？？？？',
   OPERATORS_ID         numeric(8,0) comment '？？？？？？id',
   ENTERPRISE_CONSIGNEE varchar(100) comment '？？？？？？？？？？',
   CONSIGNEE_PHONE      varchar(20) comment '？？？？？？？？？？',
   CONSIGNEE_IDCARD     varchar(20) comment '？？？？？？？？？？？？',
   INDUSTRY_CATEGORY    varchar(100) comment '？？？？？？？？',
   INDUSTRY_SEGMENTATION varchar(100) comment '？？？？？？？？',
   DELIVERY_ADDRESS     varchar(100) comment '？？？？？？？？',
   SIM_TYPE             varchar(20) comment 'SIM？？？？？？',
   SIM_SIZE             varchar(20) comment 'SIM？？？？？？',
   SIM_FEE              varchar(20) default '0' comment 'SIM？？？？？？？？？？？？/？？？？',
   ICCID_START          varchar(20) comment '？？？？ICCID',
   ICCID_END            varchar(20) comment '？？？？ICCID',
   NUMBER_START         varchar(20) comment '？？？？？？？？？？？？',
   NUMBER_END           varchar(20) comment '？？？？？？？？？？？？',
   DELIVERY_DATE        datetime comment '？？？？？？？？',
   CONSIGNOR            varchar(100) comment '？？？？？？',
   LOGISTICS_COMPANY    varchar(100) comment '？？？？？？？？',
   LOGISTICS_NUMBER     varchar(50) comment '？？？？？？？？',
   PULL_PERSON          varchar(100) comment '？？？？？？',
   PULL_NUMBER          varchar(20) comment '？？？？？？？？？？',
   RECV_TIME            datetime comment '？？？？？？？？',
   OPER_ID              varchar(40) comment '？？？？？？？？',
   SUM_NUM              numeric(11,0) default 0 comment '？？？？？？',
   SUCC_NUM             numeric(11,0) default 0 comment '？？？？？？？？',
   FAIL_NUM             numeric(11,0) default 0 comment '？？？？？？？？',
   REMARK               varchar(200) comment '？？？？',
   RSRV_INFO1           varchar(200) comment '？？？？？？？？1',
   RSRV_INFO2           varchar(200) comment '？？？？？？？？2',
   ORDER_FEE            varchar(20) default '0' comment '？？？？？？？？？？',
   PAYMENT              varchar(2) comment '？？？？？？？？？？？？0？？？？？？？？1？？？？？？',
   GENERATEINFOR        varchar(2) comment '？？？？？？？？？？？？？？？？？？？？？？0？？？？1？？',
   ORDER_DATE           varchar(20) comment '？？？？？？？？',
   check ("BATCH_ID" IS NOT NULL)
);

alter table LAO_ICCID_ASSIGN_BATCH comment '卡划拨批次数据表';

/*==============================================================*/
/* Table: LAO_ICCID_ASSIGN_LIB                                  */
/*==============================================================*/
create table LAO_ICCID_ASSIGN_LIB
(
   ICCID                varchar(50),
   DEVICETYPE           varchar(20) not null,
   PRIVATEKEY           varchar(256) not null,
   CARDTYPE             varchar(256) not null,
   INITPRODUCT          varchar(256),
   CARDSTATUS           varchar(50) not null,
   OPERATORS            varchar(256) not null,
   CTYPE                varchar(50) not null,
   ATTRIBUTE1           varchar(20),
   VALUE1               varchar(256) not null,
   ATTRIBUTE2           varchar(20),
   VALUE2               varchar(256) not null,
   IF_MAINTENANCE       varchar(10) not null,
   CUSTID               varchar(50),
   IN_DATE              datetime,
   MSISDN               varchar(20),
   ACTIVED              char(1),
   BATCH_ID             varchar(20),
   TEST_CYCLE           numeric(2,0),
   VIC                  varchar(30),
   IMSI                 varchar(30),
   BUY_ORDER_NO         varchar(50),
   TEST_GOODS_RLS_ID    numeric(16,0),
   ID                   numeric(16,0) not null,
   CYCLE_STATE          char(1),
   check ("DEVICETYPE" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_ICCID_BATCHDATA                                   */
/*==============================================================*/
create table LAO_ICCID_BATCHDATA
(
   BATCH_ID             numeric(16,0) not null comment '？？？？？？？？？？',
   TRADE_TYPE_CODE      numeric(4,0) comment '？？？？？？？？？？？？',
   OPERATORS_ID         numeric(8,0) comment '？？？？？？id',
   ENTERPRISE_CONSIGNEE varchar(100) comment '？？？？？？？？？？',
   CONSIGNEE_PHONE      varchar(20) comment '？？？？？？？？？？',
   CONSIGNEE_IDCARD     varchar(20) comment '？？？？？？？？？？？？',
   INDUSTRY_CATEGORY    varchar(100) comment '？？？？？？？？',
   INDUSTRY_SEGMENTATION varchar(100) comment '？？？？？？？？',
   DELIVERY_ADDRESS     varchar(100) comment '？？？？？？？？',
   SIM_TYPE             varchar(20) comment 'SIM？？？？？？',
   SIM_SIZE             varchar(20) comment 'SIM？？？？？？',
   SIM_FEE              varchar(20) default '0' comment 'SIM？？？？？？？？？？？？/？？？？',
   ICCID_START          varchar(20) comment '？？？？ICCID',
   ICCID_END            varchar(20) comment '？？？？ICCID',
   NUMBER_START         varchar(20) comment '？？？？？？？？？？？？',
   NUMBER_END           varchar(20) comment '？？？？？？？？？？？？',
   DELIVERY_DATE        datetime comment '？？？？？？？？',
   CONSIGNOR            varchar(100) comment '？？？？？？',
   LOGISTICS_COMPANY    varchar(100) comment '？？？？？？？？',
   LOGISTICS_NUMBER     varchar(50) comment '？？？？？？？？',
   PULL_PERSON          varchar(100) comment '？？？？？？',
   PULL_NUMBER          varchar(20) comment '？？？？？？？？？？',
   RECV_TIME            datetime comment '？？？？？？？？',
   OPER_ID              varchar(40) comment '？？？？？？？？',
   SUM_NUM              numeric(11,0) default 0 comment '？？？？？？',
   SUCC_NUM             numeric(11,0) default 0 comment '？？？？？？？？',
   FAIL_NUM             numeric(11,0) default 0 comment '？？？？？？？？',
   REMARK               varchar(200) comment '？？？？',
   RSRV_INFO1           varchar(200) comment '？？？？？？？？1',
   RSRV_INFO2           varchar(200) comment '？？？？？？？？2',
   ORDER_FEE            varchar(20) default '0' comment '？？？？？？？？？？',
   PAYMENT              varchar(2) comment '？？？？？？？？？？？？0？？？？？？？？1？？？？？？',
   GENERATEINFOR        varchar(2) comment '？？？？？？？？？？？？？？？？？？？？？？0？？？？1？？',
   ORDER_DATE           varchar(20) comment '？？？？？？？？',
   FILE_NAME            varchar(225),
   primary key (BATCH_ID),
   check ("BATCH_ID" IS NOT NULL)
);

alter table LAO_ICCID_BATCHDATA comment '？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_ICCID_DONATE_STEP                                 */
/*==============================================================*/
create table LAO_ICCID_DONATE_STEP
(
   ID                   numeric(8,0) not null comment '？？？？ID',
   STEPID               varchar(100) not null comment 'STEPID',
   USERID               varchar(100) comment '？？？？ID',
   ICCID                varchar(50) comment 'Iccid',
   PLAN                 varchar(100) comment '？？？？？？？？',
   TOTALFLOWSIZE        varchar(32) comment '？？？？？？',
   TOTALSTEP            numeric(8,0) comment '？？？？？？',
   CURRENTSTEP          numeric(8,0) comment '？？？？？？？？',
   REMAINSTEP           numeric(8,0) comment '？？？？？？？？',
   CREATETIME           timestamp comment '？？？？？？？？',
   UPDATETIME           timestamp comment '？？？？？？？？',
   check ("ID" IS NOT NULL)
);

alter table LAO_ICCID_DONATE_STEP comment 'ICCID？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_ICCID_LIB                                         */
/*==============================================================*/
create table LAO_ICCID_LIB
(
   ICCID                varchar(50) comment 'iccid',
   DEVICETYPE           varchar(20) comment '？？？？？？？？？？？？ NB(Notebook); MB (Mobile);  MIFI',
   PRIVATEKEY           varchar(256) comment 'MIFI？？？？？？？？？？？？',
   CARDTYPE             varchar(256) comment '？？？？？？？？',
   INITPRODUCT          varchar(256) comment '？？？？？？？？',
   CARDSTATUS           varchar(50) comment '？？？？？？',
   OPERATORS            varchar(256) comment '？？？？？？',
   CTYPE                varchar(50) comment '？？？？？？(？？？？？？？？？？？？？？？？？？？？？？',
   ATTRIBUTE1           varchar(20) comment '？？？？？？',
   VALUE1               varchar(256) comment '？？？？',
   ATTRIBUTE2           varchar(20) comment '？？？？？？',
   VALUE2               varchar(256) comment '？？？？',
   IF_MAINTENANCE       varchar(10) comment '0:？？？？  1:？？？？',
   CUSTID               varchar(50) comment '？？？？？？？？',
   IN_DATE              datetime comment '？？？？？？？？',
   MSISDN               varchar(20) comment '？？？？？？？？？？？？？？',
   ACTIVED              char(1) comment '0:？？？？？？？？1？？？？',
   BATCH_ID             varchar(20) comment '？？？？？？',
   TEST_CYCLE           numeric(2,0),
   VIC                  varchar(30),
   IMSI                 varchar(30),
   BUY_ORDER_NO         varchar(50),
   TEST_GOODS_RLS_ID    numeric(16,0),
   ID                   numeric(16,0) not null,
   CYCLE_STATE          char(1),
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: UNIQ_IDX_LAO_ICCID_LIB_ICCID                          */
/*==============================================================*/
create unique index UNIQ_IDX_LAO_ICCID_LIB_ICCID on LAO_ICCID_LIB
(
   ICCID
);

/*==============================================================*/
/* Table: LAO_ICCID_LIBRARY                                     */
/*==============================================================*/
create table LAO_ICCID_LIBRARY
(
   ID                   numeric(8,0) not null comment '？？？？ID',
   ICCID                varchar(50) comment 'iccid',
   DEVICETYPE           varchar(20) comment '？？？？？？？？？？？？ NB(Notebook); MB (Mobile);  MIFI',
   PRIVATEKEY           varchar(256) comment 'MIFI？？？？？？？？？？？？',
   CARDTYPE             varchar(256) comment '？？？？？？？？',
   RATE                 varchar(256) comment '？？？？？？？？？？？？？？？？？？？？',
   primary key (ID),
   key AK_Key_1 (ICCID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_ICCID_LIB_BAK                                     */
/*==============================================================*/
create table LAO_ICCID_LIB_BAK
(
   ID                   numeric(8,0) not null,
   ICCID                varchar(50),
   DEVICETYPE           varchar(20) not null,
   PRIVATEKEY           varchar(256) not null,
   CARDTYPE             varchar(256) not null,
   INITPRODUCT          varchar(256),
   CARDSTATUS           varchar(50) not null,
   OPERATORS            varchar(256) not null,
   CTYPE                varchar(50) not null,
   ATTRIBUTE1           varchar(20) not null,
   VALUE1               varchar(256) not null,
   ATTRIBUTE2           varchar(20) not null,
   VALUE2               varchar(256) not null,
   IF_MAINTENANCE       varchar(10) not null,
   CUSTID               varchar(50),
   IN_DATE              datetime,
   MSISDN               varchar(20),
   ACTIVED              char(1),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_ICCID_LIB_FUHPBAK                                 */
/*==============================================================*/
create table LAO_ICCID_LIB_FUHPBAK
(
   ID                   numeric(8,0) not null,
   ICCID                varchar(50),
   DEVICETYPE           varchar(20) not null,
   PRIVATEKEY           varchar(256) not null,
   CARDTYPE             varchar(256) not null,
   INITPRODUCT          varchar(256),
   CARDSTATUS           varchar(50) not null,
   OPERATORS            varchar(256) not null,
   CTYPE                varchar(50) not null,
   ATTRIBUTE1           varchar(20) not null,
   VALUE1               varchar(256) not null,
   ATTRIBUTE2           varchar(20) not null,
   VALUE2               varchar(256) not null,
   IF_MAINTENANCE       varchar(10) not null,
   CUSTID               varchar(50),
   IN_DATE              datetime,
   MSISDN               varchar(20),
   ACTIVED              char(1),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_ICCID_LIB_SUN                                     */
/*==============================================================*/
create table LAO_ICCID_LIB_SUN
(
   ID                   numeric(8,0) not null,
   ICCID                varchar(50),
   DEVICETYPE           varchar(20) not null,
   PRIVATEKEY           varchar(256) not null,
   CARDTYPE             varchar(256) not null,
   INITPRODUCT          varchar(256),
   CARDSTATUS           varchar(50) not null,
   OPERATORS            varchar(256) not null,
   CTYPE                varchar(50) not null,
   ATTRIBUTE1           varchar(20) not null,
   VALUE1               varchar(256) not null,
   ATTRIBUTE2           varchar(20) not null,
   VALUE2               varchar(256) not null,
   IF_MAINTENANCE       varchar(10) not null,
   CUSTID               varchar(50),
   IN_DATE              datetime,
   MSISDN               varchar(20),
   ACTIVED              char(1),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_ICCID_LIB_ZC                                      */
/*==============================================================*/
create table LAO_ICCID_LIB_ZC
(
   ID                   numeric(8,0) not null,
   ICCID                varchar(50),
   DEVICETYPE           varchar(20) not null,
   PRIVATEKEY           varchar(256) not null,
   CARDTYPE             varchar(256) not null,
   INITPRODUCT          varchar(256),
   CARDSTATUS           varchar(50) not null,
   OPERATORS            varchar(256) not null,
   CTYPE                varchar(50) not null,
   ATTRIBUTE1           varchar(20),
   VALUE1               varchar(256) not null,
   ATTRIBUTE2           varchar(20),
   VALUE2               varchar(256) not null,
   IF_MAINTENANCE       varchar(10) not null,
   CUSTID               varchar(50),
   IN_DATE              datetime,
   MSISDN               varchar(20),
   ACTIVED              char(1),
   BATCH_ID             varchar(20),
   TEST_CYCLE           numeric(2,0),
   VIC                  varchar(30),
   IMSI                 varchar(30),
   BUY_ORDER_NO         varchar(50),
   TEST_GOODS_RLS_ID    numeric(16,0),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_ICCID_LOG                                         */
/*==============================================================*/
create table LAO_ICCID_LOG
(
   LOG_ID               numeric(8,0) not null comment '？？？？ID',
   STEPID               varchar(100) comment 'STEPID(？？？？？？？？？？？？？？)',
   USERID               varchar(100) comment '？？？？ID',
   ICCID                varchar(50) comment 'Iccid',
   ACTION               numeric(8,0) comment '1. ？？？？？？？？
            2. openInternet(？？？？apn？？apn2)
            3. ？？？？',
   TARGETFLOWSIZE       varchar(50) comment '？？？？？？？？？？？？',
   TARGETAPN            varchar(16) comment '？？？？APN',
   ACTIONSTATUS         numeric(8,0) comment '1 ？？？？ -1 ？？？？',
   CREATETIME           timestamp comment '？？？？？？？？',
   check ("LOG_ID" IS NOT NULL)
);

alter table LAO_ICCID_LOG comment 'iccid？？？？？？';

/*==============================================================*/
/* Table: LAO_IMEI_LB                                           */
/*==============================================================*/
create table LAO_IMEI_LB
(
   ID                   numeric(16,0) not null,
   IMEI                 varchar(30),
   primary key (ID),
   key AK_Key_1 (IMEI),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_IMEI_LIB                                          */
/*==============================================================*/
create table LAO_IMEI_LIB
(
   ID                   numeric(16,0) not null,
   IMEI                 varchar(30),
   primary key (ID),
   key AK_Key_1 (IMEI),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_IMEI_LIBRARY                                      */
/*==============================================================*/
create table LAO_IMEI_LIBRARY
(
   ID                   numeric(16,0) not null,
   IMEI                 varchar(30),
   primary key (ID),
   key AK_Key_1 (IMEI),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_IMEI_LIBRARY_INSERT                               */
/*==============================================================*/
create table LAO_IMEI_LIBRARY_INSERT
(
   ID                   numeric(8,0) not null,
   IMEI                 varchar(30),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_KEY_MANAGEMENT                                    */
/*==============================================================*/
create table LAO_KEY_MANAGEMENT
(
   ID                   numeric(20,0) not null,
   AUTH_KEY             varchar(30) not null,
   CUST_ID              varchar(40),
   UPDATE_DATE          datetime,
   PARA_NAME1           varchar(100),
   PARA_NAME2           varchar(100),
   primary key (ID),
   check ("ID" IS NOT NULL)
);

alter table LAO_KEY_MANAGEMENT comment '？？？？？？？？';

/*==============================================================*/
/* Table: LAO_LBS_DATA                                          */
/*==============================================================*/
create table LAO_LBS_DATA
(
   ID                   numeric(16,0) not null comment '主键ID',
   ICCID                varchar(50) not null comment 'iccid',
   LATITUDE             numeric(10,7) comment '纬度',
   LONGTITUDE           numeric(10,7) comment '经度',
   ADDRESS              varchar(200) comment '地址文字描述',
   ERRORINFO            varchar(200) comment '地址请求结果状态信息',
   CREATETIME           datetime not null default 'SYSDATE' comment '创建时间/上传时间',
   UPDATETIME           datetime comment '修改时间',
   BATCHID              varchar(50) comment '批次号',
   RSRVINFO1            varchar(200) comment '备用字段1',
   RSRVINFO2            varchar(200) comment '备用字段2',
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_MNO_ACCESS_LOG                                    */
/*==============================================================*/
create table LAO_MNO_ACCESS_LOG
(
   LOG_ID               numeric(16,0) not null,
   SYSTEM_ID            varchar(30),
   IP_ADDRESS           varchar(30),
   SERVER_NAME          varchar(30),
   REQUEST_ID           varchar(50),
   ICCID                varchar(20),
   MSISDN               varchar(20),
   IS_SUCCESS           varchar(1),
   REQUEST_TIMES        datetime,
   RESPONSE_TIMES       datetime,
   HANDLE_TIMES         numeric(16,0),
   REQUEST_INFO         longblob,
   RESP_INFO            longblob,
   RESP_TEXT            longblob,
   RESP_CODE            varchar(10),
   RESP_DESC            varchar(100),
   TRADE_ID             varchar(30),
   REMARKS              varchar(100),
   primary key (LOG_ID),
   check ("LOG_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_MNO_PROVIDE_SERVER                                */
/*==============================================================*/
create table LAO_MNO_PROVIDE_SERVER
(
   SERVER_ID            numeric(16,0) not null,
   SERVER_CATEGORY      varchar(2),
   SERVER_PROTOCOL      varchar(2),
   SERVER_NAME          varchar(100),
   PUSH_DATE            datetime,
   UPDATE_DATE          datetime,
   SERVER_OPEN          varchar(1),
   SERVER_DESC          varchar(50),
   HANDLER_CLASS        varchar(100),
   HANDLER_METHOD       varchar(100),
   PARA_NAME1           varchar(100),
   PARA_NAME2           varchar(100),
   primary key (SERVER_ID),
   check ("SERVER_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_MNO_SERVER_CONFIG                                 */
/*==============================================================*/
create table LAO_MNO_SERVER_CONFIG
(
   CONFIG_ID            numeric(16,0) not null,
   SYSTEM_ID            varchar(30),
   SERVER_ID            numeric(16,0),
   VISIT_LIMIT_TIMES    numeric(16,0),
   IS_OPEN              varchar(1),
   CREATE_DATE          datetime,
   UPDATE_DATE          datetime,
   PARA_NAME1           varchar(100),
   PARA_NAME2           varchar(100),
   primary key (CONFIG_ID),
   check ("CONFIG_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_MNO_SERVER_TASK                                   */
/*==============================================================*/
create table LAO_MNO_SERVER_TASK
(
   TASK_ID              numeric(16,0) not null,
   SYSTEM_ID            varchar(30),
   SRC_REQUEST_ID       varchar(50),
   ACCESS_LOG_ID        numeric(16,0),
   COM_REQUEST_ID       varchar(50),
   COM_SERVER_NAME      varchar(50),
   SERVER_NAME          varchar(30),
   TASK_STATUS          varchar(2),
   RESP_CODE            varchar(10),
   RESP_DESC            varchar(100),
   ICCID                varchar(20),
   MSISDN               varchar(20),
   REPEAT_TIMES         numeric(2,0),
   PRIORITY_LEVEL       numeric(2,0),
   CREATE_DATE          datetime,
   UPDATE_DATE          datetime,
   PARA_NAME1           varchar(100),
   PARA_NAME2           varchar(100),
   primary key (TASK_ID),
   check ("TASK_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_MNO_SYSTEM_CONFIG                                 */
/*==============================================================*/
create table LAO_MNO_SYSTEM_CONFIG
(
   CONFIG_ID            numeric(16,0) not null,
   SYSTEM_ID            varchar(30),
   APP_KEY              varchar(30),
   IP_LIMIT             varchar(1),
   SIGN_LIMIT           varchar(1),
   SERVER_CONTENT_FORMAT varchar(1),
   SERVER_DESC          varchar(50),
   SYSTEM_DESC          varchar(50),
   CREATE_DATE          datetime,
   UPDATE_DATE          datetime,
   PUSH_URL             varchar(100),
   PUSH_PROTOCOL        varchar(2),
   PUSH_CONTENT_FORMAT  varchar(1),
   PUSH_ENCRYPTION_WAY  varchar(1),
   PARA_NAME1           varchar(100),
   PARA_NAME2           varchar(100),
   primary key (CONFIG_ID),
   check ("CONFIG_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_MNO_SYSTEM_IP                                     */
/*==============================================================*/
create table LAO_MNO_SYSTEM_IP
(
   ID                   numeric(16,0) not null,
   SYSTEM_ID            varchar(30),
   IP_ADDRESS           varchar(30),
   CREATE_DATE          datetime,
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_OPERATORDEAL_LOG                                  */
/*==============================================================*/
create table LAO_OPERATORDEAL_LOG
(
   ID                   numeric(16,0) not null,
   ICCID                varchar(30),
   RESULT_CODE          varchar(50),
   RESULT_INFO          varchar(100),
   INPUT_PARAMETERS     varchar(2000),
   OUTPUT_PARAMETERS    varchar(2000),
   OPERATOR_TYPE        varchar(2) not null,
   OPERATOR_ID          varchar(30),
   SUCCESS              varchar(30) not null,
   CREATE_DATE          datetime,
   TRADE_ID             numeric(16,0),
   TRADE_TYPE_CODE      varchar(4) comment '业务类型',
   REQUEST_ID           varchar(40) comment '流水号',
   FAIL_NUM             numeric(4,0) comment '失败次数',
   UPDATE_DATE          datetime,
   UPDATE_STAFF_ID      varchar(40),
   MSISDN               varchar(50),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_OPERATORS                                         */
/*==============================================================*/
create table LAO_OPERATORS
(
   OPERATORS_ID         numeric(8,0) not null comment '？？？？？？ID',
   OPERATORS_NAME       varchar(100) comment '？？？？？？？？？？',
   OPERATORS_P          numeric(8,0) comment '？？？？？？？？？？',
   UPDATEDATE           datetime comment '？？？？？？？？',
   CREATEDATE           datetime comment '？？？？？？？？',
   STATUS_DEAL_CLASS    varchar(100) comment '？？？？？？？？？？？？？？？？',
   PLAN_DEAL_CLASS      varchar(100) comment '？？？？？？？？？？？？？？？？？？',
   TRAFFIC_QUERY_CLASS  varchar(100) comment '？？？？？？？？？？？？？？',
   QUERY_TEL_CLASS      varchar(100) comment '？？？？？？？？？？？？',
   SEND_SEM_CLASS       varchar(40),
   QUERY_CARD_STATUS    varchar(100) comment '？？？？？？？？？？',
   BATCH_QUERY_CLASS    varchar(100) comment '？？？？？？？？？？？？？？？？？？',
   ASYNC                varchar(2) comment '0 否 1 是',
   primary key (OPERATORS_ID),
   check ("OPERATORS_ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: IDX_LAO_OPERATORS_NAME                                */
/*==============================================================*/
create index IDX_LAO_OPERATORS_NAME on LAO_OPERATORS
(
   OPERATORS_NAME
);

/*==============================================================*/
/* Table: LAO_OPERATORSBILL_RESULT                              */
/*==============================================================*/
create table LAO_OPERATORSBILL_RESULT
(
   BATCH_ID             numeric(16,0) not null,
   CYCLE_ID             numeric(6,0),
   OPERATORS_ID         numeric(8,0),
   TOTAL_COST           numeric(16,0) comment '？？？？？？？？？？',
   GLA_TOTAL_COST       numeric(16,0),
   SUM_NUM              numeric(16,0),
   SUCC_NUM             numeric(16,0),
   FAIL_NUM             numeric(16,0),
   UPDATE_TIME          datetime,
   UPDATE_ACCOUNT_ID    varchar(40),
   REMARK               varchar(200),
   primary key (BATCH_ID),
   check ("BATCH_ID" IS NOT NULL)
);

alter table LAO_OPERATORSBILL_RESULT comment '？？？？？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_OPERATORS_BILL                                    */
/*==============================================================*/
create table LAO_OPERATORS_BILL
(
   OPERATORS_BILL_ID    numeric(16,0) not null,
   PARTITION_ID         numeric(4,0) comment '？？？？？？？？？？',
   CYCLE_ID             numeric(6,0),
   OUT_BILL_ID          varchar(50),
   OPERATORS_ID         numeric(8,0),
   CHANNEL_CUST_ID      numeric(16,0),
   USER_ID              numeric(16,0),
   ICCID                varchar(40),
   MSISDN               varchar(40),
   OPERATORS_PID        varchar(100) comment '？？？？lao_operator_plan.OPERATORS_PID？？？？',
   PLAN_ID              numeric(8,0),
   PRODUCT_NAME         varchar(100),
   USE_COUNT            varchar(50),
   FEE                  numeric(16,0),
   REAL_FEE             numeric(16,0),
   BALANCE_TAG          char(1) comment '0-？？？？？？？？1-？？？？？？？？2-？？？？？？',
   GLA_FEE              numeric(16,0),
   GLA_RESULT           varchar(500),
   RECV_TIME            datetime,
   UPDATE_TIME          datetime,
   UPDATE_ACCOUNT_ID    varchar(40),
   RSRV_INFO1           varchar(200),
   RSRV_INFO2           varchar(200),
   primary key (OPERATORS_BILL_ID),
   check ("OPERATORS_BILL_ID" IS NOT NULL)
);

alter table LAO_OPERATORS_BILL comment '？？？？？？？？？？？？？？';

/*==============================================================*/
/* Index: INDEX_LAO_OPERATORS_BILL_ICCID                        */
/*==============================================================*/
create index INDEX_LAO_OPERATORS_BILL_ICCID on LAO_OPERATORS_BILL
(
   ICCID
);

/*==============================================================*/
/* Table: LAO_OPERATORS_BILL_BAK                                */
/*==============================================================*/
create table LAO_OPERATORS_BILL_BAK
(
   OPERATORS_BILL_ID    numeric(16,0) not null,
   PARTITION_ID         numeric(4,0),
   CYCLE_ID             numeric(6,0),
   OUT_BILL_ID          varchar(50),
   OPERATORS_ID         numeric(8,0),
   CHANNEL_CUST_ID      numeric(16,0),
   USER_ID              numeric(16,0),
   ICCID                varchar(40),
   MSISDN               varchar(40),
   OPERATORS_PID        varchar(100),
   PLAN_ID              numeric(8,0),
   PRODUCT_NAME         varchar(100),
   USE_COUNT            varchar(50),
   FEE                  numeric(16,0),
   REAL_FEE             numeric(16,0),
   BALANCE_TAG          char(1),
   GLA_FEE              numeric(16,0),
   GLA_RESULT           varchar(500),
   RECV_TIME            datetime,
   UPDATE_TIME          datetime,
   UPDATE_ACCOUNT_ID    varchar(40),
   RSRV_INFO1           varchar(200),
   RSRV_INFO2           varchar(200),
   check ("OPERATORS_BILL_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_OPERATORS_CYCLE                                   */
/*==============================================================*/
create table LAO_OPERATORS_CYCLE
(
   CYC_ID               numeric(8,0) not null,
   ID_TYPE              char(1) comment '0-？？？？？？？？1-？？？？？？？？？？2-？？？？？？？？',
   ID_VALUE             varchar(50),
   CYCLE_DAY            numeric(2,0),
   START_DATE           datetime,
   END_DATE             datetime,
   UPDATE_TIME          datetime,
   UPDATE_ACCOUNT_ID    varchar(40),
   REMARK               varchar(200),
   MONTH_ACCT_STATUS    varchar(4) not null comment '0-？？？？？？？？1-？？？？？？？？2-？？？？？？？？？？',
   primary key (CYC_ID),
   check ("CYC_ID" IS NOT NULL)
);

alter table LAO_OPERATORS_CYCLE comment '？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_OPERATOR_PLAN                                     */
/*==============================================================*/
create table LAO_OPERATOR_PLAN
(
   PLAN_ID              numeric(8,0) not null comment '？？？？？？？？',
   PLAN_NAME            varchar(200) comment '？？？？？？？？？？？？',
   OPERATORS            numeric(8,0) comment '？？？？？？？？？？',
   OPERATORS_PID        varchar(50) comment '？？？？？？？？？？？？',
   TIME_LENGTH          numeric(8,0) comment '？？？？？？？？',
   TIME_UNIT            varchar(2) comment '？？？？？？？？',
   QUANTITY_MAX         numeric(8,0) comment '？？？？？？',
   QUANTITY_UNIT        char(1) comment '？？？？？？',
   EXP_DATE             datetime comment '？？？？？？？？',
   PLAN_STATE           char(1) comment '？？？？？？？？',
   ENABLE_TAG           varchar(1) comment '？？？？？？？？ ？？？？？？？？？？0 ？？？？？？？？？？？？1 ？？？？？？？？？？？？？？？？2',
   INVALID_TYPE         varchar(1) comment '？？？？？？？？ ？？？？？？1 ？？？？？？？？0',
   PLAN_TYPE            varchar(1) comment '？？？？？？？？',
   COST_PRICE           varchar(20) comment '？？？？？？',
   PLAN_CLASSIFY        varchar(10) comment '1流量APN1 2流量APN2 3语音 4短信',
   primary key (PLAN_ID),
   check (ENABLE_TAG is null or (ENABLE_TAG in ('0','1','2')))
);

/*==============================================================*/
/* Table: LAO_PERIPHERAL_LOG_0601                               */
/*==============================================================*/
create table LAO_PERIPHERAL_LOG_0601
(
   CUST_ID              numeric(16,0),
   IP_ADDRESS           varchar(40),
   USER_NAME            varchar(50),
   SERVER_NAME          varchar(50),
   IS_SUCCESS           char(1),
   ERROR_CODE           varchar(10),
   REQ_INFO             varchar(1000),
   RSP_INFO             varchar(2300),
   ACCESS_DATE          datetime,
   PARA_NAME1           varchar(100),
   PARA_NAME2           varchar(100)
);

/*==============================================================*/
/* Table: LAO_PERIPHERAL_SYS_ACCESS_LOG                         */
/*==============================================================*/
create table LAO_PERIPHERAL_SYS_ACCESS_LOG
(
   CUST_ID              numeric(16,0),
   IP_ADDRESS           varchar(40),
   USER_NAME            varchar(50),
   SERVER_NAME          varchar(50),
   IS_SUCCESS           char(1),
   ERROR_CODE           varchar(10),
   REQ_INFO             varchar(1000),
   RSP_INFO             varchar(2300),
   ACCESS_DATE          datetime,
   PARA_NAME1           varchar(100),
   PARA_NAME2           varchar(100)
);

/*==============================================================*/
/* Table: LAO_PICTURES                                          */
/*==============================================================*/
create table LAO_PICTURES
(
   PIC_ID               numeric(16,0) not null,
   PIC_NAME             varchar(600) not null,
   PIC_CONTENT          longblob,
   REMARK               varchar(30),
   primary key (PIC_ID),
   check ("PIC_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_POOL                                              */
/*==============================================================*/
create table LAO_POOL
(
   EID                  varchar(40),
   E_NAME               varchar(50),
   POOL_ID              varchar(20),
   POOL_DESC            varchar(256),
   IN_MONTH             numeric(6,0),
   UPDATE_TIME          datetime,
   UPDATE_STAFF_ID      varchar(20),
   ID                   numeric(16,0) not null,
   primary key (ID)
);

/*==============================================================*/
/* Table: LAO_POOL_MEMBER                                       */
/*==============================================================*/
create table LAO_POOL_MEMBER
(
   EID                  varchar(40),
   POOL_ID              varchar(20),
   MSISDN               varchar(50),
   ICCID                varchar(100),
   IN_MONTH             numeric(6,0),
   UPDATE_TIME          datetime,
   UPDATE_STAFF_ID      varchar(20),
   ID                   numeric(16,0) not null,
   primary key (ID)
);

/*==============================================================*/
/* Table: LAO_POOL_USEINFO                                      */
/*==============================================================*/
create table LAO_POOL_USEINFO
(
   ID                   numeric(16,0) not null,
   EID                  varchar(40),
   POOL_ID              varchar(20),
   USED_VOLUME          numeric(16,2),
   REMAINING_VOLUME     numeric(16,2),
   TOTAL_VOLUME         numeric(16,2),
   IN_MONTH             numeric(6,0),
   UPDATE_TIME          datetime,
   UPDATE_STAFF_ID      varchar(20),
   primary key (ID)
);

/*==============================================================*/
/* Table: LAO_POSITION_INFO                                     */
/*==============================================================*/
create table LAO_POSITION_INFO
(
   ID                   numeric(16,0) not null comment '？？？？ID',
   ICCID                varchar(50) comment 'iccid',
   LATITUDE             numeric(10,7) comment '？？？？',
   LONGTITUDE           numeric(10,7) comment '？？？？',
   ADDRESS              varchar(200) comment '？？？？？？？？？？？？',
   SPEED                numeric(5,3) comment '？？？？',
   ERRORINFO            varchar(200) comment '？？？？？？？？？？？？？？？？？？？？',
   MCC                  varchar(5) comment '？？？？？？？？？？？？？？？？？？460',
   MNC                  varchar(5) comment '？？？？？？？？？？？？？？？？？？？？？？00？？02？？04？？07？？？？？？？？01？？06？？09？？？？？？？？03？？05？？11',
   MBASESTATIONID       numeric(5,0) comment '？？？？？？？？？？？？',
   MSYSTEMID            numeric(5,0) comment '？？？？？？？？？？？？',
   MNETWORKID           numeric(5,0) comment '？？？？？？？？？？？？',
   LAC                  numeric(10,0) comment '？？？？？？',
   CID                  numeric(10,0) comment '？？？？？？',
   CREATETIME           datetime not null default 'SYSDATE' comment '？？？？？？？？',
   UPDATETIME           datetime comment '？？？？？？？？',
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_PROVIDE_SERVER                                    */
/*==============================================================*/
create table LAO_PROVIDE_SERVER
(
   SERVER_ID            numeric(16,0) not null,
   SERVER_NAME          varchar(50),
   SERVER_TAG           varchar(50),
   OPERATION_TAG        varchar(50),
   PUBLISH_DATE         datetime,
   SERVER_OPEN          char(1),
   SERVER_DESC          varchar(100),
   PARA_NAME1           varchar(100),
   PARA_NAME2           varchar(100),
   primary key (SERVER_ID),
   check ("SERVER_ID" IS NOT NULL)
);

alter table LAO_PROVIDE_SERVER comment '？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_RATECHANGE                                        */
/*==============================================================*/
create table LAO_RATECHANGE
(
   ID                   numeric(8,0) not null,
   ICCID                varchar(50),
   OLDPLAN              varchar(100),
   NEWPLAN              varchar(100),
   CREATETIME           timestamp,
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_REALNAME_VERIFY                                   */
/*==============================================================*/
create table LAO_REALNAME_VERIFY
(
   ID                   numeric(8,0) not null comment '？？？？id',
   USERID               varchar(100) comment '？？？？？？lenovoId',
   IDTYPE               numeric(1,0) comment '？？？？？？？？？？ 1 ？？？？？？',
   REALNAME             varchar(30) comment '？？？？？？？？',
   IDNUM                varchar(50) comment '？？？？？？？？',
   HANDPICURL           varchar(256) comment '？？？？？？？？？？url',
   FRONTPICURL          varchar(256) comment '？？？？？？url',
   BACKPICURL           varchar(256) comment '？？？？？？url',
   TEL                  varchar(30) comment '？？？？',
   VERIFYSTATUS         numeric(1,0) comment '？？？？？？？？ 1 ？？？？ -1 ？？？？ 2 ？？？？？？？？？？？？ 3 ？？？？？？？？？？？？？？',
   FAILTIMES            numeric(10,0) comment '？？？？？？？？',
   CREATETIME           datetime comment '？？？？？？？？',
   UPDATETIME           datetime comment '？？？？？？？？',
   ICCID                varchar(100) comment 'ICCID',
   PHOTOURL             varchar(256) comment '？？？？？？？？？？URL',
   primary key (ID),
   check ("ID" IS NOT NULL)
);

alter table LAO_REALNAME_VERIFY comment '？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_REPT_INFO                                         */
/*==============================================================*/
create table LAO_REPT_INFO
(
   REPT_ID              numeric(16,0) not null comment '报表ID',
   TRADE_TYPE_CODE      numeric(4,0) comment '业务类型',
   REPT_NAME            varchar(100) comment '报表名称',
   DISPLAY_INFO         varchar(1000) comment '展示字段集合',
   COL_IDS              varchar(4000) comment '展示字段ID集合',
   COL_ATTR             varchar(1000) comment '展示字段属性',
   COND_COLS            varchar(1000) comment '条件参数集合',
   COND_SOURCE          varchar(2) comment '条件参数来源',
   DISPLAY_TYPE         varchar(4) comment '展示类型',
   VALID_TAG            char(1) comment '有效标识',
   RECV_TIME            datetime comment '入表时间',
   UPDATE_TIME          datetime comment '更新时间',
   REMARK               varchar(200) comment '备注',
   COL_KEY              varchar(1000) comment '？？？？？？？？？？',
   SQL_INFO             text,
   primary key (REPT_ID),
   check ("REPT_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_ROLE_SERVER_CONN                                  */
/*==============================================================*/
create table LAO_ROLE_SERVER_CONN
(
   ROLE_ID              numeric(16,0) not null,
   SERVER_ID            numeric(16,0) not null,
   check ("ROLE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_RULE_DEF                                          */
/*==============================================================*/
create table LAO_RULE_DEF
(
   RULEGROUP_ID         numeric(11,0),
   RULEGROUP_NAME       varchar(200),
   RULE_ID              numeric(11,0) not null,
   RULE_NAME            varchar(200) comment '？？？？',
   COND_STAT            varchar(4000) comment '？？？？？？？？？？？？？？？？？？？？？？？？？？ #COND1#  > 480 & #COND2# < 400？？？？？？？？？？COND1？？COND2？？LAO_RULE_PARAM？？？？？？？？RULE_ID？？？？？？？？？？？？？？？？？？？？？？？？？？java？？？？js？？？？？？？？？？？？？？？？？？TRUE？？？？？？？？？？？？CAL_FORMULA？？？？？？？？？？？？？？？？？？',
   CAL_FORMULA          varchar(4000) comment '？？？？？？？？？？？？？？？？480 * 0.1+ (#VAL1# -  480) * 0.5？？？？？？？？？？VAL1？？LAO_RULE_PARAM？？？？？？？？RULE_ID？？？？？？？？？？？？？？？？？？？？？？？？？？java？？？？js？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？',
   PRIORITY             numeric(5,0) comment '？？？？？？',
   OPER_ID              varchar(40),
   REMARK               varchar(200) comment '？？？？',
   primary key (RULE_ID),
   check ("RULE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_RULE_REL                                          */
/*==============================================================*/
create table LAO_RULE_REL
(
   RULEGROUP_ID         numeric(11,0) not null comment '？？？？？？？？',
   CHANNEL_CUST_ID      numeric(16,0) not null,
   SALE_MANAGER         varchar(40),
   EFF_DATE             datetime not null comment '？？？？？？？？',
   EXP_DATE             datetime comment '？？？？？？？？',
   EPARCHY_CODE         varchar(4) comment '？？？？ZZZZ',
   STATE                varchar(3) comment '？？？？',
   OPER_ID              varchar(40) comment '？？？？？？？？',
   UPDATE_TIME          datetime comment '？？？？？？？？',
   REMARK               varchar(400) comment '？？？？？？？？',
   primary key (RULEGROUP_ID, CHANNEL_CUST_ID, EFF_DATE),
   check ("RULEGROUP_ID" IS NOT NULL)
);

alter table LAO_RULE_REL comment '？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_SERVER_PROVIDE_VERIFIY                            */
/*==============================================================*/
create table LAO_SERVER_PROVIDE_VERIFIY
(
   ID                   numeric(16,0) not null,
   RANDOMID             varchar(30),
   CUSTID               varchar(30),
   SERVERNAME           varchar(50),
   CREATEDATE           datetime,
   PARAM1               varchar(200),
   PARAM2               varchar(200),
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_SERVICE                                           */
/*==============================================================*/
create table LAO_SERVICE
(
   SERVICE_ID           numeric(8,0) not null,
   SERVICE_NAME         varchar(100),
   CREATEDATE           datetime,
   UPDATEDATE           datetime,
   STARTDATE            datetime,
   ENDDATE              datetime,
   OPERATORS_ID         numeric(8,0),
   primary key (SERVICE_ID),
   check ("SERVICE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_SERVICE_STATUS                                    */
/*==============================================================*/
create table LAO_SERVICE_STATUS
(
   STATE_CODE           varchar(10) not null comment '？？？？？？？？ID',
   SERVICE_ID           numeric(8,0) comment '？？？？ID',
   SERVICE_NAME         varchar(100) comment '？？？？？？？？？？？？',
   CREATEDATE           datetime comment '？？？？？？？？',
   UPDATEDATE           datetime comment '？？？？？？？？',
   STARTDATE            datetime comment '？？？？？？？？？？？？',
   ENDDATE              datetime comment '？？？？？？？？',
   OPERATORS_ID         numeric(8,0) comment '？？？？？？？？？？',
   STATECHANGE_ID       varchar(25) comment '？？？？？？？？ID',
   OUTSIDESTATE         varchar(20),
   primary key (STATE_CODE),
   check ("STATE_CODE" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_SERVICE_STATUS_CHG                                */
/*==============================================================*/
create table LAO_SERVICE_STATUS_CHG
(
   ID                   numeric(8,0) not null comment '主键',
   TRADE_TYPE_CODE      varchar(10) comment '业务类型',
   OLD_STATE_CODE       varchar(20) comment '老状态',
   NEW_STATE_CODE       varchar(20) comment '新状态',
   OPERATORS_ID         numeric(8,0) comment '运行商编号',
   REMARK               varchar(40) comment '说明',
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_SMS_DELIVER                                       */
/*==============================================================*/
create table LAO_SMS_DELIVER
(
   DELIVER_ID           numeric(16,0) not null comment 'ID',
   SRC_NUMBER           varchar(16) comment '？？？？？？？？？？？？',
   DEST_NUMBER          varchar(16) comment '？？？？？？？？？？？？',
   SMS_CONTENT          varchar(4000) comment '？？？？？？？？',
   PUSH_DATE            datetime comment '？？？？？？？？',
   SMS_ID               numeric(16,0) comment '？？sms_info？？？？ID',
   PUSH_STATE           numeric(11,0) comment '？？？？？？？？ 0 ？？？？ 1 ？？？？  2？？？？？？',
   PUSH_DESC            varchar(1000) comment '？？？？？？？？',
   check ("DELIVER_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_SMS_INFO                                          */
/*==============================================================*/
create table LAO_SMS_INFO
(
   SMS_ID               numeric(16,0) not null,
   CHANNEL_CUST_ID      numeric(16,0),
   USER_ID              numeric(16,0),
   ICCID                varchar(40),
   MSISDN               varchar(40),
   SMS_CONTEXT          varchar(4000),
   SEND_TIME            datetime,
   TEMPLETE_ID          numeric(8,0),
   OPER_ID              varchar(40),
   DEAL_TAG             char(1) comment '1-？？？？？？？？2-？？？？？？？？3-？？？？？？？？',
   RESULT_INFO          varchar(500),
   ERROR_CODE           varchar(50),
   SMS_CORP             varchar(10) comment '1000-？？？？？？？？？？1001-？？？？？？？？？？1002-？？？？？？？？？？1003-？？？？？？？？？？',
   UPDATE_TIME          datetime,
   REMARK               varchar(200),
   OUT_SMSID            varchar(32),
   primary key (SMS_ID),
   check ("SMS_ID" IS NOT NULL)
);

alter table LAO_SMS_INFO comment '？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_SMS_TMPL                                          */
/*==============================================================*/
create table LAO_SMS_TMPL
(
   TEMPLETE_ID          numeric(8,0) not null,
   SMS_TYPE             varchar(4),
   TMPL_CONTEXT         varchar(4000),
   START_DATE           datetime,
   END_DATE             datetime,
   SMS_CORP             varchar(10),
   REMARK               varchar(200),
   primary key (TEMPLETE_ID),
   check ("TEMPLETE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_SS_ACCESS_LOG                                     */
/*==============================================================*/
create table LAO_SS_ACCESS_LOG
(
   LOG_ID               numeric(16,0) not null,
   LOGIN_ID             varchar(20),
   LOGIN_NAME           varchar(100),
   ICCID                varchar(100),
   URL                  varchar(200),
   THIRD_PARTY_ACCOUNT_ID varchar(100),
   THIRD_PARTY_ACCOUNT_TYPE varchar(20),
   ACCESS_TIME          datetime,
   TRADE_ID             varchar(100),
   USER_IP              varchar(20),
   check ("LOG_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_SS_ACCOUNT                                        */
/*==============================================================*/
create table LAO_SS_ACCOUNT
(
   ACCONUT_ID           numeric(16,0) not null,
   LOGIN_NAME           varchar(200) not null,
   NICKNAME             varchar(200),
   PASSWORD             varchar(255) not null,
   SALT                 varchar(64),
   STATUS               char(1),
   CREATE_DATE          datetime,
   REMARK               varchar(100),
   RELATED_TYPE         varchar(20),
   RELATED_ID           varchar(50),
   CUST_ID              numeric(16,0) comment '？？？？？？？？？？？？？？？？ID',
   PLAIN_PASSWORD       varchar(255),
   FST_STRUCT           varchar(100),
   SEC_STRUCT           varchar(100),
   primary key (ACCONUT_ID),
   check ("ACCONUT_ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: UNIQUE_INDEX_LOGIN_NAME                               */
/*==============================================================*/
create unique index UNIQUE_INDEX_LOGIN_NAME on LAO_SS_ACCOUNT
(
   LOGIN_NAME
);

/*==============================================================*/
/* Table: LAO_SS_ACCOUNT_BAK                                    */
/*==============================================================*/
create table LAO_SS_ACCOUNT_BAK
(
   ACCONUT_ID           numeric(16,0) not null,
   LOGIN_NAME           varchar(200) not null,
   NICKNAME             varchar(200),
   PASSWORD             varchar(255) not null,
   SALT                 varchar(64),
   STATUS               char(1),
   CREATE_DATE          datetime,
   REMARK               varchar(100),
   RELATED_TYPE         varchar(20),
   RELATED_ID           varchar(50),
   CUST_ID              numeric(16,0),
   PLAIN_PASSWORD       varchar(255),
   check ("ACCONUT_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_SS_ACCOUNT_ROLE                                   */
/*==============================================================*/
create table LAO_SS_ACCOUNT_ROLE
(
   USER_ID              numeric(16,0) not null,
   ROLE_ID              numeric(16,0) not null,
   primary key (USER_ID, ROLE_ID),
   check ("USER_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_SS_LOGIN_LOG                                      */
/*==============================================================*/
create table LAO_SS_LOGIN_LOG
(
   ID                   numeric(16,0) not null,
   ACCOUNT_ID           numeric(16,0),
   LOGIN_TIME           datetime,
   SESSION_ID           varchar(200),
   IP                   varchar(100),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_SS_NAVIGATION                                     */
/*==============================================================*/
create table LAO_SS_NAVIGATION
(
   NAVIGATION_ID        numeric(16,0) not null,
   URL                  varchar(255),
   NAV_NAME             varchar(50) not null,
   NAV_PICTURE          varchar(50),
   URL_LEVEL            char(1),
   PARENT_ID            numeric(16,0),
   SHOW_INDEX           numeric(8,0),
   STATUS               varchar(2),
   primary key (NAVIGATION_ID),
   check ("NAVIGATION_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_SS_RESOURCE                                       */
/*==============================================================*/
create table LAO_SS_RESOURCE
(
   RESOURCE_ID          numeric(16,0) not null,
   RES_CODE             varchar(20) not null,
   RES_NAME             varchar(100),
   primary key (RESOURCE_ID),
   check ("RESOURCE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_SS_ROLE                                           */
/*==============================================================*/
create table LAO_SS_ROLE
(
   ROLE_ID              numeric(16,0) not null,
   ROLE_NAME            varchar(64),
   DESCRIPTION          varchar(255),
   AUTH_TYPE            char(1),
   PRIORITY             numeric(3,0),
   primary key (ROLE_ID),
   check (AUTH_TYPE is null or (AUTH_TYPE in ('0','1')))
);

/*==============================================================*/
/* Table: LAO_SS_ROLE_CUST                                      */
/*==============================================================*/
create table LAO_SS_ROLE_CUST
(
   ROLE_ID              numeric(16,0) not null,
   CUST_ID              numeric(16,0) not null,
   primary key (ROLE_ID, CUST_ID),
   check ("ROLE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_SS_ROLE_NAVIGATION                                */
/*==============================================================*/
create table LAO_SS_ROLE_NAVIGATION
(
   ROLE_ID              numeric(16,0) not null,
   NAVIGATION_ID        numeric(16,0) not null,
   primary key (ROLE_ID, NAVIGATION_ID),
   check ("ROLE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_SS_ROLE_RESOURCE                                  */
/*==============================================================*/
create table LAO_SS_ROLE_RESOURCE
(
   ROLE_ID              numeric(16,0) not null,
   RESOURCE_ID          numeric(16,0) not null,
   primary key (ROLE_ID, RESOURCE_ID),
   check ("ROLE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_SS_STATIC                                         */
/*==============================================================*/
create table LAO_SS_STATIC
(
   STATIC_ID            numeric(8,0) not null,
   STATIC_CODE          varchar(100),
   STATIC_NAME          varchar(100),
   TAB_NAME             varchar(100),
   COL_NAME             varchar(100),
   CUST_ID              varchar(100),
   PID                  numeric(8,0),
   primary key (STATIC_ID),
   check ("STATIC_ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: LAO_SS_STATIC_CODE                                    */
/*==============================================================*/
create index LAO_SS_STATIC_CODE on LAO_SS_STATIC
(
   STATIC_CODE
);

/*==============================================================*/
/* Table: LAO_SS_STATIC_BAK                                     */
/*==============================================================*/
create table LAO_SS_STATIC_BAK
(
   STATIC_ID            numeric(8,0) not null,
   STATIC_CODE          varchar(100),
   STATIC_NAME          varchar(100),
   TAB_NAME             varchar(100),
   COL_NAME             varchar(100),
   CUST_ID              varchar(100),
   PID                  numeric(8,0),
   check ("STATIC_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_STAFF                                             */
/*==============================================================*/
create table LAO_STAFF
(
   STAFF_ID             varchar(50),
   STAFF_NAME           varchar(50),
   REGION_ID            varchar(50),
   REGION_CODE          varchar(10),
   REGION_NAME          varchar(50),
   STAFF_PHONE          varchar(50)
);

/*==============================================================*/
/* Table: LAO_TARIFF_PLAN                                       */
/*==============================================================*/
create table LAO_TARIFF_PLAN
(
   ID                   numeric(19,0) not null,
   TARIFFPLANNAME       varchar(255),
   TARIFFPLANEXPDATE    datetime,
   TARIFFPLANLENGTH     numeric(20,0),
   TARIFFPLANSTAUTS     numeric(1,0),
   CREATEDATE           datetime,
   MODIFYDATE           datetime,
   TARIFFPLANCAPACITY   numeric(20,3) default 0.000,
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_TASK                                              */
/*==============================================================*/
create table LAO_TASK
(
   ID                   numeric(16,0) not null comment '？？？？id',
   CHANNEL_CUST_ID      numeric(16,0) comment '？？？？custid',
   DATA1                numeric(12,2) default -1 comment '？？？？？？？？？？？？？？ ？？？？？？KB',
   DATA2                numeric(12,2) default -1 comment '？？？？？？？？？？？？？？ ？？？？？？KB',
   BINARY_DATA          longblob comment '？？？？？？？？？？',
   OPER_ID              numeric(16,0) comment '？？？？？？ID',
   TASK_STATE           char(1) comment '0？？？？？？？？？？？？？？1？？？？？？？？？？？？2？？？？？？？？？？',
   START_TIME           datetime comment '？？？？？？？？？？？？',
   END_TIME             datetime comment '？？？？？？？？？？？？',
   REMARK               varchar(200) comment '？？？？？？？？',
   TYPE                 varchar(1) comment '？？？？？？？？？？1？？？？？？？？？？？？？？？？？？？？2？？？？？？？？？？？？？？？？',
   CREATE_TIME          datetime default 'SYSDATE' comment '？？？？？？？？',
   COUNT                numeric(8,0) comment '？？？？？？？？？？？？？？？？？？？？',
   VALUE1               varchar(50) comment '？？？？lao_user？？？？？？value1',
   VALUE2               varchar(50) comment '？？？？lao_user？？？？？？value2',
   check ("ID" IS NOT NULL)
);

alter table LAO_TASK comment '？？？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_TEMP_CHENJJ7                                      */
/*==============================================================*/
create table LAO_TEMP_CHENJJ7
(
   ICCID                varchar(50),
   PROVINCE_NAME        varchar(50),
   CITY                 varchar(50),
   OPERATORS            varchar(50)
);

/*==============================================================*/
/* Table: LAO_TEMP_CHENJJ7_BUCHONG                              */
/*==============================================================*/
create table LAO_TEMP_CHENJJ7_BUCHONG
(
   ICCID                varchar(50),
   PROVINCE_NAME        varchar(50),
   CITY                 varchar(50),
   OPERATORS            varchar(50)
);

/*==============================================================*/
/* Table: LAO_TEMP_ICCID                                        */
/*==============================================================*/
create table LAO_TEMP_ICCID
(
   ICCID                varchar(50),
   MSISDN               varchar(50)
);

/*==============================================================*/
/* Table: LAO_TEMP_TELECOM_1202_CHENJJ7                         */
/*==============================================================*/
create table LAO_TEMP_TELECOM_1202_CHENJJ7
(
   MSISDN               varchar(50),
   ICCID                varchar(50),
   STATUS               varchar(50)
);

/*==============================================================*/
/* Table: LAO_TEMP_ZHAO                                         */
/*==============================================================*/
create table LAO_TEMP_ZHAO
(
   ICCID                varchar(50),
   PROVINCE_NAME        varchar(50),
   CITY                 varchar(50),
   OPERATORS            varchar(50)
);

/*==============================================================*/
/* Table: LAO_TEMP_ZHAO1                                        */
/*==============================================================*/
create table LAO_TEMP_ZHAO1
(
   ICCID                varchar(50),
   MSISDN               varchar(50)
);

/*==============================================================*/
/* Table: LAO_TEMP_ZHAO2                                        */
/*==============================================================*/
create table LAO_TEMP_ZHAO2
(
   BATCHID              varchar(50),
   ICCID                varchar(50),
   USERCONT             varchar(50),
   DATACYCLE            varchar(50),
   RECVTIME             varchar(50),
   OPERATORS            varchar(50),
   DATAREMAIN           varchar(50)
);

/*==============================================================*/
/* Table: LAO_TEMP_ZZ                                           */
/*==============================================================*/
create table LAO_TEMP_ZZ
(
   ICCID                varchar(50)
);

/*==============================================================*/
/* Table: LAO_TRADE                                             */
/*==============================================================*/
create table LAO_TRADE
(
   TRADE_ID             numeric(16,0) not null comment '？？？？？？？？？？？？',
   ACCEPT_MONTH         numeric(2,0) not null comment '？？？？？？？？',
   TRADE_TYPE_CODE      numeric(4,0) not null comment '？？？？？？？？？？？？',
   IN_MODE_CODE         char(1) not null comment '？？？？？？？？？？？？ ？？？？？？？？？？0',
   SUBSCRIBE_STATE      char(1) not null default '0' comment '？？？？？？？？',
   GOODS_ID             numeric(16,0) comment '？？？？？？？？',
   USER_ID              numeric(16,0) comment '？？？？？？？？',
   CHANNAL_CUST_ID      numeric(16,0) comment '？？？？？？？？？？？？',
   CUST_ID              numeric(16,0) comment '？？？？？？？？',
   ICCID                varchar(40) comment '？？？？？？',
   CUST_NAME            varchar(100) comment '？？？？？？？？',
   ACCEPT_DATE          datetime comment '？？？？？？？？',
   EXEC_TIME            datetime comment '？？？？？？？？',
   FINISH_DATE          datetime comment '？？？？？？？？',
   FEE                  varchar(30) default '0' comment '？？？？',
   INVOICE_NO           varchar(30) comment '？？？？？？？？',
   FEE_STATE            char(1) comment '？？？？？？？？',
   FEE_TIME             datetime comment '？？？？？？？？',
   FEE_STAFF_ID         varchar(20) comment '？？？？？？？？',
   IF_MAINTENANCE       char(1) default '0' comment '？？？？？？？？',
   ORDER_ID             numeric(16,0) comment '？？？？Id',
   GOODS_RELEASE_ID     numeric(16,0) comment '？？？？？？？？id',
   primary key (TRADE_ID, ACCEPT_MONTH),
   check ("TRADE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_TRADEFEE_SUB                                      */
/*==============================================================*/
create table LAO_TRADEFEE_SUB
(
   TRADE_ID             numeric(16,0) not null comment '？？？？？？？？',
   ACCEPT_MONTH         numeric(2,0) not null comment '？？？？？？？？',
   GOODS_ID             numeric(16,0) not null comment '？？？？ID',
   OLDFEE               varchar(30) default '0' comment '？？？？？？？？',
   FEE                  varchar(30) default '0' comment '？？？？？？？？',
   DISCONT_ID           numeric(16,0) comment '？？？？Id',
   DERATE_REMARK        varchar(100) comment '？？？？？？？？',
   CALCULATE_TAG        char(1) comment '？？？？？？？？？？？？  N？？？？？？？？？？ Y？？？？？？？？？？',
   PAY_TAG              char(1) comment '？？？？？？？？ 0？？？？？？？？ 1？？？？？？？？',
   PAY_ORDER_ID         varchar(50) comment '？？？？？？？？？？',
   PAY_DATE             datetime comment '？？？？？？？？',
   FEE_STAFF_ID         char(8) comment '？？？？？？？？ admin',
   PAY_TYPE             char(1) comment '？？？？？？？？ 1？？？？？？？？ 2？？？？？？ 3？？？？？？',
   ACCEPT_DATE          datetime comment '？？？？？？？？',
   primary key (TRADE_ID, ACCEPT_MONTH, GOODS_ID),
   check ("TRADE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_TRADEFEE_SUB_BAK                                  */
/*==============================================================*/
create table LAO_TRADEFEE_SUB_BAK
(
   TRADE_ID             numeric(16,0) not null,
   ACCEPT_MONTH         numeric(2,0) not null,
   GOODS_ID             numeric(16,0) not null,
   OLDFEE               numeric(11,0) not null,
   FEE                  numeric(11,0) not null,
   DISCONT_ID           numeric(16,0),
   DERATE_REMARK        varchar(100),
   CALCULATE_TAG        char(1),
   PAY_TAG              char(1),
   PAY_ORDER_ID         numeric(16,0),
   PAY_DATE             datetime,
   FEE_STAFF_ID         char(8),
   PAY_TYPE             char(1),
   ACCEPT_DATE          datetime,
   check ("TRADE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_TRADE_BAK                                         */
/*==============================================================*/
create table LAO_TRADE_BAK
(
   TRADE_ID             numeric(16,0) not null,
   ACCEPT_MONTH         numeric(2,0) not null,
   TRADE_TYPE_CODE      numeric(4,0) not null,
   IN_MODE_CODE         char(1) not null,
   SUBSCRIBE_STATE      char(1) not null,
   GOODS_ID             numeric(16,0),
   USER_ID              numeric(16,0),
   CHANNAL_CUST_ID      numeric(16,0),
   CUST_ID              numeric(16,0),
   ICCID                varchar(40),
   CUST_NAME            varchar(100),
   ACCEPT_DATE          datetime,
   EXEC_TIME            datetime,
   FINISH_DATE          datetime,
   FEE                  numeric(11,0),
   INVOICE_NO           varchar(30),
   FEE_STATE            char(1),
   FEE_TIME             datetime,
   FEE_STAFF_ID         char(8),
   IF_MAINTENANCE       char(1),
   ORDER_ID             numeric(16,0),
   check ("TRADE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_TRADE_DISCNT                                      */
/*==============================================================*/
create table LAO_TRADE_DISCNT
(
   TRADE_ID             numeric(16,0) not null,
   ACCEPT_MONTH         numeric(2,0) not null,
   USER_ID              numeric(16,0) not null,
   GOODS_ID             numeric(16,0),
   DISCNT_ID            numeric(8,0) not null,
   START_DATE           datetime not null,
   END_DATE             datetime,
   MODIFY_TAG           char(1),
   ACCEPT_DATE          datetime,
   primary key (TRADE_ID, ACCEPT_MONTH, USER_ID, DISCNT_ID, START_DATE),
   check ("TRADE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_TRADE_GOODS                                       */
/*==============================================================*/
create table LAO_TRADE_GOODS
(
   TRADE_ID             numeric(16,0) not null,
   ACCEPT_MONTH         numeric(2,0) not null,
   USER_ID              numeric(16,0) not null,
   GOODS_ID             numeric(16,0) not null,
   MODIFY_TAG           char(1) not null,
   START_DATE           datetime not null,
   END_DATE             datetime,
   BI_RULES_ID          numeric(16,0),
   TRADE_DATE           datetime,
   ACCEPT_DATE          datetime,
   GOODS_RELEASE_ID     numeric(8,0),
   RELEASE_CYCLE        varchar(6),
   primary key (TRADE_ID, ACCEPT_MONTH, GOODS_ID, START_DATE),
   check ("TRADE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_TRADE_OPERATOR_PLAN                               */
/*==============================================================*/
create table LAO_TRADE_OPERATOR_PLAN
(
   TRADE_ID             numeric(16,0) not null,
   ACCEPT_MONTH         numeric(2,0) not null,
   USER_ID              numeric(16,0) not null,
   GOODS_ID             numeric(16,0) not null,
   MODIFY_TAG           char(1) not null,
   PLAN_ID              numeric(8,0) not null,
   OPERATORS_ID         numeric(8,0),
   OPERATORS_PID        varchar(50),
   ENABLE_TAG           varchar(1),
   INVALID_TYPE         varchar(1),
   START_DATE           datetime,
   END_DATE             datetime,
   PACKAGE_TYPE         char(1),
   GOODS_INDEX          numeric(2,0) not null,
   PLAN_TYPE            varchar(1),
   ACCEPT_DATE          datetime,
   PLAN_CLASSIFY        varchar(10) comment '1流量 2语音 3 短信',
   primary key (TRADE_ID, ACCEPT_MONTH, USER_ID, GOODS_ID, GOODS_INDEX),
   check (ENABLE_TAG is null or (ENABLE_TAG in ('0','1','2')))
);

/*==============================================================*/
/* Index: INDEX_TRADE_OPE_PLAN_USERID                           */
/*==============================================================*/
create index INDEX_TRADE_OPE_PLAN_USERID on LAO_TRADE_OPERATOR_PLAN
(
   USER_ID
);

/*==============================================================*/
/* Table: LAO_TRADE_RES                                         */
/*==============================================================*/
create table LAO_TRADE_RES
(
   TRADE_ID             numeric(16,0) not null,
   ACCEPT_MONTH         numeric(2,0) not null,
   USER_ID              numeric(16,0) not null,
   RES_TYPE_CODE        char(1) not null,
   RES_CODE             varchar(40) not null,
   RES_INFO1            varchar(50),
   RES_INFO2            varchar(50),
   RES_INFO3            varchar(50),
   RES_INFO4            varchar(50),
   RES_INFO5            varchar(50),
   RES_INFO6            varchar(50),
   RES_INFO7            varchar(100),
   RES_INFO8            varchar(100),
   MODIFY_TAG           char(1) not null,
   START_DATE           datetime not null,
   END_DATE             datetime not null,
   ACCEPT_DATE          datetime,
   primary key (TRADE_ID, ACCEPT_MONTH, USER_ID, RES_TYPE_CODE, RES_CODE, MODIFY_TAG),
   check ("TRADE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_TRADE_SVCSTATE                                    */
/*==============================================================*/
create table LAO_TRADE_SVCSTATE
(
   TRADE_ID             numeric(16,0) not null,
   ACCEPT_MONTH         numeric(2,0) not null,
   USER_ID              numeric(16,0) not null,
   SERVICE_ID           numeric(8,0) not null,
   STATE_CODE           char(10) not null,
   MODIFY_TAG           char(1) not null,
   START_DATE           datetime not null,
   END_DATE             datetime,
   ACCEPT_DATE          datetime,
   primary key (TRADE_ID, ACCEPT_MONTH, USER_ID, SERVICE_ID, STATE_CODE, MODIFY_TAG),
   check ("TRADE_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_TRADE_TYPE                                        */
/*==============================================================*/
create table LAO_TRADE_TYPE
(
   TRADE_TYPE_CODE      varchar(4) not null,
   TRADE_TYPE_NAME      varchar(100),
   TRADE_TYPE_DESC      varchar(200),
   NAVIGATION_ID        numeric(16,0),
   URL                  varchar(500),
   REMARK               varchar(200),
   primary key (TRADE_TYPE_CODE),
   check ("TRADE_TYPE_CODE" IS NOT NULL)
);

alter table LAO_TRADE_TYPE comment '？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_TRAFFICMM_TASK                                    */
/*==============================================================*/
create table LAO_TRAFFICMM_TASK
(
   ID                   numeric(16,0) not null comment '？？？？id',
   CHANNEL_CUST_ID      numeric(16,0) comment '？？？？custid',
   DATA1                numeric(12,2) default -1 comment '？？？？？？？？？？？？？？ ？？？？？？KB',
   DATA2                numeric(12,2) default -1 comment '？？？？？？？？？？？？？？ ？？？？？？KB',
   BINARY_DATA          longblob comment '？？？？？？？？？？',
   OPER_ID              numeric(16,0) comment '？？？？？？ID',
   TASK_STATE           char(1) comment '0？？？？？？？？？？？？？？1？？？？？？？？？？？？2？？？？？？？？？？',
   START_TIME           datetime comment '？？？？？？？？？？？？',
   END_TIME             datetime comment '？？？？？？？？？？？？',
   REMARK               varchar(200) comment '？？？？？？？？',
   TYPE                 varchar(1) comment '？？？？？？？？？？1？？？？？？？？？？？？？？？？？？？？2？？？？？？？？？？？？？？？？',
   CREATE_TIME          datetime default 'SYSDATE' comment '？？？？？？？？',
   COUNT                numeric(8,0) comment '？？？？？？？？？？？？？？？？？？？？',
   VALUE1               varchar(50) comment '？？？？lao_user？？？？？？value1',
   VALUE2               varchar(50) comment '？？？？lao_user？？？？？？value2',
   MONTH                varchar(6) comment '？？？？',
   OPERATORID           numeric(8,0),
   GOODSID              numeric(16,0),
   primary key (ID),
   check ("ID" IS NOT NULL)
);

alter table LAO_TRAFFICMM_TASK comment '？？？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_TRAFFICQUERY_LOG                                  */
/*==============================================================*/
create table LAO_TRAFFICQUERY_LOG
(
   ID                   numeric(16,0) not null,
   ICCID                varchar(30) not null,
   RESULT_CODE          varchar(50),
   RESULT_INFO          varchar(100),
   INPUT_PARAMETERS     varchar(2000),
   OUTPUT_PARAMETERS    varchar(2000),
   OPERATOR_TYPE        varchar(2),
   OPERATOR_ID          varchar(30),
   SUCCESS              varchar(30),
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_TRAFFIC_DETAIL                                    */
/*==============================================================*/
create table LAO_TRAFFIC_DETAIL
(
   BATCH_ID             numeric(16,0) not null comment '？？？？？？',
   CHANNEL_CUST_ID      numeric(16,0) comment '？？？？？？？？ID',
   USER_ID              numeric(16,0) comment '？？？？ID',
   ICCID                varchar(100) comment 'ICCID',
   MSISDN               varchar(40) comment '？？？？？？？？',
   OPERATORS_ID         numeric(10,0) comment '？？？？？？',
   USE_COUNT            numeric(16,2) comment '？？？？？？？？KB',
   DATA_CYCLE           varchar(10) comment '？？？？？？？？？？？？？？？？？？？？？？？？？？20161103',
   RECV_TIME            datetime comment '？？？？？？？？',
   REMARK               varchar(200) comment '？？？？',
   DATA_REMAINING       numeric(16,2) comment '？？？？？？？？？？KB',
   primary key (BATCH_ID),
   check ("BATCH_ID" IS NOT NULL)
);

alter table LAO_TRAFFIC_DETAIL comment '？？？？？？？？？？？？？？？？';

/*==============================================================*/
/* Index: IDX_TRAFFIC_DETAIL_CUST                               */
/*==============================================================*/
create index IDX_TRAFFIC_DETAIL_CUST on LAO_TRAFFIC_DETAIL
(
   CHANNEL_CUST_ID
);

/*==============================================================*/
/* Index: IDX_TRAFFIC_DETAIL_DATA_CYCLE                         */
/*==============================================================*/
create index IDX_TRAFFIC_DETAIL_DATA_CYCLE on LAO_TRAFFIC_DETAIL
(
   DATA_CYCLE
);

/*==============================================================*/
/* Index: IDX_TRAFFIC_DETAIL_USER_ID                            */
/*==============================================================*/
create index IDX_TRAFFIC_DETAIL_USER_ID on LAO_TRAFFIC_DETAIL
(
   USER_ID
);

/*==============================================================*/
/* Table: LAO_TRAFFIC_MM                                        */
/*==============================================================*/
create table LAO_TRAFFIC_MM
(
   BATCH_ID             numeric(16,0) not null comment '？？？？？？',
   CHANNEL_CUST_ID      numeric(16,0) comment '？？？？？？？？ID',
   USER_ID              numeric(16,0) comment '？？？？ID',
   ICCID                varchar(100) comment 'ICCID',
   MSISDN               varchar(40) comment '？？？？？？？？',
   OPERATORS_ID         numeric(8,0) comment '？？？？？？',
   USE_COUNT            numeric(16,2) comment '？？？？？？？？？？？？KB',
   DATA_CYCLE           varchar(6) comment '？？？？？？？？？？？？？？？？？？？？？？？？？？201611',
   DATA_ADDED           varchar(8) comment '？？？？？？？？？？？？？？？？？？YYYYMMDD？？？？20161114',
   RECV_TIME            datetime comment '？？？？？？？？',
   UPDATE_TIME          datetime comment '？？？？？？？？',
   REMARK               varchar(200) comment '？？？？',
   DATA_REMAINING       numeric(16,2) comment '？？？？？？？？？？KB',
   PLAN_CLASSIFY        varchar(10) comment '1流量 2语音 3 短信',
   primary key (BATCH_ID),
   check ("BATCH_ID" IS NOT NULL)
);

alter table LAO_TRAFFIC_MM comment '？？？？？？？？？？？？';

/*==============================================================*/
/* Index: IDX_TRAFFIC_MM_CUST                                   */
/*==============================================================*/
create index IDX_TRAFFIC_MM_CUST on LAO_TRAFFIC_MM
(
   CHANNEL_CUST_ID
);

/*==============================================================*/
/* Index: IDX_TRAFFIC_MM_CYCLE                                  */
/*==============================================================*/
create index IDX_TRAFFIC_MM_CYCLE on LAO_TRAFFIC_MM
(
   DATA_CYCLE
);

/*==============================================================*/
/* Index: IDX_TRAFFIC_MM_USER_ID                                */
/*==============================================================*/
create index IDX_TRAFFIC_MM_USER_ID on LAO_TRAFFIC_MM
(
   USER_ID
);

/*==============================================================*/
/* Table: LAO_TRAFFIC_MM00                                      */
/*==============================================================*/
create table LAO_TRAFFIC_MM00
(
   BATCH_ID             numeric(16,0) not null,
   CHANNEL_CUST_ID      numeric(16,0),
   USER_ID              numeric(16,0),
   ICCID                varchar(100),
   MSISDN               varchar(40),
   OPERATORS_ID         numeric(8,0),
   USE_COUNT            numeric(12,2),
   DATA_CYCLE           varchar(6),
   DATA_ADDED           varchar(8),
   RECV_TIME            datetime,
   UPDATE_TIME          datetime,
   REMARK               varchar(200),
   DATA_REMAINING       numeric(12,2),
   check ("BATCH_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_TRAFFIC_MM_170306                                 */
/*==============================================================*/
create table LAO_TRAFFIC_MM_170306
(
   BATCH_ID             numeric(16,0) not null,
   CHANNEL_CUST_ID      numeric(16,0),
   USER_ID              numeric(16,0),
   ICCID                varchar(100),
   MSISDN               varchar(40),
   OPERATORS_ID         numeric(8,0),
   USE_COUNT            numeric(16,2),
   DATA_CYCLE           varchar(6),
   DATA_ADDED           varchar(8),
   RECV_TIME            datetime,
   UPDATE_TIME          datetime,
   REMARK               varchar(200),
   DATA_REMAINING       numeric(16,2),
   check ("BATCH_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_TRAFFIC_MM_170426                                 */
/*==============================================================*/
create table LAO_TRAFFIC_MM_170426
(
   BATCH_ID             numeric(16,0) not null,
   CHANNEL_CUST_ID      numeric(16,0),
   USER_ID              numeric(16,0),
   ICCID                varchar(100),
   MSISDN               varchar(40),
   OPERATORS_ID         numeric(8,0),
   USE_COUNT            numeric(16,2),
   DATA_CYCLE           varchar(6),
   DATA_ADDED           varchar(8),
   RECV_TIME            datetime,
   UPDATE_TIME          datetime,
   REMARK               varchar(200),
   DATA_REMAINING       numeric(16,2),
   check ("BATCH_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_TRAFFIC_MM_170427                                 */
/*==============================================================*/
create table LAO_TRAFFIC_MM_170427
(
   BATCH_ID             numeric(16,0) not null,
   CHANNEL_CUST_ID      numeric(16,0),
   USER_ID              numeric(16,0),
   ICCID                varchar(100),
   MSISDN               varchar(40),
   OPERATORS_ID         numeric(8,0),
   USE_COUNT            numeric(16,2),
   DATA_CYCLE           varchar(6),
   DATA_ADDED           varchar(8),
   RECV_TIME            datetime,
   UPDATE_TIME          datetime,
   REMARK               varchar(200),
   DATA_REMAINING       numeric(16,2),
   check ("BATCH_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_TRAFFIC_MM_170605                                 */
/*==============================================================*/
create table LAO_TRAFFIC_MM_170605
(
   BATCH_ID             numeric(16,0) not null,
   CHANNEL_CUST_ID      numeric(16,0),
   USER_ID              numeric(16,0),
   ICCID                varchar(100),
   MSISDN               varchar(40),
   OPERATORS_ID         numeric(8,0),
   USE_COUNT            numeric(16,2),
   DATA_CYCLE           varchar(6),
   DATA_ADDED           varchar(8),
   RECV_TIME            datetime,
   UPDATE_TIME          datetime,
   REMARK               varchar(200),
   DATA_REMAINING       numeric(16,2),
   check ("BATCH_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_TRAFFIC_MM_17526                                  */
/*==============================================================*/
create table LAO_TRAFFIC_MM_17526
(
   BATCH_ID             numeric(16,0) not null,
   CHANNEL_CUST_ID      numeric(16,0),
   USER_ID              numeric(16,0),
   ICCID                varchar(100),
   MSISDN               varchar(40),
   OPERATORS_ID         numeric(8,0),
   USE_COUNT            numeric(16,2),
   DATA_CYCLE           varchar(6),
   DATA_ADDED           varchar(8),
   RECV_TIME            datetime,
   UPDATE_TIME          datetime,
   REMARK               varchar(200),
   DATA_REMAINING       numeric(16,2),
   check ("BATCH_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_USER                                              */
/*==============================================================*/
create table LAO_USER
(
   USER_ID              numeric(16,0) not null,
   CHANNEL_CUST_ID      numeric(16,0) not null,
   CUST_ID              numeric(16,0),
   MSISDN               varchar(50),
   OPERATORS_ID         numeric(8,0),
   SCORE_VALUE          numeric(11,0) default 0,
   CREDIT_CLASS         numeric(8,0) default 0,
   BASIC_CREDIT_VALUE   numeric(11,0) default 0,
   CREDIT_VALUE         numeric(11,0) default 0,
   ACCT_TAG             char(1) not null,
   PREPAY_TAG           char(1) not null default '0',
   IN_DATE              datetime not null,
   OPEN_DATE            datetime not null,
   REMOVE_TAG           char(1) not null comment '？？？？？？？？？？0-？？？？？？1-？？？？？？？？？？？？2-？？？？？？？？？？3-？？？？？？？？？？？？4-？？？？？？？？？？5-？？？？？？？？？？6-？？？？？？？？',
   DESTROY_TIME         datetime,
   PRE_DESTROY_TIME     datetime,
   FIRST_CALL_TIME      datetime,
   LAST_STOP_TIME       datetime,
   ICCID                varchar(100),
   DEVICE_ID            varchar(50),
   STATUS_CODE          varchar(1),
   ALIVE_CHECK_TIME     numeric(8,0),
   IMEI                 varchar(100),
   STATE_CODE           varchar(10) not null,
   ATTRIBUTE1           varchar(50),
   VALUE1               varchar(50),
   ATTRIBUTE2           varchar(50),
   VALUE2               varchar(50),
   TEST_START_DATE      datetime,
   TEST_CYCLE           numeric(2,0),
   VIC                  varchar(30),
   IMSI                 varchar(30),
   primary key (USER_ID),
   key AK_Key_1 (MSISDN),
   check ("USER_ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: IDX_LAO_USER_ATRIBUTE1                                */
/*==============================================================*/
create index IDX_LAO_USER_ATRIBUTE1 on LAO_USER
(
   ATTRIBUTE1
);

/*==============================================================*/
/* Index: IDX_LAO_USER_ATRIBUTE2                                */
/*==============================================================*/
create index IDX_LAO_USER_ATRIBUTE2 on LAO_USER
(
   ATTRIBUTE2
);

/*==============================================================*/
/* Index: IDX_LAO_USER_CUST_ID                                  */
/*==============================================================*/
create index IDX_LAO_USER_CUST_ID on LAO_USER
(
   CUST_ID
);

/*==============================================================*/
/* Index: IDX_LAO_USER_ICCID                                    */
/*==============================================================*/
create index IDX_LAO_USER_ICCID on LAO_USER
(
   ICCID
);

/*==============================================================*/
/* Index: IDX_LAO_USER_OPERATOR_ID                              */
/*==============================================================*/
create index IDX_LAO_USER_OPERATOR_ID on LAO_USER
(
   OPERATORS_ID
);

/*==============================================================*/
/* Table: LAO_USER2C_ACTIVE_DD                                  */
/*==============================================================*/
create table LAO_USER2C_ACTIVE_DD
(
   IN_DATE              numeric(8,0) comment '每日，格式为YYYYMMDD',
   CHANNEL_CUST_ID      numeric(16,0) comment '渠道客户ID',
   CHANNEL_CUST_NAME    varchar(100) comment '渠道客户名称',
   CUST_ID              numeric(16,0) comment '客户ID',
   CUST_NAME            varchar(100) comment '客户名称',
   STAFF_ID             varchar(50) comment '发展人员ID',
   STAFF_NAME           varchar(100) comment '发展人名称',
   REGION_ID            varchar(50) comment '区域',
   REGION_NAME          varchar(100) comment '区域名称',
   OPERATORS_ID         numeric(8,0) comment '运营商ID',
   OPERATORS_NAME       varchar(100) comment '运营商名称',
   VALUE1               varchar(50) comment '一级标签',
   VALUE1_NAME          varchar(100) comment '一级标签名称',
   VALUE2               varchar(50) comment '二级标签',
   VALUE2_NAME          varchar(100) comment '二级标签名称',
   GOODS_ID             numeric(16,0) comment '产品',
   GOODS_NAME           varchar(100) comment '产品名称',
   GOODS_CYCLE          numeric(6,0) comment '产品周期',
   OPEN_NUM             numeric(16,0) comment '激活数量',
   UPDATE_TIME          datetime comment '更新时间',
   REMARK               varchar(200) comment '备注'
);

alter table LAO_USER2C_ACTIVE_DD comment '每日开卡汇总';

/*==============================================================*/
/* Table: LAO_USER2C_ORDER_DD                                   */
/*==============================================================*/
create table LAO_USER2C_ORDER_DD
(
   IN_DATE              numeric(8,0) comment '日期,格式为YYYYMMDD',
   CHANNEL_CUST_ID      numeric(16,0) comment '渠道客户ID',
   CHANNEL_CUST_NAME    varchar(100) comment '渠道客户名称',
   CUST_ID              numeric(16,0) comment '客户ID',
   CUST_NAME            varchar(100) comment '客户名称',
   STAFF_ID             varchar(50) comment '发展人员ID',
   STAFF_NAME           varchar(100) comment '发展人名称',
   REGION_ID            varchar(50) comment '区域',
   REGION_NAME          varchar(100) comment '区域名称',
   OPERATORS_ID         numeric(8,0) comment '运营商ID',
   OPERATORS_NAME       varchar(100) comment '运营商名称',
   VALUE1               varchar(50) comment '一级标签',
   VALUE1_NAME          varchar(100) comment '一级标签名称',
   VALUE2               varchar(50) comment '二级标签',
   VALUE2_NAME          varchar(100) comment '二级标签名称',
   GOODS_ID             numeric(16,0) comment '产品',
   GOODS_NAME           varchar(100) comment '产品名称',
   GOODS_CYCLE          numeric(6,0) comment '周期',
   UNIT                 varchar(1) comment '周期单位',
   COST_PRICE           varchar(20) comment '成本价',
   GOODS_PRICE          varchar(20) comment '价格',
   ORDER_NUM            numeric(16,0) comment '订单数量',
   CARD_NUM             numeric(16,0) comment '订购卡数量',
   TOTAL_FLOW           varchar(50) comment '流量总量',
   UPDATE_TIME          datetime comment '更新时间',
   REMARK               varchar(200) comment '备注'
);

alter table LAO_USER2C_ORDER_DD comment 'toC用户每日订购汇总';

/*==============================================================*/
/* Table: LAO_USER2C_STATE_DD                                   */
/*==============================================================*/
create table LAO_USER2C_STATE_DD
(
   IN_DATE              numeric(8,0) comment '日期,格式为YYYYMMDD',
   CHANNEL_CUST_ID      numeric(16,0) comment '渠道客户ID',
   CHANNEL_CUST_NAME    varchar(100) comment '渠道客户名称',
   CUST_ID              numeric(16,0) comment '客户ID',
   CUST_NAME            varchar(100) comment '客户名称',
   STAFF_ID             varchar(50) comment '发展人员ID',
   STAFF_NAME           varchar(100) comment '发展人名称',
   REGION_ID            varchar(50) comment '区域',
   REGION_NAME          varchar(100) comment '区域名称',
   OPERATORS_ID         numeric(8,0) comment '运营商ID',
   OPERATORS_NAME       varchar(100) comment '运营商名称',
   VALUE1               varchar(50) comment '一级标签',
   VALUE1_NAME          varchar(100) comment '一级标签名称',
   VALUE2               varchar(50) comment '二级标签',
   VALUE2_NAME          varchar(100) comment '二级标签名称',
   STATE_CODE           varchar(10) comment '卡状态',
   STATE_NAME           varchar(100) comment '卡状态名称',
   STATE_ASNAME         varchar(100) comment '卡状态显示名称',
   CARD_NUM             numeric(16,0) comment '卡数量',
   UPDATE_TIME          datetime comment '更新时间',
   REMARK               varchar(200) comment '备注'
);

alter table LAO_USER2C_STATE_DD comment '每日用户状态汇总';

/*==============================================================*/
/* Table: LAO_USERBILLS_DETAIL                                  */
/*==============================================================*/
create table LAO_USERBILLS_DETAIL
(
   BILL_DETAIL_ID       numeric(16,0) not null,
   CHARGE_ID            numeric(16,0) not null comment '？？？？LAO_USERFEE_INFO.USERFEE_ID',
   CYCLE_ID             numeric(6,0),
   CHANNEL_CUST_ID      numeric(16,0),
   CUST_ID              numeric(16,0),
   USER_ID              numeric(16,0),
   TRADE_ID             numeric(16,0),
   GOODS_ID             numeric(16,0),
   GOODS_RELEASE_ID     numeric(16,0),
   OPERATOS_ID          numeric(8,0),
   PLAN_ID              numeric(8,0),
   ITEM_ID              numeric(6,0),
   FEE                  numeric(16,0),
   OWE_FEE              numeric(16,0),
   LEFT_BILL_FEE        numeric(16,0),
   RECV_TIME            datetime,
   UPDATE_TIME          datetime,
   OPER_ID              varchar(40),
   CANCEL_TAG           char(1) comment '0-？？？？？？1-？？？？？？',
   CANCEL_OPER_ID       varchar(40),
   CANCEL_TIME          datetime,
   RSRV_INFO1           varchar(100),
   RSRV_INFO2           varchar(100),
   primary key (BILL_DETAIL_ID),
   check ("BILL_DETAIL_ID" IS NOT NULL)
);

alter table LAO_USERBILLS_DETAIL comment '？？？？？？？？？？？？？？？？？？？？？？？？dba？？？？？？？？？？？？？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_USERFEE_INFO                                      */
/*==============================================================*/
create table LAO_USERFEE_INFO
(
   CHARGE_ID            numeric(16,0) not null,
   ACCEPT_MONTH         numeric(6,0),
   CHANNEL_CUST_ID      numeric(16,0),
   CUST_ID              numeric(16,0),
   USER_ID              numeric(16,0),
   MSISDN               varchar(40),
   ICCID                varchar(40),
   TRADE_ID             numeric(16,0),
   GOODS_ID             numeric(16,0),
   GOODS_RELEASE_ID     numeric(16,0),
   OPERATORS_ID         numeric(8,0),
   PAY_TAG              char(1) comment '0-？？？？？？？？1-？？？？？？？？？？？？2-？？？？？？？？？？？？',
   RECV_FEE             numeric(16,0),
   FEE                  numeric(16,0) not null,
   BILL_FEE             numeric(16,0),
   START_BILL_CYC       numeric(6,0),
   END_BILL_CYC         numeric(6,0),
   LASTEST_BILL_CYC     numeric(6,0),
   RECV_TIME            datetime,
   UPDATE_TIME          datetime,
   OPER_ID              varchar(40),
   CANCEL_TAG           char(1) comment '0-？？？？？？1-？？？？？？',
   CANCEL_OPER_ID       varchar(40),
   CANCEL_TIME          datetime,
   RSRV_INFO1           varchar(100),
   RSRV_INFO2           varchar(100),
   primary key (CHARGE_ID),
   check ("CHARGE_ID" IS NOT NULL)
);

alter table LAO_USERFEE_INFO comment '？？？？？？？？？？';

/*==============================================================*/
/* Index: INDEX_LAO_USERFEE_INFO_TRADEID                        */
/*==============================================================*/
create index INDEX_LAO_USERFEE_INFO_TRADEID on LAO_USERFEE_INFO
(
   TRADE_ID
);

/*==============================================================*/
/* Table: LAO_USER_ACTIVE_DD                                    */
/*==============================================================*/
create table LAO_USER_ACTIVE_DD
(
   IN_DATE              numeric(8,0) comment '每日，格式为YYYYMMDD',
   CHANNEL_CUST_ID      numeric(16,0) comment '渠道客户ID',
   CHANNEL_CUST_NAME    varchar(100) comment '渠道客户名称',
   CUST_ID              numeric(16,0) comment '客户ID',
   CUST_NAME            varchar(100) comment '客户名称',
   STAFF_ID             varchar(50) comment '发展人员ID',
   STAFF_NAME           varchar(100) comment '发展人名称',
   REGION_ID            varchar(50) comment '区域',
   REGION_NAME          varchar(100) comment '区域名称',
   OPERATORS_ID         numeric(8,0) comment '运营商ID',
   OPERATORS_NAME       varchar(100) comment '运营商名称',
   VALUE1               varchar(50) comment '一级标签',
   VALUE1_NAME          varchar(100) comment '一级标签名称',
   VALUE2               varchar(50) comment '二级标签',
   VALUE2_NAME          varchar(100) comment '二级标签名称',
   GOODS_ID             numeric(16,0) comment '产品',
   GOODS_NAME           varchar(100) comment '产品名称',
   GOODS_CYCLE          numeric(6,0) comment '产品周期',
   OPEN_NUM             numeric(16,0) comment '激活数量',
   UPDATE_TIME          datetime comment '更新时间',
   REMARK               varchar(200) comment '备注'
);

alter table LAO_USER_ACTIVE_DD comment '每日开卡汇总';

/*==============================================================*/
/* Table: LAO_USER_ACTIVE_MM                                    */
/*==============================================================*/
create table LAO_USER_ACTIVE_MM
(
   IN_DATE              numeric(8,0) comment '每日，格式为YYYYMM',
   CHANNEL_CUST_ID      numeric(16,0) comment '渠道客户ID',
   CHANNEL_CUST_NAME    varchar(100) comment '渠道客户名称',
   CUST_ID              numeric(16,0) comment '客户ID',
   CUST_NAME            varchar(100) comment '客户名称',
   STAFF_ID             varchar(50) comment '发展人员ID',
   STAFF_NAME           varchar(100) comment '发展人名称',
   REGION_ID            varchar(50) comment '区域',
   REGION_NAME          varchar(100) comment '区域名称',
   OPERATORS_ID         numeric(8,0) comment '运营商ID',
   OPERATORS_NAME       varchar(100) comment '运营商名称	',
   VALUE1               varchar(50) comment '一级标签',
   VALUE1_NAME          varchar(100) comment '一级标签名称',
   VALUE2               varchar(50) comment '二级标签	',
   VALUE2_NAME          varchar(100) comment '二级标签名称',
   GOODS_ID             numeric(16,0) comment '产品	',
   GOODS_NAME           varchar(100) comment '产品名称	',
   GOODS_CYCLE          numeric(6,0),
   OPEN_NUM             numeric(16,0) comment '激活数量',
   UPDATE_TIME          datetime comment '更新时间	',
   REMARK               varchar(200)
);

/*==============================================================*/
/* Table: LAO_USER_BAK1                                         */
/*==============================================================*/
create table LAO_USER_BAK1
(
   USER_ID              numeric(16,0) not null,
   CHANNEL_CUST_ID      numeric(16,0) not null,
   CUST_ID              numeric(16,0),
   MSISDN               varchar(20),
   OPERATORS_ID         numeric(8,0),
   SCORE_VALUE          numeric(11,0),
   CREDIT_CLASS         numeric(8,0),
   BASIC_CREDIT_VALUE   numeric(11,0),
   CREDIT_VALUE         numeric(11,0),
   ACCT_TAG             char(1) not null,
   PREPAY_TAG           char(1) not null,
   IN_DATE              datetime not null,
   OPEN_DATE            datetime not null,
   REMOVE_TAG           char(1) not null,
   DESTROY_TIME         datetime,
   PRE_DESTROY_TIME     datetime,
   FIRST_CALL_TIME      datetime,
   LAST_STOP_TIME       datetime,
   ICCID                varchar(100),
   DEVICE_ID            varchar(50),
   STATUS_CODE          varchar(1),
   ALIVE_CHECK_TIME     numeric(8,0),
   IMEI                 varchar(100),
   STATE_CODE           varchar(10) not null,
   ATTRIBUTE1           varchar(50),
   VALUE1               varchar(50),
   ATTRIBUTE2           varchar(50),
   VALUE2               varchar(50),
   check ("USER_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_USER_CHG                                          */
/*==============================================================*/
create table LAO_USER_CHG
(
   SEQ_ID               numeric(16,0) not null,
   USER_ID              numeric(16,0) not null,
   CHANNEL_CUST_ID      numeric(16,0) not null,
   CUST_ID              numeric(16,0),
   MSISDN               varchar(50),
   OPERATORS_ID         numeric(8,0),
   SCORE_VALUE          numeric(11,0) default 0,
   CREDIT_CLASS         numeric(8,0) default 0,
   BASIC_CREDIT_VALUE   numeric(11,0) default 0,
   CREDIT_VALUE         numeric(11,0) default 0,
   ACCT_TAG             char(1) not null,
   PREPAY_TAG           char(1) not null default '0',
   IN_DATE              datetime not null,
   OPEN_DATE            datetime not null,
   REMOVE_TAG           char(1) not null,
   DESTROY_TIME         datetime,
   PRE_DESTROY_TIME     datetime,
   FIRST_CALL_TIME      datetime,
   LAST_STOP_TIME       datetime,
   ICCID                varchar(100),
   DEVICE_ID            varchar(50),
   STATUS_CODE          varchar(1),
   ALIVE_CHECK_TIME     numeric(8,0),
   IMEI                 varchar(100),
   STATE_CODE           varchar(10) not null,
   ATTRIBUTE1           varchar(50),
   VALUE1               varchar(50),
   ATTRIBUTE2           varchar(50),
   VALUE2               varchar(50),
   OPER_TYPE            char(1) not null,
   MODIFY_TIME          datetime,
   ACCOUNT_ID           numeric(16,0),
   primary key (SEQ_ID),
   check ("SEQ_ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: IDX_LAO_USER_CHG_ATRIBUTE1                            */
/*==============================================================*/
create index IDX_LAO_USER_CHG_ATRIBUTE1 on LAO_USER_CHG
(
   ATTRIBUTE1
);

/*==============================================================*/
/* Index: IDX_LAO_USER_CHG_ATRIBUTE2                            */
/*==============================================================*/
create index IDX_LAO_USER_CHG_ATRIBUTE2 on LAO_USER_CHG
(
   ATTRIBUTE2
);

/*==============================================================*/
/* Index: IDX_LAO_USER_CHG_CHANNEL_CUST                         */
/*==============================================================*/
create index IDX_LAO_USER_CHG_CHANNEL_CUST on LAO_USER_CHG
(
   CHANNEL_CUST_ID
);

/*==============================================================*/
/* Index: IDX_LAO_USER_CHG_CUST_ID                              */
/*==============================================================*/
create index IDX_LAO_USER_CHG_CUST_ID on LAO_USER_CHG
(
   CUST_ID
);

/*==============================================================*/
/* Index: IDX_LAO_USER_CHG_ICCID                                */
/*==============================================================*/
create index IDX_LAO_USER_CHG_ICCID on LAO_USER_CHG
(
   ICCID
);

/*==============================================================*/
/* Index: IDX_LAO_USER_CHG_MSISDN                               */
/*==============================================================*/
create index IDX_LAO_USER_CHG_MSISDN on LAO_USER_CHG
(
   MSISDN
);

/*==============================================================*/
/* Index: IDX_LAO_USER_CHG_OPERATOR_ID                          */
/*==============================================================*/
create index IDX_LAO_USER_CHG_OPERATOR_ID on LAO_USER_CHG
(
   OPERATORS_ID
);

/*==============================================================*/
/* Table: LAO_USER_CMP                                          */
/*==============================================================*/
create table LAO_USER_CMP
(
   USER_ID              numeric(16,0) not null,
   CHANNEL_CUST_ID      numeric(16,0) not null,
   CUST_ID              numeric(16,0),
   MSISDN               varchar(50),
   OPERATORS_ID         numeric(8,0),
   SCORE_VALUE          numeric(11,0),
   CREDIT_CLASS         numeric(8,0),
   BASIC_CREDIT_VALUE   numeric(11,0),
   CREDIT_VALUE         numeric(11,0),
   ACCT_TAG             char(1) not null,
   PREPAY_TAG           char(1) not null,
   IN_DATE              datetime not null,
   OPEN_DATE            datetime not null,
   REMOVE_TAG           char(1) not null,
   DESTROY_TIME         datetime,
   PRE_DESTROY_TIME     datetime,
   FIRST_CALL_TIME      datetime,
   LAST_STOP_TIME       datetime,
   ICCID                varchar(100),
   DEVICE_ID            varchar(50),
   STATUS_CODE          varchar(1),
   ALIVE_CHECK_TIME     numeric(8,0),
   IMEI                 varchar(100),
   STATE_CODE           varchar(10) not null,
   ATTRIBUTE1           varchar(50),
   VALUE1               varchar(50),
   ATTRIBUTE2           varchar(50),
   VALUE2               varchar(50),
   primary key (USER_ID),
   check ("USER_ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: IDX_LAO_USER_CMP_CHANNEL_CUST                         */
/*==============================================================*/
create index IDX_LAO_USER_CMP_CHANNEL_CUST on LAO_USER_CMP
(
   CHANNEL_CUST_ID
);

/*==============================================================*/
/* Index: IDX_LAO_USER_CMP_ICCID                                */
/*==============================================================*/
create index IDX_LAO_USER_CMP_ICCID on LAO_USER_CMP
(
   ICCID
);

/*==============================================================*/
/* Index: IDX_LAO_USER_CMP_MSISDN                               */
/*==============================================================*/
create index IDX_LAO_USER_CMP_MSISDN on LAO_USER_CMP
(
   MSISDN
);

/*==============================================================*/
/* Table: LAO_USER_EXPIRE_DD                                    */
/*==============================================================*/
create table LAO_USER_EXPIRE_DD
(
   IN_DATE              numeric(8,0) comment '日期 number yyyymmdd',
   CHANNEL_CUST_ID      numeric(16,0) comment '渠道客户ID',
   CHANNEL_CUST_NAME    varchar(100) comment '渠道客户名称',
   CUST_ID              numeric(16,0) comment '客户ID',
   CUST_NAME            varchar(100) comment '客户名称',
   STAFF_ID             varchar(50) comment '发展人员ID',
   STAFF_NAME           varchar(100) comment '发展人名称',
   REGION_ID            varchar(50) comment '区域',
   REGION_NAME          varchar(100) comment '区域名称',
   OPERATORS_ID         numeric(8,0) comment '运营商ID',
   OPERATORS_NAME       varchar(100) comment '运营商名称',
   VALUE1               varchar(50) comment '一级标签',
   VALUE1_NAME          varchar(100) comment '一级标签名称',
   VALUE2               varchar(50) comment '二级标签',
   VALUE2_NAME          varchar(100) comment '二级标签名称',
   GOODS_ID             numeric(16,0) comment '产品',
   GOODS_NAME           varchar(100) comment '产品名称',
   GOODS_CYCLE          numeric(16,0) comment '产品周期',
   GOODS_TYPE           varchar(2) comment '产品类型',
   EXPIRE_NUM           numeric(16,0) comment '到期用户数',
   END_DATE             varchar(8) comment '到期时间',
   UPDATE_TIME          datetime comment '更新时间',
   REMARK               varchar(200) comment '备注'
);

alter table LAO_USER_EXPIRE_DD comment '每日到期用户汇总';

/*==============================================================*/
/* Table: LAO_USER_EXPIRE_MM                                    */
/*==============================================================*/
create table LAO_USER_EXPIRE_MM
(
   IN_DATE              numeric(8,0) comment '日期 number yyyymmdd',
   CHANNEL_CUST_ID      numeric(16,0) comment '渠道客户ID',
   CHANNEL_CUST_NAME    varchar(100) comment '渠道客户名称',
   CUST_ID              numeric(16,0) comment '客户ID',
   CUST_NAME            varchar(100) comment '客户名称',
   STAFF_ID             varchar(50) comment '发展人员ID',
   STAFF_NAME           varchar(100) comment '发展人名称',
   REGION_ID            varchar(50) comment '区域',
   REGION_NAME          varchar(100) comment '区域名称',
   OPERATORS_ID         numeric(8,0) comment '运营商ID',
   OPERATORS_NAME       varchar(100) comment '运营商名称',
   VALUE1               varchar(50) comment '一级标签',
   VALUE1_NAME          varchar(100) comment '一级标签名称',
   VALUE2               varchar(50) comment '二级标签',
   VALUE2_NAME          varchar(100) comment '二级标签名称',
   GOODS_ID             numeric(16,0) comment '产品',
   GOODS_NAME           varchar(100) comment '产品名称',
   GOODS_CYCLE          numeric(16,0) comment '产品周期',
   GOODS_TYPE           varchar(2) comment '产品类型',
   EXPIRE_NUM           numeric(16,0) comment '到期用户数',
   END_DATE             varchar(8) comment '到期时间',
   UPDATE_TIME          datetime comment '更新时间',
   REMARK               varchar(200) comment '备注'
);

alter table LAO_USER_EXPIRE_MM comment '每月到期用户汇总';

/*==============================================================*/
/* Table: LAO_USER_FLOW_DD                                      */
/*==============================================================*/
create table LAO_USER_FLOW_DD
(
   IN_DATE              numeric(8,0) comment '每日，格式为YYYYMMDD',
   CHANNEL_CUST_ID      numeric(16,0) comment '渠道客户ID',
   CHANNEL_CUST_NAME    varchar(100) comment '渠道客户名称',
   CUST_ID              numeric(16,0) comment '客户ID',
   CUST_NAME            varchar(100) comment '客户名称',
   STAFF_ID             varchar(50) comment '发展人员ID',
   STAFF_NAME           varchar(100) comment '发展人名称',
   REGION_ID            varchar(50) comment '区域',
   REGION_NAME          varchar(100) comment '区域名称',
   OPERATORS_ID         numeric(8,0) comment '运营商ID',
   OPERATORS_NAME       varchar(100) comment '运营商名称',
   VALUE1               varchar(50) comment '一级标签',
   VALUE1_NAME          varchar(100) comment '一级标签名称',
   VALUE2               varchar(50) comment '二级标签',
   VALUE2_NAME          varchar(100) comment '二级标签名称',
   TOTAL_CNT            varchar(50) comment '流量总量',
   USE_CNT              varchar(50) comment '使用量',
   LEFT_CNT             varchar(50) comment '剩余量',
   UPDATE_TIME          datetime comment '更新时间',
   REMARK               varchar(200) comment '备注'
);

alter table LAO_USER_FLOW_DD comment '每日流量汇总';

/*==============================================================*/
/* Table: LAO_USER_FLOW_MM                                      */
/*==============================================================*/
create table LAO_USER_FLOW_MM
(
   IN_DATE              numeric(8,0) comment '每日，格式为YYYYMMDD',
   CHANNEL_CUST_ID      numeric(16,0) comment '渠道客户ID',
   CHANNEL_CUST_NAME    varchar(100) comment '渠道客户名称',
   CUST_ID              numeric(16,0) comment '客户ID',
   CUST_NAME            varchar(100) comment '客户名称',
   STAFF_ID             varchar(50) comment '发展人员ID',
   STAFF_NAME           varchar(100) comment '发展人名称',
   REGION_ID            varchar(50) comment '区域',
   REGION_NAME          varchar(100) comment '区域名称',
   OPERATORS_ID         numeric(8,0) comment '运营商ID',
   OPERATORS_NAME       varchar(100) comment '运营商名称',
   VALUE1               varchar(50) comment '一级标签',
   VALUE1_NAME          varchar(100) comment '一级标签名称',
   VALUE2               varchar(50) comment '二级标签',
   VALUE2_NAME          varchar(100) comment '二级标签名称',
   TOTAL_CNT            varchar(50) comment '流量总量',
   USE_CNT              varchar(50) comment '使用量',
   LEFT_CNT             varchar(50) comment '剩余量',
   UPDATE_TIME          datetime comment '更新时间',
   REMARK               varchar(200) comment '备注'
);

alter table LAO_USER_FLOW_MM comment '每月流量汇总';

/*==============================================================*/
/* Table: LAO_USER_GOODS                                        */
/*==============================================================*/
create table LAO_USER_GOODS
(
   USER_ID              numeric(16,0) not null,
   GOODS_ID             numeric(16,0) not null,
   START_DATE           datetime not null,
   END_DATE             datetime,
   UPDATE_DATE          datetime,
   BI_RULES_ID          numeric(16,0),
   START_USE_DATE       datetime,
   RELEASE_CYCLE        varchar(6),
   GOODS_RELEASE_ID     numeric(8,0),
   primary key (USER_ID, GOODS_ID, START_DATE),
   check ("USER_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_USER_GOODS_BAK_20170628                           */
/*==============================================================*/
create table LAO_USER_GOODS_BAK_20170628
(
   USER_ID              numeric(16,0) not null,
   GOODS_ID             numeric(16,0) not null,
   START_DATE           datetime not null,
   END_DATE             datetime,
   UPDATE_DATE          datetime,
   BI_RULES_ID          numeric(16,0),
   START_USE_DATE       datetime,
   RELEASE_CYCLE        varchar(6),
   GOODS_RELEASE_ID     numeric(8,0),
   primary key (USER_ID, GOODS_ID, START_DATE),
   check ("USER_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_USER_GOODS_CMP                                    */
/*==============================================================*/
create table LAO_USER_GOODS_CMP
(
   USER_ID              numeric(16,0) not null,
   GOODS_ID             numeric(16,0) not null,
   START_DATE           datetime not null,
   END_DATE             datetime,
   UPDATE_DATE          datetime,
   BI_RULES_ID          numeric(16,0),
   START_USE_DATE       datetime,
   RELEASE_CYCLE        varchar(6),
   GOODS_RELEASE_ID     numeric(8,0),
   primary key (USER_ID, GOODS_ID, START_DATE),
   check ("USER_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_USER_HIS                                          */
/*==============================================================*/
create table LAO_USER_HIS
(
   SEQ_ID               numeric(16,0) not null,
   USER_ID              numeric(16,0) not null,
   CHANNEL_CUST_ID      numeric(16,0) not null,
   CUST_ID              numeric(16,0),
   MSISDN               varchar(50),
   OPERATORS_ID         numeric(8,0),
   SCORE_VALUE          numeric(11,0) default 0,
   CREDIT_CLASS         numeric(8,0) default 0,
   BASIC_CREDIT_VALUE   numeric(11,0) default 0,
   CREDIT_VALUE         numeric(11,0) default 0,
   ACCT_TAG             char(1) not null,
   PREPAY_TAG           char(1) not null default '0',
   IN_DATE              datetime not null,
   OPEN_DATE            datetime not null,
   REMOVE_TAG           char(1) not null,
   DESTROY_TIME         datetime,
   PRE_DESTROY_TIME     datetime,
   FIRST_CALL_TIME      datetime,
   LAST_STOP_TIME       datetime,
   ICCID                varchar(100),
   DEVICE_ID            varchar(50),
   STATUS_CODE          varchar(1),
   ALIVE_CHECK_TIME     numeric(8,0),
   IMEI                 varchar(100),
   STATE_CODE           varchar(10) not null,
   ATTRIBUTE1           varchar(50),
   VALUE1               varchar(50),
   ATTRIBUTE2           varchar(50),
   VALUE2               varchar(50),
   OPER_TYPE            varchar(50) not null,
   TRADE_TYPE_CODE      varchar(50),
   MODIFY_TIME          datetime,
   ACCOUNT_ID           numeric(16,0),
   primary key (SEQ_ID),
   check ("SEQ_ID" IS NOT NULL)
);

/*==============================================================*/
/* Index: IDX_LAO_USER_HIS_CHANNEL_CUST                         */
/*==============================================================*/
create index IDX_LAO_USER_HIS_CHANNEL_CUST on LAO_USER_HIS
(
   CHANNEL_CUST_ID
);

/*==============================================================*/
/* Index: IDX_LAO_USER_HIS_CUST_ID                              */
/*==============================================================*/
create index IDX_LAO_USER_HIS_CUST_ID on LAO_USER_HIS
(
   CUST_ID
);

/*==============================================================*/
/* Index: IDX_LAO_USER_HIS_ICCID                                */
/*==============================================================*/
create index IDX_LAO_USER_HIS_ICCID on LAO_USER_HIS
(
   ICCID
);

/*==============================================================*/
/* Index: IDX_LAO_USER_HIS_MSISDN                               */
/*==============================================================*/
create index IDX_LAO_USER_HIS_MSISDN on LAO_USER_HIS
(
   MSISDN
);

/*==============================================================*/
/* Table: LAO_USER_INFO                                         */
/*==============================================================*/
create table LAO_USER_INFO
(
   ID                   numeric(20,0) not null comment '？？？？ID',
   ICCID                varchar(100) comment 'iccid？？',
   MSISDN               varchar(50),
   IMSI                 varchar(100),
   DEVICEID             varchar(100) comment '？？？？？？',
   USERID               varchar(100) comment 'LenovoId',
   USERNAME             varchar(100) comment '？？？？？？',
   USERSTATUS           numeric(1,0) comment '？？？？？？？？ 0 ？？？？？？ 1 ？？？？？？ 2 ？？？？ 3 ？？？？？？？？',
   APNTYPE              varchar(100) comment 'apn1 apn2 ',
   CREATEDATE           datetime comment '？？？？？？？？',
   MODIFYDATE           datetime comment '？？？？？？？？',
   ALIVECHECKTIME       numeric(8,0) default 0 comment 'last heartbeat time ？？？？？？？？？？？？？？',
   IMEI                 varchar(100) comment 'imei？？',
   REALNAME             varchar(30) comment '？？？？？？？？',
   IDNUM                varchar(50) comment '？？？？？？？？',
   FIRSTCHARGE          numeric(1,0) comment '？？？？？？？？？？ 1？？ -1 ？？',
   DONATEFLAG           numeric(1,0) default 2 comment '1 ？？？？？？？？ -1 ？？？？？？？？ 2？？？？？？',
   primary key (ID),
   key AK_Key_1 (ICCID, USERID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_USER_IN_DD                                        */
/*==============================================================*/
create table LAO_USER_IN_DD
(
   IN_DATE              numeric(8,0) comment '日期,格式为YYYYMMDD',
   CHANNEL_CUST_ID      numeric(16,0) comment '渠道客户ID',
   CHANNEL_CUST_NAME    varchar(100) comment '渠道客户名称',
   CUST_ID              numeric(16,0) comment '客户ID',
   CUST_NAME            varchar(100) comment '客户名称',
   STAFF_ID             varchar(50) comment '发展人员ID',
   STAFF_NAME           varchar(100) comment '发展人名称',
   REGION_ID            varchar(50) comment '区域',
   REGION_NAME          varchar(100) comment '区域名称',
   OPERATORS_ID         numeric(8,0) comment '运营商ID',
   OPERATORS_NAME       varchar(100) comment '运营商名称',
   VALUE1               varchar(50) comment '一级标签',
   VALUE1_NAME          varchar(100) comment '一级标签名称',
   VALUE2               varchar(50) comment '二级标签',
   VALUE2_NAME          varchar(100) comment '二级标签名称',
   GOODS_ID             numeric(16,0) comment '产品',
   GOODS_NAME           varchar(100) comment '产品名称',
   CARD_NUM             numeric(10,0) comment '入库卡数量',
   UPDATE_TIME          datetime comment '更新时间',
   REMARK               varchar(200) comment '备注'
);

alter table LAO_USER_IN_DD comment '每日入库卡数量汇总';

/*==============================================================*/
/* Table: LAO_USER_IN_MM                                        */
/*==============================================================*/
create table LAO_USER_IN_MM
(
   IN_DATE              numeric(8,0) comment '日期,格式为YYYYMMDD',
   CHANNEL_CUST_ID      numeric(16,0) comment '渠道客户ID',
   CHANNEL_CUST_NAME    varchar(100) comment '渠道客户名称',
   CUST_ID              numeric(16,0) comment '客户ID',
   CUST_NAME            varchar(100) comment '客户名称',
   STAFF_ID             varchar(50) comment '发展人员ID',
   STAFF_NAME           varchar(100) comment '发展人名称',
   REGION_ID            varchar(50) comment '区域',
   REGION_NAME          varchar(100) comment '区域名称',
   OPERATORS_ID         numeric(8,0) comment '运营商ID',
   OPERATORS_NAME       varchar(100) comment '运营商名称',
   VALUE1               varchar(50) comment '一级标签',
   VALUE1_NAME          varchar(100) comment '一级标签名称',
   VALUE2               varchar(50) comment '二级标签',
   VALUE2_NAME          varchar(100) comment '二级标签名称',
   GOODS_ID             numeric(16,0) comment '产品',
   GOODS_NAME           varchar(100) comment '产品名称',
   CARD_NUM             numeric(10,0) comment '入库卡数量',
   UPDATE_TIME          datetime comment '更新时间',
   REMARK               varchar(200) comment '备注'
);

alter table LAO_USER_IN_MM comment '每yue入库卡数量汇总';

/*==============================================================*/
/* Table: LAO_USER_IP_MANAGER                                   */
/*==============================================================*/
create table LAO_USER_IP_MANAGER
(
   ID                   numeric(16,0) not null,
   CUST_ID              numeric(16,0) not null,
   IP_ADDRESS           varchar(40) not null,
   UPDAT_DATE           datetime,
   CREAT_DATE           datetime,
   PARA_NAME1           varchar(100),
   PARA_NAME2           varchar(100),
   ISDISABLED           numeric(1,0) not null default 0,
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_USER_IP_MANAGER01                                 */
/*==============================================================*/
create table LAO_USER_IP_MANAGER01
(
   ID                   numeric(16,0) not null,
   CUST_ID              numeric(16,0) not null,
   IP_ADDRESS           varchar(40) not null,
   UPDAT_DATE           datetime,
   CREAT_DATE           datetime,
   PARA_NAME1           varchar(100),
   PARA_NAME2           varchar(100),
   ISDISABLED           numeric(1,0) not null default 0,
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_USER_LINKED_TARIFFPLAN                            */
/*==============================================================*/
create table LAO_USER_LINKED_TARIFFPLAN
(
   ID                   varchar(20) not null,
   USERID               varchar(150),
   USERNAME             varchar(150),
   LINKTARIFFPLANID     numeric(19,0),
   ORDERSTATUS          numeric(1,0),
   USEDSTATUS           numeric(1,0),
   ICCID                varchar(100),
   TARIFFPLANNAME       varchar(255),
   TARIFFPLANEXPDATE    timestamp,
   TARIFFPLANLENGTH     numeric(19,0),
   TARIFFPLANSTAUTS     numeric(1,0),
   CREATEDATE           datetime,
   MODIFYDATE           datetime,
   TARIFFPLANCAPACITY   numeric(20,3) default 0.000,
   primary key (ID),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_USER_OPERATOR_INFO                                */
/*==============================================================*/
create table LAO_USER_OPERATOR_INFO
(
   ID                   numeric(16,0) not null,
   ICCID                varchar(20) not null,
   START_TIME           varchar(20),
   END_TIME             varchar(20),
   OPERATOR             varchar(10),
   PALN_CODE            varchar(50),
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_USER_OPERATOR_PLAN                                */
/*==============================================================*/
create table LAO_USER_OPERATOR_PLAN
(
   USER_ID              numeric(16,0) not null,
   GOODS_ID             numeric(16,0) not null,
   PLAN_ID              numeric(8,0) not null,
   OPERATORS_ID         numeric(8,0),
   OPERATORS_PID        varchar(50),
   START_DATE           datetime not null,
   END_DATE             datetime,
   GOODS_INDEX          numeric(2,0) not null,
   PLAN_TYPE            varchar(1),
   OPEARTORS_DEAL_RST   varchar(1) comment '？？？？？？？？？？0-？？？？？？1-？？？？',
   OPEARTORS_DEAL_CODE  varchar(50),
   OPEARTORS_DEAL_NUM   numeric(2,0),
   START_USE_DATE       datetime,
   GOODS_RELEASE_ID     numeric(8,0),
   BILL_TAG             char(1),
   TRADE_ID             numeric(16,0),
   COST_PRICE           varchar(20),
   PLAN_CLASSIFY        varchar(10) comment '1流量 2语音 3 短信',
   primary key (USER_ID, PLAN_ID, START_DATE, GOODS_INDEX),
   check (PLAN_TYPE is null or (PLAN_TYPE in ('0','1','2')))
);

/*==============================================================*/
/* Table: LAO_USER_OPERATOR_PLAN_CMP                            */
/*==============================================================*/
create table LAO_USER_OPERATOR_PLAN_CMP
(
   USER_ID              numeric(16,0) not null,
   GOODS_ID             numeric(16,0) not null,
   PLAN_ID              numeric(8,0) not null,
   OPERATORS_ID         numeric(8,0),
   OPERATORS_PID        varchar(50),
   START_DATE           datetime not null,
   END_DATE             datetime,
   GOODS_INDEX          numeric(2,0) not null,
   PLAN_TYPE            varchar(1),
   OPEARTORS_DEAL_RST   varchar(1),
   OPEARTORS_DEAL_CODE  varchar(50),
   OPEARTORS_DEAL_NUM   numeric(2,0),
   START_USE_DATE       datetime,
   GOODS_RELEASE_ID     numeric(8,0),
   BILL_TAG             char(1),
   TRADE_ID             numeric(16,0),
   COST_PRICE           varchar(20),
   primary key (USER_ID, PLAN_ID, START_DATE, GOODS_INDEX),
   check ("USER_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_USER_ORDER_DD                                     */
/*==============================================================*/
create table LAO_USER_ORDER_DD
(
   IN_DATE              numeric(8,0) comment '日期,格式为YYYYMMDD',
   CHANNEL_CUST_ID      numeric(16,0) comment '渠道客户ID',
   CHANNEL_CUST_NAME    varchar(100) comment '渠道客户名称',
   CUST_ID              numeric(16,0) comment '客户ID',
   CUST_NAME            varchar(100) comment '客户名称',
   STAFF_ID             varchar(50) comment '发展人员ID',
   STAFF_NAME           varchar(100) comment '发展人名称',
   REGION_ID            varchar(50) comment '区域',
   REGION_NAME          varchar(100) comment '区域名称',
   OPERATORS_ID         numeric(8,0) comment '运营商ID',
   OPERATORS_NAME       varchar(100) comment '运营商名称',
   VALUE1               varchar(50) comment '一级标签',
   VALUE1_NAME          varchar(100) comment '一级标签名称',
   VALUE2               varchar(50) comment '二级标签',
   VALUE2_NAME          varchar(100) comment '二级标签名称',
   GOODS_ID             numeric(16,0) comment '产品',
   GOODS_NAME           varchar(100) comment '产品名称',
   GOODS_CYCLE          numeric(6,0) comment '周期',
   UNIT                 varchar(1) comment '周期单位',
   COST_PRICE           varchar(20) comment '成本价',
   GOODS_PRICE          varchar(20) comment '价格',
   ORDER_NUM            numeric(16,0) comment '订单数量',
   CARD_NUM             numeric(16,0) comment '订购卡数量',
   TOTAL_FLOW           varchar(50) comment '流量总量',
   UPDATE_TIME          datetime comment '更新时间',
   REMARK               varchar(200) comment '备注'
);

alter table LAO_USER_ORDER_DD comment '每日订购汇总';

/*==============================================================*/
/* Table: LAO_USER_ORDER_MM                                     */
/*==============================================================*/
create table LAO_USER_ORDER_MM
(
   IN_DATE              numeric(8,0) comment '日期,格式为YYYYMMDD',
   CHANNEL_CUST_ID      numeric(16,0) comment '渠道客户ID',
   CHANNEL_CUST_NAME    varchar(100) comment '渠道客户名称',
   CUST_ID              numeric(16,0) comment '客户ID',
   CUST_NAME            varchar(100) comment '客户名称',
   STAFF_ID             varchar(50) comment '发展人员ID',
   STAFF_NAME           varchar(100) comment '发展人名称',
   REGION_ID            varchar(50) comment '区域',
   REGION_NAME          varchar(100) comment '区域名称',
   OPERATORS_ID         numeric(8,0) comment '运营商ID',
   OPERATORS_NAME       varchar(100) comment '运营商名称',
   VALUE1               varchar(50) comment '一级标签',
   VALUE1_NAME          varchar(100) comment '一级标签名称',
   VALUE2               varchar(50) comment '二级标签',
   VALUE2_NAME          varchar(100) comment '二级标签名称',
   GOODS_ID             numeric(16,0) comment '产品',
   GOODS_NAME           varchar(100) comment '产品名称',
   GOODS_CYCLE          numeric(6,0) comment '周期',
   UNIT                 varchar(1) comment '周期单位',
   COST_PRICE           varchar(20) comment '成本价',
   GOODS_PRICE          varchar(20) comment '价格',
   ORDER_NUM            numeric(16,0) comment '订购数量',
   CARD_NUM             numeric(16,0),
   TOTAL_FLOW           varchar(50) comment '流量总量',
   UPDATE_TIME          datetime comment '更新时间',
   REMARK               varchar(200) comment '备注'
);

alter table LAO_USER_ORDER_MM comment '每月订购汇总';

/*==============================================================*/
/* Table: LAO_USER_RECHARGE_DD                                  */
/*==============================================================*/
create table LAO_USER_RECHARGE_DD
(
   IN_DATE              numeric(8,0) comment '每日，格式为YYYYMMDD',
   CHANNEL_CUST_ID      numeric(16,0) comment '渠道客户ID',
   CHANNEL_CUST_NAME    varchar(100) comment '渠道客户名称',
   CUST_ID              numeric(16,0) comment '客户ID',
   CUST_NAME            varchar(100) comment '客户名称',
   STAFF_ID             varchar(50) comment '发展人员ID',
   STAFF_NAME           varchar(100) comment '发展人名称',
   REGION_ID            varchar(50) comment '区域',
   REGION_NAME          varchar(100) comment '区域名称',
   OPERATORS_ID         numeric(8,0) comment '运营商ID',
   OPERATORS_NAME       varchar(100) comment '运营商名称',
   VALUE1               varchar(50) comment '一级标签',
   VALUE1_NAME          varchar(100) comment '一级标签名称',
   VALUE2               varchar(50) comment '二级标签',
   VALUE2_NAME          varchar(100) comment '二级标签名称',
   GOODS_ID             numeric(16,0) comment '产品',
   GOODS_NAME           varchar(100) comment '产品名称',
   GOODS_FLOW           numeric(16,2) comment '充值总流量）',
   PAY_TYPE             numeric(2,0) comment '充值类型0后台，>0前台',
   UNIT                 char(2) comment '流量单位',
   SUMFEE               numeric(16,2) comment '充值总金额',
   UPDATE_TIME          datetime comment '更新时间',
   REMARK               varchar(200) comment '备注'
);

alter table LAO_USER_RECHARGE_DD comment '后台客户充值流量和金额每日汇总';

/*==============================================================*/
/* Table: LAO_USER_RECHARGE_MM                                  */
/*==============================================================*/
create table LAO_USER_RECHARGE_MM
(
   IN_DATE              numeric(8,0) comment '每日，格式为YYYYMMDD',
   CHANNEL_CUST_ID      numeric(16,0) comment '渠道客户ID',
   CHANNEL_CUST_NAME    varchar(100) comment '渠道客户名称',
   CUST_ID              numeric(16,0) comment '客户ID',
   CUST_NAME            varchar(100) comment '客户名称',
   STAFF_ID             varchar(50) comment '发展人员ID',
   STAFF_NAME           varchar(100) comment '发展人名称',
   REGION_ID            varchar(50) comment '区域',
   REGION_NAME          varchar(100) comment '区域名称',
   OPERATORS_ID         numeric(8,0) comment '运营商ID',
   OPERATORS_NAME       varchar(100) comment '运营商名称',
   VALUE1               varchar(50) comment '一级标签',
   VALUE1_NAME          varchar(100) comment '一级标签名称',
   VALUE2               varchar(50) comment '二级标签',
   VALUE2_NAME          varchar(100) comment '二级标签名称',
   GOODS_ID             numeric(16,0) comment '产品',
   GOODS_NAME           varchar(100) comment '产品名称',
   GOODS_FLOW           numeric(16,2) comment '充值总流量）',
   PAY_TYPE             numeric(2,0) comment '充值类型0前台',
   UNIT                 char(2) comment '流量单位',
   SUMFEE               numeric(16,2) comment '充值总金额',
   UPDATE_TIME          datetime comment '更新时间',
   REMARK               varchar(200) comment '备注'
);

alter table LAO_USER_RECHARGE_MM comment '后台客户充值流量和金额每日汇总';

/*==============================================================*/
/* Table: LAO_USER_RES                                          */
/*==============================================================*/
create table LAO_USER_RES
(
   PARTITION_ID         numeric(4,0),
   USER_ID              numeric(16,0) not null,
   RES_TYPE_CODE        char(1) not null,
   RES_CODE             varchar(40) not null,
   RES_INFO1            varchar(50),
   RES_INFO2            varchar(50),
   RES_INFO3            varchar(50),
   RES_INFO4            varchar(50),
   RES_INFO5            varchar(50),
   RES_INFO6            varchar(50),
   RES_INFO7            varchar(100),
   RES_INFO8            varchar(100),
   START_DATE           datetime not null,
   END_DATE             datetime not null,
   primary key (USER_ID, RES_TYPE_CODE, RES_CODE, START_DATE),
   check ("USER_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_USER_STATE_DD                                     */
/*==============================================================*/
create table LAO_USER_STATE_DD
(
   IN_DATE              numeric(8,0) comment '？？？？,？？？？？？YYYYMMDD',
   CHANNEL_CUST_ID      numeric(16,0) comment '？？？？？？？？ID',
   CHANNEL_CUST_NAME    varchar(100) comment '？？？？？？？？？？？？',
   CUST_ID              numeric(16,0) comment '？？？？ID',
   CUST_NAME            varchar(100) comment '？？？？？？？？',
   STAFF_ID             varchar(50) comment '？？？？？？？？ID',
   STAFF_NAME           varchar(100) comment '？？？？？？？？？？',
   REGION_ID            varchar(50) comment '？？？？',
   REGION_NAME          varchar(100) comment '？？？？？？？？',
   OPERATORS_ID         numeric(8,0) comment '？？？？？？ID',
   OPERATORS_NAME       varchar(100) comment '？？？？？？？？？？',
   VALUE1               varchar(50) comment '？？？？？？？？',
   VALUE1_NAME          varchar(100) comment '？？？？？？？？？？？？',
   VALUE2               varchar(50) comment '？？？？？？？？',
   VALUE2_NAME          varchar(100) comment '？？？？？？？？？？？？',
   STATE_CODE           varchar(10) comment '？？？？？？',
   STATE_NAME           varchar(100) comment '？？？？？？？？？？',
   STATE_ASNAME         varchar(100) comment '？？？？？？？？？？？？？？',
   SUM_FLOW             numeric(16,2) comment '？？？？(kb)',
   CARD_NUM             numeric(16,0) comment '？？？？？？',
   UPDATE_TIME          datetime comment '？？？？？？？？',
   REMARK               varchar(200) comment '？？？？'
);

alter table LAO_USER_STATE_DD comment '？？？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_USER_STATE_MM                                     */
/*==============================================================*/
create table LAO_USER_STATE_MM
(
   IN_DATE              numeric(8,0) comment '？？？？,？？？？？？YYYYMMDD',
   CHANNEL_CUST_ID      numeric(16,0) comment '？？？？？？？？ID',
   CHANNEL_CUST_NAME    varchar(100) comment '？？？？？？？？？？？？',
   CUST_ID              numeric(16,0) comment '？？？？ID',
   CUST_NAME            varchar(100) comment '？？？？？？？？',
   STAFF_ID             varchar(50) comment '？？？？？？？？ID',
   STAFF_NAME           varchar(100) comment '？？？？？？？？？？',
   REGION_ID            varchar(50) comment '？？？？',
   REGION_NAME          varchar(100) comment '？？？？？？？？',
   OPERATORS_ID         numeric(8,0) comment '？？？？？？ID',
   OPERATORS_NAME       varchar(100) comment '？？？？？？？？？？',
   VALUE1               varchar(50) comment '？？？？？？？？',
   VALUE1_NAME          varchar(100) comment '？？？？？？？？？？？？',
   VALUE2               varchar(50) comment '？？？？？？？？',
   VALUE2_NAME          varchar(100) comment '？？？？？？？？？？？？',
   STATE_CODE           varchar(10) comment '？？？？？？',
   STATE_NAME           varchar(100) comment '？？？？？？？？？？',
   STATE_ASNAME         varchar(100) comment '？？？？？？？？？？？？？？',
   SUM_FLOW             numeric(16,2) comment '？？？？(kb)',
   CARD_NUM             numeric(16,0) comment '？？？？？？',
   UPDATE_TIME          datetime comment '？？？？？？？？',
   REMARK               varchar(200) comment '？？？？'
);

alter table LAO_USER_STATE_MM comment '？？？？？？？？？？？？？？？？';

/*==============================================================*/
/* Table: LAO_USER_SVCSTATE                                     */
/*==============================================================*/
create table LAO_USER_SVCSTATE
(
   USER_ID              numeric(16,0) not null,
   SERVICE_ID           numeric(8,0) not null,
   START_DATE           datetime not null,
   END_DATE             datetime,
   UPDATE_TIME          datetime default 'SYSDATE',
   STATE_CODE           varchar(10) not null,
   OPEARTORS_DEAL_RST   varchar(1),
   OPEARTORS_DEAL_CODE  varchar(50),
   OPEARTORS_DEAL_NUM   numeric(2,0),
   primary key (USER_ID, SERVICE_ID, START_DATE),
   check ("USER_ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_USER_TURN_DD                                      */
/*==============================================================*/
create table LAO_USER_TURN_DD
(
   IN_DATE              numeric(8,0) comment '每日，格式为YYYYMMDD',
   CHANNEL_CUST_ID      numeric(16,0) comment '渠道客户ID',
   CHANNEL_CUST_NAME    varchar(100) comment '渠道客户名称',
   CUST_ID              numeric(16,0) comment '客户ID',
   CUST_NAME            varchar(100) comment '客户名称',
   STAFF_ID             varchar(50) comment '发展人员ID',
   STAFF_NAME           varchar(100) comment '发展人名称',
   REGION_ID            varchar(50) comment '区域',
   REGION_NAME          varchar(100) comment '区域名称',
   OPERATORS_ID         numeric(8,0) comment '运营商ID',
   OPERATORS_NAME       varchar(100) comment '运营商名称',
   VALUE1               varchar(50) comment '一级标签',
   VALUE1_NAME          varchar(100) comment '一级标签名称',
   VALUE2               varchar(50) comment '二级标签',
   VALUE2_NAME          varchar(100) comment '二级标签名称',
   GOODS_ID             numeric(16,0) comment '产品',
   GOODS_NAME           varchar(100) comment '产品名称',
   GOODS_FLOW           numeric(16,2) comment '套餐转兑总流量（初始套餐的流量和）',
   UNIT                 char(2) comment '流量单位',
   UPDATE_TIME          datetime comment '更新时间',
   REMARK               varchar(200) comment '备注'
);

alter table LAO_USER_TURN_DD comment '每日套餐内转兑总流量汇总';

/*==============================================================*/
/* Table: LAO_USER_TURN_MM                                      */
/*==============================================================*/
create table LAO_USER_TURN_MM
(
   IN_DATE              numeric(8,0) comment '每日，格式为YYYYMMDD',
   CHANNEL_CUST_ID      numeric(16,0) comment '渠道客户ID',
   CHANNEL_CUST_NAME    varchar(100) comment '渠道客户名称',
   CUST_ID              numeric(16,0) comment '客户ID',
   CUST_NAME            varchar(100) comment '客户名称',
   STAFF_ID             varchar(50) comment '发展人员ID',
   STAFF_NAME           varchar(100) comment '发展人名称',
   REGION_ID            varchar(50) comment '区域',
   REGION_NAME          varchar(100) comment '区域名称',
   OPERATORS_ID         numeric(8,0) comment '运营商ID',
   OPERATORS_NAME       varchar(100) comment '运营商名称',
   VALUE1               varchar(50) comment '一级标签',
   VALUE1_NAME          varchar(100) comment '一级标签名称',
   VALUE2               varchar(50) comment '二级标签',
   VALUE2_NAME          varchar(100) comment '二级标签名称',
   GOODS_ID             numeric(16,0) comment '产品',
   GOODS_NAME           varchar(100) comment '产品名称',
   GOODS_FLOW           numeric(16,2) comment '套餐转兑总流量（初始套餐的流量和）',
   UNIT                 char(2) comment '流量单位',
   UPDATE_TIME          datetime comment '更新时间',
   REMARK               varchar(200) comment '备注'
);

alter table LAO_USER_TURN_MM comment '每月套餐内转兑总流量汇总';

/*==============================================================*/
/* Table: LAO_VAL_PARAM                                         */
/*==============================================================*/
create table LAO_VAL_PARAM
(
   ID_TYPE              varchar(2) not null comment '1-？？？？ID？？RULE_ID,11？？？？？？1？？？？？？2-？？？？？？？？ID？？TEMPLETE_ID？？8？？？？？？1？？？？',
   ID_VALUE             numeric(11,0) not null,
   PARA_TYPE            varchar(100) comment '1-？？？？？？？？？？  2-？？？？？？？？？？
            ？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？2-？？？？？？？？  ？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？1？？？？？？？？
            ？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？',
   PARA_NAME            varchar(100) not null comment '？？？？LAO_RULE_DEF？？？？COND_STAT ？？ CAL_FORMULA？？？？？？？？？？？？？？？？？？
            ？？ VAL1',
   PARA_DESC            varchar(200) comment '？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？',
   PARA_VALUE           varchar(100) comment '？？？？？？？？？？:
            ？？PARA_TYPE=1？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？Map？？？？？？？？？？Map？？？？realFee？？？？？？？？？？？？ PARA_VALUE？？？？？？？？？？realFee？？？？？？？？？？？？？？？？Map.get(PARA_VALUE)？？？？？？？？map？？realFee？？？？？？
            ？？PARA_TYPE=2？？？？？？？？？？？？？？？？？？12？？？？',
   REMARK               varchar(200),
   primary key (ID_VALUE, ID_TYPE, PARA_NAME),
   check ("ID_TYPE" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_WHITELIST                                         */
/*==============================================================*/
create table LAO_WHITELIST
(
   ID                   numeric(20,0) not null,
   CELL                 varchar(100),
   WTYPE                numeric(1,0),
   CREATEDATE           datetime,
   MODIFYDATE           datetime,
   check ("ID" IS NOT NULL)
);

/*==============================================================*/
/* Table: LAO_WXB                                               */
/*==============================================================*/
create table LAO_WXB
(
   ICCID                varchar(50)
);

/*==============================================================*/
/* Table: T_TEST_B                                              */
/*==============================================================*/
create table T_TEST_B
(
   T_NAME               varchar(200)
);

/*==============================================================*/
/* Table: WXB_TMP                                               */
/*==============================================================*/
create table WXB_TMP
(
   ICCID                varchar(20)
);

/*==============================================================*/
/* Table: WXB_TMP1                                              */
/*==============================================================*/
create table WXB_TMP1
(
   ICCID                varchar(20)
);


create or replace procedure DD_REMAIN(csr out forDD.my_ddcsr)
is

begin
  open csr for
 select ct1.cust_id channel_cust_id,ct1.cust_name channel_cust_name,ct.cust_id,ct.cust_name,
op.operators_id,op.operators_name,
st1.static_id static1,st1.static_name staticName1,sstatic_id static2,sstatic_name staticName2,
sum(pay1.recv_fee),
sum(pay2.real_fee),
acct.deposit_money
from lao_user ur
left join lao_customer ct on ct.cust_id=ur.cust_id
left join lao_customer ct1 on ct1.cust_id=ur.channel_cust_id
left join lao_operators op on op.operators_id=ur.operators_id
left join lao_ss_static st1 on st1.static_id=ur.value1
left join lao_ss_static st2 on sstatic_id=ur.value2
left join lao_b_paylog pay1 on pay1.channel_cust_id=ur.channel_cust_id and pay1.payment_op='10000' and to_char(pay1.recv_time,'YYYYMMDD')=to_char(sysdate,'YYYYMMDD')
left join lao_b_paylog pay2 on pay2.channel_cust_id=ur.channel_cust_id and pay2.payment_op='10004' and to_char(pay2.recv_time,'YYYYMMDD')=to_char(sysdate,'YYYYMMDD')
left join lao_f_acctdeposit acct on acct.channel_cust_id=ur.channel_cust_id
group by ct1.cust_id,ct1.cust_name,ct.cust_id,ct.cust_name,
op.operators_id,op.operators_name,
st1.static_id,st1.static_name,sstatic_id,sstatic_name,acct.deposit_money;
end dd_remain;


create or replace procedure INSERTACTIVE
is

begin
insert into LAO_USER_ACTIVE_DD select 20170707,3071707120149050,'联想测试账户'，3071707120149050，'联想测试账户','','','','',1,'中国联通'，'','','','',211334,'',3,12,sysdate,'' from dual;
end;


create or replace procedure INSERTACTIVE1
is

begin
insert into LAO_USER_ACTIVE_DD(IN_DATE,CHANNEL_CUST_ID,UPDATE_TIME) select 20170707,3071707120149051,sysdate from dual;
commit;
end;


create or replace procedure INSERTACTIVE2
is

begin
insert into LAO_USER_ACTIVE_DD(IN_DATE,CHANNEL_CUST_ID,CHANNEL_CUST_NAME,CUST_ID,CUST_NAME,OPERATORS_ID,OPERATORS_NAME,VALUE1,VALUE1_NAME,VALUE2,VALUE2_NAME,GOODS_ID,GOODS_NAME,OPEN_NUM,UPDATE_TIME)
select to_number(to_char(sysdate,'YYYYMMDD')),ct1.cust_id channelcustid,ct1.cust_name channelcustname,ct.cust_id custid,ct.cust_name custname,
ur.operators_id operatorid,ur.operators_name operatorname,
ur.static_id1 value1,ur.static_name1 value1name,ur.static_id2 value2,ur.static_name2 value2name,
god.goods_id goodsid,god.goods_name goodsname,count(1) sumAll,sysdate
from lao_trade trade
left join lao_customer ct on ct.cust_id=trade.cust_id
left join lao_customer ct1 on ct1.cust_id=trade.channal_cust_id
left join (
select ur.user_id,op.operators_id,op.operators_name,
st1.static_id static_id1,st1.static_name static_name1,sstatic_id static_id2,sstatic_name static_name2
from lao_user ur
left join lao_operators op on op.operators_id=ur.operators_id
left join lao_ss_static st1 on st1.static_id=ur.value1
left join lao_ss_static st2 on sstatic_id=ur.value2) ur on ur.user_id=trade.user_id
left join lao_goods god on god.goods_id=trade.goods_id
where to_char(trade.accept_date,'yyyymmdd')=to_char(sysdate,'yyyymmdd')
and trade.trade_type_code=100
group by ct1.cust_id,ct1.cust_name,ct.cust_id,ct.cust_name,
'','','','',
ur.operators_id,ur.operators_name,
ur.static_id1,ur.static_name1,ur.static_id2,ur.static_name2,
god.goods_id,god.goods_name;
end;


create or replace procedure PROC_ACCT_DEPOSIT_DD
is
begin
insert into lao_acct_deposit_dd
select to_number(to_char(sysdate - 1, 'YYYYMMDD')) in_date,
       ct.cust_id channel_cust_id,
       ct.cust_name channel_cust_name,
       null cust_id,
       '' cust_name,
       staff.staff_id,
       staff.staff_name,
       staff.region_id,
       staff.region_name,
       null operators_id,
       '' operators_name,
       '' static_id1,
       '' static_name1,
       '' static_id2,
       '' static_id2,
       nvl(pay1.sum_recv_fee, 0) in_money,
       null fee,
       acct.deposit_money left_money,
       sysdate update_time,
       '' remark,
       10000 payment_op
  from lao_customer ct,
       (select pay.channel_cust_id, sum(pay.recv_fee) sum_recv_fee
          from lao_b_paylog pay
         where pay.payment_op = '10000'
           and pay.recv_time > to_date(sysdate - 1)
         group by pay.channel_cust_id) pay1,

       lao_f_acctdeposit acct,
       lao_staff         staff
 where ct.cust_type = '1'
   and ct.cust_id = pay1.channel_cust_id(+)
   and ct.cust_id = acct.channel_cust_id(+)
   and ct.rsrv_str1 = staff.staff_id(+)
union all
select to_number(to_char(sysdate - 1, 'YYYYMMDD')) in_date,
       ct.cust_id,
       ct.cust_name,
       null cust_id,
       '' cust_name,
       staff.staff_id,
       staff.staff_name,
       staff.region_id,
       staff.region_name,
       op.operators_id,
       op.operators_name,
       st1.static_code,
       st1.static_name,
       sstatic_code,
       sstatic_name,
       null recv_fee,
       sum(pay.real_fee),
       null deposit_money,
       sysdate,
       '' remark,
       10004 payment_op
  from lao_b_paylog pay,
       lao_user ur,
       lao_operators op,
       (select st.static_code, st.static_name
          from lao_ss_static st
         where st.tab_name = 'LAO_USER') st1,
       (select st.static_code, st.static_name
          from lao_ss_static st
         where st.tab_name = 'LAO_USER') st2,
       lao_customer ct,
       lao_staff         staff
 where ur.user_id = pay.user_id
   and ur.operators_id = op.operators_id
   and ur.attribute1 = st1.static_code(+)
   and ur.attribute2 = sstatic_code(+)
   and ur.channel_cust_id = ct.cust_id
   and ct.rsrv_str1 = staff.staff_id(+)
   and pay.payment_op = '10004'
   and pay.recv_time > to_date(sysdate - 1)
group by  ct.cust_id,
       ct.cust_name,
       staff.staff_id,
       staff.staff_name,
       staff.region_id,
       staff.region_name,
       op.operators_id,
       op.operators_name,
       st1.static_code,
       st1.static_name,
       sstatic_code,
       sstatic_name;
end;


create or replace procedure PROC_ACCT_DEPOSIT_MM
is
begin
 delete from lao_acct_deposit_mm deposit_mm where deposit_mm.in_date=to_number(to_char(sysdate-1, 'YYYYMM'));
  insert into lao_acct_deposit_mm
   select to_number(to_char(sysdate-1, 'YYYYMM')) in_date,
       deposit_dd.channel_cust_id,
       deposit_dd.channel_cust_name,
       null cust_id,
       '' cust_name,
       deposit_dd.staff_id,
       deposit_dd.staff_name,
       deposit_dd.region_id,
       deposit_dd.region_name,
       null operators_id,
       '' operators_name,
       '' static_id1,
       '' static_name1,
       '' static_id2,
       '' static_id2,
       sum(deposit_dd.in_money),
       null fee,
       acct.deposit_money,
       sysdate update_time,
       '' remark,
       10000
  from lao_acct_deposit_dd deposit_dd, lao_f_acctdeposit acct
 where deposit_dd.payment_op = 10000
   and to_char(round(deposit_dd.in_date / 100)) =
       to_char(sysdate-1, 'YYYYMM')
   and deposit_dd.channel_cust_id = acct.channel_cust_id(+)
 group by deposit_dd.channel_cust_id,
          deposit_dd.channel_cust_name,
          deposit_dd.staff_id,
          deposit_dd.staff_name,
          deposit_dd.region_id,
          deposit_dd.region_name,
          acct.deposit_money
union all
select to_number(to_char(sysdate - 1, 'YYYYMM')) in_date,
       deposit_dd.channel_cust_id,
       deposit_dd.channel_cust_name,
       null cust_id,
       '' cust_name,
       deposit_dd.staff_id,
       deposit_dd.staff_name,
       deposit_dd.region_id,
       deposit_dd.region_name,
       deposit_dd.operators_id,
       deposit_dd.operators_name,
       deposit_dd.value1,
       deposit_dd.value1_name,
       deposit_dd.value2,
       deposit_dd.value2_name,
       null recv_fee,
       sum(deposit_dd.fee),
       null deposit_money,
       sysdate,
       '' remark,
       10004
  from lao_acct_deposit_dd deposit_dd
 where deposit_dd.payment_op = 10004
   and deposit_dd.in_date >to_number(to_char(sysdate-1, 'YYYYMM')||'00')
 group by deposit_dd.channel_cust_id,
          deposit_dd.channel_cust_name,
          deposit_dd.staff_id,
          deposit_dd.staff_name,
          deposit_dd.region_id,
          deposit_dd.region_name,
          deposit_dd.operators_id,
          deposit_dd.operators_name,
          deposit_dd.value1,
          deposit_dd.value1_name,
          deposit_dd.value2,
          deposit_dd.value2_name,
          deposit_dd.fee;
end;


create or replace procedure PROC_ACCT_DEPOSIT_TEST
is
begin
 delete from lao_acct_deposit_dd deposit_dd where deposit_dd.in_date=20170100;
insert into lao_acct_deposit_dd(in_date) values(20170101);
end;


create or replace procedure PROC_GOODS_STATE_DD
is
begin
insert into lao_goods_state_dd
 select to_number(to_char(sysdate - 1, 'YYYYMMDD')),
       cust.channel_cust_id,
       cust.channel_cust_name,
       null cust_id,
       '' cust_name,
       cust.staff_id,
       cust.staff_name,
       cust.region_id,
       cust.region_name,
       op.operators_id,
       op.operators_name,
       st1.static_code,
       st1.static_name,
       sstatic_code,
       sstatic_name,
       base.goods_id,
       base.goods_name,
       base.outsidestate,
       base.static_name,
       '' service_name,
       base.num,
       sysdate update_time,
       '' remark
  from (select ur.channel_cust_id,
               ur.operators_id,
               ur.attribute1,
               ur.attribute2,
               ug.goods_id,
               god.goods_name,
               status.outsidestate,
               st1.static_name,
               count(1) num
          from lao_user ur,
          lao_user_goods ug,
          lao_goods god,
               lao_service_status status,
               (select st.static_code, st.static_name
                  from lao_ss_static st
                 where st.tab_name = 'LAO_SERVICE_STATUS'
                   and st.col_name = 'OUTSIDESTATE') st1
         where ur.user_id=ug.user_id
         and ug.goods_id=god.goods_id
         and ur.state_code = status.state_code
           and status.outsidestate = st1.static_code(+)
         group by ur.channel_cust_id,
                  ur.operators_id,
                  ur.attribute1,
                  ur.attribute2,
                  ug.goods_id,
                  god.goods_name,
                  status.outsidestate,
                  st1.static_name) base,
       (select ct.cust_id channel_cust_id,
               ct.cust_name channel_cust_name,
               staff.staff_id,
               staff.staff_name,
               staff.region_id,
               staff.region_name
          from lao_customer ct, lao_staff staff
         where ct.cust_type = '1'
           and ct.rsrv_str1 = staff.staff_id(+)) cust,
       lao_operators op,
       (select st.static_code, st.static_name
          from lao_ss_static st
         where st.tab_name = 'LAO_USER') st1,
       (select st.static_code, st.static_name
          from lao_ss_static st
         where st.tab_name = 'LAO_USER') st2
 where base.channel_cust_id = cust.channel_cust_id
   and base.operators_id = op.operators_id
   and base.attribute1 = st1.static_code(+)
   and base.attribute2 = sstatic_code(+);
end;


create or replace procedure PROC_GOODS_STATE_MM
is
begin
  delete from lao_goods_state_mm state_mm
   where state_mm.in_date = to_number(to_char(sysdate - 1, 'YYYYMM'));
  insert into lao_goods_state_mm
   select to_number(to_char(sysdate - 1, 'yyyymm')),
       usd.channel_cust_id,
       usd.channel_cust_name,
       usd.cust_id,
       usd.cust_name,
       usd.staff_id,
       usd.staff_name,
       usd.region_id,
       usd.region_name,
       usd.operators_id,
       usd.operators_name,
       usd.value1,
       usd.value1_name,
       usd.value2,
       usd.value2_name,
       usd.goods_id,
       usd.goods_name,
       usd.state_code,
       usd.state_name,
       usd.state_asname,
       usd.card_num,
       sysdate,
       ''
  from lao_goods_state_dd usd
 where usd.in_date = to_number(to_char(sysdate - 1, 'yyyymmdd'));
end;


create or replace procedure PROC_USER_ACTIVE_DD
is
begin
insert into LAO_USER_ACTIVE_DD
select to_number(to_char(sysdate - 1, 'YYYYMMDD')),
       base.channel_cust_id,
       cust.channel_cust_name,
       null cust_id,
       '' cust_name,
       cust.staff_id,
       cust.staff_name,
       cust.region_id,
       cust.region_name,
       op.operators_id,
       op.operators_name,
       base.attribute1,
       st1.static_name,
       base.attribute2,
       sstatic_name,
       god.goods_id,
       god.goods_name,
       null goods_cycle,
       base.cardnum,
       sysdate update_time,
       '' remark
  from (
 select ur.channel_cust_id,
        ur.cust_id,
        ug.goods_id,
        ur.operators_id,
        ur.attribute1,
        ur.attribute2,
        count(1) cardnum
   from lao_user ur, lao_user_goods ug
  where ur.user_id = ug.user_id(+)
    and ur.first_call_time > to_date(sysdate - 1)
    group by ur.channel_cust_id,
        ur.cust_id,
        ug.goods_id,
        ur.operators_id,
        ur.attribute1,
        ur.attribute2) base,
       lao_operators op,
       (select st.static_code, st.static_name
          from lao_ss_static st
         where st.tab_name = 'LAO_USER') st1,
       (select st.static_code, st.static_name
          from lao_ss_static st
         where st.tab_name = 'LAO_USER') st2,
       lao_goods god,
       (select ct.cust_id channel_cust_id,
               ct.cust_name channel_cust_name,
               staff.staff_id,
               staff.staff_name,
               staff.region_id,
               staff.region_name
          from lao_customer ct, lao_staff staff
         where ct.cust_type = '1'
           and ct.rsrv_str1 = staff.staff_id(+)) cust
 where base.goods_id = god.goods_id
   and base.operators_id = op.operators_id
   and base.attribute1 = st1.static_code(+)
   and base.attribute2 = sstatic_code(+)
   and base.channel_cust_id = cust.channel_cust_id;
end;


create or replace procedure PROC_USER_ACTIVE_MM
is
begin
  delete from lao_user_active_mm active_mm
   where active_mm.in_date = to_number(to_char(sysdate - 1, 'YYYYMM'));
  insert into LAO_USER_ACTIVE_mm
    select to_number(to_char(sysdate - 1, 'YYYYMM')),
           active_dd.channel_cust_id,
           active_dd.channel_cust_name,
           active_dd.cust_id,
           active_dd.cust_name,
           active_dd.staff_id,
           active_dd.staff_name,
           active_dd.region_id,
           active_dd.region_name,
           active_dd.operators_id,
           active_dd.operators_name,
           active_dd.value1,
           active_dd.value1_name,
           active_dd.value2,
           active_dd.value2_name,
           active_dd.goods_id,
           active_dd.goods_name,
           active_dd.goods_cycle,
           sum(active_dd.open_num),
           sysdate update_time,
           '' remark
      from lao_user_active_dd active_dd
      where active_dd.in_date>to_number(to_char(sysdate-1, 'YYYYMM')||'00')
     group by active_dd.channel_cust_id,
              active_dd.channel_cust_name,
              active_dd.cust_id,
              active_dd.cust_name,
              active_dd.staff_id,
              active_dd.staff_name,
              active_dd.region_id,
              active_dd.region_name,
              active_dd.operators_id,
              active_dd.operators_name,
              active_dd.value1,
              active_dd.value1_name,
              active_dd.value2,
              active_dd.value2_name,
              active_dd.goods_id,
              active_dd.goods_name,
              active_dd.goods_cycle;
end;


create or replace procedure PROC_USER_EXPIRE_DD
is
begin
insert into lao_user_expire_dd
 select to_number(to_char(sysdate - 1, 'YYYYMMDD')),
       cust.channel_cust_id,
       cust.channel_cust_name,
       null cust_id,
       '' cust_name,
       cust.staff_id,
       cust.staff_name,
       cust.region_id,
       cust.region_name,
       op.operators_id,
       op.operators_name,
       st1.static_code,
       st1.static_name,
       sstatic_code,
       sstatic_name,
       base.goods_id,
       base.goods_name,
       base.release_cycle,
       base.goods_type,
       base.num,
       base.end_date,
       sysdate update_time,
       '' remark
  from (select ur.channel_cust_id,
               gs.goods_id,
               gs.goods_name,
               gs.goods_type,
               ug.release_cycle,
               to_char(ug.end_date, 'YYYYMM') end_date,
               ur.operators_id,
               ur.attribute1,
               ur.attribute2,
               count(1) num
          from lao_user_goods ug, lao_user ur, lao_goods gs
         where ug.user_id = ur.user_id
           and ug.goods_id = gs.goods_id
           and ug.start_use_date>to_date(sysdate - 1)
         group by ur.channel_cust_id,
                  gs.goods_id,
                  gs.goods_name,
                  gs.goods_type,
                  ug.release_cycle,
                  to_char(ug.end_date, 'YYYYMM'),
                  ur.operators_id,
                  ur.attribute1,
                  ur.attribute2) base,
       (select ct.cust_id channel_cust_id,
               ct.cust_name channel_cust_name,
               staff.staff_id,
               staff.staff_name,
               staff.region_id,
               staff.region_name
          from lao_customer ct, lao_staff staff
         where ct.cust_type = '1'
           and ct.rsrv_str1 = staff.staff_id(+)) cust,
       lao_operators op,
       (select st.static_code, st.static_name
          from lao_ss_static st
         where st.tab_name = 'LAO_USER') st1,
       (select st.static_code, st.static_name
          from lao_ss_static st
         where st.tab_name = 'LAO_USER') st2
 where base.channel_cust_id = cust.channel_cust_id
   and base.operators_id = op.operators_id
   and base.attribute1 = st1.static_code(+)
   and base.attribute2 = sstatic_code(+);
end;


create or replace procedure PROC_USER_EXPIRE_MM
is
begin
  delete from lao_user_expire_mm uem
   where uem.in_date = to_number(to_char(sysdate - 1, 'YYYYMM'));
insert into lao_user_expire_mm
 select    to_number(to_char(sysdate - 1, 'YYYYMM')),
           ued.channel_cust_id,
           ued.channel_cust_name,
           ued.cust_id,
           ued.cust_name,
           ued.staff_id,
           ued.staff_name,
           ued.region_id,
           ued.region_name,
           ued.operators_id,
           ued.operators_name,
           ued.value1,
           ued.value1_name,
           ued.value2,
           ued.value2_name,
           ued.goods_id,
           ued.goods_name,
           ued.goods_cycle,
           ued.goods_type,
           sum(ued.expire_num),
           ued.end_date,
           sysdate update_time,
           '' remark
  from lao_user_expire_dd ued
 where ued.in_date > to_number(to_char(sysdate - 1, 'YYYYMM') || '00')
 group by  ued.channel_cust_id,
           ued.channel_cust_name,
           ued.cust_id,
           ued.cust_name,
           ued.staff_id,
           ued.staff_name,
           ued.region_id,
           ued.region_name,
           ued.operators_id,
           ued.operators_name,
           ued.value1,
           ued.value1_name,
           ued.value2,
           ued.value2_name,
           ued.goods_id,
           ued.goods_name,
           ued.goods_cycle,
           ued.goods_type,
           ued.end_date;
end;


create or replace procedure PROC_USER_FLOW_DD
is
begin
  insert into lao_user_flow_dd
    select to_number(to_char(sysdate - 1, 'YYYYMMDD')),
           cust.channel_cust_id,
           cust.channel_cust_name,
           null cust_id,
           '' cust_name,
           cust.staff_id,
           cust.staff_name,
           cust.region_id,
           cust.region_name,
           op.operators_id,
           op.operators_name,
           st1.static_code,
           st1.static_name,
           sstatic_code,
           sstatic_name,
           base.sum_all,
           base.sum_use,
           base.sum_remain,
           sysdate update_time,
           '' remark
      from (select ur.channel_cust_id,
                   ur.operators_id,
                   ur.attribute1,
                   ur.attribute2,
                   sum(td.use_count) + sum(td.data_remaining) sum_all,
                   sum(td.use_count) sum_use,
                   sum(td.data_remaining) sum_remain
              from lao_user ur, lao_traffic_detail td
             where ur.user_id = td.user_id
               and td.data_cycle = to_char(sysdate - 1, 'yyyymmdd')
             group by ur.channel_cust_id,
                      ur.operators_id,
                      ur.attribute1,
                      ur.attribute2) base,
           (select ct.cust_id        channel_cust_id,
                   ct.cust_name      channel_cust_name,
                   staff.staff_id,
                   staff.staff_name,
                   staff.region_id,
                   staff.region_name
              from lao_customer ct, lao_staff staff
             where ct.cust_type = '1'
               and ct.rsrv_str1 = staff.staff_id(+)) cust,
           lao_operators op,
           (select st.static_code, st.static_name
              from lao_ss_static st
             where st.tab_name = 'LAO_USER') st1,
           (select st.static_code, st.static_name
              from lao_ss_static st
             where st.tab_name = 'LAO_USER') st2
     where base.channel_cust_id = cust.channel_cust_id
       and base.operators_id = op.operators_id
       and base.attribute1 = st1.static_code(+)
       and base.attribute2 = sstatic_code(+);
end;


create or replace procedure PROC_USER_FLOW_MM
is
begin
  delete from lao_user_flow_mm flow_mm
   where flow_mm.in_date = to_number(to_char(sysdate - 1, 'YYYYMM'));
  insert into lao_user_flow_mm
    select to_number(to_char(sysdate - 1, 'YYYYMM')),
           flow_dd.channel_cust_id,
           flow_dd.channel_cust_name,
           flow_dd.cust_id,
           flow_dd.cust_name,
           flow_dd.staff_id,
           flow_dd.staff_name,
           flow_dd.region_id,
           flow_dd.region_name,
           flow_dd.operators_id,
           flow_dd.operators_name,
           flow_dd.value1,
           flow_dd.value1_name,
           flow_dd.value2,
           flow_dd.value2_name,
           sum(flow_dd.total_cnt),
           sum(flow_dd.use_cnt),
           sum(flow_dd.left_cnt),
           sysdate update_time,
           '' remark
      from lao_user_flow_dd flow_dd
     where flow_dd.in_date >to_number(to_char(sysdate-1, 'YYYYMM')||'00')
     group by flow_dd.channel_cust_id,
              flow_dd.channel_cust_name,
              flow_dd.cust_id,
              flow_dd.cust_name,
              flow_dd.staff_id,
              flow_dd.staff_name,
              flow_dd.region_id,
              flow_dd.region_name,
              flow_dd.operators_id,
              flow_dd.operators_name,
              flow_dd.value1,
              flow_dd.value1_name,
              flow_dd.value2,
              flow_dd.value2_name;
end;


create or replace procedure PROC_USER_IN_DD
is
begin
insert into lao_user_in_dd
select to_number(to_char(sysdate - 1, 'YYYYMMDD')),
       base.channel_cust_id,
       cust.channel_cust_name,
       null cust_id,
       '' cust_name,
       cust.staff_id,
       cust.staff_name,
       cust.region_id,
       cust.region_name,
       base.operators_id,
       op.operators_name,
       base.attribute1,
       st1.static_name,
       base.attribute2,
       sstatic_name,
       base.goods_id,
       god.goods_name,
       base.cardnum,
       sysdate update_time,
       '' remark
  from (select ur.channel_cust_id,
               ur.operators_id,
               ur.attribute1,
               ur.attribute2,
               ug.goods_id,
               count(1) cardnum
          from lao_user ur, lao_user_goods ug
         where ur.user_id = ug.user_id(+)
           and ur.in_date > to_date(sysdate - 10)
         group by ur.channel_cust_id,
                  ur.operators_id,
                  ur.attribute1,
                  ur.attribute2,
                  ug.goods_id) base,
       (select ct.cust_id        channel_cust_id,
               ct.cust_name      channel_cust_name,
               staff.staff_id,
               staff.staff_name,
               staff.region_id,
               staff.region_name
          from lao_customer ct, lao_staff staff
         where ct.cust_type = '1'
           and ct.rsrv_str1 = staff.staff_id(+)) cust,
       lao_operators op,
       lao_goods god,
       (select st.static_code, st.static_name
          from lao_ss_static st
         where st.tab_name = 'LAO_USER') st1,
       (select st.static_code, st.static_name
          from lao_ss_static st
         where st.tab_name = 'LAO_USER') st2
 where base.channel_cust_id = cust.channel_cust_id
   and base.operators_id = op.operators_id
   and base.goods_id=god.goods_id
   and base.attribute1 = st1.static_code(+)
   and base.attribute2 = sstatic_code(+);
end;


create or replace procedure PROC_USER_IN_MM
is
begin
 delete from lao_user_in_mm in_mm
   where in_mm.in_date = to_number(to_char(sysdate - 1, 'YYYYMM'));
  insert into lao_user_in_mm
    select to_number(to_char(sysdate - 1, 'YYYYMM')),
           in_dd.channel_cust_id,
           in_dd.channel_cust_name,
           in_dd.cust_id,
           in_dd.cust_name,
           in_dd.staff_id,
           in_dd.staff_name,
           in_dd.region_id,
           in_dd.region_name,
           in_dd.operators_id,
           in_dd.operators_name,
           in_dd.value1,
           in_dd.value1_name,
           in_dd.value2,
           in_dd.value2_name,
           in_dd.goods_id,
           in_dd.goods_name,
           sum(in_dd.card_num),
           sysdate update_time,
           '' remark
      from lao_user_in_dd in_dd
     where in_dd.in_date>to_number(to_char(sysdate-1, 'YYYYMM')||'00')
     group by in_dd.channel_cust_id,
              in_dd.channel_cust_name,
              in_dd.cust_id,
              in_dd.cust_name,
              in_dd.staff_id,
              in_dd.staff_name,
              in_dd.region_id,
              in_dd.region_name,
              in_dd.operators_id,
              in_dd.operators_name,
              in_dd.value1,
              in_dd.value1_name,
              in_dd.value2,
              in_dd.value2_name,
              in_dd.goods_id,
              in_dd.goods_name;
end;


create or replace procedure PROC_USER_ORDER_DD
is
begin
insert into lao_user_order_dd
select to_number(to_char(sysdate - 1, 'YYYYMMDD')),
       cust1.channel_cust_id,
       cust1.channel_cust_name,
       cust0.cust_id,
       cust0.cust_name,
       cust1.staff_id,
       cust1.staff_name,
       cust1.region_id,
       cust1.region_name,
       op.operators_id,
       op.operators_name,
       base.attribute1,
       st1.static_name,
       base.attribute2,
       sstatic_name,
       god.goods_id,
       god.goods_name,
       base.release_cycle,
       base.unit,
       plan.cost_price,
       base.release_price,
       base.num,
       base.cardnum,
       plan.total_flow,
       sysdate update_time,
       '' remark
  from (select trade.channal_cust_id,
               trade.cust_id,
               trade.goods_id,
               ur.operators_id,
               ur.attribute1,
               ur.attribute2,
               trade.goods_release_id,
               rel.release_cycle,
               rel.unit,
               rel.release_price,
               count(distinct ur.user_id) cardnum,
               count(1) num
          from lao_trade trade, lao_user ur, lao_goods_release rel
         where trade.accept_date > to_date(sysdate - 1)
           and trade.user_id = ur.user_id(+)
           and trade.goods_release_id = rel.goods_release_id
           and trade.trade_type_code = 120
         group by trade.channal_cust_id,
                  trade.cust_id,
                  trade.goods_id,
                  ur.operators_id,
                  ur.attribute1,
                  ur.attribute2,
                  trade.goods_release_id,
                  rel.release_cycle,
                  rel.unit,
                  rel.release_price) base,
       (select ge.goods_id,
               plan.cost_price,
               sum(decode(plan.quantity_unit,
                          'M',
                          plan.quantity_max * 1024,
                          'G',
                          plan.quantity_max * 1024 * 1024)) total_flow
          from lao_goods_element ge
          left join lao_operator_plan plan on ge.original_id = plan.plan_id
         group by ge.goods_id, plan.cost_price) plan,
       (select ct.cust_id,
               ct.cust_name
          from lao_customer ct
         where ct.cust_type = '0') cust0,
            (select ct.cust_id channel_cust_id,
               ct.cust_name channel_cust_name,
               staff.staff_id,
               staff.staff_name,
               staff.region_id,
               staff.region_name
          from lao_customer ct, lao_staff staff
         where ct.cust_type = '1'
           and ct.rsrv_str1 = staff.staff_id(+)) cust1,
       lao_operators op,
       (select st.static_code, st.static_name
          from lao_ss_static st
         where st.tab_name = 'LAO_USER') st1,
       (select st.static_code, st.static_name
          from lao_ss_static st
         where st.tab_name = 'LAO_USER') st2,
       lao_goods god
 where base.goods_id = plan.goods_id
   and base.cust_id = cust0.cust_id
   and base.channal_cust_id=cust1.channel_cust_id
   and base.operators_id = op.operators_id(+)
   and base.attribute1 = st1.static_code(+)
   and base.attribute2 = sstatic_code(+)
   and base.goods_id = god.goods_id;
end;


create or replace procedure PROC_USER_ORDER_MM
is
begin
  delete from lao_user_order_mm order_mm
   where order_mm.in_date = to_number(to_char(sysdate - 1, 'YYYYMM'));
  insert into lao_user_order_mm
    select to_number(to_char(sysdate - 1, 'YYYYMM')),
           order_dd.channel_cust_id,
           order_dd.channel_cust_name,
           order_dd.cust_id,
           order_dd.cust_name,
           order_dd.staff_id,
           order_dd.staff_name,
           order_dd.region_id,
           order_dd.region_name,
           order_dd.operators_id,
           order_dd.operators_name,
           order_dd.value1,
           order_dd.value1_name,
           order_dd.value2,
           order_dd.value2_name,
           order_dd.goods_id,
           order_dd.goods_name,
           order_dd.goods_cycle,
           order_dd.unit,
           order_dd.cost_price,
           order_dd.goods_price,
           sum(order_dd.order_num),
           sum(order_dd.card_num),
           order_dd.total_flow,
           sysdate update_time,
           '' remark
      from lao_user_order_dd order_dd
     where order_dd.in_date>to_number(to_char(sysdate-1, 'YYYYMM')||'00')
     group by order_dd.channel_cust_id,
              order_dd.channel_cust_name,
              order_dd.cust_id,
              order_dd.cust_name,
              order_dd.staff_id,
              order_dd.staff_name,
              order_dd.region_id,
              order_dd.region_name,
              order_dd.operators_id,
              order_dd.operators_name,
              order_dd.value1,
              order_dd.value1_name,
              order_dd.value2,
              order_dd.value2_name,
              order_dd.goods_id,
              order_dd.goods_name,
              order_dd.goods_cycle,
              order_dd.unit,
              order_dd.cost_price,
              order_dd.goods_price,
              order_dd.total_flow;
end;


create or replace procedure PROC_USER_RECHARGE_DD
is
begin
  insert into LAO_USER_RECHARGE_DD
  select to_number(to_char(sysdate - 1, 'YYYYMMDD')),
       base.channel_cust_id,
       cust.channel_cust_name,
       null cust_id,
       '' cust_name,
       cust.staff_id,
       cust.staff_name,
       cust.region_id,
       cust.region_name,
       base.operators_id,
       op.operators_name,
       base.attribute1,
       st1.static_name,
       base.attribute2,
       sstatic_name,
       base.goods_id,
       god.goods_name,
       base.flowmax,
       base.pay_type,
       base.quantity_unit,
       base.sumfee,
       sysdate update_time,
       '' remark
  from (select ur.channel_cust_id,
               ur.attribute1,
               ur.attribute2,
               trade.goods_id,
               ur.operators_id,
               sum(op.quantity_max) flowmax,
               op.quantity_unit,
               sub.pay_type,
               sum(sub.fee) sumfee
          from lao_trade         trade,
               lao_user          ur,
               lao_tradefee_sub  sub,
               lao_goods_element ge,
               lao_operator_plan op
         where trade.user_id = ur.user_id
           and trade.trade_id = sub.trade_id
           and trade.goods_id = ge.goods_id
           and ge.original_id = op.plan_id
           and trade.accept_date = to_date(sysdate-1)
           and trade.trade_type_code = 120
         group by ur.channel_cust_id,
                  ur.attribute1,
                  ur.attribute2,
                  trade.goods_id,
                  ur.operators_id,
                   sub.pay_type,
                  op.quantity_unit) base,
       lao_operators op,
       (select st.static_code, st.static_name
          from lao_ss_static st
         where st.tab_name = 'LAO_USER') st1,
       (select st.static_code, st.static_name
          from lao_ss_static st
         where st.tab_name = 'LAO_USER') st2,
       lao_goods god,
       (select ct.cust_id        channel_cust_id,
               ct.cust_name      channel_cust_name,
               staff.staff_id,
               staff.staff_name,
               staff.region_id,
               staff.region_name
          from lao_customer ct, lao_staff staff
         where ct.cust_type = '1'
           and ct.rsrv_str1 = staff.staff_id(+)) cust
 where base.goods_id = god.goods_id
   and base.operators_id = op.operators_id
   and base.attribute1 = st1.static_code(+)
   and base.attribute2 = sstatic_code(+)
   and base.channel_cust_id = cust.channel_cust_id;
end;


create or replace procedure PROC_USER_RECHARGE_MM
is
begin
  delete from lao_user_recharge_mm recharge_mm
   where recharge_mm.in_date = to_number(to_char(sysdate - 1, 'YYYYMM'));
  insert into lao_user_recharge_mm
    select to_number(to_char(sysdate - 1, 'YYYYMM')),
           recharge_dd.channel_cust_id,
           recharge_dd.channel_cust_name,
           recharge_dd.cust_id,
           recharge_dd.cust_name,
           recharge_dd.staff_id,
           recharge_dd.staff_name,
           recharge_dd.region_id,
           recharge_dd.region_name,
           recharge_dd.operators_id,
           recharge_dd.operators_name,
           recharge_dd.value1,
           recharge_dd.value1_name,
           recharge_dd.value2,
           recharge_dd.value2_name,
           recharge_dd.goods_id,
           recharge_dd.goods_name,
           sum(recharge_dd.goods_flow) allflow,
           recharge_dd.pay_type,
           recharge_dd.unit,
           sum(recharge_dd.sumfee) sumfee,
           sysdate update_time,
           '' remark
      from lao_user_recharge_dd recharge_dd
      where recharge_dd.in_date>to_number(to_char(sysdate-1, 'YYYYMM')||'00')
     group by recharge_dd.channel_cust_id,
              recharge_dd.channel_cust_name,
              recharge_dd.cust_id,
              recharge_dd.cust_name,
              recharge_dd.staff_id,
              recharge_dd.staff_name,
              recharge_dd.region_id,
              recharge_dd.region_name,
              recharge_dd.operators_id,
              recharge_dd.operators_name,
              recharge_dd.value1,
              recharge_dd.value1_name,
              recharge_dd.value2,
              recharge_dd.value2_name,
              recharge_dd.goods_id,
              recharge_dd.goods_name,
               recharge_dd.pay_type,
             recharge_dd.unit;
end;


create or replace procedure PROC_USER_STATE_DD
is
begin
insert into lao_user_state_dd
 select to_number(to_char(sysdate - 1, 'YYYYMMDD')),
       cust.channel_cust_id,
       cust.channel_cust_name,
       null cust_id,
       '' cust_name,
       cust.staff_id,
       cust.staff_name,
       cust.region_id,
       cust.region_name,
       op.operators_id,
       op.operators_name,
       base.attribute1,
       st1.static_name,
       base.attribute2,
       sstatic_name,
       base.outsidestate,
       base.static_name,
       '' service_name,
       base.flow,
       base.num,
       sysdate update_time,
       '' remark
  from (select ur.channel_cust_id,
               ur.operators_id,
               ur.attribute1,
               ur.attribute2,
               status.outsidestate,
               st1.static_name,
               nvl(sum(decode(p.quantity_unit,
                      'M',
                      p.quantity_max * 1024,
                      'G',
                      p.quantity_max * 1024 * 1024)),
           0) as flow,
               count(1) num
          from lao_user ur,
               lao_service_status status,
               (select st.static_code, st.static_name
                  from lao_ss_static st
                 where st.tab_name = 'LAO_SERVICE_STATUS'
                   and st.col_name = 'OUTSIDESTATE') st1,
         lao_user_operator_plan lp,
               lao_operator_plan p
         where ur.state_code = status.state_code
           and status.outsidestate = st1.static_code(+)
     and ur.user_id = lp.user_id(+)
     and lp.plan_id = p.plan_id
         group by ur.channel_cust_id,
                  ur.operators_id,
                  ur.attribute1,
                  ur.attribute2,
                  status.outsidestate,
                  st1.static_name) base,
       (select ct.cust_id channel_cust_id,
               ct.cust_name channel_cust_name,
               staff.staff_id,
               staff.staff_name,
               staff.region_id,
               staff.region_name
          from lao_customer ct, lao_staff staff
         where ct.cust_type = '1'
           and ct.rsrv_str1 = staff.staff_id(+)) cust,
       lao_operators op,
       (select st.static_code, st.static_name
          from lao_ss_static st
         where st.tab_name = 'LAO_USER') st1,
       (select st.static_code, st.static_name
          from lao_ss_static st
         where st.tab_name = 'LAO_USER') st2
 where base.channel_cust_id = cust.channel_cust_id
   and base.operators_id = op.operators_id
   and base.attribute1 = st1.static_code(+)
   and base.attribute2 = sstatic_code(+);
end;


create or replace procedure PROC_USER_STATE_MM
is
begin
  delete from lao_user_state_mm state_mm
   where state_mm.in_date = to_number(to_char(sysdate - 1, 'YYYYMM'));
  insert into lao_user_state_mm
   select to_number(to_char(sysdate - 1, 'YYYYMM')),
       cust.channel_cust_id,
       cust.channel_cust_name,
       null cust_id,
       '' cust_name,
       cust.staff_id,
       cust.staff_name,
       cust.region_id,
       cust.region_name,
       op.operators_id,
       op.operators_name,
       base.attribute1,
       st1.static_name,
       base.attribute2,
       sstatic_name,
       base.outsidestate,
       base.static_name,
       '' service_name,
       base.flow,
       base.num,
       sysdate update_time,
       '' remark
  from (select ur.channel_cust_id,
               ur.operators_id,
               ur.attribute1,
               ur.attribute2,
               status.outsidestate,
               st1.static_name,
               nvl(sum(decode(p.quantity_unit,
                      'M',
                      p.quantity_max * 1024,
                      'G',
                      p.quantity_max * 1024 * 1024)),
           0) as flow,
               count(1) num
          from lao_user ur,
               lao_service_status status,
               (select st.static_code, st.static_name
                  from lao_ss_static st
                 where st.tab_name = 'LAO_SERVICE_STATUS'
                   and st.col_name = 'OUTSIDESTATE') st1,
         lao_user_operator_plan lp,
               lao_operator_plan p
         where ur.state_code = status.state_code
           and status.outsidestate = st1.static_code(+)
     and ur.user_id = lp.user_id(+)
     and lp.plan_id = p.plan_id
         group by ur.channel_cust_id,
                  ur.operators_id,
                  ur.attribute1,
                  ur.attribute2,
                  status.outsidestate,
                  st1.static_name) base,
       (select ct.cust_id channel_cust_id,
               ct.cust_name channel_cust_name,
               staff.staff_id,
               staff.staff_name,
               staff.region_id,
               staff.region_name
          from lao_customer ct, lao_staff staff
         where ct.cust_type = '1'
           and ct.rsrv_str1 = staff.staff_id(+)) cust,
       lao_operators op,
       (select st.static_code, st.static_name
          from lao_ss_static st
         where st.tab_name = 'LAO_USER') st1,
       (select st.static_code, st.static_name
          from lao_ss_static st
         where st.tab_name = 'LAO_USER') st2
 where base.channel_cust_id = cust.channel_cust_id
   and base.operators_id = op.operators_id
   and base.attribute1 = st1.static_code(+)
   and base.attribute2 = sstatic_code(+);
end;


create or replace procedure PRO_4(num in out number)
IS
a number := 100;
Begin
  num := a*num;
End;


create or replace procedure PRO_5(num in out number)
IS
a number := 100;
Begin
  num := a*num;
End;


create or replace procedure TEST(b in out varchar2)
as
begin
  b := 'nihao'||b;
end;


create or replace procedure TEST_A(a_id in long,a_title out varchar2)
as
begin
  select notice_title into a_title from lao_customer_notice  where NOTICEID=a_id;
  DBMS_OUTPUT.put_line('name :'||a_title);
  end;

