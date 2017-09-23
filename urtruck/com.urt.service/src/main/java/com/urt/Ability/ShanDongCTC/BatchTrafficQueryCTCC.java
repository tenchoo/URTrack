package com.urt.Ability.ShanDongCTC;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.jetty.util.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.urt.Ability.ShanDongCTC.Utils.ConstantUtil;
import com.urt.Ability.ShanDongCTC.Utils.DesUtils;
import com.urt.Ability.collect.BatchTrafficQuery;
import com.urt.dto.traffic.LaoSimDateDetailDto;
import com.urt.mapper.LaoOperatordealLogMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.mapper.ext.LaoUserOperatorPlanExtMapper;
import com.urt.po.LaoOperatordealLog;
import com.urt.utils.HttpClientUtil;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

/**
 * 电信流量查询
 * 
 * @author lingfei
 * @date 2017年02月09日
 */
@Service("batchTrafficQueryCTCC")
public class BatchTrafficQueryCTCC extends BatchTrafficQuery<Object> {

	Logger log = Logger.getLogger(getClass());

	@Autowired
	private LaoUserExtMapper laoUserDao;
	@Autowired
	private LaoUserOperatorPlanExtMapper laoUserOperatorPlanExtDao;
	@Autowired
	private LaoOperatordealLogMapper laoOperatordealLogDAO;
	
