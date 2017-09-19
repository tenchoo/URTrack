package com.urt.po.mno;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class LaoMnoServerConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LaoMnoServerConfigExample() {
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

        public Criteria andVisitLimitTimesIsNull() {
            addCriterion("VISIT_LIMIT_TIMES is null");
            return (Criteria) this;
        }

        public Criteria andVisitLimitTimesIsNotNull() {
            addCriterion("VISIT_LIMIT_TIMES is not null");
            return (Criteria) this;
        }

        public Criteria andVisitLimitTimesEqualTo(Long value) {
            addCriterion("VISIT_LIMIT_TIMES =", value, "visitLimitTimes");
            return (Criteria) this;
        }

        public Criteria andVisitLimitTimesNotEqualTo(Long value) {
            addCriterion("VISIT_LIMIT_TIMES <>", value, "visitLimitTimes");
            return (Criteria) this;
        }

        public Criteria andVisitLimitTimesGreaterThan(Long value) {
            addCriterion("VISIT_LIMIT_TIMES >", value, "visitLimitTimes");
            return (Criteria) this;
        }

        public Criteria andVisitLimitTimesGreaterThanOrEqualTo(Long value) {
            addCriterion("VISIT_LIMIT_TIMES >=", value, "visitLimitTimes");
            return (Criteria) this;
        }

        public Criteria andVisitLimitTimesLessThan(Long value) {
            addCriterion("VISIT_LIMIT_TIMES <", value, "visitLimitTimes");
            return (Criteria) this;
        }

        public Criteria andVisitLimitTimesLessThanOrEqualTo(Long value) {
            addCriterion("VISIT_LIMIT_TIMES <=", value, "visitLimitTimes");
            return (Criteria) this;
        }

        public Criteria andVisitLimitTimesIn(List<Long> values) {
            addCriterion("VISIT_LIMIT_TIMES in", values, "visitLimitTimes");
            return (Criteria) this;
        }

        public Criteria andVisitLimitTimesNotIn(List<Long> values) {
            addCriterion("VISIT_LIMIT_TIMES not in", values, "visitLimitTimes");
            return (Criteria) this;
        }

        public Criteria andVisitLimitTimesBetween(Long value1, Long value2) {
            addCriterion("VISIT_LIMIT_TIMES between", value1, value2, "visitLimitTimes");
            return (Criteria) this;
        }

        public Criteria andVisitLimitTimesNotBetween(Long value1, Long value2) {
            addCriterion("VISIT_LIMIT_TIMES not between", value1, value2, "visitLimitTimes");
            return (Criteria) this;
        }

        public Criteria andIsOpenIsNull() {
            addCriterion("IS_OPEN is null");
            return (Criteria) this;
        }

        public Criteria andIsOpenIsNotNull() {
            addCriterion("IS_OPEN is not null");
            return (Criteria) this;
        }

        public Criteria andIsOpenEqualTo(String value) {
            addCriterion("IS_OPEN =", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenNotEqualTo(String value) {
            addCriterion("IS_OPEN <>", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenGreaterThan(String value) {
            addCriterion("IS_OPEN >", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenGreaterThanOrEqualTo(String value) {
            addCriterion("IS_OPEN >=", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenLessThan(String value) {
            addCriterion("IS_OPEN <", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenLessThanOrEqualTo(String value) {
            addCriterion("IS_OPEN <=", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenLike(String value) {
            addCriterion("IS_OPEN like", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenNotLike(String value) {
            addCriterion("IS_OPEN not like", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenIn(List<String> values) {
            addCriterion("IS_OPEN in", values, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenNotIn(List<String> values) {
            addCriterion("IS_OPEN not in", values, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenBetween(String value1, String value2) {
            addCriterion("IS_OPEN between", value1, value2, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenNotBetween(String value1, String value2) {
            addCriterion("IS_OPEN not between", value1, value2, "isOpen");
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