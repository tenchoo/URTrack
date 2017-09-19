package com.urt.po.mno;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class LaoMnoProvideServerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LaoMnoProvideServerExample() {
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

        public Criteria andServerIdIsNull() {
            addCriterion("SERVER_ID is null");
            return (Criteria) this;
        }

        public Criteria andServerIdIsNotNull() {
            addCriterion("SERVER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andServerIdEqualTo(Long value) {
            addCriterion("SERVER_ID =", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdNotEqualTo(Long value) {
            addCriterion("SERVER_ID <>", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdGreaterThan(Long value) {
            addCriterion("SERVER_ID >", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdGreaterThanOrEqualTo(Long value) {
            addCriterion("SERVER_ID >=", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdLessThan(Long value) {
            addCriterion("SERVER_ID <", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdLessThanOrEqualTo(Long value) {
            addCriterion("SERVER_ID <=", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdIn(List<Long> values) {
            addCriterion("SERVER_ID in", values, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdNotIn(List<Long> values) {
            addCriterion("SERVER_ID not in", values, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdBetween(Long value1, Long value2) {
            addCriterion("SERVER_ID between", value1, value2, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdNotBetween(Long value1, Long value2) {
            addCriterion("SERVER_ID not between", value1, value2, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerCategoryIsNull() {
            addCriterion("SERVER_CATEGORY is null");
            return (Criteria) this;
        }

        public Criteria andServerCategoryIsNotNull() {
            addCriterion("SERVER_CATEGORY is not null");
            return (Criteria) this;
        }

        public Criteria andServerCategoryEqualTo(String value) {
            addCriterion("SERVER_CATEGORY =", value, "serverCategory");
            return (Criteria) this;
        }

        public Criteria andServerCategoryNotEqualTo(String value) {
            addCriterion("SERVER_CATEGORY <>", value, "serverCategory");
            return (Criteria) this;
        }

        public Criteria andServerCategoryGreaterThan(String value) {
            addCriterion("SERVER_CATEGORY >", value, "serverCategory");
            return (Criteria) this;
        }

        public Criteria andServerCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("SERVER_CATEGORY >=", value, "serverCategory");
            return (Criteria) this;
        }

        public Criteria andServerCategoryLessThan(String value) {
            addCriterion("SERVER_CATEGORY <", value, "serverCategory");
            return (Criteria) this;
        }

        public Criteria andServerCategoryLessThanOrEqualTo(String value) {
            addCriterion("SERVER_CATEGORY <=", value, "serverCategory");
            return (Criteria) this;
        }

        public Criteria andServerCategoryLike(String value) {
            addCriterion("SERVER_CATEGORY like", value, "serverCategory");
            return (Criteria) this;
        }

        public Criteria andServerCategoryNotLike(String value) {
            addCriterion("SERVER_CATEGORY not like", value, "serverCategory");
            return (Criteria) this;
        }

        public Criteria andServerCategoryIn(List<String> values) {
            addCriterion("SERVER_CATEGORY in", values, "serverCategory");
            return (Criteria) this;
        }

        public Criteria andServerCategoryNotIn(List<String> values) {
            addCriterion("SERVER_CATEGORY not in", values, "serverCategory");
            return (Criteria) this;
        }

        public Criteria andServerCategoryBetween(String value1, String value2) {
            addCriterion("SERVER_CATEGORY between", value1, value2, "serverCategory");
            return (Criteria) this;
        }

        public Criteria andServerCategoryNotBetween(String value1, String value2) {
            addCriterion("SERVER_CATEGORY not between", value1, value2, "serverCategory");
            return (Criteria) this;
        }

        public Criteria andServerProtocolIsNull() {
            addCriterion("SERVER_PROTOCOL is null");
            return (Criteria) this;
        }

        public Criteria andServerProtocolIsNotNull() {
            addCriterion("SERVER_PROTOCOL is not null");
            return (Criteria) this;
        }

        public Criteria andServerProtocolEqualTo(String value) {
            addCriterion("SERVER_PROTOCOL =", value, "serverProtocol");
            return (Criteria) this;
        }

        public Criteria andServerProtocolNotEqualTo(String value) {
            addCriterion("SERVER_PROTOCOL <>", value, "serverProtocol");
            return (Criteria) this;
        }

        public Criteria andServerProtocolGreaterThan(String value) {
            addCriterion("SERVER_PROTOCOL >", value, "serverProtocol");
            return (Criteria) this;
        }

        public Criteria andServerProtocolGreaterThanOrEqualTo(String value) {
            addCriterion("SERVER_PROTOCOL >=", value, "serverProtocol");
            return (Criteria) this;
        }

        public Criteria andServerProtocolLessThan(String value) {
            addCriterion("SERVER_PROTOCOL <", value, "serverProtocol");
            return (Criteria) this;
        }

        public Criteria andServerProtocolLessThanOrEqualTo(String value) {
            addCriterion("SERVER_PROTOCOL <=", value, "serverProtocol");
            return (Criteria) this;
        }

        public Criteria andServerProtocolLike(String value) {
            addCriterion("SERVER_PROTOCOL like", value, "serverProtocol");
            return (Criteria) this;
        }

        public Criteria andServerProtocolNotLike(String value) {
            addCriterion("SERVER_PROTOCOL not like", value, "serverProtocol");
            return (Criteria) this;
        }

        public Criteria andServerProtocolIn(List<String> values) {
            addCriterion("SERVER_PROTOCOL in", values, "serverProtocol");
            return (Criteria) this;
        }

        public Criteria andServerProtocolNotIn(List<String> values) {
            addCriterion("SERVER_PROTOCOL not in", values, "serverProtocol");
            return (Criteria) this;
        }

        public Criteria andServerProtocolBetween(String value1, String value2) {
            addCriterion("SERVER_PROTOCOL between", value1, value2, "serverProtocol");
            return (Criteria) this;
        }

        public Criteria andServerProtocolNotBetween(String value1, String value2) {
            addCriterion("SERVER_PROTOCOL not between", value1, value2, "serverProtocol");
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

        public Criteria andPushDateIsNull() {
            addCriterion("PUSH_DATE is null");
            return (Criteria) this;
        }

        public Criteria andPushDateIsNotNull() {
            addCriterion("PUSH_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andPushDateEqualTo(Date value) {
            addCriterionForJDBCDate("PUSH_DATE =", value, "pushDate");
            return (Criteria) this;
        }

        public Criteria andPushDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("PUSH_DATE <>", value, "pushDate");
            return (Criteria) this;
        }

        public Criteria andPushDateGreaterThan(Date value) {
            addCriterionForJDBCDate("PUSH_DATE >", value, "pushDate");
            return (Criteria) this;
        }

        public Criteria andPushDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("PUSH_DATE >=", value, "pushDate");
            return (Criteria) this;
        }

        public Criteria andPushDateLessThan(Date value) {
            addCriterionForJDBCDate("PUSH_DATE <", value, "pushDate");
            return (Criteria) this;
        }

        public Criteria andPushDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("PUSH_DATE <=", value, "pushDate");
            return (Criteria) this;
        }

        public Criteria andPushDateIn(List<Date> values) {
            addCriterionForJDBCDate("PUSH_DATE in", values, "pushDate");
            return (Criteria) this;
        }

        public Criteria andPushDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("PUSH_DATE not in", values, "pushDate");
            return (Criteria) this;
        }

        public Criteria andPushDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("PUSH_DATE between", value1, value2, "pushDate");
            return (Criteria) this;
        }

        public Criteria andPushDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("PUSH_DATE not between", value1, value2, "pushDate");
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

        public Criteria andServerOpenIsNull() {
            addCriterion("SERVER_OPEN is null");
            return (Criteria) this;
        }

        public Criteria andServerOpenIsNotNull() {
            addCriterion("SERVER_OPEN is not null");
            return (Criteria) this;
        }

        public Criteria andServerOpenEqualTo(String value) {
            addCriterion("SERVER_OPEN =", value, "serverOpen");
            return (Criteria) this;
        }

        public Criteria andServerOpenNotEqualTo(String value) {
            addCriterion("SERVER_OPEN <>", value, "serverOpen");
            return (Criteria) this;
        }

        public Criteria andServerOpenGreaterThan(String value) {
            addCriterion("SERVER_OPEN >", value, "serverOpen");
            return (Criteria) this;
        }

        public Criteria andServerOpenGreaterThanOrEqualTo(String value) {
            addCriterion("SERVER_OPEN >=", value, "serverOpen");
            return (Criteria) this;
        }

        public Criteria andServerOpenLessThan(String value) {
            addCriterion("SERVER_OPEN <", value, "serverOpen");
            return (Criteria) this;
        }

        public Criteria andServerOpenLessThanOrEqualTo(String value) {
            addCriterion("SERVER_OPEN <=", value, "serverOpen");
            return (Criteria) this;
        }

        public Criteria andServerOpenLike(String value) {
            addCriterion("SERVER_OPEN like", value, "serverOpen");
            return (Criteria) this;
        }

        public Criteria andServerOpenNotLike(String value) {
            addCriterion("SERVER_OPEN not like", value, "serverOpen");
            return (Criteria) this;
        }

        public Criteria andServerOpenIn(List<String> values) {
            addCriterion("SERVER_OPEN in", values, "serverOpen");
            return (Criteria) this;
        }

        public Criteria andServerOpenNotIn(List<String> values) {
            addCriterion("SERVER_OPEN not in", values, "serverOpen");
            return (Criteria) this;
        }

        public Criteria andServerOpenBetween(String value1, String value2) {
            addCriterion("SERVER_OPEN between", value1, value2, "serverOpen");
            return (Criteria) this;
        }

        public Criteria andServerOpenNotBetween(String value1, String value2) {
            addCriterion("SERVER_OPEN not between", value1, value2, "serverOpen");
            return (Criteria) this;
        }

        public Criteria andServerDescIsNull() {
            addCriterion("SERVER_DESC is null");
            return (Criteria) this;
        }

        public Criteria andServerDescIsNotNull() {
            addCriterion("SERVER_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andServerDescEqualTo(String value) {
            addCriterion("SERVER_DESC =", value, "serverDesc");
            return (Criteria) this;
        }

        public Criteria andServerDescNotEqualTo(String value) {
            addCriterion("SERVER_DESC <>", value, "serverDesc");
            return (Criteria) this;
        }

        public Criteria andServerDescGreaterThan(String value) {
            addCriterion("SERVER_DESC >", value, "serverDesc");
            return (Criteria) this;
        }

        public Criteria andServerDescGreaterThanOrEqualTo(String value) {
            addCriterion("SERVER_DESC >=", value, "serverDesc");
            return (Criteria) this;
        }

        public Criteria andServerDescLessThan(String value) {
            addCriterion("SERVER_DESC <", value, "serverDesc");
            return (Criteria) this;
        }

        public Criteria andServerDescLessThanOrEqualTo(String value) {
            addCriterion("SERVER_DESC <=", value, "serverDesc");
            return (Criteria) this;
        }

        public Criteria andServerDescLike(String value) {
            addCriterion("SERVER_DESC like", value, "serverDesc");
            return (Criteria) this;
        }

        public Criteria andServerDescNotLike(String value) {
            addCriterion("SERVER_DESC not like", value, "serverDesc");
            return (Criteria) this;
        }

        public Criteria andServerDescIn(List<String> values) {
            addCriterion("SERVER_DESC in", values, "serverDesc");
            return (Criteria) this;
        }

        public Criteria andServerDescNotIn(List<String> values) {
            addCriterion("SERVER_DESC not in", values, "serverDesc");
            return (Criteria) this;
        }

        public Criteria andServerDescBetween(String value1, String value2) {
            addCriterion("SERVER_DESC between", value1, value2, "serverDesc");
            return (Criteria) this;
        }

        public Criteria andServerDescNotBetween(String value1, String value2) {
            addCriterion("SERVER_DESC not between", value1, value2, "serverDesc");
            return (Criteria) this;
        }

        public Criteria andHandlerClassIsNull() {
            addCriterion("HANDLER_CLASS is null");
            return (Criteria) this;
        }

        public Criteria andHandlerClassIsNotNull() {
            addCriterion("HANDLER_CLASS is not null");
            return (Criteria) this;
        }

        public Criteria andHandlerClassEqualTo(String value) {
            addCriterion("HANDLER_CLASS =", value, "handlerClass");
            return (Criteria) this;
        }

        public Criteria andHandlerClassNotEqualTo(String value) {
            addCriterion("HANDLER_CLASS <>", value, "handlerClass");
            return (Criteria) this;
        }

        public Criteria andHandlerClassGreaterThan(String value) {
            addCriterion("HANDLER_CLASS >", value, "handlerClass");
            return (Criteria) this;
        }

        public Criteria andHandlerClassGreaterThanOrEqualTo(String value) {
            addCriterion("HANDLER_CLASS >=", value, "handlerClass");
            return (Criteria) this;
        }

        public Criteria andHandlerClassLessThan(String value) {
            addCriterion("HANDLER_CLASS <", value, "handlerClass");
            return (Criteria) this;
        }

        public Criteria andHandlerClassLessThanOrEqualTo(String value) {
            addCriterion("HANDLER_CLASS <=", value, "handlerClass");
            return (Criteria) this;
        }

        public Criteria andHandlerClassLike(String value) {
            addCriterion("HANDLER_CLASS like", value, "handlerClass");
            return (Criteria) this;
        }

        public Criteria andHandlerClassNotLike(String value) {
            addCriterion("HANDLER_CLASS not like", value, "handlerClass");
            return (Criteria) this;
        }

        public Criteria andHandlerClassIn(List<String> values) {
            addCriterion("HANDLER_CLASS in", values, "handlerClass");
            return (Criteria) this;
        }

        public Criteria andHandlerClassNotIn(List<String> values) {
            addCriterion("HANDLER_CLASS not in", values, "handlerClass");
            return (Criteria) this;
        }

        public Criteria andHandlerClassBetween(String value1, String value2) {
            addCriterion("HANDLER_CLASS between", value1, value2, "handlerClass");
            return (Criteria) this;
        }

        public Criteria andHandlerClassNotBetween(String value1, String value2) {
            addCriterion("HANDLER_CLASS not between", value1, value2, "handlerClass");
            return (Criteria) this;
        }

        public Criteria andHandlerMethodIsNull() {
            addCriterion("HANDLER_METHOD is null");
            return (Criteria) this;
        }

        public Criteria andHandlerMethodIsNotNull() {
            addCriterion("HANDLER_METHOD is not null");
            return (Criteria) this;
        }

        public Criteria andHandlerMethodEqualTo(String value) {
            addCriterion("HANDLER_METHOD =", value, "handlerMethod");
            return (Criteria) this;
        }

        public Criteria andHandlerMethodNotEqualTo(String value) {
            addCriterion("HANDLER_METHOD <>", value, "handlerMethod");
            return (Criteria) this;
        }

        public Criteria andHandlerMethodGreaterThan(String value) {
            addCriterion("HANDLER_METHOD >", value, "handlerMethod");
            return (Criteria) this;
        }

        public Criteria andHandlerMethodGreaterThanOrEqualTo(String value) {
            addCriterion("HANDLER_METHOD >=", value, "handlerMethod");
            return (Criteria) this;
        }

        public Criteria andHandlerMethodLessThan(String value) {
            addCriterion("HANDLER_METHOD <", value, "handlerMethod");
            return (Criteria) this;
        }

        public Criteria andHandlerMethodLessThanOrEqualTo(String value) {
            addCriterion("HANDLER_METHOD <=", value, "handlerMethod");
            return (Criteria) this;
        }

        public Criteria andHandlerMethodLike(String value) {
            addCriterion("HANDLER_METHOD like", value, "handlerMethod");
            return (Criteria) this;
        }

        public Criteria andHandlerMethodNotLike(String value) {
            addCriterion("HANDLER_METHOD not like", value, "handlerMethod");
            return (Criteria) this;
        }

        public Criteria andHandlerMethodIn(List<String> values) {
            addCriterion("HANDLER_METHOD in", values, "handlerMethod");
            return (Criteria) this;
        }

        public Criteria andHandlerMethodNotIn(List<String> values) {
            addCriterion("HANDLER_METHOD not in", values, "handlerMethod");
            return (Criteria) this;
        }

        public Criteria andHandlerMethodBetween(String value1, String value2) {
            addCriterion("HANDLER_METHOD between", value1, value2, "handlerMethod");
            return (Criteria) this;
        }

        public Criteria andHandlerMethodNotBetween(String value1, String value2) {
            addCriterion("HANDLER_METHOD not between", value1, value2, "handlerMethod");
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