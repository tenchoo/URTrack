package com.urt.web.web.glaH5;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import antlr.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoCustomerDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.Goods.GoodsReleaseDto;
import com.urt.dto.unicom.DeviceDto;
import com.urt.interfaces.Goods.GoodsReleaseService;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.customer.LaoCustomerService;
import com.urt.interfaces.unicom.DeviceService;
import com.urt.interfaces.unicom.PaymentService;
import com.urt.web.common.util.ICCID;
import com.urt.web.common.util.StringUtil;


/**
 * 类说明：设备激活
 * 
 * @author sunhao
 * @date 2016年8月23日15:40:42
 */
@Controller
@RequestMapping("/glaH5AppActive")
public class GlaH5AppActivationController {
	@Value("#{configProperties['deviceid']}")
	private String deviceid;
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LaoCustomerService customerService;
	
	@Autowired
	private GoodsReleaseService goodsReleaseService;
	
	@Autowired
	private PaymentService paymentService;
	
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, String iccid) {
		ModelAndView mv = new ModelAndView("/newH5/activation");
		mv.addObject("iccid", iccid);
		return mv;
	}
	/**
	 * 激活服务方法
	 * @param request
	 * @param response
	 * @param session
	 * @param device
	 * @throws Exception
	 */
	@RequestMapping("/activeService")
	public void activeService(HttpServletRequest request, HttpServletResponse response, HttpSession session, DeviceDto device) {
		String iccid= ICCID.replaceLastChar(request.getParameter("iccid"));
		Map<String, Object> map =null;
		try {
			PrintWriter out= response.getWriter();
			IccidLibDto dto=userService.selectByIccid(iccid);
			if(dto != null){ //如果是gla系统的用户，走的是gla系统的激活方法    0激活 1失败
				if(device.getPrivatekey().equals(dto.getPrivatekey())){
					LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
					if(dto.getActived().equals("1") && user != null){
						try {
							String result = "fail";
							GoodsReleaseDto goodsReleaseDto = goodsReleaseService.findBygoodsReleaseId(Integer.valueOf(dto.getInitproduct()));
							if(goodsReleaseDto != null){
								String tradeId = tradeService.addTrade(user,user.getCustId() == null? dto.getCustid():user.getCustId().toString(), null,iccid,goodsReleaseDto.getGoodsId().toString() ,dto.getInitproduct(), "100",dto.getIfMaintenance());
								if(tradeId != null){
									result = userService.userArchiving(tradeId);
								}
							}
							
							map=new HashMap<String,Object>(1);
							if(("ok").equals(result) || ("maintenance").equals(result)){
								//更新lao_customser 证件信息
								LaoCustomerDto customer = customerService.selectDtoById(user.getCustId());
								if(customer != null){
									customer.setPsptId(device.getIdnum());
									customerService.update(customer);
								}
								map.put("retcode", "1");
								
								Long custId = user.getCustId();
								if(custId != null){
									List<String> iccidList = userService.getIccidByCustId(custId);
									session.setAttribute("glaIccidList",iccidList);
								}
							}else{
								map.put("retcode", "-11");
							}
							Object jsonObject = JSONObject.toJSON(map);
							out.write(jsonObject.toString());
							return;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else{
						map=new HashMap<String,Object>(1);
						map.put("retcode", "-10");//这张卡已经激活
						Object jsonObject = JSONObject.toJSON(map);
						out.write(jsonObject.toString());
						return;
					}
				}else{
					map=new HashMap<String,Object>(1);
					map.put("retcode", "-12");//这张卡已经激活
					Object jsonObject = JSONObject.toJSON(map);
					out.write(jsonObject.toString());
					return;
				}
				
			}else{
				map=new HashMap<String,Object>(1);
				map.put("retcode", "-7");
			}
			Object jsonObject = JSONObject.toJSON(map);
			out.write(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 激活成功
	 */
	@RequestMapping("/activeSuccess")
	public ModelAndView activeSuccess(String iccid){
		ModelAndView mv = new ModelAndView("/newH5/activeSuccess");
		mv.addObject("iccid", iccid);
		return mv;
	}
	/**
	 * 查看入网协议
	 * @return
	 */
	@RequestMapping("/protocol")
	public ModelAndView protocol() {
		ModelAndView mv = new ModelAndView("/glaH5/protocolDetail");
		return mv;
	}
	
}
