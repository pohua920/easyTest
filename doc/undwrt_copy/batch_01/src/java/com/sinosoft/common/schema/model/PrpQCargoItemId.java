// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
package com.sinosoft.common.schema.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 */
@Embeddable
public class PrpQCargoItemId implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    /** 属性组号 */
    private String teamNo;

    /** 属性行号 */
    private String lineNo;

    /** 属性投保单号 */
    private String proposalNo;

    @Column(name="PROPOSALNO")
    public String getProposalNo() {
        return proposalNo;
    }

    public void setProposalNo(String proposalNo) {
        this.proposalNo = proposalNo;
    }

    /**
     * 类PrpCCargoItemId的默认构造方法
     */
    public PrpQCargoItemId() {
    }

    /**
     * 属性组号的getter方法
     */

    @Column(name = "TEAMNO")
    public String getTeamNo() {
        return this.teamNo;
    }

    /**
     * 属性组号的setter方法
     */
    public void setTeamNo(String teamNo) {
        this.teamNo = teamNo;
    }

    /**
     * 属性行号的getter方法
     */

    @Column(name = "LINENO")
    public String getLineNo() {
        return this.lineNo;
    }

    /**
     * 属性行号的setter方法
     */
    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof PrpQCargoItemId)) {
            return false;
        }
        PrpQCargoItemId castOther = (PrpQCargoItemId) other;

        return ((this.getProposalNo()==castOther.getProposalNo()) || ( this.getProposalNo()!=null && castOther.getProposalNo()!=null && this.getProposalNo().equals(castOther.getProposalNo())))
                && ((this.getTeamNo() == castOther.getTeamNo()) || (this.getTeamNo() != null && castOther.getTeamNo() != null && this.getTeamNo().equals(castOther.getTeamNo())))
                && ((this.getLineNo() == castOther.getLineNo()) || (this.getLineNo() != null && castOther.getLineNo() != null && this.getLineNo().equals(castOther.getLineNo())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getProposalNo() == null ? 0 : this.getProposalNo().hashCode());
        result = 37 * result + (getTeamNo() == null ? 0 : this.getTeamNo().hashCode());
        result = 37 * result + (getLineNo() == null ? 0 : this.getLineNo().hashCode());
        return result;
    }

}
