package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 	GROUP_NO 詢問表群組代號，必填。<br>
 	GITEM_NO 詢問表細項群組代號，必填。<br>
  	CKLIST_NO 詢問表問題代號，必填。<br>
 * 	CKLIST_RESULT 詢問表問題答案，必填。 1 = 是。2 = 否。0 = 未作答<br>
 * 
 * 
 * 
 * @author bi086
 * 
 */
@XmlRootElement
public class PbCkListVo {

	private String groupNo;
	private String gitemNo;
	private String cklistNo;
	private String cklistResult;

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getGitemNo() {
		return gitemNo;
	}

	public void setGitemNo(String gitemNo) {
		this.gitemNo = gitemNo;
	}

	public String getCklistNo() {
		return cklistNo;
	}

	public void setCklistNo(String cklistNo) {
		this.cklistNo = cklistNo;
	}

	public String getCklistResult() {
		return cklistResult;
	}

	public void setCklistResult(String cklistResult) {
		this.cklistResult = cklistResult;
	}

}
