package com.urt.service.User;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.urt.common.enumeration.CardState;
import com.urt.common.enumeration.IndustryType;
import com.urt.common.enumeration.OperatorType;
import com.urt.common.enumeration.SalesType;
import com.urt.interfaces.User.UserTotalService;
import com.urt.mapper.ext.LaoUserTotalExtMapper;

@Service("userTotalService")
@Transactional(propagation = Propagation.REQUIRED)
public class UserTotalServiceImpl implements UserTotalService {

	Logger log = Logger.getLogger(getClass());

	@Autowired
	private LaoUserTotalExtMapper laoUserTotalExt;

	// 大区，行业，数目
	// key-value 明细
	@Override
	public Map<String, Long> queryStateDetail() {
		List<Map<String, Object>> stateNum = laoUserTotalExt
				.queryUserStateNum();
		log.info("UserTotalServiceImpl.queryDetail  stateNum.szie:"
				+ stateNum.size());
		Map<String, Long> mapInNum = new HashMap<String, Long>();
		for (Map<String, Object> map : stateNum) {
			// 北区，智能展业，待激活 NZW
			if (map.get("REGION_NAME").equals(SalesType.N.getCode())) {
				if (map.get("STATIC_NAME").equals(IndustryType.Z.getCode())) {// 展业
					if (map.get("outsidestate").equals(CardState.W.getCode())) {
						mapInNum.put("NZW",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("outsidestate").equals(
							CardState.T.getCode())) {
						mapInNum.put("NZT",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("NZZ",
								Long.valueOf(map.get("NUMB").toString()));
					}
				} else if (map.get("STATIC_NAME").equals(
						IndustryType.C.getCode())) {// 车联

					if (map.get("outsidestate").equals(CardState.W.getCode())) {
						mapInNum.put("NCW",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("outsidestate").equals(
							CardState.T.getCode())) {
						mapInNum.put("NCT",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("NCZ",
								Long.valueOf(map.get("NUMB").toString()));
					}

				} else if (map.get("STATIC_NAME").equals(
						IndustryType.H.getCode())) {
					if (map.get("outsidestate").equals(CardState.W.getCode())) {
						mapInNum.put("NHW",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("outsidestate").equals(
							CardState.T.getCode())) {
						mapInNum.put("NHT",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("NHZ",
								Long.valueOf(map.get("NUMB").toString()));
					}
				} else {
					if (map.get("outsidestate").equals(CardState.W.getCode())) {
						mapInNum.put("NYW",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("outsidestate").equals(
							CardState.T.getCode())) {
						mapInNum.put("NYT",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("NYZ",
								Long.valueOf(map.get("NUMB").toString()));
					}
				}
			} else if (map.get("REGION_NAME").equals(SalesType.E.getCode())) {

				if (map.get("STATIC_NAME").equals(IndustryType.Z.getCode())) {
					if (map.get("outsidestate").equals(CardState.W.getCode())) {
						mapInNum.put("EZW",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("outsidestate").equals(
							CardState.T.getCode())) {
						mapInNum.put("EZT",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("EZZ",
								Long.valueOf(map.get("NUMB").toString()));
					}
				} else if (map.get("STATIC_NAME").equals(
						IndustryType.C.getCode())) {

					if (map.get("outsidestate").equals(CardState.W.getCode())) {
						mapInNum.put("ECW",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("outsidestate").equals(
							CardState.T.getCode())) {
						mapInNum.put("ECT",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("ECZ",
								Long.valueOf(map.get("NUMB").toString()));
					}

				} else if (map.get("STATIC_NAME").equals(
						IndustryType.H.getCode())) {
					if (map.get("outsidestate").equals(CardState.W.getCode())) {
						mapInNum.put("EHW",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("outsidestate").equals(
							CardState.T.getCode())) {
						mapInNum.put("EHT",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("EHZ",
								Long.valueOf(map.get("NUMB").toString()));
					}
				} else {
					if (map.get("outsidestate").equals(CardState.W.getCode())) {
						mapInNum.put("EYW",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("outsidestate").equals(
							CardState.T.getCode())) {
						mapInNum.put("EYT",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("EYZ",
								Long.valueOf(map.get("NUMB").toString()));
					}
				}

			} else {

				if (map.get("STATIC_NAME").equals(IndustryType.Z.getCode())) {
					if (map.get("outsidestate").equals(CardState.W.getCode())) {
						mapInNum.put("SZW",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("outsidestate").equals(
							CardState.T.getCode())) {
						mapInNum.put("SZT",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("SZZ",
								Long.valueOf(map.get("NUMB").toString()));
					}
				} else if (map.get("STATIC_NAME").equals(
						IndustryType.C.getCode())) {

					if (map.get("outsidestate").equals(CardState.W.getCode())) {
						mapInNum.put("SCW",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("outsidestate").equals(
							CardState.T.getCode())) {
						mapInNum.put("SCT",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("SCZ",
								Long.valueOf(map.get("NUMB").toString()));
					}

				} else if (map.get("STATIC_NAME").equals(
						IndustryType.H.getCode())) {
					if (map.get("outsidestate").equals(CardState.W.getCode())) {
						mapInNum.put("SHW",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("outsidestate").equals(
							CardState.T.getCode())) {
						mapInNum.put("SHT",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("SHZ",
								Long.valueOf(map.get("NUMB").toString()));
					}
				} else {
					if (map.get("outsidestate").equals(CardState.W.getCode())) {
						mapInNum.put("SYW",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("outsidestate").equals(
							CardState.T.getCode())) {
						mapInNum.put("SYT",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("SYZ",
								Long.valueOf(map.get("NUMB").toString()));
					}
				}

			}
		}
		return mapInNum;
	}

	// key-value 明细
	@Override
	public Map<String, Long> queryOperDetail() {
		List<Map<String, Object>> operNum = laoUserTotalExt.queryUserOperNum();
		log.info("UserTotalServiceImpl.queryDetail *********************operNum.size:"
				+ operNum.size());
		Map<String, Long> mapInNum = new HashMap<String, Long>();
		// 根据销售大区，行业类别，运营商 获取数目明细
		for (Map<String, Object> map : operNum) {
			log.info("UserTotalServiceImpl.queryDetail operNum.size:"
					+ map.get("NUMB"));
			// 北区，智能展业，待激活 NZW
			if (map.get("REGION_NAME").equals(SalesType.N.getCode())) {
				if (map.get("STATIC_NAME").equals(IndustryType.Z.getCode())) {// 展业
					if (map.get("OPERATORS_ID")
							.equals(OperatorType.L.getCode())) {
						mapInNum.put("NZL",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("OPERATORS_ID").equals(
							OperatorType.Y.getCode())) {
						mapInNum.put("NZY",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("NZD",
								Long.valueOf(map.get("NUMB").toString()));
					}
				} else if (map.get("STATIC_NAME").equals(
						IndustryType.C.getCode())) {// 车联

					if (map.get("OPERATORS_ID")
							.equals(OperatorType.L.getCode())) {
						mapInNum.put("NCL",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("OPERATORS_ID").equals(
							OperatorType.Y.getCode())) {
						mapInNum.put("NCY",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("NCD",
								Long.valueOf(map.get("NUMB").toString()));
					}

				} else if (map.get("STATIC_NAME").equals(
						IndustryType.H.getCode())) {
					if (map.get("OPERATORS_ID")
							.equals(OperatorType.L.getCode())) {
						mapInNum.put("NHL",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("OPERATORS_ID").equals(
							OperatorType.Y.getCode())) {
						mapInNum.put("NHY",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("NHD",
								Long.valueOf(map.get("NUMB").toString()));
					}
				} else {
					if (map.get("OPERATORS_ID")
							.equals(OperatorType.L.getCode())) {
						mapInNum.put("NYL",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("OPERATORS_ID").equals(
							OperatorType.Y.getCode())) {
						mapInNum.put("NYY",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("NYD",
								Long.valueOf(map.get("NUMB").toString()));
					}
				}
			} else if (map.get("REGION_NAME").equals(SalesType.E.getCode())) {

				if (map.get("STATIC_NAME").equals(IndustryType.Z.getCode())) {
					if (map.get("OPERATORS_ID")
							.equals(OperatorType.L.getCode())) {
						mapInNum.put("EZL",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("OPERATORS_ID").equals(
							OperatorType.Y.getCode())) {
						mapInNum.put("EZY",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("EZD",
								Long.valueOf(map.get("NUMB").toString()));
					}
				} else if (map.get("STATIC_NAME").equals(
						IndustryType.C.getCode())) {

					if (map.get("OPERATORS_ID")
							.equals(OperatorType.L.getCode())) {
						mapInNum.put("ECL",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("OPERATORS_ID").equals(
							OperatorType.Y.getCode())) {
						mapInNum.put("ECY",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("ECD",
								Long.valueOf(map.get("NUMB").toString()));
					}

				} else if (map.get("STATIC_NAME").equals(
						IndustryType.H.getCode())) {
					if (map.get("OPERATORS_ID")
							.equals(OperatorType.L.getCode())) {
						mapInNum.put("EHL",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("OPERATORS_ID").equals(
							OperatorType.Y.getCode())) {
						mapInNum.put("EHY",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("EHD",
								Long.valueOf(map.get("NUMB").toString()));
					}
				} else {
					if (map.get("OPERATORS_ID")
							.equals(OperatorType.L.getCode())) {
						mapInNum.put("EYL",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("OPERATORS_ID").equals(
							OperatorType.Y.getCode())) {
						mapInNum.put("EYY",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("EYD",
								Long.valueOf(map.get("NUMB").toString()));
					}
				}

			} else {

				if (map.get("STATIC_NAME").equals(IndustryType.Z.getCode())) {
					if (map.get("OPERATORS_ID")
							.equals(OperatorType.L.getCode())) {
						mapInNum.put("SZL",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("OPERATORS_ID").equals(
							OperatorType.Y.getCode())) {
						mapInNum.put("SZY",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("SZD",
								Long.valueOf(map.get("NUMB").toString()));
					}
				} else if (map.get("STATIC_NAME").equals(
						IndustryType.C.getCode())) {

					if (map.get("OPERATORS_ID")
							.equals(OperatorType.L.getCode())) {
						mapInNum.put("SCL",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("OPERATORS_ID").equals(
							OperatorType.Y.getCode())) {
						mapInNum.put("SCY",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("SCD",
								Long.valueOf(map.get("NUMB").toString()));
					}

				} else if (map.get("STATIC_NAME").equals(
						IndustryType.H.getCode())) {
					if (map.get("OPERATORS_ID")
							.equals(OperatorType.L.getCode())) {
						mapInNum.put("SHL",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("OPERATORS_ID").equals(
							OperatorType.Y.getCode())) {
						mapInNum.put("SHY",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("SHD",
								Long.valueOf(map.get("NUMB").toString()));
					}
				} else {
					if (map.get("OPERATORS_ID")
							.equals(OperatorType.L.getCode())) {
						mapInNum.put("SYL",
								Long.valueOf(map.get("NUMB").toString()));
					} else if (map.get("OPERATORS_ID").equals(
							OperatorType.Y.getCode())) {
						mapInNum.put("SYY",
								Long.valueOf(map.get("NUMB").toString()));
					} else {
						mapInNum.put("SYD",
								Long.valueOf(map.get("NUMB").toString()));
					}
				}

			}
		}
		return mapInNum;
	}

	@Override
	public Map<String, Long> queryIndustryNum() {
		List<Map<String, Object>> listIndNum = laoUserTotalExt
				.queryIndustryNum();
		Map<String, Long> mapInNum = new HashMap<String, Long>();
		for (Map<String, Object> map : listIndNum) {
			mapInNum.put(
					map.get("REGION_NAME").toString()
							.concat(map.get("STATIC_NAME").toString()),
					Long.valueOf(map.get("NUMB").toString()));

			if (map.get("REGION_NAME").equals(SalesType.N.getCode())) {
				if (map.get("STATIC_NAME").equals(IndustryType.Z.getCode())) {// 展业
					mapInNum.put("NZ", Long.valueOf(map.get("NUMB").toString()));
				} else if (map.get("STATIC_NAME").equals(
						IndustryType.C.getCode())) {// 车联
					mapInNum.put("NC", Long.valueOf(map.get("NUMB").toString()));
				} else if (map.get("STATIC_NAME").equals(
						IndustryType.H.getCode())) {
					mapInNum.put("NH", Long.valueOf(map.get("NUMB").toString()));
				} else {
					mapInNum.put("NY", Long.valueOf(map.get("NUMB").toString()));
				}
			} else if (map.get("REGION_NAME").equals(SalesType.E.getCode())) {

				if (map.get("STATIC_NAME").equals(IndustryType.Z.getCode())) {
					mapInNum.put("EZ", Long.valueOf(map.get("NUMB").toString()));
				} else if (map.get("STATIC_NAME").equals(
						IndustryType.C.getCode())) {
					mapInNum.put("ECD",
							Long.valueOf(map.get("NUMB").toString()));
				} else if (map.get("STATIC_NAME").equals(
						IndustryType.H.getCode())) {
					mapInNum.put("EH", Long.valueOf(map.get("NUMB").toString()));
				} else {
					mapInNum.put("EY", Long.valueOf(map.get("NUMB").toString()));
				}

			} else {
				if (map.get("STATIC_NAME").equals(IndustryType.Z.getCode())) {
					mapInNum.put("SZ", Long.valueOf(map.get("NUMB").toString()));
				} else if (map.get("STATIC_NAME").equals(
						IndustryType.C.getCode())) {
					mapInNum.put("SC", Long.valueOf(map.get("NUMB").toString()));
				} else if (map.get("STATIC_NAME").equals(
						IndustryType.H.getCode())) {
					mapInNum.put("SHD",
							Long.valueOf(map.get("NUMB").toString()));
				} else {
					mapInNum.put("SYD",
							Long.valueOf(map.get("NUMB").toString()));
				}
			}
		}
		return mapInNum;
	}

	@Override
	public JSONObject getNumber(String startDate, String endDate) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = laoUserTotalExt.queryNumByAdmin(
				startDate, endDate);
		JSONObject json = new JSONObject();
		// ed
		JSONArray edArray = new JSONArray(5);
		JSONObject edznzyObject = new JSONObject();
		edznzyObject.put("y", 0);
		edznzyObject.put("l", 0);
		edznzyObject.put("d", 0);
		edznzyObject.put("z", 0);
		edznzyObject.put("w", 0);
		edznzyObject.put("t", 0);
		edznzyObject.put("fn", 0);
		edArray.add(edznzyObject);

		JSONObject edznclObject = new JSONObject();
		edznclObject.put("y", 0);
		edznclObject.put("l", 0);
		edznclObject.put("d", 0);
		edznclObject.put("z", 0);
		edznclObject.put("w", 0);
		edznclObject.put("t", 0);
		edznclObject.put("fn", 0);
		edArray.add(edznclObject);

		JSONObject edznhlObject = new JSONObject();
		edznhlObject.put("y", 0);
		edznhlObject.put("l", 0);
		edznhlObject.put("d", 0);
		edznhlObject.put("z", 0);
		edznhlObject.put("w", 0);
		edznhlObject.put("t", 0);
		edznhlObject.put("fn", 0);
		edArray.add(edznhlObject);

		JSONObject edyyswlObject = new JSONObject();
		edyyswlObject.put("y", 0);
		edyyswlObject.put("l", 0);
		edyyswlObject.put("d", 0);
		edyyswlObject.put("z", 0);
		edyyswlObject.put("w", 0);
		edyyswlObject.put("t", 0);
		edyyswlObject.put("fn", 0);
		edArray.add(edyyswlObject);

		JSONObject edznObject = new JSONObject();
		edznObject.put("y", 0);
		edznObject.put("l", 0);
		edznObject.put("d", 0);
		edznObject.put("z", 0);
		edznObject.put("w", 0);
		edznObject.put("t", 0);
		edznObject.put("fn", 0);
		edArray.add(edznObject);
		// sd
		JSONArray sdArray = new JSONArray(5);
		JSONObject sdznzyObject = new JSONObject();
		sdznzyObject.put("y", 0);
		sdznzyObject.put("l", 0);
		sdznzyObject.put("d", 0);
		sdznzyObject.put("z", 0);
		sdznzyObject.put("w", 0);
		sdznzyObject.put("t", 0);
		sdznzyObject.put("fn", 0);
		sdArray.add(sdznzyObject);

		JSONObject sdznclObject = new JSONObject();
		sdznclObject.put("y", 0);
		sdznclObject.put("l", 0);
		sdznclObject.put("d", 0);
		sdznclObject.put("z", 0);
		sdznclObject.put("w", 0);
		sdznclObject.put("t", 0);
		sdznclObject.put("fn", 0);
		sdArray.add(sdznclObject);

		JSONObject sdznhlObject = new JSONObject();
		sdznhlObject.put("y", 0);
		sdznhlObject.put("l", 0);
		sdznhlObject.put("d", 0);
		sdznhlObject.put("z", 0);
		sdznhlObject.put("w", 0);
		sdznhlObject.put("t", 0);
		sdznhlObject.put("fn", 0);
		sdArray.add(sdznhlObject);

		JSONObject sdyyswlObject = new JSONObject();
		sdyyswlObject.put("y", 0);
		sdyyswlObject.put("l", 0);
		sdyyswlObject.put("d", 0);
		sdyyswlObject.put("z", 0);
		sdyyswlObject.put("w", 0);
		sdyyswlObject.put("t", 0);
		sdyyswlObject.put("fn", 0);
		sdArray.add(sdyyswlObject);

		JSONObject sdznObject = new JSONObject();
		sdznObject.put("y", 0);
		sdznObject.put("l", 0);
		sdznObject.put("d", 0);
		sdznObject.put("z", 0);
		sdznObject.put("w", 0);
		sdznObject.put("t", 0);
		sdznObject.put("fn", 0);
		sdArray.add(sdznObject);

		// nd
		JSONArray ndArray = new JSONArray(5);
		JSONObject ndznzyObject = new JSONObject();
		ndznzyObject.put("y", 0);
		ndznzyObject.put("l", 0);
		ndznzyObject.put("d", 0);
		ndznzyObject.put("z", 0);
		ndznzyObject.put("w", 0);
		ndznzyObject.put("t", 0);
		ndznzyObject.put("fn", 0);
		ndArray.add(ndznzyObject);

		JSONObject ndznclObject = new JSONObject();
		ndznclObject.put("y", 0);
		ndznclObject.put("l", 0);
		ndznclObject.put("d", 0);
		ndznclObject.put("z", 0);
		ndznclObject.put("w", 0);
		ndznclObject.put("t", 0);
		ndznclObject.put("fn", 0);
		ndArray.add(ndznclObject);

		JSONObject ndznhlObject = new JSONObject();
		ndznhlObject.put("y", 0);
		ndznhlObject.put("l", 0);
		ndznhlObject.put("d", 0);
		ndznhlObject.put("z", 0);
		ndznhlObject.put("w", 0);
		ndznhlObject.put("t", 0);
		ndznhlObject.put("fn", 0);
		ndArray.add(ndznhlObject);

		JSONObject ndyyswlObject = new JSONObject();
		ndyyswlObject.put("y", 0);
		ndyyswlObject.put("l", 0);
		ndyyswlObject.put("d", 0);
		ndyyswlObject.put("z", 0);
		ndyyswlObject.put("w", 0);
		ndyyswlObject.put("t", 0);
		ndyyswlObject.put("fn", 0);
		ndArray.add(ndyyswlObject);

		JSONObject ndznObject = new JSONObject();
		ndznObject.put("y", 0);
		ndznObject.put("l", 0);
		ndznObject.put("d", 0);
		ndznObject.put("z", 0);
		ndznObject.put("w", 0);
		ndznObject.put("t", 0);
		ndznObject.put("fn", 0);
		ndArray.add(ndznObject);
		json.put("eastArea", edArray);
		json.put("southArea", sdArray);
		json.put("northArea", ndArray);
		for (Map<String, Object> map : list) {
			if (map.get("GID") != null && map.get("STATIC_NAME") != null) {
				if ("ED".equals(map.get("GID").toString())) {
					if ("智能展业".equals(map.get("STATIC_NAME").toString())) {
						setNumber(0, edznzyObject, edArray, map);
					} else if ("智能车联".equals(map.get("STATIC_NAME").toString())) {
						setNumber(1, edznclObject, edArray, map);
					} else if ("智能互联".equals(map.get("STATIC_NAME").toString())) {
						setNumber(2, edznhlObject, edArray, map);
					} else if ("运营商物联"
							.equals(map.get("STATIC_NAME").toString())) {
						setNumber(3, edyyswlObject, edArray, map);
					} else if ("智能B".equals(map.get("STATIC_NAME").toString())) {
						setNumber(4, edznObject, edArray, map);
					}
					json.put("eastArea", edArray);
				} else if ("SD".equals(map.get("GID").toString())) {
					if ("智能展业".equals(map.get("STATIC_NAME").toString())) {
						setNumber(0, sdznzyObject, sdArray, map);
					} else if ("智能车联".equals(map.get("STATIC_NAME").toString())) {
						setNumber(1, sdznclObject, sdArray, map);
					} else if ("智能互联".equals(map.get("STATIC_NAME").toString())) {
						setNumber(2, sdznhlObject, sdArray, map);
					} else if ("运营商物联"
							.equals(map.get("STATIC_NAME").toString())) {
						setNumber(3, sdyyswlObject, sdArray, map);
					} else if ("智能B".equals(map.get("STATIC_NAME").toString())) {
						setNumber(4, sdznObject, sdArray, map);
					}
					json.put("southArea", sdArray);
				} else if ("ND".equals(map.get("GID").toString())) {

					if ("智能展业".equals(map.get("STATIC_NAME").toString())) {
						setNumber(0, ndznzyObject, ndArray, map);
					} else if ("智能车联".equals(map.get("STATIC_NAME").toString())) {
						setNumber(1, ndznclObject, ndArray, map);
					} else if ("智能互联".equals(map.get("STATIC_NAME").toString())) {
						setNumber(2, ndznhlObject, ndArray, map);
					} else if ("运营商物联"
							.equals(map.get("STATIC_NAME").toString())) {
						setNumber(3, ndyyswlObject, ndArray, map);
					} else if ("智能B".equals(map.get("STATIC_NAME").toString())) {
						setNumber(4, ndznObject, ndArray, map);
					}
					json.put("northArea", ndArray);
				}
			}

		}

		return json;
	}

	private static void setNumber(Integer index, JSONObject jsonObject,
			JSONArray array, Map<String, Object> map) {
		if (jsonObject == null) {
			jsonObject = new JSONObject();
		}
		jsonObject.put("fn",
				jsonObject.getBigDecimal("fn")
						.add((BigDecimal) map.get("FLOW")));
		if ("1".equals(map.get("OPERATORSID").toString())) {
			jsonObject.put(
					"l",
					jsonObject.getBigDecimal("l").add(
							(BigDecimal) map.get("NUM")));
		} else if ("2".equals(map.get("OPERATORSID").toString())) {
			jsonObject.put(
					"y",
					jsonObject.getBigDecimal("y").add(
							(BigDecimal) map.get("NUM")));
		} else if ("3".equals(map.get("OPERATORSID").toString())) {
			jsonObject.put(
					"d",
					jsonObject.getBigDecimal("d").add(
							(BigDecimal) map.get("NUM")));
		}
		if ("1".equals(map.get("STATEID").toString())) {
			jsonObject.put(
					"z",
					jsonObject.getBigDecimal("z").add(
							(BigDecimal) map.get("NUM")));
		} else if ("2".equals(map.get("STATEID").toString())) {
			jsonObject.put(
					"t",
					jsonObject.getBigDecimal("t").add(
							(BigDecimal) map.get("NUM")));
		} else if ("3".equals(map.get("STATEID").toString())) {
			jsonObject.put(
					"w",
					jsonObject.getBigDecimal("w").add(
							(BigDecimal) map.get("NUM")));
		}
		array.set(index, jsonObject);
	}

	private static JSONObject setNumber(JSONObject jsonObject,
			Map<String, Object> map) {
		if (jsonObject == null) {
			jsonObject = new JSONObject();
		}
		if (map.get("CLASSIFY") != null) {
			if ("apn1".equals(map.get("CLASSIFY").toString())
					|| "apn2".equals(map.get("CLASSIFY").toString())) {
				jsonObject.put(
						"fn",
						jsonObject.getBigDecimal("fn").add(
								(BigDecimal) map.get("FLOW")));
			} else if (map.get("CLASSIFY") != null
					&& "2".equals(map.get("CLASSIFY").toString())) {
				jsonObject.put(
						"voice",
						jsonObject.getBigDecimal("voice").add(
								(BigDecimal) map.get("MAX")));
			} else if (map.get("CLASSIFY") != null
					&& "3".equals(map.get("CLASSIFY").toString())) {
				jsonObject.put(
						"sms",
						jsonObject.getBigDecimal("sms").add(
								(BigDecimal) map.get("MAX")));
			}
		}
		if ("1".equals(map.get("OPERATORSID").toString())) {
			jsonObject.put(
					"l",
					jsonObject.getBigDecimal("l").add(
							(BigDecimal) map.get("NUM")));
		} else if ("2".equals(map.get("OPERATORSID").toString())) {
			jsonObject.put(
					"y",
					jsonObject.getBigDecimal("y").add(
							(BigDecimal) map.get("NUM")));
		} else if ("3".equals(map.get("OPERATORSID").toString())) {
			jsonObject.put(
					"d",
					jsonObject.getBigDecimal("d").add(
							(BigDecimal) map.get("NUM")));
		}
		if ("1".equals(map.get("STATEID").toString())) {
			jsonObject.put(
					"z",
					jsonObject.getBigDecimal("z").add(
							(BigDecimal) map.get("NUM")));
		} else if ("2".equals(map.get("STATEID").toString())) {
			jsonObject.put(
					"t",
					jsonObject.getBigDecimal("t").add(
							(BigDecimal) map.get("NUM")));
		} else if ("3".equals(map.get("STATEID").toString())) {
			jsonObject.put(
					"w",
					jsonObject.getBigDecimal("w").add(
							(BigDecimal) map.get("NUM")));
		}
		return jsonObject;
	}

	@Override
	public long getCount() {
		// TODO Auto-generated method stub
		return laoUserTotalExt.getCount();
	}

	@Override
	public JSONObject queryDetailByNio(String startDate, String endDate,
			Long custId) {
		// TODO Auto-generated method stub
		Map<String, Object> parapms = new HashMap<String, Object>();
		parapms.put("startDate", startDate);
		parapms.put("endDate", endDate);
		parapms.put("custId", custId);
		List<Map<String, Object>> list = laoUserTotalExt
				.queryDetailByNio(parapms);
		JSONObject json = new JSONObject();
		// 测试期
		JSONObject testSessionObject = new JSONObject();
		testSessionObject.put("y", 0);
		testSessionObject.put("l", 0);
		testSessionObject.put("d", 0);
		testSessionObject.put("z", 0);
		testSessionObject.put("w", 0);
		testSessionObject.put("t", 0);
		testSessionObject.put("fn", 0);
		testSessionObject.put("voice", 0);
		testSessionObject.put("sms", 0);
		// 正式期
		JSONObject formalizationPhaseObject = new JSONObject();
		formalizationPhaseObject.put("y", 0);
		formalizationPhaseObject.put("l", 0);
		formalizationPhaseObject.put("d", 0);
		formalizationPhaseObject.put("z", 0);
		formalizationPhaseObject.put("w", 0);
		formalizationPhaseObject.put("t", 0);
		formalizationPhaseObject.put("fn", 0);
		formalizationPhaseObject.put("voice", 0);
		formalizationPhaseObject.put("sms", 0);
		// 休眠期
		JSONObject dormancyStageObject = new JSONObject();
		dormancyStageObject.put("y", 0);
		dormancyStageObject.put("l", 0);
		dormancyStageObject.put("d", 0);
		dormancyStageObject.put("z", 0);
		dormancyStageObject.put("w", 0);
		dormancyStageObject.put("t", 0);
		dormancyStageObject.put("fn", 0);
		dormancyStageObject.put("voice", 0);
		dormancyStageObject.put("sms", 0);
		// 销号
		JSONObject cancellationPeriodObject = new JSONObject();
		cancellationPeriodObject.put("y", 0);
		cancellationPeriodObject.put("l", 0);
		cancellationPeriodObject.put("d", 0);
		cancellationPeriodObject.put("z", 0);
		cancellationPeriodObject.put("w", 0);
		cancellationPeriodObject.put("t", 0);
		json.put("testSession", testSessionObject);
		json.put("formalizationPhase", formalizationPhaseObject);
		json.put("dormancyStage", dormancyStageObject);
		json.put("cancellationPeriod", cancellationPeriodObject);
		for (Map<String, Object> map : list) {
			if ("7".equals(map.get("LIFECYCLE").toString())) {
				json.put("testSession", setNumber(testSessionObject, map));
			} else if ("0".equals(map.get("LIFECYCLE").toString())) {
				json.put("formalizationPhase",
						setNumber(formalizationPhaseObject, map));
			} else if ("9".equals(map.get("LIFECYCLE").toString())) {
				json.put("dormancyStage", setNumber(dormancyStageObject, map));
			} else if ("1".equals(map.get("LIFECYCLE").toString())
					|| "2".equals(map.get("LIFECYCLE").toString())
					|| "3".equals(map.get("LIFECYCLE").toString())
					|| "4".equals(map.get("LIFECYCLE").toString())
					|| "5".equals(map.get("LIFECYCLE").toString())
					|| "6".equals(map.get("LIFECYCLE").toString())) {
				json.put("cancellationPeriod",
						setNumber(cancellationPeriodObject, map));
			}
		}
		return json;
	}

	@SuppressWarnings("deprecation")
	@Override
	public JSONObject initIndexData() {
		// TODO Auto-generated method stub
		Date currentTime = new Date();
		int year = currentTime.getYear();
		int beforeMonth = currentTime.getMonth();
		int currentMonth = beforeMonth + 1;
		String current="";
		String before="";
		String currentMM="";
		String beforeMM="";
		if(currentMonth>9){
			current = year + "-" + currentMonth; 
			currentMM = year +""+ currentMonth; 
		}else{
			current = year + "-0" + currentMonth; 
			currentMM = year +"0"+ currentMonth; 
		}
		if(beforeMonth>9){
			before = year + "-" + beforeMonth; 
			beforeMM = year +""+ beforeMonth; 
		}else{
			before = year + "-0" + beforeMonth; 
			beforeMM = year +"0"+ beforeMonth; 
		}
		JSONObject result = new JSONObject();
		result.put("distribution", 0);
		result.put("araePie", 0);
		result.put("link", 0);
		result.put("usedFlow", 0);
		result.put("apn1UsedFlow", 0);
		result.put("apn2UsedFlow", 0);
		result.put("monthlyTrend", 0);
		result.put("apiCount", 0);
		result.put("amountSum", 0);
		result.put("amountCount", 0);
		result.put("smsUsedCount", 0);
		result.put("voiceUsedCount", 0);
		result.put("alarmList", 0);
		// 写入总链接数
		JSONObject link = new JSONObject();
		link = initJsonObject(link);
		link.put("total", getCount());
		List<Map<String, Object>> queryCountByLifeCycle = laoUserTotalExt
				.queryCountByLifeCycle();
		for (Map<String, Object> map : queryCountByLifeCycle) {
			initLifeCycle(link, map);
		}
		List<Map<String, Object>> queryCountByValue = laoUserTotalExt
				.queryCountByValue();
		for (Map<String, Object> map : queryCountByValue) {
			initType(link, map);
		}
		Map<String, Object> linkMap = new HashMap<String, Object>();
		linkMap.put("current", current);
		linkMap.put("before", before);
		List<Map<String, Object>> queryLinkMom = laoUserTotalExt
				.queryLinkMom(linkMap);
		initMom(link, queryLinkMom);
		result.put("link", link);
		// 链接数模块结束
		// MM模块
		List<Map<String, Object>> total = laoUserTotalExt.getTotalByMM();
		JSONObject usedFlow = new JSONObject();
		usedFlow = initJsonObject(usedFlow);
		JSONObject apn1UsedFlow = new JSONObject();
		apn1UsedFlow = initJsonObject(apn1UsedFlow);
		JSONObject apn2UsedFlow = new JSONObject();
		apn2UsedFlow = initJsonObject(apn2UsedFlow);
		JSONObject smsUsedCount = new JSONObject();
		smsUsedCount = initJsonObject(smsUsedCount);
		JSONObject voiceUsedCount = new JSONObject();
		voiceUsedCount = initJsonObject(voiceUsedCount);
		BigDecimal flowSum = new BigDecimal(0);
		for (Map<String, Object> map : total) {
			if (map.get("PLANCLASSIFY") != null) {
				String planClassify = map.get("PLANCLASSIFY").toString();
				if ("apn1".equals(planClassify)) {
					apn1UsedFlow.put("total", map.get("SUMSTR"));
					flowSum.add(new BigDecimal(map.get("SUMSTR").toString()));
				} else if ("apn2".equals(planClassify)) {
					apn2UsedFlow.put("total", map.get("SUMSTR"));
					flowSum.add(new BigDecimal(map.get("SUMSTR").toString()));
				} else if ("2".equals(planClassify)) {
					// 语音
					voiceUsedCount.put("total", map.get("SUMSTR"));
				} else if ("3".equals(planClassify)) {
					// 短信
					smsUsedCount.put("total", map.get("SUMSTR"));
				}
			}
		}
		usedFlow.put("total", flowSum);
		BigDecimal flowTypeSum = new BigDecimal(0);
		List<Map<String, Object>> lifeCycleSumByMM = laoUserTotalExt
				.getLifeCycleSumByMM();
		for (Map<String, Object> map : lifeCycleSumByMM) {
			if (map.get("PLANCLASSIFY") != null) {
				String planClassify = map.get("PLANCLASSIFY").toString();
				if ("apn1".equals(planClassify)) {
					apn1UsedFlow = initLifeCycle(apn1UsedFlow, map);
				} else if ("apn2".equals(planClassify)) {
					apn2UsedFlow = initLifeCycle(apn2UsedFlow, map);
				} else if ("2".equals(planClassify)) {
					// 语音
					voiceUsedCount = initLifeCycle(voiceUsedCount, map);
				} else if ("3".equals(planClassify)) {
					// 短信
					smsUsedCount = initLifeCycle(smsUsedCount, map);
				}
			}
		}
		usedFlow.put("testSession", apn1UsedFlow.getBigDecimal("testSession")
				.add(apn2UsedFlow.getBigDecimal("testSession")));
		usedFlow.put(
				"formalizationPhase",
				apn1UsedFlow.getBigDecimal("formalizationPhase").add(
						apn2UsedFlow.getBigDecimal("formalizationPhase")));
		usedFlow.put(
				"dormancyStage",
				apn1UsedFlow.getBigDecimal("dormancyStage").add(
						apn2UsedFlow.getBigDecimal("dormancyStage")));
		usedFlow.put(
				"cancellationPeriod",
				apn1UsedFlow.getBigDecimal("cancellationPeriod").add(
						apn2UsedFlow.getBigDecimal("cancellationPeriod")));
		List<Map<String, Object>> typeSumByMM = laoUserTotalExt
				.getTypeSumByMM();
		for (Map<String, Object> map : typeSumByMM) {
			if (map.get("PLANCLASSIFY") != null) {
				String planClassify = map.get("PLANCLASSIFY").toString();
				if ("apn1".equals(planClassify)) {
					apn1UsedFlow = initType(apn1UsedFlow, map);
				} else if ("apn2".equals(planClassify)) {
					apn2UsedFlow = initType(apn2UsedFlow, map);
				} else if ("2".equals(planClassify)) {
					// 语音
					voiceUsedCount = initType(voiceUsedCount, map);
				} else if ("3".equals(planClassify)) {
					// 短信
					smsUsedCount = initType(smsUsedCount, map);
				}
			}
		}
		usedFlow.put(
				"EP9",
				apn1UsedFlow.getBigDecimal("EP9").add(
						apn2UsedFlow.getBigDecimal("EP9")));
		usedFlow.put(
				"EP8",
				apn1UsedFlow.getBigDecimal("EP8").add(
						apn2UsedFlow.getBigDecimal("EP8")));
		usedFlow.put(
				"chargingPoint",
				apn1UsedFlow.getBigDecimal("chargingPoint").add(
						apn2UsedFlow.getBigDecimal("chargingPoint")));
		Map<String, String> mmParams=new HashMap<String, String>();
		mmParams.put("before", beforeMM);
		mmParams.put("current", currentMM);
		List<Map<String, Object>> momByMM = laoUserTotalExt.getMomByMM(mmParams);
		BigDecimal currentApn1Count = new BigDecimal(0);
		BigDecimal beforeApn1Count = new BigDecimal(0);
		BigDecimal currentApn2Count = new BigDecimal(0);
		BigDecimal beforeApn2Count = new BigDecimal(0);
		BigDecimal currentVoiceCount = new BigDecimal(0);
		BigDecimal beforeVoiceCount = new BigDecimal(0);
		BigDecimal currentSmsCount = new BigDecimal(0);
		BigDecimal beforeSmsCount = new BigDecimal(0);
		for (Map<String, Object> map : momByMM) {
			if (map.get("PLANCLASSIFY") != null && map.get("MONTHSTR") != null
					&& map.get("SUMSTR") != null) {
				String planClassify = map.get("PLANCLASSIFY").toString();
				String str = map.get("MONTHSTR").toString();
				String sum = map.get("SUMSTR").toString();
				if ("apn1".equals(planClassify)) {
					if ("current".equals(str)) {
						currentApn1Count = new BigDecimal(sum);
					} else if ("before".equals(str)) {
						beforeApn1Count = new BigDecimal(sum);
					}
				} else if ("apn2".equals(planClassify)) {
					if ("current".equals(str)) {
						currentApn2Count = new BigDecimal(sum);
					} else if ("before".equals(str)) {
						beforeApn2Count = new BigDecimal(sum);
					}
				} else if ("2".equals(planClassify)) {
					// 语音
					if ("current".equals(str)) {
						currentVoiceCount = new BigDecimal(sum);
					} else if ("before".equals(str)) {
						beforeVoiceCount = new BigDecimal(sum);
					}
				} else if ("3".equals(planClassify)) {
					// 短信
					if ("current".equals(str)) {
						currentSmsCount = new BigDecimal(sum);
					} else if ("before".equals(str)) {
						beforeSmsCount = new BigDecimal(sum);
					}
				}
			}
		}
		usedFlow.put("insertCount", currentApn1Count.add(currentApn2Count)
				.subtract(beforeApn1Count).subtract(beforeApn2Count));
		apn1UsedFlow.put("insertCount",
				currentApn1Count.subtract(beforeApn1Count));
		apn2UsedFlow.put("insertCount",
				currentApn2Count.subtract(beforeApn2Count));
		voiceUsedCount.put("insertCount",
				currentVoiceCount.subtract(beforeVoiceCount));
		smsUsedCount.put("insertCount",
				currentSmsCount.subtract(beforeSmsCount));
		if (beforeApn1Count.compareTo(BigDecimal.ZERO) == 1) {
			BigDecimal mom = currentApn1Count.subtract(beforeApn1Count)
					.divide(beforeApn1Count)
					.setScale(2, BigDecimal.ROUND_HALF_UP)
					.multiply(new BigDecimal(100));
			apn1UsedFlow.put("mom", mom.toString() + "%");
		}
		if (beforeApn2Count.compareTo(BigDecimal.ZERO) == 1) {
			BigDecimal mom = currentApn2Count.subtract(beforeApn2Count)
					.divide(beforeApn2Count)
					.setScale(2, BigDecimal.ROUND_HALF_UP)
					.multiply(new BigDecimal(100));
			apn2UsedFlow.put("mom", mom.toString() + "%");
		}
		if (beforeVoiceCount.compareTo(BigDecimal.ZERO) == 1) {
			BigDecimal mom = currentVoiceCount.subtract(beforeVoiceCount)
					.divide(beforeVoiceCount)
					.setScale(2, BigDecimal.ROUND_HALF_UP)
					.multiply(new BigDecimal(100));
			voiceUsedCount.put("mom", mom.toString() + "%");
		}
		if (beforeApn1Count.compareTo(BigDecimal.ZERO) == 1) {
			BigDecimal mom = currentSmsCount.subtract(beforeSmsCount)
					.divide(beforeSmsCount)
					.setScale(2, BigDecimal.ROUND_HALF_UP)
					.multiply(new BigDecimal(100));
			smsUsedCount.put("mom", mom.toString() + "%");
		}
		BigDecimal beforeApn = beforeApn1Count.add(beforeApn2Count);
		if (beforeApn.compareTo(BigDecimal.ZERO) == 1) {
			BigDecimal mom = currentApn1Count.add(currentApn2Count)
					.subtract(beforeApn).divide(beforeApn)
					.setScale(2, BigDecimal.ROUND_HALF_UP)
					.multiply(new BigDecimal(100));
			usedFlow.put("mom", mom);
		}
		result.put("usedFlow", usedFlow);
		result.put("apn1UsedFlow", apn1UsedFlow);
		result.put("apn2UsedFlow", apn2UsedFlow);
		result.put("voiceUsedCount", voiceUsedCount);
		result.put("smsUsedCount", smsUsedCount);
		// mm表结束
		// 统计充值数据
		Map<String, Object> rechargeTotal = laoUserTotalExt.getRechargeTotal();
		JSONObject amountCount = new JSONObject();
		JSONObject amountSum = new JSONObject();
		amountSum = initJsonObject(amountSum);
		amountCount = initJsonObject(amountCount);
		amountCount.put("total", rechargeTotal.get("COUNTSTR"));
		amountSum.put("total", rechargeTotal.get("SUMSTR"));
		List<Map<String, Object>> rechargeByValue = laoUserTotalExt
				.getRechargeByValue();
		for (Map<String, Object> map : rechargeByValue) {
			if (map.get("STATICNAME") != null) {
				if ("EP9".equals(map.get("STATICNAME").toString())) {
					amountCount.put("EP9", map.get("COUNTSTR"));
					amountSum.put("EP9", map.get("SUMSTR"));
				} else if ("EP8".equals(map.get("STATICNAME").toString())) {
					amountCount.put("EP8", map.get("COUNTSTR"));
					amountSum.put("EP9", map.get("SUMSTR"));
				} else if ("充电桩".equals(map.get("STATICNAME").toString())) {
					amountCount.put("chargingPoint", map.get("COUNTSTR"));
					amountSum.put("EP9", map.get("SUMSTR"));
				}
			}
		}
		Map<String, String> param=new HashMap<String, String>();
		param.put("current", current);
		param.put("before", before);
		List<Map<String, Object>> rechargeByMom = laoUserTotalExt
				.getRechargeByMom(param);
		BigDecimal currentCount = new BigDecimal(0);
		BigDecimal beforeCount = new BigDecimal(0);
		BigDecimal currentSum = new BigDecimal(0);
		BigDecimal beforeSum = new BigDecimal(0);
		for (Map<String, Object> map : rechargeByMom) {
			if (map.get("MONTHSTR") != null) {
				String str = map.get("MONTHSTR").toString();
				if ("current".equals(str)) {
					if (map.get("COUNTSTR") != null) {
						currentCount = new BigDecimal(map.get("COUNTSTR")
								.toString());
					}
					if (map.get("SUMSTR") != null) {
						currentSum = new BigDecimal(map.get("SUMSTR")
								.toString());
					}

				} else if ("before".equals(str)) {
					if (map.get("COUNTSTR") != null) {
						beforeCount = new BigDecimal(map.get("COUNTSTR")
								.toString());
					}
					if (map.get("SUMSTR") != null) {
						beforeSum = new BigDecimal(map.get("SUMSTR").toString());
					}
				}
			}
		}
		amountCount.put("insertCount", currentCount.subtract(beforeCount));
		amountSum.put("insertCount", currentSum.subtract(beforeSum));
		if (beforeCount.compareTo(BigDecimal.ZERO) == 1) {
			BigDecimal mom = currentCount.subtract(beforeCount)
					.divide(beforeCount).setScale(2, BigDecimal.ROUND_HALF_UP)
					.multiply(new BigDecimal(100));
			amountCount.put("mom", mom.toString() + "%");
		}
		if (beforeSum.compareTo(BigDecimal.ZERO) == 1) {
			BigDecimal mom = currentSum.subtract(beforeSum).divide(beforeSum)
					.setScale(2, BigDecimal.ROUND_HALF_UP)
					.multiply(new BigDecimal(100));
			amountSum.put("mom", mom.toString() + "%");
		}
		result.put("amountCount", amountCount);
		result.put("amountSum", amountSum);
		JSONObject apiCount = new JSONObject();
		apiCount.put("total", laoUserTotalExt.getApiCount());
		List<Map<String, Object>> apiByValue = laoUserTotalExt.getApiByValue();
		for (Map<String, Object> map : apiByValue) {
			initType(apiCount, map);
		}
		List<Map<String, Object>> apiMom = laoUserTotalExt.getApiMom(param);
		BigDecimal currentApi = new BigDecimal(0);
		BigDecimal beforeApi = new BigDecimal(0);
		for (Map<String, Object> map : apiMom) {
			if (map.get("MONTHSTR") != null) {
				String str = map.get("MONTHSTR").toString();
				if ("current".equals(str)) {
					currentApi = new BigDecimal(str);
				} else if ("before".equals(str)) {
					beforeApi = new BigDecimal(str);
				}
			}
		}
		apiCount.put("insertCount", currentApi.subtract(beforeApi));
		if (beforeApi.compareTo(BigDecimal.ZERO) == 1) {
			int i = 100;
			BigDecimal mom = currentApi.subtract(beforeApi).divide(beforeApi)
					.setScale(2, BigDecimal.ROUND_HALF_UP)
					.multiply(new BigDecimal(100));
			apiCount.put("mom", mom.toString() + "%");
		}
		result.put("apiCount", apiCount);
		// 柱状图
		JSONObject monthlyTrend = new JSONObject();
		// 链接
		
		int[] linkArray = new int[12];
		for (int i = 0; i < 12; i++) {
			if (i == 0) {
				int k = year - 1;
				long bm = laoUserTotalExt.getLinkByMonth(k + "-12");
				int j = i + 1;
				long cm = laoUserTotalExt.getLinkByMonth(year + "-0" + j);
				if (cm == 0) {
					linkArray[0] = 0;
				} else {
					long l = cm - bm;
					linkArray[0] = (int) l;
				}
			} else {
				long bm = 0l;
				long cm = 0l;
				if (i > 9) {
					bm = laoUserTotalExt.getLinkByMonth(year + "-" + i);
				} else {
					bm = laoUserTotalExt.getLinkByMonth(year + "-0" + i);
				}

				int j = i + 1;
				if (j > 9) {
					cm = laoUserTotalExt.getLinkByMonth(year + "-" + j);
				} else {
					cm = laoUserTotalExt.getLinkByMonth(year + "-0" + j);
				}
				if (cm == 0) {
					linkArray[i] = 0;
				} else {
					long l = cm - bm;
					if (l > 0) {
						linkArray[i] = (int) l;
					} else {
						linkArray[i] = 0;
					}

				}
			}
		}
		monthlyTrend.put("link", linkArray);
		// flow
		BigDecimal[] flowArray = new BigDecimal[12];
		for (int i = 0; i < 12; i++) {
			BigDecimal cm = new BigDecimal(0);
			int j=i+1;
			if (j > 9) {
				cm = laoUserTotalExt.getFlowByApn(year + "" + j);
			} else {
				cm = laoUserTotalExt.getFlowByApn(year + "0" + j);
			}
			flowArray[i]=cm;
			
		}
		monthlyTrend.put("usedFlow", flowArray);
		// apn1
		BigDecimal[] apn1Array = new BigDecimal[12];
		for (int i = 0; i < 12; i++) {
			BigDecimal cm = new BigDecimal(0);
			int j=i+1;
			if (j > 9) {
				cm = laoUserTotalExt.getFlowByApn1(year + "" + j);
			} else {
				cm = laoUserTotalExt.getFlowByApn1(year + "0" + j);
			}
			apn1Array[i]=cm;
		}
		monthlyTrend.put("apn1", apn1Array);
		// apn2
		BigDecimal[] apn2Array = new BigDecimal[12];
		for (int i = 0; i < 12; i++) {
			BigDecimal cm = new BigDecimal(0);
			int j=i+1;
			if (j > 9) {
				cm = laoUserTotalExt.getFlowByApn2(year + "" + j);
			} else {
				cm = laoUserTotalExt.getFlowByApn2(year + "0" + j);
			}
			apn2Array[i]=cm;
		}
		monthlyTrend.put("apn2", apn2Array);
		// aomut
		int[] aoumntCountArray = new int[12];
		BigDecimal[] aoumntSumArray = new BigDecimal[12];
		for (int i = 0; i < 12; i++) {
			int cc = 0;
			Map<String, Object> currentAmountByMonth = new HashMap<String, Object>();
			int j = i + 1;
			if (j <= 9) {
				currentAmountByMonth = laoUserTotalExt.getAmountByMonth(year
						+ "-0" + j);
			} else {
				currentAmountByMonth = laoUserTotalExt.getAmountByMonth(year
						+ "-" + i);
			}
			
			if (currentAmountByMonth.get("COUNTSTR") != null) {
				aoumntCountArray[i] = new Integer(currentAmountByMonth.get(
						"COUNTSTR").toString());
			} else {
				aoumntCountArray[i] = 0;
			}
			BigDecimal cs = new BigDecimal(0);
			if (currentAmountByMonth.get("SUMSTR") != null) {
				cs = new BigDecimal(currentAmountByMonth.get("SUMSTR")
						.toString());
			} else {
				aoumntSumArray[i] = cs;
			}
		}
		monthlyTrend.put("amountCount", aoumntCountArray);
		monthlyTrend.put("amountSum", aoumntSumArray);
		//api
		long[] apiArray=new long[12];
		for (int i = 0; i < 12; i++) {
			long cm = 0l;
			int j=i+1;
			if (j > 9) {
				cm = laoUserTotalExt.getApiByMonth(year + "-" + j);
			} else {
				cm = laoUserTotalExt.getApiByMonth(year + "-0" + j);
			}
			apiArray[i]=cm;
		}
		System.out.println("result:"+result);
		return result;
	}

	JSONObject initJsonObject(JSONObject json) {
		json.put("total", 0);
		json.put("testSession", 0);
		json.put("formalizationPhase", 0);
		json.put("dormancyStage", 0);
		json.put("cancellationPeriod", 0);
		json.put("EP8", 0);
		json.put("EP9", 0);
		json.put("chargingPoint", 0);
		json.put("insertCount", 0);
		json.put("linkMom", "--");
		return json;
	}

	JSONObject initLifeCycle(JSONObject json, Map<String, Object> map) {

		if (map.get("CYALESTR") != null) {
			if ("7".equals(map.get("CYALESTR").toString())) {
				json.put("testSession", map.get("COUNTSTR"));
			} else if ("0".equals(map.get("CYALESTR").toString())) {
				json.put("formalizationPhase", map.get("COUNTSTR"));
			} else if ("9".equals(map.get("CYALESTR").toString())) {
				json.put("dormancyStage", map.get("COUNTSTR"));
			} else if ("1".equals(map.get("CYALESTR").toString())
					|| "2".equals(map.get("CYALESTR").toString())
					|| "3".equals(map.get("CYALESTR").toString())
					|| "4".equals(map.get("CYALESTR").toString())
					|| "5".equals(map.get("CYALESTR").toString())
					|| "6".equals(map.get("CYALESTR").toString())) {
				json.put("cancellationPeriod", map.get("COUNTSTR"));
			}
		}
		return json;
	}

	JSONObject initType(JSONObject json, Map<String, Object> map) {
		if (map.get("TYPESTR") != null) {
			if ("EP9".equals(map.get("TYPESTR").toString())) {
				json.put("EP9", map.get("COUNTSTR"));
			} else if ("EP8".equals(map.get("TYPESTR").toString())) {
				json.put("EP8", map.get("COUNTSTR"));
			} else if ("充电桩".equals(map.get("TYPESTR").toString())) {
				json.put("chargingPoint", map.get("COUNTSTR"));
			}
		}
		return json;
	}

	JSONObject initMom(JSONObject json, List<Map<String, Object>> queryLinkMom) {
		BigDecimal currentMonthLink = new BigDecimal(0);
		BigDecimal beforeMonthLink = new BigDecimal(0);
		for (Map<String, Object> map : queryLinkMom) {
			if (map.get("MONTHSTR") != null) {
				String str = map.get("MONTHSTR").toString();
				if ("current".equals(str)) {
					currentMonthLink = new BigDecimal(str);
				} else if ("before".equals(str)) {
					beforeMonthLink = new BigDecimal(str);
				}
			}
		}
		json.put("insertCount", currentMonthLink.subtract(beforeMonthLink));
		if (beforeMonthLink.compareTo(BigDecimal.ZERO) == 1) {
			int i = 100;
			BigDecimal mom = currentMonthLink.subtract(beforeMonthLink)
					.divide(beforeMonthLink)
					.setScale(2, BigDecimal.ROUND_HALF_UP)
					.multiply(new BigDecimal(100));
			json.put("mom", mom.toString() + "%");
		}
		return json;
	}
}
