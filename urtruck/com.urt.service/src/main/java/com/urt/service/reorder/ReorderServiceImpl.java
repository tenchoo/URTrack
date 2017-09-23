package com.urt.service.reorder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.net.aso.f;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.interfaces.reorder.ReorderService;
import com.urt.mapper.LaoOperatordealLogMapper;
import com.urt.mapper.ext.LaoCustConcatPoExtMapper;
import com.urt.mapper.ext.LaoOperatordealLogExtMapper;
import com.urt.msgProducter.trade.ReorderProducer;
import com.urt.po.LaoOperatordealLog;

@Service("reorderService")
public class ReorderServiceImpl implements ReorderService{
	@Autowired
	private LaoOperatordealLogExtMapper dao;
	@Autowired
	private ReorderProducer reorderProducer;
	@Override
	public void sendMsg() {
		// TODO Auto-generated method stub
		List<LaoOperatordealLog> failOrders = dao.getFailOrder();
		for(LaoOperatordealLog failOrder:failOrders){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("requestId", failOrder.getRequestId());
			map.put("tradeId", failOrder.getTradeId());
			map.put("iccid", failOrder.getIccid());
			reorderProducer.sendMessage(map);
		}
	}

}
