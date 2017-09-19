package com.urt.mapper.ext;

import java.util.List;

import com.urt.common.util.Page;
import com.urt.po.LaoOperatorsbillResult;

public interface LaoOperatorsbillResultExtMapper {

    List<LaoOperatorsbillResult> getOperatorsbillResultList(Page<LaoOperatorsbillResult> page);

}