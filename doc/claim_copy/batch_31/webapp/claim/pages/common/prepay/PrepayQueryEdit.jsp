<!--
****************************************************************************
* DESC       ：预赔查询条件输入界面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-06-07
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<title><s:text name="title.registBeforeEdit.titleName" /></title>
<!-- 公用函数 -->
<script src="${ctx}/common/js/Common.js"></script>
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<script language="javascript">
<!--案件状态标志处理-->
	function submitForm() {
		var ref1 = "";
		var ref2 = "";
		for (i = 0; i < fm.status.length; i++) {

			if (fm.status[i].checked == true) {
				ref1 = ref1 + fm.status[i].value + ",";
			}
		}

		for (i = 0; i < fm.UnderWriteFlag.length; i++) {

			if (fm.UnderWriteFlag[i].checked == true) {
				ref2 = ref2 + fm.UnderWriteFlag[i].value + ",";
			}
		}
		fm.caseFlag.value = ref1;
		fm.prepayFlag.value = ref2;
		fm.submit();//提交
	}
//-->
</script>
</head>
<body onload="initPage();">
	<form name="fm" action="${ctx}/prepayQuery.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="prepay.infoQuery" />
				</td>
			</tr>
			<%--查询预赔信息--%>
			<tr>
				<td class='title'>
					<s:text name="db.prpLprepay.preCompensateNo" />：
				</td>
				<%--预赔计算书号--%>
				<td class='input'>
					<select class=tag name="PrepayNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="PrepayNo" class="query">
				</td>
				<td class='title'>
					<s:text name="check.claimNum" />：
				</td>
				<%--赔案号--%>
				<td class='input'>
					<select class=tag name="ClaimNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="ClaimNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.view_larrearage.policyNo" />：
				</td>
				<%--保单号--%>
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="PolicyNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.operatedate" />：
				</td>
				<%--操作时间--%>
				<td class='input'>
					<select class=tag name="OperateDateSign">
						<option value="=">=&nbsp;</option>
						<option value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value=">=">&gt;=</option>
						<option value="<=">&lt;=</option>
					</select>
					<input type=text name="OperateDate" class="Wdate" onClick="WdatePicker()">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.licenseNo" />:
				</td>
				<td class='input'>
					<select class=tag name="LicenseNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="LicenseNo" class="query">
				</td>
				<td class='title' style="width: 30%" colspan="2">
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLprepay.underWriteFlag" />:
				</td>
				<%--核赔标志--%>
				<td colspan="3" class='input'>
					<input type="hidden" name="prepayFlag">
					<input type="checkbox" name="UnderWriteFlag" value="0">
					<s:text name="compensate.initValue" />
					<%--初始值--%>
					<input type="checkbox" name="UnderWriteFlag" value="1">
					<s:text name="compensate.pass" />
					<%--通过--%>
					<input type="checkbox" name="UnderWriteFlag" value="2">
					<s:text name="compensate.notPass" />
					<%--不通过--%>
					<input type="checkbox" name="UnderWriteFlag" value="3">
					<s:text name="compensate.withoutHePei" />
					<%--无需核赔--%>
					<input type="checkbox" name="UnderWriteFlag" value="9">
					<s:text name="compensate.stayHePei" />
					<%--待核赔--%>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="certainLoss.prpLscheduleMainWF.caseState" />
				</td>
				<%--案件状态：--%>
				<td colspan="3" class='input'>
					<input type="hidden" name="caseFlag">
					<input type="checkbox" name="status" value="1">
					<s:text name="common.status.untreated" />
					<%--未处理--%>
					<input type="checkbox" name="status" value="2">
					<s:text name="common.status.intreating" />
					<%--正处理--%>
					<input type="checkbox" name="status" value="3">
					<s:text name="common.status.treated" />
					<%--已处理--%>
					<input type="checkbox" name="status" value="4">
					<s:text name="common.status.submited" />
					<%--已提交--%>
					<input type="checkbox" name="status" value="5">
					<s:text name="common.status.revoked" />
					<%--已撤消--%>
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="SHOW">
	</form>
</body>
</html>