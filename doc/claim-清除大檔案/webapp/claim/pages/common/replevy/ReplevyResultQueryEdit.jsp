<!--
****************************************************************************
* DESC       ：追偿查询输入界面
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
<title>追償查詢輸入界面</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<%@ include file="/common/meta_js.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<script language="javascript">  
    function submitForm(){
        fm.submit();//提交
    }
</script>
</head>
<body onload="initPage();" class="yui-skin-sam">
    <form name="fm" action="${ctx}/replevyResultQuery.do?editType=QUERY" method="post" onsubmit="return validateForm(this);">
        <table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
            <tr>
                <td colspan=4 class="formtitle">
                    <s:text name="replevy.queryDealInformation" /><%--查询已处理追偿信息 --%>
                </td>
            </tr>
            <tr>
                <td class="title">
                    <s:text name="db.prpLclaim.claimNo" />:<%--立案号 --%>
                </td>
                <td class="input">
                    <select class="tag" name="ClaimNoSign">
                        <option value="=">=</option>
                        <option value="=*">=*</option>
                    </select>
                    <input type="text" name="ClaimNo" class="query">
                </td>
                <td class="title">
                    <s:text name="compensate.computeBookNum" />:<%--计算书号 --%>
                </td>
                <td class="input">
                    <select class="tag" name="CompensateNoSign">
                        <option value="=">=</option>
                        <option value="=*">=*</option>
                    </select>
                    <input type="text" name="CompensateNo" class="query">
                </td>
            </tr>
            <tr>
                <td class="title">
                    <s:text name="db.prpCprofit.policyNo" />:<%--保单号 --%>
                </td>
                <td class="input">
                    <select class="tag" name="PolicyNoSign">
                        <option value="=">=</option>
                        <option value="=*">=*</option>
                    </select>
                    <input type="text" name="PolicyNo" class="query">
                </td>
                <td class="title">
                    <s:text name="db.prpLregist.registNo" />:<%--报案号 --%>
                </td>
                <td class="input">
                    <select class="tag" name="RegistNoSign">
                        <option value="=">=</option>
                    </select>
                    <input type="text" name="RegistNo" class="query">
                </td>
            </tr>
            <tr>
                <td class="title">
                    <s:text name="db.prpLreplevy.repleviedName" />：<%--被追偿人名称 --%>
                </td>
                <td class="input">
                    <select class="tag" name="RepleviedNameSign">
                        <option value="*">=</option>
                        <option value="=*">=*</option>
                    </select>
                    <input type="text" class="query" name="prpLreplevyRepleviedName">
                </td>
                <td class="title">
                    <s:text name="db.prpLendor.inputDate" />：<%--输入日期 --%>
                </td>
                <td class="input">
                    <rc:rcDate name="InputStartDate" style="width:36.5%" value=""/>
                    &nbsp;<s:text name="prompt.to" />&nbsp;
                    <rc:rcDate name="InputEndDate" style="width:37%" value=""/>
                </td>
            </tr>
            <tr>
                <td class="title">追回日期：</td>
                <td class="input">
                    <rc:rcDate name="ValidStartDate" title="追回日期" style="width:36.5%" value=""/>
                    &nbsp;<s:text name="prompt.to" />&nbsp;
                    <rc:rcDate name="ValidEndDate"  title="追回日期" style="width:37%" value=""/>
                </td>
                <td class="title">即將到期件：</td>
                <td class="input"> <rc:rcDate name="PreserveDate" title="即將到期時間" style="width:36.5%" value=""/>
                </td>
            </tr>
            <tr>
                <td class="button" colspan="4">
                    <input type="button" class="button" value="<s:text name='button.query.value'/>" onClick="executeQuery(1,10,this);">
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
	function init(){
		YAHOO.widget.DataTable.formatLink = function(elCell, oRecord, oColumn, oData) {
			if(oColumn.key =="compensateNo"){
				elCell.innerHTML="<a href ='${ctx}/replevyFinishQueryList.do?editType=SHOW&compensateNo="+oData+"'>"+oData+"</a>";
			}else if(oColumn.key =="statisticsYM") {
				if(oData!=null){
					var date = new Date(oData.time);
					elCell.innerHTML = formatDate(date,'yyy-MM-dd');
				}
			}else if(oColumn.key =="indemnityDuty"){
				if(oData=="1"){
					elCell.innerHTML = "自追償";
				}else if(oData=="2"){
					elCell.innerHTML = "代追償";
				}else if(oData=="3"){
					elCell.innerHTML = "理賠";
				}
			}else if(oColumn.key =="sumThisPaid"){
				if(oData<0){
					elCell.innerHTML = -oData;
				}else{
					elCell.innerHTML = oData;
				}
			}else{
				elCell.innerHTML = oData;
			}
		}; 
		contentColumnHeaders =[
			{key:"compensateNo",label:"<s:text name="compensate.computeBookNum" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--计算书号--%>
			{key:"claimNo",label:"<s:text name="db.prpLclaim.claimNo" />",width:"40em",sortable:true},<%--立案号--%>
			{key:"policyNo",label:"<s:text name="db.prpCmain.policyNo" />",width:"40em",sortable:true},<%--保单号--%>
			{key:"indemnityDuty",label:"<s:text name="replevy.recoverType" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--追偿类型--%>
			{key:"sumThisPaid",label:"<s:text name="replevy.recoverAmountCurrentPeriod" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--当期追偿金额--%>
			{key:"statisticsYM",label:"追回日期",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}<%--追偿日期--%>
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
		if(isNaN(parseInt(pageNo))){ 
			pageNo = 1;
		}
		if(isNaN(parseInt(pageSize))){
			pageSize = 10;
		} 
		var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);	
		var myDataSource = new YAHOO.util.DataSource("${ctx}/replevyResultQuery.do?editType=QUERY");
		myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.connMethodPost = true; 
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["compensateNo","claimNo","policyNo","indemnityDuty","sumThisPaid","statisticsYM"],
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
</script>
</body>
</html>