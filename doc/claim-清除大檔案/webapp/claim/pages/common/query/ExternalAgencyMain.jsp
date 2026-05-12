<%--
****************************************************************************
* DESC       ：公估机构操作界面
* AUTHOR     ： weizeyu
* CREATEDATE ： 2009-03-06
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
<title>公估信息查询页面</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<script language="javascript">
	  <%--案件状态标志处理--%>
	  function submitForm(editType){
	    fm.action = "${ctx}/externalAgency/externalagency.do?editType="+editType;
	    fm.submit();//提交
	  }
	</script>
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
					  validStatusstr="<s:text name="query.flagTrue" />";
					}else if(oData=="0"){
					  validStatusstr="<s:text name="query.flagFalse" />";
				    }
					elCell.innerHTML = validStatusstr;
				}else if(oColumn.key =="id.comCode"){
					elCell.innerHTML = "<a href=\"${ctx}/externalAgency/externalagency.do?comCode="+oRecord.getData("id.comCode")+"&editType=show&comtype="+oRecord.getData("id.comtype") + "\">"+ oData+"</a>";
				}else if(oColumn.key =="id.comtype"){
					if(oData =='A'){
					  elCell.innerHTML = "<s:text name="query.publicPerson" />";<%--A-公估人 --%>
					}else if(oData=='S'){
					  elCell.innerHTML = "<s:text name="query.angent"/>";<%--S-代理人 --%>
					}else{
					  elCell.innerHTML = "";
					}
				}else if(oColumn.key =="operate"){
					//elCell.innerHTML ="ss";
					elCell.innerHTML =
					    "<a href=\"${ctx}/externalAgency/externalagency.do?editType=update&comCode="+oRecord.getData("id.comCode") + "\">"+"<s:text name="prompt.update" />"+"</a>"
						+"&nbsp;"+
						"<a href=\"${ctx}/externalAgency/externalagency.do?editType=delete&comCode="+oRecord.getData("id.comCode")+"&comtype="+oRecord.getData("id.comtype")+ "\">"+"<s:text name="prompt.del" />"+"</a>";
				} else{
					elCell.innerHTML = oData;
				}
			}; 
			contentColumnHeaders =[
			               		{key:"id.comCode",label:"<s:text name="query.assCode" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--公估代码--%>
			               		{key:"comcname",label:"<s:text name="query.chineseName" />",width:"40em"},<%--中文名称--%>
			               		{key:"id.comtype",label:"<s:text name="query.assessmentType" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--公估类型--%>
			               		{key:"juridicalperson",label:"<s:text name="query.legalPerson" />",width:"40em"},<%--法人--%>
			               		{key:"validStatus",label:"<s:text name="referlaw.validity" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--是否有效--%>
			               		{key:"operate",label:"<s:text name="replevy.operate"/>",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}<%--操作 --%>
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
			init();//用於删除机构後的回显，请勿删除
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
			var myDataSource = new YAHOO.util.DataSource("${ctx}/externalAgency/externalagency.do");
			myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
			myDataSource.connMethodPost = true; 
			myDataSource.responseSchema = {
			   resultsList: "data",
			   fields: [{key:"id.comCode"},{key:"id.comtype"},"comcname","juridicalperson","validStatus"],
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
</head>
<body onload="initPage();" class="yui-skin-sam">
	<form name="fm" action="${ctx}/externalAgency/externalagency.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td class='title'>
					<s:text name="query.publiquery.angentcAssessmentCode" />
					:
					<%--公估机构代码--%>
				</td>
				<td class='input'>
					<select class=tag name="ComCodeSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="ComCode" class="query">
				</td>
				<td class='title'>
					<s:text name="query.assessmentType" />
					:
					<%--公估类型 --%>
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
				<td class='title'>
					<s:text name="db.prpLclaimStatus.operatedate" />
					:
					<%--操作时间 --%>
				</td>
				<td class='input'>
					<select class=tag name="CreateTimeSign">
						<option value="=">=&nbsp;</option>
					</select>
					<%-- <input type=text name="CreateTime" class="Wdate" onClick="WdatePicker()">--%>
					<rc:rcDate name="CreateTime" style="width: 60%"/>
				</td>
				<td class="title">
					<s:text name="referlaw.validity" />
					:
					<%--是否有效 --%>
				</td>
				<td class='input'>
					<input type=radio name="Validstatus" value="1">
					<s:text name="query.flagTrue" />
					<%--有效--%>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type=radio name="Validstatus" value="0">
					<s:text name="query.flagFalse" />
					<%--无效--%>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="query.chineseName" />
					:
					<%--中文名称 --%>
				</td>
				<td class='input'>
					<select class=tag name="ComCNameSign">
						<option value="=">=</option>
					</select>
					<input type=text name="ComCName" class="query">
				</td>
				<td class='title'></td>
				<td class='input'></td>
			</tr>
			<div>
				<input type="hidden" name="editType" value="queryResult">
			</div>
			<tr>
				<td class='button' colspan="2">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="executeQuery(1,10,this);">
				</td>
				<td class='button' colspan="2">
					<input type=button class='button' value="<s:text name="prompt.add" />" <%--增加--%>
						onClick="submitForm('add');">
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<%--"="符号，必须精确查询。--%>
					<br>
					<s:text name="prompt.schedule.query2" />
					<%--"=*"符号，前匹配後模糊的查询。--%>
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
		<c:if test="${showflg=='true'}">
			<script type="text/javascript">
	executeQuery(1,10);//删除机构後的回显
	</script>
		</c:if>
	</form>
</body>
</html>