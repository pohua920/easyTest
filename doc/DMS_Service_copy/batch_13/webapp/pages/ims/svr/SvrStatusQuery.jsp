<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>服务管理</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.*"%>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">查询条件页面</h2>
</div>
<s:form name="fm" action="querySvrList" namespace="/utiISvr" method="post" >
	<s:hidden name="flag" id="flag"></s:hidden>
	<table class="fix_table">
		<tr>
			<td class="bgc_tt short">服务代码</td>
			<td class="long"><input type="text" name="utiISvr.svrCode" id="utiISvr.svrCode" class='input_w w_15'></td>

			<td class="bgc_tt short">服务名称</td>
			<td class="long"><input type="text" name="utiISvr.svrName" id="utiISvr.svrName" class='input_w w_15'></td>

			<td class="bgc_tt short">服务分类</td>
			<td class="long"><s:select list="#@java.util.HashMap@{'1':'数据库','2':'应用服务器','3':'应用系统'}" 
					name="utiISvr.svrType" id="utiISvr.svrType" /></td>
			<td><input type="button" class="button_ty" value="查 询" onclick="executeQuery(1,10);" /></td>
		</tr>
		<tr>
			<td><input type="button" class="button_ty" id="mrs" value="权限管理设置" onclick="changeStatus(this);" /></td>
			<td><input type="button" class="button_ty" id="mms" value="菜单管理设置" onclick="changeStatus(this);" /></td>
			<td><input type="button" class="button_ty" id="mls" value="登录管理设置" onclick="changeStatus(this);" /></td>
			<td><input type="button" class="button_ty" id="mas" value="账户管理设置" onclick="changeStatus(this);" /></td>
			<td><input type="button" class="button_ty" id="ass" value="账户同步设置" onclick="changeStatus(this);" /></td>
			<td><input type="button" class="button_ty" id="ams" value="账户信息设置" onclick="changeStatus(this);" /></td>
			<td><input type="button" class="button_ty" id="als" value="登录方式设置" onclick="changeStatus(this);" /></td>
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
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type='text/javascript' src="/ims/dwr/interface/ims.js"></script>
<script type='text/javascript' src="${ctx}/pages/ims/svr/SvrView.js"></script>
<script type='text/javascript' src="${ctx}/pages/ims/svr/SvrAdd.js"></script>
<script type='text/javascript' src="${ctx}/pages/ims/svr/SvrModify.js"></script>
<!--<script type='text/javascript' src="${ctx}/pages/ims/svr/changeStatus.js"></script>-->
<script type="text/javascript">
	var contentDataTable;
	var contentColumnHeaders; 
	YAHOO.namespace("query.container"); 
	var code;
	var hasSpace = false;
	function init(){
		 YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) {
			 var oCode = oRecord.svrCode;
			  if(oColumn.key=="serialNo"){
		    		  elCell.innerHTML = "<input type=\"checkbox\" name=\"checkboxes\" value="+oCode+" />";
			  } 
			  if(oColumn.key=="manageRightStatus"){
			      	switch(oRecord.manageRightStatus){
			      		case '0':elCell.innerHTML="不允许";break;
			      		case '1':elCell.innerHTML="允许";break;
			      	}
			   }
			  if(oColumn.key=="manageMenuStatus"){
			      	switch(oRecord.manageMenuStatus){
			      		case '0':elCell.innerHTML="不允许";break;
			      		case '1':elCell.innerHTML="允许";break;
			      	}
			   }
			  if(oColumn.key=="manageLoginStatus"){
			      	switch(oRecord.manageLoginStatus){
			      		case '1':elCell.innerHTML="平台管理";break;
			      		case '0':elCell.innerHTML="非平台管理";break;
			      	}
			   }
			  if(oColumn.key=="manageAccStatus"){
			      	switch(oRecord.manageAccStatus){
			      		case '1':elCell.innerHTML="平台管理";break;
			      		case '0':elCell.innerHTML="非平台管理";break;
			      	}
			   }
			  if(oColumn.key=="accSyncStatus"){
			      	switch(oRecord.accSyncStatus){
			      		case '0':elCell.innerHTML="否";break;
			      		case '1':elCell.innerHTML="是";break;
			      	}
			   }
			  if(oColumn.key=="accMsgSyncStatus"){
			      	switch(oRecord.accMsgSyncStatus){
			      		case '0':elCell.innerHTML="否";break;
			      		case '1':elCell.innerHTML="是";break;
			      	}
			   }
			  if(oColumn.key=="accLoginStatus"){
			      	switch(oRecord.accLoginStatus){
			      		case '0':elCell.innerHTML="账户登录";break;
			      		case '1':elCell.innerHTML="用户登录";break;
			      	}
			   }
			 };
	 			contentColumnHeaders =[
				{key:"svrCode",text:"服务代码",width:"30em",sortable:true},
				{key:"svrName",text:"服务名称",width:"30em",sortable:true},
				{key:"manageRightStatus",text:"权限管理",width:"30em",sortable:true,type:"link"},
			    {key:"manageMenuStatus",text:"菜单管理",width:"30em",sortable:true,type:"link"},
				{key:"manageLoginStatus",text:"登录管理方式",width:"40em",sortable:true,type:"link"},
				{key:"manageAccStatus",text:"账户管理方式",width:"40em",sortable:true,type:"link"},
				{key:"accSyncStatus",text:"账户同步管理",width:"30em",sortable:true,type:"link"},
				{key:"accMsgSyncStatus",text:"账户信息同步",width:"30em",sortable:true,type:"link"},
				{key:"accLoginStatus",text:"登录方式",width:"30em",sortable:true,type:"link"},
				{key:"serialNo",text:"序号",width:"20em",type:"link",resizeable:true}
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
		myDataSource = new YAHOO.util.DataSource("${ctx}/utiISvr/svrStatusQuery.do");
		myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["svrCode", "svrName","manageRightStatus" ,"manageMenuStatus","manageLoginStatus","manageAccStatus","accSyncStatus","accMsgSyncStatus","accLoginStatus"],
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
		YAHOO.util.Event.addListener(window,'load',init);

	    //更改管理权限状态
		function changeStatus(butt){
			var sid = butt.id;
		//	alert(sid);
			if(confirm("确定要对所选数据进行操作？")){
				//alert("是否对以上数据进行操作？");
			var aoCode;
			var codeList = new Array();;
			var num = 0;
			var n = 0;
			var checkbox = document.getElementsByName("checkboxes");
			for(var j=0;j<checkbox.length;j++){
					if(checkbox[j].checked){
						num = num + 1;
						codeList[n] = checkbox[j].value;
					//	alert(codeList[n]);
						n++;
					}
				}
			    if(num == 0){
				alert("请选择至少一条数据进行修改");
			    }else{
				aoCode = codeList[0];
				for(var i=1;i<codeList.length;i++){
					aoCode = aoCode + " and " +codeList[i];
				}
			    }
			    var args = new SINOSOFT.util.QueryString( contentDataTable.initialRequest );
				var pageSize = parseInt(args["pageSize"],10);
				var pageNo = parseInt(args["pageNo"],10);
				executeQuery(pageNo,pageSize);
				url = "contextRootPath/utiISvr/changeStatus.do?svrcode="+aoCode+"&status="+sid;
				var req = YAHOO.util.Connect.asyncRequest('POST', url, "");
			}else{
				alert("操作已取消");
			}
	    }
		YAHOO.util.Event.addListener(window,'load',init);

	</script>