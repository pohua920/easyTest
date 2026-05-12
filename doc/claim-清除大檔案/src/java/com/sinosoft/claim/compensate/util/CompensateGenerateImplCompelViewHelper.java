package com.sinosoft.claim.compensate.util;

import ins.framework.utils.DataUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLctext;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

public class CompensateGenerateImplCompelViewHelper extends CompensateGenerateViewHelper {

	/** 立案信息服务 */
	private PrpLclaimService prpLclaimService;

	public CompensateGenerateImplCompelViewHelper() {

	}

	public void compensateGenerate(HttpServletRequest httpServletRequest, CompensateDto compensateDto) throws UserException, Exception {
		CompensateData compensateData = new CompensateData();
		compensateDto.setPrpLctextList(new ArrayList<PrpLctext>());

		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		String claimNo = prpLcompensate.getClaimNo();
		compensateDto.setPrpLclaim(this.prpLclaimService.findPrpLclaim(claimNo));
		ReportForCompelB(httpServletRequest, compensateDto, compensateData);

		PrpLctext prpLctext = new PrpLctext();
		prpLctext.setPrpLctextList(compensateData.prpLctextlist);
		httpServletRequest.setAttribute("prpLctext", prpLctext);

	}

	public void compelCompensateGenerate(HttpServletRequest httpServletRequest, CompensateDto compensateDto) throws UserException, Exception {
		CompensateData compensateData = new CompensateData();
		compensateDto.setPrpLctextList(new ArrayList<PrpLctext>());
		quickcaseReportForCompelB(httpServletRequest, compensateDto, compensateData);
		PrpLctext prpLctext = new PrpLctext();
		prpLctext.setPrpLctextList(compensateData.prpLctextlist);
		httpServletRequest.setAttribute("compelPrpLctext", prpLctext);

	}

