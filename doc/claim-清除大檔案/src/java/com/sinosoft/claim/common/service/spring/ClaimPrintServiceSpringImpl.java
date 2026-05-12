package com.sinosoft.claim.common.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.sinosoft.claim.certainLoss.service.facade.CertainLossService;
import com.sinosoft.claim.certainLoss.vo.CertainLossDto;
import com.sinosoft.claim.check.service.facade.CheckService;
import com.sinosoft.claim.check.vo.CheckDto;
import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.ClaimPrintService;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.EndorseService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.common.util.PrintUtils;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.common.vo.CheckPrintDto;
import com.sinosoft.claim.common.vo.ClaimApplicationPrintDto;
import com.sinosoft.claim.common.vo.ClaimStatementPrintDto;
import com.sinosoft.claim.common.vo.CompensateReportDto;
import com.sinosoft.claim.common.vo.EndorseDto;
import com.sinosoft.claim.common.vo.PolicyDto;
import com.sinosoft.claim.common.vo.PrepayPrintDto;
import com.sinosoft.claim.common.vo.RegistPrintDto;
import com.sinosoft.claim.common.vo.SubReportPrintDto;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.compensate.service.facade.PrepayService;
import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.compensate.vo.PrepayDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schema.model.PrpCengage;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCitemCar;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpCplan;
import com.sinosoft.claim.schema.model.PrpDuser;
import com.sinosoft.claim.schema.model.PrpLbank;
import com.sinosoft.claim.schema.model.PrpLcarInsurance;
import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimLoss;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLcomponent;
import com.sinosoft.claim.schema.model.PrpLdriver;
import com.sinosoft.claim.schema.model.PrpLext;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLperson;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.PrpLprop;
import com.sinosoft.claim.schema.model.PrpLptext;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLregistText;
import com.sinosoft.claim.schema.model.PrpLrepairFee;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.PrpPhead;
import com.sinosoft.claim.schema.model.PrpPmain;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpCplanService;
import com.sinosoft.claim.schema.service.facade.PrpLbankService;
import com.sinosoft.claim.schema.service.facade.PrpLcarInsuranceService;
import com.sinosoft.claim.schema.service.facade.PrpLcheckService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimLossService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLdriverService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonLossService;
import com.sinosoft.claim.schema.service.facade.PrpLplanService;
import com.sinosoft.claim.schema.service.facade.PrpLrecaseService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.payment.common.util.DateUtil;
import com.sinosoft.reins.common.util.DataUtils;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.utility.SysConfig;
import com.sinosoft.utility.error.UserException;

public class ClaimPrintServiceSpringImpl extends GenericDaoHibernate<PrpLregist, String> implements ClaimPrintService {

	private ClaimService claimService;
	private PrpLclaimService prpLclaimService;
	private CompensateService compensateService;
//	private PrpCmainService prpCmainService;
	private PrpLdriverService prpLdriverService;
	private PrpLregistService prpLregistService;
	private PrpLcheckService prpLcheckService;
	private CodeService codeService;
	private RegistService registService;
	private PolicyService policyService;
	private CertainLossService certainLossService;
	private EndorseService endorseService;
	private PrpCitemKindService prpCitemKindService;
	private PrepayService prepayService;
	private EndorseViewHelper endorseViewHelper;
//	private PrpLrecaseService prpLrecaseService;
//	private PrpLplanService prpLplanService;
	private CheckService checkService;
	private PrpLclaimLossService prpLclaimLossService;
	private PrpLbankService prpLbankService;
	private PrpDuserService prpDuserService;
	private DecimalFormat decimalFormat = new DecimalFormat("#,##0");
	private PrpLpersonLossService prpLpersonLossService;
	private PrpLcarInsuranceService prpLcarInsuranceService;
	private PrpCplanService prpCplanService;
	
	//mantis：CLM0028 ，處理人員：BK007 蘇哲，需求單編號：CLM0028 追償處理賠付對象管控、追償計算書增加追償說明
	private WorkFlowService workFlowService;
	
