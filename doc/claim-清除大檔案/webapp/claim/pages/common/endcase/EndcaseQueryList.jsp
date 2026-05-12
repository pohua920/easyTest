<!--
****************************************************************************
* DESC       ：结案信息查询结果
* AUTHOR     ：理赔组
* CREATEDATE ：2004-06-28
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
							zhangshi		20130201			修改*操作符为=*将like '%%'改为左%右匹配查询
****************************************************************************/
-->
<!-- 这个页面不在使用 查询使用异步查询，2013-03-10 -->
<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.sinosoft.claim.dto.domain.PrpLcaseNoDto"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<html locale="true">
<script language="javascript">
<!--案件状态标志处理-->
	function submitForm() {
		if ((fm.InsuredNameSign.value == "=" && fm.InsuredName.value.length > 0)
				|| (fm.RegistNoSign.value == "=" && fm.RegistNo.value.length > 0)
				|| (fm.PolicyNoSign.value == "=" && fm.PolicyNo.value.length > 0)
				|| (fm.ClaimNoSign.value == "=" && fm.ClaimNo.value.length > 0)
				|| (fm.CaseNoSign.value == "=" && fm.CaseNo.value.length > 0)) {
			//输入了一个条件，可以查
		} else if ((fm.RegistNoSign.value == "=*" && fm.RegistNo.value.length > 8)
				|| (fm.PolicyNoSign.value == "=*" && fm.PolicyNo.value.length > 8)
				|| (fm.ClaimNoSign.value == "=*" && fm.ClaimNo.value.length > 8)
				|| (fm.CaseNoSign.value == "=*" && fm.CaseNo.value.length > 8)) {
			if ("D" == getClassCodeType(fm.RegistNo.value.substr(1, 2))
					|| "D" == getClassCodeType(fm.PolicyNo.value.substr(1, 2))
					|| "D" == getClassCodeType(fm.ClaimNo.value.substr(1, 2))
					|| "D" == getClassCodeType(fm.CaseNo.value.substr(1, 2))) {
				alert("车险必须精确查询！");
				return false;
			} else {
				//非车险可以前9位模糊查询
			}
		} else {
			alert("车险必须输入赔案号、结案号、报案号、保单号、被保险人其中一项精确查询！\n 非车险可以用赔案号、结案号、报案号或者保单号的前9位进行模糊查询！");
			return false;
		}
		fm.searchFlag.value = "true";
		fm.pageNo.value = "1";//查询後页面设为1
		fm.submit();//提交
	}
