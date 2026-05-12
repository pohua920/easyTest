<%--
****************************************************************************
* DESC       ：定损任务注销查询输入界面
* AUTHOR     ： lixiang	
* CREATEDATE ： 2005-10-19
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ page import = "java.util.Calendar"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<%@ page import = "com.sinosoft.sysframework.common.datatype.DateTime"%>

<%
  //得到本周周一与周日的日期  
  String strSunday =DateTime.current().toString();
  String strMonday = new DateTime(DateTime.current().addDay(-2),DateTime.YEAR_TO_DAY ).toString() ; 
%>

<html>
<head>
  <title><s:text name="title.claimBeforeEdit.titleName" /></title>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  <script type="text/javascript">
var isFirstLoad = true;
var contentDataTable;
var contentColumnHeaders;
YAHOO.namespace("query.container");

function init(){
	YAHOO.widget.DataTable.formatLink = function(elCell, oRecord, oColumn, oData) {
		if(oColumn.key =="keyIn"){
			var actorId = oRecord.getData("actorId");
			if(actorId==null){
				actorId = "";
			}
			elCell.innerHTML="<a href=\"${ctx}/pages/DAA/schedule/DAAScheduleCancelInput.jsp?registNo="+oRecord.getData("keyIn")+"&nodeType="+oRecord.getData("nodeType") +"&lossItemName="+oRecord.getData("lossItemName")+"&swfLogFlowID="+oRecord.getData("id.flowID")+"&swfLogLogNo="+oRecord.getData("id.logNo")+"&policyNo="+oRecord.getData("policyNo")+ "&actorId="+actorId+"\">"+ oData+"</a>";
		}else if(oColumn.key =="nodeType"){
			var checkflagstr="";
			if(oData=="propc"){
				checkflagstr="財產損失";
			}else if(oData=="wound"){
				checkflagstr="人傷";
			}else if(oData=="certa"){
				checkflagstr="車輛定損";
			}
			elCell.innerHTML = checkflagstr;
		}else if(oColumn.key =="handlerName"){
			var checkflagstr="";
			if(oData=="null" || oData== null){
				checkflagstr="";
			}
			elCell.innerHTML = checkflagstr;
		}else{
			elCell.innerHTML = oData;
		}
	}; 
	contentColumnHeaders =[
		               		{key:"keyIn",label:"<s:text name="schedule.reportRegistrateNo" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--报案登记号--%>
		               		{key:"nodeType",label:"<s:text name="schedule.typeFee" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--定损类型--%>
		               		{key:"lossItemName",label:"<s:text name="schedule.schedulObjectName" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--调度对象名称--%
		               		{key:"flowInTime",label:"<s:text name="schedule.surveyTtime" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--调度时间--%>
		               		{key:"beforeHandlerName",label:"<s:text name="check.schedulOpera" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--调度操作员--%>
		               		{key:"handlerName",label:"<s:text name="schedule.surveyPersonn" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}<%--查勘/定损人员--%>
		               		];
}
YAHOO.util.Event.addListener(window,'load',init);
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
function executeQuery(pageNo,pageSize,field){
	//init();
	if(validateForm(fm)){
	// reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
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
	var myDataSource = new YAHOO.util.DataSource("${ctx}/schedule/scheduleGetBackQuery.do");
	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.connMethodPost = true; 
	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["nodeType","lossItemName","beforeHandlerName","handlerName","flowInTime","keyIn",{key:"id.flowID"},{key:"id.logNo"},"policyNo","actorId"],
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
}
</script>
</head>



<body  onLoad="initPage();" class="yui-skin-sam">
		<form name="fm" method="post">
			<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
				<tr>
					<td class="formtitle" colspan="6">
						<s:text name="schedule.damageTaskEnd" />
						<%--定损任务注销 --%>
					</td>
				</tr>
				<tr>
					<td class='title' style="width: 10%">
						<s:text name="prompt.queRegist.RegistNo" />
						<%--报案号 --%>:
					</td>
					<td class='input' style="width: 25%">
						</select>
						<input type=text name="prpLcertainLossRegistNo" class="input" style="width: 95%">
					</td>
					<td class='title'>
						<s:text name="schedule.typeFee" />
						<%--定损类型 --%>:
					</td>
					<td class='input' colspan=3>
						<select name="nodeType" style="width: 150px">
							<option value="all" selected>
							</option>
							<option value="certa">
								<s:text name="schedule.fee" />
								<%--车辆定损 --%>
							</option>
							<option value="wound">
								<s:text name="regist.prpLregist.personLossFlag" />
								<%--人伤 --%>
							</option>
							<option value="propc">
								<s:text name="compensate.dubang.damageProperty" />
								<%--财产损失 --%>
							</option>
						</select>
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="hidden" name="editType" value="CANCELBEFOREQUERY">
				<span class="button" style="width: 10%"> <input type=button id="button" class='button' value="<s:text name="button.next.value" />" onclick="executeQuery(1,10,this);">
				</span>
			</div>
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
</html>