package com.urt.Ability.colony.service;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.urt.Ability.unicom.util.HttpsUtil;
import com.urt.Ability.unicom.util.MD5;
import com.urt.Ability.unicom.util.Rsa;
import com.urt.dto.grpnet.BillStatusQueryOutBean;
import com.urt.dto.grpnet.GetUserDiscntInfoReq;
import com.urt.dto.grpnet.UserDiscntList;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.grpnet.ColonyBillStatusQueryService;
import com.urt.mapper.LaoOperatordealLogMapper;
import com.urt.po.LaoOperatordealLog;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

@Service("colonyBillStatusQueryService")
@Transactional(propagation = Propagation.REQUIRED)
public class ColonyBillStatusQueryServiceImpl implements ColonyBillStatusQueryService{
	// 请求接口地址
	private final static String httpsUrl = "https://202.96.18.121:20000/openplatform/rest/1.0/allPreferentialQuery";
	// 签名私钥
	private final static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKBqPF3aYBSBJE7IuEQx4qwxpJvWH7Z9f/YUqKPEC87dw0vOKug0eWCWhfdwe7dRtkKXPn4t4B4WIreMYBJz4ibUeFn3vqcY4+2FrpPEJMQ1ahDypRBOC+5YrPGcVXSzUG7tJBT7bKLBPxu3WraAzX8edYK8PAnc630ipdkvfMbTAgMBAAECgYBOTKycjvv45jRHtFelCch/jlevrSubktSD0/+guA+fcMVj2gU9hGd+itpnNeNdTqgtBs/9HP1ZEirt9rnqJ+BJl+FChcHeHno7VIiyu/TNUpyvHmEHqK/ZBHuzCx2DorBOyAycf9PSpeYbyvXW0KoBUpbRrF6nYJ0mhIKYZa96YQJBANBzVn/XHYUXAStOEMTdSDf71ErmpvB9oKSgiGgAooKZNUbOwZtz0alwwt+tmTzjunuaT8GszmuRxc6jgOl0q4MCQQDFAdE2AbUX8nUrgTFbG0RT2CGpVcygNvjMqs4g65IkhR2/0aTfE7npR63hEP+aYmK71vatL7B7GgFTBh9zqAZxAkEAwGalIYyhr7WDG8CLk9oXja0Azz3EOtgKDcoq/awQU/EsDrZKV7z4eageBy2J8nzUJMBS+5YQVZOQe+HI3DMXFwJAXsZJB1o67JJPm+ZDADlV3aAUUYpdLpZDivKAx4WMgNUpQqZwUoEC5x78n1G2JWc/aG45rYdLt20DRhWj+9fRgQJBALVvz0lNoKSB00V7v4KYUGEKTo1EGq+G2+/ajSbpT/uYuCfjr14T0Knj/yzVVDAIAf9oJg/aesZZQGCWNggrGTg=";	
	// AppKey
	private final static String appkey = "10001014";
	
	@Autowired
	private LaoOperatordealLogMapper laoOperatordealLogDAO;
	@Autowired
	private UserService userService;

