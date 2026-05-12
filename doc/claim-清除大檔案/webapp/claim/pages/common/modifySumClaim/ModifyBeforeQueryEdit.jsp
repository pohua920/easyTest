<%--
****************************************************************************
* DESC       立案信息查询
* AUTHOR     ： 中科软
* CREATEDATE ： 2013-03-10
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime" %>
<html>
<head>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/common/js/prototype.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<title><s:text name="title.claimBeforeEdit.titleName" /></title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
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
		if (oColumn.key == "claimNo") {
			//mantis：CLM0217，處理人員：DP0713，需求單編號：新核心-車險更正預估卡控異常
			elCell.innerHTML = "<a href=\"/claim/claim/modifySumClaim.do?editType=modifyDetail&riskCode="+oRecord.getData("riskCode")+"&swfLogLogNo="+oRecord.getData("remark")+"&claimNo=" + oRecord.getData("claimNo") + "\">" + oData + "</a>";
		} else if (oColumn.key == "claimDate") {
			if (oData != null) {
				var date = new Date(oData.time);
				elCell.innerHTML = formatDate(date, 'yyy-MM-dd');
			}
		} else {
			elCell.innerHTML = oData;
		}
	};
	contentColumnHeaders =[
	               		{key:"claimNo",label:"<s:text name="check.claimNum" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--赔案号--%>
	               		{key:"policyNo",label:"<s:text name="db.prpCmain.policyNo" />",width:"40em"},<%--保单号--%>
	               		{key:"riskCode",label:"<s:text name="db.prpDdbs.riskCode" />",width:"40em",sortable:true},<%--险种--%>
	               		{key:"insuredName",label:"<s:text name="db.prpCmain.insured" />",width:"40em"},<%--被保险人--%>
	               		{key:"claimDate",label:"<s:text name="prpLclaim.claimDate" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}<%--立案时间--%>
	               		];
}

/*
 *@description:可以批次切分活动组别结果集
 *@param  pageNo，pageSize
 *@return  活动组别结果集
 *@author 中科软
 */

function executeQuery(pageNo, pageSize, field) {
	if ((fm.InsuredNameSign.value == "=" && fm.InsuredName.value.length > 0) || (fm.RegistNoSign.value == "=" && fm.RegistNo.value.length > 0) || (fm.PolicyNoSign.value == "=" && fm.PolicyNo.value.length > 0) || (fm.ClaimNoSign.value == "=" && fm.ClaimNo.value.length > 0)) {
		//输入了一个条件，可以查
	} else if ((fm.RegistNoSign.value == "=*" && fm.RegistNo.value.length > 8) || (fm.PolicyNoSign.value == "=*" && fm.PolicyNo.value.length > 8) || (fm.ClaimNoSign.value == "=*" && fm.ClaimNo.value.length > 8)) {
		if ("D" == getClassCodeType(fm.RegistNo.value.substr(1, 2)) || "D" == getClassCodeType(fm.PolicyNo.value.substr(1, 2)) || "D" == getClassCodeType(fm.ClaimNo.value.substr(1, 2))) {
			alert("车险必须精确查询！");
			return false;
		} else {
			//非车险可以前9位模糊查询
		}
	} else {
		alert("車險必須輸入備案號碼、保單號碼、立案號碼、被保險人其中一項精確查詢！\n 非車險可以用備案號碼、立案號碼或者保單號碼的前9位進行糢糊查詢！");
		return false;
	}
	init(); //用於回显，请勿删除
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
	var myDataSource = new YAHOO.util.DataSource("${ctx}/claim/modifySumClaim.do");
	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.connMethodPost = true;
	//mantis：CLM0217，處理人員：DP0713，需求單編號：新核心-車險更正預估卡控異常
	myDataSource.responseSchema = {
		resultsList: "data",
		fields: ["claimNo", "policyNo", "riskCode", "insuredName", "claimDate","remark"],
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
/*
 *@description:弹出页面
 *@param  title，url
 *@return
 *@author 中科软
 */

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
</script>
<%-- <body  onload="initPage();"> --%>
<body class="yui-skin-sam">
	<form name="fm" action="/claim/modifySumClaim.do" method="post">
		<input type="hidden" name="editType" value="modifyBeforeQuery">
		<table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="common">
			<tr>
				<td colspan="4" class="formtitle">
					<s:text name="claim.queryClaim" />
				</td>
				<%--查询立案信息 --%>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaim.claimNo" />：
				</td>
				<td class='input'>
					<select class=tag name="ClaimNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="ClaimNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpCmain.policyNo" />：
				</td>
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="PolicyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLarrearageadd.insuredname" />：
				</td>
				<%--被保险人名称 --%>
				<td class='input'>
					<select class=tag name="InsuredNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="InsuredName" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpDdbs.riskCode" />:
				</td>
				<%--险种 --%>
				<td class='input'>
					<select class=tag name="RiskCodeSign">
						<option value="=">=&nbsp;</option>
						<!--<option value="=*">=*</option>-->
					</select>
					<input type=text name="RiskCode" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.registNo" />:
				</td>
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="RegistNo" class="query">
				</td>
				<td class='title'></td>
				<td class='input'></td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<br>
					<%--"="符号，必须精确查询。 --%>
					<s:text name="prompt.schedule.query2" />
					<br>
					<%--"=*"符号，前匹配後模糊的查询。 --%>
					<s:text name="prompt.schedule.query3" />
					<br>
					<%--车险必须输入报案号、保单号、赔案号、车牌号、被保险人其中一项精确查询！ --%>
					<s:text name="prompt.schedule.query4" />
					<%--"="非车险可以用报案号、赔案号或者保单号的前9位进行模糊查询！ --%>
				</td>
			</tr>
		</table>
		<table width=100%>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="executeQuery(1,10,this);">
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
		<input type="hidden" name="nodeType" value="${param.nodeType}">
	</form>
</body>
</html>