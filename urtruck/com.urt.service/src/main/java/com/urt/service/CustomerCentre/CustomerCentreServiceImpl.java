package com.urt.service.CustomerCentre;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.MailDto;
import com.urt.dto.Remain.LaoBAccesslogDto;
import com.urt.interfaces.CustomerCentre.CustomerCentreService;
import com.urt.mapper.LaoBAccesslogMapper;
import com.urt.mapper.LaoFAcctdepositChangeMapper;
import com.urt.mapper.LaoFAcctdepositMapper;
import com.urt.mapper.ext.LaoBAccesslogExtMapper;
import com.urt.mapper.ext.LaoBPaylogExtMapper;
import com.urt.mapper.ext.LaoBillResultExtMapper;
import com.urt.mapper.ext.LaoFAcctdepositExtMapper;
import com.urt.modules.nosql.redis.JedisClusterFactory;
import com.urt.po.LaoBAccesslog;
import com.urt.po.LaoCustomerPo;
import com.urt.po.LaoFAcctdeposit;
import com.urt.po.LaoFAcctdepositChange;
import com.urt.service.ofo.BusinessServiceImpl;
import com.urt.service.util.MailUtil;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
import com.urt.watch.RedisAPI;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

@Service("customerCentreService")
public class CustomerCentreServiceImpl implements CustomerCentreService {

	@Autowired
	private LaoBPaylogExtMapper rechargeMapper;
	@Autowired
	private LaoBillResultExtMapper billResultMapper;
	@Autowired
	private MailUtil mailUtil;
	@Autowired
	private LaoFAcctdepositExtMapper laoFAcctdepositExtDao;
	@Autowired
	private LaoFAcctdepositMapper laoFAcctdepositDao;
	@Autowired
	private LaoBAccesslogMapper laoBAccesslogDao;
	
	@Autowired
	private LaoBAccesslogExtMapper laoBAccesslogExtDao;
	
	@Autowired
	private LaoFAcctdepositChangeMapper depositChangeMapper;
	
	@Autowired
	protected JedisClusterFactory jedisCluster;

	private static Logger logger = Logger.getLogger(CustomerCentreServiceImpl.class);

	@Override
	public Map<String, Object> queryByCustId(Long custId) {

		return null;
	}

	@Override
	public Map<String, Object> queryCurrentMonthSumitMoneyByCustId(Long custId) {

		Map<String, Object> hashMap = new HashMap<String, Object>();

		Long countMoneyByCustId2 = rechargeMapper.countMoneyByCustId(custId);

		Double countMoneyByCustId = Double.valueOf(countMoneyByCustId2 == null ? 0 : countMoneyByCustId2);
		// 查询当月已返现的总数
		Long returnMoney = billResultMapper.currentMonthReturnMoneyByCustId(custId);
		Double currentMonthReturnMoney = Double.valueOf(returnMoney == null ? 0 : returnMoney);
		hashMap.put("currentMonthSubmitMoney", countMoneyByCustId);
		hashMap.put("currentMonthReturnMoney", currentMonthReturnMoney);
		return hashMap;
	}
    
	
	
	
	
	@Override
	public Map<String, Object> queryPage(Long custId, int pageNo, int pageSize,String accessTag) {
		
		Map<String, Object> resultMap = new HashMap<>();
		//根据custId 查询充值记录表
		//laoBAccesslog.set
		Map<Object, Object> pageMap = new HashMap<>();
		
		pageMap.put("pageSize", pageSize);
		pageMap.put("pageNo", pageNo);
		pageMap.put("custId", custId);
		pageMap.put("accessTag",accessTag);
		List<Map<String, Object>> pageRsult = new ArrayList<Map<String,Object>>();
		Integer pageCount = new Integer(0);
		if("0".equals(accessTag) || "3".equals(accessTag)){//0和3表示充值和提现Lao_b_accesslog查询表
			pageRsult=laoBAccesslogExtDao.queryPage(pageMap);
			pageCount = laoBAccesslogExtDao.pageCount(pageMap);
		}else if("1".equals(accessTag)){
			//Lao_b_Paylog消费日志表
		}else{
			//lao_bill_result查询返现日志表
		}
		
		// 查询数据
		resultMap.put("data", pageRsult);
		resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", pageCount);
		return resultMap;
		
	}
	
