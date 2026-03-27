<%--
****************************************************************************
* DESC       ：调查记录
* AUTHOR     ：zhouxianli
* CREATEDATE ：2005-06-19
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<html locale="true">
	<head>
		<%@ include file="/common/taglibs.jsp"%>
		<%@include file="/common/i18njs.jsp"%>
		<%@include file="/common/meta_css.jsp"%>
		<%@include file="/common/meta_js.jsp"%>
		<title>
			<s:text name="title.checkBeforeEdit.investigatRecord" />
		</title>
		<%--调查记录--%>
	</head>
	<body class="yui-skin-sam">
		<form name="fm" action="${ctx }/check/lacciCheckBeforeQuery.do?editType=LacciCheckBeforeQuery" method="post">
			<table id="queryTable" width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="common">
				<tr>
					<td class="formtitle">
						<s:text name="title.checkBeforeEdit.investigatRecord" />
						<!--查询超时立案信息 -->
					</td>
				</tr>
			</table>
			<table width="98%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" id="tableResullt" style="display: none;">
							<tr>
								<td>
									<div id="content_message" style="display: none;"></div>
									<div id="listShowCont" align="left">
										<div id="listShow">
											<div id="content" class="sort"></div>
											<div id="content_navigation" class="query" style="text-align: center;"></div>
										</div>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<input type="hidden" name="editType" value="LacciCheckBeforeQuery">
			<input type="hidden" name="RegistNo" value="${param.RegistNo }">
		</form>
	</body>
	<script type="text/javascript">
		var isFirstLoad = true;
		var contentDataTable;
		var contentColumnHeaders;
		YAHOO.namespace("query.container");
		/*
		 *@description:初始化查询结果页面
		 *@param varSigns 无
		 *@return boolean 活动组别结果集    
		 *@author 中科软
		*/
		function init(){
			YAHOO.widget.DataTable.formatLink = function(elCell, oRecord, oColumn, oData) {
				if(oColumn.key =="certiType"){
					if(oData=="01"){
						elCell.innerHTML="<s:text name="check.report" />";<%--报案--%>
					}else if(oData=="03"){
						elCell.innerHTML="<s:text name="check.record" />";<%--立案--%>
					}else if(oData=="04"){
						elCell.innerHTML="<s:text name="check.advance" />";<%--预赔--%>
					}else if(oData=="05"){
						elCell.innerHTML="<s:text name="check.calculation" />";<%--计算书--%>
					}
				}else if(oColumn.key =="checkNature"){
					if(oData=="0"){
						elCell.innerHTML="<s:text name="check.directInvestigat" />";<%--直接调查--%>
					}else if(oData=="1"){
						elCell.innerHTML="<s:text name="check.indirectSurvey" />";<%--间接调查--%>
					}else if(oData=="2"){
						elCell.innerHTML="<s:text name="check.compositeSurvey" />";<%--複合調查--%>
					}
				}else if(oColumn.key =="checkDate"){
					if(oData!=null){
						var date = new Date(oData.time);
						elCell.innerHTML = formatDate(date,'yyy-MM-dd');
					}
				}else if(oColumn.key =="checkEndDate"){
					if(oData!=null){
						var date = new Date(oData.time);
						elCell.innerHTML = formatDate(date,'yyy-MM-dd');
					}
				}else {
					elCell.innerHTML = oData;
				}
			}; 
			contentColumnHeaders =[
				{key:"checkContext",label:"<s:text name='certainLoss.prpLacciCheck.prpLacciCheckCheckContext'/>",width:"40em",sortable:true},<%--调查内容简要描述--%>
				{key:"certiType",label:"<s:text name="check.node" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--发起节点--%>
				{key:"checkObject",label:"<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckObject" />",width:"40em",sortable:true},<%--调查对象--%>
				{key:"checkNature",label:"<s:text name="certainLoss.prpLacciCheck.prpLacciCheckMethod" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--调查方式--%>
				{key:"checkDate",label:"<s:text name="check.investigatStartDate" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--调查起始日期--%>
				{key:"checkEndDate",label:"<s:text name="check.investigatEndDate" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--调查结束日期--%>
				{key:"checkerCode",label:"<s:text name="check.investigatorCode" />",width:"40em",sortable:true}<%--调查人代码--%>
			];
			executeQuery(1,10);
		}
		
		/*
		 *@description:可以批次切分活动组别结果集 
		 *@param  pageNo，pageSize
		 *@return  活动组别结果集
		 *@author 中科软
		*/
		function executeQuery(pageNo,pageSize,field){
			// reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
			//增加!!field判断，如果field为undefined，则!!field为false
			//增加!!field判断，如果field为undefined，则!!field为false
			if(!!field){
				field.disabled = true;
			}
			if(isNaN(parseInt(pageNo))){ 
				pageNo = 1;
			}
			if(isNaN(parseInt(pageSize))){
				pageSize = 10;
			} 
			var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);
			var myDataSource = new YAHOO.util.DataSource("${ctx }/check/lacciCheckBeforeQuery.do");
			myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
			myDataSource.connMethodPost = true; 
			myDataSource.responseSchema = {
			   resultsList: "data",
			   fields: ["checkContext","certiType","checkObject","checkNature","checkDate","checkEndDate","checkerCode"],
			   metaFields : {
					totalRecords : "totalRecords"
				}
			}; 
			myDataSource.subscribe("responseParseEvent",SINOSOFT.util.navigation);
			myDataSource.connMgr.setForm(fm);
			var initialRequest = "pageSize="+pageSize+"&pageNo="+pageNo;
			var myConfiges ={
				initialRequest:initialRequest,
				paginator:false
			};
			if (isFirstLoad==true){
				contentDataTable = new YAHOO.widget.DataTable("content",myColumnSet,myDataSource,myConfiges); 
				contentDataTable.initialRequest = initialRequest;
				isFirstLoad = false;
			}else{
				contentDataTable._oRecordSet = new YAHOO.widget.RecordSet();
				contentDataTable.initialRequest = initialRequest;
				contentDataTable.dataSource = myDataSource;
				contentDataTable.dataSource.sendRequest(contentDataTable.initialRequest, contentDataTable.onDataReturnReplaceRows, contentDataTable);
			}  
			document.getElementById("tableResullt").style.display="";
			//增加!!field判断，如果field为undefined，则!!field为false
			if(!!field){
			// reason:当次查询结束，按钮恢复
				field.disabled = false;
			}
		}
		YAHOO.util.Event.addListener(window,'load',init);
	</script>
</html>