	public void quickcaseReportForCompelB(HttpServletRequest httpServletRequest, CompensateDto compensateDto, CompensateData compensateData) throws UserException, Exception {
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		// 限额
		double propLimit = 0; // 财产限额
		double dbChargeRealPay = 0; // 记入赔款费用 wealthTitle
		double nulldbpropall = 0; // 无责代赔金额
		String duty = httpServletRequest.getParameter("indemnityDuty");

		String[] StrPropLimit = httpServletRequest.getParameterValues("limitFee");
		if ("4".equals(duty)) {
			propLimit = Double.parseDouble(StrPropLimit[4]);
		} else {
			propLimit = Double.parseDouble(StrPropLimit[2]);
		}
		int licensenoNumber = 0;
		int textlosscount = 0;
		int j = 0;
		int intpropflag = 0;
		String[] arrKindCode = new String[20];
		String[] arrLicenseNo = new String[20];
		String[] arrLossName = new String[20];
		String strCompensateNo = prpLcompensate.getCompensateNo();
		String lineText = "";
		int lineNumber = 1;
		int propindex = 0;
		int proptitileindex = 0;
		double dbtempSumloss = 0;
//		double dbtempSumRest = 0;
		double dbpropall = 0;
		HashMap<String, String> mybackupvalue = new HashMap<String, String>();
		String propname = "車輛";
		String[] lineReportText = { "財產損失：", "核定賠償金", "",
				// "＋(伤亡残疾费用－强三伤亡残疾限额))×事故责任比例",
				"賠償限額：", "實賠金額＝財產賠款金額", };
		int ireport = 0;
		// 汇总车损/物损信息
		List<PrpLloss> prpLlossList = compensateDto.getPrpLlossList();
		if (prpLlossList != null && !prpLlossList.isEmpty()) {
			for (PrpLloss prpLloss : prpLlossList) {
				if (prpLloss != null) {
					if (ConstantCodes.KINDCODE_D_BZ.equals(prpLloss.getKindCode())) {
						for (j = 1; j <= licensenoNumber; j++) {
							if (arrKindCode[j].indexOf(prpLloss.getKindCode()) < 0 && prpLloss.getLicenseNo().equals(arrLicenseNo[j].trim()))
								arrKindCode[j] = arrKindCode[j].trim() + prpLloss.getKindCode();
							if (prpLloss.getLicenseNo().equals(arrLicenseNo[j].trim()))
								break;
						}
						if (j > licensenoNumber && prpLloss.getLicenseNo() != null && prpLloss.getLicenseNo().length() > 0) {
							licensenoNumber++;
							arrLossName[licensenoNumber] = prpLloss.getLossName();
							arrLicenseNo[licensenoNumber] = prpLloss.getLicenseNo();
							arrKindCode[licensenoNumber] = prpLloss.getKindCode();
						}
						if ((prpLloss.getLicenseNo() == null || prpLloss.getLicenseNo().length() == 0) && prpLloss.getLossName() != null && prpLloss.getKindCode().equals(ConstantCodes.KINDCODE_B01_BZ))
							intpropflag = 1;// 车牌号码为空，损失名称不为空，险别不为空，说明是财产损失
					}
				}
			}
		}
		List<String> strLctextList = new ArrayList<String>();
		// 对计入赔款的费用进行累加
		List<PrpLcharge> prpLchargeList = compensateDto.getPrpLchargeList();
		if (prpLchargeList != null && !prpLchargeList.isEmpty()) {
			for (PrpLcharge prpLcharge : prpLchargeList) { // 费用累加;
				if (ConstantCodes.KINDCODE_D_BZ.equals(prpLcharge.getKindCode())) {
					dbChargeRealPay += prpLcharge.getSumRealPay();
				}
			}
		}
		prpLlossList = compensateDto.getPrpLlossList();
		if (prpLlossList != null && !prpLlossList.isEmpty()) {
			for (int i = 1; i <= licensenoNumber; i++) {
				ireport = 0;
				if (arrKindCode[i].indexOf(ConstantCodes.KINDCODE_B01_BZ) < 0)
					continue;
				if (lineNumber == 1)
					strLctextList.add(lineReportText[ireport]);// 财产损失：
				lineNumber++;
				ireport++;
				if (!arrLossName[i].trim().equals(propname)) {
					lineText = space(4) + "財務:(" + arrLossName[i].trim() + ")";
				} else {
					lineText = space(4) + propname + "(" + arrLicenseNo[i].trim() + "號車" + ")";
				}
				mybackupvalue.put("proptitle" + proptitileindex, propname + "(" + arrLicenseNo[i].trim() + ")" + "核定赔偿金");
				proptitileindex++;
				strLctextList.add(lineText);

				lineText = space(4) + lineReportText[ireport]; // 核定赔偿金=
				ireport++;
				lineText += "=";
				textlosscount++;// 还不清楚这个点是干什么髟的

				for (PrpLloss prpLloss : prpLlossList) {
					if (prpLloss != null) {
						if (ConstantCodes.KINDCODE_D_BZ.equals(prpLloss.getKindCode()) && prpLloss.getLicenseNo().equals(arrLicenseNo[i])) {
							if (ExceedFormat(lineText, prpLloss.getFeeTypeName().length() + 2)) {
								strLctextList.add(lineText);
								lineText = space(17) + prpLloss.getFeeTypeName() + "＋";
							} else {
								lineText += prpLloss.getFeeTypeName() + "＋";
							}
						}
					}
				}
				lineText = SubtractDesignate(lineText, "＋");
				lineText += lineReportText[ireport];// 到残值那步了,暂时以空格替代 ireport=2
				// 第三行
				ireport++;
				strLctextList.add(lineText);
//				dbtempSumRest = 0;
				dbtempSumloss = 0;
				lineText = space(16) + "=";
				for (PrpLloss prpLloss : prpLlossList) { // 财产累加;
					if (prpLloss != null) {
						if (ConstantCodes.KINDCODE_D_BZ.equals(prpLloss.getKindCode()) && prpLloss.getLicenseNo().equals(arrLicenseNo[i])) {
							// 不考虑残值
							if (prpLcompensate.getLicenseNo().trim().equals(prpLloss.getLicenseNo().trim())) {
								nulldbpropall = nulldbpropall + prpLloss.getSumDefPay();
							}
							dbtempSumloss += prpLloss.getSumDefPay();
							// 原来取Sumloss,现在取核定赔偿SumDefPay
							if (ExceedFormat(lineText, prpLloss.getFeeTypeName().length() + 2)) {
								strLctextList.add(lineText);
								// 原来取Sumloss,现在取核定赔偿SumDefPay
								lineText = space(17) + formatPay(prpLloss.getSumDefPay()) + "＋";
							} else {
								// 原来取Sumloss,现在取核定赔偿SumDefPay
								lineText += formatPay(prpLloss.getSumDefPay()) + "＋";

							}
						}
					}
				}
				lineText = SubtractDesignate(lineText, "＋");
				strLctextList.add(lineText); // 第四行
				lineText = space(16) + "=" + formatPay(dbtempSumloss) + "元"; // 不考虑残值

				dbpropall += dbtempSumloss; // 不考虑残值 LYM
				mybackupvalue.put("prop" + propindex, formatPay(dbtempSumloss)); // 不考虑残值

				propindex++;
				strLctextList.add(lineText);

			}
			lineText = "";
			lineNumber = 1;
//			dbtempSumRest = 0;
			dbtempSumloss = 0;

			if (intpropflag == 1) {
				textlosscount++;
				for (PrpLloss prpLloss : prpLlossList) {
					if (prpLloss != null) {
						if (ConstantCodes.KINDCODE_D_BZ.equals(prpLloss.getKindCode()) && (DataUtils.emptyToNull(prpLloss.getLicenseNo()) == null) && prpLloss.getLossName() != null) {
							lineText = space(4) + "財務:" + prpLloss.getLossName();
							strLctextList.add(lineText);
							mybackupvalue.put("proptitle" + proptitileindex, prpLloss.getLossName() + "核定赔偿金");
							proptitileindex++;
							lineText = space(4) + lineReportText[1];
							lineText += "=";
							lineText += prpLloss.getFeeTypeName();
							lineText += lineReportText[2];
							strLctextList.add(lineText);
							dbtempSumloss = prpLloss.getSumDefPay();
							lineText = space(16) + "=";
							lineText += formatPay(dbtempSumloss);// 不考虑残值
							strLctextList.add(lineText);
							lineText = space(16) + "=" + formatPay(dbtempSumloss) + "元"; // 不考虑残值
							strLctextList.add(lineText);
							dbpropall += dbtempSumloss; // 不考虑残值
							mybackupvalue.put("prop" + propindex, formatPay(dbtempSumloss));// 不考虑残值
							propindex++;
						}
					}
				}
			}
			if (dbpropall > 0) {
				lineText = space(4) + "財產總核定賠償金";
				lineText += "=";
				for (int jj = 0; jj < proptitileindex; jj++) {
					lineText += mybackupvalue.get("proptitle" + jj).toString() + "＋";
				}
				lineText = SubtractDesignate(lineText, "＋");
				if (dbChargeRealPay != 0 && compensateDto.getPrpLlossList().size() > 0) {
					lineText = lineText + " +  施救費用 ";
				}

				strLctextList.add(lineText);
				lineText = space(16) + "=";
				for (int jj = 0; jj < propindex; jj++) {
					lineText += mybackupvalue.get("prop" + jj).toString() + "＋";
				}
				lineText = SubtractDesignate(lineText, "＋");
				if (dbChargeRealPay != 0) {
					lineText = lineText + " + " + dbChargeRealPay; // 加施救费
					dbpropall += dbChargeRealPay;
				}

				strLctextList.add(lineText);
				lineText = space(16) + "=" + formatPay(dbpropall) + "元"; // 得加上施救费,暂时写死100,这里100应包含到dbpropall
				strLctextList.add(lineText); // 财产总核定赔偿金
				if ((dbpropall - nulldbpropall) >= propLimit) {
					if ((dbpropall - nulldbpropall) > propLimit) {
						lineText = space(4) + " 因核定賠償金超過財產賠償限額(" + formatPay(propLimit) + "元)，所以：";
						strLctextList.add(lineText);
					}
					if (nulldbpropall > 0) {
						lineText = space(4) + "財產賠款金額＝財產賠償限額+ 無責代賠金額";
					} else {
						lineText = space(4) + "財產賠款金額＝財產賠償限額";
					}
					strLctextList.add(lineText);
					if (nulldbpropall > 0) {
						lineText = space(16) + "=" + propLimit + "+" + nulldbpropall;
					} else {
						lineText = space(16) + "=" + propLimit;
					}
					strLctextList.add(lineText);
					if (nulldbpropall > 0) {
						propLimit = propLimit + nulldbpropall;
					}
					lineText += "=" + formatPay(propLimit) + "元"; // 得加上施救费,暂时写死100
					dbpropall = propLimit; //
				} else {
					if (nulldbpropall > 0) {
						lineText = space(4) + "財產賠款金額＝財產賠償金額+ 無責代賠金額";
					} else {
						lineText = space(4) + "財產賠款金額＝財產賠償限額";
					}
					strLctextList.add(lineText);
					if (nulldbpropall > 0) {
						lineText = space(16) + "=" + (dbpropall - nulldbpropall) + "+" + nulldbpropall;
					} else {
						lineText = space(16) + "=" + dbpropall;
					}
					strLctextList.add(lineText);
					propLimit = dbpropall;
					lineText = space(16) + "=" + formatPay(propLimit) + "元"; // 得加上施救费,暂时写死100
					dbpropall = propLimit;
				}

				strLctextList.add(lineText);
			}
		} else if (prpLlossList.size() < 1 && prpLchargeList != null && !prpLchargeList.isEmpty() && dbChargeRealPay != 0) {
			// 财产部分---end-----下面是只有施救费的情况-----------------------------------------
			strLctextList.add(lineReportText[ireport]);
			lineText = space(4) + "核定賠償金 = 施救費";
			strLctextList.add(lineText);
			lineText = space(14) + " =" + formatPay(dbChargeRealPay) + "元";
			strLctextList.add(lineText);
			dbpropall = dbChargeRealPay;
			if ((dbpropall) > propLimit) {
				lineText = space(4) + " 因核定賠償金超過財產賠償限額(" + formatPay(propLimit) + "元)，所以：";
				strLctextList.add(lineText);
				lineText = space(4) + "財產賠款金額＝財產賠償限額";
				lineText += "=" + formatPay(propLimit) + "元"; // 得加上施救费,暂时写死100
				dbpropall = propLimit; //
			} else {
				lineText = space(4) + "財產賠款金額＝財產核定賠償金";
				lineText += "=" + formatPay(dbpropall) + "元"; // 得加上施救费,暂时写死100
			}
			strLctextList.add(lineText);
		}
		if (dbpropall > 0) {
			lineText = "本案實賠金額"; // /这里都被写死了
			strLctextList.add(lineText);
			lineText = space(4) + lineReportText[4];
			strLctextList.add(lineText);
			lineText = space(16) + "=" + formatPay(dbpropall);

			strLctextList.add(lineText);
			lineText = space(16) + "=" + formatPay(dbpropall) + "元";
			strLctextList.add(lineText);
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

	public void ReportForCompelB(HttpServletRequest httpServletRequest, CompensateDto compensateDto, CompensateData compensateData) throws UserException, Exception {
		// 限额
		double propLimit = 0; // 财产限额
		double medicLimit = 0; // 医疗限额
		double deathLimit = 0; // 死亡残疾限额
		double dbChargeRealPay = 0; // 记入赔款费用 wealthTitle
		double nulldbpropall = 0; // 无责代赔金额
		String[] StrPropLimit = httpServletRequest.getParameterValues("wealth");
		String[] strMedicLimit = httpServletRequest.getParameterValues("medical");
		String[] strDeathLimit = httpServletRequest.getParameterValues("diedeformity");
		propLimit = Double.parseDouble(StrPropLimit[4]);
		medicLimit = Double.parseDouble(strMedicLimit[4]);
		deathLimit = Double.parseDouble(strDeathLimit[4]);

		int licensenoNumber = 0;
		int personcount = 0;
		int textlosscount = 0;
		int textpersoncount = 0;
		int j = 0;
		int intpropflag = 0;
		String[] arrKindCode = new String[20];
		String[] arrLicenseNo = new String[20];
		String[] arrKindCodePerson = new String[20];
		String[] arrPersonNo = new String[20];
		String[] arrPersonName = new String[20];
		String[] arrFamilyName = new String[20];
		String[] arrLossName = new String[20];
		String[] arrFeeReport = {};
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		String strCompensateNo = prpLcompensate.getCompensateNo();
		String lineText = "";
		int lineNumber = 1;
		int propindex = 0;
		int proptitileindex = 0;
		double dbtempSumloss = 0;
//		double dbtempSumRest = 0;
		double dbpropall = 0;
		HashMap<String, String> mybackupvalue = new HashMap<String, String>();
		String propname = "車輛";
		String[] lineReportText = { "財產損失：", "核定賠償金", "", "＋(傷亡失能費用－強制險傷亡失能限額))×事故責任比例", "賠償限額：", "實賠金額＝（財產賠款金額＋醫療賠款金額＋死亡傷殘賠款金額）", };
		int ireport = 0;

		// 汇总车损/物损信息
		List<PrpLloss> prpLlossList = compensateDto.getPrpLlossList();
		if (prpLlossList != null && !prpLlossList.isEmpty()) {
			for (PrpLloss prpLloss : prpLlossList) {
				for (j = 1; j <= licensenoNumber; j++) {
					if (arrKindCode[j].indexOf(prpLloss.getKindCode()) < 0 && prpLloss.getLicenseNo().equals(arrLicenseNo[j].trim()))
						arrKindCode[j] = arrKindCode[j].trim() + prpLloss.getKindCode();
					if (prpLloss.getLicenseNo().equals(arrLicenseNo[j].trim()))
						break;
				}
				if (j > licensenoNumber && prpLloss.getLicenseNo() != null && prpLloss.getLicenseNo().length() > 0) {
					licensenoNumber++;
					arrLossName[licensenoNumber] = prpLloss.getLossName();
					arrLicenseNo[licensenoNumber] = prpLloss.getLicenseNo();
					arrKindCode[licensenoNumber] = prpLloss.getKindCode();
				}
				if ((prpLloss.getLicenseNo() == null || prpLloss.getLicenseNo().length() == 0) && prpLloss.getLossName() != null && prpLloss.getKindCode().equals(ConstantCodes.KINDCODE_D_BZ))
					intpropflag = 1;// 车牌号码为空，损失名称不为空，险别不为空，说明是财产损失
			}
		}
		List<PrpLpersonLoss> prpLpersonLossList = compensateDto.getPrpLpersonLossList();
		if (prpLpersonLossList != null && !prpLpersonLossList.isEmpty()) {
			// 汇总人伤信息
			for (PrpLpersonLoss prpLpersonLoss : prpLpersonLossList) {
				for (j = 1; j <= personcount; j++) {
					if (arrKindCodePerson[j].indexOf(prpLpersonLoss.getKindCode()) < 0 && String.valueOf(prpLpersonLoss.getPersonNo()).equals(arrPersonNo[j].trim()))
						arrKindCodePerson[j] = arrKindCodePerson[j].trim() + prpLpersonLoss.getKindCode();
					if (String.valueOf(prpLpersonLoss.getPersonNo()).equals(arrPersonNo[j].trim()))
						break;
				}
				if (j > personcount) {
					personcount++;
					arrKindCodePerson[personcount] = prpLpersonLoss.getKindCode();
					arrPersonNo[personcount] = String.valueOf(prpLpersonLoss.getPersonNo());
					arrPersonName[personcount] = prpLpersonLoss.getPersonName();
					arrFamilyName[personcount] = prpLpersonLoss.getFamilyName();
				}
			}
		}
		List<String> strLctextList = new ArrayList<String>();
		// 对计入赔款的费用进行累加
		List<PrpLcharge> prpLchargeList = compensateDto.getPrpLchargeList();
		if (prpLchargeList != null && !prpLchargeList.isEmpty()) {
			for (PrpLcharge prpLcharge : prpLchargeList) { // 费用累加;
				if (ConstantCodes.KINDCODE_D_BZ.equals(prpLcharge.getKindCode())) {
					dbChargeRealPay += prpLcharge.getSumRealPay();
				}
			}
		}
		if (prpLlossList != null && !prpLlossList.isEmpty()) {
			for (int i = 1; i <= licensenoNumber; i++) {
				ireport = 0;
				if (arrKindCode[i].indexOf(ConstantCodes.KINDCODE_D_BZ) < 0)
					continue;
				if (lineNumber == 1)
					strLctextList.add(lineReportText[ireport]);// 财产损失：
				lineNumber++;
				ireport++;
				if (!arrLossName[i].trim().equals(propname)) {
					lineText = space(4) + "財務:(" + arrLossName[i].trim() + ")";
				} else {
					lineText = space(4) + propname + "(" + arrLicenseNo[i].trim() + "號車" + ")";
				}
				mybackupvalue.put("proptitle" + proptitileindex, propname + "(" + arrLicenseNo[i].trim() + ")" + "核定赔偿金");
				proptitileindex++;
				strLctextList.add(lineText);

				lineText = space(4) + lineReportText[ireport]; // 核定赔偿金=
				ireport++;
				lineText += "=";
				textlosscount++;// 还不清楚这个点是干什么髟的

				for (PrpLloss prpLloss : prpLlossList) {
					if (ConstantCodes.KINDCODE_D_BZ.equals(prpLloss.getKindCode()) && prpLloss.getLicenseNo().equals(arrLicenseNo[i])) {
						if (ExceedFormat(lineText, prpLloss.getFeeTypeName().length() + 2)) {
							strLctextList.add(lineText);
							lineText = space(17) + prpLloss.getFeeTypeName() + "＋";
						} else {
							lineText += prpLloss.getFeeTypeName() + "＋";
						}
					}
				}
				lineText = SubtractDesignate(lineText, "＋");
				lineText += lineReportText[ireport];// 到残值那步了,暂时以空格替代 ireport=2
				// 第三行

				ireport++;
				strLctextList.add(lineText);
//				dbtempSumRest = 0;
				dbtempSumloss = 0;

				lineText = space(16) + "=";
				for (PrpLloss prpLloss : prpLlossList) { // 财产累加;
					if (ConstantCodes.KINDCODE_D_BZ.equals(prpLloss.getKindCode()) && prpLloss.getLicenseNo().equals(arrLicenseNo[i])) {
						if (prpLcompensate.getLicenseNo().trim().equals(prpLloss.getLicenseNo())) {
							nulldbpropall = nulldbpropall + prpLloss.getSumDefPay();
						}
						dbtempSumloss += prpLloss.getSumDefPay(); // 原来取Sumloss,现在取核定赔偿SumDefPay
						if (ExceedFormat(lineText, prpLloss.getFeeTypeName().length() + 2)) {
							strLctextList.add(lineText);
							lineText = space(17) + formatPay(prpLloss.getSumDefPay()) + "＋"; // 原来取Sumloss,现在取核定赔偿SumDefPay
						} else {
							lineText += formatPay(prpLloss.getSumDefPay()) + "＋"; // 原来取Sumloss,现在取核定赔偿SumDefPay
						}
					}
				}
				lineText = SubtractDesignate(lineText, "＋");
				strLctextList.add(lineText); // 第四行
				lineText = space(16) + "=" + formatPay(dbtempSumloss) + "元"; // 不考虑残值
				dbpropall += dbtempSumloss; // 不考虑残值 LYM
				mybackupvalue.put("prop" + propindex, formatPay(dbtempSumloss)); // 不考虑残值
				propindex++;
				strLctextList.add(lineText);

			}
			lineText = "";
			lineNumber = 1;
//			dbtempSumRest = 0;
			dbtempSumloss = 0;

			if (intpropflag == 1) {
				textlosscount++;
				for (PrpLloss prpLloss : prpLlossList) {
					if (ConstantCodes.KINDCODE_D_BZ.equals(prpLloss.getKindCode()) && (DataUtils.emptyToNull(prpLloss.getLicenseNo()) == null) && prpLloss.getLossName() != null) {
						lineText = space(4) + "財務:" + prpLloss.getLossName();
						strLctextList.add(lineText);
						mybackupvalue.put("proptitle" + proptitileindex, prpLloss.getLossName() + "核定賠償金");
						proptitileindex++;
						lineText = space(4) + lineReportText[1];
						lineText += "=";
						lineText += prpLloss.getFeeTypeName();
						lineText += lineReportText[2];
						strLctextList.add(lineText);
						dbtempSumloss = prpLloss.getSumDefPay();
						lineText = space(16) + "=";
						lineText += formatPay(dbtempSumloss);// 不考虑残值
						strLctextList.add(lineText);
						lineText = space(16) + "=" + formatPay(dbtempSumloss) + "元"; // 不考虑残值
						strLctextList.add(lineText);
						dbpropall += dbtempSumloss; // 不考虑残值
						mybackupvalue.put("prop" + propindex, formatPay(dbtempSumloss));// 不考虑残值
						propindex++;
					}
				}
			}
			lineText = space(4) + "財產總核定賠償金";
			lineText += "=";
			for (int jj = 0; jj < proptitileindex; jj++) {
				lineText += mybackupvalue.get("proptitle" + jj).toString() + "＋";
			}
			lineText = SubtractDesignate(lineText, "＋");
			if (dbChargeRealPay != 0 && compensateDto.getPrpLlossList().size() > 0) {
				lineText = lineText + " +  施救費用";
			}

			strLctextList.add(lineText);
			lineText = space(16) + "=";
			for (int jj = 0; jj < propindex; jj++) {
				lineText += mybackupvalue.get("prop" + jj).toString() + "＋";
			}
			lineText = SubtractDesignate(lineText, "＋");
			if (dbChargeRealPay != 0) {
				lineText = lineText + " + " + dbChargeRealPay; // 加施救费
				dbpropall += dbChargeRealPay;
			}

			strLctextList.add(lineText);
			lineText = space(16) + "=" + formatPay(dbpropall) + "元"; // 得加上施救费,暂时写死100,这里100应包含到dbpropall
			strLctextList.add(lineText); // 财产总核定赔偿金zheyihang
			if ((dbpropall - nulldbpropall) > propLimit) {
				lineText = space(4) + " 因核定賠償金超過財產賠償限額(" + formatPay(propLimit) + "元)，所以：";
				strLctextList.add(lineText);
				if (nulldbpropall > 0) {
					lineText = space(4) + "財產賠款金額＝財產賠償限額+ 無責代賠金額";
				} else {
					lineText = space(4) + "財產賠款金額＝財產賠償限額";
				}
				if (nulldbpropall > 0) {
					propLimit = propLimit + nulldbpropall;
				}
				lineText += "=" + formatPay(propLimit) + "元"; // 得加上施救费,暂时写死100
				dbpropall = propLimit; //
			} else {
				lineText = space(4) + "財產賠款金額＝財產核定賠償金";
				lineText += "=" + formatPay(dbpropall) + "元"; // 得加上施救费,暂时写死100
			}
			strLctextList.add(lineText);
		} else if (compensateDto.getPrpLlossList().size() < 1 && prpLchargeList != null && !prpLchargeList.isEmpty()) {
			// 财产部分---end-----下面是只有施救费的情况-----------------------------------------
			strLctextList.add(lineReportText[ireport]);
			lineText = space(4) + "核定賠償金 = 施救費";
			strLctextList.add(lineText);
			lineText = space(14) + " =" + formatPay(dbChargeRealPay) + "元";
			strLctextList.add(lineText);
			dbpropall = dbChargeRealPay;
			if ((dbpropall) > propLimit) {
				lineText = space(4) + "因核定賠償金超過財產賠償限額(" + formatPay(propLimit) + "元)，所以：";
				strLctextList.add(lineText);
				lineText = space(4) + "財產賠款金額＝財產賠償限額";
				lineText += "=" + formatPay(propLimit) + "元"; // 得加上施救费,暂时写死100
				dbpropall = propLimit; //
			} else {
				lineText = space(4) + "財產賠款金額＝財產核定賠償金";
				lineText += "=" + formatPay(dbpropall) + "元"; // 得加上施救费,暂时写死100
			}
			strLctextList.add(lineText);
		}

		String strFeeReport = "";
		String strFeeValueReport = "";
		int persontitleindex = 0;
		int personindex = 0;
		double dbpersonall = 0;
		double dbpersonmedicalall = 0;
		double dbpersondeformityall = 0;
//		dbtempSumRest = 0;
		dbtempSumloss = 0;
		String medicFlag = "N";
		String deathFlag = "N";
		if (prpLpersonLossList != null && !prpLpersonLossList.isEmpty()) {
			for (PrpLpersonLoss prpLpersonLoss : prpLpersonLossList) {
				if ("M".equals(prpLpersonLoss.getFeeCategory())) {
					medicFlag = "Y";
				}
				if ("D".equals(prpLpersonLoss.getFeeCategory())) {
					deathFlag = "Y";
				}
			}
			if ("Y".equals(medicFlag)) {
				lineText = "醫療費用：";
				strLctextList.add(lineText);
				for (j = 1; j <= personcount; j++) {
					if (arrKindCodePerson[j].indexOf("B") < 0)
						continue;
					lineText = space(4) + "出險人員" + "  " + arrPersonName[j].trim() + "：";
					strLctextList.add(lineText);
					mybackupvalue.put("persontitle" + persontitleindex, arrPersonName[j].trim() + "核定賠償金");
					persontitleindex++;
					textpersoncount++;
					for (PrpLpersonLoss prpLpersonLoss : prpLpersonLossList) {
						if (String.valueOf(prpLpersonLoss.getPersonNo()).equals(arrPersonNo[j]) && prpLpersonLoss.getFeeCategory().equals("M")) {
							strFeeReport += prpLpersonLoss.getLiabDetailName();
							strFeeReport += "＋";
							strFeeValueReport += formatPay(prpLpersonLoss.getSumDefPay());
							strFeeValueReport += "＋";
							dbtempSumloss += prpLpersonLoss.getSumDefPay();
						}
					}
					strFeeReport = space(4) + lineReportText[1] + "=" + strFeeReport;
					strFeeValueReport = space(16) + "=" + strFeeValueReport;
					if (StringUtils.getBytesLength(strFeeReport) > 60) {
						arrFeeReport = (String[]) StringUtils.split(strFeeReport, 60);
						lineText = arrFeeReport[0];
						strLctextList.add(lineText);
						lineText = space(7) + arrFeeReport[1];
					} else {
						lineText = strFeeReport;
					}
					lineText = SubtractDesignate(lineText, "＋");
					lineText = lineText + lineReportText[2];
					strLctextList.add(lineText);
					if (StringUtils.getBytesLength(strFeeValueReport) > 60) {
						arrFeeReport = (String[]) StringUtils.split(strFeeValueReport, 60);
						lineText = arrFeeReport[0];
						strLctextList.add(lineText);
						lineText = space(7) + arrFeeReport[1];
					} else {
						lineText = strFeeValueReport;
					}
					lineText = SubtractDesignate(lineText, "＋");
					// //暂时不要残值 LYM
					strLctextList.add(lineText);
					lineText = space(16) + "=" + formatPay(dbtempSumloss) + "元"; // 暂时不要残值
					strLctextList.add(lineText);
					mybackupvalue.put("person" + personindex, formatPay(dbtempSumloss)); // 暂时不要残值
					dbpersonall += (dbtempSumloss); // 暂时不要残值 LYM
					personindex++;
					strFeeReport = "";
					strFeeValueReport = "";
//					dbtempSumRest = 0;
					dbtempSumloss = 0;
				}
				lineText = space(4) + "醫療核定賠償金=";
				for (int jj = 0; jj < persontitleindex; jj++) {
					lineText += mybackupvalue.get("persontitle" + jj).toString() + "＋";
				}
				lineText = SubtractDesignate(lineText, "＋");
				strLctextList.add(lineText);
				lineText = space(16) + "=";
				for (int jj = 0; jj < personindex; jj++) {
					lineText += mybackupvalue.get("person" + jj).toString() + "＋";
				}
				lineText = SubtractDesignate(lineText, "＋");
				strLctextList.add(lineText);
				lineText = space(16) + "=" + formatPay(dbpersonall) + "元";

				if (dbpersonall > medicLimit) {
					lineText = space(4) + "因醫療核定賠償金超過醫療費用限額（" + formatPay(medicLimit) + "元）,所以：";
					strLctextList.add(lineText);
					lineText = space(4) + "醫療賠款金額＝醫療費用限額 ";
					lineText += "=" + formatPay(medicLimit) + "元";
					dbpersonmedicalall = medicLimit;
				} else {
					lineText = space(4) + "醫療賠款金額＝醫療核定賠償金 ";
					lineText += "=" + formatPay(dbpersonall) + "元";
					dbpersonmedicalall = dbpersonall;
				}
				strLctextList.add(lineText);
			}
			if ("Y".equals(deathFlag)) {
				strFeeReport = "";
				strFeeValueReport = "";
				persontitleindex = 0;
				personindex = 0;
				dbpersonall = 0;
//				dbtempSumRest = 0;
				dbtempSumloss = 0;
				lineText = "死亡傷殘費用：";
				strLctextList.add(lineText);
				for (j = 1; j <= personcount; j++) {
					if (arrKindCodePerson[j].indexOf(ConstantCodes.KINDCODE_D_BZ) < 0)
						continue;
					lineText = space(4) + "出險人員" + "  " + arrPersonName[j].trim() + "：";
					strLctextList.add(lineText);
					mybackupvalue.put("persondeformitytitle" + persontitleindex, arrPersonName[j].trim() + "核定賠償金");
					persontitleindex++;
					textpersoncount++;
					if (prpLpersonLossList != null && !prpLpersonLossList.isEmpty()) {
						for (PrpLpersonLoss prpLpersonLoss : prpLpersonLossList) {
							if (String.valueOf(prpLpersonLoss.getPersonNo()).equals(arrPersonNo[j]) && prpLpersonLoss.getFeeCategory().equals("D")) {
								strFeeReport += prpLpersonLoss.getLiabDetailName();
								strFeeReport += "＋";
								strFeeValueReport += formatPay(prpLpersonLoss.getSumDefPay());
								strFeeValueReport += "＋";
								// //暂时不要残值 LYM
								dbtempSumloss += prpLpersonLoss.getSumDefPay();
							}
						}
					}
					strFeeReport = space(4) + lineReportText[1] + "=" + strFeeReport;
					strFeeValueReport = space(16) + "=" + strFeeValueReport;
					if (StringUtils.getBytesLength(strFeeReport) > 60) {
						arrFeeReport = (String[]) StringUtils.split(strFeeReport, 60);
						lineText = arrFeeReport[0];
						strLctextList.add(lineText);
						lineText = space(7) + arrFeeReport[1];
					} else {
						lineText = strFeeReport;
					}
					lineText = SubtractDesignate(lineText, "＋");
					lineText = lineText + lineReportText[2];
					strLctextList.add(lineText);
					if (StringUtils.getBytesLength(strFeeValueReport) > 60) {
						arrFeeReport = (String[]) StringUtils.split(strFeeValueReport, 60);
						lineText = arrFeeReport[0];
						strLctextList.add(lineText);
						lineText = space(7) + arrFeeReport[1];
					} else {
						lineText = strFeeValueReport;
					}
					lineText = SubtractDesignate(lineText, "＋");
					// //暂时不要残值 LYM
					strLctextList.add(lineText);
					lineText = space(16) + "=" + formatPay(dbtempSumloss) + "元"; // 暂时不要残值

					strLctextList.add(lineText);
					mybackupvalue.put("persondeformity" + personindex, formatPay(dbtempSumloss)); // 暂时不要残值

					dbpersonall += (dbtempSumloss); // 暂时不要残值 LYM
					personindex++;
					strFeeReport = "";
					strFeeValueReport = "";
//					dbtempSumRest = 0;
					dbtempSumloss = 0;

				}
				lineText = space(4) + "死亡傷殘核定賠償金=";
				for (int jj = 0; jj < persontitleindex; jj++) {
					lineText += mybackupvalue.get("persondeformitytitle" + jj).toString() + "＋";
				}
				lineText = SubtractDesignate(lineText, "＋");
				strLctextList.add(lineText);
				lineText = space(16) + "=";
				for (int jj = 0; jj < personindex; jj++) {
					lineText += mybackupvalue.get("persondeformity" + jj).toString() + "＋";
				}
				lineText = SubtractDesignate(lineText, "＋");
				strLctextList.add(lineText);
				lineText = space(16) + "=" + formatPay(dbpersonall) + "元";
				strLctextList.add(lineText);

				if (dbpersonall > deathLimit) {
					lineText = space(4) + "因死亡傷殘核定賠償金超過死亡傷殘限額（" + formatPay(deathLimit) + "元）,所以：";
					strLctextList.add(lineText);
					lineText = space(4) + "死亡傷殘賠款金額＝死亡傷殘限額";
					lineText += "=" + formatPay(deathLimit) + "元";
					dbpersondeformityall = deathLimit;
				} else {
					lineText = space(4) + "死亡傷殘賠款金額＝死亡傷殘核定賠償金 ";
					lineText += "=" + formatPay(dbpersonall) + "元";
					dbpersondeformityall = dbpersonall;
				}
				strLctextList.add(lineText);
			}
		}
		lineText = "本案實賠金額"; // /这里都被写死了
		strLctextList.add(lineText);
		lineText = space(4) + lineReportText[5];
		strLctextList.add(lineText);
		lineText = space(16) + "=（" + formatPay(dbpropall) + "＋" + formatPay(dbpersonmedicalall) + "＋" + formatPay(dbpersondeformityall) + "）";

		strLctextList.add(lineText);
		lineText = space(16) + "=" + formatPay(dbpropall + dbpersonmedicalall + dbpersondeformityall) + "元";
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

	}

	private static class CompensateData {
		List<PrpLctext> prpLctextlist = new ArrayList<PrpLctext>();
	}

	private boolean ExceedFormat(String a, int b) {
		int limitline = 60;
		if (StringUtils.getBytesLength(a) + b > limitline) {
			return true;
		}
		;
		return false;
	}

	private String SubtractDesignate(String a, String b) {
		a = a.substring(0, (a.length() - b.length()));
		return a;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}
}
