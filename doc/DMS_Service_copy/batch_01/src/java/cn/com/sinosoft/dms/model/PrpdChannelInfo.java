package cn.com.sinosoft.dms.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PrpdChannelInfo")
public class PrpdChannelInfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 序号  */
	private PrpdChannelInfoId id;
	/** 方案代码 */
    private PrpDration prpDration;
    /** 个性方案中文名*/
    private String rationcName;
    /** 渠道一级编码*/
    private String channelCode;
    /** 渠道一级名称 */
    private String channelName;
    /** 渠道细类编码*/
    private String subChannelCode;
    /** 渠道细类名称 */
    private String subChannelName;
    /** 适用区域*/
    private String areaCode;
    /** 预留字段 1*/
    private String tcol1; 
    /** 预留字段 2*/
    private String tcol2;
    /** 预留字段 3*/
    private String tcol3;
    /** 标识字段*/
    private String flag;
	
    private List<PrpdChannelCoins> prpdChannelCoins =new ArrayList<PrpdChannelCoins>(0);
    private List<PrpDChannelRationEngage> prpDChannelRationEngage =new ArrayList<PrpDChannelRationEngage>(0);
     
    private List<PrpDChannelRationClauseKind> prpDChannelRationClauseKind =new ArrayList<PrpDChannelRationClauseKind>(0);
    
    public PrpdChannelInfo(){}


	/**       
	 * 序号
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "rationCode", column = @Column(name = "rationCode")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "channelInfoNo")) })
	public PrpdChannelInfoId getId() {
		return id;
	}
	public void setId(PrpdChannelInfoId id) {
		this.id = id;
	}
    /**
     * 方案代码
     */
    @ManyToOne()
    @JoinColumn(name="RATIONCODE", nullable=false, insertable=false, updatable=false)
    public PrpDration getPrpDration() {
        return this.prpDration;
    }
    
    public void setPrpDration(PrpDration prpDration) {
        this.prpDration = prpDration;
    }

	/** 个性方案中文名*/
	@Column(name="Rationcname")
	public String getRationcName() {
		return rationcName;
	}

	public void setRationcName(String rationcName) {
		this.rationcName = rationcName;
	}

	/** 渠道一级编码*/
	@Column(name="ChannelCode")
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	 /** 渠道一级名称 */
	@Column(name="ChannelName")
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/** 渠道细类编码*/
	@Column(name="SubChannelCode")
	public String getSubChannelCode() {
		return subChannelCode;
	}

	public void setSubChannelCode(String subChannelCode) {
		this.subChannelCode = subChannelCode;
	}

	 /** 渠道细类名称 */
	@Column(name="SubChannelName")
	public String getSubChannelName() {
		return subChannelName;
	}

	public void setSubChannelName(String subChannelName) {
		this.subChannelName = subChannelName;
	}

	/** 适用区域*/
	@Column(name="AreaCode")
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/** 预留字段 1*/
	@Column(name="Tcol1")
	public String getTcol1() {
		return tcol1;
	}

	public void setTcol1(String tcol1) {
		this.tcol1 = tcol1;
	}

	/** 预留字段 2*/
	@Column(name="Tcol2")
	public String getTcol2() {
		return tcol2;
	}

	public void setTcol2(String tcol2) {
		this.tcol2 = tcol2;
	}

	/** 预留字段 3*/
	@Column(name="Tcol3")
	public String getTcol3() {
		return tcol3;
	}

	public void setTcol3(String tcol3) {
		this.tcol3 = tcol3;
	}

	/** 标识字段*/
	@Column(name="Flag")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@OneToMany(cascade=CascadeType.ALL,  mappedBy="prpdChannelInfo")
	public List<PrpdChannelCoins> getPrpdChannelCoins() {
		return prpdChannelCoins;
	}


	public void setPrpdChannelCoins(List<PrpdChannelCoins> prpdChannelCoins) {
		this.prpdChannelCoins = prpdChannelCoins;
	}

	@OneToMany(cascade=CascadeType.ALL,  mappedBy="prpdChannelInfo")
	public List<PrpDChannelRationEngage> getPrpDChannelRationEngage() {
		return prpDChannelRationEngage;
	}


	public void setPrpDChannelRationEngage(
			List<PrpDChannelRationEngage> prpDChannelRationEngage) {
		this.prpDChannelRationEngage = prpDChannelRationEngage;
	}
	
	@OneToMany(cascade=CascadeType.ALL,  mappedBy="prpdChannelInfo")
	public List<PrpDChannelRationClauseKind> getPrpDChannelRationClauseKind() {
		return prpDChannelRationClauseKind;
	}

	public void setPrpDChannelRationClauseKind(
			List<PrpDChannelRationClauseKind> prpDChannelRationClauseKind) {
		this.prpDChannelRationClauseKind = prpDChannelRationClauseKind;
	}




	
}
