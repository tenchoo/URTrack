package com.urt.service.unicom;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lenovo.game.cache.interceptor.annotation.LiveTime;
import com.lenovo.pay.utils.HttpResponseResult;
import com.lenovo.pay.utils.HttpUtil;

@Service("activityCardService")
@Transactional
public class ActivityCardService {
	@Value("${card.request.url}")
	private String cardRequestUrl;

	@LiveTime(time = 60 * 60 * 2, key = "ActivityCardService.getActivityCardDetail")
	public String getActivityCardDetail(String rom, String deviceId,
			String devicemodel, String version) {
		StringBuffer stringBuffer = new StringBuffer();
		if (StringUtils.isNotEmpty(rom))
			stringBuffer.append("rom=").append(rom).append("&");
		if (StringUtils.isNotEmpty(deviceId))
			stringBuffer.append("deviceid=").append(deviceId).append("&");
		if (StringUtils.isNotEmpty(devicemodel))
			stringBuffer.append("devicemodel=").append(devicemodel).append("&");
		if (StringUtils.isNotEmpty(version))
			stringBuffer.append("version=").append(version);

		HttpResponseResult httpResponseResult = HttpUtil.sendPost(
				cardRequestUrl, stringBuffer.toString());
		if (httpResponseResult == null
				|| httpResponseResult.getHttpCode() != 200) {
			return null;
		}
		return httpResponseResult.getMessage();

	}

}
