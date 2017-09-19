package com.urt.Ability.ShanDongCTC.Resp;

public class TeleResp {
	
	private String RESULT;
	private String SMSG;
	public String getRESULT() {
		return RESULT;
	}
	public void setRESULT(String rESULT) {
		RESULT = rESULT;
	}
	public String getSMSG() {
		return SMSG;
	}
	public void setSMSG(String sMSG) {
		SMSG = sMSG;
	}
	public TeleResp(String rESULT, String sMSG) {
		super();
		RESULT = rESULT;
		SMSG = sMSG;
	}
	public TeleResp() {
		super();
	}
	@Override
	public String toString() {
		return "TeleResp [RESULT=" + RESULT + ", SMSG=" + SMSG + "]";
	}
	

}
