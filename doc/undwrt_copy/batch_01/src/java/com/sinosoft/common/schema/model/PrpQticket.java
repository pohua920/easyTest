// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
package com.sinosoft.common.schema.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 */
@Entity
@Table(name="PRPQTICKET"
)
public class PrpQticket  implements java.io.Serializable {
    private static final long serialVersionUID = 1L;


     /** 属性报价单号*/
     private String proposalNo;
     /** 属性序号*/
     private Integer seriesNo;
     /** 属性险类代码 */
     private String classCode;
     /** 属性险种代码 */
     private String riskCode;
     /** 属性訂購日期 */
     private Date orderDate;
     /** 属性機票號碼 */
     private String ticketNo;
     /** 属性PNR號碼 */
     private String pnrNo;
     /** 属性被保險人姓名 */
     private String name;
     /** 属性出生年月日 */
     private Date birthday;
     /** 属性身份证字号 */
     private String identifyNumber;
     /** 属性出發日期 */
     private Date departureDate;
     /** 属性航段類型 */
     private String legType;
     /** 属性航段組合 */
     private String segmentCombination ;
     /** 属性航班組合 */
     private String flightCombination;
     /** 属性飛安險成本/收款金額 */
     private String premium;
     /** 属性交易序號 */
     private String transactionNo;
     /** 属性交易明細序號 */
     private String transactionDetailNo;
     /** 属性行程 */
     private String trip;
     /** 属性保險金額 */
     private String amount;
     /** 属性銷貨收入 */
     private String salesRevenue;
     /** 属性給付金額 */
     private String netAmount;
     /** 属性付款方式 */
     private String payType;
     /** 属性航空公司*/
     private String airlineCompany;
     /** 属性應收金額 */
     private String netReceivable;
     /** 属性退票日期 */
     private Date refundDate;
     /** 属性预留字段1*/   
     private String tcol1;
     /** 属性预留字段2*/ 
     private String tcol2;
	 /** 屬性flag*/
     private String flag;
     /**
      * 属性prpQmain
      */
     private PrpQmain prpQmain;
     
     /**
 	 * 类PrpQticket的默认构造方法
 	 */
     public PrpQticket(){
    	 
     }
     
