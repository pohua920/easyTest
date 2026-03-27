<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>

<html>
<head>
<title>用户查询</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<script type="text/javascript">
	function test(){
		init();
		executeQuery(1,10);
	}
</script>
</head>
<body id="all_title" onload="test()">
<div id="container">
<div id="crash_menu">
<h2 align="center">请输入查询条件</h2>
</div>
<s:form name="fm" action="" target="accountTreeRight">
<s:hidden name="svrCode" value="${svrCode}"></s:hidden>
	<table class="fix_table">	
		<tr>
			<td class="bgc_tt short">用户代码</td>			
			<td class="long"><input type="text" name="utiIUser.userCode"  class='input_w w_15'></td>			
			<td class="bgc_tt short">用户名称</td>
			<td class="long"><input type="text" name="utiIUser.userName"  class='input_w w_15'></td>
		</tr>		
		<tr>
			<td class="bgc_tt short">归属机构</td>
			<td class="long"><div id="validStatusMapDiv" class="selectui-indiv">
			        <div class="selectConfig">
			        <div class="codeType">StaticSelect</div>
			        </div>
			        <c:set var="checked" value="0" />
			        <ce:select name="utiIUser.comCode" id="utiIUser.comCode" cssClass="selectui-input-up input_w w_30" value="${checked}" list="comCodeMap" />
			    </div>
			</td>
			<td class="bgc_tt short">用户类型</td>			
			<td class="long">
			<c:set var="checked" value="0" />
			<s:select name="utiIUser.userType" value="${checked}"
					list="#@java.util.HashMap@{'0':'全部','01':'员工用户','02':'业务员用户',
                    '03':'虚拟用户','04':'合作伙伴用户',
                    '06':'企业客户','07':'个人客户',
                    '98':'临时用户','99':'其他用户'}"  /></td>
		</tr>
		</table>
		
</s:form></div>
		<table>	
		<tr align="center">
			<td align="center" >
                <input type="button" class="button_ty" value="查询用户" onclick="executeQuery(1,10);">
            </td>
		</tr>
	   </table>
<div id="content_navigation" class="query" align="center"></div>
<div id="content" class="sort"></div>
<div id="content_navigation" class="query" align="center"></div>

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
		//var userCode_tip = new YAHOO.widget.Tooltip("userCode_tip",{text:"请双击选择员工代码",context:"utiIUser.userCode",zIndex:300});
		//var comCode_tip = new YAHOO.widget.Tooltip("comCode_tip",{text:"请双击选择机构代码",context:"utiIUser.comcode",zIndex:300});	
		 YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) { 
			 var oId = oRecord.userCode;
			 if(oColumn.key=="serialNo"){
	    		  elCell.innerHTML = "<a href='${ctx}/utiIAccount/preAccountManage.do?userCode=" + oId + "'>账户管理</a>";
		     } 
		     //效力状态的显示
		     if(oColumn.key=="validStatus"){
			      	switch(oRecord.validStatus){
			      		case '<%=IConstants.VALIDSTATUS_INVALID %>':elCell.innerHTML="无效";break;
			      		case '<%=IConstants.VALIDSTATUS_VALID %>':elCell.innerHTML="有效";break;
			      	}
			 }
		     //用户类型的显示
		     if(oColumn.key=="userType"){
			      	switch(oRecord.userType){
			      	  case '<%=IConstants.USERTYPE_STUFF %>':elCell.innerHTML="员工用户";break;
		      		  case '<%=IConstants.USERTYPE_SALES %>':elCell.innerHTML="业务员用户";break;
		      		  case '<%=IConstants.USERTYPE_VIRTUAL %>':elCell.innerHTML="虚拟用户";break;
		      		  case '<%=IConstants.USERTYPE_PARTNERS %>':elCell.innerHTML="合作伙伴用户";break;
		      		  case '<%=IConstants.USERTYPE_ENTERPRISE %>':elCell.innerHTML="企业客户";break;
		      		  case '<%=IConstants.USERTYPE_PERSONAL %>':elCell.innerHTML="个人客户";break;
		      		  case '<%=IConstants.USERTYPE_TEMPORARY %>':elCell.innerHTML="临时用户";break;
		      		  case '<%=IConstants.USERTYPE_OTHER %>':elCell.innerHTML="其他用户";break;
			      	}
			 }
		 };
		
		contentColumnHeaders =[
			{key:"userCode",text:"用户代码",width:"40em",sortable:true},
			{key:"userName",text:"用户名称",width:"40em",sortable:true},
			{key:"userType",text:"用户类型",width:"40em",sortable:true,type:"link"},
			{key:"comCode",text:"归属机构代码",width:"30em",sortable:true},
			{key:"validStatus",text:"有效性",width:"20em",sortable:true,type:"link"},
			{key:"serialNo",text:"操作",width:"20em",type:"link",resizeable:true}
			
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
		var myDataSource = new YAHOO.util.DataSource("${ctx}/utiIAccount/queryUserList.do");
	    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["userCode", "userName", "userType" , "comCode" , "validStatus"],
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
    
	</script>