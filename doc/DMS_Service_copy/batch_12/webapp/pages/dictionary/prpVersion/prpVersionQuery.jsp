<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>项目版本号</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>

</head>

<body id="all_title">

<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">项目版本号</h2>
</div>
<s:form name="fm" action="queryPrpVersion" namespace="/dictionary" method="post">
<s:hidden name="flag" id="flag"></s:hidden>
<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
<!--  	<table class="fix_table">	
		<tr>
			<td colspan="2" align="center"><input type="button"
				class="button_ty" value="查 询" onclick="executeQuery(1,10);">
		</tr>
	</table>
-->
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
	YAHOO.namespace("query.container");
	
function init() {
    	YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn,oData) { 
    		var projectName  		= oRecord["projectName"];
        	var projectVersion      = oRecord["id.projectVersion"];
        	var primaryVersion      = oRecord["primaryVersion"];
        	var productId         	= oRecord["id.productId"];
        	var times     		    = oRecord["times"];
        	var updateDate		    = oRecord["updateDate"];
            if(oColumn.key == "chkbox"){
            	elCell.innerHTML = "<input type='checkbox' name='chkbox' value=''>";
            }
           if(oColumn.key == "updateDate"){
					var data = new Date(updateDate["time"]);					
			        var showtime = "";
			        showtime+=data.getFullYear();
			        if(isNaN(showtime)){			       
			        	elCell.innerHTML="";
			        }else{			        
			        	showtime+="-";			        	
			        	showtime+=(data.getMonth()+1)+"-";			        	
		    		    showtime+=data.getDate();		    		
						elCell.innerHTML=showtime;
			        }
			}           
            if (oColumn.key == "projectName") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpVersion.do?productId="
				 + productId + "&editType=view')\">" + oData + "</a>"
				 }
			if (oColumn.key == "id.projectVersion") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpVersion.do?productId="
				 + productId + "&editType=view')\">" + oData + "</a>"
				 }			
         };
        contentColumnHeaders = [
          {key :"projectName",text :"项目名称",width :"20em",sortable :true,resizeable:true,type :"link" },
          {key :"id.projectVersion",text :"项目版本号",width :"10em",sortable :true,resizeable :true,type :"link" },               
          {key :"primaryVersion",text :"升级前版本号",width :"30em",sortable :true,resizeable :true},
          {key :"id.productId",text :"系统代码",width :"15em",sortable :true,resizeable :true},
          {key :"times",text :"变更次数",width :"20em",sortable :true,resizeable :true},
          {key :"updateDate",text :"最后修改时间",width :"30em",sortable :true,resizeable :true,type :"link"}];
          
          executeQuery(1,10);
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
        var myDataSource = new YAHOO.util.DataSource("${ctx}/dictionary/queryPrpVersion.do");
     
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
        myDataSource.responseSchema = {
            resultsList:"data",
            fields:[ "projectName", "id.projectVersion", "primaryVersion", "id.productId", "times", "updateDate" ],
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
</script>
	