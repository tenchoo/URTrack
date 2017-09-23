package com.urt.service.remain;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.LaoCustGroupDto;
import com.urt.dto.MailDto;
import com.urt.dto.Remain.BalAlarmDto;
import com.urt.dto.Remain.LaoFAcctdepositDto;
import com.urt.dto.Remain.LaoRuleDefDto;
import com.urt.interfaces.chargeOff.UserFeeInfoService;
import com.urt.interfaces.remain.RemainService;
import com.urt.interfaces.sendMessage.SendMessageService;
import com.urt.mapper.LaoBAccesslogMapper;
import com.urt.mapper.LaoBPaylogMapper;
import com.urt.mapper.LaoBalAlmLogPoMapper;
import com.urt.mapper.LaoBillResultMapper;
import com.urt.mapper.LaoFAcctdepositMapper;
import com.urt.mapper.LaoRuleDefMapper;
import com.urt.mapper.LaoRuleRelMapper;
import com.urt.mapper.LaoSmsTmplMapper;
import com.urt.mapper.ext.LaoBAccesslogExtMapper;
import com.urt.mapper.ext.LaoBPaylogExtMapper;
import com.urt.mapper.ext.LaoBillResultExtMapper;
import com.urt.mapper.ext.LaoCustGroupPoExtMapper;
import com.urt.mapper.ext.LaoCustomerPoExtMapper;
import com.urt.mapper.ext.LaoFAcctdepositExtMapper;
import com.urt.mapper.ext.LaoRuleDefExtMapper;
import com.urt.mapper.ext.LaoRuleRelExtMapper;
import com.urt.mapper.ext.LaoSsAccountPoExtMapper;
import com.urt.mapper.ext.LaoSsAccountRolePoExtMapper;
import com.urt.mapper.ext.LaoValParamExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoBAccesslog;
import com.urt.po.LaoBPaylog;
import com.urt.po.LaoBalAlmLogPo;
import com.urt.po.LaoBillResult;
import com.urt.po.LaoCustGroupPo;
import com.urt.po.LaoCustomerPo;
import com.urt.po.LaoFAcctdeposit;
import com.urt.po.LaoRuleDef;
import com.urt.po.LaoRuleRel;
import com.urt.po.LaoSmsTmpl;
import com.urt.po.LaoSsAccountRolePo;
import com.urt.po.LaoValParam;
import com.urt.service.RedisOperService;
import com.urt.service.util.MailUtil;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

/**
 * 余额中心业务层
 * 
 * @author lixing
 *
 */
@Service("remainService")
@Transactional(propagation = Propagation.REQUIRED)
public class RemainServiceImpl implements RemainService {

    Logger logger = Logger.getLogger(getClass());
    @Autowired
    LaoBAccesslogExtMapper laoBAccesslogExtDao;
    @Autowired
    LaoSsAccountPoExtMapper laoSsAccountPoExtDao;
    @Autowired
    LaoSsAccountRolePoExtMapper laoSsAccountRolePoExtDao;
    @Autowired
    LaoCustGroupPoExtMapper laoCustGroupExtDao;
    @Autowired
    LaoFAcctdepositExtMapper laoFAcctdepositExtDao;
    @Autowired
    LaoRuleDefExtMapper laoRuleDefExtDao;
    @Autowired
    LaoBPaylogExtMapper laoBPaylogExtDao;
    @Autowired
    LaoBillResultExtMapper laoBillResultExtDao;
    @Autowired
    LaoFAcctdepositMapper laoFAcctdepositDao;
    @Autowired
    LaoRuleRelMapper laoRuleRelDao;
    @Autowired
    LaoBPaylogMapper laoBPaylogDao;
    @Autowired
    LaoRuleRelExtMapper laoRuleRelExtDao;
    @Autowired
    LaoBAccesslogMapper laoBAccesslogDao;
    @Autowired
    LaoValParamExtMapper laoValParamExtDao;
    @Autowired
    LaoBillResultMapper laoBillResultDao;
    @Autowired
    LaoRuleDefMapper laoRuleDefDao;
    @Autowired
    LaoCustomerPoExtMapper laoCustomerExtDao;
    @Autowired
    SendMessageService sendMessageService;
    @Autowired
    MailUtil mailUtil;
    @Autowired
    RedisOperService redisOperService;
    @Autowired
    LaoSmsTmplMapper laoSmsTmplMapperDao ;
    @Autowired
    LaoBalAlmLogPoMapper LaoBalAlmLogDao;
    @Autowired
    LaoValParamExtMapper laoValParamExtMapperDao;

