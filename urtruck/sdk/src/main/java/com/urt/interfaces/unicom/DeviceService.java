package com.urt.interfaces.unicom;

import java.util.List;
import java.util.Map;

import com.urt.dto.IccidLibDto;
import com.urt.dto.unicom.DeviceDto;

/**
 * 设备激活方法
 * 
 * @author sunhao 2016年8月23日10:31:06
 */
public interface DeviceService {
	// 校验手机的iccid与imei API
	public Map<String, String> verifySim(String iccid, String imei, String st);

	// 设备状态查询 API
 	public Map<String, Object> getDeviceStatus(DeviceDto device);

	// 赠送流量 API
	public Map<String, Object> activeInetAccess(DeviceDto device);

	// 切换apn 开网 API
	public Map<String, Object> openInternet(DeviceDto device);
	
	//根据lenovoid 查询状态
	public Map<String, Object> queryLenovoidStatus(String lenovoid);
	
	/**
     * 简单读取
     * @throws Exception 
     */
	public String getValue(String key) throws Exception;
	/**
	 * 简单保存
	 * @param key
	 * @param value
	 * @throws Exception 
	 */
	public void set(String key,String value) throws Exception;
	/**
	 * 删除key
	 * @param key
	 * @throws Exception
	 */
	public void del(String key) throws Exception;
	/**
	 * 判断键是否存在
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Boolean exists(String key) throws Exception;
	 /**
	   * <p>设置key value并制定这个键值的有效期</p>
	   * @param key
	   * @param value
	   * @param seconds 单位:秒
	   * @return 成功返回OK 失败和异常返回null
	   */
	public String  setex(String key,int seconds,String value) throws Exception;
	
	public boolean checkSign(String custId, String iccid, String sign, String logo);
}
