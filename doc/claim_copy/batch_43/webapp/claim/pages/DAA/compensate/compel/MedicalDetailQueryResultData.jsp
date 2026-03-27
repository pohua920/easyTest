<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%> 
<c:forEach items="${requestScope.resultList}" var="objMap" varStatus="stats">
	<tr>
		<td align="center">
			<input type="checkbox" name="cbx" value="${objMap['claimNo']},${objMap['compensateNo']},${objMap['personNo']},${objMap['identifyNumber']},${objMap['underWriteEndDate']},${objMap['personName']}">
			<span style="width: 30px" align="left">${stats.count + requestScope.pageStart}</span>
		</td>
		<td align="left"><c:out value="${objMap['policyNo']}"/></td>
		<td align="left"><c:out value="${objMap['claimNo']}"/></td>
		<td align="left"><c:out value="${objMap['compensateNo']}"/></td>
		<td align="center"><c:out value="${objMap['personNo']}"/></td>
		<td align="left"><c:out value="${objMap['personName']}"/></td>
		<td align="left"><c:out value="${objMap['identifyNumber']}"/></td>
		<td align="center"><c:out value="${objMap['sumRealpay']}"/></td>
		<td align="center"><c:out value="${objMap['healthPoints']}"/></td>
		<td align="center"><c:out value="${objMap['healthAmount']}"/></td>
		<td align="center"><c:out value="${objMap['underWriteEndDateMG']}"/></td>
		<td align="center" name="tdstatus">
			<c:choose>
				<c:when test="${objMap['status']=='0'}">´ýÑaä›</c:when>
				<c:when test="${objMap['status']=='2'}">•º´æ</c:when>
				<c:when test="${objMap['status']=='4'}">ÒÑÐ£ºË</c:when>
				<c:otherwise></c:otherwise>
			</c:choose>
		</td>
		<td align="center">
			<a href="javascript:void(0)" onclick="editMedicalDetail(this,'${objMap['claimNo']}','${objMap['compensateNo']}','${objMap['personNo']}','${objMap['identifyNumber']}','${objMap['personName']}');">¾ŽÝ‹Ã÷¼š</a>
		</td>
	</tr>
</c:forEach>