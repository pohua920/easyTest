<%--
****************************************************************************
* DESC       ：简易赔案查询输入界面
* AUTHOR     ： claim
* CREATEDATE ： 2007-06-22
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
							zhangshi		20080512				修改模糊查询为右模糊查询
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<%@page import="com.sinosoft.sysframework.common.datatype.*"%>
<html:html locale="true">
<head>
<title><s:text name="title.quickCaseBeforeEdit.simpleClaimInfoQuery" /></title>
<%--简易赔案处理信息查询页面--%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script language="javascript">
  <%--案件状态标志处理--%>
  <!--
  function submitForm()
  {
     var ref="";
      for(i=0;i<fm.checkFlag.length;i++){
        if(fm.checkFlag[i].checked==true){
           ref = ref+fm.checkFlag[i].value+",";
        }
      }
      fm.caseFlag.value = ref;
      fm.submit();//提交
    fm.submit();//提交
  }
  //-->
</script>
</head>
<body onload="initPage();">
	<form name="fm" action="/claim/quickCaseQuery.do?editType=EDIT" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="quickCase.simpleClaimInfoQuery" />
				</td>
			</tr>
			<%--查询简易赔案信息--%>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaimApprov.registNo" />
					：
				</td>
				<%--报案号--%>
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="RegistNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.view_larrearage.policyNo" />
					：
				</td>
				<%--保单号--%>
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="PolicyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.operatedate" />
					:
				</td>
				<%--操作时间--%>
				<td class='input'>
					<select class=tag name="OperateDateSign">
						<option value="=">=&nbsp;</option>
						<option value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value=">=">&gt;=</option>
						<option value="<=">&lt;=</option>
						<%--modify by wangliguang --%>
					</select>
					<input type=text name="OperateDate" class="query">
					<img align="absmiddle" style='cursor: hand' src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.OperateDate', '<%=(new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).getYear()-15) %>', '<%=(new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).getYear()+2) %>')">
				</td>
				<td class='title'>
					<s:text name="db.prpLregist.licenseNo" />
					:
				</td>
				<td class='input'>
					<select class=tag name="LicenseNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="LicenseNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="endcase.insuranceAgent" />
					:
				</td>
				<%--承保机构--%>
				<td class='input'>
					<select class=tag name="comCodeSign">
						<option value="=">=</option>
						<!--<option value="*">*</option>-->
					</select>
					<input type=text name="comCode" class="query">
				</td>
				<td class='title'>
					<s:text name="db.view_larrearage.insuredname" />
					:
				</td>
				<%--被保险人名称--%>
				<td class='input'>
					<select class=tag name="InsuredNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="InsuredName" class="query">
				</td>
			</tr>
			<tr style='display: none'>
				<td class='title'>
					<s:text name="quickCase.outStartDate" />
					：
				</td>
				<%--出险开始日期--%>
				<td class='input'>
					<select class=tag name="StartDateSign">
						<option value="=">=&nbsp;</option>
						<option value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value=">=">&gt;=</option>
						<option value="<=">&lt;=</option>
					</select>
					<input name="StartDate" type="text" class="query" description="出险开始日期" description="开始日期" onkeypress="return pressFullDate(event);">
					<img align="absmiddle" style='cursor: hand' src="/claim/images/bgcalendar.gif" onclick="TogglePopupCalendarWindow('document.fm.prpLreplevyReclaimDate', '2003', '2006')">
				</td>
				<td class='title'>
					<s:text name="quickCase.outEndDate" />
					：
				</td>
				<%--出险结束日期--%>
				<td class='input'>
					<select class=tag name="EndDateSign">
						<option value="=">=&nbsp;</option>
						<option value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value=">=">&gt;=</option>
						<option value="<=">&lt;=</option>
					</select>
					<input name="EndDate" type="text" class="query" description="出险结束日期" description="结束日期" onkeypress="return pressFullDate(event);">
					<img align="absmiddle" style='cursor: hand' src="/claim/images/bgcalendar.gif" onclick="TogglePopupCalendarWindow('document.fm.prpLreplevyValidDate', '2003', '2006')">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="quickCase.simpleStatu" />
					:
				</td>
				<%--简易赔案状态--%>
				<td class='input' colspan="3">
					<input type=checkbox name="checkFlag" value='01'>
					<s:text name="common.status.intoState" />
					<%--入口状态--%>
					<input type=checkbox name="checkFlag" value='02'>
					<s:text name="common.status.saveState" />
					<%--暂存状态--%>
					<!--<input type=checkbox name="checkFlag" value='03'>已提交且核赔未通过-->
					<!--<input type=checkbox name="checkFlag" value='04'>已提交且核赔通过-->
				</td>
			</tr>
			<tr>
				<input name="caseFlag" type="hidden">
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<%--"="符号，必须精确查询。--%>
					<br>
					<s:text name="prompt.schedule.query2" />
					<%--"=*"符号，前匹配後模糊的查询。--%>
				</td>
			</tr>
		</table>
	</form>
</body>
</html:html>