<!--
****************************************************************************
* DESC       ??¡À¡§¡ã???????¡À¨ª????
* AUTHOR     ??
* CREATEDATE ??2003-03-12
* MODIFYLIST ??   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@page errorPage="/UIErrorPage"%>
<!-- ????bean?¨¤??¡¤? -->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.utility.string.*"%>
<%@page import="com.sinosoft.claim.util.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.UICodeAction"%>
<html>
<head>
<title>¡À¡§¡ã??¨¦????¡À¨ª</title>
<!-- ???????? -->
<script src="/ddccallweb/common/pub/UICommon.js"></script>
<!-- ?????¨´??  -->
<link rel="stylesheet" type="text/css" href="/ddccallweb/css/Standard.css">
</head>
<body>
	<form name="fm">
		<table class="common" align=center cellpadding="5" cellspacing="1">
			<tr class=listtitle>
				<td>¡À¡§¡ã???</td>
				<td>¡À?????</td>
				<td>??¡Á¡Â?¡À</td>
				<td>?????¡À??</td>
			</tr>
			<%
				String trClass = "";
				String strRiskCode = (String) session.getValue("RiskCode");
				String strLink = "";
				String strCondition = "";
				String strRegistNo = "";
				String strPolicyNo = "";
				int i = 0;
				BLPrpLregist blPrpLregist = new BLPrpLregist();
				PrpLregistSchema prpLregistSchema = new PrpLregistSchema();
				/* modify for utipower begin*/
				//  UtiPower utiPower = (UtiPower)session.getValue("Power");
				/* modify for utipower end*/

				BLPrpDuser blPrpDuser = new BLPrpDuser();

				UICodeAction uiCodeAction = new UICodeAction();
				String strRiskType = uiCodeAction.translateRiskCodetoRiskType(strRiskCode);

				/*???¨¦??????*/

				strCondition = " 1=1 " + Str.convertString("RegistNo", request.getParameter("RegistNo"), request.getParameter("RegistNoSign")) + Str.convertString("PolicyNo", request.getParameter("PolicyNo"), request.getParameter("PolicyNoSign"));

				if ("D".equals(strRiskType)) {
					strCondition += " AND (CancelDate IS NULL OR CancelDate = '')";
					strCondition += " AND (DealerCode IS NULL OR DealerCode = '')";
					strCondition += " AND registno NOT IN (SELECT DISTINCT registno FROM prplcheck)";
				}

				strCondition += " AND registno NOT IN (SELECT DISTINCT registno FROM prplclaim)";

				//???¨°???????¡§??????
				String PowerUserCode = (String) session.getValue("UserCode"); //???¡ì
				String PowerRiskCode = (String) session.getValue("RiskCode"); //????
				String PowerTaskCode = SysConst.getProperty("TASKCODE_LPBA"); //?????¨²??
				String PowerTableName = "PrpLregist"; //?¡§??¡À¨ª??

				UtiPower utiPower = new UtiPower();
				strCondition = strCondition + utiPower.addPower(PowerUserCode, PowerRiskCode, PowerTaskCode, PowerTableName); //?????¡§???¨¦??????


				strRiskCode = (String) session.getValue("RiskCode");

				//???¨¦???¡À?¡ã????????????
				strCondition += " AND riskcode ='" + strRiskCode + "'";

				blPrpLregist.query(strCondition);

				for (i = 0; i < blPrpLregist.getSize(); i++) {
					prpLregistSchema = blPrpLregist.getArr(i);
					if (i % 2 == 0) {
						trClass = "listodd";
					} else {
						trClass = "listeven";
					}

					strRegistNo = prpLregistSchema.getRegistNo();
					strRiskCode = prpLregistSchema.getRiskCode();

					//??¡¤?????????????¡À¨¤??????
					if ("D".equals(strRiskType))
						strRiskCode = BusinessRuleUtil.getOuterCode(request, "RISKCODE_DAA");

					strLink = "/ddccallweb/" + strRiskCode + "/lp/regist/UIL" + strRiskCode + "RegistInput.jsp?" + SysConst.getProperty("EDITTYPE") + "=" + SysConst.getProperty("EDITTYPE_UPDATE") + "&RegistNo=" + strRegistNo;
			%>
			<tr class="common">
				<td>
					<a href="<%=strLink%>"><%=strRegistNo%></a>
				</td>
				<td><%=prpLregistSchema.getPolicyNo()%></td>
				<td><%=blPrpDuser.translateCode(prpLregistSchema.getOperatorCode(), true)%></td>
				<td><%=prpLregistSchema.getInputDate()%></td>
			</tr>
			<%
				}
			%>
			<tr>
				<td colspan=5 class=input align=center>
					???¨¦????
					<%=i%>
					???¨²¡Á???????????
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
