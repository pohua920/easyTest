package cn.com.sinosoft.dms.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpDidentifierId
 */
@Embeddable
public class PrpDidentifierId implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 属性检验人代码 */
	private String identifierCode;
	
	/** 属性港口代码*/
	private String portCode;

	/**
	 * 类PrpDidentifierId的默认构造方法
	 */
	public PrpDidentifierId() {
	}
	
	/** 
	 * 属性检验人代码的getter方法
	 */
	
	@Column(name = "identifierCode")
	public String getIdentifierCode() {
		return identifierCode;
	}
	/** 
	 * 属性检验人代码的setter方法
	 */
	public void setIdentifierCode(String identifierCode) {
		this.identifierCode = identifierCode;
	}
	/** 
	 * 属性港口代码getter方法
	 */
	
	@Column(name = "portCode")
	public String getPortCode() {
		return portCode;
	}
	/** 
	 * 属性港口代码setter方法
	 */
	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((identifierCode == null) ? 0 : identifierCode.hashCode());
		result = prime * result
				+ ((portCode == null) ? 0 : portCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PrpDidentifierId other = (PrpDidentifierId) obj;
		if (identifierCode == null) {
			if (other.identifierCode != null)
				return false;
		} else if (!identifierCode.equals(other.identifierCode))
			return false;
		if (portCode == null) {
			if (other.portCode != null)
				return false;
		} else if (!portCode.equals(other.portCode))
			return false;
		return true;
	}
	
	
}
