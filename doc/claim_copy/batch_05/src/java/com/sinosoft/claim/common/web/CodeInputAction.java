package com.sinosoft.claim.common.web;

import ins.framework.common.DateTime;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.bl.facade.BLCodeInptFacade;
import com.sinosoft.claim.bl.facade.BLPrpDportFacade;
import com.sinosoft.claim.bl.facade.BLUtiAdminCityFacade;
import com.sinosoft.claim.bl.facade.BLUtiAdminProvinceFacade;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.EndorseService;
import com.sinosoft.claim.common.service.facade.PrpDcarModelService;
import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.service.facade.PrpDcurrencyService;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.service.facade.PrpPitemKindService;
import com.sinosoft.claim.common.service.facade.UtiCodeTransferService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.DataUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.common.vo.EndorseDto;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.dto.domain.PrpDitemDto;
import com.sinosoft.claim.dto.domain.PrpDliabDto;
import com.sinosoft.claim.dto.domain.PrpDportDto;
import com.sinosoft.claim.dto.domain.UtiAdminCityDto;
import com.sinosoft.claim.dto.domain.UtiAdminProvinceDto;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCinsuredNature;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpDagent;
import com.sinosoft.claim.schema.model.PrpDcarModel;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.PrpDcurrency;
import com.sinosoft.claim.schema.model.PrpDpersonFeeCodeRisk;
import com.sinosoft.claim.schema.model.PrpDuser;
import com.sinosoft.claim.schema.model.PrpLInsuranceSurveyor;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclause;
import com.sinosoft.claim.schema.model.PrpLexternalAgency;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLpayObject;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpPhead;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredNatureService;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredService;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpClimitService;
import com.sinosoft.claim.schema.service.facade.PrpDagentService;
import com.sinosoft.claim.schema.service.facade.PrpDcodeService;
import com.sinosoft.claim.schema.service.facade.PrpDpersonFeeCodeRiskService;
import com.sinosoft.claim.schema.service.facade.PrpLInsuranceSurveyorService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLclauseService;
import com.sinosoft.claim.schema.service.facade.PrpLexternalAgencyService;
import com.sinosoft.claim.schema.service.facade.PrpLpayObjectService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.ui.control.action.UIPowerInterface;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.platform.dto.domain.PrpDuserDto.QueryCondition;
import com.sinosoft.platform.ui.control.action.IConstants;
import com.sinosoft.platform.ui.control.action.UIPowerAction;
import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.sysframework.common.util.ParamUtils;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.log.Logger;

/**
 * 代码提交
 * @author 中科软
 *
 */
public class CodeInputAction extends Struts2Action {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/** 日志*/
	private static Logger logger = Logger.getLogger(CodeInputAction.class);
	/** 常数*/
	public static final String CHANGE_METHOD = "change";
	/**常数*/
	public static final String QUERY_METHOD = "query";
	/** 代码查询的任务代码*/
	public static final String CODE_INPUT = "codeInput"; 
	/**常数*/
	public static final String SESS_KEY = "CodeInputCondition";
	/** 代碼服務*/
	private PrpDcodeService prpDcodeService;
	/** 用戶服務*/
	private PrpDuserService prpDuserService;
	/** 機構服務*/
	private PrpDcompanyService prpDcompanyService;
	/** 赔案保单关联服務*/
	private PrplregistrpolicyService prpLregistrpolicyService;
	/** 險種服務*/
	private PrpCitemKindService prpCitemKindService;
	/**被保險人服務*/
	private PrpCinsuredService prpCinsuredService;
	/** 外部機構服務*/
	private PrpLexternalAgencyService prpLexternalAgencyService;
	/** PrpDagentService*/
	private PrpDagentService prpDagentService;
	/** PrpLInsuranceSurveyorService*/
	private PrpLInsuranceSurveyorService prpLInsuranceSurveyorService;//
	/** 賠付對象服務*/
	private PrpLpayObjectService prpLpayObjectService;
	/** 車輛種類服務*/
	private PrpDcarModelService prpDcarModelService;
	/** 幣種服務*/
	private PrpDcurrencyService prpDcurrencyService;
	/** PrpDpersonFeeCodeRiskService*/
	private PrpDpersonFeeCodeRiskService prpDpersonFeeCodeRiskService;
	/** EndorseService*/
	private EndorseService endorseService;
	/** EndorseViewHelper*/
	private EndorseViewHelper endorseViewHelper;
	/** 備案服務*/
	private PrpLregistService prpLregistService;
	private UtiCodeTransferService utiCodeTransferService;
	/** 理算服务 */
	private CompensateService compensateService;
	/** 数据服务 */
	private CodeService codeService;
	/** 限额服务 */
	private PrpClimitService prpClimitService;

	private PrpPitemKindService prpPitemKindService;
	private PrpLclauseService prpLclauseService;
	private PrpCinsuredNatureService prpCinsuredNatureService;
	private DecimalFormat decimalFormat = new DecimalFormat("#");
	private PrpDriskConfigService prpDriskConfigService;
	private PrpLclaimService prpLclaimService;
	/**
	 * 得到SQL条件
	 * @param codeMethod
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	private String getCondition(String codeMethod, String fieldName, String secondName, String fieldValue, String isQueryCode) {
		String strReturn = "";
		String fieldRealName = fieldName;
		if ("N".equals(isQueryCode)) {
			fieldRealName = secondName;
		}
		if (codeMethod.equalsIgnoreCase(CHANGE_METHOD)) {
			strReturn = fieldRealName + " like '" + fieldValue + "%'";
		} else {
			if (!fieldValue.trim().equals("")) {
				if (fieldValue.indexOf(",") > -1) {
					String[] values = StringUtils.split(fieldValue, ",");
					strReturn = fieldRealName + " IN (";
					for (int i = 0; i < values.length; i++) {
						strReturn += "'" + values[i] + "'";
						if (i < values.length - 1) {
							strReturn += ",";
						}
					}
					strReturn += ") ";
				} else {
					strReturn = fieldRealName + " LIKE '" + fieldValue + "%'";
				}
			}
		}
		strReturn = StringUtils.replace(strReturn, "*", "%");
		strReturn = StringUtils.replace(strReturn, "%%", "%");
		if (strReturn.trim().equals("")) {
			strReturn = "1=1";
		}
		return " " + strReturn + " ";
	}
	/***
	 * 車輛種類查詢
	 * @return
	 * @throws Exception
	 */
	public String carModelGroup_Query() throws Exception {
		return query();
	}

