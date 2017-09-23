package com.urt.web.web.Goods;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.urt.dto.Goods.OperatorsDto;
import com.urt.interfaces.Goods.OperatorsService;

@Controller
@RequestMapping("/operators")
public class OperatorsContorller {
	
	@Autowired
	private OperatorsService operatorsService;
	
	
	@RequestMapping("/findOperators")
	@ResponseBody
	private List<OperatorsDto> findOperators()throws Exception{
		List<OperatorsDto> OperatorsList = operatorsService.findOperators();
		return OperatorsList;
		
	}

}
