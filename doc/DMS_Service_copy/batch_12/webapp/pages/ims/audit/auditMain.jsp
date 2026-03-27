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
<div id="container">
<div id="crash_menu">
<h2 align="center">请输入查询条件</h2>
</div>
	<s:form name="fm" action="">
		<table class="fix_table">
			<tr>
				<td class="bgc_tt short">审核人</td>			
				<td class="long"><input type="text" name="auditTask.verifyUserName"  class='input_w w_30'></td>			
				<td class="bgc_tt short">审核机构</td>
				<td class="long"><input type="text" name="auditTask.comName"  class='input_w w_30'></td>
			</tr>
			<tr>
				<td class="bgc_tt short">审核环节</td>			
				<td class="long"><select name="auditTask.verifyNode">
					<option value="a" selected="selected">--所有环节--</option>
					<option value="0">结束</option>
					<option value="1">一级审核</option>
					<option value="2">二级审核</option>
					<option value="3">三级审核</option>
					<option value="4">审核驳回</option>
					</select>
					</td>			
				<td class="bgc_tt short">审核状态</td>
				<td class="long"><select name="auditTask.nodeStatus">
					<option value="a" selected="selected">--所有状态--</option>
					<option value="1">已提交</option>
					<option value="11">一审通过</option>
					<option value="10">一审不通过</option>
					<option value="12">一审驳回</option>
					<option value="21">二审通过</option>
					<option value="20">二审不通过</option>
					<option value="22">二审驳回</option>
					<option value="31">三审通过</option>
					<option value="30">三审不通过</option>
					<option value="32">三审驳回</option>
					<option value="00">已取消</option>
					<option value="111">重新提交</option>
					</select>
					</td>
			</tr>
			</table>
			
	</s:form></div>
	   <table>
			<tr align="center">
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
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">
var contentDataTable;
var contentColumnHeaders; 
YAHOO.namespace("query.container"); 

