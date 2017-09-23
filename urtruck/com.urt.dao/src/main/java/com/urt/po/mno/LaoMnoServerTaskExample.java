package com.urt.po.mno;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class LaoMnoServerTaskExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LaoMnoServerTaskExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andTaskIdIsNull() {
            addCriterion("TASK_ID is null");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNotNull() {
            addCriterion("TASK_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTaskIdEqualTo(Long value) {
            addCriterion("TASK_ID =", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotEqualTo(Long value) {
            addCriterion("TASK_ID <>", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThan(Long value) {
            addCriterion("TASK_ID >", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThanOrEqualTo(Long value) {
            addCriterion("TASK_ID >=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThan(Long value) {
            addCriterion("TASK_ID <", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThanOrEqualTo(Long value) {
            addCriterion("TASK_ID <=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdIn(List<Long> values) {
            addCriterion("TASK_ID in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotIn(List<Long> values) {
            addCriterion("TASK_ID not in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdBetween(Long value1, Long value2) {
            addCriterion("TASK_ID between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotBetween(Long value1, Long value2) {
            addCriterion("TASK_ID not between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andSystemIdIsNull() {
            addCriterion("SYSTEM_ID is null");
            return (Criteria) this;
        }

        public Criteria andSystemIdIsNotNull() {
            addCriterion("SYSTEM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSystemIdEqualTo(String value) {
            addCriterion("SYSTEM_ID =", value, "systemId");
            return (Criteria) this;
        }

        public Criteria andSystemIdNotEqualTo(String value) {
            addCriterion("SYSTEM_ID <>", value, "systemId");
            return (Criteria) this;
        }

        public Criteria andSystemIdGreaterThan(String value) {
            addCriterion("SYSTEM_ID >", value, "systemId");
            return (Criteria) this;
        }

        public Criteria andSystemIdGreaterThanOrEqualTo(String value) {
            addCriterion("SYSTEM_ID >=", value, "systemId");
            return (Criteria) this;
        }

        public Criteria andSystemIdLessThan(String value) {
            addCriterion("SYSTEM_ID <", value, "systemId");
            return (Criteria) this;
        }

        public Criteria andSystemIdLessThanOrEqualTo(String value) {
            addCriterion("SYSTEM_ID <=", value, "systemId");
            return (Criteria) this;
        }

        public Criteria andSystemIdLike(String value) {
            addCriterion("SYSTEM_ID like", value, "systemId");
            return (Criteria) this;
        }

        public Criteria andSystemIdNotLike(String value) {
            addCriterion("SYSTEM_ID not like", value, "systemId");
            return (Criteria) this;
        }

        public Criteria andSystemIdIn(List<String> values) {
            addCriterion("SYSTEM_ID in", values, "systemId");
            return (Criteria) this;
        }

        public Criteria andSystemIdNotIn(List<String> values) {
            addCriterion("SYSTEM_ID not in", values, "systemId");
            return (Criteria) this;
        }

        public Criteria andSystemIdBetween(String value1, String value2) {
            addCriterion("SYSTEM_ID between", value1, value2, "systemId");
            return (Criteria) this;
        }

        public Criteria andSystemIdNotBetween(String value1, String value2) {
            addCriterion("SYSTEM_ID not between", value1, value2, "systemId");
            return (Criteria) this;
        }

        public Criteria andSrcRequestIdIsNull() {
            addCriterion("SRC_REQUEST_ID is null");
            return (Criteria) this;
        }

        public Criteria andSrcRequestIdIsNotNull() {
            addCriterion("SRC_REQUEST_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSrcRequestIdEqualTo(String value) {
            addCriterion("SRC_REQUEST_ID =", value, "srcRequestId");
            return (Criteria) this;
        }

        public Criteria andSrcRequestIdNotEqualTo(String value) {
            addCriterion("SRC_REQUEST_ID <>", value, "srcRequestId");
            return (Criteria) this;
        }

        public Criteria andSrcRequestIdGreaterThan(String value) {
            addCriterion("SRC_REQUEST_ID >", value, "srcRequestId");
            return (Criteria) this;
        }

        public Criteria andSrcRequestIdGreaterThanOrEqualTo(String value) {
            addCriterion("SRC_REQUEST_ID >=", value, "srcRequestId");
            return (Criteria) this;
        }

        public Criteria andSrcRequestIdLessThan(String value) {
            addCriterion("SRC_REQUEST_ID <", value, "srcRequestId");
            return (Criteria) this;
        }

        public Criteria andSrcRequestIdLessThanOrEqualTo(String value) {
            addCriterion("SRC_REQUEST_ID <=", value, "srcRequestId");
            return (Criteria) this;
        }

        public Criteria andSrcRequestIdLike(String value) {
            addCriterion("SRC_REQUEST_ID like", value, "srcRequestId");
            return (Criteria) this;
        }

        public Criteria andSrcRequestIdNotLike(String value) {
            addCriterion("SRC_REQUEST_ID not like", value, "srcRequestId");
            return (Criteria) this;
        }

        public Criteria andSrcRequestIdIn(List<String> values) {
            addCriterion("SRC_REQUEST_ID in", values, "srcRequestId");
            return (Criteria) this;
        }

        public Criteria andSrcRequestIdNotIn(List<String> values) {
            addCriterion("SRC_REQUEST_ID not in", values, "srcRequestId");
            return (Criteria) this;
        }

        public Criteria andSrcRequestIdBetween(String value1, String value2) {
            addCriterion("SRC_REQUEST_ID between", value1, value2, "srcRequestId");
            return (Criteria) this;
        }

        public Criteria andSrcRequestIdNotBetween(String value1, String value2) {
            addCriterion("SRC_REQUEST_ID not between", value1, value2, "srcRequestId");
            return (Criteria) this;
        }

        public Criteria andAccessLogIdIsNull() {
            addCriterion("ACCESS_LOG_ID is null");
            return (Criteria) this;
        }

        public Criteria andAccessLogIdIsNotNull() {
            addCriterion("ACCESS_LOG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAccessLogIdEqualTo(Long value) {
            addCriterion("ACCESS_LOG_ID =", value, "accessLogId");
            return (Criteria) this;
        }

        public Criteria andAccessLogIdNotEqualTo(Long value) {
            addCriterion("ACCESS_LOG_ID <>", value, "accessLogId");
            return (Criteria) this;
        }

        public Criteria andAccessLogIdGreaterThan(Long value) {
            addCriterion("ACCESS_LOG_ID >", value, "accessLogId");
            return (Criteria) this;
        }

        public Criteria andAccessLogIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ACCESS_LOG_ID >=", value, "accessLogId");
            return (Criteria) this;
        }

        public Criteria andAccessLogIdLessThan(Long value) {
            addCriterion("ACCESS_LOG_ID <", value, "accessLogId");
            return (Criteria) this;
        }

        public Criteria andAccessLogIdLessThanOrEqualTo(Long value) {
            addCriterion("ACCESS_LOG_ID <=", value, "accessLogId");
            return (Criteria) this;
        }

        public Criteria andAccessLogIdIn(List<Long> values) {
            addCriterion("ACCESS_LOG_ID in", values, "accessLogId");
            return (Criteria) this;
        }

        public Criteria andAccessLogIdNotIn(List<Long> values) {
            addCriterion("ACCESS_LOG_ID not in", values, "accessLogId");
            return (Criteria) this;
        }

        public Criteria andAccessLogIdBetween(Long value1, Long value2) {
            addCriterion("ACCESS_LOG_ID between", value1, value2, "accessLogId");
            return (Criteria) this;
        }

        public Criteria andAccessLogIdNotBetween(Long value1, Long value2) {
            addCriterion("ACCESS_LOG_ID not between", value1, value2, "accessLogId");
            return (Criteria) this;
        }

        public Criteria andComRequestIdIsNull() {
            addCriterion("COM_REQUEST_ID is null");
            return (Criteria) this;
        }

        public Criteria andComRequestIdIsNotNull() {
            addCriterion("COM_REQUEST_ID is not null");
            return (Criteria) this;
        }

        public Criteria andComRequestIdEqualTo(String value) {
            addCriterion("COM_REQUEST_ID =", value, "comRequestId");
            return (Criteria) this;
        }

        public Criteria andComRequestIdNotEqualTo(String value) {
            addCriterion("COM_REQUEST_ID <>", value, "comRequestId");
            return (Criteria) this;
        }

        public Criteria andComRequestIdGreaterThan(String value) {
            addCriterion("COM_REQUEST_ID >", value, "comRequestId");
            return (Criteria) this;
        }

        public Criteria andComRequestIdGreaterThanOrEqualTo(String value) {
            addCriterion("COM_REQUEST_ID >=", value, "comRequestId");
            return (Criteria) this;
        }

        public Criteria andComRequestIdLessThan(String value) {
            addCriterion("COM_REQUEST_ID <", value, "comRequestId");
            return (Criteria) this;
        }

        public Criteria andComRequestIdLessThanOrEqualTo(String value) {
            addCriterion("COM_REQUEST_ID <=", value, "comRequestId");
            return (Criteria) this;
        }

        public Criteria andComRequestIdLike(String value) {
            addCriterion("COM_REQUEST_ID like", value, "comRequestId");
            return (Criteria) this;
        }

        public Criteria andComRequestIdNotLike(String value) {
            addCriterion("COM_REQUEST_ID not like", value, "comRequestId");
            return (Criteria) this;
        }

        public Criteria andComRequestIdIn(List<String> values) {
            addCriterion("COM_REQUEST_ID in", values, "comRequestId");
            return (Criteria) this;
        }

        public Criteria andComRequestIdNotIn(List<String> values) {
            addCriterion("COM_REQUEST_ID not in", values, "comRequestId");
            return (Criteria) this;
        }

        public Criteria andComRequestIdBetween(String value1, String value2) {
            addCriterion("COM_REQUEST_ID between", value1, value2, "comRequestId");
            return (Criteria) this;
        }

        public Criteria andComRequestIdNotBetween(String value1, String value2) {
            addCriterion("COM_REQUEST_ID not between", value1, value2, "comRequestId");
            return (Criteria) this;
        }

        public Criteria andComServerNameIsNull() {
            addCriterion("COM_SERVER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andComServerNameIsNotNull() {
            addCriterion("COM_SERVER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andComServerNameEqualTo(String value) {
            addCriterion("COM_SERVER_NAME =", value, "comServerName");
            return (Criteria) this;
        }

        public Criteria andComServerNameNotEqualTo(String value) {
            addCriterion("COM_SERVER_NAME <>", value, "comServerName");
            return (Criteria) this;
        }

        public Criteria andComServerNameGreaterThan(String value) {
            addCriterion("COM_SERVER_NAME >", value, "comServerName");
            return (Criteria) this;
        }

        public Criteria andComServerNameGreaterThanOrEqualTo(String value) {
            addCriterion("COM_SERVER_NAME >=", value, "comServerName");
            return (Criteria) this;
        }

        public Criteria andComServerNameLessThan(String value) {
            addCriterion("COM_SERVER_NAME <", value, "comServerName");
            return (Criteria) this;
        }

        public Criteria andComServerNameLessThanOrEqualTo(String value) {
            addCriterion("COM_SERVER_NAME <=", value, "comServerName");
            return (Criteria) this;
        }

        public Criteria andComServerNameLike(String value) {
            addCriterion("COM_SERVER_NAME like", value, "comServerName");
            return (Criteria) this;
        }

        public Criteria andComServerNameNotLike(String value) {
            addCriterion("COM_SERVER_NAME not like", value, "comServerName");
            return (Criteria) this;
        }

        public Criteria andComServerNameIn(List<String> values) {
            addCriterion("COM_SERVER_NAME in", values, "comServerName");
            return (Criteria) this;
        }

        public Criteria andComServerNameNotIn(List<String> values) {
            addCriterion("COM_SERVER_NAME not in", values, "comServerName");
            return (Criteria) this;
        }

        public Criteria andComServerNameBetween(String value1, String value2) {
            addCriterion("COM_SERVER_NAME between", value1, value2, "comServerName");
            return (Criteria) this;
        }

        public Criteria andComServerNameNotBetween(String value1, String value2) {
            addCriterion("COM_SERVER_NAME not between", value1, value2, "comServerName");
            return (Criteria) this;
        }

        public Criteria andServerNameIsNull() {
            addCriterion("SERVER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andServerNameIsNotNull() {
            addCriterion("SERVER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andServerNameEqualTo(String value) {
            addCriterion("SERVER_NAME =", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameNotEqualTo(String value) {
            addCriterion("SERVER_NAME <>", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameGreaterThan(String value) {
            addCriterion("SERVER_NAME >", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameGreaterThanOrEqualTo(String value) {
            addCriterion("SERVER_NAME >=", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameLessThan(String value) {
            addCriterion("SERVER_NAME <", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameLessThanOrEqualTo(String value) {
            addCriterion("SERVER_NAME <=", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameLike(String value) {
            addCriterion("SERVER_NAME like", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameNotLike(String value) {
            addCriterion("SERVER_NAME not like", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameIn(List<String> values) {
            addCriterion("SERVER_NAME in", values, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameNotIn(List<String> values) {
            addCriterion("SERVER_NAME not in", values, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameBetween(String value1, String value2) {
            addCriterion("SERVER_NAME between", value1, value2, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameNotBetween(String value1, String value2) {
            addCriterion("SERVER_NAME not between", value1, value2, "serverName");
            return (Criteria) this;
        }

        public Criteria andTaskStatusIsNull() {
            addCriterion("TASK_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andTaskStatusIsNotNull() {
            addCriterion("TASK_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andTaskStatusEqualTo(String value) {
            addCriterion("TASK_STATUS =", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusNotEqualTo(String value) {
            addCriterion("TASK_STATUS <>", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusGreaterThan(String value) {
            addCriterion("TASK_STATUS >", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusGreaterThanOrEqualTo(String value) {
            addCriterion("TASK_STATUS >=", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusLessThan(String value) {
            addCriterion("TASK_STATUS <", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusLessThanOrEqualTo(String value) {
            addCriterion("TASK_STATUS <=", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusLike(String value) {
            addCriterion("TASK_STATUS like", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusNotLike(String value) {
            addCriterion("TASK_STATUS not like", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusIn(List<String> values) {
            addCriterion("TASK_STATUS in", values, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusNotIn(List<String> values) {
            addCriterion("TASK_STATUS not in", values, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusBetween(String value1, String value2) {
            addCriterion("TASK_STATUS between", value1, value2, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusNotBetween(String value1, String value2) {
            addCriterion("TASK_STATUS not between", value1, value2, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andRespCodeIsNull() {
            addCriterion("RESP_CODE is null");
            return (Criteria) this;
        }

        public Criteria andRespCodeIsNotNull() {
            addCriterion("RESP_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andRespCodeEqualTo(String value) {
            addCriterion("RESP_CODE =", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeNotEqualTo(String value) {
            addCriterion("RESP_CODE <>", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeGreaterThan(String value) {
            addCriterion("RESP_CODE >", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeGreaterThanOrEqualTo(String value) {
            addCriterion("RESP_CODE >=", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeLessThan(String value) {
            addCriterion("RESP_CODE <", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeLessThanOrEqualTo(String value) {
            addCriterion("RESP_CODE <=", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeLike(String value) {
            addCriterion("RESP_CODE like", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeNotLike(String value) {
            addCriterion("RESP_CODE not like", value, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeIn(List<String> values) {
            addCriterion("RESP_CODE in", values, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeNotIn(List<String> values) {
            addCriterion("RESP_CODE not in", values, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeBetween(String value1, String value2) {
            addCriterion("RESP_CODE between", value1, value2, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespCodeNotBetween(String value1, String value2) {
            addCriterion("RESP_CODE not between", value1, value2, "respCode");
            return (Criteria) this;
        }

        public Criteria andRespDescIsNull() {
            addCriterion("RESP_DESC is null");
            return (Criteria) this;
        }

        public Criteria andRespDescIsNotNull() {
            addCriterion("RESP_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andRespDescEqualTo(String value) {
            addCriterion("RESP_DESC =", value, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescNotEqualTo(String value) {
            addCriterion("RESP_DESC <>", value, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescGreaterThan(String value) {
            addCriterion("RESP_DESC >", value, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescGreaterThanOrEqualTo(String value) {
            addCriterion("RESP_DESC >=", value, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescLessThan(String value) {
            addCriterion("RESP_DESC <", value, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescLessThanOrEqualTo(String value) {
            addCriterion("RESP_DESC <=", value, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescLike(String value) {
            addCriterion("RESP_DESC like", value, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescNotLike(String value) {
            addCriterion("RESP_DESC not like", value, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescIn(List<String> values) {
            addCriterion("RESP_DESC in", values, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescNotIn(List<String> values) {
            addCriterion("RESP_DESC not in", values, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescBetween(String value1, String value2) {
            addCriterion("RESP_DESC between", value1, value2, "respDesc");
            return (Criteria) this;
        }

        public Criteria andRespDescNotBetween(String value1, String value2) {
            addCriterion("RESP_DESC not between", value1, value2, "respDesc");
            return (Criteria) this;
        }

        public Criteria andIccidIsNull() {
            addCriterion("ICCID is null");
            return (Criteria) this;
        }

        public Criteria andIccidIsNotNull() {
            addCriterion("ICCID is not null");
            return (Criteria) this;
        }

        public Criteria andIccidEqualTo(String value) {
            addCriterion("ICCID =", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidNotEqualTo(String value) {
            addCriterion("ICCID <>", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidGreaterThan(String value) {
            addCriterion("ICCID >", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidGreaterThanOrEqualTo(String value) {
            addCriterion("ICCID >=", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidLessThan(String value) {
            addCriterion("ICCID <", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidLessThanOrEqualTo(String value) {
            addCriterion("ICCID <=", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidLike(String value) {
            addCriterion("ICCID like", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidNotLike(String value) {
            addCriterion("ICCID not like", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidIn(List<String> values) {
            addCriterion("ICCID in", values, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidNotIn(List<String> values) {
            addCriterion("ICCID not in", values, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidBetween(String value1, String value2) {
            addCriterion("ICCID between", value1, value2, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidNotBetween(String value1, String value2) {
            addCriterion("ICCID not between", value1, value2, "iccid");
            return (Criteria) this;
        }

        public Criteria andMsisdnIsNull() {
            addCriterion("MSISDN is null");
            return (Criteria) this;
        }

        public Criteria andMsisdnIsNotNull() {
            addCriterion("MSISDN is not null");
            return (Criteria) this;
        }

        public Criteria andMsisdnEqualTo(String value) {
            addCriterion("MSISDN =", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnNotEqualTo(String value) {
            addCriterion("MSISDN <>", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnGreaterThan(String value) {
            addCriterion("MSISDN >", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnGreaterThanOrEqualTo(String value) {
            addCriterion("MSISDN >=", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnLessThan(String value) {
            addCriterion("MSISDN <", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnLessThanOrEqualTo(String value) {
            addCriterion("MSISDN <=", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnLike(String value) {
            addCriterion("MSISDN like", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnNotLike(String value) {
            addCriterion("MSISDN not like", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnIn(List<String> values) {
            addCriterion("MSISDN in", values, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnNotIn(List<String> values) {
            addCriterion("MSISDN not in", values, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnBetween(String value1, String value2) {
            addCriterion("MSISDN between", value1, value2, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnNotBetween(String value1, String value2) {
            addCriterion("MSISDN not between", value1, value2, "msisdn");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesIsNull() {
            addCriterion("REPEAT_TIMES is null");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesIsNotNull() {
            addCriterion("REPEAT_TIMES is not null");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesEqualTo(Short value) {
            addCriterion("REPEAT_TIMES =", value, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesNotEqualTo(Short value) {
            addCriterion("REPEAT_TIMES <>", value, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesGreaterThan(Short value) {
            addCriterion("REPEAT_TIMES >", value, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesGreaterThanOrEqualTo(Short value) {
            addCriterion("REPEAT_TIMES >=", value, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesLessThan(Short value) {
            addCriterion("REPEAT_TIMES <", value, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesLessThanOrEqualTo(Short value) {
            addCriterion("REPEAT_TIMES <=", value, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesIn(List<Short> values) {
            addCriterion("REPEAT_TIMES in", values, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesNotIn(List<Short> values) {
            addCriterion("REPEAT_TIMES not in", values, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesBetween(Short value1, Short value2) {
            addCriterion("REPEAT_TIMES between", value1, value2, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andRepeatTimesNotBetween(Short value1, Short value2) {
            addCriterion("REPEAT_TIMES not between", value1, value2, "repeatTimes");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelIsNull() {
            addCriterion("PRIORITY_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelIsNotNull() {
            addCriterion("PRIORITY_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelEqualTo(Short value) {
            addCriterion("PRIORITY_LEVEL =", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelNotEqualTo(Short value) {
            addCriterion("PRIORITY_LEVEL <>", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelGreaterThan(Short value) {
            addCriterion("PRIORITY_LEVEL >", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelGreaterThanOrEqualTo(Short value) {
            addCriterion("PRIORITY_LEVEL >=", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelLessThan(Short value) {
            addCriterion("PRIORITY_LEVEL <", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelLessThanOrEqualTo(Short value) {
            addCriterion("PRIORITY_LEVEL <=", value, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelIn(List<Short> values) {
            addCriterion("PRIORITY_LEVEL in", values, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelNotIn(List<Short> values) {
            addCriterion("PRIORITY_LEVEL not in", values, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelBetween(Short value1, Short value2) {
            addCriterion("PRIORITY_LEVEL between", value1, value2, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andPriorityLevelNotBetween(Short value1, Short value2) {
            addCriterion("PRIORITY_LEVEL not between", value1, value2, "priorityLevel");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("CREATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("CREATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterionForJDBCDate("CREATE_DATE =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("CREATE_DATE <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterionForJDBCDate("CREATE_DATE >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("CREATE_DATE >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterionForJDBCDate("CREATE_DATE <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("CREATE_DATE <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterionForJDBCDate("CREATE_DATE in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("CREATE_DATE not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CREATE_DATE between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CREATE_DATE not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("UPDATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("UPDATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterionForJDBCDate("UPDATE_DATE =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("UPDATE_DATE <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterionForJDBCDate("UPDATE_DATE >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("UPDATE_DATE >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterionForJDBCDate("UPDATE_DATE <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("UPDATE_DATE <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterionForJDBCDate("UPDATE_DATE in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("UPDATE_DATE not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("UPDATE_DATE between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("UPDATE_DATE not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andParaName1IsNull() {
            addCriterion("PARA_NAME1 is null");
            return (Criteria) this;
        }

        public Criteria andParaName1IsNotNull() {
            addCriterion("PARA_NAME1 is not null");
            return (Criteria) this;
        }

        public Criteria andParaName1EqualTo(String value) {
            addCriterion("PARA_NAME1 =", value, "paraName1");
            return (Criteria) this;
        }

        public Criteria andParaName1NotEqualTo(String value) {
            addCriterion("PARA_NAME1 <>", value, "paraName1");
            return (Criteria) this;
        }

        public Criteria andParaName1GreaterThan(String value) {
            addCriterion("PARA_NAME1 >", value, "paraName1");
            return (Criteria) this;
        }

        public Criteria andParaName1GreaterThanOrEqualTo(String value) {
            addCriterion("PARA_NAME1 >=", value, "paraName1");
            return (Criteria) this;
        }

        public Criteria andParaName1LessThan(String value) {
            addCriterion("PARA_NAME1 <", value, "paraName1");
            return (Criteria) this;
        }

        public Criteria andParaName1LessThanOrEqualTo(String value) {
            addCriterion("PARA_NAME1 <=", value, "paraName1");
            return (Criteria) this;
        }

        public Criteria andParaName1Like(String value) {
            addCriterion("PARA_NAME1 like", value, "paraName1");
            return (Criteria) this;
        }

        public Criteria andParaName1NotLike(String value) {
            addCriterion("PARA_NAME1 not like", value, "paraName1");
            return (Criteria) this;
        }

        public Criteria andParaName1In(List<String> values) {
            addCriterion("PARA_NAME1 in", values, "paraName1");
            return (Criteria) this;
        }

        public Criteria andParaName1NotIn(List<String> values) {
            addCriterion("PARA_NAME1 not in", values, "paraName1");
            return (Criteria) this;
        }

        public Criteria andParaName1Between(String value1, String value2) {
            addCriterion("PARA_NAME1 between", value1, value2, "paraName1");
            return (Criteria) this;
        }

        public Criteria andParaName1NotBetween(String value1, String value2) {
            addCriterion("PARA_NAME1 not between", value1, value2, "paraName1");
            return (Criteria) this;
        }

        public Criteria andParaName2IsNull() {
            addCriterion("PARA_NAME2 is null");
            return (Criteria) this;
        }

        public Criteria andParaName2IsNotNull() {
            addCriterion("PARA_NAME2 is not null");
            return (Criteria) this;
        }

        public Criteria andParaName2EqualTo(String value) {
            addCriterion("PARA_NAME2 =", value, "paraName2");
            return (Criteria) this;
        }

        public Criteria andParaName2NotEqualTo(String value) {
            addCriterion("PARA_NAME2 <>", value, "paraName2");
            return (Criteria) this;
        }

        public Criteria andParaName2GreaterThan(String value) {
            addCriterion("PARA_NAME2 >", value, "paraName2");
            return (Criteria) this;
        }

        public Criteria andParaName2GreaterThanOrEqualTo(String value) {
            addCriterion("PARA_NAME2 >=", value, "paraName2");
            return (Criteria) this;
        }

        public Criteria andParaName2LessThan(String value) {
            addCriterion("PARA_NAME2 <", value, "paraName2");
            return (Criteria) this;
        }

        public Criteria andParaName2LessThanOrEqualTo(String value) {
            addCriterion("PARA_NAME2 <=", value, "paraName2");
            return (Criteria) this;
        }

        public Criteria andParaName2Like(String value) {
            addCriterion("PARA_NAME2 like", value, "paraName2");
            return (Criteria) this;
        }

        public Criteria andParaName2NotLike(String value) {
            addCriterion("PARA_NAME2 not like", value, "paraName2");
            return (Criteria) this;
        }

        public Criteria andParaName2In(List<String> values) {
            addCriterion("PARA_NAME2 in", values, "paraName2");
            return (Criteria) this;
        }

        public Criteria andParaName2NotIn(List<String> values) {
            addCriterion("PARA_NAME2 not in", values, "paraName2");
            return (Criteria) this;
        }

        public Criteria andParaName2Between(String value1, String value2) {
            addCriterion("PARA_NAME2 between", value1, value2, "paraName2");
            return (Criteria) this;
        }

        public Criteria andParaName2NotBetween(String value1, String value2) {
            addCriterion("PARA_NAME2 not between", value1, value2, "paraName2");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}