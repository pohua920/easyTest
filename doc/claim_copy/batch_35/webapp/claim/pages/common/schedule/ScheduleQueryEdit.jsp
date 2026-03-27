<%--
****************************************************************************
* DESC       ：调度通用查询输入界面
* AUTHOR     ： lixiang	
* CREATEDATE ： 2004-08-05
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
							zhangshi		20130201			修改*操作符为=*将like '%%'改为左%右匹配查询
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.claim.dto.custom.UserDto"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<%
	//得到本周周一与周日的日期
	DateTime dateTime = DateTime.current();
	String strSunday = dateTime.toString(DateTime.YEAR_TO_DAY);
	String strMonday = dateTime.addDay(-7).toString(
			DateTime.YEAR_TO_DAY);
	UserDto user = (UserDto) request.getSession().getAttribute("user");
%>
<html>
<head>
<title><s:text name="title.scheduleBeforeEdit.titleName" /></title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css" />
</head>
<body class="yui-skin-sam">
	<form name="fm" method="post">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td class="formtitle" colspan="4">
					<s:text name="schedule.schedulingInfoQuery" />
					<%--查询调度信息 --%>
				</td>
			</tr>
			<tr>
				<td width="8%" class='title' style="width: 15%">
					<s:text name="prpLbpmMain.mainNo" />
					<%--报案号 --%>
					:
				</td>
				<td width="25%" class='input' style="width: 20%">
					<select class=query name="registNoSign" style="width: 20%">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="registNo" class="input" style="width: 70%">
				</td>
				<td width="9%" class='title' style="width: 15%">
					<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFOperatorCode" />
					<%--调度员 :--%>
				</td>
				<td width="28%" class='input' >
					<input type=text name="handlerCode" class="codecode" style="width: 30%" title="分案員" value="" ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y');"
						onkeyup="code_CodeSelect(this, 'handerCode','0,1','Y');">
					<input type=text name="handlerName" readonly class="codecode" style="width: 60%" title="分案員" value="" ondblclick="code_CodeSelect(this, 'handerCode','-1,0','Y','N');"
						onkeyup="code_CodeSelect(this, 'handerCode','-1,0','Y','N');">
				</td>
				<td width="1%" rowspan=5 class='button' style="width: 10%">&nbsp;</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="check.schedulObject" />
					<%--调度对象 --%>
					:
				</td>
				<td class='input'>
					<input type=text class="codecode" name="scheduleObjectID" style="width: 30%" title="具體單位" value="" ondblclick="code_CodeSelect(this, 'prpdcompany','0,1','Y');"
						onkeyup="code_CodeSelect(this, 'prpdcompany','0,1','Y');">
					<input type=text class="codecode" name="scheduleObjectName" readonly title="具體單位" style="width: 60%" value="" ondblclick="code_CodeSelect(this, 'prpdcompany','-1,0','Y','N');"
						onkeyup="code_CodeSelect(this, 'prpdcompany','-1,0','Y','N');">
				</td>
				<td class='title'>
					<s:text name="db.prpCitem_car.licenseNo" />
					<%--车牌号码 --%>
					:
				</td>
				<td class='input'>
					<select class=query name="prpLscheduleItemLicenseNoSign" style="width: 20%">
						<option value="=">=</option>
						<%--<option value="*">*</option>--%>
					</select>
					<input name="prpLscheduleItemLicenseNo" class="input" readonly="readonly" onfocus="checkType(this);" style="width: 70%">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="schedule.schedulingType" />
					<%--调度类型 --%>
					:
				</td>
				<td class='input'>
					<select name="scheduleType" style="width: 91%" onchange="SetLicenseNo(this)">
						<option value="sched" selected>
							<s:text name="schedule.mentionedHereunder" />
							<%--查勘 --%>
						</option>
						<option value="schel">
							<s:text name="compensate.fee" />
							<%--定损 --%>
						</option>
					</select>
				</td>
				<td width="8%" class='title' style="width: 15%">
					<s:text name="db.prpCmain.insuredName" />
					<%--被保险人名称 --%>
					:
				</td>
				<td width="21%" class='input' style="width: 25%">
					<select class=query name="InsuredNameSign" style="width: 20%">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="InsuredName" class="input" style="width: 70%">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="manage.startTime" />
					<%--开始时间 --%>
					:
				</td>
				<td class='input'>
					<%--<input name="startDate" class="input" style="width: 120px"
							value=<%=strMonday%>>
							 
						<img src="/claim/images/bgcalendar.gif" align="middle"
							style='cursor: hand'
							onClick="TogglePopupCalendarWindow('document.fm.startDate', '<%=(new DateTime(DateTime.current().toString(),
							DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
							DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
							--%>
					<rc:rcDate name="startDate" style="width: 91%" value="<%=strMonday%>" />
				</td>
				<td class='title'>
					<s:text name="manage.endTime" />
					<%--结束时间 --%>
					:
				</td>
				<td class='input'>
					<%--<input name="endDate" class="input" style="width: 120px"
							value=<%=strSunday%>>
						<img src="/claim/images/bgcalendar.gif" align="middle"
							style='cursor: hand'
							onClick="TogglePopupCalendarWindow('document.fm.endDate', '<%=(new DateTime(DateTime.current().toString(),
							DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
							DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
							 --%>
					<rc:rcDate name="endDate" style="width: 91%" value="<%=strSunday%>" />
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="schedule.schedulingCondite" />
					:
					<%--调度状态 --%>
				</td>
				<td class='input' colspan="3">
					<input type=checkbox name="checkFlag0">
					<s:text name="common.status.newSchedule" />
					<%--新调度 --%>
					<%
						//<input type=checkbox name="checkFlag2" >正处理
					%>
					<input type=checkbox name="checkFlag4">
					<s:text name="common.status.submited" />
					<%--已提交 --%>
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<%--"="符号，必须精确查询。 --%>
					<br>
					<s:text name="prompt.schedule.query2" />
					<%--"=*"符号，前匹配後模糊的查询。 --%>
				</td>
			</tr>
		</table>
		<div align="center">
			<input type="hidden" name="editType" value="QUERY">
			<input type="hidden" name=comcode value="<%=user.getComCode()%>">
			<span class="button" style="width: 10%"> <input type=button id="button" class='button' value="<s:text name='button.query.value' />" onclick="executeQuery(1,10,this);">
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
<script type="text/javascript">
var isFirstLoad = true;
var contentDataTable;
var contentColumnHeaders;
YAHOO.namespace("query.container");