function init(){
	 YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) { 
		 var oId = oRecord.serialNo;
		 var node = oRecord.verifyNode;
		 if(node == '0'){
			 if(oColumn.key=="manage"){
	    		  elCell.innerHTML = "<a onclick='queryTaskNode("+ oId + ")' href='#'>查看状态</a>";
		     }
		 }else {
			 if(oColumn.key=="manage"){
	    		  elCell.innerHTML = "<a href='#' onclick='cancelTask("+ oId +")'>取消任务</a><a onclick='queryTaskNode("+ oId + ")' href='#''>查看状态</a>";
		     }
		 }

		 if(oColumn.key=="verifyNode"){
		      	switch(oRecord.verifyNode){
		      	  case '<%=IConstants.VERIFYNODE_END %>':elCell.innerHTML="结束";break;
		  		  case '<%=IConstants.VERIFYNODE_FIRSTAUDIT %>':elCell.innerHTML="一审";break;
		  		  case '<%=IConstants.VERIFYNODE_SECONDAUDIT %>':elCell.innerHTML="二审";break;
		  		  case '<%=IConstants.VERIFYNODE_THIRDAUDIT %>':elCell.innerHTML="三审";break;
		  		  case '<%=IConstants.VERIFYNODE_REJECT %>':elCell.innerHTML="驳回";break;
		      	}
		 	}

			if(oColumn.key=="nodeStatus"){
		      	switch(oRecord.nodeStatus){
		      	  case '<%=IConstants.NODESTATUS_APPLICATIONSUBMIT %>':elCell.innerHTML="提交申请";break;
		  		  case '<%=IConstants.NODESTATUS_NOTVERIFY %>':elCell.innerHTML="未审核";break;
		  		  case '<%=IConstants.NODESTATUS_FIRSTDISAPPROVE %>':elCell.innerHTML="一审未通过";break;
		  		  case '<%=IConstants.NODESTATUS_FIRSTAPPROVE %>':elCell.innerHTML="一审通过";break;
		  		  case '<%=IConstants.NODESTATUS_FIRSTREJECT %>':elCell.innerHTML="一审驳回";break;
		  		  case '<%=IConstants.NODESTATUS_SECONDDISAPPROVE %>':elCell.innerHTML="二审不通过";break;
		  		  case '<%=IConstants.NODESTATUS_SECONDAPPROVE %>':elCell.innerHTML="二审通过";break;
		  		  case '<%=IConstants.NODESTATUS_SECONDREJECT %>':elCell.innerHTML="二审驳回";break;
		  		  case '<%=IConstants.NODESTATUS_THIRDDISAPPROVE %>':elCell.innerHTML="三审不通过";break;
		  		  case '<%=IConstants.NODESTATUS_THIRDAPPROVE %>':elCell.innerHTML="三审通过";break;
		  		  case '<%=IConstants.NODESTATUS_THIRDREJECT %>':elCell.innerHTML="三审驳回";break;
		  		  case '<%=IConstants.NODESTATUS_USERCANCELTASK %>':elCell.innerHTML="任务取消";break;
		  		  case '<%=IConstants.NODESTATUS_USERREPLAYSUBMIT %>':elCell.innerHTML="重新提交申请";break;
		      	}
		 	}

		 	if(oColumn.key=="verifyUserName"){
			 	switch(oRecord.verifyUserName){
			 	 case '<%=IConstants.NODESTATUS_NOTVERIFY %>':elCell.innerHTML="未审核";break;
			 	 case '<%=IConstants.NODESTATUS_USERCANCELTASK%>':elCell.innerHTML="任务取消";break;
			 	 default :elCell.innerHTML=oRecord.verifyUserName;break;
			 	}
			}

		 	if(oColumn.key=="comName"){
			 	switch(oRecord.comName){
			 	 case '<%=IConstants.NODESTATUS_NOTVERIFY %>':elCell.innerHTML="未审核";break;
			 	 case '<%=IConstants.NODESTATUS_USERCANCELTASK%>':elCell.innerHTML="任务取消";break;
			 	 default :elCell.innerHTML=oRecord.comName;break;
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
		{key:"userName",text:"新用户",width:"10em",sortable:true},
		{key:"taskName",text:"审核内容",width:"20em",sortable:true},
		{key:"verifyUserName",text:"审核人",width:"10em",sortable:true,type:"link"},
		{key:"comName",text:"审核机构",width:"15em",sortable:true,type:"link"},
		{key:"verifyNode",text:"审核环节",width:"10em",sortable:true,type:"link"},
		{key:"nodeStatus",text:"审核状态",width:"15em",sortable:true,type:"link"},
		{key:"verifyOpinion",text:"审核意见",width:"35em",sortable:true,type:"link"},
		{key:"manage",text:"操作",width:"15em",type:"link",resizeable:true}
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
	var myDataSource = new YAHOO.util.DataSource("${ctx}/audit/queryTaskList.do");

    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;

	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["serialNo" ,"userName", "taskName" , "verifyUserName" , "comName","verifyNode","nodeStatus","verifyOpinion"],
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

	//用户取消任务
	function cancelTask(taskId){
		if(confirm("确定要对所选数据进行操作？")){
			url = "contextRootPath/audit/cancleTask.do?taskId="+taskId;
			var handleSuccess = function(o){
				var args = new SINOSOFT.util.QueryString( contentDataTable.initialRequest );
				var pageSize = parseInt(args["pageSize"],10);
				var pageNo = parseInt(args["pageNo"],10);
				executeQuery(pageNo,pageSize);
			};
			var callback =
			{
			  success:handleSuccess
			};
			var req = YAHOO.util.Connect.asyncRequest('POST', url, callback , "");
		}else{
			alert("操作已取消");
		}
    }
	YAHOO.util.Event.addListener(window,'load',init);

	function queryTaskNode(taskId){
		vURL='${ctx}/audit/queryTaskNode.do?taskId=' + taskId;
		window.open(vURL,"","width=600,height=400,top=200,left=300,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=yes");
	}
	
</script>
