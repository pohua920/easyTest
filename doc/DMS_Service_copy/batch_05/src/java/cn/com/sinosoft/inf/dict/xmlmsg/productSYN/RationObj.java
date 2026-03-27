package cn.com.sinosoft.inf.dict.xmlmsg.productSYN;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDChannelRationClauseKind;
import cn.com.sinosoft.dms.model.PrpDChannelRationEngage;
import cn.com.sinosoft.dms.model.PrpDChannelRationPeriodRate;
import cn.com.sinosoft.dms.model.PrpDRationEngage;
import cn.com.sinosoft.dms.model.PrpDRationPeriodRate;
import cn.com.sinosoft.dms.model.PrpDration;
import cn.com.sinosoft.dms.model.PrpDrationClauseKind;
import cn.com.sinosoft.dms.model.PrpDrationCondition;
import cn.com.sinosoft.dms.model.PrpDrationLimit;
import cn.com.sinosoft.dms.model.PrpDrationShortrate;
import cn.com.sinosoft.dms.model.PrpdChannelCoins;
import cn.com.sinosoft.dms.model.PrpdChannelInfo;
import cn.com.sinosoft.dms.model.PrpDrationRelation;
import com.sinosoft.dmsdriver.domain.common.SchemaNode;
import com.sinosoft.dmsdriver.domain.getPlanInfo.TranslateObj;
import com.sinosoft.dmsdriver.model.PrpDarea;

public class RationObj implements SchemaNode {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private List<PrpDration> prpDration = new ArrayList<PrpDration>();
	private List<PrpDrationClauseKind> prpDrationClauseKind = new ArrayList<PrpDrationClauseKind>();
	private List<PrpDrationLimit> prpDrationLimit = new ArrayList<PrpDrationLimit>();
	private List<PrpDarea> prpDarea = new ArrayList<PrpDarea>();
	private List<PrpDrationShortrate> prpDrationShortrate =new ArrayList<PrpDrationShortrate>();
	private List<PrpDRationEngage> prpDrationEngage =new ArrayList<PrpDRationEngage>();
	private List<TranslateObj> translateObj = new ArrayList<TranslateObj>();
	
    private List<PrpdChannelInfo> prpdChannelInfo = new ArrayList<PrpdChannelInfo>();
    private List<PrpDRationPeriodRate> prpDRationPeriodRate = new ArrayList<PrpDRationPeriodRate>();
    private List<PrpdChannelCoins> prpdChannelCoins = new ArrayList<PrpdChannelCoins>();
    private List<PrpDChannelRationEngage> prpDChannelRationEngage = new ArrayList<PrpDChannelRationEngage>();
    private List<PrpDChannelRationClauseKind> prpDChannelRationClauseKind = new ArrayList<PrpDChannelRationClauseKind>();
    private List<PrpDChannelRationPeriodRate> prpDChannelRationPeriodRate = new ArrayList<PrpDChannelRationPeriodRate>();
    private List<PrpDrationRelation> prpDrationRelation = new ArrayList<PrpDrationRelation>();
    //add by fengyang 20140329 reason:承保条件设定信息
    private List<PrpDrationCondition> prpDrationCondition = new ArrayList<PrpDrationCondition>();
	public List<TranslateObj> getTranslateObj() {
		return translateObj;
	}

	public void setTranslateObj(List<TranslateObj> translateObj) {
		this.translateObj = translateObj;
	}

	public List<PrpDration> getPrpDration() {
		return prpDration;
	}

	public void setPrpDration(List<PrpDration> prpDration) {
		this.prpDration = prpDration;
	}

	public List<PrpDrationClauseKind> getPrpDrationClauseKind() {
		return prpDrationClauseKind;
	}

	public void setPrpDrationClauseKind(List<PrpDrationClauseKind> prpDrationClauseKind) {
		this.prpDrationClauseKind = prpDrationClauseKind;
	}

	public List<PrpDrationLimit> getPrpDrationLimit() {
		return prpDrationLimit;
	}

	public void setPrpDrationLimit(List<PrpDrationLimit> prpDrationLimit) {
		this.prpDrationLimit = prpDrationLimit;
	}

	public List<PrpDarea> getPrpDarea() {
		return prpDarea;
	}

	public void setPrpDarea(List<PrpDarea> prpDarea) {
		this.prpDarea = prpDarea;
	}

	public List<PrpDrationShortrate> getPrpDrationShortrate() {
		return prpDrationShortrate;
	}

	public void setPrpDrationShortrate(List<PrpDrationShortrate> prpDrationShortrate) {
		this.prpDrationShortrate = prpDrationShortrate;
	}

	public List<PrpDRationEngage> getPrpDrationEngage() {
		return prpDrationEngage;
	}

	public void setPrpDrationEngage(List<PrpDRationEngage> prpDrationEngage) {
		this.prpDrationEngage = prpDrationEngage;
	}

	public List<PrpdChannelInfo> getPrpdChannelInfo() {
		return prpdChannelInfo;
	}

	public void setPrpdChannelInfo(List<PrpdChannelInfo> prpdChannelInfo) {
		this.prpdChannelInfo = prpdChannelInfo;
	}

	public List<PrpDRationPeriodRate> getPrpDRationPeriodRate() {
		return prpDRationPeriodRate;
	}

	public void setPrpDRationPeriodRate(
			List<PrpDRationPeriodRate> prpDRationPeriodRate) {
		this.prpDRationPeriodRate = prpDRationPeriodRate;
	}

	public List<PrpdChannelCoins> getPrpdChannelCoins() {
		return prpdChannelCoins;
	}

	public void setPrpdChannelCoins(List<PrpdChannelCoins> prpdChannelCoins) {
		this.prpdChannelCoins = prpdChannelCoins;
	}

	public List<PrpDChannelRationEngage> getPrpDChannelRationEngage() {
		return prpDChannelRationEngage;
	}

	public void setPrpDChannelRationEngage(
			List<PrpDChannelRationEngage> prpDChannelRationEngage) {
		this.prpDChannelRationEngage = prpDChannelRationEngage;
	}

	public List<PrpDChannelRationClauseKind> getPrpDChannelRationClauseKind() {
		return prpDChannelRationClauseKind;
	}

	public void setPrpDChannelRationClauseKind(
			List<PrpDChannelRationClauseKind> prpDChannelRationClauseKind) {
		this.prpDChannelRationClauseKind = prpDChannelRationClauseKind;
	}

	public List<PrpDChannelRationPeriodRate> getPrpDChannelRationPeriodRate() {
		return prpDChannelRationPeriodRate;
	}

	public void setPrpDChannelRationPeriodRate(
			List<PrpDChannelRationPeriodRate> prpDChannelRationPeriodRate) {
		this.prpDChannelRationPeriodRate = prpDChannelRationPeriodRate;
	}

	public List<PrpDrationRelation> getPrpDrationRelation() {
		return prpDrationRelation;
	}

	public void setPrpDrationRelation(List<PrpDrationRelation> prpDrationRelation) {
		this.prpDrationRelation = prpDrationRelation;
	}

	public List<PrpDrationCondition> getPrpDrationCondition() {
		return prpDrationCondition;
	}

	public void setPrpDrationCondition(List<PrpDrationCondition> prpDrationCondition) {
		this.prpDrationCondition = prpDrationCondition;
	}

}
