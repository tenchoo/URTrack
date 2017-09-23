package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import com.urt.common.util.Page;
import com.urt.po.LaoBPaylog;
import com.urt.po.LaoBPaylogPo;

public interface LaoBPaylogExtMapper {
    int selectPayStatus(Map<String,Object> paramMap);//payTag  0:企业未交;1:用户已缴;2:企业已缴
    
    List<LaoBPaylog> selectByCustId(Map<String, Object> paraMap);

	int countByCustId(Map<String, Object> paraMap);
	
	/**
	* 功能描述:营收 分页  （这里如果需要查询recv_time 在固定时间断的记录， 需要将 page对象中的LaoBPaylog setRecvTime 为开始时间， setFeecntTime 为结束时间）
	* @author sunhao
	* @date 2017年4月6日 上午10:36:48
	* @param @return
	* @return List<Map<String,String>>
	* @throws
	 */
	List<Map> revenueByPage(Page<LaoBPaylog> page);
	
	/**
	* 功能描述:营收  （这里如果需要查询recv_time 在固定时间断的记录， 需要将 LaoBPaylog setRecvTime 为开始时间， setFeecntTime 为结束时间）
	* @author sunhao
	* @date 2017年4月6日 上午10:36:48
	* @param @return
	* @return List<Map<String,String>>
	* @throws
	 */
	List<LaoBPaylogPo> revenue(LaoBPaylog log);
    /**
     * 企业当月的充值数
     * @param custId
     * @return
     */
	Long countMoneyByCustId(Long custId);
}