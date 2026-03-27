package com.sinosoft.claim.regist.util;

import ins.framework.utils.DataUtils;
import ins.framework.utils.StringUtils;

import java.util.ArrayList;
import java.util.Date;//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種
import com.sinosoft.app.webservice.server.schema.model.regist.vo.ClaimExternalRiskSourceVo;
import com.sinosoft.app.webservice.server.schema.model.regist.vo.ClaimExternalSourceVo;//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
import com.sinosoft.app.webservice.server.schema.model.regist.vo.PersonTrace;//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCinsuredNature;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpLacciPerson;
import com.sinosoft.claim.schema.model.PrpLclaim;//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLregistText;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.util.StringConvert;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sun.org.apache.commons.beanutils.PropertyUtils;//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案

/**
 * <p>
 * Title: RegistViewHelper
 * </p>
 * <p>
 * Description:报案ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2013
 * </p>
 * @author 中科软
 * @version 1.0 <br>
 */
public abstract class RegistViewHelper {
	/** 报案文字信息每行最大显示的字符长度 */
	private int RULE_LENGTH = 70; // rule字段的长度
	/** 保单基本信息服务 */
	private PrpCmainService prpCmainService;
	/** 保单数据传输对象服务 */
	private PolicyService policyService;
	/** 代码服务 */
	private CodeService codeService;
	private EndorseViewHelper endorseViewHelper;
	/**
	 * 默认构造方法
	 */
	public RegistViewHelper() {
	}

	/**
	 * 保存报案时报案页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return registDto 报案数据传输数据结构
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public RegistDto viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		RegistDto registDto = new RegistDto();
		/*---------------------报案主表prpLregist------------------------------------*/
		PrpLregist prpLregist = new PrpLregist();
		// 交强险迁移 报案类型
		String registType = httpServletRequest.getParameter("registType");
		prpLregist.setRegistType(registType);
		prpLregist.setRegistNo((String) httpServletRequest.getAttribute("registNo"));
		if (httpServletRequest.getParameter("prpLregistSharingFlag") != null) {
			prpLregist.setSharingFlag(DataUtils.nullToEmpty(httpServletRequest.getParameter("prpLregistSharingFlag")));// 同業共摊
		}
		//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 START
		if (httpServletRequest.getParameter("prpLregistIsCompulsoryBchainClaim") != null) {
			prpLregist.setIsCompulsoryBchainClaim(DataUtils.nullToEmpty(httpServletRequest.getParameter("prpLregistIsCompulsoryBchainClaim")));// 是否為強制險區塊鏈攤賠案件 
		}
		//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 END
		prpLregist.setLflag(httpServletRequest.getParameter("prpLregistLFlag"));
		prpLregist.setClassCode(httpServletRequest.getParameter("prpLregistClassCode"));
		// 交强险迁移
		if (registType != null && registType.equals("1")) {
			prpLregist.setRiskCode(ConstantCodes.RISKCODE_DAZ);
			prpLregist.setPolicyNo(httpServletRequest.getParameter("mainPolicyNo"));
		} else {
			prpLregist.setRiskCode(httpServletRequest.getParameter("prpLregistRiskCode"));
			prpLregist.setPolicyNo(httpServletRequest.getParameter("prpLregistPolicyNo"));
		}

