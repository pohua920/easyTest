package com.sinosoft.undwrt.undwrtBase.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sinosoft.common.schema.model.CITradeVanDemand;
import com.sinosoft.undwrt.undwrtBase.model.RenewalInfoId;

@Entity(name = "RenewalInfo_UNDWRT")
@Table(name = "RENEWALINFO")
public class RenewalInfo implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private RenewalInfoId id;
    private String quoteNo;
    private String proposalNo;
    private String identifyNumber;
    private String insuredNature;
    private String useNatureCode;
    private String licenseNo;
    private String operatorCode;
    //0表示初始化 1表示导出txt 2暂时保留  3表示关贸返回文件存入库成功  4保费计算成功  5核保成功 6导出成功
    private String renewalFlag;
    private Date operateDate;
    private String operatorName;
  
    private List<CITradeVanDemand> cITradeVanDemand = new ArrayList<CITradeVanDemand>(0);

    public RenewalInfo() {
    }

    @EmbeddedId
    @AttributeOverrides( {
        @AttributeOverride(name="policyNo", column=@Column(name="POLICYNO") ), 
        @AttributeOverride(name="renewalNo", column=@Column(name="RENEWALNO") ) } )
    public RenewalInfoId getId() {
        return id;
    }

    public void setId(RenewalInfoId id) {
        this.id = id;
    }

    @Column(name="QUOTENO")
    public String getQuoteNo() {
        return quoteNo;
    }

    public void setQuoteNo(String quoteNo) {
        this.quoteNo = quoteNo;
    }

    @Column(name="PROPOSALNO")
    public String getProposalNo() {
        return proposalNo;
    }

    public void setProposalNo(String proposalNo) {
        this.proposalNo = proposalNo;
    }

    @Column(name="IDENTIFYNUMBER")
    public String getIdentifyNumber() {
        return identifyNumber;
    }

    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
    }

    @Column(name="INSUREDNATURE")
    public String getInsuredNature() {
        return insuredNature;
    }

    public void setInsuredNature(String insuredNature) {
        this.insuredNature = insuredNature;
    }

    @Column(name="USENATURECODE")
    public String getUseNatureCode() {
        return useNatureCode;
    }

    public void setUseNatureCode(String useNatureCode) {
        this.useNatureCode = useNatureCode;
    }

    @Column(name="LICENSENO")
    public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	
	
    @Column(name="OPERATORCODE")
    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    @Column(name="RENEWALFLAG")
    public String getRenewalFlag() {
        return renewalFlag;
    }

    public void setRenewalFlag(String renewalFlag) {
        this.renewalFlag = renewalFlag;
    }

    @Column(name="OPERATEDATE")
    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    @Column(name="OPERATORNAME")
    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    
    
}
