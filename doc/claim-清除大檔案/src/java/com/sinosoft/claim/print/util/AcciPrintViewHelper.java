package com.sinosoft.claim.print.util;

import ins.framework.common.QueryRule;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.service.facade.PrpDriskService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.service.facade.PrpPheadService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.DataUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.util.PrintUtils;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.print.vo.AcciPrintObject;
import com.sinosoft.claim.print.vo.AcciPrintSubObject;
import com.sinosoft.claim.print.vo.CompensateContextObject;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCinsuredNature;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.PrpDrisk;
import com.sinosoft.claim.schema.model.PrpDuser;
import com.sinosoft.claim.schema.model.PrpLacciCheck;
import com.sinosoft.claim.schema.model.PrpLcertifyDirect;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLctext;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpPhead;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredNatureService;
import com.sinosoft.claim.schema.service.facade.PrpJPayRefRecHisService;
import com.sinosoft.claim.schema.service.facade.PrpLacciCheckService;
import com.sinosoft.claim.schema.service.facade.PrpLcertifyDirectService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLctextService;
import com.sinosoft.claim.schema.service.facade.PrpLpayObjectInfoService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;

public class AcciPrintViewHelper {

	/** 立案主表Service */
	private PrpLclaimService prpLclaimService;
	/** 报案主表Service */
	private PrpLregistService prpLregistService;
	/** 险种信息Service */
	private PrpDriskService prpDriskService;
	/** 赔款计算书信息Service */
	private PrpLcompensateService prpLcompensateService;
	/** 用户基本信息Service */
	private PrpDuserService prpDuserService;
	/** 意健险调查信息Service */
	private PrpLacciCheckService prpLacciCheckService;
	/** 支付对象信息Service */
	private PrpLpayObjectInfoService prpLpayObjectInfoService;
	/** 机构信息Service */
	private PrpDcompanyService prpDcompanyService;
	/** 索赔单证指引信息Service */
	private PrpLcertifyDirectService prpLcertifyDirectService;
	/** 理算实赔业务处理Service */
	private CompensateService compensateService;
	/** 代码翻译服务Service */
	private CodeService codeService;
	/** 实收实付记录转储表Service */
	private PrpJPayRefRecHisService prpJPayRefRecHisService;
	/** 批单信息Service */
	private PrpPheadService prpPheadService;
	/** 赔款计算文字信息Service */
	private PrpLctextService prpLctextService;
	/** 保单服务 */
	private PolicyService policyService;
	/** 保单回滚功能 */
	private EndorseViewHelper endorseViewHelper;
	/** 被保险人信息 */
	private PrpCinsuredNatureService prpCinsuredNatureService;

