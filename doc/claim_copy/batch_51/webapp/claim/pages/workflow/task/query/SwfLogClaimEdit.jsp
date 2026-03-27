<%--
****************************************************************************
* DESC       立案修改
* AUTHOR     ： CD078
* CREATEDATE ： 2024-06-18
* MODIFYLIST ： 立案節點工作流 (用於修改出險時間、出險地點)
*          ------------------------------------------------------
****************************************************************************
--%>
<!-- mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能 -->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<%@taglib prefix="rc" uri="/WEB-INF/tlds/rc-date.tld"%>
<html>
<head>
<script type="text/javascript">
var isFirstLoad = true;
var contentDataTable;
var contentColumnHeaders;
YAHOO.namespace("query.container");
function init() {
	YAHOO.widget.DataTable.formatLink = function(elCell, oRecord, oColumn, oData) {
		if(oColumn.key =="claimNo"){elCell.innerHTML="<a href=\"/claim/claim/claimFinishQueryList.do?prpLclaimClaimNo=" + oRecord.getData("claimNo") + "&editType=EDIT&nodeType=claim&specialEditCase=specialEditCase&swfLogLogNo="+oRecord.getData("remark")+"&riskCode=" + oRecord.getData("riskCode") + "\">"+ oData+"</a>";
		}
	}; 
	contentColumnHeaders =[
    {key:"claimNo",label:"<s:text name='db.prpLclaim.claimNo'/>",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
    {key:"registNo",label:"<s:text name='db.prpLclaim.registNo'/>",width:"40em",sortable:true},
    {key:"riskCode",label:"<s:text name='db.prpLclaim.riskCode'/>",width:"40em",sortable:true},
    {key:"insuredName",label:"<s:text name='db.prpLclaim.insuredName'/>",width:"40em",sortable:true},
    {key:"operatorCode",label:"<s:text name='db.prpLclaim.operatorCode'/>",width:"40em",sortable:true}
    ];
}
function executeQuery(pageNo, pageSize,field) {
	if((fm.LicenseNoSign.value=="="&&fm.LicenseNo.value.length>0)
		  	 ||(fm.RegistNoSign.value=="="&&fm.RegistNo.value.length>0)
		  	 ||(fm.PolicyNoSign.value=="="&&fm.PolicyNo.value.length>0)
		  	 ||(fm.ClaimNoSign.value=="="&&fm.ClaimNo.value.length>0)){
		  	 //输入了一个条件，可以查		  	  		
	}else{
		alert("必須輸入立案號碼、備案號碼、保單號碼、牌照號碼其中一項精準查詢！");
		return false;
	}
	// 	reason:當按下按鈕時，將按鈕返灰，以防使用者多次點擊造成錯誤
	// 	增加!!field判断，如果field为undefined，则!!field为false
	if(!!field){
		field.disabled = true;
	}
	if (isNaN(parseInt(pageNo))) {
		pageNo = 1;
	}
	if (isNaN(parseInt(pageSize))) {
		pageSize = 10;
	}
	var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);
	var myDataSource = new YAHOO.util.DataSource(
			"${ctx}/regist/claimQuery.do");
	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.connMethodPost = true;
	myDataSource.responseSchema = {
		resultsList : "data",
		fields : [ "claimNo", "registNo", "riskCode", "insuredName","operatorCode","remark" ],
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
	//增加!!field判断，如果field为undefined，则!!field为false
	if(!!field){
		field.disabled = false;
	}
}
//init on load
YAHOO.util.Event.addListener(window, 'load', init);

</script>
<%--案件状态标志处理--%>
</head>
<body onload="initPage();" class="yui-skin-sam">
	<form name="fm" method="post" onkeypress="KeyDown()">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan="4" class="formtitle">
					<s:text name="claim.queryClaimEdit" /><!-- 立案修改 -->
				</td>		
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaim.claimNo" />：<!-- 立案號碼 -->
				</td> 
				<td class='input'>
					<select class=tag name="ClaimNoSign">
							<option value="=">=</option>
					</select> 
					<input type=text name="ClaimNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpCmain.policyNo" />：<!-- 保單號碼 -->
				</td> 
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="=">=</option>
					</select> 
					<input type=text name="PolicyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.registNo" />： <!-- 備案號碼 -->
				</td>
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
					</select> 
					<input type=text name="RegistNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLregist.licenseNo" />：<!-- 牌照號碼 -->
				</td>
				<td class='input'>
					<select class=tag name="LicenseNoSign">
							<option value="=">=&nbsp;</option>
					</select> 
					<input type=text name="LicenseNo" class="query">
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					"=
					<s:text name="prompt.schedule.query1" /> <%-- "符号，必须精确查询。--%>
					<br>
					<s:text name="workflow.LeastOnePreciseQueryCondition" /><%-- 必須輸入立案號碼、備案號碼、保單號碼、牌照號碼其中一項精準查詢！ --%>
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button id="button" class='button' value="<s:text name="button.query.value" />" onclick="executeQuery(1,10,this);">
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
	<input type="hidden" name="editType" value="EditCase">
	<input type="hidden" name="nodeType" value="${param.nodeType}">
	</form>
</body>
</html>
