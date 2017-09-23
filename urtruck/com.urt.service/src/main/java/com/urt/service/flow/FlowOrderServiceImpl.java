package com.urt.service.flow;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.unicom.util.HttpClientUtil;
import com.urt.interfaces.flow.FlowOrderService;
import com.urt.mapper.FlowOperationFlowOrderMapper;
import com.urt.mapper.FlowOperationLogMapper;
import com.urt.mapper.ext.FlowOperationFlowOrderExtMapper;
import com.urt.mapper.ext.LaoKeyManagementExtMapper;
import com.urt.po.FlowOperationFlowOrder;
import com.urt.po.FlowOperationLog;
import com.urt.service.util.FlowOrderUtil;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

@Service("flowOrderService")
@Transactional(propagation = Propagation.REQUIRED)
public class FlowOrderServiceImpl   implements FlowOrderService{
//	private final static String DAHANSANTONGREQ = "http://120.26.43.108:3429/if/FCOrderServlet";//大汉三通流量订购接口
//	private final static String DAHANSANTONGREQ = "http://test.if.dahanbjfc.com/FCOrderNewServlet";//北京测试下单地址
//	private final static String ACCOUNT = "adminlxddcs";//登录大汉三通测试账户
//	private final static String PWD = "test1234";//登录大汉三通测试接口密码
//	private final static String EDGECALLBACKURL = "http://sms.weiyingjia.cn:8080/ytx/data/callback/lenovoReportCall.jsp";//聚通达回调接口
	private final static String EDGECALLBACKURL = "http://211.103.158.85:8015/Callback/Flow/141";//维邦云计算回调接口
	
	private final static String DAHANSANTONGREQ = "Http://hyif.dahanbank.cn/FCOrderNewServlet";//正式下单地址
	private final static String ACCOUNT = "Adminziyoukqw";//登录大汉三通账户
	private final static String PWD = "test1234";//登录大汉三通接口密码
	@Autowired
	LaoKeyManagementExtMapper managermentExtDao;
	@Autowired
	FlowOperationFlowOrderMapper flowOrderDao;
	@Autowired
	FlowOperationFlowOrderExtMapper flowOrderExtDao;
	@Autowired
	FlowOperationLogMapper orderLogDao;
	
	Logger log = Logger.getLogger(getClass());

    /**
     * 校验请求参数，返回校验结果
     */
	@Override
	public JSONObject paramCheck(String custId,String appkey,String clientOrderId, String mobile,String packageSize) {
		log.info("enter the method paramCheck");
		JSONObject respObj=new JSONObject();
		String resultCode="00";
		String resultMsg="订单提交成功";
		String failPhones="";
		String cOrderId=clientOrderId;
		log.info("custId="+custId+",appkey="+appkey+",clientOrderId="+clientOrderId+",mobile="+mobile+",packageSize");
		//{"custId":"3071458290028741","appKey":"4q31joU71n30V4ni9cmjy65U486u29","packageSize":"10","mobiles":"13522824721","clientOrderId":"123456789"}
		if("".equals(custId.trim())||"".equals(appkey.trim())||
		   "".equals(clientOrderId.trim())||"".equals(mobile.trim())||
		   "".equals(packageSize.trim())){
			resultCode="01";
			resultMsg="缺少必要的参数";
			failPhones=mobile;
		}else{
			//根据custId去数据库查出appkey
			String appkeyDB = managermentExtDao.selectAppKeyByCustId(custId);
			if(appkeyDB==null||"".equals(appkeyDB)){
				resultCode="51";
				resultMsg="未开通流量业务";
				failPhones=mobile;
			}else if(!appkey.trim().equals(appkeyDB.trim())){
				resultCode="07";
				resultMsg="appkey校验不成功";
				failPhones=mobile;
			}
		}
		respObj.put("RESP_CODE", resultCode);
		respObj.put("RESP_DESC", resultMsg);
		respObj.put("failPhones", failPhones);
		respObj.put("clientOrderId", cOrderId);
		log.debug("exit the method paramCheck");
	    return respObj;
	}
	 /**
     * 第一步流量订购请求，插入订单表和订单日志表
     */
	@Override
	public String insertFlowOrderAndLog(JSONObject requestParams,
			JSONObject respObj) {
		log.debug("enter the method insertFlowOrderAndLog");
		String flowOrderId = insertFlowOrderOne(requestParams);
		insertFlowOrderLogOne(requestParams,respObj,flowOrderId);
		log.debug("exit the method insertFlowOrderAndLog");
		return flowOrderId;
	}

