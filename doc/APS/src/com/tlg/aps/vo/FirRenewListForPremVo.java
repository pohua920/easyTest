package com.tlg.aps.vo;

import java.math.BigDecimal;
import java.util.Date;

/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 */
public class FirRenewListForPremVo {
	
	private BigDecimal oid;
	private String rnType;
	private String rnPayway;
	private String oldPolicyno;
	private Date oldEnddate;
	private Long oldAmtQ;
	private Long oldAmtF1;
	private Long oldAmtF2;
	private String proposalno;
	private String addresscode;
	private String wallmaterial;
	private String roofmaterial;
	private String sumfloors;
	private BigDecimal buildarea;
	/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額  start */
	private Long amtF1;
	private String businessnature;
	/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額  end */
	
	//mantis：FIR0570_1，處理人員：DP0706，需求單編號：FIR0570_1 住火_APS每月應續件產生排程START
	//mail 內容及sql語法修改
	private String dataStatus;
	private BigDecimal nrec;
	//mantis：FIR0570_1，處理人員：DP0706，需求單編號：FIR0570_1 住火_APS每月應續件產生排程END
	
	public BigDecimal getOid() {
		return oid;
	}
	public void setOid(BigDecimal oid) {
		this.oid = oid;
	}
	public String getRnType() {
		return rnType;
	}
	public void setRnType(String rnType) {
		this.rnType = rnType;
	}
	public String getRnPayway() {
		return rnPayway;
	}
	public void setRnPayway(String rnPayway) {
		this.rnPayway = rnPayway;
	}
	public String getOldPolicyno() {
		return oldPolicyno;
	}
	public void setOldPolicyno(String oldPolicyno) {
		this.oldPolicyno = oldPolicyno;
	}
	public Date getOldEnddate() {
		return oldEnddate;
	}
	public void setOldEnddate(Date oldEnddate) {
		this.oldEnddate = oldEnddate;
	}
	public Long getOldAmtQ() {
		return oldAmtQ;
	}
	public void setOldAmtQ(Long oldAmtQ) {
		this.oldAmtQ = oldAmtQ;
	}
	public Long getOldAmtF1() {
		return oldAmtF1;
	}
	public void setOldAmtF1(Long oldAmtF1) {
		this.oldAmtF1 = oldAmtF1;
	}
	public Long getOldAmtF2() {
		return oldAmtF2;
	}
	public void setOldAmtF2(Long oldAmtF2) {
		this.oldAmtF2 = oldAmtF2;
	}
	public String getProposalno() {
		return proposalno;
	}
	public void setProposalno(String proposalno) {
		this.proposalno = proposalno;
	}
	public String getAddresscode() {
		return addresscode;
	}
	public void setAddresscode(String addresscode) {
		this.addresscode = addresscode;
	}
	public String getWallmaterial() {
		return wallmaterial;
	}
	public void setWallmaterial(String wallmaterial) {
		this.wallmaterial = wallmaterial;
	}
	public String getRoofmaterial() {
		return roofmaterial;
	}
	public void setRoofmaterial(String roofmaterial) {
		this.roofmaterial = roofmaterial;
	}
	public String getSumfloors() {
		return sumfloors;
	}
	public void setSumfloors(String sumfloors) {
		this.sumfloors = sumfloors;
	}
	public BigDecimal getBuildarea() {
		return buildarea;
	}
	public void setBuildarea(BigDecimal buildarea) {
		this.buildarea = buildarea;
	}
	//mantis：FIR0570_1，處理人員：DP0706，需求單編號：FIR0570_1 住火_APS每月應續件產生排程START
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	public BigDecimal getNrec() {
		return nrec;
	}
	public void setNrec(BigDecimal nrec) {
		this.nrec = nrec;
	}
	//mantis：FIR0570_1，處理人員：DP0706，需求單編號：FIR0570_1 住火_APS每月應續件產生排程END
	/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額  start */
	public Long getAmtF1() {
		return amtF1;
	}
	public void setAmtF1(Long amtF1) {
		this.amtF1 = amtF1;
	}
	public String getBusinessnature() {
		return businessnature;
	}
	public void setBusinessnature(String businessnature) {
		this.businessnature = businessnature;
	}
	/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額  end */
}
