package com.urt.service.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.common.util.ConversionUtil;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.interfaces.reports.CardCycleService;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.mapper.ext.LaoUserSvcstateExtMapper;
import com.urt.po.LaoUser;
@Service("cardCycleServiceImpl")
@Transactional(propagation = Propagation.REQUIRED)
public class CardCycleServiceImpl implements CardCycleService{
	private static final Logger log = Logger.getLogger(CardCycleServiceImpl.class);
	@Autowired
	private LaoUserExtMapper userExtMapper;
	@Autowired
	private LaoUserSvcstateExtMapper userSvcstateExtMapper;
	@Override
	public List<Map<String, Object>> queryCardsCycle(LaoUserDto userDto) {
		List<Map<String,Object>> stateList=new ArrayList<Map<String,Object>>();
		Map<String,Object> stateMap1=new HashMap<String, Object>();
		LaoUser laoUserPo = (LaoUser) ConversionUtil.dto2po(userDto, LaoUser.class);
		String instoreDate=userExtMapper.queryInStoreDate(laoUserPo);
		if(instoreDate!=null){
			stateMap1.put("STATENAME", "入库");
			stateMap1.put("STATEDATE",instoreDate);
			stateList.add(stateMap1);
		}
		log.info("stateList:"+stateList);
        List<Map<String, Object>> stateCycle = userSvcstateExtMapper.queryCardCycle(laoUserPo);
        log.info("stateCycle:"+stateCycle);
        if(stateCycle!=null&&stateCycle.size()>0)
           stateList.addAll(stateCycle);
		return stateList;
	}
	@Override
	public List<Map<String, Object>> selectCountByState(LaoUserDto userDto) {
		LaoUser laoUserPo = (LaoUser) ConversionUtil.dto2po(userDto, LaoUser.class);
		return userExtMapper.selectCountByState(laoUserPo);
	}
}