	 /**
     * 流量订购第一步，数据存储到数据库订单表
     * @param requestParams
     * @throws ParseException 
     */
	public String insertFlowOrderOne(JSONObject jsonObj){
		String flowOrderId=ZkGenerateSeq.getIdSeq(SeqID.FLOWORDER_ID);
		log.debug("enter the method insertFlowOrderOne");
		FlowOperationFlowOrder flowOrder=new FlowOperationFlowOrder();
		flowOrder.setFloworderid(flowOrderId);
		flowOrder.setCustchannelid(jsonObj.getString("custId"));
		flowOrder.setClientorderid(jsonObj.getString("clientOrderId"));
		flowOrder.setMobile(jsonObj.getString("mobile"));
		flowOrder.setPackagesize(jsonObj.getString("packageSize"));
		flowOrder.setStepstate1("1");
		flowOrder.setStepstatetime1(new Date());
		flowOrderDao.insertSelective(flowOrder);
		log.debug("exit the method insertFlowOrderOne");
		return flowOrderId;
	}


	public void insertFlowOrderLogOne(JSONObject requestParams,JSONObject respObj, String flowOrderId){
		log.debug("enter the method insertFlowOrderLogOne");
		FlowOperationLog orderLog=new FlowOperationLog();
		orderLog.setFloworderid(flowOrderId);
		orderLog.setRequestparm(requestParams.toString());
		orderLog.setRequestsystem(requestParams.getString("custId"));
		orderLog.setResponsesystem("懂得通信");
		orderLog.setResponseparm(respObj.toString());
		orderLog.setCallserver("flowOrder");
		orderLog.setCalltime(new Date());
		if("00".equals(respObj.getString("RESP_CODE"))){
			orderLog.setCallissuccess("yes");
		}else{
			orderLog.setCallissuccess("no");
		}
		orderLog.setRespcode(respObj.getString("resultCode"));
		orderLog.setRespdesc(respObj.getString("resultMsg"));
		orderLog.setStepstate("1");
		orderLogDao.insertSelective(orderLog);
		log.debug("exit the method insertFlowOrderLogOne");
	}

    
	@Override
	public JSONObject sentPostToDaHan(JSONObject requestParams,String flowOrderId) {
		log.debug("enter the method sentPostToDaHan");
		String mobiles = requestParams.getString("mobile");
		String packageSize = requestParams.getString("packageSize");
		String clientOrderId = requestParams.getString("clientOrderId"); 
		long timestamp = System.currentTimeMillis();
		String mobileStr = FlowOrderUtil.degistMobile(mobiles, PWD);//加密后的电话
		String sign=FlowOrderUtil.degistByMD5(ACCOUNT+FlowOrderUtil.degistByMD5(PWD)+timestamp+mobiles+packageSize+clientOrderId);
		//请求大汉三通的参数
		JSONObject jsonPost=new JSONObject();
		jsonPost.put("timestamp", timestamp);
		jsonPost.put("sign", sign);
		jsonPost.put("mobiles", mobileStr);
		jsonPost.put("account", ACCOUNT);
		jsonPost.put("clientOrderId", clientOrderId);
		jsonPost.put("packageSize", packageSize);
		HttpClientUtil httpClient=HttpClientUtil.getInstance();
		String resp="{\"resultCode\":\"01\",\"resultMsg\":\"处理失败\",\"failPhones\":\"\",\"clientOrderId\":\"\"}";
		JSONObject respJson = null; 
		try{
			resp = httpClient.sendHttpPostOfJson(DAHANSANTONGREQ, jsonPost.toString());
			respJson = JSONObject.parseObject(resp);
		}catch(Exception e){
			System.out.println(">>>>>>>>>>>请求大汉三通接口失败，大汉三通返回值不合法");
		}
		recordFlowLog(requestParams,jsonPost,respJson,flowOrderId);
		return respJson;
	}
	/**
	 * 流量订购第二步存储订单表和日志表
	 * @param requestParams
	 * @param jsonPost
	 * @param resp
	 * @param flowOrderId 
	 */
	private void recordFlowLog(JSONObject requestParams,JSONObject jsonPost,JSONObject resp, String flowOrderId){
		log.debug("enter the method recordFlowLog");
		FlowOperationFlowOrder flowOrder=new FlowOperationFlowOrder();
		flowOrder.setFloworderid(flowOrderId);
		flowOrder.setStepstate2("2");
		flowOrder.setStepstatetime2(new Date());
		flowOrderDao.updateByPrimaryKeySelective(flowOrder);
		
		insertFlowOrderLogTwo(requestParams,jsonPost,resp,flowOrderId);
		log.debug("exit the method recordFlowLog");
	}
	/**
	 * 流量订购第四步存储订单表和日志表
	 * @param requestParams
	 * @param jsonPost
	 * @param resp
	 */
	private void recordFlowLog(JSONObject jsonPost,JSONObject resp,String flowOrderId){
		log.debug("enter the method recordFlowLog");
		FlowOperationFlowOrder flowOrder=new FlowOperationFlowOrder();
		flowOrder.setFloworderid(flowOrderId);
		flowOrder.setStepstate4("4");
		flowOrder.setStepstatetime4(new Date());
		flowOrderDao.updateByPrimaryKeySelective(flowOrder);
		
		insertFlowOrderLogFour(jsonPost,resp,flowOrderId);
		log.debug("exit the method recordFlowLog");
	}
    /**
     * 流量订购，第四部数据库记录
     * @param jsonPost
     * @param resp
     * @param flowOrderId
     */
	private void insertFlowOrderLogFour(JSONObject jsonPost, JSONObject resp,
			String flowOrderId) {
		log.debug("enter the method insertFlowOrderLogFour");
		FlowOperationFlowOrder flowOrder = flowOrderDao.selectByPrimaryKey(flowOrderId);
		FlowOperationLog orderLog=new FlowOperationLog();
		orderLog.setFloworderid(flowOrderId);
		orderLog.setRequestparm(jsonPost.toString());
		orderLog.setRequestsystem("懂的通信");
		orderLog.setResponsesystem(flowOrder.getCustchannelid());
		orderLog.setCallserver("flowOrder");
		orderLog.setCalltime(new Date());
		if(resp!=null){
			orderLog.setResponseparm(resp.toString());
			if("0000".equals(resp.getString("RESP_CODE"))){
				orderLog.setCallissuccess("yes");
			}else{
				orderLog.setCallissuccess("no");
			}
			orderLog.setRespcode(resp.getString("RESP_CODE"));
			orderLog.setRespdesc(resp.getString("RESP_DESC"));
		}else{
			orderLog.setCallissuccess("no");
		}
		orderLog.setStepstate("4");
		orderLogDao.insertSelective(orderLog);
		log.debug("exit the method insertFlowOrderLogFour");
	}
	/**
	 * 流量订购第三步存储订单表和日志表
	 * @param requestParams
	 * @param jsonPost
	 * @param resp
	 */
	@Override
	public void recordBackFlowLogThree(JSONObject requestParams, JSONObject resp,String flowOrderId) {
		log.debug("enter the method insertFlowOrderLogTwo");
		String states=requestParams.getString("status");
		FlowOperationFlowOrder flowOrder=new FlowOperationFlowOrder();
		flowOrder.setFloworderid(flowOrderId);
		flowOrder.setStepstate3("3");
		flowOrder.setStepstatetime3(new Date());
		flowOrder.setCallbackdostmoney(requestParams.getString("costMoney"));
		flowOrder.setCallbackdiscount(requestParams.getString("discount"));
		if("0".equals(states)){
			flowOrder.setIssuccess("yes");
		}else{
			flowOrder.setIssuccess("no");
		}
		flowOrderDao.updateByPrimaryKeySelective(flowOrder);
		
		FlowOperationLog orderLog=new FlowOperationLog();
		orderLog.setFloworderid(flowOrderId);
		orderLog.setRequestparm(requestParams.toString());
		orderLog.setRequestsystem("运营商");
		orderLog.setResponsesystem("懂的通信");
		orderLog.setResponseparm(resp.toString());
		orderLog.setCallserver("flowOrder");
		orderLog.setCalltime(new Date());
		if("0000".equals(resp.getString("resultCode"))){
			orderLog.setCallissuccess("yes");
		}else{
			orderLog.setCallissuccess("no");
		}
		orderLog.setRespcode(resp.getString("resultCode"));
		orderLog.setRespdesc(resp.getString("resultMsg"));
		orderLog.setStepstate("3");
		orderLogDao.insertSelective(orderLog);
		log.debug("exit the method insertFlowOrderLogTwo");
		
	}

