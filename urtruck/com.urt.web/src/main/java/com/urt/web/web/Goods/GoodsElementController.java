package com.urt.web.web.Goods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSON;
import com.urt.common.util.JsonUtil;
import com.urt.common.util.ResultMsg;
import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.Goods.GoodsElementDto;
import com.urt.interfaces.Goods.GoodsElementService;
import com.urt.interfaces.Goods.GoodsService;
import com.urt.modules.mapper.BeanMapper;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
@Controller
@RequestMapping("/goodsElement")
public class GoodsElementController {
	
//	private static Log log = LogFactory.getLog(GoodsElementController.class);
	@Autowired
	private GoodsElementService goodsElementService;
	@Autowired
	private GoodsService goodsService;
	
	//编辑产品
	@ResponseBody
	@RequestMapping("/editGoodsElement")
	public ResultMsg editGoodsElement(HttpServletRequest req)throws Exception{
		GoodsElementDto goodsElementDto = new GoodsElementDto();
		ResultMsg msg = new ResultMsg();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		String elementType = req.getParameter("elementType");
		String originalId = req.getParameter("originalId");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		String packageType = req.getParameter("packageType");
		String goodsIndex = req.getParameter("goodsIndex");
		String goodsId = req.getParameter("goodsId");
		goodsElementDto.setElementType(elementType);
		goodsElementDto.setEndDate(sdf.parse(endDate));
		goodsElementDto.setGoodsId(Long.parseLong(goodsId));
		goodsElementDto.setOriginalId(Integer.parseInt(originalId));
		goodsElementDto.setPackageType(packageType);
		goodsElementDto.setStartDate(sdf.parse(startDate));
		goodsElementDto.setGoodsIndex(Short.parseShort(goodsIndex));
		int insert = goodsElementService.insert(goodsElementDto);
		if("1".equals(String.valueOf(insert))){			
			msg.setSuccess(true);			
		}else{
			msg.setSuccess(false);
		}
		return msg;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@ResponseBody
	@RequestMapping(value="/inserts")
	public ResultMsg inserts(String elements){
		ResultMsg msg = new ResultMsg();
		System.out.println("jsonStr="+elements);
		List<GoodsElementDto> list=new ArrayList<GoodsElementDto>();
		/*list = JsonUtil.fromJson(elements, ArrayList.class);*/
		JSONArray array = JSONArray.fromObject(elements);
		list = JSONArray.toList(array, GoodsElementDto.class);
		System.out.println("list="+list.size());
		GoodsElementDto goodsElementDto = list.get(0);
		int insert = goodsElementService.inserts(list);
		if("1".equals(String.valueOf(insert))){			
			msg.setSuccess(true);			
		}else{
			msg.setSuccess(false);
		}
		return msg;
	}
	//跳转到产品元素展示页面
	@RequestMapping("/findGoodsElementByGoodsId")
	public String findGoodsElementByGoodsId(String goodsId,HttpServletRequest req)throws Exception{
		List<GoodsElementDto> goodsElementList = goodsElementService.findGoodsElementByGoodsId(Long.parseLong(goodsId));
		if(null == goodsElementList){
			return "error/500";
		}
		GoodsDto goodsDto = goodsService.findByGoodsId(Long.parseLong(goodsId));
		req.setAttribute("goodsDto", goodsDto);
		req.setAttribute("goodsElementList", goodsElementList);
		return "Goods/goodsInfo";
	}
		
	//跳转到元素修改页面
	@RequestMapping("/toupdateGoodsElement")
	public String toupdateGoodsElement(HttpServletRequest req,Integer goodsElementId)throws Exception{
		GoodsElementDto goodsElement = goodsElementService.findByGoodsId(goodsElementId);
		GoodsDto goods = goodsService.findByGoodsId(goodsElement.getGoodsId());
		req.setAttribute("goodsElement", goodsElement);
		req.setAttribute("goods", goods);
		return "Goods/elementUpdate";
	}
	
	//修改元素信息
	@ResponseBody
	@RequestMapping("/updateGoodsElement")
	public ResultMsg updateGoodsElement(HttpServletRequest req)throws Exception{
		GoodsElementDto goodsElementDto = new GoodsElementDto();
		ResultMsg msg = new ResultMsg();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		String elementType = req.getParameter("elementType");
		String originalId = req.getParameter("originalId");
		String startDate = req.getParameter("startDate");	
		String endDate = req.getParameter("endDate");
		String packageType = req.getParameter("packageType");
		String goodsIndex = req.getParameter("goodsIndex");
		String goodsId = req.getParameter("goodsId");
		goodsElementDto.setElementType(elementType);
		goodsElementDto.setEndDate(sdf.parse(endDate));
		goodsElementDto.setGoodsId(Long.parseLong(goodsId));
		goodsElementDto.setOriginalId(Integer.parseInt(originalId));
		goodsElementDto.setPackageType(packageType);
		goodsElementDto.setStartDate(sdf.parse(startDate));
		goodsElementDto.setGoodsIndex(Short.parseShort(goodsIndex));
		int update = goodsElementService.update(goodsElementDto);		
//		return "redirect:/goodsElement/findGoodsElementByGoodsId?goodsId="+goodsId;
		if("1".equals(String.valueOf(update))){			
			msg.setSuccess(true);			
		}else{
			msg.setSuccess(false);
		}
		return msg;
	}
	//删除元素
	@ResponseBody
	@RequestMapping("/deleteGoodsElement")
	public ResultMsg deleteGoodsElement(String elementId,HttpServletRequest req)throws Exception{
		ResultMsg msg = new ResultMsg();
/*		GoodsElementDto findByElementId = goodsElementService.findByElementId(elementId);
		Long goodsId = findByElementId.getGoodsId();*/
		int delete = goodsElementService.delete(Integer.parseInt(elementId));			
//		return "redirect:/goodsElement/findGoodsElementByGoodsId?goodsId="+goodsId;
		if("1".equals(String.valueOf(delete))){			
			msg.setSuccess(true);			
		}else{
			msg.setSuccess(false);
		}
		return msg;
	}
}
