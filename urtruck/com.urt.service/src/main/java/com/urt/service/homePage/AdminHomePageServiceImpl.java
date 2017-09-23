package com.urt.service.homePage;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.interfaces.homePage.AdminHomePageService;
import com.urt.mapper.ext.LaoSsStaticPoExtMapper;
import com.urt.mapper.ext.LaoTrafficMmExtMapper;

@Service("adminHomePageService")
@Transactional(propagation = Propagation.REQUIRED)
public class AdminHomePageServiceImpl implements AdminHomePageService{

	private static final Logger log=Logger.getLogger(AdminHomePageServiceImpl.class);

	@Autowired
	private LaoTrafficMmExtMapper laoTrafficMmExtDao;
	@Autowired
	private LaoSsStaticPoExtMapper laoSsStaticPoExtDao;
	
	// 查询本月流量使用量前五名
	@Override
	public List<Map<String, Object>> selectTop5ByUseFlow() {
		List<Map<String, Object>> list = laoTrafficMmExtDao.selectTop5ByUseFlow();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				double useCount = ((BigDecimal) map.get("USECOUNT")).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				map.put("USECOUNT",useCount);
			}
		}
		return list;
	}
	// 查询本月流量平均单卡使用量前五名
	@Override
	public List<Map<String, Object>> selectTop5ByAverageUseFlow() {
		List<Map<String, Object>> list = laoTrafficMmExtDao.selectTop5ByAverageUseFlow();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				double useCount = ((BigDecimal) map.get("USECOUNT")).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				map.put("USECOUNT",useCount);
			}
		}
		return list;
	}
	@Override
	public List<Map<String, Object>> selectTop5ByCustConsume() {
		
		return laoTrafficMmExtDao.selectTop5ByCustConsume();
	}
	@Override
	public List<Map<String, Object>> selectTop5ByAverageConsume() {
		
		return laoTrafficMmExtDao.selectTop5ByAverageConsume();
	}
	@Override
	public List<Map<String, Object>> selectTop5ByPersonal() {
		
		return laoTrafficMmExtDao.selectTop5ByPersonal();
	}
	@Override
	public List<Map<String, Object>> selectLast5ByPersonal() {
		
		return laoTrafficMmExtDao.selectLast5ByPersonal();
	}
	@Override
	public List<Map<String, Object>> selectCountByState() {
		
		return laoTrafficMmExtDao.selectCountByState();
	}
	@Override
	public List<Map<String, Object>> selectCountByOperators() {
		
		return laoTrafficMmExtDao.selectCountByOperators();
	}
	@Override
	public List<Map<String, Object>> selectTop10ByCust() {
		
		return laoTrafficMmExtDao.selectTop10ByCust();
	}
	@Override
	public List<Map<String, Object>> selectUserCountByYear() {

		return laoTrafficMmExtDao.selectUserCountByYear();
	}
	@Override
	public List<Map<String, Object>> selectCountByTrade() {
		
		return laoTrafficMmExtDao.selectCountByTrade();
	}
	@Override
	public List<Map<String, Object>> selectFlowCountByMonth() {
	
		return laoTrafficMmExtDao.selectFlowCountByMonth();
	}
	@Override
	public List<Map<String, Object>> selectComeByPayment() {
		
		return laoTrafficMmExtDao.selectComeByPayment();
	}
	@Override
	public List<Map<String, Object>> selectConsumeBy6Month() {
		
		return laoTrafficMmExtDao.selectConsumeBy6Month();
	}
	@Override
	public List<Map<String, Object>> queryUserIncomeByIntelligent(String date) {
		log.info("进入方法queryUserIncomeByIntelligent======================date:"+date);
		String date1 = "";
		String date2 = "";
		Date date3 = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(date));
			int year = calendar.get(Calendar.YEAR);    //获取年
			int month = calendar.get(Calendar.MONTH) + 1; 
			if (4 <= month) {
				date1 = year+"-04-01";
				date2 = (year+1)+"-04-01";
			} else {
				date1 = (year-1)+"-04-01";
				date2 = year+"-04-01";
			}
			date3 = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		log.info("queryUserIncomeByIntelligent开始查询 ===========================date1="+date1+"date2="+date2+"date3="+date3);
		List<Map<String, Object>> list = laoSsStaticPoExtDao.queryUserIncomeByIntelligent(date1, date2, date3);
		log.info("queryUserIncomeByIntelligent查询结果 ===========================list:"+list);
		return list;
	}
	@Override
	public Map<String, Object> queryZhiNengHuLian(String date) {
		log.info("进入方法queryZhiNengHuLian======================date="+date);
		String date1 = "";
		String date2 = "";
		Date date3 = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(date));
			int year = calendar.get(Calendar.YEAR);    //获取年
			int month = calendar.get(Calendar.MONTH) + 1; 
			if (4 <= month) {
				date1 = year+"-04-01";
				date2 = (year+1)+"-04-01";
			} else {
				date1 = (year-1)+"-04-01";
				date2 = year+"-04-01";
			}
			date3 = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		log.info("queryZhiNengHuLian开始查询 ===========================date1="+date1+"date2="+date2+"date3="+date3);
		List<Map<String, Object>> list = laoSsStaticPoExtDao.queryZhiNengHuLian(date1, date2, date3);
		log.info("queryZhiNengHuLian查询结果 ===========================list:"+list);
		Map<String, Object> map = null;
		if (list != null&&list.size()>0) {
			map = list.get(0);
		}
		log.info("queryZhiNengHuLian返回结果 ===========================map:"+map);
		return map;
	}

}
