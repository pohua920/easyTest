package cn.com.sinosoft.dms.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;


@Entity
@Table(name="PRPDCHANNELCOINS")
public class PrpdChannelCoins implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 序号 */
	private PrpdChannelCoinsId id;
	/** 个性信息编号 */
	 private PrpdChannelInfo prpdChannelInfo;  
	/** 批次 *//*
	private BigDecimal period;
	*//** 共保体单位代码 *//*
	private String coinsComCode;
	*//** 共保体单位名称 *//*
	private String coinsComName;
	*//** 共保身份 *//*
	private String coinsType;*/
	/** 共保比例 */
	private BigDecimal coinsRate;
	/** 标志字段 */
	private String flag;
	/** 插入时间 *//*
	private Date insertTimeForHis;
	*//** 修改时间 *//*
	private Date operateTimeForHis;
	*//** 编号 */
	
	private String coidenTity;
	private String coinsCode;
	private String coinsName;
	//private String coinsRate;
	private String handler1Code;
	private String handler1Name;
	private String comCode;
	private String comName;

	
	
	
	
	public PrpdChannelCoins(){}
	
	/** 序号 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "serialNo", column = @Column(name = "serialNo")),
			@AttributeOverride(name = "channelInfoNo", column = @Column(name = "CHANNELINFONO")),
			@AttributeOverride(name = "ratioinCode", column = @Column(name = "rationCode")) })
	public PrpdChannelCoinsId getId() {
		return id;
	}

	public void setId(PrpdChannelCoinsId id) {
		this.id = id;
	}

	/**       
	 * 个性信息编号
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns( {
			@JoinColumn(name = "channelInfoNo", referencedColumnName="channelInfoNo", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "rationCode", referencedColumnName="rationCode", nullable = false, insertable = false, updatable = false) })
	public PrpdChannelInfo getPrpdChannelInfo() {
		return this.prpdChannelInfo;
	}
	public void setPrpdChannelInfo(PrpdChannelInfo prpdChannelInfo) {
		this.prpdChannelInfo = prpdChannelInfo;
	}

	/**       
	 * 批次
	 *//*

	@Column(name = "period")
	public BigDecimal getPeriod() {
		return period;
	}

	public void setPeriod(BigDecimal period) {
		this.period = period;
	}
	
	*//**       
	 * 共保体单位代码
	 *//*

	@Column(name = "coinscomcode")
	public String getCoinsComCode() {
		return coinsComCode;
	}
	
	public void setCoinsComCode(String coinsComCode) {
		this.coinsComCode = coinsComCode;
	}
	
	*//**       
	 * 共保体单位名称
	 *//*

	@Column(name = "coinscomname")
	public String getCoinsComName() {
		return coinsComName;
	}
	
	public void setCoinsComName(String coinsComName) {
		this.coinsComName = coinsComName;
	}
	
	*//**       
	 * 共保身份
	 *//*

	@Column(name = "coinstype")
	public String getCoinsType() {
		return coinsType;
	}
	
	public void setCoinsType(String coinsType) {
		this.coinsType = coinsType;
	}
	*/
	/**       
	 * 共保比例
	 */

	@Column(name = "coinsrate")
	public BigDecimal getCoinsRate() {
		return coinsRate;
	}
	
	public void setCoinsRate(BigDecimal coinsRate) {
		this.coinsRate = coinsRate;
	}
	
	/**       
	 * 标志字段
	 */

	@Column(name = "flag")
	public String getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	/**       
	 * 插入时间
	 */
/*
	@Column(name = "inserttimeforhis", insertable = false, updatable = false)
	public Date getInsertTimeForHis() {
		return this.insertTimeForHis;
	}

	public void setInsertTimeForHis(Date insertTimeForHis) {
		this.insertTimeForHis = insertTimeForHis;
	}
	
	*//**       
	 * 更新时间
	 *//*

	@Column(name = "operatetimeforhis", insertable = false)
	public Date getOperateTimeForHis() {
		return this.operateTimeForHis;
	}

	public void setOperateTimeForHis(Date operateTimeForHis) {
		this.operateTimeForHis = operateTimeForHis;
	}*/

	@Column(name = "COIDENTITY")
	public String getCoidenTity() {
		return coidenTity;
	}

	public void setCoidenTity(String coidenTity) {
		this.coidenTity = coidenTity;
	}

	@Column(name = "COINSCODE")
	public String getCoinsCode() {
		return coinsCode;
	}

	public void setCoinsCode(String coinsCode) {
		this.coinsCode = coinsCode;
	}

	@Column(name = "COINSNAME")
	public String getCoinsName() {
		return coinsName;
	}

	public void setCoinsName(String coinsName) {
		this.coinsName = coinsName;
	}

	@Column(name = "HANDLER1CODE")
	public String getHandler1Code() {
		return handler1Code;
	}

	public void setHandler1Code(String handler1Code) {
		this.handler1Code = handler1Code;
	}

	@Column(name = "HANDLER1NAME")
	public String getHandler1Name() {
		return handler1Name;
	}

	public void setHandler1Name(String handler1Name) {
		this.handler1Name = handler1Name;
	}

	@Column(name = "COMECODE")
	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	@Column(name = "COMENAME")
	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	

}
