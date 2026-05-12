<%--
****************************************************************************
* DESC       ：立案查询条件输入页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-02-01
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
								zhangshi		20130201			修改*操作符为=*将like '%%'改为左%右匹配查询
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<html>
<head>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<title><s:text name="title.claimBeforeEdit.titleName" /></title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<jsp:include page="${ctx}/common/pub/StaticJavascript.jsp" />
<script language="javascript">  <%--案件状态标志处理--%>
  function submitForm()
  {
    if((fm.InsuredNameSign.value=="="&&fm.InsuredName.value.length>0)
  	 ||(fm.LicenseNoSign.value=="="&&fm.LicenseNo.value.length>0)
  	 ||(fm.RegistNoSign.value=="="&&fm.RegistNo.value.length>0)
  	 ||(fm.PolicyNoSign.value=="="&&fm.PolicyNo.value.length>0)
  	 ||(fm.ClaimNoSign.value=="="&&fm.ClaimNo.value.length>0)){
  	 //输入了一个条件，可以查
  	 }else if((fm.RegistNoSign.value=="=*"&&fm.RegistNo.value.length>8)
  	        ||(fm.PolicyNoSign.value=="=*"&&fm.PolicyNo.value.length>8)
  	        ||(fm.ClaimNoSign.value=="=*"&&fm.ClaimNo.value.length>8)){
  	 	if("D"==getClassCodeType(fm.RegistNo.value.substr(1,2))
  	 	 ||"D"==getClassCodeType(fm.PolicyNo.value.substr(1,2))
  	 	 ||"D"==getClassCodeType(fm.ClaimNo.value.substr(1,2))){
  	 		alert("车险必须精确查询！");
  	 		return false;
  	 	}else{
  	 		//非车险可以前9位模糊查询
  	 	}  		
  	}else{
  		alert("车险必须输入立案号、报案号、保单号、车牌号、被保险人其中一项精确查询！\n 非车险可以用立案号、报案号或者保单号的前9位进行模糊查询！");
  		return false;
  	}
    var ref="";

    for(i=0;i<fm.status.length;i++){

      if(fm.status[i].checked==true){
        ref = ref+fm.status[i].value+",";
      }
    }

    fm.caseFlag.value = ref;

    fm.submit();//提交
  }
  //-->
    function document.onkeydown() 
    { 
    if(event.keyCode==13) 
    { 
      document.getElementById("button").click(); 
      return false; 
    } 
    }  
  </script>
</head>
<body class="yui-skin-sam">
	<form name="fm" method="POST">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="title.claimBeforeEdit.titleName" />
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="regist.prpLregist.registNo" />：
				</td>
				<%--报案号--%>
				<td class='input'>
					<select class=tag name="BusinessNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="BusinessNo" class="query" style="width: 70%">
				</td>
				<td class='title'>
					<s:text name="db.view_larrearage.policyNo" />
				</td>
				<%--保单号--%>
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="PolicyNo" class="query" style="width: 160">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="regist.prpLregist.riskCodeName" />
				</td>
				<%--险种--%>
				<td class='input'>
					<select class=tag name="RiskCodeNoSign">
						<option value="=">=</option>
						<!--<option value="*">*</option>-->
					</select>
					<input type=text name="RiskCode" class="query" style="width: 70%">
				</td>
				<td class='title' colspan=2>
					<s:text name="claim.intoTime" />:
					<%--流入时间--%>
					<input type=text style="width: 85" width="30%" name="statStartDate" class="query">
					<img style='cursor: hand' src="/claim/images/bgcalendar.gif" align="absmiddle"
						onClick="TogglePopupCalendarWindow('document.fm.statStartDate', '<%=(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY)
					.getYear() - 15)%>', '<%=(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY)
					.getYear() + 2)%>')">
					<s:text name="claim.toThe" />
					<%--到--%>
					<input type=text style="width: 85" name="statEndDate" class="query">
					<img style='cursor: hand' src="/claim/images/bgcalendar.gif" align="absmiddle"
						onClick="TogglePopupCalendarWindow('document.fm.statEndDate', '<%=(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY)
					.getYear() - 15)%>', '<%=new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY)
					.getYear() + 2%>')">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.insuredName" />
				</td>
				<%--被保险人--%>
				<td class='input'>
					<select class=tag name="insuredNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="insuredName" class="query" style="width: 160">
				</td>
				<td class='title'></td>
				<td class='input'></td>
			</tr>
			<tr>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button id="button" class='button' value="<s:text name="button.query.value" />" onclick="executeQuery(1,10);">
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
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/common/js/prototype.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
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
				elCell.innerHTML="<a href=\"/claim/claim/viewClaim.do?claimNo=" + oRecord.getData("claimNo") + "\">"+ oData+"</a>";
		}else if(oColumn.key =="powerEdit"){
				elCell.innerHTML="<a href=\"#\" onclick=\"showDlg('权限配置','/saaUserGrade/prepareUpdateUserGradePower.do?userCode=" + oRecord.getData("userCode") + "')\">配置</a>";
		}else if(oColumn.key =="validStatus"){
			if(oData=='1'){
				elCell.innerHTML="有效";
			}else if(oData=='0'){
				elCell.innerHTML="无效";
			}
		}else if(oColumn.key =="comCName"){
			elCell.innerHTML=oRecord.getData("comCName")+"("+oRecord.getData("comCode")+")";
		}else{
			elCell.innerHTML = oData;
		}
	}; 
	contentColumnHeaders =[
        {key:"nomber",label:"序号",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
		{key:"status",label:"状态",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
		{key:"registNo",label:"报案号",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
		{key:"policyNo",label:"保单号码",width:"40em",sortable:true},
		{key:"riskName",label:"险种",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
		{key:"insuredName",label:"被保险人",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
		{key:"lossTime",label:"剩余(H)",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
		{key:"operatorName",label:"处理人员",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
		{key:"wflogInTime",label:"流入时间",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
		{key:"operate",label:"操作",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}
		]; 
}

/*
 *@description:可以批次切分活动组别结果集 
 *@param  pageNo，pageSize
 *@return  活动组别结果集
 *@author 中科软
*/
function executeQuery(pageNo,pageSize){
	if(isNaN(parseInt(pageNo))){ 
		pageNo = 1;
	}
	if(isNaN(parseInt(pageSize))){
		pageSize = 10;
	} 
	var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);	
	var myDataSource = new YAHOO.util.DataSource("${ctx}/claim/claimBeforeQueryList.do");
	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.connMethodPost = true; 
	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["nomber","status","registNo","policyNo","riskName","insuredName","lossTime","operatorName","wflogInTime","operate"],
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