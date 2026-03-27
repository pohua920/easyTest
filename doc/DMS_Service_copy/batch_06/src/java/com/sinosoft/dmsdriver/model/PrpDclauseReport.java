package com.sinosoft.dmsdriver.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="PRPDCLAUSEREPORT", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"CLAUSECODE", "VERSIONNO"})})
public class PrpDclauseReport
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private PrpDclauseReportId id;
  private Date validDate;
  private Date invalidDate;
  private String validInd;
  private String auditFlag;
  private String tcol1;
  private String tcol2;
  private String riskCode;
  private String riskName;
  private String policyType;
  private String printSign;

  @EmbeddedId
  @AttributeOverrides({@javax.persistence.AttributeOverride(name="clauseCode", column=@Column(name="CLAUSECODE")), @javax.persistence.AttributeOverride(name="versionno", column=@Column(name="VERSIONNO")), @javax.persistence.AttributeOverride(name="reportNo", column=@Column(name="REPORTNO"))})
  public PrpDclauseReportId getId()
  {
    return this.id;
  }

  public void setId(PrpDclauseReportId id) {
    this.id = id;
  }
  @Temporal(TemporalType.DATE)
  @Column(name="VALIDDATE")
  public Date getValidDate() {
    return this.validDate;
  }

  public void setValidDate(Date validDate) {
    this.validDate = validDate;
  }
  @Temporal(TemporalType.DATE)
  @Column(name="INVALIDDATE")
  public Date getInvalidDate() {
    return this.invalidDate;
  }

  public void setInvalidDate(Date invalidDate) {
    this.invalidDate = invalidDate;
  }

  @Column(name="VALIDIND")
  public String getValidInd() {
    return this.validInd;
  }

  public void setValidInd(String validInd) {
    this.validInd = validInd;
  }

  @Column(name="TCOL2")
  public String getTcol2() {
    return this.tcol2;
  }

  public void setTcol2(String tcol2) {
    this.tcol2 = tcol2;
  }
  @Column(name="AUDITFLAG")
  public String getAuditFlag() {
    return this.auditFlag;
  }

  public void setAuditFlag(String auditFlag) {
    this.auditFlag = auditFlag;
  }
  @Column(name="TCOL1")
  public String getTcol1() {
    return this.tcol1;
  }

  public void setTcol1(String tcol1) {
    this.tcol1 = tcol1;
  }
  @Column(name="RISKCODE")
  public String getRiskCode() {
	return riskCode;
}

public void setRiskCode(String riskCode) {
	this.riskCode = riskCode;
}
@Column(name="RISKNAME")
public String getRiskName() {
	return riskName;
}
@Column(name="POLICYTYPE")
public String getPolicyType() {
	return policyType;
}

public void setPolicyType(String policyType) {
	this.policyType = policyType;
}
@Column(name="PRINTSIGN")
public String getPrintSign() {
	return printSign;
}

public void setPrintSign(String printSign) {
	this.printSign = printSign;
}
public void setRiskName(String riskName) {
	this.riskName = riskName;
}
  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
    return result;
  }

  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (super.getClass() != obj.getClass())
      return false;
    PrpDclauseReport other = (PrpDclauseReport)obj;
    if (this.id == null)
      if (other.id != null)
        return false;
    else if (!this.id.equals(other.id))
      return false;
    return true;
  }
}