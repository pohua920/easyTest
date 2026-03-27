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
<title>项目代码</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>

</head>

<body id="all_title">

<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">请输入查询条件</h2>
</div>
<s:form name="fm" action="queryPrpDproject" namespace="/dictionary" method="post">
<s:hidden name="flag" id="flag"></s:hidden>
<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
	<table class="fix_table">	
		<tr>
			<td class="bgc_tt short">项目代码</td>			
			<td class="long"><input name="prpDproject.projectCode"
				id="prpDproject.projectCode" class='input_w w_15'></td>
			<td class="bgc_tt short">项目代码名称</td>
			<td class="long"><input name="prpDproject.projectCName"
				id="prpDproject.projectCName" class='input_w w_15'></td>
			<td colspan="2" align="center">
			<button type="button"
				class="button_ty" value="查 询" onclick="executeQuery(1,10);"><span><em>确定</em></span></button>
<!--			<input type="button"-->
<!--				class="button_ty" value="查 询" onclick="executeQuery(1,10);">-->
			</td>
			<td colspan="2" align="center">
			<% if(SyncConstants.ComCode_Head.equals(deployCom)){%>
			<buttontype="button" value="" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDproject.do?editType=insert');"><span><em>增 加</em></span></button>
<!--			<input type="button"class="button_ty" value="增 加" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDproject.do?editType=insert');">-->
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
        //var userCode_tip = new YAHOO.widget.Tooltip("userCode_tip",{text:"请双击选择员工代码",context:"saaUser.userCode",zIndex:300});
        //var comCode_tip = new YAHOO.widget.Tooltip("comCode_tip",{text:"请双击选择机构代码",context:"saaUser.comCode",zIndex:300});	
        YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn,oData) {
            var projectCode = oRecord.projectCode;
            var valid = oRecord.validInd;
            if (oColumn.key == "projectCode") {
                elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDproject.do?projectCode="
                    + projectCode + "&editType=view')\">"+oData+"</a>"
            }
            if (oColumn.key == "edit") {
                elCell.innerHTML = "<a href=\"#\" onclick=\"editRecord('${ctx}/dictionary/prepareUpdatePrpDproject.do?projectCode="
                        + projectCode + "&editType=update')\">修改</a>";
            }
 			if(oColumn.key=="status"){
		    	
		    	//if(valid == "1"){
		    	 //	elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('${ctx}/dictionary/changePrpDprojectValidStatus.do?prpDproject.projectCode="+projectCode+"')\">注销</a>";
		    	//}else{
		    	//	elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('${ctx}/dictionary/changePrpDprojectValidStatus.do?prpDproject.projectCode="+projectCode+"')\">启用</a>";
		    	//}
		    	if(valid == "1"){
		    	 	elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('"+projectCode+"','"+valid+"')\">注销</a>";
		    	}else{
		    		elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('"+projectCode+"','"+valid+"')\">启用</a>";
		    	}    		
		    }
            if(oColumn.key == "chkbox"){
             	elCell.innerHTML = "<input type='checkbox' name='chkbox' value='"+projectCode+"'>";
             }
             
            if (oColumn.key == "validInd") {
                switch (valid) {
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
          {key :"chkbox",text :"选择",width :"15em",sortable :false,resizeable :true,type :"link"},
          {key :"projectCode",text :"项目代码",width :"20em",sortable :true,type :"link"}, 
          {key :"projectCName",text :"项目代码名称",width :"40em",sortable :true}, 
          {key :"creatorCode",text :"创建人",width :"50em",sortable :true},
          {key :"comCode",text :"归属机构",width :"50em",sortable :true},
          {key :"validInd",text :"有效状态",width :"10em",sortable :true,type :"link"}, 
          {key :"edit",text :"修改",width :"10em",type :"link",resizeable :true},
          {key :"status",text :"注销/启用",width :"20em",type :"link",resizeable :true}];
          
           executeQuery(1,10);
          }
          else{
          contentColumnHeaders = [ 
          {key :"chkbox",text :"选择",width :"15em",sortable :false,resizeable :true,type :"link"},
          {key :"projectCode",text :"项目代码",width :"20em",sortable :true,type :"link"}, 
          {key :"projectCName",text :"项目代码名称",width :"40em",sortable :true}, 
          {key :"creatorCode",text :"创建人",width :"50em",sortable :true},
          {key :"comCode",text :"归属机构",width :"50em",sortable :true},
          {key :"validInd",text :"有效状态",width :"10em",sortable :true,type :"link"}];
          
           executeQuery(1,10);
          }
        }
    //Query Data
    function executeQuery(pageNo, pageSize) {
        if (isNaN(parseInt(pageNo))) {
            pageNo = 1;
        }
        if (isNaN(parseInt(pageSize))) {
            pageSize = 10;
        }
        var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);
        var myDataSource = new YAHOO.util.DataSource("${ctx}/dictionary/queryPrpDproject.do");
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
        myDataSource.responseSchema = {
            resultsList :"data",
            fields : [ "projectCode", "projectCName", "creatorCode", "comCode","validInd" ],
            totalRecords :"totalRecords"
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
    YAHOO.util.Event.addListener(window, 'load', init);
	function changeValidStatus(projectCode,valid){
	    var result;
	    if(valid == "1"){
	    result = "确定要注销吗？"
	    }
	    else{
	    result = "确定要启用吗？"
	    }
		if(confirm(result)){
		url="${ctx}/dictionary/changePrpDprojectValidStatus.do?prpDproject.projectCode="+projectCode;
		var handleSuccess = function(o){
			var args = new SINOSOFT.util.QueryString( contentDataTable.initialRequest );
			var pageSize = parseInt(args["pageSize"],10);
			var pageNo = parseInt(args["pageNo"],10);
			executeQuery(pageNo,pageSize);
		}
		var handleFailure = function(o){
			if(o.responseText !== undefined){
				var msg = i18n.errors.deletefail+"!\n"+ o.status +" " + o.statusText;
				alert("操作失败！");
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