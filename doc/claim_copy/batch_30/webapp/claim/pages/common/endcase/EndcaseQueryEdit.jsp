<%@ page contentType="text/html; charset=GBK"%>
<%--
****************************************************************************
* DESC       ：结案查询输入界面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-06-28
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
							zhangshi		20130201			修改*操作符为=*将like '%%'改为左%右匹配查询
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<title><s:text name="title.claimBeforeEdit.titleName" /></title>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script language="javascript">
  <%--案件状态标志处理--%>
  
  function isSubmitForm()
  {
      if((fm.InsuredNameSign.value=="="&&fm.InsuredName.value.length>0)
  	 ||(fm.RegistNoSign.value=="="&&fm.RegistNo.value.length>0)
  	 ||(fm.PolicyNoSign.value=="="&&fm.PolicyNo.value.length>0)
  	 ||(fm.ClaimNoSign.value=="="&&fm.ClaimNo.value.length>0)
  	 ||(fm.CaseNoSign.value=="="&&fm.CaseNo.value.length>0)){
  	 //输入了一个条件，可以查
  	 }else if((fm.RegistNoSign.value=="=*"&&fm.RegistNo.value.length>8)
  	        ||(fm.PolicyNoSign.value=="=*"&&fm.PolicyNo.value.length>8)
  	        ||(fm.ClaimNoSign.value=="=*"&&fm.ClaimNo.value.length>8)
  	        ||(fm.CaseNoSign.value=="=*"&&fm.CaseNo.value.length>8)){
  	 	if("D"==getClassCodeType(fm.RegistNo.value.substr(1,2))
  	 	 ||"D"==getClassCodeType(fm.PolicyNo.value.substr(1,2))
  	 	 ||"D"==getClassCodeType(fm.ClaimNo.value.substr(1,2))
  	 	 ||"D"==getClassCodeType(fm.CaseNo.value.substr(1,2))){
  	 		alert("車險必須精確查詢！");
  	 		return false;
  	 	}else{
  	 		//非车险可以前9位模糊查询
  	 	}  		
  	}else{
  		alert("車險必須輸入賠案號碼、結案號碼、備案號碼、保單號碼、被保險人其中一項精確查詢！\n非車險可以用賠案號碼、結案號碼、備案號碼或者保單號碼的前9位進行模糊查詢！");
  		return false;
  	}
    return true;
    //fm.submit();//提交
  }
</script>
</head>
<%-- <body  onload="initPage();"> --%>
<body class="yui-skin-sam">
	<form name="fm" action="${ctx }/endcase/endcaseQuery.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="title.endcaseBeforeEdit.titleName" />
				</td>
			</tr>
			<!-- 查询结案信息 -->
			<tr>
				<td class='title'>
					<s:text name="check.claimNum" />:
				</td>
				<!-- 赔案号 -->
				<td class='input'>
					<select class=tag name="ClaimNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="ClaimNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLcompensate.caseNo" />:
				</td>
				<!-- 结案号 -->
				<td class='input'>
					<select class=tag name="CaseNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="CaseNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLcfee.policyNo" />:
				</td>
				<!-- 保单号 -->
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="PolicyNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.operatedate" />:
				</td>
				<!-- 操作时间 -->
				<td class='input'>
					<select class=tag name="OperateDateSign">
						<%@ include file="/pages/common/pub/CommonStringOption.jsp"%>
					</select>
					<%-- <input type=text name="OperateDate" class="Wdate" onClick="WdatePicker()" >--%>
					<rc:rcDate name="OperateDate" style="width:60%"/>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLrepairFee.registNo" />：
				</td>
				<!-- 报案号 -->
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="RegistNo" class="query">
				</td>
				<td class='title'>
					<s:text name="endcase.insuranceAgent" />:
				</td>
				<!-- 承保机构 -->
				<td class='input'>
					<select class=tag name="comCodeSign">
						<option value="=">=</option>
						<!--<option value="=*">=*</option>-->
					</select>
					<input type=text name="comCode" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="prpLclaim.claimDate" />:
				</td>
				<!-- 立案时间 -->
				<td class='input'>
					<select class=tag name="claimDateSign">
						<%@ include file="/pages/common/pub/CommonStringOption.jsp"%>
					</select>
					<%-- <input type=text name="claimDate" class="Wdate" onClick="WdatePicker()"> --%>
					<rc:rcDate name="claimDate" style="width:60%"/>
				</td>
				<!--报案查询增加被保险人查询条件-->
				<td class='title'>
					<s:text name="db.prpCmain.insuredName" />:
				</td>
				<!-- 被保险人名称 -->
				<td class='input'>
					<select class=tag name="InsuredNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="InsuredName" class="query">
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<br>
					<!-- "="符号，必须精确查询。 -->
					<s:text name="prompt.schedule.query2" />
					<br>
					<!-- "=*"符号，前匹配後模糊的查询。 -->
					<s:text name="endcase.query1" />
					<br>
					<!-- 车险必须输入赔案号、结案号、报案号、保单号、被保险人其中一项精确查询！ -->
					<s:text name="endcase.query2" />
					<!-- 非车险可以用赔案号、结案号、报案号或者保单号的前9位进行模糊查询！ -->
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="executeQuery(1,10,this);">
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
		<input type="hidden" name="nodeType" value="${param.nodeType }">
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
		if(oColumn.key =="claimNo"){
			elCell.innerHTML="<a href='${ctx}/endcase/endcaseFinishQueryList.do?prpLendcaseEndcaseNo="+oData+"&editType=SHOW&riskCode="+oRecord.getData("riskCode")+"&ClaimNoSign=*'>"+oData+"</a>";
		}else if(oColumn.key =="endCaserCode"){
			elCell.innerHTML=oData+"("+oRecord.getData("endCaserName")+")";
		}else if(oColumn.key =="endCaseDate") {
			if(oData!=null){
				var date = new Date(oData.time);
				elCell.innerHTML = formatDate(date,'yyy-MM-dd');
			}
		}else{
			elCell.innerHTML = oData;
		}
	}; 
	contentColumnHeaders =[
		{key:"claimNo",label:"<s:text name="check.claimNum" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}, <%--赔案号--%>       
		{key:"caseNo",label:"<s:text name="db.prpLcompensate.caseNo" />",width:"40em",sortable:true},<%--结案号--%>                                            
		{key:"policyNo",label:"<s:text name="prompt.queRegist.PolicyNo" />",width:"40em",sortable:true},<%--保单号--%>                                         
		{key:"endCaserCode",label:"<s:text name="endcase.closed" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--结案员--%>    
		{key:"endCaseDate",label:"<s:text name="recase.closingTime" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}<%--结案时间--%>
		];
}

/*
 *@description:可以批次切分活动组别结果集 
 *@param  pageNo，pageSize
 *@return  活动组别结果集
 *@author 中科软
*/
function executeQuery(pageNo,pageSize,field){
	//增加查询的判断只能精确查询
	var isSubmit = isSubmitForm();
	if(!isSubmit){
		return;
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
	var myDataSource = new YAHOO.util.DataSource("${ctx }/endcase/endcaseQuery.do");
	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.connMethodPost = true; 
	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["status","claimNo","riskCode","caseNo","policyNo","endCaserCode","endCaserName","endCaseDate"],
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
</html>