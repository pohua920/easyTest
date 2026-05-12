<%--
****************************************************************************
* DESC       ：通用按钮画面
* AUTHOR     : 理赔组
* CREATEDATE ：2004-10-11
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
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

		var newWindow = window.open(
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
<c:choose>
	<c:when test="${param.editType=='SHOW'||param.editType=='DELETE'}">
		<%-- 保存通用按钮 --%>
		<table cellpadding="0" cellspacing="0" width="80%" class="common">
			<tr>
				<%-- 隐藏所按的保存按钮是哪个的标志--%>
				<td align="center">
					<input type="hidden" name=buttonSaveType value="1">
					<!--返回按钮-->
					<input type=button name=buttonCancel class='button' value="<s:text name='button.return.value'/>" onclick="javascript:history.go(-1);">
				</td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" width="0" height="0" id="buttonArea">
		</table>
	</c:when>
	<c:otherwise>
		<%-- 保存通用按钮 --%>
		<table id="buttonArea" cellpadding="0" cellspacing="0" width="80%" style="display:">
			<!-- 在不同状态下，按钮的数量是不同的，-->
			<%--(1)立案登记 " 暂存" " 取消" "已完成","已完成並提交"
				(2)正处理	"更新" "取消","已完成","已完成並提交","撤消"
				(4)已提交	"返回"
				(5)撤消	"返回" 因为是自动的，所以先注释掉相应的人员提交 
			--%>
			<input type="hidden" name=buttonSaveType value="1">
			<tr>
				<td align="center">
					<!-- mantis：CLM0211，處理人員：DP0713，需求單編號：新核心-CA工程險理算處理畫面舊有按鈕移除 START-->
					<input type="hidden" name="clm0211_01_for_SendUndwrtButton.jsp" value="${requestScope.prpLcompensate.status}"/>
					<input type=hidden name="sendUndwrtFlag" value="<c:out value='${requestScope.sendUndwrtFlag}'/>" />
					<input type=hidden name="needUndwrtFlag" value="<c:out value='${requestScope.needUndwrtFlag}' />" />
					<!-- mantis：CLM0211，處理人員：DP0713，需求單編號：新核心-CA工程險理算處理畫面舊有按鈕移除 END-->
					<input type="hidden" name="print" class='bigbutton' value="<s:text name='button.printInsClaimInfo.value'/>" onclick="printWindow(fm.registno.value, '列印1');">
					<%-- 列印承保理赔信息 --%>
					<c:if test="${requestScope.prpLcompensate.status!='4'}">
						<!--保存按钮-->
						<input type="button" name=buttonSave class='button' value="<s:text name='button.save.value'/>" onclick="return saveForm(this,'2');">
						<!-- 如果涉及联共保，提交前，先调用联共保分摊按钮的方法 -->
						<c:choose>
							<c:when test="${not empty requestScope.coinsFlag}">
								<c:choose>
									<c:when test="${requestScope.coinsFlag=='1'||requestScope.coinsFlag=='2'||requestScope.coinsFlag=='3'}">
										<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="if(!fm.countFlag.value=='1'){alert('請先產生聯共保分攤信息！')}else{return saveForm(this,'4')};">
									</c:when>
									<c:otherwise>
										<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="return saveForm(this,'4');">
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="return saveForm(this,'4');">
							</c:otherwise>
						</c:choose>
						<!--取消按钮-->
						<input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value'/>" onclick="return resetForm();">
						<c:if test="${param.status=='0'}">
							<!--放弃任务按钮style="width:33%"-->
							<input type=button name=buttonGiveup class='button' value="<s:text name='button.giveUpTask.value'/>" onclick="taskGiveup();">
						</c:if>
					</c:if>
					<c:if test="${requestScope.prpLcompensate.status=='4'}">
						<!--返回按钮-->
						<input type=button name=buttonBack class='button' value="<s:text name='button.return.value'/>" onclick="return history.back();">
					</c:if>
				</td>
				<c:if test="${requestScope.prpLcompensate.status!='4'}">
					<%@include file="/pages/common/sendUndwrt/SendUndwrtButton.jsp"%>
				</c:if>
			</tr>
		</table>
	</c:otherwise>
</c:choose>
