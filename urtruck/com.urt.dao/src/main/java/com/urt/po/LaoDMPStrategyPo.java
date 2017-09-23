package com.urt.po;

import java.io.Serializable;
import java.util.Date;

public class LaoDMPStrategyPo implements Serializable {
    private Long id;

    private String strategyName;

    private String stategyComment;

    private String strategyimpl;

    private String delflag;

    private Date createtime;

    private Date updatetime;

    private String parameter1;

    private String parameter2;

    private String parameter3;

    private String parameter4;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName == null ? null : strategyName.trim();
    }

    public String getStategyComment() {
        return stategyComment;
    }

    public void setStategyComment(String stategyComment) {
        this.stategyComment = stategyComment == null ? null : stategyComment.trim();
    }

    public String getStrategyimpl() {
        return strategyimpl;
    }

    public void setStrategyimpl(String strategyimpl) {
        this.strategyimpl = strategyimpl == null ? null : strategyimpl.trim();
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getParameter1() {
        return parameter1;
    }

    public void setParameter1(String parameter1) {
        this.parameter1 = parameter1 == null ? null : parameter1.trim();
    }

    public String getParameter2() {
        return parameter2;
    }

    public void setParameter2(String parameter2) {
        this.parameter2 = parameter2 == null ? null : parameter2.trim();
    }

    public String getParameter3() {
        return parameter3;
    }

    public void setParameter3(String parameter3) {
        this.parameter3 = parameter3 == null ? null : parameter3.trim();
    }

    public String getParameter4() {
        return parameter4;
    }

    public void setParameter4(String parameter4) {
        this.parameter4 = parameter4 == null ? null : parameter4.trim();
    }
}