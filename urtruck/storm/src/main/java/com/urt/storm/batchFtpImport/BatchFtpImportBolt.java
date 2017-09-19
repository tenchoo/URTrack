package com.urt.storm.batchFtpImport;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.urt.dto.LaoBatchDatadetailDto;
import com.urt.dto.grpnet.GrpnetImpbillDto;
import com.urt.interfaces.batch.BatchService;
import com.urt.interfaces.grpnet.BBillService;
import com.urt.interfaces.grpnet.GrpnetImpbillService;

public class BatchFtpImportBolt extends BaseRichBolt {
	private static Logger logger = LoggerFactory.getLogger(BatchFtpImportBolt.class);

	private BBillService bBillService;
	private BatchService batchService;
	private GrpnetImpbillService grpnetImpbillService;
	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"classpath:applicationContext-dubbo-service.xml");

	private transient OutputCollector collector;

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		this.bBillService = applicationContext.getBean(BBillService.class);
		this.batchService = applicationContext.getBean(BatchService.class);
		this.grpnetImpbillService = applicationContext.getBean(GrpnetImpbillService.class);
	}

	@Override
	public void execute(Tuple input) {
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>进入BatchFtpImportBolt的execute函数");
		String str = (String) input.getValue(0);
		if ("".equals(str) || str.length() < 15) {
			collector.ack(input);
			return;
		}
		logger.debug(str);
		String msisdn = "";
		List<GrpnetImpbillDto> listDto = null; 
		try {
			Gson gson = new Gson();
			listDto = gson.fromJson(str,
					new TypeToken<List<GrpnetImpbillDto>>() {}.getType());
			if (listDto == null || listDto.size() == 0) {
				collector.ack(input);
    			return ;
			} else {
				GrpnetImpbillDto dto = listDto.get(0);
				msisdn = dto.getSerialNumber();
				for (int i = 0; i < listDto.size(); i++) {
					GrpnetImpbillDto dtoi = listDto.get(i);
					//在批量详细表中查询，如果同一批次有记录，就不执行
					if(!batchService.duplicateRemovalByMsisdn(msisdn, dtoi.getBillId()+"")){
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>重复跑了,直接ack>>>>>>>>>>>>>>>>>>>>");
						collector.ack(input);
						return ;
					}
				}
			}
			// 记录明细表
			batchService.batchInsert(listDto);
			// 存入账目表
			int in1 = grpnetImpbillService.batchInsert(listDto);
			logger.debug("存入账目表数："+in1);
			// 计算费用,优惠,存入账单表
			int in2 = bBillService.batchInsert(listDto);
			logger.debug("账单表表数："+in2);
			// 先根据手机号查询LAO_USER表 ,若不存在则新添加
			int in3 = bBillService.insertUser(msisdn);
			logger.debug("新添加集群网的用户数："+in3);
			for (int i = 0; i < listDto.size(); i++) {
				// 更新明细表
				GrpnetImpbillDto grpnetImpbillDto = listDto.get(i);
				LaoBatchDatadetailDto datadetaildto = new LaoBatchDatadetailDto();
			/*	if (grpnetImpbillDto != null) {
					long long1 = Long.parseLong(grpnetImpbillDto.getRsrvInfo2());
					datadetaildto.setDatadetailId(long1);
				}*/
				datadetaildto.setDatadetailId(grpnetImpbillDto.getBillId());
				datadetaildto.setDealTag("2");// 0-待处理，1-处理中，2-处理成功，3-处理失败
				datadetaildto.setFlowStatus("4");// 扭转状态:1、数据校验中；2、生成订单中；3、业务处理中；4、业务处理完成；
				datadetaildto.setUpdateTime(new Date());
				int in4 = bBillService.updateDatadetail(datadetaildto);
				logger.debug("更新明细表数："+in4);
			}
			collector.ack(input);
		} catch (Exception e) {
			logger.info("------------------------出异常了:start----------------------------");
			logger.info("------------------------"+e.toString()+"----------------------------");
			logger.info("------------------------出异常了:end----------------------------");

			for (int i = 0; i < listDto.size(); i++) {
				// 更新明细表
				GrpnetImpbillDto grpnetImpbillDto = listDto.get(i);
				LaoBatchDatadetailDto datadetaildto = new LaoBatchDatadetailDto();
//				long long1 = 0;
//				if (grpnetImpbillDto.getRsrvInfo2() != null) {
//					long1 = Long.parseLong(grpnetImpbillDto.getRsrvInfo2());
//				}
				datadetaildto.setDatadetailId(grpnetImpbillDto.getBillId());
				datadetaildto.setDealTag("3");// 0-待处理，1-处理中，2-处理成功，3-处理失败
				datadetaildto.setFlowStatus("4");// 扭转状态:1、数据校验中；2、生成订单中；3、业务处理中；4、业务处理完成；
				datadetaildto.setUpdateTime(new Date());
				bBillService.updateDatadetail(datadetaildto);
			}
			collector.ack(input);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("message"));
	}

}
