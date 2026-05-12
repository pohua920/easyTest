<%--
****************************************************************************
* DESC       ：简易赔案信息查询结果
* AUTHOR     ：claim
* CREATEDATE ：2007-06-22
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
							zhangshi		20080512				修改模糊查询为右模糊查询
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@ page import="com.sinosoft.claim.dto.domain.PrpLquickCaseDto"%>
<html:html locale="true">
<head>
<app:css />
<title><s:text name="title.claimBeforeEdit.queryClaim" /></title>
<script src="/claim/common/js/showpage.js"> </script>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<html:base />
<script language="javascript">
  <%--案件状态标志处理--%>  function submitForm()
  {
    if((fm.InsuredNameSign.value=="="&&fm.InsuredName.value.length>0)
  	 ||(fm.LicenseNoSign.value=="="&&fm.LicenseNo.value.length>0)
  	 ||(fm.RegistNoSign.value=="="&&fm.RegistNo.value.length>0)
  	 ||(fm.PolicyNoSign.value=="="&&fm.PolicyNo.value.length>0)){
  	 //输入了一个条件，可以查
  	 }else if((fm.RegistNoSign.value=="=*"&&fm.RegistNo.value.length>8)
  	        ||(fm.PolicyNoSign.value=="=*"&&fm.PolicyNo.value.length>8)){
  	 	if("D"==getClassCodeType(fm.RegistNo.value.substr(1,2))
  	 	 ||"D"==getClassCodeType(fm.PolicyNo.value.substr(1,2))){
  	 		alert("车险必须精确查询！");
  	 		return false;
  	 	}else{
  	 		//非车险可以前9位模糊查询
  	 	}  		
  	}else{
  		alert("車險必須輸入備案號碼、保單號碼、牌照號碼、被保險人其中一項精確查詢！\n非車險可以用備案號碼或者保單號碼的前9位進行模糊查詢！");
  		return false;
  	}
     var ref="";
      for(i=0;i<fm.checkFlag.length;i++){
        if(fm.checkFlag[i].checked==true){
           ref = ref+fm.checkFlag[i].value+",";
        }
      }
	  fm.searchFlag.value="true";
      fm.pageNo.value="1";//查询後页面设为1
      fm.caseFlag.value = ref;
      fm.submit();//提交
  }
  </script>
