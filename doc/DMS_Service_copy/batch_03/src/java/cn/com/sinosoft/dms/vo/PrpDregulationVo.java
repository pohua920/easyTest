package cn.com.sinosoft.dms.vo;

import java.math.BigDecimal;
import java.util.Date;

public class PrpDregulationVo {
	/** 条例代码 */
    private String regulationCode;
    /** 文件号 */
    private String fileCode;
    /** 文件名称 */
    private String fileName;
    /** 省代码 */
    private String proviceCode;
    /** 市代码 */
    private String cityCode;
    /** 县代码 */
    private String countyCode;
    /** 实施时间 */
    private Date validDate;
    /** 投保人归属行业类别 */
    private String indusCategory;
    /** 工伤鉴定周期 */
    private String identifyPeriod;
    /** 工伤发生率 */
    private BigDecimal jobInjuryRate;
    /** 条例类型（I-工伤条例，B-基本医疗政策） */
    private String regulationType;
    /** 审核状态(0-初始状态，1-审核通过，2-审核不通过) */
    private String auditFlag;
    /** 效力状态(0-无效，1-有效) */
    private String validStatus;
  //add by duanfa 20110923影像文件地址
    /** IMAGEPATH */
    private String imagePath;
    
	public String getRegulationCode() {
		return regulationCode;
	}
	public void setRegulationCode(String regulationCode) {
		this.regulationCode = regulationCode;
	}
	public String getFileCode() {
		return fileCode;
	}
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getProviceCode() {
		return proviceCode;
	}
	public void setProviceCode(String proviceCode) {
		this.proviceCode = proviceCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	public Date getValidDate() {
		return validDate;
	}
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	public String getIndusCategory() {
		return indusCategory;
	}
	public void setIndusCategory(String indusCategory) {
		this.indusCategory = indusCategory;
	}
	public String getIdentifyPeriod() {
		return identifyPeriod;
	}
	public void setIdentifyPeriod(String identifyPeriod) {
		this.identifyPeriod = identifyPeriod;
	}
	public BigDecimal getJobInjuryRate() {
		return jobInjuryRate;
	}
	public void setJobInjuryRate(BigDecimal jobInjuryRate) {
		this.jobInjuryRate = jobInjuryRate;
	}
	public String getRegulationType() {
		return regulationType;
	}
	public void setRegulationType(String regulationType) {
		this.regulationType = regulationType;
	}
	public String getAuditFlag() {
		return auditFlag;
	}
	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}
	public String getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
    
    
}
