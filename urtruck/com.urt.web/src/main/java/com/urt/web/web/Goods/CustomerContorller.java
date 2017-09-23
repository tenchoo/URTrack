package com.urt.web.web.Goods;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.urt.dto.Goods.CustomerDto;
import com.urt.interfaces.Goods.CustomerService;


@Controller
@RequestMapping("/customer")
public class CustomerContorller {
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/findCustomer")
	@ResponseBody
	public List<CustomerDto> findDiscont()throws Exception{
		List<CustomerDto> CustomerList = customerService.findCustomer();
		return CustomerList;
	}

}
