package com.urt.service.cmpp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.Ability.http.util.HttpPostSend;
import com.urt.dto.LaoSmsDeliverDto;
import com.urt.dto.LaoSmsInfoDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.cmpp.LaoCmppService;
import com.urt.mapper.LaoKeyManagementMapper;
import com.urt.mapper.LaoSmsDeliverMapper;
import com.urt.mapper.LaoSmsInfoMapper;
import com.urt.mapper.ext.LaoCustConfigExtMapper;
import com.urt.mapper.ext.LaoSmsDeliverExtMapper;
import com.urt.mapper.ext.LaoSmsInfoExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoCustConfig;
import com.urt.po.LaoKeyManagement;
import com.urt.po.LaoSmsDeliver;
import com.urt.po.LaoSmsInfo;
import com.urt.utils.HttpClientUtil;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

/**
 * 
 * @param targetMsisdn
 *            目标手机号
 * @param sendMsisdn
 *            发送手机号
 * @param smsId
 *            短信流水
 * @param smsConent
 *            短信内容
 * @param pushDate
 *            提交时间
 * @return 提交第三方是否成功 成功返回 0000 失败 9999
 * @function 次接口主要实现将push过来的短信记录通过http接口推送到第三方平台
 */
@Service("laoCmppService")
@Transactional(propagation = Propagation.REQUIRED)
public class LaoCmppServiceImpl implements LaoCmppService {
	@Autowired
	private LaoSmsInfoMapper laoSmsInfoDao;
	@Autowired
	private LaoSmsInfoExtMapper laoSmsInfoExtDao;
	@Autowired
	private LaoSmsDeliverMapper laoSmsDeliverDao;
	@Autowired
	private UserService userService;
	@Autowired
	LaoKeyManagementMapper laoKey;
	@Autowired
	LaoSmsInfoMapper laoSmsInfoMapper;
	@Autowired
	LaoSmsDeliverExtMapper laoSmsDeliverExtDao;
	@Autowired
	LaoCustConfigExtMapper laoCustConfigExtMapper;
	private static Logger logger = Logger.getLogger(LaoCmppServiceImpl.class);

