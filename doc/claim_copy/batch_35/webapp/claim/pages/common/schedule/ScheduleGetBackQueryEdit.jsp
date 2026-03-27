<%--
****************************************************************************
* DESC       ：调度改派查询输入界面
* AUTHOR     ： lixiang	
* CREATEDATE ： 2004-08-05
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
								zhangshi		20130201			修改*操作符为=*将like '%%'改为左%右匹配查询
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ page import = "java.util.Calendar"%>
<%@ page import = "com.sinosoft.sysframework.common.datatype.DateTime"%>
<%@ include file="/common/taglibs.jsp"%>  
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<%
  //得到本周周一与周日的日期  
  String strSunday =DateTime.current().toString(DateTime.YEAR_TO_DAY);
	//String strSunday = new DateTime(DateTime.current(),DateTime.YEAR_TO_DAY ).toString() ;
  String strMonday = new DateTime(DateTime.current().addDay(-2),DateTime.YEAR_TO_DAY ).toString() ; 
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
<title><s:text name="title.claimBeforeEdit.titleName" /></title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script type="text/javascript">
  var isFirstLoad = true;
  var contentDataTable;
  var contentColumnHeaders;
  YAHOO.namespace("query.container");

  function init(){
  	YAHOO.widget.DataTable.formatLink = function(elCell, oRecord, oColumn, oData) {
  		var scheduleType="schel";
  		if(oColumn.key =="handlerName"){
  			if(oData==null){
  				elCell.innerHTML = "";
  				}
  			else{
  				elCell.innerHTML = oData;
  				}
  			}
  		else if(oColumn.key =="keyIn"){
  			elCell.innerHTML="<a href=\"${ctx}/schedule/schedulegetBackEdit.do?prpLscheduleMainWFRegistNo="
  				+oRecord.getData("keyIn")+"&editType=GETBACKEDIT&nodeType="+oRecord.getData("nodeType")
  				+"&lossItemCode="+oRecord.getData("lossItemCode")
  				+"&scheduleType="+scheduleType
  				+"&swfLogFlowID="+oRecord.getData("id.flowID")
  				+"&swfLogLogNo="+oRecord.getData("id.logNo")
  				+"&policyNo="+oRecord.getData("policyNo")
  				+"&handleDept="+oRecord.getData("handleDept")
  				+"&riskCode="+oRecord.getData("riskCode")
  				+ "\">"+ oData+"</a>";
  			}
  		else if(oColumn.key =="flowInTime"){
  			if(oColumn.key == null){
  				elCell.innerHTML = "";
  			}
  			else{
  				elCell.innerHTML = formatDate(oData,"yyy-MM-dd");
  				}
  		}
  		else{
  				elCell.innerHTML = oData;
  				}
  	}; 
  	contentColumnHeaders =[
  	               	  {key:"keyIn",label:"<s:text name="schedule.reportRegistrateNo" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--报案登记号--%>
					  {key:"flowInTime",label:"<s:text name="schedule.surveyTtime" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--调度时间--%>
					  {key:"beforeHandlerName",label:"<s:text name="check.schedulOpera" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--调度操作员--%>
					  {key:"handlerName",label:"<s:text name="schedule.surveyPersonn" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--查勘/定损人员--%>
					  {key:"lossItemName",label:"<s:text name="schedule.schedulObjectName" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}<%--调度对象名称--%>
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
  	   fields: [{key:"id.flowID"},{key:"id.logNo"},"nodeType","lossItemCode","policyNo","handleDept","riskCode","keyIn","flowInTime","beforeHandlerName","handlerName","lossItemName"],
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
</script>
</head>
<body class="yui-skin-sam">
	<form name="fm" method="post">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td class="formtitle" colspan="4">
					<s:text name="title.scheduleBeforeEdit.titleName" />
				</td>
			</tr>
			<tr>
				<td class='title' style="width: 10%">
					<s:text name="prpLbpmMain.mainNo" />
					<%--报案号 --%>
					:
				</td>
				<td class='input' style="width: 25%">
					<select class=query name="registNoSign" style="width: 20%"">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="registNo" class="input" style="width: 50%">
				</td>
				<td class='title' style="width: 10%">
					<s:text name="certainLoss.prpLcheck.lossItemName" />
					<%--车牌号码 --%>
				</td>
				<td class='input' style="width: 25%">
					<select class=query name="licenseNoSign" style="width: 20%"">
						<option value="=">=</option>
						<%--<option value="*">*</option>--%>
					</select>
					<input type=text name="licenseNo" class="input" style="width: 50%">
				</td>
				<td class='button' style="width: 20%" rowspan=4>&nbsp;</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFOperatorCode" />
					<%--调度员 --%>
				</td>
				<td class='input'>
					<input type=text name="handlerCode" class="codecode" style="width: 35%" title="分案員" value="" ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y','Y');"
						onkeyup="code_CodeSelect(this, 'handerCode','0,1','Y','Y');">
					<input type=text name="handlerName" class="codecode" readonly style="width: 35%" title="分案員" value="" ondblclick="code_CodeSelect(this, 'handerCode','-1,0','Y','N');"
						onkeyup="code_CodeSelect(this, 'handerCode','-1,0','Y','N');">
				</td>
				<td class='title'>
					<s:text name="schedule.surveyPersonn" />
					<%--查勘/定损人 --%>
					:
				</td>
				<td class='input'>
					<input type=text name="NhandlerCode" class="codecode" style="width: 35%" title="查勘/定損人員" value="" ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y','Y');"
						onkeyup="code_CodeSelect(this, 'handerCode','0,1','Y','Y');">
					<input type=text name="NhandlerName" class="codecode" readonly style="width: 35%" title="查勘/定損人員" value="" ondblclick="code_CodeSelect(this, 'handerCode','-1,0','Y','N');"
						onkeyup="code_CodeSelect(this, 'handerCode','-1,0','Y','N');">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="manage.startTime" />
					<%--开始时间 --%>
					:
				</td>
				<td class='input'>
					<%--<input name="startDate"  value=<%=strMonday%> class="Wdate" onclick="WdatePicker()">--%>
					<rc:rcDate name="startDate" value="<%=strMonday%>" style="width: 71%"/>
				</td>
				<td class='title'>
					<s:text name="manage.endTime" />
					<%--结束时间 --%>
					:
				</td>
				<td class='input'>
					<%--<input  name="endDate" value = <%=strSunday%>	> --%>
					<rc:rcDate name="endDate" value="<%=strSunday%>" style="width: 71%"/>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="schedule.schedulingType1" />
					<%--调度去向类型 --%>
					:
				</td>
				<td class='input' colspan=3>
					<select name="nodeType" style="width: 297px">
						<option value="check" selected>
							<s:text name="schedule.mentionedHereunder" />
							<%--查勘 --%>
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
			<input type="hidden" name="editType" value="GETBACKQUERY">
			<input type="hidden" name="nodeType" value="<%= request.getParameter("nodeType") %>">
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