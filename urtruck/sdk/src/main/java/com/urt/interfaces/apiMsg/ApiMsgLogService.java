package com.urt.interfaces.apiMsg;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.urt.dto.ApiMsgLogDto;
import com.urt.dto.LaoUserOperatorPlanDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.Trade.TradeDto;

public interface ApiMsgLogService {
	int deleteByPrimaryKey(Long msgId);

    int insert(ApiMsgLogDto record);

    int insertSelective(ApiMsgLogDto record);

    ApiMsgLogDto selectByPrimaryKey(Long msgId);

    int updateByPrimaryKeySelective(ApiMsgLogDto record);

    int updateByPrimaryKey(ApiMsgLogDto record);
    
    void sendApiMsgLog(Long id,String iccid);
    
    void sendButtomlessInfo(List<Map<String, Object>> maps);
    
    List<LaoUserOperatorPlanDto> getNextPlans(String iccid);
    
    LaoUserOperatorPlanDto getNextPlan(String iccid);
    
    LaoUserOperatorPlanDto getBeforePlan(LaoUserOperatorPlanDto userOperatorPlanDto);
    
    int updateUserOperatorPlan(LaoUserOperatorPlanDto dto);
    
    boolean order(LaoUserOperatorPlanDto userOperatorPlan,LaoUserDto laoUser,String iccid,Boolean flag);
    
    boolean order(List<LaoUserOperatorPlanDto> userOperatorPlan,LaoUserDto laoUser,String iccid);
    
    boolean order(Long goodsId, String iccid, Long userId,Integer releaseId,Date date);
    
    List<Map<String, Object>> getIccidAndGoodsId();
    
    List<Map<String, Object>> getIccidAndGoodsIdByDay();
    
    boolean isButtomless(LaoUserOperatorPlanDto userOperatorPlan);
    
    
}
