package com.sinosoft.claim.compensate.util;

import ins.framework.common.DateTime;
import ins.framework.common.QueryRule;
import ins.framework.utils.DataUtils;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.vo.PolicyDto;
import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.dto.domain.PrpDpersonPayDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schema.model.PrpCitemCar;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLctext;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLperson;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonService;
import com.sinosoft.claim.ui.control.action.UIPrpDpersonPayAction;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.log.Logger;

public class CompensateGenerateImplDubangViewHelper extends CompensateGenerateViewHelper {

	/** Log日志对象 */
	private static Logger logger = Logger.getLogger(CompensateGenerateImplDubangViewHelper.class);

	/** 立案信息服务 */
	private PrpLclaimService prpLclaimService;
	/** 报案服务 */
	private RegistService registService;
	/** 人员伤亡明细信息服务 */
	private PrpLpersonService prpLpersonService;
	/** 批单viewHelper */
	private EndorseViewHelper endorseViewHelper;
	/** 代码服务 */
	private CodeService codeService;
	/** 保单数据传输对象服务 */
	private PolicyService policyService;

	public CompensateGenerateImplDubangViewHelper() {
	}

	/**
	 * 生成机动车险理算报告
	 * @param 无
	 * @throws UserException
	 * @throws Exception
	 */
	public void compensateGenerate(HttpServletRequest httpServletRequest, CompensateDto compensateDto) throws UserException, Exception {
		CompensateData compensateData = new CompensateData();
		compensateDto.setPrpLctextList(new ArrayList<PrpLctext>());
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		String claimNo = prpLcompensate.getClaimNo();
		String strPolicyNo = prpLcompensate.getPolicyNo();
		PrpLclaim prpLclaim = this.getPrpLclaimService().findPrpLclaim(claimNo);
		compensateDto.setPrpLclaim(prpLclaim);

		String strCompensateNo = prpLcompensate.getCompensateNo();
		// 获取标的车车牌号码
		String licenseNo = prpLcompensate.getLicenseNo();
		// 是否为逃逸案件
		compensateData.strEscapeFlag = prpLclaim.getEscapeFlag();
		// 获取出险时间
		// 取得保单的信息
		PrpCitemCar prpCitemCar = new PrpCitemCar();
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		List<PrpCitemCar> prpCitemCarList = this.endorseViewHelper.findPrpCitemCar(policyNo, damageDate, damageHour);
		if (prpCitemCarList != null && !prpCitemCarList.isEmpty()) {
			prpCitemCar = prpCitemCarList.get(0);
		}
		List<String> vecKindCode = new ArrayList<String>();
		// 先初始化A险及B险,再检测该案有无A及B险,没有再删除
		vecKindCode.add("A");
		vecKindCode.add("B");
		vecKindCode.add("D11");
		vecKindCode.add("D12");
		vecKindCode.add("G");
		boolean isHaveRiskA = false;
		boolean isHaveRiskB = false;
		boolean isHaveRiskD11 = false;
		boolean isHaveRiskD12 = false;
		boolean isHaveRiskG = false;
		boolean isHaveOther = false;
		boolean isHaveRiskD = false;
		List<PrpLloss> prpLlossList = compensateDto.getPrpLlossList();
		if (prpLlossList != null && !prpLlossList.isEmpty()) {
			for (PrpLloss prpLloss : prpLlossList) {
				if (DataUtils.emptyToNull(prpLloss.getFamilyName()) == null) {
					// 车牌号码
					prpLloss.setFamilyName(prpLclaim.getLossName());// 增加车牌号码
				}
				if ("A".equals(prpLloss.getKindCode())) {
					isHaveRiskA = true;
					if (prpCitemCar.getPurchasePrice() <= 0) {
						prpCitemCar.setPurchasePrice(prpLloss.getAmount());// ???
					}
				} else if ("B".equals(prpLloss.getKindCode())) {
					isHaveRiskB = true;
				} else if ("G".equals(prpLloss.getKindCode())) {
					isHaveRiskG = true;
				} else if ("D11".equals(prpLloss.getKindCode())) {
					isHaveRiskD11 = true;
				} else if ("D12".equals(prpLloss.getKindCode())) {
					isHaveRiskD12 = true;
				} else if ("AB".equals(prpLloss.getKindCode())) {// 提车保险特殊处理
					// add
					if (licenseNo.equals(prpLloss.getLicenseNo().trim())) {
						isHaveRiskA = true;
					} else {
						isHaveRiskB = true;
					}
				}
				if (!(vecKindCode.contains(prpLloss.getKindCode()))) {
					vecKindCode.add(prpLloss.getKindCode());
				}
			}
		}
		List<PrpLpersonLoss> prpLpersonLossList = compensateDto.getPrpLpersonLossList();
		if (prpLpersonLossList != null && !prpLpersonLossList.isEmpty()) {
			for (PrpLpersonLoss prpLpersonLoss : prpLpersonLossList) {
				if ("A".equals(prpLpersonLoss.getKindCode())) {
					isHaveRiskA = true;
				} else if ("B".equals(prpLpersonLoss.getKindCode())) {
					isHaveRiskB = true;
				} else if ("D11".equals(prpLpersonLoss.getKindCode())) {
					isHaveRiskD11 = true;
				} else if ("D12".equals(prpLpersonLoss.getKindCode())) {
					isHaveRiskD12 = true;
				} else if ("G".equals(prpLpersonLoss.getKindCode())) {
					isHaveRiskG = true;
				} else if ("AB".equals(prpLpersonLoss.getKindCode())) {// 提车保险特殊处理
					isHaveRiskB = true;
				}
				if (!(vecKindCode.contains(prpLpersonLoss.getKindCode()))) {
					vecKindCode.add(prpLpersonLoss.getKindCode());
				}
			}
		}
		if (!isHaveRiskA) {
			vecKindCode.remove("A");
		}
		if (!isHaveRiskB) {
			vecKindCode.remove("B");
		}

		if (!isHaveRiskD11) {
			vecKindCode.remove("D11");
		}

		if (!isHaveRiskD12) {
			vecKindCode.remove("D12");
		}

		if (!isHaveRiskG) {
			vecKindCode.remove("G");
		}
		logger.debug("开始產生各险别的公式及内容");
		for (String kindCode : vecKindCode) {
			if ("A".equals(kindCode)) {
				CarCreateForA(httpServletRequest, compensateDto, compensateData, "A");
			} else if ("B".equals(kindCode)) {
				CarCreateForB(httpServletRequest, compensateDto, compensateData);
			} else if ("D11".equals(kindCode)) {
				CarCreateForD11(httpServletRequest, compensateDto, compensateData);
			} else if ("D12".equals(kindCode)) {
				CarCreateForD12(httpServletRequest, compensateDto, compensateData);
			} else if ("G".equals(kindCode)) {
				CarCreateForG(httpServletRequest, compensateDto, compensateData);
			} else {
				CarCreateForOther(kindCode, httpServletRequest, compensateDto, compensateData);
			}
		}
		List<String> strLctextList = new ArrayList<String>();
		if (!("".equals(compensateData.lineM1)) && !("".equals(compensateData.lineM2))) {
			strLctextList.add("");
			strLctextList.add("不計免賠特約條款：");
			strLctextList.add(space(9) + "實賠金額 =" + (compensateData.lineM1).substring(0, (compensateData.lineM1).length() - 1));
			strLctextList.add(space(18) + "=" + (compensateData.lineM2).substring(0, (compensateData.lineM2).length() - 1));
			strLctextList.add(space(18) + "=" + (new DecimalFormat("#.##").format(compensateData.dblAllExceptDeductiblePay)));
		}
		logger.debug("开始產生本案实赔金额公式及内容");
		strLctextList.add("");
		String lineText = "";
		if (vecKindCode.size() > 0) {
			lineText = "本案實賠金額=";

			for (Iterator<String> iter = vecKindCode.iterator(); iter.hasNext();) {
				String kindCode = iter.next();
				if ("A".equals(kindCode)) {
					lineText += "車損險賠款＋";
				} else if ("B".equals(kindCode)) {
					lineText += "第三者責任險賠款＋";
				} else if ("D11".equals(kindCode) || "D12".equals(kindCode)) {
					if (isHaveRiskD == false) {
						isHaveRiskD = true;
						lineText += "車上人員責任險＋";
					}
				} else if ("G".equals(kindCode)) {
					lineText += "盜搶險＋";
				} else {
					if (isHaveOther != true) {
						isHaveOther = true;
						lineText += "其它附加險賠款＋";
					}
				}

			}
			if (compensateData.dblAllExceptDeductiblePay > 0) {
				lineText += "不計免賠險＋";
			}
			isHaveOther = false;

			if (StringUtils.getBytesLength(lineText) > 14) {
				lineText = lineText.substring(0, (lineText.length() - 1));
			}
			lineText += " - 已預付賠款 ";
			strLctextList.add(lineText);
			lineText = "";
			isHaveRiskD = false;
			for (String kindCode : vecKindCode) {
				if ("A".equals(kindCode)) {
					lineText = space(12) + "=" + formatPay(compensateData.dblCarSumRealPay + compensateData.dblChargeSumRealPay) + "＋";
				} else if ("B".equals(kindCode)) {
					if (lineText == null || lineText.length() == 0) {
						lineText = space(12) + "=" + formatPay(compensateData.dblThirdSumRealPay) + "＋";
					} else {
						lineText += formatPay(compensateData.dblThirdSumRealPay) + "＋";
					}
				} else if ("D11".equals(kindCode) || "D12".equals(kindCode)) {
					if (isHaveRiskD == false) {
						isHaveRiskD = true;
						if (lineText == null || lineText.length() == 0) {
							lineText = space(12) + "=" + formatPay(compensateData.dblCarPersonSumRealpay) + "＋";
						} else {
							lineText += formatPay(compensateData.dblCarPersonSumRealpay) + "＋";
						}
					}
				} else if ("G".equals(kindCode)) {
					if (lineText == null || lineText.length() == 0)
						lineText = space(12) + "=" + formatPay(compensateData.dblCarStealSumRealPay) + "＋";
					else
						lineText += formatPay(compensateData.dblCarStealSumRealPay) + "＋";
				} else {
					if (isHaveOther != true) {
						if (lineText == null || lineText.length() == 0) {
							isHaveOther = true;
							lineText = space(12) + "=" + formatPay(compensateData.dblOthSumRealPay + compensateData.dblNoFranchise) + "＋";
						} else {
							isHaveOther = true;
							lineText += formatPay(compensateData.dblOthSumRealPay + compensateData.dblNoFranchise) + "＋";
						}
					}
				}
			}
			if (new Double(compensateData.dblAllExceptDeductiblePay).intValue() != 0) {
				if (lineText == null || lineText.length() == 0)
					lineText = space(12) + "=" + formatPay(compensateData.dblAllExceptDeductiblePay) + "＋";
				else
					lineText += formatPay(compensateData.dblAllExceptDeductiblePay) + "＋";
			}

			if (StringUtils.getBytesLength(lineText) > 14) {
				lineText = lineText.substring(0, (lineText.length() - 1));
			}
			lineText += " - " + compensateDto.getPrpLcompensate().getSumPrePaid();
			strLctextList.add(lineText);
			lineText = "            " + "=" + formatPay(compensateData.dblAllSumRealPay - compensateDto.getPrpLcompensate().getSumPrePaid()) + "元";
		}
		strLctextList.add(lineText);
		List<PrpLctext> prpLctextList = compensateDto.getPrpLctextList();
		int ctextcount = 0;
		if (prpLctextList != null && !prpLctextList.isEmpty()) {
			ctextcount = prpLctextList.size();
		}
		PrpLctext prpLctext = null;
		for (String lineTextTemp : strLctextList) {
			prpLctext = new PrpLctext();
			prpLctext.getId().setCompensateNo(strCompensateNo);
			prpLctext.getId().setTextType("1");
			prpLctext.getId().setLineNo(ctextcount++);
			prpLctext.setContext(lineTextTemp);
			prpLctext.setFlag("");
			compensateData.prpLctextlist.add(prpLctext);
		}
		prpLctext = new PrpLctext();
		prpLctext.setPrpLctextList(compensateData.prpLctextlist);
		httpServletRequest.setAttribute("prpLctext", prpLctext);
	}

