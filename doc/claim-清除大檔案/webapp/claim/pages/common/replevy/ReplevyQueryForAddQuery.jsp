<!--
****************************************************************************
* DESC       ：追償登錄查詢界面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2013-10-13
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------ 
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<title>追償登錄查詢</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<%@ include file="/common/meta_js.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
</head>
<body class="yui-skin-sam">
<form name="fm" action="${ctx}/replevyQuery.do"  method="post" onsubmit="return validateForm(this);">
    <input type="hidden" name="editType" value="addQuery">
    <table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
        <tr>
            <td colspan=4 class="formtitle">
                <s:text name="replevy.queryRecoverInformation" /><%--查询追偿信息 --%>
            </td>
        </tr>
        <tr>
            <td class='title'>
                <s:text name="db.prpLclaim.claimNo" />：<%--立案号 --%>
            </td>
            <td class='input'>
                <select class=tag name="ClaimNoSign">
                    <option value="=">=</option>
                    <option value="=*">=*</option>
                </select>
                <input type=text name="ClaimNo" class="query">
            </td>
            <td class='title'>
                <s:text name="db.prpLregist.registNo" />：<%-- 报案号--%>
            </td>
            <td class='input'>
                <select class=tag name="RegistNoSign">
                    <option value="=">=</option>
                    <option value="=*">=*</option>
                </select>
                <input type=text name="RegistNo" class="query">
            </td>
        </tr>
        <tr>
            <td class='title'>
                <s:text name="db.prpCmain.policyNo" />：<%--保单号 --%>
            </td>
            <td class='input'>
                <select class=tag name="PolicyNoSign">
                    <option value="=">=</option>
                    <option value="=*">=*</option>
                </select>
                <input type=text name="PolicyNo" class="query">
            </td>
            <td class='title'>
                <s:text name="db.prpCmain.insuredName" />：<%-- 被保险人名称--%>
            </td>
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
                <s:text name="db.prpLregist.licenseNo" />：
            </td>
            <td class='input'>
                <select class=tag name="LicenseNoSign">
                    <option value="=">=</option>
                    <option value="=*">=*</option>
                </select>
                <input type=text name="LicenseNo" class="query">
            </td>
            <td class='title'>
                <s:text name="endcase.insuranceAgent" />：<%-- 承保机构--%>
            </td>
            <td class='input'>
                <select class=tag name="comCodeSign">
                    <option value="=">=</option>
                    <!--<option value="*">*</option>-->
                </select>
                <input type=text name="comCode" class="query">
            </td>
        </tr>
        <tr>
            <td class='title'>理算是否標記有追償：</td>
            <td class='input' colspan="3">
                <select class=tag name="ReplevyFlagSign" style="width: 83px">
                    <option value="=">=</option>
                </select>
                <input type="radio" checked="checked" name="replevyFlag" value="1">是
                <input type="radio" name="replevyFlag" value="0">否
            </td>
        </tr>
        <tr>
            <td class='button' colspan="4">
                <input type=button class='button' value="<s:text name='button.query.value'/>" onClick="executeQuery(1,10,this);">
            </td>
        </tr>
        <tr>
            <td class="title" style="color: red" colspan="4">
                <s:text name="prompt.schedule.query1" /><%--"="符号，必须精确查询。 --%><br>
                <s:text name="prompt.schedule.query2" /><%-- "=*"符号，前匹配後模糊的查询。--%>
            </td>
        </tr>
    </table>
    <table width="98%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td>
                <table  width="100%" border="0" cellspacing="0" cellpadding="0" id="tableResullt" style="display: none;">
                    <tr>
                        <td>
                            <div id="content_message" style="display: none;"></div>
                            <div id="listShowCont" align = "left">
                                <div id="listShow" >
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
            if(oColumn.key =="claimNo"){
                var hasReplevy = oRecord.getData("hasReplevy");
                var helf = "${ctx}/replevyFinishQueryList.do?editType=addQuery&hasReplevy="+hasReplevy+"&claimNo="+oData+"&policyNo="+oRecord.getData("policyNo");
                if(hasReplevy=='0'){
                    elCell.innerHTML="<a href='"+helf+"'>"+oData+"</a>";
                }else if(hasReplevy=='1'){
                    elCell.innerHTML="<a href='javascript:alert(\"該案件已登錄未處理，不可再次登錄！\");' >"+oData+"</a>";
                }else if(hasReplevy=='2'){
                    elCell.innerHTML="<a href='javascript:alert(\"該案件追償處理進行中，不可再次登錄！\");' >"+oData+"</a>";
                }else if(hasReplevy=='3'){
                    elCell.innerHTML="<a href='javascript:alert(\"該案件追償協商處理中，不可再次登錄！\");'>"+oData+"</a>";
                }
                
            }else if(oColumn.key =="insuredCode"){
                elCell.innerHTML=oData+"("+oRecord.getData("insuredName")+")";
            }else if(oColumn.key =="claimDate") {
                if(oData!=null){
                    var date = new Date(oData.time);
                    elCell.innerHTML = formatDate(date,'yyy-MM-dd');
                }
            }else if(oColumn.key =="hasReplevy"){
                // 0:未登錄，1：未處理可修改；2：已處理待審核 
                if(oData=="0"){
                    elCell.innerHTML = "未登錄";
                }else if(oData=="1"){
                    elCell.innerHTML = "未處理";
                }else if(oData=="2"){
                    elCell.innerHTML = "已處理";
                }else if(oData=="3"){
                    elCell.innerHTML = "協商中";
                }
            }else{
                elCell.innerHTML = oData;
            }
        }; 
        contentColumnHeaders =[
            {key:"claimNo",label:"<s:text name="db.prpLclaim.claimNo" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--立案号--%>
            {key:"registNo",label:"<s:text name="db.prpLregist.registNo" />",width:"40em",sortable:true},<%--报案号--%>
            {key:"policyNo",label:"<s:text name="db.prpCmain.policyNo" />",width:"40em",sortable:true},<%--保单号--%>
            {key:"insuredCode",label:"<s:text name="db.prpLregist.insuredName" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--被保险人--%>
            {key:"claimDate",label:"<s:text name="prpLclaim.claimDate" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--立案时间--%>
            {key:"hasReplevy",label:"追償狀態",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}<%--狀態--%>
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
        var myDataSource = new YAHOO.util.DataSource("${ctx}/replevyQuery.do");
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
        myDataSource.connMethodPost = true; 
        myDataSource.responseSchema = {
           resultsList: "data",
           fields: ["claimNo","registNo","policyNo","insuredCode","insuredName","claimDate","hasReplevy"],
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