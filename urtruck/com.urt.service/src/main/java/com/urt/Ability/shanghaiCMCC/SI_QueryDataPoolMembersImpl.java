package com.urt.Ability.shanghaiCMCC;
/**
 * 流量池成员查询
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.urt.Ability.shanghaiCMCC.utils.ConstantUtil;
import com.urt.interfaces.ShangHaiCMC.SI_QueryDataPoolMembers;
import com.urt.utils.HttpClientUtil;
@Service(value="sI_QueryDataPoolMembers")
public class SI_QueryDataPoolMembersImpl implements SI_QueryDataPoolMembers {

	@Override
	public String queryDataPoolMembers(String eid, List<String> pool_id,
			String msisdn) {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Request_ID", "");
		jsonObject.put("Request_Date_Time", "");
		jsonObject.put("EID", eid);
		jsonObject.put("Pool_ID", pool_id);
		
		//封装Multi_Records的数据结构
		if(msisdn != null && msisdn != ""){
			Map<String,String> maps = new HashMap<>();
			maps.put("MSISDN", msisdn);
			List<Map<String,String>> list = new ArrayList<>();
			list.add(maps);
			jsonObject.put("Multi_Records", list);
		}
		System.out.println(jsonObject.toJSONString());
		String httpUrl = ConstantUtil.URL + "/SI_QueryDataPoolMembers";
		HttpClientUtil instance = HttpClientUtil.getInstance();
		String result = instance.sendHttpPost(httpUrl, jsonObject.toJSONString());
		//对返回值解析  插入数据库
		JSONObject json = JSON.parseObject(result);
		//包含流量池记录就不包含车机归属流量池信息
		if(json.containsKey("Pool_Records")){
			System.out.println("pool");
		}
		if(json.containsKey("Multi_Records")){
			System.out.println("Multi");
		}
		
		/*测试返回数据
		 * {"Pool_Records":[{"Pool_ID":"0000023","MemAddrOfPo":"ftp://base_url"},{"Pool_ID":"0000023","MemAddrOfPo":"ftp://base_url"},{"Pool_ID":"0000023","MemAddrOfPo":"ftp://base_url"},{"Pool_ID":"0000023","MemAddrOfPo":"ftp://base_url"},{"Pool_ID":"0000023","MemAddrOfPo":"ftp://base_url"}],
		"Result_Date_Time":"20170814145653","Multi_Records":[{"PoolID":"2222222","Msisdn":"18723456743"},{"PoolID":"2222222","Msisdn":"18723456743"},{"PoolID":"2222222","Msisdn":"18723456743"},{"PoolID":"2222222","Msisdn":"18723456743"},{"PoolID":"2222222","Msisdn":"18723456743"}],
		"Result_Code":"S0000","EID":"c09924c09c034b1f83ad052ae078de1412345678","Result_Description":"成功"}*/
		
		return result;
	}
}
