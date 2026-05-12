package com.sinosoft.claim.verifyLoss.util;

import ins.framework.common.DateTime;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.common.ServiceFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;

import com.sinosoft.claim.certainLoss.service.facade.CertainLossService;
import com.sinosoft.claim.certainLoss.vo.CertainLossDto;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.vo.CaseRelateNodeDto;
import com.sinosoft.claim.common.vo.ICollections;
import com.sinosoft.claim.common.vo.LabelValueBean;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.util.DAARegistViewHelper;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpLcarLoss;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLclaimGrade;
import com.sinosoft.claim.schema.model.PrpLclaimGradeId;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLclaimStatusId;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLcomponent;
import com.sinosoft.claim.schema.model.PrpLperson;
import com.sinosoft.claim.schema.model.PrpLpersonWound;
import com.sinosoft.claim.schema.model.PrpLprop;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.schema.model.PrpLrepairFee;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.PrpLverifyLoss;
import com.sinosoft.claim.schema.model.PrpLverifyLossExt;
import com.sinosoft.claim.schema.model.PrpLverifyLossItem;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfPath;
import com.sinosoft.claim.schema.service.facade.PrpDcodeService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimGradeService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimStatusService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLverifyLossService;
import com.sinosoft.claim.util.BusinessRuleUtil;
import com.sinosoft.claim.util.StringConvert;
import com.sinosoft.claim.verifyLoss.service.facade.VerifyLossService;
import com.sinosoft.claim.verifyLoss.vo.VerifyLossDto;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowQueryDto;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * @ClassName VerifyLossViewHelper
 * @Description 车险核损ViewHelper类，在该类中完成页面数据的整理
 * @author 中科软
 * @date Feb 19, 2013 12:29:51 PM
 */
public class DAAVerifyLossViewHelper extends VerifyLossViewHelper {
	/**
	 * 保存定损时定损页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return verifyLossDto 定损数据传输数据结构
	 * @throws Exception
	 */
	public VerifyLossDto viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		String nodeType = httpServletRequest.getParameter("nodeType");
		
