package com.sinosoft.claim.compensate.util;

import ins.framework.common.DateTime;
import ins.framework.common.QueryRule;
import ins.framework.utils.DataUtils;
import ins.framework.utils.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.sinosoft.claim.bl.facade.BLPrpDdeprecateRateFacade;
import com.sinosoft.claim.certainLoss.service.facade.CertainLossService;
import com.sinosoft.claim.certainLoss.vo.CertainLossDto;
import com.sinosoft.claim.certify.service.facade.CertifyService;
import com.sinosoft.claim.certify.vo.CertifyDto;
import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.claim.util.DAAClaimViewHelper;
import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PersonLossService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.service.facade.PrpLagentService;
import com.sinosoft.claim.common.util.BusinessRuleUtil;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.UIPowerInterface;
import com.sinosoft.claim.common.vo.ExceptDeductibleRateDto;
import com.sinosoft.claim.common.vo.ICollections;
import com.sinosoft.claim.common.vo.LabelValueBean;
import com.sinosoft.claim.common.vo.PolicyDto;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.compensate.service.facade.PrepayService;
import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.dto.domain.PrpDdeprecateRateDto;
import com.sinosoft.claim.endcase.service.facade.RecaseService;
import com.sinosoft.claim.endcase.vo.ReCaseDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.util.DAARegistViewHelper;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.reins.service.ReinsServiceManager;
import com.sinosoft.claim.schema.model.PrpCcoins;
import com.sinosoft.claim.schema.model.PrpCengage;
import com.sinosoft.claim.schema.model.PrpCitemCar;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpClimit;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpDcurrency;
import com.sinosoft.claim.schema.model.PrpDlimit;
import com.sinosoft.claim.schema.model.PrpDriskConfig;
import com.sinosoft.claim.schema.model.PrpLagent;
import com.sinosoft.claim.schema.model.PrpLcarInsurance;
import com.sinosoft.claim.schema.model.PrpLcarLoss;
import com.sinosoft.claim.schema.model.PrpLcertifyCollect;
import com.sinosoft.claim.schema.model.PrpLcfee;
import com.sinosoft.claim.schema.model.PrpLcfeecoins;
import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLcheckId;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimLoss;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLcomponent;
import com.sinosoft.claim.schema.model.PrpLctext;
import com.sinosoft.claim.schema.model.PrpLdeductCond;
import com.sinosoft.claim.schema.model.PrpLdeductible;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLltext;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLperson;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.PrpLpersonTrace;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.PrpLprop;
import com.sinosoft.claim.schema.model.PrpLqualityCheck;
import com.sinosoft.claim.schema.model.PrpLrecase;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.schema.model.PrpLrepairFee;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.PrpLverifyLoss;
import com.sinosoft.claim.schema.model.SwfNotion;
import com.sinosoft.claim.schema.service.facade.PrpCcoinsService;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpClimitService;
import com.sinosoft.claim.schema.service.facade.PrpDdeductCondService;
import com.sinosoft.claim.schema.service.facade.PrpLcarInsuranceService;
import com.sinosoft.claim.schema.service.facade.PrpLcfeecoinsService;
import com.sinosoft.claim.schema.service.facade.PrpLcheckService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimLossService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLdeductCondService;
import com.sinosoft.claim.schema.service.facade.PrpLlossService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonLossService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrpLthirdPartyService;
import com.sinosoft.claim.schema.service.facade.PrpLverifyLossService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.schema.service.facade.SwfNotionService;
import com.sinosoft.claim.ui.control.viewHelper.SendUndwrtViewHelper;
import com.sinosoft.claim.util.StringConvert;
import com.sinosoft.reins.common.model.PrpLDangerItem;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.log.Logger;
import com.sinosoft.utility.string.Str;

/**
 * <p>
 * Title: CompensateViewHelper
 * </p>
 * <p>
 * Description:实赔ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2013
 * </p>
 * @author 中科软
 * @version 1.0 <br>
 */

public class SunnyCompensateViewHelper extends DAACompensateViewHelper {
	/** Log日志对象 */
	private static Logger logger = Logger.getLogger(SunnyCompensateViewHelper.class);
	/** 理算实赔服务 */
	private CompensateService compensateService;
	/** 立案服务 */
	private ClaimService claimService;
	/** 报案信息服务 */
	private PrpLregistService prpLregistService;
	/** 立案信息服务 */
	private PrpLclaimService prpLclaimService;
	/** 定损服务 */
	private CertainLossService certainLossService;
	/** 报案服务 */
	private RegistService registService;
	/** 重开赔案服务 */
	private RecaseService recaseService;
	/** 计算书免赔条件信息服务 */
	private PrpLdeductCondService prpLdeductCondService;
	/** 报案ViewHelper */
	private DAARegistViewHelper daaRegistViewHelper;
	/** 立案ViewHelper */
	private DAAClaimViewHelper daaClaimViewHelper;
	/** 联共保赔付金额分摊服务 */
	private PrpLcfeecoinsService prpLcfeecoinsService;
	/** 查勘信息服务 */
	private PrpLcheckService prpLcheckService;
	/** 限额/免赔跟踪信息服务 */
	private PrpClimitService prpClimitService;
	/** 人伤信息服务 */
	private PersonLossService personLossService;
	/** 预赔服务 */
	private PrepayService prepayService;
	/** 定核损信息服务 */
	private PrpLverifyLossService prpLverifyLossService;
	/** 单证服务 */
	private CertifyService certifyService;
	/** 免赔条件信息服务 */
	private PrpDdeductCondService prpDdeductCondService;
	/** 代码服务 */
	private CodeService codeService;
	/** 保单数据传输对象服务 */
	private PolicyService policyService;
	/** 工作流意见处理信息服务 */
	private SwfNotionService swfNotionService;
	/** 共保信息服务 */
	private PrpCcoinsService prpCcoinsService;
	/** 险种配置信息服务 */
	private PrpDriskConfigService prpDriskConfigService;
	/** PrpLagent信息服务 */
	private PrpLagentService prpLagentService;
	/** 立案估损信息服务 */
	private PrpLclaimLossService prpLclaimLossService;
	/** 再保管理对象 */
	private ReinsServiceManager reinsServiceManager;
	private PrpCitemKindService prpCitemKindService;
	private PrplregistrpolicyService prpLregistrpolicyService;
	private PrpLthirdPartyService prpLthirdPartyService;
	
	private PrpLlossService prpLlossService;
	private PrpLpersonLossService prpLpersonLossService;
	private PrpLcarInsuranceService prpLcarInsuranceService;
	

	public static SunnyCompensateViewHelper getInstance() {
		return new SunnyCompensateViewHelper();
	}

	/**
	 * 保存实赔时实赔页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return compensateDto 实赔数据传输数据结构
	 * @throws Exception
	 */
	public CompensateDto viewToDto(HttpServletRequest httpServletRequest) throws Exception {

		// 继承对compensate,compensateText表的赋值
		CompensateDto compensateDto = super.viewToDto(httpServletRequest);
		// 加入危险单位处理,目前只有一个危险单位，所以和标的信息放在一起处理，如果，有多个危险单位必须放入危险单位信息里面处理！
		List<PrpLDangerItem> prpLprpLdangerItemList = new ArrayList<PrpLDangerItem>(); // 理赔的危险单位信息表
		// String lastRealPay =
		// httpServletRequest.getParameter("lastRealPay");// 总赔款
		String prpLDeductible = httpServletRequest.getParameter("prpLDeductible");// 免赔额
		String prpLlossDtoRiskCode = httpServletRequest.getParameter("prpLcompensateRiskCode");
		String configCode = this.getCodeService().translateRiskCodetoConfigCode(prpLlossDtoRiskCode);
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		if (DataUtils.emptyToNull(httpServletRequest.getParameter("prpLcompensateindependentCosts")) != null) {
			prpLcompensate.setIndependentCosts(new Double(httpServletRequest.getParameter("prpLcompensateindependentCosts")));
		}
		// 赔付标的信息
		String prpLlossDtoCompensateNo = (String) httpServletRequest.getAttribute("compensateNo");
		// 免赔条件信息
		if ("RISKCODE_DAZ".equals(configCode) == false) {
			List<PrpLdeductCond> prpLdeductCondList = UIDeductCondAction.getInstance().getDeductCondList(httpServletRequest, true);
			compensateDto.getPrpLcompensate().setPrpLdeductCondList(prpLdeductCondList);
		}
		String days = httpServletRequest.getParameter("days");
		String prpLlossDtoPolicyNo = httpServletRequest.getParameter("prpLcompensatePolicyNo");
		String[] prpLlossDtoSerialNo = httpServletRequest.getParameterValues("prpLlossDtoSerialNo");
		String[] prpLlossDtoItemKindNo = httpServletRequest.getParameterValues("prpLlossDtoItemKindNo");
		String[] prpLlossDtoFamilyNo = httpServletRequest.getParameterValues("prpLlossDtoFamilyNo");
		String[] prpLlossDtoFamilyName = httpServletRequest.getParameterValues("prpLlossDtoFamilyName");
		String[] prpLlossDtoKindCode = httpServletRequest.getParameterValues("prpLlossDtoKindCode");
		String[] prpLlossDtoLicenseNo = httpServletRequest.getParameterValues("prpLlossDtoLicenseNo");
		String[] prpLlossDtoItemCode = httpServletRequest.getParameterValues("prpLlossDtoItemCode");
		String[] prpLlossDtoLossName = httpServletRequest.getParameterValues("prpLlossDtoLossName");
		String[] prpLlossDtoItemAddress = httpServletRequest.getParameterValues("prpLlossDtoItemAddress");
		String[] prpLlossDtoFeeTypeCode = httpServletRequest.getParameterValues("prpLlossDtoFeeTypeCode");
		String[] prpLlossDtoFeeTypeName = httpServletRequest.getParameterValues("prpLlossDtoFeeTypeName");
		String[] prpLlossDtoLossQuantity = httpServletRequest.getParameterValues("prpLlossDtoLossQuantity");
		String[] prpLlossDtoUnit = httpServletRequest.getParameterValues("prpLlossDtoUnit");
		String[] prpLlossDtoUnitPrice = httpServletRequest.getParameterValues("prpLlossDtoUnitPrice");
		String[] prpLlossDtoBuyDate = httpServletRequest.getParameterValues("prpLlossDtoBuyDate");
		String[] prpLlossDtoDepreRate = httpServletRequest.getParameterValues("prpLlossDtoDepreRate");
		String[] prpLlossDtoCurrency = httpServletRequest.getParameterValues("prpLlossDtoCurrency");
		String[] prpLlossDtoKindCodeShow = httpServletRequest.getParameterValues("prpLlossDtoKindCodeShow");
		String[] prpLlossDtoAmount = httpServletRequest.getParameterValues("kindAmount");
		String[] prpLlossDtounitAmount = httpServletRequest.getParameterValues("unitAmount");
		String[] prpLlossDtoCurrency1 = httpServletRequest.getParameterValues("prpLlossDtoCurrency1");
		String[] prpLlossDtoItemValue = httpServletRequest.getParameterValues("prpLlossDtoItemValue");
		String[] prpLlossDtoCurrency2 = httpServletRequest.getParameterValues("prpLlossDtoCurrency2");
		String[] prpLlossDtoSumLoss = httpServletRequest.getParameterValues("prpLlossDtoSumLoss");
		String[] prpLlossDtoSumDefPay = httpServletRequest.getParameterValues("prpLlossDtoSumDefPay");
		String[] prpLlossDtoSumRest = httpServletRequest.getParameterValues("prpLlossDtoSumRest");
		String[] prpLlossDtoIndemnityDutyRate = httpServletRequest.getParameterValues("prpLlossDtoIndemnityDutyRate");
		String[] prpLlossDtoArrangeRate = httpServletRequest.getParameterValues("prpLlossDtoArrangeRate");
		String[] prpLlossDtoClaimRate = httpServletRequest.getParameterValues("prpLlossDtoClaimRate");
		String[] prpLlossDtoCurrency3 = httpServletRequest.getParameterValues("prpLlossDtoCurrency3");
		String[] prpLlossDtoDeductibleRate = httpServletRequest.getParameterValues("prpLlossDtoDeductibleRate");
		String[] prpLlossDtoDutyDeductibleRate = httpServletRequest.getParameterValues("prpLlossDtoDutyDeductibleRate");

		String[] prpLlossDtoDriverDeductibleRate = httpServletRequest.getParameterValues("prpLlossDtoDriverDeductibleRate");
		String[] prpLlossDtoCurrency4 = httpServletRequest.getParameterValues("prpLlossDtoCurrency4");
		String[] prpLlossDtoSumRealPay = httpServletRequest.getParameterValues("prpLlossDtoSumRealPay");
		String[] prpLlossDtoCompelPay = httpServletRequest.getParameterValues("prpLlossDtoCompelPay");
		// 理赔拆分危险单位
		String[] prpLlossDtoExceptDeductiblePay = null;
		String[] prpLlossDtoExceptDeductibleRate = null;
		if ("RISKCODE_DAZ".equals(configCode) == false) {
			prpLlossDtoExceptDeductiblePay = httpServletRequest.getParameterValues("prpLlossDtoExceptDeductiblePay");
			prpLlossDtoExceptDeductibleRate = httpServletRequest.getParameterValues("prpLlossDtoExceptDeductibleRate");
		}
		String[] prpLlossDtoFlag = httpServletRequest.getParameterValues("prpLlossDtoFlag");
		String[] prpLlossPayObjectSerialNo = httpServletRequest.getParameterValues("prpLlossPayObjectSerialNo");// 支付对象的序号
		String[] prpLlossReservedEstimate = httpServletRequest.getParameterValues("prpLlossReservedEstimate");// 保留预估
//		String[] prpLlossAccidentType = httpServletRequest.getParameterValues("prpLlossAccidentType");// delete by chenjie 20150601 需求變更-095
		// 对象赋值
		List<PrpLloss> prpLlossList = new ArrayList<PrpLloss>();
		// double alreadySplit = 0.0;
		PrpLloss prpLloss = null;
		// 增加危险单位序号  默认为1
		// 立案中的估损信息有有可能是多条，危险单位信息，不一致，先取第一条。。
		int dangerNo = 1;
		List<PrpLclaimLoss> prpLclaimLossList = prpLclaimLossService.findPrpLclaimLoss(prpLcompensate.getClaimNo());
		if (prpLclaimLossList.size() > 0) {
			Integer i = prpLclaimLossList.get(0).getDangerNo();
			if (i != null) {
				dangerNo = i;
			}
		}

		// 客制化调整，取本次需要的 chenjie 2013-05-06
		// 自负额
		String[] prpLlossDtoDeductible = httpServletRequest.getParameterValues("prpLlossDtoDeductible");
		if (prpLlossDtoSerialNo != null && prpLlossDtoSerialNo.length > 0) {
			for (int index = 1; index < prpLlossDtoSerialNo.length; index++) {
				prpLloss = new PrpLloss();
				prpLloss.setPolicyNo(prpLlossDtoPolicyNo);
				prpLloss.setRiskCode(prpLlossDtoRiskCode);
				prpLloss.getId().setCompensateNo(prpLlossDtoCompensateNo);
				prpLloss.getId().setSerialNo(index);
				prpLloss.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(prpLlossDtoItemKindNo[index])));
				prpLloss.setFamilyNo(Integer.parseInt(DataUtils.nullToZero(prpLlossDtoFamilyNo[index])));
				prpLloss.setFamilyName(prpLlossDtoFamilyName[index]);
				prpLloss.setKindCode(prpLlossDtoKindCode[index]);
				prpLloss.setLicenseNo(prpLlossDtoLicenseNo[index]);
				prpLloss.setItemCode(prpLlossDtoItemCode[index]);
				prpLloss.setLossName(prpLlossDtoLossName[index]);
				prpLloss.setItemAddress(prpLlossDtoItemAddress[index]);
				prpLloss.setFeeTypeCode(prpLlossDtoFeeTypeCode[index]);
				prpLloss.setFeeTypeName(prpLlossDtoFeeTypeName[index]);
				prpLloss.setLossQuantity(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoLossQuantity[index])));
				prpLloss.setUnit(prpLlossDtoUnit[index]);
				prpLloss.setUnitPrice(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoUnitPrice[index])));
				prpLloss.setBuyDate(new DateTime(prpLlossDtoBuyDate[index]));
				prpLloss.setDepreRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoDepreRate[index])));
				prpLloss.setCurrency(prpLlossDtoCurrency[index]);
				for (int i = 0; i < prpLlossDtoAmount.length; i++) {
					if (prpLlossDtoKindCode[index].equals(prpLlossDtoKindCodeShow[i])) {
						prpLloss.setAmount(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoAmount[i])));
						// 设置日约定金额
						if ("T".equals(prpLloss.getKindCode())) {
							prpLloss.setUnitPrice(Double.parseDouble(DataUtils.nullToZero(prpLlossDtounitAmount[i])));
							prpLloss.setLossQuantity(Double.parseDouble(DataUtils.nullToZero(days)));
						}
					}
				}
				prpLloss.setCurrency1(prpLlossDtoCurrency1[index]);
				prpLloss.setItemValue(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoItemValue[index])));
				prpLloss.setCurrency2(prpLlossDtoCurrency2[index]);
				prpLloss.setSumLoss(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoSumLoss[index])));
				prpLloss.setSumRest(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoSumRest[index])));
				prpLloss.setSumDefPay(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoSumDefPay[index])));
				prpLloss.setIndemnityDutyRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoIndemnityDutyRate[index])));
				prpLloss.setArrangeRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoArrangeRate[index])));
				prpLloss.setClaimRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoClaimRate[index])));
				prpLloss.setCurrency3(prpLlossDtoCurrency3[index]);
				prpLloss.setDutyDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoDutyDeductibleRate[index])));
				prpLloss.setDeductiblerate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoDeductibleRate[index])));
				prpLloss.setDriverDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoDriverDeductibleRate[index])));
				prpLloss.setCurrency4(prpLlossDtoCurrency4[index]);
				prpLloss.setSumRealPay(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoSumRealPay[index])));
				prpLloss.setExceptDeductiblePay(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoExceptDeductiblePay[index])));
				prpLloss.setExceptDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoExceptDeductibleRate[index])));
				prpLloss.setFlag(prpLlossDtoFlag[index]);
				// 用於存储标的车实际价值和强制保险赔款
				prpLloss.setCompelPay(Double.parseDouble(prpLlossDtoCompelPay[index]));
				if (ConstantsCollection.MainCarLoss.contains(prpLlossDtoKindCode[index])) {// 主车损失存备案时标的车实际价值（富邦是A、G、AB）
					prpLloss.setCarRealValue(Double.parseDouble((String) httpServletRequest.getParameter("factValue")));
				}
				// 对免赔额进行分摊 ，只有输入了免赔额，才进行分摊
				if (DataUtils.emptyToNull(prpLDeductible) == null) {
					prpLDeductible = "0.00";
				}
				prpLloss.setDeductible(Double.valueOf(prpLlossDtoDeductible[index]));// 自负额
				prpLloss.setDangerNo(dangerNo);// 增加危险代位信息
				if (prpLlossPayObjectSerialNo[index] != null && !"".equals(prpLlossPayObjectSerialNo[index])) {
					prpLloss.setPayObjectSerialNo(prpLlossPayObjectSerialNo[index]);
				}
				prpLloss.setReservedEstimate(CommonUtils.getValue(prpLlossReservedEstimate,index));
//				prpLloss.setAccidentType(CommonUtils.getValue(prpLlossAccidentType,index));//delete by chenjie 20150601 需求變更-095
				prpLlossList.add(prpLloss);
			}
		}
		// -----------------------------------------------------------------
		String strConfigCode = this.getCodeService().translateRiskCodetoConfigCode(prpLlossDtoRiskCode);
		if ("RISKCODE_DAZ".equals(strConfigCode)) {
			// this.lossCompelInit(httpServletRequest,
			// prpLlossList);//客制化强制险车物损不再收集
		}
		compensateDto.setPrpLlossList(prpLlossList);
		// 从界面得到输入数组
		String prpLpersonLossCompensateNo = (String) httpServletRequest.getAttribute("compensateNo");
		String prpLpersonLossRiskCode = httpServletRequest.getParameter("prpLcompensateRiskCode");
		String prpLpersonLossPolicyNo = httpServletRequest.getParameter("prpLcompensatePolicyNo");

		String[] personLossSerialNo = httpServletRequest.getParameterValues("personLossSerialNo");
		String[] prpLpersonLossSerialNo = httpServletRequest.getParameterValues("prpLpersonLossSerialNo");
		String[] prpLpersonLossPersonName = httpServletRequest.getParameterValues("prpLpersonLossPersonName");
		String[] prpLpersonLossIdentifyNumber = httpServletRequest.getParameterValues("prpLpersonLossIdentifyNumber");
		String[] prpLpersonLossSex = httpServletRequest.getParameterValues("prpLpersonLossSex");
		String[] prpLpersonLossAge = httpServletRequest.getParameterValues("prpLpersonLossAge");
		String[] prpLpersonLossItemKindNo = httpServletRequest.getParameterValues("prpLpersonLossItemKindNo");
		String[] prpLpersonLossFamilyNo = httpServletRequest.getParameterValues("prpLpersonLossFamilyNo");
		String[] prpLpersonLossFamilyName = httpServletRequest.getParameterValues("prpLpersonLossFamilyName");
		String[] prpLpersonLossKindCode = httpServletRequest.getParameterValues("prpLpersonLossKindCode");
		String[] prpLpersonLossLiabCode = httpServletRequest.getParameterValues("prpLpersonLossLiabCode");
		String[] prpLpersonLossLiabName = httpServletRequest.getParameterValues("prpLpersonLossLiabName");
		String[] prpLpersonLossJobCode = httpServletRequest.getParameterValues("prpLpersonLossJobCode");
		String[] prpLpersonLossJobName = httpServletRequest.getParameterValues("prpLpersonLossJobName");
		String[] prpLpersonLossLiabDetailCode = httpServletRequest.getParameterValues("prpLpersonLossLiabDetailCode");
		String[] prpLpersonLossLiabDetailName = httpServletRequest.getParameterValues("prpLpersonLossLiabDetailName");
		String[] prpLpersonLossItemAddress = httpServletRequest.getParameterValues("prpLpersonLossItemAddress");
		String[] prpLpersonLossLossQuantity = httpServletRequest.getParameterValues("prpLpersonLossLossQuantity");
		String[] prpLpersonLossUnit = httpServletRequest.getParameterValues("prpLpersonLossUnit");
		String[] prpLpersonLossUnitAmount = httpServletRequest.getParameterValues("prpLpersonLossUnitAmount");
		String[] prpLpersonLossHospitalDays = httpServletRequest.getParameterValues("prpLpersonLossHospitalDays");
		String[] prpLpersonLossCurrency = httpServletRequest.getParameterValues("prpLpersonLossCurrency");
		String[] prpLpersonLossAmount = httpServletRequest.getParameterValues("prpLpersonLossAmount");
		String[] prpLpersonLossCurrency1 = httpServletRequest.getParameterValues("prpLpersonLossCurrency1");
		String[] prpLpersonLossItemValue = httpServletRequest.getParameterValues("prpLpersonLossItemValue");
		String[] prpLpersonLossCurrency2 = httpServletRequest.getParameterValues("prpLpersonLossCurrency2");
		String[] prpLpersonLossSumLoss = httpServletRequest.getParameterValues("prpLpersonLossSumLoss");
		String[] prpLpersonLossSumRest = httpServletRequest.getParameterValues("prpLpersonLossSumRest");
		String[] prpLpersonLossSumDefPay = httpServletRequest.getParameterValues("prpLpersonLossSumDefPay");
		String[] prpLpersonLossIndemnityDutyRate = httpServletRequest.getParameterValues("prpLpersonLossIndemnityDutyRate");
		String[] prpLpersonLossPersonpaid = httpServletRequest.getParameterValues("prpLpersonLossSumRealPay1");
		String[] prpLpersonLossArrangeRate = httpServletRequest.getParameterValues("prpLpersonLossArrangeRate");
		String[] prpLpersonLossClaimRate = httpServletRequest.getParameterValues("prpLpersonLossClaimRate");
		String[] prpLpersonLossCurrency3 = httpServletRequest.getParameterValues("prpLpersonLossCurrency3");
		String[] prpLpersonLossDeductibleRate = httpServletRequest.getParameterValues("prpLpersonLossDeductibleRate");
		String[] prpLpersonLossDutyDeductibleRate = httpServletRequest.getParameterValues("prpLpersonLossDutyDeductibleRate");

		String[] prpLpersonLossDriverDeductibleRate = httpServletRequest.getParameterValues("prpLpersonLossDriverDeductibleRate");

		String[] prpLpersonLossMainKindDeductibleRate = httpServletRequest.getParameterValues("prpLpersonLossMainKindDeductibleRate");
		String[] prpLpersonLossDeductible = httpServletRequest.getParameterValues("prpLpersonLossDeductible");
		String[] prpLpersonLossCurrency4 = httpServletRequest.getParameterValues("prpLpersonLossCurrency4");
		String[] prpLpersonLossSumRealPay = httpServletRequest.getParameterValues("prpLpersonLossSumRealPay");
		String[] prpLpersonLossExceptDeductiblePay = null;
		String[] prpLpersonLossExceptDeductibleRate = null;

		String[] prpLpersonLossBirthday = httpServletRequest.getParameterValues("prpLpersonLossBirthday");
		String[] prpLpersonLossIdentityOfInjuredPerson = httpServletRequest.getParameterValues("prpLpersonLossIdentityOfInjuredPerson");
		String[] prpLpersonLossRideSituation = httpServletRequest.getParameterValues("prpLpersonLossRideSituation");
		String[] prpLpersonLossMedicalCode = httpServletRequest.getParameterValues("prpLpersonLossMedicalCode");
		String[] prpLpersonLossEndCaseAndRecoverFlag = httpServletRequest.getParameterValues("prpLpersonLossEndCaseAndRecoverFlag");
		String[] prpLpersonLossTelephoneNo = httpServletRequest.getParameterValues("prpLpersonLossTelephoneNo");
		String[] prpLpersonLossProsecutorsOffice = httpServletRequest.getParameterValues("prpLpersonLossProsecutorsOffice");
		String[] prpLpersonLossCourtDoctor = httpServletRequest.getParameterValues("prpLpersonLossCourtDoctor");
		String[] prpLpersonLossMobilePhone = httpServletRequest.getParameterValues("prpLpersonLossMobilePhone");
		String[] prpLpersonLossProsecutor = httpServletRequest.getParameterValues("prpLpersonLossProsecutor");
		String[] prpLpersonLossGarageHeadName = httpServletRequest.getParameterValues("prpLpersonLossGarageHeadName");
		String[] prpLpersonLossHospitalCode = httpServletRequest.getParameterValues("prpLpersonLossHospitalCode");
		String[] prpLpersonLossHospitalName = httpServletRequest.getParameterValues("prpLpersonLossHospitalName");
		String[] prpLpersonLossDoctor = httpServletRequest.getParameterValues("prpLpersonLossDoctor");
		String[] prpLpersonLossCasualties = httpServletRequest.getParameterValues("prpLpersonLossCasualties");
		String[] prpLpersonLossInjuryGrade = httpServletRequest.getParameterValues("prpLpersonLossInjuryGrade");
		// 赔付人员序号
		String[] prpLpersonLossPayObjectSerialNo = httpServletRequest.getParameterValues("prpLpersonLossPayObjectSerialNo");
		// 婚姻别
		String[] prpLpersonLossIsMarried = httpServletRequest.getParameterValues("prpLpersonLossIsMarried");

		if ("RISKCODE_DAZ".equals(this.getCodeService().translateRiskCodetoConfigCode(prpLpersonLossRiskCode)) == false) {
			prpLpersonLossExceptDeductiblePay = httpServletRequest.getParameterValues("prpLpersonLossExceptDeductiblePay");
			prpLpersonLossExceptDeductibleRate = httpServletRequest.getParameterValues("prpLpersonLossExceptDeductibleRate");
		}
		String[] prpLpersonLossFlag = httpServletRequest.getParameterValues("prpLpersonLossFlag");
		String[] prpLpersonLossCompelPay = httpServletRequest.getParameterValues("prpLpersonLossCompelPay");
		String[] prpLpersonLossReservedEstimate = httpServletRequest.getParameterValues("prpLpersonLossReservedEstimate");
//		String[] prpLpersonLossAccidentType = httpServletRequest.getParameterValues("prpLpersonLossAccidentType");// delete by chenjie 20150601 需求變更-095
		String strRiskType = this.getCodeService().translateRiskCodetoRiskType(prpLpersonLossRiskCode);
		// 赔付人员信息
		List<PrpLpersonLoss> prpLpersonLossList = new ArrayList<PrpLpersonLoss>();
		PrpLpersonLoss prpLpersonLoss = null;
		if (personLossSerialNo != null && personLossSerialNo.length > 0) {
			if (prpLpersonLossRiskCode != null && "D".equals(strRiskType)) {
				// 对象赋值
				for (int index = 1; index < personLossSerialNo.length; index++) {
					prpLpersonLoss = new PrpLpersonLoss();
					prpLpersonLoss.setPolicyNo(prpLpersonLossPolicyNo);
					prpLpersonLoss.setRiskCode(prpLpersonLossRiskCode);
					prpLpersonLoss.getId().setCompensateNo(prpLpersonLossCompensateNo);
					prpLpersonLoss.getId().setSerialNo(index);
					prpLpersonLoss.setFamilyNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossFamilyNo[index])));
					prpLpersonLoss.setLiabCode(prpLpersonLossLiabCode[index]);
					prpLpersonLoss.setLiabName(prpLpersonLossLiabName[index]);
					prpLpersonLoss.setJobCode(prpLpersonLossJobCode[index]);
					prpLpersonLoss.setJobName(prpLpersonLossJobName[index]);
					prpLpersonLoss.setItemAddress(prpLpersonLossItemAddress[index]);
					prpLpersonLoss.setUnit(prpLpersonLossUnit[index]);
					prpLpersonLoss.setCurrency(prpLpersonLossCurrency[index]);
					prpLpersonLoss.setAmount(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossAmount[index])));
					prpLpersonLoss.setCurrency1(prpLpersonLossCurrency1[index]);
					prpLpersonLoss.setItemValue(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossItemValue[index])));
					prpLpersonLoss.setCurrency2(prpLpersonLossCurrency2[index]);
					prpLpersonLoss.setSumRest(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossSumRest[index])));
					prpLpersonLoss.setClaimRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossClaimRate[index])));
					prpLpersonLoss.setDeductible(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossDeductible[index])));
					prpLpersonLoss.setCurrency4(prpLpersonLossCurrency4[index]);
					prpLpersonLoss.setSumRealPay(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossSumRealPay[index])));
					prpLpersonLoss.setFlag(prpLpersonLossFlag[index]);
					prpLpersonLoss.setLiabDetailCode(prpLpersonLossLiabDetailCode[index]);
					prpLpersonLoss.setLiabDetailName(prpLpersonLossLiabDetailName[index]);
					prpLpersonLoss.setUnitAmount(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossUnitAmount[index])));

					prpLpersonLoss.setHospitalDays(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossHospitalDays[index])));
					prpLpersonLoss.setLossQuantity(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossLossQuantity[index])));
					prpLpersonLoss.setSumLoss(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossSumLoss[index])));
					prpLpersonLoss.setSumDefPay(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossSumDefPay[index])));
					prpLpersonLoss.setCompelPay(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossCompelPay[index])));
					prpLpersonLoss.setExceptDeductiblePay(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossExceptDeductiblePay[index])));
					prpLpersonLoss.setExceptDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossExceptDeductibleRate[index])));

					prpLpersonLoss.setInjuryGrade(prpLpersonLossInjuryGrade[index]);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					for (int index2 = 1; index2 < prpLpersonLossSerialNo.length; index2++) {
						if (prpLpersonLossSerialNo[index2].equals(personLossSerialNo[index])) {
							// 少数派
							prpLpersonLoss.setSex(prpLpersonLossSex[index2]);
							prpLpersonLoss.setPersonName(prpLpersonLossPersonName[index2]);
							prpLpersonLoss.setAge(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossAge[index2])));
							prpLpersonLoss.setCurrency3(prpLpersonLossCurrency3[index2]);
							prpLpersonLoss.setKindCode(prpLpersonLossKindCode[index2]);
							prpLpersonLoss.setFamilyName(prpLpersonLossFamilyName[index2]);// 车牌号码

							prpLpersonLoss.setIndemnityDutyRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossIndemnityDutyRate[index2])));
							prpLpersonLoss.setPersonpaid(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossPersonpaid[index2])));
							prpLpersonLoss.setArrangeRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossArrangeRate[index2])));

							prpLpersonLoss.setKindCode(prpLpersonLossKindCode[index2]);
							prpLpersonLoss.setDutyDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossDutyDeductibleRate[index2])));
							prpLpersonLoss.setMainKindDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossMainKindDeductibleRate[index2])));
							prpLpersonLoss.setDeductiblerate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossDeductibleRate[index2])));
							prpLpersonLoss.setDriverDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossDriverDeductibleRate[index2])));
							prpLpersonLoss.setPersonNo(index2);
							if ("RISKCODE_DAZ".equals(strConfigCode)) {
								prpLpersonLoss.setKindCode(prpLpersonLossKindCode[index2]);
								prpLpersonLoss.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossItemKindNo[index2])));
								if (null != prpLpersonLossPayObjectSerialNo[index2] && !"".equals(prpLpersonLossPayObjectSerialNo[index2])) {
									prpLpersonLoss.setPayObjectSerialNo(prpLpersonLossPayObjectSerialNo[index2]);
								}
							}
							prpLpersonLoss.setBirthday(DataUtils.emptyToNull(prpLpersonLossBirthday[index2]) == null ? null : format.parse(prpLpersonLossBirthday[index2]));
							prpLpersonLoss.setIdentityOfInjuredPerson(prpLpersonLossIdentityOfInjuredPerson[index2]);
							prpLpersonLoss.setRideSituation(prpLpersonLossRideSituation[index2]);
							prpLpersonLoss.setIdentifyNumber(prpLpersonLossIdentifyNumber[index2]);
							prpLpersonLoss.setMedicalCode(prpLpersonLossMedicalCode[index2]);
							prpLpersonLoss.setEndCaseAndRecoverFlag(prpLpersonLossEndCaseAndRecoverFlag[index2]);
							prpLpersonLoss.setTelephoneNo(prpLpersonLossTelephoneNo[index2]);
							prpLpersonLoss.setProsecutorsOffice(prpLpersonLossProsecutorsOffice[index2]);
							if (null != DataUtils.emptyToNull(prpLpersonLossProsecutorsOffice[index2])) {
								prpLpersonLoss.setProsecutorsOfficeName(ConstantsCollection.prosecutorsOfficeList.get(prpLpersonLossProsecutorsOffice[index2]).substring(10));
							}
							prpLpersonLoss.setCourtDoctor(prpLpersonLossCourtDoctor[index2]);
							prpLpersonLoss.setMobilePhone(prpLpersonLossMobilePhone[index2]);
							prpLpersonLoss.setProsecutor(prpLpersonLossProsecutor[index2]);
							prpLpersonLoss.setGarageHeadName(prpLpersonLossGarageHeadName[index2]);
							prpLpersonLoss.setHospitalCode(prpLpersonLossHospitalCode[index2]);
							prpLpersonLoss.setHospitalName(prpLpersonLossHospitalName[index2]);
							prpLpersonLoss.setDoctor(prpLpersonLossDoctor[index2]);
							prpLpersonLoss.setCasualties(prpLpersonLossCasualties[index2]);
							prpLpersonLoss.setIsMarried(prpLpersonLossIsMarried[index2]);
							break;
						}
					}
					if (!"RISKCODE_DAZ".equals(strConfigCode)) {
						prpLpersonLoss.setKindCode(prpLpersonLossKindCode[index]);
						prpLpersonLoss.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossItemKindNo[index])));
						if (null != prpLpersonLossPayObjectSerialNo[index] && !"".equals(prpLpersonLossPayObjectSerialNo[index])) {
							prpLpersonLoss.setPayObjectSerialNo(prpLpersonLossPayObjectSerialNo[index]);
						}
					}
					prpLpersonLoss.setReservedEstimate(CommonUtils.getValue(prpLpersonLossReservedEstimate,index));
//					prpLpersonLoss.setAccidentType(CommonUtils.getValue(prpLpersonLossAccidentType,index));// delete by chenjie 20150601 需求變更-095
					prpLpersonLoss.setDangerNo(dangerNo);// 增加危险代位信息
					prpLpersonLossList.add(prpLpersonLoss);
				}
			} else {
				// 对象赋值
				for (int index = 1; index < personLossSerialNo.length; index++) {
					prpLpersonLoss = new PrpLpersonLoss();
					prpLpersonLoss.setPolicyNo(prpLpersonLossPolicyNo);
					prpLpersonLoss.setRiskCode(prpLpersonLossRiskCode);
					prpLpersonLoss.getId().setCompensateNo(prpLpersonLossCompensateNo);
					prpLpersonLoss.getId().setSerialNo(index);
					prpLpersonLoss.setIdentifyNumber(prpLpersonLossIdentifyNumber[index]);
					prpLpersonLoss.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossItemKindNo[index])));
					prpLpersonLoss.setFamilyNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossFamilyNo[index])));
					prpLpersonLoss.setLiabCode(prpLpersonLossLiabCode[index]);
					prpLpersonLoss.setLiabName(prpLpersonLossLiabName[index]);
					prpLpersonLoss.setJobCode(prpLpersonLossJobCode[index]);
					prpLpersonLoss.setJobName(prpLpersonLossJobName[index]);
					prpLpersonLoss.setItemAddress(prpLpersonLossItemAddress[index]);
					prpLpersonLoss.setUnit(prpLpersonLossUnit[index]);
					prpLpersonLoss.setCurrency(prpLpersonLossCurrency[index]);
					prpLpersonLoss.setAmount(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossAmount[index])));
					prpLpersonLoss.setCurrency1(prpLpersonLossCurrency1[index]);
					prpLpersonLoss.setItemValue(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossItemValue[index])));
					prpLpersonLoss.setCurrency2(prpLpersonLossCurrency2[index]);
					prpLpersonLoss.setSumRest(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossSumRest[index])));
					prpLpersonLoss.setClaimRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossClaimRate[index])));
					prpLpersonLoss.setDeductible(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossDeductible[index])));
					prpLpersonLoss.setCurrency4(prpLpersonLossCurrency4[index]);
					prpLpersonLoss.setSumRealPay(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossSumRealPay[index])));
					prpLpersonLoss.setFlag(prpLpersonLossFlag[index]);
					prpLpersonLoss.setLiabDetailCode(prpLpersonLossLiabDetailCode[index]);
					prpLpersonLoss.setLiabDetailName(prpLpersonLossLiabDetailName[index]);
					prpLpersonLoss.setUnitAmount(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossUnitAmount[index])));
					prpLpersonLoss.setHospitalDays(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossHospitalDays[index])));
					prpLpersonLoss.setLossQuantity(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossLossQuantity[index])));
					prpLpersonLoss.setSumLoss(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossSumLoss[index])));
					prpLpersonLoss.setCurrency3(prpLpersonLossCurrency3[index]);
					prpLpersonLoss.setFamilyName(prpLpersonLossFamilyName[index]);
					prpLpersonLoss.setIndemnityDutyRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossIndemnityDutyRate[index])));
					prpLpersonLoss.setPersonpaid(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossPersonpaid[index])));
					prpLpersonLoss.setArrangeRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossArrangeRate[index])));

					prpLpersonLoss.setKindCode(prpLpersonLossKindCode[index]);
					prpLpersonLoss.setDutyDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossDutyDeductibleRate[index])));
					prpLpersonLoss.setDriverDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossDriverDeductibleRate[index])));
					prpLpersonLoss.setDeductiblerate(Double.parseDouble(DataUtils.nullToZero(prpLpersonLossDeductibleRate[index])));
					prpLpersonLoss.setPersonNo(index);
					for (int index2 = 0; index2 < prpLpersonLossSerialNo.length; index2++) {
						if (prpLpersonLossSerialNo[index2].equals(personLossSerialNo[index])) {
							prpLpersonLoss.setAge(Integer.parseInt(DataUtils.nullToZero(prpLpersonLossAge[index2])));
							prpLpersonLoss.setPersonNo(index2);
							prpLpersonLoss.setPersonName(prpLpersonLossPersonName[index2]);
							prpLpersonLoss.setSex(prpLpersonLossSex[index2]);
							break;
						}
					}
					prpLpersonLoss.setDangerNo(dangerNo);// 增加危险代位信息
					// 加入集合
					prpLpersonLossList.add(prpLpersonLoss);
				}
			}
		}
		if ("RISKCODE_DAZ".equals(strConfigCode)) {
			this.personLossCompelInit(httpServletRequest, prpLpersonLossList);
		}
		compensateDto.setPrpLpersonLossList(prpLpersonLossList);
		// 联共保信息
		String prpLcfeecoinsCompensateNo = (String) httpServletRequest.getAttribute("compensateNo");
		String prpLcfeecoinsPolicyNo = httpServletRequest.getParameter("prpLcompensatePolicyNo");
		String prpLcfeecoinsRiskCode = httpServletRequest.getParameter("prpLcompensateRiskCode");
		String[] prpLcfeecoinsSerialNo = httpServletRequest.getParameterValues("prpLcoinsSerialNo");
		String[] prpLcfeecoinsChargeCode = httpServletRequest.getParameterValues("prpLcoinsChargeCode");
		String[] prpLcfeecoinsChargeName = httpServletRequest.getParameterValues("prpLcoinsChargeName");
		String[] prpLcfeecoinsCurrency = httpServletRequest.getParameterValues("prpLcoinsCurrency");
		String[] prpLcfeecoinsLossFeeType = httpServletRequest.getParameterValues("prpLcoinsLossFeeType");
		String[] prpLcfeecoinsCoinsCode = httpServletRequest.getParameterValues("prpLcoinsCoinsCode");
		String[] prpLcfeecoinsCoinsName = httpServletRequest.getParameterValues("prpLcoinsCoinsName");
		String[] prpLcfeecoinsCoinsType = httpServletRequest.getParameterValues("prpLcoinsCoinsType");
		String[] prpLcfeecoinsChiefFlag = httpServletRequest.getParameterValues("prpLcoinsChiefFlag");
		String[] prpLcfeecoinsCoinsRate = httpServletRequest.getParameterValues("prpLcoinsCoinsRate");
		String[] prpLcfeecoinsCoinsSumpaid = httpServletRequest.getParameterValues("prpLcoinsCoinsSumpaid");
		String[] prpLcfeecoinsSumpaid = httpServletRequest.getParameterValues("prpLcoinsSumpaid");
		String[] prpLcoinsFlag = httpServletRequest.getParameterValues("prpLcoinsFlag");

		List<PrpLcfeecoins> prpLcfeecoinsList = new ArrayList<PrpLcfeecoins>();
		PrpLcfeecoins prpLcfeecoins = null;
		if (prpLcfeecoinsSerialNo != null && prpLcfeecoinsSerialNo.length > 0) {
			for (int index = 1; index < prpLcfeecoinsSerialNo.length; index++) {
				prpLcfeecoins = new PrpLcfeecoins();
				prpLcfeecoins.getId().setBusinessNo(prpLcfeecoinsCompensateNo);
				prpLcfeecoins.setPolicyNo(prpLcfeecoinsPolicyNo);
				prpLcfeecoins.setRiskCode(prpLcfeecoinsRiskCode);
				prpLcfeecoins.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLcfeecoinsSerialNo[index])));
				prpLcfeecoins.setChargeCode(prpLcfeecoinsChargeCode[index]);
				prpLcfeecoins.setChargeName(prpLcfeecoinsChargeName[index]);
				prpLcfeecoins.setCurrency(prpLcfeecoinsCurrency[index]);
				prpLcfeecoins.setLossFeeType(prpLcfeecoinsLossFeeType[index]);
				prpLcfeecoins.setCoinsCode(prpLcfeecoinsCoinsCode[index]);
				prpLcfeecoins.setCoinsName(prpLcfeecoinsCoinsName[index]);
				prpLcfeecoins.setCoinsType(prpLcfeecoinsCoinsType[index]);
				prpLcfeecoins.setChiefFlag(prpLcfeecoinsChiefFlag[index]);
				prpLcfeecoins.setCoinsRate(Double.parseDouble(DataUtils.nullToZero(prpLcfeecoinsCoinsRate[index])));
				prpLcfeecoins.setCoinsSumPaid(Double.parseDouble(DataUtils.nullToZero(prpLcfeecoinsCoinsSumpaid[index])));
				prpLcfeecoins.setSumPaid(Double.parseDouble(DataUtils.nullToZero(prpLcfeecoinsSumpaid[index])));
				prpLcfeecoins.setFlag(prpLcoinsFlag[index]);
				prpLcfeecoinsList.add(prpLcfeecoins);
			}
		}
		compensateDto.setPrpLcfeecoinsList(prpLcfeecoinsList);// 联共保信息收集结束
		strConfigCode = this.getCodeService().translateRiskCodetoConfigCode(prpLpersonLossRiskCode);

		/******************* 费用资讯信息 start ******************************/
		// 从界面得到输入数组
		String prpLchargePolicyNo = httpServletRequest.getParameter("prpLcompensatePolicyNo");
		String prpLchargeRiskCode = httpServletRequest.getParameter("prpLcompensateRiskCode");
		String prpLchargeCompensateNo = (String) httpServletRequest.getAttribute("compensateNo");
		String[] prpLchargeSerialNo = httpServletRequest.getParameterValues("prpLchargeSerialNo");
		String[] prpLchargeKindCode = httpServletRequest.getParameterValues("prpLchargeKindCode");
		String[] prpLchargeChargeCode = httpServletRequest.getParameterValues("prpLchargeChargeCode");
		String[] prpLchargeChargeName = httpServletRequest.getParameterValues("prpLchargeChargeName");
		String[] prpLchargeCurrency = httpServletRequest.getParameterValues("prpLchargeCurrency");
		String[] prpLchargeChargeAmount = httpServletRequest.getParameterValues("prpLchargeChargeAmount");
		String[] prpLchargeSumRealPay = httpServletRequest.getParameterValues("prpLchargeSumRealPay");
		String[] prpLchargeFlag = httpServletRequest.getParameterValues("prpLchargeFlag");
		String[] prpLchargeChargeReport = httpServletRequest.getParameterValues("prpLchargeChargeReport");
		String[] prpLchargeExceptDeductiblePay = httpServletRequest.getParameterValues("prpLchargeExceptDeductiblePay");
		String[] prpLchargeExceptDeductibleRate = httpServletRequest.getParameterValues("prpLchargeExceptDeductibleRate");
		String[] prpLchargeChargeLicenseNo = httpServletRequest.getParameterValues("prpLchargeChargeLicenseNo");
		String[] prpLchargePayObjectType = httpServletRequest.getParameterValues("prpLchargePayObjectType"); //
		String[] prpLchargePayObjectCode = httpServletRequest.getParameterValues("prpLchargePayObjectCode"); //
		String[] prpLchargePayObjectName = httpServletRequest.getParameterValues("prpLchargePayObjectName");

		// 增加对支付对象的保存
		String[] prpLchargeOwnerShip = httpServletRequest.getParameterValues("prpLchargeOwnerShip");// 費用支付方式
		String[] prpLchargeOwnerName = httpServletRequest.getParameterValues("prpLchargeOwnerName");// 賠付對象
		String[] prpLchargeUniformNo = httpServletRequest.getParameterValues("prpLchargeUniformNo");// ID/統一編號
		String[] prpLchargeCutBack = httpServletRequest.getParameterValues("prpLchargeCutBack");// 禁背
		String[] prpLchargeBankCode = httpServletRequest.getParameterValues("prpLchargeBankCode");// 總行代號
		String[] prpLchargeBankName = httpServletRequest.getParameterValues("prpLchargeBankName");// 總行名稱
		String[] prpLchargeAccountCode = httpServletRequest.getParameterValues("prpLchargeAccountCode");// 匯款帳號
		String[] prpLchargeCustomBankCode = httpServletRequest.getParameterValues("prpLchargeCustomBankCode");// 分行代號
		String[] prpLchargeCustomBankName = httpServletRequest.getParameterValues("prpLchargeCustomBankName");// 分行名稱
		String[] prpLchargeAreaCode = httpServletRequest.getParameterValues("prpLchargeAreaCode");// 郵遞區號
		String[] prpLchargeCourierAddress = httpServletRequest.getParameterValues("prpLchargeCourierAddress");// 郵遞地址
		String[] prpLchargeCertificateCode = httpServletRequest.getParameterValues("prpLchargeCertificateCode");// 證件類型
		String[] prpLchargeAMLFlag = httpServletRequest.getParameterValues("prpLchargeAMLFlag");// AML命中結果

		if ("RISKCODE_DAZ".equals(strConfigCode) == false) {
			prpLchargeExceptDeductiblePay = httpServletRequest.getParameterValues("prpLchargeExceptDeductiblePay");
			prpLchargeExceptDeductibleRate = httpServletRequest.getParameterValues("prpLchargeExceptDeductibleRate");
		}

		// 赔款费用信息
		List<PrpLcharge> prpLchargeList = new ArrayList<PrpLcharge>();
		// 支付对象信息，存储费用支付对象和赔款支付对象，certiType/**业务类型01赔款，02费用*/区分
		List<PrpLpayObjectInfo> prpLpayObjectInfoList = new ArrayList<PrpLpayObjectInfo>();
		PrpLcharge prpLcharge = null;
		PrpLpayObjectInfo prpLpayObjectInfo = null;

		// 对象赋值
		if (prpLchargeSerialNo != null && prpLchargeSerialNo.length > 0) {
			for (int index = 1; index < prpLchargeSerialNo.length; index++) {
				prpLcharge = new PrpLcharge();
				prpLcharge.setPolicyNo(prpLchargePolicyNo);
				prpLcharge.setRiskCode(prpLchargeRiskCode);
				prpLcharge.getId().setCompensateNo(prpLchargeCompensateNo);
				prpLcharge.getId().setSerialNo(index);
				prpLcharge.setKindCode(prpLchargeKindCode[index]);
				prpLcharge.setChargeCode(prpLchargeChargeCode[index]);
				prpLcharge.setChargeName(prpLchargeChargeName[index]);
				prpLcharge.setCurrency(prpLchargeCurrency[index]);
				prpLcharge.setChargeAmount(Double.parseDouble(DataUtils.nullToZero(prpLchargeChargeAmount[index])));
				prpLcharge.setSumRealPay(Double.parseDouble(DataUtils.nullToZero(prpLchargeSumRealPay[index])));
				prpLcharge.setFlag(prpLchargeFlag[index]);
				prpLcharge.setPayObjectCode(prpLchargePayObjectCode[index]);
				prpLcharge.setPayObjectType(prpLchargePayObjectType[index]);
				prpLcharge.setPayObjectName(prpLchargePayObjectName[index]);
				prpLcharge.setOwnerShip(prpLchargeOwnerShip[index]);
				if ("RISKCODE_DAZ".equals(strConfigCode) == false) {
					prpLcharge.setLicenseNo(prpLchargeChargeLicenseNo[index]);
					prpLcharge.setExceptDeductiblePay(Double.parseDouble(DataUtils.nullToZero(prpLchargeExceptDeductiblePay[index])));
					prpLcharge.setExceptDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLchargeExceptDeductibleRate[index])));
				}
				prpLcharge.setChargeReport(Double.parseDouble(DataUtils.nullToZero(prpLchargeChargeReport[index])));
				prpLcharge.setDangerNo(dangerNo);// 增加危险单位信息
				if (prpLchargeAMLFlag!=null&&prpLchargeAMLFlag.length>0) {
					prpLcharge.setAmlFlag(prpLchargeAMLFlag[index]) ;
					prpLcharge.setAmlDate(new Date()) ;
				}	

				prpLpayObjectInfo = new PrpLpayObjectInfo();
				// 增加对支付对象的保存
				prpLpayObjectInfo.getId().setCompensateNo(prpLchargeCompensateNo);
				prpLpayObjectInfo.getId().setCertiType(PrpLpayObjectInfo.CERTITYPE_CHARGE);
				prpLpayObjectInfo.getId().setSerialNo(index);
				prpLpayObjectInfo.setRiskCode(prpLchargeRiskCode);
				prpLpayObjectInfo.setKindCode(prpLchargeKindCode[index]);

				prpLpayObjectInfo.setOwnerName(prpLchargeOwnerName[index]);
				prpLpayObjectInfo.setUniformNo(prpLchargeUniformNo[index]);
				prpLpayObjectInfo.setAreaCode(prpLchargeAreaCode[index]);
				prpLpayObjectInfo.setCourierAddress(prpLchargeCourierAddress[index]);
				prpLpayObjectInfo.setOwnerShip(prpLchargeOwnerShip[index]);
				if ("B".equals(prpLchargeOwnerShip[index])) {// 汇款
					prpLpayObjectInfo.setBankCode(prpLchargeBankCode[index]);
					prpLpayObjectInfo.setBankName(prpLchargeBankName[index]);
					prpLpayObjectInfo.setAccountCode(prpLchargeAccountCode[index]);
					prpLpayObjectInfo.setCustomBankCode(prpLchargeCustomBankCode[index]);
					prpLpayObjectInfo.setCustomBankName(prpLchargeCustomBankName[index]);
				} else if ("Q".equals(prpLchargeOwnerShip[index])) {// 支票
					prpLpayObjectInfo.setCutBack(prpLchargeCutBack[index]);
				}
				// 存实际费用
				prpLpayObjectInfo.setPayAmount(Double.parseDouble(DataUtils.nullToZero(prpLchargeChargeAmount[index])));
				// 證件類型
				prpLpayObjectInfo.setCertificateCode(prpLchargeCertificateCode[index]);
				if (prpLchargeAMLFlag!=null&&prpLchargeAMLFlag.length>0) {
					prpLpayObjectInfo.setAmlFlag(prpLchargeAMLFlag[index]) ;
					prpLpayObjectInfo.setAmlDate(new Date()) ;
				}	
				prpLchargeList.add(prpLcharge);
				prpLpayObjectInfoList.add(prpLpayObjectInfo);
			}
		}
		// 赔款费用信息
		compensateDto.setPrpLchargeList(prpLchargeList);
		compensateDto.setPrpLpayObjectInfoList(prpLpayObjectInfoList);
		/******************* 费用资讯信息 end ******************************/

		/******************* 工作质量审核信息 start ******************************/
		List<PrpLqualityCheck> prpLqualityCheckList = new ArrayList<PrpLqualityCheck>();
		PrpLqualityCheck prpLqualityCheck = null;
		String strCount = httpServletRequest.getParameter("txtRecordNum");
		int intCount = Integer.parseInt(strCount);
		int j = 0;
		String strQuestionCode = "";
		String strQuestionName = "";
		String strQuestionRemark = "";
		String strVisitBackQueRes = "";
		for (int i = 0; i < intCount; i++) {
			j = i + 1;
			strQuestionCode = "txtQuestionCode" + j;
			strQuestionName = "txtQuestionName" + j;
			strQuestionRemark = "txtQuestionRemark" + j;
			strVisitBackQueRes = "VisitBackQue" + j;
			prpLqualityCheck = new PrpLqualityCheck();
			prpLqualityCheck.getId().setRegistNo(prpLcompensate.getCompensateNo());
			prpLqualityCheck.getId().setQualityCheckType(httpServletRequest.getParameter("qualityCheckType"));
			prpLqualityCheck.getId().setSerialNo(i + 1);
			prpLqualityCheck.setTypeName(httpServletRequest.getParameter(strQuestionName));
			prpLqualityCheck.setTypeCode(httpServletRequest.getParameter(strQuestionCode));
			prpLqualityCheck.setCheckResult(httpServletRequest.getParameter(strVisitBackQueRes));
			prpLqualityCheck.setCheckRemark(httpServletRequest.getParameter(strQuestionRemark));
			prpLqualityCheck.setFlag("");
			prpLqualityCheckList.add(prpLqualityCheck);
		}
		// 加到ArrayList中
		compensateDto.setPrpLqualityCheckList(prpLqualityCheckList);

		/******************* 工作质量审核信息 end ******************************/
		// 整理回访问询信息结束
		// 从界面得到输入数组
		String prpLregistExtRegistNo = (String) httpServletRequest.getParameter("prpLregistExtRegistNo");
		String prpLregistExtRiskCode = httpServletRequest.getParameter("prpLregistExtRiskCode");
		String[] prpLregistExtSerialNo = httpServletRequest.getParameterValues("prpLregistExtSerialNo");
		String[] prpLregistExtInputDate = httpServletRequest.getParameterValues("prpLregistExtInputDate");
		String[] prpLregistExtInputHour = httpServletRequest.getParameterValues("prpLregistExtInputHour");
		String[] prpLregistExtOperatorCode = httpServletRequest.getParameterValues("prpLregistExtOperatorCode");
		String[] prpLregistExtContext = httpServletRequest.getParameterValues("prpLregistExtContext");
		// 报案信息补充说明 PrpLregistExt
		List<PrpLregistExt> prpLregistExtList = new ArrayList<PrpLregistExt>();
		PrpLregistExt prpLregistExt = null;
		// 对象赋值
		// 人员伤亡跟踪 部分开始
		if (prpLregistExtSerialNo != null && prpLregistExtSerialNo.length > 0) {
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
			compensateDto.setPrpLregistExtList(prpLregistExtList);
		}
		// 从界面得到输入数组
		String prpLcfeeCompensateNo = (String) httpServletRequest.getAttribute("compensateNo");
		String prpLcfeePolicyNo = httpServletRequest.getParameter("prpLcompensatePolicyNo");
		String prpLcfeeRiskCode = httpServletRequest.getParameter("prpLcompensateRiskCode");
		String prpLcfeeCurrency1 = httpServletRequest.getParameter("prpLcompensateCurrency");
		String prpLcfeeSumThisPaid = httpServletRequest.getParameter("prpLcompensateSumThisPaid");
		// 对象赋值
		// 赔款计算金额信息
		List<PrpLcfee> prpLcfeeList = new ArrayList<PrpLcfee>();
		PrpLcfee prpLcfee = new PrpLcfee();
		prpLcfee.getId().setCompensateNo(prpLcfeeCompensateNo);
		prpLcfee.getId().setPolicyNo(prpLcfeePolicyNo);
		prpLcfee.setRiskCode(prpLcfeeRiskCode);
		prpLcfee.getId().setCurrency(prpLcfeeCurrency1);
		prpLcfee.setSumPaid(Double.parseDouble(DataUtils.nullToZero(prpLcfeeSumThisPaid)));
		prpLcfee.setFlag("");
		prpLcfeeList.add(prpLcfee);
		compensateDto.setPrpLcfeeList(prpLcfeeList);
		// 如果案件属於案终赔付，则需要结案报告文本
		// reason:由於不是第一张计算书的情况下，要说明後续情况，所以就要保存数据了
		List<PrpLltext> prpLltextList = new ArrayList<PrpLltext>();
		String TextTemp = httpServletRequest.getParameter("prpLltextContextInnerHTML");
		String[] rules = StringUtils.split(TextTemp, RULE_LENGTH);
		// 得到连接串,下面将其切分到数组
		PrpLltext prpLltext = null;
		for (int k = 0; k < rules.length; k++) {
			prpLltext = new PrpLltext();
			prpLltext.getId().setClaimNo(httpServletRequest.getParameter("prpLcompensateClaimNo"));
			prpLltext.setContext(rules[k]);
			prpLltext.getId().setLineNo(k + 1);
			prpLltext.getId().setTextType("08");
			prpLltextList.add(prpLltext);
		}
		compensateDto.setPrpLltextList(prpLltextList);
		// 将危险单位标的信息保存在集合中
		compensateDto.setPrpLprpLdangerItemList(prpLprpLdangerItemList);
		// 危险单位信息
		// 加入对车险免赔额的收集
		List<PrpLdeductible> dedutibleList = new ArrayList<PrpLdeductible>();
		PrpLdeductible prpLdeductibleDto = new PrpLdeductible();

		prpLdeductibleDto.setClaimNo(prpLcompensate.getClaimNo());
		prpLdeductibleDto.setCompensateNo(prpLcompensate.getCompensateNo());
		prpLdeductibleDto.setCurrency(prpLcompensate.getCurrency());
		prpLdeductibleDto.setDeductible(prpLDeductible);
		prpLdeductibleDto.setSerialNo("1");
		prpLdeductibleDto.setDeductibleLevel("21");
		dedutibleList.add(prpLdeductibleDto);
		compensateDto.setPrpLdeductibleList(dedutibleList);

		// 第一位：0-保单 1-赔付类型 2-险别 3-赔付项目；第二位0-默认不区分 1-物损 2-人损）
		// 主子表金额是否一致校验
		// double prpLCompensateSumRealPay = 0.00;
		double prpLLossSumRealPay = 0.00;
		double prpLPersonLossSumRealPay = 0.00;
		double prpLChargeSumRealPay = 0.00;
		// prpLCompensateSumRealPay = prpLcompensate.getSumThisPaid() +
		// prpLcompensate.getSumPrePaid();
		if (prpLlossList != null && !prpLlossList.isEmpty()) {
			for (PrpLloss temp : prpLlossList) {
				prpLLossSumRealPay += temp.getSumRealPay() + temp.getExceptDeductiblePay();
			}
		}
		if (prpLpersonLossList != null && !prpLpersonLossList.isEmpty()) {
			for (PrpLpersonLoss temp : prpLpersonLossList) {
				prpLPersonLossSumRealPay += temp.getSumRealPay() + temp.getExceptDeductiblePay();
			}
		}
		if (prpLchargeList != null && !prpLchargeList.isEmpty()) {
			for (PrpLcharge temp : prpLchargeList) {
				prpLChargeSumRealPay += temp.getSumRealPay() + temp.getExceptDeductiblePay();
			}
		}
		// 金額校驗剔除...
		/*
		 * if (Math.abs(prpLCompensateSumRealPay / sumTemp - 1) > 0.0001) {
		 * throw new
		 * UserException(0,-1,"理算","本次賠付總金額與各分項損失匯總金額不相等，請檢查各項金額及費用,重新輸入!<br>" +
		 * "標的損失總金額:" + prpLLossSumRealPay + "<br>" + "人傷損失總金額:" +
		 * prpLPersonLossSumRealPay + "<br>" + "計入賠款費用總金額:" +
		 * prpLChargeSumRealPay + "<br>" +
		 * "各分項損失匯總金額 = 標的損失總金額+人傷損失總金額+計入賠款費用總金額 = " + sumTemp + "<br>" +
		 * "本次賠付總金額:" + prpLCompensateSumRealPay + "<br>" +
		 * "本次賠付總金額與各分項損失匯總金額相差 " + (prpLCompensateSumRealPay - sumTemp)); }
		 */
		// 客制化开发，收集賠款給付對象資訊
		/******************* 賠付對象信息 start ******************************/
		// 从界面得到输入数组
		String prpLpayObjectInfoPaycodeType = httpServletRequest.getParameter("prpLpayObjectInfoPaycodeType");
		String[] prpLpayObjectInfoOwnerShip = httpServletRequest.getParameterValues("prpLpayObjectInfoOwnerShip");
		String[] prpLpayObjectInfoPayAmount = httpServletRequest.getParameterValues("prpLpayObjectInfoPayAmount");
		String[] prpLpayObjectInfoOwnerName = httpServletRequest.getParameterValues("prpLpayObjectInfoOwnerName");
		String[] prpLpayObjectInfoPaymentKind = httpServletRequest.getParameterValues("prpLpayObjectInfoPaymentKind");
		String[] prpLpayObjectInfoUniformNo = httpServletRequest.getParameterValues("prpLpayObjectInfoUniformNo");
		String[] prpLpayObjectInfoBeneficiaryPhone = httpServletRequest.getParameterValues("prpLpayObjectInfoBeneficiaryPhone");
		String[] prpLpayObjectInfoCutBack = httpServletRequest.getParameterValues("prpLpayObjectInfoCutBack");
		String[] prpLpayObjectInfoBankCode = httpServletRequest.getParameterValues("prpLpayObjectInfoBankCode");
		String[] prpLpayObjectInfoBankName = httpServletRequest.getParameterValues("prpLpayObjectInfoBankName");
		String[] prpLpayObjectInfoAccountCode = httpServletRequest.getParameterValues("prpLpayObjectInfoAccountCode");
		String[] prpLpayObjectInfoCustomBankCode = httpServletRequest.getParameterValues("prpLpayObjectInfoCustomBankCode");
		String[] prpLpayObjectInfoCustomBankName = httpServletRequest.getParameterValues("prpLpayObjectInfoCustomBankName");
		String[] prpLpayObjectInfoCourierAddress = httpServletRequest.getParameterValues("prpLpayObjectInfoCourierAddress");
		String[] prpLpayObjectInfoAreaCode = httpServletRequest.getParameterValues("prpLpayObjectInfoAreaCode");
		String[] prpLpayObjectInfoPayDate = httpServletRequest.getParameterValues("prpLpayObjectInfoPayDate");
		String[] prpLpayObjectInfoMobilePhoneNo = httpServletRequest.getParameterValues("prpLpayObjectInfoMobilePhoneNo");
		String[] prpLpayObjectInfoCertificateCode = httpServletRequest.getParameterValues("prpLpayObjectInfoCertificateCode");
		String[] prpLpayObjectInfoAMLFlag = httpServletRequest.getParameterValues("prpLpayObjectInfoAMLFlag");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (prpLpayObjectInfoOwnerShip != null && prpLpayObjectInfoOwnerShip.length > 0) {
			for (int index = 1; index < prpLpayObjectInfoOwnerShip.length; index++) {
				prpLpayObjectInfo = new PrpLpayObjectInfo();
				prpLpayObjectInfo.getId().setCompensateNo((String) httpServletRequest.getAttribute("compensateNo"));
				prpLpayObjectInfo.getId().setSerialNo(index);
				prpLpayObjectInfo.getId().setCertiType(PrpLpayObjectInfo.CERTITYPE_PAYOBJECT);

				prpLpayObjectInfo.setRiskCode(prpLlossDtoRiskCode);
				prpLpayObjectInfo.setPaycodeType(prpLpayObjectInfoPaycodeType);
				prpLpayObjectInfo.setOwnerShip(prpLpayObjectInfoOwnerShip[index]);
				prpLpayObjectInfo.setPayAmount(Double.parseDouble(DataUtils.nullToZero(prpLpayObjectInfoPayAmount[index])));
				prpLpayObjectInfo.setOwnerName(prpLpayObjectInfoOwnerName[index]);
				prpLpayObjectInfo.setPaymentKind(prpLpayObjectInfoPaymentKind[index]);
				prpLpayObjectInfo.setUniformNo(prpLpayObjectInfoUniformNo[index]);
				prpLpayObjectInfo.setBeneficiaryPhone(prpLpayObjectInfoBeneficiaryPhone[index]);
				if (PrpLpayObjectInfo.OWNERSHIP_B.equals(prpLpayObjectInfo.getOwnerShip())) {
					prpLpayObjectInfo.setBankCode(prpLpayObjectInfoBankCode[index]);
					prpLpayObjectInfo.setBankName(prpLpayObjectInfoBankName[index]);
					prpLpayObjectInfo.setAccountCode(prpLpayObjectInfoAccountCode[index]);
					prpLpayObjectInfo.setCustomBankCode(prpLpayObjectInfoCustomBankCode[index]);
					prpLpayObjectInfo.setCustomBankName(prpLpayObjectInfoCustomBankName[index]);
				} else if (PrpLpayObjectInfo.OWNERSHIP_Q.equals(prpLpayObjectInfo.getOwnerShip())) {
					prpLpayObjectInfo.setCutBack(prpLpayObjectInfoCutBack[index]);
				} else if (PrpLpayObjectInfo.OWNERSHIP_C.equals(prpLpayObjectInfo.getOwnerShip())) {
					if (!"".equals(prpLpayObjectInfoPayDate[index]) && prpLpayObjectInfoPayDate[index] != null) {
						prpLpayObjectInfo.setPayDate(format.parse(prpLpayObjectInfoPayDate[index]));
					}
					prpLpayObjectInfo.setMobilePhoneNo(prpLpayObjectInfoMobilePhoneNo[index]);
				}
				prpLpayObjectInfo.setCourierAddress(prpLpayObjectInfoCourierAddress[index]);
				prpLpayObjectInfo.setAreaCode(prpLpayObjectInfoAreaCode[index]);
				// 證件類型
				prpLpayObjectInfo.setCertificateCode(prpLpayObjectInfoCertificateCode[index]);
				if (prpLpayObjectInfoAMLFlag!=null&&prpLpayObjectInfoAMLFlag.length>0) {
					prpLpayObjectInfo.setAmlFlag(prpLpayObjectInfoAMLFlag[index]) ;
					prpLpayObjectInfo.setAmlDate(new Date()) ;
				}	
				prpLpayObjectInfoList.add(prpLpayObjectInfo);
			}
		}
		compensateDto.setPrpLpayObjectInfoList(prpLpayObjectInfoList);
		/******************* 賠付對象信息 end ******************************/
		String certainLossFlag = httpServletRequest.getParameter("certainLossFlag");
		if (certainLossFlag != null) {
			CertainLossDto certainLossDto = viewToCertainLossDto(httpServletRequest);
			compensateDto.setCertainLossDto(certainLossDto);
		}
		this.viewToCarInsuranceDto(compensateDto,httpServletRequest);
		// end
		return compensateDto;
	}

	/**
	 * 保存简易赔案商业险实赔时实赔页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return compensateDto 实赔数据传输数据结构
	 * @throws Exception
	 */
	public CompensateDto quickCaseViewToDto(HttpServletRequest httpServletRequest) throws Exception {

		// 继承对compensate,compensateText表的赋值
		CompensateDto compensateDto = super.quickCaseViewToDto(httpServletRequest);
		// 加入危险单位处理
		// reason: 目前只有一个危险单位，所以和标的信息放在一起处理，如果，有多个危险单位必须放入危险单位信息里面处理！
		int prpCitemKindCount = 1;
		String lastRealPay = httpServletRequest.getParameter("lastRealPay");// 总赔款
		String prpLDeductible = httpServletRequest.getParameter("prpLDeductible");// 免赔额
		double alreadySplit = 0.0;

		String prpLlossDtoRiskCode = httpServletRequest.getParameter("riskCode");

		/*---------------------赔付标的信息prpLlossDto------------------------------------*/
		String prpLlossDtoCompensateNo = (String) httpServletRequest.getAttribute("compensateNo");
		List<PrpLdeductCond> deductCondList = UIDeductCondAction.getInstance().getQuickCaseDeductCondList(httpServletRequest, true);
		compensateDto.getPrpLcompensate().setPrpLdeductCondList(deductCondList);
		String days = httpServletRequest.getParameter("days");
		String prpLlossDtoPolicyNo = httpServletRequest.getParameter("policyNo");
		String[] prpLlossDtoSerialNo = httpServletRequest.getParameterValues("lossDtoSerialNo");
		String[] prpLlossDtoItemKindNo = httpServletRequest.getParameterValues("prpLlossDtoItemKindNo");
		String[] prpLlossDtoFamilyNo = httpServletRequest.getParameterValues("prpLlossDtoFamilyNo");
		String[] prpLlossDtoFamilyName = httpServletRequest.getParameterValues("prpLlossDtoFamilyName");
		String[] prpLlossDtoKindCode = httpServletRequest.getParameterValues("prpLlossDtoKindCode");
		String[] prpLlossDtoLicenseNo = httpServletRequest.getParameterValues("licenseNo");
		String[] prpLlossDtoItemCode = httpServletRequest.getParameterValues("prpLlossDtoItemCode");
		String[] prpLlossDtoLossName = httpServletRequest.getParameterValues("prpLlossDtoLossName");
		String[] prpLlossDtoItemAddress = httpServletRequest.getParameterValues("prpLlossDtoItemAddress");
		String[] prpLlossDtoFeeTypeCode = httpServletRequest.getParameterValues("prpLlossDtoFeeTypeCode");
		String[] prpLlossDtoFeeTypeName = httpServletRequest.getParameterValues("prpLlossDtoFeeTypeName");
		String[] prpLlossDtoLossQuantity = httpServletRequest.getParameterValues("prpLlossDtoLossQuantity");
		String[] prpLlossDtoUnit = httpServletRequest.getParameterValues("prpLlossDtoUnit");
		String[] prpLlossDtoUnitPrice = httpServletRequest.getParameterValues("prpLlossDtoUnitPrice");
		String[] prpLlossDtoBuyDate = httpServletRequest.getParameterValues("prpLlossDtoBuyDate");
		String[] prpLlossDtoDepreRate = httpServletRequest.getParameterValues("prpLlossDtoDepreRate");
		String[] prpLlossDtoCurrency = httpServletRequest.getParameterValues("prpLlossDtoCurrency");
		String[] prpLlossDtoKindCodeShow = httpServletRequest.getParameterValues("prpLlossDtoKindCodeShow");
		String[] prpLlossDtoAmount = httpServletRequest.getParameterValues("kindAmount");
		String[] prpLlossDtounitAmount = httpServletRequest.getParameterValues("unitAmount");
		String[] prpLlossDtoCurrency1 = httpServletRequest.getParameterValues("prpLlossDtoCurrency1");
		String[] prpLlossDtoItemValue = httpServletRequest.getParameterValues("prpLlossDtoItemValue");
		String[] prpLlossDtoCurrency2 = httpServletRequest.getParameterValues("prpLlossDtoCurrency2");
		String[] prpLlossDtoSumLoss = httpServletRequest.getParameterValues("prpLlossDtoSumLoss");
		String[] prpLlossDtoSumDefPay = httpServletRequest.getParameterValues("prpLlossDtoSumDefPay");
		String[] prpLlossDtoSumRest = httpServletRequest.getParameterValues("prpLlossDtoSumRest");
		String[] prpLlossDtoIndemnityDutyRate = httpServletRequest.getParameterValues("prpLlossDtoIndemnityDutyRate");
		String[] prpLlossDtoArrangeRate = httpServletRequest.getParameterValues("prpLlossDtoArrangeRate");
		String[] prpLlossDtoClaimRate = httpServletRequest.getParameterValues("prpLlossDtoClaimRate");
		String[] prpLlossDtoCurrency3 = httpServletRequest.getParameterValues("prpLlossDtoCurrency3");
		String[] prpLlossDtoDeductibleRate = httpServletRequest.getParameterValues("prpLlossDtoDeductibleRate");
		String[] prpLlossDtoDutyDeductibleRate = httpServletRequest.getParameterValues("prpLlossDtoDutyDeductibleRate");
		String[] prpLlossDtoMainKindDeductibleRate = httpServletRequest.getParameterValues("PrpLlossDtoMainKindDuctibleRate");

		String[] prpLlossDtoDriverDeductibleRate = httpServletRequest.getParameterValues("prpLlossDtoDriverDeductibleRate");

		String[] prpLlossDtoCurrency4 = httpServletRequest.getParameterValues("prpLlossDtoCurrency4");
		String[] prpLlossDtoSumRealPay = httpServletRequest.getParameterValues("prpLlossDtoSumRealPay");
		String[] prpLlossDtoCompelPay = httpServletRequest.getParameterValues("prpLlossDtoCompelPay");
		// 理赔拆分危险单位
		String[] prpLlossDtoExceptDeductiblePay = null;
		String[] prpLlossDtoExceptDeductibleRate = null;
		prpLlossDtoExceptDeductiblePay = httpServletRequest.getParameterValues("prpLlossDtoExceptDeductiblePay");
		prpLlossDtoExceptDeductibleRate = httpServletRequest.getParameterValues("prpLlossDtoExceptDeductibleRate");
		String[] prpLlossDtoFlag = httpServletRequest.getParameterValues("prpLlossDtoFlag");
		String riskCode = httpServletRequest.getParameter("riskCode");

		List<PrpLloss> prpLlossList = new ArrayList<PrpLloss>();
		// 对象赋值
		if (prpLlossDtoSerialNo != null) {
			PrpLloss prpLloss = null;
			for (int index = prpCitemKindCount; index < prpLlossDtoSerialNo.length; index++) {
				prpLloss = new PrpLloss();
				if (!ConstantCodes.KINDCODE_D_BZ.equals(prpLlossDtoKindCode[index])) {
					prpLloss.setPolicyNo(prpLlossDtoPolicyNo);
					prpLloss.setRiskCode(riskCode);
					prpLloss.getId().setCompensateNo(prpLlossDtoCompensateNo);
					prpLloss.getId().setSerialNo(index);
					prpLloss.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(prpLlossDtoItemKindNo[index])));
					prpLloss.setFamilyNo(Integer.parseInt(DataUtils.nullToZero(prpLlossDtoFamilyNo[index])));
					prpLloss.setFamilyName(prpLlossDtoFamilyName[index]);
					prpLloss.setKindCode(prpLlossDtoKindCode[index]);
					prpLloss.setLicenseNo(prpLlossDtoLicenseNo[index]);
					prpLloss.setItemCode(prpLlossDtoItemCode[index]);
					prpLloss.setLossName(prpLlossDtoLossName[index]);
					prpLloss.setItemAddress(prpLlossDtoItemAddress[index]);
					prpLloss.setFeeTypeCode(prpLlossDtoFeeTypeCode[index]);
					prpLloss.setFeeTypeName(prpLlossDtoFeeTypeName[index]);
					prpLloss.setLossQuantity(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoLossQuantity[index])));
					prpLloss.setUnit(prpLlossDtoUnit[index]);
					prpLloss.setUnitPrice(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoUnitPrice[index])));
					prpLloss.setBuyDate(new DateTime(prpLlossDtoBuyDate[index]));
					prpLloss.setDepreRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoDepreRate[index])));
					prpLloss.setCurrency(prpLlossDtoCurrency[index]);
					for (int i = 0; i < prpLlossDtoAmount.length; i++) {
						if (prpLlossDtoKindCode[index].equals(prpLlossDtoKindCodeShow[i])) {
							prpLloss.setAmount(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoAmount[i])));
							if ("T".equals(prpLloss.getKindCode())) {
								// 设置日约定金额
								prpLloss.setUnitPrice(Double.parseDouble(DataUtils.nullToZero(prpLlossDtounitAmount[i])));
								prpLloss.setLossQuantity(Double.parseDouble(DataUtils.nullToZero(days)));
							}
						}
					}
					prpLloss.setCurrency1(prpLlossDtoCurrency1[index]);
					prpLloss.setItemValue(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoItemValue[index])));
					prpLloss.setCurrency2(prpLlossDtoCurrency2[index]);
					prpLloss.setSumLoss(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoSumLoss[index])));
					prpLloss.setSumRest(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoSumRest[index])));
					prpLloss.setSumDefPay(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoSumDefPay[index])));
					prpLloss.setIndemnityDutyRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoIndemnityDutyRate[index])));
					prpLloss.setArrangeRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoArrangeRate[index])));
					prpLloss.setClaimRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoClaimRate[index])));
					prpLloss.setCurrency3(prpLlossDtoCurrency3[index]);
					prpLloss.setDutyDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoDutyDeductibleRate[index])));
					prpLloss.setDeductiblerate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoDeductibleRate[index])));
					prpLloss.setDriverDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoDriverDeductibleRate[index])));
					if ("A".equals(prpLlossDtoKindCode[index])) {
						prpLloss.setDeductible(Double.parseDouble(DataUtils.nullToZero(prpLDeductible)));
					}
					prpLloss.setCurrency4(prpLlossDtoCurrency4[index]);
					prpLloss.setSumRealPay(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoSumRealPay[index])));
					prpLloss.setExceptDeductiblePay(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoExceptDeductiblePay[index])));
					prpLloss.setExceptDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoExceptDeductibleRate[index])));
					prpLloss.setFlag(prpLlossDtoFlag[index]);
					// 用於存储标的车实际价值和强制保险赔款
					prpLloss.setCompelPay(Double.parseDouble(prpLlossDtoCompelPay[index]));
					if (ConstantsCollection.MainCarLoss.contains(prpLlossDtoKindCode[index])) {
						prpLloss.setCarRealValue(Double.parseDouble((String) httpServletRequest.getParameter("factValue")));
					}
					// 对免赔额进行分摊 只有输入了免赔额，才进行分摊
					if (DataUtils.emptyToNull(prpLDeductible) == null) {
						prpLDeductible = "0.00";
					}
					if (Double.valueOf(prpLDeductible) == 0 && "A".equals(prpLloss.getKindCode())) {
						if (prpLlossDtoSerialNo.length != (index + 1)) {
							double realDeductible = Double.parseDouble(prpLDeductible) * (prpLloss.getSumRealPay() / (Double.parseDouble(lastRealPay) + Double.parseDouble(prpLDeductible)));
							realDeductible = Str.round(realDeductible, 2);
							alreadySplit = alreadySplit + realDeductible;
							prpLloss.setDeductible(Str.round(realDeductible, 2));
						} else {
							prpLloss.setDeductible(Str.round(Double.parseDouble(prpLDeductible) - alreadySplit, 2));
						}
					}
					prpLloss.setMainKindDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoMainKindDeductibleRate[index])));
					prpLlossList.add(prpLloss);
				}
			}
		}
		String strConfigCode = this.getCodeService().translateRiskCodetoConfigCode(prpLlossDtoRiskCode);
		compensateDto.setPrpLlossList(prpLlossList);

		// 从界面得到输入数组
		String prpLchargePolicyNo = httpServletRequest.getParameter("policyNo");
		String prpLchargeRiskCode = httpServletRequest.getParameter("riskCode");
		String prpLchargeCompensateNo = (String) httpServletRequest.getAttribute("compensateNo");
		String[] prpLchargeSerialNo = httpServletRequest.getParameterValues("prpLchargeSerialNo");
		String[] prpLchargeKindCode = httpServletRequest.getParameterValues("prpLchargeKindCode");
		String[] prpLchargeChargeCode = httpServletRequest.getParameterValues("prpLchargeChargeCode");
		String[] prpLchargeChargeName = httpServletRequest.getParameterValues("prpLchargeChargeName");
		String[] prpLchargeCurrency = httpServletRequest.getParameterValues("prpLchargeCurrency");
		String[] prpLchargeChargeAmount = httpServletRequest.getParameterValues("prpLchargeChargeAmount");
		String[] prpLchargeSumRealPay = httpServletRequest.getParameterValues("prpLchargeSumRealPay");
		String[] prpLchargeFlag = httpServletRequest.getParameterValues("prpLchargeFlag");
		String[] prpLchargeChargeReport = httpServletRequest.getParameterValues("prpLchargeChargeReport");
		String[] prpLchargeExceptDeductiblePay = httpServletRequest.getParameterValues("prpLchargeExceptDeductiblePay");
		String[] prpLchargeExceptDeductibleRate = httpServletRequest.getParameterValues("prpLchargeExceptDeductibleRate");
		// 增加对支付对象的保存
		String[] prpLchargeAccountCode = httpServletRequest.getParameterValues("prpLchargeAccountCode");
		String[] prpLchargeAccountType = httpServletRequest.getParameterValues("prpLchargeAccountType");
		String[] prpLchargeBankCode = httpServletRequest.getParameterValues("prpLchargeBankCode");
		String[] prpLchargeBankName = httpServletRequest.getParameterValues("prpLchargeBankName");
		String[] prpLchargeOwnerName = httpServletRequest.getParameterValues("prpLchargeOwnerName");
		String[] prpLchargeCertifiCateCode = httpServletRequest.getParameterValues("prpLchargeCertifiCateCode");
		String[] prpLchargePhoneNo = httpServletRequest.getParameterValues("prpLchargePhoneNo");
		String[] prpLchargeCustomBankName = httpServletRequest.getParameterValues("prpLchargeCustomBankName");
		String[] prpLchargeAccountCurrency = httpServletRequest.getParameterValues("prpLchargeAccountCurrency");
		String[] prpLchargeOwnerShip = httpServletRequest.getParameterValues("prpLchargeOwnerShip");
		if ("RISKCODE_DAZ".equals(strConfigCode) == false) {
			prpLchargeExceptDeductiblePay = httpServletRequest.getParameterValues("prpLchargeExceptDeductiblePay");
			prpLchargeExceptDeductibleRate = httpServletRequest.getParameterValues("prpLchargeExceptDeductibleRate");
		}
		String[] prpLchargePayObjectType = null;
		String[] prpLchargePayObjectCode = null;
		String[] prpLchargePayObjectName = null;
		prpLchargePayObjectType = httpServletRequest.getParameterValues("prpLchargePayObjectType"); // add
		prpLchargePayObjectCode = httpServletRequest.getParameterValues("prpLchargePayObjectCode"); // add
		prpLchargePayObjectName = httpServletRequest.getParameterValues("prpLchargePayObjectName");
		// 对象赋值
		// 赔款费用信息
		List<PrpLcharge> prpLchargeList = new ArrayList<PrpLcharge>();
		if (prpLchargeSerialNo != null) {
			PrpLcharge prpLcharge = null;
			for (int index = 1; index < prpLchargeSerialNo.length; index++) {
				prpLcharge = new PrpLcharge();
				if (!ConstantCodes.KINDCODE_D_BZ.equals(prpLchargeKindCode[index])) {
					prpLcharge.setPolicyNo(prpLchargePolicyNo);
					prpLcharge.setRiskCode(prpLchargeRiskCode);
					prpLcharge.getId().setCompensateNo(prpLchargeCompensateNo);
					prpLcharge.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLchargeSerialNo[index])));
					prpLcharge.setKindCode(prpLchargeKindCode[index]);
					prpLcharge.setChargeCode(prpLchargeChargeCode[index]);
					prpLcharge.setChargeName(prpLchargeChargeName[index]);
					prpLcharge.setCurrency(prpLchargeCurrency[index]);
					prpLcharge.setChargeAmount(Double.parseDouble(DataUtils.nullToZero(prpLchargeChargeAmount[index])));
					prpLcharge.setSumRealPay(Double.parseDouble(DataUtils.nullToZero(prpLchargeSumRealPay[index])));
					prpLcharge.setFlag(prpLchargeFlag[index]);
					// 增加对支付对象的保存
					prpLcharge.setAccountCode(prpLchargeAccountCode[index]);
					prpLcharge.setAccountType(prpLchargeAccountType[index]);
					prpLcharge.setBankCode(prpLchargeBankCode[index]);
					prpLcharge.setBankName(prpLchargeBankName[index]);
					prpLcharge.setOwnerName(prpLchargeOwnerName[index]);
					prpLcharge.setCertifiCateCode(prpLchargeCertifiCateCode[index]);
					prpLcharge.setCustomBankName(prpLchargeCustomBankName[index]);
					prpLcharge.setOwnerPhoneNo(prpLchargePhoneNo[index]);
					prpLcharge.setAccountCurrency(prpLchargeAccountCurrency[index]);
					prpLcharge.setOwnerShip(prpLchargeOwnerShip[index]);
					if ("RISKCODE_DAZ".equals(strConfigCode) == false) {
						prpLcharge.setPayObjectCode(prpLchargePayObjectCode[index]);
						prpLcharge.setPayObjectType(prpLchargePayObjectType[index]);
						prpLcharge.setPayObjectName(prpLchargePayObjectName[index]);
						prpLcharge.setExceptDeductiblePay(Double.parseDouble(DataUtils.nullToZero(prpLchargeExceptDeductiblePay[index])));
						prpLcharge.setExceptDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLchargeExceptDeductibleRate[index])));
					}
					prpLcharge.setChargeReport(Double.parseDouble(DataUtils.nullToZero(prpLchargeChargeReport[index]))); // add
				}
				// 加入集合
				prpLchargeList.add(prpLcharge);
			}
		}
		// 赔款费用信息
		compensateDto.setPrpLchargeList(prpLchargeList);

		int intCount = Integer.parseInt("5");
		int j = 0;
		String strQuestionCode = "";
		String strQuestionName = "";
		String strQuestionRemark = "";
		String strVisitBackQueRes = "";
		List<PrpLqualityCheck> prpLqualityCheckList = new ArrayList<PrpLqualityCheck>();
		PrpLqualityCheck prpLqualityCheck = null;
		for (int i = 0; i < intCount; i++) {
			j = i + 1;
			strQuestionCode = "txtQuestionCode" + j;
			strQuestionName = "txtQuestionName" + j;
			strQuestionRemark = "txtQuestionRemark" + j;
			strVisitBackQueRes = "VisitBackQue" + j;
			prpLqualityCheck = new PrpLqualityCheck();
			prpLqualityCheck.getId().setRegistNo(compensateDto.getPrpLcompensate().getCompensateNo());
			prpLqualityCheck.getId().setQualityCheckType("compe");
			prpLqualityCheck.getId().setSerialNo(i + 1);
			prpLqualityCheck.setTypeName(httpServletRequest.getParameter(strQuestionName));
			prpLqualityCheck.setTypeCode(httpServletRequest.getParameter(strQuestionCode));
			prpLqualityCheck.setCheckResult(httpServletRequest.getParameter(strVisitBackQueRes));
			prpLqualityCheck.setCheckRemark(httpServletRequest.getParameter(strQuestionRemark));
			prpLqualityCheck.setFlag("");
			prpLqualityCheckList.add(prpLqualityCheck);
		}
		// 加到ArrayList中
		compensateDto.setPrpLqualityCheckList(prpLqualityCheckList);
		// 整理回访问询信息结束
		// 从界面得到输入数组
		String prpLcfeeCompensateNo = (String) httpServletRequest.getAttribute("compensateNo");
		String prpLcfeePolicyNo = httpServletRequest.getParameter("policyNo");
		String prpLcfeeRiskCode = httpServletRequest.getParameter("riskCode");
		String prpLcfeeSumThisPaid = httpServletRequest.getParameter("prpLcompensateSumThisPaid");
		// 对象赋值
		// 赔款计算金额信息
		List<PrpLcfee> prpLcfeeList = new ArrayList<PrpLcfee>();
		PrpLcfee prpLcfee = new PrpLcfee();
		prpLcfee.getId().setCompensateNo(prpLcfeeCompensateNo);
		prpLcfee.getId().setPolicyNo(prpLcfeePolicyNo);
		prpLcfee.setRiskCode(prpLcfeeRiskCode);
		prpLcfee.getId().setCurrency(ConstantCodes.LOCAL_CURRENCY);
		prpLcfee.setSumPaid(Double.parseDouble(DataUtils.nullToZero(prpLcfeeSumThisPaid)));
		prpLcfee.setFlag("");
		prpLcfeeList.add(prpLcfee);
		compensateDto.setPrpLcfeeList(prpLcfeeList);
		return compensateDto;
	}

	/**
	 * 保存简易赔案交强险实赔时实赔页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return compensateDto 实赔数据传输数据结构
	 * @throws Exception
	 */
	public CompensateDto compelViewToDto(HttpServletRequest httpServletRequest) throws Exception {

		// 继承对compensate,compensateText表的赋值
		CompensateDto compensateDto = super.compelViewToDto(httpServletRequest);
		// 加入危险单位处理
		// reason: 目前只有一个危险单位，所以和标的信息放在一起处理，如果，有多个危险单位必须放入危险单位信息里面处理！
		// 文件的处理方式改变，所以这里可以从1开始，而不用从文件中的数据
		int prpCitemKindCount = 1;
		String prpLDeductible = httpServletRequest.getParameter("prpLDeductible");// 免赔额
		String prpLlossDtoRiskCode = httpServletRequest.getParameter("compelRiskCode");
		// 赔付标的信息
		String prpLlossDtoPolicyNo = httpServletRequest.getParameter("prpLRegistRPolicyNo");
		String[] prpLlossDtoSerialNo = httpServletRequest.getParameterValues("lossDtoSerialNo");
		String[] prpLlossDtoItemKindNo = httpServletRequest.getParameterValues("prpLlossDtoItemKindNo");
		String[] prpLlossDtoFamilyNo = httpServletRequest.getParameterValues("prpLlossDtoFamilyNo");
		String[] prpLlossDtoFamilyName = httpServletRequest.getParameterValues("prpLlossDtoFamilyName");
		String[] prpLlossDtoKindCode = httpServletRequest.getParameterValues("prpLlossDtoKindCode");
		String[] prpLlossDtoLicenseNo = httpServletRequest.getParameterValues("licenseNo");
		String[] prpLlossDtoItemCode = httpServletRequest.getParameterValues("prpLlossDtoItemCode");
		String[] prpLlossDtoLossName = httpServletRequest.getParameterValues("prpLlossDtoLossName");
		String[] prpLlossDtoItemAddress = httpServletRequest.getParameterValues("prpLlossDtoItemAddress");
		String[] prpLlossDtoFeeTypeCode = httpServletRequest.getParameterValues("prpLlossDtoFeeTypeCode");
		String[] prpLlossDtoFeeTypeName = httpServletRequest.getParameterValues("prpLlossDtoFeeTypeName");
		String[] prpLlossDtoLossQuantity = httpServletRequest.getParameterValues("prpLlossDtoLossQuantity");
		String[] prpLlossDtoUnit = httpServletRequest.getParameterValues("prpLlossDtoUnit");
		String[] prpLlossDtoUnitPrice = httpServletRequest.getParameterValues("prpLlossDtoUnitPrice");
		String[] prpLlossDtoBuyDate = httpServletRequest.getParameterValues("prpLlossDtoBuyDate");
		String[] prpLlossDtoDepreRate = httpServletRequest.getParameterValues("prpLlossDtoDepreRate");
		String[] prpLlossDtoCurrency = httpServletRequest.getParameterValues("prpLlossDtoCurrency");
		String[] prpLlossDtoCurrency1 = httpServletRequest.getParameterValues("prpLlossDtoCurrency1");
		String[] prpLlossDtoItemValue = httpServletRequest.getParameterValues("prpLlossDtoItemValue");
		String[] prpLlossDtoCurrency2 = httpServletRequest.getParameterValues("prpLlossDtoCurrency2");
		String[] prpLlossDtoSumLoss = httpServletRequest.getParameterValues("prpLlossDtoSumLoss");
		String[] prpLlossDtoSumDefPay = httpServletRequest.getParameterValues("prpLlossDtoSumDefPay");
		String[] prpLlossDtoSumRest = httpServletRequest.getParameterValues("prpLlossDtoSumRest");
		String[] prpLlossDtoIndemnityDutyRate = httpServletRequest.getParameterValues("prpLlossDtoIndemnityDutyRate");
		String[] prpLlossDtoArrangeRate = httpServletRequest.getParameterValues("prpLlossDtoArrangeRate");
		String[] prpLlossDtoClaimRate = httpServletRequest.getParameterValues("prpLlossDtoClaimRate");
		String[] prpLlossDtoCurrency3 = httpServletRequest.getParameterValues("prpLlossDtoCurrency3");
		String[] prpLlossDtoDeductibleRate = httpServletRequest.getParameterValues("prpLlossDtoDeductibleRate");
		String[] prpLlossDtoDutyDeductibleRate = httpServletRequest.getParameterValues("prpLlossDtoDutyDeductibleRate");
		String[] prpLlossDtoMainKindDeductibleRate = httpServletRequest.getParameterValues("PrpLlossDtoMainKindDuctibleRate");

		String[] prpLlossDtoDriverDeductibleRate = httpServletRequest.getParameterValues("prpLlossDtoDriverDeductibleRate");

		String[] prpLlossDtoCurrency4 = httpServletRequest.getParameterValues("prpLlossDtoCurrency4");
		String[] prpLlossDtoSumRealPay = httpServletRequest.getParameterValues("prpLlossDtoSumRealPay");
		String[] prpLlossDtoCompelPay = httpServletRequest.getParameterValues("prpLlossDtoCompelPay");
		// 理赔拆分危险单位
		String[] prpLlossDtoFlag = httpServletRequest.getParameterValues("prpLlossDtoFlag");
		// 对象赋值
		List<PrpLloss> prpLlossList = new ArrayList<PrpLloss>();
		if (prpLlossDtoSerialNo != null) {
			PrpLloss prpLloss = null;
			for (int index = prpCitemKindCount; index < prpLlossDtoSerialNo.length; index++) {
				if (ConstantCodes.KINDCODE_D_BZ.equals(prpLlossDtoKindCode[index])) {
					prpLloss = new PrpLloss();
					prpLloss.setPolicyNo(prpLlossDtoPolicyNo);
					prpLloss.setRiskCode(prpLlossDtoRiskCode);
					prpLloss.getId().setCompensateNo((String) httpServletRequest.getAttribute("compelCompensateNo"));
					prpLloss.getId().setSerialNo(index);
					prpLloss.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(prpLlossDtoItemKindNo[index])));
					prpLloss.setFamilyNo(Integer.parseInt(DataUtils.nullToZero(prpLlossDtoFamilyNo[index])));
					prpLloss.setFamilyName(prpLlossDtoFamilyName[index]);
					prpLloss.setKindCode(prpLlossDtoKindCode[index]);
					prpLloss.setLicenseNo(prpLlossDtoLicenseNo[index]);
					prpLloss.setItemCode(prpLlossDtoItemCode[index]);
					prpLloss.setLossName(prpLlossDtoLossName[index]);
					prpLloss.setItemAddress(prpLlossDtoItemAddress[index]);
					prpLloss.setFeeTypeCode(prpLlossDtoFeeTypeCode[index]);
					prpLloss.setFeeTypeName(prpLlossDtoFeeTypeName[index]);
					prpLloss.setLossQuantity(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoLossQuantity[index])));
					prpLloss.setUnit(prpLlossDtoUnit[index]);
					prpLloss.setUnitPrice(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoUnitPrice[index])));
					prpLloss.setBuyDate(new DateTime(prpLlossDtoBuyDate[index]));
					prpLloss.setDepreRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoDepreRate[index])));
					prpLloss.setCurrency(prpLlossDtoCurrency[index]);

					prpLloss.setCurrency1(prpLlossDtoCurrency1[index]);
					prpLloss.setItemValue(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoItemValue[index])));
					prpLloss.setCurrency2(prpLlossDtoCurrency2[index]);
					prpLloss.setSumLoss(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoSumLoss[index])));
					prpLloss.setSumRest(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoSumRest[index])));
					prpLloss.setSumDefPay(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoSumDefPay[index])));
					prpLloss.setIndemnityDutyRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoIndemnityDutyRate[index])));
					prpLloss.setArrangeRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoArrangeRate[index])));
					prpLloss.setClaimRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoClaimRate[index])));
					prpLloss.setCurrency3(prpLlossDtoCurrency3[index]);
					prpLloss.setDutyDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoDutyDeductibleRate[index])));
					prpLloss.setDeductiblerate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoDeductibleRate[index])));
					prpLloss.setDriverDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoDriverDeductibleRate[index])));

					prpLloss.setCurrency4(prpLlossDtoCurrency4[index]);
					prpLloss.setSumRealPay(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoSumRealPay[index])));
					prpLloss.setFlag(prpLlossDtoFlag[index]);
					// 用於存储标的车实际价值和强制保险赔款
					prpLloss.setCompelPay(Double.parseDouble(prpLlossDtoCompelPay[index]));
					// 对免赔额进行分摊
					// 只有输入了免赔额，才进行分摊
					if (DataUtils.emptyToNull(prpLDeductible) == null) {
						prpLDeductible = "0.00";
					}
					prpLloss.setMainKindDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLlossDtoMainKindDeductibleRate[index])));
					// 加入集合
					prpLlossList.add(prpLloss);
				}
			}
		}
		compensateDto.setPrpLlossList(prpLlossList);
		String strConfigCode = this.getCodeService().translateRiskCodetoConfigCode(prpLlossDtoRiskCode);
		// 从界面得到输入数组
		String prpLchargePolicyNo = httpServletRequest.getParameter("prpLRegistRPolicyNo");
		String prpLchargeRiskCode = httpServletRequest.getParameter("compelRiskCode");
		String prpLchargeCompensateNo = (String) httpServletRequest.getAttribute("compelCompensateNo");
		String[] prpLchargeSerialNo = httpServletRequest.getParameterValues("prpLchargeSerialNo");
		String[] prpLchargeKindCode = httpServletRequest.getParameterValues("prpLchargeKindCode");
		String[] prpLchargeChargeCode = httpServletRequest.getParameterValues("prpLchargeChargeCode");
		String[] prpLchargeChargeName = httpServletRequest.getParameterValues("prpLchargeChargeName");
		String[] prpLchargeCurrency = httpServletRequest.getParameterValues("prpLchargeCurrency");
		String[] prpLchargeChargeAmount = httpServletRequest.getParameterValues("prpLchargeChargeAmount");
		String[] prpLchargeSumRealPay = httpServletRequest.getParameterValues("prpLchargeSumRealPay");
		String[] prpLchargeFlag = httpServletRequest.getParameterValues("prpLchargeFlag");
		String[] prpLchargeChargeReport = httpServletRequest.getParameterValues("prpLchargeChargeReport");
		// String[] prpLchargeExceptDeductiblePay =
		// httpServletRequest.getParameterValues("prpLchargeExceptDeductiblePay");
		// String[] prpLchargeExceptDeductibleRate =
		// httpServletRequest.getParameterValues("prpLchargeExceptDeductibleRate");
		if ("RISKCODE_DAZ".equals(strConfigCode) == false) {
			// prpLchargeExceptDeductiblePay =
			// httpServletRequest.getParameterValues("prpLchargeExceptDeductiblePay");
			// prpLchargeExceptDeductibleRate =
			// httpServletRequest.getParameterValues("prpLchargeExceptDeductibleRate");
		}
		// 对象赋值
		// 赔款费用信息
		List<PrpLcharge> prpLchargeList = new ArrayList<PrpLcharge>();
		if (prpLchargeSerialNo != null) {
			PrpLcharge prpLcharge = null;
			for (int index = 1; index < prpLchargeSerialNo.length; index++) {
				prpLcharge = new PrpLcharge();
				if (ConstantCodes.KINDCODE_D_BZ.equals(prpLchargeKindCode[index])) {
					prpLcharge.setPolicyNo(prpLchargePolicyNo);
					prpLcharge.setRiskCode(prpLchargeRiskCode);
					prpLcharge.getId().setCompensateNo(prpLchargeCompensateNo);
					prpLcharge.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLchargeSerialNo[index])));
					prpLcharge.setKindCode(prpLchargeKindCode[index]);
					prpLcharge.setChargeCode(prpLchargeChargeCode[index]);
					prpLcharge.setChargeName(prpLchargeChargeName[index]);
					prpLcharge.setCurrency(prpLchargeCurrency[index]);
					prpLcharge.setChargeAmount(Double.parseDouble(DataUtils.nullToZero(prpLchargeChargeAmount[index])));
					prpLcharge.setSumRealPay(Double.parseDouble(DataUtils.nullToZero(prpLchargeSumRealPay[index])));
					prpLcharge.setFlag(prpLchargeFlag[index]);
					prpLcharge.setChargeReport(Double.parseDouble(DataUtils.nullToZero(prpLchargeChargeReport[index]))); // add
					prpLchargeList.add(prpLcharge);
				}
			}
		}
		// 赔款费用信息
		compensateDto.setPrpLchargeList(prpLchargeList);

		// 从界面得到输入数组
		String prpLcfeeCompensateNo = (String) httpServletRequest.getAttribute("compelCompensateNo");
		String prpLcfeePolicyNo = httpServletRequest.getParameter("prpLRegistRPolicyNo");
		String prpLcfeeRiskCode = httpServletRequest.getParameter("compelRiskCode");
		String prpLcfeeSumThisPaid = httpServletRequest.getParameter("prpLcompensateSumThisPaid");
		// 对象赋值
		// 赔款计算金额信息
		List<PrpLcfee> prpLcfeeList = new ArrayList<PrpLcfee>();
		PrpLcfee prpLcfee = new PrpLcfee();
		prpLcfee.getId().setCompensateNo(prpLcfeeCompensateNo);
		prpLcfee.getId().setPolicyNo(prpLcfeePolicyNo);
		prpLcfee.setRiskCode(prpLcfeeRiskCode);
		prpLcfee.getId().setCurrency(ConstantCodes.LOCAL_CURRENCY);
		prpLcfee.setSumPaid(Double.parseDouble(DataUtils.nullToZero(prpLcfeeSumThisPaid)));
		prpLcfee.setFlag("");
		prpLcfeeList.add(prpLcfee);
		compensateDto.setPrpLcfeeList(prpLcfeeList);
		// 如果案件属於案终赔付，则需要结案报告文本
		// 由於不是第一张计算书的情况下，要说明後续情况，所以就要保存数据了
		List<PrpLltext> prpLltextList = new ArrayList<PrpLltext>();
		String TextTemp = httpServletRequest.getParameter("prpLltextContextInnerHTML");
		String[] rules = StringUtils.split(TextTemp, RULE_LENGTH);
		if (rules.length > 0) {
			// 得到连接串,下面将其切分到数组
			PrpLltext prpLltext = null;
			for (int k = 0; k < rules.length; k++) {
				prpLltext = new PrpLltext();
				prpLltext.getId().setClaimNo((String) httpServletRequest.getParameter("prpLcompensateClaimNo"));
				prpLltext.setContext(rules[k]);
				prpLltext.getId().setLineNo(k + 1);
				prpLltext.getId().setTextType("08");
				prpLltextList.add(prpLltext);
			}
		}
		compensateDto.setPrpLltextList(prpLltextList);
		return compensateDto;
	}

	/**
	 * 取初始化信息需要的数据的整理. 填写实赔单时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等。取这些信息需要一些入参，
	 * 考虑到接口的一致性，将这些入参作为Dto方式传入，Dto利用聚合而非继承的方式。
	 * @param httpServletRequest
	 * @return compensateDto 取初始化信息需要的数据
	 * @throws Exception
	 */
	public CompensateDto iniViewToDto(HttpServletRequest httpServletRequest) throws Exception {
		return new CompensateDto();
	}

	/**
	 * 填写实赔页面及查询实赔request的生成.
	 * 填写实赔时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param proposalIniDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public void dtoToView(HttpServletRequest httpServletRequest, CompensateDto compensateDto) throws Exception {
		// 得到request的PrpLcompensateForm用於显示
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		httpServletRequest.setAttribute("prpLcompensate", prpLcompensate);
	}

	/**
	 * 根据赔款计算书号查询预赔信息
	 * @param httpServletRequest 返回给页面的request
	 * @param compensateNo 赔款计算书号
	 * @throws Exception
	 */
	public void compensateDtoView(HttpServletRequest httpServletRequest, String compensateNo, String editType) throws Exception {
		// 特殊赔案标志,从工作流上获得。
		String caseType = httpServletRequest.getParameter("caseType");
		CompensateDto compensateDto = this.getCompensateService().findByPrimaryKey(compensateNo, caseType);
		// 赔款计算书主信息
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		prpLcompensate.setEditType(editType.trim());
		// 得到是否重开赔案
		String claimNo = prpLcompensate.getClaimNo().trim();
		int recount = 0;
		// 查询重开赔案信息
		ReCaseDto reCaseDto = this.getRecaseService().findByPrimaryKey(claimNo, 1);
		PrpLrecase prpLrecase = reCaseDto.getPrpLrecase();
		if (prpLrecase != null && DataUtils.emptyToNull(prpLrecase.getId().getClaimNo()) != null) {
			recount = 1;
			httpServletRequest.setAttribute("recaseFlag", String.valueOf(recount));
		}
		httpServletRequest.setAttribute("recaseFlag", String.valueOf(recount));
		// 设置实赔操作的状态为 案件修改 (正处理任务)
		PrpLclaimStatus prpLclaimStatus = compensateDto.getPrpLclaimStatus();
		if (prpLclaimStatus != null) {
			// 设置初始化的内容
			if ("7".equals(prpLclaimStatus.getStatus())) {
				prpLclaimStatus.setStatus("3");
			}
			prpLcompensate.setStatus(prpLclaimStatus.getStatus());
		} else {
			// 已提交，已经处理完毕的状态
			prpLcompensate.setStatus("4");
		}
		String status = httpServletRequest.getParameter("status");// 从工作流上去状态
		// 在理算环节如果是核赔退回的单子，必须显示退回原因
		if (DataUtils.emptyToNull(status) != null) {
			if (status.equals("3")) {
				String flowId = httpServletRequest.getParameter("swfLogFlowID");
				int logNo = Integer.parseInt(httpServletRequest.getParameter("swfLogLogNo")) - 1; // 传过来的logNo是最大加了1，所以减掉
				SwfNotion swfNotion = this.getSwfNotionService().findSwfNotion(flowId, logNo, 1);
				httpServletRequest.setAttribute("swfNotion", swfNotion);
			}
			prpLcompensate.setStatus(status);
		}
		if (DataUtils.emptyToNull(prpLcompensate.getPolicyNo()) != null) {
			// 查询保单信息
			// 根据出险日期还原保单信息
			PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
			String policyNo = prpLclaim.getPolicyNo();
			String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
			String damageHour = prpLclaim.getDamageStartHour();
			PrpCmain prpCmain = this.getEndorseViewHelper().findPrpCmain(policyNo, damageDate, damageHour);//
			httpServletRequest.setAttribute("coinsFlag", prpCmain.getCoinsFlag());
			if ("1".equals(prpCmain.getCoinsFlag())) {
				List<PrpCcoins> coinsList = this.getPrpCcoinsService().findPrpCcoins(QueryRule.getInstance().addSql("policyNo = '" + prpCmain.getPolicyNo() + "'"));
				if (coinsList != null && coinsList.size() > 0) {
					for (Iterator<PrpCcoins> it = coinsList.iterator(); it.hasNext();) {
						PrpCcoins prpCcoinsDto = it.next();
						if ("1".equals(prpCcoinsDto.getChiefFlag())) {
							httpServletRequest.setAttribute("chiefFlag", prpCcoinsDto.getChiefFlag());
							break;
						}
					}
				}
			}
			// 将险别信息压到页面上
			List<PrpCitemKind> prpCitemKindList = this.getEndorseViewHelper().findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
			httpServletRequest.setAttribute("damageKindList", prpCitemKindList);
			PrpCitemCar PrpCitemCarDto = new PrpCitemCar();
			httpServletRequest.setAttribute("prpCitemKindList", prpCitemKindList);
			// 获得保单默认的险别
			String defaultKindCode = new DAAClaimViewHelper().getDefaultKindCodeByPolicyDto(prpCitemKindList);
			httpServletRequest.setAttribute("defaultKindCode", defaultKindCode);
			List<PrpCitemCar> prpCitemCarList = this.getEndorseViewHelper().findPrpCitemCar(policyNo, damageDate, damageHour);
			if (prpCitemCarList != null && !prpCitemCarList.isEmpty()) {
				// 对车型等信息的支持
				PrpCitemCarDto = prpCitemCarList.get(0);
				prpLcompensate.setClauseType(PrpCitemCarDto.getClauseType());
				prpLcompensate.setLicenseNo(PrpCitemCarDto.getLicenseNo());
				prpLcompensate.setLicenseColorCode(PrpCitemCarDto.getLicenseColorCode());
				prpLcompensate.setLicenseColorCode(PrpCitemCarDto.getLicenseColorCode());
				prpLcompensate.setBrandName(PrpCitemCarDto.getBrandName());
				prpLcompensate.setCarKindCode(PrpCitemCarDto.getCarKindCode());
				prpLcompensate.setEngineNo(PrpCitemCarDto.getEngineNo());
				prpLcompensate.setFrameNo(PrpCitemCarDto.getFrameNo());
				prpLcompensate.setClauseTypeCode(PrpCitemCarDto.getClauseType());
				prpLcompensate.setPurchasePrice(String.valueOf(PrpCitemCarDto.getPurchasePrice()));
			}
		}
		ClaimDto claimDto = null;
		if (DataUtils.emptyToNull(claimNo) != null) {
			// 查询保单信息
			claimDto = this.getClaimService().findByPrimaryKey(claimNo);
			// 得到人伤个数
			List<PrpLpersonTrace> prpLpersonTraceList = claimDto.getPrpLpersonTraceList();
			httpServletRequest.setAttribute("personCount", String.valueOf(prpLpersonTraceList.size()));
			PrpLclaim prpLclaim = claimDto.getPrpLclaim();
			prpLcompensate.setDamageStartDate(prpLclaim.getDamageStartDate());
			prpLcompensate.setEscapeFlag(prpLclaim.getEscapeFlag());
			String timeTemp = StringConvert.toStandardTime(prpLclaim.getDamageStartHour());
			prpLcompensate.setDamageStartHour(timeTemp.substring(0, 2));
			prpLcompensate.setDamageStartMinute(timeTemp.substring(3, 5));
			prpLcompensate.setStartDate(prpLclaim.getStartDate());
			prpLcompensate.setStartHour(prpLclaim.getStartHour());
			prpLcompensate.setEndDate(prpLclaim.getEndDate());
			prpLcompensate.setEndHour(prpLclaim.getEndHour());
			//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
			prpLcompensate.setAddressCode(prpLclaim.getAddressCode());
			prpLcompensate.setDamageAddress(prpLclaim.getDamageAddress());
			prpLcompensate.setSumClaim(prpLclaim.getSumClaim());
			prpLcompensate.setSumAmount(prpLclaim.getSumAmount());
			prpLcompensate.setInsuredName(prpLclaim.getInsuredName());
			prpLcompensate.setInsuredCode(prpLclaim.getInsuredCode());
			prpLcompensate.setSumPaidAll(prpLclaim.getSumPaid());
			prpLcompensate.setDamageCode(prpLclaim.getDamageCode());
			prpLcompensate.setDamageName(prpLclaim.getDamageName());
			// 添加是否有赔偿信息
			if ("1".equals(prpLclaim.getReplevyFlag())) {
				prpLcompensate.setReplevyFlag("1");
			} else {
				prpLcompensate.setReplevyFlag("0");
			}
			prpLcompensate.setReplevyRemark(prpLclaim.getReplevyRemark());
		}
		// 摘要的信息
		StringBuffer tempContext = new StringBuffer("");
		List<PrpLctext> prpLctextList = compensateDto.getPrpLctextList();
		if (prpLctextList != null && !prpLctextList.isEmpty()) {
			for (PrpLctext prpLctext : prpLctextList) {
				tempContext.append(prpLctext.getContext());
			}
		}
		PrpLctext prpLctext = new PrpLctext();
		prpLctext.setContext(tempContext.toString());
		prpLctext.getId().setTextType("1");
		httpServletRequest.setAttribute("prpLctext", prpLctext);
		// 设置相关代码的中文转换
		changeCodeToName(httpServletRequest, prpLcompensate);

		if (compensateDto.getPrpLlossList() != null) {
			for (PrpLloss prpLloss : compensateDto.getPrpLlossList()) {
				prpLloss.setKindName(this.getCodeService().translateKindCode(prpLloss.getRiskCode(), prpLloss.getKindCode(), true));
				prpLloss.setCurrency2Name(this.getCodeService().translateCurrencyCode(prpLloss.getCurrency2(), true));
			}
		}
		// 承保保单中的内容以及免赔率等东西
		// 免赔条件
		List<PrpDcode> prpDCodeList = (ArrayList<PrpDcode>) this.getCodeService().getDeductCondition(prpLcompensate.getRiskCode());
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.compensateNo", prpLcompensate.getCompensateNo());
		List<PrpLdeductCond> prpLdeductCondList = this.getPrpLdeductCondService().findPrpLdeductCond(queryRule);
		PrpLdeductCond prpLdeductCond = new PrpLdeductCond();
		prpLdeductCond.getId().setCompensateNo("");
		prpLdeductCond.getId().setDeductCondCode("");
		prpLdeductCond.setDeductCondName("");
		prpLdeductCond.setTimes(0);
		int count = prpDCodeList.size() - prpLdeductCondList.size();
		for (int i = 0; i < count; i++) {
			prpLdeductCondList.add(prpLdeductCond);
		}
		prpLcompensate.setPrpLdeductCondList(prpLdeductCondList);
		ExceptDeductibleRateDto afterDeductibleRateDto = this.getLossInfo(httpServletRequest, prpLcompensate);
		// 设置窗体表单中各个多选框中列表信息的内容
		setSelectionList(httpServletRequest, prpLcompensate);
		// 查询相同保单号的出险次数
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		this.getDaaRegistViewHelper().getSamePolicyRegistInfo(httpServletRequest, prpLcompensate.getPolicyNo(), prpLclaim.getRegistNo());

		httpServletRequest.setAttribute("prpLqualityCheckList", compensateDto.getPrpLqualityCheckList());
		// 设置主实赔信息内容到窗体表单
		httpServletRequest.setAttribute("prpLcompensate", prpLcompensate);
		String strRiskType = this.getCodeService().translateRiskCodetoRiskType(prpLcompensate.getRiskCode());
		httpServletRequest.setAttribute("riskType", strRiskType);
		// 设置各个子表信息项到窗体表单
		setSubInfo(httpServletRequest, compensateDto, afterDeductibleRateDto);
		Collection<?> reinsDangerUnitCollection = reinsServiceManager.getReinsService().getDangerUnit(prpLcompensate.getPolicyNo(), new com.sinosoft.sysframework.common.datatype.DateTime(prpLclaim.getDamageStartDate()));
		httpServletRequest.setAttribute("ReinsDangerUnitCollection", reinsDangerUnitCollection);
		// 结案报告
		tempContext = new StringBuffer("");
		List<PrpLltext> prpLltextList = compensateDto.getPrpLltextList();
		if (prpLltextList != null && !prpLltextList.isEmpty()) {
			for (PrpLltext prpLltext : prpLltextList) {
				if ("08".equals(prpLltext.getId().getTextType())) {
					tempContext.append(prpLltext.getContext());
				}
			}
		}
		PrpLltext prpLltext = new PrpLltext();
		prpLltext.setContext(tempContext.toString());
		prpLltext.getId().setTextType("08");
		httpServletRequest.setAttribute("prpLltext", prpLltext);
		String conditions = " businessno = '" + compensateNo + "' order by serialNo";
		queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		List<PrpLcfeecoins> prpLcfeecoinsList = this.getPrpLcfeecoinsService().findPrpLcfeecoins(queryRule);
		PrpLcfeecoins prpLcfeecoins = new PrpLcfeecoins();
		prpLcfeecoins.setPrpLcfeecoinsList(prpLcfeecoinsList);
		httpServletRequest.setAttribute("prpLcfeecoins", prpLcfeecoins);
		PrpDriskConfig prpDriskConfig = this.getPrpDriskConfigService().findByPrimaryKey(prpLclaim.getComCode().substring(0, 2), prpLclaim.getRiskCode(), "dealFast_case");
		if ((prpDriskConfig != null && "1".equals(prpDriskConfig.getConfigValue()))) {
			String conditions1 = " registno = '" + prpLclaim.getRegistNo() + "'";
			queryRule = QueryRule.getInstance();
			queryRule.addSql(conditions1);
			List<PrpLcheck> collection = this.getPrpLcheckService().findPrpLcheck(queryRule);
			if (collection.size() > 0) {
				PrpLcheck prpLcheck = (PrpLcheck) collection.get(0);
				httpServletRequest.setAttribute("dealFastFlag", prpLcheck.getDealFastFlag());
			}
		}
		// 送审初复核初始化
		SendUndwrtViewHelper sendUndwrtViewHelper = new SendUndwrtViewHelper();
		sendUndwrtViewHelper.LoadingSendUndwrt(httpServletRequest, compensateNo, "compp");
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		String exceedingPayout = compensateService.findExceedingPayout(user);
		httpServletRequest.setAttribute("exceedingPayout", exceedingPayout);
	}

	/**
	 * 填写实赔页面及查询实赔request的生成.
	 * 填写实赔时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param PrpLcompensate 取出的初始化信息Dto
	 * @throws Exception
	 */
	public void claimDtoToView(HttpServletRequest httpServletRequest, String claimNo, String editType) throws Exception {

		// 特殊赔案标志
		String caseType = httpServletRequest.getParameter("caseType");// 特殊赔案标志
		ClaimDto claimDto = this.getClaimService().findByPrimaryKey(claimNo);
		// 得到人伤个数
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		httpServletRequest.setAttribute("personCount", String.valueOf(claimDto.getPrpLpersonTraceList().size()));
		// 原因：要在界面上显示该保单的所有立案信息
		httpServletRequest.setAttribute("registClaimList", this.getClaimService().findByPolicyNo(prpLclaim.getPolicyNo()));
		// 得到是否重开赔案
		int recount = 0;
		// 查询重开赔案信息
		ReCaseDto reCaseDto = this.getRecaseService().findByPrimaryKey(claimNo, 1);
		PrpLrecase prpLrecase = reCaseDto.getPrpLrecase();
		if (prpLrecase != null && DataUtils.emptyToNull(prpLrecase.getId().getClaimNo()) != null) {
			recount = 1;
		}
		httpServletRequest.setAttribute("recaseFlag", String.valueOf(recount));
		// 赔款计算书主信息
		PrpLcompensate prpLcompensate = new PrpLcompensate();
		// 设置赔案类型开始
		prpLcompensate.setCaseType(prpLclaim.getCaseType());
		// 特殊赔案标志
		// 因为传过来的caseType="null"
		if (!"".equals(DataUtils.dbNullToEmpty(caseType))) {
			if (caseType.trim().equals("3") || caseType.trim().equals("4") || caseType.trim().equals("6")) {
				prpLcompensate.setCaseType(caseType);
			}
		} else {
			// 正常的流程，进行赔付
			prpLcompensate.setCaseType("2");
		}
		// 设置赔案类型结束
		prpLcompensate.setCompensateNo(""); // 计算书号
		prpLcompensate.setEditType(editType.trim());
		prpLcompensate.setLflag(prpLclaim.getLflag());
		prpLcompensate.setCaseNo(prpLclaim.getCaseNo());
		prpLcompensate.setTimes(1);
		prpLcompensate.setEscapeFlag(prpLclaim.getEscapeFlag());
		prpLcompensate.setIndemnityDuty(prpLclaim.getIndemnityDuty());
		prpLcompensate.setClassCode(prpLclaim.getClassCode());
		prpLcompensate.setRiskCode(prpLclaim.getRiskCode());
		prpLcompensate.setClaimNo(prpLclaim.getClaimNo());
		prpLcompensate.setPolicyNo(prpLclaim.getPolicyNo());
		prpLcompensate.setDeductCond(httpServletRequest.getParameter("DeductibleTerm")); // 免赔条件
		prpLcompensate.setPreserveDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLcompensate.setIndemnityDutyRate(prpLclaim.getIndemnityDutyRate());
		prpLcompensate.setIndemnityDuty(prpLclaim.getIndemnityDuty());
		prpLcompensate.setUnderWriteFlag("0");
		prpLcompensate.setSumPaidAll(prpLclaim.getSumPaid());
		prpLcompensate.setBusinessNature(prpLclaim.getBusinessNature());
		prpLcompensate.setDamageCode(prpLclaim.getDamageCode());
		prpLcompensate.setDamageName(prpLclaim.getDamageName());
		prpLcompensate.setUnderWriteFlag("0");
		// 判断保费是否已经实收
		String conditions = " policyno = '" + prpLcompensate.getPolicyNo() + "'";
		int intReturn = this.getPolicyService().checkPay(conditions);// -1为未缴费，0为未缴全，1为缴全
		prpLcompensate.setPalyFlag(intReturn);
		// 获取系统设置信息
		// 获取保费未实收是否允许理算信息
		String configValue = prpDriskConfigService.getConfigValue("ALLOW_UNPAYED_COMPENSATE", prpLcompensate.getRiskCode());
		if (configValue == null || configValue.equals("")) {
			throw new UserException(1, 3, "platform", "該險種未進行基礎數據初始化，請在基礎平台系統，險種配置中進行初始化！");
		}
		// 如果configValue =2 intReturn！=1则表示未交费不能立案
		if (configValue.equals("2") && intReturn != 1) {
			httpServletRequest.setAttribute("premiumFee", "0");// 0表示不允许理算提交
		} else {
			httpServletRequest.setAttribute("premiumFee", "1");// 0表示允许理算提交
		}
		if ("1".equals(prpLclaim.getReplevyFlag())) {
			prpLcompensate.setReplevyFlag("1");
		} else {
			prpLcompensate.setReplevyFlag("0");
		}
		prpLcompensate.setReplevyRemark(prpLclaim.getReplevyRemark());
		prpLcompensate.setCurrency(prpLclaim.getCurrency());
		prpLcompensate.setSumPaid(prpLclaim.getSumPaid());
		prpLcompensate.setMakeCom(prpLclaim.getMakeCom());
		prpLcompensate.setComCode(prpLclaim.getComCode());
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		prpLcompensate.setHandlerCode(user.getUserCode());
		prpLcompensate.setHandler1Code(prpLclaim.getHandler1Code());
		prpLcompensate.setStatisticsYM(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLcompensate.setOperatorCode(prpLclaim.getOperatorCode());
		prpLcompensate.setInputDate(new DateTime(new Date(), DateTime.YEAR_TO_SECOND));
		prpLcompensate.setUnderWriteEndDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLcompensate.setRemark(prpLclaim.getRemark());
		prpLcompensate.setFlag(prpLclaim.getFlag());

		// 设置实赔操作的状态为 新案件登记 (未处理任务)
		prpLcompensate.setStatus("1");
		prpLcompensate.setInsuredCode(prpLclaim.getInsuredCode());
		prpLcompensate.setInsuredName(prpLclaim.getInsuredName());
		prpLcompensate.setStartDate(prpLclaim.getStartDate());
		prpLcompensate.setStartHour(prpLclaim.getStartHour());
		prpLcompensate.setEndDate(prpLclaim.getEndDate());
		prpLcompensate.setEndHour(prpLclaim.getEndHour());
		prpLcompensate.setClauseType(prpLclaim.getClauseType());

		prpLcompensate.setDamageStartDate(prpLclaim.getDamageStartDate());
		String timeTemp = StringConvert.toStandardTime(prpLclaim.getDamageStartHour());
		prpLcompensate.setDamageStartHour(timeTemp.substring(0, 2));
		prpLcompensate.setDamageStartMinute(timeTemp.substring(3, 5));
		prpLcompensate.setDamageEndDate(prpLclaim.getDamageEndDate());
		String endTimeTemp = StringConvert.toStandardTime(prpLclaim.getDamageEndHour());
		prpLcompensate.setDamageEndHour(endTimeTemp.substring(0, 2));
		prpLcompensate.setDamageAddressType(prpLclaim.getDamageAddressType());
		//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
		prpLcompensate.setAddressCode(prpLclaim.getAddressCode());
		prpLcompensate.setDamageAddress(prpLclaim.getDamageAddress());
		prpLcompensate.setSumAmount(prpLclaim.getSumAmount());
		prpLcompensate.setSumPremium(prpLclaim.getSumPremium());
		prpLcompensate.setSumClaim(prpLclaim.getSumClaim());
		prpLcompensate.setHandlerName(prpLclaim.getHandlerName());
		/***  add by 中科軟 20150601 需求變更-095 begin ***/
		String riskCode = prpLclaim.getRiskCode();
		String riskType = codeService.translateRiskCodetoRiskType(riskCode);
		if (ConstantCodes.CLASSCODE_D.equals(riskType)) {
			if(ConstantCodes.RISKCODE_DAZ.equals(riskCode)){
				prpLcompensate.setAccidentType(prpLclaim.getPropAccidentType());
			} else {
				prpLcompensate.setAccidentType(prpLclaim.getCarAccidentType());//車體險肇責類型
				prpLcompensate.setPropAccidentType(prpLclaim.getPropAccidentType());//責任險肇責類型
			}
		}
		/***  add by 中科軟 20150601 需求變更-095 end ***/
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		// 查询保单信息
		// 根据出险日期还原保单信息
		PrpCmain prpCmain = this.getEndorseViewHelper().findPrpCmain(policyNo, damageDate, damageHour);
		List<PrpCitemKind> prpCitemKindList = this.getEndorseViewHelper().findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
		List<PrpCitemCar> prpCitemCarList = this.getEndorseViewHelper().findPrpCitemCar(policyNo, damageDate, damageHour);
		httpServletRequest.setAttribute("damageKindList", prpCitemKindList);
		httpServletRequest.setAttribute("prpCitemKindList", prpCitemKindList);
		// 获得保单默认的险别
		String defaultKindCode = this.getDaaClaimViewHelper().getDefaultKindCodeByPolicyDto(prpCitemKindList);
		httpServletRequest.setAttribute("defaultKindCode", defaultKindCode);
		if (prpCitemCarList != null && !prpCitemCarList.isEmpty()) {
			// 对车型等信息的支持
			PrpCitemCar prpCitemCar = prpCitemCarList.get(0);
			prpLcompensate.setClauseType(prpCitemCar.getClauseType());
			prpLcompensate.setLicenseNo(prpCitemCar.getLicenseNo());
			prpLcompensate.setClauseTypeCode(prpCitemCar.getClauseType());
			prpLcompensate.setPurchasePrice(String.valueOf(prpCitemCar.getPurchasePrice()));
			prpLcompensate.setLicenseColorCode(prpCitemCar.getLicenseColorCode());
			prpLcompensate.setLicenseColorCode(prpCitemCar.getLicenseColorCode());
			prpLcompensate.setBrandName(prpCitemCar.getBrandName());
			prpLcompensate.setCarKindCode(prpCitemCar.getCarKindCode());
			prpLcompensate.setEngineNo(prpCitemCar.getEngineNo());
			prpLcompensate.setFrameNo(prpCitemCar.getFrameNo());
			prpLcompensate.setSeatCount(String.valueOf(prpCitemCar.getSeatCount()));
		}
		// 得到共保和股东业务信息临分信息
		httpServletRequest.setAttribute("coinsFlag", prpCmain.getCoinsFlag());
		httpServletRequest.setAttribute("shareHolderFlag", prpCmain.getShareHolderFlag());
		conditions = " reinsType in ('1','2')  and policyno = '" + prpLclaim.getPolicyNo() + "'";
		httpServletRequest.setAttribute("tempReinsFlag", reinsServiceManager.getReinsService().getSumFacShare(prpLclaim.getPolicyNo(), new com.sinosoft.sysframework.common.datatype.DateTime(prpLclaim.getDamageStartDate())) > 0 ? "1" : "0");
		httpServletRequest.setAttribute("coinsFlag", prpCmain.getCoinsFlag());
		if ("1".equals(prpCmain.getCoinsFlag())) {
			List<PrpCcoins> coinsList = this.getPrpCcoinsService().findPrpCcoins(QueryRule.getInstance().addSql("policyNo = '" + prpCmain.getPolicyNo() + "'"));
			if (coinsList != null && !coinsList.isEmpty()) {
				for (Iterator<PrpCcoins> it = coinsList.iterator(); it.hasNext();) {
					PrpCcoins prpCcoins = it.next();
					if ("1".equals(prpCcoins.getChiefFlag())) {
						httpServletRequest.setAttribute("chiefFlag", prpCcoins.getChiefFlag());
						break;
					}

				}
			}
		}
		// 默认带出查勘人
		if (DataUtils.emptyToNull(prpLclaim.getRegistNo()) != null) {
			PrpLcheck prpLcheck = this.getPrpLcheckService().findPrpLcheck(new PrpLcheckId(prpLclaim.getRegistNo(), 1));
			if (prpLcheck != null) {
				prpLcompensate.setChecker1(prpLcheck.getChecker1());
			}
		}
		Collection<?> reinsDangerUnitCollection = reinsServiceManager.getReinsService().getDangerUnit(prpLcompensate.getPolicyNo(), new com.sinosoft.sysframework.common.datatype.DateTime(prpLclaim.getDamageStartDate()));
		httpServletRequest.setAttribute("ReinsDangerUnitCollection", reinsDangerUnitCollection);
		// 赋值初始的计算书Times信息
		conditions = " ClaimNo= '" + claimNo + "'";
		List<PrpLcompensate> tempTime = this.getCompensateService().findByConditions(conditions);
		if (tempTime != null) {
			prpLcompensate.setTimes(tempTime.size() + 1);
		} else {
			prpLcompensate.setTimes(1);
		}
		// 初始化人员信息（从定核损带过来）
		List<PrpLpersonLoss> personLossList = this.initPersonLoss(httpServletRequest, claimNo, claimDto);
		// double medicLimit = 0;
		// double deathLimit = 0;
		double propLimit = 0;
		String duty = prpLcompensate.getIndemnityDuty();
		String configCode = this.getCodeService().translateRiskCodetoConfigCode(prpLclaim.getRiskCode());
		// 取强制保单限额
		if ("RISKCODE_DAZ".equals(configCode)) {
			conditions = " policyNo='" + prpLclaim.getPolicyNo() + "'";
			List<PrpClimit> limitList = this.getPrpClimitService().findPrpClimit(conditions, new DateTime(prpLclaim.getDamageStartDate()).toString(), new DateTime(prpCmain.getStartDate()).toString());
			if (limitList != null && !limitList.isEmpty()) {
				String limitType = "";
				for (PrpClimit prpClimit : limitList) {
					limitType = prpClimit.getId().getLimitType();
					if (!"4".equals(duty)) {
						if ("90".equals(limitType) || "91".equals(limitType) || "92".equals(limitType)) {
							propLimit = prpClimit.getLimitFee();
						}
					} else {
						if ("93".equals(limitType) || "94".equals(limitType) || "95".equals(limitType)) {
							propLimit = prpClimit.getLimitFee();
						}
					}
				}
			}
		}
		// this.getPersonLossService().initPersonLoss(configCode,
		// prpLclaim.getRiskCode(), personLossList, medicLimit, deathLimit);

		// 初始化标的（从定核损带过来）
		List<PrpLloss> prpLlossList = this.initLossItem(httpServletRequest, claimDto);
		// 对prpLlossListTemp进行 进一步初始化 ---------------------------
		double realPay = 0.00;
		if ("RISKCODE_DAZ".equals(configCode)) { // 强制得核定赔偿 按费用优先级别赋值
			for (PrpLloss prpLloss : prpLlossList) {
				if (ConstantCodes.KINDCODE_D_BZ.equals(prpLloss.getKindCode())) {
					if (propLimit >= (prpLloss.getSumLoss() - prpLloss.getSumRest())) {
						prpLloss.setSumDefPay(prpLloss.getSumLoss() - prpLloss.getSumRest());
						propLimit -= (prpLloss.getSumLoss() - prpLloss.getSumRest());
					} else if (propLimit > 0) {
						prpLloss.setSumDefPay(Number2(propLimit));
						propLimit = 0;
					} else {
						prpLloss.setSumDefPay(0);
					}
					prpLloss.setSumRealPay(Number2(prpLloss.getSumDefPay()));
					prpLloss.setRiskCode(prpLclaim.getRiskCode());
				}
			}
		} else if ("RISKCODE_DAY".equals(configCode)) { // 0505核定赔偿设为零
			for (PrpLloss prpLloss : prpLlossList) {
				if ("B".equals(prpLloss.getKindCode())) {
					prpLloss.setSumDefPay(0);
				} else {
					prpLloss.setSumDefPay(prpLloss.getSumLoss() - prpLloss.getSumRest());
				}
				realPay = (prpLloss.getSumDefPay()) * (prpLloss.getClaimRate() * 0.01) * (prpLloss.getIndemnityDutyRate() * 0.01) * (1 - (prpLloss.getDutyDeductibleRate() * 0.01 + prpLloss.getDeductiblerate() * 0.01));
				prpLloss.setSumRealPay(realPay);
			}
		} else { // 其他的车险 核定赔偿设为 核定损失-残值
			for (PrpLloss prpLloss : prpLlossList) {
				prpLloss.setSumDefPay(prpLloss.getSumLoss());// 应该再减去机动车交强险的金额
			}
		}
		ExceptDeductibleRateDto afterDeductibleRateDto = this.getLossInfo(httpServletRequest, prpLcompensate);
		Collection<Map<String, Object>> collection = (Collection<Map<String, Object>>) httpServletRequest.getAttribute("limitList");
		Map<String, Map<String, Object>> limitMap = new HashMap<String, Map<String, Object>>();
		Map<String, Object> limit = null;
		if (collection != null && !collection.isEmpty()) {
			Iterator<?> it = collection.iterator();
			while (it.hasNext()) {
				limit = (Map<String, Object>) it.next();
				limitMap.put(limit.get("limitKindCode").toString(), limit);
			}
		}
		// 初始化时保存一次
		// 客制化，新的计算车物损方式
		if (prpLlossList != null && !prpLlossList.isEmpty()) {
			for (PrpLloss prpLloss : prpLlossList) {
				if (DataUtils.emptyToNull(prpLloss.getKindCode()) != null) {
					limit = limitMap.get(prpLloss.getKindCode());
					if (limit != null) {
						//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y START
						prpLloss.setDeductible(limit.get("limitDeductible").equals(0)?new Double( limit.get("limitDeductible")+""):(Double) limit.get("limitDeductible"));
						prpLloss.setDeductiblerate(limit.get("limitDeductibleRate").equals(0)?new Double( limit.get("limitDeductibleRate")+""):(Double) limit.get("limitDeductibleRate"));
						//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y END
					}
					try {
						double sumRealPay = 0d;
						sumRealPay = (Double) GroovyViewHelper.evaluate(prpLloss);
						prpLloss.setSumRealPay(sumRealPay < 0 ? 0 : sumRealPay);
					} catch (UserException e) {
						e.printStackTrace();
					}
				}
			}
		}
		// 客制化人伤理赔金计算 人伤赔付合计
		Map<Integer, Double> personSumPay = new HashMap<Integer, Double>();
		Double sumPersonPay = 0d;
		for (PrpLpersonLoss prpLpersonLoss : personLossList) {
			prpLpersonLoss.setClaimNo(prpLclaim.getClaimNo());
			sumPersonPay = personSumPay.get(prpLpersonLoss.getPersonNo()) == null ? 0 : personSumPay.get(prpLpersonLoss.getPersonNo());
			limit = limitMap.get(prpLpersonLoss.getKindCode());
			if (limit != null) {
				prpLpersonLoss.setDeductible((Double) limit.get("limitDeductible"));
				prpLpersonLoss.setDeductiblerate((Double) limit.get("limitDeductibleRate"));
			}
			double sumRealPay = 0d;
			try {
				sumRealPay = (Double) GroovyViewHelper.evaluate(prpLpersonLoss);
			} catch (UserException e) {
				e.printStackTrace();
			}
			sumRealPay = sumRealPay < 0 ? 0 : sumRealPay;
			prpLpersonLoss.setSumRealPay(sumRealPay);
			personSumPay.put(prpLpersonLoss.getPersonNo(), sumPersonPay + sumRealPay);
		}
		// 汇总人员赔付合计
		for (PrpLpersonLoss prpLpersonLoss : personLossList) {
			prpLpersonLoss.setSumRealPay1(personSumPay.get(prpLpersonLoss.getPersonNo()));
		}
		PrpLctext prpLctext = new PrpLctext();
		// 设值文本的内容
		httpServletRequest.setAttribute("prpLctext", prpLctext);

		// 设置结案信息
		PrpLltext prpLltext = new PrpLltext();
		// 增加 结案报告的内容
		StringBuffer strTemp = new StringBuffer("");
		strTemp.append("一、被保險人概況").append("\r\n");
		strTemp.append("二、事故經過及原因調查").append("\r\n");
		strTemp.append("三、認定責任").append("\r\n");
		strTemp.append("四、足額投保").append("\r");
		strTemp.append("五、是否存在重複投保及向第三者追償前景").append("\r\n");
		strTemp.append("六、索賠及定損").append("\r\n");
		strTemp.append("七、總結和賠付");
		prpLltext.setContext(strTemp.toString());
		prpLltext.getId().setTextType("08");
		httpServletRequest.setAttribute("prpLltext", prpLltext);

		// 设置相关代码的中文转换
		this.changeCodeToName(httpServletRequest, prpLcompensate);
		// 设置窗体表单中各个多选框中列表信息的内容
		this.setSelectionList(httpServletRequest, prpLcompensate);
		// 查询相同保单号的出险次数
		this.getDaaRegistViewHelper().getSamePolicyRegistInfo(httpServletRequest, prpLcompensate.getPolicyNo(), prpLclaim.getRegistNo());

		// 设置主实赔信息内容到窗体表单
		httpServletRequest.setAttribute("prpLcompensate", prpLcompensate);
		String strRiskType = this.getCodeService().translateRiskCodetoRiskType(prpLcompensate.getRiskCode());
		httpServletRequest.setAttribute("riskType", strRiskType);
		// 设置各个子表信息项到窗体表单
		CompensateDto compensateDto = new CompensateDto();
		compensateDto.setPrpLcompensate(prpLcompensate);
		compensateDto.setPrpLclaim(prpLclaim);
		compensateDto = this.compensateService.findByAppendInformation(compensateDto);
		compensateDto.setPrpLpersonLossList(personLossList);
		compensateDto.setPrpLlossList(prpLlossList);
		/**  add by 中科軟  二結帶出一結之車體險訊息顯示 begin */
		if(ConstantCodes.CLASSCODE_D.equals(strRiskType)){
			QueryRule queryRule = QueryRule.getInstance();
//			mantis：CLM0074 ，處理人員：BK007 蘇哲，需求單編號：CLM0074.理賠系統-車體險訊息[自負額發票] -start
//			queryRule.addLike("id.compensateNo", "C" + prpLcompensate.getClaimNo() + "%");
			queryRule.addEqual("id.compensateNo", prpLcompensate.getCompensateNo());
			queryRule.addAscOrder("id.serialNo");
//			mantis：CLM0074 ，處理人員：BK007 蘇哲，需求單編號：CLM0074.理賠系統-車體險訊息[自負額發票] -end
			compensateDto.setPrpLcarInsuranceList(prpLcarInsuranceService.findPrpLcarInsurance(queryRule));
		}
		/**  add by 中科軟  二結帶出一結之車體險訊息顯示 end */
		// 缴费标志，判断保费是否交付
		int intPayFee = this.checkPay(httpServletRequest, policyNo);
		prpLcompensate.setPayFee(intPayFee);
		compensateDto.setPrpLcompensate(prpLcompensate);
		String registNo = this.getCodeService().translateBusinessCode(prpLcompensate.getClaimNo(), false);
		RegistDto registDto = this.getRegistService().findByPrimaryKey(registNo);
		compensateDto.setPrpLregistExtList(registDto.getPrpLregistExtList());
		this.setSubInfo(httpServletRequest, compensateDto, afterDeductibleRateDto);
		// 送审初复核初始化
		SendUndwrtViewHelper sendUndwrtViewHelper = new SendUndwrtViewHelper();
		sendUndwrtViewHelper.LoadingSendUndwrt(httpServletRequest, claimNo, "compe");
		String exceedingPayout = compensateService.findExceedingPayout(user);
		httpServletRequest.setAttribute("exceedingPayout", exceedingPayout);
	}

	/**
	 * 根据compensateDto中的各子表内的信息填充界面
	 * @param httpServletRequest 返回给页面的request
	 * @param compensateDto 实赔的数据类
	 * @throws Exception
	 */
	private void setSubInfo(HttpServletRequest httpServletRequest, CompensateDto compensateDto, ExceptDeductibleRateDto afterDeductibleRateDto) throws Exception {

		// 给报案信息补充说明多行列表准备数据
		PrpLregistExt prpLregistExt = new PrpLregistExt();
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		String registNo = this.getCodeService().translateBusinessCode(prpLcompensate.getClaimNo(), false);
		prpLregistExt.getId().setRegistNo(registNo);
		prpLregistExt.setRiskCode(prpLcompensate.getRiskCode());
		List<PrpLregistExt> prpLregistExtList = compensateDto.getPrpLregistExtList();
		prpLregistExt.setRegistExtList(prpLregistExtList);
		httpServletRequest.setAttribute("prpLregistExt", prpLregistExt);

		// 特别约定信息多行列表准备数据
		PrpCengage prpCengage = new PrpCengage();
		List<PrpCengage> prpCengageList = compensateDto.getPrpCengageList();
		List<PrpCengage> prpCengageListTemp = new ArrayList<PrpCengage>();
		if (prpCengageList != null && !prpCengageList.isEmpty()) {
			for (PrpCengage temp : prpCengageList) {
				if (DataUtils.emptyToNull(temp.getClauseCode()) != null && temp.getClauseCode().charAt(0) == 'T') {
					prpCengageListTemp.add(temp);
				}
			}
			// boolean cFlag = false;
			prpCengageList.clear();
			prpCengageList.addAll(prpCengageListTemp);
			prpCengageListTemp.clear();
			PrpCengage prpCengageTemp = new PrpCengage();
			for (PrpCengage temp : prpCengageList) {
				if ("0".equals(temp.getTitleFlag())) {
					// cFlag = true;
					prpCengageListTemp.add(prpCengageTemp);
					prpCengageTemp = new PrpCengage();
					PropertyUtils.copyProperties(prpCengageTemp, temp);
				} else {
					prpCengageTemp.setContext(prpCengageTemp.getContext() + temp.getClauses() + "<br>");
				}
			}
			prpCengageListTemp.add(prpCengageTemp);
			if (prpCengageListTemp.size() > 0) {
				prpCengageListTemp.remove(0);
			}
		}
		prpCengage.setPrpCengageList(prpCengageListTemp);
		httpServletRequest.setAttribute("prpCengage", prpCengage);

		// 赔偿限额/免赔额信息多行列表准备数据
		PrpDlimit prpDlimit = new PrpDlimit();
		List<PrpDlimit> prpDlimitList = compensateDto.getPrpDlimitList();
		prpDlimit.setPrpDlimitList(prpDlimitList);
		httpServletRequest.setAttribute("prpDlimit", prpDlimit);

		// 货币代码的列表
		Map<String, String> currencyMap = new HashMap<String, String>();
		List<PrpDcurrency> collection = this.getCodeService().getCurrencyList();
		for (Iterator<PrpDcurrency> iterator = collection.iterator(); iterator.hasNext();) {
			PrpDcurrency prpDcurrency = iterator.next();
			currencyMap.put(prpDcurrency.getCurrencyCode(), prpDcurrency.getCurrencyCName());
		}

		// 赔偿限额/免赔额信息多行列表准备数据
		List<PrpClimit> prpClimitList = compensateDto.getPrpClimitList();
		for (PrpClimit temp : prpClimitList) {
			if (currencyMap.containsKey(temp.getId().getCurrency())) {
				temp.setCurrencyName(currencyMap.get(temp.getId().getCurrency()));
			}
			if (temp.getId().getLimitGrade().trim().equals("1")) {
				temp.getId().setLimitGrade("保單");
			} else {
				temp.getId().setLimitGrade("標的險別");
			}
			temp.setLimitTypeName(this.getCodeService().translateLimit(temp.getRiskCode(), temp.getId().getLimitType(), true));
		}
		PrpClimit prpClimit = new PrpClimit();
		prpClimit.setPrpClimitList(prpClimitList);
		httpServletRequest.setAttribute("prpClimit", prpClimit);

		// String edtiType = httpServletRequest.getParameter("editType");
		List<PrpLloss> prpLlossList = compensateDto.getPrpLlossList();
		if (prpLlossList != null && !prpLlossList.isEmpty()) {
			for (PrpLloss temp : prpLlossList) {
				if (currencyMap.containsKey(temp.getCurrency())) {
					temp.setCurrencyName(currencyMap.get(temp.getCurrency()));
				}
				if (currencyMap.containsKey(temp.getCurrency())) {
					temp.setCurrency1Name(currencyMap.get(temp.getCurrency1()));
				}

				if (currencyMap.containsKey(temp.getCurrency())) {
					temp.setCurrency2Name(currencyMap.get(temp.getCurrency2()));
				}
				if (currencyMap.containsKey(temp.getCurrency())) {
					temp.setCurrency3Name(currencyMap.get(temp.getCurrency3()));
				}
				if (currencyMap.containsKey(temp.getCurrency())) {
					temp.setCurrency4Name(currencyMap.get(temp.getCurrency4()));
				}
				temp.setKindName(this.getCodeService().translateKindCode(temp.getRiskCode(), temp.getKindCode(), true));
			}
		}

		PrpLloss prpLloss = new PrpLloss();
		prpLloss.setPrpLlossList(prpLlossList);
		httpServletRequest.setAttribute("prpLloss", prpLloss);

		// 赔付人员信息多行列表准备数据
		List<PrpLpersonLoss> prpLpersonLossList = compensateDto.getPrpLpersonLossList();
		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (prpLpersonLossList != null && !prpLpersonLossList.isEmpty()) {
			for (PrpLpersonLoss temp : prpLpersonLossList) {
				if (currencyMap.containsKey(temp.getCurrency2())) {
					temp.setCurrency2Name(currencyMap.get(temp.getCurrency2()).toString());
				}
				if (currencyMap.containsKey(temp.getCurrency())) {
					temp.setCurrencyName(currencyMap.get(temp.getCurrency()).toString());
				}
				temp.setKindName(this.getCodeService().translateKindCode(temp.getRiskCode(), temp.getKindCode(), true));
			}
		}
		PrpLpersonLoss prpLpersonLoss = new PrpLpersonLoss();
		prpLpersonLoss.setPrpLpersonLossList(prpLpersonLossList);
		httpServletRequest.setAttribute("prpLpersonLoss", prpLpersonLoss);

		/****************** 支付对象信息 start *********************************/
		List<PrpLpayObjectInfo> tempPrpLpayObjectInfoList = compensateDto.getPrpLpayObjectInfoList();
		List<PrpLpayObjectInfo> prpLpayObjectInfoList = new ArrayList<PrpLpayObjectInfo>();
		List<PrpLpayObjectInfo> chargePrpLpayObjectInfoList = new ArrayList<PrpLpayObjectInfo>();
		if (tempPrpLpayObjectInfoList != null && !tempPrpLpayObjectInfoList.isEmpty()) {
			for (PrpLpayObjectInfo prpLpayObjectInfo : tempPrpLpayObjectInfoList) {
				// 赔付对象信息
				if (PrpLpayObjectInfo.CERTITYPE_PAYOBJECT.equals(prpLpayObjectInfo.getId().getCertiType())) {
					prpLpayObjectInfoList.add(prpLpayObjectInfo);
				} else if (PrpLpayObjectInfo.CERTITYPE_CHARGE.equals(prpLpayObjectInfo.getId().getCertiType())) {
					chargePrpLpayObjectInfoList.add(prpLpayObjectInfo);
				}
			}
		}
		PrpLpayObjectInfo prpLpayObjectInfo = new PrpLpayObjectInfo();
		prpLpayObjectInfo.setPrpLpayObjectInfoList(prpLpayObjectInfoList);
		httpServletRequest.setAttribute("prpLpayObjectInfo", prpLpayObjectInfo);
		/****************** 支付对象信息 end *********************************/

		/****************** 赔款费用信息 start *********************************/
		// 赔款费用信息多行列表准备数据
		List<PrpLcharge> prpLchargeList = compensateDto.getPrpLchargeList();
		if (prpLchargeList != null && !prpLchargeList.isEmpty()) {
			for (PrpLcharge temp : prpLchargeList) {
				temp.setKindName(this.getCodeService().translateKindCode(temp.getRiskCode(), temp.getKindCode(), true));
				if (currencyMap.containsKey(temp.getCurrency())) {
					temp.setCurrencyName(currencyMap.get(temp.getCurrency()).toString());
				}
				// 客制化增加赔付信息
				for (PrpLpayObjectInfo tempObject : chargePrpLpayObjectInfoList) {
					if (tempObject.getId().getSerialNo().intValue() == temp.getId().getSerialNo().intValue()) {
						temp.setPrpLpayObjectInfo(tempObject);
						break;
					}
				}
			}
		}
		PrpLcharge prpLcharge = new PrpLcharge();
		prpLcharge.setPrpLchargeList(prpLchargeList);
		httpServletRequest.setAttribute("prpLcharge", prpLcharge);

		/****************** 赔款费用信息 end *********************************/
		// 加入对车险损失险免赔额的处理
		List<PrpLdeductible> prpLdeductibleList = compensateDto.getPrpLdeductibleList();
		// 首先，在理算免赔额表中查找数据，如果，找不到再从险别表中取免赔额
		PrpLdeductible prpLdeductible = null;
		if (prpLdeductibleList != null && !prpLdeductibleList.isEmpty()) {
			prpLdeductible = prpLdeductibleList.get(0);// 目前，车险只有一个到险别的免赔额
		}
		// 如果，找不到免赔额再从险别表中取免赔额
		PrpLclaim prpLclaim = compensateDto.getPrpLclaim();
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.getEndorseViewHelper().findPrpCmain(policyNo, damageDate, damageHour);
		List<PrpCitemKind> prpCitemKindList = this.getEndorseViewHelper().findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
		if (prpLdeductible == null) {
			if (prpCitemKindList != null && !prpCitemKindList.isEmpty()) {
				Iterator<PrpCitemKind> citemKind = prpCitemKindList.iterator();
				PrpCitemKind prpCitemKind = null;
				while (citemKind.hasNext()) {
					prpCitemKind = citemKind.next();
					if ("M1".equals(prpCitemKind.getKindCode())) {
						if (prpCitemKind.getValue() != 0) {
							prpLdeductible = new PrpLdeductible();
							prpLdeductible.setDeductible(String.valueOf(prpCitemKind.getValue()));
						} else {
							prpLdeductible = null;
						}
					}
				}
			}
		}
		httpServletRequest.setAttribute("prpLdeductible", prpLdeductible);
		// 设置是否全损信息
		if (DataUtils.emptyToNull(prpLclaim.getEscapeFlag()) != null && prpLclaim.getEscapeFlag().length() > 1) {
			String isLossAll = prpLclaim.getEscapeFlag().substring(1, 2);
			httpServletRequest.setAttribute("isLossAll", isLossAll);
		} else {
			httpServletRequest.setAttribute("isLossAll", prpLclaim.getEscapeFlag2());
		}
		// 用於取出车辆的实际价值
		List<PrpCitemCar> prpCitemCarList = this.getEndorseViewHelper().findPrpCitemCar(policyNo, damageDate, damageHour);
		PolicyDto policyDto = new PolicyDto();
		policyDto.setPrpCitemCarList(prpCitemCarList);
		policyDto.setPrpCitemKindList(prpCitemKindList);
		if (prpCitemCarList != null && !prpCitemCarList.isEmpty()) {
			PrpCitemCar prpCitemCar = prpCitemCarList.get(0);
			double purchasePrice = prpCitemCar.getPurchasePrice();
			double finalValue = this.getDeprecateRate(policyDto, prpLclaim);
			double factValue = Double.parseDouble(new DecimalFormat("#").format((purchasePrice * finalValue)));
			httpServletRequest.setAttribute("finalValue", new Double(finalValue));
			if (DataUtils.emptyToNull(prpLcompensate.getCompensateNo()) != null) {
				for (PrpLloss temp : prpLlossList) {
					if (ConstantsCollection.MainCarLoss.contains(temp.getKindCode()) && temp.getCarRealValue() != 0) {
						factValue = temp.getCarRealValue();
						purchasePrice = Double.parseDouble(new DecimalFormat("#").format(factValue / finalValue));
					}
				}
			}
			httpServletRequest.setAttribute("factValue", new Double(factValue));
			httpServletRequest.setAttribute("purchasePrice", new Double(purchasePrice));
		}
		// 用於取出车辆的实际价值
		this.getExceptDeductibleRate(httpServletRequest, compensateDto, policyDto, prpLloss, prpLpersonLoss, afterDeductibleRateDto);
		// 垫付赔案所走代码，保证按钮显示状态正确
		PrpLregist prpLregist = this.getPrpLregistService().findPrpLregist(registNo);
		String comCode = prpLregist.getComCode().substring(0, 2);
		PrpDriskConfig prpDriskConfig = this.getPrpDriskConfigService().findByPrimaryKey(comCode, prpLregist.getRiskCode(), "advance_case");
		if (prpDriskConfig != null && "1".equals(prpDriskConfig.getConfigValue())) {
			String advanceCaseStatus = "";
			String conditions = " fullreportno='" + registNo + "' and claimtype='1'";
			List<PrpLagent> prpLagentList = this.getPrpLagentService().findByConditions(conditions);
			if (prpLagentList != null && prpLagentList.size() > 0) {
				PrpLagent prpLagent = prpLagentList.get(0);
				advanceCaseStatus = prpLagent.getCaseStatus();
				httpServletRequest.setAttribute("advanceCaseStatus", prpLagent.getCaseStatus());
			}
			if ("1".equals(prpLregist.getAdvanceType())) {
				httpServletRequest.setAttribute("displayInputInfo", "display:");
				// 影像信息已经上传但事故信息还未上传，显示上传信息按钮
				if ("".equals(advanceCaseStatus) || "00".equals(advanceCaseStatus)) {
					httpServletRequest.setAttribute("displayUpload", "display:");
					httpServletRequest.setAttribute("displayGetConfirm", "display:none");
				} else if ("10".equals(advanceCaseStatus)) {
					// 上传完成还未获取确认信息，显示获取确认信息按钮
					httpServletRequest.setAttribute("displayUpload", "display:none");
					httpServletRequest.setAttribute("displayGetConfirm", "display:");
				} else {
					httpServletRequest.setAttribute("displayUpload", "display:none");
					httpServletRequest.setAttribute("displayGetConfirm", "display:");
					httpServletRequest.setAttribute("disabled", "disabled");
				}
			} else {
				// 否则的话全部不显示
				httpServletRequest.setAttribute("displayInputInfo", "display:none");
				httpServletRequest.setAttribute("displayUpload", "display:none");
				httpServletRequest.setAttribute("displayGetConfirm", "display:none");
			}
			httpServletRequest.setAttribute("isSpecial", "1");
			httpServletRequest.setAttribute("advance", "1");
			httpServletRequest.setAttribute("advanceType", prpLregist.getAdvanceType());
		}
		String chargeType = httpServletRequest.getParameter("chargeType");
		if (!CommonUtils.isEmpty(prpLcompensate.getCompensateNo()) && prpLcompensate.getCompensateNo().startsWith("D")) {
			chargeType = "D";
		}
		httpServletRequest.setAttribute("chargeType", chargeType);
		//mantis：CLM0179，處理人員：DP0713，需求單編號：新核心-車險關聯單判斷確認是否導致車輛訊息頁簽隱藏 START
		// 判断是否是关联单
//		boolean isCompelFlag = prpLregistrpolicyService.isCompelFlag(registNo);
		//任意險 看的到 A01
		boolean isCompelFlag = true;
		if(prpLcompensate.getRiskCode().equals("A01")){
			isCompelFlag = false;
		}
		//mantis：CLM0179，處理人員：DP0713，需求單編號：新核心-車險關聯單判斷確認是否導致車輛訊息頁簽隱藏 END
		// 简易赔案
		String simpleFlag = prpLclaim.getSimpleFlag();
		boolean certainLossFlag = false; // 定损是否显示
		if (!isCompelFlag && "1".equals(simpleFlag)) {
			certainLossFlag = true;
			this.setCertainLoss(compensateDto, httpServletRequest);
		}
		httpServletRequest.setAttribute("certainLossFlag", certainLossFlag);
		PrpLcarInsurance prpLcarInsurance = new PrpLcarInsurance();
		List<PrpLcarInsurance> prpLcarInsuranceList = compensateDto.getPrpLcarInsuranceList();
		prpLcarInsurance.setPrpLcarInsuranceList(prpLcarInsuranceList);
		if(prpLcarInsuranceList!=null&&prpLcarInsuranceList.size()>0){
			prpLcarInsurance.setInvoiceDate(prpLcarInsuranceList.get(0).getInvoiceDate());
		}
		httpServletRequest.setAttribute("prpLcarInsurance", prpLcarInsurance);
		httpServletRequest.setAttribute("KINDCODE_D_BZ",com.sinosoft.claim.common.ConstantCodes.KINDCODE_D_BZ);
		
		//獲取各個險種一結肇責類型
		String claimNo = prpLcompensate.getClaimNo();
		String conditions = " compensateNo like 'C"+claimNo+"%' and ( underWriteFlag = '1' or underWriteFlag = '3' ) and mutualCompensateNo is null order by underWriteEndDate asc , times asc ";
		List<PrpLcompensate> compensateList = this.getPrpLcompensateService().findByConditions(conditions);
		if(!CommonUtils.isEmpty(compensateList)){//非一結
			Map<String , String > firstCompeAccidentTpye = new HashMap<String , String >();
			List<PrpLloss> tempPrpLlossList = null;
			List<PrpLpersonLoss> tempPrpLpersonLossList = null;
			String tempKindCode = null;
			for(PrpLcompensate compe : compensateList){
				if(!CommonUtils.isEmpty(compe.getAccidentType()) || !CommonUtils.isEmpty(compe.getPropAccidentType())){
					tempPrpLpersonLossList = this.prpLpersonLossService.findByConditions(" compensateNo = '"+compe.getCompensateNo()+"'");
					for(PrpLpersonLoss p : tempPrpLpersonLossList){
						tempKindCode = p.getKindCode();
						if(!firstCompeAccidentTpye.containsKey(tempKindCode)){
							firstCompeAccidentTpye.put("times_" + tempKindCode , String.valueOf(compe.getTimes()));
							if(ConstantCodes.RISKCODE_DAZ.equals(compe.getRiskCode())){
								firstCompeAccidentTpye.put(tempKindCode, compe.getAccidentType());
							} else {
								firstCompeAccidentTpye.put(tempKindCode, compe.getPropAccidentType());
							}
						}
					}
					if(ConstantCodes.RISKCODE_DAZ.equals(compe.getRiskCode())){
						continue;
					}
					tempPrpLlossList = this.prpLlossService.findByConditions(" compensateNo = '"+compe.getCompensateNo()+"'");
					for(PrpLloss p : tempPrpLlossList){
						tempKindCode = p.getKindCode();
						if(!firstCompeAccidentTpye.containsKey(tempKindCode)){
							firstCompeAccidentTpye.put("times_" + tempKindCode , String.valueOf(compe.getTimes()));
							List<String> carKindCodes = this.codeService.getResponKindCode(1);
							if(carKindCodes.contains(tempKindCode)){//車體險
								firstCompeAccidentTpye.put(tempKindCode, compe.getAccidentType());
							}else{//非車體險
								firstCompeAccidentTpye.put(tempKindCode, compe.getPropAccidentType());
							}
						}
					}
				}
			}
			httpServletRequest.setAttribute("firstCompeAccidentTpye", firstCompeAccidentTpye);
		}
	}

	/***
	 * 取折旧率
	 * @param policyDto
	 * @return
	 * @throws Exception
	 */
	private double getDeprecateRate(PolicyDto policyDto, PrpLclaim prpLclaim) throws Exception {
		double finalValue = 1d;
		List<PrpCitemCar> prpCitemCarList = policyDto.getPrpCitemCarList();
		if (prpCitemCarList != null && !prpCitemCarList.isEmpty()) {
			PrpCitemCar prpCitemCar = prpCitemCarList.get(0);
			String riskCode = prpCitemCar.getRiskCode();
			String clauseType = prpCitemCar.getClauseType();
			String useNatureCode = prpCitemCar.getUseNatureCode();
			String carKindCode1 = "";
			Calendar enrollDate = Calendar.getInstance();
			enrollDate.setTime(prpCitemCar.getEnrollDate());
			enrollDate.set(Calendar.HOUR_OF_DAY, 12);
			Calendar startDate = Calendar.getInstance();
			startDate.setTime(prpLclaim.getDamageStartDate());// 初登记日期
			String standardTime = StringConvert.toStandardTime(prpLclaim.getDamageStartHour());// 出险日期
			startDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(standardTime.substring(0, 2)));
			startDate.set(Calendar.MINUTE, Integer.parseInt(standardTime.substring(3, 5)));
			int intMonth = 1;
			int intYear = 0;
			if (riskCode.equals(ConstantCodes.RISKCODE_DAA)) {
				// useNatureCode是使用性质 1是自用，2是营业 自用的clausType 设置为01，营业为02
				if (CommonUtils.isEmpty(clauseType)) {
					clauseType = "01";
					if ("1".equals(useNatureCode)) {
						clauseType = "01";
					} else if ("2".equals(useNatureCode)) {
						clauseType = "02";// 其他
					}
					List<PrpCitemKind> prpCitemKindList = policyDto.getPrpCitemKindList();
					for (PrpCitemKind prpCitemKind : prpCitemKindList) {
						if (ConstantCodes.KINDCODE_A01_0L.equals(prpCitemKind.getKindCode()) || ConstantCodes.KINDCODE_A01_1B.equals(prpCitemKind.getKindCode())) {
							clauseType = "03";
							break;
						} else if (ConstantCodes.KINDCODE_A01_0M.equals(prpCitemKind.getKindCode()) || ConstantCodes.KINDCODE_A01_1C.equals(prpCitemKind.getKindCode())) {
							clauseType = "04";
							break;
						}
					}
				}
				while (true) {// 计算已折旧年
					enrollDate.add(Calendar.YEAR, 1);
					if (enrollDate.compareTo(startDate) > 0) {
						enrollDate.add(Calendar.YEAR, -1);// 减去本次的偏移量
						break;
					}
					intYear++;
				}
				while (true) {// 计算折旧年外的折旧月
					enrollDate.add(Calendar.MONTH, 1);
					if (enrollDate.compareTo(startDate) > 0) {
						break;
					}
					intMonth++;
				}
				carKindCode1 = String.valueOf(intMonth);
			}
			BLPrpDdeprecateRateFacade blPrpDdeprecateRateFacade = new BLPrpDdeprecateRateFacade();
			PrpDdeprecateRateDto prpDdeprecateRateDto = blPrpDdeprecateRateFacade.findByPrimaryKey(riskCode, clauseType, carKindCode1);
			if (prpDdeprecateRateDto != null) {
				String perMonthRate = prpDdeprecateRateDto.getPerMonthRate();
				Double perYearRate = prpDdeprecateRateDto.getPerYearRate();
				finalValue = 1 - (Double.parseDouble(perMonthRate) + intYear * perYearRate);
				if (finalValue < 0.20) {
					finalValue = 0.20;
				}
			}
		}
		return finalValue;
	}

	/**
	 * 获取选择框和列表框中的所有内容
	 * @param httpServletRequest 返回给页面的request
	 * @param prpPrepayDto 实赔的数据类
	 * @throws Exception
	 */
	private void setSelectionList(HttpServletRequest httpServletRequest, PrpLcompensate prpLcompensateDto) throws Exception {

		// (1)得到实赔类型列表
		List<PrpDcode> compeQuality = this.getCodeService().getCodeType("CompeQuality", prpLcompensateDto.getRiskCode());
		httpServletRequest.setAttribute("qualityCheckList", compeQuality);
		// (1)得到实赔类型列表
		List<PrpDcode> reportTypes = this.getCodeService().getCodeType("ReportType", prpLcompensateDto.getRiskCode());
		httpServletRequest.setAttribute("reportTypes", reportTypes);
		// (2)得到案件种类列表列表
		List<PrpDcode> claimTypes = this.getCodeService().getCodeType("CaseCode", prpLcompensateDto.getRiskCode());
		httpServletRequest.setAttribute("claimTypes", claimTypes);
		// (3)得到出险地址类型列表
		List<PrpDcode> damageAddressTypes = this.getCodeService().getCodeType("DamageAddress", prpLcompensateDto.getRiskCode());
		httpServletRequest.setAttribute("damageAddressTypes", damageAddressTypes);
		// (4)得到车辆种类列表
		List<PrpDcode> carKindCodes = this.getCodeService().getCodeType("CarKind", prpLcompensateDto.getRiskCode());
		httpServletRequest.setAttribute("carKindCodes", carKindCodes);
		// (5)得到车牌底色列表
		List<PrpDcode> licenseColorCode = this.getCodeService().getCodeType("LicenseColor", prpLcompensateDto.getRiskCode());
		httpServletRequest.setAttribute("licenseColorCodes", licenseColorCode);
		// (6)得到赔偿责任列表
		List<PrpDcode> indemnityDuty = this.getCodeService().getCodeType("IndemnityDuty", prpLcompensateDto.getRiskCode());
		httpServletRequest.setAttribute("indemnityDutys", indemnityDuty);
		// (7)得到赔案类别列表
		List<PrpDcode> escapeFlags = this.getCodeService().getCodeType("CaseCode", prpLcompensateDto.getRiskCode());
		httpServletRequest.setAttribute("escapeFlags", escapeFlags);
		// (8)得到得到性别
		List<PrpDcode> driverSex = this.getCodeService().getCodeType("SexCode", prpLcompensateDto.getRiskCode());
		httpServletRequest.setAttribute("driverSexs", driverSex);
		// 车牌号码
		List<PrpLthirdParty> LicenseNoList = this.getCodeService().getLicenseNoList(this.getCodeService().translateBusinessCode(prpLcompensateDto.getClaimNo(), false));
		List<LabelValueBean> claimFlagList = new ArrayList<LabelValueBean>();
		if (LicenseNoList != null && !LicenseNoList.isEmpty()) {
			Iterator<PrpLthirdParty> iteratorTemp = LicenseNoList.iterator();
			while (iteratorTemp.hasNext()) {
				PrpLthirdParty prpLthirdPartyDto = iteratorTemp.next();
				claimFlagList.add(new LabelValueBean(prpLthirdPartyDto.getLicenseNo(), prpLthirdPartyDto.getLicenseNo()));
			}
		}
		httpServletRequest.setAttribute("LicenseNoList", claimFlagList);// LabelValueBean
		// 车牌号码
		List<PrpLthirdParty> licenseNoList = this.getCodeService().getLicenseNoList(this.getCodeService().translateBusinessCode(prpLcompensateDto.getClaimNo(), false));
		httpServletRequest.setAttribute("licenseNoList", licenseNoList);// PrpLthirdParty
		/*** add by chenjie 2013-04-27 客制化需求内容 start */
		if (ConstantCodes.RISKCODE_DAZ.equals(prpLcompensateDto.getRiskCode())) {
			// 强制险 赔付代号
			httpServletRequest.setAttribute("payCodeList", ConstantsCollection.qzPayCodeList);
		} else {
			// 任意险 赔付代号
			httpServletRequest.setAttribute("payCodeList", ConstantsCollection.payCodeList);
		}
		// 全損/分損代號
		httpServletRequest.setAttribute("lossTypeList", ConstantsCollection.lossTypeList);
		// 本車肇事責任
		httpServletRequest.setAttribute("selfIndemnityDutyNameList", ConstantsCollection.selfIndemnityDutyNameList);
		// 肇事类型
		httpServletRequest.setAttribute("accidentTypeList", ConstantsCollection.accidentTypeList);
		// 肇责百分比
		httpServletRequest.setAttribute("indemnityDutyList", ConstantsCollection.indemnityDutyList);
		// 給付追償情況
		httpServletRequest.setAttribute("payOfCompList", ConstantsCollection.payOfCompList);
		// 受害人身份
		httpServletRequest.setAttribute("identityOfInjuredPersonList", ConstantsCollection.identityOfInjuredPersonList);
		// 出事當時乘坐狀況
		httpServletRequest.setAttribute("rideSituationList", ConstantsCollection.rideSituationList);
		// 受害人健保就醫代號
		httpServletRequest.setAttribute("medicalCodeList", ConstantsCollection.medicalCodeList);
		/** 受害关系 */
		httpServletRequest.setAttribute("rideSituationList", ConstantsCollection.rideSituationList);
		/** 檢察署 */
		httpServletRequest.setAttribute("prosecutorsOfficeList", ConstantsCollection.prosecutorsOfficeList);
		/** 傷亡情形 */
		httpServletRequest.setAttribute("casualtiesList", ConstantsCollection.casualtiesList);
		/**
		 * 费用类型
		 */
		httpServletRequest.setAttribute("detailCodeList", ConstantsCollection.detailCodeList);
		/**
		 * 伤残等级
		 */
		httpServletRequest.setAttribute("injuryGradeList", ConstantsCollection.injuryGradeList);
		/** 理算 费用资讯 费用名称 */
		httpServletRequest.setAttribute("chargeCodeList", ConstantsCollection.chargeCodeList);
		/** 理算 赔付对象 費用類型 */
		httpServletRequest.setAttribute("paymentKindList", ConstantsCollection.paymentKindList);
		/** 支付類別 */
		httpServletRequest.setAttribute("payObjectTypeList", ConstantsCollection.payObjectTypeList);
		/*** 任意险 - 人伤給付類別代號 */
		httpServletRequest.setAttribute("payTypeCodeList", ConstantsCollection.payTypeCodeList);
		/** 給付追償情況 */
		httpServletRequest.setAttribute("paySituationList", ConstantsCollection.paySituationList);
		/** 健保局追償狀況 */
		httpServletRequest.setAttribute("chasingLossesStatusList", ConstantsCollection.chasingLossesStatusList);
		/** 赔偿速度 */
		httpServletRequest.setAttribute("speedFlagList", ConstantsCollection.speedFlagList);
		/** 赔偿速度 */
		httpServletRequest.setAttribute("subrogationList", ConstantsCollection.subrogationList);
		/*** add by chenjie 2013-04-27 客制化需求内容 end */
		// 用于独立处理费用的判断，如果是强制险则显示，任意险隐藏
		httpServletRequest.setAttribute("RISKCODE_DAZ", ConstantCodes.RISKCODE_DAZ);
		// 支付对象 帳號歸屬人證件類型
		httpServletRequest.setAttribute("prpdpaymentaccountCertificateTypeList", ConstantsCollection.prpdpaymentaccountCertificateTypeList);
		// 支付对象 支付币种
		httpServletRequest.setAttribute("prpLpayObjectInfoCurrencyList", this.codeService.findPayCurrencyMap());
		httpServletRequest.setAttribute("LOCAL_CURRENCY", ConstantCodes.LOCAL_CURRENCY);
		httpServletRequest.setAttribute("writtenEstimateList", ConstantsCollection.writtenEstimateList);
		httpServletRequest.setAttribute("collisionCountList", ConstantsCollection.collisionCountList);
		/* #083 第三次修改 需求变更 增加憑證類型 */
		httpServletRequest.setAttribute("certificateTypeList", ConstantsCollection.certificateTypeList);
		httpServletRequest.setAttribute("reservedEstimateList", ConstantsCollection.reservedEstimateList);
	}

	/**
	 * 根据PrpPrepayDto中的已经设置的代码内容，对代码进行名称转换
	 * @param httpServletRequest 返回给页面的request
	 * @param prpPrepayDto 实赔的数据类
	 * @param PrepayDto 查询出的数据类
	 * @throws Exception
	 */
	private void changeCodeToName(HttpServletRequest httpServletRequest, PrpLcompensate prpLcompensate) throws Exception {

		// (1)条款名称的转换
		String clauseType = prpLcompensate.getClauseType();
		String clauseName = this.getCodeService().translateCodeCode("ClauseType", clauseType, true);
		prpLcompensate.setClauseName(clauseName);
		// (2)号牌颜色转换
		String licenseColorCodeCode = prpLcompensate.getLicenseColorCode();
		String licenseColor = this.getCodeService().translateCodeCode("LicenseColor", licenseColorCodeCode, true);
		prpLcompensate.setLicenseColor(licenseColor);
		// (3)车辆类型转换
		String carKindCode = prpLcompensate.getCarKindCode();
		String carKind = this.getCodeService().translateCodeCode("CarKind", carKindCode, true);
		prpLcompensate.setCarKind(carKind);
		// (4)对业务归属结构进行转换
		String comCode = prpLcompensate.getComCode();
		String comName = this.getCodeService().translateComCode(comCode, true);
		prpLcompensate.setComName(comName);
		// (7)对经办人进行转换
		String handlerCode = prpLcompensate.getHandlerCode();
		String handlerName = this.getCodeService().translateUserCode(handlerCode, true);
		prpLcompensate.setHandlerName(handlerName);
		// (8)对案件类型进行转换
		String caseType = prpLcompensate.getCaseType();
		String caseTypeName = this.getCodeService().translateCodeCode("CaseType", caseType, true);
		prpLcompensate.setCaseTypeName(caseTypeName);
		// (8)对案件类型进行转换
		String indemnityDutyRate = String.valueOf(prpLcompensate.getIndemnityDuty());
		String indemnityDutyRateName = this.getCodeService().translateCodeCode("IndemnityDuty", indemnityDutyRate, true);
		prpLcompensate.setIndemnityDutyName(indemnityDutyRateName);

	}

	/**
	 * 根据赔款计算书号和保单号,赔案号,案件状态，车牌号码，操作时间查询实赔信息
	 * @param httpServletRequest 返回给页面的request
	 * @param compensateNo 赔款计算书号
	 * @param policyNo 保单号
	 * @param claimNo 赔案号
	 * @throws Exception
	 */

	public void setPrpLcompensateDtoToView(HttpServletRequest httpServletRequest, String compensateNo, String policyNo, String claimNo, String licenseNo, String status, String operateDate, String underWriteFlag) throws Exception {

		// compensateNo,policyNo,claimNo
		// 根据输入的保单号，实赔号生成SQL where 子句
		claimNo = StringUtils.rightTrim(claimNo);
		policyNo = StringUtils.rightTrim(policyNo);
		compensateNo = StringUtils.rightTrim(compensateNo);
		licenseNo = StringUtils.rightTrim(licenseNo);
		operateDate = StringUtils.rightTrim(operateDate);
		String strSign = httpServletRequest.getParameter("OperateDateSign");

		StringBuffer conditions = new StringBuffer(" 1=1 ");
		conditions.append(StringConvert.convertString("a.compensateNo", compensateNo, httpServletRequest.getParameter("CompensateNoSign")));
		conditions.append(StringConvert.convertString("a.claimNo", claimNo, httpServletRequest.getParameter("ClaimNoSign")));
		conditions.append(StringConvert.convertString("a.policyNo", policyNo, httpServletRequest.getParameter("PolicyNoSign")));
		conditions.append(StringConvert.convertString("c.licenseNo", licenseNo, httpServletRequest.getParameter("LicenseNoSign")));
		if (status.trim().length() > 0) {
			conditions.append(" AND b.status in (" + status + ")");
		}
		if (underWriteFlag.trim().length() > 0) {
			conditions.append(" AND a.underWriteFlag in (" + underWriteFlag + ") ");
		}
		if (operateDate != null && !operateDate.trim().equals("")) {
			conditions.append(StringConvert.convertDate("b.operateDate", operateDate, strSign));
		}

		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		conditions.append(uiPowerInterface.addPower(userDto, "a", "", "ComCode"));
		// 查询预赔信息
		// 得到多行实赔主表信息
		List<PrpLcompensate> compensateList = this.getCompensateService().findByQueryConditions(conditions.toString());
		PrpLcompensate prpLcompensate = new PrpLcompensate();
		prpLcompensate.setCompensateList(compensateList);
		prpLcompensate.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLcompensate", prpLcompensate);

	}

	/**
	 * 有预赔的案件复核不通过的案件不能进入实赔理算 返回值 true 已出 false 未出
	 * @param httpServletRequest 返回给页面的request
	 * @param claimNo 赔案号
	 * @throws Exception
	 */
	public boolean checkPrepay(HttpServletRequest httpServletRequest, String claimNo) throws Exception {
		boolean blnRetrun = true;
		// 取得赔款计算书信息
		String conditions = "claimNo ='" + claimNo.trim() + "'";
		List<PrpLprepay> prpLprepayList = this.getPrepayService().findByConditions(conditions);
		for (PrpLprepay prpLprepay : prpLprepayList) {
			if (prpLprepay.getUnderWriteFlag() == null || !prpLprepay.getUnderWriteFlag().trim().equals("1")) {
				blnRetrun = false;
				break;
			}
		}
		return blnRetrun;
	}

	/**
	 * 核损不通过的案件不能进入实赔理算 返回值 true 已出 false 未出
	 * @param httpServletRequest 返回给页面的request
	 * @param claimNo 赔案号
	 * @throws Exception
	 */
	public boolean checkVerifyLoss(HttpServletRequest httpServletRequest, String claimNo) throws Exception {
		boolean blnRetrun = true;
		// 根据赔案号码取得对应的报案号码

		String registNo = this.getCodeService().translateBusinessCode(claimNo, false);
		// 取得赔款计算书信息
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", registNo);
		//mantis：CLM0180，處理人員：DP0713，需求單編號：新核心-無法產生未處理理算核損註記確認
		System.out.println("CLM0180:registNo="+registNo);
		List<PrpLverifyLoss> prpLverifyLossList = this.getPrpLverifyLossService().findPrpLverifyLoss(queryRule);

		//mantis：CLM0180，處理人員：DP0713，需求單編號：新核心-無法產生未處理理算核損註記確認
		System.out.println("CLM0180:prpLverifyLossList="+(prpLverifyLossList != null));
		if (prpLverifyLossList != null && !prpLverifyLossList.isEmpty()) {
			//mantis：CLM0180，處理人員：DP0713，需求單編號：新核心-無法產生未處理理算核損註記確認
			System.out.println("CLM0180:prpLverifyLossList222="+(!prpLverifyLossList.isEmpty()));
			for (PrpLverifyLoss prpLverifyLoss : prpLverifyLossList) {
				//mantis：CLM0180，處理人員：DP0713，需求單編號：新核心-無法產生未處理理算核損註記確認
				System.out.println("CLM0180:underWriteFlag="+(prpLverifyLoss.getUnderWriteFlag()));
				if (prpLverifyLoss.getUnderWriteFlag() == null || !prpLverifyLoss.getUnderWriteFlag().trim().equals("1")) {
					blnRetrun = false;
					break;
				}
			}
		}
		return blnRetrun;
	}

	/**
	 * 核损不通过的案件不能进入实赔理算 返回值 true 已出 false 未出
	 * @param httpServletRequest 返回给页面的request
	 * @param claimNo 赔案号
	 * @throws Exception
	 */
	public int checkCondition(HttpServletRequest httpServletRequest, String claimNo) throws Exception {
		int intReturn = 0;
		ClaimDto claimDto = this.getClaimService().findByPrimaryKey(claimNo);
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		if (prpLclaim == null) {
			return 1;
		}
		// 根据赔案号码取得对应的报案号码

		String registNo = this.getCodeService().translateBusinessCode(claimNo, false);
		// 取得赔款计算书信息
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", registNo);
		List<PrpLverifyLoss> prpLverifyLossList = this.getPrpLverifyLossService().findPrpLverifyLoss(queryRule);
		if (prpLverifyLossList == null || prpLverifyLossList.isEmpty()) {
			return 2;
		}
		return intReturn;
	}

	/**
	 * 核损不通过的案件不能进入实赔理算 返回值 true 已出 false 未出
	 * @param httpServletRequest 返回给页面的request
	 * @param claimNo 赔案号
	 * @throws Exception
	 */
	public boolean checkCertify(HttpServletRequest httpServletRequest, String claimNo) throws Exception {
		boolean blnReturn1 = false;
		boolean blnReturn2 = false;
		// 根据赔案号码取得对应的报案号码

		String registNo = this.getCodeService().translateBusinessCode(claimNo, false);
		RegistDto registDto = this.getRegistService().findByPrimaryKey(registNo);
		String licenseNo = registDto.getPrpLregist().getLicenseNo();
		CertifyDto certifyDto = this.getCertifyService().findCertifyDto(registNo);
		List<PrpLcertifyCollect> prpLcertifyCollectList = certifyDto.getPrpLcertifyCollectList();
		for (PrpLcertifyCollect prpLcertifyCollect : prpLcertifyCollectList) {
			if (prpLcertifyCollect.getId().getLossItemCode() != null && prpLcertifyCollect.getId().getLossItemCode().trim().equals("0")) {
				if (prpLcertifyCollect.getCollectFlag() != null && prpLcertifyCollect.getCollectFlag().trim().equals("1")) {
					blnReturn1 = true;
				}
			}
			if (prpLcertifyCollect.getLossItemName() != null && prpLcertifyCollect.getLossItemName().trim().equals(licenseNo)) {
				if (prpLcertifyCollect.getCollectFlag() != null && prpLcertifyCollect.getCollectFlag().trim().equals("1")) {
					blnReturn2 = true;
				}
			}
		}
		return (blnReturn1 && blnReturn2);
	}

	/**
	 * 根据赔款计算书号和保单号和赔案号查询待复核的实赔信息
	 * @param httpServletRequest 返回给页面的request
	 * @param compensateNo 赔款计算书号
	 * @param policyNo 保单号
	 * @param claimNo 赔案号
	 * @throws Exception
	 */

	public void getApproveCompensateList(HttpServletRequest httpServletRequest, String compensateNo, String policyNo, String claimNo) throws Exception {
		// compensateNo,policyNo,claimNo
		// 根据输入的保单号，实赔号生成SQL where 子句
		claimNo = StringUtils.rightTrim(claimNo);
		policyNo = StringUtils.rightTrim(policyNo);
		compensateNo = StringUtils.rightTrim(compensateNo);

		StringBuffer conditions = new StringBuffer(" 1=1 ");
		conditions.append(StringConvert.convertString("prplcompensate.compensateNo", compensateNo, httpServletRequest.getParameter("CompensateNoSign")));
		conditions.append(StringConvert.convertString("prplcompensate.policyNo", policyNo, httpServletRequest.getParameter("PolicyNoSign")));
		conditions.append(StringConvert.convertString("prplcompensate.claimNo", claimNo, httpServletRequest.getParameter("ClaimNoSign")));
		conditions.append(" AND ( prplcompensate.ApproverCode IS NULL OR  prplcompensate.ApproverCode='' OR prplcompensate.UnderWriteFlag='2')");
		// 查询预赔信息
		// 得到多行实赔主表信息
		List<PrpLcompensate> compensateList = this.getCompensateService().findByApproveConditions(conditions.toString());
		PrpLcompensate prpLcompensate = new PrpLcompensate();
		prpLcompensate.setCompensateList(compensateList);
		prpLcompensate.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLcompensate", prpLcompensate);
	}

	/**
	 * 承保保单中的内容以及免赔率等内容
	 * @throws Exception
	 */
	public ExceptDeductibleRateDto getLossInfo(HttpServletRequest httpServletRequest, PrpLcompensate prpLcompensate) throws Exception {
		UIDeductCondAction uiDeductCondAction = UIDeductCondAction.getInstance();// ??
		double dblDeductibleRate = 0;
		double dblDutyDeductibleRate = 0;
		double dblDriverDeductibleRate = 0;
		//
		Map<String, Double> afterDeductibleRateMap = new HashMap<String, Double>(); // 绝对免赔率的不计免赔
		Map<String, Double> afterDutyDeductibleRateMap = new HashMap<String, Double>(); // 事故责任免赔率的不计免赔
		String claimNo = prpLcompensate.getClaimNo();
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(claimNo);
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.getEndorseViewHelper().findPrpCmain(policyNo, damageDate , damageHour);
		List<PrpCitemKind> prpCitemKindList = this.getEndorseViewHelper().findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
		List<PrpCitemCar> prpCitemCarList = this.getEndorseViewHelper().findPrpCitemCar(policyNo, damageDate, damageHour);

		List<PrpLclaimLoss> prpLclaimLossList = this.prpLclaimLossService.findPrpLclaimLoss(claimNo);
		List<PrpCitemKind> prpcItemKindDtoLossList = this.getPrpcItemKindDtoLossList(prpCitemKindList, prpLclaimLossList);
		String clauseType = "";
		if (!CommonUtils.isEmpty(prpCitemCarList)) {
			PrpCitemCar prpCitemCarDto = prpCitemCarList.get(0);
			clauseType = prpCitemCarDto.getClauseType();
		}
		for (int i = 0; i < prpcItemKindDtoLossList.size(); i++) {
			PrpCitemKind prpCitemKind = (PrpCitemKind) prpcItemKindDtoLossList.get(i);
			if (prpCmain.getOperateDate() == null) {
				throw new UserException(0, 0, "保單簽單日期爲空");
			}
			// 事故责任免赔率PrpDdeductCondService
			dblDutyDeductibleRate = uiDeductCondAction.getDeductibleRateOfAccident(prpLcompensate.getRiskCode(), prpCitemKind.getKindCode(), prpLclaim.getIndemnityDuty(), prpCitemKind.getModeCode(), clauseType, new DateTime(prpCmain.getStartDate())
					.toString());
			afterDutyDeductibleRateMap.put(prpCitemKind.getKindCode(), new Double(dblDutyDeductibleRate));// 按险别将事故责任免赔责任免除後的不计免赔率存入
			ExceptDeductibleRateDto exceptDeductibleRateDto = uiDeductCondAction.getDeductibleRateOfAbsolute(prpLcompensate.getClauseType(), prpCitemKind.getKindCode(), prpLcompensate.getPrpLdeductCondList(), prpLcompensate.getRiskCode(),
					new DateTime(prpCmain.getOperateDate()).toString());
			dblDeductibleRate = exceptDeductibleRateDto.getDeductibleRate();
			afterDeductibleRateMap.put(prpCitemKind.getKindCode(), new Double(exceptDeductibleRateDto.getAfterDeductibleRate()));// 按险别将绝对免赔责任免除後的不计免赔率存入
			// 驾驶员免赔率
			dblDriverDeductibleRate = 0;

			prpCitemKind.setDeductibleRate(dblDeductibleRate);
			prpCitemKind.setDutyDeductibleRate(dblDutyDeductibleRate);
			prpCitemKind.setDriverDeductibleRate(dblDriverDeductibleRate);
			// 赔偿比例
			double lossClaimRate = 100.00;
			if (prpCitemKind.getKindCode().equals("A")) {
				String escapeFlag = prpLcompensate.getEscapeFlag();
				if (escapeFlag != null && escapeFlag.length() > 1 && escapeFlag.substring(1, 2).equals("Y")) {
					double purchasePrice = Double.parseDouble(DataUtils.nullToZero(prpLcompensate.getPurchasePrice()));
					double amount = prpCitemKind.getAmount();
					if (purchasePrice != amount) {
						lossClaimRate = amount * 100.00 / purchasePrice;
						prpCitemKind.setClaimRate(lossClaimRate);
					}
				}
			} else {
				prpCitemKind.setClaimRate(lossClaimRate);
			}
			httpServletRequest.setAttribute("prpCitemKind", prpCitemKind);
			// 责任比例
			prpCitemKind.setIndemnityDutyRate(100.0d);
		}
		httpServletRequest.setAttribute("prpCitemKindListInit", prpcItemKindDtoLossList);
		ExceptDeductibleRateDto afterDeductibleRateDto = new ExceptDeductibleRateDto();

		afterDeductibleRateDto.setAfterDutyDeductibleRateMap(afterDutyDeductibleRateMap);// 将事故责任免赔率以及绝对免赔率责任免除後的不计免赔率送入DTO
		afterDeductibleRateDto.setAfterDeductibleRateMap(afterDeductibleRateMap);// 将事故责任免赔率以及绝对免赔率责任免除後的不计免赔率送入DTO
		// 本案对各险别限额的控制
		String configCode = this.getCodeService().translateRiskCodetoConfigCode(prpLclaim.getRiskCode());
		if ("RISKCODE_DAZ".equals(configCode)) {// 强制险限制到每个个人
			CompensateKindLimitViewHelper.setLimitInfo(prpLclaim, httpServletRequest);
		} else {
			PolicyDto policyDto = new PolicyDto();
			policyDto.setPrpCmain(prpCmain);
			policyDto.setPrpCitemKindList(prpCitemKindList);
			policyDto.setPrpCitemCarList(prpCitemCarList);
			CompensateKindLimitViewHelper.setLimitInfo(policyDto, prpLclaim, httpServletRequest);
		}
		return afterDeductibleRateDto;
	}

	/**
	 * 出新的赔款计算数的时候初始化人员的信息
	 * @throws Exception
	 */
	public List<PrpLpersonLoss> initPersonLoss(HttpServletRequest httpServletRequest, String claimNo, ClaimDto claimDto) throws Exception {

		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		String riskCode = prpLclaim.getRiskCode();
		String configCode = this.getCodeService().translateRiskCodetoConfigCode(riskCode);
		// 得到赔付人员信息
		PrpCitemKind prpCitemKindTemp = new PrpCitemKind();
		List<PrpLpersonLoss> personLossListTemp = new ArrayList<PrpLpersonLoss>();
		// 报案号码
		String registNo = prpLclaim.getRegistNo();
		// 序列号
		int serialNo = 0;
		// 免赔率
		double dblDutyDeductibleRate = 0;

		String flag = ""; // 不计免赔率特约条款标志
		double exceptDeductiblePay = 0.00;
		// 判断是否已出过计算书且核赔通过，如果是这样，再出计算书，不需要带出人伤定损信息-----------------|
		String conditions = "  ClaimNo = '"+prpLclaim.getClaimNo()+"' and (UnderWriteFlag = '1' or UnderWriteFlag = '3') and mutualCompensateNo is null order by inputDate desc";
		List<PrpLcompensate> compensateList = this.getCompensateService().findByConditions(conditions);
		Date inputDate = null;
		conditions = "";
		if (!CommonUtils.isEmpty(compensateList)) {
			String compeNo = null;
			for(PrpLcompensate prpLcompensate : compensateList){
				compeNo = prpLcompensate.getCompensateNo();
				if(compeNo.charAt(0)=='C' || compeNo.charAt(0)=='R' ){
					if(conditions.length() > 0){
						conditions += " or ";
					}
					conditions += "compensateNo = '"+ compeNo +"'";
					if(compeNo.charAt(0)=='C'){
						inputDate = prpLcompensate.getInputDate();
					}
				}
			}
		}
		/* 需求变更#83第二次调整  理算有估損調整，以估損調整為主 */
		//獲取理算之前的估損金額調整。
		List<PrpLclaimLoss> claimLoss = claimDto.getPrpLclaimLossList();
		Map<String, Double> personLossMap = new HashMap<String, Double>();//有調整的險別之估損金額
		String tempKey = null;
		String tempFeeCategory = null;
		Map<String, Double> lossMap = new HashMap<String, Double>();//車物損賠付險別估損金額
		Map<String, PrpLclaimLoss> claimLossMap = new HashMap<String, PrpLclaimLoss>();
		if (claimLoss != null && !claimLoss.isEmpty()) {//存在估損
			for (PrpLclaimLoss loss : claimLoss) {
				tempFeeCategory = loss.getFeeCategory();
				if ("P".equals(loss.getLossFeeType()) ) {
					tempKey = loss.getKindCode();
					//範圍是車物損
					boolean carAndprop = "C".equals(tempFeeCategory) || "G".equals(tempFeeCategory);
					//可賠付車損
					boolean KindCodeForProp = ConstantsCollection.KindCodeForProp.contains(tempKey);
					//可賠付物損
					boolean KindCodeForCar = ConstantsCollection.KindCodeForCar.contains(tempKey);
					//可賠付人傷
					boolean KindCodeForPerson = ConstantsCollection.KindCodeForPerson.contains(tempKey);
					if ( (!carAndprop && KindCodeForPerson)
							|| ( carAndprop && KindCodeForPerson && !(KindCodeForCar || KindCodeForProp))) {
						//範圍是非車物損賠付且可賠付人傷的，範圍是車損但能賠付人傷且不能賠付車物損的（WEB端沒控制這裡處理）
						//tempKey = loss.getKindCode() + "_" + tempFeeCategory;
						//只區分車物損和人傷，不區分車損、無損 user自行判斷
						if (lossMap.containsKey(tempKey)) {//險別估損合併
							lossMap.put(tempKey, loss.getSumClaim() + lossMap.get(tempKey));
							//加總同險別同賠付類型的預估金額
							PrpLclaimLoss temp = claimLossMap.get(tempKey);
							temp.setSumClaim(temp.getSumClaim() + loss.getSumClaim());
						} else {
							lossMap.put(tempKey, loss.getSumClaim());
							claimLossMap.put(tempKey, (PrpLclaimLoss) BeanUtils.cloneBean(loss));
						}
						if ("2".equals(loss.getDatafrom())) {// 本筆記錄有調整
							if (personLossMap.containsKey(tempKey)) {
								personLossMap.put(tempKey, loss.getSumClaim() + personLossMap.get(tempKey));
							} else {//取險別該類賠付的預估總額
								personLossMap.put(tempKey, lossMap.get(tempKey));
							}
						} else {//本次記錄無調整
							if (personLossMap.containsKey(tempKey)) {//但是險別之前有過調整，則統計調整之後的險別估損金額
								personLossMap.put(tempKey, loss.getSumClaim() + personLossMap.get(tempKey));
							}
						}
					}
				}
			}
		}
		//統計人傷賠付
		Map<String , PrpLpersonLoss> mapPrpLpersonLoss = new HashMap<String , PrpLpersonLoss>();
		Map<String,Double> payMap = new HashMap<String , Double>();//已賠付人傷賠付各險別已賠付
		if(!CommonUtils.isEmpty(conditions)){//有過賠付，屬二次賠付
			if(conditions.length() > 0){
				conditions = " ( "+ conditions +" ) order by compensateNo asc , serialno asc ";
				List<PrpLpersonLoss> tempLosslist = this.prpLpersonLossService.findByConditions(conditions);
				tempKey = null;
				for (PrpLpersonLoss tempPrpLpersonLoss : tempLosslist) {
					if(CommonUtils.isEmpty(tempPrpLpersonLoss.getIdentifyNumber())){
						continue;
					}
					tempKey = tempPrpLpersonLoss.getIdentifyNumber() + "_" + tempPrpLpersonLoss.getKindCode() + "_" + tempPrpLpersonLoss.getLiabDetailCode();
					if(tempPrpLpersonLoss.getId().getCompensateNo().charAt(0) == 'C'){
						//賠付只統計賠款，不統計追償
						mapPrpLpersonLoss.put(tempKey, tempPrpLpersonLoss);// 身份證號碼、險別、費用類型一致代表同一筆記錄
					}
					tempKey = tempPrpLpersonLoss.getKindCode();
					if (tempPrpLpersonLoss.getId().getCompensateNo().charAt(0) == 'R') {
						//已追償的案子統計各險別已追償
						if (payMap.containsKey(tempKey)) {
							payMap.put(tempKey, tempPrpLpersonLoss.getSumRealPay() + payMap.get(tempKey));
						} else {
							if (lossMap.containsKey(tempKey)) {//有險別估損，才計入已追償的金額到險別已賠付
								payMap.put(tempKey, tempPrpLpersonLoss.getSumRealPay());
							}
						}
					}
					if(claimLossMap.containsKey(tempKey)){//險別已有過賠付了，不再帶出估損
						claimLossMap.remove(tempKey);
					}
				}
			}
			//統計已賠付各個險別的賠付金額
			List<PrpLloss> allKindPayInfos = this.getCompensateService().getPrpLlossForReplevy(prpLclaim.getClaimNo());
			for(PrpLloss tempPrpLloss : allKindPayInfos){
				tempKey = tempPrpLloss.getKindCode();
				if (payMap.containsKey(tempKey)) {
					payMap.put(tempKey, tempPrpLloss.getSumLoss()+ payMap.get(tempKey));
				} else {
					if (lossMap.containsKey(tempKey)) {//有險別估損，才計入已追償的金額到險別已賠付
						payMap.put(tempKey, tempPrpLloss.getSumLoss());
					}
				}
			}
		}
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.getEndorseViewHelper().findPrpCmain(policyNo, damageDate , damageHour);
		List<PrpCitemKind> prpCitemKindList = this.getEndorseViewHelper().findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
		List<PrpCitemCar> prpCitemCarList = this.getEndorseViewHelper().findPrpCitemCar(policyNo, damageDate, damageHour);
		Map<String, PrpCitemKind> kindMap = new HashMap<String, PrpCitemKind>();
		if (prpCitemKindList != null && !prpCitemKindList.isEmpty()) {
			for (PrpCitemKind p : prpCitemKindList) {
				kindMap.put(p.getKindCode(), p);
			}
		}
		UIDeductCondAction uiDeductCondAction = UIDeductCondAction.getInstance();
		CertainLossDto certainLossDto = this.getCertainLossService().findByUnderWriteEndDate(registNo, inputDate);
		if (!CommonUtils.isEmpty(certainLossDto.getPrpLverifyLossList())) {
			PrpLloss prpLloss = new PrpLloss();
			String defaultKindCode = this.findDefaultKindCode(prpCitemKindList, "prpLpersonLoss");

			List<PrpLperson> prpLpersonList = certainLossDto.getPrpLpersonList();
			if (prpLpersonList != null && prpLpersonList.size() > 0) {
				PrpLpersonLoss prpLpersonLoss = null;
				// 判断是否是关联单
				boolean isCompelFlag = prpLregistrpolicyService.isCompelFlag(registNo);
				for (PrpLperson prpLpersonTemp : prpLpersonList) {
					// 取得保单的信息
					serialNo++;
					prpLpersonLoss = new PrpLpersonLoss();
					prpCmain = this.getEndorseViewHelper().findPrpCmain(prpLpersonTemp.getPolicyNo(), damageDate , damageHour);
					prpCitemKindList = this.getEndorseViewHelper().findPrpCitemKind(prpLpersonTemp.getPolicyNo(), damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
					List<PrpCitemKind> cTemp = prpCitemKindList;
					for (int i = 0; i < cTemp.size(); i++) {
						prpCitemKindTemp = cTemp.get(i);
						if (prpCitemKindTemp.getId().getItemKindNo().equals(prpLpersonTemp.getItemKindNo())) {
							break;
						}
					}
					if (prpCitemKindTemp.getFlag() != null && prpCitemKindTemp.getFlag().length() >= 5) {
						flag = prpCitemKindTemp.getFlag().substring(4, 5);
					}
					prpCitemCarList = this.getEndorseViewHelper().findPrpCitemCar(policyNo, damageDate, damageHour);
					PrpCitemCar prpCitemCar = prpCitemCarList.get(0);
					if (prpCmain.getOperateDate() == null) {
						throw new UserException(0, 0, "保單生效期爲空");
					}
					// 获得事故责任免赔率
					dblDutyDeductibleRate = uiDeductCondAction.getDeductibleRateOfAccident(prpLpersonTemp.getRiskCode(), prpLpersonTemp.getKindCode(), prpLclaim.getIndemnityDuty(), "0", prpCitemCar.getClauseType(), new DateTime(prpCmain.getStartDate()).toString());
					String strRiskType = this.getCodeService().translateRiskCodetoRiskType(prpLpersonTemp.getRiskCode());
					if ("D".equals(strRiskType)) {
						prpLloss.setRiskCode(prpLpersonTemp.getRiskCode());
						prpLloss.setPolicyNo(prpLpersonTemp.getPolicyNo());
						prpLloss.setAmount(prpCitemKindTemp.getAmount());
						prpLloss.getId().setCompensateNo(claimNo);
						prpLloss.setKindCode(prpLpersonTemp.getKindCode() == null ? "" : prpLpersonTemp.getKindCode().trim());
						prpLloss.setKindName(this.getCodeService().translateKindCode(prpLpersonTemp.getRiskCode(), prpLpersonTemp.getKindCode(), true));
						prpLloss.setCurrency(prpLpersonTemp.getCurrency());
						prpLloss.setSumLoss(prpLpersonTemp.getSumDefLoss());
						prpLloss.setSumDefPay(prpLpersonTemp.getSumDefLoss());
						prpLloss.setSumRest(0);
						prpLloss.setIndemnityDutyRate(prpLclaim.getIndemnityDutyRate());
						prpLloss.setDeductiblerate(0);
						prpLloss.setDutyDeductibleRate(dblDutyDeductibleRate);
						// 没有传递claimNo的值
						prpLpersonLoss.setPersonNo(prpLpersonTemp.getId().getPersonNo());
						prpLpersonLoss.setCasualties(prpLpersonTemp.getWoundGrade());
						prpLpersonLoss.setOverAmount("");
						prpLpersonLoss.getId().setSerialNo(serialNo);
						prpLpersonLoss.setRiskCode(prpLpersonTemp.getRiskCode());
						prpLpersonLoss.setPersonName(prpLpersonTemp.getPersonName());
						prpLpersonLoss.setSex(prpLpersonTemp.getPersonSex());
						prpLpersonLoss.setAge(prpLpersonTemp.getPersonAge());
						prpLpersonLoss.setIdentifyNumber(prpLpersonTemp.getIdentifyNumber());
						prpLpersonLoss.setFamilyName(prpLpersonTemp.getFamilyName());
						prpLpersonLoss.setItemKindNo(prpLpersonTemp.getItemKindNo());
						prpLpersonLoss.setAmount(prpCitemKindTemp.getAmount());
						prpLpersonLoss.setDeductible(prpCitemKindTemp.getDeductible());
						if ("1".equals(flag)) {
							exceptDeductiblePay = prpLpersonTemp.getSumDefLoss() * prpLpersonLoss.getClaimRate() * 0.01 * prpLpersonLoss.getIndemnityDutyRate() * 0.01 * prpLpersonLoss.getDeductiblerate() * 0.01
									* prpLpersonLoss.getDutyDeductibleRate() * 0.01;
							prpLpersonLoss.setExceptDeductiblePay(exceptDeductiblePay);
							prpLpersonLoss.setExceptDeductibleRate(dblDutyDeductibleRate);
						} else {
							prpLpersonLoss.setExceptDeductibleRate(0);
							prpLpersonLoss.setExceptDeductiblePay(0);
						}

						prpLpersonLoss.setFlag(flag);

						if ("RISKCODE_DAZ".equals(configCode)
								&& (ConstantsCollection.MainPersonLoss.contains(prpLpersonTemp.getKindCode()) || ConstantsCollection.InsAnddriver.contains(prpLpersonTemp.getKindCode()) || ConstantsCollection.ThirdPersonLoss.contains(prpLpersonTemp
										.getKindCode()))) {
							prpLpersonLoss.setKindCode(ConstantCodes.KINDCODE_D_BZ);
						} else if (ConstantCodes.KINDCODE_D_BZ.equals(prpLpersonTemp.getKindCode())) {
							prpLpersonLoss.setKindCode(defaultKindCode);
						} else {
							prpLpersonLoss.setKindCode(prpLpersonTemp.getKindCode() == null ? "" : prpLpersonTemp.getKindCode().trim());
						}
						prpLpersonLoss.setKindName(this.getCodeService().translateKindCode(prpLpersonTemp.getRiskCode(), prpLpersonLoss.getKindCode(), true));
						prpLpersonLoss.setJobCode(prpLpersonTemp.getJobCode());
						prpLpersonLoss.setJobName(prpLpersonTemp.getJobName());
						prpLpersonLoss.setCurrency3(prpLpersonTemp.getCurrency());
						prpLpersonLoss.setCurrency3Name(this.getCodeService().translateCurrencyCode(prpLpersonTemp.getCurrency(), true));
						prpLpersonLoss.setIndemnityDutyRate(prpLclaim.getIndemnityDutyRate());
						// 协商赔偿比例默认为100%
						prpLpersonLoss.setArrangeRate(100);
						prpLpersonLoss.setDeductiblerate(prpLloss.getDeductiblerate());
						prpLpersonLoss.setDutyDeductibleRate(prpLloss.getDutyDeductibleRate());
						// 赔付合计
						prpLpersonLoss.setSumRealPay1(0);
						prpLpersonLoss.setSumRealPay(prpLpersonTemp.getSumDefLoss());
						if ("RISKCODE_DAZ".equals(configCode) && isCompelFlag) {
							prpLpersonLoss.setLiabDetailCode("");
							prpLpersonLoss.setLiabDetailName("");
						} else {
							prpLpersonLoss.setLiabDetailCode(prpLpersonTemp.getFeeTypeCode());
							prpLpersonLoss.setLiabDetailName(prpLpersonTemp.getFeeTypeName());
						}
						prpLpersonLoss.setUnitAmount(prpLpersonTemp.getUnitLoss());
						prpLpersonLoss.setLossQuantity((int) prpLpersonTemp.getQuantity());
						prpLpersonLoss.setSumLoss(prpLpersonTemp.getSumDefLoss());
						prpLpersonLoss.setSumDefPay(prpLpersonTemp.getSumDefLoss());
						prpLpersonLoss.setClaimRate(prpLloss.getClaimRate());

						// 赔付标的信息中赔付金额值不对 ；
						double realPay = 0.00;
						realPay = (prpLpersonLoss.getSumLoss() - prpLpersonLoss.getSumRest()) * (prpLpersonLoss.getClaimRate() * 0.01) * (prpLpersonLoss.getIndemnityDutyRate() * 0.01)
								* (1 - (prpLpersonLoss.getDutyDeductibleRate() * 0.01 + prpLpersonLoss.getDeductiblerate() * 0.01));
						prpLpersonLoss.setSumRealPay(Number2(realPay));
						personLossListTemp.add(prpLpersonLoss);
					} else {
						prpLpersonLoss.setPersonNo(prpLpersonTemp.getId().getPersonNo());
						prpLpersonLoss.setOverAmount("");
						prpLpersonLoss.getId().setSerialNo(serialNo);
						prpLpersonLoss.setRiskCode(prpLpersonTemp.getRiskCode());
						prpLpersonLoss.setPersonName(prpLpersonTemp.getPersonName());
						prpLpersonLoss.setSex(prpLpersonTemp.getPersonSex());
						prpLpersonLoss.setAge(prpLpersonTemp.getPersonAge());
						prpLpersonLoss.setSumRealPay(0);
						prpLpersonLoss.setItemKindNo(prpLpersonTemp.getItemKindNo());
						prpLpersonLoss.setLiabCode(prpCitemKindTemp.getItemCode());
						prpLpersonLoss.setLiabName(prpCitemKindTemp.getItemDetailName());
						if ("RISKCODE_DAZ".equals(configCode) && BusinessRuleUtil.checkKindType("ThirdPersonLoss", prpLpersonTemp.getKindCode())) {
							prpLpersonLoss.setKindCode(ConstantCodes.KINDCODE_D_BZ);
						} else if (ConstantCodes.KINDCODE_D_BZ.equals(prpLpersonTemp.getKindCode())) {
							prpLpersonLoss.setKindCode(defaultKindCode);
						} else {
							prpLpersonLoss.setKindCode(prpLpersonTemp.getKindCode() == null ? "" : prpLpersonTemp.getKindCode().trim());
						}
						prpLpersonLoss.setKindName(this.getCodeService().translateKindCode(prpLpersonTemp.getRiskCode(), prpLpersonLoss.getKindCode(), true));
						prpLpersonLoss.setKindCode(prpLpersonTemp.getKindCode() == null ? "" : prpLpersonTemp.getKindCode().trim());
						prpLpersonLoss.setKindName(this.getCodeService().translateKindCode(prpLpersonTemp.getRiskCode(), prpLpersonTemp.getKindCode(), true));
						if ("RISKCODE_DAZ".equals(configCode) && isCompelFlag) {
							prpLpersonLoss.setLiabDetailCode("");
							prpLpersonLoss.setLiabDetailName("");
						} else {
							prpLpersonLoss.setLiabDetailCode(prpLpersonTemp.getFeeTypeCode());
							prpLpersonLoss.setLiabDetailName(prpLpersonTemp.getFeeTypeName());
						}
						prpLpersonLoss.setCurrency2(prpLpersonTemp.getCurrency());
						prpLpersonLoss.setCurrency2Name(this.getCodeService().translateCurrencyCode(prpLpersonTemp.getCurrency(), true));
						prpLpersonLoss.setSumLoss(prpLpersonTemp.getSumDefLoss());
						prpLpersonLoss.setAmount(prpCitemKindTemp.getAmount());
						prpLpersonLoss.setClaimRate(prpLpersonTemp.getLossRate());
						prpLpersonLoss.setDeductible(0);
						prpLpersonLoss.setCurrency(prpLpersonTemp.getCurrency());
						prpLpersonLoss.setCurrency1(prpLpersonTemp.getCurrency());
						prpLpersonLoss.setCurrency3(prpLpersonTemp.getCurrency());
						prpLpersonLoss.setCurrency4(prpLpersonTemp.getCurrency());
						// 赔付标的信息中赔付金额值不对 ；
						double realPay = 0.00;
						realPay = (prpLpersonLoss.getSumLoss() - prpLpersonLoss.getSumRest()) * (prpLpersonLoss.getClaimRate() * 0.01) * (prpLpersonLoss.getIndemnityDutyRate() * 0.01)
								* (1 - (prpLpersonLoss.getDutyDeductibleRate() * 0.01 + prpLpersonLoss.getDeductiblerate() * 0.01));
						prpLpersonLoss.setSumRealPay(realPay);
						// 赔付标的信息中赔付金额值不对 ；
						personLossListTemp.add(prpLpersonLoss);
					}
				}
				for(PrpLpersonLoss personLoss : personLossListTemp){
					tempKey = personLoss.getKindCode();
					if(personLossMap.containsKey(tempKey)){
						personLoss.setSumLoss(personLossMap.get(tempKey));
						personLoss.setSumDefPay(personLossMap.get(tempKey));
					}
					//本次已有賠付記錄的估損險別，不需要重新帶出
					if (claimLossMap.containsKey(tempKey)) {
						claimLossMap.remove(tempKey);
					}
				}
			}
		}
		Map<String , List<PrpLpersonLoss>> allPerson = new LinkedHashMap<String , List<PrpLpersonLoss>>();
		List<PrpLpersonLoss> allPersonLoss = null ;
		Map<String , String > tempNo = new HashMap<String , String >();//存新序人傷序號
		String personNo = null;
		for(PrpLpersonLoss tempPrpLpersonLoss : personLossListTemp){
			personNo = tempPrpLpersonLoss.getPersonNo() + "";
			if(tempNo.containsKey(personNo)){
				allPersonLoss = allPerson.get(personNo);
			} else {//
				allPersonLoss = new ArrayList<PrpLpersonLoss>();
				tempNo.put(personNo, String.valueOf(tempNo.size() + 1));
			}
			allPersonLoss.add(tempPrpLpersonLoss);
			allPerson.put(personNo, allPersonLoss);
		}
		tempNo.clear();
		//保留處理預估的人傷
		if (!mapPrpLpersonLoss.isEmpty()) {
			PrpLpersonLoss personLoss = null;
			for(Map.Entry<String , PrpLpersonLoss> entry : mapPrpLpersonLoss.entrySet()){
				personLoss = entry.getValue();
				if("Y".equals(personLoss.getReservedEstimate())){//該筆賠付有保留預估
					personLoss.setSumLoss(0d);
					Double claimLossValue = lossMap.get(personLoss.getKindCode());//預估金額
					Double hasPayValue = payMap.get(personLoss.getKindCode());
					if(claimLossValue != null){//有預估
						if(hasPayValue==null){
							hasPayValue = 0d;
						}
						Double sumLoss = claimLossValue - hasPayValue;
						if(sumLoss >= 0){//有預估，有保留且，保留預估 大於 0 
							personLoss.setSumLoss(sumLoss);
							personLoss.setSumDefPay(sumLoss);
							personLoss.setPayObjectSerialNo("");
							personLoss.setSumRealPay(0);
							personNo = null;
							if(tempNo.containsKey(personLoss.getIdentifyNumber())){//身份證號碼代表同一個受害人
								personNo = tempNo.get(personLoss.getIdentifyNumber());
								allPersonLoss = allPerson.get(personNo);
							} else {
								allPersonLoss  = new ArrayList<PrpLpersonLoss>();
								personNo = String.valueOf(allPerson.size() + 1);
								tempNo.put(personLoss.getIdentifyNumber(), personNo);
							}
							allPersonLoss.add(personLoss);
							allPerson.put(personNo, allPersonLoss);
						}
					}
				}
			}
		}
		//如果該險別有人傷賠付的估損，但沒有過賠付
		if(!claimLossMap.isEmpty()){
			allPersonLoss = new ArrayList<PrpLpersonLoss>();
			PrpLpersonLoss tempPrpLpersonLoss = null;
			for(Entry<String, PrpLclaimLoss> entry : claimLossMap.entrySet()){
				PrpLclaimLoss tempPrpLclaimLoss = entry.getValue();
				String kindCode = tempPrpLclaimLoss.getKindCode();
				prpCitemKindTemp = kindMap.get(kindCode);
				// 获得事故责任免赔率
				dblDutyDeductibleRate = uiDeductCondAction.getDeductibleRateOfAccident(tempPrpLclaimLoss.getRiskCode(), kindCode, prpLclaim.getIndemnityDuty(), "0", prpCitemCarList.get(0).getClauseType(), new DateTime(prpCmain.getStartDate()).toString());
				tempPrpLpersonLoss = new PrpLpersonLoss();
				tempPrpLpersonLoss.setRiskCode(tempPrpLclaimLoss.getRiskCode());
				tempPrpLpersonLoss.setItemKindNo(prpCitemKindTemp.getId().getItemKindNo());
				tempPrpLpersonLoss.setAmount(prpCitemKindTemp.getAmount());
				tempPrpLpersonLoss.setDeductible(prpCitemKindTemp.getDeductible());
				tempPrpLpersonLoss.setExceptDeductibleRate(0);
				tempPrpLpersonLoss.setExceptDeductiblePay(0);
				tempPrpLpersonLoss.setFlag(flag);
				tempPrpLpersonLoss.setKindCode(kindCode);
				tempPrpLpersonLoss.setKindName(this.getCodeService().translateKindCode(tempPrpLclaimLoss.getRiskCode(), tempPrpLpersonLoss.getKindCode(), true));
				tempPrpLpersonLoss.setCurrency3(prpLclaim.getCurrency());
				tempPrpLpersonLoss.setCurrency3Name(this.getCodeService().translateCurrencyCode(prpLclaim.getCurrency(), true));
				tempPrpLpersonLoss.setIndemnityDutyRate(prpLclaim.getIndemnityDutyRate());
				// 协商赔偿比例默认为100%
				tempPrpLpersonLoss.setArrangeRate(100);
				tempPrpLpersonLoss.setDeductiblerate(0);
				tempPrpLpersonLoss.setDutyDeductibleRate(0);
				// 赔付合计
				tempPrpLpersonLoss.setSumRealPay1(0);
				tempPrpLpersonLoss.setUnitAmount(0);
				tempPrpLpersonLoss.setLossQuantity(0);
				tempPrpLpersonLoss.setSumLoss(tempPrpLclaimLoss.getSumClaim());
				tempPrpLpersonLoss.setSumDefPay(tempPrpLclaimLoss.getSumClaim());
				tempPrpLpersonLoss.setClaimRate(0);
				tempPrpLpersonLoss.setSumRealPay(tempPrpLclaimLoss.getSumClaim());
				allPersonLoss.add(tempPrpLpersonLoss);
			}
			if(allPersonLoss.size() > 0){
				personNo = String.valueOf(allPerson.size() + 1);
				allPerson.put(personNo, allPersonLoss);
			}
		}
		List<PrpLpersonLoss> last = new ArrayList<PrpLpersonLoss>();
		serialNo = 0;
		for(Entry<String, List<PrpLpersonLoss>> entry : allPerson.entrySet()){
			personNo = entry.getKey();
			allPersonLoss = entry.getValue();
			//整理每個受害人序號
			for(PrpLpersonLoss tempPrpLpersonLoss : allPersonLoss){
				tempPrpLpersonLoss.setPersonNo(Integer.parseInt(personNo));
				tempPrpLpersonLoss.getId().setSerialNo(++serialNo);
				last.add(tempPrpLpersonLoss);
			}
		}
		return last;
	}

	/**
	 * STUB-ONLY 计算实赔金额
	 * @param PrpLloss prpLlossDto
	 * @throws UserException
	 * @throws Exception
	 * @throws prpLlossSchema
	 */
	public PrpLloss calculateSumLoss(PrpLloss prpLloss) throws Exception {

		double dblSumLoss = 0;
		double dblSumRest = 0;
		double dblDeductible = 0;
		double dblDeductRate = 0;
		double dblDeductible1 = 0;
		double dblSumRealPay = 0;
		double dblRate = 0;// 是兑换率吗
		double dblIndemnityDutyRate = 0;
		String strRiskCode = "";
		double dblDutyDeducRate = 0;
		double arrangeRate = 0;// 协商比例
		// String flag = "";// 不计免赔标记
		PrpCitemKind prpCitemKind = new PrpCitemKind();
		// String strCond = "ClaimNo = '" + prpLloss.getId().getCompensateNo() +
		// "'";// 做什么用的？
		ClaimDto claimDto = this.getClaimService().findByPrimaryKey(prpLloss.getId().getCompensateNo());
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
//		PolicyDto policyDto = this.getEndorseViewHelper().findForEndorBefore(prpLloss.getPolicyNo(), new DateTime(prpLclaim.getDamageStartDate()).toString(), prpLclaim.getDamageStartHour());
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		List<PrpCitemKind> prpCitemKindList = this.getEndorseViewHelper().findPrpCitemKind(policyNo, damageDate, damageHour , prpLclaim.getRiskCode() , null);
		for (int i = 0; i < prpCitemKindList.size(); i++) {
			prpCitemKind = prpCitemKindList.get(i);
			if (prpCitemKind.getKindCode().equals(prpLloss.getKindCode())) {
				break;
			}
		}
		List<PrpCitemCar> prpCitemCarList = this.getEndorseViewHelper().findPrpCitemCar(policyNo, damageDate, damageHour);
		PrpCitemCar prpCitemCar = prpCitemCarList.get(0);
		dblSumLoss = prpLloss.getSumLoss();
		dblSumRest = prpLloss.getSumRest();
		dblDeductible = prpLloss.getDeductible();
		dblDeductRate = prpLloss.getDeductiblerate() / 100;
		dblIndemnityDutyRate = prpLloss.getIndemnityDutyRate();
		strRiskCode = prpLloss.getRiskCode();
		dblDutyDeducRate = prpLloss.getDutyDeductibleRate() / 100;
		// flag = prpLloss.getFlag();
		arrangeRate = prpLloss.getArrangeRate() / 100;
		// 根据所注释程序逻辑可推出如下公式
		if (!strRiskCode.equals(this.getCodeService().translateProductCode("RISKCODE_DAA"))) {
			dblSumRealPay = (dblSumLoss - dblSumRest) * (prpLloss.getClaimRate()) / 100;
		}
		if (DataUtils.dbNullToEmpty(prpLloss.getCurrency2()).equals(DataUtils.dbNullToEmpty(prpLloss.getCurrency4()))) {
			dblRate = 1;
		}
		// 应该用损失扣减交强险後的值与限额进行比较
		if (dblSumLoss > prpLloss.getAmount() && !prpLclaim.getEscapeFlag().equals("5N")) {
			// 非逃逸案
			dblSumLoss = prpLloss.getAmount();
		}
		double dblSumLossRest = prpLloss.getSumLoss() - prpLloss.getSumRest();
		String strRiskType = this.getCodeService().translateRiskCodetoRiskType(strRiskCode);
		if ("D".equals(strRiskType)) {
			// 机动车辆损失险
			if (BusinessRuleUtil.checkKindType("MainCarLoss", prpLloss.getKindCode())) {
				if (prpLloss.getAmount() == prpCitemCar.getPurchasePrice()) {

					prpLloss.setClaimRate(100);
					prpLloss.setSumRealPay(dblSumLossRest * dblIndemnityDutyRate / 100 * arrangeRate * prpLloss.getClaimRate() / 100 * (1 - (dblDeductRate + dblDutyDeducRate)));
					if (prpLloss.getSumRealPay() > prpLloss.getItemValue()) {
						prpLloss.setSumRealPay(prpLloss.getItemValue());
					}
				} else {
					// 按投保时保险车辆的实际价值确定保险金额或协商确定保额
					if (prpLloss.getAmount() < prpCitemCar.getPurchasePrice()) {
						if (prpLclaim.getEscapeFlag().length() > 1 && prpLclaim.getEscapeFlag().substring(1, 2).equals("Y") && strRiskCode.equals(this.getCodeService().translateProductCode("RISKCODE_DAA"))) {
							prpLloss.setClaimRate(100);
						} else {
							prpLloss.setClaimRate(prpLloss.getAmount() * 100 / prpCitemCar.getPurchasePrice());
						}
					}
					prpLloss.setSumRealPay(dblSumLossRest * arrangeRate * dblIndemnityDutyRate / 100 * prpLloss.getClaimRate() / 100 * (1 - (dblDeductRate + dblDutyDeducRate)));
					if (prpLloss.getSumRealPay() > prpLloss.getItemValue()) {
						prpLloss.setSumRealPay(prpLloss.getItemValue());
					}
				}
			}
			// 机动车辆第三者责任险
			if (BusinessRuleUtil.checkKindType("ThirdCarLoss", prpLloss.getKindCode()) || BusinessRuleUtil.checkKindType("ThirdPropLoss", prpLloss.getKindCode())) {
				// dblDeductRate绝对免赔率
				// DutyDeductibleRate责任免赔率
				prpLloss.setClaimRate(100);
				if (prpLloss.getSumLoss() * dblIndemnityDutyRate / 100 > prpLloss.getAmount()) {
					// 赔款=赔偿限额×（1-（事故责任免赔率+绝对免赔率））
					prpLloss.setSumRealPay(prpLloss.getAmount() * arrangeRate * dblIndemnityDutyRate / 100 * prpLloss.getClaimRate() / 100 * (1 - (dblDeductRate + dblDutyDeducRate)));

				} else {
					// 赔款=应付赔偿金额(乘责任比例之後的)×（1-事故责任免赔率）×（1-绝对免赔率）
					prpLloss.setSumRealPay(prpLloss.getSumLoss() * dblIndemnityDutyRate / 100 * (1 - (dblDeductRate + dblDutyDeducRate)));
				}
			}
			// 全车盗抢险，计算方式按附加盗抢方式计算
			if (prpLloss.getKindCode().equals("C")) {
				if (prpLclaim.getEscapeFlag().length() > 1 && prpLclaim.getEscapeFlag().substring(1, 2).equals("Y") && strRiskCode.equals(this.getCodeService().translateProductCode("RISKCODE_DAA"))) {
					prpLloss.setSumRealPay(prpLloss.getAmount() * (1 - dblDeductRate));
				} else {
					prpLloss.setSumRealPay(prpLloss.getSumLoss() - prpLloss.getSumRest());
				}
				if (prpLloss.getSumRealPay() > prpLloss.getAmount()) {
					prpLloss.setSumRealPay(prpLloss.getAmount());
				}
				prpLloss.setClaimRate(100);
				prpLloss.setIndemnityDutyRate(100);
				prpLloss.setArrangeRate(100);
			}// end C
			// 盗抢险
			if (prpLloss.getKindCode().equals("G")) {
				if (prpLclaim.getEscapeFlag().length() > 1 // 全损
						&& prpLclaim.getEscapeFlag().substring(1, 2).equals("Y") && strRiskCode.equals(this.getCodeService().translateProductCode("RISKCODE_DAA"))) {
					// 盗抢全车损增加20%的免赔
					prpLloss.setSumRealPay(prpLloss.getAmount() * (1 - (dblDeductRate + dblDutyDeducRate + 0.2)));
				} else {
					prpLloss.setSumRealPay((prpLloss.getSumLoss() - prpLloss.getSumRest()) * (1 - (dblDeductRate + dblDutyDeducRate)));
					prpLloss.setDeductible(0);
				}
				if (prpLloss.getSumRealPay() > prpLloss.getAmount()) {
					prpLloss.setSumRealPay(prpLloss.getAmount());
				}
				prpLloss.setClaimRate(100);
				prpLloss.setIndemnityDutyRate(100);
				prpLloss.setArrangeRate(100);
			}// end G
			// 玻璃单独破碎险
			if (prpLloss.getKindCode().equals("F")) {
				prpLloss.setSumRealPay(prpLloss.getSumLoss());
				prpLloss.setClaimRate(100);
				prpLloss.setIndemnityDutyRate(100);
				prpLloss.setArrangeRate(100);
			}// end F
			// 火灾、爆炸、自燃损失险
			if (prpLloss.getKindCode().equals("E")) {
				prpLloss.setSumRealPay(dblSumLossRest * (1 - dblDeductRate));
				if (prpLloss.getSumRealPay() > prpLloss.getAmount()) {
					prpLloss.setSumRealPay(prpLloss.getAmount());
				}
				prpLloss.setClaimRate(100);
				prpLloss.setIndemnityDutyRate(100);
				prpLloss.setArrangeRate(100);
			}// end E
			// 自燃损失险
			if (prpLloss.getKindCode().equals("Z")) {
				prpLloss.setSumRealPay(dblSumLossRest * (1 - dblDeductRate));
				if (prpLloss.getSumRealPay() > prpLloss.getAmount()) {
					prpLloss.setSumRealPay(prpLloss.getAmount());
				}
				prpLloss.setClaimRate(100);
				prpLloss.setIndemnityDutyRate(100);
				prpLloss.setArrangeRate(100);
			}// end Z
			// 车身划痕损失险 冲减保额
			if (prpLloss.getKindCode().equals("L")) {
				prpLloss.setSumRealPay(prpLloss.getSumLoss());
				if ((prpLloss.getSumRealPay() * 0.85) > prpLloss.getAmount()) {
					prpLloss.setSumRealPay(0);
				} else {
					// 每次赔偿实行15%的免赔
					prpLloss.setSumRealPay(prpLloss.getSumLoss() * 0.85);
				}
				prpLloss.setClaimRate(100);
				prpLloss.setIndemnityDutyRate(100);
				prpLloss.setArrangeRate(100);
			}// end L
			// 车辆停驶损失险 冲减最高赔偿天数
			if (prpLloss.getKindCode().equals("T")) {
				if (prpLloss.getLossQuantity() > prpCitemKind.getQuantity()) {
					prpLloss.setSumRealPay(prpCitemKind.getUnitAmount() * prpCitemKind.getQuantity());
				} else {
					prpLloss.setSumRealPay(prpCitemKind.getUnitAmount() * prpLloss.getLossQuantity());
				}
				prpLloss.setClaimRate(100);
				prpLloss.setIndemnityDutyRate(100);
				prpLloss.setArrangeRate(100);
			}// end T
			// 车载货物掉落责任险
			if (prpLloss.getKindCode().equals("H")) {
				if (prpLloss.getSumLoss() <= prpLloss.getAmount()) {
					prpLloss.setSumRealPay(prpLloss.getSumLoss() * (1 - dblDeductRate));
				} else {
					prpLloss.setSumRealPay(prpLloss.getAmount() * (1 - dblDeductRate));
				}
				prpLloss.setClaimRate(100);
			}// end H
			// 新增加设备损失险
			if (prpLloss.getKindCode().equals("X")) {
				// 2007版A款 */
				if (prpLloss.getSumLoss() <= prpLloss.getAmount()) {
					prpLloss.setSumRealPay((prpLloss.getSumLoss() - prpLloss.getSumRest()) * arrangeRate * dblIndemnityDutyRate / 100 * prpLloss.getClaimRate() / 100 * (1 - (dblDutyDeducRate + dblDeductRate)));
				} else {
					prpLloss.setSumRealPay((prpLloss.getAmount() - prpLloss.getSumRest()) * arrangeRate * dblIndemnityDutyRate / 100 * prpLloss.getClaimRate() / 100 * (1 - (dblDutyDeducRate + dblDeductRate)));
				}
			}// end X
			// 车上人员责任险
			// modify reason : 在人伤处进行计算

			// 车上货物责任险 //2000版和2003版的免赔率不同
			if (prpLloss.getKindCode().equals("D2")) {
				prpLloss.setSumRealPay(prpLloss.getSumLoss() * dblIndemnityDutyRate / 100 * (1 - dblDeductRate));
				if (prpLloss.getSumRealPay() > prpLloss.getAmount()) {
					prpLloss.setSumRealPay(prpLloss.getAmount() * (1 - dblDeductRate));
				}
			}// end D2
			prpLloss.setClaimRate(100);

			// 无过失责任险
			if (prpLloss.getKindCode().equals("W")) {
				if (prpLloss.getSumLoss() <= prpLloss.getAmount()) {
					prpLloss.setSumRealPay(prpLloss.getSumLoss() * (1 - dblDeductRate));
				} else {
					prpLloss.setSumRealPay(prpLloss.getAmount() * (1 - dblDeductRate));
				}
				prpLloss.setClaimRate(100);
			}// end W

			// 救助险计算赔付金额
			if (prpLloss.getKindCode().equals("J")) {
				prpLloss.setSumRealPay(prpLloss.getSumLoss());
				prpLloss.setDeductible(0);
			}// end J
			// 车险全损
			if (prpLclaim.getEscapeFlag() != null && prpLclaim.getEscapeFlag().length() > 1) {
				if (prpLclaim.getEscapeFlag().substring(1, 2).equals("Y")) {
				}
			}

			// 不计免赔特约条款
			if (prpLloss.getKindCode().equals("M")) {
			}
		}// end D车险
		else {
			dblDeductible1 = prpLloss.getSumLoss() * prpLloss.getDeductiblerate() / 100;
			if (dblDeductible > dblDeductible1) {
				prpLloss.setSumRealPay((dblSumRealPay * dblRate - dblDeductible));
			} else {
				prpLloss.setSumRealPay((dblSumRealPay * dblRate - dblDeductible1));
			}
		}
		if (prpLloss.getSumRealPay() < 0) {
			prpLloss.setSumRealPay(0);
		}
		return prpLloss;
	}

	/***
	 * 初始化理算车物损赔付信息 （客制化）
	 * @param httpServletRequest
	 * @param claimNo
	 * @param claimDto
	 * @return
	 * @throws Exception
	 */
	public List<PrpLloss> initLossItem(HttpServletRequest httpServletRequest, ClaimDto claimDto) throws Exception {
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		String configCode = this.getCodeService().translateRiskCodetoConfigCode(prpLclaim.getRiskCode());
		List<PrpLloss> prpLlossList = new ArrayList<PrpLloss>();
		if (!"RISKCODE_DAZ".equals(configCode)) {// 强制险无车物损赔付
			String conditions = "  ClaimNo = '"+prpLclaim.getClaimNo()+"' and (UnderWriteFlag = '1' or UnderWriteFlag = '3') and mutualCompensateNo is null order by inputDate desc";
			List<PrpLcompensate> compensateDtoList = this.compensateService.findByConditions(conditions);
			String registNo = prpLclaim.getRegistNo();
			Date inputDate = null;
			conditions = "";
			if (!CommonUtils.isEmpty(compensateDtoList)) {
				String compeNo = null;
				for(PrpLcompensate prpLcompensate : compensateDtoList){
					compeNo = prpLcompensate.getCompensateNo();
					if(compeNo.charAt(0)=='C' || compeNo.charAt(0)=='R' ){
						if(conditions.length() > 0){
							conditions += " or ";
						}
						conditions += "compensateNo = '"+ compeNo +"'";
						if(compeNo.charAt(0)=='C'){
							inputDate = prpLcompensate.getInputDate();
						}
					}
				}
			}
			/* 需求变更#83第二次调整  理算有估損調整，以估損調整為主 */
			//獲取理算之前的估損金額調整。
			List<PrpLclaimLoss> claimLoss = claimDto.getPrpLclaimLossList();
			Map<String, Double> propLossMap = new HashMap<String, Double>();//有調整的險別之估損金額
			String tempKey = null;
			String tempFeeCategory = null;
			Map<String, Double> lossMap = new HashMap<String, Double>();//車物損賠付險別估損金額
			Map<String, PrpLclaimLoss> claimLossMap = new HashMap<String, PrpLclaimLoss>();
			if (claimLoss != null && !claimLoss.isEmpty()) {//存在估損
				for (PrpLclaimLoss loss : claimLoss) {
					tempFeeCategory = loss.getFeeCategory();
					if ("P".equals(loss.getLossFeeType()) ) {
						tempKey = loss.getKindCode();
						//範圍是車物損
						boolean carAndprop = "C".equals(tempFeeCategory) || "G".equals(tempFeeCategory);
						//範圍是體傷、失能、死亡
						boolean person = "M".equals(tempFeeCategory) || "H".equals(tempFeeCategory) || "D".equals(tempFeeCategory);
						//可賠付車損
						boolean KindCodeForProp = ConstantsCollection.KindCodeForProp.contains(tempKey);
						//可賠付物損
						boolean KindCodeForCar = ConstantsCollection.KindCodeForCar.contains(tempKey);
						//可賠付人傷
						boolean KindCodeForPerson = ConstantsCollection.KindCodeForPerson.contains(tempKey);
						if ( (!person && (KindCodeForCar || KindCodeForProp))
								|| ( person && (KindCodeForCar || KindCodeForProp) && !KindCodeForPerson)) {
							//範圍是非人傷賠付且可賠付車物損的，範圍是人傷但能賠付車物損且不能賠付人傷的（WEB端沒控制這裡處理）
							//tempKey = loss.getKindCode() + "_" + tempFeeCategory;
							//只區分車物損和人傷，不區分車損、無損 user自行判斷
							if (lossMap.containsKey(tempKey)) {//險別估損合併
								lossMap.put(tempKey, loss.getSumClaim() + lossMap.get(tempKey));
								//加總同險別同賠付類型的預估金額
								PrpLclaimLoss temp = claimLossMap.get(tempKey);
								temp.setSumClaim(temp.getSumClaim() + loss.getSumClaim());
							} else {
								lossMap.put(tempKey, loss.getSumClaim());
								claimLossMap.put(tempKey, (PrpLclaimLoss) BeanUtils.cloneBean(loss));
							}
							if ("2".equals(loss.getDatafrom())) {// 本筆記錄有調整
								if (propLossMap.containsKey(tempKey)) {
									propLossMap.put(tempKey, loss.getSumClaim() + propLossMap.get(tempKey));
								} else {//取險別該類賠付的預估總額
									propLossMap.put(tempKey, lossMap.get(tempKey));
								}
							} else {//本次記錄無調整
								if (propLossMap.containsKey(tempKey)) {//但是險別之前有過調整，則統計調整之後的險別估損金額
									propLossMap.put(tempKey, loss.getSumClaim() + propLossMap.get(tempKey));
								}
							}
						}
					}
				}
			}
			//統計車物損賠付
			Map<String , PrpLloss> mapPrpLloss = new HashMap<String , PrpLloss>();
			Map<String,Double> payMap = new HashMap<String , Double>();//車物損賠付各險別已賠付
			if(!CommonUtils.isEmpty(conditions)){//有過賠付，屬二次賠付
				if(conditions.length() > 0){
					conditions = " ( "+ conditions +" ) order by compensateNo asc , serialno asc ";
					List<PrpLloss> tempLosslist = this.prpLlossService.findByConditions(conditions);
					tempKey = null;
					for (PrpLloss prpLloss : tempLosslist) {
						tempKey = prpLloss.getKindCode() + "_" + prpLloss.getLicenseNo() + "_" + prpLloss.getLossName();
						if(prpLloss.getId().getCompensateNo().charAt(0) == 'C'){
							//賠付只統計賠款，不統計追償
							mapPrpLloss.put(tempKey, prpLloss);// 險別，車牌，財務名稱一致，代表同一筆記錄
						}
						tempKey = prpLloss.getKindCode();
						if (prpLloss.getId().getCompensateNo().charAt(0) == 'R') {
							//已追償的案子統計各險別已追償
							if (payMap.containsKey(tempKey)) {
								payMap.put(tempKey, CommonUtils.round(prpLloss.getSumRealPay() * prpLloss.getExchRate(), 0) + payMap.get(tempKey));
							} else {
								if (lossMap.containsKey(tempKey)) {//有險別估損，才計入已追償的金額到險別已賠付
									payMap.put(tempKey, CommonUtils.round(prpLloss.getSumRealPay() * prpLloss.getExchRate(), 0));
								}
							}
						}
						if(claimLossMap.containsKey(tempKey)){//險別已有過賠付了，不再帶出估損
							claimLossMap.remove(tempKey);
						}
					}
				}
				//統計已賠付各個險別的賠付金額
				List<PrpLloss> allKindPayInfos = this.getCompensateService().getPrpLlossForReplevy(prpLclaim.getClaimNo());
				for(PrpLloss tempPrpLloss : allKindPayInfos){
					tempKey = tempPrpLloss.getKindCode();
					if (payMap.containsKey(tempKey)) {
						payMap.put(tempKey, tempPrpLloss.getSumLoss()+ payMap.get(tempKey));
					} else {
						if (lossMap.containsKey(tempKey)) {//有險別估損，才計入已追償的金額到險別已賠付
							payMap.put(tempKey, tempPrpLloss.getSumLoss());
						}
					}
				}
			}
			String policyNo = prpLclaim.getPolicyNo();
			String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
			String damageHour = prpLclaim.getDamageStartHour();
			PrpCmain prpCmain = this.getEndorseViewHelper().findPrpCmain(policyNo, damageDate , damageHour);
			List<PrpCitemKind> prpCitemKindList = this.getEndorseViewHelper().findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
			List<PrpCitemCar> prpCitemCarList = this.getEndorseViewHelper().findPrpCitemCar(policyNo, damageDate, damageHour);
			
			Map<String, PrpCitemKind> kindMap = new HashMap<String, PrpCitemKind>();
			if (prpCitemKindList != null && !prpCitemKindList.isEmpty()) {
				for (PrpCitemKind p : prpCitemKindList) {
					kindMap.put(p.getKindCode(), p);
				}
			}
			PrpCitemCar prpCitemCar = new PrpCitemCar();// 标的车
			if (prpCitemCarList != null && !prpCitemCarList.isEmpty()) {
				prpCitemCar = prpCitemCarList.get(0);
			}
			Map<String, String> currencyMap = new HashMap<String, String>();
			CertainLossDto certainLossDto = this.getCertainLossService().findByUnderWriteEndDate(registNo, inputDate);
			PrpLloss prpLlossDto = null;
			PrpCitemKind prpCitemKind = null;
			int serialNo = 0;
			String currency = "";
			String currencyName = "";
			UIDeductCondAction uiDeductCondAction = UIDeductCondAction.getInstance();
			if (!CommonUtils.isEmpty(certainLossDto.getPrpLverifyLossList())) {// 初次出计算书
				Map<String, Double> carLossMap = new HashMap<String, Double>();
				Map<String, PrpLcarLoss> prpLcarLossMap = new HashMap<String, PrpLcarLoss>();
				// 计算每个车损的换件合计
				List<PrpLcarLoss> prpLcarLossList = certainLossDto.getPrpLcarLossList();
				if (prpLcarLossList != null && !prpLcarLossList.isEmpty()) {
					for (PrpLcarLoss prpLcarLoss : prpLcarLossList) {
						prpLcarLossMap.put(prpLcarLoss.getLossItemName(), prpLcarLoss);
					}
				}
				String lossKey = "";
				double lossValue = 0d;
				// 汇总零配件更换费用
				List<PrpLcomponent> prpLcomponentList = certainLossDto.getPrpLcomponentList();
				if (prpLcomponentList != null && !prpLcomponentList.isEmpty()) {
					// 需要控制每个零配件更换信息险别一致
					PrpLcarLoss prpLcarLoss = null;
					for (PrpLcomponent prpLcomponent : prpLcomponentList) {
						prpLcarLoss = prpLcarLossMap.get(prpLcomponent.getLicenseNo());
						lossKey = "02," + prpLcomponent.getKindCode() + "," + prpLcomponent.getLicenseNo();
						lossValue = prpLcomponent.getSumVeriLoss();
						lossValue += lossValue * (prpLcarLoss.getSumManager() / 100);// 加上管理费
						if ("0".equals(prpLcomponent.getIfRemain())) {// 不回收的需要减掉残值部分
							lossValue -= (prpLcomponent.getRestFee() == null ? 0d : prpLcomponent.getRestFee());
						}
						if (carLossMap.containsKey(lossKey)) {
							lossValue += carLossMap.get(lossKey);
						}
						carLossMap.put(lossKey, lossValue);
					}
				}
				List<PrpLrepairFee> prpLrepairFeeList = certainLossDto.getPrpLrepairFeeList();
				// 按险种,车牌,标的序号汇总修理费和材料费用
				if (prpLrepairFeeList != null && !prpLrepairFeeList.isEmpty()) {
					for (PrpLrepairFee prpLrepairFee : prpLrepairFeeList) {
						lossKey = "01," + prpLrepairFee.getKindCode() + "," + prpLrepairFee.getLicenseNo();
						lossValue = prpLrepairFee.getVeriSumLoss();
						if (carLossMap.containsKey(lossKey)) {
							lossValue += carLossMap.get(lossKey);
						}
						carLossMap.put(lossKey, lossValue);
					}
				}
				if (prpLcarLossList != null && !prpLcarLossList.isEmpty()) {// 运费要摊到第一条
					for (PrpLcarLoss prpLcarLoss : prpLcarLossList) {
						for (PrpLcomponent prpLcomponent : prpLcomponentList) {
							if (prpLcarLoss.getId().getLossItemCode().equals(prpLcomponent.getId().getLossItemCode())) {
								lossKey = "02," + prpLcomponent.getKindCode() + "," + prpLcomponent.getLicenseNo();
								lossValue = carLossMap.get(lossKey) + prpLcarLoss.getSumTransFee();
								carLossMap.put(lossKey, lossValue);
								break;
							}
						}
					}
				}
				// 合并每车的换件费和修理费
				Map<String, Double> sumCarLossMap = new HashMap<String, Double>();
				for (Entry<String, Double> entry : carLossMap.entrySet()) {
					lossKey = entry.getKey().substring(3);//
					lossValue = entry.getValue();
					if (sumCarLossMap.containsKey(lossKey)) {
						lossValue += sumCarLossMap.get(lossKey);
					}
					sumCarLossMap.put(lossKey, lossValue);
				}
				// 转换财产损失为理算物损赔付
				List<PrpLprop> prpLpropList = certainLossDto.getPrpLpropList();
				if (prpLpropList != null && !prpLpropList.isEmpty()) {
					for (PrpLprop prpLpropDto : prpLpropList) {
						prpLlossDto = new PrpLloss();
						serialNo++;
						prpCitemKind = kindMap.get(prpLpropDto.getKindCode());
						prpLlossDto.getId().setSerialNo(serialNo);
						prpLlossDto.setItemKindNo(prpCitemKind.getId().getItemKindNo());
						prpLlossDto.setAmount(prpCitemKind.getAmount());
						prpLlossDto.setLicenseNo("無");
						prpLlossDto.setPolicyNo(prpCitemKind.getId().getPolicyNo());
						prpLlossDto.setRiskCode(prpLpropDto.getRiskCode());
						prpLlossDto.setKindCode(prpCitemKind.getKindCode());
						prpLlossDto.setKindName(prpCitemKind.getKindName());
						prpLlossDto.setFeeTypeCode(prpLpropDto.getFeeTypeCode());
						prpLlossDto.setFeeTypeName(prpLpropDto.getFeeTypeName());
						currency = prpLpropDto.getCurrency();
						prpLlossDto.setCurrency(currency);
						prpLlossDto.setCurrency1(currency);
						prpLlossDto.setCurrency2(currency);
						prpLlossDto.setCurrency3(currency);
						prpLlossDto.setCurrency4(currency);
						if (currencyMap.containsKey(currency)) {
							currencyName = currencyMap.get(currency);
						} else {
							currencyName = this.getCodeService().translateCurrencyCode(currency, true);
							currencyMap.put(currency, currencyName);
						}
						prpLlossDto.setCurrencyName(currencyName);
						prpLlossDto.setCurrency1Name(currencyName);
						prpLlossDto.setCurrency2Name(currencyName);
						prpLlossDto.setCurrency3Name(currencyName);
						prpLlossDto.setCurrency4Name(currencyName);
						prpLlossDto.setLossName(prpLpropDto.getLossItemName());
						prpLlossDto.setUnitPrice(prpLpropDto.getUnitPrice());
						prpLlossDto.setLossQuantity(prpLpropDto.getLossQuantity());
						prpLlossDto.setItemValue(prpCitemKind.getAmount());
						prpLlossDto.setSumLoss(prpLpropDto.getSumDefLoss());
						prpLlossDto.setSumRest(0);
						prpLlossDto.setIndemnityDutyRate(prpLclaim.getIndemnityDutyRate());
						prpLlossDto.setArrangeRate(100);
						prpLlossDto.setClaimRate(0);
						prpLlossDto.setDeductiblerate(prpCitemKind.getDeductibleRate());
						double dblDutyDeductibleRate = uiDeductCondAction.getDeductibleRateOfAccident(prpLpropDto.getRiskCode(), prpCitemKind.getKindCode(), prpLclaim.getIndemnityDuty(), "0", prpCitemCar.getClauseType(), new DateTime(prpCmain
								.getStartDate()).toString());
						prpLlossDto.setDutyDeductibleRate(dblDutyDeductibleRate);// 事故责任免赔率
						prpLlossDto.setDeductible(prpCitemKind.getDeductible());
						if (prpCitemKind.getFlag() != null && prpCitemKind.getFlag().length() > 4) {
							prpLlossDto.setFlag(prpCitemKind.getFlag().substring(4, 5).trim());
						} else {
							prpLlossDto.setFlag("0");
						}
						prpLlossDto.setExceptDeductiblePay(0);
						prpLlossDto.setExceptDeductibleRate(0);
						prpLlossDto.setCompelPay(0);
						prpLlossDto.setSumRealPay(0);
						prpLlossDto.setLossType(PrpLloss.LOSSTYPE_PROP);
						prpLlossList.add(prpLlossDto);
					}
				}
				if (!sumCarLossMap.isEmpty()) {
					String[] str = null;
					String kindCode = "";// 损失险别
					String licenseNo = "";// 车牌号码
					double sumLoss = 0d;// 核损金额
					for (Entry<String, Double> entry : sumCarLossMap.entrySet()) {
						str = entry.getKey().split(",");
						kindCode = str[0];
						licenseNo = str[1];
						sumLoss = entry.getValue();
						prpLlossDto = new PrpLloss();
						serialNo++;
						prpCitemKind = kindMap.get(kindCode);
						prpLlossDto.getId().setSerialNo(serialNo);
						prpLlossDto.setItemKindNo(prpCitemKind.getId().getItemKindNo());
						prpLlossDto.setAmount(prpCitemKind.getAmount());
						prpLlossDto.setLicenseNo(licenseNo);
						prpLlossDto.setPolicyNo(prpCitemKind.getId().getPolicyNo());
						prpLlossDto.setRiskCode(prpCitemKind.getRiskCode());
						prpLlossDto.setKindCode(prpCitemKind.getKindCode());
						prpLlossDto.setKindName(prpCitemKind.getKindName());
						prpLlossDto.setFeeTypeCode("01");
						prpLlossDto.setFeeTypeName("修理費");
						currency = prpLclaim.getCurrency();
						prpLlossDto.setCurrency(currency);
						prpLlossDto.setCurrency1(currency);
						prpLlossDto.setCurrency2(currency);
						prpLlossDto.setCurrency3(currency);
						prpLlossDto.setCurrency4(currency);
						if (currencyMap.containsKey(currency)) {
							currencyName = currencyMap.get(currency);
						} else {
							currencyName = this.getCodeService().translateCurrencyCode(currency, true);
							currencyMap.put(currency, currencyName);
						}
						prpLlossDto.setCurrencyName(currencyName);
						prpLlossDto.setCurrency1Name(currencyName);
						prpLlossDto.setCurrency2Name(currencyName);
						prpLlossDto.setCurrency3Name(currencyName);
						prpLlossDto.setCurrency4Name(currencyName);
						prpLlossDto.setLossName(prpCitemKind.getItemDetailName());
						prpLlossDto.setUnitPrice(prpCitemKind.getUnitAmount() == null ? 0 : prpCitemKind.getUnitAmount());
						prpLlossDto.setLossQuantity(prpCitemKind.getQuantity() == null ? 0 : prpCitemKind.getQuantity());
						prpLlossDto.setItemValue(prpCitemKind.getAmount());
						prpLlossDto.setSumLoss(sumLoss);
						prpLlossDto.setSumRest(0);
						prpLlossDto.setIndemnityDutyRate(prpLclaim.getIndemnityDutyRate());
						prpLlossDto.setArrangeRate(100);
						prpLlossDto.setClaimRate(0);
						prpLlossDto.setDeductiblerate(prpCitemKind.getDeductibleRate());
						double dblDutyDeductibleRate = uiDeductCondAction.getDeductibleRateOfAccident(prpCitemKind.getRiskCode(), prpCitemKind.getKindCode(), prpLclaim.getIndemnityDuty(), "0", prpCitemCar.getClauseType(), new DateTime(prpCmain
								.getStartDate()).toString());
						prpLlossDto.setDutyDeductibleRate(dblDutyDeductibleRate);// 事故责任免赔率
						prpLlossDto.setDeductible(prpCitemKind.getDeductible());
						if (prpCitemKind.getFlag() != null && prpCitemKind.getFlag().length() > 4) {
							prpLlossDto.setFlag(prpCitemKind.getFlag().substring(4, 5).trim());
						} else {
							prpLlossDto.setFlag("0");
						}
						prpLlossDto.setExceptDeductiblePay(0);
						prpLlossDto.setExceptDeductibleRate(0);
						prpLlossDto.setCompelPay(0);
						prpLlossDto.setSumRealPay(0);
						prpLlossDto.setLossType(PrpLloss.LOSSTYPE_CAR);
						prpLlossList.add(prpLlossDto);
					}
				}
				/*C:車損,G:物損 */
				for (PrpLloss prpLloss : prpLlossList) {
					//區分險別 , 不區分車物損。
//					tempKey = prpLloss.getKindCode() + "_";
//					//車牌為空，或者車牌是“無”
//					if (PrpLloss.LOSSTYPE_PROP.equals(prpLloss.getLossType())) {
//						tempKey += "G";//物損
//					} else if(PrpLloss.LOSSTYPE_CAR.equals(prpLloss.getLossType())){
//						tempKey += "C";//車損
//					}
					tempKey = prpLloss.getKindCode();
					if (propLossMap.containsKey(tempKey)) {//有估損調整，以調整后的金額為準
						prpLloss.setSumLoss(propLossMap.get(tempKey));
					}
					//險別存在定損的訊息，則移除
					if (claimLossMap.containsKey(tempKey)) {
						claimLossMap.remove(tempKey);
					}
				}
			}
			//如果該估損訊息沒有車物損賠付定損訊息，則生成一筆 （有預估沒有賠付的）
			if(!claimLossMap.isEmpty()){
				PrpLclaimLoss tempPrpLclaimLoss = null;
				for(Map.Entry<String, PrpLclaimLoss> entry : claimLossMap.entrySet()){
					tempPrpLclaimLoss = entry.getValue();
					String kindCode = tempPrpLclaimLoss.getKindCode();
					prpLlossDto = new PrpLloss();
					serialNo++;
					prpCitemKind = kindMap.get(kindCode);
					prpLlossDto.getId().setSerialNo(serialNo);
					prpLlossDto.setItemKindNo(prpCitemKind.getId().getItemKindNo());
					prpLlossDto.setAmount(prpCitemKind.getAmount());
					prpLlossDto.setLicenseNo("");
					prpLlossDto.setPolicyNo(prpCitemKind.getId().getPolicyNo());
					prpLlossDto.setRiskCode(prpCitemKind.getRiskCode());
					prpLlossDto.setKindCode(prpCitemKind.getKindCode());
					prpLlossDto.setKindName(prpCitemKind.getKindName());
					prpLlossDto.setFeeTypeCode("");
					prpLlossDto.setFeeTypeName("");
					currency = prpLclaim.getCurrency();
					prpLlossDto.setCurrency(currency);
					prpLlossDto.setCurrency1(currency);
					prpLlossDto.setCurrency2(currency);
					prpLlossDto.setCurrency3(currency);
					prpLlossDto.setCurrency4(currency);
					if (currencyMap.containsKey(currency)) {
						currencyName = currencyMap.get(currency);
					} else {
						currencyName = this.getCodeService().translateCurrencyCode(currency, true);
						currencyMap.put(currency, currencyName);
					}
					prpLlossDto.setCurrencyName(currencyName);
					prpLlossDto.setCurrency1Name(currencyName);
					prpLlossDto.setCurrency2Name(currencyName);
					prpLlossDto.setCurrency3Name(currencyName);
					prpLlossDto.setCurrency4Name(currencyName);
					prpLlossDto.setLossName(prpCitemKind.getItemDetailName());
					prpLlossDto.setUnitPrice(prpCitemKind.getUnitAmount() == null ? 0 : prpCitemKind.getUnitAmount());
					prpLlossDto.setLossQuantity(prpCitemKind.getQuantity() == null ? 0 : prpCitemKind.getQuantity());
					prpLlossDto.setItemValue(prpCitemKind.getAmount());
					prpLlossDto.setSumLoss(tempPrpLclaimLoss.getSumClaim());
					prpLlossDto.setSumRest(0);
					prpLlossDto.setIndemnityDutyRate(prpLclaim.getIndemnityDutyRate());
					prpLlossDto.setArrangeRate(100);
					prpLlossDto.setClaimRate(0);
					prpLlossDto.setDeductiblerate(prpCitemKind.getDeductibleRate());
					double dblDutyDeductibleRate = uiDeductCondAction.getDeductibleRateOfAccident(prpCitemKind.getRiskCode(), prpCitemKind.getKindCode(), prpLclaim.getIndemnityDuty(), "0", prpCitemCar.getClauseType(), new DateTime(prpCmain
							.getStartDate()).toString());
					prpLlossDto.setDutyDeductibleRate(dblDutyDeductibleRate);// 事故责任免赔率
					prpLlossDto.setDeductible(prpCitemKind.getDeductible());
					if (prpCitemKind.getFlag() != null && prpCitemKind.getFlag().length() > 4) {
						prpLlossDto.setFlag(prpCitemKind.getFlag().substring(4, 5).trim());
					} else {
						prpLlossDto.setFlag("0");
					}
					prpLlossDto.setExceptDeductiblePay(0);
					prpLlossDto.setExceptDeductibleRate(0);
					prpLlossDto.setCompelPay(0);
					prpLlossDto.setSumRealPay(0);
					prpLlossList.add(prpLlossDto);
				}
			}
			// 處理保留預估的
			if (!mapPrpLloss.isEmpty()) {
				PrpLloss prpLloss = null;
				for(Map.Entry<String , PrpLloss> entry : mapPrpLloss.entrySet()){
					prpLloss = entry.getValue();
					if("Y".equals(prpLloss.getReservedEstimate())){//該筆賠付有保留預估
						prpLloss.setSumLoss(0d);
						Double claimLossValue = lossMap.get(prpLloss.getKindCode());//險別之預估金額
						Double hasPayValue = payMap.get(prpLloss.getKindCode());//險別已賠付金額
						if(claimLossValue != null){//有預估
							if(hasPayValue==null){
								hasPayValue = 0d;
							}
							Double sumLoss = claimLossValue - hasPayValue;
							if(sumLoss >= 0){
								prpLloss.setSumLoss(sumLoss);
								prpLloss.setSumDefPay(sumLoss);
								prpLloss.setPayObjectSerialNo("");
								prpLloss.setSumRealPay(0);
								prpLlossList.add(prpLloss);
							}
						}
					}
				}
			}
		}
		return prpLlossList;
	}

	/**
	 * 出新的赔款计算数的时候初始化赔付标的的信息 || 未处理计算书初始化从定损带出的车、物损 信息 || CHENJIE 需要改造
	 * @throws Exception
	 */
	public List<PrpLloss> initLossItem(HttpServletRequest httpServletRequest, String claimNo, ClaimDto claimDto) throws Exception {
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		int claimRelateFlag = 0;// 关联报案 标志；
		{
			List<PrpLclaim> claimRelate = this.getPrpLclaimService().findByRegistNo(prpLclaim.getRegistNo());
			claimRelateFlag = claimRelate.size();
		}
		// 报案号码
		String registNo = this.getCodeService().translateBusinessCode(claimNo, false);
		CertainLossDto certainLossDto = this.getCertainLossService().findByPrimaryKey(registNo);
		// 定义变量部分
		String strRiskCode = prpLclaim.getRiskCode();
		String configCode = this.getCodeService().translateRiskCodetoConfigCode(strRiskCode);

		String strCode = "";
		// 定义对象
		PrpCitemCar prpCitemCar = null;
		PrpLloss prpLlossDto = null;
		PrpLprop prpLpropDto = null;
		PrpCitemKind prpCitemKind = null;

		Hashtable<String, Double> hashRepComFee = new Hashtable<String, Double>(); // 按币别汇总的修理费和换件费

		double strItemValue = 0;
		int serialNo = 0;
		double strIndemnityDutyRate = 0;
		// String strLicenseNo = "";
		String strRepComKindCode = "";
		String strRepComLicenseNo = "";
		int strRepComItemKindNo = 0;
		String[] arrKindCode = null; // 汇总险别
		String[] arrLossFeeType = null; // 01：修理费 02：材料费
		String[] arrLossFeeTypeName = null; // 01：修理费 02：材料费
		String[] arrItemKindNo = null; // 汇总标的序号
		String[] arrLicenseNo = null; // 汇总车牌号
		double[] arrSumDefFee = null; // 汇总金额
		double dbRepComSumDefFee = 0d;
		int intRepFeeCount = 0; // 按险别,车牌号汇总後的修理费
		int intComFeeCount = 0; // 按险别,车牌号汇总後的材料费
		int intPropCount = 0;// 赔付财产的记录数
		// int intRepairFeeCount = 0;
		// int intComponentCount = 0;
		int intItemKindCount = 0;
		int index = 0;
		int index1 = 0;
		String flag = "";// 不计免赔标志
		ArrayList<PrpLloss> prpLlossListTemp = new ArrayList<PrpLloss>();

		// 判断是否已出过计算书且核赔通过，如果是这样，再出计算书，不需要带出人伤定损信息-----------------|
		String compensateFlag = "";
		String conditions = "  claimNo = '" + claimNo + "'";
		List<PrpLcompensate> compensateDtoList = this.compensateService.findByConditions(conditions);
		if (compensateDtoList != null && !compensateDtoList.isEmpty()) { // 出过计算书
			for (PrpLcompensate prpLcompensate : compensateDtoList) {
				if (prpLcompensate.getUnderWriteFlag().equals("1") || prpLcompensate.getUnderWriteFlag().equals("3")) {
					compensateFlag = "1";
					break;
				} else {
					compensateFlag = "0";
					continue;
				}
			}
		} else { // 没出过计算书
			compensateFlag = "0";
		}
		if (!compensateFlag.equals("1")) {
			String comCode = prpLclaim.getComCode().substring(0, 2);
			PrpDriskConfig prpDriskConfig = this.getPrpDriskConfigService().findByPrimaryKey(comCode.substring(0, 2), prpLclaim.getRiskCode(), "dealFast_case");
			PrpLcheck prpLcheck = new PrpLcheck();
			if ((prpDriskConfig != null && "1".equals(prpDriskConfig.getConfigValue()))) {
				QueryRule queryRule = QueryRule.getInstance();
				queryRule.addEqual("id.registNo", prpLclaim.getRegistNo());
				List<PrpLcheck> collection = this.getPrpLcheckService().findPrpLcheck(queryRule);
				if (collection != null && !collection.isEmpty()) {
					prpLcheck = collection.get(0);
					httpServletRequest.setAttribute("dealFastFlag", prpLcheck.getDealFastFlag());
				}
			}
			// 初始化
			// boolean bFlag = false;
			List<PrpLprop> prpLpropList = certainLossDto.getPrpLpropList();
			if (prpLpropList != null) {
				intPropCount = prpLpropList.size();
			}
			// 取得保单的信息
			String policyNo = prpLclaim.getPolicyNo();
			String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
			String damageHour = prpLclaim.getDamageStartHour();
			PrpCmain prpCmain = this.getEndorseViewHelper().findPrpCmain(policyNo, damageDate , damageHour);
			List<PrpCitemKind> prpCitemKindList = this.getEndorseViewHelper().findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
			List<PrpCitemCar> prpCitemCarList = this.getEndorseViewHelper().findPrpCitemCar(policyNo, damageDate, damageHour);
			String defaultKindCode = this.findDefaultKindCode(prpCitemKindList, "prpLloss");
			List<PrpLrepairFee> prpLrepairFeeList = certainLossDto.getPrpLrepairFeeList();
			// 按险种,车牌,标的序号汇总修理费和材料费用
			if (prpLrepairFeeList != null && !prpLrepairFeeList.isEmpty()) {
				// intRepairFeeCount = prpLrepairFeeList.size();
				for (PrpLrepairFee prpLrepairFee : prpLrepairFeeList) {
					// 各赔各车处理
					if ((prpDriskConfig != null && "1".equals(prpDriskConfig.getConfigValue())) && "1".equals(prpLcheck.getDealFastFlag())) {
						if ("RISKCODE_DAZ".equals(configCode) && ConstantsCollection.MainCarLoss.contains(prpLrepairFee.getKindCode())) {
							strRepComKindCode = ConstantCodes.KINDCODE_D_BZ;
						} else if (ConstantCodes.KINDCODE_D_BZ.equals(prpLrepairFee.getKindCode())) {
							strRepComKindCode = defaultKindCode;
						} else {
							strRepComKindCode = prpLrepairFee.getKindCode();
						}
					} else {
						if ("RISKCODE_DAZ".equals(configCode) && (BusinessRuleUtil.checkKindType("ThirdCarLoss", prpLrepairFee.getKindCode()) || BusinessRuleUtil.checkKindType("ThirdPropLoss", prpLrepairFee.getKindCode()))) {
							strRepComKindCode = ConstantCodes.KINDCODE_D_BZ;
						} else if (ConstantCodes.KINDCODE_D_BZ.equals(prpLrepairFee.getKindCode())) {
							strRepComKindCode = defaultKindCode;
						} else {
							strRepComKindCode = prpLrepairFee.getKindCode();
						}
					}
					strRepComLicenseNo = prpLrepairFee.getLicenseNo();
					strRepComItemKindNo = prpLrepairFee.getItemKindNo().intValue();
					if ((Double) hashRepComFee.get("01," + strRepComKindCode + "," + strRepComLicenseNo + "," + strRepComItemKindNo) != null) {
						dbRepComSumDefFee = ((Double) hashRepComFee.get("01," + strRepComKindCode + "," + strRepComLicenseNo + "," + strRepComItemKindNo)).doubleValue();
						dbRepComSumDefFee += prpLrepairFee.getVeriSumLoss();
						hashRepComFee.put("01," + strRepComKindCode + "," + strRepComLicenseNo + "," + strRepComItemKindNo, new Double(dbRepComSumDefFee));
					} else {
						hashRepComFee.put("01," + strRepComKindCode + "," + strRepComLicenseNo + "," + strRepComItemKindNo, new Double(prpLrepairFee.getVeriSumLoss()));
					}
				}// end for
			}
			intRepFeeCount = hashRepComFee.size(); // 按币别，险别汇总後的修理费
			List<PrpLcomponent> prpLcomponentList = certainLossDto.getPrpLcomponentList();
			if (prpLcomponentList != null && !prpLcomponentList.isEmpty()) {
				// intComponentCount = prpLcomponentList.size();
				for (PrpLcomponent prpLcomponent : prpLcomponentList) {
					// 各赔各车处理
					if ((prpDriskConfig != null && "1".equals(prpDriskConfig.getConfigValue())) && "1".equals(prpLcheck.getDealFastFlag())) {
						if ("RISKCODE_DAZ".equals(configCode) && BusinessRuleUtil.checkKindType("MainCarLoss", prpLcomponent.getKindCode())) {
							strRepComKindCode = ConstantCodes.KINDCODE_D_BZ;
						} else if (ConstantCodes.KINDCODE_D_BZ.equals(prpLcomponent.getKindCode())) {
							strRepComKindCode = defaultKindCode;
						} else {
							strRepComKindCode = prpLcomponent.getKindCode();
						}
					} else {
						if ("RISKCODE_DAZ".equals(configCode) && (BusinessRuleUtil.checkKindType("ThirdCarLoss", prpLcomponent.getKindCode()) || BusinessRuleUtil.checkKindType("ThirdPropLoss", prpLcomponent.getKindCode()))) {
							strRepComKindCode = ConstantCodes.KINDCODE_D_BZ;
						} else if (ConstantCodes.KINDCODE_D_BZ.equals(prpLcomponent.getKindCode())) {
							strRepComKindCode = defaultKindCode;
						} else {
							strRepComKindCode = prpLcomponent.getKindCode();
						}
					}
					strRepComLicenseNo = prpLcomponent.getLicenseNo();
					strRepComItemKindNo = prpLcomponent.getItemKindNo().intValue();
					if ((Double) hashRepComFee.get("02," + strRepComKindCode + "," + strRepComLicenseNo + "," + strRepComItemKindNo) != null) {
						dbRepComSumDefFee = ((Double) hashRepComFee.get("02," + strRepComKindCode + "," + strRepComLicenseNo + "," + strRepComItemKindNo)).doubleValue();
						dbRepComSumDefFee += prpLcomponent.getSumVeriLoss();
						hashRepComFee.put("02," + strRepComKindCode + "," + strRepComLicenseNo + "," + strRepComItemKindNo, new Double(dbRepComSumDefFee));
					} else {
						hashRepComFee.put("02," + strRepComKindCode + "," + strRepComLicenseNo + "," + strRepComItemKindNo, new Double(prpLcomponent.getSumVeriLoss()));
					}
				}// end for
			}
			double dbComFee = 0d;
			List<PrpLcarLoss> prpLcarLossList = certainLossDto.getPrpLcarLossList();
			if (prpLcarLossList != null && !prpLcarLossList.isEmpty()) {
				for (PrpLcarLoss prpLcarLoss : prpLcarLossList) {
					String strKindCode = "";
					int strItemKindNo = -1;
					String strLicenseNo1 = "";
					for (PrpLcomponent prpLcomponent : prpLcomponentList) {
						if (prpLcomponent.getId().getLossItemCode().equals(prpLcarLoss.getId().getLossItemCode())) {
							if ((prpDriskConfig != null && "1".equals(prpDriskConfig.getConfigValue())) && "1".equals(prpLcheck.getDealFastFlag())) {
								if ("RISKCODE_DAZ".equals(configCode) && BusinessRuleUtil.checkKindType("MainCarLoss", prpLcomponent.getKindCode())) {
									strKindCode = ConstantCodes.KINDCODE_D_BZ;
								} else if (ConstantCodes.KINDCODE_D_BZ.equals(prpLcomponent.getKindCode())) {
									strKindCode = defaultKindCode;
								} else {
									strKindCode = prpLcomponent.getKindCode();
								}
							} else {
								if ("RISKCODE_DAZ".equals(configCode) && (BusinessRuleUtil.checkKindType("ThirdCarLoss", prpLcomponent.getKindCode()) || BusinessRuleUtil.checkKindType("ThirdPropLoss", prpLcomponent.getKindCode()))) {
									strKindCode = ConstantCodes.KINDCODE_D_BZ;
								} else if (ConstantCodes.KINDCODE_D_BZ.equals(prpLcomponent.getKindCode())) {
									strKindCode = defaultKindCode;
								} else {
									strKindCode = prpLcomponent.getKindCode();
								}
							}
							strItemKindNo = (int) prpLcomponent.getItemKindNo();
							strLicenseNo1 = prpLcomponent.getLicenseNo();
						}
					}// end for
					if ((Double) hashRepComFee.get("02," + strKindCode + "," + strLicenseNo1 + "," + strItemKindNo) != null) {
						dbComFee = ((Double) hashRepComFee.get("02," + strKindCode + "," + strLicenseNo1 + "," + strItemKindNo)).doubleValue();
						dbComFee += prpLcarLoss.getSumTax() + prpLcarLoss.getSumTransFee() - prpLcarLoss.getSumRest(); // 各零配件的核价累加
						dbComFee = dbComFee * (1 + prpLcarLoss.getSumManager() / 100);
						// +还得加上prplcarloss中的三金和残值
						hashRepComFee.put("02," + strKindCode + "," + strLicenseNo1 + "," + strItemKindNo, new Double(dbComFee));
					}
				}// end for
			}
			// 用於将A险B险按车辆统计到一起
			Enumeration<String> eSumEle1 = hashRepComFee.keys();
			double sumDefFeeA = 0d;
			double[] sumDefFeeB = new double[50];
			String[] licenseNoB = new String[50];
			double[] sumDefFeeBZ = new double[50];
			String[] licenseNoBZ = new String[50];
			String licenseNoA = "";
			String itemKindNoA = "";
			String[] itemKindNoB = new String[50];
			String[] itemKindNoBZ = new String[50];
			// String lossFeeType = "";
			int flagA = 0;
			int flagB = 0;
			int flagBZ = 0;
			int flagBn = 0;
			String kindCodeA = "";
			String kindCodeB = "";
			int flag1 = 0;// 被减掉的修理费数量
			int i1 = 0;// B险车辆数量
			int i2 = 0;// BZ险车辆数量
			while (eSumEle1.hasMoreElements()) {
				String strElement = (String) eSumEle1.nextElement();
				int intIndex1 = strElement.indexOf(",", 3);
				int intIndex2 = strElement.indexOf(",", intIndex1 + 1);
				// 不用合并为一个险种
				// if (BusinessRuleUtil.checkKindType("MainCarLoss",
				// strElement.substring(3, intIndex1))) {
				// flagA = 1;
				// kindCodeA = strElement.substring(3, intIndex1);
				// licenseNoA = strElement.substring(intIndex1 + 1, intIndex2);
				// itemKindNoA = strElement.substring(intIndex2 + 1);
				// sumDefFeeA += ((Double)
				// hashRepComFee.get(strElement)).doubleValue();
				// lossFeeType = strElement.substring(0, 2);
				// if ("01".equals(lossFeeType)) {
				// flag1++;
				// }
				// hashRepComFee.remove(strElement);
				// } else
				if ((ConstantCodes.KINDCODE_D_BZ.equals(strElement.substring(3, intIndex1))) && !("".equals(strElement.substring(intIndex1 + 1, intIndex2)))) {
					flagBZ = 1;
					flagBn = 0;
					kindCodeB = strElement.substring(3, intIndex1);
					for (int j1 = 0; j1 < i2; j1++) {
						if (licenseNoBZ[j1].equals(strElement.substring(intIndex1 + 1, intIndex2))) {
							flagBn = 1;
							sumDefFeeBZ[j1] += ((Double) hashRepComFee.get(strElement)).doubleValue();
							break;
						}
					}
					if (flagBn == 0) {
						itemKindNoBZ[i2] = strElement.substring(intIndex2 + 1);
						licenseNoBZ[i2] = strElement.substring(intIndex1 + 1, intIndex2);
						sumDefFeeBZ[i2] = ((Double) hashRepComFee.get(strElement)).doubleValue();
						i2++;
					}
					if ("01".equals(strElement.substring(0, 2))) {
						flag1++;
					}
					hashRepComFee.remove(strElement);
				}
				// else if (((BusinessRuleUtil.checkKindType("ThirdCarLoss",
				// strElement.substring(3,
				// intIndex1)))||BusinessRuleUtil.checkKindType("ThirdPropLoss",
				// strElement.substring(3, intIndex1))) &&
				// !("".equals(strElement.substring(intIndex1 + 1, intIndex2))))
				// {
				// flagB = 1;
				// flagBn = 0;
				// for (int j1 = 0; j1 < i1; j1++) {
				// if (licenseNoB[j1].equals(strElement.substring(intIndex1 + 1,
				// intIndex2))) {
				// flagBn = 1;
				// sumDefFeeB[j1] += ((Double)
				// hashRepComFee.get(strElement)).doubleValue();
				// break;
				// }
				// }
				// if (flagBn == 0) {
				// itemKindNoB[i1] = strElement.substring(intIndex2 + 1);
				// licenseNoB[i1] = strElement.substring(intIndex1 + 1,
				// intIndex2);
				// sumDefFeeB[i1] = ((Double)
				// hashRepComFee.get(strElement)).doubleValue();
				// i1++;
				// }
				// if ("01".equals(strElement.substring(0, 2))) {
				// flag1++;
				// }
				// hashRepComFee.remove(strElement);
				// }
			}
			if (flagA == 1) {
				hashRepComFee.put("01," + kindCodeA + "," + licenseNoA + "," + itemKindNoA, new Double(sumDefFeeA));
			}
			if (flagB == 1) {
				for (int c = 0; c < i1; c++) {
					hashRepComFee.put("01," + kindCodeB + "," + licenseNoB[c] + "," + itemKindNoB[c], new Double(sumDefFeeB[c]));
				}
			}
			if (flagBZ == 1) {
				for (int c = 0; c < i2; c++) {
					hashRepComFee.put("01," + ConstantCodes.KINDCODE_D_BZ + "," + licenseNoBZ[c] + "," + itemKindNoBZ[c], new Double(sumDefFeeBZ[c]));
				}
			}
			String licenseNo = "";
			List<PrpLthirdParty> prpLthirdPartyList = claimDto.getPrpLthirdPartyList();
			if (prpLthirdPartyList != null && !prpLthirdPartyList.isEmpty()) {
				for (PrpLthirdParty temp : prpLthirdPartyList) {
					if ("1".equals(temp.getInsureCarFlag())) {
						licenseNo = temp.getLicenseNo();
						break;
					}
				}
			}
			Enumeration<String> eSumEle2 = hashRepComFee.keys();
			if ((prpDriskConfig != null && "1".equals(prpDriskConfig.getConfigValue())) && "1".equals(prpLcheck.getDealFastFlag())) {
				// 如果是快速处理案件将三者车数据去掉
				if ("RISKCODE_DAZ".equals(configCode)) {
					while (eSumEle2.hasMoreElements()) {
						String strElement = (String) eSumEle2.nextElement();
						int intIndex1 = strElement.indexOf(",", 3);
						int intIndex2 = strElement.indexOf(",", intIndex1 + 1);
						String licenseNon = strElement.substring(intIndex1 + 1, intIndex2);
						if (!(licenseNo.equals(licenseNon))) {
							hashRepComFee.remove(strElement);
							intRepFeeCount--;
						}
					}
				}
			}
			intRepFeeCount = intRepFeeCount - flag1 + i1 + 1;

			intComFeeCount = hashRepComFee.size() - intRepFeeCount; // 按险别,车牌号，标的序号汇总後的材料费

			arrKindCode = new String[intRepFeeCount + intComFeeCount];// 险别
			arrLossFeeType = new String[intRepFeeCount + intComFeeCount];// 费用种类
			arrLossFeeTypeName = new String[intRepFeeCount + intComFeeCount];// 费用种类名称
			arrLicenseNo = new String[intRepFeeCount + intComFeeCount];// 车牌号码
			arrItemKindNo = new String[intRepFeeCount + intComFeeCount];// 车辆序号
			arrSumDefFee = new double[intRepFeeCount + intComFeeCount];// 总费用
			int k = 0;
			Enumeration<String> eSumEle = hashRepComFee.keys();
			while (eSumEle.hasMoreElements()) {
				String strElement = (String) eSumEle.nextElement();
				int intIndex1 = strElement.indexOf(",", 3);
				int intIndex2 = strElement.indexOf(",", intIndex1 + 1);
				arrLossFeeType[k] = strElement.substring(0, 2);
				arrKindCode[k] = strElement.substring(3, intIndex1);
				arrLicenseNo[k] = strElement.substring(intIndex1 + 1, intIndex2);
				arrItemKindNo[k] = strElement.substring(intIndex2 + 1);
				arrSumDefFee[k] = ((Double) hashRepComFee.get(strElement)).doubleValue();
				k++;
			}// end while

			for (k = 0; k < arrLossFeeType.length; k++) {
				if (arrLossFeeType[k].equals("01")) {
					arrLossFeeTypeName[k] = "修理费";
				} else {
					// "02"
					arrLossFeeTypeName[k] = "材料费";
				}
			}// end for
			// 得到险别估损信息
			List<PrpLclaimLoss> prpLclaimLossList = claimDto.getPrpLclaimLossList();
			if (prpCitemKindList != null) {
				intItemKindCount = prpCitemKindList.size();
			}
			// 得到理赔车辆信息
			String strPersonLicenseNo = "";
			if (prpLthirdPartyList != null && !prpLthirdPartyList.isEmpty()) {
				PrpLthirdParty temp = null;
				for (index = 0; index < prpLthirdPartyList.size(); index++) {
					temp = prpLthirdPartyList.get(index);
					if (index == 0) {
						strPersonLicenseNo += temp.getLicenseNo() + "_FIELD_SEPARATOR_" + temp.getLicenseNo();
					} else {
						strPersonLicenseNo += "_GROUP_SEPARATOR_" + temp.getLicenseNo() + "_FIELD_SEPARATOR_" + temp.getLicenseNo();
					}
				}
			}// end for
			if (!((intPropCount + intRepFeeCount + intComFeeCount) == 0)) {
				// 得到赔付标的信息
				// double dblDeductibleRate = 0;
				double dblDutyDeductibleRate = 0;
				int kindFlag = 0;
				PrpLloss prpLlossDtoTmp = new PrpLloss();
				// 取出财产的损失
				String citemKindCode = "";
				for (index = 0; index < intPropCount; index++) {
					strItemValue = 0;
					strIndemnityDutyRate = 0;
					serialNo++;
					prpLlossDto = new PrpLloss();
					prpLlossDtoTmp = new PrpLloss();
					prpLpropDto = prpLpropList.get(index);
					for (int i = 0; i < intItemKindCount; i++) {
						prpCitemKind = prpCitemKindList.get(i);
						// 判断该险别是否为估损险别
						if ("RISKCODE_DAZ".equals(configCode) && (BusinessRuleUtil.checkKindType("ThirdCarLoss", prpCitemKind.getKindCode()) || BusinessRuleUtil.checkKindType("ThirdPropLoss", prpCitemKind.getKindCode()))) {
							citemKindCode = ConstantCodes.KINDCODE_D_BZ;
						} else if (ConstantCodes.KINDCODE_D_BZ.equals(prpCitemKind.getKindCode())) {
							citemKindCode = defaultKindCode;
						} else {
							citemKindCode = prpCitemKind.getKindCode();
						}
						kindFlag = this.getLossKind(citemKindCode, prpLclaimLossList);
						if (kindFlag == 0)
							continue;
						if (BusinessRuleUtil.checkKindType("MainCarLoss", citemKindCode) || (BusinessRuleUtil.checkKindType("ThirdCarLoss", citemKindCode) || BusinessRuleUtil.checkKindType("ThirdPropLoss", citemKindCode))) {
							strItemValue = prpCitemKind.getAmount();
						}
						// reason:增加可选免赔额
						if (BusinessRuleUtil.checkKindType("MainCarLoss", prpCitemKind.getKindCode()) && prpCitemKind.getValue() > 0 && prpLpropDto.getRiskCode().equals(this.getCodeService().translateProductCode("RISKCODE_DAA"))
								&& BusinessRuleUtil.checkKindType("MainCarLoss", prpLpropDto.getKindCode())) {
							// bFlag = true;
						}
						if (prpCitemKind.getId().getItemKindNo() == prpLpropDto.getItemKindNo()) {
							break;
						}
					}// end intItemKindCount
					if (prpCitemCarList != null && prpCitemCarList.size() > 0) {
						prpCitemCar = prpCitemCarList.get(0);
						// strLicenseNo = prpCitemCar.getLicenseNo();
					}
					if ("RISKCODE_DAZ".equals(configCode) && (BusinessRuleUtil.checkKindType("ThirdCarLoss", prpLpropDto.getKindCode()) || BusinessRuleUtil.checkKindType("ThirdPropLoss", prpLpropDto.getKindCode()))) {
						strCode = ConstantCodes.KINDCODE_D_BZ;
					} else if (ConstantCodes.KINDCODE_D_BZ.equals(prpLpropDto.getKindCode())) {
						strCode = defaultKindCode;
					} else {
						strCode = prpLpropDto.getKindCode();
					}
					if (strCode == null) {
						strCode = "";
					}
					if (BusinessRuleUtil.checkKindType("MainCarLoss", strCode) || (BusinessRuleUtil.checkKindType("ThirdCarLoss", strCode) || BusinessRuleUtil.checkKindType("ThirdPropLoss", strCode)) || strCode.equals("X") || strCode.equals("D1")
							|| strCode.equals("D2") || strCode.equals(ConstantCodes.KINDCODE_D_BZ)) {
						strIndemnityDutyRate = prpLclaim.getIndemnityDutyRate();
					} else {
						strIndemnityDutyRate = 100;
					}
					UIDeductCondAction uiDeductCondAction = UIDeductCondAction.getInstance();
					if (prpCmain.getOperateDate() == null) {
						throw new UserException(0, 0, "保單生效期爲空");
					}
					for (int i = 0; i < intItemKindCount; i++) {
						PrpCitemKind prpCitemKindDto1 = prpCitemKindList.get(i);
						if (strCode.equals(prpCitemKindDto1.getKindCode())) {
							// 事故责任免赔率
							dblDutyDeductibleRate = uiDeductCondAction.getDeductibleRateOfAccident(prpLpropDto.getRiskCode(), strCode, prpLclaim.getIndemnityDuty(), "0", prpCitemCar.getClauseType(), new DateTime(prpCmain.getStartDate()).toString());
							if (prpCitemKindDto1.getFlag() != null && prpCitemKindDto1.getFlag().length() > 4) {
								flag = prpCitemKindDto1.getFlag().substring(4, 5).trim();
							}

						}
					}// end intItemKindCount
					prpLlossDtoTmp.setRiskCode(prpLpropDto.getRiskCode());
					prpLlossDtoTmp.setPolicyNo(prpLpropDto.getPolicyNo());
					prpLlossDtoTmp.getId().setCompensateNo(claimNo);
					prpLlossDtoTmp.setKindCode(strCode);
					prpLlossDtoTmp.setCurrency(prpLpropDto.getCurrency());
					prpLlossDtoTmp.setAmount(prpCitemKind.getAmount());
					prpLlossDtoTmp.setLossQuantity(prpLpropDto.getLossQuantity());
					prpLlossDtoTmp.setItemValue(strItemValue);
					prpLlossDtoTmp.setSumLoss(prpLpropDto.getSumDefLoss());
					prpLlossDtoTmp.setSumRest(0);
					prpLlossDtoTmp.setIndemnityDutyRate(strIndemnityDutyRate);
					prpLlossDtoTmp.setArrangeRate(100);
					prpLlossDtoTmp.setDeductiblerate(0);
					prpLlossDtoTmp.setDutyDeductibleRate(dblDutyDeductibleRate);
					prpLlossDtoTmp.setFlag(flag);
					// 在计算引擎中计算金额，不在用方法计算
					// prpLlossDtoTmp = calculateSumLoss(prpLlossDtoTmp);
					prpLlossDto.getId().setSerialNo(serialNo);
					prpLlossDto.setAmount(prpCitemKind.getAmount());
					prpLlossDto.setLicenseNo("");
					prpLlossDto.setItemKindNo(prpLpropDto.getItemKindNo());
					prpLlossDto.setKindCode(strCode);
					// 不计免赔率金额:
					// 不计免赔率金额 = 受损金额*承保比例*协商比例*责任比例*免赔
					if ("1".equals(flag)) {
						if ("G".equals(prpLlossDto.getKindCode())) {
							prpLlossDtoTmp.setExceptDeductibleRate(0);
							prpLlossDtoTmp.setExceptDeductiblePay(prpLlossDtoTmp.getSumLoss() * prpLlossDtoTmp.getClaimRate() / 100 * prpLlossDtoTmp.getArrangeRate() / 100 * prpLlossDtoTmp.getIndemnityDutyRate() / 100
									* prpLlossDtoTmp.getExceptDeductibleRate() / 100);
						} else {
							prpLlossDtoTmp.setExceptDeductibleRate(prpLlossDtoTmp.getDutyDeductibleRate());
							prpLlossDtoTmp.setExceptDeductiblePay(prpLlossDtoTmp.getSumLoss() * prpLlossDtoTmp.getClaimRate() / 100 * prpLlossDtoTmp.getArrangeRate() / 100 * prpLlossDtoTmp.getIndemnityDutyRate() / 100
									* prpLlossDtoTmp.getExceptDeductibleRate() / 100);
						}
					} else {
						prpLlossDtoTmp.setExceptDeductibleRate(0);
						prpLlossDtoTmp.setExceptDeductiblePay(0);
					}

					prpLlossDto.setRiskCode(prpLpropDto.getRiskCode());
					prpLlossDto.setKindName(this.getCodeService().translateKindCode(prpLpropDto.getRiskCode(), strCode, true));
					prpLlossDto.setFeeTypeCode(prpLpropDto.getFeeTypeCode());
					prpLlossDto.setFeeTypeName(prpLpropDto.getFeeTypeName());
					prpLlossDto.setCurrency1(prpLpropDto.getCurrency());
					prpLlossDto.setCurrency1Name(this.getCodeService().translateCurrencyCode(prpLpropDto.getCurrency(), true));
					prpLlossDto.setCurrency(prpLpropDto.getCurrency());
					prpLlossDto.setCurrencyName(this.getCodeService().translateCurrencyCode(prpLpropDto.getCurrency(), true));
					prpLlossDto.setCurrency2(prpLpropDto.getCurrency());
					prpLlossDto.setCurrency2Name(this.getCodeService().translateCurrencyCode(prpLpropDto.getCurrency(), true));
					prpLlossDto.setCurrency3(prpLpropDto.getCurrency());
					prpLlossDto.setCurrency3Name(this.getCodeService().translateCurrencyCode(prpLpropDto.getCurrency(), true));
					prpLlossDto.setCurrency4(prpLpropDto.getCurrency());
					prpLlossDto.setCurrency4Name(this.getCodeService().translateCurrencyCode(prpLpropDto.getCurrency(), true));
					prpLlossDto.setLossName(prpLpropDto.getLossItemName());
					prpLlossDto.setUnitPrice(prpLpropDto.getUnitPrice());
					prpLlossDto.setLossQuantity(prpLpropDto.getLossQuantity());
					prpLlossDto.setItemValue(strItemValue);
					prpLlossDto.setSumLoss(prpLpropDto.getSumDefLoss());
					prpLlossDto.setSumRest(0);
					prpLlossDto.setIndemnityDutyRate(strIndemnityDutyRate);
					prpLlossDto.setArrangeRate(100);
					prpLlossDto.setClaimRate(prpLlossDtoTmp.getClaimRate());
					prpLlossDto.setDeductiblerate(prpLlossDtoTmp.getDeductiblerate());
					prpLlossDto.setDutyDeductibleRate(prpLlossDtoTmp.getDutyDeductibleRate());
					prpLlossDto.setDeductible(prpLlossDtoTmp.getDeductible());
					prpLlossDto.setSumRealPay(prpLlossDtoTmp.getSumRealPay());
					prpLlossDto.setFlag(prpLlossDtoTmp.getFlag().trim());
					prpLlossDto.setExceptDeductiblePay(prpLlossDtoTmp.getExceptDeductiblePay());
					prpLlossDto.setExceptDeductibleRate(prpLlossDtoTmp.getExceptDeductibleRate());
					prpLlossListTemp.add(prpLlossDto);
				}// end for intPropCount
				for (index1 = index; index1 < (intPropCount + intRepFeeCount + intComFeeCount); index1++) {
					strItemValue = 0;
					strIndemnityDutyRate = 0;
					k = 0;
					serialNo++;
					prpLlossDto = new PrpLloss();
					PrpLloss prpLlossDtoTemp = new PrpLloss();
					for (int i = 0; i < intItemKindCount; i++) {
						prpCitemKind = prpCitemKindList.get(i);
						// 显示的险别必须是立案中估损的险别
						if (BusinessRuleUtil.checkKindType("MainCarLoss", prpCitemKind.getKindCode()) || (BusinessRuleUtil.checkKindType("ThirdCarLoss", strCode) || BusinessRuleUtil.checkKindType("ThirdPropLoss", prpCitemKind.getKindCode()))) {
							strItemValue = prpCitemKind.getAmount();
						}
						// reason:增加自负额
						if (BusinessRuleUtil.checkKindType("MainCarLoss", prpCitemKind.getKindCode()) && prpCitemKind.getValue() > 0 && strRiskCode.equals(this.getCodeService().translateProductCode("RISKCODE_DAA"))
								&& BusinessRuleUtil.checkKindType("MainCarLoss", arrKindCode[index1 - index])) {
							// bFlag = true;
						}
						if (String.valueOf(prpCitemKind.getId().getItemKindNo()).equals(arrItemKindNo[index1 - index])) {
							break;
						}
					}// end for
					if (prpCitemCarList != null && prpCitemCarList.size() > 0) {
						prpCitemCar = prpCitemCarList.get(0);
					}
					if ((prpDriskConfig != null && "1".equals(prpDriskConfig.getConfigValue())) && "1".equals(prpLcheck.getDealFastFlag())) {
						if ("RISKCODE_DAZ".equals(configCode) && (BusinessRuleUtil.checkKindType("MainCarLoss", arrKindCode[index1 - index]))) {
							strCode = ConstantCodes.KINDCODE_D_BZ;
						} else if (ConstantCodes.KINDCODE_D_BZ.equals(arrKindCode[index1 - index])) {
							strCode = defaultKindCode;
						} else {
							strCode = arrKindCode[index1 - index];
						}
					} else {
						if ("RISKCODE_DAZ".equals(configCode) && (BusinessRuleUtil.checkKindType("ThirdCarLoss", arrKindCode[index1 - index]) || BusinessRuleUtil.checkKindType("ThirdPropLoss", arrKindCode[index1 - index]))) {
							strCode = ConstantCodes.KINDCODE_D_BZ;
						} else if (ConstantCodes.KINDCODE_D_BZ.equals(arrKindCode[index1 - index])) {
							strCode = defaultKindCode;
						} else {
							strCode = arrKindCode[index1 - index];
						}
					}
					if (BusinessRuleUtil.checkKindType("MainCarLoss", strCode) || (BusinessRuleUtil.checkKindType("ThirdCarLoss", strCode) || BusinessRuleUtil.checkKindType("ThirdPropLoss", strCode)) || strCode.equals("X") || strCode.equals("D1")
							|| strCode.equals("D2")) {
						strIndemnityDutyRate = prpLclaim.getIndemnityDutyRate();
					} else {
						strIndemnityDutyRate = 100;
					}

					// 事故责任免赔率
					UIDeductCondAction uiDeductCondAction = UIDeductCondAction.getInstance();
					if (prpCmain.getOperateDate() == null) {
						throw new UserException(0, 0, "保單生效期爲空");
					}
					for (int i = 0; i < intItemKindCount; i++) {
						PrpCitemKind prpCitemKindDto1 = prpCitemKindList.get(i);
						if ((BusinessRuleUtil.checkKindType("MainCarLoss", strCode) || (BusinessRuleUtil.checkKindType("ThirdCarLoss", strCode) || BusinessRuleUtil.checkKindType("ThirdPropLoss", strCode)) || "D1".equals(strCode) || "G"
								.equals(strCode))
								&& strCode.equals(prpCitemKindDto1.getKindCode())) {
							dblDutyDeductibleRate = uiDeductCondAction.getDeductibleRateOfAccident(strRiskCode, strCode, prpLclaim.getIndemnityDuty(), "0", prpCitemCar.getClauseType(), new DateTime(prpCmain.getStartDate()).toString());
						}
					}// end for intItemKindCount
					// dblDeductibleRate = 0;
					prpLlossDto.setRiskCode(strRiskCode);
					prpLlossDto.setPolicyNo(prpCitemKind.getId().getPolicyNo());
					prpLlossDto.getId().setCompensateNo(prpLclaim.getClaimNo());
					prpLlossDto.setKindCode(strCode);
					prpLlossDto.setCurrency(prpLclaim.getCurrency());
					prpLlossDto.setAmount(prpCitemKind.getAmount());
					prpLlossDto.setLossQuantity(prpCitemKind.getQuantity() == null ? 0 : prpCitemKind.getQuantity());
					prpLlossDto.setItemValue(strItemValue);
					prpLlossDto.setSumLoss(arrSumDefFee[index1 - index]);
					prpLlossDto.setSumRest(0);
					prpLlossDto.setIndemnityDutyRate(strIndemnityDutyRate);
					prpLlossDto.setArrangeRate(100);

					prpLlossDto.setDeductiblerate(0);
					prpLlossDto.setDutyDeductibleRate(dblDutyDeductibleRate);

					prpLlossDtoTemp.getId().setSerialNo(serialNo);
					prpLlossDtoTemp.setRiskCode(prpLlossDto.getRiskCode());
					prpLlossDtoTemp.setAmount(prpCitemKind.getAmount());

					prpLlossDtoTemp.setLicenseNo(arrLicenseNo[index1 - index]);
					prpLlossDtoTemp.setItemKindNo((int) Double.parseDouble(arrItemKindNo[index1 - index]));

					prpLlossDtoTemp.setKindCode(strCode);
					prpLlossDtoTemp.setKindName(this.getCodeService().translateKindCode(this.getCodeService().translateProductCode("RISKCODE_DAA"), prpLlossDtoTemp.getKindCode(), true));
					prpLlossDtoTemp.setFeeTypeCode(arrLossFeeType[index1 - index]);
					prpLlossDtoTemp.setFeeTypeName(arrLossFeeTypeName[index1 - index]);
					String currency = prpLclaim.getCurrency();
					String currencyName = this.getCodeService().translateCurrencyCode(currency, true);
					prpLlossDtoTemp.setCurrency1(currency);
					prpLlossDtoTemp.setCurrency1Name(currencyName);
					prpLlossDtoTemp.setCurrency(currency);
					prpLlossDtoTemp.setCurrencyName(currencyName);
					prpLlossDtoTemp.setCurrency2(currency);
					prpLlossDtoTemp.setCurrency2Name(currencyName);
					prpLlossDtoTemp.setCurrency3(currency);
					prpLlossDtoTemp.setCurrency3Name(currencyName);
					prpLlossDtoTemp.setCurrency4(currency);
					prpLlossDtoTemp.setCurrency4Name(currencyName);
					prpLlossDtoTemp.setLossName(prpCitemKind.getItemDetailName());
					prpLlossDtoTemp.setUnitPrice(prpCitemKind.getUnitAmount() == null ? 0 : prpCitemKind.getUnitAmount());
					prpLlossDtoTemp.setLossQuantity(prpCitemKind.getQuantity() == null ? 0 : prpCitemKind.getQuantity());
					prpLlossDtoTemp.setItemValue(strItemValue);
					prpLlossDtoTemp.setSumLoss(arrSumDefFee[index1 - index]);
					if (claimRelateFlag > 1) {
						// 判断是否有关联报案
						if ((prpDriskConfig != null && "1".equals(prpDriskConfig.getConfigValue())) && "1".equals(prpLcheck.getDealFastFlag()) && BusinessRuleUtil.checkKindType("MainCarLoss", strCode)
								&& !(ConstantCodes.RISKCODE_DAZ.equals(strRiskCode))) {
							if (arrSumDefFee[index1 - index] <= 2000) {
								prpLlossDtoTemp.setCompelPay(arrSumDefFee[index1 - index]);
							} else {
								prpLlossDtoTemp.setCompelPay(2000);
							}
						}
					}
					prpLlossDtoTemp.setSumRest(0);
					prpLlossDtoTemp.setIndemnityDutyRate(strIndemnityDutyRate);
					prpLlossDtoTemp.setArrangeRate(100);
					prpLlossDtoTemp.setClaimRate(prpLlossDto.getClaimRate());
					prpLlossDtoTemp.setDeductiblerate(prpLlossDto.getDeductiblerate());
					prpLlossDtoTemp.setDutyDeductibleRate(prpLlossDto.getDutyDeductibleRate());
					if (null != prpCitemKind.getFlag() && prpCitemKind.getFlag().length() > 4) {
						prpLlossDtoTemp.setFlag(prpCitemKind.getFlag().substring(4, 5));
					} else {
						prpLlossDtoTemp.setFlag("0");
					}
					prpLlossDtoTemp.setDeductible(prpLlossDto.getDeductible());
					// 赔付标的信息中赔付金额值不对 ；
					double exceptDeductiblePay = 0.00;
					if ("1".equals(prpLlossDtoTemp.getFlag())) {
						if ("G".equals(prpLlossDtoTemp.getKindCode())) {
							prpLlossDtoTemp.setExceptDeductibleRate(0);
							exceptDeductiblePay = (prpLlossDto.getSumLoss() - prpLlossDto.getSumRest()) * (prpLlossDto.getClaimRate() / 100) * (prpLlossDto.getIndemnityDutyRate() / 100) * (prpLlossDtoTemp.getExceptDeductibleRate() / 100);
							prpLlossDtoTemp.setExceptDeductiblePay(exceptDeductiblePay);
						} else {
							prpLlossDtoTemp.setExceptDeductibleRate(prpLlossDtoTemp.getDutyDeductibleRate());
							exceptDeductiblePay = (prpLlossDto.getSumLoss() - prpLlossDto.getSumRest()) * (prpLlossDto.getClaimRate() / 100) * (prpLlossDto.getIndemnityDutyRate() / 100) * (prpLlossDtoTemp.getExceptDeductibleRate() / 100);
							prpLlossDtoTemp.setExceptDeductiblePay(exceptDeductiblePay);
						}
					} else {
						prpLlossDtoTemp.setExceptDeductibleRate(0);
						prpLlossDtoTemp.setExceptDeductiblePay(0);
					}
					prpLlossDtoTemp.setSumRealPay(0);
					// 赔付标的信息中赔付金额值不对 ；
					prpLlossListTemp.add(prpLlossDtoTemp);
				}
			}
		}// end else
		return prpLlossListTemp;
	}

	/**
	 * 对根据立案估损获得所有出险的险别信息的List进行排序
	 * @author 中科软
	 */
	private static class getPrpcItemKindDtoLossListSort implements Comparator<Object> {
		public int compare(Object arg0, Object arg1) {
			int i = 0;
			if (((PrpCitemKind) arg0).getKindCode().compareTo(((PrpCitemKind) arg1).getKindCode()) == 0) {
				i = 0;
			} else if (((PrpCitemKind) arg0).getKindCode().compareTo(((PrpCitemKind) arg1).getKindCode()) < 0) {
				i = -1;
			} else {
				i = 1;
			}
			return i;
		}
	}

	/**
	 * 根据立案估损获得所有出险的险别信息
	 * @param prpcItemKindDtoList
	 * @param prplClaimLossDtoList
	 * @return
	 * @throws Exception
	 */
	private List<PrpCitemKind> getPrpcItemKindDtoLossList(List<PrpCitemKind> prpcItemKindDtoList, List<PrpLclaimLoss> prplClaimLossList) throws Exception {
		List<PrpCitemKind> getPrpcItemKindDtoLossList = new ArrayList<PrpCitemKind>();
		for (Iterator<PrpCitemKind> iter = prpcItemKindDtoList.iterator(); iter.hasNext();) {
			PrpCitemKind prpCitemKindDto = iter.next();
			if (!prpCitemKindDto.getKindCode().equals("D3") && !prpCitemKindDto.getKindCode().equals("D4")) {
				getPrpcItemKindDtoLossList.add(prpCitemKindDto);
			}
		}
		Collections.sort(getPrpcItemKindDtoLossList, new getPrpcItemKindDtoLossListSort());
		return getPrpcItemKindDtoLossList;
	}

	/**
	 * 获取险别是否为出险险别
	 * @param kindCode
	 * @param prplClaimLossList
	 * @return
	 * @throws Exception
	 */
	private int getLossKind(String kindCode, List<PrpLclaimLoss> prplClaimLossList) throws Exception {
		if (prplClaimLossList != null && !prplClaimLossList.isEmpty()) {
			for (PrpLclaimLoss prpLclaimLoss : prplClaimLossList) {
				if (prpLclaimLoss.getKindCode().equals(kindCode)) {
					return 1;
				}
			}
		}
		return 0;
	}

	private double Number2(double pDouble) {
		BigDecimal bd = new BigDecimal(pDouble);
		BigDecimal bd1 = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		pDouble = bd1.doubleValue();
		return pDouble;
	}

	public void lossCompelInit(HttpServletRequest httpServletRequest, List<PrpLloss> prpLlossList) throws Exception {
		// 赔付标的信息
		String[] propSerialNo = httpServletRequest.getParameterValues("propSerialNo");
		String[] propLicenseNo = httpServletRequest.getParameterValues("propLicenseNo");
		String[] propName = httpServletRequest.getParameterValues("propName");
		String[] propFeeTypeCode = httpServletRequest.getParameterValues("propFeeTypeCode");
		String[] propFeeTypeName = httpServletRequest.getParameterValues("propFeeTypeName");
		String[] propSumLoss = httpServletRequest.getParameterValues("propSumLoss");
		String[] propEliminate = httpServletRequest.getParameterValues("propEliminate");
		String[] propSumDefPay = httpServletRequest.getParameterValues("propSumDefPay");

		String[] prpLlossDtoSerialNo = httpServletRequest.getParameterValues("lossDtoSerialNo");
		String prpLlossDtoPolicyNo = httpServletRequest.getParameter("prpLcompensatePolicyNo");
		String prpLlossDtoRiskCode = httpServletRequest.getParameter("prpLcompensateRiskCode");
		String prpLlossDtoCompensateNo = (String) httpServletRequest.getAttribute("compensateNo");
		int propindex = 0;
		if (prpLlossDtoSerialNo != null) {
			propindex = prpLlossDtoSerialNo.length;
		}
		if (propSerialNo != null && propSerialNo.length > 0) {
			PrpLloss prpLloss = null;
			for (int index = 1; index < propSerialNo.length; index++) {
				prpLloss = new PrpLloss();
				prpLloss.setPolicyNo(prpLlossDtoPolicyNo);
				prpLloss.setRiskCode(prpLlossDtoRiskCode);
				prpLloss.getId().setCompensateNo(prpLlossDtoCompensateNo);
				prpLloss.getId().setSerialNo(index + propindex - 1);
				prpLloss.setKindCode(ConstantCodes.KINDCODE_D_BZ);
				prpLloss.setLicenseNo(propLicenseNo[index]);
				prpLloss.setFeeTypeCode(propFeeTypeCode[index]);
				prpLloss.setFeeTypeName(propFeeTypeName[index]);
				prpLloss.setSumLoss(Double.parseDouble(DataUtils.nullToZero(propSumLoss[index])));
				prpLloss.setSumRest(Double.parseDouble(DataUtils.nullToZero(propEliminate[index])));
				prpLloss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
				prpLloss.setCurrency1(ConstantCodes.LOCAL_CURRENCY);
				prpLloss.setCurrency2(ConstantCodes.LOCAL_CURRENCY);
				prpLloss.setCurrency3(ConstantCodes.LOCAL_CURRENCY);
				prpLloss.setCurrency4(ConstantCodes.LOCAL_CURRENCY);
				prpLloss.setLossName(propName[index]);
				prpLloss.setSumDefPay(Double.parseDouble(DataUtils.nullToZero(propSumDefPay[index])));
				prpLloss.setSumRealPay(Double.parseDouble(DataUtils.nullToZero(propSumDefPay[index])));
				prpLlossList.add(prpLloss);
			}
		}
	}

	/*
	 * 简易赔案财产损失分项统计
	 */
	public void quickCaseLossCompelInit(HttpServletRequest httpServletRequest, List<PrpLloss> prpLlossList) throws Exception {
		// 赔付标的信息
		String[] propSerialNo = httpServletRequest.getParameterValues("lossDtoSerialNo");
		String[] propLicenseNo = httpServletRequest.getParameterValues("licenseNo");
		String[] propName = httpServletRequest.getParameterValues("prpLlossDtoLossName");
		String[] propSumLoss = httpServletRequest.getParameterValues("prpLlossDtoSumLoss");
		String[] propEliminate = httpServletRequest.getParameterValues("prpLlossDtoSumRest");
		String[] propSumDefPay = httpServletRequest.getParameterValues("prpLlossDtoSumDefPay");
		String[] prpLlossDtoSerialNo = httpServletRequest.getParameterValues("lossDtoSerialNo");
		String prpLlossDtoPolicyNo = httpServletRequest.getParameter("prpLcompensatePolicyNo");
		String prpLlossDtoRiskCode = httpServletRequest.getParameter("prpLcompensateRiskCode");
		String prpLlossDtoCompensateNo = (String) httpServletRequest.getAttribute("compelPrpLcompensateNo");
		int propindex = 0;
		if (prpLlossDtoSerialNo != null) {
			propindex = prpLlossDtoSerialNo.length;
		}
		if (propSerialNo != null) {
			PrpLloss prpLloss = null;
			for (int index = 1; index < propSerialNo.length; index++) {
				prpLloss = new PrpLloss();
				prpLloss.setPolicyNo(prpLlossDtoPolicyNo);
				prpLloss.setRiskCode(prpLlossDtoRiskCode);
				prpLloss.getId().setCompensateNo(prpLlossDtoCompensateNo);
				prpLloss.getId().setSerialNo(index + propindex - 1);
				prpLloss.setKindCode(ConstantCodes.KINDCODE_D_BZ);
				prpLloss.setLicenseNo(propLicenseNo[index]);
				prpLloss.setSumLoss(Double.parseDouble(DataUtils.nullToZero(propSumLoss[index])));
				prpLloss.setSumRest(Double.parseDouble(DataUtils.nullToZero(propEliminate[index])));
				prpLloss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
				prpLloss.setCurrency1(ConstantCodes.LOCAL_CURRENCY);
				prpLloss.setCurrency2(ConstantCodes.LOCAL_CURRENCY);
				prpLloss.setCurrency3(ConstantCodes.LOCAL_CURRENCY);
				prpLloss.setCurrency4(ConstantCodes.LOCAL_CURRENCY);
				prpLloss.setLossName(propName[index]);
				prpLloss.setSumDefPay(Double.parseDouble(DataUtils.nullToZero(propSumDefPay[index])));
				prpLloss.setSumRealPay(Double.parseDouble(DataUtils.nullToZero(propSumDefPay[index])));
				prpLlossList.add(prpLloss);
			}
		}
	}

	public void personLossCompelInit(HttpServletRequest httpServletRequest, List<PrpLpersonLoss> prpLpersonLossList) throws Exception {
		// 赔付人员信息
		String prpLpersonLossCompensateNo = (String) httpServletRequest.getAttribute("compensateNo");
		String prpLpersonLossRiskCode = httpServletRequest.getParameter("prpLcompensateRiskCode");
		String prpLpersonLossPolicyNo = httpServletRequest.getParameter("prpLcompensatePolicyNo");
		String[] prpLpersonLossDangerNo = httpServletRequest.getParameterValues("prpLpersonLossDangerNo");

		String[] medicDeathFlag = httpServletRequest.getParameterValues("medicDeathFlag");
		String[] personMedicalSerialNo = httpServletRequest.getParameterValues("personMedicalSerialNo");
		String[] prpLpersonCommerceSerialNo = httpServletRequest.getParameterValues("prpLpersonCommerceSerialNo");
		String[] prpLpersonCommercePersonName = httpServletRequest.getParameterValues("prpLpersonCommercePersonName");
		String[] prpLpersonCommerceSex = httpServletRequest.getParameterValues("prpLpersonCommerceSex");
		String[] prpLpersonCommerceAge = httpServletRequest.getParameterValues("prpLpersonCommerceAge");
		String[] prpLpersonCommerceFamilyName = httpServletRequest.getParameterValues("prpLpersonCommerceFamilyName");
		String[] prpLpersonCommerceBirthday = httpServletRequest.getParameterValues("prpLpersonCommerceBirthday");
		String[] prpLpersonCommerceIdentityOfInjuredPerson = httpServletRequest.getParameterValues("prpLpersonCommerceIdentityOfInjuredPerson");
		String[] prpLpersonCommerceRideSituation = httpServletRequest.getParameterValues("prpLpersonCommerceRideSituation");
		String[] prpLpersonCommerceIdentifyNumber = httpServletRequest.getParameterValues("prpLpersonCommerceIdentifyNumber");
		//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單
		String[] prpLpersonLossIdNumberType = httpServletRequest.getParameterValues("prpLpersonLossIdNumberType");
		String[] prpLpersonCommerceMedicalCode = httpServletRequest.getParameterValues("prpLpersonCommerceMedicalCode");
		String[] prpLpersonCommerceEndCaseAndRecoverFlag = httpServletRequest.getParameterValues("prpLpersonCommerceEndCaseAndRecoverFlag");
		String[] prpLpersonCommerceTelephoneNo = httpServletRequest.getParameterValues("prpLpersonCommerceTelephoneNo");
		String[] prpLpersonCommerceProsecutorsOffice = httpServletRequest.getParameterValues("prpLpersonCommerceProsecutorsOffice");
		String[] prpLpersonCommerceCourtDoctor = httpServletRequest.getParameterValues("prpLpersonCommerceCourtDoctor");
		String[] prpLpersonCommerceMobilePhone = httpServletRequest.getParameterValues("prpLpersonCommerceMobilePhone");
		String[] prpLpersonCommerceProsecutor = httpServletRequest.getParameterValues("prpLpersonCommerceProsecutor");
		String[] prpLpersonCommerceGarageHeadName = httpServletRequest.getParameterValues("prpLpersonCommerceGarageHeadName");
		String[] prpLpersonCommerceHospitalCode = httpServletRequest.getParameterValues("prpLpersonCommerceHospitalCode");
		String[] prpLpersonCommerceHospitalName = httpServletRequest.getParameterValues("prpLpersonCommerceHospitalName");
		String[] prpLpersonCommerceDoctor = httpServletRequest.getParameterValues("prpLpersonCommerceDoctor");
		String[] prpLpersonCommerceCasualties = httpServletRequest.getParameterValues("prpLpersonCommerceCasualties");
		String[] prpLpersonCommerceChasingLossesStatus = httpServletRequest.getParameterValues("prpLpersonCommerceChasingLossesStatus");
		String[] prpLpersonCommerceIsMarried = httpServletRequest.getParameterValues("prpLpersonCommerceIsMarried");
		//需求變更#121-強制險報送增加健保欄位
		String[] prpLpersonCommerceHealthAmount = httpServletRequest.getParameterValues("prpLpersonCommerceHealthAmount");
		String[] prpLpersonCommerceHealthPoints = httpServletRequest.getParameterValues("prpLpersonCommerceHealthPoints");

		String[] prpLpersonMedicalDetailCode = httpServletRequest.getParameterValues("prpLpersonMedicalDetailCode");
		String[] prpLpersonMedicalDetailName = httpServletRequest.getParameterValues("prpLpersonMedicalDetailName");
		String[] prpLpersonMedicalSumLoss = httpServletRequest.getParameterValues("prpLpersonMedicalSumLoss");
		String[] prpLpersonMedicalRejectSum = httpServletRequest.getParameterValues("prpLpersonMedicalRejectSum");
		String[] prpLpersonMedicalSumDefPay = httpServletRequest.getParameterValues("prpLpersonMedicalSumDefPay");
		String[] prpLpersonMedicalInjuryGrade = httpServletRequest.getParameterValues("prpLpersonMedicalInjuryGrade");
		// //赔付人员序号
		String[] prpLpersonLossPayObjectSerialNo = httpServletRequest.getParameterValues("prpLpersonLossPayObjectSerialNo");
		String[] prpLpersonLossReservedEstimate = httpServletRequest.getParameterValues("prpLpersonLossReservedEstimate");
//		String[] prpLpersonLossAccidentType = httpServletRequest.getParameterValues("prpLpersonLossAccidentType");// delete by chenjie 20150601 需求變更-095
		
		String mdCurrency = ConstantCodes.LOCAL_CURRENCY;
		if (personMedicalSerialNo != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			// 对象赋值
			PrpLpersonLoss prpLpersonLoss = null;
			for (int index = 1; index < personMedicalSerialNo.length; index++) {
				prpLpersonLoss = new PrpLpersonLoss();
				prpLpersonLoss.setPolicyNo(prpLpersonLossPolicyNo);
				prpLpersonLoss.setRiskCode(prpLpersonLossRiskCode);
				prpLpersonLoss.getId().setCompensateNo(prpLpersonLossCompensateNo);
				prpLpersonLoss.getId().setSerialNo(index);
				prpLpersonLoss.setLiabDetailCode(prpLpersonMedicalDetailCode[index]);
				prpLpersonLoss.setLiabDetailName(prpLpersonMedicalDetailName[index]);
				prpLpersonLoss.setInjuryGrade(prpLpersonMedicalInjuryGrade[index]);
				prpLpersonLoss.setSumLoss(Double.parseDouble(DataUtils.nullToZero(prpLpersonMedicalSumLoss[index])));
				prpLpersonLoss.setSumRest(Double.parseDouble(DataUtils.nullToZero(prpLpersonMedicalRejectSum[index])));
				prpLpersonLoss.setCurrency(mdCurrency);
				prpLpersonLoss.setCurrency1(mdCurrency);
				prpLpersonLoss.setCurrency2(mdCurrency);
				prpLpersonLoss.setCurrency4(mdCurrency);
				prpLpersonLoss.setFeeCategory(medicDeathFlag[index]); // medicDeathFlag
				prpLpersonLoss.setSumDefPay(Double.parseDouble(DataUtils.nullToZero(prpLpersonMedicalSumDefPay[index])));
				prpLpersonLoss.setSumRealPay(Double.parseDouble(DataUtils.nullToZero(prpLpersonMedicalSumDefPay[index])));
				prpLpersonLoss.setReservedEstimate(CommonUtils.getValue(prpLpersonLossReservedEstimate,index));
				
//				prpLpersonLoss.setAccidentType(CommonUtils.getValue(prpLpersonLossAccidentType,index));// delete by chenjie 20150601 需求變更-095
				for (int index2 = 1; index2 < prpLpersonCommerceSerialNo.length; index2++) {
					if (personMedicalSerialNo[index].equals(prpLpersonCommerceSerialNo[index2])) {
						prpLpersonLoss.setSex(prpLpersonCommerceSex[index2]);
						prpLpersonLoss.setPersonName(prpLpersonCommercePersonName[index2]);
						prpLpersonLoss.setAge(Integer.parseInt(DataUtils.nullToZero(prpLpersonCommerceAge[index2])));
						prpLpersonLoss.setCurrency3(ConstantCodes.LOCAL_CURRENCY);
						prpLpersonLoss.setPersonNo(index2);
						prpLpersonLoss.setKindCode(ConstantCodes.KINDCODE_D_BZ);
						prpLpersonLoss.setItemKindNo(1);
						if ("".equals(prpLpersonLossDangerNo[index2])) {
							prpLpersonLoss.setDangerNo(1);// 如果没有，就使用默认值为1
						} else {
							prpLpersonLoss.setDangerNo(Integer.parseInt(prpLpersonLossDangerNo[index2]));
						}
						prpLpersonLoss.setFamilyName(prpLpersonCommerceFamilyName[index2]);
						if (!"".equals(prpLpersonCommerceBirthday[index2])) {
							prpLpersonLoss.setBirthday(format.parse(prpLpersonCommerceBirthday[index2]));
						}
						prpLpersonLoss.setIdentityOfInjuredPerson(prpLpersonCommerceIdentityOfInjuredPerson[index2]);
						prpLpersonLoss.setRideSituation(prpLpersonCommerceRideSituation[index2]);
						prpLpersonLoss.setIdentifyNumber(prpLpersonCommerceIdentifyNumber[index2]);
						//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單
						prpLpersonLoss.setIdNumberType(prpLpersonLossIdNumberType[index2]);
						prpLpersonLoss.setMedicalCode(prpLpersonCommerceMedicalCode[index2]);
						prpLpersonLoss.setEndCaseAndRecoverFlag(prpLpersonCommerceEndCaseAndRecoverFlag[index2]);
						prpLpersonLoss.setTelephoneNo(prpLpersonCommerceTelephoneNo[index2]);
						prpLpersonLoss.setProsecutorsOffice(prpLpersonCommerceProsecutorsOffice[index2]);
						prpLpersonLoss.setCourtDoctor(prpLpersonCommerceCourtDoctor[index2]);
						prpLpersonLoss.setMobilePhone(prpLpersonCommerceMobilePhone[index2]);
						prpLpersonLoss.setProsecutor(prpLpersonCommerceProsecutor[index2]);
						prpLpersonLoss.setGarageHeadName(prpLpersonCommerceGarageHeadName[index2]);
						prpLpersonLoss.setHospitalCode(prpLpersonCommerceHospitalCode[index2]);
						prpLpersonLoss.setHospitalName(prpLpersonCommerceHospitalName[index2]);
						prpLpersonLoss.setDoctor(prpLpersonCommerceDoctor[index2]);
						prpLpersonLoss.setCasualties(prpLpersonCommerceCasualties[index2]);
						if (null != prpLpersonLossPayObjectSerialNo[index2] && !"".equals(prpLpersonLossPayObjectSerialNo[index2])) {
							prpLpersonLoss.setPayObjectSerialNo(prpLpersonLossPayObjectSerialNo[index2]);
						}
						prpLpersonLoss.setIsMarried(prpLpersonCommerceIsMarried[index2]);
						prpLpersonLoss.setChasingLossesStatus(prpLpersonCommerceChasingLossesStatus[index2]);
						//需求變更#121-強制險報送增加健保欄位
						prpLpersonLoss.setHealthAmount(Double.parseDouble(DataUtils.nullToZero(prpLpersonCommerceHealthAmount[index2])));
						prpLpersonLoss.setHealthPoints(Double.parseDouble(DataUtils.nullToZero(prpLpersonCommerceHealthPoints[index2])));
						break;
					}
				}
				prpLpersonLossList.add(prpLpersonLoss);
			}
		}
	}

	// 用於初始化页面不计免赔数据
	private void getExceptDeductibleRate(HttpServletRequest request, CompensateDto compensateDto, PolicyDto policyDto, PrpLloss prpLloss, PrpLpersonLoss prpLpersonLoss, ExceptDeductibleRateDto afterDeductibleRateDto) {
		// 获取不计免赔率 、由於有2个车险（老版车险0505和新版车险0506
		// 此次改造只为新版车险）
		List<ExceptDeductibleRateDto> exceptLossList = new ArrayList<ExceptDeductibleRateDto>();
		Map<String, ExceptDeductibleRateDto> exceptLossMap = new HashMap<String, ExceptDeductibleRateDto>();
		double exceptDeuctibleRateSum = 0.0; // 按险别划分总的不计免赔额
		double sumThisExceptDutyPay = 0.0; // 本次总不计免赔金额
		Map<String, String> isExceptLossMap = new HashMap<String, String>();
		List<PrpCitemKind> prpCitemKindList = policyDto.getPrpCitemKindList();
		// 判断该险别是否投保了不计免赔
		for (Iterator<PrpCitemKind> iter = prpCitemKindList.iterator(); iter.hasNext();) {
			PrpCitemKind prpCitemKindDto = iter.next();
			if (!(ConstantCodes.RISKCODE_DAZ.equals(prpCitemKindDto.getRiskCode()))) {
				if (!"M".equals(prpCitemKindDto.getKindCode()) && prpCitemKindDto.getFlag() != null && prpCitemKindDto.getFlag().length() > 4 && "1".equals((prpCitemKindDto.getFlag()).substring(4, 5))) { // 不计免赔特约险别不展示
					isExceptLossMap.put(prpCitemKindDto.getKindCode(), prpCitemKindDto.getFlag());
				}
			}
		}
		// 受损标的不计免赔信息
		List<PrpLloss> prpLlossList = prpLloss.getPrpLlossList();
		ExceptDeductibleRateDto exceptDeductibleRateDto = null;
		for (PrpLloss temp : prpLlossList) {
			exceptDeductibleRateDto = new ExceptDeductibleRateDto();
			String isExceptLoss = isExceptLossMap.get(temp.getKindCode());
			// 判断该险别是否投保了不计免赔
			if (isExceptLoss != null) {
				if ("1".equals(isExceptLoss.substring(4, 5))) {
					exceptDeuctibleRateSum = temp.getExceptDeductiblePay();
					sumThisExceptDutyPay += temp.getExceptDeductiblePay();
					// 如果属於同一险别，做如下处理
					if (exceptLossMap.containsKey(temp.getKindCode())) {
						// ???exceptDeductibleRateDto1 怎么没用到
						// ExceptDeductibleRateDto exceptDeductibleRateDto1 =
						// new ExceptDeductibleRateDto();
						// exceptDeductibleRateDto1 = (ExceptDeductibleRateDto)
						// exceptLossMap.get(temp.getKindCode());
						exceptDeuctibleRateSum = exceptDeductibleRateDto.getExceptDeductibleRatePay();
						exceptDeuctibleRateSum += temp.getExceptDeductiblePay();
					} else {
						exceptDeductibleRateDto.setKindCode(temp.getKindCode());
						exceptDeductibleRateDto.setKindName(temp.getKindName());
						if ((afterDeductibleRateDto.getAfterDeductibleRateMap()).containsKey(temp.getKindCode())) {
							exceptDeductibleRateDto.setExceptDeductibleRate((Double) (afterDeductibleRateDto.getAfterDeductibleRateMap()).get(temp.getKindCode()));
						} else {
							exceptDeductibleRateDto.setExceptDeductibleRate(new Double(temp.getExceptDeductibleRate()));
						}
					}
					exceptDeductibleRateDto.setExceptDeductibleRatePay(exceptDeuctibleRateSum);
					exceptLossMap.put(exceptDeductibleRateDto.getKindCode(), exceptDeductibleRateDto);
				}
			}
		}
		exceptDeuctibleRateSum = 0.0;
		// 赔付人员不计免赔信息
		List<PrpLpersonLoss> prpLpersonLossList = prpLpersonLoss.getPrpLpersonLossList();
		for (PrpLpersonLoss temp : prpLpersonLossList) {
			exceptDeductibleRateDto = new ExceptDeductibleRateDto();
			String isExceptLoss = (String) isExceptLossMap.get(temp.getKindCode());
			// 判断该险别是否投保了不计免赔
			if (isExceptLoss != null) {
				if ("1".equals(isExceptLoss.substring(4, 5))) {
					sumThisExceptDutyPay += temp.getExceptDeductiblePay();
					exceptDeuctibleRateSum = temp.getExceptDeductiblePay();
					// 如果属於同一险种作如下处理
					if (exceptLossMap.containsKey(temp.getKindCode())) {
						// ???exceptDeductibleRateDto1 怎么没用到
						// ExceptDeductibleRateDto exceptDeductibleRateDto1 =
						// new ExceptDeductibleRateDto();
						// exceptDeductibleRateDto1 = (ExceptDeductibleRateDto)
						// exceptLossMap.get(temp.getKindCode());
						exceptDeuctibleRateSum = exceptDeductibleRateDto.getExceptDeductibleRatePay();
						exceptDeuctibleRateSum += temp.getExceptDeductiblePay();
					} else {
						exceptDeductibleRateDto.setKindCode(temp.getKindCode());
						exceptDeductibleRateDto.setKindName(temp.getKindName());
						if ((afterDeductibleRateDto.getAfterDeductibleRateMap()).containsKey(temp.getKindCode())) {
							exceptDeductibleRateDto.setExceptDeductibleRate((Double) (afterDeductibleRateDto.getAfterDeductibleRateMap()).get(temp.getKindCode()));
						} else {
							exceptDeductibleRateDto.setExceptDeductibleRate(new Double(temp.getExceptDeductibleRate()));
						}
					}
					exceptDeductibleRateDto.setExceptDeductibleRatePay(exceptDeuctibleRateSum);
					exceptLossMap.put(exceptDeductibleRateDto.getKindCode(), exceptDeductibleRateDto);
				}
			}
		}
		// 费用信息
		exceptDeuctibleRateSum = 0.0;
		List<PrpLcharge> prpLchargeList = compensateDto.getPrpLchargeList();
		if (exceptLossMap != null && prpLchargeList != null)
			// if (!prpLchargeList.isEmpty()) {
			// for (PrpLcharge temp : prpLchargeList) {
			// exceptDeductibleRateDto = new ExceptDeductibleRateDto();
			// String isExceptLoss = (String)
			// isExceptLossMap.get(temp.getKindCode());
			// if (isExceptLoss != null) {
			// if ("1".equals(isExceptLoss.substring(4, 5))) {
			// if ("A".equals(temp.getKindCode()) ||
			// "B".equals(temp.getKindCode())) {
			// if ("03".equals(temp.getChargeCode())) {
			// sumThisExceptDutyPay += temp.getExceptDeductiblePay();
			// exceptDeuctibleRateSum = temp.getExceptDeductiblePay();
			// if (exceptLossMap.containsKey(temp.getKindCode())) {
			// // ExceptDeductibleRateDto exceptDeductibleRateDto1 = new
			// ExceptDeductibleRateDto();
			// // exceptDeductibleRateDto1 = (ExceptDeductibleRateDto)
			// exceptLossMap.get(temp.getKindCode());
			// exceptDeuctibleRateSum =
			// exceptDeductibleRateDto.getExceptDeductibleRatePay();
			// exceptDeuctibleRateSum += temp.getExceptDeductiblePay();
			// } else {
			// exceptDeductibleRateDto.setKindCode(temp.getKindCode());
			// exceptDeductibleRateDto.setKindName(temp.getKindName());
			// if
			// ((afterDeductibleRateDto.getAfterDeductibleRateMap()).containsKey(temp.getKindCode()))
			// {
			// exceptDeductibleRateDto.setExceptDeductibleRate((Double)
			// (afterDeductibleRateDto.getAfterDeductibleRateMap()).get(temp.getKindCode()));
			// } else {
			// exceptDeductibleRateDto.setExceptDeductibleRate(new
			// Double(temp.getExceptDeductibleRate()));
			// }
			// }
			// exceptDeductibleRateDto.setExceptDeductibleRatePay(exceptDeuctibleRateSum);
			// exceptLossMap.put(exceptDeductibleRateDto.getKindCode(),
			// exceptDeductibleRateDto);
			// }
			// }
			// }
			// }
			// }
			// }
			// 暂存後，将全部不计免赔险别显示出来，已暂存的带值，没有的不带值
			if (exceptLossMap.size() != 0) {
				for (Iterator<ExceptDeductibleRateDto> iter = exceptLossMap.values().iterator(); iter.hasNext();) {
					ExceptDeductibleRateDto exceptDeductibleRateDto1 = (ExceptDeductibleRateDto) iter.next();
					if (!("".equals(exceptDeductibleRateDto1.getKindCode())))
						exceptLossList.add(exceptDeductibleRateDto1);
				}
				request.setAttribute("sumThisExceptDutyPay", Double.toString(sumThisExceptDutyPay));// 送本次总不计免赔金额
				request.setAttribute("exceptLossList", exceptLossList);
			}
	}

	/**
	 * 查询理算中车物损默认带出来的险种
	 * @param prpCitemKindList承保的所有险别
	 * @param kindType，类型，人伤，还是车物损
	 * @return
	 * @throws Exception
	 */
	public String findDefaultKindCode(List<PrpCitemKind> prpCitemKindList, String kindType) throws Exception {
		String kindCode = "";
		if (prpCitemKindList != null && prpCitemKindList.size() > 0) {
			String[] defaultKindCode = new String[6];// 定义险种的默认值
			String kindCodeTemp = null;
			for (int i = 0; i < prpCitemKindList.size(); i++) {
				kindCodeTemp = prpCitemKindList.get(i).getKindCode();
				if ("prpLloss".equals(kindType)) {
					if (kindCodeTemp.equals(ConstantCodes.KINDCODE_A01_32)) {
						defaultKindCode[1] = kindCodeTemp;
					}
					if (BusinessRuleUtil.checkKindType("ThirdCarLoss", kindCodeTemp)) {
						defaultKindCode[2] = kindCodeTemp;
					} else if (BusinessRuleUtil.checkKindType("ThirdPropLoss", kindCodeTemp)) {
						defaultKindCode[3] = kindCodeTemp;
					}
				} else {
					if (kindCodeTemp.equals(ConstantCodes.KINDCODE_A01_31)) {
						defaultKindCode[0] = kindCodeTemp;
					} else if (BusinessRuleUtil.checkKindType("ThirdPersonLoss", kindCodeTemp)) {
						defaultKindCode[4] = kindCodeTemp;
					}
				}
			}
			for (int i = 0; i < defaultKindCode.length; i++) {
				if (defaultKindCode[i] != null && !"".equals(defaultKindCode[i])) {
					kindCode = defaultKindCode[i];
					break;
				}
			}
		}
		return kindCode;
	}

	/**
	 * 设置简易流程，定损讯息
	 * @param compensateDto
	 * @param request
	 * @throws Exception
	 */
	public void setCertainLoss(CompensateDto compensateDto,HttpServletRequest request) throws Exception{
		PrpLclaim prpLclaim = compensateDto.getPrpLclaim();
		String registNo = prpLclaim.getRegistNo();
		QueryRule queryRule = QueryRule.getInstance().addEqual("id.registNo", registNo).addEqual("insureCarFlag","1");
		List<PrpLthirdParty> prpLthirdPartyList = this.prpLthirdPartyService.findPrpLthirdParty(queryRule);
		String lossItemCode = "1";
		if(prpLthirdPartyList.size()>0){
			PrpLthirdParty prpLthirdParty = prpLthirdPartyList.get(0);
			lossItemCode = String.valueOf(prpLthirdParty.getId().getSerialNo());
		}
		CertainLossDto certainLossDto = this.getCertainLossService().findByPrimaryKey(registNo,lossItemCode,"certa");
		PrpLverifyLoss prpLverifyLoss = certainLossDto.getPrpLverifyLoss();
		List<PrpLcarLoss> prpLcarLossList  =  certainLossDto.getPrpLcarLossList();
		HttpSession session = request.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		if(prpLverifyLoss==null){
			prpLverifyLoss = new PrpLverifyLoss();
			prpLverifyLoss.setClaimNo(prpLclaim.getClaimNo());
			prpLverifyLoss.setRiskCode(prpLclaim.getRiskCode());
			prpLverifyLoss.getId().setRegistNo(prpLclaim.getRegistNo());
			prpLverifyLoss.setPolicyNo(prpLclaim.getPolicyNo());
			prpLverifyLoss.setInsuredName(prpLclaim.getInsuredName());
			prpLverifyLoss.setLicenseNo(prpLclaim.getLicenseNo());
			prpLverifyLoss.setClauseType(prpLclaim.getClauseType());
			prpLverifyLoss.getId().setNodeType("certa");
			prpLverifyLoss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
			prpLverifyLoss.setMakeCom(prpLclaim.getMakeCom());
			prpLverifyLoss.setComCode(prpLclaim.getComCode());
			
			prpLverifyLoss.setHandlerCode(user.getUserCode());
			prpLverifyLoss.setHandlerName(user.getUserName());
			prpLverifyLoss.setDefLossDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));
			prpLverifyLoss.setFlag(prpLclaim.getFlag());
			prpLverifyLoss.setStatus("1");
			if (prpLthirdPartyList!=null&&prpLthirdPartyList.size()>0) {
				PrpLthirdParty prpLthirdParty = prpLthirdPartyList.get(0);
				prpLverifyLoss.setLicenseNo(prpLthirdParty.getLicenseNo());
				prpLverifyLoss.setLossItemName(prpLthirdParty.getLicenseNo());
				prpLverifyLoss.setLicenseColorCode(prpLthirdParty.getLicenseColorCode());
				prpLverifyLoss.setCarKindCode(prpLthirdParty.getCarKindCode());
				prpLverifyLoss.setInsureCarFlag(prpLthirdParty.getInsureCarFlag());
				prpLverifyLoss.getId().setLossItemCode(String.valueOf(prpLthirdParty.getId().getSerialNo()));
				PrpLcarLoss prpLcarLoss = new PrpLcarLoss();
				prpLcarLoss.getId().setRegistNo(prpLthirdParty.getId().getRegistNo());
				// 此处需要一个根据报案号码查询关联的赔案号码的转换，管李香要
				prpLcarLoss.setClaimNo(prpLclaim.getClaimNo());
				prpLcarLoss.setRiskCode(prpLthirdParty.getRiskCode());
				prpLcarLoss.getId().setLossItemCode(prpLverifyLoss.getId().getLossItemCode());
				prpLcarLoss.setLossItemName(prpLthirdParty.getLicenseNo());
				prpLcarLoss.setPolicyNo(prpLclaim.getPolicyNo());
				prpLcarLoss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
				prpLcarLoss.setLossDesc("理算條件的描述訊息");
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
				prpLcarLossList.add(prpLcarLoss);
			}
		}
		
		PrpLcomponent prpLcomponent = new PrpLcomponent();
		prpLcomponent.setComponentList(certainLossDto.getPrpLcomponentList());
		PrpLrepairFee prpLrepairFee = new PrpLrepairFee();
		prpLrepairFee.setRepairFeeList(certainLossDto.getPrpLrepairFeeList());
		if(certainLossDto.getPrpLrepairFeeList().size()>0){
			PrpLrepairFee temp = certainLossDto.getPrpLrepairFeeList().get(0);
			prpLrepairFee.setRepairStartDate(new DateTime(temp.getRepairStartDate(),DateTime.YEAR_TO_DAY));
			prpLrepairFee.setRepairEndDate(new DateTime(temp.getRepairEndDate(),DateTime.YEAR_TO_DAY));
			prpLrepairFee.setHandlerCode(temp.getHandlerCode());
			prpLrepairFee.setHandlerName(this.codeService.translateUserCode(temp.getHandlerCode(),true));
			//mantis：CLM0221 ，處理人員：DP0713，需求單編號：新核心-車體險車輛資料完工日期欄位調整 START
			prpLrepairFee.setCompleteDate(new DateTime(temp.getCompleteDate(),DateTime.YEAR_TO_DAY));
			prpLrepairFee.setCourtesyCarUseDates(temp.getCourtesyCarUseDates());
			//mantis：CLM0221 ，處理人員：DP0713，需求單編號：新核心-車體險車輛資料完工日期欄位調整 END
		}else if(certainLossDto.getPrpLcomponentList().size()>0){
			PrpLcomponent temp = certainLossDto.getPrpLcomponentList().get(0);
			prpLrepairFee.setRepairStartDate(new DateTime(temp.getRepairStartDate(),DateTime.YEAR_TO_DAY));
			prpLrepairFee.setRepairEndDate(new DateTime(temp.getRepairEndDate(),DateTime.YEAR_TO_DAY));
			prpLrepairFee.setHandlerCode(temp.getHandlerCode());
			prpLrepairFee.setHandlerName(this.codeService.translateUserCode(temp.getHandlerCode(),true));
			//mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種 START
			prpLrepairFee.setCompleteDate(new DateTime(temp.getCompleteDate(),DateTime.YEAR_TO_DAY));
			prpLrepairFee.setCourtesyCarUseDates(temp.getCourtesyCarUseDates());
			//mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種 END
		}else{
			prpLrepairFee.setHandlerCode(user.getUserCode());
			prpLrepairFee.setHandlerName(user.getUserName());
		}
		for(PrpLcomponent temp : prpLcomponent.getComponentList()){
			temp.setKindName(this.getCodeService().translateKindCode(temp.getRiskCode(), temp.getKindCode(), true));
		}
		for(PrpLrepairFee temp : prpLrepairFee.getRepairFeeList()){
			temp.setKindName(this.getCodeService().translateKindCode(temp.getRiskCode(), temp.getKindCode(), true));
		}
		request.setAttribute("prpLverifyLoss", prpLverifyLoss);
		request.setAttribute("prpLcomponent", prpLcomponent);
		request.setAttribute("prpLrepairFee", prpLrepairFee);
		request.setAttribute("prpLcarLossList", prpLcarLossList);
		
		String strRiskCode = compensateDto.getPrpLcompensate().getRiskCode();
		// 修理廠類型
		request.setAttribute("feeRepairFactoryCodeList", ConstantsCollection.feeRepairFactoryCodeList);
		request.setAttribute("partCodeList", ICollections.getPartCodeList());
		List<PrpDcode> repairTypes = this.codeService.getCodeType("RepairType", strRiskCode);
		request.setAttribute("repairTypes", repairTypes);
		request.setAttribute("ifRemainList", ConstantsCollection.ifRemainList);
	}

	/**
	 * 保存定损时定损页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return stepFlag 标志
	 * @throws Exception
	 */
	public CertainLossDto viewToCertainLossDto(HttpServletRequest httpServletRequest) throws Exception {
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		String userCode = user.getUserCode();
		String userName = user.getUserName();
		CertainLossDto certainLossDto = new CertainLossDto();
		/*---------------------定损主表 PrpLverifyLoss------------------------------------*/
		PrpLverifyLoss prpLverifyLoss = new PrpLverifyLoss();

		prpLverifyLoss.getId().setRegistNo(httpServletRequest.getParameter("prpLverifyLossRegistNo"));
		prpLverifyLoss.setClaimNo(httpServletRequest.getParameter("prpLverifyLossClaimNo"));
		prpLverifyLoss.setRiskCode(httpServletRequest.getParameter("prpLverifyLossRiskCode"));
		prpLverifyLoss.setPolicyNo(httpServletRequest.getParameter("prpLverifyLossPolicyNo"));
		prpLverifyLoss.setInsuredName(httpServletRequest.getParameter("prpLverifyLossInsuredName"));
		prpLverifyLoss.setLicenseNo(httpServletRequest.getParameter("prpLverifyLossLicenseNo"));
		prpLverifyLoss.setLicenseColorCode(httpServletRequest.getParameter("prpLverifyLossLicenseColorcode"));
		prpLverifyLoss.setCarKindCode(httpServletRequest.getParameter("prpLverifyLossCarKindCode"));
		prpLverifyLoss.setCurrency(httpServletRequest.getParameter("prpLverifyLossCurrency"));
		prpLverifyLoss.setSumPreDefLoss(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLverifyLossSumPreDefLoss"))));
		prpLverifyLoss.setSumDefLoss(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLverifyLossSumDefLoss"))));
		prpLverifyLoss.setMakeCom(httpServletRequest.getParameter("prpLverifyLossMakeCom"));
		prpLverifyLoss.setComCode(httpServletRequest.getParameter("prpLverifyLossComCode"));
		prpLverifyLoss.setHandlerCode(httpServletRequest.getParameter("prpLverifyLossHandlerCode"));
		prpLverifyLoss.setHandlerName(httpServletRequest.getParameter("prpLverifyLossHandlerName"));

		prpLverifyLoss.getId().setLossItemCode(httpServletRequest.getParameter("prpLverifyLossLossItemCode"));
		prpLverifyLoss.setLossItemName(httpServletRequest.getParameter("prpLverifyLossLossItemName"));
		prpLverifyLoss.setInsureCarFlag(httpServletRequest.getParameter("prpLverifyLossInsureCarFlag"));
		if (httpServletRequest.getParameter("prpLverifyLossDefLossDate") == null || "".equals(httpServletRequest.getParameter("prpLverifyLossDefLossDate"))) {
			prpLverifyLoss.setDefLossDate(new DateTime(new Date()));
		} else {
			prpLverifyLoss.setDefLossDate(new DateTime(httpServletRequest.getParameter("prpLverifyLossDefLossDate"), DateTime.YEAR_TO_DAY));
		}
		prpLverifyLoss.setUnderWriteCode(httpServletRequest.getParameter("prpLverifyLossUnderWriteCode"));
		prpLverifyLoss.setUnderWriteName(httpServletRequest.getParameter("prpLverifyLossUnderWriteName"));
		// prpLverifyLoss.setUnderWriteEndDate(new DateTime(new Date()));

		prpLverifyLoss.setUnderWriteFlag(httpServletRequest.getParameter("prpLverifyLossUnderWriteFlag"));
		prpLverifyLoss.setRemark(httpServletRequest.getParameter("prpLverifyLossRemark"));
		prpLverifyLoss.setVerifyRemark(httpServletRequest.getParameter("prpLverifyLossVerifyRemark"));
		prpLverifyLoss.setVeriwReturnReason(httpServletRequest.getParameter("prpLverifyLossVeriwReturnReason"));
		prpLverifyLoss.setFlag(httpServletRequest.getParameter("prpLverifyLossFlag"));
		// reason:增加修理厂类型和修理厂名称
		prpLverifyLoss.setRepairFactoryCode(httpServletRequest.getParameter("prpLrepairFeeRepairFactoryCode"));
		prpLverifyLoss.setRepairFactoryName(httpServletRequest.getParameter("prpLrepairFeeRepairFactoryName"));
		// reason: 增加保存理算退回的定损的原有数据的保存,若有数据不会被保存冲掉
		prpLverifyLoss.setCompensateApproverCode(httpServletRequest.getParameter("prpLverifyLossCompensateApproverCode"));
		prpLverifyLoss.setCompensateBackDate(new DateTime((String) httpServletRequest.getParameter("prpLverifyLossCompensateBackDate"), DateTime.YEAR_TO_DAY));
		prpLverifyLoss.setCompensateFlag(httpServletRequest.getParameter("prpLverifyLossCompensateFlag"));
		prpLverifyLoss.setCompensateOpinion(httpServletRequest.getParameter("prpLverifyLossCompensateOpinion"));
		// 定损偏差率
		// 初次定损金额取第一次定损提交的金额
		String saveType = httpServletRequest.getParameter("buttonSaveType");
		double prpLverifyLossFirstDefLoss = Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLverifyLossFirstDefLoss")));
		double prpLverifyLossWarpDefLoss = Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLverifyLossWarpDefLoss")));
		if (prpLverifyLossFirstDefLoss <= 0) {
			prpLverifyLossFirstDefLoss = prpLverifyLossWarpDefLoss;
		}
		if (saveType != null && "4".equals(saveType)) {
			prpLverifyLoss.setFirstDefLoss(prpLverifyLossFirstDefLoss);
			prpLverifyLoss.setWarpDefLoss(prpLverifyLossFirstDefLoss);
		}
		certainLossDto.setPrpLverifyLoss(prpLverifyLoss);

		// certainLossDto.setSwfNotionList(notionList);
		// 人伤、财产定损，没有核损环节，定损提交时即核损通过
		String buttonSaveType = httpServletRequest.getParameter("buttonSaveType");
		String nodeType = "certa";
		// String nodeType = httpServletRequest.getParameter("nodeType");
		// 没有核损设置核损人员和定损人员相同
		if ("4".equals(buttonSaveType)) {
			prpLverifyLoss.setUnderWriteCode(userCode);
			prpLverifyLoss.setUnderWriteName(userName);
			DateTime dateTime = new DateTime(new Date());
			dateTime = new DateTime(dateTime.toString(DateTime.YEAR_TO_DAY)+" 00:00:00",DateTime.YEAR_TO_MINUTE);
			prpLverifyLoss.setUnderWriteEndDate(dateTime);
			prpLverifyLoss.setUnderWriteFlag("1");
		}
		prpLverifyLoss.getId().setNodeType(nodeType);
		/*---------------------状态内容prpLclaimStatus------------------------------------*/
		PrpLclaimStatus prpLclaimStatus = new PrpLclaimStatus();
		prpLclaimStatus.getId().setBusinessNo(httpServletRequest.getParameter("prpLverifyLossRegistNo"));
		prpLclaimStatus.getId().setNodeType(nodeType);
		prpLclaimStatus.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLverifyLoss.getId().getLossItemCode())));
		prpLclaimStatus.setPolicyNo(httpServletRequest.getParameter("prpLverifyLossPolicyNo"));
		prpLclaimStatus.setRiskCode(httpServletRequest.getParameter("prpLverifyLossRiskCode"));
		prpLclaimStatus.setStatus(buttonSaveType);
		prpLclaimStatus.setTypeFlag(prpLverifyLoss.getId().getLossItemCode());
		// 取得当前用户信息，写操作员信息到定损中
		prpLclaimStatus.setHandlerCode(user.getUserCode());
		prpLclaimStatus.setInputDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));
		prpLclaimStatus.setOperateDate(new DateTime(new Date()));
		certainLossDto.setPrpLclaimStatus(prpLclaimStatus);

		/*-------------------------------------损失清单------------------------------------------*/
		String[] prpLcarLossLossItemCode = httpServletRequest.getParameterValues("prpLcarLossLossItemCode");
		String[] prpLcarLossLossItemName = httpServletRequest.getParameterValues("prpLcarLossLossItemName");
		String[] prpLcarLossCurrency = httpServletRequest.getParameterValues("prpLcarLossCurrency");
		String[] prpLcarLossSumRest = httpServletRequest.getParameterValues("prpLcarLossSumRest");
//		String[] prpLcarLossSumManager = httpServletRequest.getParameterValues("prpLcarLossSumManager");
		String[] prpLcarLossSumCertainLoss = httpServletRequest.getParameterValues("prpLcarLossSumCertainLoss");
		String[] prpLcarLossSumVeriRest = httpServletRequest.getParameterValues("prpLcarLossSumVeriRest");
//		String[] prpLcarLossSumVeriManager = httpServletRequest.getParameterValues("prpLcarLossSumVeriManager");
		String[] prpLcarLossSumVerifyLoss = httpServletRequest.getParameterValues("SumDefLoss2");
		String[] prpLcarLossLossDesc = httpServletRequest.getParameterValues("prpLcarLossLossDesc");
		String[] prpLcarLossIndemnityDuty = httpServletRequest.getParameterValues("prpLcarLossIndemnityDuty");
		String[] prpLcarLossIndemnityDutyRate = httpServletRequest.getParameterValues("prpLcarLossIndemnityDutyRate");
		String[] prpLcarLossVeriIndeDutyRate = httpServletRequest.getParameterValues("prpLcarLossVeriIndeDutyRate");
		String[] prpLcarLossRemark = httpServletRequest.getParameterValues("prpLcarLossRemark");
		String[] prpLcarLossOperatorCode = httpServletRequest.getParameterValues("prpLcarLossOperatorCode");
		String[] prpLcarLossApproverCode = httpServletRequest.getParameterValues("prpLcarLossApproverCode");
		String[] prpLcarLossFlag = httpServletRequest.getParameterValues("prpLcarLossFlag");
//		String[] prpLcarLossVINNo = httpServletRequest.getParameterValues("prpLcarLossVINNo");
		String[] prpLcarLossSumManageFeeRate = httpServletRequest.getParameterValues("prpLcarLossSumManageFeeRate");
//		String[] prpLcarLossSumTransFee = httpServletRequest.getParameterValues("prpLcarLossSumTransFee");
//		String[] prpLcarLossSumTax = httpServletRequest.getParameterValues("prpLcarLossSumTax");
//		String[] prpLcarLossSumFloatRate = httpServletRequest.getParameterValues("prpLcarLossSumFloatRate");
		String[] prpLcarLossBackCheckRemark = httpServletRequest.getParameterValues("prpLcarLossBackCheckRemark");
		String[] prpLcarLossLicenseColorCode = httpServletRequest.getParameterValues("prpLcarLossLicenseColorCode");
		String[] prpLcarLossLicenseCarKindCode = httpServletRequest.getParameterValues("prpLcarLossLicenseCarKindCode");
		String[] prpLcarLossLicenseBrandName = httpServletRequest.getParameterValues("prpLcarLossLicenseBrandName");
		String[] prpLcarLossLicenseModelCode = httpServletRequest.getParameterValues("prpLcarLossLicenseModelCode");
		String[] prpLcarLossLicenseFrameNo = httpServletRequest.getParameterValues("prpLcarLossLicenseFrameNo");
		String[] prpLcarLossLicenseVINNo = httpServletRequest.getParameterValues("prpLcarLossLicenseVINNo");
		String[] prpLcarLossInsureCarFlag = httpServletRequest.getParameterValues("prpLcarLossInsureCarFlag");
		List<PrpLcarLoss> prpLcarLossList = new ArrayList<PrpLcarLoss>();
		PrpLcarLoss prpLcarLoss = null;
		String prplCarLossHandlerRange = null;
		int intPrpLcarLossLossItemCode = 0;
		if (prpLcarLossLossItemCode != null) {
			for (int i = 0; i < prpLcarLossLossItemCode.length; i++) {
				prpLcarLoss = new PrpLcarLoss();
				prpLcarLoss.setPolicyNo(prpLverifyLoss.getPolicyNo());
				prpLcarLoss.setRiskCode(prpLverifyLoss.getRiskCode());
				prpLcarLoss.setClaimNo(prpLverifyLoss.getClaimNo());
				prpLcarLoss.getId().setRegistNo(prpLverifyLoss.getId().getRegistNo());
				prpLcarLoss.getId().setLossItemCode(prpLverifyLoss.getId().getLossItemCode());
				// Reason:定损提交核损时，去掉人员选择，增加核损级别
				intPrpLcarLossLossItemCode = Integer.parseInt(prpLcarLoss.getId().getLossItemCode());
				if (intPrpLcarLossLossItemCode > 0) {
					prplCarLossHandlerRange = httpServletRequest.getParameter("prplCarLossHandlerRange");
				}
				prpLcarLoss.setHandlerRange(prplCarLossHandlerRange);
				if (prpLcarLossLossItemName[i] == null || "".equals(prpLcarLossLossItemName[i])) {
					prpLcarLossLossItemName[i] = " ";
				}
				prpLcarLoss.setLossItemName(prpLcarLossLossItemName[i]);
				prpLcarLoss.setCurrency(prpLcarLossCurrency[i]);
				prpLcarLoss.setSumRest(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumRest[i])));
//				prpLcarLoss.setSumManager(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumManager[i])));
				// 增加浮動比例
//				prpLcarLoss.setSumFloatRate(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumFloatRate[i])));

				prpLcarLoss.setSumCertainLoss(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumCertainLoss[i])));
				prpLcarLoss.setSumVeriRest(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumVeriRest[i])));
//				prpLcarLoss.setSumVeriManager(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumVeriManager[i])));
				prpLcarLoss.setSumVerifyLoss(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumVerifyLoss[i])));
				prpLcarLoss.setLossDesc(prpLcarLossLossDesc[i]);
				prpLcarLoss.setIndemnityDuty(prpLcarLossIndemnityDuty[i]);
				prpLcarLoss.setIndemnityDutyRate(Double.parseDouble(DataUtils.nullToZero(prpLcarLossIndemnityDutyRate[i])));
				prpLcarLoss.setVeriIndeDutyRate(Double.parseDouble(DataUtils.nullToZero(prpLcarLossVeriIndeDutyRate[i])));
				prpLcarLoss.setRemark(prpLcarLossRemark[i]);
				prpLcarLoss.setVINNo(prpLcarLossLicenseVINNo[i]);
				prpLcarLoss.setSumManageFeeRate(Double.parseDouble(DataUtils.nullToZero(prpLcarLossSumManageFeeRate[i])));
				prpLcarLoss.setOperatorCode(prpLcarLossOperatorCode[i]);
				prpLcarLoss.setApproverCode(prpLcarLossApproverCode[i]);
				prpLcarLoss.setFlag(prpLcarLossFlag[i]);
				prpLcarLoss.setBackCheckFlag(prpLcarLossBackCheckRemark[i]);
				prpLcarLoss.setLicenseColorCode(prpLcarLossLicenseColorCode[i]);
				prpLcarLoss.setBrandName(prpLcarLossLicenseBrandName[i]);
				prpLcarLoss.setCarKindCode(prpLcarLossLicenseCarKindCode[i]);
				prpLcarLoss.setModelCode(prpLcarLossLicenseModelCode[i]);
				prpLcarLoss.setFrameNo(prpLcarLossLicenseFrameNo[i]);
				prpLcarLoss.setInsureCarFlag(prpLcarLossInsureCarFlag[i]);
				
//				prpLcarLoss.setBackCheckFlag(prpLcarLossBackCheckFlag[i]);
//				prpLcarLoss.setSumTransFee(Double.parseDouble(prpLcarLossSumTransFee[i]));
//				prpLcarLoss.setSumTax(Double.parseDouble(prpLcarLossSumTax[i]));
				// 加入集合
				prpLcarLossList.add(prpLcarLoss);
			}
		}
		certainLossDto.setPrpLcarLossList(prpLcarLossList);
		/*-----------------------------------------零配件更換專案費用清單--------------------------------------------*/
		// 换件项目清单
		List<PrpLcomponent> prpLcomponentList = new ArrayList<PrpLcomponent>();
		PrpLcomponent prpLcomponent = null;
		// reason: 增加保存理算退回的定损的标志的保存,若有数据不会被保存冲掉
		String[] prpLrepairFeeCompensateBackFlag = httpServletRequest.getParameterValues("prpLrepairFeeCompensateBackFlag");
		// reason: 增加保存理算退回的定损的标志的保存,若有数据不会被保存冲掉
		String[] prpLcomponentCompensateBackFlag = httpServletRequest.getParameterValues("prpLcomponentCompensateBackFlag");
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
		//mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種 START
		String prpLcomponentCompleteDate = httpServletRequest.getParameter("prpLcomponentCompleteDate");
		String prpLcomponentCourtesyCarUseDates = httpServletRequest.getParameter("prpLcomponentCourtesyCarUseDates");
		String prpLrepairFeeRepairStartDate_c = httpServletRequest.getParameter("prpLrepairFeeRepairStartDate");
		String prpLrepairFeeRepairEndDate_c = httpServletRequest.getParameter("prpLrepairFeeRepairEndDate");
		//mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種 END
		// 对象赋值
		if (carLossComponentLossItemCode != null) {
			for (int index = 1; index < carLossComponentLossItemCode.length; index++) {
				prpLcomponent = new PrpLcomponent();
				prpLcomponent.setPolicyNo(prpLverifyLoss.getPolicyNo());
				prpLcomponent.setRiskCode(prpLverifyLoss.getRiskCode());
				prpLcomponent.setClaimNo(prpLverifyLoss.getClaimNo());
				prpLcomponent.getId().setRegistNo(prpLverifyLoss.getId().getRegistNo());
				prpLcomponent.getId().setSerialNo(index);
				prpLcomponent.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(prpLcomponentItemKindNo[index])));
				if(CommonUtils.isEmpty(prpLcomponentKindCode[index])){
					prpLcomponent.setKindCode(" ");			
				}else{
					prpLcomponent.setKindCode(prpLcomponentKindCode[index]);
				}
				prpLcomponent.setIndId(DataUtils.nullToZero(prpLcomponentIndId[index]));

				prpLcomponent.getId().setLossItemCode(prpLverifyLoss.getId().getLossItemCode());
				prpLcomponent.setLicenseNo(prpLverifyLoss.getLossItemName());
				prpLcomponent.setLicenseColorCode(prpLverifyLoss.getLicenseColorCode());
				prpLcomponent.setCarKindCode(prpLverifyLoss.getCarKindCode());
				prpLcomponent.setMakeYear(prpLcomponentMakeYear[index]);
				prpLcomponent.setGearboxType(prpLcomponentGearboxType[index]);
				prpLcomponent.setQuoteCompanyGrade(prpLcomponentQuoteCompanyGrade[index]);
				prpLcomponent.setManageFeeRate(Double.parseDouble(DataUtils.nullToZero(prpLcomponentManageFeeRate[index])));
				prpLcomponent.setRepairFactoryCode(prpLcomponentRepairFactoryCode);
				prpLcomponent.setRepairFactoryName(prpLcomponentRepairFactoryName);
				prpLcomponent.setHandlerCode(prpLcomponentHandlerCode);
				//mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種 START
//				prpLcomponent.setRepairStartDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));
//				prpLcomponent.setRepairEndDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));
				prpLcomponent.setRepairStartDate(new DateTime(prpLrepairFeeRepairStartDate_c));
				prpLcomponent.setRepairEndDate(new DateTime(prpLrepairFeeRepairEndDate_c));
				//mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種 END

				prpLcomponent.setSanctioner(prpLcomponentSanctioner[index]);
				prpLcomponent.setApproverCode(prpLcomponentApproverCode[index]);
				prpLcomponent.setOperatorCode(prpLcomponentOperatorCode[index]);
				prpLcomponent.setRepairFactoryFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentRepairFactoryFee[index])));
				if (prpLcomponentPriceType != null) {
					prpLcomponent.setPriceType(prpLcomponentPriceType[index]);
				}
				// Reason:在换件清单中加上损失部位
				prpLcomponent.setPartCode(prpLcomponentPartCode[index]);
				prpLcomponent.setPartName(prpLcomponentPartName[index]);
				//
				prpLcomponent.setOriginalId(prpLcomponentOriginalId[index]);

				prpLcomponent.setSys4SPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentSys4SPrice[index])));
				prpLcomponent.setSysMarketPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentSysMarketPrice[index])));
				prpLcomponent.setSysMatchPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentSysMatchPrice[index])));
				prpLcomponent.setNative4SPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentNative4SPrice[index])));
				prpLcomponent.setNativeMarketPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentNativeMarketPrice[index])));
				prpLcomponent.setNativeMatchPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentNativeMatchPrice[index])));
				prpLcomponent.setVerpCompPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentVerpCompPrice[index])));

				prpLcomponent.setCompName(prpLcomponentCompName[index]);
				if (prpLcomponentCompCode[index] == null || prpLcomponentCompCode[index].trim().length() == 0) {
					prpLcomponent.setCompCode("00");
				} else {
					prpLcomponent.setCompCode(prpLcomponentCompCode[index]);
				}
				prpLcomponent.setQuantity(Integer.parseInt(DataUtils.nullToZero(prpLcomponentQuantity[index])));
				prpLcomponent.setManHourFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentManHourFee[index])));
				prpLcomponent.setMaterialFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentMaterialFee[index])));
				prpLcomponent.setMaterialFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentMaterialFee[index])));

				prpLcomponent.setRestFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentRestFee[index])));
				prpLcomponent.setQueryPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentQueryPrice[index])));
				prpLcomponent.setQuotedPrice(Double.parseDouble(DataUtils.nullToZero(prpLcomponentQuotedPrice[index])));

				prpLcomponent.setLossRate(Double.parseDouble(DataUtils.nullToZero(prpLcomponentLossRate[index])));
				prpLcomponent.setCurrency(prpLcomponentCurrency[index]);
				prpLcomponent.setSumDefLoss(Double.parseDouble(DataUtils.nullToZero(prpLcomponentSumDefLoss[index])));
				prpLcomponent.setRemark(prpLcomponentRemark[index]);
				prpLcomponent.setVeriManHourFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentVeriManHourFee[index])));

				if (prpLcomponentVeriMaterFee != null) {
					prpLcomponent.setVeriMaterFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentVeriMaterFee[index])));
				}
				if (prpLcomponentSumVeriLoss != null) {
					prpLcomponent.setSumVeriLoss(Double.parseDouble(DataUtils.nullToZero(prpLcomponentSumVeriLoss[index])));
				}
				if (prpLcomponentVeriQuantity != null) {
					prpLcomponent.setVeriQuantity(Integer.parseInt(DataUtils.nullToZero(prpLcomponentVeriQuantity[index])));
				}
				if ((prpLcomponentVeriRestFee != null)) {
					prpLcomponent.setVeriRestFee(Double.parseDouble(DataUtils.nullToZero(prpLcomponentVeriRestFee[index])));
				}
				if ((prpLcomponentVeriRemark != null && prpLcomponentVeriRemark.length > carLossComponentLossItemCode.length)) {
					prpLcomponent.setVeriRemark(prpLcomponentVeriRemark[index]);
				}
				prpLcomponent.setVeriLossRate(Double.parseDouble(DataUtils.nullToZero(prpLcomponentVeriLossRate[index])));
				prpLcomponent.setFlag(prpLcomponentFlag[index]);
				prpLcomponent.setIfRemain(DataUtils.nullToZero(prpLcomponentIfRemain[index]));
				prpLcomponent.setCompensateBackFlag(prpLcomponentCompensateBackFlag[index]);
				//mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種 START
				prpLcomponent.setCompleteDate((null!=prpLcomponentCompleteDate&&""!=prpLcomponentCompleteDate)?new DateTime(prpLcomponentCompleteDate):null);
				prpLcomponent.setCourtesyCarUseDates(Integer.parseInt(DataUtils.nullToZero(prpLcomponentCourtesyCarUseDates)));
				//mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種 END
				prpLcomponentList.add(prpLcomponent);
			}
		}
		certainLossDto.setPrpLcomponentList(prpLcomponentList);

		/*-----------------------------------------修理專案費用清單--------------------------------------------*/
		// 修理费用清单
		List<PrpLrepairFee> prpLrepairFeeList = new ArrayList<PrpLrepairFee>();
		PrpLrepairFee prpLrepairFee = null;
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
		String[] prpLrepairFeeIndid = httpServletRequest.getParameterValues("prpLrepairFeeIndId");
		
		//mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核 START
		String prpLrepairFeeCompleteDate = httpServletRequest.getParameter("prpLrepairFeeCompleteDate");
		String prpLrepairFeeCourtesyCarUseDates = httpServletRequest.getParameter("prpLrepairFeeCourtesyCarUseDates");
		//mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核 END

		// 对象赋值
		if (carLossRepairFeeLossItemCode != null) {
			for (int index1 = 1; index1 < carLossRepairFeeLossItemCode.length; index1++) {
				prpLrepairFee = new PrpLrepairFee();
				prpLrepairFee.setPolicyNo(prpLverifyLoss.getPolicyNo());
				prpLrepairFee.setRiskCode(prpLverifyLoss.getRiskCode());
				prpLrepairFee.setClaimNo(prpLverifyLoss.getClaimNo());
				prpLrepairFee.getId().setRegistNo(prpLverifyLoss.getId().getRegistNo());

				prpLrepairFee.getId().setSerialNo(index1);
				prpLrepairFee.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(prpLrepairFeeItemKindNo[index1])));
				prpLrepairFee.getId().setLossItemCode(prpLverifyLoss.getId().getLossItemCode());
				if(CommonUtils.isEmpty(prpLrepairFeeKindCode[index1])){
					prpLrepairFee.setKindCode(" ");			
				}else{
					prpLrepairFee.setKindCode(prpLrepairFeeKindCode[index1]);
				}
				prpLrepairFee.setLicenseNo(prpLverifyLoss.getLicenseNo());
				prpLrepairFee.setLicenseColorCode(prpLverifyLoss.getLicenseColorCode());
				prpLrepairFee.setCarKindCode(prpLverifyLoss.getCarKindCode());
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
				prpLrepairFee.setCompensateBackFlag(prpLrepairFeeCompensateBackFlag[index1]);
				
				//mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核 START
				prpLrepairFee.setCompleteDate((null!=prpLrepairFeeCompleteDate&&""!=prpLrepairFeeCompleteDate)?new DateTime(prpLrepairFeeCompleteDate):null);
				prpLrepairFee.setCourtesyCarUseDates(Integer.parseInt(DataUtils.nullToZero(prpLrepairFeeCourtesyCarUseDates)));
				//mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核 END
				// 加入集合
				prpLrepairFeeList.add(prpLrepairFee);
				// }
			}
			certainLossDto.setPrpLrepairFeeList(prpLrepairFeeList);
		}
		// certainLossDto.setPrpLthirdParty(this.prpLthirdPartyService.findPrpLthirdParty(new
		// PrpLthirdPartyId(prpLverifyLoss.getId().getRegistNo(),
		// Integer.parseInt(prpLverifyLoss.getId().getLossItemCode()))));
		return certainLossDto;
	}
	/**
	 * 保存定损时定损页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return stepFlag 标志
	 * @throws Exception
	 */
	public void viewToCarInsuranceDto(CompensateDto compensateDto,HttpServletRequest httpServletRequest) throws Exception {
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		String prpLcarInsuranceInvoiceDate = httpServletRequest.getParameter("prpLcarInsuranceInvoiceDate");
		Date invoiceDate = null;
		if(!CommonUtils.isEmpty(prpLcarInsuranceInvoiceDate)){
			invoiceDate = new DateTime(prpLcarInsuranceInvoiceDate);
		}
		
		String[] prpLcarInsuranceSerialNo = httpServletRequest.getParameterValues("prpLcarInsuranceSerialNo");
		String[] prpLcarInsuranceWrittenEstimate = httpServletRequest.getParameterValues("prpLcarInsuranceWrittenEstimate");
		String[] prpLcarInsuranceDeductibleInvoice = httpServletRequest.getParameterValues("prpLcarInsuranceDeductibleInvoice");
		/* #083 第三次修改 需求变更 增加憑證類型 */
		String[] prpLcarInsuranceCertificateType = httpServletRequest.getParameterValues("prpLcarInsuranceCertificateType");
		/*
		 * #083 第三次修改 需求变更  刪除開立者統編
		 * String[] prpLcarInsuranceUniformNo = httpServletRequest.getParameterValues("prpLcarInsuranceUniformNo");
		 */
		String[] prpLcarInsuranceCollisionCount = httpServletRequest.getParameterValues("prpLcarInsuranceCollisionCount");
		String[] prpLcarInsuranceRepairUniformNo = httpServletRequest.getParameterValues("prpLcarInsuranceRepairUniformNo");
		String[] prpLcarInsuranceHandlerCode = httpServletRequest.getParameterValues("prpLcarInsuranceHandlerCode");
		String[] prpLcarInsuranceHandlerName = httpServletRequest.getParameterValues("prpLcarInsuranceHandlerName");
		List<PrpLcarInsurance> list = new ArrayList<PrpLcarInsurance>();
		
		if(prpLcarInsuranceSerialNo!=null&&prpLcarInsuranceSerialNo.length>1){
			PrpLcarInsurance prpLcarInsurance = null;
			for(int i=1;i<prpLcarInsuranceSerialNo.length;i++){
				prpLcarInsurance = new PrpLcarInsurance();
				prpLcarInsurance.getId().setCompensateNo(prpLcompensate.getCompensateNo());
				prpLcarInsurance.getId().setSerialNo(i);
				prpLcarInsurance.setClaimNo(prpLcompensate.getClaimNo());
				prpLcarInsurance.setPolicyNo(prpLcompensate.getPolicyNo());
				prpLcarInsurance.setClassCode(prpLcompensate.getClassCode());
				prpLcarInsurance.setRiskCode(prpLcompensate.getRiskCode());
				prpLcarInsurance.setInputDate(prpLcompensate.getInputDate());
				prpLcarInsurance.setWrittenEstimate(prpLcarInsuranceWrittenEstimate[i]);
				prpLcarInsurance.setDeductibleInvoice(prpLcarInsuranceDeductibleInvoice[i]);
				/* #083 第三次修改 需求变更 增加憑證類型 */
				prpLcarInsurance.setCertificateType(prpLcarInsuranceCertificateType[i]);
				
				/**
				 * #083 第三次修改 需求变更 刪除開立者統編
				 * prpLcarInsurance.setUniformNo(prpLcarInsuranceUniformNo[i]);
				 */
				prpLcarInsurance.setCollisionCount(prpLcarInsuranceCollisionCount[i]);
				prpLcarInsurance.setRepairUniformNo(prpLcarInsuranceRepairUniformNo[i]);
				prpLcarInsurance.setInvoiceDate(invoiceDate);
				prpLcarInsurance.setHandlerCode(CommonUtils.getValue(prpLcarInsuranceHandlerCode,i));
				prpLcarInsurance.setHandlerName(CommonUtils.getValue(prpLcarInsuranceHandlerName,i));
				list.add(prpLcarInsurance);
			}
		}
		compensateDto.setPrpLcarInsuranceList(list);
	}
	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public CertainLossService getCertainLossService() {
		return certainLossService;
	}

	public void setCertainLossService(CertainLossService certainLossService) {
		this.certainLossService = certainLossService;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public RecaseService getRecaseService() {
		return recaseService;
	}

	public void setRecaseService(RecaseService recaseService) {
		this.recaseService = recaseService;
	}

	public PrpLdeductCondService getPrpLdeductCondService() {
		return prpLdeductCondService;
	}

	public void setPrpLdeductCondService(PrpLdeductCondService prpLdeductCondService) {
		this.prpLdeductCondService = prpLdeductCondService;
	}

	public DAARegistViewHelper getDaaRegistViewHelper() {
		return daaRegistViewHelper;
	}

	public void setDaaRegistViewHelper(DAARegistViewHelper daaRegistViewHelper) {
		this.daaRegistViewHelper = daaRegistViewHelper;
	}

	public DAAClaimViewHelper getDaaClaimViewHelper() {
		return daaClaimViewHelper;
	}

	public void setDaaClaimViewHelper(DAAClaimViewHelper daaClaimViewHelper) {
		this.daaClaimViewHelper = daaClaimViewHelper;
	}

	public PrpLcfeecoinsService getPrpLcfeecoinsService() {
		return prpLcfeecoinsService;
	}

	public void setPrpLcfeecoinsService(PrpLcfeecoinsService prpLcfeecoinsService) {
		this.prpLcfeecoinsService = prpLcfeecoinsService;
	}

	public PrpLcheckService getPrpLcheckService() {
		return prpLcheckService;
	}

	public void setPrpLcheckService(PrpLcheckService prpLcheckService) {
		this.prpLcheckService = prpLcheckService;
	}

	public PrpClimitService getPrpClimitService() {
		return prpClimitService;
	}

	public void setPrpClimitService(PrpClimitService prpClimitService) {
		this.prpClimitService = prpClimitService;
	}

	public PersonLossService getPersonLossService() {
		return personLossService;
	}

	public void setPersonLossService(PersonLossService personLossService) {
		this.personLossService = personLossService;
	}

	public PrepayService getPrepayService() {
		return prepayService;
	}

	public void setPrepayService(PrepayService prepayService) {
		this.prepayService = prepayService;
	}

	public PrpLverifyLossService getPrpLverifyLossService() {
		return prpLverifyLossService;
	}

	public void setPrpLverifyLossService(PrpLverifyLossService prpLverifyLossService) {
		this.prpLverifyLossService = prpLverifyLossService;
	}

	public CertifyService getCertifyService() {
		return certifyService;
	}

	public void setCertifyService(CertifyService certifyService) {
		this.certifyService = certifyService;
	}

	public PrpDdeductCondService getPrpDdeductCondService() {
		return prpDdeductCondService;
	}

	public void setPrpDdeductCondService(PrpDdeductCondService prpDdeductCondService) {
		this.prpDdeductCondService = prpDdeductCondService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public SwfNotionService getSwfNotionService() {
		return swfNotionService;
	}

	public void setSwfNotionService(SwfNotionService swfNotionService) {
		this.swfNotionService = swfNotionService;
	}

	public PrpCcoinsService getPrpCcoinsService() {
		return prpCcoinsService;
	}

	public void setPrpCcoinsService(PrpCcoinsService prpCcoinsService) {
		this.prpCcoinsService = prpCcoinsService;
	}

	public PrpDriskConfigService getPrpDriskConfigService() {
		return prpDriskConfigService;
	}

	public void setPrpDriskConfigService(PrpDriskConfigService prpDriskConfigService) {
		this.prpDriskConfigService = prpDriskConfigService;
	}

	public PrpLagentService getPrpLagentService() {
		return prpLagentService;
	}

	public void setPrpLagentService(PrpLagentService prpLagentService) {
		this.prpLagentService = prpLagentService;
	}

	public PrpLclaimLossService getPrpLclaimLossService() {
		return prpLclaimLossService;
	}

	public void setPrpLclaimLossService(PrpLclaimLossService prpLclaimLossService) {
		this.prpLclaimLossService = prpLclaimLossService;
	}

	public ReinsServiceManager getReinsServiceManager() {
		return reinsServiceManager;
	}

	public void setReinsServiceManager(ReinsServiceManager reinsServiceManager) {
		this.reinsServiceManager = reinsServiceManager;
	}

	public PrpCitemKindService getPrpCitemKindService() {
		return prpCitemKindService;
	}

	public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
		this.prpCitemKindService = prpCitemKindService;
	}

	public PrplregistrpolicyService getPrpLregistrpolicyService() {
		return prpLregistrpolicyService;
	}

	public void setPrpLregistrpolicyService(PrplregistrpolicyService prpLregistrpolicyService) {
		this.prpLregistrpolicyService = prpLregistrpolicyService;
	}

	public PrpLthirdPartyService getPrpLthirdPartyService() {
		return prpLthirdPartyService;
	}

	public void setPrpLthirdPartyService(PrpLthirdPartyService prpLthirdPartyService) {
		this.prpLthirdPartyService = prpLthirdPartyService;
	}

	public PrpLlossService getPrpLlossService() {
		return prpLlossService;
	}

	public void setPrpLlossService(PrpLlossService prpLlossService) {
		this.prpLlossService = prpLlossService;
	}

	public PrpLpersonLossService getPrpLpersonLossService() {
		return prpLpersonLossService;
	}

	public void setPrpLpersonLossService(PrpLpersonLossService prpLpersonLossService) {
		this.prpLpersonLossService = prpLpersonLossService;
	}

	public PrpLcarInsuranceService getPrpLcarInsuranceService() {
		return prpLcarInsuranceService;
	}

	public void setPrpLcarInsuranceService(PrpLcarInsuranceService prpLcarInsuranceService) {
		this.prpLcarInsuranceService = prpLcarInsuranceService;
	}

}
