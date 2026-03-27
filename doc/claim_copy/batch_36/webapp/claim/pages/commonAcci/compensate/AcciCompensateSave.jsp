<%--
****************************************************************************
* DESC       ：通用按钮画面
* AUTHOR     : 理赔组
* CREATEDATE ：2004-10-11
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@include file="/common/taglibs.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
	//显示列印窗口
	function printWindow(registNo, strWindowName) {
    //alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
    return false;
    strUrl = "${ctx}/ClaimPrint.do?printType=CopyPrint&RegistNo=" + registNo;
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
            'width=' + pageWidth + ',height=' + pageHeight + ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
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
				<td>
					<input type="hidden" name=buttonSaveType value="1">
				</td>
			</tr>
			<tr>
				<td>
					<%--<input type="button" name="print" class='bigbutton' value="<s:text name='button.printInsClaimInfo.value' />" onclick="printWindow(fm.registno.value, '列印1');">
					&nbsp;&nbsp;--%>
					<%--列印承保理赔信息--%>
				</td>
				<td class=button style="width: 33%" align="center">
					<!--取消按钮-->
					<input type=button name=buttonCancel class='button' value="<s:text name='button.return.value' />" onclick="javascript:history.go(-1);">
				</td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" width="0" height="0" id="buttonArea">
		</table>
	</c:when>
	<c:otherwise>
		<%-- 保存通用按钮 --%>
		<table id="buttonArea" cellpadding="0" cellspacing="0" width="80%" style="display:">
			<%--
     	 <!--在不同状态下，按钮的数量是不同的，-->
       (1)立案登记 " 暂存" " 取消" "已完成","已完成並提交"
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
				<td>
					<%--<input type="button" name="print" class='bigbutton' value="<s:text name='button.printInsClaimInfo.value' />" onclick="printWindow(fm.registno.value, '列印1');">
					&nbsp;&nbsp;--%>
					<%--列印承保理赔信息--%>
				</td>
				<c:if test="${prpLcompensate.status!='4'}">
					<td class=button style="width: 25%" align="center">
						<%--保存按钮--%>
						<input type="button" name=buttonSave class='button' value="<s:text name='button.save.value' />" onclick="return saveForm(this,'2');">
					</td>
					<td class=button style="width: 25%" align="center">
						<%--如果涉及联共保，提交前，先调用联共保分摊按钮的方法  --%>
						<s:if test="#attr.coinsFlag!=null">
							<s:if test="#attr.coinsFlag==1||#attr.coinsFlag==2||#attr.coinsFlag==3">
								<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="if(!fm.countFlag.value=='1'){alert('请先產生联共保分摊信息！')}else{return saveForm(this,'4')};">
							</s:if>
							<s:else>
								<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="return saveForm(this,'4');">
							</s:else>
						</s:if>
						<s:else>
							<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="return saveForm(this,'4');">
						</s:else>
						<input type='hidden' name="createdCoinsFlag">
					</td>
					<td class=button style="width: 25%" align="center">
						<!--取消按钮-->
						<input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value'/>" onclick="return resetForm();">
					</td>
					<%@include file="/pages/common/sendUndwrt/SendUndwrtButton.jsp"%>
				</c:if>
				<c:if test="${param.status=='0'}">
					<td class=button align="center">
						<!--放弃按钮style="width:33%"-->
						<input type=button name=buttonGiveup class='button' value="<s:text name='button.giveUpTask.value' />" onclick="taskGiveup();">
						<%--放弃任务--%>
					</td>
				</c:if>
				<c:if test="${prpLcompensate.status=='4'}">
					<td class=button style="width: 33%" align="center">
						<!--取消按钮-->
						<input type=button name=buttonBack class='button' value="<s:text name='button.return.value' />" onclick="return history.back();">
					</td>
				</c:if>
			</tr>
		</table>
	</c:otherwise>
</c:choose>