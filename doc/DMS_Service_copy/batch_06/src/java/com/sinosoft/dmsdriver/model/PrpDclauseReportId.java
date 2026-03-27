package com.sinosoft.dmsdriver.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrpDclauseReportId
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String clauseCode;
  private String versionno;
  private String reportNo;

  @Column(name="CLAUSECODE")
  public String getClauseCode()
  {
    return this.clauseCode;
  }

  public void setClauseCode(String clauseCode) {
    this.clauseCode = clauseCode;
  }

  @Column(name="VERSIONNO")
  public String getVersionno() {
    return this.versionno;
  }

  public void setVersionno(String versionno) {
    this.versionno = versionno;
  }

  @Column(name="REPORTNO")
  public String getReportNo() {
    return this.reportNo;
  }

  public void setReportNo(String reportNo) {
    this.reportNo = reportNo;
  }

  public int hashCode() {
    int prime = 31;
    int result = 1;
    result = 31 * result + (
      (this.clauseCode == null) ? 0 : this.clauseCode.hashCode());
    result = 31 * result + (
      (this.versionno == null) ? 0 : this.versionno.hashCode());
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
    PrpDclauseReportId other = (PrpDclauseReportId)obj;
    if (this.clauseCode == null)
      if (other.clauseCode != null)
        return false;
    else if (!this.clauseCode.equals(other.clauseCode))
      return false;
    if (this.versionno == null)
      if (other.versionno != null)
        return false;
    else if (!this.versionno.equals(other.versionno))
      return false;
    return true;
  }
}