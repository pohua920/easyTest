<%--
****************************************************************************
* DESC       ：单证查询输入界面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-07-05
* MODIFYLIST ：   Name       Date            Reason/Contents
           1. 增加车牌号，案件状态，操作时间查询条件
*          ------------------------------------------------------
								zhangshi		20130201			修改*操作符为=*将like '%%'改为左%右匹配查询
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title><s:text name="title.claimBeforeEdit.titleName" /></title>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<script language="javascript">
//按钮响应回车

function document.onkeydown() {
	if (event.keyCode == 13) {
		document.getElementById("button").click();
		return false;
	}
}
  </script>
</head>
<%-- <body  onload="initPage();document.onkeydown();"> --%>
<body class="yui-skin-sam">
	<form name="fm" action="${ctx }/certifyQuery.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="certify.queryDocumentInfo" />
				</td>
			</tr>
			<%--查询单证信息--%>
			<tr>
				<td class='title'>
					<s:text name="prpLregist.registNo" />:
				</td>
				<%--报案号--%>
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="RegistNo" class="query">
				</td>
				<%--报案查询增加被保险人查询条件--%>
				<td class='title'>
					<s:text name="db.view_larrearage.insuredname" />:
				</td>
				<%--被保险人名称--%>
				<td class='input'>
					<select class=tag name="InsuredNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="InsuredName" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.licenseNo" />:
				</td>
				<td class='input'>
					<select class=tag name="LicenseNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="LicenseNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.operatedate" />:
				</td>
				<%--操作时间--%>
				<td class='input'>
					<select class=tag name="OperateDateSign">
						<%@ include file="/pages/common/pub/CommonStringOption.jsp"%>
					</select>
					<%-- <input type=text name="OperateDate" class="Wdate" onClick="WdatePicker()"  description="操作时间"/>--%>
					<rc:rcDate name="OperateDate" title="操作時間" style="width:60%"/>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="certainLoss.prpLscheduleMainWF.caseState" />
				</td>
				<%--案件状态:--%>
				<td colspan="3" class='input'>
					<input type="hidden" name="caseFlag">
					<!--<input type="checkbox" name="status" value="1">未处理-->
					<input type="checkbox" name="status" value="2">
					<s:text name="common.status.intreating" />
					<%--正处理--%>
					<%--没有此种案件状态 <input type="checkbox" name="status" value="3">已处理--%>
					<input type="checkbox" name="status" value="4">
					<s:text name="common.status.submited" />
					<%--已提交--%>
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<br>
					<%--"="符号，必须精确查询。--%>
					<s:text name="prompt.schedule.query2" />
					<br>
					<%--"=*"符号，前匹配後模糊的查询。--%>
					<s:text name="prompt.schedule.query3" />
					<br>
					<%--车险必须输入报案号、车牌号、被保险人其中一项精确查询！--%>
					<s:text name="prompt.schedule.query4" />
					<%--非车险可以用报案号的前9位进行模糊查询！--%>
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button id="button" class='button' value="<s:text name='button.query.value' />" onClick="executeQuery(1,10,this);">
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
		<input type="hidden" name="editType" value="SHOW">
		${prpLcertifyCollect.editType}
		<input type="hidden" name="nodeType" value="<s:property value='#parameters.nodeType'/>">
	</form>
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

	function init() {
		YAHOO.widget.DataTable.formatLink = function (elCell, oRecord, oColumn, oData) {
			if (oColumn.key == "id.businessNo") {
				elCell.innerHTML = "<a href='/claim/certifyFinishQueryList.do?prpLcertifyCertifyNo=" + oData + "&nodeType=certi&editType=SHOW&riskCode=" + oRecord.getData("riskCode") + "'>" + oData + "</a>";
			} else if (oColumn.key == "status") {
				var statusStr = "";
				if (oData == "1") {
					statusStr = "未處理";
				} else if (oData == "2") {
					statusStr = "正處理";
				} else if (oData == "3") {
					statusStr = "已處理";
				} else if (oData == "4") {
					statusStr = "已提交";
				} else if (oData == "5") {
					statusStr = "已撤銷";
				}
				elCell.innerHTML = statusStr
			} else if (oColumn.key == "operatorCode") {
				elCell.innerHTML = oData + "(" + oRecord.getData("operatorName") + ")";
			} else if (oColumn.key == "operateDate" || oColumn.key == "startDate") {
				if (oData != null) {
					var date = new Date(oData.time);
					elCell.innerHTML = formatDate(date, 'yyy-MM-dd');
				}
			} else {
				elCell.innerHTML = oData;
			}
		};
		contentColumnHeaders =[
		               		{key:"status",label:"案件狀態",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
		               		{key:"id.businessNo",label:"備案號碼",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
		               		{key:"startDate",label:"開始收集日期",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
		               		{key:"operatorCode",label:"操作人員",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
		               		{key:"operateDate",label:"操作時間",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
		               		{key:"collectFlag",label:"收集標誌",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}
		               		];
	}

	/*
	 *@description:可以批次切分活动组别结果集
	 *@param  pageNo，pageSize
	 *@return  活动组别结果集
	 *@author 中科软
	 */

	function executeQuery(pageNo, pageSize, field) {
		if ((fm.InsuredNameSign.value == "=" && fm.InsuredName.value.length > 0) || (fm.LicenseNoSign.value == "=" && fm.LicenseNo.value.length > 0) || (fm.RegistNoSign.value == "=" && fm.RegistNo.value.length > 0)) {
			//输入了一个条件，可以查
		} else if ((fm.RegistNoSign.value == "=*" && fm.RegistNo.value.length > 8)) {
			if ("D" == getClassCodeType(fm.RegistNo.value.substr(1, 2))) {
				alert("车险必须精确查询！");
				return false;
			} else {
				//非车险可以前9位模糊查询
			}
		} else {
			alert("車險必須輸入備案號碼、牌照號碼、被保險人其中一項精確查詢！\n 非車險可以用備案號碼的前9位進行模糊查詢！");
			return false;
		}
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
		var myDataSource = new YAHOO.util.DataSource("${ctx}/certifyQuery.do");
		myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.connMethodPost = true;
		myDataSource.responseSchema = {
			resultsList: "data",
			fields: ["status", "id.businessNo", "startDate", "operatorCode", "operatorName", "operateDate", "collectFlag", "riskCode"],
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
	//init on load
	YAHOO.util.Event.addListener(window, 'load', init);
</script>
</body>
</html>