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
<title>兑换率</title>
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
			<td class="bgc_tt short">基准币别</td>	
		<td class="long">
<%--
			<div id="validStatusMapDiv" class="selectui-indiv">
				<div class="selectConfig">
					<div class="codeType">StaticSelect</div>
				</div>
				<ce:select name="prpDexch.id.baseCurrency" id="prpDexch.id.baseCurrency" cssClass="selectui-input-up input_y w_p90" value="" list="currencyMap" />
			 </div>
--%>
				<ct:select name="prpDexch.id.baseCurrency" headValue="所有" id="prpDexch.id.baseCurrency" cssClass="selectui-input-up input_y w_p90" sysCode="DMS" codeType="Currency"></ct:select>
		</td>
<!--	
			<td class="long"><input name="prpDexch.id.baseCurrency"
				id="prpDexch.id.baseCurrency" class='input_w w_15'></td>
-->
			<td class="bgc_tt short">兑换币别</td>
		<td class="long">
<%--
			<div id="validStatusMapDiv" class="selectui-indiv">
				<div class="selectConfig">
					<div class="codeType">StaticSelect</div>
				</div>
				<ce:select name="prpDexch.id.exchCurrency" id="prpDexch.id.exchCurrency" cssClass="selectui-input-up input_y w_p90 dc-chk" value="" list="currencyMap" />
			 </div>
--%>
			<ct:select name="prpDexch.id.exchCurrency" headValue="所有" id="prpDexch.id.exchCurrency" cssClass="selectui-input-up input_y w_p90" sysCode="DMS" codeType="Currency"></ct:select>
		</td>
<!--
			<td class="long"><input name="prpDexch.id.exchCurrency"
				id="prpDexch.id.exchCurrency" class='input_w w_15'></td>
-->
			<td colspan="2" align="center">
			<button  type="button"
				 value="" onclick="executeQuery(1,10);"><span><em>查 询</em></span></button>
<!--			<input type="button"-->
<!--				class="button_ty" value="查 询" onclick="executeQuery(1,10);">-->
			</td>
			<td colspan="2" align="center">
			<% if(SyncConstants.ComCode_Head.equals(deployCom)){%>	
			<button type="button"
				 value="" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDexch.do?editType=insert');"><span><em>增 加</em></span></button>												
<!--			<input type="button"-->
<!--				class="button_ty" value="增 加" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDexch.do?editType=insert');">-->
			<%}%>
			</td>
<!-- 
			<td colspan="2" align="center"><input type="button"
				class="button_ty" value="删除" onclick="deleteMethod();">
			</td>
-->
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
<script type="text/javascript"><!--
	var contentDataTable;
	var contentColumnHeaders;
	var deployCom = document.getElementById("deployCom").value;
	YAHOO.namespace("query.container");  
	
	function init(){
		//var userCode_tip = new YAHOO.widget.Tooltip("userCode_tip",{text:"请双击选择员工代码",context:"saaUser.userCode",zIndex:300});
		//var comCode_tip = new YAHOO.widget.Tooltip("comCode_tip",{text:"请双击选择机构代码",context:"saaUser.comCode",zIndex:300});	
		 YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) {
		    var baseCurrency = oRecord["id.baseCurrency"];
		    var exchCurrency = oRecord["id.exchCurrency"];
			    var exchDate = oRecord["id.exchDate"];
			    var data = new Date(exchDate["time"]);
			    var showtime = "";
			    showtime+=data.getFullYear()+"-";
			    showtime+=(data.getMonth()+1)+"-";
			    showtime+=data.getDate();
		     if(oColumn.key=="edit"){
	    		 elCell.innerHTML = "<a href=\"#\" onclick=\"editRecord('${ctx}/dictionary/prepareUpdatePrpDexch.do?editType=update&prpDexch.id.baseCurrency="+baseCurrency+"&prpDexch.id.exchCurrency="+exchCurrency+"&showTime="+showtime+"')\">修改</a>";
		     }
		     if(oColumn.key=="id.exchDate"){
	    		elCell.innerHTML = showtime;
		      }
			
		//     if(oColumn.key == "chkbox"){
	    //        	elCell.innerHTML = "<input type='checkbox' name='chkbox' value='"+baseCurrency+"@"+exchCurrency+"@"+showtime+"'>";
	    //     }
		     if (oColumn.key == "id.baseCurrency") {
	                elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDexch.do?editType=view&prpDexch.id.baseCurrency="+baseCurrency+"&prpDexch.id.exchCurrency="+exchCurrency+"&showTime="+showtime+"')\">"+oData+"</a>"
	         }
		};
		 if(deployCom == '<%=SyncConstants.ComCode_Head %>'){
		contentColumnHeaders =[
//		    {key :"chkbox",text :"选择",width :"15em",sortable :false,resizeable :true,type:"link"},
			{key:"id.baseCurrency",text:"基准币别",width:"40em",sortable:true,type:"link"},
			{key:"id.exchCurrency",text:"兑换币别",width:"40em",sortable:true},
			{key:"exchRate",text:"兑换汇率",width:"50em",sortable:true},
			{key:"id.exchDate",text:"汇率日期",width:"50em",sortable:false,type:"link"},
			{key:"edit",text:"修改",width:"20em",type:"link",resizeable:true}];
			
			executeQuery(1,10); 
		}
	      else{
	      	contentColumnHeaders =[
//		    {key :"chkbox",text :"选择",width :"15em",sortable :false,resizeable :true,type:"link"},
			{key:"id.baseCurrency",text:"基准币别",width:"40em",sortable:true,type:"link"},
			{key:"id.exchCurrency",text:"兑换币别",width:"40em",sortable:true},
			{key:"exchRate",text:"兑换汇率",width:"50em",sortable:true},
			{key:"id.exchDate",text:"汇率日期",width:"50em",sortable:false,type:"link"}];
			
			executeQuery(1,10);		
	      }
	    }
	//Query Data
	function executeQuery(pageNo,pageSize){
		
		if(isNaN(parseInt(pageNo))){ 
			pageNo = 1;
		}
		if(isNaN(parseInt(pageSize))){
			pageSize = 10;
		}
		var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);	
		var myDataSource = new YAHOO.util.DataSource("${ctx}/dictionary/queryPrpDexch.do");
		myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["id.baseCurrency", "id.exchCurrency", "exchRate","id.exchDate"],
		   totalRecords: "totalRecords"
		};
		myDataSource.subscribe("responseParseEvent", SINOSOFT.util.navigation);	
		myDataSource.connMgr.setForm(fm);
		var initialRequest = "pageSize=" + pageSize + "&pageNo=" + pageNo;
		var myConfiges = {
			initialRequest :initialRequest,
			paginator :false
		};
			contentDataTable = new YAHOO.widget.DataTable("content",myColumnSet,myDataSource,myConfiges);
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
 				deleteRecord('${ctx}/dictionary/deletePrpDexch.do?chkbox='+checkedValue);
 			}else{
 				alert("没有选中列！");
 			}
          }
 }
	--></script>