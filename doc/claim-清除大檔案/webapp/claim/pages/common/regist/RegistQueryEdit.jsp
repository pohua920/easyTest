<%--
****************************************************************************
* DESC       ：报案查询条件输入页面
* AUTHOR     ：中科软
* CREATEDATE ：2013-02-26
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.sinosoft.sysframework.common.datatype.*"%>
<%@page import="java.util.*" %>
<%
    Calendar date = Calendar.getInstance();
    date.add(Calendar.MONTH,-3);//事故日期控制在3个月内的
    pageContext.setAttribute("damageStartDate", date.getTime());
    pageContext.setAttribute("damageEndDate", Calendar.getInstance().getTime());
%>
<html>
<head>
<title><s:text name="title.registBeforeEdit.titleName" /></title>
<%-- 公用函数 --%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script language="javascript">
    //按钮响应回车
    function document.onkeydown(){
        if(event.keyCode==13){
            document.getElementById("button").click();
            return false;
        }
    }
    $(function(){
        $(":radio[name='cancelFlag']").bind("click",function(){
            if( this.checked && this.value == "1" ){
                $(":checkbox[name='status']").attr("checked",false);
            }
        });
        $(":checkbox[name='status']").bind("click",function(){
            $(":radio[name='cancelFlag'][value='0']").attr("checked",true);
        });
    })
  </script>
</head>
<body class="yui-skin-sam">
    <form name="fm" method="POST">
        <table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
            <tr>
                <td colspan="4" class="formtitle">
                    <s:text name="title.registBeforeEdit.titleName" />
                </td>
            </tr>
            <tr>
                <td class='title'>
                    <s:text name="db.prpLregist.registNo" />：
                </td>
                <td class='input'>
                    <select class=tag name="RegistNoSign">
                        <option value="=">=</option>
                        <option value="=*">=*</option>
                    </select>
                    <input type=text name="RegistNo" class="query">
                </td>
                <td class='title'>
                    <s:text name="db.prpLregist.policyNo" />：
                </td>
                <td class='input'>
                    <select class=tag name="PolicyNoSign">
                        <option value="=">=</option>
                        <option value="=*">=*</option>
                    </select>
                    <input type=text name="PolicyNo" class="query">
                </td>
            </tr>
            <tr>
                <td class='title'>
                    <s:text name="db.prpLregist.riskCode" />：
                </td>
                <td class='input'>
                    <select class=tag name="RiskCodeSign">
                        <option value="=">=</option>
                    </select>
                    <input type=text name="RiskCode" class="query">
                </td>
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
            </tr>
            <tr>
                <td class='title'>
                    <s:text name="db.prpLclaimStatus.operatedate" /><%--操作时间 --%>：
                </td>
                <td class='input'>
                    <select class=tag name="OperateDateSign">
                        <option value="=">=&nbsp;</option>
                        <option selected value=">">&gt;&nbsp;</option>
                        <option value="<">&lt;&nbsp;</option>
                        <option value=">=">&gt;=</option>
                        <option value="<=">&lt;=</option>
                    </select>
                    <rc:rcDate name="OperateDate" style="width: 60%"/>
                </td>
                <td class='title'><s:text name="db.prplcheck.identifyNumber" />：<%-- 要保人身份证号--%></td>
                <td class='input'>
                    <select class=tag name="AppliIdentifyNumberSign">
                        <option value="=">=</option>
                    </select>
                    <input type=text name="AppliIdentifyNumber" class="query">
                </td>
            </tr>
            <tr>
                <td class='title'>
                    <s:text name="db.prpCmain.insuredName" /><%--被保险人名称 --%>：
                </td>
                <td class='input'>
                    <select class=tag name="InsuredNameSign">
                        <option value="=">=</option>
                        <option value="=*">=*</option>
                    </select>
                    <input type=text name="InsuredName" class="query">
                </td>
                <td class='title'><s:text name="db.prpCmain.insured" />ID：<%-- 被保险人身份证号--%></td>
                <td class='input'>
                    <select class=tag name="InsuredIdentifyNumberSign">
                        <option value="=">=</option>
                    </select>
                    <input type=text name="InsuredIdentifyNumber" class="query">
                </td>
            </tr>
            <tr>
                <td class='title'>
                    <s:text name="query.damageDate" />：
                </td>
                <td class='input' colspan="3" align="left">
                    <rc:rcDate name="damageStartDate" style="width:156px"  value="${pageScope.damageStartDate}"/>&nbsp;至&nbsp;
                    <rc:rcDate name="damageEndDate" style="width:156px"  value="${pageScope.damageEndDate}"/>
                </td>
            </tr>
            <tr>
                <td class='title'>
                    <s:text name="regist.WhetherUnregister" /><%--是否注销 --%>：
                </td>
                <td class='input'>
                    <input type="radio" name="cancelFlag" value="1" >
                        <s:text name="regist.prpLregist.yes" /><%-- 是--%>
                    </input>
                    <input type="radio" name="cancelFlag" value="0" checked="checked">
                        <s:text name="regist.prpLregist.no" /><%-- 否--%>
                    </input>
                </td>
                <td class='title'>
                    <s:text name="db.prpLclaimStatus.status" /><%--案件状态 --%>：
                </td>
                <td class='input'>
                    <input type="checkbox" name="status" value="2" checked="checked"><s:text name="common.status.intreating" /><%--正处理--%>
                    <input type="checkbox" name="status" value="4"><s:text name="common.status.treated" /><%--已处理 --%>
                </td>
            </tr>
            <tr id="cancelDate" style="display: none">
                <td class='title'>
                    <s:text name="regist.OffStartTime" /><%-- 注销开始时间--%>：
                </td>
                <td class='input'>
                    <rc:rcDate name="registStartCancelDate" />
                </td>
                <td class='title'>
                    <s:text name="regist.OffEndTime" /><%-- 注销结束时间--%>：
                </td>
                <td class='input'>
                    <rc:rcDate name="registEndCancelDate" />
                </td>
            </tr>
            <tr>
                <td class="title" style="color: red" colspan="4">
                    <s:text name="prompt.schedule.query1" /><%--"="符号，必须精确查询。 --%><br>
                    <s:text name="prompt.schedule.query2" /><%--"=*"符号，前匹配後模糊的查询。 --%><br>
                    <s:text name="prompt.schedule.query3" /><%--车险必须输入报案号、保单号、车牌号、被保险人其中一项精确查询！ --%><br>
                    <s:text name="prompt.schedule.query4" /><%-- 非车险可以用报案号或者保单号的前9位进行模糊查询！--%>
                </td>
            </tr>
        </table>
        <table width=100%>
            <td align=center>
                <input type=button id="button" class='button' value="<s:text name='button.query.value' />" onclick="executeQuery(1,10,this);">
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
        <input type="hidden" name="editType" value="SHOW">
        <input type="hidden" name="nodeType" value="<%=request.getParameter("nodeType")%>">
    </form>
