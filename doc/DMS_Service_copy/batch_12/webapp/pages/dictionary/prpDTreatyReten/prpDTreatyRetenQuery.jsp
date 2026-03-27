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
<title>自留额计划</title>
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
		<td class="bgc_tt short">业务年度</td>			
		<td class="long">
		<input name="prpDtreatyReten.id.uwYear" id="uwYear" class='input_w w_15 dt-date dc-chk dt-nzhs' maxlength="4">
		</td>
		<td class="bgc_tt short">币别</td>
		<td class="long">
			<s:textfield name="prpDtreatyReten.currency" id="currency" cssClass="input_w w_15" maxlength="30" />
		</td>
	</tr>  
    <tr>     
		<td class="bgc_tt short">险类代码</td>
		<td class="long">
			<ct:select name="prpDtreatyReten.id.classCode" headValue="所有" id="classCode" 
				cssClass="selectui-input-up input_y w_p90" sysCode="DMS" codeType="PrpDclass" onclick="">
			</ct:select>
		</td>
		<td class="bgc_tt short">险种代码</td>			
		<td class="long">
			<input name="prpDtreatyReten.id.riskCode" id="riskCode" class='input_w w_15'>
		</td>
	</tr>
	<tr>
		<td colspan="4" valign="baseline" nowrap class="bgc_tt short">
		<button type="button"  value="" onclick="executeQuery(1,10);"><span><em>查 询</em></span></button>
