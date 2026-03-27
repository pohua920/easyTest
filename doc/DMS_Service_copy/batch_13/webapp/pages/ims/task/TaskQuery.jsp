<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>功能管理</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.*"%>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">功能查询</h2>
</div>
<s:form name="fm" action="" namespace="/utiITask" method="post" >
	<table class="fix_table">
	<input type="hidden" name="saaTask.id" id="saaTask.id" value="${saaTask.id }" />
		<tr>
			<td class="bgc_tt short">功能代码</td>
			<td class="long">
			    <div id="validStatusMapDiv" class="selectui-indiv">
			        <div class="selectConfig">
			        <div class="codeType">StaticSelect</div>
			        </div>
			        <c:set var="checked" value="0" />
			        <ce:select name="saaTask.taskCode" id="saaTask.taskCode" cssClass="selectui-input-up input_w w_30" value="${checked}" onchange="getName();" list="taskCodeMap" />
			    </div>
			</td>
			<td class="bgc_tt short">功能名称</td>
			<td class="long">
				<div id="validStatusMapDiv" class="selectui-indiv">
				<div class="selectConfig"></div>
					<input type="text" name="saaTask.taskCName" id="saaTask.taskCName" class="selectui-input-up input_w w_30" readonly="true" value="${saaTask.taskCName }" />
				</div>
			</td>
			<td class="bgc_tt short">有效性</td>
			<td class="long">
		   		<div id="validStatusDiv" class="selectui-indiv">
		        <div class="selectConfig">
		        <div class="codeType">StaticSelect</div>
		        </div>
				<ce:select name="saaTask.validStatus" id="saaTask.validStatus" cssClass="selectui-input input_w w_30" list="#{'':'所有','1':'有效','0':'无效'}" />
				</div>
			</td>
		</tr>
		<tr>
		</table>
		
</s:form></div>
<div id="content_navigation" class="query" align="center"></div>
<div id="content" class="sort"></div>
<div id="content_navigation" class="query" align="center"></div>
</div>
<table>
			<td colspan="3" align="center">
				<input type="button" name="query" class="button_ty" align="center" value="查询" onclick="executeQuery(1,10);"/>
			</td>
			<td colspan="3" align="center">
				<input type="button" name="add" class="button_ty" align="center" value="增加" onclick="prepareAddTask();"/>
			</td>
		</tr>
	</table>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script type="text/javascript" src='/ims/dwr/interface/Ims.js'></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">
var contentDataTable;
var contentColumnHeaders; 
var isFirstLoad = true;
YAHOO.namespace("query.container"); 
function init(){
	 YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) {
		 var oCode = oRecord.taskCode;
		  if(oColumn.key=="modify"){
			  elCell.innerHTML = "<a href=\"#\" onclick=\"modifyTask('"+oCode+"');\">修改</a>";
		  }
		  if(oColumn.key=="delete"){
			  elCell.innerHTML = "<a href=\"#\" onclick=\"logOutOrIn('"+oCode+"');\">启动/注销</a>";
	      }
	  	  if(oColumn.key=="taskCode"){
	  		  elCell.innerHTML = "<a href=\"#\" onclick=\"viewTask('"+oCode+"');\">"+oCode+"</a>";
		  }
		  if(oColumn.key=="validStatus"){
			  switch(oRecord.validStatus){
	      		  case '0':elCell.innerHTML="无效";break;
	      		  case '1':elCell.innerHTML="有效";break;
      		  }
		  }
	 };
 			contentColumnHeaders =[
				{key:"taskCode",text:"功能代码",width:"25em",sortable:true,type:"link"},
				{key:"taskCName",text:"功能名称",width:"25em",sortable:true},
				{key:"parentCode",text:"上级功能代码",width:"20em",sortable:true},
				{key:"svrCode",text:"所属服务代码",width:"20em",sortable:true},
			    {key:"validStatus",text:"有效标志",width:"10em",sortable:true,type:"link"},
				{key:"modify",text:"修改",width:"8em",type:"link",resizeable:true},
				{key:"delete",text:"启动/注销",width:"12em",type:"link",resizeable:true}
			];
}
//查询数据
var myDataSource ;
var initialRequest;
function executeQuery(pageNo,pageSize){
	if(isNaN(parseInt(pageNo))){
		pageNo = 1;
	}
	if(isNaN(parseInt(pageSize))){
		pageSize = 10;
	}
	var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);	
	myDataSource = new YAHOO.util.DataSource("${ctx}/utiITask/taskQuery.do");
	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["taskCode","taskCName","parentCode","svrCode","validStatus"],
	   totalRecords: "totalRecords"
	};
	myDataSource.subscribe("responseParseEvent",SINOSOFT.util.navigation);	
	myDataSource.connMgr.setForm(fm);
	initialRequest = "pageSize="+pageSize+"&pageNo="+pageNo;
	var myConfiges ={
		initialRequest:initialRequest,
		paginator:false
	};   
		contentDataTable = new YAHOO.widget.DataTable("content",myColumnSet,myDataSource,myConfiges);
	}
	
function getName(){
	//alert(fm.taskCode.options[fm.taskCode.selectedIndex].text);
	var code = fm.document.getElementById("saaTask.taskCode").options[fm.document.getElementById("saaTask.taskCode").selectedIndex].text;
	var scode = code.split("-");
	if(scode[1]!=""){
		Ims.getTaskCName(scode[1],callBack);
	}else{
		document.getElementById("saaTask.taskCName").value = "";
	}
}
function callBack(data){
	if(data!=null){
		document.getElementById("saaTask.taskCName").value = data;
	}else{
		document.getElementById("saaTask.taskCName").value = "";
	}
}
/*
function confirmDel(code){
	if(confirm("确认要删除此功能?")){
		fm.action = "${ctx}/utiITask/deleteTask.do?taskCode="+code;
		fm.submit();
	}else{
		return;
	}
}
*/
function logOutOrIn(code){
		if(confirm("确认操作?")){
			var args = new SINOSOFT.util.QueryString( contentDataTable.initialRequest );
			var pageSize = parseInt(args["pageSize"],10);
			var pageNo = parseInt(args["pageNo"],10);
			executeQuery(pageNo,pageSize);
		//	fm.action = "${ctx}/utiITask/logOutOrIn.do?taskCode="+code;
		//	fm.submit();
			var url = "${ctx}/utiITask/logOutOrIn.do?taskCode="+code;
			var req = YAHOO.util.Connect.asyncRequest('POST', url, "");
		}else{
			alert("操作取消");
			return;
		}
	}
function viewTask(code){
	fm.action = "${ctx}/utiITask/viewTask.do?taskCode="+code;
	fm.submit();
}
function prepareAddTask(){
	fm.action = "${ctx}/utiITask/prepareAddTask.do";
	fm.submit();
}

function modifyTask(code){
	fm.action = "${ctx}/utiITask/prepareModifyTask.do?taskCode="+code;
	fm.submit();
}

	YAHOO.util.Event.addListener(window,'load',init);
</script>
