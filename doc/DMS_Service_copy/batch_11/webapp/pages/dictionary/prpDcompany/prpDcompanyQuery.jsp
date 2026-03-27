<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.sync.SyncConstants"%>
<%
	String deployCom = (String)session.getAttribute("deployCom");
%>
<html>
<head>
<title>公司代码</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
</head>

<body id="all_title">

<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">请输入查询条件</h2>
</div>
<s:form name="fm" action="" namespace="" method="post">
<s:hidden name="flag" id="flag"></s:hidden>
<s:hidden name="currentCode" id="currentCode"></s:hidden>
<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
	<table class="fix_table">	
		<tr>
			<td class="bgc_tt short">机构代码</td>			
			<td class="long"><s:textfield name="prpDcompany.comCode"
				id="comCode" cssClass='input_w w_15'/></td>
			<td class="bgc_tt short">机构名称</td>
			<td class="long"><s:textfield name="prpDcompany.comCName"
				id="comCName" cssClass='input_w w_15'/></td>

			<td colspan="2" align="center">
			<button type="button"
				 value="查 询" onclick="executeQuery(1,10);"><span><em>查 询</em></span></button>
<!--			<input type="button"-->
<!--				class="button_ty" value="查 询" onclick="executeQuery(1,10);">-->
			</td>
		</tr>
	</table>
</s:form></div>
<div id="content_navigation" class="query" align="right"></div>
<div id="content" class="sort"></div>
<div id="content_navigation" class="query" align="right"></div>
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
	var deployCom = document.getElementById("deployCom").value;
	YAHOO.namespace("query.container");
	function init(){
		//var userCode_tip = new YAHOO.widget.Tooltip("userCode_tip",{text:"请双击选择员工代码",context:"saaUser.userCode",zIndex:300});
		//var comCode_tip = new YAHOO.widget.Tooltip("comCode_tip",{text:"请双击选择机构代码",context:"saaUser.comCode",zIndex:300});	
		 YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) {
		    var comCode = oRecord.comCode;
		    var valid = oRecord.validStatus;
		     if(oColumn.key=="edit"){	        
	    		 elCell.innerHTML = "<a href=\"#\" onclick=\"updateChild('${ctx}/dictionary/prepareUpdatePrpDcompany.do?editType=update&comCode="+comCode+"')\">申请修改</a>";		  
		      }
			if(oColumn.key=="add"){
	    		 elCell.innerHTML = "<a href=\"#\" onclick=\"addChild('${ctx}/dictionary/prepareInsertPrpDcompany.do?editType=insert&comCode="+comCode+"')\">申请增加</a>";
		      }
		    if(oColumn.key=="del"){    	
		    	if(valid == "1"){
		    	 	elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('"+comCode+"','"+valid+"')\">申请注销</a>";
		    	}else{
		    		elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('"+comCode+"','"+valid+"')\">申请启用</a>";
		    		}    		
		      }
		     if (oColumn.key == "comCode") {
	                elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDcompany.do?editType=view&comCode="+comCode+"')\">"+oData+"</a>"
	         }
		};
		if(deployCom == '<%=SyncConstants.ComCode_Head %>'){
			contentColumnHeaders =[
			{key:"comCode",text:"机构代码",width:"15em",sortable:true,type:"link"},
			{key:"comCName",text:"机构中文名",width:"40em",sortable:true},
			{key:"addressCName",text:"地址",width:"50em",sortable:true},
			{key:"add",text:"增加下级机构",width:"30em",type:"link",resizeable:true},
			{key:"edit",text:"修改",width:"20em",type:"link",resizeable:true},
			{key:"del",text:"注销/启用",width:"10em",type:"link",resizeable:true}]; 
			
		var currCompany = document.getElementById("currentCode").value;
		if(currCompany!==""){//如果用户点击树上的节点则直接查询
			executeQuery(1,10);
			}
		}
		else{
			contentColumnHeaders =[
			{key:"comCode",text:"机构代码",width:"15em",sortable:true,type:"link"},
			{key:"comCName",text:"机构中文名",width:"40em",sortable:true},
			{key:"addressCName",text:"地址",width:"50em",sortable:true}]; 
			
		var currCompany = document.getElementById("currentCode").value;
		if(currCompany!==""){//如果用户点击树上的节点则直接查询
			executeQuery(1,10);
			}			
		}
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
		var myDataSource = new YAHOO.util.DataSource("${ctx}/dictionary/queryPrpDcompany.do");
		myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["comCode", "comCName", "comEName","addressCName","validStatus"],
		   totalRecords: "totalRecords"
		};
		myDataSource.subscribe("responseParseEvent", SINOSOFT.util.navigation);	
		myDataSource.connMgr.setForm(fm);
		var initialRequest = "pageSize=" + pageSize + "&pageNo=" + pageNo;
		var myConfiges = {
			initialRequest :initialRequest,
			paginator :false
		}	;
			contentDataTable = new YAHOO.widget.DataTable("content",myColumnSet,myDataSource,myConfiges);
	}
	
	YAHOO.util.Event.addListener(window,'load',init);
	
		function delMethod(url){
			if(confirm("确定要删除？")){
			
			var handleSuccess = function(o){
				if(o.responseText=="error"){
					alert("本机构存在下级机构或存在用户，不能删除！");
					return false;
				}else{
				window.parent.companyTreeLeft.location.reload();//刷新树
				var args = new SINOSOFT.util.QueryString( contentDataTable.initialRequest );
				var pageSize = parseInt(args["pageSize"],10);
				var pageNo = parseInt(args["pageNo"],10);
				executeQuery(pageNo,pageSize);
				}
			};
			var handleFailure = function(o){
				if(o.responseText !== undefined){
					var msg = i18n.errors.deletefail+"!\n"+ o.status +" " + o.statusText;
					alert(msg);
				}
			};
			var callback =
			{
			  success:handleSuccess,
			  failure:handleFailure
			};
			var req = YAHOO.util.Connect.asyncRequest('POST', url, callback, "");
			}
		}
		function changeValidStatus(comCode,valid){
			var result;
	    	if(valid == "1"){
	   	 		result = "确定要注销吗？"
	   		 }
	   		 else{
	   			 result = "确定要启用吗？"
	   		 }
			if(confirm(result)){
			url="${ctx}/dictionary/changeValidStatus.do?prpDcompany.comCode="+comCode
			var handleSuccess = function(o){
				if(o.responseText=="error"){
					alert("本机构存在下级机构，不能注销！");
					return false;
				}else if(o.responseText=="error1"){
					alert("本机构上级机构已注销，不能启用！");
					return false;
				}else{
				//window.parent.companyTreeLeft.location.reload();//刷新树
				var args = new SINOSOFT.util.QueryString( contentDataTable.initialRequest );
				var pageSize = parseInt(args["pageSize"],10);
				var pageNo = parseInt(args["pageNo"],10);
				executeQuery(pageNo,pageSize);
				}
			};
			var handleFailure = function(o){
				if(o.responseText !== undefined){
					var msg = i18n.errors.deletefail+"!\n"+ o.status +" " + o.statusText;
					alert("操作失败！");
				}
			};
			var callback =
			{
			  success:handleSuccess,
			  failure:handleFailure
			};
			var req = YAHOO.util.Connect.asyncRequest('POST', url, callback, "");
			}
		}
		function addChild(url){
		editRecord(url);
			//fm.action = url;
			//fm.submit();
		}

		function updateChild(url){
		editRecord(url);
		//	fm.action = url;
		//	fm.submit();
		}
	</script>