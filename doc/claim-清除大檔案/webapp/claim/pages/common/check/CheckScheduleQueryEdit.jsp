<%--
****************************************************************************
* DESC       ：查勘调度查询输入界面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-07-13
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import = "java.util.Calendar"%>
<%@ page import="com.sinosoft.claim.dto.custom.UserDto" %>
<%@ page import = "com.sinosoft.sysframework.common.datatype.DateTime"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<%
  //得到本周周一与周日的日期  
  String strSunday =DateTime.current().toString(DateTime.YEAR_TO_DAY);
  String strMonday = new DateTime(DateTime.current().addMonth(-1),DateTime.YEAR_TO_DAY ).toString() ;
  UserDto user   = (UserDto)request.getSession().getAttribute("user"); 
%>
<script>
    function document.onkeydown() 
    { 
    if(event.keyCode==13) 
    { 
      document.getElementById("button").click(); 
      return false; 
    } 
    }  
</script>
<html>
<head>
<title><s:text name="title.checkBeforeEdit.queryCheck" /></title>
<%--查询查勘信息--%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script type="text/javascript">
  var isFirstLoad = true;
  var contentDataTable;
  var contentColumnHeaders;
  YAHOO.namespace("query.container");

  function init() {
  	YAHOO.widget.DataTable.formatLink = function (elCell, oRecord, oColumn, oData) {
  		if (oColumn.key == "registNo") {
  			if (oRecord.getData("nodeStatus") == "0") {
  				elCell.innerHTML = "<a href=\"javascript:alert('該任務目前還沒有可以查看的" + oRecord.getData("nodeName") + "信息！');\">" + oData + "</a>";
  			} else {
  				var flowStr = "&swfLogFlowID=" + oRecord.getData("id.flowID") + "&swfLogLogNo=" + oRecord.getData("id.logNo") + "&status=" + oRecord.getData("nodeStatus") + "&riskCode=" + oRecord.getData("riskCode") + "&editType=SHOW" + "&nodeType=" + oRecord.getData("nodeType") + "&businessNo=" + oRecord.getData("businessNo") + "&policyNo=" + oRecord.getData("policyNo") + "&modelNo=" + oRecord.getData("modelNo") + "&nodeNo=" + oRecord.getData("nodeNo");
  				if (oRecord.getData("nodeType") == "check") {
  					elCell.innerHTML = "<a href=\"${ctx}/schedule/checkFinishQueryList.do?prpLcheckCheckNo=" + oRecord.getData("keyIn") + "&lossItemCode=" + oRecord.getData("lossItemCode") + "&lossItemName=" + oRecord.getData("lossItemName") + "&insureCarFlag=" + oRecord.getData("insureCarFlag") + "&commiFlag=0" + flowStr + "\">" + oData + "</a>";
  				} else {
  					elCell.innerHTML = "<a href=\"${ctx}/schedule/certainLossFinishQueryList.do?prpLverifyLossRegistNo=" + oRecord.getData("keyIn") + "&lossTypeFlag=" + oRecord.getData("typeFlag") + "&insureCarFlag=" + oRecord.getData("insureCarFlag") + "&lossItemCode=" + oRecord.getData("lossItemCode") + "&lossItemName=" + oRecord.getData("lossItemName") + "&commiFlag=0" + flowStr + "\">" + oData + "</a>";
  				}
  			}
  		} else if (oColumn.key == "nodeStatus") {
  			var checkflagstr = "";
  			if (oData == "0") {
  				checkflagstr = "新分案";
  			} else if (oData == "2") {
  				checkflagstr = "正處理";
  			} else if (oData == "4") {
  				checkflagstr = "已提交";
  			}
  			elCell.innerHTML = checkflagstr;
  		} else if (oColumn.key == "handlerName") {
  			if (oData == null) {
  				elCell.innerHTML = "";
  			} else {
  				elCell.innerHTML = oData;
  			}
  		} else if (oColumn.key == "lossItemName") {
  			if (oData == null) {
  				elCell.innerHTML = "";
  			} else {
  				elCell.innerHTML = oData;
  			}
  		} else if (oColumn.key == "flowInTime") {
  			//alert(oData);
  			if (oData != null) {
  				elCell.innerHTML = formatDate(oData, 'yyy-MM-dd');
  			}
  		} else {
  			elCell.innerHTML = oData;
  		}
  	};
	contentColumnHeaders =[
		               		{key:"nodeStatus",label:"<s:text name="schedule.rrocesseStatus" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--处理状态--%>
	                        {key:"registNo",label:"<s:text name="prompt.queRegist.RegistNo" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--报案号--%>
	                        {key:"flowInTime",label:"<s:text name="schedule.surveyTtime" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--调度时间--%>
	                        {key:"beforeHandlerName",label:"<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFOperatorCode" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--调度员--%>
	                        {key:"handlerName",label:"<s:text name="certainLoss.prpLscheduleMainWF.Handler" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--查勘人员--%>
	                        {key:"lossItemName",label:"<s:text name="schedule.schedulObjectName" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}<%--调度对象名称--%>
		               		];
  }
  YAHOO.util.Event.addListener(window, 'load', init);

  function showDlg(title, url) {
  	submitDlg = new YAHOO.widget.Panel("submitDlg", {
  		iframe: true,
  		visible: false,
  		width: 780,
  		height: 463,
  		underlay: "shadow",
  		constraintoviewport: true,
  		fixedcenter: true,
  		modal: true,
  		zIndex: 320
  	});
  	submitDlg.setHeader(title);
  	submitDlg.setBody("<iframe name='submitFrame' src='javascript:false;'  frameborder='0' style='margin:0; padding:0; width:98%; height: 97%' align='left'></iframe>");
  	submitDlg.render(document.body);
  	submitDlg.show();
  	var oldTarget = fm.target;
  	var oldAction = fm.action;
  	fm.target = "submitFrame";
  	fm.action = contextRootPath + url; // 链接
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
		var myDataSource = new YAHOO.util.DataSource("${ctx}/schedule/scheduleCheckQuery.do");
		myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.connMethodPost = true; 
		myDataSource.responseSchema = {
		   resultsList: "data",
		            fields: [{key:"id.flowID"},{key:"id.logNo"},"nodeStatus","registNo","flowInTime","handlerName","beforeHandlerName","lossItemName","riskCode","nodeType","businessNo","policyNo","modelNo","nodeNo","keyIn","lossItemCode","insureCarFlag","typeFlag","nodeName"],
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
<body class="yui-skin-sam">
	<form name="fm" method="post">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="title.checkBeforeEdit.titleName" />
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.registNo" />:
				</td>
				<%--报案号--%>
				<td class='input' style="width: 20%">
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="registNo" class="input" style="width: 50%">
				</td>
				<td class='title' style="">
					<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFOperatorCode" />
				</td>
				<%--调度员:--%>
				<td class='input'>
					<input type=text name="handlerCode" class="codecode" style="width: 30%" title="分案員" value="" ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y');"
						onkeyup="code_CodeSelect(this, 'handerCode','0,1','Y');">
					<input type=text name="handlerName" readonly class="codecode" style="width: 40%" title="分案員" value="" ondblclick="code_CodeSelect(this, 'handerCode','-1,0','Y','N');"
						onkeyup="code_CodeSelect(this, 'handerCode','-1,0','Y','N');">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="manage.startTime" />:
				</td>
				<%--开始时间--%>
				<td class='input'>
					<%--<input name="startDate" class="input" value=<%=strMonday%> >--%>
					<rc:rcDate name="startDate" value="<%=strMonday%>" style="width: 71%"/>
				</td>
				<td class='title'>
					<s:text name="manage.endTime" />:
				</td>
				<%--结束时间--%>
				<td class='input'>
					<%--<input  name="endDate" class="input" value = <%=strSunday%>>  --%>
					<rc:rcDate name="endDate" value="<%=strSunday%>" style="width: 71%"/>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="prpLcheck.checkUser" />:
				</td>
				<%--查勘人--%>
				<td class='input'>
					<input type=text name="NhandlerCode" class="codecode" title="查勘人" value="" ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y');" onkeyup="code_CodeSelect(this, 'handerCode','0,1','Y');"
						style="width: 30%">
					<input type=text name="NhandlerName" class="codename" title="查勘人" value="" style="width: 40%" ondblclick="code_CodeSelect(this, 'handerCode','-1,0','Y','N');"
						onkeyup="code_CodeSelect(this, 'handerCode','-1,0','Y','N');">
				</td>
				<td class='title'>
					<s:text name="certainLoss.prpLscheduleMainWF.caseState" />
				</td>
				<%--案件状态:--%>
				<td class='input'>
					<input type=checkbox name="checkFlag0">
					<s:text name="schedule.noMentioned" />
					<%--未查勘--%>
					<input type=checkbox name="checkFlag2">
					<s:text name="schedule.noMentioneding" />
					<%--正在查勘--%>
					<input type=checkbox name="checkFlag4">
					<s:text name="schedule.Mentioned" />
					<%--已查勘--%>
				</td>
			</tr>
		</table>
		<div align="center">
			<span class="button" style="width: 10%"> <input type=button id="button" class='button' value="<s:text name='button.query.value' />" onclick="executeQuery(1,10,this);">
			</span>
			<input type="hidden" name="editType" value="QUERYCHECK">
			<input type="hidden" name="nodeType" value="<%= request.getParameter("nodeType") %>">
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