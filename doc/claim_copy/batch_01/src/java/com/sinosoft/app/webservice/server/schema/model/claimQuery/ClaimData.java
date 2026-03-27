package com.sinosoft.app.webservice.server.schema.model.claimQuery;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.sinosoft.app.webservice.util.JaxbDateAdapter;

/**
 *自定义理赔查询对象
 * 
  */
@XmlRootElement(name="claimData")
@XmlAccessorType(XmlAccessType.FIELD)

public class ClaimData {
	/** 賠案號碼  */
	private String claimNo;
	/** 保單險類 */
	private String code;
	/** 賠案金額 */
	private Double sumPaid;
	/** 賠案狀態 */
	private String status;
	/** 結案日期 */
	private Date endCaseDate;
	/** 賠付日期 */
	private Date payDate;
	
	@XmlElementWrapper(name="undwrtList")
    @XmlElement(name="undwrtData")
    private List<UndwrtData> undwrtList;
	
    public String getClaimNo() {
        return claimNo;
    }
    public void setClaimNo(String claimNo) {
        this.claimNo = claimNo;
    }
    
    public Double getSumPaid() {
        return sumPaid;
    }
    public void setSumPaid(Double sumPaid) {
        this.sumPaid = sumPaid;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getEndCaseDate() {
        return endCaseDate;
    }
    public void setEndCaseDate(Date endCaseDate) {
        this.endCaseDate = endCaseDate;
    }
    public Date getPayDate() {
        return payDate;
    }
    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }
    public List<UndwrtData> getUndwrtList() {
        return undwrtList;
    }
    public void setUndwrtList(List<UndwrtData> undwrtList) {
        this.undwrtList = undwrtList;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    
    
}
