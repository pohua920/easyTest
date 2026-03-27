<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.sync.SyncConstants"%>
<%
	String deployCom = (String)session.getAttribute("deployCom");
%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<html>
<head>
<title>国管局项目一级预算单位</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>

</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">请输入查询条件</h2>
</div>
<s:form name="fm" action="queryPrpDTreatyReten" namespace="/dictionary" method="post">
<s:hidden name="flag" id="flag"></s:hidden>
<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
<table class="fix_table">	
	<tr>
		<td class="bgc_tt short">预算单位代码</td>			
		<td class="long">
		<input name="prpDsettlementByr.buyerUnitCode" id="buyerUnitCode" class='input_w w_15 dt-date dc-chk dt-nzhs' maxlength="">
		</td>
		<td class="bgc_tt short">预算单位名称</td>
		<td class="long">
			<s:textfield name="prpDsettlementByr.buyerUnitName" id="buyerUnitName" cssClass="input_w w_15" maxlength="30" />
		</td>
	</tr>  
	<tr>
		<td colspan="4" valign="baseline" nowrap class="bgc_tt short">
		<button type="button"  value="" onclick="executeQuery(1,10);"><span><em>查 询</em></span></button>
<!--			<input type="button" class="button_ty" value="查 询" onclick="executeQuery(1,10);">-->
			<% if(SyncConstants.ComCode_Head.equals(deployCom)){%>
			<button type="button"  value="" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDsettlementByr.do?editType=insert');"><span><em>增 加</em></span></button>
<!--			<input type="button" class="button_ty" value="增 加" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDsettlementByr.do?editType=insert');">-->
			<%}%>
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
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">
	var contentDataTable;
	var contentColumnHeaders;
	var deployCom = document.getElementById("deployCom").value; 
	YAHOO.namespace("query.container");
	
    function init() {
        YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn,oData) {
        	var buyerUnitCode    = oRecord.buyerUnitCode;
            var validStatus      = oRecord.validStatus;
            var buyerUnitName    = oRecord.buyerUnitName;
            if(oColumn.key == "buyerUnitName"){
            	elCell.innerHTML = buyerUnitName;
            }
            //if(oColumn.key == "chkbox"){
            //	elCell.innerHTML = "<input type='checkbox' name='chkbox' value=''>";
            //}
           if (oColumn.key == "buyerUnitCode") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDsettlementByr.do?editType=view&prpDsettlementByr.buyerUnitCode="
						+ buyerUnitCode
						+ "')\">" + oData + "</a>"
			}
			if (oColumn.key == "edit") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDsettlementByr.do?prpDsettlementByr.buyerUnitCode="
						+ buyerUnitCode
						+ "&editType=update')\">修改</a>";
			}
			if(oColumn.key == "status"){
				if(validStatus == "1"){
					elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('"+buyerUnitCode+"','"+validStatus+"')\">注销</a>";
				}else{
					elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('"+buyerUnitCode+"','"+validStatus+"')\">启用</a>";
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
			
        };
		if(deployCom == '<%=SyncConstants.ComCode_Head %>'){
        contentColumnHeaders = [
          //{key :"chkbox",text :"选择",width :"15em",sortable :false,resizeable :true,type :"link"},
          {key :"buyerUnitCode",text :"预算单位代码",width :"10em",sortable :true,resizeable :true,type :"link"},
          {key :"buyerUnitName",text :"预算单位名称",width :"20em",sortable :true,resizeable :true},
          {key :"buyerUnitAddress",text :"预算单位地址",width :"25em",sortable :true,resizeable :true},
          {key :"validStatus",text :"有效状态",width :"7em",sortable :true,resizeable :true,type :"link"},
          {key :"flag",text :"标志字段",width :"8em",sortable :true,resizeable :true},
          {key :"edit",text :"修改",width :"10em",type :"link",resizeable :true},
          {key :"status",text :"注销/启用",width :"10em",type :"link",resizeable :true}];
          
          executeQuery(1,10);
          }
          else{
          contentColumnHeaders = [
          //{key :"chkbox",text :"选择",width :"15em",sortable :false,resizeable :true,type :"link"},
          {key :"buyerUnitCode",text :"预算单位代码",width :"10em",sortable :true,resizeable :true,type :"link"},
          {key :"buyerUnitName",text :"预算单位名称",width :"20em",sortable :true,resizeable :true},
          {key :"buyerUnitAddress",text :"预算单位地址",width :"25em",sortable :true,resizeable :true},
          {key :"validStatus",text :"有效状态",width :"7em",sortable :true,resizeable :true,type :"link"},
          {key :"flag",text :"标志字段",width :"8em",sortable :true,resizeable :true}];
          
          executeQuery(1,10);
          }
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
        var myDataSource = new YAHOO.util.DataSource("${ctx}/dictionary/queryPrpDsettlementByr.do");
        
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
        myDataSource.responseSchema = {
            resultsList:"data",
            fields:["buyerUnitCode","buyerUnitName","buyerUnitAddress","validStatus","flag"],
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
	function changeValidStatus(buyerUnitCode,validStatus){
	    var result;
	    if(validStatus == "1"){
	    result = "确定要注销吗？"
	    }
	    else{
	    result = "确定要启用吗？"
	    }
		if(confirm(result)){
		url="'${ctx}/dictionary/changePrpDsettlementByrValidStatus.do?prpDsettlementByr.buyerUnitCode="+buyerUnitCode;
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
