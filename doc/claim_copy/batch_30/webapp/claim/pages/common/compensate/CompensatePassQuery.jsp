<%--
****************************************************************************
* DESC       ：计算数是否通过核赔查询
* AUTHOR     ：理赔组
* CREATEDATE ：2004-05-19
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>

<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<%@ page import="com.sinosoft.claim.dto.custom.*" %>
<%@ page import="com.sinosoft.claim.dto.domain.*" %>
<html:html locale="true">
<head>
<app:css />
<title><s:text name="title.claimBeforeEdit.queryClaim" /></title>
<script src="/claim/common/js/showpage.js"> </script>
<script language="javascript">
  <%--案件状态标志处理--%>
  <!--
  function submitForm()
  {
    var ref2="";

    for(i=0;i<fm.UnderWriteFlag.length;i++){
      if(fm.UnderWriteFlag[i].checked==true){
        ref2 = ref2+fm.UnderWriteFlag[i].value+",";
      }
    }
    fm.compensateFlag.value = ref2;
    fm.searchFlag.value="true";
    fm.submit();//提交
  }
  //-->
  </script>
<html:base />
</head>
<body>
	<form name="fm" action="/claim/compensatePassQuery.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="title.compensate.queryAdjustInformation " />
				</td>
			</tr>
			<!-- 查询理算信息 -->
			<tr>
				<td class='title'>
					<s:text name="db.prpLcfee.compensateNo" />
					：
				</td>
				<!-- 赔款计算书号 -->
				<td class='input'>
					<select class=tag name="CompensateNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="CompensateNo" class="query">
				</td>
				<td class='title'>
					<s:text name="check.claimNum" />
					：
				</td>
				<!-- 赔案号 -->
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
					<s:text name="prompt.queRegist.PolicyNo" />
					：
				</td>
				<!-- 保单号 -->
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="*">*</option>
						<option value="=">=</option>
					</select>
					<input type=text name="PolicyNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.operatedate" />
					：
				</td>
				<!-- 操作时间 -->
				<td class='input'>
					<select class=tag name="OperateDateSign">
						<option value="=">=&nbsp;</option>
						<option value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value=">=">&gt;=</option>
						<option value="<=">&lt;=</option>
					</select>
					<input type=text name="OperateDate" class="query">
					<img style='cursor: hand' src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.OperateDate', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLprepay.underWriteFlag" />
					:
				</td>
				<!-- 核赔标志 -->
				<td class='input'>
					<input type="hidden" name="compensateFlag">
					<input type="checkbox" name="UnderWriteFlag" value="0">
					<s:text name="compensate.initValue" />
					<!-- 初始值 -->
					<input type="checkbox" name="UnderWriteFlag" value="1">
					<s:text name="compensate.pass" />
					<!-- 通过 -->
					<input type="checkbox" name="UnderWriteFlag" value="2">
					<s:text name="compensate.notPass" />
					<!-- 不通过 -->
					<input type="checkbox" name="UnderWriteFlag" value="3">
					<s:text name="compensate.withoutHePei" />
					<!-- 无需核赔 -->
					<input type="checkbox" name="UnderWriteFlag" value="9">
					<s:text name="compensate.stayHePei" />
					<!-- 待核赔 -->
				</td>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
				</td>
			</tr>
		</table>
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td class="centertitle">
					<s:text name="db.prpLcfee.compensateNo" />
				</td>
				<!-- 赔款计算书号 -->
				<td class="centertitle">
					<s:text name="check.claimNum" />
				</td>
				<!-- 赔案号 -->
				<td class="centertitle">
					<s:text name="prompt.queRegist.PolicyNo" />
				</td>
				<!-- 保单号 -->
				<td class="centertitle">
					<s:text name="compensate.sumPayMoney" />
				</td>
				<!-- 总赔付金额(折TWD) -->
				<td class="centertitle">
					<s:text name="db.prpDshortrate.validStatus" />
				</td>
				<!-- 状态 -->
			</tr>
			<%
				int index = 0;
			%>
			<logic:notEmpty name="prpLcompensateDto" property="compensateList">
				<logic:iterate id="prpLcompensate1" name="prpLcompensateDto" property="compensateList">
					<%
						if (index % 2 == 0)
										out.print("<tr class=listodd>");
									else
										out.print("<tr class=listeven>");
					%>
					<tr class=common>
						<td>
							<a
								href="/claim/compensateFinishQueryList.do?prpLcompensateCompensateNo=<bean:write name='prpLcompensate1' property='compensateNo'/>&editType=<bean:write name='prpLcompensateDto' property='editType'/>">
								<bean:write name="prpLcompensate1" property="compensateNo" />
							</a>
						</td>
						<td>
							<bean:write name="prpLcompensate1" property="claimNo" />
						</td>
						<td>
							<bean:write name="prpLcompensate1" property="policyNo" />
						</td>
						<td>
							<bean:write name="prpLcompensate1" property="sumPaid" />
						</td>
						<td>
							<logic:equal name="prpLcompensate1" property="underWriteFlag" value="0">
								<s:text name="compensate.initValue" />
							</logic:equal>
							<!-- 初始值 -->
							<logic:equal name="prpLcompensate1" property="underWriteFlag" value="1">
								<s:text name="compensate.pass" />
							</logic:equal>
							<!-- 通过 -->
							<logic:equal name="prpLcompensate1" property="underWriteFlag" value="2">
								<s:text name="compensate.notPass" />
							</logic:equal>
							<!-- 不通过 -->
							<logic:equal name="prpLcompensate1" property="underWriteFlag" value="3">
								<s:text name="compensate.withoutHePei" />
							</logic:equal>
							<!-- 无需核赔 -->
							<logic:equal name="prpLcompensate1" property="underWriteFlag" value="9">
								<s:text name="compensate.stayHePei" />
							</logic:equal>
							<!-- 待核赔 -->
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
							<bean:define id="pageview" name="prpLcompensateDto" property="turnPageDto" />
							<%
								PrpLcompensateDto prpLcompensateDto = (PrpLcompensateDto) request.getAttribute("prpLcompensateDto");
									int curPage = prpLcompensateDto.getTurnPageDto().getPageNo();
							%>
							<%@include file="/common/pub/TurnOverPage.jsp"%>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</tr>
		</table>
		<input type="hidden" name="searchFlag" value="">
		<input type="hidden" name="editType" value="SHOW">
	</form>
</body>
</html:html>