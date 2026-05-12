<%--
****************************************************************************
* DESC       ：定损查询输入界面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2013-02-24
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
  修改*操作符为=*将like '%%'改为左%右匹配查询
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
			if(oColumn.key =="id.registNo"){
			   elCell.innerHTML="<a  href=\"${ctx}/certainLoss/certainLossBeforeEdit.do?prpLverifyLossRegistNo="+oRecord.getData("id.registNo")+"&editType="+oRecord.getData("editType")+"&riskCode="+oRecord.getData("riskCode")+"&lossItemCode="+oRecord.getData("id.lossItemCode")+"&lossItemName="+oRecord.getData("lossItemName")+"&nodeType=${param.nodeType}" + "\">"+ oData+"</a>";
			}else if(oColumn.key =="status"){
			   if(oData=="0") {
					elCell.innerHTML = "未處理";
			   } else if(oData=="2"){
			        elCell.innerHTML = "正處理";
			   } else if(oData=="3"){
					elCell.innerHTML = "回退並處理";
			   } else if(oData=="4"){
					elCell.innerHTML = "已提交";
			   } else if(oData=="5"){
				    elCell.innerHTML = "已撤消";
			   }else{
			        elCell.innerHTML
			   }
			}else if(oColumn.key =="id.lossItemCode"){
			    if(oRecord.getData("id.nodeType")=='certa'){
			      elCell.innerHTML = oRecord.getData("id.lossItemCode")+"&nbsp;"+oRecord.getData("lossItemName");
			    }else{
			      elCell.innerHTML = oRecord.getData("lossItemName");
			    }
			}else if(oColumn.key =="defLossDate") {
				if(oData!=null){
					var date = new Date(oData.time);
					elCell.innerHTML = formatDate(date,'yyy-MM-dd');
				}
			}else if(oColumn.key =="relatepolicyNo") {
				if(oData!=null){
				    if(oData.length == 2){<%--交强险保单换行显示--%>
				       elCell.innerHTML = oData[0] + "<br>" + oData[1];
				    }else{
				       elCell.innerHTML = oData;
				    }
				}
			}else{
				elCell.innerHTML = oData;
			}
		}; 
		contentColumnHeaders =[
			 {key:"status",label:"<s:text name="db.prpLclaimStatus.status" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--案件状态--%>
			 {key:"id.registNo",label:"<s:text name="prompt.queRegist.RegistNo" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--报案号--%>
			 <c:if test="${param.nodeType!='wound'}">
			 	{key:"id.lossItemCode",label:"<s:text name="certainLoss.lossMarkName" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--损失标的名称--%>
			 </c:if>
			 <c:if test="${param.nodeType=='wound'}">
			 	{key:"id.lossItemCode",label:"<s:text name="title.wound.personName" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--损失标的名称--%>
			 </c:if>
			 {key:"relatepolicyNo",label:"<s:text name="prompt.queRegist.PolicyNo" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%--保单号--%>
			 {key:"handlerCode",label:"<s:text name="certainLoss.feeCode" />",width:"40em",sortable:true},<%--定损人代码--%>
			 {key:"defLossDate",label:"<s:text name="certainLoss.feeDateGeneration" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}<%--定损/代定损日期--%>
			];
	}
	/*
	 *@description:可以批次切分活动组别结果集 
	 *@param  pageNo，pageSize
	 *@return  活动组别结果集
	 *@author 中科软
	*/
	function executeQuery(pageNo,pageSize,field){
	  	if((fm.InsuredNameSign.value=="="&&fm.InsuredName.value.length>0)
	  		  	 ||(fm.LicenseNoSign.value=="="&&fm.LicenseNo.value.length>0)
	  		  	 ||(fm.RegistNoSign.value=="="&&fm.RegistNo.value.length>0)
	  		  	 ||(fm.PolicyNoSign.value=="="&&fm.PolicyNo.value.length>0)){
	  		  	 //输入了一个条件，可以查
	  		  	 }else if((fm.RegistNoSign.value=="=*"&&fm.RegistNo.value.length>8)
	  		  	        ||(fm.PolicyNoSign.value=="=*"&&fm.PolicyNo.value.length>8)){
	  		  	 	if("D"==getClassCodeType(fm.RegistNo.value.substr(1,2))
	  		  	 	 ||"D"==getClassCodeType(fm.PolicyNo.value.substr(1,2))){
	  		  	 		alert("車險必須精確查詢！");
	  		  	 		return false;
	  		  	 	}else{
	  		  	 		//非车险可以前9位模糊查询
	  		  	 	}  		
	  		  	}else{
	  		  		alert("車險必須輸入備案號碼、保單號碼、牌照號碼、被保險人其中一項精確查詢！\n 非車險可以用備案號碼或者保單號碼的前9位進行模糊查詢!");
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
		var myDataSource = new YAHOO.util.DataSource("${ctx}/certainLoss/queryCertainLoss.do");
		myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.connMethodPost = true; 
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: [{key:"id.registNo"},{key:"id.lossItemCode"},{key:"id.nodeType"},"policyNo", "handlerCode", "defLossDate", "operateDate", "status", "riskCode", "lossItemName","relatepolicyNo","editType"],
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
</head>
<body class="yui-skin-sam">
	<form name="fm" method="post" action="${ctx}/certainLoss/queryCertainLoss.do" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="certainLoss.queryCertainLoss" />
				</td>
			</tr>
			<%--查询定损信息--%>
			<tr>
				<td class='title'>
					<s:text name="prpLregist.registNo" />：
				</td>
				<%--报案号--%>
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="RegistNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.view_larrearage.policyNo" />：
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
				<c:choose>
					<c:when test="${'certa' != param.nodeType}">
						<td class='title'></td>
						<td class='input'>
							<input name="LicenseNoSign" type="hidden">
							<input name="LicenseNo" class="query" type="hidden">
						</td>
					</c:when>
					<c:otherwise>
						<td class='title'>
							<s:text name="db.prpLregist.licenseNo" />：
						</td>
						<td class='input'>
							<select class=tag name="LicenseNoSign">
								<option value="=">=</option>
								<option value="=*">=*</option>
							</select>
							<input type="text" name="LicenseNo" class="query">
						</td>
					</c:otherwise>
				</c:choose>
				<td class='title' style="text-align: left">
					<s:text name="db.prpLclaimStatus.operatedate" />:
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
					<%-- <input type=text name="OperateDate" class="Wdate" onClick="WdatePicker()"  value="">--%>
					<rc:rcDate name="OperateDate" value="" style="width:60%"/>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.status" />:
				</td>
				<%--案件状态--%>
				<td class='input' align="left">
					<input type="checkbox" name="status" value="2">
					<s:text name="common.status.intreating" />
					<%--正处理--%>
					<input type="checkbox" name="status" value="4">
					<s:text name="common.status.submited" />
					<%--已提交--%>
				</td>
				<%--报案查询增加被保险人查询条件--%>
				<td class='title'>
					<s:text name="db.view_larrearage.insuredname" />:
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
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<br>
					<%--"="符号，必须精确查询。--%>
					<s:text name="prompt.schedule.query2" />
					<br>
					<%--"=*"符号，前匹配後模糊的查询。--%>
					<s:text name="prompt.schedule.query3" />
					<br>
					<%--车险必须输入报案号、保单号、车牌号、被保险人其中一项精确查询！--%>
					<s:text name="prompt.schedule.query4" />
					<%--非车险可以用报案号或者保单号的前9位进行模糊查询！--%>
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name="button.query.value" />" onclick="executeQuery(1,10,this);">
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
</html>