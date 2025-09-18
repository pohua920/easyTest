package com.tlg.aps.vo.mob.fetPolicy.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResContentVo {
	
	@SerializedName("CUSTOMER")
	private List<CustomerVo> customer;

	public List<CustomerVo> getCustomer() {
		return customer;
	}

	public void setCustomer(List<CustomerVo> customer) {
		this.customer = customer;
	}
	
}