//-->
</script>
<head>
<app:css />
<title><s:text name="title.claimBeforeEdit.queryClaim" /></title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="/claim/common/js/showpage.js"> </script>
<html:base />
</head>
<body onload="initPage();">
	<form name="fm" action="/claim/endcaseQuery.do" method="post" onSubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="title.endcaseBeforeEdit.titleName" />
				</td>
			</tr>
			<!-- 查询结案信息 -->
			<tr>
				<td class='title'>
					<s:text name="check.claimNum" />:
				</td>
				<!-- 赔案号 -->
				<td class='input'>
					<select class=tag name="ClaimNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="ClaimNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLcompensate.caseNo" />:
				</td>
				<!-- 结案号 -->
				<td class='input'>
					<select class=tag name="CaseNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="CaseNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLcfee.policyNo" />:
				</td>
				<!-- 保单号 -->
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="PolicyNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.operatedate" />:
				</td>
				<!-- 操作时间 -->
				<td class='input'>
					<select class=tag name="OperateDateSign">
						<%@ include file="/pages/common/pub/CommonStringOption.jsp"%>
					</select>
					<input type=text name="OperateDate" class="query">
					<img align="absmiddle" style='cursor: hand' src="/claim/images/bgcalendar.gif"
						onClick="TogglePopupCalendarWindow('document.fm.OperateDate', '<%=new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY).getYear() - 15%>', '<%=new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY).getYear() + 2%>')">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLrepairFee.registNo" />：
				</td>
				<!-- 报案号 -->
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="RegistNo" class="query">
				</td>
				<td class='title'>
					<s:text name="endcase.insuranceAgent" />:
				</td>
				<!-- 承保机构 -->
				<td class='input'>
					<select class=tag name="comCodeSign">
						<option value="=">=</option>
						<!--<option value="*">*</option>-->
					</select>
					<input type=text name="comCode" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="prpLclaim.claimDate" />:
				</td>
				<!-- 立案时间 -->
				<td class='input'>
					<select class=tag name="claimDateSign">
						<%@ include file="/pages/common/pub/CommonStringOption.jsp"%>
					</select>
					<input type=text name="claimDate" class="query">
					<img align="absmiddle" style='cursor: hand' src="/claim/images/bgcalendar.gif"
						onClick="TogglePopupCalendarWindow('document.fm.claimDate', '<%=new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY).getYear() - 15%>', '<%=new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY).getYear() + 2%>')">
				</td>
				<!--报案查询增加被保险人查询条件-->
				<td class='title'>
					<s:text name="db.prpCmain.insuredName" />:
				</td>
				<!-- 被保险人名称 -->
				<td class='input'>
					<select class=tag name="InsuredNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="InsuredName" class="query">
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<br>
					<!-- "="符号，必须精确查询。 -->
					<s:text name="prompt.schedule.query2" />
					<br>
					<!-- "=*"符号，前匹配後模糊的查询。 -->
					<s:text name="endcase.query1" />
					<br>
					<!-- 车险必须输入赔案号、结案号、报案号、保单号、被保险人其中一项精确查询！ -->
					<s:text name="endcase.query2" />
					<!-- 非车险可以用赔案号、结案号、报案号或者保单号的前9位进行模糊查询！ -->
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
					<input type="hidden" name="nodeType" value="<%=request.getParameter("nodeType")%>">
					<input type="hidden" name="editType2" value="SHOW">
					<input name="searchFlag" type="hidden" id="searchFlag">
				</td>
			</tr>
		</table>
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td colspan=5 class="formtitle">
					<s:text name="endcase.query2" />
					查询结案信息
				</td>
			</tr>
			<tr>
				<td class="centertitle">
					<s:text name="endcase.query2" />
					赔案号
				</td>
				<td class="centertitle">
					<s:text name="endcase.query2" />
					结案号
				</td>
				<td class="centertitle">
					<s:text name="endcase.query2" />
					保单号
				</td>
				<td class="centertitle">
					<s:text name="endcase.query2" />
					结案员
				</td>
				<td class="centertitle">
					<s:text name="endcase.query2" />
					结案时间
				</td>
			</tr>
			<%
				int index = 0;
			%>
			<logic:notEmpty name="prpLcaseNoDto" property="caseList">
				<logic:iterate id="caseList1" name="prpLcaseNoDto" property="caseList">
					<%
						if (index % 2 == 0)
									out.print("<tr class=listodd>");
								else
									out.print("<tr class=listeven>");
					%>
					<tr class=common>
						<td align="center">
							<a
								href="/claim/endcaseFinishQueryList.do?prpLendcaseEndcaseNo=<bean:write name='caseList1' property='claimNo'/>&editType=<bean:write name='prpLcaseNoDto' property='editType'/>&riskCode=<bean:write name="caseList1" property="riskCode"/>&ClaimNoSign=*">
								<bean:write name="caseList1" property="claimNo" />
							</a>
						</td>
						<td align="center">
							<bean:write name="caseList1" property="caseNo" />
						</td>
						<td align="center">
							<bean:write name="caseList1" property="policyNo" />
						</td>
						<td align="center">
							<bean:write name="caseList1" property="endCaserCode" />
						</td>
						<td align="center">
							<bean:write name="caseList1" property="endCaseDate" />
						</td>
					</tr>
					<%
						index++;
					%>
				</logic:iterate>
			</logic:notEmpty>
			<tr class="listtail">
				<td colspan="5">
					<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<bean:define id="pageview" name="prpLcaseNoDto" property="turnPageDto" />
							<%
								PrpLcaseNoDto prpLcaseNoDto = (PrpLcaseNoDto) request.getAttribute("prpLcaseNoDto");
								int curPage = prpLcaseNoDto.getTurnPageDto().getPageNo();
							%>
							<%@include file="/common/pub/TurnOverPage.jsp"%>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="<%=request.getParameter("editType")%>">
	</form>
</body>
</html>