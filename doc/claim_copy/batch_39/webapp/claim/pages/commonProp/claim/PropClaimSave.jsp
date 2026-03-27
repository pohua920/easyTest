<script language='javascript'>
	//显示列印窗口
	function printWindow(registNo, strWindowName) {
		//add print liudaoping 2013-04-15
		//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
		return false;
		strUrl = "/claim/ClaimPrint.do?printType=CopyPrint&RegistNo="
				+ registNo;
		var pageWidth = screen.availWidth - 10;
		var pageHeight = screen.availHeight - 30;

		if (pageWidth < 100) {
			pageWidth = 100;
		}

		if (pageHeight < 100) {
			pageHeight = 100;
		}

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
	
	//mantis：CLM0272 ，處理人員：DP0713，需求單編號：新核心-立案修改功能新增火險修改 START
	function checkUndwrt() {
		return true;
	}
	//mantis：CLM0272 ，處理人員：DP0713，需求單編號：新核心-立案修改功能新增火險修改 END
</script>
<%-- 保存通用按钮 --%>
<table id="buttonArea" cellpadding="0" cellspacing="0" width="80%" style="display:">
	<!--在不同状态下，按钮的数量是不同的，-->
	<%-- (1)立案登记 " 保存" " 取消" "已完成","已完成並提交"
           (2)正处理   "更新" "取消","已完成","已完成並提交","撤消"
           (3)已完成   "更新" "取消" "提交"
           (4)已提交   "返回"
           (5)撤消     "返回"
      --%>
	<tr>
		<%-- 隐藏所按的保存按钮是哪个的标志--%>
		<td>
			<input type="hidden" name=buttonSaveType value="1">
			<!-- mantis：CLM0272 ，處理人員：DP0713，需求單編號：新核心-立案修改功能新增火險修改 START-->
			<!-- 立案是否可暂存、提交判断 默认可以 -->
			<input type="hidden" name="buttonSubmitFlag" value="Y">
			<!-- mantis：CLM0272 ，處理人員：DP0713，需求單編號：新核心-立案修改功能新增火險修改 END-->
		</td>
	</tr>
	<tr>
		<td>
			<%--<input type="button" name="print" class='bigbutton' value="<s:text name='button.printInsClaimInfo.value'/>" onclick="printWindow('${prpLclaimDto.registNo}', '<s:text name='common.check.print'/>');"> --%><%--列印1--%>
			<%-- 列印承保理赔信息 --%>
		</td>
		<!-- mantis：CLM0276 ，處理人員：DP0713，需求單編號：新核心-修正正在處理立案任務的[提交]按鈕問題 START -->
		<!-- mantis：CLM0272 ，處理人員：DP0713，需求單編號：新核心-立案修改功能新增火險修改 START-->
		<c:choose>
			<c:when test="${editType == 'EDIT' && requestScope.specialEditCase == 'specialEditCase'}">
				<input type="hidden" name="editSpecial" value="${requestScope.specialEditCase == 'specialEditCase'? 'EDITSPECIAL':''}">
				<input type="hidden" name="editSpecialTest" value="${requestScope.specialEditCase}">
				<td class=button style="width: 50%" align="center">
					<!--提交按钮-->
					<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value' />" onclick="return updateClaimEditForm(this,'4');">
				</td>
				&nbsp;&nbsp;
				<td class=button style="width: 50%" align="center">
					<!--取消按钮-->
					<input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value' />" onclick="return resetForm();">
				</td>
			</c:when>
			<c:otherwise>
				<!-- mantis：CLM0272 ，處理人員：DP0713，需求單編號：新核心-立案修改功能新增火險修改 END-->
				<c:if test="${prpLclaim.status!='4'}">
					<td class=button style="width: 33%" align="center">
						<!--保存按钮-->
						<input type="button" name=buttonSave class='button' value="<s:text name='button.save.value' />" onclick="return saveForm(this,'2');">
					</td>
					<td class=button style="width: 33%" align="center">
						<!--提交按钮-->
						<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="return saveForm(this,'4');">
					</td>
					<td class=button style="width: 33%" align="center">
						<!--取消按钮-->
						<input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value' />" onclick="return resetForm();">
					</td>
					<c:if test="${param.status=='0'}">
						<td class=button align="center">
							<!--放弃任务按钮style="width:33%"-->
							<input type=button name=buttonGiveup class='button' value="<s:text name='button.giveUpTask.value'/>" onclick="taskClaimGiveup();">
						</td>
					</c:if>
					<!--放弃任务-->
					<%@include file="/pages/common/sendUndwrt/SendUndwrtButton.jsp"%>
				</c:if>
			</c:otherwise>
		</c:choose>
		<!-- mantis：CLM0276，處理人員：DP0713，需求單編號：新核心-修正正在處理立案任務的[提交]按鈕問題 END-->
		<c:if test="${prpLclaim.status=='4'}">
			<td class=button style="width: 33%" align="center">
				<!--返回按钮-->
				<input type=button name=buttonBack class='button' value="<s:text name='button.return.value'/>" onclick="return history.back();">
			</td>
		</c:if>
	</tr>
</table>
