package com.sinosoft.claim.schema.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/***
 * 理賠進度
 * @author 理賠組
 */
@Entity
@Table(name = "PRPLCLAIMPROGRESS")
public class PrpLclaimProgress implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	/** 進度類別 */
	private String progressType;
	/** 進度序號 */
	private int progressSerialNo;
	/** 進度描述 */
	private String progressDesc;
	/***
	 * 任務識別碼 : 01立案;02查勘;03定損;04核損;05人傷定損;06人傷核損;07財產定損;08財產核損;09理算
	 */
	private String taskCode;
	/** 每個節點任務名稱描述 lossitemname + nodetype */
	private String taskObject;
	/** 損失項序號 區分定損、核損損失項 其他默認0 */
	private int lossItemCode = 0;
	/** 處理日期 */
	private Date processDate;
	/** 系統錄入日期 */
	private Date inputDate;
	/** 備案號碼 */
	private String registNo;
	/** 保單號碼 */
	private String policyNo;
	/** 業務號碼 立案存立案號，理算存理算號碼 */
	private String businessNo ;
	/** 處理人代碼 */
	private String handlerCode ;
	/** 處理人名稱 */
	private String handlerName ;
	/** 任務節點的處理狀態 */
	private String nodeStatus;

	@Id
	@Column(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "PROGRESSTYPE")
	public String getProgressType() {
		return progressType;
	}

	public void setProgressType(String progressType) {
		this.progressType = progressType;
	}

	@Column(name = "PROGRESSSERIALNO")
	public int getProgressSerialNo() {
		return progressSerialNo;
	}

	public void setProgressSerialNo(int progressSerialNo) {
		this.progressSerialNo = progressSerialNo;
	}

	@Column(name = "PROGRESSDESC")
	public String getProgressDesc() {
		return progressDesc;
	}

	public void setProgressDesc(String progressDesc) {
		this.progressDesc = progressDesc;
	}

	@Column(name = "TASKCODE")
	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	@Column(name = "TASKOBJECT")
	public String getTaskObject() {
		return taskObject;
	}

	public void setTaskObject(String taskObject) {
		this.taskObject = taskObject;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PROCESSDATE")
	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	@Column(name = "REGISTNO")
	public String getRegistNo() {
		return registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return handlerCode;
	}

	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	@Column(name = "HANDLERNAME")
	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	@Column(name = "NODESTATUS")
	public String getNodeStatus() {
		return nodeStatus;
	}

	public void setNodeStatus(String nodeStatus) {
		this.nodeStatus = nodeStatus;
	}
	
	@Column(name = "lossItemCode")
	public int getLossItemCode() {
		return lossItemCode;
	}

	public void setLossItemCode(int lossItemCode) {
		this.lossItemCode = lossItemCode;
	}
	
	@Column(name = "businessNo")
	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	/***
	 * 判斷進度訊息是否有做修改
	 * @param p
	 * @return
	 */
	public boolean contentEquals(PrpLclaimProgress p) {
		return isSameClaimProgress(p) && taskObject.equals(p.getTaskObject()) && progressDesc.equals(p.getProgressDesc()) && processDate.getTime() == p.getProcessDate().getTime() && nodeStatus == p.getNodeStatus();
	}

	/***
	 * 判斷是否同一任務的同一進度訊息
	 * @param p
	 * @return
	 */
	public boolean isSameClaimProgress(PrpLclaimProgress p) {
		return progressType.equals(p.getProgressType()) && progressSerialNo == p.getProgressSerialNo() && taskCode.equals(p.getTaskCode()) && registNo.equals(p.getRegistNo()) && this.businessNo.equals(p.getBusinessNo());
	}
}
