package com.urt.service.util;

public class WeixinPayConstants {
	// 微信支付商户开通后 微信会提供appid和appsecret和商户号partner
	public static final String appid = "wxc282e33f9192be5d";// 微信开放平台审核通过的应用APPID
	// 微信接口链接 
	public static final String orderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// openId 是微信用户针对公众号的标识，授权的部分这里不解释
	// 微信支付成功后通知地址 必须要求80端口并且地址不能带参数
	public static final String notifyurl = "http://gla.lenovo.com/glaH5/payCallBack";
	
	// 微信支付成功后通知地址 必须要求80端口并且地址不能带参数
	public static final String weixinPayBackurl = "http://gla.lenovo.com/glaH5AppPay/payCallBack";
	// 微信支付分配的商户号
	public static final String mch_id = "1277879901";
	// 交易类型 或支付类型
	public static final String trade_type = "JSAPI";
	// 编码类型
	public static final String charset = "UTF-8";
	// 加密方法
	public static final String signType = "MD5";
	//API key
	public static final String api_key = "09871234trec789jklAv48748733vbmm";
	public static final String AppSecret = "cb3ab65cb7a5e395eb32992292ea1b19";
}
