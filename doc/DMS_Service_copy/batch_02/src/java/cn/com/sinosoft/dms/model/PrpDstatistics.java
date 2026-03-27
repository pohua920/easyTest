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
 * POJO类prpdstatistics
 */
@Entity
@Table(name = "prpdstatistics")
public class PrpDstatistics implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性出单机构 */
	private String makeCom;

	/** 属性ksdm */
	private String ksdm;

	/** 属性opCode */
	private String opCode;

	/** 属性statisticsYM */
	private Date statisticsYM;

	/** 属性insertTime */
	private Date insertTime;

	/** 属性最后修改时间 */
	private Date updateTime;

	/** 属性标志位 */
	private String flag;

	/**
	 * 类prpdstatistics的默认构造方法
	 */
	public PrpDstatistics() {
	}

	/**       
	 * 属性出单机构的getter方法
	 */
	@Id
	@Column(name = "makecom")
	public String getMakeCom() {
		return this.makeCom;
	}

	/**       
	 * 属性出单机构的setter方法
	 */
	public void setMakeCom(String makeCom) {
		this.makeCom = makeCom;
	}

	/**       
	 * 属性ksdm的getter方法
	 */

	@Column(name = "ksdm")
	public String getKsdm() {
		return this.ksdm;
	}

	/**       
	 * 属性ksdm的setter方法
	 */
	public void setKsdm(String ksdm) {
		this.ksdm = ksdm;
	}

	/**       
	 * 属性opcode的getter方法
	 */

	@Column(name = "opcode")
	public String getOpCode() {
		return this.opCode;
	}

	/**       
	 * 属性opcode的setter方法
	 */
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	/**       
	 * 属性statisticsym的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "statisticsym")
	public Date getStatisticsYM() {
		return this.statisticsYM;
	}

	/**       
	 * 属性statisticsym的setter方法
	 */
	public void setStatisticsYM(Date statisticsYM) {
		this.statisticsYM = statisticsYM;
	}

	/**       
	 * 属性inserttime的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inserttime")
	public Date getInsertTime() {
		return this.insertTime;
	}

	/**       
	 * 属性inserttime的setter方法
	 */
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	/**       
	 * 属性最后修改时间的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatetime")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	/**       
	 * 属性最后修改时间的setter方法
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
