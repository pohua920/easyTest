<%--
****************************************************************************
* DESC       ：立案除外处理页面
* AUTHOR     ：中科软
* CREATEDATE ： 2013-03-07
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html >
	<head>
		<!--对title处理-->
		<title><s:text name="title.excludeClaimBeforeEdit.ExceptProcessing" /> <%--立案除外处理  --%></title>
		<%-- 页面样式  --%>
		<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
		<%-- 标签页样式 
		<jsp:include page="/behaviors/MpcStyle.jsp" />
		--%>
		<script src="${ctx}/pages/excludeClaim/js/ExcludeClaimEdit.js"></script>
	</head>
	<body>
		<form name="fm" action="" method="post">
			<div style="width: 100%; height: 100%; overflow: auto;" class="common">
				<table class="common" style="width: 100%" cellspacing="1" cellpadding="5">
					<thead>
						<tr>
							<td class="subformtitle" colspan="7">
								<s:text name="excludeClaim.CaseInformation" />
							</td>
							<%--案件信息  --%>
						</tr>
						<tr>
							<td class="centertitle">
								<s:text name="prpLregist.registNo" />
							</td>
							<%-- 报案号 --%>
							<td class="centertitle">
								<s:text name="db.prpLlawsuit.policyNo" />
							</td>
							<%-- 保单号 --%>
							<td class="centertitle">
								<s:text name="db.prpLarrearageNew.riskCode" />
							</td>
							<%-- 险种 --%>
							<td class="centertitle">
								<s:text name="db.prpLclaim.insuredName" />
							</td>
							<%-- 被保险人 --%>
							<td class="centertitle">
								<s:text name="regist.prpLregist.damageTime" />
							</td>
							<%-- 出险时间 --%>
							<td class="centertitle">
								<s:text name="prpLregist.reportHour" />
							</td>
							<%-- 报案时间 --%>
							<td class="centertitle">
								<s:text name="compensate.insuranceComCode" />
							</td>
							<%-- 承保机构代码 --%>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty prpLregist}">
							<tr>
								<td class="input" align="center" style="width: 15%">
									<input class='input' type='hidden' name='userCode' value="${user.userCode }">
									<input class='input' type='hidden' name='registNo' value="${prpLregist.registNo}">
									${prpLregist.registNo}
								</td>
								<td class="input" align="center" style="width: 15%">
									${prpLregist.policyNo}
								</td>
								<td class="input" align="center" style="width: 10%">
									${prpLregist.riskCode}
								</td>
								<td class="input" align="center" style="width: 15%">
									${prpLregist.insuredName}
								</td>
								<td class="input" align="center" style="width: 15%">
									<%--  ${prpLregist.damageStartDate}--%>
									<rc:rcDate name="damageStartDate" value="${prpLregist.damageStartDate}" class="readonly" readonly="true" wdatePicker="false" style="width:110px" />
								</td>
								<td class="input" align="center" style="width: 15%">
									<%-- ${prpLregist.reportDate}--%>
									<rc:rcDate name="reportDate" value="${prpLregist.reportDate}" class="readonly" readonly="true" wdatePicker="false" style="width:110px" />
								</td>
								<td class="input" align="center" style="width: 15%">
									${prpLregist.comCode}
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
				<TABLE class="common" cellpadding="3" cellspacing="1">
					<tr>
						<td class="subformtitle" colspan="6">
							<s:text name="regist.prpLregist.registMain" />
						</td>
						<%-- 基本信息 --%>
					</tr>
					<TR>
						<TD class='left'>
							<s:text name="regist.prpLregist.damageTime" />：
						</td>
						<%-- 出险时间 --%>
						<TD class='right'>
							<%-- ${prpLregist.damageStartDate}--%>
							<rc:rcDate name="damageStartDate" value="${prpLregist.damageStartDate}" class="readonly" readonly="true" wdatePicker="false" />
						</TD>
						<TD class='left'>
							<s:text name="regist.prpLregist.damageCode" />：
						</td>
						<%-- 出险原因 --%>
						<TD class='right'>
							${prpLregist.damageName}
						</td>
						<TD class='left'>
							<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckCase" />
						</td>
						<%--  事故原因--%>
						<TD class='right'>
							${prpLregist.damageTypeName}
						</td>
					</TR>
					<TR>
						<TD class='left'>
							<s:text name="db.prpLregist.linkerName" />：
						</td>
						<%-- 联系人 --%>
						<TD class='right'>
							${prpLregist.linkerName}
						</td>
						<TD class='left'>
							<s:text name="db.prpLregist.damageAddress" />：
						</td>
						<%-- 出险地点 --%>
						<TD class='right' colspan="3">
							${prpLregist.damageAddress}
						</td>
					</TR>
				</TABLE>
				<TABLE cellpadding="3" cellspacing="0" class="common"
					id=ExcludeClaimReasonTable>
					<thead>
						<tr>
							<TD class=formtitle>
								<s:text name="excludeClaim.ExceptReason" />
							</td>
							<%--除外原因  --%>
						</tr>
					</thead>
					<tbody>
						<TR align=center>
							<TD>
								<textarea type="text" name="excludereason" style="width: 400; height: 130"></textarea>
							</TD>
						</TR>
					</tbody>
				</table>
				<TABLE id="buttonTable" cellpadding="0" cellspacing="0" align='center' style="width: 40%;">
					<TR>
						<TD align='center'>
							<input class='button' type='button' name='button' value='<s:text name="button.submit.value"/>' onclick="return insert();">
						</TD>
						<%-- 提交 --%>
						<TD align='center'>
							<input class='button' type='button' name='button' value='<s:text name="button.cancel.value"/>' onclick="history.back(-1);">
						</TD>
						<%-- 取消 --%>
					</TR>
				</TABLE>
			</div>
		</form>
	</body>
</html>