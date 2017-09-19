package com.urt.storm.apiMsgLog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.eclipse.jetty.util.log.Log;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.urt.dto.ApiMsgLogDto;
import com.urt.dto.LaoUserGoodsDto;
import com.urt.dto.LaoUserOperatorPlanDto;
import com.urt.dto.Goods.GoodsDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.interfaces.Goods.GoodsService;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.apiMsg.ApiMsgLogService;
import com.urt.storm.batchUpdate.card.CardInfoBolt;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

public class ApiMsgBolt extends BaseRichBolt{
	private static Logger logger = LoggerFactory.getLogger(ApiMsgBolt.class);

	
	private static final long serialVersionUID = 1L;
	
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"classpath:applicationContext-dubbo-service.xml");

	private transient OutputCollector collector;
	
	private UserService userService;
	
	private  ApiMsgLogService apiMsgLogService;
	
	private GoodsService goodsService;
	
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
		this.userService = applicationContext.getBean(UserService.class);
		this.apiMsgLogService = applicationContext.getBean(ApiMsgLogService.class);
		this.goodsService = applicationContext.getBean(GoodsService.class);
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		//执行逻辑
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>进入ApiMsgBolt的execute函数");
		String str = (String) input.getValue(0);
		if ("".equals(str) || str.length() < 15) {
			collector.ack(input);
			return;
		}
		logger.debug(str);
		JSONObject json=new JSONObject();
		Object object=null;
		String iccid="";
		boolean isGoodsId=true;
		LaoUserOperatorPlanDto lastPlan=null;
		try {
			json = new JSONObject(str);
			iccid=json.getString("iccid");
			if("89860616020050732416".equals(iccid)){
				System.out.println("你大爷的");
			}
			object = json.get("GOODS_ID");

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			isGoodsId=false;
			e1.printStackTrace();
			logger.info(">>>>>>>>>>>>>apiMsg异常信息："+e1.getMessage());
		}finally{
			try {
				logger.info(">>>>>>>>>>>>>apiMsg isGoodsId="+isGoodsId);
				if(object!=null && isGoodsId){
					logger.info(">>>>>>>>>>>>>>进入月初初始化流量包的处理逻辑");
					Long goodsId = json.getLong("GOODS_ID");
					Long userId = json.getLong("USER_ID");
					Integer releaseId=json.getInt("GOODS_RELEASE_ID");
					String dateStr = json.getString("START_DATE");
					Date date=new Date(dateStr);
					logger.info(">>>>>>>>>>>>>>>月初初始化operatorPlan的参数：goodsId="+goodsId+";releaseId="+releaseId+";dateStr="+dateStr);
					//模拟订购流程
					boolean flag = apiMsgLogService.order(goodsId, iccid, userId, releaseId,date);
					logger.info(">>>>>>>>>>>>>>>>>>>月初初始化operatorPlans结果是否成功"+flag);
					ApiMsgLogDto apiMsgLog=new ApiMsgLogDto();
					Long id= Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
					apiMsgLog.setMsgId(id);
		    		apiMsgLog.setIccid(iccid);
		    		apiMsgLog.setOperatorId(1);
		    		apiMsgLog.setRecvTime(new Date());
		    		apiMsgLog.setUpdateTime(new Date());
		    		apiMsgLog.setOperatorId(1);
		    		apiMsgLog.setUpdateAccount("admin");
					if(flag){
						apiMsgLog.setDealTag("1");
						apiMsgLog.setDealResult("1");
						
					}else{
						apiMsgLog.setDealTag("0");
						apiMsgLog.setDealResult("0");
					}
					apiMsgLogService.updateByPrimaryKeySelective(apiMsgLog);
				}else{
					logger.info(">>>>>>>>>>>>>>进入二次计费或者不限量,包月限量的处理逻辑");
					String logId=json.getString("id");
					List<GoodsDto> goodsList = userService.getGoodsByIccid(iccid);
					boolean isReturn=false;
					List<GoodsDto> buttoms=new ArrayList<GoodsDto>();
					List<GoodsDto> charg=new ArrayList<GoodsDto>();
					List<GoodsDto> monthPackages=new ArrayList<GoodsDto>();
					for(GoodsDto dto:goodsList){
						if("4".equals(dto.getGoodsType())){
							charg.add(dto);
						}else if("5".equals(dto.getGoodsType())){
							buttoms.add(dto);
						}else if("6".equals(dto.getGoodsType())){
							monthPackages.add(dto);
						}
					}
					logger.info(">>>>>>>>包月限量count"+monthPackages.size()+"不限量count："+buttoms.size()+";二次计费count："+charg.size());
					LaoUserDto laoUserDto = userService.getLaoUserDtoByIccid(iccid);
					//包月限量
					for(GoodsDto dto:monthPackages){
						logger.info(">>>>>>>>>>>>>进入包月限量iccid:"+iccid+";goodsId:"+dto.getGoodsId());
						LaoUserOperatorPlanDto nextPlan = apiMsgLogService.getNextPlan(iccid);
						if(nextPlan==null){
							logger.info(">>>>>>>>>>>>>>>>>限量没有下个要发的包 退出当前循环");
							continue;
						}
						
						logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<apiMsg   iccid:"+iccid+";logId:"+logId);
						nextPlan.setOpeartorsDealRst("3");
						apiMsgLogService.updateUserOperatorPlan(nextPlan);
						logger.info(">>>>>包月限量修改套餐状态");
						//订购
						logger.info(">>>>>>>>>>>>>>>>>>>>>即将包月限量订购流程");
						boolean flag=apiMsgLogService.order(nextPlan, laoUserDto, iccid,false);
						//修改套餐状态
						ApiMsgLogDto logDto = apiMsgLogService.selectByPrimaryKey(Long.valueOf(logId));
						logDto.setUpdateTime(new Date());
						if(flag){
							logger.info(">>>>>>>>>>>>>包月不限量订购成功");
							nextPlan.setOpeartorsDealRst("0");
							nextPlan.setStartUseDate(new Date());
							LaoUserOperatorPlanDto beforePlan=new LaoUserOperatorPlanDto();
							beforePlan.setTradeId(nextPlan.getTradeId());
							beforePlan.setGoodsIndex(nextPlan.getGoodsIndex()-1);
							beforePlan.setGoodsId(nextPlan.getGoodsId());
							beforePlan.setUserId(nextPlan.getUserId());
							LaoUserOperatorPlanDto beforePlan2 = apiMsgLogService.getBeforePlan(beforePlan);
							
							beforePlan2.setEndDate(nextPlan.getStartUseDate());
							apiMsgLogService.updateUserOperatorPlan(beforePlan2);
							logDto.setDealTag("1");
							logDto.setDealResult("1");
							isReturn=true;
						}else{
							nextPlan.setOpeartorsDealRst("1");
							logger.info(">>>>>>>>>>>>>>>>>>>>包月不限量订购失败");
							logDto.setDealTag("0");
							logDto.setDealResult("0");
						}
						apiMsgLogService.updateUserOperatorPlan(nextPlan);
						//修改日志信息
						apiMsgLogService.updateByPrimaryKeySelective(logDto);
						logger.info(">>>>>>>>>>>>限量修改套餐日志信息");
						if(isReturn){
							logger.info("<<<<<<<<<<<<<<<<限量订购成功退出订购流程");
							return;
						}
						
						
					}
					//不限量
					for(GoodsDto dto:buttoms){
						logger.info(">>>>>>>>>>>>>进入二次不限量iccid:"+iccid+";goodsId:"+dto.getGoodsId());
						LaoUserOperatorPlanDto nextPlan = apiMsgLogService.getNextPlan(iccid);
						if(nextPlan==null){
							logger.info(">>>>>>>>>>>>>>>>>不限量没有下个要发的包 退出当前循环");
							continue;
						}
						boolean buttomless = apiMsgLogService.isButtomless(nextPlan);
						if(buttomless){
							lastPlan=nextPlan;
							continue;
						}else{
							logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<apiMsg   iccid:"+iccid+";logId:"+logId);
							nextPlan.setOpeartorsDealRst("3");
							apiMsgLogService.updateUserOperatorPlan(nextPlan);
							logger.info(">>>>>包月不限量修改套餐状态");
							//订购
							logger.info(">>>>>>>>>>>>>>>>>>>>>即将包月不限量订购流程");
							boolean flag=apiMsgLogService.order(nextPlan, laoUserDto, iccid,true);
							//修改套餐状态
							ApiMsgLogDto logDto = apiMsgLogService.selectByPrimaryKey(Long.valueOf(logId));
							logDto.setUpdateTime(new Date());
							if(flag){
								logger.info(">>>>>>>>>>>>>包月不限量订购成功");
								nextPlan.setOpeartorsDealRst("0");
								nextPlan.setStartUseDate(new Date());
								LaoUserOperatorPlanDto beforePlan=new LaoUserOperatorPlanDto();
								beforePlan.setTradeId(nextPlan.getTradeId());
								beforePlan.setGoodsIndex(nextPlan.getGoodsIndex()-1);
								beforePlan.setGoodsId(nextPlan.getGoodsId());
								beforePlan.setUserId(nextPlan.getUserId());
								LaoUserOperatorPlanDto beforePlan2 = apiMsgLogService.getBeforePlan(beforePlan);
								
								beforePlan2.setEndDate(nextPlan.getStartUseDate());
								apiMsgLogService.updateUserOperatorPlan(beforePlan2);
								logDto.setDealTag("1");
								logDto.setDealResult("1");
								isReturn=true;
							}else{
								nextPlan.setOpeartorsDealRst("1");
								logger.info(">>>>>>>>>>>>>>>>>>>>包月不限量订购失败");
								logDto.setDealTag("0");
								logDto.setDealResult("0");
							}
							apiMsgLogService.updateUserOperatorPlan(nextPlan);
							//修改日志信息
							apiMsgLogService.updateByPrimaryKeySelective(logDto);
							logger.info(">>>>>>>>>>>>二次计费修改套餐日志信息");
							if(isReturn){
								logger.info("<<<<<<<<<<<<<<<<不限量订购成功退出订购流程");
								return;
							}
						}
						
					}
					//二次计费
					for(GoodsDto dto:charg){
						List<LaoUserOperatorPlanDto> nextPlans = apiMsgLogService.getNextPlans(iccid);	
						logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<apiMsg   iccid:"+iccid+";logId:"+logId);
						if(nextPlans.size()<2){
							logger.info(">>>>>>>>>>>>>>>>>>>>二次计费查询的下次要发的包错误iccid:"+iccid);
							continue;
						}
						//修改套餐的状态
						logger.info(">>>>>二次计费修改套餐状态");
						for(LaoUserOperatorPlanDto nextPlan:nextPlans){
							nextPlan.setOpeartorsDealRst("3");
							apiMsgLogService.updateUserOperatorPlan(nextPlan);
						}
						
						//订购
						logger.info(">>>>>>>>>>>>>>>>>>>>>即将进入二次计费订购流程");
						ApiMsgLogDto logDto = apiMsgLogService.selectByPrimaryKey(Long.valueOf(logId));
						boolean flag=apiMsgLogService.order(nextPlans, laoUserDto, iccid);
						//修改套餐状态
						if(flag){
							logger.info(">>>>>>>>>>>>>>>>>二次计费订购成功");
							LaoUserOperatorPlanDto beforePlan=new LaoUserOperatorPlanDto();
							beforePlan.setTradeId(nextPlans.get(0).getTradeId());
							beforePlan.setGoodsIndex(nextPlans.get(0).getGoodsIndex()-2);
							beforePlan.setGoodsId(nextPlans.get(0).getGoodsId());
							beforePlan.setUserId(nextPlans.get(0).getUserId());
							LaoUserOperatorPlanDto beforePlan2 = apiMsgLogService.getBeforePlan(beforePlan);
							
							beforePlan2.setEndDate(new Date());
							logDto.setDealTag("1");
							logDto.setDealResult("1");
							isReturn=true;
							apiMsgLogService.updateUserOperatorPlan(beforePlan2);
						}else{
							logger.info(">>>>>>>>>>>>>>>二次计费订购失败");
							logDto.setDealTag("0");
							logDto.setDealResult("0");
						}
						//修改日志信息
						logger.info(">>>>>>>>>>>>>>>>>>>二次计费修改套餐日志信息");
						logDto.setUpdateTime(new Date());
						apiMsgLogService.updateByPrimaryKeySelective(logDto);
						if(isReturn){
							logger.info(">>>>>>>>>>>>>>>>>>>二次计费订购成功 退出订购流程");
							return ;
						}
						
					}
					if(lastPlan!=null){
						//触发限速包
						logger.info("<<<<<<<<<<<<<<<<<<<<<<<不限量限速包且二次计费无包可发触发限速包   iccid:"+iccid);
						lastPlan.setOpeartorsDealRst("3");
						apiMsgLogService.updateUserOperatorPlan(lastPlan);
						logger.info(">>>>>包月不限量修改套餐状态");
						//订购
						logger.info(">>>>>>>>>>>>>>>>>>>>>即将包月不限量订购流程");
						boolean flag=apiMsgLogService.order(lastPlan, laoUserDto, iccid,true);
						//修改套餐状态
						ApiMsgLogDto logDto = apiMsgLogService.selectByPrimaryKey(Long.valueOf(logId));
						logDto.setUpdateTime(new Date());
						if(flag){
							logger.info(">>>>>>>>>>>>>包月不限量订购成功");
							lastPlan.setOpeartorsDealRst("0");
							lastPlan.setStartUseDate(new Date());
							LaoUserOperatorPlanDto beforePlan=new LaoUserOperatorPlanDto();
							beforePlan.setTradeId(lastPlan.getTradeId());
							beforePlan.setGoodsIndex(lastPlan.getGoodsIndex()-1);
							beforePlan.setGoodsId(lastPlan.getGoodsId());
							beforePlan.setUserId(lastPlan.getUserId());
							LaoUserOperatorPlanDto beforePlan2 = apiMsgLogService.getBeforePlan(beforePlan);
							
							beforePlan2.setEndDate(lastPlan.getStartUseDate());
							apiMsgLogService.updateUserOperatorPlan(beforePlan2);
							logDto.setDealTag("1");
							logDto.setDealResult("1");
							isReturn=true;
						}else{
							lastPlan.setOpeartorsDealRst("1");
							logger.info(">>>>>>>>>>>>>>>>>>>>包月不限量订购失败");
							logDto.setDealTag("0");
							logDto.setDealResult("0");
						}
						apiMsgLogService.updateUserOperatorPlan(lastPlan);
						//修改日志信息
						apiMsgLogService.updateByPrimaryKeySelective(logDto);
						logger.info(">>>>>>>>>>>>二次计费修改套餐日志信息");
						if(isReturn){
							logger.info("<<<<<<<<<<<<<<<<不限量订购成功退出订购流程");
							return;
						}
					}
					
				}			
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.info("apiMsg 异常信息："+e.getMessage());
				e.printStackTrace();
			}finally{
				collector.ack(input);
			}
		}
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("message"));
	}  
	

    





}
