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
//add by duanfa20110815 start 查询结果排序时，序号不会改变/*
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
//add by duanfa20110815 end*/
function init() {
	YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) {
		if (oColumn.key == "checkbox") {
				elCell.innerHTML = "<input type='checkbox' name='regulationCode' value="+oRecord.regulationCode+" />";
			}
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
		if (oColumn.key == "viewImage") {
			//modify by duanfa20110921 查看影像
			if(oRecord.imagePath!=""){
				elCell.innerHTML = "<a href='"+oRecord.imagePath+"' target='_blank' >查看</a>";
			}else{
				elCell.innerHTML = "无影像文件";
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
		//add by duanfa20110815
		if (oColumn.key == "auditFlag") {
			switch (oRecord.auditFlag) {
			case '1':
				elCell.innerHTML = "新增";
				break;
			case '3':
				elCell.innerHTML = "修改";
				break;
			case '5':
				elCell.innerHTML = "修改打回";
				break;
			}
		}
		if (oColumn.key == "check") {
			var regulationCode = oRecord.regulationCode;
	    	 elCell.innerHTML = "<a href='checkPrpDregulation.do?editType=view&prpdRegulation.regulationCode="+oRecord.regulationCode+"'>审核</a>";
		}
	};
	contentColumnHeaders = [ {
		key : "checkbox",
		text : "选择",
		width : "5em",
		sortable : false,
		resizeable : true,
		type : "link"
	},{
		key : "regulationCode",
		text : "序号",
		width : "10em",
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
	}, {
		key : "validStatus",
		text : "状态",
		width : "15em",
		sortable : true,
		resizeable : true,
		type : "link"
	}, {
		//add by duanfa20110815
		key : "auditFlag",
		text : "操作类型",
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
		key : "check",
		text : "审核",
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
			"queryCheckPrpDregulation.do");

	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.responseSchema = {
		resultsList : "data",
		//modify by duanfa20110921
		fields : [ "regulationCode", "fileName", "fileCode","cityCode", "validDate", "validStatus", "operate",
				"viewImage", "disable","proviceCode","countyCode","auditFlag","imagePath" ],
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

function addNewRegulation(){
	if(document.getElementById("regulationType").value==''){
		alert("请选择条例类型");
	}else{
		
		editRecord('prepareInsertPrpDregulation.do?editType=insert&prpdRegulation.regulationType='+document.getElementById("regulationType").value);
	}
}
