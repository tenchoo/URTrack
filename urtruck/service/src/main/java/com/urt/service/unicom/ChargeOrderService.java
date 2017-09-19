package com.urt.service.unicom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urt.mapper.ChargeOrderMapper;
import com.urt.po.ChargeOrder;

@Service("chargeOrderService")
@Transactional
public class ChargeOrderService {

	@Autowired
	private ChargeOrderMapper chargeOrderDAO;
	
	public boolean addChargeOrder(ChargeOrder chargeOrder){
		chargeOrderDAO.insert(chargeOrder);
		return true;
	}

	public  ChargeOrder findChargeOrderByOrderId(String orderId) {
		return	chargeOrderDAO.doQueryFirst(orderId);
	}

	public boolean  updateChargeOrder(ChargeOrder chargeOrder) {
		chargeOrderDAO.updateByPrimaryKey(chargeOrder);
		return true;
	}
	
	
	
	
}
