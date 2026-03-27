<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>

<html>
<head>
<title>用户列表</title>
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
<h2 align="center">用户列表</h2>
</div>
<s:form name="fm" action="" target="userTypeRight">
<s:hidden name="flag" id="flag"></s:hidden>
<s:hidden name="comCode" id="comCode" value="${comCode}"></s:hidden>
	
</s:form></div>
<div id="content" class="sort"></div>
<div id="content_navigation" class="query" align="center"></div>
</div>
<table class="fix_table">
	<tr align="center">
		<td><input type="button" class="button_ty" value="添 加" onclick="return modifyMethod()" /></td>
	</tr>
</table>

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
			 var userCode = oRecord.userCode;
			 if(oColumn.key=="serialNo"){
	    		  elCell.innerHTML = "<input type=\"radio\" name=\"checkboxes\" value="+userCode+" ></input>";
		     } 
		     
		 };
		
		contentColumnHeaders =[
			{key:"serialNo",text:"序号",width:"20em",type:"link",resizeable:true},
			{key:"userCode",text:"用户代码",width:"40em",sortable:true},
			{key:"userName",text:"用户名称",width:"40em",sortable:true}
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
		var myDataSource = new YAHOO.util.DataSource("${ctx}/utiIUser/queryUserOnMatch.do");
	    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["userCode", "userName"],
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
	
	//修改
    function modifyMethod(){    
        var checkbox = document.getElementsByName("checkboxes");
		var userCodes = 'test';
        var num = 0;
        for(var j=0;j<checkbox.length;j++){
			if(checkbox[j].checked){
				num = num + 1;
				userCodes = userCodes + ',' + checkbox[j].value;
			}
        }
        if(num == 0){
        	alert("请选择一项进行修改");
        }else{
			fm.action="${ctx}/utiIUser/matchUserType.do?userCodes="+userCodes;
		    fm.submit();
		    window.close();
	        }
        return true;
    }

	</script>