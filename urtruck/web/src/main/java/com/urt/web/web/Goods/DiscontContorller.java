package com.urt.web.web.Goods;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.urt.dto.Goods.DiscontDto;
import com.urt.interfaces.Goods.DiscontService;


@Controller
@RequestMapping("/discont")
public class DiscontContorller {
	
	@Autowired
	private DiscontService discontService;
	
	@RequestMapping("/findDiscont")
	@ResponseBody
	public List<DiscontDto> findDiscont()throws Exception{
		List<DiscontDto> discontList = discontService.findDiscont();
		return discontList;
	}

}
