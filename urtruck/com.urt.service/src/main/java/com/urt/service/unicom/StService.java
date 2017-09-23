package com.urt.service.unicom;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.log.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lenovo.pay.utils.HttpUtil;
import com.urt.Ability.unicom.jsonbean.Account;

@Service("stService")
@Transactional
public class StService {
	@Value("${passort.auth.url}")
	private String authUrl;
	@Value("${lenovoid.realm}")
	private String passportRealm;	
	
	public Account authSt(String authName) {
		try {
			Log.info("lpsust=" + authName + "&realm=" + passportRealm);
			String xml = HttpUtil.sendGetHttps(authUrl + "lpsust=" + authName + "&realm=" + passportRealm);
			return parseXML2Account(xml);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private Account parseXML2Account(String xml) {
		Account account = null;
		// try {
		// XStream stream = new XStream();
		// stream.alias("IdentityInfo", Account.class);
		// account = (Account) stream.fromXML(xml);
		// } catch (Exception e) {
		// System.out.println(e.getMessage());
		// }
//		System.out.println(xml);
		String accountID = StringUtils.substringBetween(xml, "<AccountID>", "</AccountID>");
		String userName = StringUtils.substringBetween(xml, "<Username>", "</Username>");
		String deviceID = StringUtils.substringBetween(xml, "<DeviceID>", "</DeviceID>");
		String verified = StringUtils.substringBetween(xml, "<verified>", "</verified>");
		if (StringUtils.isNotBlank(accountID) && StringUtils.isNotBlank(userName)) {
			account = new Account();
			account.setAccountID(accountID);
			account.setUname(userName);
			account.setUsername(userName);
			account.setDeviceID(deviceID);
			account.setVerified(verified);
		}

		return account;
	}
	public static void main(String[] args) {
		String url = "https://passport.lenovo.com/interserver/authen/1.2/getaccountid?";
//		String realm = "lpc.lenovo.com";
		String realm = "mobileaccess.mvno.lenovo.com";
		String st = "OnLoginResult:ZAgAAAAAAAGE9MTAwMjA1MjEzNDYmYj0xJmM9MSZkPTE2OTY0JmU9RjkxQjgxQTQ2Mzk1RjA2QjkwQUU3MjhBNjIyMTJFRTIxJmg9MTQ1MjY2NjE5Njg4MiZpPTQzMjAwJnE9MCZ1c2VybmFtZT15YW5ndGlhbnRlc3QxMDElNDAxNjMuY29tJmlsPWNulCJE-ll2HPvXqny2xnvoBA&s=e261ae75b8fa285648f2784f6a9d1c4";
		String xml = HttpUtil.sendGetHttps(url + "lpsust=" + st + "&realm=" + realm);
		System.out.println(xml);
	}

}
