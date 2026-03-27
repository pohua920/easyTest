<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>

<html>
<head>
<title>用户登录情况统计</title>
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
<s:form name="fm" action="" target="companyTreeRight">
<s:hidden name="flag" id="flag"></s:hidden>
	<table class="fix_table">	
		<tr>
			<td class="bgc_tt short">用户代码</td>			
			<td class="long"><input type="text" name="utiIUser.userCode"  class='input_w w_30'></td>			
			<td class="bgc_tt short">用户名称</td>
			<td class="long"><input type="text" name="utiIUser.userName"  class='input_w w_30'></td>
		</tr>		
		
       <tr>
			<td class="bgc_tt short">归属机构</td>
			<td class="long" colspan="3">
			<c:set var="checked" value="0" />
			<ce:select name="comCode" id="companyCode" cssClass="selectui-input-up input_w w_45" value="${checked}" list="companyListMap" /></td>
			<input type="hidden" name="test" value="">
		</tr>
        
		<tr align="center">
			<td align="center"  colspan="4">
                <input type="button" class="button_ty" value="查 询" onclick="executeQuery(1,10);">
                
                
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
		var comCode_tip = new YAHOO.widget.Tooltip("comCode_tip",{text:"请双击选择机构代码",context:"userStatisticsVO.comCode",zIndex:300});	
		 YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) { 
			// var userCode = oRecord["id.userCode"];
			 
		 };
		
		contentColumnHeaders =[
		    {key:"userCode",text:"用户代码",width:"25em",sortable:true},
			{key:"userName",text:"用户名称",width:"20em",sortable:true},
			{key:"comCode",text:"所属机构",width:"20em",sortable:true},
			{key:"loadNum",text:"登录次数",width:"15em",resizeable:true},
			{key:"loginTime",text:"最后登录时间",width:"30em",resizeable:true},
			{key:"exitTime",text:"离开时间",width:"20em",resizeable:true},
			{key:"holdTime",text:"持续时间",width:"30em",resizeable:true}
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
		var myDataSource = new YAHOO.util.DataSource("${ctx}/userQuery/getLoginStatList.do");
		
	    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["userCode", "userName", "comCode","loadNum","loginTime","exitTime","holdTime"],
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
	
	
	</script>