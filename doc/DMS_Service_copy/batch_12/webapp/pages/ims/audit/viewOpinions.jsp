<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>

<html>
<head>
<title>审核意见查看</title>
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
<div id="crash_menu">
<h2 align="center">查看审核意见</h2>
</div>
</div>
	<s:form name="fm" action="">
	<s:hidden name="taskId" value="${taskId}"></s:hidden>
	</s:form></div>
<div id="content" class="sort"></div>
<div id="content_navigation" class="query" align="center"></div>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr align="center" class="top">
		<td><input type="button" value="关闭" class="button_ty" onclick="window.close()"></td>
		</tr>
	</table>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script type="text/javascript">
var contentDataTable;
var contentColumnHeaders; 
YAHOO.namespace("query.container"); 

function init(){
	YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) { 
		if(oColumn.key=="verifyNode"){
	      	switch(oRecord.verifyNode){
	      	  case '<%=IConstants.VERIFYNODE_END %>':elCell.innerHTML="结束";break;
	      	  case '<%=IConstants.VERIFYNODE_START %>':elCell.innerHTML="开始";break;
	  		  case '<%=IConstants.VERIFYNODE_FIRSTAUDIT %>':elCell.innerHTML="一级审核";break;
	  		  case '<%=IConstants.VERIFYNODE_SECONDAUDIT %>':elCell.innerHTML="二级审核";break;
	  		  case '<%=IConstants.VERIFYNODE_THIRDAUDIT %>':elCell.innerHTML="三级审核";break;
	  		  case '<%=IConstants.VERIFYNODE_REJECT %>':elCell.innerHTML="驳回";break;
	  		  case '<%=IConstants.VERIFYNODE_USER %>':elCell.innerHTML="用户";break;
	      	}
	 	}

		if(oColumn.key=="verifyUserName"){
		 	switch(oRecord.verifyUserName){
		 	 case '<%=IConstants.NODESTATUS_NOTVERIFY %>':elCell.innerHTML="未审核";break;
		 	 case '<%=IConstants.NODESTATUS_USERCANCELTASK%>':elCell.innerHTML="任务取消";break;
		 	 default :elCell.innerHTML=oRecord.verifyUserName;break;
		 	}
		}
	 	if(oColumn.key=="verifyOpinion"){
		 	switch(oRecord.verifyOpinion){
		 	 case '<%=IConstants.NODESTATUS_NOTVERIFY %>':elCell.innerHTML="未审核";break;
		 	 case '<%=IConstants.NODESTATUS_USERCANCELTASK%>':elCell.innerHTML="任务取消";break;
		 	 default :elCell.innerHTML=oRecord.verifyOpinion;break;
		 	}
		}
	};
	contentColumnHeaders =[
		{key:"verifyNode",text:"审核环节",width:"20em",sortable:true,type:"link"},
		{key:"verifyUserName",text:"审核人",width:"20em",sortable:true,type:"link"},
		{key:"verifyOpinion",text:"审核意见",width:"40em",sortable:true,type:"link"}
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

	var myDataSource = new YAHOO.util.DataSource("${ctx}/audit/viewOpinions.do");

    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;

	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["verifyNode" , "verifyUserName", "verifyOpinion"],
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
