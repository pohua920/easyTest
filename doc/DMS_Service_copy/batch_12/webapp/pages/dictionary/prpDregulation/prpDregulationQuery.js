var contentDataTable;
var contentColumnHeaders;
var deployCom ;
var regulationType ;
var i=0;
YAHOO.namespace("query.container");

function initJsp(){
	deployCom = document.getElementById("deployCom").value;
	regulationType = document.getElementById("regulationType");
}
function init() {
//add by duanfa20110803 start 查询结果排序时，序号不会改变/*
YAHOO.widget.DataTable.prototype.newFun = YAHOO.widget.DataTable.prototype.sortColumn;
YAHOO.widget.DataTable.prototype.sortColumn = function (oColumn) {
	var args = new SINOSOFT.util.QueryString(contentDataTable.initialRequest);
	var pageSize = parseInt(args["pageSize"], 10);
	var pageNo = parseInt(args["pageNo"], 10);
	if (isNaN(parseInt(pageNo))) {
		pageNo = 1;
	}
	if (isNaN(parseInt(pageSize))) {
		pageSize = 10;
	}
	i = pageSize*(pageNo-1);
	this.newFun(oColumn);
};
//add by duanfa20110803 end*/
	YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) {
		if (oColumn.key == "regulationCode") {
				i++;
				elCell.innerHTML = "<a href='#' onclick=editRecord(\"prepareUpdatePrpDregulation.do?editType=view&prpdRegulation.regulationCode="+oRecord.regulationCode+"\")>" + i + "</a>";
			}
		if (oColumn.key == "validDate") {
		 	var date = new Date(oRecord.validDate.time);
			elCell.innerHTML = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate() ;
		}
		if (oColumn.key == "cityCode") {
			elCell.innerHTML =oRecord.proviceCode+"-"+oRecord.cityCode+"-"+oRecord.countyCode ;
		}
		if (oColumn.key == "operate") {
		var regulationCode = oRecord.regulationCode;
			var regulationCode = oRecord.regulationCode;
			var valid = oRecord.validStatus;
	    	if(valid == "1"){
				elCell.innerHTML = "<a href='#' onclick=editRecord(\"prepareUpdatePrpDregulation.do?editType=update&prpdRegulation.regulationCode="+oRecord.regulationCode+"\") >修改</a>";
			}else{
				elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('"+regulationCode+"','"+valid+"')\">启用</a>";
			}
			
		}
		if (oColumn.key == "viewImage") {
			//modify by duanfa20110921 查看影像
			if(oRecord.imagePath!=""){
				elCell.innerHTML = "<a href='"+oRecord.imagePath+"' target='_blank' >查看</a>";
			}else{
				elCell.innerHTML = "无影像文件";
			}
		}
		
		if (oColumn.key == "auditFlag") {
			//modify by duanfa 20110729 start
			//switch (oRecord.validStatus) {
			switch (oRecord.auditFlag) {
			//modify by duanfa 20110729 end
			case '0':
				elCell.innerHTML = "未审核";
				break;
			case '1':
				elCell.innerHTML = "审核通过";
				break;
			case '2':
				elCell.innerHTML = "审核打回";
				break;
			}
		}
		if (oColumn.key == "validStatus") {
			switch (oRecord.validStatus) {
			case '0':
				elCell.innerHTML = "无效";
				break;
			case '1':
				elCell.innerHTML = "有效";
				break;
			}
		}
		if (oColumn.key == "disable") {
			var regulationCode = oRecord.regulationCode;
			var valid = oRecord.validStatus;
	    	if(valid == "1"){
	    	 	elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('"+regulationCode+"','"+valid+"')\">注销</a>";
	    	}else{
	    		//modify by duanfa20110915
	    		//elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('"+regulationCode+"','"+valid+"')\">启用</a>";
	    		elCell.innerHTML = "已注销";
	    	}
		}
	};
	contentColumnHeaders = [ {
		key : "regulationCode",
		//modify by duanfa20110808
		//text : "选择",
		text : "序号",
		width : "15em",
		sortable : false,
		resizeable : true,
		type : "link"
	}, {
		key : "fileName",
		text : "文件名称",
		width : "15em",
		sortable : true,
		resizeable : true
	}, {
		key : "fileCode",
		text : "文号",
		width : "15em",
		sortable : true,
		resizeable : true
	},  {
		key : "cityCode",
		text : "适用范围",
		width : "15em",
		sortable : true,
		resizeable : true,
		type : "link"
	},{
		key : "validDate",
		text : "生效日期",
		width : "15em",
		sortable : true,
		resizeable : true,
		type : "link"
	},{
		key : "auditFlag",
		text : "审核状态",
		width : "15em",
		sortable : true,
		resizeable : true,
		type : "link"
	}, {
		key : "validStatus",
		text : "状态",
		width : "15em",
		sortable : true,
		resizeable : true,
		type : "link"
	}, {
		key : "operate",
		text : "操作",
		width : "15em",
		sortable : true,
		resizeable : true,
		type : "link"
	}, {
		key : "viewImage",
		text : "查看影像",
		width : "15em",
		sortable : true,
		resizeable : true,
		type : "link"
	}, {
		key : "disable",
		//modify by duanfa 20110728
//		text : "注销",
		text : "注销/启用",
		width : "15em",
		sortable : true,
		resizeable : true,
		type : "link"
	} ];

	executeQuery(1, 10);
}
// Query Data
function executeQuery(pageNo, pageSize) {
	if (isNaN(parseInt(pageNo))) {
		pageNo = 1;
	}
	if (isNaN(parseInt(pageSize))) {
		pageSize = 10;
	}
	i = pageSize*(pageNo-1);
	var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);
	var myDataSource = new YAHOO.util.DataSource(
			"queryPrpDregulation.do");

	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.responseSchema = {
		resultsList : "data",
		//modify by duanfa20110921
		fields : [ "regulationCode", "fileName", "fileCode","cityCode", "validDate","auditFlag", "validStatus", "operate",
				"viewImage", "disable","proviceCode","countyCode","imagePath"],
		totalRecords : "totalRecords"
	};
	myDataSource.subscribe("responseParseEvent", SINOSOFT.util.navigation);
	myDataSource.connMgr.setForm(fm);
	var initialRequest = "pageSize=" + pageSize + "&pageNo=" + pageNo;
	var myConfiges = {
		initialRequest : initialRequest,
		paginator : false
	};
	contentDataTable = new YAHOO.widget.DataTable("content", myColumnSet,
			myDataSource, myConfiges);
}
YAHOO.util.Event.addListener(window, 'load', init);
function deleteMethod() {
	var chkbox = document.getElementsByName('chkbox');
	var flag = false;
	var checkedValue = "";
	if (chkbox.length == 0) {
		alert("没有选中列！");
	} else {
		for ( var j = 0; j < chkbox.length; j++) {
			if (chkbox[j].checked) {
				flag = true;
				if (checkedValue == "") {
					checkedValue = chkbox[j].value;
				} else {
					checkedValue += "," + chkbox[j].value;
				}
			}
		}
		if (flag) {
			// deleteRecord('${ctx}/dictionary/deletePrpDplane.do?chkbox='+checkedValue);
		} else {
			alert("没有选中列！");
		}
	}
}
function changeValidStatus(regulationCode , valid) {
	var result;
	if (valid == "1") {
		result = "确定要注销吗？";
	} else {
		result = "确定要启用吗？";
	}
	if (confirm(result)) {
		var url = "${ctx}/dictionary/changeRegulationStatus.do?regulationCode="
				+ regulationCode;
		var handleSuccess = function(o) {
			var args = new SINOSOFT.util.QueryString(
					contentDataTable.initialRequest);
			var pageSize = parseInt(args["pageSize"], 10);
			var pageNo = parseInt(args["pageNo"], 10);
			executeQuery(pageNo, pageSize);
		};
		var handleFailure = function(o) {
			if (o.responseText !== undefined) {
				var msg = i18n.errors.deletefail + "!\n" + o.status + " "
						+ o.statusText;
				// alert("操作失败！");
			}
		};
		var callback = {
			success : handleSuccess,
			failure : handleFailure
		};
		var callback = {
			success : handleSuccess,
			failure : handleFailure
		};
		var req = YAHOO.util.Connect.asyncRequest('POST', url, callback, "");
	}
}
function addNewRegulation(){
	if(document.getElementById("regulationType").value==''){
		alert("请选择条例类型");
	}else{
		
		editRecord('prepareInsertPrpDregulation.do?editType=insert&prpdRegulation.regulationType='+document.getElementById("regulationType").value);
	}
}

 function(a, b, desc, field) {
            var compare = YAHOO.util.Sort.compare,
                sorted = compare(a.getData(field),b.getData(field), desc);
            if(sorted === 0) {
                return compare(a.getCount(),b.getCount(), desc); // Bug 1932978
            }
            else {
                return sorted;
            }
        }
function initPageIndex(a, b, desc, field){
	var args = new SINOSOFT.util.QueryString(contentDataTable.initialRequest);
	var pageSize = parseInt(args["pageSize"], 10);
	var pageNo = parseInt(args["pageNo"], 10);
	if (isNaN(parseInt(pageNo))) {
		pageNo = 1;
	}
	if (isNaN(parseInt(pageSize))) {
		pageSize = 10;
	}
	i = pageSize*(pageNo-1);
	
	alert(field);
	return YAHOO.util.Sort.compareDesc(a.regulationCode, b.regulationCode);
}