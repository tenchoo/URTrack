package com.urt.service.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.urt.interfaces.User.UserStateMmService;
import com.urt.mapper.ext.LaoUserStateMMPoExtMapper;

@Service("userStateService")
@Transactional(propagation = Propagation.REQUIRED)
public class UserStateMmServiceImpl implements UserStateMmService{
	@Autowired
	private LaoUserStateMMPoExtMapper laoUserStateExt;
	@Override
	public JSONObject getNumber() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = laoUserStateExt.queryNumByAdmin(
				);
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
}
