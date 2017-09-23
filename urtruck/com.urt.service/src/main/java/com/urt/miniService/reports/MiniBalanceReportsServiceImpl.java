package com.urt.miniService.reports;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.reports.LaoAcctDepositMmDto;
import com.urt.mapper.ext.LaoAcctDepositMmPoExtMapper;
import com.urt.po.LaoAcctDepositMmPo;
@Service("minBalanceReportsImpl")
public class MiniBalanceReportsServiceImpl {
	@Autowired
	LaoAcctDepositMmPoExtMapper dao;
	/**
	 * 功能描述：余额中心报表分页查询
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryPage(LaoAcctDepositMmDto dto, int pageNo, int pageSize) {
		Page<LaoAcctDepositMmPo> page = new Page<LaoAcctDepositMmPo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		String indate = dto.getInDate().toString();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
		Integer indate2 = 0;
		try {
			Date date = sf.parse(indate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH,-1);
			Date date2 = cal.getTime();
			String format = sf.format(date2);
			 indate2 = Integer.parseInt(format);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		LaoAcctDepositMmPo po = (LaoAcctDepositMmPo) ConversionUtil.dto2po(dto, LaoAcctDepositMmPo.class);
		params.put("param", po);
		params.put("indate",indate2);
		page.setParams(params);
		dao.queryPage(page);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("pageDate", page);
		return resultMap;
	}
}
