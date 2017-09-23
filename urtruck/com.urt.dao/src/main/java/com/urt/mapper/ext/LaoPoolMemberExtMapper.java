package com.urt.mapper.ext;

import java.util.List;

import com.urt.common.util.Page;
import com.urt.po.LaoPoolMember;

public interface LaoPoolMemberExtMapper {

	List<LaoPoolMember> queryMenberInfoMethod(Page<LaoPoolMember> page);

}
