<%--
****************************************************************************
* DESC       ：代理人手机号码查询界面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2013-03-10
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<title><s:text name="title.query.agentClaimsMaintainQueryPage" /></title>
<%--代理人理赔维护查询页面 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script type="text/javascript">
	var isFirstLoad = true;
	var contentDataTable;
	var contentColumnHeaders;
	YAHOO.namespace("query.container");
	<%--
	 *@description:初始化查询结果页面
	 *@param varSigns 无
	 *@return boolean 活动组别结果集    
	 *@author 中科软
	--%>
	function init(){
		YAHOO.widget.DataTable.formatLink = function(elCell, oRecord, oColumn, oData) {
			if(oColumn.key =="radio"){
			   elCell.innerHTML="<input type=radio name=checkboxEdit value="+oRecord.getData("agentCode")+" >";
			}else{
			   elCell.innerHTML = oData;
			}
		}; 
		contentColumnHeaders =[
			 {key:"radio",label:"<s:text name="archive.choice" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--选择--%>
			 {key:"agentCode",label:"<s:text name="db.prpDagent.agentCode" />",width:"40em",sortable:true},<%--代理人代码--%>
			 {key:"agentName",label:"<s:text name="db.prpDagent.agentName" />",width:"40em",sortable:true},<%--代理人名称--%>
			 {key:"mobileNo",label:"<s:text name="query.mobilePhone" />",width:"40em",sortable:true}<%--手机号码--%>
			];
		executeQuery(1,10);
	}
	<%--
	 *@description:可以批次切分活动组别结果集 
	 *@param  pageNo，pageSize
	 *@return  活动组别结果集
	 *@author 中科软
	 --%>
	function executeQuery(pageNo,pageSize){
		if(isNaN(parseInt(pageNo))){
			pageNo = 1;
		}
		if(isNaN(parseInt(pageSize))){
			pageSize = 10;
		} 
		var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);	
		var myDataSource = new YAHOO.util.DataSource("${ctx}/AgentMobile.do?editType=select&AgentCode=${param.AgentCode}&AgentName=${param.AgentName}");
		myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.connMethodPost = true; 
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["agentCode", "agentName", "mobileNo"],
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
</head>
<body onload="initPage();" class="yui-skin-sam">
	<form name="fm" action="${ctx}/AgentMobile.do" method="post" onsubmit="return validateForm(this);">
		<table width=100%>
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="query.agentPhoneQueryShow" />
					<%--代理人手机号码查询展示 --%>
				</td>
			</tr>
			<tr>
			<tr>
				<td colspan="4">
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
			<tr>
				<td class='button' colspan="2">
					<input type=button class='button' value="<s:text name='button.delete.value' />" onClick="submitForm('delete');">
				</td>
				<td class='button' colspan="2">
					<input type=button class='button' value="<s:text name="button.edit.value" />" onClick="submitForm('update');">
					<%--修改 --%>
				</td>
			</tr>
		</table>
	</form>
</body>
<script language="javascript">
function submitForm(editType){
	var agentCode = "";
	var flag = false;
	var checkboxEdit = document.getElementsByName("checkboxEdit");
	for (var i = 0; i < checkboxEdit.length; i++) {
		if (checkboxEdit[i].checked==true) {
			agentCode = checkboxEdit[i].value;
			flag = true;
			break;
		}
	}
	if (!flag || agentCode=="") {
		if("update"==editType){
			alert("请选择一条记录进行修改!");
		}
		if("delete"==editType){
			alert("请选择一条记录进行删除!");
		}
		return false;
	}
	fm.action = "${ctx}/AgentMobile.do?editType="+editType+"&agentCode="+agentCode;
	fm.submit();
	return true;
}
</script>
</html>