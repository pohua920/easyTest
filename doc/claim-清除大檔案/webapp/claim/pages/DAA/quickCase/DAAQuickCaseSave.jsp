<% String quickCaseStatus= request.getParameter("quickCaseStatus");%>
<% String registNo =request.getParameter("registNo"); %>

<SCRIPT   LANGUAGE="VBScript">   
    function showMessage(str)
showMessage = msgbox(str, 3)
//是 6   
//否 7   
//取消 2   
end
function < /SCRIPT> 

 <script language="javascript">
 <!-- 
  / * * * @description注销拒赔的申请，之前的判断。 * @param报案号 * @
return通过返回 true, 否则返回false * /
 
function ApplyCancel(registNo,quickCaseStatus,fieldObject)
{/ / alert("first")
//modify by wangliguang begin
disablebutton();
var strMsg = "";
//判断输入的报案号
if (registNo == "") {
	alert("没有得到报案号,请重新操作。");
	undisablebutton();
	return false;
}
//判断输入的状态
if (quickCaseStatus == "") {
	alert("没有得到简易赔案的状态,请重新操作。");
	undisablebutton();
	return false;
}
//alert("ok")
//提示是否转赔案？
if (quickCaseStatus == "01" || quickCaseStatus == "02") {
	strMsg = "确定要将报案号为'" + registNo + "'的简易赔案申请註銷或拒赔吗?";
	//alert("ok2")
	//执行cancelApply的操作
	if (confirm(strMsg)) {
		var riskCode = fm.riskCode.value;
		if (riskCode == "") {
			riskCode = fm.compelRiskCode.value;
		}
		fm.action = "/claim/claimBeforeCancel.do?keyIn=<%=registNo%>&nodeType=quickCase&quickCaseStatus=<%=quickCaseStatus%>&editType=CANCEL&riskCode=" + riskCode;
		fm.submit();
		//fieldObject.disabled=true;
		return true;
	}
}
undisablebutton();
return false;
//modify by wangliguang end
}
/**
 *@description 入口转一般赔案的操作
 *@param       报案号
 *@return      通过返回true,否则返回false
 */

function changeToComm(registNo, quickCaseStatus, fieldObject) {
	var strMsg = "";
	var strAction = "/claim/quickCaseChangeToCommon.do?registNo=" + registNo + "&quickCaseStatus=" + quickCaseStatus + "&callType=LIST";
	//判断输入的报案号
	if (registNo == "") {
		alert("没有得到报案号,请重新操作。");
		return false;
	}
	//判断输入的状态
	if (quickCaseStatus == "") {
		alert("没有得到简易赔案的状态,请重新操作。");
		return false;
	}
	//提示是否转赔案？
	if (quickCaseStatus == "01") {
		strMsg = "确定要将报案号为'" + registNo + "'的简易赔案转为一般赔案吗?";
		//执行back的操作
		if (confirm(strMsg)) {
			fm.action = "/claim/quickCaseChangeToCommon.do?registNo=<%=registNo%>&quickCaseStatus=<%=quickCaseStatus%>";
			fm.submit();
			//fieldObject.disabled=true;
			return true;
		}
	}

	//提示是否转赔案？
	if (quickCaseStatus == "02") {
		strMsg = "要将报案号为'" + registNo + "'的简易赔案转为一般赔案到查勘处吗？选'是'转到查勘，选'否'转到定损",
		strMsg = strMsg + "~r~n选'取消'返回不做操作!";
		strMsg = strMsg + "若转到查勘，则不保留定损和理算数据 "
		strMsg = strMsg + "若转到定损，则不保留理算数据 "
		var blreturn = showMessage(strMsg);
		//取消操作
		if (blreturn == '2') {
			return false;
		}
		//转移到查勘的操作
		if (blreturn == '6') {
			fm.nodeType.value = 'check';
			//     fieldObject.disabled=true;
			fm.action = "/claim/quickCaseChangeToCommon.do?registNo=<%=registNo%>&quickCaseStatus=<%=quickCaseStatus%>&nodeType=check";
			fm.submit();
			return true;

		}
		//转移到定损的操作
		if (blreturn == '7') {
			fm.nodeType.value = 'certa';
			//     fieldObject.disabled=true;
			fm.action = "/claim/quickCaseChangeToCommon.do?registNo=<%=registNo%>&quickCaseStatus=<%=quickCaseStatus%>&nodeType=certa";
			fm.submit();
			return true;
		}
	}
	return false;
}

function backCheckSubmit(field) {
	var backCheckTypeFlag = fm.backCheckTypeFlag.value;
	var registNo = fm.registNo.value;
	fm.action = "/claim/backCheckQuerySave.do?registNo=" + registNo + "&backCheckTypeFlag=" + backCheckTypeFlag;
	fm.submit();
	return true;
}

