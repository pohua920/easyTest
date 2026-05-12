<%--
****************************************************************************
* DESC       :已决赔案查询条件录入界面
* AUTHOR     :
* CREATEDATE : 2004-10-08
* MODIFYLIST :   Name       Date            Reason/Contents

****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<%@ page import = "java.util.Calendar"%>
<%@ page import = "com.sinosoft.sysframework.common.datatype.DateTime"%>
<%
	//得到本周周一与周日的日期
	//Date date = new Date();
	//String strMonday = ""; //date.getMondayOFWeek();
	//String strSunday = ""; //date.getSundayOFWeek();

	String strSunday = DateTime.current().toString();
	String strMonday = new DateTime(DateTime.current().addMonth(-1), DateTime.YEAR_TO_DAY).toString();
%>

<html locale="true">
<head>
  <title><s:text name="title.claimBeforeEdit.titleName" /></title>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  

   <script language=javascript>
    function submitForm()
    {
      fm.submit();
    }
    function resetForm()
    {
      fm.reset();
    }

  </script>


</head>
<body onload="initPage();">
	<form name="fm" action="/claim/EndCaseSumQuery.do?" method="post" onsubmit="return validateForm(this);">
		<table width="90%" border="0" align="center" cellpadding="6" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="query.definitelyClaimQuery" />
				</td>
			</tr>
			<%--已决赔案查询 --%>
			<tr>
				<td class='title'>
					<s:text name="db.prpDclass.classCode" />
					:
				</td>
				<%--险类代码 --%>
				<td class=input>
					<select class=tag name="ClassCodeSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input name="ClassCode" maxlength="3" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpDrisk.riskCode" />
					：
				</td>
				<%--险种代码 --%>
				<td class=input>
					<select class=tag name="RiskCodeSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input name="RiskCode" class="query" maxlength="3">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpCmain.policyNo" />
					：
				</td>
				<%--保单号码 --%>
				<td class=input>
					<select class=tag name="PolicyNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input name="PolicyNo" class="query" maxlength="22">
				</td>
				<td class=title>
					<s:text name="query.claimNumber" />
					：
				</td>
				<%--赔案号码 --%>
				<td class="input">
					<select class=tag name="ClaimNoCodeSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input name="ClaimNo" class="query" maxlength="22">
				</td>
			</tr>
			<!--
    <tr>
      <td class=title>要保人代码:</td>
      <td class=input>
        <select class=tag name="AppliCodeSign">
          <jsp://include page="/common/pub/UIStringOption.html" />
        </select><input name="AppliCode" class="common" maxlength="16"></td>
      <td class=title>要保人名称:</td>
      <td class=input>
        <select class=tag name="AppliNameSign">
          <jsp://include page="/common/pub/UIStringOption.html" />
        </select><input name="AppliName" class="common" maxlength="120"></td>
    </tr>
-->
			<tr>
				<td class=title>
					<s:text name="db.prpCmain.insuredCode" />
					：
				</td>
				<%--被保险人代码 --%>
				<td class=input>
					<select class=tag name="InsuredCodeSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input name="InsuredCode" class="query" maxlength="16">
				</td>
				<td class=title>
					<s:text name="db.prpLCMain.insuredName" />
					：
				</td>
				<%--被保险人名称 --%>
				<td class=input>
					<select class=tag name="InsuredNameSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input name="InsuredName" class="query" maxlength="120">
				</td>
			</tr>
			<tr>
				<td class=title>
					<s:text name="query.initiateStartDate" />
					：
				</td>
				<%--立案起始日期 --%>
				<td class=input>
					<select class=tag name="StartDateSign">
						<option value="=">=&nbsp;</option>
						<option value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value=">=" SELECTED>&gt;=</option>
						<option value="<=">&lt;=</option>
					</select>
					<input name="StartDate" class="query" maxlength="21" value=<%=strMonday%>>
					<img align="absmiddle" style='cursor: hand' src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.StartDate', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
				</td>
				<td class=title>
					<s:text name="query.initiateEndDate" />
					：
				</td>
				<%--立案截至日期 %>
      <td class=input>
        <select class=tag   name="EndDateSign" >
           <option value="=">=&nbsp;</option>
          <option value=">">&gt;&nbsp;</option>
          <option value="<">&lt;&nbsp;</option>
          <option value=">=">&gt;=</option>
          <option value="<="  SELECTED>&lt;=</option>
          </select> <input name="EndDate" class="query"  maxlength="21" value=<%=strSunday%> > <img align="absmiddle" style='cursor: hand' src="/claim/images/bgcalendar.gif" onclick="TogglePopupCalendarWindow('document.fm.EndDate', '<%=(new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).getYear()-15) %>', '<%=(new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).getYear()+2) %>')"></td>
    </tr>

     <tr>
      <td class=title><s:text name="button.edit.value" />结案起始日期：</td>
      <td class=input>
        <select class=tag  name="CaseStartDateSign">
          <option value="=" >=&nbsp;</option>
          <option value=">">&gt;&nbsp;</option>
          <option value="<">&lt;&nbsp;</option>
          <option value=">=" SELECTED>&gt;=</option>
          <option value="<="  >&lt;=</option>
        </select> <input name="CaseStartDate" class="query"  maxlength="21"> <img align="absmiddle" style='cursor: hand' src="/claim/images/bgcalendar.gif" onclick="TogglePopupCalendarWindow('document.fm.CaseStartDate', '<%=(new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).getYear()-15) %>', '<%=(new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).getYear()+2) %>')"></td>
      <td class=title><s:text name="button.edit.value" />结案截至日期：</td>
      <td class=input>
        <select class=tag   name="CaseEndDateSign">
          <option value="=">=&nbsp;</option>
          <option value=">">&gt;&nbsp;</option>
          <option value="<">&lt;&nbsp;</option>
          <option value=">=">&gt;=</option>
          <option value="<="  SELECTED>&lt;=</option>
        </select> <input name="CaseEndDate" class="query"  maxlength="21" > <img align="absmiddle" style='cursor: hand' src="/claim/images/bgcalendar.gif" onclick="TogglePopupCalendarWindow('document.fm.CaseEndDate', '<%=(new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).getYear()-15) %>', '<%=(new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).getYear()+2) %>')"></td>
    </tr>


    <tr>
	  <td class=title><s:text name="button.edit.value" />业务来源:</td>
      <td class="input">
        <select class=tag  name="CurBusinessNatureSign">
            <option value="*">*</option>
            <option value="=">=</option>
          </select> <input name="BusinessNature" class="query"   maxlength="22"></td>
      <td class=title><s:text name="button.edit.value" />币别:</td>
      <td class=input>
        <select class=tag  name="CurrencySign">
            <option value="*">*</option>
            <option value="=">=</option>
          </select> <input name="Currency" class="query"   maxlength="3"></td>
    </tr>
