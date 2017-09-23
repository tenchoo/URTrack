package com.urt.interfaces.ShangHaiCMC;
/**
 * 实名登记/变更
 *
 */
public interface SI_RegisterEndUserInfo {
	/**
	 * 
	 * @param MSISDN
	 * @param User_Name
	 * @param Id_Type
	 * @param Id_Number
	 * @param Id_Address
	 * @param Id_Expirdate
	 * @param VIC
	 * @param Oper_Type
	 * @param VehicleUserMdn
	 * @param IdFrontalView
	 * @param IdBackView
	 * @param HandedIdPhoto
	 * @return
	 */
	public String registerEndUserInfo(String msisdn,String user_name,
			String id_type,String id_number,String id_address,String id_expirdate
			,String vic,String oper_type,String vehicleUserMdn,
			String idFrontalView,String idBackView,String handedIdPhoto);
}
