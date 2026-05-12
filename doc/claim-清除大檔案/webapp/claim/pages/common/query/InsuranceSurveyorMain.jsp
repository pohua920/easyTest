<%--
****************************************************************************
* DESC       ：公估师操作界面
* AUTHOR     ： liuwei
* CREATEDATE ： 2011-05-07
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<head>
<title><s:text name="query.publicOperateView" /></title>
<%--公估师操作界面 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script type="text/javascript">
var isFirstLoad = true;
var contentDataTable;
var contentColumnHeaders;
YAHOO.namespace("query.container");

function init(){
	YAHOO.widget.DataTable.formatLink = function(elCell, oRecord, oColumn, oData) {
		    if(oColumn.key =="validStatus"){
			var validStatusstr="";
			if(oData=="1"){
				validStatusstr="是";
				}
			else if(oData=="0"){
				validStatusstr="否";
				}
			elCell.innerHTML = validStatusstr;
			}else if(oColumn.key =="id.comCode"){
				elCell.innerHTML="<a href=\"${ctx}/externalAgency/insuranceSurveyor.do?&editType=show&comCode="+oRecord.getData("id.comCode")+"&newcomcode="+oRecord.getData("id.newcomcode") + "\">"+oData+"</a>";
				}
			else if(oColumn.key =="operateupd"){
				elCell.innerHTML =
					"<a href=\"${ctx}/externalAgency/insuranceSurveyor.do?editType=update&comCode="+oRecord.getData("id.comCode")+"&newcomcode="+oRecord.getData("id.newcomcode") + "\">"+"修改"+"</a>";
				}
			else{
				elCell.innerHTML = oData;
			}
	
	}; 
	contentColumnHeaders =[
	               		{key:"id.comCode",label:"<s:text name="query.surveyorCode" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--公估师代码--%>
	               		{key:"comcname",label:"<s:text name="query.chineseName" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--中文名称--%>
	               		{key:"newComCName",label:"<s:text name="query.publicTeam" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--公估机构--%>
	               		{key:"comType",label:"<s:text name="query.assessmentType" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--公估类型--%>
	               		{key:"validStatus",label:"<s:text name="referlaw.validity" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--是否有效--%>
	               		{key:"operateupd",label:"<s:text name="prompt.update" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}<%--修改--%>
	               		];
}
YAHOO.util.Event.addListener(window,'load',init);
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
function executeQuery(pageNo,pageSize,field){
	//init();
	if(validateForm(fm)){
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
	var myDataSource = new YAHOO.util.DataSource("${ctx}/externalAgency/insuranceSurveyor.do?editType=queryResult");
	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.connMethodPost = true; 
	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: [{key:"id.comCode"},{key:"id.newcomcode"},"comcname","newComCName","comType","validStatus"],
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
}
</script>
<script language="javascript">
<%--案件状态标志处理--%>
  function submitForm(editType)
  {
    fm.action = "/claim/externalAgency/externalagency.do?editType="+editType;
    fm.submit();//提交
  }
 
</script>
</head>
<body onload="initPage();" class="yui-skin-sam">
	<form name="fm" method="post">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="query.queryPublicInformation" />
					<%--查询公估师信息 --%>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="query.publicTeam" />
					<%--公估机构 --%>
					:
				</td>
				<td class='input'>
					<input type=text name="NewComCode" class="codecode" title="公估機構代碼" value="" ondblclick="code_CodeSelect(this, 'getExternalAgency','0,1','Y');"
						onchange="code_CodeSelect(this, 'getExternalAgency','0,1','Y');" onkeyup="code_CodeSelect(this, 'getExternalAgency','0,1','Y');" style="width: 25%">
					<input type=text name="NewComCName" class="codename" title="公估機構名稱" value="" style="width: 55%" ondblclick="code_CodeSelect(this, 'getExternalAgency','-1,0','Y','N');"
						onchange="code_CodeSelect(this, 'getExternalAgency','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'getExternalAgency','-1,0','Y','N');">
				</td>
				<td class='title'>
					<s:text name="query.surveyorCode" />
					:
					<%--公估师代码 --%>
				</td>
				<td class='input'>
					<select class=tag name="ComCodeSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="ComCode" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="query.chineseName" />
					<%--中文名称 --%>
					:
				</td>
				<td class='input'>
					<select class=tag name="ComCNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="ComCName" class="query">
				</td>
				<td class='title'>
					<s:text name="query.assessmentType" />
					<%--公估类型 --%>
					:
				</td>
				<td class='input'>
					<input type=radio name="ComType" value="A">
					<s:text name="query.publicPerson" />
					<%--A-公估人 --%>
					<input type=radio name="ComType" value="S">
					<s:text name="query.angent" />
					<%--S-代理人 --%>
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="query.ifUsefull" />
					:
					<%--是否有效 --%>
				</td>
				<td class='input'>
					<input type=radio name="Validstatus" value="1">
					<s:text name="regist.prpLregist.yes" />
					<%--是 --%>
					<input type=radio name="Validstatus" value="0">
					<s:text name="regist.prpLregist.no" />
					<%--否 --%>
				</td>
				<td class="title" style="color: red" colspan="2">
					<s:text name="prompt.schedule.query1" />
					<%--"="符号，必须精确查询。 --%>
					<s:text name="prompt.schedule.query2" />
					<%--"=*"符号，前匹配後模糊的查询。 --%>
				</td>
			</tr>
			<tr>
				<td class='button' colspan="2">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="executeQuery(1,10,this);">
				</td>
				<td class='button' colspan="2">
					<input type=button class='button' value="<s:text name="prompt.add" />" onClick="return insertMethod();">
					<%--增加 --%>
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
</body>
<script language="javascript">
function iFrameHeight() {   
    var ifm= document.getElementById("QueryResultFrame");   
    var subWeb = document.frames ? document.frames["QueryResultFrame"].document : ifm.contentDocument;   
    if(ifm != null && subWeb != null) {
        ifm.height = subWeb.body.scrollHeight;
    }   
}   
function insertMethod(){
	fm.action="/claim/pages/common/query/InsuranceSurveyorEdit.jsp?editType=insert";
	//fm.target="QueryResultFrame";
	fm.submit();
	return true;
}
</script>
</html>