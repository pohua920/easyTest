<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>

<html>
<head>
<title>账户信息查询</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>

</head>

<body id="all_title">

<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">请输入查询条件</h2>
</div>
<s:form name="fm" action="" >
	<table class="fix_table">	
		<tr>
			<td class="bgc_tt short">用户代码</td>			
			<td class="long"><input type="text" name="utiIAccount.userCode"  class='input_w w_30'></td>			
			<td class="bgc_tt short">用户名称</td>
			<td class="long"><input type="text" name="utiIAccount.userName"  class='input_w w_30'></td>
		</tr>		
		<tr>
			<td class="bgc_tt short">账户代码</td>			
			<td class="long"><input type="text" name="utiIAccount.accCode"  class='input_w w_30'></td>			
			<td class="bgc_tt short">账户名称</td>
			<td class="long"><input type="text" name="utiIAccount.accName"  class='input_w w_30'></td>
		</tr>	
		<tr>
			<td class="bgc_tt short">服务代码</td>			
			<td class="long"><input name="utiIAccount.utiISvr.svrCode" 
					  class='input_y w_p90' id="svrCode" 
				ondblclick="code_CodeQuery(this, 'SvrCode', '0,1', 'Y','')"
				onkeyup="code_CodeQuery(this, 'SvrCode', '0,1', 'Y','')"
				onchange="code_CodeChange(this, 'SvrCode', '0,1', 'Y','')"/>
				</td>		
			<td class="bgc_tt short">服务名称</td>
			<td class="long"><input type="text" name="utiIAccount.svrName"  class='input_w w_30'></td>
		</tr>	
		<tr>			
			<td class="bgc_tt short">有效标志</td>
			<td class="long"><s:select name="utiIAccount.validStatus"
					list="#@java.util.HashMap@{'a':'请选择','0':'无效','1':'有效'}"  /></td>
		</tr>
		
				
		<tr align="center">
			<td align="center"  colspan="4">
                <input type="button" class="button_ty" value="查询" onclick="executeQuery(1,10);">
                <input type="button" class="button_ty" value="修改" onclick="updateAccountInfo()">
                <input type="button" class="button_ty" value="查看" onclick="viewAccountInfo()">
                <input type="button" class="button_ty" value="账户信息同步" onclick="accInfoSynch()">
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
		var svrCode_tip = new YAHOO.widget.Tooltip("svrCode_tip",{text:"请双击选择服务代码",context:"svrCode",zIndex:300});	
		 YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) { 
			 var oId = oRecord.accCode;
			 if(oColumn.key=="serialNo"){
				 elCell.innerHTML = "<input type=\"checkbox\" name=\"checkboxes\" value="+oId+" ></input>";
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
			{key:"userCode",text:"用户代码",width:"40em",sortable:true},
			{key:"userName",text:"用户名称",width:"40em",sortable:true},
			{key:"accCode",text:"账号代码",width:"40em",sortable:true},
			{key:"accName",text:"账号名称",width:"40em",sortable:true},
			{key:"svrName",text:"服务名称",width:"40em",sortable:true},
			{key:"validStatus",text:"有效性",width:"30em",sortable:true,type:"link"},
			{key:"serialNo",text:"操作",width:"30em",type:"link",resizeable:true}
			
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
		var myDataSource = new YAHOO.util.DataSource("${ctx}/utiIAccount/queryUtiIAccountList.do");
	    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["userCode", "userName", "accCode" , "accName" , "svrName" , "validStatus"],
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

	function updateAccountInfo(){
		var checkbox = document.getElementsByName("checkboxes");
		var num = 0;
        for(var j=0;j<checkbox.length;j++){
			if(checkbox[j].checked){
				num = num + 1;
			}
        }
        if(num == 0){
        	alert("请选择一项进行修改");
        }
        else if(num>1){
			alert("只能选择一项进行修改");
        }else{
	        for(var i=0;i<checkbox.length;i++){
				if(checkbox[i].checked){
					aoCode = checkbox[i].value ;
					fm.action="${ctx}/utiIAccount/prepareUpdateAccountInfo.do?editType=update&accCode=" + aoCode;
			        fm.submit();
				}
	        }
        }
	}

	function viewAccountInfo(){
		var checkbox = document.getElementsByName("checkboxes");
		var num = 0;
        for(var j=0;j<checkbox.length;j++){
			if(checkbox[j].checked){
				num = num + 1;
			}
        }
        if(num == 0){
        	alert("请选择一项进行修改");
        }
        else if(num>1){
			alert("只能选择一项进行修改");
        }else{
	        for(var i=0;i<checkbox.length;i++){
				if(checkbox[i].checked){
					aoCode = checkbox[i].value ;
					fm.action="${ctx}/utiIAccount/prepareViewAccountInfo.do?editType=view&accCode=" + aoCode;
			        fm.submit();
				}
	        }
        }
	}

	function accInfoSynch(){
		var checkbox = document.getElementsByName("checkboxes");
		var num = 0;
        for(var j=0;j<checkbox.length;j++){
			if(checkbox[j].checked){
				num = num + 1;
			}
        }
        if(num == 0){
        	alert("请选择一项进行修改");
        }
        else if(num>1){
			alert("只能选择一项进行修改");
        }else{
	        for(var i=0;i<checkbox.length;i++){
				if(checkbox[i].checked){
					aoCode = checkbox[i].value ;
					fm.action="${ctx}/utiIAccount/prepareAccInfoSynch.do?accCode=" + aoCode;
			        fm.submit();
				}
	        }
        }
	}
    
	</script>