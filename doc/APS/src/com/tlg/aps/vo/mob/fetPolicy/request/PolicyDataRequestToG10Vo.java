package com.tlg.aps.vo.mob.fetPolicy.request;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;

/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程
 *  </br>回傳保單號給數開Vo
 * */
@JsonSerialize
public class PolicyDataRequestToG10Vo {

	@SerializedName("CUSTOMER")
	private List<PolicyDataRequestToG10CustomerVo> customer;

	public List<PolicyDataRequestToG10CustomerVo> getCustomer() {
		return customer;
	}

	public void setCustomer(List<PolicyDataRequestToG10CustomerVo> customer) {
		this.customer = customer;
	}
	
}
