<%--
****************************************************************************
* DESC       ：賠款帳戶查詢頁面
* mantis：CLM0075 ，處理人員：BK007  蘇哲，需求單編號：CLM0075.理賠系統-修改或刪除已失效匯款帳戶
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<head>
<title>賠款帳戶查詢頁面</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<script language="javascript">
	  function submitForm(editType){
	    fm.action = "${ctx}/ctbcins/paymentAccount/paymentAccountQuery.do?editType="+editType;
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
				}else if(oColumn.key =="accountCode"){
					elCell.innerHTML = "<a href=\"${ctx}/ctbcins/paymentAccount/paymentAccountView.do?paymentAccount.accountCode="+oData+"\">"+ oData+"</a>";
				}else if(oColumn.key =="operate"){
					elCell.innerHTML =
					    "<a href=\"${ctx}/ctbcins/paymentAccount/paymentAccountEdit.do?paymentAccount.accountCode="+oRecord.getData("accountCode")+"\">"+"<s:text name="prompt.update" />"+"</a>"
				} else{
					elCell.innerHTML = oData;
				}
			}; 
			contentColumnHeaders =[
			               		{key:"accountCode",label:"<s:text name="db.prpLcompensate.account" />",width:"40em",formatter:YAHOO.widget.DataTable.formatLink},<%--銀行帳號--%>
			               		{key:"certificateCode",label:"<s:text name="account.accountOwnershipPersonCode" />",width:"40em",formatter:YAHOO.widget.DataTable.formatLink},<%--帳號歸屬人證件代碼--%>
			               		{key:"bankName",label:"總行名稱",width:"40em"},<%--總行名稱--%>
			               		{key:"customBankName",label:"分行名稱",width:"40em"},<%--分行名稱--%>
			               		{key:"ownerName",label:"帳戶歸屬/支付對象名稱 ",width:"40em"},<%--帳戶歸屬人姓名(支付對象帳戶名稱)--%>
			               		{key:"operatorCode",label:"<s:text name="account.operatorCode" />",width:"40em"},<%--操作人員代碼--%>
			               		{key:"validStatus",label:"<s:text name="referlaw.validity" />",width:"40em",formatter:YAHOO.widget.DataTable.formatLink},<%--是否有效--%>
			               		{key:"operate",label:"<s:text name="replevy.operate"/>",width:"40em",formatter:YAHOO.widget.DataTable.formatLink}<%--操作 --%>
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
			var myDataSource = new YAHOO.util.DataSource("${ctx}/ctbcins/paymentAccount/paymentAccountQuery.do");
			myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
			myDataSource.connMethodPost = true; 
			myDataSource.responseSchema = {
			   resultsList: "data",
			   fields: [{key:"accountCode"},"certificateCode","bankName","customBankName","ownerName","operatorCode","validStatus"],
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
	<form name="fm" action="${ctx}/ctbcins/paymentAccount/paymentAccountQuery.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					賠款帳戶查詢頁面
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLcompensate.account" />
					:
				</td>
				<td class='input'>
					<select class=tag name="accountCodeSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<!-- mantis：CLM0151，處理人員：DP0713，需求單編號：新核心理賠-賠款帳戶維護查詢錯誤問題排除 -->
					<input type=text name="accountCode" class="query" onkeyup="value=value.replace(/[^(\d)]/g,'')" onchange="value=value.replace(/[^(\d)]/g,'')">
				</td>
				<td class='title'>
					<s:text name="account.accountName" />
					:
					<%--中文名称 --%>
				</td>
				<td class='input'>
					<select class=tag name="accountNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="accountName" class="query">
				</td>
			</tr>
			<div>
				<input type="hidden" name="editType" value="queryResult">
			</div>
			<tr>
				<td class='button' colspan="2">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="executeQuery(1,10,this);">
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