package com.urt.Ability.collect;

import org.springframework.beans.factory.annotation.Autowired;

import com.urt.dto.traffic.LaoSimDateDetailDto;
import com.urt.mapper.LaoOperatordealLogMapper;

/**
 * 批量任务流量查询 当日使用量，当月使用量，当月剩余量
 * 
 * @author Administrator
 *
 * @param <T>
 */
public abstract class BatchTrafficQuery<T> {

	@Autowired
	private LaoOperatordealLogMapper laoOperatordealLogDAO;

	/**
	 * 日使用量查询 推送参数，记录入参出参
	 * 
	 * @param args
	 * @return
	 */
	protected abstract void queryDayUseCount(LaoSimDateDetailDto laoSimDateDetailDto);

	/**
	 * 当月使用量查询 推送参数，记录入参出参
	 * 
	 * @param args
	 * @return
	 */
	protected abstract void queryMonthUseCount(LaoSimDateDetailDto laoSimDateDetailDto);

	/**
	 * 剩余量查询 推送参数，记录入参出参
	 * 
	 * @param args
	 * @return
	 */
	protected abstract void queryMonthDataRemaining(LaoSimDateDetailDto laoSimDateDetailDto);
	/**
	 * 卡信息查询 推送参数，记录入参出参
	 * 
	 * @param args
	 * @return
	 */
	protected abstract void queryCardMsg(LaoSimDateDetailDto laoSimDateDetailDto);
	
	
	public void doQuery(LaoSimDateDetailDto laoSimDateDetailDto){
		queryDayUseCount(laoSimDateDetailDto);
		queryMonthUseCount(laoSimDateDetailDto);
		queryMonthDataRemaining(laoSimDateDetailDto);
		queryCardMsg(laoSimDateDetailDto);
	}

}
