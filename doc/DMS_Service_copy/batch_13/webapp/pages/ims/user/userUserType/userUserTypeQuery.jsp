<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>

<html>
<head>
<title>添加用户</title>
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
<h2 align="center">请输入查询条件</h2>
</div>
<s:form name="fm" action="" target="userTypeRight">
<s:hidden name="userType" value="${userType}"></s:hidden>
	<table class="fix_table">	
		<tr>
			<td class="bgc_tt short">用户代码</td>			
			<td class="long"><input type="text" name="utiIUser.userCode"  class='input_w w_30'></td>			
			<td class="bgc_tt short">用户名称</td>
			<td class="long"><input type="text" name="utiIUser.userName"  class='input_w w_30'></td>
		</tr>		
		
        <tr>
			<td class="bgc_tt short">用户类型</td>	
            <td class="long" colspan="3">
					    <div id="userTypeDiv" class="selectui-indiv">
					        <div class="selectConfig">
					        <div class="codeType">StaticSelect</div>
					        </div>
					        <c:set var="checked" value="" />
					        <ce:select name="utiIUser.userType" id="userType" cssClass="selectui-input"  value="${checked}" 
					        list="#{'':'所有','01':'员工用户','02':'业务员用户','03':'虚拟用户','04':'合作伙伴用户','06':'企业客户','07':'个人客户','98':'临时用户','99':'其他用户'}" />
					    </div>
			</td>				
		</tr>		
		<tr align="center">
			<td align="center"  colspan="4">
                <input type="button" class="button_ty" value="查 询" onclick="executeQuery(1,10);">
            </td>
		</tr>
	</table>
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
	    		  elCell.innerHTML = "<input type=\"checkbox\" name=\"checkboxes\" value="+userCode+" ></input>";
		     } 
		     
		     //用户类型的显示
		     if(oColumn.key=="userType"){
			      	switch(oRecord["userType"]){
			      	  case '<%=IConstants.USERTYPE_STUFF %>':elCell.innerHTML="员工用户";break;
		      		  case '<%=IConstants.USERTYPE_SALES %>':elCell.innerHTML="业务员用户";break;
		      		  case '<%=IConstants.USERTYPE_VIRTUAL %>':elCell.innerHTML="虚拟用户";break;
		      		  case '<%=IConstants.USERTYPE_PARTNERS %>':elCell.innerHTML="合作伙伴用户";break;
		      		  case '<%=IConstants.USERTYPE_ENTERPRISE %>':elCell.innerHTML="企业客户";break;
		      		  case '<%=IConstants.USERTYPE_PERSONAL %>':elCell.innerHTML="个人客户";break;
		      		  case '<%=IConstants.USERTYPE_TEMPORARY %>':elCell.innerHTML="临时用户";break;
		      		  case '<%=IConstants.USERTYPE_OTHER %>':elCell.innerHTML="其他用户";break;
			      	}
			 }
		 };
		
		contentColumnHeaders =[
			{key:"serialNo",text:"序号",width:"20em",type:"link",resizeable:true},
			{key:"userCode",text:"用户代码",width:"40em",sortable:true},
			{key:"userName",text:"用户名称",width:"40em",sortable:true},
			{key:"userType",text:"用户类型",width:"40em",sortable:true,type:"link"}
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
		var myDataSource = new YAHOO.util.DataSource("${ctx}/utiIUser/queryUsersList.do");
	    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["userCode", "userName", "userType"],
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