	@Override
	public Map<String, Object> offLineReserveRecharge(String rechargeType, Long money, LaoSsAccountDto user) {

		Map<String, Object> resultMap=null;
		JedisPool pool = RedisAPI.getPool();
		Jedis jedis = pool.getResource();
		String keyString="money";
		// 根据CustId去查询账本ID
		try{
		LaoFAcctdeposit laoFAcctdeposit = laoFAcctdepositExtDao.selectByCustId(user.getCustId());
		//加锁
		if(jedisCluster.getObject().setnx(keyString,String.valueOf(laoFAcctdeposit.getDepositMoney()))==1){
			if (jedisCluster.getObject().expire(keyString, 3) != 1) {
				logger.error("加锁的key" + keyString+"---已失效");
				throw new RuntimeException("设置的锁已失效");
			}
			jedis.watch(keyString);  //监听加锁的key
			Transaction transaction = jedis.multi(); //开启事务
			//0  存  3  取
			String accessTag="".equals(rechargeType)? "0":"3";
			resultMap = this.inssetOrUpdateBAccessLog("10000", accessTag, laoFAcctdeposit, null,
					user.getAcconutId().toString(),user.getCustId(), money);
			
			List<Object> result = transaction.exec();//提交事务
			
			if (result==null || result.isEmpty()) {
				logger.info("目前账户不能被操作-------------");
				resultMap.put("result", false);
			}else{
				resultMap.put("result", true);
			}
		}
		}catch (Exception e){
			resultMap.put("result", false);
		}finally {
			jedis.unwatch();  //释放监听
			RedisAPI.returnResource(pool,jedis);
			// 邮件通知财务人员
			MailDto mail = new MailDto("shiyf3@lenovo.com", "线下充值充值待确认处理", "");
			boolean send = mailUtil.send(mail);
			logger.info("邮件发送结果-----" + send);
			
		}
		return resultMap;
	}


