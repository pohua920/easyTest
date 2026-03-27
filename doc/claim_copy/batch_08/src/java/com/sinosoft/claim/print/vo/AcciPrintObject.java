package com.sinosoft.claim.print.vo;

import java.util.ArrayList;
import java.util.List;

/***
 * 伤害险报表list打印类
 * @author 中科软
 */
public class AcciPrintObject {
	/** 匯款同意書报表list */
	private List<AcciPrintSubObject> remittanceList;
	/** 賠款同意書暨領款收據list */
	private List<AcciPrintSubObject> receiptList;
	/** 補件通知函list */
	private List<AcciPrintSubObject> notificationList;
	/** 保險金給付通知書list */
	private List<AcciPrintSubObject> paymentNoticeList;
	/** 计算书書結案險list */
	private List<AcciPrintSubObject> compensateInfoList;
	/** 计算书書賠付對象list */
	private List<AcciPrintSubObject> sumFeeInfoList;
	/** 賠付內容 */
	private List<CompensateContextObject> contextList = new ArrayList<CompensateContextObject>();

	public List<AcciPrintSubObject> getRemittanceList() {
		return remittanceList;
	}

	public void setRemittanceList(List<AcciPrintSubObject> remittanceList) {
		this.remittanceList = remittanceList;
	}

	public List<AcciPrintSubObject> getReceiptList() {
		return receiptList;
	}

	public void setReceiptList(List<AcciPrintSubObject> receiptList) {
		this.receiptList = receiptList;
	}

	public List<AcciPrintSubObject> getNotificationList() {
		return notificationList;
	}

	public void setNotificationList(List<AcciPrintSubObject> notificationList) {
		this.notificationList = notificationList;
	}

	public List<AcciPrintSubObject> getPaymentNoticeList() {
		return paymentNoticeList;
	}

	public void setPaymentNoticeList(List<AcciPrintSubObject> paymentNoticeList) {
		this.paymentNoticeList = paymentNoticeList;
	}

	public List<AcciPrintSubObject> getCompensateInfoList() {
		return compensateInfoList;
	}

	public void setCompensateInfoList(List<AcciPrintSubObject> compensateInfoList) {
		this.compensateInfoList = compensateInfoList;
	}

	public List<AcciPrintSubObject> getSumFeeInfoList() {
		return sumFeeInfoList;
	}

	public void setSumFeeInfoList(List<AcciPrintSubObject> sumFeeInfoList) {
		this.sumFeeInfoList = sumFeeInfoList;
	}

	public List<CompensateContextObject> getContextList() {
		return contextList;
	}

	public void setContextList(List<CompensateContextObject> contextList) {
		this.contextList = contextList;
	}
}
