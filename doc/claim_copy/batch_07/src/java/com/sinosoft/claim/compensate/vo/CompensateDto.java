package com.sinosoft.claim.compensate.vo;

import java.io.Serializable;
import java.util.List;

import com.sinosoft.claim.certainLoss.vo.CertainLossDto;
import com.sinosoft.claim.schema.model.PrpCengage;
import com.sinosoft.claim.schema.model.PrpClimit;
import com.sinosoft.claim.schema.model.PrpDlimit;
import com.sinosoft.claim.schema.model.PrpLacciPerson;
import com.sinosoft.claim.schema.model.PrpLcarInsurance;
import com.sinosoft.claim.schema.model.PrpLcfee;
import com.sinosoft.claim.schema.model.PrpLcfeecoins;
import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLctext;
import com.sinosoft.claim.schema.model.PrpLdeductible;
import com.sinosoft.claim.schema.model.PrpLearthquakeFund;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLltext;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLpersonHospital;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.PrpLqualityCheck;
import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.reins.common.model.PrpLDangerItem;
import com.sinosoft.reins.common.model.PrpLDangerTot;
import com.sinosoft.reins.common.model.PrpLDangerUnit;

/**
 * 自定义实赔数据传输对象
 * <p>
 * Title: 车险理赔实赔
 * </p>
 * <p>
 * Description: 车险理赔实赔样本程序
 * </p>
 * <p>
 */