	/**
	 * 查勘列印
	 * @param path 列印模板的路徑
	 * @param emptyHashMap Map中有列印模板的路徑，備案號碼
	 * @param registNo 備案號碼
	 * @return checkBytes 列印的字節
	 */
	public byte[] checkBytes(String path, Map<String, Object> emptyHashMap, String registNo) {
		String strRegistNo = "";// 报案号
		String strCompPolicyNo = "";// 交强险保单号
		String strCompClaimNo = "";// 交强险立案号
		String strInsuredName = "";// 被保险人
		String strPolicyNo = "";// 商业险保单号
		String strClaimNo = "";// 商业险立案号
		String strBrandName = "";// 厂牌型号
		String strEngineNo = "";// 发动机号
		String strRunDistance = "";// 车辆已行驶里程
		String strUseYear = "";// 已使用年限
		String strLicenseNo = "";// 号牌号码
		String strFrameNo = "";// 车架号（VIN）
		String strDriverName = "";// 驾驶员姓名
		String strSex = "";// 性别
		String strAge = "";// 年龄
		String strDriverLicenseNo = "";// 驾驶证号码
		String strCheckSite = "";// 查勘地点
		String strFirstSite = "";// 是否第一现场
		String strgetCheckDate = "";// 查勘日期
		int index = 0;
		try {
			RegistDto registDto = registService.findByPrimaryKey(registNo);
			PrpLregist prplregist = registDto.getPrpLregist();
			strRegistNo = registNo;
			strInsuredName = prplregist.getInsuredName();
			Prplregistrpolicy prpLRegistRPolicy = null;
			for (int i = 0; i < registDto.getPrpLRegistRPolicyList().size(); i++) {
				prpLRegistRPolicy = registDto.getPrpLRegistRPolicyList().get(i);
				if ("1".equals(prpLRegistRPolicy.getPolicyType())) {
					strPolicyNo = prpLRegistRPolicy.getId().getPolicyNo(); // 商业险保单号
					strClaimNo = prpLRegistRPolicy.getClaimNo(); // 商业险立案号
					// 保险期间
				} else {
					strCompPolicyNo = prpLRegistRPolicy.getId().getPolicyNo(); // 交强险保单号
					strCompClaimNo = prpLRegistRPolicy.getClaimNo(); // 交强险立案号
					// 保险期间
				}
			}
			PrpLthirdParty prpLthirdParty = registDto.getPrpLthirdPartyList().get(0);
			// 取得保险车辆信息
			strBrandName = prpLthirdParty.getBrandName();
			strEngineNo = prpLthirdParty.getEngineNo();
			strRunDistance = prpLthirdParty.getRunDistance() + "";
			strUseYear = prpLthirdParty.getUseYears() + "";
			strLicenseNo = prpLthirdParty.getLicenseNo();
			strFrameNo = prpLthirdParty.getFrameNo();
			// --------------车险驾驶员信息表PrpLdriver*****
			if (registDto.getPrpLdriverList() != null) {
				PrpLdriver prpLdriver = null;
				int intDriverCount = registDto.getPrpLdriverList().size();
				for (index = 0; index < intDriverCount; index++) {
					prpLdriver = registDto.getPrpLdriverList().get(index);
					String strLicenseNo1 = prpLdriver.getLicenseNo();
					if (index == 0) {
						prpLdriver = registDto.getPrpLdriverList().get(index);
					}
					if (strLicenseNo1.equals(strLicenseNo)) {
						strDriverName = prpLdriver.getDriverName();// 驾驶员姓名
						strSex = PrintUtils.getDriverSexName(prpLdriver.getDriverSex());
						if (prpLdriver.getDriverAge() != null) {
							strAge = prpLdriver.getDriverAge() + "";
						}
						strDriverLicenseNo = prpLdriver.getDrivingLicenseNo();// 驾驶证号码
					}
				}
				if (intDriverCount > 0) {
					strDriverName = prpLdriver.getDriverName();// 驾驶员姓名
					strSex = PrintUtils.getDriverSexName(prpLdriver.getDriverSex());
					if (prpLdriver.getDriverAge() != null) {
						strAge = prpLdriver.getDriverAge() + "";
					}
					strDriverLicenseNo = prpLdriver.getDrivingLicenseNo();// 驾驶证号码
				}
			}
			// 查勘信息
			CheckDto checkDto = checkService.findByPrimaryKey(strRegistNo);
			String strFirstSiteFlag = checkDto.getPrpLcheck().getFirstSiteFlag();
			if ("0".equals(strFirstSiteFlag))
				strFirstSite = "否";
			if ("1".equals(strFirstSiteFlag))
				strFirstSite = "是";
			PrpLcheck prpLcheck = checkDto.getPrpLcheck();
			if (checkDto.getPrpLcheck() != null) {
				strgetCheckDate = PrintUtils.getYearToDayMGName(prpLcheck.getCheckDate());
				strCheckSite = prpLcheck.getCheckSite();
			}
			List<CheckPrintDto> checkPrintList = new ArrayList<CheckPrintDto>();
			CheckPrintDto checkPrintDto = new CheckPrintDto();
			checkPrintDto.setStrRegistNo(strRegistNo);// 报案号
			checkPrintDto.setStrCompPolicyNo(strCompPolicyNo);// 交强险保单号
			checkPrintDto.setStrCompClaimNo(strCompClaimNo);// 交强险立案号
			checkPrintDto.setStrInsuredName(strInsuredName);// 被保险人
			checkPrintDto.setStrPolicyNo(strPolicyNo);// 商业险保单号
			checkPrintDto.setStrClaimNo(strClaimNo);// 商业险立案号
			checkPrintDto.setStrBrandName(strBrandName);// 厂牌型号
			checkPrintDto.setStrEngineNo(strEngineNo);// 发动机号
			checkPrintDto.setStrRunDistance(strRunDistance);// 车辆已行驶里程
			checkPrintDto.setStrUseYear(strUseYear);// 已使用年限
			checkPrintDto.setStrLicenseNo(strLicenseNo);// 号牌号码l
			checkPrintDto.setStrFrameNo(strFrameNo);// 车架号（VIN）
			checkPrintDto.setStrDriverName(strDriverName);// 驾驶员姓名
			checkPrintDto.setStrSex(strSex);// 性别
			checkPrintDto.setStrAge(strAge);// 年龄
			checkPrintDto.setStrDriverLicenseNo(strDriverLicenseNo);// 驾驶证号码
			checkPrintDto.setStrFirstSite(strFirstSite);
			checkPrintDto.setStrCheckSite(strCheckSite);
			checkPrintDto.setStrgetCheckDate(strgetCheckDate);
			checkPrintList.add(checkPrintDto);
		    return JasperRunManager.runReportToPdf(path + "check.jasper", emptyHashMap, new JRBeanCollectionDataSource(checkPrintList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 失竊車輛
	 * @param path 列印模板的路徑
	 * @param emptyHashMap Map中有列印模板的路徑
	 * @return checkBytes 列印的字節
	 */
	public byte[] lossCarPrint(String path, Map<String, Object> emptyHashMap) {
		try {
			
			
			return JasperRunManager.runReportToPdf(path + "lossCarDetail.jasper", emptyHashMap, new JREmptyDataSource());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 理賠申請
	 * @param path 列印模板的路徑
	 * @param emptyHashMap Map中有列印模板的路徑
	 * @return checkBytes 列印的字節
	 */
	public byte[] carClaim(String path, Map<String, Object> emptyHashMap) {
		try {
			return JasperRunManager.runReportToPdf(path + "carClaim.jasper", emptyHashMap, new JREmptyDataSource());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 強制險現金給付審核表
	 * @param path 列印模板的路徑
	 * @param emptyHashMap Map中有列印模板的路徑
	 * @param prepayNo 預賠號碼
	 * @return checkBytes 列印的字節
	 */
	public byte[] bzPay(String path, Map<String, Object> emptyHashMap, String prepayNo) {
		try {
			// 变量声明部分
			String strInsuredDate = ""; // 保险期间
			String strDamageStartDate = ""; // 出险时间
			String strInsureCarFlag = ""; // 是否为本保单车辆
			String strCSumPrePaid = ""; // 大写预赔金额
			PrpLclaim prpLclaim = null; // ClaimDto对象
			PrpLthirdParty prpLthirdParty = null; // ThirdPartyDto对象
			// 得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象
			PrepayDto prepayDto = prepayService.findByPrimaryKey(prepayNo);
			ClaimDto claimDto = claimService.findByPrimaryKey(prepayDto.getPrpLclaim().getClaimNo());
			RegistDto registDto = registService.findByPrimaryKey(claimDto.getPrpLclaim().getRegistNo());

			// 得到prpLclaimDto对象
			prpLclaim = claimDto.getPrpLclaim();
			double sumAmount = prpLclaim.getSumAmount();
			String strClaimNo = prpLclaim.getClaimNo();
			// 得到blPrpLthirdParty对象的记录数
			PrpLprepay prpLprepay = prepayDto.getPrpLprepay();

			// 增加交强险预付处理类型
			String strEstimateLoss = decimalFormat.format(prpLclaim.getSumClaim());
			String strRiskName = codeService.translateRiskCode(prpLclaim.getRiskCode(), true);
			String strLicenseNo = "";
			String strBrandName = "";

			// 预付、垫付原因
			String strPrepayReason = "";
			List<PrpLptext> prpLptextList = prepayDto.getPrpLptextList();
			if (prpLptextList != null && prpLptextList.size() > 0) {
				for (int i = 0; i < prpLptextList.size(); i++) {
					strPrepayReason += (prpLptextList.get(i)).getContext();
					if (strPrepayReason.length() > 300) {
						strPrepayReason += "<br>&nbsp;&nbsp;&nbsp;&nbsp;";
					}
				}
			}
			// 预付、垫付原因
			String strRegistReason = "";
			List<PrpLregistText> prpLregistTextList = registDto.getPrpLregistTextList();
			if (prpLregistTextList != null && prpLregistTextList.size() > 0) {
				for (int i = 0; i < prpLregistTextList.size(); i++) {
					strRegistReason += (prpLregistTextList.get(i)).getContext();
					if (strPrepayReason.length() > 300) {
						strRegistReason += "<br>&nbsp;&nbsp;&nbsp;&nbsp;";
					}
				}
			}
			String strInsuredName = prpLclaim.getInsuredName();
			String strPolicyNo = prpLclaim.getPolicyNo();
			strInsuredDate = "自 " + PrintUtils.getYearToDayMGName(prpLclaim.getStartDate()) + " 零時起" + "至 " + PrintUtils.getYearToDayMGName(prpLclaim.getEndDate()) + " 二十四時止";
			strDamageStartDate = PrintUtils.getYearToDayMGName(prpLclaim.getDamageStartDate());
			String strDamageAddress = StringConvert.encode(prpLclaim.getDamageAddress());
			// *****理赔车辆信息PrpLthirdParty*****
			if (registDto.getPrpLthirdPartyList() != null) {
				// for (index = 0; index < intThirdPartyCount; index++) {
				prpLthirdParty = registDto.getPrpLthirdPartyList().get(0);
				strInsureCarFlag = prpLthirdParty.getInsureCarFlag();
				if (strInsureCarFlag.equals("1")) {
					strLicenseNo = prpLthirdParty.getLicenseNo();
					strBrandName = prpLthirdParty.getBrandName();
				}
				// }
			}

			// *****预赔登记表PrpLprepay*****
			strCSumPrePaid = PrintUtils.toChinese(prpLprepay.getSumPrePaid(), prpLprepay.getCurrency());
			strCSumPrePaid = "預付賠款金額（大寫新台幣）：" + strCSumPrePaid;

			List<PrepayPrintDto> prepayPrintList = new ArrayList<PrepayPrintDto>();
			PrepayPrintDto prepayPrintDto = new PrepayPrintDto();
			prepayPrintDto.setStrClaimNo(strClaimNo);
			prepayPrintDto.setStrBrandName(strBrandName);
			prepayPrintDto.setStrCSumPrePaid(strCSumPrePaid);
			prepayPrintDto.setStrDamageAddress(strDamageAddress);
			prepayPrintDto.setStrDamageStartDate(strDamageStartDate);
			prepayPrintDto.setStrEstimateLoss(strEstimateLoss);
			prepayPrintDto.setStrInsuredDate(strInsuredDate);
			prepayPrintDto.setStrInsuredName(strInsuredName);
			prepayPrintDto.setStrLicenseNo(strLicenseNo);
			prepayPrintDto.setStrPolicyNo(strPolicyNo);
			prepayPrintDto.setStrPrepayReason(strPrepayReason);
			prepayPrintDto.setStrRegistReason(strRegistReason);
			prepayPrintDto.setStrRiskName(strRiskName);
			prepayPrintDto.setStrSumAmount(decimalFormat.format(sumAmount));
			prepayPrintList.add(prepayPrintDto);
			return JasperRunManager.runReportToPdf(path + "bzPay.jasper", emptyHashMap, new JRBeanCollectionDataSource(prepayPrintList));
		} catch (UserException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 汽車險賠案查證記錄表
	 * @param path 列印模板的路徑
	 * @param emptyHashMap Map中有列印模板的路徑，立案號碼
	 * @return checkBytes 列印的字節
	 * @throws Exception 
	 */
	public byte[] carCase(String path, Map<String, Object> emptyHashMap) throws Exception {
		DBManager dbManager = new DBManager();
		byte[] checkBytes = null;
		try {
			dbManager.open(SysConfig.getProperty("DDCCDATASOURCE"));
			Connection conn = dbManager.getConnection();
			checkBytes = JasperRunManager.runReportToPdf(path + "carCase.jasper", emptyHashMap, conn);
		} catch (UserException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dbManager.close();
		}
		return checkBytes;
	}

	/**
	 * 汽車險理賠計算書
	 * @param path 列印模板的路徑
	 * @param emptyHashMap Map中有列印模板的路徑加上公司logo地址，列印模板的路徑，計算書號
	 * @param compensateNo 計算書號
	 * @return checkBytes 列印的字節
	 */
	public byte[] claimStatement(String path, Map<String, Object> emptyHashMap, String compensateNo) {
		try {
			String strClaimNo = "";// 赔案号码
			String strPolicyno = "";// 保单号码
			String strDriverName = "";// 肇事驾驶人
			String strSex = "";// 驾驶员性别
			String strBirthday = "";// 驾驶员生日
			String strMarriage = "";// 驾驶员婚姻
			String strDrivingLicenseNo = "";// 驾照号码
			String strRelationship = "";// 与被保险人关系
			String strBrandName = "";// 厂牌车型
			String strFrameNo = "";// 引擎/车身/号码
			String strCarKindName = "";// 车辆种类
			String strLicenseNo = "";// 牌照号码
			String strExhaustScale = "";// 排气量
			String strDamageStartDate = "";// 出险日期
			String strDamageReason = "";// 出险原因
			String damageAreaName = "";// 出险地址
			String strInsuredName = "";// 被保险人
			String strInsuredTerm = "";// 保险期间
			String strSeatCount = "";// 载客限制
			String strMakeDate = "";// 制造年份
			String strReplevytimes = "";// 賠付追償次數
			String strIsReplevy = "";// 是否追偿
			String strCloseCaseDate = "";// 结案日期
			String strClaimDate = "";// 受理时间
			String damageAreaCode = "";// 地区代号
			String damageCode = "";// 出险代号
			String strLossType = "";// 本车损失 1全损\2分损
			String indemnityDutyRate = "";// 保车肇责
			String strOppositeIndemnityDuty = "";// 对造车肇责
			String strOtherIndemnityDuty = "";// 其他肇责
			String palyName = "";// 收费情形 :-1为未缴费，0为未缴全，1为缴全
			String strComName = "";

			String strBillingUnit = "";// 开票单位
			String strEnrollDate = "";// 原始发照年月
			String strSubjectNo = "0001";// 标的编号(车险只有一个标的)
			String strPassengerRestrictions = "";// 载客限制
			String strPayDate = "";// 收费日期
			String strNotesMaturityDate = "";// 票据到期日
			String strMoney = "0";// 金额
			String strCasualties = "";// 人员伤亡
			String strManipulateCode = "";// 对造身份代号
			String strRepairerKindCode = "";// 对造车种代号98-行人，99-逃逸

			String strInsureComName = "";// 对造车承保公司
			String strThirdLicenseNo = "";// 对造车牌号码
			String strInsuranceNo = "";// 对造车保险正号
			
			String handleCode = "";//總公司經辦人code
			String handleName = "";//總公司經辦人name
			String handleCode1 = "";//分公司經辦人code
			String handleName1 = "";//分公司經辦人name
			/** 自負額發票號 */
			String strDeductibleInvoice = "";
			/** 車體險肇責 */
			String strAccidentType = "";
			/** 責任險肇責 */
			String strPropAccidentType = "";
			CompensateDto compensateDto = compensateService.findByPrimaryKey(compensateNo);
			RegistDto registDto = registService.findByPrimaryKey(compensateDto.getPrpLclaim().getRegistNo());
			PrpLregist prplregist = registDto.getPrpLregist();
			PrpLclaim prpLclaim = compensateDto.getPrpLclaim();
			String policyNo = prpLclaim.getPolicyNo();
			String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
			String damageHour = prpLclaim.getDamageStartHour();
			//PolicyDto policyDto = endorseViewHelper.findForEndorBefore(prpLclaim.getPolicyNo(),new DateTime(prpLclaim.getDamageStartDate()).toString(),prpLclaim.getDamageStartHour());
			PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
			PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
			List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
			strComName = codeService.translateComCode(prpCmain.getComCode(), true);
			strMoney = decimalFormat.format(prpCmain.getSumPremium());
			strCloseCaseDate = PrintUtils.getYearToDayMGStr(prpLcompensate.getInputDate());
			Calendar date = Calendar.getInstance();
		    date.setTime(prpCmain.getEndDate());
		    date.add(Calendar.DATE, -93);
		    //mantis： CLM0092 ，處理人員：BK007 蘇哲，需求單編號：CLM0092.新核心-閉鎖期提醒 - start
		    //是否在閉鎖期內理算
			if(isEffectiveDate(prpLcompensate.getInputDate(),date.getTime(),prpCmain.getEndDate())){
				strCloseCaseDate += " (！)";
			}
		    //mantis： CLM0092 ，處理人員：BK007 蘇哲，需求單編號：CLM0092.新核心-閉鎖期提醒 - end
			if (compensateDto != null) {
				// mantis：CLM0243，處理人員：DP0714，新核心-車險列印計算書report欄位調整 -- start
				if (org.apache.commons.lang.StringUtils.isNotBlank(compensateDto.getPrpLclaim().getReceiptDate())) {
					strClaimDate = compensateDto.getPrpLclaim().getReceiptDate().substring(0, 10);
				}
				// mantis：CLM0243，處理人員：DP0714，新核心-車險列印計算書report欄位調整 -- end
			}
			strReplevytimes = compensateNo.substring(compensateNo.length()-2);//取計算書的后兩位即為當前計算書的賠付，追償次數
			if ("0".equals(prpLclaim.getReplevyFlag())) {
				strIsReplevy = "否";
			} else {
				strIsReplevy = "是";
			}
			strClaimNo = compensateDto.getPrpLclaim().getClaimNo();
			strPolicyno = compensateDto.getPrpLclaim().getPolicyNo();
			if ("1".equals(compensateDto.getPrpLcompensate().getLossType())) {
				strLossType = "全損";// 本车损失 1全损\2分损
			} else if ("2".equals(compensateDto.getPrpLcompensate().getLossType())) {
				strLossType = "分損";
			}
			indemnityDutyRate = compensateDto.getPrpLcompensate().getIndemnityDutyRate() + "%";// 保车肇责
			strOppositeIndemnityDuty = compensateDto.getPrpLcompensate().getOppositeIndemnityDuty() + "%";// 对造车肇责
			strOtherIndemnityDuty = compensateDto.getPrpLcompensate().getOtherIndemnityDuty() + "%";// 其他肇责
			// 收费情形 -1为未缴费，0为未缴全，1为缴全  判断保费是否已经实收
			int intReturn = this.getPolicyService().checkPay(" policyno = '" + prpLcompensate.getPolicyNo() + "'");// -1为未缴费，0为未缴全，1为缴全
			if (intReturn == -1) {
				palyName = "未繳費";
			} else if (intReturn == 0) {
				palyName = "未繳全";
			} else if (intReturn == 1) {
				palyName = "繳全";
			}
			strDamageStartDate = PrintUtils.getYearToDayMGStr(prplregist.getDamageStartDate());
			damageCode = prpLclaim.getDamageCode();
			strDamageReason = prpLclaim.getDamageName();
//			if (ConstantCodes.RISKCODE_DAZ.equals(prplregist.getRiskCode())) {
//				strDamageReason = prplregist.getDamageNameBZ();
//				damageCode = prplregist.getDamageCodeBZ();
//			} else {
//				strDamageReason = prplregist.getDamageName();
//				damageCode = prplregist.getDamageCode();
//			}
			List<?> tempResult = HibernateUtils.findbySql(getSession(), "select centerCode from prplplan where certino='" + compensateNo + "'");
			if (!CommonUtils.isEmpty(tempResult)) {// 开票单位
				strBillingUnit = tempResult.get(0).toString();
			}
			String querySql = "select payRefDate from prpJPayRefRecHis where policyno = '" + prpLclaim.getPolicyNo() + "' and realpayrefflag = '1' and certitype='P'";
			tempResult = HibernateUtils.findbySql(getSession(), querySql);
			if (!CommonUtils.isEmpty(tempResult)) {// 收费日期
				strPayDate = PrintUtils.getYearToDayMGStr(new Date(((Timestamp) tempResult.get(0)).getTime()));
			}
			querySql = "select billEndDate From prpjfeebillsub Where businessno In (select businessNo From prpjpayinfo Where certino='" + prpLclaim.getPolicyNo() + "')";
			tempResult = HibernateUtils.findbySql(getSession(), querySql);
			if (!CommonUtils.isEmpty(tempResult)) {// 票据到期日
				strNotesMaturityDate = PrintUtils.getYearToDayMGStr(CommonUtils.toYearToDayDate(tempResult.get(0).toString()));
			}
			damageAreaCode = prpLclaim.getDamageAreaCode();
			damageAreaName = prpLclaim.getDamageAreaName();
			strInsuredTerm = PrintUtils.getYearToDayMGStr(prpCmain.getStartDate()) + " " + prpCmain.getStartHour() + " 至" + PrintUtils.getYearToDayMGStr(prpCmain.getEndDate()) + " " + prpCmain.getEndHour();
			// mantis：CLM0171，處理人員：DP0714，新核心-車險計算書新增理賠已出險次數 -- start
			QueryRule queryRule2 = QueryRule.getInstance();
			queryRule2.addEqual("policyNo", prpCmain.getPolicyNo());
			List<PrpLclaim> prpLclaimList = prpLclaimService.findPrpLclaim(queryRule2);
			strInsuredTerm += (" （次數：" + prpLclaimList.size() + "）");
			// mantis：CLM0171，處理人員：DP0714，新核心-車險計算書新增理賠已出險次數 -- end
			strInsuredName = prplregist.getInsuredName();
			if (registDto.getPrpLthirdPartyList() != null) {
				int intThirdPartyCount = registDto.getPrpLthirdPartyList().size();
				PrpLthirdParty prpLthirdParty = null;
				List<PrpCitemCar> prpCitemCarList = this.endorseViewHelper.findPrpCitemCar(policyNo, damageDate, damageHour);
				PrpCitemCar prpCitemCar = prpCitemCarList.get(0);
				for (int index = 0; index < intThirdPartyCount; index++) {
					prpLthirdParty = registDto.getPrpLthirdPartyList().get(index);
					String strInsureCarFlag = prpLthirdParty.getInsureCarFlag();
					if (strInsureCarFlag.equals("1")) {// 标的车
						strRelationship = ConstantsCollection.thirdPartyRelationshipList.get(prpLthirdParty.getRelationship());
						strLicenseNo = StringConvert.encode(prpLthirdParty.getLicenseNo());
						strBrandName = prpLthirdParty.getBrandName();
						strFrameNo = prpLthirdParty.getFrameNo();
						if (CommonUtils.isEmpty(strFrameNo)) {
							strFrameNo = prpLthirdParty.getEngineNo();
						}
						strExhaustScale = prpCitemCar.getExhaustScale() + "";
						strCarKindName = codeService.translateCodeCode("CarKind", prpLthirdParty.getCarKindCode(), true);
						strMakeDate = new SimpleDateFormat("yyyy年MM月").format(prpCitemCar.getMakeDate());//製造年份：西元年月 yyyy年MM月
						strEnrollDate = CommonUtils.getMGDateStr(prpCitemCar.getEnrollDate(), new SimpleDateFormat("yyyy年MM月"));
						if (prpCitemCar.getSeatCount() > 0) {
							strPassengerRestrictions = prpCitemCar.getSeatCount() + "人";
						} else {
							strPassengerRestrictions = prpCitemCar.getTonCount() + "噸";
						}
					} else if (prpLthirdParty.getInsureCarFlag().equals("0")) {// 三者车
						strInsureComName = prpLthirdParty.getInsureComName();// 对造车承保公司
						strThirdLicenseNo = prpLthirdParty.getLicenseNo();// 对造车牌号码
						strInsuranceNo = prpLthirdParty.getInsuranceNo();// 对造车保险正号
						if (CommonUtils.isEmpty(strRepairerKindCode)) {
							// 三者车且三者车辆信息未赋值
							strRepairerKindCode = prpLthirdParty.getCarKindCode();
							strManipulateCode = prpLthirdParty.getInsuredIdentity();
							if (prpLthirdParty.getCarryingNumber() != null) {
								strSeatCount = prpLthirdParty.getCarryingNumber() + (prpLthirdParty.getCarryingUnit()==null?"":prpLthirdParty.getCarryingUnit());
							}
						}
					}
					if (!CommonUtils.isEmpty(strLicenseNo) && !CommonUtils.isEmpty(strRepairerKindCode)) {// 赋值完毕
						break;
					}
				}
			}
			// --------------车险驾驶员信息表PrpLdriver*****
			if (registDto.getPrpLdriverList() != null) {
				int intDriverCount = registDto.getPrpLdriverList().size();
				PrpLdriver prpLdriver = null;
				for (int index = 0; index < intDriverCount; index++) {
					prpLdriver = registDto.getPrpLdriverList().get(index);
					String strLicenseNo1 = prpLdriver.getLicenseNo();
					strLicenseNo1 = strLicenseNo1==null ? "" : strLicenseNo1;
					if (index == 0) {
						prpLdriver = registDto.getPrpLdriverList().get(index);
					}
					if (strLicenseNo1.equals(strLicenseNo)) {
						strDriverName = prpLdriver.getDriverName();// 驾驶员姓名
						strSex = PrintUtils.getDriverSexName(prpLdriver.getDriverSex());
						// 驾驶员生日
						strBirthday = PrintUtils.getYearToDayMGStr(prpLdriver.getBirthday());
						strMarriage = PrintUtils.getDriverIsMarried(prpLdriver.getIsMarried());
						strDrivingLicenseNo = prpLdriver.getDrivingLicenseNo();// 驾驶证号码
						break;
					}
				}
				if (intDriverCount > 0 && CommonUtils.isEmpty(strDriverName)) {// 驾驶员信息为赋值时候
					strDriverName = prpLdriver.getDriverName();// 驾驶员姓名
					strSex = PrintUtils.getDriverSexName(prpLdriver.getDriverSex());
					// 驾驶员生日
					strBirthday = PrintUtils.getYearToDayMGStr(prpLdriver.getBirthday());
					strMarriage = PrintUtils.getDriverIsMarried(prpLdriver.getIsMarried());// 驾驶员婚姻
					strDrivingLicenseNo = prpLdriver.getDrivingLicenseNo();// 驾驶证号码
				}
			}
			// ----------------计算赔款总计
			double dblSumPaid = 0;
			String strSumPaid = "";
			String strCSumPaidOut = "";// 赔款总计

			dblSumPaid = compensateDto.getPrpLcompensate().getSumPaid();
			strSumPaid = decimalFormat.format(dblSumPaid);
			strCSumPaidOut = strSumPaid + "元";
			// 计算理算
			List<SubReportPrintDto> compensateInfoList = new ArrayList<SubReportPrintDto>();
			List<SubReportPrintDto> victimsInfoList = new ArrayList<SubReportPrintDto>();
			SubReportPrintDto kindInfoPrintDto = null;// 各个险别赔付信息
			SubReportPrintDto personPrintDto = null;// 受害人子报表
			SubReportPrintDto payObjPrintDto = null;// 赔付对象子报表
			Map<String, SubReportPrintDto> infoMap = new LinkedHashMap<String, SubReportPrintDto>();
			//展示所有的险种
//			List<String> referenceKinds = getReferenceKinds(compensateNo);
			if (prpCitemKindList!=null && !prpCitemKindList.isEmpty()) {
				PrpCitemKind prpCitemKind = null;
				for (int index = 0; index < prpCitemKindList.size(); index++) {
					prpCitemKind = prpCitemKindList.get(index);
					generateKindInfoPrintDto(infoMap, prpCitemKind);
//					if (referenceKinds.contains(prpCitemKind.getKindCode())) {// 仅展现本次理算涉及的险种
//					}
				}
			}
			double sumNoDutyFee = 0d;
			double sumDutyPaid = 0d;
			double sumDeductible = 0d;
			PrpLloss prpLloss = null;
			for (int i = 0; i < compensateDto.getPrpLlossList().size(); i++) {// 车财损失汇总
				prpLloss = compensateDto.getPrpLlossList().get(i);
				kindInfoPrintDto = infoMap.get(prpLloss.getKindCode());
				sumDutyPaid = prpLloss.getExceptDeductiblePay() + prpLloss.getSumRealPay() + kindInfoPrintDto.getSumDutyPaid();
				sumDeductible = prpLloss.getDeductible() + kindInfoPrintDto.getSumDeductible();

				kindInfoPrintDto.setSumDutyPaid(sumDutyPaid);
				kindInfoPrintDto.setSumDeductible(sumDeductible);
				kindInfoPrintDto.setStrActualClaimAmount(decimalFormat.format(sumDutyPaid));
				kindInfoPrintDto.setStrDeductible(decimalFormat.format(sumDeductible));
			}

			Map<String, SubReportPrintDto> personMap = new HashMap<String, SubReportPrintDto>();// 受害人统计
			Map<String,Set<Integer>> temp = new HashMap<String,Set<Integer>>();
			PrpLpersonLoss prpLpersonLoss = null;
			Set<Integer> set = null;
			for (int i = 0; i < compensateDto.getPrpLpersonLossList().size(); i++) {// 人伤损失汇总
				prpLpersonLoss = compensateDto.getPrpLpersonLossList().get(i);
				if(temp.containsKey(prpLpersonLoss.getKindCode())){
					set = temp.get(prpLpersonLoss.getKindCode());
				}else{
					set = new HashSet<Integer>();
				}
				set.add(prpLpersonLoss.getPersonNo());
				temp.put(prpLpersonLoss.getKindCode(), set);
				if (!"21".equals(prpLpersonLoss.getKindCode())) {
					kindInfoPrintDto = infoMap.get(prpLpersonLoss.getKindCode());
				}
				if (ConstantCodes.RISKCODE_DAZ.equals(prpLpersonLoss.getRiskCode())) {// 强制险计算需要打印受害人信息
					String liabDetailCode = prpLpersonLoss.getLiabDetailCode();
					double tempPaid = prpLpersonLoss.getSumRealPay() + prpLpersonLoss.getExceptDeductiblePay();
					kindInfoPrintDto = infoMap.get(prpLpersonLoss.getKindCode() + liabDetailCode.substring(0, 1));
					if (personMap.containsKey(prpLpersonLoss.getPersonNo() + "")) {
						personPrintDto = personMap.get(prpLpersonLoss.getPersonNo() + "");
					} else {
						personPrintDto = new SubReportPrintDto();
						personPrintDto.setStrID(prpLpersonLoss.getIdentifyNumber());
						personPrintDto.setStrPersonName(prpLpersonLoss.getPersonName());
						personPrintDto.setStrBirthday(PrintUtils.getYearToDayMGStr(prpLpersonLoss.getBirthday()));
						personMap.put(prpLpersonLoss.getPersonNo() + "", personPrintDto);
						List<String> casualtiesList = Arrays.asList(prpLpersonLoss.getCasualties().split(","));
						SubReportPrintDto tempKindInfoPrintDto = null;// 各个险别赔付信息
						if (casualtiesList.contains("1")) {// 医疗
							tempKindInfoPrintDto = infoMap.get(prpLpersonLoss.getKindCode() + "A");
							tempKindInfoPrintDto.setStrClaimNumber(Integer.parseInt(tempKindInfoPrintDto.getStrClaimNumber()) + 1 + "");
						}
						if (casualtiesList.contains("2")) {// 残废
							tempKindInfoPrintDto = infoMap.get(prpLpersonLoss.getKindCode() + "C");
							tempKindInfoPrintDto.setStrClaimNumber(Integer.parseInt(tempKindInfoPrintDto.getStrClaimNumber()) + 1 + "");
						}
						if (casualtiesList.contains("3")) {// 死亡
							tempKindInfoPrintDto = infoMap.get(prpLpersonLoss.getKindCode() + "B");
							tempKindInfoPrintDto.setStrClaimNumber(Integer.parseInt(tempKindInfoPrintDto.getStrClaimNumber()) + 1 + "");
						}
					}
					if (liabDetailCode.startsWith("A")) {// 医疗
						personPrintDto.setStrMedicalPaid(personPrintDto.getStrMedicalPaid() + tempPaid);
					} else if (liabDetailCode.startsWith("C")) {// 残废給付
						personPrintDto.setStrDisPaid(personPrintDto.getStrDisPaid() + tempPaid);
					} else if (liabDetailCode.startsWith("B")) {// 死亡給付
						personPrintDto.setStrDiePaid(personPrintDto.getStrDiePaid() + tempPaid);
					}
					personPrintDto.setStrPerPersonPay(personPrintDto.getStrMedicalPaid() + personPrintDto.getStrDisPaid() + personPrintDto.getStrDiePaid());
					personPrintDto.setHealthAmount(prpLpersonLoss.getHealthAmount());
					personPrintDto.setHealthPoints(prpLpersonLoss.getHealthPoints());
				} else {
					if (personMap.containsKey(prpLpersonLoss.getPersonNo() + "")) {
					} else {
						personMap.put(prpLpersonLoss.getPersonNo() + "", personPrintDto);
					}
				}
				sumDutyPaid = prpLpersonLoss.getExceptDeductiblePay() + prpLpersonLoss.getSumRealPay() + kindInfoPrintDto.getSumDutyPaid();
				sumDeductible = prpLpersonLoss.getDeductible() + kindInfoPrintDto.getSumDeductible();

				kindInfoPrintDto.setSumDutyPaid(sumDutyPaid);
				kindInfoPrintDto.setSumDeductible(sumDeductible);
				kindInfoPrintDto.setStrActualClaimAmount(decimalFormat.format(sumDutyPaid));
				kindInfoPrintDto.setStrDeductible(decimalFormat.format(sumDeductible));
			}
			PrpLcharge prpLcharge = null;
			for (int i = 0; i < compensateDto.getPrpLchargeList().size(); i++) {// 费用信息汇总
				prpLcharge = compensateDto.getPrpLchargeList().get(i);
				if ("21".equals(prpLcharge.getKindCode())) {
					kindInfoPrintDto = infoMap.get(prpLcharge.getKindCode() + "A");// 强制险因拆分为多条全部放在医疗
				} else {
					kindInfoPrintDto = infoMap.get(prpLcharge.getKindCode());
				}
				sumNoDutyFee = prpLcharge.getExceptDeductiblePay() + prpLcharge.getChargeAmount() + kindInfoPrintDto.getSumNoDutyFee();

				kindInfoPrintDto.setSumNoDutyFee(sumNoDutyFee);
				kindInfoPrintDto.setStrClaimAmount(decimalFormat.format(sumNoDutyFee));
			}
			List<PrpLclaimLoss> lossList = this.getPrpLclaimLossService().findPrpLclaimLoss(prpLclaim.getClaimNo());
			String feeCategory = "";
			if(lossList!=null && !lossList.isEmpty()){//险别估损金额
				for(PrpLclaimLoss prpLclaimLoss : lossList){
					//强制险估损金额按范围拆分
					kindInfoPrintDto = infoMap.get(prpLclaimLoss.getKindCode());
					if(ConstantCodes.RISKCODE_DAZ.equals(prpLclaimLoss.getRiskCode())){
						feeCategory = prpLclaimLoss.getFeeCategory();
						if("M".equals(feeCategory)){//医疗
							kindInfoPrintDto = infoMap.get(prpLclaimLoss.getKindCode()+"A");
						}else if("H".equals(feeCategory)){//失能
							kindInfoPrintDto = infoMap.get(prpLclaimLoss.getKindCode()+"C");
						}else if("D".equals(feeCategory)){//
							kindInfoPrintDto = infoMap.get(prpLclaimLoss.getKindCode()+"B");
						}
					}
					if(kindInfoPrintDto!=null){
						String lossAmount = kindInfoPrintDto.getStrEstimatedLossAmount();
						if(DataUtils.emptyToNull(lossAmount)==null){
							lossAmount = "0";
						}
						kindInfoPrintDto.setStrEstimatedLossAmount(decimalFormat.format(prpLclaimLoss.getKindLoss() + decimalFormat.parse(lossAmount).doubleValue()));
					}
				}
			}
			if(!temp.isEmpty()){//设置险别理赔人数
				for(Entry<String,Set<Integer>> entry : temp.entrySet()){
					kindInfoPrintDto = infoMap.get(entry.getKey());
					if(kindInfoPrintDto!=null){
						kindInfoPrintDto.setStrClaimNumber(String.valueOf(entry.getValue().size()));
					}
				}
			}
			strCasualties = personMap.size() + "";// 人员伤亡
			//强制险，若无人伤、费用讯息，只有独立处理费用，则可初始化一条，并设置独立处理费用
			if(ConstantCodes.RISKCODE_DAZ.equals(prpLcompensate.getRiskCode())
					&& infoMap.isEmpty() && prpLcompensate.getIndependentCosts().doubleValue()!=0){
				List<PrpCitemKind> bzList = this.prpCitemKindService.findByConditions(" policyNo = '"+prpLcompensate.getPolicyNo()+"' and riskCode ='"+prpLcompensate.getRiskCode()+"'");
			    if(bzList!=null && !bzList.isEmpty()){
			    	this.generateKindInfoPrintDto(infoMap, bzList.get(0));
			    }
			}
			compensateInfoList.addAll(infoMap.values());
			if (ConstantCodes.RISKCODE_DAZ.equals(prpLcompensate.getRiskCode())) {
				victimsInfoList.addAll(personMap.values());
				SubReportPrintDto subReportPrintDto = null;
				if(!compensateInfoList.isEmpty()){//设置独立处理费用 加到医疗的费用里面
					subReportPrintDto = compensateInfoList.get(0);
					double d = Double.valueOf(subReportPrintDto.getStrClaimAmount().replaceAll(",", "")) + prpLcompensate.getIndependentCosts();
					subReportPrintDto.setStrClaimAmount(decimalFormat.format(d));
				}
			}
			//經辦人信息查詢
			//PrpLregist prpLregist = registDto.getPrpLregist();
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
			List<SubReportPrintDto> sumFeeInfoList = new ArrayList<SubReportPrintDto>();
			for (PrpLpayObjectInfo prpLpayObjectInfo : compensateDto.getPrpLpayObjectInfoList()) {
				payObjPrintDto = new SubReportPrintDto();
				PrpLbank prpLbank = new PrpLbank();
				if(!CommonUtils.isEmpty(prpLpayObjectInfo.getCustomBankCode())){
					List<PrpLbank > prpLbankList = this.prpLbankService.findPrpLbank(QueryRule.getInstance().addEqual("id.bankCode", prpLpayObjectInfo.getCustomBankCode()));
					if(!CommonUtils.isEmpty(prpLbankList)){
						prpLbank = prpLbankList.get(0);
					}
				}
				payObjPrintDto.setStrUniformNo(prpLpayObjectInfo.getUniformNo());
				payObjPrintDto.setStrName(prpLpayObjectInfo.getOwnerName());
				payObjPrintDto.setStrAreaCode(strBillingUnit);
				payObjPrintDto.setSumRealPay(prpLpayObjectInfo.getPayAmount());
				payObjPrintDto.setAccountCode(prpLpayObjectInfo.getAccountCode());// 账号
				payObjPrintDto.setBankCode(prpLpayObjectInfo.getBankCode());// 总行代码
				payObjPrintDto.setCustomBankName(prpLbank.getBankShortName());// 银行名称，取银行简称
				payObjPrintDto.setCustomBankCode(prpLpayObjectInfo.getCustomBankCode().replaceFirst(prpLpayObjectInfo.getBankCode(), ""));// 分行代码
				payObjPrintDto.setStrAmlFlag(prpLpayObjectInfo.getAmlFlag()) ;
				payObjPrintDto.setStrAmlDate(new SimpleDateFormat("yyyyMMdd").format(prpLpayObjectInfo.getAmlDate())) ;
				sumFeeInfoList.add(payObjPrintDto);
			}
			String accidentType = CommonUtils.nullToEmpty(prpLcompensate.getAccidentType());
			if ("1".equals(accidentType)) {
				accidentType = "有肇責，計次";
			} else if ("2".equals(accidentType)) {
				accidentType = "無肇責，不計次";
			} else if ("3".equals(accidentType)) {
				accidentType = "有肇責，不計次";
			}
			String propAccidentType = CommonUtils.nullToEmpty(prpLcompensate.getPropAccidentType());
			if ("1".equals(propAccidentType)) {
				propAccidentType = "有肇責，計次";
			} else if ("2".equals(propAccidentType)) {
				propAccidentType = "無肇責，不計次";
			} else if ("3".equals(propAccidentType)) {
				propAccidentType = "有肇責，不計次";
			}
			if (ConstantCodes.RISKCODE_DAZ.equals(prpLcompensate.getRiskCode())) {
				strPropAccidentType = accidentType;
			} else {
				strAccidentType = accidentType;
				strPropAccidentType = propAccidentType;
			}
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.compensateNo", prpLcompensate.getCompensateNo());
			queryRule.addAscOrder("id.serialNo");
			List<PrpLcarInsurance> list = this.prpLcarInsuranceService.findPrpLcarInsurance(queryRule);
			if(!CommonUtils.isEmpty(list)){
				strDeductibleInvoice = CommonUtils.nullToEmpty(list.get(0).getDeductibleInvoice());
			}
			List<ClaimStatementPrintDto> claimStatementPrintList = new ArrayList<ClaimStatementPrintDto>();
			ClaimStatementPrintDto claimStatementPrintDto = new ClaimStatementPrintDto();
			claimStatementPrintDto.setStrClaimNo(strClaimNo);// 赔案号码
			claimStatementPrintDto.setStrPolicyno(strPolicyno);// 保单号码
			claimStatementPrintDto.setStrDriverName(strDriverName);// 肇事驾驶人
			claimStatementPrintDto.setStrSex(strSex);// 驾驶员性别
			claimStatementPrintDto.setStrBirthday(strBirthday);// 驾驶员生日
			claimStatementPrintDto.setStrMarriage(strMarriage);// 驾驶员婚姻
			claimStatementPrintDto.setStrDrivingLicenseNo(strDrivingLicenseNo);// 驾照号码
			claimStatementPrintDto.setStrRelationship(strRelationship);// 与被保险人关系
			claimStatementPrintDto.setStrBrandName(strBrandName);// 厂牌车型
			claimStatementPrintDto.setStrFrameNo(strFrameNo);// 引擎/车身/号码
			claimStatementPrintDto.setStrCarKindName(strCarKindName);// 车辆种类
			claimStatementPrintDto.setStrLicenseNo(strLicenseNo);// 牌照号码
			claimStatementPrintDto.setStrExhaustScale(strExhaustScale);// 排气量
			claimStatementPrintDto.setStrDamageStartDate(strDamageStartDate);// 出险日期
			claimStatementPrintDto.setStrDamageAddress(damageAreaName);// 出险地址
			claimStatementPrintDto.setStrDamageReason(strDamageReason);// 出险原因
			claimStatementPrintDto.setStrInsuredName(strInsuredName);// 被保险人
			claimStatementPrintDto.setStrInsuredTerm(strInsuredTerm);// 保险期间
			claimStatementPrintDto.setStrSeatCount(strSeatCount);
			claimStatementPrintDto.setStrMakeDate(strMakeDate);
			claimStatementPrintDto.setStrIsReplevy(strIsReplevy);
			claimStatementPrintDto.setStrReplevytimes(strReplevytimes);
			claimStatementPrintDto.setStrCloseCaseDate(strCloseCaseDate);
			claimStatementPrintDto.setStrClaimDate(strClaimDate);
			claimStatementPrintDto.setStrComName(strComName);
			claimStatementPrintDto.setDamageCode(damageCode);
			claimStatementPrintDto.setDamageAddressType(damageAreaCode);
			claimStatementPrintDto.setStrLlossType(strLossType);
			claimStatementPrintDto.setIndemnityDutyRate(indemnityDutyRate);
			claimStatementPrintDto.setStrOppositeIndemnityDuty(strOppositeIndemnityDuty);
			claimStatementPrintDto.setStrOtherIndemnityDuty(strOtherIndemnityDuty);
			claimStatementPrintDto.setPalyName(palyName);
			claimStatementPrintDto.setStrBillingUnit(strBillingUnit);
			claimStatementPrintDto.setStrUseDriverLicense(strEnrollDate);
			claimStatementPrintDto.setStrSubjectNo(strSubjectNo);
			claimStatementPrintDto.setStrPassengerRestrictions(strPassengerRestrictions);
			claimStatementPrintDto.setStrPayDate(strPayDate);
			claimStatementPrintDto.setStrNotesMaturityDate(strNotesMaturityDate);
			claimStatementPrintDto.setStrMoney(strMoney);
			claimStatementPrintDto.setStrCasualties(strCasualties);
			claimStatementPrintDto.setStrManipulateCode(strManipulateCode);
			claimStatementPrintDto.setStrRepairerKindCode(strRepairerKindCode);
			claimStatementPrintDto.setStrCSumPaidOut(strCSumPaidOut);
			claimStatementPrintDto.setCompensateInfoList(compensateInfoList);
			claimStatementPrintDto.setVictimsInfoList(victimsInfoList);
			claimStatementPrintDto.setSumFeeInfoList(sumFeeInfoList);
			claimStatementPrintDto.setCompelFlag(1);// 判断是否显示人员信息
			claimStatementPrintDto.setStrInsureComName(DataUtils.dbNullToEmpty(strInsureComName));
			claimStatementPrintDto.setStrThirdLicenseNo(strThirdLicenseNo);
			claimStatementPrintDto.setStrInsuranceNo(strInsuranceNo);
			claimStatementPrintDto.setHandleCode(handleCode);
			claimStatementPrintDto.setHandleName(handleName);
			claimStatementPrintDto.setHandleCode1(handleCode1);
			claimStatementPrintDto.setHandleName1(handleName1);
			claimStatementPrintDto.setStrAccidentType(strAccidentType);
			claimStatementPrintDto.setStrPropAccidentType(strPropAccidentType);
			claimStatementPrintDto.setStrDeductibleInvoice(strDeductibleInvoice);
			//mantis： CLM0091 ，處理人員：BK007 蘇哲，需求單編號：CLM0091.新核心-理算文件齊全日
			claimStatementPrintDto.setFileReadyDate(PrintUtils.getYearToDayMGStr(new DateTime(compensateDto.getPrpLcompensate().getFileReadyDate(),DateTime.YEAR_TO_DAY)));
			/* mantis： CLM0044 ，處理人員：BK007 蘇哲，需求單編號：CLM0044理賠計算書將開票單位異動成服務人員 --start */
			String handler1Code2 = prplregist.getHandler1Code();
			String handler1Name = this.getCodeService().translateUserCode(handler1Code2, true);
			claimStatementPrintDto.setHandler1Name(handler1Name);
			/* mantis： CLM0044 ，處理人員：BK007 蘇哲，需求單編號：CLM0044理賠計算書將開票單位異動成服務人員 --end */
			String strBusinessNature = codeService.translateCodeCode("BusinessNature", prpCmain.getBusinessNature(), true);
			claimStatementPrintDto.setStrBusinessNature(strBusinessNature);
			claimStatementPrintList.add(claimStatementPrintDto);
			emptyHashMap.put("SUBREPORT_DIR", path);
			return JasperRunManager.runReportToPdf(path + "ClaimStatement.jasper", emptyHashMap, new JRBeanCollectionDataSource(claimStatementPrintList));
		} catch (UserException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//mantis： CLM0092 ，處理人員：BK007 蘇哲，需求單編號：CLM0092.新核心-閉鎖期提醒
	private boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
	    if (nowTime.getTime() == startTime.getTime()
	            || nowTime.getTime() == endTime.getTime()) {
	        return true;
	    }

	    Calendar date = Calendar.getInstance();
	    date.setTime(nowTime);

	    Calendar begin = Calendar.getInstance();
	    begin.setTime(startTime);

	    Calendar end = Calendar.getInstance();
	    end.setTime(endTime);

	    if (date.after(begin) && date.before(end)) {
	        return true;
	    } else {
	        return false;
	    }
	}
	/**
	 * 汽車險追償計算書計算書
	 * @param path 列印模板的路徑
	 * @param emptyHashMap Map中有列印模板的路徑加上公司logo地址，列印模板的路徑，計算書號
	 * @param compensateNo 計算書號
	 * @return checkBytes 列印的字節
	 * @throws Exception 
	 */
	public byte[] claimStatementReplevy(String path, Map<String, Object> emptyHashMap, String compensateNo) throws Exception{
		List<ClaimStatementPrintDto> claimStatementPrintList = new ArrayList<ClaimStatementPrintDto>();
		CompensateDto compensateDto = compensateService.findByPrimaryKey(compensateNo);
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		PrpLclaim prpLclaim = compensateDto.getPrpLclaim();
		RegistDto registDto = registService.findByPrimaryKey(prpLclaim.getRegistNo());
		PrpLregist prpLregist = registDto.getPrpLregist();
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);//
		List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
		List<PrpCitemCar> prpCitemCarList = this.endorseViewHelper.findPrpCitemCar(policyNo, damageDate, damageHour);
		PrpCitemCar prpCitemCar = prpCitemCarList.get(0);
		//第一張理算書
		PrpLcompensate compe = this.compensateService.findByConditions(" compensateNo like 'C"+prpLclaim.getClaimNo()+"%' order by compensateNo asc ").get(0);
		try {
			ClaimStatementPrintDto claimStatementPrintDto = new ClaimStatementPrintDto();
			String strReplevytimes = compensateNo.substring(compensateNo.length()-2);//取計算書的后兩位即為當前計算書的賠付，追償次數
			// mantis：CLM0243，處理人員：DP0714，新核心-車險列印計算書report欄位調整 -- start
			String strClaimDate = "";//受理時間
			if (org.apache.commons.lang.StringUtils.isNotBlank(compensateDto.getPrpLclaim().getReceiptDate())) {
				strClaimDate = compensateDto.getPrpLclaim().getReceiptDate().substring(0, 10);
			}
			// mantis：CLM0243，處理人員：DP0714，新核心-車險列印計算書report欄位調整 -- end
			String strBillingUnit = "";//開票單位
			String strIsReplevy = "";//追償
			String strCloseCaseDate = PrintUtils.getYearToDayMGStr(prpLcompensate.getInputDate());//結案日期
			String strComName = codeService.translateComCode(prpCmain.getComCode(), true);//出單單位
			String strInsuredTerm = PrintUtils.getYearToDayMGStr(prpCmain.getStartDate()) + " " + prpCmain.getStartHour() + " 至" + PrintUtils.getYearToDayMGStr(prpCmain.getEndDate()) + " " + prpCmain.getEndHour();
			String strDriverName = "";// 肇事駕駛人
			String strRelationship = "";//本車駕駛人與被保險人關係
			String strDrivingLicenseNo = "";//肇事駕駛人  駕照號碼
			String strBirthday = "";//肇事駕駛人  生日
			String strSex = "";//肇事駕駛人  性別
			String strMarriage = "";//肇事駕駛人  婚姻別
			String strDamageStartDate = PrintUtils.getYearToDayMGStr(prpLregist.getDamageStartDate());//出險日期
			String damageCode = "";//出險原因代號
			String strDamageReason = "";//出險原因
			String damageAreaName = prpLclaim.getDamageAreaName();//出險地區
			String damageAreaCode = prpLclaim.getDamageAreaCode();//地區代號
			String strLicenseNo = "";// 牌照號碼
			String strEnrollDate = CommonUtils.getMGDateStr(prpCitemCar.getEnrollDate(), new SimpleDateFormat("yyyy年MM月"));//原始發照年月
			String strSubjectNo = "0001";// 標的編號（車險唯一）
			String strMakeDate = new SimpleDateFormat("yyyy年MM月").format(prpCitemCar.getMakeDate());//製造年份：西元年月 yyyy年MM月
			String strBrandName = "";//廠牌車型
			String strFrameNo = "";// 引擎/車身號碼
			String strExhaustScale = "";//排氣量
			String strCarKindName = "";//車輛種類
			String strPassengerRestrictions = "";// 載客/載重 限制
			String palyName = "";//收費情形
			String strPayDate = "";//收費日期
			String strNotesMaturityDate = "";// 票據到期日
			String strMoney = decimalFormat.format(prpCmain.getSumPremium());//總保費 金額
			String strLossType = "";//本車損失
			String strManipulateCode = "";//對造車身份代號
			String strRepairerKindCode = "";//對造車車種代號
			String strSeatCount = "";//對造車 承載數量
			String indemnityDutyRate = compe.getIndemnityDutyRate() + "%";// 保車肇責
			String strOppositeIndemnityDuty = compe.getOppositeIndemnityDuty() + "%";// 對造車肇責
			String strOtherIndemnityDuty = compe.getOtherIndemnityDuty() + "%";// 其他肇責
			String strInsureComName = "";// 對造車承保公司
			String strThirdLicenseNo = "";// 對造車車牌號碼
			String strInsuranceNo = "";// 對造車強制保險證編號
			
			String handleCode = "";//總公司 經辦人代號
			String handleName = "";//總公司 經辦人名稱
			String handleCode1 = "";//分公司 經辦人代號
			String handleName1 = "";//分公司 經辦人名稱	
			String strCSumPaidOut = decimalFormat.format(prpLcompensate.getSumPaid())+ "元";// 賠付總和
			List<?> tempResult = HibernateUtils.findbySql(getSession(), "select centerCode from prplplan where certino='" + compensateNo + "'");
			if (!CommonUtils.isEmpty(tempResult)) {// 開票單位
				strBillingUnit = tempResult.get(0).toString();
			}
			strIsReplevy = "0".equals(prpLclaim.getReplevyFlag())?"否":"是";
			if (prpCitemCar.getSeatCount() > 0) {
				strPassengerRestrictions = prpCitemCar.getSeatCount() + "人";
			} else {
				strPassengerRestrictions = prpCitemCar.getTonCount() + "噸";
			}
			damageCode = prpLclaim.getDamageCode();
			strDamageReason = prpLclaim.getDamageName();
//			if (ConstantCodes.RISKCODE_DAZ.equals(prpLregist.getRiskCode())) {
//				strDamageReason = prpLregist.getDamageNameBZ();
//				damageCode = prpLregist.getDamageCodeBZ();
//			} else {
//				strDamageReason = prpLregist.getDamageName();
//				damageCode = prpLregist.getDamageCode();
//			}
			//涉案車中找標的車
			boolean insureCarFlag = false;
			boolean thirdCarFlag = false;
			for(PrpLthirdParty prpLthirdParty : registDto.getPrpLthirdPartyList()){
				if(insureCarFlag && thirdCarFlag){
					break;
				}
				if(!insureCarFlag && "1".equals(prpLthirdParty.getInsureCarFlag())){//標的車
					insureCarFlag = true;
					strRelationship = ConstantsCollection.thirdPartyRelationshipList.get(prpLthirdParty.getRelationship());
					strBrandName = prpLthirdParty.getBrandName();
					strFrameNo = prpLthirdParty.getFrameNo();
					if (CommonUtils.isEmpty(strFrameNo)) {
						strFrameNo = prpLthirdParty.getEngineNo();
					}
					strExhaustScale = prpCitemCar.getExhaustScale() + "";
					strCarKindName = codeService.translateCodeCode("CarKind", prpLthirdParty.getCarKindCode(), true);
					strLicenseNo = prpLthirdParty.getLicenseNo();
					for(PrpLdriver prpLdriver : registDto.getPrpLdriverList()){//查找標的車駕駛員
						if(prpLthirdParty.getLicenseNo().equals(prpLdriver.getLicenseNo())){
							strDriverName = prpLdriver.getDriverName();// 駕駛員姓名
							strSex = PrintUtils.getDriverSexName(prpLdriver.getDriverSex());// 駕駛員 性別
							strBirthday = PrintUtils.getYearToDayMGStr(prpLdriver.getBirthday());//駕駛員 生日
							strMarriage = PrintUtils.getDriverIsMarried(prpLdriver.getIsMarried());//駕駛員 婚姻別
							strDrivingLicenseNo = prpLdriver.getDrivingLicenseNo();//駕駛員 駕駛證號碼
							break;
						}
					}
				}
				if (!thirdCarFlag && "0".equals(prpLthirdParty.getInsureCarFlag())) {//對造車
					strManipulateCode = prpLthirdParty.getInsuredIdentity();//對造車身份
					strRepairerKindCode = prpLthirdParty.getCarKindCode();//對造車車種代號
					if (prpLthirdParty.getCarryingNumber() != null) {//對造車承載數量
						strSeatCount = prpLthirdParty.getCarryingNumber() + (prpLthirdParty.getCarryingUnit()==null?"":prpLthirdParty.getCarryingUnit());
					}
					strInsureComName = prpLthirdParty.getInsureComName();//對造車承保公司
					strThirdLicenseNo = prpLthirdParty.getLicenseNo();//對造車車牌號碼
					strInsuranceNo = prpLthirdParty.getInsuranceNo();//對造車強制保險證編號
					if(!CommonUtils.isEmpty(strRepairerKindCode)){
						thirdCarFlag = true;
					}
				}
			}
			if ("1".equals(compe.getLossType())) {
				strLossType = "全損";// 本车损失 1全损\2分损
			} else if ("2".equals(compe.getLossType())) {
				strLossType = "分損";
			}
			// 收费情形 -1为未缴费，0为未缴全，1为缴全  判断保费是否已经实收
			int intReturn = this.getPolicyService().checkPay(" policyno = '" + prpLcompensate.getPolicyNo() + "'");// -1为未缴费，0为未缴全，1为缴全
			if (intReturn == -1) {
				palyName = "未繳費";
			} else if (intReturn == 0) {
				palyName = "未繳全";
			} else if (intReturn == 1) {
				palyName = "繳全";
			}

			String querySql = "select payRefDate from prpJPayRefRecHis where policyno = '" + prpLclaim.getPolicyNo() + "' and realpayrefflag = '1' and certitype='P'";
			tempResult = HibernateUtils.findbySql(getSession(), querySql);
			if (!CommonUtils.isEmpty(tempResult)) {// 收费日期
				strPayDate = PrintUtils.getYearToDayMGStr(new Date(((Timestamp) tempResult.get(0)).getTime()));
			}
			querySql = "select billEndDate From prpjfeebillsub Where businessno In (select businessNo From prpjpayinfo Where certino='" + prpLclaim.getPolicyNo() + "')";
			tempResult = HibernateUtils.findbySql(getSession(), querySql);
			if (!CommonUtils.isEmpty(tempResult)) {// 票据到期日
				strNotesMaturityDate = PrintUtils.getYearToDayMGStr(CommonUtils.toYearToDayDate(tempResult.get(0).toString()));
			}
			PolicyDto policyDto = new PolicyDto();
			policyDto.setPrpCitemKindList(prpCitemKindList);
			List<SubReportPrintDto> compensateInfoList = this.getCompeKindInfo(policyDto,prpLclaim,compensateDto,claimStatementPrintDto);
			//經辦人訊息
			//mantis：CLM0028 ，處理人員：BK007 蘇哲，需求單編號：CLM0028 追償處理賠付對象管控、追償計算書增加追償說明  開始
			Page handlerSwfLogList = workFlowService.findViewSwfLogAll("BUSINESSNO = '" + StringUtils.rightTrim(compensateNo) + "' AND NODETYPE = 'Broker' ORDER BY LOGNO DESC ", 1, 1);
			if(!handlerSwfLogList.getResult().isEmpty()){
				SwfLog swfLog = (SwfLog)handlerSwfLogList.getResult().get(0);
				if("00".equals(swfLog.getHandleDept())){
					handleCode = swfLog.getHandlerCode();
					handleName = swfLog.getHandlerName();
				}else {
					handleCode1 = swfLog.getHandlerCode();
					handleName1 = swfLog.getHandlerName();
				}
			}
			//mantis：CLM0028 ，處理人員：BK007 蘇哲，需求單編號：CLM0028 追償處理賠付對象管控、追償計算書增加追償說明  結束
			//付款對象訊息
			List<SubReportPrintDto> sumFeeInfoList = new ArrayList<SubReportPrintDto>();
			SubReportPrintDto payObjPrintDto = null;//付款對象
			for (PrpLpayObjectInfo prpLpayObjectInfo : compensateDto.getPrpLpayObjectInfoList()) {
				payObjPrintDto = new SubReportPrintDto();
				PrpLbank prpLbank = new PrpLbank();
				if(!CommonUtils.isEmpty(prpLpayObjectInfo.getCustomBankCode())){
					List<PrpLbank > prpLbankList = this.prpLbankService.findPrpLbank(QueryRule.getInstance().addEqual("id.bankCode", prpLpayObjectInfo.getCustomBankCode()));
					if(!CommonUtils.isEmpty(prpLbankList)){
						prpLbank = prpLbankList.get(0);
					}
				}
				payObjPrintDto.setStrUniformNo(prpLpayObjectInfo.getUniformNo());
				payObjPrintDto.setStrName(prpLpayObjectInfo.getOwnerName());
				payObjPrintDto.setStrAreaCode(strBillingUnit);
				if(PrpLpayObjectInfo.CERTITYPE_PAYOBJECT.equals(prpLpayObjectInfo.getId().getCertiType())){
					payObjPrintDto.setSumRealPay(-prpLpayObjectInfo.getPayAmount());//追償收取 負值
				}else{
					payObjPrintDto.setSumRealPay(prpLpayObjectInfo.getPayAmount());	
				}
				payObjPrintDto.setAccountCode(prpLpayObjectInfo.getAccountCode());// 账号
				payObjPrintDto.setBankCode(prpLpayObjectInfo.getBankCode());// 总行代码
				payObjPrintDto.setCustomBankName(prpLbank.getBankShortName());// 银行名称，取银行简称
				payObjPrintDto.setCustomBankCode(prpLpayObjectInfo.getCustomBankCode().replaceFirst(prpLpayObjectInfo.getBankCode(), ""));// 分行代码
				sumFeeInfoList.add(payObjPrintDto);
			}
			claimStatementPrintDto.setStrClaimNo(prpLcompensate.getClaimNo());// 賠案號碼
			claimStatementPrintDto.setStrPolicyno(prpLcompensate.getPolicyNo());// 保單號碼
			claimStatementPrintDto.setStrDriverName(strDriverName);// 肇事駕駛人
			claimStatementPrintDto.setStrSex(strSex);// 性別
			claimStatementPrintDto.setStrBirthday(strBirthday);// 出生日期
			claimStatementPrintDto.setStrMarriage(strMarriage);// 婚姻
			claimStatementPrintDto.setStrDrivingLicenseNo(strDrivingLicenseNo);// 證件號碼
			claimStatementPrintDto.setStrRelationship(strRelationship);// 與被保險人關係
			claimStatementPrintDto.setStrBrandName(strBrandName);// 厂牌车型
			claimStatementPrintDto.setStrFrameNo(strFrameNo);// 引擎/车身/号码
			claimStatementPrintDto.setStrCarKindName(strCarKindName);// 车辆种类
			claimStatementPrintDto.setStrLicenseNo(strLicenseNo);// 牌照号码
			claimStatementPrintDto.setStrExhaustScale(strExhaustScale);// 排气量
			claimStatementPrintDto.setStrDamageStartDate(strDamageStartDate);// 出险日期
			claimStatementPrintDto.setStrDamageAddress(damageAreaName);// 出险地址
			claimStatementPrintDto.setDamageCode(damageCode);// 出險原因代號
			claimStatementPrintDto.setStrDamageReason(strDamageReason);// 出险原因
			claimStatementPrintDto.setStrInsuredName(prpLregist.getInsuredName());// 被保险人
			claimStatementPrintDto.setStrInsuredTerm(strInsuredTerm);// 保险期间
			claimStatementPrintDto.setStrSeatCount(strSeatCount);
			claimStatementPrintDto.setStrMakeDate(strMakeDate);
			claimStatementPrintDto.setStrIsReplevy(strIsReplevy);
			claimStatementPrintDto.setStrReplevytimes(strReplevytimes);
			claimStatementPrintDto.setStrCloseCaseDate(strCloseCaseDate);
			claimStatementPrintDto.setStrClaimDate(strClaimDate);
			claimStatementPrintDto.setStrComName(strComName);
			claimStatementPrintDto.setDamageAddressType(damageAreaCode);
			claimStatementPrintDto.setStrLlossType(strLossType);
			claimStatementPrintDto.setIndemnityDutyRate(indemnityDutyRate);
			claimStatementPrintDto.setStrOppositeIndemnityDuty(strOppositeIndemnityDuty);
			claimStatementPrintDto.setStrOtherIndemnityDuty(strOtherIndemnityDuty);
			claimStatementPrintDto.setPalyName(palyName);
			claimStatementPrintDto.setStrBillingUnit(strBillingUnit);
			claimStatementPrintDto.setStrUseDriverLicense(strEnrollDate);
			claimStatementPrintDto.setStrSubjectNo(strSubjectNo);
			claimStatementPrintDto.setStrPassengerRestrictions(strPassengerRestrictions);
			claimStatementPrintDto.setStrPayDate(strPayDate);
			claimStatementPrintDto.setStrNotesMaturityDate(strNotesMaturityDate);
			claimStatementPrintDto.setStrMoney(strMoney);
			claimStatementPrintDto.setStrManipulateCode(strManipulateCode);
			claimStatementPrintDto.setStrRepairerKindCode(strRepairerKindCode);
			claimStatementPrintDto.setStrCSumPaidOut(strCSumPaidOut);
			claimStatementPrintDto.setCompensateInfoList(compensateInfoList);
			claimStatementPrintDto.setVictimsInfoList(new ArrayList<SubReportPrintDto>());//追償計算書不顯示受害人
			claimStatementPrintDto.setSumFeeInfoList(sumFeeInfoList);
			claimStatementPrintDto.setCompelFlag(1);// 判断是否显示人员信息
			claimStatementPrintDto.setStrInsureComName(DataUtils.dbNullToEmpty(strInsureComName));
			claimStatementPrintDto.setStrThirdLicenseNo(DataUtils.dbNullToEmpty(strThirdLicenseNo));
			claimStatementPrintDto.setStrInsuranceNo(DataUtils.dbNullToEmpty(strInsuranceNo));
			claimStatementPrintDto.setHandleCode(handleCode);
			claimStatementPrintDto.setHandleName(handleName);
			claimStatementPrintDto.setHandleCode1(handleCode1);
			claimStatementPrintDto.setHandleName1(handleName1);
			//mantis：CLM0028 ，處理人員：BK007 蘇哲，需求單編號：CLM0028 追償處理賠付對象管控、追償計算書增加追償說明 
			claimStatementPrintDto.setContext(getContextByCompensateNo(compensateNo));

			claimStatementPrintList.add(claimStatementPrintDto);
			emptyHashMap.put("SUBREPORT_DIR", path);
			return JasperRunManager.runReportToPdf(path + "ClaimStatementReplevy.jasper", emptyHashMap, new JRBeanCollectionDataSource(claimStatementPrintList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * mantis：CLM0028 ，處理人員：BK007 蘇哲，需求單編號：CLM0028 追償處理賠付對象管控、追償計算書增加追償說明
	 * 取出計算書備註
	 * @param compensateNo 計算書號
	 * @return 計算書備註
	 */
	private String getContextByCompensateNo(String compensateNo) {
		try{
			String querySql = " SELECT NVL(listagg(CONTEXT,'') WITHIN GROUP(ORDER BY LINENO),'') FROM prpLctext Where compensateNo = '" + StringUtils.rightTrim(compensateNo) + "'";
			List<?> tempResult = HibernateUtils.findbySql(getSession(), querySql);
			String context = "";
			if (!CommonUtils.isEmpty(tempResult) && tempResult.get(0) != null) {// 收费日期
				context = tempResult.get(0).toString();
			}
			return context;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	/***
	 * 獲取險別的追償賠付訊息，
	 * @param policyDto
	 * @param prpLclaim
	 * @param compensateDto 
	 * @param claimStatementPrintDto 
	 * @throws Exception 
	 */
	public List<SubReportPrintDto> getCompeKindInfo(PolicyDto policyDto, PrpLclaim prpLclaim, CompensateDto compensateDto, ClaimStatementPrintDto claimStatementPrintDto) throws Exception{
		//保單承保險種
		Map<String,PrpCitemKind> kindMap = new LinkedHashMap<String,PrpCitemKind>();
		for(PrpCitemKind temp : policyDto.getPrpCitemKindList()){
			kindMap.put(temp.getKindCode(), temp);
		}
		//險別的賠付訊息
		List<PrpLloss> prpLlossList = this.compensateService.getPrpLlossForReplevy(prpLclaim.getClaimNo());
		//本案涉及險種
		Map<String,PrpLloss> currKindMap = new HashMap<String,PrpLloss>();
		for(PrpLloss temp : prpLlossList){
			currKindMap.put(temp.getKindCode(), temp);
		}
		Map<String,SubReportPrintDto> infoMap = new LinkedHashMap<String,SubReportPrintDto>();
		if(!currKindMap.isEmpty()){//需要初始化各險別的訊息設置
			for(String kindCode : currKindMap.keySet()){
				if(kindMap.containsKey(kindCode)){// 仅展现本次理算涉及的险种
					generateKindInfoPrintDto(infoMap, kindMap.get(kindCode));
				}
			}
		}
		String tempKey = null;
		Map<String,Double> chargeInfoMap = new HashMap<String,Double>();
		//統計追償費用
		for(PrpLcharge temp : compensateDto.getPrpLchargeList()){
			tempKey = temp.getKindCode();
			if(!chargeInfoMap.containsKey(temp.getKindCode())){
				chargeInfoMap.put(tempKey, 0d);
			}
			chargeInfoMap.put(tempKey, chargeInfoMap.get(tempKey)+temp.getChargeAmount());
		}
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		String compelPayType = prpLcompensate.getCompelPayType();
		compelPayType = "1".equals(compelPayType)?"A":("2".equals(compelPayType)?"C":"B");//1.傷害；2.失能；3.死亡*/
		SubReportPrintDto tempKindInfoPrintDto = null;//各險別賠付訊息
		for(PrpLloss temp : prpLlossList){//設置實際理賠金額
			tempKey = temp.getKindCode();
			if (ConstantCodes.RISKCODE_DAZ.equals(prpLclaim.getRiskCode())) {
				tempKey = temp.getKindCode() + compelPayType;
			}
			tempKindInfoPrintDto = infoMap.get(tempKey);
			tempKindInfoPrintDto.setStrActualClaimAmount(decimalFormat.format(temp.getSumLoss()));
		}
		//設置追償金額
		for(PrpLloss temp : compensateDto.getPrpLlossList()){
			tempKey = temp.getKindCode();
			if (ConstantCodes.RISKCODE_DAZ.equals(prpLclaim.getRiskCode())) {
				tempKey = temp.getKindCode() + compelPayType;
			}
			tempKindInfoPrintDto = infoMap.get(tempKey);
			tempKindInfoPrintDto.setStrReplevyAmount(decimalFormat.format(temp.getSumRealPay()));
		}
		String statements = " compensateNo in (select compensateNo from prplcompensate where compensateNo like 'C"+prpLclaim.getClaimNo()+"%' and (underwriteflag = '1' or underwriteflag = '3')) order by compensateNo asc,serialNo asc";
		List<PrpLpersonLoss> personLossList = this.prpLpersonLossService.findByConditions(statements);
		Map<String,Set<String>> personKind = new HashMap<String,Set<String>>();//各險別理賠人數
		Set<String> personSet = new HashSet<String>();//傷亡人員
		if (personLossList!=null && !personLossList.isEmpty()) {
			String casualties = "";
			if (ConstantCodes.RISKCODE_DAZ.equals(prpLclaim.getRiskCode())) {//強制險詳細分類
				tempKey = personLossList.get(0).getKindCode();
				personKind.put(tempKey + "A", new HashSet<String>());//初始化醫療人數
				personKind.put(tempKey + "C", new HashSet<String>());//初始化失能人數
				personKind.put(tempKey + "B", new HashSet<String>());//初始化死亡人數
			}
			for (PrpLpersonLoss temp : personLossList) {
				tempKey = temp.getKindCode();
				personSet.add(temp.getIdentifyNumber());//根據身份證號碼確定唯一受害人
				if (ConstantCodes.RISKCODE_DAZ.equals(prpLclaim.getRiskCode())) {
					casualties = temp.getCasualties();
					if (casualties.indexOf("1") != -1) {//強制險有傷害
						personKind.get(tempKey + "A").add(temp.getIdentifyNumber());
					}
					if (casualties.indexOf("2") != -1) {//強制險有失能
						personKind.get(tempKey + "C").add(temp.getIdentifyNumber());
					}
					if (casualties.indexOf("3") != -1) {//強制險有死亡
						personKind.get(tempKey + "B").add(temp.getIdentifyNumber());
					}
				} else {
					if(!personKind.containsKey(tempKey)){
						personKind.put(tempKey, new HashSet<String>());
					}
					personKind.get(tempKey).add(temp.getIdentifyNumber());
				}
			}
		}
		claimStatementPrintDto.setStrCasualties(String.valueOf(personSet.size()));//人員傷亡
		for (Entry<String, SubReportPrintDto> entry : infoMap.entrySet()) {
			tempKey = entry.getKey();
			tempKindInfoPrintDto = entry.getValue();
			if(personKind.containsKey(tempKey)){//設置險別的理賠
				tempKindInfoPrintDto.setStrClaimNumber(String.valueOf(personKind.get(tempKey).size()));
			}
			if (!chargeInfoMap.isEmpty()) {
				if (ConstantCodes.RISKCODE_DAZ.equals(prpLclaim.getRiskCode())) {//強制險費用放在傷害里
					if (tempKey.endsWith("A")) {
						tempKindInfoPrintDto.setStrClaimAmount(decimalFormat.format(chargeInfoMap.values().iterator().next()));
					}
				} else if (chargeInfoMap.containsKey(tempKey)) {
					tempKindInfoPrintDto.setStrClaimAmount(decimalFormat.format(chargeInfoMap.get(tempKey)));
				}
			}
		}
		List<SubReportPrintDto> compensateInfoList = new ArrayList<SubReportPrintDto>();
		compensateInfoList.addAll(infoMap.values());
		return compensateInfoList;
	}

	/**
	 * 汽車險理賠申請書
	 * @param path 列印模板的路徑
	 * @param emptyHashMap Map中有列印模板的路徑加上公司logo地址，列印模板的路徑，計算書號
	 * @param compensateNo 計算書號
	 * @return checkBytes 列印的字節
	 */
	public byte[] claimApplication(String path, Map<String, Object> emptyHashMap, String claimNo) {
		try {
			PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
			// 变量声明部分
			String strRegistNo = ""; // 报案号
			String strCompPolicyNo = ""; // 交强险保单号
			String strPolicyNo = ""; // 商业险保单号
			String strCompClaimNo = ""; // 交强险立案号
			String strCompInsuredTerm = "";// 交强险保险期间
			String strClaimNo = ""; // 商业险立案号
			String strInsuredTerm = "";// 保险期间
			String strInsuredName = "";// 被保险人
			String strBrandName = "";// 厂牌型号
			String strLicenseNo = ""; // 保单中的号牌号码
			String strInsuredPhoneNumber = "";// 被保险人电话
			String strInsuredPhoneNumber1 = "";// 被保险人移动电话
			String strInsuredEmail = "";// 被保险人email
			String strDamageStartDate = "";// 出险日期
			String strDamageAddress = "";// 出险地址
			String strDriverPhoneNumber = "";// 驾驶员电话
			String strDriverPhoneNumber1 = "";// 驾驶员移动电话
			String strEmail = "";// 驾驶员email
			String strSex = "";// 驾驶员性别
			String strBirthday = "";// 驾驶员生日
			String strMarriage = "";// 驾驶员婚姻
			String strNationality = "";// 驾驶员国籍
			String strInsuredAddress = "";// 被保险人地址
			String strDriverName = "";// 驾驶人
			String strRelationType = "";// 报案人与被保险人的关系
			String strDrivingLicenseNo = "";// 驾照号
			String strContext = "";// 事故经过

			RegistDto registDto = registService.findByPrimaryKey(prpLclaim.getRegistNo());
//			PolicyDto policyDto = policyService.findByPrimaryKey(prpLclaim.getPolicyNo());
			PrpLregist prplregist = registDto.getPrpLregist();
			String policyNo = prpLclaim.getPolicyNo();
			String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
			String damageHour = prpLclaim.getDamageStartHour();
			PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
			// 报案人与被保险人的关系
			if ("1".equals(prplregist.getRelationType())) {
				strRelationType = "■本人   □親屬   □朋友    □業務員    □代理人   □修理廠    □其他";
			} else if ("2".equals(prplregist.getRelationType())) {
				strRelationType = "□本人   ■親屬   □朋友    □業務員    □代理人   □修理廠    □其他";
			} else if ("3".equals(prplregist.getRelationType())) {
				strRelationType = "□本人   □親屬   ■朋友    □業務員    □代理人   □修理廠    □其他";
			} else if ("4".equals(prplregist.getRelationType())) {
				strRelationType = "□本人   □親屬   □朋友    ■業務員    □代理人   □修理廠    □其他";
			} else if ("5".equals(prplregist.getRelationType())) {
				strRelationType = "□本人   □親屬   □朋友    □業務員    ■代理人   □修理廠    □其他";
			} else if ("6".equals(prplregist.getRelationType())) {
				strRelationType = "□本人   □親屬   □朋友    □業務員    □代理人   ■修理廠    □其他";
			} else {
				strRelationType = "□本人   □親屬   □朋友    □業務員    □代理人   □修理廠    ■其他";
			}
			Prplregistrpolicy prpLRegistRPolicy = null;
			for (int i = 0; i < registDto.getPrpLRegistRPolicyList().size(); i++) {
				prpLRegistRPolicy = registDto.getPrpLRegistRPolicyList().get(i);
				if ("1".equals(prpLRegistRPolicy.getPolicyType())) {
					strPolicyNo = prpLRegistRPolicy.getId().getPolicyNo(); // 商业险保单号
					strClaimNo = prpLRegistRPolicy.getClaimNo(); // 商业险立案号
					// 保险期间
					strInsuredTerm = PrintUtils.getYearToDayMGStr(prpCmain.getStartDate()) + "至" + PrintUtils.getYearToDayMGStr(prpCmain.getEndDate());
				} else {
					strCompPolicyNo = prpLRegistRPolicy.getId().getPolicyNo(); // 交强险保单号
					strCompClaimNo = prpLRegistRPolicy.getClaimNo(); // 交强险立案号
					// 保险期间
					strCompInsuredTerm = PrintUtils.getYearToDayMGStr(prpCmain.getStartDate()) + "至" + PrintUtils.getYearToDayMGStr(prpCmain.getEndDate());
				}
			}
			List<PrpCinsured> prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour , prplregist.getInsuredCode() , prplregist.getInsuredName());
			if (prpCinsuredList != null) {
				for (int index = 0; index < prpCinsuredList.size(); index++) {
					PrpCinsured prpCinsured = prpCinsuredList.get(index);
					String insuredCode = prpCinsured.getInsuredCode();
					String identifyNumber = prpCinsured.getIdentifyNumber();
					if((!CommonUtils.isEmpty(insuredCode) && insuredCode.equals(prplregist.getInsuredCode()))
							|| (!CommonUtils.isEmpty(identifyNumber) && identifyNumber.equals(prplregist.getInsuredCode()))){
						strInsuredName = prpCinsured.getInsuredName();// 被保险人
						strInsuredPhoneNumber = prpCinsured.getPhoneNumber();// 被保险人电话
						strInsuredPhoneNumber1 = prpCinsured.getMobile();// 被保险人移动电话
						strInsuredEmail = prpCinsured.getEmail();// 被保险人email
						strInsuredAddress = prpCinsured.getInsuredAddress();
						break;
					}
				}
			}

			if (registDto.getPrpLdriverList() != null&&registDto.getPrpLdriverList().size()>0) {
				PrpLdriver prpLdriver = registDto.getPrpLdriverList().get(0);
				// for (int index = 0; index <
				// registDto.getPrpLdriverList().size(); index++) {
				// prpLdriver = registDto.getPrpLdriverList().get(index);
				// strLicenseNo1 = prpLdriver.getLicenseNo();

				// if (strLicenseNo1.equals(strLicenseNo)) {
				strDriverPhoneNumber = prpLdriver.getDriverSeaRoute();// 驾驶员电话
				strDriverPhoneNumber1 = prpLdriver.getMobilePhone();// 驾驶员移动电话
				strDriverName = prpLdriver.getDriverName();// 驾驶人
				strEmail = "";// 驾驶员email
				strSex = PrintUtils.getDriverSexName(prpLdriver.getDriverSex());// 驾驶员性别
				// 驾驶员生日
				strBirthday = PrintUtils.getYearToDayMGStr(prpLdriver.getBirthday());
				strMarriage = PrintUtils.getDriverIsMarried(prpLdriver.getIsMarried());// 驾驶员婚姻
				strNationality = prpLdriver.getDriverApanage();// 驾驶员国籍
				strDrivingLicenseNo = prpLdriver.getDrivingLicenseNo();
				// }
				// }
			}
			DateTime dateTime = new DateTime(prpLclaim.getDamageStartDate());
			if(!CommonUtils.isEmpty(prpLclaim.getDamageStartHour())&&prpLclaim.getDamageStartHour().lastIndexOf(":")>=5){
				dateTime = new DateTime(dateTime.toString(DateTime.YEAR_TO_DAY)+ " " +prpLclaim.getDamageStartHour(),DateTime.YEAR_TO_SECOND);
			}else if(!CommonUtils.isEmpty(prpLclaim.getDamageStartHour())&&prpLclaim.getDamageStartHour().lastIndexOf(":")<0){
				dateTime = new DateTime(dateTime.toString(DateTime.YEAR_TO_DAY)+ " " +prpLclaim.getDamageStartHour(),DateTime.YEAR_TO_HOUR);
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");
			strDamageStartDate = CommonUtils.getMGDateStr(dateTime, format);
			strDamageAddress = prpLclaim.getDamageAddress();
			for (int index = 0; index < registDto.getPrpLthirdPartyList().size(); index++) {
				PrpLthirdParty prpLthirdParty = registDto.getPrpLthirdPartyList().get(index);
				// 取得保险车辆信息
				if (prpLthirdParty.getInsureCarFlag().equals("1")) {
					strBrandName = prpLthirdParty.getBrandName();
					strLicenseNo = prpLthirdParty.getLicenseNo();
//					strDrivingLicenseNo = prpLthirdParty.getDrivingLicenseNo();
				}
			}
			if (registDto.getPrpLregistTextList().size() > 0) {
				for (int i = 0; i < registDto.getPrpLregistTextList().size(); i++) {
					PrpLregistText prpLregistText = registDto.getPrpLregistTextList().get(i);
					strContext = strContext + prpLregistText.getContext();
				}
			}
			ClaimApplicationPrintDto claimApplicationPrintDto = new ClaimApplicationPrintDto();
			List<ClaimApplicationPrintDto> claimApplicationList = new ArrayList<ClaimApplicationPrintDto>();
			claimApplicationPrintDto.setStrRegistNo(strRegistNo); // 报案号
			claimApplicationPrintDto.setStrCompPolicyNo(strCompPolicyNo); // 交强险保单号
			claimApplicationPrintDto.setStrPolicyNo(strPolicyNo); // 商业险保单号
			claimApplicationPrintDto.setStrCompClaimNo(strCompClaimNo); // 交强险立案号
			claimApplicationPrintDto.setStrClaimNo(strClaimNo); // 商业险立案号
			claimApplicationPrintDto.setStrInsuredTerm(strInsuredTerm); // 保险期间
			claimApplicationPrintDto.setStrInsuredName(strInsuredName); // 被保险人
			claimApplicationPrintDto.setStrBrandName(strBrandName); // 厂牌型号
			claimApplicationPrintDto.setStrLicenseNo(strLicenseNo); // 保单中的号牌号码
			claimApplicationPrintDto.setStrInsuredPhoneNumber(strInsuredPhoneNumber); // 被保险人电话
			claimApplicationPrintDto.setStrInsuredPhoneNumber1(strInsuredPhoneNumber1); // 被保险人移动电话
			claimApplicationPrintDto.setStrInsuredEmail(strInsuredEmail); // 被保险人email
			claimApplicationPrintDto.setStrDamageStartDate(strDamageStartDate); // 出险日期
			claimApplicationPrintDto.setStrDamageAddress(strDamageAddress); // 出险地址
			claimApplicationPrintDto.setStrDriverPhoneNumber(strDriverPhoneNumber); // 驾驶员电话
			claimApplicationPrintDto.setStrDriverPhoneNumber1(strDriverPhoneNumber1); // 驾驶员移动电话
			claimApplicationPrintDto.setStrEmail(strEmail); // 驾驶员email
			claimApplicationPrintDto.setStrSex(strSex); // 驾驶员性别
			claimApplicationPrintDto.setStrBirthday(strBirthday); // 驾驶员生日
			claimApplicationPrintDto.setStrMarriage(strMarriage); // 驾驶员婚姻
			claimApplicationPrintDto.setStrNationality(strNationality); // 驾驶员国籍
			claimApplicationPrintDto.setStrInsuredAddress(strInsuredAddress);// 被保险人地址
			claimApplicationPrintDto.setStrDriverName(strDriverName);// 驾驶人
			claimApplicationPrintDto.setStrCompInsuredTerm(strCompInsuredTerm);
			claimApplicationPrintDto.setStrRelationType(strRelationType);
			claimApplicationPrintDto.setStrDrivingLicenseNo(strDrivingLicenseNo);
			claimApplicationPrintDto.setStrContext(strContext);
			claimApplicationList.add(claimApplicationPrintDto);
			return JasperRunManager.runReportToPdf(path + "ClaimApplication.jasper", emptyHashMap, new JRBeanCollectionDataSource(claimApplicationList));
		} catch (UserException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 理算報告書
	 * @param path 列印模板的路徑
	 * @param emptyHashMap Map中有列印模板的路徑加上公司logo地址，列印模板的路徑，計算書號
	 * @param compensateNo 計算書號
	 * @return checkBytes 列印的字節
	 */
	public byte[] compensateReport(String path, Map<String, Object> emptyHashMap, String compensateNo) {
		try {
			CompensateDto compensateDto = compensateService.findByPrimaryKey(compensateNo.trim());
			PrpLcheck prpLcheck = prpLcheckService.findPrpLcheck(compensateDto.getPrpLclaim().getRegistNo());
			PrpLregist prpLregist = prpLregistService.findPrpLregist(compensateDto.getPrpLclaim().getRegistNo());
			//PrpCmain prpCmain = prpCmainService.findPrpCmain(compensateDto.getPrpLclaim().getPolicyNo());
			String strOperatorName = codeService.translateUserCode(compensateDto.getPrpLcompensate().getHandlerCode(), true);// 获取经办人
			String strCompany = codeService.translateComCode(prpLregist.getComCode(), true);
			String strIndemnityDutyName = codeService.translateCodeCode("IndemnityDuty", compensateDto.getPrpLcompensate().getIndemnityDuty(), true);// 获取责任类型
			String strFirstDuanluo = ""; // 第一段
			String strSecondDuanluo = ""; // 第二段
			String strThirdDuanluo = "";// 第三段
			String strInsuredTerm = ""; // 保险期限
			String strFrameNo = ""; // 车架号
			String dblPurchasePrice = ""; // 新车购置价
			String strRunAreaName = ""; // 车辆行驶区域
			String strUseYears = ""; // 使用年限
			String strSumAmount = ""; // 应交保费
			// String strPlanFee = ""; // 已交保费
			String strlIndemnityDutyRate = ""; // 责任比例
			String strTypeName = "";// 险种
			int intDriverCount = 0;
			int index = 0;
			int j = 0;
			int intItemCarCount = 0;
			int intPlanCount = 0;
			double douSumFee = 0; // 应缴
			double douSumFeeTem = 0;
			double douDelinquentFee = 0;
			double douDelinquentFeeTem = 0;
			String strPayFee = "";
			String strLicenseNo1 = "";// 报案中的车型
			String strLicenseNo = ""; // 保单中的车型
			String strDriverName = "";// 驾驶员名称
			String strDrivingCarType = "";// 准驾车型
			String strUseNature = "";// 车辆使用性质
			String strDrivingYear = ""; // 驾驶年限
			String strDamageName = ""; // 出险原因
			String strDamageTypeName = ""; // 事故类型
			String strChecker1 = ""; // 获取第一查勘人
			String strChecker2 = ""; // 获取第二查勘人
			String strCheckSite = ""; // 查勘地点

			double dblPayFee = 0; // 已交
//			PolicyDto policyDto = policyService.findByPrimaryKey(compensateDto.getPrpLclaim().getPolicyNo());
			String policyNo = compensateDto.getPrpLclaim().getPolicyNo();
			String damageDate = new DateTime(compensateDto.getPrpLclaim().getDamageStartDate()).toString();
			String damageHour = compensateDto.getPrpLclaim().getDamageStartHour();
			PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
			RegistDto registDto = registService.findByPrimaryKey(compensateDto.getPrpLclaim().getRegistNo());
			CertainLossDto certainLossDto = certainLossService.findByPrimaryKey(compensateDto.getPrpLclaim().getRegistNo());
			List<PrpCitemCar> prpCitemCarList = this.endorseViewHelper.findPrpCitemCar(policyNo, damageDate, damageHour);
			if (prpCitemCarList != null) {
				intItemCarCount = prpCitemCarList.size();
			}
			if (prpCitemCarList != null) {
				PrpCitemCar prpCitemCar = null;
				for (index = 0; index < intItemCarCount; index++) {
					prpCitemCar = prpCitemCarList.get(index);
					strLicenseNo = prpCitemCar.getLicenseNo();
					strUseNature = codeService.translateCodeCode("UseNature", prpCitemCar.getUseNatureCode(), true); // 车辆使用性质
					strFrameNo = prpCitemCar.getFrameNo(); // 车架号
					dblPurchasePrice = decimalFormat.format(prpCitemCar.getPurchasePrice()); // 新车购置价
					strRunAreaName = codeService.translateCodeCode("RunArea", prpCitemCar.getRunAreaCode(), true);
					strUseYears = prpCitemCar.getUseYears() + "";//使用年限
				}
			}

			if (registDto.getPrpLdriverList() != null) {
				intDriverCount = registDto.getPrpLdriverList().size();
				PrpLdriver prpLdriver = null;
				for (index = 0; index < intDriverCount; index++) {
					prpLdriver = registDto.getPrpLdriverList().get(index);
					strLicenseNo1 = prpLdriver.getLicenseNo();
					if (strLicenseNo1.equals(strLicenseNo)) {
						strDriverName = prpLdriver.getDriverName(); // 驾驶员名称
						strDrivingCarType = prpLdriver.getDrivingCarType(); // 准驾车型
						if ("05".equals(strDrivingCarType)) {
						}
						strDrivingYear = prpLdriver.getDrivingYear() + ""; // 驾驶年限
						if ("".equals(DataUtils.dbNullToEmpty(strDrivingYear))) {
							strDrivingYear = "0";
						}
					}
				}
			}
//			if (ConstantCodes.RISKCODE_DAZ.equals(prplregist.getRiskCode())) {
//				strDamageName = prpLregist.getDamageNameBZ(); // 出险原因
//			} else {
//				strDamageName = prpLregist.getDamageName(); // 出险原因
//			}
			strDamageName = compensateDto.getPrpLclaim().getDamageName();// 出险原因
			if (DataUtils.emptyToNull(prpLregist.getDamageTypeCode()) != null) {
				strDamageTypeName = codeService.translateCodeCode("DamageTypeCode", prpLregist.getDamageTypeCode(), true); // 事故类型
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH時");
			strFirstDuanluo = "        " + CommonUtils.getMGDateStr(compensateDto.getPrpLclaim().getDamageStartDate(), format) + "，駕駛員（姓名）：" + strDriverName + "駕駛車輛，因" + strDamageName + "原因發生" + strDamageTypeName + "（保險事故類型）事故，造成保險損失。";

			strChecker1 = prpLcheck.getChecker1(); // 获取第一查勘人
			strChecker2 = prpLcheck.getChecker2(); // 获取第二查勘人
			if (strChecker2 != null) {
				strChecker2 = "和" + strChecker2;
			} else {
				strChecker2 = "";
			}
			strCheckSite = prpLcheck.getCheckSite(); // 查勘地点
			strTypeName = codeService.translateRiskCode(compensateDto.getPrpLclaim().getRiskCode(), true); // 获取保险险种
			strOperatorName = codeService.translateUserCode(compensateDto.getPrpLcompensate().getHandlerCode(), true); // 获取经办人
			strIndemnityDutyName = codeService.translateCodeCode("IndemnityDuty", compensateDto.getPrpLcompensate().getIndemnityDuty(), true); // 获取责任类型

			strlIndemnityDutyRate = compensateDto.getPrpLcompensate().getIndemnityDutyRate() + ""; // 获取责任比例
			strlIndemnityDutyRate = strlIndemnityDutyRate.replace(".0", "");

			strSecondDuanluo = "        接到報案後，由" + strChecker1 + strChecker2 + "於" + PrintUtils.getYearToDayMGName(prpLcheck.getCheckDate()) + "到" + strCheckSite + "（地點）進行了查勘。根據查勘情況以及有關證明材料，認定該事故屬" + strTypeName + "（險種）保險責任。此事故經" + strOperatorName
					+ "認定被保險人負" + strIndemnityDutyName + "責任，被保險人應當承擔" + strlIndemnityDutyRate + "%的損失。";
			strInsuredTerm = PrintUtils.getYearToDayMGStr(prpCmain.getStartDate()) + "至" + PrintUtils.getYearToDayMGStr(prpCmain.getEndDate());
			strSumAmount = decimalFormat.format(prpCmain.getSumPremium());
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.policyNo", policyNo);
			List<PrpCplan> prpCplanList= this.prpCplanService.findPrpCplan(queryRule);
			if (prpCplanList != null) {
				intPlanCount = prpCplanList.size();
				PrpCplan prpCplan = null;
				for (index = 0; index < intPlanCount; index++) {
					prpCplan = prpCplanList.get(index);
					douSumFee = prpCplan.getPlanFee();
					douSumFeeTem = douSumFeeTem + douSumFee;
					douDelinquentFee = prpCplan.getDelinquentFee();
					douDelinquentFeeTem = douDelinquentFeeTem + douDelinquentFee;
				}
				dblPayFee = douSumFeeTem - douDelinquentFeeTem;
				strPayFee = decimalFormat.format(dblPayFee);
			}
			strThirdDuanluo = "被保險人應繳保費：" + strSumAmount + "元，已繳付" + strPayFee + "元。";
			// 计算定损
			List<PrpCitemKind> cTemp = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
			String[] strKindName = new String[cTemp.size()]; // 条款名称
			double[] dbSumFee = new double[cTemp.size()]; // 每个条款对应的总保费
			PrpCitemKind prpCitemKind = null;
			PrpLperson prpLperson = null;
			PrpLcomponent prpLcomponent = null;
			PrpLrepairFee prpLrepairFee = null;
			PrpLprop prpLprop = null;
			for (index = 0; index < cTemp.size(); index++) {
				prpCitemKind = cTemp.get(index);
				strKindName[index] = prpCitemKind.getKindName();
				dbSumFee[index] = 0;
				for (j = 0; j < certainLossDto.getPrpLpersonList().size(); j++) {
					prpLperson = certainLossDto.getPrpLpersonList().get(j);
					if (prpCitemKind.getKindCode().equals(prpLperson.getKindCode())) {
						dbSumFee[index] += prpLperson.getSumDefLoss();
					}
				}
				for (j = 0; j < certainLossDto.getPrpLcomponentList().size(); j++) {
					prpLcomponent = certainLossDto.getPrpLcomponentList().get(j);
					if (prpCitemKind.getKindCode().equals(prpLcomponent.getKindCode())) {
						dbSumFee[index] += prpLcomponent.getSumVeriLoss();// 显示核损金额
					}
				}
				for (j = 0; j < certainLossDto.getPrpLrepairFeeList().size(); j++) {
					prpLrepairFee = (PrpLrepairFee) certainLossDto.getPrpLrepairFeeList().get(j);
					if (prpCitemKind.getKindCode().equals(prpLrepairFee.getKindCode())) {
						dbSumFee[index] += prpLrepairFee.getVeriSumLoss();// 显示核损金额
					}
				}
				for (j = 0; j < certainLossDto.getPrpLpropList().size(); j++) {
					prpLprop = certainLossDto.getPrpLpropList().get(j);
					if (prpCitemKind.getKindCode().equals(prpLprop.getKindCode())) {
						dbSumFee[index] += prpLprop.getSumDefLoss();
					}
				}
			}
			List<SubReportPrintDto> certaInfoList = new ArrayList<SubReportPrintDto>();
			SubReportPrintDto subReportPrintDto = null;
			for (index = 0; index < cTemp.size(); index++) {
				if (dbSumFee[index] != 0 && !"".equals(strKindName[index])) {
					subReportPrintDto = new SubReportPrintDto();
					subReportPrintDto.setStrKindName(strKindName[index]);
					subReportPrintDto.setDbSumFee(decimalFormat.format(dbSumFee[index]));
					certaInfoList.add(subReportPrintDto);
				}
			}
			// 计算理算
			List<PrpCitemKind> cTemp1 = cTemp ;
			double[] sumDutyPaid = new double[cTemp1.size()]; // 每个条款对应的总保费
			double[] StrClaimAmount = new double[cTemp1.size()]; // 每个条款对应的费用合计
			String[] strKindName1 = new String[cTemp1.size()]; // 条款名称
			PrpLloss prpLloss = null;
			PrpLpersonLoss prpLpersonLoss = null;
			PrpLcharge prpLcharge = null;
			for (index = 0; index < cTemp1.size(); index++) {
				prpCitemKind = cTemp.get(index);
				strKindName1[index] = prpCitemKind.getKindName();
				sumDutyPaid[index] = 0;
				StrClaimAmount[index] = 0;
				for (j = 0; j < compensateDto.getPrpLlossList().size(); j++) {
					prpLloss = compensateDto.getPrpLlossList().get(j);
					if (prpCitemKind.getKindCode().equals(prpLloss.getKindCode())) {
						sumDutyPaid[index] += prpLloss.getSumRealPay();
					}
				}
				for (j = 0; j < compensateDto.getPrpLpersonLossList().size(); j++) {
					prpLpersonLoss = compensateDto.getPrpLpersonLossList().get(j);
					if (prpCitemKind.getKindCode().equals(prpLpersonLoss.getKindCode())) {
						sumDutyPaid[index] += prpLpersonLoss.getSumRealPay();
					}
				}
				for (j = 0; j < compensateDto.getPrpLchargeList().size(); j++) {
					prpLcharge = compensateDto.getPrpLchargeList().get(j);
					if (prpCitemKind.getKindCode().equals(prpLcharge.getKindCode())) {
						//dbSumFee1[index] += prpLcharge.getSumRealPay();
						StrClaimAmount[index]+=prpLcharge.getChargeAmount();
					}
				}
			}
			List<SubReportPrintDto> compensateInfoList = new ArrayList<SubReportPrintDto>();
			for (index = 0; index < cTemp1.size(); index++) {
				if (sumDutyPaid[index] != 0 && !strKindName[index].equals("")) {
					subReportPrintDto = new SubReportPrintDto();
					subReportPrintDto.setStrKindName1(strKindName1[index]);
					subReportPrintDto.setDbSumFee1(decimalFormat.format(sumDutyPaid[index]));
					subReportPrintDto.setStrChargeAmount(decimalFormat.format(StrClaimAmount[index]));
					compensateInfoList.add(subReportPrintDto);
				}
			}
			/******************** 计算赔款总计 ***********************/
			double dblSumPaid = 0;
			double dblSumPaidTmp = 0;
			String strSumPaid = "";
			String strCSumPaid = "";
			String strCSumPaidOut = "";

			dblSumPaid = compensateDto.getPrpLcompensate().getSumPaid();
			dblSumPaidTmp = Math.abs(dblSumPaid);
			strSumPaid = decimalFormat.format(dblSumPaid);
			strCSumPaid = PrintUtils.toChinese(dblSumPaidTmp, compensateDto.getPrpLcompensate().getCurrency());
			if (dblSumPaid < 0) {
				strCSumPaid = "負" + strCSumPaid;
			}
			strCSumPaidOut = "（新台幣大寫）" + strCSumPaid + "（$：" + strSumPaid + "元）";
			CompensateReportDto compensateReportDto = new CompensateReportDto();
			compensateReportDto.setCompensateDto(compensateDto);
			compensateReportDto.setPrpCmain(prpCmain);
			compensateReportDto.setStrCompany(strCompany);
			compensateReportDto.setStrFirstDuanluo(strFirstDuanluo);
			compensateReportDto.setStrSecondDuanluo(strSecondDuanluo);
			compensateReportDto.setStrThirdDuanluo(strThirdDuanluo);
			compensateReportDto.setStrInsuredTerm(strInsuredTerm);
			compensateReportDto.setDblPurchasePrice(dblPurchasePrice);
			compensateReportDto.setStrFrameNo(strFrameNo);
			compensateReportDto.setStrRunAreaName(strRunAreaName);
			compensateReportDto.setStrUseYears(strUseYears);
			compensateReportDto.setStrUseNature(strUseNature);
			compensateReportDto.setStrCSumPaidOut(strCSumPaidOut);
			List<CompensateReportDto> compensateList = new ArrayList<CompensateReportDto>();
			compensateReportDto.setCompensateInfoList(compensateInfoList);
			compensateReportDto.setCertaInfoList(certaInfoList);
			compensateList.add(compensateReportDto);
			emptyHashMap.put("SUBREPORT_DIR", path);
			return JasperRunManager.runReportToPdf(path + "compensateReport.jasper", emptyHashMap, new JRBeanCollectionDataSource(compensateList));
		} catch (UserException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 汽車保險報案記錄（承保理賠資訊）
	 * @param path 列印模板的路徑
	 * @param emptyHashMap Map中有列印模板的路徑加上公司logo地址，列印模板的路徑，備案號碼
	 * @param registNo 備案號碼
	 * @return checkBytes 列印的字節
	 */
	public byte[] findRegist(String path, Map<String, Object> emptyHashMap, String registNo) {
		try {
			RegistDto registDto = registService.findByPrimaryKey(registNo);
			PrpLregist prplregist = registDto.getPrpLregist();
			EndorseDto endorseDto = endorseService.findByConditions(prplregist.getPolicyNo());
			String policyNo = prplregist.getPolicyNo();
			String damageDate = new DateTime(prplregist.getDamageStartDate()).toString();
			String damageHour = prplregist.getDamageStartHour();
			//PolicyDto policyDto = this.endorseViewHelper.findForEndorBefore(policyNo, damageDate, damageHour);
			PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
			String carHavePerson = "";
			String carWeight = "";
			int index = 0;
			String strInsureCarFlag = ""; // 是否为本保单车辆
			String strLicenseNo = ""; // 保单中的号牌号码
			String strBrandName = "";// 厂牌型号
			String strEngineNo = "";// 发动机号
			String strFrameNo = "";// 车架号（VIN）
			String strUseNatureName = "";// 使用性质
			String strRunAreaName = "";// 车辆行驶区域
			String strPurchasePrice = "";// 新车购置价
			String strInsuredName = "";// 被保险人
			String strInsuredAddress = "";// 被保险人住址
			String strPostCode = "";// 邮政编码
			String strMobile = "";// 移动电话
			String strReportType = "";// 报案方式
			String strRelationType = "";// 报案人与被保险人的关系
			String strDriverName = "";// 驾驶员姓名
			String strDrivingCarType = "";// 准驾车型
			String strDrivingLicenseNo = "";// 驾驶证号码
			String strDamageArea = "";// 出险区域
			String strDamageName = "";// 出险原因
			String strDamageAddressType = "";// 出险地点分类
			String strFirstSiteName = "";// 是否是第一现场报案
			String strHandleUnit = "";// 事故处理部门
			String strPersonInjure = "";// 伤亡人员
			String strcheckInfo = "";// 查勘信息回复
			double dblSumPaid = 0; // 赔款总计
			int intClaimCount = 0;// 赔款次数
			String strPolicy = "";// 是否是交强商业险关联，如果是现实关联保单的信息，不是各自显示
			String strUseYears = "";// 使用年限
			// String strCarClauseName = "";// 基本条款类别名称
			String strInsuredTerm = "";// 保险期间
			String strSeatCount = "";
			String strRunAreaCode = "";
			String strEnrollDate = "";
			String strPheadText = "";
			String strClaimText = "   出險經過及損失情況：（行駛方向，避讓措施，財物損壞部位等） ";// 出险信息
			String strCompelNo = "";// 交强险保单号
			String strCompelComName = "";// 交强险承保公司
			String strSumPremium = "";// 应收保费
			String strComName = "";// 业务归属部门
			String strHanderName1 = "";// 出单员
			String strHandlerName = "";// 经办人
			String strUnderwriteName = "";// 核保人
			String strUserName = "";// 抄单人
			String strInputDate = "";// 抄单日期
			String strCode = "";
			String strLicenseNo1 = "";
			String strDirverFirstTime = "";
			String strEndorseNo = "";
			String strContext1 = "";
			int intThirdPartyCount = 0;
			int intInsuredCount = 0;
			int intDriverCount = 0;
			int intItemKindCount = 0;
			int intPlanCount = 0;
			int intPheadCount = 0;
			int intPheadCountTmp = 0;
			int intCompensateCount = 0;
			int intItemCarCount = 0;
			double douDelinquentFee = 0d;
			int intEngageCount = 0; // EngageDto对象的记录数
			int intEngageCountTmp = 0; // textarea行数
			String strClauses = ""; // 得到特别约定内容
			String strPolicyNo = "";// 商业险保单号
			Collection<PrpLregist> prpLregistList = new ArrayList<PrpLregist>();
			String strRegistInfo = "";
			PrpLregist prpLregist1 = null;
			prpLregistList = registService.findSamePolicyRegist(prplregist.getPolicyNo());
			Iterator<PrpLregist> it = prpLregistList.iterator();
			// 得到交强险保单信息
			if (registDto.getPrpLRegistRPolicyOfCompel() != null) {
				strCompelNo = registDto.getPrpLRegistRPolicyOfCompel().getId().getPolicyNo();
				String comCode = policyService.findByPrimaryKey(strCompelNo).getPrpCmain().getComCode();
				if (!(comCode == null || "".equals(comCode))) {
					strCompelComName = codeService.translateComCode(comCode, true);
				}
			}
			int count = 0;
			while (it.hasNext()) {
				count++;
				prpLregist1 = (PrpLregist) it.next();
				strRegistInfo += "   備案號碼：" + prpLregist1.getRegistNo() + "      備案時間：" + prpLregist1.getDamageStartDate();
				if (count > 2) {
					strRegistInfo += "資料過多，請查看歷次出險訊息！";
					break;
				}
			}
			// 得到PrpPhead对象
			if (endorseDto.getPrpPheadList() != null) {
				intPheadCount = endorseDto.getPrpPheadList().size();
			}
			String conditions = " PolicyNo='" + prplregist.getPolicyNo() + "' AND RegistNo !='" + prplregist.getRegistNo() + "' AND ClaimDate<='" + prplregist.getReportDate() + "' ";
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addSql(conditions);
			List<PrpLclaim> listTemp = prpLclaimService.findPrpLclaim(queryRule);
			if (listTemp != null) {
				intCompensateCount = listTemp.size();
			}
			if (strCompelNo.equals(prplregist.getPolicyNo())) {
				strPolicyNo = "";// 商业险保单号
				strPolicy = "交強保險基本訊息";
			} else if (strCompelNo != null && !"".equals(strCompelNo) && !strCompelNo.equals(prplregist.getPolicyNo())) {
				strPolicy = "商業交強保險基本訊息";
				strPolicyNo = prplregist.getPolicyNo();// 商业险保单号
			} else if (strCompelNo == null || "".equals(strCompelNo)) {
				strPolicy = "商業保險基本訊息";
				strPolicyNo = prplregist.getPolicyNo();// 商业险保单号
			}
			strReportType = codeService.translateCodeCode("ReportType", prplregist.getReportType(), true);
			strRelationType = codeService.translateCodeCode("RelationType", prplregist.getRelationType(), true);
			strComName = codeService.translateComCode(prpCmain.getComCode(), true);
			strHanderName1 = codeService.translateUserCode(prpCmain.getHandlerCode(), true);
			// 经办人
			strHandlerName = codeService.translateUserCode(prpCmain.getHandler1Code(), true);
			// 核保人
			strUnderwriteName = StringConvert.encode(prpCmain.getUnderWriteName());
			// 抄单人
			strUserName = codeService.translateUserCode(prplregist.getOperatorCode(), true);
			// 抄单日期
			strInputDate = PrintUtils.getYearToDayMGName(new Date());
			// 出险区域
			if ("01".equals(prplregist.getDamageAreaCode())) {
				strDamageArea = "出险網域：■市內   □市外   □省內    □省外    □中國境外  ";
			} else if ("02".equals(prplregist.getDamageAreaCode())) {
				strDamageArea = "出险網域：□市內   ■市外   □省內    □省外    □中國境外  ";
			} else if ("03".equals(prplregist.getDamageAreaCode())) {
				strDamageArea = "出险網域：□市內   □市外   ■省內    □省外    □中國境外  ";
			} else if ("04".equals(prplregist.getDamageAreaCode())) {
				strDamageArea = "出险網域：□市內   □市外   □省內    ■省外    □中國境外  ";
			} else if ("10".equals(prplregist.getDamageAreaCode())) {
				strDamageArea = "出险網域：□市內   □市外   □省內    □省外    ■中國境外  ";
			} else {
				strDamageArea = "出险網域：□市內   □市外   □省內    □省外    □中國境外  ";
			}
			// 出险原因
			if (ConstantCodes.RISKCODE_DAZ.equals(prplregist.getRiskCode())) {
				strDamageName = prplregist.getDamageNameBZ(); // 出险原因
			} else {
				strDamageName = prplregist.getDamageName(); // 出险原因
			}
			// 出险地点分类
			if ("06".equals(prplregist.getDamageAddressType())) {
				strDamageAddressType = "出險地點分類：■高速公路    □普通公路    □城市道路    □鄉村便道和機耕道    □場院及其它";
			} else if ("13".equals(strDamageAddressType)) {
				strDamageAddressType = "出險地點分類：□高速公路    ■普通公路    □城市道路    □鄉村便道和機耕道    □場院及其它";
			} else if ("14".equals(strDamageAddressType)) {
				strDamageAddressType = "出險地點分類：□高速公路    □普通公路    ■城市道路    □鄉村便道和機耕道    □場院及其它";
			} else if ("15".equals(strDamageAddressType)) {
				strDamageAddressType = "出險地點分類：□高速公路    □普通公路    □城市道路    ■鄉村便道和機耕道    □場院及其它";
			} else if ("16".equals(strDamageAddressType)) {
				strDamageAddressType = "出險地點分類：□高速公路    □普通公路    □城市道路    □鄉村便道和機耕道    ■場院及其它";
			} else {
				strDamageAddressType = "出險地點分類：□高速公路    □普通公路    □城市道路    □鄉村便道和機耕道    □場院及其它";
			}

			// 是否是第一现场报案
			if ("0".equals(prplregist.getFirstSiteFlag())) {
				strFirstSiteName = "否";
			} else {
				strFirstSiteName = "是";
			}

			// 处理部门
			if ("01".equals(prplregist.getHandleUnit())) {
				strHandleUnit = "■交警    □派出所     □消防部門    □保險公司    □自行處理    □其它";
			} else if ("02".equals(strCode)) {
				strHandleUnit = "□交警    ■派出所     □消防部門    □保險公司    □自行處理    □其它";
			} else if ("03".equals(strCode)) {
				strHandleUnit = "□交警    □派出所     ■消防部門    □保險公司    □自行處理    □其它";
			} else if ("04".equals(strCode)) {
				strHandleUnit = "□交警    □派出所     □消防部門    ■保險公司    □自行處理    □其它";
			} else if ("05".equals(strCode)) {
				strHandleUnit = "□交警    □派出所     □消防部門    □保險公司    ■自行處理    □其它";
			} else if ("99".equals(strCode)) {
				strHandleUnit = "□交警    □派出所     □消防部門    □保險公司    □自行處理    ■其它";
			} else {
				strHandleUnit = "□交警    □派出所     □消防部門    □保險公司    □自行處理    □其它";
			}
			PrpCinsured prpCinsured = null;
			List<PrpCinsured> prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(strPolicyNo, damageDate, damageHour);
			if (!CommonUtils.isEmpty(prpCinsuredList)) {
				intInsuredCount = prpCinsuredList.size();
				for (index = 0; index < intInsuredCount; index++) {
					prpCinsured = prpCinsuredList.get(index);
					if ("1".equals(prpCinsured.getInsuredFlag())) {
						strInsuredName = prpCinsured.getInsuredName();
						strInsuredAddress = prpCinsured.getInsuredAddress();
						strPostCode = prpCinsured.getPostCode();
						strMobile = prpCinsured.getMobile();
					}
				}
			}
			// --------------保单基本信息表PrpCmain*****
			// 保险费
			strSumPremium = prpCmain.getSumPremium() + "";
			// 保险期间
			strInsuredTerm = PrintUtils.getYearToDayMGStr(prpCmain.getStartDate()) + "至" + PrintUtils.getYearToDayMGStr(prpCmain.getEndDate());
			if (registDto.getPrpLthirdPartyList() != null) {
				intThirdPartyCount = registDto.getPrpLthirdPartyList().size();
				PrpLthirdParty prpLthirdParty = null;
				for (index = 0; index < intThirdPartyCount; index++) {
					prpLthirdParty = registDto.getPrpLthirdPartyList().get(index);
					strInsureCarFlag = prpLthirdParty.getInsureCarFlag();
					if (strInsureCarFlag.equals("1")) {
						strLicenseNo = StringConvert.encode(prpLthirdParty.getLicenseNo());
						strUseYears = prpLthirdParty.getUseYears() + "";
						strBrandName = prpLthirdParty.getBrandName();
						strEngineNo = prpLthirdParty.getEngineNo();
						strFrameNo = prpLthirdParty.getFrameNo();
					}
				}
			}
			// ------------机动车险标的信息表PrpCitemCar*****
			List<PrpCitemCar> prpCitemCarList =  this.endorseViewHelper.findPrpCitemCar(strPolicyNo, damageDate, damageHour);
			if (!CommonUtils.isEmpty(prpCitemCarList)) {
				intItemCarCount = prpCitemCarList.size();
				PrpCitemCar prpCitemCar = null;
				for (index = 0; index < intItemCarCount; index++) {
					prpCitemCar = prpCitemCarList.get(index);
					carHavePerson = prpCitemCar.getSeatCount() + "";
					carWeight = prpCitemCar.getTonCount() + "";
					strSeatCount = "核定載客 " + carHavePerson + " 人  核定載質量 " + carWeight + " 千克";
					strUseNatureName = codeService.translateCodeCode("UseNature", prpCitemCar.getUseNatureCode(), true);// 使用性质
					// 初次登记日期
					strEnrollDate = prpCitemCar.getEnrollDate().toString();
					if ((strEnrollDate == null) || (strEnrollDate.equals(""))) {
						strEnrollDate = "";
					}
					// 行驶区域
					strRunAreaCode = StringConvert.encode(prpCitemCar.getRunAreaCode());
					strRunAreaName = codeService.translateCodeCode("RunArea", strRunAreaCode, true);
					strPurchasePrice = decimalFormat.format(prpCitemCar.getPurchasePrice()) + "元";
				}
			}
			// --------------车险驾驶员信息表PrpLdriver*****
			if (registDto.getPrpLdriverList() != null) {
				intDriverCount = registDto.getPrpLdriverList().size();
				PrpLdriver prpLdriver = null;
				for (index = 0; index < intDriverCount; index++) {
					prpLdriver = registDto.getPrpLdriverList().get(index);
					strLicenseNo1 = prpLdriver.getLicenseNo();
					if (index == 0) {
						prpLdriver = registDto.getPrpLdriverList().get(index);
					}
					strDirverFirstTime = prpLdriver.getReceiveLicenseDate() + "";
					if ((strDirverFirstTime == null) || (strDirverFirstTime.equals(""))) {
						strDirverFirstTime = "";
					}
					if (!CommonUtils.isEmpty(strLicenseNo1)&&strLicenseNo1.equals(strLicenseNo)) {
						strDriverName = prpLdriver.getDriverName();// 驾驶员姓名
						strDrivingCarType = prpLdriver.getDrivingCarType();// 准驾车型
						if ("05".equals(strDrivingCarType)) {
							strDrivingLicenseNo = prpLdriver.getDrivingLicenseNo();// 驾驶证号码
						}
					}
				}
			}
			// --------------特别约定表PrpCengage*****
			List<PrpCengage> prpCengageList = this.endorseViewHelper.findPrpCengage(strPolicyNo, damageDate, damageHour);
			if (!CommonUtils.isEmpty(prpCengageList)) {
				PrpCengage prpCengage = null;
				for (index = 0; index < intEngageCount; index++) {
					prpCengage = prpCengageList.get(index);
					strClauses += StringConvert.encode(prpCengage.getClauses());
				}
			}
			// 检查特别约定打出来是几行
			intEngageCountTmp = 0; // textarea的行数

			for (index = 0; index < strClauses.length(); index++) {
				if (strClauses.substring(index, index + 1).equals("\\")) {
					if (!(strClauses.substring(index).length() < 4)) {
						if (strClauses.substring(index, index + 4).equals("\\r\\n")) {
							intEngageCountTmp += 1; // 只要有回车换行，intEngageCountTmp+1
						}
					}
				}
			}
			int x = 0;
			int y = 0;
			if (!(strClauses.length() < 4)) {// 如果strClauses.length()>=4，判断strClauses结尾是文字，还是回车换行
				x = strClauses.length() - 4;
				y = strClauses.length();
				if (!strClauses.substring(x, y).equals("\\r\\n")) {
					intEngageCountTmp += 1;
				}
			} else
				intEngageCountTmp = 1;
			if (intEngageCountTmp > 3) {
				strClauses = "特別約定：內容較多，請詳見特別約定清單";
			}

			// 批改信息表PrpPhead
			int intPheadCount1 = 0;
			String strEndorType = "";
			// 理赔组
			if (intPheadCount > 7) {
				intPheadCountTmp = 7;
			} else {
				intPheadCountTmp = intPheadCount;
			}
			PrpPhead prpPhead = null;
			PrpPmain prpPmain = null;
			for (index = 0; index < intPheadCountTmp; index++) {
				prpPhead = endorseDto.getPrpPheadList().get(index);
				strEndorseNo = prpPhead.getEndorseNo();
				strEndorType = prpPhead.getEndorType();
				if (!("56").equals(strEndorType)) {
					// 得到批单号和批单日期
					strPheadText += "批單號碼：" + prpPhead.getEndorseNo();
					strPheadText += " 批單日期：" + PrintUtils.getYearToDayMGName(prpPhead.getEndorDate());
					EndorseDto endorseDtoTemp = endorseService.findByPrimaryKey(strEndorseNo);
					prpPmain = endorseDtoTemp.getPrpPmain();
					// 得到保额变化量和保费变化量
					if (prpPmain != null) {
						strPheadText += " 保額變化量：" + new DecimalFormat("0.00").format(DataUtils.nullToZero(prpPmain.getChgAmount()));
						strPheadText += " 保費變化量：" + new DecimalFormat("0.00").format(DataUtils.nullToZero(prpPmain.getChgPremium()));
					}
				} else {
					intPheadCount1++;
				}
			}
			if (intPheadCount > 7) {
				strPheadText += "(其余批改訊息請見批單)";
			}

			// 出险信息
			strClaimText += " 出險日期：" + PrintUtils.getYearToDayMGName(prplregist.getDamageStartDate());
			if (ConstantCodes.RISKCODE_DAZ.equals(prplregist.getRiskCode())) {
				strClaimText += " 出險原因：" + StringConvert.encode(prplregist.getDamageNameBZ());
			} else {
				strClaimText += " 出險原因：" + StringConvert.encode(prplregist.getDamageName());
			}
			// reason:保险车辆出险信息建议显示报案出险摘要信息，目前只显示报案出险经过
			if (registDto.getPrpLregistTextList() != null) {
				int intSizeTemp = registDto.getPrpLregistTextList().size();
				if (intSizeTemp > 0) {
					PrpLregistText prpLregistText = null;
					for (int i = 0; i < intSizeTemp; i++) {
						if ((registDto.getPrpLregistTextList().get(i)).getId().getTextType().trim().equals("1")) {
							prpLregistText = registDto.getPrpLregistTextList().get(i);
							if (!prpLregistText.getContext().trim().equals("")) {
								strContext1 += prpLregistText.getContext();
							}
						}
						// 查勘回复信息取查勘报告
						if ((registDto.getPrpLregistTextList().get(i)).getId().getTextType().trim().equals("3")) {
							prpLregistText = registDto.getPrpLregistTextList().get(i);
							if (!prpLregistText.getContext().trim().equals("")) {
								strcheckInfo += prpLregistText.getContext();
							}
						}
					}
					if (!strContext1.trim().equals("")) {
						strClaimText += strContext1.trim();
					}
				}
			}
			if (intCompensateCount > 8) {
				strClaimText += "(其余出險訊息請見立案)";
			}
			// 计算赔款总计
			PrpLclaim prpLclaim = null;
			for (int m = 0; m < intCompensateCount; m++) {
				prpLclaim = listTemp.get(m);
				dblSumPaid = dblSumPaid + prpLclaim.getSumPaid();
			}
			String strPheadCount = (intPheadCount - intPheadCount1) + "";// 本单批改次数
			String strCompensateCount = intCompensateCount + "";// 车辆出险次数
			String strClaimCount = intClaimCount + "";// 赔款次数
			String strSumPaid = dblSumPaid + "";// 赔款总计
			String[] strKindCode = null;
			String[] strKindName = null;
			String[] strDangerLevel = null;// 风险水平
			String[] douAmount = null;
			List<SubReportPrintDto> subReportPrintList = null;
			List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
			if (!CommonUtils.isEmpty(prpCitemKindList)) {
				intItemKindCount = prpCitemKindList.size();
				PrpCitemKind rPrpCitemKind = null;
				String riskType = codeService.translateRiskCodetoRiskType(prplregist.getRiskCode());
				String prpLRegistRPolicyNo = "";
				if ("RISKCODE_DAZ".equals(riskType) == false) {
					if (registDto.getPrpLRegistRPolicyOfCompel() != null) {
						prpLRegistRPolicyNo = registDto.getPrpLRegistRPolicyOfCompel().getId().getPolicyNo();
						List<PrpCitemKind> rgPrpCitemKindList = this.endorseViewHelper.findPrpCitemKind(prpLRegistRPolicyNo, damageDate, damageHour, null , null);
						if (!CommonUtils.isEmpty(rgPrpCitemKindList)) {
							rPrpCitemKind = rgPrpCitemKindList.get(0);
						}
					}
				}
				if (rPrpCitemKind != null) {
					intItemKindCount = intItemKindCount + 1;
				}
				strKindCode = new String[intItemKindCount];
				strKindName = new String[intItemKindCount];
				strDangerLevel = new String[intItemKindCount];
				douAmount = new String[intItemKindCount];
				PrpCitemKind prpCitemKind = null;
				for (index = 0; index < intItemKindCount; index++) {
					if (rPrpCitemKind != null) {
						if (index == intItemKindCount - 1) {
							strKindCode[index] = rPrpCitemKind.getKindCode();
							strKindName[index] = rPrpCitemKind.getKindName();
							douAmount[index] = rPrpCitemKind.getAmount() + "";
						}
					}
					if (index != intItemKindCount - 1 || rPrpCitemKind == null) {
						prpCitemKind = prpCitemKindList.get(index);
						strKindCode[index] = prpCitemKind.getKindCode();
						strKindName[index] = prpCitemKind.getKindName();
						strDangerLevel[index] = "";
						if (prpCitemKind.getModeCode() != null && !prpCitemKind.getModeCode().equals("") && (prpCitemKind.getKindCode().equals(ConstantCodes.KINDCODE_D_A) || prpCitemKind.getKindCode().equals(ConstantCodes.KINDCODE_D_B))) {
							if (prpCitemKind.getModeCode().equals("1")) {
								strDangerLevel[index] = "(風險水平A)";
							}
							if (prpCitemKind.getModeCode().equals("2")) {
								strDangerLevel[index] = "(風險水平B)";
							}
							if (prpCitemKind.getModeCode().equals("3")) {
								strDangerLevel[index] = "(風險水平C)";
							}
						}

						/* else{ */
						if (prpCitemKind.getAmount() == 0) {
							douAmount[index] = "0.00";
						} else {
							douAmount[index] = String.valueOf(decimalFormat.format(prpCitemKind.getAmount()));
						}
						if (strKindCode[index].equals("F")) {
							if ((prpCitemKind.getModeName()).equals("國產玻璃")) {
								douAmount[index] += "（國產玻璃）";
							} else {
								douAmount[index] += "（進口玻璃）";
							}
						}
					}
				}
				subReportPrintList = new ArrayList<SubReportPrintDto>();
				SubReportPrintDto subReportPrintDto = null;
				for (index = 0; index < intItemKindCount; index++) {
					subReportPrintDto = new SubReportPrintDto();
					subReportPrintDto.setStrSerialNo((index + 1) + "");
					subReportPrintDto.setStrKindCode2(strKindCode[index]);
					subReportPrintDto.setStrKindName2(strKindName[index]);
					subReportPrintDto.setDbSumFee2(douAmount[index]);
					subReportPrintList.add(subReportPrintDto);
				}
				queryRule = QueryRule.getInstance();
				queryRule.addEqual("id.policyNo", policyNo);
				List<PrpCplan> prpCplanList = this.prpCplanService.findPrpCplan(queryRule);
				if (!CommonUtils.isEmpty(prpCplanList)) {
					intPlanCount = prpCplanList.size();
					PrpCplan prpCplan = null;
					for (index = 0; index < intPlanCount; index++) {
						prpCplan = prpCplanList.get(index);
						douDelinquentFee = prpCplan.getDelinquentFee();
						if (douDelinquentFee == 0) {
						} else {
						}
					}
				}
				PrpLext prpLext = registDto.getPrpLext();
				strPersonInjure = "傷亡人員：□第三者（傷 0 人 亡 0 人）   □車上人員（傷 0 人 亡 0 人）";
				if (prpLext != null) {
					long personInjureB = prpLext.getPersonInjureB();
					long personDeathB = prpLext.getPersonDeathB();
					long personInjureD1 = prpLext.getPersonInjureD1();
					long personDeathD1 = prpLext.getPersonDeathD1();
					strPersonInjure = "傷亡人員：";

					if (personInjureB == 0) {
						if (personDeathB == 0) {
							strPersonInjure += "□第三者（傷 0 人 亡 0 人）";
						} else {
							strPersonInjure += "■第三者（傷 0 人 亡 " + personDeathB + " 人）";
						}
					} else {
						if (personDeathB == 0) {
							strPersonInjure += "■第三者（傷 " + personInjureB + " 人 亡 0 人）";
						} else {
							strPersonInjure += "■第三者（傷 " + personInjureB + " 人 亡 " + personDeathB + " 人）";
						}
					}
					if (personInjureD1 == 0) {
						if (personDeathD1 == 0) {
							strPersonInjure += "□車上人員（傷 0 人 亡 0 人）";
						} else {
							strPersonInjure += "■車上人員（傷 0 人 亡 " + personDeathD1 + " 人）";
						}
					} else {
						if (personDeathD1 == 0) {
							strPersonInjure += "■車上人員（傷 " + personInjureD1 + " 人 亡 0 人）";
						} else {
							strPersonInjure += "■車上人員（傷 " + personInjureD1 + " 人 亡 " + personDeathD1 + " 人）";
						}
					}
				}
			}
			String strReportDate = PrintUtils.getYearToDayMGName(prplregist.getReportDate());
			String strDamageStartDate = PrintUtils.getYearToDayMGName(prplregist.getDamageStartDate());
			RegistPrintDto registPrintDto = new RegistPrintDto();
			List<RegistPrintDto> registList = new ArrayList<RegistPrintDto>();
			registPrintDto.setPrpLregist(prplregist);
			registPrintDto.setStrLicenseNo(strLicenseNo);
			registPrintDto.setStrBrandName(strBrandName);
			registPrintDto.setStrEngineNo(strEngineNo);
			registPrintDto.setStrFrameNo(strFrameNo);
			registPrintDto.setStrInsuredName(strInsuredName);
			registPrintDto.setStrReportType(strReportType);
			registPrintDto.setStrRelationType(strRelationType);
			registPrintDto.setStrDriverName(strDriverName);
			registPrintDto.setStrDrivingCarType(strDrivingCarType);
			registPrintDto.setStrDrivingLicenseNo(strDrivingLicenseNo);
			registPrintDto.setStrDamageArea(strDamageArea);
			registPrintDto.setStrDamageName(strDamageName);
			registPrintDto.setStrDamageAddressType(strDamageAddressType);
			registPrintDto.setStrFirstSiteName(strFirstSiteName);
			registPrintDto.setStrHandleUnit(strHandleUnit);
			registPrintDto.setStrPersonInjure(strPersonInjure);
			registPrintDto.setStrClaimText(strClaimText);
			registPrintDto.setStrPolicy(strPolicy);
			registPrintDto.setStrPolicy(strPolicy);
			registPrintDto.setStrPurchasePrice(strPurchasePrice);
			registPrintDto.setStrRunAreaName(strRunAreaName);
			registPrintDto.setStrUseNatureName(strUseNatureName);
			registPrintDto.setStrUseYears(strUseYears);
			registPrintDto.setStrSeatCount(strSeatCount);
			registPrintDto.setStrInsuredTerm(strInsuredTerm);
			registPrintDto.setStrSumPremium(strSumPremium);
			registPrintDto.setStrComName(strComName);
			registPrintDto.setStrHanderName1(strHanderName1);
			registPrintDto.setStrHandlerName(strHandlerName);
			registPrintDto.setStrUnderwriteName(strUnderwriteName);
			registPrintDto.setStrUserName(strUserName);
			registPrintDto.setStrInputDate(strInputDate);
			registPrintDto.setStrClauses(strClauses);
			registPrintDto.setStrPheadText(strPheadText);
			registPrintDto.setStrcheckInfo(strcheckInfo);
			registPrintDto.setStrPheadCount(strPheadCount);
			registPrintDto.setStrCompensateCount(strCompensateCount);
			registPrintDto.setStrClaimCount(strClaimCount);
			registPrintDto.setStrSumPaid(strSumPaid);
			registPrintDto.setStrCompelNo(strCompelNo);
			registPrintDto.setStrCompelComName(strCompelComName);
			registPrintDto.setStrInsuredAddress(strInsuredAddress);
			registPrintDto.setStrPostCode(strPostCode);
			registPrintDto.setStrMobile(strMobile);
			registPrintDto.setStrPolicyNo(strPolicyNo);
			registPrintDto.setStrDamageStartDate(strDamageStartDate);
			registPrintDto.setStrReportDate(strReportDate);
			registPrintDto.setStrItemKindList(subReportPrintList);
			registList.add(registPrintDto);
			emptyHashMap.put("SUBREPORT_DIR", path);
			return JasperRunManager.runReportToPdf(path + "regist.jasper", emptyHashMap, new JRBeanCollectionDataSource(registList));
		} catch (UserException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 失竊車客戶訪談表
	 * @param path 列印模板的路徑
	 * @param emptyHashMap Map中有列印模板的路徑
	 * @return checkBytes 列印的字節
	 */
	public byte[] customerInterview(String path, Map<String, Object> emptyHashMap) {
		try {
			return JasperRunManager.runReportToPdf(path + "customerInterview.jasper", emptyHashMap, new JREmptyDataSource());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 事故照片粘貼單
	 * @param path 列印模板的路徑
	 * @param emptyHashMap Map中有列印模板的路徑
	 * @param claimNo 立案號碼
	 * @return checkBytes 列印的字節
	 */
	public byte[] accidentPhotoCard(String path, Map<String, Object> emptyHashMap, String claimNo) {
		try {
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("claimNo", claimNo.trim());
			List<PrpLclaim> prpLclaimList = prpLclaimService.findPrpLclaim(queryRule);
			return JasperRunManager.runReportToPdf(path + "accidentPhotoCard.jasper", emptyHashMap, new JRBeanCollectionDataSource(prpLclaimList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 当前理算书涉及险类
	 * @param compensateno
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getReferenceKinds(String compensateno) {
		List<String> kindList = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		try {
			String conditions = "compensateno='" + compensateno + "'";
			buffer.append("select kindcode from prplloss where ").append(conditions).append(" union ");
			buffer.append("select kindcode from prplpersonloss where ").append(conditions).append(" union ");
			buffer.append("select kindcode from prplcharge where ").append(conditions);
			kindList = (List<String>) HibernateUtils.findbySql(getSession(), buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kindList;
	}

	/**
	 * 生成险别打印子报表
	 * @param infoMap
	 * @param prpCitemKind
	 */
	private void generateKindInfoPrintDto(Map<String, SubReportPrintDto> infoMap, PrpCitemKind prpCitemKind) {
		SubReportPrintDto kindInfoPrintDto = null;
		String shortTitle = null;
		List<String> kindShortTitleList = ConstantsCollection.kindShortTitle.get(prpCitemKind.getKindCode());
		if (CommonUtils.isEmpty(kindShortTitleList)) {// 正常险种
			kindInfoPrintDto = new SubReportPrintDto();
			kindInfoPrintDto.setStrKindCode(prpCitemKind.getKindCode());
			kindInfoPrintDto.setStrKindName(prpCitemKind.getKindCode() + prpCitemKind.getKindName());
			kindInfoPrintDto.setStrShortTitle("");
			setItemKindBI(prpCitemKind,kindInfoPrintDto) ;
			if (!CommonUtils.isEmpty(prpCitemKind.getModel()) && prpCitemKind.getModel().indexOf("/") != -1) {
				kindInfoPrintDto.setStrSumFee(prpCitemKind.getModel() + "萬");
			} else {
				kindInfoPrintDto.setStrSumFee(decimalFormat.format(prpCitemKind.getAmount()));
			}
			infoMap.put(prpCitemKind.getKindCode(), kindInfoPrintDto);
		} else {
			for (int i = 0; i < kindShortTitleList.size(); i++) {// 特殊险种会进行拆分
				shortTitle = kindShortTitleList.get(i);
				kindInfoPrintDto = new SubReportPrintDto();
				kindInfoPrintDto.setStrKindCode(prpCitemKind.getKindCode());
				kindInfoPrintDto.setStrShortTitle(DataUtils.dbNullToEmpty(shortTitle));
				kindInfoPrintDto.setStrSumFee(decimalFormat.format(prpCitemKind.getAmount()));
				setItemKindBI(prpCitemKind,kindInfoPrintDto) ;
				if ("21".equals(prpCitemKind.getKindCode())) {
					if (!infoMap.containsKey("21A")) {// 医疗
						kindInfoPrintDto.setStrKindCode("21A");
						kindInfoPrintDto.setStrSumFee("貳拾萬");
						kindInfoPrintDto.setStrKindName(prpCitemKind.getKindName());
					} else if (!infoMap.containsKey("21C")) {// 残废
						kindInfoPrintDto.setStrKindCode("21C");
						kindInfoPrintDto.setStrSumFee("貳佰萬");
					} else if (!infoMap.containsKey("21B")) {// 死亡
						kindInfoPrintDto.setStrKindCode("21B");
						kindInfoPrintDto.setStrSumFee("貳佰萬");
					}
				}
				infoMap.put(kindInfoPrintDto.getStrKindCode(), kindInfoPrintDto);
			}
		}
	}
	/**
	 * 設定承保自負額欄
	 * @param prpCitemKind
	 * @param kindInfoPrintDto
	 */
	public void setItemKindBI(PrpCitemKind prpCitemKind, SubReportPrintDto kindInfoPrintDto) {
        if("72".equals(prpCitemKind.getKindCode()) || "71".equals(prpCitemKind.getKindCode())){
            if(prpCitemKind.getDeductible() ==null || prpCitemKind.getDeductible().compareTo(new Double("0"))==0){
          	  kindInfoPrintDto.setStrContractDeductible("10000");
            }
        }
		  
		if(prpCitemKind.getDeductibleType()!=null && !"".equals(prpCitemKind.getDeductibleType())){
		    if("1".equals(prpCitemKind.getDeductibleType())){
		        kindInfoPrintDto.setStrContractDeductible(prpCitemKind.getDeductible().toString());
		    }else if("2".equals(prpCitemKind.getDeductibleType())){
		        if(prpCitemKind.getDeductible().doubleValue()==1){
		            kindInfoPrintDto.setStrContractDeductible("3000/5000/7000");
		        }
		        if(prpCitemKind.getDeductible().doubleValue()==2){
                  kindInfoPrintDto.setStrContractDeductible("5000/8000");
		        }
		        if(prpCitemKind.getDeductible().doubleValue()==3){
                  kindInfoPrintDto.setStrContractDeductible("5000/8000/10000");
		        }
		    }else if("3".equals(prpCitemKind.getDeductibleType())){
		        kindInfoPrintDto.setStrContractDeductible(prpCitemKind.getDeductibleRate().toString()+"%");
		    }else if("5".equals(prpCitemKind.getDeductibleType())){
		        kindInfoPrintDto.setStrContractDeductible("無自負額");
		    }
		}
	}	
	public byte[] printPrpLcompelMedical(String path,Map<String, Object> emptyHashMap,List<PrpLperson> prpLpersonList) {
		try {
			emptyHashMap.put("SUBREPORT_DIR", path);
			return JasperRunManager.runReportToPdf(path + "prpLcompelMedical.jasper", emptyHashMap, new JRBeanCollectionDataSource(prpLpersonList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

//	public PrpCmainService getPrpCmainService() {
//		return prpCmainService;
//	}
//
//	public void setPrpCmainService(PrpCmainService prpCmainService) {
//		this.prpCmainService = prpCmainService;
//	}

	public PrpLdriverService getPrpLdriverService() {
		return prpLdriverService;
	}

	public void setPrpLdriverService(PrpLdriverService prpLdriverService) {
		this.prpLdriverService = prpLdriverService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrpLcheckService getPrpLcheckService() {
		return prpLcheckService;
	}

	public void setPrpLcheckService(PrpLcheckService prpLcheckService) {
		this.prpLcheckService = prpLcheckService;
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

	public CertainLossService getCertainLossService() {
		return certainLossService;
	}

	public void setCertainLossService(CertainLossService certainLossService) {
		this.certainLossService = certainLossService;
	}

	public EndorseService getEndorseService() {
		return endorseService;
	}

	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
	}

	public PrpCitemKindService getPrpCitemKindService() {
		return prpCitemKindService;
	}

	public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
		this.prpCitemKindService = prpCitemKindService;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}

	public PrepayService getPrepayService() {
		return prepayService;
	}

	public void setPrepayService(PrepayService prepayService) {
		this.prepayService = prepayService;
	}

//	public PrpLrecaseService getPrpLrecaseService() {
//		return prpLrecaseService;
//	}
//
//	public void setPrpLrecaseService(PrpLrecaseService prpLrecaseService) {
//		this.prpLrecaseService = prpLrecaseService;
//	}

	public CheckService getCheckService() {
		return checkService;
	}

	public void setCheckService(CheckService checkService) {
		this.checkService = checkService;
	}

//	public PrpLplanService getPrpLplanService() {
//		return prpLplanService;
//	}
//
//	public void setPrpLplanService(PrpLplanService prpLplanService) {
//		this.prpLplanService = prpLplanService;
//	}
	public PrpLclaimLossService getPrpLclaimLossService() {
		return prpLclaimLossService;
	}

	public void setPrpLclaimLossService(PrpLclaimLossService prpLclaimLossService) {
		this.prpLclaimLossService = prpLclaimLossService;
	}

	public PrpLbankService getPrpLbankService() {
		return prpLbankService;
	}

	public void setPrpLbankService(PrpLbankService prpLbankService) {
		this.prpLbankService = prpLbankService;
	}

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
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

	public PrpCplanService getPrpCplanService() {
		return prpCplanService;
	}

	public void setPrpCplanService(PrpCplanService prpCplanService) {
		this.prpCplanService = prpCplanService;
	}

	//mantis：CLM0028 ，處理人員：BK007 蘇哲，需求單編號：CLM0028 追償處理賠付對象管控、追償計算書增加追償說明 Start
	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}
	//mantis：CLM0028 ，處理人員：BK007 蘇哲，需求單編號：CLM0028 追償處理賠付對象管控、追償計算書增加追償說明 END
	
}
