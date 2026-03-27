<%--
****************************************************************************
* DESC       ：综合查询条件输入页面
* AUTHOR     ：中科软
* CREATEDATE ：2013-03-19
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<html>
<head>
<title><s:text name="quickCase.queryInfo" /></title>
<%--综合查询信息 --%>
</head>
<body class="yui-skin-sam">
	<form name="fm" action="/claim/integratedQuery.do?editType=query" method="post" onsubmit="return validateForm(this);">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="quickCase.queryInfo" />
					<%--综合查询信息 --%>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.registNo" />
					：
					<%--报案号码 --%>
				</td>
				<td class='input'>
					<select name="RegistNoSign" class=tag>
						<option value="=">=</option>
					</select>
					<input type=text name="prpLregistRegistNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLregist.policyNo" />
					：
				</td>
				<%--保单号码 --%>
				<td class='input'>
					<select name="PolicyNoSign" class=tag>
						<option value="=">=</option>
					</select>
					<input type=text name="prpLregistPolicyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLcheckExt.claimNo" />
					：
				</td>
				<%--立案号码 --%>
				<td class='input'>
					<select name="ClaimNoSign" class=tag>
						<option value="=">=</option>
					</select>
					<input type="text" id="prpLregistClaimNo" name="prpLregistClaimNo" class="query" />
				</td>
				<td class='title'>強制證號碼：</td>
				<%--強制證號碼--%>
				<td class='input'>
					<select name="CompelLicenseNo" class=tag>
						<option value="=">=</option>
					</select>
					<input type="text" name="prpLregistCompelLicenseNo" class="query" />
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.licenseNo" />
					：
				</td>
				<%--牌照號碼 --%>
				<td class='input'>
					<select name="LicenseNoSign" class=tag>
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="prpLregistLicenseNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpCmain.insuredName" />
					：
				</td>
				<%--被保险人名称 --%>
				<td class='input'>
					<select class=tag name="InsuredNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="prpLregistInsuredName" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>財車車牌：</td>
				<%--財车车牌 --%>
				<td class='input'>
					<select name="ThirdLicenseNoSign" class=tag>
						<option value="=">=</option>
					</select>
					<input type=text name="prpLregistThirdLicenseNo" class="query">
				</td>
				<td class='title'>被保險人ID：</td>
				<%--被保險人ID --%>
				<td class='input'>
					<select name="InsuredIdSign" class=tag>
						<option value="=">=</option>
					</select>
					<input type=text name="prpLregistInsuredId" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>受害人ID：</td>
				<%--受害人ID --%>
				<td class='input'>
					<select name="IdentifyNumberSign" class=tag>
						<option value="=">=</option>
					</select>
					<input type=text name="prpLregistIdentifyNumber" class="query">
				</td>
				<td class='title' style="width: 10%">
					<s:text name="workflow.oaFlowState" />
					:
				</td>
				<%--流程流转状态 --%>
				<td class='input' style="width: 25%; display:">
					<input type="radio" name="caseType" value="0" checked>
					<s:text name="workflow.normalFlow" />
					<%--正常流转 --%>
					<input type="radio" name="caseType" value="1">
					<s:text name="workflow.endFlow" />
					<%-- 结束流转--%>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="regist.prpLregist.comName" />
					：
					<%--归属机构--%>
				</td>
				<td class='input'>
					<input type="hidden" name="comCode" value="${user.comCode}">
					<input type=text name="comCName" class="codename" title="出單單位"
						value="${user.comName}" style="width: 81%" 
						ondblclick="code_CodeSelect(this, 'prpdcompany2','-1,0','Y','N',fm.comCode.value);" 
						onchange="code_CodeChange(this, 'prpdcompany2','-1,0','Y','N',fm.comCode.value);" 
						onkeyup="code_CodeSelect(this, 'prpdcompany2','-1,0','Y','N',fm.comCode.value);">
				</td>
				<td class='title'>
					<s:text name="quickCase.operator" />
					：
					<%--操作人员 --%>
				</td>
				<td class='input'>
					<select name="userNameSign" class=tag>
						<option value="=">=</option>
					</select>
					<input type=text name="userName" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="check.claimType" />
					：
					<%--赔案类型 --%>
				</td>
				<td class='input'>
					<input type="hidden" name="claimNodeCode">
					<input type=text name="claimNodeName" class="codename" title="賠案類型" value="" style="width: 81%" ondblclick="code_CodeSelect(this, 'ClaimNode','-1,0','Y','N');"
						onchange="code_CodeSelect(this, 'ClaimNode','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'ClaimNode','-1,0','Y','N');">
					<img src="/claim/images/bgMarkMustInput.jpg">
				</td>
				<td class='title'>
					<s:text name="guarantee.dealIime" />
					：
					<%--处理时间 --%>
				</td>
				<td class='input'>
					<%-- <input type=text style="width:85" name="statStartDate" class="Wdate" onClick="WdatePicker()" readonly="readonly" value="<%= new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY) %>" >--%>
					<rc:rcDate name="statStartDate" readonly="true" style="width:156px" value="<%=new DateTime(DateTime.current().toString(),
					DateTime.YEAR_TO_DAY)%>" />
					<s:text name="claim.toThe" />
					<%--到 --%>
					<%--<input type=text style="width:85" name="statEndDate" class="Wdate" onClick="WdatePicker()" readonly="readonly" value="<%= new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY) %>" > --%>
					<rc:rcDate name="statEndDate" readonly="true" style="width:156px" value="<%=new DateTime(DateTime.current().toString(),
					DateTime.YEAR_TO_DAY)%>" />
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value' />" onclick="submitForm(this);">
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
	</form>
