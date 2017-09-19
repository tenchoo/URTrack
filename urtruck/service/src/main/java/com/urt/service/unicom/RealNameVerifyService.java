package com.urt.service.unicom;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urt.Ability.unicom.util.Base64;
import com.urt.Ability.unicom.util.HttpClientUtil;
import com.urt.Ability.unicom.util.ThreedesUtils;
import com.urt.mapper.RealNameVerifyMapper;
import com.urt.po.RealNameVerify;
/**
 * 
 * @author zhaoyufei
 *
 */
@Service("realNameVerifyService")
@Transactional
public class RealNameVerifyService {
	
	private static final String REQURL = "http://www.lenauth.com/idcard-service/services";
	private static final String USERKEY = "609073FBE37F49D7B38D428934690930";
	private static final String USERSECRET = "lYxYX8mR2SdsKnCo";
	
	@Autowired
	private RealNameVerifyMapper realNameVerifyDao;
	
	/**
	 * 
	 * @param name
	 * @param cardNo
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> verifyName(String name,String cardNo) throws Exception{
		String nameSecret="";
		String cardScert="";
		if(name!=null&&!name.trim().equals("")){
				byte[] encrypt3desName = ThreedesUtils.encrypt3DES(name.getBytes("UTF-8"), USERSECRET);
				nameSecret = Base64.encode(encrypt3desName);
		}
		if(cardNo!=null&&!cardNo.trim().equals("")){
				byte[] encrypt3desCard = ThreedesUtils.encrypt3DES(cardNo.getBytes("UTF-8"), USERSECRET);
				cardScert = Base64.encode(encrypt3desCard);
		}
		Map<String,String>  map = new HashMap<String, String>();
		String date = new SimpleDateFormat("yyyyMMddhhmm").format(new Date()).toString();
		map.put("name", nameSecret);
		map.put("cardNo", cardScert);
		map.put("userKey", USERKEY);
		map.put("clienttime", date);
		map.put("method", "verify");
		String  sign = HttpClientUtil.sign(map,"lYxYX8mR2SdsKnCo");
        map.put("v", "1.0");
        map.put("sign", sign);		
		Document sendHttpPostXML = HttpClientUtil.sendHttpPostXML(REQURL, map);
		
		
		//解析xml文件
		Element root = sendHttpPostXML.getRootElement();
//		System.out.println(root.toString());
		String result = root.elementText("result");
//		String photoUrl = root.elementText("photo");
	    Map<String,String> resultMap=new HashMap<String,String>();
	    resultMap.put("result", result);
// 	    resultMap.put("photoUrl", photoUrl);
	    return resultMap;
	    
	}
	
	public boolean addRealName(RealNameVerify realNameVerify)throws Exception{
		realNameVerifyDao.insert(realNameVerify);	
		return true;	
	}
	
	public boolean updateRealName(RealNameVerify realNameVerify)throws Exception{
		realNameVerifyDao.updateByPrimaryKey(realNameVerify);
		return true;		
	}
	
	public  RealNameVerify findRealNameByUserId(String userId)throws Exception{
		String hql = " from RealNameVerify where userId=?";				
		return realNameVerifyDao.doQueryUnique(userId);
	}
	
	public boolean addOrUpdate(RealNameVerify realNameVerify)throws Exception{
		RealNameVerify findRealNameByUserId = this.findRealNameByUserId(realNameVerify.getUserid());
		if(null == findRealNameByUserId){
			realNameVerify.setCreatetime(new Date());
			realNameVerifyDao.insert(realNameVerify);
		}else{
			realNameVerify.setUpdatetime(new Date());
			realNameVerifyDao.updateByPrimaryKey(realNameVerify);
		}
		return false;
	}
}
