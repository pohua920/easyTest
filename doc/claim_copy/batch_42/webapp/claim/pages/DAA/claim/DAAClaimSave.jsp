<%@ include file="/common/taglibs.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
	function showButton() { //此函数用於在加载时按钮可用
		if (fm.buttonCloseReturn.value == "close") {
			fm.buttonClose.disabled = false;
		} else {
			fm.buttonReturn.disabled = false;
		}
	}
</script>
<%-- 保存通用按钮 --%>
<table id="buttonArea">
	<!--在不同状态下，按钮的数量是不同的，-->
	<%--(1)立案登记 " 暂存" " 取消" "已完成","已完成並提交"
		(2)正处理 "更新" "取消","已完成","已完成並提交","撤消"
		(3)已完成 "更新" "取消" "提交"
		(4)已提交 "返回"
		(5)撤消 "返回"
	--%>
	<tr>
		<td>
			<input type="hidden" name=buttonSaveType value="1">
			<!-- 立案是否可暂存、提交判断 默认可以 -->
			<input type="hidden" name="buttonSubmitFlag" value="Y">
			<c:choose>
				<c:when test="${'Y'==dfFlag}">
					<!--返回按钮-->
					<input type=button name=buttonBack class='button' value="<s:text name='button.return.value' />" onclick="return history.back();">
					&nbsp;&nbsp;
				</c:when>
				<c:otherwise>
					<!-- mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能 START-->
					<c:if test="${editType == 'EDIT'}">
						<input type="hidden" name="editSpecial" value="${requestScope.specialEditCase == 'specialEditCase'? 'EDITSPECIAL':''}">
						<input type="hidden" name="editSpecialTest" value="${requestScope.specialEditCase}">
						<!--提交按钮-->
						<!-- mantis：CLM0242，處理人員： DP0713 ，需求單編號：新核心-立案正在處理提交按鈕隱藏(UAT) START-->
						<!-- CLM0242 20250826 復原 回復到VER.15465之前的狀態 -->
						
						<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value' />" onclick="return updateClaimEditForm(this,'4');">
						
						<!-- mantis：CLM0242，處理人員： DP0713 ，需求單編號：新核心-立案正在處理提交按鈕隱藏(UAT) END -->
						&nbsp;&nbsp;
					</c:if>
					<!-- mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能 END-->
					<c:if test="${prpLclaim.status != '4'}">
					<!-- mantis： CLM0058 ，處理人員：CLM0058車臉出險只走簡易流程，並將一般流程隱藏  start-->
						<c:if test="${!requestScope.simpleFlag}">
						<!--保存按钮-->
						<input type="button" name=buttonSave class='button' value="<s:text name='button.save.value' />" onclick="return beforeSaveForm(this,'2');">
						&nbsp;&nbsp;
						<!--提交按钮-->
						<!-- mantis：CLM0242，處理人員： DP0713 ，需求單編號：新核心-立案正在處理提交按鈕隱藏(UAT) -->
						<!-- CLM0242 20250826 復原 回復到VER.15465之前的狀態 -->
						<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value' />" onclick="return beforeSaveForm(this,'4');">
						&nbsp;&nbsp;
						</c:if>
						<c:if test="${requestScope.simpleFlag}">
						<!--簡易賠案保存按鈕 -->
							<input type="button" name=buttonSaveFinishSubmitSimple class='button' style="width:100px;" value="簡易賠案暫存" onclick="return beforeSaveForm(this,'22');">
							&nbsp;&nbsp;
						<!--簡易賠案發起按鈕 -->
							<input type="button" name=buttonSaveFinishSubmitSimple class='button' value="簡易賠案" onclick="return beforeSaveForm(this,'44');">
							<input type="hidden" name="prpLclaimSimpleFlag" value="0" >
							&nbsp;&nbsp;
						</c:if>
						<!--取消按钮-->
						<input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value' />" onclick="return resetForm();">
						&nbsp;&nbsp;
					<!-- mantis： CLM0058 ，處理人員：CLM0058車臉出險只走簡易流程，並將一般流程隱藏  end-->
					</c:if>
					<c:if test="${prpLclaim.status=='3' }">
						<!--保存按钮-->
						<input type="button" name=buttonSave class='button' value="<s:text name='button.save.value' />" onclick="return beforeSaveForm(this,'3');">
						&nbsp;&nbsp;
						<!--回退按钮-->
						<input type="button" name=buttonSave class='button' value="<s:text name='button.return.value' />" onclick="return beforeSaveForm(this,'2');">
						&nbsp;&nbsp;
						<!--提交按钮-->
						<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value' />" onclick="return beforeSaveForm(this,'4');">
						&nbsp;&nbsp;
						<!--取消按钮-->
						<input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value' />" onclick="return resetForm();">
						&nbsp;&nbsp;
					</c:if>
					<c:if test="${prpLclaim.status=='4'}">
						<c:choose>
							<c:when test="${'true'==ifclose}">
								<input type="button" name=buttonClose class='button' value="<s:text name='button.close.value' />" onclick="window.close();">
								&nbsp;&nbsp;
								<input type="hidden" name=buttonCloseReturn value="close">
							</c:when>
							<c:otherwise>
								<!--返回按钮-->
								<input type=button name=buttonReturn class='button' value="<s:text name='button.return.value' />" onclick="history.go(-1);">
								&nbsp;&nbsp;
								<input type="hidden" name=buttonCloseReturn value="return">
							</c:otherwise>
						</c:choose>
					</c:if>
					<!--放弃任务(只对待处理状态)-->
					<c:if test="${status!=null && status=='0' }">
						<!--放弃按钮style="width:33%"-->
						<input type=button name=buttonGiveup class='button' value="<s:text name='button.giveUpTask.value' />" onclick="taskGiveup();">
						&nbsp;&nbsp;
					</c:if>
					<!--放弃任务-->
					<c:if test="${prpLclaim.classCode=='27' }">
						<!--申请调查-->
						<input type=button name=buttonSchedule class='bigbutton' value="<s:text name='button.applyInvest.value' />"
							onclick="applySchedule('${prpLclaimDto.registNo}','claim','${swfLogFlowID }','${swfLogLogNo }','${status }','03','${prpLclaimDto.claimNo}');">
						&nbsp;&nbsp;
					</c:if>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
</table>
