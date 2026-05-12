package com.sinosoft.claim.certainLoss.util;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.common.ServiceFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;

import com.sinosoft.claim.certainLoss.service.facade.CertainLossService;
import com.sinosoft.claim.certainLoss.vo.CertainLossDto;
import com.sinosoft.claim.claim.util.DAAClaimViewHelper;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PrpDcarModelService;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.vo.CaseRelateNodeDto;
import com.sinosoft.claim.common.vo.ICollections;
import com.sinosoft.claim.common.vo.LabelValueBean;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.dto.domain.PrpDcarModelDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.util.DAARegistViewHelper;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schema.model.PrpCitemCar;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDcarModel;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpDriskConfig;
import com.sinosoft.claim.schema.model.PrpLcarLoss;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLcheckLoss;
import com.sinosoft.claim.schema.model.PrpLclaimGrade;
import com.sinosoft.claim.schema.model.PrpLclaimGradeId;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLclaimStatusId;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLcomponent;
import com.sinosoft.claim.schema.model.PrpLperson;
import com.sinosoft.claim.schema.model.PrpLpersonTrace;
import com.sinosoft.claim.schema.model.PrpLpersonWound;
import com.sinosoft.claim.schema.model.PrpLprop;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.schema.model.PrpLrepairFee;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.PrpLthirdProp;
import com.sinosoft.claim.schema.model.PrpLverifyLoss;
import com.sinosoft.claim.schema.model.PrpLverifyLossExt;
import com.sinosoft.claim.schema.model.PrpLverifyLossItem;
import com.sinosoft.claim.schema.model.SwfPath;
import com.sinosoft.claim.schema.service.facade.PrpDcodeService;
import com.sinosoft.claim.schema.service.facade.PrpLcertainLossService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimGradeService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimStatusService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonTraceService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrpLthirdPartyService;
import com.sinosoft.claim.schema.service.facade.PrpLverifyLossService;
import com.sinosoft.claim.util.BusinessRuleUtil;
import com.sinosoft.claim.util.StringConvert;
import com.sinosoft.claim.verifyLoss.service.facade.VerifyLossService;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowQueryDto;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * <p>
 * Title: CertainLossViewHelper
 * </p>
 * <p>
 * Description:定损ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2004
 * </p>
 * <br>
 */
public class DAACertainLossViewHelper extends CertainLossViewHelper {
	/** 立案ViewHelper */
	private DAAClaimViewHelper daaClaimViewHelper;
	/** 理算实赔服务 */
	private CompensateService compensateService;
	/** 车型代码的业务对象数据访问服务 */
	private PrpDcarModelService prpDcarModelService;
	/** 核损服务 */
	private VerifyLossService verifyLossService;
	/** 通用代码数据服务 */
	private PrpDcodeService prpDcodeService;
	/** 险种配置信息服务 */
	private PrpDriskConfigService prpDriskConfigService;
	/** 工作流viewHelper */
	private WorkFlowViewHelper workFlowViewHelper;

