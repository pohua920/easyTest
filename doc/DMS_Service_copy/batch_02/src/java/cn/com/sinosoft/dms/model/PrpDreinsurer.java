package cn.com.sinosoft.dms.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类prpDreinsurer
 */
@Entity
@Table(name = "prpdreinsurer")
public class PrpDreinsurer implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性接受人代码 */
	private String reinsCode;

	/** 属性接受人全称 */
	private String longName;

	/** 属性接受人简称 */
	private String shortName;

	/** 属性所在城市／地区 */
	private String regionCode;

	/** 属性所属国家 */
	private String countryName;

	/** 属性所在地区分类D：国内，F-国外 */
	private String locationFlag;

	/** 属性中文地址 */
	private String caddr;

	/** 属性英文地址 */
	private String eaddr;

	/** 属性标的层级 */
	private String level;

	/** 属性合同业务联系人 */
	private String ttyLinker;

	/** 属性合同业务电话 */
	private String ttyPhone;

	/** 属性合同业务传真 */
	private String ttyFax;

	/** 属性合同业务EMAIL */
	private String ttyEmail;

	/** 属性临分业务联系人 */
	private String facLinker;

	/** 属性临分业务电话 */
	private String facPhone;

	/** 属性临分业务传真 */
	private String facFax;

	/** 属性临分业务EMAIL */
	private String facEmail;

	/** 属性备注 */
	private String remarks;

	/** 属性账号代码 */
	private String accCode;

	/** 属性变更日期 */
	private Date chgDate;

	/** 属性新的分保接受人 */
	private String newReinsCode;

	/** 属性有效标志 */
	private String validStatus;

	/** 属性维护人员代码 */
	private String operatorCode;

	/** 属性操作时间 */
	private Date operatedTime;

	/** 属性标志位 */
	private String flag;

	/**
	 * 类prpDreinsurer的默认构造方法
	 */
	public PrpDreinsurer() {
	}

	/**       
	 * 属性接受人代码的getter方法
	 */
	@Id
	@Column(name = "reinscode")
	public String getReinsCode() {
		return this.reinsCode;
	}

	/**       
	 * 属性接受人代码的setter方法
	 */
	public void setReinsCode(String reinsCode) {
		this.reinsCode = reinsCode;
	}

	/**       
	 * 属性接受人全称的getter方法
	 */

	@Column(name = "longname")
	public String getLongName() {
		return this.longName;
	}

	/**       
	 * 属性接受人全称的setter方法
	 */
	public void setLongName(String longName) {
		this.longName = longName;
	}

	/**       
	 * 属性接受人简称的getter方法
	 */

	@Column(name = "shortname")
	public String getShortName() {
		return this.shortName;
	}

	/**       
	 * 属性接受人简称的setter方法
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**       
	 * 属性所在城市／地区的getter方法
	 */

	@Column(name = "regioncode")
	public String getRegionCode() {
		return this.regionCode;
	}

	/**       
	 * 属性所在城市／地区的setter方法
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**       
	 * 属性所属国家的getter方法
	 */

	@Column(name = "countryname")
	public String getCountryName() {
		return this.countryName;
	}

	/**       
	 * 属性所属国家的setter方法
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**       
	 * 属性所在地区分类D：国内，F-国外的getter方法
	 */

	@Column(name = "locationflag")
	public String getLocationFlag() {
		return this.locationFlag;
	}

	/**       
	 * 属性所在地区分类D：国内，F-国外的setter方法
	 */
	public void setLocationFlag(String locationFlag) {
		this.locationFlag = locationFlag;
	}

	/**       
	 * 属性中文地址的getter方法
	 */

	@Column(name = "caddr")
	public String getCaddr() {
		return this.caddr;
	}

	/**       
	 * 属性中文地址的setter方法
	 */
	public void setCaddr(String caddr) {
		this.caddr = caddr;
	}

	/**       
	 * 属性英文地址的getter方法
	 */

	@Column(name = "eaddr")
	public String getEaddr() {
		return this.eaddr;
	}

	/**       
	 * 属性英文地址的setter方法
	 */
	public void setEaddr(String eaddr) {
		this.eaddr = eaddr;
	}

	/**       
	 * 属性标的层级的getter方法
	 */

	@Column(name = "level")
	public String getLevel() {
		return this.level;
	}

	/**       
	 * 属性标的层级的setter方法
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**       
	 * 属性合同业务联系人的getter方法
	 */

	@Column(name = "ttylinker")
	public String getTtyLinker() {
		return this.ttyLinker;
	}

	/**       
	 * 属性合同业务联系人的setter方法
	 */
	public void setTtyLinker(String ttyLinker) {
		this.ttyLinker = ttyLinker;
	}

	/**       
	 * 属性合同业务电话的getter方法
	 */

	@Column(name = "ttyphone")
	public String getTtyPhone() {
		return this.ttyPhone;
	}

	/**       
	 * 属性合同业务电话的setter方法
	 */
	public void setTtyPhone(String ttyPhone) {
		this.ttyPhone = ttyPhone;
	}

	/**       
	 * 属性合同业务传真的getter方法
	 */

	@Column(name = "ttyfax")
	public String getTtyFax() {
		return this.ttyFax;
	}

	/**       
	 * 属性合同业务传真的setter方法
	 */
	public void setTtyFax(String ttyFax) {
		this.ttyFax = ttyFax;
	}

	/**       
	 * 属性合同业务EMAIL的getter方法
	 */

	@Column(name = "ttyemail")
	public String getTtyEmail() {
		return this.ttyEmail;
	}

	/**       
	 * 属性合同业务EMAIL的setter方法
	 */
	public void setTtyEmail(String ttyEmail) {
		this.ttyEmail = ttyEmail;
	}

	/**       
	 * 属性临分业务联系人的getter方法
	 */

	@Column(name = "faclinker")
	public String getFacLinker() {
		return this.facLinker;
	}

	/**       
	 * 属性临分业务联系人的setter方法
	 */
	public void setFacLinker(String facLinker) {
		this.facLinker = facLinker;
	}

	/**       
	 * 属性临分业务电话的getter方法
	 */

	@Column(name = "facphone")
	public String getFacPhone() {
		return this.facPhone;
	}

	/**       
	 * 属性临分业务电话的setter方法
	 */
	public void setFacPhone(String facPhone) {
		this.facPhone = facPhone;
	}

	/**       
	 * 属性临分业务传真的getter方法
	 */

	@Column(name = "facfax")
	public String getFacFax() {
		return this.facFax;
	}

	/**       
	 * 属性临分业务传真的setter方法
	 */
	public void setFacFax(String facFax) {
		this.facFax = facFax;
	}

	/**       
	 * 属性临分业务EMAIL的getter方法
	 */

	@Column(name = "facemail")
	public String getFacEmail() {
		return this.facEmail;
	}

	/**       
	 * 属性临分业务EMAIL的setter方法
	 */
	public void setFacEmail(String facEmail) {
		this.facEmail = facEmail;
	}

	/**       
	 * 属性备注的getter方法
	 */

	@Column(name = "remarks")
	public String getRemarks() {
		return this.remarks;
	}

	/**       
	 * 属性备注的setter方法
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**       
	 * 属性账号代码的getter方法
	 */

	@Column(name = "acccode")
	public String getAccCode() {
		return this.accCode;
	}

	/**       
	 * 属性账号代码的setter方法
	 */
	public void setAccCode(String accCode) {
		this.accCode = accCode;
	}

	/**       
	 * 属性变更日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "chgdate")
	public Date getChgDate() {
		return this.chgDate;
	}

	/**       
	 * 属性变更日期的setter方法
	 */
	public void setChgDate(Date chgDate) {
		this.chgDate = chgDate;
	}

	/**       
	 * 属性新的分保接受人的getter方法
	 */

	@Column(name = "newreinscode")
	public String getNewReinsCode() {
		return this.newReinsCode;
	}

	/**       
	 * 属性新的分保接受人的setter方法
	 */
	public void setNewReinsCode(String newReinsCode) {
		this.newReinsCode = newReinsCode;
	}

	/**       
	 * 属性有效标志的getter方法
	 */

	@Column(name = "validstatus")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**       
	 * 属性有效标志的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**       
	 * 属性维护人员代码的getter方法
	 */

	@Column(name = "operatorcode")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**       
	 * 属性维护人员代码的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**       
	 * 属性操作时间的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "operatedtime")
	public Date getOperatedTime() {
		return this.operatedTime;
	}

	/**       
	 * 属性操作时间的setter方法
	 */
	public void setOperatedTime(Date operatedTime) {
		this.operatedTime = operatedTime;
	}

	/**       
	 * 属性标志位的getter方法
	 */

	@Column(name = "flag")
	public String getFlag() {
		return this.flag;
	}

	/**       
	 * 属性标志位的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
