package com.sinosoft.claim.certainLoss.vo;

import java.io.Serializable;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpLcarLoss;
import com.sinosoft.claim.schema.model.PrpLclaimProgress;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLcomponent;
import com.sinosoft.claim.schema.model.PrpLperson;
import com.sinosoft.claim.schema.model.PrpLpersonWound;
import com.sinosoft.claim.schema.model.PrpLprop;
import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.schema.model.PrpLrepairFee;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.PrpLthirdProp;
import com.sinosoft.claim.schema.model.PrpLverifyLoss;
import com.sinosoft.claim.schema.model.PrpLverifyLossExt;
import com.sinosoft.claim.schema.model.PrpLverifyLossItem;
import com.sinosoft.claim.schema.model.PrpLcheckLoss;
import com.sinosoft.claim.schema.model.SwfNotion;

/**
 * 自定义定损数据传输对象
 * @ClassName CertainLossDto
 * @Description 
 * @author 中科软
 */

public class CertainLossDto implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 三者车辆信息 */
	private PrpLthirdParty prpLthirdParty; // add by liuyanmei 20051215
	/** 三者车辆信息 */
	private List<PrpLthirdParty> prpLthirdPartyList; // add by liuyanmei
														// 20051215
	/** 定损主表 */
	private PrpLverifyLoss prpLverifyLoss;
	/** 定损主表 */
	private List<PrpLverifyLoss> prpLverifyLossList;
	/** 定损车辆表 */
	private List<PrpLcarLoss> prpLcarLossList;
	/** 修理费用清单 */
	private List<PrpLrepairFee> prpLrepairFeeList;
	/** 换件项目清单 */
	private List<PrpLcomponent> prpLcomponentList;
	/** 人员伤亡明细信息表 */
	private List<PrpLperson> prpLpersonList;
	/** 财产核定损明细清单表 */
	private List<PrpLprop> prpLpropList;
	/** 财产查勘明细清单表 */
	private List<PrpLcheckLoss> prpLchecklossList;
	/** 财产查勘损失明细清单表 */
	private List<PrpLthirdProp> prpLthirdpropList;
	/** 定核损处理标的表 */
	private List<PrpLverifyLossItem> prpLverifyLossItemList;
	/** 伤情信息表 */
	private List<PrpLpersonWound> prpLpersonWoundList;
	/** 报案信息补充说明 */
	private List<PrpLregistExt> prpLregistExtList;
	/** 定核损信息补充说明 */
	private List<PrpLverifyLossExt> prpLverifyLossExtList;
	/** 处理意见表 */
	private List<SwfNotion> swfNotionList;

	private List<PrpLprop> prpLpropCarList;

	/** 操作状态信息 */
	private PrpLclaimStatus prpLclaimStatus;
	/** 理賠進度訊息  */
	private List<PrpLclaimProgress> claimProgressList;

	public PrpLthirdParty getPrpLthirdParty() {
		return prpLthirdParty;
	}

	public void setPrpLthirdParty(PrpLthirdParty prpLthirdParty) {
		this.prpLthirdParty = prpLthirdParty;
	}

	public List<PrpLthirdParty> getPrpLthirdPartyList() {
		return prpLthirdPartyList;
	}

	public void setPrpLthirdPartyList(List<PrpLthirdParty> prpLthirdPartyList) {
		this.prpLthirdPartyList = prpLthirdPartyList;
	}

	public PrpLverifyLoss getPrpLverifyLoss() {
		return prpLverifyLoss;
	}

	public void setPrpLverifyLoss(PrpLverifyLoss prpLverifyLoss) {
		this.prpLverifyLoss = prpLverifyLoss;
	}

	public List<PrpLverifyLoss> getPrpLverifyLossList() {
		return prpLverifyLossList;
	}

	public void setPrpLverifyLossList(List<PrpLverifyLoss> prpLverifyLossList) {
		this.prpLverifyLossList = prpLverifyLossList;
	}

	public List<PrpLcarLoss> getPrpLcarLossList() {
		return prpLcarLossList;
	}

	public void setPrpLcarLossList(List<PrpLcarLoss> prpLcarLossList) {
		this.prpLcarLossList = prpLcarLossList;
	}

	public List<PrpLrepairFee> getPrpLrepairFeeList() {
		return prpLrepairFeeList;
	}

	public void setPrpLrepairFeeList(List<PrpLrepairFee> prpLrepairFeeList) {
		this.prpLrepairFeeList = prpLrepairFeeList;
	}

	public List<PrpLcomponent> getPrpLcomponentList() {
		return prpLcomponentList;
	}

	public void setPrpLcomponentList(List<PrpLcomponent> prpLcomponentList) {
		this.prpLcomponentList = prpLcomponentList;
	}

	public List<PrpLperson> getPrpLpersonList() {
		return prpLpersonList;
	}

	public void setPrpLpersonList(List<PrpLperson> prpLpersonList) {
		this.prpLpersonList = prpLpersonList;
	}

	public List<PrpLprop> getPrpLpropList() {
		return prpLpropList;
	}

	public void setPrpLpropList(List<PrpLprop> prpLpropList) {
		this.prpLpropList = prpLpropList;
	}

	public List<PrpLcheckLoss> getPrpLchecklossList() {
		return prpLchecklossList;
	}

	public void setPrpLchecklossList(List<PrpLcheckLoss> prpLchecklossList) {
		this.prpLchecklossList = prpLchecklossList;
	}

	public List<PrpLthirdProp> getPrpLthirdpropList() {
		return prpLthirdpropList;
	}

	public void setPrpLthirdpropList(List<PrpLthirdProp> prpLthirdpropList) {
		this.prpLthirdpropList = prpLthirdpropList;
	}

	public List<PrpLverifyLossItem> getPrpLverifyLossItemList() {
		return prpLverifyLossItemList;
	}

	public void setPrpLverifyLossItemList(List<PrpLverifyLossItem> prpLverifyLossItemList) {
		this.prpLverifyLossItemList = prpLverifyLossItemList;
	}

	public List<PrpLpersonWound> getPrpLpersonWoundList() {
		return prpLpersonWoundList;
	}

	public void setPrpLpersonWoundList(List<PrpLpersonWound> prpLpersonWoundList) {
		this.prpLpersonWoundList = prpLpersonWoundList;
	}

	public List<PrpLregistExt> getPrpLregistExtList() {
		return prpLregistExtList;
	}

	public void setPrpLregistExtList(List<PrpLregistExt> prpLregistExtList) {
		this.prpLregistExtList = prpLregistExtList;
	}

	public List<PrpLverifyLossExt> getPrpLverifyLossExtList() {
		return prpLverifyLossExtList;
	}

	public void setPrpLverifyLossExtList(List<PrpLverifyLossExt> prpLverifyLossExtList) {
		this.prpLverifyLossExtList = prpLverifyLossExtList;
	}

	public List<SwfNotion> getSwfNotionList() {
		return swfNotionList;
	}

	public void setSwfNotionList(List<SwfNotion> swfNotionList) {
		this.swfNotionList = swfNotionList;
	}

	public List<PrpLprop> getPrpLpropCarList() {
		return prpLpropCarList;
	}

	public void setPrpLpropCarList(List<PrpLprop> prpLpropCarList) {
		this.prpLpropCarList = prpLpropCarList;
	}

	public PrpLclaimStatus getPrpLclaimStatus() {
		return prpLclaimStatus;
	}

	public void setPrpLclaimStatus(PrpLclaimStatus prpLclaimStatus) {
		this.prpLclaimStatus = prpLclaimStatus;
	}

	public List<PrpLclaimProgress> getClaimProgressList() {
		return claimProgressList;
	}

	public void setClaimProgressList(List<PrpLclaimProgress> claimProgressList) {
		this.claimProgressList = claimProgressList;
	}

}
