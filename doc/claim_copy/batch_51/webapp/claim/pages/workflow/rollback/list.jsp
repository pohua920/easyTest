<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="ins.framework.common.ServiceFactory"%>
<%@ page import="com.sinosoft.claim.workflow.service.facade.WorkFlowService"%>
<%@ page import="com.sinosoft.claim.schema.model.SwfLog"%>
<%@ page import="java.util.*"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<%
	String businessId = request.getParameter("businessId");
	String resetStr = "";
	if (businessId == null || businessId.trim().length() == 0) {
		response.sendRedirect(request.getContextPath() + "/pages/workflow/query.jsp");
	} else {
		WorkFlowService workFlowService = (WorkFlowService) ServiceFactory.getService("workFlowService");
		String condition = " businessId = '" + businessId.trim() + "' order by flowintime asc,logNo asc ";
		List<SwfLog> list = workFlowService.findByConditions(condition);
		pageContext.setAttribute("list", list);
		if(list!=null && !list.isEmpty()){
			SwfLog swfLog = list.get(0);
			condition = " flowid = '"+swfLog.getId().getFlowID()+"' and ( nodeType = 'compe' or nodeType = 'sched' or not exists (select 0 from swfpathlog where flowid = '"+swfLog.getId().getFlowID()+"' and swfpathlog.startnodeno = swflog.logno ) ) ";
			//可以恢復的節點
			List<SwfLog> resetList = workFlowService.findByConditions(condition);
			if(resetList!=null && !resetList.isEmpty()){
				resetStr += ",";
				for(SwfLog s : resetList){
					resetStr += s.getId().getLogNo() + ",";
				}
			}
			pageContext.setAttribute("resetStr", resetStr);
		}
	}
%>
	<title>JBPM工作流任務回滾</title>
	<script type="text/javascript" src="/claim/common/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript">
		function processreset(processId,businessId,actorId,flowID,logNo){
			$.ajax({
				type : "POST",
				url : "${ctx}/pages/workflow/rollback/reset.jsp",
				data : {
					"processId" : processId,
					"businessId" : businessId,
					"actorId" : actorId,
					"flowID" : flowID,
					"logNo" : logNo
				},
				dataType : "json",
				success : function(data){
					if (data.status == "1") {
						alert("恢復成功！");
					} else {
						alert("恢復失敗！" + data.message);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					alert(textStatus);
				}
			});
		}
	</script>
</head>
<body class="body_12" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<br/>
	<div style="width: 80%">businessId：${param.businessId}</div>
	<div style="width: 80%">
		<table class="common" cellpadding="5" cellspacing="1" >
			<thead>
				<tr>
					<td class="centertitle">業務號碼</td>
					<td class="centertitle">險種</td>
					<td class="centertitle">節點名稱</td>
					<td class="centertitle">狀態</td>
					<td class="centertitle">流入時間</td>
					<td class="centertitle">提交時間</td>
					<td class="centertitle">處理人員</td>
					<td class="centertitle">userId</td>
					<td class="centertitle">操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pageScope.list}" var="swflog">
					<tr>
						<td align="left"><c:out value="${swflog.businessNo}" /></td>
						<td align="left"><c:out value="${swflog.riskCode}" /></td>
						<td align="left">
							<c:out value="${swflog.nodeName}" />
							（
							<c:out value="${swflog.nodeType}" />
							）
						</td>
						<td align="center">
							<c:choose>
								<c:when test="${swflog.nodeStatus=='3'}">回退处理</c:when>
								<c:when test="${swflog.nodeStatus=='4'}">已提交</c:when>
								<c:when test="${swflog.nodeStatus=='5'}">已退回</c:when>
								<c:when test="${swflog.nodeStatus=='2'}">正處理</c:when>
								<c:when test="${swflog.nodeStatus=='1' || swflog.nodeStatus=='0'}">未處理</c:when>
								<c:when test="${swflog.nodeStatus=='6'}">已註銷</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
						</td>
						<td align="center">
							<rc:rcDate name="flowInTime" class="readonly" readonly="true" wdatePicker="false" style="width:150px" value="${swflog.flowInTime}" />
						</td>
						<td align="center">
							<rc:rcDate name="flowInTime" class="readonly" readonly="true" wdatePicker="false" style="width:150px" value="${swflog.submitTime}" />
						</td>
						<td align="left">
							<c:if test="${!(swflog.handlerCode eq '0')}">
								<c:out value="${swflog.handlerName}" />
								（
								<c:out value="${swflog.handlerCode}" />
								）
							</c:if>
						</td>
						<td align="left"><c:out value="${swflog.actorId}" /></td>
						<td align="center">
							<c:if test="${swflog.nodeStatus=='0' || swflog.nodeStatus=='1' || swflog.nodeStatus=='2' || swflog.nodeStatus=='3'}">
								<c:if test="${not empty swflog.processId && not empty swflog.businessId && not empty swflog.actorId}">
									<c:set var="logNo" value=",${swflog.id.logNo},"/>
									<c:if test="${fn:contains(resetStr,logNo)}">
										<a href="javascript:void(0)" onclick="processreset('${swflog.processId}','${swflog.businessId}','${swflog.actorId}','${swflog.id.flowID}','${swflog.id.logNo}')" >恢復</a>
									</c:if>
								</c:if>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>