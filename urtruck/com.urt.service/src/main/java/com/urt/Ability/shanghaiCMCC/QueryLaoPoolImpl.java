package com.urt.Ability.shanghaiCMCC;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.interfaces.ShangHaiCMC.QueryLaoPool;
import com.urt.mapper.ext.LaoPoolExtMapper;
import com.urt.po.LaoPool;
/**
 * 查询流量池ID
 * @author h
 *
 */
@Service("queryLaoPool")
public class QueryLaoPoolImpl implements QueryLaoPool {
	@Autowired
	private LaoPoolExtMapper laoPoolExtDao;
	@Override
	public List<String> queryPool() {
		List<LaoPool> laoPools = laoPoolExtDao.findLaoPool();
		List<String> pooIds = new ArrayList<>();
		for (LaoPool laoPool : laoPools) {
			if(laoPool.getPoolId() != null && laoPool.getPoolId() !=""){
				pooIds.add(laoPool.getPoolId());
			}
		}
		return pooIds;
	}

}
