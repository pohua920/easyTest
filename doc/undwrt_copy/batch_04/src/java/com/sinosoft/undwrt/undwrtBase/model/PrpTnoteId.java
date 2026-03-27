// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
package com.sinosoft.undwrt.undwrtBase.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpTnoteId
 */
@Embeddable
public class PrpTnoteId implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    /** 属性序列号 */
    private Integer serialNo;

    /** 属性投保单号 */
    private String proposalNo;


    /**
     * 类PrpCengageId的默认构造方法
     */
    public PrpTnoteId() {
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
    
    @Column(name = "PROPOSALNO")
    public String getProposalNo() {
        return proposalNo;
    }

    public void setProposalNo(String proposalNo) {
        this.proposalNo = proposalNo;
    }

}