	/**
	 * 生成车损险的公式及内容
	 * @param httpServletRequest
	 * @param compensateDto
	 * @throws UserException
	 * @throws Exception
	 */
	public void CarCreateForA(HttpServletRequest httpServletRequest, CompensateDto compensateDto, CompensateData compensateData, String flag) throws UserException, Exception {
		PrpCitemCar prpCitemCar = new PrpCitemCar();
		PrpLloss prpLlossbak = new PrpLloss();
		double dblcarsumrealpay = 0;// 实赔金额总和
		double dbdeductible = 0;// 可选免配额
		double dbCompelPay = 0;// 交强险赔款
		double dblsumrest = 0;// 残值
		double factValue = 0d;
		double dblsumDefPay = 0;// 核定赔偿金额计算用值
		double exceptDeductiblePayA = 0.0;// 不计免赔赔款
		double dbExceptDeductiblePayA = 0.0;// 不计免赔赔款合计
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		String strCompensateNo = prpLcompensate.getCompensateNo();
		// 获取uticodeTransfer对应险别的configCode值
		String configCode = this.getCodeService().translateRiskCodetoConfigCode(prpLcompensate.getRiskCode());
		// 读取标的车车牌号码
		String licenseNo = prpLcompensate.getLicenseNo();
		String dedutibleAall = httpServletRequest.getParameter("prpLDeductible");
		if (DataUtils.emptyToNull(dedutibleAall) == null) {
			dedutibleAall = "0";
		}
		dbdeductible = Double.parseDouble(dedutibleAall);
		// ==============取得保单的信息
		PrpLclaim prpLclaim = compensateDto.getPrpLclaim();
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		List<PrpCitemCar> prpCitemCarList = this.getEndorseViewHelper().findPrpCitemCar(policyNo, damageDate, damageHour);
		if (prpCitemCarList != null && !prpCitemCarList.isEmpty()) {
			prpCitemCar = prpCitemCarList.get(0);
		}
		// 投保新车购置价
		double purchasePrice = prpCitemCar.getPurchasePrice();
		// 是否全损
		String isLossAll = httpServletRequest.getParameter("prpLlossDtoIsLossAll");
		// 出险时的新车购置价
		List<String> strLctextList = new ArrayList<String>();
		String lineText = "";
		List<PrpCitemKind> limitlist = this.getEndorseViewHelper().findPrpCitemKind(policyNo, damageDate, damageHour, null , null);
		// 取保额
		double amount = 0.0;
		if (limitlist != null && limitlist.size() > 0) {
			Iterator<PrpCitemKind> it = limitlist.iterator();
			PrpCitemKind prpCitemKind = null;
			while (it.hasNext()) {
				prpCitemKind = it.next();
				if ("A".equals(prpCitemKind.getKindCode())) {
					amount = prpCitemKind.getAmount();
					break;
				} else if ("AB".equals(prpCitemKind.getKindCode())) {// 提车保险车损部分保险金额获取2008-04-15
					amount = prpCitemKind.getAmount() - prpCitemKind.getValue();
					break;
				}
			}
		}
		if ("RISKCODE_DTC".equals(configCode) || "RISKCODE_DAS".equals(configCode)) {
			// 提车保险特殊处理
			strLctextList.add("提車保險車輛損失險：");
		} else {
			strLctextList.add("車輛損失險：");
		}
		List<PrpLloss> prpLlossList = compensateDto.getPrpLlossList();
		if (prpLlossList != null && !prpLlossList.isEmpty()) {
			for (PrpLloss prpLloss : prpLlossList) {
				if ("A".equals(prpLloss.getKindCode())) {
					exceptDeductiblePayA = exceptDeductiblePayA + prpLloss.getExceptDeductiblePay();
					factValue = prpLloss.getCarRealValue();
					String strRiskType = this.getCodeService().translateRiskCodetoRiskType(prpLloss.getRiskCode());
					if ("A".equals(prpLloss.getKindCode())) {
						if ("D".equals(strRiskType) && "27".equals(DataUtils.dbNullToEmpty(prpLloss.getFeeTypeCode()).trim())) {
							continue;
						}
						dblsumDefPay = prpLloss.getSumDefPay();
						dbCompelPay = prpLloss.getCompelPay();
						dblsumrest = prpLloss.getSumRest();
						dblcarsumrealpay = prpLloss.getSumRealPay();
						exceptDeductiblePayA = prpLloss.getExceptDeductiblePay();
						dbExceptDeductiblePayA += exceptDeductiblePayA;
						prpLlossbak = new PrpLloss();
						PropertyUtils.copyProperties(prpLlossbak, prpLloss);
					}
				}
				if ("AB".equals(prpLloss.getKindCode()) && licenseNo.equals(prpLloss.getLicenseNo().trim())) {
					exceptDeductiblePayA = exceptDeductiblePayA + prpLloss.getExceptDeductiblePay();
					factValue = prpLloss.getCarRealValue();
					String strRiskType = this.getCodeService().translateRiskCodetoRiskType(prpLloss.getRiskCode());
					if ("AB".equals(prpLloss.getKindCode())) {
						if ("D".equals(strRiskType) && prpLloss.getFeeTypeCode().trim().equals("27")) {
							continue;
						}
						dblsumDefPay = prpLloss.getSumDefPay();
						dbCompelPay = prpLloss.getCompelPay();
						dblsumrest = prpLloss.getSumRest();
						dblcarsumrealpay = prpLloss.getSumRealPay();
						exceptDeductiblePayA = prpLloss.getExceptDeductiblePay();
						dbExceptDeductiblePayA += exceptDeductiblePayA;
						prpLlossbak = new PrpLloss();
						PropertyUtils.copyProperties(prpLlossbak, prpLloss);
					}
				}
			}
		}
		if ("Y".equals(isLossAll)) {
			if ("RISKCODE_DTC".equals(configCode) || "RISKCODE_DAS".equals(configCode)) {
				// 提车保险特殊处理
				lineText = space(8) + "賠款=(實際價值-強制險賠款";
			} else {
				if (factValue >= amount) {
					lineText = space(8) + "賠款=(保險金額-強制險賠款";
				} else {
					lineText = space(8) + "賠款=(實際價值-強制險賠款";
				}
			}
		} else {
			lineText = space(8) + "賠款=(核定賠償金額-強制險賠款";
		}
		if (StringUtils.getBytesLength(lineText) + 8 > 60) {
			lineText = lineText.substring(0, (lineText.length()));
			strLctextList.add(lineText);
			lineText = "-殘值)"; // LYM 不用残值计算
		} else {
			lineText = lineText.substring(0, (lineText.length()));
			lineText += "-殘值)"; // LYM 不用残值计算
		}
		if (!("Y".equals(isLossAll)) && purchasePrice > amount) {
			if (StringUtils.getBytesLength(lineText) + 10 > 60) {
				strLctextList.add(lineText);
				lineText = space(17) + "×(保險金額/新車購置價)";
			} else {
				lineText += "×(保險金額/新車購置價)";
			}
		}
		lineText += "×事故責任比例";
		if (StringUtils.getBytesLength(lineText) + 11 > 60) {
			strLctextList.add(lineText);
			lineText = space(17) + "×(1－免賠率之和)";
		} else {
			lineText += "×(1－免賠率之和)";
		}
		if (StringUtils.getBytesLength(lineText) + 11 > 60) {
			strLctextList.add(lineText);
			lineText = space(17) + "－免賠額";
			strLctextList.add(lineText);
		} else {
			lineText += "－免賠額";
			strLctextList.add(lineText);
		}

		lineText = space(16) + "=(";

		if ("Y".equals(isLossAll)) {
			if ("RISKCODE_DTC".equals(configCode) || "RISKCODE_DAS".equals(configCode)) {
				// 提车保险特殊处理
				dblsumDefPay = factValue;
			} else {
				if (factValue >= amount) {
					dblsumDefPay = amount;
				} else {
					dblsumDefPay = factValue;
				}
			}
			if (StringUtils.getBytesLength(lineText) + 3 > 60) {
				strLctextList.add(lineText);
				lineText = space(17) + formatPay(dblsumDefPay);
			} else {
				lineText += formatPay(dblsumDefPay);
			}
		} else {
			if (StringUtils.getBytesLength(lineText) + 3 > 60) {
				strLctextList.add(lineText);
				lineText = space(17) + formatPay(dblsumDefPay);
			} else {
				lineText += formatPay(dblsumDefPay);
			}
		}
		if (StringUtils.getBytesLength(lineText) + 8 > 60) {
			strLctextList.add(lineText);
			lineText = space(17) + "-" + formatPay(dbCompelPay); // LYM
			// 不用残值计算
		} else {
			lineText += "-" + formatPay(dbCompelPay); // LYM 不用残值计算
		}

		if (StringUtils.getBytesLength(lineText) + 8 > 60) {
			strLctextList.add(lineText);
			lineText = space(17) + "-" + formatPay(dblsumrest) + ")"; // LYM
			// 不用残值计算
		} else {
			lineText += "-" + formatPay(dblsumrest) + ")"; // LYM 不用残值计算
		}
		dblsumDefPay = dblsumDefPay - dbCompelPay - dblsumrest;
		if (!("Y".equals(isLossAll)) && purchasePrice > amount) {
			if (StringUtils.getBytesLength(lineText) + 8 > 60) {
				strLctextList.add(lineText);
				lineText = space(17) + "×(" + formatPay(amount) + "/" + formatPay(purchasePrice) + ")"; // LYM
				// 不用残值计算
			} else {
				lineText += "×(" + formatPay(amount) + "/" + formatPay(purchasePrice) + ")"; // LYM
				// 不用残值计算
			}
			dblsumDefPay = dblsumDefPay * (amount / purchasePrice);
		}
		if (StringUtils.getBytesLength(lineText) + 8 > 60) {
			strLctextList.add(lineText);
			lineText = space(17) + "×" + formatPay(prpLlossbak.getIndemnityDutyRate()) + "％";
		} else {
			lineText += "×" + formatPay(prpLlossbak.getIndemnityDutyRate()) + "％";
		}
		if (StringUtils.getBytesLength(lineText) + 10 > 60) {
			strLctextList.add(lineText);
			lineText = space(17) + "×(1-" + formatPay(prpLlossbak.getDutyDeductibleRate() + prpLlossbak.getDeductiblerate()) + "％)";

		} else {
			lineText += "×(1-" + formatPay(prpLlossbak.getDutyDeductibleRate() + prpLlossbak.getDeductiblerate()) + "％)";

		}
		if (StringUtils.getBytesLength(lineText) + 10 > 60) {
			strLctextList.add(lineText);
			lineText = space(17) + "-" + formatPay(dbdeductible);
			strLctextList.add(lineText);
		} else {
			lineText += "-" + formatPay(dbdeductible);
			strLctextList.add(lineText);
		}
		dblsumDefPay = dblsumDefPay * (prpLlossbak.getIndemnityDutyRate() / 100) * (1 - (prpLlossbak.getDutyDeductibleRate() / 100 + prpLlossbak.getDeductiblerate() / 100)) - dbdeductible;

		lineText = space(16) + "=" + formatPay(dblsumDefPay) + "元";
		strLctextList.add(lineText);
		lineText = "";
		if (dblsumDefPay > factValue && !("Y".equals(isLossAll))) {
			strLctextList.add(lineText);
			lineText = space(4) + "賠款超出保險車輛實際價值,故賠款按以下計算:";
			strLctextList.add(lineText);
			lineText = space(8) + "賠付金額 = (保險車輛實際價值-強制險賠款";
			if (StringUtils.getBytesLength(lineText) + 8 > 60) {
				lineText = lineText.substring(0, (lineText.length())); // -
				// 1));
				strLctextList.add(lineText);
				lineText = "-殘值)"; // LYM 不用残值计算
			} else {
				lineText = lineText.substring(0, (lineText.length())); // -
				// 1));
				lineText += "-殘值)"; // LYM 不用残值计算
			}
			if (!("Y".equals(isLossAll)) && purchasePrice > amount) {
				if (StringUtils.getBytesLength(lineText) + 10 > 60) {
					strLctextList.add(lineText);
					lineText = space(17) + "×(保險金額/新車購置價)";
				} else {
					lineText += "×(保險金額/新車購置價)";
				}
			}
			lineText += "×事故責任比例";

			if (StringUtils.getBytesLength(lineText) + 11 > 60) {
				strLctextList.add(lineText);
				lineText = space(17) + "×(1－免賠率之和)";
			} else {
				lineText += "×(1－免賠率之和)";
			}
			if (StringUtils.getBytesLength(lineText) + 11 > 60) {
				strLctextList.add(lineText);
				lineText = space(17) + "－免賠額";
				strLctextList.add(lineText);
			} else {
				lineText += "－免賠額";
				strLctextList.add(lineText);
			}
			lineText = space(16) + "=(";
			if (StringUtils.getBytesLength(lineText) + 3 > 60) {
				strLctextList.add(lineText);
				lineText = space(17) + formatPay(factValue);
			} else {
				lineText += formatPay(factValue);
			}
			if (StringUtils.getBytesLength(lineText) + 8 > 60) {
				strLctextList.add(lineText);
				lineText = space(17) + "-" + formatPay(dbCompelPay); // LYM
				// 不用残值计算
			} else {
				lineText += "-" + formatPay(dbCompelPay); // LYM 不用残值计算
			}

			if (StringUtils.getBytesLength(lineText) + 8 > 60) {
				strLctextList.add(lineText);
				lineText = space(17) + "-" + formatPay(dblsumrest) + ")"; // LYM
				// 不用残值计算
			} else {
				lineText += "-" + formatPay(dblsumrest) + ")"; // LYM 不用残值计算
			}

			if (!("Y".equals(isLossAll)) && purchasePrice > amount) {
				if (StringUtils.getBytesLength(lineText) + 8 > 60) {
					strLctextList.add(lineText);
					lineText = space(17) + "×(" + formatPay(amount) + "/" + formatPay(purchasePrice) + ")"; // LYM
					// 不用残值计算
				} else {
					lineText += "×(" + formatPay(amount) + "/" + formatPay(purchasePrice) + ")"; // LYM
					// 不用残值计算
				}
			}
			if (StringUtils.getBytesLength(lineText) + 8 > 60) {
				strLctextList.add(lineText);
				lineText = space(17) + "×" + formatPay(prpLlossbak.getIndemnityDutyRate()) + "％";
			} else {
				lineText += "×" + formatPay(prpLlossbak.getIndemnityDutyRate()) + "％";
			}
			if (StringUtils.getBytesLength(lineText) + 10 > 60) {
				strLctextList.add(lineText);
				lineText = space(17) + "×(1-" + formatPay(prpLlossbak.getDutyDeductibleRate() + prpLlossbak.getDeductiblerate()) + "％)";
			} else {
				lineText += "×(1-" + formatPay(prpLlossbak.getDutyDeductibleRate() + prpLlossbak.getDeductiblerate()) + "％)";
			}
			if (StringUtils.getBytesLength(lineText) + 10 > 60) {
				strLctextList.add(lineText);
				lineText = space(17) + "-" + formatPay(dbdeductible);
				strLctextList.add(lineText);
			} else {
				lineText += "-" + formatPay(dbdeductible);
				strLctextList.add(lineText);
			}
			lineText = space(16) + "=" + formatPay(dblcarsumrealpay) + "元";
			strLctextList.add(lineText);
		}
		/**
		 * 开始进行施救费的计算
		 */
		int chargecount = 0;
		int j = 0;
		double dblchargesumrealpay = 0;
		double dblRescuesumrealpay = 0;
		PrpLcharge[] arrPrpLcharge = new PrpLcharge[50];
		// 首先对费用信息进行合並，将同险别同费用类别同币别的进行分类
		List<PrpLcharge> prpLchargeList = compensateDto.getPrpLchargeList();
		if (prpLchargeList != null && !prpLchargeList.isEmpty()) {
			for (PrpLcharge prpLcharge : prpLchargeList) {
				if ("A".equals(prpLcharge.getKindCode()) || "AB".equals(prpLcharge.getKindCode())) {
					// 施救费要按车险公式进行计算
//					if ("03".equals(prpLcharge.getChargeCode())) {
//						dblchargesumrealpay += prpLcharge.getSumRealPay();
//						dbExceptDeductiblePayA += prpLcharge.getExceptDeductiblePay();
//					}
					// 将费用按险别分类进行统计
					for (j = 1; j <= chargecount; j++) {
						if (arrPrpLcharge[j].getChargeCode().equals(prpLcharge.getChargeCode()) && arrPrpLcharge[j].getCurrency().equals(prpLcharge.getCurrency())) {
							arrPrpLcharge[j].setChargeReport(arrPrpLcharge[j].getChargeReport() + prpLcharge.getChargeReport());
							arrPrpLcharge[j].setChargeAmount(arrPrpLcharge[j].getChargeAmount() + prpLcharge.getChargeAmount());
							arrPrpLcharge[j].setSumRealPay(arrPrpLcharge[j].getSumRealPay() + prpLcharge.getSumRealPay());
							break;
						}
					}
					if (j > chargecount) {
						chargecount++;
						if (arrPrpLcharge[chargecount] == null) {
							arrPrpLcharge[chargecount] = new PrpLcharge();
						}
						PropertyUtils.copyProperties(arrPrpLcharge[chargecount], prpLcharge);
					}
				}
			}
		}
//		if (chargecount > 0) {
//			for (int i = 1; i <= chargecount; i++) {
//				// 仅计算车损险的施救费
//				if ("03".equals(arrPrpLcharge[i].getChargeCode())) {
//					// 开始生成施救费的计算公式；
//					dblRescuesumrealpay = arrPrpLcharge[i].getChargeReport();
//					lineText = space(8) + "施救費=施救費用金額×承保比例×責任比例" + "×(1－免賠率之和)";
//					strLctextList.add(lineText);
//					lineText = space(16) + "=" + formatPay(dblRescuesumrealpay) + "×" + formatPay(prpLlossbak.getClaimRate()) + "％" + "×" + formatPay(prpLlossbak.getIndemnityDutyRate()) + "％" + "×(1-"
//							+ formatPay(prpLlossbak.getDutyDeductibleRate() + prpLlossbak.getDeductiblerate()) + "％)";
//					strLctextList.add(lineText);
//					dblRescuesumrealpay = dblRescuesumrealpay * prpLlossbak.getClaimRate() / 100 * prpLlossbak.getIndemnityDutyRate() / 100 * (1 - (prpLlossbak.getDutyDeductibleRate() + prpLlossbak.getDeductiblerate()) / 100);
//					lineText = space(16) + "=" + formatPay(arrPrpLcharge[i].getSumRealPay()) + "元";
//					strLctextList.add(lineText);
//				}
//			}
//		}
		compensateData.dblChargeSumRealPay += dblchargesumrealpay;
		compensateData.dblAllSumRealPay += dblchargesumrealpay;
		compensateData.dblCarSumRealPay += dblcarsumrealpay;
		compensateData.dblAllSumRealPay += dblcarsumrealpay + dbExceptDeductiblePayA;
		compensateData.dblAllExceptDeductiblePay += dbExceptDeductiblePayA;
		if (new Double(dbExceptDeductiblePayA).intValue() != 0) {
			compensateData.lineM1 += "車損險不計免賠額+";
			compensateData.lineM2 += new Double(dbExceptDeductiblePayA).toString() + "+";
		}
		List<PrpLctext> prpLctextList = compensateDto.getPrpLctextList();
		int ctextcount = 0;
		if (prpLctextList != null && !prpLctextList.isEmpty()) {
			ctextcount = prpLctextList.size();
		}
		PrpLctext prpLctext = null;
		for (String lineTextTemp : strLctextList) {
			prpLctext = new PrpLctext();
			prpLctext.getId().setCompensateNo(strCompensateNo);
			prpLctext.getId().setTextType("1");
			prpLctext.getId().setLineNo(ctextcount++);
			prpLctext.setContext(lineTextTemp);
			prpLctext.setFlag("");
			compensateData.prpLctextlist.add(prpLctext);
		}
	}

