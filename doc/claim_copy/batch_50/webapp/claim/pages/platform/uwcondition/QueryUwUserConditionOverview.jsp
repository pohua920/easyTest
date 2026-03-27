<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-platform.tld" prefix="app" %>
<html>
<head>
	<jsp:include page="/platform/uwcondition/StaticJavascript.jsp" />
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link href="/claim/platform/css/Standard.css" rel="stylesheet" type="text/css">
	<script src="/claim/platform/uwcondition/js/uwcondition1.js"></script>
</head>
<body  onload="initPage();">
<html:form action="/processUwUserCondition.do">
<input type="hidden" name="actionType" value="<%=request.getParameter("actionType")%>">
<table class="common" cellpadding="1" cellspacing="1" align="center">
		<tr>
			<td colspan="9" align="center" class="top"><s:text name="uwcondition.QueryDoubleCondition"/></td><%-- 查询双核条件 --%>
		</tr>
		<tr>
			<td width="10%" class="page"><s:text name="uwcondition.TypeAuditing"/>：</td> <%-- 审核类型 --%>
			<td width="10%" class="page">
				<input name="uwTypeQuery" type="text" class="codecode" style="width:60px;"
				       value='<bean:write name="UserConditionQueryDto" property="uwType"/>'
							 ondblclick="code_CodeQuery(this,'UwType','0,1','Y');"
							 onkeyup="code_CodeQuery(this,'UwType','0,1','Y');"
							 onchange="code_CodeChange(this,'UwType','0,1','Y');">
			</td>
			<td width="13%" class="page">
				<input name="uwTypeNameQuery" type="text" class="codename" 
				       value='<bean:write name="UserConditionQueryDto" property="uwTypeName"/>' readonly>
			</td>
			<td width="10%" class="page"><s:text name="uwcondition.AuditDepartment"/>：</td><%-- 审核部门 --%>
			<td width="10%" class="page">
				<input name="comCodeQuery" type="text" class="codecode" style="width:60px;"
				       value='<bean:write name="UserConditionQueryDto" property="comCode"/>'
					   ondblclick="code_CodeQuery(this,'comCode','0,1','Y');"
					   onkeyup="code_CodeQuery(this,'comCode','0,1','Y');"
					   onchange="code_CodeChange(this,'comCode','0,1','Y');">
			</td>
			<td width="14%" class="page">
				<input name="comNameQuery" type="text" class="codename"
				       value='<bean:write name="UserConditionQueryDto" property="comName"/>' readonly>
			</td>
			<td width="10%" class="page"><s:text name="uwcondition.InsuranceCategories"/>：</td><%-- 险种大类 --%>
			<td width="10%" class="page">
			  <input name="riskCategoryCodeQuery" type="text" class="codecode" style="width:60px;"
				       value='<bean:write name="UserConditionQueryDto" property="riskCategoryCode"/>'
							 ondblclick="code_CodeQuery(this,'RiskCategory','0,1','Y');"
					     onkeyup="code_CodeQuery(this,'RiskCategory','0,1','Y');"
					     onchange="code_CodeChange(this,'RiskCategory','0,1','Y');">
			</td>
			<td width="13%" class="page">
				<input name="riskCategoryNameQuery" type="text" class="codename" readonly 
				       value='<bean:write name="UserConditionQueryDto" property="riskCategoryName"/>'>
			</td>
		</tr>
		<tr>
			<td width="10%" class="page"><s:text name="archive.riskClass"/>：</td><%-- 险类 --%>
			<td width="10%" class="page">
				 <input name="classCodeQuery" type="text" class="codecode" style="width:60px;"
				        value='<bean:write name="UserConditionQueryDto" property="classCode"/>'
                ondblclick="code_CodeQuery(this,'classCodeByRiskCategory','0,1','Y', fm.riskCategoryCodeQuery.value);"
                onkeyup="code_CodeQuery(this,'classCodeByRiskCategory','0,1','Y', fm.riskCategoryCodeQuery.value);"
								onchange="code_CodeChange(this,'classCodeByRiskCategory','0,1','Y', fm.riskCategoryCodeQuery.value);">
			</td>
			<td width="13%" class="page">
				<input name="classNameQuery" type="text" class="codename" readonly 
				       value='<bean:write name="UserConditionQueryDto" property="className"/>'>
			</td>
			<td width="10%" class="page"><s:text name="regist.prpLregist.riskCodeName"/>：</td><%-- 险种 --%>
			<td width="24%" class="page" colspan="2">
			  <input name="riskCodeQuery" type="text" class="codecode" style="width:150px;"
				       value='<bean:write name="UserConditionQueryDto" property="riskCode"/>'
							 ondblclick="code_CodeQuery(this,'riskcodeByClassCode','0,1','Y', addCondition('prpDriskClassCode',fm.classCodeQuery.value));"
					     onkeyup="code_CodeQuery(this,'riskcodeByClassCode','0,1','Y', addCondition('prpDriskClassCode',fm.classCodeQuery.value));"
					     onchange="code_CodeChange(this,'riskcodeByClassCode','0,1','Y', addCondition('prpDriskClassCode',fm.classCodeQuery.value));">
			</td>
			<td width="33%" class="page" colspan="3">
				<input name="riskNameQuery" type="text" class="codename" readonly 
				       value='<bean:write name="UserConditionQueryDto" property="riskName"/>'>
			</td>
		</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr align="center">
		<td>
			<input type="button" class="button" value="<s:text name='button.query.value'/>" onclick="doQuery();"> <%-- 查 询 --%>
		</td>
	</tr>
