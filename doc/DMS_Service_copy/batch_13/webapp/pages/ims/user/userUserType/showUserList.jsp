<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>

<html>
<head>
<title>用户查看</title>
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
</div>
	<s:form name="fm" action="">
		<s:hidden name="userType" value="${userType}"></s:hidden>
		<table class="fix_table">
			<tr>
			 	<div id="crash_menu">
			 	<h2 align="center">
			 	<s:if test="${userType == '01'}">员工用户</s:if>
				<s:if test="${userType == '02'}">业务员用户</s:if>
				<s:if test="${userType == '03'}">虚拟用户</s:if>
				<s:if test="${userType == '04'}">合作伙伴用户</s:if>
				<s:if test="${userType == '06'}">企业用户</s:if>
				<s:if test="${userType == '07'}">个人客户</s:if>
				<s:if test="${userType == '98'}">临时用户</s:if>
				</h2>
				</div>
			</tr>
		</table>
	</s:form>
</div>

	<div id="content" class="sort"></div>
	<div id="content_navigation" class="query" align="center"></div>
		<s:if test="${userType != '01'}">
<table>
	<tr align="right">


		<td><input type="button" value="添加用户" onclick="addUser()" class="button_ty"></td>
		<td><input type="button" value="删除用户" onclick="delUser()" class="button_ty"></td>
	
	</tr>
</table>
	</s:if>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script type="text/javascript">
var contentDataTable;
var contentColumnHeaders; 
YAHOO.namespace("query.container"); 

function init(){
	YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) { 
		 var userCode = oRecord.userCode;
		 if(oColumn.key=="serialNo"){
   		  elCell.innerHTML = "<input type=\"checkbox\" name=\"checkboxes\" value="+userCode+" ></input>";
	     } 
	 };

	
	contentColumnHeaders =[
		{key:"serialNo",text:"选择",width:"20em",type:"link",resizeable:true},
		{key:"userCode",text:"用户代码",width:"20em",sortable:true},
		{key:"userName",text:"用户名称",width:"30em",sortable:true}
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

	var myDataSource = new YAHOO.util.DataSource("${ctx}/utiIUser/queryUserByUserType.do");

    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;

	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["userCode" , "userName"],
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

	function addUser(){
		vURL='${ctx}/utiIUser/userUserTypeQuery.do';
		window.open(vURL,"","width=600,height=400,top=200,left=300,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=yes");
	}
	function delUser(){
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
            if(confirm('确认删除该用户!')){
            	fm.action="${ctx}/utiIUser/delUserFromUserType.do?userCodes="+userCodes;
    		    fm.submit();
              }
			
	        }
        return true;
	}
	
</script>