		// 继承对verifyLoss,verifyLossText表的赋值
		VerifyLossDto verifyLossDto = super.viewToDto(httpServletRequest);
		/*---------------------财产核定损明细清单表 prpLprop ------------------------------------*/
		List<PrpLprop> prpLpropList = new ArrayList<PrpLprop>();
		PrpLprop prpLprop = null;
		// reason: 因为考虑到录入的时候，可能没有立案，但是在提交的时候，做了立案，导致立案号没写入。
		String claimNo = httpServletRequest.getParameter("prpLverifyLossClaimNo");
		String registNo = httpServletRequest.getParameter("prpLverifyLossRegistNo");
		PrpLregist prpLregist = this.registService.findByPrimaryKeyForPrpLRegist(registNo);
		String policyNo = prpLregist.getPolicyNo();
		String damageDate = new DateTime(prpLregist.getDamageStartDate()).toString();
		String damageHour = prpLregist.getDamageStartHour();
		List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, null , null);
		PrpCitemKind prpCitemKind = null;
		// 先取立案号码，很重要，不要从页面上取得。。。
		if (claimNo == null || claimNo.length() < 2) {
			claimNo = this.getCodeService().translateBusinessCode(registNo, true);
		}
		// 定核损处理标的表
		PrpLverifyLossItem LossItemRepairComponent = new PrpLverifyLossItem();
		PrpLverifyLossItem LossItemPerson = new PrpLverifyLossItem();
		PrpLverifyLossItem LossItemProp = new PrpLverifyLossItem();
		List<PrpLverifyLossItem> lossItemListTemp = new ArrayList<PrpLverifyLossItem>();
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
		String[] prpLpropFeeTypeName = httpServletRequest.getParameterValues("prpLpropFeeTypeName");
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
		// reason: 增加保存理算退回的定损的标志的保存,若有数据不会被保存冲掉
		String[] prpLpropCompensateBackFlag = httpServletRequest.getParameterValues("prpLpropCompensateBackFlag");
		// 对象赋值
		if (prpLpropSerialNo != null) {
			for (int index = 1; index < prpLpropSerialNo.length; index++) {
				prpLprop = new PrpLprop();
				prpLprop.setPolicyNo(prpLpropPolicyNo);
				prpLprop.setRiskCode(prpLpropRiskCode);
				prpLprop.setClaimNo(claimNo);
				prpLprop.getId().setRegistNo(prpLpropRegistNo);
				prpLprop.getId().setSerialNo(index);
				prpLprop.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(prpLpropItemKindNo[index])));
				prpLprop.setFamilyNo(Integer.parseInt(DataUtils.nullToZero(prpLpropFamilyNo[index])));
				prpLprop.setFamilyName(prpLpropFamilyName[index]);
				prpLprop.setKindCode(prpLpropKindCode[index]);
				prpLprop.setItemCode(prpLpropItemCode[index]);
				prpLprop.setLossItemCode(prpLpropLossItemCode[index]);
				prpLprop.setLossItemName(prpLpropLossItemName[index]);
				prpLprop.setFeeTypeCode(feeTypeCode[index]);
				prpLprop.setFeeTypeName(prpLpropFeeTypeName[index]);
				prpLprop.setCurrency(prpLpropCurrency[index]);
				prpLprop.setUnitPrice(Double.parseDouble(DataUtils.nullToZero(prpLpropUnitPrice[index])));
				prpLprop.setLossQuantity(Double.parseDouble(DataUtils.nullToZero(prpLpropLossQuantity[index])));
				prpLprop.setUnit(prpLpropUnit[index]);
				prpLprop.setBuyDate(new Date());
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
				
				for (int k = 0; k < prpCitemKindList.size(); k++) {
					prpCitemKind = prpCitemKindList.get(k);
					if (prpCitemKind.getKindCode().equals(prpLprop.getKindCode())) {
						prpLprop.setItemKindNo(prpCitemKind.getId().getItemKindNo());
						break;
					}
				}
				// 加入集合
				prpLpropList.add(prpLprop);
			}
			PropertyUtils.copyProperties(LossItemProp.getId(), verifyLossDto.getPrpLverifyLoss().getId());
			PropertyUtils.copyProperties(LossItemProp.getId(), verifyLossDto.getPrpLverifyLoss());
			LossItemProp.getId().setSerialNo(3);
			LossItemProp.getId().setLossType("3");
			LossItemProp.getId().setNodeType(nodeType);
			lossItemListTemp.add(LossItemProp);
		}
		// 财产核定损明细清单表
		verifyLossDto.setPrpLpropList(prpLpropList);
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

		int repairFeeNo = 0;
		int componentNo = 0;

		String[] prpLcarLossLossItemCode = httpServletRequest.getParameterValues("prpLcarLossLossItemCode");
		String[] prpLcarLossLossItemName = httpServletRequest.getParameterValues("prpLcarLossLossItemName");
		String[] prpLcarLossCurrency = httpServletRequest.getParameterValues("prpLcarLossCurrency");
		String[] prpLcarLossSumRest = httpServletRequest.getParameterValues("prpLcarLossSumRest");
		String[] prpLcarLossSumManager = httpServletRequest.getParameterValues("prpLcarLossSumManager");
		// 增加浮動比例
		String[] prpLcarLossSumFloatRate = httpServletRequest.getParameterValues("prpLcarLossSumFloatRate");

		String[] prpLcarLossSumCertainLoss = httpServletRequest.getParameterValues("prpLcarLossSumCertainLoss");
		String[] prpLcarLossSumVeriRest = httpServletRequest.getParameterValues("prpLcarLossSumVeriRest");
		String[] prpLcarLossSumVeriManager = httpServletRequest.getParameterValues("prpLcarLossSumVeriManager");
		String[] prpLcarLossSumTransFee = httpServletRequest.getParameterValues("prpLcarLossSumTransFee");
		String[] prpLcarLossSumVerifyLoss = httpServletRequest.getParameterValues("prpLcarLossSumVerifyLoss");
		String[] prpLcarLossLossDesc = httpServletRequest.getParameterValues("prpLcarLossLossDesc");
		String[] prpLcarLossIndemnityDuty = httpServletRequest.getParameterValues("prpLcarLossIndemnityDuty");
		String[] prpLcarLossIndemnityDutyRate = httpServletRequest.getParameterValues("prpLcarLossIndemnityDutyRate");
		String[] prpLcarLossVeriIndeDutyRate = httpServletRequest.getParameterValues("prpLcarLossVeriIndeDutyRate");
		String[] prpLcarLossRemark = httpServletRequest.getParameterValues("prpLcarLossRemark");
		String[] prpLcarLossOperatorCode = httpServletRequest.getParameterValues("prpLcarLossOperatorCode");
		String[] prpLcarLossApproverCode = httpServletRequest.getParameterValues("prpLcarLossApproverCode");
		String[] prpLcarLossFlag = httpServletRequest.getParameterValues("prpLcarLossFlag");
		String[] prpLcarLossBackCheckFlag = httpServletRequest.getParameterValues("prpLcarLossBackCheckFlag");
		String[] prpLcarLossBackCheckRemark = httpServletRequest.getParameterValues("prpLcarLossBackCheckRemark");
		String[] prpLcarLossCarKindCode = httpServletRequest.getParameterValues("prpLcarLossCarKindCode");
		String[] prpLcarLossLicenseColorCode = httpServletRequest.getParameterValues("prpLcarLossLicenseColorCode");
		String[] prpLcarLossVINNo = httpServletRequest.getParameterValues("prpLcarLossVINNo");
		String[] prpLcarLossSumManageFeeRate = httpServletRequest.getParameterValues("prpLcarLossSumManageFeeRate");
		String prpLrepairFeeRepairFactoryCode = httpServletRequest.getParameter("prpLrepairFeeRepairFactoryCode");
		String prpLrepairFeeRepairFactoryName = httpServletRequest.getParameter("prpLrepairFeeRepairFactoryName");
		String prpLrepairFeeHandlerCode = httpServletRequest.getParameter("prpLrepairFeeHandlerCode");

		// prpLrepairFee
		String[] carLossRepairFeeLossItemCode = httpServletRequest.getParameterValues("carLossRepairFeeLossItemCode");
		String[] prpLrepairFeeItemKindNo = httpServletRequest.getParameterValues("prpLrepairFeeItemKindNo");
		String[] prpLrepairFeeKindCode = httpServletRequest.getParameterValues("prpLrepairFeeKindCode");
		String[] prpLrepairFeeSanctioner = httpServletRequest.getParameterValues("prpLrepairFeeSanctioner");
		String[] prpLrepairFeeApproverCode = httpServletRequest.getParameterValues("prpLrepairFeeApproverCode");
		String[] prpLrepairFeeOperatorCode = httpServletRequest.getParameterValues("prpLrepairFeeOperatorCode");
		String[] prpLrepairFeeCompCode = httpServletRequest.getParameterValues("prpLrepairFeeCompCode");
		String[] prpLrepairFeeCompName = httpServletRequest.getParameterValues("prpLrepairFeeCompName");
		String[] prpLrepairFeeManHour = httpServletRequest.getParameterValues("prpLrepairFeeManHour");
		String[] prpLrepairFeeManHourUnitPrice = httpServletRequest.getParameterValues("prpLrepairFeeManHourUnitPrice");
		String[] prpLrepairFeeManHourFee = httpServletRequest.getParameterValues("prpLrepairFeeManHourFee");
		String[] prpLrepairFeeMaterialFee = httpServletRequest.getParameterValues("prpLrepairFeeMaterialFee");
		String[] prpLrepairFeeLossRate = httpServletRequest.getParameterValues("prpLrepairFeeLossRate");
		String[] prpLrepairFeeCurrency = httpServletRequest.getParameterValues("prpLrepairFeeCurrency");
		String[] prpLrepairFeeSumDefLoss = httpServletRequest.getParameterValues("prpLrepairFeeSumDefLoss");
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
		String[] prpLrepairFeeBackCheckRemark = httpServletRequest.getParameterValues("prpLrepairFeeBackCheckRemark");
		String[] prpLrepairFeeFlag = httpServletRequest.getParameterValues("prpLrepairFeeFlag");
		String[] prpLrepairFeeIndId = httpServletRequest.getParameterValues("prpLrepairFeeIndId");
		String[] prpLrepairFeePartCode = httpServletRequest.getParameterValues("prpLrepairFeePartCode");

		String[] prpLrepairFeePartName = httpServletRequest.getParameterValues("prpLrepairFeePartName");
		String[] prpLrepairFeeRepairType = httpServletRequest.getParameterValues("prpLrepairFeeRepairType");

		// reason: 增加保存理算退回的定损的标志的保存,若有数据不会被保存冲掉
		String[] prpLrepairFeeCompensateBackFlag = httpServletRequest.getParameterValues("prpLrepairFeeCompensateBackFlag");
		String[] prpLcomponentCompensateBackFlag = httpServletRequest.getParameterValues("prpLcomponentCompensateBackFlag");

		// prpLcomponent
		String[] carLossComponentLossItemCode = httpServletRequest.getParameterValues("carLossComponentLossItemCode");
		String[] prpLcomponentItemKindNo = httpServletRequest.getParameterValues("prpLcomponentItemKindNo");
		String[] prpLcomponentKindCode = httpServletRequest.getParameterValues("prpLcomponentKindCode");
		String[] prpLcomponentMakeYear = httpServletRequest.getParameterValues("prpLcomponentMakeYear");
		String[] prpLcomponentGearboxType = httpServletRequest.getParameterValues("prpLcomponentGearboxType");
		String[] prpLcomponentQuoteCompanyGrade = httpServletRequest.getParameterValues("prpLcomponentQuoteCompanyGrade");
		String[] prpLcomponentManageFeeRate = httpServletRequest.getParameterValues("prpLcomponentManageFeeRate");
		String[] prpLcomponentRepairFactoryFee = httpServletRequest.getParameterValues("prpLcomponentRepairFactoryFee");
		String prpLcomponentRepairFactoryCode = httpServletRequest.getParameter("prpLrepairFeeRepairFactoryCode");
		String prpLcomponentRepairFactoryName = httpServletRequest.getParameter("prpLrepairFeeRepairFactoryName");
		String prpLcomponentHandlerCode = httpServletRequest.getParameter("prpLrepairFeeHandlerCode");
		String[] prpLcomponentSanctioner = httpServletRequest.getParameterValues("prpLcomponentSanctioner");
		String[] prpLcomponentApproverCode = httpServletRequest.getParameterValues("prpLcomponentApproverCode");
		String[] prpLcomponentOperatorCode = httpServletRequest.getParameterValues("prpLcomponentOperatorCode");
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
		String[] prpLcomponentSumVeriLoss = httpServletRequest.getParameterValues("prpLcomponentVeriVerpCompPrice");
		String[] prpLcomponentOriginalId = httpServletRequest.getParameterValues("prpLcomponentOriginalId");
		String[] prpLcomponentSys4SPrice = httpServletRequest.getParameterValues("prpLcomponentSys4SPrice");
		String[] prpLcomponentSysMarketPrice = httpServletRequest.getParameterValues("prpLcomponentSysMarketPrice");
		String[] prpLcomponentSysMatchPrice = httpServletRequest.getParameterValues("prpLcomponentSysMatchPrice");
		String[] prpLcomponentNative4SPrice = httpServletRequest.getParameterValues("prpLcomponentNative4SPrice");
		String[] prpLcomponentNativeMarketPrice = httpServletRequest.getParameterValues("prpLcomponentNativeMarketPrice");
		String[] prpLcomponentNativeMatchPrice = httpServletRequest.getParameterValues("prpLcomponentNativeMatchPrice");
		String[] prpLcomponentVerpCompPrice = httpServletRequest.getParameterValues("prpLcomponentVerpCompPrice");
		String[] prpLcomponentVeriRemark = httpServletRequest.getParameterValues("prpLcomponentVeriRemark");
		String[] prpLcomponentBackCheckRemark = httpServletRequest.getParameterValues("prpLcomponentBackCheckRemark");
		String[] prpLcomponentFlag = httpServletRequest.getParameterValues("prpLcomponentFlag");
		String[] prpLcomponentPartCode = httpServletRequest.getParameterValues("prpLcomponentPartCode");
		String[] prpLcomponentPartName = httpServletRequest.getParameterValues("prpLcomponentPartName");
		String[] prpLcomponentIndId = httpServletRequest.getParameterValues("prpLcomponentIndId");
		String[] prpLcomponentIfRemain = httpServletRequest.getParameterValues("prpLcomponentIfRemain");
		String[] prpLcomponentPriceType = httpServletRequest.getParameterValues("prpLcomponentPriceType");
		if (prpLcarLossLossItemCode != null) {
			for (int i = 0; i < prpLcarLossLossItemCode.length; i++) {
				prpLcarLoss = new PrpLcarLoss();
				prpLcarLoss.setPolicyNo(prpLcarLossPolicyNo);
				prpLcarLoss.setRiskCode(prpLcarLossRiskCode);
				prpLcarLoss.setClaimNo(claimNo);
				prpLcarLoss.getId().setRegistNo(prpLcarLossRegistNo);
				prpLcarLoss.getId().setLossItemCode(prpLcarLossLossItemCode[i]);

				prpLcarLoss.setLossItemName(prpLcarLossLossItemName[i]);
				prpLcarLoss.setCurrency(prpLcarLossCurrency[i]);
				prpLcarLoss.setSumRest(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumRest[i])));
				prpLcarLoss.setSumManager(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumManager[i])));
				// 增加浮動比例
				prpLcarLoss.setSumFloatRate(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumFloatRate[i])));

				prpLcarLoss.setSumCertainLoss(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumCertainLoss[i])));
				prpLcarLoss.setSumVeriRest(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumVeriRest[i])));
				prpLcarLoss.setSumVeriManager(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumVeriManager[i])));
				prpLcarLoss.setSumVerifyLoss(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumVerifyLoss[i])));
				prpLcarLoss.setSumTransFee(Double.parseDouble(prpLcarLossSumTransFee[i]));
				prpLcarLoss.setLossDesc(prpLcarLossLossDesc[i]);
				prpLcarLoss.setIndemnityDuty(prpLcarLossIndemnityDuty[i]);
				prpLcarLoss.setIndemnityDutyRate(Double.parseDouble(DataUtils.nullToZero(prpLcarLossIndemnityDutyRate[i])));
				prpLcarLoss.setVeriIndeDutyRate(Double.parseDouble(DataUtils.nullToZero(prpLcarLossVeriIndeDutyRate[i])));
				prpLcarLoss.setRemark(prpLcarLossRemark[i]);
				prpLcarLoss.setSumManageFeeRate(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumManageFeeRate[i])));
				prpLcarLoss.setVINNo(prpLcarLossVINNo[i]);
				prpLcarLoss.setOperatorCode(prpLcarLossOperatorCode[i]);
				prpLcarLoss.setApproverCode(prpLcarLossApproverCode[i]);
				prpLcarLoss.setFlag(prpLcarLossFlag[i]);
				prpLcarLoss.setBackCheckRemark(prpLcarLossBackCheckRemark[i]);

				prpLcarLoss.setBackCheckFlag(prpLcarLossBackCheckFlag[i]);
				// 加入集合
				prpLcarLossList.add(prpLcarLoss);

				// 对象赋值
				for (int index1 = 1; index1 < carLossRepairFeeLossItemCode.length; index1++) {
					if (String.valueOf(Integer.parseInt(carLossRepairFeeLossItemCode[index1]) + 1).equals(prpLcarLossLossItemCode[i])) {
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
						prpLrepairFee.setLicenseNo(prpLcarLossLossItemName[i]);
						prpLrepairFee.setLicenseColorCode(prpLcarLossLicenseColorCode[i]);
						prpLrepairFee.setCarKindCode(prpLcarLossCarKindCode[i]);
						prpLrepairFee.setRepairFactoryCode(prpLrepairFeeRepairFactoryCode);
						prpLrepairFee.setRepairFactoryName(prpLrepairFeeRepairFactoryName);
						prpLrepairFee.setHandlerCode(prpLrepairFeeHandlerCode);
						prpLrepairFee.setRepairStartDate(new Date());
						prpLrepairFee.setRepairEndDate(new Date());
						prpLrepairFee.setSanctioner(prpLrepairFeeSanctioner[index1]);
						prpLrepairFee.setApproverCode(prpLrepairFeeApproverCode[index1]);
						prpLrepairFee.setOperatorCode(prpLrepairFeeOperatorCode[index1]);
						prpLrepairFee.setCompCode(prpLrepairFeeCompCode[index1]);
						prpLrepairFee.setCompName(prpLrepairFeeCompName[index1]);
						prpLrepairFee.setManHour(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeManHour[index1])));
						prpLrepairFee.setManHourUnitPrice(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeManHourUnitPrice[index1])));
						prpLrepairFee.setManHourFee(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeManHourFee[index1])));
						prpLrepairFee.setMaterialFee(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeMaterialFee[index1])));
						prpLrepairFee.setLossRate(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeLossRate[index1])));
						prpLrepairFee.setCurrency(prpLrepairFeeCurrency[index1]);
						prpLrepairFee.setSumDefLoss(Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeSumDefLoss[index1])));
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
						prpLrepairFee.setBackCheckRemark(prpLrepairFeeBackCheckRemark[index1]);
						prpLrepairFee.setPartCode(prpLrepairFeePartCode[index1 - 1]);
						prpLrepairFee.setPartName(prpLrepairFeePartName[index1 - 1]);
						prpLrepairFee.setRepairType(prpLrepairFeeRepairType[index1]);
						prpLrepairFee.setFlag(prpLrepairFeeFlag[index1]);
						prpLrepairFee.setIndId(prpLrepairFeeIndId[index1]);
						// reason: 增加保存理算退回的定损的标志的保存,若有数据不会被保存冲掉
						prpLrepairFee.setCompensateBackFlag(prpLrepairFeeCompensateBackFlag[index1]);
						prpLrepairFeeList.add(prpLrepairFee);
					}
				}
				// 对象赋值
				for (int index2 = 1; index2 < carLossComponentLossItemCode.length; index2++) {
					if (String.valueOf(Integer.parseInt(carLossComponentLossItemCode[index2]) + 1).equals(prpLcarLossLossItemCode[i])) {
						componentNo = componentNo + 1;
						prpLcomponent = new PrpLcomponent();
						prpLcomponent.setPolicyNo(prpLcarLossPolicyNo);
						prpLcomponent.setRiskCode(prpLcarLossRiskCode);
						prpLcomponent.setClaimNo(claimNo);
						prpLcomponent.getId().setRegistNo(prpLcarLossRegistNo);
						prpLcomponent.getId().setSerialNo(componentNo);
						prpLcomponent.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(prpLcomponentItemKindNo[index2])));
						prpLcomponent.setKindCode(prpLcomponentKindCode[index2]);
						for (int k = 0; k < prpCitemKindList.size(); k++) {
							prpCitemKind = prpCitemKindList.get(k);
							if (prpCitemKind.getKindCode().equals(prpLcomponent.getKindCode())) {
								prpLcomponent.setItemKindNo(prpCitemKind.getId().getItemKindNo());
								break;
							}
						}
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

						prpLcomponent.setRepairFactoryFee(Double.parseDouble(prpLcomponentRepairFactoryFee[index2 - 1]));

						prpLcomponent.setRepairStartDate(new Date());
						prpLcomponent.setRepairEndDate(new Date());
						prpLcomponent.setSanctioner(prpLcomponentSanctioner[index2]);
						prpLcomponent.setApproverCode(prpLcomponentApproverCode[index2]);
						prpLcomponent.setOperatorCode(prpLcomponentOperatorCode[index2]);
						prpLcomponent.setCompCode(prpLcomponentCompCode[index2]);
						prpLcomponent.setCompName(prpLcomponentCompName[index2]);
						prpLcomponent.setQuantity(Integer.parseInt(DataUtils.nullToZero(prpLcomponentQuantity[index2])));
						prpLcomponent.setManHourFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentManHourFee[index2])));
						prpLcomponent.setMaterialFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentMaterialFee[index2])));
						prpLcomponent.setRestFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentRestFee[index2])));
						prpLcomponent.setVeriRestFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentVeriRestFee[index2])));
						prpLcomponent.setQueryPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentQueryPrice[index2])));
						prpLcomponent.setQuotedPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentQuotedPrice[index2])));
						prpLcomponent.setLossRate(Double.parseDouble(DataUtils.nullToZero(prpLcomponentLossRate[index2])));
						prpLcomponent.setCurrency(prpLcomponentCurrency[index2]);
						prpLcomponent.setSumDefLoss(Double.parseDouble(DataUtils.nullToZero(prpLcomponentSumDefLoss[index2])));
						prpLcomponent.setRemark(prpLcomponentRemark[index2]);
						prpLcomponent.setVeriQuantity(Integer.parseInt(DataUtils.nullToZero(prpLcomponentVeriQuantity[index2 - 1])));
						prpLcomponent.setVeriManHourFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentVeriManHourFee[index2])));
						prpLcomponent.setVeriMaterFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentVeriMaterFee[index2 - 1])));
						prpLcomponent.setVeriLossRate(Double.parseDouble(DataUtils.nullToZero(prpLcomponentVeriLossRate[index2])));
						prpLcomponent.setSumVeriLoss(Double.parseDouble(DataUtils.nullToZero(prpLcomponentSumVeriLoss[index2 - 1])));
						prpLcomponent.setOriginalId(prpLcomponentOriginalId[index2]);
						prpLcomponent.setSys4SPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentSys4SPrice[index2])));
						prpLcomponent.setSysMarketPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentSysMarketPrice[index2])));
						prpLcomponent.setSysMatchPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentSysMatchPrice[index2])));
						prpLcomponent.setNative4SPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentNative4SPrice[index2])));
						prpLcomponent.setNativeMarketPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentNativeMarketPrice[index2])));
						prpLcomponent.setNativeMatchPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentNativeMatchPrice[index2])));
						prpLcomponent.setVerpCompPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentVerpCompPrice[index2])));
						prpLcomponent.setVeriRemark(prpLcomponentVeriRemark[index2 - 1]);
						prpLcomponent.setBackCheckRemark(prpLcomponentBackCheckRemark[index2]);
						prpLcomponent.setFlag(prpLcomponentFlag[index2]);
						prpLcomponent.setPartCode(prpLcomponentPartCode[index2]);
						prpLcomponent.setPartName(prpLcomponentPartName[index2]);
						// 更换项目保存价格类型，避免被冲掉 begin
						prpLcomponent.setPriceType(prpLcomponentPriceType[index2]);