function init(){
	YAHOO.widget.DataTable.formatLink = function(elCell, oRecord, oColumn, oData) {
		if(oColumn.key =="id.registNo"){
			elCell.innerHTML="<a href=\"${ctx}/schedule/scheduleFinishQueryList.do?prpLscheduleMainWFRegistNo="
				+oRecord.getData("id.registNo")+"&prpLscheduleMainWFScheduleID="
				+oRecord.getData("id.scheduleID")+"&editType=SHOW"+"&scheduleType="
				+oRecord.getData("scheduleType") +"&riskCode="+oRecord.getData("riskCode")
				+ "\">"+ oData+"</a>";
		}else if(oColumn.key =="checkFlag"){
			var checkflagstr="";
			if(oData=="0"){
				checkflagstr="新分案";
			}else if(oData=="4"){
				checkflagstr="已提交";
			}
			elCell.innerHTML = checkflagstr;
		}else if(oColumn.key =="checkInfo"){
				if(oData==null){
					elCell.innerHTML = "";
				}else{
					elCell.innerHTML = oData;
				}
		}else if(oColumn.key =="nextHandlerName"){
				if(oData==null){
					elCell.innerHTML = "";
				}else{
					elCell.innerHTML = oData;
				}
		}else if(oColumn.key =="inputDate") {
				if(oData!=null){
					var date = new Date(oData.time);
					elCell.innerHTML = formatDate(date,'yyy-MM-dd');
				}
		}else{
			elCell.innerHTML = oData;
		}
	};
	var surveyOperator = "查勘操作員";
	if(fm.scheduleType.value=="schel"){
		surveyOperator = "定損操作員";
	}
	contentColumnHeaders =[
 		{key:"checkFlag",label:"<s:text name="db.prpDshortrate.validStatus" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--状态--%>
         {key:"id.registNo",label:"<s:text name="prompt.queRegist.RegistNo" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--报案号--%>
         {key:"checkInfo",label:"<s:text name="check.schedulInfo" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--调度信息--%>
         {key:"inputDate",label:"<s:text name="schedule.surveyTtime" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--调度时间--%>
         {key:"operatorName",label:"<s:text name="check.schedulOpera" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--调度操作员--%>
         {key:"nextHandlerName",label:surveyOperator,width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}<%--查勘操作员--%>
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
	var myDataSource = new YAHOO.util.DataSource("${ctx}/schedule/scheduleQuery.do");
	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.connMethodPost = true; 
	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["checkFlag",{key:"id.registNo"},"checkInfo","inputDate","operatorName",{key:"id.scheduleID"},"nextHandlerName","scheduleType","riskCode"],
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

function document.onkeydown(){ 
	if(event.keyCode==13){ 
	  document.getElementById("button").click(); 
	  return false; 
	} 
}  

function SetLicenseNo(field){
	if(field.value == "schel"){//定损
		fm.prpLscheduleItemLicenseNo.value = "";
		fm.prpLscheduleItemLicenseNo.readOnly = false;
	}else if(field.value == "sched"){//查勘
		fm.prpLscheduleItemLicenseNo.value = "";
		fm.prpLscheduleItemLicenseNo.readOnly = true;
	}
}

function checkType(field){
	var scheduleType = $(field).parents("table").find(":input[name='scheduleType']");
	if(scheduleType[0].value == "sched"){//查勘
		alert("只有分案類型為定損時，考慮牌照號碼的查詢");
	}
}
</script>
