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
<%@include file="/common/meta_js.jsp"%>
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
					<select class=tag name="ClaimNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="ClaimNo" class="query">
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
					<input type=text name="PolicyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="regist.prpLregist.riskCodeName" />
				</td>
				<%--险种--%>
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="RegistNo" class="query">
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
					<s:text name="db.prpLclaimStatus.operatedate" />：
				</td>
				<%--操作时间--%>
				<td class='input'>
					<select class=tag name="OperateDateSign">
						<option value="=">=&nbsp;</option>
						<option value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value=">=">&gt;=</option>
						<option value="<=">&lt;=</option>
					</select>
					<input type=text name="OperateDate" class="query" style="width: 40%">
					<img style='cursor: hand' src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.OperateDate', '<%=(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY)
					.getYear()) - 15%>', '<%=(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY)
					.getYear()) + 2%>')">
				</td>
				<%--报案查询增加被保险人查询条件--%>
				<td class='title'>
					<s:text name="db.prpLCMain.insuredName" />:
				</td>
				<%--被保险人名称--%>
				<td class='input'>
					<select class=tag name="InsuredNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="InsuredName" class="query">
				</td>
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
		<input type="hidden" name="status" value="<%=request.getParameter("status")%>">
		<input type="hidden" name="searchLabel" value="<%=request.getParameter("searchLabel")%>">
		<input type="hidden" name="searchField" value="<%=request.getParameter("searchField")%>">
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
		if(oColumn.key =="businessNo"){
				elCell.innerHTML="<a href=\"/claim/claimtemp/claimTempEdit.do?claimNo=" + oRecord.getData("businessNo") 
						+ "&swfLogFlowID=" + oRecord.getData("flowID") 
						+ "&swfLogLogNo=" + oRecord.getData("logNo") 
						+ "&status=" + oRecord.getData("nodeStatus") 
						+ "&riskCode=" + oRecord.getData("riskCode") 
						+ "&editType=EDIT"  
						+ "&nodeType=" + oRecord.getData("nodeType") 
						+ "&businessNo=" + oRecord.getData("businessNo") 
						+ "&keyIn=" + oRecord.getData("keyIn") 
						+ "&policyNo=" + oRecord.getData("policyNo") 
						+ "&modelNo=" + oRecord.getData("modelNo") 
						+ "&nodeNo=" + oRecord.getData("nodeNo") 
						+ "&dfFlag=\">"+ oData+"</a>";
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
		{key:"flowID",label:"工作流ID",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
		{key:"businessNo",label:"业务号",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
		{key:"registNo",label:"报案号",width:"40em",sortable:true},
		{key:"policyNo",label:"保单号",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
		{key:"riskCode",label:"险种",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
		{key:"handlerName",label:"处理人员",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},
		{key:"handleTime",label:"处理时间",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}
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
	var myDataSource = new YAHOO.util.DataSource("${ctx}/claimtemp/claimTempQueryList.do?editType=EDIT");
	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.connMethodPost = true; 
	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["flowID","logNo","nodeStatus","riskCode","registNo","businessNo","handlerCode","handlerName","handleTime","policyNo","keyIn","keyOut"],
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