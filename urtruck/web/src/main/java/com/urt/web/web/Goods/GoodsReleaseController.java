package com.urt.web.web.Goods;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.urt.common.util.ResultMsg;
import com.urt.dto.Goods.GoodsReleaseDto;
import com.urt.interfaces.Goods.GoodsReleaseService;
import com.urt.interfaces.customer.LaoCustomerService;

@Controller
@RequestMapping("/goodsRelease")
public class GoodsReleaseController {
	
	@Autowired
	private GoodsReleaseService goodsReleaseService;
	@Autowired
	private LaoCustomerService laoCustomerService;
	
	@RequestMapping("toAddGoodRelease")
	public String toAddGoodRelease()throws Exception{
		return "/Goods/goodsReleaseAdd";
	}
	
	@ResponseBody	
	@RequestMapping("/addGoodsRelease")
	public ResultMsg addGoodRelease(HttpServletRequest req, GoodsReleaseDto goodsReleaseDto)throws Exception{
		int add = goodsReleaseService.add(goodsReleaseDto);		
		ResultMsg msg = new ResultMsg();
		if("1".equals(String.valueOf(add))){			
			msg.setSuccess(true);			
		}else{
			msg.setSuccess(false);
		}
		return msg;
		
	}
	@ResponseBody
	@RequestMapping("/delGoodsRelease")
	public ResultMsg delGoodRelease(String goodsReleaseId,HttpServletRequest req)throws Exception{
		ResultMsg msg = new ResultMsg();		
		int delete = goodsReleaseService.delete(Integer.parseInt(goodsReleaseId));
//		return "redirect:/goodsRelease/showGoodsRelease";
		if("1".equals(String.valueOf(delete))){			
			msg.setSuccess(true);			
		}else{
			msg.setSuccess(false);
		}
		return msg;
	}
	
	@RequestMapping("/toUpdateGoodsRelease")	  
	public String  toUpdateGoodsReleaseDto(String goodsReleaseId,HttpServletRequest req)throws Exception{	
		GoodsReleaseDto goodsRelease = goodsReleaseService.findBygoodsReleaseId(Integer.parseInt(goodsReleaseId));
		req.setAttribute("goodsRelease", goodsRelease);
		return "/Goods/goodsReleaseUpdate";
	}
	
	@ResponseBody
	@RequestMapping("/updateGoodsRelease")
	public ResultMsg updateGoodsReleaseDto(HttpServletRequest req)throws Exception{
//		goodsReleaseService.update(goodsReleaseDto);
		ResultMsg msg = new ResultMsg();
		GoodsReleaseDto goodsReleaseDto = new GoodsReleaseDto();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  		
		String goodsId = req.getParameter("goodsId");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		String channelGroupId = req.getParameter("custId");
		String groupAttrV1 = req.getParameter("type");
		String groupAttrV2 = req.getParameter("version");
		System.out.println("goodsId   "+goodsId+"     startDate"+startDate+"       endDate"+endDate+"       channelGroupId"+channelGroupId+"     groupAttrV1"+groupAttrV1+"        groupAttrV2"+groupAttrV2);
		goodsReleaseDto.setGoodsId(Long.parseLong(goodsId));
		goodsReleaseDto.setStartDate(sdf.parse(startDate));
		goodsReleaseDto.setEndDate(sdf.parse(endDate));
		goodsReleaseDto.setGroupAttrV1(groupAttrV1);
		goodsReleaseDto.setGroupAttrV2(groupAttrV2);
		goodsReleaseDto.setChannelGroupId(Long.parseLong(channelGroupId));
		int update = goodsReleaseService.update(goodsReleaseDto);
//		return "redirect:/goodsRelease/showGoodsRelease";
		if("1".equals(String.valueOf(update))){			
			msg.setSuccess(true);			
		}else{
			msg.setSuccess(false);
		}
		return msg;
		
	}

	@RequestMapping("/showGoodsRelease")
	public String showGoodsRelease(HttpServletRequest req)throws Exception{
		List<GoodsReleaseDto> goodsReleaseList = goodsReleaseService.findGoodsRelease();
		req.setAttribute("goodsReleaseList", goodsReleaseList);
		return "Goods/goodsReleaseList";
	}
	

	
	@ResponseBody
	@RequestMapping(value="list")
	public  Map<String, Object> goodsReleaseList(HttpServletRequest req,HttpServletResponse resp,GoodsReleaseDto goodsReleaseDto) {
		  int pageStart = Integer.parseInt(req.getParameter("iDisplayStart").toString());//开始显示的项
		  int pageSize = Integer.parseInt(req.getParameter("iDisplayLength").toString());//显示多少项
		  int pageNo = (pageStart / pageSize)+1;
		  Map<String, Object> resultMap = goodsReleaseService.queryPage(goodsReleaseDto, pageNo, pageSize);
		 return resultMap;
	}
	


}
