package com.urt.service.grpnet;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.grpnet.GrpnetImpbillDto;
import com.urt.interfaces.grpnet.GrpnetImpbillService;
import com.urt.mapper.ext.DItemExtMapper;
import com.urt.mapper.ext.GrpnetImpbillExtMapper;
import com.urt.msgProducter.trade.BatchFtpImportProducer;
import com.urt.po.DItem;
import com.urt.po.GrpnetImpbill;

@Service("grpnetImpbillService")
@Transactional(propagation = Propagation.REQUIRED)
public class GrpnetImpbillServiceImpl implements GrpnetImpbillService{

	@Autowired
	private GrpnetImpbillExtMapper grpnetImpbillExtDao;
	@Autowired
	private DItemExtMapper dItemExtDao;
	@Autowired
	private BatchFtpImportProducer batchFtpImportProducer;
	
	@Override
	public int batchInsert(List<GrpnetImpbillDto> list) {
		List<GrpnetImpbill> listNew = new ArrayList<GrpnetImpbill>();
		GrpnetImpbill recordNew = null;
		for (int i = 0; i < list.size(); i++) {
			GrpnetImpbillDto record = list.get(i);
			recordNew = new GrpnetImpbill();
			try {
				BeanUtils.copyProperties(recordNew, record);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			listNew.add(recordNew);
		}
		return grpnetImpbillExtDao.batchInsert(listNew);
	}

	@Override
	public Map<String, Integer> selectAllMapDtem() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		List<DItem> list = dItemExtDao.selectAll();
		DItem record = null;
		for (int i = 0; i < list.size(); i++) {
			record = list.get(i);
			map.put(record.getItemDesc(), record.getItemId());
		}
		return map;
	}

	@Override
	public void sendUserMsg(List<List<GrpnetImpbillDto>> list) {
		batchFtpImportProducer.sendMessage(list);
	}
}
