<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-platform.tld" prefix="app" %>
<br>
<table class="common" cellpadding="5" cellspacing="1" align="center">
	<tr>
		<td colspan="8" align="center" class="top">
			<bean:write name="conditionDto" property="nodeName"/>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<bean:write name="conditionDto" property="userCode"/>
			-
			<bean:write name="conditionDto" property="userName"/>
		</td>
	</tr>
</table>
<table class="common" cellpadding="0" cellspacing="0" align="center">
	<logic:present name="enumFactorList">
	<%int i=0;%>
	<logic:iterate id="factorDto" name="enumFactorList">
	<%if(i%2 == 0){%><tr><%}i++;%>
		<td width="50%">
			<logic:present name="factorDto" property="enumCodeList">
			<table class="common" cellpadding="3" cellspacing="1">
				<tr>
					<td class="top"><bean:write name="factorDto" property="factorName"/></td>
					<INPUT type="hidden" name="enumfactorFactorCode" 
					       value='<bean:write name="factorDto" property="factorCode"/>'>
				</tr>
			</table>
			<div style="height:250px;overflow:auto;">
			<table class="common" cellpadding="2" cellspacing="1" align="top">
			<%int index=0;%>
			<logic:iterate id="codeDto" name="factorDto" property="enumCodeList">
				<tr>
					<td class="page">
						<input type="checkbox" name="enumfactorCheckbox" 
						       value='<bean:write name="factorDto" property="factorCode"/>,<bean:write name="codeDto" property="codeCode"/>'
									 <bean:write name="codeDto" property="checked"/>>
						<bean:write name="codeDto" property="codeCode"/> - <bean:write name="codeDto" property="codeName"/>
					</td>
				</tr>
			</logic:iterate>
			</table>
			</div>
			</logic:present>
		</td>
	<%if(i%2 == 0){%></tr><%}%>
	</logic:iterate>
	<%if(i%2 != 0){%><td width="50%"></td></tr><%}%>
	</logic:present>
</table>