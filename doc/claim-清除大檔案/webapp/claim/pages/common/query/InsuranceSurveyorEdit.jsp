<%--
****************************************************************************
* DESC       ：公估师新增界面
* AUTHOR     ： liuwei
* CREATEDATE ： 2011-05-07
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<html locale="true">
<head>
<title><s:text name="query.publicAddView" /></title>
<%--公估师新增界面 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="/claim/common/js/Process.js"></script>
<script language="javascript">
	function submitForm(editType) {
		if (fm.NewComCode.value == "") {
			alert("請選擇公估機構！");
			return false;
		}

		if (fm.ComCName.value == "") {
			alert("請輸入中文名稱！");
			return false;
		}
		fm.action = "/claim/externalAgency/insuranceSurveyor.do?editType=" + editType;
		fm.submit();//提交
	}
</script>
</head>
<body onload="initPage();">
	<form name="fm" action="/claim/externalAgency/insuranceSurveyor.do" method="post" onsubmit="return validateForm(this);">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td class="formtitle" align="center" colspan="4">
					<s:text name="query.addPublic" />
					<%--增加公估师 --%>
				</td>
			</tr>
			<tr>
				<td class="title" align="right" style="width: 15%">
					<s:text name="query.publicTeam" />
					：
					<%--公估机构 --%>
				</td>
				<td class="input" style="width: 35%">
					<input type=text name="NewComCode" class="codecode" title="公估機構代碼" value="" ondblclick="code_CodeSelect(this, 'getExternalAgency','0,1','Y');"
						onchange="code_CodeSelect(this, 'getExternalAgency','0,1','Y');" onkeyup="code_CodeSelect(this, 'getExternalAgency','0,1','Y');" style="width: 19%">
					<input type=text name="NewComCName" class="codename" title="公估機構名稱" value="" style="width: 40%" ondblclick="code_CodeSelect(this, 'getExternalAgency','-1,0','Y','N');"
						onchange="code_CodeSelect(this, 'getExternalAgency','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'getExternalAgency','-1,0','Y','N');">
					<img src="/claim/images/bgMarkMustInput.jpg" complete="complete" />
				</td>
				<td class="title" align="right" style="width: 15%">
					<s:text name="query.chineseName" />
					：
					<%--中文名称 --%>
				</td>
				<td class="input" style="width: 35%">
					<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
					<input type=text name="ComCName" class="input" maxlength="100" style="width: 220px">
					<img src="/claim/images/bgMarkMustInput.jpg" complete="complete" />
				</td>
			</tr>
			<tr>
				<td class="title" align="right" style="width: 15%">
					<s:text name="query.englishName" />
					<%--英文名称 --%>
					：
				</td>
				<td class="input" style="width: 35%">
					<input type=text name="ComEName" class="input" style="width: 220px" maxlength="40">
				</td>
				<td class="title" align="right" style="width: 15%">
					<s:text name="db.prpLregist.phoneNumber" />
					<%--联系电话 --%>
					：
				</td>
				<td class="input" style="width: 35%">
					<input type=text name="Telephone" class="input" maxlength="25" style="width: 220px">
				</td>
			</tr>
			<tr>
				<td class="title" align="right" style="width: 15%">E-mail：</td>
				<td class="input" style="width: 35%">
					<input type=text name="EMail" class="input" style="width: 220px">
				</td>
				<td class="title" align="right" style="width: 15%">
					<s:text name="db.prpLregistText.flag" />
					：
					<%--标志位 --%>
				</td>
				<td class="input" width="35%">
					<input type=radio name="Validstatus" value="1" checked>
					<s:text name="query.flagTrue" />
					<%--有效 --%>
					<input type=radio name="Validstatus" value="0">
					<s:text name="query.flagFalse" />
					<%--无效 --%>
				</td>
			</tr>
			<tr>
				<td class=button style="width: 40%" colspan="4" align="center">
					<input type="button" name=buttonSave class='button' value="<s:text name="button.save.value" />" onClick="submitForm('insertSave');">
					<%--保存 --%>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>