<!--
      <td class=title><s:text name="button.edit.value" />总保险金额:</td>
      <td class=input>
        <select class=tag name="SumAmountSign">
                    <jsp://include page="/common/pub/UINumOption.html" />
        --</select><input name="SumAmount" class="common" onkeypress="return pressDecimal(event)"></td>
    </tr>
    <tr>
      <td class=title><s:text name="button.edit.value" />总保险费:</td>
      <td class=input>
        <select class=tag name="SumPremiumSign">
                    <jsp://include page="/common/pub/UINumOption.html" />
        </select><input name="SumPremium" class="common" onkeypress="return pressDecimal(event)"></td>
-->
    <tr>
      <td class=title><s:text name="button.edit.value" />出单机构:</td>
      <td class=input>
        <select class=tag  name="MakeComSign">
            <option value="*">*</option>
            <option value="=">=</option>
          </select> <input name="MakeCom" class="query"   maxlength="8"></td>
      <td class=title><s:text name="button.edit.value" />归属机构:</td>
      <td class=input>
        <select class=tag  name="ComCodeSign">
            <option value="*">*</option>
            <option value="=">=</option>
          </select> <input name="ComCode" class="query"   maxlength="8"></td>
    </tr>
    <tr>
		<td class=title><s:text name="button.edit.value" />业务员代码:</td>
      <td class=input>
        <select class=tag   name="Handler1CodeSign">
            <option value="*">*</option>
            <option value="=">=</option>
          </select> <input name="Handler1Code" class="query"   maxlength="10"></td>
	  <td class=title><s:text name="button.edit.value" />经办人代码:</td>
      <td class=input>
        <select class=tag  name="HandlerCodeSign">
            <option value="*">*</option>
            <option value="=">=</option>
          </select> <input name="HandlerCode" class="query"   maxlength="10"></td>
    </tr>
    <tr>
      <td class=title><s:text name="button.edit.value" />操作员代码:</td>
      <td class=input>
        <select class=tag  name="OperatorCodeSign">
            <option value="*">*</option>
            <option value="=">=</option>
          </select> <input name="OperatorCode" class="query"   maxlength="10"></td>
<!--
    <tr>
      <td class=title>签单日期:</td>
      <td class=input>
        <select class=tag name="OperateDateSign">
          <jsp://include page="/common/pub/UIDateOption.html" />
        </select><input name="OperateDate" class="common" maxlength="21" onkeypress="return pressDatetime(event)" onblur="checkBetweenDate(this,'YEAR','DAY');"></td>
      <td class=title>计算机输单日期:</td>
      <td class=input>
        <select class=tag name="InputDateSign">
                    <jsp://include page="/common/pub/UIDateOption.html" />
        </select><input name="InputDate" class="common" maxlength="21" onkeypress="return pressDatetime(event)" onblur="checkBetweenDate(this,'YEAR','DAY');"></td>
    </tr>
-->
	  <td class=title><s:text name="button.edit.value" />代理人代码:</td>
      <td class=input>
        <select class=tag  name="AgentCodeSign">
            <option value="*">*</option>
            <option value="=">=</option>
          </select> <input name="AgentCode" class="query"   maxlength="12"></td>
<!--
      <td class=title>新保/续保:</td>
      <td class=input>
        <input type="checkbox" name="OthFlag1" value="0">新保
        <input type="checkbox" name="OthFlag1" value="1">续保
      </td>
-->
    </tr>

    <tr>
      <td colspan=2 class=button>
        <input type="button" name="buttonSubmit" class="button" value=" <s:text name="button.edit.value" />查 询 " onclick="submitForm()">
      </td>
      <td colspan=2 class=button>
        <input type="button" name="buttonCancel" class="button" value=" <s:text name="button.edit.value" />清 除 " onclick="resetForm()">
      </td>
    </tr>

    </table>
    <input type="hidden" name="editType" value="EDIT">
    <input type="hidden" name="conditions" value="">

  </form>
</body>
</html>