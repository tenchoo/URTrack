package com.urt.mapper.ext;

import java.util.List;

import com.urt.po.LaoEsimIccidLib;

public interface LaoEsimIccidLibExtMapper {
    /**
     * 通过goodsId 查询 空闲卡资源
     * @param goodsId
     * @return
     */
	LaoEsimIccidLib selectByGoodsId(String goodsId);
    /**
     * 更新卡状态到在用
     * @param iccid
     * @return
     */
	int updateByIccid(String iccid);
	/**
	 * 根据iccid查询
	 * @param iccid
	 * @return
	 */
	LaoEsimIccidLib selectByIccid(String iccid);

}
