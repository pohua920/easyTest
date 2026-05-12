<%--
****************************************************************************
* DESC       ：实体资料调阅查询页面
* AUTHOR     ： 中科软
* CREATEDATE ： 2013-03-11
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<title><s:text name="title.claimBeforeEdit.titleName" /></title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="${ctx}/common/js/date/WdatePicker.js"></script>
<script src="${ctx}/common/js/selectClassCode.js"></script>
</head>
<body class="yui-skin-sam">
	<form name="fm" method="post">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<c:if test="${editType=='query'}">
					<td colspan="4" class="formtitle">
						<s:text name="title.archive.entityDataReadQuery" />
					</td>
					<%--实体资料调阅查询 --%>
				</c:if>
				<c:if test="${editType=='apply'}">
					<td colspan="4" class="formtitle">
						<s:text name="archive.entityDataReadApplyQuery" />
					</td>
					<%--实体资料调阅申请前查询 --%>
				</c:if>
			</tr>
			<tr>
				<td class="title">
					<s:text name="check.claimNum" />：
				</td>
				<%--赔案号 --%>
				<td class="input">
					<select class="tag" name="claimNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="claimNo" class="query">
				</td>
				<td class="title">
					<s:text name="db.prpLregist.policyNo" />：
				</td>
				<td class="input">
					<select class="tag" name="policyNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="policyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="archive.riskClass" />
				</td>
				<%--险类 --%>
				<td class="input">
					<select class="tag" name="classNoSign">
						<option value="=">=</option>
						<!--  <option value="=*">=*</option> -->
					</select>
					<input type="text" name="strClassCode" class="query" value="">
					<!--<input type="button" name="classCodeSelect" value="..." onclick="selectPublicCheckbox('selectClassCode')">-->
				</td>
				<td class="title">
					<s:text name="db.prpCmain.insuredName" />：
				</td>
				<%--被保险人名称 --%>
				<td class="input">
					<select class="tag" name="insuredNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="insuredName" class="query">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="currentTime" />:
				</td>
				<%--时间--%>
				<td colspan='2' class="input">
					<s:text name="archive.startTime" />
					<%-- <input class="Wdate" type="text" id="startDate" name="startDate" onFocus="WdatePicker({isShowClear:false,readOnly:true})"/>--%>
					<%--起始时间 --%>
					<rc:rcDate name="startDate" id="startDate" defaultValue="-1" style="width: 14%"/>
					<s:text name="archive.endTime" />
					<%-- <input class="Wdate" type="text" id="endDate" name="endDate" onFocus="WdatePicker({isShowClear:false,readOnly:true})"/>--%>
					<%--终止时间--%>
					<rc:rcDate name="endDate" id="endDate" defaultValue="0" style="width: 14.5%"/>
				</td>
				<td class="title"></td>
				<td class="title"></td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<br>
					<%--"="符号，必须精确查询。--%>
					<s:text name="prompt.schedule.query2" />
					<%--"=*"符号，前匹配後模糊的查询。 --%>
				</td>
			</tr>
		</table>
		<table width="100%">
			<tr>
				<td align="center">
					<input type="button" id="button" class='button' value="<s:text name='button.query.value' />" onclick="executeQuery(1,10);">
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
		<input type="hidden" name="editType" value="${parameters.editType[0] }">
	</form>
