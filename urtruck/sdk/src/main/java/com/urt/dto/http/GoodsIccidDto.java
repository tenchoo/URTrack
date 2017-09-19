package com.urt.dto.http;

import java.io.Serializable;
import java.util.List;

public class GoodsIccidDto implements Serializable{
	
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
		public List<GoodsList> products;
		public String respCode;
		public String respDesc;
		
		
		public List<GoodsList> getProducts() {
			return products;
		}
		public void setProducts(List<GoodsList> products) {
			this.products = products;
		}
		public String getRespCode() {
			return respCode;
		}
		public void setRespCode(String respCode) {
			this.respCode = respCode;
		}
		public String getRespDesc() {
			return respDesc;
		}
		public void setRespDesc(String respDesc) {
			this.respDesc = respDesc;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		
}
