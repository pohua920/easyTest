<%--
****************************************************************************
* DESC       ：立案查询条件输入页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-02-01
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@page contentType="text/html; charset=GBK"%>
<%@include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<html>
<head>
<title><s:text name="title.claimBeforeEdit.titleName" /></title>
<script language="javascript">
  <%--案件状态标志处理--%>
  //-->
  //按钮响应回车
    function document.onkeydown() 
    { 
    if(event.keyCode==13) 
    { 
      document.getElementById("button").click(); 
      return false; 
    } 
    }  
    //
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
					<s:text name="db.prpLclaim.claimNo" />：
				</td>
				<td class='input'>
					<select class=tag name="ClaimNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="ClaimNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLclaim.policyNo" />：
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
					<s:text name="db.prpLclaim.registNo" />：
				</td>
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
					<%--  <input type=text name="OperateDate" class="Wdate" onClick="WdatePicker()"   style="width:40%">--%>
					<rc:rcDate name="OperateDate" style="width: 60%"/>
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
				<td class='title'>
					<s:text name="db.prpCmain.insured" />ID：<%-- 身份證字號/統一編碼--%>
				</td>
				<td class='input'>
					<select class=tag name="insuredIdentifyNumberSign">
						<option value="=">=</option>
					</select>
					<input type=text name="insuredIdentifyNumber" class="query" value="<c:out value="${param.InsuredIdentifyNumber}"/>">
				</td>
				<td class='title'>
					<s:text name="query.damageDate" />：<%-- 事故日期 --%>
				</td>
				<td class='input' align="left">
					<rc:rcDate name="damageStartDate" style="width:149px" value="${page.damageStartDate}" />
					&nbsp;
					<s:text name="prompt.to" />
					&nbsp;
					<rc:rcDate name="damageEndDate" style="width:149px" value="${page.damageEndDate}" />
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.status" />：
				</td>
				<%--案件状态--%>
				<td colspan="3" class='input'>
					<input type="hidden" name="caseFlag">
					<%-- 立案查询中没有未处理的查询
          <input type="checkbox" name="status" value="1"><s:text name="common.status.untreated" /><!--未处理-->
          --%>
					<input type="checkbox" name="status" value="2">
					<s:text name="common.status.intreating" />
					<%--正处理--%>
					<input type="checkbox" name="status" value="4">
					<s:text name="common.status.submited" />
					<%--已提交--%>
					<input type="checkbox" name="status" value="6">
					<s:text name="claim.logOut" />
					<%--注销--%>
					<input type="checkbox" name="status" value="7">
					<s:text name="claim.rejectClaim" />
					<%--拒赔--%>
					<input type="checkbox" name="status" value="8">
					<s:text name="claim.endCase" />
					<%--结案--%>
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<br>
					<%--"="符号，必须精确查询。--%>
					<s:text name="prompt.schedule.query2" />
					<br>
					<%--"=*"符号，前匹配後模糊的查询。--%>
					<s:text name="prompt.schedule.query3" />
					<br>
					<%--车险必须输入立案号、报案号、保单号、车牌号、被保险人其中一项精确查询！--%>
					<s:text name="prompt.schedule.query4" />
					<%--非车险可以用立案号、报案号或者保单号的前9位进行模糊查询！--%>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button id="button" class='button' value="<s:text name="button.query.value" />" onclick="executeQuery(1,10,this);">
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
		<input type="hidden" name="nodeType" value="${param.nodeType}">
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

/**
 *@description:初始化查询结果页面
 *@param varSigns 无
 *@return boolean 活动组别结果集
 *@author 中科软
 */
function init(){
	YAHOO.widget.DataTable.formatLink = function(elCell, oRecord, oColumn, oData) {
		if(oColumn.key =="claimNo"){
				elCell.innerHTML="<a href=\"/claim/claim/claimFinishQueryList.do?prpLclaimClaimNo=" + oRecord.getData("claimNo") + "&editType=SHOW&riskCode=" + oRecord.getData("riskCode") + "\">"+ oData+"</a>";
		}else if(oColumn.key =="powerEdit"){
				elCell.innerHTML="<a href=\"#\" onclick=\"showDlg('权限配置','/saaUserGrade/prepareUpdateUserGradePower.do?userCode=" + oRecord.getData("userCode") + "')\">配置</a>";
		}else if(oColumn.key =="status"){
			var statusStr = "";
			if(oRecord.getData("caseType") == null) {
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
			}else if(oRecord.getData("caseType")=="0"){
				statusStr = "已註銷";
	        } else if(oRecord.getData("caseType")=="1"){
	         	statusStr = "已拒賠";
	        } else if(oRecord.getData("caseType")=="2"){
	         	statusStr = "已結案";
	        }
        	elCell.innerHTML = statusStr
		}else if(oColumn.key =="operatorName"){
			elCell.innerHTML=oRecord.getData("operatorCode")+"("+oRecord.getData("operatorName")+")";
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
		{key:"status",label:"<s:text name="db.prpLclaimStatus.status" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--案件状态--%>
		{key:"claimNo",label:"<s:text name="db.prpLclaim.claimNo" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--立案号--%>
		{key:"registNo",label:"<s:text name="prompt.queRegist.RegistNo" />",width:"40em",sortable:true},<%--报案号--%>
		{key:"operatorName",label:"<s:text name="db.prpLlawsuit.operatorCode" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--操作员--%>
		{key:"operateDate",label:"<s:text name="db.prpLclaimStatus.operatedate" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}<%--操作时间--%>
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
  		alert("車險必須輸入立案號碼、備案號碼、保單號碼、牌照號碼、被保險人其中一項精確查詢！\n 非車險可以用立案號碼、備案號碼或者保單號碼的前9位進行模糊查詢！");
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
	var myDataSource = new YAHOO.util.DataSource("${ctx}/claim/claimQuery.do");
	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.connMethodPost = true; 
	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["status","claimNo","riskCode","registNo","operatorCode","operatorName","operateDate","caseType","reportDate","inputDate"],
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