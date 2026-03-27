<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>
 <%-- moidfy  update by tongziliang 2011-09-29 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<%
	String sex = (String) request.getAttribute("sex");
	String flag = (String) request.getAttribute("flag");
%>
<script language="javascript" src="${ctx}/common/js/newCalendar.js"></script>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script type="text/javascript" src="${ctx}/common/js/sinosoft.js"></script>
<script type="text/javascript" src="${ctx}/common/js/MulLine.js"></script>
<script type="text/javascript" src="${ctx}/common/js/Common.js"></script>
<script type="text/javascript" src="${ctx }/common/dtree/dtree.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>

</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<h2 align="center">用户信息</h2>
<s:form name="fm" action="">
	<s:hidden name="accSort" id="accSort" value="${accSort }"/>
		<s:hidden name="svrCode" value="${svrCode}"></s:hidden>
	<table width="100%" class="fix_table">
		<s:hidden name="utiIUser.userCode " />
		<tr>
			<td class="bgc_tt short">用户代码</td>
			<td class="long"><s:textfield name="userCode"
				value="${userCode}" id="userCode" cssClass='input_w w_30'
				maxlength="40" readonly="true" /></td>
			<td class="bgc_tt short">用户名称</td>
			<td class="long"><s:textfield name="userName"
				value="${userName}" id="userName" cssClass='input_w w_30'
				maxlength="30" readonly="true" /></td>

		</tr>
		<tr>
			<td class="bgc_tt short">用户类型</td>
			<td class="long">
			<div id="userTypeDiv1" class="selectui-indiv">
			<div class="selectConfig">
			<div class="codeType">StaticSelect</div>
			</div>
			<c:set var="checked" value="${userType}" /> <ce:select
				name="userType" cssClass="selectui-input" disabled="true"
				value="${checked}"
				list="#@java.util.HashMap@{'01':'员工用户','02':'业务员用户','03':'虚拟用户','04':'合作伙伴用户','06':'企业用户','07':'个人用户','98':'临时用户','99':'其他用户'}" />
			</div>
			</td>
			<td class="bgc_tt short">归属机构</td>
			<td class="long"><s:textfield name="comCode"
				value="${comCode}" id="comCode" cssClass='input_w w_30'
				maxlength="10" disabled="true" /></td>
		</tr>
		<tr align="center">
			<td colspan="4" >
			<button type="button" value="" onclick="executeQuery(1,10)"><span><em>查询账户</em></span></button>
<!--				<input type="button" class="button_ty" value="查询账户" onclick="executeQuery(1,10)">-->
			</td>
		</tr>
	</table>
		<div id="content_navigation" class="query" align="center"></div>
		<div id="content" class="sort"></div>
		<div id="content_navigation" class="query" align="center"></div>
	<table align="center">
		<tr>
			<td>
			<button type="button"  value="" onclick="prepareAddAccount()"><span><em>添加账户</em></span></button>
<!--				<input type="button" class="button_ty" value="添加账户" onclick="prepareAddAccount()">-->
			</td>
		</tr>
	</table>
</s:form></div>
</div>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">
var contentDataTable;
var contentColumnHeaders; 
YAHOO.namespace("query.container"); 

