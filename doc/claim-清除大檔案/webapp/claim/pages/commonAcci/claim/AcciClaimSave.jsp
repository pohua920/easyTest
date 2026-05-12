<%@ include file="/common/taglibs.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
function showButton() { //此函数用於在加载时按钮可用
    if (fm.buttonCloseReturn.value == "close") {
        fm.buttonClose.disabled = false;
    } else {
        fm.buttonReturn.disabled = false;
    }
}

 //reason：1.交验员工是否有权限申请调查;
 //reason: 2.交验是否上次调查还没有结束，没结束，不允许再次提调
 //reason：3.打开申请调查录入页面      

function appcheck() {
    if (fm.cancheck.value == "1") { //"0"无权限申请调查,"1"有权限申请（有核赔权限）
        if (fm.checkNotOver.value == "1") { //"1"上次调查还没有结束;"0"没有提起过调查，或上次调查结束；
            alert("上次申请调查还没有处理，不能再次申请调查");
            return false;
        } else {
            applySchedule('${prpLclaim.registNo}', 'claim',
                '${param.swfLogFlowID}', '${param.swfLogLogNo}',
                '${param.status}', '03', '${prpLclaim.claimNo}');
        }

    } else { //无核赔权限
        alert("只有有核赔权限的人才可以申请调查！");
        return false;
    }
}

 //显示列印窗口

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

    var newWindow = window
        .open(
            strUrl,
            strWindowName,
            'width=' + pageWidth + ',height=' + pageHeight + ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
    newWindow.focus();
    return newWindow;
}
</script>
<%-- 保存通用按钮 --%>
<table id="buttonArea" class=common style="display:">
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
		</td>
	</tr>
	<tr>
		<td><%--
			<input type="button" name="print" class='bigbutton' value="<s:text name='button.printInsClaimInfo.value' />" onclick="printWindow('${prpLclaim.registNo}', '列印1');">
			&nbsp;&nbsp;--%>
			<%--列印承保理赔信息--%>
		</td>
		<c:if test="${prpLclaim.status!='4'}">
			<td class=button style="width: 25%" align="center">
				<!--保存按钮-->
				<input type="button" name=buttonSave class='button' value="<s:text name='button.save.value' />" " onclick="return saveForm(this,'2');">
			</td>
			<td class=button style="width: 25%" align="center">
				<!--提交按钮-->
				<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value' />" onclick="return saveForm(this,'4');">
			</td>
			<td class=button style="width: 25%" align="center">
				<!--取消按钮-->
				<input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value' />" onclick="return resetForm();">
			</td>
			<%@include file="/pages/common/sendUndwrt/SendUndwrtButton.jsp"%>
		</c:if>
		<c:if test="${prpLclaim.status=='3'}">
			<td class=button style="width: 33%" align="center">
				<!--保存按钮-->
				<input type="button" name=buttonSave class='button' value="<s:text name='button.save.value' />" onclick="return saveForm(this,'3');">
			</td>
			<td class=button style="width: 33%" align="center">
				<!--回退按钮-->
				<input type="button" name=buttonSave class='button' value="<s:text name='button.return.value' />" onclick="return saveForm(this,'2');">
			</td>
			<td class=button style="width: 33%" align="center">
				<!--提交按钮-->
				<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value' />" onclick="return saveForm(this,'4');">
			</td>
			<td class=button style="width: 33%" align="center">
				<!--取消按钮-->
				<input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value' />" onclick="return resetForm();">
			</td>
		</c:if>
		<!--放弃任务(只对待处理状态)-->
		<c:if test="${param.status!=null&&param.status=='0'}">
			<td class=button align="center">
				<!--放弃按钮style="width:33%"-->
				<input type=button name=buttonGiveup class='button' value="<s:text name='button.giveUpTask.value' />" onclick="taskClaimGiveup();">
			</td>
			<%--放弃任务--%>
		</c:if>
		<!--放弃任务-->
		<c:if test="${prpLclaim.status=='4'}">
			<c:if test="${param.ifclose=='true'}">
				<td class=button style="width: 33%" align="center">
					<input type="button" name=buttonClose class='button' value="<s:text name='button.close.value' />" onclick="window.close();">
					<input type="hidden" name=buttonCloseReturn value="close">
				</td>
			</c:if>
			<c:if test="${param.ifclose!='true'}">
				<td  style="width: 33%" align="center">
					<!--取消按钮-->
					<input type="button" name="buttonBack" class='button' value="<s:text name='button.return.value' />" onclick="history.back(0)">
				</td>
			</c:if>
		</c:if>
	</tr>
</table>
