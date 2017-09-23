package com.urt.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserLinkedTariffPlan implements Serializable {
    private String id;

    private String userid;

    private String username;

    private BigDecimal linktariffplanid;

    private int orderstatus;

    private int usedstatus;

    private String iccid;

    private String tariffplanname;

    private Date tariffplanexpdate;

    private BigDecimal tariffplanlength;

    private Short tariffplanstauts;

    private Date createdate;

    private Date modifydate;

    private BigDecimal tariffplancapacity;

    private static final long serialVersionUID = 1L;
    
    public static enum OrderStatus {
		INIT(0, "初始化"),
		BUYED_SUCCESS(1, "订购成功"),
		BUYED_FAIL(2, "订购失败");
		
		private final int code;
		private final String message;
		
		private OrderStatus (int code, String message) {
			this.code = code;
			this.message = message;
		}
		public int getCodeValue() {
			return code;
		}
		private String getMessage() {
			return message;
		}
	}
    public static enum UsedStatus {
		AVAILABLE(1, "可用的"),
		IS_END(0, "已经用完");
		
		private final int code;
		private final String message;
		
		private UsedStatus (int code, String message) {
			this.code = code;
			this.message = message;
		}
		public int getCodeValue() {
			return code;
		}
		private String getMessage() {
			return message;
		}
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public BigDecimal getLinktariffplanid() {
        return linktariffplanid;
    }

    public void setLinktariffplanid(BigDecimal linktariffplanid) {
        this.linktariffplanid = linktariffplanid;
    }

    public int getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(int orderstatus) {
		this.orderstatus = orderstatus;
	}

	public int getUsedstatus() {
		return usedstatus;
	}

	public void setUsedstatus(int usedstatus) {
		this.usedstatus = usedstatus;
	}

	public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getTariffplanname() {
        return tariffplanname;
    }

    public void setTariffplanname(String tariffplanname) {
        this.tariffplanname = tariffplanname == null ? null : tariffplanname.trim();
    }

    public Date getTariffplanexpdate() {
        return tariffplanexpdate;
    }

    public void setTariffplanexpdate(Date tariffplanexpdate) {
        this.tariffplanexpdate = tariffplanexpdate;
    }

    public BigDecimal getTariffplanlength() {
        return tariffplanlength;
    }

    public void setTariffplanlength(BigDecimal tariffplanlength) {
        this.tariffplanlength = tariffplanlength;
    }

    public Short getTariffplanstauts() {
        return tariffplanstauts;
    }

    public void setTariffplanstauts(Short tariffplanstauts) {
        this.tariffplanstauts = tariffplanstauts;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    public BigDecimal getTariffplancapacity() {
        return tariffplancapacity;
    }

    public void setTariffplancapacity(BigDecimal tariffplancapacity) {
        this.tariffplancapacity = tariffplancapacity;
    }
}