	/**
	 * 调用第三方接口
	 * 
	 * @param InfoReq
	 * @return
	 */
	@Override
	public Map<String, Object> queryColonyBillStatus(GetUserDiscntInfoReq infoReq) {
		Map<String, Object> map = null;
		BillStatusQueryOutBean outBean = null ;
		LaoOperatordealLog logger= new LaoOperatordealLog();
		logger.setIccid("0");
		logger.setOperatorType("6");// 1订购2发短信3状态变更4查询卡状态5流量查询6集群网微信厅接口
		logger.setSuccess("1"); // 0成功1失败
		try {
			// 1.参数校验
			doCheck(infoReq);
			if (!StringUtils.isBlank(infoReq.getPhoneNo())) {
				logger.setIccid(infoReq.getPhoneNo());
			}
			// 2.签名
			// 2a.将参数明文转化为Json串,字典顺序进行排序
			StringBuilder strBuil = new StringBuilder();
			SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
			strBuil.append("DiscntId=").append(infoReq.getDiscntId())
				.append("&OperDepart=").append(infoReq.getOperDepart())
				.append("&Operator=").append(infoReq.getOperator())
				.append("&PhoneNo=").append(infoReq.getPhoneNo())
				.append("&PhoneType=").append(infoReq.getPhoneType())
				.append("&TradeId=").append(infoReq.getTradeId())
				.append("&appKey=").append(appkey)
				.append("&status=").append("1")//status=1 生产环境
				.append("&timeStamp=").append(sdf.format(new Date()))
				;
			String text = strBuil.toString();
			String textEncoder = "";
			// 2b.做URLEncoder操作
			textEncoder = URLEncoder.encode(text, "UTF-8");
			// 2c.进行md5运算
			String md5Str = MD5.sign(textEncoder, "", "UTF-8");
			// 2d.rsa加密
			String sign = Rsa.sign(md5Str, privateKey, "UTF-8");
			// 2e.再转码
			sign = URLEncoder.encode(sign, "UTF-8");// 本地测试可以不用encode
			// 3.拼接参数报文串。
			text = text + "&sign=" + sign;
			logger.setInputParameters("httpsUrl=" + httpsUrl + ";text=" + text);
			String retJson = "";
			try {
				retJson = HttpsUtil.doPost(httpsUrl, text, "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
				logger.setResultInfo("接口请求失败!");
			}
			logger.setOutputParameters(retJson);
			Gson gson = new Gson();
			outBean = gson.fromJson(retJson,
					new TypeToken<BillStatusQueryOutBean>() {}.getType());
			if (!StringUtils.isBlank(retJson)) {
				if (outBean.getResCode() != null) {
					// 4.参数日志记录
					logger.setResultCode("1"); // 0成功1失败
					logger.setResultInfo("ResCode:"+outBean.getResCode()+";ResMsg:"+outBean.getResMsg());
					return null;
				}
				if (outBean != null ) {
					// 4.参数日志记录
					logger.setResultCode("0"); // 0成功1失败
					logger.setResultInfo(outBean.getResMsg());
					if (outBean.getGetUserDiscntInfoRsp() != null) {
						if ("00000".equals(outBean.getGetUserDiscntInfoRsp().getReturnCode())) {
							map = new HashMap<String, Object>();
							logger.setSuccess("0"); // 0成功1失败
							List<UserDiscntList> list = outBean.getGetUserDiscntInfoRsp().getUserDiscntList();
							if (list != null && list.size() > 0) {
								for (int i = 0; i < list.size(); i++) {
									UserDiscntList userDiscnt = list.get(i);
									double usedDiscnt = 0;
									double excess = 0;
									if (!StringUtils.isBlank(userDiscnt.getUsedDiscnt())) {
										usedDiscnt = Double.parseDouble(userDiscnt.getUsedDiscnt());
									}
									if (!StringUtils.isBlank(userDiscnt.getExcess())) {
										excess = Double.parseDouble(userDiscnt.getExcess());
									}
									if (!StringUtils.isBlank(userDiscnt.getDiscntUnit())) {
										excess = Double.parseDouble(userDiscnt.getExcess());
									}
									if ("1".equals(userDiscnt.getDiscntTypeCode())) {
										String soundsDiscnt = (int)(usedDiscnt + excess)+"";
										map.put("soundsDiscnt",soundsDiscnt + userDiscnt.getDiscntUnit());
									} else if ("2".equals(userDiscnt.getDiscntTypeCode())) {
										String messageDiscnt = (int)(usedDiscnt + excess)+"";
										map.put("messageDiscnt",messageDiscnt + userDiscnt.getDiscntUnit());
									} else if ("4".equals(userDiscnt.getDiscntTypeCode())) {
										DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");//格式化设置
										String flowDiscnt = decimalFormat.format((usedDiscnt + excess) / 1024);
										map.put("flowDiscnt",flowDiscnt + "MB");
									}
								}
								if (map.get("soundsDiscnt") == null) {
									map.put("soundsDiscnt","0分钟");
								}
								if (map.get("messageDiscnt") == null) {
									map.put("messageDiscnt","0条");
								}
								if (map.get("flowDiscnt") == null) {
									map.put("flowDiscnt","0MB");
								}
							}
						} else {
							// 4.参数日志记录
							logger.setResultCode("1"); // 0成功1失败
							logger.setResultInfo(outBean.getResCode()+outBean.getResMsg());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			logger.setCreateDate(new Date());
			logger.setId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID)));
			laoOperatordealLogDAO.insertSelective(logger);
		}
		return map;
	}

	/**
	 * 参数校验
	 * 
	 * @param InfoReq
	 * @throws Exception 
	 */
	private void doCheck(GetUserDiscntInfoReq InfoReq) throws Exception {
		if (InfoReq == null) {
				throw new Exception("入参不能为空！");
		} else {
			if (StringUtils.isBlank(InfoReq.getDiscntId())) {
					throw new Exception("参数DiscntId不能为空！");
			}
			if (StringUtils.isBlank(InfoReq.getOperator())) {
					throw new Exception("参数Operator不能为空！");
			}
			if (StringUtils.isBlank(InfoReq.getOperDepart())) {
					throw new Exception("参数OperDepart不能为空！");
			}
			if (StringUtils.isBlank(InfoReq.getPhoneNo())) {
					throw new Exception("参数PhoneNo不能为空！");
			}
			if (StringUtils.isBlank(InfoReq.getPhoneType())) {
					throw new Exception("参数PhoneType不能为空！");
			}
			if (StringUtils.isBlank(InfoReq.getTradeId())) {
					throw new Exception("参数TradeId不能为空！");
			}
		}
	}
}
