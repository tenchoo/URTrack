package com.urt.web.web.trafficQuery;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.urt.dto.LaoBatchDataDto;
import com.urt.interfaces.DongguanCMC3.InterfaceMethod;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.batch.BatchService;
import com.urt.interfaces.traffic.BatchInsertTrafficQueryService;
import com.urt.interfaces.traffic.TimerTrafficQueryService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

public class BatchQueryTraffic {

	@Autowired
	private BatchInsertTrafficQueryService batchInsertService;
	@Autowired
	private TimerTrafficQueryService timerTrafficQueryService;
	@Autowired
	private UserService userService;
	@Autowired
	private BatchService batchService;
	@Autowired
	private InterfaceMethod interfaceMethod;
	/**
	 * 定时执行任务
	 * 
	 */
	public void doBatchQuery() {
		int total = userService.getUserCount();
		Long batchId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID));
		int page = (int) Math.ceil((total - 1)/ 1000 );
		if (page == 0) {
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("begin", 1);
			param.put("end", total);
			List<Map<String, Object>> listMap = userService.queryUserInfo(param);
			for (Map<String, Object> map : listMap) {
				map.put("batchId", batchId);
			}
			// 发送消息给kafka
			timerTrafficQueryService.sendUserMsg(listMap);
		} else {
			for (int i = 1; i <= page + 1; i++) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("begin", (i-1)* 1000 + 1);
				param.put("end", i * 1000);
				List<Map<String, Object>> listMap = userService.queryUserInfo(param);
				for (Map<String, Object> map : listMap) {
					map.put("batchId", batchId);
				}
				// 发送消息给kafka
				timerTrafficQueryService.sendUserMsg(listMap);
			}
		}
		// 插入批量总表
		saveBatchData((long)total, batchId);
	}
	
	
	/**
	 * 存入批量汇总表
	 * 
	 * @param count
	 * @param batchId
	 */
	private void saveBatchData(Long count, Long batchId) {
		LaoBatchDataDto dto = new LaoBatchDataDto();
		dto.setBatchId(batchId);// 批次号
		dto.setDealTag("1");// 处理状态:0-未处理，1-处理中，2-处理完成
		dto.setFailNum(0L);// 失败数量
		dto.setOperId("admin");// 操作人员
		dto.setOperatorsId(0);// 运营商ID
		dto.setRecvTime(new Date());// 初始入表时间
		dto.setRemark("");// 备注
		dto.setResultInfo("");// 处理结果信息
		dto.setRsrvInfo1("");// 保留字段1 :暂填运行商ID
		dto.setRsrvInfo2("");// 保留字段2
		dto.setSuccNum(0L);// 成功数量
		dto.setSumNum(count);// 总记录数
		dto.setTradeTypeCode((short)160);// 业务类型编码
		dto.setUpdateTime(new Date());// 更新时间
		batchService.saveBatchData(dto);
	}
	/**
	 * ftp文件定时任务
	 */
	public void ftpDownload(){
		interfaceMethod.ftpFileDownload();
	}
}
