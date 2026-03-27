<!--
****************************************************************************
* DESC       ：追償審核查詢
* AUTHOR     ： 中科軟
* CREATEDATE ： 2014-04-27
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>追償審核查詢</title>
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
    <form name="fm" action="${ctx}/replevyQuery.do" method="post" onsubmit="return validateForm(this);">
        <input type="hidden" name="underWriteFlag" value="9">
        <input type="hidden" name="editType" value="UNDWRT">
        <table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
            <tr>
                <td colspan="4" class="formtitle">
                    <s:text name="replevy.queryRecoverInformation" />
                    <%--查询追偿信息 --%>
                </td>
            </tr>
            <tr>
                <td class="title">
                    <s:text name="db.prpLclaim.claimNo" />
                    <%--立案号 --%>
                    :
                </td>
                <td class="input">
                    <select class="tag" name="ClaimNoSign">
                        <option value="=">=</option>
                        <option value="=*">=*</option>
                    </select>
                    <input type="text" name="ClaimNo" class="query">
                </td>
                <td class="title">
                    <s:text name="db.prpLregist.registNo" />
                    <%--报案号 --%>
                    :
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
                    <s:text name="db.prpLregist.policyNo" />
                    <%--保单号 --%>
                    :
                </td>
                <td class="input">
                    <select class="tag" name="PolicyNoSign">
                        <option value="=">=</option>
                        <option value="=*">=*</option>
                    </select>
                    <input type="text" name="PolicyNo" class="query">
                </td>
                <td class="title">
                   <s:text name="compensate.computeBookNum" />
                    <%--计算书号 --%>:
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
                <td class="title">審核類型： </td>
                <td class="input">
                    <select class="common" name="UndwrtType" style="width: 120px;">
                        <option value="1">一般追償</option>
                        <option value="0">追償協商</option>
                    </select>
                </td>
                <td class="title"></td>
                <td class="input"></td>
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
                elCell.innerHTML="<a href='${ctx}/replevyFinishQueryList.do?editType=UNDWRT&claimNo="+oRecord.getData("claimNo")+"&compensateNo="+oData+"&swfLogFlowID="+oRecord.getData("flowID")+"&swfLogLogNo="+oRecord.getData("logNo")+"'>"+oData+"</a>";
            }else if(oColumn.key =="handlerName"){
                if(oRecord.getData("flowID")==""){
                    elCell.innerHTML=oRecord.getData("operatorName")+"("+oRecord.getData("operatorCode")+")";
                }else if(oData !=""){
                    elCell.innerHTML=oData+"("+oRecord.getData("handlerCode")+")";
                }
            }else if(oColumn.key =="flowInTime"){
                if(oData!=null){
                    elCell.innerHTML = formatDate(oData,'yyy-MM-dd HH:mm:ss');
                }
            }else{
                elCell.innerHTML = oData;
            }
        }; 
        contentColumnHeaders =[
            {key:"compensateNo",label:"計算書號碼",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--计算书号--%>
            {key:"claimNo",label:"<s:text name="db.prpLclaim.claimNo" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--立案号--%>
            {key:"policyNo",label:"<s:text name="db.prpCmain.policyNo" />",width:"40em",sortable:true},<%--保单号--%>
            {key:"handlerName",label:"<s:text name="db.prpLlawsuit.operatorCode" />",width:"40em",sortable:false,formatter:YAHOO.widget.DataTable.formatLink},<%--操作员--%>
            {key:"nodeName",label:"審核級別",width:"40em",sortable:false,formatter:YAHOO.widget.DataTable.formatLink},
            {key:"flowInTime",label:"流入時間",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}
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
           fields: ["compensateNo", "claimNo", "policyNo","operatorCode","operatorName", "flowID", "logNo", "nodeName","handlerCode","handlerName", "flowInTime"],
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