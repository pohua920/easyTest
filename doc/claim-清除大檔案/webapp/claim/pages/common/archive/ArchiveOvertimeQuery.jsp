<%--
****************************************************************************
* DESC       ：实体资料调阅超时查询页面
* AUTHOR     ： liuwei
* CREATEDATE ： 2011-01-05
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>

<html>
<head>
    <title><s:text name="archive.entityDataReadOuttimeQuery"/></title><!-- 实体资料调阅超时查询 -->
    <%-- 页面样式  --%>
    <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
    <%-- 公用函数 --%>
    <script src="/claim/common/js/Common.js"></script>
    <script type="text/javascript">
    var isFirstLoad = true;
    var contentDataTable;
    var contentColumnHeaders;
    YAHOO.namespace("query.container");

    function init() {
    	YAHOO.widget.DataTable.formatLink = function (elCell, oRecord, oColumn, oData) {
    		if (oColumn.key == "applicantName") {
    			if (oData == null) {
    				elCell.innerHTML = "";
    			} else {
    				elCell.innerHTML = oData;
    			}
    		} else if (oColumn.key == "insuredName") {
    			if (oData == null) {
    				elCell.innerHTML = "";
    			} else {
    				elCell.innerHTML = oData;
    			}
    		} else if (oColumn.key == "startReviewDate") {
    			if (oData != null) {
    				var date = new Date(oData.time);
    				elCell.innerHTML = formatDate(date, 'yyy-MM-dd');
    			}
    		} else if (oColumn.key == "estimateReturnDate") {
    			if (oData != null) {
    				var date = new Date(oData.time);
    				elCell.innerHTML = formatDate(date, 'yyy-MM-dd');
    			}
    		} else {
    			elCell.innerHTML = oData;
    		}

    	};
    	contentColumnHeaders =[
    		               		{key:"claimNo",label:"<s:text name="check.claimNum"/>",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--赔案号--%>
    	                        {key:"policyNo",label:"<s:text name="prompt.queRegist.PolicyNo"/>",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--保单号--%>
    	                        {key:"insuredName",label:"<s:text name="db.prpCmain.insuredName"/>",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--被保险人名称--%>
    	                        {key:"applicantName",label:"<s:text name="archive.applicantName"/>",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--申请人名称--%>
    	                        {key:"startReviewDate",label:"<s:text name="archive.readData"/>",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--调阅日期--%>
    	                        {key:"estimateReturnDate",label:"<s:text name="archive.expectedReturnDateResult"/>",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}<%--预计归还日期--%>
    		               		];
    }
    YAHOO.util.Event.addListener(window, 'load', init);

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

    function executeQuery(pageNo, pageSize) {
    	//init();
    	if (validateForm(fm)) {
    		if (isNaN(parseInt(pageNo))) {
    			pageNo = 1;
    		}
    		if (isNaN(parseInt(pageSize))) {
    			pageSize = 10;
    		}
    		var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);
    		var myDataSource = new YAHOO.util.DataSource("${ctx}/archive/archiveQuery.do");
    		myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
    		myDataSource.connMethodPost = true;
    		myDataSource.responseSchema = {
    			resultsList: "data",
    			fields: ["claimNo", "policyNo", "insuredName", "applicantName", "startReviewDate", "estimateReturnDate"],
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
    	}
    }
</script>
</head>

<body  onload="initPage();" class="yui-skin-sam">
    <form name="fm"  method="post">
        <table width="100%" border="0" align="center" cellpadding="5" cellspacing="1"  class="common">
            <tr>
                <td colspan="4" class="formtitle"><s:text name="archive.entityDataReadOuttimeQuery"/></td><!-- 实体资料调阅超时查询 -->
            </tr>
            <tr>
                <td class="title"><s:text name="check.claimNum"/>：</td><!-- 赔案号 -->
                <td class="input">
                    <select class="tag" name="claimNoSign">
                        <option value="=">=</option>
                        <option value="=*">=*</option>
                    </select> 
                    <input type="text" name="claimNo" class="query">
                </td>
                <td class="title"><s:text name="db.prpLregist.policyNo" />：</td>
                <td class="input">
                    <select class="tag" name="policyNoSign">
                        <option value="=">=</option>
                        <option value="=*">=*</option>
                    </select>
                    <input type=text name="policyNo" class="query" >
                </td>
            </tr>
            <tr>
                <td class="title"><s:text name="db.prpCmain.insuredName"/>：</td><!-- 被保险人名称 -->
                <td class="input">
                    <select class="tag" name="insuredNameSign">
                        <option value="=">=</option>
                        <option value="=*">=*</option>
                    </select>
                    <input type="text"  name="insuredName" class="query" >
                </td>
                <td class="title"><s:text name="archive.applicantName"/>：</td><!-- 申请人名称 -->
                <td class="input">
                    <select class="tag" name="applicantNameSign">
                        <option value="=">=</option>
                        <option value="=*">=*</option>
                    </select>
                    <input type="text"  name="applicantName" class="query" >
                </td>
            </tr>
            <tr>
                <td class="title"><s:text name="archive.readData"/>：</td><!-- 调阅日期 -->
                <td class="input">
                    <select class="tag" name="startReviewDateSign">
                        <option value="=">=&nbsp;</option>
                    </select>
                    <%-- <input type="text"  name="startReviewDate" class="query" > <img style='cursor hand' align="absmiddle" 
                    src="/claim/images/bgcalendar.gif" onclick="TogglePopupCalendarWindow('document.fm.startReviewDate', '<%=(new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).getYear()-15) %>', '<%=(new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).getYear()+2) %>')">--%>
                    <rc:rcDate name="startReviewDate" style="width: 60%"/> 
                </td>
                <td class="title"><s:text name="archive.expectedReturnDate"/></td><!-- 预计归还日期： -->
                <td class="input">
                    <select class="tag" name="estimateReturnDateSign">
                        <option value="=">=&nbsp;</option>
                    </select>
                    <%-- <input type="text"  name="estimateReturnDate" class="query" > <img style='cursor hand' align="absmiddle" 
                    src="/claim/images/bgcalendar.gif" onclick="TogglePopupCalendarWindow('document.fm.estimateReturnDate', '<%=(new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).getYear()-15) %>', '<%=(new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).getYear()+2) %>')">--%>
                	<rc:rcDate name="estimateReturnDate" style="width: 60%"/>
                </td>
                
            </tr>
            <tr>
                <td class="title" style="color: red" colspan="4">
                    <s:text name="prompt.schedule.query1"/><br><!-- "="符号，必须精确查询。 -->
                    <s:text name="prompt.schedule.query2"/><!-- "=*"符号，前匹配後模糊的查询。 -->
                </td>
            </tr>
        </table>
        <table width="100%">
            <tr>
                <td align="center">
                    <input type=button id="button" class='button' value="<s:text name='button.query.value' />" onclick="executeQuery(1,10);">
                </td>
            </tr>
        </table>
        			<table width="98%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							id="tableResullt" style="display: none;">
							<tr>
								<td>
									<div id="content_message" style="display: none;"></div>
									<div id="listShowCont" align="left">
										<div id="listShow">
											<div id="content" class="sort"></div>
											<div id="content_navigation" class="query"
												style="text-align: center;"></div>
										</div>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
        <input type="hidden" name="editType" value="overtime">
    </form>
</body>
</html>