	/**
	 * 查询代码，如果要配置代码权限，需要有codeInput的任务权限 命名规范：第一个字母为大写时表示从PRPDCODE表里查询，为小写从其它表查询
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String codeType = request.getParameter("codeType");
		logger.debug("codeType=" + codeType);
		if (DataUtils.emptyToNull(codeType) == null) {
			codeType = "";
			logger.warn("代码輸入找不到codeType");
		}
		try {
			if ("handerCode".equals(codeType)) {// 车险报案接案人
				handerCode(request, response);
			} else if ("modelCode".equals(codeType)) {// 厂牌型号
				modelCode(request, response);
			} else if ("insureComCode".equals(codeType) || "UnderWriteDeptCode".equals(codeType) || "DeptCode".equals(codeType)) {// 承保公司/核赔单位/处理单位
				insureComCode(request, response);
			} else if ("ComCodeByProvinceCode".equals(codeType)) {
				comCodeByProvinceCode(request, response);
			} else if ("CheckPerson".equals(codeType)) {
				checkPerson(request, response);
			} else if ("certainLossHanderCode".equals(codeType)) {
				certainLossHanderCode(request, response);
			} else if ("SelectPerson".equals(codeType)) {
				selectPerson(request, response);
			} else if ("currency".equals(codeType)) {
				currencycode(request, response);
			} else if ("payObject".equals(codeType)) {// 追偿支付对象代码
				payObject(request, response);
			} else if ("policyItemKindCodeNoRisk".equals(codeType)) {
				policyItemKindCodeNoRisk(request, response);
			} else if ("policyItemKindCodeForAcci".equals(codeType)) {
				policyItemKindCodeForAcci(request, response);
			} else if ("prpCinsured".equals(codeType)) {
				prpCinsured(request, response);
			}else if("PrpCinsuredAcci".equals(codeType)){
				prpCinsured(request, response);
			} else if ("policyItemCode".equals(codeType) || "PolicyItemKindCode".equals(codeType)) {
				policyItemCode(request, response);
			} else if ("prpDliab".equals(codeType)) {// 责任明细
				prpDliab(request, response);
			} else if ("factory".equals(codeType)) {
				factory(request, response);
			} else if ("PersonFeeTypeFlag".equals(codeType)) {// 获取人伤费用类型
				PersonFeeTypeFlag(request, response, codeType);
			//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
			} else if ("PersonFeeTypeFlagE9".equals(codeType)) {// 获取人伤费用类型
				PersonFeeTypeFlag(request, response, codeType);
			//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END
			} else if ("prpdcompany".equals(codeType) ||"prpdcompany2".equals(codeType) ||"prpdcompany3".equals(codeType)) { // 获取机构代码
				Prpdcompany(request, response, codeType);
			} else if ("prpdCustomerUnit".equals(codeType)) {// 获取机构代码
				PrpdCustomerUnit(request, response, codeType);
			} else if ("KindCodeForPerson".equals(codeType)) {
				policyKindCodeForPerson(request, response);
			} else if ("policyKindCodeForProp".equals(codeType)) {
				policyKindCodeForProp(request, response);
			} else if ("PolicyKindCodeForReplevy".equals(codeType)) {
				policyKindCodeForReplevy(request, response);
			} else if ("carKind".equals(codeType)) {
				carKind(request, response);
			} else if ("CatastropheCode2".equals(codeType)) {
				catastropheCode2(request, response);
			} else if ("getCinsured".equals(codeType)) {
				getCinsured(request, response);
			} else if ("getPayObject".equals(codeType)) {
				getPayObject(request, response);
			} else if ("queryLevel2Com".equals(codeType)) {
				queryLevel2Com(request, response);
			} else if ("queryProvince".equals(codeType)) {
				queryProvince(request, response);
			} else if ("queryUserHaveRights".equals(codeType)) {
				queryUserHaveRights(request, response);
			} else if ("getReplevyPayObject".equals(codeType)) {
				getReplevyPayObject(request, response);
			} else if ("utiAdminProvice".equals(codeType)) {
				utiAdminProvice(request, response);
			} else if ("utiAdminCity".equals(codeType)) {
				utiAdminCity(request, response);
			} else if ("BusinessSource".equals(codeType) || "BusinessSource1".equals(codeType) || "BusinessSource2".equals(codeType)) {
				// 按三级获取行业
				queryBusinessSource(request, response, codeType);
			} else if ("getExternalAgency".equals(codeType)) {// 获取外部机构代码
				getExternalAgency(request, response, codeType);
			} else if ("getInsuranceSurveyor".equals(codeType)) {
				getInsuranceSurveyor(request, response, codeType);
			} else if ("AgentCode".equals(codeType)) {// 代理人
				Prpdagent(request, response, codeType);
			} else if ("portCode".equals(codeType)) {// 港口
				portCode(request, response, codeType);
			} else if ("foreignCountryCode".equals(codeType)) {
				foreignCountryCode(request, response, codeType);
			} else if (DataUtils.dbNullToEmpty(codeType).equals("policyKindCodeOfPerson")){
				policyKindCodeOfPerson(request, response, codeType);
			} else if (DataUtils.dbNullToEmpty(codeType).startsWith("PolicyKindCode")) {//双击域选择险别的
				policyKindCode(request, response, codeType);
			} else if ("InjuryCode".equals(codeType) || "InjuryItemCode".equals(codeType)) {//获取残废项目和残废程度
				queryInjury(request, response, codeType);
			} else {
				queryPrpDcode(request, response, codeType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "query";
	}

	/***
	 * 获取残废项目和残废程度
	 * @param request
	 * @param response
	 * @param codeType
	 * @throws Exception 
	 */
	private void queryInjury(HttpServletRequest request, HttpServletResponse response, String codeType) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");
		String conditions = " ";
		conditions = getCondition(codeMethod, "codeCode", "codeCName", fieldValue, isQueryCode);
		conditions += " AND codetype = 'InjuryCode' ";
		String[] others = StringUtils.split(otherCondition,"|");
		if ("InjuryCode".equals(codeType)) {
			conditions += " AND FLAG = '1' ";
		}else if ("InjuryItemCode".equals(codeType)){
			conditions += " AND FLAG = '2' ";
			conditions += " and uppercode = '" + others[1] + "'";
		}
		conditions += " and newCodeCode in (select codeCode from prpDcodeRisk where codeType='InjuryCode' and (riskCode='"+others[0]+"' or riskCode='0000'))";
		conditions += " AND validstatus='1' order by codecode";
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpDcode", pageNo, rowsPerPage, conditions);
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpDcode");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = queryCondition.getConditions();
			}
		}
		Page page = this.prpDcodeService.findByConditions(conditions, pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpDcode");
	}
	/***
	 * 追偿险别选择，只显示本案已赔付的险别
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void policyKindCodeForReplevy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");// Y代码 N中文
		String[] arrValue = StringUtils.split(otherCondition.trim(), "|");
		String policyNo = arrValue[0];
		String claimNo = arrValue[1];
		String familyNo = arrValue[2];
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(claimNo);
		String riskType = this.codeService.translateRiskCodetoRiskType(prpLclaim.getRiskCode());
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		List<PrpCitemKind> prpCitemKindList = null;
		if (ConstantCodes.CLASSCODE_E.equals(riskType)) {
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, Integer.parseInt(familyNo));
		} else {
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, null, null);
		}
		List<PrpLloss> lossList = this.compensateService.getPrpLlossForReplevy(claimNo);
		Set<String> kindCodes = new HashSet<String>();
		for(PrpLloss p : lossList){
			kindCodes.add(p.getKindCode());
		}
		List<PrpCitemKind> list = new ArrayList<PrpCitemKind>();
		int size = prpCitemKindList.size();
		if(size > 0){
			int start = (pageNo - 1) * rowsPerPage;
			int end = start + rowsPerPage;
			String kindCode = null;
			String kindName = null;
			for (; start < end && start < size; start++) {
				PrpCitemKind prpCitemKind = prpCitemKindList.get(start);
				kindCode = prpCitemKind.getKindCode();
				kindName = prpCitemKind.getKindName();
				if (kindCodes.contains(kindCode)) {
					if (!CommonUtils.isEmpty(fieldValue)) {
						if ("Y".equals(isQueryCode) && !kindCode.startsWith(fieldValue)) {
							continue;
						} else if ("N".equals(isQueryCode) && !kindName.startsWith(fieldValue)) {
							continue;
						}
					}
					list.add(prpCitemKind);
				}
			}
		}
		Page page = new Page((pageNo-1)*rowsPerPage + 1, size, rowsPerPage, list);
		request.setAttribute("page",page);
		this.setData(request, list, "PrpCitemKind");
	}
	/**
	 * 分页功能，继续查询
	 * @return
	 * @throws Exception
	 */
	public String queryContinue() throws Exception {
		return query();
	}

	/**
	 * 无分页功能，首页登陆双击仍然保留
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryLogon() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String codeType = request.getParameter("codeType");
		logger.debug("codeType=" + codeType);
		if (DataUtils.emptyToNull(codeType) == null) {
			codeType = "";
			logger.warn("代码輸入找不到codeType");
		}
		try {
			if (codeType.equals("comCodeByUserCode")) {
				queryComCodeByUserCode(request, response);
			} else {
				queryPrpDcode(request, response, codeType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据用户代码查询他所在的机构
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryComCodeByUserCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("userCode=");
		String userCode = "";
		if (start > -1) {
			userCode = otherCondition.substring(start + "userCode=".length()).trim();
		}
		List<PrpDcompany> result = null;
		if (!userCode.equals("")) {
			result = this.prpDcompanyService.findUserGradeCompanyListByUserCode(userCode);
		}
		this.setData(request, result, "PrpDcompany");
	}

	/***
	 * 从prpDCode表里查询
	 * @param request
	 * @param response
	 * @param codeType
	 * @throws Exception
	 */
	private void queryPrpDcode(HttpServletRequest request, HttpServletResponse response, String codeType) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");
		String conditions = " ";
		conditions = getCondition(codeMethod, "codeCode", "codeCName", fieldValue, isQueryCode);
		conditions += " AND codetype ='" + codeType + "' ";
		if ("AcciAddress".equals(codeType)) {
			if ("20".equals(otherCondition)) {
				otherCondition = "22";
			}
			conditions += "and codeCode like '" + otherCondition + "%'";
		}
		/** 特殊处理 */
		// 得到伤残等级，只有伤残类责任才能选择伤残等级
		if ("InjuryGrade".equals(codeType)) {
			String[] arrInjuryGrade = StringUtils.split(otherCondition.trim(), "|");
			conditions += " and codecode in (select codecode from PrpDcodeRisk where (riskcode='" + arrInjuryGrade[0] + "'  OR riskcode='0000') and codetype='" + codeType + "')";
		} else if ("ChargeCode".equals(codeType) || "CaseCode".equals(codeType)) {// 出险原因特殊处理/费用
			conditions += " AND Codecode in (select codecode from PrpDcodeRisk where ( riskcode='" + otherCondition + "' Or riskCode='0000' ) and codetype='" + codeType + "')";
			if(ConstantCodes.RISKCODE_DAZ.equals(otherCondition)&&"ChargeCode".equals(codeType)) {//强制险费用不带出延迟利息
				conditions += " AND Codecode not in ('D')";
			}
		} else if ("DamageTypeCode".equals(codeType)) {// 出险原因特殊处理/费用
			conditions += " AND Codecode in (select codecode from PrpDcodeRisk where riskcode='" + otherCondition + "' and codetype='" + codeType + "')";
		} else if ("ReplevyChargeCode".equals(codeType)) {// 追偿费用特殊处理
			conditions += " AND Codecode in (select codecode from PrpDcodeRisk where riskcode='" + otherCondition + "' and codetype='" + codeType + "')";
			conditions = conditions.replaceAll("ReplevyChargeCode", "ChargeCode");
		} else if ("CountryCode".equals(codeType)) {// 查询国外国家名去掉中国、中国台湾
			conditions += " and codecode not in ('CTN','CHN')";
		} else if ("PersonFeeType".equals(codeType)) {// 人伤定损
			conditions += " AND Codecode in (select codecode from PrpDcodeRisk where ( riskcode='" + otherCondition + "' Or riskCode='0000' ) and codetype='" + codeType + "')";
		} else if("DamageCode".equals(codeType)){
			conditions += " AND Newcodecode in (select codecode from PrpDcodeRisk where ( riskcode='" + otherCondition + "' Or riskCode='0000' ) and codetype='" + codeType + "')";
		}else if("DangerousClassSubItem".equals(codeType)){
			conditions += " AND upperCode = '" + otherCondition + "' ";
		}
		if("DamageCode".equals(codeType)){
			conditions += " AND validstatus='1' order by to_number(newCodecode),codeCode";
		}else{
			conditions += " AND validstatus='1' order by codecode";
		}
		
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpDcode", pageNo, rowsPerPage, conditions);
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpDcode");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = queryCondition.getConditions();
			}
		}
		Page page = this.prpDcodeService.findByConditionBySql(conditions, pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpDcode");
	}

	/***
	 * 查询接案人 经办人（车险报案）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void handerCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String otherCondition = paramUtils.getParameter("otherCondition");
		String[] arrValue = StringUtils.split(otherCondition.trim(), "|");
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String comCode = ((UserDto) request.getSession().getAttribute("user")).getComCode();
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");
		StringBuffer conditions = new StringBuffer();
		conditions.append(getCondition(codeMethod, "UserCode", "UserName", fieldValue, isQueryCode));
		conditions.append(" AND ValidStatus='1' ");
		if("ALL".equals(otherCondition)){
			conditions.append(" AND usercode in (select usercode from utiusergrade where gradecode='005')");
		} else {
			if(arrValue.length > 1 && "RISKCODE".equals(arrValue[0])){
				//修正理算車體險訊息抓取不到當前處理該業務人員的問題。
				String riskCode = arrValue[1];
				//車體險訊息理賠經辦人處理。抓取包含擁有該險種和機構權限的人員。
				conditions.append(" AND exists ( ");
				conditions.append(" select 0 from utiusergrade t where comCode = '" + comCode + "' and usercode = PrpDuser.userCode and gradecode in ('003','005','009') and validstatus = '1' ");
				conditions.append(" and exists ( select 0 from utiusergradepower where comCode = t.comCode and gradecode = t.gradecode ");
				conditions.append(" and ( permitriskcode = '*' OR permitriskcode like '%"+riskCode+"%' ) and userCode = t.userCode ");
				conditions.append(" )) ");
			} else {
				conditions.append(" AND comCode in (select ComCode from prpdCompany Start With ComCode = '" + comCode + "' Connect By Prior comCode = uppercomCode and prior comcode != comcode and validstatus = '1')");
			}
		}
		conditions.append(" Order by usercode");
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpDuser", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpDuser");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = new StringBuffer(queryCondition.getConditions());
			}
		}
		Page page = this.prpDuserService.findByPage(conditions.toString(), pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpDuser");
	}
	/**
	 * 工廠查詢
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void factory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String fieldValue = paramUtils.getParameter("fieldValue");
		StringBuffer conditions = new StringBuffer();
		if (fieldValue.length() != 0) {
			conditions.append("factory like'%" + fieldValue + "%'");
		}
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpDCarmodel", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpDCarmodel");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = new StringBuffer(queryCondition.getConditions());
			}
		}
		Page page = this.prpDcarModelService.findByConditionsFactory(conditions.toString(), pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpDcarModel", "factory");
	}

	/**
	 * 厂牌型号(用於车险）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void modelCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");// Y代码 N中文
		StringBuffer conditions = new StringBuffer();
		conditions.append(getCondition(codeMethod, "ModelCode", "ModelName", fieldValue, isQueryCode));
		conditions.append(" ORDER BY ModelCode");
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpDCarmodel", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpDCarmodel");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = new StringBuffer(queryCondition.getConditions());
			}
		}
		Page page = this.prpDcarModelService.findByPage(conditions.toString(), pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpDcarModel", "modelCode");
	}

	/**
	 * 得到承保公司
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void insureComCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");// Y代码 N中文
		StringBuffer conditions = new StringBuffer();
		conditions.append(getCondition(codeMethod, "ComCode", "ComCName", fieldValue, isQueryCode));
		conditions.append(" AND ValidStatus='1'");
		conditions.append(" Order By ComCode");
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpDCompany", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpDCompany");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = new StringBuffer(queryCondition.getConditions());
			}
		}
		Page page = this.prpDcompanyService.findByPage(conditions.toString(), pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpDcompany");
	}

	/***
	 * 得到查勘处理单位
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void comCodeByProvinceCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");// Y代码 N中文
		// 此段代码严重有问题，明确业务规则尽早改变
		String conditions = "(";
		conditions += getCondition(codeMethod, "ComCode", "ComCName", fieldValue, isQueryCode) + ")";
		conditions +=" order by comCode,comLevel";
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpDCompany", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpDCompany");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = queryCondition.getConditions();
			}
		}
		Page page = this.prpDcompanyService.findByPage(conditions.toString(), pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpDcompany");
	}
	/**
	 * 意健险取险别
	 */
	private void policyKindCodeOfPerson(HttpServletRequest request, HttpServletResponse response, String codeType) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");// Y代码 N中文
		String[] arrValue = StringUtils.split(otherCondition.trim(), "|");
		String policyNo = arrValue[0];
		String damageDate = arrValue[1];
		String damageHour = arrValue[2];
		String familyNo = arrValue[3];
		StringBuffer conditions = new StringBuffer();
		conditions.append(getCondition(codeMethod, "KindCode", "KindName", fieldValue, isQueryCode));
		// 意健险人伤险别查询，根据policyNo和FamilyNo
		conditions.append(" AND PolicyNo= '" + policyNo+ "'");
		if (DataUtils.emptyToNull(familyNo)!=null && !"0".equals(familyNo)) {
			conditions.append(" AND FamilyNo= " + familyNo);
		}
		conditions.append(" Order By familyno");
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpCItemKind", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpCItemKind");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = new StringBuffer(queryCondition.getConditions());
			}
		}
		//查询出的保单承保险别
		List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, Integer.parseInt(familyNo));
		// 先做过滤，提高查询时间。保证分页不会出问题
		List<PrpCitemKind> list = new ArrayList<PrpCitemKind>();
		int size = prpCitemKindList.size();
		if(size > 0){
			int start = (pageNo - 1) * rowsPerPage;
			int end = start + rowsPerPage;
			String kindCode = null;
			String kindName = null;
			for (; start < end && start < size; start++) {
				PrpCitemKind prpCitemKind = prpCitemKindList.get(start);
				kindCode = prpCitemKind.getKindCode();
				kindName = prpCitemKind.getKindName();
				if(!CommonUtils.isEmpty(fieldValue)){
					if("Y".equals(isQueryCode) && !kindCode.startsWith(fieldValue)){
						continue;
					} else if("N".equals(isQueryCode) && !kindName.startsWith(fieldValue)){
						continue;
					}
				}
				list.add(prpCitemKind);
			}
		}
		Page page = new Page((pageNo-1)*rowsPerPage + 1, size, rowsPerPage, list);
		request.setAttribute("page",page);
		this.setData(request, list, "PrpCitemKind");
	}
	/***
	 * 获得双击域相应险别
	 * @param request
	 * @param response
	 * @param codeType
	 * @throws Exception
	 */
	private void policyKindCode(HttpServletRequest request, HttpServletResponse response, String codeType) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");// Y代码 N中文
		String[] arrValue = StringUtils.split(otherCondition.trim(), "|");//0保单；1出险日期；2出险小时；
		StringBuffer conditions = new StringBuffer("");
		conditions.append(getCondition(codeMethod, "KindCode", "KindName", fieldValue, isQueryCode));
		conditions.append(" AND POLICYNO ='"+arrValue[0]+"' Order By KindCode");
		// 先做过滤，提高查询时间。保证分页不会出问题
		Page page = this.prpCitemKindService.findKindCodeAndNameByConditionsDistinct(conditions.toString(),0,0);
		List<?> resultList = page.getResult();//查询出的保单承保险别
		List<?> retainList = null;
		if(arrValue.length>3&&DataUtils.emptyToNull(arrValue[3])!=null){
			//根据批单好做回滚
			retainList = this.retain(resultList, this.backWardPrpCitemKind(arrValue[0],arrValue[1], arrValue[2],null,arrValue[3]));
		}else{
			retainList = this.retain(resultList, this.backWardPrpCitemKind(arrValue[0], arrValue[1], arrValue[2],null,null));
		}
		
		List<PrpCitemKind> list = new ArrayList<PrpCitemKind>();
		if(!CommonUtils.isEmpty(retainList)){
			//临时存储 属于当前双击域选择范围的险别
			Set<String> tempKindSet = new HashSet<String>();
			boolean allFlag = false;
			if("PolicyKindCodeForCarAndProp".equals(codeType)){//车财
				tempKindSet.addAll(ConstantsCollection.KindCodeForCar);
				tempKindSet.addAll(ConstantsCollection.KindCodeForProp);
			}else if("PolicyKindCodeForPerson".equals(codeType)){//人伤
				tempKindSet.addAll(ConstantsCollection.KindCodeForPerson);
			}else if("PolicyKindCodeForProp".equals(codeType)){//财产
				tempKindSet.addAll(ConstantsCollection.KindCodeForProp);
			}else if("PolicyKindCodeForCar".equals(codeType)){//车损
				tempKindSet.addAll(ConstantsCollection.KindCodeForCar);
			}else if("PolicyKindCodeForMainCar".equals(codeType)){//主车损
				tempKindSet.addAll(ConstantsCollection.MainCarLoss);
			}else if("PolicyKindCodeForThirdCar".equals(codeType)){//三者车损
				tempKindSet.addAll(ConstantsCollection.ThirdCarLoss);
			}else {
				allFlag = true;
				tempKindSet.addAll(ConstantsCollection.KindCodeForAll);
			}
			Iterator<?> it = retainList.iterator();
			while (it.hasNext()) {
				PrpCitemKind tempPrpCitemKind = (PrpCitemKind)it.next();
				//筛选有效险别、在赔付范围内的险别
				if(allFlag || tempKindSet.contains(tempPrpCitemKind.getKindCode())){
					list.add(tempPrpCitemKind);
				}
			}
		}
		//承保的批单承保险别
		int size = list.size();
		request.setAttribute("page",new Page(1, size, size==0?20:size, list));
		if ("PolicyKindCode".equals(codeType)) {
			this.setData(request, list, "PrpCitemKind", "PolicyKindCode");
		} else {
			this.setData(request, list, "PrpCitemKind");
		}
	}
	/***
	 * 险别查询结果集和通过批单回滚的险别结果集的交集
	 * @param resultList 查询险别集
	 * @param backWardList 批单回滚险别集
	 * @return
	 */
	private List<PrpCitemKind> retain(List<?> resultList, List<?> backWardList){
		Map<String,PrpCitemKind> retainMap = new LinkedHashMap<String,PrpCitemKind>();
		if(resultList!=null && !resultList.isEmpty()){
			Set<String> retainSet = new HashSet<String>();
			Iterator<?> it = resultList.iterator();
			while (it.hasNext()) {
				PrpCitemKind temp = (PrpCitemKind)it.next();
				retainSet.add(temp.getKindCode());
			}
			Iterator<?> it1 = backWardList.iterator();
			while (it1.hasNext()) {
			PrpCitemKind temp = (PrpCitemKind) it1.next();
				if(retainSet.contains(temp.getKindCode())){
					retainMap.put(temp.getKindCode(), temp);
				}
			}
		}
		List<PrpCitemKind> list = new ArrayList<PrpCitemKind>();
		list.addAll(retainMap.values());
		return list;
	}
	/***
	 * 险别查询结果集和通过批单回滚的险别结果集的交集
	 * @param resultList 查询险别集
	 * @param backWardList 批单回滚险别集
	 * @return
	 */
	private List<PrpCitemKind> retainItemCode(List<?> resultList, List<?> backWardList){
		Map<String,PrpCitemKind> retainMap = new LinkedHashMap<String,PrpCitemKind>();
		if(resultList!=null && !resultList.isEmpty()){
			Set<String> retainSet = new HashSet<String>();
			Iterator<?> it = resultList.iterator();
			while (it.hasNext()) {
				PrpCitemKind temp = (PrpCitemKind)it.next();
				retainSet.add(temp.getKindCode()+temp.getId().getItemKindNo());
			}
			Iterator<?> it1 = backWardList.iterator();
			while (it1.hasNext()) {
			PrpCitemKind temp = (PrpCitemKind) it1.next();
				if(retainSet.contains(temp.getKindCode()+temp.getId().getItemKindNo())){
					retainMap.put(temp.getKindCode()+temp.getId().getItemKindNo(), temp);
				}
			}
		}
		List<PrpCitemKind> list = new ArrayList<PrpCitemKind>();
		list.addAll(retainMap.values());
		return list;
	}
	/**
	 * 回滚保单出险时的险别
	 * @param policyNo 保单号码
	 * @param damageStartDate 出险日期
	 * @param damageStartHour 出险小时
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<PrpCitemKind> backWardPrpCitemKind(String policyNo,String damageStartDate,String damageStartHour,String familyNo,String endorseNo) throws Exception{
		String configValue = prpDriskConfigService.getConfigValue("BACKWARDCOPYMAIN","*");
		List<PrpCitemKind> prpCitemKindList = null;
		if("1".equals(configValue)){
			prpCitemKindList = this.backWardPrpCitemKindByEndorseNo(policyNo, damageStartDate, damageStartHour, familyNo,endorseNo);
			return prpCitemKindList;
		}
		String conditions = " policyno = '"+policyNo+"'";
		if(DataUtils.emptyToNull(familyNo)!=null){
			conditions += " and familyNo='"+familyNo+"' ";
		}
		conditions += " Order By familyNo,KindCode ";
		//查最新险别
		prpCitemKindList = this.prpCitemKindService.findKindCodeAndNameByConditionsDistinct(conditions,0,0).getResult();
		// 取得批改信息表信息
		String timeTemp = StringConvert.toStandardTime(damageStartHour);//处理出险小时未经格式的情况
		damageStartHour = timeTemp.substring(0, 2);
		String iWherePart = "PolicyNo = '" + policyNo + "'" + " AND (ValidDate >to_date('" + damageStartDate + "','yyyy-MM-dd') OR (ValidDate=to_date('" + damageStartDate + "','yyyy-MM-dd') AND ValidHour>" + Integer.parseInt(damageStartHour) + "))"
				+ " AND UnderWriteFlag in ('1', '3') " + " ORDER BY InputDate DESC,EndorseTimes DESC ";
		PrpPhead prpPhead = null;
		List<?> listTemp = (ArrayList<?>) this.endorseService.findByPrpPheadConditions(iWherePart);
		EndorseDto endorseDto = null;
		for (int i = 0; i < listTemp.size(); i++) {
			prpPhead = (PrpPhead) listTemp.get(i);
			conditions = "endorseNo='"+prpPhead.getEndorseNo()+"' ";
			if(DataUtils.emptyToNull(familyNo)!=null){
				conditions += " and familyNo='"+familyNo+"' ";
			}
			endorseDto = new EndorseDto();
			endorseDto.setPrpPitemKindList(this.prpPitemKindService.findByConditions(conditions));
			this.endorseViewHelper.backWardPrpCitemKind(prpCitemKindList, endorseDto);
			if(!CommonUtils.isEmpty(endorseNo)&&prpPhead.getEndorseNo().equals(endorseNo)){
				break;
			}
		}
		return prpCitemKindList;
	}
	/**
	 * 回滚保单出险时的险别
	 * @param policyNo 保单号码
	 * @param damageStartDate 出险日期
	 * @param damageStartHour 出险小时
	 * @return
	 * @throws Exception
	 */
	private List<PrpCitemKind> backWardPrpCitemKindByEndorseNo(String policyNo,String damageStartDate,String damageStartHour,String familyNo,String endorseNo) throws Exception{
		String strEndorseNo = policyNo;
		if(!CommonUtils.isEmpty(endorseNo)){
			strEndorseNo = endorseNo;
		}else{
			String timeTemp = StringConvert.toStandardTime(damageStartHour);//处理出险小时未经格式的情况
			damageStartHour = timeTemp.substring(0, 2);
			String iWherePart = "PolicyNo = '" + policyNo + "'" + " AND (ValidDate <to_date('" + damageStartDate + "','yyyy-MM-dd') OR " + //
					"(ValidDate=to_date('" + damageStartDate + "','yyyy-MM-dd') AND ValidHour<=" + damageStartHour + "))" + //
					" AND UnderWriteFlag in ('1', '3') " + " ORDER BY InputDate DESC,EndorseTimes DESC ";
			List<PrpPhead> listTemp = this.endorseService.findByPrpPheadConditions(iWherePart);
			if (!CommonUtils.isEmpty(listTemp)) {
				PrpPhead prpPhead = listTemp.get(0);
				strEndorseNo = prpPhead.getEndorseNo();
			}
		}
		List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.backWardPrpCitemKind(null,familyNo,strEndorseNo);
		return prpCitemKindList;
	}
	/***
	 * 查勘调度处理（查勘人员）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void checkPerson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");
		StringBuffer conditions = new StringBuffer();
		conditions.append(getCondition(codeMethod, "UserCode", "UserName", fieldValue, isQueryCode));
		conditions.append(" AND ValidStatus='1'");
		conditions.append(" and usercode in (select distinct usercode");
		conditions.append(" from utiusergrade");
		conditions.append(" where gradecode in");
		conditions.append(" (select gradecode from utigradetask where taskcode = 'claim.check.insert')");
		conditions.append(" AND ComCode in (");
		conditions.append(" Select ComCode From PrpDcompany");
		conditions.append(" Start With");
		conditions.append(" ComCode = '" + otherCondition + "'");
		conditions.append(" Connect By Prior comCode =  uppercomCode");
		conditions.append(" And prior ComCode != ComCode");
		conditions.append(" And validstatus='1')");
		conditions.append(")");
		conditions.append(" Order by usercode");
		PrpDuserDto user = new PrpDuserDto();
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			user.setQueryCondition("PrpDuser", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute(SESS_KEY, user);
		} else {
			user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpDuser");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = new StringBuffer(queryCondition.getConditions());
			}
		}
		Page page = this.prpDuserService.findByPage(conditions.toString(), pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, this.checkPower(user, page.getResult(), "claim.check.insert"), "PrpDuser");
	}

	/**
	 * 選擇人員
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void selectPerson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");
		StringBuffer conditions = new StringBuffer();
		conditions.append(getCondition(codeMethod, "UserCode", "UserName", fieldValue, isQueryCode));
		conditions.append(" AND ValidStatus='1'");
		conditions.append(" AND ComCode in (");
		conditions.append(" Select ComCode From PrpDcompany");
		conditions.append(" Start With");
		conditions.append(" ComCode = '" + otherCondition + "'");
		conditions.append(" Connect By Prior comCode =  uppercomCode");
		conditions.append(" And prior ComCode != ComCode");
		conditions.append(" And validstatus='1')");
		conditions.append(" Order by usercode");
		PrpDuserDto user = new PrpDuserDto();
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			user.setQueryCondition("PrpDuser", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute(SESS_KEY, user);
		} else {
			user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpDuser");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = new StringBuffer(queryCondition.getConditions());
			}
		}
		Page page = this.prpDuserService.findByPage(conditions.toString(), pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpDuser");
	}

	/**
	 * 定损调度任务处理（定损人员）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void certainLossHanderCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		String[] arrValue = StringUtils.split(otherCondition.trim(), "|");//定損節點類型、機構
		String nextNodeType = arrValue[0];
		String comcode = arrValue[1];
		String taskcode = "";
		if("certa".equals(nextNodeType)){
			taskcode = "claim.certaincarloss.insert";
		} else if("wound".equals(nextNodeType)){
			taskcode = "claim.certainpersonloss.insert";
		} else if("propc".equals(nextNodeType)){
			taskcode = "claim.certainloss.insert";
		}
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");
		StringBuffer conditions = new StringBuffer();
		conditions.append(getCondition(codeMethod, "UserCode", "UserName", fieldValue, isQueryCode));
		conditions.append(" AND ValidStatus='1'");
		conditions.append(" and usercode in (select distinct usercode");
		conditions.append(" from utiusergrade");
		conditions.append(" where gradecode in");
		conditions.append(" (select gradecode from utigradetask where taskcode = '"+taskcode+"')");
		conditions.append(" AND ComCode in (");
		conditions.append(" Select ComCode From PrpDcompany");
		conditions.append(" Start With");
		conditions.append(" ComCode = '" + comcode + "'");
		conditions.append(" Connect By Prior comCode =  uppercomCode");
		conditions.append(" And prior ComCode != ComCode");
		conditions.append(" And validstatus='1')");
		conditions.append(")");
		conditions.append(" Order by usercode");
		PrpDuserDto user = new PrpDuserDto();
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			user.setQueryCondition("PrpDuser", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute(SESS_KEY, user);
		} else {
			user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpDuser");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = new StringBuffer(queryCondition.getConditions());
			}
		}
		Page page = this.prpDuserService.findByPage(conditions.toString(), pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, this.checkPower(user, page.getResult(), "claim.certainloss.insert"), "PrpDuser");
	}

	/**
	 * 得到币别
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void currencycode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");// Y代码 N中文
		StringBuffer conditions = new StringBuffer();
		conditions.append(getCondition(codeMethod, "CurrencyCode", "CurrencyCName", fieldValue, isQueryCode));
		conditions.append(" AND ValidStatus='1'");
		conditions.append(" Order By CurrencyCode");
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpDcurrency", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpDcurrency");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = new StringBuffer(queryCondition.getConditions());
			}
		}
		Page page = this.prpDcurrencyService.findByPage(conditions.toString(), pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpDcurrency");
	}

	/**
	 * 得到支付对象
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void payObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");// Y代码 N中文
		StringBuffer conditions = new StringBuffer();
		conditions.append(getCondition(codeMethod, "PayObjectCode", "PayObjectName", fieldValue, isQueryCode));
		conditions.append(" AND PayObjectType = '" + otherCondition + "'");
		conditions.append(" AND ValidStatus='1'");
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpLpayObject", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpLpayObject");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = new StringBuffer(queryCondition.getConditions());
			}
		}
		Page page = this.prpLpayObjectService.findByPage(conditions.toString(), pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpLpayObject");
	}

	/**
	 * 人伤定损险别
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void policyKindCodeForPerson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		String registno = "";
		String isQueryCode = paramUtils.getParameter("isQueryCode");
		String arrValue[] = StringUtils.split(otherCondition.trim(), "|");
		StringBuffer conditions = new StringBuffer();
		conditions.append(getCondition(codeMethod, "KindCode", "KindName", fieldValue, isQueryCode));
		conditions.append(" And ( 1=0");
		if(arrValue!=null && arrValue.length>=1){
			registno = arrValue[0];//第一位是报案号
		}
		List<String> policyNos = new ArrayList<String>();
 		if (DataUtils.emptyToNull(registno) != null) {
			String condition = " VALIDSTATUS!='0' AND REGISTNO = '" + registno + "'";
			List<Prplregistrpolicy> prpLregistRPolicyList = this.prpLregistrpolicyService.findPrplregistrpolicy(QueryRule.getInstance().addSql(condition));
			if (prpLregistRPolicyList != null && !prpLregistRPolicyList.isEmpty()) {
				for (Prplregistrpolicy prplregistrpolicy : prpLregistRPolicyList) {
					policyNos.add(prplregistrpolicy.getId().getPolicyNo());
					conditions.append(" Or PolicyNo = '" + prplregistrpolicy.getId().getPolicyNo() + "'");
				}
			}
		}
		conditions.append(" ) Order By KindCode");
		PrpLregist prpLregist = this.getPrpLregistService().findPrpLregist(registno);
		List<PrpCitemKind> list = new ArrayList<PrpCitemKind>();
		if(prpLregist!=null){
			Page page = this.prpCitemKindService.findByPage(conditions.toString(), 0, 0);
			String damageTime = StringConvert.toStandardTime(prpLregist.getDamageStartHour());;
			List<?> resultList = page.getResult();//查询出的保单承保险别
			// 临时存储 属于当前双击域选择范围的险别
			Set<String> tempKindSet = new HashSet<String>();
			tempKindSet.addAll(ConstantsCollection.KindCodeForPerson);
			for(String policyNo : policyNos){
				List<PrpCitemKind> retainList = this.retain(resultList,this.backWardPrpCitemKind(policyNo,new DateTime(prpLregist.getDamageStartDate()).toString(), damageTime.substring(0, 2),null,null));
				if (!retainList.isEmpty()) {
					for (PrpCitemKind tempPrpCitemKind : retainList) {
						// 筛选有效险别、在赔付范围内的险别
						if (tempKindSet.contains(tempPrpCitemKind.getKindCode())) {
							list.add(tempPrpCitemKind);
						}
					}
				}	
			}
		}
		// 承保的批单承保险别
		int size = list.size();
		request.setAttribute("page",new Page(1, size, size==0?20:size, list));
		this.setData(request, list, "PrpCitemKind");
	}

	/**
	 *  财产定损险别
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void policyKindCodeForProp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");// Y代码 N中文
		String[] arrValue = StringUtils.split(otherCondition.trim(), "|");
		StringBuffer conditions = new StringBuffer();
		conditions.append(getCondition(codeMethod, "KindCode", "KindName", fieldValue, isQueryCode));
		// 强制保单号的处理
		conditions.append(" And ( 1=0");
		for (int i = 0; i < arrValue.length; i++) {
			if (arrValue[i] != null || arrValue[i].length() > 0) {
				conditions.append(" Or PolicyNo = '" + arrValue[i] + "'");
			}
		}
//		新险别使用，不在用这些判断 claim_new_risk_code
//		conditions.append(" ) and (KindCode='BZ' or KindCode='B' or KindCode='D2' or KindCode='H' or KindCode ='NZ'");
		StringBuffer tempKindCode = new StringBuffer("");
		for(String temp:ConstantsCollection.KindCodeForProp){
			tempKindCode.append(" KindCode='"+temp+"' or");
		}
		if(tempKindCode.length()>0){
			conditions.append(" ) and ("+tempKindCode.substring(0,tempKindCode.lastIndexOf("or")));
		}
		conditions.append(" ) Order By KindCode");
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpDCompany", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpDCompany");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = new StringBuffer(queryCondition.getConditions());
			}
		}
		Page page = this.prpCitemKindService.findByPage(conditions.toString(), pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpCitemKind");
	}

	/**
	 * 得到受损标的信息(责任明细)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void policyItemKindCodeNoRisk(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		String[] arrValue = StringUtils.split(otherCondition.trim(), "|");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");// Y代码 N中文
		StringBuffer conditions = new StringBuffer();
		if("N".equals(isQueryCode)){
			conditions.append("("+getCondition(codeMethod, "ItemCode", "itemName", fieldValue, isQueryCode));
			conditions.append(" or "+getCondition(codeMethod, "ItemCode", "itemDetailName", fieldValue, isQueryCode)+") ");
		}else{
			conditions.append(getCondition(codeMethod, "ItemCode", "itemName", fieldValue, isQueryCode));
		}
		conditions.append(" AND PolicyNo='" + arrValue[0] + "'");
		if(arrValue.length > 1){
			conditions.append(" AND KindCode ='" + arrValue[1] + "'");
		}
		conditions.append(" Order By ItemCode");
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpCitemKind", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpCitemKind");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = new StringBuffer(queryCondition.getConditions());
			}
		}
		Page page = this.prpCitemKindService.findByPage(conditions.toString(), pageNo, rowsPerPage);
		request.setAttribute("page", page);
		// 去除重复的标的
		List<PrpCitemKind> list = new ArrayList<PrpCitemKind>();
		List<PrpCitemKind> tempList = new ArrayList<PrpCitemKind>();
		List<?> result = page.getResult();
		if (result != null && !result.isEmpty()) {
			Set<String> kindCodeSet = new HashSet<String>();
			PrpCitemKind prpCitemKindDto = null;
			String tempKey = "";
			for (Iterator<?> it = result.iterator(); it.hasNext();) {
				prpCitemKindDto = (PrpCitemKind) it.next();
				String strRiskType = codeService.translateRiskCodetoRiskType(prpCitemKindDto.getRiskCode());
				if(ConstantCodes.CLASSCODE_Z.equals(strRiskType)||ConstantCodes.CLASSCODE_G.equals(strRiskType)||ConstantCodes.CLASSCODE_Q.equals(strRiskType)) {//责任险标的选择。
					tempList = prpCitemKindService.generateVirtualKind(prpCitemKindDto);
					if(!CommonUtils.isEmpty(tempList)) {
						list.addAll(tempList);
						continue;
					}
				}
				//mantis：CLM0128，處理人員：DP0713，需求單編號：新核心-藝術品AR立案錯誤問題
				tempKey = prpCitemKindDto.getKindCode() + "," + DataUtils.dbNullToEmpty(prpCitemKindDto.getItemCode())+ "," + DataUtils.dbNullToEmpty(prpCitemKindDto.getItemDetailName());
				if (!kindCodeSet.contains(tempKey)) {
					list.add(prpCitemKindDto);
					//火险不去除重复标的
					if(!ConstantCodes.CLASSCODE_Q.equals(strRiskType)){
						kindCodeSet.add(tempKey);
					}
				}
			}
		}
		this.setData(request, list, "PrpCitemKind","policyItemKindCodeNoRiskForProp");
	}

	/**
	 * 得到事故者代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void prpCinsured(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");// Y代码 N中文
		StringBuffer conditions = new StringBuffer();
		conditions.append(getCondition(codeMethod, "InsuredCode", "InsuredName", fieldValue, isQueryCode));
		// 由於事故者是被保险人，所以需要将投保人等信息都不能显示出来，只显示被保险人 所以增加过滤条件 1--被保险人 2--投保人
		String codeType = request.getParameter("codeType");
		if(!"PrpCinsuredAcci".equals(codeType)){
			conditions.append(" AND insuredflag='1'");
		}
		conditions.append(" AND PolicyNo='" + otherCondition + "'");
		conditions.append(" Order By InsuredCode");
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("prpCinsured", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("prpCinsured");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = new StringBuffer(queryCondition.getConditions());
			}
		}
		Page page = this.prpCinsuredService.findByPage(conditions.toString(), pageNo, rowsPerPage);
		request.setAttribute("page", page);
		// 去除重复的事故者代码
		List<?> list = page.getResult();
		Map<String, PrpCinsured> itemKindMap = new HashMap<String, PrpCinsured>();
		if (list != null && !list.isEmpty()) {
			PrpCinsured prpcinsred = null;
			for (Iterator<?> it = list.iterator(); it.hasNext();) {
				prpcinsred = (PrpCinsured) it.next();
				if (DataUtils.emptyToNull(prpcinsred.getInsuredCode()) != null) {
					itemKindMap.put(prpcinsred.getInsuredCode(), prpcinsred);
				}
			}
		}
		if("PrpCinsuredAcci".equals(codeType)){
			this.setData(request, new ArrayList<PrpCinsured>(itemKindMap.values()), "PrpCinsured", "prpCinsuredAcci");
		}else{
			this.setData(request, new ArrayList<PrpCinsured>(itemKindMap.values()), "PrpCinsured", "prpCinsured");
		}
	}

	/**
	 * 根据险别得到得到受损标的
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void policyItemCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");// Y代码 N中文
		String[] arrValue = StringUtils.split(otherCondition.trim(), "|");
		StringBuffer conditions = new StringBuffer();
		if("N".equals(isQueryCode)){
			conditions.append("("+getCondition(codeMethod, "ItemCode", "itemName", fieldValue, isQueryCode));
			conditions.append(" or "+getCondition(codeMethod, "ItemCode", "itemDetailName", fieldValue, isQueryCode)+") ");
		}else{
			conditions.append(getCondition(codeMethod, "ItemCode", "itemName", fieldValue, isQueryCode));
		}
		conditions.append(" And policyNo='" + arrValue[0] + "'");
		if(DataUtils.emptyToNull(arrValue[1])!=null){
			conditions.append(" And KindCode='" + arrValue[1] + "'");
		}
//		if ("policyItemCode".equals(request.getParameter("codeType")) && DataUtils.emptyToNull(arrValue[2])!=null) {
//			conditions.append(" And familyNo='" + arrValue[2] + "'");
//		}
		conditions.append(" Order By ItemCode");
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpCitemKind", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpCitemKind");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = new StringBuffer(queryCondition.getConditions());
			}
		}
		Page page = this.prpCitemKindService.findByPage(conditions.toString(), pageNo, rowsPerPage);
		List<?> resultList = page.getResult();//查询出的保单承保险别
		List<?> retainList = null;
		if(arrValue.length>5&&DataUtils.emptyToNull(arrValue[4])!=null){
			//根据批单好做回滚
			retainList = this.retainItemCode(resultList, this.backWardPrpCitemKind(arrValue[0],arrValue[2], arrValue[3],null,arrValue[4]));
		}else if(arrValue.length>4&&DataUtils.emptyToNull(arrValue[2])!=null&&DataUtils.emptyToNull(arrValue[3])!=null){
			//根据出险时间做回滚
			retainList = this.retainItemCode(resultList, this.backWardPrpCitemKind(arrValue[0], arrValue[2], arrValue[3],null,null));
		}else{
			retainList = resultList;
		}
		request.setAttribute("page", page);
		List<String> codeValues = new ArrayList<String>();
		List<String> codeLabels = new ArrayList<String>();
		// 得到分页循环
		List<?> list = retainList;
		if (!CommonUtils.isEmpty(list)) {
			PrpCitemKind prpCitemKind = null;
			List<PrpCitemKind> tempList = new ArrayList<PrpCitemKind>();
			boolean flag = "policyItemCode".equals(request.getParameter("codeType"));
			for (Iterator<?> it = list.iterator(); it.hasNext();) {
				prpCitemKind = (PrpCitemKind) it.next();
				String strRiskType = codeService.translateRiskCodetoRiskType(prpCitemKind.getRiskCode());
				if(ConstantCodes.CLASSCODE_Z.equals(strRiskType)||ConstantCodes.CLASSCODE_G.equals(strRiskType)||ConstantCodes.CLASSCODE_Q.equals(strRiskType)) {//责任险标的选择。
					tempList = prpCitemKindService.generateVirtualKind(prpCitemKind);
					if (!CommonUtils.isEmpty(tempList)) {
						for (int i = 0; i < tempList.size(); i++) {
							prpCitemKind = tempList.get(i);
//							String itemName = prpCitemKind.getItemName() == null ? prpCitemKind.getItemDetailName() : prpCitemKind.getItemName();
//							itemName = DataUtils.dbNullToEmpty(itemName);
							String itemCode = codeService.getItemCode(prpCitemKind);
							String itemName = codeService.getItemName(prpCitemKind);
							
							this.setData(codeValues, IConstants.FIELD_SEPARATOR, itemCode, itemName, String.valueOf(prpCitemKind.getId().getItemKindNo()), decimalFormat.format(prpCitemKind.getAmount()),
									decimalFormat.format(prpCitemKind.getValue()), prpCitemKind.getKindCode(), prpCitemKind.getKindName());
							this.setData(codeLabels, "--", prpCitemKind.getKindCode(), itemCode, itemName, (flag ? prpCitemKind.getFamilyName() : String.valueOf(prpCitemKind.getId().getItemKindNo())));
						}
						continue;
					}
				}
				String itemCode = codeService.getItemCode(prpCitemKind);
				String itemName = codeService.getItemName(prpCitemKind);
//				String itemName = prpCitemKind.getItemName() == null ? prpCitemKind.getItemDetailName() : prpCitemKind.getItemName();
//				itemName = DataUtils.dbNullToEmpty(itemName);
				this.setData(codeValues, IConstants.FIELD_SEPARATOR, itemCode, itemName, String.valueOf(prpCitemKind.getId().getItemKindNo()), decimalFormat.format(prpCitemKind.getAmount()),
						decimalFormat.format(prpCitemKind.getValue()), prpCitemKind.getKindCode(), prpCitemKind.getKindName());
				this.setData(codeLabels, "--", prpCitemKind.getKindCode(), itemCode, itemName, (flag ? prpCitemKind.getFamilyName() : String.valueOf(prpCitemKind.getId().getItemKindNo())));
			}
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 得到责任明细（根据险种代码）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void prpDliab(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		String isQueryCode = paramUtils.getParameter("isQueryCode");
		StringBuffer conditions = new StringBuffer();
		conditions.append(getCondition(codeMethod, "ItemCode", "ItemCName", fieldValue, isQueryCode));
		conditions.append(" AND RiskCode = '" + otherCondition + "'");
		conditions.append(" Order By ItemCode");
		int pageNo = 1;
		int rowsPerPage = 20;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpDItem", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute("CodeInputCondition", user);
		} else {
			PrpDuserDto user = (PrpDuserDto) request.getSession().getAttribute("CodeInputCondition");
			com.sinosoft.platform.dto.domain.PrpDuserDto.QueryCondition queryCondition = user.getQueryCondition("PrpDItem");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = new StringBuffer(queryCondition.getConditions());
			}
		}
		PageRecord pageRecord = (new BLCodeInptFacade()).findLiabCodeFromPrpDItem(conditions.toString(), pageNo, rowsPerPage);
		Page page = new Page(Integer.valueOf((pageNo - 1) * rowsPerPage).longValue(), Integer.valueOf(pageRecord.getCount()).longValue(), pageRecord.getRowsPerPage(), (List<?>) pageRecord.getResult());
		request.setAttribute("page", page);
		List<?> list = page.getResult();
		Map<String, PrpDitemDto> resultMap = new HashMap<String, PrpDitemDto>();
		if (list != null && !list.isEmpty()) {
			PrpDitemDto prpDitemDto = null;
			for (Iterator<?> it = list.iterator(); it.hasNext();) {
				prpDitemDto = (PrpDitemDto) it.next();
				if (DataUtils.emptyToNull(prpDitemDto.getItemCode()) != null) {
					resultMap.put(prpDitemDto.getItemCode(), prpDitemDto);
				}
			}
		}
		this.setData(request, new ArrayList<PrpDitemDto>(resultMap.values()), "PrpDitem");
	}

	/**
	 * 得到车辆种类代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void carKind(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition"); // 此处的otherCondition为riskCode
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");// Y代码 N中文
		StringBuffer conditions = new StringBuffer();
		conditions.append(getCondition(codeMethod, "CarKind", "CarKinkCName", fieldValue, isQueryCode));
		conditions.append(" AND RiskCode like '" + otherCondition + "'");
		conditions.append(" Order By LiabCode");

		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpDCode", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpDCode");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = new StringBuffer(queryCondition.getConditions());
			}
		}

		PageRecord pageRecord = new BLCodeInptFacade().findByConditionsCarKind(conditions.toString(), pageNo, rowsPerPage);
		Page page = new Page(Integer.valueOf((pageNo - 1) * rowsPerPage).longValue(), Integer.valueOf(pageRecord.getCount()).longValue(), pageRecord.getRowsPerPage(), (List<?>) pageRecord.getResult());
		request.setAttribute("page", page);
		// 去除重复的标的
		List<?> list = page.getResult();
		Map<String, PrpDliabDto> resultMap = new HashMap<String, PrpDliabDto>();
		if (list != null && !list.isEmpty()) {
			PrpDliabDto temp = null;
			for (Iterator<?> it = list.iterator(); it.hasNext();) {
				temp = (PrpDliabDto) it.next();
				if (DataUtils.emptyToNull(temp.getLiabCode()) != null) {
					resultMap.put(temp.getLiabCode(), temp);
				}
			}
		}
		this.setData(request, new ArrayList<PrpDliabDto>(resultMap.values()), "PrpDliab");
	}

	/**
	 * 获取人伤费用类型
	 * @param request
	 * @param response
	 * @param codeType
	 * @throws Exception
	 */
	private void PersonFeeTypeFlag(HttpServletRequest request, HttpServletResponse response, String codeType) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String riskCode = paramUtils.getParameter("otherCondition");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");
		String conditions = " ";
		conditions = getCondition(codeMethod, "codeCode", "codeCName", fieldValue, isQueryCode);
		conditions += " AND codetype ='PersonFeeType' ";
		conditions += " and codeCode in (select codeCode from prpdcoderisk where codetype ='PersonFeeType' and (riskcode='" + riskCode + "' or riskcode='0000')) ";
		conditions += " AND validstatus='1' order by codecode";
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpDcode", pageNo, rowsPerPage, conditions);
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpDcode");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = queryCondition.getConditions();
			}
		}
		Page page = this.prpDcodeService.findByConditions(conditions, pageNo, rowsPerPage);
		request.setAttribute("page", page);
		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3
		this.setData(request, page.getResult(), "PrpDcode", codeType, riskCode);
	}

	/**
	 * 获取系统内机构
	 * @param request
	 * @param response
	 * @param codeType
	 * @throws Exception
	 */
	private void Prpdcompany(HttpServletRequest request, HttpServletResponse response, String codeType) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");
		String conditions = " ";
		conditions = getCondition(codeMethod, "comCode", "comCName", fieldValue, isQueryCode);
		String comCode = "";
		if ("prpdcompany2".equals(codeType)|| "prpdcompany3".equals(codeType)) {
			comCode = otherCondition;
		}
		if ("prpdcompany3".equals(codeType)) {
			UserDto userDto = (UserDto) request.getSession().getAttribute("user");
			UIPowerInterface uiPowerInterface = new UIPowerInterface();
			conditions += uiPowerInterface.addCustomerPower(userDto, "PrpDcompany", "", "ComCode");
		}
		if (!"".equals(comCode)) {
			conditions += " AND comCode in (Select ComCode from prpdCompany Start With ComCode  = '"+comCode+"' Connect By Prior comCode = uppercomCode  and prior ComCode != ComCode  and validstatus='1')";
		}
		conditions += " AND validstatus='1' order by comCode";
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpDcode", pageNo, rowsPerPage, conditions);
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpDcode");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = queryCondition.getConditions();
			}
		}
		Page page = this.prpDcompanyService.findByPage(conditions, pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpDcompany");
	}

	/**
	 * 获取系统外机构
	 * @param request
	 * @param response
	 * @param codeType
	 * @throws Exception
	 */
	private void PrpdCustomerUnit(HttpServletRequest request, HttpServletResponse response, String codeType) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");
		StringBuffer conditions = new StringBuffer();
		conditions.append(getCondition(codeMethod, "comCode", "comCName", fieldValue, isQueryCode));
		conditions.append(" AND COMTYPE in ('A','L','S')");
		conditions.append(" AND ValidStatus='1' order by comCName desc");
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpLpayObject", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpLpayObject");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = new StringBuffer(queryCondition.getConditions());
			}
		}
		Page page = this.prpLexternalAgencyService.findByPage(conditions.toString(), pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpLexternalAgency");
	}

	/**
	 * 巨灾代码查询
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void catastropheCode2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String otherCondition = paramUtils.getParameter("otherCondition");
		String conditions = " 1=1 ";
		conditions += " AND NEWCODECODE = '" + otherCondition + "'";
		conditions += " AND CODETYPE = 'CatastropheCode2'";
		conditions += " Order by codecode";
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpDcode", pageNo, rowsPerPage, conditions);
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpDcode");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = queryCondition.getConditions();
			}
		}
		Page page = this.prpDcodeService.findByConditions(conditions, pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpDcode");
	}

	/**
	 * 得到受损标的信息(责任明细)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void policyItemKindCodeForAcci(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String isQueryCode = paramUtils.getParameter("isQueryCode");// Y代码 N中文
		String otherCondition = paramUtils.getParameter("otherCondition");
		String[] arrValue = StringUtils.split(otherCondition.trim(), "|");
		String policyNo = arrValue[0];
		String damageDate = arrValue[1];
		String damageHour = arrValue[2];
		String familyNo = arrValue[3];
		StringBuffer conditions = new StringBuffer();
		conditions.append(getCondition(codeMethod, "KindCode", "KindName", fieldValue, isQueryCode));
		// 意健险人伤险别查询，根据policyNo和FamilyNo
		conditions.append(" AND PolicyNo= '" + policyNo+ "'");
		if (DataUtils.emptyToNull(familyNo)!=null && !"0".equals(familyNo)) {
			conditions.append(" AND FamilyNo= " + familyNo);
		}
		conditions.append(" Order By familyno");
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpCitemKind", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpCitemKind");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = new StringBuffer(queryCondition.getConditions());
			}
		}
		//查询出的保单承保险别
		List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, Integer.parseInt(familyNo));
		// 先做过滤，提高查询时间。保证分页不会出问题
		List<PrpCitemKind> list = new ArrayList<PrpCitemKind>();
		int size = prpCitemKindList.size();
		if(size > 0){
			int start = (pageNo - 1) * rowsPerPage;
			int end = start + rowsPerPage;
			String kindCode = null;
			String kindName = null;
			for (; start < end && start < size; start++) {
				PrpCitemKind prpCitemKind = prpCitemKindList.get(start);
				kindCode = prpCitemKind.getKindCode();
				kindName = prpCitemKind.getKindName();
				if(!CommonUtils.isEmpty(fieldValue)){
					if("Y".equals(isQueryCode) && !kindCode.startsWith(fieldValue)){
						continue;
					} else if("N".equals(isQueryCode) && !kindName.startsWith(fieldValue)){
						continue;
					}
				}
				PrpLclause prpLclause = prpLclauseService.findPrpLclause(kindCode);
				if (prpLclause != null && !CommonUtils.isEmpty(prpLclause.getRange2())) {
					prpCitemKind.setContractingScope(prpLclause.getRange2());// 承保范围设定
				}
				list.add(prpCitemKind);
			}
		}
		Page page = new Page((pageNo-1)*rowsPerPage + 1, size, rowsPerPage, list);
		request.setAttribute("page",page);
		this.setData(request, page.getResult(), "PrpCitemKind", "policyItemKindCodeForAcci");
	}

	/**
	 * 得到被保险人 (家财险等险别，被保险人列表记录在prpcinserd中的，取到具体的被保险人名称)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void getCinsured(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		String[] arrValue = StringUtils.split(otherCondition.trim(), "|");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");// Y代码 N中文
		String policyNo =  arrValue[0];
		String damageDate = null;
		String damageHour = null;
		if(arrValue.length > 1){
			damageDate = arrValue[1];
			damageHour = arrValue[2];
		} else {
			damageDate = DateTime.current().toString();
			damageHour = "12";
		}
		StringBuffer conditions = new StringBuffer();
		conditions.append(getCondition(codeMethod, "InsuredCode", "InsuredName", fieldValue, isQueryCode));
		// 由於事故者是被保险人，所以需要将投保人等信息都不能显示出来，只显示被保险人 所以增加过滤条件 1--被保险人 2--投保人
		conditions.append(" AND insuredflag='1'");
		conditions.append(" Order By serialNo ");
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("getCinsured", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("getCinsured");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = new StringBuffer(queryCondition.getConditions());
			}
		}
		String endorseNo = this.endorseViewHelper.getEndorseNo(policyNo, damageDate, damageHour);
		Page page = this.endorseViewHelper.findPrpCinsuredFromCopy(endorseNo, conditions.toString() , pageNo, rowsPerPage);
		request.setAttribute("page", page);
		@SuppressWarnings("unchecked")
		List<PrpCinsured> prpCinsuredList = page.getResult();
		int[] serialNos = this.getEndorseViewHelper().getPrpCinsuredSerialNos(prpCinsuredList);
		List<PrpCinsuredNature> prpCinsuredNatureList = this.getEndorseViewHelper().findPrpCinsuredNatureFromCopy(policyNo, damageDate, damageHour, serialNos);
		List<String> codeValues = new ArrayList<String>();
		List<String> codeLabels = new ArrayList<String>();
		for(PrpCinsured prpCinsured : prpCinsuredList){
			PrpCinsuredNature temp = new PrpCinsuredNature();
			for(PrpCinsuredNature prpCinsuredNature : prpCinsuredNatureList){
				if(prpCinsured.getId().getSerialNo().intValue() == prpCinsuredNature.getId().getSerialNo().intValue()){
					temp = prpCinsuredNature;
					break;
				}
			}
			String insuredCode = prpCinsured.getInsuredCode();
			insuredCode = CommonUtils.isEmpty(insuredCode) ? prpCinsured.getIdentifyNumber():insuredCode;
			this.setData(codeValues, IConstants.FIELD_SEPARATOR, insuredCode , prpCinsured.getInsuredName(), prpCinsured.getIdentifyNumber() , String.valueOf(temp.getSex()) , String.valueOf(temp.getAge()));
			this.setData(codeLabels, "--", insuredCode, prpCinsured.getInsuredName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 获得支付对象
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void getPayObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");// Y代码 N中文
		String comType = "";
		String[] arrValue = StringUtils.split(otherCondition.trim(), "|");
		Page page = null;
		String payObjectType = "";
		if ("B".equals(arrValue[1]) ) {// 其他费用
			comType = "'A','L','S'";
		} else if("A".equals(arrValue[1])){
			payObjectType = arrValue[1];
		}
		if (!"".equals(comType)) {
			StringBuffer conditions = new StringBuffer();
			conditions.append(getCondition(codeMethod, "comCode", "comCName", fieldValue, isQueryCode));
			conditions.append(" AND COMTYPE in (" + comType + ")");
			conditions.append(" AND ValidStatus='1' order by comCName desc");
			// 双击分页功能的实现
			int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
			if ("query".equals(paramUtils.getParameter("actionType"))) {
				HttpSession session = request.getSession();
				PrpDuserDto user = new PrpDuserDto();
				user.setQueryCondition("PrpLpayObject", pageNo, rowsPerPage, conditions.toString());
				session.setAttribute(SESS_KEY, user);
			} else {
				PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
				QueryCondition queryCondition = user.getQueryCondition("PrpLpayObject");
				if (queryCondition != null) {
					pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
					rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
					conditions = new StringBuffer(queryCondition.getConditions());
				}
			}
			page = this.prpLexternalAgencyService.findByPage(conditions.toString(), pageNo, rowsPerPage);
		} else {
			StringBuffer conditions = new StringBuffer();
			conditions.append(getCondition(codeMethod, "PayObjectCode", "PayObjectName", fieldValue, isQueryCode));
			conditions.append(" AND PayObjectType = '" + payObjectType + "'");
			conditions.append(" AND ValidStatus='1'");
			// 双击分页功能的实现
			int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
			if ("query".equals(paramUtils.getParameter("actionType"))) {
				HttpSession session = request.getSession();
				PrpDuserDto user = new PrpDuserDto();
				user.setQueryCondition("PrpLpayObject", pageNo, rowsPerPage, conditions.toString());
				session.setAttribute(SESS_KEY, user);
			} else {
				PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
				QueryCondition queryCondition = user.getQueryCondition("PrpLpayObject");
				if (queryCondition != null) {
					pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
					rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
					conditions = new StringBuffer(queryCondition.getConditions());
				}
			}
			page = this.prpLpayObjectService.findByPage(conditions.toString(), pageNo, rowsPerPage);
		}
		request.setAttribute("page", page);
		// 得到分页循环
		if (!"".equals(comType)) {
			this.setData(request, page.getResult(), "PrpLexternalAgency");
		} else {
			this.setData(request, page.getResult(), "PrpLpayObject");
		}
	}

	/**
	 * 查询二级机构
	 * @throws Exception
	 * @return void
	 */
	public void queryLevel2Com(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ParamUtils paramUtils = new ParamUtils(request);
		String conditions = "";
		conditions = " VALIDSTATUS='1' AND COMLEVEL = '2' order By comCode";
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpDCompany", pageNo, rowsPerPage, conditions);
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpDCompany");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = queryCondition.getConditions();
			}
		}
		Page page = this.prpDcompanyService.findByPage(conditions, pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpDcompany");
	}

	/**
	 * 查询一个省的所有机构
	 * @throws Exception
	 * @return void
	 * @author 中科软
	 */
	public void queryProvince(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ParamUtils paramUtils = new ParamUtils(request);
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		String conditions = " 1=1 ";
		String otherConditionTemp1 = otherCondition.substring(0, 2);
		String otherConditionTemp2 = otherCondition.substring(0, 2);
		if (!"".equals(otherCondition)) {
			if ("3302".equals(otherConditionTemp2) || "3502".equals(otherConditionTemp2) || "4403".equals(otherConditionTemp2) || "2102".equals(otherConditionTemp2) || "3702".equals(otherConditionTemp2)) {// 计划单列市特殊处理
				conditions += " AND COMCODE LIKE '" + otherCondition.substring(0, 4) + "%'";
			} else if ("33".equals(otherConditionTemp1) || "35".equals(otherConditionTemp1) || "21".equals(otherConditionTemp1) || "37".equals(otherConditionTemp1)) {
				conditions += " AND COMCODE LIKE '" + otherCondition.substring(0, 2) + "%' and comcode not like '" + otherConditionTemp1 + "02%'";
			} else if ("44".equals(otherConditionTemp1)) {
				conditions += " AND COMCODE LIKE '" + otherCondition.substring(0, 2) + "%' and comcode not like '4403%'";
			} else {
				conditions += " AND COMCODE LIKE '" + otherCondition.substring(0, 2) + "%'";
			}
		}
		if (!"".equals(fieldValue)) {
			conditions += " AND COMCODE LIKE '" + fieldValue + "%'";
		}
		conditions += " AND VALIDSTATUS='1' ORDER BY COMCODE";
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpDCompany", pageNo, rowsPerPage, conditions);
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpDCompany");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = queryCondition.getConditions();
			}
		}
		Page page = this.prpDcompanyService.findByPage(conditions, pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpDcompany", "queryProvince");
	}

	/**
	 * 查询能够处理某一机构下拥有某项权限的操作员
	 * @throws Exception
	 * @return void
	 */
	public void queryUserHaveRights(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String otherCondition = paramUtils.getParameter("otherCondition");
		// otherCondition例子：11000000|查勘
		String[] conditionParams = otherCondition.split(",");
		String fieldValue = paramUtils.getParameter("fieldValue");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");// Y代码 N中文
		String codeMethod = paramUtils.getParameter("codeMethod");
		String comCode = conditionParams[0];
		String gradeName = conditionParams[1];
		String conditions = "";
		StringBuffer buffer = new StringBuffer(200);
		buffer.append("SELECT DISTINCT A.USERCODE,C.USERNAME");
		buffer.append(" FROM UTIUSERGRADE A, UTIGRADETASK B, PRPDUSER C");
		buffer.append(" WHERE A.COMCODE IN(");
		buffer.append(" SELECT COMCODE FROM PRPDCOMPANY");
		buffer.append(" START WITH COMCODE = '");
		buffer.append(comCode);
		buffer.append("' CONNECT BY PRIOR UPPERCOMCODE = COMCODE");
		buffer.append(" AND PRIOR COMCODE != UPPERCOMCODE");
		buffer.append(" AND SUBSTR(COMCODE, 1, 2) <> '00')");
		buffer.append(" AND A.GRADECODE = B.GRADECODE");
		buffer.append(" AND A.USERCODE = C.USERCODE");
		buffer.append(" AND "+getCondition(codeMethod, "C.userCode", "C.userName", fieldValue, isQueryCode));
		buffer.append(" AND B.TASKCODE LIKE '");
		buffer.append(this.translateGrade(gradeName));
		buffer.append("%' ORDER BY A.USERCODE");
		conditions = buffer.toString();
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("UserHaveRights", pageNo, rowsPerPage, conditions.toString());
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("UserHaveRights");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = queryCondition.getConditions();
			}
		}
		Page page = this.prpDuserService.queryUserHaveRights(conditions, pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpDuser");
	}
	/**
	 * 等級轉換
	 * @param gradeName
	 * @return
	 */
	private String translateGrade(String gradeName) {
		String gradTask = "";
		if ("check".equals(gradeName)) {
			gradTask = "claim.check.insert";
		} else if ("claim".equals(gradeName)) {
			gradTask = "claim.claim.insert";
		} else if ("certi".equals(gradeName)) {
			gradTask = "claim.certify.insert";
		} else if ("compe".equals(gradeName) || "compp".equals(gradeName)) {
			gradTask = "claim.compensate.insert";
		} else if ("veric".equals(gradeName)) {
			gradTask = "claim.undwrt.deal";
		} else if ("speif".equals(gradeName)) {
			gradTask = "claim.specialcase.insert";
		}else if ("certa".equals(gradeName)) {
			gradTask = "claim.certaincarloss.insert";
		}else if ("wound".equals(gradeName)) {
			gradTask = "claim.certainpersonloss.insert";
		}else if ("propc".equals(gradeName)) {
			gradTask = "claim.certainloss.insert";
		}else if ("endca".equals(gradeName)) {
			gradTask = "claim.endcase.insert";
		}else if ("sched".equals(gradeName)) {
			gradTask = "claim.schedule.insert";
		}else if ("verif".equals(gradeName)) {
			gradTask = "claim.verifycarloss.insert";
		}else if ("veriw".equals(gradeName)) {
			gradTask = "claim.verifypersonloss.insert";
		}else if ("propv".equals(gradeName)) {
			gradTask = "claim.verifyloss.insert";
		}
		
		return gradTask;
	}

	/**
	 * 获得支付对象
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void getReplevyPayObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");// Y代码 N中文
		String comType = "";
		String[] arrValue = StringUtils.split(otherCondition.trim(), "|");
		Page page = null;
		String payObjectType = "";
		if ("B".equals(arrValue[1]) ) {// 其他费用
			comType = "'A','L','S'";
		} else if("A".equals(arrValue[1])){
			payObjectType = arrValue[1];
		}
		if (!"".equals(comType)) {
			StringBuffer conditions = new StringBuffer();
			conditions.append(getCondition(codeMethod, "comCode", "comCName", fieldValue, isQueryCode));
			conditions.append(" AND COMTYPE in (" + comType + ")");
			conditions.append(" AND ValidStatus='1'  order by comCName desc");
			// 双击分页功能的实现
			int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
			if ("query".equals(paramUtils.getParameter("actionType"))) {
				HttpSession session = request.getSession();
				PrpDuserDto user = new PrpDuserDto();
				user.setQueryCondition("PrpLpayObject", pageNo, rowsPerPage, conditions.toString());
				session.setAttribute(SESS_KEY, user);
			} else {
				PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
				QueryCondition queryCondition = user.getQueryCondition("PrpLpayObject");
				if (queryCondition != null) {
					pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
					rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
					conditions = new StringBuffer(queryCondition.getConditions());
				}
			}
			page = this.prpLexternalAgencyService.findByPage(conditions.toString(), pageNo, rowsPerPage);
		} else {
			StringBuffer conditions = new StringBuffer();
			conditions.append(getCondition(codeMethod, "PayObjectCode", "PayObjectName", fieldValue, isQueryCode));
			conditions.append(" AND PayObjectType = '" + payObjectType + "'");
			conditions.append(" AND ValidStatus='1'");
			// 双击分页功能的实现
			int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
			if ("query".equals(paramUtils.getParameter("actionType"))) {
				HttpSession session = request.getSession();
				PrpDuserDto user = new PrpDuserDto();
				user.setQueryCondition("PrpLpayObject", pageNo, rowsPerPage, conditions.toString());
				session.setAttribute(SESS_KEY, user);
			} else {
				PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
				QueryCondition queryCondition = user.getQueryCondition("PrpLpayObject");
				if (queryCondition != null) {
					pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
					rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
					conditions = new StringBuffer(queryCondition.getConditions());
				}
			}
			page = this.prpLpayObjectService.findByPage(conditions.toString(), pageNo, rowsPerPage);
		}
		request.setAttribute("page", page);
		if (!"".equals(comType)) {
			this.setData(request, page.getResult(), "PrpLexternalAgency");
		} else {
			this.setData(request, page.getResult(), "PrpLpayObject");
		}
	}

	/**
	 * 查询国内所有省份
	 * @throws Exception
	 * @return void
	 * @author 中科软
	 * @date 2010-06-22
	 */
	public void utiAdminProvice(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ParamUtils paramUtils = new ParamUtils(request);
		String conditions = "";
		conditions = " provincename <> '境外' ORDER BY provincecode";
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("UtiAdminProvince", pageNo, rowsPerPage, conditions);
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("UtiAdminProvince");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = queryCondition.getConditions();
			}
		}
		PageRecord pageRecord = new BLUtiAdminProvinceFacade().findByConditions(conditions, pageNo, rowsPerPage);
		Page page = new Page(Integer.valueOf((pageNo - 1) * rowsPerPage).longValue(), Integer.valueOf(pageRecord.getCount()).longValue(), pageRecord.getRowsPerPage(), (List<?>) pageRecord.getResult());
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "UtiAdminProvince");
	}

	/**
	 * 查询省份对应的所有城市
	 * @throws Exception
	 * @return void
	 * @author 中科软
	 * @date 2010-06-22
	 */
	public void utiAdminCity(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ParamUtils paramUtils = new ParamUtils(request);
		String otherCondition = paramUtils.getParameter("otherCondition");
		String conditions = " provincecode = '" + otherCondition + "'";
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("UtiAdminCity", pageNo, rowsPerPage, conditions);
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("UtiAdminCity");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = queryCondition.getConditions();
			}
		}
		PageRecord pageRecord = new BLUtiAdminCityFacade().findByConditions(conditions, pageNo, rowsPerPage);
		Page page = new Page(Integer.valueOf((pageNo - 1) * rowsPerPage).longValue(), Integer.valueOf(pageRecord.getCount()).longValue(), pageRecord.getRowsPerPage(), (List<?>) pageRecord.getResult());
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "UtiAdminCity");
	}

	/**
	 * 按三级获取行业
	 * @param request
	 * @param response
	 * @param codeType
	 * @throws Exception
	 */
	private void queryBusinessSource(HttpServletRequest request, HttpServletResponse response, String codeType) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");
		String riskCode = "";
		String strRiskType = "";
		
		String[] arrValue = StringUtils.split(otherCondition.trim(), "|");
		if(arrValue.length > 1){
			riskCode = arrValue[0];
			otherCondition = arrValue[1];
		} 
		strRiskType = codeService.translateRiskCodetoRiskType(riskCode);
		if(CommonUtils.isEmpty(strRiskType)){
			strRiskType = "D";
		}
		String conditions = " ";
		conditions = getCondition(codeMethod, "codeCode", "codeCName", fieldValue, isQueryCode);
		conditions += " AND codetype = 'BusinessSource' AND codeEname like '%,"+strRiskType+",%' ";
		if ("BusinessSource1".equals(codeType)) {
			conditions += "and flag = '1' ";
		} else if ("BusinessSource2".equals(codeType)) {
			int index = otherCondition.indexOf("=");
			String codecodeStr = otherCondition.substring(index + 1);
			conditions += "and flag = '2'  and codecode like '" + codecodeStr + "%'";
		} else {
			int index = otherCondition.indexOf("=");
			String codecodeStr = otherCondition.substring(index + 1);
			if("Y".equals(strRiskType)||"E".equals(strRiskType)){
				conditions += "and flag = '3'  and upperCode = '" + codecodeStr + "'";
			}else{
				conditions += "and flag = '3'  and codecode like '" + codecodeStr + "%'";
			}
		}
		conditions += " AND validstatus='1' order by codecode";
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpDcode", pageNo, rowsPerPage, conditions);
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpDcode");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = queryCondition.getConditions();
			}
		}
		Page page = this.prpDcodeService.findByConditions(conditions, pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpDcode");
	}

	/**
	 * 获取代理人
	 * @param request
	 * @param response
	 * @param codeType
	 * @throws Exception
	 */
	private void Prpdagent(HttpServletRequest request, HttpServletResponse response, String codeType) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");
		String conditions = " ";
		conditions = getCondition(codeMethod, "agentCode", "agentName", fieldValue, isQueryCode);
		conditions += " AND validstatus='1' order by agentCode";
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("PrpDcode", pageNo, rowsPerPage, conditions);
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("PrpDcode");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = queryCondition.getConditions();
			}
		}
		Page page = this.prpDagentService.findPrpDagent(conditions, pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpDagent");
	}
	
	/**
	 * 查詢機構
	 * @param request
	 * @param response
	 * @param codeType
	 * @throws Exception
	 */
	private void getExternalAgency(HttpServletRequest request, HttpServletResponse response, String codeType) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");
		String conditions = " ";
		conditions = getCondition(codeMethod, "comCode", "comCName", fieldValue, isQueryCode);
		conditions += " AND validstatus='1' order by comCName desc";

		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("getExternalAgency", pageNo, rowsPerPage, conditions);
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("getExternalAgency");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = queryCondition.getConditions();
			}
		}
		Page page = this.prpLexternalAgencyService.findByPage(conditions, pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpLexternalAgency");
	}
	/**
	 * 查詢 PrpDuser
	 * @param request
	 * @param response
	 * @param codeType
	 * @throws Exception
	 */
	private void getInsuranceSurveyor(HttpServletRequest request, HttpServletResponse response, String codeType) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");
		String conditions = "";
		if (otherCondition != null && otherCondition.length() > 0) {
			conditions = otherCondition + " AND ";
		}
		conditions = conditions + getCondition(codeMethod, "a.comCode", "a.comCName", fieldValue, isQueryCode);
		conditions += " AND a.validstatus='1' order by a.comcode";
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("getInsuranceSurveyor", pageNo, rowsPerPage, conditions);
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("getInsuranceSurveyor");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = queryCondition.getConditions();
			}
		}
		Page page = this.prpLInsuranceSurveyorService.findByPage(conditions, pageNo, rowsPerPage);
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpLInsuranceSurveyor");
	}
	/**
	 * 查詢機構
	 * @param request
	 * @param response
	 * @param codeType
	 * @throws Exception
	 */
	private void portCode(HttpServletRequest request, HttpServletResponse response, String codeType) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");
		String conditions = "";
		String[] otherConditions = otherCondition.split(",");
		String countryFlag = otherConditions[0];
		String language = otherConditions[1];
		if ("0".equals(countryFlag)) {// 国内
			if ("E".equals(language)) {
				conditions = conditions + getCondition(codeMethod, "portCode", "portEName", fieldValue, isQueryCode);
				conditions += " AND validstatus='1' AND countryCode in ('CN','HKG','TW') order by portCode";
			} else {
				conditions = conditions + getCondition(codeMethod, "portCode", "portCName", fieldValue, isQueryCode);
				conditions += " AND validstatus='1' AND countryCode in ('CN','HKG','TW') order by portCode";
			}
		} else {// 国外
			String foreignCountryCode = otherConditions[2];
			if ("E".equals(language)) {
				conditions = conditions + getCondition(codeMethod, "portCode", "portEName", fieldValue, isQueryCode);
				conditions += " AND validstatus='1' AND countryCode='" + foreignCountryCode + "' order by portCode";
			} else {
				conditions = conditions + getCondition(codeMethod, "portCode", "portCName", fieldValue, isQueryCode);
				conditions += " AND validstatus='1' AND countryCode='" + foreignCountryCode + "' order by portCode";
			}
		}
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("portCode", pageNo, rowsPerPage, conditions);
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("portCode");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = queryCondition.getConditions();
			}
		}
		PageRecord pageRecord = new BLPrpDportFacade().findByConditions(conditions, pageNo, rowsPerPage);
		Page page = new Page(Integer.valueOf((pageNo - 1) * rowsPerPage).longValue(), Integer.valueOf(pageRecord.getCount()).longValue(), pageRecord.getRowsPerPage(), (List<?>) pageRecord.getResult());
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpDport", language);
	}
	/**
	 * 查詢外國代碼
	 * @param request
	 * @param response
	 * @param codeType
	 * @throws Exception
	 */
	private void foreignCountryCode(HttpServletRequest request, HttpServletResponse response, String codeType) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 增加是代码还是中文查询
		String isQueryCode = paramUtils.getParameter("isQueryCode");
		String conditions = "";
		if ("E".equals(otherCondition)) {
			conditions = conditions + getCondition(codeMethod, "countryCode", "countryEName", fieldValue, isQueryCode);
			conditions += " AND validstatus='1' AND countryCode not in ('CN','HKG','TW') order by countryCode";
		} else {
			conditions = conditions + getCondition(codeMethod, "countryCode", "countryCName", fieldValue, isQueryCode);
			conditions += " AND validstatus='1' AND countryCode not in ('CN','HKG','TW') order by countryCode";
		}
		// 双击分页功能的实现
		int pageNo = ConstantCodes.DEFAULT_PAGENO, rowsPerPage = ConstantCodes.DEFAULT_ROWSPERPAGE;
		if ("query".equals(paramUtils.getParameter("actionType"))) {
			HttpSession session = request.getSession();
			PrpDuserDto user = new PrpDuserDto();
			user.setQueryCondition("foreignCountryCode", pageNo, rowsPerPage, conditions);
			session.setAttribute(SESS_KEY, user);
		} else {
			PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute(SESS_KEY));
			QueryCondition queryCondition = user.getQueryCondition("foreignCountryCode");
			if (queryCondition != null) {
				pageNo = paramUtils.getIntParameter("pageNo", queryCondition.getPageNo());
				rowsPerPage = paramUtils.getIntParameter("rowsPerPage", queryCondition.getRowsPerPage());
				conditions = queryCondition.getConditions();
			}
		}
		PageRecord pageRecord = new BLPrpDportFacade().findCountryByConditions(conditions, pageNo, rowsPerPage);
		Page page = new Page(Integer.valueOf((pageNo - 1) * rowsPerPage).longValue(), Integer.valueOf(pageRecord.getCount()).longValue(), pageRecord.getRowsPerPage(), (List<?>) pageRecord.getResult());
		request.setAttribute("page", page);
		this.setData(request, page.getResult(), "PrpDport", otherCondition, "foreignCountryCode");
	}

	/**
	 * 根据结果集，设置双击域选择框数据
	 * @author 中科软
	 * @date Mar 24, 2013 3:11:28 PM
	 * @param request
	 * @param result
	 * @param param
	 */
	private void setData(HttpServletRequest request, List<?> result, Object... param) throws Exception {
		List<String> codeValues = new ArrayList<String>();
		List<String> codeLabels = new ArrayList<String>();
		if (result != null && !result.isEmpty()) {
			Iterator<?> it = result.iterator();
			String objectName = String.valueOf(param[0]);// 第一个参数为对象名
			if ("PrpDcode".equalsIgnoreCase(objectName)) {
				PrpDcode element = null;
				String methodName = param.length >= 2 ? String.valueOf(param[1]) : "";
				for (; it.hasNext();) {
					element = (PrpDcode) it.next();
					//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
					if("PersonFeeTypeFlagE9".equalsIgnoreCase(methodName)){
						String[] choseCode = {"24","25"};
						for(String right :choseCode){
							if(right.equals(element.getId().getCodeCode())){
								PrpDpersonFeeCodeRisk prpDpersonFeeCodeRisk = prpDpersonFeeCodeRiskService.findByPrimaryKey(String.valueOf(param[2]), right);
								if(prpDpersonFeeCodeRisk!=null){
									this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getId().getCodeCode(), element.getCodeCName(), prpDpersonFeeCodeRisk.getFeeCategory());
									this.setData(codeLabels, "--", element.getId().getCodeCode(), element.getCodeCName());
								}else{
									this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getId().getCodeCode(), element.getCodeCName());
									this.setData(codeLabels, "--", element.getId().getCodeCode(), element.getCodeCName());
								}
							}
						}
					}else {
						if ("PersonFeeTypeFlag".equalsIgnoreCase(methodName)) {
							PrpDpersonFeeCodeRisk prpDpersonFeeCodeRisk = prpDpersonFeeCodeRiskService.findByPrimaryKey(String.valueOf(param[2]), element.getId().getCodeCode());
							if(prpDpersonFeeCodeRisk!=null){
								this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getId().getCodeCode(), element.getCodeCName(), prpDpersonFeeCodeRisk.getFeeCategory());
							}else{
								this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getId().getCodeCode(), element.getCodeCName());
							}
						} else {
							this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getId().getCodeCode(), element.getCodeCName());
						}
						this.setData(codeLabels, "--", element.getId().getCodeCode(), element.getCodeCName());
					}
					//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END
				}
			} else if ("PrpDuser".equalsIgnoreCase(objectName)) {
				PrpDuser element = null;
				for (; it.hasNext();) {
					element = (PrpDuser) it.next();
					this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getUserCode(), element.getUserName());
					this.setData(codeLabels, "--", element.getUserCode(), element.getUserName());
				}
			} else if ("PrpDcompany".equalsIgnoreCase(objectName)) {
				PrpDcompany element = null;
				String methodName = param.length > 1 ? String.valueOf(param[1]) : "";
				for (; it.hasNext();) {
					element = (PrpDcompany) it.next();
					if ("".equalsIgnoreCase(methodName)) {
						this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getComCode(), element.getComCName());
					} else if ("queryProvince".equalsIgnoreCase(methodName)) {
						this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getComCode(), element.getComCode(), element.getComCName());
					}
					this.setData(codeLabels, "--", element.getComCode(), element.getComCName());
				}
			} else if ("PrpDcarModel".equalsIgnoreCase(objectName)) {
				String methodName = String.valueOf(param[1]);
				PrpDcarModel element = null;
				for (; it.hasNext();) {
					element = (PrpDcarModel) it.next();
					if ("modelCode".equalsIgnoreCase(methodName)) {
						this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getModelCode(), element.getModelName());
						this.setData(codeLabels, "--", element.getModelCode(), element.getModelName());
					} else if ("factory".equalsIgnoreCase(methodName)) {
						this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getFactory());
						this.setData(codeLabels, "--", element.getFactory());
					}
				}
			} else if ("PrpCitemKind".equalsIgnoreCase(objectName)) {
				PrpCitemKind element = null;
				String methodName = param.length > 1 ? String.valueOf(param[1]) : "";
				for (; it.hasNext();) {
					element = (PrpCitemKind) it.next();
					if ("".equals(methodName)) {
						this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getKindCode(), element.getKindName(),String.valueOf(element.getId().getItemKindNo()),DataUtils.getPointNumber(element.getAmount()));
						this.setData(codeLabels, "--", element.getKindCode(), element.getKindName());
					} else if ("policyItemKindCodeForAcci".equalsIgnoreCase(methodName)) {
						this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getKindCode(),element.getKindName(),element.getContractingScope(),String.valueOf(element.getId().getItemKindNo()));
						this.setData(codeLabels, "--", element.getKindCode(),element.getKindName());
					} else if ("PolicyKindCode".equals(methodName)) {
						this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getKindCode(), element.getKindName(), String.valueOf(element.getId().getItemKindNo()),DataUtils.getPointNumber(element.getAmount()));
						this.setData(codeLabels, "--", element.getKindCode(), element.getKindName());
					}else if("policyItemKindCodeNoRiskForProp".equals(methodName)){
						String itemCode = codeService.getItemCode(element);
						String itemName = codeService.getItemName(element);
						this.setData(codeValues, IConstants.FIELD_SEPARATOR, itemCode, itemName, String.valueOf(element.getId().getItemKindNo()), DataUtils.getPointNumber(element.getAmount()),
								element.getKindCode(), element.getKindName());
						this.setData(codeLabels, "--", element.getKindCode(), itemCode, itemName);
					}
				}
			} else if ("PrpDcurrency".equalsIgnoreCase(objectName)) {
				PrpDcurrency element = null;
				for (; it.hasNext();) {
					element = (PrpDcurrency) it.next();
					this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getCurrencyCode(), element.getCurrencyCName());
					this.setData(codeLabels, "--", element.getCurrencyCode(), element.getCurrencyCName());
				}
			} else if ("PrpLpayObject".equalsIgnoreCase(objectName)) {
				PrpLpayObject element = null;
				for (; it.hasNext();) {
					element = (PrpLpayObject) it.next();
					this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getPayObjectCode(), element.getPayObjectName());
					this.setData(codeLabels, "--", element.getPayObjectCode(), element.getPayObjectName());
				}
			} else if ("PrpCinsured".equalsIgnoreCase(objectName)) {
				PrpCinsured element = null;
				String methodName = param.length > 1 ? String.valueOf(param[1]) : "";
				PrpCinsuredNature prpCinsuredNature = null;
				for (; it.hasNext();) {
					element = (PrpCinsured) it.next();
					// 这里的事故者需要从保单中带出更多的信息，比如familyno,性别，身份证号等等的数据。
					// 所以需要按顺序进行拼values的顺序，目前的顺序为AcciCode|AcciName|PersonSex|Age|IdentifyNumber|FamilyNo
					// 需要从prpcinsurenature中，取事故者的性别，年龄信息。其中getSerialNo的serialNo对应的是familyNo
					prpCinsuredNature = prpCinsuredNatureService.findPrpCinsuredNature(element.getId().getPolicyNo(), element.getId().getSerialNo().intValue());
					// 空值处理
					if (prpCinsuredNature == null) {
						prpCinsuredNature = new PrpCinsuredNature();
					}
					if ("getCinsured".equalsIgnoreCase(methodName)) {
						this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getInsuredCode(), element.getInsuredName(), element.getIdentifyNumber());
					}else if("prpCinsuredAcci".equalsIgnoreCase(methodName)){
						this.setData(codeValues, IConstants.FIELD_SEPARATOR,element.getInsuredName(), prpCinsuredNature.getSex(), String.valueOf(prpCinsuredNature.getAge()), element.getIdentifyNumber());
					} else {
						this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getInsuredCode(), element.getInsuredName(), prpCinsuredNature.getSex(), String.valueOf(prpCinsuredNature.getAge()), element.getIdentifyNumber(),
								String.valueOf(element.getId().getSerialNo()));
					}
					this.setData(codeLabels, "--", element.getInsuredCode(), element.getInsuredName());
				}
			} else if ("PrpDitem".equalsIgnoreCase(objectName)) {
				PrpDitemDto element = null;
				for (; it.hasNext();) {
					element = (PrpDitemDto) it.next();
					this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getItemCode(), element.getItemCName());
					this.setData(codeLabels, "--", element.getItemCode(), element.getItemCName());
				}
			} else if ("PrpDliab".equalsIgnoreCase(objectName)) {
				PrpDliabDto element = null;
				for (; it.hasNext();) {
					element = (PrpDliabDto) it.next();
					this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getLiabCode(), element.getLiabCName());
					this.setData(codeLabels, "--", element.getLiabCode(), element.getLiabCName());
				}
			} else if ("PrpLexternalAgency".equalsIgnoreCase(objectName)) {
				PrpLexternalAgency element = null;
				for (; it.hasNext();) {
					element = (PrpLexternalAgency) it.next();
					this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getId().getComCode(), element.getComcname());
					this.setData(codeLabels, "--", element.getId().getComCode(), element.getComcname());
				}
			} else if ("UtiAdminProvince".equalsIgnoreCase(objectName)) {
				UtiAdminProvinceDto element = null;
				for (; it.hasNext();) {
					element = (UtiAdminProvinceDto) it.next();
					this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getProvinceCode(), element.getProvinceName());
					this.setData(codeLabels, "--", element.getProvinceCode(), element.getProvinceName());
				}
			} else if ("UtiAdminCity".equalsIgnoreCase(objectName)) {
				UtiAdminCityDto element = null;
				for (; it.hasNext();) {
					element = (UtiAdminCityDto) it.next();
					this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getCityCode(), element.getCityName());
					this.setData(codeLabels, "--", element.getCityCode(), element.getCityName());
				}
			} else if ("PrpDagent".equalsIgnoreCase(objectName)) {
				PrpDagent element = null;
				for (; it.hasNext();) {
					element = (PrpDagent) it.next();
					this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getAgentCode(), element.getAgentName());
					this.setData(codeLabels, "--", element.getAgentCode(), element.getAgentName());
				}
			} else if ("PrpLInsuranceSurveyor".equalsIgnoreCase(objectName)) {
				PrpLInsuranceSurveyor element = null;
				for (; it.hasNext();) {
					element = (PrpLInsuranceSurveyor) it.next();
					this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getId().getComCode(), element.getComcname());
					this.setData(codeLabels, "--", element.getId().getComCode(), element.getComcname());
				}
			} else if ("PrpDport".equalsIgnoreCase(objectName)) {
				PrpDportDto element = null;
				boolean f = param.length > 1 && "E".equals(String.valueOf(param[1]));// 第二个参数是否为E
				String methodName = param.length > 2 ? String.valueOf(param[2]) : "";
				for (; it.hasNext();) {
					element = (PrpDportDto) it.next();
					if ("foreignCountryCode".equals(methodName)) {
						this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getCountryCode(), f ? element.getCountryEName() : element.getCountryCName());
						this.setData(codeLabels, "--", element.getCountryCode(), f ? element.getCountryEName() : element.getCountryCName());
					} else {
						this.setData(codeValues, IConstants.FIELD_SEPARATOR, element.getPortCode(), f ? element.getPortEName() : element.getPortCName());
						this.setData(codeLabels, "--", element.getPortCode(), f ? element.getPortEName() : element.getPortCName());
					}
				}
			}
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * separator分割数据
	 * @author 中科软
	 * @date Mar 24, 2013 6:54:01 PM
	 * @param list
	 * @param separator
	 * @param values 至少保证长度1
	 */
	private void setData(List<String> list, String separator, String... values) {
		StringBuffer str = new StringBuffer("");
		for (int i = 0, length = values.length; i < length; i++) {
			str.append(separator);
			str.append(values[i]==null?"":values[i]);
		}
		list.add(str.substring(separator.length()).toString());
	}

	/**
	 * 校验人员的权限
	 * @author 中科软
	 * @date Mar 24, 2013 2:54:34 PM
	 * @param user 当前user
	 * @param list 需要校验的User
	 * @param TASK_CLAIM 权限类型
	 * @return
	 * @throws Exception
	 */
	private List<PrpDuser> checkPower(PrpDuserDto user, List<?> list, String TASK_CLAIM) throws Exception {
		List<PrpDuser> result = new ArrayList<PrpDuser>();
		if (list != null && !list.isEmpty()) {
			String strSql = "";
			List<PrpDcompany> companyList = null;
			PrpDuser element = null;
			QueryRule queryRule = null;
			for (Iterator<?> it = list.iterator(); it.hasNext();) {
				element = (PrpDuser) it.next();
				// 权限校验
				// 错误：1）另外当用户的登陆机构不等於用户归属机构时，则也应该满足，因为几簬有的用户的登陆机构不等於归属机构。
				// 3）导致生产环境现在调度那，根本选不出查勘人员来！！！这样的权限判断逻辑是有错误的！！
				// 解决问题思路：应该把这个用户所有现在可以登陆的机构都读出来，只要判断一个有查勘权限，就可以把这个任务调度给他
				// 问题：可能会放大一些查勘的权限，但是可以让调度调度该查勘人员了。
				user.setUserCode(element.getUserCode());
				strSql = " comcode in (select distinct comcode " + " from utiusergrade where usercode ='" + user.getUserCode() + "')";
				queryRule = QueryRule.getInstance();
				queryRule.addSql(strSql);
				companyList = this.prpDcompanyService.findPrpDcompany(queryRule);
				if (companyList != null && !companyList.isEmpty()) {
					for (PrpDcompany prpDcompany : companyList) {
						user.setLoginComCode(prpDcompany.getComCode());
						if (UIPowerAction.checkPowerReturn(user, TASK_CLAIM)) {// 是否通过校验
							result.add(element);
							break;
						}
					}
				}
			}
		}
		return result;
	}

	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	public PrpCitemKindService getPrpCitemKindService() {
		return prpCitemKindService;
	}

	public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
		this.prpCitemKindService = prpCitemKindService;
	}

	public PrpCinsuredService getPrpCinsuredService() {
		return prpCinsuredService;
	}

	public void setPrpCinsuredService(PrpCinsuredService prpCinsuredService) {
		this.prpCinsuredService = prpCinsuredService;
	}

	public PrpLexternalAgencyService getPrpLexternalAgencyService() {
		return prpLexternalAgencyService;
	}

	public void setPrpLexternalAgencyService(PrpLexternalAgencyService prpLexternalAgencyService) {
		this.prpLexternalAgencyService = prpLexternalAgencyService;
	}

	public PrpDagentService getPrpDagentService() {
		return prpDagentService;
	}

	public void setPrpDagentService(PrpDagentService prpDagentService) {
		this.prpDagentService = prpDagentService;
	}

	public PrpLInsuranceSurveyorService getPrpLInsuranceSurveyorService() {
		return prpLInsuranceSurveyorService;
	}

	public void setPrpLInsuranceSurveyorService(PrpLInsuranceSurveyorService prpLInsuranceSurveyorService) {
		this.prpLInsuranceSurveyorService = prpLInsuranceSurveyorService;
	}

	public PrpLpayObjectService getPrpLpayObjectService() {
		return prpLpayObjectService;
	}

	public void setPrpLpayObjectService(PrpLpayObjectService prpLpayObjectService) {
		this.prpLpayObjectService = prpLpayObjectService;
	}

	public PrpDcarModelService getPrpDcarModelService() {
		return prpDcarModelService;
	}

	public void setPrpDcarModelService(PrpDcarModelService prpDcarModelService) {
		this.prpDcarModelService = prpDcarModelService;
	}

	public PrpDcurrencyService getPrpDcurrencyService() {
		return prpDcurrencyService;
	}

	public void setPrpDcurrencyService(PrpDcurrencyService prpDcurrencyService) {
		this.prpDcurrencyService = prpDcurrencyService;
	}

	public PrplregistrpolicyService getPrpLregistrpolicyService() {
		return prpLregistrpolicyService;
	}

	public void setPrpLregistrpolicyService(PrplregistrpolicyService prpLregistrpolicyService) {
		this.prpLregistrpolicyService = prpLregistrpolicyService;
	}

	public PrpDpersonFeeCodeRiskService getPrpDpersonFeeCodeRiskService() {
		return prpDpersonFeeCodeRiskService;
	}

	public void setPrpDpersonFeeCodeRiskService(PrpDpersonFeeCodeRiskService prpDpersonFeeCodeRiskService) {
		this.prpDpersonFeeCodeRiskService = prpDpersonFeeCodeRiskService;
	}
	
	public EndorseService getEndorseService() {
		return endorseService;
	}

	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
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

	public UtiCodeTransferService getUtiCodeTransferService() {
		return utiCodeTransferService;
	}

	public void setUtiCodeTransferService(UtiCodeTransferService utiCodeTransferService) {
		this.utiCodeTransferService = utiCodeTransferService;
	}
	
	public CompensateService getCompensateService() {
		return compensateService;
	}
	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}
	public PrpPitemKindService getPrpPitemKindService() {
		return prpPitemKindService;
	}
	public void setPrpPitemKindService(PrpPitemKindService prpPitemKindService) {
		this.prpPitemKindService = prpPitemKindService;
	}
	public PrpCinsuredNatureService getPrpCinsuredNatureService() {
		return prpCinsuredNatureService;
	}
	public void setPrpCinsuredNatureService(PrpCinsuredNatureService prpCinsuredNatureService) {
		this.prpCinsuredNatureService = prpCinsuredNatureService;
	}
	public CodeService getCodeService() {
		return codeService;
	}
	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
	public PrpLclauseService getPrpLclauseService() {
		return prpLclauseService;
	}
	public void setPrpLclauseService(PrpLclauseService prpLclauseService) {
		this.prpLclauseService = prpLclauseService;
	}
	public PrpClimitService getPrpClimitService() {
		return prpClimitService;
	}
	public void setPrpClimitService(PrpClimitService prpClimitService) {
		this.prpClimitService = prpClimitService;
	}
	public PrpDriskConfigService getPrpDriskConfigService() {
		return prpDriskConfigService;
	}
	public void setPrpDriskConfigService(PrpDriskConfigService prpDriskConfigService) {
		this.prpDriskConfigService = prpDriskConfigService;
	}
	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}
	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}
	
}
