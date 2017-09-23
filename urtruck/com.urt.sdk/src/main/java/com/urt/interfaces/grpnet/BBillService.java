package com.urt.interfaces.grpnet;

import java.util.List;

import com.urt.dto.LaoBatchDatadetailDto;
import com.urt.dto.grpnet.DObjectFeecountExtDto;
import com.urt.dto.grpnet.DObjectFeediscntExtDto;
import com.urt.dto.grpnet.GrpnetImpbillDto;

public interface BBillService {
	
	List<DObjectFeecountExtDto> selectAllFeecount();
	
	List<DObjectFeediscntExtDto> selectAllFeediscnt();
    
    int batchInsert(List<GrpnetImpbillDto> listDto);
    // 集群网 新增用户 
    int insertUser(String msisdn);
    // 更新批量明细表
    int updateDatadetail(LaoBatchDatadetailDto dto);
}
