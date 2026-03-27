package cn.com.sinosoft.dms.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO类prpdcompanycheck
 */
@Entity
@Table(name = "prpdcompanycheck")
public class PrpDcompanyCheck implements java.io.Serializable {
	private static final long	serialVersionUID	= 1L;

	/** 属性infoId */
	private String				infoId;

	/** 属性checkComcode */
	private String				checkComcode;

	/** 属性checkType */
	private String				checkType;

	/** 属性checkAccount */
	private String				checkAccount;

	/** 属性checkDate */
	private String				checkDate;

	/** 属性checkFlag */
	private String				checkFlag;

	/** 属性子公司报送时间 */
	private String				dateSend;

	/**
	 * 类prpdcompanycheck的默认构造方法
	 */
	public PrpDcompanyCheck() {
	}

	/**       
	 * 属性infoId的getter方法
	 */
	@Id
	@Column(name = "info_id")
	public String getInfoId() {
		return this.infoId;
	}

	/**       
	 * 属性infoId的setter方法
	 */
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	/**       
	 * 属性checkComcode的getter方法
	 */

	@Column(name = "check_comcode")
	public String getCheckComcode() {
		return this.checkComcode;
	}

	/**       
	 * 属性checkComcode的setter方法
	 */
	public void setCheckComcode(String checkComcode) {
		this.checkComcode = checkComcode;
	}

	/**       
	 * 属性checkType的getter方法
	 */

	@Column(name = "check_type")
	public String getCheckType() {
		return this.checkType;
	}

	/**       
	 * 属性checkType的setter方法
	 */
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	/**       
	 * 属性checkAccount的getter方法
	 */

	@Column(name = "check_account")
	public String getCheckAccount() {
		return this.checkAccount;
	}

	/**       
	 * 属性checkAccount的setter方法
	 */
	public void setCheckAccount(String checkAccount) {
		this.checkAccount = checkAccount;
	}

	/**       
	 * 属性checkDate的getter方法
	 */

	@Column(name = "check_date")
	public String getCheckDate() {
		return this.checkDate;
	}

	/**       
	 * 属性checkDate的setter方法
	 */
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	/**       
	 * 属性checkFlag的getter方法
	 */

	@Column(name = "check_flag")
	public String getCheckFlag() {
		return this.checkFlag;
	}

	/**       
	 * 属性checkFlag的setter方法
	 */
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	/**       
	 * 属性子公司报送时间的getter方法
	 */

	@Column(name = "date_send")
	public String getDatesend() {
		return this.dateSend;
	}

	/**       
	 * 属性子公司报送时间的setter方法
	 */
	public void setDatesend(String datesend) {
		this.dateSend = datesend;
	}
	
	public String enCode() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.infoId).append("|");
		sb.append(this.checkComcode).append("|");
		sb.append(this.checkType).append("|");
		sb.append(this.checkAccount).append("|");
		sb.append(this.checkDate).append("|");
		sb.append(this.checkFlag).append("|");
		sb.append(this.dateSend);
		return sb.toString();
	}
	
	public void deCode(String str) {
		String[] fields = str.split("\\|",-1);
		setInfoId(fields[0]);
		setCheckComcode(fields[0].split("-", -1)[1]);
		setCheckType(fields[1]);
		setCheckAccount(fields[2]);
		setCheckDate(fields[3]);
		setCheckFlag(fields[4]);
		setDatesend(fields[5]);
	}

}
