package com.tlg.aps.bs.firPanhsinFeedbackFile.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firPanhsinFeedbackFile.RenewalDataProcessingService;
import com.tlg.aps.bs.firVerifyService.FirVerifyDatasService;
import com.tlg.aps.vo.Aps016DetailVo;
import com.tlg.aps.vo.FirAddressCheckVo;
import com.tlg.aps.vo.FirAmountWsParamVo;
import com.tlg.aps.vo.FirEqFundQueryVo;
import com.tlg.aps.vo.FirInsPremVo;
import com.tlg.aps.vo.FirPremWsParamVo;
import com.tlg.aps.vo.FirVerifyVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirPremcalcTmp;
import com.tlg.prpins.entity.FirPremcalcTmpdtl;
import com.tlg.util.StringUtil;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class RenewalDataProcessingServiceImpl implements RenewalDataProcessingService {
	/* mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業  start */

	private static final Logger logger = Logger.getLogger(RenewalDataProcessingServiceImpl.class);
	
	//mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
	private FirVerifyDatasService firVerifyDatasService;
	
	@Override
	public Map<String, Object> basicDataCheck(Aps016DetailVo aps016DetailVo, String userId) throws SystemException, Exception{
		Map<String,Object> returnMap = new LinkedHashMap<>(); 
		StringBuilder errMsg = new StringBuilder();
		Map<String,String> resultMap = new HashMap<>();
		int structureValid = 0; 
		
		//續保單號檢核
		String oldpolicyno = aps016DetailVo.getOldpolicyno();
		if(StringUtil.isSpace(oldpolicyno) || oldpolicyno.length()!=14) {
			errMsg.append("續保單號必填且長度為需為14碼;");
		}else if(!"18".equals(oldpolicyno.substring(0,2))) {
			errMsg.append("續保單號需為18開頭;");
		}
		
		//受理編號檢核
		String orderseq = aps016DetailVo.getOrderseq();
		if(StringUtil.isSpace(orderseq) || orderseq.length()!=15) {
			errMsg.append("受理編號必填且長度需為15碼;");
		}else if(!"118".equals(orderseq.substring(0,3))) {
			errMsg.append("受理編號需為118開頭;");
		}else if(!"C".equals(orderseq.substring(9,10))||!"C".equals(orderseq.substring(10,11))) {
			errMsg.append("受理編號第10碼及第11碼都需為C;");
		//mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
		}else if(firVerifyDatasService.checkIsExistInsured(orderseq)){
			errMsg.append("受理編號於新核心內已有一張生效中的要保書;");
		}
		
		//保單生效日檢核
		String startdate = aps016DetailVo.getStartdateCheck();
		Date transStartdate = null;
		String formatStartdate = "";
		if(!StringUtil.isSpace(startdate)){
			transStartdate = checkDate(startdate);
			if(transStartdate == null) {
			errMsg.append("保單生效日需為合理日期;");			
			}else {
				formatStartdate = new SimpleDateFormat("yyyyMMdd").format(transStartdate);
				structureValid++ ;
			}
		}else {
			errMsg.append("保單生效日必填;");	
		}
		
		//業務員登錄證號檢核
		String extracomcode = aps016DetailVo.getExtracomcode();
		String handleridentifynumber = aps016DetailVo.getHandleridentifynumber();
		if(StringUtil.isSpace(handleridentifynumber)) {
			errMsg.append("業務員登錄證號必填;");
		}else {
			//mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
			resultMap = firVerifyDatasService.findPrpyddagentByParams(handleridentifynumber, "I99065");
			if(!resultMap.containsKey("errMsg")) {
				if(StringUtil.isSpace(extracomcode)) {
					extracomcode = resultMap.get("extracomcode");					
				}
				returnMap.put("extracomcode",extracomcode);
			}else {
				errMsg.append(resultMap.get("errMsg"));
			}
		}
		
		//分行代號檢核
		if(!StringUtil.isSpace(extracomcode)) {
			//mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
			resultMap = firVerifyDatasService.checkExtracomcode(extracomcode, "I99065");
			if(resultMap.containsKey("handler1code")){
				returnMap.put("handler1code", resultMap.get("handler1code"));
				returnMap.put("extracomname", resultMap.get("extracomname"));
				if(resultMap.containsKey("comcode")) {
					returnMap.put("comcode", resultMap.get("comcode"));
				}else {
					errMsg.append(resultMap.get("errMsg"));
				}
			}else {
				errMsg.append(resultMap.get("errMsg"));
			}			
		}else {
			errMsg.append("分行代號必填;");
		}
		
		//郵遞區號檢核
		String addresscode = aps016DetailVo.getAddresscode();
		if(!StringUtil.isSpace(addresscode)) {
			String addressname = findPrpdNewCode("PostAddress",addresscode);
			if(StringUtil.isSpace(addressname)) {
				errMsg.append("查無郵遞區號;");
			}else {
				returnMap.put("addressname", addressname);
			}
		}else{
			errMsg.append("郵遞區號必填;");
		}
		
		//外牆檢核
		String wallmaterial = aps016DetailVo.getWallmaterial();
		String wallname = "";
		if(!StringUtil.isSpace(wallmaterial)) {
			wallname = findPrpdNewCode("WallMaterial",wallmaterial);
			if(StringUtil.isSpace(wallname)) {
				errMsg.append("外牆輸入錯誤;");
			}else {
				returnMap.put("wallname", wallname);
				structureValid++;
			}
		}else{
			errMsg.append("外牆必填;");
		}
		
		//屋頂檢核
		String roofmaterial = aps016DetailVo.getRoofmaterial();
		String roofname ="";
		if(!StringUtil.isSpace(roofmaterial)) {
			roofname = findPrpdNewCode("RoofMaterial",roofmaterial);
			if(StringUtil.isSpace(roofname)) {
				errMsg.append("屋頂輸入錯誤;");
			}else {
				returnMap.put("roofname", roofname);
				structureValid++;
			}
		}else{
			errMsg.append("屋頂必填;");
		}
		
		//總樓層數檢核
		String sumfloors = aps016DetailVo.getSumfloors();
		String highrisefee = "";
		int floorNum = 0;
		if(StringUtil.isSpace(sumfloors) || !StringUtil.isNumeric(sumfloors) || Integer.parseInt(sumfloors) == 0) {
			errMsg.append("總樓層數必填且應為正整數、數字應>0;");
		}else {
			floorNum = Integer.parseInt(sumfloors);
			if(floorNum >= 25){
				highrisefee = "15";
			}else if(floorNum >= 15) {
				highrisefee = "10";
			}else {
				highrisefee = "0";
			}
			structureValid++;
			returnMap.put("highrisefee", highrisefee);
		}
		
		//「建築等級」處理
		String structure = "";
		if(structureValid == 4 ) {
			//mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
			Map<String,String> structMap = firVerifyDatasService.findPrpdPropStructByParams(wallmaterial, roofmaterial, formatStartdate, floorNum);
			if(!structMap.isEmpty()) {
				structure = structMap.get("structureno");
				returnMap.put("structure", structure);
				//mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業 
				returnMap.put("structureText", wallname+roofname+floorNum+"層樓"+structMap.get("structureText"));
			}else {
				errMsg.append("查無建物等級，無法計算保費;");
			}
		}

		//建築年分檢核
		String buildyears = aps016DetailVo.getBuildyears();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR)-1911;
		if(StringUtil.isSpace(buildyears) || !StringUtil.isNumeric(buildyears) || 
				Integer.parseInt(buildyears) > year || Integer.parseInt(buildyears) < 1) {
			errMsg.append("建築年分必填且應應介於1~今年之間;");
		}
		
		//火險保額檢核
		String amountF = aps016DetailVo.getAmountF().replace(",", "");
		if(!checkIsNum(amountF)) {
			errMsg.append("火險保額必填且必須為數字;");
		}
		
		//地震險保額檢核
		String amountQ = aps016DetailVo.getAmountQ().replace(",", "");
		if(!checkIsNum(amountQ) || Integer.parseInt(amountQ) < 10000 || Integer.parseInt(amountQ) > 1500000) {
			errMsg.append("地震險保額必填且保額應在10000~1500000之間;");
		}
		
		//火險保費檢核
		String premiumF = aps016DetailVo.getPremiumF().replace(",", "");
		if(!checkIsNum(premiumF)) {
			errMsg.append("火險保費必填且必須為數字;");
		}
		
		//火險保費檢核
		String premiumQ = aps016DetailVo.getPremiumQ().replace(",", "");
		if(!checkIsNum(premiumQ)) {
			errMsg.append("地震險保費必填且必須為數字;");
		}
		
		//要保人資料檢核
		//證照號碼
		String identifynumber2 = aps016DetailVo.getIdentifynumber2();
		Map<String, String> idMap = new HashMap<>();
		String insurednature2 = "";
		if(StringUtil.isSpace(identifynumber2)) {
			errMsg.append("要保人-證照號碼必填;");
		}else {
			//mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
			idMap = firVerifyDatasService.verifyID(identifynumber2);
			if(idMap.containsKey("errMsg")) {
				errMsg.append("要保人-"+idMap.get("errMsg"));
			}else {
				insurednature2 = idMap.get("insuredNature");
				returnMap.put("insurednature2", insurednature2);
				returnMap.put("identifytype2", idMap.get("idType"));
			}	
		}
		
		//行動電話
		String mobile2 = aps016DetailVo.getMobile2();
		if(!StringUtil.isSpace(mobile2) && !checkMobile(mobile2)) {
			errMsg.append("要保人-行動電話需為09開頭共10位數字;");
		}
		//郵遞區號
		String postcode2 = aps016DetailVo.getPostcode2();
		if(!StringUtil.isSpace(postcode2)) {
			String postname2 = findPrpdNewCode("PostAddress",postcode2);
			if(StringUtil.isSpace(postname2)) {
				errMsg.append("要保人-查無郵遞區號;");
			}else {
				returnMap.put("postname2", postname2);
			}
		}else{
			errMsg.append("要保人-郵遞區號必填;");
		}
		
		//通訊地址
		if(StringUtil.isSpace(aps016DetailVo.getPostaddress2())) {
			errMsg.append("要保人-通訊地址必填;");
		}
		//居住地/註冊地
		String domicile2 = aps016DetailVo.getDomicile2();
		if(!StringUtil.isSpace(domicile2)) {
			if(StringUtil.isSpace(findPrpdNewCode("countryCName",domicile2))) {
				errMsg.append("要保人-查無居住地/住冊地;");
			}
		}else {
			errMsg.append("要保人-居住地/住冊地必填;");
		}
		//國籍
		String countryename2 = aps016DetailVo.getCountryename2();
		if(!StringUtil.isSpace(countryename2)) {
			if(StringUtil.isSpace(findPrpdNewCode("countryCName",countryename2))) {
				errMsg.append("要保人-查無國籍;");
			}
		}else {
			errMsg.append("要保人-國籍必填;");
		}
		//高危險職業
		String ishighdengeroccupation2 = aps016DetailVo.getIshighdengeroccupation2();
		if(StringUtil.isSpace(ishighdengeroccupation2)) {
			errMsg.append("要保人-是否高危職業必填;");
		}
		//生日/註冊日
		String birthday2 = aps016DetailVo.getBirthday2Check();
		if(StringUtil.isSpace(birthday2)) {
			errMsg.append("要保人-生日/註冊日必填;");
		}else {
			birthday2 = rocToAd(birthday2,"/");
			Date transBirthday2 = checkDate(birthday2);
			if(transBirthday2 == null || !compareDate(transBirthday2)) {
				errMsg.append("要保人-生日/註冊日需為合理日期且不可大於系統日期;");
			}
		}
		//法人代表人
		String headname2 = aps016DetailVo.getHeadname2();
		if(StringUtil.isSpace(headname2) && insurednature2.equals("4")) {
			errMsg.append("要保人-若客戶類型為法人時，法人代表人必需輸入;");
		}
		
		//被保險人資料檢核
		//證照號碼
		String identifynumber1 = aps016DetailVo.getIdentifynumber1();
		String insurednature1 = "";
		//mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業
		if(StringUtil.isSpace(identifynumber1)) {
			errMsg.append("被保險人-證照號碼必填;");
		}else {
			idMap.clear();
			//mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
			idMap = firVerifyDatasService.verifyID(identifynumber1);
			if(idMap.containsKey("errMsg")) {
				errMsg.append("被保險人-"+idMap.get("errMsg"));
			}else {
				insurednature1 = idMap.get("insuredNature");
				returnMap.put("insurednature1", insurednature1);
				returnMap.put("identifytype1", idMap.get("idType"));
			}
		}	
		//行動電話
		String mobile1 = aps016DetailVo.getMobile1();
		if(!StringUtil.isSpace(mobile1) && !checkMobile(mobile1)) {
			errMsg.append("被保險人-行動電話需為09開頭共10位數字;");
		}
		
		//郵遞區號
		String postcode1 = aps016DetailVo.getPostcode1();
		if(!StringUtil.isSpace(postcode1)) {
			String postname1 = findPrpdNewCode("PostAddress",postcode1);
			if(StringUtil.isSpace(postname1)) {
				errMsg.append("被保險人-查無郵遞區號;");
			}else {
				returnMap.put("postname1", postname1);
			}
		}else{
			errMsg.append("被保險人-郵遞區號必填;");
		}
		
		//通訊地址
		if(StringUtil.isSpace(aps016DetailVo.getPostaddress1())) {
			errMsg.append("被保險人-通訊地址必填;");
		}
		//居住地/註冊地
		String domicile1 = aps016DetailVo.getDomicile1();
		if(!StringUtil.isSpace(domicile1)) {
			if(StringUtil.isSpace(findPrpdNewCode("countryCName",domicile1))) {
				errMsg.append("被保險人-查無居住地/住冊地;");
			}
		}else {
			errMsg.append("被保險人-居住地/住冊地必填;");
		}
		//國籍
		String countryename1 = aps016DetailVo.getCountryename1();
		if(!StringUtil.isSpace(countryename1)) {
			if(StringUtil.isSpace(findPrpdNewCode("countryCName",countryename1))) {
				errMsg.append("被保險人-查無國籍;");
			}
		}else {
			errMsg.append("被保險人-國籍必填;");
		}
		//高危險職業
		String ishighdengeroccupation1 = aps016DetailVo.getIshighdengeroccupation1();
		if(StringUtil.isSpace(ishighdengeroccupation1)) {
			errMsg.append("被保險人-是否高危職業必填;");
		}
		
		//生日/註冊日
		String birthday1 = aps016DetailVo.getBirthday1Check();
		if(StringUtil.isSpace(birthday1)) {
			errMsg.append("被保險人-生日/註冊日必填;");
		}else {
			birthday1 = rocToAd(birthday1,"/");
			Date transBirthday1 = checkDate(birthday1);
			if(transBirthday1 == null || !compareDate(transBirthday1)) {
				errMsg.append("被保險人-生日/註冊日需為合理日期且不可大於系統日期;");
			}
		}
		
		//法人代表人
		String headname1 = aps016DetailVo.getHeadname1();
		if(StringUtil.isSpace(headname1) && insurednature1.equals("4")) {
			errMsg.append("被保險人-若客戶類型為法人時，法人代表人必需輸入;");
		}
		
		//其他檢核
		if(StringUtil.isSpace(mobile2) && StringUtil.isSpace(aps016DetailVo.getPhonenumber2())) {
			errMsg.append("要保人-市話與行動電話應擇一輸入;");
		}
		
		if(StringUtil.isSpace(mobile1) && StringUtil.isSpace(aps016DetailVo.getPhonenumber1())) {
			errMsg.append("被保險人-市話與行動電話應擇一輸入;");
		}
		
		if(insurednature2.equals("4") && StringUtil.isSpace(aps016DetailVo.getListedcabinetcompany2())){
			errMsg.append("要保人為法人，上市櫃公司必填;");
		}
		
		if(insurednature1.equals("4") && StringUtil.isSpace(aps016DetailVo.getListedcabinetcompany1())){
			errMsg.append("被保險人為法人，上市櫃公司必填;");
		}
		
		if(!StringUtil.isSpace(errMsg.toString())) {
			returnMap.put("errMsg",errMsg.toString());
			return returnMap;
		}
		StringBuilder warnMsg = new StringBuilder();
		
		/*mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 start*/
		//複保險查詢檢核
		FirEqFundQueryVo firEqFundQueryVo = new FirEqFundQueryVo();
		String endDate = String.valueOf(Integer.parseInt(startdate.substring(0, 4))+1)+startdate.substring(4);
		firEqFundQueryVo.setStartDate(startdate);
		firEqFundQueryVo.setEndDate(endDate);
		firEqFundQueryVo.setPostcode(addresscode);
		firEqFundQueryVo.setAddress(aps016DetailVo.getAddressdetailinfo());
		firEqFundQueryVo.setSourceType("BOP_RN");
		firEqFundQueryVo.setSourceUserid(userId);
		FirVerifyVo firVerifyVo = firVerifyDatasService.queryDoubleInsVerify(firEqFundQueryVo);
		returnMap.put("dquakeStatus", firVerifyVo.getDquakeStatus());
		returnMap.put("dquakeNo", firVerifyVo.getDquakeNo());
		warnMsg.append(firVerifyVo.getWarnMsg());
		//複保險查詢檢核 end
		/*mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程 end*/
		
		//保額檢核start
		BigDecimal amountFNum = new BigDecimal(amountF);
		BigDecimal amountQNum = new BigDecimal(amountQ).multiply(BigDecimal.valueOf(0.0001)).setScale(0, RoundingMode.CEILING).multiply(new BigDecimal(10000));
		FirAmountWsParamVo firWsParamVo = new FirAmountWsParamVo();
		firWsParamVo.setSourceType("BOPRN");
		firWsParamVo.setSourceUser(userId);
		firWsParamVo.setCalcType("1");
		firWsParamVo.setCalcDate(formatStartdate);
		firWsParamVo.setChannelType("20");
		firWsParamVo.setPostcode(addresscode);
		firWsParamVo.setWallno(wallmaterial);
		firWsParamVo.setRoofno(roofmaterial);
		firWsParamVo.setSumfloors(sumfloors);
		firWsParamVo.setBuildarea(aps016DetailVo.getBuildarea());
		firWsParamVo.setDecorFee("0");
		try {
			//mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
			FirPremcalcTmp firPremcalcTmp = firVerifyDatasService.firAmountCal(firWsParamVo);
			if(firPremcalcTmp != null) {
				returnMap.put("oidFirPremcalcTmp", firPremcalcTmp.getOid().toString());
				if("Y".equals(firPremcalcTmp.getReturnType())) {
					BigDecimal fsAmt = firPremcalcTmp.getFsAmt();
					BigDecimal wsQuakeAmt = firPremcalcTmp.getEqAmt();
					returnMap.put("wsFirAmt", fsAmt.toString());
					returnMap.put("wsQuakeAmt", wsQuakeAmt.toString());
					BigDecimal maxAmt = firPremcalcTmp.getFsMaxAmt();
					String famtStatus = "";
					String qamtStatus = "";
					//判斷火險保額是否足額
					if(amountFNum.compareTo(new BigDecimal(0))>0 && amountFNum.compareTo(maxAmt)>0) {
						famtStatus = "3";
						errMsg.append("火險超額(上限保額："+maxAmt.toString()+")；");
					}else if(amountFNum.compareTo(new BigDecimal(0))>0 && amountFNum.compareTo(fsAmt)<0) {
						famtStatus = "2";
						warnMsg.append("火險不足額(建議保額："+fsAmt.toString()+")；");
					}else if(amountFNum.compareTo(new BigDecimal(0))>0) {
						famtStatus = "1";//足額
					}
					
					//判斷地震保額是否足額
					if(amountQNum.compareTo(wsQuakeAmt)>0) {
						qamtStatus = "3";
						errMsg.append("地震險超額(保額："+wsQuakeAmt+")；");
					}else if(amountQNum.compareTo(wsQuakeAmt)<0) {
						qamtStatus = "2";
						errMsg.append("地震險不足額(保額："+wsQuakeAmt+")；");
					}else if(amountQNum.compareTo(wsQuakeAmt)==0) {
						qamtStatus = "1";//足額
					}
					
					returnMap.put("famtStatus", famtStatus);
					returnMap.put("qamtStatus", qamtStatus);
				}else {
					errMsg.append("保額計算WS失敗"+firPremcalcTmp.getReturnMsg()+";");
				}
			}
		}catch(Exception e) {
			logger.error(e);
			warnMsg.append("檢核-保額計算WS無回應；");
		}
		//保額檢核end
		
		/*mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程 start*/
		//地址正確性檢核 start
		FirAddressCheckVo firAddressCheckVo = new FirAddressCheckVo();
		firAddressCheckVo.setZip(addresscode);
		firAddressCheckVo.setAddress(aps016DetailVo.getAddressdetailinfo());
		firAddressCheckVo.setStructure(structure);
		firAddressCheckVo.setFloors(sumfloors);
		firAddressCheckVo.setBuildyears(aps016DetailVo.getBuildyears());//mantis：FIR0613，處理人員：DP0706，需求單編號：FIR0613_住火_火險地址檢核_呼叫WS程式新增傳入年份參數
		firVerifyVo = firVerifyDatasService.addressVerify(firAddressCheckVo);
		returnMap.put("addrStatus", firVerifyVo.getAddrStatus());
		returnMap.put("addrDetail", firVerifyVo.getAddrDetail());
		warnMsg.append(firVerifyVo.getWarnMsg());
		if(!"".equals(firVerifyVo.getAddrDetail())) {
			warnMsg.append(firVerifyVo.getAddrDetail());
		}
		//地址正確性檢核 end
		/*mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 end*/
		
		//保費驗證start
		FirPremWsParamVo firPremWsParamVo = new FirPremWsParamVo();
		firPremWsParamVo.setSourceType("BOPRN");
		firPremWsParamVo.setSourceUser(userId);
		firPremWsParamVo.setCalcType("2");
		firPremWsParamVo.setCalcDate(formatStartdate);
		firPremWsParamVo.setChannelType("20");
		
		ArrayList<FirInsPremVo> firInsPremVoList = new  ArrayList<FirInsPremVo>();
		if(amountQNum.compareTo(new BigDecimal(0))>0) {
			FirInsPremVo firInsPremVoQ = new FirInsPremVo();
			firInsPremVoQ.setRiskcode("F02");
			firInsPremVoQ.setKindcode("FR2");
			firInsPremVoQ.setPara01(amountQ);
			firInsPremVoList.add(firInsPremVoQ);
		}
		
		if(amountFNum.compareTo(new BigDecimal(0))>0) {
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
		//mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
		FirPremcalcTmp firPremcalcTmp = firVerifyDatasService.firPremCal(firPremWsParamVo);
		
		returnMap.put("oidFirPremcalcTmp2",firPremcalcTmp.getOid());
		if(firPremcalcTmp.getReturnType().equals("N")) {
			errMsg.append("保費計算失敗，請聯繫系統負責人："+firPremcalcTmp.getReturnMsg());
			returnMap.put("errMsg", errMsg.toString());
			returnMap.put("warnMsg", warnMsg.toString());
			return returnMap;
		}
		BigDecimal premiumFnum = new BigDecimal(premiumF);
		BigDecimal premiumQnum = new BigDecimal(premiumQ);
		BigDecimal tmpPremiumT = premiumFnum.add(premiumQnum);
		BigDecimal premiumTnum = null;
		String premiumT = aps016DetailVo.getPremiumT().replace(",", "");
		if(!StringUtil.isSpace(premiumT)) {
			premiumTnum = new BigDecimal(premiumT);
		}
		if(premiumTnum.compareTo(tmpPremiumT) != 0) {
			warnMsg.append("畫面輸入的保費與板信轉檔總保費不符("+premiumT+");");
		}
		for(FirPremcalcTmpdtl firPremcalcTmpdtl : firPremcalcTmp.getFirPremcalcTmpdtlList()) {
			if("FR2".equals(firPremcalcTmpdtl.getKindcode()) && premiumQnum.compareTo(firPremcalcTmpdtl.getPremium()) != 0) {
				errMsg.append("畫面輸入的地震險保費與保費計算結果不符("+firPremcalcTmpdtl.getPremium()+");");
			}else if("FR3".equals(firPremcalcTmpdtl.getKindcode()) && premiumFnum.compareTo(firPremcalcTmpdtl.getPremium()) != 0) {
				errMsg.append("畫面輸入的火險保費與保費計算結果不符("+firPremcalcTmpdtl.getPremium()+");");
			}
		}
		/* mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業 start*/
		//檢查板信前期保額與我方前期保額是否一致
		if(aps016DetailVo.getAmountFLast() !=null && 
				!aps016DetailVo.getAmountFAgt().replace(",", "").equals(aps016DetailVo.getAmountFLast().toString())) {
			warnMsg.append("前期火險保額與板信轉檔火險保額不符(" + aps016DetailVo.getAmountFAgt() + ");");
		}
		/* mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業 end*/
		
		//保費驗證end
		returnMap.put("errMsg", errMsg.toString());
		returnMap.put("warnMsg", warnMsg.toString());
		return returnMap;
		
	}
	
	@Override
	public String theFinalDataCheck(Aps016DetailVo aps016DetailVo) throws Exception {
		StringBuilder errMsg = new StringBuilder();
		String buildarea = aps016DetailVo.getBuildarea();
		if(StringUtil.isSpace(buildarea) || !StringUtil.isNum(buildarea) || countDecimal(buildarea)>2 
				|| new BigDecimal(buildarea).compareTo(BigDecimal.ZERO) <= 0){
				errMsg.append("坪數必填且需>0，需為數字且小數點至多2位;");
		}
		if(StringUtil.isSpace(aps016DetailVo.getInsuredname2())){
			errMsg.append("要保人-姓名必填");
		}
		if(StringUtil.isSpace(aps016DetailVo.getInsuredname1())){
			errMsg.append("被保險人-姓名必填");
		}
		
		return errMsg.toString();
	}
	
	public boolean checkIsNum(String str) throws Exception {
		if(StringUtil.isSpace(str) || !StringUtil.isNumeric(str)) {
			return false;
		}
		return true;
	}
	
	public boolean checkMobile(String mobile) throws Exception {
		if(!StringUtil.isNumeric(mobile) || !mobile.substring(0,2).equals("09") || mobile.length() != 10) {
			return false;
		}
		return true;
	}
	
	private Date checkDate(String date) {
		if(date.length()!=10) {
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
	
	private String findPrpdNewCode(String codetype,String codecode) {
		String codecname = "";
		try {
			//mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
			codecname = firVerifyDatasService.findPrpdNewCode(codetype,codecode);
		} catch (SystemException e) {
			e.printStackTrace();
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return codecname;
	}
	
	private boolean compareDate(Date date){
		if(new Date().compareTo(date) < 0) {
			return false;
		}
		return true;
	}
	
	//取小數後有幾位
	private int countDecimal(String str){
		int count = 0;
		if(str.contains(".")) {
			str = str.substring(str.indexOf(".")+1);
			count = str.length();
		}
		return count;
	}
	
	public String rocToAd(String rocDate, String delimiter) {
		String[] arrDate = rocDate.split(delimiter);
		if(arrDate.length >= 3) {
			return Integer.parseInt(arrDate[0]) + 1911 + "/" + arrDate[1] + "/" + arrDate[2] ;
		}
		return "";
	}

	/*mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 start*/
	public FirVerifyDatasService getFirVerifyDatasService() {
		return firVerifyDatasService;
	}

	public void setFirVerifyDatasService(FirVerifyDatasService firVerifyDatasService) {
		this.firVerifyDatasService = firVerifyDatasService;
	}
	/*mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 end*/
}