	@Override
	public Long saveMsg(String msg, String iccid, String custId) {
		Date date = new Date();
		LaoUserDto userInfo = userService.getLaoUserDtoByIccid(iccid);
		String number = "";
		if (userInfo != null) {
			number = userInfo.getMsisdn();
		}
		LaoSmsInfo info = new LaoSmsInfo();
		info.setDealTag("3");
		info.setIccid(iccid);
		info.setSmsContext(msg);
		info.setSendTime(date);
		info.setMsisdn(number);
		if (!custId.equals("")) {
			info.setChannelCustId(Long.valueOf(custId));
		}
		Long smsId = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)); // TODO
																				// 生成ID,赋值
		info.setSmsId(smsId);
		int res = laoSmsInfoDao.insertSelective(info);
		if (res == 0) {
			smsId = 0L;
		}
		return smsId;
	}

	@Override
	public Long saveMsg(String msg, String iccid, String custId, Long acctId) {
		Date date = new Date();
		LaoUserDto userInfo = userService.getLaoUserDtoByIccid(iccid);
		String number = "";
		if (userInfo != null) {
			number = userInfo.getMsisdn();
		}
		LaoSmsInfo info = new LaoSmsInfo();
		info.setDealTag("3");
		info.setIccid(iccid);
		info.setSmsContext(msg);
		info.setSendTime(date);
		info.setMsisdn(number);
		if (!custId.equals("")) {
			info.setChannelCustId(Long.valueOf(custId));
		}
		if (acctId != null) {
			info.setOperId(acctId.toString());
		}
		Long smsId = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
		info.setSmsId(smsId);
		int res = laoSmsInfoDao.insertSelective(info);
		if (res == 0) {
			smsId = 0L;
		}
		return smsId;
	}

	@Override
	public int updateMsgSuccess(Long smsId) {
		LaoSmsInfo info = new LaoSmsInfo();
		Date date = new Date();
		info.setSmsId(smsId);
		info.setDealTag("2");
		info.setUpdateTime(date);
		int res = laoSmsInfoDao.updateByPrimaryKeySelective(info);
		return res;
	}

	@Override
	public List<LaoSmsInfoDto> querySendInfo(HashMap<String, Object> paraMap) {
		List<LaoSmsInfo> smsList = laoSmsInfoExtDao.querySendInfo(paraMap);
		List<LaoSmsInfoDto> dtos = new ArrayList<LaoSmsInfoDto>();
		for (LaoSmsInfo po : smsList) {
			LaoSmsInfoDto dto = new LaoSmsInfoDto();
			BeanMapper.copy(po, dto);
			String smsContext = dto.getSmsContext();
			if (null!=smsContext) {
				dto.setSmsContext(smsContext.replaceAll("<", "&#60"));
			}
			//<转成  	&lt 或者 	&#60 LaoKeyManagement  -> 未推送
			/*try {
				dto.setSmsContext(URLEncoder.encode(smsContext, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public int countSendInfo(HashMap<String, Object> paraMap) {
		int res = laoSmsInfoExtDao.countSendInfo(paraMap);
		return res;
	}

	@Override
	public void saveDeliver(LaoSmsDeliverDto laoSmsDeliver) {
		LaoSmsDeliver po = new LaoSmsDeliver();
		BeanMapper.copy(laoSmsDeliver, po);
		laoSmsDeliverDao.insert(po);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lenovo.LAOAPI.interfaces.cmpp.LaoCmppService#pushSmstoPlatform(java.
	 * lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String pushSmstoPlatform(String targetMsisdn, String sendMsisdn, String smsId, String smsContent,
			String pushDate) {
		if (StringUtils.isBlank(targetMsisdn) || StringUtils.isBlank(sendMsisdn) || StringUtils.isBlank(smsId)) {
			return errorCode("param is null");
		}
		logger.info("**pushSmstoPlatform****" + targetMsisdn + "---" + sendMsisdn + "----" + smsId + "----" + smsContent
				+ "----" + pushDate);

		// 通过smsId在短信表获取custId
		logger.info("****smsId******" + Long.valueOf(smsId));
		logger.info("****smsIdgetLong******" + Long.getLong(smsId));
		LaoSmsInfo smsInfo = laoSmsInfoMapper.selectByPrimaryKey(Long.valueOf(smsId));

		// logger.info("smsInfo ChannelCustId:"+smsInfo.getChannelCustId());
		LaoCustConfig lc = null;
		// 通过custId获取push地址
		if (null != smsInfo) {
			logger.info("****smsInfo.getChannelCustId()****" + String.valueOf(smsInfo.getChannelCustId()));
			// lao =
			// laoKey.selectByCustId(String.valueOf(smsInfo.getChannelCustId()));
			lc = laoCustConfigExtMapper.selectByPrimaryKey(smsInfo.getChannelCustId());
			if (null != lc && StringUtils.isNotBlank(lc.getSendsmsCallbackurl())) {
				logger.info("LaoKeyManagement pushUrl:" + lc.getSendsmsCallbackurl());
			} else {
				return errorCode("LaoKeyManagement table is not custId or pushUrl is null");
			}
		} else {
			return errorCode("smsInfo table is not smsId");
		}

		Map<String, String> sms = new HashMap<String, String>();
		sms.put("targetMsisdn", targetMsisdn);
		sms.put("sendMsisdn", sendMsisdn);
		sms.put("smsId", smsId);
		sms.put("smsContent", smsContent);
		sms.put("pushDate", pushDate);
		sms.put("isReturn", "1");
		JSONObject json = JSONObject.fromObject(sms);
		// http调用第三方地址
		String rspMessage = "";
		logger.info("request json:" + json.toString());
		logger.info("REQUEST>>>>>>" + lc.getParaName2());
		try {
			if ("GET".equals(lc.getParaName2())) {
				logger.info("GET REQUEST>>>>>>" + lc.getSendsmsCallbackurl());
				smsContent = URLEncoder.encode(smsContent, "UTF-8");
				pushDate = URLEncoder.encode(pushDate, "UTF-8");
				String httpUrl = lc.getSendsmsCallbackurl() + "?targetMsisdn=" + targetMsisdn + "&sendMsisdn="
						+ sendMsisdn + "&smsId=" + smsId + "&smsContent=" + smsContent + "&pushDate=" + pushDate;
				logger.info("GET REQUEST>>>>>>" + httpUrl);
				rspMessage = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			} else if ("POST".equals(lc.getParaName2())) {
				logger.info("POST REQUEST>>>>>>" + lc.getSendsmsCallbackurl());
				HttpPostSend http = new HttpPostSend();
				rspMessage = http.invoke(json.toString(), lc.getSendsmsCallbackurl());
				logger.info("POST REQUEST>>>>>>" + rspMessage);
			} else {
				logger.info("POST REQUEST>>>>>>************************");
				HttpPostSend http = new HttpPostSend();
				rspMessage = http.invoke(json.toString(), lc.getSendsmsCallbackurl());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return errorCode("http time out or server is shutdown");
		}

		logger.info("rspMessage=" + rspMessage);
		return rspMessage;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {

		// http://144.123.37.126:5019/api/ReceiveSms?targetMsisdn=1064899193613&sendMsisdn=1064888948123
		// &smsId=3071407570636373&smsContent=666778&pushDate=2017-04-21
		// 14:07:57
		/*
		 * Map<String,String> sms = new HashMap<String,String>();
		 * sms.put("targetMsisdn", "1064899193613"); sms.put("sendMsisdn",
		 * "1064888948123"); sms.put("smsId", "3071407570636373");
		 * sms.put("smsContent", "666778"); String targetMsisdn =
		 * "1064899193613"; String sendMsisdn ="1064888948123"; String smsId =
		 * "3071407570636373"; String smsContent ="666778"; String pushDate =
		 * "2017-04-21 14:07:57"; pushDate = URLEncoder.encode(pushDate,
		 * "UTF-8"); String httpUrl =
		 * "http://144.123.37.126:5019/api/ReceiveSms"+"?targetMsisdn="+
		 * targetMsisdn+"&sendMsisdn="
		 * +sendMsisdn+"&smsId="+smsId+"&smsContent="+smsContent+"&pushDate="+
		 * pushDate; //rspMessage =
		 * HttpClientUtil.getInstance().sendHttpGet(httpUrl); //String httpUrl =
		 * "http://144.123.37.126:5019/api/ReceiveSms?targetMsisdn=132232323&sendMsisdn=123&smsId=123&smsContent=abc&pushDate=2099";
		 * System.out.println(httpUrl); String str1 =
		 * HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		 * 
		 * System.out.println(str1); LaoCmppServiceImpl c = new
		 * LaoCmppServiceImpl(); //String str = c.errorCode(
		 * "http time out or server is shutdown"); //System.out.println(str);;
		 * JSONObject jo1 = JSONObject.fromObject(str1);
		 * System.out.println("**转换JSON**"+jo1); JSONObject jay =
		 * jo1.getJSONObject("resultInfo");
		 */
	}

	/**
	 * 
	 * @param desc
	 *            错误描述
	 * @return 程序在报错的时候 返回的json参数
	 */
	private String errorCode(String desc) {
		Map<String, String> m = new HashMap<String, String>();
		m.put("respCode", "9999");
		m.put("respDesc", desc);
		Map<String, Object> mm = new HashMap<String, Object>();
		mm.put("resultInfo", m);
		String st = JSONObject.fromObject(mm).toString();
		return st;
	}

	@Override
	public LaoSmsDeliverDto selectBySrcNumber(Long srcNumber) {
		LaoSmsDeliver laoSmsDeliver = laoSmsDeliverExtDao.selectBySrcNumber(srcNumber);
		LaoSmsDeliverDto laoSmsDeliverDto = new LaoSmsDeliverDto();
		BeanMapper.copy(laoSmsDeliver, laoSmsDeliverDto);
		return laoSmsDeliverDto;
	}

	@Override
	public void updateSmsDeliver(LaoSmsDeliverDto laoSmsDeliver) {
		LaoSmsDeliver laoSmsDeliverPo = new LaoSmsDeliver();
		BeanMapper.copy(laoSmsDeliver, laoSmsDeliverPo);
		laoSmsDeliverDao.updateByPrimaryKeySelective(laoSmsDeliverPo);
	}

	@Override
	public List<LaoSmsInfoDto> queryAcceptSms(HashMap<String, Object> paraMap) {
		List<Map<String, Object>> smsList = laoSmsInfoExtDao.queryqueryAcceptSms(paraMap);

		List<LaoSmsInfoDto> dtos = new ArrayList<LaoSmsInfoDto>();
		if (null != smsList && smsList.size() > 0) {
			for (Map<String, Object> map : smsList) {
				System.out.println(map.toString());

				LaoSmsInfoDto dto = new LaoSmsInfoDto();
				Object CHANNEL_CUST_ID=map.get("CHANNEL_CUST_ID");
				Object OPER_ID = map.get("OPER_ID");
				Object ICCID = map.get("ICCID");
				Object SMS_ID = map.get("SMS_ID");
				Object MSISDN = map.get("MSISDN");
				Object PUSH_DATE = map.get("PUSH_DATE");
				Object PUSH_DESC = map.get("PUSH_DESC");
				Object PUSH_STATE = map.get("PUSH_STATE");
				Object SMS_CONTENT = map.get("SMS_CONTENT");
				 
				if (SMS_CONTENT != null) {
					dto.setSmsContext(SMS_CONTENT.toString().replaceAll("<", "&#60"));
				} else {
					dto.setSmsContext(null);
				}
				if (OPER_ID != null) {
					dto.setOperId(OPER_ID.toString());
				} else {
					dto.setOperId(null);
				}
				if (CHANNEL_CUST_ID != null) {
					dto.setChannelCustId(Long.valueOf(CHANNEL_CUST_ID.toString()));
				} else {
					dto.setChannelCustId(null);
				}
				if (ICCID != null) {
					dto.setIccid(ICCID.toString());
				} else {
					dto.setIccid(null);
				}
				if (SMS_ID != null) {
					dto.setSmsId(Long.valueOf(SMS_ID.toString()));
				} else {
					dto.setSmsId(null);
				}
				if (MSISDN!=null) {
					dto.setMsisdn(MSISDN.toString());
					
				}else{
					dto.setMsisdn(null);
				}
				if (PUSH_DATE!=null) {
					dto.setSendTime((Date)PUSH_DATE);
					dto.setUpdateTime((Date) PUSH_DATE);
					
				}else{
					dto.setSendTime(null);
					dto.setUpdateTime(null);
				}
				if (PUSH_DESC!=null ) {
					if (PUSH_DESC.toString().contains("LaoKeyManagement")) {
						dto.setResultInfo("未推送");
					}else{
						
						dto.setResultInfo(PUSH_DESC.toString());
					}
					
				}else{
					dto.setResultInfo(null);
				}
				if (PUSH_STATE!=null) {
					dto.setDealTag(PUSH_STATE.toString());
					
				}else{
					dto.setDealTag(null);
				}
				dtos.add(dto);
			}
		}
		return dtos;
	}

	@Override
	public int countAcceptSms(Map<String, Object> paraMap) {
		int res = laoSmsInfoExtDao.countAcceptSms(paraMap);
		return res;
	}

}