    // @Autowired
    // private UserFeeInfoService userFeeInfoService;

    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private LaoFAcctdeposit creatAct(Long custId, long fee, String cashTag) {
        LaoFAcctdeposit po = new LaoFAcctdeposit();
        po.setChannelCustId(custId);
        po.setDepositMoney(fee);
        po.setCashTag(cashTag);
        // -- po.setInitMoney(initMoney);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMM");
        String startCycId = sdf.format(cal.getTime());
        po.setStartCycId(Long.valueOf(startCycId));
        po.setEndCycId(205002L);
        po.setStartDate(cal.getTime());
        Calendar cal2 = Calendar.getInstance();
        cal2.set(2050, 1, 1);
        po.setEndDate(cal2.getTime());
        po.setInvoiceFee(fee);
        po.setPrintFee(fee);
        Long acctBalanceId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.ACCT_BALANCE_ID));
        po.setAcctBalanceId(acctBalanceId);
        laoFAcctdepositDao.insert(po);
        return po;
    }

    @Override
    public List<LaoCustGroupDto> selectAll() {
        List<LaoCustGroupPo> lcgList = laoCustGroupExtDao.selectAll();
        List<LaoCustGroupDto> dtos = new ArrayList<LaoCustGroupDto>();
        for (LaoCustGroupPo po : lcgList) {
            LaoCustGroupDto dto = new LaoCustGroupDto();
            BeanMapper.copy(po, dto);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public LaoFAcctdepositDto selectdepositByCustId(Long channelCustId) {
        LaoFAcctdeposit po = new LaoFAcctdeposit();
        if (laoFAcctdepositExtDao.selectByCustId(channelCustId) != null) {
            po = laoFAcctdepositExtDao.selectByCustId(channelCustId);
            LaoFAcctdepositDto dto = new LaoFAcctdepositDto();
            BeanMapper.copy(po, dto);
            return dto;
        } else {
            return null;
        }
    }

    @Override
    public List<LaoRuleDefDto> selectRuleByCustId(HashMap<String, Object> paraMap) {
        List<LaoRuleDef> lcgList = laoRuleDefExtDao.selectRuleByCustId(paraMap);
        List<LaoRuleDefDto> dtos = new ArrayList<LaoRuleDefDto>();
        for (LaoRuleDef po : lcgList) {
            LaoRuleDefDto dto = new LaoRuleDefDto();
            BeanMapper.copy(po, dto);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<LaoRuleDefDto> selectRules() {
        List<LaoRuleDef> lrdList = laoRuleDefExtDao.selectRules();
        List<LaoRuleDefDto> dtos = new ArrayList<LaoRuleDefDto>();
        for (LaoRuleDef po : lrdList) {
            LaoRuleDefDto dto = new LaoRuleDefDto();
            BeanMapper.copy(po, dto);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public int countUsersByCustId(Long custId) {
        int count = laoFAcctdepositExtDao.countUsersByCustId(custId);
        return count;
    }

    @Override
    public int selectRecvFeeByPayTag(Map<String, Object> paramMap) {
        return laoBPaylogExtDao.selectPayStatus(paramMap);
    }

    @Override
    public int selectNeedReturn(Map<String, Object> paramMap) {
        return laoBillResultExtDao.selectNeedReturn(paramMap);
    }

    @Override
    public int selectReturned(Map<String, Object> paramMap) {
        return laoBillResultExtDao.selectReturned(paramMap);
    }

    @Override
    public List<LaoRuleDefDto> selectRulesByGroupId(HashMap<String, Object> paraMap) {
        List<LaoRuleDef> lrdList = laoRuleDefExtDao.selectRulesByGroupId(paraMap);
        List<LaoRuleDefDto> dtos = new ArrayList<LaoRuleDefDto>();
        for (LaoRuleDef po : lrdList) {
            LaoRuleDefDto dto = new LaoRuleDefDto();
            BeanMapper.copy(po, dto);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public int updateRules(String param) throws ParseException {
        String[] datas = param.split(",");
        long ruleGroupId = Long.valueOf(datas[2]);
        LaoRuleRel lrlPo = new LaoRuleRel();
        lrlPo.setChannelCustId(Long.valueOf(datas[0]));
        lrlPo.setExpDate(Calendar.getInstance().getTime());
        lrlPo.setUpdateTime(lrlPo.getExpDate());
        lrlPo.setState("1");
        laoRuleRelExtDao.updateByCustId(lrlPo);
        LaoFAcctdeposit record = new LaoFAcctdeposit();
        record.setCashTag(datas[1]);
        record.setChannelCustId(Long.valueOf(datas[0]));
        laoFAcctdepositExtDao.updateByCustId(record);
        if (ruleGroupId != -1) {
            Date expDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2030-01-01 00:00:00");
            lrlPo.setRulegroupId(ruleGroupId);
            lrlPo.setExpDate(expDate);
            lrlPo.setEffDate(Calendar.getInstance().getTime());
            lrlPo.setUpdateTime(lrlPo.getEffDate());
            lrlPo.setState("0");
            return laoRuleRelDao.insert(lrlPo);
        }
        return 1;
    }

    @Override
    public int saveRules(String param) throws ParseException {
        String[] datas = param.split(",");
        Long custId = Long.valueOf(datas[0]);
        // long initMoney = Long.valueOf(datas[1]);
        String cashTag = datas[1];
        long ruleGroupId = Long.valueOf(datas[2]);
        long despositMoney = Long.valueOf(datas[3]);
        long invoiceFee = Long.valueOf(datas[4]);
        long printFee = Long.valueOf(datas[5]);

        // 账户入库
        LaoFAcctdeposit po = new LaoFAcctdeposit();
        po.setChannelCustId(custId);
        po.setDepositMoney(despositMoney);
        po.setCashTag(cashTag);
        // -- po.setInitMoney(initMoney);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMM");
        String startCycId = sdf.format(cal.getTime());
        po.setStartCycId(Long.valueOf(startCycId));
        po.setEndCycId(205002L);
        po.setStartDate(cal.getTime());
        Calendar cal2 = Calendar.getInstance();
        cal2.set(2050, 1, 1);
        po.setEndDate(cal2.getTime());
        po.setInvoiceFee(invoiceFee);
        po.setPrintFee(printFee);
        Long acctBalanceId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.ACCT_BALANCE_ID));
        po.setAcctBalanceId(acctBalanceId);
        laoFAcctdepositDao.insert(po);

        LaoRuleRel lrr = laoRuleRelExtDao.selectRelsByCustId(custId);
        int status = 1;
        // 没有则添加
        if (lrr == null && ruleGroupId != -1) {
            LaoRuleRel lrlPo = new LaoRuleRel();
            lrlPo.setChannelCustId(custId);
            lrlPo.setEffDate(Calendar.getInstance().getTime());
            Date expDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2030-01-01 00:00:00");
            lrlPo.setExpDate(expDate);
            lrlPo.setRulegroupId(ruleGroupId);
            status = laoRuleRelDao.insert(lrlPo);
        }
        return status;
    }

    @Override
    public int payment(String params, Long recvOperId) {
        logger.info("payment-params:" + params);
        String[] paraArr = params.split(",");
        Long custId = Long.valueOf(paraArr[0]);
        String postPayment = paraArr[1];
        postPayment = postPayment.split("\\.")[0];
        Long payment = Long.valueOf(postPayment);
        Long cashTag = Long.valueOf(paraArr[2]);

        // 缴费日志信息
        LaoBPaylog payLog = new LaoBPaylog();
        Long chargeId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.PAYLOG_ID));
        payLog.setPayFeeModeCode((short) 1);// 现金
        payLog.setChannelCustId(custId);
        payLog.setPaymentOp(10000);
        String feecntTag = "";
        feecntTag = cashTag == 0 ? "0" : "1";
        payLog.setFeecntTag(feecntTag);
        payLog.setRecvFee(payment);
        payLog.setChargeId(chargeId);
        payLog.setRealFee(payment);
        payLog.setRecvTime(Calendar.getInstance().getTime());
        payLog.setFeecntTime(Calendar.getInstance().getTime());
        payLog.setRecvOperId(recvOperId.toString());
        laoBPaylogDao.insert(payLog);

        // 缴费账户变更
        LaoFAcctdeposit laoFAcctdeposit = laoFAcctdepositExtDao.selectByCustId(custId);
        laoFAcctdeposit.setDepositMoney(laoFAcctdeposit.getDepositMoney() + payment);
        laoFAcctdeposit.setInvoiceFee(laoFAcctdeposit.getInvoiceFee() + payment);
        laoFAcctdepositDao.updateByPrimaryKeySelective(laoFAcctdeposit);

        // 存取款日志变更
        LaoBAccesslog laoBAccesslog = new LaoBAccesslog();
        laoBAccesslog.setChargeId(chargeId);// TODO
        laoBAccesslog.setMoney(payment);
        laoBAccesslog.setAcctBalanceId(laoFAcctdeposit.getAcctBalanceId());
        laoBAccesslog.setNewBalance(laoFAcctdeposit.getDepositMoney());
        laoBAccesslog.setOldBalance(laoFAcctdeposit.getDepositMoney() - payment);
        laoBAccesslog.setUpdateTime(Calendar.getInstance().getTime());
        Long accId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.ACCESSLOG_ID));
        laoBAccesslog.setAccessId(accId);
        laoBAccesslog.setRecvOperId(recvOperId.toString());
        int res = laoBAccesslogDao.insert(laoBAccesslog);

        HashMap<String, Object> resMap = new HashMap<String, Object>();
        // 结算计算服务
        LaoRuleRel laoRuleRel = laoRuleRelExtDao.selectRelsByCustId(custId);
        if (laoRuleRel != null) {
            // 遍历所有def对象(desc),通过ruleId取参数对象
            List<LaoRuleDef> laoRuleDefs = laoRuleDefExtDao.selectRuleDefs(laoRuleRel.getRulegroupId());
            if (laoRuleDefs.size() != 0) {
                // 调用结算计算接口
                if (cashTag == 1 || cashTag == 2) {
                    HashMap<String, Object> paraMap = new HashMap<String, Object>();
                    paraMap.put("realFee", payment);
                    paraMap.put("channelCustId", custId);
                    paraMap.put("cashTag", cashTag);
                    paraMap.put("recvOperId", recvOperId);
                    logger.info(paraMap);
                    resMap = balanceCalcu(paraMap);
                }

                // 调用结算返现接口 cashTag==1实时结算调用
                if (cashTag == 1) {
                    if (laoRuleDefs != null) {
                        returnFee(resMap);
                    }
                }
            }
        }
        return res;
    }

    /**
     * 结算返现接口(Map)
     * 
     * @param paraMap
     */
    public void returnFee(HashMap<String, Object> paraMap) {
        Long balanceId = (Long) paraMap.get("balanceId");
        Long custId = (Long) paraMap.get("channelCustId");
        String recvOperId = "";
        if (paraMap.get("recvOperId") != null) {
            recvOperId = paraMap.get("recvOperId").toString();
        }
        LaoBillResult laoBillResult = laoBillResultDao.selectByPrimaryKey(balanceId);
        LaoBPaylog laoBPaylog = new LaoBPaylog();
        laoBPaylog.setPayFeeModeCode((short) 1);
        laoBPaylog.setPaymentOp(10002);
        laoBPaylog.setRecvFee(laoBillResult.getBackFee());
        laoBPaylog.setRealFee(laoBillResult.getBackFee());
        laoBPaylog.setChannelCustId(custId);
        LaoFAcctdeposit deposit = laoFAcctdepositExtDao.selectByCustId(custId);
        laoBPaylog.setTradeId(balanceId);
        laoBPaylog.setRecvTime(Calendar.getInstance().getTime());// 返现时间
        Long payLogId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.PAYLOG_ID));
        laoBPaylog.setChargeId(payLogId);
        String feecntTag = deposit.getCashTag().equals("0") ? "0" : "1";
        laoBPaylog.setFeecntTag(feecntTag);
        laoBPaylog.setFeecntTime(Calendar.getInstance().getTime());
        laoBPaylog.setRecvOperId(recvOperId);
        laoBPaylogDao.insert(laoBPaylog);

        // 缴费账户变更
        LaoFAcctdeposit retu = laoFAcctdepositExtDao.selectByCustId(custId);
        retu.setDepositMoney(retu.getDepositMoney() + laoBillResult.getBackFee());
        laoFAcctdepositDao.updateByPrimaryKeySelective(retu);

        // 缴费日志信息
        LaoBAccesslog accLog = new LaoBAccesslog();
        accLog.setChargeId(payLogId);
        accLog.setMoney(laoBillResult.getBackFee());
        accLog.setAcctBalanceId(retu.getAcctBalanceId());
        accLog.setNewBalance(retu.getDepositMoney());
        accLog.setOldBalance(retu.getDepositMoney() - laoBillResult.getBackFee());
        accLog.setUpdateTime(Calendar.getInstance().getTime());
        Long accessId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.ACCESSLOG_ID));
        accLog.setAccessId(accessId);
        accLog.setRecvOperId(recvOperId);
        laoBAccesslogDao.insert(accLog);

        // 修改BillResult
        laoBillResult.setCashbackTag("1");
        laoBillResult.setCashChargeId(payLogId);
        laoBillResult.setCashTime(Calendar.getInstance().getTime());
        laoBillResult.setRecvOperId(recvOperId);
        laoBillResultDao.updateByPrimaryKey(laoBillResult);
    }

    /**
     * 结算返现接口(Long)
     * 
     * @param balanceId
     */
    @Override
    public void returnFee(Long balanceId, String recvOperId) {
        LaoBillResult laoBillResult = laoBillResultDao.selectByPrimaryKey(balanceId);
        if (laoBillResult.getCashbackTag().equals("0")) {
            HashMap<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put("balanceId", balanceId);
            paraMap.put("channelCustId", laoBillResult.getChannelCustId());
            paraMap.put("recvOperId", recvOperId);
            returnFee(paraMap);
        }
    }

    /**
     * 结算计算
     * 
     * @param paraMap
     */
    private HashMap<String, Object> balanceCalcu(Map<String, Object> paraMap) {
        logger.info("paraMap" + paraMap);
        Long custId = (Long) paraMap.get("channelCustId");
        String recvOperId = "";
        if (paraMap.get("recvOperId") != null) {
            recvOperId = paraMap.get("recvOperId").toString();
        }
        // 结算计算服务
        LaoRuleRel laoRuleRel = laoRuleRelExtDao.selectRelsByCustId(custId);
        // 定义可用的参数对象
        List<LaoValParam> usefulParams = new ArrayList<LaoValParam>();
        LaoRuleDef usefulDef = new LaoRuleDef();
        // 遍历所有def对象(优先级ASC),通过ruleId取参数对象
        List<LaoRuleDef> laoRuleDefs = laoRuleDefExtDao.selectRuleDefs(laoRuleRel.getRulegroupId());
        outer: for (LaoRuleDef laoRuleDef : laoRuleDefs) {
            List<LaoValParam> ruleValParams = laoValParamExtDao.selectAllParamsByRuleId(laoRuleDef.getRuleId());
            String condStat = laoRuleDef.getCondStat();
            logger.info("condStat:" + condStat);
            for (int i = 0; i < ruleValParams.size(); i++) {
                // 固定公式
                if (Long.valueOf(ruleValParams.get(i).getIdType()) == 2) {
                    usefulParams = ruleValParams;// 如果条件为真，记录当前可用的参数
                    usefulDef = laoRuleDef;// 如果条件为真， 记录当前规则
                    break outer;
                    // 可变参数
                } else if (Long.valueOf(ruleValParams.get(i).getIdType()) == 1) {
                    Float val = Float.valueOf(paraMap.get(ruleValParams.get(i).getParaValue()).toString()) / 100f;
                    condStat = condStat.replaceAll(ruleValParams.get(i).getParaName(), val.toString());
                    if (i == ruleValParams.size() - 1) {
                        ScriptEngineManager manager = new ScriptEngineManager();
                        ScriptEngine engine = manager.getEngineByName("javascript");
                        try {
                            logger.info("condStat替换后:" + condStat);
                            Object obj = engine.eval(condStat);
                            if (obj.toString().equals("true")) {
                                usefulParams = ruleValParams;// 如果条件为真，记录当前可用的参数
                                usefulDef = laoRuleDef;// 如果条件为真， 记录当前规则
                                break outer;
                            } else {
                                continue outer;
                            }
                        } catch (ScriptException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
        String calFormula = "";
        if (usefulDef.getCalFormula() != null) {
            calFormula = usefulDef.getCalFormula();// 获取可用计算公式
        }
        logger.info("before replace calFormula:" + calFormula);
        for (LaoValParam param : usefulParams) {
            calFormula = calFormula.replaceAll(param.getParaName(), paraMap.get(param.getParaValue()).toString());
        }
        logger.info("calFormula:" + calFormula);
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        String result = "";
        try {
            if (!calFormula.equals("")) {
                result = engine.eval(calFormula).toString();// 公式计算后的值
            }
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        // 将结算信息记录到结算表
        LaoBillResult laoBillResult = new LaoBillResult();
        Long balanceId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BILLID_ID));
        laoBillResult.setBalanceId(balanceId);
        laoBillResult.setBalanceTime(Calendar.getInstance().getTime());
        laoBillResult.setChannelCustId(custId);
        laoBillResult.setRuleId(usefulDef.getRuleId());
        laoBillResult.setCashbackTag("0"); // todo
        laoBillResult.setRecvOperId(recvOperId);
        if (result.indexOf(".") != -1) {
            result = result.split("\\.")[0];
        }
        laoBillResult.setBackFee(Long.valueOf(result));
        laoBillResult.setCycleId(
                Calendar.getInstance().get(Calendar.YEAR) * 100 + Calendar.getInstance().get(Calendar.MONTH) + 1);
        laoBillResult.setBalanceTime(Calendar.getInstance().getTime());
        laoBillResultDao.insert(laoBillResult);

        // 定义返回的Map集
        HashMap<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("balanceId", balanceId);
        resMap.put("date", Calendar.getInstance().getTime());
        resMap.put("channelCustId", custId);
        resMap.put("ruleId", usefulDef.getRuleId());
        resMap.put("backFee", Long.valueOf(result));
        resMap.put("cycleId",
                Calendar.getInstance().get(Calendar.YEAR) * 100 + Calendar.getInstance().get(Calendar.MONTH) + 1);
        resMap.put("cashBackTag", 0);
        resMap.put("recvOperId", recvOperId);
        return resMap;
    }

    Long getCashTag(String param) {
        long res = 0;
        switch (param) {
            case "不结算返现":
                res = 0;
                break;
            case "实时结算返现":
                res = 1;
                break;
            case "手工结算返现":
                res = 2;
                break;
        }
        return res;
    }

    @Override
    public int getRoleIdByAccountId(Long accountId) {
        int result = 0;
        List<LaoSsAccountRolePo> roleList = laoSsAccountRolePoExtDao.queryListByUserId(accountId);
        for (LaoSsAccountRolePo laoSsAccountRolePo : roleList) {
            if (laoSsAccountRolePo.getRoleId() == 1) {
                result = 1;
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> getAccessList(Long custId, String cyc, int start, int end) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        Map<String, Object> paraMap = generateCyc(cyc);
        paraMap.put("start", start);
        paraMap.put("end", end);
        LaoFAcctdepositDto laoFAcctdepositDto = selectdepositByCustId(custId);
        List<HashMap<String, Object>> resList = new ArrayList<HashMap<String, Object>>();
        int count = 0;
        if (laoFAcctdepositDto != null) {
            paraMap.put("balanceId", laoFAcctdepositDto.getAcctBalanceId());
            List<LaoBAccesslog> accessList = laoBAccesslogExtDao.selectByBalanceId(paraMap);
            for (LaoBAccesslog laoBAccesslog : accessList) {
                HashMap<String, Object> localMap = new HashMap<String, Object>();
                localMap = getReflectMap(laoBAccesslog);
                resList.add(localMap);
            }
            count = laoBAccesslogExtDao.countByBalanceId(paraMap);
        }

        resMap.put("resList", resList);
        resMap.put("count", count);
        return resMap;
    }

    @Override
    public Map<String, Object> getPayLogList(Long custId, String cyc, int start, int end) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        Map<String, Object> paraMap = generateCyc(cyc);
        List<LaoCustomerPo> custList = laoCustomerExtDao.queryAgent(null);
        HashMap<Long, Object> lMap = new HashMap<Long, Object>();
        for (LaoCustomerPo laoCustomerPo : custList) {
            lMap.put(laoCustomerPo.getCustId(), laoCustomerPo.getCustName());
        }
        paraMap.put("start", start);
        paraMap.put("end", end);
        paraMap.put("custId", custId);
        List<LaoBPaylog> payLogList = laoBPaylogExtDao.selectByCustId(paraMap);
        List<HashMap<String, Object>> resList = new ArrayList<HashMap<String, Object>>();
        for (LaoBPaylog payLog : payLogList) {
            HashMap<String, Object> localMap = new HashMap<String, Object>();
            localMap = getReflectMap(payLog);
            if (localMap.get("channelCustId") != null) {
                localMap.put("channelCustId", lMap.get(localMap.get("channelCustId")));
            }
            resList.add(localMap);
        }
        int count = laoBPaylogExtDao.countByCustId(paraMap);
        resMap.put("resList", resList);
        resMap.put("count", count);
        return resMap;
    }

    @Override
    public Map<String, Object> getBillResList(Long custId, String cyc, int start, int end) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        Map<String, Object> paraMap = generateCyc(cyc);
        List<LaoCustomerPo> custList = laoCustomerExtDao.queryAgent(null);
        HashMap<Long, Object> lMap = new HashMap<Long, Object>();
        for (LaoCustomerPo laoCustomerPo : custList) {
            lMap.put(laoCustomerPo.getCustId(), laoCustomerPo.getCustName());
        }
        paraMap.put("start", start);
        paraMap.put("end", end);
        paraMap.put("custId", custId);
        List<LaoBillResult> billResList = laoBillResultExtDao.selectByCustId(paraMap);
        List<HashMap<String, Object>> resList = new ArrayList<HashMap<String, Object>>();
        for (LaoBillResult billRes : billResList) {
            HashMap<String, Object> localMap = new HashMap<String, Object>();
            localMap = getReflectMap(billRes);
            if (localMap.get("channelCustId") != null) {
                localMap.put("channelCustId", lMap.get(localMap.get("channelCustId")));
            }
            resList.add(localMap);
        }
        int count = laoBillResultExtDao.countByCustId(paraMap);
        resMap.put("resList", resList);
        resMap.put("count", count);
        return resMap;
    }

    public HashMap<String, Object> getReflectMap(Object object) {
        HashMap<String, Object> resMap = new HashMap<String, Object>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                resMap.put(field.getName(), field.get(object));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException(
                        "get " + object.getClass().getSimpleName() + " Field " + field.getName() + " error");
            }
        }
        return resMap;
    }

    /**
     * 根据账期年月算出时间范围 精确到秒
     * 
     * @param cyc 格式：年/月
     * @return
     */
    @Override
    public HashMap<String, Object> generateCyc(String cyc) {
        int year = Integer.valueOf(cyc.split("/")[0]);
        int month = Integer.valueOf(cyc.split("/")[1]);
        if (month == 1) {
            year = year - 1;
            month = 12;
        } else {
            month = month - 1;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        cal.set(Calendar.YEAR, Integer.valueOf(year));
        cal.set(Calendar.MONTH, Integer.valueOf(month));
        cal.set(year, month, 1, 0, 0, 0);
        HashMap<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("startDate", cal.getTime());
        cal.set(year, month, 1, 23, 59, 59);
        cal.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        resMap.put("endDate", cal.getTime());
        return resMap;
    }

    /**
     * 扣费接口
     * 
     * @param paraMap
     */
    @Override
    public int charge(HashMap<String, Object> paraMap) {       
            Long userId = null;
            Long tradeId = null;
            Long goodsId = null;
            short tradeTypeCode = Short.valueOf(paraMap.get("tradeTypeCode").toString());
            Long fee = Long.valueOf(paraMap.get("fee").toString());
            Long discntFee = Long.valueOf(paraMap.get("discntFee").toString());
            Long realFee = Long.valueOf(paraMap.get("realFee").toString());
            Long payTag = Long.valueOf(paraMap.get("payTag").toString());
            Long custId = Long.valueOf(paraMap.get("channelCustId").toString());
            Long recvFee = Long.valueOf(paraMap.get("recvFee").toString());
            if (paraMap.get("userId") != null) {
                userId = Long.valueOf(paraMap.get("userId").toString());
            }
            if (paraMap.get("tradeId") != null) {
                tradeId = Long.valueOf(paraMap.get("tradeId").toString());
            }
            if (paraMap.get("goodsId") != null) {
                goodsId = Long.valueOf(paraMap.get("goodsId").toString());
            }

            if (payTag == 0) {
                recvFee = 0L;
            }
            Integer paymentOp = 10004;
            Short payFeeModeCode = 1;
            String feecntTag = "0";
            String cancelTag = "0";
            Long cashTag = 0L;
            LaoFAcctdeposit deposit = laoFAcctdepositExtDao.selectByCustId(custId);
            if (deposit == null) {
                logger.info("custId=" + custId + "没有账本,创建余额为0的默认账本");
                // TODO 创建一个默认账本
                deposit = creatAct(custId, 0L, cancelTag);
            }
            if (payTag == 2 || payTag == 0) {
                cashTag = Long.valueOf(deposit.getCashTag());
                feecntTag = cashTag == 0 ? "0" : "2";
            }
            // end
            LaoBPaylog payLog = new LaoBPaylog();
            payLog.setDiscntFee(discntFee);
            payLog.setUserId(userId);
            payLog.setFee(fee);
            payLog.setRealFee(realFee);
            payLog.setTradeTypeCode(tradeTypeCode);
            payLog.setCancelTag(cancelTag);
            payLog.setChannelCustId(custId);
            payLog.setRecvTime(Calendar.getInstance().getTime());
            Long payLogId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.PAYLOG_ID));
            payLog.setChargeId(payLogId);
            payLog.setFeecntTag(feecntTag);
            payLog.setPaymentOp(paymentOp);
            payLog.setRecvFee(recvFee);
            payLog.setPayFeeModeCode(payFeeModeCode);
            payLog.setPayTag(paraMap.get("payTag").toString());
            logger.info("---tradeId====" + tradeId);
            payLog.setTradeId(tradeId);
            payLog.setGoodsId(goodsId);
            laoBPaylogDao.insert(payLog);

            // 如果未缴费
            if (payTag == 0) {
                if ((deposit.getDepositMoney() - realFee) >= 0) {
                    // deposit.setDepositMoney(deposit.getDepositMoney() -
                    // realFee); // 扣减的时候
                    // laoFAcctdepositDao.updateByPrimaryKey(deposit);
                    long depositBalance = deposit.getDepositMoney();
                    deposit.setDepositMoney(-realFee); // 扣减的时候
                    laoFAcctdepositExtDao.updateByPrimaryKey(deposit);
                    LaoBAccesslog accesslog = new LaoBAccesslog();
                    accesslog.setAccessId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.ACCESSLOG_ID)));
                    accesslog.setChargeId(payLogId);
                    accesslog.setUpdateTime(Calendar.getInstance().getTime());
                    accesslog.setAcctBalanceId(deposit.getAcctBalanceId());
                    accesslog.setChargeId(payLog.getChargeId());
                    accesslog.setMoney(realFee);
                    accesslog.setOldBalance(depositBalance);
                    accesslog.setNewBalance(depositBalance - realFee);
                    laoBAccesslogDao.insert(accesslog);
                    sendMsg(custId,depositBalance - realFee);
                } else {
                    logger.info("custId:" + custId + " userId:" + userId + " DepositMoney:" + deposit.getDepositMoney()
                            + " realFee:" + realFee+" Money is not enough!");
                    return 1;
                }
            }
            // 插入数据到userFeeInfo 这张表
           // logger.info("fee=" + fee + "recvFee=" + recvFee + "payTag=" + payTag + "tradeId=" + tradeId);
          //  userFeeInfoService.insert(fee.toString(), recvFee.toString(), payTag.toString(), tradeId);        
        return 0;
    }

    @Override
    public List<Map<String, Object>> selectComCustByName(String param) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<LaoCustomerPo> customers = laoCustomerExtDao.queryComCustByName(param);
        for (LaoCustomerPo po : customers) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", po.getCustName());
            map.put("id", po.getCustId());
            list.add(map);
        }
        return list;
    }

    @Override
    public List<HashMap<String, Object>> queryBillResByCustId(HashMap<String, Object> paraMap) {
        List<LaoCustomerPo> custList = laoCustomerExtDao.queryAgent(null);
        HashMap<Long, Object> lMap = new HashMap<Long, Object>();
        for (LaoCustomerPo laoCustomerPo : custList) {
            lMap.put(laoCustomerPo.getCustId(), laoCustomerPo.getCustName());
        }

        List<LaoBillResult> results = laoBillResultExtDao.selectByCustIdAndCashBackTag(paraMap);
        List<HashMap<String, Object>> resList = new ArrayList<HashMap<String, Object>>();
        for (LaoBillResult laoBillResult : results) {
            HashMap<String, Object> localMap = new HashMap<String, Object>();
            localMap = getReflectMap(laoBillResult);
            if (localMap.get("channelCustId") != null) {
                localMap.put("channelCustId", lMap.get(localMap.get("channelCustId")));
            }
            resList.add(localMap);
        }
        return resList;
    }

    @Override
    public int coutBillResByCustId(HashMap<String, Object> paraMap) {
        return laoBillResultExtDao.countBillResByCustId(paraMap);
    }

    @Override
    public int countRulesByCustId(Long custId) {
        return laoRuleDefExtDao.countRulesByCustId(custId);
    }

    @Override
    public int countRulesByGroupId(Long groupId) {
        return laoRuleDefExtDao.countRulesByGroupId(groupId);
    }

    @Override
    public int paymentOut(String fee, Long custId, Long accId) {
        // 判断是否有账本 fee 单位：分
        LaoFAcctdeposit deposit = laoFAcctdepositExtDao.selectByCustId(custId);
        StringBuffer paramsNew = new StringBuffer();
        if (deposit != null) {
            paramsNew.append(custId).append(",").append(fee).append(",").append(deposit.getCashTag());
        } else {
            // 新建账本
            StringBuffer params = new StringBuffer();
            params.append(custId).append(",").append("2").append(",")// 手工结算标识
                    .append("10000001").append(",")// 规则ID
                    .append("0").append(",")// 账本余额
                    .append("0").append(",")// 发票金额
                    .append("0");// 已打发票金额
            try {
                this.saveRules(params.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            paramsNew.append(custId).append(",").append(fee).append(",").append("2");
        }
        return this.payment(paramsNew.toString(), accId);
    }

    @Override
    public int saveBalRules(String param) throws ParseException {
        String[] datas = param.split(";");
        if(redisOperService.getRedisObj("BALRUL".concat(datas[5]), BalAlarmDto.class)!=null){
            return -1;
        }
        //优先级，阈值，规则名称，邮箱地址，电话号码
        BalAlarmDto balAlarmDto=new BalAlarmDto(Long.valueOf(datas[4]),Long.valueOf(datas[1]),datas[0],datas[2],datas[3],Long.valueOf(datas[5]));      
        redisOperService.setRedisObj("BALRUL".concat(datas[5]), balAlarmDto);
        return 0;
    }
    
    @Override
    public BalAlarmDto getBalRules(Long custId) throws ParseException {   
        logger.info("RemainServiceImpl getBalRules custId:"+ custId);    
        return redisOperService.getRedisObj("BALRUL".concat(String.valueOf(custId)), BalAlarmDto.class);
    }
    @Override
    public Long delBalRules(Long custId) throws ParseException {      
        return redisOperService.delRedisObj("BALRUL".concat(String.valueOf(custId)));
    }
    
    public int sendMsg(Long channelCustId,Long curBal) {
        BalAlarmDto balAlarmDto= redisOperService.getRedisObj("BALRUL".concat(channelCustId.toString()), BalAlarmDto.class);
        if(balAlarmDto==null || curBal>=balAlarmDto.getBalRemain()){
            return 0;
        }   
        logger.info("RemainServiceImpl sendMsg RuleName:"
                + balAlarmDto.getRuleName()+" curBal:"+curBal+" BalRemain:"+balAlarmDto.getBalRemain());    
            MailDto mail = null;
            Map<String, Object> map = new HashMap<String, Object>();
            String emailReceiver = balAlarmDto.getEmail();
            String smsReceiver = balAlarmDto.getTelphone();          
            logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&进入余额告警");               
            // 获取短信模板  
            map.put("templateId", "6");// 集群网的短信模板ID
            map.put("sendWay", "1000");// 集群网的短信模板ID  
            map.put("curBal", curBal);//当前余额
            map.put("BalRemain", balAlarmDto.getBalRemain());//余额阈值
            map.put("number", smsReceiver);           
            String context=this.getTempletContent(map);
            boolean smsSend =sendMessageService.smsSend(map); 
            String[] emailReceivers = emailReceiver
                        .split(",");
            boolean bmail=false;
            for (int index = 0; index < emailReceivers.length; index++) {
                logger.info("&&&&&&&&&&&mail Receiver:"+emailReceivers[index]);   
                mail = new MailDto(emailReceivers[index],"余额告警", context);
                bmail=mailUtil.send(mail);
            }
            logger.info("&&&&&&&&&&&mail result:"+bmail); 
            LaoBalAlmLogPo logPo = new LaoBalAlmLogPo();
            logPo.setAlarmId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BALALARM_ID)));
            logPo.setRuleName(balAlarmDto.getRuleName());
            logPo.setCurBal(curBal);
            logPo.setRemianbal(balAlarmDto.getBalRemain());
            logPo.setChannelCustId(channelCustId);
            logPo.setEmailTag(bmail?"0":"1");
            logPo.setSmsTag(smsSend?"0":"1");
            logPo.setDealTime(new Date());
            LaoBalAlmLogDao.insert(logPo);            
        return 0;
    }
    public String getTempletContent(Map<String, Object> map) {

        int templateId = Integer.parseInt((String) map.get("templateId"));
        LaoSmsTmpl laoSmsTmpl = laoSmsTmplMapperDao
                .selectByPrimaryKey(templateId);
        if (laoSmsTmpl == null) {
            return "";
        }
        // 查询变量参数表
        List<LaoValParam> listValParam = laoValParamExtMapperDao
                .selectAllParamsByRuleId((long) templateId);
        String content = laoSmsTmpl.getTmplContext();
        try {
            if (listValParam == null) {
                return "";
            } else {
                for (int i = 0; i < listValParam.size(); i++) {
                    LaoValParam laoVal = listValParam.get(i);
                    String paramValue = map.get(laoVal.getParaValue())
                            .toString();
                    content = content.replaceAll(laoVal.getParaName(),
                            paramValue);
                }
            }
        } catch (Exception e) {
            logger.info(">>>>>>>>>>>>获取模板失败");
            content = "";
        }
        return content;
    }

}
