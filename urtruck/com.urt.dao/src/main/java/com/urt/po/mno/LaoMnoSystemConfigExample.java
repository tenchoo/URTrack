package com.urt.po.mno;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class LaoMnoSystemConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LaoMnoSystemConfigExample() {
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

        public Criteria andConfigIdIsNull() {
            addCriterion("CONFIG_ID is null");
            return (Criteria) this;
        }

        public Criteria andConfigIdIsNotNull() {
            addCriterion("CONFIG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andConfigIdEqualTo(Long value) {
            addCriterion("CONFIG_ID =", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdNotEqualTo(Long value) {
            addCriterion("CONFIG_ID <>", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdGreaterThan(Long value) {
            addCriterion("CONFIG_ID >", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdGreaterThanOrEqualTo(Long value) {
            addCriterion("CONFIG_ID >=", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdLessThan(Long value) {
            addCriterion("CONFIG_ID <", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdLessThanOrEqualTo(Long value) {
            addCriterion("CONFIG_ID <=", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdIn(List<Long> values) {
            addCriterion("CONFIG_ID in", values, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdNotIn(List<Long> values) {
            addCriterion("CONFIG_ID not in", values, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdBetween(Long value1, Long value2) {
            addCriterion("CONFIG_ID between", value1, value2, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdNotBetween(Long value1, Long value2) {
            addCriterion("CONFIG_ID not between", value1, value2, "configId");
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

        public Criteria andAppKeyIsNull() {
            addCriterion("APP_KEY is null");
            return (Criteria) this;
        }

        public Criteria andAppKeyIsNotNull() {
            addCriterion("APP_KEY is not null");
            return (Criteria) this;
        }

        public Criteria andAppKeyEqualTo(String value) {
            addCriterion("APP_KEY =", value, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyNotEqualTo(String value) {
            addCriterion("APP_KEY <>", value, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyGreaterThan(String value) {
            addCriterion("APP_KEY >", value, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyGreaterThanOrEqualTo(String value) {
            addCriterion("APP_KEY >=", value, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyLessThan(String value) {
            addCriterion("APP_KEY <", value, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyLessThanOrEqualTo(String value) {
            addCriterion("APP_KEY <=", value, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyLike(String value) {
            addCriterion("APP_KEY like", value, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyNotLike(String value) {
            addCriterion("APP_KEY not like", value, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyIn(List<String> values) {
            addCriterion("APP_KEY in", values, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyNotIn(List<String> values) {
            addCriterion("APP_KEY not in", values, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyBetween(String value1, String value2) {
            addCriterion("APP_KEY between", value1, value2, "appKey");
            return (Criteria) this;
        }

        public Criteria andAppKeyNotBetween(String value1, String value2) {
            addCriterion("APP_KEY not between", value1, value2, "appKey");
            return (Criteria) this;
        }

        public Criteria andIpLimitIsNull() {
            addCriterion("IP_LIMIT is null");
            return (Criteria) this;
        }

        public Criteria andIpLimitIsNotNull() {
            addCriterion("IP_LIMIT is not null");
            return (Criteria) this;
        }

        public Criteria andIpLimitEqualTo(String value) {
            addCriterion("IP_LIMIT =", value, "ipLimit");
            return (Criteria) this;
        }

        public Criteria andIpLimitNotEqualTo(String value) {
            addCriterion("IP_LIMIT <>", value, "ipLimit");
            return (Criteria) this;
        }

        public Criteria andIpLimitGreaterThan(String value) {
            addCriterion("IP_LIMIT >", value, "ipLimit");
            return (Criteria) this;
        }

        public Criteria andIpLimitGreaterThanOrEqualTo(String value) {
            addCriterion("IP_LIMIT >=", value, "ipLimit");
            return (Criteria) this;
        }

        public Criteria andIpLimitLessThan(String value) {
            addCriterion("IP_LIMIT <", value, "ipLimit");
            return (Criteria) this;
        }

        public Criteria andIpLimitLessThanOrEqualTo(String value) {
            addCriterion("IP_LIMIT <=", value, "ipLimit");
            return (Criteria) this;
        }

        public Criteria andIpLimitLike(String value) {
            addCriterion("IP_LIMIT like", value, "ipLimit");
            return (Criteria) this;
        }

        public Criteria andIpLimitNotLike(String value) {
            addCriterion("IP_LIMIT not like", value, "ipLimit");
            return (Criteria) this;
        }

        public Criteria andIpLimitIn(List<String> values) {
            addCriterion("IP_LIMIT in", values, "ipLimit");
            return (Criteria) this;
        }

        public Criteria andIpLimitNotIn(List<String> values) {
            addCriterion("IP_LIMIT not in", values, "ipLimit");
            return (Criteria) this;
        }

        public Criteria andIpLimitBetween(String value1, String value2) {
            addCriterion("IP_LIMIT between", value1, value2, "ipLimit");
            return (Criteria) this;
        }

        public Criteria andIpLimitNotBetween(String value1, String value2) {
            addCriterion("IP_LIMIT not between", value1, value2, "ipLimit");
            return (Criteria) this;
        }

        public Criteria andSignLimitIsNull() {
            addCriterion("SIGN_LIMIT is null");
            return (Criteria) this;
        }

        public Criteria andSignLimitIsNotNull() {
            addCriterion("SIGN_LIMIT is not null");
            return (Criteria) this;
        }

        public Criteria andSignLimitEqualTo(String value) {
            addCriterion("SIGN_LIMIT =", value, "signLimit");
            return (Criteria) this;
        }

        public Criteria andSignLimitNotEqualTo(String value) {
            addCriterion("SIGN_LIMIT <>", value, "signLimit");
            return (Criteria) this;
        }

        public Criteria andSignLimitGreaterThan(String value) {
            addCriterion("SIGN_LIMIT >", value, "signLimit");
            return (Criteria) this;
        }

        public Criteria andSignLimitGreaterThanOrEqualTo(String value) {
            addCriterion("SIGN_LIMIT >=", value, "signLimit");
            return (Criteria) this;
        }

        public Criteria andSignLimitLessThan(String value) {
            addCriterion("SIGN_LIMIT <", value, "signLimit");
            return (Criteria) this;
        }

        public Criteria andSignLimitLessThanOrEqualTo(String value) {
            addCriterion("SIGN_LIMIT <=", value, "signLimit");
            return (Criteria) this;
        }

        public Criteria andSignLimitLike(String value) {
            addCriterion("SIGN_LIMIT like", value, "signLimit");
            return (Criteria) this;
        }

        public Criteria andSignLimitNotLike(String value) {
            addCriterion("SIGN_LIMIT not like", value, "signLimit");
            return (Criteria) this;
        }

        public Criteria andSignLimitIn(List<String> values) {
            addCriterion("SIGN_LIMIT in", values, "signLimit");
            return (Criteria) this;
        }

        public Criteria andSignLimitNotIn(List<String> values) {
            addCriterion("SIGN_LIMIT not in", values, "signLimit");
            return (Criteria) this;
        }

        public Criteria andSignLimitBetween(String value1, String value2) {
            addCriterion("SIGN_LIMIT between", value1, value2, "signLimit");
            return (Criteria) this;
        }

        public Criteria andSignLimitNotBetween(String value1, String value2) {
            addCriterion("SIGN_LIMIT not between", value1, value2, "signLimit");
            return (Criteria) this;
        }

        public Criteria andServerContentFormatIsNull() {
            addCriterion("SERVER_CONTENT_FORMAT is null");
            return (Criteria) this;
        }

        public Criteria andServerContentFormatIsNotNull() {
            addCriterion("SERVER_CONTENT_FORMAT is not null");
            return (Criteria) this;
        }

        public Criteria andServerContentFormatEqualTo(String value) {
            addCriterion("SERVER_CONTENT_FORMAT =", value, "serverContentFormat");
            return (Criteria) this;
        }

        public Criteria andServerContentFormatNotEqualTo(String value) {
            addCriterion("SERVER_CONTENT_FORMAT <>", value, "serverContentFormat");
            return (Criteria) this;
        }

        public Criteria andServerContentFormatGreaterThan(String value) {
            addCriterion("SERVER_CONTENT_FORMAT >", value, "serverContentFormat");
            return (Criteria) this;
        }

        public Criteria andServerContentFormatGreaterThanOrEqualTo(String value) {
            addCriterion("SERVER_CONTENT_FORMAT >=", value, "serverContentFormat");
            return (Criteria) this;
        }

        public Criteria andServerContentFormatLessThan(String value) {
            addCriterion("SERVER_CONTENT_FORMAT <", value, "serverContentFormat");
            return (Criteria) this;
        }

        public Criteria andServerContentFormatLessThanOrEqualTo(String value) {
            addCriterion("SERVER_CONTENT_FORMAT <=", value, "serverContentFormat");
            return (Criteria) this;
        }

        public Criteria andServerContentFormatLike(String value) {
            addCriterion("SERVER_CONTENT_FORMAT like", value, "serverContentFormat");
            return (Criteria) this;
        }

        public Criteria andServerContentFormatNotLike(String value) {
            addCriterion("SERVER_CONTENT_FORMAT not like", value, "serverContentFormat");
            return (Criteria) this;
        }

        public Criteria andServerContentFormatIn(List<String> values) {
            addCriterion("SERVER_CONTENT_FORMAT in", values, "serverContentFormat");
            return (Criteria) this;
        }

        public Criteria andServerContentFormatNotIn(List<String> values) {
            addCriterion("SERVER_CONTENT_FORMAT not in", values, "serverContentFormat");
            return (Criteria) this;
        }

        public Criteria andServerContentFormatBetween(String value1, String value2) {
            addCriterion("SERVER_CONTENT_FORMAT between", value1, value2, "serverContentFormat");
            return (Criteria) this;
        }

        public Criteria andServerContentFormatNotBetween(String value1, String value2) {
            addCriterion("SERVER_CONTENT_FORMAT not between", value1, value2, "serverContentFormat");
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

        public Criteria andSystemDescIsNull() {
            addCriterion("SYSTEM_DESC is null");
            return (Criteria) this;
        }

        public Criteria andSystemDescIsNotNull() {
            addCriterion("SYSTEM_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andSystemDescEqualTo(String value) {
            addCriterion("SYSTEM_DESC =", value, "systemDesc");
            return (Criteria) this;
        }

        public Criteria andSystemDescNotEqualTo(String value) {
            addCriterion("SYSTEM_DESC <>", value, "systemDesc");
            return (Criteria) this;
        }

        public Criteria andSystemDescGreaterThan(String value) {
            addCriterion("SYSTEM_DESC >", value, "systemDesc");
            return (Criteria) this;
        }

        public Criteria andSystemDescGreaterThanOrEqualTo(String value) {
            addCriterion("SYSTEM_DESC >=", value, "systemDesc");
            return (Criteria) this;
        }

        public Criteria andSystemDescLessThan(String value) {
            addCriterion("SYSTEM_DESC <", value, "systemDesc");
            return (Criteria) this;
        }

        public Criteria andSystemDescLessThanOrEqualTo(String value) {
            addCriterion("SYSTEM_DESC <=", value, "systemDesc");
            return (Criteria) this;
        }

        public Criteria andSystemDescLike(String value) {
            addCriterion("SYSTEM_DESC like", value, "systemDesc");
            return (Criteria) this;
        }

        public Criteria andSystemDescNotLike(String value) {
            addCriterion("SYSTEM_DESC not like", value, "systemDesc");
            return (Criteria) this;
        }

        public Criteria andSystemDescIn(List<String> values) {
            addCriterion("SYSTEM_DESC in", values, "systemDesc");
            return (Criteria) this;
        }

        public Criteria andSystemDescNotIn(List<String> values) {
            addCriterion("SYSTEM_DESC not in", values, "systemDesc");
            return (Criteria) this;
        }

        public Criteria andSystemDescBetween(String value1, String value2) {
            addCriterion("SYSTEM_DESC between", value1, value2, "systemDesc");
            return (Criteria) this;
        }

        public Criteria andSystemDescNotBetween(String value1, String value2) {
            addCriterion("SYSTEM_DESC not between", value1, value2, "systemDesc");
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

        public Criteria andPushUrlIsNull() {
            addCriterion("PUSH_URL is null");
            return (Criteria) this;
        }

        public Criteria andPushUrlIsNotNull() {
            addCriterion("PUSH_URL is not null");
            return (Criteria) this;
        }

        public Criteria andPushUrlEqualTo(String value) {
            addCriterion("PUSH_URL =", value, "pushUrl");
            return (Criteria) this;
        }

        public Criteria andPushUrlNotEqualTo(String value) {
            addCriterion("PUSH_URL <>", value, "pushUrl");
            return (Criteria) this;
        }

        public Criteria andPushUrlGreaterThan(String value) {
            addCriterion("PUSH_URL >", value, "pushUrl");
            return (Criteria) this;
        }

        public Criteria andPushUrlGreaterThanOrEqualTo(String value) {
            addCriterion("PUSH_URL >=", value, "pushUrl");
            return (Criteria) this;
        }

        public Criteria andPushUrlLessThan(String value) {
            addCriterion("PUSH_URL <", value, "pushUrl");
            return (Criteria) this;
        }

        public Criteria andPushUrlLessThanOrEqualTo(String value) {
            addCriterion("PUSH_URL <=", value, "pushUrl");
            return (Criteria) this;
        }

        public Criteria andPushUrlLike(String value) {
            addCriterion("PUSH_URL like", value, "pushUrl");
            return (Criteria) this;
        }

        public Criteria andPushUrlNotLike(String value) {
            addCriterion("PUSH_URL not like", value, "pushUrl");
            return (Criteria) this;
        }

        public Criteria andPushUrlIn(List<String> values) {
            addCriterion("PUSH_URL in", values, "pushUrl");
            return (Criteria) this;
        }

        public Criteria andPushUrlNotIn(List<String> values) {
            addCriterion("PUSH_URL not in", values, "pushUrl");
            return (Criteria) this;
        }

        public Criteria andPushUrlBetween(String value1, String value2) {
            addCriterion("PUSH_URL between", value1, value2, "pushUrl");
            return (Criteria) this;
        }

        public Criteria andPushUrlNotBetween(String value1, String value2) {
            addCriterion("PUSH_URL not between", value1, value2, "pushUrl");
            return (Criteria) this;
        }

        public Criteria andPushProtocolIsNull() {
            addCriterion("PUSH_PROTOCOL is null");
            return (Criteria) this;
        }

        public Criteria andPushProtocolIsNotNull() {
            addCriterion("PUSH_PROTOCOL is not null");
            return (Criteria) this;
        }

        public Criteria andPushProtocolEqualTo(String value) {
            addCriterion("PUSH_PROTOCOL =", value, "pushProtocol");
            return (Criteria) this;
        }

        public Criteria andPushProtocolNotEqualTo(String value) {
            addCriterion("PUSH_PROTOCOL <>", value, "pushProtocol");
            return (Criteria) this;
        }

        public Criteria andPushProtocolGreaterThan(String value) {
            addCriterion("PUSH_PROTOCOL >", value, "pushProtocol");
            return (Criteria) this;
        }

        public Criteria andPushProtocolGreaterThanOrEqualTo(String value) {
            addCriterion("PUSH_PROTOCOL >=", value, "pushProtocol");
            return (Criteria) this;
        }

        public Criteria andPushProtocolLessThan(String value) {
            addCriterion("PUSH_PROTOCOL <", value, "pushProtocol");
            return (Criteria) this;
        }

        public Criteria andPushProtocolLessThanOrEqualTo(String value) {
            addCriterion("PUSH_PROTOCOL <=", value, "pushProtocol");
            return (Criteria) this;
        }

        public Criteria andPushProtocolLike(String value) {
            addCriterion("PUSH_PROTOCOL like", value, "pushProtocol");
            return (Criteria) this;
        }

        public Criteria andPushProtocolNotLike(String value) {
            addCriterion("PUSH_PROTOCOL not like", value, "pushProtocol");
            return (Criteria) this;
        }

        public Criteria andPushProtocolIn(List<String> values) {
            addCriterion("PUSH_PROTOCOL in", values, "pushProtocol");
            return (Criteria) this;
        }

        public Criteria andPushProtocolNotIn(List<String> values) {
            addCriterion("PUSH_PROTOCOL not in", values, "pushProtocol");
            return (Criteria) this;
        }

        public Criteria andPushProtocolBetween(String value1, String value2) {
            addCriterion("PUSH_PROTOCOL between", value1, value2, "pushProtocol");
            return (Criteria) this;
        }

        public Criteria andPushProtocolNotBetween(String value1, String value2) {
            addCriterion("PUSH_PROTOCOL not between", value1, value2, "pushProtocol");
            return (Criteria) this;
        }

        public Criteria andPushContentFormatIsNull() {
            addCriterion("PUSH_CONTENT_FORMAT is null");
            return (Criteria) this;
        }

        public Criteria andPushContentFormatIsNotNull() {
            addCriterion("PUSH_CONTENT_FORMAT is not null");
            return (Criteria) this;
        }

        public Criteria andPushContentFormatEqualTo(String value) {
            addCriterion("PUSH_CONTENT_FORMAT =", value, "pushContentFormat");
            return (Criteria) this;
        }

        public Criteria andPushContentFormatNotEqualTo(String value) {
            addCriterion("PUSH_CONTENT_FORMAT <>", value, "pushContentFormat");
            return (Criteria) this;
        }

        public Criteria andPushContentFormatGreaterThan(String value) {
            addCriterion("PUSH_CONTENT_FORMAT >", value, "pushContentFormat");
            return (Criteria) this;
        }

        public Criteria andPushContentFormatGreaterThanOrEqualTo(String value) {
            addCriterion("PUSH_CONTENT_FORMAT >=", value, "pushContentFormat");
            return (Criteria) this;
        }

        public Criteria andPushContentFormatLessThan(String value) {
            addCriterion("PUSH_CONTENT_FORMAT <", value, "pushContentFormat");
            return (Criteria) this;
        }

        public Criteria andPushContentFormatLessThanOrEqualTo(String value) {
            addCriterion("PUSH_CONTENT_FORMAT <=", value, "pushContentFormat");
            return (Criteria) this;
        }

        public Criteria andPushContentFormatLike(String value) {
            addCriterion("PUSH_CONTENT_FORMAT like", value, "pushContentFormat");
            return (Criteria) this;
        }

        public Criteria andPushContentFormatNotLike(String value) {
            addCriterion("PUSH_CONTENT_FORMAT not like", value, "pushContentFormat");
            return (Criteria) this;
        }

        public Criteria andPushContentFormatIn(List<String> values) {
            addCriterion("PUSH_CONTENT_FORMAT in", values, "pushContentFormat");
            return (Criteria) this;
        }

        public Criteria andPushContentFormatNotIn(List<String> values) {
            addCriterion("PUSH_CONTENT_FORMAT not in", values, "pushContentFormat");
            return (Criteria) this;
        }

        public Criteria andPushContentFormatBetween(String value1, String value2) {
            addCriterion("PUSH_CONTENT_FORMAT between", value1, value2, "pushContentFormat");
            return (Criteria) this;
        }

        public Criteria andPushContentFormatNotBetween(String value1, String value2) {
            addCriterion("PUSH_CONTENT_FORMAT not between", value1, value2, "pushContentFormat");
            return (Criteria) this;
        }

        public Criteria andPushEncryptionWayIsNull() {
            addCriterion("PUSH_ENCRYPTION_WAY is null");
            return (Criteria) this;
        }

        public Criteria andPushEncryptionWayIsNotNull() {
            addCriterion("PUSH_ENCRYPTION_WAY is not null");
            return (Criteria) this;
        }

        public Criteria andPushEncryptionWayEqualTo(String value) {
            addCriterion("PUSH_ENCRYPTION_WAY =", value, "pushEncryptionWay");
            return (Criteria) this;
        }

        public Criteria andPushEncryptionWayNotEqualTo(String value) {
            addCriterion("PUSH_ENCRYPTION_WAY <>", value, "pushEncryptionWay");
            return (Criteria) this;
        }

        public Criteria andPushEncryptionWayGreaterThan(String value) {
            addCriterion("PUSH_ENCRYPTION_WAY >", value, "pushEncryptionWay");
            return (Criteria) this;
        }

        public Criteria andPushEncryptionWayGreaterThanOrEqualTo(String value) {
            addCriterion("PUSH_ENCRYPTION_WAY >=", value, "pushEncryptionWay");
            return (Criteria) this;
        }

        public Criteria andPushEncryptionWayLessThan(String value) {
            addCriterion("PUSH_ENCRYPTION_WAY <", value, "pushEncryptionWay");
            return (Criteria) this;
        }

        public Criteria andPushEncryptionWayLessThanOrEqualTo(String value) {
            addCriterion("PUSH_ENCRYPTION_WAY <=", value, "pushEncryptionWay");
            return (Criteria) this;
        }

        public Criteria andPushEncryptionWayLike(String value) {
            addCriterion("PUSH_ENCRYPTION_WAY like", value, "pushEncryptionWay");
            return (Criteria) this;
        }

        public Criteria andPushEncryptionWayNotLike(String value) {
            addCriterion("PUSH_ENCRYPTION_WAY not like", value, "pushEncryptionWay");
            return (Criteria) this;
        }

        public Criteria andPushEncryptionWayIn(List<String> values) {
            addCriterion("PUSH_ENCRYPTION_WAY in", values, "pushEncryptionWay");
            return (Criteria) this;
        }

        public Criteria andPushEncryptionWayNotIn(List<String> values) {
            addCriterion("PUSH_ENCRYPTION_WAY not in", values, "pushEncryptionWay");
            return (Criteria) this;
        }

        public Criteria andPushEncryptionWayBetween(String value1, String value2) {
            addCriterion("PUSH_ENCRYPTION_WAY between", value1, value2, "pushEncryptionWay");
            return (Criteria) this;
        }

        public Criteria andPushEncryptionWayNotBetween(String value1, String value2) {
            addCriterion("PUSH_ENCRYPTION_WAY not between", value1, value2, "pushEncryptionWay");
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