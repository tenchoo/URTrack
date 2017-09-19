package com.urt.mapper;

import com.urt.po.LaoEsimUserBunding;

public interface LaoEsimUserBundingMapper {
    int insert(LaoEsimUserBunding record);

    int insertSelective(LaoEsimUserBunding record);
    /**
     * 根据userId 更新绑定信息
     * @param laoEsimUserBunding
     * @return
     */
	int updateUserBunding(LaoEsimUserBunding laoEsimUserBunding);

	LaoEsimUserBunding findUserBunding(Long userId);
}