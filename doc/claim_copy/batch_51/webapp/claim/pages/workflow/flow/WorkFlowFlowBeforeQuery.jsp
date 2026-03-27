<%--
****************************************************************************
* DESC       ：工作流查询条件输入页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-03-07
* 修改模糊查询为右模糊查询
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<title>工作流查詢訊息</title>
<%-- 公用函数 --%>
<script src="${ctx }/pages/workflow/flow/js/WorkFlowFlowBeforeQuery.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
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
		        if (oColumn.key == "businessNo") {
		        	var businessNo = oData;
		        	if(businessNo.charAt(0)=='R'){
		        		businessNo = oRecord.getData("registNo");
		        	}
		            elCell.innerHTML = "<a  href=\"${ctx}/workflow/swfFlowBeforeQuery.do?swfLogFlowID=" + oRecord.getData("id.flowID") + "\">" + businessNo + "</a>";
		        } else if (oColumn.key == "relatePolicyList") {
		            if (oData != null) {
		                if (oData.length >= 2) { <%--交强险保单换行显示--%>
		                        elCell.innerHTML = oData[0] + "<br/>" + oData[1];
		                } else {
		                    elCell.innerHTML = oData;
		                }
		            }
		        } else if ("submitTime" == oColumn.key) {
		            if (oData == null) {
		                elCell.innerHTML = "";
		            } else {
		                elCell.innerHTML = formatDate(oData, "yyy-MM-dd HH:mm:ss");
		            }
		        } else if ("flowtype" == oColumn.key) {
		        	var businessNo = oRecord.getData("businessNo");
		        	if(businessNo.charAt(0)=='R'){
		                elCell.innerHTML = "追償流程";
		            } else {
		            	elCell.innerHTML = "理賠流程";
		            }
		        } else {
		            if (oData == null) {
		                elCell.innerHTML = "";
		            } else {
		                elCell.innerHTML = oData;
		            }
		        }
		    }; 
			contentColumnHeaders =[
				 {key:"businessNo",label:"<s:text name='db.prpLregist.registNo'/>",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
				 {key:"relatePolicyList",label:"<s:text name='db.prpCmain.policyNo'/>",width:"40em",formatter:YAHOO.widget.DataTable.formatLink},
				 {key:"insuredName",label:"<s:text name='workflow.applicantName'/>",width:"40em"},
				 {key:"lossItemName",label:"<s:text name='compensate.underly'/>",width:"40em"},
				 {key:"comName",label:"受理單位",width:"40em",sortable:true},
				 {key:"handlerName",label:"<s:text name='workflow.dealPerson'/>",width:"40em"},
				 {key:"submitTime",label:"<s:text name='db.prpLregist.reportDate'/>",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
				 {key:"riskCodeName",label:"<s:text name='query.xianzhongName'/>",width:"40em",sortable:true},
				 {key:"flowtype",label:"流程類別",width:"40em",formatter:YAHOO.widget.DataTable.formatLink},
				 {key:"otherFlag",label:"<s:text name='prompt.flow.cancel'/>",width:"40em"}
				];
		}
		<%--
		於国际化对应的信息，
		contentColumnHeaders =[
				 {key:"businessNo",label:"报案号",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
				 {key:"relatePolicyList",label:"保单号",width:"40em",formatter:YAHOO.widget.DataTable.formatLink},
				 {key:"insuredName",label:"被保人名称",width:"40em"},
				 {key:"lossItemName",label:"标的",width:"40em"},
				 {key:"handlerName",label:"处理人员",width:"40em"},
				 {key:"submitTime",label:"报案日期",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
				 {key:"riskCodeName",label:"险种名称",width:"40em",sortable:true},
				 {key:"otherFlag",label:"註銷状态",width:"40em"}
				];
		--%>
		/*
		 *@description:可以批次切分活动组别结果集 
		 *@param  pageNo，pageSize
		 *@return  活动组别结果集
		 *@author 中科软
		*/
		function executeQuery(pageNo, pageSize, field) {
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
		    var myDataSource = new YAHOO.util.DataSource("${ctx}/workflow/swfFlowQuery.do");
		    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		    myDataSource.connMethodPost = true;
		    myDataSource.responseSchema = {
		        resultsList: "data",
		        fields: [{
		            key: "id.flowID"
		        }, "businessNo", "relatePolicyList", "insuredName", "lossItemName", "comName", "handlerName", "submitTime", "riskCodeName", "otherFlag" , "registNo"],
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
</head>
<body class="yui-skin-sam">
	<form name="fm" action="${ctx }/swfFlowQuery.do" method="post" onsubmit="return validateForm(this);">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="title.sendUndwrtBeforeEdit.QueryingIformation" />
					<%--工作流查询信息 --%>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.registNo" />：
					<%--报案号码 --%>
				</td>
				<td class='input'>
					<select name="RegistNoSign" class=tag>
						<option value="=">=</option>
					</select>
					<input type=text name="prpLregistRegistNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpCmain.policyNo" />：
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
					<s:text name="db.prpLclaim.claimNo" />：
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
					<s:text name="db.prpLregist.licenseNo" />：
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
					<s:text name="db.prpCmain.insuredName" />：
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
					<s:text name="workflow.oaFlowState" />:
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
				<td class='title'><s:text name="db.prpCmain.visaCodeBI" />：<%-- 任意保險卡號    --%></td>
				<td class='input'>
					<select name="visaCodeBISign" class=tag>
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="prpLregistVisaCodeBI" class="query">
				</td>
				<td class='title' style="width: 10%">
				</td>
				<td class='input' style="width: 25%;">
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="executeQuery(1,10,this);">
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<%--
						"="符号，必须精确查询。<br>
						"=*"符号，前匹配後模糊的查询、被保险人名称根据前2位名称模糊查询。
					--%>
					<s:text name="prompt.schedule.query1" />
					<br>
					<s:text name="workflow.query4" />
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
		<input type="hidden" name="editType" value="WorkFlow">
		<input type="hidden" name="taskCodeC" value="lplc">
	</form>
</body>
</html>