	/**
	 * 流量订购第二步存储日志表
	 * @param requestParams
	 * @param jsonPost
	 * @param resp
	 * @param flowOrderId 
	 */
	private void insertFlowOrderLogTwo(JSONObject requestParams,JSONObject jsonPost, JSONObject resp, String flowOrderId) {
		log.debug("enter the method insertFlowOrderLogTwo");
		FlowOperationLog orderLog=new FlowOperationLog();
		orderLog.setFloworderid(flowOrderId);
		orderLog.setRequestparm(jsonPost.toString());
		orderLog.setRequestsystem("懂的通信");
		orderLog.setResponsesystem("运营商");
		if(resp!=null){
			orderLog.setResponseparm(resp.toString());
			if("00".equals(resp.getString("resultCode"))){
				orderLog.setCallissuccess("yes");
			}else{
				orderLog.setCallissuccess("no");
			}
			orderLog.setRespcode(resp.getString("resultCode"));
			orderLog.setRespdesc(resp.getString("resultMsg"));
		}else{
			orderLog.setCallissuccess("no");
		}
		orderLog.setCallserver("flowOrder");
		orderLog.setCalltime(new Date());
		orderLog.setStepstate("2");
		orderLogDao.insertSelective(orderLog);
		log.debug("exit the method insertFlowOrderLogTwo");
	}
	@Override
	public JSONObject callBackCheck(String clientOrderId, String mobile,
			String reportTime, String callBackTime, String status,
			String errorCode, String errorDesc, String intervalTime,
			String clientSubmitTime, String discount,String costMoney, String sign) throws UnsupportedEncodingException {
		log.debug("enter the method callBackCheck");
		JSONObject respObj=new JSONObject();
		String resultCode="1111";
		String resultMsg="参数校验失败";
		StringBuffer buffer=new StringBuffer();
		if(clientOrderId!=null&&!"".equals(clientOrderId.trim())){
			buffer.append("clientOrderId="+clientOrderId);
		}
        if(mobile!=null&&!"".equals(mobile.trim())){
        	buffer.append("&mobile="+mobile);
		}
        if(callBackTime!=null&&!"".equals(callBackTime.trim())){
        	buffer.append("&callBackTime="+callBackTime);
		}
        if(status!=null&&!"".equals(status.trim())){
        	buffer.append("&status="+status);
		}
        if(errorCode!=null&&!"".equals(errorCode.trim())){
        	buffer.append("&errorCode="+errorCode);
		}
        if(errorDesc!=null&&!"".equals(errorDesc.trim())){
        	buffer.append("&errorDesc="+errorDesc);
		}
        if(intervalTime!=null&&!"".equals(intervalTime.trim())){
        	buffer.append("&intervalTime="+intervalTime);
		}
        if(clientSubmitTime!=null&&!"".equals(clientSubmitTime.trim())){
        	buffer.append("&clientSubmitTime="+clientSubmitTime);
        }
        if(discount!=null&&!"".equals(discount.trim())){
        	buffer.append("&discount="+discount);
		}
        if(costMoney!=null&&!"".equals(costMoney.trim())){
        	buffer.append("&costMoney="+costMoney);
		}
        String pwdByMD5=FlowOrderUtil.degistByMD5(PWD);
        System.out.println("大汉三通回调，pwd的MD5加密pwdByMD5="+pwdByMD5);
        buffer.append("&"+pwdByMD5);
        String originString=buffer.toString();
        System.out.println("大汉三通回调，获取sign前的字符串originString="+originString);
        String codeName = java.net.URLEncoder.encode(originString, "UTF-8");
        System.out.println("大汉三通回调，获取sign前utf8加密的字符串codeName="+codeName);
        String signByMD5=FlowOrderUtil.degistByMD5(codeName);
        System.out.println("大汉三通回调，MD5加密后获得的sign,signByMD5="+codeName);
        System.out.println("大汉三通回调，参数中的sign="+codeName);
        if(!"".equals(sign.trim())&&sign.equals(signByMD5)){
        	resultCode="0000";
        	resultMsg="处理成功";
        }
        respObj.put("resultCode", resultCode);
        respObj.put("resultMsg", resultMsg);
		log.debug("exit the method callBackCheck");
		return respObj;
	}
	@Override
	public String getFlowOrderId(String clientOrderId) {
		log.debug("enter the method getFlowOrderId");
		String flowOrderId=flowOrderExtDao.getFlowOrderId(clientOrderId);
		log.debug("enter the method getFlowOrderId");
		return flowOrderId;
	}
	@Override
	public JSONObject sentPostToEdge(JSONObject requestParams,String flowOrderId) {
		log.debug("enter the method sentPostToEdge");
		System.out.println("》》》》》懂得通信开始回调聚通达的下单回调接口");
		JSONObject respJSON=new JSONObject();
		respJSON.put("flowOrderId", flowOrderId);
		respJSON.put("clientOrderId", requestParams.getString("clientOrderId"));
		respJSON.put("mobile", requestParams.getString("mobile"));
		respJSON.put("reportTime", requestParams.getString("reportTime"));
		respJSON.put("callBackTime", requestParams.getString("callBackTime"));
		respJSON.put("status", requestParams.getString("status"));
		respJSON.put("errorCode", requestParams.getString("errorCode"));
		respJSON.put("errorDesc", requestParams.getString("errorCode"));
		respJSON.put("intervalTime", requestParams.getString("intervalTime"));
		respJSON.put("clientSubmitTime", requestParams.getString("clientSubmitTime"));
		respJSON=getSignByMD5(respJSON);//md5加密获得sign，放入respJSON中
		//String callBackUrl = flowOrderExtDao.getHttpUrl(flowOrderId);
		String callBackUrl ="http://119.23.30.61:8015/Callback/Flow/112";
		HttpClientUtil httpClient=HttpClientUtil.getInstance();
		String resp = httpClient.sendHttpPostOfJson(callBackUrl, respJSON.toString());
		System.out.println("》》》》》懂得通信回调聚通达的下单回调接口完毕 返回信息为："+resp);
		recordFlowLog(respJSON,JSONObject.parseObject(resp),flowOrderId);
		log.debug("enter the method sentPostToEdge");
		return JSONObject.parseObject(resp);
	}
	
