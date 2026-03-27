<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<html>
<head>
<title>飞机代码</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>

</head>

<body id="all_title">

<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">请输入查询条件</h2>
</div>
<s:form name="fm" action="queryPrpDplane" namespace="/dictionary" method="post">
<s:hidden name="flag" id="flag"></s:hidden>
	<table class="fix_table">	
		<tr>
			<td class="bgc_tt short">注册号</td>			
			<td class="long"><input name="prpDplane.licenceNo"
				id="prpDplane.licenceNo" class='input_w w_15'></td>			
			<td class="bgc_tt short">种类</td>
			 <td class="long"><s:select name="prpDplane.planeType" 
          		list="#@java.util.HashMap@{'':'所有','1':'宽体机','2':'窄体机','3':'混合机'}"/></td>
			<td colspan="2" align="center">
			<button type="button"
				 value="" onclick="executeQuery(1,10);"><span><em>查 询</em></span></button>
<!--			<input type="button"-->
<!--				class="button_ty" value="查 询" onclick="executeQuery(1,10);">-->
			</td>
			<td colspan="2" align="center">
			<button type="button"
				 value="" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDplane.do?editType=insert');"><span><em>增 加</em></span></button>
<!--			<input type="button"-->
<!--				class="button_ty" value="增 加" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDplane.do?editType=insert');">-->
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
<script type="text/javascript">
	var contentDataTable;
	var contentColumnHeaders; 
	YAHOO.namespace("query.container");
	
	
    function init() {
        //var userCode_tip = new YAHOO.widget.Tooltip("userCode_tip",{text:"请双击选择员工代码",context:"saaUser.userCode",zIndex:300});
        //var comCode_tip = new YAHOO.widget.Tooltip("comCode_tip",{text:"请双击选择机构代码",context:"saaUser.comCode",zIndex:300});	
        YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn,oData) {
            var licenceNo = oRecord.licenceNo;
            var valid = oRecord.validStatus;
            if (oColumn.key == "licenceNo") {
                elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDplane.do?licenceNo="
                    + licenceNo + "&editType=view')\">"+oData+"</a>"
            }
            
            if (oColumn.key == "edit") {
                elCell.innerHTML = "<a href=\"#\" onclick=\"editRecord('${ctx}/dictionary/prepareUpdatePrpDplane.do?licenceNo="
                        + licenceNo + "&editType=update')\">修改</a>";
            }
 			if(oColumn.key=="status"){
		    	if(valid == "1"){
		    	 	elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('${ctx}/dictionary/changePrpDplaneValidStatus.do?prpDplane.licenceNo="+licenceNo+"')\">注销</a>";
		    	}else{
		    		elCell.innerHTML = "<a href=\"#\" onclick=\"changeValidStatus('${ctx}/dictionary/changePrpDplaneValidStatus.do?prpDplane.licenceNo="+licenceNo+"')\">启用</a>";
		    	}
	    		
		      }
            if(oColumn.key == "chkbox"){
            	elCell.innerHTML = "<input type='checkbox' name='chkbox' value='"+licenceNo+"'>";
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
             
            if (oColumn.key == "planeType") {
                switch (oRecord.planeType) {
                    case '1':
                        elCell.innerHTML = "宽体机";
                        break;
                    case '2':
                        elCell.innerHTML = "窄体机";
                        break;
                    case '3':
                        elCell.innerHTML= "混合型";
                        break;
                }
            }
        };

        contentColumnHeaders = [
          {key :"chkbox",text :"选择",width :"15em",sortable :false,resizeable :true,type :"link"},
          {key :"licenceNo",text :"注册号",width :"20em",sortable :true,type :"link"}, 
          {key :"planeType",text :"飞机种类",width :"20em",sortable :true,type :"link"}, 
          {key :"model",text :"机型",width :"30em",sortable :true}, 
          {key :"airlineCname",text :"航空公司中文名",width :"50em",sortable :true}, 
          {key :"validStatus",text :"状态",width :"15em",sortable :true,type :"link"}, 
          {key :"edit",text :"修改",width :"15em",type :"link",resizeable :true},
          {key :"status",text :"注销/启用",width :"20em",type :"link",resizeable :true}];
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
        var myDataSource = new YAHOO.util.DataSource("${ctx}/dictionary/queryPrpDplane.do");
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
        myDataSource.responseSchema = {
            resultsList :"data",
            fields : ["licenceNo", "planeType", "model", "factoryNo","airlineCname","validStatus"],
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
				deleteRecord('${ctx}/dictionary/deletePrpDplane.do?chkbox='+checkedValue);
			}else{
				alert("没有选中列！");
			}
         }
    }
	function changeValidStatus(url){
		if(confirm("确定要注销/启用？")){
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