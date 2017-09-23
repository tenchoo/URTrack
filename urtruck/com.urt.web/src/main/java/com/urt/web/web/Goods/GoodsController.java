package com.urt.web.web.Goods;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.urt.common.util.ResultMsg;
import com.urt.common.util.SoapConstant;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.interfaces.Goods.GoodsService;
import com.urt.interfaces.Goods.OperatorsService;
import com.urt.web.common.util.ResponseUtils;
@Controller
@RequestMapping("/goods")
public class GoodsController {
	
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private OperatorsService operatorsService;
	
	//跳转到产品添加页面
	@RequestMapping("/toAddGoods")
	public String toAddGoods()throws Exception{		
		return "Goods/goodsAdd";		
	}
	//跳转到产品元素添加页面
	@RequestMapping("/toAddGoodsInfo")
	public String toAddGoodsInfo(Long goodsId,HttpServletRequest req)throws Exception{
		GoodsDto goods = goodsService.findByGoodsId(goodsId);
		req.setAttribute("goods", goods);
		return "Goods/elementAdd";		
	}
	
	//添加产品
	@ResponseBody
	@RequestMapping("/editGoods")
	public ResultMsg editGoods(HttpServletRequest req,HttpSession session)throws Exception{
		GoodsDto goods = new GoodsDto();
		ResultMsg msg=new ResultMsg();
		LaoSsAccountDto currentUser=(LaoSsAccountDto)session.getAttribute("sessionUser");
		Long acconutId = currentUser.getAcconutId();
		String goodsName = req.getParameter("goodsName");
		String goodsPic = req.getParameter("goodsPic");
		String operatorsId = req.getParameter("operatorsId");
		String goodsPrices = req.getParameter("goodsPrices");
		String goodsType = req.getParameter("goodsType");
		String activeWay = req.getParameter("activeWay");
		goods.setGoodsName(goodsName);
		goods.setGoodsPic(goodsPic);
		goods.setGoodsPrices(goodsPrices);
		goods.setOperatorsId(Integer.parseInt(operatorsId));
		goods.setCreateStaffId(acconutId);
		goods.setActiveWay(activeWay);
		goods.setGoodsType(goodsType);
		int insert = goodsService.insert(goods);
		//return "redirect:/goods/goodsList";
		if("1".equals(String.valueOf(insert))){			
			msg.setSuccess(true);			
		}else{
			msg.setSuccess(false);
		}
		return msg;
	}
	//修改产品
	@ResponseBody
	@RequestMapping("/updateGoods")
	public ResultMsg update(HttpServletRequest req)throws Exception{
		GoodsDto goods = new GoodsDto();
		ResultMsg msg=new ResultMsg();
		String goodsId = req.getParameter("goodsId");
		String goodsName = req.getParameter("goodsName");
		String goodsPic = req.getParameter("goodsPic");
		String operatorId = req.getParameter("operatorsId");
		String goodsPrices = req.getParameter("goodsPrices");
		String goodsType = req.getParameter("goodsType");
		String activeWay = req.getParameter("activeWay");
		goods.setGoodsId(Long.parseLong(goodsId));
		goods.setGoodsName(goodsName);
		goods.setGoodsPic(goodsPic);
		goods.setGoodsPrices(goodsPrices);
		goods.setOperatorsId(Integer.parseInt(operatorId));
		goods.setCreatedate(goodsService.findByGoodsId(Long.parseLong(goodsId)).getCreatedate());
		goods.setActiveWay(activeWay);
		goods.setGoodsType(goodsType);
		int update = goodsService.update(goods);
//		return "redirect:/goods/goodsList";
		if("1".equals(String.valueOf(update))){			
			msg.setSuccess(true);			
		}else{
			msg.setSuccess(false);
		}
		return msg;
		
	}
	//跳转到修改页面
	@RequestMapping("/toUpdateGoods")
	public String toUpdateGoods(Long goodsId,HttpServletRequest req)throws Exception{
		GoodsDto goods = goodsService.findByGoodsId(goodsId);
		req.setAttribute("goods", goods);
		return "Goods/goodsUpdate";		
	}
	
	//删除产品
	@ResponseBody
	@RequestMapping("/delGoods")
	public ResultMsg del(Long goodsId)throws Exception{
		int delete = goodsService.delete(goodsId);
		ResultMsg msg = new ResultMsg();
//		return "redirect:/goods/goodsList";
		if("1".equals(String.valueOf(delete))){			
			msg.setSuccess(true);			
		}else{
			msg.setSuccess(false);
		}
		return msg;
		
	}
	
	//产品展示页面
	@RequestMapping("/goodsList")
	public String goodsList(HttpServletRequest req)throws Exception{
		/*List<GoodsDto> findGoods = goodsService.findGoods();
		req.setAttribute("goodslist", findGoods);*/
		return "Goods/goodsList";
	}
	
	//查询产品
	@RequestMapping("/findGoods")
	@ResponseBody
	public List<GoodsDto> findGoods()throws Exception{
		List<GoodsDto> goodsList = goodsService.findGoods();
		return goodsList;
	}
	
	@ResponseBody
	@RequestMapping(value="list")
	public  Map<String, Object> goodsList(HttpServletRequest req,HttpServletResponse resp,GoodsDto goodsDto) {
		int pageStart = Integer.parseInt(req.getParameter("iDisplayStart")
				.toString());// 开始显示的项
		int pageSize = Integer.parseInt(req.getParameter("iDisplayLength")
				.toString());// 显示多少项
		int pageNo = (pageStart / pageSize) + 1;
		  Map<String, Object> resultMap = goodsService.queryPage(goodsDto, pageNo, pageSize);
		 return resultMap;
	}
	
	
	//上传图片
	@RequestMapping(value = "/uploadPic")
	public void uploadPic(@RequestParam(required = false) MultipartFile pic,HttpServletResponse response)throws Exception{

		
		
		
		//扩展名
		String ext = FilenameUtils.getExtension(pic.getOriginalFilename());

			//图片名称生成策略
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			//图片名称一部分
			String format = df.format(new Date());
			
			//随机三位数
			Random r = new Random();
			// n 1000   0-999   99
			for(int i=0 ; i<3 ;i++){
				format += r.nextInt(10);
			}
			
			//实例化一个Jersey
			Client client = new Client();
			//保存数据库
			String path = "upload/" + format + "." + ext;
			
			//另一台服务器的请求路径是?
			String url = SoapConstant.IMAGE_URL  + path;
			//设置请求路径
			WebResource resource = client.resource(url);
			
			//发送开始  POST  GET   PUT
			try {
				resource.put(String.class, pic.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//返回二个路径	
			JSONObject jo = new JSONObject();
			jo.put("url", url);
			jo.put("path",path);
			
			System.out.println(jo.toString());
			ResponseUtils.renderJson(response, jo.toString());
		}
		

	}
	
	

