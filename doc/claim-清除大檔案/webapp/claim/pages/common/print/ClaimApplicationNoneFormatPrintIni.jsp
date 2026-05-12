<%--
****************************************************************************
* DESC       ：非车险理赔申请书打印页面
* AUTHOR     ：罗畅
* CREATEDATE ：2010-05-27
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>

<%-- 引入bean类部分 --%>
<%@page import="java.text.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%> 
<%@page import="com.sinosoft.claim.util.*"%> 
<%@page import="java.util.Vector"%> 
<%@page import="com.sinosoft.sysframework.common.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%> 
<%@page import="com.sinosoft.sysframework.exceptionlog.*"%> 
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.sinosoft.claim.bl.facade.BLPrpDuserFacade" %>
<%@ page import="com.sinosoft.claim.bl.facade.BLPrpDcompanyFacade" %>
<%@ page import="com.sinosoft.prpall.dbsvr.cb.DBPrpCmainCargoSub" %>
<%@ page import="com.sinosoft.prpall.schema.PrpCmainCargoSubSchema" %>
<%
	//变量声明部分
	String RiskCode = "";//险种代码
	String strRiskName = "";//险种名称
	String strClaimNo = "";//赔案号
	String strPolicyNo = "";//保单号
	String strInsuredName = "";//被保险人名称
	String strInsuredAddress = "";//被保险人地址
	String strInsuredPhoneNumber = "";//被保险人电话
	String strInsuredDate = "";//保险期限
	String strLinkerName = "";//联系人名称
	String strLinkerAddress = "";//联系人地址
	String strLinkerPhoneNumber = "";//联系人电话
	String strDamageStartDate = "";//出险日期
	String strDamageName = "";//出险原因
	String strDamageAddress = "";//出现地址
	String strEstimateLoss = "";//报损金额
	String strComAddress = "";//公司地址
	String strComPhoneNumber = "";//公司电话
	String strFaxNumber = "";//传真
	String strInvoiceNo = "";//提单号
	String strStartSiteName = "";//起运地
	String strEndSiteName = "";//目的地
	String strItemDetailName = "";//货物名称
	String strBlno = "";//运输工具
	String strOPcode = "";//OP单号
	String strHandlerName = "";//经办人名称

	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");

	RiskCode = claimDto.getPrpLclaimDto().getRiskCode();
	strClaimNo = claimDto.getPrpLclaimDto().getClaimNo();
	strPolicyNo = claimDto.getPrpLclaimDto().getPolicyNo();
	strInsuredName = claimDto.getPrpLclaimDto().getInsuredName();
	strInsuredAddress = registDto.getPrpLregistDto().getInsuredAddress();
	if (policyDto.getPrpCinsuredDtoList().size() > 0) {
		Iterator it = policyDto.getPrpCinsuredDtoList().iterator();
		while (it.hasNext()) {
			PrpCinsuredDto prpCinsuredDto = (PrpCinsuredDto) it.next();
			if (prpCinsuredDto.getInsuredCode().equals(claimDto.getPrpLclaimDto().getInsuredCode())) {
				strInsuredPhoneNumber = prpCinsuredDto.getPhoneNumber();
				break;
			}
		}
	}

	strInsuredDate = "自 " + claimDto.getPrpLclaimDto().getStartDate().getYear() + "年" + claimDto.getPrpLclaimDto().getStartDate().getMonth() + "月" + claimDto.getPrpLclaimDto().getStartDate().getDate() + "日 零时起" + "至 " + policyDto.getPrpCmainDto().getEndDate().getYear() + "年" + policyDto.getPrpCmainDto().getEndDate().getMonth() + "月" + policyDto.getPrpCmainDto().getEndDate().getDate()
			+ "日 二十四时止";
	strLinkerName = registDto.getPrpLregistDto().getLinkerName();
	strLinkerAddress = registDto.getPrpLregistDto().getLinkerAddress();
	strLinkerPhoneNumber = registDto.getPrpLregistDto().getPhoneNumber();
	strDamageStartDate = claimDto.getPrpLclaimDto().getDamageStartDate().getYear() + "年" + claimDto.getPrpLclaimDto().getDamageStartDate().getMonth() + "月" + claimDto.getPrpLclaimDto().getDamageStartDate().getDate() + "日" + claimDto.getPrpLclaimDto().getDamageEndHour().toString().substring(0, 2) + "时" + claimDto.getPrpLclaimDto().getDamageEndHour().toString().substring(3, 5) + "分";
	strDamageName = claimDto.getPrpLclaimDto().getDamageName();
	strDamageAddress = claimDto.getPrpLclaimDto().getDamageAddress();
	strEstimateLoss = registDto.getPrpLregistDto().getEstiCurrency() + "  " + registDto.getPrpLregistDto().getEstimateLoss();

	//货运险特殊字段
	if (RiskCode.substring(0, 2).equals("09") || RiskCode.substring(0, 2).equals("10")) {
		if (RiskCode.substring(0, 2).equals("10")) {
			strBlno = policyDto.getPrpCmainCargoDto().getBLNo();
			strInvoiceNo = policyDto.getPrpCmainCargoDto().getLadingNo();
			strStartSiteName = policyDto.getPrpCmainCargoDto().getStartSiteName();
			strEndSiteName = policyDto.getPrpCmainCargoDto().getEndSiteName();
		} else if (RiskCode.substring(0, 2).equals("09")) {
			strInvoiceNo = policyDto.getPrpCmainCargoDto().getShipNoteNo();
			DBPrpCmainCargoSub dbPrpCmainCargoSub = new DBPrpCmainCargoSub();
			strEndSiteName = policyDto.getPrpCmainCargoDto().getEndDetailName();
			Vector vector = dbPrpCmainCargoSub.findByConditions("select * from prpcmaincargosub where policyno = '" + strPolicyNo + "' and serialno = 1");
			if (vector.size() > 0) {
				Iterator it = vector.iterator();
				while (it.hasNext()) {
					PrpCmainCargoSubSchema prpCmainCargoSubSchema = (PrpCmainCargoSubSchema) it.next();
					strStartSiteName = prpCmainCargoSubSchema.getSiteName();
					strBlno = prpCmainCargoSubSchema.getConveyance();
					break;
				}
			}
		}

		if (policyDto.getPrpCitemKindDtoList().size() > 0) {
			Iterator it = policyDto.getPrpCitemKindDtoList().iterator();
			while (it.hasNext()) {
				PrpCitemKindDto prpCitemKindDto = (PrpCitemKindDto) it.next();
				strItemDetailName += prpCitemKindDto.getItemDetailName() + "  ";
			}
		}
		if (policyDto.getPrpCmainSubDtoList().size() > 0) {
			Iterator it = policyDto.getPrpCmainSubDtoList().iterator();
			while (it.hasNext()) {
				PrpCmainSubDto prpCmainSubDto = (PrpCmainSubDto) it.next();
				if (!prpCmainSubDto.getFlag().equals("")) {
					if (prpCmainSubDto.getFlag().substring(0, 1).equals("Y")) {
						strOPcode = prpCmainSubDto.getMainPolicyNo();
						break;
					}
				}
			}
		}
	}

	BLPrpDcompanyFacade blPrpDcompanyFacade = new BLPrpDcompanyFacade();
	UserDto userDto = (UserDto) session.getAttribute("user");
	String comcode = userDto.getComCode();
	PrpDcompanyDto prpDcompanyDto = blPrpDcompanyFacade.findByPrimaryKey(comcode);
	strComAddress = prpDcompanyDto.getAddressCName();
	strFaxNumber = prpDcompanyDto.getFaxNumber();
	strComPhoneNumber = prpDcompanyDto.getPhoneNumber();
	BLPrpDuserFacade blPrpDuserFacade = new BLPrpDuserFacade();
	strHandlerName = blPrpDuserFacade.findByPrimaryKey(claimDto.getPrpLclaimDto().getHandlerCode()).getUserName();

	UICodeAction uiCodeAction = new UICodeAction();
	strRiskName = uiCodeAction.translateRiskCode(RiskCode, true);
%>