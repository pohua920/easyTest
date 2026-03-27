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
<div id="crash_menu">
<h2 align="center">驳回的任务</h2>
</div>
	<s:form name="fm" action="">
	</s:form></div>
<div id="content_navigation" class="query" align="center"></div>
<div id="content" class="sort"></div>
<div id="content_navigation" class="query" align="center"></div>
</div>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script type="text/javascript">
var contentDataTable;
var contentColumnHeaders; 
YAHOO.namespace("query.container"); 

function init(){
	 YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) { 
		 var oTd = oRecord.taskinstanceCode;
		 var oUd = oRecord.userCode;
		 if(oColumn.key=="modify"){
    		  elCell.innerHTML = "<a href='${ctx}/audit/queryNewUser.do?taskInstanceId=" + oTd + "&userCode="+ oUd +"'>修改信息</a>";
	     } 
	 };
	
	contentColumnHeaders =[
		{key:"taskinstanceCode",text:"任务编号",width:"20em",sortable:true},
		{key:"userName",text:"新增用户",width:"20em",sortable:true},
		{key:"verifyUserName",text:"审核人",width:"20em",sortable:true},
		{key:"comName",text:"审核机构",width:"30em",sortable:true},
		{key:"verifyOpinion",text:"审核意见",width:"50em",sortable:true},
		{key:"modify",text:"操作",width:"20em",type:"link",resizeable:true}
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

	var myDataSource = new YAHOO.util.DataSource("${ctx}/audit/queryRejectTaskList.do");

    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;

	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["taskinstanceCode" , "userName","verifyUserName", "comName", "verifyOpinion","verifyDate","userCode"],
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
