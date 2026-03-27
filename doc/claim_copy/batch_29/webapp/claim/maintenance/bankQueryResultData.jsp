<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<table class="common" cellpadding="5" cellspacing="1" >
	<thead>
		<tr>
			<td colspan="13" class="formtitle">銀行代碼維護</td>
		</tr>
		<tr>
			<td class="centertitle" style="width: 10%">選擇/序號</td>
			<td class="centertitle" style="width: 10%">級別</td>
			<td class="centertitle" style="width: 10%">總行代碼</td>
			<td class="centertitle" style="width: 25%">總行名稱</td>
			<td class="centertitle" style="width: 10%">分行代碼</td>
			<td class="centertitle" style="width: 25%">分行名稱</td>
			<td class="centertitle" style="width: 10%">生效狀態</td>
		</tr>
	</thead>
	<tbody id="tbresult">
		<c:choose>
			<c:when test="${not empty requestScope.resultList}">
				<c:forEach items="${requestScope.resultList}" var="prpLbank" varStatus="stats">
					<tr >
						<td align="center" style="width: 10%">
							<input type="radio" name="cbx" value="${prpLbank.id.bankCode},${prpLbank.id.upperBankCode},${prpLbank.bankLevel}">
							<span style="width: 30px" align="left">${stats.count + requestScope.pageStart}</span>
						</td>
						<td align="center" style="width: 10%">
							<c:choose>
								<c:when test="${prpLbank.bankLevel=='1'}">總行</c:when>
								<c:when test="${prpLbank.bankLevel=='2'}">分行</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
						</td>
						<td align="center" style="width: 10%"><c:out value="${prpLbank.id.upperBankCode}"/></td>
						<td align="left" style="width: 25%"><c:out value="${prpLbank.upperBankCName}"/></td>
						<td align="center" style="width: 10%"><c:if test="${prpLbank.bankLevel=='2'}"><c:out value="${prpLbank.id.bankCode}"/></c:if></td>
						<td align="left" style="width: 25%"><c:if test="${prpLbank.bankLevel=='2'}"><c:out value="${prpLbank.bankCName}"/></c:if></td>
						<td align="center" name="tdstatus" style="width: 10%">
							<c:choose>
								<c:when test="${prpLbank.validstatus=='0'}">無效</c:when>
								<c:when test="${prpLbank.validstatus=='1'}">有效</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr><td align="center" colspan="13">未查詢到資料！</td></tr>
			</c:otherwise>
		</c:choose>
	</tbody>
	<tfoot>
		<tr class="listtail">
			<td colspan="13" align="center">
				<%@include file="/pages/common/pub/TurnPage.jsp"%>
			</td>
		</tr>
	</tfoot>
</table>