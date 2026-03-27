package cn.com.sinosoft.dms.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpDidentifier
 */
@Entity
@Table(name = "PrpDidentifier")	
public class PrpDidentifier implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 属性流程动作标识 */
	private PrpDidentifierId id;
	
	/** 属性国家代码 */
	private String countryCode;

	/** 属性国家中文名称 */
	private String countryCName;

	/** 属性国家英文名称 */
	private String countryEName;
	
	/** 属性港口名称 */
	private String portName;

	/** 属性优先级别 */
	private String identifierOrder;

	/** 属性检验人名称*/
	private String identifierName;
	
	/** 属性检验人地址*/
	private String identifierAddress;

	/** 属性检验人类型 */
	private String identifierType;

	/** 属性效力状态*/
	private String validStatus;
	
	/** 属性标志*/
	private String flag;
	
	/**
	 * 类PrpDidentifier的默认构造方法
	 */
	public 	PrpDidentifier() {
	}
	/**       
	 * 属性流程动作标识的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides( { @AttributeOverride(name = "identifierCode", column = @Column(name = "identifierCode")),
			@AttributeOverride(name = "portCode", column = @Column(name = "portCode")) })
	public PrpDidentifierId getId() {
		return this.id;
	}

	/**       
	 * 属性流程动作标识的setter方法
	 */
	public void setId(PrpDidentifierId id) {
		this.id = id;
	}
	
	/** 
	 * 属性国家代码的的getter方法
	 */
	
	@Column(name = "countryCode")
	public String getCountryCode() {
		return countryCode;
	}
	
	/** 
	 * 属性国家代码的setter方法
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	/** 
	 * 属性国家中文名称的getter方法
	 */
	
	@Column(name = "countryCName")
	public String getCountryCName() {
		return countryCName;
	}
	
	/** 
	 * 属性国家中文名称的setter方法
	 */
	public void setCountryCName(String countryCName) {
		this.countryCName = countryCName;
	}
	
	/** 
	 * 属性国家英文名称的getter方法
	 */
	
	@Column(name = "CountryEName")
	public String getCountryEName() {
		return countryEName;
	}
	
	/** 
	 * 属性国家英文名称的setter方法
	 */
	public void setCountryEName(String countryEName) {
		this.countryEName = countryEName;
	}
	
	/** 
	 * 属性港口名称的getter方法
	 */
	
	@Column(name = "portName")
	public String getPortName() {
		return portName;
	}
	
	/** 
	 * 属性港口名称的setter方法
	 */
	public void setPortName(String portName) {
		this.portName = portName;
	}

	/** 
	 * 属性优先级别的getter方法
	 */
	
	@Column(name = "identifierOrder")
	public String getIdentifierOrder() {
		return identifierOrder;
	}
	
	/** 
	 * 属性优先级别的setter方法
	 */
	public void setIdentifierOrder(String identifierOrder) {
		this.identifierOrder = identifierOrder;
	}
	
	/** 
	 * 属性检验人名称的getter方法
	 * */
	
	@Column(name = "identifierName")
	public String getIdentifierName() {
		return identifierName;
	}
	
	/** 
	 * 属性检验人名称的setter方法
	 * */
	public void setIdentifierName(String identifierName) {
		this.identifierName = identifierName;
	}

	/** 
	 * 属性检验人地址的getter方法
	 * */
	
	@Column(name = "identifierAddress")
	public String getIdentifierAddress() {
		return identifierAddress;
	}
	
	/** 
	 * 属性检验人地址的setter方法
	 * */
	public void setIdentifierAddress(String identifierAddress) {
		this.identifierAddress = identifierAddress;
	}
	
	/** 
	 * 属性检验人类型的getter方法
	 */
	
	@Column(name = "identifierType")
	public String getIdentifierType() {
		return identifierType;
	}
	
	/** 
	 * 属性检验人类型的setter方法
	 */
	public void setIdentifierType(String identifierType) {
		this.identifierType = identifierType;
	}
	
	/** 
	 * 属性效力状态的getter方法
	 * */
	
	@Column(name = "validStatus")
	public String getValidStatus() {
		return validStatus;
	}
	
	/** 
	 * 属性效力状态的setter方法
	 * */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	
	/** 
	 * 属性标志的getter方法
	 * */
	
	@Column(name = "flag")
	public String getFlag() {
		return flag;
	}
	
	/** 
	 * 属性标志的setter方法
	 * */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
