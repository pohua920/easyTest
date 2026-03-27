package com.sinosoft.reins.interf.service.spring;

import com.sinosoft.reins.base.model.FpoReendor;
import com.sinosoft.reins.base.service.facade.FpoReendorService;
import com.sinosoft.reins.common.model.PrpCDangerItem;
import com.sinosoft.reins.common.model.PrpCDangerRisk;
import com.sinosoft.reins.common.model.PrpCDangerUnit;
import com.sinosoft.reins.common.model.PrpCDangerUnitId;
import com.sinosoft.reins.common.model.PrpPDangerItem;
import com.sinosoft.reins.common.model.PrpPDangerRisk;
import com.sinosoft.reins.common.model.PrpPDangerUnit;
import com.sinosoft.reins.common.model.PrpPDangerUnitId;
import com.sinosoft.reins.common.model.PrpTDangerRisk;
import com.sinosoft.reins.common.service.facade.BLCDangerGetService;
import com.sinosoft.reins.common.service.facade.BLPDangerGetService;
import com.sinosoft.reins.common.service.facade.PrpCDangerItemService;
import com.sinosoft.reins.common.service.facade.PrpCDangerRiskService;
import com.sinosoft.reins.common.service.facade.PrpCDangerUnitService;
import com.sinosoft.reins.common.service.facade.PrpPDangerItemService;
import com.sinosoft.reins.common.service.facade.PrpPDangerRiskService;
import com.sinosoft.reins.common.service.facade.PrpPDangerTotService;
import com.sinosoft.reins.common.service.facade.PrpPDangerUnitService;
import com.sinosoft.reins.common.service.facade.PrpTDangerRiskService;
import com.sinosoft.reins.common.vo.PrpCDangerRiskVO;
import com.sinosoft.reins.common.vo.PrpCDangerUnitVO;
import com.sinosoft.reins.common.vo.PrpPDangerItemVO;
import com.sinosoft.reins.common.vo.PrpPDangerRiskVO;
import com.sinosoft.reins.common.vo.PrpPDangerUnitVO;
import com.sinosoft.reins.common.vo.PrpTDangerRiskVO;
import com.sinosoft.reins.common.vo.PrpTDangerUnitVO;
import com.sinosoft.reins.interf.model.CDanger;
import com.sinosoft.reins.interf.model.PDanger;
import com.sinosoft.reins.interf.service.facade.BLDangerService;
import com.sinosoft.reins.interf.service.facade.BLTDangerGetService;
import com.sinosoft.reins.interf.vo.CDangerVO;
import com.sinosoft.reins.interf.vo.PDangerVO;
import com.sinosoft.reins.interf.vo.TDangerVO;
import com.sinosoft.sysframework.common.datatype.DateTime;
import ins.framework.common.QueryRule;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class BLDangerServiceSpringImpl implements BLDangerService {
	private FpoReendorService fpoReendorService;
	private PrpPDangerUnitService prpPDangerUnitService;
	private PrpPDangerItemService prpPDangerItemService;
	private PrpPDangerTotService prpPDangerTotService;
	private PrpCDangerUnitService prpCDangerUnitService;
	private PrpCDangerItemService prpCDangerItemService;
	private BLCDangerGetService blCDangerGetService;
	private PrpTDangerRiskService prpTDangerRiskService;
	private PrpCDangerRiskService prpCDangerRiskService;
	private PrpPDangerRiskService prpPDangerRiskService;
	private BLTDangerGetService blTDangerGetService;
	private BLPDangerGetService blPDangerGetService;

	public Collection getCDangerListForClaim(String policyNo, DateTime damageDate) throws Exception {
		String endorseNo = "";
		PrpPDangerUnit prpPDangerUnit = null;
		Collection prpPdangerItemList = null;
		Collection prpCdangerItemList = null;
		Collection cDangerList = new ArrayList();
		endorseNo = getRecentlyEndorseNo(policyNo, 1, damageDate);
		if (!(endorseNo.equals(""))) {
			Collection pDangerList = getPDangerListForClaim(endorseNo);
			if (pDangerList.size() > 0) {
				Iterator itp = pDangerList.iterator();
				while (itp.hasNext()) {
					PDangerVO pDanger = (PDangerVO) itp.next();
					prpPDangerUnit = pDanger.getPrpPDangerUnitVO().voToPoJo();
					prpCdangerItemList = new ArrayList();
					PrpCDangerUnit prpCdangerUnit = new PrpCDangerUnit();
					PrpCDangerUnitId prpCDangerUnitId = new PrpCDangerUnitId();
					prpCdangerUnit.setId(prpCDangerUnitId);
					prpCDangerUnitId.setDangerNo(prpPDangerUnit.getId().getDangerNo());
					prpCdangerUnit.setDangerDesc(prpPDangerUnit.getDangerDesc());
					prpCdangerUnit.setAddressName(prpPDangerUnit.getAddressName());
					prpCdangerUnit.setAmount(Double.valueOf(prpPDangerUnit.getAmount().doubleValue() + prpPDangerUnit.getChgAmount().doubleValue()));
					prpCdangerUnit.setPremium(Double.valueOf(prpPDangerUnit.getPremium().doubleValue() + prpPDangerUnit.getChgPremium().doubleValue() - prpPDangerUnit.getDisFee().doubleValue() - prpPDangerUnit.getChgDisFee().doubleValue()));
					prpCdangerUnit.setDangerShare(prpPDangerUnit.getDangerShare());
					prpCdangerUnit.setSameRiskNo(prpPDangerUnit.getSameRiskNo());
					CDanger cDanger = new CDanger();
					cDanger.setPrpCDangerUnit(prpCdangerUnit);
					cDanger.setPrpCDangerItemList(prpCdangerItemList);
					cDangerList.add(cDanger);
				}
			}
		} else {
			cDangerList = getCDangerList(policyNo);
		}

		return cDangerList;
	}

	public Collection getCDangerListForClaim(String policyNo) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		Collection cDangerDtoList = new ArrayList();
		Collection prpCdangerUnitList = this.prpCDangerUnitService.findByConditions(queryRule);
		if ((prpCdangerUnitList != null) && (prpCdangerUnitList.size() > 0)) {
			Iterator iterator = prpCdangerUnitList.iterator();
			while (iterator.hasNext()) {
				PrpCDangerUnit prpCdangerUnitDto = (PrpCDangerUnit) iterator.next();
				int dangerNo = prpCdangerUnitDto.getId().getDangerNo().intValue();
				CDanger cDanger = getCDangerInfoForClaim(policyNo, dangerNo);
				cDangerDtoList.add(cDanger);
			}
		}
		return cDangerDtoList;
	}

	public CDanger getCDangerInfoForClaim(String policyNo, int dangerNo) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo).addEqual("id.dangerNo", Integer.valueOf(dangerNo));
		PrpCDangerUnitId prpCDangerUnitId = new PrpCDangerUnitId();
		prpCDangerUnitId.setPolicyNo(policyNo);
		prpCDangerUnitId.setDangerNo(Integer.valueOf(dangerNo));
		CDanger cDanger = new CDanger();
		cDanger.setPrpCDangerUnit(this.prpCDangerUnitService.findByConditions(prpCDangerUnitId));
		return cDanger;
	}

	public Collection getPDangerListForClaim(String endorseNo) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.endorseNo", endorseNo);
		Collection pDangerDtoList = new ArrayList();
		Collection prpPdangerUnitList = this.prpPDangerUnitService.findByConditions(queryRule);
		if ((prpPdangerUnitList != null) && (prpPdangerUnitList.size() > 0)) {
			Iterator iterator = prpPdangerUnitList.iterator();
			while (iterator.hasNext()) {
				PrpPDangerUnit prpPDangerUnit = (PrpPDangerUnit) iterator.next();
				int dangerNo = prpPDangerUnit.getId().getDangerNo().intValue();
				PDangerVO pDanger = getPDangerInfoForClaim(endorseNo, dangerNo);
				pDangerDtoList.add(pDanger);
			}
		}
		return pDangerDtoList;
	}

	public PDangerVO getPDangerInfoForClaim(String endorseNo, int dangerNo) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.endorseNo", endorseNo).addEqual("id.dangerNo", Integer.valueOf(dangerNo));
		PrpPDangerUnitId prpPDangerUnitId = new PrpPDangerUnitId();
		prpPDangerUnitId.setEndorseNo(endorseNo);
		prpPDangerUnitId.setDangerNo(Integer.valueOf(dangerNo));
		PDanger pDanger = new PDanger();
		pDanger.setPrpPDangerUnit(this.prpPDangerUnitService.findByConditions(prpPDangerUnitId));
		// 為避免傷害險查此數據，改造移除（TA大保單被保險人多時，該數據量較大，影響效率）。
		// pDanger.setPrpPDangerItemList(this.prpPDangerItemService.findByConditions(queryRule));
		pDanger.setPrpPDangerTotList(this.prpPDangerTotService.findByConditions(queryRule));
		return new PDangerVO(pDanger);
	}

	public Collection getCDangerList(String policyNo, DateTime damageDate) throws Exception {
		String endorseNo = "";

		PrpPDangerUnit prpPDangerUnit = null;
		Collection prpPdangerItemList = null;
		Collection prpCdangerItemList = null;
		Collection cDangerList = new ArrayList();
		endorseNo = getRecentlyEndorseNo(policyNo, 1, damageDate);

		if (!(endorseNo.equals(""))) {
			Collection pDangerList = getPDangerList(endorseNo);
			if (pDangerList.size() > 0) {
				Iterator itp = pDangerList.iterator();
				while (itp.hasNext()) {
					PDangerVO pDanger = (PDangerVO) itp.next();
					prpPDangerUnit = pDanger.getPrpPDangerUnitVO().voToPoJo();
					prpPdangerItemList = pDanger.getPrpPDangerItemVOList();
					if (prpPdangerItemList.size() != 0) {
						Iterator itItem = prpPdangerItemList.iterator();
						prpCdangerItemList = new ArrayList();
						while (itItem.hasNext()) {
							PrpPDangerItem prpPdangerItem = ((PrpPDangerItemVO) itItem.next()).voToPoJo();
							PrpCDangerItem prpCdangerItem = new PrpCDangerItem();
							prpCdangerItem.setAddressName(prpPdangerItem.getAddressName());
							prpCdangerItem.setAmount(Double.valueOf(prpPdangerItem.getAmount().doubleValue() + prpPdangerItem.getChgAmount().doubleValue()));
							prpCdangerItem.setCalculateFlag(prpPdangerItem.getCalculateFlag());
							prpCdangerItem.setCurrency(prpPdangerItem.getCurrency());
							prpCdangerItem.setFlag(prpPdangerItem.getFlag());

							prpCdangerItem.setItemCode(prpPdangerItem.getItemCode());
							prpCdangerItem.setItemDetailName(prpPdangerItem.getItemDetailName());
							prpCdangerItem.setKindCode(prpPdangerItem.getKindCode());
							prpCdangerItem.setKindFlag(prpPdangerItem.getKindFlag());
							prpCdangerItem.setKindName(prpPdangerItem.getKindName());
							prpCdangerItem.setPostCode(prpPdangerItem.getPostCode());
							prpCdangerItem.setPremium(Double.valueOf(prpPdangerItem.getPremium().doubleValue() + prpPdangerItem.getChgPremium().doubleValue()));
							prpCdangerItem.setReTCurrency(prpPdangerItem.getReTCurrency());
							prpCdangerItem.setRetentionValue(prpPdangerItem.getRetentionValue());
							prpCdangerItem.setRiskClass(prpPdangerItem.getRiskClass());
							prpCdangerItem.setRiskClassDesc(prpPdangerItem.getRiskClassDesc());
							prpCdangerItem.setRiskCode(prpPdangerItem.getRiskCode());
							prpCdangerItem.setRiskLevel(prpPdangerItem.getRiskLevel());
							prpCdangerItem.setRiskLevelDesc(prpPdangerItem.getRiskLevelDesc());
							prpCdangerItemList.add(prpCdangerItem);
						}
					}
					PrpCDangerUnit prpCdangerUnit = new PrpCDangerUnit();
					PrpCDangerUnitId prpCDangerUnitId = new PrpCDangerUnitId();
					prpCdangerUnit.setId(prpCDangerUnitId);
					prpCDangerUnitId.setDangerNo(prpPDangerUnit.getId().getDangerNo());
					prpCdangerUnit.setDangerDesc(prpPDangerUnit.getDangerDesc());
					prpCdangerUnit.setAddressName(prpPDangerUnit.getAddressName());
					prpCdangerUnit.setAmount(Double.valueOf(prpPDangerUnit.getAmount().doubleValue() + prpPDangerUnit.getChgAmount().doubleValue()));
					prpCdangerUnit.setPremium(Double.valueOf(prpPDangerUnit.getPremium().doubleValue() + prpPDangerUnit.getChgPremium().doubleValue() - prpPDangerUnit.getDisFee().doubleValue() - prpPDangerUnit.getChgDisFee().doubleValue()));
					prpCdangerUnit.setDangerShare(prpPDangerUnit.getDangerShare());
					prpCdangerUnit.setSameRiskNo(prpPDangerUnit.getSameRiskNo());
					CDanger cDanger = new CDanger();
					cDanger.setPrpCDangerUnit(prpCdangerUnit);
					cDanger.setPrpCDangerItemList(prpCdangerItemList);
					cDangerList.add(cDanger);
				}
			}
		} else {
			cDangerList = getCDangerListForClaim(policyNo);
		}

		return cDangerList;
	}

	public Collection getCDangerList(String policyNo) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		Collection cDangerDtoList = new ArrayList();

		Collection prpCdangerUnitList = this.prpCDangerUnitService.findByConditions(queryRule);
		if ((prpCdangerUnitList != null) && (prpCdangerUnitList.size() > 0)) {
			Iterator iterator = prpCdangerUnitList.iterator();
			while (iterator.hasNext()) {
				PrpCDangerUnit prpCdangerUnitDto = (PrpCDangerUnit) iterator.next();
				int dangerNo = prpCdangerUnitDto.getId().getDangerNo().intValue();
				CDanger cDanger = getCDangerInfo(policyNo, dangerNo);
				cDangerDtoList.add(cDanger);
			}
		}
		return cDangerDtoList;
	}

	public CDanger getCDangerInfo(String policyNo, int dangerNo) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo).addEqual("id.dangerNo", Integer.valueOf(dangerNo));
		PrpCDangerUnitId prpCDangerUnitId = new PrpCDangerUnitId();
		prpCDangerUnitId.setPolicyNo(policyNo);
		prpCDangerUnitId.setDangerNo(Integer.valueOf(dangerNo));
		CDanger cDanger = new CDanger();

		cDanger.setPrpCDangerUnit(this.prpCDangerUnitService.findByConditions(prpCDangerUnitId));
		cDanger.setPrpCDangerItemList(this.prpCDangerItemService.findByConditions(queryRule));

		return cDanger;
	}

	public Collection getPDangerList(String endorseNo) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.endorseNo", endorseNo);
		Collection pDangerDtoList = new ArrayList();
		Collection prpPdangerUnitList = this.prpPDangerUnitService.findByConditions(queryRule);
		if ((prpPdangerUnitList != null) && (prpPdangerUnitList.size() > 0)) {
			Iterator iterator = prpPdangerUnitList.iterator();
			while (iterator.hasNext()) {
				PrpPDangerUnit prpPDangerUnit = (PrpPDangerUnit) iterator.next();
				int dangerNo = prpPDangerUnit.getId().getDangerNo().intValue();
				PDangerVO pDanger = getPDangerInfo(endorseNo, dangerNo);
				pDangerDtoList.add(pDanger);
			}
		}
		return pDangerDtoList;
	}

	public String getRecentlyEndorseNo(String policyNo, int dangerNo, DateTime damageDate) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		String conditions = "";
		Collection fpoReendorDtoList = null;
		FpoReendor fpoReendor = null;
		String endorseNo = "";

		queryRule.addEqual("policyNo", policyNo).addEqual("dangerNo", Integer.valueOf(dangerNo));
		queryRule.addLessEqual("endorDate", damageDate).addDescOrder("endorNo");
		fpoReendorDtoList = this.fpoReendorService.findByConditions(queryRule);
		if ((fpoReendorDtoList != null) && (fpoReendorDtoList.size() > 0)) {
			Iterator iter = fpoReendorDtoList.iterator();
			if (iter.hasNext()) {
				fpoReendor = (FpoReendor) iter.next();
				endorseNo = fpoReendor.getEndorNo();
			}
		}

		return endorseNo;
	}

	public PDangerVO getPDangerInfo(String endorseNo, int dangerNo) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.endorseNo", endorseNo).addEqual("id.dangerNo", Integer.valueOf(dangerNo));
		PrpPDangerUnitId prpPDangerUnitId = new PrpPDangerUnitId();
		prpPDangerUnitId.setEndorseNo(endorseNo);
		prpPDangerUnitId.setDangerNo(Integer.valueOf(dangerNo));
		PDanger pDanger = new PDanger();

		pDanger.setPrpPDangerUnit(this.prpPDangerUnitService.findByConditions(prpPDangerUnitId));
		pDanger.setPrpPDangerItemList(this.prpPDangerItemService.findByConditions(queryRule));
		pDanger.setPrpPDangerTotList(this.prpPDangerTotService.findByConditions(queryRule));

		return new PDangerVO(pDanger);
	}

	public void saveCDangerUnit(CDangerVO cDangerVO) throws Exception {
		PrpCDangerUnitVO prpCDangerUnitVO = null;
		prpCDangerUnitVO = cDangerVO.getPrpCDangerUnitVO();
		this.blCDangerGetService.delete(prpCDangerUnitVO.getPolicyNo(), prpCDangerUnitVO.getDangerNo());
		this.blCDangerGetService.save(cDangerVO.voToPoJo());
	}

	public Collection getDangerRiskList(String businessType, String businessNo, String dangerNo) throws Exception {
		Collection prpDangerRiskDtoList = new ArrayList();
		QueryRule queryRule = QueryRule.getInstance();
		if (businessType.equals("T")) {
			queryRule.addSql(" proposalNo = '" + businessNo + "' And dangerNo = " + dangerNo);
			prpDangerRiskDtoList = this.prpTDangerRiskService.findByConditions(queryRule);
		} else if (businessType.equals("P")) {
			queryRule.addSql(" policyNo = '" + businessNo + "' And dangerNo = " + dangerNo);
			prpDangerRiskDtoList = this.prpCDangerRiskService.findByConditions(queryRule);
		} else if (businessType.equals("E")) {
			queryRule.addSql(" endorseNo = '" + businessNo + "' And dangerNo = " + dangerNo);
			prpDangerRiskDtoList = this.prpPDangerRiskService.findByConditions(queryRule);
		}
		return getDangerRiskListToVOList(prpDangerRiskDtoList, businessType);
	}

	private Collection getDangerRiskListToVOList(Collection prpDangerRiskDtoList, String businessType) {
		Collection prpDangerRiskVOList = new ArrayList();
		Iterator it;
		if (businessType.equals("T")) {
			for (it = prpDangerRiskDtoList.iterator(); it.hasNext();)
				prpDangerRiskVOList.add(new PrpTDangerRiskVO((PrpTDangerRisk) it.next()));
		} else if (businessType.equals("P")) {
			for (it = prpDangerRiskDtoList.iterator(); it.hasNext();)
				prpDangerRiskVOList.add(new PrpCDangerRiskVO((PrpCDangerRisk) it.next()));
		} else if (businessType.equals("E")) {
			for (it = prpDangerRiskDtoList.iterator(); it.hasNext();) {
				prpDangerRiskVOList.add(new PrpPDangerRiskVO((PrpPDangerRisk) it.next()));
			}
		}
		return prpDangerRiskVOList;
	}

	public void saveTDangerUnit(TDangerVO tDanger) throws Exception {
		PrpTDangerUnitVO prpTDangerUnitVO = null;
		prpTDangerUnitVO = tDanger.getPrpTDangerUnitVO();
		this.blTDangerGetService.delete(prpTDangerUnitVO.getProposalNo(), prpTDangerUnitVO.getDangerNo());
		this.blTDangerGetService.save(tDanger);
	}

	public void savePDangerUnit(PDangerVO pDanger) throws Exception {
		PrpPDangerUnitVO prpPDangerUnitVO = null;
		prpPDangerUnitVO = pDanger.getPrpPDangerUnitVO();
		this.blPDangerGetService.delete(prpPDangerUnitVO.getEndorseNo(), prpPDangerUnitVO.getDangerNo());
		this.blPDangerGetService.save(pDanger);
	}

	public FpoReendorService getFpoReendorService() {
		return this.fpoReendorService;
	}

	public void setFpoReendorService(FpoReendorService fpoReendorService) {
		this.fpoReendorService = fpoReendorService;
	}

	public PrpPDangerUnitService getPrpPDangerUnitService() {
		return this.prpPDangerUnitService;
	}

	public void setPrpPDangerUnitService(PrpPDangerUnitService prpPDangerUnitService) {
		this.prpPDangerUnitService = prpPDangerUnitService;
	}

	public PrpPDangerItemService getPrpPDangerItemService() {
		return this.prpPDangerItemService;
	}

	public void setPrpPDangerItemService(PrpPDangerItemService prpPDangerItemService) {
		this.prpPDangerItemService = prpPDangerItemService;
	}

	public PrpPDangerTotService getPrpPDangerTotService() {
		return this.prpPDangerTotService;
	}

	public void setPrpPDangerTotService(PrpPDangerTotService prpPDangerTotService) {
		this.prpPDangerTotService = prpPDangerTotService;
	}

	public PrpCDangerUnitService getPrpCDangerUnitService() {
		return this.prpCDangerUnitService;
	}

	public void setPrpCDangerUnitService(PrpCDangerUnitService prpCDangerUnitService) {
		this.prpCDangerUnitService = prpCDangerUnitService;
	}

	public PrpCDangerItemService getPrpCDangerItemService() {
		return this.prpCDangerItemService;
	}

	public void setPrpCDangerItemService(PrpCDangerItemService prpCDangerItemService) {
		this.prpCDangerItemService = prpCDangerItemService;
	}

	public BLCDangerGetService getBlCDangerGetService() {
		return this.blCDangerGetService;
	}

	public void setBlCDangerGetService(BLCDangerGetService blCDangerGetService) {
		this.blCDangerGetService = blCDangerGetService;
	}

	public PrpTDangerRiskService getPrpTDangerRiskService() {
		return this.prpTDangerRiskService;
	}

	public void setPrpTDangerRiskService(PrpTDangerRiskService prpTDangerRiskService) {
		this.prpTDangerRiskService = prpTDangerRiskService;
	}

	public PrpCDangerRiskService getPrpCDangerRiskService() {
		return this.prpCDangerRiskService;
	}

	public void setPrpCDangerRiskService(PrpCDangerRiskService prpCDangerRiskService) {
		this.prpCDangerRiskService = prpCDangerRiskService;
	}

	public PrpPDangerRiskService getPrpPDangerRiskService() {
		return this.prpPDangerRiskService;
	}

	public void setPrpPDangerRiskService(PrpPDangerRiskService prpPDangerRiskService) {
		this.prpPDangerRiskService = prpPDangerRiskService;
	}

	public BLTDangerGetService getBlTDangerGetService() {
		return this.blTDangerGetService;
	}

	public void setBlTDangerGetService(BLTDangerGetService blTDangerGetService) {
		this.blTDangerGetService = blTDangerGetService;
	}

	public BLPDangerGetService getBlPDangerGetService() {
		return this.blPDangerGetService;
	}

	public void setBlPDangerGetService(BLPDangerGetService blPDangerGetService) {
		this.blPDangerGetService = blPDangerGetService;
	}
}