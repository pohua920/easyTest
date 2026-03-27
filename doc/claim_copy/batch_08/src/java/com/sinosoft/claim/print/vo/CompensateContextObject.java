package com.sinosoft.claim.print.vo;

import java.io.Serializable;

/***
 * 理算說明內容
 * @author 中科软
 */
public class CompensateContextObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String context = "";

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

}
