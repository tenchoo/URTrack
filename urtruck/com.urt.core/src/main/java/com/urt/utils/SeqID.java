package com.urt.utils;

/**
* 类说明：
* @author Administrator
* @date 2016年5月26日 下午8:36:14
*/
public enum SeqID {
	BATCHID("BATCHID","401"),
	USER_ID("USER_ID", "100"), 
	TRADE_ID("TRADE_ID", "101"), 
	ACCT_ID("ACCT_ID", "102"),
	CUST_ID("CUST_ID", "103"),
	CONTACT_ID("CONTACT_ID", "104"),
	BILLID_ID("BILLID_ID", "105"),
	SUBSCRIBE_ID("SUBSCRIBE_ID", "106"),
	SERIAL_NUM("SERIALNUM","107"),
	TF_F_RESOURCE_ID("TFFRESOURCE_ID","108"),
	RESOURCEINTERFACELOG_ID("RESOURCEINTERFACELOG_ID","109"),
    BILLINTERFACELOG_ID("BILLINTERFACELOG_ID","200"),
    PAYMENTINTERFACE_ID("tfFPaymentInterfacePo","201"),
    RESDISPLAYLOG_ID("RESDISPLAYLOG_ID","202"),
	RESDISPLAYLOG_BATHNUM("RESDISPLAYLOG_BATHNUM","203"),
    PAYLOG_ID("PAYLOG_ID","301"),
    ACCESSLOG_ID("ACCESSLOG_ID","302"),
    WRITEOFFLOG_ID("WRITEOFFLOG_ID","303"),
    WRITESNAPLOG_ID("WRITESNAPLOG_ID","304"),
    PAYMENTINTERFACELOG_ID("PAYMENTINTERFACELOG_ID","305"),
	ORG_ID("ORG_ID", "306"),
	SYSUSER_ID("SYSUSER_ID", "307"),
	ROLE_ID("ROLE_ID", "308"),
	ACCT_BALANCE_ID("ACCT_BALANCE_ID","309"),
	CAR_NUMBER("CAR_NUMBER","310"),
	GOODS_ID("GOODS_ID","311"),
	GOODSELEMENT_ID("GOODSELEMENT_ID","312"),
	GOODRELEASE_ID("GOODSELEMENT_ID","313"),
	BATCH_ID("BATCH_ID","314"),
	FLOWORDER_ID("FLOWORDER_ID","315"),
	ESIMDETAIL_ID("ESIMDETAIL_ID","316"),
	LENOVOIMEI_ID("LENOVOIMEI_ID","317"),
	FLOWINFO_ID("FLOWINFO_ID","318"),
	SEQUENCE_ID("SEQUENCE_ID","319"),
	DELIVER_ID("DELIVER_ID","320"),
	LENOVOGOODS_ID("LENOVOGOODS_ID","321"),
	DEVICEREL_ID("REL_ID","322"),
	GIVINFLOW_ID("REL_ID","323"),
	TASK_ID("TASK_ID","324"),
	VERIFIY_ID("ID","325"),
	REMOVE_ID("REMOVE_ID","326"),
	BILL_ID("BILL_ID","328"),
	BILL_DETAIL_ID("BILL_DETAIL_ID","329"),
	POSITIONINFO_ID("POSITIONINFO_ID","330"),
	DEVICEDATA_ID("DEVICEDATA_ID","331"),
	BILL_RESULT_ID("BILL_RESULT_ID","332"),
	LAODMPGROUP_ID("LAODMPGROUP_ID","333"),
	CARD_ID("CARD_ID","334"),
	CHARGE_ID("CHARGE_ID","335"),
	DMPLOG_ID("DMPLOG_ID","336"),
	DMPSCHEME_ID("DMPSCHEME_ID","336"),
	LOGIN_ID("LOGIN_ID","337"),
    BALALARM_ID("BalAlarm_id","338"),
	ALMRULERELATION_ID("ALMRULERELATION_ID","339"),
	PICTURE_ID("PICTURE_ID","340"),
    USER_HIS_ID("USER_HIS_ID","341");
	// 成员变量
	private String IdName;
	private String IdSeq;

	// 构造方法
	private SeqID(String IdName, String IdSeq) {
		this.IdName = IdName;
		this.IdSeq = IdSeq;
	}

	// 普通方法
	public static String getIdName(String IdSeq) {
		for (SeqID c : SeqID.values()) {
			if (c.getIdSeq().equals(IdSeq)) {
				return c.IdName;
			}
		}
		return null;
	}

	// 普通方法
	public static String getIdSeq(String IdName) {
		for (SeqID c : SeqID.values()) {
			if (c.getIdName().equals(IdName)) {
				return c.IdSeq;
			}
		}
		return null;
	}

	public String getIdName() {
		return IdName;
	}

	public void setIdName(String idName) {
		IdName = idName;
	}

	public String getIdSeq() {
		return IdSeq;
	}

	public void setIdSeq(String idSeq) {
		IdSeq = idSeq;
	}
	
}
