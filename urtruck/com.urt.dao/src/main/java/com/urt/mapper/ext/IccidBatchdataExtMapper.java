package com.urt.mapper.ext;

import java.util.List;

import com.urt.common.util.Page;
import com.urt.po.IccidBatchdata;

public interface IccidBatchdataExtMapper {
 
	 List<IccidBatchdata> selectByPage(Page<IccidBatchdata> iccidBatchExtpage);
	 
	 IccidBatchdata selectByIccid(String iccid);
}