     /**
      * 属性prpQmain的getter方法
      */
     @ManyToOne(fetch = FetchType.LAZY)
 	@JoinColumn(name = "PROPOSALNO", nullable = false, insertable = false, updatable = false)
	public PrpQmain getPrpQmain() {
		return prpQmain;
	}
	/**
	 * 属性prpQmain的setter方法
	 */
	public void setPrpQmain(PrpQmain prpQmain) {
		this.prpQmain = prpQmain;
	}
	/**属性proposalNo的getter方法*/
	@Id
	@Column(name="PROPOSALNO")
	public String getProposalNo() {
		return proposalNo;
	}
	/**属性proposalNo的setter方法*/
	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}
	/**属性seriesNo的getter方法*/
	@Column(name="SERIESNO")
	public Integer getSeriesNo() {
		return seriesNo;
	}
	/**属性seriesNo的setter方法*/
	public void setSeriesNo(Integer seriesNo) {
		this.seriesNo = seriesNo;
	}
	
	/**属性classCode的getter方法*/
	@Column(name="CLASSCODE")
	public String getClassCode() {
		return classCode;
	}

	/**属性classCode的setter方法*/
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	/**属性riskCode的getter方法*/
	@Column(name="RISKCODE")
	public String getRiskCode() {
		return riskCode;
	}
	/**属性riskCode的setter方法*/
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	/**属性orderDate的getter方法*/
	@Column(name="ORDERDATE")
	public Date getOrderDate() {
		return orderDate;
	}
	/**属性orderDate的setter方法*/
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	/**属性ticketNo的getter方法*/
	@Column(name="TICKETNO")
	public String getTicketNo() {
		return ticketNo;
	}
	/**属性ticketNo的setter方法*/
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	/**属性pnrNo的getter方法*/
	@Column(name="PNRNO")
	public String getPnrNo() {
		return pnrNo;
	}
	/**属性pnrNo的setter方法*/
	public void setPnrNo(String pnrNo) {
		this.pnrNo = pnrNo;
	}
	/**属性name的getter方法*/
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	/**属性name的setter方法*/
	public void setName(String name) {
		this.name = name;
	}
	/**属性birthday的getter方法*/
	@Column(name="BIRTHDAY")
	public Date getBirthday() {
		return birthday;
	}
	/**属性birthday的setter方法*/
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**属性identifyNumber的getter方法*/
	@Column(name="IDENTIFYNUMBER")
	public String getIdentifyNumber() {
		return identifyNumber;
	}
	/**属性identifyNumber的setter方法*/
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}
	/**属性departureDate的getter方法*/
	@Column(name="DEPARTUREDATE")
	public Date getDepartureDate() {
		return departureDate;
	}
	/**属性departureDate的setter方法*/
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	/**属性legType的getter方法*/
	@Column(name="LEGTYPE")
	public String getLegType() {
		return legType;
	}
	/**属性legType的setter方法*/
	public void setLegType(String legType) {
		this.legType = legType;
	}
	/**属性segmentCombination的getter方法*/
	@Column(name="SEGMENTCOMBINATION")
	public String getSegmentCombination() {
		return segmentCombination;
	}
	/**属性segmentCombination的setter方法*/
	public void setSegmentCombination(String segmentCombination) {
		this.segmentCombination = segmentCombination;
	}
	/**属性flightCombination的getter方法*/
	@Column(name="FLIGHTCOMBINATION")
	public String getFlightCombination() {
		return flightCombination;
	}
	/**属性flightCombination的setter方法*/
	public void setFlightCombination(String flightCombination) {
		this.flightCombination = flightCombination;
	}
	/**属性premium的getter方法*/
	@Column(name="PREMIUM")
	public String getPremium() {
		return premium;
	}
	/**属性premium的setter方法*/
	public void setPremium(String premium) {
		this.premium = premium;
	}
	/**属性transactionNo的getter方法*/
	@Column(name="TRANSACTIONNO")
	public String getTransactionNo() {
		return transactionNo;
	}
	/**属性transactionNo的setter方法*/
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	/**属性transactionDetailNo的getter方法*/
	@Column(name="TRANSACTIONDETAILNO")
	public String getTransactionDetailNo() {
		return transactionDetailNo;
	}
	/**属性transactionDetailNo的setter方法*/
	public void setTransactionDetailNo(String transactionDetailNo) {
		this.transactionDetailNo = transactionDetailNo;
	}
	/**属性trip的getter方法*/
	@Column(name="TRIP")
	public String getTrip() {
		return trip;
	}
	/**属性trip的setter方法*/
	public void setTrip(String trip) {
		this.trip = trip;
	}
	/**属性amount的getter方法*/
	@Column(name="AMOUNT")
	public String getAmount() {
		return amount;
	}
	/**属性amount的setter方法*/
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**属性salesRevenue的getter方法*/
	@Column(name="SALESREVENUE")
	public String getSalesRevenue() {
		return salesRevenue;
	}
	/**属性salesRevenue的setter方法*/
	public void setSalesRevenue(String salesRevenue) {
		this.salesRevenue = salesRevenue;
	}
	/**属性netAmount的getter方法*/
	@Column(name="NETAMOUNT")
	public String getNetAmount() {
		return netAmount;
	}
	/**属性netAmount的setter方法*/
	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}
	/**属性payType的getter方法*/
	@Column(name="PAYTYPE")
	public String getPayType() {
		return payType;
	}
	/**属性payType的setter方法*/
	public void setPayType(String payType) {
		this.payType = payType;
	}
	/**属性airlineCompany的getter方法*/
	@Column(name="AIRLINECOMPANY")
	public String getAirlineCompany() {
		return airlineCompany;
	}
	/**属性airlineCompany的setter方法*/
	public void setAirlineCompany(String airlineCompany) {
		this.airlineCompany = airlineCompany;
	}
	/**属性netReceivable的getter方法*/
	@Column(name="NETRECEIVABLE")
	public String getNetReceivable() {
		return netReceivable;
	}
	/**属性netReceivable的setter方法*/
	public void setNetReceivable(String netReceivable) {
		this.netReceivable = netReceivable;
	}
	/**属性refundDate的getter方法*/
	@Column(name="REFUNDDATE")
	public Date getRefundDate() {
		return refundDate;
	}
	/**属性refundDate的setter方法*/
	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}
	/**属性tcol1的getter方法*/
	@Column(name="TCOL1")
	public String getTcol1() {
		return tcol1;
	}
	/**属性tcol1的setter方法*/
	public void setTcol1(String tcol1) {
		this.tcol1 = tcol1;
	}
	/**属性tcol2的getter方法*/
	@Column(name="TCOL2")
	public String getTcol2() {
		return tcol2;
	}
	/**属性tcol2的setter方法*/
	public void setTcol2(String tcol2) {
		this.tcol2 = tcol2;
	}
	/**属性flag的getter方法*/
	@Column(name="FLAG")
	public String getFlag() {
		return flag;
	}
	/**属性flag的setter方法*/
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	

}


