package com.urt.web.web.Goods;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.urt.common.util.ResultMsg;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Goods.GoodsReleaseDto;
import com.urt.interfaces.Goods.GoodsReleaseService;
import com.urt.web.common.util.ActionUtil;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/custgoods")
public class CustomerGoodsController {
	/*
	 *  企业客户产品展示 wyp
	 *
	 * */
	@Autowired
	private GoodsReleaseService goodsReleaseService;
	   //客户产品展示页面
		@RequestMapping("/custgoodsList")
		public ModelAndView CustgoodsList(HttpServletRequest req)throws Exception{
			ModelAndView model = new ModelAndView("Goods/custgoodsList");
			return model;
		}
		@ResponseBody
		@RequestMapping(value="list")
		public  Map<String, Object> CustgoodsList(HttpServletRequest req,HttpServletResponse resp,GoodsReleaseDto goodsReleaseDto) {
			int pageStart = Integer.parseInt(req.getParameter("iDisplayStart")
					.toString());// 开始显示的项
			int pageSize = Integer.parseInt(req.getParameter("iDisplayLength")
					.toString());// 显示多少项
			int pageNo = (pageStart / pageSize) + 1;
			LaoSsAccountDto currentUser = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
			boolean ifSuperUser = ActionUtil.ifSuperUser(req);
			if (!ifSuperUser) {
				goodsReleaseDto.setRelsCustId(currentUser.getCustId());
				return  goodsReleaseService.queryCustPage(goodsReleaseDto, pageNo, pageSize);
			}
			return null;
		}
	
		//删除企业客户产品
		@ResponseBody
		@RequestMapping("/delCustGoodsRelease")
		public ResultMsg delGoodRelease(Integer goodsReleaseId,HttpServletRequest req)throws Exception{
			ResultMsg msg = new ResultMsg();
			LaoSsAccountDto currentUser = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
			boolean ifSuperUser = ActionUtil.ifSuperUser(req);
			if (ifSuperUser == false) {	
				int delete = goodsReleaseService.deleteCustGoods(goodsReleaseId);
				if("1".equals(String.valueOf(delete))){			
					msg.setSuccess(true);			
				}else{
					msg.setSuccess(false);
				}
				
			}
			return msg;
			
		}
		//企业客户产品发布
		@ResponseBody	
		@RequestMapping("/custGoodsRelease")
		public ResultMsg addGoodRelease(HttpServletRequest req, GoodsReleaseDto goodsReleaseDto)throws Exception{
			LaoSsAccountDto currentUser = (LaoSsAccountDto) req.getSession().getAttribute("sessionUser");
			boolean ifSuperUser = ActionUtil.ifSuperUser(req);	
			if (ifSuperUser == false) {
			goodsReleaseDto.setChannelGroupId(currentUser.getCustId());
			}
			int add=goodsReleaseService.add(goodsReleaseDto);
			ResultMsg msg = new ResultMsg();
			if("1".equals(String.valueOf(add))){			
				msg.setSuccess(true);			
			}else{
				msg.setSuccess(false);
			}
			return msg; 	
		}
		//跳转到产品发布页
		@RequestMapping("GoodsRelease")
		public ModelAndView CustgoodsRelease(HttpServletRequest req)throws Exception{
			ModelAndView model = new ModelAndView("Goods/custgoodsReleaseAdd");
			return model;
		}
		
		//跳转到企业产品发布展示页
		@RequestMapping("/toshowGoodsRelease")
		public ModelAndView toAddGoods(HttpServletRequest req)throws Exception{		
			ModelAndView model = new ModelAndView("Goods/custgoodsReleaseList");
			return model;	
		}
		
		
		
}

