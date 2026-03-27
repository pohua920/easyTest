<%--
****************************************************************************
* DESC       ：送审任务审核查询界面
* AUTHOR     ：中科软
* CREATEDATE ： 2013-03-14
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@page contentType="text/html; charset=GBK"%>
<%@include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<html>
<head>
<title><s:text name="title.sendUndwrtBeforeEdit.QueryingIformation" /></title>
<%--工作流查询信息 --%>
<%-- 公用函数 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body class="yui-skin-sam">
	<form name="fm" method="post" onsubmit="return validateForm(this);">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="sendUndwrt.AuditTasks" />
				</td>
			</tr>
			<%--待审核任务信息 --%>
			<tr>
				<td class='title'>
					<s:text name="sendUndwrt.OperationType" />
					：
				</td>
				<%--理赔操作类型 --%>
				<td class='input'>
					<select name="prpLSendUndwrtNodeType" style="width: 150px">
						<option value="">
							<s:text name="print.all" />
						</option>
						<%--全部 --%>
						<option value="check">
							<s:text name="check.mentHereunde" />
						</option>
						<%--查勘 --%>
						<option value="claim">
							<s:text name="check.record" />
						</option>
						<%--立案--%>
						<option value="compe">
							<s:text name="sendUndwrt.Adjusting" />
						</option>
						<%-- 理算--%>
					</select>
				</td>
				<td class='title'>
					<s:text name="sendUndwrt.OrganizationCode" />
					：
				</td>
				<%-- 理赔组织机构代码--%>
				<td class='input'>
					<input type=text name="prpLSendUndwrtComCode" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="sendUndwrt.StaffCode" />
					：
				</td>
				<%--送审人员代码 --%>
				<td class='input'>
					<input type=text name="prpLSendUndwrtOperatorCode" class="query">
				</td>
				<td class='title'>
					<s:text name="sendUndwrt.StaffName" />
					:
				</td>
				<%--送审人员名称 --%>
				<td class='input'>
					<input type=text name="prpLSendUndwrtOperatorName" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="sendUndwrt.BusinessNumber" />
					：
				</td>
				<%-- 业务号--%>
				<td class='input'>
					<input type=text name="prpLSendUndwrtBusinessNo" class="query">
				</td>
				<td class='title'></td>
				<td class='input'></td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="executeQuery(1,10);">
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
YAHOO.namespace("query.container");
/*
 *@description:初始化查询结果页面
 *@param varSigns 无
 *@return boolean 活动组别结果集 
 *@author 中科软
*/
function init(){
	YAHOO.widget.DataTable.formatLink = function(elCell, oRecord, oColumn, oData) {
		if(oColumn.key =="id.businessNo"){
			var urlStr = "";
			if(oRecord.getData("nodeType") == "claim") {
				if(oRecord.getData("swfLog.nodeStatus") == "0") {
					urlStr = "claimBeforeEdit.do?RegistNo="+oRecord.getData("swfLog.keyIn");
				} else {
					urlStr = "claimFinishQueryList.do?prpLclaimClaimNo="+oRecord.getData("swfLog.keyOut");
				}
			} else if(oRecord.getData("nodeType") == "check") {
				if(oRecord.getData("swfLog.nodeStatus") == "0") {
					urlStr = "check/checkBeforeEdit.do?RegistNo="+oRecord.getData("swfLog.keyIn");
				} else {
					urlStr = "check/checkFinishQueryList.do?prpLcheckCheckNo="+oRecord.getData("swfLog.keyIn");
				}
			} else if(oRecord.getData("nodeType") == "compe") {
				urlStr = "compensate/compensateBeforeEdit.do?ClaimNo="+oRecord.getData("swfLog.keyIn");
			} else if(oRecord.getData("nodeType") == "compp") {
				urlStr = "compensate/compensateFinishQueryList.do?prpLcompensateCompensateNo="+oRecord.getData("swfLog.businessNo");
			}
			if(oRecord.getData("swfLog.nodeStatus") == "0") {
				urlStr = urlStr + "&editType=ADD";
			} else {
				urlStr = urlStr + "&editType=EDIT";
			}
			urlStr = urlStr +"&swfLogFlowID="+oRecord.getData("swfLog.id.flowID")+
				"&swfLogLogNo="+oRecord.getData("swfLog.id.logNo")+"&status="+oRecord.getData("swfLog.nodeStatus")+
				"&riskCode="+oRecord.getData("swfLog.riskCode")+"&nodeType="+oRecord.getData("swfLog.nodeType")+
				"&businessNo="+oRecord.getData("swfLog.businessNo")+"&keyIn="+oRecord.getData("swfLog.keyIn")+
				"&policyNo="+oRecord.getData("swfLog.policyNo")+"&modelNo="+oRecord.getData("swfLog.modelNo")+
				"&nodeNo="+oRecord.getData("swfLog.nodeNo")+"&dfFlag="+oRecord.getData("swfLog.dfFlag");
			elCell.innerHTML = "<a href=\"${ctx}/"+urlStr + "\">"+ oData+"</a>";
		}else if(oColumn.key =="nodeType") {
			var nodeName = ""
			if(oRecord.getData("nodeType") == "claim"){
				nodeName="立案";
			}else if(oRecord.getData("nodeType") == "check"){
				nodeName="查勘";
			}else if(oRecord.getData("nodeType") == "compe"){
				nodeName="理算";
			}else if(oRecord.getData("nodeType") == "compp"){
				nodeName="理算";
			}
			elCell.innerHTML = nodeName;
		}else if(oColumn.key =="inputDate") {
			if(oData!=null){
				var date = new Date(oData.time);
				elCell.innerHTML = formatDate(date,'yyy-MM-dd');
			}
		}else{
			elCell.innerHTML = oData;
		}
	};
	contentColumnHeaders =[
		{key:"id.businessNo",label:"<s:text name="sendUndwrt.BusinessNumber" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--业务号--%>
		{key:"swfLog.insuredName",label:"<s:text name="db.prpCmain.insuredName" />",width:"40em",sortable:true},<%--被保险人名称--%>
		{key:"nodeType",label:"<s:text name="sendUndwrt.NodeName" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--送审节点名称--%>
		{key:"operatorName",label:"<s:text name="sendUndwrt.StaffName" />",width:"40em",sortable:true},<%--送审人员名称--%>
		{key:"inputDate",label:"<s:text name="sendUndwrt.TrialTime" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}<%--送审时间--%>
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
	var myDataSource = new YAHOO.util.DataSource("${ctx}/sendUndwrt/sendUndwrtQuery.do?actionType=Query");
	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.connMethodPost = true;
	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["nodeType", {key:"id.businessNo"},{key:"swfLog.insuredName"},{key:"swfLog.nodeStatus"},{key:"swfLog.keyIn"},{key:"swfLog.keyOut"},{key:"swfLog.keyIn"},{key:"swfLog.businessNo"},{key:"swfLog.nodeStatus"},{key:"swfLog.id.flowID"},{key:"swfLog.id.logNo"},{key:"swfLog.nodeStatus"},{key:"swfLog.policyNo"},{key:"swfLog.modelNo"},{key:"swfLog.nodeNo"},{key:"swfLog.dfFlag"},{key:"swfLog.riskCode"},{key:"swfLog.nodeType"},{key:"swfLog.riskCode"},"operatorName", "inputDate"],
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