	/**
	 * 生成三者险的理算公式及报告
	 * @param httpServletRequest
	 * @param compensateDto
	 * @throws UserException
	 * @throws Exception
	 */
	public void CarCreateForB(HttpServletRequest httpServletRequest, CompensateDto compensateDto, CompensateData compensateData) throws UserException, Exception {

		PrpLloss prpLlossbak = new PrpLloss();
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		int licensenocount = 0;
		int personcount = 0;
		int textlosscount = 0;
		int textpersoncount = 0;
		int j = 0;
		int intpropflag = 0;
		int intCarForBflag = 0;
		double dblsumloss = 0;
		double dblsumrest = 0;
		double dbldeductiblerate = 0;
		double dblDutydeductiblerate = 0;
		double dblthirdsumrealpay = 0;
		double dblthirdsumrealpayAll = 0;
		double dblthirdsumdefpay = 0;
		double dblthirdsumdefpayAll = 0;
		double dblthirdCompelPay = 0d;
		double dblamount = 0;
		double dbExceptDeductiblePayB = 0.0;// 总的不计免赔额
		String[] arrKindCode = new String[20];
		String[] arrLicenseNo = new String[20];
		String[] arrKindCode1 = new String[20];
		String[] arrPersonNo = new String[20];
		String[] arrPersonName = new String[20];
		String[] arrFamilyName = new String[20];
		String[] lossName = new String[20];
		String strCompensateNo = prpLcompensate.getCompensateNo();
		PrpLpersonLoss prpLpersonLossDtobak = new PrpLpersonLoss();
		PrpLcharge[] arrPrpLcharge = new PrpLcharge[50];
		// //获取uticodeTransfer对应险别的configCode值
		String configCode = this.getCodeService().translateRiskCodetoConfigCode(prpLcompensate.getRiskCode());
		// 读取标的车车牌号码
		String licenseNo = prpLcompensate.getLicenseNo();
		// 取B限额---------------------------
		PolicyDto policyDto = this.getPolicyService().findByPrimaryKey(prpLcompensate.getPolicyNo());
		List<PrpCitemKind> limitlist = policyDto.getPrpCitemKindList();
		// 取限额
		if (limitlist != null && limitlist.size() > 0) {
			Iterator<PrpCitemKind> it = limitlist.iterator();
			while (it.hasNext()) {
				PrpCitemKind prpCitemKind = it.next();
				if ("B".equals(prpCitemKind.getKindCode())) {
					dblamount = prpCitemKind.getAmount();
					break;
				} else if ("AB".equals(prpCitemKind.getKindCode())) {
					// 提车保险车损部分保险金额获取
					dblamount = prpCitemKind.getValue();
					break;
				}
			}
		}
		// 循环遍历赔付标的表
		List<PrpLloss> prpLlossList = compensateDto.getPrpLlossList();
		if (prpLlossList != null && !prpLlossList.isEmpty()) {
			for (PrpLloss prpLloss : prpLlossList) {
				for (j = 1; j <= licensenocount; j++) {
					if (arrKindCode[j].indexOf(prpLloss.getKindCode()) < 0 && prpLloss.getLicenseNo().equals(arrLicenseNo[j].trim()))
						arrKindCode[j] = arrKindCode[j].trim() + prpLloss.getKindCode();
					if (prpLloss.getLicenseNo().equals(arrLicenseNo[j].trim()))
						break;
				}
				if (j > licensenocount && DataUtils.emptyToNull(prpLloss.getLicenseNo()) != null) {
					if ("AB".equals(prpLloss.getKindCode()) && !prpLloss.getLicenseNo().equals(compensateDto.getPrpLcompensate().getLicenseNo())) {
						licensenocount++;
						lossName[licensenocount] = prpLloss.getLossName();
						arrLicenseNo[licensenocount] = prpLloss.getLicenseNo();
						arrKindCode[licensenocount] = prpLloss.getKindCode();
					} else if (!"AB".equals(prpLloss.getKindCode())) {
						licensenocount++;
						lossName[licensenocount] = prpLloss.getLossName();
						arrLicenseNo[licensenocount] = prpLloss.getLicenseNo();
						arrKindCode[licensenocount] = prpLloss.getKindCode();
					}
				}
				if ((DataUtils.emptyToNull(prpLloss.getLicenseNo()) == null) && prpLloss.getLossName() != null && ("B".equals(prpLloss.getKindCode()) || "AB".equals(prpLloss.getKindCode()))) {
					// 提车保险车损部分保险金额获取
					intpropflag = 1;
				}
				if (DataUtils.emptyToNull(prpLloss.getLicenseNo()) != null && "B".equals(prpLloss.getKindCode())
						&& !("".equals(prpLloss.getLicenseNo()) || (prpLloss.getLicenseNo() != null && !prpLloss.getLicenseNo().equals(compensateDto.getPrpLcompensate().getLicenseNo()) && "AB".equals(prpLloss.getKindCode())))) {
					// 提车保险车损部分保险金额获取
					intCarForBflag = 1;
				}
			}
		}
		// 循环遍历费用信息表
		int chargecount = 0;
		double dblchargesumrealpay = 0;// 总的费用金额
		List<PrpLcharge> prpLchargeList = compensateDto.getPrpLchargeList();
		if (prpLchargeList != null && !prpLchargeList.isEmpty()) {
			for (PrpLcharge prpLcharge : prpLchargeList) {
				if ("B".equals(prpLcharge.getKindCode()) || ("AB".equals(prpLcharge.getKindCode()) && !licenseNo.equals(prpLcharge.getLicenseNo()))) {
					// 施救费要按车险公式进行计算
//					if ("03".equals(prpLcharge.getChargeCode())) {
//						dblchargesumrealpay += prpLcharge.getSumRealPay();
//						dbExceptDeductiblePayB += prpLcharge.getExceptDeductiblePay();
//					}
					// 将费用按险别分类进行统计
					for (j = 1; j <= chargecount; j++) {
						if (arrPrpLcharge[j].getChargeCode().equals(prpLcharge.getChargeCode()) && arrPrpLcharge[j].getCurrency().equals(prpLcharge.getCurrency()) && arrPrpLcharge[j].getLicenseNo().equals(prpLcharge.getLicenseNo())) {
							arrPrpLcharge[j].setChargeReport(arrPrpLcharge[j].getChargeReport() + prpLcharge.getChargeReport());
							arrPrpLcharge[j].setChargeAmount(arrPrpLcharge[j].getChargeAmount() + prpLcharge.getChargeAmount());
							arrPrpLcharge[j].setSumRealPay(arrPrpLcharge[j].getSumRealPay() + prpLcharge.getSumRealPay());
							break;
						}
					}
					if (j > chargecount) {
						chargecount++;
						if (arrPrpLcharge[chargecount] == null) {
							arrPrpLcharge[chargecount] = new PrpLcharge();
						}
						PropertyUtils.copyProperties(arrPrpLcharge[chargecount], prpLcharge);
					}
				}
			}
		}
		dblthirdsumrealpayAll = dblthirdsumrealpayAll + dblchargesumrealpay;
		List<PrpLpersonLoss> prpLpersonLossList = compensateDto.getPrpLpersonLossList();
		if (prpLpersonLossList != null && !prpLpersonLossList.isEmpty()) {
			// 循环遍历人伤表
			for (PrpLpersonLoss prpLpersonLoss : prpLpersonLossList) {
				for (j = 1; j <= personcount; j++) {
					if (arrKindCode1[j].indexOf(prpLpersonLoss.getKindCode()) < 0 && String.valueOf(prpLpersonLoss.getPersonNo()).equals(arrPersonNo[j].trim())) {
						arrKindCode1[j] = arrKindCode1[j].trim() + prpLpersonLoss.getKindCode();
					}
					if (String.valueOf(prpLpersonLoss.getPersonNo()).equals(arrPersonNo[j].trim())) {
						break;
					}
				}
				if (j > personcount) {
					personcount++;
					arrKindCode1[personcount] = prpLpersonLoss.getKindCode();
					arrPersonNo[personcount] = String.valueOf(prpLpersonLoss.getPersonNo());
					arrPersonName[personcount] = prpLpersonLoss.getPersonName();
					arrFamilyName[personcount] = prpLpersonLoss.getFamilyName();
				}
			}
		}
		logger.debug("开始產生三者理算报告");
		List<String> strLctextList = new ArrayList<String>();
		String lineText = "";
		if ("RISKCODE_DTC".equals(configCode) || "RISKCODE_DAS".equals(configCode)) {
			// 提车保险特殊处理
			lineText = "提車保險第三者責任險：";
		} else {
			lineText = "第三者責任險：";
		}
		strLctextList.add(lineText);
		if (intCarForBflag == 1) {
			lineText = space(4) + "三者車損賠付:";
			strLctextList.add(lineText);
		}
		for (int i = 1; i <= licensenocount; i++) {
			dblthirdsumdefpay = 0d;
			dblsumloss = 0d;
			dblsumrest = 0d;
			dblthirdCompelPay = 0d;
			dblthirdsumrealpay = 0d;
			if (arrKindCode[i].indexOf("B") < 0) {
				continue;
			}
			lineText = "";
			if (!lossName[i].trim().equals("車輛")) {
				lineText = space(8) + lossName[i].trim();
				strLctextList.add(lineText);
			} else {
				lineText = space(8) + arrLicenseNo[i].trim() + "號車";
				strLctextList.add(lineText);
			}
			lineText = space(8) + "本項實賠金額=(核定賠償金額-強制險賠款";
			textlosscount++;
			lineText = lineText.substring(0, (lineText.length())) + "-殘值)";
			if (StringUtils.getBytesLength(lineText) + 10 > 60) {
				strLctextList.add(lineText);
				lineText = space(21) + "×責任比例";
			} else {
				lineText += "×責任比例";
			}
			if (StringUtils.getBytesLength(lineText) + 11 > 60) {
				strLctextList.add(lineText);
				lineText = space(21) + "×(1－免賠率之和)";
			} else {
				lineText += "×(1－免賠率之和)";
			}
			strLctextList.add(lineText);
			lineText = space(20) + "=(";
			prpLlossList = compensateDto.getPrpLlossList();
			if (prpLlossList != null && !prpLlossList.isEmpty()) {
				for (PrpLloss prpLloss : prpLlossList) {
					if ("B".equals(prpLloss.getKindCode()) && prpLloss.getLicenseNo().equals(arrLicenseNo[i].trim())
							|| ("AB".equals(prpLloss.getKindCode()) && prpLloss.getLicenseNo().equals(arrLicenseNo[i].trim()) && !prpLloss.getLicenseNo().equals(licenseNo))) {
						// 提车保险特殊处理
						dbExceptDeductiblePayB += prpLloss.getExceptDeductiblePay();
						dbldeductiblerate = prpLloss.getDeductiblerate();
						dblDutydeductiblerate = prpLloss.getDutyDeductibleRate();
						dblthirdsumdefpay += prpLloss.getSumDefPay();
						dblsumloss += prpLloss.getSumLoss();
						dblsumrest += prpLloss.getSumRest();
						dblthirdCompelPay += prpLloss.getCompelPay();
						dblthirdsumrealpay += prpLloss.getSumRealPay();
						prpLlossbak = new PrpLloss();
						PropertyUtils.copyProperties(prpLlossbak, prpLloss);
					}
				}
			}
			dblthirdsumrealpayAll = dblthirdsumrealpayAll + dblthirdsumrealpay;
			if (StringUtils.getBytesLength(lineText) + 8 > 60) {
				strLctextList.add(lineText);
				lineText = space(21) + formatPay(dblthirdsumdefpay);
			} else {
				lineText += formatPay(dblthirdsumdefpay);
			}
			if (StringUtils.getBytesLength(lineText) + 8 > 60) {
				strLctextList.add(lineText);
				lineText = space(21) + "-" + formatPay(dblthirdCompelPay);
			} else {
				lineText += "-" + formatPay(dblthirdCompelPay);
			}
			if (StringUtils.getBytesLength(lineText) + 8 > 60) {
				strLctextList.add(lineText);
				lineText = space(21) + "-" + formatPay(dblsumrest) + ")";
			} else {
				lineText += "-" + formatPay(dblsumrest) + ")";
			}
			dblthirdsumdefpay = (dblthirdsumdefpay - dblthirdCompelPay - dblsumrest) * prpLlossbak.getArrangeRate() / 100;
			dblthirdsumdefpayAll = dblthirdsumdefpayAll + dblthirdsumdefpay;
			if (StringUtils.getBytesLength(lineText) + 8 > 60) {
				strLctextList.add(lineText);
				lineText = space(21) + "×" + formatPay(prpLlossbak.getIndemnityDutyRate()) + "％";
			} else {
				lineText += "×" + formatPay(prpLlossbak.getIndemnityDutyRate()) + "％";
			}
			dblthirdsumdefpay = dblthirdsumdefpay * prpLlossbak.getIndemnityDutyRate() / 100;
			if (StringUtils.getBytesLength(lineText) + 10 > 60) {
				strLctextList.add(lineText);
				lineText = space(21) + "×(1－" + formatPay(prpLlossbak.getDutyDeductibleRate() + prpLlossbak.getDeductiblerate()) + "％)";// +
				// "×(1－"+new
			} else {
				lineText += "×(1－" + formatPay(prpLlossbak.getDutyDeductibleRate() + prpLlossbak.getDeductiblerate()) + "％)";
			}
			dblthirdsumdefpay = dblthirdsumdefpay * (1 - (prpLlossbak.getDutyDeductibleRate() + prpLlossbak.getDeductiblerate()) / 100);
			strLctextList.add(lineText);
			lineText = space(20) + "=" + formatPay(dblthirdsumdefpay) + "元";
			strLctextList.add(lineText);
			if (chargecount > 0) {
				for (int c = 1; c <= chargecount; c++) {
					// 只有录入施救费时，才进入
					if (arrPrpLcharge[c].getLicenseNo().equals(arrLicenseNo[i])) {
						lineText = space(8) + "施救費實賠金額=費用金額×責任比例×(1－免賠率之和)";
						strLctextList.add(lineText);
						lineText = space(20) + "=" + arrPrpLcharge[c].getChargeReport() + "×" + formatPay(prpLlossbak.getIndemnityDutyRate()) + "×" + "(1-" + formatPay(prpLlossbak.getDutyDeductibleRate() + prpLlossbak.getDeductiblerate()) + "％)";
						strLctextList.add(lineText);
						lineText = space(20) + "=" + formatPay(arrPrpLcharge[c].getSumRealPay()) + "元";
						strLctextList.add(lineText);
					}
				}
			}
		}
		if (intpropflag == 1) {
			textlosscount++;
			lineText = space(4) + "三者財產賠付:";
			prpLlossList = compensateDto.getPrpLlossList();
			if (prpLlossList != null && !prpLlossList.isEmpty()) {
				for (PrpLloss prpLloss : prpLlossList) {
					if (("B".equals(prpLloss.getKindCode()) || "AB".equals(prpLloss.getKindCode())) && (prpLloss.getLicenseNo() == null || prpLloss.getLicenseNo().length() == 0) && prpLloss.getLossName() != null) {
						// 提车保险车损部分保险金额获取
						dbExceptDeductiblePayB = dbExceptDeductiblePayB + prpLloss.getExceptDeductiblePay();
						dblthirdsumdefpay = prpLloss.getSumDefPay();
						dblsumloss = prpLloss.getSumLoss();
						dblsumrest = prpLloss.getSumRest();
						dblthirdCompelPay = prpLloss.getCompelPay();
						dblthirdsumrealpay = prpLloss.getSumRealPay();
						dblthirdsumdefpayAll = dblthirdsumdefpayAll + dblthirdsumdefpay - dblsumrest - dblthirdCompelPay;
						dblthirdsumrealpayAll = dblthirdsumrealpayAll + dblthirdsumrealpay;
						strLctextList.add(lineText);
						lineText = space(8) + "項目:" + prpLloss.getLossName();
						strLctextList.add(lineText);
						lineText = space(8) + "本項賠款金額=(核定賠償金額-強制險賠款-殘值)×事故責任比例×(1－免賠率之和)";
						dbldeductiblerate = prpLloss.getDeductiblerate();
						dblDutydeductiblerate = prpLloss.getDutyDeductibleRate();
						strLctextList.add(lineText);
						lineText = space(20) + "=(" + formatPay(dblthirdsumdefpay) + "-" + formatPay(dblthirdCompelPay) + "-" + formatPay(dblsumrest) + ")×" + formatPay(prpLloss.getIndemnityDutyRate()) + "％" + "×" + "(1-"
								+ formatPay(prpLloss.getDutyDeductibleRate() + prpLloss.getDeductiblerate()) + "％)";
						strLctextList.add(lineText);
						dblthirdsumdefpay = (dblthirdsumdefpay - dblthirdCompelPay - dblsumrest) * prpLloss.getIndemnityDutyRate() / 100 * (1 - (prpLloss.getDutyDeductibleRate() + prpLloss.getDeductiblerate()) / 100);
						lineText = space(20) + "=" + formatPay(dblthirdsumdefpay) + "元";
						PropertyUtils.copyProperties(prpLlossbak, prpLloss);
						strLctextList.add(lineText);
					}
				}
			}
			if (chargecount > 0) {
				for (int c = 1; c <= chargecount; c++) {
					// 只有录入施救费时，才进入
					if ("".equals(arrPrpLcharge[c].getLicenseNo())) {
						lineText = space(8) + "施救費實賠金額=費用金額×責任比例×(1－免賠率之和)";
						strLctextList.add(lineText);
						lineText = space(20) + "=" + arrPrpLcharge[c].getChargeReport() + "×" + formatPay(prpLlossbak.getIndemnityDutyRate()) + "×" + "(1-" + formatPay(prpLlossbak.getDutyDeductibleRate() + prpLlossbak.getDeductiblerate()) + "％)";
						strLctextList.add(lineText);
						lineText = space(20) + "=" + formatPay(arrPrpLcharge[c].getSumRealPay()) + "元";
						strLctextList.add(lineText);
					}
				}
			}
		}
		// 人员处理表。
		if (personcount > 0) {
			lineText = space(4) + "三者人傷賠付:";
			strLctextList.add(lineText);
		}
		for (j = 1; j <= personcount; j++) {
			dblthirdsumdefpay = 0d;
			dblsumloss = 0d;
			dblsumrest = 0d;
			dblthirdCompelPay = 0d;
			dblthirdsumrealpay = 0d;
			if (arrKindCode1[j].indexOf("B") < 0) {
				// 提车保险车损部分保险金额获取2008-04-15
				continue;
			}
			lineText = space(8) + "車輛號牌號碼為" + arrFamilyName[j].trim() + "的出險人員" + "  " + arrPersonName[j].trim() + "：";
			textpersoncount++;
			strLctextList.add(lineText);
			prpLpersonLossList = compensateDto.getPrpLpersonLossList();
			if (prpLpersonLossList != null && !prpLpersonLossList.isEmpty()) {
				for (PrpLpersonLoss prpLpersonLoss : prpLpersonLossList) {
					if ("B".equals(prpLpersonLoss.getKindCode()) || "AB".equals(prpLpersonLoss.getKindCode())) {
						// 提车保险车损部分保险金额获取2008-04-15
						dbldeductiblerate = prpLpersonLoss.getDeductiblerate();
						dblDutydeductiblerate = prpLpersonLoss.getDutyDeductibleRate();
					}
				}
			}
			lineText = space(8) + "本項賠款金額=(";
			prpLpersonLossList = compensateDto.getPrpLpersonLossList();
			if (prpLpersonLossList != null && !prpLpersonLossList.isEmpty()) {
				for (PrpLpersonLoss prpLpersonLoss : prpLpersonLossList) {
					if (("B".equals(prpLpersonLoss.getKindCode()) || "AB".equals(prpLpersonLoss.getKindCode())) && String.valueOf(prpLpersonLoss.getPersonNo()).equals(arrPersonNo[j].trim())) {
						// 提车保险车损部分保险金额获取2008-04-15
						if (StringUtils.getBytesLength(lineText) + StringUtils.getBytesLength(prpLpersonLoss.getLiabDetailName()) + 1 > 60) {
							strLctextList.add(lineText);
							lineText = space(21) + prpLpersonLoss.getLiabDetailName() + "＋";
						} else {
							lineText += prpLpersonLoss.getLiabDetailName() + "＋";
						}
					}
				}
			}
			lineText = lineText.substring(0, (lineText.length() - 1)) + "-強制險賠款)";
			if (StringUtils.getBytesLength(lineText) + 10 > 60) {
				strLctextList.add(lineText);
				lineText = space(21) + "×事故責任比例";
			} else {
				lineText += "×事故責任比例";
			}
			if (StringUtils.getBytesLength(lineText) + 11 > 60) {
				strLctextList.add(lineText);
				lineText = space(21) + "×(1－免賠率之和)";
			} else {
				lineText += "×(1－免賠率之和)";
			}
			strLctextList.add(lineText);
			lineText = space(20) + "=(";
			prpLpersonLossList = compensateDto.getPrpLpersonLossList();
			if (prpLpersonLossList != null && !prpLpersonLossList.isEmpty()) {
				for (PrpLpersonLoss prpLpersonLoss : prpLpersonLossList) {
					if (("B".equals(prpLpersonLoss.getKindCode()) || "AB".equals(prpLpersonLoss.getKindCode())) && String.valueOf(prpLpersonLoss.getPersonNo()).equals(arrPersonNo[j].trim())) {
						// 提车保险车损部分保险金额获取
						if (StringUtils.getBytesLength(lineText) + 8 > 60) {
							strLctextList.add(lineText);
							lineText = space(21) + formatPay(prpLpersonLoss.getSumDefPay()) + "＋";
						} else {
							lineText += formatPay(prpLpersonLoss.getSumDefPay()) + "＋";
						}
						dbExceptDeductiblePayB = dbExceptDeductiblePayB + prpLpersonLoss.getExceptDeductiblePay();
						dblthirdsumdefpay += prpLpersonLoss.getSumDefPay();
						dblsumloss += prpLpersonLoss.getSumLoss();
						dblthirdCompelPay += prpLpersonLoss.getCompelPay();
						dblthirdsumrealpay += prpLpersonLoss.getSumRealPay();
						PropertyUtils.copyProperties(prpLpersonLossDtobak, prpLpersonLoss);
					}
				}
			}
			dblthirdsumrealpayAll = dblthirdsumrealpayAll + dblthirdsumrealpay;
			lineText = lineText.substring(0, (lineText.length() - 1)) + "-" + formatPay(dblthirdCompelPay) + ")";
			dblthirdsumdefpay = dblthirdsumdefpay - dblthirdCompelPay;
			dblthirdsumdefpayAll = dblthirdsumdefpayAll + dblthirdsumdefpay;
			if (StringUtils.getBytesLength(lineText) + 8 > 60) {
				strLctextList.add(lineText);
				lineText = space(21) + "×" + formatPay(prpLpersonLossDtobak.getIndemnityDutyRate()) + "％";
			} else {
				lineText += "×" + formatPay(prpLpersonLossDtobak.getIndemnityDutyRate()) + "％";
			}
			dblthirdsumdefpay = dblthirdsumdefpay * prpLpersonLossDtobak.getIndemnityDutyRate() / 100;
			dblthirdsumdefpay = dblthirdsumdefpay * prpLpersonLossDtobak.getArrangeRate() / 100;
			if (StringUtils.getBytesLength(lineText) + 10 > 60) {
				strLctextList.add(lineText);
				lineText = space(21) + "×(1－" + formatPay(prpLpersonLossDtobak.getDutyDeductibleRate() + prpLpersonLossDtobak.getDeductiblerate()) + "％)";
			} else {
				lineText += "×(1－" + formatPay(prpLpersonLossDtobak.getDutyDeductibleRate() + prpLpersonLossDtobak.getDeductiblerate()) + "％)";
			}
			dblthirdsumdefpay = dblthirdsumdefpay * (1 - (prpLpersonLossDtobak.getDutyDeductibleRate() + prpLpersonLossDtobak.getDeductiblerate()) / 100);
			strLctextList.add(lineText);
			lineText = space(20) + "=" + formatPay(dblthirdsumdefpay) + "元";
			strLctextList.add(lineText);
		}
		PrpLclaim prpLclaim = compensateDto.getPrpLclaim();

		if (!(DataUtils.emptyToNull(prpLclaim.getEscapeFlag()) != null && prpLclaim.getEscapeFlag().charAt(0) == '5')) {
			if (textlosscount * textpersoncount == 0 && textlosscount + textpersoncount >= 1) {
				if (textlosscount == 0) {
					// 超限判断 核定赔偿要乘事故责任比例和限额比较
					if (dblthirdsumdefpayAll * (prpLpersonLossDtobak.getIndemnityDutyRate() / 100) > dblamount) {
						lineText = space(8) + "因按事故責任比例應承擔的賠償金額" + "超過三者險的賠償限額，所以：";
						strLctextList.add(lineText);
						lineText = space(8) + "本險別實賠金額=賠償限額×（1－免賠率之和)=" + formatPay(dblamount) + "×(1－" + formatPay(dblDutydeductiblerate + dbldeductiblerate) + "％)";
						strLctextList.add(lineText);
						lineText = space(21) + "=" + formatPay(dblthirdsumrealpay) + "元";
						strLctextList.add(lineText);
					} else {
						lineText = space(8) + "本險別實賠金額=人員實賠金額=" + formatPay(dblthirdsumrealpay) + "元";
						strLctextList.add(lineText);
					}
				} else if (textpersoncount == 0) {
					// 超限判断 核定赔偿要乘事故责任比例和限额比较
					if (dblthirdsumdefpayAll * (prpLlossbak.getIndemnityDutyRate() / 100) > dblamount && (compensateData.strEscapeFlag.length() > 0 && compensateData.strEscapeFlag.charAt(0) != 'T' || compensateData.strEscapeFlag.length() == 0)) {
						lineText = space(8) + "因按事故責任比例應承擔的賠償金額" + "超過三者險的賠償限額，所以：";
						strLctextList.add(lineText);
						lineText = space(8) + "本險別實賠金額=賠償限額×（1－免賠率)=" + formatPay(dblamount) + "×(1－" + formatPay(dblDutydeductiblerate + dbldeductiblerate) + "％)";
						strLctextList.add(lineText);
						lineText = space(21) + "=" + formatPay(dblthirdsumrealpayAll) + "元";
						strLctextList.add(lineText);
					} else {
						lineText = space(8) + "本險別實賠金額=標的實賠金額=" + formatPay(dblthirdsumrealpayAll) + "元";
						strLctextList.add(lineText);
					}
				}
			} else if (textlosscount * textpersoncount > 0) {
				// 超限判断 核定赔偿要乘事故责任比例和限额比较
				if (dblthirdsumdefpayAll * (prpLlossbak.getIndemnityDutyRate() / 100) > dblamount && (compensateData.strEscapeFlag.length() > 0 && compensateData.strEscapeFlag.charAt(0) != 'T' || compensateData.strEscapeFlag.length() == 0)) {
					lineText = space(8) + "因按事故責任比例應承擔的賠償金額" + "超過三者險的賠償限額，所以：";
					strLctextList.add(lineText);
					lineText = space(8) + "本險別實賠金額=賠償限額×（1－免賠率)=" + formatPay(dblamount) + "×(1－" + formatPay(dblDutydeductiblerate + dbldeductiblerate) + "％)";
					strLctextList.add(lineText);
					lineText = space(21) + "=" + formatPay(dblthirdsumrealpayAll) + "元";
					strLctextList.add(lineText);

				} else {
					lineText = space(4) + "本險別實賠金額=實賠金額 ";
					strLctextList.add(lineText);
					lineText = "                  " + "=" + formatPay(dblthirdsumrealpayAll - dblthirdsumrealpay) + "＋" + formatPay(dblthirdsumrealpay);
					strLctextList.add(lineText);
					lineText = "                  " + "=" + formatPay(dblthirdsumrealpayAll) + "元";
					strLctextList.add(lineText);
				}
			}
		}
		compensateData.dblAllExceptDeductiblePay += dbExceptDeductiblePayB;
		compensateData.dblThirdSumRealPay += dblthirdsumrealpayAll;
		compensateData.dblAllSumRealPay += dblthirdsumrealpayAll + dbExceptDeductiblePayB;
		if (new Double(dbExceptDeductiblePayB).intValue() != 0) {
			compensateData.lineM1 += "第三者責任險不計免賠額+";
			compensateData.lineM2 += new Double(dbExceptDeductiblePayB).toString() + "+";
		}
		List<PrpLctext> prpLctextList = compensateDto.getPrpLctextList();
		int ctextcount = 0;
		if (prpLctextList != null && !prpLctextList.isEmpty()) {
			ctextcount = prpLctextList.size();
		}
		logger.debug("三者险-ctextcount:" + ctextcount);
		PrpLctext prpLctext = null;
		for (String lineTextTemp : strLctextList) {
			prpLctext = new PrpLctext();
			prpLctext.getId().setCompensateNo(strCompensateNo);
			prpLctext.getId().setTextType("1");
			prpLctext.getId().setLineNo(ctextcount++);
			prpLctext.setContext(lineTextTemp);
			prpLctext.setFlag("");
			compensateData.prpLctextlist.add(prpLctext);
		}
	}

