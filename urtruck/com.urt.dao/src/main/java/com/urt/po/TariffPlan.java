package com.urt.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TariffPlan implements Serializable {
    private BigDecimal id;

    private String tariffplanname;

    private Date tariffplanexpdate;

    private BigDecimal tariffplanlength;

    private Short tariffplanstauts;

    private Date createdate;

    private Date modifydate;

    private BigDecimal tariffplancapacity;

    private static final long serialVersionUID = 1L;
    
    public static enum TariffPlanStatus {
		NORMAL(1, "正常的"),
		INVALIDATE(2, "无效的");
		
		private final int code;
		private final String message;
		
		private TariffPlanStatus (int code, String message) {
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

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
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

	@Override
	public String toString() {
		return "TariffPlan [id=" + id + ", tariffplanname=" + tariffplanname
				+ ", tariffplanexpdate=" + tariffplanexpdate
				+ ", tariffplanlength=" + tariffplanlength
				+ ", tariffplanstauts=" + tariffplanstauts + ", createdate="
				+ createdate + ", modifydate=" + modifydate
				+ ", tariffplancapacity=" + tariffplancapacity + "]";
	}
    
}