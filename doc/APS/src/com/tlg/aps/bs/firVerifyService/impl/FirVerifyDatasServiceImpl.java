/**
 * mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
 * 火險相關檢核Service
 * 包含欄位驗證、webService
 */
package com.tlg.aps.bs.firVerifyService.impl;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firVerifyService.FirVerifyDatasService;
import com.tlg.aps.vo.Aps016DetailVo;
import com.tlg.aps.vo.FirAddressCheckVo;
import com.tlg.aps.vo.FirAddressCompareVo;
import com.tlg.aps.vo.FirAddressRuleObj;
import com.tlg.aps.vo.FirAmountWsParamVo;
import com.tlg.aps.vo.FirEqFundQueryVo;
import com.tlg.aps.vo.FirInsPremVo;
import com.tlg.aps.vo.FirPahsinRenewalVo;
import com.tlg.aps.vo.FirPremWsParamVo;
import com.tlg.aps.vo.FirVerifyVo;
import com.tlg.aps.vo.RuleReponseVo;
import com.tlg.aps.vo.VerifyIdVo;
import com.tlg.aps.webService.addressCheckService.client.AddressCheckService;
import com.tlg.aps.webService.firCalAmountService.client.FirAmountService;
import com.tlg.aps.webService.firCalPremService.client.FirPremService;
import com.tlg.aps.webService.firDoubleInsService.client.FirDoubleInsService;
import com.tlg.aps.webService.firRuleService.client.RuleCheckService;
import com.tlg.dms.entity.PrpdNewCode;
import com.tlg.dms.service.PrpdNewCodeService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtSalesMapping;
import com.tlg.prpins.entity.FirAgtrnAs400Data;
import com.tlg.prpins.entity.FirPremcalcTmp;
import com.tlg.prpins.entity.FirPremcalcTmpdtl;
import com.tlg.prpins.entity.PrpdPropStruct;
import com.tlg.prpins.entity.Prpduser;
import com.tlg.prpins.entity.Prptmain;
import com.tlg.prpins.entity.Prpyddagent;
import com.tlg.prpins.service.FirAgtSalesMappingService;
import com.tlg.prpins.service.FirAgtrnAs400DataService;
import com.tlg.prpins.service.PrpcinsuredService;
import com.tlg.prpins.service.PrpcmainService;
import com.tlg.prpins.service.PrpdPropStructService;
import com.tlg.prpins.service.PrpduserService;
import com.tlg.prpins.service.PrptmainService;
import com.tlg.prpins.service.PrpyddagentService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.JsonUtil;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.util.WebserviceObjConvert;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirVerifyDatasServiceImpl implements FirVerifyDatasService {
	
	private static final Logger logger = Logger.getLogger(FirVerifyDatasServiceImpl.class);
	private PrptmainService prptmainService;
	private FirAgtSalesMappingService firAgtSalesMappingService;
	private PrpduserService prpduserService;
	private PrpdNewCodeService prpdNewCodeService;
	private PrpdPropStructService prpdPropStructService;
	private FirDoubleInsService clientFirDoubleInsService;
	private FirAmountService clientFirAmountService;
	private AddressCheckService clientAddressCheckService;
	private FirPremService clientFirCalPremService;
	private PrpyddagentService prpyddagentService;
	private FirAgtrnAs400DataService firAgtrnAs400DataService;
	private ConfigUtil configUtil;
	private RuleCheckService clientRuleCheckService;
	
	private Map<String,String> structureMap = new LinkedHashMap<>();
	
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start*/
	private PrpcmainService prpcmainService;
	private PrpcinsuredService prpcinsuredService;
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end*/
	
	//檢核要保書是否已存在新核心
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkIsExistInsured(String orderseq) throws Exception {
		Map<String,Object> params = new HashMap<>();
		params.put("riskcode", "F02");
		params.put("orderseq", orderseq);
		Result result = prptmainService.findPrptmainByParams(params);
		if(result.getResObject()!=null) {
			List<Prptmain> resultList = (List<Prptmain>) result.getResObject();
			for(Prptmain prptmain : resultList) {
				if(!prptmain.getOthflag().substring(3,4).contains("2")) {
					return true;
				}
			}
		}
		return false;
	}
	
	//分行代號檢核
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,String> checkExtracomcode(String branchNo, String businessnature) throws Exception {
		Map<String,String> returnMap = new HashMap<>();
		Map<String,Object> params = new HashMap<>();
		params.put("branchNo", branchNo);
		params.put("businessnature", businessnature);
		params.put("deleteFlag", "N");
		Result result = firAgtSalesMappingService.findFirAgtSalesMappingByParams(params);
		if(result.getResObject()!=null) {
			List<FirAgtSalesMapping> firAgtSalesMappingList = (List<FirAgtSalesMapping>) result.getResObject();
			returnMap.put("handler1code", firAgtSalesMappingList.get(0).getHandler1code());
			returnMap.put("extracomname", firAgtSalesMappingList.get(0).getBranchName());
			params.clear();
			params.put("usercode", firAgtSalesMappingList.get(0).getHandler1code());
			params.put("validstatus", "1");
			result = prpduserService.findPrpduserByParams(params);
			if(result.getResObject()!=null) {
				List<Prpduser> prpduserList = (List<Prpduser>) result.getResObject();
				returnMap.put("comcode", prpduserList.get(0).getComcode());
			}else {
				returnMap.put("errMsg", "分行代號查無服務人員或服務人員已失效;");
			}
		}else {
			returnMap.put("errMsg", "分行代號未設定對應服務人員，請先至對應作業進行設定;");
		}
		
		return returnMap;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String findPrpdNewCode(String codetype,String codecode) throws Exception {
		Map<String,String> params = new HashMap<>();
		params.put("codetype", codetype);
		params.put("codecode", codecode);
		String codecname = "";
		Result result = prpdNewCodeService.findPrpdNewCodeByParams(params);
		if(result.getResObject()!=null) {
			List<PrpdNewCode> prpdNewCode = (List<PrpdNewCode>) result.getResObject();
			codecname = prpdNewCode.get(0).getCodecname();
		}
		return codecname;
	}
	
	//驗證身份證字號
	@Override
	public Map<String,String> verifyID(String id) throws Exception {
		Map<String,String> resultMap = new HashMap<>();
		
		String url = configUtil.getString("verifyIDUrl");
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			String encodedURLID = URLEncoder.encode(id, "UTF-8");
			String httpURL = url + encodedURLID;
	        HttpGet getRequest = new HttpGet(httpURL);
	        getRequest.addHeader("accept", "application/json");
	        HttpResponse response = httpClient.execute(getRequest);
	        int statusCode = response.getStatusLine().getStatusCode();
	        logger.info("statusCode = " + statusCode);
	        if (statusCode != 200) {
	            throw new RuntimeException("Failed with HTTP error code : " + statusCode);
	        }
	        HttpEntity httpEntity = response.getEntity();
	        String jsonString = EntityUtils.toString(httpEntity);
	        VerifyIdVo verifyIdVo = (VerifyIdVo)JsonUtil.getDTO(jsonString, VerifyIdVo.class);
	        
	        if(!verifyIdVo.getCode().equals("S0000")) {
				resultMap.put("errMsg",verifyIdVo.getCode()+verifyIdVo.getMsg()+";");
				return resultMap;
			}
			//檢核為法人或自然人
			if(verifyIdVo.getInsuredType().equals("1")) {
				resultMap.put("insuredNature", "3");
			}else {
				resultMap.put("insuredNature", "4");
			}
			
			String identifyType = verifyIdVo.getIdentifyType();
			if(identifyType.equals("01")||identifyType.equals("05")||identifyType.equals("60")) {
				resultMap.put("idType", verifyIdVo.getIdentifyType());
			}else {
				resultMap.put("idType", "04");
			}
			/** mantis：FIR0620，處理人員：CD094，需求單編號：FIR0620 住火-台銀續保作業  start **/
			String gender = verifyIdVo.getGender();
			resultMap.put("gender", gender);
			/** mantis：FIR0620，處理人員：CD094，需求單編號：FIR0620 住火-台銀續保作業  end**/
		}
		return resultMap;
	}
	
	//建築等級處理
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,String> findPrpdPropStructByParams(String wallno, String roofno, String startdate,int sumfloors) throws Exception {
		Map<String,String> params = new HashMap<>();
		params.put("wallno", wallno);
		params.put("roofno", roofno);
		params.put("calcDate", startdate);
		Result result = prpdPropStructService.findPrpdPropStructByParams(params);
		Map<String,String> returnMap = new HashMap<>();
		if(result.getResObject()!=null) {
			//以外牆、屋頂、樓層數判斷建築等級
			String[] wallmateriaArr = {"01","02","03","27","28","30","32","35","36","39","40","41","42"};
			List<PrpdPropStruct> prpdPropStructList = (List<PrpdPropStruct>) result.getResObject();
			String structureno = prpdPropStructList.get(0).getStructureno();
			if(Arrays.asList(wallmateriaArr).contains(wallno) && sumfloors >= 14 && (roofno.equals("50") || roofno.equals("70"))) {
				structureno = "1";
			}else if(Arrays.asList(wallmateriaArr).contains(wallno) && sumfloors < 14 && (roofno.equals("50") || roofno.equals("70"))) {
				structureno = "2";
			}
			String structureText = getStructureMap().get(structureno);
			returnMap.put("structureno", structureno);
			returnMap.put("structureText", structureText);
		}
		return returnMap;
	}
	
	/*複保險查詢檢核*/
	@Override
	public FirEqFundQueryVo queryDoubleIns(FirEqFundQueryVo firEqFundQueryVo) throws Exception {
		String soapxml = WebserviceObjConvert.convertObjToBase64Str(FirEqFundQueryVo.class, firEqFundQueryVo);
		String returnValue = clientFirDoubleInsService.queryDoubleIns(soapxml);
		byte[] decryptedByteArray = Base64.decodeBase64(returnValue);
		String tmp = new String(decryptedByteArray, StandardCharsets.UTF_8);
		logger.info("queryDoubleIns returnValue : " + tmp);
		firEqFundQueryVo = new FirEqFundQueryVo();
		firEqFundQueryVo = (FirEqFundQueryVo)WebserviceObjConvert.convertBase64StrToObj(returnValue, FirEqFundQueryVo.class);
		return firEqFundQueryVo;
	}
	
	@Override
	public FirVerifyVo queryDoubleInsVerify(FirEqFundQueryVo firEqFundQueryVo) {
		String dquakeStatus = "";
		String dquakeNo = "";
		String warnMsg = "";
		FirVerifyVo verifyVo = new FirVerifyVo();
		try {
			firEqFundQueryVo = queryDoubleIns(firEqFundQueryVo);
			if(firEqFundQueryVo != null) {
				dquakeNo = firEqFundQueryVo.getRepeatPolicyno();
				String repeatCode = firEqFundQueryVo.getRepeatCode();
				if(repeatCode.equals("0")) {
					dquakeStatus = "S";
				}else if(repeatCode.equals("1")) {
					dquakeStatus = "D";
					warnMsg = "複保險；";
				}else {
					dquakeStatus = "E";
					warnMsg = "複保險檢核異常:"+firEqFundQueryVo.getRepeatMsg()+";";
				}
			}
		}catch(Exception e) {
			logger.error("webService查詢複保險異常", e);
			dquakeStatus = "E";
			warnMsg = "查詢複保險出現異常;";
		}
		verifyVo.setDquakeStatus(dquakeStatus);
		verifyVo.setDquakeNo(dquakeNo);
		verifyVo.setWarnMsg(warnMsg);
		return verifyVo;
	}
	
	/*保額檢核*/
	@Override
	public FirPremcalcTmp firAmountCal(FirAmountWsParamVo firWsParamVo) throws Exception {
		String soapxml = WebserviceObjConvert.convertObjToBase64Str(FirAmountWsParamVo.class, firWsParamVo);
		String returnValue = clientFirAmountService.firAmountCal(soapxml);
		byte[] decryptedByteArray = Base64.decodeBase64(returnValue);
		String tmp = new String(decryptedByteArray, StandardCharsets.UTF_8);
		logger.info(tmp);
		return (FirPremcalcTmp)WebserviceObjConvert.convertBase64StrToObj(returnValue, FirPremcalcTmp.class);
	}
	
	/*地址正確性檢核*/
	@Override
	public FirAddressCheckVo checkAddress(FirAddressCheckVo firAddressCheckVo) throws Exception {
		String soapxml = WebserviceObjConvert.convertObjToBase64Str(FirAddressCheckVo.class, firAddressCheckVo);
		String returnValue = clientAddressCheckService.addressCheck(soapxml);
		byte[] decryptedByteArray = Base64.decodeBase64(returnValue);
		String tmp = new String(decryptedByteArray, StandardCharsets.UTF_8);
		logger.info(tmp);
		return (FirAddressCheckVo)WebserviceObjConvert.convertBase64StrToObj(returnValue, FirAddressCheckVo.class);
	}
	
	@Override
	public FirVerifyVo addressVerify(FirAddressCheckVo firAddressCheckVo) {
		String addrStatus = "";
		StringBuilder addrDetail = new StringBuilder();
		String warnMsg = "";
		FirVerifyVo verifyVo = new FirVerifyVo();
		try {
			firAddressCheckVo = checkAddress(firAddressCheckVo);
			if ("N".equals(firAddressCheckVo.getQueryResult())) {
				warnMsg = "地址WS執行異常；";
				addrStatus = "E";
			}else if("Y".equals(firAddressCheckVo.getCompareResult())) {
				addrStatus = "Y";
			}else {//地址結果有差異
				addrStatus = "F";
				List<FirAddressCompareVo> compareList = firAddressCheckVo.getFirAddressCompareVoList();
				if (!compareList.isEmpty()) {
					for (FirAddressCompareVo addressCompareVo : compareList ) {
						addrDetail.append(addressCompareVo.getPolicyNo()).append("/");
						addrDetail.append(addressCompareVo.getStructure()).append("/");
						addrDetail.append(addressCompareVo.getFloors()).append("樓").append("/");
						//mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_排程作業 
						addrDetail.append(addressCompareVo.getBuildyears()).append("年").append(";");
					}
				}
			}
		} catch (Exception e) {
			logger.error("webService查詢火險地址檢核異常", e);
			warnMsg = "地址WS無回應；";
			addrStatus = "E";
		}
		
		verifyVo.setAddrStatus(addrStatus);
		verifyVo.setAddrDetail(addrDetail.toString());
		verifyVo.setWarnMsg(warnMsg);
		return verifyVo;
	}
	
	/*保費檢核*/
	public FirPremcalcTmp firPremCal(FirPremWsParamVo firPremWsParamVo) throws Exception {
		String soapxml = WebserviceObjConvert.convertObjToBase64Str(FirPremWsParamVo.class, firPremWsParamVo);
		String returnValue = clientFirCalPremService.firPremCal(soapxml);
		byte[] decryptedByteArray = Base64.decodeBase64(returnValue);
		String tmp = new String(decryptedByteArray, StandardCharsets.UTF_8);
		logger.info(tmp);
		return (FirPremcalcTmp)WebserviceObjConvert.convertBase64StrToObj(returnValue, FirPremcalcTmp.class);
	}
	
	/*檢核業務員登錄證號、對應的業務員姓名*/
	@SuppressWarnings("unchecked")
	public Map<String, String> findPrpyddagentByParams(String agentid, String businesssource) throws Exception {
		Map<String,String> params = new HashMap<>();
		params.put("businesssource", businesssource);
		params.put("verifyremark", "1");
		params.put("agentid", agentid);
		Result result = prpyddagentService.findPrpyddagentByParams(params);
		Map<String,String> returnMap = new HashMap<>();
		if(result.getResObject() != null) {
			List<Prpyddagent> resultList = (List<Prpyddagent>) result.getResObject();
			returnMap.put("extracomcode", resultList.get(0).getSalecomcode());
			returnMap.put("extracomname", resultList.get(0).getSalecomname());
		}else {
			returnMap.put("errMsg", "業務員登錄證號不存在或無效，請與通路部聯繫確認。");
		}
		return returnMap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map findFirAgtrnAs400Data(Map<String, String> params) throws Exception {
		Result result = firAgtrnAs400DataService.findFirAgtrnAs400DataByParams(params);
		Map returnMap = new HashMap<>();
		if(result.getResObject() == null) {
			returnMap.put("errMsg", "無法取得相關資料，請聯繫商品部確認或自行補入資料。");
			return returnMap;
		}
		List<FirAgtrnAs400Data> resultList = (List<FirAgtrnAs400Data>) result.getResObject();
		if(resultList.size()>1) {
			returnMap.put("errMsg", "AS400外銀續保資料匯入異常，同一個續保單號有二筆記錄，無法帶入資料，請聯繫商品部確認或自行補入資料。");
			return returnMap;
		}
		returnMap.put("AS400Data", resultList.get(0));
		return returnMap;
	}
	
	/*稽核議題檢核*/
	@Override
	public RuleReponseVo firAddressRule(FirAddressRuleObj firAddressRuleObj) throws Exception {
		String soapxml = WebserviceObjConvert.convertObjToBase64Str(FirAddressRuleObj.class, firAddressRuleObj);
		String returnValue = clientRuleCheckService.ruleCheck(soapxml);
		byte[] decryptedByteArray = Base64.decodeBase64(returnValue);
		String tmp = new String(decryptedByteArray, StandardCharsets.UTF_8);
		logger.info(tmp);
		return (RuleReponseVo)WebserviceObjConvert.convertBase64StrToObj(returnValue, RuleReponseVo.class);
	}
	
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	//mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額
	public Map findFirAgtrnCoreData(Aps016DetailVo aps016DetailVo, String userId) throws Exception {
		
		StringBuilder warnMsg = new StringBuilder();
		
		String oldpolicyno = aps016DetailVo.getOldpolicyno();
		Map<String, String> params = new HashMap<>();
		params.put("policyno", oldpolicyno);
		Result result = prpcmainService.findForPanhsinRenewalCoreData(params);
		Map returnMap = new HashMap<>();
		if(result.getResObject() == null) {
			returnMap.put("errMsg", "無法取得相關資料，請聯繫商品部確認或自行補入資料。");
			return returnMap;
		}
		FirPahsinRenewalVo coreData = (FirPahsinRenewalVo) result.getResObject();
		//取要被保人資料
		params.put("insuredflag", "2");
		result = prpcinsuredService.findForPanhsinCoreInsured(params);
		if(result.getResObject()!=null) {
			List<FirPahsinRenewalVo> applyList = (List<FirPahsinRenewalVo>) result.getResObject();
			if(applyList.size()>1) {
				warnMsg.append("前一年度保單資料有多筆要保人，請於資料轉入核心時處理；");
			}
			
			//比對核心要保人資料
			boolean isEqual = false;
			StringBuilder coreApply = new StringBuilder();
			for(FirPahsinRenewalVo applyData : applyList) {
				if(!"".equals(coreApply.toString())) {
					coreApply.append("、");
				}
				coreApply.append(applyData.getIdentifynumber());
				if(aps016DetailVo.getIdentifynumber2().equals(applyData.getIdentifynumber())) {
					isEqual = true;
					coreData.setBirthday2(applyData.getBirthday()==null?"":adToRoc(applyData.getBirthday()));
					coreData.setPostcode2(applyData.getPostcode().length()>3?applyData.getPostcode().substring(0,3):applyData.getPostcode());
					//若比對到ID相同，比對姓名是否一致
					if(!StringUtil.isSpace(applyData.getInsuredname()) &&
							!applyData.getInsuredname().equals(aps016DetailVo.getInsuredname2())) {
						warnMsg.append("板信轉入借款人(要保人)姓名("+applyData.getInsuredname()+")與核心資料不一致；");
					}
				}
			}
			if(!isEqual) warnMsg.append("板信轉入借款人ID(要保人)與核心("+coreApply.toString()+")資料不一致；");
		}
		
		//取被保險人資料
		params.put("insuredflag", "1");
		result = prpcinsuredService.findForPanhsinCoreInsured(params);
		if(result.getResObject()!=null) {
			List<FirPahsinRenewalVo> insuredList = (List<FirPahsinRenewalVo>) result.getResObject();
			if(insuredList.size()>1) {
				warnMsg.append("前一年度保單資料有多筆被保險人，請於資料轉入核心時處理；");
			}
			
			//比對核心被保險人資料
			boolean isEqual = false;
			StringBuilder coreInsured = new StringBuilder();
			for(FirPahsinRenewalVo insuredData : insuredList) {
				if(!"".equals(coreInsured.toString())) {
					coreInsured.append("、");
				}
				coreInsured.append(insuredData.getIdentifynumber());
				if(aps016DetailVo.getIdentifynumber1().equals(insuredData.getIdentifynumber())) {
					coreData.setBirthday1(insuredData.getBirthday()==null?"":adToRoc(insuredData.getBirthday()));
					coreData.setPostcode1(insuredData.getPostcode().length()>3?insuredData.getPostcode().substring(0,3):insuredData.getPostcode());
					isEqual = true;
					//若比對到ID相同，比對姓名是否一致
					if(!StringUtil.isSpace(insuredData.getInsuredname()) &&
							!insuredData.getInsuredname().equals(aps016DetailVo.getInsuredname1())) {
						warnMsg.append("板信轉入提供人(被保險人)姓名("+insuredData.getInsuredname()+")與核心資料不一致；");
					}
				}
			}
			if(!isEqual) warnMsg.append("板信轉入提供人ID(被保險人)與核心("+coreInsured.toString()+")資料不一致；");
		}
		
		//比對核心與板信資料差異
		String strStartDate = aps016DetailVo.getStartdateCheck();
		Date startDate = dateParser(strStartDate,"yyyy/MM/dd");
		if(null != startDate && coreData.getEnddate().compareTo(startDate) != 0) {
			warnMsg.append("板信轉入保單到期日與核心("+new SimpleDateFormat("yyyy/MM/dd").format(coreData.getEnddate())+")資料不一致；");
		}
		
		if(!coreData.getAddresscode().equals(aps016DetailVo.getAddresscode())) {
			warnMsg.append("板信轉入標的物郵遞區號與核心("+coreData.getAddresscode()+")資料不一致；");
		}
		if(!coreData.getAddressdetailinfo().equals(aps016DetailVo.getAddressdetailinfo())) {
			warnMsg.append("板信轉入標的物地址與核心("+coreData.getAddressdetailinfo()+")資料不一致；");
		}
		if(!coreData.getAmtF().equals(aps016DetailVo.getAmountF().replace(",", ""))) {
			warnMsg.append("板信轉入火險保額與核心("+coreData.getAmtF()+")資料不一致；");
		}
		
		//多筆抵押權人、是否有批改處理
		if(!StringUtil.isSpace(coreData.getMortgageepcode2())){
			warnMsg.append("前一年度保單資料有多筆抵押權人，請於資料轉入核心時處理；");
		}
		if("Y".equals(coreData.getIsEndorse())){
			warnMsg.append("前一年度保單有批改紀錄；");
		}
		
		
		/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 start
		 *地震險保額須依造價表重新計算，不得直接帶入前一年度保額，火險保額則依保單生效日判斷是否需*1.14 */
		
		//先寫入前一年度保額保費資料
		coreData.setAmtFLast(coreData.getAmtF());
		coreData.setAmtQLast(coreData.getAmtQ());
		coreData.setPremFLast(coreData.getPremF());
		coreData.setPremQLast(coreData.getPremQ());

		BigDecimal quakeAmt = new BigDecimal(0);
		BigDecimal premiumT = new BigDecimal(0);//WS計算總保費
		BigDecimal premiumF = new BigDecimal(0);//WS計算火險保費
		BigDecimal premiumQ = new BigDecimal(0);//WS計算地震險保費

		//計算新年度火險保額
		String amtF = recalAmount(coreData.getAmtF(),coreData.getEnddate());
		
		//計算新年度地震險保額
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		FirAmountWsParamVo firWsParamVo = new FirAmountWsParamVo();
		firWsParamVo.setSourceType("BOPRN");
		firWsParamVo.setSourceUser(userId);
		firWsParamVo.setCalcType("1");
		firWsParamVo.setCalcDate(sdf.format(coreData.getEnddate()));
		firWsParamVo.setChannelType("20");
		firWsParamVo.setPostcode(coreData.getAddresscode());
		firWsParamVo.setWallno(coreData.getWallmaterial());
		firWsParamVo.setRoofno(coreData.getRoofmaterial());
		firWsParamVo.setSumfloors(coreData.getSumfloors());
		firWsParamVo.setBuildarea(coreData.getBuildarea());
		firWsParamVo.setDecorFee("0"); 
		try {
			FirPremcalcTmp firPremcalcTmp = firAmountCal(firWsParamVo);
			if ("Y".equals(firPremcalcTmp.getReturnType())) {
				quakeAmt = firPremcalcTmp.getEqAmt();//地震險建議保額
			} else {
				warnMsg.append("檢核-保額計算WS異常(" + firPremcalcTmp.getOid() + "):"
						+ firPremcalcTmp.getReturnMsg() + "；");
			}
		} catch (Exception e) {
			logger.error("保額計算失敗", e);
			warnMsg.append("檢核-保額計算WS無回應；");
		}
		coreData.setAmtF(amtF);
		coreData.setAmtQ(quakeAmt.toString());
		
		//依計算後的保額計算火險、地震險保費
		FirPremWsParamVo firPremWsParamVo = new FirPremWsParamVo();
		firPremWsParamVo.setSourceType("BOPRN");
		firPremWsParamVo.setSourceUser(userId);
		firPremWsParamVo.setCalcType("2");
		firPremWsParamVo.setCalcDate(sdf.format(coreData.getEnddate()));
		firPremWsParamVo.setChannelType("20");

		ArrayList<FirInsPremVo> firInsPremVoList = new ArrayList<>();
		if (new BigDecimal(amtF).compareTo(new BigDecimal(0)) > 0) {
			FirInsPremVo firInsPremVoF = new FirInsPremVo();
			firInsPremVoF.setRiskcode("F02");
			firInsPremVoF.setKindcode("FR3");
			firInsPremVoF.setParaType("1");
			firInsPremVoF.setPara01(amtF);
			firInsPremVoF.setPara02(coreData.getWallmaterial());
			firInsPremVoF.setPara03(coreData.getRoofmaterial());
			firInsPremVoF.setPara04(coreData.getSumfloors());
			firInsPremVoF.setPara05("N");
			firInsPremVoList.add(firInsPremVoF);
		}

		if (quakeAmt.compareTo(new BigDecimal(0)) > 0) {
			FirInsPremVo firInsPremVoQ = new FirInsPremVo();
			firInsPremVoQ.setRiskcode("F02");
			firInsPremVoQ.setKindcode("FR2");
			firInsPremVoQ.setPara01(quakeAmt.toString());
			firInsPremVoList.add(firInsPremVoQ);
		}
		firPremWsParamVo.setInsPremList(firInsPremVoList);

		try {
			FirPremcalcTmp firPremcalcTmp = firPremCal(firPremWsParamVo);
			List<FirPremcalcTmpdtl> premList = firPremcalcTmp.getFirPremcalcTmpdtlList();
			FirPremcalcTmpdtl fr2Dtl = null;
			FirPremcalcTmpdtl fr3Dtl = null;
			for (int p = 0; p < premList.size(); p++) {
				if ("FR2".equals(premList.get(p).getKindcode())) {
					fr2Dtl = premList.get(p);
				} else if ("FR3".equals(premList.get(p).getKindcode())) {
					fr3Dtl = premList.get(p);
				}
			}
			if ("Y".equals(firPremcalcTmp.getReturnType())) {
				premiumT = firPremcalcTmp.getSumPremium();
				premiumF = fr3Dtl.getPremium();
				premiumQ = fr2Dtl.getPremium();
				coreData.setPremF(premiumF.toString());
				coreData.setPremQ(premiumQ.toString());
			} else {
				warnMsg.append("檢核-保費計算WS計算發生錯誤(" + firPremcalcTmp.getOid() + firPremcalcTmp.getReturnMsg() + "；");
			}
		} catch (Exception e) {
			logger.error("保費計算WS失敗", e);
			warnMsg.append("檢核-保費計算WS無回應，未計算保費；");
		}
		/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 end*/
		
		if(warnMsg!=null) {
			coreData.setWarnMsg(warnMsg.toString());
		}
		
		returnMap.put("coreData", coreData);
		return returnMap;
	}
	
	private Date dateParser(String strDate, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date parseDate = null;
		try {
			parseDate = sdf.parse(strDate);
		}catch (Exception e) {
			return parseDate;
		}
		return parseDate;
	}
	
	public String adToRoc(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String dateStr = sdf.format(date);
		String[] arrDate = dateStr.split("/");
		if(arrDate.length >= 3) {
			return Integer.parseInt(arrDate[0]) - 1911 + "/" + arrDate[1] + "/" + arrDate[2] ;
		}
		return "";
	}
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end*/
	
	/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 start */
	public String recalAmount(String amountF, Date startdate) throws SystemException, Exception {
		
		//先查詢設定是否要重算保額區間
		Map<String, String> params = new HashMap<>();
		params.put("wallno", "CalParam");
		params.put("roofno", "F02STOPRECALAMOUNTDATE");
		
		Date recalAmountValiddate = null;
		Date recalAmountInvaliddate = null;
		
		Result result = prpdPropStructService.findPrpdPropStructByParams(params);
		if(result.getResObject()!=null) {
			List<PrpdPropStruct> resultList = (List<PrpdPropStruct>) result.getResObject();
			PrpdPropStruct prpdPropStruct = resultList.get(0);
			recalAmountValiddate = prpdPropStruct.getValiddate();
			recalAmountInvaliddate = prpdPropStruct.getInvaliddate();
		}
		
		if(startdate != null && !StringUtil.isSpace(amountF) &&
				(startdate.compareTo(recalAmountValiddate) < 0 || startdate.compareTo(recalAmountInvaliddate) > 0)) {
			amountF = (new BigDecimal(amountF).multiply(BigDecimal.valueOf(1.14)).divide(new BigDecimal(10000)).setScale(0, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(10000))).toString();
		}
		
		return amountF;
	}
	/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 end */
	
	public PrptmainService getPrptmainService() {
		return prptmainService;
	}
	public void setPrptmainService(PrptmainService prptmainService) {
		this.prptmainService = prptmainService;
	}

	public FirAgtSalesMappingService getFirAgtSalesMappingService() {
		return firAgtSalesMappingService;
	}

	public void setFirAgtSalesMappingService(FirAgtSalesMappingService firAgtSalesMappingService) {
		this.firAgtSalesMappingService = firAgtSalesMappingService;
	}

	public PrpduserService getPrpduserService() {
		return prpduserService;
	}

	public void setPrpduserService(PrpduserService prpduserService) {
		this.prpduserService = prpduserService;
	}

	public PrpdNewCodeService getPrpdNewCodeService() {
		return prpdNewCodeService;
	}

	public void setPrpdNewCodeService(PrpdNewCodeService prpdNewCodeService) {
		this.prpdNewCodeService = prpdNewCodeService;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public PrpdPropStructService getPrpdPropStructService() {
		return prpdPropStructService;
	}

	public void setPrpdPropStructService(PrpdPropStructService prpdPropStructService) {
		this.prpdPropStructService = prpdPropStructService;
	}
	
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start*/
	public Map<String, String> getStructureMap() {
		structureMap.put("1", " - 特一等建築");
		structureMap.put("2", " - 特二等建築");
		structureMap.put("3", " - 頭等");
		structureMap.put("5", " - 二等");
		structureMap.put("6", " - 三等");
		structureMap.put("7", " - 露天");
		return structureMap;
	}
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end*/

	public void setStructureMap(Map<String, String> structureMap) {
		this.structureMap = structureMap;
	}

	public FirDoubleInsService getClientFirDoubleInsService() {
		return clientFirDoubleInsService;
	}

	public void setClientFirDoubleInsService(FirDoubleInsService clientFirDoubleInsService) {
		this.clientFirDoubleInsService = clientFirDoubleInsService;
	}

	public FirAmountService getClientFirAmountService() {
		return clientFirAmountService;
	}

	public void setClientFirAmountService(FirAmountService clientFirAmountService) {
		this.clientFirAmountService = clientFirAmountService;
	}

	public AddressCheckService getClientAddressCheckService() {
		return clientAddressCheckService;
	}

	public void setClientAddressCheckService(AddressCheckService clientAddressCheckService) {
		this.clientAddressCheckService = clientAddressCheckService;
	}

	public FirPremService getClientFirCalPremService() {
		return clientFirCalPremService;
	}

	public void setClientFirCalPremService(FirPremService clientFirCalPremService) {
		this.clientFirCalPremService = clientFirCalPremService;
	}

	public PrpyddagentService getPrpyddagentService() {
		return prpyddagentService;
	}

	public void setPrpyddagentService(PrpyddagentService prpyddagentService) {
		this.prpyddagentService = prpyddagentService;
	}

	public FirAgtrnAs400DataService getFirAgtrnAs400DataService() {
		return firAgtrnAs400DataService;
	}

	public void setFirAgtrnAs400DataService(FirAgtrnAs400DataService firAgtrnAs400DataService) {
		this.firAgtrnAs400DataService = firAgtrnAs400DataService;
	}

	public RuleCheckService getClientRuleCheckService() {
		return clientRuleCheckService;
	}

	public void setClientRuleCheckService(RuleCheckService clientRuleCheckService) {
		this.clientRuleCheckService = clientRuleCheckService;
	}

	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start*/
	public PrpcmainService getPrpcmainService() {
		return prpcmainService;
	}

	public void setPrpcmainService(PrpcmainService prpcmainService) {
		this.prpcmainService = prpcmainService;
	}

	public PrpcinsuredService getPrpcinsuredService() {
		return prpcinsuredService;
	}

	public void setPrpcinsuredService(PrpcinsuredService prpcinsuredService) {
		this.prpcinsuredService = prpcinsuredService;
	}
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end*/
	
}
