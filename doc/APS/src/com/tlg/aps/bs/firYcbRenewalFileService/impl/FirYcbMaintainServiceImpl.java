package com.tlg.aps.bs.firYcbRenewalFileService.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firVerifyService.FirVerifyDatasService;
import com.tlg.aps.bs.firYcbRenewalFileService.FirYcbMaintainService;
import com.tlg.aps.vo.Aps060YcbDetailVo;
import com.tlg.aps.vo.FirAddressCheckVo;
import com.tlg.aps.vo.FirAmountWsParamVo;
import com.tlg.aps.vo.FirEqFundQueryVo;
import com.tlg.aps.vo.FirInsPremVo;
import com.tlg.aps.vo.FirPremWsParamVo;
import com.tlg.aps.vo.FirVerifyVo;
import com.tlg.prpins.entity.FirAgtTocoreInsured;
import com.tlg.prpins.entity.FirPremcalcTmp;
import com.tlg.prpins.entity.FirPremcalcTmpdtl;
import com.tlg.util.DateUtils;
import com.tlg.util.StringUtil;

/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirYcbMaintainServiceImpl implements FirYcbMaintainService {

	private static final Logger logger = Logger.getLogger(FirYcbMaintainServiceImpl.class);
	private FirVerifyDatasService firVerifyDatasService;

	private Map<String, String> structureMap = new LinkedHashMap<>();

	@Override
	public Map<String, Object> basicDataCheck(Aps060YcbDetailVo botDetailVo, String userId, String checkType)
			throws Exception {
		Map<String, Object> returnMap = new LinkedHashMap<>();
		StringBuilder errMsg = new StringBuilder();
		Map<String,String> resultMap = new HashMap<>();
		int structureValid = 0;

		// 續保單號檢核
		String oldpolicyno = botDetailVo.getOldpolicyno();
		if (StringUtil.isSpace(oldpolicyno) || oldpolicyno.length() != 14) {
			errMsg.append("續保單號必填且長度需為14碼;");
		} else if (!"18".equals(oldpolicyno.substring(0, 2))) {
			errMsg.append("續保單號需為18開頭;");
		}

		// 業務員登錄證號檢核
		String handleridentifynumber = botDetailVo.getHandleridentifynumber();
		if (StringUtil.isSpace(handleridentifynumber)) {
			errMsg.append("業務員登錄證號必填;");
		}else {
			resultMap = firVerifyDatasService.findPrpyddagentByParams(handleridentifynumber, "I00006");
			if(resultMap.containsKey("errMsg")) {
				errMsg.append(resultMap.get("errMsg"));
			}
		}

		//歸屬單位、服務人員檢核
		if (StringUtil.isSpace(botDetailVo.getComcode()) || StringUtil.isSpace(botDetailVo.getHandler1code())) {
			errMsg.append("歸屬單位、服務人員需有值，請確認業務員登錄證號是否正確;");
		}
		
		//分行員編、分行代號、分行名稱檢核
		
		/*mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 20250310議題重啟 start */
		//if (StringUtil.isSpace(botDetailVo.getTemp2())) {
		//	errMsg.append("分行員編必填;");
		//}
		/*mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業20250310議題重啟 end */
		
		if (StringUtil.isSpace(botDetailVo.getExtracomcode())) {
			errMsg.append("分行代號必填;");
		}
		if (StringUtil.isSpace(botDetailVo.getExtracomname())) {
			errMsg.append("分行名稱必填;");
		}
		
		// 保單生效日檢核
		String startdate = botDetailVo.getStartdate();
		Date transStartdate = null;
		String formatStartdate = "";
		if (!StringUtil.isSpace(startdate)) {
			transStartdate = checkDate(startdate);
			if (transStartdate == null) {
				errMsg.append("保單生效日需為合理日期;");
			} else {
				formatStartdate = new SimpleDateFormat("yyyyMMdd").format(transStartdate);
				structureValid++;
			}
		} else {
			errMsg.append("保單生效日必填;");
		}

		// 郵遞區號檢核
		String addresscode = botDetailVo.getAddresscode();
		if (!StringUtil.isSpace(addresscode)) {
			String addressname = firVerifyDatasService.findPrpdNewCode("PostAddress", addresscode);
			if (StringUtil.isSpace(addressname)) {
				errMsg.append("查無郵遞區號;");
			} else {
				returnMap.put("addressname", addressname);
			}
		} else {
			errMsg.append("郵遞區號必填;");
		}

		// 外牆檢核
		String wallmaterial = botDetailVo.getWallmaterial();
		String wallname = "";
		if (!StringUtil.isSpace(wallmaterial)) {
			wallname = firVerifyDatasService.findPrpdNewCode("WallMaterial", wallmaterial);
			if (StringUtil.isSpace(wallname)) {
				errMsg.append("外牆輸入錯誤;");
			} else {
				returnMap.put("wallname", wallname);
				structureValid++;
			}
		} else {
			errMsg.append("外牆必填;");
		}

		// 屋頂檢核
		String roofmaterial = botDetailVo.getRoofmaterial();
		String roofname = "";
		if (!StringUtil.isSpace(roofmaterial)) {
			roofname = firVerifyDatasService.findPrpdNewCode("RoofMaterial", roofmaterial);
			if (StringUtil.isSpace(roofname)) {
				errMsg.append("屋頂輸入錯誤;");
			} else {
				returnMap.put("roofname", roofname);
				structureValid++;
			}
		} else {
			errMsg.append("屋頂必填;");
		}

		// 總樓層數檢核
		String sumfloors = botDetailVo.getSumfloors();
		String highrisefee = "";
		int floorNum = 0;
		if (StringUtil.isSpace(sumfloors) || !StringUtil.isNumeric(sumfloors) || Integer.parseInt(sumfloors) == 0) {
			errMsg.append("總樓層數必填且應為正整數、數字應>0;");
		} else {
			floorNum = Integer.parseInt(sumfloors);
			if (floorNum >= 25) {
				highrisefee = "15";
			} else if (floorNum >= 15) {
				highrisefee = "10";
			} else {
				highrisefee = "0";
			}
			structureValid++;
			returnMap.put("highrisefee", highrisefee);
		}

		// 「建築等級」處理
		String structure = "";
		if (structureValid == 4) {
			Map<String, String> structMap = firVerifyDatasService.findPrpdPropStructByParams(wallmaterial, roofmaterial,
					formatStartdate, floorNum);
			if (!structMap.isEmpty()) {
				structure = structMap.get("structureno");
				returnMap.put("structure", structure);
				returnMap.put("structureText",
						wallname + roofname + sumfloors + "層樓" + (structMap.get("structureText")));
			} else {
				errMsg.append("查無建物等級，無法計算保費;");
			}
		}

		// 建築年分檢核
		String buildyears = botDetailVo.getBuildyears();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR) - 1911;
		if (StringUtil.isSpace(buildyears) || !StringUtil.isNumeric(buildyears) || Integer.parseInt(buildyears) > year
				|| Integer.parseInt(buildyears) < 1) {
			errMsg.append("建築年分必填且應應介於1~今年之間;");
		}

		// 火險保額檢核
		String amountF = botDetailVo.getAmountF().replace(",", "");
		if (!checkIsNum(amountF)) {
			errMsg.append("火險保額必填且必須為數字;");
		}

		// 地震險保額檢核
		String amountQ = botDetailVo.getAmountQ().replace(",", "");
		if (!checkIsNum(amountQ) || Integer.parseInt(amountQ) < 10000 || Integer.parseInt(amountQ) > 1500000) {
			errMsg.append("地震險保額必填且保額應在10000~1500000之間;");
		}

		// 火險保費檢核
		String premiumF = botDetailVo.getPremiumF().replace(",", "");
		if (!checkIsNum(premiumF)) {
			errMsg.append("火險保費必填且必須為數字;");
		}

		// 火險保費檢核
		String premiumQ = botDetailVo.getPremiumQ().replace(",", "");
		if (!checkIsNum(premiumQ)) {
			errMsg.append("地震險保費必填且必須為數字;");
		}

		// 要被保險人資料檢核
		String insuredType = "";
		String insuredflag = "";
		Integer serial;
		Map<String, String> insuredInfoMap = new HashMap<>();
		List<Map<String,String>> insuredInfoList = new ArrayList<>();
		List<FirAgtTocoreInsured> insuredList = botDetailVo.getInsuredList();
		for (int i = 0; i < insuredList.size(); i++) {
			FirAgtTocoreInsured insured = insuredList.get(i);
			insuredflag = insured.getInsuredflag();
			if ("1".equals(insuredflag)) {
				insuredType = "被保險人";
			} else {
				insuredType = "要保人";
			}
			serial = insured.getInsuredSeq();
			String identifynumber = insured.getIdentifynumber();
			Map<String, String> idMap = new HashMap<>();
			String insurednature = "";
			if (StringUtil.isSpace(identifynumber)) {
				errMsg.append(insuredType + serial + "-證照號碼必填;");
			} else {
				idMap = firVerifyDatasService.verifyID(identifynumber);
				if (idMap.containsKey("errMsg")) {
					errMsg.append(insuredType + serial + "-" + idMap.get("errMsg"));
				} else {
					insurednature = idMap.get("insuredNature");
					insuredInfoMap.put("inseuredSeq", insured.getInsuredSeq().toString());
					insuredInfoMap.put("inseuredflag", insuredflag);
					insuredInfoMap.put("insurednature", insurednature);
					insuredInfoMap.put("idType", idMap.get("idType"));
					insuredInfoMap.put("gender", idMap.get("gender"));
					insuredInfoList.add(insuredInfoMap);
				}
			}
			
			//姓名
			if(StringUtil.isSpace(insured.getInsuredname())){
				errMsg.append(insuredType + serial + "-姓名必填");
			}

			// 行動電話
			String mobile = insured.getMobile();
			if (!StringUtil.isSpace(mobile) && !checkMobile(mobile)) {
				errMsg.append(insuredType + serial + "-行動電話需為09開頭共10位數字;");
			}
			
			// 郵遞區號
			/*mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 20250310議題重啟 start */
			/*
			String postcode = insured.getPostcode();
			if (!StringUtil.isSpace(postcode)) {
				String postname = firVerifyDatasService.findPrpdNewCode("PostAddress", postcode);
				if (StringUtil.isSpace(postname)) {
					errMsg.append(insuredType + serial + "-查無郵遞區號;");
				}
			} else {
				errMsg.append(insuredType + serial + "-郵遞區號必填;");
			}
			*/
			/*mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 20250310議題重啟 end */

			// 通訊地址
			if (StringUtil.isSpace(insured.getPostaddress())) {
				errMsg.append(insuredType + serial + "-通訊地址必填;");
			}
			
			// 居住地/註冊地
			String domicile = insured.getDomicile();
			if (!StringUtil.isSpace(domicile)) {
				if (StringUtil.isSpace(firVerifyDatasService.findPrpdNewCode("countryCName", domicile))) {
					errMsg.append(insuredType + serial + "-查無居住地/住冊地;");
				}
			} else {
				errMsg.append(insuredType + serial + "-居住地/住冊地必填;");
			}
			
			// 國籍
			String countryename = insured.getCountryename();
			if (!StringUtil.isSpace(countryename)) {
				if (StringUtil.isSpace(firVerifyDatasService.findPrpdNewCode("countryCName", countryename))) {
					errMsg.append(insuredType + serial + "-查無國籍;");
				}
			} else {
				errMsg.append(insuredType + serial + "-國籍必填;");
			}
			
			// 高危險職業
			String ishighdengeroccupation = insured.getIshighdengeroccupation();
			if (StringUtil.isSpace(ishighdengeroccupation)) {
				errMsg.append(insuredType + serial + "-是否高危職業必填;");
			}
			
			// 生日/註冊日
			String birthday = insured.getStrBirthday();
			if (birthday == null) {
				errMsg.append(insuredType + serial + "-生日/註冊日必填;");
			} else {
				birthday = DateUtils.transDate(birthday, "/");
				Date transBirthday = checkDate(birthday);
				if (transBirthday == null || new Date().compareTo(transBirthday) < 0) {
					errMsg.append(insuredType + serial + "-生日/註冊日需為合理日期且不可大於系統日期;");
				}
			}

			// 法人代表人
			String headname = insured.getHeadname();
			if (StringUtil.isSpace(headname) && "4".equals(insurednature)) {
				errMsg.append(insuredType + serial + "-若客戶類型為法人時，法人代表人必需輸入;");
			}

			/*mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 start
			  20240813-台銀續保處理作業不做電話檢核*/
			//if (StringUtil.isSpace(mobile) && StringUtil.isSpace(insured.getPhonenumber())) {
			//	errMsg.append(insuredType + serial + "-市話與行動電話應擇一輸入;");
			//}
			/*mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 end*/

			if ("4".equals(insurednature) && StringUtil.isSpace(insured.getListedcabinetcompany())) {
				errMsg.append(insuredType + serial + "為法人，上市櫃公司必填;");
			}
			
			if ("2".equals(insuredflag) && StringUtil.isSpace(insured.getInsuredidentity())) {
				errMsg.append(insuredType + serial + "-與被保人關係必填;");
			}
		}
		
		if (insuredInfoList != null && !insuredInfoList.isEmpty()) {
			returnMap.put("insuredInfoList", insuredInfoList);
		}

		// 若以上基本資料驗證都通過才繼續做以下驗證
		if (!StringUtil.isSpace(errMsg.toString())) {
			returnMap.put("checkMsg", "基本檢查未通過，請先依據「資料檢查結果」修正資料後再重新執行「資料檢查」以進行後續檢核(複保險、保額、保費…等)。");
			returnMap.put("errMsg", errMsg.toString());
			return returnMap;
		}
		StringBuilder warnMsg = new StringBuilder();

		// 複保險查詢檢核
		FirEqFundQueryVo firEqFundQueryVo = new FirEqFundQueryVo();
		String endDate = String.valueOf(Integer.parseInt(startdate.substring(0, 4)) + 1) + startdate.substring(4);
		firEqFundQueryVo.setStartDate(startdate);
		firEqFundQueryVo.setEndDate(endDate);
		firEqFundQueryVo.setPostcode(addresscode);
		firEqFundQueryVo.setAddress(botDetailVo.getAddressdetailinfo());
		firEqFundQueryVo.setSourceType("BOTRN");
		firEqFundQueryVo.setSourceUserid(userId);

		FirVerifyVo firVerifyVo = firVerifyDatasService.queryDoubleInsVerify(firEqFundQueryVo);

		returnMap.put("dquakeStatus", firVerifyVo.getDquakeStatus());
		returnMap.put("dquakeNo", firVerifyVo.getDquakeNo());
		returnMap.put("repeatpolicyno", firVerifyVo.getDquakeNo());

		warnMsg.append(firVerifyVo.getWarnMsg());
		// 複保險查詢檢核 end

		// 保額檢核start
		BigDecimal amountFNum = new BigDecimal(amountF);
		BigDecimal amountQNum = new BigDecimal(amountQ).setScale(-4, RoundingMode.UP);// 無條件進位為萬元
		FirAmountWsParamVo firWsParamVo = new FirAmountWsParamVo();
		firWsParamVo.setSourceType("BOTRN");
		firWsParamVo.setSourceUser(userId);
		firWsParamVo.setCalcType("1");
		firWsParamVo.setCalcDate(formatStartdate);
		firWsParamVo.setChannelType("20");
		firWsParamVo.setPostcode(addresscode);
		firWsParamVo.setWallno(wallmaterial);
		firWsParamVo.setRoofno(roofmaterial);
		firWsParamVo.setSumfloors(sumfloors);
		firWsParamVo.setBuildarea(botDetailVo.getBuildarea());
		firWsParamVo.setDecorFee("0");
		try {
			FirPremcalcTmp firPremcalcTmp = firVerifyDatasService.firAmountCal(firWsParamVo);
			if (firPremcalcTmp != null) {
				returnMap.put("oidFirPremcalcTmp", firPremcalcTmp.getOid().toString());
				if ("Y".equals(firPremcalcTmp.getReturnType())) {
					BigDecimal fsAmt = firPremcalcTmp.getFsAmt();
					BigDecimal eqAmt = firPremcalcTmp.getEqAmt();
					returnMap.put("wsFirAmt", fsAmt.toString());
					returnMap.put("wsQuakeAmt", eqAmt.toString());
					BigDecimal maxAmt = firPremcalcTmp.getFsMaxAmt();
					String famtStatus = "";
					String qamtStatus = "";
					// 判斷火險保額是否足額
					if (amountFNum.compareTo(new BigDecimal(0)) > 0 && amountFNum.compareTo(maxAmt) > 0) {
						famtStatus = "3";
						warnMsg.append("火險超額(上限保額：" + maxAmt.toString() + ")；");
					} else if (amountFNum.compareTo(new BigDecimal(0)) > 0 && amountFNum.compareTo(fsAmt) < 0) {
						famtStatus = "2";
						warnMsg.append("火險不足額(建議保額：" + fsAmt.toString() + ")；");
					} else if (amountFNum.compareTo(new BigDecimal(0)) > 0) {
						famtStatus = "1";// 足額
					}

					// 判斷地震保額是否足額
					if (amountQNum.compareTo(eqAmt) > 0) {
						qamtStatus = "3";
						errMsg.append("地震險超額(保額：" + eqAmt + ")；");
					} else if (amountQNum.compareTo(eqAmt) < 0) {
						qamtStatus = "2";
						errMsg.append("地震險不足額(保額：" + eqAmt + ")；");
					} else if (amountQNum.compareTo(eqAmt) == 0) {
						qamtStatus = "1";// 足額
					}

					returnMap.put("famtStatus", famtStatus);
					returnMap.put("qamtStatus", qamtStatus);
				} else {
					errMsg.append("保額計算WS失敗" + firPremcalcTmp.getReturnMsg() + ";");
				}
			}
		} catch (Exception e) {
			logger.error(e);
			warnMsg.append("檢核-保額計算WS無回應；");
		}
		// 保額檢核end

		// 地址正確性檢核 start
		FirAddressCheckVo firAddressCheckVo = new FirAddressCheckVo();
		firAddressCheckVo.setZip(addresscode);
		firAddressCheckVo.setAddress(botDetailVo.getAddressdetailinfo());
		firAddressCheckVo.setStructure(structure);
		firAddressCheckVo.setBuildyears(botDetailVo.getBuildyears());
		firAddressCheckVo.setFloors(sumfloors);

		firVerifyVo = firVerifyDatasService.addressVerify(firAddressCheckVo);
		returnMap.put("addrStatus", firVerifyVo.getAddrStatus());
		returnMap.put("addrDetail", firVerifyVo.getAddrDetail());
		warnMsg.append(firVerifyVo.getWarnMsg());
		if (!"".equals(firVerifyVo.getAddrDetail())) {
			warnMsg.append(firVerifyVo.getAddrDetail());
		}
		// 地址正確性檢核 end

		// 保費驗證start
		FirPremWsParamVo firPremWsParamVo = new FirPremWsParamVo();
		firPremWsParamVo.setSourceType("FBRN");
		firPremWsParamVo.setSourceUser(userId);
		firPremWsParamVo.setCalcType("2");
		firPremWsParamVo.setCalcDate(formatStartdate);
		firPremWsParamVo.setChannelType("20");

		ArrayList<FirInsPremVo> firInsPremVoList = new ArrayList<>();
		if (amountQNum.compareTo(new BigDecimal(0)) > 0) {
			FirInsPremVo firInsPremVoQ = new FirInsPremVo();
			firInsPremVoQ.setRiskcode("F02");
			firInsPremVoQ.setKindcode("FR2");
			firInsPremVoQ.setPara01(amountQ);
			firInsPremVoList.add(firInsPremVoQ);
		}

		if (amountFNum.compareTo(new BigDecimal(0)) > 0) {
			FirInsPremVo firInsPremVoF = new FirInsPremVo();
			firInsPremVoF.setRiskcode("F02");
			firInsPremVoF.setKindcode("FR3");
			firInsPremVoF.setParaType("1");
			firInsPremVoF.setPara01(amountF);
			firInsPremVoF.setPara02(wallmaterial);
			firInsPremVoF.setPara03(roofmaterial);
			firInsPremVoF.setPara04(sumfloors);
			firInsPremVoF.setPara05("N");
			firInsPremVoList.add(firInsPremVoF);
		}
		firPremWsParamVo.setInsPremList(firInsPremVoList);
		FirPremcalcTmp firPremcalcTmp = firVerifyDatasService.firPremCal(firPremWsParamVo);

		returnMap.put("oidFirPremcalcTmp2", firPremcalcTmp.getOid());
		if (firPremcalcTmp.getReturnType().equals("N")) {
			errMsg.append("保費計算失敗，請聯繫系統負責人：" + firPremcalcTmp.getReturnMsg());
			returnMap.put("errMsg", errMsg.toString());
			returnMap.put("warnMsg", warnMsg.toString());
			return returnMap;
		}

		for (FirPremcalcTmpdtl firPremcalcTmpdtl : firPremcalcTmp.getFirPremcalcTmpdtlList()) {
			if ("FR2".equals(firPremcalcTmpdtl.getKindcode())
					&& new BigDecimal(premiumQ).compareTo(firPremcalcTmpdtl.getPremium()) != 0) {
				errMsg.append("畫面輸入的地震險保費與保費計算結果不符(" + firPremcalcTmpdtl.getPremium() + ");");
			} else if ("FR3".equals(firPremcalcTmpdtl.getKindcode())
					&& new BigDecimal(premiumF).compareTo(firPremcalcTmpdtl.getPremium()) != 0) {
				errMsg.append("畫面輸入的火險保費與保費計算結果不符(" + firPremcalcTmpdtl.getPremium() + ");");
			}
		}
		// 保費驗證end

		if (checkType.equals("basic")) {
			returnMap.put("errMsg", errMsg.toString());
			returnMap.put("warnMsg", warnMsg.toString());
			return returnMap;
		}

		// 儲存時才做以下檢核
		String buildarea = botDetailVo.getBuildarea();
		if (StringUtil.isSpace(buildarea) || !StringUtil.isNum(buildarea) || countDecimal(buildarea) > 2
				|| new BigDecimal(buildarea).compareTo(BigDecimal.ZERO) <= 0) {
			errMsg.append("坪數必填且需>0，需為數字且小數點至多2位;");
		}
		
		returnMap.put("errMsg", errMsg.toString());

		return returnMap;

	}

	private Date checkDate(String date) {
		if (date.length() != 10) {
			return null;
		}
		Date transDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		sdf.setLenient(false);
		try {
			transDate = sdf.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return transDate;
	}

	public boolean checkIsNum(String str) throws Exception {
		boolean check = true;
		if (StringUtil.isSpace(str) || !StringUtil.isNumeric(str)) {
			check = false;
		}
		return check;
	}

	public boolean checkMobile(String mobile) {
		boolean check = true;
		if (!StringUtil.isNumeric(mobile) || !mobile.substring(0, 2).equals("09") || mobile.length() != 10) {
			check = false;
		}
		return check;
	}

	// 取小數後有幾位
	private int countDecimal(String str) {
		int count = 0;
		if (str.contains(".")) {
			str = str.substring(str.indexOf(".") + 1);
			count = str.length();
		}
		return count;
	}

	public Map<String, String> getStructureMap() {
		structureMap.put("1", "特一");
		structureMap.put("2", "特二");
		structureMap.put("3", "頭等");
		structureMap.put("5", "二等");
		structureMap.put("6", "三等");
		structureMap.put("7", "露天");
		return structureMap;
	}

	public void setStructureMap(Map<String, String> structureMap) {
		this.structureMap = structureMap;
	}

	public FirVerifyDatasService getFirVerifyDatasService() {
		return firVerifyDatasService;
	}

	public void setFirVerifyDatasService(FirVerifyDatasService firVerifyDatasService) {
		this.firVerifyDatasService = firVerifyDatasService;
	}
}
