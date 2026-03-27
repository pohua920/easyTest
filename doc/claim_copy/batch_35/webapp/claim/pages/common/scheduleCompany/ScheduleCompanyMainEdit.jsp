<%--
****************************************************************************
* DESC       ：设置调度中心的设置
* AUTHOR     ： lixiang
* CREATEDATE ： 2005-08-30
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<script language="javascript">
	function changeComCode() {
		fm.action = "/claim/scheduleCompanyQuery.do";
		fm.submit();
	}
</script>
<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
	<tr>
		<td width="30%">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="12">
						<img src="/claim/images/bgBarLeft.gif" width="12" height="19">
					</td>
					<td class="formtitle">
						<s:text name="scheduleCompany.controlCenterConfiguration " />
						<%--调度中心配置 --%>
					</td>
					<td width="11">
						<img src="/claim/images/bgBarRight.gif" width="11" height="19">
					</td>
				</tr>
			</table>
		</td>
		<td width="70%" align="right">
			<font color="#666666"> <s:text name="scheduleObject.note1" /> <%--注： --%>“<font color="#FF0000">*</font>”<s:text name="scheduleObject.note2 " /> <%--为必选项 --%>，“<img
				src="/claim/images/bgDoubleClick2.gif" width="13" height="13" align="absbottom">” <s:text name="scheduleObject.note2" /> <%--为双击选择项。 --%></font>
		</td>
	</tr>
</table>
<table border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" class="title" style="width: 100%">
	<tr>
		<td class="title" colspan="4">
			<s:text name="scheduleCompany.controlCenterConfiguration " />
			<%--调度中心配置 --%>
			<input type="hidden" name="prpLscheduleCompanyMobileNo" value="<bean:write name='prpLscheduleCompanyDto' property='mobileNo' />">
			<input type="hidden" name="prpLscheduleCompanyLinkerName" value="<bean:write name='prpLscheduleCompanyDto' property='linkerName' />">
			<input type="hidden" name="prpLscheduleCompanyValidStatus" value="<bean:write name='prpLscheduleCompanyDto' property='validStatus' />">
			<input type="hidden" name="prpLscheduleCompanyRemark" value="Remark">
			<input type="hidden" name="prpLscheduleCompanyFlag" value="<bean:write name='prpLscheduleCompanyDto' property='flag' />">
			<input type="hidden" name="upperCompCode" value="";>
			<input type="hidden" name="editType" value="<%=editType%>";>
		</td>
	</tr>
	<tr>
		<%
			//如果是总公司的，可以设置所有的调度机构

			UserDto user = (UserDto) session.getAttribute("user");
			String comCodeTemp = user.getComCode();
			comCodeTemp = comCodeTemp.substring(0, 4);
			if (comCodeTemp.equals("3100") || comCodeTemp.equals("3199")) {
		%>
		<td class="title" style="width: 15%">
			<s:text name="scheduleCompany.controlCenter " />
			<%--调度中心 --%>
			:
		</td>
		<td class="input" colspan=3>
			<html:select name="prpLscheduleCompanyDto" property="comCode" styleClass="three" onchange="changeComCode();">
				<html:options collection="scheduleCenterList" property="comCode" labelProperty="comCName" />
			</html:select>
			<input type=hidden name="prpLscheduleCompanyComName" title="分案中心名稱" class="readonly" readonly="true" value="<bean:write name='prpLscheduleCompanyDto' property='comName' filter='true' />">
		</td>
		<%
			} else {
		%>
		<td class="title" style="width: 15%">
			<s:text name="scheduleCompany.controlCenterCode " />
			<%--调度中心代码 --%>
			:
		</td>
		<td class="input" style="width: 37%">
			<input type=text name="comCode" title="分案中心代碼" maxlength="22" class="readonly" readonly="true" value="<bean:write name='prpLscheduleCompanyDto' property='comCode' filter='true' />">
		</td>
		<td class="title" style="width: 15%">
			<s:text name="scheduleCompany.controlCenterName " />
			<%--调度中心名称 --%>
			:
		</td>
		<td class="input" style="width: 37%">
			<input type=text name="prpLscheduleCompanyComName" title="分案中心名稱" class="readonly" readonly="true" value="<bean:write name='prpLscheduleCompanyDto' property='comName' filter='true' />">
		</td>
		<%
			}
		%>
	</tr>
	<tr>
		<td class="title">
			<s:text name="scheduleCompany.companyWorkTime " />
			<%--公司上班时间 --%>
			:
		</td>
		<td class="input">
			<input type="text" class=input name="prpLscheduleCompanyOnDutyTime" maxlength="8" onkeypress='return pressIntegerCopy2(event)' onblur="return formatTime(event, this);"
				value="<bean:write name='prpLscheduleCompanyDto' property='onDutyTime' />">
		</td>
		<td class="title">
			<s:text name="scheduleCompany.companyOffWorkTime " />
			<%--公司下班时间 --%>
			:
		</td>
		<td class="input">
			<input type="text" class=input name="prpLscheduleCompanyOffDutyTime" maxlength="8" onkeypress='return pressIntegerCopy2(event)' onblur="return formatTime(event, this);"
				value="<bean:write name='prpLscheduleCompanyDto' property='offDutyTime' />">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="scheduleCompany.staffOnDutyDay" />
			<%--调度人员24小时值班 --%>
			:
		</td>
		<td class="input">
			<html:radio name="prpLscheduleCompanyDto" property="validStatus" value="1">
				<s:text name="regist.prpLregist.no " />
				<%--否 --%>
			</html:radio>
			<html:radio name="prpLscheduleCompanyDto" property="validStatus" value="0">
				<s:text name="regist.prpLregist.yes " />
				<%--是 --%>
			</html:radio>
		</td>
		<td class="title" style="width: 15%" style="valign:bottom">
			<s:text name="scheduleCompany.fixedTelephone " />
			<%--固定电话 --%>
			:
		</td>
		<td class="input" style="width: 20%">
			<input type="text" class=input name="prpLscheduleCompanyPhoneNo" maxlength="100" value="<bean:write name='prpLscheduleCompanyDto' property='phoneNo' />">
		</td>
	</tr>
	<!--
         modify by ww add start 2005-11-07
         reason:客户需求，添加AM帐号。
      -->
	<tr>
		<td class="title">
			<s:text name="scheduleCompany.amAccount " />
			<%--AM帐号 --%>
			:
		</td>
		<td class="input" colspan="3">
			<input type="text" class="input" name="prpLscheduleCompanyAmNo" maxlength="254" value="<bean:write name='prpLscheduleCompanyDto' property='amNo' />" onkeypress='return pressIntegerCopy2(event)'>
		</td>
	</tr>
	<!--modify by ww add end 2005-11-07-->
	<tr>
		<td class="title">
			<s:text name="scheduleCompany.alertInformation " />
			<%--提示信息 --%>
			:
		</td>
		<td class="input" colspan="3">
			<input type="text" class=input name="prpLscheduleCompanyPromptContent" maxlength="254" value="<bean:write name='prpLscheduleCompanyDto' property='promptContent' />">
		</td>
	</tr>
</table>