</body>
<script type="text/javascript">
var serialNo = 1;
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
function init(){
	YAHOO.widget.DataTable.formatLink = function(elCell, oRecord, oColumn, oData) {
		var nodeType = oRecord.getData("nodeType");
		var status = oRecord.getData("nodeStatus");
		var typeFlag = oRecord.getData("typeFlag");
		if(oColumn.key =="businessNo"){
			if ("0"==status||"1"==status||"5"==status) {
				elCell.innerHTML = "<a href=\"javascript:alert('該節點目前沒有訊息')\">"+ oRecord.getData("registNo") +"</a>";
			} else {
				var flowStr = "&swfLogFlowID=" + oRecord.getData("id.flowID") + "&swfLogLogNo=" + oRecord.getData("id.logNo") + "&status=" + oRecord.getData("nodeStatus") + 
				"&riskCode=" + oRecord.getData("riskCode") + "&editType=SHOW" + "&nodeType=" + oRecord.getData("nodeType") + "&businessNo=" + oRecord.getData("businessNo") +
				 "&policyNo=" + oRecord.getData("policyNo") + "&modelNo=" + oRecord.getData("modelNo") + "&nodeNo=" + oRecord.getData("nodeNo");
				var strInfoLink = "";
				if (nodeType=="regis") { //报案信息
					strInfoLink = "/claim/regist/registFinishQueryList.do?prpLregistRegistNo=" + oRecord.getData("businessNo") + "&editType=SHOW&riskCode=" + oRecord.getData("riskCode");
				}
				if ("sched"==nodeType) { //调度信息
					strInfoLink = "/claim/schedule/scheduleFinishQueryList.do?prpLscheduleMainWFRegistNo=" + oRecord.getData("registNo") + "&editType=SHOW&prpLscheduleMainWFScheduleID=1&scheduleType=schel";
				}
				if ("check"==nodeType) { //查勘信息
					var accicheckNo = "";
					if ("27"==oRecord.getData("riskCode").substring(0, 2)) {
						accicheckNo = oRecord.getData("keyIn");
					}
					strInfoLink = "/claim/check/checkFinishQueryList.do?prpLcheckCheckNo=" + oRecord.getData("registNo") + "&editType=SHOW&riskCode=" + oRecord.getData("riskCode") + "&checkNo=" + oRecord.getData("registNo") + "&accicheckNo=" + accicheckNo;
				}
				if ("claim"==nodeType) { //立案信息
					strInfoLink = "/claim/claimFinishQueryList.do?prpLclaimClaimNo=" + oRecord.getData("keyOut") + "&editType=SHOW&riskCode=" + oRecord.getData("riskCode");
				}
				if ("certa"==nodeType) { //定损信息
					strInfoLink = "/claim/certainLoss/certainLossFinishQueryList.do?prpLverifyLossRegistNo=" + oRecord.getData("registNo") + "&lossItemCode=" + oRecord.getData("lossItemCode") + "&lossItemName=" + oRecord.getData("lossItemName")+ flowStr;
				}
				if ("wound"==nodeType) { //人伤定损信息
					strInfoLink = "/claim/certainLoss/certainLossFinishQueryList.do?prpLverifyLossRegistNo=" + oRecord.getData("registNo")+ "&lossItemCode=" + oRecord.getData("lossItemCode") + "&lossItemName=" + oRecord.getData("lossItemName")+ flowStr;
				}
				if ("propc"==nodeType) { //财产定损信息
					strInfoLink = "/claim/certainLoss/certainLossFinishQueryList.do?prpLverifyLossRegistNo=" + oRecord.getData("registNo") + "&lossItemCode=" + oRecord.getData("lossItemCode") + "&lossItemName=" + oRecord.getData("lossItemName")+ flowStr;
				}
				if ("verif"==nodeType||"veriw"==nodeType||"propv"==nodeType) { //核损信息
					strInfoLink = "/claim/verifyLoss/verifyLossFinishQueryList.do?prpLverifyLossRegistNo=" + oRecord.getData("registNo")  + "&lossItemCode=" + oRecord.getData("lossItemCode") + "&lossItemName=" + oRecord.getData("lossItemName")+ flowStr;
				}
				if ("certi"==nodeType) { //单证信息
					strInfoLink = "/claim/certify/certifyFinishQueryList.do?prpLcertifyCertifyNo=" + oRecord.getData("keyIn") + "&nodeType=certi&editType=SHOW&riskCode=" + oRecord.getData("riskCode");
				}
				if ("compp"==nodeType) { //计算书信息
					strInfoLink = "/claim/compensate/compensateFinishQueryList.do?prpLcompensateCompensateNo=" + oRecord.getData("keyOut") + "&editType=SHOW&riskCode=" + oRecord.getData("riskCode");
				}
				if ("veric"==nodeType) { //核赔信息
					strInfoLink = "/claim/CommonCheckTask.do?iFlowID=" + oRecord.getData("iFlowID") + "&iLogNo=" + oRecord.getData("iLogNo") + "&EditType=query&HandType=22&iRiskCode=" + oRecord.getData("riskCode") + "&BusinessNo=" + oRecord.getData("businessNo") + "&iBusinessType=" + oRecord.getData("businessType") + "&iBusinessNo=" + oRecord.getData("iBusinessNo") + "&iModelNo=" + oRecord.getData("iModelNo") + "&iNodeNo=" + oRecord.getData("iNodeNo");
				}
				if ("speci"==nodeType) { //预赔信息
					if ("7"==typeFlag || "8"==typeFlag || "5"==typeFlag) {
						strInfoLink = "/claim/specailCase/prepayFinishQueryList.do?prpLprepayPrepayNo=" + oRecord.getData("keyOut") + "&caseType=" + typeFlag + flowStr;
					} else {
						strInfoLink = "/claim/compensate/compensateFinishQueryList.do?prpLcompensateCompensateNo=" + oRecord.getData("keyOut") + "&caseType=" + typeFlag + flowStr;
					}
				}
				if ("endca"==nodeType) { //结案信息
					strInfoLink = "/claim/endcase/endcaseFinishQueryList.do?prpLendcaseEndcaseNo=" + oRecord.getData("keyIn") + flowStr;
				}
				if ("cance"==nodeType) { //已注销信息
					strInfoLink = "/claim/claimFinishQueryList.do?prpLclaimClaimNo=" + oRecord.getData("keyIn") + flowStr;
				}
				elCell.innerHTML = "<a href=\""+ strInfoLink + "\">"+ oRecord.getData("registNo")+"</a>";
			}
		} else if(oColumn.key =="status") {
			var statusName = "";
			var claimNodeCode = fm.claimNodeCode.value;
			if ("noendca"==claimNodeCode && "endca"==nodeType && "4"==status) {
				statusName = "重開賠案";
			} else {
				if ("0" == status) {
					statusName = "待處理";
				} else if ("1" == status) {
					statusName = "未處理"
				} else if ("2" == status) {
					statusName = "正在處理";
				} else if ("3" == status) {
					statusName = "回退處理";
				} else if ("4" == status) {
					statusName = "已處理";
				} else if ("5" == status) {
					statusName = "已回退";
				} else if ("6" == status) {
					statusName = "已撤銷";
				} else if ("9" == status) {
					statusName = "通賠待接收";
				}
			}
			elCell.innerHTML = statusName;
		} else if(oColumn.key =="serialNo"){
		    elCell.innerHTML = serialNo++;
		} else {
			elCell.innerHTML = oData;
		}
	};
	contentColumnHeaders =[
		{key:"serialNo",label:"<s:text name="db.prpDrate.serialNo" />",width:"40em",sortable:false,formatter:YAHOO.widget.DataTable.formatLink},
		{key:"businessNo",label:"<s:text name="sendUndwrt.BusinessNumber" />",width:"40em",sortable:false,formatter:YAHOO.widget.DataTable.formatLink},<%--业务号--%>
		{key:"policyNo",label:"<s:text name="db.prpCmain.policyNo" />",width:"40em",sortable:false},<%--保单号--%>
		{key:"riskCodeName",label:"<s:text name="query.xianzhongName" />",width:"40em",sortable:false},<%--险种名称--%>
		{key:"insuredName",label:"<s:text name="db.prpLregist.insuredName" />",width:"40em",sortable:false},<%--被保险人--%>
		{key:"handlerName",label:"<s:text name="workflow.dealPerson" />",width:"40em",sortable:false},<%--处理人员--%>
		{key:"nodeName",label:"<s:text name="check.claimType" />",width:"40em",sortable:false},<%--赔案类型--%>
		{key:"status",label:"<s:text name="quickCase.peiState" />",width:"40em",sortable:false,formatter:YAHOO.widget.DataTable.formatLink}<%--赔案状态--%>
		];
}
/*
 *@description:可以批次切分活动组别结果集 
 *@param  pageNo，pageSize
 *@return  活动组别结果集
 *@author 中科软
*/
function executeQuery(pageNo,pageSize,field){
	// reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	//增加!!field判断，如果field为undefined，则!!field为false
	if(!!field){
		field.disabled = true;
	}
	serialNo = 1;
	if(isNaN(parseInt(pageNo))){ 
		pageNo = 1;
	}
	if(isNaN(parseInt(pageSize))){
		pageSize = 10;
	} 
	var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);
	var myDataSource = new YAHOO.util.DataSource("${ctx}/integrated/integratedQuery.do?t=new Date()");
	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.connMethodPost = true; 
	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: [{key:"id.flowID"},{key:"id.logNo"},"nodeStatus","riskCode","riskCodeName","nodeType","nodeName","businessNo","policyNo","modelNo","nodeNo","insuredName","handlerCode","handlerName","typeFlag","registNo","handleTime","keyIn","keyOut","lossItemCode","lossItemName","iFlowID","iModelNo","iNodeNo","businessType","iBusinessNo","iLogNo"],
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

//init on load
YAHOO.util.Event.addListener(window,'load',init);
/*
 *@description:弹出页面
 *@param  title，url
 *@return  
 *@author 中科软
*/
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
function submitForm(field) {
	if (fm.claimNodeCode.value=="") {
		alert("請選擇賠案類型！");
		return false;
	}
	executeQuery(1,10,field);
}
</script>
</html>
