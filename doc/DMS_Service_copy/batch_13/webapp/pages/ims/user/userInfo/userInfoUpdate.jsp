<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>
<html>
<head>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>

<script type="text/javascript" src="${ctx}/common/js/sinosoft.js"></script>
<script type="text/javascript">
	function test(){
		init();
		executeQuery(1,10);
	}
</script>
</head>
<body id="all_title" onload="test()">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">用户信息更新</h2>
</div>
<s:form name="fm" action="" method="post">
<s:hidden name="comCode" id="comCode" value="${comCode}"></s:hidden>
<s:hidden name="userCode" id="userCode" value="${userCode }"></s:hidden>
<s:hidden name="svrName" id="svrName" value="${svrName}"></s:hidden>
<s:hidden name="userSort" id="userSort" value="${userSort }"></s:hidden>
<s:hidden name="accName" id="accName" value="${accName }"></s:hidden>
<s:hidden name="name" id="name" value="${atrrsName }"></s:hidden>
<!--	<table width="100%" class="fix_table" >-->
<!--		<tr>-->
<!--			<td>选择</td>-->
<!--			<td colspan="2" align="center"></td>-->
<!--		</tr>-->
<!--		<s:iterator value="#request.atrrsName" id="names" status="status">-->
<!--		    <tr>-->
<!--		    	<td class='input_w w_15'><input type="checkbox" name="checkBoxes" value="<s:property value="names" />" ></input></td>-->
<!--		   		<td class="bgc_tt class='input_w w_15'" ><s:property value="names" /></td>-->
<!--		   		<td class="bgc_tt class='input_w w_15'" ><s:property value="#request.atrrsValue[#status.index]" /></td>-->
<!--			</tr>-->
<!--		</s:iterator>-->
<!--	</table>-->
</s:form>
	<div id="content" class="sort"></div>
	<div id="content_navigation" class="query" align="center"></div>
</div>
</div>
	<table width="100%" class="fix_table">
		<tr align="center" class="top" >
			<td align="center"><input type="button" value="全选" class="button_ty" onclick="selectAll();"/></td>
			<td align="center"><input type="button" value="更新" class="button_ty" onclick="update();"/></td>
		</tr>
	</table>
</body>
</html>
<%@include file="/common/meta_js.jsp"%>
<script language="javascript">
var flag = '0';
var contentDataTable;
var contentColumnHeaders; 
YAHOO.namespace("query.container"); 
function init(){
	YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) {
		var oCode = oRecord.attrName;
		if(flag == '0'){
			if(oColumn.key=="serialNo"){
	  		    elCell.innerHTML = "<input type=\"checkbox\" name=\"checkboxes\" value="+oCode+" />";
		    }
		}else if(flag == '1'){
			if(oColumn.key=="serialNo"){
	  		    elCell.innerHTML = "<input type=\"checkbox\" name=\"checkboxes\" value="+oCode+" checked=\"true\"/>";
		    }
		} 
		
	    if(oColumn.key=="value"){
		    if(oRecord.attrName=="性别"){
			    if(oRecord.value=="0"){
			    	elCell.innerHTML="男";
				}else{
					elCell.innerHTML="女";
			    }
		    }else{
		    	elCell.innerHTML=oRecord.value;
			}
	    }
	};
	contentColumnHeaders =[
		{key:"serialNo",text:"选择",width:"20em",sortable:true,type:"link"},
		{key:"attrName",text:"账户信息名称",width:"20em",sortable:true},
		{key:"value",text:"账户信息",width:"30em",sortable:true,type:"link"}
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

	var myDataSource = new YAHOO.util.DataSource("${ctx}/utiIUser/getAtrrList.do");

    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;

	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["attrName" , "value"],
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

	function update(){
		//var values = new Array();
		var checkbox = document.getElementsByName("checkboxes");
		//values = document.getElementById("name").value;
		var num = 0;
		var values = "";
        for(var j=0;j<checkbox.length;j++){
			if(checkbox[j].checked){
				num = num + 1;
				values = values + '-' + checkbox[j].value;
			}
        }
        if(num == 0){
        	alert("请选择一项进行修改");
        }else{
        	fm.action="${ctx}/utiIUser/userInfoUpdate.do?values="+values;
		    fm.submit();
	        }
        return true;
	}

	function selectAll(){
		flag = "1";
		executeQuery(1,10);
	}
	

</script>