</head>
<body onload="initPage();">
	<form name="fm" action="/claim/quickCaseQuery.do" method="post" onSubmit="return validateForm(this);">
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
					</select>
					<input type=text name="OperateDate" class="query">
					<img align="absmiddle" style='cursor: hand' src="/claim/images/bgcalendar.gif"
						onClick="TogglePopupCalendarWindow('document.fm.OperateDate', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
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
					<img align="absmiddle" style='cursor: hand' src="/claim/images/bgcalendar.gif" onClick="TogglePopupCalendarWindow('document.fm.prpLreplevyReclaimDate', '2003', '2006')">
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
					<img align="absmiddle" style='cursor: hand' src="/claim/images/bgcalendar.gif" onClick="TogglePopupCalendarWindow('document.fm.prpLreplevyValidDate', '2003', '2006')">
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
					<input type=checkbox name="checkFlag" value='03'>
					<s:text name="common.status.submited" />
					<%--已提交--%>
					<!--<input type=checkbox name="checkFlag" value='03'>已提交且核赔未通过-->
					<!--<input type=checkbox name="checkFlag" value='04'>已提交且核赔通过-->
				</td>
			</tr>
			<tr>
				<input name="caseFlag" type="hidden">
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
					<input type="hidden" name="searchFlag" value="">
					<input type="hidden" name="pageFlag">
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<%--"="符号，必须精确查询。--%>
					<br>
					<s:text name="prompt.schedule.query2" />
					<%--"=*"符号，前匹配後模糊的查询。--%>
					<br>
					<s:text name="prompt.schedule.query3" />
					<br>
					<%--车险必须输入报案号、保单号、车牌号、被保险人其中一项精确查询！--%>
					<s:text name="prompt.schedule.query4" />
					<%--非车险可以用报案号或者保单号的前9位进行模糊查询！--%>
				</td>
			</tr>
		</table>
		<table class="common" cellpadding="6" cellspacing="1">
			<tr>
				<td colspan=7 class="formtitle">
					<s:text name="quickCase.simpleClaimInfoQueryResult" />
				</td>
			</tr>
			<%--简易赔案信息查询结果--%>
			<tr>
				<td class="centertitle">
					<s:text name="db.prpLclaimApprov.registNo" />
				</td>
				<%--报案号--%>
				<td class="centertitle">
					<s:text name="db.view_larrearage.policyNo" />
				</td>
				<%--保单号--%>
				<td class="centertitle">
					<s:text name="db.prpLregist.insuredName" />
				</td>
				<%--被保险人--%>
				<td class="centertitle">
					<s:text name="certainLoss.thirdCarLoss.prpLthirdCarLicenseNo" />
				</td>
				<%--车牌号--%>
				<td class="centertitle">
					<s:text name="quickCase.simpleStateTime" />
				</td>
				<%--转简易赔案时间--%>
				<td class="centertitle">
					<s:text name="quickCase.simpleStatu" />
				</td>
				<%--简易赔案状态--%>
				<td class="centertitle">
					<s:text name="quickCase.operator" />
				</td>
				<%--操作人员--%>
			</tr>
			<%
				int index = 0;
			%>
			<logic:notEmpty name="prpLquickCaseDto" property="prpLquickCaseList">
				<logic:iterate id="quickCaseList1" name="prpLquickCaseDto" property="prpLquickCaseList">
					<%
						if (index % 2 == 0)
										out.print("<tr class=listodd>");
									else
										out.print("<tr class=listeven>");
					%>
					<tr class=common>
						<td align="center">
							<a
								href="/claim/quickCaseFinishQueryList.do?nodeType=quickCase&editType=SHOW&registNo=<bean:write name='quickCaseList1' property='registNo'/>&policyNo=<bean:write name="quickCaseList1" property="policyNo"/>&riskCode=<bean:write name="quickCaseList1" property="riskCode"/>">
								<bean:write name="quickCaseList1" property="registNo" />
							</a>
						</td>
						<td align="center">
							<bean:write name="quickCaseList1" property="policyNo" />
						</td>
						<td align="center">
							<bean:write name="quickCaseList1" property="insuredName" />
						</td>
						<td align="center">
							<bean:write name="quickCaseList1" property="licenseNo" />
						</td>
						<td align="center">
							<bean:write name="quickCaseList1" property="startTime" />
						</td>
						<td align="center">
							<logic:equal name="quickCaseList1" property="quickCaseStatus" value='01'>
								<s:text name="common.status.intoState" />
								<%--入口状态--%>
							</logic:equal>
							<logic:equal name="quickCaseList1" property="quickCaseStatus" value='02'>
								<s:text name="common.status.saveState" />
								<%--暂存状态--%>
							</logic:equal>
							<logic:equal name="quickCaseList1" property="quickCaseStatus" value='03'>
								<s:text name="common.status.submittedHePeiNoPoass" />
								<%--已提交且核赔未通过--%>
							</logic:equal>
							<logic:equal name="quickCaseList1" property="quickCaseStatus" value='04'>
								<s:text name="common.status.submittedHePeiPoass" />
								<%--已提交且核赔通过--%>
							</logic:equal>
						</td>
						<td align="center">
							<bean:write name="quickCaseList1" property="operatorCode" />
						</td>
					</tr>
					<%
						index++;
					%>
				</logic:iterate>
			</logic:notEmpty>
			<tr class="listtail">
				<td colspan="7">
					<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<bean:define id="pageview" name="prpLquickCaseDto" property="turnPageDto" />
							<%
								PrpLquickCaseDto prpLquickCaseDto = (PrpLquickCaseDto) request.getAttribute("prpLquickCaseDto");
									int curPage = prpLquickCaseDto.getTurnPageDto().getPageNo();
							%>
							<%@include file="/common/pub/TurnOverPage.jsp"%>
						</tr>
					</table>
				</td>
			</tr>
			<input type="hidden" name="editType" value="<%=request.getParameter("editType")%>">
		</table>
	</form>
</body>
</html:html>