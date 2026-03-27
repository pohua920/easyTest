package com.sinosoft.dmsdriver.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRPDITEMSHIP")
public class PrpDitemShip implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	
	 /** 船舶代碼*/
	private String shipCode;
	 /** 船号 */
    private String shipNo;
    /** 船名中文 */
    private String shipCName;	
    /** 船名(英文) */
    private String shipEName;	
    /** 曾用名 */
    private String oldShipName;
    /** 船东 */
    private String shipOwner;
    /** 建造起始日期 */
    private Date makeStartDate; 
    /** 建造终止日期 */
    private Date makeEndDate;
    /** 船级 */
    private String stepHull;
    /** 船状态 */
    private String shipFlag;
    /** 船舶一级种类 */
    private String shipTypeCode;  
    /** 船舶二级种类 */
    private String useNatureCode;
    /** 船质结构代码 */
    private String shipStruct;
    /** 注册地点 */
    private String registrySite;
    /** 吨位数 */
    private BigDecimal tonCount;
    /** 座位数 */
    private Long seatCount;
    /** 载重吨 */
    private BigDecimal loadTon;
    /** 船籍港 */
    private String shipPort;
    /** 备注 */
    private String remark;
    /** 老船名 */
    private String oldShipEName;
    /** 船操控者 */
    private String shipOperator;
    /** 證書載明的所有人 */
    private String certificateOwner;
    /** 經營方式 */
    private String manageType;
    /** 小船執照日期 */
    private Date manageStartDate;   
    /** 船舶檢驗證書有效期起期 */
    private Date checkStartDate;
    /** 船舶檢驗證書有效期止期 */
    private Date checkEndDate; 
    /** 建造年月 */
    private String makeYearMonth;
    private String shipTypeCodeCName;
    private String useNatureCodeCName;
    private String useNatureCodeEName;
    
    public PrpDitemShip() {
	}
    
    @Id
    @Column(name = "SHIPCODE",nullable = false)
    public String getShipCode() {
        return this.shipCode;
    }

    public void setShipCode(String shipCode) {
        this.shipCode = shipCode;
    }
	@Column(name = "SHIPNO")
	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}
	@Column(name = "SHIPCNAME")
	public String getShipCName() {
		return shipCName;
	}

	public void setShipCName(String shipCName) {
		this.shipCName = shipCName;
	}
	@Column(name = "SHIPENAME")
	public String getShipEName() {
		return shipEName;
	}

	public void setShipEName(String shipEName) {
		this.shipEName = shipEName;
	}
	@Column(name = "OLDSHIPNAME")
	public String getOldShipName() {
		return oldShipName;
	}

	public void setOldShipName(String oldShipName) {
		this.oldShipName = oldShipName;
	}
	@Column(name = "SHIPOWNER")
	public String getShipOwner() {
		return shipOwner;
	}

	public void setShipOwner(String shipOwner) {
		this.shipOwner = shipOwner;
	}
	@Column(name = "MAKESTARTDATE")
	public Date getMakeStartDate() {
		return makeStartDate;
	}

	public void setMakeStartDate(Date makeStartDate) {
		this.makeStartDate = makeStartDate;
	}
	@Column(name = "MAKEENDDATE")
	public Date getMakeEndDate() {
		return makeEndDate;
	}

	public void setMakeEndDate(Date makeEndDate) {
		this.makeEndDate = makeEndDate;
	}
	@Column(name = "STEPHULL")
	public String getStepHull() {
		return stepHull;
	}

	public void setStepHull(String stepHull) {
		this.stepHull = stepHull;
	}
	@Column(name = "SHIPFLAG")
	public String getShipFlag() {
		return shipFlag;
	}

	public void setShipFlag(String shipFlag) {
		this.shipFlag = shipFlag;
	}
	@Column(name = "SHIPTYPECODE")
	public String getShipTypeCode() {
		return shipTypeCode;
	}

	public void setShipTypeCode(String shipTypeCode) {
		this.shipTypeCode = shipTypeCode;
	}
	
	@Column(name = "USENATURECODE")
	public String getUseNatureCode() {
		return useNatureCode;
	}
	public void setUseNatureCode(String useNatureCode) {
		this.useNatureCode = useNatureCode;
	}

	@Column(name = "SHIPSTRUCT")
	public String getShipStruct() {
		return shipStruct;
	}

	public void setShipStruct(String shipStruct) {
		this.shipStruct = shipStruct;
	}
	@Column(name = "REGISTRYSITE")
	public String getRegistrySite() {
		return registrySite;
	}

	public void setRegistrySite(String registrySite) {
		this.registrySite = registrySite;
	}
	@Column(name = "TONCOUNT")
	public BigDecimal getTonCount() {
		return tonCount;
	}

	public void setTonCount(BigDecimal tonCount) {
		this.tonCount = tonCount;
	}
	@Column(name = "SEATCOUNT")
	public Long getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(Long seatCount) {
		this.seatCount = seatCount;
	}
	@Column(name = "LOADTON")
	public BigDecimal getLoadTon() {
		return loadTon;
	}

	public void setLoadTon(BigDecimal loadTon) {
		this.loadTon = loadTon;
	}
	@Column(name = "SHIPPORT")
	public String getShipPort() {
		return shipPort;
	}

	public void setShipPort(String shipPort) {
		this.shipPort = shipPort;
	}
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "OLDSHIPENAME")
	public String getOldShipEName() {
		return oldShipEName;
	}

	public void setOldShipEName(String oldShipEName) {
		this.oldShipEName = oldShipEName;
	}
	@Column(name = "SHIPOPERATOR")
	public String getShipOperator() {
		return shipOperator;
	}

	public void setShipOperator(String shipOperator) {
		this.shipOperator = shipOperator;
	}
	@Column(name = "CERTIFICATEOWNER")
	public String getCertificateOwner() {
		return certificateOwner;
	}

	public void setCertificateOwner(String certificateOwner) {
		this.certificateOwner = certificateOwner;
	}
	@Column(name = "MANAGETYPE")
	public String getManageType() {
		return manageType;
	}

	public void setManageType(String manageType) {
		this.manageType = manageType;
	}
	@Column(name = "MANAGESTARTDATE")
	public Date getManageStartDate() {
		return manageStartDate;
	}

	public void setManageStartDate(Date manageStartDate) {
		this.manageStartDate = manageStartDate;
	}
	@Column(name = "CHECKSTARTDATE")
	public Date getCheckStartDate() {
		return checkStartDate;
	}

	public void setCheckStartDate(Date checkStartDate) {
		this.checkStartDate = checkStartDate;
	}
	@Column(name = "CHECKENDDATE")
	public Date getCheckEndDate() {
		return checkEndDate;
	}

	public void setCheckEndDate(Date checkEndDate) {
		this.checkEndDate = checkEndDate;
	}
	@Column(name = "MAKEYEARMONTH")
	public String getMakeYearMonth() {
		return makeYearMonth;
	}

	public void setMakeYearMonth(String makeYearMonth) {
		this.makeYearMonth = makeYearMonth;
	}
	@Column(name = "SHIPTYPECODECNAME")
	public String getShipTypeCodeCName() {
		return shipTypeCodeCName;
	}

	public void setShipTypeCodeCName(String shipTypeCodeCName) {
		this.shipTypeCodeCName = shipTypeCodeCName;
	}
	@Column(name = "USENATURECODECNAME")
	public String getUseNatureCodeCName() {
		return useNatureCodeCName;
	}

	public void setUseNatureCodeCName(String useNatureCodeCName) {
		this.useNatureCodeCName = useNatureCodeCName;
	}
	@Column(name = "USENATURECODEENAME")
	public String getUseNatureCodeEName() {
		return useNatureCodeEName;
	}

	public void setUseNatureCodeEName(String useNatureCodeEName) {
		this.useNatureCodeEName = useNatureCodeEName;
	}
    
}
