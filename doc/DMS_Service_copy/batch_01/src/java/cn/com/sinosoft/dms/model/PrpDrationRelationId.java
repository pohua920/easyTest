package cn.com.sinosoft.dms.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrpDrationRelationId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主方案代码
	 */
	private String rationCodeMain;
	/**
	 * 辅助方案代码
	 */
	private String rationCodeAssist;

	public PrpDrationRelationId() {
	}
	@Column(name = "RATIONCODEMAIN")
	public String getRationCodeMain() {
		return rationCodeMain;
	}

	public void setRationCodeMain(String rationCodeMain) {
		this.rationCodeMain = rationCodeMain;
	}
	@Column(name = "RATIONCODEASSIST")
	public String getRationCodeAssist() {
		return rationCodeAssist;
	}

	public void setRationCodeAssist(String rationCodeAssist) {
		this.rationCodeAssist = rationCodeAssist;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((rationCodeAssist == null) ? 0 : rationCodeAssist.hashCode());
		result = prime * result
				+ ((rationCodeMain == null) ? 0 : rationCodeMain.hashCode());
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
		PrpDrationRelationId other = (PrpDrationRelationId) obj;
		if (rationCodeAssist == null) {
			if (other.rationCodeAssist != null)
				return false;
		} else if (!rationCodeAssist.equals(other.rationCodeAssist))
			return false;
		if (rationCodeMain == null) {
			if (other.rationCodeMain != null)
				return false;
		} else if (!rationCodeMain.equals(other.rationCodeMain))
			return false;
		return true;
	}

}
