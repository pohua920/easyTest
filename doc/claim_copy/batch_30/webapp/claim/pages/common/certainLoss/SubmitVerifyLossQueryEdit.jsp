<%--
****************************************************************************
* DESC       ： 已核损案件查询
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-07-13
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>

<html:html locale="true">
<head>
  <title><s:text name="title.claimBeforeEdit.titleName" /></title>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  
  <script language="javascript">
  <%--案件状态标志处理--%>
  <!--
	function submitForm() {
    fm.submit();//提交
  }
//-->
</script>
</head>

<body  onload="initPage();">
<form name="fm" action="/claim/verifyLossQuery.do"  method="post" onsubmit="return validateForm(this);">

    <table  border="0" align="center" cellpadding="5" cellspacing="1"  class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="certainLoss.nuclearDamageQuery" />
				</td>
			</tr>
			<%--查询核损信息--%>
      <tr>
				<td class='title'>
					<s:text name="prompt.queRegist.RegistNo" />：
				</td>
				<%--报案号--%>
        <td class='input' >
        <select class=tag name="RegistNoSign" >
            <option value="*">*</option>
            <option value="=">=</option>
					</select>
					<input type=text name="RegistNo" class="query">
        </td>
				<td class='title'>
					<s:text name="db.prpCprofitDetail.policyno" />：
				</td>
				<%--保单号--%>
        <td class='input' >
        <select class=tag name="PolicyNoSign" >
            <option value="*">*</option>
            <option value="=">=</option>
					</select>
					<input type=text name="PolicyNo" class="query">
        </td>


      </tr>
      <tr>
				<td class='title'>
					<s:text name="db.prpLregist.licenseNo" />:
				</td>
        <td class='input' >
        <select class=tag name="LicenseNoSign" >
            <option value="*">*</option>
            <option value="=">=</option>
					</select>
					<input type=text name="LicenseNo" class="query">
        </td>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.operatedate" />:
				</td>
				<%--操作时间--%>
        <td class='input' >
          <select class=tag name="OperateDateSign" >
	          <option value="=">=&nbsp;</option>
	          <option value=">">&gt;&nbsp;</option>
	          <option value="<">&lt;&nbsp;</option>
	          <option value=">=">&gt;=</option>
	          <option value="<=">&lt;=</option>
					</select>
					<input type=text name="OperateDate" class="query">
					<img style='cursor: hand' src="/claim/images/bgcalendar.gif" align="absmiddle"
						onclick="TogglePopupCalendarWindow('document.fm.OperateDate', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
        </td>
      </tr>
			<tr>
				<td class='button' colspan="3">
          <input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
				</td>
			</tr>
        <input type="hidden" name="caseFlag" value="4,">
    </table>
    <input type="hidden" name="editType" value="SHOW">
    <input type="hidden" name="nodeType" value="<%= request.getParameter("nodeType") %>">
  </form>
</body>

</html:html>