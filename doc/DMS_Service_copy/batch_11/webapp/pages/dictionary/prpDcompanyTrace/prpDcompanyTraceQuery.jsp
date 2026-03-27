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
<title>申请代码</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">请输入查询条件</h2>
</div>
<s:form name="fm" action="queryPrpDcompanyTrace" namespace="/dictionary"
	method="post">
	<s:hidden name="flag" id="flag"></s:hidden>
	<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
	<table class="fix_table">
		<tr>
			<td class="bgc_tt short">机构代码</td>
			<td class="long"><input name="prpDcompanyTrace.comCode"
				id="comCode" class='input_w w_15 dt-date dc-chk dt-nzhs'
				maxlength=""></td>
			<td class="bgc_tt short">机构名称</td>
			<td class="long"><s:textfield name="prpDcompanyTrace.comCName"
				id="comCName" cssClass="input_w w_15" maxlength="30" /></td>
		</tr>
		<tr>
			<td colspan="4" valign="baseline" nowrap class="bgc_tt short">
			<button type="button"  value=""
				onclick="executeQuery(1,10);"><span><em>查 询</em></span></button>
<!--			<input-->
<!--				type="button" class="button_ty" value="查 询"-->
<!--				onclick="executeQuery(1,10);">-->
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
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript"><!--
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
            var auditSuggest      = oRecord["auditSuggest"];
            var approvalMen  	  = oRecord["approvalMen"];
            if(oColumn.key == "chkbox"){
            	elCell.innerHTML = "<input type='checkbox' name='chkbox' value=''>";
            }           
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
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDcompanyTrace.do?editType=view&serialNo="						
						+ serialNo
						+ "')\">" + oData + "</a>"
			}
			if (oColumn.key == "comCode") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDcompanyTrace.do?editType=view&serialNo="
						+ serialNo
						+ "')\">" + oData + "</a>"
			}			
			if (oColumn.key == "comCName") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDcompanyTrace.do?editType=view&serialNo="					
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
				if(oRecord.currentStatus == 0 ||oRecord.currentStatus == 9){
				
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDcompanyTrace.do?serialNo="
						+ serialNo					
						+ "&editType=update')\">修改/提交</a>";
				}
				else
				{
					elCell.innerHTML="修改/提交";				
				}
				
			}	
         };
          if(deployCom == '<%=SyncConstants.ComCode_Head %>'){
        contentColumnHeaders = [
          {key :"chkbox",text :"选择",width :"12em",sortable :false,resizeable :true,type :"link"},
          {key :"serialNo",text :"序号",width :"12em",sortable :false,resizeable :true,type :"link"},
          {key :"comCode",text :"机构代码",width :"15em",sortable :true,resizeable :true,type :"link"},
          {key :"comCName",text :"机构名称",width :"20em",sortable :true,resizeable :true,type :"link"},
          {key :"addressCName",text :"机构地址",width :"25em",sortable :true,resizeable:true },
          {key :"applicantMen",text :"申请人",width :"30em",sortable :true,resizeable :true},
          {key :"currentStatus",text :"审核状态",width :"30em",sortable :true,resizeable :true,type :"link"},
          {key :"applicantDate",text :"申请时间",width :"25em",sortable :true,resizeable :true,type :"link"},
          {key :"applicantType",text :"申请类型",width :"25em",sortable :true,resizeable :true,type :"link"},
          {key :"updateDate",text :"最后修改时间",width :"35em",sortable :true,resizeable :true,type :"link"},
          {key :"applicantDesc",text :"申请描述",width :"40em",sortable :true,resizeable :true},
          {key :"auditSuggest",text :"审核意见",width :"40em",sortable :true,resizeable :true},
          {key :"approvalMen",text :"审批人",width:"30em",sortable :true,resizeable :true},           
          {key :"edit",text :"修改/提交",width :"30em",type :"link",resizeable :true}];
          
          executeQuery(1,10);
          }
          else{
            contentColumnHeaders = [
          {key :"chkbox",text :"选择",width :"12em",sortable :false,resizeable :true,type :"link"},
          {key :"serialNo",text :"序号",width :"12em",sortable :false,resizeable :true,type :"link"},
          {key :"comCode",text :"机构代码",width :"15em",sortable :true,resizeable :true,type :"link"},
          {key :"comCName",text :"机构名称",width :"20em",sortable :true,resizeable :true,type :"link"},
          {key :"addressCName",text :"机构地址",width :"25em",sortable :true,resizeable:true },
          {key :"applicantMen",text :"申请人",width :"30em",sortable :true,resizeable :true},
          {key :"currentStatus",text :"审核状态",width :"30em",sortable :true,resizeable :true,type :"link"},
          {key :"applicantDate",text :"申请时间",width :"25em",sortable :true,resizeable :true,type :"link"},
          {key :"applicantType",text :"申请类型",width :"25em",sortable :true,resizeable :true,type :"link"},
          {key :"updateDate",text :"最后修改时间",width :"35em",sortable :true,resizeable :true,type :"link"},
          {key :"applicantDesc",text :"申请描述",width :"40em",sortable :true,resizeable :true},
          {key :"auditSuggest",text :"审核意见",width :"40em",sortable :true,resizeable :true},
          {key :"approvalMen",text :"审批人",width:"30em",sortable :true,resizeable :true}];
          
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
        var myDataSource = new YAHOO.util.DataSource("${ctx}/dictionary/queryPrpDcompanyTrace.do");
     
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
        myDataSource.responseSchema = {
            resultsList:"data",
            fields:["serialNo","comCode","comCName","addressCName","applicantMen","currentStatus","applicantDate","applicantType","updateDate","applicantDesc","auditSuggest","approvalMen"],
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
--></script>
