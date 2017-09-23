package com.urt.interfaces.batch;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoBatchDataDto;
import com.urt.dto.LaoBatchDatadetailDto;
import com.urt.dto.UserOperatorInfoDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.grpnet.GrpnetImpbillDto;

public interface BatchService {

	public int saveBatchData(LaoBatchDataDto dto);

	public int saveBatchDataDetail(LaoBatchDatadetailDto dto);
	
	public int saveBatchUserOperatorPlan(UserOperatorInfoDto dto);

	public String queryCardStatus(IccidLibDto iccid);
	
	public String queryCardStatusByUserDto(LaoUserDto userDto);

	public String queryCardTel(IccidLibDto iccid);
	
	public String queryCardTelByUserDto(LaoUserDto userDto);
	
	public List<Map<String, Object>> queryPlanByUserDto(LaoUserDto userDto);
	
	public void sendCardMsg(int total,long id);
	
	public void sendUserMsg(int total,long id);
	
	public Map<String,Object> batchImport(List<Map<String,Object>> list, String custId, String cardstatus, String ifMaintenance, String accountId, String ifAdmin);
	
	public void updateBatchDate(String startTime,String endTime);
	
	public void updateByBatchId(LaoBatchDataDto dataDto);
	
	public Map<String, Object> queryFailDateList(LaoBatchDatadetailDto datadetailDto,int pageNo,int pageSize);
	
	public Map<String, Object> queryFailDateList();
	
	public void updateBatchDate(Date startDate,Date endDate,LaoBatchDataDto batchDataDto);
	
	public Map<String, Object> queryPage(int pageNo,int pageSize,Date startDate,Date endDate,LaoBatchDataDto batchDataDto);

	Map<String, Object> queryFailDateList(Long batchId);
	
	// 查询lbs
	List<Map<String, Object>> queryLbsList(Long batchId);
	
	//查询状态变更编码 如100 （激活） 120（订购）。。
	public List<String> getTypeCode();
	
	// 批量添加
	public List<GrpnetImpbillDto> batchInsert(List<GrpnetImpbillDto> list);
	
	//去重，批量操作中如果任务重复，不操作
	public boolean duplicateRemoval(String iccid ,String batchId);
	//去重，批量操作中如果任务重复，不操作
	public boolean duplicateRemovalByMsisdn(String msisdn ,String batchId);
	/**
	* 功能描述：批量删除资费计划
	* @author sunhao
	* @date 2017年3月1日 下午3:46:31
	* @param @param list
	* @param @return
	* @throws
	 */
	public void batchRemoveRatePlan(List<Map<String,Object>> list);
}
