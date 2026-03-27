<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.sync.SyncConstants"%>
<%
	String deployCom = (String)session.getAttribute("deployCom");
%>
<html>
<head>
<title>类型代码</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
</head>
<body id="all_title">
<s:form name="fm" action="queryPrpDship" namespace="/dictionary" method="post">
<div id="wrapper">
<div id="container">
<div id="">
</div>
<s:hidden name="flag" id="flag"></s:hidden>
<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
</div>
<div id="crash_menu">
		<h2>代码类型</h2>
</div>
<table class="fix_table" width="100%">
						<tr>
							<td class="bgc_tt short">
								代码类型：
							</td>
							<td class="long">
								<input name="prpDtype.codeType" id="prpDtype.codeType"
									class='input_w w_15'>
							</td>
						</tr>
						<tr>
							<td class="bgc_tt short">
								类型名称：
							</td>
							<td class="long">
								<input name="prpDtype.codeTypeDesc" id="prpDtype.codeTypeDesc"
									class='input_w w_15'>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
							<button type="button" value=""
									onclick="executeQuery(1,10);"><span><em>查 询</em></span></button>
<!--								<input type="button" class="button_ty" value="查 询"-->
<!--									onclick="executeQuery(1,10);">-->
							</td>
						</tr>
					</table>
<div id="content" class="sort"></div>
<div id="content_navigation" class="query" align="center"></div>
<table class="fix_table">
		<tr>
			<td align="center">
			<% if(SyncConstants.ComCode_Head.equals(deployCom)){%>	
			<button type='button' 
				name=buttonInsert value="" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDtype.do?editType=insert');"><span><em>增加</em></span></button>
<!--			<input type='button' class="button_ty"-->
<!--				name=buttonInsert value="增加" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDtype.do?editType=insert');">-->
			<%}%>
			</td>

			<td align="center">
			<% if(SyncConstants.ComCode_Head.equals(deployCom)){%>	
			<button type='button'
				name=buttonModify value="" onclick="return updateMethod();"><span><em>修改</em></span></button>
<!--			<input type='button' class="button_ty"-->
<!--				name=buttonModify value="修改" onclick="return updateMethod();">-->
			<%}%>	
			</td>
<!--
			<td align="center" ><input type='button' class="button_ty"
				name=buttonInsert value="删除" onclick="return deleteMethod();"></td>
-->
			<td align="center">
			<button type='button' 
				name=buttonModify value="" onclick="return viewMethod();"><span><em>查看</em></span></button>
<!--			<input type='button' class="button_ty"-->
<!--				name=buttonModify value="查看" onclick="return viewMethod();">-->
				</td>
		</tr>
</table>
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
	var deployCom = document.getElementById("deployCom").value;
	YAHOO.namespace("query.container");
    function init() {
    	YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn,oData) {
            var codeType = oRecord.codeType;
            if (oColumn.key == "codeType") {
            	elCell.innerHTML = "<a href=\"#\"  onclick=\"toPrpDcodeType('"
                    + codeType + "')\">"+oData+"</a>";
            }
            
            if (oColumn.key == "codeTypeDesc") {
                elCell.innerHTML = "<a href=\"#\"  onclick=\"toPrpDcodeType('"
                   + codeType + "')\">"+oData+"</a>";
            }
            if(oColumn.key == "chkbox"){
            //modify by duanfa 2011-06-22 改为单选框
            //elCell.innerHTML = "<input type=\"checkbox\" name=\"chkbox\" value="+codeType+" />";
           	elCell.innerHTML = "<input type=\"radio\" name=\"chkbox\" value="+codeType+" />";
           	  //modify by duanfa 2011-06-22 end
            }
        };
        contentColumnHeaders = [
          {key :"chkbox",text :"选择",width :"20em",sortable :false,resizeable :true,type :"link"},
          {key :"codeType",text :"类型",width :"40em",sortable :true,type :"link"}, 
          {key :"codeTypeDesc",text :"名称",width :"40em",sortable :true,type :"link"}];
        
	       executeQuery(1,10);
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
        var myDataSource = new YAHOO.util.DataSource("${ctx}/dictionary/queryPrpDtype.do");
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
        myDataSource.responseSchema = {
            resultsList :"data",
            fields : ["codeType", "codeTypeDesc"],
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
        }else if(true){//为了修改方便，将if中的条件改为true，也可以讲此条件删除。
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
				deleteRecord('${ctx}/dictionary/deletePrpDtype.do?chkbox='+checkedValue);
				window.parent.prpDcodeRight.location.href="${ctx}/pages/dictionary/prpDtype/mainInitPage.jsp";
			}else{
				alert("没有选中列！");
			}
         }
    }
	function viewMethod(){
    	var chkbox = document.getElementsByName('chkbox');
    	var flag = false;
    	var num=0;
    	var checkedValue="";
    	if(chkbox.length==0){
			alert("没有选中列！");
        }else{
        	for(var j=0;j<chkbox.length;j++){
				if(chkbox[j].checked){
					flag = true;
					num+=1;
					if(num>1){
						alert("最多选择一条数据！");
						return false;
					}
					if(checkedValue==""){
						checkedValue=chkbox[j].value;
					}else{
						checkedValue+=","+chkbox[j].value;
					}
				}
			}
			if(flag){
				showRecord('${ctx}/dictionary/prepareUpdatePrpDtype.do?editType=view&chkbox='+checkedValue);
			}else{
				alert("没有选中列！");
			}
         }
	}
	function updateMethod(){
		var chkbox = document.getElementsByName('chkbox');
    	var flag = false;
    	var num=0;
    	var checkedValue="";
    	if(chkbox.length==0){
			alert("没有选中列！");
			return false;
        }else{
        	for(var j=0;j<chkbox.length;j++){
				if(chkbox[j].checked){
					flag = true;
					num+=1;
					if(num>1){
						alert("最多选择一条数据！");
						return false;
					}
					if(checkedValue==""){
						checkedValue=chkbox[j].value;
					}else{
						checkedValue+=","+chkbox[j].value;
					}
				}
			}
			if(flag){
				showRecord('${ctx}/dictionary/prepareUpdatePrpDtype.do?editType=update&chkbox='+checkedValue);
			}else{
				alert("没有选中列！");
			}
         }
	}
	
	function toPrpDcodeType(codeType){
		fm.action = '${ctx }/dictionary/prepareQueryPrpDcode.do?codeType='+codeType;
		fm.target ="prpDcodeRight";
   		fm.submit();
	}
</script>


