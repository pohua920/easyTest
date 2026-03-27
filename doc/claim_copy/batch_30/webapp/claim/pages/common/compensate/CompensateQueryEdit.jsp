<%--
****************************************************************************
* DESC       ：理算查询条件输入页面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-06-07
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
        zhangshi  20130201   修改*操作符为=*将like '%%'改为左%右匹配查询
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%
 String riskType = request.getParameter("type");
 if ((riskType == null) || riskType.equals("")) {
  riskType = "notacci";
 }
 pageContext.setAttribute("riskType",riskType);
%>
<html>
 <head>
  <%@include file="/common/i18njs.jsp"%>
  <%@include file="/common/meta_css.jsp"%>
  <%@include file="/common/meta_js.jsp"%>
  <title><s:text name="title.compensateBeforeEdit.queryCompensate" />
  </title>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  <script language="javascript">
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
  	var sumPaid = "總賠付金額(折" + CURRENCYINFO.LOCAL_CURRENCY + ")";
  	YAHOO.widget.DataTable.formatLink = function (elCell, oRecord, oColumn, oData) {
  		if (oColumn.key == "compensateNo") {
  			elCell.innerHTML = "<a href=\"${ctx}/compensate/compensateFinishQueryList.do?prpLcompensateCompensateNo=" + oData + "&editType=" + oRecord.getData("editType") + "&riskCode=" + oRecord.getData("riskCode") + "&nodeType=${param.nodeType}" + "\">" + oData + "</a>";
  		} else if (oColumn.key == "status") {
  			if (oData == "1") {
  				elCell.innerHTML = "未處理";
  			} else if (oData == "2") {
  				elCell.innerHTML = "正處理";
  			} else if (oData == "3") {
  				elCell.innerHTML = "已處理";
  			} else if (oData == "4") {
  				elCell.innerHTML = "已提交";
  			} else if (oData == "5") {
  				elCell.innerHTML = "已撤銷";
  			} else {
  				elCell.innerHTML;
  			}
  		} else if (oColumn.key == "underWriteFlag") {
  			if (oData == "0") {
  				elCell.innerHTML = "初始值";
  			} else if (oData == "1") {
  				elCell.innerHTML = "通過";
  			} else if (oData == "2") {
  				elCell.innerHTML = "不通過";
  			} else if (oData == "3") {
  				elCell.innerHTML = "無需核賠";
  			} else if (oData == "9") {
  				elCell.innerHTML = "待核賠";
  			} else {
  				elCell.innerHTML;
  			}
  		} else {
  			elCell.innerHTML = oData;
  		}
  	};
    contentColumnHeaders =[
                           {key:"status",label:"案件狀態",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
                           {key:"underWriteFlag",label:"核賠標誌",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
                           {key:"compensateNo",label:"賠款計算書號",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
                           {key:"claimNo",label:"賠案號碼",width:"40em",sortable:true},
                           {key:"policyNo",label:"保單號碼",width:"40em",sortable:true},
                           {key:"sumPaid",label:sumPaid,width:"40em",sortable:true}
                          ];
  }
  /*
   *@description:可以批次切分活动组别结果集
   *@param  pageNo，pageSize
   *@return  活动组别结果集
   *@author 中科软
   */

  function executeQuery(pageNo, pageSize, field) {
  	//add by caozhigang 20090324 start 解决性能问题，控制查询条件
  	if ((fm.InsuredNameSign.value == "=" && fm.InsuredName.value.length > 0) || (fm.LicenseNoSign.value == "=" && fm.LicenseNo.value.length > 0) || (fm.RegistNoSign.value == "=" && fm.RegistNo.value.length > 0) || (fm.PolicyNoSign.value == "=" && fm.PolicyNo.value.length > 0) || (fm.ClaimNoSign.value == "=" && fm.ClaimNo.value.length > 0) || (fm.CompensateNoSign.value == "=" && fm.CompensateNo.value.length > 0)) {
  		//输入了一个条件，可以查
  	} else if ((fm.RegistNoSign.value == "=*" && fm.RegistNo.value.length > 8) || (fm.PolicyNoSign.value == "=*" && fm.PolicyNo.value.length > 8) || (fm.ClaimNoSign.value == "=*" && fm.ClaimNo.value.length > 8) || (fm.CompensateNoSign.value == "=*" && fm.CompensateNo.value.length > 8)) {
  		if ("D" == getClassCodeType(fm.RegistNo.value.substr(1, 2)) || "D" == getClassCodeType(fm.PolicyNo.value.substr(1, 2)) || "D" == getClassCodeType(fm.ClaimNo.value.substr(1, 2)) || "D" == getClassCodeType(fm.CompensateNo.value.substr(1, 2))) {
  			alert("車險必須精確查詢！");
  			return false;
  		} else {
  			//非车险可以前9位模糊查询
  		}
  	} else {
  		alert("車險必須輸入計算書號、立案號碼、備案號碼、保單號碼、牌照號碼、被保險人其中一項精確查詢！\n 非車險可以用計算書號、立案號碼、備案號碼或者保單號碼的前9位進行模糊查詢！");
  		return false;
  	}
  	// reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
  	//增加!!field判断，如果field为undefined，则!!field为false
  	if ( !! field) {
  		field.disabled = true;
  	}
  	//add by caozhigang 20090324 end 解决性能问题，控制查询条件
  	if (isNaN(parseInt(pageNo))) {
  		pageNo = 1;
  	}
  	if (isNaN(parseInt(pageSize))) {
  		pageSize = 10;
  	}
  	var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);
  	var myDataSource = new YAHOO.util.DataSource("${ctx}/compensate/compensateQuery.do");
  	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
  	myDataSource.connMethodPost = true;
  	myDataSource.responseSchema = {
  		resultsList: "data",
  		fields: ["status", "compensateNo", "claimNo", "policyNo", "sumPaid", "underWriteFlag", "riskCode", "editType"],
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
 </head>
<body class="yui-skin-sam">
	<form name="fm" action="${ctx}/compensate/compensateQuery.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<c:choose>
					<c:when test="${pageScope.riskType=='acci'}">
						<td colspan=4 class="formtitle">
							<s:text name="compensate.queryCheckedInformation" />
						</td>
						<!-- 查询审核信息 -->
					</c:when>
					<c:otherwise>
						<td colspan=4 class="formtitle">
							<s:text name="title.compensate.queryAdjustInformation" />
						</td>
						<!-- 查询理算信息 -->
					</c:otherwise>
				</c:choose>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLcfee.compensateNo" />
					：
				</td>
				<!-- 赔款计算书号 -->
				<td class='input'>
					<select class=tag name="CompensateNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="CompensateNo" class="query">
				</td>
				<td class='title'>
					<s:text name="check.claimNum" />
					：
				</td>
				<!-- 赔案号 -->
				<td class='input'>
					<select class=tag name="ClaimNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="ClaimNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="prompt.queRegist.PolicyNo" />
					：
				</td>
				<!-- 保单号 -->
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="PolicyNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.operatedate" />：
				</td>
				<!-- 操作时间 -->
				<td class='input'>
					<select class="tag" name="OperateDateSign">
						<option value="=">=&nbsp;</option>
						<option value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value=">=">&gt;=</option>
						<option value="<=" selected>&lt;=</option>
					</select>
					<rc:rcDate name="OperateDate" defaultValue="0" style="width: 60%"/>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.licenseNo" />
					：
				</td>
				<td class='input'>
					<select class=tag name="LicenseNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="LicenseNo" class="query">
				</td>
				<%--报案查询增加被保险人查询条件--%>
				<td class='title'>
					<s:text name="db.prpLCMain.insuredName" />
					:
				</td>
				<!-- 被保险人名称 -->
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
					<s:text name="db.prpLclaimStatus.status" />
					：
				</td>
				<!-- 案件状态 -->
				<td class='input' colspan=3>
					<input type="checkbox" name="status" value="1">
					<s:text name="common.status.untreated" />
					<!-- 未处理 -->
					<input type="checkbox" name="status" value="2">
					<s:text name="common.status.intreating" />
					<!-- 正处理 -->
					<input type="checkbox" name="status" value="4">
					<s:text name="common.status.submited" />
					<!-- 已提交 -->
					<input type="checkbox" name="status" value="5">
					<s:text name="common.status.revoked" />
					<!-- 已撤消 -->
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLprepay.underWriteFlag" />
					：
				</td>
				<!-- 核赔标志 -->
				<td class='input' colspan=3>
					<input type="checkbox" name="UnderWriteFlag" value="0">
					<s:text name="compensate.initValue" />
					<!-- 初始值 -->
					<input type="checkbox" name="UnderWriteFlag" value="1">
					<s:text name="compensate.pass" />
					<!-- 通过 -->
					<input type="checkbox" name="UnderWriteFlag" value="2">
					<s:text name="compensate.notPass" />
					<!-- 不通过 -->
					<input type="checkbox" name="UnderWriteFlag" value="3">
					<s:text name="compensate.withoutHePei" />
					<!-- 无需核赔 -->
					<input type="checkbox" name="UnderWriteFlag" value="9">
					<s:text name="compensate.stayHePei" />
					<!-- 待核赔 -->
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="prompt.queRegist.RegistNo" />
					：
				</td>
				<!-- 报案号 -->
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="RegistNo" class="query">
				</td>
				<td class='title'></td>
				<td class='input' colspan="3"></td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<br>
					<!--  ="符号，必须精确查询。 -->
					<s:text name="prompt.schedule.query2" />
					<br>
					<!-- "=*"符号，前匹配後模糊的查询。 -->
					<s:text name="compensate.queryResult1" />
					<br>
					<!-- 车险必须输入计算书号、立案号、报案号、保单号、车牌号、被保险人其中一项精确查询！ -->
					<s:text name="compensate.queryResult2" />
					<!-- 非车险可以用计算书号、立案号、报案号或者保单号的前9位进行模糊查询！ -->
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value' />" onclick="executeQuery(1,10,this);">
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
		<input type="hidden" name="riskType" value="<%=riskType%>">
		<input type="hidden" name="editType" value="SHOW">
		<input type="hidden" name="nodeType" value="<%=request.getParameter("nodeType")%>">
	</form>
</body>
</html>