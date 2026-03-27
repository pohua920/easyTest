package com.sinosoft.claim.undwrt.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.PrpDcodeRiskService;
import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.service.facade.PrpDkindService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpDagent;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpDcodeRisk;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.PrpDkind;
import com.sinosoft.claim.schema.model.PrpDuser;
import com.sinosoft.claim.schema.model.SwfModelUse;
import com.sinosoft.claim.schema.model.UtiUserGrade;
import com.sinosoft.claim.schema.service.facade.PrpDagentService;
import com.sinosoft.claim.schema.service.facade.PrpDclassService;
import com.sinosoft.claim.schema.service.facade.PrpDcodeService;
import com.sinosoft.claim.schema.service.facade.SwfModelUseService;
import com.sinosoft.claim.schema.service.facade.UtiUserGradeService;
import com.sinosoft.claim.ui.control.action.UIPowerInterface;
import com.sinosoft.platform.bl.facade.BLPrpDaccItemFacade;
import com.sinosoft.platform.bl.facade.BLPrpDagentComFacade;
import com.sinosoft.platform.bl.facade.BLPrpDagreeDetailFacade;
import com.sinosoft.platform.bl.facade.BLPrpDagreementFacade;
import com.sinosoft.platform.bl.facade.BLPrpDareaGroupFacade;
import com.sinosoft.platform.bl.facade.BLPrpDartItemFacade;
import com.sinosoft.platform.bl.facade.BLPrpDarticleFacade;
import com.sinosoft.platform.bl.facade.BLPrpDcarFamilyFacade;
import com.sinosoft.platform.bl.facade.BLPrpDcarGroupFacade;
import com.sinosoft.platform.bl.facade.BLPrpDcarModelFacade;
import com.sinosoft.platform.bl.facade.BLPrpDcarModelGroupFacade;
import com.sinosoft.platform.bl.facade.BLPrpDclassFacade;
import com.sinosoft.platform.bl.facade.BLPrpDclauseFacade;
import com.sinosoft.platform.bl.facade.BLPrpDclauseKindFacade;
import com.sinosoft.platform.bl.facade.BLPrpDcurrencyFacade;
import com.sinosoft.platform.bl.facade.BLPrpDcustomerUnitFacade;
import com.sinosoft.platform.bl.facade.BLPrpDdangerCarModelFacade;
import com.sinosoft.platform.bl.facade.BLPrpDdeductCondFacade;
import com.sinosoft.platform.bl.facade.BLPrpDdeprecateRateFacade;
import com.sinosoft.platform.bl.facade.BLPrpDfieldExtFacade;
import com.sinosoft.platform.bl.facade.BLPrpDidentifierFacade;
import com.sinosoft.platform.bl.facade.BLPrpDinjuryGradeFacade;
import com.sinosoft.platform.bl.facade.BLPrpDinvestRationFacade;
import com.sinosoft.platform.bl.facade.BLPrpDitemFacade;
import com.sinosoft.platform.bl.facade.BLPrpDkindClauseFacade;
import com.sinosoft.platform.bl.facade.BLPrpDkindRelateFacade;
import com.sinosoft.platform.bl.facade.BLPrpDliabFacade;
import com.sinosoft.platform.bl.facade.BLPrpDlimitFacade;
import com.sinosoft.platform.bl.facade.BLPrpDpersonPayFacade;
import com.sinosoft.platform.bl.facade.BLPrpDplaneFacade;
import com.sinosoft.platform.bl.facade.BLPrpDportFacade;
import com.sinosoft.platform.bl.facade.BLPrpDrateFactorFacade;
import com.sinosoft.platform.bl.facade.BLPrpDrationFacade;
import com.sinosoft.platform.bl.facade.BLPrpDreinsFacade;
import com.sinosoft.platform.bl.facade.BLPrpDreportFacade;
import com.sinosoft.platform.bl.facade.BLPrpDriskFacade;
import com.sinosoft.platform.bl.facade.BLPrpDriskKindFacade;
import com.sinosoft.platform.bl.facade.BLPrpDshipFacade;
import com.sinosoft.platform.bl.facade.BLPrpDstatiTypeFacade;
import com.sinosoft.platform.bl.facade.BLPrpDtypeFacade;
import com.sinosoft.platform.bl.facade.BLPrpXPcolFacade;
import com.sinosoft.platform.bl.facade.BLPrpXPformFacade;
import com.sinosoft.platform.bl.facade.BLPrpdcarbrandFacade;
import com.sinosoft.platform.bl.facade.BLSapCompanyFacade;
import com.sinosoft.platform.bl.facade.BLSapCostCenterFacade;
import com.sinosoft.platform.bl.facade.BLSwfModelMainFacade;
import com.sinosoft.platform.bl.facade.BLSwfNodeFacade;
import com.sinosoft.platform.bl.facade.BLUtiBulletinFacade;
import com.sinosoft.platform.bl.facade.BLUtiConfigFacade;
import com.sinosoft.platform.bl.facade.BLUtiDiscussFacade;
import com.sinosoft.platform.bl.facade.BLUtiGradeFacade;
import com.sinosoft.platform.bl.facade.BLUtiGroupFacade;
import com.sinosoft.platform.bl.facade.BLUtiMessageFacade;
import com.sinosoft.platform.bl.facade.BLUtiParamFacade;
import com.sinosoft.platform.bl.facade.BLUtiProductAttrFacade;
import com.sinosoft.platform.bl.facade.BLUtiSystemFacade;
import com.sinosoft.platform.bl.facade.BLUtiTaskFacade;
import com.sinosoft.platform.bl.facade.BLUtiUserGradeFacade;
import com.sinosoft.platform.bl.facade.BLUtiUwFactorFacade;
import com.sinosoft.platform.bl.facade.BLUtiadmincityFacade;
import com.sinosoft.platform.bl.facade.BLUtiadmindistrictFacade;
import com.sinosoft.platform.bl.facade.BLUtiadminprovinceFacade;
import com.sinosoft.platform.bl.facade.BLUwGroupFacade;
import com.sinosoft.platform.dto.domain.PrpDaccItemDto;
import com.sinosoft.platform.dto.domain.PrpDagentComDto;
import com.sinosoft.platform.dto.domain.PrpDagentDto;
import com.sinosoft.platform.dto.domain.PrpDagreeDetailDto;
import com.sinosoft.platform.dto.domain.PrpDagreementDto;
import com.sinosoft.platform.dto.domain.PrpDareaGroupDto;
import com.sinosoft.platform.dto.domain.PrpDartItemDto;
import com.sinosoft.platform.dto.domain.PrpDarticleDto;
import com.sinosoft.platform.dto.domain.PrpDcarFamilyDto;
import com.sinosoft.platform.dto.domain.PrpDcarGroupDto;
import com.sinosoft.platform.dto.domain.PrpDcarModelDto;
import com.sinosoft.platform.dto.domain.PrpDcarModelGroupDto;
import com.sinosoft.platform.dto.domain.PrpDclassDto;
import com.sinosoft.platform.dto.domain.PrpDclauseDto;
import com.sinosoft.platform.dto.domain.PrpDclauseKindDto;
import com.sinosoft.platform.dto.domain.PrpDcurrencyDto;
import com.sinosoft.platform.dto.domain.PrpDcustomerUnitDto;
import com.sinosoft.platform.dto.domain.PrpDdangerCarModelDto;
import com.sinosoft.platform.dto.domain.PrpDdeductCondDto;
import com.sinosoft.platform.dto.domain.PrpDdeprecateRateDto;
import com.sinosoft.platform.dto.domain.PrpDfieldExtDto;
import com.sinosoft.platform.dto.domain.PrpDidentifierDto;
import com.sinosoft.platform.dto.domain.PrpDinjuryGradeDto;
import com.sinosoft.platform.dto.domain.PrpDinvestRationDto;
import com.sinosoft.platform.dto.domain.PrpDitemDto;
import com.sinosoft.platform.dto.domain.PrpDkindClauseDto;
import com.sinosoft.platform.dto.domain.PrpDkindRelateDto;
import com.sinosoft.platform.dto.domain.PrpDliabDto;
import com.sinosoft.platform.dto.domain.PrpDlimitDto;
import com.sinosoft.platform.dto.domain.PrpDpersonPayDto;
import com.sinosoft.platform.dto.domain.PrpDplaneDto;
import com.sinosoft.platform.dto.domain.PrpDportDto;
import com.sinosoft.platform.dto.domain.PrpDrateFactorDto;
import com.sinosoft.platform.dto.domain.PrpDrationDto;
import com.sinosoft.platform.dto.domain.PrpDreinsDto;
import com.sinosoft.platform.dto.domain.PrpDreportDto;
import com.sinosoft.platform.dto.domain.PrpDriskDto;
import com.sinosoft.platform.dto.domain.PrpDriskKindDto;
import com.sinosoft.platform.dto.domain.PrpDshipDto;
import com.sinosoft.platform.dto.domain.PrpDstatiTypeDto;
import com.sinosoft.platform.dto.domain.PrpDtypeDto;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.platform.dto.domain.PrpXPcolDto;
import com.sinosoft.platform.dto.domain.PrpXPformDto;
import com.sinosoft.platform.dto.domain.PrpdcarbrandDto;
import com.sinosoft.platform.dto.domain.SapCompanyDto;
import com.sinosoft.platform.dto.domain.SapCostCenterDto;
import com.sinosoft.platform.dto.domain.SwfModelMainDto;
import com.sinosoft.platform.dto.domain.SwfNodeDto;
import com.sinosoft.platform.dto.domain.UtiBulletinDto;
import com.sinosoft.platform.dto.domain.UtiConfigDto;
import com.sinosoft.platform.dto.domain.UtiDiscussDto;
import com.sinosoft.platform.dto.domain.UtiGradeDto;
import com.sinosoft.platform.dto.domain.UtiGroupDto;
import com.sinosoft.platform.dto.domain.UtiMessageDto;
import com.sinosoft.platform.dto.domain.UtiParamDto;
import com.sinosoft.platform.dto.domain.UtiProductAttrDto;
import com.sinosoft.platform.dto.domain.UtiSystemDto;
import com.sinosoft.platform.dto.domain.UtiTaskDto;
import com.sinosoft.platform.dto.domain.UtiUserGradeDto;
import com.sinosoft.platform.dto.domain.UtiUwFactorDto;
import com.sinosoft.platform.dto.domain.UtiadmincityDto;
import com.sinosoft.platform.dto.domain.UtiadmindistrictDto;
import com.sinosoft.platform.dto.domain.UtiadminprovinceDto;
import com.sinosoft.platform.dto.domain.UwGroupDto;
import com.sinosoft.platform.ui.control.action.IConstants;
import com.sinosoft.platform.ui.model.PowerIsSuperUserCommand;
import com.sinosoft.platform.utiall.control.OperatorConfigAction;
import com.sinosoft.sysframework.common.Constants;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.sysframework.common.util.ParamUtils;
import com.sinosoft.sysframework.common.util.SqlUtils;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.log.Logger;
import com.sinosoft.sysframework.log.Priority;
import com.sinosoft.utiall.blsvr.BLPrpDagent;
import com.sinosoft.utiall.blsvr.BLPrpDconfigCode;
import com.sinosoft.utility.string.ChgDate;

/**
 * 平台双击域录入处理类
 * @Description
 * @author 中科软
 */
public class CodeInputAction extends Struts2Action {
	/**
	 * @Fields serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;
	/** 日志*/
	private static Logger logger = Logger.getLogger(CodeInputAction.class);
	/** 常數*/
	public static final String CHANGE_METHOD = "change";
	/**常數*/
	public static final String QUERY_METHOD = "query";
	/**常數*/
	public static final String CODE_INPUT = "codeInput";// 代码查询的任务代码
	/**代碼服務*/
	private PrpDcodeService prpDcodeService;
	/**險類服務*/
	private PrpDclassService prpDclassService;
	/**險別服務*/
	private PrpDkindService prpDkindService;
	/**險種服務*/
	private PrpDcodeRiskService prpDcodeRiskService;
	/**機構服務*/
	private PrpDcompanyService prpDcompanyService;
	/**用戶服務*/
	private PrpDuserService prpDuserService;
	/**保險代理服務*/
	private PrpDagentService prpDagentService;
	/**用戶崗位服務*/
	private UtiUserGradeService utiUserGradeService;
	/**模板服務*/
	private SwfModelUseService swfModelUseService;