	/**
	 * 
	 * @param operationType
	 *            代表操作类型 10000 插入操作 其他 更新
	 * @param accessTag
	 *            存取标识 0 标识存 3 表示取
	 * @param LaoFAcctdeposit
	 *            账本对象
	 * @param accessId
	 *            存取日志表主键 做插入操作 参数为null,更新操作不能为空
	 * @param accountId
	 *            操作人员Id
	 * @param rechargeMoney
	 *            要操作的钱
	 * @return
	 */
	public Map<String, Object> inssetOrUpdateBAccessLog(String operationType, String accessTag,
			LaoFAcctdeposit laoFAcctdeposit, Long accessId,  String accountId ,Long custId,Long rechargeMoney) {

		// 生成预存取款日志表
		LaoBAccesslog laoBAccesslog = new LaoBAccesslog();
		// 10000 存取,生成存取日志表
		if ("10000".equals(operationType) && null == accessId) {
			accessId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.PAYLOG_ID));
			laoBAccesslog.setAccessId(accessId);
			laoBAccesslog.setAcctBalanceId(laoFAcctdeposit.getAcctBalanceId());
			laoBAccesslog.setOldBalance(laoFAcctdeposit.getDepositMoney());
			laoBAccesslog.setMoney(Long.valueOf(rechargeMoney));
			laoBAccesslog.setRecvOperId(accountId);
			// 存款 取款标识
			laoBAccesslog.setAccessTag(accessTag);
			if ("0".equals(accessTag)) {
				laoBAccesslog.setNewBalance(laoFAcctdeposit.getDepositMoney() + Long.valueOf(rechargeMoney));
			}else{
				laoBAccesslog.setNewBalance(laoFAcctdeposit.getDepositMoney() - Long.valueOf(rechargeMoney));
			}
			laoBAccesslog.setCreateDate(new Date());
            laoBAccesslog.setChannelCustId(custId);
			laoBAccesslog.setCheckedTag(Short.valueOf("0")); // 待审核
			int insertSelective = laoBAccesslogDao.insertSelective(laoBAccesslog);
		} else {
			laoBAccesslog.setAccessId(accessId);
			laoBAccesslog.setUpdateTime(new Date());
			// 审核结果
			laoBAccesslog.setCheckedTag(Short.valueOf("1"));  // 1已审核

			int updateResult = laoBAccesslogDao.updateByPrimaryKey(laoBAccesslog);
			if (updateResult > 0) {
				// 插入到账本变化表
				if (this.insertAcctdepositChange(laoFAcctdeposit, rechargeMoney, accessId, accessTag, accountId) > 0 ? true
						: false) {
					// 更新账本余额
					LaoFAcctdeposit deposit = new LaoFAcctdeposit();
					if ("0".equals(accessTag)) {
						deposit.setDepositMoney(laoFAcctdeposit.getDepositMoney() + rechargeMoney);
						
					}else if ("3".equals(accessTag)) {
						deposit.setDepositMoney(laoFAcctdeposit.getDepositMoney()-rechargeMoney);
					}
					deposit.setAcctBalanceId(laoFAcctdeposit.getAcctBalanceId());
					deposit.setUpdateTime(new Date());
					laoFAcctdepositDao.updateByPrimaryKeySelective(laoFAcctdeposit);
				}

			}
		}
		return null;
	}

	/**
	 * 账本变化表
	 * 
	 * @param laoFAcctdeposit
	 *            账本对象
	 * @param rechargeMoney
	 *            更新的钱数(单位分)
	 * @param runningIdId
	 *            流水号 (充值流水,消费流水,返现流水,提现流水)
	 * @param changeType
	 *            账本变化类型 0 充值 1 消费 2 返现 3 提现
	 * @param openID
	 *            操作id
	 */
	private int insertAcctdepositChange(LaoFAcctdeposit laoFAcctdeposit, Long rechargeMoney, Long runningId,
			String changeType, String openId) {

		LaoFAcctdepositChange accountChange = new LaoFAcctdepositChange();

		Long accountChangeId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.PAYLOG_ID));

		accountChange.setAccountChangeId(accountChangeId);
		accountChange.setChangeType(Short.valueOf(changeType));

		accountChange.setCreateDate(new Date());
		accountChange.setOldMoney(laoFAcctdeposit.getDepositMoney());
		
		accountChange.setUpdateMoney(rechargeMoney);
		accountChange.setOperId(openId);
		
		
		if ("0".equals(changeType)) {
			accountChange.setAccessId(runningId);
			accountChange.setNewMoney(laoFAcctdeposit.getDepositMoney()+rechargeMoney);
		} else if ("1".equals(changeType)) {
			accountChange.setChargeId(runningId);
			accountChange.setNewMoney(laoFAcctdeposit.getDepositMoney()-rechargeMoney);
		} else if ("2".equals(changeType)) {
			accountChange.setBalanceId(runningId);
			accountChange.setNewMoney(laoFAcctdeposit.getDepositMoney()+rechargeMoney);
		} else if ("3".equals(changeType)) {
			accountChange.setMoveId(runningId);
			accountChange.setNewMoney(laoFAcctdeposit.getDepositMoney()-rechargeMoney);
		}  
		

		return depositChangeMapper.insertSelective(accountChange);
	}

	@Override
	public LaoBAccesslogDto selectByPrimaryKey(Long accessId) {
		LaoBAccesslog laoLog = laoBAccesslogExtDao.selectByPrimaryKey(accessId);
		LaoBAccesslogDto laoDto=(LaoBAccesslogDto)ConversionUtil.po2dto(laoLog, LaoBAccesslogDto.class);
		 return laoDto;
	}


}
