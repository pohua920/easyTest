<!--
****************************************************************************
* DESC       ：登錄訊息修改查詢頁面
* AUTHOR     ： 中科軟
* CREATEDATE ： 2013-10-13
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
                                        zhangshi          20080512                    修改模糊查询为右模糊查询
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>追償登錄訊息修改查詢</title>
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
        <input type="hidden" name="underWriteFlag" value="0">
        <input type="hidden" name="editType" value="editQuery">
        <table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
            <tr>
                <td colspan=4 class="formtitle">
                    <s:text name="replevy.queryRecoverInformation" />
                    <%--查询追偿信息 --%>
                </td>
            </tr>
            <tr>
                <td class='title'>
                    <s:text name="db.prpLclaim.claimNo" />:<%--立案號碼 --%>
                </td>
                <td class='input'>
                    <select class=tag name="ClaimNoSign">
                         <option value="=">=</option>
                         <option value="=*">=*</option>
                    </select>
                    <input type=text name="ClaimNo" class="query">
                </td>
                <td class='title'>
                    <s:text name="db.prpLregist.registNo" />:<%--備案號碼 --%>
                </td>
                <td class='input'>
                    <select class=tag name="RegistNoSign">
                        <option value="=">=</option>
                    </select>
                    <input type=text name="RegistNo" class="query">
                </td>
             </tr>
            <tr>
                <td class='title'>
                    <s:text name="db.prpLregist.policyNo" />:<%--保單號碼 --%>
                </td>
                <td class='input'>
                    <select class=tag name="PolicyNoSign">
                        <option value="=">=</option>
                        <option value="=*">=*</option>
                    </select>
                    <input type=text name="PolicyNo" class="query">
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
                    elCell.innerHTML="<a href='${ctx}/replevyFinishQueryList.do?editType=editQuery&claimNo="+oData+"&compensateNo="+oRecord.getData("compensateNo")+"'>"+oData+"</a>";
               } else if(oColumn.key =="operatorCode"){
                    elCell.innerHTML=oData+"("+oRecord.getData("operatorName")+")";
               } else if(oColumn.key =="inputDate") {
                    if(oData!=null){
                         var date = new Date(oData.time);
                         elCell.innerHTML = formatDate(date,'yyy-MM-dd HH:mm:ss');
                    }
               }else{
                    elCell.innerHTML = oData;
               }
          }; 
          contentColumnHeaders =[
               {key:"claimNo",label:"<s:text name="db.prpLclaim.claimNo" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--立案号--%>
               {key:"policyNo",label:"<s:text name="db.prpCmain.policyNo" />",width:"40em",sortable:true},<%--保单号--%>
               {key:"operatorCode",label:"<s:text name="db.prpLlawsuit.operatorCode" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--操作员--%>
               {key:"inputDate",label:"登錄時間",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}
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
             fields: ["compensateNo","claimNo","policyNo","operatorCode","operatorName","inputDate"],
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