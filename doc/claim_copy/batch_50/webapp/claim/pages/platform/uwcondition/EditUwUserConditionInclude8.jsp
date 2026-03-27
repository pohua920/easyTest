<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<br>
<!------------------------------------------------------------------------------------------------------------>
<table class="common" cellpadding="5" cellspacing="1" align="center">
	<tr>
		<td class="top">
			<bean:write name="conditionDto" property="nodeName"/>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<bean:write name="conditionDto" property="userCode"/>
			-
			<bean:write name="conditionDto" property="userName"/>
		</td>
	</tr>
</table>
<logic:present name="comboFactorList">
<%int tableId=0;%>
<logic:iterate id="utiUwFactorDto" name="comboFactorList">
	<span style="display:none">
		<%int col1 = 0, col2 = 0;%>
		<logic:present name="utiUwFactorDto" property="utiUwComboFactorList">
		<logic:iterate id="comboFactorDto" name="utiUwFactorDto" property="utiUwComboFactorList">
			<%col1++; col2++;%>
		</logic:iterate>
		</logic:present>
		<%
			int tdWidth = (int)((600-80-80-55)/col1);
			int codeWidth = (int)(tdWidth * 0.3);
			int nameWidth = (int)(tdWidth * 0.6);
		%>
		<table class="common" style="display:none" id="Combo<%=tableId%>_Data" cellspacing="1" cellpadding="1">
			<tbody>
				<tr>
				    <td class="page" width="30px;">
				        <p align="center">
						<select name="utiUwConditionFactorCodeValue" class="three" >   
	                      <option value="Value"><s:text name="uwcondition.Details"/></option><%-- 详细 --%>
	                      <option value="OtherValue" ><s:text name="certainLoss.other"/></option><%-- 其它 --%>
	                    </select>
	                    </p>
					</td>
					<logic:present name="utiUwFactorDto" property="utiUwComboFactorList">
					<logic:iterate id="comboFactorDto" name="utiUwFactorDto" property="utiUwComboFactorList">
						<td class="page" width="<%=tdWidth%>px;">
							<INPUT type="hidden" name="combofactorFactorCode" 
							value='<bean:write name="utiUwFactorDto" property="factorCode"/>'>
							<INPUT type="hidden" name="combofactorCodeType"
							value='<bean:write name="utiUwFactorDto" property="factorCode"/>,<bean:write name="comboFactorDto" property="codeType"/>'>
							<input type="text" name="combofactorCodeCode" class="codecode" style="width:<%=codeWidth%>px;" value=""
						  ondblclick="code_CodeQuery(this, 
							'combofactorcodetype=<bean:write name="comboFactorDto" property="codeType"/>','0,1','Y',
							'<bean:write name="utiUwFactorDto" property="classCode"/>');">
							<input name="typeName" type="text" class="codename" value="" style="width:<%=nameWidth%>px;" readonly>
						</td>
					</logic:iterate>
					</logic:present>
					<td class="page" width="55px">
						<input type="text" class="common" name="combofactorFactorValue">
						<input type="text" class="common" name="combofactorFactorNodeValue">
						<INPUT type="hidden" name="combofactorFactorName" 
						       value='<bean:write name="utiUwFactorDto" property="factorName"/>'>
						<INPUT type="hidden" name="combofactorFactorAttr" 
						       value='<bean:write name="utiUwFactorDto" property="factorAttr"/>'>
					</td>
					<td class="page" width="55px">
						<bean:write name="utiUwFactorDto" property="exampleValue"/>
						<INPUT type="hidden" name="comboFactorDefaultValue" 
						       value='<bean:write name="utiUwFactorDto" property="factorCode"/>,<bean:write name="utiUwFactorDto" property="exampleValue"/>'>
					</td>
					<td class="page" width="25px">
						<input type=button class="smallbutton" name="btnDelCombo<%=tableId%>"
									 onclick="deleteRow(this,'Combo<%=tableId%>');" value="-" style="cursor: hand">
					</td>
				</tr>
			</tbody>
						<INPUT type="hidden" name="combofactorFactorCols" 
						       value='<bean:write name="utiUwFactorDto" property="factorCode"/>,<%=col1%>'>
		</table>
	</span>

	<table id="Combo<%=tableId%>" border="0" cellpadding="1" cellspacing="1" class="common">
		<thead>
			<tr>
				<td class="top" colspan="<%=col1+4%>"><bean:write name="utiUwFactorDto" property="factorName"/></td>
			</tr>
			<tr>
			    <td class="top" width="30px"></td>
				<logic:present name="utiUwFactorDto" property="utiUwComboFactorList">
				<logic:iterate id="comboFactorDto" name="utiUwFactorDto" property="utiUwComboFactorList">
					<td class="top" width="<%=tdWidth%>px;">
						<bean:write name="comboFactorDto" property="typeName"/>
					</td>
				</logic:iterate>
				</logic:present>
				<td class="top" width="80px"><s:text name="uwcondition.StaffNo"/></td><%-- 人员权限值 --%>
				<td class="top" width="80px"><s:text name="uwcondition.LevelNo"/></td> <%-- 级别权限值 --%>
				<td class="top" width="55px"><s:text name="uwcondition.Example"/></td> <%-- 示例 --%>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<%for(;col2>0;col2--){%><td class="page" width="<%=tdWidth%>px;">&nbsp;</td><%}%>
				<td class="page" width="80px">&nbsp;</td>
				<td class="page" width="80px">&nbsp;</td>
				<td class="page" width="55px">&nbsp;</td>
			</tr>
		</tfoot>
		<logic:present name="utiUwFactorDto" property="comboRowList">
		<logic:iterate id="comboRowDto" name="utiUwFactorDto" property="comboRowList">
			<tr>
			        <td class="page" width="30">
                       <logic:equal name="comboRowDto" property="utiUwConditionFactorCodeValue" value="OtherValue">
                       <input type="text" name="utiUwConditionFactorCodeValue" value="<s:text name='certainLoss.other'/>" size="4" readonly><%-- 其它 --%>
                       </logic:equal>
                       <logic:notEqual name="comboRowDto" property="utiUwConditionFactorCodeValue" value="OtherValue">
                       	<input type="text" name="utiUwConditionFactorCodeValue" value="<s:text name='uwcondition.Details'/>" size="4" readonly><%-- 详细 --%>
                       </logic:notEqual>
					</td>
				<logic:present name="comboRowDto" property="cellList">
				<logic:iterate id="cellDto" name="comboRowDto" property="cellList">
					<td class="page" width="<%=tdWidth%>px;">
						<INPUT type="hidden" name="combofactorFactorCode" 
						value='<bean:write name="utiUwFactorDto" property="factorCode"/>'>
						<INPUT type="hidden" name="combofactorCodeType"
						value='<bean:write name="utiUwFactorDto" property="factorCode"/>,<bean:write name="comboFactorDto" property="codeType"/>'>
						<input type="text" name="combofactorCodeCode" class="codecode" readonly
						       style="width:<%=codeWidth%>px;" 
						value='<bean:write name="cellDto" property="codeCode"/>'>
						<input name="typeName" type="text" class="codename" readonly
						       style="width:<%=nameWidth%>px;" 
						value='<bean:write name="cellDto" property="codeName"/>'>
					</td>
				</logic:iterate>
				</logic:present>
				<td class="page" width="80px">
					<input type="text" class="common" name="combofactorFactorValue"
					value='<bean:write name="comboRowDto" property="userValue"/>'>
					<INPUT type="hidden" name="combofactorFactorName" 
								 value='<bean:write name="utiUwFactorDto" property="factorName"/>'>
					<INPUT type="hidden" name="combofactorFactorAttr" 
								 value='<bean:write name="utiUwFactorDto" property="factorAttr"/>'>
				</td>
				<td class="page" width="80px">
					<bean:write name="comboRowDto" property="nodeValue"/>
					<INPUT type="hidden" name="combofactorFactorNodeValue" 
								 value='<bean:write name="utiUwFactorDto" property="factorCode"/>,<bean:write name="comboRowDto" property="nodeValue"/>'>
				</td>
				<td class="page" width="55px">
					<bean:write name="utiUwFactorDto" property="exampleValue"/>
					<INPUT type="hidden" name="comboFactorDefaultValue" 
								 value='<bean:write name="utiUwFactorDto" property="factorCode"/>,<bean:write name="utiUwFactorDto" property="exampleValue"/>'>
				</td>
			</tr>
		</logic:iterate>
		</logic:present>
	</table>
	&nbsp;
<%tableId++;%>
</logic:iterate>
</logic:present>