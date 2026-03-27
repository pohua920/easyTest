<%--
****************************************************************************
* DESC       ：资料调阅操作页面
* AUTHOR     ：liuwei
* CREATEDATE ：2011-01-05
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<html locale="true">
<head>
<title><s:text name="title.archive.dataRead" /></title>
<!-- 资料调阅 -->
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
<script type="text/javascript">
    function buttonOnClick1() {
    	var spanId = 'span_Engage_Context';
    	var span = eval(spanId);
    	var ex = window.event.clientX + document.body.scrollLeft; //得到事件的坐标x
    	var ey = window.event.clientY + document.body.scrollTop; //得到事件的坐标y
    	ex = ex - 520;
    	if (ex < 0) {
    		ex = 0;
    	}
    	span.style.left = ex;
    	span.style.top = ey;
    	span.style.display = '';
    }

    function hideSubPage() {
    	var spanId = 'span_Engage_Context';
    	var span = eval(spanId);
    	span.style.display = 'none';
    }

    function submitForm() {
    	fm.buttonSave.disabled = "disabled";
    	fm.submit();
    }
    </script>
</head>
<body class="interface" onload="initPage();">
	<form name=fm action="/claim/archive/archiveApply.do" method="post" onsubmit="return validateForm(this);">
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td class=formtitle colspan="4">
					<s:text name="archive.dataReadApply" />
				</td>
				<!-- 资料调阅申请书 -->
			</tr>
			<tr>
				<td class="title">
					<s:text name="check.claimNum" />：
				</td>
				<!-- 赔案号 -->
				<td class="input">
					<input name="claimNo" class="readonly" readonly value="${prpLDocArchiveLogDto.id.claimNo }">
				</td>
				<td class="title">
					<s:text name="prompt.queRegist.PolicyNo" />：
				</td>
				<!-- 保单号 -->
				<td class="input">
					<input name="policyNo" class="readonly" readonly value="${prpLDocArchiveLogDto.policyNo}">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="db.prpCmain.insuredName" />：
				</td>
				<!-- 被保险人名称 -->
				<td class="input">
					<input name="insuredName" class="readonly" readonly value="${prpLDocArchiveLogDto.insuredName}">
				</td>
				<td class="title">
					<s:text name="db.prpLclaim.endCaseDate" />：
				</td>
				<!-- 结案日期 -->
				<td class="input">
					<input name="endCaseDate" class="readonly" readonly value="${prpLDocArchiveLogDto.endCaseDate}">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="compensate.compel.paymentAmount" />：
				</td>
				<!-- 赔款金额 -->
				<td class="input">
					<input name="sumDutyPaid" class="readonly" readonly value="${prpLDocArchiveLogDto.sumDutyPaid}">
				</td>
				<td class="title">
					<s:text name="archive.applicationName" />：
				</td>
				<!-- 申请人姓名 -->
				<td class="input">
					<input name="applicantName" class="readonly" readonly value="${prpLDocArchiveDto.applicantName}">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="archive.applyDate" />：
				</td>
				<!-- 申请日期 -->
				<td class="input">
					<input name="applyDate" class="readonly" readonly value="${prpLDocArchiveDto.applyDate}">
				</td>
				<td class="title">
					<s:text name="archive.expectedArchivingPeriod" />：
				</td>
				<!-- 预计归档周期 -->
				<td class="input" colspan="3">
					<input type="radio" value="1" disabled="disabled" <c:if test="${prpLDocArchiveLogDto.estimatePeriod == '1'}">checked</c:if>>
					<s:text name="archive.oneWeek" />
					<!-- 一周 -->
					<input type="radio" value="2" disabled="disabled" <c:if test="${prpLDocArchiveLogDto.estimatePeriod == '2'}">checked</c:if>>
					<s:text name="archive.oneMonth" />
					<!-- 一月 -->
					<input type="radio" value="3" disabled="disabled" <c:if test="${prpLDocArchiveLogDto.estimatePeriod == '3'}">checked</c:if>>
					<s:text name="archive.oneQuarter" />
					<!-- 一季 -->
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="archive.applyReadReason" />：
				</td>
				<!-- 申请调阅事由 -->
				<td class="input">
					<select name="applyReason" style="width: 80%" disabled="disabled">
						<option value="1" <c:if test="${prpLDocArchiveLogDto.applyReason == '1'}">selected</c:if>>
							<s:text name="archive.reopenClaim" />
						</option>
						<!-- 重开赔案 -->
						<option value="2" <c:if test="${prpLDocArchiveLogDto.applyReason == '2'}">selected</c:if>>
							<s:text name="archive.copyClaimDocuments" />
						</option>
						<!-- 查阅赔案内容 -->
						<option value="3" <c:if test="${prpLDocArchiveLogDto.applyReason == '3'}">selected</c:if>>
							<s:text name="archive.copyClaimDocumentsFiles" />
						</option>
						<!-- 复印赔案文件 -->
						<option value="4" <c:if test="${prpLDocArchiveLogDto.applyReason == '4'}">selected</c:if>>
							<s:text name="check.other" />
						</option>
						<!-- 其它 -->
					</select>
				</td>
				<td colspan="2" class="input">
					<input type="button" name="button" value=" ... " class="button" onclick="buttonOnClick1();">
					<span id="span_Engage_Context" style='width: 520; display: none; position: absolute; background-color: FFFFFF;'>
						<table class="common">
							<tr>
								<td class="prompttitle" colspan="6">
									<s:text name="archive.readInstructions" />
								</td>
								<!-- 调阅说明 -->
							</tr>
							<tr>
								<td class="prompt" colspan="6">${prpLDocArchiveLogDto.remark}</td>
							</tr>
							<tr>
								<td colspan=6 class="common">
									<input type=button class=button name='button_Engage_Close_Context' value='<s:text name="button.useOClose.value"/>' ACCESSKEY="O" onclick="hideSubPage()">
									<!-- (O)关闭 -->
								</td>
							</tr>
						</table>
					</span>
				</td>
			</tr>
		</table>
		<table class="common" align="center">
			<tr>
				<td class="button">
					<input type="button" name="buttonSave" value=" <s:text name="button.archive.confirm"/>" class="button" onclick="return submitForm();">
					<!-- 确 认  -->
				</td>
			</tr>
		</table>
		<!-- 隐藏域 -->
		<input type="hidden" name="editType" value="retrival">
		<input type="hidden" name="serialNo" value="${prpLDocArchiveLogDto.id.serialNo }">
		<input type="hidden" name="estimatePeriod" value="${prpLDocArchiveLogDto.estimatePeriod}">
	</form>
</body>
</html>
