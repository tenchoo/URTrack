package com.urt.Ability.ShanDongCTC;
/**
 * 电信卡状态查询
 * @author zhaoyf
 *
 */
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.Ability.ShanDongCTC.CTCService.CTCServiceImpl;
import com.urt.Ability.ShanDongCTC.Utils.ConstantUtil;
import com.urt.Ability.ShanDongCTC.Utils.DesUtils;
import com.urt.Ability.ShanDongCTC.Utils.DocUtil;
import com.urt.Ability.collect.UserQueryCardStatus;
import com.urt.mapper.ext.ServiceStatusExtMapper;
import com.urt.po.ServiceStatus;
import com.urt.utils.HttpClientUtil;
@Service(value="userQueryCardStatusCTCC")
public class UserQueryCardStatusCTCC extends UserQueryCardStatus{
	

	@Autowired
	private ServiceStatusExtMapper serviceStatusExtMapper;

	CTCServiceImpl ctcServiceImpl = new CTCServiceImpl();
	
	Logger log = Logger.getLogger(getClass());
	@Override
	protected ResultMsg sendMessage(Object... args) {
		if (args == null || args.length == 0)
			throw new IllegalArgumentException();
		String iccid = (String) args[0];
		String tel = null;
		ResultMsg msg = new ResultMsg();
		String status = null;
		String code = null;
		String httpUrl = null;
		
		Long beginTime = System.currentTimeMillis();
		log.info("................激活前查询设备状态...................time:"
				+ beginTime);
		try {
//			status = ctcServiceImpl.queryCardStatus(iccid, tel);		
			if(iccid!=null && iccid.trim().length()>0){
				code=iccid;
			}else{
				code=tel;
			}
			String method = "queryCardStatus";
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
			httpUrl = ConstantUtil.URL+"/query.do"+ "?"
				   +ConstantUtil.method+"="+method+ "&"
			       +ConstantUtil.userId+"="+ConstantUtil.userIdValue+ "&"
				   +ConstantUtil.passWord+"="+passwordEnc+ "&"
			       +ConstantUtil.sign+"="+signEnc+ "&";
			msg.setInputMessage(httpUrl);
			if(iccid!=null && iccid.trim().length()>0){
				httpUrl+=ConstantUtil.iccid+"="+iccid;
			}else{
				httpUrl+=ConstantUtil.tel+"="+tel;
			}
			status = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(status.equals("-1") || status.equals("-2") || status.equals("-3") || status.equals("-4")  ){
			msg.setOutMessage(status);
			msg.setOpeartorsDealCode("failed");
			msg.setOpeartorsDealRst("1");
		}else{
			org.jdom.Element rootElement = DocUtil.string2Doc(status).getRootElement();	
			String res = rootElement.getChild("Query_response").getChild("BasicInfo").getChild("result").getText();
			
			if("0" .equalsIgnoreCase(res)){
				String text = rootElement.getChild("Query_response").getChild("prodRecords").getChild("prodRecord").getChild("productInfo").getChild("productStatusCd").getText();
				msg.setSuccess(true);
				msg.setOutMessage(status);
				msg.setOpeartorsDealCode("success");
				msg.setOpeartorsDealRst("0");
				ServiceStatus selectByStatechangeId = serviceStatusExtMapper.selectByStatechangeId(text);
				if(selectByStatechangeId != null){
					msg.setStatus(selectByStatechangeId.getStateCode());
				}else{
					log.info("没有相匹配的状态");
					msg.setStatus("error");
				}	
			}else{
				msg.setOutMessage(status);
				msg.setOpeartorsDealCode("failed");
				msg.setOpeartorsDealRst("1");
			}
		}
		return msg;
	}
	

}
