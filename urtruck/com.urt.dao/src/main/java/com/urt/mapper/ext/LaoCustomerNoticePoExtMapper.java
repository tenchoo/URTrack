package com.urt.mapper.ext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.urt.common.util.Page;
import com.urt.po.LaoCustomerNoticePo;


public interface LaoCustomerNoticePoExtMapper {
	//查询指定条数的通知
	List<Map<String, Object>> selectNotice(long custId);
	//查询所有条数的通知
	List<Map<String, Object>> selectNoticeAll(long custId);
	//重要通知分页展示
	List<HashMap<String, Object>> queryPage(Page<LaoCustomerNoticePo> page);
	//根据id查询重要通知内容
	Map<String, Object> selectNoticeContent(long noticeId);
	//更新点击次数
	void updateClickNum(@Param("noticeId")long noticeId);
}