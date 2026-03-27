<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${ctx }/pages/undwrt/css/Standard.css">
<!-- 公用函数 -->
<script src="${ctx }/pages/undwrt/common/js/Common.js"></script>
<script src="${ctx }/pages/undwrt/common/js/WfLogQuery.js"></script>
<script src="${ctx }/pages/undwrt/common/js/CommonTaskDeal.js"></script>
</head>
<body>
	<form name="fm" method="post" action="">
		<input type="hidden" name="HandType" value='22'> <input type="hidden" name="EditType" value='${EditType }'>
	<table class="common" cellpadding="5" cellspacing="1" align="center">
			<tr class="listtitle">
				<td><b><s:text name="undwrt.nuclearTaskSubmit" /></b></td>
			</tr>
			<%-- 核赔任务批量提交上级 --%>
	</table>
	&nbsp;
	<table class="common" cellpadding="5" cellspacing="1" align="center">
		<tr class=listtitle>
				<td colspan="3"><s:text name="undwrt.CannotBulkSubmission" /></td>
				<%-- 以下任务无法批量提交上级 --%>
		</tr>
		<c:if test="${noToSubmitList!=null}">
		<c:set var="index" value="1" scope="page"/>
			<c:forEach items="${noToSubmitList}" var="wfLogDto">
				<c:if test="${index%3==1}">
					<tr class="common">
				</c:if>
				<td>${wfLogDto.businessNo}</td>
				<c:if test="${index%3==0}">
					</tr>
				</c:if>
				<c:set var="index" value="${index+1}" scope="page"/>
			</c:forEach>
			<c:set var="index" value="${index-1}" scope="page"/>
			<c:if test="${index%3!=0}">
				<c:forEach begin="0" end="${3-index%3}" step="1">
					<td></td>
				</c:forEach>
				</tr>
			</c:if>
		</c:if>
		<c:if test="${noToSubmitList==null}">
				<tr class="common">
					<td colspan="3"><s:text name="undwrt.No" /></td>
				</tr>
				<%-- （无） --%>
		</c:if>
	</table>
	&nbsp;
<!--==========================================================================================================-->
	<table class="common" cellpadding="5" cellspacing="1" align="center">
		<tr class=listtitle>
				<td colspan="3"><s:text name="undwrt.CanBulkSubmit" /></td>
				<%-- 以下任务可以批量提交上级 --%>
		</tr>
		<c:if test="${yesToSubmitList!=null}">
			<c:set var="index" value="1" scope="page"/>
			<c:forEach items="${yesToSubmitList}" var="wfLogDto">
				<c:if test="${index%3==1}">
					<tr class="common">
				</c:if>
			<td>${wfLogDto.businessNo}</td>
			<input type="hidden" name="businessNo" value='${wfLogDto.businessNo}'>
			<input type="hidden" name="comCode" value='${wfLogDto.comCode}'>
			<input type="hidden" name="modelNo" value='${wfLogDto.modelNo}'>
			<input type="hidden" name="nodeNo"  value='${wfLogDto.nodeNo}'>
			<input type="hidden" name="flowId"  value='${wfLogDto.flowID}'>
			<input type="hidden" name="logNo"   value='${wfLogDto.logNo}'>
			<input type="hidden" name="nodeStatus" value='${wfLogDto.nodeStatus}'>
			<input type="hidden" name="nextNodeNo" value='${wfLogDto.nextNodeNo}'>
			<input type="hidden" name="nextNodeName" value='${wfLogDto.nextNodeName}'>
			<c:if test="${index%3==0}">
				</tr>
			</c:if>
			<c:set var="index" value="${index+1}" scope="page"/>
			</c:forEach>
			<c:set var="index" value="${index-1}" scope="page"/>
			<c:if test="${index%3!=0}">
				<c:forEach begin="0" end="${3-index%3}" step="1">
					<td></td>
				</c:forEach>
				</tr>
			</c:if>
		</c:if>
		<c:if test="${yesToSubmitList==null}">
				<tr class="common">
					<td colspan="3"><s:text name="undwrt.No" /></td>
				</tr>
				<%-- （无） --%>
		</c:if>
	</table>
<!--==========================================================================================================-->
	&nbsp;
	<table class="common" cellpadding="5" cellspacing="1" align="center">
		<tr class=listtitle>
				<td colspan="9"><s:text name="undwrt.ApprovalInformation" /></td>
				<%-- 审批信息 --%>
        </tr>
        <tr>
				<td class=title4><s:text name="undwrt.SignedComments" />：</td>
				<%-- 签署审批意见 --%>
				<td class=input4><textarea class=big wrap="soft" name="HandleText"></textarea></td>
				<td class=title4><s:text name="undwrt.ApprovalPhrases" />：</td>
				<%-- 审批片语 --%>
				<td class=input4><select class=common name="notion" onchange="changeNotion(this)">
						<option value="">
							-----
							<s:text name="undwrt.PleaseSelect" />
							-----
						</option>
						<%-- 请选择 --%>
             <c:if test="${notionList!=null}">
             	<c:forEach items="${notionList}" var="NotionCode">
								<option value="${NotionCode.codeCName}">${NotionCode.codeCName}</option>
             	</c:forEach>
             </c:if>
				</select></td>
        </tr>
	</table>
	&nbsp;
	<table class="two">
		<tr>
				<td align="center"><input type="button" class="button" value="<s:text name='button.determine.value'/>" <%-- 确定 --%>
			       onclick="batchSubmit('batchSubmitSuperior', '提交高階');"></td>
		</tr>
	</table>
	</form>
</body>
</html>