</body>
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
        if(oColumn.key =="registNo"){
            elCell.innerHTML="<a href=\"${ctx}/regist/registBeforeEdit.do?prpLregistRegistNo="+oRecord.getData("registNo")+"&prpCmainPolicyNo="+oRecord.getData("policyNo")+"&editType="+oRecord.getData("editType")+"&riskCode="+oRecord.getData("riskCode") + "\">"+ oData+"</a>";
        }else if(oColumn.key =="status"){
            var statusStr = "";
            if(oRecord.getData("cancelDate") == null) {
                if(oData=="1") {
                    statusStr = "未處理";
                } else if(oData=="2"){
                     statusStr = "正處理";
                } else if(oData=="3"){
                    statusStr = "已處理";
                } else if(oData=="4"){
                    statusStr = "已提交";
                } else if(oData=="5"){
                    statusStr = "已撤銷";
                }
            }
            if(oRecord.getData("cancelDate") != null){
                statusStr = "已註銷";
            }
            elCell.innerHTML = statusStr
        }else if(oColumn.key =="operatorCode"){
            elCell.innerHTML= oData +"("+oRecord.getData("operatorName")+")";
        }else if(oColumn.key =="operateDate") {
            if(oData!=null){
                var date = new Date(oData.time);
                elCell.innerHTML = formatDate(date,'yyy-MM-dd');
            }
        }else{
            elCell.innerHTML = oData;
        }
    }; 
    contentColumnHeaders =[
        {key:"status",label:"<s:text name='db.prpLclaimStatus.status'/>",width:"40em",sortable:false,formatter:YAHOO.widget.DataTable.formatLink},
        {key:"registNo",label:"<s:text name='db.prpLregist.registNo'/>",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
        {key:"policyNo",label:"<s:text name='db.prpLregist.policyNo'/>",width:"40em",sortable:true},
        {key:"licenseNo",label:"<s:text name='db.prpLregist.licenseNo'/>",width:"40em",sortable:false},
        {key:"insuredName",label:"<s:text name='db.prpLCMain.insuredName'/>",width:"40em",sortable:false},
        {key:"operatorCode",label:"<s:text name='db.prpLregist.operatorCode'/>",width:"40em",sortable:false,formatter:YAHOO.widget.DataTable.formatLink},
        {key:"operateDate",label:"<s:text name='db.prpLclaimStatus.operatedate'/>",width:"40em",sortable:false,formatter:YAHOO.widget.DataTable.formatLink}
        ];
}
/*
 *@description:可以批次切分活动组别结果集 
 *@param  pageNo，pageSize
 *@return  活动组别结果集
 *@author 中科软
*/
function executeQuery(pageNo,pageSize,field){
    //解决性能问题，控制查询条件
    if((fm.InsuredNameSign.value=="="&&trim(fm.InsuredName.value).length>0)
       ||(fm.LicenseNoSign.value=="="&&trim(fm.LicenseNo.value).length>0)
       ||(fm.RegistNoSign.value=="="&&trim(fm.RegistNo.value).length>0)
       ||(fm.PolicyNoSign.value=="="&&trim(fm.PolicyNo.value).length>0)
       ||(fm.AppliIdentifyNumberSign.value=="="&&trim(fm.AppliIdentifyNumber.value).length>0)
       ||(fm.InsuredIdentifyNumberSign.value=="="&&trim(fm.InsuredIdentifyNumber.value).length>0)){
       //输入了一个条件，可以查
       }else if((fm.RegistNoSign.value=="=*"&&fm.RegistNo.value.length>8)
              ||(fm.PolicyNoSign.value=="=*"&&fm.PolicyNo.value.length>8)){
      }else{
          alert("車險必須輸入備案號碼、保單號碼、牌照號碼、要保人ID、要保人ID、被保險人其中一項精確查詢！\n非車險可以用備案號碼或者保單號碼的前9位進行模糊查詢！");
          return false;
      }
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
    var myDataSource = new YAHOO.util.DataSource("${ctx}/regist/registQuery.do");
    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
    myDataSource.connMethodPost = true; 
    myDataSource.responseSchema = {
       resultsList: "data",
       fields: ["registNo","policyNo","riskCode","relatepolicyNo","licenseNo","status","cancelDate","insuredName", "operatorCode", "operatorName","operateDate","editType"],
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
</script>
</html>