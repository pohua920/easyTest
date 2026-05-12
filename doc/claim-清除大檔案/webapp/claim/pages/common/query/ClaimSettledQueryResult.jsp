<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/tlds/claim-app.tld" prefix="app" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>已決賠案明細查詢</title>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<style type="text/css">
	tbody tr td {
		word-break: keep-all; /*必须*/
		overflow-x: hidden;
		text-overflow: ellipsis;
		white-space: nowrap
	}
	td{
		padding: 0 5px 0 5px;
		line-height: 25px;
	}
</style>
</head>
<body >
	<form name="fm" action="${ctx }/claimSettledQuery.do" method="post" >
		<c:if test="${not empty param.MakeCom}"><input type="hidden" name="MakeCom" value="${param.MakeCom}"></c:if>
		<c:if test="${not empty param.ComCode}"><input type="hidden" name="ComCode" value="${param.ComCode}"></c:if>
		<c:if test="${not empty param.dateStart}"><input type="hidden" name="dateStart" value="${param.dateStart}"></c:if>
		<c:if test="${not empty param.dateEnd}"><input type="hidden" name="dateEnd" value="${param.dateEnd}"></c:if>
		<table cellpadding="3" cellspacing="1"  class="common"
			<thead>
				<tr>
					<td class="formtitle" colspan="18">已決賠案明細查詢</td>
				</tr>
				<tr class="tableHead">
					<td class="centertitle">出單單位</td>
					<td class="centertitle">理賠單位</td>
					<td class="centertitle">通路代號</td>
					<td class="centertitle">業務來源代碼</td>
					<td class="centertitle">業務來源名稱</td>
					<td class="centertitle">賠案號碼</td>
					<td class="centertitle">保單號碼</td>
					<td class="centertitle">被保險人</td>
					<td class="centertitle">車牌號碼</td>
					<td class="centertitle">出險日期</td>
					<td class="centertitle">受理日期</td>
					<td class="centertitle">賠付險種</td>
					<td class="centertitle">估計金額</td>
					<td class="centertitle">賠付金額</td>
					<td class="centertitle">理賠費用</td>
					<td class="centertitle">確認日期</td>
					<td class="centertitle">理賠人員</td>
					<td class="centertitle">服務人員</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${requestScope.resultList}" var="mapObject" varStatus="stat">
				<tr class=content bgcolor='#F7F7F7'>
				<c:forEach items="${mapObject}" var="entry">
					<c:choose>
						<c:when test="${entry.key == 'damagestartdate' || entry.key == 'claimdate' || entry.key == 'underwriteenddate'}">
							<td align="center"><fmt:formatDate value="${entry.value}" pattern="yyyy-MM-dd"/></td>
						</c:when>
						<c:when test="${entry.key == 'sumclaim' || entry.key == 'sumkindpay' || entry.key == 'sumkindfee'}">
							<td align="right"><fmt:formatNumber value="${entry.value}" pattern="#,##0"/></td>
						</c:when>
						<c:otherwise>
							<td align="left"><c:out value="${entry.value}" /></td>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				</tr>
			</c:forEach>
			<c:if test="${empty requestScope.resultList}">
				<tr class=content bgcolor='#F7F7F7'>
					<td align="center" colspan="17">&nbsp;無記錄！&nbsp;</td>
				</tr>
			</c:if>
			</tbody>
			<tfoot>
				<tr class="listtail" align="center">
					<%ins.framework.common.Page pageRecode = (ins.framework.common.Page) request.getAttribute("page");%>
					<input type="hidden" name="pageSize" value="<%=pageRecode.getPageSize() %>">
					<input type="hidden" name="pageNo" value="<%=pageRecode.getCurrentPageNo() %>">
					<app:navigate objectName="page" />
				</tr>
			</tfoot>
		</table>
	</form>
	<script type="text/javascript">
		function processtd($td){
			var textdata = $td.text();
			var text = getSubStr(textdata , 6);
			if(text.length < textdata.length){
				$td.text(text + "...");
				$td.prop("title",textdata);
			}
		}	
		$("table tbody").children("tr").each(function(){
			var $datatd = $(this).children("td");
			processtd($datatd.eq(3));
			processtd($datatd.eq(6));
		});
	</script>
</body>
</html>