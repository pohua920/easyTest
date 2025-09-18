package com.tlg.aps.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RuleReponseVo {
	/* mantis：FIR0225，處理人員：BJ085，需求單編號：FIR0225 稽核議題調整-過往資料檢核 start */
	/**
	 * 執行結果
	 */
	private String status;

	private ArrayList<RuleReponseDetailVo> detailList;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<RuleReponseDetailVo> getDetailList() {
		return detailList;
	}

	public void setDetailList(ArrayList<RuleReponseDetailVo> detailList) {
		this.detailList = detailList;
	}
	
}
