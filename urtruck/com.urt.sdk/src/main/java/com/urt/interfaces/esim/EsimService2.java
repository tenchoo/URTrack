package com.urt.interfaces.esim;

import java.util.List;
import java.util.Map;

import com.urt.dto.LaoEsimGoodsDto;
import com.urt.dto.LaoEsimLogDto;

public interface EsimService2 {
    /**
     * 校验eid,imei
     * @param imei
     * @param eid
     * @return
     */
	boolean checkCard(String imei,String eid);
    /**
     * 查询发布的产品
     * @return
     */
	List<Map<String,Object>> queryGoods();
    /**
     * 根据GoodsId查询产品对象
     * @param goodsId
     * @return
     */
	LaoEsimGoodsDto findGoodByGoodsId(String goodsId);

	Map<String, Object> orderGoods(String username, LaoEsimGoodsDto dto, String eid, String orderTag,String iccid);
    /**
     * 连接 Server
     * @param username
     * @param eid
     * @param iccid
     */
	boolean connectServer(String username, String eid, String iccid);
    /**
     * 解绑 Server 
     * @param username
     * @param eid
     * @param iccid
     */
	Map<String,Object> cancelServer(String username, String eid, String iccid);
    /**
     * 转赠 Server
     * @param username
     * @param eid
     * @param iccid
     * @param username2
     * @param goodsId
     */
	Map<String,Object> changeServer(String username, String eid, String iccid, String username2, String goodsId);
    /**
     * 已订购产品查询
     * @param username
     * @return
     */
	List<Map<String, Object>> findOrderGoods(String username);
	/**
	 * 记录记录日志
	 * @param esimLogDto
	 */
	void insertEsimLog(LaoEsimLogDto esimLogDto);
	/**
	 * 检查Iccid
	 * @param iccid
	 * @return
	 */
	boolean checkIccid(String iccid);

}