	@Override
	protected void queryDayUseCount(LaoSimDateDetailDto laoSimDateDetailDto) {
		String iccid ="0";
		String startDate ="";// yyyyMMdd
		String endDate ="";// yyyyMMdd
		if (laoSimDateDetailDto != null) {
			iccid = laoSimDateDetailDto.getIccid();
			startDate = laoSimDateDetailDto.getData().replace("-", "");
			endDate = laoSimDateDetailDto.getData().replace("-", "");
		}
		LaoOperatordealLog logger = new LaoOperatordealLog();
		logger.setCreateDate(new Date());
		logger.setIccid(iccid);
		logger.setSuccess("1");
		logger.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
		logger.setOperatorType("5");// 1订购2发短信3状态变更4查询卡状态5流量查询
		logger.setOperatorId(laoSimDateDetailDto.getOperatorsId()+"");
		try {
			String method = "queryTrafficByDate";
			// 调用des加密工具类
			DesUtils des = new DesUtils();
			/*
			 * sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
			 * 的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。
			 */
			String[] arr = { iccid, ConstantUtil.userIdValue,
					ConstantUtil.passWordValue, method };
			String resultStr = DesUtils.naturalOrdering(arr);
			// 密码加密结果
			String passwordEnc = des.strEnc(ConstantUtil.passWordValue,
					ConstantUtil.firstKey, ConstantUtil.secondKey,
					ConstantUtil.thirdKey);
			// sign加密结果
			String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey,
					ConstantUtil.secondKey, ConstantUtil.thirdKey);
			// 拼接httpUrl
			String httpUrl = ConstantUtil.URL + "/query.do" + "?"
					+ ConstantUtil.method + "=" + method + "&"
					+ ConstantUtil.userId + "=" + ConstantUtil.userIdValue + "&"
					+ ConstantUtil.passWord + "=" + passwordEnc + "&"
					+ ConstantUtil.sign + "=" + signEnc + "&"
					+ ConstantUtil.startDate + "=" + startDate + "&"
					+ ConstantUtil.endDate + "=" + endDate + "&";
			logger.setInputParameters(httpUrl);
			httpUrl += ConstantUtil.iccid + "=" + iccid;
			Log.info(httpUrl);
			String retXml = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
			if (!StringUtils.isBlank(retXml)) {
				if (retXml.length() > 1500) {
					logger.setOutputParameters(retXml.substring(0, 1500));
				} else {
					logger.setOutputParameters(retXml);
				}
				if (StringUtils.isBlank(retXml) || retXml.equals("-1") || retXml.equals("-2") || retXml.equals("-3") || retXml.equals("-4")) {
					logger.setResultCode("failed");
					logger.setResultInfo("1");// 0成功1失败
					logger.setSuccess("1"); // 0成功1失败
					return ;
				}
				int in1 = retXml.indexOf("<web:");
				int in2 = retXml.indexOf(">", in1);
				String str1 = retXml.substring(in1, in2 + 1);
				int in3 = retXml.lastIndexOf("</web:");
				String str2 = retXml.substring(in3, in3 + 6);
				retXml = retXml.replace(str1, "").replace(str2, "");
				Map<String, String> map = xmlToMapByDay(retXml);
				if ("0".equals(map.get("IRESULT"))) {
					logger.setResultCode("0");
					logger.setResultInfo("0");// 0成功1失败
					logger.setSuccess("0"); // 0成功1失败
					laoSimDateDetailDto.setDayUseCount(map.get("dayUseCount"));
				} else {
					logger.setResultCode(map.get("IRESULT"));
					logger.setResultInfo("1");// 0成功1失败
					logger.setSuccess("1"); // 0成功1失败
				}
			} else {
				logger.setOutputParameters(retXml);
				logger.setResultCode("failed");
				logger.setResultInfo("1");// 0成功1失败
				logger.setSuccess("1"); // 0成功1失败
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.setResultInfo("调用接口异常");// 0成功1失败
			logger.setSuccess("1"); // 0成功1失败
			return ;
		} finally {
			try {
				// 记录传入传出参数
				laoOperatordealLogDAO.insertSelective(logger);
			} catch (Exception e) {
				log.info("记录传入传出参数异常");
				e.printStackTrace();
			}
		}
	}
	@Override
	protected void queryMonthUseCount(LaoSimDateDetailDto laoSimDateDetailDto) {
		log.info("电信查询方法queryMonthUseCount不作任何操作");
	}
	@Override
	protected void queryMonthDataRemaining(
			LaoSimDateDetailDto laoSimDateDetailDto) {
		log.info("电信查询方法queryMonthDataRemaining不作任何操作");
	}
	@Override
	protected void queryCardMsg(LaoSimDateDetailDto laoSimDateDetailDto) {
		String iccid ="0";
		if (laoSimDateDetailDto != null) {
			iccid = laoSimDateDetailDto.getIccid();
		}
		LaoOperatordealLog logger = new LaoOperatordealLog();
		logger.setCreateDate(new Date());
		logger.setIccid(iccid);
		logger.setSuccess("1");
		logger.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
		logger.setOperatorType("5");// 1订购2发短信3状态变更4查询卡状态5流量查询
		logger.setOperatorId(laoSimDateDetailDto.getOperatorsId()+"");
			try {
				String method = "queryPakage";
				String code = "";
				//调用des加密工具类
				DesUtils des = new DesUtils();
				/*sign参数为method、tel、userId、password(没有加密的密码)等参数经过自然排序后拼接成
		的以逗号分隔的字符串，在通过DES加密算法加密之后所得结果。*/
				String[] arr = {code,ConstantUtil.userIdValue,ConstantUtil.passWordValue,method};
				String resultStr = des.naturalOrdering(arr);
				//密码加密结果
				String passwordEnc = des.strEnc(ConstantUtil.passWordValue, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
				//sign加密结果
				String signEnc = des.strEnc(resultStr, ConstantUtil.firstKey, ConstantUtil.secondKey, ConstantUtil.thirdKey);
				//拼接httpUrl
				String httpUrl = ConstantUtil.URL+"/query.do"+ "?"
						+ConstantUtil.method+"="+method+ "&"
						+ConstantUtil.userId+"="+ConstantUtil.userIdValue+ "&"
						+ConstantUtil.passWord+"="+passwordEnc+ "&"
						+ConstantUtil.sign+"="+signEnc+ "&";
				httpUrl+=ConstantUtil.iccid+"="+iccid;
				Log.info(httpUrl);
				logger.setInputParameters(httpUrl);
				String retXml = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
				if (!StringUtils.isBlank(retXml)) {
					if (retXml.length() > 1500) {
						logger.setOutputParameters(retXml.substring(0, 1500));
					} else {
						logger.setOutputParameters(retXml);
					}
					if (StringUtils.isBlank(retXml) || retXml.equals("-1") || retXml.equals("-2") || retXml.equals("-3") || retXml.equals("-4")) {
						logger.setResultCode("failed");
						logger.setResultInfo("1");// 0成功1失败
						logger.setSuccess("1"); // 0成功1失败
						return ;
					}
				int in1 = retXml.indexOf("<web:");
				int in2 = retXml.indexOf(">", in1);
				String str1 = retXml.substring(in1, in2 + 1);
				int in3 = retXml.lastIndexOf("</web:");
				String str2 = retXml.substring(in3, in3 + 6);
				retXml = retXml.replace(str1, "").replace(str2, "");
				Map<String, String> map = xmlToMapByNow(retXml);
				if ("0".equals(map.get("IRESULT"))) {
					logger.setResultCode("0");
					logger.setResultInfo("0");// 0成功1失败
					logger.setSuccess("0"); // 0成功1失败
					laoSimDateDetailDto.setMonthUseCount(map.get("useCount"));
					laoSimDateDetailDto.setDataRemaining(map.get("dataRemaining"));
				} else {
					logger.setResultCode(map.get("IRESULT"));
					logger.setResultInfo("1");// 0成功1失败
					logger.setSuccess("1"); // 0成功1失败
				}
			} else {
				logger.setOutputParameters(retXml);
				logger.setResultCode("failed");
				logger.setResultInfo("1");// 0成功1失败
				logger.setSuccess("1"); // 0成功1失败
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.setResultInfo("调用接口异常");// 0成功1失败
			logger.setSuccess("1"); // 0成功1失败
			return ;
		} finally {
			try {
				// 记录传入传出参数
				laoOperatordealLogDAO.insertSelective(logger);
			} catch (Exception e) {
				log.info("记录传入传出参数异常");
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @param xmlStr
	 * @return
	 */
	private Map<String, String> xmlToMapByDay(String xmlStr) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// 将xml格式的字符串转换成Document对象
		Document doc = DocumentHelper.parseText(xmlStr);
		// 获取根节点
		Element root = doc.getRootElement();
		// 获取根节点下的所有元素
		List<?> children = root.elements();
		// 循环所有子元素
		if (children != null && children.size() > 0) {
			for (int i = 0; i < children.size(); i++) {
				Element child = (Element) children.get(i);
				if ("TOTAL_BYTES_CNT".equals(child.getName())) {
					map.put("dayUseCount", child.getTextTrim());
				}
			}
		}
		return map;
	}
	
	/**
	 * 
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> xmlToMapByNow(String xmlStr)throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String dataRemaining = "";
		String useCount = "";
		if ("".equals(xmlStr) || xmlStr == null) {
			return map;
		}
		long total_left = 0;
		long total_already = 0;
		// 将xml格式的字符串转换成Document对象
		Document doc = DocumentHelper.parseText(xmlStr);
		// 获取根节点
		Element root = doc.getRootElement();
		// 获取根节点下的所有元素
		List<?> children = root.elements();
		// 循环所有子元素
        DecimalFormat decimalFormat = new DecimalFormat("#0.0");//格式化设置  
		if (children != null && children.size() > 0) {
			for (int i = 0; i < children.size(); i++) {
				Element child = (Element) children.get(i);
				if ("CumulRspList".equals(child.getName())) {
					String left = child.element("CUMULATION_LEFT").getTextTrim().replace("KB", "");
					String already = child.element("CUMULATION_ALREADY").getTextTrim().replace("KB", "");
					total_left = total_left + Long.valueOf(left);
					total_already = total_already + Long.valueOf(already);
				} else {
					map.put(child.getName(), child.getTextTrim());
				}
			}
			dataRemaining = decimalFormat.format(total_left);
			useCount = decimalFormat.format(total_already);
			map.put("dataRemaining", dataRemaining);
			map.put("useCount", useCount);
		}
		return map;
	}
}
