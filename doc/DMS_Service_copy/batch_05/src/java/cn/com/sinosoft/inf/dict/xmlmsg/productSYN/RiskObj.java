package cn.com.sinosoft.inf.dict.xmlmsg.productSYN;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.dmsdriver.model.PrpDclass;

import cn.com.sinosoft.dms.model.PrpDRCKRateLower;
import cn.com.sinosoft.dms.model.PrpDaccountInfo;
import cn.com.sinosoft.dms.model.PrpDarea;
import cn.com.sinosoft.dms.model.PrpDnewCodeRisk;
import cn.com.sinosoft.dms.model.PrpDrisk;
import cn.com.sinosoft.dms.model.PrpDriskClause;
import cn.com.sinosoft.dms.model.PrpDriskClauseKind;
import cn.com.sinosoft.dms.model.PrpDriskClauseKindMinPremium;
import cn.com.sinosoft.dms.model.PrpDriskClauseKindRelation;
import cn.com.sinosoft.dms.model.PrpDriskEngage;
import cn.com.sinosoft.dms.model.PrpDriskItem;
import cn.com.sinosoft.dms.model.PrpDriskLimit;
import cn.com.sinosoft.dms.model.PrpDriskMinPremium;
import cn.com.sinosoft.dms.model.PrpDriskShortRate;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;



public class RiskObj implements SchemaNode{

	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private List<PrpDrisk> prpDrisk = new ArrayList<PrpDrisk>();
	private List<PrpDriskClause> prpDriskClause = new ArrayList<PrpDriskClause>();
	private List<PrpDriskClauseKind> prpDriskClauseKind = new ArrayList<PrpDriskClauseKind>();
	private List<PrpDriskShortRate> prpDriskShortRate = new ArrayList<PrpDriskShortRate>();
	private List<PrpDriskItem> prpDriskItem = new ArrayList<PrpDriskItem>();
	private List<PrpDriskLimit> prpDriskLimit = new ArrayList<PrpDriskLimit>();
	private List<PrpDriskEngage>prpDriskEngage = new ArrayList<PrpDriskEngage>();
	private List<PrpDriskClauseKindRelation> prpDriskClauseKindRelation = new ArrayList<PrpDriskClauseKindRelation>();
	private List<PrpDriskMinPremium> prpDriskMinPremium = new ArrayList<PrpDriskMinPremium>();
	private List<PrpDriskClauseKindMinPremium> prpDriskClauseKindMinPremium = new ArrayList<PrpDriskClauseKindMinPremium>();
	private List<PrpDaccountInfo> prpDaccountInfo = new ArrayList<PrpDaccountInfo>();
	private List<PrpDarea> prpDarea = new ArrayList<PrpDarea>();
	private List<PrpDnewCodeRisk> prpDnewCodeRisk = new ArrayList<PrpDnewCodeRisk>();
	private List<PrpDRCKRateLower> prpDRCKRateLower = new ArrayList<PrpDRCKRateLower>();
	private List<PrpDclass> prpDclass = new ArrayList<PrpDclass>();

	public List<PrpDrisk> getPrpDrisk() {
		return prpDrisk;
	}
	public void setPrpDrisk(List<PrpDrisk> prpDrisk) {
		this.prpDrisk = prpDrisk;
	}
	public List<PrpDriskClause> getPrpDriskClause() {
		return prpDriskClause;
	}
	public void setPrpDriskClause(List<PrpDriskClause> prpDriskClause) {
		this.prpDriskClause = prpDriskClause;
	}
	public List<PrpDriskClauseKind> getPrpDriskClauseKind() {
		return prpDriskClauseKind;
	}
	public void setPrpDriskClauseKind(List<PrpDriskClauseKind> prpDriskClauseKind) {
		this.prpDriskClauseKind = prpDriskClauseKind;
	}
	public List<PrpDriskShortRate> getPrpDriskShortRate() {
		return prpDriskShortRate;
	}
	public void setPrpDriskShortRate(List<PrpDriskShortRate> prpDriskShortRate) {
		this.prpDriskShortRate = prpDriskShortRate;
	}
	public List<PrpDriskItem> getPrpDriskItem() {
		return prpDriskItem;
	}
	public void setPrpDriskItem(List<PrpDriskItem> prpDriskItem) {
		this.prpDriskItem = prpDriskItem;
	}
	public List<PrpDriskLimit> getPrpDriskLimit() {
		return prpDriskLimit;
	}
	public void setPrpDriskLimit(List<PrpDriskLimit> prpDriskLimit) {
		this.prpDriskLimit = prpDriskLimit;
	}
	
	public List<PrpDriskEngage> getPrpDriskEngage() {
		return prpDriskEngage;
	}
	public void setPrpDriskEngage(List<PrpDriskEngage> prpDriskEngage) {
		this.prpDriskEngage = prpDriskEngage;
	}
	public List<PrpDriskClauseKindRelation> getPrpDriskClauseKindRelation() {
		return prpDriskClauseKindRelation;
	}
	public void setPrpDriskClauseKindRelation(
			List<PrpDriskClauseKindRelation> prpDriskClauseKindRelation) {
		this.prpDriskClauseKindRelation = prpDriskClauseKindRelation;
	}
	public List<PrpDriskMinPremium> getPrpDriskMinPremium() {
		return prpDriskMinPremium;
	}
	public void setPrpDriskMinPremium(List<PrpDriskMinPremium> prpDriskMinPremium) {
		this.prpDriskMinPremium = prpDriskMinPremium;
	}
	public List<PrpDriskClauseKindMinPremium> getPrpDriskClauseKindMinPremium() {
		return prpDriskClauseKindMinPremium;
	}
	public void setPrpDriskClauseKindMinPremium(
			List<PrpDriskClauseKindMinPremium> prpDriskClauseKindMinPremium) {
		this.prpDriskClauseKindMinPremium = prpDriskClauseKindMinPremium;
	}
	public List<PrpDaccountInfo> getPrpDaccountInfo() {
		return prpDaccountInfo;
	}
	public void setPrpDaccountInfo(List<PrpDaccountInfo> prpDaccountInfo) {
		this.prpDaccountInfo = prpDaccountInfo;
	}
	public List<PrpDarea> getPrpDarea() {
		return prpDarea;
	}
	public void setPrpDarea(List<PrpDarea> prpDarea) {
		this.prpDarea = prpDarea;
	}
	public List<PrpDnewCodeRisk> getPrpDnewCodeRisk() {
		return prpDnewCodeRisk;
	}
	public void setPrpDnewCodeRisk(List<PrpDnewCodeRisk> prpDnewCodeRisk) {
		this.prpDnewCodeRisk = prpDnewCodeRisk;
	}
	public List<PrpDRCKRateLower> getPrpDRCKRateLower() {
		return prpDRCKRateLower;
	}
	public void setPrpDRCKRateLower(List<PrpDRCKRateLower> prpDRCKRateLower) {
		this.prpDRCKRateLower = prpDRCKRateLower;
	}
	public List<PrpDclass> getPrpDclass() {
		return prpDclass;
	}
	public void setPrpDclass(List<PrpDclass> prpDclass) {
		this.prpDclass = prpDclass;
	}
	
	

}
