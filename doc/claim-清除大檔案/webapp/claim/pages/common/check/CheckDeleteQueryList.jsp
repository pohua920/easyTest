<%--
****************************************************************************
* DESC       ：删除查勘查询结果页面
* AUTHOR     ：中科软
* CREATEDATE ：2004-06-15
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>

<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>

<html:html locale="true">
<head>
    <app:css />

  <title><s:text name="title.checkBeforeEdit.queryCheck"/></title>
          <script src="/claim/common/js/showpage.js"> </script>
  <html:base/>

  <script language=javascript>
  function submitForm(fieldObject) {
		var registNo = "";
		var status = "";
		var intIndex = parseInt(fieldObject.num);
		if (isNaN(fm.buttonDelete.length)) {
			registNo = fm.registNoRow.value;
			status = fm.checkStatusNoRow.value;
		} else {
			registNo = fm.registNoRow[intIndex].value;
			status = fm.checkStatusNoRow[intIndex].value;
		}


		if (status == "4" || status == "") {
			alert("选中的查勘'" + registNo + "' 已经被提交了，不可以进行删除操作!")
			return false;

		}

		//撤消确认提示
		if (confirm("确定要删除选中的查勘'" + registNo + "'吗？")) {
			fm.buttonDelete.disabled = true;
			fm.registNo.value = registNo;
			fm.status.value = status;
			fm.submit();
			return true;
		}

		return false;

	}
  </script>
</head>
<body>
	<form name=fm action="/claim/checkDelete.do" method="post" onsubmit="return validateForm(this);">
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td colspan=6 class="formtitle">
					<s:text name="check.surveyInfoResult" />
				</td>
			</tr>
			<%--查勘查询结果信息--%>
			<tr>
				<td class="centertitle">
					<s:text name="db.prpLcheck.registNo" />
				</td>
				<td class="centertitle">
					<s:text name="db.prpLcheck.policyNo" />
				</td>
				<td class="centertitle">
					<s:text name="certainLoss.person1" />
				</td>
				<%--查勘人1--%>
				<td class="centertitle">
					<s:text name="certainLoss.person2" />
				</td>
				<%--查勘人2--%>
				<td class="centertitle">
					<s:text name="check.enterTime" />
				</td>
				<%--输入时间--%>
				<td class="centertitle">
					<s:text name="certify.operate" />
				</td>
				<%--操作--%>
			</tr>
			<%
				int index = 0;
					String strindex = "";
			%>
			<logic:notEmpty name="prpLcheckDto" property="checkList">
				<logic:iterate id="prpLcheck1" name="prpLcheckDto" property="checkList">
					<%
						strindex = "'" + String.valueOf(index) + "'";

									if (index % 2 == 0)
										out.print("<tr class=listodd>");
									else
										out.print("<tr class=listeven>");
					%>
					<tr class=common>
						<td align="center">
							<a href="/claim/checkFinishQueryList.do?prpLcheckCheckNo=<bean:write name='prpLcheck1' property='registNo'/>&editType=<bean:write name='prpLcheckDto' property='editType'/>&riskCode='DAA'">
								<bean:write name="prpLcheck1" property="registNo" />
							</a>
						</td>
						<td align="center">
							<bean:write name="prpLcheck1" property="policyNo" />
						</td>
						<td align="center">
							<bean:write name="prpLcheck1" property="checker1" />
						</td>
						<td align="center">
							<bean:write name="prpLcheck1" property="checker2" />
						</td>
						<td align="center">
							<bean:write name="prpLcheck1" property="checkDate" />
							<input type="hidden" name="registNoRow" class="common" value="<bean:write name='prpLcheck1' property='registNo'/>">
							<input type="hidden" name="checkStatusNoRow" class="common" value="<bean:write name='prpLcheck1' property='status'/>">
						</td>
						<td align="center">
							<input type="button" name="buttonDelete" class='button' num=<%=strindex%> value="<s:text name='button.delete.value' />" onclick="return submitForm(this);">
						</td>
						<%--删除--%>
					</tr>
					<%
						index++;
					%>
				</logic:iterate>
			</logic:notEmpty>
			<tr class=listtail>
				<td colspan=6>
					<s:text name="certainLoss.totalInquiries" />
					<%--共查询出--%><%=index%><s:text name="certainLoss.meetRecord" />
					<%--条满足条件的记录--%>
					<input type="hidden" name="registNo" class="common" value="">
					<input type="hidden" name="status" class="common" value="">
				</td>
			</tr>
		</table>
		</tr>
		</table>
	</form>
</body>
</html:html>