	/**
	 * 生成附加险的理算公式及内容
	 * @param iKindCode
	 * @param httpServletRequest
	 * @param compensateDto
	 * @throws UserException
	 * @throws Exception
	 */
	public void CarCreateForOther(String iKindCode, HttpServletRequest httpServletRequest, CompensateDto compensateDto, CompensateData compensateData) throws UserException, Exception {
		int personcount = 0;
		int j = 0;
		PrpLloss prpLloss = new PrpLloss();
		double dblnofranchise = 0;
		double dblsumdefPay = 0d;
		double dblsumloss = 0;
		double dblothsumrealpay = 0;
		double dblsumrealPay = 0d;
		double dblchargesumrealpay = 0d;
		double exceptDeductiblePayOther = 0d;
		String[] arrKindCode1 = new String[20];
		String[] arrPersonNo = new String[20];
		String[] arrPersonName = new String[20];
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		String strCompensateNo = prpLcompensate.getCompensateNo();
		PrpLloss prpLlossbak = new PrpLloss();
		List<String> strLctextList = new ArrayList<String>();
		String lineText = "";
		// 简易赔案不允许赔付人伤
		List<PrpLpersonLoss> prpLpersonLossList = compensateDto.getPrpLpersonLossList();
		if (prpLpersonLossList != null && prpLpersonLossList.size() > 0) {
			for (PrpLpersonLoss prpLpersonLoss : prpLpersonLossList) {
				for (j = 1; j <= personcount; j++) {
					if (arrKindCode1[j].indexOf(prpLpersonLoss.getKindCode()) < 0 && String.valueOf(prpLpersonLoss.getPersonNo()).equals(arrPersonNo[j].trim()))
						arrKindCode1[j] = arrKindCode1[j].trim() + prpLpersonLoss.getKindCode();
					if (String.valueOf(prpLpersonLoss.getPersonNo()).equals(arrPersonNo[j].trim()))
						break;
				}
				if (j > personcount) {
					personcount++;
					arrKindCode1[personcount] = prpLpersonLoss.getKindCode();
					arrPersonNo[personcount] = String.valueOf(prpLpersonLoss.getPersonNo());
					arrPersonName[personcount] = prpLpersonLoss.getPersonName();
				}
			}
		}
		List<PrpLloss> prpLlossList = compensateDto.getPrpLlossList();
		List<PrpLcharge> prpLchargeList = compensateDto.getPrpLchargeList();
		if ("C5".equals(iKindCode.trim())) {
			// 异地出险住宿费特约
			double exceptDeductiblePayC5 = 0d;
			strLctextList.add(lineText);
			if (prpLlossList != null && !prpLlossList.isEmpty()) {
				for (int i = 0; i < prpLlossList.size(); i++) {
					prpLloss = prpLlossList.get(i);
					if ("C5".equals(prpLloss.getKindCode())) {
						lineText = "異地出險住宿費特約=";
						lineText += formatPay(prpLloss.getSumRealPay()) + "元";
						dblothsumrealpay += prpLloss.getSumRealPay();
						exceptDeductiblePayOther += prpLloss.getExceptDeductiblePay();
						exceptDeductiblePayC5 += prpLloss.getExceptDeductiblePay();
					}
				}
			}
			if (new Double(exceptDeductiblePayC5).intValue() != 0) {
				compensateData.lineM1 += "異地出險住宿費不計免賠額+";
				compensateData.lineM2 += new Double(exceptDeductiblePayC5).toString() + "+";
			}
		} else if ("C6".equals(iKindCode.trim())) {
			// 法律服务特约
			double exceptDeductiblePayC6 = 0d;
			strLctextList.add(lineText);
			if (prpLlossList != null && !prpLlossList.isEmpty()) {
				for (int i = 0; i < prpLlossList.size(); i++) {
					prpLloss = prpLlossList.get(i);
					if ("C6".equals(prpLloss.getKindCode())) {
						lineText = "法律服務特約=";
						lineText += formatPay(prpLloss.getSumRealPay()) + "元";
						dblothsumrealpay += prpLloss.getSumRealPay();
						exceptDeductiblePayOther += prpLloss.getExceptDeductiblePay();
						exceptDeductiblePayC6 += prpLloss.getExceptDeductiblePay();
					}
				}
			}
			if (new Double(exceptDeductiblePayC6).intValue() != 0) {
				compensateData.lineM1 += "法律服務特約不計免賠額+";
				compensateData.lineM2 += new Double(exceptDeductiblePayC6).toString() + "+";
			}
		} else if ("C7".equals(iKindCode.trim())) {
			// 节假日行驶区域扩展特约
			strLctextList.add(lineText);
			if (prpLlossList != null && !prpLlossList.isEmpty()) {
				for (int i = 0; i < prpLlossList.size(); i++) {
					prpLloss = prpLlossList.get(i);
					if ("C7".equals(prpLloss.getKindCode())) {
						dblsumrealPay += prpLloss.getSumRealPay();
						dblothsumrealpay += prpLloss.getSumRealPay();
					}
				}
			}
			lineText = "節假日行駛區域擴展特約=";
			lineText += formatPay(dblsumrealPay) + "元";
			dblsumrealPay = 0;
		} else if ("X1".equals(iKindCode.trim())) {
			// 发动机特约条款
			int flagX1 = 0;
			strLctextList.add(lineText);
			double exceptDeductiblePayX1 = 0d;
			double dblchargesumrealpayX1 = 0d;
			if (prpLlossList != null && !prpLlossList.isEmpty()) {
				for (int i = 0; i < prpLlossList.size(); i++) {
					prpLloss = prpLlossList.get(i);
					if ("X1".equals(prpLloss.getKindCode())) {
						dblsumrealPay += prpLloss.getSumRealPay();
						dblothsumrealpay += prpLloss.getSumRealPay();
						exceptDeductiblePayX1 = prpLloss.getExceptDeductiblePay();
						exceptDeductiblePayOther += prpLloss.getExceptDeductiblePay();
					}
				}
			}
//			prpLchargeList = compensateDto.getPrpLchargeList();
//			if (prpLchargeList != null && !prpLchargeList.isEmpty()) {
//				for (PrpLcharge prpLcharge : prpLchargeList) {
//					if ("X1".equals(prpLcharge.getKindCode())) {
//						// 施救费要按车险公式进行计算
//						if ("03".equals(prpLcharge.getChargeCode())) {
//							dblchargesumrealpayX1 += prpLcharge.getSumRealPay();
//							dblchargesumrealpay += prpLcharge.getSumRealPay();
//							dblothsumrealpay += prpLcharge.getSumRealPay();
//							exceptDeductiblePayX1 += prpLcharge.getExceptDeductiblePay();
//							exceptDeductiblePayOther += prpLcharge.getExceptDeductiblePay();
//							flagX1 = 1;
//						}
//					}
//				}
//			}
			lineText = "發動機特約條款賠款=";
			if (flagX1 == 1) {
				lineText += "實賠金額+施救費";
				strLctextList.add(lineText);
				lineText = space(18) + "=" + formatPay(dblsumrealPay) + "+" + formatPay(dblchargesumrealpayX1);
				strLctextList.add(lineText);
				lineText = space(18) + "=";
			}
			lineText += formatPay(dblsumrealPay + dblchargesumrealpayX1) + "元";
			if (new Double(exceptDeductiblePayX1).intValue() != 0) {
				compensateData.lineM1 += "發動機特約條款不計免賠額+";
				compensateData.lineM2 += new Double(exceptDeductiblePayX1).toString() + "+";
			}
			dblsumrealPay = 0;

		} else if ("D2".equals(iKindCode.trim())) {
			// 车上货物责任险
			double exceptDeductiblePayD2 = 0d;
			strLctextList.add(lineText);
			prpLlossList = compensateDto.getPrpLlossList();
			if (prpLlossList != null && !prpLlossList.isEmpty()) {
				for (int i = 0; i < prpLlossList.size(); i++) {
					prpLloss = prpLlossList.get(i);
					if ("D2".equals(prpLloss.getKindCode())) {
						dblsumrealPay += prpLloss.getSumRealPay();
						dblothsumrealpay += prpLloss.getSumRealPay();
						exceptDeductiblePayD2 = prpLloss.getExceptDeductiblePay();
						exceptDeductiblePayOther += prpLloss.getExceptDeductiblePay();
					}
				}
			}
			lineText = "車上貨物責任險=";
			lineText += formatPay(dblsumrealPay) + "元";
			if (new Double(exceptDeductiblePayD2).intValue() != 0) {
				compensateData.lineM1 += "車上貨物責任險不計免賠額+";
				compensateData.lineM2 += new Double(exceptDeductiblePayD2).toString() + "+";
			}
			dblsumrealPay = 0;
		} else if ("K1".equals(iKindCode.trim())) {
			/*
			 * ------------------------------------------------------------------
			 * ---------------------- 起重、装卸、挖掘车辆损失扩展条款
			 * --------------------------
			 * --------------------------------------------------------------
			 */
			int flagK1 = 0;
			strLctextList.add(lineText);
			double exceptDeductiblePayK1 = 0d;
			double dblchargesumrealpayK1 = 0d;
			prpLlossList = compensateDto.getPrpLlossList();
			if (prpLlossList != null && !prpLlossList.isEmpty()) {
				for (int i = 0; i < prpLlossList.size(); i++) {
					prpLloss = prpLlossList.get(i);
					if ("K1".equals(prpLloss.getKindCode())) {
						dblsumrealPay += prpLloss.getSumRealPay();
						dblothsumrealpay += prpLloss.getSumRealPay();
						exceptDeductiblePayK1 = prpLloss.getExceptDeductiblePay();
						exceptDeductiblePayOther += prpLloss.getExceptDeductiblePay();
					}
				}
			}
//			prpLchargeList = compensateDto.getPrpLchargeList();
//			if (prpLchargeList != null && !prpLchargeList.isEmpty()) {
//				for (PrpLcharge prpLcharge : prpLchargeList) {
//					if ("K1".equals(prpLcharge.getKindCode())) {
//						/**
//						 * 施救费要按车险公式进行计算
//						 */
//						if ("03".equals(prpLcharge.getChargeCode())) {
//							dblchargesumrealpayK1 += prpLcharge.getSumRealPay();
//							dblchargesumrealpay += prpLcharge.getSumRealPay();
//							dblothsumrealpay += prpLcharge.getSumRealPay();
//							exceptDeductiblePayK1 += prpLcharge.getExceptDeductiblePay();
//							exceptDeductiblePayOther += prpLcharge.getExceptDeductiblePay();
//							flagK1 = 1;
//						}
//					}
//				}
//			}
			lineText = "起重、裝卸、挖掘車輛損失擴展條款=";
			if (flagK1 == 1) {
				lineText += "實賠金額+施救費";
				strLctextList.add(lineText);
				lineText = space(18) + "=" + formatPay(dblsumrealPay) + "+" + formatPay(dblchargesumrealpayK1);
				strLctextList.add(lineText);
				lineText = space(18) + "=";
			}
			lineText += formatPay(dblsumrealPay + dblchargesumrealpayK1) + "元";
			if (new Double(exceptDeductiblePayK1).intValue() != 0) {
				compensateData.lineM1 += "起重、裝卸、挖掘車輛損失擴展條款不計免賠額+";
				compensateData.lineM2 += new Double(exceptDeductiblePayK1).toString() + "+";
			}
			dblsumrealPay = 0;
		} else if ("K2".equals(iKindCode.trim())) {
			/*
			 * ------------------------------------------------------------------
			 * ---------------------- 特种车固定设备、仪器损坏扩展条款
			 * --------------------------
			 * --------------------------------------------------------------
			 */
			int flagK2 = 0;
			strLctextList.add(lineText);
			double exceptDeductiblePayK2 = 0d;
			double dblchargesumrealpayK2 = 0d;
			prpLlossList = compensateDto.getPrpLlossList();
			if (prpLlossList != null && !prpLlossList.isEmpty()) {
				for (int i = 0; i < prpLlossList.size(); i++) {
					prpLloss = prpLlossList.get(i);
					if ("K2".equals(prpLloss.getKindCode())) {
						dblsumrealPay += prpLloss.getSumRealPay();
						dblothsumrealpay += prpLloss.getSumRealPay();
						exceptDeductiblePayK2 = prpLloss.getExceptDeductiblePay();
						exceptDeductiblePayOther += prpLloss.getExceptDeductiblePay();
					}
				}
			}
//			prpLchargeList = compensateDto.getPrpLchargeList();
//			if (prpLchargeList != null && !prpLchargeList.isEmpty()) {
//				for (PrpLcharge prpLcharge : prpLchargeList) {
//					if ("K2".equals(prpLcharge.getKindCode())) {
//						/**
//						 * 施救费要按车险公式进行计算
//						 */
//						if ("03".equals(prpLcharge.getChargeCode())) {
//							dblchargesumrealpayK2 += prpLcharge.getSumRealPay();
//							dblchargesumrealpay += prpLcharge.getSumRealPay();
//							dblothsumrealpay += prpLcharge.getSumRealPay();
//							exceptDeductiblePayK2 += prpLcharge.getExceptDeductiblePay();
//							exceptDeductiblePayOther += prpLcharge.getExceptDeductiblePay();
//							flagK2 = 1;
//						}
//					}
//				}
//			}
			lineText = "特種車固定設備、儀器損壞擴展條款=";
			if (flagK2 == 1) {
				lineText += "實賠金額+施救費";
				strLctextList.add(lineText);
				lineText = space(32) + "=" + formatPay(dblsumrealPay) + "+" + formatPay(dblchargesumrealpayK2);
				strLctextList.add(lineText);
				lineText = space(32) + "=";
			}
			lineText += formatPay(dblsumrealPay + dblchargesumrealpayK2) + "元";
			if (new Double(exceptDeductiblePayK2).intValue() != 0) {
				compensateData.lineM1 += "特種車固定設備、儀器損壞擴展條款不計免賠額+";
				compensateData.lineM2 += new Double(exceptDeductiblePayK2).toString() + "+";
			}
			dblsumrealPay = 0;

		} else if (iKindCode.trim().equals("M")) {
			/*
			 * ------------------------------------------------------------------
			 * ---------------------- 车辆不计免赔特约保险
			 * --------------------------------
			 * --------------------------------------------------------
			 */
		} else if (iKindCode.trim().equals("NX")) {
			/*
			 * ------------------------------------------------------------------
			 * ---------------------- 新车特约条款A
			 * ------------------------------------
			 * ----------------------------------------------------
			 */
			double exceptDeductiblePayNX = 0d;
			strLctextList.add(lineText);
			prpLlossList = compensateDto.getPrpLlossList();
			if (prpLlossList != null && !prpLlossList.isEmpty()) {
				for (int i = 0; i < prpLlossList.size(); i++) {
					prpLloss = prpLlossList.get(i);
					if ("NX".equals(prpLloss.getKindCode())) {
						lineText = "新車特約條款A=";
						lineText += formatPay(prpLloss.getSumRealPay()) + "元";
						dblothsumrealpay += prpLloss.getSumRealPay();
						exceptDeductiblePayNX = prpLloss.getExceptDeductiblePay();
						exceptDeductiblePayOther += prpLloss.getExceptDeductiblePay();
					}
				}
			}
			if (new Double(exceptDeductiblePayNX).intValue() != 0) {
				compensateData.lineM1 += "新車特約條款A不計免賠額+";
				compensateData.lineM2 += new Double(exceptDeductiblePayNX).toString() + "+";
			}
		} else if (iKindCode.trim().equals("NY")) {
			/*
			 * ------------------------------------------------------------------
			 * ---------------------- 新车特约条款B
			 * ------------------------------------
			 * ----------------------------------------------------
			 */
			double exceptDeductiblePayNY = 0d;
			strLctextList.add(lineText);
			prpLlossList = compensateDto.getPrpLlossList();
			if (prpLlossList != null && !prpLlossList.isEmpty()) {
				for (int i = 0; i < prpLlossList.size(); i++) {
					prpLloss = prpLlossList.get(i);
					if ("NY".equals(prpLloss.getKindCode())) {
						lineText = "新車特約條款B=";
						lineText += formatPay(prpLloss.getSumRealPay()) + "元";
						dblothsumrealpay += prpLloss.getSumRealPay();
						exceptDeductiblePayNY = prpLloss.getExceptDeductiblePay();
						exceptDeductiblePayOther += prpLloss.getExceptDeductiblePay();
					}
				}
			}
			if (new Double(exceptDeductiblePayNY).intValue() != 0) {
				compensateData.lineM1 += "新車特約條款B不計免賠額+";
				compensateData.lineM2 += new Double(exceptDeductiblePayNY).toString() + "+";
			}
		} else if (iKindCode.trim().equals("NZ")) {
			/*
			 * ------------------------------------------------------------------
			 * ---------------------- 随车行李物品损失保险条款
			 * ------------------------------
			 * ----------------------------------------------------------
			 */
			double exceptDeductiblePayNZ = 0d;
			double amount = 0d;
			PrpLclaim prpLclaim = compensateDto.getPrpLclaim();
			String policyNo = prpLclaim.getPolicyNo();
			String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
			String damageHour = prpLclaim.getDamageStartHour();
			List<PrpCitemKind> limitlist = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, null , null);
			// 取保额
			if (limitlist != null && limitlist.size() > 0) {
				Iterator<PrpCitemKind> it = limitlist.iterator();
				PrpCitemKind prpCitemKind = null;
				while (it.hasNext()) {
					prpCitemKind = it.next();
					if ("NZ".equals(prpCitemKind.getKindCode())) {
						amount = prpCitemKind.getAmount();
						break;
					}
				}
			}
			strLctextList.add(lineText);
			lineText = "隨車行李物品損失保險條款";
			prpLlossList = compensateDto.getPrpLlossList();
			if (prpLlossList != null && !prpLlossList.isEmpty()) {
				for (int i = 0; i < prpLlossList.size(); i++) {
					prpLloss = prpLlossList.get(i);
					if ("NZ".equals(prpLloss.getKindCode())) {
						dblsumdefPay += prpLloss.getSumDefPay();
						dblsumloss += prpLloss.getSumLoss();
						dblsumrealPay += prpLloss.getSumRealPay();
						dblothsumrealpay += prpLloss.getSumRealPay();
						exceptDeductiblePayOther += prpLloss.getExceptDeductiblePay();
						exceptDeductiblePayNZ += prpLloss.getExceptDeductiblePay();
						PropertyUtils.copyProperties(prpLlossbak, prpLloss);
					}
				}
			}
			if (dblsumdefPay <= amount) {
				strLctextList.add(lineText);
				lineText = space(4) + "本項實賠金額 = 核定賠償金額×責任比例×(1-免賠率之和)";
				strLctextList.add(lineText);
				lineText = space(10) + "=" + dblsumdefPay + "×" + prpLlossbak.getIndemnityDutyRate() + "×(1-" + (prpLlossbak.getDutyDeductibleRate() + prpLlossbak.getDeductiblerate()) + "%)";
				strLctextList.add(lineText);
				lineText = space(10) + "=" + formatPay(dblsumrealPay) + "元";
			} else {
				strLctextList.add(lineText);
				lineText = space(4) + "本項實賠金額 = 保險金額×責任比例×(1-免賠率之和)";
				strLctextList.add(lineText);
				lineText = space(10) + "=" + amount + "×" + prpLlossbak.getIndemnityDutyRate() + "×(1-" + (prpLlossbak.getDutyDeductibleRate() + prpLlossbak.getDeductiblerate()) + "%)";
				strLctextList.add(lineText);
				lineText = space(10) + "=" + formatPay(dblsumrealPay) + "元";
			}
			if (new Double(exceptDeductiblePayNZ).intValue() != 0) {
				compensateData.lineM1 += "隨車行李物品損失保險不計免賠額+";
				compensateData.lineM2 += new Double(exceptDeductiblePayNZ).toString() + "+";
			}
			dblsumloss = 0;
			dblsumdefPay = 0;
			dblsumrealPay = 0;
		} else if (iKindCode.trim().equals("R")) {
			/*
			 * ------------------------------------------------------------------
			 * ---------------------- 交通事故精神损害赔偿责任保险
			 * ----------------------------
			 * ------------------------------------------------------------
			 */
			double exceptDeductiblePayR = 0d;
			strLctextList.add(lineText);
			prpLpersonLossList = compensateDto.getPrpLpersonLossList();
			if (prpLpersonLossList != null && !prpLpersonLossList.isEmpty()) {
				for (PrpLpersonLoss prpLpersonLoss : prpLpersonLossList) {
					if ("R".equals(prpLpersonLoss.getKindCode())) {
						dblsumrealPay += prpLpersonLoss.getSumRealPay();
						dblothsumrealpay += prpLpersonLoss.getSumRealPay();
						exceptDeductiblePayR = prpLpersonLoss.getExceptDeductiblePay();
						exceptDeductiblePayOther += prpLpersonLoss.getExceptDeductiblePay();
					}
				}
			}
			lineText = "交通事故精神損害賠償責任保險=";
			lineText += formatPay(dblsumrealPay) + "元";
			if (new Double(exceptDeductiblePayR).intValue() != 0) {
				compensateData.lineM1 += "交通事故精神損害賠償責任險不計免賠額+";
				compensateData.lineM2 += new Double(exceptDeductiblePayR).toString() + "+";
			}
			dblsumrealPay = 0;

		} else if (iKindCode.trim().equals("S")) {
			/*
			 * ------------------------------------------------------------------
			 * ---------------------- 机动车出境保险
			 * ------------------------------------
			 * ----------------------------------------------------
			 */
			int flagS = 0;
			strLctextList.add(lineText);
			double exceptDeductiblePayS = 0d;
			double dblchargesumrealpayS = 0d;
			for (int i = 0; i < prpLlossList.size(); i++) {
				prpLloss = prpLlossList.get(i);
				if ("S".equals(prpLloss.getKindCode())) {
					dblsumrealPay += prpLloss.getSumRealPay();
					dblothsumrealpay += prpLloss.getSumRealPay();
					exceptDeductiblePayS = prpLloss.getExceptDeductiblePay();
					exceptDeductiblePayOther += prpLloss.getExceptDeductiblePay();
				}
			}
			for (PrpLpersonLoss prpLpersonLoss : compensateDto.getPrpLpersonLossList()) {
				if (prpLpersonLoss.getKindCode().equals("S")) {
					dblsumrealPay += prpLpersonLoss.getSumRealPay();
					dblothsumrealpay += prpLpersonLoss.getSumRealPay();
					exceptDeductiblePayS = prpLpersonLoss.getExceptDeductiblePay();
					exceptDeductiblePayOther += prpLpersonLoss.getExceptDeductiblePay();
				}
			}
//			for (PrpLcharge prpLcharge : compensateDto.getPrpLchargeList()) {
//				if ("S".equals(prpLcharge.getKindCode())) {
//					/**
//					 * 施救费要按车险公式进行计算
//					 */
//					if ("03".equals(prpLcharge.getChargeCode())) {
//						dblchargesumrealpayS += prpLcharge.getSumRealPay();
//						dblchargesumrealpay += prpLcharge.getSumRealPay();
//						dblothsumrealpay += prpLcharge.getSumRealPay();
//						exceptDeductiblePayS += prpLcharge.getExceptDeductiblePay();
//						exceptDeductiblePayOther += prpLcharge.getExceptDeductiblePay();
//						flagS = 1;
//					}
//				}
//			}
			lineText = "機動車出境保險=";
			if (flagS == 1) {
				lineText += "實賠金額+施救費";
				strLctextList.add(lineText);
				lineText = space(14) + "=" + formatPay(dblsumrealPay) + "+" + formatPay(dblchargesumrealpayS);
				strLctextList.add(lineText);
				lineText = space(14) + "=";
			}
			lineText += formatPay(dblsumrealPay + dblchargesumrealpayS) + "元";
			if (new Double(exceptDeductiblePayS).intValue() != 0) {
				compensateData.lineM1 += "機動車出境保險不計免賠額+";
				compensateData.lineM2 += new Double(exceptDeductiblePayS).toString() + "+";
			}
			dblsumrealPay = 0;
		} else if (iKindCode.trim().equals("U")) {
			/*
			 * ------------------------------------------------------------------
			 * ---------------------- 车辆不计免赔特约保险
			 * --------------------------------
			 * --------------------------------------------------------
			 */
		} else if (iKindCode.trim().equals("V1")) {

			strLctextList.add(lineText);
			double exceptDeductiblePayV1 = 0d;
			for (int i = 0; i < prpLlossList.size(); i++) {
				prpLloss = prpLlossList.get(i);
				if ("V1".equals(prpLloss.getKindCode())) {
					dblsumrealPay += prpLloss.getSumRealPay();
					dblothsumrealpay += prpLloss.getSumRealPay();
					exceptDeductiblePayV1 = prpLloss.getExceptDeductiblePay();
					exceptDeductiblePayOther += prpLloss.getExceptDeductiblePay();
				}
			}
			lineText = "油污污染責任保險=";
			lineText += formatPay(dblsumrealPay) + "元";
			if (new Double(exceptDeductiblePayV1).intValue() != 0) {
				compensateData.lineM1 += "油污污染責任保險不計免賠額+";
				compensateData.lineM2 += new Double(exceptDeductiblePayV1).toString() + "+";
			}
			dblsumrealPay = 0;

		} else if (iKindCode.trim().equals("X")) {
			/*
			 * ------------------------------------------------------------------
			 * ---------------------- 新增加设备损失保险
			 * ----------------------------------
			 * ------------------------------------------------------
			 */
			strLctextList.add(lineText);
			double exceptDeductiblePayX = 0d;
			for (int i = 0; i < prpLlossList.size(); i++) {
				prpLloss = prpLlossList.get(i);
				if ("X".equals(prpLloss.getKindCode())) {
					dblsumrealPay += prpLloss.getSumRealPay();
					dblothsumrealpay += prpLloss.getSumRealPay();
					exceptDeductiblePayX = prpLloss.getExceptDeductiblePay();
					exceptDeductiblePayOther += prpLloss.getExceptDeductiblePay();
				}
			}
			lineText = "新增加設備損失保險=";
			lineText += formatPay(dblsumrealPay) + "元";
			if (new Double(exceptDeductiblePayX).intValue() != 0) {
				compensateData.lineM1 += "新增加設備損失險不計免賠額+";
				compensateData.lineM2 += new Double(exceptDeductiblePayX).toString() + "+";
			}
			dblsumrealPay = 0;
		} else if (iKindCode.trim().equals("Y")) {
			/*
			 * ------------------------------------------------------------------
			 * ---------------------- 教练车特约
			 * --------------------------------------
			 * --------------------------------------------------
			 */
			int flagY = 0;
			strLctextList.add(lineText);
			double exceptDeductiblePayY = 0d;
			double dblchargesumrealpayY = 0d;
			for (int i = 0; i < prpLlossList.size(); i++) {
				prpLloss = prpLlossList.get(i);
				if ("Y".equals(prpLloss.getKindCode())) {
					dblsumrealPay += prpLloss.getSumRealPay();
					dblothsumrealpay += prpLloss.getSumRealPay();
					exceptDeductiblePayY = prpLloss.getExceptDeductiblePay();
					exceptDeductiblePayOther += prpLloss.getExceptDeductiblePay();
				}
			}
			for (PrpLpersonLoss prpLpersonLoss : compensateDto.getPrpLpersonLossList()) {
				if ("Y".equals(prpLpersonLoss.getKindCode())) {
					dblsumrealPay += prpLpersonLoss.getSumRealPay();
					dblothsumrealpay += prpLpersonLoss.getSumRealPay();
					exceptDeductiblePayY = prpLpersonLoss.getExceptDeductiblePay();
					exceptDeductiblePayOther += prpLpersonLoss.getExceptDeductiblePay();
				}
			}
//			for (PrpLcharge prpLcharge : compensateDto.getPrpLchargeList()) {
//				if ("Y".equals(prpLcharge.getKindCode())) {
//					/**
//					 * 施救费要按车险公式进行计算
//					 */
//					if ("03".equals(prpLcharge.getChargeCode())) {
//						dblchargesumrealpayY += prpLcharge.getSumRealPay();
//						dblchargesumrealpay += prpLcharge.getSumRealPay();
//						dblothsumrealpay += prpLcharge.getSumRealPay();
//						exceptDeductiblePayY += prpLcharge.getExceptDeductiblePay();
//						exceptDeductiblePayOther += prpLcharge.getExceptDeductiblePay();
//						flagY = 1;
//					}
//				}
//			}
			lineText = "教練車特約條款=";
			if (flagY == 1) {
				lineText += "實賠金額+施救費";
				strLctextList.add(lineText);
				lineText = space(14) + "=" + formatPay(dblsumrealPay) + "+" + formatPay(dblchargesumrealpayY);
				strLctextList.add(lineText);
				lineText = space(14) + "=";
			}
			lineText += formatPay(dblsumrealPay + dblchargesumrealpayY) + "元";
			if (new Double(exceptDeductiblePayY).intValue() != 0) {
				compensateData.lineM1 += "教練車特約條款不計免賠額+";
				compensateData.lineM2 += new Double(exceptDeductiblePayY).toString() + "+";
			}
			dblsumrealPay = 0;
		} else if (iKindCode.trim().equals("Z")) {
			/*
			 * ------------------------------------------------------------------
			 * ---------------------- 自燃损失险
			 * --------------------------------------
			 * --------------------------------------------------
			 */
			int flagZ = 0;
			strLctextList.add(lineText);
			double exceptDeductiblePayZ = 0d;
			double dblchargesumrealpayZ = 0d;
			for (int i = 0; i < prpLlossList.size(); i++) {
				prpLloss = prpLlossList.get(i);
				if ("Z".equals(prpLloss.getKindCode())) {
					dblsumrealPay += prpLloss.getSumRealPay();
					dblothsumrealpay += prpLloss.getSumRealPay();
					exceptDeductiblePayZ = prpLloss.getExceptDeductiblePay();
					exceptDeductiblePayOther += prpLloss.getExceptDeductiblePay();
				}
			}
//			for (PrpLcharge prpLcharge : compensateDto.getPrpLchargeList()) {
//				if ("Z".equals(prpLcharge.getKindCode())) {
//					/**
//					 * 施救费要按车险公式进行计算
//					 */
//					if ("03".equals(prpLcharge.getChargeCode())) {
//						dblchargesumrealpayZ += prpLcharge.getSumRealPay();
//						dblchargesumrealpay += prpLcharge.getSumRealPay();
//						dblothsumrealpay += prpLcharge.getSumRealPay();
//						exceptDeductiblePayZ += prpLcharge.getExceptDeductiblePay();
//						exceptDeductiblePayOther += prpLcharge.getExceptDeductiblePay();
//						flagZ = 1;
//					}
//				}
//			}
			lineText = "自燃損失險=";
			if (flagZ == 1) {
				lineText += "實賠金額+施救費";
				strLctextList.add(lineText);
				lineText = space(10) + "=" + formatPay(dblsumrealPay) + "+" + formatPay(dblchargesumrealpayZ);
				strLctextList.add(lineText);
				lineText = space(10) + "=";
			}
			lineText += formatPay(dblsumrealPay + dblchargesumrealpayZ) + "元";
			if (new Double(exceptDeductiblePayZ).intValue() != 0) {
				compensateData.lineM1 += "自燃損失險不計免賠額+";
				compensateData.lineM2 += new Double(exceptDeductiblePayZ).toString() + "+";
			}
			dblsumrealPay = 0;

		} else if (iKindCode.equals("T")) {
			/*
			 * ------------------------------------------------------------------
			 * ---------------------- 车辆停驶险、代步险费用险
			 * ------------------------------
			 * ----------------------------------------------------------
			 */
			String isLossAll = httpServletRequest.getParameter("prpLlossDtoIsLossAll");
			double unitValue = 0d;
			double days = 0d;
			for (int i = 0; i < prpLlossList.size(); i++) {
				prpLloss = prpLlossList.get(i);
				if ("T".equals(prpLloss.getKindCode())) {
					dblsumrealPay += prpLloss.getSumRealPay();
					dblothsumrealpay += prpLloss.getSumRealPay();
					break;
				}
			}
			strLctextList.add(lineText);
			if (iKindCode.equals("T")) {
				lineText = "車輛停駛險：";
			}
			if ("Y".equals(isLossAll)) {
				lineText += "全損";
				strLctextList.add(lineText);
				lineText = space(4) + "本項實賠金額=" + formatPay(prpLloss.getSumRealPay()) + "元";
			} else {
				strLctextList.add(lineText);
				lineText = space(4) + "本項實賠金額=(約定日賠償金額× 賠償天數)";
				unitValue = prpLloss.getUnitPrice();
				days = prpLloss.getLossQuantity();
				strLctextList.add(lineText);
				if (unitValue > 300)
					unitValue = 300;
				if (days > 60)
					days = 60;
				lineText = space(16) + "=(" + formatPay(unitValue) + "×" + formatPay(days) + ")";
				strLctextList.add(lineText);
				lineText = space(16) + "=" + formatPay(prpLloss.getSumRealPay()) + "元";
			}
		} else if (iKindCode.equals("F")) {
			/*
			 * ------------------------------------------------------------------
			 * ---------------------- 玻璃单独破碎险
			 * ------------------------------------
			 * ----------------------------------------------------
			 */
			double amount = 0d;
			PrpLclaim prpLclaim = compensateDto.getPrpLclaim();
			String policyNo = prpLclaim.getPolicyNo();
			String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
			String damageHour = prpLclaim.getDamageStartHour();
			List<PrpCitemKind> limitlist = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, null , null);
			// 取保额
			if (limitlist != null && limitlist.size() > 0) {
				Iterator<PrpCitemKind> it = limitlist.iterator();
				PrpCitemKind prpCitemKind = null;
				while (it.hasNext()) {
					prpCitemKind = it.next();
					if ("A".equals(prpCitemKind.getKindCode())) {
						amount = prpCitemKind.getAmount();
						break;
					}
				}
			}
			strLctextList.add(lineText);
			lineText = "玻璃單獨破碎險：";
			for (int i = 0; i < prpLlossList.size(); i++) {
				prpLloss = prpLlossList.get(i);
				if (prpLloss.getKindCode().equals(iKindCode.trim())) {
					dblsumloss += prpLloss.getSumLoss();
					dblsumdefPay += prpLloss.getSumDefPay();
					dblothsumrealpay += prpLloss.getSumRealPay();
					dblsumrealPay += prpLloss.getSumRealPay();
					PropertyUtils.copyProperties(prpLlossbak, prpLloss);
				}
			}
			if (dblsumdefPay > amount) {
				strLctextList.add(lineText);
				lineText = space(4) + "本項實賠金額 = 保額 =" + formatPay(amount) + "元";
			} else {
				strLctextList.add(lineText);
				lineText = space(4) + "本項實賠金額=核定賠償金額=" + formatPay(dblsumrealPay) + "元";
			}
			dblsumloss = 0;
			dblsumdefPay = 0;
			dblsumrealPay = 0;
		} else if (iKindCode.equals("L")) {
			/*
			 * ------------------------------------------------------------------
			 * ---------------------- 车身划痕险
			 * --------------------------------------
			 * --------------------------------------------------
			 */
			double exceptDeductiblePayL = 0.0;
			double aMount = 0.0;
			PrpLclaim prpLclaim = compensateDto.getPrpLclaim();
			String policyNo = prpLclaim.getPolicyNo();
			String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
			String damageHour = prpLclaim.getDamageStartHour();
			List<PrpCitemKind> limitlist = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, null, null);
			// 取保额
			if (limitlist != null && limitlist.size() > 0) {
				Iterator<PrpCitemKind> it = limitlist.iterator();
				while (it.hasNext()) {
					PrpCitemKind prpCitemKind = it.next();
					if ("L".equals(prpCitemKind.getKindCode())) {
						aMount = prpCitemKind.getAmount();
						break;
					}
				}
			}
			for (int i = 0; i < prpLlossList.size(); i++) {
				prpLloss = prpLlossList.get(i);
				if ("L".equals(prpLloss.getKindCode())) {
					exceptDeductiblePayOther += prpLloss.getExceptDeductiblePay();
					exceptDeductiblePayL += prpLloss.getExceptDeductiblePay();
					dblsumloss += prpLloss.getSumLoss();
					dblsumdefPay += prpLloss.getSumDefPay();
					dblothsumrealpay += prpLloss.getSumRealPay();
					dblsumrealPay += prpLloss.getSumRealPay();
					PropertyUtils.copyProperties(prpLlossbak, prpLloss);
				}
			}
			lineText = "車身劃痕險:";
			strLctextList.add(lineText);
			if (dblsumdefPay <= aMount) {
				lineText = space(4) + "本項實賠金額= 核定賠償金額×（1-免賠率）";
				strLctextList.add(lineText);
				lineText = space(17) + "=" + formatPay(dblsumdefPay) + "×(1-" + formatPay(prpLlossbak.getDeductiblerate()) + "％)";
				dblsumdefPay = dblsumdefPay * (1 - prpLlossbak.getDeductiblerate() / 100);
				strLctextList.add(lineText);
				lineText = space(17) + "=" + formatPay(dblsumdefPay) + "元";
			} else {
				lineText = space(4) + "本項實賠金額= 保險金額×（1-免賠率）";
				strLctextList.add(lineText);
				lineText = space(17) + "=" + formatPay(aMount) + "×(1-" + formatPay(prpLlossbak.getDeductiblerate()) + "％)";
				strLctextList.add(lineText);
				lineText = space(17) + "=" + formatPay(dblsumrealPay) + "元";
			}
			if (new Double(exceptDeductiblePayL).intValue() != 0) {
				compensateData.lineM1 += "車身劃痕險不計免賠額+";
				compensateData.lineM2 += new Double(exceptDeductiblePayL).toString() + "+";
			}
			dblsumloss = 0;
			dblsumdefPay = 0;
			dblsumrealPay = 0;
		} else if (iKindCode.equals("E")) {
			/*
			 * ------------------------------------------------------------------
			 * ---------------------- 火灾、爆炸、自燃损失险条款
			 * ------------------------------
			 * ----------------------------------------------------------
			 */
			strLctextList.add(lineText);
			int flagE = 0;
			double exceptDeductiblePayE = 0d;
			double dblchargesumrealpayE = 0d;
			for (int i = 0; i < prpLlossList.size(); i++) {
				prpLloss = prpLlossList.get(i);
				if (prpLloss.getKindCode().equals("E")) {
					dblsumrealPay += prpLloss.getSumRealPay();
					dblothsumrealpay += prpLloss.getSumRealPay();
					exceptDeductiblePayE = prpLloss.getExceptDeductiblePay();
					exceptDeductiblePayOther += prpLloss.getExceptDeductiblePay();
				}
			}
//			for (PrpLcharge prpLcharge : compensateDto.getPrpLchargeList()) {
//				if ("E".equals(prpLcharge.getKindCode())) {
//					/**
//					 * 施救費要按車險公式進行計算
//					 */
//					if ("03".equals(prpLcharge.getChargeCode())) {
//						dblchargesumrealpayE += prpLcharge.getSumRealPay();
//						dblchargesumrealpay += prpLcharge.getSumRealPay();
//						dblothsumrealpay += prpLcharge.getSumRealPay();
//						exceptDeductiblePayE += prpLcharge.getExceptDeductiblePay();
//						exceptDeductiblePayOther += prpLcharge.getExceptDeductiblePay();
//						flagE = 1;
//					}
//				}
//			}
			lineText = "火災、爆炸、自燃損失險=";
			if (flagE == 1) {
				lineText += "實賠金額+施救費";
				strLctextList.add(lineText);
				lineText = space(10) + "=" + formatPay(dblsumrealPay) + "+" + formatPay(dblchargesumrealpayE);
				strLctextList.add(lineText);
				lineText = space(10) + "=";
			}
			lineText += formatPay(dblsumrealPay + dblchargesumrealpayE) + "元";
			if (new Double(exceptDeductiblePayE).intValue() != 0) {
				compensateData.lineM1 += "火災、爆炸、自燃損失險不計免賠額+";
				compensateData.lineM2 += new Double(exceptDeductiblePayE).toString() + "+";
			}
			dblsumdefPay = 0;
			dblsumrealPay = 0;
		} else if (iKindCode.equals("R")) {
			/*
			 * ------------------------------------------------------------------
			 * ---------------------- 驾驶员责任险、乘客责任险、交通意外精神损失险、车载货物掉落险
			 * ------------
			 * ------------------------------------------------------
			 * ----------------------
			 */
		} else {
			/*
			 * ------------------------------------------------------------------
			 * ---------------------- 其他险
			 * ----------------------------------------
			 * ------------------------------------------------
			 */
		}
		strLctextList.add(lineText);
		compensateData.dblChargeSumRealPay += dblchargesumrealpay;
		compensateData.dblAllExceptDeductiblePay += exceptDeductiblePayOther;
		compensateData.dblOthSumRealPay += dblothsumrealpay;
		compensateData.dblAllSumRealPay += dblothsumrealpay + dblnofranchise + exceptDeductiblePayOther;
		compensateData.dblNoFranchise += dblnofranchise;
		List<PrpLctext> prpLctextList = compensateDto.getPrpLctextList();
		int ctextcount = 0;
		if (prpLctextList != null && !prpLctextList.isEmpty()) {
			ctextcount = prpLctextList.size();
		}
		logger.debug("附加险-ctextcount:" + ctextcount);
		PrpLctext prpLctext = null;
		for (String lineTextTemp : strLctextList) {
			prpLctext = new PrpLctext();
			prpLctext.getId().setCompensateNo(strCompensateNo);
			prpLctext.getId().setTextType("1");
			prpLctext.getId().setLineNo(ctextcount++);
			prpLctext.setContext(lineTextTemp);
			prpLctext.setFlag("");
			compensateData.prpLctextlist.add(prpLctext);
		}
	}

	@SuppressWarnings("unchecked")
	public String CarCreateForFee(PrpLpersonLoss prpLpersonLoss, CompensateDto compensateDto, CompensateData compensateData) throws UserException, Exception {

		String strFeeReport = "";
		int intpayyears = 0;
		UIPrpDpersonPayAction uiPrpDpersonPayAction = new UIPrpDpersonPayAction();
		PrpDpersonPayDto prpDpersonPayDto = new PrpDpersonPayDto();
		PrpLclaim prpLclaim = compensateDto.getPrpLclaim();
		String strSQL = " ClaimNo = '" + prpLclaim.getClaimNo() + "' AND PersonNo = '" + prpLpersonLoss.getPersonNo() + "' AND KindCode = '" + prpLpersonLoss.getKindCode() + "' AND FeeTypeCode = '" + prpLpersonLoss.getLiabDetailCode() + "' ";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(strSQL);
		List<PrpLperson> prpLpersonList = this.getPrpLpersonService().findPrpLperson(queryRule);
		if (prpLpersonList == null || prpLpersonList.isEmpty()) {
			strFeeReport = space(4) + prpLpersonLoss.getLiabDetailName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getSumDefPay()) + "元"; // SumLoss改为SumDefPay
			return strFeeReport;
		}

		PrpLperson prpLpersonDto = prpLpersonList.get(0);
		if ("15".equals(prpLpersonLoss.getLiabDetailCode())) {
			if (prpLpersonDto.getFixedIncomeFlag().equals("1")) {
				strSQL = " PayItemCode = '01' AND DamageAreaCode = '" + prpLpersonDto.getAreaCode() + "' AND BusinessSource = '0000' AND issuedate = '" + prpLclaim.getDamageStartDate() + "' ";
				List<PrpDpersonPayDto> prpDpersonPaylist = (ArrayList<PrpDpersonPayDto>) uiPrpDpersonPayAction.findByConditions(strSQL);
				// 人员赔付误工费等生成赔款计算书报错
				if (prpDpersonPaylist != null && !prpDpersonPaylist.isEmpty()) {
					prpDpersonPayDto = (PrpDpersonPayDto) prpDpersonPaylist.get(0);
					if (prpLpersonDto.getSumLoss() / prpLpersonDto.getQuantity() > prpDpersonPayDto.getStandardFee() / 365 * 3) {
						prpLpersonDto.setSumDefLoss(prpDpersonPayDto.getStandardFee() / 365 * 3 * prpLpersonDto.getQuantity() - prpLpersonDto.getSumReject());
						strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getUnitAmount()) + "(3倍標準)×" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getLossQuantity())
								+ "(天數)=" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getSumDefPay()) + "元"; // SumLoss改為SumDefPay
					} else {
						strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getUnitAmount()) + "(3倍標準)×" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getLossQuantity())
								+ "(天數)=" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getSumDefPay()) + "元"; // SumLoss改為SumDefPay
					}
				} else {
					strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getSumDefPay()) + "元"; // SumLoss改为SumDefPay
				}
			} else if (prpLpersonDto.getFixedIncomeFlag().equals("2")) {
				strSQL = " PayItemCode = '07' AND DamageAreaCode = '" + prpLpersonDto.getAreaCode() + "' AND BusinessSource = '" + prpLpersonDto.getJobCode() + "' AND issuedate = '" + prpLclaim.getDamageStartDate() + "' ";
				List<PrpDpersonPayDto> prpDpersonPaylist = (ArrayList<PrpDpersonPayDto>) uiPrpDpersonPayAction.findByConditions(strSQL);
				if (prpDpersonPaylist.size() > 0) {
					prpDpersonPayDto = (PrpDpersonPayDto) prpDpersonPaylist.get(0);
					strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getUnitAmount()) + "(3倍标准)×" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getLossQuantity()) + "(天数)="
							+ new DecimalFormat("#,##0.00").format(prpLpersonLoss.getSumDefPay()) + "元"; // SumLoss改为SumDefPay
				} else {
					strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getSumDefPay()) + "元"; // SumLoss改为SumDefPay
				}
			}
		} else if ("17".equals(prpLpersonLoss.getLiabDetailCode())) {
			strSQL = " PayItemCode = '01' AND DamageAreaCode = '" + prpLpersonDto.getAreaCode() + "' AND BusinessSource = '0000' AND issuedate = '" + prpLclaim.getDamageStartDate() + "' ";
			List<PrpDpersonPayDto> prpDpersonPaylist = (ArrayList<PrpDpersonPayDto>) uiPrpDpersonPayAction.findByConditions(strSQL);
			if (prpDpersonPaylist.size() > 0) {
				prpDpersonPayDto = (PrpDpersonPayDto) prpDpersonPaylist.get(0);
				if (prpLpersonDto.getPersonAge() > 50) {
					intpayyears = prpLpersonDto.getPersonAge() - 50;
					if (intpayyears > 10 && intpayyears < 20)
						intpayyears = 10;
					else if (intpayyears >= 20)
						intpayyears = 5;
				} else {
					intpayyears = 20;
				}
				strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpDpersonPayDto.getStandardFee()) + "(人均生活費)×" + new DecimalFormat("#,##0.00").format((double) intpayyears) + "(賠償年限)×"
						+ new DecimalFormat("#,##0.00").format(prpLpersonDto.getLossRate()) + "％(傷殘等級)=" + new DecimalFormat("#,##0.00").format(prpLpersonDto.getSumDefLoss()) + "元";
			} else {
				strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonDto.getSumDefLoss()) + "元";
			}
		} else if ("17".equals(prpLpersonLoss.getLiabDetailCode())) {
			if (prpLpersonDto.getFixedIncomeFlag().equals("1")) {
				strSQL = " PayItemCode = '01' AND DamageAreaCode = '" + prpLpersonDto.getAreaCode() + "' AND BusinessSource = '0000' AND issuedate = '" + prpLclaim.getDamageStartDate() + "' ";
				List<PrpDpersonPayDto> prpDpersonPaylist = (ArrayList<PrpDpersonPayDto>) uiPrpDpersonPayAction.findByConditions(strSQL);
				if (prpDpersonPaylist.size() > 0) {
					prpDpersonPayDto = (PrpDpersonPayDto) prpDpersonPaylist.get(0);
					if (prpLpersonDto.getSumLoss() / prpLpersonDto.getQuantity() > prpDpersonPayDto.getStandardFee() / 365 * 3) {
						prpLpersonDto.setSumDefLoss(prpDpersonPayDto.getStandardFee() / 365 * 3 * prpLpersonDto.getQuantity() - prpLpersonDto.getSumReject());
						strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getUnitAmount()) + "(3倍标准)×" + new DecimalFormat("#,##0.00").format(prpLpersonDto.getQuantity()) + "(天数)="
								+ new DecimalFormat("#,##0.00").format(prpLpersonLoss.getSumDefPay()) + "元"; // SumLoss改为SumDefPay
					} else {
						strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getUnitAmount()) + "(3倍标准)×" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getLossQuantity())
								+ "(天数)=" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getSumDefPay()) + "元"; // SumLoss改为SumDefPay
					}
				} else {
					strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getSumDefPay()) + "元"; // SumLoss改为SumDefPay
				}
			} else if ("2".equals(prpLpersonDto.getFixedIncomeFlag())) {
				strSQL = " PayItemCode = '07' AND DamageAreaCode = '" + prpLpersonDto.getAreaCode() + "' AND BusinessSource = '" + prpLpersonDto.getJobCode() + "' AND issuedate = '" + prpLclaim.getDamageStartDate() + "' ";
				List<PrpDpersonPayDto> prpDpersonPaylist = (ArrayList<PrpDpersonPayDto>) uiPrpDpersonPayAction.findByConditions(strSQL);
				if (prpDpersonPaylist.size() > 0) {
					prpDpersonPayDto = (PrpDpersonPayDto) prpDpersonPaylist.get(0);
					strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getUnitAmount()) + "(标准)×" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getLossQuantity()) + "(天数)="
							+ new DecimalFormat("#,##0.00").format(prpLpersonLoss.getSumDefPay()) + "元"; // SumLoss改为SumDefPay
				} else {
					strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getSumDefPay()) + "元"; // SumLoss改为SumDefPay
				}
			} else if ("3".equals(prpLpersonDto.getFixedIncomeFlag())) {
				strSQL = " PayItemCode = '01' AND DamageAreaCode = '" + prpLpersonDto.getAreaCode() + "' AND BusinessSource = '0000' AND issuedate = '" + prpLclaim.getDamageStartDate() + "' ";
				List<PrpDpersonPayDto> prpDpersonPaylist = (ArrayList<PrpDpersonPayDto>) uiPrpDpersonPayAction.findByConditions(strSQL);
				if (prpDpersonPaylist.size() > 0) {
					prpDpersonPayDto = (PrpDpersonPayDto) prpDpersonPaylist.get(0);
					prpLpersonDto.setUnitLoss(prpDpersonPayDto.getStandardFee() / 365);
					strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getUnitAmount()) + "(标准)×" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getLossQuantity()) + "(天数)="
							+ new DecimalFormat("#,##0.00").format(prpLpersonLoss.getSumDefPay()) + "元"; // SumLoss改为SumDefPay
				} else {
					strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getSumDefPay()) + "元";// SumLoss改为SumDefPay
				}
			}
		} else if ("19".equals(prpLpersonLoss.getLiabDetailCode())) {
			strSQL = " PayItemCode = '01' AND DamageAreaCode = '" + prpLpersonDto.getAreaCode() + "' AND BusinessSource = '0000' AND issuedate = '" + prpLclaim.getDamageStartDate() + "' ";
			List<PrpDpersonPayDto> prpDpersonPaylist = (ArrayList<PrpDpersonPayDto>) uiPrpDpersonPayAction.findByConditions(strSQL);
			if (prpDpersonPaylist.size() > 0) {
				prpDpersonPayDto = (PrpDpersonPayDto) prpDpersonPaylist.get(0);
				if (prpLpersonDto.getPersonAge() > 70) {
					intpayyears = prpLpersonDto.getPersonAge() - 70;
					if (intpayyears > 5)
						intpayyears = 5;
					else
						intpayyears = 10 - intpayyears;
				} else if (prpLpersonDto.getPersonAge() < 16) {
					intpayyears = 16 - prpLpersonDto.getPersonAge();
					if (intpayyears > 5)
						intpayyears = 5;
					else
						intpayyears = 10 - intpayyears;
				} else {
					intpayyears = 10;
				}
				strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpDpersonPayDto.getStandardFee()) + "(平均生活費)×" + new DecimalFormat("#,##0.00").format((double) intpayyears) + "(賠償年限)="
						+ new DecimalFormat("#,##0.00").format(prpLpersonDto.getSumDefLoss()) + "元";
			} else {
				strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonDto.getSumDefLoss()) + "元";
			}
		} else if ("20".equals(prpLpersonLoss.getLiabDetailCode())) {
			strSQL = " PayItemCode = '04' AND DamageAreaCode = '" + prpLpersonDto.getAreaCode() + "' AND BusinessSource = '0000' AND issuedate = '" + prpLclaim.getDamageStartDate() + "' ";
			List<PrpDpersonPayDto> prpDpersonPaylist = (ArrayList<PrpDpersonPayDto>) uiPrpDpersonPayAction.findByConditions(strSQL);
			if (prpDpersonPaylist.size() > 0) {
				prpDpersonPayDto = (PrpDpersonPayDto) prpDpersonPaylist.get(0);
				prpLpersonDto.setSumDefLoss(prpDpersonPayDto.getStandardFee() - prpLpersonDto.getSumReject());
				strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpDpersonPayDto.getStandardFee()) + "(喪葬標準)元";
			} else {
				strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonDto.getSumDefLoss()) + "元";
			}
		} else if ("21".equals(prpLpersonLoss.getLiabDetailCode())) {
			if (prpLpersonDto.getFixedIncomeFlag().equals("4")) {
				if (prpLpersonDto.getPersonAge() > 50) {
					intpayyears = prpLpersonDto.getPersonAge() - 50;
					if (intpayyears > 10 && intpayyears < 20)
						intpayyears = 10;
					else if (intpayyears >= 20)
						intpayyears = 5;
				}
			} else {
				if (prpLpersonDto.getPersonAge() < 16)
					intpayyears = 16 - prpLpersonDto.getPersonAge();
				else
					intpayyears = 5;
			}
			strSQL = " PayItemCode = '06' AND DamageAreaCode = '" + prpLpersonDto.getAreaCode() + "' AND BusinessSource = '0000' AND issuedate = '" + prpLclaim.getDamageStartDate() + "' ";
			List prpDpersonPaylist = (ArrayList) uiPrpDpersonPayAction.findByConditions(strSQL);
			if (prpDpersonPaylist.size() > 0) {
				strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpDpersonPayDto.getStandardFee()) + "(生活困難補助標準)×" + new DecimalFormat("#,##0.00").format((double) intpayyears) + "(賠償年限)="
						+ new DecimalFormat("#,##0.00").format(prpLpersonDto.getSumDefLoss()) + "元";
			} else {
				strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonDto.getSumDefLoss()) + "元";
			}
		} else if ("23".equals(prpLpersonLoss.getLiabDetailCode())) {
			strSQL = " PayItemCode = '02' AND DamageAreaCode = '" + prpLpersonDto.getAreaCode() + "' AND BusinessSource = '0000' AND issuedate = '" + prpLclaim.getDamageStartDate() + "' ";
			List prpDpersonPaylist = (ArrayList) uiPrpDpersonPayAction.findByConditions(strSQL);
			if (prpDpersonPaylist.size() > 0) {
				strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getUnitAmount()) + "(出差住宿标准)×" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getLossQuantity()) + "(天数)="
						+ new DecimalFormat("#,##0.00").format(prpLpersonLoss.getSumDefPay()) + "元";// //SumLoss改为SumDefPay
			} else {
				strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonDto.getSumDefLoss()) + "元";
			}
		} else if ("34".equals(prpLpersonLoss.getLiabDetailCode())) {
			strSQL = " PayItemCode = '03' AND DamageAreaCode = '" + prpLpersonDto.getAreaCode() + "' AND BusinessSource = '0000' AND issuedate = '" + prpLclaim.getDamageStartDate() + "' ";
			List prpDpersonPaylist = (ArrayList) uiPrpDpersonPayAction.findByConditions(strSQL);
			if (prpDpersonPaylist.size() > 0) {
				strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getUnitAmount()) + "(出差伙食補助標準)×" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getLossQuantity()) + "(天數)="
						+ new DecimalFormat("#,##0.00").format(prpLpersonLoss.getSumDefPay()) + "元"; // SumLoss改為SumDefPay
			} else {
				strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonDto.getSumDefLoss()) + "元";
			}
		} else {
			strFeeReport = space(4) + prpLpersonDto.getFeeTypeName() + ":" + new DecimalFormat("#,##0.00").format(prpLpersonLoss.getSumDefPay()) + "元"; // SumLoss改为SumDefPay
		}
		return strFeeReport;
	}

	private static class CompensateData {

		private double dblCarSumRealPay = 0; // 车损险实赔
		private double dblThirdSumRealPay = 0; // 三者险实赔
		private double dblCarPersonSumRealpay = 0; // 车上人员责任险实赔
		private double dblCarStealSumRealPay = 0; // 盗抢险实赔
		private double dblOthSumRealPay = 0; // 附加险实赔
		private double dblChargeSumRealPay = 0; // 费用实赔
		private double dblNoFranchise = 0; //
		private double dblAllSumRealPay = 0; // 实赔合计
		private String strEscapeFlag = ""; // 是否为逃逸案
		private String lineM1 = "";
		private String lineM2 = "";
		private double dblAllExceptDeductiblePay = 0; // 不计免赔率赔款金额
		List<PrpLctext> prpLctextlist = new ArrayList<PrpLctext>();
	}

	// 车上乘客
	public void CarCreateForD12(HttpServletRequest httpServletRequest, CompensateDto compensateDto, CompensateData compensateData) throws SQLException, UserException, Exception {
		int personcount = 0;
		int j = 0;
		double dblsumloss = 0;
		double dblsumdefpay = 0;
		double dblsumrealpay = 0;
		double dblAllSumRealPayD12 = 0;
		double dblCompelPay = 0d;
		double dbExceptDeductiblePayD12 = 0.0;// 总的不计免赔额
		double dblunitamount = 0;
		String[] arrKindCode1 = new String[20];
		String[] arrPersonNo = new String[20];
		String[] arrPersonName = new String[20];
		String[] arrFamilyName = new String[20];
		String strCompensateNo = compensateDto.getPrpLcompensate().getCompensateNo();
		logger.debug("產生的计算书号:" + strCompensateNo);
		PrpLpersonLoss prpLpersonLoss = new PrpLpersonLoss();
		PrpLpersonLoss prpLpersonLossbak = new PrpLpersonLoss();
		// 取限额
		PolicyDto policyDto = this.getPolicyService().findByPrimaryKey(compensateDto.getPrpLcompensate().getPolicyNo());
		List<PrpCitemKind> limitlist = policyDto.getPrpCitemKindList();
		Iterator<PrpCitemKind> it = limitlist.iterator();
		if (limitlist != null && limitlist.size() > 0) {
			while (it.hasNext()) {
				PrpCitemKind prpCitemKind = it.next();
				if ("D12".equals(prpCitemKind.getKindCode())) {
					dblunitamount = prpCitemKind.getUnitAmount();
					break;
				}
			}
		}
		List<PrpLpersonLoss> prpLpersonLossList = compensateDto.getPrpLpersonLossList();
		for (int i = 0; i < prpLpersonLossList.size(); i++) {
			prpLpersonLoss = prpLpersonLossList.get(i);
			for (j = 1; j <= personcount; j++) {
				if (arrKindCode1[j].indexOf(prpLpersonLoss.getKindCode()) < 0 && String.valueOf(prpLpersonLoss.getPersonNo()).equals(arrPersonNo[j].trim()))
					arrKindCode1[j] = arrKindCode1[j].trim() + prpLpersonLoss.getKindCode();
				if (String.valueOf(prpLpersonLoss.getPersonNo()).equals(arrPersonNo[j].trim()))
					break;
			}
			if (j > personcount) {
				personcount++;
				arrKindCode1[personcount] = prpLpersonLoss.getKindCode();
				arrPersonNo[personcount] = String.valueOf(prpLpersonLoss.getPersonNo());
				arrPersonName[personcount] = prpLpersonLoss.getPersonName();
				arrFamilyName[personcount] = prpLpersonLoss.getFamilyName();
			}
		}
		List<String> strLctextList = new ArrayList<String>();
		String lineText = "車上人員責任險(乘客)：";
		for (j = 1; j <= personcount; j++) {
			dblsumloss = 0;
			dblsumdefpay = 0;
			dblsumrealpay = 0;
			dblCompelPay = 0d;
			if (arrKindCode1[j].indexOf("D12") < 0)
				continue;
			strLctextList.add(lineText);
			lineText = space(4) + "車輛號牌號碼為" + arrFamilyName[j].trim() + "的出險人員" + "" + arrPersonName[j].trim() + "：";
			strLctextList.add(lineText);
			lineText = space(4) + "本項賠款金額=";

			for (int i = 0; i < prpLpersonLossList.size(); i++) {
				prpLpersonLoss = prpLpersonLossList.get(i);
				if ("D12".equals(prpLpersonLoss.getKindCode()) && String.valueOf(prpLpersonLoss.getPersonNo()).equals(arrPersonNo[j].trim())) {
					dblsumdefpay += prpLpersonLoss.getSumDefPay();
					dblCompelPay += prpLpersonLoss.getCompelPay();
					dblsumloss += prpLpersonLoss.getSumLoss();
					dblsumrealpay += prpLpersonLoss.getSumRealPay();
					dbExceptDeductiblePayD12 = dbExceptDeductiblePayD12 + prpLpersonLoss.getExceptDeductiblePay();
					PropertyUtils.copyProperties(prpLpersonLossbak, prpLpersonLoss);
				}
			}
			dblAllSumRealPayD12 = dblAllSumRealPayD12 + dblsumrealpay;
			// 超限判断 核定赔偿乘事故责任比例与限额比较
			if ((dblsumdefpay - dblCompelPay) * (prpLpersonLossbak.getIndemnityDutyRate() / 100) > dblunitamount) {
				if (StringUtils.getBytesLength(lineText) + StringUtils.getBytesLength(prpLpersonLoss.getLiabDetailName()) + 1 > 60) {
					strLctextList.add(lineText);
					lineText = lineText + space(17) + "每座責任限額";
				} else {
					lineText = lineText + "每座責任限額";
				}
			} else {
				if (StringUtils.getBytesLength(lineText) + 10 > 60) {
					strLctextList.add(lineText);
					lineText = lineText + space(17) + "(核定賠償金額-強制險賠償金額)";
				} else {
					lineText = lineText + "(核定賠償金額-強制險賠償金額)";
				}

				// 超限额赔偿限额不乘事故责任比例
				if (StringUtils.getBytesLength(lineText) + 10 > 60) {
					strLctextList.add(lineText);
					lineText = space(17) + "×事故責任比例 ";
				} else {
					lineText += "×事故責任比例";
				}
			}
			if (StringUtils.getBytesLength(lineText) + 11 > 60) {
				strLctextList.add(lineText);
				lineText = space(17) + "×(1－免賠率之和)";
			} else {
				lineText += "×(1－免賠率之和)";
			}
			strLctextList.add(lineText);
			lineText = space(16) + "=";
			// 超限判断 核定赔偿乘事故责任比例与限额比较
			if ((dblsumdefpay - dblCompelPay) * (prpLpersonLossbak.getIndemnityDutyRate() / 100) > dblunitamount) {
				if (StringUtils.getBytesLength(lineText) + 8 > 60) {
					strLctextList.add(lineText);
					lineText = space(17) + formatPay(dblunitamount) + "×";// modify
					// 超限额赔偿，限额不称事故责任比例
				} else {
					lineText += formatPay(dblunitamount - dblCompelPay) + "×" + formatPay(prpLpersonLossbak.getIndemnityDutyRate()) + "％" + "×";
				}
				dblsumdefpay = dblunitamount - dblCompelPay;
			} else {
				if (StringUtils.getBytesLength(lineText) + 8 > 60) {
					strLctextList.add(lineText);
					lineText = space(17) + "(" + formatPay(dblsumdefpay) + "-" + formatPay(dblCompelPay) + ")×" + formatPay(prpLpersonLossbak.getIndemnityDutyRate()) + "％" + "×";

				} else {
					lineText += "(" + formatPay(dblsumdefpay) + "-" + formatPay(dblCompelPay) + ")" + "×" + formatPay(prpLpersonLossbak.getIndemnityDutyRate()) + "％" + "×";
				}
				dblsumdefpay = dblsumdefpay - dblCompelPay;
			}
			dblsumdefpay = dblsumdefpay * prpLpersonLossbak.getIndemnityDutyRate() / 100;
			dblsumdefpay = dblsumdefpay * prpLpersonLossbak.getArrangeRate() / 100;
			if (StringUtils.getBytesLength(lineText) + 10 > 60) {
				strLctextList.add(lineText);
				lineText = space(17) + "(1－" + formatPay(prpLpersonLossbak.getDutyDeductibleRate() + prpLpersonLossbak.getDeductiblerate()) + "％)";

			} else {
				lineText += "(1－" + formatPay(prpLpersonLossbak.getDutyDeductibleRate() + prpLpersonLossbak.getDeductiblerate()) + "％)";

			}

			dblsumdefpay = dblsumdefpay * (1 - (prpLpersonLossbak.getDutyDeductibleRate() + prpLpersonLossbak.getDeductiblerate()) / 100);
			strLctextList.add(lineText);
			lineText = space(16) + "=" + formatPay(dblsumdefpay) + "元";
			strLctextList.add(lineText);
		}
		compensateData.dblCarPersonSumRealpay += dblAllSumRealPayD12;
		compensateData.dblAllExceptDeductiblePay += dbExceptDeductiblePayD12;
		compensateData.dblAllSumRealPay += dblAllSumRealPayD12 + dbExceptDeductiblePayD12;
		compensateData.lineM1 += "車上人員責任險（乘客）不計免賠額+";
		compensateData.lineM2 += new Double(dbExceptDeductiblePayD12).toString() + "+";
		List<PrpLctext> prpLctextList = compensateDto.getPrpLctextList();
		if (prpLctextList != null && !prpLctextList.isEmpty()) {
			int ctextcount = prpLctextList.size();
			PrpLctext prpLctextDto = null;
			logger.debug("车上人员责任-ctextcount:" + ctextcount);
			for (String lineTextTemp : strLctextList) {
				prpLctextDto = new PrpLctext();
				prpLctextDto.getId().setCompensateNo(strCompensateNo);
				prpLctextDto.getId().setTextType("1");
				prpLctextDto.getId().setLineNo(ctextcount++);
				prpLctextDto.setContext(lineTextTemp);
				prpLctextDto.setFlag("");
				compensateData.prpLctextlist.add(prpLctextDto);
			}
		}
	}

	// 车上人员(驾驶员)
	public void CarCreateForD11(HttpServletRequest httpServletRequest, CompensateDto compensateDto, CompensateData compensateData) throws SQLException, UserException, Exception {
		int personcount = 0;
		int j = 0;
		double dblsumloss = 0;
		double dblsumdefpay = 0;
		double dblsumrealpay = 0;
		double dblAllSumRealPayD11 = 0;
		double dblCompelPay = 0d;
		double dbExceptDeductiblePayD11 = 0.0;// 总的不计免赔额
		double dblamount = 0;
		String[] arrKindCode1 = new String[20];
		String[] arrPersonNo = new String[20];
		String[] arrPersonName = new String[20];
		String[] arrFamilyName = new String[20];
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		String strCompensateNo = prpLcompensate.getCompensateNo();
		logger.debug("產生的计算书号:" + strCompensateNo);
		PrpLpersonLoss prpLpersonLoss = new PrpLpersonLoss();
		PrpLpersonLoss prpLpersonLossbak = new PrpLpersonLoss();
		// 取限额
		PolicyDto policyDto = this.getPolicyService().findByPrimaryKey(prpLcompensate.getPolicyNo());
		List<PrpCitemKind> limitlist = policyDto.getPrpCitemKindList();
		Iterator<PrpCitemKind> it = limitlist.iterator();
		if (limitlist != null && limitlist.size() > 0) {
			while (it.hasNext()) {
				PrpCitemKind prpCitemKind = it.next();
				if ("D11".equals(prpCitemKind.getKindCode())) {
					dblamount = prpCitemKind.getAmount();
					break;
				}
			}
		}
		List<PrpLpersonLoss> prpLpersonLossList = compensateDto.getPrpLpersonLossList();
		if (prpLpersonLossList != null && !prpLpersonLossList.isEmpty()) {
			for (int i = 0; i < prpLpersonLossList.size(); i++) {
				prpLpersonLoss = prpLpersonLossList.get(i);
				for (j = 1; j <= personcount; j++) {
					if (arrKindCode1[j].indexOf(prpLpersonLoss.getKindCode()) < 0 && String.valueOf(prpLpersonLoss.getPersonNo()).equals(arrPersonNo[j].trim()))
						arrKindCode1[j] = arrKindCode1[j].trim() + prpLpersonLoss.getKindCode();
					if (String.valueOf(prpLpersonLoss.getPersonNo()).equals(arrPersonNo[j].trim()))
						break;
				}
				if (j > personcount) {
					personcount++;
					arrKindCode1[personcount] = prpLpersonLoss.getKindCode();
					arrPersonNo[personcount] = String.valueOf(prpLpersonLoss.getPersonNo());
					arrPersonName[personcount] = prpLpersonLoss.getPersonName();
					arrFamilyName[personcount] = prpLpersonLoss.getFamilyName();
				}
			}
		}
		List<String> strLctextList = new ArrayList<String>();
		String lineText = "車上人員責任險(駕駛員)：";

		for (j = 1; j <= personcount; j++) {
			if (arrKindCode1[j].indexOf("D11") < 0)
				continue;
			strLctextList.add(lineText);
			lineText = space(4) + "車輛號牌號碼為" + arrFamilyName[j].trim() + "的出險人員" + "" + arrPersonName[j].trim() + "：";
			strLctextList.add(lineText);
			lineText = space(4) + "本項賠款金額=";

			if (prpLpersonLossList != null && !prpLpersonLossList.isEmpty()) {
				for (int i = 0; i < prpLpersonLossList.size(); i++) {
					prpLpersonLoss = prpLpersonLossList.get(i);
					if (prpLpersonLoss.getKindCode().equals("D11") && String.valueOf(prpLpersonLoss.getPersonNo()).equals(arrPersonNo[j].trim())) {
						dblsumdefpay += prpLpersonLoss.getSumDefPay();
						dblCompelPay += prpLpersonLoss.getCompelPay();
						dblsumloss += prpLpersonLoss.getSumLoss();
						dblsumrealpay += prpLpersonLoss.getSumRealPay();
						dbExceptDeductiblePayD11 = dbExceptDeductiblePayD11 + prpLpersonLoss.getExceptDeductiblePay();
						PropertyUtils.copyProperties(prpLpersonLossbak, prpLpersonLoss);
					}
				}
			}
			dblAllSumRealPayD11 = dblAllSumRealPayD11 + dblsumrealpay;
			// 超限处理 核定赔偿乘责任比例与限额比较
			if ((dblsumdefpay - dblCompelPay) * (prpLpersonLossbak.getIndemnityDutyRate() / 100) > dblamount) {
				if (StringUtils.getBytesLength(lineText) + StringUtils.getBytesLength(prpLpersonLoss.getLiabDetailName()) + 1 > 60) {
					strLctextList.add(lineText);
					lineText = lineText + space(17) + "責任限額";
				} else {
					lineText = lineText + "責任限額";
				}
			} else {
				if (StringUtils.getBytesLength(lineText) + 10 > 60) {
					strLctextList.add(lineText);
					lineText = lineText + space(17) + "(核定賠償金額-強制險賠償金額)";
				} else {
					lineText = lineText + "(核定賠償金額-強制險賠償金額)";
				}
				// 超限额赔偿不乘事故责任比例
				if (StringUtils.getBytesLength(lineText) + 10 > 60) {
					strLctextList.add(lineText);
					lineText = space(17) + "×事故責任比例 ";
				} else {
					lineText += "×事故責任比例";
				}
			}
			if (StringUtils.getBytesLength(lineText) + 11 > 60) {
				strLctextList.add(lineText);
				lineText = space(17) + "×(1－免賠率之和)";
			} else {
				lineText += "×(1－免賠率之和)";
			}
			strLctextList.add(lineText);
			lineText = space(16) + "=";
			// 超限处理 核定赔偿乘责任比例与限额比较
			if ((dblsumdefpay - dblCompelPay) * (prpLpersonLossbak.getIndemnityDutyRate() / 100) > dblamount) {
				if (StringUtils.getBytesLength(lineText) + 8 > 60) {
					strLctextList.add(lineText);
					lineText = space(17) + formatPay(dblamount) + "×";
					// 超限额赔偿不乘事故责任比例
				} else {
					lineText += formatPay(dblamount) + "×";
					// 超限额赔偿不乘事故责任比例
				}
				dblsumdefpay = dblamount - dblCompelPay;
			} else {
				if (StringUtils.getBytesLength(lineText) + 8 > 60) {
					strLctextList.add(lineText);
					lineText = space(17) + "(" + formatPay(dblsumloss) + "-" + formatPay(dblCompelPay) + ")×" + formatPay(prpLpersonLoss.getIndemnityDutyRate()) + "％" + "×";

				} else {
					lineText += "(" + formatPay(dblsumloss) + "-" + formatPay(dblCompelPay) + ")" + "×" + formatPay(prpLpersonLoss.getIndemnityDutyRate()) + "％" + "×";
				}
				dblsumdefpay = dblsumdefpay - dblCompelPay;
			}
			dblsumdefpay = dblsumdefpay * prpLpersonLossbak.getIndemnityDutyRate() / 100;
			dblsumdefpay = dblsumdefpay * prpLpersonLossbak.getArrangeRate() / 100;
			if (StringUtils.getBytesLength(lineText) + 10 > 60) {
				strLctextList.add(lineText);
				lineText = space(17) + "(1－" + formatPay(prpLpersonLossbak.getDutyDeductibleRate() + prpLpersonLossbak.getDeductiblerate()) + "％)";

			} else {
				lineText += "(1－" + formatPay(prpLpersonLossbak.getDutyDeductibleRate() + prpLpersonLossbak.getDeductiblerate()) + "％)";

			}
			dblsumdefpay = dblsumdefpay * (1 - (prpLpersonLossbak.getDutyDeductibleRate() + prpLpersonLossbak.getDeductiblerate()) / 100);
			strLctextList.add(lineText);
			lineText = space(16) + "=" + formatPay(dblsumdefpay) + "元";
			strLctextList.add(lineText);
		}
		compensateData.dblCarPersonSumRealpay += dblAllSumRealPayD11;
		compensateData.dblAllExceptDeductiblePay += dbExceptDeductiblePayD11;
		compensateData.dblAllSumRealPay += dblAllSumRealPayD11 + dbExceptDeductiblePayD11;
		compensateData.lineM1 += "車上人員責任險（駕駛員）不計免賠額+";
		compensateData.lineM2 += new Double(dbExceptDeductiblePayD11).toString() + "+";
		List<PrpLctext> prpLctextList = compensateDto.getPrpLctextList();
		int ctextcount = 0;
		if (prpLctextList != null && !prpLctextList.isEmpty()) {
			ctextcount = compensateDto.getPrpLctextList().size();
		}
		PrpLctext prpLctext = null;
		logger.debug("车上人员责任-ctextcount:" + ctextcount);
		for (String lineTextTemp : strLctextList) {
			prpLctext = new PrpLctext();
			prpLctext.getId().setCompensateNo(strCompensateNo);
			prpLctext.getId().setTextType("1");
			prpLctext.getId().setLineNo(ctextcount++);
			prpLctext.setContext(lineTextTemp);
			prpLctext.setFlag("");
			compensateData.prpLctextlist.add(prpLctext);
		}
	}

	// 盗抢险
	public void CarCreateForG(HttpServletRequest httpServletRequest, CompensateDto compensateDto, CompensateData compensateData) throws SQLException, UserException, Exception {
		double factValue = 0;// 出险时机动车的实际价值
		double amount = 0;
		double dutyDeductibleRate = 0.0;
		double deductibleRate = 0.0;
		double dblsumrest = 0;// 残值
		double dblsumDefPay = 0;// 核定赔偿金额计算用值
		double dbExceptDeductiblePayG = 0.0;// 不计免赔赔款合计
		double dblsumrealPay = 0d;
		String strCompensateNo = compensateDto.getPrpLcompensate().getCompensateNo();
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		PolicyDto policyDto = this.getPolicyService().findByPrimaryKey(prpLcompensate.getPolicyNo());
		List<PrpCitemKind> limitlist = policyDto.getPrpCitemKindList();
		if (limitlist != null && limitlist.size() > 0) {
			Iterator<PrpCitemKind> it = limitlist.iterator();
			PrpCitemKind prpCitemKind = null;
			while (it.hasNext()) {
				prpCitemKind = it.next();
				if ("G".equals(prpCitemKind.getKindCode())) {
					amount = prpCitemKind.getAmount();
					break;
				}
			}
		}
		List<String> strLctextList = new ArrayList<String>();
		String lineText = "";
		strLctextList.add("盜搶險：");
		List<PrpLloss> prpLlossList = compensateDto.getPrpLlossList();
		if (prpLlossList != null && !prpLlossList.isEmpty()) {
			for (PrpLloss prpLloss : compensateDto.getPrpLlossList()) {
				if ("G".equals(prpLloss.getKindCode())) {
					factValue = prpLloss.getCarRealValue();
					deductibleRate = prpLloss.getDeductiblerate();
					dblsumDefPay = prpLloss.getSumDefPay();
					dblsumrealPay = prpLloss.getSumRealPay();
					dblsumrest = prpLloss.getSumRest();
					dbExceptDeductiblePayG = dbExceptDeductiblePayG + prpLloss.getExceptDeductiblePay();
				}
			}
		}
		// 非全车损
		if (compensateData.strEscapeFlag.length() > 1 && compensateData.strEscapeFlag.charAt(1) != 'Y') {
			if (factValue >= dblsumDefPay) {
				lineText = space(4) + "本項實賠金額 = (核定賠償金額-殘值) × (1-免賠率之和) ";
				strLctextList.add(lineText);
				lineText = space(17) + "= (" + dblsumDefPay + "-" + dblsumrest + ") × (1-" + (dutyDeductibleRate + deductibleRate) + "%)";
				strLctextList.add(lineText);
				lineText = space(17) + "= " + String.valueOf(dblsumrealPay).toString();
				strLctextList.add(lineText);
			} else {
				lineText = space(4) + "本項實賠金額 = (標的車實際價值-殘值)×（1-免賠率之和） ";
				strLctextList.add(lineText);
				lineText = space(17) + "= (" + factValue + "-" + dblsumrest + ") × (1-" + (dutyDeductibleRate + deductibleRate) + "%)";
				strLctextList.add(lineText);
				lineText = space(17) + "= " + String.valueOf(dblsumrealPay).toString();
				strLctextList.add(lineText);
			}
		} else {
			if (factValue >= amount) {
				lineText = space(4) + "本項實賠金額 = (保險金額-殘值) × (1-免賠率之和) ";
				strLctextList.add(lineText);
				lineText = space(17) + "= (" + amount + "-" + dblsumrest + ") × (1-" + (dutyDeductibleRate + deductibleRate) + "%)";
				strLctextList.add(lineText);
				lineText = space(17) + "= " + String.valueOf(dblsumrealPay).toString();
				strLctextList.add(lineText);
			} else {
				lineText = space(4) + "本項實賠金額 = (標的車實際價值-殘值)×（1-免賠率之和） ";

				strLctextList.add(lineText);
				lineText = space(17) + "= (" + factValue + "-" + dblsumrest + ") × (1-" + (dutyDeductibleRate + deductibleRate) + "%)";
				strLctextList.add(lineText);
				lineText = space(17) + "= " + String.valueOf(dblsumrealPay).toString();
				strLctextList.add(lineText);
			}
		}
		compensateData.dblCarStealSumRealPay += dblsumrealPay;
		compensateData.dblAllExceptDeductiblePay += dbExceptDeductiblePayG;
		compensateData.dblAllSumRealPay += dblsumrealPay + dbExceptDeductiblePayG;
		if (new Double(dbExceptDeductiblePayG).intValue() != 0) {
			compensateData.lineM1 += "全車盜搶險不計免賠額+";
			compensateData.lineM2 += new Double(dbExceptDeductiblePayG).toString() + "+";
		}
		List<PrpLctext> prpLctextList = compensateDto.getPrpLctextList();
		int ctextcount = 0;
		if (prpLctextList != null && !prpLctextList.isEmpty()) {
			ctextcount = prpLctextList.size();
		}
		PrpLctext prpLctext = null;
		logger.debug("盗抢险-ctextcount:" + ctextcount);
		for (String lineTextTemp : strLctextList) {
			prpLctext = new PrpLctext();
			prpLctext.getId().setCompensateNo(strCompensateNo);
			prpLctext.getId().setTextType("1");
			prpLctext.getId().setLineNo(ctextcount++);
			prpLctext.setContext(lineTextTemp);
			prpLctext.setFlag("");
			compensateData.prpLctextlist.add(prpLctext);
		}
	}

	/**
	 * 简易赔案商业生成机动车险理算报告
	 * @param 无
	 * @throws UserException
	 * @throws Exception
	 */
	public void quickCaseCompensateGenerate(HttpServletRequest httpServletRequest, CompensateDto compensateDto) throws UserException, Exception {
		CompensateData compensateData = new CompensateData();

		compensateDto.setPrpLctextList(new ArrayList<PrpLctext>());
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		String strPolicyNo = prpLcompensate.getPolicyNo();
		String strCompensateNo = prpLcompensate.getCompensateNo();
		// 获取出险时间
		String registNo = prpLcompensate.getRegistNo();
		RegistDto registDto = this.getRegistService().findByPrimaryKey(registNo);
		// 取得保单的信息
		PrpLregist prpLregist = registDto.getPrpLregist();
		String damageDate = new DateTime(prpLregist.getDamageStartDate()).toString();
		String damageHour = prpLregist.getDamageStartHour();
		List<PrpCitemCar> prpCitemCarList = this.endorseViewHelper.findPrpCitemCar(strPolicyNo, damageDate, damageHour);
		PrpCitemCar prpCitemCarDto = new PrpCitemCar();
		if (prpCitemCarList != null && !prpCitemCarList.isEmpty()) {
			prpCitemCarDto = prpCitemCarList.get(0);
		}
		// 获取uticodeTransfer对应险别的configCode值
		// 获取标的车车牌号码
		String licenseNo = prpLcompensate.getLicenseNo();

		List<String> vecKindCode = new ArrayList<String>();
		/**
		 * 先初始化A险及B险,再检测该案有无A及B险,没有再删除
		 */
		vecKindCode.add("A");
		vecKindCode.add("B");
		boolean isHaveRiskA = false;
		boolean isHaveRiskB = false;
		boolean isHaveOther = false;
		List<PrpLloss> prpLlossList = compensateDto.getPrpLlossList();
		if (prpLlossList != null && !prpLlossList.isEmpty()) {
			for (PrpLloss prpLlossDto : prpLlossList) {
				if (DataUtils.emptyToNull(prpLlossDto.getFamilyName()) == null) {
					// 车牌号码
					// 增加车牌号码
				}
				if ("A".equals(prpLlossDto.getKindCode())) {
					isHaveRiskA = true;
					if (prpCitemCarDto.getPurchasePrice() <= 0)
						prpCitemCarDto.setPurchasePrice(prpLlossDto.getAmount());// ???
				} else if ("B".equals(prpLlossDto.getKindCode())) {
					isHaveRiskB = true;
				} else if ("AB".equals(prpLlossDto.getKindCode())) {
					// 提车保险特殊处理
					if (licenseNo.equals(prpLlossDto.getLicenseNo().trim())) {
						isHaveRiskA = true;
					} else {
						isHaveRiskB = true;
					}
				}
				if (DataUtils.emptyToNull(prpLlossDto.getKindCode()) != null && !"AB".equals(prpLlossDto.getKindCode())) {
					if (!(vecKindCode.contains(prpLlossDto.getKindCode()))) {
						vecKindCode.add(prpLlossDto.getKindCode());
					}
				}
			}
		}

		if (!isHaveRiskA) {
			vecKindCode.remove("A");
		}
		if (!isHaveRiskB) {
			vecKindCode.remove("B");
		}
		logger.debug("开始產生各险别的公式及内容");
		for (String kindCode : vecKindCode) {
			logger.debug("kindCode==" + kindCode);
			if ("A".equals(kindCode)) {
				CarCreateForA(httpServletRequest, compensateDto, compensateData, "A");
			} else if ("B".equals(kindCode)) {
				quickCaseCarCreateForB(httpServletRequest, compensateDto, compensateData);
			} else if (!ConstantCodes.KINDCODE_D_BZ.equals(kindCode) && !"".equals(kindCode)) {
				CarCreateForOther(kindCode, httpServletRequest, compensateDto, compensateData);
			}
		}
		List<String> strLctextList = new ArrayList<String>();
		if (!("".equals(compensateData.lineM1)) && !("".equals(compensateData.lineM2))) {
			strLctextList.add("");
			strLctextList.add("不計免賠特約條款：");
			strLctextList.add(space(9) + "實賠金額 =" + (compensateData.lineM1).substring(0, (compensateData.lineM1).length() - 1));
			strLctextList.add(space(18) + "=" + (compensateData.lineM2).substring(0, (compensateData.lineM2).length() - 1));
			strLctextList.add(space(18) + "=" + (compensateData.dblAllExceptDeductiblePay));
		}
		logger.debug("开始產生本案实赔金额公式及内容");
		strLctextList.add("");
		String lineText = "";
		if (vecKindCode.size() > 0) {
			lineText = "本案實賠金額=";
			for (String kindCode : vecKindCode) {
				if ("A".equals(kindCode)) {
					lineText += "車損險賠款＋";
				} else if ("B".equals(kindCode)) {
					lineText += "第三者責任險賠款＋";
				} else if (!ConstantCodes.KINDCODE_D_BZ.equals(kindCode) && !"".equals(kindCode)) {
					if (isHaveOther != true) {
						isHaveOther = true;
						lineText += "其它附加險賠款＋";
					}
				}
			}
			if (compensateData.dblAllExceptDeductiblePay > 0) {
				lineText += "不計免賠險＋";
			}
			isHaveOther = false;
			if (StringUtils.getBytesLength(lineText) > 14) {
				lineText = lineText.substring(0, (lineText.length() - 1));
			}
			strLctextList.add(lineText);
			lineText = "";
			for (String kindCode : vecKindCode) {
				if ("A".equals(kindCode)) {
					lineText = space(12) + "=" + formatPay(compensateData.dblCarSumRealPay + compensateData.dblChargeSumRealPay) + "＋";
				} else if ("B".equals(kindCode)) {
					if (lineText == null || lineText.length() == 0) {
						lineText = space(12) + "=" + formatPay(compensateData.dblThirdSumRealPay) + "＋";
					} else {
						lineText += formatPay(compensateData.dblThirdSumRealPay) + "＋";
					}
				} else if (!ConstantCodes.KINDCODE_D_BZ.equals(kindCode) && !"".equals(kindCode)) {
					if (isHaveOther != true) {
						if (DataUtils.emptyToNull(lineText) == null) {
							isHaveOther = true;
							lineText = space(12) + "=" + formatPay(compensateData.dblOthSumRealPay + compensateData.dblNoFranchise) + "＋";
						} else {
							isHaveOther = true;
							lineText += formatPay(compensateData.dblOthSumRealPay + compensateData.dblNoFranchise) + "＋";
						}
					}
				}
			}
			if (new Double(compensateData.dblAllExceptDeductiblePay).intValue() != 0) {
				if (DataUtils.emptyToNull(lineText) == null) {
					lineText = space(12) + "=" + formatPay(compensateData.dblAllExceptDeductiblePay) + "＋";
				} else {
					lineText += formatPay(compensateData.dblAllExceptDeductiblePay) + "＋";
				}
			}

			if (StringUtils.getBytesLength(lineText) > 14) {
				lineText = lineText.substring(0, (lineText.length() - 1));
			}
			strLctextList.add(lineText);
			lineText = "            " + "=" + formatPay(compensateData.dblAllSumRealPay - prpLcompensate.getSumPrePaid()) + "元";
		}
		strLctextList.add(lineText);
		List<PrpLctext> prpLctextList = compensateDto.getPrpLctextList();
		int ctextcount = 0;
		if (prpLctextList != null && !prpLctextList.isEmpty()) {
			ctextcount = prpLctextList.size();
		}
		PrpLctext prpLctext = null;
		for (String lineTextTemp : strLctextList) {
			prpLctext = new PrpLctext();
			prpLctext.getId().setCompensateNo(strCompensateNo);
			prpLctext.getId().setTextType("1");
			prpLctext.getId().setLineNo(ctextcount++);
			prpLctext.setContext(lineTextTemp);
			prpLctext.setFlag("");
			compensateData.prpLctextlist.add(prpLctext);
		}
		prpLctext = new PrpLctext();
		prpLctext.setPrpLctextList(compensateData.prpLctextlist);
		httpServletRequest.setAttribute("prpLctext", prpLctext);
	}

	/**
	 * 生成三者险的理算公式及报告
	 * @param httpServletRequest
	 * @param compensateDto
	 * @throws UserException
	 * @throws Exception
	 */
	public void quickCaseCarCreateForB(HttpServletRequest httpServletRequest, CompensateDto compensateDto, CompensateData compensateData) throws UserException, Exception {

		PrpLloss prpLlossbak = new PrpLloss();
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		int licensenocount = 0;
		int textlosscount = 0;
		int j = 0;
		int intpropflag = 0;
		int intCarForBflag = 0;
		double dblsumloss = 0;
		double dblsumrest = 0;
		double dblthirdsumrealpay = 0;
		double dblthirdsumrealpayAll = 0;
		double dblthirdsumdefpay = 0;
		double dblthirdsumdefpayAll = 0;
		double dblthirdCompelPay = 0d;
		double dbExceptDeductiblePayB = 0.0;// 总的不计免赔额
		String[] arrKindCode = new String[20];
		String[] arrLicenseNo = new String[20];
		String[] lossName = new String[20];
		String strCompensateNo = prpLcompensate.getCompensateNo();
		// 取B限额---------------------------
		PolicyDto policyDto = this.getPolicyService().findByPrimaryKey(prpLcompensate.getPolicyNo());
		// 获取uticodeTransfer对应险别的configCode值
		String configCode = this.getCodeService().translateRiskCodetoConfigCode(prpLcompensate.getRiskCode());
		// 读取标的车车牌号码
		String licenseNo = prpLcompensate.getLicenseNo();
		// 取限额
		List<PrpCitemKind> limitlist = policyDto.getPrpCitemKindList();
		if (limitlist != null && limitlist.size() > 0) {
			Iterator<PrpCitemKind> it = limitlist.iterator();
			PrpCitemKind prpCitemKind = null;
			while (it.hasNext()) {
				prpCitemKind = it.next();
				if ("B".equals(prpCitemKind.getKindCode())) {
					break;
				} else if ("AB".equals(prpCitemKind.getKindCode())) {// 提车保险车损部分保险金额获取2008-04-15
					break;
				}
			}
		}
		// 循环遍历赔付标的表
		List<PrpLloss> prpLlossList = compensateDto.getPrpLlossList();
		for (PrpLloss prpLloss : prpLlossList) {
			for (j = 1; j <= licensenocount; j++) {
				if (arrKindCode[j].indexOf(prpLloss.getKindCode()) < 0 && prpLloss.getLicenseNo().equals(arrLicenseNo[j].trim()))
					arrKindCode[j] = arrKindCode[j].trim() + prpLloss.getKindCode();
				if (prpLloss.getLicenseNo().equals(arrLicenseNo[j].trim()))
					break;
			}
			if (j > licensenocount && prpLloss.getLicenseNo() != null && prpLloss.getLicenseNo().length() > 0) {
				if ("AB".equals(prpLloss.getKindCode()) && !prpLloss.getLicenseNo().equals(prpLcompensate.getLicenseNo())) {
					licensenocount++;
					lossName[licensenocount] = prpLloss.getLossName();
					arrLicenseNo[licensenocount] = prpLloss.getLicenseNo();
					arrKindCode[licensenocount] = prpLloss.getKindCode();
				} else if (!"AB".equals(prpLloss.getKindCode())) {
					licensenocount++;
					lossName[licensenocount] = prpLloss.getLossName();
					arrLicenseNo[licensenocount] = prpLloss.getLicenseNo();
					arrKindCode[licensenocount] = prpLloss.getKindCode();
				}
			}
			if (DataUtils.emptyToNull(prpLloss.getLicenseNo()) == null && prpLloss.getLossName() != null && ("B".equals(prpLloss.getKindCode()) || "AB".equals(prpLloss.getKindCode()))) {
				// 提车保险车损部分保险金额获取
				intpropflag = 1;
			}
			if (prpLloss.getLicenseNo() != null && prpLloss.getKindCode().equals("B")
					&& !("".equals(prpLloss.getLicenseNo()) || (prpLloss.getLicenseNo() != null && !prpLloss.getLicenseNo().equals(prpLcompensate.getLicenseNo()) && "AB".equals(prpLloss.getKindCode())))) {
				// 提车保险车损部分保险金额获取
				intCarForBflag = 1;
			}
		}
		List<String> strLctextList = new ArrayList<String>();
		String lineText = "";
		if ("RISKCODE_DTC".equals(configCode) || "RISKCODE_DAS".equals(configCode)) {// 提車保險特殊處理
			lineText = "提車保險第三者責任險：";
		} else {
			lineText = "第三者責任險：";
		}
		strLctextList.add(lineText);
		if (intCarForBflag == 1) {
			lineText = space(4) + "三者車損賠付:";
			strLctextList.add(lineText);
		}
		for (int i = 1; i <= licensenocount; i++) {
			dblthirdsumdefpay = 0d;
			dblsumloss = 0d;
			dblsumrest = 0d;
			dblthirdCompelPay = 0d;
			dblthirdsumrealpay = 0d;
			if (arrKindCode[i].indexOf("B") < 0)
				continue;
			lineText = "";
			if (!lossName[i].trim().equals("車輛")) {
				lineText = space(8) + lossName[i].trim();
				strLctextList.add(lineText);
			} else {
				lineText = space(8) + arrLicenseNo[i].trim() + "號車";
				strLctextList.add(lineText);
			}
			lineText = space(8) + "本項實賠金額=(核定賠償金額-強制險賠款";
			textlosscount++;
			lineText = lineText.substring(0, (lineText.length())) + "-殘值)";
			if (StringUtils.getBytesLength(lineText) + 10 > 60) {
				strLctextList.add(lineText);
				lineText = space(21) + "×責任比例";
			} else {
				lineText += "×責任比例";
			}
			if (StringUtils.getBytesLength(lineText) + 11 > 60) {
				strLctextList.add(lineText);
				lineText = space(21) + "×(1－免賠率之和)";
			} else {
				lineText += "×(1－免賠率之和)";
			}
			strLctextList.add(lineText);
			lineText = space(20) + "=(";
			if (prpLlossList != null && !prpLlossList.isEmpty()) {
				for (PrpLloss prpLloss : prpLlossList) {
					if ("B".equals(prpLloss.getKindCode()) && prpLloss.getLicenseNo().equals(arrLicenseNo[i].trim())
							|| ("AB".equals(prpLloss.getKindCode()) && prpLloss.getLicenseNo().equals(arrLicenseNo[i].trim()) && !prpLloss.getLicenseNo().equals(licenseNo))) {
						// 提车保险特殊处理
						dbExceptDeductiblePayB += prpLloss.getExceptDeductiblePay();
						dblthirdsumdefpay += prpLloss.getSumDefPay();
						dblsumloss += prpLloss.getSumLoss();
						dblsumrest += prpLloss.getSumRest();
						dblthirdCompelPay += prpLloss.getCompelPay();
						dblthirdsumrealpay += prpLloss.getSumRealPay();
						prpLlossbak = new PrpLloss();
						PropertyUtils.copyProperties(prpLlossbak, prpLloss);
					}
				}
			}
			dblthirdsumrealpayAll = dblthirdsumrealpayAll + dblthirdsumrealpay;
			if (StringUtils.getBytesLength(lineText) + 8 > 60) {
				strLctextList.add(lineText);
				lineText = space(21) + formatPay(dblthirdsumdefpay);
			} else {
				lineText += formatPay(dblthirdsumdefpay);
			}
			if (StringUtils.getBytesLength(lineText) + 8 > 60) {
				strLctextList.add(lineText);
				lineText = space(21) + "-" + formatPay(dblthirdCompelPay);
			} else {
				lineText += "-" + formatPay(dblthirdCompelPay);
			}
			if (StringUtils.getBytesLength(lineText) + 8 > 60) {
				strLctextList.add(lineText);
				lineText = space(21) + "-" + formatPay(dblsumrest) + ")";
			} else {
				lineText += "-" + formatPay(dblsumrest) + ")";
			}
			dblthirdsumdefpay = (dblthirdsumdefpay - dblthirdCompelPay - dblsumrest);
			dblthirdsumdefpayAll = dblthirdsumdefpayAll + dblthirdsumdefpay;
			if (StringUtils.getBytesLength(lineText) + 8 > 60) {
				strLctextList.add(lineText);
				lineText = space(21) + "×" + formatPay(prpLlossbak.getIndemnityDutyRate()) + "％";
			} else {
				lineText += "×" + formatPay(prpLlossbak.getIndemnityDutyRate()) + "％";
			}
			dblthirdsumdefpay = dblthirdsumdefpay * prpLlossbak.getIndemnityDutyRate() / 100;
			if (StringUtils.getBytesLength(lineText) + 10 > 60) {
				strLctextList.add(lineText);
				lineText = space(21) + "×(1－" + formatPay(prpLlossbak.getDutyDeductibleRate() + prpLlossbak.getDeductiblerate()) + "％)";// +
				// "×(1－"+new
			} else {
				lineText += "×(1－" + formatPay(prpLlossbak.getDutyDeductibleRate() + prpLlossbak.getDeductiblerate()) + "％)";
			}
			dblthirdsumdefpay = dblthirdsumdefpay * (1 - (prpLlossbak.getDutyDeductibleRate() + prpLlossbak.getDeductiblerate()) / 100);
			strLctextList.add(lineText);
			lineText = space(20) + "=" + formatPay(dblthirdsumdefpay) + "元";
			strLctextList.add(lineText);
		}
		if (intpropflag == 1) {
			textlosscount++;
			lineText = space(4) + "三者財產賠付:";
			if (prpLlossList != null && !prpLlossList.isEmpty()) {
				for (PrpLloss prpLlossDto : compensateDto.getPrpLlossList()) {
					if ((prpLlossDto.getKindCode().equals("B") || prpLlossDto.getKindCode().equals("AB"))// 提车保险车损部分保险金额获取2008-04-15
							&& (prpLlossDto.getLicenseNo() == null || prpLlossDto.getLicenseNo().length() == 0) && prpLlossDto.getLossName() != null) {
						dbExceptDeductiblePayB = dbExceptDeductiblePayB + prpLlossDto.getExceptDeductiblePay();
						dblthirdsumdefpay = prpLlossDto.getSumDefPay();
						dblsumloss = prpLlossDto.getSumLoss();
						dblsumrest = prpLlossDto.getSumRest();
						dblthirdCompelPay = prpLlossDto.getCompelPay();
						dblthirdsumrealpay = prpLlossDto.getSumRealPay();
						dblthirdsumdefpayAll = dblthirdsumdefpayAll + dblthirdsumdefpay - dblsumrest - dblthirdCompelPay;
						dblthirdsumrealpayAll = dblthirdsumrealpayAll + dblthirdsumrealpay;
						strLctextList.add(lineText);
						lineText = space(8) + "項目:" + prpLlossDto.getLossName();
						strLctextList.add(lineText);
						lineText = space(8) + "本項賠款金額=(核定賠償金額-強制險賠款-殘值)×事故責任比例×(1－免賠率之和)";
						strLctextList.add(lineText);
						lineText = space(20) + "=(" + formatPay(dblthirdsumdefpay) + "-" + formatPay(dblthirdCompelPay) + "-" + formatPay(dblsumrest) + ")×" + formatPay(prpLlossDto.getIndemnityDutyRate()) + "％" + "×" + "(1-"
								+ formatPay(prpLlossDto.getDutyDeductibleRate() + prpLlossDto.getDeductiblerate()) + "％)";
						strLctextList.add(lineText);
						dblthirdsumdefpay = (dblthirdsumdefpay - dblthirdCompelPay - dblsumrest) * prpLlossDto.getIndemnityDutyRate() / 100 * (1 - (prpLlossDto.getDutyDeductibleRate() + prpLlossDto.getDeductiblerate()) / 100);
						lineText = space(20) + "=" + formatPay(dblthirdsumdefpay) + "元";
						PropertyUtils.copyProperties(prpLlossbak, prpLlossDto);
						strLctextList.add(lineText);
					}
				}
			}
		}
		compensateData.dblAllExceptDeductiblePay += dbExceptDeductiblePayB;
		compensateData.dblThirdSumRealPay += dblthirdsumrealpayAll;
		compensateData.dblAllSumRealPay += dblthirdsumrealpayAll + dbExceptDeductiblePayB;
		if (new Double(dbExceptDeductiblePayB).intValue() != 0) {
			compensateData.lineM1 += "第三者責任險不計免賠額+";
			compensateData.lineM2 += new Double(dbExceptDeductiblePayB).toString() + "+";
		}
		List<PrpLctext> prpLctextList = compensateDto.getPrpLctextList();
		int ctextcount = 0;
		if (prpLctextList != null && !prpLctextList.isEmpty()) {
			ctextcount = prpLctextList.size();
		}
		PrpLctext prpLctext = null;
		logger.debug("三者险-ctextcount:" + ctextcount);
		for (String lineTextTemp : strLctextList) {
			prpLctext = new PrpLctext();
			prpLctext.getId().setCompensateNo(strCompensateNo);
			prpLctext.getId().setTextType("1");
			prpLctext.getId().setLineNo(ctextcount++);
			prpLctext.setContext(lineTextTemp);
			prpLctext.setFlag("");
			compensateData.prpLctextlist.add(prpLctext);
		}
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public PrpLpersonService getPrpLpersonService() {
		return prpLpersonService;
	}

	public void setPrpLpersonService(PrpLpersonService prpLpersonService) {
		this.prpLpersonService = prpLpersonService;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
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
}
