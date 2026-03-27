<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>

<html>
<head>
<title>帐号登录权限设置</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
 <%-- moidfy  update by tongziliang 2011-09-29 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
</head>

<body id="all_title">

<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">请输入查询条件</h2>
</div>
<s:form name="fm" action="" target="accountTreeRight">
	<table class="fix_table">
	    <tr>
			<td class="bgc_tt short">账号代码</td>			
			<td class="long"><input type="text" name="utiIAccount.accCode"  class='input_w w_30'></td>			
			<td class="bgc_tt short">账号名称</td>
			<td class="long"><input type="text" name="utiIAccount.accName"  class='input_w w_30'></td>
		</tr>
		<tr>		
			<td class="bgc_tt short">服务名称</td>
			<td class="long">
			<c:set var="checked" value="0" />
			<ce:select name="svrCode" value="${checked}"
				id="svrCode" cssClass="input_y w_p90" list="svrCodeMap" />
			</td>
			<td class="bgc_tt short">用户名称</td>
			<td class="long">
			<ce:select name="utiIAccount.userCode" value="${checked}"
				id="companyCode" cssClass="input_y w_p90" list="userCodeMap" />
			</td>
		</tr>	
		<tr colspan="4">		
			<td class="bgc_tt short">允许登录标志</td>
			<td class="long" colspan="3">
			<c:set var="checked" value="" />
		    <ce:select name="utiIAccount.loginPowerFlag" id="loginPowerFlag" cssClass="selectui-input"  value="${checked}" 
			list="#{'':'所有','0':'不允许','1':'允许','2':'未设置'}" />
			</td>
		</tr>		
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr align="center" class="top">
			<td>
			<button type="button"  value="" onclick="executeQuery(1,10);"><span><em>查 询</em></span></button>
<!--			<input type="button" class="button_ty" value="查询" onclick="executeQuery(1,10);">-->
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
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">
	var contentDataTable;
	var contentColumnHeaders; 
	YAHOO.namespace("query.container"); 

	
	
	function init(){
		
		 YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) { 
			 var oId = oRecord.accCode;
			 var loginFlag = oRecord.loginPowerFlag;

			 
			 if(oColumn.key=="oper"){
				 if(oId == '00100000000ims000000'){
					 elCell.innerHTML = "";
				}else if(oId == '${sessionScope.accCode}'){
					 elCell.innerHTML = "";
				 }else if("<%=IConstants.ACCOUNTLOGINSTATUS_VALID %>"==loginFlag){
					 elCell.innerHTML = "<a href=\"#\" onclick=\"logOutOrIn('"+oId+"');\">设置为不允许</a>";
				 }else{
					 elCell.innerHTML = "<a href=\"#\" onclick=\"logOutOrIn('"+oId+"');\">设置为允许</a>";
				 }
	    	 }
			 if(oColumn.key=="accCode"){
				  elCell.innerHTML = "<a href=\"#\" onclick=\"viewMethod('"+oId+"');\">"+oId+"</a>";
			 }

			//是否允许 访问
		     if(oColumn.key=="loginPowerFlag"){
			      	switch(oRecord.loginPowerFlag){
			      	    
			      		case '<%=IConstants.ACCOUNTLOGINSTATUS_VALID %>':elCell.innerHTML="允许";break;
			      		case '<%=IConstants.ACCOUNTLOGINSTATUS_INVALID %>':elCell.innerHTML="不允许";break;
			      		default:elCell.innerHTML="未设置";break;
			      	}
			 } 
		 };
		
		contentColumnHeaders =[
			{key:"accCode",text:"账户代码",width:"20em",sortable:true},
			{key:"accName",text:"账户名称",width:"20em",sortable:true},
			{key:"userCode",text:"用户代码",width:"25em",sortable:true},
			{key:"userName",text:"用户名称",width:"20em",sortable:true},
			{key:"svrName",text:"服务名称",width:"20em",sortable:true},
			{key:"loginPowerFlag",text:"是否允许访问",width:"15em",sortable:true,type:"link"},
			{key:"oper",text:"操作",width:"20em",type:"link",resizeable:true}
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
		var myDataSource = new YAHOO.util.DataSource("${ctx}/utiIAccount/queryAccountSvrList.do");
	    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["accCode" , "accName" , "userCode", "userName", "svrName", "loginPowerFlag","accLogOrIn" ],
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


	
	//查看账户
	function viewMethod(aoCode){
		fm.action="${ctx}/utiIAccount/viewAccount.do?editType=view&accCode=" + aoCode;
        fm.submit();
	}
	
  //注销/启动
	function logOutOrIn(aoCode){
		if(confirm("确定要对所选数据进行操作？")){
			url = "${ctx}/utiIAccount/changeLoginPowerFlag.do?accCodes="+aoCode;
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
    
	</script>