function init(){
	var userCode_tip = new YAHOO.widget.Tooltip("userCode_tip",{text:"请双击选择员工代码",context:"utiIUser.userCode",zIndex:300});
	var comCode_tip = new YAHOO.widget.Tooltip("comCode_tip",{text:"请双击选择机构代码",context:"utiIUser.comcode",zIndex:300});	
	 YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) { 
		 var oId = oRecord.accCode;
		 var validStatus = oRecord.validStatus;
		 if(oColumn.key=="accModify"){
    		  elCell.innerHTML = "<a href=\"#\" onclick=\"modifyMethod('"+oId+"');\">修改</a>";
	     }
		 if(oColumn.key=="accLogOrIn"){
			 if(validStatus == '0'){
				 elCell.innerHTML = "<a href=\"#\" onclick=\"logOutOrIn('"+oId+"');\">启动</a>";
			 }else{
    		  	elCell.innerHTML = "<a href=\"#\" onclick=\"logOutOrIn('"+oId+"');\">注销</a>";
			 }
	     }
		 if(oColumn.key=="accCode"){
			  elCell.innerHTML = "<a href=\"#\" onclick=\"viewMethod('"+oId+"');\">"+oId+"</a>";
		 }

		//效力状态的显示
	     if(oColumn.key=="validStatus"){
		      	switch(oRecord.validStatus){
		      		case '<%=IConstants.VALIDSTATUS_INVALID %>':elCell.innerHTML="无效";break;
		      		case '<%=IConstants.VALIDSTATUS_VALID %>':elCell.innerHTML="有效";break;
		      	}
		 } 
	 };
	
	contentColumnHeaders =[
		{key:"userName",text:"用户名称",width:"20em",sortable:true},
		{key:"accCode",text:"账户代码",width:"20em",sortable:true,type:"link"},
		{key:"accName",text:"账户名称",width:"30em",sortable:true},
		{key:"validStatus",text:"有效性",width:"10em",sortable:true,type:"link"},
		{key:"accModify",text:"修改",width:"10em",type:"link",resizeable:true},
		{key:"accLogOrIn",text:"注销/启动",width:"15em",type:"link",resizeable:true}
		]; 
}
//Query Data
function executeQuery(pageNo,pageSize){
	
	if(isNaN(parseInt(pageNo))){ 
		pageNo = 1;
	}
	if(isNaN(parseInt(pageSize))){
		pageSize = 10;
	}
	var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);	
	var myDataSource = new YAHOO.util.DataSource("${ctx}/utiIAccount/queryAccListByUserCode.do");
    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["userCode", "userName", "accCode" , "accName" , "validStatus","accModify","accLogOrIn" ],
	   totalRecords: "totalRecords"
	};
	myDataSource.subscribe("responseParseEvent",SINOSOFT.util.navigation);	
	myDataSource.connMgr.setForm(fm);
	var initialRequest = "pageSize="+pageSize+"&pageNo="+pageNo;
	var myConfiges ={
		initialRequest:initialRequest,
		paginator:false
	};   
	
		contentDataTable = new YAHOO.widget.DataTable("content",myColumnSet,myDataSource,myConfiges);
	
}

YAHOO.util.Event.addListener(window,'load',init);
 	 function prepareAddAccount(){
		fm.action = "${ctx}/utiIAccount/selectSvr.do";
		fm.submit();
   	 }
	/* function addAccount(){
		fm.action = "${ctx}/utiIAccount/preAddAccount.do?editType=update";
		fm.submit();
	 }
	 */
	 function viewMethod(aoCode){
			fm.action="${ctx}/utiIAccount/viewAccount.do?editType=view&accCode=" + aoCode;
	        fm.submit();
		}
		//调整账户
		function modifyMethod(aoCode){
			fm.action="${ctx}/utiIAccount/viewAccount.do?editType=update&accCode=" + aoCode;
		    fm.submit();
		}
	  //注销/启动
		function logOutOrIn(aoCode){
			if(confirm("确定要对所选数据进行操作？")){
				url = "contextRootPath/utiISvr/changeValidStatus.do?accCodes="+aoCode;
				var handleSuccess = function(o){
					var args = new SINOSOFT.util.QueryString( contentDataTable.initialRequest );
					var pageSize = parseInt(args["pageSize"],10);
					var pageNo = parseInt(args["pageNo"],10);
					executeQuery(pageNo,pageSize);
				};
				var callback =
				{
				  success:handleSuccess
				};
				var req = YAHOO.util.Connect.asyncRequest('POST', url, callback , "");
			}else{
				alert("操作已取消");
			}
	    }
</script>