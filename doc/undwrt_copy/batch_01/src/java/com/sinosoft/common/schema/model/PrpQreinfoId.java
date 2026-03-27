// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
package com.sinosoft.common.schema.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 */
@Embeddable
public class PrpQreinfoId implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    /** 属性序列号 */
    private Integer serialNo;

    /** 属性投保单号 */
    private String proposalNo;


    /**
     * 类PrpCengageId的默认构造方法
     */
    public PrpQreinfoId() {
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