		prpLregist.setLanguage(httpServletRequest.getParameter("prpLregistLanguage"));
		prpLregist.setInsuredCode(httpServletRequest.getParameter("prpLregistInsuredCode"));
		prpLregist.setInsuredName(httpServletRequest.getParameter("prpLregistInsuredName"));
		// e保通
		prpLregist.setInsuredPhoneNumber(httpServletRequest.getParameter("prpLregistInsuredPhoneNumber"));
		prpLregist.setPersonLossFlag(httpServletRequest.getParameter("personLossFlag"));
		prpLregist.setThirdLicenseNo(httpServletRequest.getParameter("prpLregistthirdLicenseNo"));
		prpLregist.setClauseType(httpServletRequest.getParameter("prpLregistClauseType"));
		prpLregist.setLicenseNo(httpServletRequest.getParameter("prpLregistLicenseNo"));
		prpLregist.setLicenseColorCode(httpServletRequest.getParameter("prpLregistLicenseColorCode"));
		prpLregist.setCarKindCode(httpServletRequest.getParameter("prpLregistCarKindCode"));
		prpLregist.setModelCode(httpServletRequest.getParameter("prpLregistModelCode"));
		prpLregist.setBrandName(httpServletRequest.getParameter("prpLregistBrandName"));
		prpLregist.setEngineNo(httpServletRequest.getParameter("prpLregistEngineNo"));
		prpLregist.setFrameNo(httpServletRequest.getParameter("prpLregistFrameNo"));
		prpLregist.setRunDistance(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLregistRunDistance"))));
		prpLregist.setUseYears(Integer.parseInt(DataUtils.nullToZero(httpServletRequest.getParameter("prpLregistUseYears"))));
		prpLregist.setReportDate(new DateTime(httpServletRequest.getParameter("prpLregistReportDate"), DateTime.YEAR_TO_DAY));
		prpLregist.setReportorPhoneNumber(httpServletRequest.getParameter("prpLregistReportorPhoneNumber"));
		prpLregist.setReportorMobile(httpServletRequest.getParameter("prpLregistReportorMobile"));// 备案人手机
		prpLregist.setLinkerPostCode(httpServletRequest.getParameter("prpLregistLinkerPostCode"));
		prpLregist.setLinkerAddress(httpServletRequest.getParameter("prpLregistLinkerAddress"));
		// 为了兼容大地程序的小时设置情况，所以只保存小时，分和秒先不保存
		prpLregist.setReportHour(StringConvert.newString(httpServletRequest.getParameter("prpLregistReportHour")) + ":" + StringConvert.newString(httpServletRequest.getParameter("prpLregistReportMinute")) + ":00");
		prpLregist.setReportAddress(httpServletRequest.getParameter("prpLregistReportAddress"));
		prpLregist.setReportorName(httpServletRequest.getParameter("prpLregistReportorName"));
		prpLregist.setReportType(httpServletRequest.getParameter("reportType"));
		prpLregist.setPhoneNumber(httpServletRequest.getParameter("prpLregistPhoneNumber"));
		prpLregist.setDriverMobile(httpServletRequest.getParameter("prpLregistDriverMobile"));// 出险车辆驾驶人手机
		prpLregist.setLinkerName(httpServletRequest.getParameter("prpLregistLinkerName"));
		prpLregist.setDamageStartDate(new DateTime(httpServletRequest.getParameter("prpLregistDamageStartDate"), DateTime.YEAR_TO_DAY));
		// 为了兼容大地程序的小时设置情况，所以只保存小时，分和秒先不保存
		prpLregist.setDamageStartHour(StringConvert.newString(httpServletRequest.getParameter("prpLregistDamageStartHour")) + ":" + StringConvert.newString(httpServletRequest.getParameter("prpLregistDamageStartMinute")) + ":00");
		prpLregist.setDamageEndDate(new DateTime(httpServletRequest.getParameter("prpLregistDamageStartDate"), DateTime.YEAR_TO_DAY));
		// 为了兼容大地程序的小时设置情况，所以只保存小时，分和秒先不保存
		prpLregist.setDamageEndHour(StringConvert.newString(httpServletRequest.getParameter("prpLregistDamageStartHour")) + ":" + StringConvert.newString(httpServletRequest.getParameter("prpLregistDamageStartMinute")) + ":00");
		// 没有保存被保险人地址，单证无法带出
		String damageDate = new DateTime(prpLregist.getDamageStartDate()).toString();
		String damageHour = prpLregist.getDamageStartHour();
		PrpCmain prpCmain = getEndorseViewHelper().findPrpCmain(prpLregist.getPolicyNo(), damageDate, damageHour);
		prpLregist.setInsuredAddress(prpCmain.getInsuredAddress());
		prpLregist.setDamageCode(DataUtils.dbNullToEmpty(httpServletRequest.getParameter("prpLregistDamageCode")).trim());
		prpLregist.setDamageName(httpServletRequest.getParameter("prpLregistDamageName"));
		prpLregist.setDamageCodeBZ(DataUtils.dbNullToEmpty(httpServletRequest.getParameter("prpLregistDamageCodeBZ")).trim());// 强制险出险原因代码
		prpLregist.setDamageNameBZ(httpServletRequest.getParameter("prpLregistDamageNameBZ"));// 强制险出险原因
		prpLregist.setDamageTypeCode(httpServletRequest.getParameter("prpLregistDamageTypeCode"));
		prpLregist.setDamageTypeName(httpServletRequest.getParameter("prpLregistDamageTypeName"));
		prpLregist.setFirstSiteFlag(httpServletRequest.getParameter("firstSiteFlag"));
		prpLregist.setAddressCode(httpServletRequest.getParameter("prpLregistAddressCode"));
		// 是否发短信标志位
		prpLregist.setSendMesFlag(httpServletRequest.getParameter("sendMesFlag"));
		// 交强险迁移
		prpLregist.setPayselfFlag(httpServletRequest.getParameter("payselfFlag"));// 互碰自赔
		prpLregist.setPropLossFlag(httpServletRequest.getParameter("propLossFlag"));// 物损

		prpLregist.setDamageAreaCode(httpServletRequest.getParameter("prpLregistDamageAreaCode"));
		prpLregist.setDamageAreaName(httpServletRequest.getParameter("prpLregistDamageAreaName"));
		prpLregist.setDamageAddressType(httpServletRequest.getParameter("damageAddressType"));
		prpLregist.setDamageAddress(httpServletRequest.getParameter("prpLregistDamageAddress"));
		//mantis：CLM0274，處理人員：DP0713，需求單編號：新核心- TA海外突發疾病修改
		prpLregist.setAddressCode(httpServletRequest.getParameter("addressCode"));
		prpLregist.setAuthorityUnit(httpServletRequest.getParameter("prpLregistAuthorityUnit"));//憲警單位
		prpLregist.setDamageAreaPostCode(httpServletRequest.getParameter("prpLregistDamageAreaPostCode"));
		prpLregist.setHandleUnit(httpServletRequest.getParameter("prpLregistHandleUnit"));
		prpLregist.setLossName(httpServletRequest.getParameter("prpLregistLossName"));
		prpLregist.setLossQuantity(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLregistLossQuantity"))));
		prpLregist.setUnit(httpServletRequest.getParameter("prpLregistUnit"));
		prpLregist.setEstiCurrency(httpServletRequest.getParameter("prpLregistEstiCurrency"));
		prpLregist.setEstimateLoss(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLregistEstimateLoss"))));
		prpLregist.setManageType(httpServletRequest.getParameter("prpLregistManageType"));
		prpLregist.setManageTypeName(httpServletRequest.getParameter("prpLregistManageTypeName"));
		prpLregist.setWeather(httpServletRequest.getParameter("prpLregistWeather"));
		prpLregist.setWeatherName(httpServletRequest.getParameter("prpLregistWeatherName"));
		prpLregist.setSection(httpServletRequest.getParameter("prpLregistSection"));
		prpLregist.setSectionName(httpServletRequest.getParameter("prpLregistSectionName"));
		prpLregist.setEstimateFee(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLregistEstimateFee"))));
		prpLregist.setReceiverName(httpServletRequest.getParameter("prpLregistReceiverName"));
		prpLregist.setHandlerCode(httpServletRequest.getParameter("prpLregistHandlerCode"));
		prpLregist.setHandler1Code(httpServletRequest.getParameter("prpLregistHandler1Code"));
		prpLregist.setComCode(httpServletRequest.getParameter("prpLregistComCode"));
		prpLregist.setInputDate(new DateTime(httpServletRequest.getParameter("prpLregistInputDate"), DateTime.YEAR_TO_DAY));
		prpLregist.setAcceptFlag(httpServletRequest.getParameter("acceptFlag"));
		prpLregist.setRepeatInsureFlag(httpServletRequest.getParameter("repeatInsureFlag"));
		prpLregist.setClaimType(httpServletRequest.getParameter("claimType"));
		if (!isEmpty(httpServletRequest.getParameter("prpLregistCancelDate"))) {
			prpLregist.setCancelDate(new DateTime(httpServletRequest.getParameter("prpLregistCancelDate"), DateTime.YEAR_TO_DAY));
		}
		prpLregist.setDealerCode(httpServletRequest.getParameter("prpLregistDealerCode"));
		prpLregist.setRemark(httpServletRequest.getParameter("prpLregistRemark"));
		prpLregist.setOperatorCode(httpServletRequest.getParameter("prpLregistOperatorCode"));
		prpLregist.setMakeCom(httpServletRequest.getParameter("prpLregistMakeCom"));
		prpLregist.setFlag(httpServletRequest.getParameter("prpLregistFlag"));
		// 原因：添加巨灾代码
		prpLregist.setCatastropheCode1(httpServletRequest.getParameter("prpCatastropheCode1"));// 巨灾类型
		prpLregist.setCatastropheName1(httpServletRequest.getParameter("prpCatastropheName1"));// 巨灾名称
		prpLregist.setCatastropheCode2(httpServletRequest.getParameter("prpCatastropheCode2"));
		prpLregist.setCatastropheName2(httpServletRequest.getParameter("prpCatastropheName2"));// 巨灾代码
		// 添加垫付赔案类型
		String advanceType = httpServletRequest.getParameter("prplregistAdvance");
		if (advanceType != null) {
			prpLregist.setAdvanceType(advanceType);
		}
		// 是否团单免导标志
		prpLregist.setTermFlag(httpServletRequest.getParameter("termFlag"));
		// 意键险需要添加是否呈报字段
		prpLregist.setReportFlag(httpServletRequest.getParameter("prplregistReportFlag"));
		String strInnerCode = getCodeService().translateRiskCodetoInnerCode(httpServletRequest.getParameter("prpLregistRiskCode"));
		if ("YII".equals(strInnerCode)) {
			prpLregist.setBrandName(httpServletRequest.getParameter("prpLregistCargoName"));
		}
		// 添加报案修改人信息
		prpLregist.setAlterName(httpServletRequest.getParameter("alterName"));
		prpLregist.setAlterPhoneNumber(httpServletRequest.getParameter("alterPhoneNumber"));
		prpLregist.setAlterRelationType(httpServletRequest.getParameter("alterRelationType"));
		prpLregist.setAlterTime(new DateTime(httpServletRequest.getParameter("alterTime"), DateTime.YEAR_TO_SECOND));
		prpLregist.setAlterType("claim");// 从理赔系统发起的报案
		prpLregist.setCoinsFlag(httpServletRequest.getParameter("prpLregistCoinsFlag"));
		prpLregist.setClaimAgent(httpServletRequest.getParameter("prpLregistClaimAgent"));
		prpLregist.setAreaCode(httpServletRequest.getParameter("prpLregistAreaCode"));
		prpLregist.setShipCName(httpServletRequest.getParameter("prpLregistShipCName"));
		prpLregist.setShipModel(httpServletRequest.getParameter("prpLregistShipModel"));
		// 车险保存报案人与被保险人关系
		prpLregist.setRelationType(httpServletRequest.getParameter("prpLregistRelationType"));
		String riskType = this.getCodeService().translateRiskCodetoRiskType(prpCmain.getRiskCode());
		if (ConstantCodes.CLASSCODE_E.equals(riskType)) {
			// 原因：添加出险人员信息
			if (httpServletRequest.getParameter("prpLacciPersonAcciCode") != null || httpServletRequest.getParameter("prpLacciPersonAcciName") != null || httpServletRequest.getParameter("prpLacciPersonAge") != null
					|| httpServletRequest.getParameter("prpLacciPersonIdentifyNumber") != null) {
				PrpLacciPerson prpLacciPerson = new PrpLacciPerson();
				prpLacciPerson.getId().setCertiNo((String) httpServletRequest.getAttribute("registNo"));
				prpLacciPerson.getId().setCertiType("01");
				// 交强险迁移
				prpLacciPerson.setPolicyNo(prpLregist.getPolicyNo());
				prpLacciPerson.getId().setSerialNo(getPolicyService().findBySeriaNo("1=1") + 1);
				String endorseNo = this.getEndorseViewHelper().getEndorseNo(prpCmain.getPolicyNo(), damageDate, damageHour);
				List<PrpCinsured> prpCinsuredList = this.getEndorseViewHelper().findPrpCinsuredFromCopy(endorseNo, prpCmain.getPolicyNo(), prpLregist.getInsuredCode(), prpLregist.getInsuredName());
				PrpCinsured prpCinsured = this.getEndorseViewHelper().getPrpCinsured(prpCinsuredList, prpLregist.getInsuredCode(), prpLregist.getInsuredName());
				int serialNo = prpCinsured.getId().getSerialNo();
				prpLacciPerson.setFamilyNo(DataUtils.getInteger(serialNo));
				prpLacciPerson.setAcciCode(prpCinsured.getInsuredCode());
				prpLacciPerson.setAcciName(prpCinsured.getInsuredName());
				prpLacciPerson.setIdentifyNumber(prpCinsured.getIdentifyNumber());
				prpLacciPerson.setIdentifyType(prpCinsured.getIdentifytype());
				prpLacciPerson.setPhone(prpCinsured.getPhoneNumber());
				int[] serialnos = new int[] { serialNo };
				List<PrpCinsuredNature> prpCinsuredNatureList = this.getEndorseViewHelper().findPrpCinsuredNatureFromCopy(endorseNo, prpCmain.getPolicyNo(), serialnos);
				PrpCinsuredNature prpCinsuredNature = this.getEndorseViewHelper().getPrpCinsuredNature(prpCinsuredNatureList, serialNo);
				if (prpCinsuredNature != null) {
					prpLacciPerson.setAge(prpCinsuredNature.getAge() == null ? 0 : prpCinsuredNature.getAge().intValue());
					prpLacciPerson.setSex(prpCinsuredNature.getSex());
				}
				registDto.setPrpLacciPerson(prpLacciPerson);
			}
		}
		// 加到ArrayList中
		registDto.setPrpLregist(prpLregist);
		/*---------------------报案文本表prpLregistText------------------------------------*/
		List<PrpLregistText> prpLregistTextList = new ArrayList<PrpLregistText>();
		String TextTemp = httpServletRequest.getParameter("prpLregistTextContextInnerHTML");
		String[] rules = StringUtils.split(TextTemp, RULE_LENGTH);
		// 得到连接串,下面将其切分到数组
		for (int k = 0; k < rules.length; k++) {
			PrpLregistText prpLregistText = new PrpLregistText();
			prpLregistText.getId().setRegistNo((String) httpServletRequest.getAttribute("registNo"));
			prpLregistText.setContext(rules[k]);
			prpLregistText.getId().setLineNo(k + 1);
			prpLregistText.getId().setTextType("1");
			prpLregistTextList.add(prpLregistText);
		}
		// 装入RegistDto
		registDto.setPrpLregistTextList(prpLregistTextList);
		// 原因：添加呈报信息
		if (httpServletRequest.getParameter("prplregistReportFlag") != null && !httpServletRequest.getParameter("prplregistReportFlag").equals("")) {
			List<PrpLregistText> prpLregistTextDtoList2 = new ArrayList<PrpLregistText>();
			String TextTemp2 = httpServletRequest.getParameter("prpLregistTextContextInnerHTML2");
			String[] rules2 = StringUtils.split(TextTemp2, RULE_LENGTH);
			// 得到连接串,下面将其切分到数组
			for (int k = 0; k < rules2.length; k++) {
				PrpLregistText prpLregistText = new PrpLregistText();
				prpLregistText.getId().setRegistNo((String) httpServletRequest.getAttribute("registNo"));
				prpLregistText.setContext(rules2[k]);
				prpLregistText.getId().setLineNo(k + 1);
				prpLregistText.getId().setTextType("4");
				prpLregistTextDtoList2.add(prpLregistText);
			}
			// 装入RegistDto
			registDto.setPrpLregistTextList2(prpLregistTextDtoList2);
		}
		/*---------------------立案操作状态内容prpLclaimStatus------------------------------------*/
		PrpLclaimStatus prpLclaimStatus = new PrpLclaimStatus();
		prpLclaimStatus.setStatus(httpServletRequest.getParameter("buttonSaveType"));
		prpLclaimStatus.getId().setBusinessNo(prpLregist.getRegistNo());
		prpLclaimStatus.setPolicyNo(prpLregist.getPolicyNo());
		prpLclaimStatus.getId().setNodeType("regis");
		prpLclaimStatus.getId().setSerialNo(0);
		// 取得当前用户信息，写操作员信息到实赔中
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		prpLclaimStatus.setHandlerCode(user.getUserCode());
		prpLclaimStatus.setInputDate(prpLregist.getInputDate());
		prpLclaimStatus.setOperateDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLclaimStatus.setRiskCode(prpLregist.getRiskCode());
		registDto.setPrpLclaimStatus(prpLclaimStatus);
		return registDto;
	}
	
	/**
	 * (外部存入)保存报案时报案页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
	 * @param httpServletRequest
	 * @return registDto 报案数据传输数据结构
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public RegistDto externalToDto(HttpServletRequest httpServletRequest,ClaimExternalSourceVo claimExternalSourceVo,PrpCmain prpCmainIn,PrpLregist prpLregistIn) throws Exception {
		RegistDto registDto = new RegistDto();
		/*---------------------报案主表prpLregist------------------------------------*/
		PrpLregist prpLregist = new PrpLregist();
		PropertyUtils.copyProperties(prpLregist ,prpLregistIn);
		// 交强险迁移 报案类型
		String registType = prpLregist.getRegistType();//httpServletRequest.getParameter("registType");
//		prpLregist.setRegistType(registType);
		prpLregist.setRegistNo((String) httpServletRequest.getAttribute("registNo"));
//		if (httpServletRequest.getAttribute("prpLregistSharingFlag") != null) {
//			prpLregist.setSharingFlag(DataUtils.nullToEmpty(prpLregistIn.getSharingFlag()));// 同業共摊
//		}
//		if (httpServletRequest.getParameter("prpLregistIsCompulsoryBchainClaim") != null) {
//			prpLregist.setIsCompulsoryBchainClaim(DataUtils.nullToEmpty(httpServletRequest.getParameter("prpLregistIsCompulsoryBchainClaim")));// 是否為強制險區塊鏈攤賠案件 
//		}
//		prpLregist.setLflag((String)httpServletRequest.getAttribute("prpLregistLFlag"));
//		prpLregist.setClassCode((String)httpServletRequest.getAttribute("prpLregistClassCode"));
		// 交强险迁移
		if (registType != null && registType.equals("1")) {
			//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種 START
			prpLregist.setRiskCode(ConstantCodes.RISKCODE_DAZ);
			if(!isEmpty((String)httpServletRequest.getAttribute("mainPolicyNo"))){
				prpLregist.setPolicyNo((String)httpServletRequest.getAttribute("mainPolicyNo"));				
			}
			//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種 END
		} else {
			prpLregist.setRiskCode((String)httpServletRequest.getAttribute("prpLregistRiskCode"));
			prpLregist.setPolicyNo((String)httpServletRequest.getAttribute("prpLregistPolicyNo"));
		}
//		prpLregist.setLanguage(httpServletRequest.getParameter("prpLregistLanguage"));
//		prpLregist.setInsuredCode(httpServletRequest.getParameter("prpLregistInsuredCode"));
//		prpLregist.setInsuredName(httpServletRequest.getParameter("prpLregistInsuredName"));
		// e保通
//		prpLregist.setInsuredPhoneNumber(httpServletRequest.getParameter("prpLregistInsuredPhoneNumber"));
//		prpLregist.setPersonLossFlag(httpServletRequest.getParameter("personLossFlag"));
//		prpLregist.setThirdLicenseNo(httpServletRequest.getParameter("prpLregistthirdLicenseNo"));
//		prpLregist.setClauseType(httpServletRequest.getParameter("prpLregistClauseType"));
//		prpLregist.setLicenseNo(httpServletRequest.getParameter("prpLregistLicenseNo"));
//		prpLregist.setLicenseColorCode(httpServletRequest.getParameter("prpLregistLicenseColorCode"));
//		prpLregist.setCarKindCode(httpServletRequest.getParameter("prpLregistCarKindCode"));
//		prpLregist.setModelCode(httpServletRequest.getParameter("prpLregistModelCode"));
//		prpLregist.setBrandName(httpServletRequest.getParameter("prpLregistBrandName"));
//		prpLregist.setEngineNo(httpServletRequest.getParameter("prpLregistEngineNo"));
//		prpLregist.setFrameNo(httpServletRequest.getParameter("prpLregistFrameNo"));
//		prpLregist.setRunDistance(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLregistRunDistance"))));
//		prpLregist.setUseYears(Integer.parseInt(DataUtils.nullToZero(httpServletRequest.getParameter("prpLregistUseYears"))));
		prpLregist.setReportDate(new DateTime(claimExternalSourceVo.getReportDate(), DateTime.YEAR_TO_DAY));
		//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種
		prpLregist.setReportorPhoneNumber(claimExternalSourceVo.getReportorPhoneNumber());
		prpLregist.setReportorMobile(claimExternalSourceVo.getReportorMobile());// 备案人手机
//		prpLregist.setLinkerPostCode(httpServletRequest.getParameter("prpLregistLinkerPostCode"));
//		prpLregist.setLinkerAddress(httpServletRequest.getParameter("prpLregistLinkerAddress"));
		// 为了兼容大地程序的小时设置情况，所以只保存小时，分和秒先不保存
		prpLregist.setReportHour(StringConvert.newString(claimExternalSourceVo.getReportHour()) + ":" + StringConvert.newString(claimExternalSourceVo.getReportMinute()) + ":00");
//		prpLregist.setReportAddress(httpServletRequest.getParameter("prpLregistReportAddress"));
		prpLregist.setReportorName(claimExternalSourceVo.getReportorName());
		prpLregist.setReportType(claimExternalSourceVo.getReportType());
		prpLregist.setPhoneNumber(!isEmpty(claimExternalSourceVo.getPhoneNumber())?claimExternalSourceVo.getPhoneNumber():claimExternalSourceVo.getReportorPhoneNumber());//claimExternalSourceVo.getReportorPhoneNumber()??
		prpLregist.setDriverMobile(!isEmpty(claimExternalSourceVo.getDriverMobile())?claimExternalSourceVo.getDriverMobile():claimExternalSourceVo.getReportorMobile());// 出险车辆驾驶人手机
		prpLregist.setLinkerName(!isEmpty(claimExternalSourceVo.getLinkerName())?claimExternalSourceVo.getLinkerName():claimExternalSourceVo.getReportorName());
//		prpLregist.setDamageStartDate(new DateTime(httpServletRequest.getParameter("prpLregistDamageStartDate"), DateTime.YEAR_TO_DAY));
		// 为了兼容大地程序的小时设置情况，所以只保存小时，分和秒先不保存
		prpLregist.setDamageStartHour(StringConvert.newString(claimExternalSourceVo.getDamageStartHour()) + ":" + StringConvert.newString(claimExternalSourceVo.getDamageStartMinute()) + ":00");
//		prpLregist.setDamageEndDate(new DateTime(httpServletRequest.getParameter("prpLregistDamageStartDate"), DateTime.YEAR_TO_DAY));
		// 为了兼容大地程序的小时设置情况，所以只保存小时，分和秒先不保存
//		prpLregist.setDamageEndHour(StringConvert.newString(httpServletRequest.getParameter("prpLregistDamageStartHour")) + ":" + StringConvert.newString(httpServletRequest.getParameter("prpLregistDamageStartMinute")) + ":00");
		// 没有保存被保险人地址，单证无法带出
//		String damageDate = new DateTime(claimExternalSourceVo.getDamageStartDate()).toString();
//		String damageHour = claimExternalSourceVo.getDamageStartHour();
		PrpCmain prpCmain = new PrpCmain();//prpCmainIn;//getEndorseViewHelper().findPrpCmain(prpLregist.getPolicyNo(), damageDate, damageHour);
		PropertyUtils.copyProperties(prpCmain ,prpCmainIn);
		prpLregist.setInsuredAddress(prpCmain.getInsuredAddress());
		prpLregist.setDamageCode(DataUtils.dbNullToEmpty(claimExternalSourceVo.getDamageCode()).trim());
//		prpLregist.setDamageName(httpServletRequest.getParameter("prpLregistDamageName"));//???
//		prpLregist.setDamageCodeBZ(DataUtils.dbNullToEmpty(httpServletRequest.getParameter("prpLregistDamageCodeBZ")).trim());// 强制险出险原因代码
//		prpLregist.setDamageNameBZ(httpServletRequest.getParameter("prpLregistDamageNameBZ"));// 强制险出险原因
//		prpLregist.setDamageTypeCode(httpServletRequest.getParameter("prpLregistDamageTypeCode"));
//		prpLregist.setDamageTypeName(httpServletRequest.getParameter("prpLregistDamageTypeName"));
//		prpLregist.setFirstSiteFlag(httpServletRequest.getParameter("firstSiteFlag"));
//		prpLregist.setAddressCode(httpServletRequest.getParameter("prpLregistAddressCode"));
		// 是否发短信标志位
//		prpLregist.setSendMesFlag(httpServletRequest.getParameter("sendMesFlag"));
		// 交强险迁移
//		prpLregist.setPayselfFlag(httpServletRequest.getParameter("payselfFlag"));// 互碰自赔
//		prpLregist.setPropLossFlag(httpServletRequest.getParameter("propLossFlag"));// 物损

//		prpLregist.setDamageAreaCode(httpServletRequest.getParameter("prpLregistDamageAreaCode"));
//		prpLregist.setDamageAreaName(httpServletRequest.getParameter("prpLregistDamageAreaName"));
//		prpLregist.setDamageAddressType(httpServletRequest.getParameter("damageAddressType"));
		prpLregist.setDamageAddress(claimExternalSourceVo.getDamageAddress());
//		prpLregist.setAuthorityUnit(httpServletRequest.getParameter("prpLregistAuthorityUnit"));//憲警單位
//		prpLregist.setDamageAreaPostCode(httpServletRequest.getParameter("prpLregistDamageAreaPostCode"));
		prpLregist.setHandleUnit(claimExternalSourceVo.getHandleUnit());
//		prpLregist.setLossName(httpServletRequest.getParameter("prpLregistLossName"));
		//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種START
		//多元需要給null 讓其轉為0
		prpLregist.setLossQuantity(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLregistLossQuantity"))));
//		prpLregist.setUnit(httpServletRequest.getParameter("prpLregistUnit"));
//		prpLregist.setEstiCurrency(httpServletRequest.getParameter("prpLregistEstiCurrency"));
		//多元需要給null 讓其轉為0
		prpLregist.setEstimateLoss(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLregistEstimateLoss"))));
		prpLregist.setManageType(claimExternalSourceVo.getManageType());
//		prpLregist.setManageTypeName(httpServletRequest.getParameter("prpLregistManageTypeName"));
//		prpLregist.setWeather(httpServletRequest.getParameter("prpLregistWeather"));
//		prpLregist.setWeatherName(httpServletRequest.getParameter("prpLregistWeatherName"));
//		prpLregist.setSection(httpServletRequest.getParameter("prpLregistSection"));
//		prpLregist.setSectionName(httpServletRequest.getParameter("prpLregistSectionName"));
		//多元需要給null 讓其轉為0
		prpLregist.setEstimateFee(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLregistEstimateFee"))));
		//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種
//		prpLregist.setReceiverName(httpServletRequest.getParameter("prpLregistReceiverName"));
		prpLregist.setHandlerCode(claimExternalSourceVo.getHandlerCode());
//		prpLregist.setHandler1Code(httpServletRequest.getParameter("prpLregistHandler1Code"));
//		prpLregist.setComCode(httpServletRequest.getParameter("prpLregistComCode"));
//		prpLregist.setInputDate(new DateTime(httpServletRequest.getParameter("prpLregistInputDate"), DateTime.YEAR_TO_DAY));
//		prpLregist.setAcceptFlag(httpServletRequest.getParameter("acceptFlag"));
//		prpLregist.setRepeatInsureFlag(httpServletRequest.getParameter("repeatInsureFlag"));
//		prpLregist.setClaimType(httpServletRequest.getParameter("claimType"));
//		if (!isEmpty(httpServletRequest.getParameter("prpLregistCancelDate"))) {
//			prpLregist.setCancelDate(new DateTime(httpServletRequest.getParameter("prpLregistCancelDate"), DateTime.YEAR_TO_DAY));
//		}
//		prpLregist.setDealerCode(httpServletRequest.getParameter("prpLregistDealerCode"));
//		prpLregist.setOperatorCode(httpServletRequest.getParameter("prpLregistOperatorCode"));
//		prpLregist.setMakeCom(httpServletRequest.getParameter("prpLregistMakeCom"));
//		prpLregist.setFlag(httpServletRequest.getParameter("prpLregistFlag"));
		// 原因：添加巨灾代码
//		prpLregist.setCatastropheCode1(httpServletRequest.getParameter("prpCatastropheCode1"));// 巨灾类型
//		prpLregist.setCatastropheName1(httpServletRequest.getParameter("prpCatastropheName1"));// 巨灾名称
//		prpLregist.setCatastropheCode2(httpServletRequest.getParameter("prpCatastropheCode2"));
//		prpLregist.setCatastropheName2(httpServletRequest.getParameter("prpCatastropheName2"));// 巨灾代码
		// 添加垫付赔案类型
//		String advanceType = httpServletRequest.getParameter("prplregistAdvance");
//		if (advanceType != null) {
//			prpLregist.setAdvanceType(advanceType);
//		}
		// 是否团单免导标志
//		prpLregist.setTermFlag(httpServletRequest.getParameter("termFlag"));
		// 意键险需要添加是否呈报字段
//		prpLregist.setReportFlag(httpServletRequest.getParameter("prplregistReportFlag"));
		String strInnerCode = getCodeService().translateRiskCodetoInnerCode(
//				httpServletRequest.getParameter("prpLregistRiskCode")
				prpLregist.getRiskCode()
				);
		if ("YII".equals(strInnerCode)) {
			prpLregist.setBrandName((String)httpServletRequest.getAttribute("prpLregistCargoName"));
//			prpLregist.setBrandName(prpLregistIn.get)??????找不到cargoName
		}
		// 添加报案修改人信息
//		prpLregist.setAlterName(httpServletRequest.getParameter("alterName"));
//		prpLregist.setAlterPhoneNumber(httpServletRequest.getParameter("alterPhoneNumber"));
		prpLregist.setAlterRelationType(claimExternalSourceVo.getRelationType());
//		prpLregist.setAlterTime(new DateTime(httpServletRequest.getParameter("alterTime"), DateTime.YEAR_TO_SECOND));
		prpLregist.setAlterType("claim");// 从理赔系统发起的报案
//		prpLregist.setCoinsFlag(httpServletRequest.getParameter("prpLregistCoinsFlag"));
//		prpLregist.setClaimAgent(httpServletRequest.getParameter("prpLregistClaimAgent"));
//		prpLregist.setAreaCode(httpServletRequest.getParameter("prpLregistAreaCode"));
//		prpLregist.setShipCName(httpServletRequest.getParameter("prpLregistShipCName"));
//		prpLregist.setShipModel(httpServletRequest.getParameter("prpLregistShipModel"));
		// 车险保存报案人与被保险人关系
//		prpLregist.setRelationType(prpLregistIn.getRelationType());//httpServletRequest.getParameter("prpLregistRelationType"));
		String riskType = this.getCodeService().translateRiskCodetoRiskType(prpCmain.getRiskCode());
		if (ConstantCodes.CLASSCODE_E.equals(riskType)) {
			// 原因：添加出险人员信息
			//write List<PersonTrace> PersonTraceList = claimExternalSourceVo.getPersonTraceList();
			//???
//			if (httpServletRequest.getParameter("prpLacciPersonAcciCode") != null || httpServletRequest.getParameter("prpLacciPersonAcciName") != null || httpServletRequest.getParameter("prpLacciPersonAge") != null
//					|| httpServletRequest.getParameter("prpLacciPersonIdentifyNumber") != null) {
//				PrpLacciPerson prpLacciPerson = new PrpLacciPerson();
//				prpLacciPerson.getId().setCertiNo((String) httpServletRequest.getAttribute("registNo"));
//				prpLacciPerson.getId().setCertiType("01");
//				// 交强险迁移
//				prpLacciPerson.setPolicyNo(prpLregist.getPolicyNo());
//				prpLacciPerson.getId().setSerialNo(getPolicyService().findBySeriaNo("1=1") + 1);
//				String endorseNo = this.getEndorseViewHelper().getEndorseNo(prpCmain.getPolicyNo(), damageDate, damageHour);
//				List<PrpCinsured> prpCinsuredList = this.getEndorseViewHelper().findPrpCinsuredFromCopy(endorseNo, prpCmain.getPolicyNo(), prpLregist.getInsuredCode(), prpLregist.getInsuredName());
//				PrpCinsured prpCinsured = this.getEndorseViewHelper().getPrpCinsured(prpCinsuredList, prpLregist.getInsuredCode(), prpLregist.getInsuredName());
//				int serialNo = prpCinsured.getId().getSerialNo();
//				prpLacciPerson.setFamilyNo(DataUtils.getInteger(serialNo));
//				prpLacciPerson.setAcciCode(prpCinsured.getInsuredCode());
//				prpLacciPerson.setAcciName(prpCinsured.getInsuredName());
//				prpLacciPerson.setIdentifyNumber(prpCinsured.getIdentifyNumber());
//				prpLacciPerson.setIdentifyType(prpCinsured.getIdentifytype());
//				prpLacciPerson.setPhone(prpCinsured.getPhoneNumber());
//				int[] serialnos = new int[] { serialNo };
//				List<PrpCinsuredNature> prpCinsuredNatureList = this.getEndorseViewHelper().findPrpCinsuredNatureFromCopy(endorseNo, prpCmain.getPolicyNo(), serialnos);
//				PrpCinsuredNature prpCinsuredNature = this.getEndorseViewHelper().getPrpCinsuredNature(prpCinsuredNatureList, serialNo);
//				if (prpCinsuredNature != null) {
//					prpLacciPerson.setAge(prpCinsuredNature.getAge() == null ? 0 : prpCinsuredNature.getAge().intValue());
//					prpLacciPerson.setSex(prpCinsuredNature.getSex());
//				}
//				registDto.setPrpLacciPerson(prpLacciPerson);
//			}
		}
		String TextTemp = generateRegistText(null,claimExternalSourceVo,prpLregist);//httpServletRequest.getParameter("prpLregistTextContextInnerHTML");
		prpLregist.setRemark(TextTemp);
		// 加到ArrayList中
		registDto.setPrpLregist(prpLregist);
		/*---------------------报案文本表prpLregistText------------------------------------*/
		List<PrpLregistText> prpLregistTextList = new ArrayList<PrpLregistText>();
		//generateRegistText 來自DAARegistEdit.js 
		String[] rules = StringUtils.split(TextTemp, RULE_LENGTH);
		// 得到连接串,下面将其切分到数组
		for (int k = 0; k < rules.length; k++) {
			PrpLregistText prpLregistText = new PrpLregistText();
			prpLregistText.getId().setRegistNo((String) httpServletRequest.getAttribute("registNo"));
			prpLregistText.setContext(rules[k]);
			prpLregistText.getId().setLineNo(k + 1);
			prpLregistText.getId().setTextType("1");
			prpLregistTextList.add(prpLregistText);
		}
		// 装入RegistDto
		registDto.setPrpLregistTextList(prpLregistTextList);
		// 原因：添加呈报信息
		if (httpServletRequest.getParameter("prplregistReportFlag") != null && !httpServletRequest.getParameter("prplregistReportFlag").equals("")) {
			List<PrpLregistText> prpLregistTextDtoList2 = new ArrayList<PrpLregistText>();
			String TextTemp2 = httpServletRequest.getParameter("prpLregistTextContextInnerHTML2");
			String[] rules2 = StringUtils.split(TextTemp2, RULE_LENGTH);
			// 得到连接串,下面将其切分到数组
			for (int k = 0; k < rules2.length; k++) {
				PrpLregistText prpLregistText = new PrpLregistText();
				prpLregistText.getId().setRegistNo((String) httpServletRequest.getAttribute("registNo"));
				prpLregistText.setContext(rules2[k]);
				prpLregistText.getId().setLineNo(k + 1);
				prpLregistText.getId().setTextType("4");
				prpLregistTextDtoList2.add(prpLregistText);
			}
			// 装入RegistDto
			registDto.setPrpLregistTextList2(prpLregistTextDtoList2);
		}
		/*---------------------立案操作状态内容prpLclaimStatus------------------------------------*/
		PrpLclaimStatus prpLclaimStatus = new PrpLclaimStatus();
		prpLclaimStatus.setStatus((String)httpServletRequest.getAttribute("buttonSaveType"));
		prpLclaimStatus.getId().setBusinessNo(prpLregist.getRegistNo());
		prpLclaimStatus.setPolicyNo(prpLregist.getPolicyNo());
		prpLclaimStatus.getId().setNodeType("regis");
		prpLclaimStatus.getId().setSerialNo(0);
		// 取得当前用户信息，写操作员信息到实赔中
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		prpLclaimStatus.setHandlerCode(user.getUserCode());
		prpLclaimStatus.setInputDate(prpLregist.getInputDate());
		prpLclaimStatus.setOperateDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLclaimStatus.setRiskCode(prpLregist.getRiskCode());
		registDto.setPrpLclaimStatus(prpLclaimStatus);
		return registDto;
	}

	/**
	 * 來自DAARegistEdit.js 
	 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
	 * 來源自 http://localhost:7001/claim/pages/DAA/regist/js/DAARegistEdit.js >>function generateRegistText() {
	 * @param claimExternalSourceVo
	 * @return
	 */
	public String generateRegistText(String orgRemark,ClaimExternalSourceVo claimExternalSourceVo,PrpLregist prpLregist){
		// 得到驾驶员信息
		String prplDriver = "";
		String prplRegistText = "";
		if(null!=claimExternalSourceVo && null!=claimExternalSourceVo.getDriverList()
				&& claimExternalSourceVo.getDriverList().size()>0){
			prplDriver = claimExternalSourceVo.getDriverList().get(0).getDriverName().trim();
		}
		
		// 得到标的车牌信息
		String prplLicenseNo = "";
		if(null!=claimExternalSourceVo && null!=claimExternalSourceVo.getThirdPartyList()
				&& claimExternalSourceVo.getThirdPartyList().size()>0){
			prplLicenseNo = claimExternalSourceVo.getThirdPartyList().get(0).getLicenseNo();
		}
		// 得到时间、地点、事故原因、出险原因
		String prpLregistLinkerName = prpLregist.getLinkerName();//trim(fm.prpLregistLinkerName.value); // 得到驾驶人
		String prpLregistPhoneNumber = prpLregist.getPhoneNumber();//trim(fm.prpLregistPhoneNumber.value); // 得到驾驶人联系电话
		String prpLregistDamageStartDate = new DateTime(claimExternalSourceVo.getDamageStartDate()).toString();//trim(fm.prpLregistDamageStartDate.value);
		String prpLregistDamageStartHour = claimExternalSourceVo.getDamageStartHour();//trim(fm.prpLregistDamageStartHour.value);
		String prpLregistDamageStartMinute = claimExternalSourceVo.getDamageStartMinute();//trim(fm.prpLregistDamageStartMinute.value);
		String prpLregistDamageAddress = prpLregist.getDamageAddress();//trim(fm.prpLregistDamageAddress.value);
		String prpLregistDamageName = "";
		if (prpLregist.getRegistType().equals('1')) { //强制险的取强制险出险原因
			prpLregistDamageName = prpLregist.getDamageNameBZ();//prpLregistDamageNameBZ.value;
		} else {
			prpLregistDamageName = prpLregist.getDamageName();
		}
		if (prpLregistLinkerName == "" || prpLregistLinkerName == null) {
			prpLregistLinkerName = "駕駛人";
		}
		if (!isEmpty(prpLregist.getDriverMobile())){//trim(fm.prpLregistDriverMobile.value) != "") { //联系方式默认取手机
			prpLregistPhoneNumber = prpLregist.getDriverMobile().trim();//trim(fm.prpLregistDriverMobile.value);
		}
		if (prpLregistPhoneNumber == "" || prpLregistLinkerName == null) {
			prpLregistPhoneNumber = "聯係電話";
		}
		String[] prpLregistDamageStartDateAry = prpLregistDamageStartDate.split("-");
		int year = (new Date().getYear()+1900)-1911;//Integer.parseInt(prpLregistDamageStartDate) - 1911;
		if(null!=prpLregistDamageStartDateAry &&
				prpLregistDamageStartDateAry.length==3){
			year = Integer.parseInt(prpLregistDamageStartDateAry[0]) - 1911;
		}
		String date = prpLregistDamageStartDate.substring(4, prpLregistDamageStartDate.length());
		String prplRegistText1 = year + date + "日" + prpLregistDamageStartHour + "時" + prpLregistDamageStartMinute + "分由" + prpLregistLinkerName + "(" + prpLregistPhoneNumber + ")駕駛";

		// 得到标的车与三者车损失信息
//		var count = getElementCount("prpLthirdPartySerialNo"); // 受损车数量
//		var serialNoCount = getElementCount("RelateSerialNo"); // 受损部位数量，没有受损部位为1
//		var lossMessage1 = ""; // 标的车信息
//		var lossMessage2 = ""; // 三者车信息
		String lossMessage3 = ""; // 总的出险摘要
//		var lossMessageTemp = ""; // 三者受损部位信息

//		for (var j = 1; j < count; j++) {
//			if (serialNoCount == 1) { // 没有受损部位
//				if (j < (count - 1)) { // 除去标的车
//					lossMessage2 = lossMessage2 + fm.prpLthirdPartyLicenseNo[j + 1].value + "受損;"
//				}
//			} else { // 有受损部位
//				lossMessageTemp = "";
//				for (var k = 0; k < serialNoCount; k++) {
//					if (fm.RelateSerialNo[k].value == "1" && j == 1) { // 标的车有受损部位
//						lossMessage1 = lossMessage1 + fm.partName[k].value + fm.compName[k].value + "、";
//					} else { // 三者车
//						if (fm.RelateSerialNo[k].value == j) {
//							lossMessageTemp = lossMessageTemp + fm.partName[k].value + fm.compName[k].value + "、";
//						}
//					}
//				} // endfor
//				if (j > 1) { // 三者车
//					var position2 = lossMessageTemp.lastIndexOf("、");
//					lossMessageTemp = lossMessageTemp.substring(0, position2);
//					lossMessage2 = lossMessage2 + trim(fm.prpLthirdPartyLicenseNo[j].value) + lossMessageTemp + "受損;";
//				}
//			} // endelse
//		} // endfor

//		var position1 = lossMessage1.lastIndexOf("、");
//		lossMessage1 = lossMessage1.substring(0, position1);
//		lossMessage3 = prplLicenseNo + lossMessage1 + "號車於" + prpLregistDamageAddress + "發生" + prpLregistDamageName + "事故";
		lossMessage3 = prplLicenseNo + "號車於" + prpLregistDamageAddress + "發生" + prpLregistDamageName + "事故";

		// 得到处理部门信息
		String HandleUnitName = "";
		HandleUnitName = prpLregist.getHandleUnitName();//trim(fm.prpLregistHandleUnitName.value);

		// 得到人伤信息(需求不明确,生成规则可能以後还需改动)
		String personCount = "";//getElementCount("prpLpersonTracePersonNo");
		String personMessage = "";
		String personMessageTemp = "";
//		for (var j = 1; j < personCount; j++) {//這裡應該是有受傷的列表
//		if(null!=claimExternalSourceVo && null!=claimExternalSourceVo.getPersonTraceList()
//				&& claimExternalSourceVo.getPersonTraceList().size()>0){
////			personMessageTemp = fm.prpLpersonTracePersonName[j].value + "涉及險種爲" + fm.prpLpersonTraceReferKind[j].value + "受傷部位:" + fm.prpLpersonTracePartDesc[j].value + ";";
//			String personName = claimExternalSourceVo.getPersonTraceList().get(0).getPersonName();
//			String referKind = prpLregist.getReferKind();
//			String partDesc = "";// 
//			personMessageTemp = personName + "涉及險種爲" + referKind + "受傷部位:" + partDesc+ ";";
//			personMessage = personMessage + personMessageTemp;
//		}
		String personLossFlag = prpLregist.getPersonLossFlag();//trim(fm.personLossFlag.value);
		String personFlag = "";
//		if (personLossFlag == 1) {
		if ("1".equals(personLossFlag)) {
			personFlag = ",有人傷";
		}
		// 得到其它损失信息(需求不明确,生成规则可能以後还需改动)
		String propCount = "";//getElementCount("prpLthirdPropItemNo");
		String propMessage = "";
		String propMessageTemp = "";
//		for (var j = 1; j < propCount; j++) {//WS暫時無傳這項
//			propMessageTemp = fm.prpLthirdLossItemName[j].value + "受損(" + fm.prpLthirdPropLossDesc[j].value + ");";
//			propMessage = propMessage + propMessageTemp;
//		}
		String prpLregistthirdLicenseNo = prpLregist.getThirdLicenseNo();//trim(fm.prpLregistthirdLicenseNo.value);
		// 拼串得到出险摘要
		prplRegistText = "     " + prplRegistText1 + lossMessage3 + personMessage + propMessage + personFlag;
		if (prpLregistthirdLicenseNo != "") {
			prplRegistText += ",三者車：" + prpLregistthirdLicenseNo;
		}
		String prpLregistRemark = prpLregist.getRemark();//trim(fm.prpLregistRemark.value);
		if (prpLregistRemark != "") {
			prplRegistText += "\n" + "     " + "備註：" + prpLregistRemark;
		}
//		fm.prpLregistTextContextInnerHTML.value = prplRegistText;//js code return
		return prplRegistText;
	}
	
	/**
	 * (外部存入)保存报案时报案页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * mantis：CLM0282、CLM9009，處理人員：DP0713，需求單編號：新核心-多元理賠非車
	 * 盡量保持最原始的程式，不需要的會用"//"來處理不刪除
	 * @param httpServletRequest
	 * @return registDto 报案数据传输数据结构
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public RegistDto externalRiskToDto(HttpServletRequest httpServletRequest,ClaimExternalRiskSourceVo claimExternalRiskSourceVo,PrpCmain prpCmainIn,PrpLregist prpLregistIn) throws Exception {
		RegistDto registDto = new RegistDto();
		/*---------------------报案主表prpLregist------------------------------------*/
		//*m9009 主要畫面取得資料塞入物件區塊 prpLregistLossName範例 prpLregistLossName畫面NAME 
		PrpLregist prpLregist = new PrpLregist();
		PropertyUtils.copyProperties(prpLregist ,prpLregistIn);
		// 交强险迁移 报案类型
		String registType = prpLregist.getRegistType();//httpServletRequest.getParameter("registType");
//		prpLregist.setRegistType(registType);
		prpLregist.setRegistNo((String) httpServletRequest.getAttribute("registNo"));
//		if (httpServletRequest.getAttribute("prpLregistSharingFlag") != null) {
//			prpLregist.setSharingFlag(DataUtils.nullToEmpty(prpLregistIn.getSharingFlag()));// 同業共摊
//		}
//		if (httpServletRequest.getParameter("prpLregistIsCompulsoryBchainClaim") != null) {
//			prpLregist.setIsCompulsoryBchainClaim(DataUtils.nullToEmpty(httpServletRequest.getParameter("prpLregistIsCompulsoryBchainClaim")));// 是否為強制險區塊鏈攤賠案件 
//		}
//		prpLregist.setLflag((String)httpServletRequest.getAttribute("prpLregistLFlag"));
//		prpLregist.setClassCode((String)httpServletRequest.getAttribute("prpLregistClassCode"));
		// 交强险迁移
		if (registType != null && registType.equals("1")) {
			//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種 START
			prpLregist.setRiskCode(ConstantCodes.RISKCODE_DAZ);
			if(!isEmpty((String)httpServletRequest.getAttribute("mainPolicyNo"))){
				prpLregist.setPolicyNo((String)httpServletRequest.getAttribute("mainPolicyNo"));				
			}
			//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種 END
		} else {
			prpLregist.setRiskCode((String)httpServletRequest.getAttribute("prpLregistRiskCode"));
			prpLregist.setPolicyNo((String)httpServletRequest.getAttribute("prpLregistPolicyNo"));
		}
//		prpLregist.setLanguage(httpServletRequest.getParameter("prpLregistLanguage"));
//		prpLregist.setInsuredCode(prpLregistIn.getInsuredCode());//claimExternalRiskSourceVo.getInsuredCode());
//		prpLregist.setInsuredName(prpLregistIn.getInsuredName());//claimExternalRiskSourceVo.getInsuredName());
		// e保通
//		prpLregist.setInsuredPhoneNumber(httpServletRequest.getParameter("prpLregistInsuredPhoneNumber"));
//		prpLregist.setPersonLossFlag(httpServletRequest.getParameter("personLossFlag"));
//		prpLregist.setThirdLicenseNo(httpServletRequest.getParameter("prpLregistthirdLicenseNo"));
		prpLregist.setClauseType(claimExternalRiskSourceVo.getClauseType());
//		prpLregist.setLicenseNo(httpServletRequest.getParameter("prpLregistLicenseNo"));
//		prpLregist.setLicenseColorCode(httpServletRequest.getParameter("prpLregistLicenseColorCode"));
//		prpLregist.setCarKindCode(httpServletRequest.getParameter("prpLregistCarKindCode"));
//		prpLregist.setModelCode(httpServletRequest.getParameter("prpLregistModelCode"));
//		prpLregist.setBrandName(httpServletRequest.getParameter("prpLregistBrandName"));
//		prpLregist.setEngineNo(httpServletRequest.getParameter("prpLregistEngineNo"));
//		prpLregist.setFrameNo(httpServletRequest.getParameter("prpLregistFrameNo"));
//		prpLregist.setRunDistance(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLregistRunDistance"))));
//		prpLregist.setUseYears(Integer.parseInt(DataUtils.nullToZero(httpServletRequest.getParameter("prpLregistUseYears"))));
		prpLregist.setReportDate(new DateTime(claimExternalRiskSourceVo.getReportDate(), DateTime.YEAR_TO_DAY));
		prpLregist.setReportorPhoneNumber(claimExternalRiskSourceVo.getReportorPhoneNumber());
		prpLregist.setReportorMobile(claimExternalRiskSourceVo.getReportorMobile());// 备案人手机
//		prpLregist.setLinkerPostCode(httpServletRequest.getParameter("prpLregistLinkerPostCode"));
		prpLregist.setLinkerAddress(claimExternalRiskSourceVo.getLinkerAddress());
		// 为了兼容大地程序的小时设置情况，所以只保存小时，分和秒先不保存
		prpLregist.setReportHour(StringConvert.newString(claimExternalRiskSourceVo.getReportHour()) + ":" + StringConvert.newString(claimExternalRiskSourceVo.getReportMinute()) + ":00");
//		prpLregist.setReportAddress(httpServletRequest.getParameter("prpLregistReportAddress"));
		prpLregist.setReportorName(claimExternalRiskSourceVo.getReportorName());
		prpLregist.setReportType(claimExternalRiskSourceVo.getReportType());
		prpLregist.setPhoneNumber(!isEmpty(claimExternalRiskSourceVo.getPhoneNumber())?claimExternalRiskSourceVo.getPhoneNumber():claimExternalRiskSourceVo.getReportorPhoneNumber());//claimExternalSourceVo.getReportorPhoneNumber()??
		prpLregist.setDriverMobile(!isEmpty(claimExternalRiskSourceVo.getDriverMobile())?claimExternalRiskSourceVo.getDriverMobile():claimExternalRiskSourceVo.getReportorMobile());// 出险车辆驾驶人手机
		prpLregist.setLinkerName(!isEmpty(claimExternalRiskSourceVo.getLinkerName())?claimExternalRiskSourceVo.getLinkerName():claimExternalRiskSourceVo.getReportorName());
//		prpLregist.setDamageStartDate(new DateTime(httpServletRequest.getParameter("prpLregistDamageStartDate"), DateTime.YEAR_TO_DAY));
		// 为了兼容大地程序的小时设置情况，所以只保存小时，分和秒先不保存
		prpLregist.setDamageStartHour(StringConvert.newString(claimExternalRiskSourceVo.getDamageStartHour()) + ":" + StringConvert.newString(claimExternalRiskSourceVo.getDamageStartMinute()) + ":00");
//		prpLregist.setDamageEndDate(new DateTime(httpServletRequest.getParameter("prpLregistDamageStartDate"), DateTime.YEAR_TO_DAY));
		// 为了兼容大地程序的小时设置情况，所以只保存小时，分和秒先不保存
//		prpLregist.setDamageEndHour(StringConvert.newString(httpServletRequest.getParameter("prpLregistDamageStartHour")) + ":" + StringConvert.newString(httpServletRequest.getParameter("prpLregistDamageStartMinute")) + ":00");
		// 没有保存被保险人地址，单证无法带出
//		String damageDate = new DateTime(claimExternalSourceVo.getDamageStartDate()).toString();
//		String damageHour = claimExternalSourceVo.getDamageStartHour();
		PrpCmain prpCmain = new PrpCmain();//prpCmainIn;//getEndorseViewHelper().findPrpCmain(prpLregist.getPolicyNo(), damageDate, damageHour);
		PropertyUtils.copyProperties(prpCmain ,prpCmainIn);
		prpLregist.setInsuredAddress(prpCmain.getInsuredAddress());
		prpLregist.setDamageCode(DataUtils.dbNullToEmpty(claimExternalRiskSourceVo.getDamageCode()).trim());
//		prpLregist.setDamageName(httpServletRequest.getParameter("prpLregistDamageName"));//???
//		prpLregist.setDamageCodeBZ(DataUtils.dbNullToEmpty(httpServletRequest.getParameter("prpLregistDamageCodeBZ")).trim());// 强制险出险原因代码
//		prpLregist.setDamageNameBZ(httpServletRequest.getParameter("prpLregistDamageNameBZ"));// 强制险出险原因
		prpLregist.setDamageTypeCode(claimExternalRiskSourceVo.getDamageTypeCode());
//		prpLregist.setDamageTypeName(httpServletRequest.getParameter("prpLregistDamageTypeName"));
//		prpLregist.setFirstSiteFlag(httpServletRequest.getParameter("firstSiteFlag"));
//		prpLregist.setAddressCode(httpServletRequest.getParameter("prpLregistAddressCode"));
		// 是否发短信标志位
//		prpLregist.setSendMesFlag(httpServletRequest.getParameter("sendMesFlag"));
		// 交强险迁移
//		prpLregist.setPayselfFlag(httpServletRequest.getParameter("payselfFlag"));// 互碰自赔
//		prpLregist.setPropLossFlag(httpServletRequest.getParameter("propLossFlag"));// 物损

//		prpLregist.setDamageAreaCode(httpServletRequest.getParameter("prpLregistDamageAreaCode"));
//		prpLregist.setDamageAreaName(httpServletRequest.getParameter("prpLregistDamageAreaName"));
//		prpLregist.setDamageAddressType(httpServletRequest.getParameter("damageAddressType"));
		prpLregist.setDamageAddress(claimExternalRiskSourceVo.getDamageAddress());
//		prpLregist.setAuthorityUnit(httpServletRequest.getParameter("prpLregistAuthorityUnit"));//憲警單位
//		prpLregist.setDamageAreaPostCode(httpServletRequest.getParameter("prpLregistDamageAreaPostCode"));
		prpLregist.setHandleUnit(claimExternalRiskSourceVo.getHandleUnit());
		prpLregist.setLossName(claimExternalRiskSourceVo.getLossName());
		//多元需要給null 讓其轉為0
		prpLregist.setLossQuantity(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLregistLossQuantity"))));
//		prpLregist.setUnit(httpServletRequest.getParameter("prpLregistUnit"));
//		prpLregist.setEstiCurrency(httpServletRequest.getParameter("prpLregistEstiCurrency"));
		prpLregist.setEstimateLoss(Double.parseDouble(DataUtils.nullToZero(claimExternalRiskSourceVo.getEstimateLoss()!=null?claimExternalRiskSourceVo.getEstimateLoss().toString():null)));
		prpLregist.setManageType(claimExternalRiskSourceVo.getManageType());
//		prpLregist.setManageTypeName(httpServletRequest.getParameter("prpLregistManageTypeName"));
//		prpLregist.setWeather(httpServletRequest.getParameter("prpLregistWeather"));
//		prpLregist.setWeatherName(httpServletRequest.getParameter("prpLregistWeatherName"));
//		prpLregist.setSection(httpServletRequest.getParameter("prpLregistSection"));
//		prpLregist.setSectionName(httpServletRequest.getParameter("prpLregstSectionName"));
		//多元需要給null 讓其轉為0
		prpLregist.setEstimateFee(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLregistEstimateFee"))));
//		prpLregist.setReceiverName(httpServletRequest.getParameter("prpLregistReceiverName"));
		prpLregist.setHandlerCode(claimExternalRiskSourceVo.getHandlerCode());
//		prpLregist.setHandler1Code(httpServletRequest.getParameter("prpLregistHandler1Code"));
//		prpLregist.setComCode(httpServletRequest.getParameter("prpLregistComCode"));
//		prpLregist.setInputDate(new DateTime(httpServletRequest.getParameter("prpLregistInputDate"), DateTime.YEAR_TO_DAY));
//		prpLregist.setAcceptFlag(httpServletRequest.getParameter("acceptFlag"));
//		prpLregist.setRepeatInsureFlag(httpServletRequest.getParameter("repeatInsureFlag"));
//		prpLregist.setClaimType(httpServletRequest.getParameter("claimType"));
//		if (!isEmpty(httpServletRequest.getParameter("prpLregistCancelDate"))) {
//			prpLregist.setCancelDate(new DateTime(httpServletRequest.getParameter("prpLregistCancelDate"), DateTime.YEAR_TO_DAY));
//		}
//		prpLregist.setDealerCode(httpServletRequest.getParameter("prpLregistDealerCode"));
//		prpLregist.setOperatorCode(httpServletRequest.getParameter("prpLregistOperatorCode"));
//		prpLregist.setMakeCom(httpServletRequest.getParameter("prpLregistMakeCom"));
//		prpLregist.setFlag(httpServletRequest.getParameter("prpLregistFlag"));
		// 原因：添加巨灾代码
//		prpLregist.setCatastropheCode1(httpServletRequest.getParameter("prpCatastropheCode1"));// 巨灾类型
//		prpLregist.setCatastropheName1(httpServletRequest.getParameter("prpCatastropheName1"));// 巨灾名称
//		prpLregist.setCatastropheCode2(httpServletRequest.getParameter("prpCatastropheCode2"));
//		prpLregist.setCatastropheName2(httpServletRequest.getParameter("prpCatastropheName2"));// 巨灾代码
		// 添加垫付赔案类型
//		String advanceType = httpServletRequest.getParameter("prplregistAdvance");
//		if (advanceType != null) {
//			prpLregist.setAdvanceType(advanceType);
//		}
		// 是否团单免导标志
//		prpLregist.setTermFlag(httpServletRequest.getParameter("termFlag"));
		// 意键险需要添加是否呈报字段
//		prpLregist.setReportFlag(httpServletRequest.getParameter("prplregistReportFlag"));
		String strInnerCode = getCodeService().translateRiskCodetoInnerCode(
//				httpServletRequest.getParameter("prpLregistRiskCode")
				prpLregist.getRiskCode()
				);
		if ("YII".equals(strInnerCode)) {
			prpLregist.setBrandName((String)httpServletRequest.getAttribute("prpLregistCargoName"));
//			prpLregist.setBrandName(prpLregistIn.get)??????找不到cargoName
		}
		// 添加报案修改人信息
//		prpLregist.setAlterName(httpServletRequest.getParameter("alterName"));
//		prpLregist.setAlterPhoneNumber(httpServletRequest.getParameter("alterPhoneNumber"));
		prpLregist.setAlterRelationType(claimExternalRiskSourceVo.getRelationType());
//		prpLregist.setAlterTime(new DateTime(httpServletRequest.getParameter("alterTime"), DateTime.YEAR_TO_SECOND));
		prpLregist.setAlterType("claim");// 从理赔系统发起的报案
//		prpLregist.setCoinsFlag(httpServletRequest.getParameter("prpLregistCoinsFlag"));
//		prpLregist.setClaimAgent(httpServletRequest.getParameter("prpLregistClaimAgent"));
//		prpLregist.setAreaCode(httpServletRequest.getParameter("prpLregistAreaCode"));
//		prpLregist.setShipCName(httpServletRequest.getParameter("prpLregistShipCName"));
//		prpLregist.setShipModel(httpServletRequest.getParameter("prpLregistShipModel"));
		// 车险保存报案人与被保险人关系
//		prpLregist.setRelationType(prpLregistIn.getRelationType());//httpServletRequest.getParameter("prpLregistRelationType"));
		String riskType = this.getCodeService().translateRiskCodetoRiskType(prpCmain.getRiskCode());
		if (ConstantCodes.CLASSCODE_E.equals(riskType)) {
			// 原因：添加出险人员信息
			//write List<PersonTrace> PersonTraceList = claimExternalSourceVo.getPersonTraceList();
			//???
//			if (httpServletRequest.getParameter("prpLacciPersonAcciCode") != null || httpServletRequest.getParameter("prpLacciPersonAcciName") != null || httpServletRequest.getParameter("prpLacciPersonAge") != null
//					|| httpServletRequest.getParameter("prpLacciPersonIdentifyNumber") != null) {
//				PrpLacciPerson prpLacciPerson = new PrpLacciPerson();
//				prpLacciPerson.getId().setCertiNo((String) httpServletRequest.getAttribute("registNo"));
//				prpLacciPerson.getId().setCertiType("01");
//				// 交强险迁移
//				prpLacciPerson.setPolicyNo(prpLregist.getPolicyNo());
//				prpLacciPerson.getId().setSerialNo(getPolicyService().findBySeriaNo("1=1") + 1);
//				String endorseNo = this.getEndorseViewHelper().getEndorseNo(prpCmain.getPolicyNo(), damageDate, damageHour);
//				List<PrpCinsured> prpCinsuredList = this.getEndorseViewHelper().findPrpCinsuredFromCopy(endorseNo, prpCmain.getPolicyNo(), prpLregist.getInsuredCode(), prpLregist.getInsuredName());
//				PrpCinsured prpCinsured = this.getEndorseViewHelper().getPrpCinsured(prpCinsuredList, prpLregist.getInsuredCode(), prpLregist.getInsuredName());
//				int serialNo = prpCinsured.getId().getSerialNo();
//				prpLacciPerson.setFamilyNo(DataUtils.getInteger(serialNo));
//				prpLacciPerson.setAcciCode(prpCinsured.getInsuredCode());
//				prpLacciPerson.setAcciName(prpCinsured.getInsuredName());
//				prpLacciPerson.setIdentifyNumber(prpCinsured.getIdentifyNumber());
//				prpLacciPerson.setIdentifyType(prpCinsured.getIdentifytype());
//				prpLacciPerson.setPhone(prpCinsured.getPhoneNumber());
//				int[] serialnos = new int[] { serialNo };
//				List<PrpCinsuredNature> prpCinsuredNatureList = this.getEndorseViewHelper().findPrpCinsuredNatureFromCopy(endorseNo, prpCmain.getPolicyNo(), serialnos);
//				PrpCinsuredNature prpCinsuredNature = this.getEndorseViewHelper().getPrpCinsuredNature(prpCinsuredNatureList, serialNo);
//				if (prpCinsuredNature != null) {
//					prpLacciPerson.setAge(prpCinsuredNature.getAge() == null ? 0 : prpCinsuredNature.getAge().intValue());
//					prpLacciPerson.setSex(prpCinsuredNature.getSex());
//				}
//				registDto.setPrpLacciPerson(prpLacciPerson);
//			}
		}
		//F02 = 於114-11-17日13時36分電話報案:楊靜玉於114-11-17日11時36分受損標的物為建築物,
		String TextTemp = "";
		//remark跟
		if(ConstantCodes.CLASSCODE_D.equals(riskType)){//車
			TextTemp = generateRegistRiskCarText(null,claimExternalRiskSourceVo,prpLregist);//httpServletRequest.getParameter("prpLregistTextContextInnerHTML");
			if(null!=claimExternalRiskSourceVo.getConText() && !"".equals(claimExternalRiskSourceVo.getConText())){
				TextTemp = claimExternalRiskSourceVo.getConText();
			}
			prpLregist.setRemark(TextTemp);
			// 加到ArrayList中
			registDto.setPrpLregist(prpLregist);
			/*---------------------报案文本表prpLregistText------------------------------------*/
			List<PrpLregistText> prpLregistTextList = new ArrayList<PrpLregistText>();
			//generateRegistText 來自DAARegistEdit.js 
			String[] rules = StringUtils.split(TextTemp, RULE_LENGTH);
			// 得到连接串,下面将其切分到数组
			for (int k = 0; k < rules.length; k++) {
				PrpLregistText prpLregistText = new PrpLregistText();
				prpLregistText.getId().setRegistNo((String) httpServletRequest.getAttribute("registNo"));
				prpLregistText.setContext(rules[k]);
				prpLregistText.getId().setLineNo(k + 1);
				prpLregistText.getId().setTextType("1");
				prpLregistTextList.add(prpLregistText);
			}
			// 装入RegistDto
			registDto.setPrpLregistTextList(prpLregistTextList);
			// 原因：添加呈报信息
			if (httpServletRequest.getParameter("prplregistReportFlag") != null && !httpServletRequest.getParameter("prplregistReportFlag").equals("")) {
				List<PrpLregistText> prpLregistTextDtoList2 = new ArrayList<PrpLregistText>();
				String TextTemp2 = httpServletRequest.getParameter("prpLregistTextContextInnerHTML2");
				String[] rules2 = StringUtils.split(TextTemp2, RULE_LENGTH);
				// 得到连接串,下面将其切分到数组
				for (int k = 0; k < rules2.length; k++) {
					PrpLregistText prpLregistText = new PrpLregistText();
					prpLregistText.getId().setRegistNo((String) httpServletRequest.getAttribute("registNo"));
					prpLregistText.setContext(rules2[k]);
					prpLregistText.getId().setLineNo(k + 1);
					prpLregistText.getId().setTextType("4");
					prpLregistTextDtoList2.add(prpLregistText);
				}
				// 装入RegistDto
				registDto.setPrpLregistTextList2(prpLregistTextDtoList2);
			}
		}else{
			TextTemp = generateRegistRiskText(null,claimExternalRiskSourceVo,prpLregist);//httpServletRequest.getParameter("prpLregistTextContextInnerHTML");
			if(null!=claimExternalRiskSourceVo.getConText() && !"".equals(claimExternalRiskSourceVo.getConText())){
				TextTemp = claimExternalRiskSourceVo.getConText();
			}
			prpLregist.setRemark(claimExternalRiskSourceVo.getRemark());
			// 加到ArrayList中
			registDto.setPrpLregist(prpLregist);
			/*---------------------报案文本表prpLregistText------------------------------------*/
			List<PrpLregistText> prpLregistTextList = new ArrayList<PrpLregistText>();
			//generateRegistText 來自DAARegistEdit.js 
			String[] rules = StringUtils.split(TextTemp, RULE_LENGTH);
			// 得到连接串,下面将其切分到数组
			for (int k = 0; k < rules.length; k++) {
				PrpLregistText prpLregistText = new PrpLregistText();
				prpLregistText.getId().setRegistNo((String) httpServletRequest.getAttribute("registNo"));
				prpLregistText.setContext(rules[k]);
				prpLregistText.getId().setLineNo(k + 1);
				prpLregistText.getId().setTextType("1");
				prpLregistTextList.add(prpLregistText);
			}
			// 装入RegistDto
			registDto.setPrpLregistTextList(prpLregistTextList);
			// 原因：添加呈报信息
			if (httpServletRequest.getParameter("prplregistReportFlag") != null && !httpServletRequest.getParameter("prplregistReportFlag").equals("")) {
				List<PrpLregistText> prpLregistTextDtoList2 = new ArrayList<PrpLregistText>();
				String TextTemp2 = httpServletRequest.getParameter("prpLregistTextContextInnerHTML2");
				String[] rules2 = StringUtils.split(TextTemp2, RULE_LENGTH);
				// 得到连接串,下面将其切分到数组
				for (int k = 0; k < rules2.length; k++) {
					PrpLregistText prpLregistText = new PrpLregistText();
					prpLregistText.getId().setRegistNo((String) httpServletRequest.getAttribute("registNo"));
					prpLregistText.setContext(rules2[k]);
					prpLregistText.getId().setLineNo(k + 1);
					prpLregistText.getId().setTextType("4");
					prpLregistTextDtoList2.add(prpLregistText);
				}
				// 装入RegistDto
				registDto.setPrpLregistTextList2(prpLregistTextDtoList2);
			}
		}
		/*---------------------立案操作状态内容prpLclaimStatus------------------------------------*/
		PrpLclaimStatus prpLclaimStatus = new PrpLclaimStatus();
		prpLclaimStatus.setStatus((String)httpServletRequest.getAttribute("buttonSaveType"));
		prpLclaimStatus.getId().setBusinessNo(prpLregist.getRegistNo());
		prpLclaimStatus.setPolicyNo(prpLregist.getPolicyNo());
		prpLclaimStatus.getId().setNodeType("regis");
		prpLclaimStatus.getId().setSerialNo(0);
		// 取得当前用户信息，写操作员信息到实赔中
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		prpLclaimStatus.setHandlerCode(user.getUserCode());
		prpLclaimStatus.setInputDate(prpLregist.getInputDate());
		prpLclaimStatus.setOperateDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLclaimStatus.setRiskCode(prpLregist.getRiskCode());
		registDto.setPrpLclaimStatus(prpLclaimStatus);
		return registDto;
	}
	
	/**
	 * 取初始化信息需要的数据的整理. 填写报案单时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等。取这些信息需要一些入参，
	 * 考虑到接口的一致性，将这些入参作为Dto方式传入，Dto利用聚合而非继承的方式。 整理采用继承的方式分层处理，具体的逻辑放在险种险类子类中整理.
	 * @param httpServletRequest
	 * @return RequestDto 取初始化信息需要的数据
	 * @throws Exception
	 */
	public abstract RegistDto iniViewToDto(HttpServletRequest httpServletRequest) throws Exception;

	/**
	 * 填写报案页面及查询报案request的生成.
	 * 填写报案时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param registDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public abstract void dtoToView(HttpServletRequest httpServletRequest, RegistDto registDto) throws Exception;

	/**
	 * mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種
	 */
	public String generateRegistRiskText(String orgRemark,ClaimExternalRiskSourceVo claimExternalRiskSourceVo,PrpLregist prpLregist){
		String prpLregistDamageStartDate = new DateTime(claimExternalRiskSourceVo.getDamageStartDate()).toString();//trim(fm.prpLregistDamageStartDate.value);
		String prpLregistDamageStartHour = claimExternalRiskSourceVo.getDamageStartHour();//trim(fm.prpLregistDamageStartHour.value);
		String prpLregistDamageStartMinute = claimExternalRiskSourceVo.getDamageStartMinute();//trim(fm.prpLregistDamageStartMinute.value);
		
		String prpLregistReportDate = new DateTime(claimExternalRiskSourceVo.getReportDate()).toString();
		
		String prplRegistText = "       ";
		String prpLregistReportorName = claimExternalRiskSourceVo.getReportorName();
		if (prpLregistReportorName.length() > 0) {
			prplRegistText = prplRegistText + "備案人" + prpLregistReportorName;
		}
		String[] prpLregistDamageStartDateAry = prpLregistDamageStartDate.split("-");
		int year = (new Date().getYear()+1900)-1911;//Integer.parseInt(prpLregistDamageStartDate) - 1911;
		if(null!=prpLregistDamageStartDateAry &&
				prpLregistDamageStartDateAry.length==3){
			year = Integer.parseInt(prpLregistDamageStartDateAry[0]) - 1911;
		}
		
		String date = prpLregistDamageStartDate.substring(4, prpLregistDamageStartDate.length());
//		String prplRegistText1 = year + date + "日" + prpLregistDamageStartHour + "時" + prpLregistDamageStartMinute + "分由" + prpLregistLinkerName + "(" + prpLregistPhoneNumber + ")駕駛";

		String prpLregistReportDateTmp = prpLregistReportDate;
		prpLregistReportDate = (Integer.parseInt(prpLregistReportDateTmp.substring(0,4),10)-1911) + prpLregistReportDateTmp.substring(4,prpLregistReportDateTmp.length());
		if (null!=prpLregistReportDate && prpLregistReportDate.length() > 0) {
			prplRegistText = prplRegistText + "於" + prpLregistReportDate + "日";
		}
		String prpLregistReportHour = claimExternalRiskSourceVo.getReportHour();
		if (null!=prpLregistReportHour && prpLregistReportHour.length() > 0) {
			prplRegistText = prplRegistText + prpLregistReportHour + "時";
		}
		String prpLregistReportMinute = claimExternalRiskSourceVo.getReportMinute();
		if (null!=prpLregistReportMinute && prpLregistReportMinute.length() > 0) {
			prplRegistText = prplRegistText + prpLregistReportMinute + "分";
		}
		String reportType = claimExternalRiskSourceVo.getReportType();
		String strReportType = codeService.translateCodeCode("ReportType", reportType, true);
		if (null!=strReportType && strReportType.length() > 0) {
			prplRegistText = prplRegistText + strReportType + ":";
		}
		String prpLregistInsuredName = prpLregist.getInsuredName();
		if (null!=prpLregistInsuredName && prpLregistInsuredName.length() > 0) {
			prplRegistText = prplRegistText + prpLregistInsuredName;
		}
		String prpLregistDamageStartDateTmp = claimExternalRiskSourceVo.getDamageStartDate();
		String prpLregistDamageStartDateStr = (Integer.parseInt(prpLregistDamageStartDateTmp.substring(0,4),10)-1911) + prpLregistDamageStartDateTmp.substring(4,prpLregistDamageStartDateTmp.length());
		if (null!=prpLregistDamageStartDateStr && prpLregistDamageStartDateStr.length() > 0) {
			prplRegistText = prplRegistText + "於" + prpLregistDamageStartDateStr + "日";
		}
		
		if (null!=prpLregistDamageStartHour && prpLregistDamageStartHour.length() > 0) {
			prplRegistText = prplRegistText + prpLregistDamageStartHour + "時";
		}

		if (null!=prpLregistDamageStartMinute && prpLregistDamageStartMinute.length() > 0) {
			prplRegistText = prplRegistText + prpLregistDamageStartMinute + "分";
		}
		String prpLregistDamageName = claimExternalRiskSourceVo.getLinkerName();
		if (null!=prpLregistDamageName && prpLregistDamageName.length() > 0) {
			prplRegistText = prplRegistText + "由於" + prpLregistDamageName + "原因";
		}
		String prpLregistDamageAddress = claimExternalRiskSourceVo.getDamageAddress();
		if (null!=prpLregistDamageAddress && prpLregistDamageAddress.length() > 0) {
			prplRegistText = prplRegistText + "在" + prpLregistDamageAddress + "位置發生事故，";
		}
		String prpLregistLossName = claimExternalRiskSourceVo.getLossName();
		if (null!=prpLregistLossName && prpLregistLossName.length() > 0) {
			prplRegistText = prplRegistText + "受損標的物為" + prpLregistLossName + ",";
		}
		String prpLregistEstimateLoss = null!=claimExternalRiskSourceVo.getEstimateLoss()?String.valueOf(claimExternalRiskSourceVo.getEstimateLoss()):"";
		if (null!=prpLregistEstimateLoss && prpLregistEstimateLoss.length() > 0) {
			prplRegistText = prplRegistText + "估計損失" + prpLregistEstimateLoss + "台幣";
		}
		
		return prplRegistText;
	}
	
	/**
	 * mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種
	 */
	public String generateRegistRiskCarText(String orgRemark,ClaimExternalRiskSourceVo claimExternalRiskSourceVo,PrpLregist prpLregist){
		// 得到驾驶员信息
		String prplDriver = "";
		String prplRegistText = "";
		if(null!=claimExternalRiskSourceVo && null!=claimExternalRiskSourceVo.getDriverList()
				&& claimExternalRiskSourceVo.getDriverList().size()>0){
			prplDriver = claimExternalRiskSourceVo.getDriverList().get(0).getDriverName().trim();
		}
		
		// 得到标的车牌信息
		String prplLicenseNo = "";
		if(null!=claimExternalRiskSourceVo && null!=claimExternalRiskSourceVo.getThirdPartyList()
				&& claimExternalRiskSourceVo.getThirdPartyList().size()>0){
			prplLicenseNo = claimExternalRiskSourceVo.getThirdPartyList().get(0).getLicenseNo();
		}
		// 得到时间、地点、事故原因、出险原因
		String prpLregistLinkerName = prpLregist.getLinkerName();//trim(fm.prpLregistLinkerName.value); // 得到驾驶人
		String prpLregistPhoneNumber = prpLregist.getPhoneNumber();//trim(fm.prpLregistPhoneNumber.value); // 得到驾驶人联系电话
		String prpLregistDamageStartDate = new DateTime(claimExternalRiskSourceVo.getDamageStartDate()).toString();//trim(fm.prpLregistDamageStartDate.value);
		String prpLregistDamageStartHour = claimExternalRiskSourceVo.getDamageStartHour();//trim(fm.prpLregistDamageStartHour.value);
		String prpLregistDamageStartMinute = claimExternalRiskSourceVo.getDamageStartMinute();//trim(fm.prpLregistDamageStartMinute.value);
		String prpLregistDamageAddress = prpLregist.getDamageAddress();//trim(fm.prpLregistDamageAddress.value);
		String prpLregistDamageName = "";
		if (prpLregist.getRegistType().equals('1')) { //强制险的取强制险出险原因
			prpLregistDamageName = prpLregist.getDamageNameBZ();//prpLregistDamageNameBZ.value;
		} else {
			prpLregistDamageName = prpLregist.getDamageName();
		}
		if (prpLregistLinkerName == "" || prpLregistLinkerName == null) {
			prpLregistLinkerName = "駕駛人";
		}
		if (!isEmpty(prpLregist.getDriverMobile())){//trim(fm.prpLregistDriverMobile.value) != "") { //联系方式默认取手机
			prpLregistPhoneNumber = prpLregist.getDriverMobile().trim();//trim(fm.prpLregistDriverMobile.value);
		}
		if (prpLregistPhoneNumber == "" || prpLregistLinkerName == null) {
			prpLregistPhoneNumber = "聯係電話";
		}
		String[] prpLregistDamageStartDateAry = prpLregistDamageStartDate.split("-");
		int year = (new Date().getYear()+1900)-1911;//Integer.parseInt(prpLregistDamageStartDate) - 1911;
		if(null!=prpLregistDamageStartDateAry &&
				prpLregistDamageStartDateAry.length==3){
			year = Integer.parseInt(prpLregistDamageStartDateAry[0]) - 1911;
		}
		String date = prpLregistDamageStartDate.substring(4, prpLregistDamageStartDate.length());
		String prplRegistText1 = year + date + "日" + prpLregistDamageStartHour + "時" + prpLregistDamageStartMinute + "分由" + prpLregistLinkerName + "(" + prpLregistPhoneNumber + ")駕駛";

		// 得到标的车与三者车损失信息
//		var count = getElementCount("prpLthirdPartySerialNo"); // 受损车数量
//		var serialNoCount = getElementCount("RelateSerialNo"); // 受损部位数量，没有受损部位为1
//		var lossMessage1 = ""; // 标的车信息
//		var lossMessage2 = ""; // 三者车信息
		String lossMessage3 = ""; // 总的出险摘要
//		var lossMessageTemp = ""; // 三者受损部位信息

//		for (var j = 1; j < count; j++) {
//			if (serialNoCount == 1) { // 没有受损部位
//				if (j < (count - 1)) { // 除去标的车
//					lossMessage2 = lossMessage2 + fm.prpLthirdPartyLicenseNo[j + 1].value + "受損;"
//				}
//			} else { // 有受损部位
//				lossMessageTemp = "";
//				for (var k = 0; k < serialNoCount; k++) {
//					if (fm.RelateSerialNo[k].value == "1" && j == 1) { // 标的车有受损部位
//						lossMessage1 = lossMessage1 + fm.partName[k].value + fm.compName[k].value + "、";
//					} else { // 三者车
//						if (fm.RelateSerialNo[k].value == j) {
//							lossMessageTemp = lossMessageTemp + fm.partName[k].value + fm.compName[k].value + "、";
//						}
//					}
//				} // endfor
//				if (j > 1) { // 三者车
//					var position2 = lossMessageTemp.lastIndexOf("、");
//					lossMessageTemp = lossMessageTemp.substring(0, position2);
//					lossMessage2 = lossMessage2 + trim(fm.prpLthirdPartyLicenseNo[j].value) + lossMessageTemp + "受損;";
//				}
//			} // endelse
//		} // endfor

//		var position1 = lossMessage1.lastIndexOf("、");
//		lossMessage1 = lossMessage1.substring(0, position1);
//		lossMessage3 = prplLicenseNo + lossMessage1 + "號車於" + prpLregistDamageAddress + "發生" + prpLregistDamageName + "事故";
		lossMessage3 = prplLicenseNo + "號車於" + prpLregistDamageAddress + "發生" + prpLregistDamageName + "事故";

		// 得到处理部门信息
		String HandleUnitName = "";
		HandleUnitName = prpLregist.getHandleUnitName();//trim(fm.prpLregistHandleUnitName.value);

		// 得到人伤信息(需求不明确,生成规则可能以後还需改动)
		String personCount = "";//getElementCount("prpLpersonTracePersonNo");
		String personMessage = "";
		String personMessageTemp = "";
//		for (var j = 1; j < personCount; j++) {//這裡應該是有受傷的列表
//		if(null!=claimExternalSourceVo && null!=claimExternalSourceVo.getPersonTraceList()
//				&& claimExternalSourceVo.getPersonTraceList().size()>0){
////			personMessageTemp = fm.prpLpersonTracePersonName[j].value + "涉及險種爲" + fm.prpLpersonTraceReferKind[j].value + "受傷部位:" + fm.prpLpersonTracePartDesc[j].value + ";";
//			String personName = claimExternalSourceVo.getPersonTraceList().get(0).getPersonName();
//			String referKind = prpLregist.getReferKind();
//			String partDesc = "";// 
//			personMessageTemp = personName + "涉及險種爲" + referKind + "受傷部位:" + partDesc+ ";";
//			personMessage = personMessage + personMessageTemp;
//		}
		String personLossFlag = prpLregist.getPersonLossFlag();//trim(fm.personLossFlag.value);
		String personFlag = "";
//		if (personLossFlag == 1) {
		if ("1".equals(personLossFlag)) {
			personFlag = ",有人傷";
		}
		// 得到其它损失信息(需求不明确,生成规则可能以後还需改动)
		String propCount = "";//getElementCount("prpLthirdPropItemNo");
		String propMessage = "";
		String propMessageTemp = "";
//		for (var j = 1; j < propCount; j++) {//WS暫時無傳這項
//			propMessageTemp = fm.prpLthirdLossItemName[j].value + "受損(" + fm.prpLthirdPropLossDesc[j].value + ");";
//			propMessage = propMessage + propMessageTemp;
//		}
		String prpLregistthirdLicenseNo = prpLregist.getThirdLicenseNo();//trim(fm.prpLregistthirdLicenseNo.value);
		// 拼串得到出险摘要
		prplRegistText = "     " + prplRegistText1 + lossMessage3 + personMessage + propMessage + personFlag;
		if (prpLregistthirdLicenseNo != "") {
			prplRegistText += ",三者車：" + prpLregistthirdLicenseNo;
		}
		String prpLregistRemark = prpLregist.getRemark();//trim(fm.prpLregistRemark.value);
		if (prpLregistRemark != "") {
			prplRegistText += "\n" + "     " + "備註：" + prpLregistRemark;
		}
//		fm.prpLregistTextContextInnerHTML.value = prplRegistText;//js code return
		return prplRegistText;
	}
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.length() == 0) {
			return true;
		}
		return false;
	}

	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
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

}
