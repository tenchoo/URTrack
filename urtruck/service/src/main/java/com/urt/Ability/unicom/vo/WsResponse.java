package com.urt.Ability.unicom.vo;

import net.sf.json.JSONObject;

public class WsResponse {

	@Override
	public String toString() {
		JSONObject json = JSONObject.fromObject(this);
		return json.toString();
	}
}
