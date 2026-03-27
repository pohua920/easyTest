<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>账户管理</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>

</head>
<body id="all_title">
<s:form name="fm" action="queryUserList" namespace="/utiIAccount" method="post"
	target="utiIUserQueryRight">
	<div id="wrapper">
	<div id="container">
	<div id=""></div>
	<s:hidden name="flag" id="flag"></s:hidden></div>
	<table class="fix_table" width="100%">
		<tr>
			<td align="center">
			<h2>查询用户</h2>
			</td>
		</tr>
	</table>
	<table class="fix_table" width="100%">
		<s:hidden name="svrCode" value="${svrCode}"></s:hidden>
		<tr>
			<td class="bgc_tt short">用户代码</td>
			<td class="long"><input name="utiIUser.userCode"
				id="utiIUser.userCode" class='input_w w_45'></td>
		</tr>
		<tr>
			<td class="bgc_tt short">用户名称</td>
			<td class="long"><input name="utiIUser.userName"
				id="utiIUser.userName" class='input_w w_45'></td>
		</tr>
		<tr>
			<td class="bgc_tt short">归属机构</td>
			<td class="long">
			<div id="validStatusMapDiv" class="selectui-indiv">
			<div class="selectConfig">
			<div class="codeType">StaticSelect</div>
			</div>
			<c:set var="checked" value="0" /> <ce:select name="utiIUser.comCode"
				id="utiIUser.comCode" cssClass="selectui-input-up input_w w_45"
				value="${checked}" list="comCodeMap" /></div>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">用户类型</td>
			<td class="long">
				             <div id="userTypeDiv" class="selectui-indiv">
					        <div class="selectConfig">
					        <div class="codeType">StaticSelect</div>
					        </div>
					        <c:set var="checked" value="00" />
					        <ce:select name="utiIUser.userType" id="userType" cssClass="selectui-input input_w w_15	"  value="${checked}" list="userTypeMap" />
					    </div>
				                    </td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="button" class="button_ty" value="查 询" onclick="executeQuery(1, 10);">
			</td>
		</tr>

	</table>
	<div id="content_navigation" class="query" align="center"></div>
	<div id="content" class="sort"></div>
	<div id="content_navigation" class="query" align="center"></div>
	</div>
</s:form>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">
	var contentDataTable;
	var contentColumnHeaders;
	YAHOO.namespace("query.container");
	function init() {
		YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn,
				oData) {
			var uCode = oRecord.userCode;
			if (oColumn.key == "operate") {
				elCell.innerHTML = "<a href=\"#\"  onclick=\"chooseUser('"
						+ uCode + "')\">选择</a>";
			}
		};
		contentColumnHeaders = [ {
			key : "userCode",
			text : "用户代码",
			width : "35em",
			sortable : true
		}, {
			key : "userName",
			text : "用户名称",
			width : "35em",
			sortable : true
		}, {
			key : "operate",
			text : "操作",
			width : "30em",
			type : "link",
			resizeable : true
		} ];
		executeQuery(1, 10);
	}
	//Query Data
	function executeQuery(pageNo, pageSize) {

		if (isNaN(parseInt(pageNo))) {
			pageNo = 1;
		}
		if (isNaN(parseInt(pageSize))) {
			pageSize = 10;
		}
		var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);
		var myDataSource = new YAHOO.util.DataSource(
				"${ctx}/utiIAccount/queryUserList.do");
		myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.responseSchema = {
			resultsList : "data",
			fields : [ "userCode", "userName" ],
			totalRecords : "totalRecords"
		};
		myDataSource.subscribe("responseParseEvent", SINOSOFT.util.navigation);
		myDataSource.connMgr.setForm(fm);
		var initialRequest = "pageSize=" + pageSize + "&pageNo=" + pageNo;
		var myConfiges = {
			initialRequest : initialRequest,
			paginator : false
		};
		contentDataTable = new YAHOO.widget.DataTable("content", myColumnSet,
				myDataSource, myConfiges);
	}
	YAHOO.util.Event.addListener(window, 'load', init);

	function chooseUser(userCode) {
		fm.action = "${ctx }/utiIAccount/userAndAccountQuery.do?userCode="
				+ userCode;
		fm.submit();
	}
</script>