<!--			<input type="button" class="button_ty" value="查 询" onclick="executeQuery(1,10);">-->
			<% if(SyncConstants.ComCode_Head.equals(deployCom)){%>	
			<button type="button"  value="" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDTreatyReten.do?editType=insert');"><span><em>增 加</em></span></button>
<!--			<input type="button" class="button_ty" value="增 加" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDTreatyReten.do?editType=insert');">-->
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
        	var uwYear    = oRecord["id.uwYear"];
        	var classCode = oRecord["id.classCode"];
        	var riskCode  = oRecord["id.riskCode"];
            var serialNo  = oRecord["id.serialNo"];
            var currency  = oRecord.currency;
            var endDate   = oRecord.endDate;
			    
            var grade = oRecord.grade;
            if(oColumn.key == "chkbox"){
            	elCell.innerHTML = "<input type='checkbox' name='chkbox' value=''>";
            }
            if(oColumn.key == "currency"){
            	elCell.innerHTML = currency;
            }
            if (oColumn.key == "id.uwYear") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDTreatyReten.do?editType=view&prpDtreatyReten.id.uwYear="
						+ uwYear
						+ "&prpDtreatyReten.id.classCode="
						+ classCode
						+ "&prpDtreatyReten.id.riskCode="
						+ riskCode
						+ "&prpDtreatyReten.id.serialNo="
						+ serialNo
						+ "')\">" + oData + "</a>"
			}
			if (oColumn.key == "id.riskCode") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDTreatyReten.do?editType=view&prpDtreatyReten.id.uwYear="
						+ uwYear
						+ "&prpDtreatyReten.id.classCode="
						+ classCode
						+ "&prpDtreatyReten.id.riskCode="
						+ riskCode
						+ "&prpDtreatyReten.id.serialNo="
						+ serialNo
						+ "')\">" + oData + "</a>"
			}
			if (oColumn.key == "id.classCode") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDTreatyReten.do?editType=view&prpDtreatyReten.id.uwYear="
						+ uwYear
						+ "&prpDtreatyReten.id.classCode="
						+ classCode
						+ "&prpDtreatyReten.id.riskCode="
						+ riskCode
						+ "&prpDtreatyReten.id.serialNo="
						+ serialNo
						+ "')\">" + oData + "</a>"
			}
			if (oColumn.key == "id.serialNo") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDTreatyReten.do?editType=view&prpDtreatyReten.id.uwYear="
						+ uwYear
						+ "&prpDtreatyReten.id.classCode="
						+ classCode
						+ "&prpDtreatyReten.id.riskCode="
						+ riskCode
						+ "&prpDtreatyReten.id.serialNo="
						+ serialNo
						+ "')\">" + oData + "</a>"
			}
			if (oColumn.key == "edit") {
				elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDTreatyReten.do?prpDtreatyReten.id.uwYear="
						+ uwYear
						+ "&prpDtreatyReten.id.classCode="
						+ classCode
						+ "&prpDtreatyReten.id.riskCode="
						+ riskCode
						+ "&prpDtreatyReten.id.serialNo="
						+ serialNo
						+ "&editType=update')\">修改</a>";
			}
			if(oColumn.key == "endDate"){
					var data = new Date(endDate["time"]);
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
			if(oColumn.key == "grade"){
				elCell.innerHTML=grade;
			}
			if(oColumn.key == "status"){
				if(grade == "1"){
					elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('"+ uwYear+ "','"+classCode+ "','"+ riskCode+ "','"+ serialNo+ "','"+grade+"')\">注销</a>";
				}else{
					elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('"+ uwYear+ "','"+classCode+ "','"+ riskCode+ "','"+ serialNo+ "','"+grade+"')\">启用</a>";
				}
			}
			 if (oColumn.key == "grade") {
                 switch (oRecord.grade) {
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
          {key :"id.uwYear",text :"业务年度",width :"15em",sortable :true,resizeable :true,type :"link"},
          {key :"id.classCode",text :"险类代码",width :"15em",sortable :true,resizeable :true,type :"link"},
          {key :"id.riskCode",text :"险种代码",width :"15em",sortable :true,resizeable :true,type :"link"},
          {key :"id.serialNo",text :"序号",width :"15em",sortable :true,resizeable :true,type :"link"},
          {key :"currency",text :"币别",width :"15em",sortable :true,resizeable :true},
          {key :"grade",text :"评分级别",width :"15em",sortable :true,resizeable :true},
          {key :"retentionValue",text :"自留额",width :"15em",sortable :true,resizeable :true},
          {key :"retentionRate",text :"自留比例(%)",width :"15em",sortable :true,resizeable :true},
          {key :"endDate",text :"终止日期",width :"20em",sortable :true,type :"link",resizeable :true},
          {key :"edit",text :"修改",width :"10em",type :"link",resizeable :true},
          {key :"status",text :"注销/启用",width :"20em",type :"link",resizeable :true}
          ];
          
          executeQuery(1,10);
          }
          else{
           contentColumnHeaders = [
          {key :"chkbox",text :"选择",width :"15em",sortable :false,resizeable :true,type :"link"},
          {key :"id.uwYear",text :"业务年度",width :"15em",sortable :true,resizeable :true,type :"link"},
          {key :"id.classCode",text :"险类代码",width :"15em",sortable :true,resizeable :true,type :"link"},
          {key :"id.riskCode",text :"险种代码",width :"15em",sortable :true,resizeable :true,type :"link"},
          {key :"id.serialNo",text :"序号",width :"15em",sortable :true,resizeable :true,type :"link"},
          {key :"currency",text :"币别",width :"15em",sortable :true,resizeable :true},
          {key :"grade",text :"评分级别",width :"15em",sortable :true,resizeable :true},
          {key :"retentionValue",text :"自留额",width :"15em",sortable :true,resizeable :true},
          {key :"retentionRate",text :"自留比例(%)",width :"15em",sortable :true,resizeable :true},
          {key :"endDate",text :"终止日期",width :"20em",sortable :true,type :"link",resizeable :true}];
          
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
        var myDataSource = new YAHOO.util.DataSource("${ctx}/dictionary/queryPrpDTreatyReten.do");
        
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
        myDataSource.responseSchema = {
            resultsList:"data",
            fields:["id.uwYear","id.classCode","id.riskCode","id.serialNo","currency","grade","retentionValue","retentionRate","endDate"],
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
	function changeValidStatus(uwYear,classCode,riskCode,serialNo,grade){
	var result ;
	if(grade=="1"){
	result ="确定要注销吗？";
		}else{
	result="确定要启用吗？";
	}
		if(confirm(result)){
		var url="${ctx}/dictionary/changePrpDTreatyRetenValidStatus.do?prpDtreatyReten.id.uwYear="+ uwYear+ "&prpDtreatyReten.id.classCode="
						+ classCode+ "&prpDtreatyReten.id.riskCode="+ riskCode+ "&prpDtreatyReten.id.serialNo="+ serialNo;
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