	/***
	 * 意健险计算书
	 * @return
	 * @throws Exception
	 */
	public AcciPrintObject printCompensate(Map<String, Object> param, String businessNo) throws Exception {
		DecimalFormat df = new DecimalFormat("#,###");
		String billingUnit = "";
		AcciPrintObject acciPrintObject = new AcciPrintObject();
		PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(CommonUtils.nullToEmpty(businessNo).trim());
		if (prpLcompensate != null) {
			String conditions = " compensateNo = '" + prpLcompensate.getCompensateNo() + "' and textType ='1' order by lineNo asc ";
			List<PrpLctext> prpLctextList = prpLctextService.findPrpLctext(QueryRule.getInstance().addSql(conditions));
			StringBuffer contextAll = new StringBuffer("");// 赔付内容
			CompensateContextObject contextObject = null;
			for (Iterator<PrpLctext> it = prpLctextList.iterator(); it.hasNext();) {
				contextAll.append((it.next()).getContext());
			}
			for (String line : contextAll.toString().split("\r\n")) {
				contextObject = new CompensateContextObject();
				contextObject.setContext(line);
				acciPrintObject.getContextList().add(contextObject);
			}
			String querySql = "";
			param.put("COMPENSATENO", prpLcompensate.getCompensateNo());
			param.put("ENDCASEDATE", PrintUtils.getYearToDayMGStr(prpLcompensate.getInputDate()));
			int intReturn = this.getPolicyService().checkPay(" policyno = '" + prpLcompensate.getPolicyNo() + "'");// -1为未缴费，0为未缴全，1为缴全
			// 收费情形 -1为未缴费，0为未缴全，1为缴全
			if (intReturn < 0) {
				param.put("PLAYNAME", "未繳費");
			} else if (intReturn == 0) {
				param.put("PLAYNAME", "未繳全");
			} else if (intReturn == 1) {
				param.put("PLAYNAME", "繳全");
			}
			param.put("SUMDUTYPAID", df.format(prpLcompensate.getSumPaid()));
			PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo().trim());
			/* mantis： CLM0045 ，處理人員：BK007 蘇哲，需求單編號：CLM0045理賠計算書將開票單位異動成服務人員(非車) --start */
			//經辦人信息查詢
			String handleCode = "";//總公司經辦人code
			String handleName = "";//總公司經辦人name
			String handleCode1 = "";//分公司經辦人code
			String handleName1 = "";//分公司經辦人name
			if(!CommonUtils.isEmpty(prpLclaim.getHandlerCode())){
				String userCode = prpLclaim.getHandlerCode();
				PrpDuser prpDuser = this.prpDuserService.findPrpDuser( userCode);
				if("00".equals(prpDuser.getComCode())){
					handleCode = userCode;
					handleName = prpDuser.getUserName();
				}else {
					handleCode1 = userCode;
					handleName1 = prpDuser.getUserName();
				}
			}
			//服務人員信息查詢
			String handler1Code2 = prpLregistService.findPrpLregist(prpLclaim.getRegistNo()).getHandler1Code();
			String handler1Name = this.getCodeService().translateUserCode(handler1Code2, true);
			//參數放入
			param.put("handleCode", handleCode);
			param.put("handleName", handleName);
			param.put("handleCode1", handleCode1);
			param.put("handleName1", handleName1);
			param.put("handler1Name", handler1Name);
			/* mantis： CLM0045 ，處理人員：BK007 蘇哲，需求單編號：CLM0045理賠計算書將開票單位異動成服務人員(非車) --end */
			querySql = " policyno = '" + prpLcompensate.getPolicyNo() + "' and validdate < to_date('" + new DateTime(prpLclaim.getDamageStartDate(), DateTime.YEAR_TO_DAY) + "','yyyy-mm-dd') order by validdate desc";
			List<PrpPhead> prpPheadList = prpPheadService.findByQueryConditions(querySql);
			if (!CommonUtils.isEmpty(prpPheadList)) {
				PrpPhead prpPhead = prpPheadList.get(0);
				param.put("VALIDDATE", PrintUtils.getYearToDayMGStr(prpPhead.getValidDate()));
			}
			String policyNo = prpLclaim.getPolicyNo();
			String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
			String damageHour = prpLclaim.getDamageStartHour();
			PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate , damageHour);
			String insuredCode = prpLclaim.getInsuredCode();
			String insuredName = prpLclaim.getInsuredName();
			
			List<PrpCinsured> prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName);
			param.put("CLAIMNO", prpLclaim.getClaimNo());
			param.put("POLICYNO", prpLclaim.getPolicyNo());
			param.put("INSUREDNAME", prpLclaim.getInsuredName());
			param.put("DAMAGESTARTDATE", PrintUtils.getYearToDayMGStr(prpLclaim.getDamageStartDate()));
			/* mantis：CLM0113  處理人員：BL061張明財   ，需求單編號：CLM0113.新核心-傷害險增加AML功能--start */
			param.put("receiptdate", PrintUtils.getYearToDayMGStr(CommonUtils.toYearToDayDate(prpLclaim.getReceiptDate().substring(0,11))));
			param.put("CLAIMDATE", PrintUtils.getYearToDayMGStr(prpLclaim.getClaimDate()));
			/* mantis：CLM0113  處理人員：BL061張明財   ，需求單編號：CLM0113.新核心-傷害險增加AML功能--end */
			param.put("INPUTDATE", PrintUtils.getYearToDayMGStr(prpCmain.getInputDate()));
			param.put("RISKNAME", this.codeService.translateRiskCode(prpLclaim.getRiskCode(), true));
			if (prpCmain != null) {
				//mantis： CLM0099 ，處理人員：BK007 蘇哲，需求單編號：CLM0099 新核心-PA理賠計畫書欄位調整
				String strInsuredTerm = PrintUtils.getYearToDayMGStr(prpLclaim.getStartDate())+" "+prpLclaim.getStartHour()+" 時起   "+PrintUtils.getYearToDayMGStr(prpLclaim.getEndDate())+" "+prpLclaim.getEndHour()+" 時止";
				param.put("INSUREDTERM", strInsuredTerm);
				param.put("APPLINAME", prpCmain.getAppliName());
				param.put("SUMPREMIUN", df.format(prpCmain.getSumPremium()));
				param.put("INPUTDATE", PrintUtils.getYearToDayMGStr(prpCmain.getInputDate()));
			}
