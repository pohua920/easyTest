<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>员工岗位</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>

</head>

<body id="all_title">

<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">请输入查询条件</h2>
</div>
<s:form name="fm" action="findUser" namespace="/userGrade" method="post">
<s:hidden name="flag" id="flag"></s:hidden>
	<table class="fix_table">	
		<tr>
			<td class="bgc_tt short">员工代码</td>			
			<td class="long"><input name="saaUser.userCode"
				id="saaUser.userCode" class='input_y w_p90'
				ondblclick="code_CodeQuery(this, 'UserCode', '0,1', 'Y','')"
				onkeyup="code_CodeQuery(this, 'UserCode', '0,1', 'Y','')"
				onchange="code_CodeChange(this, 'UserCode', '0,1', 'Y','')"/></td>			
			<td class="bgc_tt short">员工名称</td>
			<td class="long"><input name="saaUser.userName"
				id="saaUser.userName" class='input_w w_30'></td>
		</tr>		
		<tr>
			<td class="bgc_tt short">归属机构代码</td>
			<td class="long"><input name="saaUser.comCode"
				id="saaUser.comCode" class='input_y w_p90'
				ondblclick="code_CodeQuery(this, 'ComCode', '0,1', 'Y','')"
				onkeyup="code_CodeQuery(this, 'ComCode', '0,1', 'Y','')"
				onchange="code_CodeChange(this, 'ComCode', '0,1', 'Y','')"/></td>
			
			<td class="bgc_tt short">归属机构名称</td>
			<td class="long"><input name="comName"
				id="comName" class='input_w w_30'></td>
		</tr>
		<tr>
			<td colspan="4" align="center"><input type="button"
				class="button_ty" value="查 询" onclick="executeQuery(1,10);">
				<!--  class="button_ty" value="查 询" onclick="executeQuery(1,10);userPowerFlag='user';"> -->
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
	var userCode_tip = new YAHOO.widget.Tooltip("userCode_tip",{text:"请双击选择员工代码",context:"saaUser.userCode",zIndex:300});
	var comCode_tip = new YAHOO.widget.Tooltip("comCode_tip",{text:"请双击选择机构代码",context:"saaUser.comCode",zIndex:300});		
		 YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) { 
		    var oId = oRecord.userCode;
		     if(oColumn.key=="gradeEdit"){
	    		 elCell.innerHTML = "<a href=\"#\" onclick=\"editRecordBig('${ctx}/saaUserGrade/prepareUpdateUserGrade.do?userCode=" + oId+"')\">配置</a>";
		      }
		      if(oColumn.key=="powerEdit"){
	    		  elCell.innerHTML = "<a href=\"#\" onclick=\"editRecordBig('${ctx}/saaUserGrade/prepareUpdateUserGradePower.do?userCode=" + oId+"')\">配置</a>";
		      }
		      if(oColumn.key=="powerGrantEdit"){
	    		  elCell.innerHTML = "<a href=\"#\" onclick=\"editRecordBig('${ctx}/saaUserPower/prepareUpdateUserGradePower.do?userCode=" + oId+"')\">配置</a>";
		      }
		      if(oColumn.key=="validStatus"){
		      	switch(oRecord.validStatus){
		      		case '0':elCell.innerHTML="无效";break;
		      		case '1':elCell.innerHTML="有效";break;
		      	}
		      }
		 };	
		
		if(document.getElementById("flag").value=="powerGrant"){
			contentColumnHeaders =[
				{key:"userCode",text:"员工代码",width:"40em",sortable:true},
				{key:"userName",text:"员工姓名",width:"40em",sortable:true},
				{key:"comCode",text:"归属机构代码",width:"50em",sortable:true},
				 {key:"validStatus",text:"有效标志",width:"50em",sortable:true,type:"link"},
				{key:"powerGrantEdit",text:"分级授权",width:"20em",type:"link",resizeable:true}
				];
			}else{
			 contentColumnHeaders =[
				{key:"userCode",text:"员工代码",width:"40em",sortable:true},
				{key:"userName",text:"员工姓名",width:"40em",sortable:true},
				{key:"comCode",text:"归属机构代码",width:"50em",sortable:true},
				 {key:"validStatus",text:"有效标志",width:"50em",sortable:true,type:"link"},
				{key:"gradeEdit",text:"岗位配置",width:"20em",type:"link",resizeable:true},
				{key:"powerEdit",text:"权限配置",width:"20em",type:"link",resizeable:true}
				];	
			} 
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
		var myDataSource = new YAHOO.util.DataSource("${ctx}/saaUser/queryUser.do");
		myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["userCode", "userName", "comCode" , "validStatus"],
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