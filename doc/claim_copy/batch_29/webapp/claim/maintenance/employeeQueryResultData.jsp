<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<!-- mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護 -->
<table class="common" cellpadding="5" cellspacing="1" >
	<thead>
		<tr>
			<td colspan="13" class="formtitle">理賠人員資料維護</td>
		</tr>
		<tr>
			<td class="centertitle" style="width: 10%">選擇/序號</td>
			<td class="centertitle" style="width: 10%">員工編號</td>
			<td class="centertitle" style="width: 25%">員工姓名</td>
			<td class="centertitle" style="width: 25%">工作地點</td>
			<td class="centertitle" style="width: 10%">歸屬單位</td>
			<td class="centertitle" style="width: 10%">車資上限金額</td>
			<td class="centertitle" style="width: 10%">生效狀態</td>
		</tr>
	</thead>
	<tbody id="tbresult">
		<c:choose>
			<c:when test="${not empty requestScope.resultList}">
				<c:forEach items="${requestScope.resultList}" var="prpLuser" varStatus="stats">
					<tr >
						<td align="center" style="width: 10%">
							<input type="radio" name="cbx" value="${prpLuser.id},${prpLuser.userCode}">
							<span style="width: 30px" align="left">${stats.count + requestScope.pageStart}</span>
						</td>
						<td align="center" style="width: 10%"><c:out value="${prpLuser.userCode}"/></td>
						<td align="center" style="width: 25%"><c:out value="${prpLuser.userName}"/></td>
						<td align="left" style="width: 25%"><c:out value="${prpLuser.workPlaceNm}"/></td>
						<td align="center" style="width: 10%"><c:out value="${prpLuser.comcode}"/></td></td>
						<td align="right" style="width: 10%"><fmt:formatNumber value='${prpLuser.feeQuota}' pattern='#'/></td></td>
						<td align="center" name="tdstatus" style="width: 10%">
							<c:choose>
								<c:when test="${prpLuser.userFlag=='0'}">無效</c:when>
								<c:when test="${prpLuser.userFlag=='1'}">有效</c:when>
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