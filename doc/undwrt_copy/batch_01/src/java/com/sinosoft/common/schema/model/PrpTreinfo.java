// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
package com.sinosoft.common.schema.model;


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 */
@Entity
@Table(name = "PRPTREINFO")
public class PrpTreinfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/** 属性id */
	private PrpTreinfoId id;

	/** 属性险类代码 */
	private String riskCode;
	
	/** 属性PRPCMAIN */
	private PrpTmain prpTmain;
	
	/**管理防护标志 */
	private String manfenceNature;
	/** 管理防护 */
	private String manfence;
	/**消防 设备标志*/
	private String hydrantNature;
	/**消防设备 */
	private String hydrant;
	
	/**去年承保资料标志 */
	private String lastcbdataFlag;
	/** 保险公司 */
	private String lastcbdataFlag1;
	/** 费率 */
	private String lastcbdataFlag2;
	/**总保费 */
	private String lastcbdataFlag3;
	
	/** 最近三年损失经验 */
	private String experienceloss;
	/** 损失原因 */
	private String lossReason;
	/**损失金额 */
	private String lossSum;
	/**损失时间 */
	private String lossData;
	/** 损失措施 */
	private String lossMeasure;	
	/** 属性tcol1 */
	private String tcol1;
	/** 属性tcol2 */
	private String tcol2;
	/** 属性tcol3 */
	private String tcol3;
	/** 属性flag */
	private String flag;	
	/**属性是否有损失经验*/
	private String yOrN;
	/**属性自負額*/
	private String excess;
    /** 承保比例 */
	private Double prpinsRate;
	
	public PrpTreinfo() {
	}
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "proposalNo", column = @Column(name = "PROPOSALNO")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpTreinfoId getId() {
		return this.id;
	}

	public void setId(PrpTreinfoId id) {
		this.id = id;
	}
	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROPOSALNO", nullable = false, insertable = false, updatable = false)
	public PrpTmain getPrpTmain() {
		return this.prpTmain;
	}

	public void setPrpTmain(PrpTmain prpTmain) {
		this.prpTmain = prpTmain;
	}
	

	@Column(name = "MANFENCENATURE")
	public String getManfenceNature() {
		return manfenceNature;
	}

	public void setManfenceNature(String manfenceNature) {
		this.manfenceNature = manfenceNature;
	}
	@Column(name = "MANFENCE")
	public String getManfence() {
		return manfence;
	}

	public void setManfence(String manfence) {
		this.manfence = manfence;
	}
	@Column(name = "HYDRANTNATURE")
	public String getHydrantNature() {
		return hydrantNature;
	}

	public void setHydrantNature(String hydrantNature) {
		this.hydrantNature = hydrantNature;
	}
	@Column(name = "HYDRANT")
	public String getHydrant() {
		return hydrant;
	}

	public void setHydrant(String hydrant) {
		this.hydrant = hydrant;
	}
	@Column(name = "LASTCBDATAFLAG")
	public String getLastcbdataFlag() {
		return lastcbdataFlag;
	}

	public void setLastcbdataFlag(String lastcbdataFlag) {
		this.lastcbdataFlag = lastcbdataFlag;
	}
	@Column(name = "LASTCBDATAFLAG1")
	public String getLastcbdataFlag1() {
		return lastcbdataFlag1;
	}
	
	public void setLastcbdataFlag1(String lastcbdataFlag1) {
		this.lastcbdataFlag1 = lastcbdataFlag1;
	}
	@Column(name = "LASTCBDATAFLAG2")
	public String getLastcbdataFlag2() {
		return lastcbdataFlag2;
	}

	public void setLastcbdataFlag2(String lastcbdataFlag2) {
		this.lastcbdataFlag2 = lastcbdataFlag2;
	}
	@Column(name = "LASTCBDATAFLAG3")
	public String getLastcbdataFlag3() {
		return lastcbdataFlag3;
	}

	public void setLastcbdataFlag3(String lastcbdataFlag3) {
		this.lastcbdataFlag3 = lastcbdataFlag3;
	}
	@Column(name = "EXPERIENCELOSS")
	public String getExperienceloss() {
		return experienceloss;
	}

	public void setExperienceloss(String experienceloss) {
		this.experienceloss = experienceloss;
	}
	@Column(name = "LOSSREASON")
	public String getLossReason() {
		return lossReason;
	}

	public void setLossReason(String lossReason) {
		this.lossReason = lossReason;
	}
	@Column(name = "LOSSSUM")
	public String getLossSum() {
		return lossSum;
	}

	public void setLossSum(String lossSum) {
		this.lossSum = lossSum;
	}
	@Column(name = "LOSSDATA")
	public String getLossData() {
		return lossData;
	}

	public void setLossData(String lossData) {
		this.lossData = lossData;
	}
	@Column(name = "LOSSMEASURE")
	public String getLossMeasure() {
		return lossMeasure;
	}

	public void setLossMeasure(String lossMeasure) {
		this.lossMeasure = lossMeasure;
	}

	@Column(name = "TCOL1")
	public String getTcol1() {
		return tcol1;
	}

	public void setTcol1(String tcol1) {
		this.tcol1 = tcol1;
	}
	@Column(name = "TCOL2")
	public String getTcol2() {
		return tcol2;
	}

	public void setTcol2(String tcol2) {
		this.tcol2 = tcol2;
	}
	@Column(name = "TCOL3")
	public String getTcol3() {
		return tcol3;
	}

	public void setTcol3(String tcol3) {
		this.tcol3 = tcol3;
	}

	@Column(name = "FLAG")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Column(name = "YORN")
	public String getyOrN() {
		return yOrN;
	}

	public void setyOrN(String yOrN) {
		this.yOrN = yOrN;
	}
	@Column(name = "EXCESS")
	public String getExcess() {
		return excess;
	}

	public void setExcess(String excess) {
		this.excess = excess;
	}
	@Column(name = "prpinsRate")
	public Double getPrpinsRate() {
		return prpinsRate;
	}

	public void setPrpinsRate(Double prpinsRate) {
		this.prpinsRate = prpinsRate;
	}
}
