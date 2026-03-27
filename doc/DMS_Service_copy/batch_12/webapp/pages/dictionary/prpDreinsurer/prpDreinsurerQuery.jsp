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
<title>分保接受人代码</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>

</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">请输入查询条件</h2>
</div>
<s:form name="fm" action="queryPrpDreinsurer" namespace="/dictionary" method="post">
<s:hidden name="flag" id="flag"></s:hidden>
<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
<table class="fix_table">	
	<tr>
		<td class="bgc_tt short">接受人代码</td>			
		<td class="long">
		<input name="prpDreinsurer.reinsCode" id="reinsCode" class='input_w w_15' maxlength="">
		</td>
		<td colspan="4" valign="baseline" nowrap class="bgc_tt short">
		<button  type="button"  value="" onclick="executeQuery(1,10);"><span><em>查 询</em></span></button>
<!--			<input type="button" class="button_ty" value="查 询" onclick="executeQuery(1,10);">-->
			<% if(SyncConstants.ComCode_Head.equals(deployCom)){%>	
			<button type="button" value="" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDreinsurer.do?editType=insert');"><span><em>增 加</em></span></button>
<!--			<input type="button" class="button_ty" value="增 加" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDreinsurer.do?editType=insert');">-->
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
	var url;
	var deployCom = document.getElementById("deployCom").value; 
	YAHOO.namespace("query.container");
	    function init() {
		YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn,oData) {
        	var reinsCode    = oRecord.reinsCode;
            var validStatus  = oRecord.validStatus;
            //if(oColumn.key == "chkbox"){
            //	elCell.innerHTML = "<input type='checkbox' name='chkbox' value=''>";
            //}
            if(oColumn.key == "reinsCode"){
            	elCell.innerHTML = reinsCode;
            }
             if(oColumn.key == "validStatus"){
            	elCell.innerHTML = validStatus;
            }
            if (oColumn.key == "reinsCode") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDreinsurer.do?editType=view&prpDreinsurer.reinsCode="
						+ reinsCode
						+ "')\">" + oData + "</a>"
			}
            if (oColumn.key == "edit") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDreinsurer.do?prpDreinsurer.reinsCode="
						+ reinsCode
						+ "&editType=update')\">修改</a>";
			}
			if(oColumn.key == "status"){
				if(validStatus == "1"){
					elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('"+reinsCode+"','"+validStatus+"')\">注销</a>";
				}else{
					elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('"+reinsCode+"','"+validStatus+"')\">启用</a>";
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
          {key :"reinsCode",text :"接受人代码",width :"10em",sortable :true,resizeable :true,type :"link"},
          {key :"longName",text :"接受人全称",width :"55em",sortable :true,resizeable :true},
          {key :"regionCode",text :"所在城市／地区",width :"15em",sortable :true,resizeable :true},
          {key :"countryName",text :"所属国家",width :"10em",sortable :true,resizeable :true},
          {key :"validStatus",text :"有效状态",width :"10em",sortable :true,resizeable :true,type :"link"},
          {key :"edit",text :"修改",width :"10em",type :"link",resizeable :true},
          {key :"status",text :"注销/启用",width :"10em",type :"link",resizeable :true}];
          
          executeQuery(1,10);
          }
          else{
          contentColumnHeaders = [
          //{key :"chkbox",text :"选择",width :"15em",sortable :false,resizeable :true,type :"link"},
          {key :"reinsCode",text :"接受人代码",width :"10em",sortable :true,resizeable :true,type :"link"},
          {key :"longName",text :"接受人全称",width :"55em",sortable :true,resizeable :true},
          {key :"regionCode",text :"所在城市／地区",width :"15em",sortable :true,resizeable :true},
          {key :"countryName",text :"所属国家",width :"10em",sortable :true,resizeable :true},
          {key :"validStatus",text :"有效状态",width :"10em",sortable :true,resizeable :true,type :"link"}];
          
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
        var myDataSource = new YAHOO.util.DataSource("${ctx}/dictionary/queryPrpDreinsurer.do");
        
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
        myDataSource.responseSchema = {
            resultsList:"data",
            fields:["reinsCode","longName","regionCode","countryName","validStatus"],
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
    
    function changeValidStatus(reinsCode,validStatus){
			var result;
			if(validStatus=="1"){
				result = "确定要注销吗？";
			}else{
				result = "确定要启用吗？";
			}
			if(confirm(result)){
			url ="${ctx}/dictionary/changePrpDreinsurerValidStatus.do?prpDreinsurer.reinsCode="+reinsCode;
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
