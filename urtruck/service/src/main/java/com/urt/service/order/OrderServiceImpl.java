package com.urt.service.order;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.dto.LaoOperatordealLogDto;
import com.urt.interfaces.http.OrderProductService;
import com.urt.interfaces.order.OrderService;
import com.urt.mapper.ext.LaoOperatordealLogExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.msgProducter.trade.OrderProducer;
import com.urt.po.LaoOperatordealLog;
@Service("orderService")
public class OrderServiceImpl implements OrderService{
	
	@Autowired LaoOperatordealLogExtMapper dealLogExtDao;
	
	@Autowired OrderProducer OrderProduct;
	@Override
	public void sendMsg(String requestId) {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("requestId", requestId);
		OrderProduct.sendMessage(map);
	}

	@Override
	public LaoOperatordealLogDto getDealLogByRequestId(String requestId) {
		// TODO Auto-generated method stub
		LaoOperatordealLog dealLogByRequest = dealLogExtDao.getDealLogByRequest(requestId);
		LaoOperatordealLogDto dto=null;
		if(dealLogByRequest!=null){
			dto=new LaoOperatordealLogDto();
			BeanMapper.copy(dealLogByRequest, dto);
		}
		return dto;
	}

	@Override
	public int updateFailNumByRequestId(String requestId) {
		// TODO Auto-generated method stub
		return dealLogExtDao.updateFailNumByRequestId(requestId);
	}
	

}
