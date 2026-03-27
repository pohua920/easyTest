<%--
****************************************************************************
* DESC       ：新增定损调度通用查询输入界面
* AUTHOR     ： 理赔组	
* CREATEDATE ： 2013-03-12
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>

<%
	//得到本周周一与周日的日期
	String strMonday = new DateTime(DateTime.current().addDay(-4),
			DateTime.YEAR_TO_DAY).toString();
%>
<html>
<head>
<title>新增定损调度通用查询输入界面</title>
</head>
<body class="yui-skin-sam">
	<form name="fm" method="post">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td class="formtitle" colspan="4">
					<s:text name="schedule.addDamageAdjustQuery" />
					<%--新增定损调度查询--%>
				</td>
			</tr>
			<tr>
				<td class='title' style="width: 15%">
					<s:text name="db.prpLregist.registNo" />
					:
					<%--报案号--%>
				</td>
				<td class='input' style="width: 25%">
					<select class=query name="registNoSign" style="width: 40px">
						<option value="=">=</option>
						<option value="*">*</option>
					</select>
					<input type=text name="registNo" class="input" style="width: 70%">
				</td>
				<td class='title' style="width: 15%">
					<s:text name="db.prpLregist.licenseNo" />
					:
					<%--车牌号码--%>
				</td>
				<td class='input'>
					<select class=query name="prpLscheduleItemLicenseNoSign" style="width: 40px">
						<option value="=">=</option>
					</select>
					<input name="prpLscheduleItemLicenseNo" class="input" style="width: 70%">
				</td>
			</tr>
			<tr>
				<td class='title' style="width: 15%">
					<s:text name="manage.startTime" />
					:
					<%--开始时间--%>
				</td>
				<td class='input'>
					<%-- <input name="startDate" value="<%=strMonday%>"
					class="Wdate" onClick="WdatePicker()"/>--%>
					<rc:rcDate name="startDate" value="<%=strMonday%>" />
				</td>
				<td class='title' style="width: 15%">
					<s:text name="manage.endTime" />
					:
					<%--结束时间--%>
				</td>
				<td class='input'>
					<%-- <input name="endDate" value="<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd" />" class="Wdate" onClick="WdatePicker()"/>--%>
					<rc:rcDate name="endDate" defaultValue="0" format="yyyy-MM-dd" />
				</td>
			</tr>
			<tr>
				<td class='title' style="width: 15%">
					<s:text name="db.prpLregist.insuredName" />
					:
					<%--被保险人名称--%>
				</td>
				<td class='input' style="width: 25%" colspan=3>
					<select class=query name="InsuredNameSign" style="width: 40px">
						<option value="=">=</option>
						<option value="*">*</option>
					</select>
					<input type=text name="InsuredName" class="input" style="width: 28%" value="">
				</td>
			</tr>
		</table>
		<div align="center">
			<input type="hidden" name="editType" value="ADDQUERY">
			<input type="hidden" name="nodeType" value="${param.nodeType }">
			<span class="button" style="width: 20%"> <input type=button id="button" class='button' value="<s:text name='button.query.value' />" onclick="executeQuery(1,10,this);">
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
<script type="text/javascript">
	var isFirstLoad = true;
	var contentDataTable;
	var contentColumnHeaders;
	YAHOO.namespace("query.container");
	
	function init() {
		YAHOO.widget.DataTable.formatLink = function (elCell, oRecord, oColumn, oData) {
			if (oColumn.key == "nodeStatus") {
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
				if (oData != null) {
					elCell.innerHTML = oData;
				}
			} else if (oColumn.key == "businessNo") {
				elCell.innerHTML = "<a href=\"${ctx}/schedule/scheduleAddCertainLossTask.do?businessNo=" + oRecord.getData("keyIn") + "&editType=ADDSHOW" + "&swfLogFlowID=" + oRecord.getData("id.flowID") + "&swfLogLogNo=" + oRecord.getData("id.logNo") + "&policyNo=" + oRecord.getData("policyNo") + "&nodeStatus=" + oRecord.getData("nodeStatus") + "&riskCode=" + oRecord.getData("riskCode") + "\">" + oData + "</a>";
			} else if (oColumn.key == "flowInTime") {
				if (oData != null) {
					elCell.innerHTML = formatDate(oData, "yyy-MM-dd");
				}
			} else if (oColumn.key == "lossItemName") {
				if (oData != null) {
					elCell.innerHTML = oData;
				}
			} else {
				elCell.innerHTML = oData;
			}
		};
		contentColumnHeaders =[
			               		{key:"nodeStatus",label:"<s:text name="db.prpLclaimStatus.status" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--案件状态--%>
			               		{key:"businessNo",label:"<s:text name="prompt.queRegist.RegistNo" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--报案号--%>
			               		{key:"lossItemName",label:"<s:text name="db.prpLdriver.licenseNo" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--车牌号--%>
			               		{key:"handlerName",label:"<s:text name="db.prpLlawsuit.operatorCode" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--操作员--%>
			               		{key:"flowInTime",label:"<s:text name="claim.intoTime" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}<%--流入时间--%>
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
	
	function executeQuery(pageNo, pageSize, field) {
		//init();
		if (validateForm(fm)) {
			// reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
			//增加!!field判断，如果field为undefined，则!!field为false
			if ( !! field) {
				field.disabled = true;
			}
			if (isNaN(parseInt(pageNo))) {
				pageNo = 1;
			}
			if (isNaN(parseInt(pageSize))) {
				pageSize = 10;
			}
			var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);
			var myDataSource = new YAHOO.util.DataSource("${ctx}/schedule/scheduleCheckQuery.do");
			myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
			myDataSource.connMethodPost = true;
			myDataSource.responseSchema = {
				resultsList: "data",
				fields: ["nodeStatus", "businessNo", "lossItemName", "handlerName", "flowInTime", "policyNo", "nodeStatus", "riskCode", "keyIn", {
					key: "id.flowID"
				}, {
					key: "id.logNo"
				}],
				metaFields: {
					totalRecords: "totalRecords"
				}
			};
			myDataSource.subscribe("responseParseEvent", SINOSOFT.util.navigation);
			myDataSource.connMgr.setForm(fm);
			var initialRequest = "pageSize=" + pageSize + "&pageNo=" + pageNo;
			var myConfiges = {
				initialRequest: initialRequest,
				paginator: false
			};
			if (isFirstLoad == true) {
				contentDataTable = new YAHOO.widget.DataTable("content", myColumnSet, myDataSource, myConfiges);
				contentDataTable.initialRequest = initialRequest;
				isFirstLoad = false;
			} else {
				contentDataTable._oRecordSet = new YAHOO.widget.RecordSet();
				contentDataTable.initialRequest = initialRequest;
				contentDataTable.dataSource = myDataSource;
				contentDataTable.dataSource.sendRequest(contentDataTable.initialRequest, contentDataTable.onDataReturnReplaceRows, contentDataTable);
			}
			document.getElementById("tableResullt").style.display = "";
			//增加!!field判断，如果field为undefined，则!!field为false
			if ( !! field) {
				// reason:当次查询结束，按钮恢复
				field.disabled = false;
			}
		}
	}
	//按钮响应回车
	
	function document.onkeydown() {
		if (event.keyCode == 13) {
			document.getElementById("button").click();
			return false;
		}
	}
</script>
</html>