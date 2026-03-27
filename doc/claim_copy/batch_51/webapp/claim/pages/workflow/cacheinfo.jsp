<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="ins.framework.common.ServiceFactory"%>
<%@ page import="com.sinosoft.one.bpm.model.ProcessInstanceBOInfo"%>
<%@ page import="com.sinosoft.claim.common.util.CommonUtils"%>
<%@ page import="com.sinosoft.claim.common.service.facade.CommonService"%>
<%@ page import="com.sinosoft.one.bpm.support.BpmServiceSupport"%>
<%@ page import="java.util.*"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<%
	BpmServiceSupport bpmServiceSupport = (BpmServiceSupport) ServiceFactory.getService("bpmServiceSupport");
	CommonService commonService = (CommonService) ServiceFactory.getService("commonService");
	Map<String, Long> processInstanceIdCache = bpmServiceSupport.getProcessInstanceIdCache();
	List<ProcessInstanceBOInfo> infoes = new ArrayList<ProcessInstanceBOInfo>();
	if(!processInstanceIdCache.isEmpty()){
		String processId = request.getParameter("processId");
		String businessId = request.getParameter("businessId");
		if(!CommonUtils.isEmpty(processId) && !CommonUtils.isEmpty(businessId)){
			for(Map.Entry<String, Long> entry : processInstanceIdCache.entrySet()){
				System.out.println(entry.getKey() + " : " + entry.getValue());
			}
			String key = processId + "_" + businessId;
			if(processInstanceIdCache.containsKey(key)){
				System.err.println(true);
				Long processInstanceId = processInstanceIdCache.get(key);
				ProcessInstanceBOInfo temp = bpmServiceSupport.getProcessInstanceBOInfo(processInstanceId);
				if(temp != null){
					infoes.add(temp);
				}
			}
		} else if(CommonUtils.isEmpty(processId) && CommonUtils.isEmpty(businessId)){
			
		} else {
			Set<String> keys = processInstanceIdCache.keySet();
			List<String> list = new ArrayList<String>();
			list.addAll(keys);
			Collections.sort(list);
			for(String k : list){
				if((!CommonUtils.isEmpty(processId) && k.startsWith(processId)) || (!CommonUtils.isEmpty(businessId) && k.endsWith(businessId))){
					Long processInstanceId = processInstanceIdCache.get(k);
					ProcessInstanceBOInfo temp = bpmServiceSupport.getProcessInstanceBOInfo(processInstanceId);
					if(temp != null){
						infoes.add(temp);
					}
				}
			}
		}
	}
	pageContext.setAttribute("infoes", infoes);
%>
	<title>JBPM工作流實例訊息</title>
	<script type="text/javascript" src="/claim/common/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript">
		function clear(processId , businessId){
			$.ajax({
				type : "POST",
				url : "${ctx}/pages/workflow/cacheclear.jsp",
				data : {
					"processId" : processId ,
					"businessId" : businessId 
				},
				dataType : "json",
				success : function(data){
					if (data.status == "1") {
						$($("tr:contains('"+businessId+"')")).fadeOut("slow",function(){
							$(this).remove();
						});
					}
				}
			});
		}
	</script>
</head>
<body class="body_12" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<br/>
	<div style="width: 80%">
		<form action="${ctx}/pages/workflow/cacheinfo.jsp" method="post">
			&nbsp;&nbsp;processId：
			<select name="processId" id = "processId">
				<option value="">--</option>
				<option value="claim_D">車險 - claim_D</option>
				<option value="claim_E">傷害險 - claim_E</option>
				<option value="claim_G">工程險 - claim_G</option>
				<option value="claim_Q">火險 - claim_Q</option>
				<option value="claim_Y">水險 - claim_Y</option>
				<option value="claim_Z">責任險 - claim_Z</option>
				<option value="claim_audit">追償 - claim_audit</option>
				<option value="claim_reCase_D">重開賠案 - claim_reCase_D</option>
			</select>
			&nbsp;&nbsp;businessId：<input type="text" name="businessId" class="input" value="${param.businessId}" style="width: 240px;">
			&nbsp;&nbsp;<input type="submit" class="button" value="查詢" >
			<script type="text/javascript">
				$("#processId").val('${param.processId}');
			</script>
		</form>
	</div>
	<div style="width: 80%">
		<table class="common" cellpadding="5" cellspacing="1" >
			<thead>
				<tr>
					<td class="centertitle">流程ID</td>
					<td class="centertitle">業務號碼</td>
					<td class="centertitle">實例ID</td>
					<td class="centertitle">創建時間</td>
					<td class="centertitle">狀態</td>
					<td class="centertitle">操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pageScope.infoes}" var="instanceBOInfo">
					<tr>
						<td align="left">&nbsp;<c:out value="${instanceBOInfo.processId}" /></td>
						<td align="left">&nbsp;<c:out value="${instanceBOInfo.businessId}" /></td>
						<td align="left">&nbsp;<c:out value="${instanceBOInfo.processInstanceId}" /></td>
						<td align="center">&nbsp;<fmt:formatDate value="${instanceBOInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td align="left">
							<c:choose>
								<c:when test="${instanceBOInfo.status=='1'}">有效</c:when>
								<c:otherwise>無效</c:otherwise>
							</c:choose>
						</td>
						<td align="left"><a href="javascript:clear('${instanceBOInfo.processId}','${instanceBOInfo.businessId}');" >清理</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>