<%@	page contentType="text/html; charset=GBK"	language="java"	%>
<%--
****************************************************************************
* DESC       ：已出险信息显示画面
* AUTHOR     ： Sinosoft
* ------------------------------------------------------
****************************************************************************
--%> 
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title><s:text name="title.registBeforeEdit.damage" /></title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<SCRIPT LANGUAGE="JavaScript">
//按钮单击事件，用於相同保单号码多报案的显示
//reason:在报案登记画面中，已出险次数的历次出险事故的清单中,可以点击报案号关联到相关案件信息  
/**
*@description 弹出关联报案信息页面
*@param       无
*@return      通过返回true,否则返回false
*/

function showRegist(registNo) {

	var linkURL = "/claim/registFinishQueryList.do?prpLregistRegistNo=" + registNo + "&editType=SHOW";
	var newWindow = window.open(linkURL, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}

function showPicture(registNo) {
	var linkURL = "/claim/pages/common/certify/CertifyViewAllFile.jsp?businessNo=" + registNo;
	var newWindow = window.open(linkURL, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}
</SCRIPT>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginhigh="0">
	<form name="fm">
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td class="prompttitle">
					<s:text name="regist.prpLregist.serialNo" />
				</td>
				<%-- 序号 --%>
				<td class="prompttitle">
					<s:text name="db.prpLregist.registNo" />
				</td>
				<%-- 备案号 --%>
				<td class="prompttitle">
					<s:text name="db.prpLclaim.claimNo" />
				</td>
				<%-- 赔案号 --%>
				<td class="prompttitle">
					<s:text name="certainLoss.prpLcheck.prpLcheckEstimateLoss" />
				</td>
				<%-- 赔预估金额 --%>
				<td class="prompttitle">
					<s:text name="db.prpLCMain.sumClaim" />
				</td>
				<%-- 赔付金额 --%>
				<td class="prompttitle">
					<s:text name="regist.prpLregist.damageTime" />
				</td>
				<%-- 出险时间 --%>
				<td class="prompttitle">
					<s:text name="regist.prpLregist.damageAddress" />
				</td>
				<%-- 出险地点 --%>
				<td class="prompttitle">
					<s:text name="regist.prpLregist.damageCode" />
				</td>
				<%-- 出险原因 --%>
				<td class="prompttitle">
					<s:text name="certainLoss.thirdCarLoss.prpLchecDemagePart" />
				</td>
				<%-- 损失部位 --%>
				<td class="prompttitle">
					<s:text name="regist.prpLregist.casePhoto" />
				</td>
				<%-- 案件照片 --%>
				<td class="prompttitle">
					<s:text name="db.prpLregist.phoneNumber" />
				</td>
				<%-- 联系电话 --%>
				<td class="prompttitle">
					<s:text name="regist.prpLregist.status" />
				</td>
				<%-- 状态 --%>
			</tr>
			<!-- 插入出险次数详细信息-->
			<c:set var="index" value="1"/>
			<c:forEach items="${requestScope.registClaimDtoList}" var="registClaimDto" varStatus="stat">
				<c:if test="${pageScope.registClaimDto.registNo != requestScope.curRegistNo}">
					<tr>
						<td class="prompt">
							<c:out value="${index}" />
							<c:set var="index" value="${index+1}"/>
						</td>
						<%--resson:在报案登记画面中，已出险次数的历次出险事故的清单中,可以点击报案号关联到相关案件信息--%>
						<td class="prompt">
							<a href="javascript:showRegist('${pageScope.registClaimDto.registNo}')"><c:out value="${pageScope.registClaimDto.registNo}" /></a>
						</td>
						<td class="prompt">
							<c:out value="${pageScope.registClaimDto.claimNo}" />
						</td>
						<td class="prompt">
							<fmt:formatNumber value="${pageScope.registClaimDto.sumClaim}" pattern="#" />
						</td>
						<td class="prompt">
							<fmt:formatNumber value="${pageScope.registClaimDto.sumPaidShow}" pattern="#" />
						</td>
						<td class="prompt">
							<!--<c:out value="${pageScope.registClaimDto.damageStartDate}" />-->
							<rc:rcDate name="damageStartDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${pageScope.registClaimDto.damageStartDate}" />
						</td>
						<td class="prompt">
							<c:out value="${pageScope.registClaimDto.damageAreaName}" />
						</td>
						<td class="prompt">
							<c:out value="${pageScope.registClaimDto.damageName}" />
						</td>
						<td class="prompt">
							<c:out value="${pageScope.registClaimDto.compName}" />
						</td>
						<td class="prompt">
							<a href="javascript:showPicture('${pageScope.registClaimDto.registNo}')"><c:out value="${pageScope.registClaimDto.registNo}" /></a>
						</td>
						<td class="prompt">
							<c:out value="${pageScope.registClaimDto.phoneNumber}" />
						</td>
						<td class="prompt">
							<c:out value="${pageScope.registClaimDto.status}" />
						</td>
					</tr>
				</c:if>
			</c:forEach>
			<tr>
				<td colspan=14 class="common" align="center">
					<input type=button name = 'button_Peril_Close_Context' class=button value='(O)關閉'  ACCESSKEY="O" onclick="window.close()">  
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