</body>
<script type="text/javascript">
var isFirstLoad = true;
var contentDataTable;
var contentColumnHeaders;
YAHOO.namespace("query.container");
<c:if test="${parameters.editType[0]!='query'}">
/*
 *@description:初始化查询结果页面
 *@param varSigns 无
 *@return boolean 活动组别结果集 
 *@author 中科软
*/
function init(){
	YAHOO.widget.DataTable.formatLink = function(elCell, oRecord, oColumn, oData) {
	if(oColumn.key =="claimNo"){
		elCell.innerHTML="<a href=\"${ctx}/archive/archiveFinishQueryList.do?claimNo="
			+oData+"&editType=applyFinish"
			+ "\">"+ oData+"</a>";
		}else if(oColumn.key =="endCaseDate"||oColumn.key =="applyDate"||oColumn.key =="estimateReturnDate") {
			if(oData!=null){
				var date = new Date(oData.time);
				elCell.innerHTML = formatDate(date,'yyy-MM-dd');
			}
		}else if(oColumn.key=="oper"){
            elCell.innerHTML="<a href=\"${ctx}/archive/archiveFinishQueryList.do?claimNo="
			+oData+"&editType=applyFinish"
			+ "\">"
			+ "<img name=buttonDistribute  src='/claim/images/butDeal.gif' border='0' hspace='5' alt='选择处理'></a>"
			+"</a>";
		}else{
			elCell.innerHTML = oData;
		}
	};
	contentColumnHeaders =[
		{key:"claimNo",label:"<s:text name="check.claimNum" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--赔案号--%>
		{key:"policyNo",label:"<s:text name="prompt.queRegist.PolicyNo" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--保单号--%>
		{key:"insuredName",label:"<s:text name="db.prpLregist.insuredName" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--被保险人名称--%>
		{key:"endCaseDate",label:"<s:text name="db.prpLclaim.endCaseDate" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--结案日期--%>
		{key:"sumDutyPaid",label:"<s:text name="compensate.compel.paymentAmount" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--赔款金额--%>
		{key:"oper",label:"<s:text name="certify.operate" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}<%--操作--%>
		];
}

/*
 *@description:可以批次切分活动组别结果集 
 *@param  pageNo，pageSize
 *@return  活动组别结果集
 *@author 中科软
*/
function executeQuery(pageNo,pageSize){
	if(isNaN(parseInt(pageNo))){
		pageNo = 1;
	}
	if(isNaN(parseInt(pageSize))){
		pageSize = 10;
	}
	var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);
	var myDataSource = new YAHOO.util.DataSource("${ctx}/archive/archiveQuery.do");
	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.connMethodPost = true;
	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["claimNo","policyNo","insuredName","endCaseDate","sumDutyPaid"],
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
}
</c:if>
<c:if test="${parameters.editType[0]=='query'}">
/*
 *@description:初始化查询结果页面
 *@param varSigns 无
 *@return boolean 活动组别结果集 
 *@author 中科软
*/
function init(){
	YAHOO.widget.DataTable.formatLink = function(elCell, oRecord, oColumn, oData) {
		if(oColumn.key =="endCaseDate"||oColumn.key =="applyDate"||oColumn.key =="estimateReturnDate") {
			if(oData!=null){
				var date = new Date(oData.time);
				elCell.innerHTML = formatDate(date,'yyy-MM-dd');
			}
		}else if(oColumn.key =="status"){
			if(oData=="1"){
				elCell.innerHTML = "已歸檔";
			}else if(oData=="2"){
				elCell.innerHTML = "調閱審核中";
			}else if(oData=="3"){
				elCell.innerHTML = "調閱中";
			}else if(oData=="4"){
				elCell.innerHTML = "理賠處理中";
			}else{
				elCell.innerHTML = "";
			}
		}else{
			elCell.innerHTML = oData;
		}
	};
	contentColumnHeaders =[
		{key:"claimNo",label:"<s:text name='check.claimNum' />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--赔案号--%>
		{key:"insuredName",label:"<s:text name='db.prpLregist.insuredName' />",width:"40em",sortable:true},<%--被保险人名称--%>
		{key:"endCaseDate",label:"<s:text name='db.prpLclaim.endCaseDate' />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--结案日期--%>
		{key:"applicantName",label:"調閱申請人姓名",width:"40em",sortable:true},<%--调阅申请人姓名--%>
		{key:"applyDate",label:"申請調閱時間",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--调阅调阅时间--%>
		{key:"estimateReturnDate",label:"預計歸檔時間",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%-- 预计归档时间--%>
		{key:"status",label:"資料狀態",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}<%-- 资料状态--%>
		];
}

/*
 *@description:可以批次切分活动组别结果集 
 *@param  pageNo，pageSize
 *@return  活动组别结果集
 *@author 中科软
*/
function executeQuery(pageNo,pageSize){
	if(isNaN(parseInt(pageNo))){ 
		pageNo = 1;
	}
	if(isNaN(parseInt(pageSize))){
		pageSize = 10;
	}
	var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);
	var myDataSource = new YAHOO.util.DataSource("${ctx}/archive/archiveQuery.do");
	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.connMethodPost = true; 
	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["claimNo","insuredName","endCaseDate","applicantName","applyDate","estimateReturnDate","status"],
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
}
</c:if>
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