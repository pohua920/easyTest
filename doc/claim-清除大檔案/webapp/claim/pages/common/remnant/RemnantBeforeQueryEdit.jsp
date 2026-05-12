<!--
****************************************************************************
* DESC       ：殘餘物處理查詢輸入界面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2013-10-11
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
							zhangshi		20080512				修改模糊查询为右模糊查询
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<title><s:text name='prplremnant.editquery.page' /></title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<%@ include file="/common/meta_js.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<script language="javascript">
	//案件状态标志处理
	function submitForm() {
		fm.submit();//提交
	}
//-->
</script>
</head>
<body onload="initPage();" class="yui-skin-sam">
	<form name="fm" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name='workflow.query.info' />
				</td>
			</tr>
			<%--工作流查詢信息 --%>
			<tr>
				<td class='title'>
					<s:text name='prompt.queRegist.RegistNo' />
					<%--备案号码 --%>
					:
				</td>
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
					</select>
					<input type=text name="RegistNo" class="query">
				</td>
				<td class='title'>
					<s:text name='prompt.queRegist.PolicyNo' />
					:
				</td>
				<%--保單號碼 --%>
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="=">=</option>
					</select>
					<input type=text name="PolicyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name='db.prpLclaim.claimNo' />
					:
				</td>
				<%--立案号码 --%>
				<td class='input'>
					<select class=tag name="ClaimNoSign">
						<option value="=">=</option>
					</select>
					<input type=text name="ClaimNo" class="query">
				</td>
				<td class='title'>
					<s:text name='prpcmain.printNo' />
					:
					<%--強制證號碼 --%>
				</td>
				<td class='input'>
					<select class=tag name="CompelLicenseNoSign">
						<option value="=">=</option>
					</select>
					<input type=text name="CompelLicenseNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name='db.prpCitem_car.licenseNo' />
					:
				</td>
				<%--牌照號碼 --%>
				<td class='input'>
					<select class=tag name="LicenseNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="LicenseNo" class="query">
				</td>
				<td class='title'>
					<s:text name='db.prpCmain.insuredName' />
					:
					<%--被保险人名称 --%>
				</td>
				<td class='input'>
					<select class=tag name="InsuredNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" class="query" name="InsuredName">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name='schedule.rrocesseStatus' />
					:
				</td>
				<%--處理狀態 --%>
				<td class='input'>
					<input id="Radio1" type="radio" name="status" value="0" checked="checked">
					<s:text name='common.status.untreated' />
					<%--未处理 --%>
					<input type="radio" name="status" value="1">
					<s:text name='common.status.treated' />
					<%--已處理 --%>
				</td>
				<%--理算是否標記有追償 --%>
				<td class='title'>理算是否標記有殘餘物
				: </td>
				<td class='input'>
					<input type="radio" checked="checked" name="remnantFlag" value="1">是
					<input type="radio" name="remnantFlag" value="0">否
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value'/>" onClick="executeQuery(1,10,this);">
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
		<!---<input type="hidden" name="editType" value="SHOW">--->
		<input type="hidden" name="nodeType" value="${param.nodeType }">
	</form>
	<script language="javascript">
		var serialNo = 0;
		var isFirstLoad = true;
		var contentDataTable;
		var contentColumnHeaders;
		YAHOO.namespace("query.container");
	<%-- 
		* @description: 初始化查询结果页面 
		* @param varSigns无 
		* @return boolean活动组别结果集
		* @author中科软
		--%>
		function init() {
			YAHOO.widget.DataTable.formatLink = function(elCell, oRecord,
					oColumn, oData) {
				if (oColumn.key == "serialNo") {
					elCell.innerHTML = ++serialNo;
				} else if (oColumn.key == "remnantDate") {
					var status = $("#Radio1").is(":checked") ? "0" : "1";
					if (status == "0") { //未处理则显示当前日期
						var myDate = new Date();
						elCell.innerHTML = formatDate(myDate, 'yyy-MM-dd');
					} else {
						if (oData != null) {
							var myDate = new Date(oData.time);
							elCell.innerHTML = formatDate(myDate, 'yyy-MM-dd');
						}
					}
				} else if (oColumn.key == "claimNo") {
					//var status = $("#Radio1").is(":checked") ? "0" : "1";
					//if (status == "0") {
						elCell.innerHTML = "<a href='${ctx}/remnantBeforeEdit.do?editType=add&claimNo="
								+ oRecord.getData("claimNo")
								+ "&policyNo="
								+ oRecord.getData("policyNo")
								+ "&comName="
								+ oRecord.getData("comName")
								+ "'>"
								+ oRecord.getData("claimNo") + "</a>"
					//} else {
					//	elCell.innerHTML = oData;
					//}
				} else if (oColumn.key == "operate") {
					var status = $("#Radio1").is(":checked") ? "0" : "1";
					if (status == "0") {
						elCell.innerHTML = "<a href='${ctx}/remnantBeforeEdit.do?editType=add&claimNo="
								+ oRecord.getData("claimNo")
								+ "&policyNo="
								+ oRecord.getData("policyNo")
								+ "&comName="
								+ oRecord.getData("comName")
								+ "'>"
								+ "<img name=buttonDistribute  src='/claim/images/butDeal.gif' border='0'    hspace='5'  alt='添加残余物'></a>"
					}
				} else if (oColumn.key == "handleStatus") {
					elCell.innerHTML = $("#Radio1").is(":checked") ? "<s:text name='common.status.untreated'/>"
							: "<s:text name='common.status.treated'/>"; //未处理 已处理
				} else {
					elCell.innerHTML = oData;
				}
			};
	        contentColumnHeaders =[
	                   			 {key:"serialNo",label:"<s:text name='db.prpDrate.serialNo'/>",width:"40em",formatter:YAHOO.widget.DataTable.formatLink},                  
	                   			{key:"claimNo",label:"<s:text name='check.claimNum'/>",width:"40em",formatter:YAHOO.widget.DataTable.formatLink},//赔案号码
	                   			{key:"handleStatus",label:"<s:text name='schedule.rrocesseStatus'/>",width:"40em",formatter:YAHOO.widget.DataTable.formatLink},//处理状态
	                  			{key:"remnantDate",label:"<s:text name='prplremnant.remnantDate'/>",width:"40em",formatter:YAHOO.widget.DataTable.formatLink},
	                  			{key:"comName",label:"<s:text name='prplremnant.comName'/>",width:"40em"},
	                  			{key:"riskCodeName",label:"<s:text name='db.prpDdbs.riskCode'/>",width:"40em"},
	                  			{key:"operate",label:"<s:text name='certify.operate'/>",width:"40em",formatter:YAHOO.widget.DataTable.formatLink}
	                              ];
		}
	<%-- 
		* @description: 可以批次切分活动组别结果集 
		* @param pageNo， pageSize 
		* @return活动组别结果集
		* @author中科软
		--%>
		function executeQuery(pageNo, pageSize,field) {
			// reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
			//增加!!field判断，如果field为undefined，则!!field为false
			if(!!field){
				field.disabled = true;
			}
			serialNo = 0;
			if (isNaN(parseInt(pageNo))) {
				pageNo = 1;
			}
			if (isNaN(parseInt(pageSize))) {
				pageSize = 10;
			}
			var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);
			var myDataSource = new YAHOO.util.DataSource(
					"${ctx}/remnantResultQuery.do?editType=addquery");
			myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
			myDataSource.connMethodPost = true;
			myDataSource.responseSchema = {
				resultsList : "data",
				fields : [ "claimNo", "comCode", "riskCodeName", "comName",
						"policyNo", "remnantDate" ],
				metaFields : {
					totalRecords : "totalRecords"
				}
			};
			myDataSource.subscribe("responseParseEvent",
					SINOSOFT.util.navigation);
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
						contentDataTable.onDataReturnReplaceRows,
						contentDataTable);
			}
			document.getElementById("tableResullt").style.display = "";
			//增加!!field判断，如果field为undefined，则!!field为false
			if(!!field){
			// reason:当次查询结束，按钮恢复
			field.disabled = false;
			}
		}

		//init on load
		YAHOO.util.Event.addListener(window, 'load', init);
	<%-- 
		* @description: 弹出页面 
		* @param title， url 
		* @return 
		* @author中科软
		--%>
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
</body>
</html>