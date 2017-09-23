package com.urt.mapper.ext;

import java.util.List;
import java.util.Map;

import com.urt.po.LaoEsimUserGoods;

public interface LaoEsimUserGoodsExtMapper {

	int updateByUserId(LaoEsimUserGoods esimGoods);

	List<Map<String, Object>> findOrderGoodsByUsername(String username);

}