	/**
	 * 保存定损时定损页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return certainLossDto 定损数据传输数据结构
	 * @throws Exception
	 */
	public CertainLossDto viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		// 继承对certainLoss,certainLossText表的赋值
		CertainLossDto certainLossDto = super.viewToDto(httpServletRequest);
		// reason: 因为考虑到录入的时候，可能没有立案，但是在提交的时候，做了立案，导致立案号没写入。
		// 先取立案号码，很重要，不要从页面上取得。。。
		String claimNo = httpServletRequest.getParameter("prpLverifyLossClaimNo");
		String registNo = httpServletRequest.getParameter("prpLverifyLossRegistNo");
		String registNoTemp = (String) httpServletRequest.getAttribute("registNo");
		if(registNoTemp==null||"".equals(registNoTemp)){
			registNoTemp = registNo;
		}
		PrpLregist prpLregist = this.getRegistService().findByPrimaryKeyForPrpLRegist(registNoTemp);
		String policyNo = prpLregist.getPolicyNo();
		String damageDate = new DateTime(prpLregist.getDamageStartDate()).toString();
		String damageHour = prpLregist.getDamageStartHour();
		List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, null, null);
		PrpCitemKind prpCitemKind = null;
		if (claimNo == null || claimNo.length() < 2) {
			claimNo = this.codeService.translateBusinessCode(registNo, true);
		}
		// 定损处涉案车辆信息可修改，此处却没有存三者车的信息
		String lossItemCode = httpServletRequest.getParameter("prpLcarLossLossItemCode");
		/*---------------------三者车辆prpLthirdParty-----------------------------------*/
		if (!(lossItemCode == null || lossItemCode.equals(""))) {
			String prpLthirdPartyRegistNo = httpServletRequest.getParameter("prplCheckRegistNoShow");
			int serialNo = 0;
			int useYears = 0;
			double runDistance = 0.00;
			String carOwner = "";
			String insuredFlag = "";
			String flag = "";
			String clauseType = "";
			String lossFlag = "";
			double dutyPercent = 0.00;
			String insureComCode = "";
			String garageHeadName = "";
			String insuranceNo = "";
			long carryingNumber = 0L;
			String insuredIdentity = "";
			String carsOwners = "";
			String drivingAddress = "";
			String carryingUnit = "";
			String relationship = "";
			// 等待调用报案的?? 2013-01-24
			RegistDto registDto = this.getRegistService().findByPrimaryKey(prpLthirdPartyRegistNo);
			List<PrpLthirdParty> prpLthirdPartyList = registDto.getPrpLthirdPartyList();
			PrpLthirdParty prpLthirdParty = null;
			if (prpLthirdPartyList != null && !prpLthirdPartyList.isEmpty()) {
				for (int i = 0; i < prpLthirdPartyList.size(); i++) {
					prpLthirdParty = (PrpLthirdParty) prpLthirdPartyList.get(i);
					serialNo = prpLthirdParty.getId().getSerialNo();
					if (serialNo == Integer.parseInt(lossItemCode)) {
						useYears = prpLthirdParty.getUseYears();
						runDistance = prpLthirdParty.getRunDistance();
						carOwner = prpLthirdParty.getCarOwner();
						insuredFlag = prpLthirdParty.getInsuredFlag();
						flag = prpLthirdParty.getFlag();
						clauseType = prpLthirdParty.getClauseType();
						dutyPercent = prpLthirdParty.getDutyPercent();
						lossFlag = prpLthirdParty.getLossFlag();
						insureComCode = prpLthirdParty.getInsureComCode();
						garageHeadName = prpLthirdParty.getGarageHeadName();
						insuranceNo = prpLthirdParty.getInsuranceNo();
						if(prpLthirdParty.getCarryingUnit()!=null){
							carryingUnit = prpLthirdParty.getCarryingUnit();
						}
						if(prpLthirdParty.getCarryingNumber()!=null){
							carryingNumber = prpLthirdParty.getCarryingNumber();
						}
						insuredIdentity = prpLthirdParty.getInsuredIdentity();
						carsOwners = prpLthirdParty.getCarsOwners();
						drivingAddress = prpLthirdParty.getDrivingAddress();
						relationship = prpLthirdParty.getRelationship();
					} 
				}
			}
			// 从界面得到输入数组
			String prpLthirdPartyRiskCode = httpServletRequest.getParameter("prpLverifyLossRiskCode");
			String prpLthirdPartyLicenseNo = httpServletRequest.getParameter("prpLcarLossLossItemName");
			String prpLthirdPartyLicenseColorCode = httpServletRequest.getParameter("prpLcarLossLicenseColorCode");
			String prpLthirdPartyCarKindCode = httpServletRequest.getParameter("prpLcarLossCarKindCode");
			String carKindCode = httpServletRequest.getParameter("carKindCode");
			String prpLthirdPartyInsureCarFlag = httpServletRequest.getParameter("prpLcarLossInsureCarFlag");
			String prpLthirdPartyEngineNo = httpServletRequest.getParameter("prpLcarLossEngineNo");
			String prpLthirdPartyFrameNo = httpServletRequest.getParameter("prpLcarLossFrameNo");
			String prpLthirdPartyBrandName = httpServletRequest.getParameter("prpLcarLossBrandName");
			String prpLthirdPartyModelCode = httpServletRequest.getParameter("prpLcarLossModelCode");
			//String prpLthirdPartyInsureComCode = httpServletRequest.getParameter("prpLcarLossInsureComCode");//
			String prpLthirdPartyInsureComName = httpServletRequest.getParameter("prpLcarLossInsureComName");
			String prpLthirdPartyVINNo = httpServletRequest.getParameter("prpLcarLossVINNo");

			prpLthirdParty = new PrpLthirdParty();
			prpLthirdParty.getId().setRegistNo(prpLthirdPartyRegistNo);
			prpLthirdParty.getId().setSerialNo(Integer.parseInt(lossItemCode));
			prpLthirdParty.setRiskCode(prpLthirdPartyRiskCode);
			prpLthirdParty.setClaimNo(claimNo);
			prpLthirdParty.setClauseType(clauseType);
			if(prpLthirdPartyLicenseNo==null||"".equals(prpLthirdPartyLicenseNo)){
				prpLthirdPartyLicenseNo=" ";
			}
			prpLthirdParty.setLicenseNo(prpLthirdPartyLicenseNo);
			prpLthirdParty.setLicenseColorCode(prpLthirdPartyLicenseColorCode);
			prpLthirdParty.setCarKindCode(prpLthirdPartyCarKindCode);
			if (carKindCode != null) {
				prpLthirdParty.setCarKindCode(carKindCode);
			}
			prpLthirdParty.setInsureCarFlag(prpLthirdPartyInsureCarFlag);
			prpLthirdParty.setEngineNo(prpLthirdPartyEngineNo);
			prpLthirdParty.setFrameNo(prpLthirdPartyFrameNo);
			prpLthirdParty.setBrandName(prpLthirdPartyBrandName);
			prpLthirdParty.setModelCode(prpLthirdPartyModelCode);
			prpLthirdParty.setRunDistance(runDistance);
			prpLthirdParty.setUseYears(useYears);
			prpLthirdParty.setDutyPercent(dutyPercent);
			prpLthirdParty.setInsuredFlag(insuredFlag);
			prpLthirdParty.setInsureComCode(insureComCode);
			prpLthirdParty.setInsureComName(prpLthirdPartyInsureComName);
			prpLthirdParty.setVINNo(prpLthirdPartyVINNo);
			prpLthirdParty.setCarOwner(carOwner);
			prpLthirdParty.setFlag(flag);
			prpLthirdParty.setLossFlag(lossFlag);
			prpLthirdParty.setGarageHeadName(garageHeadName);
			prpLthirdParty.setInsuranceNo(insuranceNo);
			prpLthirdParty.setCarryingNumber(carryingNumber);
			prpLthirdParty.setInsuredIdentity(insuredIdentity);
			prpLthirdParty.setCarsOwners(carsOwners);
			prpLthirdParty.setDrivingAddress(drivingAddress);
			prpLthirdParty.setCarryingUnit(carryingUnit);
			prpLthirdParty.setRelationship(relationship);
			// 加入三者车辆
			certainLossDto.setPrpLthirdParty(prpLthirdParty);
		}
		// modify by liuyanmei add end reason: 定损处涉案车辆信息可修改，此处却没有存三者车的信息
		/*---------------------财产核定损明细清单表 prpLprop ------------------------------------*/
		List<PrpLprop> prpLpropList = new ArrayList<PrpLprop>();

		// 定核损处理标的表
		PrpLverifyLossItem LossItemRepairComponent = new PrpLverifyLossItem();
		PrpLverifyLossItem LossItemPerson = new PrpLverifyLossItem();
		PrpLverifyLossItem LossItemProp = new PrpLverifyLossItem();
		ArrayList<PrpLverifyLossItem> lossItemListTemp = new ArrayList<PrpLverifyLossItem>();
		// 从界面得到输入数组
		String prpLpropPolicyNo = httpServletRequest.getParameter("prpLverifyLossPolicyNo");
		String prpLpropRiskCode = httpServletRequest.getParameter("prpLverifyLossRiskCode");
		String prpLpropRegistNo = httpServletRequest.getParameter("prpLverifyLossRegistNo");

		String[] prpLpropSerialNo = httpServletRequest.getParameterValues("prpLpropSerialNo");
		String[] prpLpropItemKindNo = httpServletRequest.getParameterValues("prpLpropItemKindNo");
		String[] prpLpropFamilyNo = httpServletRequest.getParameterValues("prpLpropFamilyNo");
		String[] prpLpropFamilyName = httpServletRequest.getParameterValues("prpLpropFamilyName");
		String[] prpLpropKindCode = httpServletRequest.getParameterValues("prpLpropKindCode");
		String[] prpLpropItemCode = httpServletRequest.getParameterValues("prpLpropItemCode");
		String[] prpLpropLossItemCode = httpServletRequest.getParameterValues("prpLpropLossItemCode");
		String[] prpLpropLossItemName = httpServletRequest.getParameterValues("prpLpropLossItemName");
		String[] feeTypeCode = httpServletRequest.getParameterValues("feeTypeCode");
		String[] prpLpropCurrency = httpServletRequest.getParameterValues("prpLpropCurrency");
		String[] prpLpropUnitPrice = httpServletRequest.getParameterValues("prpLpropUnitPrice");
		String[] prpLpropLossQuantity = httpServletRequest.getParameterValues("prpLpropLossQuantity");
		String[] prpLpropUnit = httpServletRequest.getParameterValues("prpLpropUnit");
		String[] prpLpropDepreRate = httpServletRequest.getParameterValues("prpLpropDepreRate");
		String[] prpLpropSumLoss = httpServletRequest.getParameterValues("prpLpropSumLoss");
		String[] prpLpropSumReject = httpServletRequest.getParameterValues("prpLpropSumReject");
		String[] prpLpropRejectReason = httpServletRequest.getParameterValues("prpLpropRejectReason");
		String[] prpLpropLossRate = httpServletRequest.getParameterValues("prpLpropLossRate");
		String[] prpLpropSumDefLoss = httpServletRequest.getParameterValues("prpLpropSumDefLoss");
		String[] prpLpropRemark = httpServletRequest.getParameterValues("prpLpropRemark");
		String[] prpLpropVeriUnitPrice = httpServletRequest.getParameterValues("prpLpropVeriUnitPrice");
		String[] prpLpropVeriLossQuantity = httpServletRequest.getParameterValues("prpLpropVeriLossQuantity");
		String[] prpLpropVeriUnit = httpServletRequest.getParameterValues("prpLpropVeriUnit");
		String[] prpLpropVeriDepreRate = httpServletRequest.getParameterValues("prpLpropVeriDepreRate");
		String[] prpLpropVeriSumLoss = httpServletRequest.getParameterValues("prpLpropVeriSumLoss");
		String[] prpLpropVeriSumReject = httpServletRequest.getParameterValues("prpLpropVeriSumReject");
		String[] prpLpropVeriRejectReason = httpServletRequest.getParameterValues("prpLpropVeriRejectReason");
		String[] prpLpropVeriLossRate = httpServletRequest.getParameterValues("prpLpropVeriLossRate");
		String[] prpLpropVeriSumDefLoss = httpServletRequest.getParameterValues("prpLpropVeriSumDefLoss");
		String[] prpLpropVeriRemark = httpServletRequest.getParameterValues("prpLpropVeriRemark");
		String[] prpLpropFlag = httpServletRequest.getParameterValues("prpLpropFlag");
		// add by lixiang start at 2006-04-21
		// reason: 增加保存理算退回的定损的标志的保存,若有数据不会被保存冲掉
		String[] prpLpropCompensateBackFlag = httpServletRequest.getParameterValues("prpLpropCompensateBackFlag");
		// add by lixiang end at 2006-04-21
		// 对象赋值
		PrpLprop prpLprop = null;
		if (prpLpropSerialNo != null) {
			// 增加循环中代码
			for (int index = 1; index < prpLpropSerialNo.length; index++) {
				prpLprop = new PrpLprop();
				prpLprop.setPolicyNo(prpLpropPolicyNo);
				prpLprop.setRiskCode(prpLpropRiskCode);
				prpLprop.setClaimNo(claimNo);
				prpLprop.getId().setRegistNo(prpLpropRegistNo);

				prpLprop.getId().setSerialNo(index);
				prpLprop.setFamilyNo(Integer.parseInt(DataUtils.nullToZero(prpLpropFamilyNo[index])));
				prpLprop.setFamilyName(prpLpropFamilyName[index]);
				prpLprop.setKindCode(prpLpropKindCode[index]);
				prpLprop.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(prpLpropItemKindNo[index])));
				// 将循环中的代码放在循环外
				for (int k = 0; k < prpCitemKindList.size(); k++) {
					prpCitemKind = prpCitemKindList.get(k);
					if (prpCitemKind.getKindCode() == prpLprop.getKindCode()) {
						prpLprop.setItemKindNo(prpCitemKind.getId().getItemKindNo());
						break;
					}
				}
				prpLprop.setItemCode(prpLpropItemCode[index]);
				prpLprop.setLossItemCode(prpLpropLossItemCode[index]);
				prpLprop.setLossItemName(prpLpropLossItemName[index]);
				prpLprop.setFeeTypeCode(feeTypeCode[index]);

				if ("01".equals(feeTypeCode[index])) {
					prpLprop.setFeeTypeName("修理费");
				} else {
					prpLprop.setFeeTypeName("材料费");
				}
				prpLprop.setCurrency(prpLpropCurrency[index]);
				prpLprop.setUnitPrice(Double.parseDouble(DataUtils.nullToZero(prpLpropUnitPrice[index])));
				prpLprop.setLossQuantity(Double.parseDouble(DataUtils.nullToZero(prpLpropLossQuantity[index])));
				prpLprop.setUnit(prpLpropUnit[index]);
				prpLprop.setBuyDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));
				prpLprop.setDepreRate(Double.parseDouble(DataUtils.nullToZero(prpLpropDepreRate[index])));
				prpLprop.setSumLoss(Double.parseDouble(DataUtils.nullToZero(prpLpropSumLoss[index])));
				prpLprop.setSumReject(Double.parseDouble(DataUtils.nullToZero(prpLpropSumReject[index])));
				prpLprop.setRejectReason(prpLpropRejectReason[index]);
				prpLprop.setLossRate(Double.parseDouble(DataUtils.nullToZero(prpLpropLossRate[index])));
				prpLprop.setSumDefLoss(Double.parseDouble(DataUtils.nullToZero(prpLpropSumDefLoss[index])));
				prpLprop.setRemark(prpLpropRemark[index]);
				prpLprop.setVeriUnitPrice(Double.parseDouble(DataUtils.nullToZero(prpLpropVeriUnitPrice[index])));
				prpLprop.setVeriLossQuantity(Double.parseDouble(DataUtils.nullToZero(prpLpropVeriLossQuantity[index])));
				prpLprop.setVeriUnit(prpLpropVeriUnit[index]);
				prpLprop.setVeriDepreRate(Double.parseDouble(DataUtils.nullToZero(prpLpropVeriDepreRate[index])));
				prpLprop.setVeriSumLoss(Double.parseDouble(DataUtils.nullToZero(prpLpropVeriSumLoss[index])));
				prpLprop.setVeriSumReject(Double.parseDouble(DataUtils.nullToZero(prpLpropVeriSumReject[index])));
				prpLprop.setVeriRejectReason(prpLpropVeriRejectReason[index]);
				prpLprop.setVeriLossRate(Double.parseDouble(DataUtils.nullToZero(prpLpropVeriLossRate[index])));
				prpLprop.setVeriSumDefLoss(Double.parseDouble(DataUtils.nullToZero(prpLpropVeriSumDefLoss[index])));
				prpLprop.setVeriRemark(prpLpropVeriRemark[index]);
				prpLprop.setFlag(prpLpropFlag[index]);
				prpLprop.setCompensateBackFlag(prpLpropCompensateBackFlag[index]);
				// 加入集合
				prpLpropList.add(prpLprop);
			}
			PropertyUtils.copyProperties(LossItemProp.getId(), certainLossDto.getPrpLverifyLoss().getId());
			PropertyUtils.copyProperties(LossItemProp.getId(), certainLossDto.getPrpLverifyLoss());
			LossItemProp.getId().setSerialNo(3);
			LossItemProp.getId().setLossType("3");
			LossItemProp.getId().setNodeType("propc");
			lossItemListTemp.add(LossItemProp);
		}
		// 财产核定损明细清单表
		certainLossDto.setPrpLpropList(prpLpropList);

		/*---------------------定损车辆表&修理费用清单&换件项目清单 prpLcarLoss&prpLrepairFee&prpLcomponent ------------------------------------*/
		List<PrpLcarLoss> prpLcarLossList = new ArrayList<PrpLcarLoss>();
		PrpLcarLoss prpLcarLoss = null;
		// 修理费用清单
		List<PrpLrepairFee> prpLrepairFeeList = new ArrayList<PrpLrepairFee>();
		PrpLrepairFee prpLrepairFee = null;
		// 换件项目清单
		List<PrpLcomponent> prpLcomponentList = new ArrayList<PrpLcomponent>();
		PrpLcomponent prpLcomponent = null;

		// 从界面得到输入数组
		String prpLcarLossPolicyNo = httpServletRequest.getParameter("prpLverifyLossPolicyNo");
		String prpLcarLossRiskCode = httpServletRequest.getParameter("prpLverifyLossRiskCode");
		String prpLcarLossRegistNo = httpServletRequest.getParameter("prpLverifyLossRegistNo");

		// 修理换件的数量
		int repairFeeNo = 0;
		int componentNo = 0;

		String[] prpLcarLossLossItemCode = httpServletRequest.getParameterValues("prpLcarLossLossItemCode");
		String[] prpLcarLossLossItemName = httpServletRequest.getParameterValues("prpLcarLossLossItemName");
		String[] prpLcarLossCurrency = httpServletRequest.getParameterValues("prpLcarLossCurrency");
		String[] prpLcarLossSumRest = httpServletRequest.getParameterValues("prpLcarLossSumRest");
		String[] prpLcarLossSumManager = httpServletRequest.getParameterValues("prpLcarLossSumManager");
		String[] prpLcarLossSumCertainLoss = httpServletRequest.getParameterValues("prpLcarLossSumCertainLoss");
		String[] prpLcarLossSumVeriRest = httpServletRequest.getParameterValues("prpLcarLossSumVeriRest");
		String[] prpLcarLossSumVeriManager = httpServletRequest.getParameterValues("prpLcarLossSumVeriManager");
		String[] prpLcarLossSumVerifyLoss = httpServletRequest.getParameterValues("SumDefLoss2");
		String[] prpLcarLossLossDesc = httpServletRequest.getParameterValues("prpLcarLossLossDesc");
		String[] prpLcarLossIndemnityDuty = httpServletRequest.getParameterValues("prpLcarLossIndemnityDuty");
		String[] prpLcarLossIndemnityDutyRate = httpServletRequest.getParameterValues("prpLcarLossIndemnityDutyRate");
		String[] prpLcarLossVeriIndeDutyRate = httpServletRequest.getParameterValues("prpLcarLossVeriIndeDutyRate");
		String[] prpLcarLossRemark = httpServletRequest.getParameterValues("prpLcarLossRemark");
		String[] prpLcarLossOperatorCode = httpServletRequest.getParameterValues("prpLcarLossOperatorCode");
		String[] prpLcarLossApproverCode = httpServletRequest.getParameterValues("prpLcarLossApproverCode");
		String[] prpLcarLossFlag = httpServletRequest.getParameterValues("prpLcarLossFlag");
		String[] prpLcarLossBackCheckFlag = httpServletRequest.getParameterValues("prpLcarLossBackCheckFlag");
		String[] prpLcarLossCarKindCode = httpServletRequest.getParameterValues("prpLcarLossCarKindCode");
		String[] prpLcarLossLicenseColorCode = httpServletRequest.getParameterValues("prpLcarLossLicenseColorCode");
		String[] prpLcarLossVINNo = httpServletRequest.getParameterValues("prpLcarLossVINNo");
		String[] prpLcarLossSumManageFeeRate = httpServletRequest.getParameterValues("prpLcarLossSumManageFeeRate");

		String[] prpLcarLossSumTransFee = httpServletRequest.getParameterValues("prpLcarLossSumTransFee");
		String[] prpLcarLossSumTax = httpServletRequest.getParameterValues("prpLcarLossSumTax");
		String[] prpLcarLossSumFloatRate = httpServletRequest.getParameterValues("prpLcarLossSumFloatRate");

		String prpLrepairFeeRepairFactoryCode = httpServletRequest.getParameter("prpLrepairFeeRepairFactoryCode");
		String prpLrepairFeeRepairFactoryName = httpServletRequest.getParameter("prpLrepairFeeRepairFactoryName");
		String prpLrepairFeeRepairStartDate = httpServletRequest.getParameter("prpLrepairFeeRepairStartDate");
		String prpLrepairFeeRepairEndDate = httpServletRequest.getParameter("prpLrepairFeeRepairEndDate");
		String prpLrepairFeeHandlerCode = httpServletRequest.getParameter("prpLrepairFeeHandlerCode");

		// prpLrepairFee
		String[] carLossRepairFeeLossItemCode = httpServletRequest.getParameterValues("carLossRepairFeeLossItemCode");
		String[] prpLrepairFeeItemKindNo = httpServletRequest.getParameterValues("prpLrepairFeeItemKindNo");
		String[] prpLrepairFeeKindCode = httpServletRequest.getParameterValues("prpLrepairFeeKindCode");

		String[] prpLrepairFeeSanctioner = httpServletRequest.getParameterValues("prpLrepairFeeSanctioner");
		String[] prpLrepairFeeApproverCode = httpServletRequest.getParameterValues("prpLrepairFeeApproverCode");
		String[] prpLrepairFeeOperatorCode = httpServletRequest.getParameterValues("prpLrepairFeeOperatorCode");
		// Modify by chenrenda add begin 20050413
		// Reason:在修理清单中加上损失部位、修理方式
		String[] prpLrepairFeePartCode = httpServletRequest.getParameterValues("prpLrepairFeePartCode");
		String[] prpLrepairFeePartName = httpServletRequest.getParameterValues("prpLrepairFeePartName");
		String[] prpLrepairFeeRepairType = httpServletRequest.getParameterValues("prpLrepairFeeRepairType");
		// Modify by chenrenda add end 20050413
		String[] prpLrepairFeeCompCode = httpServletRequest.getParameterValues("prpLrepairFeeCompCode");
		String[] prpLrepairFeeCompName = httpServletRequest.getParameterValues("prpLrepairFeeCompName");
		String[] prpLrepairFeeManHour = httpServletRequest.getParameterValues("prpLrepairFeeManHour");
		String[] prpLrepairFeeManHourUnitPrice = httpServletRequest.getParameterValues("prpLrepairFeeManHourUnitPrice");
		String[] prpLrepairFeeManHourFee = httpServletRequest.getParameterValues("prpLrepairFeeManHourFee");
		String[] prpLrepairFeeMaterialFee = httpServletRequest.getParameterValues("prpLrepairFeeMaterialFee");
		String[] prpLrepairFeeLossRate = httpServletRequest.getParameterValues("prpLrepairFeeLossRate");
		String[] prpLrepairFeeCurrency = httpServletRequest.getParameterValues("prpLrepairFeeCurrency");

		String[] prpLrepairFeeSumDefLoss = httpServletRequest.getParameterValues("prpLrepairFeeSumDefLoss");
		String[] prpLrepairFeeFirstSumDefLoss = httpServletRequest.getParameterValues("prpLrepairFeeFirstSumDefLoss");

		String[] prpLrepairFeeRemark = httpServletRequest.getParameterValues("prpLrepairFeeRemark");
		String[] prpLrepairFeeVeriManHour = httpServletRequest.getParameterValues("prpLrepairFeeVeriManHour");
		String[] prpLrepairFeeVeriManUnitPrice = httpServletRequest.getParameterValues("prpLrepairFeeVeriManUnitPrice");
		String[] prpLrepairFeeVeriManHourFee = httpServletRequest.getParameterValues("prpLrepairFeeVeriManHourFee");
		String[] prpLrepairFeeVeriMaterQuantity = httpServletRequest.getParameterValues("prpLrepairFeeVeriMaterQuantity");
		String[] prpLrepairFeeVeriMaterUnitPrice = httpServletRequest.getParameterValues("prpLrepairFeeVeriMaterUnitPrice");
		String[] prpLrepairFeeVeriMaterialFee = httpServletRequest.getParameterValues("prpLrepairFeeVeriMaterialFee");
		String[] prpLrepairFeeVeriLossRate = httpServletRequest.getParameterValues("prpLrepairFeeVeriLossRate");
		String[] prpLrepairFeeVeriSumLoss = httpServletRequest.getParameterValues("prpLrepairFeeVeriSumLoss");
		String[] prpLrepairFeeVeriRemark = httpServletRequest.getParameterValues("prpLrepairFeeVeriRemark");
		String[] prpLrepairFeeFlag = httpServletRequest.getParameterValues("prpLrepairFeeFlag");
		// add by luochang begin at 2010-09-04 增加精友唯一标志
		String[] prpLrepairFeeIndid = httpServletRequest.getParameterValues("prpLrepairFeeIndId");
		// add by luochang end at 2010-09-04 增加精友唯一标志
		// add by lixiang start at 2006-04-21
		// reason: 增加保存理算退回的定损的标志的保存,若有数据不会被保存冲掉
		String[] prpLrepairFeeCompensateBackFlag = httpServletRequest.getParameterValues("prpLrepairFeeCompensateBackFlag");
		// add by lixiang start at 2006-04-21
		// reason: 增加保存理算退回的定损的标志的保存,若有数据不会被保存冲掉
		String[] prpLcomponentCompensateBackFlag = httpServletRequest.getParameterValues("prpLcomponentCompensateBackFlag");
		// add by lixiang end at 2006-04-21
		// prpLcomponent
		String[] carLossComponentLossItemCode = httpServletRequest.getParameterValues("carLossComponentLossItemCode");
		String[] prpLcomponentItemKindNo = httpServletRequest.getParameterValues("prpLcomponentItemKindNo");
		String[] prpLcomponentKindCode = httpServletRequest.getParameterValues("prpLcomponentKindCode");
		String[] prpLcomponentIndId = httpServletRequest.getParameterValues("prpLcomponentIndId");
		String[] prpLcomponentMakeYear = httpServletRequest.getParameterValues("prpLcomponentMakeYear");
		String[] prpLcomponentGearboxType = httpServletRequest.getParameterValues("prpLcomponentGearboxType");
		String[] prpLcomponentQuoteCompanyGrade = httpServletRequest.getParameterValues("prpLcomponentQuoteCompanyGrade");
		String[] prpLcomponentManageFeeRate = httpServletRequest.getParameterValues("prpLcomponentManageFeeRate");
		String prpLcomponentRepairFactoryCode = httpServletRequest.getParameter("prpLrepairFeeRepairFactoryCode");
		String prpLcomponentRepairFactoryName = httpServletRequest.getParameter("prpLrepairFeeRepairFactoryName");
		String prpLcomponentHandlerCode = httpServletRequest.getParameter("prpLrepairFeeHandlerCode");
		String[] prpLcomponentSanctioner = httpServletRequest.getParameterValues("prpLcomponentSanctioner");
		String[] prpLcomponentApproverCode = httpServletRequest.getParameterValues("prpLcomponentApproverCode");
		String[] prpLcomponentOperatorCode = httpServletRequest.getParameterValues("prpLcomponentOperatorCode");
		String[] prpLcomponentRepairFactoryFee = httpServletRequest.getParameterValues("prpLcomponentRepairFactoryFee");
		String[] prpLcomponentPriceType = httpServletRequest.getParameterValues("prpLcomponentPriceType");
		// Modify by chenrenda add begin 20050413
		// Reason:在换件清单中加上损失部位
		String[] prpLcomponentPartCode = httpServletRequest.getParameterValues("prpLcomponentPartCode");
		String[] prpLcomponentPartName = httpServletRequest.getParameterValues("prpLcomponentPartName");
		// //Modify by chenrenda add end 20050413
		String[] prpLcomponentCompCode = httpServletRequest.getParameterValues("prpLcomponentCompCode");
		String[] prpLcomponentCompName = httpServletRequest.getParameterValues("prpLcomponentCompName");
		String[] prpLcomponentQuantity = httpServletRequest.getParameterValues("prpLcomponentQuantity");
		String[] prpLcomponentManHourFee = httpServletRequest.getParameterValues("prpLcomponentManHourFee");

		String[] prpLcomponentMaterialFee = httpServletRequest.getParameterValues("prpLcomponentMaterialFee");

		String[] prpLcomponentRestFee = httpServletRequest.getParameterValues("prpLcomponentRestFee");
		String[] prpLcomponentVeriRestFee = httpServletRequest.getParameterValues("prpLcomponentVeriRestFee");

		String[] prpLcomponentQueryPrice = httpServletRequest.getParameterValues("prpLcomponentQueryPrice");

		String[] prpLcomponentQuotedPrice = httpServletRequest.getParameterValues("prpLcomponentQuotedPrice");
		String[] prpLcomponentLossRate = httpServletRequest.getParameterValues("prpLcomponentLossRate");
		String[] prpLcomponentCurrency = httpServletRequest.getParameterValues("prpLcomponentCurrency");
		String[] prpLcomponentSumDefLoss = httpServletRequest.getParameterValues("prpLcomponentSumDefLoss");
		String[] prpLcomponentRemark = httpServletRequest.getParameterValues("prpLcomponentRemark");
		String[] prpLcomponentVeriQuantity = httpServletRequest.getParameterValues("prpLcomponentVeriQuantity");
		String[] prpLcomponentVeriManHourFee = httpServletRequest.getParameterValues("prpLcomponentVeriManHourFee");
		String[] prpLcomponentVeriMaterFee = httpServletRequest.getParameterValues("prpLcomponentVeriMaterFee");
		String[] prpLcomponentVeriLossRate = httpServletRequest.getParameterValues("prpLcomponentVeriLossRate");
		String[] prpLcomponentSumVeriLoss = httpServletRequest.getParameterValues("prpLcomponentVeriSumDefLoss");
		String[] prpLcomponentVeriRemark = httpServletRequest.getParameterValues("prpLcomponentVeriRemark");
		String[] prpLcomponentFlag = httpServletRequest.getParameterValues("prpLcomponentFlag");
		// add by luochang begin at 2010-09-04 增加是否回收标志
		String[] prpLcomponentIfRemain = httpServletRequest.getParameterValues("prpLcomponentIfRemain");
		// add by luochang end at 2010-09-04 增加是否回收标志
		String[] prpLcomponentOriginalId = httpServletRequest.getParameterValues("prpLcomponentOriginalId");

		String[] prpLcomponentSys4SPrice = httpServletRequest.getParameterValues("prpLcomponentSys4SPrice");
		String[] prpLcomponentSysMarketPrice = httpServletRequest.getParameterValues("prpLcomponentSysMarketPrice");
		String[] prpLcomponentSysMatchPrice = httpServletRequest.getParameterValues("prpLcomponentSysMatchPrice");
		String[] prpLcomponentNative4SPrice = httpServletRequest.getParameterValues("prpLcomponentNative4SPrice");
		String[] prpLcomponentNativeMarketPrice = httpServletRequest.getParameterValues("prpLcomponentNativeMarketPrice");
		String[] prpLcomponentNativeMatchPrice = httpServletRequest.getParameterValues("prpLcomponentNativeMatchPrice");
		String[] prpLcomponentVerpCompPrice = httpServletRequest.getParameterValues("prpLcomponentVerpCompPrice");

		// Modify by chenrenda update begin 20050420
		// Reason:定损提交核损时，去掉人员选择，增加核损级别
		String prplCarLossHandlerRange = "";
		int intPrpLcarLossLossItemCode = 0;
		// Modify by chenrenda update end 20050420
		if (prpLcarLossLossItemCode != null) {
			for (int i = 0; i < prpLcarLossLossItemCode.length; i++) {
				prpLcarLoss = new PrpLcarLoss();
				prpLcarLoss.setPolicyNo(prpLcarLossPolicyNo);
				prpLcarLoss.setRiskCode(prpLcarLossRiskCode);
				prpLcarLoss.setClaimNo(claimNo);
				prpLcarLoss.getId().setRegistNo(prpLcarLossRegistNo);
				prpLcarLoss.getId().setLossItemCode(prpLcarLossLossItemCode[i]);
				// Modify by chenrenda update begin 20050420
				// Reason:定损提交核损时，去掉人员选择，增加核损级别
				intPrpLcarLossLossItemCode = Integer.parseInt(DataUtils.nullToZero(prpLcarLossLossItemCode[i]));
				if (intPrpLcarLossLossItemCode > 0) {
					prplCarLossHandlerRange = httpServletRequest.getParameter("prplCarLossHandlerRange");
				}
				prpLcarLoss.setHandlerRange(prplCarLossHandlerRange);
				if(prpLcarLossLossItemName[i]==null||"".equals(prpLcarLossLossItemName[i])){
					prpLcarLossLossItemName[i]=" ";
				}
				prpLcarLoss.setLossItemName(prpLcarLossLossItemName[i]);
				prpLcarLoss.setCurrency(prpLcarLossCurrency[i]);
				prpLcarLoss.setSumRest(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumRest[i])));
				prpLcarLoss.setSumManager(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumManager[i])));
				// add by zhyi 20110905 fubon-2422 增加浮動比例
				prpLcarLoss.setSumFloatRate(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumFloatRate[i])));

				prpLcarLoss.setSumCertainLoss(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumCertainLoss[i])));
				prpLcarLoss.setSumVeriRest(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumVeriRest[i])));
				prpLcarLoss.setSumVeriManager(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumVeriManager[i])));
				prpLcarLoss.setSumVerifyLoss(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumVerifyLoss[i])));
				prpLcarLoss.setLossDesc(prpLcarLossLossDesc[i]);
				prpLcarLoss.setIndemnityDuty(prpLcarLossIndemnityDuty[i]);
				prpLcarLoss.setIndemnityDutyRate(Double.parseDouble(DataUtils.nullToZero(prpLcarLossIndemnityDutyRate[i])));
				prpLcarLoss.setVeriIndeDutyRate(Double.parseDouble(DataUtils.nullToZero(prpLcarLossVeriIndeDutyRate[i])));
				prpLcarLoss.setRemark(prpLcarLossRemark[i]);
				prpLcarLoss.setVINNo(prpLcarLossVINNo[i]);
				prpLcarLoss.setSumManageFeeRate(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumManageFeeRate[i])));
				prpLcarLoss.setOperatorCode(prpLcarLossOperatorCode[i]);
				prpLcarLoss.setApproverCode(prpLcarLossApproverCode[i]);
				prpLcarLoss.setFlag(prpLcarLossFlag[i]);

				prpLcarLoss.setBackCheckFlag(prpLcarLossBackCheckFlag[i]);
				prpLcarLoss.setSumTransFee(Double.parseDouble(prpLcarLossSumTransFee[i]));
				prpLcarLoss.setSumTax(Double.parseDouble(prpLcarLossSumTax[i]));

				// 加入集合
				prpLcarLossList.add(prpLcarLoss);
				// ==============================================================================
				// zhangshi 将循环中registDto放在循环外处理

				// 对象赋值
				for (int index1 = 1; index1 < carLossRepairFeeLossItemCode.length; index1++) {
					repairFeeNo = repairFeeNo + 1;
					prpLrepairFee = new PrpLrepairFee();
					prpLrepairFee.setPolicyNo(prpLcarLossPolicyNo);
					prpLrepairFee.setRiskCode(prpLcarLossRiskCode);
					prpLrepairFee.setClaimNo(claimNo);
					prpLrepairFee.getId().setRegistNo(prpLcarLossRegistNo);

					prpLrepairFee.getId().setSerialNo(repairFeeNo);
					prpLrepairFee.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(prpLrepairFeeItemKindNo[index1])));
					prpLrepairFee.getId().setLossItemCode(prpLcarLossLossItemCode[i]);
					prpLrepairFee.setKindCode(prpLrepairFeeKindCode[index1]);
					
					for (int k = 0; k < prpCitemKindList.size(); k++) {
						prpCitemKind = prpCitemKindList.get(k);
						if (prpCitemKind.getKindCode().equals(prpLrepairFee.getKindCode())) {
							prpLrepairFee.setItemKindNo(prpCitemKind.getId().getItemKindNo());
							break;
						}
					}
					// ===========================================================
					prpLrepairFee.setLicenseNo(prpLcarLossLossItemName[i]);
					prpLrepairFee.setLicenseColorCode(prpLcarLossLicenseColorCode[i]);
					prpLrepairFee.setCarKindCode(prpLcarLossCarKindCode[i]);
					prpLrepairFee.setRepairFactoryCode(prpLrepairFeeRepairFactoryCode);
					prpLrepairFee.setRepairFactoryName(prpLrepairFeeRepairFactoryName);
					prpLrepairFee.setHandlerCode(prpLrepairFeeHandlerCode);

					prpLrepairFee.setRepairStartDate(new DateTime(prpLrepairFeeRepairStartDate));
					prpLrepairFee.setRepairEndDate(new DateTime(prpLrepairFeeRepairEndDate));
					prpLrepairFee.setSanctioner(prpLrepairFeeSanctioner[index1]);
					prpLrepairFee.setApproverCode(prpLrepairFeeApproverCode[index1]);
					prpLrepairFee.setOperatorCode(prpLrepairFeeOperatorCode[index1]);
					// Modify by chenrenda add begin 20050413
					// Reason:在修理清单中加上损失部位、修理方式
					prpLrepairFee.setPartCode(prpLrepairFeePartCode[index1]);
					prpLrepairFee.setPartName(prpLrepairFeePartName[index1]);
					prpLrepairFee.setRepairType(prpLrepairFeeRepairType[index1]);
					// Modify by chenrenda add end 20050413
					prpLrepairFee.setCompCode(prpLrepairFeeCompCode[index1]);
					prpLrepairFee.setCompName(prpLrepairFeeCompName[index1]);
					prpLrepairFee.setManHour(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeManHour[index1])));
					prpLrepairFee.setManHourUnitPrice(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeManHourUnitPrice[index1])));

					prpLrepairFee.setManHourFee(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeManHourFee[index1])));
					prpLrepairFee.setMaterialFee(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeMaterialFee[index1])));
					prpLrepairFee.setLossRate(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeLossRate[index1])));
					prpLrepairFee.setCurrency(prpLrepairFeeCurrency[index1]);

					prpLrepairFee.setSumDefLoss(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeSumDefLoss[index1])));
					prpLrepairFee.setFirstSumDefLoss(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeFirstSumDefLoss[index1])));

					prpLrepairFee.setRemark(prpLrepairFeeRemark[index1]);
					prpLrepairFee.setVeriManHour(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeVeriManHour[index1])));
					prpLrepairFee.setVeriManUnitPrice(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeVeriManUnitPrice[index1])));
					prpLrepairFee.setVeriManHourFee(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeVeriManHourFee[index1])));
					prpLrepairFee.setVeriMaterQuantity(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeVeriMaterQuantity[index1])));
					prpLrepairFee.setVeriMaterUnitPrice(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeVeriMaterUnitPrice[index1])));
					prpLrepairFee.setVeriMaterialFee(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeVeriMaterialFee[index1])));
					prpLrepairFee.setVeriLossRate(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeVeriLossRate[index1])));
					prpLrepairFee.setVeriSumLoss(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeVeriSumLoss[index1])));
					prpLrepairFee.setVeriRemark(prpLrepairFeeVeriRemark[index1]);
					prpLrepairFee.setFlag(prpLrepairFeeFlag[index1]);
					prpLrepairFee.setIndId(prpLrepairFeeIndid[index1]);
					// add by lixiang start at 2006-04-21
					// reason: 增加保存理算退回的定损的标志的保存,若有数据不会被保存冲掉
					prpLrepairFee.setCompensateBackFlag(prpLrepairFeeCompensateBackFlag[index1]);
					// add by lixiang end at 2006-04-21
					// 加入集合
					prpLrepairFeeList.add(prpLrepairFee);
					// }
				}
				int indexpos = 0;
				// 对象赋值
				for (int index2 = 1; index2 < carLossComponentLossItemCode.length; index2++) {
					componentNo = componentNo + 1;
					prpLcomponent = new PrpLcomponent();
					prpLcomponent.setPolicyNo(prpLcarLossPolicyNo);
					prpLcomponent.setRiskCode(prpLcarLossRiskCode);
					prpLcomponent.setClaimNo(claimNo);
					prpLcomponent.getId().setRegistNo(prpLcarLossRegistNo);
					prpLcomponent.getId().setSerialNo(componentNo);
					prpLcomponent.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(prpLcomponentItemKindNo[index2])));
					prpLcomponent.setKindCode(prpLcomponentKindCode[index2]);
					prpLcomponent.setIndId(DataUtils.nullToZero(prpLcomponentIndId[index2]));
					// ===========================================================
					// zhangshi 简化循环内代码 20080509
					if (!"".equals(prpLregist.getPolicyNo())) {
						for (int k = 0; k < prpCitemKindList.size(); k++) {
							prpCitemKind = prpCitemKindList.get(k);
							if (prpCitemKind.getKindCode().equals(prpLcomponent.getKindCode())) {
								prpLcomponent.setItemKindNo(prpCitemKind.getId().getItemKindNo());
								break;
							}
						}
					}
					// ===========================================================
					prpLcomponent.getId().setLossItemCode(prpLcarLossLossItemCode[i]);
					prpLcomponent.setLicenseNo(prpLcarLossLossItemName[i]);
					prpLcomponent.setLicenseColorCode(prpLcarLossLicenseColorCode[i]);
					prpLcomponent.setCarKindCode(prpLcarLossCarKindCode[i]);
					prpLcomponent.setMakeYear(prpLcomponentMakeYear[index2]);
					prpLcomponent.setGearboxType(prpLcomponentGearboxType[index2]);
					prpLcomponent.setQuoteCompanyGrade(prpLcomponentQuoteCompanyGrade[index2]);
					prpLcomponent.setManageFeeRate(Double.parseDouble(DataUtils.nullToZero(prpLcomponentManageFeeRate[index2])));
					prpLcomponent.setRepairFactoryCode(prpLcomponentRepairFactoryCode);
					prpLcomponent.setRepairFactoryName(prpLcomponentRepairFactoryName);
					prpLcomponent.setHandlerCode(prpLcomponentHandlerCode);
					prpLcomponent.setRepairStartDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));
					prpLcomponent.setRepairEndDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));

					prpLcomponent.setSanctioner(prpLcomponentSanctioner[index2]);
					prpLcomponent.setApproverCode(prpLcomponentApproverCode[index2]);
					prpLcomponent.setOperatorCode(prpLcomponentOperatorCode[index2]);
					prpLcomponent.setRepairFactoryFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentRepairFactoryFee[index2])));
					if (prpLcomponentPriceType != null)
						prpLcomponent.setPriceType(prpLcomponentPriceType[index2]);
					// Modify by chenrenda add begin 20050413
					// Reason:在换件清单中加上损失部位

					prpLcomponent.setPartCode(prpLcomponentPartCode[index2]);
					prpLcomponent.setPartName(prpLcomponentPartName[index2]);
					//
					prpLcomponent.setOriginalId(prpLcomponentOriginalId[index2]);

					prpLcomponent.setSys4SPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentSys4SPrice[index2])));
					prpLcomponent.setSysMarketPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentSysMarketPrice[index2])));
					prpLcomponent.setSysMatchPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentSysMatchPrice[index2])));
					prpLcomponent.setNative4SPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentNative4SPrice[index2])));
					prpLcomponent.setNativeMarketPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentNativeMarketPrice[index2])));
					prpLcomponent.setNativeMatchPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentNativeMatchPrice[index2])));
					prpLcomponent.setVerpCompPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentVerpCompPrice[index2])));

					prpLcomponent.setCompName(prpLcomponentCompName[index2]);
					if (prpLcomponentCompCode[index2] == null || prpLcomponentCompCode[index2].trim().length() == 0) {
						prpLcomponent.setCompCode("00");
					} else {
						prpLcomponent.setCompCode(prpLcomponentCompCode[index2]);
					}
					prpLcomponent.setQuantity(Integer.parseInt(DataUtils.nullToZero(prpLcomponentQuantity[index2])));
					prpLcomponent.setManHourFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentManHourFee[index2])));
					prpLcomponent.setMaterialFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentMaterialFee[index2])));
					prpLcomponent.setMaterialFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentMaterialFee[index2])));

					prpLcomponent.setRestFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentRestFee[index2])));
					prpLcomponent.setQueryPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentQueryPrice[index2])));
					prpLcomponent.setQuotedPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentQuotedPrice[index2])));

					prpLcomponent.setLossRate(Double.parseDouble(DataUtils.nullToZero(prpLcomponentLossRate[index2])));
					prpLcomponent.setCurrency(prpLcomponentCurrency[index2]);
					prpLcomponent.setSumDefLoss(Double.parseDouble(DataUtils.nullToZero(prpLcomponentSumDefLoss[index2])));
					prpLcomponent.setRemark(prpLcomponentRemark[index2]);
					prpLcomponent.setVeriManHourFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentVeriManHourFee[index2])));

					if (prpLcomponentVeriMaterFee != null) {
						if (prpLcomponentVeriMaterFee.length > carLossComponentLossItemCode.length) {
							// 核损退回定损，在定损中的换件中，添加一个零件，在提交核损时报下标越界，
							// prpLcomponentVeriMaterFee 定核总数-->a1 ,a为其索引值。
							// carLossComponentLossItemCode 定损总数-->b1,b为其索引值。
							// 正常情况下，a1=2b1。
							// 当a1！=2b1时，也就是定损回退中录入了配件信息。
							// 所以将index2阻止到定损回退中没有增加新的零件之前
							// 最後一次a=2b时b的值为index_wangliguang
							// prpLcomponentVeriMaterFee.length-carLossComponentLossItemCode.length------>核损条数----->没有回退之前的定损条数----->求出index2（阻止到定损回退中没有增加新的零件之前）
							// 原因在这之前是二倍关系
							if (index2 < (prpLcomponentVeriMaterFee.length - carLossComponentLossItemCode.length + 1)) {
								prpLcomponent.setVeriMaterFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentVeriMaterFee[index2 * 2])));
								indexpos = index2;
							} else {
								// else里就是一对一了
								// 定核损当前索引值a1=indexpos*2+（index2-indexpos）
								// （index2-indexpos）else里又走了几回
								// indexpos*2--->if最後的index2*2
								prpLcomponent.setVeriMaterFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentVeriMaterFee[indexpos + index2])));
							}
						} else {
							prpLcomponent.setVeriMaterFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentVeriMaterFee[index2])));
						}
					}
					if (prpLcomponentSumVeriLoss != null) {
						if (prpLcomponentSumVeriLoss.length > carLossComponentLossItemCode.length) {
							if (index2 < (prpLcomponentVeriMaterFee.length - carLossComponentLossItemCode.length + 1)) {
								prpLcomponent.setSumVeriLoss(Double.parseDouble(DataUtils.nullToZero(prpLcomponentSumVeriLoss[index2 * 2])));
							} else {
								// 定核损当前索引值a1=indexpos*2+（index2-indexpos）
								prpLcomponent.setSumVeriLoss(Double.parseDouble(DataUtils.nullToZero(prpLcomponentSumVeriLoss[indexpos + index2])));
							}
						} else {
							prpLcomponent.setSumVeriLoss(Double.parseDouble(DataUtils.nullToZero(prpLcomponentSumVeriLoss[index2])));
						}
					}
					if (prpLcomponentVeriQuantity != null && prpLcomponentVeriQuantity.length > carLossComponentLossItemCode.length) {
						if (index2 < (prpLcomponentVeriMaterFee.length - carLossComponentLossItemCode.length + 1)) {
							prpLcomponent.setVeriQuantity(Integer.parseInt(DataUtils.nullToZero(prpLcomponentVeriQuantity[index2 * 2])));
						} else {
							// 定核损当前索引值a1=indexpos*2+（index2-indexpos）
							prpLcomponent.setVeriQuantity(Integer.parseInt(DataUtils.nullToZero(prpLcomponentVeriQuantity[indexpos + index2])));
						}
					} else {
						prpLcomponent.setVeriQuantity(Integer.parseInt(DataUtils.nullToZero(prpLcomponentVeriQuantity[index2])));
					}
					if ((prpLcomponentVeriRestFee != null && prpLcomponentVeriRestFee.length > carLossComponentLossItemCode.length)) {
						if (index2 < (prpLcomponentVeriMaterFee.length - carLossComponentLossItemCode.length + 1)) {
							prpLcomponent.setVeriRestFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentVeriRestFee[index2 * 2])));
						} else {
							// 定核损当前索引值a1=indexpos*2+（index2-indexpos）
							prpLcomponent.setVeriRestFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentVeriRestFee[indexpos + index2])));
						}
					} else {
						prpLcomponent.setVeriRestFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentVeriRestFee[index2])));
					}
					if ((prpLcomponentVeriRemark != null && prpLcomponentVeriRemark.length > carLossComponentLossItemCode.length)) {
						if (index2 < (prpLcomponentVeriMaterFee.length - carLossComponentLossItemCode.length + 1)) {
							prpLcomponent.setVeriRemark(prpLcomponentVeriRemark[index2 * 2]);
						} else {
							// 定核损当前索引值a1=indexpos*2+（index2-indexpos）
							prpLcomponent.setVeriRemark(prpLcomponentVeriRemark[indexpos + index2]);
						}
					} else {
						prpLcomponent.setVeriRemark(prpLcomponentVeriRemark[index2]);
					}
					// modify by lidonghui 2007-09-17 end
					prpLcomponent.setVeriLossRate(Double.parseDouble(DataUtils.nullToZero(prpLcomponentVeriLossRate[index2])));
					prpLcomponent.setFlag(prpLcomponentFlag[index2]);
					prpLcomponent.setIfRemain(DataUtils.nullToZero(prpLcomponentIfRemain[index2]));
					// reason: 增加保存理算退回的定损的标志的保存,若有数据不会被保存冲掉
					prpLcomponent.setCompensateBackFlag(prpLcomponentCompensateBackFlag[index2]);
					// add by lixiang end at 2006-04-21
					prpLcomponentList.add(prpLcomponent);
				}
			}
			PrpLverifyLoss tempPrpLverifyLoss = certainLossDto.getPrpLverifyLoss();
			PropertyUtils.copyProperties(LossItemRepairComponent.getId(), tempPrpLverifyLoss.getId());
			PropertyUtils.copyProperties(LossItemRepairComponent.getId(), tempPrpLverifyLoss);
			LossItemRepairComponent.getId().setSerialNo(1);
			LossItemRepairComponent.getId().setLossType("1");
			LossItemRepairComponent.getId().setNodeType("certa");
			lossItemListTemp.add(LossItemRepairComponent);
		}
		// 定损车辆表
		certainLossDto.setPrpLcarLossList(prpLcarLossList);
		certainLossDto.setPrpLrepairFeeList(prpLrepairFeeList);
		certainLossDto.setPrpLcomponentList(prpLcomponentList);

		/*---------------------人员伤亡明细信息表 prpLperson ------------------------------------*/
		List<PrpLperson> prpLpersonList = new ArrayList<PrpLperson>();
		PrpLperson prpLperson = null;
		// 从界面得到输入数组
		String prpLpersonPolicyNo = httpServletRequest.getParameter("prpLverifyLossPolicyNo");
		String prpLpersonRiskCode = httpServletRequest.getParameter("prpLverifyLossRiskCode");
		String prpLpersonRegistNo = httpServletRequest.getParameter("prpLverifyLossRegistNo");
		
		String[] prpLpersonPersonNo = httpServletRequest.getParameterValues("prpLpersonPersonNo");
		String[] personSerialNo = httpServletRequest.getParameterValues("personSerialNo");
		String[] prpLpersonSerialNo = httpServletRequest.getParameterValues("prpLpersonSerialNo");
		String[] prpLpersonItemKindNo = httpServletRequest.getParameterValues("prpLpersonItemKindNo");
		String[] prpLpersonFamilyNo = httpServletRequest.getParameterValues("prpLpersonFamilyNo");
		String[] familyName = httpServletRequest.getParameterValues("familyName");
		String[] prpLpersonKindCode = httpServletRequest.getParameterValues("prpLpersonKindCode");
		String[] prpLpersonItemCode = httpServletRequest.getParameterValues("prpLpersonItemCode");
		String[] prpLpersonAreaCode = httpServletRequest.getParameterValues("prpLpersonAreaCode");
		String[] prpLpersonFixedIncomeFlag = httpServletRequest.getParameterValues("prpLpersonFixedIncomeFlag");
		String[] prpLpersonJobCode = httpServletRequest.getParameterValues("prpLpersonJobCode");
		String[] prpLpersonJobName = httpServletRequest.getParameterValues("prpLpersonJobName");
		String[] prpLpersonPayPersonType = httpServletRequest.getParameterValues("prpLpersonPayPersonType");
		String[] prpLpersonFeeTypeCode = httpServletRequest.getParameterValues("prpLpersonFeeTypeCode");
		String[] prpLpersonFeeTypeName = httpServletRequest.getParameterValues("prpLpersonFeeTypeName");
		String[] prpLpersonPersonName = httpServletRequest.getParameterValues("prpLpersonPersonName");
		String[] prpLpersonPersonSex = httpServletRequest.getParameterValues("prpLpersonPersonSex");

		String[] prpLpersonJobUnit = httpServletRequest.getParameterValues("prpLpersonJobUnit");
		String[] prpLpersonMonthStdWage = httpServletRequest.getParameterValues("prpLpersonMonthStdWage");
		String[] prpLpersonMonthBonus = httpServletRequest.getParameterValues("prpLpersonMonthBonus");
		String[] prpLpersonAllowance = httpServletRequest.getParameterValues("prpLpersonAllowance");
		String[] prpLpersonMonthWage = httpServletRequest.getParameterValues("prpLpersonMonthWage");
		String[] prpLpersonHospital = httpServletRequest.getParameterValues("prpLpersonHospital");
		String[] prpLpersonNursePersons = httpServletRequest.getParameterValues("prpLpersonNursePersons");
		String[] prpLpersonNurseDays = httpServletRequest.getParameterValues("prpLpersonNurseDays");
		String[] prpLpersonDiagnose = httpServletRequest.getParameterValues("prpLpersonDiagnose");
		String[] prpLpersonWoundGrade = httpServletRequest.getParameterValues("prpLpersonWoundGrade");
		String[] prpLpersonHospitalDays = httpServletRequest.getParameterValues("prpLpersonHospitalDays");
		String[] prpLpersonCureDays = httpServletRequest.getParameterValues("prpLpersonCureDays");
		String[] prpLpersonChangeHospital = httpServletRequest.getParameterValues("prpLpersonChangeHospital");
		String[] prpLpersonPersonAge = httpServletRequest.getParameterValues("prpLpersonPersonAge");
		String[] prpLpersonCurrency = httpServletRequest.getParameterValues("prpLpersonCurrency");
		String[] prpLpersonIdentifyNumber = httpServletRequest.getParameterValues("prpLpersonIdentifyNumber");
		String[] prpLpersonRelatePersonNo = httpServletRequest.getParameterValues("prpLpersonRelatePersonNo");
		String[] prpLpersonUnit = httpServletRequest.getParameterValues("prpLpersonUnit");
		String[] prpLpersonTimes = httpServletRequest.getParameterValues("prpLpersonTimes");
		String[] prpLpersonSumLoss = httpServletRequest.getParameterValues("prpLpersonSumLoss");
		String[] prpLpersonSumReject = httpServletRequest.getParameterValues("prpLpersonSumReject");
		String[] prpLpersonRejectReason = httpServletRequest.getParameterValues("prpLpersonRejectReason");
		String[] prpLpersonLossRate = httpServletRequest.getParameterValues("prpLpersonLossRate");
		String[] prpLpersonSumDefLoss = httpServletRequest.getParameterValues("prpLpersonSumDefLoss");
		String[] prpLpersonRemark = httpServletRequest.getParameterValues("prpLpersonRemark");
		String[] prpLpersonVeriQuantity = httpServletRequest.getParameterValues("prpLpersonVeriQuantity");
		String[] prpLpersonVeriUnitLoss = httpServletRequest.getParameterValues("prpLpersonVeriUnitLoss");
		String[] prpLpersonVeriUnit = httpServletRequest.getParameterValues("prpLpersonVeriUnit");
		String[] prpLpersonVeriTimes = httpServletRequest.getParameterValues("prpLpersonVeriTimes");
		String[] prpLpersonVeriSumLoss = httpServletRequest.getParameterValues("prpLpersonVeriSumLoss");
		String[] prpLpersonVeriSumReject = httpServletRequest.getParameterValues("prpLpersonVeriSumReject");
		String[] prpLpersonVeriRejectReason = httpServletRequest.getParameterValues("prpLpersonVeriRejectReason");
		String[] prpLpersonVeriLossRate = httpServletRequest.getParameterValues("prpLpersonVeriLossRate");
		String[] prpLpersonVeriSumDefLoss = httpServletRequest.getParameterValues("prpLpersonVeriSumDefLoss");
		String[] prpLpersonVeriRemark = httpServletRequest.getParameterValues("prpLpersonVeriRemark");
		String[] prpLpersonFlag = httpServletRequest.getParameterValues("prpLpersonFlag");
		// add by lixiang start at 2006-04-21
		// reason: 增加保存理算退回的定损的标志的保存,若有数据不会被保存冲掉
		String[] prpLpersonCompensateBackFlag = httpServletRequest.getParameterValues("prpLpersonCompensateBackFlag");
		// add by lixiang end at 2006-04-21

		// modify by wangli add start 20050401
		// reason:保存继医情况说明 等
		String[] prpLpersonFllowHospRemark = httpServletRequest.getParameterValues("prpLpersonFllowHospRemark");// 继医情况说明
		String[] prpLpersonInHospDate = httpServletRequest.getParameterValues("prpLpersonInHospDate"); // 入院日期
		String[] prpLpersonOutHospDate = httpServletRequest.getParameterValues("prpLpersonOutHospDate"); // 出院日期
		String[] prpLpersonRestDate = httpServletRequest.getParameterValues("prpLpersonRestDate"); // 定残日期

		// modify by wangli add end 20050401
		// 对象赋值
		if (personSerialNo != null) {
			for (int index = 1; index < personSerialNo.length; index++) {
				prpLperson = new PrpLperson();
				prpLperson.setPolicyNo(prpLpersonPolicyNo);
				prpLperson.setRiskCode(prpLpersonRiskCode);
				prpLperson.setClaimNo(claimNo);
				prpLperson.getId().setRegistNo(prpLpersonRegistNo);
				prpLperson.getId().setSerialNo(index);
				prpLperson.setFeeTypeCode(prpLpersonFeeTypeCode[index]);
				prpLperson.setFeeTypeName(prpLpersonFeeTypeName[index]);
				prpLperson.setSumLoss(Double.parseDouble(DataUtils.nullToZero(prpLpersonSumLoss[index])));
				prpLperson.setSumReject(Double.parseDouble(DataUtils.nullToZero(prpLpersonSumReject[index])));
				prpLperson.setSumDefLoss(Double.parseDouble(DataUtils.nullToZero(prpLpersonSumDefLoss[index])));
				prpLperson.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonItemKindNo[index])));
				prpLperson.setFamilyNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonFamilyNo[index])));
				prpLperson.setItemCode(prpLpersonItemCode[index]);
				prpLperson.setUnit(prpLpersonUnit[index]);
				prpLperson.setTimes(Double.parseDouble(DataUtils.nullToZero(prpLpersonTimes[index])));
				prpLperson.setRejectReason(prpLpersonRejectReason[index]);
				prpLperson.setVeriQuantity(Double.parseDouble(DataUtils.nullToZero(prpLpersonVeriQuantity[index])));
				prpLperson.setVeriUnitLoss(Double.parseDouble(DataUtils.nullToZero(prpLpersonVeriUnitLoss[index])));
				prpLperson.setVeriUnit(prpLpersonVeriUnit[index]);
				prpLperson.setVeriTimes(Double.parseDouble(DataUtils.nullToZero(prpLpersonVeriTimes[index])));
				prpLperson.setVeriSumLoss(Double.parseDouble(DataUtils.nullToZero(prpLpersonVeriSumLoss[index])));
				prpLperson.setVeriSumReject(Double.parseDouble(DataUtils.nullToZero(prpLpersonVeriSumReject[index])));
				prpLperson.setVeriRejectReason(prpLpersonVeriRejectReason[index]);
				prpLperson.setVeriLossRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonVeriLossRate[index])));
				prpLperson.setVeriSumDefLoss(Double.parseDouble(DataUtils.nullToZero(prpLpersonVeriSumDefLoss[index])));
				prpLperson.setVeriRemark(prpLpersonVeriRemark[index]);
				prpLperson.setFlag(prpLpersonFlag[index]);
				prpLperson.setCompensateBackFlag(prpLpersonCompensateBackFlag[index]);
				
				// ===================================================================
				for (int index2 = 0; index2 < prpLpersonSerialNo.length; index2++) {
					if (prpLpersonSerialNo[index2].equals(personSerialNo[index])) {
						prpLperson.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index2]));
						prpLperson.setFamilyName(familyName[index2]);
						prpLperson.setAreaCode(prpLpersonAreaCode[index2]);
						prpLperson.setFixedIncomeFlag(prpLpersonFixedIncomeFlag[index2]);
						prpLperson.setJobCode(prpLpersonJobCode[index2]);
						prpLperson.setJobName(prpLpersonJobName[index2]);
						prpLperson.setPayPersonType(prpLpersonPayPersonType[index2]);
						prpLperson.setPersonName(prpLpersonPersonName[index2]);
						prpLperson.setPersonSex(prpLpersonPersonSex[index2]);
						prpLperson.setKindCode(prpLpersonKindCode[index2]);
						// ===========================================================
						// zhangshi 简化循环内代码，registDto =
						// uiRegistAction.findByPrimaryKey(prpLcarLossRegistNo);在前面已得到结果
						// 20080509
						if (!"".equals(prpLregist.getPolicyNo())) {
							for (int k = 0; k < prpCitemKindList.size(); k++) {
								prpCitemKind = prpCitemKindList.get(k);
								if (prpCitemKind.getKindCode().equals(prpLperson.getKindCode())) {
									prpLperson.setItemKindNo(prpCitemKind.getId().getItemKindNo());
									break;
								}
							}
						}
						prpLperson.setJobUnit(prpLpersonJobUnit[index2]);
						prpLperson.setMonthStdWage(Double.parseDouble(DataUtils.nullToZero(prpLpersonMonthStdWage[index2])));
						prpLperson.setMonthBonus(Double.parseDouble(DataUtils.nullToZero(prpLpersonMonthBonus[index2])));
						prpLperson.setAllowance(Double.parseDouble(DataUtils.nullToZero(prpLpersonAllowance[index2])));
						prpLperson.setMonthWage(Double.parseDouble(DataUtils.nullToZero(prpLpersonMonthWage[index2])));
						prpLperson.setHospital(prpLpersonHospital[index2]);
						prpLperson.setNursePersons(Integer.parseInt(DataUtils.nullToZero(prpLpersonNursePersons[index2])));
						prpLperson.setNurseDays(Integer.parseInt(DataUtils.nullToZero(prpLpersonNurseDays[index2])));
						prpLperson.setDiagnose(prpLpersonDiagnose[index2]);
						prpLperson.setWoundGrade(prpLpersonWoundGrade[index2]);
						prpLperson.setHospitalDays(Integer.parseInt(DataUtils.nullToZero(prpLpersonHospitalDays[index2])));
						prpLperson.setCureDays(Integer.parseInt(DataUtils.nullToZero(prpLpersonCureDays[index2])));
						prpLperson.setChangeHospital(prpLpersonChangeHospital[index2]);
						prpLperson.setPersonAge(Integer.parseInt(DataUtils.nullToZero(prpLpersonPersonAge[index2])));
						prpLperson.setCurrency(prpLpersonCurrency[index2]);
						prpLperson.setIdentifyNumber(prpLpersonIdentifyNumber[index2]);
						prpLperson.setRelatePersonNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonRelatePersonNo[index2])));
						prpLperson.setLossRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossRate[index2])));
						prpLperson.setRemark(prpLpersonRemark[index2]);
						prpLperson.setFllowHospRemark(prpLpersonFllowHospRemark[index2]);
						prpLperson.setInHospDate(new DateTime(prpLpersonInHospDate[index2], DateTime.YEAR_TO_DAY));
						prpLperson.setOutHospDate(new DateTime(prpLpersonOutHospDate[index2], DateTime.YEAR_TO_DAY));
						prpLperson.setRestDate(new DateTime(prpLpersonRestDate[index2], DateTime.YEAR_TO_DAY));
					}
				}
				// 加入集合
				prpLpersonList.add(prpLperson);
			}
			PropertyUtils.copyProperties(LossItemPerson.getId(), certainLossDto.getPrpLverifyLoss().getId());
			PropertyUtils.copyProperties(LossItemPerson.getId(), certainLossDto.getPrpLverifyLoss());
			LossItemPerson.getId().setSerialNo(0);
			LossItemPerson.getId().setLossType("0");
			LossItemPerson.getId().setNodeType("wound");
			lossItemListTemp.add(LossItemPerson);
		}
		// 财产核定损明细清单表
		certainLossDto.setPrpLpersonList(prpLpersonList);
		certainLossDto.setPrpLverifyLossItemList(lossItemListTemp);
		/*---------------------伤情信息表 PrpLpersonWound ------------------------------------*/
		List<PrpLpersonWound> prpLpersonWoundList = new ArrayList<PrpLpersonWound>();
		PrpLpersonWound prpLpersonWound = null;

		// 从界面得到输入数组
		String[] woundCodeCheck001Txt = httpServletRequest.getParameterValues("woundCodeCheck001Txt");
		String[] woundCodeCheck002Txt = httpServletRequest.getParameterValues("woundCodeCheck002Txt");
		String[] woundCodeCheck003Txt = httpServletRequest.getParameterValues("woundCodeCheck003Txt");
		String[] woundCodeCheck004Txt = httpServletRequest.getParameterValues("woundCodeCheck004Txt");
		String[] woundCodeCheck005Txt = httpServletRequest.getParameterValues("woundCodeCheck005Txt");
		String[] woundCodeCheck006Txt = httpServletRequest.getParameterValues("woundCodeCheck006Txt");
		String[] woundCodeCheck007Txt = httpServletRequest.getParameterValues("woundCodeCheck007Txt");
		String[] woundCodeCheck008Txt = httpServletRequest.getParameterValues("woundCodeCheck008Txt");
		String[] woundCodeCheck009Txt = httpServletRequest.getParameterValues("woundCodeCheck009Txt");
		String[] woundCodeCheck010Txt = httpServletRequest.getParameterValues("woundCodeCheck010Txt");
		String[] woundCodeCheck011Txt = httpServletRequest.getParameterValues("woundCodeCheck011Txt");
		String[] woundCodeCheck012Txt = httpServletRequest.getParameterValues("woundCodeCheck012Txt");
		String[] woundCodeCheck013Txt = httpServletRequest.getParameterValues("woundCodeCheck013Txt");
		String[] woundCodeCheck014Txt = httpServletRequest.getParameterValues("woundCodeCheck014Txt");
		String[] woundCodeCheck015Txt = httpServletRequest.getParameterValues("woundCodeCheck015Txt");
		String[] woundCodeCheck016Txt = httpServletRequest.getParameterValues("woundCodeCheck016Txt");
		String[] woundCodeCheck017Txt = httpServletRequest.getParameterValues("woundCodeCheck017Txt");
		String[] woundCodeCheck018Txt = httpServletRequest.getParameterValues("woundCodeCheck018Txt");
		String[] woundCodeCheck019Txt = httpServletRequest.getParameterValues("woundCodeCheck019Txt");
		String[] woundCodeCheck020Txt = httpServletRequest.getParameterValues("woundCodeCheck020Txt");
		String[] woundCodeCheck021Txt = httpServletRequest.getParameterValues("woundCodeCheck021Txt");
		String[] woundCodeCheck022Txt = httpServletRequest.getParameterValues("woundCodeCheck022Txt");
		String[] woundCodeCheck023Txt = httpServletRequest.getParameterValues("woundCodeCheck023Txt");
		String[] woundCodeCheck024Txt = httpServletRequest.getParameterValues("woundCodeCheck024Txt");

		// 取得伤情损伤的代码信息
		List<PrpDcode> woundCodeList = (ArrayList<PrpDcode>) this.codeService.getCodeType("WoundCode", null);
		Map<String, String> woundCodeMap = new HashMap<String, String>();
		for (int i = 0; i < woundCodeList.size(); i++) {
			PrpDcode prpDcode = (PrpDcode) woundCodeList.get(i);
			woundCodeMap.put(prpDcode.getId().getCodeCode(), prpDcode.getCodeCName());
		}
		int intSerialNo = 1;
		if (woundCodeCheck001Txt != null) {
			for (int index = 1; index < woundCodeCheck001Txt.length; index++) {
				intSerialNo = 1;
				if (woundCodeCheck001Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("001");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("001"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck002Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("002");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("002"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck003Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("003");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("003"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck004Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("004");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("004"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck005Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("005");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("005"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck006Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("006");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("006"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck007Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("007");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("007"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck008Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("008");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("008"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck009Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("009");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("009"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck010Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("010");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("010"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck011Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("011");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("011"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck012Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("012");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("012"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck013Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("013");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("013"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck014Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("014");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("014"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck015Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("015");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("015"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck016Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("016");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("016"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck017Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("017");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("017"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck018Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("018");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("018"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck019Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("019");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("019"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck020Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("020");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("020"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck021Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("021");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("021"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck022Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("022");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("022"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck023Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("023");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("023"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
				if (woundCodeCheck024Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(certainLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(claimNo);
					prpLpersonWound.setPolicyNo(certainLossDto.getPrpLverifyLoss().getPolicyNo());
					prpLpersonWound.getId().setSerialNo(intSerialNo);
					prpLpersonWound.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index]));
					prpLpersonWound.setPersonName(prpLpersonPersonName[index]);
					prpLpersonWound.setWoundCode("024");
					prpLpersonWound.setWoundDesc((String) woundCodeMap.get("024"));
					prpLpersonWound.setFlag("");
					prpLpersonWoundList.add(prpLpersonWound);
				}
				intSerialNo++;
			}
		}
		certainLossDto.setPrpLpersonWoundList(prpLpersonWoundList);

		/*---------------------定核损信息补充说明 PrpLverifyLossExt ------------------------------------*/

		// 从界面得到输入数组
		String prpLverifyLossExtRegistNo = (String) httpServletRequest.getParameter("prpLverifyLossExtRegistNo");
		String prpLverifyLossExtRiskCode = httpServletRequest.getParameter("prpLverifyLossExtRiskCode");
		String[] prpLverifyLossExtSerialNo = httpServletRequest.getParameterValues("prpLverifyLossExtSerialNo");
		String[] prpLverifyLossExtInputDate = httpServletRequest.getParameterValues("prpLverifyLossExtInputDate");
		String[] prpLverifyLossExtInputHour = httpServletRequest.getParameterValues("prpLverifyLossExtInputHour");
		String[] prpLverifyLossExtOperatorCode = httpServletRequest.getParameterValues("prpLverifyLossExtOperatorCode");
		String[] prpLverifyLossExtTitle = httpServletRequest.getParameterValues("prpLverifyLossExtTitle");
		String[] prpLverifyLossExtContext = httpServletRequest.getParameterValues("prpLverifyLossExtContext");
		String prpLverifyLossExtLossItemCode = httpServletRequest.getParameter("prpLcarLossLossItemCode");

		// 对象赋值
		// 人员伤亡跟踪 部分开始
		if (prpLverifyLossExtSerialNo != null) {
			List<PrpLverifyLossExt> prpLverifyLossExtList = new ArrayList<PrpLverifyLossExt>();
			PrpLverifyLossExt prpLverifyLossExt = null;
			for (int index = 1; index < prpLverifyLossExtSerialNo.length; index++) {
				prpLverifyLossExt = new PrpLverifyLossExt();
				prpLverifyLossExt.getId().setRegistNo(prpLverifyLossExtRegistNo);
				prpLverifyLossExt.setRiskCode(prpLverifyLossExtRiskCode);
				prpLverifyLossExt.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLverifyLossExtSerialNo[index])));
				prpLverifyLossExt.setInputDate(new DateTime(prpLverifyLossExtInputDate[index], DateTime.YEAR_TO_DAY));
				prpLverifyLossExt.setInputHour(prpLverifyLossExtInputHour[index]);
				prpLverifyLossExt.setOperatorCode(prpLverifyLossExtOperatorCode[index]);
				prpLverifyLossExt.setTitle(prpLverifyLossExtTitle[index]);
				prpLverifyLossExt.setContext(prpLverifyLossExtContext[index]);
				prpLverifyLossExt.getId().setLossItemCode(prpLverifyLossExtLossItemCode);
				// 加入集合
				prpLverifyLossExtList.add(prpLverifyLossExt);
			}
			// 报案集合中加入损失部位
			certainLossDto.setPrpLverifyLossExtList(prpLverifyLossExtList);
		}
		/*---------------------报案信息补充说明 PrpLregistExt ------------------------------------*/
		// 从界面得到输入数组
		String prpLregistExtRegistNo = (String) httpServletRequest.getParameter("prpLregistExtRegistNo");
		String prpLregistExtRiskCode = httpServletRequest.getParameter("prpLregistExtRiskCode");
		String[] prpLregistExtSerialNo = httpServletRequest.getParameterValues("prpLregistExtSerialNo");
		String[] prpLregistExtInputDate = httpServletRequest.getParameterValues("prpLregistExtInputDate");
		String[] prpLregistExtInputHour = httpServletRequest.getParameterValues("prpLregistExtInputHour");
		String[] prpLregistExtOperatorCode = httpServletRequest.getParameterValues("prpLregistExtOperatorCode");
		String[] prpLregistExtContext = httpServletRequest.getParameterValues("prpLregistExtContext");
		// 对象赋值
		// 人员伤亡跟踪 部分开始
		if (prpLregistExtSerialNo != null) {
			List<PrpLregistExt> prpLregistExtList = new ArrayList<PrpLregistExt>();
			PrpLregistExt prpLregistExt = null;
			// 人员伤亡跟踪部分开始;
			for (int index = 1; index < prpLregistExtSerialNo.length; index++) {
				prpLregistExt = new PrpLregistExt();
				prpLregistExt.getId().setRegistNo(prpLregistExtRegistNo);
				prpLregistExt.setRiskCode(prpLregistExtRiskCode);
				prpLregistExt.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLregistExtSerialNo[index])));
				prpLregistExt.setInputDate(new DateTime(prpLregistExtInputDate[index], DateTime.YEAR_TO_DAY));
				prpLregistExt.setInputHour(prpLregistExtInputHour[index]);
				prpLregistExt.setOperatorCode(prpLregistExtOperatorCode[index]);
				prpLregistExt.setContext(prpLregistExtContext[index]);
				// 加入集合
				prpLregistExtList.add(prpLregistExt);
			}
			// 报案集合中加入损失部位
			certainLossDto.setPrpLregistExtList(prpLregistExtList);
		}
		return certainLossDto;
	}

	/**
	 * 新定损查询
	 * @Description:
	 * @author 中科软
	 * @param httpServletRequest
	 * @param workFlowQueryDto
	 * @param intPageNo
	 * @param intRecordPerPage
	 * @return
	 * @throws Exception
	 */
	public Page setPrpLcertainLossDtoToView(HttpServletRequest httpServletRequest, WorkFlowQueryDto workFlowQueryDto, int intPageNo, int intRecordPerPage) throws Exception {
		String registNo = StringUtils.rightTrim(workFlowQueryDto.getRegistNo());
		String policyNo = StringUtils.rightTrim(workFlowQueryDto.getPolicyNo());
		String licenseNo = StringUtils.rightTrim(workFlowQueryDto.getLicenseNo());
		String status = StringUtils.rightTrim(workFlowQueryDto.getStatus());
		String operateDate = StringUtils.rightTrim(workFlowQueryDto.getOperateDate());
		String insuredName = StringUtils.rightTrim(workFlowQueryDto.getInsuredName());
		String conditions = " 1=1 ";
		conditions = conditions + StringConvert.convertString("a.registNo", registNo, workFlowQueryDto.getRegistNoSign());
		conditions = conditions + StringConvert.convertString("d.policyNo", policyNo, workFlowQueryDto.getPolicyNoSign());
		conditions = conditions + StringConvert.convertString("a.lossItemName", licenseNo, workFlowQueryDto.getLicenseNoSign());
		conditions = conditions + StringConvert.convertString("c.insuredName", insuredName, workFlowQueryDto.getInsuredNameSign());
		if (status.trim().length() > 0) {
			conditions = conditions + " AND b.status in (" + status + ")";
		}
		if (operateDate != null && !operateDate.trim().equals("")) {
			conditions = conditions + StringConvert.convertDate("b.operateDate", operateDate, workFlowQueryDto.getOperateDateSign());
		}
		String strNodeType = httpServletRequest.getParameter("nodeType");
		if (strNodeType != null) {
			strNodeType = StringUtils.rightTrim(strNodeType);
		}
		String nodeType = CommonUtils.getCertainNodeType(strNodeType);
		String condition = httpServletRequest.getParameter("condition");
		String searchFlag = httpServletRequest.getParameter("searchFlag");
		if (!"true".equals(searchFlag)) {
			if (condition != null && condition.trim().length() > 0) {
				conditions = condition;
			}
		}
		conditions = conditions + " AND a.nodeType ='"+nodeType+"'";
		return this.getCertainLossService().findByQueryConditions(conditions, intPageNo, intRecordPerPage);
	}

	/**
	 * 生成定损信息详细画面
	 * @param httpServletRequest 返回给页面的request
	 * @param businessNo 业务号码
	 * @param editType 编辑类型
	 * @throws Exception
	 */
	public void certainLossDtoView(HttpServletRequest httpServletRequest, String registNo, String editType) throws Exception {
		// 标的序号，如果是人伤为0，否则为1，2，3，4，5等车辆序号
		String lossItemCode = httpServletRequest.getParameter("lossItemCode");
		// 取得当前用户信息，写操作员信息到定损中
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String riskCode = httpServletRequest.getParameter("riskCode");
		// 非车险，lossItemCode默认为1,这样就可以正常进行财产险详细的删除操作了
		String strRiskType = this.codeService.translateRiskCodetoRiskType(riskCode);
		if (riskCode != null && !"D".equals(strRiskType)) {
			lossItemCode = "1";
		}
		String nodeType = httpServletRequest.getParameter("nodeType");
		CertainLossDto certainLossDto = this.getCertainLossService().findByPrimaryKey(registNo.trim(), lossItemCode,nodeType);
		// 根据查询出来的数据内容，给PrpLverifyLossDto赋值
		PrpLverifyLoss prpLverifyLoss = certainLossDto.getPrpLverifyLoss();
		// 设置定损操作的状态为 案件修改 (正处理任务)
		// add for 涉案车辆 定损处可修改
		PrpLthirdParty prpLthirdParty1 = certainLossDto.getPrpLthirdParty();
		if (prpLthirdParty1 == null) {
			prpLthirdParty1 = new PrpLthirdParty();
		}
		httpServletRequest.setAttribute("prpLthirdParty1", prpLthirdParty1);
		if (prpLverifyLoss == null) {
			// reason :没有定损信息的话，需要提示出来。。
			String msg = "";
			msg = "未發現備案號爲：" + registNo.trim() + ",序號" + lossItemCode + "的定損數據,可能是進行了註銷拒賠申請！";
			throw new UserException(1, 3, "定損查詢", msg);
		}
		PrpLclaimStatus prpLclaimStatus = certainLossDto.getPrpLclaimStatus();
		if (prpLclaimStatus != null) {
			if (prpLclaimStatus.getStatus().equals("7")) {
				prpLclaimStatus.setStatus("3");
			}
			prpLverifyLoss.setStatus(prpLclaimStatus.getStatus());
		} else {
			// 已提交，已经处理完毕的状态
			prpLverifyLoss.setStatus("4");
		}
		String nodeStatus = httpServletRequest.getParameter("status");
		if (nodeStatus != null) {
			prpLverifyLoss.setStatus(nodeStatus);
		}
		RegistDto registDto = this.getRegistService().findByPrimaryKey(registNo.trim());
		PrpLregist prpLregist = registDto.getPrpLregist();
		// 属性条款类别
		prpLverifyLoss.setClauseType(prpLregist.getClauseType());
		List<PrpLcarLoss> prpLcarLossList = certainLossDto.getPrpLcarLossList();
		if (prpLcarLossList != null && !prpLcarLossList.isEmpty()) {
			PrpLcarLoss prpLcarLoss = null;
			PrpLthirdParty prpLthirdParty = null;
			List<PrpLthirdParty> prpLthirdPartyList = registDto.getPrpLthirdPartyList();
			for (int i = 0; i < prpLcarLossList.size(); i++) {
				prpLcarLoss = prpLcarLossList.get(i);
				for (int j = 0; j < prpLthirdPartyList.size(); j++) {
					prpLthirdParty = prpLthirdPartyList.get(j);
					if (prpLcarLoss.getId().getLossItemCode().equals(String.valueOf(prpLthirdParty.getId().getSerialNo()))) {
						prpLcarLoss.setLicenseColorCode(prpLthirdParty.getLicenseColorCode());
						prpLcarLoss.setCarKindCode(prpLthirdParty.getCarKindCode());
						prpLcarLoss.setBrandName(prpLthirdParty.getBrandName());
						prpLcarLoss.setModelCode(prpLthirdParty.getModelCode());
						prpLcarLoss.setEngineNo(prpLthirdParty.getEngineNo());
						prpLcarLoss.setFrameNo(prpLthirdParty.getFrameNo());
						prpLcarLoss.setVINNo(prpLthirdParty.getVINNo());
						prpLcarLoss.setInsureCarFlag(prpLthirdParty.getInsureCarFlag());
						prpLcarLoss.setInsureComCode(prpLthirdParty.getInsureComCode());
						prpLcarLoss.setInsureComName(prpLthirdParty.getInsureComName());
					}
				}
			}
		}
		// 查询相同保单号的出险次数
		this.getDaaRegistViewHelper().getSamePolicyRegistInfo(httpServletRequest, prpLverifyLoss.getPolicyNo(), prpLverifyLoss.getId().getRegistNo());
		// 设置相关代码的中文转换
		changeCodeToName(httpServletRequest, prpLverifyLoss);
		changeCodeToName(httpServletRequest, certainLossDto);
		// 设置窗体表单中各个多选框中列表信息的内容
		setSelectionList(httpServletRequest, registNo);
		// 设置定损信息内容到窗体表单
		if (prpLverifyLoss.getClaimNo() == null || prpLverifyLoss.getClaimNo().equals("")) {
			prpLverifyLoss.setClaimNo(daaClaimViewHelper.getLossClaimNo(registNo));
		}
		httpServletRequest.setAttribute("prpLverifyLoss", prpLverifyLoss);
		httpServletRequest.setAttribute("certainLossDto", certainLossDto);
		/*
		 * 理赔组 //取得查勘的信息 UICheckAction uiCheckAction = new UICheckAction();
		 * CheckDto checkDto = uiCheckAction.findByPrimaryKey(registNo);
		 * httpServletRequest.setAttribute("prpLcheckDto",
		 * checkDto.getPrpLcheckDto());
		 */
		// 取得相关主表的信息
		CaseRelateNodeDto caseRelateNodeDto = this.getRegistService().relateNode(registNo);
		PrpLcheck prpLcheckTemp1 = caseRelateNodeDto.getPrpLcheck();
		if (prpLcheckTemp1 == null) {
			prpLcheckTemp1 = new PrpLcheck();
		}
		httpServletRequest.setAttribute("prpLcheckTemp", prpLcheckTemp1);
		PrpLregist prpLregistTemp1 = caseRelateNodeDto.getPrpLregist();
		String timeTemp = StringConvert.toStandardTime(prpLregistTemp1.getDamageStartHour());
		prpLregistTemp1.setDamageStartMinute(timeTemp.substring(3, 5));
		prpLregistTemp1.setDamageStartHour(timeTemp.substring(0, 2));
		httpServletRequest.setAttribute("prpLregist", prpLregistTemp1);
		// Reason:得到修理类型列表
		String strRiskCode = BusinessRuleUtil.getRiskCode(registNo, "RegistNo");
		List<PrpDcode> repairTypes = this.codeService.getCodeType("RepairType", strRiskCode);
		httpServletRequest.setAttribute("repairTypes", repairTypes);
		// 查询核价权限
		String taskCode = AppConfig.get("sysconst.TASKCODE_LPDS"); // 任务代码为定损
		// 工时费合计
		PrpLclaimGrade prpLclaimGrade1 = this.getPrpLclaimGradeService().findPrpLclaimGrade(new PrpLclaimGradeId(user.getUserCode(), taskCode, "SUM_WORK_HOUR_FEE"));
		// 换件费合计
		PrpLclaimGrade prpLclaimGrade2 = this.getPrpLclaimGradeService().findPrpLclaimGrade(new PrpLclaimGradeId(user.getUserCode(), taskCode, "SUM_CHANGE_COMP_FEE"));

		httpServletRequest.setAttribute("prpLclaimGrade1", prpLclaimGrade1);
		httpServletRequest.setAttribute("prpLclaimGrade2", prpLclaimGrade2);

		httpServletRequest.setAttribute("verifyPriceOpinionList", ICollections.getVerifyPriceOpinionList());
		// Reason:增加核损意见
		httpServletRequest.setAttribute("verifyOpinionList", ICollections.getVerifyOpinionList());

		// 设置各个子表信息项到窗体表单
		setSubInfo(httpServletRequest, certainLossDto);

		// 设置工作流下一个节点提交的配置信息
		getSubmitNodes(httpServletRequest);
	}

	/**
	 * 填写定损页面及查询定损request的生成.
	 * @param httpServletRequest 返回给页面的request
	 * @param businessNo 业务号码
	 * @param editType 编辑类型
	 * @throws Exception
	 */
	public void registDtoToView(HttpServletRequest httpServletRequest, String businessNo, String editType) throws Exception {

		// 标的序号，如果是人伤为0，否则为1，2，3，4，5等车辆序号
		String lossItemCode = DataUtils.nullToZero(httpServletRequest.getParameter("lossItemCode"));
		//如果是人伤，查询出调度号码，人伤有多个，lossItemCode没有办法区分是哪个人。
		// Reason:得到修理类型列表
		String strRiskCode = BusinessRuleUtil.getRiskCode(businessNo, "RegistNo");
		Collection<PrpDcode> repairTypes = this.codeService.getCodeType("RepairType", strRiskCode);
		httpServletRequest.setAttribute("repairTypes", repairTypes);
		// 转换,无论有没有，都取一次
		String claimNo = this.codeService.translateBusinessCode(businessNo, true);
		// 判断原来的定损是否存在
		String nodeType = httpServletRequest.getParameter("nodeType");
		CertainLossDto certainLossDto = this.getCertainLossService().findByPrimaryKey(businessNo, lossItemCode,nodeType);
		CertainLossDto certainLossDto1 = certainLossDto;
		httpServletRequest.setAttribute("certainLossDto1", certainLossDto1);
		PrpLthirdParty prpLthirdParty1 = certainLossDto.getPrpLthirdParty();
		PrpLverifyLoss prpLverifyLoss = certainLossDto.getPrpLverifyLoss();
		if (prpLthirdParty1 == null && prpLverifyLoss != null) {
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.registNo", businessNo.trim());
			queryRule.addEqual("licenseNo", prpLverifyLoss.getLicenseNo());
			List<PrpLthirdParty> collection = this.getPrpLthirdPartyService().findPrpLthirdParty(queryRule);
			if (collection != null && collection.size() > 0) {
				prpLthirdParty1 = collection.get(0);
			}
		}
		if (prpLthirdParty1 == null) {
			prpLthirdParty1 = new PrpLthirdParty();
		}
		if (prpLthirdParty1 != null) {
			httpServletRequest.setAttribute("prpLthirdParty1", prpLthirdParty1);
		}
		RegistDto registDto = this.getRegistService().findByPrimaryKey(businessNo.trim());
		PrpLregist prplregist = registDto.getPrpLregist();
		// 如果是已经存在的定损
		if (certainLossDto.getPrpLverifyLoss() != null) {
			prpLverifyLoss = certainLossDto.getPrpLverifyLoss();
			// 设置定损操作的状态为 案件修改 (正处理任务)
			prpLverifyLoss.setClaimNo(claimNo);
			PrpLclaimStatus prpLclaimStatus = certainLossDto.getPrpLclaimStatus();
			if (prpLclaimStatus != null) {
				if (prpLclaimStatus.getStatus().equals("7")) {
					prpLclaimStatus.setStatus("3");
				}
				prpLverifyLoss.setStatus(prpLclaimStatus.getStatus());
			} else {
				// 已提交，已经处理完毕的状态
				prpLverifyLoss.setStatus("1");
			}
			String nodeStatus = httpServletRequest.getParameter("status");
			if (nodeStatus != null) {
				prpLverifyLoss.setStatus(nodeStatus);
			}
			// 修理换件的车辆是否存在
			PrpLthirdParty prpLthirdParty = null;
			PrpLcarLoss prpLcarLoss = null;
			if (certainLossDto.getPrpLcarLossList() == null || certainLossDto.getPrpLcarLossList().size() < 1) {
				UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
				String policyNo = prplregist.getPolicyNo();
				List<PrpLcarLoss> prpLcarLossList1 = new ArrayList<PrpLcarLoss>();
				List<PrpLthirdParty> prpLthirdPartyList = registDto.getPrpLthirdPartyList();
				if (prpLthirdPartyList != null && !prpLthirdPartyList.isEmpty()) {
					for (int i = 0; i < prpLthirdPartyList.size(); i++) {
						prpLthirdParty = prpLthirdPartyList.get(i);
						// 取得定前车辆的信息
						if ((String.valueOf(prpLthirdParty.getId().getSerialNo())).equals(lossItemCode.trim())) {
							prpLcarLoss = new PrpLcarLoss();
							prpLcarLoss.getId().setRegistNo(prpLthirdParty.getId().getRegistNo());
							// 此处需要一个根据报案号码查询关联的赔案号码的转换，管李香要
							prpLcarLoss.setClaimNo(claimNo);
							prpLcarLoss.setRiskCode(prpLthirdParty.getRiskCode());
							prpLcarLoss.getId().setLossItemCode(String.valueOf(i + 1));
							prpLcarLoss.setLossItemName(prpLthirdParty.getLicenseNo());
							prpLcarLoss.setPolicyNo(policyNo);
							prpLcarLoss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
							prpLcarLoss.setLossDesc("");
							prpLcarLoss.setOperatorCode(user.getUserCode());
							prpLcarLoss.setLicenseColorCode(prpLthirdParty.getLicenseColorCode());
							prpLcarLoss.setCarKindCode(prpLthirdParty.getCarKindCode());
							prpLcarLoss.setBrandName(prpLthirdParty.getBrandName());
							prpLcarLoss.setModelCode(prpLthirdParty.getModelCode());
							prpLcarLoss.setEngineNo(prpLthirdParty.getEngineNo());
							prpLcarLoss.setFrameNo(prpLthirdParty.getFrameNo());
							prpLcarLoss.setVINNo(prpLthirdParty.getVINNo());
							prpLcarLoss.setInsureCarFlag(prpLthirdParty.getInsureCarFlag());
							prpLcarLoss.setInsureComCode(prpLthirdParty.getInsureComCode());
							prpLcarLoss.setInsureComName(prpLthirdParty.getInsureComName());
							prpLcarLoss.setFlag(prpLthirdParty.getFlag());
							prpLcarLossList1.add(prpLcarLoss);
						}
					}
				}
				certainLossDto.setPrpLcarLossList(prpLcarLossList1);
			} else {
				// carLoss不为空的时候
				List<PrpLcarLoss> prpLcarLossList = certainLossDto.getPrpLcarLossList();
				List<PrpLthirdParty> prpLthirdPartyList = registDto.getPrpLthirdPartyList();
				for (int i = 0; i < prpLcarLossList.size(); i++) {
					prpLcarLoss = prpLcarLossList.get(i);
					for (int j = 0; j < prpLthirdPartyList.size(); j++) {
						prpLthirdParty = prpLthirdPartyList.get(j);
						if (prpLcarLoss.getId().getLossItemCode().equals(String.valueOf(prpLthirdParty.getId().getSerialNo()))) {
							prpLcarLoss.setLicenseColorCode(prpLthirdParty.getLicenseColorCode());
							prpLcarLoss.setCarKindCode(prpLthirdParty.getCarKindCode());
							prpLcarLoss.setBrandName(prpLthirdParty.getBrandName());
							prpLcarLoss.setModelCode(prpLthirdParty.getModelCode());
							prpLcarLoss.setEngineNo(prpLthirdParty.getEngineNo());
							prpLcarLoss.setFrameNo(prpLthirdParty.getFrameNo());
							prpLcarLoss.setVINNo(prpLthirdParty.getVINNo());
							prpLcarLoss.setInsureCarFlag(prpLthirdParty.getInsureCarFlag());
							prpLcarLoss.setInsureComCode(prpLthirdParty.getInsureComCode());
							prpLcarLoss.setInsureComName(prpLthirdParty.getInsureComName());
						}
					}
				}
			}
		} else {
			prpLverifyLoss = new PrpLverifyLoss();
			String policyNo = prplregist.getPolicyNo();
			// 定损表
			certainLossDto = new CertainLossDto();
			prpLverifyLoss.getId().setRegistNo(prplregist.getRegistNo());
			// 此处需要一个根据报案号码查询关联的赔案号码的转换，管李香要
			prpLverifyLoss.setClaimNo(claimNo);
			prpLverifyLoss.setRiskCode(prplregist.getRiskCode());
			prpLverifyLoss.setPolicyNo(prplregist.getPolicyNo());
			prpLverifyLoss.setInsuredName(prplregist.getInsuredName());
			prpLverifyLoss.setLicenseNo(prplregist.getLicenseNo());
			prpLverifyLoss.setClauseType(prplregist.getClauseType());
			prpLverifyLoss.getId().setLossItemCode(httpServletRequest.getParameter("lossItemCode"));
			prpLverifyLoss.getId().setNodeType(nodeType);
			prpLverifyLoss.setLossItemName(httpServletRequest.getParameter("lossItemName"));
			prpLverifyLoss.setInsureCarFlag(httpServletRequest.getParameter("insureCarFlag"));
			prpLverifyLoss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
			prpLverifyLoss.setMakeCom(prplregist.getMakeCom());
			prpLverifyLoss.setComCode(prplregist.getComCode());

			HttpSession session = httpServletRequest.getSession();
			UserDto user = (UserDto) session.getAttribute("user");
			prpLverifyLoss.setHandlerCode(user.getUserCode());
			prpLverifyLoss.setHandlerName(user.getUserName());
			prpLverifyLoss.setDefLossDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));
			prpLverifyLoss.setFlag(prplregist.getFlag());
			List<PrpLthirdParty> prpLthirdPartyList = registDto.getPrpLthirdPartyList();
			if (!prplregist.getPolicyNo().equals("")) {
				// 查询保单信息
				String damageDate = new DateTime(prplregist.getDamageStartDate()).toString();
				String damageHour = prplregist.getDamageStartHour();
				PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate , damageHour);
				List<PrpCitemCar> prpCitemCarList = this.endorseViewHelper.findPrpCitemCar(policyNo, damageDate, damageHour);
				PrpCitemCar prpCitemCar = null;
				if (!CommonUtils.isEmpty(prpCitemCarList)) {
					// 对车型等信息的支持
					prpCitemCar = prpCitemCarList.get(0);
					prpLverifyLoss.setLicenseNo(prpCitemCar.getLicenseNo());
					prpLverifyLoss.setLossItemName(prpCitemCar.getLicenseNo());
					prpLverifyLoss.setLicenseColorCode(prpCitemCar.getLicenseColorCode());
					prpLverifyLoss.setCarKindCode(prpCitemCar.getCarKindCode());
				}
				if (prpCmain != null) {
					prpLverifyLoss.setCurrency(prpCmain.getCurrency());
				}
			}
			List<PrpLcarLoss> prpLcarLossDtoList1 = new ArrayList<PrpLcarLoss>();
			
			if (prpLthirdPartyList != null && !prpLthirdPartyList.isEmpty()) {
				PrpLcarLoss prpLcarLoss = null;
				PrpLthirdParty prpLthirdParty = null;
				for (int i = 0; i < prpLthirdPartyList.size(); i++) {
					prpLthirdParty = prpLthirdPartyList.get(i);
					if ((String.valueOf(prpLthirdParty.getId().getSerialNo())).equals(lossItemCode.trim())) {
						prpLcarLoss = new PrpLcarLoss();
						prpLcarLoss.getId().setRegistNo(prpLthirdParty.getId().getRegistNo());
						// 此处需要一个根据报案号码查询关联的赔案号码的转换，管李香要
						prpLcarLoss.setClaimNo(claimNo);
						prpLcarLoss.setRiskCode(prpLthirdParty.getRiskCode());
						prpLcarLoss.getId().setLossItemCode(String.valueOf(i + 1));
						prpLcarLoss.setLossItemName(prpLthirdParty.getLicenseNo());
						prpLcarLoss.setPolicyNo(policyNo);
						prpLcarLoss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
						prpLcarLoss.setLossDesc("");
						prpLcarLoss.setOperatorCode(user.getUserCode());
						prpLcarLoss.setLicenseColorCode(prpLthirdParty.getLicenseColorCode());
						prpLcarLoss.setCarKindCode(prpLthirdParty.getCarKindCode());
						prpLcarLoss.setBrandName(prpLthirdParty.getBrandName());
						prpLcarLoss.setModelCode(prpLthirdParty.getModelCode());
						prpLcarLoss.setEngineNo(prpLthirdParty.getEngineNo());
						prpLcarLoss.setFrameNo(prpLthirdParty.getFrameNo());
						prpLcarLoss.setVINNo(prpLthirdParty.getVINNo());
						prpLcarLoss.setInsureCarFlag(prpLthirdParty.getInsureCarFlag());
						prpLcarLoss.setInsureComCode(prpLthirdParty.getInsureComCode());
						prpLcarLoss.setInsureComName(prpLthirdParty.getInsureComName());

						prpLcarLoss.setFlag(prpLthirdParty.getFlag());
						prpLcarLossDtoList1.add(prpLcarLoss);
					}
				}
			}
			certainLossDto.setPrpLcarLossList(prpLcarLossDtoList1);
			certainLossDto.setPrpLregistExtList(registDto.getPrpLregistExtList());
			// 设置实赔操作的状态为 新案件登记 (未处理任务)
			prpLverifyLoss.setStatus("1");
		}
		// 查询相同保单号的出险次数
		this.getDaaRegistViewHelper().getSamePolicyRegistInfo(httpServletRequest, prpLverifyLoss.getPolicyNo(), prpLverifyLoss.getId().getRegistNo());
		// 设置相关代码的中文转换
		changeCodeToName(httpServletRequest, prpLverifyLoss);
		changeCodeToName(httpServletRequest, certainLossDto);
		// 设置窗体表单中各个多选框中列表信息的内容
		setSelectionList(httpServletRequest, businessNo);
		if (claimNo == null || claimNo.equals("")) {
			prpLverifyLoss.setClaimNo(this.daaClaimViewHelper.getLossClaimNo(businessNo));
		}
		// 设置定损信息内容到窗体表单
		httpServletRequest.setAttribute("prpLverifyLoss", prpLverifyLoss);
		httpServletRequest.setAttribute("prpLregist", prplregist);
		httpServletRequest.setAttribute("certainLossDto", certainLossDto);

		// 这句话出错了（容易出现空指针错误） wangli 给注释掉了
		// 取得相关主表的信息
		CaseRelateNodeDto caseRelateNodeDto = this.getRegistService().relateNode(businessNo);
		PrpLcheck prpLcheckTemp1 = caseRelateNodeDto.getPrpLcheck();
		if (prpLcheckTemp1 == null) {
			prpLcheckTemp1 = new PrpLcheck();
		}
		prpLcheckTemp1.setClaimNo(claimNo);
		httpServletRequest.setAttribute("prpLcheckTemp", prpLcheckTemp1);
		PrpLregist prpLregistTemp1 = caseRelateNodeDto.getPrpLregist();
		String timeTemp = StringConvert.toStandardTime(prpLregistTemp1.getDamageStartHour());
		prpLregistTemp1.setDamageStartMinute(timeTemp.substring(3, 5));
		prpLregistTemp1.setDamageStartHour(timeTemp.substring(0, 2));
		httpServletRequest.setAttribute("prpLregist", prpLregistTemp1);

		certainLossDto = this.translateTraceToPerson(httpServletRequest, certainLossDto, businessNo);
		// modify by wangli add end 20050401
		// 设置各个子表信息项到窗体表单
		certainLossDto.setPrpLverifyLoss(prpLverifyLoss);
		setSubInfo(httpServletRequest, certainLossDto);
		// Modify by chenrenda update begin 20050419
		// Reason:根据员工代码取出相应理赔业务权限
		UserDto prpLclaimUser = (UserDto) httpServletRequest.getSession().getAttribute("user");
		String strPrpLclaimGradeUserCode = prpLclaimUser.getUserCode();
		String strPrpLclaimTaskCode = "certa"; // 任务代码为定损
		PrpLclaimGrade prpLclaimGrade = this.getPrpLclaimGradeService().findPrpLclaimGrade(new PrpLclaimGradeId(strPrpLclaimGradeUserCode, strPrpLclaimTaskCode, "ALL"));
		httpServletRequest.setAttribute("prpLclaimGrade", prpLclaimGrade);
		// Modify by chenrenda update begin 20050419
		// 查询核价权限
		String taskCode = AppConfig.get("sysconst.TASKCODE_LPDS"); // 任务代码为定损
		// 工时费合计
		PrpLclaimGrade prpLclaimGrade1 = this.getPrpLclaimGradeService().findPrpLclaimGrade(new PrpLclaimGradeId(strPrpLclaimGradeUserCode, taskCode, "SUM_WORK_HOUR_FEE"));
		// 换件费合计
		PrpLclaimGrade prpLclaimGrade2 = this.getPrpLclaimGradeService().findPrpLclaimGrade(new PrpLclaimGradeId(strPrpLclaimGradeUserCode, taskCode, "SUM_CHANGE_COMP_FEE"));
		httpServletRequest.setAttribute("prpLclaimGrade1", prpLclaimGrade1);
		httpServletRequest.setAttribute("prpLclaimGrade2", prpLclaimGrade2);
		httpServletRequest.setAttribute("verifyPriceOpinionList", ICollections.getVerifyPriceOpinionList());
		// Reason:增加核损意见
		httpServletRequest.setAttribute("verifyOpinionList", ICollections.getVerifyOpinionList());
		// 设置工作流下一个节点提交的配置信息
		getSubmitNodes(httpServletRequest);
	}

	/**
	 * 根据赔案号和报案号查询定损信息
	 * @param httpServletRequest 返回给页面的request
	 * @param businessNo 赔案号
	 * @throws Exception
	 */
	public void setPrpLcertainLossDtoToView(HttpServletRequest httpServletRequest, String registNo, String policyNo) throws Exception {
		// caseNO,policyNo,claimNo
		// 根据输入的保单号，定损号生成SQL where 子句
		registNo = StringUtils.rightTrim(registNo);
		policyNo = StringUtils.rightTrim(policyNo);
		// 得到多行定损主表信息
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addLike("id.registNo", "%" + registNo + "%");
		queryRule.addLike("policyNo", "%" + policyNo + "%");
		List<PrpLverifyLoss> prpLverifyLossList = this.getPrpLverifyLossService().findPrpLverifyLoss(queryRule);
		PrpLverifyLoss prpLverifyLoss = new PrpLverifyLoss();
		prpLverifyLoss.setVerifyLossList(prpLverifyLossList);
		prpLverifyLoss.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLverifyLoss", prpLverifyLoss);
	}

	/**
	 * 获取选择框和列表框中的所有内容
	 * @param httpServletRequest 返回给页面的request
	 * @param prpLcaseNoDto 定损的数据类
	 * @throws Exception
	 */
	private void setSelectionList(HttpServletRequest httpServletRequest, String registNo) throws Exception {

		// 得到费用名称的列表
		List<LabelValueBean> FeeTypeCodeList = new ArrayList<LabelValueBean>();
		FeeTypeCodeList.add(new LabelValueBean("修理費", "01"));
		FeeTypeCodeList.add(new LabelValueBean("材料費", "02"));
		httpServletRequest.setAttribute("FeeTypeCodeList", FeeTypeCodeList);

		// 得到收入情况的列表
		List<LabelValueBean> FixedIncomeFlagList = new ArrayList<LabelValueBean>();
		FixedIncomeFlagList.add(new LabelValueBean("有固定收入", "1"));
		FixedIncomeFlagList.add(new LabelValueBean("無固定收入", "2"));
		FixedIncomeFlagList.add(new LabelValueBean("無收入", "3"));
		FixedIncomeFlagList.add(new LabelValueBean("無勞動能力", "4"));
		httpServletRequest.setAttribute("FixedIncomeFlagList", FixedIncomeFlagList);

		// 得到人员类型的列表
		List<LabelValueBean> PayPersonTypeList = new ArrayList<LabelValueBean>();
		PayPersonTypeList.add(new LabelValueBean("傷", "1"));
		PayPersonTypeList.add(new LabelValueBean("被撫養人", "2"));
		PayPersonTypeList.add(new LabelValueBean("護理人", "3"));
		PayPersonTypeList.add(new LabelValueBean("殘", "4"));
		PayPersonTypeList.add(new LabelValueBean("亡", "5"));
		PayPersonTypeList.add(new LabelValueBean("參加事故處理人員", "6"));
		httpServletRequest.setAttribute("PayPersonTypeList", PayPersonTypeList);

		// 得到号牌号码的列表
		List<?> LicenseNoList = this.codeService.getLicenseNoList(registNo);
		httpServletRequest.setAttribute("LicenseNoList", LicenseNoList);

		// 修理廠類型
		httpServletRequest.setAttribute("feeRepairFactoryCodeList", ConstantsCollection.feeRepairFactoryCodeList);

		// 是否回收
		httpServletRequest.setAttribute("ifRemainList", ConstantsCollection.ifRemainList);
		// 傷勢程度
		httpServletRequest.setAttribute("woundGradeList", ConstantsCollection.woundGradeList);
		// 是否需要轉院治療
		httpServletRequest.setAttribute("changeHospitalList", ConstantsCollection.changeHospitalList);
		// 案件狀態
		httpServletRequest.setAttribute("exigenceGreeList", ConstantsCollection.exigenceGreeList);
	}

	/**
	 * 根据PrpPrepayDto中的已经设置的代码内容，对代码进行名称转换
	 * @param httpServletRequest 返回给页面的request
	 * @param prpLcaseNoDto 定损的数据类
	 * @throws Exception
	 */
	private void changeCodeToName(HttpServletRequest httpServletRequest, PrpLverifyLoss prpLverifyLoss) throws Exception {
		// 号牌颜色转换
		String licenseColorCodeCode = prpLverifyLoss.getLicenseColorCode();
		String licenseColor = this.codeService.translateCodeCode("LicenseColor", licenseColorCodeCode, true);
		prpLverifyLoss.setLicenseColor(licenseColor);
		// 车辆类型转换
		String carKindCode = prpLverifyLoss.getCarKindCode();
		String carKind = this.codeService.translateCodeCode("CarKind", carKindCode, true);
		prpLverifyLoss.setCarKind(carKind);
		// 条款名称的转换
		String clauseType = prpLverifyLoss.getClauseType();
		String clauseName = this.codeService.translateCodeCode("ClauseType", clauseType, true);
		prpLverifyLoss.setClauseName(clauseName);

	}

	/**
	 * 根据PrpPrepayDto中的已经设置的代码内容，对代码进行名称转换
	 * @param httpServletRequest 返回给页面的request
	 * @param prpLcaseNoDto 定损的数据类
	 * @throws Exception
	 */
	private void changeCodeToName(HttpServletRequest httpServletRequest, CertainLossDto certainLossDto) throws Exception {
		List<PrpLprop> prpLpropList = certainLossDto.getPrpLpropList();
		if (prpLpropList != null && !prpLpropList.isEmpty()) {
			PrpLprop prpLprop = null;
			for (int i = 0; i < prpLpropList.size(); i++) {
				prpLprop = prpLpropList.get(i);
				prpLprop.setFeeTypeName(this.codeService.translateCodeCode("PropertyFeeType", prpLprop.getFeeTypeCode(), true));
				if (ConstantCodes.KINDCODE_D_BZ.equals(prpLprop.getKindCode())) {
					prpLprop.setKindName(this.codeService.translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAZ"), prpLprop.getKindCode(), true));
				} else {
					prpLprop.setKindName(this.codeService.translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"), prpLprop.getKindCode(), true));
				}
				prpLprop.setCurrencyName(this.codeService.translateCurrencyCode(prpLprop.getCurrency(), true));
			}
		}
		List<PrpLcarLoss> prpLcarLossList = certainLossDto.getPrpLcarLossList();
		if (prpLcarLossList != null && !prpLcarLossList.isEmpty()) {
			PrpLcarLoss prpLcarLoss = null;
			for (int i = 0; i < prpLcarLossList.size(); i++) {
				prpLcarLoss = prpLcarLossList.get(i);
				// 是否为本保单车辆转换
				if (prpLcarLoss.getInsureCarFlag().trim().equals("1")) {
					prpLcarLoss.setInsureCarFlagName("是");
				} else {
					prpLcarLoss.setInsureCarFlagName("否");
				}
				// 车辆种类
				prpLcarLoss.setCarKindName(this.codeService.translateCodeCode("CarKind", prpLcarLoss.getCarKindCode(), true));
			}
		}
		List<PrpLrepairFee> prpLrepairFeeList = certainLossDto.getPrpLrepairFeeList();
		if (prpLrepairFeeList != null && !prpLrepairFeeList.isEmpty()) {
			// 险别名称转换
			for (PrpLrepairFee prpLrepairFee : prpLrepairFeeList) {
				prpLrepairFee.setHandlerName(this.codeService.translateUserCode(prpLrepairFee.getHandlerCode(), true));
				if (ConstantCodes.KINDCODE_D_BZ.equals(prpLrepairFee.getKindCode())) {
					prpLrepairFee.setKindName(this.codeService.translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAZ"), prpLrepairFee.getKindCode(), true));
				} else {
					prpLrepairFee.setKindName(this.codeService.translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"), prpLrepairFee.getKindCode(), true));
				}
			}
		}
		List<PrpLcomponent> prpLcomponentList = certainLossDto.getPrpLcomponentList();
		if (prpLcomponentList != null && !prpLcomponentList.isEmpty()) {
			// 险别名称转换
			for (PrpLcomponent prpLcomponent : prpLcomponentList) {
				prpLcomponent.setHandlerName(this.codeService.translateUserCode(prpLcomponent.getHandlerCode(), true));
				if (ConstantCodes.KINDCODE_D_BZ.equals(prpLcomponent.getKindCode())) {
					prpLcomponent.setKindName(this.codeService.translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAZ"), prpLcomponent.getKindCode(), true));
				} else {
					prpLcomponent.setKindName(this.codeService.translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"), prpLcomponent.getKindCode(), true));
				}
			}
		}
		List<PrpLperson> prpLpersonList = certainLossDto.getPrpLpersonList();
		if (prpLpersonList != null && !prpLpersonList.isEmpty()) {
			for (PrpLperson prpLperson : prpLpersonList) {
				if (ConstantCodes.KINDCODE_D_BZ.equals(prpLperson.getKindCode())) {
					prpLperson.setKindName(this.codeService.translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAZ"), prpLperson.getKindCode(), true));
				} else {
					prpLperson.setKindName(this.codeService.translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"), prpLperson.getKindCode(), true));
				}
				prpLperson.setItemName("test");
				// prplperson表中有存入的FeeTypeName，不用转化，有时候由於codetype不一样，可能转换有错。
				prpLperson.setCurrencyName(this.codeService.translateCurrencyCode(prpLperson.getCurrency(), true));
				prpLperson.setAreaName(this.codeService.translateCodeCode("DamageAreaCode", prpLperson.getAreaCode(), true));
			}
		}

	}

	/**
	 * 取初始化信息需要的数据的整理. 填写定损单时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等。取这些信息需要一些入参，
	 * 考虑到接口的一致性，将这些入参作为Dto方式传入，Dto利用聚合而非继承的方式。 整理采用继承的方式分层处理，具体的逻辑放在险种险类子类中整理.
	 * @param httpServletRequest
	 * @throws Exception
	 */
	public CertainLossDto iniViewToDto(HttpServletRequest httpServletRequest) throws Exception {
		CertainLossDto certainLossDto = new CertainLossDto();
		return certainLossDto;
	}

	/**
	 * 填写定损页面及查询定损request的生成.
	 * @param httpServletRequest 返回给页面的request
	 * @param certainLossDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public void dtoToView(HttpServletRequest httpServletRequest, CertainLossDto certainLossDto) throws Exception {
	}

	/**
	 * 根据certainLossDto中的各子表内的信息填充界面
	 * @param httpServletRequest 返回给页面的request
	 * @param certainLossDto 定损的数据类
	 * @throws Exception
	 */
	private void setSubInfo(HttpServletRequest httpServletRequest, CertainLossDto certainLossDto) throws Exception {
		// Modify by chenrenda add begin 20050413
		httpServletRequest.setAttribute("partCodeList", ICollections.getPartCodeList());

		PrpLverifyLoss prpLverifyLoss = certainLossDto.getPrpLverifyLoss();
		String strRiskType = codeService.translateRiskCodetoRiskType(prpLverifyLoss.getRiskCode());
		// 给定核损信息补充说明多行列表准备数据
		PrpLverifyLossExt prpLverifyLossExt = new PrpLverifyLossExt();
		PrpLverifyLossExt prpLverifyLossExtAdd = new PrpLverifyLossExt();
		prpLverifyLossExtAdd.setTitle("定損完成");
		prpLverifyLossExtAdd.getId().setRegistNo(prpLverifyLoss.getId().getRegistNo());
		prpLverifyLossExtAdd.setRiskCode(prpLverifyLoss.getRiskCode());
		prpLverifyLossExtAdd.setInputDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));
		prpLverifyLossExtAdd.setInputHour(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_SECOND).getHour() + "時" + new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_SECOND).getMinute() + "分");
		prpLverifyLossExt.getId().setRegistNo(prpLverifyLoss.getId().getRegistNo());
		prpLverifyLossExt.setRiskCode(prpLverifyLoss.getRiskCode());

		List<PrpLverifyLossExt> arrayListVerifyLossExt = certainLossDto.getPrpLverifyLossExtList();
		if (arrayListVerifyLossExt == null) {
			arrayListVerifyLossExt = new ArrayList<PrpLverifyLossExt>();
		} else {
			for (PrpLverifyLossExt p : arrayListVerifyLossExt) {
				if (null != p.getOperatorCode() && !"".equals(p.getOperatorCode())) {
					p.setOperatorCodeName(this.codeService.translateUserCode(p.getOperatorCode(), true));
				}
			}
		}
		prpLverifyLossExtAdd.getId().setSerialNo(arrayListVerifyLossExt.size() + 1);
		String status = httpServletRequest.getParameter("status");
		if ("0".equals(status) || "3".equals(status)) {
			UserDto user = (UserDto)httpServletRequest.getSession().getAttribute("user");
			String operatorCode = user.getUserCode();
			String operatorName = user.getUserName();
			prpLverifyLossExtAdd.setOperatorCode(operatorCode);
			prpLverifyLossExtAdd.setOperatorCodeName(operatorName);
			arrayListVerifyLossExt.add(prpLverifyLossExtAdd);
		}
		prpLverifyLossExt.setVerifyLossExtList(arrayListVerifyLossExt);
		httpServletRequest.setAttribute("prpLverifyLossExt", prpLverifyLossExt);

		// 给报案信息补充说明多行列表准备数据

		PrpLregistExt prpLregistExt = new PrpLregistExt();
		prpLregistExt.getId().setRegistNo(prpLverifyLoss.getId().getRegistNo());
		prpLregistExt.setRiskCode(prpLverifyLoss.getRiskCode());
		List<PrpLregistExt> arrayListRegistExt = certainLossDto.getPrpLregistExtList();
		prpLregistExt.setRegistExtList(arrayListRegistExt);
		httpServletRequest.setAttribute("prpLregistExt", prpLregistExt);
		// 修理费用清单多行列表准备数据
		List<PrpLrepairFee> prpLrepairFeeList = certainLossDto.getPrpLrepairFeeList();
		if (prpLrepairFeeList != null) {
			for (PrpLrepairFee prpLrepairFee : prpLrepairFeeList) {
				prpLrepairFee.setPrpLrepairFeePartCode(prpLrepairFee.getPartCode());
			}
		}
		PrpLrepairFee prpLrepairFee = new PrpLrepairFee();
		// Modify by chenrenda 20050409 end
		prpLrepairFee.setRepairFeeList(prpLrepairFeeList);
		httpServletRequest.setAttribute("prpLrepairFee", prpLrepairFee);

		// 换件项目清单多行列表准备数据
		List<PrpLcomponent> prpLcomponentList = certainLossDto.getPrpLcomponentList();
		// Modify by chenrenda 20050409 begin
		if (prpLcomponentList != null) {
			for (PrpLcomponent prpLcomponent : prpLcomponentList) {
				prpLcomponent.setPrpLcomponentPartCode(prpLcomponent.getPartCode());
			}
		}
		// Modify by chenrenda 20050409 end
		PrpLcomponent prpLcomponent = new PrpLcomponent();
		prpLcomponent.setComponentList(prpLcomponentList);
		httpServletRequest.setAttribute("prpLcomponent", prpLcomponent);
		// 人员伤亡明细信息清单多行列表准备数据
		List<PrpLperson> prpLpersonList = certainLossDto.getPrpLpersonList();
		if (prpLpersonList != null && prpLpersonList.size() > 0) {
			for (PrpLperson prpLperson : prpLpersonList) {
				if (ConstantCodes.KINDCODE_D_BZ.equals(prpLperson.getKindCode())) {
					prpLperson.setKindName(this.codeService.translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAZ"), prpLperson.getKindCode(), true));
				} else {
					prpLperson.setKindName(this.codeService.translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"), prpLperson.getKindCode(), true));
				}
				// add by liuwei at 2011-02-15 获取一级行业和二级行业信息 start
				String jobCode = prpLperson.getJobCode();// 三级行业代码
				if (jobCode != null && !"".equals(jobCode)) {
					String jobCode1 = jobCode.substring(0, jobCode.length() - 2);// 一级行业代码
					String jobCode2 = jobCode.substring(0, jobCode.length() - 1);// 二级行业代码
					String conditions1 = "codecode='" + jobCode1 + "' and flag='1' and validstatus='1' and codetype='BusinessSource' AND codeEname like '%,"+strRiskType+",%' ";
					String conditions2 = "codecode='" + jobCode2 + "' and flag='2' and validstatus='1' and codetype='BusinessSource' AND codeEname like '%,"+strRiskType+",%' ";
					List<PrpDcode> collection1 = prpDcodeService.findByConditions(conditions1);

					for (Iterator<PrpDcode> it = collection1.iterator(); it.hasNext();) {
						PrpDcode prpDcode = (PrpDcode) it.next();
						String jobName1 = prpDcode.getCodeCName();
						prpLperson.setJobCode1(jobCode1);
						prpLperson.setJobName1(jobName1);
					}
					List<PrpDcode> collection2 = prpDcodeService.findByConditions(conditions2);
					for (Iterator<PrpDcode> it = collection2.iterator(); it.hasNext();) {
						PrpDcode prpDcode = (PrpDcode) it.next();
						String jobName2 = prpDcode.getCodeCName();
						prpLperson.setJobCode2(jobCode2);
						prpLperson.setJobName2(jobName2);
					}
				}
				// add by liuwei at 2011-02-15 获取一级行业和二级行业信息 end
			}
		}
		PrpLperson prpLperson = new PrpLperson();
		prpLperson.setPersonList(prpLpersonList);
		httpServletRequest.setAttribute("prpLperson", prpLperson);
		// 财产核定损明细清单多行列表准备数据
		List<PrpLprop> prpLpropList = certainLossDto.getPrpLpropList();
		if (prpLpropList == null) {
			prpLpropList = new ArrayList<PrpLprop>();
			CertainLossDto certainLossDto1 = (CertainLossDto) httpServletRequest.getAttribute("certainLossDto1");
			PrpLthirdProp prpLthirdProp = null;
			PrpLcheckLoss prplcheckloss = null;
			if (certainLossDto1 != null) {
				List<PrpLthirdProp> prplThirdPropList = certainLossDto1.getPrpLthirdpropList();
				List<PrpLcheckLoss> prplCheckLossList = certainLossDto1.getPrpLchecklossList();
				if (prplThirdPropList != null && prplThirdPropList.size() > 0) {
					PrpLprop prpLprop = null;
					for (int i = 0; i < prplThirdPropList.size(); i++) {
						prpLprop = new PrpLprop();
						prpLthirdProp = (PrpLthirdProp) prplThirdPropList.get(i);
						if (prplCheckLossList != null && prplCheckLossList.size() == prplThirdPropList.size()) {
							prplcheckloss = prplCheckLossList.get(i);
						} else {
							prplcheckloss = new PrpLcheckLoss();
						}
						prpLprop.setLossItemName(prpLthirdProp.getLossItemName());
						prpLprop.setKindCode(prplcheckloss.getKindCode());
						if (ConstantCodes.KINDCODE_D_BZ.equals(prplcheckloss.getKindCode())) {
							prpLprop.setKindName(this.codeService.translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAZ"), prplcheckloss.getKindCode(), true));
						} else {
							prpLprop.setKindName(this.codeService.translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"), prplcheckloss.getKindCode(), true));
						}
						prpLprop.setSumLoss(prplcheckloss.getLossFee());
						prpLprop.setSumDefLoss(prplcheckloss.getLossFee());
						prpLprop.setCurrency(ConstantCodes.LOCAL_CURRENCY);
						prpLpropList.add(prpLprop);
					}
				}
			}
		}
		PrpLprop prpLprop = new PrpLprop();
		prpLprop.setPropList(prpLpropList);
		httpServletRequest.setAttribute("prpLprop", prpLprop);

		// 伤情信息表 多行列表准备数据
		PrpLpersonWound prpLpersonWound = new PrpLpersonWound();
		prpLpersonWound.setWoundList(certainLossDto.getPrpLpersonWoundList());
		httpServletRequest.setAttribute("prpLpersonWound", prpLpersonWound);
		// 理算退回意见
		Collection<LabelValueBean> compensateBackOptionsList = ICollections.getCompensateBackList();
		httpServletRequest.setAttribute("compensateBackOptionsList", compensateBackOptionsList);
		// add by lidonghui 2007-08-21 start 垫付赔案所走代码，保证按钮显示状态正确
		List<PrpLcarLoss> prpLcarLossList = certainLossDto.getPrpLcarLossList();
		if (prpLcarLossList != null && !prpLcarLossList.isEmpty()) {
			String registNo = prpLcarLossList.get(0).getId().getRegistNo();
			PrpLregist prpLregist = this.getPrpLregistService().findPrpLregist(registNo);
			String comCode = prpLregist.getComCode().substring(0, 2);
			PrpDriskConfig prpDriskConfig = prpDriskConfigService.findByPrimaryKey(comCode, prpLregist.getRiskCode(), "advance_case");
			if (prpDriskConfig != null && "1".equals(prpDriskConfig.getConfigValue())) {
				if ("1".equals(prpLregist.getAdvanceType())) {
					httpServletRequest.setAttribute("display1", "display:");
				} else {
					httpServletRequest.setAttribute("display1", "display:none");
				}
				httpServletRequest.setAttribute("advance", "1");
				httpServletRequest.setAttribute("advanceType", prpLregist.getAdvanceType());
			}
		}
	}

	/**
	 * 检查是否已出赔案计算书 返回值 true 已出 false 未出
	 * @param httpServletRequest 返回给页面的request
	 * @param claimNo 赔案号
	 * @throws Exception
	 */
	public boolean checkCompensate(HttpServletRequest httpServletRequest, String registNo) throws Exception {
		// 根据报案号码取得对应的赔案号码
		String claimNo = this.codeService.translateBusinessCode(registNo, true);
		// 取得赔款计算书信息
		String conditions = "claimNo ='" + claimNo.trim() + "'";
		List<PrpLcompensate> arraylist = compensateService.findByConditions(conditions);
		if (arraylist == null || arraylist.size() < 1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 检查是否已经核损了
	 * @param httpServletRequest 返回给页面的request
	 * @param claimNo 赔案号
	 * @throws Exception
	 */
	public boolean checkVerifyLoss(HttpServletRequest httpServletRequest, String registNo, String lossItemCode, String nodeType) throws Exception {
//		VerifyLossDto verifyLossDto = verifyLossService.findByPrimaryKey(registNo, lossItemCode, nodeType);
//		if (verifyLossDto.getPrpLclaimStatus() != null) {
//			return false;
//		} else {
//			return false;
//		}
		PrpLclaimStatusId prpLclaimStatusId = new PrpLclaimStatusId(registNo,CommonUtils.getVerifyNodeType(nodeType),Integer.valueOf(lossItemCode));
		PrpLclaimStatus prpLclaimStatus = prpLclaimStatusService.findPrpLclaimStatus(prpLclaimStatusId);
		if(prpLclaimStatus==null||"4".equals(prpLclaimStatus.getStatus())){
			return false;
		}
		return true;
	}

	/**
	 * 根据赔案号,报案号,案件状态，车牌号码，操作时间查询定损信息
	 * @param httpServletRequest 返回给页面的request
	 * @param businessNo 赔案号
	 * @throws Exception Modify By sunhao 2004-08-24 Reason:增加车牌号，案件状态，操作时间查询条件
	 */
	public void setPrpLcertainLossDtoToView(HttpServletRequest httpServletRequest, WorkFlowQueryDto workFlowQueryDto) throws Exception {

		String registNo = StringUtils.rightTrim(workFlowQueryDto.getRegistNo());
		String policyNo = StringUtils.rightTrim(workFlowQueryDto.getPolicyNo());
		String licenseNo = StringUtils.rightTrim(workFlowQueryDto.getLicenseNo());
		String status = StringUtils.rightTrim(workFlowQueryDto.getStatus());
		String operateDate = StringUtils.rightTrim(workFlowQueryDto.getOperateDate());
		String insuredName = StringUtils.rightTrim(workFlowQueryDto.getInsuredName());
		String conditions = " 1=1 ";
		conditions = conditions + StringConvert.convertString("a.registNo", registNo, workFlowQueryDto.getRegistNoSign());
		// add by zhouliu start at 2006-6-9
		// reason:强三查询
		conditions = conditions + StringConvert.convertString("d.policyNo", policyNo, workFlowQueryDto.getPolicyNoSign());
		// add by zhouliu end at 2006-6-9

		conditions = conditions + StringConvert.convertString("a.lossItemName", licenseNo, workFlowQueryDto.getLicenseNoSign());
		conditions = conditions + StringConvert.convertString("c.insuredName", insuredName, workFlowQueryDto.getInsuredNameSign());
		if (status.trim().length() > 0) {
			conditions = conditions + " AND b.status in (" + status + ")";
		}
		if (operateDate != null && !operateDate.trim().equals("")) {
			conditions = conditions + StringConvert.convertDate("b.operateDate", operateDate, workFlowQueryDto.getOperateDateSign());
		}
		// modify by zhaolu 20060816 start
		com.sinosoft.claim.ui.control.action.UIPowerInterface uiPowerInterface = new com.sinosoft.claim.ui.control.action.UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		conditions = conditions + uiPowerInterface.addPower(userDto, "a", "", "ComCode");
		// modify by zhaolu 20060816 end
		// Modify by caopeng add begin 20051215 Reason:使每种定损、核损查询只能查询本种类的数据
		String strNodeType = httpServletRequest.getParameter("nodeType");
		if (strNodeType != null) {
			strNodeType = StringUtils.rightTrim(strNodeType);
		}
		conditions = conditions + " and a.nodeType='"+CommonUtils.getCertainNodeType(strNodeType)+"'";
		// Modify by caopeng add end 20051216 Reason:使每种定损、核损查询只能查询本种类的数据
		// 查询立案信息
		// 得到多行定损主表信息
		List<PrpLverifyLoss> verifyLossList = this.getPrpLcertainLossService().findPrpLcetainLoss(conditions, 0, 0);
		// Modify by caopeng add begin 20051215 Reason:使每种核损查询只能查询本种类的数据
		if ("propv".equals(strNodeType) || "veriw".equals(strNodeType) || "verif".equals(strNodeType)) {
			PrpLclaimStatus prpLclaimStatus = null;
			for (PrpLverifyLoss prpLverifyLoss : verifyLossList) {
				prpLclaimStatus = this.getPrpLclaimStatusService().findPrpLclaimStatus(new PrpLclaimStatusId(prpLverifyLoss.getId().getRegistNo(), strNodeType, Integer.parseInt(prpLverifyLoss.getId().getLossItemCode())));
				if (prpLclaimStatus != null) {
					prpLverifyLoss.setStatus(prpLclaimStatus.getStatus());
				} else {
					prpLverifyLoss.setStatus("0");
				}
			}
		}
		// Modify by caopeng add end 20051216 Reason:使每种核损查询只能查询本种类的数据
		PrpLverifyLoss prpLverifyLossDto = new PrpLverifyLoss();
		prpLverifyLossDto.setVerifyLossList(verifyLossList);
		prpLverifyLossDto.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLverifyLoss", prpLverifyLossDto);
	}

	/**
	 * 查询工作流可以用来选择的节点内容
	 * @param modelNo String
	 * @param nodeNo String
	 * @throws Exception
	 */
	private void getSubmitNodes(HttpServletRequest httpServletRequest) throws Exception {
		String modelNo = httpServletRequest.getParameter("modelNo"); // 模板号
		String nodeNo = httpServletRequest.getParameter("nodeNo"); // 节点号
		int nextNodeNo = 0;
		List<SwfPath> pathList = new ArrayList<SwfPath>();
		SwfPath swfPath = new SwfPath();
		if (modelNo != null && nodeNo != null) {
			pathList = workFlowViewHelper.getNextSumbitNodes(modelNo, nodeNo);
			Iterator<SwfPath> it = pathList.iterator();
			if (it.hasNext()) {
				SwfPath swfPathDtoTemp = it.next();
				nextNodeNo = swfPathDtoTemp.getEndNodeNo();
				swfPath.setNextNodeNo(nextNodeNo);
			}
		}
		swfPath.setPathList(pathList);
		httpServletRequest.setAttribute("pathList", pathList);
		httpServletRequest.setAttribute("swfPath", swfPath);
	}

	// modify by wangli add start 20050401
	/**
	 * 把人伤跟踪信息保存到人伤DTO中 以在界面显示
	 * @param certainLossDto 定损DTO
	 * @param registNo 报案号
	 * @throws Exception
	 */
	private CertainLossDto translateTraceToPerson(HttpServletRequest httpServletRequest, CertainLossDto certainLossDto, String registNo) throws Exception {
		String status = httpServletRequest.getParameter("status");
		List<PrpLperson> prpLpersonList = certainLossDto.getPrpLpersonList();
		if (prpLpersonList == null) {
			prpLpersonList = new ArrayList<PrpLperson>();
		}
		if ("0".equals(status)) {
			String lossItemCode = DataUtils.nullToZero(httpServletRequest.getParameter("lossItemCode"));
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.registNo", registNo);
			queryRule.addEqual("id.personNo", Integer.valueOf(lossItemCode));
			List<PrpLpersonTrace> prpLpersonTraceList = this.getPrpLpersonTraceService().findPrpLpersonTrace(queryRule);
			PrpLperson prpLperson = null;
			PrpLregist prpLregist = this.getRegistService().findByPrimaryKey(registNo.trim()).getPrpLregist();
			String policyNo = prpLregist.getPolicyNo();
			String damageDate = new DateTime(prpLregist.getDamageStartDate()).toString();
			String damageHour = prpLregist.getDamageStartHour();
			List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, null , null);
			for (int index = 0;index<prpLpersonTraceList.size();index++) {
				PrpLpersonTrace prpLpersonTrace = prpLpersonTraceList.get(index);
				prpLperson = new PrpLperson();
				prpLperson.getId().setRegistNo(prpLpersonTrace.getId().getRegistNo());
				prpLperson.setClaimNo(prpLpersonTrace.getClaimNo());
				prpLperson.setPolicyNo(prpLpersonTrace.getPolicyNo());
				prpLperson.setKindCode(prpLpersonTrace.getReferKind());
				prpLperson.setPersonAge(prpLpersonTrace.getPersonAge());
				prpLperson.setPersonName(prpLpersonTrace.getPersonName());
				prpLperson.setPersonSex(prpLpersonTrace.getPersonSex());
				prpLperson.getId().setPersonNo(prpLpersonTrace.getId().getPersonNo());
				prpLperson.getId().setSerialNo(index+1);
				prpLperson.setJobCode(prpLpersonTrace.getJobCode());
				prpLperson.setJobName(prpLpersonTrace.getJobName());
				// reasion:增加就医医院
				prpLperson.setHospital(prpLpersonTrace.getHospital());
				prpLperson.setWoundGrade(prpLpersonTrace.getFlag());
				if (!CommonUtils.isEmpty(prpCitemKindList)) {
					for (PrpCitemKind prpCitemKind : prpCitemKindList) {
						if (prpCitemKind.getKindCode().equals(prpLpersonTrace.getReferKind())) {
							prpLperson.setItemKindNo(prpCitemKind.getId().getItemKindNo());
							break;
						}
					}
				}
				prpLpersonList.add(prpLperson);
			}
		}
		if (prpLpersonList != null) {
			certainLossDto.setPrpLpersonList(prpLpersonList);
		}
		return certainLossDto;

	}

	/**
	 * 查询车型
	 * @param httpServletRequest
	 * @throws Exception
	 */
	public void prpDcarModelDtoToView(HttpServletRequest httpServletRequest) throws Exception {
		List<PrpDcarModel> prpDcarModelDtoList = new ArrayList<PrpDcarModel>();
		String findType = httpServletRequest.getParameter("findType");
		String factory = httpServletRequest.getParameter("prpdCarModelFactory");
		String carBrand = httpServletRequest.getParameter("carModelBrand");
		String carSeriesName = httpServletRequest.getParameter("carModelSeriesName");
		String prpdCarModelBrand = httpServletRequest.getParameter("prpdCarModelBrand");
		String prpdCarModelSeriesName = httpServletRequest.getParameter("prpdCarModelSeriesName");
		PrpDcarModelDto prpDcarModelDto = new PrpDcarModelDto();
		prpDcarModelDto.setFactory(factory);
		prpDcarModelDto.setCarBrand(carBrand);
		prpDcarModelDto.setCarSeriesName(carSeriesName);
		StringBuffer conditions = new StringBuffer();
		if ("1".equals(findType)) {
			if ("".equals(factory) == false)
				conditions.append("factory='" + factory + "'");
			if ("0".equals(prpdCarModelBrand) == false)
				conditions.append("and carBrand='" + carBrand + "'");
			if ("0".equals(prpdCarModelSeriesName) == false)
				conditions.append("and carSeriesName='" + carSeriesName + "'");
			prpDcarModelDtoList = prpDcarModelService.findByConditions(conditions.toString());
		}
		if ("2".equals(findType)) {
			String iSpuerWherePart = "";
			String srhContext = "";
			String srhContextJPModel = "";
			String srhJPContext = "";
			String strCarModelSpellAb = "";
			String strCarModelId = "";
			srhJPContext = httpServletRequest.getParameter("JPModelName");
			srhContext = httpServletRequest.getParameter("SuperModelName");

			if (srhJPContext != null) {
				if (!srhJPContext.trim().equals("")) {
					srhContextJPModel = srhJPContext;
					// 判断是否有空格
					int td = srhContextJPModel.indexOf(" ");
					if (td == -1) {
						// 无空格
						strCarModelSpellAb = srhContextJPModel;
					} else {
						// 有空格
						String[] sstr = srhContextJPModel.split(" ");
						strCarModelSpellAb = sstr[0];
						for (int ss = 1; ss < sstr.length; ss++) {
							strCarModelId += sstr[ss];
						}
					}
				}
			}

			Pattern pen = Pattern.compile("[A-Z]+"); // 英文
			Pattern pnum = Pattern.compile("[0-9]+"); // 数字
			Pattern pch = Pattern.compile("[\u4E00-\u9FA5]+"); // 中文

			Matcher men = pen.matcher(srhContext);
			Matcher mch = pch.matcher(srhContext);
			Matcher mnum = pnum.matcher(srhContext);

			Matcher cmen = pen.matcher(strCarModelId);
			Matcher cmnum = pnum.matcher(strCarModelId);

			Matcher smen = pen.matcher(srhContextJPModel);

			int iii = 0;
			int j = 0;
			int k = 0;

			int cme = 0;
			int cmn = 0;
			int sme = 0;
			if (srhContext != null) {
				while (men != null && men.find()) {
					iii++;
				}

				while (mch != null && mch.find()) {
					j++;
				}

				while (mnum != null && mnum.find()) {
					k++;
				}
			}

			if (srhContextJPModel != null) {
				while (smen != null && smen.find()) {
					sme++;
				}
			}

			if (strCarModelId != null) {
				while (cmen != null && cmen.find()) {
					cme++;
				}

				while (cmnum != null && cmnum.find()) {
					cmn++;
				}
			}
			String[] letter = new String[iii];
			String[] chinese = new String[j];
			String[] number = new String[k];

			String[] celetter = new String[cme];
			String[] cmnumber = new String[cmn];
			String[] seletter = new String[sme];
			men = pen.matcher(srhContext);
			mch = pch.matcher(srhContext);
			mnum = pnum.matcher(srhContext);

			smen = pen.matcher(srhContextJPModel);
			cmen = pen.matcher(strCarModelId);
			cmnum = pnum.matcher(strCarModelId);

			if (srhContext == null) {
				iSpuerWherePart = "1<>1";
			} else {
				iSpuerWherePart = "1=1";
			}
			int m = 0;
			int n = 0;
			int v = 0;
			int cm = 0;
			int cv = 0;
			int sv = 0;
			// 拼凑车型(modelname)代码查询
			if (srhContext != null) {
				while (mch != null && mch.find()) {
					chinese[n] = mch.group();
					iSpuerWherePart += " and modelname like '%" + mch.group() + "%'";
					n++;
				}

				while (mnum != null && mnum.find()) {
					number[v] = mnum.group();
					iSpuerWherePart += " and modelname like '%" + mnum.group() + "%'";
					v++;
				}

				while (men != null && men.find()) {
					letter[m] = men.group();
					iSpuerWherePart += " and upper(modelname)  like '%" + men.group() + "%'";
					m++;
				}

			}
			// 拼凑(carmodelspellab)简拼SQL
			if (strCarModelSpellAb != null) {
				while (smen != null && smen.find()) {
					seletter[sv] = smen.group();
					iSpuerWherePart += " and upper(carmodelspellab) like '%" + smen.group() + "%'";
					sv++;
				}
			}
			// 拼凑(CarModelId)参数SQL
			if (strCarModelId != null) {
				while (cmnum != null && cmnum.find()) {
					cmnumber[cv] = cmnum.group();
					iSpuerWherePart += " and CarModelId like '%" + cmnum.group() + "%'";
					cv++;
				}

				while (cmen != null && cmen.find()) {
					celetter[cm] = cmen.group();
					iSpuerWherePart += " and upper(CarModelId)  like '%" + cmen.group() + "%'";
					cm++;
				}

			}
			if (srhContext != null) {
				prpDcarModelDtoList = prpDcarModelService.findByConditions(iSpuerWherePart);
			}

		}
		httpServletRequest.setAttribute("carModelList", prpDcarModelDtoList);

	}

	/** 代码service */
	private CodeService codeService;
	/** 定损信息service */
	private CertainLossService certainLossService;
	/** 报案信息service */
	private RegistService registService;
	/** 立案Service */
	private PrpLclaimService prpLclaimService;
	/** 理赔节点状态 Service */
	private PrpLclaimStatusService prpLclaimStatusService;
	/** 报案基本信息Service */
	private PrpLregistService prpLregistService;
	/** 定核损信息Service */
	private PrpLverifyLossService prpLverifyLossService;
	/** 定损基本信息Service */
	private PrpLcertainLossService prpLcertainLossService;
	/** 人伤跟踪信息Service */
	private PrpLpersonTraceService prpLpersonTraceService;
	/** 理赔业务权限Service */
	private PrpLclaimGradeService prpLclaimGradeService;

	private PrpLthirdPartyService prpLthirdPartyService;

	private DAARegistViewHelper daaRegistViewHelper;

	private EndorseViewHelper endorseViewHelper;

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}

	public CertainLossService getCertainLossService() {
		if (certainLossService == null) {
			return (CertainLossService) ServiceFactory.getService("certainLossService");
		}
		return certainLossService;
	}

	public void setCertainLossService(CertainLossService certainLossService) {
		this.certainLossService = certainLossService;
	}

	public RegistService getRegistService() {
		if (registService == null) {
			return (RegistService) ServiceFactory.getService("registService");
		}
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public PrpLclaimService getPrpLclaimService() {
		if (prpLclaimService == null) {
			return (PrpLclaimService) ServiceFactory.getService("prpLclaimService");
		}
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpLclaimStatusService getPrpLclaimStatusService() {
		if (prpLclaimStatusService == null) {
			return (PrpLclaimStatusService) ServiceFactory.getService("prpLclaimStatusService");
		}
		return prpLclaimStatusService;
	}

	public void setPrpLclaimStatusService(PrpLclaimStatusService prpLclaimStatusService) {
		this.prpLclaimStatusService = prpLclaimStatusService;
	}

	public PrpLregistService getPrpLregistService() {
		if (prpLregistService == null) {
			return (PrpLregistService) ServiceFactory.getService("prpLregistService");
		}
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrpLverifyLossService getPrpLverifyLossService() {
		if (prpLverifyLossService == null) {
			return (PrpLverifyLossService) ServiceFactory.getService("prpLverifyLossService");
		}
		return prpLverifyLossService;
	}

	public void setPrpLverifyLossService(PrpLverifyLossService prpLverifyLossService) {
		this.prpLverifyLossService = prpLverifyLossService;
	}

	public PrpLcertainLossService getPrpLcertainLossService() {
		if (prpLcertainLossService == null) {
			return (PrpLcertainLossService) ServiceFactory.getService("prpLcertainLossService");
		}
		return prpLcertainLossService;
	}

	public void setPrpLcertainLossService(PrpLcertainLossService prpLcertainLossService) {
		this.prpLcertainLossService = prpLcertainLossService;
	}

	public PrpLpersonTraceService getPrpLpersonTraceService() {
		if (prpLpersonTraceService == null) {
			return (PrpLpersonTraceService) ServiceFactory.getService("prpLpersonTraceService");
		}
		return prpLpersonTraceService;
	}

	public void setPrpLpersonTraceService(PrpLpersonTraceService prpLpersonTraceService) {
		this.prpLpersonTraceService = prpLpersonTraceService;
	}

	public PrpLclaimGradeService getPrpLclaimGradeService() {
		if (prpLclaimGradeService == null) {
			return (PrpLclaimGradeService) ServiceFactory.getService("prpLclaimGradeService");
		}
		return prpLclaimGradeService;
	}

	public void setPrpLclaimGradeService(PrpLclaimGradeService prpLclaimGradeService) {
		this.prpLclaimGradeService = prpLclaimGradeService;
	}

	public DAARegistViewHelper getDaaRegistViewHelper() {
		if (daaRegistViewHelper == null) {
			return (DAARegistViewHelper) ServiceFactory.getService("daaRegistViewHelper");
		}
		return daaRegistViewHelper;
	}

	public void setDaaRegistViewHelper(DAARegistViewHelper daaRegistViewHelper) {
		this.daaRegistViewHelper = daaRegistViewHelper;
	}

	public PrpLthirdPartyService getPrpLthirdPartyService() {
		if (prpLthirdPartyService == null) {
			return (PrpLthirdPartyService) ServiceFactory.getService("prpLthirdPartyService");
		}
		return prpLthirdPartyService;
	}

	public void setPrpLthirdPartyService(PrpLthirdPartyService prpLthirdPartyService) {
		this.prpLthirdPartyService = prpLthirdPartyService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public DAAClaimViewHelper getDaaClaimViewHelper() {
		return daaClaimViewHelper;
	}

	public void setDaaClaimViewHelper(DAAClaimViewHelper daaClaimViewHelper) {
		this.daaClaimViewHelper = daaClaimViewHelper;
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public PrpDcarModelService getPrpDcarModelService() {
		return prpDcarModelService;
	}

	public void setPrpDcarModelService(PrpDcarModelService prpDcarModelService) {
		this.prpDcarModelService = prpDcarModelService;
	}

	public PrpDriskConfigService getPrpDriskConfigService() {
		return prpDriskConfigService;
	}

	public void setPrpDriskConfigService(PrpDriskConfigService prpDriskConfigService) {
		this.prpDriskConfigService = prpDriskConfigService;
	}

	public void setVerifyLossService(VerifyLossService verifyLossService) {
		this.verifyLossService = verifyLossService;
	}

	public VerifyLossService getVerifyLossService() {
		return verifyLossService;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}
	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}

}
