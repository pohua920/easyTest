// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
package com.sinosoft.claim.schema.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * POJO类PrpCopyInsuredNatureId
 */
@Embeddable
public class PrpCopyInsuredNatureId implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    /** 属性保单号码 */
	private String endorseNo;

    /** 属性序列号 */
    private Integer serialNo;

    /** 属性投保单号 */
    private String proposalNo;

    @Transient
    public String getProposalNo() {
        return proposalNo;
    }

    public void setProposalNo(String proposalNo) {
        this.proposalNo = proposalNo;
    }

    /**
     * 类PrpCinsuredNatureId的默认构造方法
     */
    public PrpCopyInsuredNatureId() {
    }

    public PrpCopyInsuredNatureId(String endorseNo, Integer serialNo) {
		this.endorseNo = endorseNo;
		this.serialNo = serialNo;
	}

	/**
     * 属性投保单号的getter方法
     */

    @Column(name = "endorseNo")
    public String getEndorseNo() {
        return this.endorseNo;
    }

    /**
     * 属性投保单号的setter方法
     */
    public void setEndorseNo(String endorseNo) {
        this.endorseNo = endorseNo;
    }

    /**
     * 属性序列号的getter方法
     */

    @Column(name = "SERIALNO")
    public Integer getSerialNo() {
        return this.serialNo;
    }

    /**
     * 属性序列号的setter方法
     */
    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof PrpCopyInsuredNatureId)) {
            return false;
        }
        PrpCopyInsuredNatureId castOther = (PrpCopyInsuredNatureId) other;

        return ((this.getEndorseNo() == castOther.getEndorseNo()) || (this.getEndorseNo() != null && castOther.getEndorseNo() != null && this.getEndorseNo().equals(castOther.getEndorseNo())))
                && ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getEndorseNo() == null ? 0 : this.getEndorseNo().hashCode());
        result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
        return result;
    }

}
