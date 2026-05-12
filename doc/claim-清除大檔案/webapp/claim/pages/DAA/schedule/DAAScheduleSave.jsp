
<%@ include file="/common/taglibs.jsp"%>
<script language='javascript'>
function backDeal(buttonType) {
	fm.buttonSaveType.value = buttonType;
	fm.submit();
}
//显示列印窗口

function printWindow(registNo, strWindowName) {
	//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
	//return false;
	strUrl = "/claim/print/claimPrint.do?printType=Regist&RegistNo=" + registNo;
	var pageWidth = screen.availWidth - 10;
	var pageHeight = screen.availHeight - 30;
	if (pageWidth < 100)
		pageWidth = 100;
	if (pageHeight < 100)
		pageHeight = 100;
	var newWindow = window
		.open(
			strUrl,
			strWindowName,
			'width=' + pageWidth + ',height=' + pageHeight + ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
	newWindow.focus();
	return newWindow;
}
</script>
<%-- 调度提交通用按钮 --%>
<c:set var="displaymessage" value="" />
<c:set var="displaymessage" value="此案件为单独交强险报案，至少有一个第三者损失,请通知报案员或者手工增加第三者损失後再调度！" />
<input type="hidden" name=buttonSaveType value="1">
<c:choose>
	<c:when test="${param.editType eq 'SHOW' || param.editType eq 'DELETE'}">
		<table id="buttonArea">
		</table>
		<table cellpadding="0">
			<tr>
				<%--增加列印承保理赔信息的按钮即可实现报案记录带抄单从调度到查勘人员--%>
				<td>
					<input type="button" name="print" class='bigbutton' value=<s:text name="button.printInsClaimInfo.value" /> onclick="printWindow('${prpLscheduleMainWF.id.registNo}','');">
					&nbsp;&nbsp;
				</td>
				<td style="width: 5px"></td>
				</c:when>
				<c:otherwise>
					<table id="buttonArea">
						<%--如果定损调度和查勘调度都为空的，则不能调度的--%>
						<c:set var="nblnull" value="disabled" />
						<c:if test="${prpLscheduleItem.scheduleItemList != null}">
							<c:set var="nblnull" value="" />
							<c:set var="displaymessage" value="" />
						</c:if>
						<tr>
							<%--增加列印承保理赔信息的按钮即可实现报案记录带抄单从调度到查勘人员--%>
							<td>
								<input type="button" name="print" class='bigbutton' value="<s:text name="button.printInsClaimInfo.value" />" onclick="printWindow('${prpLscheduleMainWF.id.registNo}','');">
								&nbsp;&nbsp;
								<input type=button name=buttonGiveup class='button' value="<s:text name="button.giveUpTask.value" />" onclick="taskGiveup();">
							</td>
							<td style="width: 5px"></td>
							<td>
								<!--调度按钮-->
								<c:if test="${prpLscheduleMainWF.saveType == 'GETBACKEDIT' }">
									<%--取回並修改名称改成提交--%>
									<%--改派--%>
									<input type="button" name=buttonSave class='button' value="<s:text name="button.send.value" />" <c:out value="${nblnull}"/> onclick="saveForm(this);">&nbsp;&nbsp;
                 				</c:if>
								<c:if test="${prpLscheduleMainWF.saveType != 'GETBACKEDIT' }">
									<!--提交的对象和内容-->
									<c:if test="${schedule.flag != 'false' }">
										<%--调度--%>
										<input type="button" name=buttonSave class='button' value="<s:text name="button.scheduling.value" />" onclick="saveForm(this);">&nbsp;&nbsp;
                   					</c:if>
									<c:if test="${schedule.flag == 'false' }">
										<%--退回到待处理--%>
										<input type="button" name=buttonSave class='bigbutton' value="<s:text name="button.backPending.value" />" <c:out value="${nblnull}"/> onclick="backDeal('20');">&nbsp;&nbsp;
                   					</c:if>
								</c:if>
							</td>
							</c:otherwise>
							</c:choose>
							<td>
								<!--取消按钮-->
								<input type="button" name=buttonCancel class='button' value="<s:text name="prompt.back" />" onclick="javascript:history.go(-1);">
								&nbsp;&nbsp;
							</td>
						</tr>
						<c:if test="${editType eq 'ADD'}">
							<tr>
								<td style="color: red" colspan=4>${displaymessage}</td>
							</tr>
						</c:if>
					</table>