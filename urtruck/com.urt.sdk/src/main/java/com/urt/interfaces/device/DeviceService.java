package com.urt.interfaces.device;

import java.util.List;
import java.util.Map;

import org.springframework.expression.spel.ast.BooleanLiteral;

import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoDeviceRelDto;
import com.urt.dto.device.Account;

public interface DeviceService {
	public Account authSt(String authName);
	public Account xdswAuthSt(String authName);
	public boolean lenovoIdCheck(String accountId);
	public String cardStatusChange(String cardStatus, String iccid);
	public Map<String,String> getCurentPlan(String iccid);
	public boolean findImeiLibraryByIccid(String iccid);
	public String getActivityCardDetail(String rom, String deviceId, String devicemodel, String version);
	public Map<String,String> getUnicomNotify(Map<String,String> params,String flag);
	public String initPersonCustomer(String lenovoId,String lenovoLoginId);
	public List<Map<String,Object>> queryIccidList(String lenovoId);
	public LaoDeviceRelDto selectByUserId(Long userId);
	public boolean insertLaoDeviceRel(String userId,String iccid,String deviceid,String lenovoId,String lenovoLoginName);
	public String checkLenovoIccid(Long userId,String iccid, String deviceid,String AccountID,String userName);
	public Long queryCustId(String accountId);
	public boolean insertImeiLibrary(String imei);
}
