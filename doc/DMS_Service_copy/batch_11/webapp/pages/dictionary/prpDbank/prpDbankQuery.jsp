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
<title>金融机构</title>
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
<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
	<table class="fix_table">	
		<tr>
			<td class="bgc_tt short">机构代码</td>			
			<td class="long"><input name="prpDbank.bankCode"
				id="prpDbank.bankCode" class='input_w w_15'></td>			
			<td class="bgc_tt short">机构名称</td>
			<td class="long"><input name="prpDbank.bankName"
				id="prpDbank.bankName" class='input_w w_15'></td>
			<td colspan="2" align="center">
			<button type="button"
				 value="" onclick="executeQuery(1,10);"><span><em>查 询</em></span></button>
<!--			<input type="button"-->
<!--				class="button_ty" value="查 询" onclick="executeQuery(1,10);">-->
			</td>
			<td colspan="2" align="center">
			<% if(SyncConstants.ComCode_Head.equals(deployCom)){%>	
			<button type="button"
				 value="" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDbank.do?editType=insert');"><span><em>增 加</em></span></button>
<!--			<input type="button"-->
<!--				class="button_ty" value="增 加" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDbank.do?editType=insert');">-->
			<%}%>
			</td>
<%--
			<td colspan="2" align="center"><input type="button"
				class="button_ty" value="删除" onclick="deleteMethod();">
			</td>
--%>
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
            var bankCode = oRecord.bankCode;
              var valid = oRecord.validStatus;
            if (oColumn.key == "bankCode") {
                elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDbank.do?bankCode="
                    + bankCode + "&editType=view')\">"+oData+"</a>"
            }
            if (oColumn.key == "edit") {
                elCell.innerHTML = "<a href=\"#\" onclick=\"editRecord('${ctx}/dictionary/prepareUpdatePrpDbank.do?bankCode="
                        + bankCode + "&editType=update')\">修改</a>";
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
            if (oColumn.key == "bankType") {
                switch (oRecord.bankType) {
                    case '1':
                        elCell.innerHTML = "银行";
                        break;
                    case '0':
                        elCell.innerHTML = "汽车金融公司";
                        break;
                }
            }
            
            if(oColumn.key=="status"){
		    	
		    	if(valid == "1"){
		    	 	elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('"+bankCode+"','"+valid+"')\">注销</a>";
		    	}else{
		    		elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('"+bankCode+"','"+valid+"')\">启用</a>";
		    	}
	    		
		      }
        };
		if(deployCom == '<%=SyncConstants.ComCode_Head %>'){
        contentColumnHeaders = [ 
		  //{key :"chkbox",text :"选择",width :"15em",sortable :false,resizeable :true,type :"link"},
          {key :"bankCode",text :"机构代码",width :"30em",sortable :true,type :"link"}, 
          {key :"bankName",text :"机构名称",width :"80em",sortable :true}, 
          {key :"bankType",text :"机构类型",width :"50em",sortable :true,type :"link"}, 
          {key :"validStatus",text :"状态",width :"20em",sortable :true,type :"link"}, 
          {key :"edit",text :"修改",width :"20em",type :"link",resizeable :true},
          {key :"status",text :"注销/启用",width :"20em",type :"link",resizeable :true}];
          
          executeQuery(1,10);
          }
          else{
          contentColumnHeaders = [ 
		  //{key :"chkbox",text :"选择",width :"15em",sortable :false,resizeable :true,type :"link"},
          {key :"bankCode",text :"机构代码",width :"30em",sortable :true,type :"link"}, 
          {key :"bankName",text :"机构名称",width :"80em",sortable :true}, 
          {key :"bankType",text :"机构类型",width :"50em",sortable :true,type :"link"}, 
          {key :"validStatus",text :"状态",width :"20em",sortable :true,type :"link"}];
          
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
        var myDataSource = new YAHOO.util.DataSource("${ctx}/dictionary/queryPrpDbank.do");
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
        myDataSource.responseSchema = {
            resultsList :"data",
            fields : [ "bankCode", "bankName", "bankType", "comCode","validStatus" ],
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
    function deleteMethod(){
    	var chkbox = document.getElementsByName('chkbox');
    	var flag = false;
    	var checkedValue="";
    	if(chkbox.length==0){
			alert("没有选中列！");
			return false;
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
				deleteRecord('${ctx}/dictionary/deletePrpDbank.do?chkbox='+checkedValue);
			}else{
				alert("没有选中列！");
			}
         }
    }

	function changeValidStatus(bankCode,valid){
		var result;
	    	if(valid == "1"){
	   	 		result = "确定要注销吗？"
	   		 }
	   		 else{
	   			 result = "确定要启用吗？"
	   		 }
		if(confirm(result)){
		url="${ctx}/dictionary/changePrpDbankValidStatus.do?prpDbank.bankCode="+bankCode
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