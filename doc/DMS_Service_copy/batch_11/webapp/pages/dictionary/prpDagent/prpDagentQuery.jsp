<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.sync.SyncConstants"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<%
	String deployCom = (String)session.getAttribute("deployCom");
%>
<html>
<head>
<title>渠道代码</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>

</head>

<body id="all_title">
<s:form name="fm" action="queryprpDagent" namespace="/dictionary" method="post">
<s:hidden name="flag" id="flag"></s:hidden>
<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">请输入查询条件</h2>
</div>
	<table class="fix_table">	
		<tr>
			<td class="bgc_tt short">渠道代码</td>			
			<td class="long"><input name="prpDagent.agentCode"
				id="prpDagent.agentCode" class='input_w w_15'></td>			
			<td class="bgc_tt short">渠道名称</td>
			<td class="long"><input name="prpDagent.agentName"
				id="prpDagent.agentName" class='input_w w_15'></td>
<%--
			<td colspan="2" align="center"><input type="button"
				class="button_ty" value="增 加" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDagent.do?editType=insert');">
			</td>
			<td colspan="2" align="center"><input type="button"
				class="button_ty" value="删除" onclick="deleteMethod();">
			</td>
--%>
		</tr>
		<tr>
			<td colspan="4" align="center" class="title">
			<button  type="button"
				value="" onclick="executeQuery(1,10);"><span><em>查 询</em></span></button>
<!--			<input type="button"-->
<!--				class="button_ty" value="查 询" onclick="executeQuery(1,10);">-->
			</td>
		</tr>
	</table>
</div>
<div id="content_navigation" class="query" align="right"></div>
<div id="content" class="sort"></div>
<div id="content_navigation" class="query" align="right"></div>
</div>
</s:form>
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
	var deployCom = document.getElementById("deployCom").value;
    function init() {
        //var userCode_tip = new YAHOO.widget.Tooltip("userCode_tip",{text:"请双击选择员工代码",context:"saaUser.userCode",zIndex:300});
        //var comCode_tip = new YAHOO.widget.Tooltip("comCode_tip",{text:"请双击选择机构代码",context:"saaUser.comCode",zIndex:300});	
        YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn,oData) {
        	if(deployCom == '<%=SyncConstants.ComCode_Head %>'){
            var agentCode = oRecord["id.agentCode"];
             var valid    = oRecord["validStatus"];
            if (oColumn.key == "id.agentCode") {
                elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDagentAll.do?agentCode="
                    + agentCode + "&editType=view')\">"+oData+"</a>"
            	}
            }
            else{
            var agentCode = oRecord["agentCode"];
             var valid    = oRecord["validStatus"];
             if (oColumn.key == "agentCode") {
                elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDagent.do?agentCode="
                    + agentCode + "&editType=view')\">"+oData+"</a>"
            	}
            }
            
            //if (oColumn.key == "edit") {
            //    elCell.innerHTML = "<a href=\"#\" onclick=\"editRecord('${ctx}/dictionary/prepareUpdatePrpDagent.do?agentCode="
            //            + agentCode + "&editType=update')\">修改</a>";
            //}
 			//if(oColumn.key=="status"){
		    //	if(valid == "1"){
		    //	 	elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('"+agentCode+"','"+valid+"')\">注销</a>";
		    //	}else{
		    //		elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('"+agentCode+"','"+valid+"')\">启用</a>";
		    //	}
		    //  }
            //if(oColumn.key == "chkbox"){
            //	elCell.innerHTML = "<input type='checkbox' name='chkbox' value="+agentCode+">";
            //}
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
		  //{key :"chkbox",text :"选择",width :"20em",sortable :false,type:"link",resizeable :true},
          {key :"id.agentCode",text :"渠道代码",width :"40em",sortable :true,type :"link",resizeable :true}, 
          {key :"id.locateComCode",text:"本地机构代码",width :"40em",sortable :true},
          {key :"agentName",text :"渠道名称",width :"60em",sortable :true,resizeable :true}, 
          {key :"addressName",text :"渠道地址",width :"60em",sortable :true,resizeable :true},
          {key :"validStatus",text :"状态",width :"20em",sortable :true,type:"link",resizeable :true}];
          //{key :"edit",text :"修改",width :"20em",type :"link",resizeable :true},
          //{key :"status",text :"注销/启用",width :"20em",type :"link",resizeable :true}];
          
          executeQuery(1,10);
        }
        else
        {
        contentColumnHeaders = [
		  //{key :"chkbox",text :"选择",width :"20em",sortable :false,type:"link",resizeable :true},
          {key :"agentCode",text :"渠道代码",width :"40em",sortable :true,type :"link",resizeable :true}, 
          {key :"agentName",text :"渠道名称",width :"60em",sortable :true,resizeable :true}, 
          {key :"addressName",text :"渠道地址",width :"60em",sortable :true,resizeable :true},
          {key :"validStatus",text :"状态",width :"20em",sortable :true,type:"link",resizeable :true}];
          //{key :"edit",text :"修改",width :"20em",type :"link",resizeable :true},
          //{key :"status",text :"注销/启用",width :"20em",type :"link",resizeable :true}];
          
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
        var myDataSource = new YAHOO.util.DataSource("${ctx}/dictionary/queryPrpDagent.do");
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
        if(deployCom == '<%=SyncConstants.ComCode_Head %>'){
        myDataSource.responseSchema = {
            resultsList :"data",
            fields : [ "id.agentCode", "id.locateComCode","agentName", "addressName", "postCode", "agentType", "validStatus" ],
            totalRecords :"totalRecords"
        };
        }
        else{
        myDataSource.responseSchema = {
            resultsList :"data",
            fields : [ "agentCode","agentName", "addressName", "postCode", "agentType", "validStatus" ],
            totalRecords :"totalRecords"
        };
        }
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
				deleteRecord('${ctx}/dictionary/deletePrpDagent.do?chkbox='+checkedValue);
			}else{
				alert("没有选中列！");
			}
         }
    }
    	function changeValidStatus(agentCode,valid){
    		var result;
	    	if(valid == "1"){
	   	 		result = "确定要注销吗？"
	   		 }
	   		 else{
	   			 result = "确定要启用吗？"
	   		 }
			if(confirm(result)){
			url="${ctx}/dictionary/changePrpDagentValidStatus.do?prpDagent.agentCode="+agentCode
			var handleSuccess = function(o){
				var args = new SINOSOFT.util.QueryString( contentDataTable.initialRequest );
				var pageSize = parseInt(args["pageSize"],10);
				var pageNo = parseInt(args["pageNo"],10);
				executeQuery(pageNo,pageSize);
			};
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