package com.urt.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.urt.po.DeviceProductPo;

public interface DeviceProductPoExtMapper {
	/**
	 * 查询所有需要展示的广告信息
	 * @return
	 */
	List<DeviceProductPo> selectShowAds(@Param("tag")String tag);
}