public class CompensateDto implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 赔款计算书主信息 */
	private PrpLcompensate prpLcompensate;
	/** 特别约定信息 */
	private List<PrpCengage> prpCengageList;
	/** 意健险索赔申请人信息 */
	private List<PrpLacciPerson> prpLacciPersonList;
	/** 赔付标的信息 */
	private List<PrpLloss> prpLlossList;
	/** 赔付人员信息 */
	private List<PrpLpersonLoss> prpLpersonLossList;
	/** 赔款费用信息 */
	private List<PrpLcharge> prpLchargeList;
	/** 理算报告 */
	private List<PrpLctext> prpLctextList;
	/** 立案信息 */
	private PrpLclaim prpLclaim;
	/** 限额免赔代码表 */
	private List<PrpDlimit> prpDlimitList;
	/** 赔款计算金额表 */
	private List<PrpLcfee> prpLcfeeList;
	/** 限额免赔代码表 */
	private List<PrpClimit> prpClimitList;
	/** 质量评审内容 */
	private List<PrpLqualityCheck> prpLqualityCheckList;
	/** 报案信息补充说明 */
	private List<PrpLregistExt> prpLregistExtList;
	/** 结案报告 */
	/** 理算免赔信息 add by qinyongli 2006-01-10 */
	private List<PrpLdeductible> prpLdeductibleList;
	/** 理算免赔信息 add by qinyongli 2006-01-10 */
	private List<PrpLltext> prpLltextList;
	/** 危险单位add by qinyongli 2005-8-19 */
	private List<PrpLDangerUnit> prplRiskUnitList;// ??prpLdangerUnitDto
	/** 理赔危险单位金额合计信息 add by qinyongli 2005-09-10 */
	private List<PrpLDangerTot> prpLprpLdangerTotList;// ??
	/** 理赔的危险单位信息表 add by qinyongli 2005-09-10 */
	private List<PrpLDangerItem> prpLprpLdangerItemList;// ??
	/** 联共保信息 */
	private List<PrpLcfeecoins> prpLcfeecoinsList;
	/** 操作状态信息 */
	private PrpLclaimStatus prpLclaimStatus;
	/** 支付对象信息 */
	private List<PrpLpayObjectInfo> prpLpayObjectInfoList;
	private List<PrpLpersonHospital>prpLpersonHospitalList;
	/**
	 * 地震基金
	 */
	private List<PrpLearthquakeFund> prpLearthquakeFundList; 
	/**
	 * 定损讯息
	 */
	private CertainLossDto certainLossDto = null;
	/** 车体险讯息 */
	private List<PrpLcarInsurance> prpLcarInsuranceList;

	/**
	 * 赔款计算书主信息
	 */
	public PrpLcompensate getPrpLcompensate() {
		return prpLcompensate;
	}

	public void setPrpLcompensate(PrpLcompensate prpLcompensate) {
		this.prpLcompensate = prpLcompensate;
	}

	/**
	 * 
	 */
	public List<PrpCengage> getPrpCengageList() {
		return prpCengageList;
	}

	public void setPrpCengageList(List<PrpCengage> prpCengageList) {
		this.prpCengageList = prpCengageList;
	}

	/**
	 * 
	 */
	public List<PrpLacciPerson> getPrpLacciPersonList() {
		return prpLacciPersonList;
	}

	public void setPrpLacciPersonList(List<PrpLacciPerson> prpLacciPersonList) {
		this.prpLacciPersonList = prpLacciPersonList;
	}

	/**
	 * 赔付标的信息
	 */
	public List<PrpLloss> getPrpLlossList() {
		return prpLlossList;
	}

	public void setPrpLlossList(List<PrpLloss> prpLlossList) {
		this.prpLlossList = prpLlossList;
	}

	/**
	 * 赔付人员信息
	 */
	public List<PrpLpersonLoss> getPrpLpersonLossList() {
		return prpLpersonLossList;
	}

	public void setPrpLpersonLossList(List<PrpLpersonLoss> prpLpersonLossList) {
		this.prpLpersonLossList = prpLpersonLossList;
	}

	/**
	 * 赔款费用信息
	 */
	public List<PrpLcharge> getPrpLchargeList() {
		return prpLchargeList;
	}

	public void setPrpLchargeList(List<PrpLcharge> prpLchargeList) {
		this.prpLchargeList = prpLchargeList;
	}

	/**
	 * 理算报告
	 */
	public List<PrpLctext> getPrpLctextList() {
		return prpLctextList;
	}

	public void setPrpLctextList(List<PrpLctext> prpLctextList) {
		this.prpLctextList = prpLctextList;
	}

	/**
	 * 立案信息
	 */
	public PrpLclaim getPrpLclaim() {
		return prpLclaim;
	}

	public void setPrpLclaim(PrpLclaim prpLclaim) {
		this.prpLclaim = prpLclaim;
	}

	/**
	 * 限额免赔代码表
	 */
	public List<PrpDlimit> getPrpDlimitList() {
		return prpDlimitList;
	}

	public void setPrpDlimitList(List<PrpDlimit> prpDlimitList) {
		this.prpDlimitList = prpDlimitList;
	}

	/**
	 * 赔款计算金额表
	 */
	public List<PrpLcfee> getPrpLcfeeList() {
		return prpLcfeeList;
	}

	public void setPrpLcfeeList(List<PrpLcfee> prpLcfeeList) {
		this.prpLcfeeList = prpLcfeeList;
	}

	/**
	 * 限额免赔代码表
	 */
	public List<PrpClimit> getPrpClimitList() {
		return prpClimitList;
	}

	public void setPrpClimitList(List<PrpClimit> prpClimitList) {
		this.prpClimitList = prpClimitList;
	}

	/**
	 * 质量评审内容
	 */
	public List<PrpLqualityCheck> getPrpLqualityCheckList() {
		return prpLqualityCheckList;
	}

	public void setPrpLqualityCheckList(List<PrpLqualityCheck> prpLqualityCheckList) {
		this.prpLqualityCheckList = prpLqualityCheckList;
	}

	/**
	 * 报案信息补充说明
	 */
	public List<PrpLregistExt> getPrpLregistExtList() {
		return prpLregistExtList;
	}

	public void setPrpLregistExtList(List<PrpLregistExt> prpLregistExtList) {
		this.prpLregistExtList = prpLregistExtList;
	}

	/**
	 * 结案报告
	 */
	public List<PrpLdeductible> getPrpLdeductibleList() {
		return prpLdeductibleList;
	}

	public void setPrpLdeductibleList(List<PrpLdeductible> prpLdeductibleList) {
		this.prpLdeductibleList = prpLdeductibleList;
	}

	/**
	 * 理算免赔信息
	 */
	public List<PrpLltext> getPrpLltextList() {
		return prpLltextList;
	}

	public void setPrpLltextList(List<PrpLltext> prpLltextList) {
		this.prpLltextList = prpLltextList;
	}

	/**
	 * 危险单位
	 */
	public List<PrpLDangerUnit> getPrplRiskUnitList() {
		return prplRiskUnitList;
	}

	public void setPrplRiskUnitList(List<PrpLDangerUnit> prplRiskUnitList) {
		this.prplRiskUnitList = prplRiskUnitList;
	}

	/**
	 * 理赔危险单位金额合计信息
	 */
	public List<PrpLDangerTot> getPrpLprpLdangerTotList() {
		return prpLprpLdangerTotList;
	}

	public void setPrpLprpLdangerTotList(List<PrpLDangerTot> prpLprpLdangerTotList) {
		this.prpLprpLdangerTotList = prpLprpLdangerTotList;
	}

	/**
	 * 理赔的危险单位信息
	 */
	public List<PrpLDangerItem> getPrpLprpLdangerItemList() {
		return prpLprpLdangerItemList;
	}

	public void setPrpLprpLdangerItemList(List<PrpLDangerItem> prpLprpLdangerItemList) {
		this.prpLprpLdangerItemList = prpLprpLdangerItemList;
	}

	/**
	 * 联共保信息
	 */
	public List<PrpLcfeecoins> getPrpLcfeecoinsList() {
		return prpLcfeecoinsList;
	}

	public void setPrpLcfeecoinsList(List<PrpLcfeecoins> prpLcfeecoinsList) {
		this.prpLcfeecoinsList = prpLcfeecoinsList;
	}

	/**
	 * 操作状态信息
	 */
	public PrpLclaimStatus getPrpLclaimStatus() {
		return prpLclaimStatus;
	}

	public void setPrpLclaimStatus(PrpLclaimStatus prpLclaimStatus) {
		this.prpLclaimStatus = prpLclaimStatus;
	}

	public List<PrpLpayObjectInfo> getPrpLpayObjectInfoList() {
		return prpLpayObjectInfoList;
	}

	public void setPrpLpayObjectInfoList(List<PrpLpayObjectInfo> prpLpayObjectInfoList) {
		this.prpLpayObjectInfoList = prpLpayObjectInfoList;
	}

	public List<PrpLpersonHospital> getPrpLpersonHospitalList() {
		return prpLpersonHospitalList;
	}

	public void setPrpLpersonHospitalList(List<PrpLpersonHospital> prpLpersonHospitalList) {
		this.prpLpersonHospitalList = prpLpersonHospitalList;
	}

	public List<PrpLearthquakeFund> getPrpLearthquakeFundList() {
		return prpLearthquakeFundList;
	}

	public void setPrpLearthquakeFundList(List<PrpLearthquakeFund> prpLearthquakeFundList) {
		this.prpLearthquakeFundList = prpLearthquakeFundList;
	}

	public CertainLossDto getCertainLossDto() {
		return certainLossDto;
	}

	public void setCertainLossDto(CertainLossDto certainLossDto) {
		this.certainLossDto = certainLossDto;
	}

	public List<PrpLcarInsurance> getPrpLcarInsuranceList() {
		return prpLcarInsuranceList;
	}

	public void setPrpLcarInsuranceList(List<PrpLcarInsurance> prpLcarInsuranceList) {
		this.prpLcarInsuranceList = prpLcarInsuranceList;
	}
	
}
