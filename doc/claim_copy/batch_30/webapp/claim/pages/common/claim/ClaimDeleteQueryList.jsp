<%--
****************************************************************************
* DESC       ：立案查询结果显示页面（立案撤消可以使用）
* AUTHOR     ：中科软
* CREATEDATE ：2004-09-15
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<html:html locale="true">
<head>
<app:css />
<title><s:text name="title.claimBeforeEdit.queryClaim" /></title>
<script src="/claim/common/js/showpage.js">
	
</script>
<script language="javascript">
	/**
	 *@description 撤消操作
	 *@param       按钮对象
	 *@return      通过返回true,否则返回false
	 */

	function deleteClaim(fieldObject) {
		var bussinessNo = "";
		var status = "";
		var registNo = "";

		var intIndex = parseInt(fieldObject.num);
		if (isNaN(fm.buttonDelete.length)) {
			bussinessNo = fm.prplclaimClaimNo.value;
			status = fm.prplclaimStatus.value;
			registNo = fm.RegistNo.value;
		} else {
			bussinessNo = fm.prplclaimClaimNo[intIndex].value;
			status = fm.prplclaimStatus[intIndex].value;
			registNo = fm.RegistNo[intIndex].value;
		}

		//输入地址不能为空
		if (bussinessNo == "") {
			alert("没有得到bussinessNo，请重新操作。");
			return false;
		}

		if (status == "4") {
			alert("已经提交的案件，不能够被进行撤消的操作！");
			return false;
		}

		//提示是否撤消？
		strMsg = "确定要撤消业务号码为'" + bussinessNo + "'的案件吗?";
		//执行delete的操作
		if (confirm(strMsg)) {
			fm.bussinessNo.value = bussinessNo;
			fm.claimRegistNo.value = registNo;
			//防止重复提交
			fm.userLastAction.value = "deleteclaim?" + bussinessNo;
			fieldObject.disabled = true;
			fm.submit();
			return true;
		}

		return false;
	}

	function ShowAlertMessage() {
		var msg = fm.alertMessage.value;
		if (msg != "") {
			alert(msg)
			fm.alertMessage.value = "";
		}
	}
</script>
<html:base />
</head>
<body>
	<form name=fm action="/claim/claimDeleteSave.do" method="post">
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td colspan=6 class="formtitle">
					<s:text name="title.claimBeforeEdit.titleName" />
				</td>
			</tr>
			<tr>
				<td class="centertitle">
					<s:text name="certainLoss.prpLscheduleMainWF.caseState" />
				</td>
				<%--案件状态：--%>
				<td class="centertitle">
					<s:text name="db.prpLclaim.claimNo" />
				</td>
				<td class="centertitle">
					<s:text name="prpLregist.registNo" />:
				</td>
				<%--报案号--%>
				<td class="centertitle">
					<s:text name="db.prpCOpenCoverBal.operatorCode" />
				</td>
				<%--操作员--%>
				<td class="centertitle">
					<s:text name="db.prpLclaimStatus.operatedate" />
				</td>
				<%--操作时间--%>
				<td class="centertitle">
					<s:text name="task.deal" />
				</td>
				<%--处理--%>
			</tr>
			<%
				int index = 0;
			%>
			<logic:notEmpty name="prpLclaimDto" property="claimList">
				<logic:iterate id="prpLclaim1" name="prpLclaimDto" property="claimList">
					<%
						if (index % 2 == 0)
							out.print("<tr class=listodd>");
						else
							out.print("<tr class=listeven>");
					%>
					<tr class=common>
						<td align="center">
							<logic:equal name="prpLclaim1" property="status" value='1'>
								<s:text name="common.status.untreated" />
								<%--未处理--%>
							</logic:equal>
							<logic:equal name="prpLclaim1" property="status" value='2'>
								<s:text name="common.status.intreating" />
								<%--正处理--%>
							</logic:equal>
							<logic:equal name="prpLclaim1" property="status" value='3'>
								<s:text name="common.status.treated" />
								<%--已处理--%>
							</logic:equal>
							<logic:equal name="prpLclaim1" property="status" value='4'>
								<s:text name="common.status.submited" />
								<%--已提交--%>
							</logic:equal>
							<logic:equal name="prpLclaim1" property="status" value='5'>
								<s:text name="common.status.revoked" />
								<%--已撤消--%>
							</logic:equal>
						</td>
						<td align="center">
							<a href="/claim/claimFinishQueryList.do?prpLclaimClaimNo=<bean:write name='prpLclaim1' property='claimNo'/>&editType=SHOW"><bean:write name="prpLclaim1" property="claimNo" /></a>
						</td>
						<td align="center">
							<bean:write name="prpLclaim1" property="registNo" />
						</td>
						<td align="center">
							<bean:write name="prpLclaim1" property="operatorCode" />
						</td>
						<td align="center">
							<bean:write name="prpLclaim1" property="operateDate" />
						</td>
						<td>
							<input type="button" name="buttonDelete" class='button' num=<%=index%> value="<s:text name='button.undo.value' />" onclick="return deleteClaim(this);">
							<%--撤消--%>
							<input type="hidden" name="prplclaimClaimNo" value="<bean:write name='prpLclaim1' property='claimNo'/>">
							<input type="hidden" name="prplclaimStatus" value="<bean:write name='prpLclaim1' property='status'/>">
							<input type="hidden" name="RegistNo" value="<bean:write name='prpLclaim1' property='registNo'/>">
						</td>
					</tr>
					<%
						index++;
					%>
				</logic:iterate>
			</logic:notEmpty>
			<tr class="listtail">
				<td colspan="6">
					<s:text name="certainLoss.totalInquiries" />
					<%--共查询出--%><%=index%><s:text name="certainLoss.meetRecord" />
					<%--条满足条件的记录--%>
				</td>
				<input type="hidden" name="bussinessNo" value="">
				<input type="hidden" name="status" value="">
				<input type="hidden" name="userLastAction" value="">
				<input type="hidden" name="queryCondtions" value="">
				<input type="hidden" name="editType" value="SHOW">
				<input type="hidden" name="claimRegistNo" value="">
			</tr>
		</table>
		</tr>
		</table>
	</form>
</body>
</html:html>