</table>
<app:claimPlatFromCodeInput/>
&nbsp;
<html:hidden property="pageNo"/>
<html:hidden property="rowsCount"/>
<html:hidden property="rowsPerPage"/>
<table class="common" cellpadding="1" cellspacing="1" align="center">
	<tr>
		<td colspan="7" align="center" class="top"><s:text name="uwcondition.DoubleQueryResults"/></td> <%-- 双核条件查询结果 --%>
	</tr>
	<tr>
		<td class="top" style="text-align:left">&nbsp;</td>
		<td class="top"><s:text name="uwcondition.TypeAuditing"/></td> <%-- 审核类型 --%>
		<td class="top"><s:text name="archive.riskClass"/></td><%-- 险类 --%>
		<td class="top"><s:text name="regist.prpLregist.riskCodeName"/></td> <%-- 险种 --%>
		<td class="top"><s:text name="uwcondition.AuditDepartment"/></td> <%-- 审核部门 --%>
		<td class="top"><s:text name="uwcondition.Template"/></td><%-- 模板 --%>
		<td class="top"><s:text name="query.ifUsefull"/></td><%-- 是否有效 --%>
	</tr>
<%int index=0;%>
<logic:present name="conditionList">
<logic:iterate id="iterateDto" name="conditionList">
	<input type="hidden" name="uwType" value='<bean:write name="iterateDto" property="uwType"/>'>
	<input type="hidden" name="uwTypeName" value='<bean:write name="iterateDto" property="uwTypeName"/>'>
	<input type="hidden" name="classCode" value='<bean:write name="iterateDto" property="classCode"/>'>
	<input type="hidden" name="className" value='<bean:write name="iterateDto" property="className"/>'>
	<input type="hidden" name="riskCode" value='<bean:write name="iterateDto" property="riskCode"/>'>
	<input type="hidden" name="comCode" value='<bean:write name="iterateDto" property="comCode"/>'>
	<input type="hidden" name="comName" value='<bean:write name="iterateDto" property="comName"/>'>
	<input type="hidden" name="modelNo" value='<bean:write name="iterateDto" property="modelNo"/>'>
	<input type="hidden" name="modelName" value='<bean:write name="iterateDto" property="modelName"/>'>
	<input type="hidden" name="remark" value='<bean:write name="iterateDto" property="remark"/>'>
	<input type="hidden" name="createTime" value='<bean:write name="iterateDto" property="createTime"/>'>
	<input type="hidden" name="validStatus" value='<bean:write name="iterateDto" property="validStatus"/>'>
	<tr>
		<td align="left" class="page">
			<input type=radio name=checkboxSelect value="<%=index++%>">
		</td>
		<td align="center" class="page">
				<bean:write name="iterateDto" property="uwType"/>-<bean:write name="iterateDto" property="uwTypeName"/>
		</td>
		<td align="center" class="page">
			<bean:write name="iterateDto" property="classCode"/>-<bean:write name="iterateDto" property="className"/>
		</td>
		<td align="center" class="page" width="18%">
			<bean:write name="iterateDto" property="riskCode"/>
		</td>
		<td align="center" class="page">
			<bean:write name="iterateDto" property="comCode"/>-<bean:write name="iterateDto" property="comName"/>
		</td>
		<td align="center" class="page">
			<bean:write name="iterateDto" property="modelNo"/>-<bean:write name="iterateDto" property="modelName"/>
		</td>
		<td align="center" class="page">
			<bean:write name="iterateDto" property="validStatus"/>-<bean:write name="iterateDto" property="validStatusName"/>
		</td>
	</tr>
</logic:iterate>
</logic:present>
	<tr>
		<td colspan="7" align="center" class="page">
			<app:navigate name="fm" objectName="fm"/>
		</td>
	</tr>
	<tr>
		<td colspan="7" align="center" class="page">
			<app:command name="fm" objectName="UwUserCondition" action="update" path="system"/>
		</td>
	</tr>
</table>
</html:form>
<script language="javascript">
	function doQuery()
	{
		fm.actionType.value = "query";
		fm.submit();
	}
</script>
</body>
</html>
