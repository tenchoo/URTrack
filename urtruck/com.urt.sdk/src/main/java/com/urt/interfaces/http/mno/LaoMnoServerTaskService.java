package com.urt.interfaces.http.mno;

import java.util.List;
import java.util.Map;

import com.urt.dto.http.mno.InterfaceHandlerContext;
import com.urt.dto.http.mno.LaoMnoServerTaskDto;

/**
 * mno 操作结果查询接口
 *
 * @author Tian Peng4
 * @date 2017/8/21
 */
public interface LaoMnoServerTaskService {

    int insert(LaoMnoServerTaskDto serverTaskDto);

    int updateStatus(String status, String code, String desc);

    List<LaoMnoServerTaskDto> queryTaskList(Map<String, String> param);

    LaoMnoServerTaskDto queryTask(Map<String, String> param);

    InterfaceHandlerContext operationResultQuery(InterfaceHandlerContext handlerContext);

    void operationResultPush(Map<String, String> params);
}
