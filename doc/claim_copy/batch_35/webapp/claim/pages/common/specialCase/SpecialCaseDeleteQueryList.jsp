<!--
****************************************************************************
* DESC       ：撤消报案查询结果页面
* AUTHOR     ：中科软
* CREATEDATE ：2004-06-15
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
-->

<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
	<head>
		<title>
			<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
			<s:text name="title.registBeforeEdit.queryRegist" />
		</title>
		<script src="${ctx}/common/js/showpage.js"> </script>
		<script language=javascript>
		function submitForm(fieldObject) {
			var registNo = "";
			var intIndex = parseInt(fieldObject.num);
			if (isNaN(fm.buttonDelete.length)) {
				registNo = fm.registNoRow.value;
			} else {
				registNo = fm.registNoRow[intIndex].value;
			}
			//撤消确认提示
			if (confirm("确定要删除选中的报案'" + registNo + "'吗？")) {
				fm.buttonDelete.disabled = true;
				fm.registNo.value = registNo;
				fm.submit();
				return true;
			}
			return false;
		}
  		</script>
	</head>
	<body>
	<form name=fm action="${ctx}/registDelete.do" method="post" onsubmit="return validateForm(this);">
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td colspan=5 class="formtitle">
					<s:text name="specialCase.ResultInformation" />
					<%-- 报案查询结果信息 --%>
				</td>
			</tr>
			<tr>
				<td class="centertitle">
					<s:text name="db.prpLregist.registNo" />
				</td>
				<td class="centertitle">
					<s:text name="db.prpLregist.policyNo" />
				</td>
				<td class="centertitle">
					<s:text name="db.prpLregist.operatorCode" />
				</td>
				<td class="centertitle">
					<s:text name="prompt.queRegist.Date" />
					<%-- 输入时间 --%>
				</td>
				<td class="centertitle">
					<s:text name="certify.operate" />
					<%-- 操作 --%>
				</td>
			</tr>
			<s:set var="prpLregist_count" value="0" scope="page" />
			<c:if test="${prpLregist.registList!=null}">
				<s:set var="prpLregist_count" value="#attr.prpLregist.registList.size()" scope="page" />
				<c:forEach var="prpLregistTemp" value="${prpLregist.registList}" varStatus="prpLregistTemp_status">
					<c:if test="${prpLregistTemp_status.index%2==0}">
						<tr class="listodd">
					</c:if>
					<c:if test="${prpLregistTemp_status.index%2!=0}">
						<tr class="listeven">
					</c:if>
					<td align="center">
						<a href="${ctx}/registFinishQueryList.do?prpLregistRegistNo=${prpLregistTemp.registNo }&editType=${prpLregist.editType}">${prpLregistTemp.registNo}</a>
					</td>
					<td align="center">${prpLregistTemp.policyNo}</td>
					<td align="center">${prpLregistTemp.operatorName}</td>
					<td align="center">
						${prpLregistTemp.inputDate}
						<input type="hidden" name="registNoRow" class="common" value="${prpLregistTemp.registNo}">
					</td>
					<td align="center">
						<input type="button" name="buttonDelete" class='button' num='${${prpLregistTemp_status.index }' value="<s:text name="title.prepayBeforeEdit.editPrepay"/>删除" onclick="return submitForm(this);">
					</td>
					</tr>
				</c:forEach>
			</c:if>
			<tr class="listtail">
				<td colspan=5>
					<s:text name="certainLoss.totalInquiries" />
					${prpLregist_count }
					<s:text name="certainLoss.meetRecord" />
					<%-- 共查询出 --%>
					<%-- 条满足条件的记录 --%>
					<input type="hidden" name="registNo" class="common" value="">
				</td>
			</tr>
		</table>
		</tr>
		</table>
	</form>
</body>
</html>