	/**
	 * 查询入口
	 * @return
	 */
	public String execute() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String actionType = request.getParameter("actionType");
		String forward = actionType;
		if (actionType == null || actionType.trim().equals(""))
			forward = "invalid";
		if (actionType.equals("query")) {
			query(request, response);
		} else {
			Object parameters[] = { request, response };
			Method method = getClass().getMethod(actionType, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, parameters);
		}
		return actionType;
	}

	/**
	 * 得到SQL条件
	 * @param codeMethod
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public String getCondition(String codeMethod, String fieldName, String fieldValue) {
		String strReturn = "";
		if (codeMethod.equalsIgnoreCase(CHANGE_METHOD)) {
			strReturn = fieldName + " ='" + fieldValue + "'";
		} else {
			if (!fieldValue.trim().equals("")) {
				if (fieldValue.indexOf(",") > -1) {
					String[] values = StringUtils.split(fieldValue, ",");
					strReturn = fieldName + " IN (";
					for (int i = 0; i < values.length; i++) {
						strReturn += "'" + values[i] + "'";
						if (i < values.length - 1) {
							strReturn += ",";
						}
					}
					strReturn += ") ";
				} else {
					strReturn = fieldName + " LIKE '" + fieldValue + "%'";
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
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void carModelGroup_Query(HttpServletRequest request, HttpServletResponse response) throws Exception {
		query(request, response);
	}

	/**
	 * 查询代码，如果要配置代码权限，需要有codeInput的任务权限
	 * @see com.sinosoft.sysframework.common.AbstractAction#query(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void query(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String codeType = request.getParameter("codeType");
		logger.debug("codeType=" + codeType);
		if (codeType == null) {
			codeType = "";
			logger.warn("代码輸入找不到codeType");
		}
		try {
			if (codeType.equals("recLevel")) {// 日志级别
				queryRecLevel(request, response);
			} else if (codeType.equals("userCode") || codeType.equals("userCodeByPower")) {// 用户代码
				queryUserCode(request, response);
			} else if (codeType.equals("agentCode") || codeType.equals("agentManCode") || codeType.equals("agentManCodeByPower")) {// 代理人
				queryAgentCode(request, response);
			} else if (codeType.equals("agentType")) { // 代理人类型
				queryAgentType(request, response);
			} else if (codeType.equals("comCode") || codeType.equals("comCodeByPower")) {
				queryComCode(request, response);
			} else if (codeType.equals("comCenterCode")) {
				queryCenterComCode(request, response);
			} else if (codeType.equals("lowerComCode")) {// 下级机构，不包括本身
				queryLowerComCode(request, response);
			} else if (codeType.equals("companyCode")) {
				queryCompanyCode(request, response);
			} else if (codeType.equals("taskCode")) {
				queryTaskCode(request, response);
			} else if (codeType.equals("agreementNo")) { // 代理协议号
				queryAgreementNo(request, response);
			} else if (codeType.equals("groupCode")) {
				queryGroupCode(request, response);
			} else if (codeType.equals("systemCode")) {
				querySystemCode(request, response);
			} else if (codeType.equals("paramCode")) {
				queryParamCode(request, response);
			} else if (codeType.equals("classCode")) {
				queryClassCode(request, response);
			} else if (codeType.equals("gradeCode")) {
				queryGradeCode(request, response);
			} else if (codeType.equals("UserGradeUserCode")) {
				queryUserGradeUserCode(request, response);
			} else if (codeType.equals("kindCode")) {
				queryKindCode(request, response);
			} else if (codeType.equals("carKindCode")) {
				queryCarKindCode(request, response);
			} else if (codeType.equals("riskCode")) {
				queryRiskCode(request, response);
			} else if (codeType.equals("factorCode")) {
				queryFactorCode(request, response);
			} else if (codeType.equals("carModelCode")) {
				queryCarModelCode(request, response);
			} else if (codeType.equals("currencyCode")) {
				queryCurrencyCode(request, response);
			} else if (codeType.equals("customerCode")) { // 查询客户代码
				queryCustomerCode(request, response);
			} else if (codeType.equals("classCodeByRiskCategory")) {
				queryClassCodeByRiskCategory(request, response);
			} else if (codeType.equals("riskCategoryByClassCode")) {
				queryRiskCategoryByClassCode(request, response);
			} else if (codeType.equals("modelNoByComCodeRiskCode")) {
				queryModelNoByComCodeRiskCode(request, response);
			} else if (codeType.equals("prpDtype")) {
				queryPrpDtype(request, response);
			} else if (codeType.startsWith("combofactorcodetype")) {
				queryComboFactorCodeType(request, response);
			} else if (codeType.equals("nodeNoByModelNo")) {
				queryNodeNoByModelNo(request, response);
			} else if (codeType.equals("userCodeByComCode")) {
				queryUserCodeByComCode(request, response);
			} else if (codeType.equals("riskcodeByClassCode")) {
				queryRiskcodeByClassCode(request, response);
			} else if (codeType.equals("riskClass")) {
				queryRiskClass(request, response);
			} else if (codeType.equals("riskCodeByRiskClass")) {
				queryRiskCodeByRiskClass(request, response);
			} else if (codeType.equals("comUserCode")) {
				queryComUserCode(request, response);
			} else if (codeType.equals("comUserGradeCode")) {
				queryComUserGradeCode(request, response);
			} else if (codeType.equals("exceptGradeCode")) {
				queryExceptGradeCode(request, response);
			} else if (codeType.equals("comCodeByUserCode")) {
				queryComCodeByUserCode(request, response);
			} else if (codeType.equals("gradeCodeByLevel")) {// 查询当前机构员工的岗位级别以下的岗位
				queryGradeCodeByLevel(request, response);
			} else if (codeType.equals("portCode")) {// 查询港口代码
				queryPortCode(request, response);
			} else if (codeType.equals("planeCode")) {
				queryPlaneCode(request, response);
			} else if (codeType.equals("codeType")) {
				queryCodeType(request, response);
			} else if (codeType.equals("shipCode")) {// 查询船舶代码
				queryShipCode(request, response);
			} else if (codeType.equals("agentComeCentreCode")) {// 查询代赔地区公司--承保地区代码
				queryAgentComCentre(request, response);
			} else if (codeType.equals("agentComUnitCode")) {// 查询代赔地区公司--承保公司代码
				queryAgentComUnitCode(request, response);
			} else if (codeType.equals("clauseCode")) {// 查询条款代码identifierCode
				queryClauseCode(request, response);
			} else if (codeType.equals("identifierCode")) {// 查询检验人代码
				queryIdentifierCode(request, response);
			} else if (codeType.equals("reinsCode")) {
				queryReinsCode(request, response);
			} else if (codeType.equals("accItemCode")) {
				queryAccItemCode(request, response);
			} else if (codeType.equals("prpDarticleItemCode")) {
				queryPrpDarticleItemCode(request, response);
			} else if (codeType.equals("artItemCode")) {
				queryArtItemCode(request, response);
			} else if (codeType.equals("attrCode")) {// 查询产品属性代码
				queryAttrCode(request, response);
			} else if (codeType.equals("upperClaimComCode")) {// yanglibo
				// add20081016上级理赔机构代码
				queryUpperClaimComCode(request, response);
			} else if (codeType.equals("riskCodeForCopy")) {// 查询产品属性代码
				queryRiskCodeForCopy(request, response);
			} else if (codeType.equals("kindCodeByRiskCode")) {// 查询险别代码
				queryKindCodeByRiskCode(request, response);
			} else if (codeType.equals("itemCodeByRiskCode")) {// 按险种查询标的
				queryItemCodeByRiskCode(request, response);
			} else if (codeType.equals("carKindCodeByRiskCode")) {
				queryCarKindCodeByRiskCode(request, response);
			} else if (codeType.equals("rationTypeByRiskCode")) {
				queryRationTypeByRiskCode(request, response);
			} else if (codeType.equals("limitCodeByRiskCode")) {
				queryLimitCodeByRiskCode(request, response);
			} else if (codeType.equals("itemCode")) {
				queryItemCode(request, response);
			} else if (codeType.equals("rationRationTypeByRiskCode")) {
				queryRationRationTypeByRiskCode(request, response);
			} else if (codeType.equals("endorTypeByRiskCode")) {
				queryEndorTypeByRiskCode(request, response);
			} else if (codeType.equals("liabCodeByRiskCode")) {
				queryLiabCodeByRiskCode(request, response);
			} else if (codeType.equals("liabCode")) {
				queryLiabCode(request, response);
			} else if (codeType.equals("codeCodeByCodeType")) {
				queryCodeCodeByCodeType(request, response);
			} else if (codeType.equals("configCode")) {
				queryConfigCode(request, response);
			} else if (codeType.equals("modelNo")) {
				queryModelNo(request, response);
			} else if (codeType.equals("carGroupGroupCode")) {
				queryCarGroupCode(request, response);
			} else if (codeType.equals("familyID")) {
				queryFamilyID(request, response);
			} else if (codeType.equals("tradeMarkID")) {
				queryTradeMarkID(request, response);
			} else if (codeType.equals("upperReinsCode")) {
				queryUpperReinsCode(request, response);
			} else if (codeType.equals("reportCode")) {
				queryReportCode(request, response);
			} else if (codeType.equals("clauseType")) {
				queryClauseType(request, response);
			} else if (codeType.equals("clauseTypeByRiskCode")) {
				queryClauseTypeByRiskCode(request, response);
			} else if (codeType.equals("relateKindCode")) {
				queryRelateKindCode(request, response);
			} else if (codeType.equals("distinctKindCode")) {
				queryDistinctKindCode(request, response);
			} else if (codeType.equals("relateKindCodeByRiskCodeKindCode")) {
				queryRelateKindCodeByRiskCodeKindCode(request, response);
			} else if (codeType.equals("belongType")) {
				queryBelongType(request, response);
			} else if (codeType.equals("belongTypeByRiskCode")) {
				queryBelongTypeByRiskCode(request, response);
			} else if (codeType.equals("payItemCode")) {
				queryPayItemCode(request, response);
			} else if (codeType.equals("deductCondCodeByRiskCode")) {
				queryDeductCondCodeByRiskCode(request, response);
			} else if (codeType.equals("injuryGrade")) {
				queryInjuryGrade(request, response);
			} else if (codeType.equals("riskKindCode")) {
				queryRiskKindCode(request, response);
			} else if (codeType.equals("factorCodeDistinct")) {
				queryFactorCodeDistinct(request, response);
			} else if (codeType.equals("rateTypeValue")) {
				queryRateTypeValue(request, response);
			} else if (codeType.equals("carTypeGroupNo")) {
				queryCarTypeGroupNo(request, response);
			} else if (codeType.equals("codeCodeDistinct")) {
				queryCodeCodeDistinct(request, response);
			} else if (codeType.equals("relateKindCodeByRiskCode")) {
				queryRelateKindCodeByRiskCode(request, response);
			} else if (codeType.equals("columnNameByRiskCode")) {
				queryColumnNameByRiskCode(request, response);
			} else if (codeType.equals("areaGroupByRiskCode")) {
				queryAreaGroupByRiskCode(request, response);
			} else if (codeType.equals("tableNameByRiskCode")) {
				queryTableNameByRiskCode(request, response);
			} else if (codeType.equals("clauseCodeByRiskCode")) {
				queryClauseCodeByRiskCode(request, response);
			} else if (codeType.equals("bulletinID")) {// 查询公告板ID
				queryUtiBulletinID(request, response);
			} else if (codeType.equals("discussID")) {// 查询讨论ID
				queryUtiDiscussID(request, response);
			} else if (codeType.equals("messageId")) {// 查询私信ID
				queryUtiMessageID(request, response);
			} else if (codeType.equals("nodeNo")) {
				queryNodeNo(request, response);
			} else if (codeType.equals("groupNo")) {
				queryGroupNo(request, response);
			} else if (codeType.equals("factorCodeByClassCodeUwType")) {
				queryFactorCodeByClassCodeUwType(request, response);
			} else if (codeType.equals("sapComCode")) { // 查询SAP公司代码
				querySapComCode(request, response);
			} else if (codeType.equals("costCenterCode")) { // 查询成本中心代码
				queryCostCenterCode(request, response);
			} else if (codeType.equals("Province")) {
				queryProvince(request, response);
			} else if (codeType.equals("City")) {
				queryCity(request, response);
			} else if (codeType.equals("Area")) {
				queryArea(request, response);
			} else if (codeType.equals("bankTreeCode")) {
				queryBankCode(request, response);
			} else if (codeType.equals("orgCode")) {// 查询prpddangercarmodel表中存在的comcode
				queryOrgCode(request, response);
			} else if (codeType.equals("comCodeByCarDanger")) {// 查询prpddangercarmodel表中存在的comcode
				queryComCodeByCarDanger(request, response);
			} else if (codeType.equals("comCodeByCarBandGroupCode")) { // 查询prpddangercarmodel表中存在的车系组号
				queryComCodeByCarBandGroupCode(request, response);
			} else if (codeType.equals("seriesId")) {
				querySeriesId(request, response);
			} else if (codeType.equals("seriesIdByDangerCarModel")) {
				querySeriesIdByCarDangerCarmodel(request, response);
			} else if (codeType.equals("series_Name")) {
				querySeriesName(request, response);
			} else if (codeType.equals("carModelName")) {
				queryCarModelName(request, response);
			} else if (codeType.equals("modelCodeByDangerCarModel")) {// 查询prpddangercarmodel表中存在的ModelCode
				querymodelCodeByDangerCarModel(request, response);
			} else if (codeType.equals("modelNameByDangerCarModel")) {// 查询prpddangercarmodel表中存在的ModelCode
				querymodelNameByDangerCarModel(request, response);
			} else if (codeType.equals("KindByDangerCarModel")) {// 查询prpddangercarmodel表中存在的ModelCode
				queryKindByDangerCarModel(request, response);
			} else if (codeType.equals("subAgentType")) {// 查询prpdcode add by
				// dengpeng 20080305
				querySubAgentType(request, response);
			} else if (codeType.equals("queryJFeeCombine")) {
				queryJFeeCombine(request, response);
			} else if (codeType.equals("comAccountCode")) {
				querycomAccountCode(request, response);
			} else if (codeType.equals("utiUwLevelComCode")) {
				queryutiUwLevelComCode(request, response);//
			} else if (codeType.equals("LogonComcode")) {
				queryLogonComcode(request, response);// (选择的usercode)人员的登录机构
			} else if (codeType.equals("queryComCodeGK")) {
				queryComCodeGK(request, response);// (选择的usercode)选择归属机构
			} else if (codeType.equals("Handler2Code")) {
				Handler2Code(request, response);// (选择的usercode)选择归属机构的归属业务员
			} else if (codeType.equals("BusinessNatureGK")) {
				BusinessNatureGK(request, response);// 选择业务来源
			} else if (codeType.equals("AgentCodeGK")) {
				AgentCodeGK(request, response);// 选择代理人
			} else if (codeType.equals("btSelectAgentProtocol")) {
				btSelectAgentProtocol(request, response);// 选择代理人
			} else if (codeType.equals("CountryCode")) {
				queryCountryCode(request, response);
			} else if (codeType.equals("PortCountryCode")) {
				queryPortCountryCode(request, response, codeType);// 选择国别代码
			} else if (codeType.equals("groupNature")) {
				GroupNature(request, response);
			} else if (codeType.equals("groupNatureDetail")) {
				GroupNatureDetail(request, response);
			} else {
				queryPrpDcode(request, response, codeType);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ArrayList<?> codeValues = new ArrayList<Object>();
			ArrayList<?> codeLabels = new ArrayList<Object>();
			request.setAttribute("codeValues", codeValues.toArray());
			request.setAttribute("codeLabels", codeLabels.toArray());
		}
	}

	/**
	 * 日志级别代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryRecLevel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String fieldValue = paramUtils.getParameter("fieldValue");
		if (fieldValue.trim().length() == 0 || fieldValue.indexOf("" + Priority.DEBUG) > -1) {
			codeValues.add(Priority.DEBUG + IConstants.FIELD_SEPARATOR + "调试");
			codeLabels.add("调试");
		}
		if (fieldValue.trim().length() == 0 || fieldValue.indexOf("" + Priority.INFO) > -1) {
			codeValues.add(Priority.INFO + IConstants.FIELD_SEPARATOR + "信息");
			codeLabels.add("信息");
		}
		if (fieldValue.trim().length() == 0 || fieldValue.indexOf("" + Priority.WARN) > -1) {
			codeValues.add(Priority.WARN + IConstants.FIELD_SEPARATOR + "警告");
			codeLabels.add("警告");
		}
		if (fieldValue.trim().length() == 0 || fieldValue.indexOf("" + Priority.ERROR) > -1) {
			codeValues.add(Priority.ERROR + IConstants.FIELD_SEPARATOR + "错误");
			codeLabels.add("错误");
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询用户代码，含权限处理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryUserCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "UserCode", fieldValue);
		conditions += " AND validstatus='1'";
		// 添加权限处理
		// 添加员工查询权限
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions += uiPowerInterface.addPower((UserDto) request.getSession().getAttribute("user"), "PrpDuser", "UserCode", "ComCode");

		conditions = conditions + " order by UserCode ";
		List<PrpDuser> result = prpDuserService.findByConditions(conditions);
		for (Iterator<PrpDuser> iter = result.iterator(); iter.hasNext();) {
			PrpDuser element = iter.next();
			codeValues.add(element.getUserCode() + IConstants.FIELD_SEPARATOR + element.getUserName());
			codeLabels.add(element.getUserCode() + "--" + element.getUserName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询代理人代码，含权限处理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryAgentCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		int pageNo = paramUtils.getIntParameter("pageNo", 1);
		int rowsPerPage = paramUtils.getIntParameter("rowsPerPage", 1000);

		String conditions = getCondition(codeMethod, "AgentCode", fieldValue);

		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto userDto = (UserDto) request.getSession().getAttribute("user");
		conditions += uiPowerInterface.addPower(userDto, "PrpDagent", "", "ComCode");

		conditions += " order by AgentCode ";
		Page record = prpDagentService.findPrpDagent(conditions, pageNo, pageNo);
		Collection<?> result = record.getResult();
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDagentDto prpDagentManDto = (PrpDagentDto) iter.next();
			codeValues.add(prpDagentManDto.getAgentCode() + IConstants.FIELD_SEPARATOR + prpDagentManDto.getAgentName());
			codeLabels.add(prpDagentManDto.getAgentCode() + "--" + prpDagentManDto.getAgentName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询代理类型
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryAgentType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "codecode", fieldValue);
		conditions += " AND CodeType ='BusinessNature' ";
		conditions += " AND ValidStatus='1' ORDER BY CodeCode";

		List<PrpDcode> result = prpDcodeService.findByConditions(conditions);
		for (Iterator<PrpDcode> iter = result.iterator(); iter.hasNext();) {
			PrpDcode element = (PrpDcode) iter.next();
			// Reason:新增时，"代理人/寿险机构类型："的选择内容中去掉"互动业务--个险营销"、"互动业务--团险"、
			// "互动业务--收展"、"互动业务--中介"四个选项
			if (element.getId().getCodeCode().equals("b") || element.getId().getCodeCode().equals("c") || element.getId().getCodeCode().equals("d") || element.getId().getCodeCode().equals("k")) {
				continue;
			}
			codeValues.add(element.getId().getCodeCode() + IConstants.FIELD_SEPARATOR + element.getCodeCName());
			codeLabels.add(element.getId().getCodeCode() + "--" + element.getCodeCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询代理子类型
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void querySubAgentType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		String conditions = " ";
		conditions = getCondition(codeMethod, "codecode", fieldValue);
		conditions += " AND CodeType ='SubBusinessNature'";
		// 对otherCondition进行判断
		if (otherCondition != null && !("".equals(otherCondition)) && otherCondition.length() == 1) {
			conditions += " AND substr(CodeCode,1,1) = '" + otherCondition + "'";
		}
		conditions += " AND ValidStatus='1' ORDER BY CodeCode";
		List<PrpDcode> result = prpDcodeService.findByConditions(conditions);
		for (Iterator<PrpDcode> iter = result.iterator(); iter.hasNext();) {
			PrpDcode element = iter.next();
			codeValues.add(element.getId().getCodeCode() + IConstants.FIELD_SEPARATOR + element.getCodeCName());
			codeLabels.add(element.getId().getCodeCode() + "--" + element.getCodeCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 根據險種查詢險類
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryRiskcodeByClassCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");

		// 处理upperComCode
		int start = otherCondition.indexOf("prpDriskClassCode=");
		int begin = otherCondition.indexOf("=");
		String prpDriskClassCode = "";
		if (start > -1) {
			prpDriskClassCode = otherCondition.substring(begin + 1, otherCondition.length());
		}
		String conditions = " ";

		conditions = getCondition(codeMethod, "RiskCode", fieldValue);
		if (!prpDriskClassCode.equals("")) {
			conditions += SqlUtils.convertString("ClassCode", prpDriskClassCode);
		}

		conditions = conditions + " AND ValidStatus='1' order by RiskCode ";
		BLPrpDriskFacade facade = new BLPrpDriskFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDriskDto element = (PrpDriskDto) iter.next();
			codeValues.add(element.getRiskCode() + IConstants.FIELD_SEPARATOR + element.getRiskCName());
			codeLabels.add(element.getRiskCode() + "--" + element.getRiskCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 查詢險種
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryRiskCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "RiskCode", fieldValue);
		conditions = conditions + " AND validstatus='1' order by RiskCode ";
		BLPrpDriskFacade facade = new BLPrpDriskFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDriskDto prpDriskDto = (PrpDriskDto) iter.next();
			codeValues.add(prpDriskDto.getRiskCode() + IConstants.FIELD_SEPARATOR + prpDriskDto.getRiskCName());
			codeLabels.add(prpDriskDto.getRiskCode() + "--" + prpDriskDto.getRiskCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 查詢用戶
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryUserGradeUserCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "GradeCode", fieldValue);
		// POWER 添加员工查询权限
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions += uiPowerInterface.addPower((UserDto) request.getSession().getAttribute("user"), "UtiUserGrade", "UserCode", "ComCode");
		BLUtiUserGradeFacade facade = new BLUtiUserGradeFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			UtiUserGradeDto utiGroupDto = (UtiUserGradeDto) iter.next();
			codeValues.add(utiGroupDto.getUserCode() + IConstants.FIELD_SEPARATOR + utiGroupDto.getGradeCode());
			codeLabels.add(utiGroupDto.getUserCode() + "--" + utiGroupDto.getGradeCode());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 查詢險別
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryKindCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "KindCode", fieldValue);
		conditions = conditions + " order by KindCode ";
		List<PrpDkind> result = prpDkindService.findByConditions(conditions);
		for (Iterator<PrpDkind> iter = result.iterator(); iter.hasNext();) {
			PrpDkind PrpDkind = iter.next();
			codeValues.add(PrpDkind.getId().getKindCode() + IConstants.FIELD_SEPARATOR + PrpDkind.getKindCName());
			codeLabels.add(PrpDkind.getId().getKindCode() + "--" + PrpDkind.getKindCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 查詢車輛類型
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryCarKindCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		String conditions = " ";
		conditions = getCondition(codeMethod, "KindCode", fieldValue);
		conditions = conditions + " AND RiskCode='" + otherCondition + "' AND ValidStatus='1' order by KindCode";
		List<PrpDkind> result = prpDkindService.findByConditions(conditions);
		for (Iterator<PrpDkind> iter = result.iterator(); iter.hasNext();) {
			PrpDkind PrpDkind = iter.next();
			codeValues.add(PrpDkind.getId().getKindCode() + IConstants.FIELD_SEPARATOR + PrpDkind.getKindCName());
			codeLabels.add(PrpDkind.getId().getKindCode() + "--" + PrpDkind.getKindCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 查詢權限等級
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryGradeCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "GradeCode", fieldValue);
		conditions = conditions + " order by GradeCode ";
		BLUtiGradeFacade facade = new BLUtiGradeFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			UtiGradeDto utiGroupDto = (UtiGradeDto) iter.next();
			codeValues.add(utiGroupDto.getGradeCode() + IConstants.FIELD_SEPARATOR + utiGroupDto.getGradeName());
			codeLabels.add(utiGroupDto.getGradeCode() + "--" + utiGroupDto.getGradeName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询部门代码，含权限处理（add by yanglibo 20081016查询上级理赔机构代码）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryUpperClaimComCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("upperComCode=");
		String upperComCode = "";
		if (start > -1) {
			int end = otherCondition.indexOf(start + ",");
			if (end > -1) {
				upperComCode = otherCondition.substring(start + "upperComCode=".length(), end).trim();
			} else {
				upperComCode = otherCondition.substring(start + "upperComCode=".length()).trim();
			}
		}
		String conditions = " ";
		conditions = getCondition(codeMethod, "ComCode", fieldValue);
		if (!upperComCode.equals("")) {
			conditions += " AND uppercomcode='" + upperComCode + "' ";
		}
		conditions += " AND validstatus='1'";
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions += uiPowerInterface.addPower((UserDto) (request.getSession().getAttribute("user")), "PrpDcompany", "", "ComCode");
		conditions += " And substr(comtype,3,1)  = '1' ";
		conditions = conditions + " order by ComCode ";
		List<PrpDcompany> result = prpDcompanyService.findByConditions(conditions);
		for (Iterator<PrpDcompany> iter = result.iterator(); iter.hasNext();) {
			PrpDcompany element = (PrpDcompany) iter.next();
			codeValues.add(element.getComCode() + IConstants.FIELD_SEPARATOR + element.getComCName());
			codeLabels.add(element.getComCode() + "--" + element.getComCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询部门代码，含权限处理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryComCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");

		// 处理机构查询条件
		String conditions = " ";
		conditions = getCondition(codeMethod, "ComCode", fieldValue);
		if (!otherCondition.equals("")) {
			String attribute = "";
			String value = "";
			int comma = otherCondition.indexOf(",");
			int equal;
			while (comma > -1) {
				attribute = otherCondition.substring(0, comma);
				equal = attribute.indexOf("=");
				value = attribute.substring(equal + 1);
				attribute = attribute.substring(0, equal);
				otherCondition = otherCondition.substring(comma + 1);
				comma = otherCondition.indexOf(",");
				conditions += " AND " + attribute + "='" + value + "'";
			}
			attribute = otherCondition;
			equal = attribute.indexOf("=");

			value = attribute.substring(equal + 1);

			attribute = attribute.substring(0, equal);

			conditions += " AND " + attribute + "='" + value + "'";
		}

		conditions += " AND validstatus='1'";
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions += uiPowerInterface.addPower((UserDto) (request.getSession().getAttribute("user")), "PrpDcompany", "", "ComCode");
		conditions = conditions + " order by ComCode ";
		List<PrpDcompany> result = prpDcompanyService.findByConditions(conditions);
		for (Iterator<PrpDcompany> iter = result.iterator(); iter.hasNext();) {
			PrpDcompany element = iter.next();
			codeValues.add(element.getComCode() + IConstants.FIELD_SEPARATOR + element.getComCName());
			codeLabels.add(element.getComCode() + "--" + element.getComCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询部门代码，含权限处理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryCenterComCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 处理upperComCode
		int start = otherCondition.indexOf("upperComCode=");
		String upperComCode = "";
		if (start > -1) {
			int end = otherCondition.indexOf(start + ",");
			if (end > -1) {
				upperComCode = otherCondition.substring(start + "upperComCode=".length(), end).trim();
			} else {
				upperComCode = otherCondition.substring(start + "upperComCode=".length()).trim();
			}
		}
		String conditions = " ";
		conditions = getCondition(codeMethod, "ComCode", fieldValue);
		if (!upperComCode.equals("")) {
			conditions += " AND uppercomcode='" + upperComCode + "' ";
		}
		conditions += " AND validstatus='1'";
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions += uiPowerInterface.addPower((UserDto) (request.getSession().getAttribute("user")), "PrpDcompany", "", "ComCode");
		conditions = conditions + "AND (Centerflag = '3' Or (Substr(Comtype, 3, 1) = '1' And comlevel='2')) order by ComCode ";
		List<PrpDcompany> result = prpDcompanyService.findByConditions(conditions);
		for (Iterator<PrpDcompany> iter = result.iterator(); iter.hasNext();) {
			PrpDcompany element = iter.next();
			codeValues.add(element.getComCode() + IConstants.FIELD_SEPARATOR + element.getComCName());
			codeLabels.add(element.getComCode() + "--" + element.getComCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询下级部门代码，含权限处理
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author 中科软 包含本级别
	 */
	private void queryutiUwLevelComCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");

		// 处理机构查询条件
		String conditions = " ";
		conditions = getCondition(codeMethod, "ComCode", fieldValue);
		if (!otherCondition.equals("")) {
			String attribute = "";
			String value = "";
			int comma = otherCondition.indexOf(",");
			int equal;
			while (comma > -1) {
				attribute = otherCondition.substring(0, comma);
				equal = attribute.indexOf("=");
				value = attribute.substring(equal + 1);
				attribute = attribute.substring(0, equal);
				otherCondition = otherCondition.substring(comma + 1);
				comma = otherCondition.indexOf(",");
				conditions += " AND " + attribute + "='" + value + "'";
			}
			attribute = otherCondition;
			equal = attribute.indexOf("=");

			value = attribute.substring(equal + 1);
			attribute = attribute.substring(0, equal);
			conditions += " Start With " + attribute + "='" + value + "'" + "Connect By Prior Comcode = Uppercomcode And Comcode <> Prior Uppercomcode";
		}
		conditions += " AND validstatus='1'";
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions += uiPowerInterface.addPower((UserDto) (request.getSession().getAttribute("user")), "PrpDcompany", "", "ComCode");
		conditions = conditions + "order by ComCode ";
		List<PrpDcompany> result = prpDcompanyService.findByConditions(conditions);
		for (Iterator<PrpDcompany> iter = result.iterator(); iter.hasNext();) {
			PrpDcompany element = iter.next();
			codeValues.add(element.getComCode() + IConstants.FIELD_SEPARATOR + element.getComCName());
			codeLabels.add(element.getComCode() + "--" + element.getComCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询下级部门代码，含权限处理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryLowerComCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 处理upperComCode
		int start = otherCondition.indexOf("upperComCode=");
		String upperComCode = "";
		if (start > -1) {
			int end = otherCondition.indexOf(start + ",");
			if (end > -1) {
				upperComCode = otherCondition.substring(start + "upperComCode=".length(), end).trim();
			} else {
				upperComCode = otherCondition.substring(start + "upperComCode=".length()).trim();
			}
		}
		String conditions = " ";
		conditions = " AND " + getCondition(codeMethod, "ComCode", fieldValue);
		if (upperComCode.trim().length() > 0) {
			PrpDcompany upperCom = prpDcompanyService.findByPrimaryKey(upperComCode);

			codeValues.add(upperCom.getComCode() + IConstants.FIELD_SEPARATOR + upperCom.getComCode() + "--" + upperCom.getComCName());
			codeLabels.add(upperCom.getComCode() + "--" + upperCom.getComCName());

			Collection<PrpDcompany> result = prpDcompanyService.findByComCode(upperComCode, true, conditions);

			for (Iterator<PrpDcompany> iter = result.iterator(); iter.hasNext();) {
				PrpDcompany element = iter.next();
				if (element.getComCode().equals(upperComCode)) {
					continue;
				}
				codeValues.add(element.getComCode() + IConstants.FIELD_SEPARATOR + element.getComCode() + "--" + element.getComCName());
				codeLabels.add(element.getComCode() + "--" + element.getComCName());
			}

			request.setAttribute("codeValues", codeValues.toArray());
			request.setAttribute("codeLabels", codeLabels.toArray());
		}
	}

	/**
	 * 查询公司代码，含权限处理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryCompanyCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "ComCode", fieldValue);
		conditions += " AND ComCode[7,8]='00' AND validstatus='1'";
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions += uiPowerInterface.addPower((UserDto) (request.getSession().getAttribute("user")), "PrpDcompany", "", "ComCode");
		conditions = conditions + " order by ComCode ";

		List<PrpDcompany> result = prpDcompanyService.findByConditions(conditions);
		for (Iterator<PrpDcompany> iter = result.iterator(); iter.hasNext();) {
			PrpDcompany element = iter.next();
			codeValues.add(element.getComCode() + IConstants.FIELD_SEPARATOR + element.getComCName());
			codeLabels.add(element.getComCode() + "--" + element.getComCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 查詢菜單代碼
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryTaskCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "TaskCode", fieldValue);
		conditions = conditions + " order by TaskCode ";
		BLUtiTaskFacade facade = new BLUtiTaskFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			UtiTaskDto utiTaskDto = (UtiTaskDto) iter.next();
			codeValues.add(utiTaskDto.getTaskCode() + IConstants.FIELD_SEPARATOR + utiTaskDto.getTaskName());
			codeLabels.add(utiTaskDto.getTaskCode() + "--" + utiTaskDto.getTaskName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询权限组号
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryGroupCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "GroupCode", fieldValue);
		conditions = conditions + " order by GroupCode ";
		BLUtiGroupFacade facade = new BLUtiGroupFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			UtiGroupDto utiGroupDto = (UtiGroupDto) iter.next();
			codeValues.add(utiGroupDto.getGroupCode() + IConstants.FIELD_SEPARATOR + utiGroupDto.getGroupName());
			codeLabels.add(utiGroupDto.getGroupCode() + "--" + utiGroupDto.getGroupName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 查詢PrpDcode
	 * @param request
	 * @param response
	 * @param codeType
	 * @throws Exception
	 */
	private void queryPrpDcode(HttpServletRequest request, HttpServletResponse response, String codeType) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "codecode", fieldValue);
		conditions += " AND codetype ='" + codeType + "' ";
		conditions += " AND validstatus='1' order by codecode";
		List<PrpDcode> result = prpDcodeService.findByConditions(conditions);
		for (Iterator<PrpDcode> iter = result.iterator(); iter.hasNext();) {
			PrpDcode element = iter.next();
			codeValues.add(element.getId().getCodeCode() + IConstants.FIELD_SEPARATOR + element.getCodeCName());
			codeLabels.add(element.getId().getCodeCode() + "--" + element.getCodeCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 查詢險種
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryPrpRiskCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "RiskCode", fieldValue);
		conditions += " AND validstatus='1' order by riskcode";
		BLPrpDriskFacade facade = new BLPrpDriskFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDriskDto element = (PrpDriskDto) iter.next();
			codeValues.add(element.getRiskCode() + IConstants.FIELD_SEPARATOR + element.getRiskCName());
			codeLabels.add(element.getRiskCode() + "--" + element.getRiskCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 查詢系統代碼
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void querySystemCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "SystemCode", fieldValue);
		conditions += " order by SystemCode";
		BLUtiSystemFacade facade = new BLUtiSystemFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			UtiSystemDto element = (UtiSystemDto) iter.next();
			codeValues.add(element.getSystemCode() + IConstants.FIELD_SEPARATOR + element.getSystemName());
			codeLabels.add(element.getSystemCode() + "--" + element.getSystemName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 查詢 UtiParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryParamCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "ParamCode", fieldValue);
		conditions += " order by ParamCode";
		BLUtiParamFacade facade = new BLUtiParamFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			UtiParamDto element = (UtiParamDto) iter.next();
			codeValues.add(element.getParamCode() + IConstants.FIELD_SEPARATOR + element.getParamDesc());
			codeLabels.add(element.getParamCode() + "--" + element.getParamDesc());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 查詢險類
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryClassCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "ClassCode", fieldValue);
		conditions += " and ValidStatus='1' order by ClassCode";
		BLPrpDclassFacade facade = new BLPrpDclassFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDclassDto element = (PrpDclassDto) iter.next();
			codeValues.add(element.getClassCode() + IConstants.FIELD_SEPARATOR + element.getClassName());
			codeLabels.add(element.getClassCode() + "--" + element.getClassName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 通過RiskCategory 查詢險類
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryClassCodeByRiskCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<String> codeValues = new ArrayList<String>(), codeLabels = new ArrayList<String>();
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		StringBuffer conditions = new StringBuffer(200);
		conditions.append(getCondition(codeMethod, "ClassCode", fieldValue)).append(" ");
		conditions.append(SqlUtils.convertString("RiskCategory", otherCondition));
		conditions.append(" and ValidStatus='1' order by ClassCode");
		BLPrpDclassFacade facade = new BLPrpDclassFacade();
		Collection<?> result = facade.findByConditions(conditions.toString());
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDclassDto element = (PrpDclassDto) iter.next();
			codeValues.add(element.getClassCode() + IConstants.FIELD_SEPARATOR + element.getClassName());
			codeLabels.add(element.getClassCode() + "--" + element.getClassName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 查詢PrpDtype
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryPrpDtype(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<String> codeValues = new ArrayList<String>(), codeLabels = new ArrayList<String>();
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		StringBuffer conditions = new StringBuffer(200);
		conditions.append(getCondition(codeMethod, "CodeType", fieldValue));
		conditions.append(" and ValidStatus='1' order by CodeType");
		BLPrpDtypeFacade facade = new BLPrpDtypeFacade();
		Collection<?> result = facade.findByConditions(conditions.toString());
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDtypeDto element = (PrpDtypeDto) iter.next();
			codeValues.add(element.getCodeType() + IConstants.FIELD_SEPARATOR + element.getCodeTypeDesc());
			codeLabels.add(element.getCodeType() + "--" + element.getCodeTypeDesc());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 通過險類查險種
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryRiskCategoryByClassCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<String> codeValues = new ArrayList<String>(), codeLabels = new ArrayList<String>();
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		StringBuffer conditions = new StringBuffer(200);
		conditions.append(getCondition(codeMethod, "codecode", fieldValue));
		conditions.append(" AND codetype ='RiskCategory' AND validstatus='1' order by codecode");
		List<PrpDcode> bigRiskCategory = prpDcodeService.findByConditions(conditions.toString());
		// 所有的险种大类
		conditions = new StringBuffer(200);
		conditions.append(getCondition(codeMethod, "ClassCode", otherCondition));
		conditions.append(" and ValidStatus='1' order by ClassCode");
		BLPrpDclassFacade blPrpDclassFacade = new BLPrpDclassFacade();
		Collection<?> classCodeList = blPrpDclassFacade.findByConditions(conditions.toString());
		HashSet<String> classCodeRiskCategorySet = new HashSet<String>();
		PrpDclassDto prpDclassDto = null;
		PrpDcode prpDcode = null;
		Iterator<?> it = classCodeList.iterator();
		while (it.hasNext()) {
			prpDclassDto = (PrpDclassDto)it.next();
			classCodeRiskCategorySet.add(prpDclassDto.getRiskCategory());
		}
		for (int i = 0; i < bigRiskCategory.size(); i++) {
			prpDcode = bigRiskCategory.get(i);
			if (classCodeRiskCategorySet.contains(prpDcode.getId().getCodeCode())) {
				codeValues.add(prpDcode.getId().getCodeCode() + IConstants.FIELD_SEPARATOR + prpDcode.getCodeCName());
				codeLabels.add(prpDcode.getId().getCodeCode() + "--" + prpDcode.getCodeCName());
			}
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 查詢模板信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryModelNoByComCodeRiskCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String otherCondition = paramUtils.getParameter("otherCondition");
		String conditions = " 1=1 ";
		String[] addonsCondition = otherCondition.split(";");
		String addonsComCode = " AND ComCode='" + addonsCondition[0] + "' ";
		String addonsRiskCode = SqlUtils.convertString("RiskCode", addonsCondition[1]);
		String addonsModelType = SqlUtils.convertString("ModelType", addonsCondition[2]);
		conditions = conditions + addonsComCode + addonsRiskCode + addonsModelType;
		// POWER 添加员工查询权限
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions += uiPowerInterface.addPower((UserDto) request.getSession().getAttribute("user"), "SwfModelUse", "", "ComCode");

		BLSwfModelMainFacade mainFacade = new BLSwfModelMainFacade();
		List<SwfModelUse> swfModelUseList = swfModelUseService.findByConditions(conditions);
		SwfModelUse modelUse = null;
		SwfModelMainDto modelMainDto = null;
		int modelNo = 0;
		String label = null;
		for (int i = 0; i < swfModelUseList.size(); i++) {
			modelUse = swfModelUseList.get(i);
			modelNo = modelUse.getId().getModelNo();
			modelMainDto = mainFacade.findByPrimaryKey(modelNo);
			label = modelNo + "--" + modelMainDto.getModelName();
			if (!codeLabels.contains(label)) {
				codeValues.add(modelNo + IConstants.FIELD_SEPARATOR + modelMainDto.getModelName());
				codeLabels.add(modelNo + "--" + modelMainDto.getModelName());
			}
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryComboFactorCodeType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeType = paramUtils.getParameter("codeType").split("=")[1];
		if ("CarItemKind".equalsIgnoreCase(codeType)) {
			queryPrpDkind(request, ConstantCodes.RISKCODE_DAA);
			return;
		}
		if ("MotoItemKind".equalsIgnoreCase(codeType)) {
			queryPrpDkind(request, "0520");
			return;
		}
		String classCode = paramUtils.getParameter("otherCondition");
		StringBuffer conditions = new StringBuffer("1=1");
		conditions.append(SqlUtils.convertString("codetype", codeType));
		conditions.append(SqlUtils.convertString("riskcode", classCode + "%"));
		List<PrpDcodeRisk> codeRiskList = prpDcodeRiskService.findByConditions(conditions.toString());
		String codecode = "";
		for (int i = 0; i < codeRiskList.size(); i++) {
			PrpDcodeRisk codeRisk = codeRiskList.get(i);
			codecode = codecode + codeRisk.getId().getCodeCode() + ",";
		}
		codecode = codecode.length() > 0 ? codecode.substring(0, codecode.length() - 1) : codecode;
		conditions = new StringBuffer("1=1");
		conditions.append(SqlUtils.convertString("codetype", codeType));
		conditions.append(SqlUtils.convertString("codecode", codecode));
		conditions.append(SqlUtils.convertString("validstatus", "1"));
		conditions.append(" order by codecode");
		List<PrpDcode> result = prpDcodeService.findByConditions(conditions.toString());
		for (Iterator<PrpDcode> iter = result.iterator(); iter.hasNext();) {
			PrpDcode element = (PrpDcode) iter.next();
			codeValues.add(element.getId().getCodeCode() + IConstants.FIELD_SEPARATOR + element.getCodeCName());
			codeLabels.add(element.getId().getCodeCode() + "--" + element.getCodeCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 查詢PrpDkind
	 * @param request
	 * @param riskCode
	 * @throws Exception
	 */
	private void queryPrpDkind(HttpServletRequest request, String riskCode) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String fieldValue = paramUtils.getParameter("fieldValue");
		StringBuffer conditions = new StringBuffer("1=1");
		conditions.append(SqlUtils.convertString("KindCode", fieldValue));
		conditions.append(SqlUtils.convertString("RiskCode", riskCode));
		conditions.append(SqlUtils.convertString("ValidStatus", "1"));
		conditions.append(" Order By KindCode");
		List<PrpDkind> result = prpDkindService.findByConditions(conditions.toString());
		for (Iterator<PrpDkind> iter = result.iterator(); iter.hasNext();) {
			PrpDkind PrpDkind = iter.next();
			codeValues.add(PrpDkind.getId().getKindCode() + IConstants.FIELD_SEPARATOR + PrpDkind.getKindCName());
			codeLabels.add(PrpDkind.getId().getKindCode() + "--" + PrpDkind.getKindCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 查詢節點信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryNodeNoByModelNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String otherCondition = paramUtils.getParameter("otherCondition");
		String conditions = " ModelNo=" + otherCondition;
		BLSwfNodeFacade blSwfNodeFacade = new BLSwfNodeFacade();
		Collection<?> swfNodeList = blSwfNodeFacade.findByConditions(conditions);
		SwfNodeDto swfNodeDto = null;
		Iterator<?> it = swfNodeList.iterator();
		while (it.hasNext()) {
			swfNodeDto = (SwfNodeDto)it.next();
			codeValues.add(swfNodeDto.getNodeNo() + IConstants.FIELD_SEPARATOR + swfNodeDto.getNodeName());
			codeLabels.add(swfNodeDto.getNodeNo() + "--" + swfNodeDto.getNodeName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 查詢用戶代碼
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryUserCodeByComCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		String conditions = "1=1" + SqlUtils.convertString("UserCode", fieldValue) + SqlUtils.convertString("ComCode", otherCondition);
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions += uiPowerInterface.addPower((UserDto) request.getSession().getAttribute("user"), "PrpDuser", "UserCode", "ComCode");
		conditions = conditions + " Order By UserCode";
		List<PrpDuser> prpDuserList = prpDuserService.findByConditions(conditions);
		PrpDuser prpDuser = null;
		for (int i = 0; i < prpDuserList.size(); i++) {
			prpDuser = prpDuserList.get(i);
			codeValues.add(prpDuser.getUserCode() + IConstants.FIELD_SEPARATOR + prpDuser.getUserName());
			codeLabels.add(prpDuser.getUserCode() + "--" + prpDuser.getUserName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryFactorCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();

		String conditions = " 1=1 ";
		String otherCondition = paramUtils.getParameter("otherCondition");
		String con2 = otherCondition.substring(otherCondition.indexOf("=") + 1);
		String con1 = otherCondition.substring(0, otherCondition.indexOf("=") + 1);
		otherCondition = con1 + "'" + con2 + "'";
		conditions += "and " + otherCondition;
		BLPrpDrateFactorFacade blPrpDrateFactorFacade = new BLPrpDrateFactorFacade();
		Collection<?> result = blPrpDrateFactorFacade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDrateFactorDto element = (PrpDrateFactorDto) iter.next();
			codeValues.add(element.getFactorCode() + IConstants.FIELD_SEPARATOR + element.getFactorName() + IConstants.FIELD_SEPARATOR + element.getFactorType());
			codeLabels.add(element.getFactorCode() + "--" + element.getFactorName() + "--" + element.getFactorType());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询车型代码
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws Exception
	 */
	private void queryCarModelCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("prpDdangerCarModelCarBandCode=");
		int begin = otherCondition.indexOf("=");
		String prpDdangerCarModelCarBandCode = "";
		if (start > -1) {
			prpDdangerCarModelCarBandCode = otherCondition.substring(begin + 1, otherCondition.length());
		}
		conditions += getCondition(codeMethod, "ModelCode", fieldValue);
		if (!prpDdangerCarModelCarBandCode.equals("")) {
			conditions += SqlUtils.convertString("Series_Id", prpDdangerCarModelCarBandCode);
		}
		conditions += " ORDER BY ModelCode";
		BLPrpDcarModelFacade blPrpDcarModelFacade = new BLPrpDcarModelFacade();
		Collection<?> result = blPrpDcarModelFacade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDcarModelDto element = (PrpDcarModelDto) iter.next();
			codeValues.add(element.getModelCode() + IConstants.FIELD_SEPARATOR + element.getModelName());
			codeLabels.add(element.getModelCode() + "--" + element.getModelName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询币别代码
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws Exception
	 */
	private void queryCurrencyCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = getCondition(codeMethod, "CurrencyCode", fieldValue);
		conditions += " ORDER BY CurrencyCode";
		BLPrpDcurrencyFacade blPrpDcurrencyFacade = new BLPrpDcurrencyFacade();
		Collection<?> result = blPrpDcurrencyFacade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDcurrencyDto element = (PrpDcurrencyDto) iter.next();
			codeValues.add(element.getCurrencyCode() + IConstants.FIELD_SEPARATOR + element.getCurrencyCName());
			codeLabels.add(element.getCurrencyCode() + "--" + element.getCurrencyCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryCustomerCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = getCondition(codeMethod, "CustomerCode", fieldValue);
		// POWER 添加员工查询权限
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions += uiPowerInterface.addPower((UserDto) request.getSession().getAttribute("user"), "PrpDcustomerUnit", "", "ComCode");
		conditions += " AND ValidStatus='1' ORDER BY CustomerCode ";

		BLPrpDcustomerUnitFacade blPrpDcustomerUnitFacade = new BLPrpDcustomerUnitFacade();
		Collection<?> result = blPrpDcustomerUnitFacade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDcustomerUnitDto element = (PrpDcustomerUnitDto) iter.next();
			codeValues.add(element.getCustomerCode() + IConstants.FIELD_SEPARATOR + element.getCustomerCName());
			codeLabels.add(element.getCustomerCode() + "--" + element.getCustomerCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询机构员工代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryComUserCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 处理comCode
		int start = otherCondition.indexOf("comCode=");
		String comCode = "";
		if (start > -1) {
			int end = otherCondition.indexOf(start + ",");
			if (end > -1) {
				comCode = otherCondition.substring(start + "comCode=".length(), end).trim();
			} else {
				comCode = otherCondition.substring(start + "comCode=".length()).trim();
			}
		}

		BLUtiUserGradeFacade facade = new BLUtiUserGradeFacade();
		Collection<?> result = facade.queryByCompany(comCode);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			UtiUserGradeDto element = (UtiUserGradeDto) iter.next();
			codeValues.add(element.getUserCode() + IConstants.FIELD_SEPARATOR + element.getUserName());
			codeLabels.add(element.getUserCode() + "--" + element.getUserName());
		}

		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询机构员工岗位代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryComUserGradeCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 处理comUserCode
		String[] otherConditions = otherCondition.split(",");
		int start = otherConditions[0].indexOf("comCode=");
		String comCode = "";
		if (start > -1) {
			int end = otherConditions[0].indexOf(start + ",");
			if (end > -1) {
				comCode = otherConditions[0].substring(start + "comCode=".length(), end).trim();
			} else {
				comCode = otherConditions[0].substring(start + "comCode=".length()).trim();
			}
		}
		start = otherConditions[1].indexOf("comUserCode=");
		String comUserCode = "";
		if (start > -1) {
			int end = otherConditions[1].indexOf(start + ",");
			if (end > -1) {
				comUserCode = otherConditions[1].substring(start + "comUserCode=".length(), end).trim();
			} else {
				comUserCode = otherConditions[1].substring(start + "comUserCode=".length()).trim();
			}
		}
		String condition = "ComCode='" + comCode + "' and UserCode='" + comUserCode + "'";
		// POWER 添加员工查询权限
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		condition += uiPowerInterface.addPower((UserDto) request.getSession().getAttribute("user"), "UtiUserGrade", "UserCode", "ComCode");
		BLUtiUserGradeFacade facade = new BLUtiUserGradeFacade();
		Collection<?> result = facade.findByConditions(condition);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			UtiUserGradeDto element = (UtiUserGradeDto) iter.next();
			codeValues.add(element.getGradeCode() + IConstants.FIELD_SEPARATOR + element.getGradeName());
			codeLabels.add(element.getGradeCode() + "--" + element.getGradeName());
		}

		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询本机构员工没有的岗位代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryExceptGradeCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String otherCondition = paramUtils.getParameter("otherCondition");

		String[] otherConditions = otherCondition.split("=");
		String comCode = otherConditions[0];
		String userCode = otherConditions[1];
		String condition = "ComCode='" + comCode + "' and UserCode='" + userCode + "'";
		// POWER 添加员工查询权限
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		condition += uiPowerInterface.addPower((UserDto) request.getSession().getAttribute("user"), "UtiUserGrade", "UserCode", "ComCode");
		BLUtiUserGradeFacade facade = new BLUtiUserGradeFacade();
		Collection<?> result = facade.findByConditions(condition);
		Collection<String> haveGrade = new ArrayList<String>();
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			UtiUserGradeDto element = (UtiUserGradeDto) iter.next();
			haveGrade.add(element.getGradeCode());
		}
		String conditions = "";
		BLUtiGradeFacade blUtiGradeFacade = new BLUtiGradeFacade();
		Collection<?> result1 = blUtiGradeFacade.findByConditions(conditions);
		for (Iterator<?> iter = result1.iterator(); iter.hasNext();) {
			UtiGradeDto element = (UtiGradeDto) iter.next();
			boolean haveExist = false;
			if (haveGrade.size() > 0) {
				for (Iterator<String> iter1 = haveGrade.iterator(); iter1.hasNext();) {
					if (((String) (iter1.next())).equals(element.getGradeCode())) {
						haveExist = true;
					}
				}

			}
			if (!haveExist) {
				codeValues.add(element.getGradeCode() + IConstants.FIELD_SEPARATOR + element.getGradeName());
				codeLabels.add(element.getGradeCode() + "--" + element.getGradeName());
			}
		}

		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 根据用户代码查询他所在的机构
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryComCodeByUserCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("userCode=");
		String userCode = "";
		if (start > -1) {
			userCode = otherCondition.substring(start + "userCode=".length()).trim();
		}
		if (!userCode.equals("")) {
			BLUtiUserGradeFacade facade = new BLUtiUserGradeFacade();
			facade.queryByUserCode(userCode, 1, 65535);
			PageRecord record = facade.queryByUserCode(userCode, 1, 65535);
			Collection<?> result = record.getResult();
			for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
				UtiUserGradeDto element = (UtiUserGradeDto) iter.next();
				codeValues.add(element.getComCode() + IConstants.FIELD_SEPARATOR + element.getComName());
				codeLabels.add(element.getComCode() + "--" + element.getComName());
			}

		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询当前机构员工的岗位级别以下的岗位
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryGradeCodeByLevel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrpDuserDto user = (PrpDuserDto) (request.getSession().getAttribute("user"));
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		PrpDcompany prpDcompany = new PrpDcompany();
		String strComLevel = "";

		// 判断是否是超级用户
		PowerIsSuperUserCommand powerIsSuperUserCommand = new PowerIsSuperUserCommand(user.getUserCode(), user.getLoginComCode());
		boolean isSuperUser = ((Boolean) powerIsSuperUserCommand.execute()).booleanValue();
		String conditions = getCondition(codeMethod, "GradeCode", fieldValue);
		// 根据是否是超级用户，如果是则显示所有的级别，否则显示比所拥有的最低岗位级别还低的岗位级别
		if (isSuperUser) {
			conditions = conditions + " ORDER BY GradeCode ";
		} else {
			// modify by xuning for 按照登陆机构的级别对岗位的级别进行查询
			prpDcompany = prpDcompanyService.findByPrimaryKey(user.getLoginComCode());
			strComLevel = prpDcompany.getComLevel();
			// 下面根据登录员工的员工代码和机构代码取出的岗位代码
			// 並根据岗位代码表中其岗位代码级别最低的岗位代码来查询比此级别低的岗位代码
			conditions = conditions + " AND GradeLevel >= " + strComLevel + " ORDER BY GradeCode ";
		}
		BLUtiGradeFacade facade = new BLUtiGradeFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			UtiGradeDto utiGroupDto = (UtiGradeDto) iter.next();
			codeValues.add(utiGroupDto.getGradeCode() + IConstants.FIELD_SEPARATOR + utiGroupDto.getGradeName());
			codeLabels.add(utiGroupDto.getGradeCode() + "--" + utiGroupDto.getGradeName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询险类
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryRiskClass(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String conditions = "1=1";
		BLPrpDclassFacade facade = new BLPrpDclassFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDclassDto element = (PrpDclassDto) iter.next();
			codeValues.add(element.getClassCode() + IConstants.FIELD_SEPARATOR + element.getClassName());
			codeLabels.add(element.getClassCode() + "--" + element.getClassName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 根据险类查询险种
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryRiskCodeByRiskClass(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("riskClassCode=");
		String riskClassCodes = "";
		if (start > -1) {
			riskClassCodes = otherCondition.substring(start + "riskClassCode=".length()).trim();
		}
		String[] riskClassCode = riskClassCodes.split(",");
		String conditions = "ClassCode IN (";
		int i = 0;
		for (; i < riskClassCode.length - 1; i++) {
			conditions = conditions + "'" + riskClassCode[i] + "',";
		}
		conditions = conditions + "'" + riskClassCode[i] + "')";

		BLPrpDriskFacade facade = new BLPrpDriskFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDriskDto prpDriskDto = (PrpDriskDto) iter.next();
			codeValues.add(prpDriskDto.getRiskCode() + IConstants.FIELD_SEPARATOR + prpDriskDto.getRiskCName());
			codeLabels.add(prpDriskDto.getRiskCode() + "--" + prpDriskDto.getRiskCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询港口代码和名字
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryPortCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions += getCondition(codeMethod, "PortCode", fieldValue);
		conditions += " ORDER BY PortCode";
		BLPrpDportFacade blPrpDportFacade = new BLPrpDportFacade();
		Collection<?> result = blPrpDportFacade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDportDto element = (PrpDportDto) iter.next();
			codeValues.add(element.getPortCode() + IConstants.FIELD_SEPARATOR + element.getPortEName() + IConstants.FIELD_SEPARATOR + element.getCountryCode() + IConstants.FIELD_SEPARATOR + element.getCountryCName() + IConstants.FIELD_SEPARATOR
					+ element.getCountryEName());
			codeLabels.add(element.getPortCode() + "--" + element.getPortEName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询飞机代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryPlaneCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "planeCode", fieldValue);
		conditions += " ORDER BY PlaneCode";
		BLPrpDplaneFacade blPrpDplaneFacade = new BLPrpDplaneFacade();
		Collection<?> result = blPrpDplaneFacade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDplaneDto prpDplaneDto = (PrpDplaneDto) iter.next();
			codeValues.add(prpDplaneDto.getPlaneCode() + IConstants.FIELD_SEPARATOR + prpDplaneDto.getFleetName());
			codeLabels.add(prpDplaneDto.getPlaneCode() + "--" + prpDplaneDto.getFleetName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询通用代码类型
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryCodeType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String conditions = "1=1 ";
		conditions = getCondition(codeMethod, "codeType", fieldValue);
		conditions += " ORDER BY codeType";
		BLPrpDtypeFacade blPrpDtypeFacade = new BLPrpDtypeFacade();
		Collection<?> result = blPrpDtypeFacade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDtypeDto prpDtypeDto = (PrpDtypeDto) iter.next();
			codeValues.add(prpDtypeDto.getCodeType() + IConstants.FIELD_SEPARATOR + prpDtypeDto.getCodeTypeDesc());
			codeLabels.add(prpDtypeDto.getCodeType() + "--" + prpDtypeDto.getCodeTypeDesc());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询船舶代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryShipCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = getCondition(codeMethod, "ShipCode", fieldValue);
		conditions += " ORDER BY ShipCode";
		BLPrpDshipFacade blPrpDshipFacade = new BLPrpDshipFacade();
		Collection<?> result = blPrpDshipFacade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDshipDto element = (PrpDshipDto) iter.next();
			codeValues.add(element.getShipCode() + IConstants.FIELD_SEPARATOR + element.getShipCName());
			codeLabels.add(element.getShipCode() + "--" + element.getShipCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询代赔地区公司--承保地区代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryAgentComCentre(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		String conditions = getCondition(codeMethod, "CentreCode", fieldValue);
		conditions += " ORDER BY CentreCode";
		BLPrpDagentComFacade blPrpDagentComFacade = new BLPrpDagentComFacade();
		Collection<?> result = blPrpDagentComFacade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDagentComDto element = (PrpDagentComDto) iter.next();
			codeValues.add(element.getCentreCode() + IConstants.FIELD_SEPARATOR + element.getCentreName());
			codeLabels.add(element.getCentreCode() + "--" + element.getCentreName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询代赔地区公司--承保公司代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryAgentComUnitCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = getCondition(codeMethod, "UnitCode", fieldValue);
		conditions += "  ORDER BY UnitCode";
		BLPrpDagentComFacade blPrpDagentComFacade = new BLPrpDagentComFacade();
		Collection<?> result = blPrpDagentComFacade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDagentComDto element = (PrpDagentComDto) iter.next();
			codeValues.add(element.getUnitCode() + IConstants.FIELD_SEPARATOR + element.getUnitName());
			codeLabels.add(element.getUnitCode() + "--" + element.getUnitName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询条款代码和名字queryIdentifierCode
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryClauseCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = getCondition(codeMethod, "ClauseCode", fieldValue);
		conditions += " Order By ClauseCode";
		BLPrpDclauseFacade blPrpDclauseFacade = new BLPrpDclauseFacade();
		Collection<?> result = blPrpDclauseFacade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDclauseDto element = (PrpDclauseDto) iter.next();
			codeValues.add(element.getClauseCode() + IConstants.FIELD_SEPARATOR + element.getClauseName());
			codeLabels.add(element.getClauseCode() + "--" + element.getClauseName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询检验人代码和名字
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryIdentifierCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " 1=1 ";
		conditions = getCondition(codeMethod, "IdentifierCode", fieldValue);
		conditions = conditions + " ORDER BY IdentifierCode";
		BLPrpDidentifierFacade facade = new BLPrpDidentifierFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDidentifierDto element = (PrpDidentifierDto) iter.next();
			codeValues.add(element.getIdentifierCode() + IConstants.FIELD_SEPARATOR + element.getIdentifierName());
			codeLabels.add(element.getIdentifierCode() + "--" + element.getIdentifierName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询接受人代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryReinsCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String conditions = "1=1 ";
		conditions = getCondition(codeMethod, "ReinsCode", fieldValue);
		conditions = conditions + " ORDER BY ReinsCode";
		BLPrpDreinsFacade facade = new BLPrpDreinsFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDreinsDto prpDreinsDto = (PrpDreinsDto) iter.next();
			codeValues.add(prpDreinsDto.getReinsCode() + IConstants.FIELD_SEPARATOR + prpDreinsDto.getLongName());
			codeLabels.add(prpDreinsDto.getReinsCode() + "--" + prpDreinsDto.getLongName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询再保财务科目代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryAccItemCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String conditions = "1=1 ";
		conditions = getCondition(codeMethod, "AccItemCode", fieldValue);
		BLPrpDaccItemFacade facade = new BLPrpDaccItemFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDaccItemDto prpDaccItemDto = (PrpDaccItemDto) iter.next();
			codeValues.add(prpDaccItemDto.getAccItemCode());
			codeLabels.add(prpDaccItemDto.getAccItemCode());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询再保财务专项代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryPrpDarticleItemCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String conditions = "1=1 ";
		BLPrpDarticleFacade facade = new BLPrpDarticleFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDarticleDto prpDarticleDto = (PrpDarticleDto) iter.next();
			codeValues.add(prpDarticleDto.getItemCode());
			codeLabels.add(prpDarticleDto.getItemCode());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询再保专项代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryArtItemCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String conditions = "1=1 ";
		BLPrpDartItemFacade facade = new BLPrpDartItemFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDartItemDto prpDartItemDto = (PrpDartItemDto) iter.next();
			codeValues.add(prpDartItemDto.getAccItemCode());
			codeLabels.add(prpDartItemDto.getAccItemCode());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询产品属性代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryAttrCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String conditions = getCondition(codeMethod, "AttrCode", fieldValue);
		conditions += " AND ValidStatus='1' ORDER BY AttrCode";
		BLUtiProductAttrFacade facade = new BLUtiProductAttrFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			UtiProductAttrDto utiProductAttrDto = (UtiProductAttrDto) iter.next();
			codeValues.add(utiProductAttrDto.getAttrCode() + IConstants.FIELD_SEPARATOR + utiProductAttrDto.getAttrName());

			codeLabels.add(utiProductAttrDto.getAttrCode() + "--" + utiProductAttrDto.getAttrName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询险种，要带出多个属性
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryRiskCodeForCopy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("=");
		String classCode = "";
		if (start > -1) {
			classCode = otherCondition.substring(start + "=".length()).trim();
		}
		String conditions = " ";
		conditions = getCondition(codeMethod, "RiskCode", fieldValue);
		conditions = conditions + "And classCode='" + classCode + "' order by RiskCode ";
		BLPrpDriskFacade facade = new BLPrpDriskFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDriskDto prpDriskDto = (PrpDriskDto) iter.next();

			codeValues.add(prpDriskDto.getRiskCode() + IConstants.FIELD_SEPARATOR + prpDriskDto.getRiskCName() + IConstants.FIELD_SEPARATOR + prpDriskDto.getClassName() + IConstants.FIELD_SEPARATOR + prpDriskDto.getRiskFlag10Value()
					+ IConstants.FIELD_SEPARATOR + prpDriskDto.getRiskFlag2Value() + IConstants.FIELD_SEPARATOR + prpDriskDto.getValidStatusValue());
			codeLabels.add(prpDriskDto.getRiskCode() + "--" + prpDriskDto.getRiskCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 根据险种查询险别
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryKindCodeByRiskCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("=");
		String riskCodes = "";
		if (start > -1) {
			riskCodes = otherCondition.substring(start + "=".length()).trim();
		}
		String[] riskClassCode = riskCodes.split(",");
		String conditions = getCondition(codeMethod, "KindCode", fieldValue);
		conditions += " AND RiskCode IN (";
		int i = 0;
		for (; i < riskClassCode.length - 1; i++) {
			conditions += "'" + riskClassCode[i] + "',";
		}
		conditions += "'" + riskClassCode[i] + "')";
		conditions += " ORDER BY RiskCode,KindCode";
		List<PrpDkind> collection = prpDkindService.findByConditions(conditions);
		PrpDkind prpDkind = null;
		for (Iterator<PrpDkind> iter = collection.iterator(); iter.hasNext();) {
			prpDkind = iter.next();
			codeValues.add(prpDkind.getId().getKindCode() + IConstants.FIELD_SEPARATOR + prpDkind.getKindCName());
			codeLabels.add(prpDkind.getId().getKindCode() + "--" + prpDkind.getKindCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 按险种查询标的
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryItemCodeByRiskCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("=");
		String riskCodes = "";
		if (start > -1) {
			riskCodes = otherCondition.substring(start + "=".length()).trim();
		}
		String[] riskClassCode = riskCodes.split(",");
		String conditions = getCondition(codeMethod, "ItemCode", fieldValue);
		conditions += " AND RiskCode IN (";
		int i = 0;
		for (; i < riskClassCode.length - 1; i++) {
			conditions += "'" + riskClassCode[i] + "',";
		}
		conditions += "'" + riskClassCode[i] + "')";
		conditions += " ORDER BY RiskCode,ItemCode";

		BLPrpDitemFacade prpDitemCommand = new BLPrpDitemFacade();
		Collection<?> collection = prpDitemCommand.findByConditions(conditions);
		PrpDitemDto prpDitemDto = null;
		for (Iterator<?> iter = collection.iterator(); iter.hasNext();) {
			prpDitemDto = (PrpDitemDto) iter.next();
			codeValues.add(prpDitemDto.getItemCode() + IConstants.FIELD_SEPARATOR + prpDitemDto.getItemCName());
			codeLabels.add(prpDitemDto.getItemCode() + "--" + prpDitemDto.getItemCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 *查詢車輛種類
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryCarKindCodeByRiskCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("=");
		String riskCodes = "";
		if (start > -1) {
			riskCodes = otherCondition.substring(start + "=".length()).trim();
		}
		String[] riskClassCode = riskCodes.split(",");
		String conditions = getCondition(codeMethod, "carKindCode", fieldValue);
		conditions += " AND RiskCode IN (";
		int i = 0;
		for (; i < riskClassCode.length - 1; i++) {
			conditions += "'" + riskClassCode[i] + "',";
		}
		conditions += "'" + riskClassCode[i] + "')";
		conditions += " ORDER BY RiskCode,CarKindCode";

		BLPrpDdeprecateRateFacade prpDdeprecateRateCommand = new BLPrpDdeprecateRateFacade();
		Collection<?> collection = prpDdeprecateRateCommand.findByConditions(conditions);
		PrpDdeprecateRateDto prpDdeprecateRateDto = null;
		for (Iterator<?> iter = collection.iterator(); iter.hasNext();) {
			prpDdeprecateRateDto = (PrpDdeprecateRateDto) iter.next();
			codeValues.add(prpDdeprecateRateDto.getCarKindCode() + IConstants.FIELD_SEPARATOR + prpDdeprecateRateDto.getCarKindName());
			codeLabels.add(prpDdeprecateRateDto.getCarKindCode() + "--" + prpDdeprecateRateDto.getCarKindName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryRationTypeByRiskCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("=");
		String riskCodes = "";
		if (start > -1) {
			riskCodes = otherCondition.substring(start + "=".length()).trim();
		}
		String[] riskClassCode = riskCodes.split(",");
		String conditions = getCondition(codeMethod, "RationType", fieldValue);
		conditions += " AND RiskCode IN (";
		int i = 0;
		for (; i < riskClassCode.length - 1; i++) {
			conditions += "'" + riskClassCode[i] + "',";
		}
		conditions += "'" + riskClassCode[i] + "')";
		conditions += " ORDER BY RationType";

		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryItemCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "ItemCode", fieldValue);
		conditions = conditions + " order by ItemCode ";
		BLPrpDitemFacade facade = new BLPrpDitemFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDitemDto prpDitemDto = (PrpDitemDto) iter.next();
			codeValues.add(prpDitemDto.getItemCode() + IConstants.FIELD_SEPARATOR + prpDitemDto.getItemCName());
			codeLabels.add(prpDitemDto.getItemCode() + "--" + prpDitemDto.getItemCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryLimitCodeByRiskCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("=");
		String riskCodes = "";
		if (start > -1) {
			riskCodes = otherCondition.substring(start + "=".length()).trim();
		}
		String[] riskClassCode = riskCodes.split(",");
		String conditions = getCondition(codeMethod, "LimitCode", fieldValue);

		conditions += " AND RiskCode IN (";
		int i = 0;
		for (; i < riskClassCode.length - 1; i++) {
			conditions += "'" + riskClassCode[i] + "',";
		}
		conditions += "'" + riskClassCode[i] + "')";
		conditions += " ORDER BY LimitCode";

		BLPrpDlimitFacade prpDlimitCommand = new BLPrpDlimitFacade();
		Collection<?> collection = prpDlimitCommand.findByConditions(conditions);
		PrpDlimitDto prpDlimitDto = null;
		for (Iterator<?> iter = collection.iterator(); iter.hasNext();) {
			prpDlimitDto = (PrpDlimitDto) iter.next();
			codeValues.add(prpDlimitDto.getLimitCode() + IConstants.FIELD_SEPARATOR + prpDlimitDto.getLimitCName());
			codeLabels.add(prpDlimitDto.getLimitCode() + "--" + prpDlimitDto.getLimitCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryRationRationTypeByRiskCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("=");
		String riskCodes = "";
		if (start > -1) {
			riskCodes = otherCondition.substring(start + "=".length()).trim();
		}
		String[] riskClassCode = riskCodes.split(",");
		String conditions = getCondition(codeMethod, "RationType", fieldValue);
		conditions += " AND RiskCode IN (";
		int i = 0;
		for (; i < riskClassCode.length - 1; i++) {
			conditions += "'" + riskClassCode[i] + "',";
		}
		conditions += "'" + riskClassCode[i] + "')";
		conditions += " ORDER BY RiskCode,RationType";

		BLPrpDrationFacade prpDrationommand = new BLPrpDrationFacade();
		Collection<?> collection = prpDrationommand.findByConditions(conditions);
		PrpDrationDto prpDrationDto = null;
		for (Iterator<?> iter = collection.iterator(); iter.hasNext();) {
			prpDrationDto = (PrpDrationDto) iter.next();
			codeValues.add(prpDrationDto.getRationType() + IConstants.FIELD_SEPARATOR + prpDrationDto.getRationName());
			codeLabels.add(prpDrationDto.getRationType() + "--" + prpDrationDto.getRationName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryEndorTypeByRiskCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("=");
		String riskCodes = "";
		if (start > -1) {
			riskCodes = otherCondition.substring(start + "=".length()).trim();
		}
		String[] riskClassCode = riskCodes.split(",");
		String conditions = getCondition(codeMethod, "EndorType", fieldValue);
		conditions += " AND RiskCode IN (";
		int i = 0;
		for (; i < riskClassCode.length - 1; i++) {
			conditions += "'" + riskClassCode[i] + "',";
		}
		conditions += "'" + riskClassCode[i] + "')";
		conditions += " ORDER BY RiskCode,EndorType";

		BLPrpXPformFacade prpXPformommand = new BLPrpXPformFacade();
		Collection<?> collection = prpXPformommand.findByConditions(conditions);
		PrpXPformDto prpXPformDto = null;
		for (Iterator<?> iter = collection.iterator(); iter.hasNext();) {
			prpXPformDto = (PrpXPformDto) iter.next();
			codeValues.add(prpXPformDto.getEndorType() + IConstants.FIELD_SEPARATOR + prpXPformDto.getEndorTypeName());
			codeLabels.add(prpXPformDto.getEndorType() + "--" + prpXPformDto.getEndorTypeName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryLiabCodeByRiskCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("=");
		String riskCodes = "";
		if (start > -1) {
			riskCodes = otherCondition.substring(start + "=".length()).trim();
		}
		String[] riskClassCode = riskCodes.split(",");
		String conditions = getCondition(codeMethod, "LiabCode", fieldValue);
		conditions += " AND RiskCode IN (";
		int i = 0;
		for (; i < riskClassCode.length - 1; i++) {
			conditions += "'" + riskClassCode[i] + "',";
		}
		conditions += "'" + riskClassCode[i] + "')";
		conditions += " ORDER BY RiskCode,LiabCode";

		BLPrpDliabFacade prpDliabommand = new BLPrpDliabFacade();
		Collection<?> collection = prpDliabommand.findByConditions(conditions);
		PrpDliabDto prpDliabDto = null;
		for (Iterator<?> iter = collection.iterator(); iter.hasNext();) {
			prpDliabDto = (PrpDliabDto) iter.next();
			codeValues.add(prpDliabDto.getLiabCode() + IConstants.FIELD_SEPARATOR + prpDliabDto.getLiabCName());
			codeLabels.add(prpDliabDto.getLiabCode() + "--" + prpDliabDto.getLiabCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryLiabCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "KindCode", fieldValue);
		conditions = conditions + " order by KindCode ";
		List<PrpDkind> result = prpDkindService.findByConditions(conditions);
		for (Iterator<PrpDkind> iter = result.iterator(); iter.hasNext();) {
			PrpDkind PrpDkind = iter.next();
			codeValues.add(PrpDkind.getId().getKindCode() + IConstants.FIELD_SEPARATOR + PrpDkind.getKindCName());
			codeLabels.add(PrpDkind.getId().getKindCode() + "--" + PrpDkind.getKindCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryCodeCodeByCodeType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = getCondition(codeMethod, "codeCode", fieldValue);
		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("=");
		String codeType = "";
		if (start > -1) {
			codeType = otherCondition.substring(start + "=".length()).trim();
		}
		conditions += "  AND codeType ='" + codeType + "'";
		conditions += " ORDER BY CodeCode";
		List<PrpDcode> collection = prpDcodeService.findByConditions(conditions);
		PrpDcode prpDcode = null;
		for (Iterator<PrpDcode> iter = collection.iterator(); iter.hasNext();) {
			prpDcode = (PrpDcode) iter.next();
			codeValues.add(prpDcode.getId().getCodeCode() + IConstants.FIELD_SEPARATOR + prpDcode.getCodeCName());
			codeLabels.add(prpDcode.getId().getCodeCode() + "--" + prpDcode.getCodeCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询配置项代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryConfigCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "configcode", fieldValue);
		conditions += " ORDER BY configcode";
		BLUtiConfigFacade facade = new BLUtiConfigFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			UtiConfigDto element = (UtiConfigDto) iter.next();
			codeValues.add(element.getConfigCode() + IConstants.FIELD_SEPARATOR + element.getConfigCName());
			codeLabels.add(element.getConfigCode() + "--" + element.getConfigCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());

	}

	/**
	 * 查询模版名称代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryModelNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);

		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();

		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		String conditions = " ";

		conditions = getCondition(codeMethod, "modelNo", fieldValue);
		conditions += "ORDER BY ModelNo";
		BLSwfModelMainFacade facade = new BLSwfModelMainFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			SwfModelMainDto swfModelMainDto = (SwfModelMainDto) iter.next();
			codeValues.add(swfModelMainDto.getModelNo() + IConstants.FIELD_SEPARATOR + swfModelMainDto.getModelName());

			codeLabels.add(swfModelMainDto.getModelNo() + "--" + swfModelMainDto.getModelName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryCarGroupCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "GroupCode", fieldValue);
		conditions = conditions + " order by GroupCode ";
		BLPrpDcarGroupFacade facade = new BLPrpDcarGroupFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDcarGroupDto PrpDcarGroupDto = (PrpDcarGroupDto) iter.next();
			codeValues.add(PrpDcarGroupDto.getGroupCode() + IConstants.FIELD_SEPARATOR + PrpDcarGroupDto.getGroupName());
			codeLabels.add(PrpDcarGroupDto.getGroupCode() + "--" + PrpDcarGroupDto.getGroupName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryFamilyID(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "FamilyID", fieldValue);
		conditions = conditions + " order by FamilyID ";
		BLPrpDcarFamilyFacade facade = new BLPrpDcarFamilyFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDcarFamilyDto prpDcarFamilyDto = (PrpDcarFamilyDto) iter.next();
			codeValues.add(prpDcarFamilyDto.getFamilyID() + IConstants.FIELD_SEPARATOR + prpDcarFamilyDto.getFamilyName());
			codeLabels.add(prpDcarFamilyDto.getFamilyID() + "--" + prpDcarFamilyDto.getFamilyName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryTradeMarkID(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "tradeMarkID", fieldValue);
		conditions = conditions + " AND tradeMarkID IS NOT NULL order by tradeMarkID ";
		BLPrpDcarFamilyFacade facade = new BLPrpDcarFamilyFacade();
		Collection<?> result = facade.findByConditionsDistinct(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDcarFamilyDto prpDcarFamilyDto = (PrpDcarFamilyDto) iter.next();
			codeValues.add(prpDcarFamilyDto.getTradeMarkID() + IConstants.FIELD_SEPARATOR + prpDcarFamilyDto.getTradeMarkName());
			codeLabels.add(prpDcarFamilyDto.getTradeMarkID() + "--" + prpDcarFamilyDto.getTradeMarkName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询上级公司代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryUpperReinsCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

		queryComCode(request, response);
	}

	/**
	 * 查询报表代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryReportCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "reportCode", fieldValue);
		BLPrpDreportFacade facade = new BLPrpDreportFacade();
		Collection<?> result = facade.findByConditionsDistinct(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDreportDto prpDreportDto = (PrpDreportDto) iter.next();
			codeValues.add(prpDreportDto.getReportCode() + IConstants.FIELD_SEPARATOR + prpDreportDto.getReportName());
			codeLabels.add(prpDreportDto.getReportCode() + "--" + prpDreportDto.getReportName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询条款类别
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryClauseType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "ClauseType", fieldValue);
		conditions += " Order By ClauseType";
		String clauseType = "";
		BLPrpDclauseKindFacade facade = new BLPrpDclauseKindFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {

			PrpDclauseKindDto prpDclauseKindDto = (PrpDclauseKindDto) iter.next();
			if (!(prpDclauseKindDto.getClauseType().equals(clauseType))) {
				codeValues.add(prpDclauseKindDto.getClauseType());
				codeLabels.add(prpDclauseKindDto.getClauseType());
				clauseType = prpDclauseKindDto.getClauseType();
			}
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询关联险别代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryRelateKindCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "RelateKindCode", fieldValue);
		conditions += " Order By RelateKindCode";
		String relateKindCode = "";
		BLPrpDkindRelateFacade facade = new BLPrpDkindRelateFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDkindRelateDto prpDkindRelateDto = (PrpDkindRelateDto) iter.next();
			if (!(prpDkindRelateDto.getRelateKindCode().equals(relateKindCode))) {
				codeValues.add(prpDkindRelateDto.getRelateKindCode());
				codeLabels.add(prpDkindRelateDto.getRelateKindCode());
				relateKindCode = prpDkindRelateDto.getRelateKindCode();
			}
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询险别代码，去掉重复的
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryDistinctKindCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "KindCode", fieldValue);
		conditions = conditions + " order by KindCode ";
		String kindCode = "";
		List<PrpDkind> result = prpDkindService.findByConditions(conditions);
		for (Iterator<PrpDkind> iter = result.iterator(); iter.hasNext();) {
			PrpDkind prpDkind = iter.next();
			if (!(prpDkind.getId().getKindCode().equals(kindCode))) {
				codeValues.add(prpDkind.getId().getKindCode());
				codeLabels.add(prpDkind.getId().getKindCode());
				kindCode = prpDkind.getId().getKindCode();
			}
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 根据险种、险别查询关联险别代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryRelateKindCodeByRiskCodeKindCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 处理riskCode
		String[] otherConditions = otherCondition.split(",");
		int start = otherConditions[0].indexOf("riskCode=");
		String riskCode = "";
		if (start > -1) {
			int end = otherConditions[0].indexOf(start + ",");
			if (end > -1) {
				riskCode = otherConditions[0].substring(start + "riskCode=".length(), end).trim();
			} else {
				riskCode = otherConditions[0].substring(start + "riskCode=".length()).trim();
			}
		}
		start = otherConditions[1].indexOf("kindCode=");
		String kindCode = "";
		if (start > -1) {
			int end = otherConditions[1].indexOf(start + ",");
			if (end > -1) {
				kindCode = otherConditions[1].substring(start + "kindCode=".length(), end).trim();
			} else {
				kindCode = otherConditions[1].substring(start + "kindCode=".length()).trim();
			}
		}
		String conditions = getCondition(codeMethod, "RelateKindCode", fieldValue);

		conditions += " And riskCode='" + riskCode + "' And KindCode='" + kindCode + "'";

		BLPrpDkindRelateFacade facade = new BLPrpDkindRelateFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDkindRelateDto element = (PrpDkindRelateDto) iter.next();
			codeValues.add(element.getRelateKindCode() + IConstants.FIELD_SEPARATOR + element.getRelateKindName());
			codeLabels.add(element.getRelateKindCode() + "--" + element.getRelateKindName());
		}

		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询统计类型
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryBelongType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "BelongType", fieldValue);
		conditions += " Order By BelongType";
		String belongType = "";
		BLPrpDstatiTypeFacade facade = new BLPrpDstatiTypeFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDstatiTypeDto prpDstatiTypeDto = (PrpDstatiTypeDto) iter.next();
			if (!(prpDstatiTypeDto.getBelongType().equals(belongType))) {
				codeValues.add(prpDstatiTypeDto.getBelongType());
				codeLabels.add(prpDstatiTypeDto.getBelongType());
				belongType = prpDstatiTypeDto.getBelongType();
			}
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询赔付项目代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryPayItemCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "payItemCode", fieldValue);
		conditions += " Order By PayItemCode";
		String payItemCode = "";
		BLPrpDpersonPayFacade facade = new BLPrpDpersonPayFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDpersonPayDto prpDpersonPayDto = (PrpDpersonPayDto) iter.next();
			if (!(prpDpersonPayDto.getPayItemCode().equals(payItemCode))) {
				codeValues.add(prpDpersonPayDto.getPayItemCode());
				codeLabels.add(prpDpersonPayDto.getPayItemCode());
				payItemCode = prpDpersonPayDto.getPayItemCode();
			}
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 根据险种查询免赔条件代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryDeductCondCodeByRiskCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("=");
		String riskCodes = "";
		if (start > -1) {
			riskCodes = otherCondition.substring(start + "=".length()).trim();
		}
		String[] riskClassCode = riskCodes.split(",");
		String conditions = getCondition(codeMethod, "KindCode", fieldValue);
		conditions += " AND RiskCode IN (";
		int i = 0;
		for (; i < riskClassCode.length - 1; i++) {
			conditions += "'" + riskClassCode[i] + "',";
		}
		conditions += "'" + riskClassCode[i] + "')";
		conditions += " ORDER BY RiskCode,DeductCondCode";
		BLPrpDdeductCondFacade prpDdeductCondCommand = new BLPrpDdeductCondFacade();
		Collection<?> collection = prpDdeductCondCommand.findByConditions(conditions);
		PrpDdeductCondDto prpDdeductCondDto = null;
		for (Iterator<?> iter = collection.iterator(); iter.hasNext();) {
			prpDdeductCondDto = (PrpDdeductCondDto) iter.next();
			codeValues.add(prpDdeductCondDto.getDeductCondCode() + IConstants.FIELD_SEPARATOR + prpDdeductCondDto.getDeductCondName());
			codeLabels.add(prpDdeductCondDto.getDeductCondCode() + "--" + prpDdeductCondDto.getDeductCondName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询伤残烧伤等级代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryInjuryGrade(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "injuryGrade", fieldValue);
		conditions += " Order By InjuryGrade";
		BLPrpDinjuryGradeFacade facade = new BLPrpDinjuryGradeFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDinjuryGradeDto prpDinjuryGradeDto = (PrpDinjuryGradeDto) iter.next();
			codeValues.add(prpDinjuryGradeDto.getInjuryGrade() + IConstants.FIELD_SEPARATOR + prpDinjuryGradeDto.getInjuryGradeDesc());
			codeLabels.add(prpDinjuryGradeDto.getInjuryGrade() + "--" + prpDinjuryGradeDto.getInjuryGradeDesc());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询风险类别代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryRiskKindCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "riskKindCode", fieldValue);
		conditions += " Order By RiskKindCode";
		BLPrpDriskKindFacade facade = new BLPrpDriskKindFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDriskKindDto prpDriskKindDto = (PrpDriskKindDto) iter.next();
			codeValues.add(prpDriskKindDto.getRiskKindCode() + IConstants.FIELD_SEPARATOR + prpDriskKindDto.getCodeCName());
			codeLabels.add(prpDriskKindDto.getRiskKindCode() + "--" + prpDriskKindDto.getCodeCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询费率因子代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryFactorCodeDistinct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "factorCode", fieldValue);
		conditions += " Order By FactorCode";
		BLPrpDrateFactorFacade facade = new BLPrpDrateFactorFacade();
		Collection<?> result = facade.findDistinctByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {

			PrpDrateFactorDto prpDrateFactorDto = (PrpDrateFactorDto) iter.next();
			codeValues.add(prpDrateFactorDto.getFactorCode());
			codeLabels.add(prpDrateFactorDto.getFactorCode());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询系数分组
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryRateTypeValue(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "rateTypeValue", fieldValue);
		conditions += " Order By RateTypeValue";
		BLPrpDcarModelGroupFacade facade = new BLPrpDcarModelGroupFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDcarModelGroupDto prpDcarModelGroupDto = (PrpDcarModelGroupDto) iter.next();
			codeValues.add(prpDcarModelGroupDto.getRateTypeValue());
			codeLabels.add(prpDcarModelGroupDto.getRateTypeValue());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询厂牌型号分组号
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryCarTypeGroupNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "carTypeGroupNo", fieldValue);
		conditions += " Order By CarTypeGroupNo";
		BLPrpDcarModelGroupFacade facade = new BLPrpDcarModelGroupFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDcarModelGroupDto prpDcarModelGroupDto = (PrpDcarModelGroupDto) iter.next();
			codeValues.add(prpDcarModelGroupDto.getCarTypeGroupNo() + IConstants.FIELD_SEPARATOR + prpDcarModelGroupDto.getCarTypeGroupName());
			codeLabels.add(prpDcarModelGroupDto.getCarTypeGroupNo() + "--" + prpDcarModelGroupDto.getCarTypeGroupName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询公告ID和公告标题
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryUtiBulletinID(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " 1=1 ";
		conditions = getCondition(codeMethod, "BulletinID", fieldValue);
		conditions = conditions + " ORDER BY BulletinID";
		BLUtiBulletinFacade facade = new BLUtiBulletinFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			UtiBulletinDto element = (UtiBulletinDto) iter.next();
			codeValues.add(element.getBulletinID() + IConstants.FIELD_SEPARATOR + element.getTitle());
			codeLabels.add(element.getBulletinID() + "--" + element.getTitle());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询唯一条款类别
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryClauseTypeByRiskCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("=");
		String riskCodes = "";
		if (start > -1) {
			riskCodes = otherCondition.substring(start + "=".length()).trim();
		}
		String[] riskClassCode = riskCodes.split(",");
		String conditions = getCondition(codeMethod, "ClauseType", fieldValue);
		conditions += " AND RiskCode IN (";
		int i = 0;
		for (; i < riskClassCode.length - 1; i++) {
			conditions += "'" + riskClassCode[i] + "',";
		}
		conditions += "'" + riskClassCode[i] + "')";
		conditions += " ORDER BY RiskCode,ClauseType";
		BLPrpDclauseKindFacade prpDclauseTypeCommand = new BLPrpDclauseKindFacade();
		Collection<?> collection = prpDclauseTypeCommand.findByConditions(conditions);
		PrpDclauseKindDto prpDclauseKindDto = null;
		for (Iterator<?> iter = collection.iterator(); iter.hasNext();) {
			prpDclauseKindDto = (PrpDclauseKindDto) iter.next();
			codeValues.add(prpDclauseKindDto.getClauseType() + IConstants.FIELD_SEPARATOR);
			codeLabels.add(prpDclauseKindDto.getClauseType());
		}

		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询唯一统计类型
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryBelongTypeByRiskCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("=");
		String riskCodes = "";
		if (start > -1) {
			riskCodes = otherCondition.substring(start + "=".length()).trim();
		}
		String[] riskClassCode = riskCodes.split(",");
		String conditions = getCondition(codeMethod, "BelongType", fieldValue);
		conditions += " AND RiskCode IN (";
		int i = 0;
		for (; i < riskClassCode.length - 1; i++) {
			conditions += "'" + riskClassCode[i] + "',";
		}
		conditions += "'" + riskClassCode[i] + "')";
		conditions += " ORDER BY RiskCode,BelongType";
		BLPrpDstatiTypeFacade prpDstatiTypeCommand = new BLPrpDstatiTypeFacade();
		Collection<?> collection = prpDstatiTypeCommand.findByConditions(conditions);
		PrpDstatiTypeDto prpDstatiTypeDto = null;
		for (Iterator<?> iter = collection.iterator(); iter.hasNext();) {
			prpDstatiTypeDto = (PrpDstatiTypeDto) iter.next();
			codeValues.add(prpDstatiTypeDto.getBelongType() + IConstants.FIELD_SEPARATOR);
			codeLabels.add(prpDstatiTypeDto.getBelongType());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryCodeCodeDistinct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " 1=1 ";
		conditions = getCondition(codeMethod, "CodeCode", fieldValue);
		conditions = conditions + " ORDER BY CodeCode";
		List<PrpDcode> result = prpDcodeService.findByConditions(conditions);
		PrpDcode prpDcode = null;
		for (Iterator<PrpDcode> iter = result.iterator(); iter.hasNext();) {
			prpDcode = iter.next();
			codeValues.add(prpDcode.getId().getCodeCode());
			codeLabels.add(prpDcode.getId().getCodeCode());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 根据险种查询关联险别代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryRelateKindCodeByRiskCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("=");
		String riskCodes = "";
		if (start > -1) {
			riskCodes = otherCondition.substring(start + "=".length()).trim();
		}
		String[] riskClassCode = riskCodes.split(",");
		String conditions = getCondition(codeMethod, "RelateKindCode", fieldValue);
		conditions += " AND RiskCode IN (";
		int i = 0;
		for (; i < riskClassCode.length - 1; i++) {
			conditions += "'" + riskClassCode[i] + "',";
		}
		conditions += "'" + riskClassCode[i] + "')";
		conditions += " ORDER BY RelateKindCode";
		BLPrpDkindRelateFacade prpDkindRelateCommand = new BLPrpDkindRelateFacade();
		Collection<?> collection = prpDkindRelateCommand.findByConditionsDistinct(conditions);
		PrpDkindRelateDto prpDkindRelateDto = null;
		for (Iterator<?> iter = collection.iterator(); iter.hasNext();) {
			prpDkindRelateDto = (PrpDkindRelateDto) iter.next();
			codeValues.add(prpDkindRelateDto.getRelateKindCode() + IConstants.FIELD_SEPARATOR);
			codeLabels.add(prpDkindRelateDto.getRelateKindCode());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 根据险种查询扩充字段名
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryColumnNameByRiskCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("=");
		String riskCodes = "";
		if (start > -1) {
			riskCodes = otherCondition.substring(start + "=".length()).trim();
		}
		String[] riskClassCode = riskCodes.split(",");
		String conditions = getCondition(codeMethod, "ColumnName", fieldValue);
		conditions += " AND RiskCode IN (";
		int i = 0;
		for (; i < riskClassCode.length - 1; i++) {
			conditions += "'" + riskClassCode[i] + "',";
		}
		conditions += "'" + riskClassCode[i] + "')";
		conditions += " ORDER BY ColumnName";
		BLPrpDfieldExtFacade prpDfieldExtCommand = new BLPrpDfieldExtFacade();
		Collection<?> collection = prpDfieldExtCommand.findByConditions(conditions);
		PrpDfieldExtDto prpDfieldExtDto = null;
		for (Iterator<?> iter = collection.iterator(); iter.hasNext();) {
			prpDfieldExtDto = (PrpDfieldExtDto) iter.next();
			codeValues.add(prpDfieldExtDto.getColumnName() + IConstants.FIELD_SEPARATOR + prpDfieldExtDto.getDisplayName());
			codeLabels.add(prpDfieldExtDto.getColumnName() + "--" + prpDfieldExtDto.getDisplayName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 根据险种查询费率区域组号
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryAreaGroupByRiskCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("=");
		String riskCodes = "";
		if (start > -1) {
			riskCodes = otherCondition.substring(start + "=".length()).trim();
		}
		String[] riskClassCode = riskCodes.split(",");
		String conditions = getCondition(codeMethod, "AreaGroup", fieldValue);
		conditions += " AND RiskCode IN (";
		int i = 0;
		for (; i < riskClassCode.length - 1; i++) {
			conditions += "'" + riskClassCode[i] + "',";
		}
		conditions += "'" + riskClassCode[i] + "')";
		conditions += " ORDER BY AreaGroup";
		BLPrpDareaGroupFacade prpDareaGroupCommand = new BLPrpDareaGroupFacade();
		Collection<?> collection = prpDareaGroupCommand.findByConditions(conditions);
		PrpDareaGroupDto prpDareaGroupDto = null;
		for (Iterator<?> iter = collection.iterator(); iter.hasNext();) {
			prpDareaGroupDto = (PrpDareaGroupDto) iter.next();
			codeValues.add(prpDareaGroupDto.getAreaGroup() + IConstants.FIELD_SEPARATOR);
			codeLabels.add(prpDareaGroupDto.getAreaGroup());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询代理协议号，含权限处理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryAgreementNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "AgreementNo", fieldValue);
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions += uiPowerInterface.addPower(user, "PrpDagreement", "", "ComCode");
		conditions = conditions + " order by AgreementNo ";
		BLPrpDagreementFacade facade = new BLPrpDagreementFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDagreementDto prpDagreementDto = (PrpDagreementDto) iter.next();
			codeValues.add(prpDagreementDto.getAgreementNo() + IConstants.FIELD_SEPARATOR + prpDagreementDto.getAgentCode());
			codeLabels.add(prpDagreementDto.getAgreementNo() + "--" + prpDagreementDto.getAgentCode());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 根据险种查询数据表名
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryTableNameByRiskCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("=");
		String riskCodes = "";
		if (start > -1) {
			riskCodes = otherCondition.substring(start + "=".length()).trim();
		}
		String[] riskClassCode = riskCodes.split(",");
		String conditions = getCondition(codeMethod, "tableName", fieldValue);
		conditions += " AND RiskCode IN (";
		int i = 0;
		for (; i < riskClassCode.length - 1; i++) {
			conditions += "'" + riskClassCode[i] + "',";
		}
		conditions += "'" + riskClassCode[i] + "')";
		conditions += " ORDER BY tableName";
		BLPrpXPcolFacade facade = new BLPrpXPcolFacade();
		Collection<?> collection = facade.findByConditionsDistinct(conditions);
		PrpXPcolDto prpXPcolDto = null;
		for (Iterator<?> iter = collection.iterator(); iter.hasNext();) {
			prpXPcolDto = (PrpXPcolDto) iter.next();
			codeValues.add(prpXPcolDto.getTableName() + IConstants.FIELD_SEPARATOR);
			codeLabels.add(prpXPcolDto.getTableName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 根据险种查询条款代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryClauseCodeByRiskCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("=");
		String riskCodes = "";
		if (start > -1) {
			riskCodes = otherCondition.substring(start + "=".length()).trim();
		}
		String[] riskClassCode = riskCodes.split(",");
		String conditions = getCondition(codeMethod, "clauseCode", fieldValue);
		conditions += " AND RiskCode IN (";
		int i = 0;
		for (; i < riskClassCode.length - 1; i++) {
			conditions += "'" + riskClassCode[i] + "',";
		}
		conditions += "'" + riskClassCode[i] + "')";
		conditions += " ORDER BY clauseCode";
		BLPrpDkindClauseFacade prpDkindClauseCommand = new BLPrpDkindClauseFacade();
		Collection<?> collection = prpDkindClauseCommand.findByConditionsDistinct(conditions);
		BLPrpDclauseFacade prpDclauseCommand = new BLPrpDclauseFacade();
		PrpDkindClauseDto prpDkindClauseDto = null;
		PrpDclauseDto prpDclauseDto = null;
		for (Iterator<?> iter = collection.iterator(); iter.hasNext();) {
			prpDkindClauseDto = (PrpDkindClauseDto) iter.next();
			String clauseConditions = "1=1";
			clauseConditions += " AND ClauseCode=" + "'" + prpDkindClauseDto.getClauseCode() + "'";
			Collection<?> clauseCollection = prpDclauseCommand.findClauseCodeAndName(clauseConditions);
			for (Iterator<?> clauseIter = clauseCollection.iterator(); clauseIter.hasNext();) {
				prpDclauseDto = (PrpDclauseDto) clauseIter.next();
				codeValues.add(prpDclauseDto.getClauseCode() + IConstants.FIELD_SEPARATOR + prpDclauseDto.getClauseName());
				codeLabels.add(prpDclauseDto.getClauseCode() + "--" + prpDclauseDto.getClauseName());
			}
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询讨论ID和讨论标题
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryUtiDiscussID(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " 1=1 ";
		conditions = getCondition(codeMethod, "discussID", fieldValue);
		conditions = conditions + " ORDER BY DiscussID";
		BLUtiDiscussFacade facade = new BLUtiDiscussFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			UtiDiscussDto element = (UtiDiscussDto) iter.next();
			codeValues.add(element.getDiscussID() + IConstants.FIELD_SEPARATOR + element.getTitle());
			codeLabels.add(element.getDiscussID() + "--" + element.getTitle());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询私信ID和私信标题
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryUtiMessageID(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " 1=1 ";
		conditions = getCondition(codeMethod, "messageID", fieldValue);
		conditions = conditions + " ORDER BY MessageID";
		BLUtiMessageFacade facade = new BLUtiMessageFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			UtiMessageDto element = (UtiMessageDto) iter.next();
			codeValues.add(element.getMessageId() + IConstants.FIELD_SEPARATOR + element.getMessageTitle());
			codeLabels.add(element.getMessageId() + "--" + element.getMessageTitle());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	public void getComCodeOptionsText(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputStream os = response.getOutputStream();
		try {
			ParamUtils paramUtils = new ParamUtils(request);
			String userCode = paramUtils.getParameter("userCode");
			String conditions = "UserCode ='" + userCode + "'";
			BLUtiUserGradeFacade facade = new BLUtiUserGradeFacade();
			Collection<?> collection = facade.findByConditions(conditions);
			StringBuffer buffer = new StringBuffer();
			Map<String, String> comCodeMap = new HashMap<String, String>();
			for (Iterator<?> iter = collection.iterator(); iter.hasNext();) {
				UtiUserGradeDto element = (UtiUserGradeDto) iter.next();
				if (!comCodeMap.containsKey(element.getComCode())) {
					if (comCodeMap.size() > 0) {
						buffer.append(Constants.GROUP_SEPARATOR);
					}
					comCodeMap.put(element.getComCode(), "");
					buffer.append(element.getComCode());
					buffer.append(Constants.FIELD_SEPARATOR);
					buffer.append(element.getComName());
				}
			}
			os.write(buffer.toString().getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
			os.write("error:getComCodeOptionsText ".getBytes());
		}
		os.flush();

	}

	/**
	 * 查询不同财务类型和用户权限以内的机构代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getComCodeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputStream os = response.getOutputStream();
		ParamUtils paramUtils = new ParamUtils(request);
		String upperComCode = paramUtils.getParameter("upperComCode");
		String centerFlag = paramUtils.getParameter("centerFlag");

		String conditions = "validstatus='1' ";

		if (!"".equals(upperComCode)) {
			conditions += "AND uppercomcode='" + upperComCode + "' ";
		}
		if (!"".equals(centerFlag) ) {
			conditions += "AND centerflag='" + centerFlag + "' ";
		}
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions += uiPowerInterface.addPower((UserDto) (request.getSession().getAttribute("user")), "PrpDcompany", "", "ComCode");
		conditions = conditions + " order by comcode ";
		List<PrpDcompany> collection = prpDcompanyService.findByConditions(conditions);
		StringBuffer buffer = new StringBuffer();
		Map<String, String> comCodeMap = new HashMap<String, String>();
		for (Iterator<PrpDcompany> iter = collection.iterator(); iter.hasNext();) {
			PrpDcompany element = iter.next();
			if (!comCodeMap.containsKey(element.getComCode())) {
				if (comCodeMap.size() > 0) {
					buffer.append(Constants.GROUP_SEPARATOR);
				}
				comCodeMap.put(element.getComCode(), "");
				buffer.append(element.getComCode());
				buffer.append(Constants.FIELD_SEPARATOR);
				buffer.append(element.getComCName());
			}
		}
		os.write(buffer.toString().getBytes("UTF-8"));
		os.flush();
	}

	/**
	 * 查询公司代码和公司名称
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getSapComCodeList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		OutputStream os = response.getOutputStream();
		ParamUtils paramUtils = new ParamUtils(request);

		String centerFlag = paramUtils.getParameter("centerFlag");
		String conditions = " 1=1";
		Collection<?> collection;

		if (!centerFlag.equals("1")) {
			String upperComCode = paramUtils.getParameter("monitorComCode");
			conditions += " AND validstatus='1' AND centerflag='1' ";

			if (!"".equals(upperComCode)) {
				conditions += "AND uppercomcode='" + upperComCode + "' ";
			}

			UIPowerInterface uiPowerInterface = new UIPowerInterface();
			conditions += uiPowerInterface.addPower((UserDto) (request.getSession().getAttribute("user")), "PrpDcompany", "", "ComCode");
			conditions = conditions + " order by comcode ";

			collection = prpDcompanyService.findByConditions(conditions);
			conditions = "comcode in (";
			for (Iterator<?> companyIter = collection.iterator(); companyIter.hasNext();) {
				PrpDcompany element = (PrpDcompany) companyIter.next();
				conditions = conditions + "'" + element.getComCode() + "',";
			}
			conditions += "'')";
		}

		BLSapCompanyFacade sapCommand = new BLSapCompanyFacade();
		collection = sapCommand.findByConditions(conditions);

		StringBuffer buffer = new StringBuffer();
		Map<String, String> comCodeMap = new HashMap<String, String>();
		for (Iterator<?> sapIter = collection.iterator(); sapIter.hasNext();) {
			SapCompanyDto element = (SapCompanyDto) sapIter.next();
			if (!comCodeMap.containsKey(element.getComCode())) {
				if (comCodeMap.size() > 0) {
					buffer.append(Constants.GROUP_SEPARATOR);
				}
				comCodeMap.put(element.getComCode(), "");
				buffer.append(element.getComCode());
				buffer.append(Constants.FIELD_SEPARATOR);
				buffer.append(element.getComName());
			}
		}

		os.write(buffer.toString().getBytes("UTF-8"));
		os.flush();

	}

	/**
	 * 查询成本中心代碼和名稱
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getCostCenterList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputStream os = response.getOutputStream();
		ParamUtils paramUtils = new ParamUtils(request);
		String comCode = paramUtils.getParameter("ComCode");
		String conditions = "comcode='" + comCode + "' ";
		conditions = conditions + " order by costcentercode ";
		BLSapCostCenterFacade facade = new BLSapCostCenterFacade();
		Collection<?> collection = facade.findByConditions(conditions);
		StringBuffer buffer = new StringBuffer();
		Map<String, String> comCodeMap = new HashMap<String, String>();
		for (Iterator<?> iter = collection.iterator(); iter.hasNext();) {
			SapCostCenterDto element = (SapCostCenterDto) iter.next();

			if (!comCodeMap.containsKey(element.getCostCenterCode())) {
				if (comCodeMap.size() > 0) {
					buffer.append(Constants.GROUP_SEPARATOR);
				}
				comCodeMap.put(element.getCostCenterCode(), "");
				buffer.append(element.getCostCenterCode());
				buffer.append(Constants.FIELD_SEPARATOR);
				buffer.append(element.getCostCenterName());
			}
		}

		os.write(buffer.toString().getBytes("UTF-8"));
		os.flush();

	}

	/**
	 * 查询SAP公司代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void querySapComCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		PrpDuserDto user = (PrpDuserDto) request.getSession().getAttribute("user");
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		String strComCode = "";// 平台管理员登录的机构
		String conditions = "";
		conditions = getCondition(codeMethod, "ComCode", fieldValue);
		strComCode = user.getLoginComCode();
		if (strComCode.length() > 2) {
			if (strComCode.substring(0, 2).equals("00")) {

			} else {
				conditions += " And ComCode Like '" + strComCode.substring(0, 2) + "%' ";
			}
		}

		if (!otherCondition.equals("")) {
			String attribute = "";
			String value = "";
			int comma = otherCondition.indexOf(",");
			int equal;
			while (comma > -1) {
				attribute = otherCondition.substring(0, comma);
				equal = attribute.indexOf("=");
				value = attribute.substring(equal + 1);
				attribute = attribute.substring(0, equal);
				otherCondition = otherCondition.substring(comma + 1);
				comma = otherCondition.indexOf(",");
				conditions += " AND " + attribute + "='" + value + "'";
			}
			attribute = otherCondition;
			equal = attribute.indexOf("=");

			value = attribute.substring(equal + 1);

			attribute = attribute.substring(0, equal);

			conditions += " AND " + attribute + "='" + value + "'";
		}
		conditions = conditions + " order by ComCode ";
		BLSapCompanyFacade facade = new BLSapCompanyFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			SapCompanyDto element = (SapCompanyDto) iter.next();
			codeValues.add(element.getComCode() + IConstants.FIELD_SEPARATOR + element.getComName());
			codeLabels.add(element.getComCode() + "--" + element.getComName());
		}

		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 查询成本中心代码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryCostCenterCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();

		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");

		String conditions = "";
		conditions = getCondition(codeMethod, "CostCenterCode", fieldValue);

		if (!otherCondition.equals("")) {
			String attribute = "";
			String value = "";
			int comma = otherCondition.indexOf(",");
			int equal;
			while (comma > -1) {
				attribute = otherCondition.substring(0, comma);
				equal = attribute.indexOf("=");
				value = attribute.substring(equal + 1);
				attribute = attribute.substring(0, equal);
				otherCondition = otherCondition.substring(comma + 1);
				comma = otherCondition.indexOf(",");
				conditions += " AND " + attribute + "='" + value + "'";
			}
			attribute = otherCondition;
			equal = attribute.indexOf("=");

			value = attribute.substring(equal + 1);

			attribute = attribute.substring(0, equal);

			conditions += " AND " + attribute + "='" + value + "'";
		}

		conditions = conditions + " order by CostCenterCode ";
		BLSapCostCenterFacade facade = new BLSapCostCenterFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			SapCostCenterDto element = (SapCostCenterDto) iter.next();
			codeValues.add(element.getCostCenterCode() + IConstants.FIELD_SEPARATOR + element.getCostCenterName());
			codeLabels.add(element.getCostCenterCode() + "--" + element.getCostCenterName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryNodeNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");

		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("=");
		String modelNo = "";
		if (start > -1) {
			modelNo = otherCondition.substring(start + "=".length()).trim();
		}

		String conditions = getCondition(codeMethod, "modelNo", fieldValue);

		if (!modelNo.equals("")) {
			conditions += SqlUtils.convertString("ModelNo", modelNo);
		}

		conditions += "And endFlag<>'1' ORDER BY modelNo";
		BLSwfNodeFacade facade = new BLSwfNodeFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			SwfNodeDto swfNodeDto = (SwfNodeDto) iter.next();
			codeValues.add(swfNodeDto.getNodeNo() + IConstants.FIELD_SEPARATOR + swfNodeDto.getNodeName());
			codeLabels.add(swfNodeDto.getNodeNo() + "--" + swfNodeDto.getNodeName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryGroupNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "GroupNo", fieldValue);
		conditions += " order by GroupNo";
		BLUwGroupFacade bLUwGroupFacade = new BLUwGroupFacade();
		int pageNo = paramUtils.getIntParameter("pageNo", 1);
		int rowsPerPage = paramUtils.getIntParameter("rowsPerPage", 1000);
		PageRecord pageRecord = bLUwGroupFacade.findDistinctCode(conditions, pageNo, rowsPerPage);
		Collection<?> collection = pageRecord.getResult();
		for (Iterator<?> iter = collection.iterator(); iter.hasNext();) {
			UwGroupDto uwGroupDto = (UwGroupDto) iter.next();
			codeValues.add(uwGroupDto.getGroupNo() + IConstants.FIELD_SEPARATOR + uwGroupDto.getGroupDesc());

			codeLabels.add(uwGroupDto.getGroupNo() + "--" + uwGroupDto.getGroupDesc());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryFactorCodeByClassCodeUwType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String otherCondition = paramUtils.getParameter("otherCondition");
		String conditions = " 1=1 ";
		String[] addonsCondition = otherCondition.split(";");
		String addonsUwType = " AND UwType='" + addonsCondition[0] + "' ";
		String addonsClassCode = SqlUtils.convertString("ClassCode", addonsCondition[1]);
		String addonsRiskCategoryCode = SqlUtils.convertString("RiskCategoryCode", addonsCondition[2]);
		conditions = conditions + addonsUwType + addonsClassCode + addonsRiskCategoryCode;
		// POWER 添加员工查询权限
		BLUtiUwFactorFacade facade = new BLUtiUwFactorFacade();
		Collection<?> UtiUwFactorList = facade.findByConditions(conditions);
		UtiUwFactorDto utiUwFactorDto = null;
		String label = null;
		Iterator<?> it = UtiUwFactorList.iterator();
		while (it.hasNext()) {
			utiUwFactorDto = (UtiUwFactorDto)it.next();
			String uwType = utiUwFactorDto.getUwType();
			String classCode = utiUwFactorDto.getClassCode();
			String factorCode = utiUwFactorDto.getFactorCode();
			utiUwFactorDto = facade.findByPrimaryKey(uwType, classCode, factorCode);
			label = utiUwFactorDto.getFactorCode() + "--" + utiUwFactorDto.getFactorName();
			if (!codeLabels.contains(label)) {
				codeValues.add(utiUwFactorDto.getFactorCode() + IConstants.FIELD_SEPARATOR + utiUwFactorDto.getFactorName());
				codeLabels.add(utiUwFactorDto.getFactorCode() + "--" + utiUwFactorDto.getFactorName());
			}
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryInvestRationRisk(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String conditions = " ";
		conditions += "validstatus='1' ORDER BY RiskCode";
		BLPrpDriskFacade facade = new BLPrpDriskFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDriskDto element = (PrpDriskDto) iter.next();
			codeValues.add(element.getRiskCode() + IConstants.FIELD_SEPARATOR + element.getRiskCName());
			codeLabels.add(element.getRiskCode() + "--" + element.getRiskCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryInvestRationKind(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String otherCondition = paramUtils.getParameter("otherCondition");
		String conditions = " ";
		conditions += "RiskCode = '" + otherCondition + "'";
		conditions += "and validstatus='1' ORDER BY KindCode";
		List<PrpDkind> result = prpDkindService.findByConditions(conditions);
		for (Iterator<PrpDkind> iter = result.iterator(); iter.hasNext();) {
			PrpDkind element = iter.next();
			codeValues.add(element.getId().getKindCode() + IConstants.FIELD_SEPARATOR + element.getKindCName() + IConstants.FIELD_SEPARATOR + element.getCalculateFlag());
			codeLabels.add(element.getId().getKindCode() + "--" + element.getKindCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryInvestRationItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String otherCondition = paramUtils.getParameter("otherCondition");
		String conditions = " ";
		conditions += "RiskCode = '" + otherCondition + "'";
		conditions += "and validstatus='1' ORDER BY ItemCode";
		BLPrpDitemFacade facade = new BLPrpDitemFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDitemDto element = (PrpDitemDto) iter.next();
			codeValues.add(element.getItemCName() + IConstants.FIELD_SEPARATOR + element.getItemFlag());
			codeLabels.add(element.getItemCode() + "--" + element.getItemCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryInvestRationClause(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String otherCondition = paramUtils.getParameter("otherCondition");
		String conditions = " ";
		conditions += "ClauseCode = 'F" + otherCondition + "'";
		conditions += " and titleFlag='1' and validstatus='1' ORDER BY ClauseCode";
		BLPrpDclauseFacade facade = new BLPrpDclauseFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDclauseDto element = (PrpDclauseDto) iter.next();
			codeValues.add(element.getClauseCode() + IConstants.FIELD_SEPARATOR + element.getLanguage());
			codeLabels.add(element.getClauseCode() + "--" + element.getClauseName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryInvestRationCurrency(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String conditions = " ";

		conditions += "validstatus='1' ORDER BY CurrencyCode";
		BLPrpDcurrencyFacade facade = new BLPrpDcurrencyFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDcurrencyDto element = (PrpDcurrencyDto) iter.next();
			codeValues.add(element.getCurrencyCode());
			codeLabels.add(element.getCurrencyCode() + "--" + element.getCurrencyCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryInvestRationLimit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "RiskCode", fieldValue);
		conditions += " ORDER BY RiskCode";
		BLPrpDinvestRationFacade facade = new BLPrpDinvestRationFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDinvestRationDto element = (PrpDinvestRationDto) iter.next();
			codeValues.add(element.getRiskCode() + IConstants.FIELD_SEPARATOR + element.getComCode() + IConstants.FIELD_SEPARATOR + element.getStartDate() + IConstants.FIELD_SEPARATOR + element.getEndDate() + IConstants.FIELD_SEPARATOR
					+ element.getInsuredYear());
			codeLabels.add(element.getRiskCode() + "--" + element.getComCode() + "--" + element.getStartDate());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryInvestRationLimitKind(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String conditions = " ";
		String otherCondition = paramUtils.getParameter("otherCondition");
		conditions = otherCondition + "order by KindCode";
		BLPrpDinvestRationFacade facade = new BLPrpDinvestRationFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDinvestRationDto element = (PrpDinvestRationDto) iter.next();
			codeValues.add(element.getKindCode());
			codeLabels.add(element.getKindCode());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryInvestRationLimitItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String conditions = " ";
		String otherCondition = paramUtils.getParameter("otherCondition");
		conditions = otherCondition + "order by KindCode";
		BLPrpDinvestRationFacade facade = new BLPrpDinvestRationFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDinvestRationDto element = (PrpDinvestRationDto) iter.next();
			codeValues.add(element.getItemCode() + IConstants.FIELD_SEPARATOR + element.getItemCode());
			codeLabels.add(element.getItemCode() + "--" + element.getItemCode());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	// 银行树行政区划代码
	private void queryProvince(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String conditions = " ";
		String otherCondition = paramUtils.getParameter("otherCondition");
		conditions = otherCondition + "order by ProvinceCode";
		BLUtiadminprovinceFacade facade = new BLUtiadminprovinceFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			UtiadminprovinceDto element = (UtiadminprovinceDto) iter.next();
			codeValues.add(element.getProvinceCode() + IConstants.FIELD_SEPARATOR + element.getProvinceName());
			codeLabels.add(element.getProvinceCode() + "--" + element.getProvinceName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 查詢城市代碼
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryCity(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String conditions = " ";
		String otherCondition = paramUtils.getParameter("otherCondition");
		conditions = otherCondition + "order by CityCode";
		BLUtiadmincityFacade facade = new BLUtiadmincityFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			UtiadmincityDto element = (UtiadmincityDto) iter.next();
			codeValues.add(element.getCityCode() + IConstants.FIELD_SEPARATOR + element.getCityName());
			codeLabels.add(element.getCityCode() + "--" + element.getCityName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 查詢地區代碼
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryArea(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String conditions = " ";
		String otherCondition = paramUtils.getParameter("otherCondition");
		conditions = otherCondition + "order by DistrictCode";
		BLUtiadmindistrictFacade facade = new BLUtiadmindistrictFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			UtiadmindistrictDto element = (UtiadmindistrictDto) iter.next();
			codeValues.add(element.getDistrictCode() + IConstants.FIELD_SEPARATOR + element.getDistrictName());
			codeLabels.add(element.getDistrictCode() + "--" + element.getDistrictName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}
	/**
	 * 查詢銀行代碼
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryBankCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String conditions = " ";
		String otherCondition = paramUtils.getParameter("otherCondition");
		conditions = otherCondition + "codetype='BankTreeCode' order by CodeCode";
		List<PrpDcode> result = prpDcodeService.findByConditions(conditions);
		for (Iterator<PrpDcode> iter = result.iterator(); iter.hasNext();) {
			PrpDcode element = (PrpDcode) iter.next();
			codeValues.add(element.getId().getCodeCode() + IConstants.FIELD_SEPARATOR + element.getCodeCName());
			codeLabels.add(element.getId().getCodeCode() + "--" + element.getCodeCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 *  查询prpddangercarmodel表的机构代码 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryComCodeByCarDanger(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "ComCode", fieldValue);
		conditions += " AND validstatus='1'";
		conditions = conditions + " order by ComCode ";
		BLPrpDdangerCarModelFacade blPrpDdangerCarModelFacade = new BLPrpDdangerCarModelFacade();
		Collection<?> result = blPrpDdangerCarModelFacade.findDistinctDangerCarModelByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDdangerCarModelDto element = (PrpDdangerCarModelDto) iter.next();
			PrpDcompany prpDcompany = prpDcompanyService.findByPrimaryKey(element.getComCode());
			codeValues.add(element.getComCode() + IConstants.FIELD_SEPARATOR + prpDcompany.getComCName());
			codeLabels.add(element.getComCode() + "--" + prpDcompany.getComCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryComCodeByCarBandGroupCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String otherCondition = paramUtils.getParameter("otherCondition");
//		String codeMethod = paramUtils.getParameter("codeMethod");
//		String fieldValue = paramUtils.getParameter("fieldValue");
		int start = otherCondition.indexOf("prpDcarBandGroupComCode=");
		int begin = otherCondition.indexOf("=");
		String prpDcarBandGroupComCode = "";
		if (start > -1) {
			prpDcarBandGroupComCode = otherCondition.substring(begin + 1, otherCondition.length());
		}
		String conditions = "1=1";
		if (!prpDcarBandGroupComCode.equals("")) {
			conditions += SqlUtils.convertString("ComCode", prpDcarBandGroupComCode);
		}
		conditions += " AND validstatus='1'";
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions += uiPowerInterface.addPower((UserDto) (request.getSession().getAttribute("user")), "PrpDcompany", "", "ComCode");
		conditions = conditions + " order by carBandGroupCode ";
		BLPrpDdangerCarModelFacade blPrpDdangerCarModelFacade = new BLPrpDdangerCarModelFacade();
		Collection<?> result = blPrpDdangerCarModelFacade.findDistinctCarBandGroupCodeByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDdangerCarModelDto element = (PrpDdangerCarModelDto) iter.next();
			codeValues.add(element.getCarBandGroupCode() + IConstants.FIELD_SEPARATOR + element.getCarBandGroupName());
			codeLabels.add(element.getCarBandGroupCode() + "--" + element.getCarBandGroupName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void querySeriesId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String conditions = "1=1";
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions += uiPowerInterface.addPower((UserDto) (request.getSession().getAttribute("user")), "PrpDcompany", "", "ComCode");
		conditions = conditions + " order by series_id ";
		BLPrpdcarbrandFacade blPrpdcarbrandFacade = new BLPrpdcarbrandFacade();
		Collection<?> result = blPrpdcarbrandFacade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpdcarbrandDto element = (PrpdcarbrandDto) iter.next();
			codeValues.add(element.getSeries_id() + IConstants.FIELD_SEPARATOR + element.getSeries_name());
			codeLabels.add(element.getSeries_id() + "--" + element.getSeries_name());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void querySeriesIdByCarDangerCarmodel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
//		String codeMethod = paramUtils.getParameter("codeMethod");
//		String fieldValue = paramUtils.getParameter("fieldValue");

		String otherCondition = paramUtils.getParameter("otherCondition");

		// 处理upperComCode
		int start = otherCondition.indexOf("prpDdangerCarModelComCode=");
		int begin = otherCondition.indexOf("=");
		String prpDdangerCarModelComCode = "";
		if (start > -1) {
			prpDdangerCarModelComCode = otherCondition.substring(begin + 1, otherCondition.length());
		}
		String conditions = "1=1";
		if (!prpDdangerCarModelComCode.equals("")) {
			conditions += SqlUtils.convertString("ComCode", prpDdangerCarModelComCode);
		}
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions += uiPowerInterface.addPower((UserDto) (request.getSession().getAttribute("user")), "PrpDcompany", "", "ComCode");
		conditions = conditions + " order by carBandCode ";
		BLPrpDdangerCarModelFacade blPrpDdangerCarModelFacade = new BLPrpDdangerCarModelFacade();
		Collection<?> result = blPrpDdangerCarModelFacade.findDistinctCarBandCodeByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDdangerCarModelDto element = (PrpDdangerCarModelDto) iter.next();
			codeValues.add(element.getCarBandCode() + IConstants.FIELD_SEPARATOR + element.getCarBandName());
			codeLabels.add(element.getCarBandCode() + "--" + element.getCarBandName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void querySeriesName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String conditions = "1=1";
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions += uiPowerInterface.addPower((UserDto) (request.getSession().getAttribute("user")), "PrpDcompany", "", "ComCode");
		BLPrpdcarbrandFacade blPrpdcarbrandFacade = new BLPrpdcarbrandFacade();
		Collection<?> result = blPrpdcarbrandFacade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpdcarbrandDto element = (PrpdcarbrandDto) iter.next();
			codeValues.add(element.getSeries_name() + IConstants.FIELD_SEPARATOR + element.getSeries_id());
			codeLabels.add(element.getSeries_name() + "--" + element.getSeries_id());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryCarModelName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("prpDdangerCarModelCarBandCode=");
		int begin = otherCondition.indexOf("=");
		String prpDdangerCarModelCarBandCode = "";
		if (start > -1) {
			prpDdangerCarModelCarBandCode = otherCondition.substring(begin + 1, otherCondition.length());
		}
		conditions += getCondition(codeMethod, "ModelCode", fieldValue);
		if (!prpDdangerCarModelCarBandCode.equals("")) {
			conditions += SqlUtils.convertString("Series_Id", prpDdangerCarModelCarBandCode);
		}
		conditions += " ORDER BY ModelCode";
		BLPrpDcarModelFacade blPrpDcarModelFacade = new BLPrpDcarModelFacade();
		Collection<?> result = blPrpDcarModelFacade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDcarModelDto element = (PrpDcarModelDto) iter.next();
			codeValues.add(element.getModelName() + IConstants.FIELD_SEPARATOR + element.getModelCode());
			codeLabels.add(element.getModelName() + "--" + element.getModelCode());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void querymodelCodeByDangerCarModel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("prpDdangerCarModelCarBandCode=");
		int begin = otherCondition.indexOf("=");
		String prpDdangerCarModelCarBandCode = "";
		if (start > -1) {
			prpDdangerCarModelCarBandCode = otherCondition.substring(begin + 1, otherCondition.length());
		}
		conditions += getCondition(codeMethod, "ModelCode", fieldValue);
		if (!prpDdangerCarModelCarBandCode.equals("")) {
			// conditions += " AND ClassCode='" + prpDriskClassCode + "'";
			conditions += SqlUtils.convertString("carBandCode", prpDdangerCarModelCarBandCode);
		}
		conditions += " ORDER BY ModelCode";
		BLPrpDdangerCarModelFacade blPrpDdangerCarModelFacade = new BLPrpDdangerCarModelFacade();
		Collection<?> result = blPrpDdangerCarModelFacade.findDistinctModelCodeByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDdangerCarModelDto element = (PrpDdangerCarModelDto) iter.next();
			codeValues.add(element.getModelCode() + IConstants.FIELD_SEPARATOR + element.getModelName());
			codeLabels.add(element.getModelCode() + "--" + element.getModelName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void querymodelNameByDangerCarModel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("prpDdangerCarModelCarBandCode=");
		int begin = otherCondition.indexOf("=");
		String prpDdangerCarModelCarBandCode = "";
		if (start > -1) {
			prpDdangerCarModelCarBandCode = otherCondition.substring(begin + 1, otherCondition.length());
		}
		conditions += getCondition(codeMethod, "ModelCode", fieldValue);
		if (!prpDdangerCarModelCarBandCode.equals("")) {
			conditions += SqlUtils.convertString("carBandCode", prpDdangerCarModelCarBandCode);
		}
		conditions += " ORDER BY ModelName";
		BLPrpDdangerCarModelFacade blPrpDdangerCarModelFacade = new BLPrpDdangerCarModelFacade();

		Collection<?> result = blPrpDdangerCarModelFacade.findDistinctModelCodeByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDdangerCarModelDto element = (PrpDdangerCarModelDto) iter.next();
			codeValues.add(element.getModelName() + IConstants.FIELD_SEPARATOR + element.getModelCode());
			codeLabels.add(element.getModelName() + "--" + element.getModelCode());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryKindByDangerCarModel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
//		String strWhere = "";
		String otherCondition = paramUtils.getParameter("otherCondition");
		int start = otherCondition.indexOf("prpDcarBandGroupCarBandGroupCode=");
		int begin = otherCondition.indexOf("=");
		String prpDcarBandGroupCarBandGroupCode = "";
		if (start > -1) {
			prpDcarBandGroupCarBandGroupCode = otherCondition.substring(begin + 1, otherCondition.length());
		}
		conditions += getCondition(codeMethod, "ModelCode", fieldValue);
		if (!prpDcarBandGroupCarBandGroupCode.equals("")) {
			conditions += SqlUtils.convertString("CarBandGroupCode", prpDcarBandGroupCarBandGroupCode);
		}
		conditions += " ORDER BY KindCode";
		BLPrpDdangerCarModelFacade blPrpDdangerCarModelFacade = new BLPrpDdangerCarModelFacade();
		Collection<?> result = blPrpDdangerCarModelFacade.findDistinctKindCodeByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDdangerCarModelDto element = (PrpDdangerCarModelDto) iter.next();
//			strWhere = "1=1 AND ClassCode ='" + element.getKindCode() + "'";
			BLPrpDclassFacade blPrpDclassFacade = new BLPrpDclassFacade();
			Collection<?> results = blPrpDclassFacade.findByConditions(conditions);
			for (Iterator<?> iters = results.iterator(); iters.hasNext();) {
				PrpDclassDto elements = (PrpDclassDto) iters.next();
				codeValues.add(element.getKindCode() + IConstants.FIELD_SEPARATOR + elements.getClassName());
				codeLabels.add(element.getKindCode() + "--" + elements.getClassName());
			}
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	// 理赔组更新过的文件但没有合进vss中,需要合入,並已经升级到正式环境
	private void queryOrgCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 处理upperComCode
		int start = otherCondition.indexOf("upperComCode=");
		String upperComCode = "";
		if (start > -1) {
			int end = otherCondition.indexOf(start + ",");
			if (end > -1) {
				upperComCode = otherCondition.substring(start + "upperComCode=".length(), end).trim();
			} else {
				upperComCode = otherCondition.substring(start + "upperComCode=".length()).trim();
			}
		}
		String conditions = " ";
		conditions = getCondition(codeMethod, "ComCode", fieldValue);
		if (!upperComCode.equals("")) {
			conditions += " AND uppercomcode='" + upperComCode + "' ";
		}
		conditions += " AND validstatus='1' AND centerflag='0'";
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions += uiPowerInterface.addPower((UserDto) (request.getSession().getAttribute("user")), "PrpDcompany", "", "ComCode");
		conditions = conditions + " order by ComCode ";
		List<PrpDcompany> result = prpDcompanyService.findByConditions(conditions);
		for (Iterator<PrpDcompany> iter = result.iterator(); iter.hasNext();) {
			PrpDcompany element = iter.next();
			codeValues.add(element.getComCode() + IConstants.FIELD_SEPARATOR + element.getComCName());
			codeLabels.add(element.getComCode() + "--" + element.getComCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 增加见费出单的联合查询，通过对人员、机构、代理处理的类型进行查不同内容。
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryJFeeCombine(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		String otherCondition = paramUtils.getParameter("otherCondition");
		Collection<?> result = null;
		if ("1".equals(otherCondition)) {
			conditions = getCondition(codeMethod, "USERCODE", fieldValue);
			conditions = conditions + " AND VALIDSTATUS='1' ORDER BY USERCODE ";
			result = prpDuserService.findByConditions(conditions);
			PrpDuser element = null;
			for (Iterator<?> iter = result.iterator(); iter.hasNext(); codeLabels.add(element.getUserCode() + "--" + element.getUserName())) {
				element = (PrpDuser) iter.next();
				codeValues.add(element.getUserCode() + "_FIELD_SEPARATOR_" + element.getUserName());
			}

		} else if ("2".equals(otherCondition)) {
			conditions = getCondition(codeMethod, "COMCODE", fieldValue);
			conditions = conditions + " AND VALIDSTATUS='1' ORDER BY COMCODE ";
			result = prpDcompanyService.findByConditions(conditions);
			PrpDcompany element;
			for (Iterator<?> iter = result.iterator(); iter.hasNext(); codeLabels.add(element.getComCode() + "--" + element.getComCName())) {
				element = (PrpDcompany) iter.next();
				codeValues.add(element.getComCode() + "_FIELD_SEPARATOR_" + element.getComCName());
			}

		} else if ("3".equals(otherCondition)) {
			conditions = getCondition(codeMethod, "AGENTCODE", fieldValue);
			conditions = conditions + " AND VALIDSTATUS='1' ORDER BY AGENTCODE ";
			result = prpDagentService.findByConditions(conditions);
			PrpDagent element;
			for (Iterator<?> iter = result.iterator(); iter.hasNext(); codeLabels.add(element.getAgentCode() + "--" + element.getAgentName())) {
				element = (PrpDagent) iter.next();
				codeValues.add(element.getAgentCode() + "_FIELD_SEPARATOR_" + element.getAgentName());
			}

		}
		request.setAttribute("codeValues", ((Object) (codeValues.toArray())));
		request.setAttribute("codeLabels", ((Object) (codeLabels.toArray())));
	}

	/**
	 * add by xushaobo 20081024 reason:收付登录机构处理
	 */
	public void getComCodeOptionsTextPayment(HttpServletRequest request, HttpServletResponse response) throws Exception {

		OutputStream os = response.getOutputStream();
		PrpDcompany prpDcompany = new PrpDcompany();
		try {
			ParamUtils paramUtils = new ParamUtils(request);
			String userCode = paramUtils.getParameter("userCode");
			PrpDuser prpDuser = prpDuserService.findPrpDuser(userCode);

			String strAccountCode = "";
			if (prpDuser != null) {
				strAccountCode = prpDuser.getAccountCode();
				if (!"".equals(strAccountCode)) {
					prpDcompany = prpDcompanyService.findByPrimaryKey(strAccountCode);
				}
			}

			String strAccountName = "";
			if (prpDcompany != null) {
				strAccountName = prpDcompany.getComCName();
			}
			StringBuffer buffer = new StringBuffer();
			buffer.append(strAccountCode);
			buffer.append(Constants.FIELD_SEPARATOR);
			buffer.append(strAccountName);
			os.write(buffer.toString().getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
			os.write("error:getComCodeOptionsTextPayment ".getBytes());
		}
		os.flush();

	}

	private void querycomAccountCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		// 处理upperComCode
		int start = otherCondition.indexOf("upperComCode=");
		String upperComCode = "";
		if (start > -1) {
			int end = otherCondition.indexOf(start + ",");
			if (end > -1) {
				upperComCode = otherCondition.substring(start + "upperComCode=".length(), end).trim();
			} else {
				upperComCode = otherCondition.substring(start + "upperComCode=".length()).trim();
			}
		}
		String conditions = " ";
		conditions = getCondition(codeMethod, "ComCode", fieldValue);
		if (!upperComCode.equals("")) {
			conditions += " AND uppercomcode='" + upperComCode + "' ";
		}
		conditions += " AND validstatus='1'";
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions += uiPowerInterface.addPower((UserDto) (request.getSession().getAttribute("user")), "PrpDcompany", "", "ComCode");
		conditions = conditions + "AND CENTERFLAG IN('1','2') order by ComCode ";

		List<PrpDcompany> result = prpDcompanyService.findByConditions(conditions);
		for (Iterator<PrpDcompany> iter = result.iterator(); iter.hasNext();) {
			PrpDcompany element = (PrpDcompany) iter.next();
			codeValues.add(element.getComCode() + IConstants.FIELD_SEPARATOR + element.getComCName());
			codeLabels.add(element.getComCode() + "--" + element.getComCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 增加管控信息人员绑定 归属机构选择 liuzhenquan20090515
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryComCodeGK(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		String usercode,chanelType;
		String ss[] = otherCondition.split("@");
		usercode = ss[0];
//		gradeCode = ss[1];
		chanelType = ss[2];
//		loginComCode = ss[3];
//		riskcode = ss[4];
		PrpDuser prpDuser = prpDuserService.findPrpDuser(usercode);
		prpDuser.setLoginSystem("prpall");
		String conditions = " ";
		conditions = getCondition(codeMethod, "ComCode", fieldValue);
		conditions += " AND validstatus='1'";
		conditions += " AND substr(ComType,2,1)='1' AND  CENTERFLAG ='3'";
		ChgDate chgDate = new ChgDate();
		String TodayDate = chgDate.getCurrentTime("yyyy-MM-dd");
		BLPrpDconfigCode blprpDconfigCode = new BLPrpDconfigCode();
		blprpDconfigCode.getFunNameOrFunType(prpDuser.getComCode(), "0000", "GROUPNATURE", TodayDate);// comcode
		if (blprpDconfigCode.getSize() > 0) {
			if ("01".equals(chanelType)) {// chanlType=1
				conditions = conditions + " AND Groupnature =  '" + chanelType + "' "; //
			} else {
				conditions = conditions + " AND (Groupnature !=  '01' or Groupnature is null) ";
			}
		}
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto user = new UserDto();
		user.setUserCode(usercode);
		conditions += uiPowerInterface.addPower(user, "PrpDcompany", "", "ComCode");
		conditions = conditions + " order by ComCode ";
		List<PrpDcompany> result = prpDcompanyService.findByConditions(conditions);
		for (Iterator<PrpDcompany> iter = result.iterator(); iter.hasNext();) {
			PrpDcompany element = (PrpDcompany) iter.next();
			codeValues.add(element.getComCode() + IConstants.FIELD_SEPARATOR + element.getComCName());
			codeLabels.add(element.getComCode() + "--" + element.getComCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 增加管控信息人员绑定 登陆机构选择用 liuzhenquan20090515
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void queryLogonComcode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		String conditions = " ";
		conditions = getCondition(codeMethod, "comCode", fieldValue);
		conditions += " and " + otherCondition;
		Collection<UtiUserGrade> collection = utiUserGradeService.findByConditions(conditions);
		Map<String, String> comCodeMap = new HashMap<String, String>();
		for (Iterator<UtiUserGrade> iter = collection.iterator(); iter.hasNext();) {
			UtiUserGrade element = iter.next();
			if (!comCodeMap.containsKey(element.getId().getComcode())) {

				comCodeMap.put(element.getId().getComcode(), "");
				codeValues.add(element.getId().getComcode() + "@" + element.getId().getGradeCode() + "@" + element.getComName());

				codeLabels.add(element.getId().getComcode() + "--" + element.getComName());

			}
		}
		request.setAttribute("GradeCode_GK", "GradeCode_GK");
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * 增加管控信息人员绑定 归属机构选择 liuzhenquan20090515
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void Handler2Code(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		String usercode,comcode;
		String ss[] = otherCondition.split("@");
		usercode = ss[0];
//		gradeCode = ss[1];
//		chanelType = ss[2];
//		loginComCode = ss[3];
//		riskcode = ss[4];
		comcode = ss[5];
		PrpDuser prpDuser = prpDuserService.findPrpDuser(usercode);
		prpDuser.setLoginSystem("prpall");

		String conditions = " ";
		conditions = getCondition(codeMethod, "UserCode", fieldValue);
		conditions += " AND ComCode = '" + comcode + "'";
		conditions += " AND validstatus='1'";
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto userDto = new UserDto();
		userDto.setUserCode(usercode);
		conditions += uiPowerInterface.addPower(userDto, "PrpDuser", "UserCode", "ComCode");
		conditions = conditions + " order by UserCode ";
		List<PrpDuser> result = prpDuserService.findByConditions(conditions);
		for (Iterator<PrpDuser> iter = result.iterator(); iter.hasNext();) {
			PrpDuser element = iter.next();
			codeValues.add(element.getUserCode() + IConstants.FIELD_SEPARATOR + element.getUserName());
			codeLabels.add(element.getUserCode() + "--" + element.getUserName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void BusinessNatureGK(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		String conditions = " ";
		conditions = getCondition(codeMethod, "codecode", fieldValue);
		conditions += " AND validstatus='1' and codetype = 'BusinessNature' AND flag like '%" + otherCondition + "%'";
		List<PrpDcode> result = prpDcodeService.findByConditions(conditions);
		for (Iterator<PrpDcode> iter = result.iterator(); iter.hasNext();) {
			PrpDcode element = iter.next();
			codeValues.add(element.getId().getCodeCode() + IConstants.FIELD_SEPARATOR + element.getCodeCName());
			codeLabels.add(element.getId().getCodeCode() + "--" + element.getCodeCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	public void AgentCodeGK(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		String businessNature = otherCondition.split("@")[0];
		String usercode = otherCondition.split("@")[1];
		String comcodeGk = otherCondition.split("@")[2];
		String conditions = " ";
		String strQueryConditionSub = "";
		String strQueryConditionBak = "  validstatus='1'";
		OperatorConfigAction operatorConfigAction = new OperatorConfigAction();
		String flag = operatorConfigAction.query_flag("InteractionType").getRule();
		BLPrpDagent blPrpDagent = new BLPrpDagent();
		if ("g".equals(businessNature)) {
			if (usercode.equals(ConstantCodes.TOP_USERCODE)) {
				conditions += strQueryConditionBak + " AND AgentType in ('1','2','3','4','5')";
			} else {
				conditions += " AND AgentType in ('1','2','3','4','5')" + " AND ( " + " ComCode IN(select comcode from prpdcompany " + " Start With  comcode = '" + comcodeGk + "' " + " Connect by prior uppercomcode=comcode "
						+ " and prior comcode!=uppercomcode) AND LowerViewFlag='Y' " + " )";
				strQueryConditionSub = strQueryConditionBak + "  AND AgentType in ('1','2','3','4','5')" + " AND  ComCode = '" + comcodeGk + "' AND LowerViewFlag='N'";
			}
		} else if (flag.indexOf(businessNature) > -1) {
			if (usercode.equals(ConstantCodes.TOP_USERCODE)) {
				conditions = strQueryConditionBak + " AND AgentType in ('" + businessNature + "','9')";
				conditions += "and " + getCondition(codeMethod, "AgentCode", fieldValue);
			} else {
				conditions = strQueryConditionBak + " AND AgentType in ('" + businessNature + "','9')";
				conditions += "and " + getCondition(codeMethod, "AgentCode", fieldValue) + " AND ( " + " ComCode IN(select comcode from prpdcompany " + " Start With  comcode = '" + comcodeGk + "' " + " Connect by prior uppercomcode=comcode "
						+ " and prior comcode!=uppercomcode) AND LowerViewFlag='Y' " + " )";
				strQueryConditionSub = strQueryConditionBak + " AND AgentType in ('" + businessNature + "','9')";
				strQueryConditionSub += " and " + getCondition(codeMethod, "AgentCode", fieldValue) + " AND ComCode  = '" + comcodeGk + "'  AND LowerViewFlag='N' ";
			}
		} else {
			if (usercode.equals(ConstantCodes.TOP_USERCODE)) {
				conditions = strQueryConditionBak + " AND AgentType ='" + businessNature + "'";
			} else {
				conditions = strQueryConditionBak + " AND AgentType ='" + businessNature + "'" + " AND (" + " ComCode IN(select comcode from prpdcompany " + " Start With  comcode = '" + comcodeGk + "' " + " Connect by prior uppercomcode=comcode "
						+ " and prior comcode!=uppercomcode) AND LowerViewFlag='Y' " + " ) ";
				strQueryConditionSub = strQueryConditionBak + " AND AgentType ='" + businessNature + "'" + " AND ComCode  = '" + comcodeGk + "'  AND LowerViewFlag='N' ";
			}
		}
		conditions += "  and " + getCondition(codeMethod, "AGENTCODE", fieldValue);
		blPrpDagent.query(conditions, 200, strQueryConditionSub);
		int intSize = blPrpDagent.getSize();
		for (int i = 0; i < intSize; i++) {
			codeValues.add(blPrpDagent.getArr(i).getAgentCode() + IConstants.FIELD_SEPARATOR + blPrpDagent.getArr(i).getAgentName());
			codeLabels.add(blPrpDagent.getArr(i).getAgentCode() + "--" + blPrpDagent.getArr(i).getAgentName());
		}

		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void btSelectAgentProtocol(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		String conditions = " ";
		DateTime dateTime = DateTime.current();
		String strDateTimes = (dateTime.toString().substring(0, 10));

		String riskCode, agentCode;
		riskCode = otherCondition.split("@")[0];
		agentCode = otherCondition.split("@")[1];

		conditions = " AgreementNo IN (SELECT AgreementNo FROM PrpDagreement WHERE StartDate<=TO_DATE('" + strDateTimes + "','yyyy-mm-dd') AND EndDate>=TO_DATE('" + strDateTimes + "','yyyy-mm-dd') AND ValidStatus='1')" + " AND AgentCode='"
				+ agentCode + "'" + " AND RiskCode='" + riskCode + "'";
		conditions += " and " + getCondition(codeMethod, "AgreementNo", fieldValue);
		BLPrpDagreeDetailFacade facade = new BLPrpDagreeDetailFacade();
		Collection<?> result = facade.findByConditions(conditions);
		for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
			PrpDagreeDetailDto element = (PrpDagreeDetailDto) iter.next();
			codeValues.add(element.getAgreementNo().trim());
			codeLabels.add(element.getAgreementNo().trim());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	public void queryCountryCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "CodeCode", fieldValue);
		conditions = conditions + "and codetype = 'CountryCode' order by CodeCode ";
		List<PrpDcode> result = prpDcodeService.findByConditions(conditions);
		for (Iterator<PrpDcode> iter = result.iterator(); iter.hasNext();) {
			PrpDcode prpDcode = (PrpDcode) iter.next();
			codeValues.add(prpDcode.getId().getCodeCode() + IConstants.FIELD_SEPARATOR + prpDcode.getCodeCName());
			codeLabels.add(prpDcode.getId().getCodeCode() + "--" + prpDcode.getCodeCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	private void queryPortCountryCode(HttpServletRequest request, HttpServletResponse response, String codeType) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = " ";
		conditions = getCondition(codeMethod, "codecode", fieldValue);
		conditions += " AND codetype ='CountryCode' ";
		conditions += " AND validstatus='1' order by codecode";
		List<PrpDcode> result = prpDcodeService.findByConditions(conditions);
		for (Iterator<PrpDcode> iter = result.iterator(); iter.hasNext();) {
			PrpDcode element = iter.next();
			codeValues.add(element.getId().getCodeCode() + IConstants.FIELD_SEPARATOR + element.getCodeCName() + IConstants.FIELD_SEPARATOR + element.getCodeEName());
			codeLabels.add(element.getId().getCodeCode() + "--" + element.getCodeCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	// groupNatrueDetail的查询
	private void GroupNatureDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String otherCondition = paramUtils.getParameter("otherCondition");
		String conditions = " ";
		conditions = getCondition(codeMethod, "codecode", fieldValue);
		conditions += " AND validstatus='1' and codetype = 'BusinessNature' AND flag like '%" + otherCondition + "%'";
		List<PrpDcode> result = prpDcodeService.findByConditions(conditions);
		for (Iterator<PrpDcode> iter = result.iterator(); iter.hasNext();) {
			PrpDcode element = iter.next();
			codeValues.add(element.getId().getCodeCode() + IConstants.FIELD_SEPARATOR + element.getCodeCName());
			codeLabels.add(element.getId().getCodeCode() + "--" + element.getCodeCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	// 新增groutNature的查询
	private void GroupNature(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		ArrayList<String> codeValues = new ArrayList<String>();
		ArrayList<String> codeLabels = new ArrayList<String>();
		String codeMethod = paramUtils.getParameter("codeMethod");
		String fieldValue = paramUtils.getParameter("fieldValue");
		String conditions = "";
		conditions = getCondition(codeMethod, "codecode", fieldValue);
		conditions += " And validstatus='1' and codetype= 'ChannelType'";
		List<PrpDcode> result = prpDcodeService.findByConditions(conditions);
		for (Iterator<PrpDcode> iter = result.iterator(); iter.hasNext();) {
			PrpDcode element = iter.next();
			codeValues.add(element.getId().getCodeCode() + IConstants.FIELD_SEPARATOR + element.getCodeCName());
			codeLabels.add(element.getId().getCodeCode() + "--" + element.getCodeCName());
		}
		request.setAttribute("codeValues", codeValues.toArray());
		request.setAttribute("codeLabels", codeLabels.toArray());
	}

	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}

	public PrpDclassService getPrpDclassService() {
		return prpDclassService;
	}

	public void setPrpDclassService(PrpDclassService prpDclassService) {
		this.prpDclassService = prpDclassService;
	}

	public PrpDkindService getPrpDkindService() {
		return prpDkindService;
	}

	public void setPrpDkindService(PrpDkindService prpDkindService) {
		this.prpDkindService = prpDkindService;
	}

	public PrpDcodeRiskService getPrpDcodeRiskService() {
		return prpDcodeRiskService;
	}

	public void setPrpDcodeRiskService(PrpDcodeRiskService prpDcodeRiskService) {
		this.prpDcodeRiskService = prpDcodeRiskService;
	}

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	public PrpDagentService getPrpDagentService() {
		return prpDagentService;
	}

	public void setPrpDagentService(PrpDagentService prpDagentService) {
		this.prpDagentService = prpDagentService;
	}

	public UtiUserGradeService getUtiUserGradeService() {
		return utiUserGradeService;
	}

	public void setUtiUserGradeService(UtiUserGradeService utiUserGradeService) {
		this.utiUserGradeService = utiUserGradeService;
	}

	public SwfModelUseService getSwfModelUseService() {
		return swfModelUseService;
	}

	public void setSwfModelUseService(SwfModelUseService swfModelUseService) {
		this.swfModelUseService = swfModelUseService;
	}

}