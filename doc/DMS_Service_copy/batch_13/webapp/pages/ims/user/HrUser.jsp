<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>

<html>
<head>
<title>用户管理</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>

</head>

<body id="all_title">

<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">请输入员工的身份证</h2>
</div>
<s:form name="fm" action="" >
	<table class="fix_table">
		<tr>
			<td class="bgc_tt short" >身份证</td>
			<td class="long" ><input type="text" name="uid" id="uid" class='input_w w_30'></td>			
		</tr>	
		<tr align="center">
			<td align="center">
                <input type="button" class="button_ty" value="查  询" onclick="executeQuery(1,10);">
            </td>
            <td>
            	<input type="button" class="button_ty" value="返  回" onclick="return back();">
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
	function init(){
		 YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) { 
			 var uid = oRecord.id;
			 var uCode = oRecord.userCode;
			 var uName = oRecord.userName;
			 var cCode = oRecord.comCode;
			 var vs = oRecord.validStatus;
			 if(oColumn.key=="validStatus"){
			      	switch(oRecord.validStatus){
			      	  case '1':elCell.innerHTML="有效";break;
		      		  case '0':elCell.innerHTML="无效";break;
			      	}
			 }
			 if(oColumn.key=="insert"){
				  elCell.innerHTML = "<a href=\"#\" onclick=\"insertMethod('"+uCode+"','"+uName+"','"+cCode+"','"+uid+"','"+vs+"');\">添加到系统</a>";
		      }
		 };
		
		contentColumnHeaders =[
			{key:"userCode",text:"用户代码",width:"25em",sortable:true},
			{key:"userName",text:"用户名称",width:"30em",sortable:true},
			{key:"comCode",text:"归属机构代码",width:"20em",sortable:true},
			{key:"id",text:"身份证号",width:"20em",sortable:true},
			{key:"validStatus",text:"有效性",width:"15em",sortable:true,type:"link"},
			{key:"insert",text:"保存",width:"15em",sortable:true,type:"link"}
			]; 
	}
	//Query Data
	var myDataSource ;
	var initialRequest;
	function executeQuery(pageNo,pageSize){
		var id = document.getElementById("uid");
	//	if(id==null||id==""){
	//		alert("请输入身份证号");
	//	}
		if(isNaN(parseInt(pageNo))){ 
			pageNo = 1;
		}
		if(isNaN(parseInt(pageSize))){
			pageSize = 10;
		}
		var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);	
		var myDataSource = new YAHOO.util.DataSource("${ctx}/utiIUser/queryHrUser.do");
	    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["userCode", "userName", "comCode" , "id" ,"validStatus"],
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
	
	// 增加
	function insertMethod(uCode,uName,cCode,uid,vs){
		fm.action = "${ctx}/utiIUser/insertHrUser.do?uCode="+uCode+"&uName="+uName+"&cCode="+cCode+"&uid="+uid+"&vs="+vs;
        fm.submit();
        return true;
    }
	function back() {
		fm.action = "${ctx}/utiIUser/prepareFrame.do";
		fm.submit();
		return true;
	}
    
    
	</script>