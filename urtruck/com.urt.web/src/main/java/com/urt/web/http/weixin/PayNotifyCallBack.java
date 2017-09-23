package com.urt.web.http.weixin;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.urt.dto.Trade.TradeDto;
import com.urt.interfaces.Trade.TradeService;
import com.urt.interfaces.User.UserService;
import com.urt.web.util.WeixinPayConstants;
@Controller
@RequestMapping("/PayNotifyCallBack")
public class PayNotifyCallBack {
	
	 @Autowired
	 private UserService userService;
	 
	 
	 @Autowired
	 private TradeService tradeService;
	 
	 
	 private static final Logger log = Logger.getLogger(PayNotifyCallBack.class);
	 @RequestMapping("/payCallBack")
	 public void callBack(HttpServletRequest request,
				HttpServletResponse response, HttpSession session) throws Exception{
		   log.info(">>>>>>>>>>>>>>>>微信支付开始回调>>>>>>>>>>>>>>>>>>>>>>>");
           //读取参数  
	        InputStream inputStream=request.getInputStream();  
	        StringBuffer sb = new StringBuffer();  
	        String s ;  
	        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
	        while ((s = in.readLine()) != null){  
	            sb.append(s);  
	        }  
	        in.close();  
	        inputStream.close();  
	        log.info(">>>>>>>>>>>>>>>>微信回调接收到的参数>>>>>>>>>"+sb.toString());
	        //解析xml成map  
	        Map<String, String> m = new HashMap<String, String>();
	        // 测试
//	        sb = new StringBuffer("<xml><appid><![CDATA[wxc282e33f9192be5d]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1277879901]]></mch_id><nonce_str><![CDATA[iytxvrm9emac286361i9kbeouxax2q8u]]></nonce_str><openid><![CDATA[oORPawp65an99w03u1sS02uTlLBQ]]></openid><out_trade_no><![CDATA[1011748230000053]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[5D82BE2DE1A1B8B6C0F4CA4712184F94]]></sign><time_end><![CDATA[20170411174813]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[NATIVE]]></trade_type><transaction_id><![CDATA[4010072001201704116651292879]]></transaction_id></xml>");
	        // 测试
	        m = XMLUtil.doXMLParse(sb.toString());  
	        
	        //过滤空 设置 TreeMap  
	        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();        
	        Iterator it = m.keySet().iterator();  
	        while (it.hasNext()) {  
	            String parameter = (String) it.next();  
	            String parameterValue = m.get(parameter);  
	              
	            String v = "";  
	            if(null != parameterValue) {  
	                v = parameterValue.trim();  
	            }  
	            packageParams.put(parameter, v);  
	        }  
	          
	        // 账号信息  
	        String key = WeixinPayConstants.api_key; // key  
	  
	        log.info(packageParams);  
	        //判断签名是否正确  
//	        String chargeMsg = "";
	        if(PayCommonUtil.isTenpaySign("UTF-8", packageParams,key)) {
	            String resXml = "";  
	            if("SUCCESS".equals((String)packageParams.get("result_code"))){  
	                // 这里是支付成功  
	            	log.info(">>>>>>>>>>>>>>>>>>>>>>>>>支付成功<<<<<<<<<<<<<<<");  
	                  
	            	String out_trade_no = (String)packageParams.get("out_trade_no");
	            	TradeDto tradeDto = tradeService.queryTradeId(Long.parseLong(out_trade_no));
	                String transaction_id = (String)packageParams.get("transaction_id");  
	                //处理业务
	                Long channalCustId = tradeDto.getChannalCustId();
	                userService.callBackOfWeixin(transaction_id, channalCustId.toString(), out_trade_no);
	        		log.info(">>>>>>>>>>>>订单信息>>>>>>>>>>>>>>>>"+tradeDto.toString());
	                //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.  
	                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"  
	                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";  
					/*mv = new ModelAndView("redirect:../customerQuery/toRateSearch2");
	                chargeMsg = "支付成功！";*/
	            } else {  
	            	log.info("支付失败,错误信息：>>>>>>>>>>" + packageParams.get("err_code"));  
	                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
	                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";  
	                //支付失败 处理业务
	            /*    chargeMsg = "支付失败！";
	                mv = new ModelAndView("redirect:../customerQuery/toRecharge");*/
	            }  
	            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
	            out.write(resXml.getBytes());
	            out.flush();  
	            out.close();  
	            //小懂上网2C的回调到充值查询记录页面
//	            if ("150".equals(tradeDto.getTradeTypeCode()+"")) {
//					mv.addObject("chargeMsg", chargeMsg);
//					mv.addObject("iccid", (String)session.getAttribute("iccid"));
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//					String today = sdf.format(new Date());
//					mv.addObject("startTime", today);
//					mv.addObject("endTime", today);
//					mv.addObject("currentPage", 1);
//					return mv;
//				}
	            log.info(">>>>>>>>>>>>>>>>>>通知微信完成<<<<<<<<<<<<<<<<<<<<<<");  
	        } else{  
	        	log.info(">>>>>>>>>>>>>>>>>>通知签名验证失败<<<<<<<<<<<<<<<<<<<<<<");  
	        } 
//	        return null;
	 }
}
