package com.urt.web.quartz;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.urt.dto.LaoBatchDataDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.batch.BatchService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
public class BatchCardInfo{
	@Autowired
	private UserService  userService;
	/*@Autowired
	private CardInfoUpdateProducer cardInfoUpdateProducer;*/
	
	@Autowired
	BatchService batchService;
	public void batchCardInfo() {
		
		System.out.println("》》》》》》》》》》》》》》》开启批量刷新数据之旅");
		
		//获取卡信息
		int total=userService.getCardCount();
		LaoBatchDataDto dto=new LaoBatchDataDto();
		Long id = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
		dto.setBatchId(id);
		dto.setRecvTime(new Date());
		dto.setSumNum((long)total);
		dto.setOperId("admin");
		dto.setTradeTypeCode((short)1);
		batchService.saveBatchData(dto);
		/*batchService.sendMsg(total,id);*/
		/*CardInfoProducer cardInfoUpdateProducer=new CardInfoProducer();
		cardInfoUpdateProducer.init();*/
		batchService.sendCardMsg(total,id);
		
		//获取用户信息
		int userTotal=userService.getCardCount();
		LaoBatchDataDto dataDto=new LaoBatchDataDto();
		Long batchId = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
		dataDto.setBatchId(batchId);
		dataDto.setRecvTime(new Date());
		dataDto.setSumNum((long)userTotal);
		dataDto.setOperId("admin");
		dataDto.setTradeTypeCode((short)2);
		batchService.saveBatchData(dataDto);
		batchService.sendUserMsg(userTotal, batchId);
	/*	UserInfoProducer userInfoProducer=new UserInfoProducer();
		userInfoProducer.init();*/
		
		
	}
	
}