//			int insuredCount = 0;// 统计被保险人的个数
			String prpCinsuredBearer = "";//是否不计明保单
			String totalInsuredNo ="0";//不计名保单人数
			int familyNo = 0;
			for (PrpCinsured prpCinsured : prpCinsuredList) {
				if ("2".equals(prpCinsured.getInsuredFlag())) {// 要保人ID
					param.put("IDENTIFYNUMBER1", prpCinsured.getIdentifyNumber());
					prpCinsuredBearer = prpCinsured.getBearer();
					totalInsuredNo = prpCinsured.getTotalInsuredNo();
				} else if ("1".equals(prpCinsured.getInsuredFlag())) {
					if (DataUtils.dbNullToEmpty(prpLclaim.getInsuredCode()).equals(DataUtils.dbNullToEmpty((prpCinsured.getIdentifyNumber())))
							&& DataUtils.dbNullToEmpty(prpLclaim.getInsuredName()).equals(DataUtils.dbNullToEmpty((prpCinsured.getInsuredName())))) {
						param.put("IDENTIFYNUMBER2", prpCinsured.getIdentifyNumber());
						familyNo = prpCinsured.getId().getSerialNo();
					}
				}
				if (param.containsKey("IDENTIFYNUMBER1") && param.containsKey("IDENTIFYNUMBER2")) {
					break;
				}
			}
			if("1".equals(prpCinsuredBearer) && !CommonUtils.isEmpty(totalInsuredNo)){
				param.put("INSUREDNAME", prpLclaim.getInsuredName() + "  不記名保單，等" + totalInsuredNo + "人");
			}
			param.put("FAMILYNO", String.format("%06d", familyNo));
			querySql = "select centerCode from prplplan where certino='" + prpLcompensate.getCompensateNo() + "'";
			List<?> tempResult = prpJPayRefRecHisService.findByQueryConditions(querySql);
			if (!CommonUtils.isEmpty(tempResult)) {// 开票单位
				billingUnit = tempResult.get(0).toString();
			}
			querySql = "select payRefDate from prpJPayRefRecHis where policyno = '" + prpLclaim.getPolicyNo() + "' and realpayrefflag = '1' and certitype='P'";
			tempResult = prpJPayRefRecHisService.findByQueryConditions(querySql);
			if (!CommonUtils.isEmpty(tempResult)) {// 收费日期
				param.put("PAYDATE", PrintUtils.getYearToDayMGStr(new Date(((Timestamp) tempResult.get(0)).getTime())));
			}
			querySql = "select billEndDate From prpjfeebillsub Where businessno In (select businessNo From prpjpayinfo Where certino='" + prpLclaim.getPolicyNo() + "')";
			tempResult = prpJPayRefRecHisService.findByQueryConditions(querySql);
			if (!CommonUtils.isEmpty(tempResult)) {// 票据到期日
				param.put("NOTESMATURITYDATE", PrintUtils.getYearToDayMGStr(CommonUtils.toYearToDayDate(tempResult.get(0).toString())));
			}
			// mantis：CLM0260，處理人員：DP0714，新核心-PA、TA列印理算書新增[文件備齊日]欄位 -- start
			String fileReadyDate = "";
			if (org.apache.commons.lang3.StringUtils.isNotBlank(prpLcompensate.getFileReadyDate())) {
				fileReadyDate = prpLcompensate.getFileReadyDate();
				// 轉民國年
				int CYear = new Integer(fileReadyDate.substring(0, 4)) - 1911;
				fileReadyDate = CYear + fileReadyDate.substring(4, 10);
			}
			param.put("FILE_READY_DATE", fileReadyDate);
			// mantis：CLM0260，處理人員：DP0714，新核心-PA、TA列印理算書新增[文件備齊日]欄位 -- end
			AcciPrintSubObject acciPrintSubObject = null;
			List<AcciPrintSubObject> compensateInfoList = new ArrayList<AcciPrintSubObject>();
			List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, familyNo);
			if (!CommonUtils.isEmpty(prpCitemKindList)) {
				List<String> tempKindCodes = new ArrayList<String>();
				List<String> tempList = this.compensateService.getPayRiskCode(prpLcompensate.getCompensateNo());
				for (int index = 0; index < prpCitemKindList.size(); index++) {
					PrpCitemKind prpCitemKind = prpCitemKindList.get(index);
					if (tempList.contains(prpCitemKind.getKindCode()) && !tempKindCodes.contains(prpCitemKind.getKindCode())) {
						tempKindCodes.add(prpCitemKind.getKindCode());
						acciPrintSubObject = new AcciPrintSubObject();
						acciPrintSubObject.setKindCode(prpCitemKind.getKindCode());
						acciPrintSubObject.setKindName(prpCitemKind.getKindName());
						acciPrintSubObject.setAmount(df.format(prpCitemKind.getAmount()));
						compensateInfoList.add(acciPrintSubObject);
					}
				}
			}
			acciPrintObject.setCompensateInfoList(compensateInfoList);
			List<AcciPrintSubObject> sumFeeInfoList = new ArrayList<AcciPrintSubObject>();
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.compensateNo", CommonUtils.nullToEmpty(businessNo).trim());
			// queryRule.addEqual("id.certiType",
			// PrpLpayObjectInfo.CERTITYPE_PAYOBJECT);
			queryRule.addAscOrder("id.serialNo");
			List<PrpLpayObjectInfo> prpLpayObjectInfoList = prpLpayObjectInfoService.findPrpLpayObjectInfo(queryRule);
			PrpLpayObjectInfo prpLpayObjectInfo = null;
			for (int index = 0; index < prpLpayObjectInfoList.size(); index++) {
				prpLpayObjectInfo = prpLpayObjectInfoList.get(index);
				acciPrintSubObject = new AcciPrintSubObject();
				acciPrintSubObject.setNo(index + 1);
				acciPrintSubObject.setPersonName(prpLpayObjectInfo.getOwnerName());
				if ("01".equals(prpLpayObjectInfo.getCertificateCode())) {
					acciPrintSubObject.setID((DataUtils.dbNullToEmpty(prpLpayObjectInfo.getUniformNo())));
				}
				acciPrintSubObject.setBillingUnit(billingUnit);
				acciPrintSubObject.setComsumRealPay(df.format(prpLpayObjectInfo.getPayAmount()));
				String bankCode = prpLpayObjectInfo.getBankCode();
				String customBankCode = prpLpayObjectInfo.getCustomBankCode();
				acciPrintSubObject.setAccountCode(DataUtils.dbNullToEmpty(prpLpayObjectInfo.getAccountCode()));// 账号
				acciPrintSubObject.setBankCode(DataUtils.dbNullToEmpty(bankCode));// 总行代码
				acciPrintSubObject.setCustomBankCode(DataUtils.dbNullToEmpty(customBankCode));// 分行代码
				acciPrintSubObject.setCustomBankName(DataUtils.dbNullToEmpty(prpLpayObjectInfo.getCustomBankName()));// 银行名称
				if (!CommonUtils.isEmpty(customBankCode) && !CommonUtils.isEmpty(bankCode) && customBankCode.startsWith(bankCode)) {
					acciPrintSubObject.setCustomBankCode(customBankCode.substring(bankCode.length()));// 分行代码
				}
				/* mantis：CLM0113  處理人員：BL061張明財   ，需求單編號：CLM0113.新核心-傷害險增加AML功能--start */
				acciPrintSubObject.setAmlFlagDesc(DataUtils.dbNullToEmpty(prpLpayObjectInfo.getAmlFlag()));
				/* mantis：CLM0113  處理人員：BL061張明財   ，需求單編號：CLM0113.新核心-傷害險增加AML功能--start */
				sumFeeInfoList.add(acciPrintSubObject);
			}
			acciPrintObject.setSumFeeInfoList(sumFeeInfoList);
		} else {
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的計算書號碼！");
		}
		return acciPrintObject;
	}

	/***
	 * 補件通知函
	 * @return
	 * @throws Exception
	 */
	public AcciPrintObject printNotification(Map<String, Object> param, String businessNo) throws Exception {
		AcciPrintObject acciPrintObject = new AcciPrintObject();
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(CommonUtils.nullToEmpty(businessNo).trim());
		if (prpLclaim != null) {
			PrpDrisk prpDrisk = this.prpDriskService.findPrpDrisk(prpLclaim.getRiskCode());
			param.put("CLAIMNO", prpLclaim.getClaimNo());
			param.put("INSUREDNAME", DataUtils.dbNullToEmpty(prpLclaim.getInsuredName()));
			param.put("DAMAGENAME", DataUtils.dbNullToEmpty(prpLclaim.getDamageName()));
			param.put("DAMAGESTARTDATE", PrintUtils.getYearToDayMGName(prpLclaim.getDamageStartDate()));
			param.put("NOWDATE", PrintUtils.getYearToDayMGName(new Date()));// 得到当前日期
			if (prpDrisk != null) {
				param.put("RISKNAME", DataUtils.dbNullToEmpty(prpDrisk.getRiskCName()));
			}
			PrpDcompany prpDcompany = prpDcompanyService.findByPrimaryKey(prpLclaim.getMakeCom());
			if (prpDcompany != null) {
				param.put("ADDRESSCNAME", DataUtils.dbNullToEmpty(prpDcompany.getAddressCName()));
				param.put("SERVICEPHONE", DataUtils.dbNullToEmpty(prpDcompany.getServicePhone()));
			}
			PrpDuser prpDuser = prpDuserService.findPrpDuser(prpLclaim.getHandlerCode());
			if (prpDuser != null) {
				param.put("USERNAME", DataUtils.dbNullToEmpty(prpDuser.getUserName()));
				param.put("HPONE", DataUtils.dbNullToEmpty(prpDuser.getPhone()));
			}
		}
		AcciPrintSubObject acciPrintSubObject = null;
		List<AcciPrintSubObject> list = new ArrayList<AcciPrintSubObject>();
		List<PrpLcertifyDirect> prpLcertifyDirectList = prpLcertifyDirectService.findPrpLcertifyDirect(prpLclaim.getRegistNo());
		int serialNo = 0;
		String typeName = "";
		int index = 0;
		if (!CommonUtils.isEmpty(prpLcertifyDirectList)) {
			PrpLcertifyDirect prpLcertifyDirect = null;
			for (index = 0; index < prpLcertifyDirectList.size(); index++) {
				prpLcertifyDirect = prpLcertifyDirectList.get(index);
				acciPrintSubObject = new AcciPrintSubObject();
				typeName = prpLcertifyDirect.getTypeName();
				serialNo = index + 1;
				acciPrintSubObject.setTypeName(DataUtils.dbNullToEmpty(typeName));
				acciPrintSubObject.setSerialNo(serialNo);
				list.add(acciPrintSubObject);
			}
		}
		acciPrintObject.setNotificationList(list);
		// resultList.add(acciPrintObject);
		return acciPrintObject;
	}
	/***
	 * 理賠申請書
	 * @return
	 * @throws Exception
	 */
	public void printClaimApplication(Map<String, Object> param, String businessNo) throws Exception {
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(CommonUtils.nullToEmpty(businessNo).trim());
		PrpDrisk prpDrisk = this.prpDriskService.findPrpDrisk(prpLclaim.getRiskCode());
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", prpLclaim.getPolicyNo());
		queryRule.addEqual("insuredCode", prpLclaim.getInsuredCode());
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate , damageHour);
		String insuredCode = prpLclaim.getInsuredCode();
		String insuredName = prpLclaim.getInsuredName();
		List<PrpCinsured> prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName);
		PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, insuredCode, insuredName);
		if (prpLclaim != null) {
			param.put("CLAIMNO", prpLclaim.getClaimNo());
			param.put("POLICYNO", prpLclaim.getPolicyNo());
			param.put("INSUREDNAME", prpLclaim.getInsuredName());
		}
		if (prpDrisk != null) {
			param.put("RISKNAME", prpDrisk.getRiskCName());
		}
		if (prpCinsured != null) {
			param.put("IDENTIFYNUMBER", prpCinsured.getIdentifyNumber());
			int[] serialNos = {prpCinsured.getId().getSerialNo()};
			List<PrpCinsuredNature> prpCinsuredNatureList = this.endorseViewHelper.findPrpCinsuredNatureFromCopy(policyNo, damageDate, damageHour, serialNos);
			if (!CommonUtils.isEmpty(prpCinsuredNatureList)) {
				PrpCinsuredNature prpCinsuredNature = prpCinsuredNatureList.get(0);
				param.put("BIRTHDAY", PrintUtils.getYearToDayMGName(prpCinsuredNature.getBirthday()));
			}
		}
		if (prpCmain != null) {
			String strInsuredTerm = " 自 " + PrintUtils.getYearToDayMGName(prpCmain.getStartDate()) + " 至" + PrintUtils.getYearToDayMGName(prpCmain.getEndDate());
			param.put("INSUREDTERM", strInsuredTerm);
		}
	}
	/***
	 * 撤銷申請理賠同意書
	 * @return
	 * @throws Exception
	 */
	public void printRevocation(Map<String, Object> param, String businessNo) throws Exception {
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(CommonUtils.nullToEmpty(businessNo).trim());
		if (prpLclaim != null) {
			param.put("CLAIMNO", prpLclaim.getClaimNo());
			param.put("POLICYNO", prpLclaim.getPolicyNo());
			param.put("DAMAGESTARTDATE", PrintUtils.getYearToDayMGName(prpLclaim.getDamageStartDate()));
		} else {
			PrpLregist prplregist = prpLregistService.findPrpLregist(CommonUtils.nullToEmpty(businessNo).trim());
			if (prplregist != null) {
				List<PrpLclaim> prpLclaimList = prpLclaimService.findByRegistNo(prplregist.getRegistNo());
				if (prpLclaimList.size() > 0) {
					param.put("CLAIMNO", prpLclaimList.get(0).getClaimNo());
				}
				param.put("POLICYNO", prplregist.getPolicyNo());
				param.put("DAMAGESTARTDATE", PrintUtils.getYearToDayMGName(prplregist.getDamageStartDate()));
			}
		}
	}
	/***
	 * 調查報告
	 * @return
	 * @throws Exception
	 */
	public void printReport(Map<String, Object> param, String businessNo) throws Exception {
		PrpLregist prplregist = prpLregistService.findPrpLregist(CommonUtils.nullToEmpty(businessNo).trim());
		List<PrpLclaim> resultList = prpLclaimService.findByRegistNo(prplregist.getRegistNo());
		if (!CommonUtils.isEmpty(resultList)) {
			PrpLclaim prpLclaim = resultList.get(0);
			PrpDrisk prpDrisk = this.prpDriskService.findPrpDrisk(prpLclaim.getRiskCode());
			param.put("CLAIMNO", prpLclaim.getClaimNo());
			param.put("POLICYNO", prpLclaim.getPolicyNo());
			param.put("INSUREDNAME", prpLclaim.getInsuredName());
			param.put("SUMCLAIM", prpLclaim.getSumClaim());
			if (prpDrisk != null) {
				param.put("RISKNAME", prpDrisk.getRiskCName());
			}
		} else {
			PrpDrisk prpDrisk = this.prpDriskService.findPrpDrisk(prplregist.getRiskCode());
			param.put("CLAIMNO", "");
			param.put("POLICYNO", prplregist.getPolicyNo());
			param.put("INSUREDNAME", prplregist.getInsuredName());
			param.put("SUMCLAIM", 0D);
			if (prpDrisk != null) {
				param.put("RISKNAME", prpDrisk.getRiskCName());
			}
		}
	}
	/***
	 * 匯款同意書(賠款同意書、代位求償權承諾書)
	 * @return
	 * @throws Exception
	 */
	public AcciPrintObject printRemittance(Map<String, Object> param, String businessNo) throws Exception {
		AcciPrintObject acciPrintObject = new AcciPrintObject();
		PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(CommonUtils.nullToEmpty(businessNo).trim());
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.compensateNo", CommonUtils.nullToEmpty(businessNo).trim());
		queryRule.addEqual("id.certiType", "01");
		List<PrpLpayObjectInfo> prpLpayObjectInfoList = prpLpayObjectInfoService.findPrpLpayObjectInfo(queryRule);
		if (prpLcompensate != null) {
			param.put("CLAIMNO", prpLcompensate.getClaimNo());
			param.put("POLICYNO", prpLcompensate.getPolicyNo());
		}
		AcciPrintSubObject acciPrintSubObject = null;
		List<AcciPrintSubObject> list = new ArrayList<AcciPrintSubObject>();
		String ownerName = "";
		String uniformNo = "";
		String courierAddress = "";
		int index = 0;
		if (!CommonUtils.isEmpty(prpLpayObjectInfoList)) {
			for (index = 0; index < prpLpayObjectInfoList.size(); index++) {
				PrpLpayObjectInfo prpLpayObjectInfo = prpLpayObjectInfoList.get(index);
				acciPrintSubObject = new AcciPrintSubObject();
				ownerName = prpLpayObjectInfo.getOwnerName();
				uniformNo = prpLpayObjectInfo.getUniformNo();
				courierAddress = prpLpayObjectInfo.getCourierAddress();
				acciPrintSubObject.setOwnerName(ownerName);
				acciPrintSubObject.setUniformNo(uniformNo);
				acciPrintSubObject.setCourierAddress(courierAddress);
				list.add(acciPrintSubObject);
			}
		}
		acciPrintObject.setRemittanceList(list);
		return acciPrintObject;
	}

	/***
	 * 台壽保產物保險股份有限公司新種險理賠查案單
	 * @return
	 * @throws Exception
	 */
	public void printInvestigative(Map<String, Object> param, String businessNo) throws Exception {
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(CommonUtils.nullToEmpty(businessNo).trim());
		if (prpLclaim != null) {
			param.put("CLAIMNO", prpLclaim.getClaimNo());
			param.put("INSUREDNAME", prpLclaim.getInsuredName());
			param.put("DAMAGESTARTDATE", PrintUtils.getYearToDayMGStr(prpLclaim.getDamageStartDate()));
		}
	}

	/***
	 * 債權讓與契約暨通知書
	 * @return
	 * @throws Exception
	 */
	public void printContract(Map<String, Object> param, String businessNo) throws Exception {
		PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(CommonUtils.nullToEmpty(businessNo).trim());
		if (prpLcompensate != null) {
			PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
			PrpDrisk prpDrisk = this.prpDriskService.findPrpDrisk(prpLclaim.getRiskCode());
			String policyNo = prpLclaim.getPolicyNo();
			String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
			String damageHour = prpLclaim.getDamageStartHour();
			PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
			String insuredCode = prpLclaim.getInsuredCode();
			String insuredName = prpLclaim.getInsuredName();
			List<PrpCinsured> prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName);
			PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, insuredCode, insuredName);
			if (prpCinsured != null) {
				param.put("IDENTIFYNUMBER", prpCinsured.getIdentifyNumber());
			}
			if (prpLclaim != null) {
				param.put("POLICYNO", prpLclaim.getPolicyNo());
				param.put("INSUREDNAME", prpLclaim.getInsuredName());
				param.put("DAMAGESTARTDATE", PrintUtils.getYearToDayMGName(prpLclaim.getDamageStartDate()));
			}
			if (prpDrisk != null) {
				param.put("RISKNAME", prpDrisk.getRiskCName());
			}
			if (prpCmain != null) {
				param.put("ADDRESS", prpCmain.getInsuredAddress());
			}
		}
	}

	/***
	 * 保險金給付通知書
	 * @return
	 * @throws Exception
	 */
	public AcciPrintObject printPaymentNotice(Map<String, Object> param, String businessNo) throws Exception {
		DecimalFormat df = new DecimalFormat("#,###");
		AcciPrintObject acciPrintObject = new AcciPrintObject();
		CompensateDto compensateDto = compensateService.findByPrimaryKey(CommonUtils.nullToEmpty(businessNo).trim());
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.compensateNo", CommonUtils.nullToEmpty(businessNo).trim());
		queryRule.addEqual("id.certiType", "01");
		List<PrpLpayObjectInfo> prpLpayObjectInfoList = prpLpayObjectInfoService.findPrpLpayObjectInfo(queryRule);
		if (!CommonUtils.isEmpty(prpLpayObjectInfoList)) {
			PrpLpayObjectInfo prpLpayObjectInfo = prpLpayObjectInfoList.get(0);
			String ownerShip = prpLpayObjectInfo.getOwnerShip();
			if ("B".equals(ownerShip)) {
				param.put("OWNERSHIP", "匯款");
			} else if ("Q".equals(ownerShip)) {
				param.put("OWNERSHIP", "支票");
			} else if ("C".equals(ownerShip)) {
				param.put("OWNERSHIP", "現金");
			}
			param.put("INSUREDNAME", DataUtils.dbNullToEmpty(prpLpayObjectInfo.getOwnerName()));
			param.put("POSTCODE", DataUtils.dbNullToEmpty(prpLpayObjectInfo.getAreaCode()));
			param.put("POSTADDRESS", DataUtils.dbNullToEmpty(prpLpayObjectInfo.getCourierAddress()));

		}
		if (prpLcompensate != null) {
			if (prpLclaim != null) {
				param.put("CLAIMNO", prpLclaim.getClaimNo());
				param.put("POLICYNO", prpLclaim.getPolicyNo());
				param.put("DAMAGESTARTDATE", PrintUtils.getYearToDayMGStr(prpLclaim.getDamageStartDate()));
				PrpDuser prpDuser = prpDuserService.findPrpDuser(prpLclaim.getHandlerCode());
				if (prpDuser != null) {
					param.put("USERNAME", DataUtils.dbNullToEmpty(prpDuser.getUserName()));
					param.put("PHONE", DataUtils.dbNullToEmpty(prpDuser.getPhone()));
				}
			}
			param.put("ENDCASEDATE", PrintUtils.getYearToDayMGStr(prpLcompensate.getInputDate()));
			param.put("SUMDUTYPAID", df.format(prpLcompensate.getSumDutyPaid()));
			int compensateTime = Integer.parseInt(prpLcompensate.getCompensateNo().substring(prpLcompensate.getCompensateNo().length() - 2));
			param.put("COMPENSATETIME", compensateTime);
		}
		PrpLpersonLoss prpLpersonLoss = null;
		AcciPrintSubObject acciPrintSubObject = null;
		List<AcciPrintSubObject> list = new ArrayList<AcciPrintSubObject>();
		for (int i = 0; i < compensateDto.getPrpLpersonLossList().size(); i++) {
			acciPrintSubObject = new AcciPrintSubObject();
			prpLpersonLoss = compensateDto.getPrpLpersonLossList().get(i);
			String kindName = codeService.translateKindCode(prpLpersonLoss.getRiskCode(), prpLpersonLoss.getKindCode(), true);
			acciPrintSubObject.setKindCode(prpLpersonLoss.getKindCode());
			acciPrintSubObject.setKindName(kindName);
			acciPrintSubObject.setFractureDegree(DataUtils.dbNullToEmpty(prpLpersonLoss.getPaymentContent()));
			acciPrintSubObject.setSumRealPay(df.format(prpLpersonLoss.getSumRealPay() + prpLpersonLoss.getExceptDeductiblePay()));
			list.add(acciPrintSubObject);
		}
		acciPrintObject.setPaymentNoticeList(list);
		return acciPrintObject;
	}

	/***
	 * 台壽保產物保險公證公司委託申請單
	 * @return
	 * @throws Exception
	 */
	public void printCommissioned(Map<String, Object> param, String businessNo) throws Exception {
		PrpLclaim prpLclaim = null;
		prpLclaim = prpLclaimService.findPrpLclaim(CommonUtils.nullToEmpty(businessNo).trim());
		if (prpLclaim != null) {
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("registNo", prpLclaim.getRegistNo());
			List<PrpLacciCheck> prpLacciCheckList = prpLacciCheckService.findPrpLacciCheck(queryRule);
			if (!CommonUtils.isEmpty(prpLacciCheckList)) {
				PrpDcompany prpDcompany = prpDcompanyService.findPrpDcompany(prpLacciCheckList.get(0).getHandleDept());
				if (prpDcompany != null) {
					param.put("CHECKERCODE", prpDcompany.getComCName());
				}
			}
			PrpDuser prpDuser = prpDuserService.findPrpDuser(prpLclaim.getHandlerCode());
			param.put("CLAIMNO", prpLclaim.getClaimNo());
			param.put("DAMAGESTARTDATE", PrintUtils.getYearToDayMGName(prpLclaim.getDamageStartDate()));
			if (prpDuser != null) {
				param.put("HANDLERNAME", prpDuser.getUserName());
			}
		}
		param.put("NOWDATE", PrintUtils.getYearToDayMGName(new Date()));// 得到当前日期
	}

	/***
	 * 賠款同意書暨領款收據
	 * @return
	 * @throws Exception
	 */
	public AcciPrintObject printReceipt(Map<String, Object> param, String businessNo) throws Exception {
		AcciPrintObject acciPrintObject = new AcciPrintObject();
		PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(CommonUtils.nullToEmpty(businessNo).trim());
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.compensateNo", CommonUtils.nullToEmpty(businessNo).trim());
		queryRule.addEqual("id.certiType", "01");
		List<PrpLpayObjectInfo> prpLpayObjectInfoList = prpLpayObjectInfoService.findPrpLpayObjectInfo(queryRule);
		if (prpLcompensate != null) {
			param.put("CLAIMNO", prpLcompensate.getClaimNo());
			param.put("POLICYNO", prpLcompensate.getPolicyNo());
		}
		AcciPrintSubObject acciPrintSubObject = null;
		List<AcciPrintSubObject> list = new ArrayList<AcciPrintSubObject>();
		String ownerName = "";
		String uniformNo = "";
		String courierAddress = "";
		String ownerPhoneNo = "";
		int index = 0;
		if (!CommonUtils.isEmpty(prpLpayObjectInfoList)) {
			PrpLpayObjectInfo prpLpayObjectInfo = null;
			for (index = 0; index < prpLpayObjectInfoList.size(); index++) {
				prpLpayObjectInfo = prpLpayObjectInfoList.get(index);
				acciPrintSubObject = new AcciPrintSubObject();
				ownerName = prpLpayObjectInfo.getOwnerName();
				uniformNo = prpLpayObjectInfo.getUniformNo();
				courierAddress = prpLpayObjectInfo.getCourierAddress();
				ownerPhoneNo = prpLpayObjectInfo.getOwnerPhoneNo();
				acciPrintSubObject.setOwnerName(ownerName);
				acciPrintSubObject.setUniformNo(uniformNo);
				acciPrintSubObject.setCourierAddress(courierAddress);
				acciPrintSubObject.setOwnerPhoneNo(ownerPhoneNo);
				list.add(acciPrintSubObject);
			}
		}
		acciPrintObject.setReceiptList(list);
		return acciPrintObject;
	}
	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpLpayObjectInfoService getPrpLpayObjectInfoService() {
		return prpLpayObjectInfoService;
	}

	public void setPrpLpayObjectInfoService(PrpLpayObjectInfoService prpLpayObjectInfoService) {
		this.prpLpayObjectInfoService = prpLpayObjectInfoService;
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public PrpJPayRefRecHisService getPrpJPayRefRecHisService() {
		return prpJPayRefRecHisService;
	}

	public void setPrpJPayRefRecHisService(PrpJPayRefRecHisService prpJPayRefRecHisService) {
		this.prpJPayRefRecHisService = prpJPayRefRecHisService;
	}

	public PrpPheadService getPrpPheadService() {
		return prpPheadService;
	}

	public void setPrpPheadService(PrpPheadService prpPheadService) {
		this.prpPheadService = prpPheadService;
	}

	public PrpLctextService getPrpLctextService() {
		return prpLctextService;
	}

	public void setPrpLctextService(PrpLctextService prpLctextService) {
		this.prpLctextService = prpLctextService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrpDriskService getPrpDriskService() {
		return prpDriskService;
	}

	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	public PrpLacciCheckService getPrpLacciCheckService() {
		return prpLacciCheckService;
	}

	public void setPrpLacciCheckService(PrpLacciCheckService prpLacciCheckService) {
		this.prpLacciCheckService = prpLacciCheckService;
	}

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	public PrpLcertifyDirectService getPrpLcertifyDirectService() {
		return prpLcertifyDirectService;
	}

	public void setPrpLcertifyDirectService(PrpLcertifyDirectService prpLcertifyDirectService) {
		this.prpLcertifyDirectService = prpLcertifyDirectService;
	}

	public PrpCinsuredNatureService getPrpCinsuredNatureService() {
		return prpCinsuredNatureService;
	}

	public void setPrpCinsuredNatureService(PrpCinsuredNatureService prpCinsuredNatureService) {
		this.prpCinsuredNatureService = prpCinsuredNatureService;
	}

}
