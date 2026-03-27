<%--
****************************************************************************
* DESC       ：立案除外历史查询界面
* AUTHOR     ： 中科软
* CREATEDATE ： 2013-03-06
* MODIFYLIST ：   Name       Date           Reason/Contents
*             --------------------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<html>
<head>
<title><s:text name="title.excludeClaimBeforeEdit.ExceptHistory" /></title>
<%-- 立案除外历史记录 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="${ctx}/pages/excludeClaim/js/ExcludeClaimEdit.js"></script>
</head>
<body class="yui-skin-sam">
	<form name="fm" method="post">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle"><s:text name="excludeClaim.QueryHistory" /></td>
			</tr>
			<%-- 查询立案除外历史任务 --%>
			<tr>
				<td class='title'><s:text name="prpLregist.registNo" />：</td>
				<%-- 报案号 --%>
				<td class='input'><input type=text name="RegistNo" class="query" onchange="clickable()" onkeypress="KeyDown('history')"></td>
				<td class='title'><s:text name="db.prpLarrearageadd.policyNo" />:</td>
				<%-- 保单号码 --%>
				<td class='input'><input type=text name="PolicyNo" class="query" onchange="clickable()" onkeypress="KeyDown('history')"></td>
			</tr>
			<tr>
				<td class='title'><s:text name="endcase.insuranceAgent" />：</td>
				<%-- 承保机构 --%>
				<td class='input'><input type=text name="ComCode" class="query" onchange="clickable()" onkeypress="KeyDown('history')"></td>
				<td class='title'><s:text name="regist.prpLregist.riskCodeName" />：</td>
				<%--险种  --%>
				<td class='input'><input type=text name="RiskCode" class="query" onchange="clickable()" onkeypress="KeyDown('history')"></td>
			</tr>
			<tr>
				<td class='title'><s:text name="excludeClaim.ExceptTime" />：</td>
				<%-- 除外时间 --%>
				<td class='input'><s:text name="prompt.from" />
					<%-- 从 --%> <rc:rcDate name="InputStartDate" style="width:100px" /> <s:text name="prompt.to" />: <%-- 至 --%> <rc:rcDate name="InputEndDate" style="width:100px" /></td>
				<td class='title'><s:text name="db.prpLclaim.claimNo" />：</td>
				<td class='input'><input type=text name="ClaimNo" class="query" onchange="clickable()" onkeypress="KeyDown('history')">
				<!-- 立案号 --></td>
			</tr>
		</table>
		<TABLE id="buttonTable" class="common" cellpadding="3" cellspacing="1">
			<TR class="center">
				<td><input type="button" class="button" name="queryButton" value="<s:text name='button.query.value'/>" onClick="executeQuery(1,10,this);">
				<%-- 查询 --%></TD>
			</TR>
			<TR>
				<td></TD>
			</TR>
		</TABLE>
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
	</form>
</body>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/common/js/prototype.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">
var isFirstLoad = true;
var contentDataTable;
var contentColumnHeaders;
var count = 1;
YAHOO.namespace("query.container");
/*
 *@description:初始化查询结果页面
 *@param varSigns 无
 *@return boolean 活动组别结果集 
 *@author 中科软
*/
function init(){
	YAHOO.widget.DataTable.formatLink = function(elCell, oRecord, oColumn, oData) {
        if(oColumn.key =="inputDate") {
			if(oData!=null){
				var date = new Date(oData.time);
				elCell.innerHTML = formatDate(date,'yyy-MM-dd');
			}
		}else if(oColumn.key =="serialNo"){
		    elCell.innerHTML = count++;
		}else{
			elCell.innerHTML = oData;
		}
	}; 
	contentColumnHeaders =[
	    {key:"serialNo",label:"<s:text name="db.prpDrate.serialNo" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--序号--%>
		{key:"registNo",label:"<s:text name="prompt.queRegist.RegistNo" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--报案号--%>
		{key:"policyNo",label:"<s:text name="db.prpCmain.policyNo" />",width:"40em",sortable:true},<%--保单号码--%>
		{key:"operatorCode",label:"<s:text name="excludeClaim.ExceptNumber" />",width:"40em",sortable:true},<%--除外人员工号--%>
		{key:"operatorname",label:"<s:text name="excludeClaim.ExceptNames" />",width:"40em",sortable:true},<%--除外人员名称--%>
		{key:"inputDate",label:"<s:text name="excludeClaim.ExceptTime" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--除外时间--%>
		{key:"excludereason",label:"<s:text name="excludeClaim.ExceptReason" />",width:"40em",sortable:true}<%--除外原因--%>
		];
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
	if(!!field){
		field.disabled = true;
	}
    count=1;
	if(isNaN(parseInt(pageNo))){
		pageNo = 1;
	}
	if(isNaN(parseInt(pageSize))){
		pageSize = 10;
	}
	var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);
	var myDataSource = new YAHOO.util.DataSource("${ctx}/claim/excludeClaim.do?actionType=historyQuery");
	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.connMethodPost = true; 
	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["registNo", "policyNo", "operatorCode", "operatorname", "inputDate", "excludereason"],
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

//init on load
YAHOO.util.Event.addListener(window,'load',init);
/*
 *@description:弹出页面
 *@param  title，url
 *@return  
 *@author 中科软
*/
function showDlg(title,url){
	submitDlg = new YAHOO.widget.Panel("submitDlg",{iframe:true, visible:false, width:780, height:463, underlay:"shadow", constraintoviewport:true, fixedcenter:true, modal:true, zIndex:320});
	submitDlg.setHeader(title);
	submitDlg.setBody("<iframe name='submitFrame' src='javascript:false;'  frameborder='0' style='margin:0; padding:0; width:98%; height: 97%' align='left'></iframe>");
	submitDlg.render(document.body);
	submitDlg.show();
	var oldTarget = fm.target;
	var oldAction = fm.action;
	fm.target="submitFrame";
	fm.action = contextRootPath+url; // 链接
	fm.submit();
	fm.target = oldTarget;
	fm.action = oldAction;
}
</script>
</html>