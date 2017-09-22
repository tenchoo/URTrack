package com.urt.test.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.urt.common.util.Page;
import com.urt.mapper.ChargeOrderMapper;
import com.urt.mapper.FlowConfigMapper;
import com.urt.mapper.IccidDonateStepMapper;
import com.urt.mapper.IccidLibMapper;
import com.urt.mapper.IccidLibraryMapper;
import com.urt.mapper.IccidLogMapper;
import com.urt.mapper.ImeiLibraryMapper;
import com.urt.mapper.LaoUserStateDdMapper;
import com.urt.mapper.LaoUserSvcstateMapper;
import com.urt.mapper.OperatorsMapper;
import com.urt.mapper.RealNameVerifyMapper;
import com.urt.mapper.TariffPlanMapper;
import com.urt.mapper.UserInfoMapper;
import com.urt.mapper.WhiteListMapper;
import com.urt.mapper.ext.ChargeOrderExtMapper;
import com.urt.mapper.ext.IccidLibExtMapper;
import com.urt.mapper.ext.LaoBPaylogExtMapper;
import com.urt.mapper.ext.LaoCustomerPoExtMapper;
import com.urt.mapper.ext.LaoOperatorsBillExtMapper;
import com.urt.mapper.ext.LaoOperatorsCycleExtMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.mapper.ext.LaoUserOperatorPlanExtMapper;
import com.urt.mapper.ext.LaoUserbillsDetailExtMapper;
import com.urt.mapper.ext.ServiceStatusChgExtMapper;
import com.urt.mapper.ext.ServiceStatusExtMapper;
import com.urt.mapper.ext.TradeFeeSubExtMapper;
import com.urt.po.LaoBPaylog;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/applicationContext-mybatis.xml"})
public class TestUntil {
	@Autowired
	ChargeOrderMapper chargeOrderDao;
	@Autowired
	ChargeOrderExtMapper chargeOrderExtDao;
	@Autowired
	WhiteListMapper whiteDao;
	@Autowired
	FlowConfigMapper flowDao;
	@Autowired
	IccidDonateStepMapper iccidDao;
	@Autowired
	IccidLibraryMapper libraryDao;
	@Autowired
	private IccidLogMapper iccidLogDAO;
	@Autowired
	private ImeiLibraryMapper imeDAO;
	@Autowired
	private RealNameVerifyMapper RealNameDAO;
	@Autowired
	private TariffPlanMapper tariffDAO;
	@Autowired
	private UserInfoMapper userInfoDAO;
	
	@Autowired
	private IccidLibMapper libDao;
	@Autowired
	private IccidLibExtMapper libExtDao;
	
	@Autowired
	private ServiceStatusChgExtMapper seeDAO;
	
	@Autowired
	private OperatorsMapper   operatorsDAO;
	
	@Autowired
	private LaoUserSvcstateMapper laoUserSvcDAO;
	
	@Autowired
	private LaoUserOperatorPlanExtMapper laoUserOperatorPlanExtDAO;
	
	@Autowired
	private ServiceStatusExtMapper serviceStatusExtDAO;
	@Autowired
	private LaoUserExtMapper laoUserExtMapper;
	
	@Autowired
	private TradeFeeSubExtMapper tradeFeeSubExtMapper;
	
	@Autowired
	private LaoOperatorsCycleExtMapper laoOperatorsCycleExtMapper;
	
	@Autowired
	private LaoOperatorsBillExtMapper laoOperatorsBillExtMapper;
	
	@Autowired
	private LaoUserbillsDetailExtMapper laoUserbillsDetailExtMapper;
	
	@Autowired
	private LaoCustomerPoExtMapper laoCustomerPoExtDAO;
	
	@Autowired
	private LaoBPaylogExtMapper laoBPaylogExtDAO;
	@Autowired
	private LaoUserStateDdMapper laoUserStateDdMapper;
	@Test
	public void testPerson(){
		/*Page<LaoBPaylog> page = new Page<LaoBPaylog>();
		page.setPageNo(1);
		page.setPageSize(10);
		Map<String, Object> params = new HashMap<String, Object>();
		LaoBPaylog payLog = new LaoBPaylog();
		payLog.setChannelCustId(3071134450000004l);
		payLog.setUserId(1002002370000550l);
		payLog.setPaymentOp(10000);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			payLog.setRecvTime(dateFormat.parse("2016-12-8 00:00:00"));
			payLog.setFeecntTime(dateFormat.parse("2016-12-8 23:59:59"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		params.put("result", payLog);
		page.setParams(params);*/
//		List<Map> revenue = laoBPaylogExtDAO.revenue(page);
//		System.out.println(revenue);
		
		
//		System.out.println(laoOperatorsCycleExtMapper.getOperatorsCycle(201703,"1","0"));;
//		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
//		Calendar calendar = Calendar.getInstance();
//		int acceptMonth = calendar.get(Calendar.MONTH);
//		
//		System.out.println(format.format(new Date()));
		
//		laoUserbillsDetailExtMapper.getUserBillDetailByChargeId("1000000000000313");
//		System.out.println(laoUserbillsDetailExtMapper.countTotal(null, 201704, null));
		
//		System.out.println(laoUserExtMapper.getIccid("343"));
		
//		System.out.println(tradeFeeSubExtMapper.queryTradeFeeSubByCustId("3071055180057895", "2017-2-21",  "2017-2-24", 1, 10).size());
//		System.out.println(laoUserStateDdMapper.selectCardDetail((long)1).toString());
//		System.out.println(laoUserStateDdMapper.selectCardCount((long)1));
//		System.out.println(laoUserStateDdMapper.selectCardStateCount((long)1));
	}
	
}