//						if (nodeType.equals("verip") || nodeType.equals("verpo") || nodeType.equals("verif")) {
//						}
						prpLcomponent.setIndId(prpLcomponentIndId[index2]);
						prpLcomponent.setIfRemain(prpLcomponentIfRemain[index2]);
						// reason: 增加保存理算退回的定损的标志的保存,若有数据不会被保存冲掉
						prpLcomponent.setCompensateBackFlag(prpLcomponentCompensateBackFlag[index2]);
						// 加入集合
						prpLcomponentList.add(prpLcomponent);
					}
				}
			}
			PrpLverifyLoss tempPrpLverifyLoss = verifyLossDto.getPrpLverifyLoss();
			PropertyUtils.copyProperties(LossItemRepairComponent.getId(), tempPrpLverifyLoss.getId());
			PropertyUtils.copyProperties(LossItemRepairComponent.getId(), tempPrpLverifyLoss);
			LossItemRepairComponent.getId().setSerialNo(1);
			LossItemRepairComponent.getId().setLossType("1");
			LossItemRepairComponent.getId().setNodeType(nodeType);
			lossItemListTemp.add(LossItemRepairComponent);
		}

		// 定损车辆表
		verifyLossDto.setPrpLcarLossList(prpLcarLossList);
		verifyLossDto.setPrpLrepairFeeList(prpLrepairFeeList);
		verifyLossDto.setPrpLcomponentList(prpLcomponentList);

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
		String[] prpLpersonFamilyName = httpServletRequest.getParameterValues("prpLpersonFamilyName");
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
		String[] prpLpersonVeriUnit = httpServletRequest.getParameterValues("prpLpersonVeriUnit");
		String[] prpLpersonVeriTimes = httpServletRequest.getParameterValues("prpLpersonVeriTimes");
		String[] prpLpersonVeriSumLoss = httpServletRequest.getParameterValues("prpLpersonVeriSumLoss");
		String[] prpLpersonVeriSumReject = httpServletRequest.getParameterValues("prpLpersonVeriSumReject");
		String[] prpLpersonVeriRejectReason = httpServletRequest.getParameterValues("prpLpersonVeriRejectReason");
		String[] prpLpersonVeriLossRate = httpServletRequest.getParameterValues("prpLpersonVeriLossRate");
		String[] prpLpersonVeriSumDefLoss = httpServletRequest.getParameterValues("prpLpersonVeriSumDefLoss");
		String[] prpLpersonVeriRemark = httpServletRequest.getParameterValues("prpLpersonVeriRemark");
		String[] prpLpersonFlag = httpServletRequest.getParameterValues("prpLpersonFlag");
		// reason: 增加保存理算退回的定损的标志的保存,若有数据不会被保存冲掉
		String[] prpLpersonCompensateBackFlag = httpServletRequest.getParameterValues("prpLpersonCompensateBackFlag");

		// reason:保存继医情况说明 等
		String[] prpLpersonFllowHospRemark = httpServletRequest.getParameterValues("prpLpersonFllowHospRemark");// 继医情况说明
		String[] prpLpersonInHospDate = httpServletRequest.getParameterValues("prpLpersonInHospDate"); // 入院日期
		String[] prpLpersonOutHospDate = httpServletRequest.getParameterValues("prpLpersonOutHospDate"); // 出院日期
		String[] prpLpersonRestDate = httpServletRequest.getParameterValues("prpLpersonRestDate"); // 定残日期

		// 对象赋值
		if (personSerialNo != null) {
			for (int index = 1; index < personSerialNo.length; index++) {
				prpLperson = new PrpLperson();
				prpLperson.setPolicyNo(prpLpersonPolicyNo);
				prpLperson.setRiskCode(prpLpersonRiskCode);
				prpLperson.setClaimNo(claimNo);
				prpLperson.getId().setRegistNo(prpLpersonRegistNo);
				// 多数派
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
				
				for (int index2 = 0; index2 < prpLpersonSerialNo.length; index2++) {
					if (prpLpersonSerialNo[index2].equals(personSerialNo[index])) {
						// 少数派
						prpLperson.setFamilyName(prpLpersonFamilyName[index2]);
						prpLperson.setKindCode(prpLpersonKindCode[index2]);
						
						for (int k = 0; k < prpCitemKindList.size(); k++) {
							prpCitemKind = prpCitemKindList.get(k);
							if (prpCitemKind.getKindCode().equals(prpLperson.getKindCode())) {
								prpLperson.setItemKindNo(prpCitemKind.getId().getItemKindNo());
								break;
							}
						}
						prpLperson.setAreaCode(prpLpersonAreaCode[index2]);
						prpLperson.setFixedIncomeFlag(prpLpersonFixedIncomeFlag[index2]);
						prpLperson.setJobCode(prpLpersonJobCode[index2]);
						prpLperson.setJobName(prpLpersonJobName[index2]);
						prpLperson.setPayPersonType(prpLpersonPayPersonType[index2]);
						prpLperson.setPersonName(prpLpersonPersonName[index2]);
						prpLperson.setPersonSex(prpLpersonPersonSex[index2]);
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
						prpLperson.getId().setPersonNo(Integer.valueOf(prpLpersonPersonNo[index2]));

						prpLperson.setFllowHospRemark(prpLpersonFllowHospRemark[index2]);
						prpLperson.setInHospDate(CommonUtils.toYearToDayDate(prpLpersonInHospDate[index2]));
						prpLperson.setOutHospDate(CommonUtils.toYearToDayDate(prpLpersonOutHospDate[index2]));
						prpLperson.setRestDate(CommonUtils.toYearToDayDate(prpLpersonRestDate[index2]));

					}
				}
				// 加入集合
				prpLpersonList.add(prpLperson);
			}
			PropertyUtils.copyProperties(LossItemPerson.getId(), verifyLossDto.getPrpLverifyLoss().getId());
			PropertyUtils.copyProperties(LossItemPerson.getId(), verifyLossDto.getPrpLverifyLoss());
			LossItemPerson.getId().setSerialNo(0);
			LossItemPerson.getId().setLossType("0");
			LossItemPerson.getId().setNodeType(nodeType);
			lossItemListTemp.add(LossItemPerson);
		}

		// 财产核定损明细清单表
		verifyLossDto.setPrpLpersonList(prpLpersonList);
		verifyLossDto.setPrpLverifyLossItemList(lossItemListTemp);
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

		List<PrpDcode> woundCodeList = this.getCodeService().getCodeType("WoundCode", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		Map<String, String> woundCodeMap = new HashMap<String, String>();
		for (int i = 0; i < woundCodeList.size(); i++) {
			PrpDcode prpDcodeDto = woundCodeList.get(i);
			woundCodeMap.put(prpDcodeDto.getId().getCodeCode(), prpDcodeDto.getCodeCName());
		}
		int intSerialNo = 1;
		if (woundCodeCheck001Txt != null) {
			for (int index = 1; index < woundCodeCheck001Txt.length; index++) {
				intSerialNo = 1;
				if (woundCodeCheck001Txt[index].trim().equals("1")) {
					prpLpersonWound = new PrpLpersonWound();
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
					prpLpersonWound.getId().setRegistNo(verifyLossDto.getPrpLverifyLoss().getId().getRegistNo());
					prpLpersonWound.setClaimNo(verifyLossDto.getPrpLverifyLoss().getClaimNo());
					prpLpersonWound.setPolicyNo(verifyLossDto.getPrpLverifyLoss().getPolicyNo());
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
		verifyLossDto.setPrpLpersonWoundList(prpLpersonWoundList);

		/*---------------------定核损信息补充说明 PrpLverifyLossExt ------------------------------------*/
		List<PrpLverifyLossExt> prpLverifyLossExtList = new ArrayList<PrpLverifyLossExt>();
		PrpLverifyLossExt prpLverifyLossExt = null;
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
			for (int index = 1; index < prpLverifyLossExtSerialNo.length; index++) {
				prpLverifyLossExt = new PrpLverifyLossExt();
				prpLverifyLossExt.getId().setRegistNo(prpLverifyLossExtRegistNo);
				prpLverifyLossExt.setRiskCode(prpLverifyLossExtRiskCode);
				prpLverifyLossExt.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLverifyLossExtSerialNo[index])));
				prpLverifyLossExt.setInputDate(CommonUtils.toYearToDayDate(prpLverifyLossExtInputDate[index]));
				prpLverifyLossExt.setInputHour(prpLverifyLossExtInputHour[index]);
				prpLverifyLossExt.setOperatorCode(prpLverifyLossExtOperatorCode[index]);
				prpLverifyLossExt.setTitle(prpLverifyLossExtTitle[index]);
				prpLverifyLossExt.setContext(prpLverifyLossExtContext[index]);
				prpLverifyLossExt.getId().setLossItemCode(prpLverifyLossExtLossItemCode);
				// 加入集合
				prpLverifyLossExtList.add(prpLverifyLossExt);
			}
			// 报案集合中加入损失部位
			verifyLossDto.setPrpLverifyLossExtList(prpLverifyLossExtList);
		}

		/*---------------------报案信息补充说明 PrpLregistExt ------------------------------------*/
		List<PrpLregistExt> prpLregistExtList = new ArrayList<PrpLregistExt>();
		PrpLregistExt prpLregistExt = null;
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
			for (int index = 1; index < prpLregistExtSerialNo.length; index++) {
				prpLregistExt = new PrpLregistExt();
				prpLregistExt.getId().setRegistNo(prpLregistExtRegistNo);
				prpLregistExt.setRiskCode(prpLregistExtRiskCode);
				prpLregistExt.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLregistExtSerialNo[index])));
				prpLregistExt.setInputDate(CommonUtils.toYearToDayDate(prpLregistExtInputDate[index]));
				prpLregistExt.setInputHour(prpLregistExtInputHour[index]);
				prpLregistExt.setOperatorCode(prpLregistExtOperatorCode[index]);
				prpLregistExt.setContext(prpLregistExtContext[index]);
				// 加入集合
				prpLregistExtList.add(prpLregistExt);
			}
			// 报案集合中加入损失部位
			verifyLossDto.setPrpLregistExtList(prpLregistExtList);
		}
		return verifyLossDto;
	}

	/**
	 * 生成定损信息详细画面
	 * @param httpServletRequest 返回给页面的request
	 * @param businessNo 业务号码
	 * @param editType 编辑类型
	 * @throws Exception
	 */
	public void verifyLossDtoView(HttpServletRequest httpServletRequest, String registNo, String editType, String tempStatus) throws Exception {
		// 标的序号，如果是人伤为0，否则为1，2，3，4，5等车辆序号
		String lossItemCode = httpServletRequest.getParameter("lossItemCode");
		// 工作流的信息
		String swfLogFlowID = (String) httpServletRequest.getParameter("swfLogFlowID");
		String swfLogLogNo = (String) httpServletRequest.getParameter("swfLogLogNo");
		String nodeType = (String) httpServletRequest.getParameter("nodeType");
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user"); // 用户信息
		String riskCode = httpServletRequest.getParameter("riskCode");
		// 非车险，lossItemCode默认为1,这样就可以正常进行财产险详细的删除操作了

		String strRiskType = this.getCodeService().translateRiskCodetoRiskType(riskCode);
		if (riskCode != null && !strRiskType.equals(("D"))) {
			lossItemCode = "1";
		}
		CertainLossDto certainLossDto = this.getCertainLossService().findByPrimaryKey(registNo.trim(), lossItemCode,CommonUtils.getCertainNodeType(nodeType));
		VerifyLossDto verifyLossDto = this.getVerifyLossService().findByPrimaryKey(registNo.trim(), lossItemCode, nodeType);
		// 根据查询出来的数据内容，给PrpLverifyLossDto赋值
		PrpLverifyLoss prpLverifyLoss = verifyLossDto.getPrpLverifyLoss();

		// 设置定损操作的状态为 案件修改 (正处理任务)
		if (verifyLossDto.getPrpLclaimStatus() != null) {
			if (tempStatus == null) {
				prpLverifyLoss.setStatus(verifyLossDto.getPrpLclaimStatus().getStatus());
			} else {
				prpLverifyLoss.setStatus(tempStatus);
			}
		} else {
			// 已提交，已经处理完毕的状态
			prpLverifyLoss.setStatus("1");
		}

		// 校验是否已经向外询价，如果已经向外询价，则允许核价员超权限提交
		if (!prpLverifyLoss.getStatus().equals("4")) {
			String msg = "";
			msg = this.checkVerpo(swfLogFlowID, swfLogLogNo, lossItemCode);
			prpLverifyLoss.setVerifPriceOuterMsg(msg);
		}
		RegistDto registDto = this.getRegistService().findByPrimaryKey(registNo.trim());
		String relatePolicyFlag = "0";
		if (registDto.getPrpLRegistRPolicyList().size() > 1) {
			relatePolicyFlag = "1";
		}
		if ("1".equals(relatePolicyFlag)) {
			httpServletRequest.setAttribute("prpLregistRPolicyNo", registDto.getPrpLRegistRPolicyOfCompel());
		}
		// 属性条款类别
		prpLverifyLoss.setClauseType(registDto.getPrpLregist().getClauseType());

		PrpLcarLoss prpLcarLoss = null;

		if (verifyLossDto.getPrpLcarLossList() != null) {
			for (int i = 0; i < verifyLossDto.getPrpLcarLossList().size(); i++) {
				prpLcarLoss = (PrpLcarLoss) verifyLossDto.getPrpLcarLossList().get(i);
				for (int j = 0; j < registDto.getPrpLthirdPartyList().size(); j++) {
					PrpLthirdParty prpLthirdPartyDto = (PrpLthirdParty) registDto.getPrpLthirdPartyList().get(j);
					if (prpLcarLoss.getLossItemName().equals(prpLthirdPartyDto.getLicenseNo())) {
						prpLcarLoss.setLicenseColorCode(prpLthirdPartyDto.getLicenseColorCode());
						prpLcarLoss.setCarKindCode(prpLthirdPartyDto.getCarKindCode());
						prpLcarLoss.setModelCode(prpLthirdPartyDto.getModelCode());
						prpLcarLoss.setBrandName(prpLthirdPartyDto.getBrandName());
						prpLcarLoss.setEngineNo(prpLthirdPartyDto.getEngineNo());
						prpLcarLoss.setFrameNo(prpLthirdPartyDto.getFrameNo());
						prpLcarLoss.setVINNo(prpLthirdPartyDto.getVINNo());
						prpLcarLoss.setInsureCarFlag(prpLthirdPartyDto.getInsureCarFlag());
						prpLcarLoss.setInsureComCode(prpLthirdPartyDto.getInsureComCode());
						prpLcarLoss.setInsureComName(prpLthirdPartyDto.getInsureComName());
					}
				}
			}
		}
		String insureCarFlag = "0";
		if (prpLverifyLoss.getId().getLossItemCode().equals("1")) {
			insureCarFlag = "1";
		}
		prpLverifyLoss.setInsureCarFlag(insureCarFlag);

		// 查询相同保单号的出险次数
		this.getDaaRegistViewHelper().getSamePolicyRegistInfo(httpServletRequest, prpLverifyLoss.getPolicyNo(), prpLverifyLoss.getId().getRegistNo());
		// 设置相关代码的中文转换
		changeCodeToName(httpServletRequest, prpLverifyLoss);
		changeCodeToName(httpServletRequest, verifyLossDto);
		// 设置窗体表单中各个多选框中列表信息的内容
		setSelectionList(httpServletRequest,registNo);

		// 查询核价权限
		String taskCode = AppConfig.get("sysconst.TASKCODE_LPHJ"); // 任务代码为核价
		// 单个自定义配件价格
		PrpLclaimGrade prpLclaimGrade1 = this.getPrpLclaimGradeService().findPrpLclaimGrade(new PrpLclaimGradeId(user.getUserCode(), taskCode, "SINGLE_CUSTOM_COMP_PRICE"));
		// 单车自定义配件价格
		PrpLclaimGrade prpLclaimGrade2 = this.getPrpLclaimGradeService().findPrpLclaimGrade(new PrpLclaimGradeId(user.getUserCode(), taskCode, "SINGLECAR_CUSTOM_COMP_PRICE"));
		// 单车全部配件价格
		PrpLclaimGrade prpLclaimGrade3 = this.getPrpLclaimGradeService().findPrpLclaimGrade(new PrpLclaimGradeId(user.getUserCode(), taskCode, "SINGLECAR_ALL_COMP_PRICE"));
		if (prpLclaimGrade1 == null) {
			prpLclaimGrade1 = new PrpLclaimGrade();
		}
		if (prpLclaimGrade2 == null) {
			prpLclaimGrade2 = new PrpLclaimGrade();
		}
		if (prpLclaimGrade3 == null) {
			prpLclaimGrade3 = new PrpLclaimGrade();
		}
		httpServletRequest.setAttribute("prpLclaimGrade1", prpLclaimGrade1);
		httpServletRequest.setAttribute("prpLclaimGrade2", prpLclaimGrade2);
		httpServletRequest.setAttribute("prpLclaimGrade3", prpLclaimGrade3);

		// 设置定损信息内容到窗体表单
		httpServletRequest.setAttribute("prpLverifyLoss", prpLverifyLoss);
		httpServletRequest.setAttribute("verifyLossDto", verifyLossDto);

		httpServletRequest.setAttribute("verifyPriceOpinionList", ICollections.getVerifyPriceOpinionList());
		// Reason:增加核损意见
		httpServletRequest.setAttribute("verifyOpinionList", ICollections.getVerifyOpinionList());

		// 设置各个子表信息项到窗体表单
		setSubInfo(httpServletRequest, verifyLossDto);

		PrpLthirdParty prpLthirdParty1 = new PrpLthirdParty();
		prpLthirdParty1 = certainLossDto.getPrpLthirdParty();
		httpServletRequest.setAttribute("prpLthirdParty1", prpLthirdParty1);

		// 取得相关主表的信息
		CaseRelateNodeDto caseRelateNodeDto = this.getRegistService().relateNode(registNo);
		PrpLcheck prpLcheckTemp1 = caseRelateNodeDto.getPrpLcheck();
		if (prpLcheckTemp1 == null) {
			prpLcheckTemp1 = new PrpLcheck();
			// 报案处如过选择不需要查勘，则得不到任何查勘信息，此时需要添加一些基本信息
			prpLcheckTemp1.getId().setRegistNo(caseRelateNodeDto.getPrpLregist().getRegistNo());// 添加报案号
			prpLcheckTemp1.setPolicyNo(caseRelateNodeDto.getPrpLregist().getPolicyNo());// 添加保单号
		}
		httpServletRequest.setAttribute("prpLcheckTemp", prpLcheckTemp1);
		PrpLregist prpLregistTemp1 = caseRelateNodeDto.getPrpLregist();
		String timeTemp = StringConvert.toStandardTime(prpLregistTemp1.getDamageStartHour());
		prpLregistTemp1.setDamageStartMinute(timeTemp.substring(3, 5));
		prpLregistTemp1.setDamageStartHour(timeTemp.substring(0, 2));
		httpServletRequest.setAttribute("prpLregist", prpLregistTemp1);

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
	public void certainLossDtoToView(HttpServletRequest httpServletRequest, String registNo, String editType) throws Exception {
		// 标的序号，如果是人伤为0，否则为1，2，3，4，5等车辆序号
		String lossItemCode = httpServletRequest.getParameter("lossItemCode");
		// 工作流的信息
		String swfLogFlowID = (String) httpServletRequest.getParameter("swfLogFlowID");
		String swfLogLogNo = (String) httpServletRequest.getParameter("swfLogLogNo");
		String nodeType = (String) httpServletRequest.getParameter("nodeType");
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user"); // 用户信息
		String riskCode = httpServletRequest.getParameter("riskCode");
		// 非车险，lossItemCode默认为1,这样就可以正常进行财产险详细的删除操作了

		String strRiskType = this.getCodeService().translateRiskCodetoRiskType(riskCode);
		if (riskCode != null && !strRiskType.equals(("D"))) {
			lossItemCode = "1";
		}
		CertainLossDto certainLossDto = this.getCertainLossService().findByPrimaryKey(registNo.trim(), lossItemCode,CommonUtils.getCertainNodeType(nodeType));
		VerifyLossDto verifyLossDto = this.getVerifyLossService().findByPrimaryKey(registNo.trim(), lossItemCode, nodeType);
		
		// 根据查询出来的数据内容，给PrpLverifyLoss赋值
		PrpLverifyLoss prpLverifyLoss = verifyLossDto.getPrpLverifyLoss();
		if (prpLverifyLoss == null) {
			prpLverifyLoss = new PrpLverifyLoss();
		}
		// 准备数据
		List<?> collectionPartName = this.getCodeService().findPrpDcodeByConditions("codeType = 'CarPartCode'");
		if (verifyLossDto.getPrpLrepairFeeList() != null) {
			for (PrpLrepairFee prpLrepairFee : verifyLossDto.getPrpLrepairFeeList()) {
				if (prpLrepairFee.getVeriManHour() == 0) {
					prpLrepairFee.setVeriManHour(prpLrepairFee.getManHour());
				}
				if (prpLrepairFee.getVeriManUnitPrice() == 0) {
					prpLrepairFee.setVeriManUnitPrice(prpLrepairFee.getManHourUnitPrice());
				}
				if (prpLrepairFee.getVeriMaterialFee() == 0) {
					prpLrepairFee.setVeriMaterialFee(prpLrepairFee.getMaterialFee());
				}
				if (prpLrepairFee.getVeriSumLoss() == 0) {
					prpLrepairFee.setVeriSumLoss(prpLrepairFee.getSumDefLoss());
				}
				prpLrepairFee.setVeriRemark(prpLrepairFee.getRemark());
				for (Iterator<?> partNameIterator = collectionPartName.iterator(); partNameIterator.hasNext();) {
					PrpDcode prpDcodeDto = (PrpDcode) partNameIterator.next();
					if (prpLrepairFee.getPartCode().equals(prpDcodeDto.getId().getCodeCode())) {
						prpLrepairFee.setPartName(prpDcodeDto.getCodeCName());
					}
				}
			}
		}
		if (verifyLossDto.getPrpLcomponentList() != null) {
			for (int i = 0; i < verifyLossDto.getPrpLcomponentList().size(); i++) {
				PrpLcomponent prpLcomponent = (PrpLcomponent) verifyLossDto.getPrpLcomponentList().get(i);
				// 增加如果是第一进入到核损界面，看到的是定损的值，否则，用核损自己录的值
				if (prpLcomponent.getVeriQuantity() == 0) {
					prpLcomponent.setVeriQuantity(prpLcomponent.getQuantity());
				}
				if (prpLcomponent.getVeriManHourFee() == 0) {
					prpLcomponent.setVeriManHourFee(prpLcomponent.getManHourFee());
				}
				if (prpLcomponent.getVeriMaterFee() == 0) {
					prpLcomponent.setVeriMaterFee(prpLcomponent.getMaterialFee());
				}
				if (prpLcomponent.getSumVeriLoss() == 0) {
					prpLcomponent.setSumVeriLoss(prpLcomponent.getSumDefLoss());
				}
				if ("".equals(prpLcomponent.getVeriRemark())) {
					prpLcomponent.setVeriRemark(prpLcomponent.getRemark());
				}
				// 关於核损调整
				if (prpLcomponent.getVeriRestFee() == 0) {
					prpLcomponent.setVeriRestFee(prpLcomponent.getRestFee());
				}
				// 关於核损调整
			}
		}

		if (verifyLossDto.getPrpLpropList() != null) {
			for (int i = 0; i < verifyLossDto.getPrpLpropList().size(); i++) {
				PrpLprop prpLprop = (PrpLprop) verifyLossDto.getPrpLpropList().get(i);
				prpLprop.setVeriSumLoss(prpLprop.getSumLoss());
				prpLprop.setVeriSumReject(prpLprop.getSumReject());
				prpLprop.setVeriSumDefLoss(prpLprop.getSumDefLoss());
				prpLprop.setVeriRemark(prpLprop.getRemark());
			}
		}
		if (verifyLossDto.getPrpLpersonList() != null) {
			for (int i = 0; i < verifyLossDto.getPrpLpersonList().size(); i++) {
				PrpLperson prpLperson = (PrpLperson) verifyLossDto.getPrpLpersonList().get(i);
				prpLperson.setVeriSumLoss(prpLperson.getSumLoss());
				prpLperson.setVeriSumReject(prpLperson.getSumReject());
				prpLperson.setVeriSumDefLoss(prpLperson.getSumDefLoss());
				prpLperson.setVeriUnitLoss(prpLperson.getUnitLoss());
				prpLperson.setVeriQuantity(prpLperson.getQuantity());
			}
		}
		prpLverifyLoss.setStatus("1");
		// 校验是否已经向外询价，如果已经向外询价，则允许核价员超权限提交
		if (!prpLverifyLoss.getStatus().equals("4")) {
			String msg = "";
			msg = this.checkVerpo(swfLogFlowID, swfLogLogNo, lossItemCode);
			prpLverifyLoss.setVerifPriceOuterMsg(msg);
		}
		RegistDto registDto = this.getRegistService().findByPrimaryKey(registNo.trim());
		// 属性条款类别
		prpLverifyLoss.setClauseType(registDto.getPrpLregist().getClauseType());

		String relatePolicyFlag = "0";
		if (registDto.getPrpLRegistRPolicyList().size() > 1) {
			relatePolicyFlag = "1";
		}
		if ("1".equals(relatePolicyFlag)) {
			httpServletRequest.setAttribute("prpLregistRPolicyNo", registDto.getPrpLRegistRPolicyOfCompel());
		}
		PrpLcarLoss prpLcarLoss = null;
		if (verifyLossDto.getPrpLcarLossList() != null) {
			for (int i = 0; i < verifyLossDto.getPrpLcarLossList().size(); i++) {
				prpLcarLoss = (PrpLcarLoss) verifyLossDto.getPrpLcarLossList().get(i);
				for (int j = 0; j < registDto.getPrpLthirdPartyList().size(); j++) {
					PrpLthirdParty prpLthirdPartyDto = (PrpLthirdParty) registDto.getPrpLthirdPartyList().get(j);
					if (prpLcarLoss.getLossItemName().equals(prpLthirdPartyDto.getLicenseNo())) {
						prpLcarLoss.setLicenseColorCode(prpLthirdPartyDto.getLicenseColorCode());
						prpLcarLoss.setCarKindCode(prpLthirdPartyDto.getCarKindCode());
						prpLcarLoss.setModelCode(prpLthirdPartyDto.getModelCode());
						prpLcarLoss.setBrandName(prpLthirdPartyDto.getBrandName());
						prpLcarLoss.setEngineNo(prpLthirdPartyDto.getEngineNo());
						prpLcarLoss.setFrameNo(prpLthirdPartyDto.getFrameNo());
						prpLcarLoss.setVINNo(prpLthirdPartyDto.getVINNo());
						prpLcarLoss.setInsureCarFlag(prpLthirdPartyDto.getInsureCarFlag());
						prpLcarLoss.setInsureComCode(prpLthirdPartyDto.getInsureComCode());
						prpLcarLoss.setInsureComName(prpLthirdPartyDto.getInsureComName());

					}
				}
			}
		}
		// 查询相同保单号的出险次数
		this.getDaaRegistViewHelper().getSamePolicyRegistInfo(httpServletRequest, prpLverifyLoss.getPolicyNo(), prpLverifyLoss.getId().getRegistNo());
		// 设置相关代码的中文转换
		changeCodeToName(httpServletRequest, prpLverifyLoss);
		changeCodeToName(httpServletRequest, verifyLossDto);
		// 设置窗体表单中各个多选框中列表信息的内容
		setSelectionList(httpServletRequest,registNo);
		// 查询核价权限
		String taskCode = AppConfig.get("sysconst.TASKCODE_LPHJ"); // 任务代码为核价
		// 单个自定义配件价格
		PrpLclaimGrade prpLclaimGrade1 = this.getPrpLclaimGradeService().findPrpLclaimGrade(new PrpLclaimGradeId(user.getUserCode(), taskCode, "SINGLE_CUSTOM_COMP_PRICE"));
		// 单车自定义配件价格
		PrpLclaimGrade prpLclaimGrade2 = this.getPrpLclaimGradeService().findPrpLclaimGrade(new PrpLclaimGradeId(user.getUserCode(), taskCode, "SINGLECAR_CUSTOM_COMP_PRICE"));
		// 单车全部配件价格
		PrpLclaimGrade prpLclaimGrade3 = this.getPrpLclaimGradeService().findPrpLclaimGrade(new PrpLclaimGradeId(user.getUserCode(), taskCode, "SINGLECAR_ALL_COMP_PRICE"));
		if (prpLclaimGrade1 == null) {
			prpLclaimGrade1 = new PrpLclaimGrade();
		}
		if (prpLclaimGrade2 == null) {
			prpLclaimGrade2 = new PrpLclaimGrade();
		}
		if (prpLclaimGrade3 == null) {
			prpLclaimGrade3 = new PrpLclaimGrade();
		}
		httpServletRequest.setAttribute("prpLclaimGrade1", prpLclaimGrade1);
		httpServletRequest.setAttribute("prpLclaimGrade2", prpLclaimGrade2);
		httpServletRequest.setAttribute("prpLclaimGrade3", prpLclaimGrade3);
		// 设置定损信息内容到窗体表单
		httpServletRequest.setAttribute("prpLverifyLoss", prpLverifyLoss);
		httpServletRequest.setAttribute("verifyLossDto", verifyLossDto);
		if (nodeType.equals("verip")) {
			httpServletRequest.setAttribute("verifyPriceOpinionList", ICollections.getVerifyPriceOpinionList());
		} else if (nodeType.equals("verpo")) {
			httpServletRequest.setAttribute("verifyPriceOpinionList", ICollections.getVerifyPriceVerpoOpinionList());
		}
		// Reason:增加核损意见
		httpServletRequest.setAttribute("verifyOpinionList", ICollections.getVerifyOpinionList());

		// 设置各个子表信息项到窗体表单
		verifyLossDto.setPrpLverifyLoss(prpLverifyLoss);
		setSubInfo(httpServletRequest, verifyLossDto);

		PrpLthirdParty prpLthirdParty1 = new PrpLthirdParty();
		prpLthirdParty1 = certainLossDto.getPrpLthirdParty();
		httpServletRequest.setAttribute("prpLthirdParty1", prpLthirdParty1);

		// 取得相关主表的信息
		CaseRelateNodeDto caseRelateNodeDto = this.getRegistService().relateNode(registNo);
		PrpLcheck prpLcheckTemp1 = caseRelateNodeDto.getPrpLcheck();
		if (prpLcheckTemp1 == null) {
			prpLcheckTemp1 = new PrpLcheck();
			// 报案处如过选择不需要查勘，则得不到任何查勘信息，此时需要添加一些基本信息
			prpLcheckTemp1.getId().setRegistNo(caseRelateNodeDto.getPrpLregist().getRegistNo());// 添加报案号
			prpLcheckTemp1.setPolicyNo(caseRelateNodeDto.getPrpLregist().getPolicyNo());// 添加保单号
		}
		httpServletRequest.setAttribute("prpLcheckTemp", prpLcheckTemp1);
		PrpLregist prpLregistTemp1 = caseRelateNodeDto.getPrpLregist();
		String timeTemp = StringConvert.toStandardTime(prpLregistTemp1.getDamageStartHour());
		prpLregistTemp1.setDamageStartMinute(timeTemp.substring(3, 5));
		prpLregistTemp1.setDamageStartHour(timeTemp.substring(0, 2));
		httpServletRequest.setAttribute("prpLregist", prpLregistTemp1);

		// 设置工作流下一个节点提交的配置信息
		getSubmitNodes(httpServletRequest);
	}

	/**
	 * 检查本车核价是否已向外询价
	 * @param swfLogDtoCurrent SwfLogDto 需要被检查的节点对象
	 * @throws Exception
	 */
	public String checkVerpo(String swfLogFlowID, String swfLogLogNo, String lossItemCode) throws Exception {
		// 目前只检查该车核价是否已经询价过（只针对单辆车）
		String nodeType = "";
		String conditions = "";
		String msg = "";
		int swfLogCount;
		SwfLog swfLogDtoCurrent = null;
		int logNo = Integer.parseInt(DataUtils.nullToZero(swfLogLogNo));
		if (swfLogFlowID == null || logNo < 1) {
			return msg;
		}
		swfLogDtoCurrent = this.getWorkFlowService().findNodeByPrimaryKey(swfLogFlowID, logNo);
		if (swfLogDtoCurrent == null) {
			return msg;
		}
		nodeType = swfLogDtoCurrent.getNodeType();
		if (nodeType.equals("verip")) {
			// 核价检查是否已经向外询价
			conditions = " flowid='" + swfLogDtoCurrent.getId().getFlowID() + "' and nodeType='verpo'  and  lossItemCode = '" + lossItemCode + "' ";
		}
		swfLogCount = this.getWorkFlowService().findFlowNodeCountByConditon(conditions);
		if (swfLogCount > 0) {
			msg = "本车已经向外询价";
		}
		return msg;
	}

	/**
	 * 根据赔案号和报案号查询定损信息
	 * @param httpServletRequest 返回给页面的request
	 * @param businessNo 赔案号
	 * @throws Exception
	 */
	public void setPrpLverifyLossDtoToView(HttpServletRequest httpServletRequest, String registNo, String policyNo) throws Exception {
		registNo = StringUtils.rightTrim(registNo);
		policyNo = StringUtils.rightTrim(policyNo);
		// 查询立案信息
		// 得到多行定损主表信息
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addLike("id.registNo", "%" + registNo.trim() + "%");
		queryRule.addLike("policyNo", "%" + policyNo.trim() + "%");
		List<PrpLverifyLoss> verifyLossList = this.getPrpLverifyLossService().findPrpLverifyLoss(queryRule);
		PrpLverifyLoss prpLverifyLoss = new PrpLverifyLoss();
		prpLverifyLoss.setVerifyLossList(verifyLossList);
		prpLverifyLoss.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLverifyLossDto", prpLverifyLoss);
	}

	@SuppressWarnings("unchecked")
	public Page setPrpLverifyLossDtoToView(HttpServletRequest httpServletRequest, WorkFlowQueryDto workFlowQueryDto, int pageNo, int recordPerPage) throws Exception {
		// caseNO,policyNo,claimNo
		// 根据输入的保单号，定损号生成SQL where 子句
		String nodeType = workFlowQueryDto.getConSignType();
		// 判断是取委托类型还是取nodeType
		if (nodeType == null || nodeType.equals("")) {
			nodeType = workFlowQueryDto.getNodeType();
		}
		String editType = httpServletRequest.getParameter("editType");
		String registNo = StringUtils.rightTrim(workFlowQueryDto.getRegistNo());
		String policyNo = StringUtils.rightTrim(workFlowQueryDto.getPolicyNo());
		String licenseNo = StringUtils.rightTrim(workFlowQueryDto.getLicenseNo());
		String status = StringUtils.rightTrim(workFlowQueryDto.getStatus());
		String operateDate = StringUtils.rightTrim(workFlowQueryDto.getOperateDate());
		String insuredName = StringUtils.rightTrim(workFlowQueryDto.getInsuredName());
		String conditions = " 1=1 ";
		conditions = conditions + StringConvert.convertString("a.registNo", registNo, workFlowQueryDto.getRegistNoSign());
		// reason:强三查询
		conditions = conditions + StringConvert.convertString("d.policyNo", policyNo, workFlowQueryDto.getPolicyNoSign());

		conditions = conditions + StringConvert.convertString("c.licenseNo", licenseNo, workFlowQueryDto.getLicenseNoSign());
		conditions = conditions + StringConvert.convertString("c.insuredName", insuredName, workFlowQueryDto.getInsuredNameSign());
		if (status.trim().length() > 0) {
			conditions = conditions + " AND b.status in (" + status + ") ";
		}
		if (operateDate != null && !operateDate.trim().equals("")) {
			conditions = conditions + StringConvert.convertDate("b.operateDate", operateDate, workFlowQueryDto.getOperateDateSign());
		}
		/***业务表查询不再限制机构  delete by chenjie 20130614 start*/
//		com.sinosoft.claim.ui.control.action.UIPowerInterface uiPowerInterface = new com.sinosoft.claim.ui.control.action.UIPowerInterface();
//		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
//		conditions = conditions + uiPowerInterface.addPower(userDto, "a", "", "ComCode");
		/***业务表查询不再限制机构  delete by chenjie 20130614 end*/
		// Reason:使每种核损查询只能查询本种类的数据
		String strNodeType = httpServletRequest.getParameter("nodeType");
		if (strNodeType != null) {
			strNodeType = StringUtils.rightTrim(strNodeType);
		}
		String condition = httpServletRequest.getParameter("condition");
		// reason 查询标志
		String searchFlag = httpServletRequest.getParameter("searchFlag");
		if (!"true".equals(searchFlag)) {
			if (condition != null && condition.trim().length() > 0) {
				conditions = condition;
			}
		}
		conditions = conditions + " AND a.nodeType='"+CommonUtils.getCertainNodeType(strNodeType)+"'";
		Page page = this.getVerifyLossService().findByCondition(conditions, pageNo, recordPerPage);
		List<PrpLverifyLoss> verifyLossList = page.getResult();
		PrpLverifyLoss temp = null;
		for (int i=0;i<verifyLossList.size();i++) {
			temp = verifyLossList.get(i);
			temp.setEditType(editType);
			PrpLclaimStatus prpLclaimStatus = this.getPrpLclaimStatusService().findPrpLclaimStatus(new PrpLclaimStatusId(temp.getId().getRegistNo(),nodeType, Integer.parseInt(temp.getId().getLossItemCode())));
			if (prpLclaimStatus != null) {
				temp.setStatus(prpLclaimStatus.getStatus());
			} else {
				temp.setStatus("0");
			}
		}
		PrpLverifyLoss prpLverifyLoss = new PrpLverifyLoss();
		prpLverifyLoss.setVerifyLossList(verifyLossList);
		prpLverifyLoss.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLverifyLoss", prpLverifyLoss);
		return page;
	}

	/**
	 * 已核损案件查询
	 * @param httpServletRequest 返回给页面的request
	 * @param businessNo 赔案号
	 * @throws Exception
	 */
	public void getUnderWriteVerifyLossList(HttpServletRequest httpServletRequest) throws Exception {
		// caseNO,policyNo,claimNo
		// 查询立案信息
		// 得到多行定损主表信息
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addLike("underWriteFlag", "%1%");
		List<PrpLverifyLoss> verifyLossList = this.getPrpLverifyLossService().findPrpLverifyLoss(queryRule);
		PrpLverifyLoss prpLverifyLoss = new PrpLverifyLoss();
		prpLverifyLoss.setVerifyLossList(verifyLossList);
		prpLverifyLoss.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLverifyLoss", prpLverifyLoss);
	}

	/**
	 * 获取选择框和列表框中的所有内容
	 * @param httpServletRequest 返回给页面的request
	 * @param prpLcaseNoDto 定损的数据类
	 * @throws Exception
	 */
	private void setSelectionList(HttpServletRequest httpServletRequest,String registNo) throws Exception {

		// (11)得到费用名称的列表 ---
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
		
		// (2)得到车辆种类列表
		List<PrpDcode> carKindCodes = this.getCodeService().getCodeType("CarKind", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("carKindCodes", carKindCodes);
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
		String licenseColor = this.getCodeService().translateCodeCode("LicenseColor", licenseColorCodeCode, true);
		prpLverifyLoss.setLicenseColor(licenseColor);
		// 车辆类型转换
		String carKindCode = prpLverifyLoss.getCarKindCode();
		String carKind = this.getCodeService().translateCodeCode("CarKind", carKindCode, true);
		prpLverifyLoss.setCarKind(carKind);
		// 条款名称的转换
		String clauseType = prpLverifyLoss.getClauseType();
		String clauseName = this.getCodeService().translateCodeCode("ClauseType", clauseType, true);
		prpLverifyLoss.setClauseName(clauseName);

	}

	/**
	 * 根据PrpPrepayDto中的已经设置的代码内容，对代码进行名称转换
	 * @param httpServletRequest 返回给页面的request
	 * @param prpLcaseNoDto 定损的数据类
	 * @throws Exception
	 */
	private void changeCodeToName(HttpServletRequest httpServletRequest, VerifyLossDto verifyLossDto) throws Exception {

		if (verifyLossDto.getPrpLpropList() != null) {
			for (PrpLprop prpLprop : verifyLossDto.getPrpLpropList()) {
				if ("01".equals(prpLprop.getFeeTypeCode().trim())) {
					prpLprop.setFeeTypeName("修理費");
				} else if ("02".equals(prpLprop.getFeeTypeCode().trim())) {
					prpLprop.setFeeTypeName("材料費");
				}
				if (ConstantCodes.KINDCODE_D_BZ.equals(prpLprop.getKindCode())) {
					prpLprop.setKindName(this.getCodeService().translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAZ"), prpLprop.getKindCode(), true));
				} else {
					prpLprop.setKindName(this.getCodeService().translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"), prpLprop.getKindCode(), true));
				}
			}
		}
		if (verifyLossDto.getPrpLcarLossList() != null) {
			for (PrpLcarLoss prpLcarLoss : verifyLossDto.getPrpLcarLossList()) {
				// 是否为本保单车辆转换
				if (prpLcarLoss.getInsureCarFlag()!=null&&"1".equals(prpLcarLoss.getInsureCarFlag().trim())) {
					prpLcarLoss.setInsureCarFlagName("是");
				} else {
					prpLcarLoss.setInsureCarFlagName("否");
				}
				// 车辆种类
				prpLcarLoss.setCarKindName(this.getCodeService().translateCodeCode("CarKind", prpLcarLoss.getCarKindCode(), true));
			}
		}
		if (verifyLossDto.getPrpLrepairFeeList() != null) {
			for (PrpLrepairFee prpLrepairFee : verifyLossDto.getPrpLrepairFeeList()) {
				prpLrepairFee.setHandlerName(this.getCodeService().translateUserCode(prpLrepairFee.getHandlerCode(), true));
				if (ConstantCodes.KINDCODE_D_BZ.equals(prpLrepairFee.getKindCode())) {
					prpLrepairFee.setKindName(this.getCodeService().translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAZ"), prpLrepairFee.getKindCode(), true));
				} else if ("AB".equals(prpLrepairFee.getKindCode())) {
					prpLrepairFee.setKindName(this.getCodeService().translateKindCode(prpLrepairFee.getRiskCode(), prpLrepairFee.getKindCode(), true));
				} else {
					prpLrepairFee.setKindName(this.getCodeService().translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"), prpLrepairFee.getKindCode(), true));
				}
				prpLrepairFee.setRepairTypeName(this.getCodeService().translateCodeCode("RepairType", prpLrepairFee.getRepairType(), true));

			}
		}

		if (verifyLossDto.getPrpLcomponentList() != null) {
			// 险别名称转换
			for (PrpLcomponent prpLcomponent : verifyLossDto.getPrpLcomponentList()) {
				prpLcomponent.setHandlerName(this.getCodeService().translateUserCode(prpLcomponent.getHandlerCode(), true));
				if (ConstantCodes.KINDCODE_D_BZ.equals(prpLcomponent.getKindCode())) {
					prpLcomponent.setKindName(this.getCodeService().translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAZ"), prpLcomponent.getKindCode(), true));
				} else if ("AB".equals(prpLcomponent.getKindCode())) {
					prpLcomponent.setKindName(this.getCodeService().translateKindCode(prpLcomponent.getRiskCode(), prpLcomponent.getKindCode(), true));
				} else {
					prpLcomponent.setKindName(this.getCodeService().translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"), prpLcomponent.getKindCode(), true));
				}
			}
		}
		if (verifyLossDto.getPrpLpersonList() != null) {
			// 险别名称转换
			for (PrpLperson prpLperson : verifyLossDto.getPrpLpersonList()) {
				if (ConstantCodes.KINDCODE_D_BZ.equals(prpLperson.getKindCode())) {
					prpLperson.setKindName(this.getCodeService().translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAZ"), prpLperson.getKindCode(), true));
				} else {
					prpLperson.setKindName(this.getCodeService().translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"), prpLperson.getKindCode(), true));
				}
				prpLperson.setAreaName(this.getCodeService().translateCodeCode("DamageAreaCode", prpLperson.getAreaCode(), true));
				if (prpLperson.getFixedIncomeFlagName().trim().equals("1")) {
					prpLperson.setFixedIncomeFlagName("有固定收入");
				} else if (prpLperson.getFixedIncomeFlagName().trim().equals("2")) {
					prpLperson.setFixedIncomeFlagName("無固定收入");
				} else if (prpLperson.getFixedIncomeFlagName().trim().equals("3")) {
					prpLperson.setFixedIncomeFlagName("無收入");
				} else if (prpLperson.getFixedIncomeFlagName().trim().equals("4")) {
					prpLperson.setFixedIncomeFlagName("無勞動能力");
				} else {
					prpLperson.setFixedIncomeFlagName("其它");
				}
				if (prpLperson.getPayPersonType().trim().equals("1")) {
					prpLperson.setPayPersonTypeName("傷");
				} else if (prpLperson.getPayPersonType().trim().equals("2")) {
					prpLperson.setPayPersonTypeName("被扶養人");
				} else if (prpLperson.getPayPersonType().trim().equals("3")) {
					prpLperson.setPayPersonTypeName("護理人");
				} else if (prpLperson.getPayPersonType().trim().equals("4")) {
					prpLperson.setPayPersonTypeName("殘");
				} else if (prpLperson.getPayPersonType().trim().equals("5")) {
					prpLperson.setPayPersonTypeName("亡");
				} else if (prpLperson.getPayPersonType().trim().equals("6")) {
					prpLperson.setPayPersonTypeName("參加事故處理人員");
				} else {
					prpLperson.setPayPersonTypeName("其它");
				}
			}
		}
	}

	/**
	 * 取初始化信息需要的数据的整理. 填写定损单时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等。取这些信息需要一些入参，
	 * 考虑到接口的一致性，将这些入参作为Dto方式传入，Dto利用聚合而非继承的方式。 整理采用继承的方式分层处理，具体的逻辑放在险种险类子类中整理.
	 * @param httpServletRequest
	 * @throws Exception
	 */
	public VerifyLossDto iniViewToDto(HttpServletRequest httpServletRequest) throws Exception {
		VerifyLossDto verifyLossDto = new VerifyLossDto();
		return verifyLossDto;
	}

	/**
	 * 填写定损页面及查询定损request的生成.
	 * @param httpServletRequest 返回给页面的request
	 * @param verifyLossDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public void dtoToView(HttpServletRequest httpServletRequest, VerifyLossDto verifyLossDto) throws Exception {
	}

	/**
	 * 根据verifyLossDto中的各子表内的信息填充界面
	 * @param httpServletRequest 返回给页面的request
	 * @param verifyLossDto 定损的数据类
	 * @throws Exception
	 */
	private void setSubInfo(HttpServletRequest httpServletRequest, VerifyLossDto verifyLossDto) throws Exception {
		// Reason:得到修理类型列表
		PrpLverifyLoss prpLverifyLoss = verifyLossDto.getPrpLverifyLoss();
		String strRiskCode = BusinessRuleUtil.getRiskCode(prpLverifyLoss.getId().getRegistNo(), "RegistNo");
		String strRiskType = codeService.translateRiskCodetoRiskType(strRiskCode);

		List<PrpDcode> repairTypes = this.getCodeService().getCodeType("RepairType", strRiskCode);
		httpServletRequest.setAttribute("repairTypes", repairTypes);
		String nodeType = (String) httpServletRequest.getParameter("nodeType");
		httpServletRequest.setAttribute("partCodeList", ICollections.getPartCodeList());

		// 给定核损信息补充说明多行列表准备数据
		List<PrpLverifyLossExt> arrayListVerifyLossExt = new ArrayList<PrpLverifyLossExt>();
		PrpLverifyLossExt prpLverifyLossExt = new PrpLverifyLossExt();
		PrpLverifyLossExt prpLverifyLossExtAdd = new PrpLverifyLossExt();
		if (nodeType.equals("verip")) {
			prpLverifyLossExtAdd.setTitle("同意報價");
		}else if (nodeType.equals("verif")) {
			prpLverifyLossExtAdd.setTitle("同意定損");
		}else if (nodeType.equals("backc")) {
			prpLverifyLossExtAdd.setTitle("通過复勘");
		}else if("veriw".equals(nodeType)){
			prpLverifyLossExtAdd.setTitle("同意人傷定損");
		}
		prpLverifyLossExtAdd.getId().setRegistNo(prpLverifyLoss.getId().getRegistNo());
		prpLverifyLossExtAdd.setRiskCode(prpLverifyLoss.getRiskCode());
		prpLverifyLossExtAdd.setInputDate(new Date());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		prpLverifyLossExtAdd.setInputHour(calendar.get(Calendar.HOUR_OF_DAY) + "時" + calendar.get(Calendar.MINUTE) + "分");
		prpLverifyLossExt.getId().setRegistNo(prpLverifyLoss.getId().getRegistNo());
		prpLverifyLossExt.setRiskCode(prpLverifyLoss.getRiskCode());
		arrayListVerifyLossExt = verifyLossDto.getPrpLverifyLossExtList();
		if (arrayListVerifyLossExt == null) {
			arrayListVerifyLossExt = new ArrayList<PrpLverifyLossExt>();
		} else {
			for (PrpLverifyLossExt temp : arrayListVerifyLossExt) {
				temp.setOperatorCodeName(this.getCodeService().translateUserCode(temp.getOperatorCode(), true));
			}
		}
		prpLverifyLossExtAdd.getId().setSerialNo(arrayListVerifyLossExt.size() + 1);
		UserDto user = (UserDto)httpServletRequest.getSession().getAttribute("user");
		prpLverifyLossExtAdd.setOperatorCode(user.getUserCode());
		prpLverifyLossExtAdd.setOperatorCodeName(user.getUserName());
		String status = httpServletRequest.getParameter("status");
		if ("0".equals(status)) {
			arrayListVerifyLossExt.add(prpLverifyLossExtAdd);
		}
		prpLverifyLossExt.setVerifyLossExtList(arrayListVerifyLossExt);
		httpServletRequest.setAttribute("prpLverifyLossExt", prpLverifyLossExt);

		// 给报案信息补充说明多行列表准备数据
		PrpLregistExt prpLregistExt = new PrpLregistExt();
		prpLregistExt.getId().setRegistNo(prpLverifyLoss.getId().getRegistNo());
		prpLregistExt.setRiskCode(prpLverifyLoss.getRiskCode());
		prpLregistExt.setRegistExtList(verifyLossDto.getPrpLregistExtList());
		httpServletRequest.setAttribute("prpLregistExt", prpLregistExt);

		// 修理费用清单多行列表准备数据
		PrpLrepairFee prpLrepairFee = new PrpLrepairFee();
		prpLrepairFee.setRepairFeeList(verifyLossDto.getPrpLrepairFeeList());
		httpServletRequest.setAttribute("prpLrepairFee", prpLrepairFee);

		// 换件项目清单多行列表准备数据
		PrpLcomponent prpLcomponent = new PrpLcomponent();
		prpLcomponent.setComponentList(verifyLossDto.getPrpLcomponentList());
		httpServletRequest.setAttribute("prpLcomponent", prpLcomponent);

		// 人员伤亡明细信息清单多行列表准备数据
		PrpLperson prpLperson = new PrpLperson();
		List<PrpLperson> prpLpersonList = verifyLossDto.getPrpLpersonList();
		if(!CommonUtils.isEmpty(prpLpersonList)){
			for(PrpLperson prpLpersonTmp : prpLpersonList){
				String jobCode = prpLpersonTmp.getJobCode();// 三级行业代码
				if (jobCode != null && !"".equals(jobCode)) {
					String jobCode1 = jobCode.substring(0, jobCode.length() - 2);// 一级行业代码
					String jobCode2 = jobCode.substring(0, jobCode.length() - 1);// 二级行业代码
					String conditions1 = "codecode='" + jobCode1 + "' and flag='1' and validstatus='1' and codetype='BusinessSource' AND codeEname like '%,"+strRiskType+",%' ";
					String conditions2 = "codecode='" + jobCode2 + "' and flag='2' and validstatus='1' and codetype='BusinessSource' AND codeEname like '%,"+strRiskType+",%' ";
					List<PrpDcode> collection1 = prpDcodeService.findByConditions(conditions1);
					for(PrpDcode prpDcode : collection1){
						String jobName1 = prpDcode.getCodeCName();
						prpLpersonTmp.setJobCode1(jobCode1);
						prpLpersonTmp.setJobName1(jobName1);
					}
					List<PrpDcode> collection2 = prpDcodeService.findByConditions(conditions2);
					for(PrpDcode prpDcode : collection2){
						String jobName2 = prpDcode.getCodeCName();
						prpLpersonTmp.setJobCode2(jobCode2);
						prpLpersonTmp.setJobName2(jobName2);
					}
				}
			}
		}
		prpLperson.setPersonList(prpLpersonList);
		httpServletRequest.setAttribute("prpLperson", prpLperson);

		// 财产核定损明细清单多行列表准备数据
		PrpLprop prpLprop = new PrpLprop();
		prpLprop.setPropList(verifyLossDto.getPrpLpropList());
		httpServletRequest.setAttribute("prpLprop", prpLprop);
		// 伤情信息表 多行列表准备数据
		PrpLpersonWound prpLpersonWound = new PrpLpersonWound();
		prpLpersonWound.setWoundList(verifyLossDto.getPrpLpersonWoundList());
		httpServletRequest.setAttribute("prpLpersonWound", prpLpersonWound);

	}

	/**
	 * 检查是否已出赔案计算书 返回值 true 已出 false 未出
	 * @param httpServletRequest 返回给页面的request
	 * @param claimNo 赔案号
	 * @throws Exception
	 */
	public boolean checkCompensate(HttpServletRequest httpServletRequest, String registNo) throws Exception {
		// 根据报案号码取得对应的赔案号码

		String claimNo = this.getCodeService().translateBusinessCode(registNo, true);
		// 取得赔款计算书信息
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("claimNo", claimNo);
		List<PrpLcompensate> list = this.getPrpLcompensateService().findPrpLcompensate(queryRule);
		return list != null && !list.isEmpty();
	}

	/**
	 * 根据赔案号,报案号,案件状态，车牌号码，操作时间查询定损信息
	 * @param httpServletRequest 返回给页面的request
	 * @param businessNo 赔案号
	 * @throws Exception Modify By sunhao 2004-08-24
	 *             Reason:增加车牌号，节点类型，案件状态，操作时间查询条件
	 */
	public void setPrpLverifyLossToView(HttpServletRequest httpServletRequest, WorkFlowQueryDto workFlowQueryDto) throws Exception {
		// 根据输入的保单号，定损号生成SQL where 子句
		String nodeType = workFlowQueryDto.getConSignType();
		// 判断是取委托类型还是取nodeType
		if (nodeType == null || nodeType.equals("")) {
			nodeType = workFlowQueryDto.getNodeType();
		}
		String registNo = StringUtils.rightTrim(workFlowQueryDto.getRegistNo());
		String policyNo = StringUtils.rightTrim(workFlowQueryDto.getPolicyNo());
		String licenseNo = StringUtils.rightTrim(workFlowQueryDto.getLicenseNo());
		String status = StringUtils.rightTrim(workFlowQueryDto.getStatus());
		String operateDate = StringUtils.rightTrim(workFlowQueryDto.getOperateDate());
		String insuredName = StringUtils.rightTrim(workFlowQueryDto.getInsuredName());
		String conditions = " 1=1 ";
		conditions = conditions + StringConvert.convertString("a.registNo", registNo, workFlowQueryDto.getRegistNoSign());
		// reason:强三查询
		conditions = conditions + StringConvert.convertString("d.policyNo", policyNo, workFlowQueryDto.getPolicyNoSign());
		conditions = conditions + StringConvert.convertString("c.licenseNo", licenseNo, workFlowQueryDto.getLicenseNoSign());
		conditions = conditions + StringConvert.convertString("c.insuredName", insuredName, workFlowQueryDto.getInsuredNameSign());
		if (status.trim().length() > 0) {
			conditions = conditions + " AND b.status in (" + status + ") ";
		}
		if (operateDate != null && !operateDate.trim().equals("")) {
			conditions = conditions + StringConvert.convertDate("b.operateDate", operateDate, workFlowQueryDto.getOperateDateSign());
		}
		com.sinosoft.claim.ui.control.action.UIPowerInterface uiPowerInterface = new com.sinosoft.claim.ui.control.action.UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		conditions = conditions + uiPowerInterface.addPower(userDto, "a", "", "ComCode");
		// Reason:使每种核损查询只能查询本种类的数据
		String strNodeType = httpServletRequest.getParameter("nodeType");
		if (strNodeType != null) {
			strNodeType = StringUtils.rightTrim(strNodeType);
		}
		conditions = conditions + " AND a.nodeType='"+CommonUtils.getCertainNodeType(strNodeType)+"'";
		// 得到多行定损主表信息
		List<PrpLverifyLoss> verifyLossList = this.getPrpLverifyLossService().findByQueryConditions(conditions, 0, 0);
		for (PrpLverifyLoss prpLverifyLoss : verifyLossList) {
			PrpLclaimStatus PrpLclaimStatus = this.getPrpLclaimStatusService().findPrpLclaimStatus(new PrpLclaimStatusId(prpLverifyLoss.getId().getRegistNo(),strNodeType,Integer.parseInt(prpLverifyLoss.getId().getLossItemCode())));
			if (PrpLclaimStatus != null) {
				prpLverifyLoss.setStatus(PrpLclaimStatus.getStatus());
			} else {
				prpLverifyLoss.setStatus("0");
			}
		}
		PrpLverifyLoss prpLverifyLoss = new PrpLverifyLoss();
		prpLverifyLoss.setVerifyLossList(verifyLossList);
		prpLverifyLoss.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLverifyLoss", prpLverifyLoss);
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
		SwfPath swfPathDto = new SwfPath();
		if (modelNo != null && nodeNo != null) {
			pathList = this.getWorkFlowViewHelper().getNextSumbitNodes(modelNo, nodeNo);
			Iterator<?> it = pathList.iterator();
			if (it.hasNext()) {
				SwfPath swfPathDtoTemp = (SwfPath) it.next();
				nextNodeNo = swfPathDtoTemp.getEndNodeNo();
				swfPathDto.setNextNodeNo(nextNodeNo);
			}
		}
		swfPathDto.setPathList(pathList);
		httpServletRequest.setAttribute("pathList", pathList);
		httpServletRequest.setAttribute("swfPath", swfPathDto);
	}

	/** 定损信息service */
	private CertainLossService certainLossService;
	/** 报案信息service */
	private RegistService registService;
	/** 理赔节点状态 Service */
	private PrpLclaimStatusService prpLclaimStatusService;
	/** 定核损信息Service */
	private PrpLverifyLossService prpLverifyLossService;
	/** 理赔业务权限Service */
	private PrpLclaimGradeService prpLclaimGradeService;
	/** 核损业务service */
	private VerifyLossService verifyLossService;
	/** 报案viewHelper */
	private DAARegistViewHelper daaRegistViewHelper;
	/** 赔款计算书信息服务 */
	private PrpLcompensateService prpLcompensateService;
	/** 代码服务 */
	private CodeService codeService;
	/** 批单viewHelper */
	private EndorseViewHelper endorseViewHelper;
	/** 工作流viewHelper */
	private WorkFlowViewHelper workFlowViewHelper;
	/** 工作流服务 */
	private WorkFlowService workFlowService;
	/** 代码查询服务*/
	private PrpDcodeService prpDcodeService;
	

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

	public PrpLclaimStatusService getPrpLclaimStatusService() {
		if (prpLclaimStatusService == null) {
			return (PrpLclaimStatusService) ServiceFactory.getService("prpLclaimStatusService");
		}
		return prpLclaimStatusService;
	}

	public void setPrpLclaimStatusService(PrpLclaimStatusService prpLclaimStatusService) {
		this.prpLclaimStatusService = prpLclaimStatusService;
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

	public PrpLclaimGradeService getPrpLclaimGradeService() {
		if (prpLclaimGradeService == null) {
			return (PrpLclaimGradeService) ServiceFactory.getService("prpLclaimGradeService");
		}
		return prpLclaimGradeService;
	}

	public void setPrpLclaimGradeService(PrpLclaimGradeService prpLclaimGradeService) {
		this.prpLclaimGradeService = prpLclaimGradeService;
	}

	public VerifyLossService getVerifyLossService() {
		if (verifyLossService == null) {
			return (VerifyLossService) ServiceFactory.getService("verifyLossService");
		}
		return verifyLossService;
	}

	public void setVerifyLossService(VerifyLossService verifyLossService) {
		this.verifyLossService = verifyLossService;
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

	public PrpLcompensateService getPrpLcompensateService() {
		if (prpLcompensateService == null) {
			return (PrpLcompensateService) ServiceFactory.getService("prpLcompensateService");
		}
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}
	
}
