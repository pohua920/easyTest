<%--
****************************************************************************
* DESC       ：通赔接收查询界面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-03-01
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime" %>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<html>
  <title><s:text name="title.registBeforeEdit.titleName" /></title>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  <script src="/claim/pages/generalClaim/js/GeneralClaimEdit.js"></script>
<%--原因：向页面中增加一个打印按钮--%>
<script src="/claim/common/js/showpage.js"> </script>
<script>
    function submitForm(){
  		if(fm.RiskCode.value == ""){
  			alert("必须輸入险种！");
  			return;
  		}
  		fm.submit();
  	}
    function document.onkeydown() 
    {
    if(event.keyCode==13) 
    { 
      document.getElementById("button").click(); 
      return false; 
    }
    }
</script>
<head>
  <title><s:text name="general.receiveQuery"/></title><%--通赔接收 --%>
</head>
<body class="yui-skin-sam">
<form name="fm" action="/claim/generalClaim.do" method="post">
<input type="hidden" name="pageFlag">
    <table id="queryTable" width="100%" border="0" align="center" cellpadding="4" cellspacing="1"  class="common">
      <tr>
	    <td colspan="4" class="formtitle"><s:text name="general.toBeReceiveSerach"/></td><%--查询待接收通赔任务 --%>
        </tr>
      <tr>
        <td class='title' ><s:text name="prompt.queRegist.RegistNo"/>：</td> <!--报案号 -->
        <td class='input' >
            <input type=text name="RegistNo" class="query" >
        </td>
        <td class='title' ><s:text name="db.prpCmain.policyNo"/>:</td><!--保单号码 -->
        <td class='input' >
            <input type=text name="PolicyNo" class="query" >
        </td>
      </tr>
      <tr>
        <td class='title' ><s:text name="workflow.commissionedParty"/>:</td><%--委托机构 --%>
        <td class='input' >
        <input class='query' type='hidden' name='GiveComCode' value="">
    			   <input  class='codecode' type='text' name='GiveComName' value="" 
          				  ondblclick="code_CodeSelect(this,'queryLevel2Com','-1,0','Y');" 
    	  				  onchange="code_CodeSelect(this,'queryLevel2Com','-1,0','Y');">
    	  			<img src="/claim/images/bgMarkMustInput.jpg">
        </td>
        <td class='title' ><s:text name="db.prpDdbs.riskCode"/>:</td><%--险种 --%>
        <td class='input' >
      <input type=text name="RiskCode" class="query" onchange="clickable()" onkeypress="KeyDown()">
        </td>
      </tr>
      <tr>
        <td class='title' ><s:text name="workflow.commissionedTime"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text name="prompt.from"/>:</td><%--委托时间 --%><%--从 --%>
        <td class='input'>
          <%-- <input type=text name="StartOperateDate" class="Wdate" onclick="WdatePicker()"/> --%>
          <rc:rcDate name="StartOperateDate" /> 
        </td>
       <td class='title' ><s:text name="prompt.to"/>:</td><%--至 --%>
        <td class='input' >
          <%-- <input type=text name="EndOperateDate" class="Wdate" onclick="WdatePicker()"/>--%>
          <rc:rcDate name="EndOperateDate" /> 
          </td>
      </tr>
      </table>
      <table width=100%>
      <tr>
       <td class='button' colspan="6">
          <input type=button id="button" class='button' name="queryButton" value="<s:text name='button.query.value' />" onClick="executeQuery(1,10,this);">
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
<input type="hidden" name="actionType" value="${param.actionType}">
</form>
<%@ include file="/common/taglibs.jsp"%>
</body>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/common/js/prototype.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">
var isFirstLoad = true;
var contentDataTable;
var contentColumnHeaders;
var count = 1;
YAHOO.namespace("query.container");

/*
 *@description:初始化查询结果页面
 *@param varSigns 无
 *@return boolean 活动组别结果集    
*/
function init(){
	YAHOO.widget.DataTable.formatLink = function(elCell, oRecord, oColumn, oData) {
		if(oColumn.key =="registNo"){
			elCell.innerHTML="<a  href=\"${ctx}/claim/generalClaim.do?actionType=prepareReceiveInsert&registNo="+oRecord.getData("registNo") + "\">"+ oData+"</a>";
		}else if(oColumn.key =="givetime" || oColumn.key =="receivetime") {
			if(oData!=null){
				var date = new Date(oData.time);
				elCell.innerHTML = formatDate(date,'yyy-MM-dd');
			}
		}else if(oColumn.key =="serialno"){
		    elCell.innerHTML = count++;
		}else{
			elCell.innerHTML = oData;
		}
	}; 
	contentColumnHeaders =[
		{key:"serialno",label:"<s:text name="db.prpDrate.serialNo" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--序号--%>
		{key:"registNo",label:"<s:text name="prompt.queRegist.RegistNo" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--报案号--%>
		{key:"policyNo",label:"<s:text name="prompt.queRegist.PolicyNo" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--保单号--%>
		{key:"givecomname",label:"<s:text name="workflow.commissionedParty" />",width:"40em",sortable:true},<%--委托机构--%>
		{key:"giveoperatorname",label:"<s:text name="certify.groupClient" />",width:"40em",sortable:true},<%--委托人--%>
		{key:"givetime",label:"<s:text name="workflow.commissionedTime" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--委托时间--%>
		{key:"currentnode",label:"<s:text name="workflow.currentLinks" />",width:"40em",sortable:true}<%--当前环节--%>
		];
}

/*
 *@description:可以批次切分活动组别结果集 
 *@param  pageNo，pageSize
 *@return  活动组别结果集
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
	var myDataSource = new YAHOO.util.DataSource("${ctx}/generalClaim.do");
	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.connMethodPost = true; 
	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["registNo","policyNo","givecomname","receivecomname","giveoperatorname","givetime","currentnode"],
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