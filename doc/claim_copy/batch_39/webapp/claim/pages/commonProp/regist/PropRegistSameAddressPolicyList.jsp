<%--
****************************************************************************
* DESC       ：輸入报案前查询保单号码结果面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-12-06
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
	<head>
		<title>同險保單訊息</title>
		<%@include file="/common/meta_css.jsp"%>
		<%@include file="/common/meta_js.jsp"%>
		<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
	</head>
	<body>
		<form name="fm" action="/claim/regist/sameAddressPolicyNo.do" method="post">
			<input type="hidden" name="prpCaddressSameaddressNo" value="${param.prpCaddressSameaddressNo}" />
			<input type="hidden" name="prpLregistDamageStartDate" value="${param.prpLregistDamageStartDate}">
			<input type="hidden" name="prpLregistDamageStartHour" value="${param.prpLregistDamageStartHour}">
			<input type="hidden" name="prpLregistPolicyNo" value="${param.prpLregistPolicyNo}">
			<table class="common" cellpadding="4" cellspacing="1">
				<tr>
					<td colspan="4" class="formtitle">
						同險保單訊息
					</td>
				</tr>
				<tr>
					<td class="centertitle" style="width: 20%;">
						<s:text name="db.prpLregist.policyNo" />
					</td>
					<td class="centertitle" style="width: 25%;">
						<s:text name="db.prpLregist.insuredName" />
					</td>
					<td class="centertitle" style="width: 15%;">
						保險金額
					</td>
					<td class="centertitle" style="width: 40%;">
						<s:text name="compensate.dubang.project" /><s:text name="user.address" />
						<%-- 標的物地址   --%>
					</td>
				</tr>
				<c:forEach var="policyDto" items="${requestScope.policyDtoList}" varStatus="status">
					<c:choose>
						<c:when test="${status.index%2==0}">
							<tr class=listodd>
						</c:when>
						<c:otherwise>
							<tr class=listeven>
						</c:otherwise>
					</c:choose>
					<td align="center">
						<font <c:if test="${prpCmain.colorFlag=='1'}">color="red"</c:if>>${policyDto.prpCmain.policyNo}</font>
					</td>
					<td align="center">
						<font <c:if test="${prpCmain.colorFlag=='1'}">color="red"</c:if>>${policyDto.prpCmain.insuredName}</font>
					</td>
					<td align="center">
						<font <c:if test="${prpCmain.colorFlag=='1'}">color="red"</c:if>>
							<fmt:formatNumber pattern="#" value="${policyDto.prpCmain.sumAmount}" />
						</font>
					</td>
					<td align="center">
						<table cellpadding="4" cellspacing="1">
							<c:forEach var="prpCaddress" items="${policyDto.prpCaddressList}">
								<tr >
									<td>
										<font <c:if test="${prpCmain.colorFlag=='1'}">color="red"</c:if>>
											${prpCaddress.addressDetailInfo}
										</font>
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
					</tr>
				</c:forEach>
				<c:choose>
					<c:when test="${empty requestScope.policyDtoList}">
						<tr>
							<td colspan="4" align="center">
								對不起，沒有找到滿足條件的保單！
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr class="listtail">
							<td colspan="4" align="center">
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
				<tfoot>
					<td colspan="4" align="center">
						<input type="button" name="button1" value="關閉" onclick="window.close();" class="button">
					</td>
				</tfoot>
			</table>
		</form>
	</body>
</html>