<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>

<html>
<head>
<title>任务查看</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<script type="text/javascript">
	function test(){
		init();
		executeQuery(1,10);
	}
</script>
</head>
<body id="all_title" onload="test()">
<div id="wrapper">
<div id="container">
</div>
	<s:form name="fm" action="">
		<s:hidden name="editType" value="${editType}"></s:hidden>
		<table class="fix_table">
			<tr class="top">
			 	<div id="crash_menu">
			 	<h2 align="center">
				<s:if test="${editType == 'admin1'}">
					一审列表
				</s:if>
				 <s:if test="${editType == 'admin2'}">
					二审列表
				</s:if>
				<s:if test="${editType == 'admin3'}">
					三审列表
				</s:if>
				</h2>
				</div>
			</tr>
			<tr>
				<td class="bgc_tt short">任务编号</td>			
				<td class="long"><s:textfield name="auditTask.serialNo"  cssClass='input_w w_30'></s:textfield></td>			
				<td class="bgc_tt short">申请人</td>
				<td class="long"><s:textfield name="auditTask.applicantName"  cssClass='input_w w_30'></s:textfield></td>
			</tr>
			<tr>
				<td class="bgc_tt short">申请机构</td>
				<td class="long"><s:textfield name="auditTask.userComName"  cssClass='input_w w_60'></s:textfield></td>
				<td class="bgc_tt short">审核状态</td>
				<s:if test="${editType == 'admin1'}">
					<td class="long"><select name="auditTask.nodeStatus">
					<option value="a" selected="selected">全部状态</option>
					<option value="1">已提交</option>
					<option value="22">二审驳回</option>
					<option value="111">重新申请</option>
					</select>
					</td>
				</s:if>
				 <s:if test="${editType == 'admin2'}">
					<td class="long">
						<select name="auditTask.nodeStatus" >
							<option value="a" selected="selected">全部状态</option>
							<option value="11">一审通过</option>
							<option value="32">三审驳回</option>
						</select>
					</td>
				</s:if>
				<s:if test="${editType == 'admin3'}">
					<td class="long"><select name="auditTask.nodeStatus" >
					<option value="a" selected="selected">全部状态</option>
					<option value="21">二审通过</option>
					</select></td>
				</s:if>
			</tr>
			</table>
	</s:form></div>
		<table>
			<tr align="right">
				<td>
				<input type="button" value="查询" onclick="executeQuery(1,10)" class="button_ty">
				</td>
			</tr>
		 </table>
<div id="content_navigation" class="query" align="center"></div>
<div id="content" class="sort"></div>
<div id="content_navigation" class="query" align="center"></div>

</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script type="text/javascript">
var contentDataTable;
var contentColumnHeaders; 
YAHOO.namespace("query.container"); 

function init(){
	 YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) { 
		 var oId = oRecord.taskinstanceCode;
		 if(oColumn.key=="audit"){
    		  elCell.innerHTML = "<a href='${ctx}/audit/queryTaskItem.do?editType=${editType}&taskInstanceId=" + oId + "'>审核</a>";
	     } 
	 };
	
	contentColumnHeaders =[
		{key:"taskinstanceCode",text:"任务编号",width:"20em",sortable:true},
		{key:"applicantName",text:"申请人",width:"20em",sortable:true},
		{key:"userComName",text:"申请机构",width:"30em",sortable:true},
		{key:"taskName",text:"审核内容",width:"30em",sortable:true},
		{key:"userName",text:"新增用户",width:"20em",sortable:true},
		{key:"audit",text:"操作",width:"20em",type:"link",resizeable:true}
		]; 
}
//Query Data
function executeQuery(pageNo,pageSize){
	if(isNaN(parseInt(pageNo))){ 
		pageNo = 1;
	}
	if(isNaN(parseInt(pageSize))){
		pageSize = 10;
	}

	var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);	

	var myDataSource = new YAHOO.util.DataSource("${ctx}/audit/queryAuditTaskList.do");

    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;

	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["taskinstanceCode" , "taskName", "applicantName", "userComName","userName" ],
	   totalRecords: "totalRecords"
	};

	myDataSource.subscribe("responseParseEvent",SINOSOFT.util.navigation);	

	myDataSource.connMgr.setForm(fm);

	var initialRequest = "pageSize="+pageSize+"&pageNo="+pageNo;

	var myConfiges ={
		initialRequest:initialRequest,
		paginator:false
	};   

	contentDataTable = new YAHOO.widget.DataTable("content",myColumnSet,myDataSource,myConfiges);
	
}
	YAHOO.util.Event.addListener(window,'load',init);
	
</script>
