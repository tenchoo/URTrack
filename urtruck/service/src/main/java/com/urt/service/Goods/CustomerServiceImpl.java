package com.urt.service.Goods;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.Goods.CustomerDto;
import com.urt.interfaces.Goods.CustomerService;
import com.urt.mapper.ext.CustomerExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoCustomerPo;
@Service("customerGoodService")
@Transactional(propagation=Propagation.REQUIRED)
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerExtMapper customerExtMapper;
	

	@SuppressWarnings("null")
	@Override
	public List<CustomerDto> findCustomer() {
		// TODO Auto-generated method stub
		List<CustomerDto> list = new ArrayList<CustomerDto>();
		List<LaoCustomerPo> customerList = customerExtMapper.findCustomer();
		if(null != customerList || customerList.size() != 0){
			for (LaoCustomerPo laoCustomerPo : customerList) {
				CustomerDto customerDto = new CustomerDto();
				BeanMapper.copy(laoCustomerPo, customerDto);
				list.add(customerDto);
			}
			return list;
		}else{
			return null;
		}

	}

}
