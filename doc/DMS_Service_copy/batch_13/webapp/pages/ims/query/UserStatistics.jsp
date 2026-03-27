<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>

<html>
<head>
<title>用户统计</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>

</head>

<body id="all_title">

<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">请输入查询条件</h2>
</div>
<s:form name="fm" action="" target="companyTreeRight">
<s:hidden name="flag" id="flag"></s:hidden>
<s:hidden name="comCode" id="comCode" value="${comCode}"></s:hidden>
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
			<input type="hidden" value="" name="test">		
		</tr>

        <tr>
			<td class="bgc_tt short">阀值:大于等于</td>	
            <td class="long"><input type="text" name="utiIUser.userType"  class='input_w w_30'></td>
            <td class="bgc_tt short">小于等于</td>
			<td class="long"><input type="text" name="utiIUser.userSort"  class='input_w w_30'></td>
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
		    {key:"userCode",text:"用户代码",width:"40em",sortable:true},
			{key:"userName",text:"用户名称",width:"40em",sortable:true},
			{key:"comCode",text:"所属机构",width:"40em",sortable:true},
			{key:"accNum",text:"帐号个数",width:"20em",resizeable:true},
			{key:"loadNum",text:"登录次数",width:"20em",resizeable:true}
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
		var myDataSource = new YAHOO.util.DataSource("${ctx}/userQuery/getStatisticsList.do");
		
	    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["userCode", "userName", "comCode", "accNum","loadNum"],
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