
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
	//显示非车列印窗口
	function printWindow(registNo, strWindowName) {
		strUrl = "/claim/ClaimPrint.do?printType=CopyPrint&RegistNo=" + registNo;
		var pageWidth = screen.availWidth - 10;
		var pageHeight = screen.availHeight - 30;

		if (pageWidth < 100) {
			pageWidth = 100;
		}

		if (pageHeight < 100) {
			pageHeight = 100;
		}
		//add print liudaoping 2013-04-15
		//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
		return false;
		var newWindow = window.open(strUrl,strWindowName,'width='+pageWidth+',height='+pageHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
		newWindow.focus();
		return newWindow;
	}
</script>
<c:choose>
	<c:when test="${param.editType=='SHOW'||param.editType=='DELETE'}">
		<%-- 保存通用按钮 --%>
		<table cellpadding="0" cellspacing="0" id="buttonArea">
			<tr>
				<%-- 隐藏所按的保存按钮是哪个的标志--%>
				<td>
					<input type="hidden" name=buttonSaveType value="1">
				</td>
			</tr>
			<tr>
				<c:if test="${param.editType=='DELETE'}">
					<td>
						<input type="button" name=buttonDelete class='button' value="<s:text name='button.delete.value' />" onclick="submitDelete();">
						&nbsp;&nbsp;
					</td>
				</c:if>
				<c:choose>
					<c:when test="${param.claimFlag=='noDisplay'}">
						<td>
							<input type="button" name=buttonSave class='button' value="<s:text name='button.close.value' />" onclick="window.close();">
							&nbsp;&nbsp;
						</td>
					</c:when>
					<c:otherwise>
						<%
							request.setAttribute("CLASSCODE_D_A", com.sinosoft.claim.common.ConstantCodes.CLASSCODE_D_A);
											request.setAttribute("CLASSCODE_D_B", com.sinosoft.claim.common.ConstantCodes.CLASSCODE_D_B);
						%>
						<c:if test="${requestScope.prpLcompensate.classCode!=CLASSCODE_D_A&&requestScope.prpLcompensate.classCode!=CLASSCODE_D_B}">
							<td>
								<%--<input type="button" name="print" class='bigbutton' value="<s:text name='button.printInsClaimInfo.value' />" onclick="printWindow(fm.registno.value, '列印1');" >&nbsp;&nbsp;--%>
							</td>
							<%-- 列印承保理赔信息 --%>
						</c:if>
						<td>
							<!--返回按钮-->
							<input type=button name=buttonBack class='button' value="<s:text name='button.return.value' />" onclick="history.go(-1);">
							&nbsp;&nbsp;
						</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</table>
	</c:when>
	<c:otherwise>
		<table id="buttonArea" cellpadding="0" cellspacing="0">
			<!--在不同状态下，按钮的数量是不同的，-->
			<%-- (1)立案登记 " 暂存" " 取消" "已完成","已完成並提交"
           (2)正处理   "更新" "取消","已完成","已完成並提交","撤消"
           (4)已提交   "返回"
           (5)撤消     "返回"
           因为是自动的，所以先注释掉相应的人员提交 
      --%>
			<tr>
				<%-- 隐藏所按的保存按钮是哪个的标志--%>
				<td>
					<input type="hidden" name=buttonSaveType value="1">
				</td>
			</tr>
			<tr>
				<c:if test="${requestScope.prpLcompensate.classCode!=CLASSCODE_D_A&&requestScope.prpLcompensate.classCode!=CLASSCODE_D_B}">
					<td>
						<%--<input type="button" name="print" class='bigbutton' value="<s:text name='button.printInsClaimInfo.value' />" onclick="printWindow(fm.registno.value, '列印1');">&nbsp;&nbsp;--%>
					</td>
					<%-- 列印承保理赔信息 --%>
				</c:if>
				<c:if test="${requestScope.prpLcompensate.status!='4'}">
					<td>
						<!--保存按钮-->
						<input type="button" name=buttonSave class='button' value="<s:text name='button.save.value' />" onclick="return saveForm(this,'2');">
						&nbsp;&nbsp;
					</td>
					<td>
						<!--提交的对象和内容-->
						<%
							/**如果涉及联共保，提交前，先调用联共保分摊按钮的方法*/
						%>
						<c:choose>
							<c:when test="${not empty requestScope.coinsFlag}">
								<c:choose>
									<c:when test="${requestScope.coinsFlag=='1'||requestScope.coinsFlag=='2'||requestScope.coinsFlag=='3'}">
										<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>"
											onclick="if(!fm.countFlag.value=='1'){alert('请先產生联共保分摊信息！')}else{return saveForm(this,'4')};">
											&nbsp;&nbsp;
									</c:when>
									<c:otherwise>
										<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="return saveForm(this,'4');">
										&nbsp;&nbsp;
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="return saveForm(this,'4');">&nbsp;&nbsp;
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<!--取消按钮-->
						<input type="reset"  name="buttonCancel" class="button" value="<s:text name='button.cancel.value'/>" onclick="return history.back();" />
						&nbsp;&nbsp;
					</td>
					<c:if test="${param.status=='0'}">
						<td>
							<!--放弃按钮style="width:33%"-->
							<input type=button name=buttonGiveup class='button' value="<s:text name='button.giveUpTask.value' />" onclick="taskGiveup();">
							&nbsp;&nbsp;
						</td>
						<%-- 放弃任务 --%>
					</c:if>
					<%@include file="/pages/common/sendUndwrt/SendUndwrtButton.jsp"%>
				</c:if>
				<c:if test="${requestScope.prpLcompensate.status=='4'}">
					<c:choose>
						<c:when test="${param.ifclose=='true'}">
							<td>
								<input type="button" name=buttonSave class='button' value="<s:text name='button.close.value' />" onclick="window.close();">
								&nbsp;&nbsp;
							</td>
						</c:when>
						<c:otherwise>
							<td>
								<!--取消按钮-->
								<input type=button name=buttonBack class='button' value="<s:text name='button.return.value' />" onclick="return history.back();">
								&nbsp;&nbsp;
							</td>
						</c:otherwise>
					</c:choose>
				</c:if>
			</tr>
		</table>
	</c:otherwise>
</c:choose>