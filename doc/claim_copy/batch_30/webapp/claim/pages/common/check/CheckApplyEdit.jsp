<%--
****************************************************************************
* DESC	   ：调查申请信息处理页面
* AUTHOR	 ：理赔组
* CREATEDATE ：2005-06-14
* MODIFYLIST ：   Name	   Date			Reason/Contents
****************************************************************************

--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
<!--对title处理-->
<html locale="true">
<!--调查申请信息处理入口-->
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
<%@include file="/common/meta_js.jsp"%>
<script language=javascript>
	function saveForm(){
		if (fm.checkNotOver.value == "1") { //"1"上次调查还没有结束;"0"没有提起过调查，或上次调查结束；
			alert("前一次申請調查尚未結束，不能再次申請調查！");
			return false;
		}
		var context = fm.context.value
		context = rightTrim(context);
		context = leftTrim(context);
		if (context.length < 1) {
			errorMessage("調查內容不能為空！");
			fm.context.focus();
			return false;
		}
		fm.buttonSave.disabled = true;
		//window.opener.fm.AcciClaimFlag.value = 'N';
		fm.submit();
	}
</script>
</head>
<body onload="initPage();">
	<form name=fm action="${ctx}/check/checkAcciEdit.do" method="post" onsubmit="return validateForm(this);">
		<s:token></s:token>
		<table border="0" align="center" cellpadding="5" cellspacing="1" class=common>
			<tr>
				<td class="formtitle" colspan="4">
					<s:text name="check.investInfoProcess" />
				</td>
			</tr>
			<%--调查申请信息处理--%>
			<tr>
				<td class="title" colspan="4">
					<s:text name="check.investInfoProcess" />
					<%--调查申请信息处理--%>
					<input type="hidden" name="swfLogFlowID" class="common" value="${swfLogAcciDto.id.flowID}"> <input type="hidden" name="swfLogLogNo" class="common" value="${swfLogAcciDto.id.logNo}"> <input type="hidden" name="nodeStatus"
						class="common" value="${swfLogAcciDto.nodeStatus}"> <input type="hidden" name="nodeName" class="common" value="${swfLogAcciDto.nodeName}"> <input type="hidden" name="nodeType" class="common" value="${swfLogAcciDto.nodeType}">
					<input type="hidden" name="checkNotOver" class="common" value="${checkNotOver }">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="db.prpLclaim.caseType" />
					:
				</td>
				<%--案件性质--%>
				<td class="input" colspan=3>
					<input type="text" name="claimStatusName" class="readonly" title="案件性質" readonly="true" value="${ swfLogAcciDto.nodeName}${swfLogAcciDto.nodeStatusName}">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="db.prpLclaim.registNo" />
					:
				</td>
				<%--备案号码--%>
				<td class="input">
					<input type="text" name="registNo" class="readonly" title="備案號碼" maxlength="22" readonly="true" value="${prpLacciCheck.registNo}">
				</td>
				<td class="title">
					<s:text name="db.prpLclaim.claimNo" />
					:
				</td>
				<%--赔案号--%>
				<td class="input">
					<input type=text name="claimNo" title="" class="readonly" readonly="true" value="${prpLacciCheck.claimNo}">
				</td>
			</tr>
			<tr>
				<td class="title">計算書號碼:</td>
				<%--计算书号--%>
				<td class="input">
					<input type="text" name="compensateNo" class="readonly" title="計算書號碼" readonly="true" value="${prpLacciCheck.compensateNo}">
				</td>
				<td class="title">
					<s:text name="claim.applicant" />
					:
				</td>
				<%--申请人--%>
				<td class="input">
					<input type="hidden" name="specialCaseDealerCode" title="賠案申請人" class="readonly" value="${swfLogAcciDto.handlerCode}"> <input type=text name="specialCaseDealerName" title="賠案申請人" class="readonly" value="${swfLogAcciDto.handlerName}">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="claim.applyTime" />
					：
				</td>
				<%--申请时间--%>
				<td class="input" colspan=2>
					<rc:rcDate name="specialCaseflowInTime" title="申請時間" class="readonly" readonly="readonly" defaultValue="0" />
				</td>
				<td class="input" colspan=1>
					<input type=button name=buttonScheduleHistory class='bigbutton' value="<s:text name='button.everySurvey.value' />" onclick="showScheduleHistory('${prpLacciCheck.registNo}');">
					<%--历次调查--%>
				</td>
			</tr>
			<tr>
				<td class="title" colspan="1">
					<s:text name="check.surveyPeople" />
					：
				</td>
				<%--调查人--%>
				<td class="input" colspan=3>
					<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
					<input type=text name="checkerCode" title="調查人" class="common" maxLength="100">
				</td>
			</tr>
			<tr>
				<td class="title" colspan="1">
					<s:text name="check.surveyContent" />
					：
				</td>
				<%--调查内容--%>
				<td class="input" colspan=3>
					<!--textarea name='checkContext' wrap="hard"  title="調查內容" rows=15 cols=80 class=common ></textarea-->
					<input type="text" name="context" title="調查內容" class="input" maxLength=255>
				</td>
			</tr>
		</table>
		</tr>
		</table>
		<table class="common" align="center">
			<tr>
				<td class=button align="center">
					<!--确定按钮-->
					<input type=button name=buttonSave class='button' value="<s:text name='button.apply.value' />" onclick="return saveForm();">
					<%--申 请--%>
				</td>
				<td class=button align="center">
					<!--取消按钮-->
					<input type="button" name=buttonCancel class='button' value="<s:text name='button.return.value' />" onclick="javascript:history.back();">
				</td>
				<!--取消按钮-->
				<td class=button align="center">
					<c:if test="${swfLogAcciDto.nodeType=='regis'}">
						<input type=hidden name="certiType" title="發起節點" class="readonly" value="01">
						<input type=hidden name="certiNo" title="發起節點的業務號碼" class="readonly" value="${prpLacciCheck.registNo}">
					</c:if>
					<c:if test="${swfLogAcciDto.nodeType=='claim'}">
						<c:if test="${swfLogAcciDto.nodeStatus!='0'}">
							<input type=hidden name="certiType" title="發起節點" class="readonly" value="03">
							<input type=hidden name="certiNo" title="發起節點的業務號碼" class="readonly" value="${prpLacciCheck.claimNo}">
						</c:if>
						<c:if test="${swfLogAcciDto.nodeStatus=='0'}">
							<input type=hidden name="certiType" title="發起節點" class="readonly" value="03">
							<input type=hidden name="certiNo" title="發起節點的業務號碼" class="readonly" value="${prpLacciCheck.registNo}">
						</c:if>
					</c:if>
					<c:if test="${swfLogAcciDto.nodeType=='compe'}">
						<input type=hidden name="certiType" title="發起節點" class="readonly" value="05">
						<input type=hidden name="certiNo" title="發起節點的業務號碼" class="readonly" value="${prpLacciCheck.claimNo}">
					</c:if>
					<c:if test="${swfLogAcciDto.nodeType=='compp'}">
						<input type=hidden name="certiType" title="發起節點" class="readonly" value="07">
						<input type=hidden name="certiNo" title="發起節點的業務號碼" class="readonly" value="${prpLacciCheck.compensateNo}">
					</c:if>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>