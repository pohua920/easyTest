<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>

<html>
<head>
<title>用户查看</title>
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
<div id="wrapper">
<div id="container">
<h2 align="center">用户信息更新</h2>
</div>
	<s:form name="fm" action="">
	<s:hidden name="comCode" id="comCode" value="${comCode}"></s:hidden>
	<s:hidden name="userCode" id="userCode" value="${userCode }"></s:hidden>
	<s:hidden name="svrName" id="svrName" value="${svrName}"></s:hidden>
	<s:hidden name="userSort" id="userSort" value="${userSort }"></s:hidden>
	<s:hidden name="accName" id="accName" value="${accName }"></s:hidden>
	<s:hidden name="name" id="name" value="${atrrsName }"></s:hidden>
	<s:hidden name="checkBoxes" id="checkBoxes" ></s:hidden>
		<table class="fix_table">
		</table>
	</s:form>
</div>

	<div id="content" class="sort"></div>
	<div id="content_navigation" class="query" align="center"></div>
<table>
	<tr align="center" class="top" >
		<td align="center"><input type="button" value="更新" class="button_ty" onclick="update();"/></td>
	</tr>
</table>

</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script type="text/javascript">
var contentDataTable;
var contentColumnHeaders; 
YAHOO.namespace("query.container"); 

function init(){
	YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) { 
		 var attrName = oRecord.attrName;
		 if(oColumn.key=="serialNo"){
   		  elCell.innerHTML = "<input type=\"checkbox\" name=\"checkboxes\" value="+attrName+" ></input>";
	     } 
		 if(oColumn.key=="value"){
		      	if(oRecord.value=="0"){
		      		elCell.innerHTML="男";
			    }else if(oRecord.value=="1"){
			    	elCell.innerHTML="女";
				}else{
					elCell.innerHTML=oRecord.value;
				}
		   }
	 };

	
	contentColumnHeaders =[
		{key:"serialNo",text:"序号",width:"20em",type:"link",resizeable:true},
		{key:"attrName",text:"账户信息名称",width:"20em",sortable:true},
		{key:"value",text:"账户信息值",width:"30em",sortable:true,type:"link"}
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
		var checkbox = document.getElementsByName("checkboxes");
		var values = "test";
        var num = 0;
        for(var j=0;j<checkbox.length;j++){
			if(checkbox[j].checked){
				num = num + 1;
				values = values+"-"+checkbox[j].value;
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
	
</script>
