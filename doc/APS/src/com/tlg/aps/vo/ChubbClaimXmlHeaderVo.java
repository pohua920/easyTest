package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/** mantis：MOB0020，處理人員：BI086，需求單編號：MOB0020 理賠盜打詐騙API及XML作業 */

@XmlRootElement(name = "Header")
public class ChubbClaimXmlHeaderVo {

	
	private String documentTime;
	
	private Integer totalRecord;
	
	private Integer recordDate;
	
	private String projectID;
	
	private Integer documentID;
	
	private Integer documentDate;

	@XmlElement(name="DocumentTime")
	public String getDocumentTime() {
		return documentTime;
	}

	public void setDocumentTime(String documentTime) {
		this.documentTime = documentTime;
	}
	
	@XmlElement(name="RecordDate")
	public Integer getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Integer recordDate) {
		this.recordDate = recordDate;
	}

	@XmlElement(name="ProjectID")
	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	@XmlElement(name="DocumentID")
	public Integer getDocumentID() {
		return documentID;
	}

	public void setDocumentID(Integer documentID) {
		this.documentID = documentID;
	}

	@XmlElement(name="DocumentDate")
	public Integer getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Integer documentDate) {
		this.documentDate = documentDate;
	}

	@XmlElement(name="TOTAL_RECORD")
	public Integer getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
	}

}
