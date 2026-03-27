<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String contextPath = request.getContextPath();
%>
<%@ page import="com.sinosoft.claim.workflow.vo.WorkFlowDto"%>
<%@ page import="java.util.*"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
	<title>系統緩存清理</title>
	<script type="text/javascript" src="/claim/common/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript">
		function clearCache(cacheType,reloadflag,params){
			$.ajax({
				type: "POST",
				cache: false,
				url: "<%=contextPath%>/maintenance/clearCache.jsp",
				data: "cacheType="+cacheType+"&params="+params,
				async: false,
				dataType:"json",
				success:function(data){
					if(data.status == "1"){
						alert("處理成功！");
						if(reloadflag == "1"){
							window.location.reload();
						}
					} else {
						alert("處理失敗！");
					}
				}
			});
		}
	</script>
	<style type="text/css">
		div {
			float: left;
			padding-right: 10px
		}
		a {
			cursor:pointer
		}
		a:link {
			color:#37a;
			text-decoration:none
		}
		a:visited {
			color:#669;
			text-decoration:none
		}
		a:hover {
			color:#fff;
			text-decoration:none;
			background:#37a
		}
		a:active {
			color:#fff;
			text-decoration:none;
			background:#f93
		}
	</style>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<br />
	<div style="width: 90%;padding-left: 50px;">
		<fieldset style="border: 1px solid #fffff ;padding: 5px;">
			<legend>工作流相關</legend>
			<div align="left" style="width: 100%;margin-bottom: 10px;">
				<%
					boolean workFlowSwitch = WorkFlowDto.isWorkflowswitch();
					String statusStr = WorkFlowDto.isWorkflowswitch() ? "開啟" : "關閉";
					String toogleStatus = WorkFlowDto.isWorkflowswitch() ? "0" : "1";
					String toogleStatusStr = WorkFlowDto.isWorkflowswitch() ? "關閉" : "開啟";
				%>
				<div >JBPM工作流狀態：</div>
				<div ><%=statusStr%></div>
				<div >
					<a href="javascript:void(0)" onclick="clearCache('WorkFlowSwitch',1,'<%=toogleStatus%>');"><%=toogleStatusStr%></a><br>
				</div>
			</div>
			<div align="left" style="width: 100%;margin-bottom: 10px;">
				險種代碼：<input id="WorkFlowRiskCode" value="" maxlength="10"><br>
				<a href="javascript:void(0)" onclick="clearCache('WorkFlowSwfModelUseModelNo',0,$('#WorkFlowRiskCode').val());">清理險種使用模板配置訊息</a><br>
			</div>
			<div align="left" style="width: 100%;margin-bottom: 10px;">
				流程模板號碼：<input id="WorkFlowModelNo" value="" maxlength="5"><span>（1-車險，5-火險，6-工程險，7-責任險，12-傷害險，13-水險，0-全險種）</span><br>
				<a href="javascript:void(0)" onclick="clearCache('WorkFlowCancelSwfNode',0,$('#WorkFlowModelNo').val());">清理註銷拒賠節點配置訊息</a><br>
				<a href="javascript:void(0)" onclick="clearCache('WorkFlowFirstSwfNode',0,$('#WorkFlowModelNo').val());">清理初始節點配置訊息</a><br>
				<a href="javascript:void(0)" onclick="clearCache('WorkFlowSwfConditionForAutoTask',0,$('#WorkFlowModelNo').val());">清理自動節點流轉條件配置訊息</a><br>
				<a href="javascript:void(0)" onclick="clearCache('WorkFlowSwfConditionForPath',0,$('#WorkFlowModelNo').val());">清理路線流轉條件配置訊息</a><br>
				<a href="javascript:void(0)" onclick="clearCache('WorkFlowSwfPath',0,$('#WorkFlowModelNo').val());">清理路線配置訊息</a><br>
				<a href="javascript:void(0)" onclick="clearCache('WorkFlowSwfNode',0,$('#WorkFlowModelNo').val());">清理工作流節點訊息</a><br>
			</div>
			<div align="left" style="width: 100%;margin-bottom: 10px;">
				JBPM流程實例：<select id="processId">
								<option value="claim_D">車險</option>
								<option value="claim_E">傷害險</option>
								<option value="claim_Q">火險</option>
								<option value="claim_Y">水險</option>
								<option value="claim_Z">責任險</option>
								<option value="claim_G">工程險</option>
								<option value="claim_reCase_D">重開賠案</option>
								<option value="claim_audit">追償、殘餘物</option>
							</select>&nbsp;&nbsp;<input id="businessId" value="" maxlength="100" style="width: 240px"><span>（swflog.businessId）</span><br>
				<a href="javascript:void(0)" onclick="clearCache('ProcessInstanceIdCache',0,$('#businessId').val()+','+$('#businessId').val());">清理賠案JBPM實例緩存配置訊息</a><br>
			</div>
		</fieldset>
		<br>
		<fieldset style="border: 1px solid #fffff ;padding: 5px;">
			<legend>權限相關</legend>
			<div align="left" style="width: 100%;margin-bottom: 10px;">
				用戶代碼：<input id="UserCode" value="" maxlength="10"><br>
				<a href="javascript:void(0)" onclick="clearCache('UserComcodePower',0,$('#UserCode').val());">清理用戶處理機構權限訊息</a><br>
				<a href="javascript:void(0)" onclick="clearCache('UserRiskPower',0,$('#UserCode').val());">清理用戶處理險種權限訊息</a><br>
			</div>
		</fieldset>
	</div>
</body>
</html>