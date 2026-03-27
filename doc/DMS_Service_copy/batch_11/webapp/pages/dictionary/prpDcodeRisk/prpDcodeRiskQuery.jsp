<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>险种代码</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">
	var contentDataTable;
	var contentColumnHeaders; 
	YAHOO.namespace("query.container");
	
    function init() {
        YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn,oData) {
        	var riskCode    = oRecord["id.riskCode"];
        	var codeType    = oRecord["id.codeType"];
        	var codeCode    = oRecord["id.codeCode"];
            if(oColumn.key == "chkbox"){
            	elCell.innerHTML = "<input type='checkbox' name='chkbox' value=''>";
            }
            if (oColumn.key == "id.riskCode") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDcodeRisk.do?editType=view&prpDcodeRisk.id.riskCode="
						+ riskCode
						+ "&prpDcodeRisk.id.codeType="
						+ codeType
						+ "&prpDcodeRisk.id.codeCode="
						+ codeCode
						+ "')\">" + oData + "</a>"
			}
			if (oColumn.key == "id.codeType") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDcodeRisk.do?editType=view&prpDcodeRisk.id.riskCode="
						+ riskCode
						+ "&prpDcodeRisk.id.codeType="
						+ codeType
						+ "&prpDcodeRisk.id.codeCode="
						+ codeCode
						+ "')\">" + oData + "</a>"
			}
			if (oColumn.key == "id.codeCode") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDcodeRisk.do?editType=view&prpDcodeRisk.id.riskCode="
						+ riskCode
						+ "&prpDcodeRisk.id.codeType="
						+ codeType
						+ "&prpDcodeRisk.id.codeCode="
						+ codeCode
						+ "')\">" + oData + "</a>"
			}
			if (oColumn.key == "edit") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDcodeRisk.do?prpDcodeRisk.id.riskCode="
						+ riskCode
						+ "&prpDcodeRisk.id.codeType="
						+ codeType
						+ "&prpDcodeRisk.id.codeCode="
						+ codeCode
						+ "&editType=update')\">修改</a>";
			}
        };
        contentColumnHeaders = [
          {key :"chkbox",text :"选择",width :"15em",sortable :false,resizeable :true,type :"link"},
          {key :"id.riskCode",text :"险种代码",width :"15em",sortable :true,resizeable :true,type:"link"},
          {key :"id.codeType",text :"代码类型",width :"15em",sortable :true,resizeable :true,type:"link"},
          {key :"id.codeCode",text :"业务代码",width :"15em",sortable :true,resizeable :true,type:"link"},
          {key :"edit",text :"修改",width :"10em",type :"link",resizeable :true}
          //{key :"status",text :"注销/启用",width :"20em",type :"link",resizeable :true}
          ];
        }
    //Query Data
    function executeQuery(pageNo,pageSize) {
        if (isNaN(parseInt(pageNo))) {
            pageNo = 1;
        }
        if (isNaN(parseInt(pageSize))) {
            pageSize = 10;
        }
        var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);
        var myDataSource = new YAHOO.util.DataSource("${ctx}/dictionary/queryPrpDcodeRisk.do");
        
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
        myDataSource.responseSchema = {
            resultsList:"data",
            fields:["id.riskCode","id.codeType","id.codeCode"],
            totalRecords:"totalRecords"
        };
        myDataSource.subscribe("responseParseEvent", SINOSOFT.util.navigation);
        myDataSource.connMgr.setForm(fm);
        var initialRequest = "pageSize=" + pageSize + "&pageNo=" + pageNo;
        var myConfiges = {
            initialRequest :initialRequest,
            paginator :false
        };
        contentDataTable = new YAHOO.widget.DataTable("content", myColumnSet,myDataSource, myConfiges);
    }
    YAHOO.util.Event.addListener(window,'load',init);
    function deleteMethod(){
    	var chkbox = document.getElementsByName('chkbox');
    	var flag = false;
    	var checkedValue="";
    	if(chkbox.length==0){
			alert("没有选中列！");
        }else{
        	for(var j=0;j<chkbox.length;j++){
				if(chkbox[j].checked){
					flag = true;
					if(checkedValue==""){
						checkedValue=chkbox[j].value;
					}else{
						checkedValue+=","+chkbox[j].value;
					}
				}
			}
			if(flag){
				//deleteRecord('${ctx}/dictionary/deletePrpDplane.do?chkbox='+checkedValue);
			}else{
				alert("没有选中列！");
			}
         }
    }
	function changeValidStatus(url){
		if(confirm("确定要注销/启用？")){
		var handleSuccess = function(o){
			var args = new SINOSOFT.util.QueryString( contentDataTable.initialRequest );
			var pageSize = parseInt(args["pageSize"],10);
			var pageNo = parseInt(args["pageNo"],10);
			executeQuery(pageNo,pageSize);
		};
		var handleFailure = function(o){
			if(o.responseText !== undefined){
				var msg = i18n.errors.deletefail+"!\n"+ o.status +" " + o.statusText;
				//alert("操作失败！");
			}
		};
		var callback =
		{
		  success:handleSuccess,
		  failure:handleFailure
		};
		var req = YAHOO.util.Connect.asyncRequest('POST', url, callback, "");
		}
	}
</script>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">请输入查询条件</h2>
</div>
<s:form name="fm" action="queryPrpDcodeRisk" namespace="/dictionary" method="post">
<s:hidden name="flag" id="flag"></s:hidden>
<table class="fix_table">	
	<tr>
		<td class="bgc_tt short">险种代码</td>			
		<td class="long">
			<s:textfield name="prpDcodeRisk.id.riskCode" id="riskCode" cssClass='input_w w_15' maxlength="" />
		</td>
		<td class="bgc_tt short">代码类型</td>
		<td class="long">
			<s:textfield name="prpDcodeRisk.id.codeType" id="codeType" cssClass="input_w w_15" maxlength="30" />
		</td>
	</tr>  
	<tr>
		<td colspan="4" valign="baseline" nowrap class="bgc_tt short">
		<button type="button"  value="" onclick="executeQuery(1,10);"><span><em>查 询</em></span></button>
		<button type="button"  value="" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDcodeRisk.do?editType=insert');"><span><em>增 加</em></span></button>
<!--			<input type="button" class="button_ty" value="查 询" onclick="executeQuery(1,10);">-->
<!--			<input type="button" class="button_ty" value="增 加" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDcodeRisk.do?editType=insert');">-->
		</td>
	</tr>
</table>
</s:form></div>
<div id="content_navigation" class="query" align="right"></div>
<div id="content" class="sort"></div>
<div id="content_navigation" class="query" align="right"></div>
</div>
</body>
</html>
