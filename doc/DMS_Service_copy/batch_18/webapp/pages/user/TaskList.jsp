<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@ include file="/common/meta.jsp"%>
<title><s:text name="task.list" /></title>
<link href="${ctx}/styles/query.css" type="text/css" rel="stylesheet">

<script type="text/javascript">
YAHOO.namespace("query.container"); 
var isFirstLoad = true; 
var contentDataTable;
var contentColumnHeaders; 

function submitForm(url)
{
	dealForm.do = url;
	dealForm.submit();
}

//Page Init
function init(){

	YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) { 
	    var oId = oRecord.taskid;
	    if(oColumn.key=="deal"){
	    	elCell.innerHTML = "<a href=\"#\" onclick=\"submitForm('./dealTask_" + oId + ".do')\" ><s:text name="task.deal" /></a>"; 
	    }else if(oColumn.key=="businessno"){
			elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('./view_" + oData + ".do')\">"+oData+"</a>";
	    }
	    else{
	    	elCell.innerHTML = oData
	    }
	}; 

	contentColumnHeaders =[
		{key:"taskid",text:"<s:text name="prompt.id" />",width:"15em",sortable:true,type:"link"},
		{key:"businessno",text:"businessno",width:"10em",sortable:true,type:"link"},
		{key:"type",text:"type",width:"16em",sortable:true,type:"link"},
		{key:"nodeid",text:"nodeid",width:"20em",sortable:true},
		{key:"processid",text:"processid",width:"20em",sortable:true},
		{key:"deal",text:"<s:text name="task.deal" />",width:"20em",type:"link"}
		]; 
	
	initShowDialog("dialogShow","showFrame");
	
	//query
	executeQuery(1,50);

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
	var myDataSource = new YAHOO.util.DataSource("./query.do");
	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.responseSchema = {
	   resultsList: "data",
       fields: ["taskid","businessno","type","nodeid","processid"],
	   totalRecords: "totalRecords"
	};
	
	myDataSource.subscribe("responseParseEvent",SINOSOFT.util.navigation);
	myDataSource.connMgr.setForm(dealForm);
	
	var initialRequest = "pageSize=50&pageNo=1";
	
	var myConfiges ={
		initialRequest:initialRequest,
		paginator:false
	};   
	
	if (isFirstLoad==true){
		contentDataTable = new YAHOO.widget.DataTable("content",myColumnSet,myDataSource,myConfiges);
		contentDataTable.subscribe("cellClickEvent",contentDataTable.onEventSelectRow);
		contentDataTable.subscribe("cellClickEvent",contentDataTable.onEventEditCell);	 
		isFirstLoad = false;	 
	}else{
		contentDataTable._oRecordSet = new YAHOO.widget.RecordSet();
		contentDataTable.initialRequest = initialRequest;
		contentDataTable.dataSource = myDataSource;
		contentDataTable.dataSource.sendRequest(contentDataTable.initialRequest, contentDataTable.onDataReturnPopulateTable, contentDataTable);
	} 
}

//init on load
YAHOO.util.Event.addListener(window,'load',init);
</script>
</head>

<body id="all_title">
<div id="container">
<div id="header">
<h1><s:text name="task.list" /></h1>
</div>

<form name="dealForm" action="" method="POST">
	<s:hidden name="tokenName" value="%{tokenName}"/>
</form>

	<div id="content" class="sort"></div>
	<div id="content_navigation" name="content_navigation" class="query"></div>

	<div id="dialogShow">
		<div class="hd"><s:text name="prompt.show" /></div>
		<div class="bd"><iframe id="showFrame" src="about:blank" style="height:300px"></iframe>
	</div>
	
</div>

<div id="link">
	<p align="center">
	<button id="addButton" onclick="location.href='./prepareQuery.do?tokenName=input'" class="button_ty"><s:text
		name="录入任务" /></button>&nbsp;&nbsp;
	<button id="addButton" onclick="location.href='./prepareQuery.do?tokenName=underwrite'" class="button_ty"><s:text
		name="审批任务" /></button>&nbsp;&nbsp;
	<button id="addButton" onclick="location.href='../user/prepareQuery.do'" class="button_ty"><s:text
		name="员工列表" /></button>
	</p>
</div>

<%@ include file="/common/footer.jsp"%>

</body>
</html>
