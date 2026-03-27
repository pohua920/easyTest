<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<html>
<head>
<title>功能代码</title>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">功能代码表维护</h2>
</div>
<s:form name="fm" action="findTask" namespace="/saaTask" method="post">
	<table class="fix_table">
		<tr>
			<td class="bgc_tt short">功能代码</td>
			<td class="long"><input name="saaTask.taskCode"
				id="saaTask.taskCode" class='input_w w_15'></td>
			<td class="bgc_tt short">上级功能代码</td>
			<td class="long" ><input name="saaTask.parentCode"
				id="saaTask.parentCode" class='input_w w_15' 
				ondblclick="code_CodeSelect(this, 'methodTask', '0', 'Y','')"
				onkeyup="code_CodeSelect(this, 'methodTask', '0', 'Y','')"
				onchange="code_CodeChange(this, 'methodTask', '0', 'Y','')" ></td>
			<td class="bgc_tt short">功能名称简体</td>
			<td class="long"><input name="saaTask.taskCName"
				id="saaTask.taskCName" class='input_w w_15'></td>
		</tr>
		<tr>
			<td class="bgc_tt short">创建人代码</td>
			<td class="long"><input name="saaTask.creatorCode"
				id="saaTask.creatorCode" class='input_w w_15'></td>
			<td class="bgc_tt short">最后修改人代码</td>
			<td class="long"><input name="saaTask.updaterCode"
				id="saaTask.updaterCode" class='input_w w_15'></td>
			<td class="bgc_tt short">功能名称英文</td>
			<td class="long"><input name="saaTask.taskEName"
				id="saaTask.taskEName" class='input_w w_15'></td>
		</tr>
		<tr>
			<td colspan="3" align="center"><input type="button"
				class="button_ty" value="查 询" onclick="executeQuery(1,10);">
			</td>
			<td colspan="3" align="center">
			<input type="button" class="button_ty" value="增加"
				onclick="editRecord('${ctx}/saaTask/task.do?type=addTask');">
			</td>	
		</tr>
	</table>
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
	//Page Init
	function init(){
		YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) { 
		    var oId = oRecord.id;
		    if(oColumn.key=="edit"){
	    		elCell.innerHTML = "<a href=\"#\" onclick=\"editRecord('${ctx}/saaTask/task.do?type=updateTask&id=" + oRecord.id+"')\">修改</a>";
	    	}else if(oColumn.key=="del"){
	    		elCell.innerHTML = "<a href='javascript:dddddd("+oRecord.id+");'>删除</a>";
	  		}else if(oColumn.key=="validStatus"){
		    	switch(oRecord.validStatus){
		    		case '0': elCell.innerHTML="无效";break;
		    		case '1': elCell.innerHTML="有效";break;
		    	}
	  		}else if(oColumn.key=="taskCName"){
		    	elCell.innerHTML = "<a href=\"#\" onclick=\"editRecord('${ctx}/saaTask/task.do?type=showTask&id=" + oRecord.id+"')\">"+ oData +"</a>";
		    }else if(oColumn.key=="createTime" || oColumn.key=="updateTime"){
		    	var date = new Date(oData.time);
		    	elCell.innerHTML =  date.toLocaleString();
		    }else{
		    	elCell.innerHTML = oData;
		    }
		};
		contentColumnHeaders =[
			{key:"taskCode",text:"功能代码",width:"30em",sortable:true},
			{key:"parentCode",text:"上级功能",width:"40em",sortable:true},
			{key:"taskCName",text:"功能名称",width:"40em",sortable:true,type:"link"},
			{key:"validStatus",text:"有效标志",width:"40em",sortable:true,type:"link"},
			{key:"creatorCode",text:"创建人",width:"50em",sortable:true},
			{key:"createTime",text:"创建时间",width:"50em",sortable:true,type:"link"},
			{key:"updaterCode",text:"修改人",width:"50em",sortable:true},
			{key:"updateTime",text:"修改时间",width:"50em",sortable:true,type:"link"},
			{key:"edit",text:"<s:text name="prompt.edit" />",width:"20em",type:"link",resizeable:true},
			{key:"del",text:"<s:text name="prompt.del" />",width:"20em",type:"link",resizeable:true}
			];
		//initAllSelectUi();
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
		var myDataSource = new YAHOO.util.DataSource("${ctx}/saaTask/findTask.do");
		myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["taskCode", "parentCode", "taskCName", "taskEName", "taskTName", "creatorCode", 
				"createTime", "updaterCode", "updateTime" , "id" ,"validStatus"],
		   totalRecords: "totalRecords"
		};
		myDataSource.subscribe("responseParseEvent",SINOSOFT.util.navigation);	
		myDataSource.connMgr.setForm(fm);
		var initialRequest = "pageSize="+pageSize+"&pageNo="+pageNo;
		var myConfiges ={
			initialRequest:initialRequest,
			paginator:false
		};   
		//if (isFirstLoad==true){
			contentDataTable = new YAHOO.widget.DataTable("content",myColumnSet,myDataSource,myConfiges);
	
		//	isFirstLoad = false;	 
		//}else{
		//	contentDataTable._oRecordSet = new YAHOO.widget.RecordSet();
		//	contentDataTable.initialRequest = initialRequest;
		//	contentDataTable.dataSource = myDataSource;
		//	contentDataTable.dataSource.sendRequest(contentDataTable.initialRequest, contentDataTable.onDataReturnPopulateTable, contentDataTable);
		//} 
	}	
	//init on load
	YAHOO.util.Event.addListener(window,'load',init);		
	</script>
	
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/dwr/interface/dwrInvokeDataAction.js"></script>
<script language="javascript" src="${ctx}/widgets/yui/autocomplete/autocomplete-min.js"></script>

<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">
		function dddddd(id){		
			if(window.confirm("您确定删除吗？")){
				window.location.href="${ctx}/saaTask/deleteTask.do?id="+id;
			} else {				
			}	
		}
	</script>