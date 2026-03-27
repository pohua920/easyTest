<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
<%@ include file="/common/meta.jsp"%>
<title><s:text name="role.manage"/></title>
<link href="${ctx}/styles/query.css" type="text/css" rel="stylesheet">
<script type="text/javascript">
	YAHOO.namespace("query.container");
	var isFirstLoad = true;
	var contentDataTable;
	var contentColumnHeaders; 

	function init(){
		YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) { 
		    var oId = oRecord.id;
		    if(oColumn.key=="edit"){
		    	elCell.innerHTML = "<a href=\"#\" onclick=\"editRecord('./prepareUpdate_" + oId + ".do')\"><s:text name='prompt.edit'/></a>"; 
		    }else if(oColumn.key=="del"){
		    	elCell.innerHTML = "<a href=\"#\" onclick=\"deleteRecord('./delete_" + oId + ".do')\"><s:text name='prompt.del'/></a>";
		    }else if(oColumn.key=="rolename"){
		    	elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('./view_" + oId + ".do')\">"+oData+"</a>";
		    }
		    else{
		    	elCell.innerHTML = oData;
		    }
		}; 
		contentColumnHeaders =[
			{key:"id",text:"ID",width:"15em",sortable:true},
			{key:"rolename",text:"<s:text name='role.rolename'/>",width:"10em",sortable:true,type:"link"},
			{key:"privilegesFlag",text:"<s:text name='role.privilegesFlag'/>",width:"16em",sortable:true,editor:"textbox"},
			{key:"edit",text:"<s:text name='prompt.edit'/>",width:"20em",type:"link"},
			{key:"del",text:"<s:text name='prompt.del'/>",width:"20em",type:"link"}
			]; 
	
		//---init dialog 
		initEditDialog("dialogEdit","editFrame");
		initShowDialog("dialogShow","showFrame");
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
		var myDataSource = new YAHOO.util.DataSource("./query.do");
		myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["id","rolename","privilegesFlag","edit","del"],
		   totalRecords: "totalRecords"
		};

		myDataSource.subscribe("responseParseEvent", SINOSOFT.util.navigation);
		myDataSource.connMgr.setForm(form1);
		var initialRequest = "pageSize="+pageSize+"&pageNo="+pageNo;
		var myConfiges ={
			initialRequest:initialRequest,
			paginator:false
		};   
					
		if (isFirstLoad==true){
			contentDataTable = new YAHOO.widget.DataTable("content",myColumnSet,myDataSource,myConfiges);
			contentDataTable.subscribe("cellClickEvent",contentDataTable.onEventSelectRow);
			contentDataTable.subscribe("cellClickEvent",contentDataTable.onEventEditCell);
			isFirstLoad = false;	
		}else{
			contentDataTable._oRecordSet = new YAHOO.widget.RecordSet();
			contentDataTable.initialRequest = initialRequest;
			contentDataTable.dataSource = myDataSource;
			contentDataTable.dataSource.sendRequest(contentDataTable.initialRequest, contentDataTable.onDataReturnPopulateTable, contentDataTable);
		}			
	}
	//BatchUpdate
	function submitTable(url, dataTable, keys) {
		var dynamicForm = "<form id=\"batchUpdateForm\" name=\"batchUpdateForm\" action=\"saveAll.do\" method=\"post\">";

		if(dataTable == null) {
			return;
		}
		var rs = dataTable.getRecordSet();
		for(var i = 0; rs != null && i < rs.getLength(); i++) {
			var obj = rs.getRecord(i);
			
			for(var j = 0; keys != null && j < keys.length; j++) {
			  	var key = keys[j];
			  	dynamicForm += "<input type=\"hidden\" name=\"" + key + "s\" value=\"" + eval("obj." + key) + "\">";
			}
		}
		
		dynamicForm += "</form>";
		document.getElementById("batchUpdateDiv").innerHTML = dynamicForm;
		var formObj = document.getElementById("batchUpdateForm");
		//formObj.submit();
	
		YAHOO.util.Connect.setForm(formObj); 
		var handleSuccess = function(o) {
			
			var msg = o.responseText;
			document.getElementById("submitSuccess").innerHTML = msg;
		}
		var handleFailure = function(o) {
			if(o.statusText != null) {
				var msg = o.statusText + "<br><s:text name='message.batchupdate.failure'/>";
				document.getElementById("submitFailure").innerHTML = msg;
			}
		}
		var callback = {
			success:handleSuccess,
		 	failure:handleFailure		 	
		} 
		
		var cObj = YAHOO.util.Connect.asyncRequest('POST', url, callback); 
	}
	
	YAHOO.util.Event.addListener(window,'load',init);
</script>
</head>
<body id="all_title">
	<div id="container">
		<div id="header">
			<h1><s:text name="role.manage"/></h1>
		</div>
		
		<div id="case_detail" class="query">
			<form name="form1" id="fm">
				<table class="fix_table" border="0">
					<tr>
						<td class="bgc_tt short" ><s:text name="role.rolename"/></td>
						<td class="long"><input type="text" name="role.rolename" value="" class='input_w w_15'/></td>
						<td class="bgc_tt short"><button type="button" id="querybtn" onclick="executeQuery(1,10)" class="button_ty">
						<s:text name="prompt.query"/>
					</button></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="content_navigation" name="content_navigation" class="example"></div>
		<div id="content" class="sort"></div>
		<div id="content_navigation" name="content_navigation" class="example"></div>
	
		<div id="dialogEdit">
			<div class="hd"><s:text name="prompt.edit"/></div>
			<div class="bd">
				<iframe id="editFrame" src="about:blank"></iframe>
			</div>
		</div>
		<div id="dialogShow">
			<div class="hd"><s:text name="prompt.show"/></div>
			<div class="bd">
				<iframe id="showFrame" src="about:blank"></iframe>
			</div>
		</div>
	
		<br>
		<div id="batchUpdateDiv"></div>
		<div>
		<p align="center">
		    <button type="button" id="saveallbtn" name="saveallbtn" onclick="submitTable('./saveAll.do', contentDataTable, ['id', 'rolename', 'privilegesFlag']);" class="button_ty">
		    	<s:text name="prompt.batchupdate"/>
		    </button>&nbsp;&nbsp
			<button type="button" id="addbtn" onclick="location.href='prepareInsert.do'" class="button_ty">
				<s:text name="prompt.add"/>
			</button>
			<button type="button" id="batchAddbtn" onclick="location.href='prepareBatchInsert.do'" class="button_ty">
				<s:text name="role.batchAdd"/>
			</button>
			</p>
		</div>
		<br>
		<div id="submitSuccess"></div>
		<div id="submitFailure"></div>
	</div>


	<s:fielderror/>

	<%@ include file="/common/footer.jsp" %>
</body>
</html>