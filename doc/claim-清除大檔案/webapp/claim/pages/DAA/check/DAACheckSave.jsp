
<%@page import="com.sinosoft.claim.dto.custom.UserDto"%>
<%@page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@page import="com.sinosoft.claim.schema.model.PrpLcheck"%>
<script language='javascript'>
	//显示列印窗口
	function printWindow(registNo, strWindowName) {
		strUrl = "/claim/print/claimPrint.do?printType=Regist&RegistNo="
				+ registNo;
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
						'width='
								+ pageWidth
								+ ',height='
								+ pageHeight
								+ ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
		newWindow.focus();
		return newWindow;
	}
</script>
<%-- 保存通用按钮 --%>
<table id="buttonArea">
	<%
		//简易赔案增加录入按钮的判断
	%>
	<input type="hidden" name="status" value="INPUT">
	<%
		UserDto user = (UserDto) session.getAttribute("user");
		//增加简易赔案判断的部分
		//显示简易赔案的权限
		boolean quickCaseWritePower = false;
		if (user != null) {
			quickCaseWritePower = user.getQuickCaseWritePower();
		}
		String strSchedule = AppConfig.get("sysconst.CHECK_AUTOCOMMIT");
		String comCodeTemp = "";
		if (user != null) {
			comCodeTemp = user.getComCode();
		}
		PrpLcheck prpLcheck = (PrpLcheck) request.getAttribute("prpLcheck");
		String flowStr = "&swfLogFlowID=" + request.getParameter("swfLogFlowID") + "&swfLogLogNo=" + request.getParameter("swfLogLogNo") + "&riskCode=" + prpLcheck.getRiskCode() + "&editType=ADD";

		String alink = "/claim/certainLossBeforeEdit.do?=" + prpLcheck.getId().getRegistNo() + "&insureCarFlag=" + prpLcheck.getInsureCarFlag() + "&lossItemCode=" + prpLcheck.getLossItemCode() + "&lossItemName=" + prpLcheck.getLossItemName() + "&checkInput=true" + flowStr;

		if (strSchedule.indexOf(comCodeTemp) >= 0) {
			//需要自动跳转
	%>
	<tr>
		<%--增加列印承保理赔信息的按钮即可实现报案记录带抄单从调度到查勘人员--%>
		<td align="center">
			<input type="button" name="print" class='bigbutton' value="<s:text name='button.printInsClaimInfo.value' />" onclick="printWindow('${prpLcheck.id.registNo}', '列印1');">
			&nbsp;&nbsp;
			<%-- 列印承保理赔信息 --%>
			<%-- 隐藏所按的保存按钮是哪个的标志--%>
			<c:if test="${prpLcheck.status=='4'}">
				<!--返回按钮-->
				<input type=button name=buttonCancel class='button' value="<s:text name='button.return.value' />" onclick="history.go(-1);">
				&nbsp;&nbsp;
		</c:if>
			<c:if test="${prpLcheck.status!='4'}">
				<c:if test="${prpLcheck.insureCarFlag=='4'}">
					<!--保存按钮-->
					<input type="button" name=buttonSave class='button' value="<s:text name='button.save.value' />" "
						onclick="return beforeSaveForm(this,'2');">
					&nbsp;&nbsp;
			</c:if>
				<span style="display: none"> <%
 	//提示是否需要通知调度
 %> <input type="checkbox" name="scheduleCheck" value="ON" style="width: 25px"> &nbsp;&nbsp;
				</span>
				<s:text name="check.notifySchedul" />
				<input type="hidden" name="messageToScheduleCheck" value="0">
				&nbsp;&nbsp;
			<%-- 通知调度进行定损 --%>
				<!--保存按钮-->
				<input type="button" name=buttonSave class='button' value="<s:text name='button.submit.value' />" onclick="return beforeSaveForm(this,'4');">
				&nbsp;&nbsp;
			
		</td>
		</c:if>
	</tr>
	<%
		} else {
			//不需要自动跳转
	%>
	<!--在不同状态下，按钮的数量是不同的，-->
	<%-- (1)立案登记 " 暂存" " 取消" "已完成","已完成並提交"
                 (2)正处理   "更新" "取消","已完成","已完成並提交","撤消"
                 (3)已完成   "更新" "取消" "提交"
                 (4)已提交   "返回"
                 (5)撤消     "返回"
                  
            --%>
	<tr>
		<%-- 隐藏所按的保存按钮是哪个的标志--%>
		<td>
			<input type="hidden" name=buttonSaveType value="1">
			<input type="hidden" name="checkGuideUser" value="${checkGuideUser }">
			<input type="hidden" name="checkGuideMessages" value="${checkGuideMessages }">
		</td>
	</tr>
	<tr>
		<%--增加列印承保理赔信息的按钮即可实现报案记录带抄单从调度到查勘人员--%>
		<td align="center">
			<input type="button" name="print" class='bigbutton' value="<s:text name='button.printInsClaimInfo.value' />" onclick="printWindow('${prpLcheck.id.registNo}', '列印1');">
			&nbsp;&nbsp;
			<%-- 列印承保理赔信息 --%>
			<c:if test="${prpLcheck.status!='4'}">
				<!--保存按钮-->
				<input type="button" name=buttonSave class='button' value="<s:text name='button.save.value' />" onclick="return beforeSaveForm(this,'2');">
				&nbsp;&nbsp;
				<span style="display: none"> <input type="checkbox" name="scheduleCheck" class="readonly" value="ON" style="width: 25px"> &nbsp;&nbsp;
				</span>
				<span style="display: none"> <s:text name="check.notifySchedul" /> &nbsp; <input type="hidden" name="messageToScheduleCheck" value="0"> <input type="hidden"
						name="scheduleCheckFlag" value="${scheduleCheckFlag}">
				</span>
				<%-- 通知调度进行定损 --%>
				<c:if test="${!checkGuideUser}">
					<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="return beforeSaveForm(this,'4');">
					&nbsp;&nbsp;
				</c:if>
				<!--取消按钮-->
				<input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value'/>" onclick="return resetForm();">
				&nbsp;&nbsp;
		</c:if>
			<c:if test="${prpLcheck.status=='2'}">
				<input type="button" name=giveup class='button' value="<s:text name='button.giveUpTask.value' />" onclick="giveupTemporarySave('check');">
				&nbsp;&nbsp;
			<%-- 放弃任务 --%>
				<%
					if (quickCaseWritePower) {
				%>
				<!--转为简易赔案按钮-->
				<input type="button" name=buttonQuickCase class='button' value="<s:text name='button.notifySchedul.value' />" style="display: none" onclick="return changeToQuickCase();">
				&nbsp;&nbsp;
				<%
					}
				%>
				<%-- 转为简易赔案 --%>
			</c:if>
			<!--放弃任务(只对待处理状态)-->
			<%
				String statusgiv = request.getParameter("status");
					if (statusgiv != null && statusgiv.equals("0")) {
			%>
			<!--放弃按钮style="width:33%"-->
			<c:if test="${!checkGuideUser}">
				<input type=button name=buttonGiveup class='button' value="<s:text name='button.giveUpTask.value' />" onclick="taskGiveup();">
				&nbsp;&nbsp;
			</c:if>
			<%-- 放弃任务 --%>
			<%
				if (quickCaseWritePower) {
			%>
			<!--转为简易赔案按钮-->
			<input type="button" name=buttonQuickCase class='button' value="<s:text name='button.notifySchedul.value' />" style="display: none" onclick="return changeToQuickCase();">
			&nbsp;&nbsp;
			<%
				}
			%>
			<%-- 转为简易赔案 --%>
			<%
				}
			%>
			<!--放弃任务-->
			<c:if test="${prpLcheck.status=='4'}">
				<%
					String ifclose = request.getParameter("ifclose");
							if ("true".equals(ifclose)) {
				%>
				<input type="button" name=buttonClose class='button' value="<s:text name='button.close.value' />" onclick="window.close();">
				<%
					} else {
				%>
				<!--取消按钮-->
				<input type=button name=buttonCancel class='button' value="<s:text name='button.return.value' />" onclick="history.go(-1);">
				&nbsp;&nbsp;
				<%
					}
				%>
			</c:if>
		</td>
	</tr>
	<%
		}
	%>
</table>
