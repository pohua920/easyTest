package com.sinosoft.claim.verifyLoss.vo;

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
import com.sinosoft.claim.schema.model.PrpLverifyLoss;
import com.sinosoft.claim.schema.model.PrpLverifyLossExt;
import com.sinosoft.claim.schema.model.PrpLverifyLossItem;

/**
 * @ClassName VerifyLossDto 自定义核损数据传输对象
 *            <p>
 *            Title: 车险理赔核损DTO
 *            </p>
 *            <p>
 *            Description: 车险理赔核损样本程序
 *            </p>
 * @author 中科软
 */
public class VerifyLossDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 核损主表信息 */
	private PrpLverifyLoss prpLverifyLoss;
	/** 核损车辆表信息 */
	private List<PrpLcarLoss> prpLcarLossList;
	/** 修理费用清单信息 */
	private List<PrpLrepairFee> prpLrepairFeeList;
	/** 换件项目清单信息 */
	private List<PrpLcomponent> prpLcomponentList;
	/** 人员伤亡明细信息表信息 */
	private List<PrpLperson> prpLpersonList;
	/** 财产核核损明细清单表信息 */
	private List<PrpLprop> prpLpropList;
	/** 定核损处理标的表 */
	private List<PrpLverifyLossItem> prpLverifyLossItemList;
	/** 伤情信息表 */
	private List<PrpLpersonWound> prpLpersonWoundList;
	/** 定核损信息补充说明 */
	private List<PrpLverifyLossExt> prpLverifyLossExtList;
	/** 报案信息补充说明 */
	private List<PrpLregistExt> prpLregistExtList;
	/** 操作状态信息 */
	private PrpLclaimStatus prpLclaimStatus;
	/** 理賠進度訊息  */
	private List<PrpLclaimProgress> claimProgressList;
	
	public PrpLverifyLoss getPrpLverifyLoss() {
		return prpLverifyLoss;
	}

	public void setPrpLverifyLoss(PrpLverifyLoss prpLverifyLoss) {
		this.prpLverifyLoss = prpLverifyLoss;
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

	public List<PrpLverifyLossExt> getPrpLverifyLossExtList() {
		return prpLverifyLossExtList;
	}

	public void setPrpLverifyLossExtList(List<PrpLverifyLossExt> prpLverifyLossExtList) {
		this.prpLverifyLossExtList = prpLverifyLossExtList;
	}

	public List<PrpLregistExt> getPrpLregistExtList() {
		return prpLregistExtList;
	}

	public void setPrpLregistExtList(List<PrpLregistExt> prpLregistExtList) {
		this.prpLregistExtList = prpLregistExtList;
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
