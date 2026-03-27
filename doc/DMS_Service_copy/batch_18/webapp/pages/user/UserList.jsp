<!doctype html public "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title><s:text name="user.manage" /></title>
<%@ include file="/common/meta_css.jsp"%>
</head>
<body id="all_title">
<div id="wrapper">
<div id="crash_menu">
<h2 align="center">请输入查询条件</h2>
</div>
<s:form action="findUser" method="post" namespace="/user"
	validate="true" onsubmit="return submitQuery(this)">
	<table class="fix_table" border="0">
		<tr>
			<td class="bgc_tt short"><s:text name="user.username" /></td>
			<td class="long"><s:textfield name="user.userName" required="true" cssClass='input_w w_15'
				ondblclick="code_CodeSelect(this, 'userNameCodeQuery', '0', 'Y');"
				onkeyup="code_CodeSelect(this, 'userNameCodeQuery', '0', 'Y')"
				onchange="code_CodeChange(this, 'userNameCodeQuery', '0', 'Y')" />
			</td>
			<td class="bgc_tt short"><input type="submit" value="<s:text name="prompt.query" />" id="queryButton" class="button_ty"></td>
			<td class="bgc_tt short"><input type="button"
				value="<s:text name="prompt.advanced" />" id="advancedButton"
				onclick="showAdvanced(divMoreConditon,this)" class="button_ty"></td>
		</tr>
		<tr id="divMoreConditon" style="display: none">
			<td colspan="4">
			<table class="fix_table" border="0">
				<tr>
					<td class="bgc_tt short"><s:text name="prompt.id" /><s:text name="prompt.from" /></td>
					<td class="long"><s:textfield name="fromId" requiredposition="right" cssClass='input_w w_15' /></td>
					<td class="bgc_tt short"><s:text name="prompt.to" /></td>
					<td class="long"><s:textfield name="toId" requiredposition="right" cssClass='input_w w_15' /></td>
				</tr>

			</table>
			</td>
		</tr>
	</table>
</s:form></div>
<div id="content_control" class="query">
<table class="fix_table" border="0">
	<tr>

		<td  colspan="4" align="center"><input type="button"
			id="addTaskButton"
			onclick="location.href='${ctx}/user/prepareFindTask.do?tokenName=input'"
			class="button_ty" value="<s:text name="task.add" />">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" id="undwrtTaskButton"
			onclick="location.href='${ctx}/user/prepareFindTask.do?tokenName=underwrite'"
			class="button_ty" value="<s:text name="task.deal" />">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" id="addButton"
			onclick="location.href='${ctx}/user/prepareAddUser.do'"
			class="button_ty" value="<s:text name="prompt.add" />"></td>
	</tr>
</table>
</div>
<div id="content_navigation" class="query"></div>
<div id="content" class="sort"></div>
<div id="content_navigation" class="query"></div>
<%@ include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript">
var isFirstLoad = true;
var contentDataTable;
var contentColumnHeaders;
var queryForm;
YAHOO.namespace("query.container");
//Page Init
function init(){
	YAHOO.widget.Column.formatEmail = function(elCell, oRecord, oColumn, oData) { 
	    var user = oData; 
		elCell.innerHTML = "<a href=\"mailto:" + user + "@sinosoft.com.cn\">" + user + "</a>"; 
	}; 
	YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) { 
	    var oId = oRecord.id;
	    if(oColumn.key=="edit"){
	    	elCell.innerHTML = "<a href=\"#\" onclick=\"editRecord('./prepareUpdateUser.do?id=" + oId + "\')\"><s:text name="prompt.edit" /></a>"; 
	    }else if(oColumn.key=="del"){
	    	elCell.innerHTML = "<a href=\"#\" onclick=\"deleteRecord('./deleteUser.do?id=" + oId + "')\"><s:text name="prompt.del" /></a>";
	    }else if(oColumn.key=="userName"){
	    	elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('./viewUser.do?id=" + oId + "')\">"+oData+"</a>";
	    }else{
	    	elCell.innerHTML = oData
	    }
	}; 
	YAHOO.widget.Column.formatDate = function(elCell, oRecord, oColumn, oData) { 
		var value = oData;
		var date = new Date(oData.time);
		if(oColumn.key=="regDate"){			
			value = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
		}else{
			value = date.toLocaleString();
		}
		elCell.innerHTML = value; 
	}; 
	contentColumnHeaders =[
		{key:"id",text:"<s:text name="prompt.id" />",width:"5em",sortable:true},
		{key:"userName",text:"<s:text name="user.username" />",width:"10em",sortable:true,type:"link",resizeable:true},
		{key:"regDate",text:"<s:text name="user.regDate" />",width:"20em",sortable:true,type:"date",resizeable:true},
		{key:"loginTime",text:"<s:text name="user.loginTime" />",width:"20em",type:"date",sortable:true,resizeable:true},
		{key:"score",text:"<s:text name="user.score" />",width:"16em",sortable:true,resizeable:true},		
		{key:"edit",text:"<s:text name="prompt.edit" />",width:"5em",type:"link",resizeable:true},
		{key:"del",text:"<s:text name="prompt.del" />",width:"5em",type:"link",resizeable:true}
		];
}

//Query Data
function executeQuery(pageNo,pageSize){ 
// 当需要校验时打开
//	var valid = eval("validateForm_"+queryForm.name+"()");
//	if(valid==false){
//		return;
//	}
	if(isNaN(parseInt(pageNo))){
		pageNo = 1;
	}
	if(isNaN(parseInt(pageSize))){
		pageSize = 10;
	}
	var initialRequest = "pageSize="+pageSize+"&pageNo="+pageNo; 	
	var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);
	var myDataSource = new YAHOO.util.DataSource("./findUser.do");
	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["id","userName","regDate","loginTime","score","edit","del"],
	   totalRecords: "totalRecords"
	};

	myDataSource.subscribe("responseParseEvent",SINOSOFT.util.navigation);
	myDataSource.connMgr.setForm(queryForm);	
	var myConfiges ={
		initialRequest:initialRequest,
		paginator:false
	};

	if (isFirstLoad==true){
		contentDataTable = new YAHOO.widget.DataTable("content",myColumnSet,myDataSource,myConfiges);
		isFirstLoad = false;
	}else{
		contentDataTable._oRecordSet = new YAHOO.widget.RecordSet();
		contentDataTable.initialRequest = initialRequest;
		contentDataTable.dataSource = myDataSource;
		contentDataTable.dataSource.sendRequest(contentDataTable.initialRequest, contentDataTable.onDataReturnPopulateTable, contentDataTable);
	}
}
YAHOO.util.Event.addListener(window,'load',init);
function submitQuery(form){ 
	queryForm = form; 
	executeQuery(1,10);	
	return false;
}
</script>
</body>
</html>