	private JSONObject getSignByMD5(JSONObject respJSON) {
		log.debug("enter the method getSignByMD5");
		StringBuffer buffer=new StringBuffer();
		if(!StringUtils.isBlank(respJSON.getString("clientOrderId"))){
			buffer.append("clientOrderId="+respJSON.getString("clientOrderId"));
		}
		if(!StringUtils.isBlank(respJSON.getString("flowOrderId"))){
			buffer.append("&flowOrderId="+respJSON.getString("flowOrderId"));
		}
        if(!StringUtils.isBlank(respJSON.getString("mobile"))){
        	buffer.append("&mobile="+respJSON.getString("mobile"));
		}
        if(!StringUtils.isBlank(respJSON.getString("reportTime"))){
        	buffer.append("&reportTime="+respJSON.getString("reportTime"));
        }
        if(!StringUtils.isBlank(respJSON.getString("callBackTime"))){
        	buffer.append("&callBackTime="+respJSON.getString("mobile"));
		}
        if(!StringUtils.isBlank(respJSON.getString("status"))){
        	buffer.append("&status="+respJSON.getString("status"));
		}
        if(!StringUtils.isBlank(respJSON.getString("errorCode"))){
        	buffer.append("&errorCode="+respJSON.getString("errorCode"));
		}
        if(!StringUtils.isBlank(respJSON.getString("errorDesc"))){
        	buffer.append("&errorDesc="+respJSON.getString("errorDesc"));
		}
        if(!StringUtils.isBlank(respJSON.getString("intervalTime"))){
        	buffer.append("&intervalTime="+respJSON.getString("intervalTime"));
		}
        if(!StringUtils.isBlank(respJSON.getString("clientSubmitTime"))){
        	buffer.append("&clientSubmitTime="+respJSON.getString("clientSubmitTime"));
        }
        String sign=FlowOrderUtil.degistByMD5(buffer.toString());
        respJSON.put("sign", sign);
		log.debug("enter the method getSignByMD5");
		return respJSON;
	}
	public static void main(String[] args) throws UnsupportedEncodingException{
    // http://127.0.0.1/flow/flowOrder?custId=123456&iccid=123456&appkey=ABSFASDFASDFASD&flowOrderId=2123456&mobile=19239395309&packageSize=1000&clientOrderId=123456
		//{"custId":"3071458290028741","appKey":"4q31joU71n30V4ni9cmjy65U486u29","packageSize":"10","mobiles":"13522824721","clientOrderId":"123456789"}
        JSONObject respJSON=new JSONObject();
		respJSON.put("custId", "3071422311954307");
		respJSON.put("clientOrderId", "13344b4bdef5383c");
        respJSON.put("mobile", "13522824721");
		respJSON.put("appkey", "4N06c60r30s140c5U2Ox878h9l3bXq");
		respJSON.put("packageSize", "4000");
		HttpClientUtil httpClient=HttpClientUtil.getInstance();
		String resp = httpClient.sendHttpPostOfJson("http://127.0.0.1/flow/flowOrder", respJSON.toString());
		System.out.println(resp);
	}
//	public static void main(String[] args) throws UnsupportedEncodingException{
//        JSONObject respJSON=new JSONObject();
//		respJSON.put("clientOrderId", "1234567890");
//        respJSON.put("mobile", "13522824721");
//		respJSON.put("reportTime", "Dec 16, 2016 2:19:09 PM");
//		respJSON.put("callBackTime", "2016-12-16 14:19:09");
//		respJSON.put("status", "0");
//		respJSON.put("errorCode", "");
//		respJSON.put("errorDesc", "");
//		respJSON.put("intervalTime", "0");
//		respJSON.put("clientSubmitTime", "2016-12-16 14:19:09");
//		respJSON.put("discount", "9.50");
//		respJSON.put("costMoney", "2.85");
//		respJSON.put("sign", "79d982de875d2cd2dd176990d201b75a");
//		HttpClientUtil httpClient=HttpClientUtil.getInstance();
//		String resp = httpClient.sendHttpPostOfJson("http://127.0.0.1/flowback/OrderCallBack", respJSON.toString());
//		System.out.println(resp);
//		System.exit(0); 
//		
//	}
	/*public static void main(String[] args) throws UnsupportedEncodingException{
		//payAmount=1&privateAttach=http%3A%2F%2Fvbtest.lenovomm.cn%2Fcommon%2Fapplay_order.xhtml%3Fc%3DpayWeb
		//&tradeNo=1201702070557183640727993123
		//&sign_type=MD5
		//&orderTime=20170207175743
		//&orderStatus=1
		//&sign=efdbdab711c0a885ef85426256492fef
		//&_input_charset=utf-8&userId=1007084072
        //&payType=8
		//&userName=18515001231
		//&bankOrderid=2017020721001004140285999617
		//&merchantOrderId=1011739290000818
        JSONObject respJSON=new JSONObject();
		respJSON.put("payAmount", "1");
        respJSON.put("privateAttach", "http%3A%2F%2Fvbtest.lenovomm.cn%2Fcommon%2Fapplay_order.xhtml%3Fc%3DpayWeb");
		respJSON.put("tradeNo", "1201702070557183640727993123");
		respJSON.put("sign_type", "MD5");
		respJSON.put("orderTime", "20170207175743");
		respJSON.put("orderStatus", "1");
		respJSON.put("sign", "efdbdab711c0a885ef85426256492fef");
		respJSON.put("_input_charset", "utf-8");
		respJSON.put("userId", "10070840727");
		respJSON.put("payType", "8");
		respJSON.put("userName", "18515001231");
		respJSON.put("bankOrderid", "2017020721001004140285999617");
		respJSON.put("merchantOrderId", "1011739290000818");
		HttpClientUtil httpClient=HttpClientUtil.getInstance();
		String resp = httpClient.sendHttpPostOfJson("http://127.0.0.1/back/payBack", respJSON.toString());
		System.out.println(resp);
		System.exit(0); 
//		String a = URLDecoder.decode("http%3A%2F%2Fvbtest.lenovomm.cn%2Fcommon%2Fapplay_order.xhtml%3Fc%3DpayWeb",
//				"utf-8");
//		System.out.println(a);
	}
	*/
	
	
}
