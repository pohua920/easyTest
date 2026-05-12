<%--
****************************************************************************
* DESC       ：设置调度中心的设置
* AUTHOR     ： lixiang
* CREATEDATE ： 2005-08-30
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
	<tr>
		<td width="30%">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="12">
						<img src="/claim/images/bgBarLeft.gif" width="12" height="19">
					</td>
					<td class="formtitle">
						<s:text name="title.scheduleObject.schedulingInformationSettings" />
						<%--调度机构配置 --%>
					</td>
					<td width="11">
						<img src="/claim/images/bgBarRight.gif" width="11" height="19">
					</td>
				</tr>
			</table>
		</td>
		<td width="70%" align="right">
			<font color="#666666"> <s:text name="scheduleObject.note1" />
				<%--注： --%>“<font color="#FF0000">*</font>”<s:text name="scheduleObject.note2" />
				<%--为必选项 --%>，“<img src="/claim/images/bgDoubleClick2.gif" width="13" height="13" align="absbottom">” <s:text name="scheduleObject.note3" />
				<%--为双击选择项。 --%></font>
		</td>
	</tr>
</table>
<table border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" class="title" style="width: 100%">
	<tr>
		<td class="title" colspan="4">
			<s:text name="scheduleObject.beMechanismConfiguration" />
			<%--被调度机构配置 --%>
			<input type="hidden" name="prpDcompanyComEName" value="<bean:write name='prpDcompanyDto' property='comEName' />">
			<input type="hidden" name="prpDcompanyAddressEName" value="<bean:write name='prpDcompanyDto' property='addressEName' />">
			<input type="hidden" name="prpDcompanyTaxNumber" value="<bean:write name='prpDcompanyDto' property='taxNumber' />">
			<input type="hidden" name="prpDcompanyUpperComCode" value="<bean:write name='prpDcompanyDto' property='upperComCode' />">
			<input type="hidden" name="prpDcompanyInsurerName" value="<bean:write name='prpDcompanyDto' property='insurerName' />">
			<input type="hidden" name="prpDcompanyComAttribute" value="<bean:write name='prpDcompanyDto' property='comAttribute' />">
			<input type="hidden" name="prpDcompanyComType" value="<bean:write name='prpDcompanyDto' property='comType' />">
			<input type="hidden" name="prpDcompanyComLevel" value="<bean:write name='prpDcompanyDto' property='comLevel' />">
			<input type="hidden" name="prpDcompanyManager" value="<bean:write name='prpDcompanyDto' property='manager' />">
			<input type="hidden" name="prpDcompanyAccountLeader" value="<bean:write name='prpDcompanyDto' property='accountLeader' />">
			<input type="hidden" name="prpDcompanyCashier" value="<bean:write name='prpDcompanyDto' property='cashier' />">
			<input type="hidden" name="prpDcompanyAccountant" value="<bean:write name='prpDcompanyDto' property='accountant' />">
			<input type="hidden" name="prpDcompanyNewComCode" value="<bean:write name='prpDcompanyDto' property='newComCode' />">
			<input type="hidden" name="prpDcompanyValidStatus" value="<bean:write name='prpDcompanyDto' property='validStatus' />">
			<input type="hidden" name="prpDcompanyAcntUnit" value="<bean:write name='prpDcompanyDto' property='acntUnit' />">
			<input type="hidden" name="prpDcompanyArticleCode" value="<bean:write name='prpDcompanyDto' property='articleCode' />">
			<input type="hidden" name="prpDcompanyAccCode" value="<bean:write name='prpDcompanyDto' property='accCode' />">
			<input type="hidden" name="prpDcompanyCenterFlag" value="<bean:write name='prpDcompanyDto' property='centerFlag' />">
			<input type="hidden" name="prpDcompanyOuterPayCode" value="<bean:write name='prpDcompanyDto' property='outerPayCode' />">
			<input type="hidden" name="prpDcompanyInnerPayCode" value="<bean:write name='prpDcompanyDto' property='outerPayCode' />">
			<input type="hidden" name="prpDcompanyFlag" value="<bean:write name='prpDcompanyDto' property='flag' />">
			<input type="hidden" name="editType" value="<bean:write name='scheduleObjectDto' property='editType' />">
		</td>
	</tr>
	<%
		String addComReadOnly = " class='readonly' readOnly";
		if (editType.equals("ADD")) {

			addComReadOnly = " class='input' ";
		}

		//如果是总公司的，可以设置所有的调度机构

		UserDto user = (UserDto) session.getAttribute("user");
		String comCodeTemp = user.getComCode();
		if (comCodeTemp.equals("3100000000")) {
	%>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="scheduleObject.belongMechanismConfiguration" />
			<%--归属调度机构 --%>
			:
		</td>
		<td class="input" style="width: 37%" colspan=3>
			<html:select name="prpDcompanyDto" property="upperComCode" styleClass="three">
				<html:options collection="scheduleCenterList" property="comCode" labelProperty="comCName" />
			</html:select>
		</td>
	</tr>
	<%
		} else {
	%>
	<input type="hidden" name="upperComCode" value="<bean:write name='prpDcompanyDto' property='upperComCode' />">
	<%
		}
	%>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="scheduleObject.mechanismConfigurationCode" />
			<%--调度机构代码 --%>
			:
		</td>
		<td class="input" style="width: 37%">
			<input type=text name="prpDcompanyComCode" title="被分案機構代碼" maxlength="8" <%=addComReadOnly%> style="width: 90%" value="<bean:write name='prpDcompanyDto' property='comCode' filter='true' />">
			<img src="/claim/images/bgMarkMustInput.jpg">
		</td>
		<td class="title" style="width: 15%">
			<s:text name="scheduleObject.mechanismConfigurationName" />
			<%--调度机构名称 --%>
			:
		</td>
		<td class="input" style="width: 37%">
			<input type=text name="prpDcompanyComCName" title="被分案機構名稱" class="input" style="width: 90%" value="<bean:write name='prpDcompanyDto' property='comCName' filter='true' />">
			<img src="/claim/images/bgMarkMustInput.jpg">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpCaddress.addressName" />
			<%--地址 --%>
			:
		</td>
		<td class="input" colspan=3>
			<input type="text" class=input name="prpDcompanyAddressCName" value="<bean:write name='prpDcompanyDto' property='addressCName' />">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpCinsuredartif.postCode" />
			<%--邮编 --%>
			:
		</td>
		<td class="input">
			<input type="text" class=input name="prpDcompanyPostCode" value="<bean:write name='prpDcompanyDto' property='postCode' />">
		</td>
		<td class="title">
			<s:text name="db.prpDcompany.faxNumber" />
			<%--传真 --%>
			:
		</td>
		<td class="input">
			<input type="text" class=input name="prpDcompanyFaxNumber" value="<bean:write name='prpDcompanyDto' property='faxNumber' />">
		</td>
	<tr>
		<td class="title">
			<s:text name="db.prpDuser.validStatus" />
			<%--效力状态 --%>
			:
		</td>
		<td class="input">
			<html:radio name="prpDcompanyDto" property="validStatus" value="1">
				<s:text name="regist.prpLregist.yes" />
				<%--是 --%>
			</html:radio>
			<html:radio name="prpDcompanyDto" property="validStatus" value="0">
				<s:text name="regist.prpLregist.no" />
				<%--否 --%>
			</html:radio>
		</td>
		<td class="title" style="width: 15%" style="valign:bottom">
			<s:text name="scheduleObject.mechanismConfigurationKind" />
			<%--调度机构类型 --%>
			:
		</td>
		<td class="input" style="width: 20%">
			<html:radio name="prpDcompanyDto" property="comType" value="1000001">
				<s:text name="regist.prpLregist.repairFactory" />
				<%--修理厂 --%>
			</html:radio>
			<html:radio name="prpDcompanyDto" property="comType" value="1000002">
				<s:text name="scheduleObject.gongGuHang" />
				<%--公估行 --%>
			</html:radio>
		</td>
	</tr>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpDcompany.phoneNumber" />
			<%--电话 --%>
			:
		</td>
		<td class="input" colspan=3>
			<input type="text" class=input name="prpDcompanyPhoneNumber" value="<bean:write name='prpDcompanyDto' property='phoneNumber' />">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpDcompany.remark" />
			<%--备注 --%>
			:
		</td>
		<td class="input" colspan="3">
			<input type="text" class=input name="prpDcompanyRemark" value="<bean:write name='prpDcompanyDto' property='remark' />">
		</td>
	</tr>
</table>