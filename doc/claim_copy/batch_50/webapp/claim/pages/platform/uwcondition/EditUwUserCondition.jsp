<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/claim-platform.tld" prefix="app" %>
<HTML xmlns:mpc>
<HEAD>
<TITLE></TITLE>
	<jsp:include page="/platform/uwcondition/StaticJavascript.jsp" />
	<link href="/claim/platform/css/Standard.css" rel="stylesheet" type="text/css">
	<script src="/claim/platform/uwcondition/js/uwcondition1.js"></script>
	<script src="/claim/platform/uwcondition/js/uwcondition2.js"></script>
	<jsp:include page="/platform/behaviors/MpcStyle.jsp"/>
</HEAD>
<BODY BGCOLOR="#D7E1F6" ONLOAD="oMPC.style.visibility='visible'" style="scroll: no; overflow: hidden;">
<br/>
<form name="fm" action="" method="POST">
<input type="hidden" name="actionType" value="<%=request.getParameter("actionType")%>">
<input type="hidden" name="nodeNo" value="<%=request.getParameter("nodeNo")%>">
<input type="hidden" name="nodeName" value='<bean:write name="conditionDto" property="nodeName"/>'>
<input type="hidden" name="userCode" value='<bean:write name="conditionDto" property="userCode"/>'>

	<div id="Layer1" style="position:absolute; width:70px; height:22px; z-index:1; left:550px; top:2px;">
		<table border="0" cellpadding="0" cellspacing="1" class="common">
			<tr>
				<td><input type="button" name="save" value="<s:text name='button.save.value'/>" class="button" onclick="doUpdate();"></td><%-- 保 存 --%>
			</tr>
		</table>
	</div>
	<div id="Layer2" style="position:absolute; width:620px; height:450px; z-index:1; left:5px; top:30px;">
	<mpc:container ID="oMPC" STYLE="width:620px; height:450px; visibility:hidden;">
    <logic:equal name="simpleCount" value="1">
			<mpc:page ID="tab3" TABTITLE="" TABTEXT="簡單因子">
				<center>
					<div style="width:610px; height:445px; overflow:auto;">
						<jsp:include page="/platform/uwcondition/EditUwUserConditionInclude3.jsp"/>
					</div>
				</center>
			</mpc:page>
		</logic:equal>
		<logic:equal name="enumCount" value="1">
			<mpc:page ID="tab5" TABTITLE="" TABTEXT="枚舉因子">
				<center>
					<div style="width:610px; height:445px; overflow:auto;">
						<jsp:include page="/platform/uwcondition/EditUwUserConditionInclude4.jsp"/>
					</div>
				</center>
			</mpc:page>
		</logic:equal>
		<logic:equal name="comboCount" value="1">
			<mpc:page ID="tab4" TABTITLE="" TABTEXT="組合因子">
				<center>
					<div style="width:610px; height:445px; overflow:auto;">
						<jsp:include page="/platform/uwcondition/EditUwUserConditionInclude8.jsp"/>
					</div>
				</center>
			</mpc:page>
		</logic:equal>
  </mpc:container>
	</div>
<app:claimPlatFromCodeInput/>
</form>
<script language="javascript">
	 function prepareUpdate(actionType, nodeNo)
	 {
			fm.action = "/claim/processUwCondition.do?actionType=" + actionType +"&nodeNo=" + nodeNo;
			fm.submit();
	 }
	 function doUpdate()
	 {
			if(checkSimpleFactorValue() == false)
			{
				return;
			}
			if(checkComboFactorValue() == false)
			{
				return;
			}
			if(confirm("確實要保存嗎？"))
			{
				fm.action = "/claim/processUwUserCondition.do?actionType=update";
				fm.submit();
			}
	 }
</script>
</BODY>
</HTML>