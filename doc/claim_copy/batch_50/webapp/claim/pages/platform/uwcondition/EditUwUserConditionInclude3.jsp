<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-platform.tld" prefix="app" %>
<br>
<table class="common" cellpadding="5" cellspacing="1">
	<tr>
		<td colspan="8" align="center" class="top">
			<bean:write name="conditionDto" property="nodeName"/>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<bean:write name="conditionDto" property="userCode"/>
			-
			<bean:write name="conditionDto" property="userName"/>
		</td>
	</tr>
	<tr>
		<td class="top" width="15%"><s:text name="uwcondition.NameFactor"/></td> <%--因子名称  --%>
		<td class="top" width="15%"><s:text name="uwcondition.StaffValue"/></td> <%-- 人员值 --%>
		<td class="top" width="10%"><s:text name="uwcondition.LevelValue"/></td> <%-- 级别值 --%>
		<td class="top" width="10%"><s:text name="db.utiWorkflow.Note"/></td> <%-- 提示 --%>
		<td class="top" width="15%"><s:text name="uwcondition.NameFactor"/></td> <%--因子名称  --%>
		<td class="top" width="15%"><s:text name="uwcondition.StaffValue"/></td> <%-- 人员值 --%>
		<td class="top" width="10%"><s:text name="uwcondition.LevelValue"/></td> <%-- 级别值 --%>
		<td class="top" width="10%"><s:text name="db.utiWorkflow.Note"/></td> <%-- 提示 --%>
	</tr>
	<logic:present name="singleFactorList">
	<%int i=0, k=0;%>
	<logic:iterate id="factorDto" name="singleFactorList">
	<%if(i%2 == 0){%><tr><%}i++;%>
		<td class="page" width="15%">
			<bean:write name="factorDto" property="factorName"/>
		</td>
		<td class="page" width="15%">
			<input type="text" name="simpleFactorValue" class="common"
			       value='<bean:write name="factorDto" property="userValue"/>'>
		</td>
		<td class="page" width="10%">
			<bean:write name="factorDto" property="nodeValue"/>
			<input type="hidden" name="simpleFactorNodeValue"
			       value='<bean:write name="factorDto" property="nodeValue"/>'>
			<INPUT type="hidden" name="simpleFactorCode" value='<bean:write name="factorDto" property="factorCode"/>'>
			<INPUT type="hidden" name="simpleFactorName" value='<bean:write name="factorDto" property="factorName"/>'>
			<INPUT type="hidden" name="simpleFactorAttr" value='<bean:write name="factorDto" property="factorAttr"/>'>
			<INPUT type="hidden" name="simpleFactorValueDefault" 
			       value='<bean:write name="factorDto" property="exampleValue"/>'>
		</td>
		<td class="page" width="10%">
			<center><a onclick='openTipWindow(<%=k%>);' href="javascript: void(0);"><s:text name="db.utiWorkflow.Note"/>></a></center> <%-- 提示 --%>
		</td>
	<%k++; if(i%2 == 0){%></tr><%}%>
	</logic:iterate>
	<%if(i%2 != 0){%>
		<td class="page" width="15%"></td>
	  <td class="page" width="15%"></td>
		<td class="page" width="10%"></td>
		<td class="page" width="10%"></td>
	</tr><%}%>
	</logic:present>
</table>
<jsp:include page="/platform/uwcondition/FactorTipJsInclude.jsp"/>