package com.urt.storm.lbs;

import java.util.Date;
import java.util.Map;

import com.alibaba.dubbo.common.utils.StringUtils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.urt.dto.LaoBatchDatadetailDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.batch.BatchService;
import com.urt.interfaces.lbs.LocationSlisService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

public class LbsQueryLocationBolt extends BaseRichBolt {
	private static Logger logger = LoggerFactory.getLogger(LbsQueryLocationBolt.class);

	private BatchService batchService;
	private UserService userService;
	private LocationSlisService locationSlisService;
	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"classpath:applicationContext-dubbo-service.xml");

	private transient OutputCollector collector;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		this.batchService = applicationContext
				.getBean(BatchService.class);
		this.userService = applicationContext
				.getBean(UserService.class);
		this.locationSlisService = applicationContext
				.getBean(LocationSlisService.class);
	}

	@Override
	public void execute(Tuple input) {
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>进入LbsQueryLocationBolt的execute函数");
		String iccid = "";
		String batchId = "";
		LaoUserDto laoUser = null;
		LaoBatchDatadetailDto dto = new LaoBatchDatadetailDto();
		dto.setBatchId(1L);
		try {
			String str = (String) input.getValue(0);
			if ("".equals(str) || str.length() < 15) {
				dto.setRemark("-99999");//重复数据
				collector.ack(input);
				return;
			}
			logger.debug(str);
			JSONObject json = new JSONObject(str);
			if (!json.isNull("iccid")) {
				iccid = json.getString("iccid");
				laoUser = userService.getLaoUserDtoByIccid(iccid);
			}
			if (!json.isNull("batchId")) {
				batchId = json.getString("batchId");
				dto.setBatchId(json.getLong("batchId"));
			}
			//在批量详细表中查询，如果同一批次有记录，就不执行
			if(!batchService.duplicateRemoval(iccid, batchId)){
				logger.info(">>>>>>>>>>>>>>>>>>>>>批量详细表中查询，如果同一批次有记录，就不执行");
				dto.setRemark("-99999");//重复数据
				collector.ack(input);
    			return ;
			}
			
			String addrStr = locationSlisService.queryLocationjedisCluster(iccid);
			logger.info(">>>>>>>>>>>>>>>>>>>>>queryLocationjedisCluster,iccid="+iccid+";addrStr="+addrStr);
			// 调用百度工具查询地址名称
			String addrName = locationSlisService.queryByBaiduUtil(addrStr);
			logger.info(">>>>>>>>>>>>>>>>>>>>>queryLocationjedisCluster,addrStr="+addrStr+";addrName="+addrName);
			if (!StringUtils.isBlank(addrName)) {
				dto.setParaCode29(addrName);// 备用字段 填写地址名称
			}
			dto.setDealTag("2");
			dto.setRemark("ok");
			dto.setParaCode30(addrStr);// 备用字段 填写接口返回坐标
		} catch (Exception e) {
			dto.setDealTag("3");
			dto.setRemark("抛出异常");
			e.printStackTrace();
		}finally{
			if(!("-99999").equals(dto.getRemark())){
				// 插入批量任务明细表
				if (laoUser != null) {
					dto.setChannelCustId(laoUser.getChannelCustId());
					dto.setCustId(laoUser.getCustId());
					dto.setMsisdn(laoUser.getMsisdn());
					dto.setUserId(laoUser.getUserId());
				}
				dto.setIccid(iccid);
				dto.setRecvTime(new Date());
				dto.setOperId("admin");
				dto.setUpdateTime(new Date());
				dto.setTradeTypeCode((short)1135);
				dto.setDatadetailId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID)));
				logger.info(">>>>>>>>>>>>>>>>>>>>>插入批量任务明细表"+dto.toString());
				batchService.saveBatchDataDetail(dto);
			}
			collector.ack(input);
		}
	}


	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("message"));
	}
	
}
