<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.sync.SyncConstants"%>
<%
	String deployCom = (String)session.getAttribute("deployCom");
%>
<html>
	<head>
		<title>审核代码</title>
		<%@ include file="/common/i18njs.jsp"%>
		<%@ include file="/common/meta_css.jsp"%>		
	</head>
	<body id="all_title">
		<div id="wrapper">
			<div id="container">
				<div id="crash_menu">
					<h2 align="center">
						待审核的申请
					</h2>
				</div>
				<s:form name="fm" action="queryPrpDcompanyTraceNotAudit"
					namespace="/dictionary" method="post">
					<s:hidden name="flag" id="flag"></s:hidden>
					<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
				</s:form>
			</div>
			<div id="content_navigation" class="query" align="right"></div>
			<div id="content" class="sort"></div>
			<div id="content_navigation" class="query" align="right"></div>
		</div>
	</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">
	var contentDataTable;
	var contentColumnHeaders;
    var deployCom = document.getElementById("deployCom").value;
	YAHOO.namespace("query.container");
	
    function init() {
    	YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn,oData) { 
    		var serialNo  		  = oRecord["serialNo"];
        	var comCode           = oRecord["comCode"];
        	var comCName          = oRecord["comCName"];
        	var addressCName      = oRecord["addressCName"];
        	var applicantMen      = oRecord["applicantMen"];
        	var currentStatus     = oRecord["currentStatus"];
            var applicantDate     = oRecord["applicantDate"];     
            var applicantType     = oRecord["applicantType"];
            var updateDate        = oRecord["updateDate"];
            var applicantDesc     = oRecord["applicantDesc"];                    
            if(oColumn.key == "applicantDate"){
					var data = new Date(applicantDate["time"]);					
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
            if (oColumn.key == "serialNo") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareAudit.do?editType=view&serialNo="						
						+ serialNo
						+ "')\">" + oData + "</a>"
			}
			if (oColumn.key == "comCode") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareAudit.do?editType=view&serialNo="
						+ serialNo
						+ "')\">" + oData + "</a>"
			}			
			if (oColumn.key == "comCName") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareAudit.do?editType=view&serialNo="					
						+ serialNo						
						+ "')\">" + oData + "</a>"
			}
			if(oColumn.key == "applicantType"){
					if(oRecord.applicantType == 1){
						elCell.innerHTML="新增";					
					}
					else if(oRecord.applicantType == 2){
						elCell.innerHTML="修改";	
					}
					else if(oRecord.applicantType == 3){
						elCell.innerHTML="注销/启用";
					}
				}
			if(oColumn.key == "currentStatus"){
					if(oRecord.currentStatus == 0){
						elCell.innerHTML="初始化";	
				} else if (oRecord.currentStatus == 1){
						elCell.innerHTML="待审核";	
				} else if(oRecord.currentStatus == 8){
						elCell.innerHTML="审核通过";	
				} else if(oRecord.currentStatus == 9){
						elCell.innerHTML="审核未通过";	
				}				
			}				
			if (oColumn.key == "edit") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareAudit.do?serialNo="
						+ serialNo					
						+ "&editType=audit')\">审核</a>";
			}	
         };
        if(deployCom == '<%=SyncConstants.ComCode_Head %>'){ 
        contentColumnHeaders = [
          {key :"serialNo",text :"序号",width :"10em",sortable :true,resizeable :true,type :"link"},
          {key :"comCode",text :"机构代码",width :"15em",sortable :true,resizeable :true,type :"link"},
          {key :"comCName",text :"机构名称",width :"20em",sortable :true,resizeable :true,type :"link"},
          {key :"addressCName",text :"机构地址",width :"20em",sortable :true,resizeable:true },
          {key :"applicantMen",text :"申请人",width :"30em",sortable :true,resizeable :true},
          {key :"currentStatus",text :"审核状态",width :"30em",sortable :true,resizeable :true,type :"link"},
          {key :"applicantDate",text :"申请时间",width :"25em",sortable :true,resizeable :true,type :"link"},
          {key :"applicantType",text :"申请类型",width :"25em",sortable :true,resizeable :true,type :"link"},
          {key :"updateDate",text :"最后修改时间",width :"25em",sortable :true,resizeable :true,type :"link"},
          {key :"applicantDesc",text :"申请描述",width :"50em",sortable :true,resizeable :true},              
          {key :"edit",text :"审核",width :"30em",type :"link",resizeable :true}];
          
          executeQuery(1,10);
         }
         else{
         contentColumnHeaders = [
          {key :"serialNo",text :"序号",width :"10em",sortable :true,resizeable :true,type :"link"},
          {key :"comCode",text :"机构代码",width :"15em",sortable :true,resizeable :true,type :"link"},
          {key :"comCName",text :"机构名称",width :"20em",sortable :true,resizeable :true,type :"link"},
          {key :"addressCName",text :"机构地址",width :"20em",sortable :true,resizeable:true },
          {key :"applicantMen",text :"申请人",width :"30em",sortable :true,resizeable :true},
          {key :"currentStatus",text :"审核状态",width :"30em",sortable :true,resizeable :true,type :"link"},
          {key :"applicantDate",text :"申请时间",width :"25em",sortable :true,resizeable :true,type :"link"},
          {key :"applicantType",text :"申请类型",width :"25em",sortable :true,resizeable :true,type :"link"},
          {key :"updateDate",text :"最后修改时间",width :"25em",sortable :true,resizeable :true,type :"link"},
          {key :"applicantDesc",text :"申请描述",width :"50em",sortable :true,resizeable :true}];
          
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
        var myDataSource = new YAHOO.util.DataSource("${ctx}/dictionary/queryPrpDcompanyTraceNotAudit.do");
     
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
        myDataSource.responseSchema = {
            resultsList:"data",
            fields:["serialNo","comCode","comCName","addressCName","applicantMen","currentStatus","applicantDate","applicantType","updateDate","applicantDesc"],
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
