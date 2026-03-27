<%--
****************************************************************************
* DESC       ：95519报案完善查询条件输入页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-03-03
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<%@taglib prefix="rc" uri="/WEB-INF/tlds/rc-date.tld"%>
<html>
<head>
<title><s:text name="title.registBeforeEdit.titleName" />
	<%--查询报案信息 --%></title>
<%-- 公用函数 --%>
<script src="${ctx}/pages/common/regist/js/95519PerfectEdit.js"></script>
<%--案件状态标志处理--%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body onload="initPage();" class="yui-skin-sam">
	<form name="fm" method="post" onkeypress="KeyDown()">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan="4" class="formtitle">
					<s:text name="title.registBeforeEdit.titleName" />
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.registNo" />
					：
					<%-- 備案號碼 --%>
				</td>
				<td class='input'>
					<select class=tag name="RegistNoSign" onchange='clickable()'>
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="RegistNo" class="query" onchange='clickable()'>
				</td>
				<td class='title'>
					<s:text name="db.prpLregist.policyNo" />
					：
					<%-- 保單號碼 --%>
				</td>
				<td class='input'>
					<select class=tag name="PolicyNoSign" onchange='clickable()'>
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="PolicyNo" class="query" onchange='clickable()'>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.riskCode" />
					：
					<%-- 險種代碼 --%>
				</td>
				<td class='input'>
					<select class=tag name="RiskCodeSign" onchange='clickable()'>
						<option value="=">=</option>
					</select>
					<input type=text name="RiskCode" class="query" onchange='clickable()'>
				</td>
				<td class='title'>
					<s:text name="regist.prpLregist.registTime" />
					<%--備案時間 --%>
					：
				</td>
				<td class='input'>
					<select class=tag name="ReportDateSign" onchange='clickable()'>
						<option value="=">=&nbsp;</option>
						<option selected value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value=">=">&gt;=</option>
						<option value="<=">&lt;=</option>
					</select>
					<rc:rcDate name="ReportDate" defaultValue="-1" style="width: 60%"/>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.engineNo" />
					：
				</td>
				<%-- 引擎號碼--%>
				<td class='input'>
					<select class=tag name="EngineNoSign" onchange='clickable()'>
						<option value="=">=&nbsp;</option>
					</select>
					<input type=text name="EngineNo" class="query" onchange='clickable()'>
				</td>
				<%--报案查询增加被保险人查询条件--%>
				<td class='title'>
					<s:text name="db.prpLregist.licenseNo" />
					：
				</td>
				<%-- 牌照號碼--%>
				<td class='input'>
					<select class=tag name="LicenseNoSign" onchange='clickable()'>
						<option value="=">=</option>
					</select>
					<input type=text name="LicenseNo" class="query" onchange='clickable()'>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpCmain.insured" />
					ID
					<%-- 被保險人ID--%>
					：
				</td>
				<td class='input'>
					<select class=tag name="InsuredCodeSign" onchange='clickable()'>
						<option value="=">=&nbsp;</option>
					</select>
					<input type=text name="InsuredCode" class="query" onchange='clickable()'>
				</td>
				<%--报案查询增加被保险人查询条件--%>
				<td class='title'>
					<s:text name="db.prpCmain.insuredName" />
					：
				</td>
				<%-- 被保险人名称--%>
				<td class='input'>
					<select class=tag name="InsuredNameSign" onchange='clickable()'>
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="InsuredName" class="query" onchange='clickable()'>
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					"=
					<s:text name="prompt.schedule.query1" />
					<%-- "符号，必须精确查询。--%>
					<br>
					<s:text name="prompt.schedule.query2" />
					<%-- "=*"符号，前匹配後模糊的查询。--%>
					<br>
					<s:text name="prompt.schedule.query4" />
					<%-- 非车险可以用报案号或者保单号的前9位进行模糊查询！--%>
					<s:text name="regist.query3" />
					<%--立案後的报案将不能再修改！--%>
				</td>
			</tr>
		</table>
		<!-- Query Result -->
		<TABLE id="buttonTable" class="common" cellpadding="3" cellspacing="1">
			<TR class="center">
				<TD>
					<input type="button" class="button" name="queryButton" value="<s:text name="button.query.value"/>" onclick="executeQuery(1,10);">
					<%--查询 --%>
				</TD>
			</TR>
			<TR>
				<TD></TD>
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
		YAHOO.widget.DataTable.formatLink = function(elCell, oRecord, oColumn,oData) {
			if (oColumn.key == "registNo") {
				var flag = oRecord.getData("modifyFlag");
				var href = "";
				if(flag == "00" || flag == "10" || flag == "20"){
					href = "javascript:alert('"+ (flag == "00" ? "已註銷，不可修改" : ( flag == "10" ? "已立案，不可修改" : "已結案，不可修改" ))+"')";
				} else {
					href = "${ctx}/regist/registBeforeEdit.do?editType=PERFECT&prpLregistRegistNo="
						+ oRecord.getData("registNo") + "&prpCmainPolicyNo=" + oRecord.getData("policyNo");
				}
				elCell.innerHTML = "<a href=\"" + href + "\" >" + oData + "</a>";
			} else if (oColumn.key == "modifyFlag") {
				var statusStr = "";
				if (oData != null) {
					//00-已註銷，不可修改；01-未立案，可修改；10：已立案，不可修改；11-已立案，可修改；20-已結案，不可修改；21-已結案，可修改
					if (oData == "00") {
						statusStr = "已註銷，不可修改";
					} else if (oData == "01") {
						statusStr = "未立案，可修改";
					} else if (oData == "10") {
						statusStr = "已立案，不可修改";
					} else if (oData == "11") {
						statusStr = "已立案，可修改";
					} else if (oData == "20") {
						statusStr = "已結案，不可修改";
					} else if (oData == "21") {
						statusStr = "已結案，可修改";
					}
				}
				elCell.innerHTML = statusStr;
			} else if (oColumn.key == "reportDate") {
				if (oData != null) {
					var date = new Date(oData.time);
					elCell.innerHTML = formatDate(date, 'yyy-MM-dd');
				}
			} else {
				elCell.innerHTML = oData;
			}
		};
    contentColumnHeaders =[
        {key:"registNo",label:"<s:text name='db.prpLclaim.registNo'/>",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
        {key:"policyNo",label:"<s:text name='db.prpLclaim.policyNo'/>",width:"40em",sortable:true},
        {key:"riskCode",label:"<s:text name='db.prpDdbs.riskCode'/>",width:"40em",sortable:true},
        {key:"insuredName",label:"<s:text name='db.prpLregist.insuredName'/>",width:"40em",sortable:true},
        {key:"reportDate",label:"<s:text name='regist.prpLregist.registTime'/>",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
        {key:"serviceNo",label:"<s:text name='regist.serviceNumber'/>",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
        {key:"modifyFlag",label:"<s:text name='regist.modifyState'/>",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}
        ];
	}
	/*
	 *@description:可以批次切分活动组别结果集 
	 *@param  pageNo，pageSize
	 *@return  活动组别结果集
	 *@author 中科软
	 */
	function executeQuery(pageNo, pageSize) {
		if (isNaN(parseInt(pageNo))) {
			pageNo = 1;
		}
		if (isNaN(parseInt(pageSize))) {
			pageSize = 10;
		}
		var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);
		var myDataSource = new YAHOO.util.DataSource(
				"${ctx}/regist/callcenterPerfect.do?actionType=query");
		myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.connMethodPost = true;
		myDataSource.responseSchema = {
			resultsList : "data",
			fields : [ "registNo", "policyNo", "riskCode", "insuredName",
					"reportDate", "serviceNo", "modifyFlag" ],
			metaFields : {
				totalRecords : "totalRecords"
			}
		};
		myDataSource.subscribe("responseParseEvent", SINOSOFT.util.navigation);
		myDataSource.connMgr.setForm(fm);
		var initialRequest = "pageSize=" + pageSize + "&pageNo=" + pageNo;
		var myConfiges = {
			initialRequest : initialRequest,
			paginator : false
		};
		if (isFirstLoad == true) {
			contentDataTable = new YAHOO.widget.DataTable("content",
					myColumnSet, myDataSource, myConfiges);
			contentDataTable.initialRequest = initialRequest;
			isFirstLoad = false;
		} else {
			contentDataTable._oRecordSet = new YAHOO.widget.RecordSet();
			contentDataTable.initialRequest = initialRequest;
			contentDataTable.dataSource = myDataSource;
			contentDataTable.dataSource.sendRequest(
					contentDataTable.initialRequest,
					contentDataTable.onDataReturnReplaceRows, contentDataTable);
		}
		document.getElementById("tableResullt").style.display = "";
	}
	//init on load
	YAHOO.util.Event.addListener(window, 'load', init);
	/*
	 *@description:弹出页面
	 *@param  title，url
	 *@return  
	 *@author 中科软
	 */
	function showDlg(title, url) {
		submitDlg = new YAHOO.widget.Panel("submitDlg", {
			iframe : true,
			visible : false,
			width : 780,
			height : 463,
			underlay : "shadow",
			constraintoviewport : true,
			fixedcenter : true,
			modal : true,
			zIndex : 320
		});
		submitDlg.setHeader(title);
		submitDlg
				.setBody("<iframe name='submitFrame' src='javascript:false;'  frameborder='0' style='margin:0; padding:0; width:98%; height: 97%' align='left'></iframe>");
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
	
	function check(event , flag){
		//00-已註銷，不可修改；01-未立案，可修改；10：已立案，不可修改；11-已立案，可修改；20-已結案，不可修改；21-已結案，可修改
		if(flag == "00" || flag == "10" || flag == "20"){
			event.preventDefault();
			alert(flag == "00" ? "已註銷，不可修改" : ( flag == "10" ? "已立案，不可修改" : "已結案，不可修改" ));
		}
	}
</script>
</html>