function rollBack(field) {
	//alert("aaaaaa");
	//商业险计算书
	var prpLcompensateNo = fm.prpLcompensateNo.value;
	//alert(prpLcompensateNo);
	//交强险计算书
	var compelPrpLcompensateNo = fm.compelPrpLcompensateNo.value;
	//alert("11111===="+compelPrpLcompensateNo);
	fm.action = "/claim/compensateRollback.do?editType=SHOW&prpLcompensateNo=" + prpLcompensateNo + "&compelPrpLcompensateNo=" + compelPrpLcompensateNo;
	fm.submit();
	return true;
}
 //-->
  </script>
<TABLE cellpadding="0" cellspacing="0" border="0">
	<TR>
		<% if ("ADD".equals(editType)||"EDIT".equals(editType)){%>
		<TD>
			<input type="button" class=button name="eCertify" value="<s:text name="button.electronicDocuments.value"/>" onClick="openCertify(fm.registNo.value)">
			<!-- 电子单证 -->
		</TD>
		<TD>
			<input type="button" name=buttonCheckSave class='button' value="<s:text name="button.quickCase.surveyTheStaging"/>" onclick="return saveQuickForm(this,'1');">
			<!-- 查勘暂存 -->
		</TD>
		<TD>
			<input type="button" name=buttonCertainLossSave class='button' value="<s:text name="button.quickCase.damageTheStaging"/>" onclick="return saveQuickForm(this,'2');">
			<!-- 定损暂存 -->
		</TD>
		<TD>
			<input type="button" name=buttonCertainLossSaveAll class='button' value="<s:text name="button.quickCase.allTemporary"/>" onclick="return saveQuickForm(this,'3');">
			<!-- 暂存全部 -->
		</TD>
		<TD>
			<input type="button" name=buttonCertainLossSubmit class='button' value="<s:text name="button.quickCase.submit"/>" onclick="return saveQuickForm(this,'4');">
			<!-- 提  交 -->
		</TD>
		<input type='hidden' value='<%=quickCaseStatus%>'>
		<% 
	    if ("01".equals(quickCaseStatus)||"02".equals(quickCaseStatus)){%>
		<TD>
			<input type="button" name="buttonChangeToCommon" class='button' value="<s:text name="button.quickCase.intoGeneralClaim"/>"
				onclick="return changeToComm('<%=registNo%>','<%=quickCaseStatus%>',this);">
			<!-- 转为一般赔案 -->
		</TD>
		<TD>
			<input type="button" name="buttonCancelApply" class='button' value="<s:text name="button.quickCase.applyCancellationReject"/>"
				onclick="return ApplyCancel('<%=registNo%>','<%=quickCaseStatus%>',this);">
			<!-- 申请注销/拒赔 -->
		</TD>
		<% }%>
		<% }%>
		<% if ("SHOW".equals(editType)){%>
		<logic:notEmpty name="prpLBackCheckDto">
			<logic:notEqual name="prpLBackCheckDto" property="backCheckType" value="3">
				<td>
					<s:text name="quickCase.reviewOpinions" />
					<!-- 复查意见 -->
				</td>
				<td align="center" width='150px'>
					<html:select name="prpLBackCheckDto" property="backCheckType" style="width:60%">
						<html:options collection="backCheckTypeList" property="value" labelProperty="label" />
					</html:select>
				</td>
			</logic:notEqual>
			<input type="hidden" name="backCheckTypeFlag" value="<bean:write name='prpLBackCheckDto' property='backCheckType'/>">
			<td>
				<s:text name="quickCase.afterComparableInformation" />
				<input style="width: 400px" type='text' name="prpLBackCheckDtoRemark" value="<bean:write name='prpLBackCheckDto' property='remark'/>">
				<!-- 复堪信息 -->
			</td>
			<td align="center">
				<input type="button" class=button name="button" value="<s:text name="button.submit.value"/>" onClick="backCheckSubmit(this);">
				<!-- 提交 -->
			</td>
		</logic:notEmpty>
		<logic:empty name="prpLBackCheckDto">
			<input type="hidden" name="backCheckTypeFlag" value="">
		</logic:empty>
		<td>
			<!--取消按钮-->
			<input type=button name=buttonCancel class='button' value="<s:text name="button.return.value"/>" onclick="history.go(-1);">
			&nbsp;&nbsp;
			<!-- 返回 -->
			<input type=button name=rollBack class='button' value="<s:text name="button.holdBack.value"/>" onclick="rollBack(this);">
			&nbsp;&nbsp;
			<!-- 抓回 -->
		</td>
		<% }%>
	</TR>
</TABLE>