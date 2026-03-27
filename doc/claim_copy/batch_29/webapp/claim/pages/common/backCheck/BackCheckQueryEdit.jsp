<%--
****************************************************************************
* DESC       ：修复验车查询输入界面
* AUTHOR     ： lixiang
* CREATEDATE ： 2005-09-24
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
								zhangshi		20130201			修改*操作符为=*将like '%%'改为左%右匹配查询
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime" %>
<html:html locale="true">
<head>
<title><s:text name="title.claimBeforeEdit.titleName" /></title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script language="javascript">
  <%--案件状态标志处理--%>
  <!--
  function submitForm() {
		if ((fm.LicenseNoSign.value == "=" && fm.LicenseNo.value.length > 0) || (fm.RegistNoSign.value == "=" && fm.RegistNo.value.length > 0) || (fm.PolicyNoSign.value == "=" && fm.PolicyNo.value.length > 0)) {
			//输入了一个条件，可以查
		} else if ((fm.RegistNoSign.value == "=*" && fm.RegistNo.value.length > 8) || (fm.PolicyNoSign.value == "=*" && fm.PolicyNo.value.length > 8)) {
			if ("D" == getClassCodeType(fm.RegistNo.value.substr(1, 2)) || "D" == getClassCodeType(fm.PolicyNo.value.substr(1, 2))) {
				alert("车险必须精确查询！");
				return false;
			} else {
				//非车险可以前9位模糊查询
			}
		} else {
			alert("車險必須輸入備案號碼、保單號碼、牌照號碼其中一項精確查詢！\n 非車險可以用備案號碼或者保單號的前9位進行糢糊查詢！");
			return false;
		}
		var ref = "";
		for (i = 0; i < fm.status.length; i++) {
			if (fm.status[i].checked == true) {
				ref = ref + fm.status[i].value + ",";
			}
		}
		fm.caseFlag.value = ref;
		fm.submit(); //提交
	}
//-->
</script>
</head>
<body class="interface" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="initPage();">
	<form name="fm" action="/claim/verifyLossQuery.do" method="post" onSubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
			<tr>
				<td width="184" height="26" valign="bottom">
					<table width="184" height="19" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="161" class="formtitle">
								<s:text name="backCheck.queryRepairInformation" />
							</td>
							<!-- 查询修复验车信息 -->
						</tr>
					</table>
				</td>
				<td valign="bottom">
					<font color="#666666">&nbsp;
				</td>
			</tr>
		</table>
		<table width="90%" border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" class="common">
			<tr>
				<td class='title' style="width: 15%">
					<s:text name="prompt.queRegist.RegistNo" />:
				</td>
				<!-- 报案号: -->
				<td class='input' style="width: 25%">
					<select class=query name="RegistNoSign" style="width: 40px">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="RegistNo" class="input" style="width: 140px">
				</td>
				<td class='title' style="width: 15%">
					<s:text name="prompt.queRegist.PolicyNo" />:
				</td>
				<!-- 保单号： -->
				<td class='input' style="width: 25%">
					<select class=query name="PolicyNoSign" style="width: 40px">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="PolicyNo" class="input" style="width: 140px">
				</td>
			</tr>
			<tr>
				<td class='title' style="width: 15%">
					<s:text name="db.prpLregist.licenseNo" />:
				</td>
				<td class='input' style="width: 25%">
					<select class=query name="LicenseNoSign" style="width: 40px">
						<%--<option value="*">*</option>--%>
						<option value="=">=</option>
					</select>
					<input type=text name="LicenseNo" class="input" style="width: 140px">
				</td>
				<td class='title' style="width: 15%">
					<s:text name="db.prpLclaimStatus.operatedate" />
				</td>
				<!-- 操作时间 -->
				<td class='input' style="width: 25%">
					<select class=query name="OperateDateSign" style="width: 40px">
						<option value="=">=&nbsp;</option>
						<option value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value=">=">&gt;=</option>
						<option value="<=">&lt;=</option>
					</select>
					<input type=text name="OperateDate" class="input" style="width: 120px">
					<img style='cursor: hand' src="/claim/images/bgcalendar.gif"
						onClick="TogglePopupCalendarWindow('document.fm.OperateDate', '<%=new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY).getYear() - 15%>', '<%=new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY).getYear() + 2%>')">
				</td>
			</tr>
			<tr>
				<td class='title' style="width: 15%">
					<s:text name="db.prpLclaimStatus.status" />:
				</td>
				<!-- 案件状态: -->
				<td colspan="3" class='input' style="width: 65%">
					<input type="hidden" name="caseFlag" value="">
					<input type="checkbox" name="status" value="1">
					<s:text name="common.status.untreated" />
					<!-- 未处理 -->
					<input type="checkbox" name="status" value="2">
					<s:text name="common.status.intreating" />
					<!-- 正处理 -->
					<input type="checkbox" name="status" value="4">
					<s:text name="common.status.submited" />
				</td>
				<!-- 已提交  -->
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<%--"="符号，必须精确查询。 --%>
					<br>
					<s:text name="prompt.schedule.query2" />
					<%-- "=*"符号，前匹配後模糊的查询。--%>
					<br>
					<s:text name="prompt.schedule.query3" />
					<%--车险必须输入报案号、保单号、车牌号其中一项精确查询！ --%>
					<br>
					<s:text name="prompt.schedule.query4" />
					<%-- 非车险可以用报案号或者保单号的前9位进行模糊查询！--%>
				</td>
			</tr>
			<tr>
				<td colspan="4" class='title' style="width: 15%">
					<div align="center">
						<span class="button" style="width: 20%"> <input name="button" type=button class='button' onClick="submitForm();" value="<s:text name='button.query.value' />">
						</span>
					</div>
			</tr>
		</table>
		<input type="hidden" name="editType" value="SHOW">
		<input type="hidden" name="nodeType" value="<%=request.getParameter("nodeType")%>">
	</form>
</body>
<jsp:include page="/common/pub/StaticJavascript.jsp" />
</html:html>
