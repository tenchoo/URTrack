package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoUserRes implements Serializable {
    private Long userId;

    private String resTypeCode;

    private String resCode;

    private Date startDate;

    private Short partitionId;

    private String resInfo1;

    private String resInfo2;

    private String resInfo3;

    private String resInfo4;

    private String resInfo5;

    private String resInfo6;

    private String resInfo7;

    private String resInfo8;

    private Date endDate;

    private static final long serialVersionUID = 1L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getResTypeCode() {
        return resTypeCode;
    }

    public void setResTypeCode(String resTypeCode) {
        this.resTypeCode = resTypeCode == null ? null : resTypeCode.trim();
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode == null ? null : resCode.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Short getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(Short partitionId) {
        this.partitionId = partitionId;
    }

    public String getResInfo1() {
        return resInfo1;
    }

    public void setResInfo1(String resInfo1) {
        this.resInfo1 = resInfo1 == null ? null : resInfo1.trim();
    }

    public String getResInfo2() {
        return resInfo2;
    }

    public void setResInfo2(String resInfo2) {
        this.resInfo2 = resInfo2 == null ? null : resInfo2.trim();
    }

    public String getResInfo3() {
        return resInfo3;
    }

    public void setResInfo3(String resInfo3) {
        this.resInfo3 = resInfo3 == null ? null : resInfo3.trim();
    }

    public String getResInfo4() {
        return resInfo4;
    }

    public void setResInfo4(String resInfo4) {
        this.resInfo4 = resInfo4 == null ? null : resInfo4.trim();
    }

    public String getResInfo5() {
        return resInfo5;
    }

    public void setResInfo5(String resInfo5) {
        this.resInfo5 = resInfo5 == null ? null : resInfo5.trim();
    }

    public String getResInfo6() {
        return resInfo6;
    }

    public void setResInfo6(String resInfo6) {
        this.resInfo6 = resInfo6 == null ? null : resInfo6.trim();
    }

    public String getResInfo7() {
        return resInfo7;
    }

    public void setResInfo7(String resInfo7) {
        this.resInfo7 = resInfo7 == null ? null : resInfo7.trim();
    }

    public String getResInfo8() {
        return resInfo8;
    }

    public void setResInfo8(String resInfo8) {
        this.resInfo8 = resInfo8 == null ? null : resInfo8.trim();
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}