<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>

<html>
<head>
<title>账户管理</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>

</head>

<body id="all_title">

<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">请输入查询条件</h2>
</div>
<s:form name="fm" action="" target="accountTreeRight">
<s:hidden name="accSort" id="accSort" value="${accSort }"/>
	<table class="fix_table">	
		<tr>
			<td class="bgc_tt short">用户代码</td>			
			<td class="long"><input type="text" name="utiIAccount.userCode"  class='input_w w_30' value="${userCode }" readonly></td>			
			<td class="bgc_tt short">用户名称</td>
			<td class="long"><input type="text" name="utiIAccount.userName"  class='input_w w_30' value="${userName }" readonly></td>
		</tr>
		<tr>
			<td class="bgc_tt short">账号代码</td>			
			<td class="long"><input type="text" name="utiIAccount.accCode"  class='input_w w_30'></td>			
			<td class="bgc_tt short">账号名称</td>
			<td class="long"><input type="text" name="utiIAccount.accName"  class='input_w w_30'></td>
		</tr>
		<tr>
			<td class="bgc_tt short">服务代码</td>			
			<td class="long"><input type="text" name="utiIAccount.svrCode"  class='input_w w_30' value="${svrCode }" readonly></td>			
			<td class="bgc_tt short">服务名称</td>
			<td class="long"><input type="text" name="utiIAccount.svrName"  class='input_w w_30' value="${svrName }" readonly></td>
		</tr>			
		<tr colspan="4">		
			<td class="bgc_tt short">有效标志</td>
			<td class="long">
			<c:set var="chk" value="a"/>
			<s:select name="utiIAccount.validStatus" value="${chk}"
					list="#@java.util.HashMap@{'a':'请选择','0':'无效','1':'有效'}" /></td>
		</tr>		
		<tr align="center">
			<td align="center"  colspan="4">
                <input type="button" class="button_ty" value="查询账户" onclick="executeQuery(1,10);">
                <input type="button" class="button_ty" value="添加sssss账户" onclick="addAccount()">
<!--                <input type="button" class="button_ty" value="查看账户" onclick="viewMethod()">-->
<!--                <input type="button" class="button_ty" value="账户调整" onclick="modifyMethod()">-->
<!--                <input type="button" class="button_ty" value="注销/启动" onclick="logOutOrIn()">-->
            </td>
		</tr>
	</table>
</s:form></div>
<div id="content_navigation" class="query" align="center"></div>
<div id="content" class="sort"></div>
<div id="content_navigation" class="query" align="center"></div>
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
		var myDataSource = new YAHOO.util.DataSource("${ctx}/utiIAccount/queryAccountList.do");
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


	//添加账户
	function addAccount(){
		fm.action = "${ctx}/utiIAccount/preAddAccount.do?editType=update";
		fm.submit();
	}
	//查看账户
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
	YAHOO.util.Event.addListener(window,'load',init);
    
	</script>