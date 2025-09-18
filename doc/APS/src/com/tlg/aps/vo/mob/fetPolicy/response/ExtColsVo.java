package com.tlg.aps.vo.mob.fetPolicy.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExtColsVo {
	
	@SerializedName("Ext_Col")
	private List<ExtColVo> extCol;

	public List<ExtColVo> getExtCol() {
		return extCol;
	}

	public void setExtCol(List<ExtColVo> extCol) {
		this.extCol = extCol;
	}

	
}
