<%--
****************************************************************************
* DESC       ：延期申请操作页面
* AUTHOR     ：liuwei
* CREATEDATE ：2011-01-05
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<html:html locale="true">
<head>
<title><s:text name="title.archive.readDelay" /></title>
<!-- 调阅延期 -->
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script type="text/javascript">
	function submitForm() {
		fm.buttonSave.disabled = "disabled";
		fm.submit();
	}
</script>
</head>
<body class="interface" onload="initPage();">
	<form name=fm action="${ctx}/archive/archiveApply.do" method="post" onsubmit="return validateForm(this);">
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td class=formtitle colspan="4">
					<s:text name="title.archive.readDelay" />
				</td>
				<!-- 调阅延期 -->
			</tr>
			<tr>
				<td class="title">
					<s:text name="check.claimNum" />：
				</td>
				<!-- 赔案号 -->
				<td class="input">
					<input name="claimNo" class="readonly" readonly value="${prpLDocArchiveDto.claimNo}">
				</td>
				<td class="title">
					<s:text name="prompt.queRegist.PolicyNo" />：
				</td>
				<!-- 保单号 -->
				<td class="input">
					<input name="policyNo" class="readonly" readonly value="${prpLDocArchiveDto.policyNo}">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="db.prpCmain.insuredName" />:
				</td>
				<!-- 被保险人名称： -->
				<td class="input">
					<input name="insuredName" class="readonly" readonly value="${prpLDocArchiveDto.insuredName}">
				</td>
				<td class="title">
					<s:text name="db.prpLclaim.endCaseDate" />：
				</td>
				<!-- 结案日期 -->
				<td class="input">
					<%-- <input name="endCaseDate" class="readonly" readonly value="${prpLDocArchiveDto.endCaseDate}">--%>
					<rc:rcDate name="endCaseDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLDocArchiveDto.endCaseDate}" />
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="compensate.compel.paymentAmount" />：
				</td>
				<!-- 赔款金额 -->
				<td class="input">
					<input name="sumDutyPaid" class="readonly" readonly value="${prpLDocArchiveDto.sumDutyPaid}">
				</td>
				<td class="title">
					<s:text name="archive.expectedReturnDate" />
				</td>
				<!-- 预计归还日期： -->
				<td class="input">
					<%--<input name="estimateReturnDate" class="readonly" readonly value="${prpLDocArchiveDto.estimateReturnDate}"> --%>
					<rc:rcDate name="estimateReturnDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLDocArchiveDto.estimateReturnDate}" />
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="archive.delayTime" />
				</td>
				<!-- 延期时间： -->
				<td class="input" colspan="3">
					<input type="radio" name="applyDeferPeriod" value="1" checked="checked">
					<s:text name="archive.oneWeek" />
					<!-- 一周 -->
					<input type="radio" name="applyDeferPeriod" value="2">
					<s:text name="archive.oneMonth" />
					<!-- 一月 -->
					<input type="radio" name="applyDeferPeriod" value="3">
					<s:text name="archive.oneQuarter" />
					<!-- 一季 -->
				</td>
				<td class="title" colspan="2"></td>
			</tr>
		</table>
		<table class="common" align="center">
			<tr>
				<td class="button">
					<input type="button" name="buttonSave" value=" <s:text name="button.apply.value"/>" class="button" onclick="return submitForm();">
					<!-- 申 请  -->
				</td>
				<td class="button">
					<input type="reset" name="buttonCancel" value=" <s:text name="button.cancel.value"/>" class="button">
					<!-- 取 消  -->
				</td>
			</tr>
		</table>
		<!-- 隐藏域 -->
		<input type="hidden" name="editType" value="extension">
		<input type="hidden" name="sumDutyPaid" value="${prpLDocArchiveDto.sumDutyPaid}">
		<input type="hidden" name="estimateReturnDate" value="${prpLDocArchiveDto.estimateReturnDate}">
	</form>
</body>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
</html:html>
