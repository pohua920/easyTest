<!--
****************************************************************************
* DESC       ：殘餘物審核、修改查詢輸入界面
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
<title>殘餘物審核查詢輸入界面</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<%@ include file="/common/meta_js.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<script language="javascript">
	function submitForm() {
		fm.submit();//提交
	}
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
					<%--保單號碼 --%>
					:
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
					<s:text name='db.prpLclaim.claimNo' />
					<%--立案号码 --%>
					:
				</td>
				<td class='input'>
					<select class=tag name="ClaimNoSign">
						<option value="=">=</option>
					</select>
					<input type=text name="ClaimNo" class="query">
				</td>
				<td class='title'>
					<s:text name='prpcmain.printNo' />
					<%--強制證號碼 --%>
					:
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
					<%--牌照號碼 --%>
					:
				</td>
				<td class='input'>
					<select class=tag name="LicenseNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="LicenseNo" class="query">
				</td>
				<td class='title'>
					<s:text name='db.prpCmain.insuredName' />
					<%--被保险人名称 --%>
					:
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
					<s:text name='compensate.computeBookNum' />
					:
				</td>
				<%--計算書號 --%>
				<td class='input'>
					<select class=tag name="CompensateNoSign">
						<option value="=">=</option>
					</select>
					<input type=text name="CompensateNo" class="query">
				</td>
				<td class='title'></td>
				<td class='input'></td>
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
		<input type="hidden" name="nodeType" value="${param.nodeType }">
	</form>
	<script language="javascript">
		var serialNo = 1;
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
					elCell.innerHTML = serialNo++;
				} else if (oColumn.key == "remnantDate") {
					if (oData != null) {
						var date = new Date(oData.time);
						elCell.innerHTML = formatDate(date, 'yyy-MM-dd');
					}
				} else if (oColumn.key == "compensateNo") {
					if ("${param.editType}" == "undwrtquery") {
						elCell.innerHTML = "<a href='${ctx}/remnantBeforeEdit.do?editType=undwrt&claimNo="
								+ oRecord.getData("claimNo")
								+ "&policyNo="
								+ oRecord.getData("policyNo")
								+ "&comName="
								+ oRecord.getData("comName")
								+ "&compensateNo="
								+ oRecord.getData("compensateNo")
								+ "&swfLogFlowID="+oRecord.getData("flowID")+"&swfLogLogNo="+oRecord.getData("logNo")
								+ "'>"
								+ oRecord.getData("compensateNo") 
								+ "</a>"
					} else if ("${param.editType}" == "editquery") {
						elCell.innerHTML = "<a href='${ctx}/remnantBeforeEdit.do?editType=edit&claimNo="
								+ oRecord.getData("claimNo")
								+ "&policyNo="
								+ oRecord.getData("policyNo")
								+ "&comName="
								+ oRecord.getData("comName")
								+ "&compensateNo="
								+ oRecord.getData("compensateNo")
								+ "&swfLogFlowID="+oRecord.getData("flowID")+"&swfLogLogNo="+oRecord.getData("logNo")
								+ "'>"
								+ oRecord.getData("compensateNo") + "</a>"
					}
				} else if (oColumn.key == "operate") {
					if ("${param.editType}" == "undwrtquery") {
						elCell.innerHTML = "<a href='${ctx}/remnantBeforeEdit.do?editType=undwrt&claimNo="
								+ oRecord.getData("claimNo")
								+ "&policyNo="
								+ oRecord.getData("policyNo")
								+ "&comName="
								+ oRecord.getData("comName")
								+ "&compensateNo="
								+ oRecord.getData("compensateNo")
								+ "&swfLogFlowID="+oRecord.getData("flowID")+"&swfLogLogNo="+oRecord.getData("logNo")
								+ "'>"
								+ "<img name=buttonDistribute  src='/claim/images/butDeal.gif' border='0'    hspace='5'  alt='选择处理'></a>"
					} else if ("${param.editType}" == "editquery") {
						elCell.innerHTML = "<a href='${ctx}/remnantBeforeEdit.do?editType=edit&claimNo="
								+ oRecord.getData("claimNo")
								+ "&policyNo="
								+ oRecord.getData("policyNo")
								+ "&comName="
								+ oRecord.getData("comName")
								+ "&compensateNo="
								+ oRecord.getData("compensateNo")
								+ "&swfLogFlowID="+oRecord.getData("flowID")+"&swfLogLogNo="+oRecord.getData("logNo")
								+ "'>"
								+ "<img name=buttonDistribute  src='/claim/images/butDeal.gif' border='0'    hspace='5'  alt='选择处理'></a>"
					}
				} else if (oColumn.key == "handleStatus") {
					elCell.innerHTML = "<s:text name='common.status.untreated'/>";
				}else if (oColumn.key == "underWriteFlag") {
					var underWriteStr = "";
					if(oData=="0") {
						underWriteStr = "待處理";
					} else if(oData=="1"){
						underWriteStr = "已審核";
					} else if(oData=="3"){
						underWriteStr = "下發修改";
					}
					elCell.innerHTML = underWriteStr;
				} else {
					elCell.innerHTML = oData;
				}
			};
	        contentColumnHeaders =[
	                    			{key:"serialNo",label:"<s:text name='db.prpDrate.serialNo'/>",width:"40em",formatter:YAHOO.widget.DataTable.formatLink},                  
	                    			{key:"compensateNo",label:"<s:text name='compensate.computeBookNum'/>",width:"40em",formatter:YAHOO.widget.DataTable.formatLink},
	                    			{key:"handleStatus",label:"<s:text name='schedule.rrocesseStatus'/>",width:"40em",formatter:YAHOO.widget.DataTable.formatLink},
		                   			{key:"remnantDate",label:"<s:text name='prplremnant.remnantDate'/>",width:"40em",formatter:YAHOO.widget.DataTable.formatLink},
		                   			{key:"comName",label:"<s:text name='prplremnant.comName'/>",width:"40em"},
		                   			{key:"riskCodeName",label:"<s:text name='db.prpDdbs.riskCode'/>",width:"40em"},
		                   			{key:"nodeName",label:"審核進度",width:"40em",formatter:YAHOO.widget.DataTable.formatLink},
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
			serialNo = 1;
			if (isNaN(parseInt(pageNo))) {
				pageNo = 1;
			}
			if (isNaN(parseInt(pageSize))) {
				pageSize = 10;
			}
			var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);
			if ("${param.editType}" == "editquery") {
				var myDataSource = new YAHOO.util.DataSource("${ctx}/remnantResultQuery.do?editType=editquery");
			} else if ("${param.editType}" == "undwrtquery") {
				var myDataSource = new YAHOO.util.DataSource("${ctx}/remnantResultQuery.do?editType=undwrtquery");
			}
			myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
			myDataSource.connMethodPost = true;
			myDataSource.responseSchema = {
				resultsList : "data",
				fields : [ "claimNo", "comCode", "riskCodeName", "comName",
						"policyNo", "compensateNo", "remnantDate","underWriteFlag","flowID","logNo","nodeName","flowTime"],
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