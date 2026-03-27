<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>同步日志管理</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">查询条件页面</h2>
</div>
<s:form name="fm" action="viewLoginLog" namespace="/utiILog" method="post" >
	<table class="fix_table">
		<tr>
			<td class="bgc_tt short">操作人姓名</td>
			<td class="long"><input type="text" name="userName" id="userName" class='input_w w_15'></td>

			<td class="bgc_tt short">操作时间</td>
			<td class="long">
<!--			<input readonly="true" name="utiISyncLog.synOccrTime" -->
<!--					id="utiISyncLog.synOccrTime" class='input_w w_15' maxlength="20" >-->
<!--				<img src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板" id="imgBtn1" width="14" height="14" /> -->
<!--				<span class="calender-panel">-->
<!--					<div id="calContainer1" style="position: absolute;"></div>-->
<!--				</span>-->
				<input readonly="true" name="utiISyncLog.synOccrTime" id="utiISyncLog.synOccrTime" class="input_w w_15 Wdate" onFocus="WdatePicker()" maxlength="20">
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center"><input type="button" class="button_ty" value="查 询" onclick="executeQuery(1,10);" /></td>
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
<!--<script language="javascript" src="${ctx}/common/js/newCalendar.js"></script>-->
<script type="text/javascript">
	var contentDataTable;
	var contentColumnHeaders; 
	YAHOO.namespace("query.container"); 
	var code;
	var hasSpace = false;
	function init(){
		// YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) {
	 			contentColumnHeaders =[
				{key:"userCode",text:"用户代码",width:"35em",sortable:true},
				{key:"userName",text:"用户姓名",width:"35em",sortable:true},
				{key:"svrCode",text:"服务代码",width:"40em",sortable:true},
				{key:"synSourceSvr",text:"同步来源",width:"45em",sortable:true},
				{key:"synDestSvr",text:"同步目的地",width:"45em",sortable:true},
				{key:"synOccrTime",text:"同步发生时间",width:"35em",sortable:true},
				{key:"choose",text:"选择",width:"10em",sortable:true,type:"link"}
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
		myDataSource = new YAHOO.util.DataSource("${ctx}/utiILog/viewSyncLog.do");
		myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["userCode","userName","svrCode","sycSourceSvr","synDestSvr","synOccrTime"],
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

	//init_calendar("calContainer1","imgBtn1","utiISyncLog.synOccrTime");
	</script>